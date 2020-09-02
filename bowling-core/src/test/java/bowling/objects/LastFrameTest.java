package bowling.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LastFrameTest {

    private LastFrame frame;

    @Before
    public void setup(){
        frame = new LastFrame();
        frame.setIndex(0);
        frame.setComplete(Boolean.TRUE);
    }

    @Test
    public void baseFrameScoreTestShot3Empty(){
        frame.setShot1("8");
        frame.setShot2("2");
        String score = frame.baseFrameScore();
        Assert.assertEquals("Should be equals", "10", score);
    }

    @Test
    public void baseFrameScoreTestShot3(){
        frame.setShot1("10");
        frame.setShot2("10");
        frame.setShot3("5");
        String score = frame.baseFrameScore();
        Assert.assertEquals("Should be equals", "25", score);
    }

    @Test
    public void pinfallsToStringTestShot1Strike(){
        frame.setShot1("10");
        frame.setShot2("10");
        String shots = frame.pinfallsToString(Boolean.TRUE);
        Assert.assertEquals("Should be equals", "X X", shots);
    }

    @Test
    public void pinfallsToStringTestSpare(){
        frame.setShot1("4");
        frame.setShot2("6");
        frame.setShot3("10");
        String shots = frame.pinfallsToString(Boolean.FALSE);
        Assert.assertEquals("Should be equals", "4 / X", shots);
    }

    @Test
    public void pinfallsToStringTestNormal(){
        frame.setShot1("10");
        frame.setShot2("2");
        frame.setShot3("3");
        String shots = frame.pinfallsToString(Boolean.TRUE);
        Assert.assertEquals("Should be equals", "X 2 3", shots);
    }
}
