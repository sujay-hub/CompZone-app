package com.project1.ecommerce.compstore.services;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorpayClientWrapper {
	
	 private final RazorpayClient client;

	    public RazorpayClientWrapper(RazorpayClient client) {
	        this.client = client;
	    }

	    public Order createOrder(JSONObject request) throws RazorpayException {
	        return client.orders.create(request);
	    }
}
