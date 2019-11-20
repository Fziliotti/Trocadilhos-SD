package trocadilhos.grpc.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import trocadilhos.grpc.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static trocadilhos.grpc.server.GameStatus.*;
import static trocadilhos.grpc.server.ResponseType.*;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TrocadilhosGameImpl extends TrocadilhosGameGrpc.TrocadilhosGameImplBase implements Serializable {

    private int playersQuantity;
    private String ip;
    private int port;
    private Integer roundDurationInSeconds = 60;
    private Integer maxPlayers = 8;
    private Integer pointsToWin = 10;
    private List<Player> players = new ArrayList<>();
    private Round actualRound;
    private List<Round> roundsHistory = new ArrayList<>();
    private long roundBeginTime;
    private Integer pollDurationInSeconds = 20;
    private long pollBeginTime;
    private Integer actualGameMaxPontuation = 0;
    private Integer minPlayersToStartGame = 2;
    private GameStatus gameStatus;
    private int nextServerPort;
    private List<String> playersThatVoted = new ArrayList<>();
    private List<String> playersThatWrote = new ArrayList<>();
    private List<String> themes = Arrays.asList("Animais", "Celebridades", "Cinema", "Comida", "Desenho", "Futebol",
            "Heróis", "Música", "Saude", "Casa", "Mouse", "Notebook", "Camiseta", "Nascer", "Blusa", "Relógio", "Cabeça",
            "Cabelo", "Celular", "Piazza");


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

    private void sendMessageToPlayer(String message, Player player, ResponseType responseType) {
        APIResponse apiResponse = APIResponse.newBuilder()
                .setType(responseType.toString())
                .setMessage(message)
                .build();
        player.getResponseObserver().onNext(apiResponse);
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
        this.players.stream().filter(player -> player.isOnline() && player.getResponseObserver() != null)
                .forEach(player -> {
                    APIResponse apiResponse = APIResponse.newBuilder()
                            .setMessage(message)
                            .setType(messageType.toString())
                            .build();
                    player.getResponseObserver().onNext(apiResponse);
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
        });

    }

    @Override
    public void loginToGame(LoginToGameRequest loginToGameRequest, StreamObserver<APIResponse> responseObserver) {

        if (this.players.size() >= this.maxPlayers) {
            APIResponse apiResponse = APIResponse.newBuilder().setType(FULL_SERVER_ERROR.name()).build();
            responseObserver.onNext(apiResponse);
            responseObserver.onCompleted();
        }
        Player player = new Player(loginToGameRequest.getNickname());
        this.players.add(player);
        playersQuantity++;

        if (this.players.size() >= this.minPlayersToStartGame) {
            if (actualRound == null) {
                String message = showGameStartingMessage(loginToGameRequest, responseObserver);
                broadcast(message, NORMAL_MESSAGE);
                responseObserver.onCompleted();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.run();
            } else {
                String enteringMessage = getEnteringGameMessage(loginToGameRequest);
                String actualRoundMessage = getActualRoundForPlayer();
                APIResponse apiResponse = APIResponse.newBuilder()
                        .setType(NORMAL_MESSAGE.toString())
                        .setMessage(enteringMessage + actualRoundMessage)
                        .build();
                responseObserver.onNext(apiResponse);
                responseObserver.onCompleted();
            }
        } else {
            String message = showWaitingMorePlayersMessage(loginToGameRequest, responseObserver);
            broadcast(message, NORMAL_MESSAGE);
            responseObserver.onCompleted();
        }

    }

    private String showWaitingMorePlayersMessage(LoginToGameRequest loginToGameRequest, StreamObserver<APIResponse> responseObserver) {
        int remainingPlayers = this.minPlayersToStartGame - this.players.size();
        String message = loginToGameRequest.getNickname() + " connected. \n"
                + "Waiting for more " + remainingPlayers + " players to start.";

        APIResponse apiResponse = APIResponse.newBuilder()
                .setMessage(message)
                .setType(NORMAL_MESSAGE.name())
                .build();
        responseObserver.onNext(apiResponse);
        return message;
    }

    private String showGameStartingMessage(LoginToGameRequest loginToGameRequest, StreamObserver<APIResponse> responseObserver) {
        String message = loginToGameRequest.getNickname() + " connected. \n"
                + "Game starting... ";
        APIResponse apiResponse = APIResponse.newBuilder()
                .setMessage(message)
                .setType(NORMAL_MESSAGE.name())
                .build();
        responseObserver.onNext(apiResponse);
        return message;
    }

    private String getEnteringGameMessage(LoginToGameRequest loginToGameRequest) {
        return loginToGameRequest.getNickname() + " connected. \n"
                + "Entering in a game with " + (playersQuantity - 1) + " players";

    }

    private String getActualRoundForPlayer() {
        StringBuilder showThemeMessage = new StringBuilder("\n");
        if (gameStatus == WAITING_PUNS) {
            showThemeMessage.append("Tema: ").append(actualRound.getTheme()).append("\nEscreva seu trocadilho: ");
        } else if (gameStatus == WAITING_POLL) {
            showThemeMessage.append("\n---------------------HORA DA VOTAÇÃO -------------------")
                    .append("\n O TEMA DESSA RODADA FOI: ").append(actualRound.getTheme())
                    .append("\n-------VOTE DE ACORDO COM O NÚMERO DO TROCADILHO -------");
            this.getActualRound().getPuns().forEach((uuid, pun) -> {
                showThemeMessage.append("\n").append(pun.getNumber()).append(" ----- ").append(pun.getDescription());
            });
            showThemeMessage.append("\nDigite seu voto: ");
        }

        return showThemeMessage.toString();
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

                if (value.getType().equals(IDENTIFICATION.toString())) {
                    setPlayerResponseObserver(value, responseObserver);
                } else {
                    switch (gameStatus) {
                        case WAITING_PUNS:
                            receivePun(value);
                            break;
                        case WAITING_POLL:
                            receivePoll(value);
                            break;
                        case INTERVAL_TIME:
                            receiveNormalMessage(value);
                            break;
                        default: {
                            System.out.println("Invalid game status!");
                            break;
                        }
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

    private void setPlayerResponseObserver(APIRequest value, StreamObserver<APIResponse> responseObserver) {
        Player player = getPlayerByNickname(value.getFrom());
        player.setResponseObserver(responseObserver);
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
        if (playersThatVoted.contains(player.getName())) {
            sendMessageToPlayer("Você já votou! Aguarde a próxima rodada!", player, WAIT_NEXT_STEP);
            return;
        }
        Integer intMessage;
        if (value.getMessage().length() < 5 && value.getMessage().matches("[0-9]*")) {
            intMessage = Integer.parseInt(value.getMessage());
        } else intMessage = -1;
        Integer punNumber = getActualRound().getPuns().size() + 1;


        AtomicInteger playerPunNumber = new AtomicInteger();
        actualRound.getPuns().forEach((integer, pun) -> {
            if (pun.getPlayerId() == player.getId()) {
                playerPunNumber.set(pun.getNumber());
            }
        });

        if (intMessage >= punNumber || intMessage <= 0 || intMessage == playerPunNumber.intValue()) {
            sendMessageToPlayer("Número inválido, vote em um dos números acima", player, INVALID_POLL);
        } else {
            Pun pun = getActualRound().getPuns().get(Integer.parseInt(value.getMessage()));
            pun.incrementPontuation();
            actualRound.incrementVotes();
            playersThatVoted.add(player.getName());
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