package com.cdac.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderDTO {
	private Long customerId;
	private Long restaurantId;
	private List<FoodOrderItem> items;
}
