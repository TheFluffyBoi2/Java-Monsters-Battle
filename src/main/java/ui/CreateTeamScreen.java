package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Monsters.Monster;
import entities.Monsters.MonsterService;
import entities.Player.Player;

public class CreateTeamScreen extends JPanel {
    private final JComboBox<Monster> firstMonster;
    private final JComboBox<Monster> secondMonster;
    private final JComboBox<Monster> thirdMonster;

    private JButton createButton;

    public CreateTeamScreen() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(512, 512));
        setBackground(Color.WHITE);

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/assets/container3.png")));
        background.setLayout(new GridBagLayout());
        add(background, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel firstMonsterLabel = new JLabel("First Monster:");
        JLabel secondMonsterLabel = new JLabel("Second Monster:");
        JLabel thirsMonsterLabel = new JLabel("Third Monster:");
        firstMonster = new JComboBox<>(MonsterService.getMonsters().toArray(new Monster[0]));
        secondMonster = new JComboBox<>(MonsterService.getMonsters().toArray(new Monster[0]));
        thirdMonster = new JComboBox<>(MonsterService.getMonsters().toArray(new Monster[0]));
        
        createButton = new JButton("Create Team");

        JLabel exitText = new JLabel("Press esc to exit", JLabel.CENTER);

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(firstMonsterLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(firstMonster, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(secondMonsterLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(secondMonster, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(thirsMonsterLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(thirdMonster, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        contentPanel.add(createButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        contentPanel.add(exitText, gbc);

        background.add(contentPanel);

        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(18f);
            firstMonsterLabel.setFont(pkmnFont);
            firstMonster.setFont(pkmnFont);
            secondMonsterLabel.setFont(pkmnFont);
            secondMonster.setFont(pkmnFont);
            thirsMonsterLabel.setFont(pkmnFont);
            thirdMonster.setFont(pkmnFont);
            createButton.setFont(pkmnFont);
            createButton.setFont(pkmnFont);
            exitText.setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        createButton.addActionListener(this::createTeam);
    }

    private void createTeam(ActionEvent e) {
        try {
            if (firstMonster.getSelectedItem() == null || secondMonster.getSelectedItem() == null || thirdMonster.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(
                    null, "You Must Select A Monster For Each Slot", "Invalid Selection", JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Monster monster1 = (Monster) firstMonster.getSelectedItem();
            Monster monster2 = (Monster) secondMonster.getSelectedItem();
            Monster monster3 = (Monster) thirdMonster.getSelectedItem();

            Player.addMonster(monster1);
            Player.addMonster(monster2);
            Player.addMonster(monster3);

            JOptionPane.showMessageDialog(this, "Team Created!");

            System.out.println("Team created with the following monsters:");
            for (Monster m : Player.getMonsters()) {
                System.out.println(m);
            }

            firstMonster.setSelectedItem(Player.getMonsters()[0]);
            secondMonster.setSelectedItem(Player.getMonsters()[1]);
            thirdMonster.setSelectedItem(Player.getMonsters()[2]);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Select A Valid Monster!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        public void refreshMonsterBoxes() {
            firstMonster.removeAllItems();
            secondMonster.removeAllItems();
            thirdMonster.removeAllItems();
            for (Monster m : MonsterService.getMonsters()) {
                firstMonster.addItem(m);
                secondMonster.addItem(m);
                thirdMonster.addItem(m);
            }

            if (Player.getIndex() == 0) {
                createButton.setText("Create Team");
            } else {
                createButton.setText("Update Team");
                firstMonster.setSelectedItem(Player.getMonsters()[0]);
                secondMonster.setSelectedItem(Player.getMonsters()[1]);
                thirdMonster.setSelectedItem(Player.getMonsters()[2]);
            }
        }

        public void updateMonsterBoxes() {

        }
}
