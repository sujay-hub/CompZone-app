package com.project1.ecommerce.compstore.dto;

import java.math.BigDecimal;

public class ProductDTO {
	private Long productId;
    private String productName;
    private String description;
    private Integer categoryId;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private String createdAt;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public ProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductDTO(Long productId, String productName, String description, Integer categoryId, BigDecimal price,
			Integer stockQuantity, String imageUrl, String createdAt) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.categoryId = categoryId;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "ProductDTO [productId=" + productId + ", productName=" + productName + ", description=" + description
				+ ", categoryId=" + categoryId + ", price=" + price + ", stockQuantity=" + stockQuantity + ", imageUrl="
				+ imageUrl + ", createdAt=" + createdAt + "]";
	}
    
    
}
