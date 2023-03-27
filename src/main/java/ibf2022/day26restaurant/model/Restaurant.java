package ibf2022.day26restaurant.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Restaurant {
    private String name;
    private String address;
    private String cuisine;
    private Float rating;

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getAddress() {return this.address; }
    public void setAddress(String address) {this.address = address;}

    public String getCuisine() {return this.cuisine;}
    public void setCuisine(String cuisine) {this.cuisine = cuisine;}
    
    public Float getRating() {return this.rating;}
    public void setRating(Float rating) {this.rating = rating;}


    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", cuisine='" + getCuisine() + "'" +
            "}";
    }


    public Restaurant(String name, String address, String cuisine, Float rating) {
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.rating = rating;
    }
    

    public Restaurant() {
    }


    // Create method to convert Json into Restaurant object (applied if using Chuk's stream method)
    public static Restaurant toRestaurant(String jsonStr) {
        Restaurant restaurant = new Restaurant();
        JsonReader reader = Json.createReader(new StringReader(jsonStr));
        JsonObject o = reader.readObject();
        restaurant.setName(o.getString("name"));
        restaurant.setAddress(o.getString("address"));
        restaurant.setCuisine(o.getString("type_of_food"));
        
        try {
            restaurant.setRating((float)o.getJsonNumber("rating").doubleValue());
        } catch (Exception ex) {
            // If rating is not a number, try string
            restaurant.setRating(-1f);
        }
        return restaurant;
    }

}
