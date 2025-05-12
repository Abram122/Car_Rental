package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Review;
import utils.MySQLConnection;

public class ReviewDAO {
    private Connection conn;

    public ReviewDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    // Add a new review to the database
    public boolean addReview(Review review) {
        String sql = "INSERT INTO review (review_text) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, review.getReview());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve a review by ID
    public Review getReviewById(int id) {
        String sql = "SELECT * FROM review WHERE review_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Review review = new Review();
                    review.setReviewID(rs.getInt("review_id"));
                    review.setReview(rs.getString("review_text"));
                    return review;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all reviews
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review ORDER BY review_id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("review_id"));
                review.setReview(rs.getString("review_text"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    // Update an existing review
    public boolean updateReview(Review review) {
        String sql = "UPDATE review SET review_text = ? WHERE review_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, review.getReview());
            stmt.setInt(2, review.getReviewID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a review by ID
    public boolean deleteReview(int id) {
        String sql = "DELETE FROM review WHERE review_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
