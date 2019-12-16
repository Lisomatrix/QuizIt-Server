package pt.lisomatrix.demo.messages;

import lombok.Getter;
import lombok.Setter;
import pt.lisomatrix.demo.model.Participant;

import java.util.List;

@Getter
@Setter
public class JoinRoomMessage {

    private int id;
    private String name;
    private List<Participant> participants;
    private boolean isCreator;
    private boolean hasStarted;
}
