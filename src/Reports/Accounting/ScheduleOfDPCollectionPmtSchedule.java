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

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class ScheduleOfDPCollectionPmtSchedule extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Schedule of DP Collection and Payment Schedule";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCenter_b;
	private JPanel pnlSouth;
	private JPanel pnlCPFdate;

	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblYear;
	private JLabel lblMonth;
	private JLabel lblPhase;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPhase;	

	private JTextField txtProject;
	private JTextField txtCompany;	
	private JTextField txtPhase;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;


	String company;
	String company_logo;	
	String co_id 		= "";
	String proj_id 		= "";	
	String proj_name 	= "All";
	String phase_no 	= "";
	String phase		= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private JPanel pnlReport_type;
	private JPanel pnlReport_type_a;
	private JLabel lblReport;
	private JPanel pnlReport_type_b;
	private JComboBox cmbReportName;
	private JComboBox cmbYear;
	private JComboBox cmbMonth;	
	String period_id = ""; 
	String year = "";
	String date_to = "";

	public ScheduleOfDPCollectionPmtSchedule() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ScheduleOfDPCollectionPmtSchedule(String title) {
		super(title);
		initGUI();
	}

	public ScheduleOfDPCollectionPmtSchedule(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(591, 318));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(588, 319));

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.NORTH);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(578, 253));	

			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlCenter2.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(578, 122));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Cost Center Option" ));// XXX

				{
					pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(166, 169));

					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
						lblCompany.setEnabled(true);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorthWest.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setEnabled(true);
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id = (String) data[0];	
									company		= (String) data[1];	
									company_logo = (String) data[3];

									refreshFields();

									String name = (String) data[1];						
									txtCompany.setText(name);									

									refreshFields();
									enableFields();

									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
									lookupPhase.setLookupSQL(getPhase(lookupProject.getValue()));
									KEYBOARD_MANAGER.focusNextComponent();

								}
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
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									proj_id = (String) data[0];		
									proj_name = (String) data[1];	
									txtProject.setText(proj_name);
									btnPreview.setEnabled(true);
									lookupPhase.setLookupSQL(getPhase(lookupProject.getValue()));

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlNorthWest.add(lblPhase);
						lblPhase.setEnabled(false);	
					}
					{
						lookupPhase = new _JLookup(null, "Phase");
						pnlNorthWest.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(false);
						lookupPhase.setLookupSQL(getPhase(lookupProject.getValue()));
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									phase_no = (String) data[0];
									txtPhase.setText((String) data[1]);									
									KEYBOARD_MANAGER.focusNextComponent();
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
					pnlNorthEast.add(txtCompany, "Center");
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
					txtProject.setEnabled(false);
				}
				{
					txtPhase = new JTextField();
					pnlNorthEast.add(txtPhase, "Center");
					txtPhase.setEditable(false);
					txtPhase.setEnabled(false);
				}
			}
			{
				pnlCPFdate = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFdate, BorderLayout.CENTER);
				pnlCPFdate.setPreferredSize(new java.awt.Dimension(578, 68));		

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCPFdate.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(578, 62));	
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("Date Option"));

					{
						lblYear = new JLabel("Year :", JLabel.TRAILING);
						pnlCenter_b.add(lblYear, BorderLayout.CENTER);
						lblYear.setEnabled(true);	
						lblYear.setVisible(true);	
					}
					{
						String status[] = {
								"2024", "2023","2022","2021","2020","2019","2018","2017","2016","2015"};					
						cmbYear = new JComboBox(status);
						pnlCenter_b.add(cmbYear);
						cmbYear.setSelectedItem(null);
						cmbYear.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbYear.setBounds(537, 15, 160, 21);	
						cmbYear.setEnabled(true);
						cmbYear.setSelectedIndex(0);	
						cmbYear.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbYear.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{								
							}
						});
					}
					{
						lblMonth = new JLabel("Month :", JLabel.TRAILING);
						pnlCenter_b.add(lblMonth);
						lblMonth.setEnabled(true);	
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
						cmbMonth = new JComboBox(status);
						pnlCenter_b.add(cmbMonth);
						cmbMonth.setSelectedItem(null);
						cmbMonth.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbMonth.setBounds(537, 15, 160, 21);	
						cmbMonth.setEnabled(true);
						cmbMonth.setSelectedIndex(0);	
						cmbMonth.setPreferredSize(new java.awt.Dimension(418, 65));						
					}
				}	
				{
					pnlReport_type = new JPanel(new BorderLayout(0,0));
					pnlCenter2.add(pnlReport_type, BorderLayout.SOUTH);
					pnlReport_type.setPreferredSize(new java.awt.Dimension(578, 60));	
					pnlReport_type.setBorder(components.JTBorderFactory.createTitleBorder("Report Option"));

					{
						pnlReport_type_a = new JPanel(new
 BorderLayout(5,5));
						pnlReport_type.add(pnlReport_type_a, BorderLayout.CENTER);
						pnlReport_type_a.setPreferredSize(new java.awt.Dimension(107, 25));

						{
							lblReport = new JLabel("Report Name ", JLabel.TRAILING);
							pnlReport_type_a.add(lblReport, BorderLayout.CENTER);
							lblReport.setPreferredSize(new java.awt.Dimension(115, 25));
							lblReport.setEnabled(true);							
						}
					}
					{
						pnlReport_type_b = new JPanel(new BorderLayout(0,0));
						pnlReport_type.add(pnlReport_type_b, BorderLayout.EAST);
						pnlReport_type_b.setPreferredSize(new java.awt.Dimension(471, 25));
						{
							String status[] = {
									"DP Collection Status Report",
							"Payment Schedule Report"
							};					
							cmbReportName = new JComboBox(status);
							pnlReport_type_b.add(cmbReportName);
							cmbReportName.setSelectedItem(null);
							cmbReportName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
							cmbReportName.setBounds(537, 15, 160, 21);	
							cmbReportName.setEnabled(true);
							cmbReportName.setSelectedIndex(0);	
							cmbReportName.setPreferredSize(new java.awt.Dimension(418, 65));
						}

					}
				}
			}		
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new java.awt.Dimension(578, 29));

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
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 591, 318);  //174

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview"))
		{	
			/*if (cmbYear.getSelectedIndex()==0) {year = "2019";}
			if (cmbYear.getSelectedIndex()==1) {year = "2018";}
			if (cmbYear.getSelectedIndex()==2) {year = "2017";}
			if (cmbYear.getSelectedIndex()==3) {year = "2016";}
			if (cmbYear.getSelectedIndex()==4) {year = "2015";}*/
			year = (String) cmbYear.getSelectedItem();
			
			if (cmbMonth.getSelectedIndex()==0) {period_id = "01"; date_to = year + "-" + period_id+ "-" + "31";}
			if (cmbMonth.getSelectedIndex()==1) {period_id = "02"; date_to = year + "-" + period_id+ "-" + "28";}
			if (cmbMonth.getSelectedIndex()==2) {period_id = "03"; date_to = year + "-" + period_id+ "-" + "31";}
			if (cmbMonth.getSelectedIndex()==3) {period_id = "04"; date_to = year + "-" + period_id+ "-" + "30";;}
			if (cmbMonth.getSelectedIndex()==4) {period_id = "05"; date_to = year + "-" + period_id+ "-" + "31";}
			if (cmbMonth.getSelectedIndex()==5) {period_id = "06"; date_to = year + "-" + period_id+ "-" + "30";}
			if (cmbMonth.getSelectedIndex()==6) {period_id = "07"; date_to = year + "-" + period_id+ "-" + "31";}
			if (cmbMonth.getSelectedIndex()==7) {period_id = "08"; date_to = year + "-" + period_id+ "-" + "31";}
			if (cmbMonth.getSelectedIndex()==8) {period_id = "09"; date_to = year + "-" + period_id+ "-" + "30";}
			if (cmbMonth.getSelectedIndex()==9) {period_id = "10"; date_to = year + "-" + period_id+ "-" + "31";}
			if (cmbMonth.getSelectedIndex()==10) {period_id = "11"; date_to = year + "-" + period_id+ "-" + "30";;}
			if (cmbMonth.getSelectedIndex()==11) {period_id = "12"; date_to = year + "-" + period_id+ "-" + "31";}
			
			
			
			if(cmbReportName.getSelectedIndex()==0)
			{previewDP_collection(); }
			else if(cmbReportName.getSelectedIndex()==1)
			{previewPaymentScheduleRpt(); }
			else if(cmbReportName.getSelectedIndex()==3)
			{previewCheckStatusSummary(); }
		}
		if(e.getActionCommand().equals("Cancel"))
		{
			refreshFields();				
			proj_id 	= "";
			proj_name 	= "All";
			phase_no 	= "";
			phase		= "All";

			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(""));
			lookupPhase.setLookupSQL(getPhase(lookupProject.getValue()));

		}
		
	}


	//enable, disable
	public void refreshFields(){

		lookupProject.setText("");
		lookupPhase.setText("");

		txtProject.setText("");
		txtPhase.setText("");
		//dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
	}

	public void enableFields(){

		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		

		lblPhase.setEnabled(true);
		lookupPhase.setEnabled(true);
		txtPhase.setEnabled(true);

		lblYear.setEnabled(true);		
		//dteDateFrom.setEnabled(true);
		lblMonth.setEnabled(true);	
		//dateDateTo.setEnabled(true);

		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
				
		refreshFields();
		txtCompany.setText(company);									

		refreshFields();
		enableFields();

		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getPhase(lookupProject.getValue()));
		KEYBOARD_MANAGER.focusNextComponent();

		lookupCompany.setValue(co_id);
		company_logo = sql_getCompanyLogo(lookupCompany.getValue());
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


	//preview
	private void previewDP_collection(){

		String criteria = "Schedule of DP Collection Status";	
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("phase", phase);
		mapParameters.put("co_id", co_id);
		mapParameters.put("period_id", period_id);
		mapParameters.put("year", year);
		mapParameters.put("date_to", date_to);
		mapParameters.put("month", cmbMonth.getSelectedItem());

		FncReport.generateReport("/Reports/rptScheduleDPCollectionStatus.jasper", reportTitle, company, mapParameters);		

	}

	private void previewPaymentScheduleRpt(){

		String criteria = "Schedule of Payment Schedule Report";	
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("phase", phase);
		mapParameters.put("co_id", co_id);
		mapParameters.put("period_id", period_id);
		mapParameters.put("year", year);
		mapParameters.put("date_to", date_to);
		mapParameters.put("month", cmbMonth.getSelectedItem());

		FncReport.generateReport("/Reports/rptSchedulePaymentSchedulereport.jasper", reportTitle, company, mapParameters);		

	}
		
	private void previewCheckStatusSummary(){

		String criteria = "Schedule of Check Status Summary";	
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+
 company_logo));
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("phase", phase);
		mapParameters.put("co_id", co_id);
		mapParameters.put("period_id", period_id);
		mapParameters.put("year", year);
		mapParameters.put("date_to", date_to);
		mapParameters.put("month", cmbMonth.getSelectedItem());

		FncReport.generateReport("/Reports/rptCheckStatusSummary.jasper", reportTitle, company, mapParameters);		

	}


	//lookup SQL	
	public String getPhase(String proj_id){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(sub_proj_id) as \"Phase ID\",\r\n" + 
		"trim(phase) as \"Phase\"\r\n" + 
		"\r\n" + 
		"from mf_sub_project\r\n" + 
		"\r\n"
		+ " where (case when '' = '"+proj_id+"' then true else proj_id = '"+proj_id+"' end ) and status_id = 'A' " + //ADDED STATUS ID BY LESTER DCRF 2718 
		"order by sub_proj_id "  ;

	}

	public String getSalesAgent(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.entity_id as \"Employee ID\",\r\n" + 
		"upper(trim(b.entity_name)) as \"Employee Name\"\r\n" +
		"\r\n" + 
		"from em_employee a\r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 

		"order by b.entity_name"  ;

	}



}