package com.project1.ecommerce.compstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class RemoveFromCartRequest {

	@NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Product ID is required")
    private Long productId;

	public RemoveFromCartRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RemoveFromCartRequest(@NotNull(message = "User ID is required") Integer userId,
			@NotNull(message = "Product ID is required") Long productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "RemoveFromCartRequest [userId=" + userId + ", productId=" + productId + "]";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
