package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import java.sql.SQLException;

public class BankService {
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean deposit(int accountId, double amount) throws SQLException {
        return transactionDAO.deposit(accountId, amount);
    }

    public boolean withdraw(int accountId, double amount) throws SQLException {
        return transactionDAO.withdraw(accountId, amount);
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount) throws SQLException {
        return transactionDAO.transfer(fromAccountId, toAccountId, amount);
    }

    public Account getAccountDetails(int accountId) throws SQLException {
        return accountDAO.getAccountById(accountId);
    }
}

