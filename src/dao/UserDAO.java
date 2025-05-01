package dao;
import utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private Connection conn;

    public UserDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password_hash = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
