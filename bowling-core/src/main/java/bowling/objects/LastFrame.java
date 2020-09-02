package bowling.objects;

import bowling.util.ScoreValueUtil;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 10 Frame specialization class.
 */
@Data
public class LastFrame extends Frame{
    private static final Integer SPARESUM = 10;
    private static final String STRIKE = "10";
    private static final String SPARE = "/";

    String shot3;

    /**
     * LastFrame empty constructor.
     */
    @Builder
    public LastFrame(){
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String baseFrameScore(){
        Integer shot1Value = ScoreValueUtil.getShotValuePreventFoul(shot1);
        Integer shot2Value = ScoreValueUtil.getShotValuePreventFoul(shot2);
        Integer shot3Value;
        if(StringUtils.isNotEmpty(shot3)){
            shot3Value = ScoreValueUtil.getShotValuePreventFoul(shot3);
        }else{
            shot3Value = 0;
        }
        return String.valueOf(shot1Value+shot2Value+shot3Value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String pinfallsToString(Boolean pureStrings){
        String pinfalls;
        if(shot1.equals(STRIKE)){
            pinfalls = "X";
        }else{
            pinfalls = shot1;
        }

        Integer shot1Value = ScoreValueUtil.getShotValuePreventFoul(shot1);
        Integer shot2Value = ScoreValueUtil.getShotValuePreventFoul(shot2);
        if(shot1Value + shot2Value == SPARESUM){
            pinfalls = pinfalls.concat(" ").concat(SPARE);
        }else if(shot2.equals(STRIKE)){
            pinfalls = pinfalls.concat(" ").concat("X");
        }else{
            pinfalls = pinfalls.concat(" ").concat(shot2);
        }

        if(StringUtils.isNotEmpty(shot3) && !shot3.equals(STRIKE)){
            pinfalls = pinfalls.concat(" ").concat(shot3);
        }else if(StringUtils.isNotEmpty(shot3) && shot3.equals(STRIKE)){
            pinfalls = pinfalls.concat(" ").concat("X");
        }
        return  pinfalls;
    }
}
