package pds.stardust.scsensorinteraction.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pds.stardust.scsensorinteraction.config.JasyptConfig;
import pds.stardust.scsensorinteraction.services.SensorService;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SensorController.class)
@ContextConfiguration(classes = {JasyptConfig.class})
class SensorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SensorService service;

    @Test
    void create() {

    }

    @Test
    void getSensorById() {
    }

    @Test
    void getTopicLabelById() {
    }

    @Test
    void getSensors() {
    }
}