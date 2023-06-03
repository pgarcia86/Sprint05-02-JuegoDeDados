package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;

@Repository
public interface IDiceRollMySQL extends JpaRepository<DiceRollMySQL, Integer>{
	
	@Modifying
	@Query("DELETE FROM DiceRollMySQL as d WHERE d.idPlayer = :id")
	public void deleteAll(Integer id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM DiceRollMySQL")
	public void deleteTodos();

}
