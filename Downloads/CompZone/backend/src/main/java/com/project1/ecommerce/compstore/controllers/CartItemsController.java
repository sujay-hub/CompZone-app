package com.project1.ecommerce.compstore.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.dto.AddToCartRequest;
import com.project1.ecommerce.compstore.dto.CartItemsDTO;
import com.project1.ecommerce.compstore.dto.RemoveFromCartRequest;
import com.project1.ecommerce.compstore.dto.UpdateCartQuantityRequest;
import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.repositories.UsersRepository;
import com.project1.ecommerce.compstore.services.CartItemsService;
import com.project1.ecommerce.compstore.services.UsersService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins =  "http://localhost/5173", allowCredentials = "true")
public class CartItemsController {
	
	@Autowired
	private CartItemsService cartItemsService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private UsersService usersService;
	
	@GetMapping("/cart")
	public ResponseEntity<?> getUserCart() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String email = auth.getName();  // email is in token's subject
	    Users user = usersRepository.findByEmail(email)
	                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    Integer userId = user.getUserId();
	    List<CartItemsDTO> cartItems = cartItemsService.getCartItemsByUserId(userId);
	    return ResponseEntity.ok(cartItems);
	}

    
	@GetMapping("/dashboard")
	public ResponseEntity<?> dashboard(@Nullable Principal principal) {
	    if (principal != null) {
	        // Authenticated user
	        Users user = usersService.getUsersByEmail(principal.getName());
	        return ResponseEntity.ok("Welcome, " + user.getUserName());
	    } else {
	        // Anonymous access
	        return ResponseEntity.ok("Welcome to the dashboard (guest view)");
	    }
	}
	
	//For adding to cart
	@PostMapping("/cart/add")
	public ResponseEntity<String> addToCart(@RequestBody @Valid AddToCartRequest request){
		cartItemsService.addToCart(request.getUserId(), request.getProductId(), request.getQuantity());
		return ResponseEntity.ok("Product added to cart.");
	}
	
	//For updating cart quantity
	@PutMapping("/update")
	public ResponseEntity<String> updateCartQuantity(@RequestBody @Valid UpdateCartQuantityRequest request){
		cartItemsService.updateCartQuantity(request.getUserId(), request.getProductId() ,request.getQuantity());
		return ResponseEntity.ok("Cart updated successfully");
	}
	
	//Remove cart item
	@DeleteMapping("/delete")
	public ResponseEntity<String> removeFromCart(@RequestBody @Valid RemoveFromCartRequest request){
		cartItemsService.removeFromCart(request.getUserId(), request.getProductId());
		return ResponseEntity.ok("Item removed from cart successfully.");
	}
	
	//15. Clearing the cart manually when needed. Next step in OrderItemsDTO.
	@DeleteMapping("/clear")
	public ResponseEntity<String> clearCart(Principal principal){
		Users user = usersService.getUsersByEmail(principal.getName());
		cartItemsService.clearCartByUserId(user.getUserId());
		return ResponseEntity.ok("Cart cleared successfully!");
	}
	
	
}
