package views;

import controllers.CarController;
import models.Car;
import utils.AppColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CarDetailView extends JPanel {
    private final Car car;
    private final CarController carController;

    public CarDetailView(Car car) {
        this.car = car;
        this.carController = new CarController();
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(AppColors.MAIN_BG);

        // Add car image panel
        add(createImagePanel(), BorderLayout.WEST);
        
        // Add car details panel
        add(createDetailsPanel(), BorderLayout.CENTER);
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(AppColors.MAIN_BG);
        imagePanel.setPreferredSize(new Dimension(300, 200));
        
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Load image from URL or use placeholder
        try {
            Image img;
            if (car.getImageURL() != null && !car.getImageURL().isEmpty()) {
                // Try loading from URL
                try {
                    URL url = new URL(car.getImageURL());
                    img = ImageIO.read(url);
                } catch (Exception e) {
                    // Try loading from file path
                    File file = new File(car.getImageURL());
                    img = ImageIO.read(file);
                }
            } else {
                // Load placeholder image
                img = ImageIO.read(new File("src/assets/car_placeholder.png"));
            }
            
            // Resize image to fit panel
            if (img != null) {
                Image resizedImg = img.getScaledInstance(280, 180, Image.SCALE_SMOOTH);
                ImageIcon imgIcon = new ImageIcon(resizedImg);
                imageLabel.setIcon(imgIcon);
                imageLabel.setText("");
            }
        } catch (IOException e) {
            imageLabel.setText("Image not available");
        }
        
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        return imagePanel;
    }

    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(AppColors.MAIN_BG);
        
        JLabel titleLabel = new JLabel(car.getFullModelName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(AppColors.ACCENT_TIFFANY);
        
        JLabel categoryLabel = new JLabel("Category: " + car.getCategoryName());
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel statusLabel = new JLabel("Status: " + car.getAvailabilityStatus());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        if ("Available".equals(car.getAvailabilityStatus())) {
            statusLabel.setForeground(new Color(34, 139, 34)); // Green
        } else {
            statusLabel.setForeground(new Color(178, 34, 34)); // Red
        }
        
        JLabel priceLabel = new JLabel(String.format("Rental Price: $%.2f/day", car.getRentalPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel mileageLabel = new JLabel("Mileage: " + car.getMileage() + " km");
        JLabel fuelLabel = new JLabel("Fuel Type: " + car.getFuelType());
        JLabel plateLabel = new JLabel("Plate Number: " + car.getPlateNo());
        
        // Add some spacing between components
        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(categoryLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(statusLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(priceLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(mileageLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(fuelLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(plateLabel);
        
        // Add buttons for actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(AppColors.MAIN_BG);
        
        JButton bookButton = new JButton("Book This Car");
        bookButton.setBackground(AppColors.ACCENT_PURPLE);
        bookButton.setForeground(Color.WHITE);
        
        JButton maintenanceButton = new JButton("View Maintenance");
        maintenanceButton.setBackground(AppColors.ACCENT_TIFFANY);
        maintenanceButton.setForeground(Color.WHITE);
        
        buttonPanel.add(bookButton);
        buttonPanel.add(maintenanceButton);
        
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        detailsPanel.add(buttonPanel);
        
        return detailsPanel;
    }
    
    // Static method to open car details in a dialog
    public static void showCarDetails(Component parent, Car car) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Car Details - " + car.getFullModelName());
        dialog.setModal(true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setContentPane(new CarDetailView(car));
        dialog.setVisible(true);
    }
}
