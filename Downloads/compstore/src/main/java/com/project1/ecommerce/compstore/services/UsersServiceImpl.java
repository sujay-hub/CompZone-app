package com.project1.ecommerce.compstore.services;



import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.enums.Role;
import com.project1.ecommerce.compstore.exceptions.UserNotFoundException;
import com.project1.ecommerce.compstore.repositories.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
//	private PasswordEncoder passwordEncoder;
	
	 //9. To be implemented. Would be used for fetching user details for profile, order history, etc.
	@Override
	public Users getUsersById(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	//8. Used during login or profile check to retrieve user by email. Next task in ProductsServiceImpl
	@Override
	public Users getUsersByEmail(String email) {
		return usersRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found with email: "+email));
	}
	
	//20. Registration of user, check the PasswordEncoder reference above. After this, steps for login, go to SecurityConfig
	@Override
	public void registerUser(Users user) {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		//31. Roles to be set on registration, next line. After this, pagination. Go to ProductsRepository
		user.setRole(Role.CUSTOMER);
		
		usersRepository.save(user); //After this go to SecurityConfig.java
	}

}
