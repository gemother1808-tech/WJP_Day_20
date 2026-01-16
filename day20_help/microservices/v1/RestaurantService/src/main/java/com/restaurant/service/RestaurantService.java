package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RestaurantDTO;

public interface RestaurantService {
	  List<RestaurantDTO> findAllRestaurants();
	  RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO);
	  RestaurantDTO fetchRestaurantById(Long id) ;
		  
		 
}
