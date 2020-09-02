package bowling.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FrameTest {

    private Frame frame;

    @Before
    public void setup(){
        frame = new Frame();
        frame.setIndex(0);
        frame.setComplete(Boolean.TRUE);
    }

    @Test
    public void baseFrameScoreTestSpare(){
        frame.setShot1("8");
        frame.setShot2("2");
        frame.setScore(14);
        String score = frame.baseFrameScore();
        Assert.assertEquals("Should be equals", "/", score);
    }

    @Test
    public void baseFrameScoreTestStrike(){
        frame.setShot1("10");
        frame.setScore(18);
        String score = frame.baseFrameScore();
        Assert.assertEquals("Should be equals", "X", score);
    }

    @Test
    public void baseFrameScoreTestNormal(){
        frame.setShot1("4");
        frame.setShot2("2");
        frame.setScore(6);
        String score = frame.baseFrameScore();
        Assert.assertEquals("Should be equals", "6", score);
    }

    @Test
    public void pinfallsToStringTestStrike(){
        frame.setShot1("10");
        frame.setScore(18);
        String shots = frame.pinfallsToString(Boolean.FALSE);
        Assert.assertEquals("Should be equals", "    X", shots);
    }

    @Test
    public void pinfallsToStringTestSpare(){
        frame.setShot1("4");
        frame.setShot2("6");
        frame.setScore(14);
        String shots = frame.pinfallsToString(Boolean.FALSE);
        Assert.assertEquals("Should be equals", "4  /", shots);
    }

    @Test
    public void pinfallsToStringTestNormal(){
        frame.setShot1("4");
        frame.setShot2("2");
        frame.setScore(6);
        String shots = frame.pinfallsToString(Boolean.TRUE);
        Assert.assertEquals("Should be equals", "4 2", shots);
    }
}
