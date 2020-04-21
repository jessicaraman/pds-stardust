package pds.stardust.scaccount.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pds.stardust.scaccount.entity.CustomerEntity;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

    CustomerEntity findByUsername(String username);
}
