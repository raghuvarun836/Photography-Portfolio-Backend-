package com.Photography.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Photography.entity.Admin;
import com.Photography.repository.AdminRepository;



@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	  @Autowired
	  private AdminRepository repository;

	  @Override
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Optional<Admin> userInfo = repository.findByUsername(username);
	        return userInfo.map(UserInfoUserDetails::new)
	                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

	    }
}
