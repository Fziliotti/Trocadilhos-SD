package trocadilhos.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import trocadilhos.grpc.*;

import java.util.Scanner;

public class Client {

    private Boolean loggined = false;

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        TrocadilhosGameGrpc.TrocadilhosGameBlockingStub stub
                = TrocadilhosGameGrpc.newBlockingStub(channel);

        Scanner sc = new Scanner(System.in);
        String nickname = sc.nextLine();
        System.out.println("Digite seu nickname: ");
        LoginResponse loginResponse = stub.login(LoginRequest.newBuilder()
                .setNickname(nickname)
                .build());


        System.out.println(loginResponse);
        channel.shutdown();
    }
}