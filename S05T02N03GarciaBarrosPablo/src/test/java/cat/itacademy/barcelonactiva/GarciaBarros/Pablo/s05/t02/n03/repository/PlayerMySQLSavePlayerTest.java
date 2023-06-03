package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.*;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerMySQLSavePlayerTest {
	
	@Autowired
	private IPlayerMySQL iPlayerMySQL;

	@Test
	public void saveNewPlayerTest() {
		
		Date currentDate = new Date();
		currentDate.getTime();
		
		
		PlayerMySQL player = PlayerMySQL.builder()
								.playerName("Esteban")
								.registrationDate(currentDate)
								.build();
		
		PlayerMySQL playerSaved = iPlayerMySQL.save(player);
		
		assertThat(playerSaved.getIdPlayer()).isGreaterThan(0);
		assertThat(playerSaved).isNotNull();		
	}	

}
