package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions.NotFoundIdException;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions.NotValidIdException;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.ICustomMySQLImpl;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IDiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMySQL;

@Service
public class PlayerServicesMySQL implements IPlayerServicesMySQL{
	
	
	@Autowired
	private IPlayerMySQL playerMySQL;
		
	@Autowired
	private IDiceRollMySQL diceRollMySQL;
	
	@Autowired
	private ICustomMySQLImpl customMySQL;
	
	
	@Override
	public List<PlayerMySQL> getAllPlayers(){		
		return playerMySQL.findAll();
	}

	
	@Override
	public PlayerMySQL addNewPlayer(String name, String date) {
		
		PlayerMySQL newPlayerMySQL = new PlayerMySQL(name, date);
		playerMySQL.save(newPlayerMySQL);
		return newPlayerMySQL;
	}
	
	
	@Override
	public PlayerMySQL updatePlayerName(Integer id, String name) {
		
		PlayerMySQL playerMySQLAux = playerMySQL.getReferenceById(id);
		playerMySQLAux.setPlayerName(name);
		playerMySQL.save(playerMySQLAux);
		return playerMySQLAux;		
	}

	
	@Override
	public DiceRollMySQL play(Integer id, Integer firstRoll, Integer secondRoll) {
		
		//Traigo de la base de datos el jugador que esta jugando
		
		if(playerMySQL.existsById(id)) {
			PlayerMySQL playerAux = playerMySQL.getReferenceById(id);
			DiceRollMySQL newDiceRoll = new DiceRollMySQL(id, firstRoll, secondRoll); 
			
			diceRollMySQL.save(newDiceRoll);
			
			Float cantTrue = 0f;
			
			List<DiceRollMySQL> diceRollList = diceRollMySQL.findAllByIdPlayer(id);
			
			for (DiceRollMySQL diceRoll : diceRollList) {
				if(diceRoll.getWin() == true) {
					cantTrue++;
				}
			}
			
			Float cantDice = (float) diceRollList.size();
			
			playerAux.setSuccessRate((cantTrue / cantDice) * 100);
			
			playerMySQL.save(playerAux);
			
			return newDiceRoll;
		}
		return null;	
	}		
 	
	
	@Override
	public List<PlayerMySQL> getRanking(){		
		return this.customMySQL.getRanking();
	}	

	
	@Override
	public PlayerMySQL getLoser(){
		
		return this.customMySQL.getLoser();
	}	

	
	@Override
	public PlayerMySQL getWinner(){
		return this.customMySQL.getWinner();
		
	}

	
	@Override
	@Transactional
	public void deleteDiceRolls(Integer id) {	
		this.customMySQL.deleteAllDiceRolls(id);
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
		/*
		if(getOneById(id) == true) {
			return customMySQL.getOnePlayer(id);
		}
		else {
			return null;
		}
		*/
		return customMySQL.getOnePlayer(id);
	}
}
