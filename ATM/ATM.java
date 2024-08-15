import java.util.Scanner;

/**
 * Represents a user's bank account.
 */
class BankAccount {
    private double balance;

    /**
     * Constructor to initialize the bank account with a given balance.
     * 
     * @param initialBalance The starting balance of the account.
     */
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Deposits a specified amount into the account.
     * 
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. Current balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    /**
     * Withdraws a specified amount from the account if sufficient balance is available.
     * 
     * @param amount The amount to withdraw.
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Current balance: $" + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    /**
     * Returns the current balance of the account.
     * 
     * @return The account balance.
     */
    public double getBalance() {
        return balance;
    }
}

/**
 * Represents an ATM machine.
 */
public class ATM {
    private BankAccount account;

    /**
     * Constructor to initialize the ATM with a user's bank account.
     * 
     * @param account The user's bank account.
     */
    public ATM(BankAccount account) {
        this.account = account;
    }

    /**
     * Allows the user to withdraw a specified amount from their account.
     * 
     * @param amount The amount to withdraw.
     */
    public void withdraw(double amount) {
        account.withdraw(amount);
    }

    /**
     * Allows the user to deposit a specified amount into their account.
     * 
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        account.deposit(amount);
    }

    /**
     * Displays the current balance in the user's account.
     */
    public void checkBalance() {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    /**
     * Main method to run the ATM program.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount userAccount = new BankAccount(500.0); // Initialize with $500 balance
        ATM atm = new ATM(userAccount);

        boolean exit = false;

        while (!exit) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdraw(withdrawAmount);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting ATM. Thank you for using our service!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
