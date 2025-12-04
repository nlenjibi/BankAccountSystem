// Java
package com.bank.system;

import  static com.bank.system.processes.CreateAccountHandler.*;
import static com.bank.system.processes.TransactionProcessor.*;
import static com.bank.system.utils.ConsoleUtil.*;
import static com.bank.system.utils.ConsoleFormatter.*;

public class Main {
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





