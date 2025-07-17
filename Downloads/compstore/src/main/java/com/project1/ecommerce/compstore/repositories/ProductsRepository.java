package com.project1.ecommerce.compstore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.ecommerce.compstore.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
	List<Products> findByCategoryId(Long categoryId);
	Optional<Products> findByProductId(Long productId);
	//32. Pagination start. Next to ProductsServiceImpl
	Page<Products> findAll(Pageable pageable);
	Page<Products> findByCategory_CategoryId(Long categoryId, Pageable pageable);
	
	//Searching sorting one is right above
	Page<Products> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
	
	
}
 