package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMySQL;

@Repository
public interface IDiceRollMySQL extends JpaRepository<DiceRollMySQL, Integer>{

	
	public List<DiceRollMySQL> findAllByIdPlayer(Integer id);
	
	public void deleteAllByIdPlayer(Integer id);
}
