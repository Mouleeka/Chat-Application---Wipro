

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChatDAOImpl dao = new ChatDAOImpl();
        int userId = -1;

        while (true) {
            System.out.println("\n===== Chat Application =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Send Message");
            System.out.println("4. View Chat History");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Username: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String pass = sc.nextLine();
                    dao.registerUser(uname, pass);
                }
                case 2 -> {
                    System.out.print("Enter Username: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String pass = sc.nextLine();
                    userId = dao.loginUser(uname, pass);
                }
                case 3 -> {
                    if (userId == -1) {
                        System.out.println("❌ Please login first!");
                        break;
                    }
                    System.out.print("Enter Receiver ID: ");
                    int rid = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Message: ");
                    String msg = sc.nextLine();
                    dao.sendMessage(userId, rid, msg);
                }
                case 4 -> {
                    if (userId == -1) {
                        System.out.println("❌ Please login first!");
                        break;
                    }
                    System.out.print("Enter Friend ID: ");
                    int fid = Integer.parseInt(sc.nextLine());
                    dao.viewMessages(userId, fid);
                }
                case 0 -> {
                    System.out.println("👋 Exiting Chat Application...");
                    sc.close();
                    return;
                }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }
}
