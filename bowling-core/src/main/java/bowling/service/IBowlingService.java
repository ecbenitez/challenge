package bowling.service;

import bowling.objects.PlayerGame;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IBowlingService {

    /**
     * Read file, map it and make general validations.
     * @param textFile file with bowling results.
     * @return a map with shots per player.
     */
    Map<String, List<String>> readFile(File textFile);

    /**
     * Process a map to get all the needed information.
     * @param gamersShots map with shots per player
     * @return game information per player.
     */
    List<PlayerGame> processGame(Map<String, List<String>> gamersShots);
}
