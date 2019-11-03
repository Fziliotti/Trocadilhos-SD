package trocadilhos.grpc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import trocadilhos.grpc.server.TrocadilhosGameImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

public class Server {
    private static Integer MAX_PLAYERS;
    private static Integer ROUND_DURATION_IN_SECONDS;
    private static Integer POINTS_TO_WIN;
    private static Integer POLL_DURATION_IN_SECONDS;

    public static void main(String[] args) throws IOException {
        Logger log = Logger.getLogger(Server.class.getName());
        getServerValuesFromProperties();

        try {
            ServerSocket server = new ServerSocket(12345);
            log.info("Servidor iniciado na porta 12345");
            List<Player> playerList = new ArrayList<>();

            while (playerList.size() < MAX_PLAYERS) {
                Socket client = server.accept();
                PrintStream out = new PrintStream(client.getOutputStream());
                Scanner in = new Scanner(client.getInputStream());
                out.println("Digite seu nickname: ");
                String playerName = in.nextLine();
                out.println("Aguarde a entrada de mais jogadores =)");
               // playerList.add(new Player(UUID.randomUUID(), playerName, client));

            }

            TrocadilhosGameImpl trocadilhosGameImpl = Server.getBackupOrNewGame(playerList);

//            trocadilhosGameImpl.run();

            for (Player player : playerList) {
             //   player.getPlayerSocket().close();
            }
            server.close();

        } catch (IOException ex) {
            log.info("Erro de conexão");
        }
    }

    private static void getServerValuesFromProperties() throws IOException {
        Properties properties = getProperties();
        MAX_PLAYERS = Integer.parseInt(properties.getProperty("max-players"));
        ROUND_DURATION_IN_SECONDS = Integer.parseInt(properties.getProperty("round-duration-in-seconds"));
        POLL_DURATION_IN_SECONDS = Integer.parseInt(properties.getProperty("poll-duration-in-seconds"));
        POINTS_TO_WIN = Integer.parseInt(properties.getProperty("points-to-win"));
    }

    public static Properties getProperties() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("./src/main/java/trocadilhos/grpc/application.properties");
        props.load(file);
        return props;
    }


    private static TrocadilhosGameImpl getBackupOrNewGame(List<Player> playerList) {
        try {
            File arquivo = new File("backup.json");
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder json = new StringBuilder();
            while(br.ready()){
                json.append(br.readLine());
            }
            br.close();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            if(!String.valueOf(json).equals("")){
                TrocadilhosGameImpl trocadilhosGameImpl = mapper.readValue(String.valueOf(json), TrocadilhosGameImpl.class);
                setGamePlayerList(playerList, trocadilhosGameImpl);
                return trocadilhosGameImpl;
            }
            return new TrocadilhosGameImpl(MAX_PLAYERS, ROUND_DURATION_IN_SECONDS, POINTS_TO_WIN, playerList, POLL_DURATION_IN_SECONDS);

        }catch (IOException exception){
            exception.printStackTrace();
            return new TrocadilhosGameImpl(MAX_PLAYERS, ROUND_DURATION_IN_SECONDS, POINTS_TO_WIN, playerList, POLL_DURATION_IN_SECONDS);
        }
    }

    private static void setGamePlayerList(List<Player> playerList, TrocadilhosGameImpl trocadilhosGameImpl) {
        Map<String, Player> backupPlayersMap = new HashMap<>();
        trocadilhosGameImpl.getPlayers().forEach(player -> backupPlayersMap.put(player.getName().toLowerCase(), player));
        playerList.forEach(player -> {
            Player backupPlayer = backupPlayersMap.get(player.getName().toLowerCase());
            if(backupPlayer != null){
                player.setScore(backupPlayer.getScore());
                player.setPuns(backupPlayer.getPuns());
            }
        });
        trocadilhosGameImpl.setPlayers(playerList);
    }

}