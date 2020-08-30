package bowling.service;

import bowling.exception.BowlingException;
import bowling.objects.Frame;
import bowling.objects.LastFrame;
import bowling.objects.PlayerGame;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BowlingService implements IBowlingService{

    private static final String FAULT = "F";
    private static final Integer MAX_SHOTS = 21;
    private static final Integer MIN_SHOTS = 11;
    private static final Integer STRIKE = 10;
    private static final Integer SPARESUM = 10;
    private static final Integer MAX_PINS_DOWN = 10;

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
            if(value < 0 || value > MAX_PINS_DOWN){
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
        Collection<PlayerGame> playerGames = new ArrayList<>();
        for (Map.Entry<String, List<String>> playerGameE: gamersShots.entrySet()){
            if(playerGameE.getValue().size() < MIN_SHOTS || playerGameE.getValue().size() > MAX_SHOTS){
                throw new BowlingException("GAME_INVALID");
            }
            PlayerGame playerGame = new PlayerGame();
            playerGame.setPlayerName(playerGameE.getKey());
            playerGame.setFrames(buildFrames(playerGameE.getValue()));
            playerGames.add(playerGame);
        }
        System.out.println(playerGames);
    }

    private static Collection<Frame> buildFrames(List<String> playerShots){
        List<Frame> frameList = new ArrayList<>();
        Integer frameIndex = 0;
        for(String shot: playerShots){
            if(frameIndex > 9){
                throw new BowlingException("GAME_INVALID");
            }

            Boolean strike = !shot.equals(FAULT) && Integer.parseInt(shot) == STRIKE;

            if(frameList.size() - 1 < frameIndex){
                Frame frame;
                if(frameIndex == 9){
                    frame = new LastFrame();
                }else{
                    frame = new Frame();
                }

                frame.setIndex(frameIndex);
                frame.setShot1(shot);
                if(strike && frameIndex < 9){
                    frame.setComplete(Boolean.TRUE);
                    frameIndex++;
                }else{
                    frame.setComplete(Boolean.FALSE);
                }
                frameList.add(frame);
                continue;
            }
            Frame currentFrame = frameList.get(frameIndex);
            Integer shot1Value = getShotValuePreventFoul(currentFrame.getShot1());
            Integer shot2Value = 0;
            if(!currentFrame.getComplete() && frameIndex < 9){
                currentFrame.setShot2(shot);
                currentFrame.setComplete(Boolean.TRUE);
                frameIndex++;
                shot2Value = getShotValuePreventFoul(shot);
            }else if(!currentFrame.getComplete() && frameIndex == 9){
                if(StringUtils.isEmpty(currentFrame.getShot2())){
                    currentFrame.setShot2(shot);
                    shot2Value = getShotValuePreventFoul(shot);
                }else{
                    shot2Value = getShotValuePreventFoul(currentFrame.getShot2());
                    Integer shot3Value = getShotValuePreventFoul(shot);
                    if(shot1Value != STRIKE && shot1Value + shot2Value < SPARESUM || shot1Value == STRIKE && shot2Value != STRIKE && shot2Value + shot3Value > SPARESUM){
                        throw new BowlingException("GAME_INVALID");
                    }
                    ((LastFrame)currentFrame).setShot3(shot);
                    currentFrame.setComplete(Boolean.TRUE);
                    frameIndex++;
                }
            }
            if(shot1Value + shot2Value > SPARESUM && frameIndex < 9 || frameIndex == 9 && shot1Value != STRIKE && shot1Value + shot2Value > SPARESUM){
                throw new BowlingException("GAME_INVALID");
            }
        }
        return frameList;
    }


    private static Integer getShotValuePreventFoul(String shot){
        return shot.equals(FAULT)?0:Integer.parseInt(shot);
    }
}
