package pt.lisomatrix.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAnswer {

    private int question;
    private boolean isCorrect;
    private int chapter;
}
