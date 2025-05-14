package models;

import java.util.Date;


public abstract class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String phone;
    protected String email; // Added email field
    protected Date createdAt;
    protected Date updatedAt;

    public User(int userId, String username, String password, String phone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
