package views;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

public class MainView extends JPanel {
    private ResourceBundle bundle;
    private Locale currentLocale;
    private List<JComponent> translatableComponents = new ArrayList<>();

    private JLabel label;
    private JButton button;
    private JTextField textField;
    private boolean isRTL = false;

    private Timer animationTimer;
    private int offset = 0;
    private final int maxOffset = 100;
    private final int animationDelay = 10;

    public MainView(Locale initialLocale) {
        this.currentLocale = initialLocale;
        loadBundle();
        setLayout(null); // Important for manual movement
        setupUI();
    }

    private void setupUI() {
        label = new JLabel(getText("label"), SwingConstants.CENTER);
        button = new JButton(getText("button"));
        textField = new JTextField(getText("textfield"));

        label.setFont(new Font("Arial", Font.BOLD, 18));
        textField.setHorizontalAlignment(JTextField.CENTER);

        label.setBounds(150, 30, 100, 30);
        textField.setBounds(150, 80, 100, 30);
        button.setBounds(150, 130, 100, 30);

        add(label);
        add(textField);
        add(button);

        translatableComponents.add(label);
        translatableComponents.add(button);
        translatableComponents.add(textField);

        updateComponentOrientation();
    }

    private void loadBundle() {
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
    }

    public void updateLocale(Locale locale) {
        this.currentLocale = locale;
        loadBundle();
        updateTexts();
        animateSwitchDirection(locale.getLanguage().equals("ar"));
    }

    private void updateTexts() {
        label.setText(getText("label"));
        button.setText(getText("button"));
        textField.setText(getText("textfield"));
    }

    private void animateSwitchDirection(boolean toRTL) {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        offset = 0;
        isRTL = toRTL;

        animationTimer = new Timer(animationDelay, e -> {
            if (offset >= maxOffset) {
                ((Timer) e.getSource()).stop();
                applyFinalOrientation();
                return;
            }
            offset += 5;
            animateMove();
        });
        animationTimer.start();
    }

    private void animateMove() {
        int baseX = isRTL ? 250 - offset : 50 + offset;

        label.setLocation(baseX, 30);
        textField.setLocation(baseX, 80);
        button.setLocation(baseX, 130);
        repaint();
    }

    private void applyFinalOrientation() {
        int finalX = isRTL ? 250 : 50;

        label.setLocation(finalX, 30);
        textField.setLocation(finalX, 80);
        button.setLocation(finalX, 130);

        if (isRTL) {
            applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        } else {
            applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void updateComponentOrientation() {
        if (currentLocale.getLanguage().equals("ar")) {
            applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        } else {
            applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
    }

    private String getText(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());
        }
        return bundle.getString(key);
    }
}
