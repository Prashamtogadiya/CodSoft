import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a user's bank account.
 */
class BankAccount {
    private double balance;
    private List<String> transactionHistory;
    private double dailyLimit = 1000.0;
    private double dailyWithdrawn = 0;

    /**
     * Constructor to initialize the bank account with a given balance.
     * 
     * @param initialBalance The starting balance of the account.
     */
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Deposits a specified amount into the account.
     * 
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
            System.out.println("Deposit successful. Current balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    /**
     * Withdraws a specified amount from the account if sufficient balance is available and daily limit is not exceeded.
     * 
     * @param amount The amount to withdraw.
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            if (dailyWithdrawn + amount > dailyLimit) {
                System.out.println("Exceeded daily withdrawal limit.");
            } else {
                balance -= amount;
                dailyWithdrawn += amount;
                transactionHistory.add("Withdrew: $" + amount);
                System.out.println("Withdrawal successful. Current balance: $" + balance);
            }
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

    /**
     * Returns the transaction history of the account.
     * 
     * @return The list of transactions.
     */
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Converts the current balance to another currency based on the provided exchange rate.
     * 
     * @param exchangeRate The exchange rate for conversion.
     * @return The converted balance.
     */
    public double convertToCurrency(double exchangeRate) {
        return balance * exchangeRate;
    }
}

/**
 * Represents an ATM machine.
 */
public class ATM {
    private Map<String, BankAccount> accounts;
    private Map<String, String> accountPins;

    /**
     * Constructor to initialize the ATM with multiple bank accounts and their associated PINs.
     * 
     * @param accounts    A map of account IDs to bank accounts.
     * @param accountPins A map of account IDs to their corresponding PINs.
     */
    public ATM(Map<String, BankAccount> accounts, Map<String, String> accountPins) {
        this.accounts = accounts;
        this.accountPins = accountPins;
    }

    /**
     * Verifies if the entered PIN is correct for the given account ID.
     * 
     * @param accountId The account ID.
     * @param inputPin  The PIN entered by the user.
     * @return True if the PIN is correct, otherwise false.
     */
    public boolean verifyPin(String accountId, String inputPin) {
        return accountPins.containsKey(accountId) && accountPins.get(accountId).equals(inputPin);
    }

    /**
     * Allows the user to select an account by its ID.
     * 
     * @param accountId The ID of the account to select.
     * @return The selected bank account, or null if the account ID is invalid.
     */
    public BankAccount selectAccount(String accountId) {
        return accounts.get(accountId);
    }

    /**
     * Displays the current balance in the user's account.
     * 
     * @param account The user's bank account.
     */
    public void checkBalance(BankAccount account) {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    /**
     * Displays the transaction history of the user's account.
     * 
     * @param account The user's bank account.
     */
    public void showTransactionHistory(BankAccount account) {
        List<String> history = account.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : history) {
                System.out.println(transaction);
            }
        }
    }

    /**
     * Displays the balance converted to another currency.
     * 
     * @param account      The user's bank account.
     * @param exchangeRate The exchange rate for conversion.
     */
    public void showConvertedBalance(BankAccount account, double exchangeRate) {
        double convertedBalance = account.convertToCurrency(exchangeRate);
        System.out.println("Your balance in the selected currency: $" + convertedBalance);
    }

    /**
     * Main method to run the ATM program.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize multiple accounts with their respective balances
        Map<String, BankAccount> accounts = new HashMap<>();
        accounts.put("123", new BankAccount(500.0)); // Account 1 with $500 balance
        accounts.put("456", new BankAccount(1000.0)); // Account 2 with $1000 balance

        // Initialize account PINs
        Map<String, String> accountPins = new HashMap<>();
        accountPins.put("123", "1234"); // PIN for account 1
        accountPins.put("456", "4567"); // PIN for account 2
        
        ATM atm = new ATM(accounts, accountPins); // Initialize ATM with accounts and their PINs

        System.out.print("Enter your account ID: ");
        String accountId = scanner.nextLine();

        BankAccount selectedAccount = atm.selectAccount(accountId);

        if (selectedAccount != null) {
            System.out.print("Enter your PIN: ");
            String enteredPin = scanner.nextLine();

            if (atm.verifyPin(accountId, enteredPin)) {
                boolean exit = false;

                while (!exit) {
                    System.out.println("\nATM Menu:");
                    System.out.println("1. Check Balance");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. View Transaction History");
                    System.out.println("5. Convert Balance to Another Currency");
                    System.out.println("6. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            atm.checkBalance(selectedAccount);
                            break;
                        case 2:
                            System.out.print("Enter amount to deposit: $");
                            double depositAmount = scanner.nextDouble();
                            selectedAccount.deposit(depositAmount);
                            break;
                        case 3:
                            System.out.print("Enter amount to withdraw: $");
                            double withdrawAmount = scanner.nextDouble();
                            selectedAccount.withdraw(withdrawAmount);
                            break;
                        case 4:
                            atm.showTransactionHistory(selectedAccount);
                            break;
                        case 5:
                            System.out.print("Enter the exchange rate: ");
                            double exchangeRate = scanner.nextDouble();
                            atm.showConvertedBalance(selectedAccount, exchangeRate);
                            break;
                        case 6:
                            exit = true;
                            System.out.println("Exiting ATM. Thank you for using our service!");
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                }
            } else {
                System.out.println("Incorrect PIN. Access denied.");
            }
        } else {
            System.out.println("Invalid account ID.");
        }

        scanner.close();
    }
}
