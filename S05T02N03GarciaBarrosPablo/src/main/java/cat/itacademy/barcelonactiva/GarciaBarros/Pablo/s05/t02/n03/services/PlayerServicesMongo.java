package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IDiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IPlayerMongoDB;

@Service
public class PlayerServicesMongo implements IPlayerServicesMongo{
	
	@Autowired
	private IPlayerMongoDB playerMongo;
	
	@Autowired
	private IDiceRollMongoDB diceRollMongo;

	@Override
	public PlayerMongoDB add(Integer id, String name, String registrationDate) {
		PlayerMongoDB playerAux = new PlayerMongoDB(id, name, registrationDate);
		playerMongo.save(playerAux);
		return playerAux;
	}
	
	@Override
	public DiceRollMongoDB play(Integer id, Integer firstRoll, Integer secondRoll) {
		
		if(playerMongo.existsById(id)) {
			Optional<PlayerMongoDB> playerAux = playerMongo.findById(id);
			DiceRollMongoDB diceRoll = new DiceRollMongoDB(playerAux.get().getIdPlayer(), firstRoll, secondRoll); 
			diceRollMongo.save(diceRoll);
			return diceRoll;
		}
		return null;		
	}

	@Override
	public List<PlayerMongoDB> getAll() {
		return playerMongo.findAll();
	}

	@Override
	public PlayerMongoDB update(Integer id, String name, Date registrationDate) {
		
		PlayerMongoDB playerAux = new PlayerMongoDB(id, name, registrationDate);
		
		playerMongo.save(playerAux);
		
		return playerAux;
	}

	@Override
	public void delete(Integer id) {
		this.diceRollMongo.deleteAllByIdPlayer(id);
	}
	
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
}
