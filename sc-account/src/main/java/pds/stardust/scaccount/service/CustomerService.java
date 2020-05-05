package pds.stardust.scaccount.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.stardust.scaccount.controller.CustomerController;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.exception.ConstantException;
import pds.stardust.scaccount.exception.CustomException;
import pds.stardust.scaccount.repository.CustomerRepository;

/**
 * CustomerService : handles data persistence manipulation
 */
@Service
public class CustomerService implements ICustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Try to authenticate user according to received information
     *
     * @param customer JSON-described customer login and password
     * @return customer Entity if authentication is successful
     * @throws CustomException if authentication fails
     */
    public CustomerEntity connect(CustomerEntity customer) {
        CustomerEntity customerEntity = findByUsername(customer.getUsername());
        if (customerEntity == null) {
            logger.error("/connect - Bad username.");
            throw ConstantException.CONNECT_AUTH_FAILURE;
        } else if (!customerEntity.getPassword().equals(customer.getPassword())) {
            logger.error("/connect - Bad password.");
            throw ConstantException.CONNECT_AUTH_FAILURE;
        } else {
            logger.trace("/connect - Correct username and password");
        }
        return customerEntity;
    }

    /**
     * Update a customer's token according given information
     *
     * @param customer JSON described customer's info : id, login, password, token
     * @return updated customerEntity
     * @throws CustomException if pre authentication fails
     */
    public CustomerEntity updateToken(CustomerEntity customer) {
        CustomerEntity customerEntity = getById(customer.getId());
        if (customerEntity == null) {
            logger.error("/update/token - Bad customer ID.");
            throw ConstantException.UPDATE_BAD_ID;
        } else if (!customerEntity.getUsername().equals(customer.getUsername())) {
            logger.error("/update/token - Bad username.");
            throw ConstantException.UPDATE_AUTH_FAILURE;
        } else if (!customerEntity.getPassword().equals(customer.getPassword())) {
            logger.error("/update/token - Bad password.");
            throw ConstantException.UPDATE_AUTH_FAILURE;
        } else {
            customerEntity.setToken(customer.getToken());
            logger.trace("/update/token - Customer's token updated [user : " + customer.getUsername() + " , token : " + customer.getToken() + "]");
        }
        return saveCustomer(customerEntity);
    }

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
    public CustomerEntity getById(Integer id) {
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
        logger.trace("Delete all customers successfully");
        customerRepository.save(new CustomerEntity(1, "Sammandamourthy", "Suriya", "none", "suriya", "pds", "none"));
        customerRepository.save(new CustomerEntity(2, "Nguyen", "Maxime", "none", "maxime", "pds", "none"));
        customerRepository.save(new CustomerEntity(3, "Lebon", "Samuel", "none", "samuel", "pds", "none"));
        customerRepository.save(new CustomerEntity(4, "Mezned", "Alexandre", "none", "alex", "pds", "none"));
        customerRepository.save(new CustomerEntity(5, "Ramanantsoa", "Jessica", "none", "jessica", "pds", "none"));
        customerRepository.save(new CustomerEntity(6, "Faddaoui", "Ilies", "none", "ilies", "pds", "none"));
        customerRepository.save(new CustomerEntity(7, "Baraton", "Damien", "none", "damien", "pds", "none"));
        customerRepository.save(new CustomerEntity(8, "Lobiyed", "Karim", "none", "karim", "pds", "none"));
        customerRepository.save(new CustomerEntity(9, "pds", "pds", "none", "pds", "pds", "none"));
        logger.trace("Save some customers successfully");
    }
}
