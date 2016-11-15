import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SQLView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public SQLView(String FROMTable) {
		final String FROM = "FROM " + FROMTable;
		Object[] metaData = SQLConnection.getMetaData(FROMTable, "SELECT * " + FROM);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 457, 363);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 30, 39, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblFilter = new JLabel("Sort By");
		GridBagConstraints gbc_lblFilter = new GridBagConstraints();
		gbc_lblFilter.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilter.gridx = 1;
		gbc_lblFilter.gridy = 1;
		getContentPane().add(lblFilter, gbc_lblFilter);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 12;
		gbc_scrollPane.gridheight = 9;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 4;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);

		table = new JTable(makeTableModel((ResultSet) metaData[2]));
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel) table.getModel());
		table.setRowSorter(sorter);

		scrollPane.setViewportView(table);

		JLabel lblWhere = new JLabel("Where");
		GridBagConstraints gbc_lblWhere = new GridBagConstraints();
		gbc_lblWhere.insets = new Insets(0, 0, 5, 5);
		gbc_lblWhere.gridx = 1;
		gbc_lblWhere.gridy = 2;
		getContentPane().add(lblWhere, gbc_lblWhere);

		textField = new JTextField();
		textField.setToolTipText(
				"Use a semi-colon (;) to seperate conditions.\r\n(name=\"joe\";id=123) and a percent sign for\r\nwildcard search (email=%@mail.net%)");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		gbc_textField.gridwidth = 3;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblSelect = new JLabel("Select");
		GridBagConstraints gbc_lblSelect = new GridBagConstraints();
		gbc_lblSelect.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelect.gridx = 1;
		gbc_lblSelect.gridy = 4;
		getContentPane().add(lblSelect, gbc_lblSelect);

		Vector<String> attributes = new Vector<String>();
		attributes = (Vector<String>) metaData[0];
		JList<String> list = new JList<String>(new DefaultListModel<String>());
		attributes.forEach((attr) -> {
			((DefaultListModel<String>) list.getModel()).addElement(attr);
		});

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 3;
		gbc_list.gridheight = 5;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 5;
		getContentPane().add(list, gbc_list);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnLogout = new JMenu("Connection");
		menuBar.add(mnLogout);

		JMenuItem mntmTestConnection = new JMenuItem("Test Connection");
		mntmTestConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SQLConnection.testConnection();
			}
		});
		mnLogout.add(mntmTestConnection);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnLogout.add(mntmLogout);

		JMenu mnQuery = new JMenu("Query");
		menuBar.add(mnQuery);

		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand() + " done.");
				new ConcurrentHashMap<String, String>();
				List<String> sqlCustomSELECT = list.getSelectedValuesList();
				String sqlCustomWHERE = textField.getText();
				System.out.println("creating sql statement...");
				StringBuilder sbWHERE = new StringBuilder();
				if (!sqlCustomWHERE.isEmpty()) {
					String[] reWHERE = sqlCustomWHERE.split(";"); // all WHERE
					System.out.println(reWHERE.toString().length());
					if (reWHERE.length != 0) {
						sbWHERE.append("WHERE ");
						for (int index = 0; index < reWHERE.length; index++) {
							if (index < (reWHERE.length) - 1) {
								sbWHERE.append(reWHERE[index] + " AND ");
							}
							if (index == (reWHERE.length) - 1) {
								sbWHERE.append(reWHERE[index] + " ");
							}
						}
					} else if (reWHERE.length == 0) {
						sbWHERE.append("");
					}
				} else {
					sbWHERE.append("");
				}

				String WHERE = sbWHERE.toString();
				System.out.println(WHERE);
				System.out.println("\n\n");

				StringBuilder sbSELECT = new StringBuilder();
				sbSELECT.append("SELECT ");
				if (sqlCustomSELECT.isEmpty()) {
					sbSELECT.append(" * ");
				} else {
					for (int index = 0; index < sqlCustomSELECT.size(); index++) {
						if (index < sqlCustomSELECT.size() - 1) {
							sbSELECT.append(sqlCustomSELECT.get(index).toString() + ", ");
						}
						if (index == sqlCustomSELECT.size() - 1) {
							sbSELECT.append(sqlCustomSELECT.get(index).toString() + " ");
						}
					}
				}

				StringBuilder sbQuery = new StringBuilder();
				sbQuery.append(sbSELECT.toString()).append(FROM).append(" ").append(sbWHERE.toString()).append(";");

				System.out.println("QUERY: " + sbQuery.toString());
				Object[] metaData = SQLConnection.getMetaData(FROMTable, sbQuery.toString());
				System.out.println("metadata: " + metaData[0] + ";" + metaData[1] + ";" + metaData[2]);
				table.setModel(makeTableModel((ResultSet) metaData[2]));
				System.out.println(table.toString());
				table.setAutoCreateRowSorter(true);
				table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()) {
				});
			}
		});
		System.out.println("table refreshed?");
		mnQuery.add(mntmRefresh);

		JMenuItem mntmReset = new JMenuItem("Reset");
		mntmReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.clearSelection();
				textField.setText(null);
				// execute empty query
				String sqlQuery = "SELECT * " + FROM;

				Object[] metaData = SQLConnection.getMetaData(FROMTable, sqlQuery);
				table.setModel(makeTableModel((ResultSet) metaData[2]));
			}
		});
		mnQuery.add(mntmReset);

		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/blood-drop-icon.png")));
		setTitle("SAFE Blood DBMS");
		pack();
		setVisible(true);
	}

	public static DefaultTableModel makeTableModel(ResultSet rs) {
		try {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			System.out.println("metadata.length " + rs.getMetaData().getColumnCount());
			Vector<String> columnNames = new Vector<String>();
			for (int column = 1; column <= rsMetaData.getColumnCount(); column++) {
				columnNames.add(rsMetaData.getColumnName(column));
			}
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= rsMetaData.getColumnCount(); columnIndex++) {
					vector.add(rs.getObject(columnIndex));
				}
				System.out.println("vector: " + vector);
				data.add(vector);
			}
			System.out.println("data: " + data);
			return new DefaultTableModel(data, columnNames);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Vector<String> GetAttributeNames(ResultSet rs) {
		ResultSetMetaData rsMetaData;
		try {
			rsMetaData = rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();

			for (int column = 1; column <= rsMetaData.getColumnCount(); column++) {
				columnNames.add(rsMetaData.getColumnName(column));
			}
			return columnNames;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
