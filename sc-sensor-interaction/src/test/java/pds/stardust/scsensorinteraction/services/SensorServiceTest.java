package pds.stardust.scsensorinteraction.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pds.stardust.scsensorinteraction.config.JasyptConfig;
import pds.stardust.scsensorinteraction.entities.SensorEntity;
import pds.stardust.scsensorinteraction.entities.TopicEntity;
import pds.stardust.scsensorinteraction.repositories.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ContextConfiguration(classes = {JasyptConfig.class})
@SpringBootTest
class SensorServiceTest {
    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorService sensorService;

    @BeforeEach
    public void before() {
        SensorEntity sensorEntity = new SensorEntity();
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setLabel("test label topic");
        sensorEntity.setId("id");
        sensorEntity.setMessage(" test message");
        sensorEntity.setTopic(topicEntity);

        doReturn(Optional.of(sensorEntity)).when(sensorRepository).findById(anyString());
        doReturn(sensorEntity).when(sensorRepository).save(Mockito.any(SensorEntity.class));
    }

    @Test
    void update_existing_sensor() {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setId("id");
        TopicEntity topicEntity= new TopicEntity();
        topicEntity.setLabel("new_label");
        sensorEntity.setTopic(topicEntity);


        SensorEntity savedSensorEntity = sensorService.save(sensorEntity);
        assertNotNull(savedSensorEntity);
        assertEquals("id", savedSensorEntity.getId());
        assertEquals("new_label", sensorEntity.getTopic().getLabel());
    }

    @Test
    void create_sensor() {
        doReturn(Optional.empty()).when(sensorRepository).findById(anyString());
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setId("id");
        TopicEntity topicEntity= new TopicEntity();
        topicEntity.setLabel("new_label");
        sensorEntity.setTopic(topicEntity);


        SensorEntity savedSensorEntity = sensorService.save(sensorEntity);
        assertNotNull(savedSensorEntity);
        assertEquals("id", savedSensorEntity.getId());
        assertEquals("new_label", sensorEntity.getTopic().getLabel());
    }

    @Test
    void list() {
    }

    @Test
    void findById() {
        Optional<SensorEntity> optionalSensorEntity = sensorService.findById("id");
        assertTrue(optionalSensorEntity.isPresent());
        SensorEntity sensorEntity = optionalSensorEntity.get();
        assertEquals("id", sensorEntity.getId());
    }

    @Test
    void findLabelByTopicId() {
    }
}