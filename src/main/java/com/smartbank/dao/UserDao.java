package com.smartbank.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartbank.domain.User;
@Repository
public interface UserDao extends CrudRepository<User, Long> {

	@Query("from User where username=:username")
	User findByUsername(@Param("username") String username);

	User findByEmail(String email);

	List<User> findAll();

}
