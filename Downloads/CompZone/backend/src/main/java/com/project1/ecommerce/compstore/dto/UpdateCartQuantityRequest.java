package com.project1.ecommerce.compstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UpdateCartQuantityRequest {
	 @NotNull(message = "User ID is required")
	    private Integer userId;

	    @NotNull(message = "Product ID is required")
	    private Long productId;

	    @NotNull(message = "Quantity is required")
	    @Positive(message = "Quantity must be greater than zero")
	    private Integer quantity;

		public UpdateCartQuantityRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public UpdateCartQuantityRequest(@NotNull(message = "User ID is required") Integer userId,
				@NotNull(message = "Product ID is required") Long productId,
				@NotNull(message = "Quantity is required") @Positive(message = "Quantity must be greater than zero") Integer quantity) {
			super();
			this.userId = userId;
			this.productId = productId;
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "UpdateCartQuantityRequest [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity
					+ "]";
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

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
}
