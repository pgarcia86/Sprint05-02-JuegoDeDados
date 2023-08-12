package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

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
public class PlayerMongoDBSavePlayerTest {
	
	
    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;
	
	@Autowired
	private IPlayerMongoDB iPlayerMongo;
	
	@Autowired
	private IDiceRollMongoDB iDiceRollMongo;

	
	//Cargo jugadores en la base de datos de prueba antes de cada test
	@BeforeEach
	public void loadPlayerDiceRoll() {
		
		/*
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
		*/
		
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
	
	
	@Test
	public void saveNewPlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(5)
				.playerName("Antonella")
				.registrationDate(currentDate).build();
		
		PlayerMongoDB playerSaved = iPlayerMongo.save(player);
		
		assertThat(playerSaved).isNotNull();
		assertThat(playerSaved.getPlayerName()).isEqualTo("Antonella");
	}
}
