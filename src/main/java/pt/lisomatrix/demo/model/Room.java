package pt.lisomatrix.demo.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room implements Serializable {

    private int id;
    private String name;
    private List<String> participants;
    private boolean hasStarted;
    private String creatorId;
}
