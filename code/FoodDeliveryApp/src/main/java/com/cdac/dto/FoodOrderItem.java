package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodOrderItem {
	private Long foodItemId;
	private int quantity;
	private double discount;//discount in %
}
