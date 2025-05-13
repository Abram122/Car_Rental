package views;

import controllers.CarController;
import models.Car;
import utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageCarView extends JPanel {
    private final CarController carController;
    private JTable carTable;
    private DefaultTableModel tableModel;
    private final JFrame mainFrame;

    public ManageCarView(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.carController = new CarController();
        setLayout(new BorderLayout(10, 10));
        setBackground(AppColors.MAIN_BG);

        // Add the footer panel (with buttons) at the top
        add(createFooterPanel(), BorderLayout.NORTH);

        // Create a center panel to hold the header and table vertically
        JPanel centerWrapper = new JPanel(new BorderLayout(10, 10));
        centerWrapper.setBackground(AppColors.MAIN_BG);
        centerWrapper.add(createHeaderPanel(), BorderLayout.NORTH);

        // Wrap the table panel in a panel with GridBagLayout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(AppColors.MAIN_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(createTablePanel(), gbc);
        centerWrapper.add(centerPanel, BorderLayout.CENTER);

        add(centerWrapper, BorderLayout.CENTER);

        loadCars();
        mainFrame.setSize(800, 600);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.ACCENT_TIFFANY);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Car Management", SwingConstants.CENTER);
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppColors.MAIN_BG);
        tableModel = new DefaultTableModel(new Object[]{"ID", "Brand", "Model", "Registration", "Image", "Availability", "Mileage", "Rental Price", "Category ID"}, 0);
        carTable = new JTable(tableModel);
        carTable.setFillsViewportHeight(true);
        carTable.setRowHeight(30);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cars"));
        scrollPane.setPreferredSize(new Dimension(700, 400));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(AppColors.ACCENT_TIFFANY);

        JButton addButton = new JButton("Add Car");
        styleButton(addButton);
        addButton.addActionListener(_ -> showAddCarDialog());
        footerPanel.add(addButton);

        JButton updateButton = new JButton("Update Car");
        styleButton(updateButton);
        updateButton.addActionListener(_ -> showUpdateCarDialog());
        footerPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete Car");
        styleButton(deleteButton);
        deleteButton.setBackground(AppColors.ERROR_RED);
        deleteButton.addActionListener(_ -> deleteSelectedCar());
        footerPanel.add(deleteButton);

        JButton backButton = new JButton("Back to Dashboard");
        styleButton(backButton);
        backButton.addActionListener(_ -> navigateBack());
        footerPanel.add(backButton);

        return footerPanel;
    }

    private void styleButton(JButton button) {
        button.setBackground(AppColors.ACCENT_TIFFANY);
        button.setForeground(AppColors.LIGHT_TEXT);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void loadCars() {
        tableModel.setRowCount(0);
        List<Car> cars = carController.getAllCars();
        if (cars == null || cars.isEmpty()) {
            tableModel.addRow(new Object[]{"No cars found", "", "", "", "", "", "", "", ""});
            carTable.setEnabled(false);
        } else {
            for (Car car : cars) {
                tableModel.addRow(new Object[]{
                        car.getCarID(),
                        car.getBrand(),
                        car.getModel(),
                        car.getRegistration(),
                        car.getImageURL(),
                        car.isAvailability(car.getAvailability()) ? "Available" : "Not Available",
                        car.getMileAge(),
                        car.getRentalPrice(),
                        car.getCategoryID()
                });
            }
            carTable.setEnabled(true);
        }
    }

    private void showAddCarDialog() {
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField registrationField = new JTextField();
        JTextField imageURLField = new JTextField();
        JTextField mileageField = new JTextField();
        JTextField rentalPriceField = new JTextField();
        JTextField categoryIdField = new JTextField();
        JCheckBox availabilityCheckBox = new JCheckBox("Available");

        Object[] fields = {
                "Brand:", brandField,
                "Model:", modelField,
                "Registration:", registrationField,
                "Image URL:", imageURLField,
                "Mileage:", mileageField,
                "Rental Price:", rentalPriceField,
                "Category ID:", categoryIdField,
                "Available:", availabilityCheckBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Car", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                boolean success = carController.addCar(
                        Integer.parseInt(mileageField.getText()),
                        Integer.parseInt(rentalPriceField.getText()),
                        brandField.getText(),
                        modelField.getText(),
                        registrationField.getText(),
                        imageURLField.getText(),
                        availabilityCheckBox.isSelected(),
                        Float.parseFloat(mileageField.getText()),
                        Float.parseFloat(rentalPriceField.getText()),
                        Integer.parseInt(categoryIdField.getText())
                );
                if (success) {
                    JOptionPane.showMessageDialog(this, "Car added successfully!");
                    loadCars();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add car.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUpdateCarDialog() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car to update.");
            return;
        }
        int carId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentBrand = (String) tableModel.getValueAt(selectedRow, 1);
        String currentModel = (String) tableModel.getValueAt(selectedRow, 2);
        String currentRegistration = (String) tableModel.getValueAt(selectedRow, 3);
        String currentImageURL = (String) tableModel.getValueAt(selectedRow, 4);
        boolean currentAvailability = "Available".equals(tableModel.getValueAt(selectedRow, 5));
        float currentMileage = (float) tableModel.getValueAt(selectedRow, 6);
        float currentRentalPrice = (float) tableModel.getValueAt(selectedRow, 7);
        int currentCategoryId = (int) tableModel.getValueAt(selectedRow, 8);

        JTextField brandField = new JTextField(currentBrand);
        JTextField modelField = new JTextField(currentModel);
        JTextField registrationField = new JTextField(currentRegistration);
        JTextField imageURLField = new JTextField(currentImageURL);
        JTextField mileageField = new JTextField(String.valueOf(currentMileage));
        JTextField rentalPriceField = new JTextField(String.valueOf(currentRentalPrice));
        JTextField categoryIdField = new JTextField(String.valueOf(currentCategoryId));
        JCheckBox availabilityCheckBox = new JCheckBox("Available", currentAvailability);

        Object[] fields = {
                "Brand:", brandField,
                "Model:", modelField,
                "Registration:", registrationField,
                "Image URL:", imageURLField,
                "Mileage:", mileageField,
                "Rental Price:", rentalPriceField,
                "Category ID:", categoryIdField,
                "Available:", availabilityCheckBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Update Car", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                boolean success = carController.updateCar(
                        carId,
                        Integer.parseInt(mileageField.getText()),
                        Integer.parseInt(rentalPriceField.getText()),
                        brandField.getText(),
                        modelField.getText(),
                        registrationField.getText(),
                        imageURLField.getText(),
                        availabilityCheckBox.isSelected(),
                        Float.parseFloat(mileageField.getText()),
                        Float.parseFloat(rentalPriceField.getText()),
                        Integer.parseInt(categoryIdField.getText())
                );
                if (success) {
                    JOptionPane.showMessageDialog(this, "Car updated successfully!");
                    loadCars();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update car.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteSelectedCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car to delete.");
            return;
        }
        int carId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this car?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = carController.deleteCar(carId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Car deleted successfully!");
                loadCars();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete car.");
            }
        }
    }

    private void navigateBack() {
        mainFrame.setSize(600, 400);
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new AdminDashboard(mainFrame)); // Update to your dashboard view
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

