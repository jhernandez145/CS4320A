import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

public class ReportList extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ReportList() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnRequesthcprovider = new JButton("Request/HC_Provider");
		btnRequesthcprovider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReportView(SQLConnection.getRequestByHC_Provider());
			}
		});

		JButton btnDonationbranch = new JButton("Donation/Branch");
		btnDonationbranch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReportView(SQLConnection.getDonationPerIndividual());
			}
		});
		GridBagConstraints gbc_btnDonationbranch = new GridBagConstraints();
		gbc_btnDonationbranch.fill = GridBagConstraints.BOTH;
		gbc_btnDonationbranch.insets = new Insets(0, 0, 5, 5);
		gbc_btnDonationbranch.gridx = 0;
		gbc_btnDonationbranch.gridy = 0;
		panel.add(btnDonationbranch, gbc_btnDonationbranch);


		JButton btnDonationdonor = new JButton("Donation/Donor");
		GridBagConstraints gbc_btnDonationdonor = new GridBagConstraints();
		gbc_btnDonationdonor.fill = GridBagConstraints.BOTH;
		gbc_btnDonationdonor.insets = new Insets(0, 0, 5, 0);
		gbc_btnDonationdonor.gridx = 2;
		gbc_btnDonationdonor.gridy = 0;
		btnDonationdonor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ReportView(SQLConnection.getDonationPerIndividual());
			}
		});
		
				JButton btnBloodType = new JButton("Blood Type");
				btnBloodType.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new ReportView(SQLConnection.getBloodTypeReport());
					}
				});
				GridBagConstraints gbc_btnBloodType = new GridBagConstraints();
				gbc_btnBloodType.fill = GridBagConstraints.BOTH;
				gbc_btnBloodType.insets = new Insets(0, 0, 5, 5);
				gbc_btnBloodType.gridx = 1;
				gbc_btnBloodType.gridy = 0;
				panel.add(btnBloodType, gbc_btnBloodType);
		panel.add(btnDonationdonor, gbc_btnDonationdonor);
		
				JButton btnInventoryReport = new JButton("Inventory/Branch");
				btnInventoryReport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new ReportView(SQLConnection.getInventoryReport());
					}
				});
				GridBagConstraints gbc_btnInventoryReport = new GridBagConstraints();
				gbc_btnInventoryReport.fill = GridBagConstraints.BOTH;
				gbc_btnInventoryReport.insets = new Insets(0, 0, 0, 5);
				gbc_btnInventoryReport.gridx = 0;
				gbc_btnInventoryReport.gridy = 1;
				panel.add(btnInventoryReport, gbc_btnInventoryReport);
		GridBagConstraints gbc_btnRequesthcprovider = new GridBagConstraints();
		gbc_btnRequesthcprovider.fill = GridBagConstraints.BOTH;
		gbc_btnRequesthcprovider.gridx = 2;
		gbc_btnRequesthcprovider.gridy = 1;
		panel.add(btnRequesthcprovider, gbc_btnRequesthcprovider);

		pack();
		setVisible(true);
	}

}
