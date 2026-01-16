package com.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.menu.entities.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
	//derived finder method
	List<FoodItem> findByRestaurantId(Long restaurantId);
}
