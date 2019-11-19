package trocadilhos.grpc.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        GameServers gameServers = getGameServers();

        Optional<GameServers.ServerStatus> firstOffline = gameServers.getServerStatusList().stream().filter(gameServer -> !gameServer.getOnline()).findFirst();

        if (!firstOffline.isPresent()) {
            throw new RuntimeException("All servers are connected!");
        }

//        Server server = ServerBuilder.forPort(firstOffline.get().getPort())
//                .addService(new TrocadilhosGameImpl()).build();

        Server server = ServerBuilder.forPort(8080)
                .addService(new TrocadilhosGameImpl()).build();

        System.out.println("Starting server...");
        server.start();

        gameServers = getGameServers();
        gameServers.getServerStatusList().stream().filter(gameServer -> gameServer.getPort() == firstOffline.get().getPort()).findFirst().ifPresent(th -> th.setOnline(true));

        writeOnGameServers(gameServers);

        System.out.println("Server started!");
        server.awaitTermination();
    }

    private static void writeOnGameServers(GameServers gameServers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.writeValue(new File("servers.json"), gameServers);
    }


    private static GameServers getGameServers() throws IOException {
        File file = new File("servers.json");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder json = new StringBuilder();
        while (br.ready()) {
            json.append(br.readLine());
        }
        br.close();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GameServers gameServers = new GameServers();
        if (!String.valueOf(json).equals("")) {
            gameServers = mapper.readValue(String.valueOf(json), GameServers.class);
        }
        return gameServers;
    }
}