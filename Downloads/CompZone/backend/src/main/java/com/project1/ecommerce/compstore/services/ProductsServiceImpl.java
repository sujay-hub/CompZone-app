package com.project1.ecommerce.compstore.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project1.ecommerce.compstore.dto.ProductDTO;
import com.project1.ecommerce.compstore.entities.Products;
import com.project1.ecommerce.compstore.repositories.ProductsRepository;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	@Autowired
	private ProductsRepository productsRepository;

	
	//CRUD Operations on Products
	//1. Main page- When all available products are displayed on the main page/dash-board
	@Override
	public List<ProductDTO> getAllProducts() {
		List<Products> products = productsRepository.findAll();

	    return products.stream().map(product -> {
	        ProductDTO dto = new ProductDTO();
	        dto.setProductId(product.getProductId());
	        dto.setProductName(product.getProductName());
	        dto.setDescription(product.getDescription());
	        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getCategoryId() : null);
	        dto.setPrice(product.getPrice());
	        dto.setStockQuantity(product.getStockQuantity());
	        dto.setImageUrl(product.getImageUrl());
	        dto.setCreatedAt(product.getCreatedAt() != null ? product.getCreatedAt().toString() : null);
	        return dto;
	    }).collect(Collectors.toList());
	}
	//2. When user wants to search specific product on dash-board.
	@Override
	public ProductDTO getProduct(long productId) {
		Products product = productsRepository.findById(productId)
		        .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

		ProductDTO dto = new ProductDTO();
	    dto.setProductId(product.getProductId());
	    dto.setProductName(product.getProductName());
	    dto.setDescription(product.getDescription());
	    dto.setPrice(product.getPrice());
	    dto.setStockQuantity(product.getStockQuantity());
	    dto.setImageUrl(product.getImageUrl());
	    
	    return dto;
	    
	}
	//3. When user wants to search specific product on dash-board based on its category. Next call in CartServiceImpl.
	@Override
	public List<Products> findByCategoryId(long categoryId) {
		return productsRepository.findByCategoryId(categoryId);
	}

	//10. Administrator adds a product.
	@Override
	public Products addProduct(Products product) {
		return productsRepository.save(product);
	}

	//11. Administrator updates a product.
	@Override
	public Products updateProduct(Products product) {
		long productId = product.getProductId();
		List<Products> allProducts = productsRepository.findAll();
		for(int i = 0; i < allProducts.size(); i++) {
			if(allProducts.get(i).getProductId() == productId) {
				allProducts.get(i).getProductName();
				allProducts.get(i).getDescription();
				allProducts.get(i).getCategoryId();
				allProducts.get(i).getPrice();
				allProducts.get(i).getImageUrl();
				return productsRepository.save(allProducts.get(i));
			}
		}
		return null;
	}

	//12. Administrator deletes a product. Next task in OrdersServiceImpl for after placing the order.
	@Override
	public ResponseEntity<HttpStatus> deleteProduct(String productId) {
		try {
		long prdId = Long.parseLong(productId);
		List<Products> allProducts = productsRepository.findAll();
		for(int i = 0; i < allProducts.size(); i++) {
			if(allProducts.get(i).getProductId() == prdId) {
				productsRepository.deleteById(prdId);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	//33. Pagination Step 2
	@Override
	public Page<Products> getAllProducts(Pageable pageable) {
		return productsRepository.findAll(pageable); 
	}
	@Override
	public Page<Products> getProductsByCategory(Long categoryId, Pageable pageable) {
		return productsRepository.findByCategory_CategoryId(categoryId, pageable);//Go to PaginationController
	}
	@Override
	public Page<Products> searchProductByName(String productName, Pageable pageable) {
		return productsRepository.findByProductNameContainingIgnoreCase(productName, pageable);
	}
	
		
	
}
