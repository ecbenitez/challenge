package bowling;

import bowling.service.BowlingService;
import bowling.service.IBowlingService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BowlingScore {

    private static IBowlingService bowlingService = new BowlingService();

    public static void main(String[] args) {

        System.out.println("Hello game");
        askForFile();
    }

    private static void askForFile(){
        System.out.println("Enter bowling results file dir:");
        Scanner scanDir = new Scanner(System.in);
        String fileDir = scanDir.next();
        System.out.println(fileDir);
//        File textFile = new File(fileDir);
        File textFile = new File("F:/bowlingR.txt");
        if(!textFile.exists() || !textFile.getName().contains(".txt")){
            System.out.println("Invalid File or Input");
            askForFile();
        }else{
            bowlingService.readFile(textFile);
        }
    }
}
