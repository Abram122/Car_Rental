package models;

public class Customer extends User {
    private String license_number;
    public Customer(int userId, String username, String password, String phone, String license_number) {
        super(userId, username, password, phone);
        this.license_number = license_number;
    }
    public String getLicenseNumber() {
        return license_number;
    }
    public void setLicenseNumber(String license_number) {
        this.license_number = license_number;
    }

}