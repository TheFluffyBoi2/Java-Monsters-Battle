package ui;

import java.awt.CardLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
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
    private final CreateMoveScreen createMove;
    private final CreateTeamScreen createTeam;
    private final DeleteMonsterScreen deleteMonster;
    private final BattleScreen battleScreen;
    private final GameEndScreen gameOver;
    private final GameEndScreen gameWon; 
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
        setIconImage(new ImageIcon(getClass().getResource("/assets/icon.png")).getImage());

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        titleScreen = new TitleScreen();
        modeSelect = new ModeScreen();
        createSelect = new CreateSelectScreen();
        createMonster = new CreateMonsterScreen();
        createMove = new CreateMoveScreen();
        createTeam = new CreateTeamScreen();
        deleteMonster = new DeleteMonsterScreen();
        battleScreen = new BattleScreen(this);
        gameOver = new GameEndScreen("Game Over!");
        gameWon = new GameEndScreen("You Won!");
        
        cards.add(titleScreen, "TITLE_SCREEN");
        cards.add(modeSelect, "MODE_SELECT");
        cards.add(createSelect, "CREATE_SELECT");
        cards.add(createMonster, "CREATE_MONSTER");
        cards.add(createMove, "CREATE_MOVE");
        cards.add(createTeam, "CREATE_TEAM");
        cards.add(deleteMonster, "DELETE_MONSTER");
        cards.add(battleScreen, "BATTLE_SCREEN");
        cards.add(gameOver, "GAME_OVER");
        cards.add(gameWon, "GAME_WON");

        this.inputHandler.addObserver(titleScreen);
        this.inputHandler.addObserver(this);

        setContentPane(cards);
        pack();
        setFocusable(true);
        requestFocusInWindow();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    inputHandler.keyPressed(e);
                }
                return false;
            }
        });
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
                        battleScreen.refreshBattleScreen();
                        cardLayout.show(cards, "BATTLE_SCREEN");
                        pack();
                        inputHandler.addObserver(battleScreen);
                        inputHandler.removeObserver(modeSelect);
                        requestFocusInWindow();
                        // JOptionPane.showMessageDialog(this, "The battle mechanic will be implemented for the 2nd checkpoint");
                    }
                }
                break;
            }

            case BATTLE: {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    SoundManager.playSound("Start.wav");
                    gameState = GameState.MODE_SELECT;
                    cardLayout.show(cards, "MODE_SELECT");
                    pack();
                    inputHandler.addObserver(modeSelect);
                    inputHandler.removeObserver(battleScreen);
                    requestFocusInWindow();
                }
                break;
            }

            case CREATE_SELECT: {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    SoundManager.playSound("Start.wav");
                    gameState = GameState.MODE_SELECT;
                    cardLayout.show(cards, "MODE_SELECT");
                    pack();
                    inputHandler.addObserver(modeSelect);
                    inputHandler.removeObserver(createSelect);
                    requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    MenuButton createButton = createSelect.getSelectedButton();
                    if (createButton.getText().equals("MONSTER")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.CREATE_MONSTER;
                        createMonster.refreshMoveBoxes();
                        cardLayout.show(cards, "CREATE_MONSTER");
                        pack();
                        inputHandler.removeObserver(createSelect);
                        requestFocusInWindow();
                    }
                    if (createButton.getText().equals("MOVE")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.CREATE_MOVE;
                        cardLayout.show(cards, "CREATE_MOVE");
                        pack();
                        inputHandler.removeObserver(createSelect);
                        requestFocusInWindow();
                    }
                    if (createButton.getText().equals("TEAM")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.CREATE_TEAM;
                        createTeam.refreshMonsterBoxes();
                        cardLayout.show(cards, "CREATE_TEAM");
                        pack();
                        inputHandler.removeObserver(createSelect);
                        requestFocusInWindow();
                    }
                    if (createButton.getText().equals("DELETE MONSTER")) {
                        SoundManager.playSound("Start.wav");
                        gameState = GameState.DELETE_MONSTER;
                        deleteMonster.refreshMonsterBox();
                        cardLayout.show(cards, "DELETE_MONSTER");
                        pack();
                        inputHandler.removeObserver(createSelect);
                        requestFocusInWindow();
                    }
                }
                break;
            }

            case GAME_END: {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    SoundManager.playSound("Start.wav");
                    gameState = GameState.TITLE_SCREEN;
                    cardLayout.show(cards, "TITLE_SCREEN");
                    pack();
                    inputHandler.addObserver(titleScreen);
                    requestFocusInWindow();
                }
                break;
            }

            case DELETE_MONSTER:
            case CREATE_MONSTER:
            case CREATE_TEAM:
            case CREATE_MOVE: {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    SoundManager.playSound("Start.wav");
                    gameState = GameState.CREATE_SELECT;
                    cardLayout.show(cards, "CREATE_SELECT");
                    pack();
                    inputHandler.addObserver(createSelect);
                    requestFocusInWindow();
                }
                break;
            }

            default:
                break;
        }
    }

    public void showGameOverScreen() {
        gameState = GameState.GAME_END;
        cardLayout.show(cards, "GAME_OVER");
        inputHandler.removeObserver(battleScreen);
        requestFocusInWindow();
    }

    public void showGameWonScreen() {
        gameState = GameState.GAME_END;
        cardLayout.show(cards, "GAME_WON");
        inputHandler.removeObserver(battleScreen);
        requestFocusInWindow();
    }
}
