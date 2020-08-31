package bowling;

import bowling.objects.Frame;
import bowling.objects.PlayerGame;
import com.dak.wagu.Block;
import com.dak.wagu.Board;
import com.dak.wagu.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowDataAsTable {

    /**
     * Displays in console the game results in table format.
     * @param playerGames game info per player.
     */
    public static void printTable(List<PlayerGame> playerGames){

        List<String> headersList = Arrays.asList("FRAME", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        List<List<String>> rows = new ArrayList<>();
        for (PlayerGame playerGame: playerGames){
            List<String> nameRow = Stream.generate(String::new).limit(11).collect(Collectors.toList());
            nameRow.set(0, playerGame.getPlayerName());
            rows.add(nameRow);

            List<String> pinfalls = new ArrayList<>(Collections.singletonList("Pinfalls"));
            List<String> scores = new ArrayList<>(Collections.singletonList("Score"));
            for(Frame frame: playerGame.getFrames()){
                pinfalls.add(frame.pinfallsToString());
                scores.add(frame.getScore().toString());
            }
            rows.add(pinfalls);
            rows.add(scores);
        }
        Board board = new Board(120);
        Table table = new Table(board, 120, headersList, rows);
        table.setGridMode(Table.GRID_NON);
        table.setColAlignsList(Stream.generate(()-> Block.DATA_CENTER).limit(11).collect(Collectors.toList()));

        String tableString = board.setInitialBlock(table.tableToBlocks()).build().getPreview();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(tableString);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
    }
}
