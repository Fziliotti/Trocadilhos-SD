import java.util.UUID;

public class Pun {

    private UUID id;
    private String description;
    private Integer pontuation;

    public Pun(UUID id, String description) {
        this.id = id;
        this.description = description;
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
}