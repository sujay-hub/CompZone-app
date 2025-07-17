package com.project1.ecommerce.compstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {
	 @Bean
	    WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")  // Allow all endpoints
	                        .allowedOrigins("http://localhost:5173") // Your frontend URL
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "/**")
	                        .allowedHeaders("*")
	                        .allowCredentials(true); // Important for cookies and tokens
	            }
	        };
	    }
}
