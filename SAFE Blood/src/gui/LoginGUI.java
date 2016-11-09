package gui;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	public LoginGUI() {
		GridBagConstraints constraints = new GridBagConstraints();
		Font font = new Font("Courier", Font.ITALIC, 30);

		setLayout(new GridBagLayout());
		constraints.insets = new Insets(5, 5, 5, 5);

		JLabel usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(font);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(usernameLabel, constraints);

		JTextField usernameTextField = new JTextField(15);
		usernameTextField.setFont(font);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(usernameTextField, constraints);

		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(font);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(passwordLabel, constraints);

		JPasswordField passwordField = new JPasswordField(15);
		passwordField.setFont(font);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(passwordField, constraints);

		JButton loginButton = new JButton("Login");
		loginButton.setFont(font);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) getParent().getLayout();
				cl.next(getParent());
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.CENTER;
		add(loginButton, constraints);
	}

}