package trocadilhos.grpc.server;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import trocadilhos.grpc.APIResponse;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Player {

    private UUID id;
    private String name;
    private Integer score;
    private List<Pun> puns;
    private StreamObserver<APIResponse> responseObserver;
    boolean online;

    public Player(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.score = 0;
        this.puns = new ArrayList<>();
        this.online = true;
    }

    public void incrementScore (Integer amount){
        this.setScore(this.getScore() + amount);
    }

    public int compareTo(Player other){
        return other.getScore().compareTo(this.getScore());
    }
}