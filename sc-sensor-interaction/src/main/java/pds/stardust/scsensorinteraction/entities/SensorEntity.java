package pds.stardust.scsensorinteraction.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SensorEntity {

    @Id
    private String id;
    private TopicEntity topic;
    private String message;

    public SensorEntity() {
    }

    public SensorEntity(TopicEntity topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public SensorEntity(String id, TopicEntity topic, String message) {
        this.id = id;
        this.topic = topic;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SensorEntity{" +
                "id='" + id + '\'' +
                ", topic=" + topic.toString() +
                ", message='" + message + '\'' +
                '}';
    }

}
