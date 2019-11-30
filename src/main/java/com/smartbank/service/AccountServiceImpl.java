package com.smartbank.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbank.dao.PrimaryAccountDao;
import com.smartbank.dao.SavingsAccountDao;
import com.smartbank.domain.PrimaryAccount;
import com.smartbank.domain.PrimaryTransaction;
import com.smartbank.domain.SavingsAccount;
import com.smartbank.domain.SavingsTransaction;
import com.smartbank.domain.User;

@Service
public class AccountServiceImpl implements AccountService {

	// Random Number
	private static int nextAccountNumber = 11223145;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	private TransactionService transactionService;

	@Override
	public PrimaryAccount createPrimaryAccount() {
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAccountNumber(accountGen());

		primaryAccountDao.save(primaryAccount);

		return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
	}

	private int accountGen() {
		return ++nextAccountNumber;
	}

	@Override
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());

		savingsAccountDao.save(savingsAccount);

		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}

	@Override
	public String deposit(String accountType, double amount, Principal principal, int accNo) {
		User user = null;
		if (principal != null) {
			user = userService.findByUsername(principal.getName());
		} // if
		else if (accNo != 0) {
			user = userService.findByAccNo(accNo, accountType);
		} else {
			return "invalid account no/account Type";
		}
		if (user != null) {
			if (accountType.equalsIgnoreCase("primary")) {

				PrimaryAccount primaryAccount = user.getPrimaryAccount();
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
				primaryAccount = primaryAccountDao.save(primaryAccount);

				if (primaryAccount != null) {
					Date date = new Date();

					PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account",
							"Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
					transactionService.savePrimaryDepositTransaction(primaryTransaction);

					return "deposite successful";
				} else {
					return "deposite failed";
				}

			}

			else if (accountType.equalsIgnoreCase("savings")) {

				SavingsAccount savingsAccount = user.getSavingsAccount();
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
				savingsAccount = savingsAccountDao.save(savingsAccount);
				if (savingsAccount != null) {
					Date date = new Date();
					SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account",
							"Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
					transactionService.saveSavingsDepositTransaction(savingsTransaction);
					return "deposite successful";
				} else {
					return "deposite failed";
				}
			} else {
				return "deposite failed";
			}
		} else {
			return "Some thing went wrong plz try again";
		}

	}// method end

	@Override
	public String withdraw(String accountType, double amount, Principal principal, int accNo) {
		User user = null;
		int val = 0;
		if (principal != null) {
			user = userService.findByUsername(principal.getName());
		} // if
		else if (accNo != 0) {
			user = userService.findByAccNo(accNo, accountType);
		} else {
			return "invalid account no  OR account Type";
		}

		if (user != null) {
			if (accountType.equalsIgnoreCase("Primary")) {
				PrimaryAccount primaryAccount = user.getPrimaryAccount();
				val = primaryAccount.getAccountBalance().compareTo(new BigDecimal(amount));

				if (primaryAccount.getAccountBalance() != new BigDecimal(0) && val >= 0) {
					primaryAccount
							.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
					primaryAccount = primaryAccountDao.save(primaryAccount);

					if (primaryAccount != null) {

						Date date = new Date();

						PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
								"Withdraw from Primary Account", "Account", "Finished", amount,
								primaryAccount.getAccountBalance(), primaryAccount);
						transactionService.savePrimaryWithdrawTransaction(primaryTransaction);

						return "withdraw successful";
					} else {
						return "withdraw failed";
					}
				} else {

					return "Insufficent Fund";
				}

			} else if (accountType.equalsIgnoreCase("Savings")) {
				SavingsAccount savingsAccount = user.getSavingsAccount();

				val = savingsAccount.getAccountBalance().compareTo(new BigDecimal(amount));
				if (savingsAccount.getAccountBalance() != new BigDecimal(0) && val >= 0) {
					savingsAccount
							.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
					savingsAccount = savingsAccountDao.save(savingsAccount);
					if (savingsAccount != null) {

						Date date = new Date();
						SavingsTransaction savingsTransaction = new SavingsTransaction(date,
								"Withdraw from savings Account", "Account", "Finished", amount,
								savingsAccount.getAccountBalance(), savingsAccount);
						transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
						return "withdraw successful";
					} else {
						return "withdraw failed";
					}
				} else {

					return "Insufficent Fund";
				}

			} // else if
			else {
				return "withdraw failed";
			}
		} else {
			return "Some thing went wrong plz try again";
		}

	}

	@Override
	public double findAccountBalanceByUserName(String username) {

		double accountBalance = savingsAccountDao.findAccountBalanceByUserName(username);

		return accountBalance;
	}

}
