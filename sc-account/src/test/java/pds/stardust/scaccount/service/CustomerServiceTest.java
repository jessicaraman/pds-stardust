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
import pds.stardust.scaccount.exception.Constant;
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
    CustomerEntity badIdCustomerEntity;
    CustomerEntity badUsernameCustomerEntity;
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
        // Those are actually useless because returning null is the default Mockito behavior
        // But we prefer mentioning it anyway for the sick of tests understanding
        Mockito.when(this.customerRepository.findByUsername("wrong_username")).thenReturn(null);
        Mockito.when(this.customerRepository.getById(-1)).thenReturn(null);

    }

    private void initData() {
        customerEntity = new CustomerEntity(1, "suriya", "suriya", "undefined", "suriya", "tata", "no token");
        badIdCustomerEntity = new CustomerEntity(-1, "suriya", "suriya", "undefined", "suriya", "tata", "no token");
        badUsernameCustomerEntity = new CustomerEntity(1, "suriya", "suriya", "undefined", "wrong_username", "tata", "no token");
        badPasswordCustomerEntity = new CustomerEntity(1, "suriya", "suriya", "undefined", "suriya", "wrong_password", "no token");
        updatedTokenEntity = new CustomerEntity(1, "suriya", "suriya", "undefined", "suriya", "tata", "updatedToken");
    }

    // ***************
    // connect tests :
    // ***************
    @Test
    void connectNullEntityTest() {
        try {
            this.customerService.connect(badUsernameCustomerEntity);
        } catch (CustomException exception) {
            assertEquals(exception, Constant.CONNECT_AUTH_FAILURE);
        }
        Mockito.verify(this.customerRepository, times(1)).findByUsername("wrong_username");
    }

    @Test
    void connectBadPasswordTest() {
        try {
            this.customerService.connect(badPasswordCustomerEntity);
        } catch (CustomException exception) {
            assertEquals(exception, Constant.CONNECT_AUTH_FAILURE);
        }
        Mockito.verify(this.customerRepository, times(1)).findByUsername("suriya");
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
    void updateTokenNullEntityTest() {
        try {
            this.customerService.updateToken(badIdCustomerEntity);
        } catch (CustomException exception) {
            assertEquals(exception, Constant.UPDATE_BAD_ID);
        }
        Mockito.verify(this.customerRepository, times(1)).getById(-1);
    }

    @Test
    void updateTokenBadUsernameTest() {
        try {
            this.customerService.updateToken(badUsernameCustomerEntity);
        } catch (CustomException exception) {
            assertEquals(exception, Constant.UPDATE_AUTH_FAILURE);
        }
        Mockito.verify(this.customerRepository, times(1)).getById(1);
    }

    @Test
    void updateTokenBadPasswordTest() {
        try {
            this.customerService.updateToken(badPasswordCustomerEntity);
        } catch (CustomException exception) {
            assertEquals(exception, Constant.UPDATE_AUTH_FAILURE);
       }
        Mockito.verify(this.customerRepository, times(1)).getById(1);
    }

    @Test
    void updateTokenSuccessfulTest() {
        assertEquals(updatedTokenEntity.toString(), this.customerService.updateToken(updatedTokenEntity).toString());
        Mockito.verify(this.customerRepository, times(1)).getById(1);
        Mockito.verify(this.customerRepository, times(1)).save(customerEntity);
    }


    // *****************
    // get token tests :
    // *****************
    @Test
    void nullEntityGetCustomerTokenByUsernameTest() {
        try {
            this.customerService.getCustomerTokenByUsername(badUsernameCustomerEntity);
        } catch(CustomException exception) {
            assertEquals(Constant.GET_TOKEN_BAD_USERNAME, exception);
        }
        Mockito.verify(this.customerRepository, times(1)).findByUsername("wrong_username");
    }

    @Test
    void successfulGetCustomerTokenByUsernameTest() {
        String token = this.customerService.getCustomerTokenByUsername(customerEntity);
        assertEquals("no token", token);
        Mockito.verify(this.customerRepository, times(1)).findByUsername("suriya");
    }

    // ************
    // CRUD tests :
    // ************
    @Test
    void findByUsernameTest() {
        assertEquals(customerEntity, this.customerService.findByUsername("suriya"));
        Mockito.verify(this.customerRepository, times(1)).findByUsername("suriya");
    }

    @Test
    void getByIdTest() {
        assertEquals(customerEntity, this.customerService.getById(1));
        Mockito.verify(this.customerRepository, times(1)).getById(1);
    }

    @Test
    void saveCustomerTest() {
        assertEquals(customerEntity, this.customerService.saveCustomer(customerEntity));
        Mockito.verify(this.customerRepository, times(1)).save(customerEntity);
    }

    @Test
    void initCustomerDataTest() {
        this.customerService.initCustomerData();
        Mockito.verify(this.customerRepository, times(9)).save(Mockito.any());
        Mockito.verify(this.customerRepository, times(1)).deleteAll();
    }
}