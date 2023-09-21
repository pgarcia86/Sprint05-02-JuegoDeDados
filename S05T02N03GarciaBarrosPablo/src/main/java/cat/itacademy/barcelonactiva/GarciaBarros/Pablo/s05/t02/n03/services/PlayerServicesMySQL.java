package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMySQLDTO;
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
	public List<PlayerMySQLDTO> getAllPlayers(){	
		
		List<PlayerMySQL> players = playerMySQL.findAll();
		List<PlayerMySQLDTO> playersDTO = new ArrayList<>();
		
		for (PlayerMySQL player : players) {
			
			playersDTO.add(returnDTO(player));			
		}		
		return playersDTO;
	}

	
	@Override
	public PlayerMySQLDTO addNewPlayer(String name, String date) {
		
		PlayerMySQL newPlayerMySQL = new PlayerMySQL(name, date);
		playerMySQL.save(newPlayerMySQL);
		return returnDTO(newPlayerMySQL);
	}
	
	
	@Override
	public PlayerMySQLDTO updatePlayerName(Integer id, String name) {
		
		PlayerMySQL playerMySQLAux = playerMySQL.getReferenceById(id);
		playerMySQLAux.setPlayerName(name);
		playerMySQL.save(playerMySQLAux);
		return returnDTO(playerMySQLAux);		
	}

	
	@Override
	public DiceRollMySQLDTO play(Integer id, Integer firstRoll, Integer secondRoll) {
		
		//Traigo de la base de datos el jugador que esta jugando		
		PlayerMySQL playerAux = playerMySQL.getReferenceById(id);
		DiceRollMySQL newDiceRoll = new DiceRollMySQL(playerAux, firstRoll, secondRoll); 			
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
			
		return returnDiceRollDTO(newDiceRoll);
	}		
 	
	
	@Override
	public List<PlayerMySQLDTO> getRanking(){	
		
		customMySQL.getRanking();		
		return getAllPlayers();
	}	

	
	@Override
	public PlayerMySQLDTO getLoser(){
		
		return returnDTO(customMySQL.getLoser());
	}	

	
	@Override
	public PlayerMySQLDTO getWinner(){
		return returnDTO(customMySQL.getWinner());
		
	}

	
	@Override
	@Transactional
	public void deleteDiceRolls(Integer id) {	
		this.customMySQL.deleteAllDiceRolls(id);
	}
	

	@Override
	public PlayerMySQLDTO getOneByPlayerId(Integer id) {

		PlayerMySQL player = customMySQL.getOnePlayer(id);
		
		return returnDTO(player);
	}


	private PlayerMySQLDTO returnDTO(PlayerMySQL player) {
		
		PlayerMySQLDTO playerDTO = new PlayerMySQLDTO();
		playerDTO.setIdPlayer(player.getIdPlayer());
		playerDTO.setPlayerName(player.getPlayerName());
		playerDTO.setRegistrationDate(player.getRegistrationDate());
		playerDTO.setSuccessRate(player.getSuccessRate());
		
		return playerDTO;	
	}

	
	private DiceRollMySQLDTO returnDiceRollDTO(DiceRollMySQL diceRoll) {
		
		DiceRollMySQLDTO diceRollDTO = new DiceRollMySQLDTO();
		diceRollDTO.setIdGame(diceRoll.getIdGame());
		diceRollDTO.setIdPlayer(diceRoll.getIdPlayer());
		diceRollDTO.setFirstRoll(diceRoll.getFirstRoll());
		diceRollDTO.setSecondRoll(diceRoll.getSecondRoll());
		diceRollDTO.setWin(diceRoll.getWin());
		
		return diceRollDTO;		
	}
}
