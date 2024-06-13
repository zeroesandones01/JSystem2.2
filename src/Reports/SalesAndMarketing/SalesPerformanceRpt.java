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

public class SalesPerformanceRpt extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Sales Performance Reports";
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
	private JLabel lblType;
	private JLabel lblStage;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupSalesGrp;	
	private _JLookup lookupAgentName;
	private _JLookup lookupSalesDiv;
	private _JLookup lookupType;	

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtSalesGrp;
	private JTextField txtAgentName;		
	private JTextField txtSalesDiv;
	private JTextField txtType;
	private JTextField txtStage;	

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JComboBox cmbReportName;
	private JComboBox cmbStage;
	
	String company;
	String company_logo;	
	String co_id 		= "";
	String proj_id 		= "";	
	String sales_grp_id = "";
	String agent_id 	= "";
	String div_id 		= "";
	String posn_id 		= "";
	String sales_type_id= "";
	
	String proj_name 	= "All Project";
	String sales_grp_name = "All";
	String agent_name 	= "All";
	String div_name		= "All";
	String posn_name	= "All";
	String sales_type_name	= "All";
	
	String sub_proj_id	= "";
	String sub_proj_name= "All";
	Boolean personal_sale = true;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");
	DateFormat df2   = new SimpleDateFormat("MM-dd-yyyy");

	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private JTextField txtPhase;
	private JCheckBox chkPersonalSales;
	private JPanel pnlAgentName;
	private JPanel pnlAgentName_a;
	private JPanel pnlAgentName_b;	

	public SalesPerformanceRpt() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SalesPerformanceRpt(String title) {
		super(title);
		initGUI();
	}

	public SalesPerformanceRpt(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(608, 407));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(605, 384));

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
								"Sales Performance Report-Summary",
								"Sales Performance Report-Detailed",
								"Sales Report",
								"TR Conversion",
								"List of Sold Units"};					
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
									cmbStage.setSelectedIndex(1);
									txtStage.setText("Temporary Reservation");
									/*dteDateFrom.setDate(FncGlobal.dateFormat("2016-01-01"));
									dteDateFrom.setEnabled(false);*/
								}	
								else 
								{											
									dteDateFrom.setEnabled(true);
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
					pnlNorthWest = new JPanel(new GridLayout(8,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(181, 246));

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

									String name = (String) data[1];						
									txtCompany.setText(name);									
									
									enableFields();
									
									setUserDivDept();		

									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
									lookupSalesGrp.setLookupSQL(getSalesGroup());
									lookupAgentName.setLookupSQL(getSalesAgent());
									lookupSalesDiv.setLookupSQL(getSalesDiv());
									lookupType.setLookupSQL(getSalesType());
									
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
									
									lookupPhase.setLookupSQL(getPhase(lookupProject.getValue()));
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

									sub_proj_id = (String) data[0];		
									sub_proj_name = (String) data[1];	
									txtPhase.setText(sub_proj_name);
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
						lookupSalesDiv.addLookupListener(new
 LookupListener() {
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
								if (cmbReportName.getSelectedIndex()==3&&cmbStage.getSelectedIndex()==0)
								{									
									JOptionPane.showMessageDialog(getContentPane(),"OR Stage is not applicable for TR Conversion Report.","Information",JOptionPane.INFORMATION_MESSAGE);
									cmbStage.setSelectedIndex(1);
									txtStage.setText("Temporary Reservation");
								}
								
								if(cmbStage.getSelectedIndex()==0)
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
				}

				pnlNorthEast = new JPanel(new GridLayout(8, 1, 5, 5));
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
					pnlNorthEast.add(txtPhase, BorderLayout.CENTER);
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
					txtType = new JTextField();
					pnlNorthEast.add(txtType, "Center");
					txtType.setEditable(false);
					txtType.setEnabled(false);
				}
				{					
					pnlAgentName = new JPanel(new BorderLayout(5,5));
					pnlNorthEast.add(pnlAgentName, BorderLayout.CENTER);
					pnlAgentName.setPreferredSize(new java.awt.Dimension(499, 29));	
					//pnlAgentName.setBorder(lineBorder);	
					
					{
						pnlAgentName_a = new JPanel(new BorderLayout(5,5));
						pnlAgentName.add(pnlAgentName_a, BorderLayout.CENTER);
						pnlAgentName_a.setPreferredSize(new java.awt.Dimension(499, 29));	
						{
							txtAgentName = new JTextField();
							pnlAgentName_a.add(txtAgentName, BorderLayout.CENTER);
							txtAgentName.setEditable(false);
							txtAgentName.setEnabled(false);
						}
					}
					{
						pnlAgentName_b = new JPanel(new BorderLayout(5,5));
						pnlAgentName.add(pnlAgentName_b, BorderLayout.EAST);
						pnlAgentName_b.setPreferredSize(new java.awt.Dimension(111, 26));
						{							
								chkPersonalSales = new JCheckBox("Personal Sales?");
								pnlAgentName_b.add(chkPersonalSales);
								chkPersonalSales.setEnabled(true);	
								chkPersonalSales.setSelected(true);
								chkPersonalSales.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
								chkPersonalSales.setPreferredSize(new java.awt.Dimension(131, 26));
								chkPersonalSales.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {
										boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

										if (selected) {								
											personal_sale = true;
										} 
										else
										{									
											personal_sale = false;
										}
									}
								});							
						}
					}
					
				}
				{
					txtStage = new JTextField();
					pnlNorthEast.add(txtStage, "Center");
					txtStage.setText("Official Reservation");
					txtStage.setEditable(false);
					txtStage.setEnabled(false);
				}
			}
			{
				pnlCPFdate = new JPanel(new BorderLayout(5,5));
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
						dteDateFrom.setDate(FncGlobal.dateFormat("2022-01-01"));
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
						dateDateTo.setDate(null);
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
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, 
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 608, 407);  //174
		
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

		if(e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex() == 0 && cmbStage.getSelectedIndex()==0){ 
			previewSalesPerformanceRpt_OR(); 
			System.out.println("Dumaan dito sa Preview sa First If");
		}
		
		if(e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex() == 0 && cmbStage.getSelectedIndex()==1){ 
			previewSalesPerformanceRpt_TR();
			System.out.println("Dumaan dito sa Preview sa 2nd If");
		}	
		
		if(e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex() == 1 && cmbStage.getSelectedIndex()==0){ 
			previewSalesPerfRptDetailed_OR();
		}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==1&&cmbStage.getSelectedIndex()==1){ 
			previewSalesPerfRptDetailed_TR();
		}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==2){ previewSalesReport_MrLiaoFormat();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==3&&cmbStage.getSelectedIndex()==1){ previewTR_Conversion();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==4){ previewListofSoldUnits();}
		
		if(e.getActionCommand().equals("Cancel")){executeCancel();}
	}

	
	//enable, disable
	private void refreshFields(){
		
		lookupPhase.setText("");
		lookupProject.setText("");
		lookupSalesDiv.setText("");
		lookupSalesGrp.setText("");
		lookupType.setText("");
		lookupAgentName.setText("");
		
		txtPhase.setText("");
		txtProject.setText("");
		txtSalesGrp.setText("");
		txtAgentName.setText("");	
		txtSalesDiv.setText("");
		txtType.setText("");

				
		cmbStage.setSelectedIndex(0);
		txtStage.setText("Official Reservation");
		chkPersonalSales.setSelected(true);
	}
	
	private void enableFields(){
		
		lblPhase.setEnabled(true);	
		lookupPhase.setEnabled(true);
		txtPhase.setEnabled(true);	
		
		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		
		
		lblSalesDiv.setEnabled(true);
		lookupSalesDiv.setEnabled(true);
		txtSalesDiv.setEnabled(true);

		lblSalesGrp.setEnabled(true);
		lookupSalesGrp.setEnabled(true);
		txtSalesGrp.setEnabled(true);
		
		lblType.setEnabled(true);
		lookupType.setEnabled(true);
		txtType.setEnabled(true);
		
		lblAgentName.setEnabled(true);
		lookupAgentName.setEnabled(true);
		txtAgentName.setEnabled(true);	
		
		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);	
		dateDateTo.setEnabled(true);

		lblReport.setEnabled(true);		
		cmbReportName.setEnabled(true);
		
		lblStage.setEnabled(true);	
		cmbStage.setEnabled(true);
		txtStage.setEnabled(true);
		
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		chkPersonalSales.setSelected(true);
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
		
		lookupCompany.setValue(co_id);
		
		setUserDivDept();		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupSalesGrp.setLookupSQL(getSalesGroup());
		lookupAgentName.setLookupSQL(getSalesAgent());
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		lookupType.setLookupSQL(getSalesType());
		lookupPhase.setLookupSQL(getPhase(""));
}
		
	private void executeCancel(){
		
		refreshFields();	
		//co_id 		= "";
		proj_id 	= "";	
		sales_grp_id= "";
		agent_id 	= "";
		div_id 		= "";
		posn_id 	= "";
		sales_type_id 	= "";
		sub_proj_id = "";
		
		proj_name 	= "All";
		sales_grp_name = "All";
		agent_name 	= "All";
		div_name	= "All";
		posn_name	= "All";
		sales_type_name = "All";		
		sub_proj_name = "All";
		personal_sale = true;
		
		setUserDivDept();	
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupSalesGrp.setLookupSQL(getSalesGroup());
		lookupAgentName.setLookupSQL(getSalesAgent());
		lookupSalesDiv.setLookupSQL(getSalesDiv());
		lookupType.setLookupSQL(getSalesType());
		lookupPhase.setLookupSQL(getPhase(""));
	}
	
	
	//preview
	private void previewSalesPerformanceRpt_OR(){

		String criteria = "Sales Performance Report - OR";			
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String pmt_stage_name = "";
		if (cmbStage.getSelectedIndex()==0){pmt_stage_name = "Official Reservation";} else {pmt_stage_name = "Temporary Reservation";}
		
		if (agent_name.equals("All")) {} else {agent_name = agent_name+" ["+sql_getSalesType(agent_id)+"]" ;}
						
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
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", posn_name);
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("year", df2.format(dateDateTo.getDate()).substring(6));
		mapParameters.put("month", df.format(dateDateTo.getDate()).substring(8));
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("sales_type_name", sales_type_name);
		mapParameters.put("phase", sub_proj_id);
		mapParameters.put("phase_name", sub_proj_name);
		mapParameters.put("personal_sale", personal_sale);
		
		System.out.printf("SQL #1 getCV_no : %s", df.format(dateDateTo.getDate()).substring(8));

		FncReport.generateReport("/Reports/rptSalesPerformanceReport_BDT_format.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewSalesPerformanceRpt_TR(){

		String criteria = "Sales Performance Report - TR";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String pmt_stage_name = "";
		if (cmbStage.getSelectedIndex()==0){pmt_stage_name = "Temporary Reservation";} else {pmt_stage_name = "Temporary Reservation";}
		
		if (agent_name.equals("All")) {} else {agent_name = agent_name+" ["+sql_getSalesType(agent_id)+"]" ;}
						
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
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", posn_name);
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("year", df2.format(dateDateTo.getDate()).substring(6));
		mapParameters.put("month", df.format(dateDateTo.getDate()).substring(8));
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("sales_type_name", sales_type_name);
		mapParameters.put("phase", sub_proj_id);
		mapParameters.put("phase_name", sub_proj_name);
		mapParameters.put("personal_sale", personal_sale);
		
		System.out.printf("SQL #1 getCV_no : %s", df.format(dateDateTo.getDate()).substring(8));
		System.out.printf("Display value of month: %s%n", df.format(dateDateTo.getDate()).substring(8));
		System.out.printf("Display value of date to: %s%n", dateDateTo.getDate());
		System.out.printf("Display value of year: %s%n", df2.format(dateDateTo.getDate()).substring(6));
		System.out.printf("Display value of proj. id: %s%n", proj_id);
		System.out.printf("Display value dept id: %s%n", sales_grp_id.trim());

		FncReport.generateReport("/Reports/rptSalesPerformanceReport_TR_BDT_format.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewSalesPerfRptDetailed_OR(){

		String criteria = "Sales Performance Report (Detailed) - OR";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String pmt_stage_name = "";
		if (cmbStage.getSelectedIndex()==0){pmt_stage_name = "Official Reservation";} else {pmt_stage_name = "Temporary Reservation";}

		if (agent_name.equals("All")) {} else {agent_name = agent_name+" ["+sql_getSalesType(agent_id)+"]" ;}
		
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
		mapParameters.put("posn_id", "");
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", "");
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("phase", sub_proj_id);
		mapParameters.put("phase_name", sub_proj_name);
		mapParameters.put("personal_sale", personal_sale);

		FncReport.generateReport("/Reports/rptSalesReport_BDT_format.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewSalesPerfRptDetailed_TR(){

		String criteria = "Sales Performance Report (Detailed) - TR";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		String pmtStageName = "";
		
		String pmt_stage_name = "";
		if (cmbStage.getSelectedIndex()==0){pmt_stage_name = "Official Reservation";} else {pmt_stage_name = "Temporary Reservation";}
		
		if (agent_name.equals("All")) {} else {agent_name = agent_name+" ["+sql_getSalesType(agent_id)+"]" ;}
		
		if ( txtType.getText().equals("")) {pmtStageName = "All";} else {}

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
		mapParameters.put("posn_id", "");
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", "");
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("sales_type_name", pmtStageName);
		mapParameters.put("phase", sub_proj_id);
		mapParameters.put("phase_name", sub_proj_name);
		mapParameters.put("personal_sale", personal_sale);

		FncReport.generateReport("/Reports/rptSalesReport_BDT_format_TR.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewSalesReport_MrLiaoFormat(){

		String criteria = "Sales Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String pmt_stage_name = "", status = "";
		if (cmbStage.getSelectedIndex()==0)
		{pmt_stage_name = "Official Reservation"; status = "01";} 
		else {pmt_stage_name = "Temporary Reservation"; status = "17";}

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
		mapParameters.put("posn_id", "");
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", "");
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("sales_type_name", sales_type_name);
		mapParameters.put("status", status);
		mapParameters.put("phase", sub_proj_id);
		mapParameters.put("phase_name", sub_proj_name);
		mapParameters.put("personal_sale", personal_sale);
		
		FncReport.generateReport("/Reports/rptSalesReport_MrLiaoFormat.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewTR_Conversion(){

		String criteria = "TR Conversion";		
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
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", posn_name);
		mapParameters.put("pmt_stage_name", "Temporary Reservation");
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("year", df2.format(dateDateTo.getDate()).substring(6));
		mapParameters.put("month", df.format(dateDateTo.getDate()).substring(8));
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("sales_type_name", sales_type_name);
		mapParameters.put("phase", sub_proj_id);
		mapParameters.put("phase_name", sub_proj_name);

		FncReport.generateReport("/Reports/rptTR_Conversion.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewListofSoldUnits(){

		String criteria = "List of Sold Units";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		String pmt_stage_name = "", status = "";
		if (cmbStage.getSelectedIndex()==0)
		{pmt_stage_name = "Official Reservation"; status = "01";} 
		else {pmt_stage_name = "Temporary Reservation"; status = "17";}

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
		mapParameters.put("posn_id", "");
		mapParameters.put("sales_grp_name", sales_grp_name);
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("div_name", div_name);
		mapParameters.put("posn_name", "");
		mapParameters.put("pmt_stage_name", pmt_stage_name);
		mapParameters.put("pmt_stage_alias", cmbStage.getSelectedItem());
		mapParameters.put("sales_type_id", lookupType.getText());
		mapParameters.put("status", status);
		mapParameters.put("phase", sub_proj_id);
		mapParameters.put("phase_name", sub_proj_name);

		FncReport.generateReport("/Reports/rptSalesReport_ListofSoldUnits.jasper", reportTitle, company, mapParameters);		
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
		
	private String getPhase(String strProjID){
		return "select trim(a.sub_proj_id), trim(a.phase), trim(b.proj_alias), trim(c.company_alias) \n" + 
		"from mf_sub_project a \n" + 
		"left join mf_project b on a.proj_id = b.proj_id \n" + 
		"left join mf_company c on b.co_id = c.co_id \n" + 
		"where a.proj_id not in ('016') and coalesce(a.phase, '') != ''\n" + 
		"and (a.proj_id = '"+strProjID+"' or '"+strProjID+"' = '') \n" +
		"order by a.proj_id, a.sub_proj_id"  ;

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
		
		lookupAgentName.setLookupSQL(getSalesAgent());
	}
}