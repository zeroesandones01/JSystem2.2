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
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components._JInternalFrame;

public class DailyCollectionReport extends _JInternalFrame implements
ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Daily Collection Report";
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

	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblSortby;
	private JLabel lblBranch;
	private JLabel lblCashier;
	private JLabel lblFilterby;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupBranch;
	private _JLookup lookupCashier;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtBranch;
	private JTextField txtCashier;

	private JButton btnPreview;
	private JButton btnCancel;

	String company;
	String company_logo;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;

	private JComboBox cmbSortby;
	private JComboBox cmbFilterby;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	String co_id = "";

	private JLabel lblFiller;
	private JPanel pnlC;
	private JCheckBox chkRptFormat;
	private static Boolean blnWait = false; 
	
	private static JComboBox cboType; 

	public DailyCollectionReport() {
		super(title, false, true, false, true);
		initGUI();
	}

	public DailyCollectionReport(String title) {
		super(title);
		initGUI();
	}

	public DailyCollectionReport(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(562, 404));
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
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 212));
				pnlNorth.setBorder(components.JTBorderFactory
						.createTitleBorder("Check Details"));
				{
					pnlNorthWest = new JPanel(new GridLayout(4, 2, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(142,
							100));
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
								Object[] data = ((_JLookup) event.getSource())
										.getDataSet();
								if (data != null) {

									company = (String) data[1];
									company_logo = (String) data[3];

									txtCompany.setText(company);
									lookupProject.setLookupSQL(CheckStatusMonitoring
											.sql_project((String) data[0]));
									lookupBranch.setLookupSQL(getBranch());
									lookupCashier.setLookupSQL(getCashier());

									KEYBOARD_MANAGER.focusNextComponent();
									btnPreview.setEnabled(true);
									btnCancel.setEnabled(true);
									enableFields(true);
								}
							}
						});
						lookupCompany.addKeyListener(new KeyListener() {
							public void keyTyped(KeyEvent e) {
								if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
									company = "";
									company_logo = "";

									txtCompany.setText("");
									lookupCompany.setValue("");
									lookupProject.setLookupSQL(lookupMethods.getProject(""));
									lookupBranch.setLookupSQL(getBranch());
									lookupCashier.setLookupSQL(getCashier());

									KEYBOARD_MANAGER.focusNextComponent();
									btnPreview.setEnabled(true);
									btnCancel.setEnabled(true);
									enableFields(true);
								}
							}
							
							public void keyReleased(KeyEvent e) {
								
							}
							
							public void keyPressed(KeyEvent e) {
								
							}
						});
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlNorthWest.add(lblProject);
						lblProject.setEnabled(false);
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource())
										.getDataSet();
								if (data != null) {
									String name = (String) data[1];
									txtProject.setText(name);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
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
								Object[] data = ((_JLookup) event.getSource())
										.getDataSet();
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
								Object[] data = ((_JLookup) event.getSource())
										.getDataSet();
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
				pnlNorthEast = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
					txtProject.setEnabled(false);
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
				pnlCenter2.setBorder(components.JTBorderFactory
						.createTitleBorder("Cashiering Date"));

				pnlCriteria2 = new JPanel(new GridLayout(1, 4, 3, 3));
				pnlCenter2.add(pnlCriteria2, BorderLayout.WEST);
				{
					lblDateFrom = new JLabel("Date From   ", JLabel.CENTER);
					pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
					lblDateFrom.setEnabled(true);
				}
				{
					dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##",
							'_');
					pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);
					dteDateFrom.setDate(null);
					dteDateFrom.setEnabled(true);
					dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal
							.getDateSQL()));
				}
				{
					lblDateTo = new JLabel("Date To   ", JLabel.CENTER);
					pnlCriteria2.add(lblDateTo, BorderLayout.CENTER);
					lblDateTo.setEnabled(true);
				}
				{
					dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCriteria2.add(dteDateTo, BorderLayout.CENTER);
					dteDateTo.setDate(null);
					dteDateTo.setEnabled(true);
					dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteDateTo.addDateListener(new DateListener() {
						public void datePerformed(DateEvent event) {

						}
					});
				}
			}

			{
				pnlCenter = new JPanel(new GridLayout(3, 1, 5, 5)); 
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Option"));
				{
					{
						pnlA = new JPanel(new GridLayout(1, 3, 3, 3));
						pnlCenter.add(pnlA);
						{
							{
								JLabel lblSort = new JLabel("Sort by");
								pnlA.add(lblSort);
								lblSort.setEnabled(true);
							}
							{
								JLabel lblDate = new JLabel("Date");
								pnlA.add(lblDate);
								lblDate.setEnabled(true);
							}
							{
								JLabel lblType = new JLabel("Type");
								pnlA.add(lblType);
								lblType.setEnabled(true);
							}
						}

					}
					{
						JPanel panCombo = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlCenter.add(panCombo);
						{
							{
								String items[] = {
										"Receipt Type", 
										"Receipt No.",
										"Phase-Block-Lot", 
										"Buyers Name"
								};
								cmbSortby = new JComboBox(items);
								panCombo.add(cmbSortby);
								cmbSortby.setEnabled(true);
								cmbSortby.setSelectedIndex(0);
							}
							{
								String items[] = { "Transaction Date", "Actual Date" };
								cmbFilterby = new JComboBox(items);
								panCombo.add(cmbFilterby);
								cmbFilterby.setEnabled(true);
								cmbFilterby.setSelectedIndex(0);
							}
							{
								String items[] = {
										"Original Format",
										"CDR Checker Format", 
										"Export Format"
								}; 
								cboType = new JComboBox(items); 
								panCombo.add(cboType); 
								cmbFilterby.setEnabled(true);
								cmbFilterby.setSelectedIndex(0);								
							}
						}
					}
					{

					}
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
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany,
				lookupProject, dteDateFrom, btnPreview));
		this.setBounds(0, 0, 562, 404); // 174

		// added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	static String getBranch() {
		return "SELECT branch_id, branch_name, branch_alias FROM mf_office_branch;";
	}

	private String getCashier() {
		return "SELECT a.emp_code, (upper(trim(b.first_name))||' '||upper(trim(b.middle_name))||' '||upper(trim(b.last_name))) as cashier "
				+ "FROM (select * from em_employee where dept_code in ( '04', '80' ) ) a "
				+ "LEFT JOIN rf_entity b on a.entity_id = b.entity_id  "
				+ "order by b.first_name \r\n";
	}

	/*
	 * private List<JRSortField> sortBy(String sort_by) { Map<String, String[]>
	 * map = new HashMap<String, String[]>(); map.put("Receipt Type", new
	 * String[]{"receipt_type"}); map.put("Receipt No.", new
	 * String[]{"receipt_no"}); map.put("Buyers Name", new
	 * String[]{"entity_name"}); map.put("Phase-Block-Lot", new
	 * String[]{"pbl"});
	 * 
	 * List<JRSortField> sortList = new ArrayList<JRSortField>(); for(String
	 * fields : map.get(sort_by)){ JRDesignSortField sortField = new
	 * JRDesignSortField(); sortField.setName(fields);
	 * sortField.setOrder(SortOrderEnum.ASCENDING);
	 * sortField.setType(SortFieldTypeEnum.FIELD);
	 * 
	 * sortList.add(sortField); }
	 * 
	 * return sortList; }
	 */

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupProject.requestFocus();
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
			
			SwingWorker sw = new SwingWorker() {
				protected Object doInBackground()
						throws FileNotFoundException, IOException, InterruptedException {
					controls(false);
					
					
					prime(); 

					btnCancel.setEnabled(true);
					btnPreview.setEnabled(true);

					pgUpdate dbExec = new pgUpdate();
					dbExec.executeUpdate("delete from tmp_cancelled_jv where c_user = '"+UserInfo.EmployeeCode+"'", false);
					dbExec.commit();

					dbExec = new pgUpdate();
					dbExec.executeUpdate("insert into tmp_cancelled_jv \n" +
							"select *, '"+UserInfo.EmployeeCode+"' \n" + 
							"from view_cancelled_accounts('"+lookupCompany.getValue().toString()+"', '', '', '"+dteDateFrom.getDate().toString()+"'::date, '"+dteDateTo.getDate().toString()+"'::date) \n" + 
							"order by c_cancelled_date, c_entity_name", true);
					dbExec.commit();

					String detailed = "CDR";
					String summary = "CDR Summary";
					String branch_id = "", proj_id = "", branch = "", project = "", date = "";

					if (lookupBranch.getText().equals("")) {
						branch = "ALL";
						branch_id = "ALL";
					} else {
						branch = txtBranch.getText();
						branch_id = lookupBranch.getText();
					}

					if (lookupProject.getText().equals("")) {
						project = "ALL";
						proj_id = "ALL";
					} else {
						project = txtProject.getText();
						proj_id = lookupProject.getText();
					}

					if ((String) cmbFilterby.getSelectedItem() == "Actual Date") {
						date = "actualdate";
					} else {
						date = "transdate";
					}

					String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), detailed.toUpperCase());
					String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), summary.toUpperCase());

					System.out.printf("Project :" + project);
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					System.out.printf("Display Value of Date: %s%n", date);

					String strDateHeader = "";
					if (df.format(dteDateFrom.getDate()).equals(df.format(dteDateTo.getDate()))) {
						strDateHeader = df.format(dteDateFrom.getDate());
					} else {
						strDateHeader = df.format(dteDateFrom.getDate()).toString()+" to "+df.format(dteDateTo.getDate()).toString();
					}

					System.out.println(strDateHeader);

					mapParameters.put("company", company);
					mapParameters.put("branch_id", lookupBranch.getValue());
					mapParameters.put("branch", branch);
					mapParameters.put("project", project); // project
					mapParameters.put("date", date);
					mapParameters.put("co_id", lookupCompany.getText());
					mapParameters.put("proj_id", proj_id);
					mapParameters.put("cashier", txtCashier.getText());
					mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters.put("prepared_by", UserInfo.Alias);
					mapParameters.put("date_cdr", dteDateFrom.getDate());
					mapParameters.put("date_cdr_str", df.format(dteDateFrom.getDate()));
					mapParameters.put("sort", cmbSortby.getSelectedItem());
					mapParameters.put("branch_id", branch_id);
					mapParameters.put("DateTo", df.format(dteDateTo.getDate()));
					mapParameters.put("DateHeader", strDateHeader);
					mapParameters.put("emp_code", UserInfo.EmployeeCode);

					System.out.println(company);
					System.out.println(lookupBranch.getValue());
					System.out.println(branch);
					System.out.println(project);
					System.out.println(date);
					System.out.println(lookupCompany.getText());
					System.out.println(proj_id);
					System.out.println(txtCashier.getText());
					System.out.println(UserInfo.Alias);
					System.out.println(dteDateFrom.getDate());
					System.out.println(df.format(dteDateFrom.getDate()));
					System.out.println(cmbSortby.getSelectedItem());
					System.out.println(branch_id);
					System.out.println(df.format(dteDateTo.getDate()));

					if (cboType.getSelectedIndex()==1) {
						System.out.println("rptCDR_detailed");
						System.out.println("select * from view_cdr_detailed_v2('"+proj_id+"','"+branch_id+"','"+lookupCompany.getText()+"','"+date+"','"+df.format(dteDateFrom.getDate())+"','"+df.format(dteDateTo.getDate())+"','"+cmbSortby.getSelectedItem()+"', '"+UserInfo.EmployeeCode+"'); ");
						FncReport.generateReport("/Reports/rptCDR_detailed.jasper", reportTitle, txtProject.getText(), mapParameters);
					} else if (cboType.getSelectedIndex()==0) {
						System.out.println("rptCDR_detailed_v2");
						FncReport.generateReport("/Reports/rptCDR_detailed_v2.jasper", reportTitle, txtProject.getText(), mapParameters);
					} else {
						System.out.println("Rico Format");
						FncReport.generateReport("/Reports/rptCDR_detailed_export.jasper", reportTitle, txtProject.getText(), mapParameters);				
					}

					Map<String, Object> mapParameters2 = new HashMap<String, Object>();
					mapParameters2.put("company", company);
					mapParameters2.put("branch_id", lookupBranch.getValue());
					mapParameters2.put("branch", branch);
					mapParameters2.put("project", project);
					mapParameters2.put("date", date);
					mapParameters2.put("co_id", lookupCompany.getValue());
					mapParameters2.put("proj_id", proj_id);
					mapParameters2.put("cashier", txtCashier.getText());
					mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
					mapParameters2.put("prepared_by", UserInfo.Alias);
					mapParameters2.put("date_cdr", dteDateFrom.getDate());
					mapParameters2.put("date_cdr_str", df.format(dteDateFrom.getDate()));
					mapParameters2.put("branch_id", branch_id);
					mapParameters2.put("dateTo_cdr_str", df.format(dteDateTo.getDate()));
					mapParameters2.put("DateHeader", strDateHeader);
					FncReport.generateReport("/Reports/rptCDR_summary.jasper", reportTitle2, txtProject.getText(), mapParameters2);

					controls(true);
					return null;
				}
			};
			sw.execute();
		}

		if (e.getActionCommand().equals("Cancel")) {
			// lookupCompany.setText("");
			// txtCompany.setText("");
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			cmbSortby.setSelectedIndex(0);
			cmbFilterby.setSelectedIndex(0);
			// btnPreview.setEnabled(false);
			// enableFields(false);
			refreshFields();
			lookupBranch.setText("");
			// lookupCompany.setValue("");
			lookupProject.setText("");
			txtCashier.setText("");
		}

	}

	public void enableFields(Boolean x) {

		lblProject.setEnabled(x);
		lblBranch.setEnabled(x);
		lblCashier.setEnabled(x);

		lookupProject.setEnabled(x);
		lookupBranch.setEnabled(x);
		lookupCashier.setEnabled(x);

		txtProject.setEnabled(x);
		txtBranch.setEnabled(x);
		txtCashier.setEnabled(x);

	}

	public void refreshFields() {

		lookupProject.setValue("");
		lookupBranch.setValue("");
		lookupCashier.setValue("");

		txtProject.setText("");
		txtBranch.setText("");
		txtCashier.setText("");

	}

	public void initialize_comp() {

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();
		txtCompany.setText(company);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupBranch.setLookupSQL(getBranch());
		lookupCashier.setLookupSQL(getCashier());

		KEYBOARD_MANAGER.focusNextComponent();
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		enableFields(true);

		lookupCompany.setValue(co_id);
	}

	private void prime() {
		String detailed = "CDR";
		String summary = "CDR Summary";
		String branch_id = "", proj_id = "", branch = "", project = "", date = "";

		FncGlobal.startProgress("Priming CDR... Please wait...");

		btnCancel.setEnabled(false);
		btnPreview.setEnabled(false);

		if (lookupBranch.getText().equals("")) {
			branch = "ALL";
			branch_id = "ALL";
		} else {
			branch = txtBranch.getText();
			branch_id = lookupBranch.getText();
		}

		if (lookupProject.getText().equals("")) {
			project = "ALL";
			proj_id = "ALL";
		} else {
			project = txtProject.getText();
			proj_id = lookupProject.getText();
		}

		if ((String) cmbFilterby.getSelectedItem() == "Actual Date") {
			date = "actualdate";
		} else {
			date = "transdate";
		}

		pgUpdate dbExec = new pgUpdate(); 
		//ORIGINAL sp_prime_cdr2 replaced by file from july 26 because cash return does not display
		dbExec.executeUpdate("call sp_prime_cdr2_july('"+proj_id+"','"+branch_id+"','"+lookupCompany.getText()+"','"+date+"','"+df.format(dteDateFrom.getDate())+"','"+df.format(dteDateTo.getDate())+"','"+cmbSortby.getSelectedItem()+"', '"+UserInfo.EmployeeCode+"'); ", false);
		dbExec.commit();
		
		FncGlobal.stopProgress();

		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);
	}
	
	private void controls(Boolean blnRev) {
		lookupCompany.setEnabled(blnRev);
		lookupProject.setEnabled(blnRev);
		lookupBranch.setEnabled(blnRev);
		lookupCashier.setEnabled(blnRev);
		
		dteDateFrom.setEnabled(blnRev);
		dteDateTo.setEnabled(blnRev);
		
		cboType.setEnabled(blnRev);
		cmbSortby.setEnabled(blnRev);
		cmbFilterby.setEnabled(blnRev);
		
		btnCancel.setEnabled(blnRev);
		btnPreview.setEnabled(blnRev);
	}
}
