package gui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SAFEBloodGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public SAFEBloodGUI() {
		initialize();
	}

	private void initialize(){
		Rectangle rectangle = new Rectangle(0, 0, 830, 430);
		getContentPane().setLayout(new CardLayout());
		setTitle("S.A.F.E. Blood DBMS");
		setBounds(rectangle);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel loginPanel = LoginGUI.createLoginGUI();
		JPanel viewPanel = ViewGUI.createViewGUI();
		
		//TODO add listener to see when loginPanel gets removed so that it will maximize screen
		getContentPane().add(loginPanel);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SAFEBloodGUI gui = new SAFEBloodGUI();
			gui.setVisible(true);
		});
	}
}
