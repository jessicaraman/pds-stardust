package pds.stardust.scaccount.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.exception.CustomException;
import pds.stardust.scaccount.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomerController : account api
 */
@RestController
public class CustomerController {

    private CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

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
        logger.trace("Client requested heartbeat / : API account works correctly");
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
    @ApiOperation(value = "Authenticate a user according to his information", notes = "Provide an username and password", response = CustomerEntity.class)
    public CustomerEntity connect(@RequestBody CustomerEntity customer) {
        return customerService.connect(customer);
    }

    /**
     * Update Token Endpoint : update a customer's token according given information
     *
     * @param customer JSON described customer's info : id, login, password, token
     * @return updated customerEntity
     * @throws CustomException if pre authentication fails
     */
    @PostMapping(value = "/update/token", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Update a customer's token", notes = "Provide id, username and password's customer", response = CustomerEntity.class)
    public CustomerEntity updateToken(@RequestBody CustomerEntity customer) {
       return customerService.updateToken(customer);
    }
}
