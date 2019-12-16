package pt.lisomatrix.demo.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScoreMessage {

    private float score;
    private int correct;
    private int wrong;
    private String winner = "";
}
