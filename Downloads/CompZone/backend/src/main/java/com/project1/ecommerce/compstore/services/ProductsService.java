package com.project1.ecommerce.compstore.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project1.ecommerce.compstore.dto.ProductDTO;
import com.project1.ecommerce.compstore.entities.Products;

public interface ProductsService {
	public List<ProductDTO> getAllProducts();
	
	public ProductDTO getProduct(long productId);
	
	public List<Products> findByCategoryId(long categoryId);
	
	public Products addProduct(Products product);
	
	public Products updateProduct(Products product);
	
	public ResponseEntity<HttpStatus> deleteProduct(String product);
	
	Page<Products> getAllProducts(Pageable pageable);
	
	Page<Products> getProductsByCategory(Long categoryId, Pageable pageable);
	
	Page<Products> searchProductByName(String productName, Pageable pageable);
}
