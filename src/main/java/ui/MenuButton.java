package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MenuButton extends JButton {
    private boolean isSelected;
    private String content;

    public MenuButton(String text) {
        content = text;
        isSelected = false;
        setForeground(Color.GRAY);
        setText(content);
        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(24f);
            setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setHorizontalAlignment(SwingConstants.LEFT);
        setOpaque(false);
    }

    public void setSelected() {
        setForeground(Color.BLACK);
        isSelected = true;
    }

    public void setDeselected() {
        setForeground(Color.GRAY);
        isSelected = false;
    }

    public boolean getSelected() {
        return isSelected;
    }
}
