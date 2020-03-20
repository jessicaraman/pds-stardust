package com.example.geolocation.services;

import com.example.geolocation.entities.LocationEntity;
import com.example.geolocation.entities.LocationHistoryEntity;
import com.example.geolocation.repositories.LocationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationHistoryService {

    private final LocationHistoryRepository locationHistoryRepository;

    @Autowired
    public LocationHistoryService(LocationHistoryRepository locationHistoryRepository) {
        this.locationHistoryRepository = locationHistoryRepository;
    }

    public LocationHistoryEntity save(LocationHistoryEntity locationHistory) {

        Optional<LocationHistoryEntity> optLocationHistoryInDb= findById(locationHistory.getId());

        if (optLocationHistoryInDb.isPresent()) {
            LocationHistoryEntity locationHistoryInDb = optLocationHistoryInDb.get();
            List<LocationEntity> locations = locationHistoryInDb.getLocations();
            locations.addAll(locationHistory.getLocations());
            locationHistoryInDb.setLocations(locations);

            return locationHistoryRepository.save(locationHistoryInDb);
        }

        return locationHistoryRepository.save(locationHistory);
    }

    public Optional<LocationHistoryEntity> findById(String id) {
        return locationHistoryRepository.findById(id);
    }

    public String sayHello() {
        return "Hello";
    }
}
