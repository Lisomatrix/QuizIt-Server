package pt.lisomatrix.demo.handler;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.lisomatrix.demo.constants.MessageType;
import pt.lisomatrix.demo.messages.AnswerMessage;
import pt.lisomatrix.demo.messages.ScoreMessage;
import pt.lisomatrix.demo.model.*;
import pt.lisomatrix.demo.service.QuestionService;
import pt.lisomatrix.demo.service.TopScoreService;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.time.Duration;
import java.util.*;

/**
 * Responsible to handle the questions game
 */
public class RoomGameHandler {

    private static final float SCORE_MULTIPLIER = 10.3f;
    private Logger logger = LoggerFactory.getLogger(RoomGameHandler.class);

    // Current Room
    private final Room room;

    private QuestionService questionService;
    private TopScoreService topScoreService;

    // Questions amd end Flux
    private UnicastProcessor<Question> questionFlux = UnicastProcessor.create();
    private UnicastProcessor<Boolean> hasEnded = UnicastProcessor.create();

    // Last questions
    private Question lastQuestion;
    // Room users sessions
    private List<UserSession> sessions;

    // Current chapter and number of questions in done in the chapter
    private int currentQuestion = 1;
    private Chapter currentChapter;
    // Available questions to chose
    private List<Question> availableQuestions;
    // The number of sent questions
    private int totalQuestions = 0;

    // Users answers
    private HashMap<String, List<QuestionAnswer>> answers = new HashMap<>();
    private Disposable intervalDisposable;

    // Random in order to get random questions
    private Random generator = new Random();

    // Current winner name and score
    private String currentWinnerName = "";
    private float currentHighestScore = 0f;

    /**
     * Initialize required objects, and start the game
     *
     * @param room
     * @param questionService
     * @param sessions
     * @param topScoreService
     */
    public RoomGameHandler(Room room, QuestionService questionService, List<UserSession> sessions, TopScoreService topScoreService) {
        this.room = room;
        this.questionService = questionService;
        this.topScoreService = topScoreService;
        this.sessions = sessions;

        init(room.getParticipants());
        logger.info("Room with name " + room.getName() + " initialized!");
    }

    /**
     * Get incorrect answers
     *
     * @param answerMessages
     * @return list of the questions organized by questions
     */
    private Map<Integer, List<Question>> getIncorrectQuestions(List<QuestionAnswer> answerMessages) {
        // Declare KeyValue HashMap
        Map<Integer, List<Question>> wrongQuestions = new HashMap<>();

        // For each answers
        for (QuestionAnswer questionAnswer : answerMessages) {// Check if it is incorrect
            if (!questionAnswer.isCorrect()) {
                // If so get the question from the service
                Optional<Question> foundQuestion = questionService.getQuestionById(questionAnswer.getQuestion());
                // Check if the question exists
                if (foundQuestion.isPresent()) {
                    Question question = foundQuestion.get();

                    // Verify if the question chapter is already in the hash map
                    // If so then add it to the hash map value
                    // Otherwise create the chapter key and set the value as an array with this question
                    if (wrongQuestions.containsKey(question.getChapter())) {
                        wrongQuestions.get(question.getChapter()).add(question);
                    } else {
                        List<Question> temp = new ArrayList<>();
                        temp.add(question);

                        wrongQuestions.put(question.getChapter(), temp);
                    }
                }
            }
        }

        return wrongQuestions;
    }

    /**
     * Sends the incorrect answers to every user
     */
    private void showIncorrectAnswers() {
        logger.info("Room with name " + room.getName() + " showing incorrect answers!");
        answers.forEach((id, answersList) -> {
            Map<Integer, List<Question>> wrongQuestions = getIncorrectQuestions(answersList);

            getSessionById(id)
                    .ifPresent(userSession -> userSession.getOutput()
                    .onNext(new Message(MessageType.WRONG_QUESTIONS, wrongQuestions, id)));
        });
    }

    /**
     * Sends the incorrect answers to the session with given id
     *
     * @param userId
     */
    private void showIncorrectAnswers(String userId) {
        logger.info("Room with name " + room.getName() + " showing incorrect answers to user with id " + userId + "!");
        for (Map.Entry<String, List<QuestionAnswer>> entry : answers.entrySet()) {
            String id = entry.getKey();

            if (id.equals(userId)) {
                List<QuestionAnswer> answersList = entry.getValue();
                Map<Integer, List<Question>> wrongQuestions = getIncorrectQuestions(answersList);


                getSessionById(id)
                        .ifPresent(userSession -> userSession.getOutput()
                                .onNext(new Message(MessageType.WRONG_QUESTIONS, wrongQuestions, id)));

                break;
            }
        }
    }

    /**
     * Handle received answer
     *
     * @param answer
     * @param id
     */
    public void setAnswer(AnswerMessage answer, String id) {
        try {
            boolean isCorrect = false;

            if (lastQuestion.getAnswer() == answer.getAnswer()) {
                isCorrect = true;
            }

            if (answers.containsKey(answer.getParticipantId())) {
                boolean finalIsCorrect = isCorrect;
                answers.get(answer.getParticipantId()).add(new QuestionAnswer(lastQuestion.getId(), isCorrect, lastQuestion.getChapter()));
                getSessionById(id).ifPresent(session -> session.getOutput().onNext(new Message(MessageType.ANSWER_RESPONSE, finalIsCorrect, id)));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Allows the ws handler to get latest questions
     * @return
     */
    public Flux<Question> getQuestion() {
        return questionFlux.serialize();
    }

    /**
     * Allows the ws handle to when the game finished
     * @return
     */
    public Flux<Boolean> hasEnded() {
        return hasEnded.serialize();
    }

    /**
     * Clear the no longer needed fluxes
     */
    public void dispose() {
        if (!questionFlux.isDisposed()) {
            //questionFlux.dispose();
        }

        if (!hasEnded.isDisposed()) {
            //hasEnded.dispose();
        }
    }

    /**
     * Calculate users score
     */
    private void calculateScore() {
        logger.info("Room with name " + room.getName() + " calculating scores!");

        Map<String, ScoreMessage> scoreMessages = new HashMap<>();

        answers.forEach((id, answersList) -> {
            int correctNumber = getCorrectNumber(answersList);
            int incorrectNumber = totalQuestions - correctNumber;
            float score = correctNumber * SCORE_MULTIPLIER;

            if (score > currentHighestScore) {
                currentHighestScore = score;
                currentWinnerName = id;
            }

            topScoreService.addTopScore(id, score);
            scoreMessages.put(id, new ScoreMessage(score, correctNumber, incorrectNumber, currentWinnerName));
        });

        getSessionById(currentWinnerName).ifPresent(session -> currentWinnerName = session.getParticipant().getName());

        scoreMessages.forEach(this::sendScore);
    }

    /**
     * Checks if the users have at least 2 correct answers
     */
    private void nextChapterValidation() {
        logger.info("Room with name " + room.getName() + " making next chapter validation!");
        try {
            answers.forEach((id, answerList) -> {
                int correctNumber = getCorrectNumber(answerList, currentChapter.getNumber() - 1);

                // if less than 2 answers were correct
                if (correctNumber < 2) {
                    // Send them their score and remove them from room sessions and also remove is answers entry
                    correctNumber = getCorrectNumber(answerList);

                    sendScore(id, new ScoreMessage(correctNumber * SCORE_MULTIPLIER, correctNumber,
                            totalQuestions - correctNumber, currentWinnerName));

                    // Show this user incorrect answers
                    showIncorrectAnswers(id);

                    getSessionById(id).ifPresent(userSession -> sessions.remove(userSession));
                    answers.remove(id);
                }
            });
        } catch (Exception ignored) {

        }
    }

    /**
     * Send next questions if available
     * @param ignore
     */
    private void getNextQuestionByChapter(long ignore) {

        if (currentQuestion > 4 || availableQuestions.size() == 0) {
            // Get next chapter and update the available questions
            // if returns false then there are no more chapters
            if (getNextChapter()) {
                nextChapterValidation();
                // Update current questions
                updateLastQuestion();
            } else {
                // Calculate and send the score for the users
               sendEndMessage();

                // Clear interval and notify the ws handler that the game has ended
                gameEnded();
            }
        } else {
            // Update current questions
            updateLastQuestion();
            currentQuestion++;
        }
    }

    /**
     * Get a random questions from the available ones
     * @return
     */
    private Question getRandomQuestion() {
        int randomIndex = generator.nextInt(availableQuestions.size());
        logger.info("Room with name " + room.getName() + " getting next question!");
        return availableQuestions.get(randomIndex);
    }

    /**
     * Update chapter and available questions to the next chapter
     * If there aren't other chapter then return false
     * @return
     */
    private boolean getNextChapter() {
        Optional<Chapter> chapter = questionService.hasNextChapter(currentChapter.getNumber());

        if (chapter.isPresent()) {
            updateChapter(chapter.get());
            return true;
        } else {
            sendEndMessage();
            showIncorrectAnswers();
            logger.info("Room with name " + room.getName() + " ended!");
            return false;
        }
    }

    /**
     * Initialize the answers hash map
     * @param participants
     */
    private void init(List<String> participants) {
        for (String participant : participants) {
            answers.put(participant, new ArrayList<>());
        }
        // Get first chapter and update available questions
        currentChapter = questionService.getFirstChapter();
        availableQuestions = new ArrayList<>(currentChapter.getQuestions());
        updateLastQuestion();

        // Start interval of 5 seconds to send new questions
        // TODO: UNCOMMENT THE LINE WITH 23 SECONDS AND DELETE THE OTHER
        intervalDisposable = Flux.interval(Duration.ofSeconds(23)).subscribe(this::getNextQuestionByChapter);
        //intervalDisposable = Flux.interval(Duration.ofMillis(100)).subscribe(this::getNextQuestionByChapter);

        hasEnded.onNext(false);

        // Send start event to room users
        for (UserSession session: sessions) {
            session.getOutput().onNext(new Message(MessageType.START, null, session.getId()));
        }
    }

    /**
     * Find session by the given id
     * @param id
     * @return
     */
    private Optional<UserSession> getSessionById(String id) {
        for (UserSession session : sessions) {
            if (session.getId().equals(id)) {
                return Optional.of(session);
            }
        }

        return Optional.empty();
    }

    /**
     * Update the latest questions with a random one from the available
     */
    private void updateLastQuestion() {
        lastQuestion = getRandomQuestion();
        questionFlux.onNext(lastQuestion);
        availableQuestions.remove(lastQuestion);
        checkIfParticipantAnswered();
        totalQuestions++;
        logger.info("Updating last question on room with name " + room.getName());
    }

    /**
     * Check if user has answered if not then create a wrong answer
     */
    private void checkIfParticipantAnswered() {
        logger.info("Room with name " + room.getName() + " verifying received answers!");
        for (Map.Entry<String, List<QuestionAnswer>> entry : answers.entrySet()) {
            List<QuestionAnswer> value = entry.getValue();

            if (totalQuestions == 1 && value.size() == 1) {
                return;
            }

            if (value.size() < totalQuestions) {
                // TODO: CHANGE TO FALSE
                //value.add(new QuestionAnswer(lastQuestion.getId(), true, lastQuestion.getChapter()));
                value.add(new QuestionAnswer(lastQuestion.getId(), false, lastQuestion.getChapter()));
            }
        }
    }

    /**
     * Update current chapter and available questions accordingly to the given one
     * @param chapter
     */
    private void updateChapter(Chapter chapter) {
        currentChapter = chapter;
        availableQuestions = new ArrayList<>(currentChapter.getQuestions());
        currentQuestion = 1;
        logger.info("Room with name " + room.getName() + " updating chapter!");
    }

    /**
     * Send end message to all users
     */
    private void sendEndMessage() {
        logger.info("Room with name " + room.getName() + " sending end message!");
        for (UserSession session: sessions) {
            session.getOutput().onNext(new Message(MessageType.END, null, session.getId()));
            calculateScore();
        }
    }

    /**
     * Clear interval and notify listeners that the game ended
     */
    private void gameEnded() {
        intervalDisposable.dispose();
        hasEnded.onNext(true);
    }

    /**
     * Get overall correct answers number
     * @param answers
     * @return
     */
    private int getCorrectNumber(List<QuestionAnswer> answers) {
        int correct = 0;

        for (QuestionAnswer questionAnswer : answers) {
            if (questionAnswer.isCorrect()) {
                correct++;
            }
        }

        return correct;
    }

    /**
     * Get the correct answers number by chapter
     *
     * @param answers
     * @param chapter
     * @return
     */
    private int getCorrectNumber(List<QuestionAnswer> answers, int chapter) {
        int correct = 0;

        for (QuestionAnswer questionAnswer : answers) {
            if (questionAnswer.isCorrect() && questionAnswer.getChapter() == chapter) {
                correct++;
            }
        }

        return correct;
    }

    /**
     * Send the score message to the user with the given id
     * @param id
     * @param scoreMessage
     */
    private void sendScore(String id, ScoreMessage scoreMessage) {
        logger.info("Room with name " + room.getName() + " sending score!");
        for(UserSession userSession: sessions) {
            if (userSession.getId().equals(id)) {
                userSession.getOutput().onNext(new Message(MessageType.SCORE, scoreMessage, id));
            }
        }
    }

}
