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
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class House_Construction_Status extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "House Construction Status Report";
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
	private JPanel pnlReport_type;
	private JPanel pnlReport_type_a;
	private JPanel pnlReport_type_b;

	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblPhase;
	private JLabel lblReport;
	
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


	private JComboBox cmbReportName;

	public House_Construction_Status() {
		super(title, false, true, false, true);
		initGUI();
	}

	public House_Construction_Status(String title) {
		super(title);
		initGUI();
	}

	public House_Construction_Status(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
			pnlCenter2.setPreferredSize(new java.awt.Dimension(578, 246));	
								
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlCenter2.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(578, 122));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Project Details" ));// XXX

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
									lookupPhase.setLookupSQL(getPhase());
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
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("Date"));

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
				{
					pnlReport_type = new JPanel(new BorderLayout(0,0));
					pnlCenter2.add(pnlReport_type, BorderLayout.SOUTH);
					pnlReport_type.setPreferredSize(new java.awt.Dimension(578, 57));	
					pnlReport_type.setBorder(components.JTBorderFactory.createTitleBorder("Report Details"));

					{
						pnlReport_type_a = new JPanel(new BorderLayout(5,5));
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
									"House Construction Status - Detailed"};					
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
				pnlMain.add(pnlSouth, BorderLayout.CENTER);
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
			if(cmbReportName.getSelectedIndex()==0)
			{previewHouseConstStatus_Detailed1();
			 previewHouseConstStatus_Detailed2();}

		if(e.getActionCommand().equals("Cancel"))
		{
			refreshFields();				
			proj_id 	= "";
			proj_name 	= "All";
			phase_no 	= "";
			phase		= "All";

			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
			lookupPhase.setLookupSQL(getPhase());

		}
	}


	//enable, disable
	public void refreshFields(){

		lookupProject.setText("");
		lookupPhase.setText("");

		txtProject.setText("");
		txtPhase.setText("");
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
	}

	public void enableFields(){

		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		

		lblPhase.setEnabled(true);
		lookupPhase.setEnabled(true);
		txtPhase.setEnabled(true);

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
		lookupPhase.setLookupSQL(getPhase());
		KEYBOARD_MANAGER.focusNextComponent();
		
		lookupCompany.setValue(co_id);
	}


	//preview
	private void previewHouseConstStatus_Detailed1(){

		String reportTitle = "House Construction Status (Detailed - Page 1) Report";	
		//String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("phase", phase);
		mapParameters.put("co_id", co_id);

		FncReport.generateReport("/Reports/rptConstructionStatusHousing_Dtld1.jasper", reportTitle, company, mapParameters);	
		FncReport.generateReport("/Reports/rptConstructionStatusHousing_Dtld2.jasper", reportTitle, company, mapParameters);	

		//FncReport.generateReport("/Reports/rptScheduleofHouseConstruction_Dtld.jasper", reportTitle, company, mapParameters);		

	}
	private void previewHouseConstStatus_Detailed2(){

		String reportTitle = "House Construction Status (Detailed - Page 2) Report";	
		//String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("project", proj_name);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date", dateDateTo.getDate());
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("phase", phase);
		mapParameters.put("co_id", co_id);

		FncReport.generateReport("/Reports/rptConstructionStatusHousing_Dtld2.jasper", reportTitle, company, mapParameters);	

		//FncReport.generateReport("/Reports/rptScheduleofHouseConstruction_Dtld.jasper", reportTitle, company, mapParameters);		

	}
	
	//lookup SQL	
	public String getPhase(){//used
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