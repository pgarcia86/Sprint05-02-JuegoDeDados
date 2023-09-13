package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller;

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

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMongo;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services.PlayerServicesMySQL;

@RestController
public class DiceGameController {
	
	@Autowired
	private PlayerServicesMySQL playerMySQL;
	
	@Autowired
	private PlayerServicesMongo playerMongo;
	
	//Este metodo devuelve todos los jugadores
	@GetMapping("/players")
	private ResponseEntity<List<PlayerMySQL>> getAll(){
		
		return new ResponseEntity<>(playerMySQL.getAllPlayers(), HttpStatus.OK);
	}

	
	//Este metodo devuelve el jugador con el id ingresado
	@GetMapping("/players/{id}")
	private ResponseEntity<PlayerMySQL> getOne(@PathVariable("id") int id){
		
		return new ResponseEntity<PlayerMySQL>(playerMySQL.getOneByPlayerId(id), HttpStatus.OK);
	}	
	
	
	//Este metodo devuelve el ranking de los jugadores
	@GetMapping("/players/ranking")
	private ResponseEntity<List<PlayerMySQL>> getRanking(){
		
		return new ResponseEntity<List<PlayerMySQL>>(playerMySQL.getRanking(), HttpStatus.OK);
	}
	
	
	//Este metodo devuelve el jugador con peor ranking
	@GetMapping("/players/ranking/loser")
	private ResponseEntity<List<PlayerMySQL>> getLoser(){
		
		return new ResponseEntity<List<PlayerMySQL>>(playerMySQL.getLoser(), HttpStatus.OK);
	}
	
	
	//Este metodo devuelve el jugador con mejor ranking
	@GetMapping("/players/ranking/winner")
	private ResponseEntity<List<PlayerMySQL>> getWinner(){
		
		return new ResponseEntity<List<PlayerMySQL>>(playerMySQL.getWinner(), HttpStatus.OK);
	}
	
	
	//Este metodo añade un nuevo jugador
	@PostMapping("/players/{name}/{date}")
	private ResponseEntity<PlayerMySQL> addOne(@PathVariable("name") String name, @PathVariable("date") String date){
		
		PlayerMySQL playerAux = playerMySQL.addNewPlayer(name, date);
		playerMongo.addNewPlayer(playerAux.getIdPlayer(), name, date);
		return new ResponseEntity<PlayerMySQL>(playerAux, HttpStatus.CREATED);
	}
	
	
	//Este metodo simula una tirada de dados
	@PostMapping("/players/{id}/newGame")
	private ResponseEntity<DiceRollMySQL> play(@PathVariable("id") Integer id){
		
		Integer firstRoll = (int) (Math.random() * 6) + 1;
		Integer secondRoll = (int) (Math.random() * 6) + 1;
		
		playerMongo.play(id, firstRoll, secondRoll);
		
		return new ResponseEntity<DiceRollMySQL>(playerMySQL.play(id, firstRoll, secondRoll), HttpStatus.CREATED);
	}
	
	
	//Con este metodo modifico el nombre del jugador
	@PutMapping("/players/{id}/{name}")
	private ResponseEntity<PlayerMySQL> updateName(@PathVariable("id") Integer id, @PathVariable("name") String name){
		
		PlayerMySQL playerAux = playerMySQL.getOneByPlayerId(id);	
		playerMongo.updatePlayerName(playerAux.getIdPlayer(), name, playerAux.getRegistrationDate());
		
		return new ResponseEntity<PlayerMySQL>(playerMySQL.updatePlayerName(id, name), HttpStatus.OK);
	}
	
	
	//Con este metodo elimino las tiradas de un jugador especificado
	@DeleteMapping("/players/{id}/games")
	private ResponseEntity<String> deleteGame(@PathVariable("id") Integer id){
		playerMySQL.deleteDiceRolls(id);
		playerMongo.deleteDiceRolls(id);
		return new ResponseEntity<String>("Se eliminaron las jugadas", HttpStatus.OK);
	}
}
