package com.smartbank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartbank.dao.UserDao;
import com.smartbank.domain.User;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username***********from client***************"+username);
		User user = userDao.findByUsername(username);
		///dont touch this
		//System.out.println("username**************************"+user);
		if (null == user) {
			LOG.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return user;

	}
}
