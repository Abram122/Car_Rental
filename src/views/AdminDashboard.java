package views;

import javax.swing.*;

import car_rental.Main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JFrame {

    public AdminDashboard(Main mainFrame) {
        // Set up the frame
        setTitle("Car Rental - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(44, 62, 80)); // #2C3E50

        // Use a GridLayout for cards (3 columns, dynamic rows)
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Spacing between cards
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Admin actions as cards
        String[] adminActions = {
                "Manage Users", "Manage Bookings", "Manage Cars",
                "Manage Payments", "Manage Maintenance", "Moderate Reviews",
                "View Rental History", "Generate Invoices", "Manage Discounts"
        };

        // Add cards dynamically
        int gridX = 0;
        int gridY = 0;
        for (String action : adminActions) {
            JPanel card = createCard(action);
            gbc.gridx = gridX;
            gbc.gridy = gridY;
            add(card, gbc);

            // Update grid position (3 cards per row)
            gridX++;
            if (gridX > 2) {
                gridX = 0;
                gridY++;
            }
        }
    }

    private JPanel createCard(String action) {
        JPanel card = new JPanel();
        card.setBackground(new Color(52, 152, 219)); // #3498DB
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 100));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Card label
        JLabel label = new JLabel(action, SwingConstants.CENTER);
        label.setForeground(new Color(236, 240, 241)); // #ECF0F1
        label.setFont(new Font("Arial", Font.BOLD, 14));
        card.add(label, BorderLayout.CENTER);

        // Hover effect and navigation
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(41, 128, 185)); // #2980B9 on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(52, 152, 219)); // #3498DB
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Navigate to a new page (new JFrame for now)
                navigateToPage(action);
            }
        });

        return card;
    }

    private void navigateToPage(String action) {
        // Create a new JFrame for the action
        JFrame page = new JFrame(action);
        page.setSize(400, 300);
        page.setLocationRelativeTo(null);
        page.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        page.getContentPane().setBackground(new Color(44, 62, 80)); // #2C3E50

        // Add placeholder content
        JLabel contentLabel = new JLabel("Page for: " + action, SwingConstants.CENTER);
        contentLabel.setForeground(new Color(236, 240, 241)); // #ECF0F1
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        page.add(contentLabel, BorderLayout.CENTER);

        // Add a back button to return to the dashboard
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(52, 152, 219)); // #3498DB
        backButton.setForeground(new Color(236, 240, 241)); // #ECF0F1
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            page.dispose();
            AdminDashboard.this.setVisible(true);
        });
        page.add(backButton, BorderLayout.SOUTH);

        // Hide the dashboard and show the new page
        this.setVisible(false);
        page.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard dashboard = new AdminDashboard(null);
            dashboard.setVisible(true);
        });
    }
}