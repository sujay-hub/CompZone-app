package com.project1.ecommerce.compstore.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

//import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.dto.AllOrdersDTO;
import com.project1.ecommerce.compstore.dto.OrderHistoryDTO;
import com.project1.ecommerce.compstore.entities.CartItems;
import com.project1.ecommerce.compstore.entities.Orders;
import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.payload.PlaceOrderRequest;
import com.project1.ecommerce.compstore.services.OrdersService;
import com.project1.ecommerce.compstore.services.ProductsService;
import com.project1.ecommerce.compstore.services.UsersService;

import jakarta.validation.Valid;

@RestController
public class OrdersController {

	@Autowired
	public ProductsService productsService;
	@Autowired
	public OrdersService ordersService;
	@Autowired
	public UsersService usersService;
	
	@PostMapping("/place-order")
	public ResponseEntity<String> placeOrder(@RequestBody @Valid PlaceOrderRequest request, Principal principal){
		String email = principal.getName();
		 System.out.println("🔍 Email from token: " + email);
		Users user = usersService.getUsersByEmail(email);
		 if (user == null) {
		        //System.out.println("❌ No user found for email: " + email);
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
		    }
		ordersService.placeOrder(user, request.getCartItems(), request.getShippingAddress(), request.getPaymentStatus());
		return ResponseEntity.ok("Order has been placed successfully.");
	}
	
	//17. End-point for order-history. Next will be crud for categories
	@GetMapping("/orders/history")
	public List<OrderHistoryDTO> getOrderHistory(Principal principal){
		Users user = usersService.getUsersByEmail(principal.getName());
		return ordersService.getOrderHistoryByUserId(user.getUserId());
	}
	
	//Administrator control
	//25. Administrator to view all orders. Go to service impl
	@GetMapping("/admin/orders")
	public ResponseEntity<List<AllOrdersDTO>> getAllOrders(){
		return this.ordersService.getOrdersForAdmin();
	}
}
