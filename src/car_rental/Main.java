package car_rental;

import com.formdev.flatlaf.FlatDarkLaf;
import views.LoginView;
import migrations.DBMigration;
import utils.MySQLConnection;

import javax.swing.*;
import java.sql.Connection;

public class Main extends JFrame {

    public Main() {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("Failed to set FlatLaf theme:");
            e.printStackTrace();
        }

        // Basic JFrame setup
        setTitle("Car Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load Login View
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Ensure layout manager
        LoginView loginView = new LoginView(this);
        add(loginView);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Optional: Run database migrations once
        /*
        try (Connection conn = MySQLConnection.getInstance().getConnection()) {
            DBMigration.migrate(conn);
        } catch (Exception e) {
            System.err.println("Failed to run DB migrations:");
            e.printStackTrace();
        }
        */

        // Launch the application
        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.setVisible(true);
        });
    }
}
