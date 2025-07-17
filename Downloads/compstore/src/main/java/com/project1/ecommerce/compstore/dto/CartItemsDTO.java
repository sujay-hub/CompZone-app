package com.project1.ecommerce.compstore.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class CartItemsDTO {
	
	private Long productId;
	
	 public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@NotBlank(message = "Product name cannot be blank")
	    private String productName;

	    private String imageUrl;

	    @NotNull(message = "Price is required")
	    @Positive(message = "Price must be positive")
	    private BigDecimal price;

	    @NotNull(message = "Quantity is required")
	    @Positive(message = "Quantity must be greater than zero")
	    private Integer quantity;

		public CartItemsDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

		public CartItemsDTO(Long productId,@NotBlank(message = "Product name cannot be blank") String productName, String imageUrl,
				@NotNull(message = "Price is required") @Positive(message = "Price must be positive") BigDecimal price,
				@NotNull(message = "Quantity is required") @Positive(message = "Quantity must be greater than zero") Integer quantity) {
			super();
			this.productId = productId;
			this.productName = productName;
			this.imageUrl = imageUrl;
			this.price = price;
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "CartItemsDTO [productId=" + productId + ", productName=" + productName + ", imageUrl=" + imageUrl
					+ ", price=" + price + ", quantity=" + quantity + "]";
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
}
