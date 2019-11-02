package trocadilhos.grpc.server;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new TrocadilhosGameImpl()).build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("trocadilhos.grpc.Server started!");
        server.awaitTermination();
    }
}