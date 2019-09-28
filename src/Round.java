import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Round {

    private UUID id;
    private Integer number;
    private String theme;
    private Map<Integer, Pun> puns;
    private UUID winner;

    public Round() {
        this.id = UUID.randomUUID();
        this.puns = new HashMap<>();
    }

    public Round(UUID id, Map<Integer, Pun> puns, UUID winner, Integer number) {
        this.id = UUID.randomUUID();
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

    public Map<Integer, Pun> getPuns() {
        return puns;
    }

    public void setPuns(Map<Integer, Pun> puns) {
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}