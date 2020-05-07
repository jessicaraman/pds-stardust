package pds.stardust.scsensorinteraction.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pds.stardust.scsensorinteraction.entities.SensorEntity;

@Repository
public interface SensorRepository extends MongoRepository<SensorEntity, String> {
}
