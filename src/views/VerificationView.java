package views;

import car_rental.Main;
import controllers.OtpService;
import utils.AppColors;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VerificationView extends JPanel {
    private JTextField otpField;
    private JButton verifyButton;
    private JLabel switchToLoginLabel;
    private String username;
    private String email;

    public VerificationView(Main mainFrame, String username, String email) {
        this.username = username;
        this.email = email;
        setBackground(AppColors.MAIN_BG);
        setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));

        // Title
        JLabel titleLabel = new JLabel("Verify Your Email");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        add(titleLabel, "span, center, gapbottom 20");

        // OTP field
        otpField = new JTextField(6);
        otpField.setBackground(AppColors.DIVIDER_DARK_GRAY);
        otpField.setForeground(AppColors.LIGHT_TEXT);
        otpField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(new JLabel("Verification Code", JLabel.RIGHT)).setForeground(AppColors.LIGHT_TEXT);
        add(otpField, "wrap, growx, gapbottom 20");

        // Verify button
        verifyButton = new JButton("Verify");
        styleButton(verifyButton);
        verifyButton.addActionListener(_ -> {
            String otp = otpField.getText();
            try {
                // Assuming OtpService has a verifyOtp method
                boolean success = OtpService.verifyOtp(username, otp);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Verification successful!");
                    // Switch to LoginView
                    mainFrame.getContentPane().removeAll();
                    mainFrame.add(new LoginView(mainFrame));
                    mainFrame.revalidate();
                    mainFrame.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid or expired OTP!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(verifyButton, "span, center, gapbottom 10");

        // Switch to Login label
        switchToLoginLabel = new JLabel("Return to Login");
        switchToLoginLabel.setForeground(AppColors.ACCENT_TIFFANY);
        switchToLoginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Switch to LoginView
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new LoginView(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                switchToLoginLabel.setForeground(AppColors.ACCENT_PURPLE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                switchToLoginLabel.setForeground(AppColors.ACCENT_TIFFANY);
            }
        });
        add(switchToLoginLabel, "span, center");
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