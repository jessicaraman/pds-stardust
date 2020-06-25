package pds.stardust.scsensorinteraction.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pds.stardust.scsensorinteraction.config.JasyptConfig;
import pds.stardust.scsensorinteraction.entities.SensorEntity;
import pds.stardust.scsensorinteraction.entities.TopicEntity;
import pds.stardust.scsensorinteraction.exceptions.BadRequestException;
import pds.stardust.scsensorinteraction.services.SensorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {JasyptConfig.class})
class SensorControllerTest {

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorController sensorController;

    @Test
    void givenSensor_whenCreate_thenSucceed() {
        TopicEntity topic = new TopicEntity("label");
        SensorEntity sensor = new SensorEntity(topic, "message");

        TopicEntity insertedTopic = new TopicEntity("topic-1", "label");
        SensorEntity insertedSensor = new SensorEntity("sensor-1", insertedTopic, "message");

        when(sensorService.save(any())).thenReturn(insertedSensor);

        SensorEntity actual = sensorController.create(sensor);

        assertNotNull(actual);
        assertEquals("sensor-1", actual.getId());
        assertEquals("topic-1", actual.getTopic().getId());
    }

    @Test
    void givenServiceException_whenCreate_thenFail() {
        when(sensorService.save(any())).thenThrow(new BadRequestException("sensor is null"));

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> sensorController.create(null));
        assertEquals("sensor is null", thrown.getMessage());
    }

}