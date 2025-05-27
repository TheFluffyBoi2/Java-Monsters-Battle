package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameEndScreen extends JPanel {
    public GameEndScreen(String message) {
        setLayout(null);
        setBackground(Color.BLACK);
        JLabel msg = new JLabel(message);
        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(24f);
            msg.setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        msg.setForeground(Color.WHITE);
        msg.setBounds(106, 231, 300, 50);
        msg.setHorizontalAlignment(JLabel.CENTER);
        add(msg);
    }
}
