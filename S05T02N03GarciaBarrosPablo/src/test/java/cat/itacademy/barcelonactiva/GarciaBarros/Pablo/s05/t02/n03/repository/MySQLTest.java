package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.*;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DataJpaTest
public class MySQLTest {
	
	@Autowired
	private IPlayerMySQL iPlayerMySQL;
	
	
	@Autowired
	private IDiceRollMySQL iDiceRollMySQL;	

	
	//Cargo jugadores en la base de datos de prueba antes de cada test
	@BeforeEach
	public void loadPlayerDiceRoll() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMySQL player1 = PlayerMySQL.builder()
				.playerName("Esteban")
				.registrationDate(currentDate)
				.build();
		
		PlayerMySQL player2 = PlayerMySQL.builder()
				.playerName("Roberto")
				.registrationDate(currentDate)
				.build();
		
		PlayerMySQL player3 = PlayerMySQL.builder()
				.playerName("Andrea")
				.registrationDate(currentDate)
				.build();
		
		PlayerMySQL player4 = PlayerMySQL.builder()
				.playerName("Carmina")
				.registrationDate(currentDate)
				.build();
		
		iPlayerMySQL.save(player1);
		iPlayerMySQL.save(player2);
		iPlayerMySQL.save(player3);
		iPlayerMySQL.save(player4);
		
		//Jugador 1
		DiceRollMySQL diceRoll1 = DiceRollMySQL.builder()
								.idPlayer(player1.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMySQL.save(diceRoll1);

		//Jugador2
		DiceRollMySQL diceRoll2 = DiceRollMySQL.builder()
								.idPlayer(player2.getIdPlayer())
								.firstRoll(4)
								.secondRoll(2)
								.win(false)
								.build();

		iDiceRollMySQL.save(diceRoll2);

		DiceRollMySQL diceRoll3 = DiceRollMySQL.builder()
								.idPlayer(player2.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMySQL.save(diceRoll3);
		
		
		//Jugador3
		DiceRollMySQL diceRoll4 = DiceRollMySQL.builder()
								.idPlayer(player3.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMySQL.save(diceRoll4);

		DiceRollMySQL diceRoll5 = DiceRollMySQL.builder()
								.idPlayer(player3.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMySQL.save(diceRoll5);

		DiceRollMySQL diceRoll6 = DiceRollMySQL.builder()
								.idPlayer(player3.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMySQL.save(diceRoll6);
		
		
		//Jugador4
		DiceRollMySQL diceRoll7 = DiceRollMySQL.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMySQL.save(diceRoll7);

		DiceRollMySQL diceRoll8 = DiceRollMySQL.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMySQL.save(diceRoll8);

		DiceRollMySQL diceRoll9 = DiceRollMySQL.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMySQL.save(diceRoll9);
		
		DiceRollMySQL diceRoll10 = DiceRollMySQL.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMySQL.save(diceRoll10);
	}
	
	
	//Elimino los jugadores y las tiradas de dados despues de cada test
	@AfterEach
	public void deletePlayers() {
		
		iPlayerMySQL.deleteAll();
		iDiceRollMySQL.deleteAll();
	}

	
	//Test para guardar nuevo jugador
	@Test
	public void newPlayerTest() {	
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMySQL player = PlayerMySQL.builder()
				.playerName("Anabella")
				.registrationDate(currentDate)
				.build();
		
		PlayerMySQL savedPlayer = iPlayerMySQL.save(player);
		
		assertThat(savedPlayer).isNotNull();
		assertThat(savedPlayer.getIdPlayer()).isGreaterThan(0);
		assertThat(savedPlayer.getPlayerName()).isEqualTo("Anabella");				
	}	
		
	
	//Test para obtener un jugador
	@Test
	public void getOnePlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMySQL player1 = PlayerMySQL.builder()
				.playerName("Anabella")
				.registrationDate(currentDate)
				.build();
		
		PlayerMySQL player2 = PlayerMySQL.builder()
				.playerName("Carolina")
				.registrationDate(currentDate)
				.build();
		
		iPlayerMySQL.save(player1);
		iPlayerMySQL.save(player2);
			
		PlayerMySQL getPlayer = iPlayerMySQL.getReferenceById(player1.getIdPlayer());
		
		assertThat(getPlayer).isNotNull();
		assertThat(getPlayer.getPlayerName()).isEqualTo("Anabella");
	}
	
	
	//Test para obtener todos los jugadores
	@Test
	public void getAllPlayersTest() {
		
		List<PlayerMySQL> playerList = iPlayerMySQL.findAll();
		
		assertThat(playerList).isNotEmpty();
		assertThat(playerList.size()).isEqualTo(4);		
	}
	
		
	//Test para guardar una nueva tiradad de dados
	@Test
	public void newDiceRollTest() {
		
		DiceRollMySQL diceRollMySQL = DiceRollMySQL.builder()
										.idPlayer(1)
										.firstRoll(4)
										.secondRoll(3)
										.win(true)
										.build();
		
		DiceRollMySQL diceRollSaved = iDiceRollMySQL.save(diceRollMySQL);
		
		assertThat(diceRollSaved.getIdGame()).isGreaterThan(0);
		assertThat(diceRollSaved).isNotNull();
	}
	
	
	//Test para eliminar las tiradas de dados
	@Test
	public void deleteDiceRollsTest() {
		
		List<PlayerMySQL> playerList = iPlayerMySQL.findAll();		
		Integer id = playerList.get(0).getIdPlayer();				
		iDiceRollMySQL.deleteAll(id);
						
		assertThat(iDiceRollMySQL.existsById(id)).isFalse();		
	}
	
	
	//Test para obtener el ranking de jugadores
	@Test
	public void testGetRanking() {
		
		List<PlayerMySQL> getRanking = iPlayerMySQL.getRanking();
		
		assertThat(getRanking).isNotNull();
		assertThat(getRanking.size()).isEqualTo(4);
		assertThat(getRanking).isNotEmpty();		
	}
	
	
	//Test para obtener el jugador con peor ranking
	@Test
	public void getLoserTest() {
		
		List<PlayerMySQL> getLoser = iPlayerMySQL.getLoser();
		
		assertThat(getLoser.size()).isEqualTo(1);
		assertThat(getLoser.get(0).getPlayerName()).isEqualTo("Carmina");	
	}
	
	
	//Test para obtener el jugador con mejor ranking
	@Test
	public void getWinnerTest() {
		
		List<PlayerMySQL> getWinner = iPlayerMySQL.getWinner();
		
		assertThat(getWinner.size()).isEqualTo(1);
		assertThat(getWinner.get(0).getPlayerName()).isEqualTo("Esteban");		
	}	


	//Test para cambiar el nombre de un jugador
	@Test
	public void updateNameTest() {
		
		List<PlayerMySQL> playerList = iPlayerMySQL.findAll();
		
		PlayerMySQL player = playerList.get(0);
		
		player.setPlayerName("Romina");
		
		PlayerMySQL savedPlayer = iPlayerMySQL.save(player);
		
		assertThat(savedPlayer).isNotNull();
		assertThat(savedPlayer.getPlayerName()).isEqualTo("Romina");		
	}
}
