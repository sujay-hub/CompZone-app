package com.project1.ecommerce.compstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class AllOrdersDTO {
	 private Integer orderId;

	    @NotBlank(message = "Email cannot be blank")
	    @Email(message = "Email should be valid")
	    private String email;

	    @NotNull(message = "Total amount is required")
	    @Positive(message = "Total amount must be greater than zero")
	    private BigDecimal totalAmount;

	    private LocalDateTime createdAt;

		public AllOrdersDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

		public AllOrdersDTO(Integer orderId,
				@NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String email,
				@NotNull(message = "Total amount is required") @Positive(message = "Total amount must be greater than zero") BigDecimal totalAmount,
				LocalDateTime createdAt) {
			super();
			this.orderId = orderId;
			this.email = email;
			this.totalAmount = totalAmount;
			this.createdAt = createdAt;
		}

		@Override
		public String toString() {
			return "AllOrdersDTO [orderId=" + orderId + ", email=" + email + ", totalAmount=" + totalAmount
					+ ", createdAt=" + createdAt + "]";
		}

		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public BigDecimal getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
}//next is OrdersController
