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

public class DeleteMonsterScreen extends JPanel {
    private final JComboBox<Monster> deletedMonster;

    private final JButton deleteButton;

    public DeleteMonsterScreen() {
        setPreferredSize(new Dimension(512, 512));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/assets/container3.png")));
        background.setLayout(new GridBagLayout());
        add(background, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;

        JLabel deletedLabel = new JLabel("Select A Monster To Delete", JLabel.CENTER);
        deletedMonster = new JComboBox<>(MonsterService.getMonsters().toArray(new Monster[0]));
        deleteButton = new JButton("Delete Monster");
        JLabel exitText = new JLabel("Press esc to exit", JLabel.CENTER);

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(deletedLabel, gbc);

        gbc.gridy++;
        contentPanel.add(deletedMonster, gbc);

        gbc.gridy++;
        contentPanel.add(deleteButton, gbc);

        gbc.gridy++;
        contentPanel.add(exitText, gbc);

        background.add(contentPanel);

        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(20f);
            deletedLabel.setFont(pkmnFont);
            deletedMonster.setFont(pkmnFont);
            deleteButton.setFont(pkmnFont);
            exitText.setFont(pkmnFont);
            exitText.setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        deleteButton.addActionListener(this::deleteMonster);
    }

    private void deleteMonster(ActionEvent e) {
        try {
            Monster monster = (Monster) deletedMonster.getSelectedItem();
            MonsterService.deleteMonster(monster);
            System.out.println("Monster: " + monster + " was deleted");

            JOptionPane.showMessageDialog(this, "Monster Deleted!");
            refreshMonsterBox();

            deletedMonster.setSelectedIndex(0);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Delete a valid monster!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshMonsterBox() {
        deletedMonster.removeAllItems();
        for (Monster m : MonsterService.getMonsters()) {
            deletedMonster.addItem(m);
        }
    }
}
