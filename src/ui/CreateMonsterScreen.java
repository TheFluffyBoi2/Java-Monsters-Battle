package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

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
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(512, 512));
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

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

        JLabel exitText = new JLabel("Press esc to exit");

        gbc.gridx = 0; gbc.gridy = 0;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(healthLabel, gbc);
        gbc.gridx = 1;
        add(healthField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(attackLabel, gbc);
        gbc.gridx = 1;
        add(attackField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(moveLabel1, gbc);
        gbc.gridx = 1;
        add(moveBox1, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(moveLabel2, gbc);
        gbc.gridx = 1;
        add(moveBox2, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(createButton, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        add(exitText, gbc);

        createButton.addActionListener(this::createMonster);
    }

    private void createMonster(ActionEvent e) {
        try {
            String name = nameField.getText();
            int health = Integer.parseInt(healthField.getText());
            int attack = Integer.parseInt(attackField.getText());
            Move[] selectedMoves = new Move[] {
                (Move) moveBox1.getSelectedItem(),
                (Move) moveBox2.getSelectedItem()
            };
            // Type type = (Type) typeBox.getSelectedItem();

            Monster monster = new Monster(name, health, attack);
            monster.setMoves(selectedMoves);
            MonsterService.addMonster(monster);
            System.out.println(monster);

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
