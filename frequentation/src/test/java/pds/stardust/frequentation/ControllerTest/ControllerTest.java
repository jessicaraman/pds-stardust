package pds.stardust.frequentation.ControllerTest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pds.stardust.frequentation.Controller.DataController;
import pds.stardust.frequentation.Model.Data;
import pds.stardust.frequentation.Repository.DataRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ControllerTest {

    @Autowired
    DataController dc ;
    @MockBean
    DataRepository dt ;


  //  @Test public void testAddData () {
     // Data data = new Data(1,"jj2","10/10/2020") ;
      // dt.save(data) ;
     //  assertEquals("Client ajouté avec l'id : 1", dc.saveDATA(data));

  // }


}
