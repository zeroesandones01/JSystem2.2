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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.apache.poi.ss.formula.functions.Now;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Functions.FncDate;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTagLabel;

public class CRES_GAE_MAF_INT_VAT extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {
	
	/*
	 * AS OF 2021-12-10
	 * 
	 * 1. MODIFY THE REPORT OF OUTPUT VAT - TRANSFER THE CODES INTO FUNCTION 2021-12-10
	 * 
	 * 
	 * */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "CRES / Expense / VAT Reports";
	static Dimension frameSize = new Dimension(500, 250);
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

	private JLabel lblCompany;
	private static JLabel lblProject;
	private static JLabel lblYear;
	private static JLabel lblPhase;

	private static _JTagLabel tagProject;
	private static _JTagLabel tagPhase;

	public static _JLookup lookupCompany;
	private static _JLookup lookupProject;
	private static _JLookup lookupPhase;
	public static JTextField txtCompany;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

	public String co_id 		= "";
	public String proj_id 		= "";
	public String proj_name		= "";
	public String phase 		= "";
	public String phase_name	= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String period_id		= "";
	public String phase_no 		= "";
	String company;
	public static String company_logo;	


	private JPanel pnlNorth_report;
	private JPanel pnlNorth_report_a;
	private JPanel pnlNorth_report_b;
			
	private static JLabel lblReport;
	private static JLabel lblMonthFrom;
	private static JLabel lblMonthTo;

	private static JComboBox cmbMonthFrom;
	private JComboBox cmbMonthTo;	

	private static JComboBox cmbReportName;
	private static JComboBox cmbYear;	
	
	private JButton btnCancel;
	private static JButton btnPreview;
	private JButton btnExport;
	
	public CRES_GAE_MAF_INT_VAT() {
		super(title, false, true, false, true);
		initGUI();
	}

	public CRES_GAE_MAF_INT_VAT(String title) {
		super(title);
		initGUI();
	}

	public CRES_GAE_MAF_INT_VAT(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(624, 422));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(621, 359));

			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(611, 136));

				{
					pnlNorth_comp = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorth_comp, BorderLayout.NORTH);
					pnlNorth_comp.setPreferredSize(new java.awt.Dimension(611, 65));
					pnlNorth_comp.setBorder(components.JTBorderFactory.createTitleBorder("Company"));// XXX
					{
						pnlNorthWest = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlNorth_comp.add(pnlNorthWest, BorderLayout.WEST);
						pnlNorthWest.setPreferredSize(new java.awt.Dimension(190, 51));
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
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){

										co_id = (String) data[0];
										company = (String) data[1];
										company_logo = (String) data[3];

										String name = (String) data[1];						
										txtCompany.setText(name);

										//enabledFields(true);
										lblReport.setEnabled(true);	
										cmbReportName.setEnabled(true);
										
										lblProject.setEnabled(true);
										lookupProject.setEnabled(true);
										tagProject.setEnabled(true);	
										
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
										
										lblYear.setEnabled(true);	
										cmbYear.setEnabled(true);
										
										lblMonthFrom.setEnabled(true);	
										cmbMonthFrom.setEnabled(true);
										
										lblMonthTo.setEnabled(true);	
										cmbMonthTo.setEnabled(true);
										
										btnPreview.setEnabled(true);
										btnCancel.setEnabled(true);
										btnExport.setEnabled(true);
										
										lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));	

									}
								}
							});
						}
					}
					pnlNorthEast = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlNorth_comp.add(pnlNorthEast, BorderLayout.CENTER);
					pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
					{
						txtCompany = new JTextField();
						pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
						txtCompany.setEditable(false);
					}
				}	
				{
					pnlNorth_report = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorth_report, BorderLayout.CENTER);
					pnlNorth_report.setPreferredSize(new java.awt.Dimension(611, 65));
					pnlNorth_report.setBorder(components.JTBorderFactory.createTitleBorder("Report Option"));// XXX
					
					{
						pnlNorth_report_a = new JPanel(new BorderLayout(0,0));
						pnlNorth_report.add(pnlNorth_report_a, BorderLayout.CENTER);
						pnlNorth_report_a.setPreferredSize(new java.awt.Dimension(70, 44));
						{
							lblReport = new JLabel("Report Name ", JLabel.TRAILING);
							pnlNorth_report_a.add(lblReport, BorderLayout.CENTER);
							lblReport.setPreferredSize(new java.awt.Dimension(95, 65));
							lblReport.setEnabled(false);							
						}

					}
					{
						pnlNorth_report_b = new JPanel(new BorderLayout(0,0));
						pnlNorth_report.add(pnlNorth_report_b, BorderLayout.EAST);
						pnlNorth_report_b.setPreferredSize(new java.awt.Dimension(497, 60));
						{
							String status[] = {
									"Cost of Real Estate Sales",
									"GAE & SME",
									"MAF & SOE",
									"Interest Expense",
									"Input VAT Schedule",
									"Input VAT Schedule - Version 2",
									"Input VAT Schedule(Zero VAT) - Version 2",
									"Output VAT Schedule"};					
							cmbReportName = new JComboBox(status);
							pnlNorth_report_b.add(cmbReportName);
							cmbReportName.setSelectedItem(null);
							cmbReportName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
							cmbReportName.setBounds(537, 15, 160, 21);	
							cmbReportName.setEnabled(false);
							cmbReportName.setSelectedIndex(0);	
							cmbReportName.setPreferredSize(new java.awt.Dimension(418, 65));
							cmbReportName.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent evt) 
								{	
								}
							});
						}
					}
					
				}
			}	
			{
				pnlCenter =  new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new java.awt.Dimension(499, 50));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));

				{		
					pnlCenter_a = new JPanel(new GridLayout(5, 1, 0, 5));
					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 185));

					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlCenter_a.add(lblProject);
						lblProject.setEnabled(false);	
					}	
					{
						lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlCenter_a.add(lblPhase);
						lblPhase.setEnabled(false);	
					}	
					{
						lblYear = new JLabel("Year", JLabel.TRAILING);
						pnlCenter_a.add(lblYear);
						lblYear.setEnabled(false);	
					}		
					{
						lblMonthFrom = new JLabel("Month (From)", JLabel.TRAILING);
						pnlCenter_a.add(lblMonthFrom);
						lblMonthFrom.setEnabled(false);	
					}
					{
						lblMonthTo = new JLabel("Month (To)", JLabel.TRAILING);
						pnlCenter_a.add(lblMonthTo);
						lblMonthTo.setEnabled(false);	
					}	

					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 118));
					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

					pnlDRFDtl_2a = new JPanel(new GridLayout(5
							, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2a, BorderLayout.WEST);
					pnlDRFDtl_2a.setPreferredSize(new java.awt.Dimension(100, 185));
					pnlDRFDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						lookupProject = new _JLookup(null, "Request Type", 2, 2);
						pnlDRFDtl_2a.add(lookupProject);
						lookupProject.setBounds(20, 27, 20, 25);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);
						lookupProject.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		
									proj_id 	= (String) data[0];		
									proj_name 	= (String) data[1];			
									tagProject.setTag(proj_name);
									
									lblPhase.setEnabled(true);	
									lookupPhase.setEnabled(true);	
									tagPhase.setEnabled(true);	
									lookupPhase.setLookupSQL(sql_phase(proj_id));	
									lookupPhase.setValue("");
									tagPhase.setTag("");
								}
							}
						});	
					}		
					{
						lookupPhase = new _JLookup(null, "Payee", 2, 2);
						pnlDRFDtl_2a.add(lookupPhase);
						lookupPhase.setBounds(20, 27, 20, 25);
						lookupPhase.setReturnColumn(3);
						lookupPhase.setEnabled(false);	
						lookupPhase.setPreferredSize(new java.awt.Dimension(157, 22));
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
						String status[] = {"2024", "2023","2022","2021","2020","2019","2018","2017","2016","2015","2014"};					
						cmbYear = new JComboBox(status);
						pnlDRFDtl_2a.add(cmbYear);
						cmbYear.setSelectedItem(null);
						cmbYear.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbYear.setBounds(537, 15, 160, 21);	
						cmbYear.setEnabled(false);
						cmbYear.setSelectedIndex(0);	
						cmbYear.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbYear.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{								
							}
						});
					}
					{
						String status[] = {
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
						cmbMonthFrom = new JComboBox(status);
						pnlDRFDtl_2a.add(cmbMonthFrom);
						cmbMonthFrom.setSelectedItem(null);
						cmbMonthFrom.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbMonthFrom.setBounds(537, 15, 160, 21);	
						cmbMonthFrom.setEnabled(false);
						cmbMonthFrom.setSelectedIndex(0);	
						cmbMonthFrom.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbMonthFrom.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{								
							}
						});
					}
					{
						String status[] = {
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
						cmbMonthTo = new JComboBox(status);
						pnlDRFDtl_2a.add(cmbMonthTo);
						cmbMonthTo.setSelectedItem(null);
						cmbMonthTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbMonthTo.setBounds(537, 15, 160, 21);	
						cmbMonthTo.setEnabled(false);
						cmbMonthTo.setSelectedIndex(0);	
						cmbMonthTo.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbMonthTo.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{								
							}
						});
					}

					pnlDRFDtl_2b = new JPanel(new GridLayout(5, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2b, BorderLayout.CENTER);
					pnlDRFDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
					pnlDRFDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						tagProject = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagProject);
						tagProject.setBounds(209, 27, 700, 22);
						tagProject.setEnabled(false);	
						tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagPhase = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagPhase);
						tagPhase.setBounds(209, 27, 700, 22);
						tagPhase.setEnabled(false);	
						tagPhase.setPreferredSize(new java.awt.Dimension(27, 33));
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
					btnPreview.setEnabled(false);
					btnPreview.setAlignmentY(0.5f);
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
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
				{
					btnExport = new JButton("Export");
					pnlSouth.add(btnExport);
					btnExport.setAlignmentX(0.5f);
					btnExport.setAlignmentY(0.5f);
					btnExport.setMaximumSize(new Dimension(100, 30));
					btnExport.setMnemonic(KeyEvent.VK_C);
					btnExport.setEnabled(false);
					btnExport.addActionListener(this);
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,	btnPreview));
		this.setBounds(0, 0, 624, 422);  //174
		
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
		String period_id_from = "";
		String period_id_to = "";

		if (proj_id.equals("")) {proj_name = "All";} else {}
		if (phase.equals("")) {phase_no = "All";} else {}
		
		if (cmbMonthFrom.getSelectedIndex()==0) {period_id_from = "01";}
		if (cmbMonthFrom.getSelectedIndex()==1) {period_id_from = "02";}
		if (cmbMonthFrom.getSelectedIndex()==2) {period_id_from = "03";}
		if (cmbMonthFrom.getSelectedIndex()==3) {period_id_from = "04";}
		if (cmbMonthFrom.getSelectedIndex()==4) {period_id_from = "05";}
		if (cmbMonthFrom.getSelectedIndex()==5) {period_id_from = "06";}
		if (cmbMonthFrom.getSelectedIndex()==6) {period_id_from = "07";}
		if (cmbMonthFrom.getSelectedIndex()==7) {period_id_from = "08";}
		if (cmbMonthFrom.getSelectedIndex()==8) {period_id_from = "09";}
		if (cmbMonthFrom.getSelectedIndex()==9) {period_id_from = "10";}
		if (cmbMonthFrom.getSelectedIndex()==10) {period_id_from = "11";}
		if (cmbMonthFrom.getSelectedIndex()==11) {period_id_from = "12";}
		
		if (cmbMonthTo.getSelectedIndex()==0) {period_id_to = "01";}
		if (cmbMonthTo.getSelectedIndex()==1) {period_id_to = "02";}
		if (cmbMonthTo.getSelectedIndex()==2) {period_id_to = "03";}
		if (cmbMonthTo.getSelectedIndex()==3) {period_id_to = "04";}
		if (cmbMonthTo.getSelectedIndex()==4) {period_id_to = "05";}
		if (cmbMonthTo.getSelectedIndex()==5) {period_id_to = "06";}
		if (cmbMonthTo.getSelectedIndex()==6) {period_id_to = "07";}
		if (cmbMonthTo.getSelectedIndex()==7) {period_id_to = "08";}
		if (cmbMonthTo.getSelectedIndex()==8) {period_id_to = "09";}
		if (cmbMonthTo.getSelectedIndex()==9) {period_id_to = "10";}
		if (cmbMonthTo.getSelectedIndex()==10) {period_id_to = "11";}
		if (cmbMonthTo.getSelectedIndex()==11) {period_id_to = "12";}
		
		if (e.getActionCommand().equals("Preview")) {	
			
			String criteria = "";
			if (cmbReportName.getSelectedIndex()==0) {criteria = "Annual Sales (Cash/Deferred)";}	
			else if (cmbReportName.getSelectedIndex()==1) {criteria = "Annual Sales (Installment)";}	
			else if (cmbReportName.getSelectedIndex()==2) {criteria = "Sales Schedule (All)";}	
			else if (cmbReportName.getSelectedIndex()==3) {criteria = "Annual Payment";}	
			
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id", co_id);
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("company", company);
			mapParameters.put("prepared_by", UserInfo.FirstName + " " +UserInfo.LastName);
			mapParameters.put("prepared_name", prepared_by_name);
			mapParameters.put("phase", phase);	
			mapParameters.put("phase_no", phase_no.toUpperCase());	
			mapParameters.put("month_from", cmbMonthFrom.getSelectedItem());	
			mapParameters.put("month_to", cmbMonthTo.getSelectedItem());	
			mapParameters.put("year", cmbYear.getSelectedItem());	
			mapParameters.put("period_id_from", period_id_from);	
			mapParameters.put("period_id_to", period_id_to);	
			mapParameters.put("proj_name", proj_name.toUpperCase());
			
			FncGlobal.startProgress("Generating" + reportTitle);
			
			if (cmbReportName.getSelectedIndex()==0) {
				FncReport.generateReport("/Reports/rptCostOfRealEstateSales.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==1) {
				FncReport.generateReport("/Reports/rptGAE_SME.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==2) {
				FncReport.generateReport("/Reports/rptMAF_SOE.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==3) {
				FncReport.generateReport("/Reports/rptInterest_Expense.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==4) {
				FncReport.generateReport("/Reports/rptBOI_Input_VAT.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==5) {
				Date dateObj = new Date();
				String strFrom = null;
				String strTo = null;
				
				String strLastDay = LastDay(period_id_to);
				
				/*
				strFrom = period_id_from + "-01-" + FncDate.getYear("YYYY", dateObj);
				strTo = period_id_from + "-" + strLastDay + "-" + FncDate.getYear("YYYY", dateObj);
				*/
				
				strFrom = period_id_from + "-01-" + cmbYear.getSelectedItem().toString();
				strTo = period_id_to + "-" + strLastDay + "-" + cmbYear.getSelectedItem().toString();
				
				System.out.println("");
				System.out.println("Date: " + dateObj);
				System.out.println("First Date: " + strFrom);
				System.out.println("Last Date: " + strTo);
				System.out.println("SQL: " + "SELECT * \n" +
						"FROM view_input_vat('"+co_id+"', '', '"+strFrom+"', '"+strTo+"') \n" +
						"WHERE c_vat::numeric(19, 2) != 0::numeric(19, 2) \n" +
						"ORDER BY c_goods_services"); 
				
				mapParameters.put("date_From", strFrom);	
				mapParameters.put("date_To", strTo);
				
				FncReport.generateReport("/Reports/rptBOI_Input_VAT_v2.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==6) {
				Date dateObj = new Date();
				String strFrom = null;
				String strTo = null;
				
				String strLastDay = LastDay(period_id_to);
				
				strFrom = period_id_from + "-01-" + cmbYear.getSelectedItem().toString();
				strTo = period_id_to + "-" + strLastDay + "-" + cmbYear.getSelectedItem().toString();
				
				System.out.println("");
				System.out.println("Date: " + dateObj);
				System.out.println("First Date: " + strFrom);
				System.out.println("Last Date: " + strTo);
				System.out.println("SQL: " + "SELECT * \n" +
						"FROM view_input_vat('"+co_id+"', '', '"+strFrom+"', '"+strTo+"') \n" +
						"WHERE c_vat::numeric(19, 2) = 0::numeric(19, 2) \n" +
						"ORDER BY c_goods_services"); 
				
				mapParameters.put("date_From", strFrom);	
				mapParameters.put("date_To", strTo);
				
				FncReport.generateReport("/Reports/rptBOI_Input_VAT_zero_vat_v2.jasper", reportTitle, "", mapParameters);
			} else if (cmbReportName.getSelectedIndex()==7) {
				/*MODIFY BY JED VICEDO 2021-12-10 : TRANSFER THE CODES IN REPORT IN FUNCTION*/
				FncReport.generateReport("/Reports/rptBOI_Output_VAT.jasper", reportTitle, "", mapParameters);
			}
			
			//FncGlobal.stopProgress();
		} else if (e.getActionCommand().equals("Cancel")) {
			
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
			cmbMonthFrom.setSelectedIndex(0);	
			cmbMonthTo.setSelectedIndex(0);	
			
			co_id 		= "";
			period_id	= "";
		} else if (e.getActionCommand().equals("Export")) {
			Date dateObj = new Date();
			String strFrom = null;
			String strTo = null;
			
			String strLastDay = LastDay(period_id_to);
			
			/*
			strFrom = period_id_from + "-01-" + FncDate.getYear("YYYY", dateObj);
			strTo = period_id_from + "-" + strLastDay + "-" + FncDate.getYear("YYYY", dateObj);
			*/
			
			strFrom = period_id_from + "-01-" + cmbYear.getSelectedItem().toString();
			strTo = period_id_from + "-" + strLastDay + "-" + cmbYear.getSelectedItem().toString();
			
			System.out.println("");
			System.out.println("Date: " + dateObj);
			System.out.println("First Date: " + strFrom);
			System.out.println("Last Date: " + strTo);
			
			String col_names [] = {"TIN", "Payee", "Availer", "Ref. Doc. No.", "Doc. Date", "Payee Address", "Availer Address", "Net Amount", "Transaction Amount", "VAT", "Goods/Services"};
			String strSQL = "SELECT * FROM view_input_vat('02', '', '"+strFrom+"', '"+strTo+"') ORDER BY c_goods_services";
			
			if (cmbReportName.getSelectedIndex()!=5) {
				JOptionPane.showMessageDialog(null, "The export function is, for the time being, only available for Input VAT Schedule - Version 2.");
			} else {
				FncGlobal.startProgress("Creating spreadsheet.");
				FncGlobal.CreateXLS(col_names, strSQL, "BOI Input VAT");
				FncGlobal.stopProgress();	
			}
		}

	}

	public static void enabledFields(boolean x){

		lblProject.setEnabled(x);	
		lblPhase.setEnabled(x);	
		lblYear.setEnabled(x);
		lblMonthFrom.setEnabled(x);	
		lblMonthTo.setEnabled(x);	
		lblReport.setEnabled(x);	

		lookupProject.setEnabled(x);	
		lookupPhase.setEnabled(x);	
		cmbYear.setEnabled(x);
		cmbMonthFrom.setEnabled(x);
		cmbReportName.setEnabled(x);

		tagProject.setEnabled(x);	
		tagPhase.setEnabled(x);	
		
		btnPreview.setEnabled(x);

	}

	public void refreshFields(){

		lookupProject.setValue("");	
		lookupPhase.setValue("");	

		tagProject.setTag("");
		tagPhase.setTag("");

		cmbYear.setSelectedIndex(0);	
		cmbMonthFrom.setSelectedIndex(0);	

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

		//enabledFields(true);
		lblReport.setEnabled(true);	
		cmbReportName.setEnabled(true);
		
		lblProject.setEnabled(true);
		lookupProject.setEnabled(true);
		tagProject.setEnabled(true);	
		
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
		
		lblYear.setEnabled(true);	
		cmbYear.setEnabled(true);
		
		lblMonthFrom.setEnabled(true);	
		cmbMonthFrom.setEnabled(true);
		
		lblMonthTo.setEnabled(true);	
		cmbMonthTo.setEnabled(true);
		
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		btnExport.setEnabled(true);
		
		/*	Modified by Mann2x; Modified Date: November 17, 2016; Error on Lookup	*/
		/*	lookupProject.setLookupSQL(co_id);	*/
		lookupProject.setLookupSQL(ProjectSQL(co_id));
		
		lookupCompany.setValue(co_id);
}

	//SQL
	public String sql_phase(String proj_id) {//XXX Phase
		String SQL = "select\n" +
		"getinteger(a.phase) as \"Phase\",\n" +
		"a.sub_proj_name as \"Description\",\n" +
		"b.proj_alias || getinteger(a.phase) as \"Alias\"," +
		"a.sub_proj_id as \"SubProj ID\"," +
		"coalesce(a.boi_registration_no,'') as \"BOI Reg. No.\" \n" +
		"from mf_sub_project a\n" +
		"left join mf_project b on a.proj_id = b.proj_id\n" +
		"where a.proj_id = '"+ proj_id +"'\n" +
		"and a.status_id = 'A'\n" +
		"group by getinteger(a.phase), b.proj_alias, a.sub_proj_id, sub_proj_name\n" +
		"order by getinteger(a.phase);";
		return SQL;
	}
	
	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_name, proj_alias\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
			   "ORDER BY proj_name";
	}
	
	private static String LastDay(String strMonth) {
		String strLastDay = "";
		
		if (strMonth.equals("01") || strMonth.equals("03") || strMonth.equals("05") || strMonth.equals("07") || strMonth.equals("08") || strMonth.equals("10") || strMonth.equals("12")) {
			strLastDay = "31";
		} else if (strMonth.equals("02")) {
			strLastDay = "28";
		} else {
			strLastDay = "30";
		}
		
		return strLastDay;
	}
}
