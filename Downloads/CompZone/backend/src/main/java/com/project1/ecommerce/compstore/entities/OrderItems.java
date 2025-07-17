package com.project1.ecommerce.compstore.entities;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Order_Items")
public class OrderItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderitem_id")
	public Integer orderItemId;
		
	@Column(name = "Quantity")
	public Integer quantity;
	
	@Column(name = "PriceAtTime",precision = 10, scale = 3)
	public BigDecimal priceAtTime;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	public Orders orders;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	public Products products;
	
	@Override
	public String toString() {
		return "OrderItems [orderItemId=" + orderItemId + ", quantity=" + quantity + ", priceAtTime=" + priceAtTime
				+ ", orders=" + orders + ", products=" + products + "]";
	}

	public OrderItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItems(Integer orderItemId, Integer quantity, BigDecimal priceAtTime, Orders orders, Products products) {
		super();
		this.orderItemId = orderItemId;
		this.quantity = quantity;
		this.priceAtTime = priceAtTime;
		this.orders = orders;
		this.products = products;
	}

	
	
	
	
	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPriceAtTime() {
		return priceAtTime;
	}

	public void setPriceAtTime(BigDecimal priceAtTime) {
		this.priceAtTime = priceAtTime;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	
	
}
