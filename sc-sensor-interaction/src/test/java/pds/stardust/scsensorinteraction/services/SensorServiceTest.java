package pds.stardust.scsensorinteraction.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pds.stardust.scsensorinteraction.config.JasyptConfig;
import pds.stardust.scsensorinteraction.entities.SensorEntity;
import pds.stardust.scsensorinteraction.entities.TopicEntity;
import pds.stardust.scsensorinteraction.exceptions.BadRequestException;
import pds.stardust.scsensorinteraction.exceptions.ServiceException;
import pds.stardust.scsensorinteraction.repositories.SensorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = {JasyptConfig.class})
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorService sensorService;

    @Test
    void givenValidSensor_whenSave_thenSucceed() {
        TopicEntity topic = new TopicEntity("label");
        SensorEntity sensor = new SensorEntity(topic, "message");

        given(sensorRepository.save(sensor)).willAnswer(invocation -> invocation.getArgument(0));

        SensorEntity savedSensor = sensorService.save(sensor);

        assertThat(savedSensor).isNotNull();
        assertEquals(36, savedSensor.getId().length());
        assertEquals(36, savedSensor.getTopic().getId().length());
        assertEquals("message", savedSensor.getMessage());
        assertEquals("label", savedSensor.getTopic().getLabel());

        verify(sensorRepository).save(any(SensorEntity.class));
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForSave")
    void givenInvalidSensor_whenSave_thenFail(String expectedErrorMessage, SensorEntity entity) {
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> sensorService.save(entity));
        assertEquals(expectedErrorMessage, thrown.getMessage());
    }

    @Test
    void givenRepositoryFails_whenSave_thenFail() {
        TopicEntity topic = new TopicEntity("label");
        SensorEntity sensor = new SensorEntity(topic, "message");

        given(sensorRepository.save(sensor)).willAnswer(invocation -> {
            throw new Exception("Database error");
        });

        ServiceException thrown = assertThrows(ServiceException.class, () -> sensorService.save(sensor));
        assertEquals("Error when saving SensorEntity: Database error", thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForFindAll")
    void givenSensors_whenFindAll_thenSucceed(List<SensorEntity> entities) {
        given(sensorRepository.findAll()).willReturn(entities);

        List<SensorEntity> results = sensorService.list();

        assertEquals(entities, results);
    }

    @Test
    void givenRepositoryFails_whenFindAll_thenFail() {
        given(sensorRepository.findAll()).willAnswer(invocation -> {
            throw new Exception("Database error");
        });

        ServiceException thrown = assertThrows(ServiceException.class, () -> sensorService.list());
        assertEquals("Error when getting sensors: Database error", thrown.getMessage());
    }

    @Test
    void givenExistingSensor_whenFindById_thenSuccess() {
        TopicEntity topic1 = new TopicEntity("topic-1", "label");
        SensorEntity sensor1 = new SensorEntity("sensor-1", topic1, "message");

        given(sensorRepository.findById("sensor-1")).willReturn(Optional.of(sensor1));

        Optional<SensorEntity> actual = sensorService.findById(sensor1.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(sensor1);
        assertEquals("sensor-1", actual.get().getId());
    }

    @Test
    void givenNonExistingSensor_whenFindById_thenReturnEmpty() {
        TopicEntity topic1 = new TopicEntity("topic-1", "label");
        SensorEntity sensor1 = new SensorEntity("sensor-1", topic1, "message");

        given(sensorRepository.findById("sensor-1")).willReturn(Optional.of(sensor1));

        Optional<SensorEntity> actual = sensorService.findById("sensor-2");

        assertThat(actual).isNotPresent();
    }

    @Test
    void givenNullId_whenFindById_thenFail() {
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> sensorService.findById(null));
        assertEquals("ID cannot be null", thrown.getMessage());
    }

    @Test
    void givenRepositoryFails_whenFindById_thenFail() {
        given(sensorRepository.findById("sensor-1")).willAnswer(invocation -> {
            throw new Exception("Database error");
        });

        ServiceException thrown = assertThrows(ServiceException.class, () -> sensorService.findById("sensor-1"));
        assertEquals("Error when getting sensor sensor-1: Database error", thrown.getMessage());
    }

    @Test
    void findLabelByTopicId_OK() {
        TopicEntity topic = new TopicEntity("topicLabel");
        topic.setId("topicID");

        SensorEntity sensor = new SensorEntity("sensorID", topic, "sensorMessage");

        List<SensorEntity> sensors = new ArrayList<>();
        sensors.add(sensor);

        Mockito.when(sensorRepository.findAll()).thenReturn(sensors);

        Optional<String> actual = sensorService.findLabelByTopicId("topicID");

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo("topicLabel");
    }

    @Test
    void findLabelByTopicId_KO() {
        TopicEntity topic = new TopicEntity("topicLabel");
        topic.setId("topicID");

        SensorEntity sensor = new SensorEntity("sensorID", topic, "sensorMessage");

        List<SensorEntity> sensors = new ArrayList<>();
        sensors.add(sensor);

        Mockito.when(sensorRepository.findAll()).thenReturn(sensors);

        Optional<String> actual = sensorService.findLabelByTopicId("topicID_KO");

        assertThat(actual).isNotPresent();
    }

    private static Stream<Arguments> provideTestCasesForSave() {
        return Stream.of(
                Arguments.of("SensorEntity should not be null", null),
                Arguments.of("SensorEntity should have a message", new SensorEntity(new TopicEntity("label"), null)),
                Arguments.of("SensorEntity should have a topic", new SensorEntity(null, "message")),
                Arguments.of("TopicEntity should have a label", new SensorEntity(new TopicEntity(null), "message"))
        );
    }

    private static Stream<Arguments> provideTestCasesForFindAll() {
        List<SensorEntity> data = new ArrayList<>();
        TopicEntity topic1 = new TopicEntity("topic-1", "label");
        SensorEntity sensor1 = new SensorEntity("sensor-1", topic1, "message");
        TopicEntity topic2 = new TopicEntity("topic-2", "label");
        SensorEntity sensor2 = new SensorEntity("sensor-2", topic2, "message");
        TopicEntity topic3 = new TopicEntity("topic-3", "label");
        SensorEntity sensor3 = new SensorEntity("sensor-3", topic3, "message");
        data.add(sensor1);
        data.add(sensor2);
        data.add(sensor3);
        return Stream.of(
                Arguments.of(data),
                Arguments.of(new ArrayList<>())
        );
    }

}