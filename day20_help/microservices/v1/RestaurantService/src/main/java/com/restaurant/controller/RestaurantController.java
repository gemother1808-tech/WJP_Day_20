package com.restaurant.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.RestaurantDTO;
import com.restaurant.service.RestaurantService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurants")
/* will be needed with React Client
 * @CrossOrigin(origins = "http://localhost:3000")
*/
@AllArgsConstructor
public class RestaurantController {  
	//constructor based D.I - preferred way from Spring 5 onwards => mandatory D.I
	/*
	 * SC auto injects service dependency via parameterized ctor
	 */
	
    private  RestaurantService restaurantService;
    
    
    @GetMapping
    public ResponseEntity<?> fetchAllRestaurants(){
    	//get all restaurants from service layer
        List<RestaurantDTO> allRestaurants = restaurantService.findAllRestaurants();
        // in case of no restaurants , send SC 
        if(allRestaurants.isEmpty())
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        //send SC 200 + list of restaurants
        return ResponseEntity.ok(allRestaurants);
    }

    @PostMapping
    public ResponseEntity<?> addNewRestaurant(@RequestBody 
    		RestaurantDTO restaurantDTO) {
        RestaurantDTO restaurantAdded = restaurantService.
        		addRestaurantInDB(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
        		.body(restaurantAdded);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> findRestaurantById(@PathVariable Long restaurantId) {
       return ResponseEntity.ok(
    		   restaurantService.fetchRestaurantById(restaurantId));
    }

}
