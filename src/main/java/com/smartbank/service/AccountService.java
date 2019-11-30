package com.smartbank.service;

import java.security.Principal;


import com.smartbank.domain.PrimaryAccount;
import com.smartbank.domain.SavingsAccount;

public interface AccountService {

	PrimaryAccount createPrimaryAccount();

	SavingsAccount createSavingsAccount();

	String deposit(String accountType, double amount, Principal principal,int accNo);

	String withdraw(String accountType, double amount, Principal principal,int accNo);
	
	double findAccountBalanceByUserName(String username);
	
	//String txByClrk(BasicAccount baseAcc);

}
