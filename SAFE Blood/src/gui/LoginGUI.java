package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import externalclasses.SpringUtilities;
import sqlconnection.SQLConnection;

public class LoginGUI {
	public static JPanel createLoginGUI() {
		
		SpringLayout layout = new SpringLayout();
		JPanel loginPanel = new JPanel(new BorderLayout());
		JPanel fieldsPanel = new JPanel(layout);
		//fieldsPanel.setBackground(Color.RED);
		
		JTextField username = new JTextField();
		
		loginPanel.add(fieldsPanel, BorderLayout.CENTER);
		
		Component left = new JLabel("Left");
	    Component right = new JTextField(15);
		
	    fieldsPanel.add(left);
	    fieldsPanel.add(right);
	    
		layout.putConstraint(SpringLayout.WEST, left, 10, SpringLayout.WEST, fieldsPanel);
	    layout.putConstraint(SpringLayout.NORTH, left, 25, SpringLayout.NORTH, fieldsPanel);
	    layout.putConstraint(SpringLayout.NORTH, right, 25, SpringLayout.NORTH, fieldsPanel);
	    layout.putConstraint(SpringLayout.WEST, right, 20, SpringLayout.EAST, left);
		
		loginPanel.add(addButton("Login"), BorderLayout.SOUTH);
		return loginPanel;
	}
	
	public static JButton addButton(String title){
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
}
