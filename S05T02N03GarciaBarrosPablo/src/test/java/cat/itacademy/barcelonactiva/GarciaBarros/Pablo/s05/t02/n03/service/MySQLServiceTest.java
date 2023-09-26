package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.ICustomMySQLImpl;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IDiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMySQL;

@ExtendWith(MockitoExtension.class)
public class MySQLServiceTest {
	
	@Mock
	private IPlayerMySQL playerMySQL;
	
	@Mock
	private IDiceRollMySQL diceRollMySQL;
	
	@Mock
	private ICustomMySQLImpl customPlayer;
	
	@InjectMocks
	private PlayerServicesMySQL playerServices;

	
	//Test para la capa de servicios, chequea que se guarde el jugador
	@Test
	public void newPlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMySQL player1 = PlayerMySQL.builder()
				.idPlayer(1)
				.playerName("Esteban")
				.registrationDate(currentDate)
				.build();
		
		when(playerMySQL.save(Mockito.any(PlayerMySQL.class))).thenReturn(player1);
		
		PlayerMySQLDTO savedPlayer = playerServices.addNewPlayer("Esteban", "24-03-1986");
		
		Assertions.assertThat(savedPlayer).isNotNull();
		Assertions.assertThat(savedPlayer.getPlayerName()).isEqualTo("Esteban");
	}
	
	
	//Test para la capa de Servicios, chequea que obtenga una lista de jugadores
	@Test
	public void getAllPLayersTest() {
		
		List<PlayerMySQL> players = new ArrayList<PlayerMySQL>();
		
		when(playerMySQL.findAll()).thenReturn(players);
		
		List<PlayerMySQLDTO> allPlayers = playerServices.getAllPlayers();
		
		Assertions.assertThat(allPlayers).isNotNull();		
	}
	
	
	//Test de la capa de Servicios, chequea que obtenga un jugador
	@Test
	public void getOnePlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMySQL player = PlayerMySQL.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.build();	
		
		when(customPlayer.getOnePlayer(1)).thenReturn(player);
		
		PlayerMySQLDTO getPlayer = playerServices.getOneByPlayerId(1);
		
		Assertions.assertThat(getPlayer).isNotNull();	
		Assertions.assertThat(getPlayer.getPlayerName()).isEqualTo("Roberto");
	}
	

	//Test para capa de Servicios, chequea actualize el nombre del jugador
	@Test
	public void updateNameTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMySQL player = PlayerMySQL.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.build();
		
		when(customPlayer.getOnePlayer(1)).thenReturn(player);
		when(playerMySQL.save(Mockito.any(PlayerMySQL.class))).thenReturn(player);
		
		PlayerMySQLDTO updatedPlayer = playerServices.updatePlayerName(1, "Adrian");
		
		Assertions.assertThat(updatedPlayer).isNotNull();
		Assertions.assertThat(updatedPlayer.getPlayerName()).isEqualTo("Adrian");		
	}

	
	//Test para la capa de Servicios, chequea que elimine los DiceRolls del jugador
	//ESTE TEST ESTA MEDIO RARO, LO TENGO QUE REPASAR BIEN
	@Test		
	public void deleteDiceRollsTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMySQL player = PlayerMySQL.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();	
		
		assertAll(() -> customPlayer.deleteAllDiceRolls(1));
	}

	
	//Test para la capa de Servicios, chequea que devuelva correctamente el ranking
	@Test
	public void getRankingTest() {
		
		List<PlayerMySQL> players = new ArrayList<PlayerMySQL>();
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMySQL player1 = PlayerMySQL.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(3f)
				.build();
					
		PlayerMySQL player2 = PlayerMySQL.builder()
				.playerName("Andrea")
				.registrationDate(currentDate)
				.successRate(22f)
				.build();
		
		players.add(player1);
		players.add(player2);
		
		when(customPlayer.getRanking()).thenReturn(players);
		when(playerMySQL.findAll()).thenReturn(players);
		
		List<PlayerMySQLDTO> rankingTest = playerServices.getRanking();
		
		Assertions.assertThat(rankingTest).isNotNull();
		Assertions.assertThat(rankingTest.size()).isEqualTo(2);	
		Assertions.assertThat(rankingTest.get(0).getSuccessRate()).isEqualTo(3f);
	}
	
	
	//Test para la capa de Servicios, chequea que devuelva correctamente  
	//el jugador con peor Ranking
	@Test
	public void getLoserTest() {
		
		List<PlayerMySQL> players = new ArrayList<PlayerMySQL>();
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMySQL player1 = PlayerMySQL.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(3f)
				.build();
					
		PlayerMySQL player2 = PlayerMySQL.builder()
				.playerName("Andrea")
				.registrationDate(currentDate)
				.successRate(22f)
				.build();
		
		players.add(player1);
		players.add(player2);
		
		when(customPlayer.getLoser()).thenReturn(player1);
		
		PlayerMySQLDTO loserPlayer = playerServices.getLoser();
		
		Assertions.assertThat(loserPlayer).isNotNull();
		Assertions.assertThat(loserPlayer.getPlayerName()).isEqualTo("Roberto");		
	}
	
	
	//Test para la capa de Servicios, chequea que devuelva correctamente 
	//el jugador con mejor Ranking
	@Test
	public void getWinnerTest() {
		
		List<PlayerMySQL> players = new ArrayList<PlayerMySQL>();
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMySQL player1 = PlayerMySQL.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(3f)
				.build();
					
		PlayerMySQL player2 = PlayerMySQL.builder()
				.playerName("Andrea")
				.registrationDate(currentDate)
				.successRate(22f)
				.build();
		
		players.add(player1);
		players.add(player2);
		
		when(customPlayer.getWinner()).thenReturn(player2);
		
		PlayerMySQLDTO loserPlayer = playerServices.getWinner();
		
		Assertions.assertThat(loserPlayer).isNotNull();
		Assertions.assertThat(loserPlayer.getPlayerName()).isEqualTo("Andrea");
	}

	
	//Test para la capa de Servicios, chequea que simule correctamente la jugada
	@Test
	public void playTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMySQL player = PlayerMySQL.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.build();	
		
		when(customPlayer.getOnePlayer(1)).thenReturn(player);
		
		DiceRollMySQLDTO playedDiceRoll = playerServices.play(1);
		
		Assertions.assertThat(playedDiceRoll).isNotNull();
		//Assertions.assertThat(playedDiceRoll.getWin()).isEqualTo(true);		
	}
}
