package com.project1.ecommerce.compstore.payload;

import java.util.List;

import com.project1.ecommerce.compstore.dto.CartItemsDTO;
import com.project1.ecommerce.compstore.entities.CartItems;
import com.project1.ecommerce.compstore.enums.PaymentStatus;




public class PlaceOrderRequest {
	private Integer userId;
	private List<CartItemsDTO> cartItems;
	private String shippingAddress;
    private	PaymentStatus paymentStatus;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<CartItemsDTO> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemsDTO> cartItems) {
		this.cartItems = cartItems;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public PlaceOrderRequest(Integer userId, List<CartItemsDTO> cartItems, String shippingAddress, PaymentStatus paymentStatus) {
		super();
		this.userId = userId;
		this.cartItems = cartItems;
		this.shippingAddress = shippingAddress;
		this.paymentStatus = paymentStatus;
	}
	public PlaceOrderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PlaceOrderRequest [userId=" + userId + ", cartItems=" + cartItems + ", shippingAddress="
				+ shippingAddress + ", paymentStatus=" + paymentStatus + "]";
	}
	
	
}
