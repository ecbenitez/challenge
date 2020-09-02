package bowling.util;

public final class ScoreValueUtil {
    private static final String FOUL = "F";

    public static Integer getShotValuePreventFoul(String shot){
        return shot.equals(FOUL)?0:Integer.parseInt(shot);
    }
}
