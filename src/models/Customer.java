package models;

import java.time.LocalDate;

public class Customer extends User {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String licenseNumber;
    private LocalDate birthday;
    private boolean isVerified;

    public Customer(int userId, String username, String password, String phone, String license_number) {
        super(userId, username, password, phone);
        this.licenseNumber = license_number;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public boolean isVerified() {
        return isVerified;
    }
    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
    
}