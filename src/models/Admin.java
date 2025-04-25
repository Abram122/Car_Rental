package models;

//This is the admin class and i inherited it from user class but make sure to review after me Abram
public class Admin extends User {
    protected String admin_role;

    // Constructor
    public Admin(int userId, String username, String password, String phone, String admin_role) {
        super(userId, username, password, phone);
        this.admin_role = admin_role;
    }

    // Override login method to be suitable for admin purpose.
    @Override
    protected int login(String username, String password) {
        return super.login(username, password); 
    }
}