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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {JasyptConfig.class})
@SpringBootTest
class SensorServiceTest {
    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorService sensorService;


    @Test
    void update_existing_sensor() {
        TopicEntity topic =new TopicEntity();
        topic.setLabel("label");
        SensorEntity sensor = new SensorEntity();
        sensor.setTopic(topic);
        sensor.setMessage("message");

        given(sensorRepository.save(sensor)).willReturn(sensor);

        final SensorEntity expected = sensorService.save(sensor);

        assertNotNull(expected);

        verify(sensorRepository).save(any(SensorEntity.class));
    }

    @Test
    void create_sensor() {
        TopicEntity topic =new TopicEntity();
        topic.setLabel("label");
        SensorEntity sensor = new SensorEntity();
        sensor.setTopic(topic);
        sensor.setMessage("message");

        given(sensorRepository.findById(sensor.getId())).willReturn(Optional.empty());
        given(sensorRepository.save(sensor)).willAnswer(invocation -> invocation.getArgument(0));

        SensorEntity savedSensor =sensorService.save(sensor);

        assertThat(savedSensor).isNotNull();

        verify(sensorRepository).save(any(SensorEntity.class));
    }

    @Test
    void all_sensors() {
        List<SensorEntity> datas = new ArrayList<>();

        TopicEntity topic1 =new TopicEntity();
        topic1.setLabel("label");
        SensorEntity sensor1 = new SensorEntity();
        sensor1.setTopic(topic1);
        sensor1.setMessage("message");

        TopicEntity topic2 =new TopicEntity();
        topic2.setLabel("label");
        SensorEntity sensor2 = new SensorEntity();
        sensor2.setTopic(topic2);
        sensor2.setMessage("message");

        TopicEntity topic3 =new TopicEntity();
        topic3.setLabel("label");
        SensorEntity sensor3 = new SensorEntity();
        sensor3.setTopic(topic3);
        sensor3.setMessage("message");

        datas.add(sensor1);
        datas.add(sensor2);
        datas.add(sensor3);

        given(sensorRepository.findAll()).willReturn(datas);

        List<SensorEntity> expected = sensorRepository.findAll();

        assertEquals(expected, datas);
    }

    @Test
    void findById() {

    }

    @Test
    void findLabelByTopicId() {
    }
}