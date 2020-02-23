package com.example.geolocation.repositories;

import com.example.geolocation.entities.LocationHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationHistoryRepository extends MongoRepository<LocationHistoryEntity, String> {
}
