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
import javax.swing.JTextField;

import entities.Type;
import entities.Moves.Move;
import entities.Moves.MoveFactory;
import entities.Moves.MoveService;

public class CreateMoveScreen extends JPanel {
    private final JTextField nameField;
    private final JTextField damageField;

    private final JComboBox<Type> typeBox;

    private final JButton createButton;

    public CreateMoveScreen() {
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

        JLabel damageLabel = new JLabel("Damage:");
        damageField = new JTextField(10);

        JLabel typeLabel = new JLabel("Type:");
        typeBox = new JComboBox<>(Type.values());

        createButton = new JButton("Create Move");

        JLabel exitText = new JLabel("Press esc to exit", JLabel.CENTER);

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(damageLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(damageField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        contentPanel.add(typeBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        contentPanel.add(createButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        contentPanel.add(exitText, gbc);

        background.add(contentPanel);

        try {
            Font pkmnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/PKMN RBYGSC.ttf")).deriveFont(20f);
            nameLabel.setFont(pkmnFont);
            damageLabel.setFont(pkmnFont);
            damageField.setFont(pkmnFont);
            typeLabel.setFont(pkmnFont);
            typeBox.setFont(pkmnFont);
            createButton.setFont(pkmnFont);
            exitText.setFont(pkmnFont);
            nameField.setFont(pkmnFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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
