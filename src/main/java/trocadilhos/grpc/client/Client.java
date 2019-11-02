package trocadilhos.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import trocadilhos.grpc.APIResponse;
import trocadilhos.grpc.TrocadilhoRequest;
import trocadilhos.grpc.TrocadilhosGameGrpc;

public class Client {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        TrocadilhosGameGrpc.TrocadilhosGameBlockingStub stub
                = TrocadilhosGameGrpc.newBlockingStub(channel);

        APIResponse apiResponse = stub.sendTrocadilho(TrocadilhoRequest.newBuilder()
                .setDescription("Opa opa chico chico")
                .build());

        System.out.println(apiResponse);
        channel.shutdown();
    }
}