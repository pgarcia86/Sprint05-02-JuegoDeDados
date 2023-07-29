package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.List;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;



//deberia hacer un servicio para cada base de datos
public interface IPlayerServicesMySQL {
	
	public List<PlayerMySQL> getAll();
	
	public PlayerMySQL add(String name, String date);
	
	public PlayerMySQL update(Integer id, String name);	
	
	public List<PlayerMySQL> getRanking();

	public List<PlayerMySQL> getLoser();

	public List<PlayerMySQL> getWinner();
	
	public DiceRollMySQL play(Integer id, Integer firstRoll, Integer secondRoll);
	
	public Boolean getOneById(Integer id);
	
	public PlayerMySQL getOneByPlayerId(Integer id);
	
	public void delete (Integer id);


}
