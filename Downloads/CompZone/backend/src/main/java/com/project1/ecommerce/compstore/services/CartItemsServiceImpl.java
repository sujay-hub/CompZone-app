package com.project1.ecommerce.compstore.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.ecommerce.compstore.dto.CartItemsDTO;
import com.project1.ecommerce.compstore.entities.CartItems;
import com.project1.ecommerce.compstore.entities.Products;
import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.repositories.CartItemsRepository;
import com.project1.ecommerce.compstore.repositories.ProductsRepository;
import com.project1.ecommerce.compstore.repositories.UsersRepository;

@Service
public class CartItemsServiceImpl implements CartItemsService {
	
	@Autowired
	private CartItemsRepository cartItemsRepository;

	//5. Shows the added items to the cart by loading them in the cart. When clicked on "Open Cart"
	@Override
	public List<CartItemsDTO> getCartItemsByUserId(Integer userId) {
		List<CartItems> cartItems = cartItemsRepository.findByUsers_UserId(userId);
		
		List<CartItemsDTO> dtoList = new ArrayList<>();
		
		for(int i = 0; i < cartItems.size(); i++) {
			CartItems item = cartItems.get(i);
			CartItemsDTO dto = new CartItemsDTO();
			Products product = item.getProducts();
			if (product != null) {
			dto.setProductId(item.getProducts().getProductId());
			dto.setProductName(item.getProducts().getProductName());
			dto.setImageUrl(item.getProducts().getImageUrl());
			dto.setPrice(item.getProducts().getPrice());
			//dto.setQuantity(item.getQuantity());
			}
			else if (product == null) {
				dto.setProductId(-1L);
				dto.setProductName("Unknown Product");
			    dto.setImageUrl(""); // or default image URL
			    dto.setPrice(BigDecimal.ZERO);	
			}
			dto.setQuantity(item.getQuantity());
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ProductsRepository productsRepository;

	//Add to cart task
	//4. Called when user adds product to cart. When clicked "Add to Cart"
	@Override
	public void addToCart(Integer userId, Long productId, Integer quantity) {
		// Fetch user and product
		Users user = usersRepository.findById(userId)
				.orElseThrow(()-> new RuntimeException("User not found "));
		Products product = productsRepository.findByProductId(productId)
				.orElseThrow(()-> new RuntimeException("Product not found"));
		
		// Check if item already exists in cart
		CartItems existingItem = cartItemsRepository.findByUsersAndProducts(user, product);
		
		if(existingItem != null) {
            // Item exists → update quantity
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
			cartItemsRepository.save(existingItem);
		}
		else {
            // New item → create and save
			CartItems newItem = new CartItems();
			newItem.setUsers(user);
			newItem.setProducts(product);
			newItem.setQuantity(quantity);
			newItem.setAddedAt(LocalDateTime.now());
			
			cartItemsRepository.save(newItem);
		}
		
	}
	
	//Update cart task
	//6. If user wants to update his cart quantity, like 2 items by mistake reduce to 1, 1 to 0.
	@Override
	public void updateCartQuantity(Integer userId, Long productId, Integer quantity) {
		Users user = usersRepository.findById(userId)
				.orElseThrow(()-> new RuntimeException("User not found"));
		Products product = productsRepository.findByProductId(productId)
				.orElseThrow(()-> new RuntimeException("Product not found"));
		
		CartItems existingItem = cartItemsRepository.findByUsersAndProducts(user, product);
		
		if(existingItem == null) {
			throw new RuntimeException("Cart item not found for update.");
		}
		
        // Quantity is zero or negative → remove from cart
		if(quantity <= 0) {
			cartItemsRepository.delete(existingItem);
		}
        // Update quantity
		else {
			existingItem.setQuantity(quantity);
			cartItemsRepository.save(existingItem);
		}
	}

	//Remove from cart task
	//7. Separate function for removing from cart. Next call in OrdersServiceImpl
	@Override
	public void removeFromCart(Integer userId, Long productId) {
		Users user = usersRepository.findById(userId)
				.orElseThrow(()-> new RuntimeException("User not found"));
		Products product = productsRepository.findByProductId(productId)
				.orElseThrow(()-> new RuntimeException("Product not found"));
		CartItems cartItem = cartItemsRepository.findByUsersAndProducts(user, product);
		
		if(cartItem == null) {
			throw new RuntimeException("Cart Item not found.");
		}
		cartItemsRepository.delete(cartItem);
	}
	//14. Clearing whole cart automatically on order placement successful. Next step in CartItemsController
	@Override
	public void clearCartByUserId(Integer userId) {
		List<CartItems> userCart = cartItemsRepository.findByUsers_UserId(userId);
		cartItemsRepository.deleteAll(userCart);
	}

}
