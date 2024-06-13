package Reports.COO;

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
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class BuyerDemographics extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Demographic Reports";
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
	private JLabel lblRegion;
	private JLabel lblMunicipality;
	private JLabel lblSalesDiv;
	private JLabel lblCity;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupRegion;	
	private _JLookup lookupMuncipality;
	private _JLookup lookupSalesDiv;	
	private _JLookup lookupCity;	

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtRegion;
	private JTextField txtMunicipality;		
	private JTextField txtSalesDiv;
	private JTextField txtCity;

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
	String region_id	= "";
	String city_id		= "";
	String munic_id		= "";
	String address_type_id		= "";
	String status_id	= "";
	String province_id	= "";
	String ownership_id	= "";

	String proj_name 	= "All";
	String div_name		= "All";
	String region_name	= "All";
	String city_name	= "All";
	String munic_name	= "All";
	String address_type_name	= "All";
	String status_name	= "All";
	String province_name	= "All";
	String ownership_name	= "All";
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JLabel lblAddressType;
	private _JLookup lookupAddressType;
	private JTextField txtAddressType;
	private JLabel lblStatus;
	private JComboBox cmbStatus;
	private _JLookup lookupProvince;
	private JLabel lblProvince;
	private JTextField txtProvince;
	private JLabel lblOwnership;
	private _JLookup lookupOwnership;
	private JTextField txtOwnership;

	public BuyerDemographics() {
		super(title, false, true, false, true);
		initGUI();
	}

	public BuyerDemographics(String title) {
		super(title);
		initGUI();
	}

	public BuyerDemographics(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(591, 454));
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
								"Location",
								"Age",
								"Gender",
								"Civil Status",
								"Nature of Business"
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
								if (cmbReportName.getSelectedIndex()==0&&!lookupCompany.getText().equals(""))
								{		
									lookupAddressType.setEnabled(true);
									lookupOwnership.setEnabled(true);
								}
								else if (cmbReportName.getSelectedIndex()!=0&&!lookupCompany.getText().equals(""))
								{					
									lookupAddressType.setEnabled(false);
									lookupOwnership.setEnabled(false);
									lookupAddressType.setText("");
									lookupOwnership.setText("");
									txtAddressType.setText("");
									txtOwnership.setText("");
								}
								else 
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
					pnlNorthWest = new JPanel(new GridLayout(10,1, 5, 5));
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

									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
									lookupRegion.setLookupSQL(getRegion());
									lookupProvince.setLookupSQL(getProvince());
									lookupMuncipality.setLookupSQL(getMunicipality());
									lookupCity.setLookupSQL(getCity());
									lookupAddressType.setLookupSQL(getAddressType());			
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
						lookupSalesDiv


 = new _JLookup(null, "Division");
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

									lookupRegion.setValue("");
									txtRegion.setText("");
									
									lookupMuncipality.setValue("");
									txtMunicipality.setText("");
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblRegion = new JLabel("Region", JLabel.TRAILING);
						pnlNorthWest.add(lblRegion);
						lblRegion.setEnabled(false);	
					}
					{
						lookupRegion = new _JLookup(null, "Region");
						pnlNorthWest.add(lookupRegion);
						lookupRegion.setReturnColumn(0);
						lookupRegion.setEnabled(false);
						lookupRegion.setFilterName(true);
						lookupRegion.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){								
									
									region_id = (String) data[0];		
									region_name = (String) data[1];	
									txtRegion.setText(region_name);
									
									lookupProvince.setValue("");
									txtProvince.setText("");
									lookupCity.setValue("");
									txtCity.setText("");
									lookupMuncipality.setValue("");
									txtMunicipality.setText("");
									
									lookupProvince.setLookupSQL(getProvince());
									lookupCity.setLookupSQL(getCity());
									lookupMuncipality.setLookupSQL(getMunicipality());
										
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblProvince = new JLabel("Province", JLabel.TRAILING);
						pnlNorthWest.add(lblProvince);
						lblProvince.setEnabled(false);	
					}
					{
						lookupProvince = new _JLookup(null, "Province");
						pnlNorthWest.add(lookupProvince);
						lookupProvince.setReturnColumn(0);
						lookupProvince.setEnabled(false);
						lookupProvince.setLookupSQL(getRegion());
						lookupProvince.setFilterName(true);
						lookupProvince.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){								
									
									province_id = (String) data[0];		
									province_name = (String) data[1];	
									txtProvince.setText(province_name);
									
									lookupCity.setValue("");
									txtCity.setText("");
									lookupMuncipality.setValue("");
									txtMunicipality.setText("");
									
									lookupCity.setLookupSQL(getCity());
									lookupMuncipality.setLookupSQL(getMunicipality());
										
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblCity = new JLabel("City", JLabel.TRAILING);
						pnlNorthWest.add(lblCity);
						lblCity.setEnabled(false);	
					}
					{
						lookupCity = new _JLookup(null, "Position");
						pnlNorthWest.add(lookupCity);
						lookupCity.setReturnColumn(0);
						lookupCity.setEnabled(false);
						lookupCity.setFilterName(true);
						lookupCity.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									city_id = (String) data[0];		
									city_name = (String) data[1];	
									txtCity.setText(city_name);		
									
									munic_id = "";
									munic_name = "";
									lookupMuncipality.setValue("");
									txtMunicipality.setText("");
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblMunicipality = new JLabel("Municipality", JLabel.TRAILING);
						pnlNorthWest.add(lblMunicipality);
						lblMunicipality.setEnabled(false);	
					}
					{
						lookupMuncipality = new _JLookup(null, "Agent Name");
						pnlNorthWest.add(lookupMuncipality);
						lookupMuncipality.setReturnColumn(0);
						lookupMuncipality.setEnabled(false);
						lookupMuncipality.setFilterName(true);
						lookupMuncipality.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									munic_id = (String) data[0];		
									munic_name = (String) data[1];	
									txtMunicipality.setText(munic_name);
									
									city_id = "";
									city_name = "";
									lookupCity.setValue("");
									txtCity.setText("");
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}						
					{
						lblAddressType = new JLabel("Address Type", JLabel.TRAILING);
						pnlNorthWest.add(lblAddressType);
						lblAddressType.setEnabled(false);	
					}
					{
						lookupAddressType = new _JLookup(null, "Address Type");
						pnlNorthWest.add(lookupAddressType);
						lookupAddressType.setReturnColumn(0);
						lookupAddressType.setLookupSQL(getAddressType());
						lookupAddressType.setEnabled(false);
						lookupAddressType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									address_type_id = (String) data[0];		
									address_type_name = (String) data[1];	
									txtAddressType.setText(address_type_name);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}	
					{
						lblOwnership = new JLabel("Ownership", JLabel.TRAILING);
						pnlNorthWest.add(lblOwnership);
						lblOwnership.setEnabled(false);	
					}
					{
						lookupOwnership = new _JLookup(null, "Ownership");
						pnlNorthWest.add(lookupOwnership);
						lookupOwnership.setReturnColumn(0);
						lookupOwnership.setLookupSQL(getOwnership());
						lookupOwnership.setEnabled(false);
						lookupOwnership.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									ownership_id = (String) data[0];		
									ownership_name = (String) data[1];	
									txtOwnership.setText(ownership_name);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}	
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlNorthWest.add(lblStatus);
						lblStatus.setEnabled(false);	
					}
					{
						String status[] = {"All","Active","Inactive"};					
						cmbStatus = new JComboBox(status);
						pnlNorthWest.add(cmbStatus);
						cmbStatus.setSelectedItem(null);
						cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbStatus.setBounds(537, 15, 160, 21);	
						cmbStatus.setEnabled(false);
						cmbStatus.setSelectedIndex(0);	
						cmbStatus.setPreferredSize(new java.awt.Dimension(418, 65));
					}
					
				}

				pnlNorthEast = new JPanel(new GridLayout(10, 1, 5, 5));
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
					txtRegion = new JTextField();
					pnlNorthEast.add(txtRegion, "Center");
					txtRegion.setEditable(false);
					txtRegion.setEnabled(false);
				}
				{
					txtProvince = new JTextField();
					pnlNorthEast.add(txtProvince, "Center");
					txtProvince.setEditable(false);
					txtProvince.setEnabled(false);
				}
				{
					txtCity = new JTextField();
					pnlNorthEast.add(txtCity, "Center");
					txtCity.setEditable(false);
					txtCity.setEnabled(false);
				}
				{
					txtMunicipality = new JTextField();
					pnlNorthEast.add(txtMunicipality, BorderLayout.CENTER);
					txtMunicipality.setEditable(false);
					txtMunicipality.setEnabled(false);
				}
				
				{
					txtAddressType = new JTextField();
					pnlNorthEast.add(txtAddressType, "Center");
					txtAddressType.setEditable(false);
					txtAddressType.setEnabled(false);
				}					
				{
					txtOwnership = new JTextField();
					pnlNorthEast.add(txtOwnership, "Center");
					txtOwnership.setEditable(false);
					txtOwnership.setEnabled(false);
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
						lblDateFrom = new JLabel("Reserv. Date From  :", JLabel.TRAILING);
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
						lblDateTo = new JLabel("Reserv. Date To  :", JLabel.TRAILING);
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
		this.setBounds(0, 0, 591, 454);  //174
		
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

		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==0){ previewGeographicLocation();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==1){ previewAgeProfile();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==2){ previewGenderProfile();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==3){ previewCivilStatProfile();}
		
		if(e.getActionCommand().equals("Preview")&&cmbReportName.getSelectedIndex()==4){ previewNOB();}

		if(e.getActionCommand().equals("Cancel"))
		{
			refreshFields();	
			//co_id 		= "";
			proj_id 	= "";	
			region_id	= "";
			province_id	= "";
			city_id 	= "";
			div_id 		= "";
			munic_id 	= "";
			address_type_id 	= "";
			status_id 	= "";
			ownership_id 	= "";
			
			proj_name 	= "All";
			region_name = "All";
			province_name 	= "All";
			city_name 	= "All";
			div_name	= "All";
			munic_name	= "All";
			status_name	= "All";
			address_type_name 	= "All";
			ownership_name 	= "All";
			cmbStatus.setSelectedIndex(0);	
			
			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
			lookupRegion.setLookupSQL(getRegion());
			lookupProvince.setLookupSQL(getProvince());
			lookupMuncipality.setLookupSQL(getMunicipality());
			lookupCity.setLookupSQL(getCity());
			lookupAddressType.setLookupSQL(getAddressType());			
			lookupSalesDiv.setLookupSQL(getSalesDiv());	
			lookupOwnership.setLookupSQL(getOwnership());
		}
	}

	
	//enable, disable
	private void refreshFields(){
		
		lookupProject.setText("");
		lookupSalesDiv.setText("");
		lookupRegion.setText("");
		lookupCity.setText("");
		lookupMuncipality.setText("");
		lookupProvince.setText("");
		lookupAddressType.setText("");
		lookupOwnership.setText("");
		
		txtProject.setText("");
		txtRegion.setText("");
		txtMunicipality.setText("");	
		txtSalesDiv.setText("");
		txtCity.setText("");
		txtProvince.setText("");
		txtAddressType.setText("");
		txtOwnership.setText("");
	}
	
	private void enableFields(){
		
		lblProject.setEnabled(true);	
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);		
		
		lblSalesDiv.setEnabled(true);
		lookupSalesDiv.setEnabled(true);
		txtSalesDiv.setEnabled(true);

		lblRegion.setEnabled(true);
		lookupRegion.setEnabled(true);
		txtRegion.setEnabled(true);
		
		lblProvince.setEnabled(true);
		lookupProvince.setEnabled(true);
		txtProvince.setEnabled(true);
		
		lblCity.setEnabled(true);
		lookupCity.setEnabled(true);
		txtCity.setEnabled(true);
		
		lblMunicipality.setEnabled(true);
		lookupMuncipality.setEnabled(true);
		txtMunicipality.setEnabled(true);
		
		lblAddressType.setEnabled(true);
		lookupAddressType.setEnabled(true);
		txtAddressType.setEnabled(true);
		
		lblOwnership.setEnabled(true);
		lookupOwnership.setEnabled(true);
		txtOwnership.setEnabled(true);
		
		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);	
		dateDateTo.setEnabled(true);

		lblReport.setEnabled(true);		
		cmbReportName.setEnabled(true);
		
		lblStatus.setEnabled(true);		
		cmbStatus.setEnabled(true);
		
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
		
		lookupCompany.setValue(co_id);
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupRegion.setLookupSQL(getRegion());
		lookupProvince.setLookupSQL(getProvince());
		lookupMuncipality.setLookupSQL(getMunicipality());
		lookupCity.setLookupSQL(getCity());
		lookupAddressType.setLookupSQL(getAddressType());			
		lookupSalesDiv.setLookupSQL(getSalesDiv());		
		
}
		
	
	//preview
	private void previewGeographicLocation(){

		String criteria = "Demographics - Location";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		if(cmbStatus.getSelectedIndex()==0){status_id = ""; status_name = "All";}
		if(cmbStatus.getSelectedIndex()==1){status_id = "A"; status_name = "ACTIVE";}
		if(cmbStatus.getSelectedIndex()==2){status_id = "I"; status_name = "INACTIVE";}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("co_id", lookupCompany.getText());
		mapParameters.put("proj_id", lookupProject.getText());
		mapParameters.put("region_id", lookupRegion.getText());
		mapParameters.put("city_id", lookupCity.getText());
		mapParameters.put("munic_id", lookupMuncipality.getText());
		mapParameters.put("address_type_id", lookupAddressType.getText());		
		mapParameters.put("region_name", region_name);
		mapParameters.put("city_name", city_name);
		mapParameters.put("munic_name", munic_name);
		mapParameters.put("address_type_name", address_type_name);
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prov_id", lookupProvince.getText());
		mapParameters.put("prov_name",province_name);
		mapParameters.put("div_name",div_name);
		mapParameters.put("div_id",div_id);
		mapParameters.put("ownership_id",ownership_id);
		mapParameters.put("ownership_name",ownership_name);


		FncReport.generateReport("/Reports/rptCOO_demographics_location.jasper", reportTitle, company, mapParameters);		
	}
	
	private void previewAgeProfile(){

		String criteria = "Demographics - Age";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		if(cmbStatus.getSelectedIndex()==0){status_id = ""; status_name = "All";}
		if(cmbStatus.getSelectedIndex()==1){status_id = "A"; status_name = "ACTIVE";}
		if(cmbStatus.getSelectedIndex()==2){status_id = "I"; status_name = "INACTIVE";}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("co_id", lookupCompany.getText());
		mapParameters.put("proj_id", lookupProject.getText());
		mapParameters.put("region_id", lookupRegion.getText());
		mapParameters.put("city_id", lookupCity.getText());
		mapParameters.put("munic_id", lookupMuncipality.getText());
		mapParameters.put("address_type_id", lookupAddressType.getText());		
		mapParameters.put("region_name", region_name);
		mapParameters.put("city_name", city_name);
		mapParameters.put("munic_name", munic_name);
		mapParameters.put("address_type_name", address_type_name);
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prov_id", lookupProvince.getText());
		mapParameters.put("prov_name",province_name);
		mapParameters.put("div_name",div_name);
		mapParameters.put("div_id",div_id);
		mapParameters.put("ownership_id",ownership_id);
		mapParameters.put("ownership_name",ownership_name);


		FncReport.generateReport("/Reports/rptCOO_demographics_age.jasper", reportTitle, company, mapParameters);	
		
	}
	
	private void previewGenderProfile(){

		String criteria = "Demographics - Gender";		
		String
 reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		if(cmbStatus.getSelectedIndex()==0){status_id
 = ""; status_name = "All";}
		if(cmbStatus.getSelectedIndex()==1){status_id = "A"; status_name = "ACTIVE";}
		if(cmbStatus.getSelectedIndex()==2){status_id = "I"; status_name = "INACTIVE";}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("co_id", lookupCompany.getText());
		mapParameters.put("proj_id", lookupProject.getText());
		mapParameters.put("region_id", lookupRegion.getText());
		mapParameters.put("city_id", lookupCity.getText());
		mapParameters.put("munic_id", lookupMuncipality.getText());
		mapParameters.put("address_type_id", lookupAddressType.getText());		
		mapParameters.put("region_name", region_name);
		mapParameters.put("city_name", city_name);
		mapParameters.put("munic_name", munic_name);
		mapParameters.put("address_type_name", address_type_name);
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prov_id", lookupProvince.getText());
		mapParameters.put("prov_name",province_name);
		mapParameters.put("div_name",div_name);
		mapParameters.put("div_id",div_id);
		mapParameters.put("ownership_id",ownership_id);
		mapParameters.put("ownership_name",ownership_name);


		FncReport.generateReport("/Reports/rptCOO_demographics_gender.jasper", reportTitle, company, mapParameters);	
		
	}
	
	private void previewCivilStatProfile(){

		String criteria = "Demographics - Civil Status";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		if(cmbStatus.getSelectedIndex()==0){status_id = ""; status_name = "All";}
		if(cmbStatus.getSelectedIndex()==1){status_id = "A"; status_name = "ACTIVE";}
		if(cmbStatus.getSelectedIndex()==2){status_id = "I"; status_name = "INACTIVE";}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("co_id", lookupCompany.getText());
		mapParameters.put("proj_id", lookupProject.getText());
		mapParameters.put("region_id", lookupRegion.getText());
		mapParameters.put("city_id", lookupCity.getText());
		mapParameters.put("munic_id", lookupMuncipality.getText());
		mapParameters.put("address_type_id", lookupAddressType.getText());		
		mapParameters.put("region_name", region_name);
		mapParameters.put("city_name", city_name);
		mapParameters.put("munic_name", munic_name);
		mapParameters.put("address_type_name", address_type_name);
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prov_id", lookupProvince.getText());
		mapParameters.put("prov_name",province_name);
		mapParameters.put("div_name",div_name);
		mapParameters.put("div_id",div_id);
		mapParameters.put("ownership_id",ownership_id);
		mapParameters.put("ownership_name",ownership_name);


		FncReport.generateReport("/Reports/rptCOO_demographics_civilstat.jasper", reportTitle, company, mapParameters);	
		
	}
	
	private void previewNOB(){

		String criteria = "Demographics - NOB";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		if(cmbStatus.getSelectedIndex()==0){status_id = ""; status_name = "All";}
		if(cmbStatus.getSelectedIndex()==1){status_id = "A"; status_name = "ACTIVE";}
		if(cmbStatus.getSelectedIndex()==2){status_id = "I"; status_name = "INACTIVE";}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("co_id", lookupCompany.getText());
		mapParameters.put("proj_id", lookupProject.getText());
		mapParameters.put("region_id", lookupRegion.getText());
		mapParameters.put("city_id", lookupCity.getText());
		mapParameters.put("munic_id", lookupMuncipality.getText());
		mapParameters.put("address_type_id", lookupAddressType.getText());		
		mapParameters.put("region_name", region_name);
		mapParameters.put("city_name", city_name);
		mapParameters.put("munic_name", munic_name);
		mapParameters.put("address_type_name", address_type_name);
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prov_id", lookupProvince.getText());
		mapParameters.put("prov_name",province_name);
		mapParameters.put("div_name",div_name);
		mapParameters.put("div_id",div_id);
		mapParameters.put("ownership_id",ownership_id);
		mapParameters.put("ownership_name",ownership_name);


		FncReport.generateReport("/Reports/rptCOO_demographics_nob.jasper", reportTitle, company, mapParameters);		
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
		"where division_code in ('06','07','08','09')\r\n" + //'04',
		"\r\n" + 
		"order by division_code"  ;

	}
	
	private
	String getRegion(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"region_id as \"Region ID\" ," +
		"upper(region_name) as \"Region Name\"  " +
		"from mf_region where status_id != 'I' order by region_id " ;
	}
	
	private String getProvince(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"province_id as \"Province ID\" ," +
		"upper(province_name) as \"Province Name\"  " +
		"from mf_province where status_id != 'I' " +
		"and (case when '"+region_id+"' = '' then true else region_id = '"+region_id+"' end) \r\n" + 
		"order by province_name " ;
	}
	
	private String getCity(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.city_id as \"City ID\" , \n" +
		"upper(a.city_name) as \"City Name\" ," +
		"(case when coalesce(b.province_name,'') = '' then 'NCR' else b.province_name end) as \"Province\"  \n" +
		"from mf_city a \n" +
		"left join mf_province b on a.province_id = b.province_id \n" +
		"where a.status_id != 'I' \n" +
		"and (case when '"+region_id+"' = '' then true else a.province_id in  \n" +
				" (select province_id from mf_province where region_id = '"+region_id+"' and status_id != 'I') end) \n" +
		"and (case when '"+province_id+"' = '' then true else a.province_id = '"+province_id+"' end) \n" +
		"order by a.city_name \n" ;
	}
	
	private String getMunicipality(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.municipality_id as \"Municipality ID\" , \n" +
		"upper(a.municipality_name) as \"Municipality Name\", \n" +
		"(case when coalesce(b.province_name,'') = '' then 'NCR' else b.province_name end) as \"Province\"  \n" +
		"from mf_municipality a \n" +
		"left join mf_province b on a.province_id = b.province_id \n" +
		"where a.status_id != 'I' \n" +
		"and (case when '"+region_id+"' = '' then true else a.province_id in \n" +
		" (select province_id from mf_province where region_id = '"+region_id+"' and status_id != 'I') end) \n" +
		"and (case when '"+province_id+"' = '' then true else a.province_id = '"+province_id+"' end) \n" +
		"order by a.municipality_name " ;
	}
	
	private String getAddressType(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"addr_type_id as \"Address Type ID\" ," +
		"upper(addr_desc) as \"Address Type Name\"  " +
		"from mf_address_type where status_id != 'I' order by addr_type_id " ;
	}
	
	private String getOwnership(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"ownership_id as \"Ownership ID\" ," +
		"upper(ownership_desc) as \"Ownership Name\"  " +
		"from mf_home_ownership_type where status_id != 'I' order by ownership_id " ;
	}
		
	
	
}