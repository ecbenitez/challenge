package bowling.service;

import bowling.exception.BowlingException;
import bowling.objects.PlayerGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class BowlingServiceTestProcessGame {

    private BowlingService bowlingService;


    @Before
    public void setup(){
        bowlingService = new BowlingService();
    }

    @Test
    public void processGameTestSuccess(){
        Map<String, List<String>> gamersShots = new HashMap<>();
        gamersShots.put("John", new ArrayList<>(Arrays.asList("3", "7", "6", "3", "10", "8", "1", "10", "10", "9", "0", "7", "3", "4", "4", "10", "9", "F")));

        List<PlayerGame> playerGames = this.bowlingService.processGame(gamersShots);
        Assert.assertEquals("Should be equals:", "John", playerGames.get(0).getPlayerName());
        Assert.assertEquals("Should be equals:", String.valueOf(16), playerGames.get(0).getFrames().get(0).getScore().toString());
    }

    @Test
    public void processGameTestInvalid1(){
        Map<String, List<String>> gamersShots = new HashMap<>();
        gamersShots.put("Paco", new ArrayList<>(Arrays.asList("3", "7", "6", "3", "10", "8", "1", "10", "10", "F")));
        try {
            this.bowlingService.processGame(gamersShots);
        }catch (BowlingException e){
            Assert.assertEquals("Should throw BowlingException", "GAME_INVALID", e.getMessage());
        }
    }

    @Test
    public void processGameTestInvalid2(){
        Map<String, List<String>> gamersShots = new HashMap<>();
        gamersShots.put("Paco", new ArrayList<>(Arrays.asList("3", "7", "6", "3", "10", "8", "1", "10", "10", "9", "0", "7", "3", "4", "4", "10", "9", "F")));
        try {
            this.bowlingService.processGame(gamersShots);
        }catch (BowlingException e){
            Assert.assertEquals("Should throw BowlingException", "GAME_INVALID_SCORES", e.getMessage());
        }
    }

    @Test
    public void processGameTestInvalid3(){
        Map<String, List<String>> gamersShots = new HashMap<>();
        gamersShots.put("Paco", new ArrayList<>(Arrays.asList("3", "7", "6", "3", "10", "8", "1", "10", "10", "9", "0", "7", "3", "1", "1", "10", "9", "F", "5")));
        try {
            this.bowlingService.processGame(gamersShots);
        }catch (BowlingException e){
            Assert.assertEquals("Should throw BowlingException", "GAME_INVALID_FRAMES", e.getMessage());
        }
    }

    @Test
    public void processGameTestInvalid4(){
        Map<String, List<String>> gamersShots = new HashMap<>();
        gamersShots.put("Paco", new ArrayList<>(Arrays.asList("3", "7", "6", "3", "10", "8", "1", "10", "10", "9", "0", "7", "3", "1", "1", "2", "6", "F")));
        try {
            this.bowlingService.processGame(gamersShots);
        }catch (BowlingException e){
            Assert.assertEquals("Should throw BowlingException", "GAME_INVALID_LAST_FRAME", e.getMessage());
        }
    }
}
