import menu.MenuManager;
public class Main {
    public static void main(String[] args){
        java.sql.Connection conn = database.DatabaseConnection.getConnection();
        if (conn != null) {
            database.DatabaseConnection.closeConnection(conn);
        }
        menu.MenuManager manager = new MenuManager();
        manager.run();
    }
}