package com.restaurant.entities;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Restaurant extends BaseEntity{    
    private String name;
    private String address;
    private String city;
    private String description;
}
