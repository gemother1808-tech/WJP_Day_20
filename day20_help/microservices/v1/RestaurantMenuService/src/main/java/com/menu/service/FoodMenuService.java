package com.menu.service;

import com.menu.dto.FoodItemDTO;
import com.menu.dto.RestaurantMenuDetails;

public interface FoodMenuService {
	   FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO);
	    RestaurantMenuDetails getRestaurantAndMenu(Long restaurantId) ;
		FoodItemDTO getFoodItemDetails(Long foodItemId);		  
}
