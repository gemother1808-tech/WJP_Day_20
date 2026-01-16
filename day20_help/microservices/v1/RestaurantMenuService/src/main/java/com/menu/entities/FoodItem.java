package com.menu.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="food_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodItem extends BaseEntity{   

    private String itemName;

    private String itemDescription;

    private boolean isVeg;

    private int price;
    
    private Long restaurantId;    

}
