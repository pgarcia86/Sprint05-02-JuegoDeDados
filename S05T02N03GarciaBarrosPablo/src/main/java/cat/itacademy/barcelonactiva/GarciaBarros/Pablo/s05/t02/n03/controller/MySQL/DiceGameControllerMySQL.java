package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.MySQL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMongo;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMySQL;

@RestController
public class DiceGameControllerMySQL {
	
	@Autowired
	private PlayerServicesMySQL playerMySQL;
	
	@Autowired
	private PlayerServicesMongo playerMongo;
	
	//Este metodo devuelve todos los jugadores
	@GetMapping("/playersMySQL")
	private ResponseEntity<List<PlayerMySQLDTO>> getAll(){
		
		return new ResponseEntity<>(playerMySQL.getAllPlayers(), HttpStatus.OK);
	}

	
	//Este metodo devuelve el jugador con el id ingresado
	@GetMapping("/playersMySQL/{id}")
	private ResponseEntity<PlayerMySQLDTO> getOne(@PathVariable("id") int id){
		
		return new ResponseEntity<PlayerMySQLDTO>(playerMySQL.getOneByPlayerId(id), HttpStatus.OK);
	}	
	
	
	//Este metodo devuelve el ranking de los jugadores
	@GetMapping("/playersMySQL/ranking")
	private ResponseEntity<List<PlayerMySQLDTO>> getRanking(){
		
		return new ResponseEntity<List<PlayerMySQLDTO>>(playerMySQL.getRanking(), HttpStatus.OK);
	}
	
	
	//Este metodo devuelve el jugador con peor ranking
	@GetMapping("/playersMySQL/ranking/loser")
	private ResponseEntity<PlayerMySQLDTO> getLoser(){
		
		return new ResponseEntity<PlayerMySQLDTO>(playerMySQL.getLoser(), HttpStatus.OK);
	}
	
	
	//Este metodo devuelve el jugador con mejor ranking
	@GetMapping("/playersMySQL/ranking/winner")
	private ResponseEntity<PlayerMySQLDTO> getWinner(){
		
		return new ResponseEntity<PlayerMySQLDTO>(playerMySQL.getWinner(), HttpStatus.OK);
	}
	
	
	//Este metodo añade un nuevo jugador
	@PostMapping("/playersMySQL/{name}/{date}")
	private ResponseEntity<PlayerMySQLDTO> addOne(@PathVariable("name") String name, @PathVariable("date") String date){
		
		PlayerMySQLDTO playerAux = playerMySQL.addNewPlayer(name, date);
		playerMongo.addNewPlayer(playerAux.getIdPlayer(), name, date);
		return new ResponseEntity<PlayerMySQLDTO>(playerAux, HttpStatus.CREATED);
	}
	
	
	//Este metodo simula una tirada de dados
	@PostMapping("/playersMySQL/{id}/newGame")
	private ResponseEntity<DiceRollMySQLDTO> play(@PathVariable("id") Integer id){
		
		Integer firstRoll = (int) (Math.random() * 6) + 1;
		Integer secondRoll = (int) (Math.random() * 6) + 1;		
		playerMongo.play(id, firstRoll, secondRoll);
		
		return new ResponseEntity<DiceRollMySQLDTO>(playerMySQL.play(id, firstRoll, secondRoll), HttpStatus.CREATED);
	}
	
	
	//Con este metodo modifico el nombre del jugador
	@PutMapping("/playersMySQL/{id}/{name}")
	private ResponseEntity<PlayerMySQLDTO> updateName(@PathVariable("id") Integer id, @PathVariable("name") String name){
		
		PlayerMySQLDTO playerAux = playerMySQL.getOneByPlayerId(id);	
		playerMongo.updatePlayerName(playerAux.getIdPlayer(), name, playerAux.getRegistrationDate());
		
		return new ResponseEntity<PlayerMySQLDTO>(playerMySQL.updatePlayerName(id, name), HttpStatus.OK);
	}
	
	
	//Con este metodo elimino las tiradas de un jugador especificado
	@DeleteMapping("/playersMySQL/{id}/games")
	private ResponseEntity<String> deleteGame(@PathVariable("id") Integer id){
		playerMySQL.deleteDiceRolls(id);
		playerMongo.deleteDiceRolls(id);
		return new ResponseEntity<String>("Se eliminaron las jugadas", HttpStatus.OK);
	}
}
