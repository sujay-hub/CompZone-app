package com.project1.ecommerce.compstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;



public class OrderHistoryDTO {
	private Integer orderId;
	
	@JsonFormat(pattern = "dd-MM-yyyy' 'HH:mm:ss")
	public LocalDateTime createdAt;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    private BigDecimal totalAmount;

    @NotNull(message = "Order items list cannot be null")
    private List<@Valid OrderItemsDTO> items;
	private Integer userId;
	public OrderHistoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderHistoryDTO(Integer orderId, LocalDateTime createdAt,
			@NotNull(message = "Total amount is required") @Positive(message = "Total amount must be positive") BigDecimal totalAmount,
			@NotNull(message = "Order items list cannot be null") List<@Valid OrderItemsDTO> items, Integer userId) {
		super();
		this.orderId = orderId;
		this.createdAt = createdAt;
		this.totalAmount = totalAmount;
		this.items = items;
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "OrderHistoryDTO [orderId=" + orderId + ", createdAt=" + createdAt + ", totalAmount=" + totalAmount
				+ ", items=" + items + ", userId=" + userId + "]";
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<OrderItemsDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemsDTO> items) {
		this.items = items;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	//Next create method getOrderHistoryByUserId in OrdersService
}
