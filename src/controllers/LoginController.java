package controllers;

import dao.UserDAO;
import dao.AdminDAO;
import utils.ValidationException;
import controllers.OtpService;

public class LoginController {
    private UserDAO userDAO;
    private AdminDAO adminDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
        this.adminDAO = new AdminDAO();
    }

    public boolean login(String username, String password, boolean isAdmin)
            throws ValidationException {

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new ValidationException(
                    "All fields are required! Please fill in both username and password.");
        }

        if (isAdmin) {

            if (!adminDAO.login(username, password)) {
                return false;
            }

            if (adminDAO.isOtpEnabled(username)) {

                String email = adminDAO.getEmailByUsername(username);
                try {
                    OtpService.generateAndSendOtp(username, email);
                } catch (Exception e) {
                    throw new ValidationException(
                            "Failed to send OTP email: " + e.getMessage());
                }

                throw new ValidationException("OTP_REQUIRED");
            }

            return true;
        } else {

            return userDAO.login(username, password);
        }
    }

    /**
     * Called by the UI when the admin submits their OTP code.
     */
    public boolean verifyAdminOtp(String username, String code) {
        return adminDAO.verifyOtp(username, code);
    }
}
