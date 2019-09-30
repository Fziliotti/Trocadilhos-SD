import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Game implements Serializable{

    private Integer playersQuantity;
    private Integer roundDurationInSeconds;
    private Integer pointsToWin;
    private List<Player> players;
    private Round actualRound;
    private List<Round> roundsHistory;
    private long roundBeginTime;
    private Integer pollDurationInSeconds;
    private long pollBeginTime;
    private Integer actualGameMaxPontuation;

    public Game() {
    }

    public Game(Integer playersQuantity, Integer roundDurationInSeconds, Integer pointsToWin, List<Player> players, Integer pollDurationInSeconds) {
        this.playersQuantity = playersQuantity;
        this.roundDurationInSeconds = roundDurationInSeconds;
        this.pointsToWin = pointsToWin;
        this.players = players;
        this.actualRound = null;
        this.roundsHistory = new ArrayList<>();
        this.pollDurationInSeconds = pollDurationInSeconds;
        this.actualGameMaxPontuation = 0;
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

    public long getRoundBeginTime() {
        return roundBeginTime;
    }

    public void setRoundBeginTime(long roundBeginTime) {
        this.roundBeginTime = roundBeginTime;
    }

    public Integer getPollDurationInSeconds() {
        return pollDurationInSeconds;
    }

    public void setPollDurationInSeconds(Integer pollDurationInSeconds) {
        this.pollDurationInSeconds = pollDurationInSeconds;
    }

    public long getPollBeginTime() {
        return pollBeginTime;
    }

    public void setPollBeginTime(long pollBeginTime) {
        this.pollBeginTime = pollBeginTime;
    }

    public Integer getActualGameMaxPontuation() {
        return actualGameMaxPontuation;
    }

    public void setActualGameMaxPontuation(Integer actualGameMaxPontuation) {
        this.actualGameMaxPontuation = actualGameMaxPontuation;
    }

    public void run() {

        while (this.actualGameMaxPontuation < this.pointsToWin) {
            startRound();
            showScoreboard();
            showTheme();
            listenPlayersPuns();
            waitListenTime(this.roundDurationInSeconds);
            startPoll();
            listenPlayersVotes();
            waitPollListenTime(this.pollDurationInSeconds);
            showRoundScoreboard();
            updatePlayerScore();
            saveGameBackup();
        }

    }



    private void saveGameBackup() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.writeValue(new File("backup.json"), this);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private void updatePlayerScore() {
        this.getActualRound().getPuns().forEach((integer, pun) -> {
            this.players.stream().filter(player -> player.getId() == pun.getPlayerId())
                    .findFirst()
                    .ifPresent(player -> {
                        player.incrementScore(pun.getPontuation());
                    });
        });
    }

    private void showRoundScoreboard() {
        List<Pun> punList = new ArrayList<>(this.getActualRound().getPuns().values());
        punList.sort(Pun::compareTo);
        setMaxPontuation(punList);
        broadcast("---------PONTUAÇÃO DA RODADA --------\n");
        for (int i = 0; i < punList.size(); i++) {
            Pun pun = punList.get(i);
            broadcast((i + 1) + "º - " + pun.getDescription() + " --- " + pun.getPontuation() + "\n");
        }
    }

    private void setMaxPontuation(List<Pun> punList) {
        Integer scoreboardMaxPontuation = 0;
        if (punList.get(0) != null) scoreboardMaxPontuation = punList.get(0).getPontuation();
        if (scoreboardMaxPontuation > this.actualGameMaxPontuation) {
            this.setActualGameMaxPontuation(scoreboardMaxPontuation);
        }
    }

    private void startPoll() {
        broadcast("---------------------HORA DA VOTAÇÃO -------------------");
        broadcast("-------VOTE DE ACORDO COM O NÚMERO DO TROCADILHO -------");
        this.getActualRound().getPuns().forEach((uuid, pun) -> {
            String message = pun.getNumber() + " ----- " + pun.getDescription();
            broadcastPoll(message, pun.getPlayerId());
        });
        broadcast("Digite seu voto: ");
        this.pollBeginTime = System.currentTimeMillis();
    }

    private void waitListenTime(Integer seconds) {
        while ((System.currentTimeMillis() - this.roundBeginTime) < ((seconds + 5) * 1000)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.getActualRound().getPuns().size() == this.playersQuantity)
                break;
        }
    }

    private void waitPollListenTime(Integer seconds) {
        while ((System.currentTimeMillis() - this.pollBeginTime) < ((seconds + 5) * 1000)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.getActualRound().getNumberOfVotes() == this.playersQuantity)
                break;
        }
    }


    private void showTheme() {
        actualRound.setTheme("Exemplo");
        broadcast("Tema: " + actualRound.getTheme());
        broadcast("Escreva seu trocadilho: ");
        this.roundBeginTime = System.currentTimeMillis();
    }

    private void listenPlayersPuns() {
        this.getPlayers().forEach(player -> {
            final CompletableFuture<String> c1 = new CompletableFuture<>();
            createListenThreads(player, c1);

            c1.exceptionally(ex -> {
                System.out.println("Erro = " + ex.getMessage());
                return "Erro";
            }).thenAccept(str -> {
                int totalPuns = this.getActualRound().getPuns().size();
                Pun pun = new Pun(str, totalPuns + 1, player.getId());
                this.getActualRound().getPuns().put(totalPuns + 1, pun);
                player.getPuns().add(pun);
            });
        });
    }

    private void listenPlayersVotes() {
        this.getPlayers().forEach(player -> {
            final CompletableFuture<String> c1 = new CompletableFuture<>();
            createListenThreads(player, c1);

            c1.exceptionally(ex -> {
                System.out.println("Erro = " + ex.getMessage());
                return "Erro";
            }).thenAccept(str -> {
                Pun pun = this.getActualRound().getPuns().get(Integer.parseInt(str));
                pun.incrementPontuation();
                this.actualRound.incrementVotes();
            });

        });
    }

    private void createListenThreads(Player player, CompletableFuture<String> c1) {
        new Thread(() -> {
            try {
                Scanner entrada = new Scanner(player.getPlayerSocket().getInputStream());
                if (entrada.hasNextLine()) {
                    String message = entrada.nextLine();
                    c1.complete(message);
                }
            } catch (Exception ex) {
                c1.completeExceptionally(ex);
            }
        }).start();
    }


    private void showScoreboard() {
        broadcast("PLACAR - RODADA " + this.actualRound.getNumber());

        players.stream().sorted(Player::compareTo).forEach(player -> {
            broadcast(player.getName() + " ---- " + player.getScore());
        });

        Integer timeLeft = 5;
        while (timeLeft > 0) {
            broadcast("Rodada iniciando em " + timeLeft + " segundos...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeLeft--;
        }
        broadcast("*******************************************************");
    }


    private void startRound() {
        if (this.actualRound == null) {
            this.actualRound = new Round();
            actualRound.setNumber(1);
        } else {
            this.roundsHistory.add(this.actualRound);

            Integer newNumber = this.actualRound.getNumber() + 1;
            this.actualRound = new Round();
            this.actualRound.setNumber(newNumber);
        }
    }

    private void broadcast(String message) {
        this.players.forEach(player -> {
            try {
                PrintStream output = new PrintStream(player.getPlayerSocket().getOutputStream());
                output.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void broadcastPoll(String message, UUID playerUUID) {

        List<Player> otherPlayers = this.players.stream().filter(player -> player.getId() != playerUUID).collect(Collectors.toList());
        otherPlayers.forEach(player -> {
            try {
                PrintStream output = new PrintStream(player.getPlayerSocket().getOutputStream());
                output.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public Game(Integer playersQuantity, Integer roundDurationInSeconds, Integer pointsToWin, List<Player> players, Round actualRound, List<Round> roundsHistory, long roundBeginTime, Integer pollDurationInSeconds, long pollBeginTime, Integer actualGameMaxPontuation) {
        this.playersQuantity = playersQuantity;
        this.roundDurationInSeconds = roundDurationInSeconds;
        this.pointsToWin = pointsToWin;
        this.players = players;
        this.actualRound = actualRound;
        this.roundsHistory = roundsHistory;
        this.roundBeginTime = roundBeginTime;
        this.pollDurationInSeconds = pollDurationInSeconds;
        this.pollBeginTime = pollBeginTime;
        this.actualGameMaxPontuation = 0;
    }
}