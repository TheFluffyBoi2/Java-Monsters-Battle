package ui;

import java.util.List;
import java.util.stream.Collectors;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import entities.Monsters.Monster;
import entities.Monsters.MonsterService;
import entities.Player.Player;
import sound.SoundManager;

public class BattleScreen extends JPanel implements KeyEventObserver {
    private int selectedIndex = 0;
    private final MenuButton[] menuButtons = new MenuButton[3];
    private final GameWindow gameWindow;
    private final JPanel battlePanel;
    private final JPanel enemyPanel;
    private final JPanel playerPanel;
    private final MenuButton attack;
    private final MenuButton defend;
    private final MenuButton save;
    private JPanel playerStats;
    private JPanel enemyStats;
    private JLabel playerMonsterName;
    private JLabel enemyMonsterName;
    private JLabel enemyMonster;
    private JLabel playerMonster;
    private JLabel optionsBackground;
    private JProgressBar enemyHpBar;
    private JProgressBar playerHpBar;
    private List<Monster> playerMonsters;
    private List<Monster> enemyMonsters;
    private int remainingPlayerHp;
    private int remainingEnemyHp;
    private Monster selectedPlayerMonster;
    private Monster selectedEnemyMonster;
    private Timer hpAnimatedTimer;
    private Timer deathAnimatedTimer;
    private boolean animationPlaying = false;
    private boolean defending = false;
    private int currentSpriteYPosition;

    public BattleScreen(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        optionsBackground = new JLabel(new ImageIcon(getClass().getResource("/assets/container4.png")));
        optionsBackground.setLayout(new BorderLayout());

        attack = new MenuButton("ATTACK");
        defend = new MenuButton("DEFEND");
        save = new MenuButton("SAVE");
        menuButtons[0] = attack;
        menuButtons[1] = defend;
        menuButtons[2] = save;
        menuButtons[0].setSelected();
        
        optionsBackground.add(attack, BorderLayout.WEST);
        optionsBackground.add(defend, BorderLayout.CENTER);
        optionsBackground.add(save, BorderLayout.EAST);

        setPreferredSize(new Dimension(512, 512));
        setBackground(new Color(0, 0, 0));
        setLayout(new BorderLayout());

        add(optionsBackground, BorderLayout.SOUTH);

        battlePanel = new JPanel();
        battlePanel.setLayout(new BorderLayout());
        playerPanel = new JPanel();
        enemyPanel = new JPanel();

        playerPanel.setLayout(new BorderLayout());
        ImageIcon backSprite = new ImageIcon(getClass().getResource("/assets/monster1back.png"));
        Image backImage = backSprite.getImage();
        Image scaledBackImage = backImage.getScaledInstance(
            backImage.getWidth(null) * 4,
            backImage.getHeight(null) * 4,
            Image.SCALE_FAST
        );
        playerMonster = new JLabel(new ImageIcon(scaledBackImage));
        playerPanel.add(playerMonster, BorderLayout.WEST);

        enemyPanel.setLayout(new BorderLayout());
        ImageIcon frontSprite = new ImageIcon(getClass().getResource("/assets/monster1.png"));
        Image frontImage = frontSprite.getImage();
        Image scaledFrontImage = frontImage.getScaledInstance(
            frontImage.getWidth(null) * 3,
            frontImage.getHeight(null) * 3,
            Image.SCALE_FAST
        );
        enemyMonster = new JLabel(new ImageIcon(scaledFrontImage));
        enemyPanel.add(enemyMonster, BorderLayout.EAST);

        battlePanel.add(playerPanel, BorderLayout.SOUTH);
        battlePanel.add(enemyPanel, BorderLayout.NORTH);

        add(battlePanel, BorderLayout.CENTER);


        try {
            playerMonsters = Player.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectedPlayerMonster = playerMonsters.get(0);
        remainingPlayerHp = selectedPlayerMonster.getHealth();

        playerStats = new JPanel();
        playerStats.setLayout(new BoxLayout(playerStats, BoxLayout.PAGE_AXIS));
        playerStats.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        playerMonsterName = new JLabel(selectedPlayerMonster.getName());
        playerStats.add(playerMonsterName);
        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(24f);
            playerMonsterName.setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        playerHpBar = new JProgressBar();
        playerHpBar.setMinimum(0);
        playerHpBar.setMaximum(selectedPlayerMonster.getHealth());
        playerHpBar.setValue(remainingPlayerHp);
        playerHpBar.setForeground(new Color(47, 148, 16));
        playerHpBar.setBackground(Color.WHITE);
        playerHpBar.setMaximumSize(new Dimension(380, 20));
        playerHpBar.setPreferredSize(new Dimension(380, 20));
        playerHpBar.setStringPainted(false);

        JPanel hpWrapper = new JPanel();
        hpWrapper.setLayout(new BoxLayout(hpWrapper, BoxLayout.Y_AXIS));
        hpWrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        hpWrapper.add(playerHpBar, BorderLayout.CENTER);
        playerStats.add(hpWrapper, BorderLayout.CENTER);

        playerPanel.add(playerStats, BorderLayout.CENTER);

        try {
            enemyMonsters = MonsterService.getMonsters().stream().limit(3).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectedEnemyMonster = enemyMonsters.get(0);
        remainingEnemyHp = selectedEnemyMonster.getHealth();

        enemyStats = new JPanel();
        enemyStats.setLayout(new BoxLayout(enemyStats, BoxLayout.PAGE_AXIS));
        enemyStats.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        enemyMonsterName = new JLabel(selectedEnemyMonster.getName());
        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(24f);
            enemyMonsterName.setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        enemyStats.add(enemyMonsterName, BorderLayout.NORTH);
        enemyHpBar = new JProgressBar();
        enemyHpBar.setMinimum(0);
        enemyHpBar.setMaximum(selectedEnemyMonster.getHealth());
        enemyHpBar.setValue(remainingEnemyHp);
        enemyHpBar.setForeground(new Color(47, 148, 16));
        enemyHpBar.setBackground(Color.WHITE);
        enemyHpBar.setMaximumSize(new Dimension(380, 20));
        enemyHpBar.setPreferredSize(new Dimension(380, 20));
        enemyHpBar.setStringPainted(false);

        JPanel enemyHpBarWrapper = new JPanel();
        enemyHpBarWrapper.setLayout(new BoxLayout(enemyHpBarWrapper, BoxLayout.Y_AXIS));
        enemyHpBarWrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        enemyHpBarWrapper.add(enemyHpBar, BorderLayout.CENTER);
        enemyStats.add(enemyHpBarWrapper);

        enemyPanel.add(enemyStats, BorderLayout.CENTER);
    }

    public MenuButton getSelectedButton() {
        for (MenuButton button : menuButtons) {
            if (button.getSelected()) {
                return button;
            }
        }
        return null;
    } 

    public void hideMenuButtons() {
        attack.setVisible(false);
        defend.setVisible(false);
        save.setVisible(false);
        optionsBackground.revalidate();
        optionsBackground.repaint();
    }

    public void showMenuButtons() {
        attack.setVisible(true);
        defend.setVisible(true);
        save.setVisible(true);
        optionsBackground.revalidate();
        optionsBackground.repaint();
    }

    @Override
    public void onKeyEvent(KeyEvent e) {
        if (animationPlaying) return;
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            MenuButton selectedButton = getSelectedButton();
            switch (selectedButton.getText()) {
                case "ATTACK": {
                    hideMenuButtons();
                    int damage = selectedPlayerMonster.getAttack();
                    int currentenemyHpBar = remainingEnemyHp;
                    int newenemyHpBar = currentenemyHpBar - damage;
                    enemyHpBarChange(newenemyHpBar, () -> {
                        if (remainingEnemyHp <= 0) {
                            monsterDied(enemyMonster, () -> {
                                animationPlaying = false;
                                enemyMonsters.remove(0);
                                if (enemyMonsters.size() == 0) {
                                        gameWon();
                                        return;
                                    }
                                selectedEnemyMonster = enemyMonsters.get(0);
                                remainingEnemyHp = selectedEnemyMonster.getHealth();
                                enemyMonsterName.setText(selectedEnemyMonster.getName());
                                enemyHpBar.setMinimum(0);
                                enemyHpBar.setMaximum(selectedEnemyMonster.getHealth());
                                enemyHpBar.setValue(remainingEnemyHp);
                                showMenuButtons();
                            });
                            return;
                        }
                        int enemyDamage = selectedEnemyMonster.getAttack();
                        int currentPlayerHp = remainingPlayerHp;
                        int newPlayerHp = currentPlayerHp - enemyDamage;
                        playerHpChange(newPlayerHp, () -> {
                            if (remainingPlayerHp <= 0) {
                                monsterDied(playerMonster, () -> {
                                    animationPlaying = false;
                                    playerMonsters.remove(0);
                                    if (playerMonsters.size() == 0) {
                                        gameOver();
                                        return;
                                    }
                                    selectedPlayerMonster = playerMonsters.get(0);
                                    remainingPlayerHp = selectedPlayerMonster.getHealth();
                                    playerMonsterName.setText(selectedPlayerMonster.getName());
                                    playerHpBar.setMinimum(0);
                                    playerHpBar.setMaximum(selectedPlayerMonster.getHealth());
                                    playerHpBar.setValue(remainingPlayerHp);
                                    showMenuButtons();
                                });
                            } else {
                                animationPlaying = false;
                                showMenuButtons();
                            }
                        });
                    });
                    break;
                }
                    
                case "DEFEND": {
                    hideMenuButtons();
                    defending = true;
                    int enemyDamage = selectedEnemyMonster.getAttack();
                    int currentPlayerHp = remainingPlayerHp;
                    int newPlayerHp = currentPlayerHp - enemyDamage/2;
                    playerHpChange(newPlayerHp, () -> {
                        if (remainingPlayerHp <= 0) {
                                monsterDied(playerMonster, () -> {
                                    animationPlaying = false;
                                    playerMonsters.remove(0);
                                    if (playerMonsters.size() == 0) {
                                        gameOver();
                                        return;
                                    }
                                    selectedPlayerMonster = playerMonsters.get(0);
                                    remainingPlayerHp = selectedPlayerMonster.getHealth();
                                    playerMonsterName.setText(selectedPlayerMonster.getName());
                                    playerHpBar.setMinimum(0);
                                    playerHpBar.setMaximum(selectedPlayerMonster.getHealth());
                                    playerHpBar.setValue(remainingPlayerHp);
                                    showMenuButtons();
                            });
                        } else {
                            animationPlaying = false;
                            showMenuButtons();
                        }
                    });
                    break;
                }
                default:
                    break;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            selectedIndex += 1;
            selectedIndex = (selectedIndex > 2) ? 0 : selectedIndex;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            selectedIndex -= 1;
            selectedIndex = (selectedIndex < 0) ? 2 : selectedIndex;
        }
        menuButtons[selectedIndex].setSelected();
        for (int i = 0; i <= 2; i++) {
            if (i != selectedIndex) {
                menuButtons[i].setDeselected();
            }
        }
        SoundManager.playSound("Select.wav");
        menuButtons[selectedIndex].setSelected();
        for (int i = 0; i <= 2; i++) {
            if (i != selectedIndex) {
                menuButtons[i].setDeselected();
            }
        }
    }

    public void enemyHpBarChange(int newHp, Runnable finished) {
        animationPlaying = true;
        if (hpAnimatedTimer != null && hpAnimatedTimer.isRunning()) {
            hpAnimatedTimer.stop();
        }

        hpAnimatedTimer = new Timer(15, null);
        hpAnimatedTimer.addActionListener(e -> {
            if (remainingEnemyHp == newHp || remainingEnemyHp < 0) {
                hpAnimatedTimer.stop();
                finished.run();
                return;
            }
            if (remainingEnemyHp > newHp) {
                remainingEnemyHp--;
            } else {
                remainingEnemyHp++;
            }
            enemyHpBar.setValue(remainingEnemyHp);
        });

        hpAnimatedTimer.start();
    }

    public void playerHpChange(int newHp, Runnable finished) {
        animationPlaying = true;
        if (hpAnimatedTimer != null && hpAnimatedTimer.isRunning()) {
            hpAnimatedTimer.stop();
        }

        hpAnimatedTimer = new Timer(15, null);
        hpAnimatedTimer.addActionListener(e -> {
            if (remainingPlayerHp == newHp || remainingEnemyHp < 0) {
                hpAnimatedTimer.stop();
                finished.run();
                return;
            }
            if (remainingPlayerHp > newHp) {
                remainingPlayerHp--;
            } else {
                remainingPlayerHp++;
            }
            playerHpBar.setValue(remainingPlayerHp);
        });

        hpAnimatedTimer.start();
    }

    public void monsterDied(JLabel monster, Runnable finished) {
        currentSpriteYPosition = monster.getY();
        animationPlaying = true;
        int targetY = currentSpriteYPosition + 300;
        int pixels = 5;
        if (deathAnimatedTimer != null && deathAnimatedTimer.isRunning()) {
            deathAnimatedTimer.stop();
        }

        deathAnimatedTimer = new Timer(16, null);
        deathAnimatedTimer.addActionListener(e -> {
            if (currentSpriteYPosition == targetY) {
                deathAnimatedTimer.stop();
                finished.run();
                return;
            }
            if (currentSpriteYPosition < targetY) {
                currentSpriteYPosition += pixels;
            } else {
                currentSpriteYPosition--;
            }
            monster.setLocation(monster.getX(), currentSpriteYPosition);
        });

        deathAnimatedTimer.start();
    }

    public void refreshBattleScreen() {
        try {
            playerMonsters = Player.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectedPlayerMonster = playerMonsters.get(0);
        remainingPlayerHp = selectedPlayerMonster.getHealth();
        playerMonsterName.setText(selectedPlayerMonster.getName());
        playerHpBar.setMinimum(0);
        playerHpBar.setMaximum(selectedPlayerMonster.getHealth());
        playerHpBar.setValue(remainingPlayerHp);

        try {
            enemyMonsters = MonsterService.getMonsters().stream().limit(3).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectedEnemyMonster = enemyMonsters.get(0);
        remainingEnemyHp = selectedEnemyMonster.getHealth();
        enemyMonsterName.setText(selectedEnemyMonster.getName());
        enemyHpBar.setMinimum(0);
        enemyHpBar.setMaximum(selectedEnemyMonster.getHealth());
        enemyHpBar.setValue(remainingEnemyHp);
    }

    public void gameOver() {
        gameWindow.showGameOverScreen();;
    }

    public void gameWon() {
        gameWindow.showGameWonScreen();
    }
}
