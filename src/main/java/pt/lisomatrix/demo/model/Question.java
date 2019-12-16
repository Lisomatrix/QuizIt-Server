package pt.lisomatrix.demo.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Question {

    private int id;
    private String question;
    private int answer;
    private String[] options;
    private int chapter;
}
