package com.project1.ecommerce.compstore.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.dto.LoginRequestDTO;
import com.project1.ecommerce.compstore.entities.Users;
import com.project1.ecommerce.compstore.repositories.UsersRepository;
import com.project1.ecommerce.compstore.security.JwtUtil;
import com.project1.ecommerce.compstore.services.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request, HttpServletRequest httpRequest) {
        try {
             //Step 1: Authenticate user credentials
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

             //Step 2: Set authenticated user in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

             //Step 3: Load the full user from DB
            Users user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            
            String token = jwtUtil.generateToken(request.getEmail());

             //Step 4: Create a session (Spring will create one if not exists)
            HttpSession session = httpRequest.getSession(true);  //`true` ensures session is created
            session.setAttribute("user", user); // You can also store auth.getPrincipal() if needed

             //Step 5: Return success response
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", token);
            responseBody.put("message", "Login successful");
            responseBody.put("username", user.getEmail());
            responseBody.put("role", user.getRole().name());

            return ResponseEntity.ok(responseBody);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Invalid email or password"));
        } catch (Exception e) {
             //Generic error fallback
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Login failed: " + e.getMessage()));
        }
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
//         Just dummy login logic (or even remove this API entirely)
//        return ResponseEntity.ok(Collections.singletonMap("message", "Login skipped for now"));
//    }
    
	
	//19. Authentication and security start here. This is only registration. Go to its service after the method creation
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid Users user){
		 String hashedPassword = passwordEncoder.encode(user.getPassword());
		    user.setPassword(hashedPassword);
		    usersRepository.save(user);
		    return ResponseEntity.ok("User registered successfully.");//method body in UsersService
	}
}




