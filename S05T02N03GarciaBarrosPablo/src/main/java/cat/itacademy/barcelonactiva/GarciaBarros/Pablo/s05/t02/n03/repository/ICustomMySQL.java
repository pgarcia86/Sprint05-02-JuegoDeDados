package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;

@Repository
public interface ICustomMySQL {
	
	public PlayerMySQL getOnePlayer(Integer id);
	
	public void deleteAllDiceRolls(Integer id);
	
	public List<PlayerMySQL> getRanking();
	
	public PlayerMySQL getLoser();
	
	public PlayerMySQL getWinner();

}
