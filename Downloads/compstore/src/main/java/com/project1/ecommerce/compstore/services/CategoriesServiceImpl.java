package com.project1.ecommerce.compstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project1.ecommerce.compstore.entities.Categories;
import com.project1.ecommerce.compstore.repositories.CategoriesRepository;

@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	public CategoriesRepository categoriesRepository;
	
	@Autowired
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

	@Override
	public List<Categories> getAllCategories() {
		return categoriesRepository.findAll();
	}

	@Override
	public Categories addCategory(Categories category) {
		return categoriesRepository.save(category);
	}

	@Override
	public Categories updateCategories(Categories category) {
		long categoryId = category.getCategoryId();
		List<Categories> allCategories = categoriesRepository.findAll();
		for(int i = 0; i < allCategories.size(); i++) {
			if(allCategories.get(i).getCategoryId() == categoryId) {
				allCategories.get(i).getCategoryName();
				allCategories.get(i).getDescription();
				return categoriesRepository.save(allCategories.get(i));
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<HttpStatus> deleteCategory(String categoryId) {
		try {
			long ctId = Long.parseLong(categoryId);
			List<Categories> allCategories = categoriesRepository.findAll();
			for(int i = 0; i < allCategories.size(); i++) {
				if(allCategories.get(i).getCategoryId() == ctId) {
					categoriesRepository.deleteById(ctId);
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}//Next will be user registration/login task. Creating AuthController.java
	}

}
