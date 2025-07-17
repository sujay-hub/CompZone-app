package com.project1.ecommerce.compstore.services;

import com.razorpay.Order;

public interface RazorpayService {
	Order razorpayOrder(Integer amountInRupees) throws Exception;
}
