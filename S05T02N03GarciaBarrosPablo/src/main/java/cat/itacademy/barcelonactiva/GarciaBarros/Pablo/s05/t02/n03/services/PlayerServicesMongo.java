package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.ICustomMongoDBImpl;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMongoDB;

@Service
public class PlayerServicesMongo implements IPlayerServicesMongo{
	
	
	@Autowired
	private IPlayerMongoDB playerMongo;
	
	
	@Autowired
	private ICustomMongoDBImpl customPlayerMongo;
	
	
	@Override
	public PlayerMongoDB addNewPlayer(Integer id, String name, String registrationDate) {
		PlayerMongoDB playerAux = new PlayerMongoDB(id, name, registrationDate);
		playerMongo.save(playerAux);
		return playerAux;
	}
	
	
	@Override
	public DiceRollMongoDB play(Integer id, Integer firstRoll, Integer secondRoll) {
		
		if(playerMongo.existsById(id)) {
			Optional<PlayerMongoDB> playerAux = playerMongo.findById(id);
			DiceRollMongoDB newDiceRoll = new DiceRollMongoDB(firstRoll, secondRoll); 
			
			playerAux.get().getDiceRolls().add(newDiceRoll);
			
			Float cantTrue = 0f;
			
			List<DiceRollMongoDB> diceRollList = playerAux.get().getDiceRolls();
			
			for (DiceRollMongoDB diceRoll : diceRollList) {
				if(diceRoll.getWin() == true) {
					cantTrue++;
				}
			}
			
			Float cantDice = (float) playerAux.get().getDiceRolls().size();
			
			playerAux.get().setSuccessRate((cantTrue / cantDice) * 100);
			
			playerMongo.save(playerAux.get());
			
			return newDiceRoll;
		}
		return null;		
	}
	

	@Override
	public List<PlayerMongoDB> getAllPlayers() {
		return playerMongo.findAll();
	}
	
	@Override
	public PlayerMongoDB updatePlayerName(Integer id, String name, Date registrationDate) {
		
		PlayerMongoDB playerAux = new PlayerMongoDB(id, name, registrationDate);
		
		playerMongo.save(playerAux);
		
		return playerAux;
	}

	@Override
	public void deleteDiceRolls(Integer id) {
		
		if(existPlayer(id)) {
			
			Optional<PlayerMongoDB> playerAux = playerMongo.findById(id);
			
			playerAux.get().getDiceRolls().clear();
			playerAux.get().setSuccessRate(0f);
			
			playerMongo.save(playerAux.get());
			
		}
	}
	
	
	@Override
	public boolean existPlayer(Integer id) {
		
		if(playerMongo.existsById(id) == true) {
			return true;
		}
		return false;		
	}
	

	@Override
	public Optional<PlayerMongoDB> getOnePlayer(Integer id) {
		
		if(existPlayer(id) == true) {
			
			Optional<PlayerMongoDB> onePlayer = playerMongo.findById(id);			
			return onePlayer;			
		}
		return null;		
	}

	
	@Override
	public List<PlayerMongoDB> getPlayersRanking(){
		return customPlayerMongo.getRanking();
	}

	
	@Override
	public PlayerMongoDB getWinnerPlayer() {
		return customPlayerMongo.getWinner();
	}
	
	
	@Override
	public PlayerMongoDB getLoserPlayer() {
		return customPlayerMongo.getLoser();
	}

}
