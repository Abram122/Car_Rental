package views;

import car_rental.Main;
import utils.AppColors;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppView extends JPanel {

    public AppView(Main mainFrame) {
        // Set background color
        setBackground(AppColors.MAIN_BG);

        // Use a GridBagLayout for cards (3 columns, dynamic rows)
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
                "View Rental History", "Generate Invoices", "Manage Discounts" // adjust these as needed
        };

        // Add cards dynamically
        int gridX = 0;
        int gridY = 0;
        for (String action : adminActions) {
            JPanel card = createCard(action, mainFrame);
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

    private JPanel createCard(String action, Main mainFrame) {
        JPanel card = new JPanel();
        card.setBackground(AppColors.ACCENT_TIFFANY); // Card background
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 100));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Card label
        JLabel label = new JLabel(action, SwingConstants.CENTER);
        label.setForeground(AppColors.LIGHT_TEXT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        card.add(label, BorderLayout.CENTER);

        // Hover effect and navigation
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(AppColors.ACCENT_PURPLE); // Hover effect
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(AppColors.ACCENT_TIFFANY); // Reset color
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                navigateToPage(action, mainFrame);
            }
        });

        return card;
    }

    private void navigateToPage(String action, Main mainFrame) {
        // Create a new JPanel for the action
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(AppColors.MAIN_BG);

        // Add placeholder content
        JLabel contentLabel = new JLabel("Page for: " + action, SwingConstants.CENTER);
        contentLabel.setForeground(AppColors.LIGHT_TEXT);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        page.add(contentLabel, BorderLayout.CENTER);

        // Add a back button to return to the dashboard
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(AppColors.ACCENT_TIFFANY);
        backButton.setForeground(AppColors.MAIN_BG);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(AppColors.ACCENT_PURPLE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(AppColors.ACCENT_TIFFANY);
            }
        });
        backButton.addActionListener(e -> {
            mainFrame.getContentPane().removeAll();
            mainFrame.add(new AppView(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        page.add(backButton, BorderLayout.SOUTH);

        // Navigate to the new page
        mainFrame.getContentPane().removeAll();
        mainFrame.add(page);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}