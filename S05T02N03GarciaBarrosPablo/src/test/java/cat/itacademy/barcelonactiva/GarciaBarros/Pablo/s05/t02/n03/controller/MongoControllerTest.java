package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.MongoDB.DiceGameControllerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMongo;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMySQL;

@WebMvcTest(controllers = DiceGameControllerMongoDB.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MongoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlayerServicesMySQL playerServicesMySQL;
	
	@MockBean
	private PlayerServicesMongo playerServices;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private PlayerMongoDB player;
	private PlayerMongoDTO playerDTO;
	private PlayerMySQLDTO playerMySQLDTO;
	
	
	@BeforeEach
	public void init() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		player = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();
		
		playerDTO = PlayerMongoDTO.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();				
	}
	
	
	//Test para la capa de Controlador, chequeo que crea correctamente un Player
	@Test
	public void addOneTest() throws Exception{
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		playerMySQLDTO = PlayerMySQLDTO.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();
		
		given(playerServicesMySQL.addNewPlayer("Roberto", "24-03-1986"))
				.willReturn(playerMySQLDTO);
		
		given(playerServices.addNewPlayer(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
				.willReturn(playerDTO);
		
		ResultActions response = mockMvc.perform(post("/playersMongo/Roberto/24-03-1986")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(playerDTO)));
		
        response.andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.playerName", CoreMatchers.is(playerDTO.getPlayerName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(playerDTO.getIdPlayer())));
	}
	
	
	//Test para la capa de Controlador, chequeo que devuelva una lista de Players
	@Test
	public void getAllTest() throws Exception{
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		List<PlayerMongoDTO> players = new ArrayList<PlayerMongoDTO>();
		
		players.add(new PlayerMongoDTO(1, "Roberto", currentDate, 0f));
		players.add(new PlayerMongoDTO(2, "Andrea", currentDate, 0f));
		
		when(playerServices.getAllPlayers()).thenReturn(players);
		
        ResultActions response = mockMvc.perform(get("/playersMongo")
                .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(players.size())));		
	}
	
	
	//Test para la capa de Controlador, chequeo que devuelva un único Player
	@Test
	public void getOneTest() throws Exception{
		
        when(playerServices.getOneByPlayerId(1)).thenReturn(playerDTO);

        ResultActions response = mockMvc.perform(get("/playersMongo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerName", CoreMatchers.is(playerDTO.getPlayerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(playerDTO.getIdPlayer())));
	}
	
	
	//Test para la capa de Controlador, chequeo que devuelva correctamente una lista de Players
	@Test
	public void getRankingTest() throws Exception{
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		List<PlayerMongoDTO> players = new ArrayList<PlayerMongoDTO>();
		
		players.add(new PlayerMongoDTO(1, "Roberto", currentDate, 0f));
		players.add(new PlayerMongoDTO(2, "Andrea", currentDate, 0f));
		
		when(playerServices.getRanking()).thenReturn(players);
		
        ResultActions response = mockMvc.perform(get("/playersMongo/ranking")
                .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(players.size())));		
	}
	
	
	//Test para la capa de Controlador, chequeo que devuelva el Player con peor ranking
	@Test
	public void getLoserTest() throws Exception{
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		List<PlayerMongoDTO> players = new ArrayList<PlayerMongoDTO>();
		
		players.add(new PlayerMongoDTO(1, "Roberto", currentDate, 10f));
		players.add(new PlayerMongoDTO(2, "Andrea", currentDate, 50f));
		
		when(playerServices.getLoser()).thenReturn(players.get(0));
		
        ResultActions response = mockMvc.perform(get("/playersMongo/ranking/loser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDTO)));
		
        response.andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$.playerName", CoreMatchers.is(players.get(0).getPlayerName())))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(players.get(0).getIdPlayer())));
	}
	
	
	//Test para la capa de Controlador, chequeo que devuelva el Player con mejor ranking
	@Test
	public void getWinnerTest() throws Exception{
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		List<PlayerMongoDTO> players = new ArrayList<PlayerMongoDTO>();
		
		players.add(new PlayerMongoDTO(1, "Roberto", currentDate, 10f));
		players.add(new PlayerMongoDTO(2, "Andrea", currentDate, 50f));
		
		when(playerServices.getWinner()).thenReturn(players.get(1));
		
        ResultActions response = mockMvc.perform(get("/playersMongo/ranking/winner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDTO)));
		
        response.andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$.playerName", CoreMatchers.is(players.get(1).getPlayerName())))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(players.get(1).getIdPlayer())));		
	}
	
	
	//Test para la capa de Controlador, chequeo que simule correctamente el DiceRoll
	@Test
	public void playTest() throws Exception{
		
		DiceRollMongoDTO diceRoll = DiceRollMongoDTO.builder()
				.idPlayer(1)
				.firstRoll(4)
				.secondRoll(3)
				.win(true)
				.build();
				
		when(playerServices.play(1)).thenReturn(diceRoll);
		
		ResultActions response = mockMvc.perform(post("/playersMongo/1/newGame")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(diceRoll)));    
        
        response.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(diceRoll.getIdPlayer())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.win", CoreMatchers.is(diceRoll.getWin())));			
	}
	
	
	//Test para la capa de Controlador, chequeo que actualice el nombre del Jugador
	@Test
	public void updateNameTest() throws Exception{
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		playerMySQLDTO = PlayerMySQLDTO.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();
		
		when(playerServicesMySQL.getOneByPlayerId(1)).thenReturn(playerMySQLDTO);
        when(playerServices.updatePlayerName(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(playerDTO);

        ResultActions response = mockMvc.perform(put("/playersMongo/1/Carlos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerName", CoreMatchers.is(playerDTO.getPlayerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(playerDTO.getIdPlayer())));
		
		
	}
	
	
	//Test para la capa de Controlador, chequeo que elimine todas las tiradas
	@Test
	public void deleteGameTest() throws Exception{
		
        doNothing().when(playerServices).deleteDiceRolls(1);

        ResultActions response = mockMvc.perform(delete("/playersMongo/1/games")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
