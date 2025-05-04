package controllers;

import dao.UserDAO;
import dao.AdminDAO;
import dao.CustomerDAO;
import utils.ValidationException;

public class LoginController {
    private UserDAO userDAO;
    private AdminDAO adminDAO;
    private CustomerDAO customerDAO;
    
    public LoginController() {
        this.userDAO = new UserDAO();
        this.adminDAO = new AdminDAO();
        this.customerDAO = new CustomerDAO();
    }

    public boolean login(String email, String password, boolean isAdmin)
            throws ValidationException {

        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new ValidationException(
                    "All fields are required! Please fill in both email and password.");
        }

        if (!customerDAO.is_verified(email)) {

            throw new ValidationException("NOT_VERIFIED"); 
        }

        if (isAdmin) {
            return adminDAO.login(email, password);
        } else {
            return userDAO.login(email, password);
        }
    }
}