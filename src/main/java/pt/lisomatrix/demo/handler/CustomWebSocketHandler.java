package pt.lisomatrix.demo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import pt.lisomatrix.demo.constants.MessageType;
import pt.lisomatrix.demo.messages.AnswerMessage;
import pt.lisomatrix.demo.messages.JoinRoomMessage;
import pt.lisomatrix.demo.model.*;
import pt.lisomatrix.demo.service.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class CustomWebSocketHandler implements WebSocketHandler {

    private Map<String, UserSession> sessions = new HashMap<>();

    private final RoomService roomService;
    private final ParticipantService participantService;
    private final QuestionService questionService;
    private final TopScoreService topScoreService;

    private ObjectMapper mapper;

    private Map<Integer, RoomGameHandler> roomGameHandlerMap = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(CustomWebSocketHandler.class);

    public CustomWebSocketHandler(RoomService roomService, ParticipantService participantService, QuestionService questionService, TopScoreService topScoreService) {
        this.roomService = roomService;
        this.participantService = participantService;
        this.questionService = questionService;
        this.topScoreService = topScoreService;
        this.mapper = new ObjectMapper();

        logger.info("CustomWebSocketHandler loaded!");
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        UnicastProcessor<Message> sessionOutput = UnicastProcessor.create();
        UserSession userSession = new UserSession(webSocketSession.getId());

        if (!sessions.containsKey(webSocketSession.getId())) {
            userSession.setOutput(sessionOutput);
            sessions.put(webSocketSession.getId(), userSession);
        }

        WebSocketMessageSubscriber webSocketMessageSubscriber = new WebSocketMessageSubscriber(sessionOutput, userSession, webSocketSession);

         webSocketSession.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(this::toMessage)
                .doOnError(error -> webSocketMessageSubscriber.handleUserDisconnect())
                .doFinally(signalType -> webSocketMessageSubscriber.handleUserDisconnect())
                .subscribe(message ->
                        webSocketMessageSubscriber.onNext(message, webSocketSession.getId()), webSocketMessageSubscriber::onError, webSocketMessageSubscriber::onComplete);

        return webSocketSession.send(sessionOutput
                .map(this::toJson)
                .map(webSocketSession::textMessage))
                .doOnError(error -> logger.error(error.getMessage()));
    }

    private Message toMessage(String json) {
        try {
            return mapper.readValue(json, Message.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON:" + json, e);
        }
    }

    private String toJson(Message message) {
        try {
            return mapper.writeValueAsString(message);
        } catch (IOException e) {
            throw new RuntimeException("Error converting message:", e);
        }
    }

    private List<JoinRoomMessage> createGetRoomsResponse(Collection<Room> rooms, String id) {
        List<JoinRoomMessage> joinRoomMessages = new ArrayList<>();

        for (Room room : rooms) {
            JoinRoomMessage joinRoomMessage = new JoinRoomMessage();
            joinRoomMessage.setId(room.getId());
            joinRoomMessage.setName(room.getName());

            List<Participant> participants = new ArrayList<>();

            for (String participant : room.getParticipants()) {
                if (participant != null) {
                    participantService.get(participant).subscribe(participants::add);
                    //participants.add(participantService.get(participant));
                }
            }

            joinRoomMessage.setParticipants(participants);
            joinRoomMessages.add(joinRoomMessage);
        }

        return joinRoomMessages;
    }

    private Mono<JoinRoomMessage> createJoinRoomResponse(Room room, String id) {
        //List<Participant> participants = new ArrayList<>();
        return Flux.fromIterable(room.getParticipants())
                .flatMap(participantService::get)
                .collectList()
                .map(participants -> {
                    JoinRoomMessage joinRoomMessage = new JoinRoomMessage();
                    joinRoomMessage.setName(room.getName());
                    joinRoomMessage.setId(room.getId());
                    joinRoomMessage.setParticipants(participants);
                    joinRoomMessage.setCreator(room.getCreatorId().equals(id));

                    return joinRoomMessage;
                });
    }

    private class WebSocketMessageSubscriber {
        private UnicastProcessor<Message> messagePublisher;
        private UserSession userSession;
        private WebSocketSession webSocketSession;
        private boolean isAlive = true;

        private WebSocketMessageSubscriber(UnicastProcessor<Message> messagePublisher, UserSession session, WebSocketSession webSocketSession) {
            this.messagePublisher = messagePublisher;
            this.userSession = session;
            this.webSocketSession = webSocketSession;

            performPing();
        }

        private void performPing() {

            Flux.interval(Duration.ofSeconds(8)).subscribe(ignored -> userSession.getOutput()
                    .onNext(new Message(MessageType.PING, null, userSession.getId())));

            Flux.interval(Duration.ofSeconds(13)).subscribe(ignored -> {
                if (isAlive) {
                    isAlive = false;
                } else {
                    webSocketSession.close();
                }
            });
        }

        private void onNext(Message message, String id) {

            if (message.getType().equals(MessageType.PONG)) {
                isAlive = true;
                return;
            }

            logger.info("Message received of type " + message.getType().name());

            // If user has not been created, then ignore requests that are not used for his creation
            if (userSession.getParticipantId() == null && message.getType() != MessageType.NEW_USER) {
                return;
            }

            switch (message.getType()) {
                case NEW_USER:
                    handleNewUser((String) message.getData(), id);
                    break;

                case ANSWER:
                    handleAnswer(mapper.convertValue(message.getData(), AnswerMessage.class), id);
                    break;

                case CREATE_ROOM:
                    handleNewRoom((String) message.getData(), id);
                    break;

                case JOIN_ROOM:
                    handleJoinRoom((Integer) message.getData(), id);
                    break;

                case GET_ROOMS:
                    handleGetRooms(id);
                    break;

                case START:
                    handleStart(id);
                    break;

                case TOP_SCORE:
                    handleTopScore(id);
                    break;

                case LEAVE_ROOM:
                    handleLeaveOrDelete(id, false);
                    break;

                case DELETE_ROOM:
                    handleLeaveOrDelete(id, true);
                    break;
            }
        }

        private void handleUserDisconnect() {
            logger.info(("USER WITH ID: " + userSession.getId() + " DISCONNECTED"));

            Participant me = userSession.getParticipant();

            try {
                handleLeaveOrDelete(me.getId(), true);
                notifyUserJoinedOrLeftRoom(userSession.getRoomId(), me, false);
            } catch (Exception ignore) {}

            if (me != null) {
                participantService.remove(me.getId());
            }
        }

        private void deleteRoom(Room room) {
            logger.info(("Deleting room with id " + room.getId() + " and name " + room.getName()));
            sessions.forEach((s, session) -> session.getOutput().onNext(new Message(MessageType.DELETE_ROOM, room.getId(), s)));

            roomService.delete(room.getId());
        }

        private void handleLeaveOrDelete(String id, boolean delete) {
            if (userSession.getRoomId() == 0 && sessions.get(userSession.getId()).getRoomId() == 0) {
                return;
            }

            roomService.findById(userSession.getRoomId())
                    .subscribe(room -> {
                        if (delete && !room.isHasStarted() && room.getCreatorId().equals(id)) {
                            deleteRoom(room);
                        } else {
                            while (room.getParticipants().remove(null));

                            roomService.insert(room);

                            if (room.getParticipants().size() == 1 || room.getParticipants().size() == 0) {
                                deleteRoom(room);
                            } else {
                                participantService.get(id).subscribe(participant -> notifyUserJoinedOrLeftRoom(room, participant, false));
                                roomService.removeParticipant(room.getId(), userSession.getId());
                            }
                        }

                        userSession.setRoomId(0);
                    });
        }

        private void handleTopScore(String id) {
            userSession.getOutput()
                    .onNext(new Message(MessageType.TOP_SCORE, topScoreService.getTopScores(), id));
        }

        private void handleAnswer(AnswerMessage answerMessage, String id) {
            if (roomGameHandlerMap.containsKey(answerMessage.getRoomId())) {
                roomGameHandlerMap.get(answerMessage.getRoomId()).setAnswer(answerMessage, id);
            }
        }

        private void handleStart(String id) {
            roomService.start(userSession.getRoomId()).subscribe(room -> {
                List<UserSession> roomSessions = new ArrayList<>();

                for (String i : room.getParticipants()) {
                    roomSessions.add(sessions.get(i));
                }

                RoomGameHandler roomGameHandler = new RoomGameHandler(room, questionService, roomSessions, topScoreService);

                roomGameHandler.getQuestion().subscribe(question -> {
                    for (UserSession session : roomSessions) {
                        sessions.get(session.getId()).getOutput().onNext(new Message(MessageType.NEW_QUESTION, question, session.getId()));
                    }
                });

                roomGameHandler.hasEnded()
                        .subscribe(ended -> {
                            roomGameHandler.dispose();
                            roomService.delete(room.getId());
                        });

                roomGameHandlerMap.put(room.getId(), roomGameHandler);
            });
        }

        private void handleNewRoom(String name, String id) {
            roomService.create(name, id, +new Random().nextInt())
                    .subscribe(room -> {
                        userSession.setRoomId(room.getId());
                        sessions.put(userSession.getId(), userSession);

                        createJoinRoomResponse(room, id).subscribe(joinRoomMessage -> {
                            userSession.getOutput().onNext(new Message(MessageType.ROOM_CREATED, joinRoomMessage, id));

                            logger.info(("New room created with name " + room.getName() + " and id " + room.getId()));
                            for (UserSession session : sessions.values()) {
                                if (!session.getId().equals(userSession.getId())) {
                                    session.getOutput().onNext(new Message(MessageType.NEW_ROOM, joinRoomMessage, id));
                                }
                            }
                        });
                    });
        }

        private void handleNewUser(String name, String id) {
            //Participant participant = participantService.create(id, name);
            participantService.create(id, name).subscribe(participant -> {
                messagePublisher.onNext(new Message(MessageType.USER_CREATED, participant, id));
                userSession.setParticipantId(participant.getId());
                userSession.setParticipant(participant);
                sessions.put(id, userSession);
                logger.info(("New user created with name " + participant.getName() + " and id " + participant.getId()));
            });
        }

        private void handleJoinRoom(int roomId, String id) {
            //Participant me = participantService.get(id);

            roomService.joinParticipant(roomId, userSession.getParticipantId())
                    .subscribe(room -> {
                        userSession.setRoomId(room.getId());
                        sessions.put(userSession.getId(), userSession);

                        createJoinRoomResponse(room, id).subscribe(joinRoomMessage -> {
                            messagePublisher.onNext(new Message(MessageType.ROOM_JOINED, joinRoomMessage, id));

                            participantService.get(id).subscribe(me -> notifyUserJoinedOrLeftRoom(room, me, true));
                        });
                        //JoinRoomMessage joinRoomMessage = createJoinRoomResponse(room, id);
                    });
        }

        private void handleGetRooms(String id) {
            roomService.getRooms()
                    .map(rooms -> createGetRoomsResponse(rooms, id))
                    .map(rooms -> new Message(MessageType.GET_ROOMS, rooms, id))
                    .subscribe(messagePublisher::onNext);
        }

        private void onError(Throwable error) {
            error.printStackTrace();
        }

        private void onComplete() {

        }

        private void notifyUserJoinedOrLeftRoom(Room room, Participant me, boolean joined) {

            if (joined) {
                logger.info(("User with id " + me.getId() + " joined room with id " + room.getId()));
            } else {
                userSession.setRoomId(0);
                logger.info(("User with id " + me.getId() + " left room with id " + room.getId()));
            }

            room.getParticipants()
                    .forEach(participantId -> {
                        if (!participantId.equals(userSession.getId())) {
                            sessions.get(participantId)
                                    .getOutput()
                                    .onNext(new Message(joined ? MessageType.USER_JOIN : MessageType.USER_LEFT, me, userSession.getId()));
                        }
                    });
        }

        private void notifyUserJoinedOrLeftRoom(int roomId, Participant me, boolean joined) {
            roomService.findById(roomId)
                    .subscribe(room -> notifyUserJoinedOrLeftRoom(room, me, joined));
        }

    }
}
