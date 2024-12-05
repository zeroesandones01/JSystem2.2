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
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class Cashflow extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Cashflow Monitoring Report";
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
	private JPanel pnlCPFreport_type;
	private JPanel pnlCPFreport_type_a;
	private JPanel pnlCPFreport_type_b;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblReport;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;

	private JTextField txtProject;
	private JTextField txtCompany;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbReportName;

	
	String company;
	String company_logo;	
	String co_id = "";
	String proj_id = "";	
	String project = "All";
	String phase_no 	= "";
	String phase		= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private JTextField txtPhase;

	public Cashflow() {
		super(title, false, true, false, true);
		initGUI();
	}

	public Cashflow(String title) {
		super(title);
		initGUI();
	}

	public Cashflow(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(513, 307));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(509, 250));

			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(499, 128));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project Option" ));// XXX

				{
					pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
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
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id = (String) data[0];	
									company		= (String) data[1];	
									company_logo = (String) data[3];

									refreshFields();
									
									String name = (String) data[1];						
									txtCompany.setText(name);
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
									lblProject.setEnabled(true);	
									lookupProject.setText("");
									lookupProject.setEnabled(true);
									txtProject.setEnabled(true);
									txtProject.setText("");

									lblDateFrom.setEnabled(true);		
									dteDateFrom.setEnabled(true);
									lblDateTo.setEnabled(true);	
									dateDateTo.setEnabled(true);

									lblReport.setEnabled(true);		
									cmbReportName.setEnabled(true);
									
									btnPreview.setEnabled(true);
									btnCancel.setEnabled(true);
									
									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
									btnPreview.setEnabled(true);
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
									String name = (String) data[1];	
									project = (String) data[1];	
									txtProject.setText(name);
									btnPreview.setEnabled(true);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
									
									lblPhase.setEnabled(true);	
									lookupPhase.setEnabled(true);
									
									lookupPhase.setValue("");
									txtPhase.setText("");
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
					txtPhase = new JTextField();
					pnlNorthEast.add(txtPhase, BorderLayout.CENTER);
					txtPhase.setEditable(false);
					txtPhase.setEnabled(true);
				}
			}

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.CENTER);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Report Option"));

			{
				pnlCPFdate = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFdate, BorderLayout.NORTH);
				pnlCPFdate.setPreferredSize(new java.awt.Dimension(499, 29));		

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCPFdate.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));							

					{
						lblDateFrom = new JLabel("Period From  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(false);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(false);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}		
					{
						lblDateTo = new JLabel("Period To  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(false);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(false);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}					
			}
			{
				pnlCPFreport_type = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFreport_type, BorderLayout.CENTER);
				pnlCPFreport_type.setPreferredSize(new java.awt.Dimension(499, 46));		

				{
					pnlCPFreport_type_a = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_a, BorderLayout.CENTER);
					pnlCPFreport_type_a.setPreferredSize(new java.awt.Dimension(70, 44));

					{
						lblReport = new JLabel("Report Name ", JLabel.TRAILING);
						pnlCPFreport_type_a.add(lblReport,
 BorderLayout.CENTER);
						lblReport.setPreferredSize(new java.awt.Dimension(84, 65));
						lblReport.setEnabled(false);							
					}

				}
				{
					pnlCPFreport_type_b = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_b, BorderLayout.EAST);
					pnlCPFreport_type_b.setPreferredSize(new java.awt.Dimension(404, 68));
					{
						String status[] = {
								"Cashflow Monitoring - Summary",
								"Cashflow Monitoring - Detailed",
								"Check Status Summary",
								"Cash Status Summary",
								};					
						cmbReportName = new JComboBox(status);
						pnlCPFreport_type_b.add(cmbReportName);
						cmbReportName.setSelectedItem(null);
						cmbReportName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbReportName.setBounds(537, 15, 160, 21);	
						cmbReportName.setEnabled(false);
						cmbReportName.setSelectedIndex(0);	
						cmbReportName.setPreferredSize(new java.awt.Dimension(418, 65));
					}

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
		this.setBounds(0, 0, 513, 307);  //174
		
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

		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==0){ previewCashflow();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==1){ previewCashflowDetails();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==2){ previewCheckStatusSummary();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==3){ previewCashStatusSummary();}

		if(e.getActionCommand().equals("Cancel"))
		{			
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			lookupProject.setValue("");
			lookupPhase.setValue("");
			txtProject.setText("");
			txtPhase.setText("");
			proj_id = "";
			project = "All";
			phase_no= "";
			phase	= "All";
			lookupPhase.setEnabled(false);
			lblPhase.setEnabled(false);
		}
	}

	
	//enable, disable
	public void refreshFields(){
		
		lookupProject.setText("");
		txtProject.setText("");
		//dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		//dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		cmbReportName.setSelectedIndex(0);	
	}
	
	
	//preview
	private void previewCashflow(){

		String criteria = "Cashflow Monitoring - Summary";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", project);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase_name", phase);
		mapParameters.put("phase", phase_no);
		mapParameters.put("co_id", co_id);


		FncReport.generateReport("/Reports/rptCashflow_Monitoring.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewCashflowDetails(){

		String criteria = "Cashflow Monitoring - Details";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		
		
		System.out.printf("value of company logo: %s%n", company_logo);
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", project);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		//mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase_name", phase);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("co_id", co_id);

		FncReport.generateReport("/Reports/rptCashflow_MonitoringDetails_v2.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewCheckStatusSummary(){

		String criteria = "Check Status Summary";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", project);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase", phase);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("co_id", co_id);

		FncReport.generateReport("/Reports/rptCheckStatusSummary.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewCashStatusSummary(){

		String criteria = "Cash Status Summary";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", project);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase", phase);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("co_id", co_id);

		FncReport.generateReport("/Reports/rptCashStatusSummary.jasper", reportTitle, company, mapParameters);		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		project		= "";
		company_logo = sql_getCompanyLogo(co_id);		
		txtCompany.setText(company);
		project = "All";

		refreshFields();		
					
		txtCompany.setText(company);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getPhase());
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");

		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);	
		dateDateTo.setEnabled(true);

		lblReport.setEnabled(true);		
		cmbReportName.setEnabled(true);
		
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);
		
		lookupCompany.setValue(co_id);
	}
	
	public String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		FncSystem.out("SQL for company logo", SQL);
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

	
	//SQL
	private String getPhase(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(sub_proj_id) as \"Phase ID\",\r\n" + 
		"trim(phase) as \"Phase\"\r\n" + 
		"\r\n" + 
		"from mf_sub_project\r\n" + 
		"where status_id = 'A' \r\n" +//ADDED STATUS ID BY LESTER DCRF 2718 
		"order by sub_proj_id "  ;

	}
	
}