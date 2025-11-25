package com.bank.system.manager;

import static com.bank.system.utils.ConsoleUtil.*;
import com.bank.system.model.Transaction;

public class TransactionManager {
    private final Transaction[] transactions;
    private int transactionCount;

    public TransactionManager() {
        transactions = new Transaction[200]; // Array to hold up to 200 transactions
        transactionCount = 0;
    }

    // Method to add a transaction
    public boolean addTransaction(Transaction transaction) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount] = transaction;
            transactionCount++;
            return true;
        }
        return false;
    }

    // Method to view transactions by account
    public void viewTransactionsByAccount(String accountNumber) {
        print(" ");
        print("TRANSACTION HISTORY FOR ACCOUNT: " + accountNumber);
        print(" ");

        boolean hasTransactions = false;

        // Display transactions in reverse chronological order (newest first)
        for (int i = transactionCount - 1; i >= 0; i--) {
            if (transactions[i] != null && transactions[i].getAccountNumber().equals(accountNumber)) {
                if (!hasTransactions) {
                    printf("%-12s %-20s %-10s %-12s %-15s%n",
                            "TXN ID", "DATE/TIME", "TYPE", "AMOUNT", "BALANCE AFTER");
                    hasTransactions = true;
                }

                printf("%-12s %-20s %-10s $%-11.2f $%-14.2f%n",
                        transactions[i].getTransactionId(),
                        transactions[i].getTimestamp(),
                        transactions[i].getType(),
                        transactions[i].getAmount(),
                        transactions[i].getBalanceAfter());
            }
        }

        if (!hasTransactions) {
            print("No transactions found for this account.");
        } else {
            // Display summary
            print(" ");
            print("SUMMARY:");
            printf("Total Deposits: $%.2f%n", calculateTotalDeposits(accountNumber));
            printf("Total Withdrawals: $%.2f%n", calculateTotalWithdrawals(accountNumber));
            printf("Net Change: $%.2f%n",
                    calculateTotalDeposits(accountNumber) - calculateTotalWithdrawals(accountNumber));
        }

        print(" ");
        System.out.print("Press Enter to continue...");
        pressEnterToContinue();
    }

    // Method to calculate total deposits for an account
    public double calculateTotalDeposits(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i] != null &&
                    transactions[i].getAccountNumber().equals(accountNumber) &&
                    transactions[i].getType().equals("DEPOSIT")) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    // Method to calculate total withdrawals for an account
    public double calculateTotalWithdrawals(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i] != null &&
                    transactions[i].getAccountNumber().equals(accountNumber) &&
                    transactions[i].getType().equals("WITHDRAWAL")) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    // Method to get the number of transactions
    public int getTransactionCount() {
        return transactionCount;
    }

    // Getter for transactions array
    public Transaction[] getTransactions() {
        return transactions;
    }

    // Getter for transaction count
    public int getTransactionCountActual() {
        return transactionCount;
    }
}