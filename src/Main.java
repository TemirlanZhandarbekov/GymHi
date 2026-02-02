public class Main {
    public static void main(String[] args) {
        java.sql.Connection conn = database.DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connection test passed!");
            database.DatabaseConnection.closeConnection(conn);
        }
        new menu.MenuManager().run();
    }
}