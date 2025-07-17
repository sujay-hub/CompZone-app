package com.project1.ecommerce.compstore.dto;


import jakarta.servlet.http.HttpSession;



public class LoginRequestDTO {
    private String email;
    private String password;
    public HttpSession session;
    
	public LoginRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoginRequestDTO(String email, String password, HttpSession session) {
		super();
		this.email = email;
		this.password = password;
		this.session = session;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	@Override
	public String toString() {
		return "LoginRequestDTO [email=" + email + ", password=" + password + ", session=" + session + "]";
	}
	
	
}