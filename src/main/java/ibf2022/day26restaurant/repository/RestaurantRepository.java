package ibf2022.day26restaurant.repository;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.day26restaurant.model.Restaurant;

@Repository 
public class RestaurantRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(RestaurantRepository.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    // db.restaurant.distinct("type_of_food")
    public List<String> findCuisineTypes() {

        List<String> cuisines = mongoTemplate.findDistinct(new Query(), "type_of_food", "restaurant", String.class);
        return cuisines;

    }

    
    public List<Restaurant> findRestaurantsByCuisine(String cuisine) {
        
        logger.info(">>>Cuisine Query: " + cuisine); // ok
        // 1. Specify the filter / criteria
        /* 
            db.restaurant.find({ "type_of_food": 'cuisine'},
                 {_id:0, "name":1, "address":1, "type_of_food":1, "rating":1})
        */
        Criteria criterial = Criteria.where("type_of_food").regex(cuisine, "i"); //** regex (cuisine, "i") super important, case insensitive**/

        // 2. Create the query
        Query query = Query.query(criterial);
        query.fields().include("name", "address", "type_of_food", "rating").exclude("_id");

        

        // More advanced stream method by Chuk, alternative to #3 below
        // return mongoTemplate.find(query, Document.class, "restaurant")
        // 		.stream()
        // 		.map(doc -> doc.toJson())
        // 		.map(Restaurant::toRestaurant)
        // 		.toList();
        
        
        // 3. Execute the query, convert Documents to Restaurants
        List<Document> results = mongoTemplate.find(query, Document.class, "restaurant");
        logger.info(">>>testing: ");
        List<Restaurant> restaurants = new ArrayList<>();
        for (Document document : results) {
            
            String jsonStr = document.toJson(); 
            JsonReader reader = Json.createReader(new StringReader(jsonStr));
            JsonObject o = reader.readObject();
            String name = o.getString("name"); 
            String address = o.getString("address");
            Float fRating = (float)o.getJsonNumber("rating").doubleValue();
            Restaurant restaurant = new Restaurant(name, address, cuisine, fRating);
            restaurants.add(restaurant);
        }
       return restaurants;
    }

    
}
