package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    public boolean addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO Transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getAccountId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setDouble(3, transaction.getAmount());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deposit(int accountId, double amount) throws SQLException {
        String sql = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            boolean success = stmt.executeUpdate() > 0;
            if (success) {
                Transaction transaction = new Transaction();
                transaction.setAccountId(accountId);
                transaction.setTransactionType("DEPOSIT");
                transaction.setAmount(amount);
                addTransaction(transaction);
            }
            return success;
        }
    }

    public boolean withdraw(int accountId, double amount) throws SQLException {
        String sql = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.setDouble(3, amount);
            boolean success = stmt.executeUpdate() > 0;
            if (success) {
                Transaction transaction = new Transaction();
                transaction.setAccountId(accountId);
                transaction.setTransactionType("WITHDRAWAL");
                transaction.setAmount(amount);
                addTransaction(transaction);
            }
            return success;
        }
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            if (!withdraw(fromAccountId, amount)) return false;
            if (!deposit(toAccountId, amount)) return false;

            Transaction transaction = new Transaction();
            transaction.setAccountId(fromAccountId);
            transaction.setTransactionType("TRANSFER");
            transaction.setAmount(amount);
            addTransaction(transaction);

            conn.commit();
            return true;
        } catch (SQLException ex) {
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}

