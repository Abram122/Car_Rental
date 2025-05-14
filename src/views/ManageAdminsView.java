package views;

import car_rental.Main;
import controllers.LoginController;
import controllers.RegisterController;
import models.Admin;
import utils.AppColors;
import utils.ValidationException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ManageAdminsView extends JPanel {
    private JTable adminsTable;
    private DefaultTableModel tableModel;
    private RegisterController controller;
    private String currentAdminEmail; // To track the currently logged-in admin

    public ManageAdminsView(Main mainFrame) {
        this(mainFrame, null);
    }

    public ManageAdminsView(Main mainFrame, String adminEmail) {
        this.currentAdminEmail = adminEmail;
        controller = new RegisterController();
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_BG);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.ACCENT_TIFFANY);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Admin Management");
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(AppColors.MAIN_BG);
        contentPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        // Form for adding new admins (only shown for super admins)
        if (isCurrentAdminSuperAdmin()) {
            JPanel formPanel = createNewAdminForm();
            contentPanel.add(formPanel, BorderLayout.NORTH);
        }

        // Admins table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppColors.MAIN_BG);        // Create table model with column names
        String[] columnNames = {"ID", "Username", "Email", "View Password", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Password and Actions columns are editable
            }
        };

        adminsTable = new JTable(tableModel);
        adminsTable.setBackground(AppColors.MAIN_BG);
        adminsTable.setForeground(AppColors.LIGHT_TEXT);
        adminsTable.setSelectionBackground(AppColors.ACCENT_PURPLE);
        adminsTable.setSelectionForeground(AppColors.LIGHT_TEXT);
        adminsTable.setGridColor(AppColors.DIVIDER_DARK_GRAY);
        adminsTable.getTableHeader().setBackground(AppColors.ACCENT_TIFFANY);
        adminsTable.getTableHeader().setForeground(AppColors.LIGHT_TEXT);
        adminsTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(adminsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.ACCENT_TIFFANY));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Load admins
        loadAdmins();

        // Footer with back button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(AppColors.MAIN_BG);

        JButton backButton = new JButton("Back to Dashboard");
        styleButton(backButton, AppColors.ACCENT_TIFFANY);        backButton.addActionListener(e -> {
            mainFrame.getContentPane().removeAll();
            mainFrame.add(new AdminDashboard(mainFrame, currentAdminEmail));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        footerPanel.add(backButton);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private boolean isCurrentAdminSuperAdmin() {
        if (currentAdminEmail != null) {
            return controller.isSuperAdmin(currentAdminEmail);
        }
        // For testing - assume super admin if email not provided
        return true;
    }    private JPanel createNewAdminForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(AppColors.MAIN_BG);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(AppColors.ACCENT_TIFFANY),
                "Add New Admin",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                AppColors.LIGHT_TEXT
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(AppColors.LIGHT_TEXT);
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField usernameField = new JTextField(20);
        styleTextField(usernameField);
        formPanel.add(usernameField, gbc);
        
        // Email field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(AppColors.LIGHT_TEXT);
        formPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField emailField = new JTextField(20);
        styleTextField(emailField);
        formPanel.add(emailField, gbc);
        
        // Password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(AppColors.LIGHT_TEXT);
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPasswordField passwordField = new JPasswordField(20);
        styleTextField(passwordField);
        formPanel.add(passwordField, gbc);
        
        // Checkbox to store original password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JCheckBox storePasswordCheckBox = new JCheckBox("Store original password (for testing only)");
        storePasswordCheckBox.setBackground(AppColors.MAIN_BG);
        storePasswordCheckBox.setForeground(AppColors.LIGHT_TEXT);
        formPanel.add(storePasswordCheckBox, gbc);
        
        // Add button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Add Admin");
        styleButton(addButton, AppColors.ACCENT_TIFFANY);
        addButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String role = "ADMIN"; // Default role as we're not using role column
                boolean storePassword = storePasswordCheckBox.isSelected();
                
                boolean success = controller.registerAdmin(username, password, email, role, storePassword);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Admin added successfully!");
                    
                    // Clear form fields
                    usernameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    storePasswordCheckBox.setSelected(false);
                    
                    // Reload the admins table
                    loadAdmins();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add admin!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        formPanel.add(addButton, gbc);
        
        return formPanel;
    }

    private void styleTextField(JTextField textField) {
        textField.setBackground(AppColors.DIVIDER_DARK_GRAY);
        textField.setForeground(AppColors.LIGHT_TEXT);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }    private void loadAdmins() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Get all admins
        List<Admin> admins = controller.getAllAdmins();
        
        // Populate table with admin data
        for (Admin admin : admins) {
            Object[] rowData = {
                    admin.getUserId(),
                    admin.getUsername(),
                    admin.getEmail(), // Use actual email from admin object
                    "View",
                    "Delete"
            };
            tableModel.addRow(rowData);
        }

        // Add action buttons to each row (only for super admins)
        if (isCurrentAdminSuperAdmin()) {
            adminsTable.getColumn("View Password").setCellRenderer(new ButtonRenderer("View"));
            adminsTable.getColumn("View Password").setCellEditor(new PasswordButtonEditor(new JCheckBox()));
            
            adminsTable.getColumn("Actions").setCellRenderer(new ButtonRenderer("Delete"));
            adminsTable.getColumn("Actions").setCellEditor(new DeleteButtonEditor(new JCheckBox()));
        }
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
    }    // Button renderer for the table
    private class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer(String text) {
            setOpaque(true);
            setText(text);
            setBackground(AppColors.ACCENT_TIFFANY);
            setForeground(AppColors.LIGHT_TEXT);
        }

        public ButtonRenderer() {
            this("Delete"); // Default text
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setText(value.toString());
            }
            return this;
        }
    }    // Button editor for the table
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
                int selectedRow = adminsTable.getSelectedRow();
                int adminId = (Integer) adminsTable.getValueAt(selectedRow, 0);
                
                int confirmResult = JOptionPane.showConfirmDialog(
                        button, 
                        "Are you sure you want to delete this admin?", 
                        "Confirm Deletion", 
                        JOptionPane.YES_NO_OPTION
                );
                
                if (confirmResult == JOptionPane.YES_OPTION) {
                    boolean success = controller.deleteAdmin(adminId);
                    if (success) {
                        JOptionPane.showMessageDialog(button, "Admin deleted successfully!");
                        tableModel.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(button, "Failed to delete admin!", "Error", JOptionPane.ERROR_MESSAGE);
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

    // Button editor for password view
    private class PasswordButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean clicked;

        public PasswordButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("View");
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
            clicked = false;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int selectedRow = adminsTable.getSelectedRow();
                String adminEmail = (String) adminsTable.getValueAt(selectedRow, 2);
                
                String password = controller.getAdminPassword(adminEmail);
                if (password != null) {
                    JOptionPane.showMessageDialog(button, 
                        "Admin Password: " + password, 
                        "Password Information", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(button, 
                        "Password not stored in plaintext for this admin.", 
                        "Password Information", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            return "View";
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
      // Button editor for delete action
    private class DeleteButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean clicked;

        public DeleteButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Delete");
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
            clicked = false;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int selectedRow = adminsTable.getSelectedRow();
                int adminId = (Integer) adminsTable.getValueAt(selectedRow, 0);
                
                int confirmResult = JOptionPane.showConfirmDialog(
                        button, 
                        "Are you sure you want to delete this admin?", 
                        "Confirm Deletion", 
                        JOptionPane.YES_NO_OPTION
                );
                
                if (confirmResult == JOptionPane.YES_OPTION) {
                    boolean success = controller.deleteAdmin(adminId);
                    if (success) {
                        JOptionPane.showMessageDialog(button, "Admin deleted successfully!");
                        tableModel.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(button, "Failed to delete admin!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            return "Delete";
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}
