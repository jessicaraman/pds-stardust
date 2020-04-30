package pds.stardust.scaccount.service;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

/**
 * Class CustomerServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository ;

    @InjectMocks
    CustomerService customerService = new CustomerService();

    CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        this.initData();
        this.initMockito();
    }

    private void initMockito() {
        // init annotations
        MockitoAnnotations.initMocks(this);
        // define behavior
        Mockito.when(this.customerRepository.findByUsername("suriya")).thenReturn(customerEntity);
        Mockito.when(this.customerRepository.getById("1")).thenReturn(customerEntity);
        Mockito.when(this.customerRepository.save(customerEntity)).thenReturn(customerEntity);
    }

    private void initData() {
        customerEntity = new CustomerEntity("1", "suriya", "suriya", "not defined", "lol", "tata", "no token");
    }

    @Test
    void testFindByUsername() {
        assertEquals(customerEntity, this.customerService.findByUsername("suriya"));
        Mockito.verify(this.customerRepository, times(1)).findByUsername("suriya");
    }

    @Test
    void testGetById() {
        assertEquals(customerEntity, this.customerService.getById("1"));
        Mockito.verify(this.customerRepository, times(1)).getById("1");
    }

    @Test
    void testSaveCustomer() {
        assertEquals(customerEntity, this.customerService.saveCustomer(customerEntity));
        Mockito.verify(this.customerRepository, times(1)).save(customerEntity);
    }

    @Test
    void testInitCustomerData() {
        this.customerService.initCustomerData();
        Mockito.verify(this.customerRepository, times(9)).save(Mockito.any());
        Mockito.verify(this.customerRepository, times(1)).deleteAll();
    }
}