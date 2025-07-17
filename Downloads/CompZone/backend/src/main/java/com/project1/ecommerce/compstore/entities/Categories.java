package com.project1.ecommerce.compstore.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Categories")
public class Categories {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	public Integer categoryId;
	
	@Column(name = "CategoryName")
	public String categoryName;
	
	@Column(name = "Description")
	public String description;
	
	
	public Categories() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Categories(Integer categoryId, String categoryName, String description) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.description = description;
	}



	@Override
	public String toString() {
		return "Categories [categoryId=" + categoryId + ", categoryName=" + categoryName + ", description="
				+ description + "]";
	}



	public Integer getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	
}
