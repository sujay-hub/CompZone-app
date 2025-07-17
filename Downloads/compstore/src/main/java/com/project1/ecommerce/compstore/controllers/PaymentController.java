package com.project1.ecommerce.compstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.services.RazorpayService;
import com.razorpay.Order;

//23. Controller for Payment. Next go to its service
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private RazorpayService razorpayService;
	
	@GetMapping("/create")
	public ResponseEntity<?> createOrder(@RequestParam Integer amount){
		try {
			Order order = razorpayService.razorpayOrder(amount);
			return ResponseEntity.ok(order.toString()); //Send order JSON to front end
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body("Error creating Razorpay order: "+e.getMessage());
		}
	}
}
