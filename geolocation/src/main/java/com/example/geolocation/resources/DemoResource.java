package com.example.geolocation.resources;

import com.example.geolocation.entities.LocationHistoryEntity;
import com.example.geolocation.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class DemoResource {

    private final DemoService demoService;

    @Autowired
    public DemoResource(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/automatedDemo")
    private void automatedDemo() throws Exception {
        demoService.automatedDemo();
    }

    @GetMapping("/automatedDatabaseDemo")
    private ResponseEntity<?> automatedDatabaseDemo() throws Exception {

        final Optional<LocationHistoryEntity> optLocationHistory = demoService.automatedDatabaseDemo();
        return optLocationHistory.isPresent() ? new ResponseEntity<>(optLocationHistory.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
