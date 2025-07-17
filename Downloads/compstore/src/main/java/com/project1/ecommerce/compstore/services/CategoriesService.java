package com.project1.ecommerce.compstore.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project1.ecommerce.compstore.entities.Categories;

public interface CategoriesService {
	public List<Categories> getAllCategories();
	
	public Categories addCategory(Categories category);
	
	public Categories updateCategories(Categories category);
	
	public ResponseEntity<HttpStatus> deleteCategory(String categoryId);
}
