package trocadilhos.grpc.tests;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import trocadilhos.grpc.*;
import javax.naming.AuthenticationException;
import java.util.Random;
import java.util.Scanner;
import static trocadilhos.grpc.server.ResponseType.*;


public class TestClient {

    private Boolean loggined = false;
    private final ManagedChannel channel;
    private TrocadilhosGameGrpc.TrocadilhosGameStub stub;
    private TrocadilhosGameGrpc.TrocadilhosGameBlockingStub blockingStub;

    public TestClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    private TestClient(ManagedChannel channel) {
        this.channel = channel;
        stub = TrocadilhosGameGrpc.newStub(channel);
        blockingStub = TrocadilhosGameGrpc.newBlockingStub(channel);
    }

    private void startReceive(String nickname, Boolean reconnected) throws AuthenticationException {

        LoginToGameRequest loginToGameRequest = LoginToGameRequest.newBuilder()
                .setNickname(nickname)
                .setReconnected(reconnected.toString())
                .build();

        APIResponse apiResponse = blockingStub.loginToGame(loginToGameRequest);

        if(apiResponse.getType().equals(FULL_SERVER_ERROR.toString())){
            throw new AuthenticationException("Failed to login to game server!");
        }

        if(apiResponse.getType().equals(NORMAL_MESSAGE.toString())){
            System.out.println(apiResponse.getMessage());
        }


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
                System.out.println("completed");
            }
        });


        APIRequest identificationRequest = APIRequest.newBuilder().setFrom(nickname).setType(IDENTIFICATION.toString()).build();
        connection.onNext(identificationRequest);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String message = sc.nextLine();
            APIRequest apiRequest = APIRequest.newBuilder().setFrom(nickname).setMessage(message).build();
            connection.onNext(apiRequest);
        }
    }

    public static void main(String[] args) throws AuthenticationException {

        Random random = new Random();
        TestClient client = new TestClient("localhost", 8080);


        Scanner sc = new Scanner(System.in);
        System.out.println("Digite seu nickname: ");
        String nickname = sc.nextLine();

        client.startReceive(nickname, false);
        client.channel.shutdown();
    }
}