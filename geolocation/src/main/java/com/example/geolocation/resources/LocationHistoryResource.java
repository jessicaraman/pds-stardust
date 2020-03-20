package com.example.geolocation.resources;

import com.example.geolocation.entities.LocationHistoryEntity;
import com.example.geolocation.services.LocationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LocationHistoryResource {

    private final LocationHistoryService locationHistoryService;

    @Autowired
    public LocationHistoryResource(LocationHistoryService locationHistoryService) {
        this.locationHistoryService = locationHistoryService;
    }

    @GetMapping("/history")
    public LocationHistoryEntity test(@RequestParam String id) {
        return locationHistoryService.findById(id).orElse(new LocationHistoryEntity());
    }

    @PostMapping("/history")
    public LocationHistoryEntity test(@RequestBody LocationHistoryEntity locationHistoryEntity) {
        return locationHistoryService.save(locationHistoryEntity);
    }

    @GetMapping("/hello")
    public String hello() {
        return locationHistoryService.sayHello();
    }

}
