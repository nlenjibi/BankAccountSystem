package com.bank.system.processes;


import com.bank.system.manager.AccountManager;
import com.bank.system.manager.TransactionManager;
import com.bank.system.model.*;

import static com.bank.system.utils.ConsoleUtil.*;
import static com.bank.system.utils.ConsoleFormatter.*;

public class TransactionProcessor {

    private static final TransactionManager transactionManager = new TransactionManager();
    private static final AccountManager accountManager = new AccountManager();


    private static void execute() {
        print(" ");
        print("PROCESS TRANSACTION");
        printSubSeparator(60);
        print(" ");

        String accountNumber = readString("Enter Account Number: ",
                s -> !s.isEmpty(),
                "Account Number cannot be empty."
        );

        Account account = accountManager.findAccount(accountNumber);
        if (account == null) {
            print("Account not found.");
            pressEnterToContinue();
            return;
        }
        accountManager.displayAccountDetails(account);
        print(" ");
        print("Transaction type:");
        print("1. Deposit");
        print("2. Withdrawal");
        print(" ");

        int transactionType = getValidIntInput("Select type (1-2): ", 1,2);
        print(" ");

        String typeStr = (transactionType == 1) ? "DEPOSIT" : "WITHDRAWAL";

        double amount = getValidDoubleInput("Enter amount: $",
                v -> v > 0,
                "Amount must be greater than zero.");

        boolean success;
        double previousBalance = account.getBalance();

        success = account.processTransaction(amount, typeStr);

        if (!success) {
            handleFailedTransaction(transactionType, account);
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
        transaction.displayTransactionDetails(previousBalance);

        print(" ");

        boolean confirmed = readConfirmation("Confirm transaction?");
        handleTransactionConfirmation(confirmed, account, previousBalance);
        pressEnterToContinue();
    }
    private static void handleTransactionConfirmation(boolean confirmed, Account account, double previousBalance) {
        if (confirmed) {
            print(" ");
            print("✓ Transaction completed successfully!");
        } else {
            // Rollback transaction
            account.setBalance(previousBalance); // works for deposit or withdrawal
            print(" ");
            print("Transaction cancelled.");
        }
    }
    private static void handleFailedTransaction(int transactionType, Account account) {
        if (transactionType == 1) {
            print("Deposit failed. Invalid amount.");
        } else {
            if (account instanceof SavingsAccount) {
                print("Withdrawal failed. Insufficient funds or would go below minimum balance.");
            } else {
                print("Withdrawal failed. Insufficient funds or exceeds overdraft limit.");
            }
        }
    }
    public static void processTransaction() {
        TransactionProcessor.execute();
    }
    public static void viewTransactionHistory() {
        print(" ");
        print(underline("VIEW TRANSACTION HISTORY", '-'));
        print(" ");

        String accountNumber = readString("Enter Account Number: ", s -> !s.isEmpty(), "Account Number cannot be empty.");
        Account account = accountManager.findAccount(accountNumber);
        transactionManager.viewTransactionsByAccount(accountNumber, account);
    }

}