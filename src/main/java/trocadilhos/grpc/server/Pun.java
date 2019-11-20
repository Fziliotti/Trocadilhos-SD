package trocadilhos.grpc.server;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Pun {

    private UUID id;
    private String description;
    private Integer pontuation;
    private Integer number;
    private UUID playerId;

    public Pun(String description, Integer number, UUID playerId) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.number = number;
        this.playerId = playerId;
        this.pontuation = 0;
    }

    public void incrementPontuation() {
        this.setPontuation(this.getPontuation() + 1);
    }

    public int compareTo(Pun other) {
        return other.getPontuation().compareTo(this.pontuation);
    }
}