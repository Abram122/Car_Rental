package models;
//User class but check if i applied tha abstract correclty abarm.
import java.sql.*;// didnot what to do next in the database section so i lefted it untile we finish the project
import java.util.Date;//check do you want it in date format or not 
// we can still use LocalDate and LocalDateTime and LocalTime if i am right check it also.

public abstract class User {
    protected int userId;
    protected String username;
    protected String password; 
    protected String phone;
    protected Date createdAt;
    protected Date updatedAt;

  
    public User(int userId, String username, String password, String phone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

   
    protected int login(String username, String password) {
        String DB_URL = "jdbc:mysql://localhost:3306/car_rental";
        String USER = "root";
        String PASS = "1234";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return 1; 
            } else {
                return 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }
}