package api.ATM;

public class ATM {
    public static User currentUser;

    public static boolean withdraw(String amount) {
        if (currentUser.withdraw(amount)) {
            return true;
        } else {
            return false;
        }
    }

    public static void deposit(String amount) {
        currentUser.deposit(amount);
    }

    public static void showBalance() {
        currentUser.showBalance();
    }

}


