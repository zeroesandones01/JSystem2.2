package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class SummaryofDeposits extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Summary of Daily Deposits";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	private JPanel pnlA;
	private JPanel pnlB;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblSortby;
	private JLabel lblBranch;
	private JLabel lblCashier;
	private JLabel lblFilterby;

	private _JLookup lookupCompany;
	private _JLookup lookupBranch;
	private _JLookup lookupCashier;

	private JTextField txtCompany;
	private JTextField txtBranch;
	private JTextField txtCashier;

	private JButton btnPreview;
	private JButton btnCancel;

	String company;
	String company_logo;
	String co_id;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbSortby;
	private JComboBox cmbFilterby;

	public SummaryofDeposits() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SummaryofDeposits(String title) {
		super(title);
		initGUI();
	}

	public SummaryofDeposits(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(562, 361));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(557, 247));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 193));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));// XXX
				{
					pnlNorthWest = new JPanel(new GridLayout(3, 2, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorthWest.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									company = (String) data[1];
									company_logo = (String) data[3];

									String name = (String) data[1];
									txtCompany.setText(name);
									lookupBranch.setLookupSQL(getBranch());
									lookupCashier.setLookupSQL(getCashier());

									KEYBOARD_MANAGER.focusNextComponent();
									btnPreview.setEnabled(true);
									btnCancel.setEnabled(true);
									enableFields(true);
								}
							}
						});
					}
					{
						lblBranch = new JLabel("Branch", JLabel.TRAILING);
						pnlNorthWest.add(lblBranch);
						lblBranch.setEnabled(false);
					}
					{
						lookupBranch = new _JLookup(null, "Branch");
						pnlNorthWest.add(lookupBranch);
						lookupBranch.setReturnColumn(0);
						lookupBranch.setEnabled(false);
						lookupBranch.setLookupSQL(SQL_COMPANY());
						lookupBranch.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									String name = (String) data[1];
									txtBranch.setText(name);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblCashier = new JLabel("Cashier", JLabel.TRAILING);
						pnlNorthWest.add(lblCashier);
						lblCashier.setEnabled(false);
					}
					{
						lookupCashier = new _JLookup(null, "Cashier");
						pnlNorthWest.add(lookupCashier);
						lookupCashier.setReturnColumn(0);
						lookupCashier.setEnabled(false);
						lookupCashier.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									String name = (String) data[1];
									txtCashier.setText(name);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
				}
				pnlNorthEast = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtBranch = new JTextField();
					pnlNorthEast.add(txtBranch, BorderLayout.CENTER);
					txtBranch.setEditable(false);
				}
				{
					txtCashier = new JTextField();
					pnlNorthEast.add(txtCashier, BorderLayout.CENTER);
					txtCashier.setEditable(false);
					txtCashier.setEnabled(false);
				}

				pnlCenter2 = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlNorth.add(pnlCenter2, BorderLayout.SOUTH);
				pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
				pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Cashiering Date"));
				{
					pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCenter2.add(pnlCriteria2, BorderLayout.WEST);

					{
						lblDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
						pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}

					lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
					pnlCriteria2.add(lblDateTo);
					lblDateTo.setEnabled(true);
				}
				{
					dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCriteria2.add(dateDateTo, BorderLayout.EAST);
					dateDateTo.setBounds(485, 7, 125, 21);
					dateDateTo.setDate(null);
					dateDateTo.setEnabled(true);
					dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}
			}

			{
				pnlCenter = new JPanel(new GridLayout(2, 2, 5, 5)); // 1, 2
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new java.awt.Dimension(499, 50));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Option"));
				{
					pnlA = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlCenter.add(pnlA, BorderLayout.EAST);
					pnlA.setPreferredSize(new java.awt.Dimension(142, 100));
					{
						lblSortby = new JLabel("Status");
						pnlA.add(lblSortby);
						lblSortby.setEnabled(true);
					}
					{
						lblFilterby = new JLabel("Date");
						pnlA.add(lblFilterby);
						lblFilterby.setEnabled(true);
					}

					pnlB = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlCenter.add(pnlB, BorderLayout.CENTER);
					pnlB.setPreferredSize(new java.awt.Dimension(300, 159));

					String status[] = { "Posted", "Active", "Cancelled", "All" };
					cmbSortby = new JComboBox(status);
					pnlB.add(cmbSortby);
					cmbSortby.setSelectedItem(null);
					cmbSortby.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 13));
					cmbSortby.setBounds(537, 15, 190, 21);
					cmbSortby.setEnabled(true);
					cmbSortby.setPreferredSize(new java.awt.Dimension(131, 80));
					cmbSortby.setSelectedIndex(0);

					String status2[] = { "CDR Date", "Deposit Date" };
					cmbFilterby = new JComboBox(status2);
					pnlB.add(cmbFilterby);
					cmbFilterby.setSelectedItem(null);
					cmbFilterby.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 13));
					cmbFilterby.setBounds(537, 15, 180, 21);
					cmbFilterby.setEnabled(true);
					cmbFilterby.setSelectedIndex(0);

				}
			}

			{
				pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setEnabled(false);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupBranch, lookupCashier,
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 562, 361); // 174

		// added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	private String getBranch() {
		return "SELECT branch_id, branch_name, branch_alias FROM mf_office_branch;";
	}

	private String getCashier() {
		return "SELECT a.emp_code, (upper(trim(b.first_name))||' '||upper(trim(b.middle_name))||' '||upper(trim(b.last_name))) as cashier "
				+ "FROM (select * from em_employee where dept_code in ( '04', '80' ) ) a "
				+ "LEFT JOIN rf_entity b on a.entity_id = b.entity_id  " + "order by b.first_name \r\n";
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Preview")) {
			String detailed = "Summary of Daily Deposits";
			String branch = "", date = "", status = "";
			if (lookupBranch.getValue() == null || lookupBranch.getValue().equals("")) {
				branch = "ALL";
			} else {
				branch = txtBranch.getText();
			}
			if ((String) cmbFilterby.getSelectedItem() == "Deposit Date") {
				date = "date_deposited";
			} else {
				date = "date_posted";
			}
			if ((String) cmbSortby.getSelectedItem() == "Posted") {
				status = "P";
			} else if ((String) cmbSortby.getSelectedItem() == "Active") {
				status = "A";
			} else if ((String) cmbSortby.getSelectedItem() == "Cancelled") {
				status = "X";
			} else if ((String) cmbSortby.getSelectedItem() == "All") {
				status = "All";
			}

			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), detailed.toUpperCase());

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("branch_id", lookupBranch.getValue());
			mapParameters.put("branch", branch);
			mapParameters.put("date", date);
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("cashier", txtCashier.getText());
			mapParameters.put("status", status);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("prepared_name", UserInfo.FullName);
			mapParameters.put("date_from", dteDateFrom.getDate());
			mapParameters.put("date_to", dateDateTo.getDate());
			mapParameters.put("based_on", cmbFilterby.getSelectedItem());

			FncReport.generateReport("/Reports/rptSummaryofDeposits.jasper", reportTitle, "", mapParameters);

		}

		if (e.getActionCommand().equals("Cancel")) {
			lookupCompany.setText("");
			txtCompany.setText("");
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			cmbSortby.setSelectedIndex(0);
			cmbFilterby.setSelectedIndex(0);
			btnPreview.setEnabled(false);
			enableFields(false);
			refreshFields();
			lookupBranch.setValue("");
			lookupCompany.setValue("");
			txtCashier.setText("");
		}

	}

	public void enableFields(Boolean x) {

		lblBranch.setEnabled(x);
		lblCashier.setEnabled(x);

		lookupBranch.setEnabled(x);
		lookupCashier.setEnabled(x);

		txtBranch.setEnabled(x);
		txtCashier.setEnabled(x);

	}

	public void refreshFields() {

		lookupBranch.setValue("");
		lookupCashier.setValue("");

		txtBranch.setText("");
		txtCashier.setText("");

	}

	public static String company() {
		String SQL = "select co_id as \"ID\", trim(company_name) as \"Company Name\", trim(company_alias) as \"Alias\", '' as logo from mf_company";
		return SQL;
	}

	public void initialize_comp() {

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();
		txtCompany.setText(company);

		lookupBranch.setLookupSQL(getBranch());
		lookupCashier.setLookupSQL(getCashier());

		KEYBOARD_MANAGER.focusNextComponent();
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		enableFields(true);

		lookupCompany.setValue(co_id);
	}

}
