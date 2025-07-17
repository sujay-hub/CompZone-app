package com.project1.ecommerce.compstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.entities.Categories;
import com.project1.ecommerce.compstore.services.CategoriesService;

import jakarta.validation.Valid;
//18. Crud for categories. Ends with service impl of this.
@RestController
public class CategoriesController {

	
	public CategoriesService categoriesService;
	
	@Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }
	
	@GetMapping("/categories")
	public List<Categories> getAllCategories(){
		return this.categoriesService.getAllCategories();
	}
		
	@PostMapping(path = "/create-category", consumes = "application/json")
	public Categories addCategory(@RequestBody @Valid Categories category) {
		return this.categoriesService.addCategory(category);
	}
	
	@PutMapping("/update-category/id/{categoryId}")
	public Categories updateCategories(@PathVariable Long categoryId, @RequestBody @Valid Categories category) {
		category.setCategoryId(categoryId.intValue());
		return this.categoriesService.updateCategories(category);
	}
	
	@DeleteMapping("/delete-category/{categoryId}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable String categoryId){
		try {
			return this.categoriesService.deleteCategory(categoryId);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
