package pds.stardust.frequentation.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pds.stardust.frequentation.Model.Data;
import pds.stardust.frequentation.Service.DataService;

import java.util.List;
import java.util.Optional;

@RestController
public class DataController {

@Autowired
    private DataService service ;

@PostMapping("/addData")
    public Data saveDATA(@RequestBody Data data) {
    return service.addData(data);
}

@GetMapping("/findAllData")
    public List<Data> getData() {
    return service.getData() ;
}

@GetMapping("/findAllData/{id}")
    public Optional<Data> getData(@PathVariable int id) {
        return service.getDataById(id) ;
    }

    @DeleteMapping("/deleteData")
    public Data removeData(@RequestBody Data data) {
        service.deletedata(data);
        return data;
    }

}
