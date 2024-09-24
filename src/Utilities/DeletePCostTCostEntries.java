package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class DeletePCostTCostEntries extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = -6517018477299344044L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlNorth1;
	JPanel pnlSouth;

	static _JLookup lookupCompany;
	_JLookup lookupProject;
	static _JLookup lookupCompany1;
	_JLookup lookupProject1;
	_JLookup lookupBatch;
	_JLookup lookupBatch1;

	JButton btnDelete;
	JButton btnCancel;

	JTextField txtCompany;
	JTextField txtProject;
	JTextField txtCompany1;
	JTextField txtProject1;

	JTabbedPane tabNorth;

	JComboBox cmbDeleteBy;
	JComboBox cmbDeleteBy1;

	private static String co_id;
	private static String projcode;

	static String title = "Delete PCost/TCost Entries";
	Dimension frameSize = new Dimension(500, 240);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public DeletePCostTCostEntries() {
		super(title, false, true, false, true);
		initGUI();
	}

	public DeletePCostTCostEntries(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public DeletePCostTCostEntries(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(10, 10));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				tabNorth = new JTabbedPane();
				pnlMain.add(tabNorth, BorderLayout.NORTH);
				tabNorth.setPreferredSize(new Dimension(500, 140));// XXX
				{
					JPanel pnlWest1 = new JPanel(new BorderLayout(5, 5));
					tabNorth.addTab("Processing Cost", null, pnlWest1, null);
					pnlWest1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlLabel = new JPanel(new GridLayout(3, 0, 5, 5));
						pnlWest1.add(pnlLabel, BorderLayout.WEST);
						{
							JLabel lblCompany = new JLabel("Company:");
							pnlLabel.add(lblCompany);
							lblCompany.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblProject = new JLabel("Project:");
							pnlLabel.add(lblProject);
							lblProject.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblClass = new JLabel("Delete By:");
							pnlLabel.add(lblClass);
							lblClass.setHorizontalAlignment(JLabel.RIGHT);
						}
					}
					{
						JPanel pnlText = new JPanel(new GridLayout(3, 0, 5, 5));
						pnlWest1.add(pnlText, BorderLayout.CENTER);
						{
							JPanel pnlLookup1 = new JPanel(new BorderLayout(5, 5));
							pnlText.add(pnlLookup1);
							{
								lookupCompany = new _JLookup(null, "Company");
								pnlLookup1.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setReturnColumn(0);
								lookupCompany.setPreferredSize(new Dimension(50, 20));
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											txtCompany.setText(data[1].toString());
											lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));
											co_id = (String) data[0];
											KEYBOARD_MANAGER.focusNextComponent();
										}else {
											txtCompany.setText("");
										}
									}
								});
								{
									txtCompany = new JTextField();
									pnlLookup1.add(txtCompany,BorderLayout.CENTER);
									txtCompany.setHorizontalAlignment(JLabel.LEFT);
									txtCompany.setEditable(false);
									txtCompany.setPreferredSize(new Dimension(50, 20));
								}
							}
						}
						{
							JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlLookup);
							{
								lookupProject = new _JLookup(null, "Project", "Please select Company.");
								pnlLookup.add(lookupProject, BorderLayout.WEST);
								lookupProject.setLookupSQL(SQL_PROJECT());
								lookupProject.setReturnColumn(0);
								lookupProject.setPreferredSize(new Dimension(50, 20));
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											projcode = (String) data[0];
											txtProject.setText(data[1].toString());

											KEYBOARD_MANAGER.focusNextComponent();
										}else {
											txtProject.setText("");
										}
									}
								});
								{
									txtProject = new JTextField();
									pnlLookup.add(txtProject,BorderLayout.CENTER);
									txtProject.setHorizontalAlignment(JLabel.LEFT);
									txtProject.setEditable(false);
								}
							}
						}
						{
							JPanel pnlcmb = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlcmb);
							{
								cmbDeleteBy = new JComboBox(new DefaultComboBoxModel(getClass1()));
								pnlcmb.add(cmbDeleteBy, BorderLayout.WEST);
								cmbDeleteBy.setPreferredSize(new Dimension(120, 20));
								cmbDeleteBy.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (cmbDeleteBy.getSelectedIndex() == 0) {
											lookupBatch.setLookupSQL(null);
											btnDelete.setEnabled(false);
										}
										if (cmbDeleteBy.getSelectedIndex() == 1) {
											lookupBatch.setLookupSQL(SQL_BATCH());
											btnDelete.setEnabled(true);
										}
										if (cmbDeleteBy.getSelectedIndex() == 2) {
											lookupBatch.setLookupSQL(SQL_RPLF());
											btnDelete.setEnabled(true);
										}
									}
								});
								{
									lookupBatch = new _JLookup(null, "Batch");
									pnlcmb.add(lookupBatch, BorderLayout.CENTER);
									lookupBatch.setReturnColumn(0);
									lookupBatch.setPreferredSize(new Dimension(50, 20));
									lookupBatch.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											FncSystem.out("Display LookupBatch SQL", lookupBatch.getLookupSQL());

										}
									});
								}
							}
						}
					}
				}
				{
					pnlNorth1 = new JPanel(new BorderLayout(5, 5));
					tabNorth.addTab("Transfer Cost", null, pnlNorth1, null);
					pnlNorth1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlLabel = new JPanel(new GridLayout(3, 0, 5, 5));
						pnlNorth1.add(pnlLabel, BorderLayout.WEST);
						{
							JLabel lblCompany = new JLabel("Company:");
							pnlLabel.add(lblCompany);
							lblCompany.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblProject = new JLabel("Project:");
							pnlLabel.add(lblProject);
							lblProject.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblClass = new JLabel("Delete By:");
							pnlLabel.add(lblClass);
							lblClass.setHorizontalAlignment(JLabel.RIGHT);
						}
					}
					{
						JPanel pnlText = new JPanel(new GridLayout(3, 0, 5, 5));
						pnlNorth1.add(pnlText, BorderLayout.CENTER);
						{
							JPanel pnlLookup1 = new JPanel(new BorderLayout(5, 5));
							pnlText.add(pnlLookup1);
							{
								lookupCompany1 = new _JLookup(null, "Company");
								pnlLookup1.add(lookupCompany1, BorderLayout.WEST);
								lookupCompany1.setLookupSQL(SQL_COMPANY());
								lookupCompany1.setReturnColumn(0);
								lookupCompany1.setPreferredSize(new Dimension(50, 20));
								lookupCompany1.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											co_id = (String) data[0];
											txtCompany1.setText(data[1].toString());
											lookupProject1.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));
											lookupProject1.setLookupSQL(SQL_PROJECT((String) data[0]));
											KEYBOARD_MANAGER.focusNextComponent();
										}else {
											txtCompany1.setText("");
										}
									}
								});
								{
									txtCompany1 = new JTextField();
									pnlLookup1.add(txtCompany1,BorderLayout.CENTER);
									txtCompany1.setHorizontalAlignment(JLabel.LEFT);
									txtCompany1.setEditable(false);
									txtCompany1.setPreferredSize(new Dimension(50, 20));
								}
							}
						}
						{
							JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlLookup);
							{
								lookupProject1 = new _JLookup(null, "Project", "Please select Company.");
								pnlLookup.add(lookupProject1, BorderLayout.WEST);
								lookupProject1.setReturnColumn(0);
								lookupProject1.setPreferredSize(new Dimension(50, 20));
								lookupProject1.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											projcode = (String) data[0];
											txtProject1.setText(data[1].toString());

											KEYBOARD_MANAGER.focusNextComponent();
										}else {
											txtProject1.setText("");
										}
									}
								});
								{
									txtProject1 = new JTextField();
									pnlLookup.add(txtProject1,BorderLayout.CENTER);
									txtProject1.setHorizontalAlignment(JLabel.LEFT);
									txtProject1.setEditable(false);
								}
							}
						}
						{
							JPanel pnlcmb = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlcmb);
							{
								cmbDeleteBy1 = new JComboBox(new DefaultComboBoxModel(getClass1()));
								pnlcmb.add(cmbDeleteBy1, BorderLayout.WEST);
								cmbDeleteBy1.setPreferredSize(new Dimension(120, 20));
								cmbDeleteBy1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (cmbDeleteBy1.getSelectedIndex() == 0) {
											lookupBatch1.setLookupSQL(null);
											btnDelete.setEnabled(false);
										}
										if (cmbDeleteBy1.getSelectedIndex() == 1) {
											lookupBatch1.setLookupSQL(SQL_BATCH1());
											btnDelete.setEnabled(true);
											System.out.println("SQL_BATCH1");
										}
										if (cmbDeleteBy1.getSelectedIndex() == 2) {
											lookupBatch1.setLookupSQL(SQL_RPLF1());
											btnDelete.setEnabled(true);
											System.out.println("SQL_RPLF1");
										}
									}
								});
								{
									lookupBatch1 = new _JLookup(null, "Batch");
									pnlcmb.add(lookupBatch1, BorderLayout.CENTER);
									lookupBatch1.setReturnColumn(0);
									lookupBatch1.setPreferredSize(new Dimension(50, 20));
									lookupBatch1.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											FncSystem.out("Display LookupBatch SQL", lookupBatch1.getLookupSQL());

										}
									});
								}
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				pnlSouth.setPreferredSize(new Dimension(500, 50));// XXX
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnDelete = new JButton("Delete");
					pnlSouth.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.setMnemonic(KeyEvent.VK_P);
					btnDelete.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		btnDelete.setEnabled(false);
	}
	private String[] getClass1() {
		return new String[] {
				"",
				"Batch No. Only",
				"With RPLF No.",
		};
	}

	private Boolean withExistingPV(String rplf_no, String co_id) {

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM rf_pv_header where pv_no = '"+rplf_no+"' and co_id = '"+co_id+"'";
		db.select(SQL);

		if(db.isNotNull()) {
			return true;
		}else {
			return false;
		}
	}


	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Delete")){

			if (tabNorth.getSelectedIndex() == 0) {
				if (cmbDeleteBy.getSelectedIndex() == 1) {
					if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION, 
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						/*String SQL = "UPDATE rf_request_header SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
						"WHERE rplf_no in (SELECT rplf_no FROM rf_processing_cost WHERE batch_no = '"+lookupBatch.getValue()+"') \n";

						pgUpdate db = new pgUpdate();
						db.executeUpdate(SQL, false);
						db.commit();

						String SQL1 = "UPDATE rf_request_detail SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
								"WHERE rplf_no in (SELECT rplf_no FROM rf_processing_cost WHERE batch_no = '"+lookupBatch.getValue()+"') \n";

						pgUpdate db1 = new pgUpdate();
						db1.executeUpdate(SQL1, false);
						db1.commit();*/

						String SQL2 = "UPDATE rf_processing_cost SET status_id = 'I'\n" +
								"WHERE batch_no = '"+lookupBatch.getValue()+"' and co_id ='"+co_id+"'\n";

						pgUpdate db2 = new pgUpdate();
						db2.executeUpdate(SQL2, false);
						db2.commit();

						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Delete.", "DELETED", JOptionPane.INFORMATION_MESSAGE);

						lookupProject.setText(" ");
						refreshPCost();

					}
				}
				if (cmbDeleteBy.getSelectedIndex() == 2) {

					if(withExistingPV(lookupBatch.getValue(), co_id) == false) {
						if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION, 
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							String SQL = "UPDATE rf_request_header SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
									"WHERE rplf_no in (SELECT rplf_no FROM rf_processing_cost WHERE rplf_no = '"+lookupBatch.getValue()+"' and co_id ='"+co_id+"')  \n";

							FncSystem.out("DISPLAY rf_processing_cost UPDATE", SQL);

							pgUpdate db = new pgUpdate();
							db.executeUpdate(SQL, false);
							db.commit();

							String SQL1 = "UPDATE rf_request_detail SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
									"WHERE rplf_no in (SELECT rplf_no FROM rf_processing_cost WHERE rplf_no = '"+lookupBatch.getValue()+"' and co_id ='"+co_id+"') \n";

							FncSystem.out("DISPLAY rf_processing_cost UPDATE", SQL1);

							pgUpdate db1 = new pgUpdate();
							db1.executeUpdate(SQL1, false);
							db1.commit();

							String SQL2 = "UPDATE rf_processing_cost SET status_id = 'I'\n" +
									"WHERE rplf_no in (SELECT rplf_no FROM rf_processing_cost WHERE rplf_no = '"+lookupBatch.getValue()+"' and co_id ='"+co_id+"')\n";

							FncSystem.out("DISPLAY rf_processing_cost UPDATE", SQL2);

							pgUpdate db2 = new pgUpdate();
							db2.executeUpdate(SQL2, false);
							db2.commit();

							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Delete.", "DELETED", JOptionPane.INFORMATION_MESSAGE);

							lookupProject.setText(" ");
							refreshPCost();
						}
					}else {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot delete rplf because it has existing PV", "Delete", JOptionPane.WARNING_MESSAGE);
					}
				}
			}

			if (tabNorth.getSelectedIndex() == 1) {

				if(withExistingPV(lookupBatch.getValue(), co_id) == false) {
					if (cmbDeleteBy1.getSelectedIndex() == 1) {
						if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION, 
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							System.out.println("UNANG DINAANAN!!!!!!!!!!!!!!!!!!!!!");

							String SQL = "UPDATE rf_request_header SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
									"WHERE rplf_no in (SELECT rplf_no FROM rf_transfer_cost WHERE batch_no = '"+lookupBatch1.getValue()+"' and co_id ='"+co_id+"') \n";

							pgUpdate db = new pgUpdate();
							db.executeUpdate(SQL, false);
							db.commit();

							FncSystem.out("UNA", SQL);
							System.out.println("PANGALAWANG DINAANAN!!!!!!!!!!!!!!!!!!!!!");

							String SQL1 = "UPDATE rf_request_detail SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
									"WHERE rplf_no in (SELECT rplf_no FROM rf_transfer_cost WHERE batch_no = '"+lookupBatch1.getValue()+"' and co_id ='"+co_id+"') \n";

							pgUpdate db1 = new pgUpdate();
							db1.executeUpdate(SQL1, false);
							db1.commit();

							FncSystem.out("PANGALAWA", SQL1);
							System.out.println("HULING DINAANAN!!!!!!!!!!!!!!!!!!!!!");

							String SQL2 = "UPDATE rf_transfer_cost SET status_id = 'I'\n" +
									"WHERE batch_no = '"+lookupBatch1.getValue()+"' and co_id ='"+co_id+"'\n";

							FncSystem.out("PANGATLO", SQL2);
							pgUpdate db2 = new pgUpdate();
							db2.executeUpdate(SQL2, false);
							db2.commit();

							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Delete.", "DELETED", JOptionPane.INFORMATION_MESSAGE);

							lookupProject1.setText(" ");
							refreshTCost();	
						}
					}
					if (cmbDeleteBy1.getSelectedIndex() == 2) {
						if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION, 
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							String SQL = "UPDATE rf_request_header SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
									"WHERE rplf_no = '"+lookupBatch1.getValue()+"' and co_id ='"+co_id+"'\n";

							FncSystem.out("DISPLAY rf_request_header UPDATE", SQL);

							pgUpdate db = new pgUpdate();
							db.executeUpdate(SQL, false);
							db.commit();

							String SQL1 = "UPDATE rf_request_detail SET status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
									"WHERE rplf_no = '"+lookupBatch1.getValue()+"' and co_id ='"+co_id+"'\n";

							FncSystem.out("DISPLAY rf_request_detail UPDATE", SQL1);

							pgUpdate db1 = new pgUpdate();
							db1.executeUpdate(SQL1, false);
							db1.commit();

							String SQL2 = "UPDATE rf_transfer_cost SET status_id = 'I'\n" +
									"WHERE rplf_no = '"+lookupBatch1.getValue()+"' and co_id ='"+co_id+"'\n";

							FncSystem.out("DISPLAY rf_transfer_cost UPDATE", SQL2);


							pgUpdate db2 = new pgUpdate();
							db2.executeUpdate(SQL2, false);
							db2.commit();

							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Delete.", "DELETED", JOptionPane.INFORMATION_MESSAGE);

							lookupProject1.setText(" ");
							refreshTCost();

						}
					}
				}else {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot delete rplf because it has existing PV", "Delete", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if(actionCommand.equals("Cancel")){

			if (tabNorth.getSelectedIndex() == 0) {
				int response = JOptionPane.showConfirmDialog(DeletePCostTCostEntries.this.getTopLevelAncestor(),"Are you sure you want to cancel?   ",
						"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					lookupProject.setText(" ");
					refreshPCost();
				}
			}

			if (tabNorth.getSelectedIndex() == 1) {
				int response = JOptionPane.showConfirmDialog(DeletePCostTCostEntries.this.getTopLevelAncestor(),"Are you sure you want to cancel?   ",
						"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					lookupProject1.setText(" ");
					refreshTCost();
				}
			}
		}
	}
	private static String SQL_BATCH() {
		return "SELECT batch_no as \"Batch No.\"\n" + 
				"FROM rf_processing_cost \n" +
				"WHERE status_id = 'A'\n" +
				"AND rplf_no = ''\n" +
				"AND co_id = '"+ co_id +"'\n" +
				"AND (CASE WHEN (NULLIF('"+projcode+"','null') IS NULL) THEN TRUE ELSE projcode = '"+projcode+"' END)\n" + 
				"Group by batch_no \n" +
				"ORDER BY batch_no DESC;";
	}
	private static String SQL_RPLF() {
		String SQL = "SELECT a.rplf_no as \"RPLF No.\"\n" + 
				"FROM rf_processing_cost a \n" + 
				"LEFT JOIN rf_request_header b ON a.rplf_no = b.rplf_no AND a.rplf_no not in (select rplf_no from rf_pv_header where status_id != 'I' and co_id = '"+co_id+"' ) \n" + 
				"WHERE b.status_id = 'A'\n" + 
				"AND a.rplf_no not in (select rplf_no from rf_pv_header where status_id != 'I' and co_id = '"+co_id+"')\n" + 
				"AND (CASE WHEN (NULLIF('"+projcode+"','null') IS NULL) THEN TRUE ELSE a.projcode = '"+projcode+"' END)\n" + 
				"Group by a.rplf_no, a.batch_no \n" + 
				"ORDER BY a.rplf_no DESC;";

		FncSystem.out("DISPLAY SQL_RPLF", SQL);
		return SQL;
	}
	private static String SQL_BATCH1() {

		String SQL = "SELECT a.batch_no as \"Batch No.\"\n" + 
				"FROM rf_transfer_cost a \n" + 
				"WHERE a.status_id = 'A'\n" + 
				"AND a.co_id = '"+co_id+"'\n" + 
				"AND a.rplf_no = ''\n" + 
				"AND (CASE WHEN (NULLIF('"+projcode+"','null') IS NULL) THEN TRUE ELSE a.projcode = '"+projcode+"' END)\n" + 
				"Group by a.batch_no \n" + 
				"ORDER BY a.batch_no DESC;";

		FncSystem.out("DISPLAY SQL_BATCH1", SQL);
		return SQL;
	}

	private static String SQL_RPLF1() {
		String SQL = "SELECT a.rplf_no as \"RPLF No.\"\n" + 
				"FROM rf_transfer_cost a \n" +
				"LEFT JOIN rf_request_header b ON a.rplf_no = b.rplf_no AND a.rplf_no not in (select rplf_no from rf_pv_header where status_id != 'I' and co_id = '"+co_id+"' ) \n" +
				"WHERE b.status_id = 'A'\n" +
				"AND a.rplf_no not in (select rplf_no from rf_pv_header where status_id != 'I' and co_id = '"+co_id+"')\n" +
				"AND (CASE WHEN (NULLIF('"+projcode+"','null') IS NULL) THEN TRUE ELSE a.projcode = '"+projcode+"' END)\n" + 
				"Group by a.rplf_no, a.batch_no \n" +
				"ORDER BY a.rplf_no DESC;";

		FncSystem.out("DISPLAY SQL_RPLF1", SQL);
		return SQL;
	}
	private void refreshPCost() {

		if (lookupProject.getText().equals(" ")) {
			co_id = null;
			projcode = null;
			lookupCompany.setValue(null);
			lookupProject.setValue(null);
			lookupBatch.setValue(null);
			txtCompany.setText(null);
			txtProject.setText(" ");
			cmbDeleteBy.setSelectedItem("");
			btnDelete.setEnabled(false);
		}
	} 
	private void refreshTCost() {

		if (lookupProject1.getText().equals(" ")) {
			co_id = null;
			projcode = null;
			lookupCompany1.setValue(null);
			lookupProject1.setValue(null);
			lookupBatch1.setValue(null);
			txtCompany1.setText(null);
			txtProject1.setText(" ");
			cmbDeleteBy1.setSelectedItem("");
			btnDelete.setEnabled(false);
		}
	} 
}