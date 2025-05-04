package dao;
import utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.HashUtil;



public class UserDAO {

    private Connection conn;

    public UserDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean login(String email, String password) {
    String sql = "SELECT password_hash FROM user WHERE email = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
    stmt.setString(1, email);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
    String storedHash = rs.getString("password_hash");

                        return HashUtil.verifyPassword(password, storedHash);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        }
    


    