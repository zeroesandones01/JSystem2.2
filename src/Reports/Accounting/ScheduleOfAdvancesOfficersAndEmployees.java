package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
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

public class ScheduleOfAdvancesOfficersAndEmployees extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Advances to Officers and Employees";
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
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblEmpName;
	private JLabel lblDivision;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupEmpName;
	private _JLookup lookupDivision;	

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtEmpName;		
	private JTextField txtDivision;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;


	String company;
	String company_logo;	
	String co_id 		= "";
	String proj_id 		= "";	
	String emp_id 	= "";
	String div_id 		= "";

	String proj_name 	= "All";
	String emp_name	    = "All";
	String div_name		= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public ScheduleOfAdvancesOfficersAndEmployees() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ScheduleOfAdvancesOfficersAndEmployees(String title) {
		super(title);
		initGUI();
	}

	public ScheduleOfAdvancesOfficersAndEmployees(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(591, 308));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(588, 319));

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.NORTH);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(578, 236));	
								
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlCenter2.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(578, 169));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Cost Center Option" ));// XXX

				{
					pnlNorthWest = new JPanel(new GridLayout(4,1, 5, 5));
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
									lookupEmpName.setLookupSQL(getSalesAgent());
									lookupDivision.setLookupSQL(getDivision());
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

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblDivision = new JLabel("Division", JLabel.TRAILING);
						pnlNorthWest.add(lblDivision);
						lblDivision.setEnabled(false);	
					}
					{
						lookupDivision = new _JLookup(null, "Division");
						pnlNorthWest.add(lookupDivision);
						lookupDivision.setReturnColumn(0);
						lookupDivision.setEnabled(false);
						lookupDivision.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									div_id = (String) data[0];		
									div_name = (String) data[2];	
									txtDivision.setText(div_name);

									lookupEmpName.setValue("");
									txtEmpName.setText("");
									emp_id 	= "";
									emp_name 	= "All";
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblEmpName = new JLabel("Employee", JLabel.TRAILING);
						pnlNorthWest.add(lblEmpName);
						lblEmpName.setEnabled(false);	
					}
					{
						lookupEmpName = new _JLookup(null, "Employee Name");
						pnlNorthWest.add(lookupEmpName);
						lookupEmpName.setReturnColumn(0);
						lookupEmpName.setFilterName(true);
						lookupEmpName.setEnabled(false);
						lookupEmpName.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									emp_id = (String) data[0];		
									emp_name = (String) data[1];	
									txtEmpName.setText(emp_name);
								
									KEYBOARD_MANAGER.focusNextComponent();
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
					txtDivision = new JTextField();
					pnlNorthEast.add(txtDivision, "Center");
					txtDivision.setEditable(false);
					txtDivision.setEnabled(false);
				}
				{
					txtEmpName = new JTextField();
					pnlNorthEast.add(txtEmpName, BorderLayout.CENTER);
					txtEmpName.setEditable(false);
					txtEmpName.setEnabled(false);
				}

			}
			{
				pnlCPFdate = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFdate, BorderLayout.CENTER);
				pnlCPFdate.setPreferredSize(new java.awt.Dimension(578, 67));		

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCPFdate.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));	
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("Date Option"));

					{
						lblDateTo = new JLabel("As of :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}		
					{
						lblDateFrom = new JLabel("Period From  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);	
						lblDateFrom.setVisible(false);	
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dteDateFrom.setVisible(false);	
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
		this.setBounds(0, 0, 591, 308);  //174
		
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

		if(e.getActionCommand().equals("Preview")){ previewAdvancestoOffEmp();}

		if(e.getActionCommand().equals("Cancel"))
		{
			refreshFields();				
			proj_id 	= "";
			emp_id 	    = "";
			div_id 		= "";

			proj_name 	= "All";
			emp_name    = "All";
			div_name	= "All";

			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(""));
			lookupEmpName.setLookupSQL(getSalesAgent());
			lookupDivision.setLookupSQL(getDivision());

		}
	}


	//enable, disable
	public void refreshFields(){

		lookupProject.setText("");
		lookupDivision.setText("");
		lookupEmpName.setText("");

		txtProject.setText("");
		txtEmpName.setText("");	
		txtDivision.setText("");
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
	}

	public void enableFields(){

		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		

		lblDivision.setEnabled(true);
		lookupDivision.setEnabled(true);
		txtDivision.setEnabled(true);

		lblEmpName.setEnabled(true);
		lookupEmpName.setEnabled(true);
		txtEmpName.setEnabled(true);

		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);	
		dateDateTo.setEnabled(true);

		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);

	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		refreshFields();
		txtCompany.setText(company);									

		refreshFields();
		enableFields();

		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupEmpName.setLookupSQL(getSalesAgent());
		lookupDivision.setLookupSQL(getDivision());
		KEYBOARD_MANAGER.focusNextComponent();
		
		lookupCompany.setValue(co_id);
	}


	//preview
	private void previewAdvancestoOffEmp(){

		String criteria = "";	//Advances to Brokers (Active Accounts)
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("co_id", co_id);
		mapParameters.put("emp_id", emp_id);
		mapParameters.put("emp_name", emp_name);
		mapParameters.put("div_id", div_id);
		mapParameters.put("div_name", div_name);

		FncReport.generateReport("/Reports/rptAdvancestoOfficersEmployees.jasper", reportTitle, company, mapParameters);		

	}



	//lookup SQL	
	public String getDivision(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(division_code) as \"Div. ID\",\r\n" + 
		"trim(division_alias) as \"Div. Alias\",\r\n" + 
		"trim(division_name) as \"Div. Name\" \r\n" + 
		"\r\n" + 
		"from mf_division\r\n" + 
		"\r\n" + 
		"order by division_code"  ;

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
