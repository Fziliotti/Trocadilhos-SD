import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private Integer playersQuantity;
    private Integer roundDurationInSeconds;
    private Integer pointsToWin;
    private List<Player> players;
    private Round actualRound;
    private List<Round> roundsHistory;

    public Game() {
    }

    public Game(Integer playersQuantity, Integer roundDurationInSeconds, Integer pointsToWin, List<Player> players) {
        this.playersQuantity = playersQuantity;
        this.roundDurationInSeconds = roundDurationInSeconds;
        this.pointsToWin = pointsToWin;
        this.players = players;
        this.actualRound = null;
        this.roundsHistory = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Integer getPlayersQuantity() {
        return playersQuantity;
    }

    public void setPlayersQuantity(Integer playersQuantity) {
        this.playersQuantity = playersQuantity;
    }

    public Integer getRoundDurationInSeconds() {
        return roundDurationInSeconds;
    }

    public void setRoundDurationInSeconds(Integer roundDurationInSeconds) {
        this.roundDurationInSeconds = roundDurationInSeconds;
    }

    public Integer getPointsToWin() {
        return pointsToWin;
    }

    public void setPointsToWin(Integer pointsToWin) {
        this.pointsToWin = pointsToWin;
    }

    public Round getActualRound() {
        return actualRound;
    }

    public void setActualRound(Round actualRound) {
        this.actualRound = actualRound;
    }

    public List<Round> getRoundsHistory() {
        return roundsHistory;
    }

    public void setRoundsHistory(List<Round> roundsHistory) {
        this.roundsHistory = roundsHistory;
    }

    public void run() {

        startRound();
        showScoreboard();


    }



    private void showScoreboard() {
        System.out.println("SCOREBOARD - ROUND " + this.actualRound.getNumber());

        players.stream().sorted(Player::compareTo).forEach(player -> {
            System.out.println(player.getName() + " ---- " + player.getScore());
        });

        Integer timeLeft = 5;
        while(timeLeft > 0){
            System.out.println("Round starting in " + timeLeft + " seconds...");
            try {
                Thread.sleep(1000* timeLeft);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeLeft--;
        }
    }



    private void startRound() {
        if( this.actualRound == null ){
            this.actualRound = new Round();
            actualRound.setNumber(1);
        }else{
            this.roundsHistory.add(this.actualRound);

            Integer newNumber = this.actualRound.getNumber() + 1;
            this.actualRound = new Round();
            this.actualRound.setNumber(newNumber);
        }
    }

}