package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Category;
import utils.MySQLConnection;

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    // Add a new category
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO category (name, description, category_img, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getCategoryIMG());
            stmt.setTimestamp(4, Timestamp.valueOf(category.getCreatedAt()));
            stmt.setTimestamp(5, Timestamp.valueOf(category.getUpdatedAt()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get category by ID
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE category_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractCategoryFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category ORDER BY category_id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(extractCategoryFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Update category
    public boolean updateCategory(Category category) {
        String sql = "UPDATE category SET name = ?, description = ?, category_img = ?, updated_at = ? WHERE category_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getCategoryIMG());
            stmt.setTimestamp(4, Timestamp.valueOf(category.getUpdatedAt()));
            stmt.setInt(5, category.getCategoryID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete category
    public boolean deleteCategory(int id) {
        String sql = "DELETE FROM category WHERE category_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Extract Category from ResultSet
    private Category extractCategoryFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryID(rs.getInt("category_id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setCategoryIMG(rs.getString("category_img"));
        category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return category;
    }
}
