import java.util.UUID;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPontuation() {
        return pontuation;
    }

    public void setPontuation(Integer pontuation) {
        this.pontuation = pontuation;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public void incrementPontuation() {
        this.setPontuation(this.getPontuation() + 1);
    }

    public int compareTo(Pun other) {
        return other.getPontuation().compareTo(this.pontuation);
    }
}