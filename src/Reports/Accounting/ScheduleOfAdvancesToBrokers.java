package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXPanel;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class ScheduleOfAdvancesToBrokers extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Advances to Brokers";
	static Dimension frameSize = new Dimension(585, 310);
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
	private JLabel lblDateTo;
	private JLabel lblAgentName;
	private JLabel lblSalesDiv;
	private JLabel lblSalesDept;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupAgentName;
	private _JLookup lookupSalesDiv;
	private _JLookup lookupSalesDept;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtAgentName;		
	private JTextField txtSalesDiv;
	private JTextField txtSalesDept;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	String company;
	String company_logo;	
	String co_id 		= "";
	String proj_id 		= "";	
	String agent_id 	= "";
	String div_id 		= "";
	String dept_id 		= "";

	String proj_name 	= "All";
	String agent_name 	= "All";
	String div_name		= "All";
	String grp_name		= "All";
	String for_liqui	= "no";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JCheckBox chkExcludeLiquidated;
	private JCheckBox chkTripping;
	private JCheckBox chkOther; 
	
	public ScheduleOfAdvancesToBrokers() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ScheduleOfAdvancesToBrokers(String title) {
		super(title);
		initGUI();
	}

	public ScheduleOfAdvancesToBrokers(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			JXPanel panCore = new JXPanel(new BorderLayout(5, 5)); 
			getContentPane().add(panCore, BorderLayout.CENTER);
			panCore.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlMain = new JPanel(new BorderLayout(5, 5));
				panCore.add(pnlMain, BorderLayout.CENTER);
				{
					pnlCenter2 = new JPanel(new BorderLayout(5, 5));
					pnlMain.add(pnlCenter2, BorderLayout.NORTH);
					pnlCenter2.setPreferredSize(new java.awt.Dimension(578, 235));	
					{
						pnlNorth = new JPanel(new BorderLayout(5, 5));
						pnlCenter2.add(pnlNorth, BorderLayout.NORTH);
						pnlNorth.setPreferredSize(new java.awt.Dimension(578, 169));
						pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details" ));
						{
							pnlNorthWest = new JPanel(new GridLayout(5,1, 5, 5));
							pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
							pnlNorthWest.setPreferredSize(new java.awt.Dimension(162, 173));
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
											lookupAgentName.setLookupSQL(getSalesAgent());
											lookupSalesDiv.setLookupSQL(getSalesDiv());
											lookupSalesDept.setLookupSQL(getSalesGroup());
											
											KEYBOARD_MANAGER.focusNextComponent();									
											setUserDivDept();
											
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
											txtSalesDiv.setText(div_name);

											lookupAgentName.setValue("");
											txtAgentName.setText("");
											agent_name 	= "All";
											
											lookupSalesDept.setText("");
											txtSalesDept.setText("");
											
											dept_id = "";
											grp_name = "All";
											
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								lblSalesDept = new JLabel("Group", JLabel.TRAILING);
								pnlNorthWest.add(lblSalesDept);
								lblSalesDept.setEnabled(false);	
							}
							{
								lookupSalesDept = new _JLookup(null, "Group");
								pnlNorthWest.add(lookupSalesDept);
								lookupSalesDept.setReturnColumn(0);
								lookupSalesDept.setEnabled(false);
								lookupSalesDept.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											dept_id = (String) data[0];		
											grp_name = (String) data[1];	
											txtSalesDept.setText(grp_name);

											lookupAgentName.setValue("");
											txtAgentName.setText("");
											agent_name 	= "All";
											
											KEYBOARD_MANAGER.focusNextComponent();
											lookupAgentName.setLookupSQL(getSalesAgent());
										}
									}
								});
							}
							{
								lblAgentName = new JLabel("Payee", JLabel.TRAILING);
								pnlNorthWest.add(lblAgentName);
								lblAgentName.setEnabled(false);	
							}
							{
								lookupAgentName = new _JLookup(null, "Agent Name");
								pnlNorthWest.add(lookupAgentName);
								lookupAgentName.setReturnColumn(0);
								lookupAgentName.setFilterName(true);
								lookupAgentName.setEnabled(false);
								lookupAgentName.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											agent_id = (String) data[0];		
											agent_name = (String) data[1];	
											txtAgentName.setText(agent_name);
										
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
						}
						{
							pnlNorthEast = new JPanel(new GridLayout(5, 1, 5, 5));
							pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
							pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
							{
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
								{
									txtSalesDept = new JTextField();
									pnlNorthEast.add(txtSalesDept, "Center");
									txtSalesDept.setEditable(false);
									txtSalesDept.setEnabled(false);
								}
								{
									txtAgentName = new JTextField();
									pnlNorthEast.add(txtAgentName, BorderLayout.CENTER);
									txtAgentName.setEditable(false);
									txtAgentName.setEnabled(false);
								}	
							}	
						}
					}
					{
						pnlCPFdate = new JPanel(new BorderLayout(5, 5));
						pnlCenter2.add(pnlCPFdate, BorderLayout.CENTER);	
						{
							{
								JXPanel panDate = new JXPanel(new BorderLayout(5, 5)); 
								pnlCPFdate.add(panDate, BorderLayout.LINE_START);
								panDate.setBorder(JTBorderFactory.createTitleBorder("As of", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								panDate.setPreferredSize(new Dimension(175, 0));
								{
									{
										dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										panDate.add(dateDateTo, BorderLayout.CENTER);;
										dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										dateDateTo.setEnabled(true);
									}
								}
							}
							{
								JXPanel panCheck = new JXPanel(new GridLayout(1, 3, 5, 5)); 
								pnlCPFdate.add(panCheck, BorderLayout.CENTER);
								panCheck.setBorder(JTBorderFactory.createTitleBorder("Report Options", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										chkExcludeLiquidated = new JCheckBox("Liquidation");
										panCheck.add(chkExcludeLiquidated);	
										chkExcludeLiquidated.setSelected(false);		
										chkExcludeLiquidated.setPreferredSize(new java.awt.Dimension(209, 62));
										chkExcludeLiquidated.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent arg0) {
												if (chkExcludeLiquidated.isSelected()==true) {								
													for_liqui = "yes";
												} else {
													for_liqui = "no";
												} 
											}
										});
									}
									{
										chkTripping = new JCheckBox("Tripping"); 
										panCheck.add(chkTripping); 
										chkTripping.setSelected(false);
										chkTripping.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												chkExcludeLiquidated.setEnabled(!chkTripping.isSelected());
												chkOther.setEnabled(!chkTripping.isSelected());
											}
										});
									}
									{
										chkOther = new JCheckBox("Other"); 
										panCheck.add(chkOther); 
										chkOther.setSelected(false);
										chkOther.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												chkExcludeLiquidated.setEnabled(!chkOther.isSelected());
												chkTripping.setEnabled(!chkOther.isSelected());
											}
										});
									}
								}
							}
						}					
					}	
					{				
						pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
						pnlMain.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setPreferredSize(new Dimension(0, 30));
						{
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
				}	
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, dteDateFrom, dateDateTo, btnPreview));
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

		if (e.getActionCommand().equals("Preview")) {
			previewAdvancestoBrkrActive();
		}

		if (e.getActionCommand().equals("Cancel")) {
			refreshFields();				
			proj_id 	= "";
			agent_id 	= "";
			div_id 		= "";
			dept_id 	= "";
			
			proj_name 	= "All";
			agent_name 	= "All";
			div_name	= "All";
			grp_name	= "All";

			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
			lookupAgentName.setLookupSQL(getSalesAgent());
			lookupSalesDiv.setLookupSQL(getSalesDiv());
			lookupSalesDept.setLookupSQL(getSalesGroup());
			setUserDivDept();
		}
	}


	//enable, disable
	private void refreshFields(){

		lookupProject.setText("");
		lookupSalesDiv.setText("");
		lookupSalesDept.setText("");
		lookupAgentName.setText("");

		txtProject.setText("");
		txtAgentName.setText("");	
		txtSalesDiv.setText("");
		txtSalesDept.setText("");
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		chkExcludeLiquidated.setSelected(false);	
		for_liqui	= "no";
	}

	private void enableFields(){

		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		

		lblSalesDiv.setEnabled(true);
		lookupSalesDiv.setEnabled(true);
		txtSalesDiv.setEnabled(true);
		
		lblSalesDept.setEnabled(true);
		lookupSalesDept.setEnabled(true);
		txtSalesDept.setEnabled(true);

		lblAgentName.setEnabled(true);
		lookupAgentName.setEnabled(true);
		txtAgentName.setEnabled(true);

		chkExcludeLiquidated.setEnabled(true);		
		dateDateTo.setEnabled(true);

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

		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupAgentName.setLookupSQL(getSalesAgent());
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		lookupSalesDept.setLookupSQL(getSalesGroup());
		KEYBOARD_MANAGER.focusNextComponent();
		
		lookupCompany.setValue(co_id);
		setUserDivDept();
		for_liqui	= "no";
		chkExcludeLiquidated.setSelected(false);	
	}

	private void previewAdvancestoBrkrActive(){
		String criteria = "";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		if (chkTripping.isSelected() || chkOther.isSelected()) {
			System.out.println("");
			System.out.println("Division: "+((div_id==null || div_id.equals(null)) ? "" :div_id));
			System.out.println("Department: "+((dept_id==null || dept_id.equals(null)) ? "" :dept_id));
			System.out.println("Agent: "+((lookupAgentName.getValue()==null || lookupAgentName.getValue().equals(null)) ? "" :lookupAgentName.getValue()));
			
			mapParameters.put("01_co_id", ((co_id==null) ? "" :co_id));
			mapParameters.put("02_co_name", company);
			mapParameters.put("03_dateParam", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
			mapParameters.put("04_dateTo", dateDateTo.getDate());
			mapParameters.put("05_userName", UserInfo.FullName);
			mapParameters.put("06_div_id", ((div_id==null || div_id.equals(null)) ? "" :div_id));
			mapParameters.put("07_dept_code", ((dept_id==null || dept_id.equals(null)) ? "" :dept_id));
			mapParameters.put("08_payee", ((lookupAgentName.getValue()==null || lookupAgentName.getValue().equals(null)) ? "" :lookupAgentName.getValue()));
			
			if (chkTripping.isSelected()) {
				FncReport.generateReport("/Reports/rpt_summary_of_advances_to_brokers.jasper", reportTitle, company, mapParameters);
			} else {
				FncReport.generateReport("/Reports/rpt_summary_of_advances_to_brokers_others.jasper", reportTitle, company, mapParameters);
			}
		} else {
			mapParameters.put("company", company);	
			mapParameters.put("project", proj_name);	
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("prepared_by_name", UserInfo.FullName);
			mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("date_from", dateFormat.format(FncGlobal.dateFormat("2016-01-01")));
			mapParameters.put("date_to", dateDateTo.getDate());
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("co_id", co_id);
			mapParameters.put("payee_id", agent_id);
			mapParameters.put("div_id", div_id);
			mapParameters.put("division_alias", div_name);
			mapParameters.put("dept_id", dept_id);
			mapParameters.put("group_alias", grp_name);
			mapParameters.put("payee_name", agent_name);
			mapParameters.put("for_liqui", for_liqui);
			FncReport.generateReport("/Reports/rptAdvancestoBroker.jasper", reportTitle, company, mapParameters);
		}
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
		"where division_code in ('06','07','08','09','29')\r\n" + 
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
		
		String sql = 
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
		"where (case when '"+div_id+"' = '' then a.agent_id is not null else " +
		"a.sales_div_id = '"+div_id+"' end)" +
		"and (case when '"+dept_id+"' = '' then a.agent_id is not null else " +
		"a.dept_id = '"+dept_id+"' end)" +
		"order by b.entity_name"  ;
		
		System.out.printf("getSalesAgent : " + sql);

		return sql;
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
			"where a.emp_code = '"+UserInfo.EmployeeCode+"' and b.division_code in ('04','06','07','08','09','29')   " ;

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
		dept_id = "";
		grp_name = "";
		
		try { div_id = (String) div_dtl[0];} catch (NullPointerException e) { div_id = ""; }
		try { div_name = (String) div_dtl[1];} catch (NullPointerException e) { div_name = "All"; }		
		try { dept_id = (String) div_dtl[2];} catch (NullPointerException e) { dept_id = ""; }
		try { grp_name = (String) div_dtl[3];} catch (NullPointerException e) { grp_name = "All"; }		
		
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
		if (!dept_id.equals(""))
		{
			lookupSalesDept.setText(dept_id);
			txtSalesDept.setText(grp_name);	
			lookupSalesDept.setEnabled(false);
			txtSalesDept.setEnabled(false);			
		}
		else
		{		
			lookupSalesDept.setText("");
			txtSalesDept.setText("");	
			lookupSalesDept.setEnabled(true);
			txtSalesDept.setEnabled(true);	
		}
		
		lookupAgentName.setLookupSQL(getSalesAgent());
	}

}