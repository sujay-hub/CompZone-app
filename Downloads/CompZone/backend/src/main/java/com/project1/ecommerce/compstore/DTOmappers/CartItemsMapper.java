package com.project1.ecommerce.compstore.DTOmappers;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.project1.ecommerce.compstore.dto.CartItemsDTO;
import com.project1.ecommerce.compstore.entities.CartItems;

@Component
public class CartItemsMapper {
	public CartItemsDTO toDTO(CartItems cart) {
	    CartItemsDTO dto = new CartItemsDTO();
	    if (cart.getProducts() != null) {
	    dto.setProductId(cart.getProducts().getProductId());
	    dto.setProductName(cart.getProducts().getProductName());
	    dto.setImageUrl(cart.getProducts().getImageUrl());
	    dto.setPrice(cart.getProducts().getPrice());
	    //dto.setQuantity(cart.getQuantity());
	    }
	    else if(cart.getProducts() == null) {
	    	dto.setProductId(-1L);
	    	dto.setProductName("Unknown");
	        dto.setImageUrl("");
	        dto.setPrice(BigDecimal.ZERO);
	    }
	    dto.setQuantity(cart.getQuantity());
	    return dto;
	  }

	  public CartItems toEntity(CartItemsDTO dto) {
	    CartItems cart = new CartItems();
	    cart.setQuantity(dto.getQuantity());
	    // Set users, products, addedAt, itemId, etc elsewhere
	    return cart;
	  }
}
