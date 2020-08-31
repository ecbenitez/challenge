package bowling.objects;

import bowling.util.ScoreValueUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Frame class.
 */
@Data
@NoArgsConstructor
public class Frame {
    private static final String STRIKE = "10";
    private static final String SPARE = "/";
    private static final Integer SPARESUM = 10;

    Integer index;
    String shot1;
    String shot2;
    Integer score;
    Boolean complete;

    /**
     * Calculates base frame score.
     * @return base frame score.
     */
    public String baseFrameScore(){
        String score;
        if(shot1.equals(STRIKE)){
            return "X";
        }
        Integer shot1Value = ScoreValueUtil.getShotValuePreventFoul(shot1);
        Integer shot2Value = ScoreValueUtil.getShotValuePreventFoul(shot2);
        if(shot1Value + shot2Value == SPARESUM){
            score = SPARE;
        }else{
            score = String.valueOf(shot1Value + shot2Value);
        }
        return score;
    }

    /**
     * Build shots string.
     * @return shots string.
     */
    public String pinfallsToString(){
        String pinfalls;
        if(shot1.equals(STRIKE)){
            pinfalls = "    X";
        }else if(baseFrameScore().equals(SPARE)){
            pinfalls = shot1.concat("  ").concat(SPARE);
        }else{
            pinfalls = shot1.concat("  ").concat(shot2);
        }
        return  pinfalls;
    }
}
