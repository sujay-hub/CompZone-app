package com.project1.ecommerce.compstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	//22. Used to let the correct user log in. Login part ends here. Next payment, go to PaymentController.
	@Autowired
	public UsersRepository usersRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Users> userOptional = usersRepository.findByEmail(email);
		Users user  = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		//29. GrantedAuthority i.e Administrator. Next 2 lines. Then go to SecurityConfig requestMatchers
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				authorities
				);
	}

}
