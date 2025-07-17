package com.project1.ecommerce.compstore.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Products")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "category_id", insertable = false, updatable = false)
	private Integer categoryId;
	
	@Column(name = "Price", precision = 10, scale = 3)
	private BigDecimal price;
	
	@Column(name = "stock_quantity")
	private Integer stockQuantity;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Categories category;
	
	@Override
	public String toString() {
		return "Products [productId=" + productId + ", productName=" + productName + ", description=" + description
				+ ", categoryId=" + categoryId + ", price=" + price + ", stockQuantity=" + stockQuantity + ", imageUrl="
				+ imageUrl + ", createdAt=" + createdAt + ", category=" + category + "]";
	}

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public Products(Long productId, String productName, String description, Integer categoryId, BigDecimal price,
			Integer stockQuantity, String imageUrl, LocalDateTime createdAt, Categories category) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.categoryId = categoryId;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
		this.category = category;
	}

	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
