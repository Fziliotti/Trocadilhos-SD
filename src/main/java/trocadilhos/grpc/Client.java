package trocadilhos.grpc;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {
        Logger log = Logger.getLogger(Client.class.getName());

        try {

            Socket socket = new Socket("localhost", 12345);
            Scanner scanner = new Scanner(System.in);
            PrintStream saida = new PrintStream(socket.getOutputStream());

//            ClientMessageSender serverMessageReceiver =
//                    new ClientMessageSender(socket.getInputStream());
//            new Thread(serverMessageReceiver).start();

            while (scanner.hasNextLine()) {
                saida.println(scanner.nextLine());
            }

        } catch (IOException ex) {
            log.info("Erro de conexão");
        }
    }

}