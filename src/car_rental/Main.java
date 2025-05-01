package car_rental;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import views.LoginView;
import migrations.DBMigration;
import utils.MySQLConnection;
import java.sql.Connection;

public class Main extends JFrame {

    public Main() {
        // Set Up FlatLaf For Better UI
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Application Frame Setup
        setTitle("Car Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window

        // Create and add the LoginView as the starting point
        LoginView loginView = new LoginView(this);
        add(loginView);
    }

    public static void main(String[] args) {
        // Uncomment the following lines to run migrations
    
        // try (Connection conn = MySQLConnection.getInstance().getConnection()) {
        //     DBMigration.migrate(conn);
        // } catch (Exception e) {
        //     System.err.println("Failed to run migrations:");
        //     e.printStackTrace();
        // }

        // Start the application
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}