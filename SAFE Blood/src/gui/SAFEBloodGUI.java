package gui;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SAFEBloodGUI extends JFrame{
	private static final long serialVersionUID = 1L;

	public SAFEBloodGUI(){
		initialize();
	}
	private void initialize(){
		getContentPane().setLayout(new CardLayout());
		setTitle("S.A.F.E. Blood DBMS");
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel loginPanel = LoginGUI.createLoginGUI();
		
		getContentPane().add(loginPanel);
		pack();
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(() -> {
			SAFEBloodGUI gui = new SAFEBloodGUI();
			gui.setVisible(true);
		});
	}
}
