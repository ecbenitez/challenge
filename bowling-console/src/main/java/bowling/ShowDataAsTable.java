package bowling;

import bowling.objects.Frame;
import bowling.objects.PlayerGame;
import com.dak.wagu.Block;
import com.dak.wagu.Board;
import com.dak.wagu.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowDataAsTable {

    /**
     * Displays in console the game results.
     * @param playerGames game info per player.
     */
    public static void printStringsAsTable(List<PlayerGame> playerGames){
        List<String> headersList = buildHeaders();
        List<List<String>> rows = buildRows(playerGames, Boolean.TRUE);

        System.out.println(String.join("    ", headersList));
        for(List<String> row: rows){
            String delimiter = "";
            System.out.println(String.join(delimiter, row));
        }
    }

    /**
     * Displays in console the game results in table format.
     * @param playerGames game info per player.
     */
    public static void printTable(List<PlayerGame> playerGames){

        List<String> headersList = buildHeaders();
        List<List<String>> rows = buildRows(playerGames, Boolean.FALSE);

        Board board = new Board(120);
        Table table = new Table(board, 120, headersList, rows);
        table.setGridMode(Table.GRID_NON);
        table.setColAlignsList(Stream.generate(()-> Block.DATA_CENTER).limit(11).collect(Collectors.toList()));

        String tableString = board.setInitialBlock(table.tableToBlocks()).build().getPreview();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(tableString);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Build headers list.
     * @return headers list.
     */
    private static List<String> buildHeaders(){
        return Arrays.asList("FRAME", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    }

    /**
     * Aux method to build table's rows.
     * @param playerGames game info per player.
     * @return List of table's rows.
     */
    private static List<List<String>> buildRows(List<PlayerGame> playerGames, Boolean pureStrings){
        List<List<String>> rows = new ArrayList<>();
        for (PlayerGame playerGame: playerGames){
            List<String> nameRow;
            if(pureStrings){
                nameRow = Collections.singletonList(playerGame.getPlayerName());
            }else{
                nameRow = Stream.generate(String::new).limit(11).collect(Collectors.toList());
                nameRow.set(0, playerGame.getPlayerName());
            }
            rows.add(nameRow);

            List<String> pinfalls = new ArrayList<>(Collections.singletonList(StringUtils.rightPad("Pinfalls", 9)));
            List<String> scores = new ArrayList<>(Collections.singletonList(StringUtils.rightPad("Score", 9)));
            for(Frame frame: playerGame.getFrames()){
                pinfalls.add(StringUtils.rightPad(frame.pinfallsToString(pureStrings), 5));
                scores.add(StringUtils.rightPad(frame.getScore().toString(), 5));
            }
            rows.add(pinfalls);
            rows.add(scores);
        }
        return rows;
    }
}
