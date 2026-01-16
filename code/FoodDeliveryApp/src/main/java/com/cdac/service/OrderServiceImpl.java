package com.cdac.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.FoodItemDao;
import com.cdac.dao.OrderDao;
import com.cdac.dao.RestaurantDao;
import com.cdac.dao.UserDao;
import com.cdac.dto.ApiResponse;
import com.cdac.dto.FoodOrderItem;
import com.cdac.dto.OrderResponse;
import com.cdac.dto.PlaceOrderDTO;
import com.cdac.entities.FoodItem;
import com.cdac.entities.Order;
import com.cdac.entities.OrderLine;
import com.cdac.entities.OrderStatus;
import com.cdac.entities.Restaurant;
import com.cdac.entities.User;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderDao orderDao;
	private final UserDao userDao;
	private final RestaurantDao restaurantDao;
	private FoodItemDao foodItemDao;
	private ModelMapper modelMapper;

	@Override
	public OrderResponse placeOrder(PlaceOrderDTO order) {
		// 1. Validate n get customer details
		User customer = userDao.findById(order.getCustomerId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid customer id"));
		// 2. Validate n get restaurant details by its id
		Restaurant restaurant = restaurantDao.findById(order.getRestaurantId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid restaurant id !!!"));
		// 3. Create new order entity
		Order orderEntity = new Order();
		//4. Order *--->1 Customer
		orderEntity.setCustomer(customer);
		//5 . Order * ---> 1 Restaurant
		orderEntity.setRestaurant(restaurant);
		//6 set order status
		orderEntity.setOrderStatus(OrderStatus.NEW);
		//7 set delivery time - in 30 minutes
		orderEntity.setDeliveryDateTime(LocalDateTime.now().plusMinutes(30));
		//8. save order (parent record for order lines)
		Order savedOrder = orderDao.save(orderEntity);
		//9. create order lines
		createOrderLines(savedOrder,order.getItems());
		//10. Generate order response
		OrderResponse resp=new OrderResponse(
				customer.getFirstName(),customer.getLastName(),
				savedOrder.getOrderAmount(),savedOrder.getDeliveryDateTime());
		return resp;
	}

	private void createOrderLines(Order savedOrder,
			List<FoodOrderItem> items) {
		items.stream()
		.map(item -> createOrderLine(item))
		.forEach(orderLine -> savedOrder.addOrderLine(orderLine));		
	}

	private OrderLine createOrderLine(FoodOrderItem item) {
		// 1. get food item from order item id
		FoodItem foodItem=foodItemDao
				.findById(item.getFoodItemId())
				.orElseThrow(() -> new ResourceNotFoundException("Food item doesn't exist"));
		//2. Create OrderLine 
		OrderLine orderLine=new OrderLine();
		orderLine.setFoodItem(foodItem);
		orderLine.setQuantity(item.getQuantity());
		double currentPrice=(foodItem.getPrice()*(100-item.getDiscount()))/100;
		orderLine.setPrice(currentPrice);
		orderLine.setSubTotal(currentPrice*item.getQuantity());
		return orderLine;
	}

}
