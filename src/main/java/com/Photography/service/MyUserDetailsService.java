package com.Photography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Photography.entity.Admin;
import com.Photography.entity.MyUserDetails;
import com.Photography.repository.AdminRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	  @Autowired
	  private AdminRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Admin admin=repository.findByUsername(username);
		if(admin==null)
		{
			System.out.println("User 404");
			throw new UsernameNotFoundException("User 404");
		}
		return new MyUserDetails(admin);
	}

//	  @Override
//	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	        Optional<Admin> userInfo = repository.findByUsername(username);
//	        return userInfo.map(MyUserDetails::new)
//	                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
//	        }
}
