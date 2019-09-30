import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

public class Server {
	private static Integer PLAYERS_QUANTITY = 1;
	private static Integer ROUND_DURATION_IN_SECONDS = 60;
	private static Integer POINTS_TO_WIN = 120;
	private static Integer POLL_DURATION_IN_SECONDS = 20;
	private static int totalPlayersOn = 0;
	private static boolean firstPlayer = true;

	public static void main(String[] args) {
		Logger log = Logger.getLogger(Server.class.getName());

		try {
			ServerSocket server = new ServerSocket(12345);
			log.info("Servidor iniciado na porta " + server.getLocalPort());

			List<Player> playerList = new ArrayList<>();

			while (playerList.size() < PLAYERS_QUANTITY) {
				Socket client = server.accept();

				PrintStream saida = new PrintStream(client.getOutputStream());
				Scanner entrada = new Scanner(client.getInputStream());

				if (firstPlayer) {
					Scanner scanner = new Scanner(client.getInputStream());
					saida.println("Digite o total de jogadores para iniciar o jogo");
					int totalPlayers = scanner.nextInt();
					PLAYERS_QUANTITY = totalPlayers;
					firstPlayer = false;
				}

				saida.println("Digite seu nickname: ");
				String playerName = entrada.nextLine();
				totalPlayersOn = totalPlayersOn + 1;
				saida.println("\nOlá " + playerName + "! "
						+ "Seja bem vindo ao jogo de Trocadilhos. Aguarde a entrada de mais jogadores =)\nTotal: "
						+ totalPlayersOn + " de " + PLAYERS_QUANTITY);
				playerList.add(new Player(UUID.randomUUID(), playerName, client));

			}

			Game game = new Game(PLAYERS_QUANTITY, ROUND_DURATION_IN_SECONDS, POINTS_TO_WIN, playerList,
					POLL_DURATION_IN_SECONDS);

			game.run();

			for (Player player : playerList) {
				player.getPlayerSocket().close();
			}
			server.close();

		} catch (IOException ex) {
			log.info("Erro de conexão" + ex);
		}
	}

}
