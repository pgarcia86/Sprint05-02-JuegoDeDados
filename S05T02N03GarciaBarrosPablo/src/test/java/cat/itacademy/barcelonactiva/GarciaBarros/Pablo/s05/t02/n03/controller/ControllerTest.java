package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMongo;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMySQL;

	
	@AutoConfigureMockMvc(addFilters = false)
	@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
	@ExtendWith(MockitoExtension.class)
	@SpringBootTest(properties = "spring.config.name=application-test")
	public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;
		
	@MockBean
	private PlayerServicesMySQL playerServiceMySQL;
		
	@MockBean
	private PlayerServicesMongo playerServiceMongo;
	
	@MockBean 
	private IPlayerMySQL iPlayerMySQL;
	
	@MockBean
	private IPlayerMongoDB iPlayerMongoDB;
		
	@Autowired
	private ObjectMapper objectMapper;
		
	private PlayerMySQL playerMySQL1;
	private PlayerMongoDB playerMongoDB;

	@BeforeEach
	public void setUp() {
		playerMySQL1 = new PlayerMySQL("Esteban", "13-10-1944");

	}

	@Test
	public void savePlayerMySQLTest() throws Exception { 
		
	    given(playerServiceMySQL.add(eq(playerMySQL1.getPlayerName()), anyString()))
	    	.willReturn(playerMySQL1); 
			    
		ResultActions response = mockMvc.perform(post("/players/{name}/{date}", playerMySQL1.getPlayerName(), "13-10-1944")
			.contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(playerMySQL1)));
		    
	    response.andExpect(MockMvcResultMatchers.status().isCreated())
	        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(playerMySQL1)));	    
	}		
	

}

