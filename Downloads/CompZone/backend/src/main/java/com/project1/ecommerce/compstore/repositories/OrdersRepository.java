package com.project1.ecommerce.compstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.ecommerce.compstore.entities.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	List<Orders> findByUsers_UserId(Integer orderId);
}
