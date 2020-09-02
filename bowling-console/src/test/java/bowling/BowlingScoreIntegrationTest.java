package bowling;

import bowling.objects.PlayerGame;
import bowling.service.BowlingService;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BowlingScoreIntegrationTest {

    private BowlingService bowlingService;


    @Before
    public void setup(){
        bowlingService = new BowlingService();
    }

    @Test
    public void bowlingScoreTestStringsAsTable() {
        File textFile = new File("./src/test/resources/perfectGameTest.txt");
        Map<String, List<String>> gamersShots = this.bowlingService.readFile(textFile);
        List<PlayerGame> playerGames = bowlingService.processGame(gamersShots);
        ShowDataAsTable.printStringsAsTable(playerGames);
    }

    @Test
    public void bowlingScoreTestAsTable() {
        File textFile = new File("./src/test/resources/perfectGameTest.txt");
        Map<String, List<String>> gamersShots = this.bowlingService.readFile(textFile);
        List<PlayerGame> playerGames = bowlingService.processGame(gamersShots);
        ShowDataAsTable.printTable(playerGames);
    }
}
