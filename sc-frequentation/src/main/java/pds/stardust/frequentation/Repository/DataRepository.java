package pds.stardust.frequentation.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pds.stardust.frequentation.Model.Data;

public interface DataRepository extends MongoRepository <Data , Integer>  {
}
