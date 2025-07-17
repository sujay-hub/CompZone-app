package com.project1.ecommerce.compstore.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.ecommerce.compstore.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByEmail(String email);
	Optional<Users> findByUserId(Integer userId);
}
