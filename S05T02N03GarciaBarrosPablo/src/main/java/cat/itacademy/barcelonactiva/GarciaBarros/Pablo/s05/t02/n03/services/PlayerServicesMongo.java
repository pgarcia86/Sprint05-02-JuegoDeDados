package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.ICustomMongoDBImpl;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMongoDB;

@Service
public class PlayerServicesMongo implements IPlayerServicesMongo{
		
	@Autowired
	private IPlayerMongoDB playerMongo;
		
	@Autowired
	private ICustomMongoDBImpl customPlayerMongo;
	
	
	@Override
	public List<PlayerMongoDTO> getAllPlayers() {
		
		List<PlayerMongoDB> players = playerMongo.findAll();
		List<PlayerMongoDTO> playersDTO = new ArrayList<>();
		
		for (PlayerMongoDB player : players) {
			
			playersDTO.add(returnDTO(player));			
		}		
		return playersDTO;
	}
	
	
	@Override
	public PlayerMongoDTO addNewPlayer(Integer id, String name, String registrationDate) {
		
		PlayerMongoDB playerAux = new PlayerMongoDB(id, name, registrationDate);
		playerMongo.save(playerAux);
		return returnDTO(playerAux);
	}
	
	
	@Override
	public PlayerMongoDTO updatePlayerName(Integer id, String name, Date registrationDate) {
		
		PlayerMongoDB playerAux = new PlayerMongoDB(id, name, registrationDate);
		
		playerMongo.save(playerAux);
		
		return returnDTO(playerAux);
	}
	
	
	@Override
	public DiceRollMongoDTO play(Integer id) {
		
		Integer firstRoll = (int) (Math.random() * 6) + 1;
		Integer secondRoll = (int) (Math.random() * 6) + 1;
			
			PlayerMongoDB playerAux = customPlayerMongo.getOnePlayer(id);
			DiceRollMongoDB newDiceRoll = new DiceRollMongoDB(id, firstRoll, secondRoll); 
			
			playerAux.getDiceRolls().add(newDiceRoll);
			
			Float cantTrue = 0f;
			
			List<DiceRollMongoDB> diceRollList = playerAux.getDiceRolls();
			
			for (DiceRollMongoDB diceRoll : diceRollList) {
				if(diceRoll.getWin() == true) {
					cantTrue++;
				}
			}
			
			Float cantDice = (float) playerAux.getDiceRolls().size();
			
			playerAux.setSuccessRate((cantTrue / cantDice) * 100);
			
			playerMongo.save(playerAux);
			
			return returnDiceRollDTO(newDiceRoll);
	}
	
	
	@Override
	public List<PlayerMongoDTO> getRanking(){
		
		customPlayerMongo.getRanking();
		return getAllPlayers();
	}

	
	@Override
	public PlayerMongoDTO getLoser() {
		
		return returnDTO(customPlayerMongo.getLoser());
	}
	
	
	@Override
	public PlayerMongoDTO getWinner() {
		return returnDTO(customPlayerMongo.getWinner());
	}
		
	
	@Override
	public void deleteDiceRolls(Integer id) {
				
			PlayerMongoDB playerAux = customPlayerMongo.getOnePlayer(id);			
			playerAux.getDiceRolls().clear();
			playerAux.setSuccessRate(0f);
			
			playerMongo.save(playerAux);
	}


	@Override
	public PlayerMongoDTO getOneByPlayerId(Integer id) {
		
		PlayerMongoDB player = customPlayerMongo.getOnePlayer(id);
		
		return returnDTO(player);
	}
	
	
	private PlayerMongoDTO returnDTO(PlayerMongoDB player) {
		
		PlayerMongoDTO playerDTO = new PlayerMongoDTO();
		playerDTO.setIdPlayer(player.getIdPlayer());
		playerDTO.setPlayerName(player.getPlayerName());
		playerDTO.setRegistrationDate(player.getRegistrationDate());
		playerDTO.setSuccessRate(player.getSuccessRate());
		
		return playerDTO;	
	}

	
	private DiceRollMongoDTO returnDiceRollDTO(DiceRollMongoDB diceRoll) {
		
		DiceRollMongoDTO diceRollDTO = new DiceRollMongoDTO();
		diceRollDTO.setIdPlayer(diceRoll.getIdPlayer());
		diceRollDTO.setFirstRoll(diceRoll.getFirstRoll());
		diceRollDTO.setSecondRoll(diceRoll.getSecondRoll());
		diceRollDTO.setWin(diceRoll.getWin());
		
		return diceRollDTO;		
	}
}
