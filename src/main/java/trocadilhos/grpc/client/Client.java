package trocadilhos.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import trocadilhos.grpc.*;

import java.util.Scanner;

public class Client {

    private Boolean loggined = false;
    private final ManagedChannel channel;
    private TrocadilhosGameGrpc.TrocadilhosGameStub stub;

    public Client(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    private Client(ManagedChannel channel) {
        this.channel = channel;
        stub = TrocadilhosGameGrpc.newStub(channel);
    }

    private void startReceive(){

        StreamObserver<APIRequest> connection = stub.start(new StreamObserver<APIResponse>() {
            @Override
            public void onNext(APIResponse value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });

    }

    public static void main(String[] args) {

        Client client = new Client("localhost", 8080);

        client.startReceive();

        Scanner sc = new Scanner(System.in);
        String nickname = sc.nextLine();
        System.out.println("Digite seu nickname: ");
//        //LoginToMasterResponse loginToMasterResponse = stub.loginToMaster(LoginToMasterRequest.newBuilder()
//                .setNickname(nickname)
//                .build());
//
//        System.out.println(loginToMasterResponse);
        client.channel.shutdown();
    }
}