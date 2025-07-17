package com.project1.ecommerce.compstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.repositories.UsersRepository;

@RestController
public class UsersController {
	
	@Autowired
	public UsersRepository usersRepository;
	
	//28. Administrator can view all users. Next Go to UserDetailsServiceImpl to include roles in the form of GrantedAuthority.
	@GetMapping("/admin/users")
	public ResponseEntity<List<Users>> getAllUsers(){
		return ResponseEntity.ok(usersRepository.findAll());
	}
}
