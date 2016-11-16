import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ViewListFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ViewListFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 123, 0, 114, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnEmployees = new JButton("Employees");
		btnEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SQLView("employee");
			}
		});
		GridBagConstraints gbc_btnEmployees = new GridBagConstraints();
		gbc_btnEmployees.insets = new Insets(0, 0, 5, 5);
		gbc_btnEmployees.gridx = 0;
		gbc_btnEmployees.gridy = 2;
		panel.add(btnEmployees, gbc_btnEmployees);

		JButton btnBranches = new JButton("Branches");
		GridBagConstraints gbc_btnBranches = new GridBagConstraints();
		gbc_btnBranches.insets = new Insets(0, 0, 5, 5);
		gbc_btnBranches.gridx = 2;
		gbc_btnBranches.gridy = 2;
		btnBranches.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLView("branch");

			}
		});
		panel.add(btnBranches, gbc_btnBranches);

		JButton btnBloodRequests = new JButton("Blood Requests");
		GridBagConstraints gbc_btnBloodRequests = new GridBagConstraints();
		gbc_btnBloodRequests.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBloodRequests.insets = new Insets(0, 0, 5, 0);
		gbc_btnBloodRequests.gridx = 4;
		gbc_btnBloodRequests.gridy = 2;
		btnBloodRequests.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLView("request");

			}
		});
		panel.add(btnBloodRequests, gbc_btnBloodRequests);

		JButton btnHealtcareProv = new JButton("HealthCare Prov");
		GridBagConstraints gbc_btnHealtcareProv = new GridBagConstraints();
		gbc_btnHealtcareProv.insets = new Insets(0, 0, 5, 5);
		gbc_btnHealtcareProv.gridx = 0;
		gbc_btnHealtcareProv.gridy = 5;
		btnHealtcareProv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLView("hc_prov");

			}
		});
		panel.add(btnHealtcareProv, gbc_btnHealtcareProv);

		JButton btnBlood = new JButton("Blood");
		GridBagConstraints gbc_btnBlood = new GridBagConstraints();
		gbc_btnBlood.insets = new Insets(0, 0, 5, 5);
		gbc_btnBlood.gridx = 2;
		gbc_btnBlood.gridy = 5;
		btnBlood.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLView("blood");

			}
		});
		panel.add(btnBlood, gbc_btnBlood);

		JButton btnDonors = new JButton("Donors");
		GridBagConstraints gbc_btnDonors = new GridBagConstraints();
		gbc_btnDonors.insets = new Insets(0, 0, 5, 0);
		gbc_btnDonors.gridx = 4;
		gbc_btnDonors.gridy = 5;
		btnDonors.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLView("donor");

			}
		});
		panel.add(btnDonors, gbc_btnDonors);

		JButton btnReports = new JButton("Reports");
		GridBagConstraints gbc_btnReports = new GridBagConstraints();
		gbc_btnReports.insets = new Insets(0, 0, 0, 5);
		gbc_btnReports.gridx = 2;
		gbc_btnReports.gridy = 7;
		btnReports.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ReportList();

			}
		});
		panel.add(btnReports, gbc_btnReports);

		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/blood-drop-icon.png")));

		setTitle("SAFE Blood DBMS");
		setVisible(true);
		pack();
	}

}
