package pds.stardust.scaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.repository.CustomerRepository;

/**
 * CustomerService : handles data persistence manipulation
 */
@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Get customer Entity related to the given username
     *
     * @param username
     * @return customerEntity
     */
    @Override
    public CustomerEntity findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    /**
     * Get customer Entity related to the given id
     *
     * @param id
     * @return customerEntity
     */
    @Override
    public CustomerEntity getById(String id) {
        return customerRepository.getById(id);
    }

    /**
     * Save customer
     *
     * @param customerEntity
     * @return customerEntity
     */
    @Override
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    /**
     * Mock data
     */
    @Override
    public void initCustomerData() {
        customerRepository.deleteAll();
        customerRepository.save(new CustomerEntity("1", "Sammandamourthy", "Suriya", "none", "suriya", "pds", "none"));
        customerRepository.save(new CustomerEntity("2", "Nguyen", "Maxime", "none", "maxime", "pds", "none"));
        customerRepository.save(new CustomerEntity("3", "Lebon", "Samuel", "none", "samuel", "pds", "none"));
        customerRepository.save(new CustomerEntity("4", "Mezned", "Alexandre", "none", "alex", "pds", "none"));
        customerRepository.save(new CustomerEntity("5", "Ramanantsoa", "Jessica", "none", "jessica", "pds", "none"));
        customerRepository.save(new CustomerEntity("6", "Faddaoui", "Ilies", "none", "ilies", "pds", "none"));
        customerRepository.save(new CustomerEntity("7", "Baraton", "Damien", "none", "damien", "pds", "none"));
        customerRepository.save(new CustomerEntity("8", "Lobiyed", "Karim", "none", "karim", "pds", "none"));
        customerRepository.save(new CustomerEntity("9", "pds", "pds", "none", "pds", "pds", "none"));
    }
}
