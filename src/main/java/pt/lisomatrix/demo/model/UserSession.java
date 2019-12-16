package pt.lisomatrix.demo.model;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.UnicastProcessor;

public class UserSession {

    private String id;
    private int roomId;
    private String participantId;
    private Participant participant;
    private UnicastProcessor<Message> output;

    public UserSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public UnicastProcessor<Message> getOutput() {
        return output;
    }

    public void setOutput(UnicastProcessor<Message> output) {
        this.output = output;
    }
}
