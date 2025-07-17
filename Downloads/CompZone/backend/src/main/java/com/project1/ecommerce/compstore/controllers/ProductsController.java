package com.project1.ecommerce.compstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.dto.ProductDTO;
import com.project1.ecommerce.compstore.entities.Products;
import com.project1.ecommerce.compstore.repositories.ProductsRepository;
import com.project1.ecommerce.compstore.services.ProductsService;

import jakarta.validation.Valid;

@RestController
public class ProductsController {
	
	@Autowired
	public ProductsService productsService;
	
	@Autowired
	public ProductsRepository productsRepository;
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		return ResponseEntity.ok(productsService.getAllProducts());
	}
	
	@GetMapping("/products/{productId}") //changes 3rd July
	public ProductDTO getProduct(@PathVariable String productId) {
		return this.productsService.getProduct(Long.parseLong(productId));
	}
	
	@GetMapping("/products/category/{categoryId}")
		public List<Products> getProductsByCategory(@PathVariable String categoryId) {
			return this.productsService.findByCategoryId(Long.parseLong(categoryId));
		}
	
	@PostMapping(path = "admin/products", consumes = "application/json") //changes 3rd July
	public Products addProduct(@RequestBody @Valid Products product) {
		return this.productsService.addProduct(product);
	}
	
	@PutMapping("admin/products/{productId}") //changes 3rd July
	public Products updateProduct(@PathVariable Long productId, @RequestBody @Valid Products product) {
		product.setProductId(productId);
		return this.productsService.updateProduct(product);
	}
	
	@DeleteMapping("admin/products/{productId}") //changes 3rd July
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable String productId) {
		try {
			return this.productsService.deleteProduct(productId);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//27. Administrator to update product stock. Next is Admin views all users. Go to UsersController
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/admin/products/{productId}/stock")
	public ResponseEntity<String> updateStock(@PathVariable Long productId, @RequestParam Integer newStock){
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		
		product.setStockQuantity(newStock);
		productsRepository.save(product);
		
		return ResponseEntity.ok("Stock updated successfully.");
	}
	
}
