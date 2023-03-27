package ibf2022.day26restaurant.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
    
    @Value("${mongo.url}")
    private String mongoUrl;

    @Bean 
    // Return a Mongo template
    public MongoTemplate createMongoTemplate() {
        // Create a Mongo Client
        MongoClient client = MongoClients.create(mongoUrl); // Create the client from connection string
        MongoTemplate template = new MongoTemplate(client, "dining"); 
        return template;
    }
}
