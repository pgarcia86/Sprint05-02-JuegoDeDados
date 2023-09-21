package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.MongoDB;

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

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMongo;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMySQL;

@RestController
public class DiceGameControllerMongoDB {
	
	@Autowired
	private PlayerServicesMySQL playerMySQL;
	
	@Autowired
	private PlayerServicesMongo playerMongo;
	
	//Este metodo devuelve todos los jugadores
	@GetMapping("/playersMongo")
	private ResponseEntity<List<PlayerMongoDTO>> getAll(){
		
		return new ResponseEntity<>(playerMongo.getAllPlayers(), HttpStatus.OK);
	}

	
	//Este metodo devuelve el jugador con el id ingresado
	@GetMapping("/playersMongo/{id}")
	private ResponseEntity<PlayerMongoDTO> getOne(@PathVariable("id") int id){
		
		return new ResponseEntity<PlayerMongoDTO>(playerMongo.getOneByPlayerId(id), HttpStatus.OK);
	}	
	
	
	//Este metodo devuelve el ranking de los jugadores
	@GetMapping("/playersMongo/ranking")
	private ResponseEntity<List<PlayerMongoDTO>> getRanking(){
		
		return new ResponseEntity<List<PlayerMongoDTO>>(playerMongo.getRanking(), HttpStatus.OK);
	}
	
	
	//Este metodo devuelve el jugador con peor ranking
	@GetMapping("/playersMongo/ranking/loser")
	private ResponseEntity<PlayerMongoDTO> getLoser(){
		
		return new ResponseEntity<PlayerMongoDTO>(playerMongo.getLoser(), HttpStatus.OK);
	}
	
	
	//Este metodo devuelve el jugador con mejor ranking
	@GetMapping("/playersMongo/ranking/winner")
	private ResponseEntity<PlayerMongoDTO> getWinner(){
		
		return new ResponseEntity<PlayerMongoDTO>(playerMongo.getWinner(), HttpStatus.OK);
	}
	
	
	//Este metodo añade un nuevo jugador
	@PostMapping("/playersMongo/{name}/{date}")
	private ResponseEntity<PlayerMongoDTO> addOne(@PathVariable("name") String name, @PathVariable("date") String date){
		
		PlayerMySQLDTO playerAux = playerMySQL.addNewPlayer(name, date);
		PlayerMongoDTO playerMongoDTO = playerMongo.addNewPlayer(playerAux.getIdPlayer(), name, date);		
		
		return new ResponseEntity<PlayerMongoDTO>(playerMongoDTO, HttpStatus.CREATED);
	}
	
	
	//Este metodo simula una tirada de dados
	@PostMapping("/playersMongo/{id}/newGame")
	private ResponseEntity<DiceRollMongoDTO> play(@PathVariable("id") Integer id){
		
		Integer firstRoll = (int) (Math.random() * 6) + 1;
		Integer secondRoll = (int) (Math.random() * 6) + 1;		
		playerMySQL.play(id, firstRoll, secondRoll);
		
		return new ResponseEntity<DiceRollMongoDTO>(playerMongo.play(id, firstRoll, secondRoll), HttpStatus.CREATED);
	}
	
	
	//Con este metodo modifico el nombre del jugador
	@PutMapping("/playersMongo/{id}/{name}")
	private ResponseEntity<PlayerMongoDTO> updateName(@PathVariable("id") Integer id, @PathVariable("name") String name){
		
		PlayerMySQLDTO playerAux = playerMySQL.getOneByPlayerId(id);	
		playerMySQL.updatePlayerName(playerAux.getIdPlayer(), name);
		
		return new ResponseEntity<PlayerMongoDTO>(playerMongo.updatePlayerName(id, name, playerAux.getRegistrationDate()), HttpStatus.OK);
	}
	
	
	//Con este metodo elimino las tiradas de un jugador especificado
	@DeleteMapping("/playersMongo/{id}/games")
	private ResponseEntity<String> deleteGame(@PathVariable("id") Integer id){
		playerMySQL.deleteDiceRolls(id);
		playerMongo.deleteDiceRolls(id);
		return new ResponseEntity<String>("Se eliminaron las jugadas", HttpStatus.OK);
	}
}
