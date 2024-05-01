package com.Photography.entity;


import java.util.Collections;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Admin admin;
	
	public MyUserDetails()
	{
		
	}
	public MyUserDetails(Admin admin)
	{
		this.admin=admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return Collections.singleton(new SimpleGrantedAuthority(admin.getRole()));
	}

	@Override
	public String getPassword()
	{
		return admin.getPassword();
	}

	@Override
	public String getUsername()
	{
		return admin.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
