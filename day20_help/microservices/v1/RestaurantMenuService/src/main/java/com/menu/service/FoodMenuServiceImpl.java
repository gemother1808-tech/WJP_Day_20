package com.menu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.menu.custom_exceptions.ResourceNotFoundException;
import com.menu.dto.FoodItemDTO;
import com.menu.dto.RestaurantDTO;
import com.menu.dto.RestaurantMenuDetails;
import com.menu.entities.FoodItem;
import com.menu.repository.FoodItemRepository;

@Service
@Transactional
public class FoodMenuServiceImpl implements FoodMenuService {

	private FoodItemRepository foodItemRepo;
	private RestTemplate restTemplate;
	private ModelMapper modelMapper;
	@Value("${restaurant.get}") //value based D.I
	private String url;

	// parameterized ctor for D.I
	//Did not use here - @AllArgCtor  - since the value of the url is getting injected via @Value , from the poprerties file
	public FoodMenuServiceImpl(FoodItemRepository foodItemRepo, RestTemplate restTemplate, ModelMapper modelMapper) {
		super();
		this.foodItemRepo = foodItemRepo;
		this.restTemplate = restTemplate;
		this.modelMapper = modelMapper;
	}

	public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
		// validate restaurant with it's id
		RestaurantDTO restaurantDTO = 
				fetchRestaurantDetailsFromRestaurantMS
				(foodItemDTO.getRestaurantId());
		// restaurant exists , proceed to adding food item to the menu
		FoodItem foodItemSaved = foodItemRepo.save
				(modelMapper.map(foodItemDTO, FoodItem.class));
		return modelMapper.map(foodItemSaved, FoodItemDTO.class);
	}

	public RestaurantMenuDetails getRestaurantAndMenu(Long restaurantId) {
		List<FoodItemDTO> foodItemList = fetchFoodItemList(restaurantId);
		RestaurantDTO restaurant = fetchRestaurantDetailsFromRestaurantMS(restaurantId);
		return createCompleteDetails(foodItemList, restaurant);
	}

	private RestaurantMenuDetails createCompleteDetails(List<FoodItemDTO> foodItemList, RestaurantDTO restaurant) {
		return new RestaurantMenuDetails(restaurant, foodItemList);
	}

	private RestaurantDTO fetchRestaurantDetailsFromRestaurantMS(Long restaurantId) {
		return restTemplate.getForObject(url, RestaurantDTO.class, restaurantId);
	}

	private List<FoodItemDTO> fetchFoodItemList(Long restaurantId) {
		return foodItemRepo.findByRestaurantId(restaurantId).stream()
				.map(foodItem -> modelMapper.map(foodItem, FoodItemDTO.class)).collect(Collectors.toList());
	}

	@Override
	public FoodItemDTO getFoodItemDetails(Long foodItemId) {
		FoodItem foodItem = foodItemRepo.findById(foodItemId)
				.orElseThrow(() -> new ResourceNotFoundException("Food item doesn't exist with ID=" + foodItemId));
		return modelMapper.map(foodItem, FoodItemDTO.class);
	}

}
