package pds.stardust.frequentation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.stardust.frequentation.Model.Data;
import pds.stardust.frequentation.Repository.DataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    DataRepository repository;

    public Data addData(Data data)  {
        return repository.save(data) ;
    }

    public List<Data> getData() {
        List<Data> data = repository.findAll() ;
        return data ;
    }

    public Optional<Data> getDataById(int id) {
        return repository.findById(id);

    }
    public void deletedata (Data data) {
        repository.delete(data);
    }

}
