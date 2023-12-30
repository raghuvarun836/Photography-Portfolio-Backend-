package com.Photography.dto;

public class ContactFormDto{

	private String name;
    private String email;
    private String contactNo;
    private String shootLocation;
    private String preferredDate;
    private String additionalRequirements;
    
	public ContactFormDto() {
		super();
	}

	public ContactFormDto(String name, String email, String contactNo, String shootLocation, String preferredDate,
			String additionalRequirements) {
		super();
		this.name = name;
		this.email = email;
		this.contactNo = contactNo;
		this.shootLocation = shootLocation;
		this.preferredDate = preferredDate;
		this.additionalRequirements = additionalRequirements;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getShootLocation() {
		return shootLocation;
	}

	public String getPreferredDate() {
		return preferredDate;
	}

	public String getAdditionalRequirements() {
		return additionalRequirements;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setShootLocation(String shootLocation) {
		this.shootLocation = shootLocation;
	}

	public void setPreferredDate(String preferredDate) {
		this.preferredDate = preferredDate;
	}

	public void setAdditionalRequirements(String additionalRequirements) {
		this.additionalRequirements = additionalRequirements;
	}

	@Override
	public String toString() {
		return "ContactFormDto [name=" + name + ", email=" + email + ", contactNo=" + contactNo + ", shootLocation="
				+ shootLocation + ", preferredDate=" + preferredDate + ", additionalRequirements="
				+ additionalRequirements + "]";
	}
}
