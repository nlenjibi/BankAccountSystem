package com.bank.system.processes;

import static com.bank.system.utils.ConsoleUtil.*;

import com.bank.system.manager.AccountManager;

import com.bank.system.model.*;

public  class CreateAccountHandler {

    private static final AccountManager accountManager = new AccountManager();
    private CreateAccountHandler() { /* no instances */ }

    private  record CustomerData(String name, int age, String contact, String address) {}

    private static void execute() {
        print(" ");
        print("ACCOUNT CREATION");
        print(" ");

        Customer customer;
        CustomerData data = readCustomerDetails();
        customer = createCustomerFromData(data);

        print(" ");
        print("Account type:");
        print("1. Savings Account (Interest: 3.5% Min Balance: $500)");
        print("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");

        int accountType = getValidIntInput("Select type (1-2): ", 1,2);
        double initialDeposit = getValidDoubleInput(
                "Enter initial deposit amount: $",
                v -> v > 0,
                "Value Must be greater than  zero."
        );

        Account account;
        if (accountType == 2) {
            account = new CheckingAccount(customer, initialDeposit);
        } else {
            account = new SavingsAccount(customer, initialDeposit);
        }

        if (accountManager.addAccount(account)) {
            displayAccountCreatedInfo(account, customer);
        } else {
            print("Failed to create account. Maximum account limit reached.");
        }
        print(" ");
        pressEnterToContinue();
    }


    public static void createAccount() {
        CreateAccountHandler.execute();
    }
    private static CustomerData readCustomerDetails() {
        String name = readString("Enter Customer Name: ",
                s -> !s.isEmpty(),
                "Name cannot be empty.");

        int age = getValidIntInput("Enter customer age: ", 1, 150);

        String contact = readString("Enter customer contact: ",
                s -> !s.isEmpty(),
                "Contact cannot be empty.");

        String address = readString("Enter customer address: ",
                s -> !s.isEmpty(),
                "Address cannot be empty.");

        return new CustomerData(name, age, contact, address);
    }
    private static void displayAccountCreatedInfo(Account account, Customer customer) {
        print(" ");
        print("✓ Account created successfully!");
        print("Account Number: " + account.getAccountNumber());
        print("Customer: " + customer.getName() + " (" + customer.getCustomerType() + ")");
        print("Account Type: " + account.getAccountType());
        printf("Initial Balance: $%.2f%n", account.getBalance());

        if (account instanceof SavingsAccount savings) {
            printf("Interest Rate: %.1f%%%n", savings.getInterestRate());
            printf("Minimum Balance: $%,.2f%n", savings.getMinimumBalance());
        } else if (account instanceof CheckingAccount checking) {
            printf("Overdraft Limit: $%,.2f%n", checking.getOverdraftLimit());
            if (customer instanceof PremiumCustomer) {
                System.out.println("Monthly Fee: Waived (Premium Customer)");
            } else {
                printf("Monthly Fee: $%,.2f%n", checking.getMonthlyFee());
            }
        }
        print("Status: " + account.getStatus());
    }
    private static Customer createCustomerFromData(CustomerData data) {

        print(" ");
        print("Customer type:");
        print("1. Regular Customer (Standard banking services)");
        print("2. Premium Customer (Enhanced benefits, min balance $10,000)");

        int customerType = getValidIntInput("Select type (1-2): ", 1, 2);

        if (customerType == 2) {
            return new PremiumCustomer(data.name, data.age, data.contact, data.address);
        } else {
            return new RegularCustomer(data.name, data.age, data.contact, data.address);
        }
    }


}