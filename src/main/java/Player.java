import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Player {

    private UUID id;
    private String name;
    private Integer score;
    private List<Pun> puns;
    private Socket playerSocket;


    public Player() {
    }

    public Player(UUID id, String name, Socket playerSocket) {
        this.id = id;
        this.name = name;
        this.playerSocket = playerSocket;
        this.score = 0;
        this.puns = new ArrayList<>();
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

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public void setPlayerSocket(Socket playerSocket) {
        this.playerSocket = playerSocket;
    }

    public int compareTo(Player other){
        return other.getScore().compareTo(this.getScore());
    }

    public void incrementScore (Integer amount){
        this.setScore(this.getScore() + amount);
    }
}