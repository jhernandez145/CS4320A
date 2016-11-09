package gui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class SAFEBloodGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public SAFEBloodGUI() {
		setTitle("SAFE Blood DBMS");
		setLayout(new FlowLayout());

		JMenuBar menuBar = new JMenuBar();
		JMenu optionsMenu = new JMenu("Options");

		menuBar.add(optionsMenu);

		JPanel mainPanel = new JPanel(new CardLayout());
		JPanel login = new LoginGUI();
		JPanel view = new ViewGUI();
		mainPanel.add(login, "0");
		mainPanel.add(view, "1");

		add(mainPanel);
		setJMenuBar(menuBar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			@SuppressWarnings("unused")
			SAFEBloodGUI gui = new SAFEBloodGUI();
		});
	}
}
