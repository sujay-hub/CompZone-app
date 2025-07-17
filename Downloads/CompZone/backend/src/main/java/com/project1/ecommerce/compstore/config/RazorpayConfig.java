package com.project1.ecommerce.compstore.config;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {
	 @Bean
	    RazorpayClient razorpayClient() throws RazorpayException {
	        return new RazorpayClient("rzp_test_YourKeyHere", "your_secret_key_here");
	    }
}
