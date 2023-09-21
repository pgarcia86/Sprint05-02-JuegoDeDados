package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBRepositoryTest {

	
	@Autowired
	private IPlayerMongoDB iPlayerMongo;
	
	
	@Autowired 	
	private ICustomMongoDBImpl implPlayerMongo;
	
	
	//Cargo jugadores en la base de datos de prueba antes de cada test
	@BeforeEach
	public void loadPlayerDiceRoll() {

		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player1 = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Esteban")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();
		iPlayerMongo.save(player1);
		
		PlayerMongoDB player2 = PlayerMongoDB.builder()
				.idPlayer(2)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();
		iPlayerMongo.save(player2);
		
		PlayerMongoDB player3 = PlayerMongoDB.builder()
				.idPlayer(3)
				.playerName("Andrea")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();
		iPlayerMongo.save(player3);
				
		PlayerMongoDB player4 = PlayerMongoDB.builder()
				.idPlayer(4)
				.playerName("Carmina")
				.registrationDate(currentDate)
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.build();	
		iPlayerMongo.save(player4);		
		
		player1.getDiceRolls().add(new DiceRollMongoDB(4,3));
		iPlayerMongo.save(player1);
		
		player2.getDiceRolls().add(new DiceRollMongoDB(4,1));
		player2.getDiceRolls().add(new DiceRollMongoDB(4,3));
		iPlayerMongo.save(player2);
		
		player3.getDiceRolls().add(new DiceRollMongoDB(4,1));
		player3.getDiceRolls().add(new DiceRollMongoDB(4,1));
		player3.getDiceRolls().add(new DiceRollMongoDB(4,3));
		iPlayerMongo.save(player3);
		
		player4.getDiceRolls().add(new DiceRollMongoDB(4,1));
		player4.getDiceRolls().add(new DiceRollMongoDB(4,1));
		player4.getDiceRolls().add(new DiceRollMongoDB(4,1));
		player4.getDiceRolls().add(new DiceRollMongoDB(4,3));
		iPlayerMongo.save(player4);		
	}
	
	
	//Elimino los jugadores y las tiradas de dados despues de cada test
	@AfterEach
	public void deletePlayers() {
		
		iPlayerMongo.deleteAll();
	}
	
	
	//Test para guardar un jugador nuevo
	@Test
	public void saveNewPlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(5)
				.playerName("Antonella")
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.registrationDate(currentDate).build();
		
		PlayerMongoDB playerSaved = iPlayerMongo.save(player);
		
		assertThat(playerSaved).isNotNull();
		assertThat(playerSaved.getIdPlayer()).isGreaterThan(0);
		assertThat(playerSaved.getPlayerName()).isEqualTo("Antonella");
	}	
	
	
	//Test para obtener un jugador
	@Test
	public void getOnePlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(6)
				.playerName("Romina")
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.registrationDate(currentDate).build();
		
		iPlayerMongo.save(player);		
		
		Optional<PlayerMongoDB> onePlayer = iPlayerMongo.findById(6);
		
		assertThat(onePlayer.get()).isNotNull();
		assertThat(onePlayer.get().getIdPlayer()).isEqualTo(6);
		assertThat(onePlayer.get().getPlayerName()).isEqualTo("Romina");		
	}	
	
	
	//Test para cambiar el nombre de un jugador
	@Test
	public void updateName() {
		
		Optional<PlayerMongoDB> updatePlayer = iPlayerMongo.findById(1);
		updatePlayer.get().setPlayerName("Samuel");
		PlayerMongoDB player = iPlayerMongo.save(updatePlayer.get());
		
		assertThat(player).isNotNull();
		assertThat(player.getIdPlayer()).isEqualTo(1);
		assertThat(player.getPlayerName()).isEqualTo("Samuel");
	}
	
	
	//Test para traer todos los jugadores
	@Test
	public void getAllPlayersTest() {
		
		List<PlayerMongoDB> allPlayers = iPlayerMongo.findAll();
		
		assertThat(allPlayers).isNotEmpty();
		assertThat(allPlayers.size()).isEqualTo(4);		
	}
	
	
	//Test para guardar una nueva tirada de dados
	@Test
	public void newDiceRollTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(7)
				.playerName("Alejandra")
				.successRate(0f)
				.diceRolls(new ArrayList<DiceRollMongoDB>())
				.registrationDate(currentDate).build();
		
		iPlayerMongo.save(player);
		
		player.getDiceRolls().add(new DiceRollMongoDB(4 ,3));
		
		iPlayerMongo.save(player);
		
		Optional<PlayerMongoDB> playerAux = iPlayerMongo.findById(7);
		
		assertThat(playerAux.get().getDiceRolls()).isNotEmpty();
		assertThat(playerAux.get().getDiceRolls().size()).isEqualTo(1);
		assertThat(playerAux.get().getDiceRolls().get(0).getWin()).isEqualTo(true);
	}
	
	
	//Test para eliminar las tiradas de dados
	@Test
	public void deleteDiceRollTest() {
		
		Optional<PlayerMongoDB> player = iPlayerMongo.findById(4);
		
		player.get().getDiceRolls().clear();
		
		iPlayerMongo.save(player.get());
		
		assertThat(player.get().getDiceRolls().size()).isEqualTo(0);
		assertThat(player.get().getDiceRolls()).isEmpty();		
	}
	
	
	//Test para obtener el rankking de los jugadores
	@Test
	public void getRankingTest() {
		List<PlayerMongoDB> ranking = implPlayerMongo.getRanking();
		
		assertThat(ranking).isNotEmpty();
		assertThat(ranking.size()).isEqualTo(4);
		assertThat(ranking.get(0).getPlayerName()).isEqualTo("Esteban");
	}
	
	
	//Test para obtener el jugador con mejor Ranking de tiradas
	@Test
	public void getWinnerTest() {
		
		PlayerMongoDB winner = implPlayerMongo.getWinner();
		
		assertThat(winner).isNotNull();		
		assertThat(winner.getPlayerName()).isEqualTo("Esteban");		
	}
	
	
	//Test para obtener el jugador con peor Ranking de tiradas
	@Test
	public void getLoserTest() {
		
		PlayerMongoDB winner = implPlayerMongo.getLoser();
		
		assertThat(winner).isNotNull();		
		assertThat(winner.getPlayerName()).isEqualTo("Carmina");		
	}	
}
