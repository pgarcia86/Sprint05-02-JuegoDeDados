package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.List;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

public interface ICustomMongoDB {
	
	List<PlayerMongoDB> getRanking();

}
