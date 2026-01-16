package com.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodItemDTO {
	//food item id property will be used only during serialization (Java -> JSON) i.e skipped during de-ser.
	@JsonProperty(access = Access.READ_ONLY)
    private Long id;
    private String itemName;
    private String itemDescription;
    private boolean isVeg;
    private int price;
    private Long restaurantId;
    
}
