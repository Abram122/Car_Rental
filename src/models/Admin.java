package models;

public class Admin extends User {
    protected String admin_role;

    public Admin(int userId, String username, String password, String phone, String admin_role) {
        super(userId, username, password, phone);
        this.admin_role = admin_role;
    }


}