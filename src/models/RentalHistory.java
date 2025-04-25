
package models;
import java.time.LocalDateTime;
import java.sql.*;

public class RentalHistory {
    private int rentalId;
    private LocalDateTime actualReturnDate;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RentalHistory(int rentalId, LocalDateTime actualReturnDate, String comments) {
        this.rentalId = rentalId;
        this.actualReturnDate = actualReturnDate;
        this.comments = comments;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRentalDetails() {
        return "Rental ID: " + rentalId + ", Return Date: " + actualReturnDate + ", Comments: " + comments;
    }

    private int log() {
        String DB_URL = "jdbc:mysql://localhost:3306/car_rental";
        String USER = "root";
        String PASS = "1234";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO rental_logs (rentalId, actualReturnDate, comments, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.rentalId);
            pstmt.setTimestamp(2, Timestamp.valueOf(this.actualReturnDate));
            pstmt.setString(3, this.comments);
            pstmt.setTimestamp(4, Timestamp.valueOf(this.createdAt));
            pstmt.setTimestamp(5, Timestamp.valueOf(this.updatedAt));

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
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