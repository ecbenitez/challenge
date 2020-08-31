package bowling.service;

import bowling.exception.BowlingException;
import bowling.objects.Frame;
import bowling.objects.LastFrame;
import bowling.objects.PlayerGame;
import bowling.util.ScoreValueUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * BowlingService class.
 */
public class BowlingService implements IBowlingService{

    private static final String FOUL = "F";
    private static final Integer MAX_SHOTS = 21;
    private static final Integer MIN_SHOTS = 11;
    private static final Integer STRIKE = 10;
    private static final Integer SPARESUM = 10;
    private static final Integer MAX_PINS_DOWN = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<String>> readFile(File textFile) {
        Scanner sc;
        Map<String, List<String>> gamersShots = new HashMap<>();
        try {
            sc = new Scanner(textFile);
            while (sc.hasNextLine()){
                Map.Entry<String, String> gamerShot = parseLineContent(sc.nextLine());
                if(gamersShots.containsKey(gamerShot.getKey())){
                    gamersShots.get(gamerShot.getKey()).add(gamerShot.getValue());
                }else{
                    gamersShots.put(gamerShot.getKey(), new ArrayList<>(Collections.singletonList(gamerShot.getValue())));
                }
            }
            sc.close();
        } catch (FileNotFoundException | BowlingException e) {
            e.printStackTrace();
        }
        return gamersShots;
    }

    /**
     * Parse the file lines to get players shots.
     * @param line file line
     * @return map with all the players shots.
     */
    private static Map.Entry<String, String> parseLineContent(String line) {
        StringTokenizer st = new StringTokenizer(line);
        if(st.countTokens() != 2){
            throw new BowlingException("INCORRECT_FORMAT");
        }
        String playerName = st.nextToken();
        String playerScore = st.nextToken();
        if(playerScore.equals(FOUL)){
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerGame> processGame(Map<String, List<String>> gamersShots){
        List<PlayerGame> playerGames = new ArrayList<>();
        for (Map.Entry<String, List<String>> playerGameE: gamersShots.entrySet()){
            if(playerGameE.getValue().size() < MIN_SHOTS || playerGameE.getValue().size() > MAX_SHOTS){
                throw new BowlingException("GAME_INVALID");
            }
            PlayerGame playerGame = new PlayerGame();
            playerGame.setPlayerName(playerGameE.getKey());
            playerGame.setFrames(buildFrames(playerGameE.getValue()));
            playerGames.add(playerGame);
        }
        calcGameScore(playerGames);
        return playerGames;
    }

    /**
     * Build the player's game frames.
     * @param playerShots player shots.
     * @return list of player Frames.
     */
    private static List<Frame> buildFrames(List<String> playerShots){
        List<Frame> frameList = new ArrayList<>();
        Integer frameIndex = 0;
        for(String shot: playerShots){
            if(frameIndex > 9){
                throw new BowlingException("GAME_INVALID");
            }

            Boolean strike = !shot.equals(FOUL) && Integer.parseInt(shot) == STRIKE;
            //create frame
            if(frameList.size() - 1 < frameIndex){
                Frame frame;
                if(frameIndex.equals(9)){
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
            //update frame
            Frame currentFrame = frameList.get(frameIndex);
            Integer shot1Value = ScoreValueUtil.getShotValuePreventFoul(currentFrame.getShot1());
            Integer shot2Value = 0;
            if(!currentFrame.getComplete() && frameIndex < 9){
                currentFrame.setShot2(shot);
                currentFrame.setComplete(Boolean.TRUE);
                frameIndex++;
                shot2Value = ScoreValueUtil.getShotValuePreventFoul(shot);
            }else if(!currentFrame.getComplete() && frameIndex == 9){
                if(StringUtils.isEmpty(currentFrame.getShot2())){
                    currentFrame.setShot2(shot);
                    shot2Value = ScoreValueUtil.getShotValuePreventFoul(shot);
                }else{
                    shot2Value = ScoreValueUtil.getShotValuePreventFoul(currentFrame.getShot2());
                    Integer shot3Value = ScoreValueUtil.getShotValuePreventFoul(shot);
                    if(!shot1Value.equals(STRIKE) && shot1Value + shot2Value < SPARESUM || shot1Value.equals(STRIKE) && !shot2Value.equals(STRIKE) && shot2Value + shot3Value > SPARESUM){
                        throw new BowlingException("GAME_INVALID");
                    }
                    ((LastFrame)currentFrame).setShot3(shot);
                    currentFrame.setComplete(Boolean.TRUE);
                    frameIndex++;
                }
            }
            if(shot1Value + shot2Value > SPARESUM && frameIndex < 9 || frameIndex.equals(9) && !shot1Value.equals(STRIKE) && shot1Value + shot2Value > SPARESUM){
                throw new BowlingException("GAME_INVALID");
            }
        }
        return frameList;
    }

    /**
     * Calc all players game score.
     * @param playerGames players game
     */
    private void calcGameScore(List<PlayerGame> playerGames){
        for(PlayerGame playerGame: playerGames){
            playerGame.calcFramesScore();
        }
    }
}
