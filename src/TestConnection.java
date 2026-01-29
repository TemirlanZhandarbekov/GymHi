import database.DatabaseConnection;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            System.out.println("Connection test passed!");
            DatabaseConnection.closeConnection(connection);
        } else {
            System.out.println("Connection test failed!");
        }
    }
}
