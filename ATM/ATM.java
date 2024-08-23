import java.util.*;

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
    private Map<String, Integer> failedAttempts;
    private Map<String, Boolean> accountLocked;

    /**
     * Constructor to initialize the ATM with multiple bank accounts and their associated PINs.
     * 
     * @param accounts    A map of account IDs to bank accounts.
     * @param accountPins A map of account IDs to their corresponding PINs.
     */
    public ATM(Map<String, BankAccount> accounts, Map<String, String> accountPins) {
        this.accounts = accounts;
        this.accountPins = accountPins;
        this.failedAttempts = new HashMap<>();
        this.accountLocked = new HashMap<>();
    }

    /**
     * Verifies if the entered PIN is correct for the given account ID.
     * 
     * @param accountId The account ID.
     * @param inputPin  The PIN entered by the user.
     * @return True if the PIN is correct, otherwise false.
     */
    public boolean verifyPin(String accountId, String inputPin) {
        if (accountLocked.getOrDefault(accountId, false)) {
            System.out.println("Account is locked due to multiple failed attempts.");
            return false;
        }

        if (accountPins.containsKey(accountId) && accountPins.get(accountId).equals(inputPin)) {
            failedAttempts.put(accountId, 0); // Reset failed attempts on successful login
            return true;
        } else {
            int attempts = failedAttempts.getOrDefault(accountId, 0) + 1;
            failedAttempts.put(accountId, attempts);

            if (attempts >= 3) {
                accountLocked.put(accountId, true);
                System.out.println("Account locked due to multiple failed attempts. Your account is temporarily blocked.");
            } else {
                System.out.println("Incorrect PIN. You have " + (3 - attempts) + " chance(s) left.");
            }

            return false;
        }
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
     * Allows the user to change their PIN after verifying the current one.
     * 
     * @param accountId The ID of the account.
     * @param oldPin    The current PIN.
     * @param newPin    The new PIN.
     */
    public void changePin(String accountId, String oldPin, String newPin) {
        if (verifyPin(accountId, oldPin)) {
            accountPins.put(accountId, newPin);
            System.out.println("PIN successfully changed.");
        } else {
            System.out.println("Incorrect current PIN.");
        }
    }

    /**
     * Transfers funds from one account to another within the ATM system.
     * 
     * @param sourceAccountId The ID of the source account.
     * @param targetAccountId The ID of the target account.
     * @param amount          The amount to transfer.
     */
    public void transferFunds(String sourceAccountId, String targetAccountId, double amount) {
        if (sourceAccountId.equals(targetAccountId)) {
            System.out.println("Cannot transfer funds into your own account.");
            return;
        }

        BankAccount sourceAccount = accounts.get(sourceAccountId);
        BankAccount targetAccount = accounts.get(targetAccountId);

        if (sourceAccount != null && targetAccount != null) {
            if (sourceAccount.getBalance() >= amount) {
                sourceAccount.withdraw(amount);
                targetAccount.deposit(amount);
                System.out.println("Transfer successful. $" + amount + " transferred from account " + sourceAccountId + " to account " + targetAccountId);
            } else {
                System.out.println("Transfer failed due to insufficient funds.");
            }
        } else {
            System.out.println("Invalid account ID(s) provided for transfer.");
        }
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
            boolean pinVerified = false;
            int attemptsLeft = 2;

            while (attemptsLeft >= 0 && !pinVerified) {
                System.out.print("Enter your PIN: ");
                String enteredPin = scanner.nextLine();

                if (atm.verifyPin(accountId, enteredPin)) {
                    pinVerified = true;
                    boolean exit = false;

                    while (!exit) {
                        System.out.println("\nATM Menu:");
                        System.out.println("1. Check Balance");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. View Transaction History");
                        System.out.println("5. Convert Balance to Another Currency");
                        System.out.println("6. Change PIN");
                        System.out.println("7. Transfer Funds");
                        System.out.println("8. Exit");
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
                                System.out.print("Enter your current PIN: ");
                                scanner.nextLine(); // consume newline
                                String oldPin = scanner.nextLine();
                                System.out.print("Enter your new PIN: ");
                                String newPin = scanner.nextLine();
                                atm.changePin(accountId, oldPin, newPin);
                                break;
                            case 7:
                                System.out.print("Enter target account ID: ");
                                scanner.nextLine(); // consume newline
                                String targetAccountId = scanner.nextLine();
                                System.out.print("Enter amount to transfer: $");
                                double transferAmount = scanner.nextDouble();
                                atm.transferFunds(accountId, targetAccountId, transferAmount);
                                break;
                            case 8:
                                exit = true;
                                System.out.println("Thank you for using the ATM. Goodbye!");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                                break;
                        }
                    }
                } else {
                    attemptsLeft--;
                    if (attemptsLeft < 0) {
                        System.out.println("Incorrect PIN. Your account is locked due to multiple failed attempts.");
                        atm.accountLocked.put(accountId, true);
                    } else {
                        System.out.println("Incorrect PIN. You have " + attemptsLeft + " chance(s) left.");
                    }
                }
            }
        } else {
            System.out.println("Invalid account ID.");
        }

        scanner.close();
    }
}
