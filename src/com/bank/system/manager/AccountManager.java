package com.bank.system.manager;

import static com.bank.system.utils.ConsoleUtil.*;
import com.bank.system.model.Account;
public class AccountManager {
    private final Account[] accounts;
    private int accountCount;


    public AccountManager() {
        accounts = new Account[50]; // Array to hold up to 50 accounts
        accountCount = 0;

    }

    // Method to add an account
    public boolean addAccount(Account account) {
        if (accountCount < accounts.length) {
            accounts[accountCount] = account;
            accountCount++;
            return true;
        }
        return false;
    }

    // Method to find an account by account number
    public Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] != null && accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null; // Account not found
    }

    // Method to view all accounts
    public void viewAllAccounts() {
        print(" ");
        print("ACCOUNT LISTING");
        print(" ");
        System.out.printf("%-15s %-15s %-9s %-12s %-8s%n", "ACCOUNT NUMBER", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] != null) {
                accounts[i].displayAccountDetails();
                print(" "); // Add a blank line after each account's details
            }
        }

        System.out.printf("Total Accounts: %d%n", getAccountCount());
        System.out.printf("Total Bank Balance: $%.2f%n", getTotalBalance());
        print(" ");

        pressEnterToContinue(); // Wait for user to press Enter
    }

    // Method to get total balance of all accounts
    public double getTotalBalance() {
        double total = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] != null) {
                total += accounts[i].getBalance();
            }
        }
        return total;
    }

    // Method to get the number of accounts
    public int getAccountCount() {
        return accountCount;
    }

    // Getter for accounts array
    public Account[] getAccounts() {
        return accounts;
    }

    // Getter for account count
    public int getAccountCountActual() {
        return accountCount;
    }
}