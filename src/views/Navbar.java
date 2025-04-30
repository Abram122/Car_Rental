package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Navbar extends JMenuBar {
    private Consumer<Locale> onLocaleChange;

    public Navbar(Consumer<Locale> onLocaleChange) {
        this.onLocaleChange = onLocaleChange;

        JMenu languageMenu = new JMenu("Language");

        JMenuItem arabicItem = new JMenuItem("عربي");
        arabicItem.addActionListener((ActionEvent e) -> onLocaleChange.accept(new Locale("ar")));

        JMenuItem englishItem = new JMenuItem("English");
        englishItem.addActionListener((ActionEvent e) -> onLocaleChange.accept(new Locale("en")));

        languageMenu.add(arabicItem);
        languageMenu.add(englishItem);

        add(languageMenu);
    }
}
