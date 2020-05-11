package pds.stardust.scsensorinteraction.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(SensorController.class);
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/sensor")
    public SensorEntity create(@RequestBody SensorEntity sensorEntity) throws JsonProcessingException {
        logger.info("Enter sensor creation");
        return sensorService.save(sensorEntity);
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<?> getSensorById(@PathVariable String id) {

        logger.info("Enter sensor research");
        Optional<SensorEntity> optSensor = sensorService.findById(id);

        return optSensor.map(sensorEntity -> new ResponseEntity<>(sensorEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/sensor/topic/{id}/label")
    public ResponseEntity<?> getTopicLabelById(@PathVariable String id) {

        logger.info("Enter topic label research");
        Optional<String> optTopicLabel = sensorService.findLabelByTopicId(id);

        return optTopicLabel.map(label -> new ResponseEntity<>(new CommonResponse(label), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/sensor")
    public List<SensorEntity> getSensors() {
        return sensorService.list();
    }
}
