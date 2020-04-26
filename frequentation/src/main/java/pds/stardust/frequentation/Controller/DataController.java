package pds.stardust.frequentation.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pds.stardust.frequentation.Model.Data;
import pds.stardust.frequentation.Repository.DataRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class DataController {
@Autowired
    private DataRepository repository ;

@PostMapping("/addData")
    public String saveDATA(@RequestBody Data data){
    repository.save(data) ;
    return "Client ajouté avec l'id : " + data.getIdclient() ;
}

@GetMapping("/findAllData")
    public List<Data> getData() {
    return repository.findAll() ;
}

@GetMapping("/findAllData/{id}")
    public Optional<Data> getData(@PathVariable int id) {
        return repository.findById(id) ;
    }

    @DeleteMapping("/deleteData/{id}")
    public String deleteData (@PathVariable int id) {
        repository.deleteById(id) ;
        return "client avec l'id :" + id + "supprimer" ;
    }

}
