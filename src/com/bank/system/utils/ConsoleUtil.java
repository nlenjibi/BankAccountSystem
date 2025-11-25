package com.bank.system.utils;

import java.util.Scanner;

public class ConsoleUtil {

    private static final Scanner scanner = new Scanner(System.in);

    // Read a full line
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Read an integer safely

    public static int getValidIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
    }
    public static double getValidDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                // Remove $ or commas if user types e.g. $1,500
                input = input.replaceAll("[$,]", "");
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Please enter a positive value: $");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: $");
            }
        }
    }



    // Pause the console
    public static void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue…");
        scanner.nextLine();
    }
    public static void print(Object text) {
        System.out.println(text);
    }
    public static void printf(String format, Object ... args) {
        System.out.printf(format, args);
    }
    // a custom print method to displace text to the console
    public static void pr(Object text) {
        System.out.print(text);
    }

}