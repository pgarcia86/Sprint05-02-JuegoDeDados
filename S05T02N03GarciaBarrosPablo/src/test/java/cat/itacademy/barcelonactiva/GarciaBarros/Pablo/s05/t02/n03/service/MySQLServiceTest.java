package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.service;

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
	private PlayerServicesMySQL playerService;

	
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
		
		PlayerMySQL savedPlayer = playerService.addNewPlayer("Esteban", "24-03-1986");
		
		Assertions.assertThat(savedPlayer).isNotNull();		
	}
	
	
	//Test para la capa de Servicios, chequea que obtenga una lista de jugadores
	@Test
	public void getAllPLayersTest() {
		
		List<PlayerMySQL> players = new ArrayList<PlayerMySQL>();
		
		when(playerMySQL.findAll()).thenReturn(players);
		
		List<PlayerMySQL> allPlayers = playerService.getAllPlayers();
		
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
		
		PlayerMySQL getPlayer = playerService.getOneByPlayerId(1);
		
		Assertions.assertThat(getPlayer).isNotNull();	
		Assertions.assertThat(getPlayer.getPlayerName()).isEqualTo("Roberto");
	}
	

}
