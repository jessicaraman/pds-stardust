package pds.stardust.scsensorinteraction.services;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {JasyptConfig.class})
@SpringBootTest
class SensorServiceTest {
    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorService sensorService;

    /*Create sensor*/
    @Test
    void givenValidSensor_whenSave_thenSucceed() throws JsonProcessingException {
        TopicEntity topic = new TopicEntity();
        topic.setLabel("label");
        SensorEntity sensor = new SensorEntity();
        sensor.setTopic(topic);
        sensor.setMessage("message");

        given(sensorRepository.findById(sensor.getId())).willReturn(Optional.empty());
        given(sensorRepository.save(sensor)).willAnswer(invocation -> invocation.getArgument(0));

        SensorEntity savedSensor = sensorService.save(sensor);

        assertThat(savedSensor).isNotNull();
        assertThat(savedSensor.getId()).isNotNull();
        assertThat(savedSensor.getTopic().getId()).isNotNull();

        verify(sensorRepository).save(any(SensorEntity.class));
    }

    @Test
    void givenNullSensor_whenSave_thenFail() {
        assertThrows(NullPointerException.class, () -> {
            sensorService.save(null);
        });
    }

    @Test
    void givenSensorWithoutLabel_thenFail(){
        SensorEntity sensor = new SensorEntity();
        sensor.setMessage("message");
        assertThrows(NullPointerException.class, () -> {
            sensorService.save(sensor);
        });

    }

    /*Find all*/
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
    void all_sensors_EMPTY() {
        List<SensorEntity> data = new ArrayList<>();

        Mockito.doReturn(data).when(sensorRepository).findAll();

        assertThat(sensorService.list()).isEmpty();
    }

    @Test
    void findById_OK() {
        TopicEntity topic1 = new TopicEntity();
        topic1.setLabel("label");

        SensorEntity sensor1 = new SensorEntity();
        sensor1.setId("sensorID");
        sensor1.setTopic(topic1);
        sensor1.setMessage("sensorMessage");

        Mockito.when(sensorRepository.findById("sensorID")).thenReturn(Optional.of(sensor1));

        Optional<SensorEntity> actual = sensorService.findById(sensor1.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(sensor1);
    }

    @Test
    void findById_KO() {
        TopicEntity topic1 = new TopicEntity();
        topic1.setLabel("label");

        SensorEntity sensor1 = new SensorEntity();
        sensor1.setId("sensorID");
        sensor1.setTopic(topic1);
        sensor1.setMessage("sensorMessage");

        Mockito.when(sensorRepository.findById("sensorID")).thenReturn(Optional.of(sensor1));

        Optional<SensorEntity> actual = sensorService.findById("sensorID_KO");

        assertThat(actual).isNotPresent();
    }

    @Test
    void findLabelByTopicId_OK() {
        TopicEntity topic = new TopicEntity();
        topic.setId("topicID");
        topic.setLabel("topicLabel");

        SensorEntity sensor = new SensorEntity();
        sensor.setId("sensorID");
        sensor.setMessage("sensorMessage");
        sensor.setTopic(topic);

        List<SensorEntity> sensors = new ArrayList<>();
        sensors.add(sensor);

        Mockito.when(sensorRepository.findAll()).thenReturn(sensors);

        Optional<String> actual = sensorService.findLabelByTopicId("topicID");

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo("topicLabel");
    }

    @Test
    void findLabelByTopicId_KO() {
        TopicEntity topic = new TopicEntity();
        topic.setId("topicID");
        topic.setLabel("topicLabel");

        SensorEntity sensor = new SensorEntity();
        sensor.setId("sensorID");
        sensor.setMessage("sensorMessage");
        sensor.setTopic(topic);

        List<SensorEntity> sensors = new ArrayList<>();
        sensors.add(sensor);

        Mockito.when(sensorRepository.findAll()).thenReturn(sensors);

        Optional<String> actual = sensorService.findLabelByTopicId("topicID_KO");

        assertThat(actual).isNotPresent();
    }
}