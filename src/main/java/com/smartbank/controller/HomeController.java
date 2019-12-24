package com.smartbank.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.dao.RoleDao;
import com.smartbank.domain.PrimaryAccount;
import com.smartbank.domain.SavingsAccount;
import com.smartbank.domain.User;
import com.smartbank.domain.UserAuth;
import com.smartbank.security.UserRole;
import com.smartbank.service.UserService;


//@CrossOrigin("*")
@RestController
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
    private DiscoveryClient discoveryClient;

	
	@Autowired
    private Environment env;

	@Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort(){
        return "Service port number : " + env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances(){
        return new ResponseEntity<>(discoveryClient.getInstances(serviceId), HttpStatus.OK);
    }

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices(){
        return new ResponseEntity<>(discoveryClient.getServices(), HttpStatus.OK);
    }
	

	@GetMapping(produces = "application/json")
	@RequestMapping({ "/validatelogin" })
	public UserAuth validateLogin() {
		System.out.println("HomeController.validateLogin()");
		return new UserAuth("User successfully authenticated");
	}
	
	@RequestMapping("/userAccountDetails")
	public List<Object> userFront(Principal principal) {
		///Principal in spring security who is logged in
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		List<Object> list = new ArrayList<>();
		list.add(savingsAccount);
		list.add(primaryAccount);
		return list;
	}

	
	
	/*
	 * @RequestMapping("/") public String sayHello() { return "hellopage"; }
	 */

	/*
	 * @RequestMapping(value="/register/", method=RequestMethod.POST)
	 * public @ResponseBody User add(@RequestBody User user){
	 * userservice.save(user); return user; }
	 */

	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody User user) {
System.out.println("----------------------inside add method");
		if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
			System.out.println("----------checkUserExists----------inside add method");

			///if (userService.checkEmailExists(user.getEmail()) && (userService.checkUsernameExists(user.getUsername())))
				///;
			System.out.println("-----------checkEmailExists(-----------inside add method");
			//return "User Already Exists";
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("User Already Exists");

		} else {
			System.out.println("----------else------------inside add method");
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
			System.out.println("----------else-user role-----------inside add method");
			userService.createUser(user, userRoles);
			

		}
		//return "User Created";
		return ResponseEntity.status(HttpStatus.CREATED).body(user);

	}

}
