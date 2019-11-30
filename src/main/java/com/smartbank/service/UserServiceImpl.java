package com.smartbank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartbank.dao.RoleDao;
import com.smartbank.dao.UserDao;

import com.smartbank.domain.User;

import com.smartbank.security.UserRole;




@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	private static int nextSSN = 111;
	private static int nextTIN = 1111;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountService accountService;
	
//	@Autowired
//	UserInfo userInfo;

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(username)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkUsernameExists(String username) {
		if (null != findByUsername(username)) {
			return true;
		}

		return false;
	}
	
	
	

	@Override
	public boolean checkEmailExists(String email) {
		if (null != findByEmail(email)) {
			return true;
		}

		return false;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUserList() {
		return userDao.findAll();
	}

	@Override
	public void enableUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableUser(String username) {
		// TODO Auto-generated method stub

	}

	
	
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userDao.findByUsername(user.getUsername());
		
System.out.println("inside create createUser----------------------- ");
		if (localUser != null) {
			LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			System.out.println("inside create createUser-------before for---------------- ");
			for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
				System.out.println("inside create createUser-------after for---------------- ");
			}

			user.getUserRoles().addAll(userRoles);
			System.out.println("inside create createUser-------user.getUserRoles().addAll(userRoles);-------------- ");

			user.setPrimaryAccount(accountService.createPrimaryAccount());
			System.out.println("inside create createUser------accountService.createPrimaryAccount()-------------- ");
			user.setSavingsAccount(accountService.createSavingsAccount());
			System.out.println("inside create createUser-----t(accountService.createSavingsAccount());------------ ");
			user.setSsn(ssntGen());
			user.setTin(tinGen());
			

			localUser = userDao.save(user);
		}

		return localUser;
	}
	
	
	@Override
	public User findByAccNo(int accNo,String accountType) {
		List<User> users=userDao.findAll();
		User user=null;
		
		if (accountType.equalsIgnoreCase("savings")) {
			 Optional<User> optional=users.stream().filter(u -> u.getSavingsAccount().getAccountNumber() == accNo).findAny();
			try {
				user= optional.get();
			} catch (Exception e) {
				e.printStackTrace();
				return user;
			}
			
			return user;
		}
		else {
			Optional<User> optional=users.stream().filter(u -> u.getPrimaryAccount().getAccountNumber() == accNo).findAny();
			try {
				user= optional.get();
			} catch (Exception e) {
				e.printStackTrace();
				return user;
			}
			return user;
		}
		
	}
	
	
	private int ssntGen() {
		return ++nextSSN;
	}
	private int tinGen() {
		return ++nextTIN;
	}
	
	
}
