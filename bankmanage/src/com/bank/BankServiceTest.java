package com.bank;

import com.bank.model.Account;
import com.bank.service.BankService;

public class BankServiceTest {
    public static void main(String[] args) {
        BankService bankService = new BankService();

        try {
            // Deposit test
            boolean depositSuccess = bankService.deposit(1, 500);
            System.out.println("Deposit successful: " + depositSuccess);

            // Withdrawal test
            boolean withdrawalSuccess = bankService.withdraw(1, 200);
            System.out.println("Withdrawal successful: " + withdrawalSuccess);

            // Transfer test
            boolean transferSuccess = bankService.transfer(1, 2, 100);
            System.out.println("Transfer successful: " + transferSuccess);

            // Get Account Details
            Account account = bankService.getAccountDetails(1);
            System.out.println("Account Balance: " + account.getBalance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


