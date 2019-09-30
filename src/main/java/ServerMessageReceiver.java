
import java.io.InputStream;
import java.util.Scanner;

class ServerMessageReceiver implements Runnable {

    private InputStream server;

    public ServerMessageReceiver(InputStream server) {
        this.server = server;
    }

    public void run() {
        try(Scanner s = new Scanner(this.server)){
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
        }
    }
}