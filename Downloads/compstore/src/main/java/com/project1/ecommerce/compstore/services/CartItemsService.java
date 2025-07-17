package com.project1.ecommerce.compstore.services;

import java.util.List;

import com.project1.ecommerce.compstore.dto.CartItemsDTO;

public interface CartItemsService {
	List<CartItemsDTO> getCartItemsByUserId(Integer userId);
	void addToCart(Integer userId, Long productId, Integer quantity);
	void updateCartQuantity(Integer userId, Long productId, Integer quantity);
	void removeFromCart(Integer userId, Long productId);
	void clearCartByUserId(Integer userId);
}
