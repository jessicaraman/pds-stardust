package pds.stardust.scsensorinteraction.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.stardust.scsensorinteraction.entities.SensorEntity;
import pds.stardust.scsensorinteraction.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SensorService {

    private final Logger logger = LoggerFactory.getLogger(SensorService.class);
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public SensorEntity save(SensorEntity sensorEntity) throws JsonProcessingException {
        sensorEntity.setId(UUID.randomUUID().toString());
        sensorEntity.getTopic().setId(UUID.randomUUID().toString());
        logger.info("Create sensor with details [{}]",  new ObjectMapper().writeValueAsString(sensorEntity));
        return sensorRepository.save(sensorEntity);
    }

    public List<SensorEntity> list() {
        return sensorRepository.findAll();
    }

    public Optional<SensorEntity> findById(String id) {
        logger.info("Retrieve sensor details with id [{}]", id );
        return sensorRepository.findById(id);
    }

    public Optional<String> findLabelByTopicId(String id) {
        logger.info("Retrieve topic label with id [{}]", id );
        return sensorRepository.findAll().stream()
                .filter(sensor -> sensor.getTopic().getId().equals(id))
                .map(sensorEntity -> sensorEntity.getTopic().getLabel())
                .findFirst();
    }
}
