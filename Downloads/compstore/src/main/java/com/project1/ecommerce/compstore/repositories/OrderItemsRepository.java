package com.project1.ecommerce.compstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.ecommerce.compstore.entities.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {

	//void save(OrderItems orderItem);
	List<OrderItems> findByOrders_OrderId(Integer orderId);

}
