package com.restaurant.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.custom_exceptions.ResourceNotFoundException;
import com.restaurant.dto.RestaurantDTO;
import com.restaurant.entities.Restaurant;
import com.restaurant.repository.RestaurantRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private RestaurantRepository restaurantRepo;
    private ModelMapper modelMapper;      


    public List<RestaurantDTO> findAllRestaurants() {
              return restaurantRepo.findAll().stream()
            		  .map(restarunt -> modelMapper.map(restarunt, RestaurantDTO.class))
            		  .collect(Collectors.toList());
        		
    }

    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
        Restaurant savedRestaurant =  restaurantRepo.save
        		(modelMapper.map(restaurantDTO, Restaurant.class));
        //Entity -> DTO
        return modelMapper.map(savedRestaurant, RestaurantDTO.class);
    }

    public RestaurantDTO fetchRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepo.findById(id).
        		orElseThrow(() -> 
        		new ResourceNotFoundException("Restaurant not found with ID "+id));
        return modelMapper.map(restaurant, RestaurantDTO.class);
       
    }
}
