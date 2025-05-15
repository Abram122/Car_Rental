package views;

import controllers.PaymentController;
import models.Payment;
import utils.AppColors;

import car_rental.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPaymentsView extends JPanel {
    private final PaymentController paymentController;
    private final Main mainFrame;
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public AdminPaymentsView(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.paymentController = new PaymentController();

        setLayout(new BorderLayout(10, 10));
        setBackground(AppColors.MAIN_BG);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        loadPayments(null);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColors.ACCENT_TIFFANY);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("All Payments", SwingConstants.CENTER);
        titleLabel.setForeground(AppColors.LIGHT_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(AppColors.ACCENT_TIFFANY);

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search by User Name");
        styleButton(searchButton);
        searchButton.addActionListener(_ -> loadPayments(searchField.getText().trim()));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppColors.MAIN_BG);

        tableModel = new DefaultTableModel(new Object[] {
                "Payment ID", "Booking ID", "User Name", "Amount", "Status", "Method", "Date"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        paymentTable = new JTable(tableModel);
        paymentTable.setFillsViewportHeight(true);
        paymentTable.setRowHeight(30);
        paymentTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Payments"));
        scrollPane.setPreferredSize(new Dimension(800, 400));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(AppColors.ACCENT_TIFFANY);

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

    private void loadPayments(String userName) {
        tableModel.setRowCount(0);
        List<Payment> payments = paymentController.getPaymentsByUserName(userName);
        if (payments == null || payments.isEmpty()) {
            tableModel.addRow(new Object[] { "No payments found", "", "", "", "", "", "" });
            paymentTable.setEnabled(false);
        } else {
            for (Payment payment : payments) {
                tableModel.addRow(new Object[] {
                        payment.getPaymentId(),
                        payment.getBookingId(),
                        payment.getUserName() != null ? payment.getUserName() : "",
                        payment.getAmount(),
                        payment.getPaymentStatus(),
                        payment.getPaymentMethod(),
                        payment.getPaymentDate()
                });
            }
            paymentTable.setEnabled(true);
        }
    }

    private void navigateBack() {
        mainFrame.setSize(600, 400);
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new AdminDashboard(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}