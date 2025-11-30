

public interface ChatDAO {
    void registerUser(String username, String password);
    int loginUser(String username, String password);
    void sendMessage(int senderId, int receiverId, String content);
    void viewMessages(int userId, int friendId);
}
