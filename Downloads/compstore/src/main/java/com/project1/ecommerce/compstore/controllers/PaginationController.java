package com.project1.ecommerce.compstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.entities.Products;
import com.project1.ecommerce.compstore.services.ProductsService;

@RestController
@RequestMapping("/pagination")
public class PaginationController {
	
	@Autowired
	private ProductsService productsService;
	
	@GetMapping
	public Page<Products> getPaginatedProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(defaultValue = "productName") String sortField,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		 Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
		    Pageable pageable = PageRequest.of(page, size, sort);
		    
		    if (categoryId != null) {
		        return productsService.getProductsByCategory(categoryId, pageable);
		    } else {
		        return productsService.getAllProducts(pageable);
		    }

	}//Pagination ends here.
	
	@GetMapping("/search")
	public Page<Products> searchProducts(
			@RequestParam String productName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size 
			){
		Pageable pageable = PageRequest.of(page, size);
		return productsService.searchProductByName(productName, pageable);
	}
}
