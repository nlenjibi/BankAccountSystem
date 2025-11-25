// Java
package com.bank.system;

import com.bank.system.manager.AccountManager;
import com.bank.system.manager.TransactionManager;
import com.bank.system.model.*;

import static com.bank.system.utils.ConsoleUtil.*;
import java.util.Scanner;

public class Main {
    private static final AccountManager accountManager = new AccountManager();
    private static final TransactionManager transactionManager = new TransactionManager();

    // a custom println method to displace text to the console


    public static void main(String[] args) {
        // Pre-populate with sample accounts for demonstration
        initializeSampleData();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getValidIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    viewAccounts();
                    break;
                case 3:
                    processTransaction();
                    break;
                case 4:
                    viewTransactionHistory();
                    break;
                case 5:
                    print("Thank you for using Bank Account Management System!");
                    running = false;
                    break;
                default:
                    print("Invalid choice. Please enter a number between 1-5.");
                    break;
            }
        }

    }

    private static void displayMainMenu() {
        print("");
        print("BANK ACCOUNT MANAGEMENT - MAIN MENU");
        print(" ");
        print("1. Create Account");
        print("2. View Accounts");
        print("3. Process Transaction");
        print("4. View Transaction History");
        print("5. Exit");
        print("");

    }
    private static void createAccount() {
        print(" ");
        print("ACCOUNT CREATION");
        print(" ");

        String name = readLine("Enter Customer Name: ");

        int age = getValidIntInput("Enter customer age: ");

        String contact = readLine("Enter customer contact: ");

        String address = readLine("Enter customer address: ");

        print(" ");
        print("Customer type:");
        print("1. Regular Customer (Standard banking services)");
        print("2. Premium Customer (Enhanced benefits, min balance $10,000)");

        int customerType = getValidIntInput("Select type (1-2): ");

        Customer customer;
        if (customerType == 2) {
            customer = new PremiumCustomer(name, age, contact, address);
        } else {
            customer = new RegularCustomer(name, age, contact, address);
        }

        print(" ");
        print("Account type:");
        print("1. Savings Account (Interest: 3.5% Min Balance: $500)");
        print("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");

        int accountType = getValidIntInput("Select type (1-2): ");


        double initialDeposit = getValidDoubleInput("Enter initial deposit amount: $");

        Account account;
        if (accountType == 2) {
            account = new CheckingAccount(customer, initialDeposit);
        } else {
            account = new SavingsAccount(customer, initialDeposit);
        }

        if (accountManager.addAccount(account)) {
            print(" ");
            print("✓ Account created successfully!");
            print("Account Number: " + account.getAccountNumber());
            print("Customer: " + customer.getName() + " (" + customer.getCustomerType() + ")");
            print("Account Type: " + account.getAccountType());
            printf("Initial Balance: $%.2f%n", account.getBalance());

            if (account instanceof SavingsAccount savings) {
                printf("Interest Rate: %.1f%%%n", savings.getInterestRate());
                printf("Minimum Balance: $%.2f%n", savings.getMinimumBalance());
            } else if (account instanceof CheckingAccount checking) {
                printf("Overdraft Limit: $%.2f%n", checking.getOverdraftLimit());
                if (customer instanceof PremiumCustomer) {
                    System.out.println("Monthly Fee: Waived (Premium Customer)");
                } else {
                    printf("Monthly Fee: $%.2f%n", checking.getMonthlyFee());
                }
            }
            print("Status: " + account.getStatus());
        } else {
            print("Failed to create account. Maximum account limit reached.");
        }

        print(" ");
        pressEnterToContinue();
    }

    private static void viewAccounts() {
        accountManager.viewAllAccounts();
    }

    private static void processTransaction() {
        print(" ");
        print("PROCESS TRANSACTION");
        print(" ");

        String accountNumber = readLine("Enter Account Number: ");

        Account account = accountManager.findAccount(accountNumber);
        if (account == null) {
            print("Account not found!");
            pressEnterToContinue();
            return;
        }

        print(" ");
        print("Account Details:");
        print("Customer: " + account.getCustomer().getName());
        print("Account Type: " + account.getAccountType());
        printf("Current Balance: $%.2f%n", account.getBalance());

        print(" ");
        print("Transaction type:");
        print("1. Deposit");
        print("2. Withdrawal");

        int transactionType = getValidIntInput("Select type (1-2): ");

        String typeStr = (transactionType == 1) ? "DEPOSIT" : "WITHDRAWAL";

        double amount = getValidDoubleInput("Enter amount: $");

        boolean success = false;
        double previousBalance = account.getBalance();

        success = account.processTransaction(amount, typeStr);



        if (!success) {
            if (transactionType == 1) {
                print("Deposit failed. Invalid amount.");
            } else {
                if (account instanceof SavingsAccount) {
                    print("Withdrawal failed. Insufficient funds or would go below minimum balance.");
                } else {
                    print("Withdrawal failed. Insufficient funds or exceeds overdraft limit.");
                }
            }

            pressEnterToContinue();
            return;
        }

        // Create transaction record
        Transaction transaction = new Transaction(
                accountNumber,
                typeStr,
                amount,
                account.getBalance()
        );

        transactionManager.addTransaction(transaction);

        // Display transaction confirmation
        transaction.displayTransactionDetails();
        printf("Previous Balance: $%.2f%n", previousBalance);

        print(" ");

        String confirmation = readLine("Confirm transaction? (Y/N): ").toUpperCase();

        if (confirmation.equals("Y") || confirmation.equals("YES")) {
            print(" ");
            print("✓ Transaction completed successfully!");
        } else {
            // Rollback transaction
            if (transactionType == 1) { // If it was a deposit, remove it
                account.setBalance(previousBalance);
            } else { // If it was a withdrawal, add it back
                account.setBalance(previousBalance);
            }
            print(" ");
            print("Transaction cancelled.");
        }

        print(" ");
        pressEnterToContinue();

    }

    private static void viewTransactionHistory() {
        print(" ");
        print("VIEW TRANSACTION HISTORY");
        print(" ");

        String accountNumber = readLine("Enter Account Number: ");

        transactionManager.viewTransactionsByAccount(accountNumber);
    }
    private static void initializeSampleData() {
        // Create sample customers
        Customer customer1 = new RegularCustomer("John Smith", 35, "+1-555-0101", "456 Elm Street, Metropolis");
        Customer customer2 = new RegularCustomer("Sarah Johnson", 28, "+1-555-0102", "789 Oak Avenue, Metropolis");
        Customer customer3 = new PremiumCustomer("Michael Chen", 42, "+1-555-0103", "321 Pine Road, Metropolis");
        Customer customer4 = new RegularCustomer("Emily Brown", 31, "+1-555-0104", "654 Maple Drive, Metropolis");
        Customer customer5 = new PremiumCustomer("David Wilson", 48, "+1-555-0105", "987 Cedar Lane, Metropolis");

        // Create sample accounts
        Account account1 = new SavingsAccount(customer1, 5250.00);
        Account account2 = new CheckingAccount(customer2, 3450.00);
        Account account3 = new SavingsAccount(customer3, 15750.00);
        Account account4 = new CheckingAccount(customer4, 890.00);
        Account account5 = new SavingsAccount(customer5, 25300.00);

        // Add accounts to the manager
        accountManager.addAccount(account1);
        accountManager.addAccount(account2);
        accountManager.addAccount(account3);
        accountManager.addAccount(account4);
        accountManager.addAccount(account5);
    }



}