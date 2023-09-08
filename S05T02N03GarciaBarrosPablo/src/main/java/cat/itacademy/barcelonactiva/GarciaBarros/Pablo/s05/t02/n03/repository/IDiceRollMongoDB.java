package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DiceRollMongoDB;

@Repository
public interface IDiceRollMongoDB extends MongoRepository<DiceRollMongoDB, Integer>{
	
}
