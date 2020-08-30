package bowling.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frame {
    Integer index;
    String shot1;
    String shot2;
    Integer score;
    Boolean complete;
}
