package api.Database;

import api.ATM.ATM;
import api.ATM.User;
import api.RFID.RFID;

import java.sql.*;

public class Database {
    private static Connection connect() {
        final String url = "url";               //Databe connection
        final String user = "user";             
        final String password = "password";     

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static boolean createUser(User user) {
        String SQL = "INSERT INTO data VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, user.getID());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getPin());
            statement.setString(6, user.getBalance());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean deleteUser() {
        String ID = RFID.readID();

        String SQL = "DELETE FROM data where ID = ?";
        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, ID);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean logUserIn() {
        User user;

        String ID = RFID.readID();
        String SQL = "SELECT * FROM data WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, ID); 
            ResultSet set = statement.executeQuery();
            set.next();
            user = new User(ID, set.getString("name"), set.getString("surname"), set.getString("email"),
                    set.getInt("pin"), set.getString("balance"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        ATM.currentUser = user;
        return true;
    }

    public static void updateBalance(String ID, String balance) {
        String SQL = "UPDATE data SET balance = ? where ID = ?";
        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, balance);
            statement.setString(2, ID);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
