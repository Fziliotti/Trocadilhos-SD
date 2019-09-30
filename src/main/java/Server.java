import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

public class Server {
    private static Integer PLAYERS_QUANTITY = 2;
    private static Integer ROUND_DURATION_IN_SECONDS = 60;
    private static Integer POINTS_TO_WIN = 120;
    private static Integer POLL_DURATION_IN_SECONDS = 20;

    public static void main(String[] args) {
        Logger log = Logger.getLogger(Server.class.getName());


        try {
            ServerSocket server = new ServerSocket(12345);
            log.info("Servidor iniciado na porta 12345");
            List<Player> playerList = new ArrayList<>();

            while (playerList.size() < PLAYERS_QUANTITY) {
                Socket client = server.accept();
                PrintStream saida = new PrintStream(client.getOutputStream());
                Scanner entrada = new Scanner(client.getInputStream());
                saida.println("Digite seu nickname: ");
                String playerName = entrada.nextLine();
                saida.println("Aguarde a entrada de mais jogadores =)");
                playerList.add(new Player(UUID.randomUUID(), playerName, client));

            }


            Game game = Server.getBackupOrNewGame(playerList);

            game.run();

            for (Player player : playerList) {
                player.getPlayerSocket().close();
            }
            server.close();

        } catch (IOException ex) {
            log.info("Erro de conexão");
        }
    }


    private static Game getBackupOrNewGame(List<Player> playerList) {
        try {
            File arquivo = new File("backup.json");
            FileReader fw = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fw);
            StringBuilder json = new StringBuilder();
            while(br.ready()){
                json.append(br.readLine());
            }
            br.close();
            ObjectMapper mapper = new ObjectMapper();

            if(!String.valueOf(json).equals("")){
                return mapper.readValue(String.valueOf(json), Game.class);
            }
            return new Game(PLAYERS_QUANTITY, ROUND_DURATION_IN_SECONDS, POINTS_TO_WIN, playerList, POLL_DURATION_IN_SECONDS);

        }catch (IOException exception){
            exception.printStackTrace();
            return new Game(PLAYERS_QUANTITY, ROUND_DURATION_IN_SECONDS, POINTS_TO_WIN, playerList, POLL_DURATION_IN_SECONDS);
        }
    }

}