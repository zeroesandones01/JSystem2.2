package Reports.SalesAndMarketing;

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
import javax.swing.JCheckBox;
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

public class ListOfSellingUnits extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "List of Selling Units";
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

	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblReport;
	private JLabel lblSalesGrp;
	private JLabel lblSalesDiv;	
	
	private _JLookup lookupCompany;
	private _JLookup lookupSalesGrp;	
	private _JLookup lookupSalesDiv;
	private _JLookup lookupType;	

	private JTextField txtCompany;
	private JTextField txtSalesGrp;	
	private JTextField txtSalesDiv;
	private JTextField txtType;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;

	private JComboBox cmbReportName;
	
	String company;
	String company_logo;	
	String co_id 		= "";
	String sales_grp_id = "";
	String div_id 		= "";
	String sales_type_id= "";	
	
	String sales_grp_name = "All";
	String div_name		= "All";
	String sales_type_name	= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");
	DateFormat df2   = new SimpleDateFormat("MM-dd-yyyy");

	private JLabel lblType;

	private JLabel lblX;

	private JCheckBox chkIncludeInactive;	

	public ListOfSellingUnits() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ListOfSellingUnits(String title) {
		super(title);
		initGUI();
	}

	public ListOfSellingUnits(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(591, 387));
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
								"List of Selling Unit"};					
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
								if (cmbReportName.getSelectedIndex()==3)
								{		
								}		
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
					pnlNorthWest = new JPanel(new GridLayout(4,1, 5, 5));
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

									lookupSalesGrp.setLookupSQL(getSalesGroup());
									lookupSalesDiv.setLookupSQL(getSalesDiv());
									lookupType.setLookupSQL(getSalesType());
									
									KEYBOARD_MANAGER.focusNextComponent();
								
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
									txtSalesDiv.setText(div_name);

									lookupSalesGrp.setValue("");
									txtSalesGrp.setText("");
									sales_grp_name = "All";
									
									lookupSalesGrp.setLookupSQL(getSalesGroup());
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblSalesGrp = new JLabel("Sales Group", JLabel.TRAILING);
						pnlNorthWest.add(lblSalesGrp);
						lblSalesGrp.setEnabled(false);	
					}
					{
						lookupSalesGrp = new _JLookup(null, "Sales Group");
						pnlNorthWest.add(lookupSalesGrp);
						lookupSalesGrp.setReturnColumn(0);
						lookupSalesGrp.setEnabled(false);
						lookupSalesGrp.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){								
									
									sales_grp_id = (String) data[0];		
									sales_grp_name = (String) data[1];	
									txtSalesGrp.setText(sales_grp_name);
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblType = new JLabel("Type", JLabel.TRAILING);
						pnlNorthWest.add(lblType);
						lblType.setEnabled(false);	
					}
					{
						lookupType = new _JLookup(null, "Type");
						pnlNorthWest.add(lookupType);
						lookupType.setReturnColumn(0);
						lookupType.setEnabled(false);
						lookupType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									sales_type_id = (String) data[0];		
									sales_type_name = (String) data[1];	
									txtType.setText(sales_type_name);								
									
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
					txtSalesDiv = new JTextField();
					pnlNorthEast.add(txtSalesDiv, "Center");
					txtSalesDiv.setEditable(false);
					txtSalesDiv.setEnabled(false);
				}
				{
					txtSalesGrp = new JTextField();
					pnlNorthEast.add(txtSalesGrp, "Center");
					txtSalesGrp.setEditable(false);
					txtSalesGrp.setEnabled(false);
				}
				{
					txtType = new JTextField();
					pnlNorthEast.add(txtType, "Center");
					txtType.setEditable(false);
					txtType.setEnabled(false);
				}
			}
			{
				pnlCPFdate = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFdate, BorderLayout.CENTER);
				pnlCPFdate.setPreferredSize(new java.awt.Dimension(499, 29));		

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 1, 3, 3));
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
						dteDateFrom.setDate(FncGlobal.dateFormat("2016-01-01"));
						dteDateFrom.setDateFormatString("yyyy-MM-dd");
					}
					{
						chkIncludeInactive = new JCheckBox("Include Inactive");
						pnlCenter_b.add(chkIncludeInactive);
						chkIncludeInactive.setEnabled(true);	
						chkIncludeInactive.setPreferredSize(new java.awt.Dimension(383, 26));
						chkIncludeInactive.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent arg0) {
							}
						});
					}

					{
						lblX = new JLabel("", JLabel.TRAILING);
						pnlCenter_b.add(lblX, BorderLayout.CENTER);
						lblX.setEnabled(true);	
						lblX.setVisible(false);	
					}/*
					{
						lblX = new JLabel("", JLabel.TRAILING);
						pnlCenter_b.add(lblX, BorderLayout.CENTER);
						lblX.setEnabled(true);	
						lblX.setVisible(false);	
					}*/

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
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, dteDateFrom, btnPreview));
		this.setBounds(0, 0, 591, 320);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==0) {
			previewSalesPerformanceRpt_OR();
		}
			
		if (e.getActionCommand().equals("Cancel")) {
			executeCancel();
		}
	}

	
	//enable, disable
	private void refreshFields(){
		
		lookupSalesDiv.setText("");
		lookupSalesGrp.setText("");
		lookupType.setText("");
		txtSalesGrp.setText("");
		txtSalesDiv.setText("");
		txtType.setText("");
	}
	
	private void enableFields(){
		
		lblSalesDiv.setEnabled(true);
		lookupSalesDiv.setEnabled(true);
		txtSalesDiv.setEnabled(true);

		lblSalesGrp.setEnabled(true);
		lookupSalesGrp.setEnabled(true);
		txtSalesGrp.setEnabled(true);
		
		lblType.setEnabled(true);
		lookupType.setEnabled(true);
		txtType.setEnabled(true);
		
		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);

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
		lookupSalesGrp.setLookupSQL(getSalesGroup());
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		lookupType.setLookupSQL(getSalesType());
}
		
	private void executeCancel(){
		
		refreshFields();
		sales_grp_id= "";
		div_id 		= "";
		sales_type_id 	= "";
		sales_grp_name = "All";
		div_name	= "All";
		sales_type_name = "All";
		
		setUserDivDept();	
		lookupSalesGrp.setLookupSQL(getSalesGroup());
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		lookupType.setLookupSQL(getSalesType());	
		
		
	}
	
	
	//preview
	private void previewSalesPerformanceRpt_OR(){

		String criteria = "Sales Performance Report - OR";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
					
		boolean incl_inactive = false;
		if (chkIncludeInactive.isSelected()==true){incl_inactive = true;} else {incl_inactive = false;}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", "");	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_to", dteDateFrom.getDate());
		mapParameters.put("dept_id", sales_grp_id.trim());
		mapParameters.put("co_id", co_id);
		mapParameters.put("div_id", div_id);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("year", df2.format(dteDateFrom.getDate()).substring(6));
		mapParameters.put("month", df.format(dteDateFrom.getDate()).substring(8));
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("sales_type_name", sales_type_name);
		mapParameters.put("incl_inactive", incl_inactive);
		
		System.out.printf(" %s", df.format(dteDateFrom.getDate()).substring(8));

		FncReport.generateReport("/Reports/rptListofSellingUnit.jasper", reportTitle, company, mapParameters);		
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
	
	private String getSalesGroup(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.dept_code as \"Dept. ID\",\r\n" + 
		"a.dept_alias  as \"Dept. Alias\",\r\n" + 
		"b.division_name as \"Division\" \r\n" + 
		"\r\n" + 
		"\r\n" + 
		"from mf_department a\r\n" + 
		"left join mf_division b on a.division_code = b.division_code\r\n" + 
		"\r\n" + 
		"where " +
		"(case when '"+div_id+"' = '' then a.division_code in ('06','07','08','09','29') else " +
		"a.division_code = '"+div_id+"' end)"  ;

	}
	
	private String getSalesAgent(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.agent_id as \"Agent ID\",\r\n" + 
		"upper(trim(b.entity_name)) as \"Agent Name\",\r\n" + 
		"c.position_desc as \"Position\" \r\n" + 
		"\r\n" + 
		"from mf_sellingagent_info a\r\n" + 
		"left join rf_entity b on a.agent_id = b.entity_id\r\n" + 
		"left join mf_sales_position c on a.salespos_id = c.position_code\r\n" + 
		"\r\n" + 
		"where (case when '"+sales_grp_id+"' = '' then a.agent_id is not null " +
		"else dept_id = '"+sales_grp_id+"' end)\r\n" + 
		"and (case when '"+div_id+"' = '' then a.agent_id is not null else " +
		"a.sales_div_id = '"+div_id+"' end)" +
		"order by b.entity_name"  ;

	}
	
	private String getSalesType(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"distinct on (a.salestype_code) " +
		"a.salestype_code as \"Code\",\r\n" + 
		"(case when salestype_code = '001' then 'In-House' else 'External' end) as \"Description\"\r\n" + 
		"\r\n" + 
		"from mf_sales_position a"  ;

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

	public static String sql_getSalesType(String agentID) {

		String salesType = "";

		String SQL = 
				"select b.position_abbv "
				+ "from mf_sellingagent_info a "
				+ "left join mf_sales_position b on a.salespos_id  = b.position_code "
				+ "where a.agent_id = '"+agentID+"'  "
				+ "limit 1 \n" ;

		System.out.printf("SQL #1 getCV_no : %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);


		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {salesType = "";}
			else {salesType = (String) db.getResult()[0][0]; }
		}else{
			salesType = "";
		}

		return salesType;
	}
	
	
	
	
	
	//Restrict User acces
	private void setUserDivDept(){
		
		Object[] div_dtl = sql_getDivId();
		
		div_id = "";
		div_name = "";
		sales_grp_id = "";
		sales_grp_name = "";
		sales_type_id = "";
		
		try { div_id = (String) div_dtl[0];} catch (NullPointerException e) { div_id = ""; }
		try { div_name = (String) div_dtl[1];} catch (NullPointerException e) { div_name = "All"; }		
		try { sales_grp_id = (String) div_dtl[2];} catch (NullPointerException e) { sales_grp_id = ""; }
		try { sales_grp_name = (String) div_dtl[3];} catch (NullPointerException e) { sales_grp_name = "All"; }	
		
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
		if (!sales_grp_id.equals(""))
		{
			lookupSalesGrp.setText(sales_grp_id);
			txtSalesGrp.setText(sales_grp_name);	
			lookupSalesGrp.setEnabled(false);
			txtSalesGrp.setEnabled(false);			
		}
		else
		{		
			lookupSalesGrp.setText("");
			txtSalesGrp.setText("");	
			lookupSalesGrp.setEnabled(true);
			txtSalesGrp.setEnabled(true);	
		}		
	}
}