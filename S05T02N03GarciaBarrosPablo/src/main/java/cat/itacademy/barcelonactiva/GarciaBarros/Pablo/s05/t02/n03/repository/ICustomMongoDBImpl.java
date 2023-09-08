package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.PlayerMongoDB;

@Repository
public class ICustomMongoDBImpl implements ICustomMongoDB{

	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public ICustomMongoDBImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	

	@Override
	public List<PlayerMongoDB> getRanking() {

		 Aggregation aggregation = Aggregation.newAggregation(
	                // Etapa de $lookup
	                Aggregation.lookup("Dice_Roll", "idPlayer", "idPlayer", "diceRolls"),
	                // Etapa de $unwind
	                Aggregation.unwind("diceRolls"),
	                // Etapa de $addFields para convertir valores booleanos en 1 o 0
	                Aggregation.addFields()
	                        .addField("diceRolls.win")
	                        .withValueOf(
	                                ConditionalOperators.when(Criteria.where("diceRolls.win").is(true))
	                                        .then(1)
	                                        .otherwise(0)
	                        )
	                        .build(),
	                // Etapa de $group
	                Aggregation.group(
	                        Fields.fields("_id", "playerName", "registrationDate"))
	                        .sum("diceRolls.win").as("totalWins")
	                        .count().as("totalGames"),
	                // Etapa de $project
	                Aggregation.project()
	                        .andExclude("_id")
	                        .andExpression("(totalWins * 100.0 / totalGames)").as("successRate")
	        );

	        AggregationResults<PlayerMongoDB> results = mongoTemplate.aggregate(aggregation, "Player", PlayerMongoDB.class);

	        return results.getMappedResults();
		
	}

}
