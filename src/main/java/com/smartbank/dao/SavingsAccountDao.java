package com.smartbank.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartbank.domain.SavingsAccount;


public interface SavingsAccountDao extends CrudRepository<SavingsAccount,Long> {
	
	public final static String FIND_ACCOUNTBALANCE_BY_USERNAME="select account_balance from onlinebanking.savings_account \r\n" + 
			"  where id=(SELECT savings_account_id FROM onlinebanking.user\r\n" + 
			"			 where username=:username)";
	
	@Query(name = "from SavingsAccount where accountNumber=:accountNumber")
	 SavingsAccount findByAccountNumber (@Param("accountNumber") int accountNumber);
	
	@Query(value =FIND_ACCOUNTBALANCE_BY_USERNAME, nativeQuery = true)
	
	double findAccountBalanceByUserName(@Param("username") String username);

}
