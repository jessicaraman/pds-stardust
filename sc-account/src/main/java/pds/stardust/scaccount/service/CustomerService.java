package pds.stardust.scaccount.service;

import org.springframework.stereotype.Service;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.repository.CustomerRepository;

@Service
public class CustomerService implements ICustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public CustomerEntity saveCustomer (CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    public void initCustomerData () {
        customerRepository.deleteAll();
        customerRepository.save(new CustomerEntity(1, "Sammandamourthy", "Suriya", "none", "suriya", "toto"));
        customerRepository.save(new CustomerEntity(2, "Nguyen", "Maxime", "none", "maxime", "toto"));
        customerRepository.save(new CustomerEntity(3, "toto", "tata", "none", "toto", "toto"));
    }
}
