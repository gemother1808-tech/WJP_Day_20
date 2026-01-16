package com.menu.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.menu.dto.FoodItemDTO;
import com.menu.dto.RestaurantMenuDetails;
import com.menu.service.FoodMenuService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/menu")
@CrossOrigin
@AllArgsConstructor
public class FoodMenuController {

    
    private FoodMenuService foodMenuService;

    @PostMapping
    public ResponseEntity<?> addFoodItemToMenu(
    		@RequestBody FoodItemDTO foodItemDTO){
        FoodItemDTO foodItemSaved = foodMenuService.addFoodItem(foodItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
        		.body(foodItemSaved);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> fetchRestaurantAndMenuDetails(
    		@PathVariable Long restaurantId){
         RestaurantMenuDetails restaurantAndMenu = foodMenuService.getRestaurantAndMenu(restaurantId);
        return ResponseEntity.ok(restaurantAndMenu);
    }
    
    @GetMapping("/food_item/{foodItemId}")
    public ResponseEntity<?> fetchFoodItemDetails(
    		@PathVariable Long foodItemId){
       FoodItemDTO foodItemDetails = foodMenuService.getFoodItemDetails(foodItemId);
        return ResponseEntity.ok(foodItemDetails);
    }



}
