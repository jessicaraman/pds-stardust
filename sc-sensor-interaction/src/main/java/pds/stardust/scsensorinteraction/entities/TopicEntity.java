package pds.stardust.scsensorinteraction.entities;

public class TopicEntity {

    private String id;
    private String label;

    public TopicEntity() {
    }

    public TopicEntity(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "TopicEntity{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

}
