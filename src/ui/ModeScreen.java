package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sound.SoundManager;

public class ModeScreen extends JPanel implements KeyEventObserver {
    private final MenuButton[] menuButtons = new MenuButton[2];

    public ModeScreen() {
        setLayout(null);

        ImageIcon image = new ImageIcon(getClass().getResource("/assets/container.png"));
        JLabel label = new JLabel(image);
        add(label);
        label.setBounds(10, 10, image.getIconWidth(), image.getIconHeight());

        MenuButton create = new MenuButton("CREATE");
        MenuButton battle = new MenuButton("BATTLE");
        menuButtons[0] = create;
        menuButtons[1] = battle;
        create.setSelected();
        int offset = 50;
        for (MenuButton button : menuButtons) {
            add(button);
            setComponentZOrder(button, 0);
            button.setBounds(20, offset, 200, 40);
            offset += 60;
        }

        setPreferredSize(new Dimension(512, 512));
        setDoubleBuffered(true);
        setBackground(Color.WHITE);
    }

    public MenuButton getSelectedButton() {
        MenuButton selectedButton = (menuButtons[0].getSelected()) ? menuButtons[0] : menuButtons[1];
        return selectedButton;
    } 

    @Override
    public void onKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
            SoundManager.playSound("Select.wav");
            MenuButton selectedButton = (menuButtons[0].getSelected()) ? menuButtons[0] : menuButtons[1];
            MenuButton deselectedButton = (!menuButtons[0].getSelected()) ? menuButtons[0] : menuButtons[1];
            selectedButton.setDeselected();
            deselectedButton.setSelected();
        }
    }
}
