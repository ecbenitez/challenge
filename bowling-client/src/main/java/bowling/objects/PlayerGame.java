package bowling.objects;

import bowling.util.ScoreValueUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Player game class.
 */
@Data
@NoArgsConstructor
public class PlayerGame {
    private static final String FOUL = "F";
    String playerName;
    List<Frame> frames;

    /**
     * Calculates the score per frame.
     */
    public void calcFramesScore(){
        Integer currentGameScore = 0;
        for(Frame frame: frames){
            String frameBase = frame.baseFrameScore();
            Integer frameBaseScore;
            if(frameBase.equals("X") || frameBase.equals("/")){
                frameBaseScore = 10;
            }else{
                frameBaseScore = Integer.parseInt(frameBase);
            }
            // bonus calc
            Integer bonus = 0;
            if(frame.getIndex() < 9 && frameBase.equals("/")){
                String shot = frames.get(frame.getIndex() + 1).getShot1();
                bonus = ScoreValueUtil.getShotValuePreventFoul(shot);
            }else if(frame.getIndex() < 9 && frameBase.equals("X")){
                Integer shot1Value = ScoreValueUtil.getShotValuePreventFoul(frames.get(frame.getIndex() + 1).getShot1());
                Integer shot2Value;
                if(frame.getIndex() == 8 || shot1Value < 10){
                    shot2Value = ScoreValueUtil.getShotValuePreventFoul(frames.get(frame.getIndex() + 1).getShot2());
                }else{
                    shot2Value = ScoreValueUtil.getShotValuePreventFoul(frames.get(frame.getIndex() + 2).getShot1());
                }
                bonus = shot1Value + shot2Value;
            }
            currentGameScore = currentGameScore + frameBaseScore + bonus;
            frame.setScore(currentGameScore);
        }
    }
}
