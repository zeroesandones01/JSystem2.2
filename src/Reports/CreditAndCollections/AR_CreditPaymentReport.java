package Reports.CreditAndCollections;

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

public class AR_CreditPaymentReport extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Credit of Payment Report";
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
	private JPanel pnlNorth_a;
	private JPanel pnlNorth_b;
	private JPanel pnlNorthWest_b;
	private JPanel pnlNorthEast_b;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblPBL;
	private JLabel lblClientName;
	private JLabel lblPhaseTo;
	private JLabel lblPhase;
	private JLabel lblCompany_to;
	private JLabel lblProject_to;
	private JLabel lblPBL_to;
	private JLabel lblClientName_to;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupPBL;	
	private _JLookup lookupClientName;
	private _JLookup lookupPhaseTo;
	private _JLookup lookupPhase;
	private _JLookup lookupCompany_to;	
	private _JLookup lookupProject_to;	
	private _JLookup lookupPBL_to;	
	private _JLookup lookupClientName_to;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtPBL;
	private JTextField txtClientName;		
	private JTextField txtPhaseTo;
	private JTextField txtPhase;
	private JTextField txtCompany_to;
	private JTextField txtProject_to;
	private JTextField txtPBL_to;
	private JTextField txtClientName_to;

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;
	
	private JCheckBox chkFloating;
	private JCheckBox chkHolding;
	private JCheckBox chkAP;
	private JCheckBox chkAll;
	
	String co_id 		= "";
	String company		= "";
	String company_logo = "";		
	String proj_id 		= "";
	String proj_name 	= "All";	
	String ph_code		= "";
	String ph_no		= "All";
	String pbl_id 		= "";
	String pbl_desc  	= "All";	
	String client_id 	= "";
	String client_name 	= "All";
	
	String co_id_to 	= "";
	String company_to	= "";	
	String proj_id_to	= "";
	String proj_name_to	= "All";	
	String ph_code_to	= "";
	String ph_no_to		= "All";
	String pbl_id_to	= "";
	String pbl_desc_to 	= "All";	
	String client_id_to = "";
	String client_name_to= "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	Boolean all_pmt = true;
	Boolean floating_pmt = false;
	Boolean ap_pmt = false;
	Boolean holding_pmt = false;	

	public AR_CreditPaymentReport() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AR_CreditPaymentReport(String title) {
		super(title);
		initGUI();
	}

	public AR_CreditPaymentReport(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(591, 547));
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
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Report Option (Original Payments)"));
				
			
			{
				pnlCPFreport_type = new JPanel(new GridLayout(1, 2, 3, 3));
				pnlCenter2.add(pnlCPFreport_type, BorderLayout.NORTH);
				pnlCPFreport_type.setPreferredSize(new java.awt.Dimension(578, 31));		
				
				{
					chkAll = new JCheckBox("All Payments");
					pnlCPFreport_type.add(chkAll);
					chkAll.setEnabled(true);	
					chkAll.setSelected(true);	
					chkAll.setHorizontalAlignment(JTextField.CENTER);	
					chkAll.setPreferredSize(new java.awt.Dimension(383, 26));
					chkAll.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkAll.isSelected()==true) 
							{
								chkFloating.setEnabled(false);	
								chkHolding.setEnabled(false);	
								chkAP.setEnabled(false);
								chkFloating.setSelected(false);	
								chkHolding.setSelected(false);	
								chkAP.setSelected(false);
								
								all_pmt = true;
								floating_pmt = false;
								ap_pmt = false;
								holding_pmt = false;
							} 
							else 
							{
								chkFloating.setEnabled(true);	
								chkHolding.setEnabled(true);	
								chkAP.setEnabled(true);	
								chkFloating.setSelected(false);	
								chkHolding.setSelected(false);	
								chkAP.setSelected(false);
								
								all_pmt = false;
								floating_pmt = false;
								ap_pmt = false;
								holding_pmt = false;
							}							
						}
					});
				}
				{
					chkFloating = new JCheckBox("Floating");
					pnlCPFreport_type.add(chkFloating);
					chkFloating.setEnabled(false);	
					chkFloating.setSelected(false);	
					chkFloating.setHorizontalAlignment(JTextField.CENTER);	
					chkFloating.setPreferredSize(new java.awt.Dimension(383, 26));
					chkFloating.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkFloating.isSelected()==true) 
							{								
								all_pmt = false;
								floating_pmt = true;
							} 
							else 
							{								
								floating_pmt = false;
							}											
						}
					});
				}
				{
					chkHolding = new JCheckBox("Holding");
					pnlCPFreport_type.add(chkHolding);
					chkHolding.setEnabled(false);	
					chkHolding.setSelected(false);	
					chkHolding.setHorizontalAlignment(JTextField.CENTER);	
					chkHolding.setPreferredSize(new java.awt.Dimension(383, 26));
					chkHolding.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkHolding.isSelected()==true) 
							{								
								all_pmt = false;
								holding_pmt = true;
							} 
							else 
							{								
								holding_pmt = false;
							}													
						}
					});
				}
				{
					chkAP = new JCheckBox("AP");
					pnlCPFreport_type.add(chkAP);
					chkAP.setEnabled(false);	
					chkAP.setSelected(false);	
					chkAP.setHorizontalAlignment(JTextField.CENTER);	
					chkAP.setPreferredSize(new java.awt.Dimension(383, 26));
					chkAP.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							if(chkAP.isSelected()==true) 
							{								
								all_pmt = false;
								ap_pmt = true;
							} 
							else 
							{								
								ap_pmt = false;
							}					
						}
					});
				}
			}
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setPreferredSize(new java.awt.Dimension(578, 319));
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project / Sales Group / Sales Agent Option" ));// XXX

				pnlNorth_a = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorth_a, BorderLayout.CENTER);
				pnlNorth_a.setPreferredSize(new java.awt.Dimension(578, 177));
				pnlNorth_a.setBorder(components.JTBorderFactory.createTitleBorder("Original Payment Options" ));// XXX
				
				{
					pnlNorthWest = new JPanel(new GridLayout(5,1, 5, 5));
					pnlNorth_a.add(pnlNorthWest, BorderLayout.WEST);
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
									
									String name = (String) data[1];						
									txtCompany.setText(name);									
									
									lookupProject.setValue("");
									lookupPhase.setValue("");
									lookupPBL.setValue("");
									lookupClientName.setValue("");						
									txtProject.setText("");
									txtPhase.setText("");
									txtPBL.setText("");
									txtClientName.setText("");	
									
									proj_id 	= "";
									proj_name 	= "All";									
									ph_code		= "";
									ph_no		= "All";
									pbl_id 		= "";
									pbl_desc  	= "All";	
									client_id 	= "";
									client_name = "All";

									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
									lookupPhase.setLookupSQL(getSubproject(co_id, proj_id));
									lookupPBL.setLookupSQL(getPBL());
									lookupClientName.setLookupSQL(getClientName());
									
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlNorthWest.add(lblProject);
						lblProject.setEnabled(true);	
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(true);
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
																		
									lookupPhase.setValue("");
									lookupPBL.setValue("");
									lookupClientName.setValue("");						
									txtPBL.setText("");
									txtClientName.setText("");	
									txtPhase.setText("");							
									ph_code		= "";
									ph_no		= "All";
									pbl_id 		= "";
									pbl_desc  	= "All";	
									client_id 	= "";
									client_name = "All";
									
									lookupPhase.setLookupSQL(getSubproject(co_id, proj_id));
									lookupPBL.setLookupSQL(getPBL());
									lookupClientName.setLookupSQL(getClientName());
									
								}
							}
						});
					}
					{
						lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlNorthWest.add(lblPhase);
						lblPhase.setBounds(8, 11, 62, 12);
						lblPhase.setEnabled(true);	
						lblPhase.setPreferredSize(new java.awt.Dimension(81, 25));
					}
					{
						lookupPhase = new _JLookup(null, "Phase",0,2);
						pnlNorthWest.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(true);	
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									ph_code		= (String) data[0];	
									ph_no		= (String) data[1];	
									txtPhase.setText(ph_no);									
																		
									lookupPBL.setValue("");
									lookupClientName.setValue("");						
									txtPBL.setText("");
									txtClientName.setText("");	
									pbl_id 		= "";
									pbl_desc  	= "All";	
									client_id 	= "";
									client_name = "All";
									
									lookupPBL.setLookupSQL(getPBL());
									lookupClientName.setLookupSQL(getClientName());
								}
							}
						});
					}
					{
						lblPBL = new JLabel("Ph-Bl-Lot", JLabel.TRAILING);
						pnlNorthWest.add(lblPBL);
						lblPBL.setEnabled(true);	
					}
					{
						lookupPBL = new _JLookup(null, "Sales Group");
						pnlNorthWest.add(lookupPBL);
						lookupPBL.setReturnColumn(0);
						lookupPBL.setEnabled(true);
						lookupPBL.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){								
									
									pbl_id = (String) data[0];		
									pbl_desc = (String) data[1];	
									txtPBL.setText(pbl_desc);
									
									lookupClientName.setValue("");
									txtClientName.setText("");
									client_name 	= "All";
																		
									lookupClientName.setLookupSQL(getClientName());
										
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblClientName = new JLabel("Client Name", JLabel.TRAILING);
						pnlNorthWest.add(lblClientName);
						lblClientName.setEnabled(true);	
					}
					{
						lookupClientName = new _JLookup(null, "Agent Name");
						pnlNorthWest.add(lookupClientName);
						lookupClientName.setReturnColumn(0);
						lookupClientName.setEnabled(true);
						lookupClientName.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									client_id = (String) data[0];		
									client_name = (String) data[1];	
									txtClientName.setText(client_name);
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
				}

				pnlNorthEast = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth_a.add(pnlNorthEast, BorderLayout.CENTER);
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
					txtProject.setEnabled(true);
				}
				{
					txtPhase = new JTextField();
					pnlNorthEast.add(txtPhase, "Phase");
					txtPhase.setEditable(false);
					txtPhase.setEnabled(true);
				}
				{
					txtPBL = new JTextField();
					pnlNorthEast.add(txtPBL, "Center");
					txtPBL.setEditable(false);
					txtPBL.setEnabled(true);
				}
				{
					txtClientName = new JTextField();
					pnlNorthEast.add(txtClientName, BorderLayout.CENTER);
					txtClientName.setEditable(false);
					txtClientName.setEnabled(true);
				}
			}
			{
				pnlNorth_b = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorth_b, BorderLayout.SOUTH);
				pnlNorth_b.setPreferredSize(new java.awt.Dimension(578, 181));
				pnlNorth_b.setBorder(components.JTBorderFactory.createTitleBorder("Credited Payments  Options" ));// XXX

				{
					pnlNorthWest_b = new JPanel(new GridLayout(5,1, 5, 5));
					pnlNorth_b.add(pnlNorthWest_b, BorderLayout.WEST);
					pnlNorthWest_b.setPreferredSize(new java.awt.Dimension(162, 173));

					{
						lblCompany_to = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest_b.add(lblCompany_to);
						lblCompany_to.setEnabled(true);
					}
					{
						lookupCompany_to = new _JLookup(null, "Company");
						pnlNorthWest_b.add(lookupCompany_to);
						lookupCompany_to.setReturnColumn(0);
						lookupCompany_to.setLookupSQL(SQL_COMPANY());
						lookupCompany_to.setEnabled(true);
						lookupCompany_to.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id_to 		= (String) data[0];	
									company_to		= (String) data[1];	
									
									String name = (String) data[1];						
									txtCompany_to.setText(name);									
									
									lookupProject_to.setValue("");
									lookupPhaseTo.setValue("");
									lookupPBL_to.setValue("");
									lookupClientName_to.setValue("");		
									txtProject_to.setText("");
									txtPhaseTo.setText("");
									txtPBL_to.setText("");
									txtClientName_to.setText("");	
									
									proj_id_to 		= "";
									proj_name_to 	= "All";									
									ph_code_to		= "";
									ph_no_to		= "All";
									pbl_id_to 		= "";
									pbl_desc_to  	= "All";	
									client_id_to 	= "";
									client_name_to = "All";

									lookupProject_to.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
									lookupPBL_to.setLookupSQL(getPBL_to());
									lookupClientName_to.setLookupSQL(getClientName_to());
									lookupPhaseTo.setLookupSQL(getSubproject_to(co_id_to, proj_id_to));
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblProject_to = new JLabel("Project", JLabel.TRAILING);
						pnlNorthWest_b.add(lblProject_to);
						lblProject_to.setEnabled(true);	
					}
					{
						lookupProject_to = new _JLookup(null, "Project");
						pnlNorthWest_b.add(lookupProject_to);
						lookupProject_to.setReturnColumn(0);
						lookupProject_to.setEnabled(true);
						lookupProject_to.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									proj_id_to = (String) data[0];		
									proj_name_to = (String) data[1];	
									txtProject_to.setText(proj_name_to);	
									
									lookupPhaseTo.setValue("");
									lookupPBL_to.setValue("");
									lookupClientName_to.setValue("");
									txtPhaseTo.setText("All");
									txtPBL_to.setText("");
									txtClientName_to.setText("");		
									
									ph_code_to		= "";
									ph_no_to		= "All";
									pbl_id_to 		= "";
									pbl_desc_to  	= "";	
									client_id_to 	= "";
									client_name_to = "All";
									
									lookupPBL_to.setLookupSQL(getPBL_to());
									lookupClientName_to.setLookupSQL(getClientName_to());
									lookupPhaseTo.setLookupSQL(getSubproject_to(co_id_to, proj_id_to));
								}
							}
						});
					}
					{
						lblPhaseTo = new JLabel("Phase", JLabel.TRAILING);
						pnlNorthWest_b.add(lblPhaseTo);
						lblPhaseTo.setEnabled(true);	
					}
					{
						lookupPhaseTo = new _JLookup(null, "Division");
						pnlNorthWest_b.add(lookupPhaseTo);
						lookupPhaseTo.setLookupSQL(SQL_COMPANY());
						lookupPhaseTo.setReturnColumn(0);
						lookupPhaseTo.setEnabled(true);
						lookupPhaseTo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									ph_code_to		= (String) data[0];	
									ph_no_to		= (String) data[1];	
									txtPhaseTo.setText(ph_no_to);
									
									lookupPBL_to.setValue("");
									lookupClientName_to.setValue("");									
									txtPBL_to.setText("");
									txtClientName_to.setText("");
									
									pbl_id_to 		= "";
									pbl_desc_to  	= "All";	
									client_id_to 	= "";
									client_name_to = "All";
									
									lookupPBL_to.setLookupSQL(getPBL_to());
									lookupClientName_to.setLookupSQL(getClientName_to());									
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblPBL_to = new JLabel("Ph-Bl-Lot", JLabel.TRAILING);
						pnlNorthWest_b.add(lblPBL_to);
						lblPBL_to.setEnabled(true);	
					}
					{
						lookupPBL_to = new _JLookup(null, "Sales Group");
						pnlNorthWest_b.add(lookupPBL_to);
						lookupPBL_to.setReturnColumn(0);
						lookupPBL_to.setEnabled(true);
						lookupPBL_to.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){								
									
									pbl_id_to = (String) data[0];		
									pbl_desc_to = (String) data[1];	
									txtPBL_to.setText(pbl_desc_to);									
									
									lookupClientName_to.setValue("");									
									txtClientName_to.setText("");	
									
									client_id_to 	= "";
									client_name_to = "All";									
									
									lookupClientName_to.setLookupSQL(getClientName_to());
										
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblClientName_to = new JLabel("Client Name", JLabel.TRAILING);
						pnlNorthWest_b.add(lblClientName_to);
						lblClientName_to.setEnabled(true);	
					}
					{
						lookupClientName_to = new _JLookup(null, "Agent Name");
						pnlNorthWest_b.add(lookupClientName_to);
						lookupClientName_to.setReturnColumn(0);
						lookupClientName_to.setEnabled(true);
						lookupClientName_to.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									client_id_to = (String) data[0];		
									client_name_to = (String) data[1];	
									txtClientName_to.setText(client_name_to);
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
				}

				pnlNorthEast_b = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth_b.add(pnlNorthEast_b, BorderLayout.CENTER);
				pnlNorthEast_b.setPreferredSize(new java.awt.Dimension(300, 159));
				
				{
					txtCompany_to = new JTextField();
					pnlNorthEast_b.add(txtCompany_to, "Center");
					txtCompany_to.setEditable(false);
				}
				{
					txtProject_to = new JTextField();
					pnlNorthEast_b.add(txtProject_to, BorderLayout.CENTER);
					txtProject_to.setEditable(false);
					txtProject_to.setEnabled(true);
				}
				{
					txtPhaseTo = new JTextField();
					pnlNorthEast_b.add(txtPhaseTo, "Center");
					txtPhaseTo.setEditable(false);
					txtPhaseTo.setEnabled(true);
				}
				{
					txtPBL_to = new JTextField();
					pnlNorthEast_b.add(txtPBL_to, "Center");
					txtPBL_to.setEditable(false);
					txtPBL_to.setEnabled(true);
				}
				{
					txtClientName_to = new JTextField();
					pnlNorthEast_b.add(txtClientName_to, BorderLayout.CENTER);
					txtClientName_to.setEditable(false);
					txtClientName_to.setEnabled(true);
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
						lblDateFrom = new JLabel("Payment Date (From) :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setPreferredSize(new java.awt.Dimension(94, 62));
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
						lblDateTo = new JLabel("Payment Date (To) :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(true);	
						lblDateTo.setVisible(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dateDateTo.setVisible(true);	
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
					btnPreview.setEnabled(true);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				/*{
					btnPDF = new JButton("PDF");
					pnlSouth.add(btnPDF);
					btnPDF.setAlignmentX(0.5f);
					btnPDF.setAlignmentY(0.5f);
					btnPDF.setEnabled(false);
					btnPDF.setMaximumSize(new Dimension(100, 30));
					btnPDF.setMnemonic(KeyEvent.VK_P);
					btnPDF.addActionListener(this);
				}*/
				{
					btnCancel = new JButton("Refresh");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_R);
					btnCancel.setEnabled(true);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, lookupPBL, lookupClientName, 
				lookupCompany_to, lookupProject_to, lookupPhaseTo, lookupPBL_to, lookupClientName_to,
				btnPreview));
		this.setBounds(0, 0, 591, 547);  //174
		
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
		
		if(e.getActionCommand().equals("Preview")) { previewARCreditPaymentReport(); }
		
		if(e.getActionCommand().equals("Refresh")) {
			
			refreshFields();
			
			chkFloating.setEnabled(false);	
			chkHolding.setEnabled(false);	
			chkAP.setEnabled(false);
			chkFloating.setSelected(false);	
			chkHolding.setSelected(false);	
			chkAP.setSelected(false);
			chkAll.setEnabled(true);	
			chkAll.setSelected(true);	
			
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					
			all_pmt = true;
			floating_pmt = false;
			ap_pmt = false;
			holding_pmt = false;
			
			proj_id 	= "";
			proj_name 	= "All";	
			ph_code		= "";
			ph_no		= "All";
			pbl_id 		= "";
			pbl_desc  	= "All";	
			client_id 	= "";
			client_name = "All";
			
			proj_id_to	= "";
			proj_name_to= "All";	
			ph_code_to	= "";
			ph_no_to	= "All";
			pbl_id_to	= "";
			pbl_desc_to = "All";	
			client_id_to = "";
			client_name_to= "All";
			
			lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
			lookupPhase.setLookupSQL(getSubproject(co_id, proj_id));
			lookupPBL.setLookupSQL(getPBL());
			lookupClientName.setLookupSQL(getClientName());		
			
			lookupProject_to.setLookupSQL(CheckStatusMonitoring.sql_project(co_id_to));
			lookupPhaseTo.setLookupSQL(getSubproject_to(co_id_to, proj_id_to));
			lookupPBL_to.setLookupSQL(getPBL());
			lookupClientName_to.setLookupSQL(getClientName());	
		}
	}

	public void refreshFields(){
		
		lookupProject.setValue("");
		lookupPhase.setValue("");
		lookupPBL.setValue("");
		lookupClientName.setValue("");	
		
		txtProject.setText("");
		txtPhase.setText("");
		txtPBL.setText("");
		txtClientName.setText("");			
		
		lookupProject_to.setValue("");
		lookupPhaseTo.setValue("");
		lookupPBL_to.setValue("");
		lookupClientName_to.setValue("");	
		
		txtProject_to.setText("");
		txtPhaseTo.setText("");
		txtPBL_to.setText("");
		txtClientName_to.setText("");	
		
	}	
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";	
		
		co_id_to 	= "02";	
		company_to	= "CENQHOMES DEVELOPMENT CORPORATION";	
		
		company_logo = RequestForPayment.sql_getCompanyLogo();
		refreshFields();
						
		txtCompany.setText(company);									
		txtCompany_to.setText(company);			
		
		refreshFields();
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getSubproject(co_id, proj_id));
		lookupPBL.setLookupSQL(getPBL());
		lookupClientName.setLookupSQL(getClientName());		
		
		lookupProject_to.setLookupSQL(CheckStatusMonitoring.sql_project(co_id_to));
		lookupPhaseTo.setLookupSQL(getSubproject_to(co_id_to, proj_id_to));
		lookupPBL_to.setLookupSQL(getPBL());
		lookupClientName_to.setLookupSQL(getClientName());			
				
		lookupCompany.setValue(co_id);
		lookupCompany_to.setValue(co_id_to);
		
		KEYBOARD_MANAGER.focusNextComponent();
}
	
	
	//preview
	private void previewARCreditPaymentReport(){

		String criteria = "AR Credit of Payment";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to",dateDateTo.getDate());
		
		mapParameters.put("all_pmt",all_pmt);
		mapParameters.put("floating_pmt",floating_pmt);
		mapParameters.put("holding_pmt",holding_pmt);
		mapParameters.put("ap_pmt",ap_pmt);
		
		mapParameters.put("co_id", co_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("ph_code", ph_code);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("client_id", client_id);		
		
		mapParameters.put("company", company);
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("ph_no", ph_no);
		mapParameters.put("pbl_desc", pbl_desc);
		mapParameters.put("client_name", client_name);	
		
		mapParameters.put("co_id_to", co_id_to);
		mapParameters.put("proj_id_to", proj_id_to);
		mapParameters.put("ph_code_to", ph_code_to);
		mapParameters.put("pbl_id_to", pbl_id_to);
		mapParameters.put("client_id_to", client_id_to);		
		
		mapParameters.put("company_to", company_to);
		mapParameters.put("proj_name_to", proj_name_to);
		mapParameters.put("ph_no_to", ph_no_to);
		mapParameters.put("pbl_desc_to", pbl_desc_to);
		mapParameters.put("client_name_to", client_name_to);			

		FncReport.generateReport("/Reports/rptAR_CreditPayment.jasper", reportTitle, company, mapParameters);	
		
	}
	
	
	//lookup SQL	
	private String getPBL(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.pbl_id as \"PBL ID\",\r\n" + 
		"b.description  as \"Unit Description\",\r\n" + 
		"c.proj_name as \"Project\" \r\n" + 
		"\r\n" + 
		"\r\n" + 
		"from (select distinct on (pbl_id, seq_no) pbl_id, seq_no from rf_sold_unit) a\r\n" + 
		"left join mf_unit_info b on a.pbl_id = b.pbl_id \r\n" + 
		"left join mf_project c on b.proj_id = c.proj_id \r\n" + 
		"\r\n" + 
		"where c.co_id = '"+co_id+"' " +
		"and (case when '"+proj_id+"' = '' then true else b.proj_id = '"+proj_id+"' end)"  +
		"and (case when '"+ph_no+"' = 'All' then true else b.sub_proj_id = '"+ph_code+"' end)" +
		"order by a.pbl_id, seq_no  "  ;
	}
	
	private String getPBL_to(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.pbl_id as \"PBL ID\",\r\n" + 
		"b.description  as \"Unit Description\",\r\n" + 
		"c.proj_name as \"Project\" \r\n" + 
		"\r\n" + 
		"\r\n" + 
		"from (select distinct on (pbl_id, seq_no) pbl_id, seq_no from rf_sold_unit) a\r\n" + 
		"left join mf_unit_info b on a.pbl_id = b.pbl_id \r\n" + 
		"left join mf_project c on b.proj_id = c.proj_id \r\n" + 
		"\r\n" + 
		"where c.co_id = '"+co_id_to+"' " +
		"and (case when '"+proj_id_to+"' = '' then true else b.proj_id = '"+proj_id_to+"' end)"  +
		"and (case when '"+ph_no_to+"' = 'All' then true else b.sub_proj_id = '"+ph_code_to+"' end)" +
		"order by a.pbl_id, seq_no  "  ;
	}
	
	private String getClientName(){//used
		return 
		"select distinct on (b.entity_name) \r\n" + 
		"\r\n" + 
		"a.entity_id as \"Client ID\",\r\n" + 
		"upper(trim(b.entity_name)) as \"Client Name\"\r\n" + 
		"\r\n" + 
		"from (select distinct on (pbl_id, seq_no) pbl_id, seq_no, entity_id from rf_sold_unit) a\r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id \r\n" + 
		"left join mf_unit_info c on a.pbl_id = c.pbl_id \r\n" + 
		"left join mf_project d on c.proj_id = c.proj_id \r\n" + 
		"\r\n" + 
		"where (case when '"+proj_id+"' = '' then true else c.proj_id = '"+proj_id+"' end)"  +
		"and (case when '"+ph_no+"' = 'All' then true else c.sub_proj_id = '"+ph_code+"' end)"  +
		"and (case when '"+pbl_id+"' = '' then true else a.pbl_id = '"+pbl_id+"' end)"  +
		"order by b.entity_name"  ;

	}	
	
	private String getClientName_to(){//used
		return 
		"select distinct on (b.entity_name) \r\n" + 
		"\r\n" + 
		"a.entity_id as \"Client ID\",\r\n" + 
		"upper(trim(b.entity_name)) as \"Client Name\"\r\n" + 
		"\r\n" + 
		"from (select distinct on (pbl_id, seq_no) pbl_id, seq_no, entity_id from rf_sold_unit) a\r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id \r\n" + 
		"left join mf_unit_info c on a.pbl_id = c.pbl_id \r\n" + 
		"left join mf_project d on c.proj_id = c.proj_id \r\n" + 
		"\r\n" + 
		"where (case when '"+proj_id_to+"' = '' then true else c.proj_id = '"+proj_id_to+"' end)"  +
		"and (case when '"+ph_no_to+"' = 'All' then true else c.sub_proj_id = '"+ph_code_to+"' end)"  +
		"and (case when '"+pbl_id_to+"' = '' then true else a.pbl_id = '"+pbl_id_to+"' end)"  +
		"order by b.entity_name"  ;

	}
	
	private String getSubproject(String coID, String prjID){
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
			"where b.co_id = '"+coID+"' " +
			"and (case when '"+prjID+"' = '' then true else a.proj_id = '"+prjID+"' end) \r\n" + 
			"\r\n" + 
			"order by a.sub_proj_id " ;
		return sql;
	}	
	
	private String getSubproject_to(String coID, String prjID){
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
			"where b.co_id = '"+coID+"' " +
			"and (case when '"+prjID+"' = '' then true else a.proj_id = '"+prjID+"' end) \r\n" + 
			"\r\n" + 
			"order by a.sub_proj_id " ;
		return sql;
	}	
	
	/*
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
	*/


}
