package bowling;

import bowling.objects.PlayerGame;
import bowling.service.BowlingService;
import bowling.service.IBowlingService;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BowlingScore {

    private static IBowlingService bowlingService = new BowlingService();

    public static void main(String[] args) {

        System.out.println("Hello to java challenge");
        bowlingScore();
    }

    /**
     * Start the bowling score program.
     */
    private static void bowlingScore(){
        System.out.println("(type exit to finish the program)");
        System.out.println("Enter bowling results file dir:");
        Scanner scanDir = new Scanner(System.in);
        String fileDir = scanDir.next();
        if(fileDir.toLowerCase().equals("exit")){
            System.exit(0);
        }
//        File textFile = new File(fileDir);
        File textFile = new File("F:/bowlingR.txt");
        if(!textFile.exists() || !textFile.getName().contains(".txt")){
            System.out.println("Invalid File or Input");
            bowlingScore();
        }else{
            Map<String, List<String>> gamersShots = bowlingService.readFile(textFile);
            List<PlayerGame> playerGames = bowlingService.processGame(gamersShots);
            ShowDataAsTable.printTable(playerGames);
            bowlingScore();
        }
    }
}
