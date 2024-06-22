package com.Photography.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "admins")
public class Admin {

    @Id
    private String id;

    private String username;
    
    private String password;
    
    private String role;
    
    private LocalDateTime tokenInvalidationTime;
    
	public Admin() {
		super();
		this.role = "ADMIN";
	}

	public Admin(String id, String username, String password, String role, LocalDateTime tokenInvalidationTime) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = (role != null && !role.isEmpty()) ? role : "ADMIN";
		this.tokenInvalidationTime = tokenInvalidationTime;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

	public LocalDateTime getTokenInvalidationTime() {
		return tokenInvalidationTime;
	}

	public void setTokenInvalidationTime(LocalDateTime tokenInvalidationTime) {
		this.tokenInvalidationTime = tokenInvalidationTime;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", tokenInvalidationTime=" + tokenInvalidationTime + "]";
	}

}

