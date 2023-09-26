package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller;

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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.MySQL.DiceGameControllerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMongo;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMySQL;

@WebMvcTest(controllers = DiceGameControllerMySQL.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MySQLControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlayerServicesMySQL playerServices;
	
	@MockBean
	private PlayerServicesMongo playerServicesMongo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private PlayerMySQL player;
	private PlayerMySQLDTO playerDTO;
	
	@BeforeEach
	public void init() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		player = PlayerMySQL.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();
		
		playerDTO = PlayerMySQLDTO.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();				
	}
	
	//Test para la capa de Controlador, chequeo que crea correctamente un Player
	@Test
	public void addOneTest() throws Exception{
		
		given(playerServices.addNewPlayer(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.willReturn(playerDTO);
		
		ResultActions response = mockMvc.perform(post("/playersMySQL/Roberto/24-03-1986")
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
		
		List<PlayerMySQLDTO> players = new ArrayList<PlayerMySQLDTO>();
		
		players.add(new PlayerMySQLDTO(1, "Roberto", currentDate, 0f));
		players.add(new PlayerMySQLDTO(2, "Andrea", currentDate, 0f));
		
		when(playerServices.getAllPlayers()).thenReturn(players);
		
        ResultActions response = mockMvc.perform(get("/playersMySQL")
                .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(players.size())));		
	}
	
	
	//Test para la capa de Controlador, chequeo que devuelva un único Player
	@Test
	public void getOneTest() throws Exception{
		
        when(playerServices.getOneByPlayerId(1)).thenReturn(playerDTO);

        ResultActions response = mockMvc.perform(get("/playersMySQL/1")
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
		
		List<PlayerMySQLDTO> players = new ArrayList<PlayerMySQLDTO>();
		
		players.add(new PlayerMySQLDTO(1, "Roberto", currentDate, 0f));
		players.add(new PlayerMySQLDTO(2, "Andrea", currentDate, 0f));
		
		when(playerServices.getRanking()).thenReturn(players);
		
        ResultActions response = mockMvc.perform(get("/playersMySQL/ranking")
                .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(players.size())));		
	}

	
	//Test para la capa de Controlador, chequeo que devuelva el Player con peor ranking
	@Test
	public void getLoserTest() throws Exception{
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		List<PlayerMySQLDTO> players = new ArrayList<PlayerMySQLDTO>();
		
		players.add(new PlayerMySQLDTO(1, "Roberto", currentDate, 10f));
		players.add(new PlayerMySQLDTO(2, "Andrea", currentDate, 50f));
		
		when(playerServices.getLoser()).thenReturn(players.get(0));
		
        ResultActions response = mockMvc.perform(get("/playersMySQL/ranking/loser")
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
		
		List<PlayerMySQLDTO> players = new ArrayList<PlayerMySQLDTO>();
		
		players.add(new PlayerMySQLDTO(1, "Roberto", currentDate, 10f));
		players.add(new PlayerMySQLDTO(2, "Andrea", currentDate, 50f));
		
		when(playerServices.getWinner()).thenReturn(players.get(1));
		
        ResultActions response = mockMvc.perform(get("/playersMySQL/ranking/winner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDTO)));
		
        response.andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$.playerName", CoreMatchers.is(players.get(1).getPlayerName())))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(players.get(1).getIdPlayer())));		
	}


	//Test para la capa de Controlador, chequeo que simule correctamente el DiceRoll
	@Test
	public void playTest() throws Exception{
		
		DiceRollMySQLDTO diceRoll = DiceRollMySQLDTO.builder()
				.idGame(1)
				.idPlayer(1)
				.firstRoll(4)
				.secondRoll(3)
				.win(true)
				.build();
				
		when(playerServices.play(1)).thenReturn(diceRoll);
		
		ResultActions response = mockMvc.perform(post("/playersMySQL/1/newGame")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(diceRoll)));    
        
        response.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.idPlayer", CoreMatchers.is(diceRoll.getIdPlayer())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.win", CoreMatchers.is(diceRoll.getWin())));			
	}


	//Test para la capa de Controlador, chequeo que actualice el nombre del Jugador
	@Test
	public void updateNameTest() throws Exception{
		
		when(playerServices.getOneByPlayerId(1)).thenReturn(playerDTO);
        when(playerServices.updatePlayerName(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())).thenReturn(playerDTO);

        ResultActions response = mockMvc.perform(put("/playersMySQL/1/Carlos")
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

        ResultActions response = mockMvc.perform(delete("/playersMySQL/1/games")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
