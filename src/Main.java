import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends JFrame {
    private JLabel label;
    private ResourceBundle bundle;

    public Main() {
        setLocale(new Locale("ar")); 
        initUI();
    }

    private void initUI() {
        setTitle("Simple Translator");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel(getText("hello"));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnArabic = new JButton("عربي");
        JButton btnEnglish = new JButton("English");

        btnArabic.addActionListener((ActionEvent e) -> {
            setLocale(new Locale("ar"));
            label.setText(getText("hello"));
        });

        btnEnglish.addActionListener((ActionEvent e) -> {
            setLocale(new Locale("en"));
            label.setText(getText("hello"));
        });

        JPanel buttons = new JPanel();
        buttons.add(btnArabic);
        buttons.add(btnEnglish);

        add(label, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("i18n.messages", locale);
    }

    private String getText(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());
        }
        return bundle.getString(key);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
