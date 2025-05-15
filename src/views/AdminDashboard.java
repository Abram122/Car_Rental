package views;

import car_rental.Main;
import models.Customer;
import utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminDashboard extends JPanel {
    private String adminEmail;

    public AdminDashboard(Main mainFrame ) {
        this(mainFrame, null);
    }

    public AdminDashboard(Main mainFrame, String adminEmail) {
        this.adminEmail = adminEmail;
        setBackground(AppColors.MAIN_BG);
        setLayout(new BorderLayout(0, 10));
        // Add header panel
        add(createHeaderPanel(mainFrame), BorderLayout.NORTH);

        // Add main content panel with cards
        add(createMainContentPanel(mainFrame), BorderLayout.CENTER);

        // Add footer panel
        add(createFooterPanel(), BorderLayout.SOUTH);

        mainFrame.setSize(800, 1000);
    }

    private JPanel createHeaderPanel(Main mainFrame) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.ACCENT_TIFFANY);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(AppColors.ACCENT_PURPLE);
        logoutButton.setForeground(AppColors.LIGHT_TEXT);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutButton.addActionListener(_ -> logout(mainFrame));
        headerPanel.add(logoutButton, BorderLayout.EAST);

        return headerPanel;
    }

    private void logout(Main mainFrame) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            mainFrame.getContentPane().removeAll();
            mainFrame.add(new LoginView(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }

    private JPanel createMainContentPanel(Main mainFrame) {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(AppColors.MAIN_BG);
        contentPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Use a GridBagLayout for cards (3 columns, dynamic rows)
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Spacing between cards
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;        // Admin actions as cards
        String[] adminActions = {
                "Manage Users", "Manage Admins", "Manage Bookings", "Manage Cars", "Manage Car Models","Manage Car Brands",
                "Manage Categories",
                "Manage Payments", "Manage Maintenance", "Moderate Reviews",
                "View Rental History", "Generate Invoices", "Manage Discounts"
        };

        // Add cards dynamically
        int gridX = 0;
        int gridY = 0;
        for (String action : adminActions) {
            JPanel card = createCard(action, mainFrame);
            gbc.gridx = gridX;
            gbc.gridy = gridY;
            contentPanel.add(card, gbc);

            // Update grid position (3 cards per row)
            gridX++;
            if (gridX > 2) {
                gridX = 0;
                gridY++;
            }
        }

        return contentPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(AppColors.ACCENT_TIFFANY.darker());
        footerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Current date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        JLabel dateLabel = new JLabel(now.format(formatter));
        dateLabel.setForeground(AppColors.LIGHT_TEXT);
        footerPanel.add(dateLabel, BorderLayout.WEST);

        // Support contact info
        JLabel supportLabel = new JLabel("Support: admin@carrentalsystem.com");
        supportLabel.setForeground(AppColors.LIGHT_TEXT);
        footerPanel.add(supportLabel, BorderLayout.EAST);

        return footerPanel;
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
                card.setBackground(AppColors.ACCENT_PURPLE); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(AppColors.ACCENT_TIFFANY); 
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                navigateToPage(action, mainFrame);
            }
        });

        return card;
    }

    private void navigateToPage(String action, Main mainFrame) {
        JPanel page = null;       
        switch (action) {
            case "Manage Users":
                page = new ManageUsersView(mainFrame, adminEmail);
                break;
            case "Manage Admins":
                page = new ManageAdminsView(mainFrame, adminEmail);
                break;
            case "Manage Bookings":
                page = new ManageBookingsView(mainFrame);
                break;
            case "Manage Cars":
                page = new ManageCarView(mainFrame);
                break;
            case "Manage Car Models":
                page = new ManageCarModelView(mainFrame);
                break;
            case "Manage Car Brands":
                page = new ManageCarBrandView(mainFrame);
                break;
            case "Manage Categories":
                page = new ManageCategoryView(mainFrame);
<<<<<<< Updated upstream
                break;            case "Manage Payments":
                // page = new ManagePaymentsView(mainFrame);
=======
                break;
            case "Manage Payments":
                page = new AdminPaymentsView(mainFrame);
>>>>>>> Stashed changes
                break;
            case "Manage Maintenance":
                page = new ManageMaintenanceView(mainFrame);
                break;
            case "Moderate Reviews":
                // page = new ModerateReviewsView(mainFrame);
                break;
            case "View Rental History":
                // page = new RentalHistoryView(mainFrame);
                break;
            case "Generate Invoices":
                // page = new GenerateInvoicesView(mainFrame);
                break;
            case "Manage Discounts":
                page = new ManageDiscountView(mainFrame);
                break;
            default:
                // Placeholder for unknown actions
                page = new JPanel(new BorderLayout());
                page.setBackground(AppColors.MAIN_BG);
                JLabel placeholder = new JLabel("Page for: " + action, SwingConstants.CENTER);
                placeholder.setForeground(AppColors.LIGHT_TEXT);
                placeholder.setFont(new Font("Arial", Font.PLAIN, 16));
                page.add(placeholder, BorderLayout.CENTER);
                break;
        }

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
        });        backButton.addActionListener(_ -> {
            mainFrame.getContentPane().removeAll();
            mainFrame.add(new AdminDashboard(mainFrame, adminEmail));
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