package trocadilhos.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import trocadilhos.grpc.*;
import trocadilhos.grpc.server.ResponseType;

import java.util.Scanner;

import static trocadilhos.grpc.server.ResponseType.POLL_TIME;

public class Client {

    private Boolean loggined = false;
    private final ManagedChannel channel;
    private TrocadilhosGameGrpc.TrocadilhosGameStub stub;
    private TrocadilhosGameGrpc.TrocadilhosGameBlockingStub blockingStub;

    public Client(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    private Client(ManagedChannel channel) {
        this.channel = channel;
        stub = TrocadilhosGameGrpc.newStub(channel);
        blockingStub = TrocadilhosGameGrpc.newBlockingStub(channel);
    }

    private void startReceive(String nickname, Boolean reconnected) {

        LoginToGameRequest loginToGameRequest = LoginToGameRequest.newBuilder()
                .setNickname(nickname)
                .setReconnected(reconnected.toString())
                .build();

        LoginToGameResponse loginToGameResponse = blockingStub.loginToGame(loginToGameRequest);
        if(loginToGameResponse.get)


        StreamObserver<APIRequest> connection = stub.start(new StreamObserver<APIResponse>() {
            @Override
            public void onNext(APIResponse value) {
                System.out.println(value.getMessage());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String message = sc.nextLine();
            APIRequest apiRequest = APIRequest.newBuilder().setFrom(nickname).setMessage(message).build();
            connection.onNext(apiRequest);
        }
    }

    public static void main(String[] args) {

        Client client = new Client("localhost", 8000);


        Scanner sc = new Scanner(System.in);
        System.out.println("Digite seu nickname: ");
        String nickname = sc.nextLine();

        //TODO validar aqui se nickname foi reconectado


        client.startReceive(nickname, false);

//        //LoginToMasterResponse loginToMasterResponse = stub.loginToMaster(LoginToMasterRequest.newBuilder()
//                .setNickname(nickname)
//                .build());
//
//        System.out.println(loginToMasterResponse);
        client.channel.shutdown();
    }
}