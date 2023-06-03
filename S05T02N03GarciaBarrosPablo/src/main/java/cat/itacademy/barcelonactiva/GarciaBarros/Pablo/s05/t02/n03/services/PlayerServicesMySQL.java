package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions.NotFoundIdException;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions.NotValidIdException;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IDiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMySQL;

@Service
public class PlayerServicesMySQL implements IPlayerServicesMySQL{
	
	@Autowired
	private IPlayerMySQL playerMySQL;
	
	@Autowired
	private IDiceRollMySQL diceRollMySQL;
	
	@Override
	public List<PlayerMySQL> getAll(){		
		return playerMySQL.findAll();
	}

	@Override
	public PlayerMySQL add(String name, String date) {
		
		PlayerMySQL newPlayerMySQL = new PlayerMySQL(name, date);
		playerMySQL.save(newPlayerMySQL);
		return newPlayerMySQL;
	}
	
	@Override
	public PlayerMySQL update(Integer id, String name) {
		
		PlayerMySQL playerMySQLAux = playerMySQL.getReferenceById(id);
		playerMySQLAux.setPlayerName(name);
		playerMySQL.save(playerMySQLAux);
		return playerMySQLAux;		
	}

	@Override
	public DiceRollMySQL play(Integer id, Integer firstRoll, Integer secondRoll) {
		
		//Traigo de la base de datos el jugador que esta jugando
		
		if(getOneById(id) == true) {
			PlayerMySQL player = playerMySQL.getReferenceById(id);	
		
			//Creo una instancia de la tirada de dados
			DiceRollMySQL diceRoll = new DiceRollMySQL(player.getIdPlayer(),firstRoll, secondRoll);			
			this.diceRollMySQL.save(diceRoll);
			return diceRoll;
		}
		
		return null;		
	}		
	
	@Override
 	public List<Object[]> getOne(Integer id) {
		return this.playerMySQL.getOnePlayer(id);
	}	
 	
	@Override
	public List<Object[]> getRanking(){		
		return this.playerMySQL.getRanking();
	}	

	@Override
	public List<Object[]> getLoser(){
		return this.playerMySQL.getLoser();
	}	

	@Override
	public List<Object[]> getWinner(){
		return this.playerMySQL.getWinner();
	}

	@Override
	@Transactional
	public void delete(Integer id) {		
		this.diceRollMySQL.deleteAll(id);		
	}
	
	
	@Override
	public Boolean getOneById(Integer id) {
		
		if(id <= 0) {
			throw new NotValidIdException("El id debe ser mayor a 0 - ID = " + id);
		}
		else if(playerMySQL.existsById(id) == false){
			throw new NotFoundIdException("El id ingresado no existe - ID = " + id);
		}
		else{
			return true;
		}
	}

	@Override
	public PlayerMySQL getOneByPlayerId(Integer id) {
		
		if(getOneById(id) == true) {
			return playerMySQL.getReferenceById(id);
		}
		else {
			return null;
		}
	}

}
