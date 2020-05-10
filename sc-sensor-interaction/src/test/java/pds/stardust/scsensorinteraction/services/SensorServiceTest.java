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
import pds.stardust.scsensorinteraction.repositories.SensorRepository;

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
        String uri = "/sensor";
        SensorEntity sensor = new SensorEntity();
        TopicEntity topic = new TopicEntity();
        topic.setId("id");
        topic.setLabel("test label topic");
        sensor.setId("id");
        sensor.setMessage(" test message");
        sensor.setTopic(topic);

        doReturn(Optional.of(sensor)).when(sensorRepository).findById(anyString());
        doReturn(sensor).when(sensorRepository).save(Mockito.any(SensorEntity.class));
    }

    @Test
    void save() {
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