package com.project1.ecommerce.compstore.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project1.ecommerce.compstore.dto.AllOrdersDTO;
import com.project1.ecommerce.compstore.dto.CartItemsDTO;
import com.project1.ecommerce.compstore.dto.OrderHistoryDTO;
import com.project1.ecommerce.compstore.entities.CartItems;
import com.project1.ecommerce.compstore.entities.Orders;
import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.enums.PaymentStatus;

public interface OrdersService {
	public void placeOrder(Users user, List<CartItemsDTO> cartItems, String shippingAddress, PaymentStatus paymentStatus);
	List<OrderHistoryDTO> getOrderHistoryByUserId(Integer userId);
	//Next create its implementation in OrdersServiceImpl
	
	public ResponseEntity<List<AllOrdersDTO>> getOrdersForAdmin();
	public Orders getOrderById(Integer orderId);
}
