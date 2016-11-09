package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ViewGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	public ViewGUI() {
		String[][] labels = { { "View Reports", "View by Donor", "View by Branch" },
				{ "View by HC_Prov", "View by Blood_Req", "View by Employee" } };
		GridBagConstraints constraints = new GridBagConstraints();
		Font font = new Font("Courier", Font.PLAIN, 20);
		setLayout(new GridBagLayout());

		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;

		for (int row = 0; row < labels.length; row++) {
			for (int column = 0; column < labels[row].length; column++) {
				JButton button = new JButton(labels[row][column]);
				button.setFont(font);
				constraints.gridx = column;
				constraints.gridy = row;
				add(button, constraints);
			}
		}

	}
}
