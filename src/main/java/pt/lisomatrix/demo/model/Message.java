package pt.lisomatrix.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.lisomatrix.demo.constants.MessageType;

@Getter
public class Message {

    private MessageType type;
    private Object data;

    @JsonIgnore
    private String destiny;

    public Message(MessageType type, Object data, String destiny) {
        this.type = type;
        this.data = data;
        this.destiny = destiny;
    }

    public Message() {
        super();
    }
}
