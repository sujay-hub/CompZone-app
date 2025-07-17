package com.project1.ecommerce.compstore.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;



public class OrderItemsDTO {
	@NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal priceAtTime;

	public OrderItemsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItemsDTO(@NotBlank(message = "Product name cannot be blank") String productName,
			@NotNull(message = "Quantity is required") @Positive(message = "Quantity must be greater than zero") Integer quantity,
			@NotNull(message = "Price is required") @Positive(message = "Price must be greater than zero") BigDecimal priceAtTime) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.priceAtTime = priceAtTime;
	}

	@Override
	public String toString() {
		return "OrderItemsDTO [productName=" + productName + ", quantity=" + quantity + ", priceAtTime=" + priceAtTime
				+ "]";
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
	
	//Next in OrderHistoryDTO
}
