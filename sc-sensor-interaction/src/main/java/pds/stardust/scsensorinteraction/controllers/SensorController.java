package pds.stardust.scsensorinteraction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pds.stardust.scsensorinteraction.beans.CommonResponse;
import pds.stardust.scsensorinteraction.entities.SensorEntity;
import pds.stardust.scsensorinteraction.services.SensorService;

import java.util.List;
import java.util.Optional;

@RestController
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/sensor")
    public SensorEntity create(@RequestBody SensorEntity sensorEntity) {
        return sensorService.save(sensorEntity);
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<?> getSensorById(@PathVariable String id) {

        Optional<SensorEntity> optSensor = sensorService.findById(id);

        return optSensor.map(sensorEntity -> new ResponseEntity<>(sensorEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/sensor/topic/{id}/label")
    public ResponseEntity<?> getTopicLabelById(@PathVariable String id) {

        Optional<String> optTopicLabel = sensorService.findLabelByTopicId(id);

        return optTopicLabel.map(label -> new ResponseEntity<>(new CommonResponse(label), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/sensor")
    public List<SensorEntity> getSensors() {
        return sensorService.list();
    }
}
