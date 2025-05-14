package views;

import car_rental.Main;
import controllers.RegisterController;
import models.Customer;
import utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ManageUsersView extends JPanel {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private RegisterController controller;

    public ManageUsersView(Main mainFrame) {
        controller = new RegisterController();
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_BG);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.ACCENT_TIFFANY);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Users table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppColors.MAIN_BG);
        tablePanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        // Create table model with column names
        String[] columnNames = {"ID", "Username", "Email", "Phone", "License Number", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Actions column is editable
            }
        };

        usersTable = new JTable(tableModel);
        usersTable.setBackground(AppColors.MAIN_BG);
        usersTable.setForeground(AppColors.LIGHT_TEXT);
        usersTable.setSelectionBackground(AppColors.ACCENT_PURPLE);
        usersTable.setSelectionForeground(AppColors.LIGHT_TEXT);
        usersTable.setGridColor(AppColors.DIVIDER_DARK_GRAY);
        usersTable.getTableHeader().setBackground(AppColors.ACCENT_TIFFANY);
        usersTable.getTableHeader().setForeground(AppColors.LIGHT_TEXT);
        usersTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.ACCENT_TIFFANY));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Load users
        loadUsers();

        // Footer with back button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(AppColors.MAIN_BG);

        JButton backButton = new JButton("Back to Dashboard");
        styleButton(backButton, AppColors.ACCENT_TIFFANY);
        backButton.addActionListener(e -> {
            mainFrame.getContentPane().removeAll();
            mainFrame.add(new AdminDashboard(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        footerPanel.add(backButton);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void loadUsers() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Get all customers
        List<Customer> customers = controller.getAllCustomers();

        // Populate table with customer data
        for (Customer customer : customers) {
            Object[] rowData = {
                    customer.getUserId(),
                    customer.getUsername(),
                    "Email", // Placeholder - replace with actual email if available
                    customer.getPhone(),
                    customer.getLicenseNumber(),
                    "Delete"
            };
            tableModel.addRow(rowData);
        }

        // Add action button to each row
        usersTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        usersTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(AppColors.LIGHT_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(AppColors.ACCENT_PURPLE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    // Button renderer for the table
    private class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(AppColors.ACCENT_TIFFANY);
            setForeground(AppColors.LIGHT_TEXT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Delete" : value.toString());
            return this;
        }
    }

    // Button editor for the table
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(AppColors.ACCENT_TIFFANY);
            button.setForeground(AppColors.LIGHT_TEXT);
            button.addActionListener(e -> {
                fireEditingStopped();
                clicked = true;
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Delete" : value.toString();
            button.setText(label);
            clicked = false;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int selectedRow = usersTable.getSelectedRow();
                int customerId = (Integer) usersTable.getValueAt(selectedRow, 0);
                String customerEmail = (String) usersTable.getValueAt(selectedRow, 2);
                
                int confirmResult = JOptionPane.showConfirmDialog(
                        button, 
                        "Are you sure you want to delete this user?", 
                        "Confirm Deletion", 
                        JOptionPane.YES_NO_OPTION
                );
                
                if (confirmResult == JOptionPane.YES_OPTION) {
                    boolean success = controller.delete(customerEmail);
                    if (success) {
                        JOptionPane.showMessageDialog(button, "User deleted successfully!");
                        tableModel.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(button, "Failed to delete user!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}
