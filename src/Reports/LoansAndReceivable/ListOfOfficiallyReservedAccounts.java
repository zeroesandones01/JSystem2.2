package Reports.LoansAndReceivable;

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

public class ListOfOfficiallyReservedAccounts extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "List of Officially Reserved Accounts";
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
	private JLabel lblSalesDiv;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupSalesDiv;

	private JTextField txtProject;
	private JTextField txtCompany;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbReportName;
	
	String company;
	String company_logo;	
	String co_id 		= "";
	String proj_id 		= "";	
	String div_id 		= "";
	
	String proj_name 	= "All";
	String div_name		= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");
	DateFormat df2   = new SimpleDateFormat("MM-dd-yyyy");

	private JTextField txtSalesDiv;	

	public ListOfOfficiallyReservedAccounts() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ListOfOfficiallyReservedAccounts(String title) {
		super(title);
		initGUI();
	}

	public ListOfOfficiallyReservedAccounts(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(400, 200));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(588, 319));

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.NORTH);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(578, 88));
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Report Option"));
			
			{
				pnlCPFreport_type = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFreport_type, BorderLayout.NORTH);
				pnlCPFreport_type.setPreferredSize(new java.awt.Dimension(578, 25));		

				{
					pnlCPFreport_type_a = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_a, BorderLayout.CENTER);
					pnlCPFreport_type_a.setPreferredSize(new java.awt.Dimension(107, 25));

					{
						lblReport = new JLabel("Report Name ", JLabel.TRAILING);
						pnlCPFreport_type_a.add(lblReport, BorderLayout.CENTER);
						lblReport.setPreferredSize(new java.awt.Dimension(115, 25));
						lblReport.setEnabled(true);							
					}
				}
				{
					pnlCPFreport_type_b = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_b, BorderLayout.EAST);
					pnlCPFreport_type_b.setPreferredSize(new java.awt.Dimension(471, 25));
					{
						String status[] = {
								"List of Officially Reserved Accounts"};					
						cmbReportName = new JComboBox(status);
						pnlCPFreport_type_b.add(cmbReportName);
						cmbReportName.setSelectedItem(null);
						cmbReportName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbReportName.setBounds(537, 15, 160, 21);	
						cmbReportName.setEnabled(true);
						cmbReportName.setSelectedIndex(0);	
						cmbReportName.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbReportName.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{	
								/*if (cmbReportName.getSelectedIndex()==3)
								{		
									cmbStage.setSelectedIndex(1);
									txtStage.setText("Temporary Reservation");
								}		*/
							}
						});
					}

				}
			}

			
			
			
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setPreferredSize(new java.awt.Dimension(578, 271));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project / Sales Group / Sales Agent Option" ));// XXX

				{
					pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(189, 224));

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
									
									setUserDivDept();		

									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
									lookupSalesDiv.setLookupSQL(getSalesDiv());							
									KEYBOARD_MANAGER.focusNextComponent();
									
									if (cmbReportName.getSelectedIndex()==0)
									{
										
									}
									else 
									{
										
									}
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
						lblSalesDiv = new JLabel("Division", JLabel.TRAILING);
						pnlNorthWest.add(lblSalesDiv);
						lblSalesDiv.setEnabled(false);	
					}
					{
						lookupSalesDiv = new _JLookup(null, "Division");
						pnlNorthWest.add(lookupSalesDiv);
						lookupSalesDiv.setReturnColumn(0);
						lookupSalesDiv.setEnabled(false);
						lookupSalesDiv.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									div_id = (String) data[0];		
									div_name = (String) data[2];
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
					txtSalesDiv = new JTextField();
					pnlNorthEast.add(txtSalesDiv, "Center");
					txtSalesDiv.setEditable(false);
					txtSalesDiv.setEnabled(false);
				}
			}
			{
				pnlCPFdate = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFdate, BorderLayout.CENTER);
				pnlCPFdate.setPreferredSize(new java.awt.Dimension(499, 29));		

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCPFdate.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));							

					{
						lblDateFrom = new JLabel("Period From  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);	
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dteDateFrom.setDateFormatString("yyyy-MM-dd");
					}		
					{
						lblDateTo = new JLabel("Period To  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
		this.setBounds(0, 0, 591, 300);  //174
		
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

		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==0){ previewListofSoldUnits();}
						
		if(e.getActionCommand().equals("Cancel")){executeCancel();}
	}

	
	//enable, disable
	private void refreshFields(){
		
		lookupProject.setText("");
		lookupSalesDiv.setText("");
		
		txtProject.setText("");
		txtSalesDiv.setText("");
	}
	
	private void enableFields(){
		
		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		
		
		lblSalesDiv.setEnabled(true);
		lookupSalesDiv.setEnabled(true);
		txtSalesDiv.setEnabled(true);
		
		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);	
		dateDateTo.setEnabled(true);

		lblReport.setEnabled(true);		
		cmbReportName.setEnabled(true);
		
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		
	}
	
	private void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";				
		company_logo = RequestForPayment.sql_getCompanyLogo();	
		refreshFields();					
		txtCompany.setText(company);									
		
		refreshFields();
		enableFields();		
		
		KEYBOARD_MANAGER.focusNextComponent();
		
		/*if (cmbReportName.getSelectedIndex()==0)
		{
			lblStage.setEnabled(false);	
			cmbStage.setEnabled(false);
			txtStage.setEnabled(false);
		}
		else 
		{
			lblStage.setEnabled(true);	
			cmbStage.setEnabled(true);
			txtStage.setEnabled(true);
		}*/
		
		lookupCompany.setValue(co_id);
		
		setUserDivDept();		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupSalesDiv.setLookupSQL(getSalesDiv());
}
		
	private void executeCancel(){
		
		refreshFields();	
		//co_id 		= "";
		proj_id 	= "";	
		div_id 		= "";
		
		proj_name 	= "All";
		div_name	= "All";
		
		setUserDivDept();	
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		
	}
	
	
	//preview	
	private void previewListofSoldUnits(){

		String criteria = "List of Officially Reserved Accounts";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("co_id", co_id);
		mapParameters.put("div_id", div_id);
		mapParameters.put("div_name", div_name);

		FncReport.generateReport("/Reports/rptListOf_OR_Accts.jasper", reportTitle, company, mapParameters);		
	}
	
	
	//lookup SQL	
	private String getSalesDiv(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(division_code) as \"Div. ID\",\r\n" + 
		"trim(division_alias) as \"Div. Alias\",\r\n" + 
		"trim(division_name) as \"Div. Name\" \r\n" + 
		"\r\n" + 
		"from mf_division\r\n" + 
		"\r\n" + 
		"where division_code in ('06','07','08','09','29')\r\n" + //'04',
		"\r\n" + 
		"order by division_code"  ;

	}

	//Variable SQL
	private Object [] sql_getDivId() {

		String SQL = 
			"select " +
			"a.div_code, " +
			"trim(b.division_name) as div_name,  " +
			"a.dept_code, " +
			"trim(c.dept_name) as dept_name  " +
			"from em_employee a \n" +
			"left join mf_division b on a.div_code = b.division_code " +
			"left join mf_department c on a.dept_code = c.dept_code " +
			"where a.emp_code = '"+UserInfo.EmployeeCode+"' and b.division_code in ('06','07','08','09','29')   " ;

		System.out.printf("SQL #1 sql_getDivId: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	
	//Restrict User acces
	private void setUserDivDept(){
		
		Object[] div_dtl = sql_getDivId();
		
		div_id = "";
		div_name = "";
		
		try { div_id = (String) div_dtl[0];} catch (NullPointerException e) { div_id = ""; }
		try { div_name = (String) div_dtl[1];} catch (NullPointerException e) { div_name = "All"; }		
		
		if (!div_id.equals(""))
		{
			lookupSalesDiv.setText(div_id);
			txtSalesDiv.setText(div_name);	
			lookupSalesDiv.setEnabled(false);
			txtSalesDiv.setEnabled(false);			
		}
		else
		{		
			lookupSalesDiv.setText("");
			txtSalesDiv.setText("");		
			lookupSalesDiv.setEnabled(true);
			txtSalesDiv.setEnabled(true);	
		}
	}
}