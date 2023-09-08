package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

public interface IPlayerServicesMongo {
	
	public List<PlayerMongoDB> getAll();
	
	public PlayerMongoDB add(Integer id, String name, String registrationDate);
	
	public DiceRollMongoDB play(Integer id, Integer firstRoll, Integer secondRoll);
	
	public PlayerMongoDB update(Integer id, String name, Date date);
	
	public void delete(Integer id);
	
	public Optional<PlayerMongoDB> getOnePlayer(Integer id);

}
