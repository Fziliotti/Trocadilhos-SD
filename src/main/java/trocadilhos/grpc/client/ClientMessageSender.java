package trocadilhos.grpc.client;

import io.grpc.stub.StreamObserver;
import trocadilhos.grpc.APIRequest;

import java.io.InputStream;
import java.util.Scanner;

class ClientMessageSender implements Runnable {

    StreamObserver<APIRequest> connection;
    String nickname;

    public ClientMessageSender(StreamObserver<APIRequest> connection, String nickname) {
        this.connection = connection;
        this.nickname = nickname;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String message = sc.nextLine();
            APIRequest apiRequest = APIRequest.newBuilder().setFrom(nickname).setMessage(message).build();
            connection.onNext(apiRequest);
        }
    }
}