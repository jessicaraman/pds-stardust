package pds.stardust.scsensorinteraction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoResource {

    @GetMapping
    public String f() {
        return "hello";
    }
}
