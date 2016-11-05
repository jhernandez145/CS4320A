package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ViewGUI {
	public static JPanel createViewGUI() {
		final String[] labels = {"Run Report", "Access Donors", "Access Branches", "Access Requests", "Access HC Providers", "Access Employees"};
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new GridLayout(2, 3));
		
		
		for(int index = 0; index < labels.length; index++){
			viewPanel.add(new JButton(labels[index]));
		}
		
		return viewPanel;
	}
}
