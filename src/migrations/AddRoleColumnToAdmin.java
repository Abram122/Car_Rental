// package migrations;

// import utils.MySQLConnection;

// import java.sql.Connection;
// import java.sql.PreparedStatement;

// public class AddRoleColumnToAdmin {
//     public static void main(String[] args) {
//         try {
//             Connection conn = MySQLConnection.getInstance().getConnection();
//             String sql = "ALTER TABLE Admin ADD COLUMN role VARCHAR(50) DEFAULT 'ADMIN'";
            
//             try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                 stmt.executeUpdate();
//                 System.out.println("Role column added to Admin table successfully!");
//             }
//         } catch (Exception e) {
//             System.err.println("Error adding role column: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }
// }
