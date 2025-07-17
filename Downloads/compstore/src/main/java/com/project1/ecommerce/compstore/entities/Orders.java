package com.project1.ecommerce.compstore.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.project1.ecommerce.compstore.enums.PaymentStatus;
import com.project1.ecommerce.compstore.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "Orders")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	public Integer orderId;
	
	@Column(name = "total_amount", precision = 10, scale = 3, nullable = false)
	public BigDecimal totalAmount;

	
	@Enumerated(EnumType.STRING)
	@Column(name = "Status")
	public Status status;

	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	public PaymentStatus paymentStatus;

	@Column(name = "created_at", updatable = false)
	public LocalDateTime createdAt;
	
	@Column(nullable = false)
	public String email;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<OrderItems> items;
	
	@Column(name = "shipping_address", length = 1000)
	public String shippingAddress;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	public Users users;
	

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", totalAmount=" + totalAmount + ", status=" + status + ", paymentStatus="
				+ paymentStatus + ", createdAt=" + createdAt + ", email=" + email + ", items=" + items
				+ ", shippingAddress=" + shippingAddress + ", users=" + users + "]";
	}

	public Orders(Integer orderId, BigDecimal totalAmount, Status status, PaymentStatus paymentStatus,
			LocalDateTime createdAt, Users users, String email, List<OrderItems> items, String shippingAddress) {
		super();
		this.orderId = orderId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.paymentStatus = paymentStatus;
		this.createdAt = createdAt;
		this.users = users;
		this.email = email;
		this.items = items;
		this.shippingAddress = shippingAddress;
	}
	
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<OrderItems> getItems() {
		return items;
	}

	public void setItems(List<OrderItems> items) {
		this.items = items;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
}
