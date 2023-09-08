package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration{

	@Override
	protected String getDatabaseName() {
		
		return "DiceGame_test";
	}
	
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(); // Configura la conexión a MongoDB
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

}
