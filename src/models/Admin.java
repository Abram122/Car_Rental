package models;

public class Admin extends User {
    protected String admin_role;

    public Admin(int userId, String username, String password, String phone, String admin_role) {
        super(userId, username, password, phone);
        this.admin_role = admin_role;
    }
    
    public String getAdminRole() {
        return admin_role;
    }
    
    public void setAdminRole(String admin_role) {
        this.admin_role = admin_role;
    }
    
    public boolean isSuperAdmin() {
        return "SUPER_ADMIN".equals(admin_role);
    }
}