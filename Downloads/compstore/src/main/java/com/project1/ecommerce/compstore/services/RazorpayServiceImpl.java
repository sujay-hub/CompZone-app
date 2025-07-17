package com.project1.ecommerce.compstore.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;


//24. Payment part ends here. Next is Administrator panel. First go to AllOrdersDTO
@Service
public class RazorpayServiceImpl implements RazorpayService {
	
	@Autowired
	private final RazorpayClientWrapper clientWrapper;
	
	public RazorpayServiceImpl(RazorpayClientWrapper clientWrapper) {
        this.clientWrapper = clientWrapper;
    }
	
	@Override
	public Order razorpayOrder(Integer amountInRupees) throws Exception {
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amountInRupees * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "txn_"+ System.currentTimeMillis());
		orderRequest.put("payment_capture", 1);
		
		return clientWrapper.createOrder(orderRequest);
	}

}
