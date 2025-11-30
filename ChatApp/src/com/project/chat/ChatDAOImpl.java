

import java.sql.*;

public class ChatDAOImpl implements ChatDAO {

    @Override
    public void registerUser(String username, String password) {
        String sql = "INSERT INTO user(username, password) VALUES (?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            System.out.println("✅ User registered successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Username already exists or error: " + e.getMessage());
        }
    }

    @Override
    public int loginUser(String username, String password) {
        String sql = "SELECT user_id FROM user WHERE username=? AND password=?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("✅ Login successful! Welcome " + username);
                return rs.getInt("user_id");
            } else {
                System.out.println("❌ Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void sendMessage(int senderId, int receiverId, String content) {
        String sql = "INSERT INTO message(sender_id, receiver_id, content) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, content);
            ps.executeUpdate();
            System.out.println("✅ Message sent!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewMessages(int userId, int friendId) {
        String sql = """
            SELECT u1.username AS sender, u2.username AS receiver, m.content, m.timestamp
            FROM message m
            JOIN user u1 ON m.sender_id = u1.user_id
            JOIN user u2 ON m.receiver_id = u2.user_id
            WHERE (m.sender_id = ? AND m.receiver_id = ?) 
               OR (m.sender_id = ? AND m.receiver_id = ?)
            ORDER BY m.timestamp;
            """;
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.setInt(3, friendId);
            ps.setInt(4, userId);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Chat History ---");
            while (rs.next()) {
                System.out.println(rs.getString("sender") + " → " + rs.getString("receiver") +
                                   ": " + rs.getString("content") + " (" + rs.getString("timestamp") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
