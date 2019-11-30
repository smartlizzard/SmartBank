package com.smartbank.dao;

import org.springframework.data.repository.CrudRepository;

import com.smartbank.security.Role;


public interface RoleDao extends CrudRepository<Role, Integer> {
	Role findByName(String name);
	
	

}
