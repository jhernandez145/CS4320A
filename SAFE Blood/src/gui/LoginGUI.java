package gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sqlconnection.SQLConnection;

public class LoginGUI {
	public static JPanel createLoginGUI() {
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel loginPanel = new JPanel(new GridBagLayout());
		loginPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		constraints = setConstraints(0, 0);
		loginPanel.add(addJLabel("Username: "), constraints);

		constraints = setConstraints(1, 0);
		constraints.ipadx = 300;
		loginPanel.add(addJTextField(), constraints);

		constraints = setConstraints(0, 1);
		loginPanel.add(addJLabel("Password: "), constraints);

		constraints = setConstraints(1, 1);
		loginPanel.add(addPasswordField(), constraints);

		constraints = setConstraints(0, 3);
		constraints.gridwidth = 2;
		loginPanel.add(addButton("Login"), constraints);

		return loginPanel;
	}

	public static GridBagConstraints setConstraints(int gridx, int gridy) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = gridx;
		constraints.gridy = gridy;

		return constraints;
	}

	public static JButton addButton(String title) {
		Dimension dimension = new Dimension(450, 50);
		JButton button = new JButton(title);
		button.setFont(new Font("Courier", Font.BOLD, 25));
		button.setPreferredSize(dimension);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SQLConnection.connectToDatabase();
			}
		});
		return button;
	}

	public static JLabel addJLabel(String title) {
		Dimension dimension = new Dimension(400, 400);
		JLabel label = new JLabel(title);
		label.setFont(new Font("Courier", Font.ITALIC, 25));
		label.setPreferredSize(dimension);

		return label;
	}

	public static JTextField addJTextField() {
		JTextField textField = new JTextField(10);
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					SQLConnection.connectToDatabase();
				}
			}
		});
		return textField;
	}

	public static JPasswordField addPasswordField() {
		JPasswordField passwordField = new JPasswordField(10);
		passwordField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					SQLConnection.connectToDatabase();
				}
			}
		});
		return passwordField;
	}
}
