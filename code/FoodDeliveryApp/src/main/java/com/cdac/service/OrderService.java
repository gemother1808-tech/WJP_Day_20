package com.cdac.service;

import com.cdac.dto.OrderResponse;
import com.cdac.dto.PlaceOrderDTO;

public interface OrderService {

	OrderResponse placeOrder(PlaceOrderDTO dto);

}
