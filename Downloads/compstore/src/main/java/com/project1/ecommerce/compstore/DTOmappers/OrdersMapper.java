package com.project1.ecommerce.compstore.DTOmappers;

import java.util.stream.Collectors;

import com.project1.ecommerce.compstore.dto.OrderHistoryDTO;
import com.project1.ecommerce.compstore.entities.Orders;
import com.project1.ecommerce.compstore.services.OrderItemsMapperService;
import com.project1.ecommerce.compstore.services.OrdersService;

public class OrdersMapper {
	 private final OrderItemsMapperService oiMapper;

	  public OrdersMapper(OrderItemsMapperService oiMapper) {
	    this.oiMapper = oiMapper;
	  }

	  public OrderHistoryDTO toDTO(Orders order) {
	    OrderHistoryDTO dto = new OrderHistoryDTO();
	    dto.setOrderId(order.getOrderId());
	    dto.setCreatedAt(order.getCreatedAt());
	    dto.setTotalAmount(order.getTotalAmount());
	    dto.setUserId(order.getUsers().getUserId());
	    dto.setItems(order.getItems().stream()
	        .map(oiMapper::toDTO)
	        .collect(Collectors.toList()));
	    return dto;
	  }

	  public Orders toEntity(OrderHistoryDTO dto) {
	    Orders order = new Orders();
	    order.setTotalAmount(dto.getTotalAmount());
	    // Set other fields (users, status, paymentStatus, email) elsewhere
	    return order;
	  }
}
