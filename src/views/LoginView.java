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
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;
    private JButton loginButton;
    private JLabel switchToRegisterLabel;

    public LoginView(Main mainFrame) {
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
        add(new JLabel("Username", JLabel.RIGHT)).setForeground(AppColors.LIGHT_TEXT);
        add(usernameField, "wrap, growx, gapbottom 10");

        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setBackground(AppColors.DIVIDER_DARK_GRAY);
        passwordField.setForeground(AppColors.LIGHT_TEXT);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(new JLabel("Password", JLabel.RIGHT)).setForeground(AppColors.LIGHT_TEXT);
        add(passwordField, "wrap, growx, gapbottom 10");

        // Admin checkbox
        adminCheckBox = new JCheckBox("Login as Admin");
        adminCheckBox.setForeground(AppColors.LIGHT_TEXT);
        adminCheckBox.setBackground(AppColors.MAIN_BG);
        add(adminCheckBox, "span, center, gapbottom 20");

        // Login button
        loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(_ -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean isAdmin = adminCheckBox.isSelected();
                LoginController loginController = new LoginController();
                boolean success = loginController.login(username, password, isAdmin);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    // should redirect to the main application view
                    // For now, just print a message
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(loginButton, "span, center, gapbottom 10");

        // Switch to Register label
        switchToRegisterLabel = new JLabel("Don't have an account? Register");
        switchToRegisterLabel.setForeground(AppColors.ACCENT_TIFFANY);
        switchToRegisterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchToRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Switch to RegisterView by replacing the content pane
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