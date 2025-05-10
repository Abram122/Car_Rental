package controllers;

import utils.HashUtil;
import dao.AdminDAO;
import dao.CustomerDAO;
import utils.ValidationException;
import utils.ValidationUtil;

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

        // Uncomment the following line if you need to register an admin
        // return adminDAO.register(userId, username, hashed, email);
    }

    public boolean delete(String email) {
        return customerDAO.delete(email);
    }
}