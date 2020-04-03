package com.example.geolocation.resources;

import com.example.geolocation.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoResource {

    private final DemoService demoService;

    @Autowired
    public DemoResource(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/demo")
    private void demo() throws Exception {
        demoService.demo();
    }
}
