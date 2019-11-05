package trocadilhos.grpc.server;

import io.grpc.stub.StreamObserver;
import trocadilhos.grpc.APIResponse;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Player {

    private UUID id;
    private String name;
    private Integer score;
    private List<Pun> puns;
    private StreamObserver<APIResponse> responseObserver;
    boolean online;

    public Player() {
    }

    public Player( String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.score = 0;
        this.puns = new ArrayList<>();
        this.online = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Pun> getPuns() {
        return puns;
    }

    public void setPuns(List<Pun> puns) {
        this.puns = puns;
    }

    public int compareTo(Player other){
        return other.getScore().compareTo(this.getScore());
    }

    public void incrementScore (Integer amount){
        this.setScore(this.getScore() + amount);
    }

    public StreamObserver<APIResponse> getResponseObserver() {
        return responseObserver;
    }

    public void setResponseObserver(StreamObserver<APIResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}