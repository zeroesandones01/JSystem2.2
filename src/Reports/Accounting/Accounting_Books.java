package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXPanel;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
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

import com.mysql.jdbc.PreparedStatement.ParseInfo;
import com.toedter.calendar.JTextFieldDateEditor;
import components._JInternalFrame;
import components._JTagLabel;

public class Accounting_Books extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Books (CRB, CV, JV, PV)";
//	static Dimension frameSize = new Dimension(500, 250);
	static Dimension frameSize = new Dimension(800, 500);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlNorth_comp;
	private JPanel pnlDRFDtl_2a;
	private JPanel pnlDRFDtl_2b;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;
	private JPanel pnlNorth_report;
	private JPanel pnlNorth_report_a;
	private JPanel pnlNorth_report_b;

	private JLabel lblCompany;
	private static JLabel lblProject;
	private static JLabel lblYear;
	private static JLabel lblPhase;
	private static JLabel lblReport;
	//private static JLabel lblMonth;
	
	private static _JTagLabel tagProject;
	private static _JTagLabel tagPhase;

	public static _JLookup lookupCompany;
	private static _JLookup lookupProject;
	private static _JLookup lookupPhase;
	public static JTextField txtCompany;

	private static JButton btnPreview;
	private static JButton btnCancel;	
	
	private static JComboBox cmbReportName;
	private static JComboBox cmbYear;
	//private static JComboBox cmbMonth;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public String co_id 		= "";
	public String proj_id 		= "";
	public String proj_name		= "";
	public String phase 	= "";
	public String phase_name	= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String period_id	= "";
	public String phase_no 	= "";
	String company;
	public static String company_logo;	
	
	private String year		= "2017";
	private String date_from= "2017-01-01";
	private String date_to	= "2017-01-31";

	private _JTagLabel tagX;
	private JLabel lbl_date_fr;
	private _JDateChooser dte_from;
	private JLabel lblDate_to;
	private _JDateChooser dte_to;
	private JLabel lblInclude;
	private JComboBox cmbInclude;

	private JTextField txtpagestart;

	public String co_address;
	static String include_month 	= "";
	public  String report_month = "";;

	public Accounting_Books() {
		super(title, false, true, true, true);
		initGUI();
	}

	public Accounting_Books(String title) {
		super(title);
		initGUI();
	}

	public Accounting_Books(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
//		this.setPreferredSize(new java.awt.Dimension(605, 402));
//		this.setPreferredSize(frameSize);
//		this.setMinimumSize(frameSize);
//		this.setLayout(new BorderLayout());
//		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
//			getContentPane().add(pnlMain, BorderLayout.WEST);
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			pnlMain.setPreferredSize(new java.awt.Dimension(602, 375));
//			pnlMain.setPreferredSize(new java.awt.Dimension(1500, 375));
			{
				JXPanel pnlCenterr = new JXPanel(new GridBagLayout());
				pnlMain.add(pnlCenterr,BorderLayout.CENTER);
				{
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.fill = GridBagConstraints.BOTH;
					{
						gbc.weightx = 1;
						gbc.weighty = 0.25;

						gbc.gridx = 0;
						gbc.gridy = 0;
						
						pnlNorth_comp = new JPanel(new GridBagLayout());
						pnlCenterr.add(pnlNorth_comp, gbc);
						pnlNorth_comp.setBorder(components.JTBorderFactory.createTitleBorder("Company"));
						{
							GridBagConstraints bagOne = new GridBagConstraints();
							bagOne.fill = GridBagConstraints.BOTH;
							bagOne.insets = new Insets(5,5,5,5);
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;

								bagOne.gridx = 0;
								bagOne.gridy = 0;
								
								
								lblCompany = new JLabel("Company", JLabel.TRAILING);
								pnlNorth_comp.add(lblCompany,bagOne);
								lblCompany.setFont(FncGlobal.sizeLabel);
								
							}
							
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;

								bagOne.gridx = 1;
								bagOne.gridy = 0;
								
								lookupCompany = new _JLookup(null, "Company");
								pnlNorth_comp.add(lookupCompany,bagOne);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(get_COMPANY());
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								lookupCompany.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											co_id = (String) data[0];
											company = (String) data[1];
											company_logo = (String) data[3];
											co_address = (String) data[4];	
											String name = (String) data[1];						
											txtCompany.setText(name);

											enabledFields(true);
											lookupProject.setLookupSQL(lookupMethods.getProject(lookupCompany.getValue()));	
											
											lblPhase.setEnabled(false);	
											lookupPhase.setEnabled(false);	
											tagPhase.setEnabled(false);									
											
											lookupProject.setValue("");	
											tagProject.setTag("");	
											proj_id 	= "";
											proj_name	= "";
											phase_no 	= "";
											
											lookupPhase.setValue("");	
											tagPhase.setTag("");	
											phase 	= "";
											phase_name	= "";
											phase_no 	= "";

										}
									}
								});
							}
							{
								bagOne.weightx = 1.5;
								bagOne.weighty = 1;

								bagOne.gridx = 2;
								bagOne.gridy = 0;
								bagOne.gridwidth = 2;
								
								txtCompany = new JTextField();
								pnlNorth_comp.add(txtCompany,bagOne);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						gbc.weightx = 1;
						gbc.weighty = 0.25;

						gbc.gridx = 0;
						gbc.gridy = 1;
						
						pnlNorth_report = new JPanel(new GridBagLayout());
						pnlCenterr.add(pnlNorth_report, gbc);
						pnlNorth_report.setBorder(components.JTBorderFactory.createTitleBorder("Report Option"));
						{
							GridBagConstraints bagTwo = new GridBagConstraints();
							bagTwo.fill = GridBagConstraints.BOTH;
							bagTwo.insets = new Insets(5,5,5,5);
							{
								bagTwo.weightx = 0;
								bagTwo.weighty = 1;

								bagTwo.gridx = 0;
								bagTwo.gridy = 0;
								bagTwo.gridwidth = 1;
								
								lblReport = new JLabel("Report Name ", JLabel.TRAILING);
								pnlNorth_report.add(lblReport, bagTwo);
								lblReport.setEnabled(false);	
								lblReport.setFont(FncGlobal.sizeLabel);
							}
							{
								bagTwo.weightx = 1.5;
								bagTwo.weighty = 1;

								bagTwo.gridx = 1;
								bagTwo.gridy = 0;
								bagTwo.gridwidth = 2;
								
								String status[] = {
										"Journal Voucher (JV)",
										"Cash Receipt Book (CRB)",
										"Cash Disbursement Book (CDB)",
										"Summary of Deleted Entries",
										"Summary of Posted Entries",
										"Summary of Active Entries",
										//"Payable Voucher (PV)"
										"Payable Voucher Register",
										"General Ledger Book",
										"Cash Receipts Book (Late OR / SI)" //Dcrf # 2546
										};					
								cmbReportName = new JComboBox(status);
								pnlNorth_report.add(cmbReportName,bagTwo);
								cmbReportName.setSelectedItem(null);
								cmbReportName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
								cmbReportName.setEnabled(false);
								cmbReportName.setSelectedIndex(0);	
								cmbReportName.setFont(FncGlobal.sizeTextValue);
								cmbReportName.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent evt) 
									{
										//added by erick 2023/01/18
										if (cmbReportName.getSelectedIndex()==7) {
											cmbYear.setEditable(false);
											cmbYear.setEnabled(false);
											lookupProject.setEditable(false);
											lookupPhase.setEnabled(false);
											cmbInclude.setEditable(false);
											lookupProject.setValue("");	
											tagProject.setTag("");	
											proj_id 	= "";
											proj_name	= "";
											phase_no 	= "";
											lookupPhase.setValue("");	
											tagPhase.setTag("");	
											phase 	= "";
											phase_name	= "";
											phase_no 	= "";
											cmbYear.setSelectedIndex(0);
											period_id	= "";
											txtpagestart.setText("0");
											
											
										}else {
											cmbYear.setEditable(true);
											cmbYear.setEnabled(true);
											lookupProject.setEditable(true);
											cmbInclude.setEditable(true);
											
											lookupProject.setValue("");	
											tagProject.setTag("");	
											proj_id 	= "";
											proj_name	= "";
											phase_no 	= "";
											
											lookupPhase.setValue("");	
											tagPhase.setTag("");	
											phase 	= "";
											phase_name	= "";
											phase_no 	= "";
											cmbYear.setSelectedIndex(0);
											period_id	= "";
											txtpagestart.setText("0");
											
										}
									}
								});
							}
						}
					}
					{
						gbc.weightx = 1;
						gbc.weighty = 2;

						gbc.gridx = 0;
						gbc.gridy = 2;
						
						pnlCenter =  new JPanel(new GridBagLayout());
						pnlCenterr.add(pnlCenter, gbc);
						pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
						{
							GridBagConstraints bagThree = new GridBagConstraints();
							bagThree.fill = GridBagConstraints.BOTH;
							bagThree.insets = new Insets(5,5,5,5);
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 0;
								bagThree.gridy = 0;
								
								
								lblProject = new JLabel("Project", JLabel.TRAILING);
								pnlCenter.add(lblProject,bagThree);
								lblProject.setEnabled(false);
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 1;
								bagThree.gridy = 0;
								
								lookupProject = new _JLookup(null, "Request Type", 2, 2);
								pnlCenter.add(lookupProject,bagThree);
//								lookupProject.setBounds(20, 27, 20, 25);
								lookupProject.setReturnColumn(0);
								lookupProject.setEnabled(false);
//								lookupProject.setPreferredSize(new java.awt.Dimension(157, 22));
								lookupProject.setFont(FncGlobal.sizeTextValue);
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											proj_id 	= (String) data[0];		
											proj_name 	= (String) data[1];			
											tagProject.setTag(proj_name);
											lookupPhase.setLookupSQL(sql_phase(proj_id));	
											
											lblPhase.setEnabled(true);	
											lookupPhase.setEnabled(true);	
											tagPhase.setEnabled(true);										
											
											lookupPhase.setValue("");	
											tagPhase.setTag("");	
											phase 	= "";
											phase_name	= "";
											phase_no 	= "";
										}
									}
								});	
							}
							{
								bagThree.weightx = 1.5;
								bagThree.weighty = 1;

								bagThree.gridx = 2;
								bagThree.gridy = 0;
								
								tagProject = new _JTagLabel("[ ]");
								pnlCenter.add(tagProject,bagThree);
//								tagProject.setBounds(209, 27, 700, 22);
								tagProject.setEnabled(false);	
//								tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
								tagProject.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 0;
								bagThree.gridy = 1;
								
								lblPhase = new JLabel("Phase", JLabel.TRAILING);
								pnlCenter.add(lblPhase,bagThree);
								lblPhase.setEnabled(false);	
								lblPhase.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 1;
								bagThree.gridy = 1;
								
								lookupPhase = new _JLookup(null, "Payee", 2, 2);
								pnlCenter.add(lookupPhase,bagThree);
//								lookupPhase.setBounds(20, 27, 20, 25);
								lookupPhase.setReturnColumn(3);
								lookupPhase.setEnabled(false);	
//								lookupPhase.setPreferredSize(new java.awt.Dimension(157, 22));
								lookupPhase.setFont(FncGlobal.sizeTextValue);
								lookupPhase.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){	
											phase 	= data[3].toString();		
											phase_name 	= (String) data[1];		
											phase_no 	= data[0].toString();		
											tagPhase.setTag(phase_name);
										}
									}
								});	
							}
							{
								bagThree.weightx = 1;
								bagThree.weighty = 1;

								bagThree.gridx = 2;
								bagThree.gridy = 1;
								
								tagPhase = new _JTagLabel("[ ]");
								pnlCenter.add(tagPhase,bagThree);
								tagPhase.setBounds(209, 27, 700, 22);
								tagPhase.setEnabled(false);	
//								tagPhase.setPreferredSize(new java.awt.Dimension(27, 33));
								tagPhase.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 0;
								bagThree.gridy = 2;
								
								lblYear = new JLabel("Year", JLabel.TRAILING);
								pnlCenter.add(lblYear,bagThree);
								lblYear.setEnabled(false);
								lblYear.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 1;
								bagThree.gridy = 2;
								
								String status[] = {
										"2024",
										"2023",
										"2022",
										"2021",
										"2020",
										"2019",
										"2018",
										"2017",
										"2016",
										"2015"};					
								cmbYear = new JComboBox(status);
								pnlCenter.add(cmbYear,bagThree);
								cmbYear.setSelectedItem(null);
//								cmbYear.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
								cmbYear.setFont(FncGlobal.sizeTextValue);
								cmbYear.setBounds(537, 15, 160, 21);	
								cmbYear.setEnabled(false);
								cmbYear.setSelectedIndex(0);	
//								cmbYear.setPreferredSize(new java.awt.Dimension(418, 65));
								cmbYear.setFont(FncGlobal.sizeTextValue);
								cmbYear.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent evt) 
									{								
									}
								});
							}
							{
								bagThree.weightx = 1.5;
								bagThree.weighty = 1;

								bagThree.gridx = 2;
								bagThree.gridy = 2;
								
								tagX = new _JTagLabel("[ ]");
								pnlCenter.add(tagX,bagThree);
//								tagX.setBounds(209, 27, 700, 22);
								tagX.setEnabled(false);	
//								tagX.setPreferredSize(new java.awt.Dimension(27, 33));
								tagX.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0;
								bagThree.weighty = 1;

								bagThree.gridx = 0;
								bagThree.gridy = 3;
								
								lbl_date_fr = new JLabel("Date From", JLabel.TRAILING);
								pnlCenter.add(lbl_date_fr,bagThree);
								lbl_date_fr.setEnabled(true);	
								lbl_date_fr.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 1;
								bagThree.gridy = 3;
								
								dte_from = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlCenter.add(dte_from,bagThree);
								dte_from.getCalendarButton().setVisible(true);
								dte_from.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dte_from.setFont(FncGlobal.sizeTextValue);
								dte_from.addPropertyChangeListener(new PropertyChangeListener() {
									public void propertyChange(PropertyChangeEvent evt) {
										//date_from = dte_from.getDate().toString();
										//System.out.println("\ndate_from: "+date_from);
										
									}
								});
								dte_from.addDateListener(new DateListener() {
									public void datePerformed(DateEvent event) {
										
										System.out.println("\ndte_from: "+dte_from);
										System.out.println("\ndte_from: "+dte_from.getDate());
										//System.out.println("\nMonth: "+dte_from.getDate().getMonth());
										DateFormat df = new SimpleDateFormat("yyyy-dd-MM");					
										String start_date = df.format(dte_from.getDate());	
										String month = FncGlobal.GetString("select split_part('"+start_date+"','-',3); ");
										String year = FncGlobal.GetString("select split_part('"+start_date+"','-',1); ");
										System.out.println("\nstart_date: "+start_date);
										System.out.println("\nDFMonth: "+month);
										
										if (month.equals("01")) {
											report_month = "JANUARY ".concat(year);
										}else if(month.equals("02")) {
											report_month = "FEBRUARY ".concat(year);
										}else if(month.equals("03")) {
											report_month = "MARCH ".concat(year);
										}else if(month.equals("04")) {
											report_month = "APRIL ".concat(year);
										}else if(month.equals("05")) {
											report_month = "MAY ".concat(year);
										}else if(month.equals("06")) {
											report_month = "JUNE ".concat(year);
										}else if(month.equals("07")) {
											report_month = "JULY ".concat(year);
										}else if(month.equals("08")) {
											report_month = "AUGUST ".concat(year);
										}else if(month.equals("09")) {
											report_month = "SEPTEMBER ".concat(year);
										}else if(month.equals("10")) {
											report_month = "OCTOBER ".concat(year);
										}else if(month.equals("11")) {
											report_month = "NOVEMBER ".concat(year);
										}else if(month.equals("12")) {
											report_month = "DECEMBER ".concat(year);
										}
										
										System.out.println("report_month:"+report_month);
									}
								});
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 0;
								bagThree.gridy = 4;
								
								lblDate_to = new JLabel("Date To", JLabel.TRAILING);
								pnlCenter.add(lblDate_to, bagThree);
								lblDate_to.setEnabled(true);
								lblDate_to.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 1;
								bagThree.gridy = 4;
								
								dte_to = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlCenter.add(dte_to,bagThree);
								dte_to.getCalendarButton().setVisible(true);
								dte_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dte_to.setFont(FncGlobal.sizeTextValue);
								dte_to.addPropertyChangeListener(new PropertyChangeListener() {
									public void propertyChange(PropertyChangeEvent evt) {
										//date_to = dte_to.getDate().toString();	

										DateFormat df = new SimpleDateFormat("yyyy-dd-MM");					
										String end_date = df.format(dte_to.getDate());	
										String end_date_sub = end_date.substring(5);	

										if(end_date_sub.equals("31-12")) {
											cmbInclude.setEnabled(true);
											lblInclude.setEnabled(true);
										} else {
											cmbInclude.setEnabled(false);
											cmbInclude.setSelectedIndex(0);
											lblInclude.setEnabled(false);
										}
										
										System.out.println("\ndate_to: "+date_to);
										System.out.println("\ndte_to: "+dte_to.getDate());
									}
								});
								dte_to.addDateListener(new DateListener() {
									public void datePerformed(DateEvent event) {
										System.out.println("\ndate_to: "+date_to);
										System.out.println("\ndte_to: "+dte_to.getDate());
									}
								});
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 0;
								bagThree.gridy = 5;
								
								lblInclude = new JLabel("Include (Until)", JLabel.TRAILING);
								pnlCenter.add(lblInclude,bagThree);
								lblInclude.setEnabled(false);
								lblInclude.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 1;
								bagThree.gridy = 5;
								
								String status2[] = { "(None)", "13th Month","14th Month","15th Month" };
								cmbInclude = new JComboBox(status2);
								pnlCenter.add(cmbInclude,bagThree);
								cmbInclude.setSelectedItem(null);
								cmbInclude.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
//								cmbInclude.setBounds(537, 15, 180, 21);
								cmbInclude.setEnabled(false);
								cmbInclude.setSelectedIndex(0);
//								cmbInclude.setPreferredSize(new java.awt.Dimension(161, 23));
								cmbInclude.setFont(FncGlobal.sizeTextValue);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 0;
								bagThree.gridy = 6;
								
								lblInclude = new JLabel("Last page", JLabel.TRAILING);
								pnlCenter.add(lblInclude,bagThree);
								lblInclude.setEnabled(true);
								lblInclude.setFont(FncGlobal.sizeLabel);
							}
							{
								bagThree.weightx = 0.5;
								bagThree.weighty = 1;

								bagThree.gridx = 1;
								bagThree.gridy = 6;
								
								txtpagestart = new JTextField("0");
								pnlCenter.add(txtpagestart,bagThree);
								txtpagestart.setEnabled(true);
								txtpagestart.setFont(FncGlobal.sizeTextValue);
							}

						}
					}
				}
			}

				
			{
				

				{		
					

				
					/*
					{
						lblMonth = new JLabel("Month", JLabel.TRAILING);
						pnlCenter_a.add(lblMonth);
						lblMonth.setEnabled(false);	
					}	
					*/
				

		
					
					/*
					{
						String status[] = {
								"All", 
								"January",
								"February",
								"March",
								"April",
								"May",
								"June",
								"July",
								"August",
								"September",
								"October",
								"November",
								"December"

						};		
						
						
						cmbMonth = new JComboBox(status);
						pnlDRFDtl_2a.add(cmbMonth);
						cmbMonth.setSelectedItem(null);
						cmbMonth.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbMonth.setBounds(537, 15, 160, 21);	
						cmbMonth.setEnabled(false);
						cmbMonth.setSelectedIndex(0);	
						cmbMonth.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbMonth.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{	
													
							
							}
						});
					}
					*/
					/*
					{
						dte_from = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlDRFDtl_2a.add(dte_from);
						dte_from.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dte_from.getDateEditor()).setEditable(true);
						dte_from.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dte_from.addPropertyChangeListener( new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent e) {
								date_from = dte_from.getDate().toString();
								
								System.out.println();
							}
						});	
					}
					*/
					
					/*
					{
						dte_to = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlDRFDtl_2a.add(dte_to);
						dte_to.setBounds(485, 7, 125, 21);
						dte_to.setDate(null);
						dte_to.setEnabled(true);
						dte_to.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dte_to.getDateEditor()).setEditable(true);
						dte_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dte_to.addPropertyChangeListener( new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent e) {
								date_to = dte_to.getDate().toString();	

								DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");					
								String end_date	 = df.format(dte_to.getDate());	
								String end_date_sub = end_date.substring(5);	

								if(end_date_sub.equals("31-12"))
								{
									cmbInclude.setEnabled(true);
									lblInclude.setEnabled(true);
								}
								else
								{
									cmbInclude.setEnabled(false);
									cmbInclude.setSelectedIndex(0);
									lblInclude.setEnabled(false);
								}
							}
						});	
					}
					*/
				

					

					
				}
			}

			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));

				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setEnabled(false);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setFont(FncGlobal.sizeControls);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setFont(FncGlobal.sizeControls);
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,	btnPreview));
//		this.setBounds(0, 0, 605, 402);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {

	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Preview")) {	
			//added by Erick 2022-09-15 dcrf#2189
			String tin = "";
			if (lookupCompany.getValue().equals("01")) {tin = "237-765-097-00000";}//VDC
			else if(lookupCompany.getValue().equals("02")) {tin = "230-799-534-00000";}//CDC
			else if(lookupCompany.getValue().equals("04")) {tin = "211-776-978-00000";}//ADC
			else if(lookupCompany.getValue().equals("05")) {tin = "000-761-994-00000";}//EDC
			
			String criteria = "";
			if (cmbReportName.getSelectedIndex()==0) {criteria = "Journal Voucher (JV)";}	
			else if (cmbReportName.getSelectedIndex()==1) {criteria = "Cash Receipt Book (CRB)";}	
			else if (cmbReportName.getSelectedIndex()==2) {criteria = "Cash Disbursement Book (CDB)";}	
			else if (cmbReportName.getSelectedIndex()==3) {criteria = "Payable Voucher (PV)";}	
			else if (cmbReportName.getSelectedIndex()==6) {criteria ="Payable Voucher Register";}
			else if (cmbReportName.getSelectedIndex()==7) {criteria ="General Ledger Book";}
			else if (cmbReportName.getSelectedIndex()==8) {criteria ="Cash Receipts Book (Late OR / SI)";}
			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			String period_id = "";
			String period_header = "";
			String acct_type = "";
			year = (String) cmbYear.getSelectedItem();
			
			if (cmbInclude.getSelectedIndex()==0) {
				include_month = "";
			} else if(cmbInclude.getSelectedIndex()==1){
				include_month = "13";
			} else if(cmbInclude.getSelectedIndex()==2){
				include_month = "14";
			} else if (cmbInclude.getSelectedIndex()==3){
				include_month = "15";
			}

			if (proj_id.equals("")) {
				proj_name = "All";
			} else {
				
			}

			if (phase.equals("")) {
				phase_no = "All";
			} else {

			}	
						
			year = cmbYear.getSelectedItem().toString();								
						
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("proj_id", ((proj_id=="All") ? "" : proj_id));
			mapParameters.put("company", company);
			mapParameters.put("prepared_by",UserInfo.Alias);
			mapParameters.put("prepared_name", prepared_by_name);
			mapParameters.put("phase", phase);	
			mapParameters.put("phase_no", (phase_no=="All") ? "" : phase_no);
			mapParameters.put("phase_no2", phase_no);
			mapParameters.put("year", cmbYear.getSelectedItem());	
			mapParameters.put("period_id", period_id);	
			mapParameters.put("proj_name", proj_name);
			mapParameters.put("date_from", dte_from.getDate().toString());	
			mapParameters.put("date_to", dte_to.getDate().toString());	
			mapParameters.put("period_header", period_header);
			mapParameters.put("include_month", include_month);	
			mapParameters.put("date_from2", dte_from.getDate());	
			mapParameters.put("date_to2", dte_to.getDate());
			mapParameters.put("pagestart", Integer.parseInt(txtpagestart.getText()));
			mapParameters.put("co_address", co_address);
			mapParameters.put("tin", tin);
			mapParameters.put("report_month", report_month);
			mapParameters.put("user_id", UserInfo.EmployeeCode);

			System.out.println("co_id: "+lookupCompany.getValue());
			System.out.println("year: "+cmbYear.getSelectedItem());
			System.out.println("period_id: "+period_id);
			System.out.println("date_from: "+dte_from.getDate());
			System.out.println("date_to: "+dte_to.getDate());
			System.out.println("include_month: "+include_month);
			
			if (cmbReportName.getSelectedIndex()==0) {
				FncReport.generateReport("/Reports/rptJV_book.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==1) {
				FncReport.generateReport("/Reports/rptCRB_book.jasper", reportTitle+" 1", "", mapParameters);
				//FncReport.generateReport("/Reports/rptCRB_book_v2.jasper", reportTitle+" 2", "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==2) {
				/*	Modified by Mann2x; Date Modified: April 16, 2019;
				FncReport.generateReport("/Reports/rptCDB_book.jasper", reportTitle, "", mapParameters);
				*/
				FncReport.generateReport("/Reports/rptCDB_book_v2.jasper", reportTitle, "", mapParameters);
				FncReport.generateReport("/Reports/rptCDB_book_subtable_v2.jasper", reportTitle+" Subreport", "", mapParameters);
			}else if (cmbReportName.getSelectedIndex()==6) {
				FncReport.generateReport("/Reports/rptPV_register.jasper", reportTitle, "", mapParameters);
			}else if (cmbReportName.getSelectedIndex()==7) {
				
				Object[] option= {"ASSETS","LIABILITIES","EQUITY","SALES & REVENUE"}; 
				
				int Option=JOptionPane.showOptionDialog(getTopLevelAncestor(), "Please select report to generate.", "Account type option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
				if(Option == 0) {
					System.out.println("Selectted 0");
					acct_type = "01";
				}
				if(Option == 1) {
					System.out.println("Selectted 1");
					acct_type = "02";
				}
				if(Option == 2) {
					System.out.println("Selectted 2");
					acct_type = "03";
				}
				if(Option == 3) {
					System.out.println("Selectted 3");
					acct_type = "04";
				}
				mapParameters.put("acct_type", acct_type);
				FncReport.generateReport("/Reports/rptGL_BIR_preview_v2.jasper", reportTitle, "", mapParameters);
			
			}else if (cmbReportName.getSelectedIndex()==8) {
				FncReport.generateReport("/Reports/rptCRB_book_lateOR_SI.jasper", reportTitle, "", mapParameters);
			}
			
			
			
			String strStatus = "";
			if (cmbReportName.getSelectedIndex()==3) {
				strStatus = "Deleted"; 
			} else if (cmbReportName.getSelectedIndex()==4) {
				strStatus = "Posted";
			} else if (cmbReportName.getSelectedIndex()==5) {
				strStatus = "Active";
			}
			
			String company_logo = RequestForPayment.sql_getCompanyLogo();
			
			Map<String, Object> mapParameters1 = new HashMap<String, Object>();
			mapParameters1.put("01_Company", company);
			mapParameters1.put("02_AsOfDate", dateFormat.format(dte_from.getDate()));
			mapParameters1.put("03_CoID", co_id);
			mapParameters1.put("04_ProID", proj_id);
			mapParameters1.put("06_Project", ""); 
			mapParameters1.put("07_User", prepared_by_name);
			mapParameters1.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters1.put("09_Batch", ""); 
			mapParameters1.put("10_branch_id", ""); 
			mapParameters1.put("11_phase_id", FncGlobal.GetString("select phase from mf_sub_project where sub_proj_id = '"+phase_no+"' AND status_id = 'A'"));//ADDED STATUS ID BY LESTER DCRF 2718 
			mapParameters1.put("12_AsOfDateTo", dateFormat.format(dte_to.getDate()));
			mapParameters1.put("13_Status", strStatus);
					
			if (cmbReportName.getSelectedIndex()==3 || cmbReportName.getSelectedIndex()==4 || cmbReportName.getSelectedIndex()==5) {
				FncReport.generateReport("/Reports/rptCRB_variable_status.jasper", reportTitle, "", mapParameters1);
			}
		}
		
		if (e.getActionCommand().equals("Cancel")) {
			lookupProject.setValue("");	
			tagProject.setTag("");	
			proj_id 	= "";
			proj_name	= "";
			phase_no 	= "";
			
			lookupPhase.setValue("");	
			tagPhase.setTag("");	
			phase 	= "";
			phase_name	= "";
			phase_no 	= "";
			
			lblPhase.setEnabled(false);	
			lookupPhase.setEnabled(false);	
			tagPhase.setEnabled(false);		
			
			cmbReportName.setSelectedIndex(0);	
			cmbYear.setSelectedIndex(0);	
			//cmbMonth.setSelectedIndex(0);	

			co_id 		= "";
			period_id	= "";
			txtpagestart.setText("0");
		}

	}

	public static void enabledFields(boolean x){

		lblProject.setEnabled(x);	
		lblPhase.setEnabled(x);	
		lblYear.setEnabled(x);
		//lblMonth.setEnabled(x);	
		lblReport.setEnabled(x);	

		lookupProject.setEnabled(x);	
		lookupPhase.setEnabled(x);	
		cmbYear.setEnabled(x);
		//cmbMonth.setEnabled(x);
		cmbReportName.setEnabled(x);

		tagProject.setEnabled(x);	
		tagPhase.setEnabled(x);	
		
		btnPreview.setEnabled(x);
		btnCancel.setEnabled(x);

	}

	public void refreshFields(){

		lookupProject.setValue("");	
		lookupPhase.setValue("");	

		tagProject.setTag("");
		tagPhase.setTag("");

		cmbYear.setSelectedIndex(0);	
		//cmbMonth.setSelectedIndex(0);	

		proj_id 	= "";
		proj_name	= "";
		phase 		= "";
		phase_name		= "";

		prepared_by_id	= "";
		prepared_by_name= "";
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";	
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);
		co_address = "4th Floor Aster Business Center,Mandala Park,312 Shaw Boulevard,Pleasant Hills,Mandaluyong City 1550";

		enabledFields(true);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));	
		
		lblPhase.setEnabled(false);	
		lookupPhase.setEnabled(false);	
		tagPhase.setEnabled(false);									
		
		lookupProject.setValue("");	
		tagProject.setTag("");	
		proj_id 	= "";
		proj_name	= "";
		phase_no 	= "";
		
		lookupPhase.setValue("");	
		tagPhase.setTag("");	
		phase 	= "";
		phase_name	= "";
		phase_no 	= "";
		
		lookupCompany.setValue(co_id);
}
	public static String get_COMPANY() {//XXX Company
		/* edited on No. 26, 2014 by Del Gonzales as needed in PV preview */
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\", company_address as \"Address\" FROM mf_company order by co_id ";
		return SQL;
}
	

	//SQL
	public String sql_phase(String proj_id) {//XXX Phase
		String SQL = "select\n" +
		"getinteger(a.phase) as \"Phase\",\n" +
		"a.sub_proj_name as \"Description\",\n" +
		"b.proj_alias || getinteger(a.phase) as \"Alias\"," +
		"a.sub_proj_id as \"SubProj ID\" \n" +
		"from mf_sub_project a\n" +
		"left join mf_project b on a.proj_id = b.proj_id\n" +
		"where a.proj_id = '"+ proj_id +"'\n" +
		"and a.status_id = 'A'\n" +
		"group by getinteger(a.phase), b.proj_alias, a.sub_proj_id, sub_proj_name\n" +
		"order by getinteger(a.phase);";
		return SQL;
	}

}
