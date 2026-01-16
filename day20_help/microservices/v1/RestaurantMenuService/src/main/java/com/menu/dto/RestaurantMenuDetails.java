package com.menu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantMenuDetails {
	private RestaurantDTO restaurant;
	private List<FoodItemDTO> foodItems;
}
