package pds.stardust.scaccount.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.exception.CustomException;
import pds.stardust.scaccount.service.CustomerService;

/**
 * CustomerController : account api
 */
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Heartbeat Endpoint, used to check service availability
     *
     * @return heartbeat message
     */
    @RequestMapping(value = {"/"})
    String index() {
        return "API account";
    }

    /**
     * Connect Endpoint, try to authenticate user according to received information
     *
     * @param customer JSON-described customer login and password
     * @return customer Entity if authentication is successful
     * @throws CustomException if authentication fails
     */
    @PostMapping(value = "/connect", consumes = "application/json", produces = "application/json")
    public CustomerEntity connect(@RequestBody CustomerEntity customer) throws CustomException {
        CustomerEntity customerEntity = customerService.findByUsername(customer.getUsername());
        if (customerEntity != null && customerEntity.getPassword().equals(customer.getPassword())) {
            return customerEntity;
        } else {
            throw new CustomException(1, "Authentication failed!", "Wrong username or password, try again.");
        }
    }

    /**
     * Update Token Endpoint : update a customer's token according given information
     *
     * @param customer JSON described customer's info : login, password, token
     * @return updated customerEntity
     * @throws CustomException if pre authentication fails
     */
    @PostMapping(value = "/update/token", consumes = "application/json", produces = "application/json")
    public CustomerEntity updateToken(@RequestBody CustomerEntity customer) throws CustomException {
        CustomerEntity customerEntity = customerService.getById(customer.getId());
        if (customerEntity.getUsername().equals(customer.getUsername()) && customerEntity.getPassword().equals(customer.getPassword())) {
            customerEntity.setToken(customer.getToken());
            return customerService.saveCustomer(customerEntity);
        } else {
            throw new CustomException(2, "Update Token failure!", "Pre authentication failed.");
        }
    }
}
