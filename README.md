# Bank Account Management System

## Project Overview

A comprehensive console-based banking application that demonstrates Object-Oriented Programming principles in Java. The system allows bank staff to manage customer accounts, process transactions, and view account and transaction history.

## Features

- Create new bank accounts with customer details
- View all accounts with detailed information
- Process deposits and withdrawals
- View transaction history for specific accounts
- Support for different account types (Savings and Checking)
- Support for different customer types (Regular and Premium)
- Menu-driven interface for easy navigation

## OOP Concepts Implemented

- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: Account and Customer hierarchies
- **Abstraction**: Abstract classes and interfaces
- **Polymorphism**: Method overriding
- **Composition**: Manager classes that contain collections of objects

## Classes Structure

### Core Classes
- `Account` (abstract): Base class for all accounts
- `SavingsAccount`: Inherits from Account, includes interest rate and minimum balance
- `CheckingAccount`: Inherits from Account, includes overdraft limit and monthly fee

### Customer Classes
- `Customer` (abstract): Base class for all customers
- `RegularCustomer`: Standard banking services
- `PremiumCustomer`: Enhanced benefits with waived fees

### Transaction Classes
- `Transaction`: Records transaction details
- `Transactable` (interface): Defines transaction contract

### Management Classes
- `AccountManager`: Manages collection of accounts
- `TransactionManager`: Manages collection of transactions

### Main Application
- `Main`: Console application with menu system

## Setup Instructions

1. **Compile all Java files:**
   ```bash
   javac *.java
   ```

2. **Run the application:**
   ```bash
   java Main
   ```

## Usage

The application provides a menu-driven interface with the following options:

1. **Create Account**: Register new bank accounts for customers
2. **View Accounts**: Display all accounts with their details
3. **Process Transaction**: Deposit or withdraw money from accounts
4. **View Transaction History**: Display transaction history for an account
5. **Exit**: Close the application

## Sample Data

On startup, the application pre-populates with 5 sample accounts:
- 3 Savings accounts
- 2 Checking accounts
- Mix of Regular and Premium customers

## Requirements

- Java Development Kit (JDK) 8 or higher
- Command-line interface to run the application

## Data Structures Used

- Arrays to manage collections of accounts and transactions
- Linear search algorithms to find accounts by ID
- Static counters for generating unique IDs (ACC001, TXN001, etc.)