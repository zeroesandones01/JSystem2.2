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

public class Commission_Reports extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Commission Reports";
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
	//private JLabel lblCoordinator;
	private JLabel lblPosition;
	//private JLabel lblStage;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupSalesGrp;	
	private _JLookup lookupAgentName;
	private _JLookup lookupSalesDiv;	
	//private _JLookup lookupCoordinator;	
	private _JLookup lookupPosition;	

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtSalesGrp;
	private JTextField txtAgentName;		
	private JTextField txtSalesDiv;
	private JTextField txtPosition;
	//private JTextField txtCoordinator;
	//private JTextField txtStage;	

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbReportName;
	//private JComboBox cmbStage;
	
	String company;
	String company_logo;	
	String co_id 		= "";
	String proj_id 		= "";	
	String sales_grp_id = "";
	String agent_id 	= "";
	String div_id 		= "";
	String posn_id 		= "";
	String coord_id 	= "";
	
	String proj_name 	= "All";
	String sales_grp_name = "All";
	String agent_name 	= "All";
	String div_name		= "All";
	String posn_name	= "All";
	String coord_name	= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public Commission_Reports() {
		super(title, false, true, false, true);
		initGUI();
	}

	public Commission_Reports(String title) {
		super(title);
		initGUI();
	}

	public Commission_Reports(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(591, 393));
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
								"Commission Schedule Report",
								//"Sales Performance Report",
								"Commission Payable Report"
								};					
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
								if (cmbReportName.getSelectedIndex()==1&&!lookupCompany.getText().equals(""))
								{		
									dteDateFrom.setEnabled(true);
									dateDateTo.setEnabled(true);
									//lblStage.setEnabled(true);	
									//cmbStage.setEnabled(true);
									//txtStage.setEnabled(true);
									lblDateFrom.setText("Release Date From: ");
									lblDateFrom.setText("Release Date To: ");
								}
								else if (cmbReportName.getSelectedIndex()==2&&!lookupCompany.getText().equals(""))
								{					
									dteDateFrom.setEnabled(false);
									dateDateTo.setEnabled(false);
									//lblStage.setEnabled(false);	
									//cmbStage.setEnabled(false);
									//txtStage.setEnabled(false);
								}
								else 
								{		
									dteDateFrom.setEnabled(true);
									dateDateTo.setEnabled(true);
									//lblStage.setEnabled(false);	
									//cmbStage.setEnabled(false);
									//txtStage.setEnabled(false);
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
									
									setUserDivDept();		

									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
									lookupSalesGrp.setLookupSQL(getSalesGroup());
									lookupAgentName.setLookupSQL(getSalesAgent());
									lookupSalesDiv.setLookupSQL(getSalesDiv());
									//lookupCoordinator.setLookupSQL(getSalesCoordinator());
									lookupPosition.setLookupSQL(getSalesPosition());
									
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
						lookupProject.setFilterName(true);
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
						lookupSalesDiv.setFilterName(true);
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
									
									//lookupCoordinator.setValue("");
									//txtCoordinator.setText("");
									coord_name	= "All";
									
									lookupSalesGrp.setLookupSQL(getSalesGroup());
									//lookupCoordinator.setLookupSQL(getSalesCoordinator());
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
						lookupSalesGrp.setFilterName(true);
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
									
									//lookupCoordinator.setValue("");
									//txtCoordinator.setText("");
									coord_name	= "All";
									
									lookupAgentName.setLookupSQL(getSalesAgent());
									//lookupCoordinator.setLookupSQL(getSalesCoordinator());
										
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblPosition = new JLabel("Position", JLabel.TRAILING);
						pnlNorthWest.add(lblPosition);
						lblPosition.setEnabled(false);	
					}
					{
						lookupPosition = new _JLookup(null, "Position");
						pnlNorthWest.add(lookupPosition);
						lookupPosition.setReturnColumn(0);
						lookupPosition.setEnabled(false);
						lookupPosition.setFilterName(true);
						lookupPosition.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									posn_id = (String) data[0];		
									posn_name = (String) data[1];	
									txtPosition.setText(posn_name);								
									
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
						lookupAgentName.setFilterName(true);
						lookupAgentName.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									agent_id = (String) data[0];		
									agent_name = (String) data[1];	
									txtAgentName.setText(agent_name);
									
									//lookupCoordinator.setValue("");
									//txtCoordinator.setText("");
									coord_name	= "All";
									
									//lookupCoordinator.setLookupSQL(getSalesCoordinator());

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					/*
					{
						lblCoordinator = new JLabel("Coordinator", JLabel.TRAILING);
						pnlNorthWest.add(lblCoordinator);
						lblCoordinator.setEnabled(false);	
					}
					{
						lookupCoordinator = new _JLookup(null, "Coordinator");
						pnlNorthWest.add(lookupCoordinator);
						lookupCoordinator.setReturnColumn(0);
						lookupCoordinator.setEnabled(false);
						lookupCoordinator.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									coord_id = (String) data[0];		
									coord_name = (String) data[1];	
									txtCoordinator.setText(coord_name);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}					
					{
						lblStage = new JLabel("Stage", JLabel.TRAILING);
						pnlNorthWest.add(lblStage);
						lblStage.setEnabled(false);	
					}
					{
						String status[] = {"OR","TR"};					
						cmbStage = new JComboBox(status);
						pnlNorthWest.add(cmbStage);
						cmbStage.setSelectedItem(null);
						cmbStage.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbStage.setBounds(537, 15, 160, 21);	
						cmbStage.setEnabled(false);
						cmbStage.setSelectedIndex(0);	
						cmbStage.setPreferredSize(new java.awt.Dimension(418, 65));
						cmbStage.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{	
								if (cmbReportName.getSelectedIndex()==0)
								{									
									txtStage.setText("Official Reservation");
								}
								else
								{
									txtStage.setText("Temporary Reservation");
								}
							}
						});
					}
					*/
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
					txtPosition = new JTextField();
					pnlNorthEast.add(txtPosition, "Center");
					txtPosition.setEditable(false);
					txtPosition.setEnabled(false);
				}
				{
					txtAgentName = new JTextField();
					pnlNorthEast.add(txtAgentName, BorderLayout.CENTER);
					txtAgentName.setEditable(false);
					txtAgentName.setEnabled(false);
				}
				/*
				{
					txtCoordinator = new JTextField();
					pnlNorthEast.add(txtCoordinator, "Center");
					txtCoordinator.setEditable(false);
					txtCoordinator.setEnabled(false);
				}				
				{
					txtStage = new JTextField();
					pnlNorthEast.add(txtStage, "Center");
					txtStage.setText("Official Reservation");
					txtStage.setEditable(false);
					txtStage.setEnabled(false);
				}
				*/
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
						lblDateFrom = new JLabel("Release Date From  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat("2016-01-01"));
					}		
					{
						lblDateTo = new JLabel("Release Date To  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat("2017-12-31"));
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
		this.setBounds(0, 0, 591, 393);  //174
		
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

		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==0){ previewCommScheduleStatus();}
		
		//if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==1){ previewSalesPerformanceRpt();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==1){ previewCommPayableRpt();}

		if(e.getActionCommand().equals("Cancel"))
		{
			refreshFields();	
			//co_id 		= "";
			proj_id 	= "";	
			sales_grp_id= "";
			agent_id 	= "";
			div_id 		= "";
			posn_id 	= "";
			coord_id 	= "";
			
			proj_name 	= "All";
			sales_grp_name = "All";
			agent_name 	= "All";
			div_name	= "All";
			posn_name	= "All";
			coord_name	= "All";
			
			setUserDivDept();	
			
			//lookupCoordinator.setLookupSQL(getSalesCoordinator());
			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
			lookupSalesGrp.setLookupSQL(getSalesGroup());
			lookupAgentName.setLookupSQL(getSalesAgent());
			lookupSalesDiv.setLookupSQL(getSalesDiv());
			//lookupCoordinator.setLookupSQL(getSalesCoordinator());
			lookupPosition.setLookupSQL(getSalesPosition());			
		}
	}

	
	//enable, disable
	private void refreshFields(){
		
		lookupProject.setText("");
		lookupSalesDiv.setText("");
		lookupSalesGrp.setText("");
		lookupPosition.setText("");
		lookupAgentName.setText("");
		//lookupCoordinator.setText("");
		
		txtProject.setText("");
		txtSalesGrp.setText("");
		txtAgentName.setText("");	
		txtSalesDiv.setText("");
		//txtCoordinator.setText("");
		txtPosition.setText("");
				
		/*cmbStage.setSelectedIndex(0);
		txtStage.setText("Official Reservation");
		lblDateFrom.setText("Date From: ");
		lblDateFrom.setText("Date To: ");*/
	}
	
	private void enableFields(){
		
		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		
		
		lblSalesDiv.setEnabled(true);
		lookupSalesDiv.setEnabled(true);
		txtSalesDiv.setEnabled(true);

		lblSalesGrp.setEnabled(true);
		lookupSalesGrp.setEnabled(true);
		txtSalesGrp.setEnabled(true);
		
		lblPosition.setEnabled(true);
		lookupPosition.setEnabled(true);
		txtPosition.setEnabled(true);
		
		lblAgentName.setEnabled(true);
		lookupAgentName.setEnabled(true);
		txtAgentName.setEnabled(true);
		
		//lblCoordinator.setEnabled(true);
		//lookupCoordinator.setEnabled(true);
		//txtCoordinator.setEnabled(true);		
		
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
		lookupSalesGrp.setLookupSQL(getSalesGroup());
		lookupAgentName.setLookupSQL(getSalesAgent());
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		//lookupCoordinator.setLookupSQL(getSalesCoordinator());
		lookupPosition.setLookupSQL(getSalesPosition());
		
		//lblDateFrom.setText("Date From: ");
		//lblDateFrom.setText("Date To: ");
}
		
	
	//preview
	private void previewCommScheduleStatus(){

		String criteria = "Commission BOI Schedule";		
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
		mapParameters.put("dept_id", sales_grp_id.trim());
		mapParameters.put("co_id", co_id);
		mapParameters.put("agent_id", agent_id.trim());
		mapParameters.put("div_id", div_id);
		mapParameters.put("posn_id", posn_id);
		mapParameters.put("coord_id", coord_id);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", posn_name);
		mapParameters.put("coord_name", coord_name);
		mapParameters.put("pmt_stage_name", "");
		mapParameters.put("pmt_stage_alias", "");
		
		FncReport.generateReport("/Reports/rptCommScheduleStatus.jasper", reportTitle, company, mapParameters);		
	}
	
	/*private void previewSalesPerformanceRpt(){

		String criteria = "Sales Performance Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String pmt_stage_name = "";
		if (cmbStage.getSelectedIndex()==0){pmt_stage_name = "Official Reservation";} else {pmt_stage_name = "Temporary Reservation";}

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
		mapParameters.put("dept_id", sales_grp_id.trim());
		mapParameters.put("co_id", co_id);
		mapParameters.put("agent_id", agent_id.trim());
		mapParameters.put("div_id", div_id);
		mapParameters.put("posn_id", posn_id);
		mapParameters.put("coord_id", coord_id);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", posn_name);
		mapParameters.put("coord_name", coord_name);
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());

		FncReport.generateReport("/Reports/rptSalesPerformanceReport.jasper", reportTitle, company, mapParameters);		
	}
	*/
	
	private void previewCommPayableRpt(){

		String criteria = "Sales Performance Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String pmt_stage_name = "";
		//if (cmbStage.getSelectedIndex()==0){pmt_stage_name = "Official Reservation";} else {pmt_stage_name = "Temporary Reservation";}

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
		mapParameters.put("dept_id", sales_grp_id.trim());
		mapParameters.put("co_id", co_id);
		mapParameters.put("agent_id", agent_id.trim());
		mapParameters.put("div_id", div_id);
		mapParameters.put("posn_id", posn_id);
		mapParameters.put("coord_id", coord_id);
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", posn_name);
		mapParameters.put("coord_name", coord_name);
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		//mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());

		FncReport.generateReport("/Reports/rptCommPayableRpt.jasper", reportTitle, company, mapParameters);		
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
	
	private String getSalesPosition(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.position_code as \"Code\",\r\n" + 
		"a.position_desc as \"Description\",\r\n" + 
		"a.position_abbv as \"Alias\"\r\n" + 
		"\r\n" + 
		"from mf_sales_position a"  ;

	}
	
	/*
	private String getSalesCoordinator(){//used
		return 
		"select a.bdo_id  as \"Code\", " +
		"upper(trim(a.entity_name)) as \"Name\", " +
		"a.entity_id  as \"Entity ID\" from (\r\n" + 
		"\r\n" + 
		"select distinct on (a.bdo_id)\r\n" + 
		"\r\n" + 
		"a.bdo_id ,\r\n" + 
		"c.entity_name ,\r\n" + 
		"b.entity_id  \r\n" + 
		"\r\n" + 
		"from mf_sellingagent_info a\r\n" + 
		"left join em_employee b on  a.bdo_id = b.emp_code\r\n" + 
		"left join rf_entity c on b.entity_id = c.entity_id\r\n" + 
		"\r\n" + 
		"where (case when '"+sales_grp_id+"' = '' then a.agent_id is not null " +
		"else dept_id = '"+sales_grp_id+"' end)\r\n" + 
		"and (case when '"+div_id+"' = '' then a.agent_id is not null else " +
		"a.sales_div_id = '"+div_id+"' end)" +
		"and (case when '"+agent_id+"' = '' then a.agent_id is not null else " +
		"a.agent_id = '"+agent_id+"' end)" +
		"order by a.bdo_id\r\n" + 
		"\r\n" + 
		") a order by a.entity_name"  ;

	}
	*/
	

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
		sales_grp_id = "";
		sales_grp_name = "";
		
		try { div_id = (String) div_dtl[0];} catch (NullPointerException e) { div_id = ""; }
		try { div_name = (String) div_dtl[1];} catch (NullPointerException e) { div_name = "All"; }		
		try { sales_grp_id = (String) div_dtl[2];} catch (NullPointerException e) { sales_grp_id = ""; }
		try { sales_grp_name = (String) div_dtl[3];} catch (NullPointerException e) { sales_grp_name = "All"; }		
		
		if (!div_id.equals(""))
		{
			if(!UserInfo.EmployeeCode.equals("900965"))//**ADDED BY JED 2020-08-10 : REQUESTED BY ROXAN**//
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
		if (!sales_grp_id.equals(""))
		{
			if(!UserInfo.EmployeeCode.equals("900965"))
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
		
		lookupAgentName.setLookupSQL(getSalesAgent());
	}

	
	
}