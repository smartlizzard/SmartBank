package com.smartbank.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.smartbank.domain.User;

@Service
public class UserCacheService {
	
	@Autowired
	UserService userService;
	
	@Cacheable(value = "ffff" )
	public User findUserDetailsByUsername(Principal principal) {
		//String name=principal.getName();
		System.out.println("UserInfoServiceImpl.findUserDetailsByUsername()-------------1");
		User user= userService.findByUsername(principal.getName());
		System.out.println("UserInfoServiceImpl.findUserDetailsByUsername()***********2");
		return user;
		 
		
	}

}
