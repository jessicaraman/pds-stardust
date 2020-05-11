package pds.stardust.frequentation.ControllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import pds.stardust.frequentation.Controller.DataController;
import pds.stardust.frequentation.Model.Data;
import pds.stardust.frequentation.Repository.DataRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ControllerTest {


    @InjectMocks
    Data data ;
    @Autowired
    DataController dc ;
    @MockBean
    DataRepository dt ;
    @Test public void testAddData () {
      Data data = new Data(1,"jj2","10/10/2020") ;
       dt.save(data) ;
       assertEquals("Client ajouté avec l'id : 1", dc.saveDATA(data));

   }


}
