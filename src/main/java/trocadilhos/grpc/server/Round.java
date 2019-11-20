package trocadilhos.grpc.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Round {

    private UUID id;
    private Integer number;
    private String theme;
    private Map<Integer, Pun> puns;
    private UUID winner;
    private Integer numberOfVotes;

    public void incrementVotes() {
        this.setNumberOfVotes(this.getNumberOfVotes() + 1);
    }
}