import com.formdev.flatlaf.FlatDarkLaf;
import net.miginfocom.swing.MigLayout;
import utils.AppColors;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf");
        }

        SwingUtilities.invokeLater(Main::showLoginForm);
    }

    static void showLoginForm() {
        JFrame frame = new JFrame("Login - Car Rental");
        frame.getContentPane().setBackground(AppColors.MAIN_BG);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new MigLayout("wrap 2", "[][grow]", "10[]10[]10[]10[]10[]"));

        JLabel title = new JLabel("Car Rental Login");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(AppColors.LIGHT_TEXT);
        frame.add(title, "span, center");

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JCheckBox adminCheck = new JCheckBox("Login as Admin");
        adminCheck.setForeground(AppColors.LIGHT_TEXT);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(AppColors.ACCENT_TIFFANY);
        loginButton.setForeground(AppColors.MAIN_BG);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(AppColors.ACCENT_PURPLE);
        registerButton.setForeground(AppColors.MAIN_BG);

        JLabel userLabel = new JLabel("Username or Email:");
        userLabel.setForeground(AppColors.LIGHT_TEXT);
        frame.add(userLabel);
        frame.add(usernameField, "growx");

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(AppColors.LIGHT_TEXT);
        frame.add(passLabel);
        frame.add(passwordField, "growx");

        frame.add(adminCheck, "span");
        frame.add(loginButton, "span, growx");
        frame.add(registerButton, "span, growx, right");

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        registerButton.addActionListener(e -> {
            frame.dispose();
            showRegisterForm();
        });
    }

    static void showRegisterForm() {
        JFrame frame = new JFrame("Register - Car Rental");
        frame.getContentPane().setBackground(AppColors.MAIN_BG);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new MigLayout("wrap 2", "[][grow]", "10[]10[]10[]10[]10[]"));

        JLabel title = new JLabel("Create New Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(AppColors.LIGHT_TEXT);
        frame.add(title, "span, center");

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField phoneField = new JTextField();
        JButton registerButton = new JButton("Register");

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(AppColors.LIGHT_TEXT);
        frame.add(userLabel);
        frame.add(usernameField, "growx");

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(AppColors.LIGHT_TEXT);
        frame.add(passLabel);
        frame.add(passwordField, "growx");

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setForeground(AppColors.LIGHT_TEXT);
        frame.add(phoneLabel);
        frame.add(phoneField, "growx");

        registerButton.setBackground(AppColors.ACCENT_PURPLE);
        registerButton.setForeground(AppColors.MAIN_BG);
        frame.add(registerButton, "span, growx");

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
