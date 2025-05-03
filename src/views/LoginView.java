package views;

import car_rental.Main;
import controllers.LoginController;
import utils.AppColors;
import utils.ValidationException;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * DashboardView: package-private class to display a welcome message.
 */
class DashboardView extends JPanel {
    public DashboardView(Main mainFrame, String username, boolean isAdmin) {
        setLayout(new BorderLayout());
        String role = isAdmin ? "Admin" : "User";
        JLabel label = new JLabel(
            "Welcome " + username + "! Logged in as " + role + ".",
            SwingConstants.CENTER
        );
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}

/**
 * LoginView: handles login and optional 2FA OTP prompt.
 */
public class LoginView extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;
    private JButton loginButton;
    private JLabel switchToRegisterLabel;
    private LoginController loginController;
    private Main mainFrame;

    public LoginView(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.loginController = new LoginController();

        setBackground(AppColors.MAIN_BG);
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        add(titleLabel, "span, center, gapbottom 20");

        // Username field
        usernameField = new JTextField(20);
        usernameField.setBackground(AppColors.DIVIDER_DARK_GRAY);
        usernameField.setForeground(AppColors.LIGHT_TEXT);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel userLabel = new JLabel("Username", JLabel.RIGHT);
        userLabel.setForeground(AppColors.LIGHT_TEXT);
        add(userLabel);
        add(usernameField, "wrap, growx, gapbottom 10");

        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setBackground(AppColors.DIVIDER_DARK_GRAY);
        passwordField.setForeground(AppColors.LIGHT_TEXT);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel passLabel = new JLabel("Password", JLabel.RIGHT);
        passLabel.setForeground(AppColors.LIGHT_TEXT);
        add(passLabel);
        add(passwordField, "wrap, growx, gapbottom 10");

        // Admin checkbox
        adminCheckBox = new JCheckBox("Login as Admin");
        adminCheckBox.setForeground(AppColors.LIGHT_TEXT);
        adminCheckBox.setBackground(AppColors.MAIN_BG);
        add(adminCheckBox, "span, center, gapbottom 20");

        // Login button
        loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(_e -> onLogin());
        add(loginButton, "span, center, gapbottom 10");

        // Switch to Register label
        switchToRegisterLabel = new JLabel("Don't have an account? Register");
        switchToRegisterLabel.setForeground(AppColors.ACCENT_TIFFANY);
        switchToRegisterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchToRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new RegisterView(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                switchToRegisterLabel.setForeground(AppColors.ACCENT_PURPLE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                switchToRegisterLabel.setForeground(AppColors.ACCENT_TIFFANY);
            }
        });
        add(switchToRegisterLabel, "span, center");
    }

    private void onLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        boolean isAdmin = adminCheckBox.isSelected();

        try {
            boolean success = loginController.login(username, password, isAdmin);
            if (success) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new DashboardView(mainFrame, username, isAdmin));
                mainFrame.revalidate();
                mainFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid credentials!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (ValidationException ve) {
            if ("OTP_REQUIRED".equals(ve.getMessage())) {
                promptForOtp(username);
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    ve.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void promptForOtp(String username) {
        String code = JOptionPane.showInputDialog(
            this,
            "Enter the 6-digit code sent to your email:",
            "Two-Factor Authentication",
            JOptionPane.PLAIN_MESSAGE
        );
        if (code != null && !code.trim().isEmpty()) {
            boolean verified = loginController.verifyAdminOtp(username, code.trim());
            if (verified) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new DashboardView(mainFrame, username, true));
                mainFrame.revalidate();
                mainFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid or expired code. Please try again.",
                    "OTP Verification Failed",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(AppColors.ACCENT_TIFFANY);
        button.setForeground(AppColors.MAIN_BG);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(AppColors.ACCENT_PURPLE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(AppColors.ACCENT_TIFFANY);
            }
        });
    }
}