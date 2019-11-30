package com.smartbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbank.dao.PrimaryTransactionDao;
import com.smartbank.dao.SavingsTransactionDao;
import com.smartbank.domain.PrimaryTransaction;
import com.smartbank.domain.SavingsTransaction;

import com.smartbank.domain.User;

@Service
public class TransactionserviceImpl implements TransactionService  {

	@Autowired
	PrimaryTransactionDao primaryTransactionDao;

	@Autowired
	SavingsTransactionDao savingsTransactionDao;

	@Autowired
	private UserService userService;

	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();

		return primaryTransactionList;
	}

	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUsername(username);
		System.out.println("TransactionserviceImpl.findSavingsTransactionList()---------------");
		//SavingsAccount savingAccount=user.getSavingsAccount();
		//System.out.println(savingAccount.toString());
		List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();
		/*
		 * savingsTransactionList.forEach(l ->{ System.out.println(l.toString()); });
		 */
			System.out.println("TransactionserviceImpl.findSavingsTransactionList()");
			//SavingsTransaction savingsTransaction=savingsTransactionList.get(0);
			//System.out.println(savingsTransaction.toString());

		return savingsTransactionList;
	}

	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}

	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	 public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
	        primaryTransactionDao.save(primaryTransaction);
	    }

	    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
	        savingsTransactionDao.save(savingsTransaction);
	    }
}
