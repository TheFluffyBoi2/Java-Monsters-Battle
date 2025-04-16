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
import entities.Moves.MoveFactory;
import entities.Moves.MoveService;

public class CreateMoveScreen extends JPanel {
    private final JTextField nameField;
    private final JTextField damageField;

    private final JComboBox<Type> typeBox;

    private final JButton createButton;

    public CreateMoveScreen() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(512, 512));
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(10);

        JLabel damageLabel = new JLabel("Damage:");
        damageField = new JTextField(10);

        JLabel typeLabel = new JLabel("Type:");
        typeBox = new JComboBox<>(Type.values());

        createButton = new JButton("Create Move");

        JLabel exitText = new JLabel("Press esc to exit");

        gbc.gridx = 0; gbc.gridy = 0;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(damageLabel, gbc);
        gbc.gridx = 1;
        add(damageField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(typeLabel, gbc);
        gbc.gridx = 1;
        add(typeBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(createButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(exitText, gbc);

        createButton.addActionListener(this::createMove);
    }
    
    private void createMove(ActionEvent e) {
        try {
            String name = nameField.getText();
            int damage = Integer.parseInt(damageField.getText());
            Type type = (Type) typeBox.getSelectedItem();

            Move move = MoveFactory.createMove(name, type, damage);
            MoveService.addMove(move);

            JOptionPane.showMessageDialog(this, "Move Created!");

            nameField.setText("");
            damageField.setText("");
            typeBox.setSelectedIndex(0);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
