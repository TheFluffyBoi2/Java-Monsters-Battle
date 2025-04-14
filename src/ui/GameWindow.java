package ui;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.nio.channels.SelectableChannel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import game.GameState;
import input.InputHandler;
import sound.SoundManager;

public class GameWindow extends JFrame implements KeyEventObserver {
    private final TitleScreen titleScreen;
    private final ModeScreen modeSelect;
    private final CreateSelectScreen createSelect;
    private final CreateMonsterScreen createMonster;
    private final InputHandler inputHandler;
    private GameState gameState;
    private JPanel cards;
    private CardLayout cardLayout;

    public GameWindow(InputHandler inputHandler) {
        this.inputHandler = inputHandler;

        gameState = GameState.TITLE_SCREEN;
        setTitle("Monsters Battle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        titleScreen = new TitleScreen();
        modeSelect = new ModeScreen();
        createSelect = new CreateSelectScreen();
        createMonster = new CreateMonsterScreen();
        
        cards.add(titleScreen, "TITLE_SCREEN");
        cards.add(modeSelect, "MODE_SELECT");
        cards.add(createSelect, "CREATE_SELECT");
        cards.add(createMonster, "CREATE_MONSTER");

        this.inputHandler.addObserver(titleScreen);
        this.inputHandler.addObserver(this);

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
                    SoundManager.playSound("Start.wav");
                    gameState = GameState.MODE_SELECT;
                    cardLayout.show(cards, "MODE_SELECT");
                    pack();
                    inputHandler.addObserver(modeSelect);
                    inputHandler.removeObserver(titleScreen);
                    requestFocusInWindow();
                }
                break;
            }

            case MODE_SELECT: {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    MenuButton selectedButton = modeSelect.getSelectedButton();
                    if (selectedButton.getText().equals("CREATE")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.CREATE_SELECT;
                        cardLayout.show(cards, "CREATE_SELECT");
                        pack();
                        inputHandler.addObserver(createSelect);
                        inputHandler.removeObserver(modeSelect);
                        requestFocusInWindow();
                    }
                    if (selectedButton.getText().equals("BATTLE")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.BATTLE;
                        System.out.println("BATTLE SCREEN");
                    }
                }
                break;
            }

            case CREATE_SELECT: {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    MenuButton createButton = createSelect.getSelectedButton();
                    if (createButton.getText().equals("MONSTER")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.CREATE_MONSTER;
                        cardLayout.show(cards, "CREATE_MONSTER");
                        pack();
                        inputHandler.removeObserver(createSelect);
                        requestFocusInWindow();
                    }
                    if (createButton.getText().equals("TEAM")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.CREATE_TEAM;
                        System.out.println("CREATE TEAM SCREEN");
                    }
                    if (createButton.getText().equals("ENEMY")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.CREATE_ENEMY;
                        System.out.println("CREATE ENEMY SCREEN");
                    }
                }
            }

            case CREATE_MONSTER: {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    SoundManager.playSound("Start.wav");
                    gameState = GameState.CREATE_SELECT;
                    cardLayout.show(cards, "CREATE_SELECT");
                    pack();
                    inputHandler.addObserver(createSelect);
                    requestFocusInWindow();
                }
            }

            default:
                break;
        }
    }
}
