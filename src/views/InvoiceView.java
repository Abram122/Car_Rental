package views;

import utils.AppColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class InvoiceView extends JPanel {

    private String invoiceNumber, customerName, carModel, rentalPeriod, date;
    private double subtotal, discount, total;

    public InvoiceView(String invoiceNumber, String customerName, String carModel, String rentalPeriod,
                       double subtotal, double discount, double total, String date) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.carModel = carModel;
        this.rentalPeriod = rentalPeriod;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.date = date;

        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_BG);

        // Header
        JLabel title = new JLabel("Car Rental Receipt", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(AppColors.ACCENT_TIFFANY);
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(AppColors.MAIN_BG);
        content.setBorder(new EmptyBorder(20, 40, 20, 40));

        content.add(invoiceRow("Invoice #:", invoiceNumber));
        content.add(invoiceRow("Date:", date));
        content.add(invoiceRow("Customer:", customerName));
        content.add(invoiceRow("Car Model:", carModel));
        content.add(invoiceRow("Rental Period:", rentalPeriod));
        content.add(Box.createVerticalStrut(10));
        content.add(divider());
        content.add(invoiceRow("Subtotal:", String.format("%.2f EGP", subtotal)));
        content.add(invoiceRow("Discount:", String.format("-%.2f EGP", discount)));
        content.add(divider());
        content.add(invoiceRowBold("Total:", String.format("%.2f EGP", total)));

        add(content, BorderLayout.CENTER);

        // Footer
        JLabel thanks = new JLabel("Thank you for choosing us!", SwingConstants.CENTER);
        thanks.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        thanks.setForeground(AppColors.ACCENT_PURPLE);
        thanks.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(thanks, BorderLayout.SOUTH);
    }

    // Optional: for compatibility, but not used
    public InvoiceView(car_rental.Main main) {}

    private JPanel invoiceRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(AppColors.MAIN_BG);
        JLabel l = new JLabel(label);
        l.setForeground(AppColors.LIGHT_TEXT);
        l.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel v = new JLabel(value);
        v.setForeground(AppColors.LIGHT_TEXT);
        v.setFont(new Font("Arial", Font.PLAIN, 16));
        row.add(l, BorderLayout.WEST);
        row.add(v, BorderLayout.EAST);
        row.setBorder(new EmptyBorder(4, 0, 4, 0));
        return row;
    }

    private JPanel invoiceRowBold(String label, String value) {
        JPanel row = invoiceRow(label, value);
        for (Component c : row.getComponents()) {
            c.setFont(new Font("Arial", Font.BOLD, 18));
            c.setForeground(AppColors.ACCENT_TIFFANY);
        }
        return row;
    }

    private JSeparator divider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(AppColors.DIVIDER_DARK_GRAY);
        sep.setBackground(AppColors.DIVIDER_DARK_GRAY);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        return sep;
    }

    // PDF Export using Apache PDFBox and basic Times fonts
    public void exportToPDF(String filePath) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float y = yStart;
            float leading = 22;

            // Title
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("Car Rental Receipt");
            contentStream.endText();

            y -= leading * 2;

            // Info
            y = writeLine(contentStream, "Invoice #: " + invoiceNumber, margin, y, leading, PDType1Font.TIMES_ROMAN, 14);
            y = writeLine(contentStream, "Date: " + date, margin, y, leading, PDType1Font.TIMES_ROMAN, 14);
            y = writeLine(contentStream, "Customer: " + customerName, margin, y, leading, PDType1Font.TIMES_ROMAN, 14);
            y = writeLine(contentStream, "Car Model: " + carModel, margin, y, leading, PDType1Font.TIMES_ROMAN, 14);
            y = writeLine(contentStream, "Rental Period: " + rentalPeriod, margin, y, leading, PDType1Font.TIMES_ROMAN, 14);

            y -= leading * 2;

            y = writeLine(contentStream, "Subtotal: " + String.format("%.2f EGP", subtotal), margin, y, leading, PDType1Font.TIMES_ROMAN, 14);
            y = writeLine(contentStream, "Discount: -" + String.format("%.2f EGP", discount), margin, y, leading, PDType1Font.TIMES_ROMAN, 14);
            y = writeLine(contentStream, "Total: " + String.format("%.2f EGP", total), margin, y, leading, PDType1Font.TIMES_BOLD, 16);

            y -= leading * 2;

            // Footer
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 14);
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("Thank you for choosing us!");
            contentStream.endText();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to export PDF: " + e.getMessage());
        }

        try {
            document.save(filePath);
            document.close();
            JOptionPane.showMessageDialog(this, "PDF exported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save PDF: " + e.getMessage());
        }
    }

    // Helper for writing lines in PDF
    private float writeLine(PDPageContentStream cs, String text, float x, float y, float leading, PDType1Font font, int fontSize) throws IOException {
        cs.beginText();
        cs.setFont(font, fontSize);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
        return y - leading;
    }
}