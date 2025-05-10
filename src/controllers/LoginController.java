package controllers;

import dao.AdminDAO;
import dao.CustomerDAO;
import utils.ValidationException;

public class LoginController {
    private AdminDAO adminDAO;
    private CustomerDAO customerDAO;

    public LoginController() {
        this.adminDAO = new AdminDAO();
        this.customerDAO = new CustomerDAO();
    }

    public boolean login(String email, String password, boolean isAdmin)
            throws ValidationException {
        // simple validation
        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new ValidationException(
                    "All fields are required! Please fill in both email and password.");
        }
        // check the email if belogs to admin or customer
        if (isAdmin) {
            return adminDAO.login(email, password);

        }
        // check email verification
        if (!customerDAO.isVerified(email)) {
            throw new ValidationException("NOT_VERIFIED");
        } else {
            return customerDAO.login(email, password);
        }

    }
}