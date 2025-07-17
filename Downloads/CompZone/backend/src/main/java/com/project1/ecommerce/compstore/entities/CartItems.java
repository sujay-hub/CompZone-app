package com.project1.ecommerce.compstore.entities;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Cart_Items")
public class CartItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Integer itemId;
	
	@Column(name = "Quantity")
	private Integer quantity;
	
	@Column(name = "AddedAt", updatable = false)
	private LocalDateTime addedAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products products;

	
	
	@Override
	public String toString() {
		return "CartItems [itemId=" + itemId + ", quantity=" + quantity + ", addedAt=" + addedAt + ", users=" + users
				+ ", products=" + products + "]";
	}

	public CartItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getItemId() {
		return itemId;
	}

	public CartItems(Integer itemId, Integer quantity, LocalDateTime addedAt, Users users, Products products) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
		this.addedAt = addedAt;
		this.users = users;
		this.products = products;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	
}
