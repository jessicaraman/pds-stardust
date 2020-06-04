package pds.stardust.frequentation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pds.stardust.frequentation.Model.Data;
import pds.stardust.frequentation.Repository.DataRepository;
import pds.stardust.frequentation.Service.DataService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
class FrequentationApplicationTests {

	@Autowired
	private DataService service;

	@MockBean
	private DataRepository repository;




	@Test
	public void saveDataTest() {
		Data k = new Data(5, "JJA", "10/10/2020");
		when(repository.save(k)).thenReturn(k);
		Assertions.assertEquals(k ,service.addData(k));
	}

	@Test
	public void deleteDataTest() {
		Data k = new Data(376, "JJA", "10/10/2020");
		service.deletedata(k);
		verify(repository,times(1)).delete(k);
	}

}
