package database;
import model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MemberDAO {
    private Member extractMemberFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("member_id");
        String name = rs.getString("name");
        String type = rs.getString("membership_type");
        double fee = 0.0;
        try { fee = rs.getDouble("monthly_fee"); } catch (Exception e) {}
        Member member = null;
        switch (type) {
            case "Student": member = new Member.StudentMember(name, String.valueOf(id)); break;
            case "Premium": member = new Member.PremiumMember(name, String.valueOf(id)); break;
            case "Senior":  member = new Member.SeniorMember(name, String.valueOf(id)); break;
            default: member = new Member.PremiumMember(name, String.valueOf(id)); break; // Fallback
        }
        return member;
    }
    public Member getMemberById(int id) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractMemberFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertMember(Member member) {
        String sql = "INSERT INTO member (name, membership_type, monthly_fee) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getMembershipType());
            double fee = switch (member.getMembershipType()) {
                case "Student" -> 30.0;
                case "Senior"  -> 45.0;
                case "Premium" -> 100.0;
                default -> 60.0;
            };
            statement.setDouble(3, fee);
            statement.executeUpdate();
            System.out.println("✅ Member saved with " + member.getMembershipType() + " fee: " + fee);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getAllMembers() {
        String sql = "SELECT * FROM member ORDER BY member_id";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("\n--- ALL MEMBERS FROM DATABASE ---");
            while (resultSet.next()) {
                int id = resultSet.getInt("member_id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("membership_type");
                System.out.println("ID: " + id + " | Name: " + name + " | Type: " + type);
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
    public boolean updateMember(Member member) {
        // Converting String ID to int for the query
        int dbId = Integer.parseInt(member.getId());

        String sql = "UPDATE member SET name = ? WHERE member_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, member.getName());
            statement.setInt(2, dbId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteMember(int memberId) {
        String sql = "DELETE FROM member WHERE member_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, memberId);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            if (rowsDeleted > 0) {
                System.out.println("✅ Member deleted");
                return true;
            } else {
                System.out.println("❌ No member found");
            }
        } catch (SQLException e) {
            System.out.println("Delete failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }
    public List<Member> searchByName(String name) {
        List<Member> memberList = new ArrayList<>();
        String sql = "SELECT * FROM member WHERE name ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return memberList;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Member member = extractMemberFromResultSet(resultSet);
                if (member != null) {
                    memberList.add(member); // Fixed: was adding 'staff'
                }
            }
            resultSet.close();
            statement.close();
            System.out.println("Found " + memberList.size() + " matches.");
        } catch (SQLException e) {
            System.out.println("Search failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return memberList;
    }
    public List<Member> searchByFeeRange(double minFee, double maxFee) {
        List<Member> memberList = new ArrayList<>();
        String sql = "SELECT * FROM member WHERE monthly_fee BETWEEN ? AND ? ORDER BY monthly_fee DESC";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return memberList;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minFee);
            statement.setDouble(2, maxFee);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Member member = extractMemberFromResultSet(resultSet);
                if (member != null) memberList.add(member);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return memberList;
    }
    public List<Member> searchByMinFee(double minFee) {
        List<Member> memberList = new ArrayList<>();
        // Using monthly_fee to match your Gym table
        String sql = "SELECT * FROM member WHERE monthly_fee >= ? ORDER BY monthly_fee DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, minFee);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Member member = extractMemberFromResultSet(resultSet);
                if (member != null) memberList.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberList;
    }
}