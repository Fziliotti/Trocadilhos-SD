package trocadilhos.grpc.server;

import trocadilhos.grpc.*;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class TrocadilhosGameImpl extends TrocadilhosGameGrpc.TrocadilhosGameImplBase {

    @Override
    public void trocadilho(
            TrocadilhoRequest request, StreamObserver<APIResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request.getDescription());

        String nickname = request.getDescription();

     //   List<String> playerList = getPlayersList();

       // if(playerList.contains(nickname)){

    //    }


        APIResponse response = APIResponse.newBuilder()
                .setMessage("Eitcha lele")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}