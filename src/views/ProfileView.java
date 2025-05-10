package views;

import dao.CustomerDAO;
import models.Customer;
import models.RentalHistory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import car_rental.Main;

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
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField(customer.getPhone());
        formPanel.add(phoneField);

        formPanel.add(new JLabel("License Number:"));
        licenseField = new JTextField(customer.getLicenseNumber());
        formPanel.add(licenseField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveProfile());
        formPanel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);

        // Rental history table
        historyTable = new JTable();
        loadRentalHistory();

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rental History"));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void saveProfile() {
        String newPhone = phoneField.getText();
        String newLicense = licenseField.getText();

        customer.setPhone(newPhone);
        customer.setLicenseNumber(newLicense);

        boolean success = customerDAO.updateProfile(customer);
        if (success) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile.");
        }
    }

    private void loadRentalHistory() {
        List<RentalHistory> history = customerDAO.getRentalHistory(customer.getUserId());

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Rental ID", "Return Date", "Comments"}, 0);
        for (RentalHistory rh : history) {
            model.addRow(new Object[]{
                rh.getRentalId(),
                rh.getActualReturnDate(),
                rh.getComments()
            });
        }

        historyTable.setModel(model);
    }

    private void goBack() {
        // Navigate back to the previous page using mainFrame
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new AppView(mainFrame, customer)); // Return to AppView
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}