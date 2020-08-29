package bowling.service;

import bowling.exception.BowlingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BowlingService implements IBowlingService{

    private static final String FAULT = "F";
    private static final Integer MAX_SHOTS = 21;

    @Override
    public void readFile(File textFile) {
        Scanner sc;
        Map<String, List<String>> gamersShots = new HashMap<>();
        try {
            sc = new Scanner(textFile);
            while (sc.hasNextLine()){
                Map.Entry<String, String> gamerShot = parseLineContent(sc.nextLine());
                if(gamersShots.containsKey(gamerShot.getKey())){
                    gamersShots.get(gamerShot.getKey()).add(gamerShot.getValue());
                }else{
                    gamersShots.put(gamerShot.getKey(), new ArrayList<>(Arrays.asList(gamerShot.getValue())));
                }
            }
            sc.close();
            System.out.println(gamersShots.entrySet());
            processGame(gamersShots);
        } catch (FileNotFoundException | BowlingException e) {
            e.printStackTrace();
        }
    }

    private static Map.Entry<String, String> parseLineContent(String line) {
        StringTokenizer st = new StringTokenizer(line);
        if(st.countTokens() != 2){
            throw new BowlingException("INCORRECT_FORMAT");
        }
        String playerName = st.nextToken();
        String playerScore = st.nextToken();
        if(playerScore.equals(FAULT)){
            return new AbstractMap.SimpleEntry<>(playerName, playerScore);
        }

        Map.Entry<String, String> gamerShot;
        try {
            Integer value = Integer.parseInt(playerScore, 10);
            if(value < 0 || value > 10){
                throw new BowlingException("INVALID_SCORE");
            }else{
                gamerShot = new AbstractMap.SimpleEntry<>(playerName, playerScore);
            }
        }catch (NumberFormatException e){
            throw new BowlingException("INVALID_SCORE", e);
        }

        return gamerShot;
    }

    private static void processGame(Map<String, List<String>> gamersShots){
        for (Map.Entry<String, List<String>> playerGame: gamersShots.entrySet()){
            if(playerGame.getValue().size() > MAX_SHOTS){
                throw new BowlingException("GAME_INVALID");
            }

        }
    }
}
