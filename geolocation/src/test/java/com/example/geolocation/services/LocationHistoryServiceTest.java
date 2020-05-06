package com.example.geolocation.services;

import com.example.geolocation.entities.LocationEntity;
import com.example.geolocation.entities.LocationHistoryEntity;
import com.example.geolocation.repositories.LocationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class LocationHistoryServiceTest {

    @Mock
    private LocationHistoryRepository locationHistoryRepository;

    @InjectMocks
    private LocationHistoryService locationHistoryService;

    @BeforeEach
    public void before() {
        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();
        locationHistoryEntity.setId("id");

        ArrayList<LocationEntity> locations = new ArrayList<>();
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));

        locationHistoryEntity.setLocations(locations);

        doReturn(Optional.of(locationHistoryEntity)).when(locationHistoryRepository).findById(anyString());
        doReturn(locationHistoryEntity).when(locationHistoryRepository).save(Mockito.any(LocationHistoryEntity.class));

    }

    @Test
    void testFindById() {
        Optional<LocationHistoryEntity> optionalLocationHistoryEntity = locationHistoryService.findById("id");
        assertTrue(optionalLocationHistoryEntity.isPresent());
        LocationHistoryEntity locationHistoryEntity = optionalLocationHistoryEntity.get();
        assertEquals("id", locationHistoryEntity.getId());
        assertEquals(6, locationHistoryEntity.getLocations().size());
    }

    @Test
    void testSave_update_existing_history() {

        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();
        locationHistoryEntity.setId("id");

        ArrayList<LocationEntity> locations = new ArrayList<>();
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));

        locationHistoryEntity.setLocations(locations);

        LocationHistoryEntity savedLocationHistoryEntity = locationHistoryService.save(locationHistoryEntity);
        assertNotNull(savedLocationHistoryEntity);
        assertEquals("id", locationHistoryEntity.getId());
        assertEquals(6, locationHistoryEntity.getLocations().size());

    }

    @Test
    void testSave_create_new_history() {

        doReturn(Optional.empty()).when(locationHistoryRepository).findById(anyString());

        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();
        locationHistoryEntity.setId("id");

        ArrayList<LocationEntity> locations = new ArrayList<>();
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));
        locations.add(new LocationEntity(1.00, 1.00, LocalDateTime.now().toString()));

        locationHistoryEntity.setLocations(locations);

        LocationHistoryEntity savedLocationHistoryEntity = locationHistoryService.save(locationHistoryEntity);
        assertNotNull(savedLocationHistoryEntity);
        assertEquals("id", locationHistoryEntity.getId());
        assertEquals(6, locationHistoryEntity.getLocations().size());

    }

    @Test
    void testSayHello() {
        assertEquals("Hello", locationHistoryService.sayHello());
    }

}