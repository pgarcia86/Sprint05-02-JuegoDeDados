package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

@DataMongoTest
public class PlayerMongoDBSavePlayerTest {
	
	@Autowired
	private IPlayerMongoDB iPlayerMongo;
	
	@Test
	public void saveNewPlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		PlayerMongoDB player = PlayerMongoDB.builder()
				.idPlayer(1)
				.playerName("Esteban")
				.registrationDate(currentDate).build();
		
		PlayerMongoDB playerSaved = iPlayerMongo.save(player);
		
		assertThat(playerSaved).isNotNull();
	}
	
	
	

}
