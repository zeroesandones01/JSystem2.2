package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
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
import Buyers.CreditandCollections._RealTimeDebit;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;

public class Schedule_AP_Others extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Schedule of AP Others";
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
	private JLabel lblSalesGrp;
	private JLabel lblAgentName;
	private JLabel lblSalesDiv;
	private JLabel lblPhase;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupSalesGrp;	
	private _JLookup lookupAgentName;
	private _JLookup lookupSalesDiv;
	private _JLookup lookupPhase;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtSalesGrp;
	private JTextField txtAgentName;		
	private JTextField txtSalesDiv;
	private JTextField txtPhase;

	private JButton btnPreview;
	private JButton btnPDF;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbReportName;
	
	String company		= "";
	String company_logo = "";	
	String co_id 		= "";
	String proj_id 		= "";
	String sales_grp_id = "";
	String agent_id 	= "";
	String ph_code 		= "";
	String ph_no 		= "All";
	String div_id 		= "";
	
	String proj_name 	= "All";
	String sales_grp_name = "All";
	String agent_name 	= "All";
	String div_name		= "All";
	String coord_name	= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public Schedule_AP_Others() {
		super(title, false, true, false, true);
		initGUI();
	}

	public Schedule_AP_Others(String title) {
		super(title);
		initGUI();
	}

	public Schedule_AP_Others(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(591, 397));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(588, 319));

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.NORTH);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(578, 98));
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Report Option"));
				
			
			{
				pnlCPFreport_type = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFreport_type, BorderLayout.NORTH);
				pnlCPFreport_type.setPreferredSize(new java.awt.Dimension(578, 31));		

				{
					pnlCPFreport_type_a = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_a, BorderLayout.CENTER);
					pnlCPFreport_type_a.setPreferredSize(new java.awt.Dimension(70, 44));

					{
						lblReport = new JLabel("Report Name ", JLabel.TRAILING);
						pnlCPFreport_type_a.add(lblReport, BorderLayout.CENTER);
						lblReport.setPreferredSize(new java.awt.Dimension(84, 65));
						lblReport.setEnabled(true);							
					}
				}
				{
					pnlCPFreport_type_b = new JPanel(new BorderLayout(0,0));
					pnlCPFreport_type.add(pnlCPFreport_type_b, BorderLayout.EAST);
					pnlCPFreport_type_b.setPreferredSize(new java.awt.Dimension(480, 69));
					{
						String status[] = {
								"Outstanding Holding Fee",
								"Outstanding Reservation Fee",};					
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
					pnlNorthWest = new JPanel(new GridLayout(6,1, 5, 5));
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
									lookupSalesGrp.setLookupSQL(getSalesGroup());
									lookupAgentName.setLookupSQL(getSalesAgent());
									lookupSalesDiv.setLookupSQL(getSalesDiv());
									
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
									btnPDF.setEnabled(true);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
									
									lblPhase.setEnabled(true);	
									lookupPhase.setEnabled(true);
									txtPhase.setEnabled(true);	

									lookupPhase.setLookupSQL(getSubproject());
								}
							}
						});
					}
					{
						lblPhase = new JLabel("       Phase", JLabel.TRAILING);
						pnlNorthWest.add(lblPhase);
						lblPhase.setBounds(8, 11, 62, 12);
						lblPhase.setEnabled(false);	
						lblPhase.setPreferredSize(new java.awt.Dimension(81, 25));
						lblPhase.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lookupPhase = new _JLookup(null, "Phase",0,2);
						pnlNorthWest.add(lookupPhase);
						lookupPhase.setLookupSQL(SQL_COMPANY());
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(false);	
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									ph_code		= (String) data[0];	
									ph_no		= (String) data[1];	
									txtPhase.setText(ph_no);
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
									
									lookupAgentName.setValue("");
									txtAgentName.setText("");
									agent_name 	= "All";									
									
									lookupSalesGrp.setLookupSQL(getSalesGroup());
									lookupAgentName.setLookupSQL(getSalesAgent());
									
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
									
									lookupAgentName.setValue("");
									txtAgentName.setText("");
									agent_name 	= "All";
																		
									lookupAgentName.setLookupSQL(getSalesAgent());
										
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblAgentName = new JLabel("Agent Name", JLabel.TRAILING);
						pnlNorthWest.add(lblAgentName);
						lblAgentName.setEnabled(false);	
					}
					{
						lookupAgentName = new _JLookup(null, "Agent Name");
						pnlNorthWest.add(lookupAgentName);
						lookupAgentName.setReturnColumn(0);
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

				pnlNorthEast = new JPanel(new GridLayout(6, 1, 5, 5));
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
					pnlNorthEast.add(txtPhase, "Phase");
					txtPhase.setEditable(false);
					txtPhase.setEnabled(false);
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
					txtAgentName = new JTextField();
					pnlNorthEast.add(txtAgentName, BorderLayout.CENTER);
					txtAgentName.setEditable(false);
					txtAgentName.setEnabled(false);
				}
			}
			{
				pnlCPFdate = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPFdate, BorderLayout.CENTER);
				pnlCPFdate.setPreferredSize(new java.awt.Dimension(499, 29));		

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCPFdate.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(456, 58));							

					{
						lblDateFrom = new JLabel("Date (as of) :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}		
					{
						lblDateTo = new JLabel("Period To  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(true);	
						lblDateTo.setVisible(false);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dateDateTo.setVisible(false);	
					}		

				}					
			}	
			
			{				
				pnlSouth = new JPanel(new GridLayout(1, 3, 5, 5));
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
					btnPDF = new JButton("PDF");
					pnlSouth.add(btnPDF);
					btnPDF.setAlignmentX(0.5f);
					btnPDF.setAlignmentY(0.5f);
					btnPDF.setEnabled(false);
					btnPDF.setMaximumSize(new Dimension(100, 30));
					btnPDF.setMnemonic(KeyEvent.VK_P);
					btnPDF.addActionListener(this);
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
		this.setBounds(0, 0, 591, 397);  //174
		
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
		String strDir = "";
		
		if(e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex()==0) {
			previewOutstanding_Holding_Fee();
		}
		
		if (e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex()==1) {
			previewOutstanding_Resfee();
		}
		
		if (e.getActionCommand().equals("PDF")) {
			strDir = _RealTimeDebit.OpenDir("File");
			
			System.out.println("");
			System.out.println("strDir: " + strDir);
			
			if (cmbReportName.getSelectedIndex()==0) {
				exportPDFOutstanding_Holding_Fee(strDir);
			} else if (cmbReportName.getSelectedIndex()==1) {
				exportPDFOutstanding_Resfee(strDir);
			}
		}		

		if(e.getActionCommand().equals("Cancel")) {
			refreshFields();
			proj_id 	= "";
			sales_grp_id= "";
			agent_id 	= "";
			ph_code 	= "";
			ph_no 		= "All";
			div_id 		= "";
			
			proj_name 	= "All";
			sales_grp_name = "All";
			agent_name 	= "All";
			div_name	= "All";
			coord_name	= "All";
			
			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
			lookupSalesGrp.setLookupSQL(getSalesGroup());
			lookupAgentName.setLookupSQL(getSalesAgent());
			lookupSalesDiv.setLookupSQL(getSalesDiv());
			
			lookupPhase.setEnabled(false);
			txtPhase.setEnabled(false);			
		}
	}

	public void refreshFields(){
		
		lookupProject.setText("");
		lookupSalesDiv.setText("");
		lookupSalesGrp.setText("");
		lookupAgentName.setText("");
		lookupPhase.setText("");
		
		txtProject.setText("");
		txtSalesGrp.setText("");
		txtAgentName.setText("");	
		txtSalesDiv.setText("");
		txtPhase.setText("");
	}
	
	public void enableFields(){
		
		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		
		
		lblSalesDiv.setEnabled(true);
		lookupSalesDiv.setEnabled(true);
		txtSalesDiv.setEnabled(true);

		lblSalesGrp.setEnabled(true);
		lookupSalesGrp.setEnabled(true);
		txtSalesGrp.setEnabled(true);
		
		lblAgentName.setEnabled(true);
		lookupAgentName.setEnabled(true);
		txtAgentName.setEnabled(true);	
		
		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);	
		dateDateTo.setEnabled(true);

		lblReport.setEnabled(true);		
		cmbReportName.setEnabled(true);		

		btnPreview.setEnabled(true);
		btnPDF.setEnabled(true);
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
		lookupSalesGrp.setLookupSQL(getSalesGroup());
		lookupAgentName.setLookupSQL(getSalesAgent());
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		
		KEYBOARD_MANAGER.focusNextComponent();
		
		lookupCompany.setValue(co_id);
}
	
	
	//preview
	private void previewOutstanding_Resfee(){

		String criteria = "Outstanding Reservation Fee";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("co_id", co_id);
		mapParameters.put("asof_date",dteDateFrom.getDate());
		mapParameters.put("project", proj_name);		
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("ph_no", ph_no);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("dept_id", sales_grp_id.trim());		
		mapParameters.put("agent_id", agent_id.trim());	
		mapParameters.put("div_id", div_id);	

		FncReport.generateReport("/Reports/rptOutstanding_Reservation_Fee.jasper", reportTitle, company, mapParameters);		
		
	}
	
	private void previewOutstanding_Holding_Fee(){

		String criteria = "Outstanding Holding Fee";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("co_id", co_id);
		mapParameters.put("asof_date",dteDateFrom.getDate());
		mapParameters.put("project", proj_name);		
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("ph_no", ph_no);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("dept_id", sales_grp_id.trim());		
		mapParameters.put("agent_id", agent_id.trim());	
		mapParameters.put("div_id", div_id);
		
		System.out.printf("Display Variables: (%s)(%s)(%s)(%s)(%s)(%s)", proj_id, ph_no, div_id, sales_grp_id.trim(), agent_id.trim(), dteDateFrom.getDate());

		FncReport.generateReport("/Reports/rptOutstanding_Holding_Fee.jasper", reportTitle, company, mapParameters);		
	}
	

	//lookup SQL	
	public String getSalesDiv(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(division_code) as \"Div. ID\",\r\n" + 
		"trim(division_alias) as \"Div. Alias\",\r\n" + 
		"trim(division_name) as \"Div. Name\" \r\n" + 
		"\r\n" + 
		"from mf_division\r\n" + 
		"\r\n" + 
		"where division_code in ('04','06','07','08','09','29')\r\n" + 
		"\r\n" + 
		"order by division_code"  ;

	}
	
	public String getSalesGroup(){//used
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
	
	public String getSalesAgent(){//used
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
	
	public String getSalesPosition(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.position_code as \"Code\",\r\n" + 
		"a.position_desc as \"Description\",\r\n" + 
		"a.position_abbv as \"Alias\"\r\n" + 
		"\r\n" + 
		"from mf_sales_position a"  ;

	}
	
	public String getSalesCoordinator(){//used
		return 
		"select a.coordinator_id  as \"Code\", " +
		"upper(trim(a.entity_name)) as \"Name\", " +
		"a.entity_id  as \"Entity ID\" from (\r\n" + 
		"\r\n" + 
		"select distinct on (a.coordinator_id)\r\n" + 
		"\r\n" + 
		"a.coordinator_id ,\r\n" + 
		"c.entity_name ,\r\n" + 
		"b.entity_id  \r\n" + 
		"\r\n" + 
		"from mf_sellingagent_info a\r\n" + 
		"left join em_employee b on  a.coordinator_id = b.emp_code\r\n" + 
		"left join rf_entity c on b.entity_id = c.entity_id\r\n" + 
		"\r\n" + 
		"where (case when '"+sales_grp_id+"' = '' then a.agent_id is not null " +
		"else dept_id = '"+sales_grp_id+"' end)\r\n" + 
		"and (case when '"+div_id+"' = '' then a.agent_id is not null else " +
		"a.sales_div_id = '"+div_id+"' end)" +
		"and (case when '"+agent_id+"' = '' then a.agent_id is not null else " +
		"a.agent_id = '"+agent_id+"' end)" +
		"order by a.coordinator_id\r\n" + 
		"\r\n" + 
		") a order by a.entity_name"  ;

	}
	
	public String getSubproject(){
		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"a.sub_proj_id,\r\n" + 
			"a.phase,\r\n" + 
			"a.proj_id,\r\n" + 
			"trim(b.proj_name) \r\n" + 
			"\r\n" + 
			"from mf_sub_project a\r\n" + 
			"left join mf_project b  on a.proj_id = b.proj_id \r\n" + 
			"\r\n" + 
			"where b.co_id = '"+co_id+"' and a.proj_id = '"+proj_id+"' \r\n" + 
			"AND a.status_id = 'A' \r\n" + 
			"order by a.sub_proj_id " ;
		return sql;
	}	
	
	private void exportPDFOutstanding_Holding_Fee(String strDir){
		String criteria = "Outstanding Holding Fee";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdf.format(dteDateFrom.getDate());
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("co_id", co_id);
		mapParameters.put("asof_date",dteDateFrom.getDate());
		mapParameters.put("project", proj_name);		
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("ph_no", ph_no);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("dept_id", sales_grp_id.trim());		
		mapParameters.put("agent_id", agent_id.trim());	
		mapParameters.put("div_id", div_id);
		
		System.out.printf("Display Variables: (%s)(%s)(%s)(%s)(%s)(%s)", proj_id, ph_no, div_id, sales_grp_id.trim(), agent_id.trim(), dteDateFrom.getDate());
		FncReport.generatePDF("/Reports/rptOutstanding_Holding_Fee.jasper", reportTitle, mapParameters, strDir + "/" + criteria + " as of " + strDate + ".pdf");
		
		File f = new File("");
	    Desktop dt = Desktop.getDesktop();
	    f = new File(strDir + "/" + criteria + " as of " + strDate + ".pdf");
	    
	    try {
			dt.open(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getContentPane(), "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void exportPDFOutstanding_Resfee(String strDir){
		String criteria = "Outstanding Reservation Fee";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdf.format(dteDateFrom.getDate());
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("co_id", co_id);
		mapParameters.put("asof_date",dteDateFrom.getDate());
		mapParameters.put("project", proj_name);		
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("ph_no", ph_no);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("dept_id", sales_grp_id.trim());		
		mapParameters.put("agent_id", agent_id.trim());	
		mapParameters.put("div_id", div_id);	
		FncReport.generatePDF("/Reports/rptOutstanding_Reservation_Fee.jasper", reportTitle, mapParameters, strDir + "/" + criteria + " as of " + strDate + ".pdf");
		
		File f = new File("");
	    Desktop dt = Desktop.getDesktop();
	    f = new File(strDir + "/" + criteria + " as of " + strDate + ".pdf");
	    
	    try {
			dt.open(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getContentPane(), "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
