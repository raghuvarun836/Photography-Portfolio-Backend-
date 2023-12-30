package com.Photography.dto;

public class StringRequest
{
	private String string;

	public StringRequest() {
		super();
	}

	public StringRequest(String string) {
		super();
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return "StringRequest [string=" + string + "]";
	}
	
	
}
