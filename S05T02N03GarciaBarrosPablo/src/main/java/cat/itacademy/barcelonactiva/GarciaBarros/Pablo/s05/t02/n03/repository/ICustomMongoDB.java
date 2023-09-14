package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

@Repository
public interface ICustomMongoDB {
	
	List<PlayerMongoDB> getRanking();
	
	PlayerMongoDB getWinner();
	
	PlayerMongoDB getLoser();

}
