package pds.stardust.scaccount.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.service.CustomerService;

/**
 * Customer Controller
 */
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = {"/"})
    String index() { return "API account"; }

    @PostMapping(value = "/connect", consumes = "application/json", produces = "application/json")
    public CustomerEntity connect (@RequestBody CustomerEntity customer) {
        CustomerEntity customerEntity = customerService.findByUsername(customer.getUsername());
        if (customerEntity != null && customerEntity.getPassword().equals(customer.getPassword())) {
            return customerEntity;
        } else {
            return new CustomerEntity();
        }
    }

    @PostMapping(value = "/update/token", consumes = "application/json", produces = "application/json")
    public CustomerEntity updateToken (@RequestBody CustomerEntity customer)  {
        CustomerEntity customerEntity = customerService.getById(customer.getId());
        if (customerEntity.getUsername().equals(customer.getUsername()) && customerEntity.getPassword().equals(customer.getPassword())) {
            customerEntity.setToken(customer.getToken());
            return customerService.saveCustomer(customerEntity);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
