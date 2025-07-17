package com.project1.ecommerce.compstore.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project1.ecommerce.compstore.dto.AllOrdersDTO;
import com.project1.ecommerce.compstore.dto.CartItemsDTO;
import com.project1.ecommerce.compstore.dto.OrderHistoryDTO;
import com.project1.ecommerce.compstore.dto.OrderItemsDTO;
import com.project1.ecommerce.compstore.entities.CartItems;
import com.project1.ecommerce.compstore.entities.OrderItems;
import com.project1.ecommerce.compstore.entities.Orders;
import com.project1.ecommerce.compstore.entities.Products;
import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.enums.PaymentStatus;
import com.project1.ecommerce.compstore.enums.Status;
import com.project1.ecommerce.compstore.repositories.OrderItemsRepository;
import com.project1.ecommerce.compstore.repositories.OrdersRepository;
import com.project1.ecommerce.compstore.repositories.ProductsRepository;

@Service
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	public OrdersRepository ordersRepository;
	@Autowired
	public OrderItemsRepository orderItemsRepository;
	@Autowired
	public ProductsRepository productsRepository;
	@Autowired
	public CartItemsService cartItemsService;
	

	//8. Creates a new order transitioned from the cart, a new order object is created for that specific user. Called when user clicks on checkout or "place order". Next call in UsersServiceImpl
	@Override
	public void placeOrder(Users user, List<CartItemsDTO> cartItems, String shippingAddress,
			PaymentStatus paymentStatus) {
		if (cartItems == null || cartItems.isEmpty()) {
	        throw new IllegalArgumentException("Cart is empty. Cannot place order.");
	    }
		
		Orders order = new Orders();
		order.setUsers(user);
		order.setEmail(user.getEmail());
		order.setTotalAmount(BigDecimal.ZERO);
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus(Status.PAID);
		order.setPaymentStatus(PaymentStatus.SUCCESS);
		order.setShippingAddress(shippingAddress);
		ordersRepository.save(order);
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		
		for(int i = 0; i < cartItems.size(); i++) {
			
			CartItemsDTO item = cartItems.get(i);
			Products product = productsRepository.findByProductId(item.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found with id: "+item.getProductId()));
			
			if(product.getStockQuantity() < item.getQuantity()) {
				throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
			}
			
			product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
			productsRepository.save(product);
			
			OrderItems orderItem = new OrderItems();
			orderItem.setOrders(order);
			orderItem.setProducts(product);
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPriceAtTime(product.getPrice());
			orderItemsRepository.save(orderItem);
			
			BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
			BigDecimal taxRate = new BigDecimal("0.18"); // 18% GST
			BigDecimal tax = itemPrice.multiply(taxRate);
			BigDecimal itemTotalWithTax = itemPrice.add(tax);

			totalAmount = totalAmount.add(itemTotalWithTax);
		}
		order.setTotalAmount(totalAmount);
		ordersRepository.save(order);
		
		//13. Calling the clear cart method in CartItemsServiceImpl on successful order placement here.
		cartItemsService.clearCartByUserId(user.getUserId());
		
	}
	
	
	
	
	//16.For order history, next step in controller of Orders.
		@Override
		public List<OrderHistoryDTO> getOrderHistoryByUserId(Integer userId){
			List<Orders> orders = ordersRepository.findByUsers_UserId(userId); 
			List<OrderHistoryDTO> historyList = new ArrayList<>();
			
			for(int i = 0; i < orders.size(); i++) {
				Orders order = orders.get(i);
				OrderHistoryDTO dto = new OrderHistoryDTO();
				dto.setOrderId(order.getOrderId());
				dto.setCreatedAt(order.getCreatedAt());
				dto.setTotalAmount(order.getTotalAmount());
				dto.setUserId(order.getUsers().getUserId());
				
				List<OrderItems> items = orderItemsRepository.findByOrders_OrderId(order.getOrderId());
				List<OrderItemsDTO> itemDTOs = new ArrayList<>();
				
				for(int j = 0; j < items.size(); j++) {
					OrderItems item = items.get(j);
					OrderItemsDTO itemDTO = new OrderItemsDTO();
					itemDTO.setProductName(item.getProducts().getProductName());
					itemDTO.setQuantity(item.getQuantity());
					itemDTO.setPriceAtTime(item.getPriceAtTime());
					itemDTOs.add(itemDTO);
				}
				dto.setItems(itemDTOs);
				historyList.add(dto);
			}
			return historyList;
			
		}
		//26. The logic for administrator to view all orders. Next administrator to update stock/inventory. Go to ProductsController
		@Override
		public ResponseEntity<List<AllOrdersDTO>> getOrdersForAdmin() {
			List<Orders> orders = ordersRepository.findAll();
			
			List<AllOrdersDTO> response = new ArrayList<>();
			
			for(int i = 0; i < orders.size(); i++) {
				Orders order = orders.get(i);
				AllOrdersDTO dto = new AllOrdersDTO();
				dto.setOrderId(order.getOrderId());
				dto.setEmail(order.getEmail());
				dto.setTotalAmount(order.getTotalAmount());
				dto.setCreatedAt(order.getCreatedAt());
				
				response.add(dto);
			}
			return ResponseEntity.ok(response);
		}
		@Override
		public Orders getOrderById(Integer orderId) {
			return ordersRepository.findById(orderId)
			        .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
		}
		
		
		
		
}

