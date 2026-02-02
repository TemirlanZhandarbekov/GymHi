package database;
import model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Added this import
import java.sql.SQLException;
public class MemberDAO {
    public void insertMember(Member member) {
        String sql = "INSERT INTO member (name, membership_type) VALUES (?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, member.getName());
            statement.setString(2, member.getMembershipType());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Member inserted successfully!");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("❌ Insert failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void getAllMembers() {
        String sql = "SELECT * FROM member";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("\n--- ALL MEMBERS FROM DATABASE ---");
            while (resultSet.next()) {
                int id = resultSet.getInt("member_id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("membership_type");
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Type: " + type + " Member");
                System.out.println("---");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("❌ Select failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }
}