package trocadilhos.grpc.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.grpc.stub.StreamObserver;
import trocadilhos.grpc.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static trocadilhos.grpc.server.GameStatus.*;
import static trocadilhos.grpc.server.ResponseType.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TrocadilhosGameImpl extends TrocadilhosGameGrpc.TrocadilhosGameImplBase implements Serializable {

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
    private GameStatus gameStatus;
    private List<String> playersThatVoted;
    private List<String> playersThatWrote;
    private List<String> themes = Arrays.asList("Animais", "Celebridades", "Cinema", "Comida", "Desenho", "Futebol",
            "Heróis", "Música", "Saude", "Casa", "Mouse", "Notebook", "Camiseta", "Nascer", "Blusa", "Relógio", "Cabeça",
            "Cabelo", "Celular", "Piazza");

    public TrocadilhosGameImpl() {
    }

    public TrocadilhosGameImpl(Integer playersQuantity, Integer roundDurationInSeconds, Integer pointsToWin, List<Player> players, Integer pollDurationInSeconds) {
        this.playersQuantity = playersQuantity;
        this.roundDurationInSeconds = roundDurationInSeconds;
        this.pointsToWin = pointsToWin;
        this.players = players;
        this.actualRound = null;
        this.roundsHistory = new ArrayList<>();
        this.pollDurationInSeconds = pollDurationInSeconds;
        this.actualGameMaxPontuation = 0;
        this.gameStatus = INTERVAL_TIME;
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
            waitListenTime(this.roundDurationInSeconds);
            startPoll();
            waitPollListenTime(this.pollDurationInSeconds);
            showRoundScoreboard();
            updatePlayerScore();
            saveGameBackup();
        }

    }


    private void saveGameBackup() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.writeValue(new File("backup.json"), this);

        } catch (IOException exception) {
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
        this.gameStatus = INTERVAL_TIME;
        List<Pun> punList = new ArrayList<>(this.getActualRound().getPuns().values());
        punList.sort(Pun::compareTo);
        setMaxPontuation(punList);
        broadcast("---------PONTUAÇÃO DA RODADA --------\n", NORMAL_MESSAGE);
        for (int i = 0; i < punList.size(); i++) {
            Pun pun = punList.get(i);
            broadcast((i + 1) + "º - " + pun.getDescription() + " --- " + pun.getPontuation() + "\n", NORMAL_MESSAGE);
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
        broadcast("---------------------HORA DA VOTAÇÃO -------------------", NORMAL_MESSAGE);
        broadcast("-------VOTE DE ACORDO COM O NÚMERO DO TROCADILHO -------", NORMAL_MESSAGE);
        this.getActualRound().getPuns().forEach((uuid, pun) -> {
            String message = pun.getNumber() + " ----- " + pun.getDescription();
            broadcastPoll(message, pun.getPlayerId());
        });
        broadcast("Digite seu voto: ", POLL_TIME);
        this.pollBeginTime = System.currentTimeMillis();
    }

    private void waitListenTime(Integer seconds) {
        this.gameStatus = WAITING_PUNS;
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
        this.gameStatus = WAITING_POLL;
        while ((System.currentTimeMillis() - this.pollBeginTime) < ((seconds + 5) * 1000)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.getActualRound().getNumberOfVotes().equals(this.playersQuantity))
                break;
        }
    }


    private void showTheme() {
        actualRound.setTheme(getRandomTheme(getThemes()));
        broadcast("Tema: " + actualRound.getTheme(), NORMAL_MESSAGE);
        broadcast("Escreva seu trocadilho: ", NORMAL_MESSAGE);
        this.roundBeginTime = System.currentTimeMillis();
    }

    static String getRandomTheme(List themes) {
        Random random = new Random();
        List<String> givenThemeList = themes;
        String randomTheme = "";

        for (int i = 0; i < themes.size(); i++) {
            int randomIndex = random.nextInt(givenThemeList.size());
            randomTheme = givenThemeList.get(randomIndex);
        }

        return randomTheme;
    }

    public List<String> getThemes() {
        return themes;
    }

//    private void listenPlayersPuns() {
//        this.getPlayers().forEach(player -> {
//            final CompletableFuture<String> c1 = new CompletableFuture<>();
//            createListenPunThreads(player, c1);
//
//            c1.exceptionally(ex -> {
//                System.out.println("Erro = " + ex.getMessage());
//                return "Erro";
//            }).thenAccept(str -> {
//                int totalPuns = this.getActualRound().getPuns().size();
//                Pun pun = new Pun(str, totalPuns + 1, player.getId());
//                this.getActualRound().getPuns().put(totalPuns + 1, pun);
//                player.getPuns().add(pun);
//            });
//        });
//    }

//    private void listenPlayersVotes() {
//        this.getPlayers().forEach(player -> {
//            final CompletableFuture<String> c1 = new CompletableFuture<>();
//            createListenPollThreads(player, c1);
//
//            c1.exceptionally(ex -> {
//                System.out.println("Erro = " + ex.getMessage());
//                return "Erro";
//            }).thenAccept(str -> {
//                Pun pun = this.getActualRound().getPuns().get(Integer.parseInt(str));
//                pun.incrementPontuation();
//                this.actualRound.incrementVotes();
//            });
//
//        });
//    }

//    private void createListenPunThreads(Player player, CompletableFuture<String> c1) {
//        new Thread(() -> {
//            try {
//                Scanner entrada = new Scanner(player.getPlayerSocket().getInputStream());
//                player.getResponseObserver().onCompleted();
//                while (entrada.hasNextLine()) {
//                    String message = entrada.nextLine();
//                    if (message.length() < 140 && !message.isEmpty()) {
//                        c1.complete(message);
//                        break;
//                    } else {
//                        sendMessageToPlayer("O trocadilho deve ter até 140 caracteres", player);
//                    }
//                }
//            } catch (Exception ex) {
//                c1.completeExceptionally(ex);
//            }
//        }).start();
//    }
//    private void createListenPollThreads(Player player, CompletableFuture<String> c1) {
//        new Thread(() -> {
//            try {
//                Scanner entrada = new Scanner(player.getPlayerSocket().getInputStream());
//                while (entrada.hasNextLine()) {
//                    String message = entrada.nextLine();
//                    Integer intMessage = Integer.parseInt(entrada.nextLine());
//                    Integer punNumber = this.getActualRound().getPuns().size() + 1;
//                    if(intMessage < punNumber && intMessage > 0  ) {
//                        c1.complete(message);
//                        break;
//                    }
//                    else{
//                        sendMessageToPlayer("Número inválido, vote em um dos números acima", player);
//                    }
//                }
//            } catch (Exception ex) {
//                c1.completeExceptionally(ex);
//            }
//        }).start();
//    }

    private void sendMessageToPlayer(String message, Player player, ResponseType responseType) {
        APIResponse apiResponse = APIResponse.newBuilder()
                .setType(responseType.toString())
                .setMessage(message)
                .build();
        player.getResponseObserver().onNext(apiResponse);
        player.getResponseObserver().onCompleted();
    }


    private void showScoreboard() {
        broadcast("PLACAR - RODADA " + this.actualRound.getNumber(), NORMAL_MESSAGE);

        players.stream().sorted(Player::compareTo).forEach(player -> {
            broadcast(player.getName() + " ---- " + player.getScore(), NORMAL_MESSAGE);
        });

        Integer timeLeft = 5;
        while (timeLeft > 0) {
            broadcast("Rodada iniciando em " + timeLeft + " segundos...", NORMAL_MESSAGE);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeLeft--;
        }
        broadcast("*******************************************************", NORMAL_MESSAGE);
    }


    private void startRound() {
        this.gameStatus = INTERVAL_TIME;
        this.playersThatVoted = new ArrayList<>();
        this.playersThatWrote = new ArrayList<>();
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

    private void broadcast(String message, ResponseType messageType) {
        this.players.stream().filter(Player::isOnline).forEach(player -> {
            APIResponse apiResponse = APIResponse.newBuilder()
                    .setMessage(message)
                    .setType(messageType.toString())
                    .build();
            player.getResponseObserver().onNext(apiResponse);
            player.getResponseObserver().onCompleted();
        });
    }

    private Player getPlayerByNickname(String nickname) {
        return this.players.stream().filter(player -> player.getName().equals(nickname)).collect(Collectors.toList()).get(0);
    }

    private void broadcastPoll(String message, UUID playerUUID) {

        List<Player> otherPlayers = this.players.stream().
                filter(player -> player.getId() != playerUUID && player.isOnline())
                .collect(Collectors.toList());

        otherPlayers.forEach(player -> {
            APIResponse apiResponse = APIResponse.newBuilder()
                    .setMessage(message)
                    .setType(NORMAL_MESSAGE.toString())
                    .build();
            player.getResponseObserver().onNext(apiResponse);
            player.getResponseObserver().onCompleted();
        });

    }

    public TrocadilhosGameImpl(Integer playersQuantity, Integer roundDurationInSeconds, Integer pointsToWin, List<Player> players, Round actualRound, List<Round> roundsHistory, long roundBeginTime, Integer pollDurationInSeconds, long pollBeginTime, Integer actualGameMaxPontuation) {
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

    @Override
    public void login(LoginToGameRequest loginToGameRequest) {


    }

    @Override
    public void sendTrocadilho(TrocadilhoRequest request, StreamObserver<APIResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request.getDescription());

        APIResponse response = APIResponse.newBuilder()
                .setMessage("Eitcha lele")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void loginToMaster(LoginToMasterRequest request, StreamObserver<LoginToMasterResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request.getNickname());


        Player player = new Player();
        player.setName(request.getNickname());
        LoginToMasterResponse response = LoginToMasterResponse.newBuilder()
                .setIp("localhost")
                .setPort("8081")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void logout(LogoutRequest request, StreamObserver<APIResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request.getNickname());

        APIResponse response = APIResponse.newBuilder()
                .setMessage("Eitcha lele")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<APIRequest> start(StreamObserver<APIResponse> responseObserver) {

        return new StreamObserver<APIRequest>() {
            @Override
            public void onNext(APIRequest value) {

                switch (gameStatus) {
                    case WAITING_PUNS: {
                        receivePun(value);
                    }
                    case WAITING_POLL: {
                        receivePoll(value);
                    }
                    case INTERVAL_TIME: {
                        receiveNormalMessage(value);
                    }
                    default: {
                        System.out.println("Invalid game status!");;
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("got error from player");
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }

        };

    }

    private void receiveNormalMessage(APIRequest value) {
        Player player = getPlayerByNickname(value.getFrom());
        if (value.getMessage().equalsIgnoreCase("!logout")) {
            player.setOnline(false);
            //TODO Chamar aqui método assíncrono que vai retirar o player da lista após um determinado tempo
        } else {
            sendMessageToPlayer("Aguarde a próxima etapa do jogo ... Para sair, digite !logout", player, NORMAL_MESSAGE);
        }
    }

    private void receivePoll(APIRequest value) {
        Player player = getPlayerByNickname(value.getFrom());
        Integer intMessage = Integer.parseInt(value.getMessage());
        Integer punNumber = getActualRound().getPuns().size() + 1;

        if (playersThatVoted.contains(player.getName())) {
            sendMessageToPlayer("Você já votou! Aguarde a próxima rodada!", player, WAIT_NEXT_STEP);
        } else if (intMessage >= punNumber || intMessage <= 0) {
            sendMessageToPlayer("Número inválido, vote em um dos números acima", player, INVALID_POLL);
        } else {
            Pun pun = getActualRound().getPuns().get(Integer.parseInt(value.getMessage()));
            pun.incrementPontuation();
            actualRound.incrementVotes();
        }
    }

    private void receivePun(APIRequest value) {
        Player player = getPlayerByNickname(value.getFrom());

        if (playersThatWrote.contains(player.getName())) {
            sendMessageToPlayer("Você já escreveu seu trocadilho. Aguarde a votação!", player, WAIT_NEXT_STEP);
        } else if (value.getMessage().length() > 140 || value.getMessage().isEmpty()) {
            sendMessageToPlayer("O trocadilho deve ter 1 até 140 caracteres", player, INVALID_PUN);
        } else {
            int totalPuns = getActualRound().getPuns().size();
            Pun pun = new Pun(value.getMessage(), totalPuns + 1, player.getId());
            getActualRound().getPuns().put(totalPuns + 1, pun);
            player.getPuns().add(pun);
            playersThatWrote.add(player.getName());
        }
    }
}