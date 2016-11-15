import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

public class executeInternalFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 * 
	 * @param metaData
	 * @param fROMTable
	 */
	public executeInternalFrame(ResultSet metaData, String FROMTable) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		Vector<JTextField> tfVector = new Vector<JTextField>();

		StringBuilder sbInsert = new StringBuilder();
		sbInsert.append("INSERT INTO " + FROMTable + " VALUES( ");

		StringBuilder sbDelete = new StringBuilder();
		sbDelete.append("DELETE FROM " + FROMTable + " WHERE ");

		JPanel parentPanel = new JPanel();
		getContentPane().add(parentPanel, BorderLayout.CENTER);
		parentPanel.setLayout(new CardLayout(0, 0));

		JPanel addPanel = new JPanel();
		parentPanel.add(addPanel, "addPanel");
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 268, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		addPanel.setLayout(gbl_panel_1);

		JButton btnAdd = new JButton("ADD");
		GridBagConstraints gbc_lblAdd = new GridBagConstraints();
		gbc_lblAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdd.gridx = 1;
		gbc_lblAdd.gridy = 1;
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int index = 0; index < tfVector.size(); index++) {
					if (index == tfVector.size() - 1) {
						sbInsert.append("'" + tfVector.elementAt(index).getText() + "' )");
					} else if (index < tfVector.size() - 1) {
						sbInsert.append("'" + tfVector.elementAt(index).getText() + "', ");
					}
				}
				Connection connection = SQLConnection.connectToDatabase();
				try {
					Statement statement = connection.createStatement();
					statement.executeUpdate(sbInsert.toString());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		addPanel.add(btnAdd, gbc_lblAdd);
		try {
			int columnCount = metaData.getMetaData().getColumnCount();
			GridBagConstraints gblc = new GridBagConstraints();

			for (int counter = 1; counter <= columnCount; counter++) {
				gblc.fill = GridBagConstraints.BOTH;
				gblc.gridx = 0;
				gblc.gridy = ((counter - 1) * 2);
				addPanel.add(new JLabel(metaData.getMetaData().getColumnName(counter)), gblc);

				gblc.gridy += 1;
				JTextField tf = new JTextField(15);
				tfVector.add(tf);
				addPanel.add(tf, gblc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel deletePanel = new JPanel();
		parentPanel.add(deletePanel, "deletePanel");
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { };
		gbl_panel_2.rowHeights = new int[] { 0 };
		gbl_panel_2.columnWeights = new double[] { };
		gbl_panel_2.rowWeights = new double[] { Double.MIN_VALUE };
		deletePanel.setLayout(gbl_panel_2);

		try {
			int columnCount = metaData.getMetaData().getColumnCount();
			GridBagConstraints gblc = new GridBagConstraints();
			gblc.insets = new Insets(0, 0, 5, 5);
			GridBagConstraints gblc_1 = new GridBagConstraints();
			gblc_1.insets = new Insets(0, 0, 5, 0);
			GridBagConstraints gblc_2 = new GridBagConstraints();
			gblc_2.insets = new Insets(0, 0, 0, 5);
			JComboBox<String> jcb = new JComboBox<String>();
			for (int counter = 1; counter < columnCount; counter++) {
				jcb.addItem(metaData.getMetaData().getColumnName(counter));
			}
			gblc.gridwidth = 3;
			gblc.gridy = 1;
			gblc.gridx = 0;
			deletePanel.add(jcb, gblc);

			JTextField tfDelete = new JTextField(10);
			gblc_1.gridwidth = 3;
			gblc_1.gridy = 1;
			gblc_1.gridx = 6;
			deletePanel.add(tfDelete, gblc_1);

			JButton btnDelete = new JButton("Delete");
			gblc_2.gridwidth = 3;
			gblc_2.gridy = 3;
			gblc_2.gridx = 3;
			btnDelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					sbDelete.append(jcb.getSelectedItem() + " = " + tfDelete.getText());

					Connection connection = SQLConnection.connectToDatabase();
					try {
						Statement statement = connection.createStatement();
						System.out.println(sbDelete.toString());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			deletePanel.add(btnDelete, gblc_2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel updatePanel = new JPanel();
		parentPanel.add(updatePanel, "updatePanel");
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0 };
		gbl_panel_3.rowHeights = new int[] { 0 };
		gbl_panel_3.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { Double.MIN_VALUE };
		updatePanel.setLayout(gbl_panel_3);

		pack();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnExecute = new JMenu("Execute");
		menuBar.add(mnExecute);

		ButtonGroup group = new ButtonGroup();

		JRadioButtonMenuItem rdbtnmntmAdd = new JRadioButtonMenuItem("Add");
		rdbtnmntmAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) parentPanel.getLayout();
				cl.show(parentPanel, "addPanel");
			}
		});
		mnExecute.add(rdbtnmntmAdd);
		group.add(rdbtnmntmAdd);

		JRadioButtonMenuItem rdbtnmntmDelete = new JRadioButtonMenuItem("Delete");
		rdbtnmntmDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) parentPanel.getLayout();
				cl.show(parentPanel, "deletePanel");
			}
		});
		mnExecute.add(rdbtnmntmDelete);
		group.add(rdbtnmntmDelete);

		JRadioButtonMenuItem rdbtnmntmUpdate = new JRadioButtonMenuItem("Update");
		rdbtnmntmUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) parentPanel.getLayout();
				cl.show(parentPanel, "updatePanel");
			}
		});
		mnExecute.add(rdbtnmntmUpdate);
		group.add(rdbtnmntmUpdate);
		setVisible(true);

	}

	public static DefaultTableModel makeTableModel(ResultSet rs) {
		try {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			Vector<String> columnSpace = new Vector<String>();
			for (int column = 1; column <= rsMetaData.getColumnCount(); column++) {
				columnNames.add(rsMetaData.getColumnName(column));
				columnSpace.add("");
			}
			return new DefaultTableModel(columnSpace, columnNames);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
