package com.Photography.dto;

public class LoginRequestDTO {

    private String username;
    private String password;
    
	public LoginRequestDTO() {
		super();
	}

	public LoginRequestDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
