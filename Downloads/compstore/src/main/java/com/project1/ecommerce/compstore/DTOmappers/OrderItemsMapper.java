package com.project1.ecommerce.compstore.DTOmappers;

import org.springframework.stereotype.Component;

import com.project1.ecommerce.compstore.dto.OrderItemsDTO;
import com.project1.ecommerce.compstore.entities.OrderItems;

@Component
public class OrderItemsMapper {
	 public OrderItemsDTO toDTO(OrderItems item) {
		    OrderItemsDTO dto = new OrderItemsDTO();
		    dto.setProductName(item.getProducts().getProductName());
		    dto.setQuantity(item.getQuantity());
		    dto.setPriceAtTime(item.getPriceAtTime());
		    return dto;
		  }

		  public OrderItems toEntity(OrderItemsDTO dto) {
		    OrderItems item = new OrderItems();
		    item.setQuantity(dto.getQuantity());
		    item.setPriceAtTime(dto.getPriceAtTime());
		    return item;
		  }
}
