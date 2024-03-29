package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions.NotFoundIdException;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

@Repository
public class ICustomMongoDBImpl implements ICustomMongoDB{
	
	@Autowired
	private IPlayerMongoDB playerMongo;
	
	
	@Override
	public PlayerMongoDB getOnePlayer(Integer id) {
		
		PlayerMongoDB player = playerMongo.findById(id).orElseThrow(() -> new NotFoundIdException("El id ingresado no existe - ID = " + id));
		return player;
	}

	
	@Override
	public List<PlayerMongoDB> getRanking() {
		
		Float cantTrue = 0f;
		
		List<PlayerMongoDB> players = playerMongo.findAll();
		
		for (PlayerMongoDB player : players) {
			
			List<DiceRollMongoDB> diceRolls = player.getDiceRolls();
			
			for (DiceRollMongoDB dice : diceRolls) {
				if(dice.getWin() == true) {
					cantTrue++;
				}
			}
			Float cantDice = (float) diceRolls.size();
			if(cantDice == 0) {
				player.setSuccessRate(0f);
			}
			else {
				player.setSuccessRate((cantTrue / cantDice) * 100);
			}
			playerMongo.save(player);				
			cantTrue = 0f;
		}		
		players = playerMongo.findAll();		
		return players;
	}

	
	@Override
	public PlayerMongoDB getWinner() {
		
		List<PlayerMongoDB> players = getRanking();		
		players.sort(Comparator.comparing(PlayerMongoDB :: getSuccessRate));				
		return players.get(players.size() - 1);		
	}

	
	@Override
	public PlayerMongoDB getLoser() {
		
		List<PlayerMongoDB> players = getRanking();		
		players.sort(Comparator.comparing(PlayerMongoDB :: getSuccessRate));				
		return players.get(0);
	}	
}
