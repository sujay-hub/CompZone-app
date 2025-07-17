package com.project1.ecommerce.compstore.security;

import java.util.Base64;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class KeyGen {

	public static void main(String[] args) {
		 byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
	        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
	        System.out.println("Secure JWT Secret: " + base64Key);

	}

}
