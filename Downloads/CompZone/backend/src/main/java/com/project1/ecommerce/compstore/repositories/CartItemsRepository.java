package com.project1.ecommerce.compstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.ecommerce.compstore.entities.CartItems;
import com.project1.ecommerce.compstore.entities.Products;
import com.project1.ecommerce.compstore.entities.Users;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
	List<CartItems> findByUsers_UserId(Integer userId);
	CartItems findByUsersAndProducts(Users users, Products products); //Used to get all cart items for a given user.
	void delete(CartItems item);
}
