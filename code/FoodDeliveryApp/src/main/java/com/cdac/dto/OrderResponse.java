package com.cdac.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
	private String firstName;
	private String lastName;
	private double totalAmount;
	private LocalDateTime deliveryDateTime;
}
