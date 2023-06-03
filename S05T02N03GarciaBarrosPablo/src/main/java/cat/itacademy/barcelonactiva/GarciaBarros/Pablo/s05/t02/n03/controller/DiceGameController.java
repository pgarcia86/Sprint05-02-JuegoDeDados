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
	
	@GetMapping("/players")
	private ResponseEntity<List<PlayerMySQL>> getAll(){
		
		return new ResponseEntity<>(playerMySQL.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/players/{id}")
	private ResponseEntity<List<Object[]>> getOne(@PathVariable("id") int id){
		
		return new ResponseEntity<List<Object[]>>(playerMySQL.getOne(id), HttpStatus.OK);
	}	
	
	@GetMapping("/players/ranking")
	private ResponseEntity<List<Object[]>> getRanking(){
		
		return new ResponseEntity<List<Object[]>>(playerMySQL.getRanking(), HttpStatus.OK);
	}
	
	@GetMapping("/players/ranking/loser")
	private ResponseEntity<List<Object[]>> getLoser(){
		
		return new ResponseEntity<List<Object[]>>(playerMySQL.getLoser(), HttpStatus.OK);
	}
	
	@GetMapping("/players/ranking/winner")
	private ResponseEntity<List<Object[]>> getWinner(){
		
		return new ResponseEntity<List<Object[]>>(playerMySQL.getWinner(), HttpStatus.OK);
	}
	
	@PostMapping("/players/{name}/{date}")
	private ResponseEntity<PlayerMySQL> addOne(@PathVariable("name") String name, @PathVariable("date") String date){
		
		PlayerMySQL playerAux = playerMySQL.add(name, date);
		playerMongo.add(playerAux.getIdPlayer(), name, date);
		return new ResponseEntity<PlayerMySQL>(playerAux, HttpStatus.CREATED);
	}
	
	//Con este metodo simulo la tirada de dados
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
		playerMongo.update(playerAux.getIdPlayer(), name, playerAux.getRegistrationDate());
		
		return new ResponseEntity<PlayerMySQL>(playerMySQL.update(id, name), HttpStatus.OK);
	}
	
	@DeleteMapping("/players/{id}/games")
	private ResponseEntity<String> deleteGame(@PathVariable("id") Integer id){
		playerMySQL.delete(id);
		playerMongo.delete(id);
		return new ResponseEntity<String>("Se eliminaron las jugadas", HttpStatus.OK);
	}
	

}
