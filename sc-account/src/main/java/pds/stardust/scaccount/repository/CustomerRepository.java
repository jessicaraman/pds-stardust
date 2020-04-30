package pds.stardust.scaccount.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pds.stardust.scaccount.entity.CustomerEntity;

/**
 * CustomerRepository
 */
@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

    CustomerEntity findByUsername(String username);
    CustomerEntity getById(String id);
}
