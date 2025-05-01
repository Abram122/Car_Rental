package controllers;

import dao.UserDAO;
import dao.AdminDAO;
import utils.ValidationException;

public class LoginController {
    private UserDAO userDAO;
    private AdminDAO adminDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
        this.adminDAO = new AdminDAO();
    }

    // Method to handle login for both customers and admins
    public boolean login(String username, String password, boolean isAdmin) throws ValidationException {
        // Validate input just simple validation for empty fields
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new ValidationException("All fields are required! Please fill in both username and password.");
        }

        // Proceed with login if validation passes
        if (isAdmin) {
            // Use AdminDAO for admin login
            return adminDAO.login(username, password);
        } else {
            // Use UserDAO for customer login
            return userDAO.login(username, password);
        }
    }
}