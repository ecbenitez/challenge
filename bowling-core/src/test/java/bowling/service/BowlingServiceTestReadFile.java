package bowling.service;

import bowling.exception.BowlingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class BowlingServiceTestReadFile {

    private BowlingService bowlingService;


    @Before
    public void setup(){
        bowlingService = new BowlingService();
    }

    @Test
    public void readFileTest(){
        File textFile = new File("./src/test/resources/perfectGameTest.txt");
        Map<String, List<String>> gamersShots = this.bowlingService.readFile(textFile);
        Assert.assertEquals("Ahould be equals:", "10", gamersShots.get("Carl").get(0));
    }

    @Test
    public void invalidFormatTest(){
        File textFile = new File("./src/test/resources/invalidFormatTest.txt");
        try {
            this.bowlingService.readFile(textFile);
        }catch (BowlingException e){
            Assert.assertEquals("Should throw BowlingException", "INCORRECT_FORMAT", e.getMessage());
        }
    }

    @Test
    public void invalidScoreTest(){
        File textFile = new File("./src/test/resources/invalidScoreTest.txt");
        try {
            this.bowlingService.readFile(textFile);
        }catch (BowlingException e){
            Assert.assertEquals("Should throw BowlingException", "INVALID_SCORE", e.getMessage());
        }
    }
}
