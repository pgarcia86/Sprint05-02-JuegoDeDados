package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.List;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;

@Repository
public class ICustomMySQLImpl implements ICustomMySQL{

	@Autowired
	private IPlayerMySQL playerMySQL;
	
	@Autowired
	private IDiceRollMySQL diceRollMySQL;
	
	
	@Override
	public PlayerMySQL getOnePlayer(Integer id) {
		
		PlayerMySQL player = playerMySQL.getReferenceById(id);
		return player;
	}

	
	@Override
	public void deleteAllDiceRolls(Integer id) {
		
		diceRollMySQL.deleteAllByIdPlayer(id);
		PlayerMySQL player = playerMySQL.getReferenceById(id);
		player.setSuccessRate(0f);
		playerMySQL.save(player);		
	}

	
	@Override
	public List<PlayerMySQL> getRanking() {
		
		Float cantTrue = 0f;
		
		List<PlayerMySQL> players = playerMySQL.findAll();		
		for (PlayerMySQL player : players) {
			
			List<DiceRollMySQL> diceRolls = diceRollMySQL.findAllByIdPlayer(player.getIdPlayer());
			for (DiceRollMySQL dice : diceRolls) {
				if(dice.getWin() == true) {
					cantTrue++;
				}						
			}		
			Float cantDice = (float)diceRolls.size();
			player.setSuccessRate((cantTrue / cantDice) * 100);
			playerMySQL.save(player);
			cantTrue = 0f;
		}		
		return playerMySQL.findAll();
	}

	
	@Override
	public PlayerMySQL getLoser() {
		
		List<PlayerMySQL> players = getRanking();		
		players.sort(Comparator.comparing(PlayerMySQL :: getSuccessRate));		
		return players.get(0);
		
	}

	
	@Override
	public PlayerMySQL getWinner() {		
		
		List<PlayerMySQL> players = getRanking();	
		players.sort(Comparator.comparing(PlayerMySQL :: getSuccessRate));			
		return players.get(players.size() - 1);	
	}

}
