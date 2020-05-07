package pds.stardust.scsensorinteraction.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.stardust.scsensorinteraction.entities.SensorEntity;
import pds.stardust.scsensorinteraction.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public SensorEntity save(SensorEntity sensorEntity) {
        sensorEntity.setId(UUID.randomUUID().toString());
        sensorEntity.getTopic().setId(UUID.randomUUID().toString());
        return sensorRepository.save(sensorEntity);
    }

    public List<SensorEntity> list() {
        return sensorRepository.findAll();
    }

    public Optional<SensorEntity> findById(String id) {
        return sensorRepository.findById(id);
    }

    public Optional<String> findLabelByTopicId(String id) {
        return sensorRepository.findAll().stream()
                .filter(sensor -> sensor.getTopic().getId().equals(id))
                .map(sensorEntity -> sensorEntity.getTopic().getLabel())
                .findFirst();
    }
}
