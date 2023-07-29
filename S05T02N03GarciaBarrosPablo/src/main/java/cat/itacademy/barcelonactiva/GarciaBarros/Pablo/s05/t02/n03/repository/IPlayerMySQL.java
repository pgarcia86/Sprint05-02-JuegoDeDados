package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL;

@Repository
public interface IPlayerMySQL extends JpaRepository<PlayerMySQL, Integer>{
	
	@Query("SELECT new cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL (p.idPlayer, p.playerName, p.registrationDate, (SUM(d.win = TRUE) * 100 / COUNT(d.win)) * 1.0 AS successRate) "
			+ "FROM DiceRollMySQL AS d INNER JOIN PlayerMySQL p ON d.idPlayer = p.idPlayer WHERE d.idPlayer = :id "
			+ "GROUP BY p.idPlayer, p.playerName, p.registrationDate")	
	public List<PlayerMySQL> getOnePlayer(@Param("id") Integer id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM PlayerMySQL")
	public void deleteTodos();
	

	@Query("SELECT new cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL(p.idPlayer, p.playerName, p.registrationDate, (SUM(d.win = TRUE) * 100.0 / COUNT(d.win)) * 1.0 AS successRate) "
			+ "FROM DiceRollMySQL AS d INNER JOIN PlayerMySQL p ON d.idPlayer = p.idPlayer "
			+ "GROUP BY p.idPlayer, p.playerName, p.registrationDate")
	public List<PlayerMySQL> getRanking();

	
	@Query("SELECT new cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL (p.idPlayer, p.playerName, p.registrationDate, (SUM(d.win = TRUE) * 100 / COUNT(d.win)) * 1.0 AS loser) "
			+ "FROM DiceRollMySQL d INNER JOIN PlayerMySQL p ON d.idPlayer = p.idPlayer "
			+ "GROUP BY p.idPlayer, p.playerName, p.registrationDate ORDER BY loser LIMIT 1")	
	public List<PlayerMySQL> getLoser();

	
	@Query("SELECT new cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMySQL (p.idPlayer, p.playerName, p.registrationDate, (SUM(d.win = TRUE) * 100 / COUNT(d.win)) * 1.0 AS loser) "
			+ "FROM DiceRollMySQL d INNER JOIN PlayerMySQL p ON d.idPlayer = p.idPlayer "
			+ "GROUP BY p.idPlayer, p.playerName, p.registrationDate ORDER BY loser DESC LIMIT 1")
	public List<PlayerMySQL> getWinner();
	

}
