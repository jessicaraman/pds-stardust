package pds.stardust.scaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.repository.CustomerRepository;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerEntity findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public CustomerEntity getById(String id) {
        return customerRepository.getById(id);
    }

    @Override
    public CustomerEntity saveCustomer (CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public void initCustomerData () {
        customerRepository.deleteAll();
        customerRepository.save(new CustomerEntity("1", "Sammandamourthy", "Suriya", "none", "suriya", "toto", "Undefined"));
        customerRepository.save(new CustomerEntity("2", "Nguyen", "Maxime", "none", "maxime", "toto", "Undefined"));
        customerRepository.save(new CustomerEntity("3", "toto", "tata", "none", "toto", "toto", "Undefined"));
    }
}
