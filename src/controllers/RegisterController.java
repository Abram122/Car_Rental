package controllers;

import utils.HashUtil;
import dao.AdminDAO;
import dao.CustomerDAO;
import models.Admin;
import models.Customer;
import utils.ValidationException;
import utils.ValidationUtil;
import java.util.List;

public class RegisterController {
    private CustomerDAO customerDAO;
    private AdminDAO adminDAO;

    public RegisterController() {
        this.customerDAO = new CustomerDAO();
        this.adminDAO = new AdminDAO();
    }

    public boolean register(String username, String password, String email, String phone, String licenseNumber)
            throws ValidationException {
        // Validate input fields using ValidationUtil
        ValidationUtil.isValidName(username); // Validate username
        ValidationUtil.isValidEmail(email); // Validate email
        ValidationUtil.isValidEgyptianPhone(phone); // Validate phone number
        ValidationUtil.isValidLicenseNumber(licenseNumber); // Validate license number
        ValidationUtil.isValidPassword(password); // Validate password

        // Create a userID (similar to MongoDB system)
        int userId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

        // Hash password
        String hashed = HashUtil.hashPassword(password);

        // Pass the hash (not plain text) to the DAO
        return customerDAO.register(userId, username, hashed, email, phone, licenseNumber);
    }
    
    public boolean registerAdmin(String username, String password, String email, String role) 
            throws ValidationException {
        // Validate input fields
        ValidationUtil.isValidName(username);
        ValidationUtil.isValidEmail(email);
        ValidationUtil.isValidPassword(password);
        
        // Create an adminID
        int adminId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        
        // Hash password
        String hashed = HashUtil.hashPassword(password);
        
        // Register the admin with the hashed password
        return adminDAO.register(adminId, username, hashed, email, role);
    }
    
    public boolean registerAdmin(String username, String password, String email, String role, boolean storeOriginalPassword) 
            throws ValidationException {
        // Validate input fields
        ValidationUtil.isValidName(username);
        ValidationUtil.isValidEmail(email);
        ValidationUtil.isValidPassword(password);
        
        // Create an adminID
        int adminId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        
        // Store the original password if requested (for super admin viewing)
        if (storeOriginalPassword) {
            // In a real system, you might want to encrypt this differently or use a more secure approach
            // This is just for demonstration purposes
            savePasswordForAdmin(email, password);
        }
        
        // Hash password
        String hashed = HashUtil.hashPassword(password);
        
        // Register the admin with the hashed password
        return adminDAO.register(adminId, username, hashed, email, role);
    }
    
    private void savePasswordForAdmin(String email, String password) {
        // In a real system, you would use a more secure approach
        // This is just for demonstration purposes
        try {
            String fileName = "admin_passwords.txt";
            java.nio.file.Path path = java.nio.file.Paths.get(fileName);
            
            String entry = email + ":" + password + "\n";
            
            if (java.nio.file.Files.exists(path)) {
                java.nio.file.Files.write(path, entry.getBytes(), 
                        java.nio.file.StandardOpenOption.APPEND);
            } else {
                java.nio.file.Files.write(path, entry.getBytes(), 
                        java.nio.file.StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getAdminPassword(String email) {
        try {
            String fileName = "admin_passwords.txt";
            java.nio.file.Path path = java.nio.file.Paths.get(fileName);
            
            if (!java.nio.file.Files.exists(path)) {
                return null;
            }
            
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(email)) {
                    return parts[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean isSuperAdmin(String email) {
        return adminDAO.isSuperAdmin(email);
    }
    
    public List<Admin> getAllAdmins() {
        return adminDAO.getAllAdmins();
    }
    
    public boolean deleteAdmin(int adminId) {
        return adminDAO.deleteAdmin(adminId);
    }

    public boolean delete(String email) {
        return customerDAO.delete(email);
    }
    
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}