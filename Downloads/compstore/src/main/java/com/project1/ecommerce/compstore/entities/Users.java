package com.project1.ecommerce.compstore.entities;

import java.time.LocalDateTime;

import com.project1.ecommerce.compstore.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	public Integer userId;
	
	@Column(name = "UserName")
	public String userName;
	
	@Column(name = "email", nullable = false, unique = true)
	public String email;
	
	@Column(name = "password", nullable = false)
	public String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Role")
	public Role role;
	
	@Column(name = "CreatedAt", updatable = false)
	public LocalDateTime createdAt;
	
	private boolean isPaid;
	private boolean isDelivered;

	public boolean isPaid() { return isPaid; }
	public boolean isDelivered() { return isDelivered; }
	
	
	
	public Users(Integer userId, String userName, String email, String password, Role role, LocalDateTime createdAt) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;
	}

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", role=" + role + ", createdAt=" + createdAt + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
}

