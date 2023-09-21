package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.ICustomMongoDBImpl;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMongo;

@ExtendWith(MockitoExtension.class)
public class MongoServicesTest {
	
	@Mock
	private IPlayerMongoDB playerMongo;
	
	@Mock
	private ICustomMongoDBImpl customPlayer;
	
	@InjectMocks
	private PlayerServicesMongo playerServices;
	
	
	//Test para la capa de Servicios, chequeo que guarda correctamente un nuevo jugador
	@Test
	public void newPlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();
		
		when(playerMongo.save(Mockito.any(PlayerMongoDB.class))).thenReturn(player);
		
		PlayerMongoDTO savedPlayer = playerServices.addNewPlayer(1, "Roberto", "24-03-1986");
		
		Assertions.assertThat(savedPlayer).isNotNull();
		Assertions.assertThat(savedPlayer.getPlayerName()).isEqualTo("Roberto");		
	}
	
	
	//Test para la capa de Servicios, chequeo que obtenga una lista con los jugadores
	@Test
	public void getAllPlayersTest() {
		Date currentDate = new Date();
		currentDate.getTime();
		
		List<PlayerMongoDB> players = new ArrayList<PlayerMongoDB>();
		
		players.add(new PlayerMongoDB(1, "Pablo", currentDate));
		players.add(new PlayerMongoDB(2, "Carlos", currentDate));
		
		when(playerMongo.findAll()).thenReturn(players);
		
		Assertions.assertThat(playerServices.getAllPlayers()).isNotNull();
		Assertions.assertThat(playerServices.getAllPlayers().size()).isEqualTo(2);		
	}
	
	
	//Test para la capa de Servicios, chequea que obtiene un jugador
	@Test
	public void getOnePlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();
		
		when(customPlayer.getOnePlayer(1)).thenReturn(player);
		
		PlayerMongoDTO getedPlayer = playerServices.getOneByPlayerId(1);
		
		Assertions.assertThat(getedPlayer).isNotNull();
		Assertions.assertThat(getedPlayer.getPlayerName()).isEqualTo("Roberto");		
	}
	
	
	//Test para la capa de Servicios, chequea que cambia correctamente el nombre del jugador
	@Test
	public void updateNameTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();
		
		when(playerMongo.save(Mockito.any(PlayerMongoDB.class))).thenReturn(player);
		
		PlayerMongoDTO updatedPlayer = playerServices.updatePlayerName(1, "Eduardo", currentDate);
		
		Assertions.assertThat(updatedPlayer).isNotNull();
		Assertions.assertThat(updatedPlayer.getPlayerName()).isEqualTo("Eduardo");		
	}
	
	
	//Test para la capa de Servicios, chequeo que elimine correctamente los DiceRolls 
	//de un jugador determinado
	@Test
	public void deleteDiceRollsTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		List<DiceRollMongoDB> diceRolls = new ArrayList<DiceRollMongoDB>();
		
		diceRolls.add(new DiceRollMongoDB(4, 3));
		diceRolls.add(new DiceRollMongoDB(4, 1));
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(44f)
				.diceRolls(diceRolls)
				.build();
		
		when(customPlayer.getOnePlayer(1)).thenReturn(player);
		
		assertAll(() -> playerServices.deleteDiceRolls(1));	
		Assertions.assertThat(player.getSuccessRate()).isEqualTo(0f);
	}


	//Test para la capa de Servicios, chequea que devuelva correctamente el ranking
	@Test
	public void getRankingTest() {
		
		List<PlayerMongoDB> players = new ArrayList<PlayerMongoDB>();
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMongoDB player1 = PlayerMongoDB.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(3f)
				.build();
					
		PlayerMongoDB player2 = PlayerMongoDB.builder()
				.playerName("Andrea")
				.registrationDate(currentDate)
				.successRate(22f)
				.build();
		
		players.add(player1);
		players.add(player2);
		
		when(customPlayer.getRanking()).thenReturn(players);
		when(playerMongo.findAll()).thenReturn(players);
		
		List<PlayerMongoDTO> rankingTest = playerServices.getRanking();
		
		Assertions.assertThat(rankingTest).isNotNull();
		Assertions.assertThat(rankingTest.size()).isEqualTo(2);	
		Assertions.assertThat(rankingTest.get(0).getSuccessRate()).isEqualTo(3f);	
	}
	
	
	//Test para la capa de Servicios, chequea que devuelva correctamente  
	//el jugador con peor Ranking
	@Test
	public void getLoserTest() {
		
		List<PlayerMongoDB> players = new ArrayList<PlayerMongoDB>();
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMongoDB player1 = PlayerMongoDB.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(3f)
				.build();
					
		PlayerMongoDB player2 = PlayerMongoDB.builder()
				.playerName("Andrea")
				.registrationDate(currentDate)
				.successRate(22f)
				.build();
		
		players.add(player1);
		players.add(player2);
		
		when(customPlayer.getLoser()).thenReturn(player1);
		
		PlayerMongoDTO loserPlayer = playerServices.getLoser();
		
		Assertions.assertThat(loserPlayer).isNotNull();
		Assertions.assertThat(loserPlayer.getPlayerName()).isEqualTo("Roberto");		
	}
	
	
	//Test para la capa de Servicios, chequea que devuelva correctamente 
	//el jugador con mejor Ranking
	@Test
	public void getWinnerTest() {
		
		List<PlayerMongoDB> players = new ArrayList<PlayerMongoDB>();
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMongoDB player1 = PlayerMongoDB.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(3f)
				.build();
					
		PlayerMongoDB player2 = PlayerMongoDB.builder()
				.playerName("Andrea")
				.registrationDate(currentDate)
				.successRate(22f)
				.build();
		
		players.add(player1);
		players.add(player2);
		
		when(customPlayer.getWinner()).thenReturn(player2);
		
		PlayerMongoDTO loserPlayer = playerServices.getWinner();
		
		Assertions.assertThat(loserPlayer).isNotNull();
		Assertions.assertThat(loserPlayer.getPlayerName()).isEqualTo("Andrea");
}
	
	
	//Test para la capa de Servicios, chequea que simule correctamente la jugada
	@Test
	public void playTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
					
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();	
		
		when(customPlayer.getOnePlayer(1)).thenReturn(player);
		
		DiceRollMongoDTO playedDiceRoll = playerServices.play(1, 4, 3);
		
		Assertions.assertThat(playedDiceRoll).isNotNull();
		Assertions.assertThat(playedDiceRoll.getWin()).isEqualTo(true);		
	}
}
