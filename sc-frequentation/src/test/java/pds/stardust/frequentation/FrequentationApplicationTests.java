package pds.stardust.frequentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pds.stardust.frequentation.Controller.DataController;
import pds.stardust.frequentation.Model.Data;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(DataController.class)

class FrequentationApplicationTests {


	@Autowired
	private MockMvc mvc;


	@Test
	public void saveDataTest() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.post("/addData")
				.content(asJsonString(new Data(66 , "Beacon1", "10/10/2020")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idclient").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.idbeacon").value("Beacon1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date").value("10/10/2020"));
	}

	@Test
	public void deleteDataTest() throws Exception {
		mvc.perform( MockMvcRequestBuilders.delete("/deleteData", 1) )
				.andExpect(status().isAccepted());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	


}
