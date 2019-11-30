package com.smartbank.controller;


import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.domain.BasicAccount;
import com.smartbank.domain.PrimaryAccount;
import com.smartbank.domain.PrimaryTransaction;
import com.smartbank.domain.SavingsAccount;
import com.smartbank.domain.SavingsTransaction;
import com.smartbank.domain.User;
import com.smartbank.service.AccountService;
import com.smartbank.service.TransactionService;
import com.smartbank.service.UserService;

@RestController
//@CrossOrigin("*")
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	AccountService accountService;
	
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/primaryAccount")
	public PrimaryAccount primaryAccount( Principal principal) {
		
		
		User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
		
		return primaryAccount;
	}

	@RequestMapping("/savingsAccount")
    public SavingsAccount savingsAccount(Principal principal) {
		
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

      

        return savingsAccount;
    }
	

	
	@RequestMapping("/transctionList/{type}")
	public List transactionList(Principal principal,@PathVariable String type){
		
		System.out.println("AccountController.transactionList()");
		
		if ("savings".equalsIgnoreCase(type)) {
			
			
			List<SavingsTransaction> savingTransactionList = transactionService.findSavingsTransactionList(principal.getName());
			return savingTransactionList;
		}
		else {
			List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());
			return primaryTransactionList;
		}
		
		
	}
	

	//this method is used for self deposite
    @RequestMapping(value = "/deposit/{accountType}/{amount}", method = RequestMethod.POST)
    public Object depositPOST(@PathVariable("amount") String amount, @PathVariable("accountType") String accountType, Principal principal) {
       System.out.println("inside depodite method");
       //0 is here for account number ,which is not required for self transaction
    	accountService.deposit(accountType, Double.parseDouble(amount), principal,0);
        User user = userService.findByUsername(principal.getName());
        if(accountType.equalsIgnoreCase("primary")) {
        	return user.getPrimaryAccount();
        }
      else {
		return user.getSavingsAccount();
	}

        
    }
    
    /*
     * method for bank clerk use
     */
    @GetMapping("/getAccDtls/{accNo}/{accountType}")
    public User getUser(@PathVariable int accNo,@PathVariable String accountType ) {
    	System.out.println("inside getAccDtls");
    	User user=userService.findByAccNo(accNo, accountType);
    	return user;
    	
    }
    
    @PostMapping("/txByClerk")
    public String depositeByClk(@RequestBody BasicAccount baseAcc) {
    	Principal principal = null;
    	//String result=accountService.txByClrk(baseAcc);
    	if (baseAcc.getOperation().equalsIgnoreCase("deposite")) {
    		String result=accountService.deposit(baseAcc.getAccType(), baseAcc.getBalance(), principal, baseAcc.getAccNo());
    		return result;
		}else {
			String result=accountService.withdraw(baseAcc.getAccType(), baseAcc.getBalance(), principal, baseAcc.getAccNo());
			return result;
		}
    	
    }
    
	@GetMapping("/savingAmount")
	public double getSavingsBalance(Principal principal) {
		System.out.println("AccountController.getSavingsBalance()");

		double savingsBalance = accountService.findAccountBalanceByUserName(principal.getName());

		return savingsBalance;

	}
}
