import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class DonationPerIndividualFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public DonationPerIndividualFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		
		
		table = new JTable(makeTableModel(SQLConnection.getDonationPerIndividual()));
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel) table.getModel());
		table.setRowSorter(sorter);

		scrollPane.setViewportView(table);
		
		
		panel.add(scrollPane, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	
	public static DefaultTableModel makeTableModel(ResultSet rs) {
		try {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			for (int column = 1; column <= rsMetaData.getColumnCount(); column++) {
				columnNames.add(rsMetaData.getColumnName(column));
			}
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= rsMetaData.getColumnCount(); columnIndex++) {
					if (rsMetaData.getColumnName(columnIndex).equals("Salary")) {
						if (SQLConnection.isManager((int) rs.getObject("employeeID"), (int) rs.getObject("mgrID"))) {
							vector.add(rs.getObject(columnIndex));
						} else {
							vector.add("xxx.xx");
						}
					} else {
						vector.add(rs.getObject(columnIndex));
					}

				}
				data.add(vector);
			}
			return new DefaultTableModel(data, columnNames);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}

