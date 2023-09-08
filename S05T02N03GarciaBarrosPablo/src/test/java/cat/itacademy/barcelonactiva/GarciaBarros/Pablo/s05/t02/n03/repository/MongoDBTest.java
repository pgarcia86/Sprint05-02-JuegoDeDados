package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.client.MongoClients;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.process.runtime.Network;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {
	
	
    private static final String CONNECTION_STRING = "mongodb://%s:%d";
    
    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;
	
	@Autowired
	private IPlayerMongoDB iPlayerMongo;
	
	@Autowired
	private IDiceRollMongoDB iDiceRollMongo;
	
	@Autowired 	
	private ICustomMongoDBImpl implPlayerMongo;
	
	
	//Cargo jugadores en la base de datos de prueba antes de cada test
	@BeforeEach
	public void loadPlayerDiceRoll() {
		
		
		String ip = "localhost";
        int port = 27017;

        ImmutableMongodConfig mongodConfig = null;
		try {
			mongodConfig = MongodConfig.builder()
			    .version(de.flapdoodle.embed.mongo.distribution.Version.Main.PRODUCTION)
			    //.version(Version.Main.PRODUCTION)
			    .net(new Net(ip, port, Network.localhostIsIPv6()))
			    .build();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        try {
			mongodExecutable.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
		
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player1 = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Esteban")
				.registrationDate(currentDate)
				.build();
		
		PlayerMongoDB player2 = PlayerMongoDB.builder()
				.idPlayer(2)
				.playerName("Roberto")
				.registrationDate(currentDate)
				.build();
		
		PlayerMongoDB player3 = PlayerMongoDB.builder()
				.idPlayer(3)
				.playerName("Andrea")
				.registrationDate(currentDate)
				.build();
		
		PlayerMongoDB player4 = PlayerMongoDB.builder()
				.idPlayer(4)
				.playerName("Carmina")
				.registrationDate(currentDate)
				.build();
		
		iPlayerMongo.save(player1);
		iPlayerMongo.save(player2);
		iPlayerMongo.save(player3);
		iPlayerMongo.save(player4);
		
		//Jugador 1
		DiceRollMongoDB diceRoll1 = DiceRollMongoDB.builder()
								.idPlayer(player1.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMongo.save(diceRoll1);

		//Jugador2
		DiceRollMongoDB diceRoll2 = DiceRollMongoDB.builder()
								.idPlayer(player2.getIdPlayer())
								.firstRoll(4)
								.secondRoll(2)
								.win(false)
								.build();

		iDiceRollMongo.save(diceRoll2);

		DiceRollMongoDB diceRoll3 = DiceRollMongoDB.builder()
								.idPlayer(player2.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMongo.save(diceRoll3);
		
		
		//Jugador3
		DiceRollMongoDB diceRoll4 = DiceRollMongoDB.builder()
								.idPlayer(player3.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMongo.save(diceRoll4);

		DiceRollMongoDB diceRoll5 = DiceRollMongoDB.builder()
								.idPlayer(player3.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMongo.save(diceRoll5);

		DiceRollMongoDB diceRoll6 = DiceRollMongoDB.builder()
								.idPlayer(player3.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMongo.save(diceRoll6);
		
		
		//Jugador4
		DiceRollMongoDB diceRoll7 = DiceRollMongoDB.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMongo.save(diceRoll7);

		DiceRollMongoDB diceRoll8 = DiceRollMongoDB.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMongo.save(diceRoll8);

		DiceRollMongoDB diceRoll9 = DiceRollMongoDB.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(1)
								.win(false)
								.build();

		iDiceRollMongo.save(diceRoll9);
		
		DiceRollMongoDB diceRoll10 = DiceRollMongoDB.builder()
								.idPlayer(player4.getIdPlayer())
								.firstRoll(4)
								.secondRoll(3)
								.win(true)
								.build();

		iDiceRollMongo.save(diceRoll10);
	}
	
	
	//Elimino los jugadores y las tiradas de dados despues de cada test
	@AfterEach
	public void deletePlayers() {
		
		iPlayerMongo.deleteAll();
		iDiceRollMongo.deleteAll();
	}
	
	
	//Test para guardar un jugador nuevo
	@Test
	public void saveNewPlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(5)
				.playerName("Antonella")
				.registrationDate(currentDate).build();
		
		PlayerMongoDB playerSaved = iPlayerMongo.save(player);
		
		DiceRollMongoDB diceRoll = DiceRollMongoDB.builder()
				.idPlayer(player.getIdPlayer())
				.firstRoll(4)
				.secondRoll(3)
				.win(true)
				.build();

		iDiceRollMongo.save(diceRoll);
		
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
				.registrationDate(currentDate).build();
		
		iPlayerMongo.save(player);
		
		DiceRollMongoDB diceRoll = DiceRollMongoDB.builder()
				.idPlayer(player.getIdPlayer())
				.firstRoll(4)
				.secondRoll(3)
				.win(true)
				.build();

		iDiceRollMongo.save(diceRoll);
		
		
		Optional<PlayerMongoDB> onePlayer = iPlayerMongo.findById(6);
		
		assertThat(onePlayer).isNotEmpty();
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
		
		DiceRollMongoDB diceRollMongo = DiceRollMongoDB.builder()
											.idPlayer(1)
											.firstRoll(4)
											.secondRoll(3)
											.win(true)
											.build();		
		
		DiceRollMongoDB savedDiceRoll = iDiceRollMongo.save(diceRollMongo);
		
		assertThat(savedDiceRoll.getIdPlayer()).isEqualTo(1);
		assertThat(savedDiceRoll.getWin()).isEqualTo(true);		
	}
	
	
	//Test para eliminar las tiradas de dados
	public void deleteDiceRollTest() {
		
		iDiceRollMongo.deleteByIdPlayer(1);
		
		List<DiceRollMongoDB> diceRolls = iDiceRollMongo.findByIdPlayer(1);
		
		assertThat(diceRolls.size()).isEqualTo(0);
		assertThat(diceRolls).isEmpty();		
	}
	
	
	//Test para obtener el rankking de los jugadores
	//ESTE TEST NO LO PUEDO HACER FUNCIONAR - NO SE SI ES ALGO EN LA RELACION 
	//ENTRE LAS COLECCIONES O QUE PUEDE SER, PERO NO FUNCIONA.
	@Test
	public void getRankingTest() {
		List<PlayerMongoDB> ranking = implPlayerMongo.getRanking();
		
		assertThat(ranking).isNotEmpty();
		assertThat(ranking.size()).isEqualTo(4);
		assertThat(ranking.get(0).getPlayerName()).isEqualTo("Esteban");
	}
}
