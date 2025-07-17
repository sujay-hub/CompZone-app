package com.project1.ecommerce.compstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.ecommerce.compstore.entities.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
	Optional<Categories> findByCategoryId(Long Category);
}
