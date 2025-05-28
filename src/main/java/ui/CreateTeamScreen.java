package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Monsters.Monster;
import entities.Monsters.MonsterService;
import entities.Player.TeamService;

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
        
        createButton = new JButton("Update Team");

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

            List<Monster> monsters = new ArrayList<>();
            Monster monster1 = (Monster) firstMonster.getSelectedItem();
            Monster monster2 = (Monster) secondMonster.getSelectedItem();
            Monster monster3 = (Monster) thirdMonster.getSelectedItem();
            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);

            try {
                TeamService.updateTeam(monsters);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "Team Created!");

            try {
                firstMonster.setSelectedItem(TeamService.getTeam().get(0));
                secondMonster.setSelectedItem(TeamService.getTeam().get(1));
                thirdMonster.setSelectedItem(TeamService.getTeam().get(2));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

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
            
        try {
            List<Monster> team = TeamService.getTeam();
            if (team.size() > 0) firstMonster.setSelectedItem(team.get(0));
            if (team.size() > 1) secondMonster.setSelectedItem(team.get(1));
            if (team.size() > 2) thirdMonster.setSelectedItem(team.get(2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
