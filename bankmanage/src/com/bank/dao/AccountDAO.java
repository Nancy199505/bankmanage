package com.bank.dao;

import com.bank.model.Account;
import com.bank.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    public boolean addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO Accounts (user_id, account_type, balance) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getAccountType());
            stmt.setDouble(3, account.getBalance());
            return stmt.executeUpdate() > 0;
        }
    }

    public Account getAccountById(int accountId) throws SQLException {
        String sql = "SELECT * FROM Accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getDouble("balance"));
                return account;
            }
            return null;
        }
    }
}

