package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sound.SoundManager;

public class CreateSelectScreen extends JPanel implements KeyEventObserver {
    private int selectedIndex = 0;
    private final MenuButton[] menuButtons = new MenuButton[5];

    public CreateSelectScreen() {
        setLayout(null);

        ImageIcon image = new ImageIcon(getClass().getResource("/assets/container2.png"));
        JLabel label = new JLabel(image);
        add(label);
        label.setBounds(10, 10, image.getIconWidth(), image.getIconHeight());

        MenuButton createMonster = new MenuButton("MONSTER");
        MenuButton createMove = new MenuButton("MOVE");
        MenuButton createTeam = new MenuButton("TEAM");
        MenuButton createItem = new MenuButton("ITEM");
        MenuButton deleteMonster = new MenuButton("DELETE MONSTER");
        menuButtons[0] = createMonster;
        menuButtons[1] = createMove;
        menuButtons[2] = createTeam;
        menuButtons[3] = createItem;
        menuButtons[4] = deleteMonster;
        createMonster.setSelected();
        int offset = 30;
        for (MenuButton button : menuButtons) {
            add(button);
            setComponentZOrder(button, 0);
            button.setBounds(20, offset, 400, 40);
            offset += 60;
        }

        setPreferredSize(new Dimension(512, 512));
        setDoubleBuffered(true);
        setBackground(Color.WHITE);
    }

    public MenuButton getSelectedButton() {
        for (MenuButton menuButton : menuButtons) {
            if (menuButton.getSelected()) return menuButton;
        }
        return null;
    }

    @Override
    public void onKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            selectedIndex += 1;
            selectedIndex = (selectedIndex > 4) ? 0 : selectedIndex;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            selectedIndex -= 1;
            selectedIndex = (selectedIndex < 0) ? 4 : selectedIndex;
        }
        menuButtons[selectedIndex].setSelected();
        for (int i = 0; i <= 4; i++) {
            if (i != selectedIndex) {
                menuButtons[i].setDeselected();
            }
        }
        SoundManager.playSound("Select.wav");
        menuButtons[selectedIndex].setSelected();
        for (int i = 0; i <= 4; i++) {
            if (i != selectedIndex) {
                menuButtons[i].setDeselected();
            }
        }
    }
}
