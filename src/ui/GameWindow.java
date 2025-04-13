package ui;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.GameState;
import input.InputHandler;

public class GameWindow extends JFrame implements KeyEventObserver {
    private GameState gameState;
    private JPanel cards;
    private CardLayout cardLayout;

    public GameWindow(InputHandler inputHandler) {
        gameState = GameState.TITLE_SCREEN;
        setTitle("Monsters Battle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        TitleScreen titleScreen = new TitleScreen();
        ModeSelect modeSelect = new ModeSelect();
        
        cards.add(titleScreen, "TITLE_SCREEN");
        cards.add(modeSelect, "MODE_SELECT");

        inputHandler.addObserver(titleScreen);
        inputHandler.addObserver(this);

        setContentPane(cards);
        pack();
        addKeyListener(inputHandler);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void onKeyEvent(KeyEvent e) {
        switch (gameState) {
            case TITLE_SCREEN: {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Exit title screen");
                    gameState = GameState.MODE_SELECT;
                    cardLayout.show(cards, "MODE_SELECT");
                }
                break;
            }    
            default:
                break;
        }
    }
}
