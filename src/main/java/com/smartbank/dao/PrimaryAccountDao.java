package com.smartbank.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartbank.domain.PrimaryAccount;


public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount,Long> {
	
	@Query(name = "from PrimaryAccount where accountNumber=:accountNumber")
	 PrimaryAccount findByAccountNumber (@Param("accountNumber") int accountNumber);

}
 