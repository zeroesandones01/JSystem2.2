package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;

public class FinancialReport extends _JInternalFrame implements ActionListener,
_GUI {

	private static final long serialVersionUID = -3793286429130957737L;
	static String title = "Financial Report";
	Dimension frameSize = new Dimension(620, 440);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);

	private JLabel lblCo;
	private JLabel lblPro;
	private JLabel lblPhase;
	private JLabel lblBranch;
	private JLabel lblFrom;
	private JLabel lblTo;

	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
	private _JLookup txtBranchID;

	private JTextField txtCo;
	private JTextField txtPro;
	private JTextField txtPhase;
	private JTextField txtBranch;

	private JComboBox cboRep;

	private JButton btnPreview;
	private JButton btnExportXLS;
	private JButton btnExportPDF;

	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;

	private JXPanel panDateMode;
	private JXPanel panDate1;

	public FinancialReport() {
		super(title, true, true, true, true);
		initGUI();
	}

	public FinancialReport(String title) {
		super(title);
		initGUI();
	}

	public FinancialReport(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setSize(frameSize);
		this.setLayout(new BorderLayout(5, 5));
		this.setMinimumSize(frameSize);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				super.componentResized(e);
				System.out.println("Resizing");
				System.out.println(((_JInternalFrame) e.getSource()).getSize());
			}
		});
		this.setMinimumSize(new Dimension(620, 440));

		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panCenter = new JXPanel(new GridBagLayout());
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					c.ipady = 40;

					{
						c.weightx = 1;
						c.weighty = 1.5;

						c.gridx = 0; 
						c.gridy = 0; 

						JPanel panParam1 = new JPanel(new GridBagLayout());
						panCenter.add(panParam1, c);
						panParam1.setBorder(JTBorderFactory.createTitleBorder("Project, Company and Phase", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.HORIZONTAL;
							cons_com.ipady = 10;
							cons_com.insets = new Insets(0, 5, 0, 5);

							/*	LINE 1	*/
							{
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 0; 
									cons_com.gridy = 0; 

									JLabel lblCompany = new JLabel("Company"); 
									panParam1.add(lblCompany, cons_com); 
									lblCompany.setHorizontalAlignment(JLabel.LEADING);
									lblCompany.setFont(FncGlobal.sizeLabel);
								}
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 1; 
									cons_com.gridy = 0; 

									txtCoID = new _JLookup();
									txtCoID.setReturnColumn(0);
									txtCoID.setLookupSQL(lookupMethods.getCompany());
									txtCoID.setHorizontalAlignment(JTextField.CENTER);
									txtCoID.setFont(FncGlobal.sizeTextValue);
									txtCoID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data!=null) {
												txtCoID.setValue(data[0].toString());
												txtCo.setText(data[1].toString());
												txtProID.setValue("");
												txtPro.setText("All Projects");

												try {
													txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));	
												} catch (NullPointerException ex) {

												}
											}

										}
									});
									txtCoID.addKeyListener(new KeyListener() {

										public void keyTyped(KeyEvent e) {
											if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
												txtCoID.setValue("");
												txtCo.setText("All Companies");
											}
										}

										public void keyReleased(KeyEvent e) {

										}

										public void keyPressed(KeyEvent e) {

										}
									});
									txtCoID.setValue("02");
									panParam1.add(txtCoID, cons_com);
								}
								{
									cons_com.weightx = 1.25;
									cons_com.weighty = 1;

									cons_com.gridx = 2; 
									cons_com.gridy = 0; 

									txtCo = new JTextField("");
									txtCo.setHorizontalAlignment(JTextField.CENTER);
									txtCo.setFont(FncGlobal.sizeTextValue);
									txtCo.setEditable(false);
									panParam1.add(txtCo, cons_com);
								}
							}
							/*	LINE 2	*/
							{
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 0; 
									cons_com.gridy = 1; 

									JLabel lblProject = new JLabel("Project"); 
									panParam1.add(lblProject, cons_com); 
									lblProject.setHorizontalAlignment(JLabel.LEADING);
									lblProject.setFont(FncGlobal.sizeLabel);
								}
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 1; 
									cons_com.gridy = 1; 

									txtProID = new _JLookup();
									txtProID.setReturnColumn(0);
									txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
									txtProID.setHorizontalAlignment(JTextField.CENTER);
									txtProID.setFont(FncGlobal.sizeTextValue);
									txtProID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data!=null) {
												txtProID.setValue(data[0].toString());
												txtPro.setText(data[1].toString());

												try {
													txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));											
												} catch (NullPointerException e) {

												}
											}
										}
									});
									txtProID.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {
											if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
												txtProID.setValue("");
												txtPro.setText("All Projects");
											}
										}

										public void keyReleased(KeyEvent e) {

										}

										public void keyPressed(KeyEvent e) {

										}
									});
									panParam1.add(txtProID, cons_com);
									txtProID.setValue("015");
								}
								{
									cons_com.weightx = 1.25;
									cons_com.weighty = 1;

									cons_com.gridx = 2; 
									cons_com.gridy = 1; 

									txtPro = new JTextField("");
									txtPro.setHorizontalAlignment(JTextField.CENTER);
									txtPro.setFont(FncGlobal.sizeTextValue);
									txtPro.setEditable(false);
									panParam1.add(txtPro, cons_com);
								}
							}
							/*	LINE 3	*/
							{
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 0; 
									cons_com.gridy = 2; 

									JLabel lblPhase = new JLabel("Phase"); 
									panParam1.add(lblPhase, cons_com); 
									lblPhase.setHorizontalAlignment(JLabel.LEADING);
									lblPhase.setFont(FncGlobal.sizeLabel);
								}
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 1; 
									cons_com.gridy = 2; 

									txtPhaseID = new _JLookup();
									txtPhaseID.setReturnColumn(0);
									txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
									txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
									txtPhaseID.setFont(FncGlobal.sizeTextValue);
									txtPhaseID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data!=null) {
												txtPhaseID.setText(data[0].toString());
												txtPhase.setText(data[1].toString());
											}
										}
									});
									txtPhaseID.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {
											if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
												txtPhaseID.setValue("");
												txtPhase.setText("All Phase");
											}
										}

										public void keyReleased(KeyEvent e) {

										}

										public void keyPressed(KeyEvent e) {

										}
									});
									panParam1.add(txtPhaseID, cons_com);
								}
								{
									cons_com.weightx = 1.25;
									cons_com.weighty = 1;

									cons_com.gridx = 2; 
									cons_com.gridy = 2; 

									txtPhase = new JTextField("");
									txtPhase.setHorizontalAlignment(JTextField.CENTER);
									txtPhase.setFont(FncGlobal.sizeTextValue);
									txtPhase.setEditable(false);
									panParam1.add(txtPhase, cons_com);
								}
							}
							/*	LINE 4	*/
							{
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 0; 
									cons_com.gridy = 3; 

									JLabel lblBranch = new JLabel("Branch"); 
									panParam1.add(lblBranch, cons_com); 
									lblBranch.setHorizontalAlignment(JLabel.LEADING);
									lblBranch.setFont(FncGlobal.sizeLabel);
								}
								{
									cons_com.weightx = 0.25;
									cons_com.weighty = 1;

									cons_com.gridx = 1; 
									cons_com.gridy = 3; 

									txtBranchID = new _JLookup();
									txtBranchID.setReturnColumn(0);
									txtBranchID.setLookupSQL(lookupMethods.getOfficeBranch());
									txtBranchID.setHorizontalAlignment(JTextField.CENTER);
									txtBranchID.setFont(FncGlobal.sizeTextValue);
									txtBranchID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data!=null) {
												txtBranchID.setText(data[0].toString());
												txtBranch.setText(data[1].toString());
											}
										}
									});
									txtBranchID.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {
											if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
												txtBranchID.setValue("");
												txtBranch.setText("All Branches");
											}
										}

										public void keyReleased(KeyEvent e) {

										}

										public void keyPressed(KeyEvent e) {

										}
									});

									panParam1.add(txtBranchID, cons_com); 
								}
								{
									cons_com.weightx = 1.25;
									cons_com.weighty = 1;

									cons_com.gridx = 2; 
									cons_com.gridy = 3; 

									txtBranch = new JTextField("");
									txtBranch.setHorizontalAlignment(JTextField.CENTER);
									txtBranch.setFont(FncGlobal.sizeTextValue);
									txtBranch.setEditable(false);
									panParam1.add(txtBranch, cons_com);
								}
							}
						}

					}
					{
						c.weightx = 1;
						c.weighty = 0.5;

						c.gridx = 0; 
						c.gridy = 1; 

						JXPanel panParam2 = new JXPanel(new GridLayout(2, 1, 5, 5));
						panCenter.add(panParam2, c);
						{
							{
								JPanel panReport = new JPanel(new GridBagLayout()); 
								panParam2.add(panReport); 
								panReport.setBorder(JTBorderFactory.createTitleBorder("Report Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									GridBagConstraints cons_report = new GridBagConstraints();
									cons_report.fill = GridBagConstraints.HORIZONTAL;
									cons_report.ipady = 10;
									cons_report.insets = new Insets(0, 5, 0, 5);

									{
										cons_report.weightx = 1;
										cons_report.weighty = 1;

										cons_report.gridx = 0;
										cons_report.gridy = 0; 
										
										cboRep = new JComboBox(new String[] {
												"Cash on hand - collection",
												"Checks on hand",
												"PDC Receipts/In trust",
												"Contract Receivables",
												"CD - Temporary Reservation",
												"CD - Processing Fees", 
												"Loan Released Accounts", 
												"Loan Released Accounts (Bank Financing)", 
												"Cash/Check Summary Report", 
												"Schedule of HDMF Retention Receivable", 
												"Schedule of Construction Bonds Payable", 
												"Cancelled Accounts Report", 
												"Late OR Entries", 
												"Schedule of Miscellaneous Other Income - CD Processing Cost", 
												"Sales Report by Turnover Date",
												"Monitoring Report of Receivable from Homeowners - Water/Garbage", 
												"Schedule of Receivable from Homeowners - Amenities",
												"TCT Status/Location Report",
												"Contract to Sell (CTS) Notarization Report", 
												"Summary of Change of Payments Requests",
												"Client Request Summary Report",
												"Customer Deposit Forfeiture",
												"Schedule of CD-DOWNPAYMENT FR", 
												"Not Yet Good PDC Deposits",
												"Schedule of CD-REGULAR", 
												"Advances from Bank - Assignment of Receivables"
												
										});
										cboRep.setFont(FncGlobal.sizeTextValue);
										cboRep.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent arg0) {
												try {
													System.out.println("");
													System.out.println("Selected Index: " + cboRep.getSelectedIndex());
													
													if(UserInfo.EmployeeCode.equals("900027") || UserInfo.EmployeeCode.equals("901265")) {
														btnPreview.setEnabled(false);
													}
													
													if (cboRep.getSelectedIndex()==1) {
														dteFrom.setEnabled(false);
													} else if (cboRep.getSelectedIndex()==2) {
														dteFrom.setEnabled(false);
													} else if (cboRep.getSelectedIndex()==3) {
														dteFrom.setEnabled(false);
													} else if (cboRep.getSelectedIndex()==4) {
														dteFrom.setEnabled(false);
													} else if (cboRep.getSelectedIndex()==5) {
														dteFrom.setEnabled(false);
													} else if (cboRep.getSelectedIndex()==6) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==7) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==8) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==9) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==10) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==11) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==12) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==13) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==14) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==15) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==16) {
														dteFrom.setEnabled(true);
													} else if (cboRep.getSelectedIndex()==18) {
														dteFrom.setEnabled(true);
														txtCoID.setValue("");
														txtCo.setText("All Company");
														txtProID.setValue("");
														txtPro.setText("All Project");
														
														//DCRF 2019
														if(UserInfo.EmployeeCode.equals("900027") || UserInfo.EmployeeCode.equals("901265")) {
															btnPreview.setEnabled(true);
														}
													} else if(cboRep.getSelectedIndex() == 19) {
														dteFrom.setEnabled(true);
													}else if(cboRep.getSelectedIndex() == 20) {
														dteFrom.setEnabled(false);
													}else if(cboRep.getSelectedIndex() == 21) {
														dteFrom.setEnabled(false);
													}else if(cboRep.getSelectedIndex() == 22){
														dteFrom.setEnabled(false);
														txtCoID.setValue(null);
														txtCo.setText("All Company");
														txtProID.setValue(null);
														txtPro.setText("All Project");
														txtPhaseID.setValue(null);
														txtPhase.setText("All Phase");
														
													}else if(cboRep.getSelectedIndex() == 23){
														dteFrom.setEnabled(false);
														txtCoID.setValue("");
														txtCo.setText("All Company");
														txtProID.setValue("");
														txtPro.setText("All Project");
														txtPhaseID.setValue("");
														txtPhase.setText("All Phase");
													}else if(cboRep.getSelectedIndex() == 24){
														dteFrom.setEnabled(false);
														txtCoID.setValue("");
														txtCo.setText("All Company");
														txtProID.setValue("");
														txtPro.setText("All Project");
														txtPhaseID.setValue("");
														txtPhase.setText("All Phase");
													}else {
														dteFrom.setEnabled(true);
													}

													dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));	
												} catch (Exception e) {
													
												}
											}
										});
										panReport.add(cboRep, cons_report);
									}

								}
							}
							{
								panDateMode = new JXPanel(new BorderLayout(5, 5)); 
								panParam2.add(panDateMode, BorderLayout.CENTER);
								panDateMode.setBorder(JTBorderFactory.createTitleBorder("Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{

								}
							}
						}
					}
				}
			}
			{
				JXPanel panEnd = new JXPanel(new GridLayout(1, 3, 5, 5));
				panMain.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPreview = new JButton("Preview");
						panEnd.add(btnPreview, BorderLayout.CENTER);
						btnPreview.setFont(FncGlobal.sizeControls);
						if(UserInfo.EmployeeCode.equals("900027") || UserInfo.EmployeeCode.equals("901265")) {
							btnPreview.setEnabled(false);
						}
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								String strCoID = "";
								String strProID = "";
								String strPhaseID = "";
								String strBranchID = "";

								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								String company_logo = sql_getCompanyLogo(txtCoID.getValue());
								
								Integer selected_index = cboRep.getSelectedIndex();
								
								System.out.printf("Value of selected index: %s%n", selected_index);
								
								if(selected_index != 22 && selected_index != 23 && selected_index != 24) {
									if (!txtCoID.getValue().equals("---")) {
										strCoID = txtCoID.getValue();
									}

									if (!txtProID.getValue().equals("---")) {
										strProID = txtProID.getValue();
									}

									if (!txtPhaseID.getValue().equals("---")) {
										strPhaseID = txtPhaseID.getValue();
									}

									if (!txtBranchID.getValue().equals("---")) {
										strBranchID = txtBranchID.getValue();
									}
								}

								String strDate = "";
								SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

								strDate = dateFormat.format(dteTo.getDate());

								Map<String, Object> mapParameters = new HashMap<String, Object>();
								mapParameters.put("01_CoID", strCoID);
								mapParameters.put("02_Company", txtCo.getText());
								mapParameters.put("03_ProID", strProID);
								mapParameters.put("04_Project", txtPro.getText());
								mapParameters.put("05_BranchID", strBranchID);
								mapParameters.put("06_Branch", txtBranch.getText());
								mapParameters.put("07_From", null);
								mapParameters.put("08_To", dteTo.getDate().toString());
								mapParameters.put("09_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
								mapParameters.put("10_User",UserInfo.FullName);
								mapParameters.put("11_AsOf", strDate);
								mapParameters.put("12_phase", strPhaseID);

								if (cboRep.getSelectedIndex()==0) {
									FncReport.generateReport("/Reports/rpt_financialreport_cashonhand.jasper", "Financial Report - Cash on Hand", "", mapParameters);
								} else if (cboRep.getSelectedIndex()==1) {
									FncReport.generateReport("/Reports/rpt_financialreport_checksonhand.jasper", "Financial Report - Checks on Hand", "", mapParameters);
								} else if (cboRep.getSelectedIndex()==2) {
									FncReport.generateReport("/Reports/rpt_financialreport_pdc.jasper", "Financial Report - PDC Receipts / In Trust", "", mapParameters);
								} else if (cboRep.getSelectedIndex()==3) {
									FncReport.generateReport("/Reports/rpt_financialreport_contract_receivable.jasper", "Financial Report - Contract Receivable", "", mapParameters);
								} else if (cboRep.getSelectedIndex()==4) {
									FncReport.generateReport("/Reports/rpt_financialreport_temporary_reservation.jasper", "Financial Report - Temporary Reservation", "", mapParameters);
								} else if (cboRep.getSelectedIndex()==5) {
									FncReport.generateReport("/Reports/rpt_financialreport_processing_fee.jasper", "Financial Report - Processing Fee", "", mapParameters);

									ArrayList<String> listcost_id = new ArrayList<String>();

									String sql = "SELECT pcostdtl_id as \"PCost ID.\" \n" + 
											"FROM mf_processing_cost_dl \n" + 
											"GROUP BY pcostdtl_id, pcostdtl_desc\n" + 
											"ORDER BY pcostdtl_desc ASC";

									pgSelect db = new pgSelect();
									db.select(sql);

									if (db.isNotNull()) {
										for (int x = 0; x < db.getRowCount(); x++) {
											listcost_id.add(db.getResult()[x][0].toString());
										}
									}

									System.out.println("");
									System.out.println("proj_name: " + txtPro.getText());
									System.out.println("emp_alias: " + UserInfo.Alias);
									System.out.println("emp_fname: " + UserInfo.FirstName);
									System.out.println("emp_lname: " + UserInfo.LastName);
									System.out.println("date_from: " + FncGlobal.dateFormat("2016-01-01"));
									System.out.println("date_to: " + dteTo.getDate());
									System.out.println("part_desc: " + "");
									System.out.println("pcost_id: " + listcost_id);
									System.out.println("projcode: " + strProID);
									System.out.println("buyer: " + "NULL");
									System.out.println("batch_no: " + "NULL");
									System.out.println("batch_no1: " + "NULL");

									Map<String, Object> mapParameters1 = new HashMap<String, Object>();
									mapParameters1.put("proj_name", txtPro.getText());
									mapParameters1.put("emp_alias", UserInfo.Alias);
									mapParameters1.put("emp_fname", UserInfo.FirstName);
									mapParameters1.put("emp_lname", UserInfo.LastName);
									mapParameters1.put("date_from", FncGlobal.dateFormat("2016-01-01"));
									mapParameters1.put("date_to", dteTo.getDate().toString());
									mapParameters1.put("part_desc", "");
									mapParameters1.put("pcost_id", listcost_id);
									mapParameters1.put("projcode", strProID);
									mapParameters1.put("buyer", "All");
									mapParameters1.put("batch_no", "All");
									mapParameters1.put("batch_no1", "All");
									mapParameters1.put("subrptPCostSummary", this.getClass().getResourceAsStream("/Reports/subrptPCostSummary.jasper"));
									mapParameters1.put("date_param", dteTo.getDate().toString());
									mapParameters1.put("phase", strPhaseID);
									FncReport.generateReport("/Reports/rptProcessingCost_v2.jasper", "Processing Cost Report", mapParameters1);
								} else if (cboRep.getSelectedIndex()==6) {
									String strStatus = "";
									String strProject = "";
									String strPhase = "";
									String strCo = "";

									if (strStatus.equals("Al")) {
										strStatus = "";
									}

									if (txtCo.getText() == "All Companies") {
										strCo = "";
									} else {
										strCo = txtCoID.getValue();
									}

									if (txtPro.getText() == "All Projects") {
										strProject = "";
									} else {
										strProject = txtProID.getValue();
									}

									if (txtPhase.getText() == "All Phase") {
										strPhase = "";
									} else {
										strPhase = txtPhaseID.getValue();
									}

									if (strCo == "---") {
										strCo = "";
									}

									if (strProject == "---") {
										strProject = "";
									}

									if (strPhase == "---") {
										strPhase = "";
									}

									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									strDate = "Date Covered: " + strDate;

									System.out.println("");
									System.out.println("co_id: " + strCo);
									System.out.println("co_name: " + txtCo.getText());
									System.out.println("date_from: " + dteFrom.getText());
									System.out.println("date_param: " + strDate);
									System.out.println("project_id: " + strProject);
									System.out.println("user_name: " + UserInfo.EmployeeCode);
									System.out.println("strStatus: " + strStatus);

									System.out.println("");
									System.out.println("SELECT * \n" +
											"FROM view_hdmf_loanreleased('"+strCo+"', '"+strProject+"', '"+strPhase+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"') a \n" +
											"ORDER BY a.c_name");

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("co_id", strCo);
									mapParameters2.put("co_name", txtCo.getText());
									mapParameters2.put("dateFrom", dteFrom.getDate().toString());
									mapParameters2.put("dateTo", dteTo.getDate().toString());
									mapParameters2.put("dateParam", strDate);
									mapParameters2.put("project_id", strProject);
									mapParameters2.put("user_name", UserInfo.EmployeeCode);
									mapParameters2.put("project", txtPro.getText());
									mapParameters2.put("phase", strPhase);
									FncReport.generateReport("/Reports/rpt_hdmf_loanReleased_v2.jasper", "Loan Released Report", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==7) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();

									mapParameters2.put("co_name", txtCo.getText());
									mapParameters2.put("dateParam", strDate);
									mapParameters2.put("user_name", UserInfo.EmployeeCode);
									mapParameters2.put("project", txtPro.getText());

									mapParameters2.put("co_id", strCoID);
									mapParameters2.put("proj_id", strProID);
									mapParameters2.put("phase", strPhaseID);
									mapParameters2.put("dteFrom", dteFrom.getDate());
									mapParameters2.put("dteTo", dteTo.getDate());

									FncReport.generateReport("/Reports/rptBankFinancing_LoanReleased.jasper", "Bank Financing Report (Loan Released)", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==8) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									if (!txtCoID.getValue().equals("---")) {
										strCoID = txtCoID.getValue();
									} else {
										strCoID = "ALL";
									}

									if (!txtProID.getValue().equals("---")) {
										strProID = txtProID.getValue();
									} else {
										strProID = "ALL"; 
									}

									if (!txtPhaseID.getValue().equals("---")) {
										strPhaseID = txtPhaseID.getValue();
									} else {
										strPhaseID = "ALL"; 
									}

									if (!txtBranchID.getValue().equals("---")) {
										strBranchID = txtBranchID.getValue();
									} else {
										strBranchID = "ALL"; 
									}

									pgUpdate dbExec = new pgUpdate(); 
									dbExec.executeUpdate("delete from tmp_check_summary where c_user = '"+UserInfo.EmployeeCode+"'", true);
									dbExec.commit();

									dbExec = new pgUpdate();
									dbExec.executeUpdate("insert into tmp_check_summary \n" +
											"select *, '"+UserInfo.EmployeeCode+"' \n" +
											"from view_check_summary('"+strPhaseID+"', '"+strBranchID+"', '"+strCoID+"', 'trans_date', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"');", true);
									dbExec.commit();

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("co_name", txtCo.getText());
									mapParameters2.put("dateParam", strDate);
									mapParameters2.put("user_id", UserInfo.EmployeeCode);
									FncReport.generateReport("/Reports/rpt_summary_check.jasper", "Check Summary", "", mapParameters2);
									FncReport.generateReport("/Reports/rpt_summary_cash.jasper", "Cash Summary", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==9) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("co_name", txtCo.getText());
									mapParameters2.put("co_id", txtCoID.getText());
									mapParameters2.put("dateFrom", dteFrom.getDate());
									mapParameters2.put("dateTo", dteTo.getDate());
									mapParameters2.put("dateParam", strDate);
									mapParameters2.put("project_id", strProID);
									mapParameters2.put("user_name", UserInfo.EmployeeCode);
									mapParameters2.put("project", txtPro.getText());
									mapParameters2.put("phase", strPhaseID);
									FncReport.generateReport("/Reports/rpt_hdmf_retainedamount_fr.jasper", "Retained Amount", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==10) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("co_name", txtCo.getText());
									mapParameters2.put("co_id", txtCoID.getText());
									mapParameters2.put("dateFrom", dteFrom.getDate());
									mapParameters2.put("dateTo", dteTo.getDate());
									mapParameters2.put("dateParam", strDate);
									mapParameters2.put("project_id", strProID);
									mapParameters2.put("user_name", UserInfo.EmployeeCode);
									mapParameters2.put("project", txtPro.getText());
									mapParameters2.put("phase", strPhaseID);
									FncReport.generateReport("/Reports/rpt_financialreport_consbond.jasper", "Financial Report - Construction Bond", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==11) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									pgUpdate dbExec = new pgUpdate();
									dbExec.executeUpdate("delete from tmp_cancelled_jv where c_user = '"+UserInfo.EmployeeCode+"'", false);
									dbExec.commit();

									dbExec = new pgUpdate();
									dbExec.executeUpdate("insert into tmp_cancelled_jv \n" +
											"select *, '"+UserInfo.EmployeeCode+"' \n" + 
											"from view_cancelled_accounts('"+txtCoID.getValue().toString()+"', '', '', '"+dteFrom.getDate().toString()+"'::date, '"+dteTo.getDate().toString()+"'::date) \n" + 
											"order by c_cancelled_date, c_entity_name", true);
									dbExec.commit();

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("co_name", txtCo.getText());
									mapParameters2.put("co_id", txtCoID.getText());
									mapParameters2.put("dateFrom", dteFrom.getDate());
									mapParameters2.put("dateTo", dteTo.getDate());
									mapParameters2.put("dateParam", strDate);
									mapParameters2.put("project_id", strProID);
									mapParameters2.put("user_name", UserInfo.EmployeeCode);
									mapParameters2.put("project", txtPro.getText());
									mapParameters2.put("phase", strPhaseID);
									FncReport.generateReport("/Reports/rpt_financialreport_cancelled.jasper", "Financial Report - Cancelled Report", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==12) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									pgUpdate dbExec = new pgUpdate();
									dbExec.executeUpdate("delete from tmp_fr_lateor where emp_code = '"+UserInfo.EmployeeCode+"'", false);
									dbExec.commit();

									dbExec = new pgUpdate();
									dbExec.executeUpdate("insert into tmp_fr_lateor \n" +
											"select *, '"+UserInfo.EmployeeCode+"' \n" +
											"from view_financial_report_lateor('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtBranchID.getValue()+"', \n" +
											"'"+txtPhaseID.getValue()+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"')", true);
									dbExec.commit();

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("co_name", txtCo.getText());
									mapParameters2.put("co_id", txtCoID.getText());
									mapParameters2.put("dateFrom", dteFrom.getDate());
									mapParameters2.put("dateTo", dteTo.getDate());
									mapParameters2.put("dateParam", strDate);
									mapParameters2.put("project_id", strProID);
									mapParameters2.put("user_name", UserInfo.FullName);
									mapParameters2.put("project", txtPro.getText());
									mapParameters2.put("phase", strPhaseID);
									mapParameters2.put("emp_code", UserInfo.EmployeeCode);
									FncReport.generateReport("/Reports/rpt_financialreport_lateor_v2.jasper", "Financial Report - Late OR", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==13) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("01_Company", txtCo.getText());
									mapParameters2.put("02_CoID", txtCoID.getText());
									mapParameters2.put("03_ProID", strProID);
									mapParameters2.put("04_Project", txtPro.getText());
									mapParameters2.put("05_User", UserInfo.FullName);
									mapParameters2.put("07_phase", strPhaseID);
									mapParameters2.put("08_dateFrom", dteFrom.getDate());
									mapParameters2.put("09_dateTo", dteTo.getDate());
									mapParameters2.put("10_AsOfDate", strDate); 
									FncReport.generateReport("/Reports/rpt_project_closing_misc_oth_income.jasper", cboRep.getSelectedItem().toString(), "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==14) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("01_Company", txtCo.getText());
									mapParameters2.put("02_CoID", txtCoID.getText());
									mapParameters2.put("03_ProID", strProID);
									mapParameters2.put("04_Project", txtPro.getText());
									mapParameters2.put("05_User", UserInfo.FullName);
									mapParameters2.put("07_phase", strPhaseID);
									mapParameters2.put("08_dateFrom", dteFrom.getDate());
									mapParameters2.put("09_dateTo", dteTo.getDate());
									mapParameters2.put("10_AsOfDate", strDate); 
									FncReport.generateReport("/Reports/rpt_financialreport_turnover.jasper", cboRep.getSelectedItem().toString(), "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==15) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("01_Company", txtCo.getText());
									mapParameters2.put("02_CoID", txtCoID.getText());
									mapParameters2.put("03_ProID", strProID);
									mapParameters2.put("04_Project", txtPro.getText());
									mapParameters2.put("05_User", UserInfo.FullName);
									mapParameters2.put("07_phase", strPhaseID);
									mapParameters2.put("08_dateFrom", dteFrom.getDate());
									mapParameters2.put("09_dateTo", dteTo.getDate());
									mapParameters2.put("10_AsOfDate", strDate); 
									FncReport.generateReport("/Reports/rpt_financialreport_hor_v1.jasper", cboRep.getSelectedItem().toString()+" Version 1", "", mapParameters2);
									FncReport.generateReport("/Reports/rpt_financialreport_hor_v2.jasper", cboRep.getSelectedItem().toString()+" Version 2", "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==16) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("01_Company", txtCo.getText());
									mapParameters2.put("02_CoID", txtCoID.getText());
									mapParameters2.put("03_ProID", strProID);
									mapParameters2.put("04_Project", txtPro.getText());
									mapParameters2.put("05_User", UserInfo.FullName);
									mapParameters2.put("07_phase", strPhaseID);
									mapParameters2.put("08_dateFrom", dteFrom.getDate());
									mapParameters2.put("09_dateTo", dteTo.getDate());
									mapParameters2.put("10_AsOfDate", strDate); 
									FncReport.generateReport("/Reports/rpt_financialreport_hor_v3.jasper", cboRep.getSelectedItem().toString(), "", mapParameters2);
								} else if (cboRep.getSelectedIndex()==17) {
									strDate = df.format(dteTo.getDate());

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("01_Company", txtCo.getText());
									mapParameters2.put("02_CoID", txtCoID.getText());
									mapParameters2.put("03_ProID", strProID);
									mapParameters2.put("04_Project", txtPro.getText());
									mapParameters2.put("05_User", UserInfo.FullName);
									mapParameters2.put("07_phase", strPhaseID);
									mapParameters2.put("08_dateFrom", dteFrom.getDate());
									mapParameters2.put("09_dateTo", dteTo.getDate());
									mapParameters2.put("10_AsOfDate", strDate); 
									FncReport.generateReport("/Reports/rpt_financialreport_tct.jasper", cboRep.getSelectedItem().toString(), "", mapParameters2);									
								} else if (cboRep.getSelectedIndex()==18) {
									if (dteFrom.getDate().equals(dteTo.getDate())) {
										strDate = df.format(dteFrom.getDate());
									} else {
										strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
									}

									Map<String, Object> mapParameters2 = new HashMap<String, Object>();
									mapParameters2.put("01_Company", txtCo.getText());
									mapParameters2.put("02_CoID", txtCoID.getText());
									mapParameters2.put("03_ProID", strProID);
									mapParameters2.put("04_Project", txtPro.getText());
									mapParameters2.put("05_User", UserInfo.FullName);
									mapParameters2.put("07_phase", strPhaseID);
									mapParameters2.put("08_dateFrom", dteFrom.getDate());
									mapParameters2.put("09_dateTo", dteTo.getDate());
									mapParameters2.put("10_AsOfDate", strDate);
									mapParameters2.put("11_DateFilter", 

									(JOptionPane.showOptionDialog(null, "Select date filter", "Date Filter", JOptionPane.YES_NO_OPTION, 
											JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Notarized Date", "Tagged Date"}, 
											JOptionPane.YES_OPTION))==JOptionPane.YES_OPTION?new Integer("0"):new Integer("1")

									); 
									FncReport.generateReport("/Reports/rpt_financialreport_cts_notarization.jasper", cboRep.getSelectedItem().toString(), "", mapParameters2);
								}else if(cboRep.getSelectedIndex() == 19) {
									
									String phase = "";
									String branch_id = "";
									
									if (txtPhase.getText() == "All Phase") {
										phase = "";
									} else {
										phase = txtPhaseID.getValue();
									}
									
									if (txtBranch.getText() == "All Branches") {
										branch_id = "";
									} else {
										branch_id = txtBranchID.getValue();
									}

									Map<String, Object> mapParameter = new HashMap<String, Object>();
									mapParameter.put("co_id", txtCoID.getValue());
									mapParameter.put("company", txtCo.getText());
									mapParameter.put("proj_id", txtProID.getValue());
									mapParameter.put("project", txtPro.getText());
									mapParameter.put("phase", phase);
									mapParameter.put("branch_id", branch_id);
									mapParameter.put("branch_name", txtBranch.getText());
									mapParameter.put("date_from", df.format(dteFrom.getDate()).toString());
									mapParameter.put("date_to", df.format(dteTo.getDate()).toString());
									mapParameter.put("user_alias", UserInfo.Alias);
									mapParameter.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
									
									
									FncReport.generateReport("/Reports/rptFR_DCRF_Changes.jasper", cboRep.getSelectedItem().toString(), txtCo.getText(), mapParameter);
								}else if(cboRep.getSelectedIndex() == 20) {
									String phase = "";
									String branch_id = "";
									
									if (txtPhase.getText() == "All Phase") {
										phase = "";
									} else {
										phase = txtPhaseID.getValue();
									}
									
									if (txtBranch.getText() == "All Branches") {
										branch_id = "";
									} else {
										branch_id = txtBranchID.getValue();
									}

									Map<String, Object> mapParameter = new HashMap<String, Object>();
									mapParameter.put("co_id", txtCoID.getValue());
									mapParameter.put("company", txtCo.getText());
									mapParameter.put("proj_id", txtProID.getValue());
									mapParameter.put("phase", phase);
									mapParameter.put("branch_id", branch_id);
									mapParameter.put("date_to", dteTo.getDate());
									
									System.out.println("date: "+dteTo.getDate());
									
									FncReport.generateReport("/Reports/rptFR_Client_Request_summary.jasper", cboRep.getSelectedItem().toString(), txtCo.getText(), mapParameter);
									
								}else if(cboRep.getSelectedIndex() == 21) {
									String phase = "";
									String branch_id = "";
									
									if (txtPhase.getText() == "All Phase") {
										phase = "";
									} else {
										phase = txtPhaseID.getValue();
									}
									
									if (txtBranch.getText() == "All Branches") {
										branch_id = "";
									} else {
										branch_id = txtBranchID.getValue();
									}

									Map<String, Object> mapParameter = new HashMap<String, Object>();
									mapParameter.put("co_id", txtCoID.getValue());
									mapParameter.put("company", txtCo.getText());
									mapParameter.put("proj_id", txtProID.getValue());
									mapParameter.put("phase", phase);
									mapParameter.put("branch_id", branch_id);
									mapParameter.put("date_from", dteFrom.getDate());
									mapParameter.put("date_to", dteTo.getDate());
									
									System.out.println("date: "+dteTo.getDate());
									
									FncReport.generateReport("/Reports/rptFR_CD_forfeiture.jasper", cboRep.getSelectedItem().toString(), txtCo.getText(), mapParameter);
									
								}else if(cboRep.getSelectedIndex() == 22) {
									
									int choice = JOptionPane.showOptionDialog(
							                null, // Parent component (null for default)
							                "Please select reference date", // Message
							                "Confirmation", // Title
							                JOptionPane.YES_NO_OPTION, // Option type
							                JOptionPane.QUESTION_MESSAGE, // Message type
							                null, // Icon (null for default)
							                new String[]{"Actual Date", "Trans Date"}, // Options
							                ""
							        );
									
									
									if(choice == 0 || choice == 1) {
										Map<String, Object> mapParameter = new HashMap<String, Object>();
										mapParameter.put("co_id", txtCoID.getValue());
										mapParameter.put("company_name", txtCo.getText());
										mapParameter.put("proj_id", txtProID.getValue());
										mapParameter.put("proj_name", txtPro.getText());
										mapParameter.put("phase", txtPhaseID.getValue());
										mapParameter.put("branch_id", txtBranchID.getValue());
										mapParameter.put("as_of_date", dteTo.getDate());
										mapParameter.put("refer_date", choice);
										
										System.out.println("date: "+dteTo.getDate());
										
										FncReport.generateReport("/Reports/rptFR_SchedCD_DP.jasper", cboRep.getSelectedItem().toString(), txtCo.getText(), mapParameter);
										
									}
								
								}else if(cboRep.getSelectedIndex() == 23) {

									Map<String, Object> mapParameter = new HashMap<String, Object>();
									mapParameter.put("co_id", txtCoID.getValue());
									mapParameter.put("company_name", txtCo.getText());
									mapParameter.put("proj_id", txtProID.getValue());
									mapParameter.put("proj_name", txtPro.getText());
									mapParameter.put("phase", txtPhaseID.getValue());
									mapParameter.put("branch_id", txtBranchID.getValue());
									mapParameter.put("as_of_date", dteTo.getDate());
									
									System.out.println("date: "+dteTo.getDate());
									
									FncReport.generateReport("/Reports/rptFR_PDC_NotGood.jasper", cboRep.getSelectedItem().toString(), txtCo.getText(), mapParameter);
									
								}else if(cboRep.getSelectedIndex() == 24) {

									Map<String, Object> mapParameter = new HashMap<String, Object>();
									mapParameter.put("co_id", txtCoID.getValue());
									mapParameter.put("company_name", txtCo.getText());
									mapParameter.put("proj_id", txtProID.getValue());
									mapParameter.put("proj_name", txtPro.getText());
									mapParameter.put("phase", txtPhaseID.getValue());
									mapParameter.put("branch_id", txtBranchID.getValue());
									mapParameter.put("as_of_date", dteTo.getDate());
									
									System.out.println("date: "+dteTo.getDate());
									
									FncReport.generateReport("/Reports/rptFR_Sched_CDREg.jasper", cboRep.getSelectedItem().toString(), txtCo.getText(), mapParameter);
									
								}else if(cboRep.getSelectedIndex() == 25) {
									Map<String, Object> mapParameter = new HashMap<String, Object>();
									mapParameter.put("co_id", txtCoID.getValue());
									mapParameter.put("company_name", txtCo.getText());
									mapParameter.put("proj_id", txtProID.getValue());
									mapParameter.put("phase", txtPhaseID.getValue());
									
									FncReport.generateReport("/Reports/rptAdvancesFromBank_AOR.jasper", cboRep.getSelectedItem().toString(), txtCo.getText(), mapParameter);
								}
								
							}
						});
					}
					{
						btnExportXLS = new JButton("Export to XLS");
						panEnd.add(btnExportXLS, BorderLayout.CENTER);
						btnExportXLS.setFont(FncGlobal.sizeControls);
						btnExportXLS.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {

							}
						});
						btnExportXLS.setEnabled(false);
					}
					{
						btnExportPDF = new JButton("Export to PDF");
						panEnd.add(btnExportPDF, BorderLayout.CENTER);
						btnExportPDF.setFont(FncGlobal.sizeControls);
						btnExportPDF.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {

							}
						});
						btnExportPDF.setEnabled(false);
					}
				}
			}
		}

		DateMode(1);
		setDefaults();
		
		if (UserInfo.EmployeeCode.equals("900932") || UserInfo.EmployeeCode.equals("901120")) {
			cboRep.setSelectedIndex(18);
			cboRep.setEnabled(false);
			
			dteFrom.setEnabled(true);
			dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));	
		} else {
			cboRep.setEnabled(true);
		}
	}

	private void setDefaults() {
		txtCoID.setValue("02");
		txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");

		txtProID.setValue("015");
		txtPro.setText("TERRAVERDE RESIDENCES");

		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");

		txtBranchID.setValue("");
		txtBranch.setText("All Branches");	
	}

	public void actionPerformed(ActionEvent arg0) {

	}

	private void DateMode(Integer intMode) {
		System.out.println("Date Mode!");

		if (intMode==1) {
			panDate1 = new JXPanel(new GridBagLayout()); 
			{
				{					
					GridBagConstraints cons_date = new GridBagConstraints();
					cons_date.fill = GridBagConstraints.HORIZONTAL;
					cons_date.ipady = 10;
					cons_date.insets = new Insets(0, 5, 0, 5);

					{
						cons_date.weightx = 0.25;
						cons_date.weighty = 1;

						cons_date.gridx = 0;
						cons_date.gridy = 0; 

						JLabel lblFrom = new JLabel("From"); 
						panDate1.add(lblFrom, cons_date); 
						lblFrom.setHorizontalAlignment(JLabel.LEADING);
						lblFrom.setFont(FncGlobal.sizeLabel);
					}
					{
						cons_date.weightx = 1;
						cons_date.weighty = 1;

						cons_date.gridx = 1;
						cons_date.gridy = 0; 

						dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						dteFrom.getCalendarButton().setVisible(true);
						dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dteFrom.setEnabled(false);
						dteFrom.setEditable(true);
						dteFrom.setFont(FncGlobal.sizeTextValue);
						panDate1.add(dteFrom, cons_date);
					}
					{
						cons_date.weightx = 0.25;
						cons_date.weighty = 1;

						cons_date.gridx = 2;
						cons_date.gridy = 0; 

						JLabel lblFrom = new JLabel("To"); 
						panDate1.add(lblFrom, cons_date); 
						lblFrom.setHorizontalAlignment(JLabel.CENTER);
						lblFrom.setFont(FncGlobal.sizeLabel);
					}
					{
						cons_date.weightx = 1;
						cons_date.weighty = 1;

						cons_date.gridx = 3;
						cons_date.gridy = 0; 

						dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						dteTo.getCalendarButton().setVisible(true);
						dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dteTo.setFont(FncGlobal.sizeTextValue);
						dteTo.setEditable(true);
						panDate1.add(dteTo, cons_date);
					}
				}
			}
		} else {
			panDate1 = new JXPanel(new GridBagLayout()); 
			{
				GridBagConstraints cons_date = new GridBagConstraints();
				cons_date.fill = GridBagConstraints.HORIZONTAL;
				cons_date.ipady = 10;
				cons_date.insets = new Insets(0, 5, 0, 5);

				{
					cons_date.weightx = 0.25;
					cons_date.weighty = 1;

					cons_date.gridx = 0;
					cons_date.gridy = 0; 

					JLabel lblFrom = new JLabel("From"); 
					panDate1.add(lblFrom, cons_date); 
					lblFrom.setHorizontalAlignment(JLabel.LEADING);
					lblFrom.setFont(FncGlobal.sizeLabel);
				}
				{
					cons_date.weightx = 3;
					cons_date.weighty = 1;

					cons_date.gridx = 1;
					cons_date.gridy = 0; 

					JTextField text = new JTextField("TEXT 9"); 
					panDate1.add(text, cons_date);
					text.setHorizontalAlignment(JLabel.CENTER);
					text.setFont(FncGlobal.sizeTextValue);
				}
			}
		}

		panDateMode.add(panDate1, BorderLayout.CENTER);
	}
	
	private String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}
}
