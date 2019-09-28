import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

public class Game {

    private Integer playersQuantity;
    private Integer roundDurationInSeconds;
    private Integer pointsToWin;
    private List<Player> players;
    private Round actualRound;
    private List<Round> roundsHistory;
    private long roundBeginTime;
    private Integer pollDurationInSeconds;
    private long pollBeginTime;

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

    public void run() {

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
        broadcast("---------PONTUAÇÃO DA RODADA --------\n");
        for (int i = 0; i < punList.size(); i++) {
            Pun pun = punList.get(i);
            broadcast((i + 1) + "º - " + pun.getDescription() + " --- " + pun.getPontuation() + "\n");
        }
    }

    private void startPoll() {
        broadcast("---------------------HORA DA VOTAÇÃO -------------------");
        broadcast("-------VOTE DE ACORDO COM O NÚMERO DO TROCADILHO -------");
        this.getActualRound().getPuns().forEach((uuid, pun) -> {
            broadcast(pun.getNumber() + " ----- " + pun.getDescription());
        });
        broadcast("Digite seu voto: ");
        this.pollBeginTime = System.currentTimeMillis();
    }

    private void waitListenTime(Integer seconds) {
    while ((System.currentTimeMillis() - this.roundBeginTime)< ((seconds + 5) * 1000)) {
            if (this.getActualRound().getPuns().size() == this.playersQuantity)
                break;
        }
    }
    private void waitPollListenTime(Integer seconds) {
        while ((System.currentTimeMillis() - this.pollBeginTime)< ((seconds + 5) * 1000)) {
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
                PrintStream saida = new PrintStream(player.getPlayerSocket().getOutputStream());
                saida.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}