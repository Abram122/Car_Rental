package views;

import dao.CustomerDAO;
import models.Booking;
import models.Customer;
import models.RentalHistory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import car_rental.Main;
import controllers.ProfileController;

import java.awt.*;
import java.util.List;

public class ProfileView extends JPanel {
    private final Customer customer;
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final Main mainFrame;

    private JTextField phoneField;
    private JTextField licenseField;
    private JTable historyTable;

    public ProfileView(Main mainFrame, Customer customer) {
        this.mainFrame = mainFrame; // Store the main frame reference
        this.customer = customer; // Store the customer object
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        // Title
        JLabel titleLabel = new JLabel("Your Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Center panel for profile form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Personal details section
        JPanel personalPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        personalPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));
        
        personalPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField(customer.getUsername());
        usernameField.setEditable(false);
        personalPanel.add(usernameField);
        
        personalPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField(customer.getPhone());
        personalPanel.add(phoneField);

        personalPanel.add(new JLabel("License Number:"));
        licenseField = new JTextField(customer.getLicenseNumber());
        personalPanel.add(licenseField);
        
        formPanel.add(personalPanel);
        
        // Add some spacing
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
          JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                saveProfile();
            }
        });
        buttonPanel.add(saveButton);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                refreshData();
            }
        });
        buttonPanel.add(refreshButton);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                goBack();
            }
        });
        buttonPanel.add(backButton);
        
        formPanel.add(buttonPanel);

        add(formPanel, BorderLayout.CENTER);

        // Create a panel for tables (rental history and bookings)
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Rental history table
        historyTable = new JTable();
        loadRentalHistory();
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setBorder(BorderFactory.createTitledBorder("Rental History"));
        historyScrollPane.setPreferredSize(new Dimension(550, 150));
        tablesPanel.add(historyScrollPane);
        
        // Add some spacing between tables
        tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Bookings table
        JPanel bookingsPanel = createBookingsPanel();
        bookingsPanel.setPreferredSize(new Dimension(550, 150));
        tablesPanel.add(bookingsPanel);
        
        add(tablesPanel, BorderLayout.SOUTH);
        
        // Make main frame larger to accommodate all content
        mainFrame.setSize(650, 700);
    }
      private void saveProfile() {
        String newPhone = phoneField.getText();
        String newLicense = licenseField.getText();

        try {
            // Validate the input fields
            utils.ValidationUtil.isValidEgyptianPhone(newPhone);
            utils.ValidationUtil.isValidLicenseNumber(newLicense);
            
            // If validation passes, update the customer object
            customer.setPhone(newPhone);
            customer.setLicenseNumber(newLicense);

            boolean success = customerDAO.updateProfile(customer);
            if (success) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (utils.ValidationException e) {
            // Show the validation error to the user
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }private void loadRentalHistory() {
        List<RentalHistory> history = customerDAO.getRentalHistory(customer.getUserId());

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Rental ID", "Return Date", "Comments"}, 0);
        
        if (history == null || history.isEmpty()) {
            // Show a message in the table when there's no history
            model.addRow(new Object[]{"No rental history found", "", ""});
            historyTable.setEnabled(false);
        } else {
            for (RentalHistory rh : history) {
                model.addRow(new Object[]{
                    rh.getRentalId(),
                    rh.getActualReturnDate(),
                    rh.getComments()
                });
            }
            historyTable.setEnabled(true);
        }

        historyTable.setModel(model);
    }private void goBack() {
        // Make the window back to original size
        mainFrame.setSize(600, 400);
        
        // Navigate back to the previous page using mainFrame
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new AppView(mainFrame, customer)); // Return to AppView
        mainFrame.revalidate();
        mainFrame.repaint();
    }    private void refreshData() {
        try {
            // Show loading message
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            // Reload customer data
            Customer refreshedCustomer = new ProfileController().load(customer.getUsername());
            if (refreshedCustomer != null) {
                // Update the form fields
                phoneField.setText(refreshedCustomer.getPhone());
                licenseField.setText(refreshedCustomer.getLicenseNumber());
                
                // Reload tables
                loadRentalHistory();
                
                // Refresh bookings panel
                JPanel bookingsPanel = createBookingsPanel();
                bookingsPanel.setPreferredSize(new Dimension(550, 150));
                
                // Find and replace the old bookings panel
                Component[] components = ((JPanel)getComponent(2)).getComponents();
                if (components.length >= 3) {
                    ((JPanel)getComponent(2)).remove(2);
                    ((JPanel)getComponent(2)).add(bookingsPanel);
                }
                
                // Repaint the UI
                revalidate();
                repaint();
                
                JOptionPane.showMessageDialog(this, "Data refreshed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to refresh data - user not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // Handle any unexpected errors
            JOptionPane.showMessageDialog(this, "Error refreshing data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Reset cursor back to normal
            setCursor(Cursor.getDefaultCursor());
        }
    }private JPanel createBookingsPanel() {
        String[] cols = { "Booking ID", "Start Date", "End Date", "Status" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        List<Booking> bookings = new ProfileController().loadBookings(customer.getUserId());
        
        if (bookings == null || bookings.isEmpty()) {
            // Show a message in the table when there are no bookings
            model.addRow(new Object[]{"No bookings found", "", "", ""});
            JTable table = new JTable(model);
            table.setEnabled(false);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JScrollPane(table), BorderLayout.CENTER);
            panel.setBorder(BorderFactory.createTitledBorder("Your Bookings"));
            return panel;
        }
        
        // If we have bookings, display them
        for (Booking b : bookings) {
            model.addRow(new Object[]{
                b.getBookingId(),
                b.getStartDate(),
                b.getEndDate(),
                b.getStatus()
            });
        }

        JTable table = new JTable(model);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createTitledBorder("Your Bookings"));
        return panel;
    }
}