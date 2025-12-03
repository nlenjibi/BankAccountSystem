// Java
package com.bank.system;

import  static com.bank.system.processes.CreateAccountHandler.*;
import static com.bank.system.processes.TransactionProcessor.*;
import static com.bank.system.utils.ConsoleUtil.*;
import static com.bank.system.utils.ConsoleFormatter.*;
import com.bank.system.manager.AccountManager;
import com.bank.system.manager.TransactionManager;
import com.bank.system.model.*;

public class Main {
    private static final AccountManager accountManager = new AccountManager();
    private static final TransactionManager transactionManager = new TransactionManager();
    // a custom println method to displace text to the console

    public static void main(String[] args) {
        // Pre-populate with sample accounts for demonstration
        displayWelcomeMessage();
        initializeSampleData();
        boolean running = true;
        while (running) {
            displayMainMenu();

            int choice = getValidIntInput("Enter your choice: ", 1,5);
            running = processMenuChoice(choice);

        }

        shutdown();
        }
    private static boolean processMenuChoice(int choice) {
        switch (choice) {
            case 1 -> createAccount();
            case 2 -> viewAccounts();
            case 3 -> processTransaction();
            case 4 -> viewTransactionHistory();
            case 5 -> { return false; }
            default -> { return true; }

        }
        return true;
    }


    public static void displayWelcomeMessage() {
        print("\nWelcome to the Bank Account Management System!");
        print("Please select an option from the menu below:");
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

    private static void displayMainMenu() {

        printHeader("BANK ACCOUNT MANAGEMENT SYSTEM - MAIN MENU");
        print("BANK ACCOUNT MANAGEMENT - MAIN MENU");
        print(" ");
        print("1. Create Account");
        print("2. View Accounts");
        print("3. Process Transaction");
        print("4. View Transaction History");
        print("5. Exit");
       print("");

  }

    private static void shutdown() {
        print("\nThank you for using Bank Account Management System!");
        print("Goodbye!");

    }


}





