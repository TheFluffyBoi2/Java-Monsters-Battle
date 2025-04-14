package ui;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sound.SoundManager;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class TitleScreen extends JPanel implements KeyEventObserver {
    public TitleScreen() {
        SoundManager.loopSong("MainTheme.wav");

        ImageIcon titleScreen = new ImageIcon(getClass().getResource("/assets/title_screen.gif"));
        JLabel label = new JLabel();
        label.setIcon(titleScreen);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        setPreferredSize(new Dimension(512, 512));
        add(label);
        setDoubleBuffered(true);
    }

    @Override
    public void onKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            SoundManager.stopSound();
        }
    }
}
