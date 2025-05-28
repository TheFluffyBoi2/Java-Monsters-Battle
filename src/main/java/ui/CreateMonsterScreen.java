package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Type;
import entities.Monsters.Monster;
import entities.Monsters.MonsterService;
import entities.Moves.Move;
import entities.Moves.MoveService;

public class CreateMonsterScreen extends JPanel {
    private final JTextField nameField;
    private final JTextField healthField;
    private final JTextField attackField;

    private final JComboBox<Move> moveBox1;
    private final JComboBox<Move> moveBox2;
    private final JComboBox<Type> typeBox;

    private final JButton createButton;

    public CreateMonsterScreen() {
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

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(10);

        JLabel healthLabel = new JLabel("Health:");
        healthField = new JTextField(10);

        JLabel attackLabel = new JLabel("Attack:");
        attackField = new JTextField(10);

        JLabel moveLabel1 = new JLabel("Move 1:");
        JLabel moveLabel2 = new JLabel("Move 2:");
        moveBox1 = new JComboBox<>(MoveService.getMoves().toArray(new Move[0]));
        moveBox2 = new JComboBox<>(MoveService.getMoves().toArray(new Move[0]));

        JLabel typeLabel = new JLabel("Type:");
        typeBox = new JComboBox<>(Type.values());

        createButton = new JButton("Create Monster");

        JLabel exitText = new JLabel("Press esc to exit", JLabel.CENTER);

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(healthLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(healthField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(attackLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(attackField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(moveLabel1, gbc);
        gbc.gridx = 1;
        contentPanel.add(moveBox1, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        contentPanel.add(moveLabel2, gbc);
        gbc.gridx = 1;
        contentPanel.add(moveBox2, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        contentPanel.add(createButton, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        contentPanel.add(exitText, gbc);

        background.add(contentPanel);

        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(20f);
            nameLabel.setFont(pkmnFont);
            healthLabel.setFont(pkmnFont);
            attackLabel.setFont(pkmnFont);
            moveLabel1.setFont(pkmnFont);
            moveLabel2.setFont(pkmnFont);
            createButton.setFont(pkmnFont);
            exitText.setFont(pkmnFont);
            nameField.setFont(pkmnFont);
            healthField.setFont(pkmnFont);
            attackField.setFont(pkmnFont);
            moveBox1.setFont(pkmnFont);
            moveBox2.setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        createButton.addActionListener(this::createMonster);
    }

    private void createMonster(ActionEvent e) {
        try {
            String name = nameField.getText();
            int health = Integer.parseInt(healthField.getText());
            int attack = Integer.parseInt(attackField.getText());
            List<Move> selectedMoves = new ArrayList<>();
            selectedMoves.add((Move) moveBox1.getSelectedItem());
            selectedMoves.add((Move) moveBox2.getSelectedItem());
            // Type type = (Type) typeBox.getSelectedItem();

            Monster monster = new Monster(name, health, attack);
            MonsterService.addMonster(monster, selectedMoves);

            JOptionPane.showMessageDialog(this, "Monster Created!");

            nameField.setText("");
            healthField.setText("");
            attackField.setText("");
            moveBox1.setSelectedIndex(0);
            moveBox2.setSelectedIndex(0);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshMoveBoxes() {
        moveBox1.removeAllItems();
        moveBox2.removeAllItems();
        for (Move m : MoveService.getMoves()) {
            moveBox1.addItem(m);
            moveBox2.addItem(m);
        }
    }
}
