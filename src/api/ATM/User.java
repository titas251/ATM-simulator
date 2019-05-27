package api.ATM;

import java.math.BigDecimal;

public class User {
    private String ID;
    private String name;
    private String surname;
    private String email;
    private int pin;
    private BigDecimal balance;

    public User(String ID, String name, String surname, String email, int pin, String balance) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pin = pin;
        this.balance = new BigDecimal(balance);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getPin() {
        return pin;
    }

    public String getBalance() {
        return balance.toPlainString();
    }

    public void deposit(String amount) {
        balance = balance.add(new BigDecimal(amount));
    }

    public boolean withdraw(String amount) {
        if (balance.subtract(new BigDecimal(amount)).compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.subtract(new BigDecimal(amount));
            return true;
        }
        else return false;
    }

    public String showBalance() {
        return "Your balance: " + balance.toPlainString();
    }
}