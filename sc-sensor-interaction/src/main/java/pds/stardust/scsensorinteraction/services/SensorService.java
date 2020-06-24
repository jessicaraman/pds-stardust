package pds.stardust.scsensorinteraction.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.stardust.scsensorinteraction.entities.SensorEntity;
import pds.stardust.scsensorinteraction.exceptions.BadRequestException;
import pds.stardust.scsensorinteraction.exceptions.ServiceException;
import pds.stardust.scsensorinteraction.repositories.SensorRepository;

import java.util.ArrayList;
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

    public SensorEntity save(SensorEntity sensorEntity) {
        validateSensorEntity(sensorEntity);

        sensorEntity.setId(UUID.randomUUID().toString());
        sensorEntity.getTopic().setId(UUID.randomUUID().toString());

        SensorEntity savedEntity;
        try {
            savedEntity = sensorRepository.save(sensorEntity);
        } catch (Exception e) {
            throw new ServiceException("Error when saving SensorEntity: " + e.getMessage());
        }

        logger.info("Create sensor with details [{}]", sensorEntity.toString());
        return savedEntity;
    }

    public List<SensorEntity> list() throws ServiceException {
        List<SensorEntity> sensors;
        try {
            sensors = sensorRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error when getting sensors: " + e.getMessage());
        }
        return sensors;
    }

    public Optional<SensorEntity> findById(String id) {
        logger.info("Retrieve sensor details with id [{}]", id);
        return sensorRepository.findById(id);
    }

    public Optional<String> findLabelByTopicId(String id) {
        logger.info("Retrieve topic label with id [{}]", id);
        return sensorRepository.findAll().stream()
                .filter(sensor -> sensor.getTopic().getId().equals(id))
                .map(sensorEntity -> sensorEntity.getTopic().getLabel())
                .findFirst();
    }

    private void validateSensorEntity(SensorEntity entity) throws BadRequestException {
        if (entity == null) {
            throw new BadRequestException("SensorEntity should not be null");
        }
        if (entity.getMessage() == null) {
            throw new BadRequestException("SensorEntity should have a message");
        }
        if (entity.getTopic() == null) {
            throw new BadRequestException("SensorEntity should have a topic");
        }
        if (entity.getTopic().getLabel() == null) {
            throw new BadRequestException("TopicEntity should have a label");
        }
    }

}
