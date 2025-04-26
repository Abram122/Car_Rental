package models;

import java.util.Date;//check do you want it in date format or not 

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

}