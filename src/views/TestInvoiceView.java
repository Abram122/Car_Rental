package views;

import javax.swing.*;

public class TestInvoiceView {
    public static void main(String[] args) {
        InvoiceView invoice = new InvoiceView(
            "INV-20240515", "Ahmed Ali", "Toyota Corolla", "2025-05-15 to 2025-05-18",
            1200.0, 100.0, 1100.0, "2025-05-15"
        );
        JFrame frame = new JFrame("Invoice Preview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(invoice);
        frame.pack();
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}