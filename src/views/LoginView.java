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



public class LoginView extends JPanel {
    private JTextField emailField;
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

        // email field
        emailField = new JTextField(20);
        emailField.setBackground(AppColors.DIVIDER_DARK_GRAY);
        emailField.setForeground(AppColors.LIGHT_TEXT);
        emailField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel userLabel = new JLabel("email", JLabel.RIGHT);
        userLabel.setForeground(AppColors.LIGHT_TEXT);
        add(userLabel);
        add(emailField, "wrap, growx, gapbottom 10");

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
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        boolean isAdmin = adminCheckBox.isSelected();

        try {
            boolean success = loginController.login(email, password, isAdmin);
            if (success) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                mainFrame.getContentPane().removeAll();
                // Check if the user is an admin
                if (isAdmin) {
                    // Open the admin dashboard
                    mainFrame.add(new AdminDashboard(mainFrame));
                } else {
                    // Open the user dashboard
                    mainFrame.add(new AppView(mainFrame));
                }
                mainFrame.revalidate();
                mainFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid credentials!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (ValidationException ve) {
            if ("NOT_VERIFIED".equals(ve.getMessage())) {
                // Open the verification view
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new VerificationView(mainFrame, email)); 
                mainFrame.revalidate();
                mainFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        ve.getMessage(),
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
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