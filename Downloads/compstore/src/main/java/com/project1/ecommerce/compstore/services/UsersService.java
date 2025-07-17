package com.project1.ecommerce.compstore.services;

import com.project1.ecommerce.compstore.entities.Users;

public interface UsersService {
	public Users getUsersById(long userId);
	public Users getUsersByEmail(String email);
	void registerUser(Users user);
}
