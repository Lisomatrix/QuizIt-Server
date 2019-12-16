package pt.lisomatrix.demo.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerMessage {

    private int roomId;
    private String participantId;
    private int answer;

}
