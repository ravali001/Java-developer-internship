package com.atm; 
import java.util.Scanner;
class User
{
    private String userID;
    private String userPIN;
    private double accountBalance;

    public User(String userID, String userPIN, double accountBalance)
    {
        this.userID = userID;
        this.userPIN = userPIN;
        this.accountBalance = accountBalance;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getUserPIN()
    {
        return userPIN;
    }

    public double getAccountBalance() 
    {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance)
    {
        this.accountBalance = accountBalance;
    }
}

class ATM
{
    private User currentUser;

    public ATM()
    {
        // Initialize with some default or predefined user data
        this.currentUser = new User("123456", "1234", 1000.0);
    }

    public void start()
    {
        System.out.println("Welcome to the ATM!");
        authenticateUser();
        performOperations();
    }

    private void authenticateUser()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userIDInput = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String userPINInput = scanner.nextLine();
        if (currentUser.getUserID().equals(userIDInput) && currentUser.getUserPIN().equals(userPINInput))
        {
            System.out.println("Authentication successful!");
        }
        else 
        {
            System.out.println("Authentication failed. Please try again.");
            authenticateUser(); // Allow the user to retry
        }
    }

    private void performOperations() 
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("Select operation:");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            switch (choice)
            {
                case 1:
                    System.out.println("Current Balance: $" + currentUser.getAccountBalance());
                    break;
                case 2:
                    // Implement withdrawal logic
                    break;
                case 3:
                    // Implement deposit logic
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    // Allow the user to retry by continuing the loop
                    continue;
            }

            // After each operation, ask the user if they want to perform another operation
            System.out.print("Do you want to perform another operation? (yes/no): ");
            String continueChoice = scanner.next().toLowerCase();
            if (!continueChoice.equals("yes"))
            {
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;
            }
        }
    }
    public static void main(String[] args)
    {
        ATM atm = new ATM();
        atm.start();
    }
}