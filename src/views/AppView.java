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

public class AppView extends JPanel {

    private Customer customer;

    public AppView(Main mainFrame, Customer customer) {
        this.customer = customer; // Store the customer object
        setBackground(AppColors.MAIN_BG);
        setLayout(new BorderLayout(0, 10));
        
        // Add header panel
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Add main content panel with cards
        add(createMainContentPanel(mainFrame), BorderLayout.CENTER);
        
        // Add footer panel
        add(createFooterPanel(mainFrame), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.ACCENT_TIFFANY);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Create welcome message with customer name
        String greeting = getGreeting();
        JLabel welcomeLabel = new JLabel(greeting + ", " + customer.getUsername() + "!");
        welcomeLabel.setForeground(AppColors.LIGHT_TEXT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
          // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(AppColors.ACCENT_PURPLE);
        logoutButton.setForeground(AppColors.LIGHT_TEXT);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                logout(logoutButton);
            }
        });
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private String getGreeting() {
        int hour = LocalDateTime.now().getHour();
        if (hour >= 5 && hour < 12) {
            return "Good Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
    }
    
    private void logout(Component component) {
        int confirm = JOptionPane.showConfirmDialog(
            component,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Get parent frame (Main)
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof Main) {
                Main mainFrame = (Main) window;
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new LoginView(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
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
        gbc.weighty = 1.0;

        // User actions as cards
        String[] userActions = {
                "Your Profile", "Available Cars", "Book a Car", "Your Reservations"
        };

        // Add cards dynamically
        int gridX = 0;
        int gridY = 0;
        for (String action : userActions) {
            JPanel card = createCard(action, mainFrame);
            gbc.gridx = gridX;
            gbc.gridy = gridY;
            contentPanel.add(card, gbc);

            // Update grid position (2 cards per row)
            gridX++;
            if (gridX > 1) {
                gridX = 0;
                gridY++;
            }
        }
        
        return contentPanel;
    }
    
    private JPanel createFooterPanel(Main mainFrame) {
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
        JLabel supportLabel = new JLabel("Support: help@carrentalsystem.com");
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
    }    private void navigateToPage(String action, Main mainFrame) {
        JPanel page = null;

        // Dynamically instantiate the target page based on the action
        switch (action) {
            case "Your Profile":
                page = new ProfileView(mainFrame, customer); 
                break;
            case "Available Cars":
                page = createAvailableCarsPanel(mainFrame);
                break;
            case "Book a Car":
                page = createBookCarPanel(mainFrame);
                break;
            case "Your Reservations":
                // page = new ReservationsView(mainFrame, customer);
                // For now, show a placeholder until this view is implemented
                page = createPlaceholderPanel(action, "View and manage your current reservations");
                break;
            default:
                // Placeholder for unknown actions
                page = createPlaceholderPanel(action, "This feature is coming soon");
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
        });        backButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mainFrame.getContentPane().removeAll();
                mainFrame.add(new AppView(mainFrame, customer));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        page.add(backButton, BorderLayout.SOUTH);

        // Navigate to the new page
        mainFrame.getContentPane().removeAll();
        mainFrame.add(page);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private JPanel createPlaceholderPanel(String title, String description) {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(AppColors.MAIN_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Description and placeholder content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AppColors.MAIN_BG);
        
        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setForeground(AppColors.LIGHT_TEXT);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JLabel comingSoonLabel = new JLabel("Under Development", SwingConstants.CENTER);
        comingSoonLabel.setForeground(AppColors.ACCENT_PURPLE);
        comingSoonLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        contentPanel.add(descLabel, BorderLayout.NORTH);
        contentPanel.add(comingSoonLabel, BorderLayout.CENTER);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createAvailableCarsPanel(Main mainFrame) {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(AppColors.MAIN_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Available Cars", SwingConstants.CENTER);
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Cars grid panel
        JPanel carsPanel = new JPanel();
        carsPanel.setLayout(new BoxLayout(carsPanel, BoxLayout.Y_AXIS));
        carsPanel.setBackground(AppColors.MAIN_BG);
        
        // Create a scroll pane in case there are many cars
        JScrollPane scrollPane = new JScrollPane(carsPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        try {
            // Get available cars from controller
            controllers.CarController carController = new controllers.CarController();
            java.util.List<models.Car> cars = carController.getAllCars().stream()
                    .filter(car -> "Available".equals(car.getAvailabilityStatus()))
                    .collect(java.util.stream.Collectors.toList());
            
            if (cars.isEmpty()) {
                JLabel noCarsLabel = new JLabel("No cars available at the moment.", SwingConstants.CENTER);
                noCarsLabel.setForeground(AppColors.LIGHT_TEXT);
                noCarsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                carsPanel.add(noCarsLabel);
            } else {
                // Add each car as a panel
                for (models.Car car : cars) {
                    JPanel carPanel = createCarListItemPanel(car, mainFrame);
                    carsPanel.add(carPanel);
                    carsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between cars
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error loading cars: " + e.getMessage(), SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            carsPanel.add(errorLabel);
        }
        
        return panel;
    }
    
    private JPanel createBookCarPanel(Main mainFrame) {
        // Reuse the available cars panel, but with booking functionality
        JPanel panel = createAvailableCarsPanel(mainFrame);
        
        // Change the title
        Component titleComp = ((BorderLayout)panel.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        if (titleComp instanceof JLabel) {
            ((JLabel) titleComp).setText("Book a Car");
        }
        
        return panel;
    }
    
    private JPanel createCarListItemPanel(models.Car car, Main mainFrame) {
        JPanel carPanel = new JPanel(new BorderLayout(10, 0));
        carPanel.setBackground(AppColors.ACCENT_TIFFANY.darker());
        carPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Car image (left)
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 80));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        try {
            // Try to load image
            if (car.getImageURL() != null && !car.getImageURL().isEmpty()) {
                ImageIcon icon = new ImageIcon(car.getImageURL());
                Image img = icon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setText("No Image");
                imageLabel.setForeground(AppColors.LIGHT_TEXT);
            }
        } catch (Exception e) {
            imageLabel.setText("No Image");
            imageLabel.setForeground(AppColors.LIGHT_TEXT);
        }
        
        carPanel.add(imageLabel, BorderLayout.WEST);
        
        // Car details (center)
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(AppColors.ACCENT_TIFFANY.darker());
        
        JLabel nameLabel = new JLabel(car.getFullModelName());
        nameLabel.setForeground(AppColors.LIGHT_TEXT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel categoryLabel = new JLabel("Category: " + car.getCategoryName());
        categoryLabel.setForeground(AppColors.LIGHT_TEXT);
        
        JLabel priceLabel = new JLabel(String.format("$%.2f/day", car.getRentalPrice()));
        priceLabel.setForeground(AppColors.LIGHT_TEXT);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(categoryLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(priceLabel);
        
        carPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // View details button (right)
        JButton viewButton = new JButton("View Details");        viewButton.setBackground(AppColors.ACCENT_PURPLE);
        viewButton.setForeground(AppColors.LIGHT_TEXT);
        viewButton.setFocusPainted(false);
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                CarDetailView.showCarDetails(mainFrame, car);
            }
        });
        
        carPanel.add(viewButton, BorderLayout.EAST);
        
        // Make the entire panel clickable to view details
        carPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                carPanel.setBackground(AppColors.ACCENT_PURPLE.darker());
                detailsPanel.setBackground(AppColors.ACCENT_PURPLE.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                carPanel.setBackground(AppColors.ACCENT_TIFFANY.darker());
                detailsPanel.setBackground(AppColors.ACCENT_TIFFANY.darker());
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                CarDetailView.showCarDetails(mainFrame, car);
            }
        });
        
        return carPanel;
    }
}