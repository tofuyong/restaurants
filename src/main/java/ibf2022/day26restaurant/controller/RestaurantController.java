package ibf2022.day26restaurant.controller;

import java.util.List;

import org.bson.Document;
import org.bson.json.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.day26restaurant.model.Restaurant;
import ibf2022.day26restaurant.repository.RestaurantRepository;

@Controller
@RequestMapping 
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    RestaurantRepository restaurantRepo;

    @GetMapping(path = "/restaurants")
    public String listRestaurantTypes(Model model) {
        List<String> cuisines = restaurantRepo.findCuisineTypes();
        model.addAttribute("cuisines", cuisines);
        return "index";
    }

    @GetMapping(path = "/{cuisine}")
    public String listRestaurants(@PathVariable String cuisine, Model model) {
        List<Restaurant> restaurants = restaurantRepo.findRestaurantsByCuisine(cuisine);
        logger.info(">>>> Cusine bound to model:" + cuisine);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("cuisine", cuisine);
        return "restaurantsbycuisine";
    }


}
