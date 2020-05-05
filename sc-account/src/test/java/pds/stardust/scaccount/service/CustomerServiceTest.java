package pds.stardust.scaccount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pds.stardust.scaccount.entity.CustomerEntity;
import pds.stardust.scaccount.exception.ConstantException;
import pds.stardust.scaccount.exception.CustomException;
import pds.stardust.scaccount.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

/**
 * CustomerServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService = new CustomerService();

    CustomerEntity customerEntity;
    CustomerEntity badPasswordCustomerEntity;
    CustomerEntity updatedTokenEntity;

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
        Mockito.when(this.customerRepository.findByUsername("wrong_password")).thenReturn(badPasswordCustomerEntity);
        Mockito.when(this.customerRepository.getById(1)).thenReturn(customerEntity);
        Mockito.when(this.customerRepository.save(customerEntity)).thenReturn(customerEntity);
        Mockito.when(this.customerRepository.save(updatedTokenEntity)).thenReturn(updatedTokenEntity);
    }

    private void initData() {
        customerEntity = new CustomerEntity(1, "suriya", "suriya", "not defined", "suriya", "tata", "no token");
        customerEntity = new CustomerEntity(1, "suriya", "suriya", "not defined", "suriya", "tata", "no token");
        badPasswordCustomerEntity = new CustomerEntity(324, "wrong_password", "wrong_password", "undefined", "myusername", "password", "undefined");
        updatedTokenEntity = new CustomerEntity(1, "suriya", "suriya", "not defined", "suriya", "tata", "updatedToken");
    }

    // ***************
    // connect tests :
    // ***************
    @Test
    void connectNullCredentialsTest() {
        CustomerEntity wrongCustomer = new CustomerEntity(324, "myname", "mysurname", "myimage", "wrong_username", "wrong_password", "undefined");
        try {
            this.customerService.connect(wrongCustomer);
        } catch (CustomException exception) {
            assertEquals(exception, ConstantException.CONNECT_AUTH_FAILURE);
        }
        Mockito.verify(this.customerRepository, times(1)).findByUsername("wrong_username");
    }

    @Test
    void connectBadPasswordTest() {
        CustomerEntity wrongCustomer = new CustomerEntity(324, "wrong_password", "wrong_password", "undefined", "wrong_password", "wrong_password", "undefined");
        try {
            this.customerService.connect(wrongCustomer);
        } catch (CustomException exception) {
            assertEquals(exception, ConstantException.CONNECT_AUTH_FAILURE);
        }
        Mockito.verify(this.customerRepository, times(1)).findByUsername("wrong_password");
    }

    @Test
    void connectSuccessfulTest() {
        assertEquals(customerEntity, customerService.connect(customerEntity));
        Mockito.verify(this.customerRepository, times(1)).findByUsername("suriya");
    }

    // ******************
    // updateTokenTests :
    // ******************
    @Test
    void updateTokenNullCredentialsTest() {
        CustomerEntity nullEntity = new CustomerEntity(-1, "undefined", "undefined", "undefined", "undefined", "undefined", "undefined");
        try {
            this.customerService.updateToken(nullEntity);
        } catch (CustomException exception) {
            assertEquals(exception, ConstantException.UPDATE_BAD_ID);
        }
        Mockito.verify(this.customerRepository, times(1)).getById(-1);
    }

    @Test
    void updateTokenBadUsernameTest() {
        CustomerEntity badUsernameEntity = new CustomerEntity(1, "bad_username", "bad_username", "not defined", "lol", "tata", "no token");
        try {
            this.customerService.updateToken(badUsernameEntity);
        } catch (CustomException exception) {
            assertEquals(exception, ConstantException.UPDATE_AUTH_FAILURE);
        }
        Mockito.verify(this.customerRepository, times(1)).getById(1);
    }

    @Test
    void updateTokenBadPasswordTest() {
        CustomerEntity badPasswordEntity = new CustomerEntity(1, "suriya", "suriya", "not defined", "lol", "bad_password", "no token");
        try {
            this.customerService.updateToken(badPasswordEntity);
        } catch (CustomException exception) {
            assertEquals(exception, ConstantException.UPDATE_AUTH_FAILURE);
        }
        Mockito.verify(this.customerRepository, times(1)).getById(1);
    }

    @Test
    void updateTokenSuccessfulTest() {
        assertEquals(updatedTokenEntity.toString(), this.customerService.updateToken(updatedTokenEntity).toString());
        Mockito.verify(this.customerRepository, times(1)).getById(1);
        Mockito.verify(this.customerRepository, times(1)).save(customerEntity);
    }

    // ************
    // CRUD tests :
    // ************
    @Test
    void testFindByUsername() {
        assertEquals(customerEntity, this.customerService.findByUsername("suriya"));
        Mockito.verify(this.customerRepository, times(1)).findByUsername("suriya");
    }

    @Test
    void testGetById() {
        assertEquals(customerEntity, this.customerService.getById(1));
        Mockito.verify(this.customerRepository, times(1)).getById(1);
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