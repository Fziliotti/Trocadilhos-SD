import java.util.Map;
import java.util.UUID;

public class Round {

    private UUID id;
    private Integer number;
    private Map<UUID, Pun> puns;
    private UUID winner;

    public Round() {
    }

    public Round(UUID id, Map<UUID, Pun> puns, UUID winner, Integer number) {
        this.id = id;
        this.puns = puns;
        this.winner = winner;
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<UUID, Pun> getPuns() {
        return puns;
    }

    public void setPuns(Map<UUID, Pun> puns) {
        this.puns = puns;
    }

    public UUID getWinner() {
        return winner;
    }

    public void setWinner(UUID winner) {
        this.winner = winner;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}