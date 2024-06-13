package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class Processing_Cost_Table extends _JInternalFrame implements _GUI, ActionListener{
	
	/*
	 * AS OF 2021-11-04
	 * 
	 * 1. PARTICULARS PER PROJECT - ADDITIONAL FIELDS FOR COMPANY AND PROJECT; TO CORRECT EACH PAYEE PER PARTICULAR PER PROJECT 2021-11-04
	 * 2. PARTICULARS PER PROJECT (REVISED) - CHANGE PROJECT TO PER LOCATION OF PROJECT 2021-11-11
	 * 
	 * */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Processing/Transfer Cost Table";
	public static Dimension frameSize = new Dimension(520,580);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JLabel lblRefundAcct;
	private JLabel lblPayee;
	private JLabel lblPayeeType;
	private JLabel lblAcctID;
	private JTextField txtDescription;
	//private JTextField txtAmt;
	private _JXFormattedTextField txtAmt;
	private JTextField txtAlias;
	private JTextField txtRemarks;
	private JComboBox cmbClass;
	private JComboBox cmbStatus;
	private JComboBox cmbRefundable;
	private _JLookup lookupAcctID;
	private _JLookup lookupRefundAcct;
	private _JLookup lookupPayee;
	private _JLookup lookupPayeeType;
	private _JLookup lookupCode;
	
	private JLabel lblTRefundAcct;
	private JLabel lblTPayee;
	private JLabel lblTPayeeType;
	private JLabel lblTAcctID;
	private JTextField txtTDescription;
	//private JTextField txtTAmt;
	private _JXFormattedTextField txtTAmt;
	private JTextField txtTAlias;
	private JTextField txtTRemarks;
	private JComboBox cmbTClass;
	private JComboBox cmbTStatus;
	private JComboBox cmbTRefundable;
	private _JLookup lookupTAcctID;
	private _JLookup lookupTRefundAcct;
	private _JLookup lookupTPayee;
	private _JLookup lookupTPayeeType;
	private _JLookup lookupTCode;
	
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	
	//JTabbedPane tabNorth;/*COMMENTED BY JED 2021-11-03 : CHANGE COMPONENT NAME*/
	static JTabbedPane tabCenter;
	
	String to_do 	= "";
	private _JLookup lookupCompany;
	private static String co_id;
	private JXTextField txtCompany;
	private _JLookup lookupProject;
	private JXTextField txtProject;
	private String proj_id;
	private Integer rec_id;
	private JXTextField txtLocation;
	
	
	public Processing_Cost_Table() {
		super(title, false, true, false, true);
		initGUI();
	}

	public Processing_Cost_Table(String title) {
		super(title);
		initGUI();
	}

	public Processing_Cost_Table(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		getContentPane().setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			this.add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setLayout(new BorderLayout(3,3));
			{/*ADDED BY JED 2021-11-03 : ADDITIONAL FIELDS FOR COMPANY AND PROJECT*/
				JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0,90));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Details"));
				{
					JPanel panWestLabel = new JPanel(new GridLayout(2,1,5,5));
					pnlNorth.add(panWestLabel, BorderLayout.WEST);
					{
						JLabel lblCompany = new JLabel("Company");
						panWestLabel.add(lblCompany);
					}
					{
						JLabel lblProject = new JLabel("Project");
						panWestLabel.add(lblProject);
					}
				}	
				{
					JPanel pnlNorthCenter = new JPanel(new GridLayout(2,1,5,5));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(5,5));
						pnlNorthCenter.add(pnlCompany, BorderLayout.CENTER);
						{
							lookupCompany = new _JLookup(null, "Company");	
							pnlCompany.add(lookupCompany, BorderLayout.CENTER);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(company());
							lookupCompany.addLookupListener(new LookupListener() {
								
								@Override
								public void lookupPerformed(LookupEvent event) {
									Object data[] = ((_JLookup)event.getSource()).getDataSet();
									
									if(data!=null) {
										System.out.printf("SQL for company:%s\n", company());	
										co_id = (String) data[0];
										String co_name = (String) data[1];
										
										txtCompany.setText(co_name);
										
										System.out.printf("company id:%s\n", co_id);	
										
										//lookupProject.setLookupSQL(sql_project(co_id));
										lookupProject.setLookupSQL(sql_Location(co_id));
									}				
								}
							});
						}
						{
							txtCompany = new JXTextField("");
							pnlCompany.add(txtCompany, BorderLayout.EAST);
							//txtCompany.setEnabled(false);
							txtCompany.setEditable(false);
							txtCompany.setPreferredSize(new Dimension(350,0));
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(5,5));
						pnlNorthCenter.add(pnlProject, BorderLayout.CENTER);
						{
							lookupProject = new _JLookup(null, "Project");	
							pnlProject.add(lookupProject, BorderLayout.WEST);
							lookupProject.setReturnColumn(0);
							lookupProject.setPreferredSize(new Dimension(72,0));
							lookupProject.addLookupListener(new LookupListener() {
								
								@Override
								public void lookupPerformed(LookupEvent event) {
									Object data[] = ((_JLookup)event.getSource()).getDataSet();
									
									if(data!=null) {
										/*COMMENTED BY JED 2021-11-11 : CHANGE PROJECT TO LOCATION OF PROJECT*/
//										System.out.printf("SQL for project:%s\n", sql_project(co_id));
//										proj_id = (String) data[0];
//										String proj_name = (String) data[1];
//										
//										txtProject.setText(proj_name);
//										
//										if(to_do.equals("edit")) {
//											
//										}
//										else {
//											lookupCode.setLookupSQL(SQL_PCOST(proj_id));
//											lookupTCode.setLookupSQL(SQL_TCOST(proj_id));
//											
//											lookupCode.setEnabled(true);
//											lookupTCode.setEnabled(true);
//											lookupCode.setEditable(true);
//											lookupTCode.setEditable(true);
//											
//											btnNew.setEnabled(true);
//										}
										System.out.printf("SQL for project:%s\n", sql_Location(co_id));

										String location = (String) data[1];
										Integer x = (Integer) data[0];

										txtLocation.setText(location);

										if(x == 1) {
											txtProject.setText("EB/ER/EVE");
										}
										if(x == 2) {
											txtProject.setText("TV");
										}

										if(to_do.equals("edit")) {

										}
										else {
											lookupCode.setLookupSQL(SQL_PCOST(x));
											lookupTCode.setLookupSQL(SQL_TCOST(x));

											lookupCode.setEnabled(true);
											lookupTCode.setEnabled(true);
											lookupCode.setEditable(true);
											lookupTCode.setEditable(true);

											btnNew.setEnabled(true);
										}
									}				
								}
							});
						}
						{
							txtLocation = new JXTextField("");
							pnlProject.add(txtLocation, BorderLayout.CENTER);
							//txtLocation.setEnabled(false);
							txtLocation.setEditable(false);
						}
						{
							txtProject = new JXTextField("");
							pnlProject.add(txtProject, BorderLayout.EAST);
							//txtProject.setEnabled(false);
							txtProject.setEditable(false);
							txtProject.setPreferredSize(new Dimension(250,0));
						}
					}
				}
			}
			{
				//tabNorth = new JTabbedPane();/*COMMENTED BY JED 2021-11-03 : CHANGE COMPONENT NAME*/
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				//tabCenter.setPreferredSize(new Dimension(500, 450));// XXX
				{
					JPanel pnlWest1 = new JPanel(new BorderLayout(5, 5));
					tabCenter.addTab("Processing Cost", null, pnlWest1, null);
					pnlWest1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{

					JPanel pnlWest = new JPanel(new GridLayout(12, 0, 5, 5));
					pnlWest1.add(pnlWest, BorderLayout.WEST);
					{

						JLabel lblCode = new JLabel("PCost ID");
						pnlWest.add(lblCode);
						lblCode.setHorizontalAlignment(JLabel.RIGHT);

						JLabel lblDesc = new JLabel("Description");
						pnlWest.add(lblDesc);
						lblDesc.setHorizontalAlignment(JLabel.RIGHT);

						JLabel lblAlias = new JLabel("Alias");
						pnlWest.add(lblAlias);
						lblAlias.setHorizontalAlignment(JLabel.RIGHT);

						JLabel lblClass = new JLabel("Class");
						pnlWest.add(lblClass);
						lblClass.setHorizontalAlignment(JLabel.RIGHT);

						JLabel lblAmt = new JLabel("Amt");
						pnlWest.add(lblAmt);
						lblAmt.setHorizontalAlignment(JLabel.RIGHT);
						
						JLabel lblAcct = new JLabel("Acct ID");
						pnlWest.add(lblAcct);
						lblAcct.setHorizontalAlignment(JLabel.RIGHT);
						
						JLabel lblRAcct = new JLabel("Refund Acct ID");
						pnlWest.add(lblRAcct);
						lblRAcct.setHorizontalAlignment(JLabel.RIGHT);
						
						JLabel lblPayee = new JLabel("Payee");
						pnlWest.add(lblPayee);
						lblPayee.setHorizontalAlignment(JLabel.RIGHT);
						
						JLabel lblPayeeType = new JLabel("Payee Type");
						pnlWest.add(lblPayeeType);
						lblPayeeType.setHorizontalAlignment(JLabel.RIGHT);
						
						JLabel lblRefundable = new JLabel("Refundable");
						pnlWest.add(lblRefundable);
						lblRefundable.setHorizontalAlignment(JLabel.RIGHT);
						
						JLabel lblRemarks = new JLabel("Remarks");
						pnlWest.add(lblRemarks);
						lblRemarks.setHorizontalAlignment(JLabel.RIGHT);
						
						JLabel lblStatus = new JLabel("Status");
						pnlWest.add(lblStatus);
						lblStatus.setHorizontalAlignment(JLabel.RIGHT);
					}
					JPanel pnlText = new JPanel(new GridLayout(12, 0, 5, 5));
					pnlWest1.add(pnlText, BorderLayout.CENTER);
					{
						JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
						pnlText.add(pnlLookup);
						{
							lookupCode = new _JLookup(null, "Code");
							pnlLookup.add(lookupCode, BorderLayout.WEST);
							lookupCode.setPreferredSize(new Dimension(200, 20));
							lookupCode.setReturnColumn(1);
							lookupCode.setFilterName(true);
							//lookupCode.setLookupSQL(SQL_PCOST());/*COMMENTED BY JED 2021-11-03 : ADDITIONAL FIELDS FOR COMPANY AND PROJECT*/
							lookupCode.addActionListener(this);
							lookupCode.setEnabled(false);
							lookupCode.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										
										rec_id = (Integer) data[13];
										
										txtDescription.setText(String.format("%s", data[2]));
										txtAlias.setText(String.format("%s", data[3]));
										//txtAmt.setText(String.format("%s", data[4]));
										txtAmt.setValue(data[4]);
										cmbClass.setSelectedItem(String.format("%s", data[6]));
										lookupAcctID.setValue(String.format("%s", data[5]));
										//lookupRefundAcct.setValue(String.format("%s", data[7]));/*commented by jed 2021-11-04 : to correct the display of the fields*/
										//lookupPayee.setValue(String.format("%s", data[8]));/*commented by jed 2021-11-04 : to correct the display of the fields*/
										//lookupPayeeType.setValue(String.format("%s", data[9]));/*commented by jed 2021-11-04 : to correct the display of the fields*/
										lookupRefundAcct.setValue(String.format("%s", data[14]));
										lookupPayee.setValue(String.format("%s", data[7]));
										lookupPayeeType.setValue(String.format("%s", data[8]));
										//cmbRefundable.setSelectedItem(String.format("%s", data[10]));
										cmbRefundable.setSelectedItem(String.format("%s", data[9]));
										if (data[11] != null){
											//txtRemarks.setText(String.format("%s", data[11]));
											txtRemarks.setText(String.format("%s", data[10]));
										} else {
											txtRemarks.setText(" ");
										}
										cmbStatus.setSelectedItem(String.format("%s", data[0]));
										//lblPayee.setText(String.format("[ %s ]", data[12]));
										//lblPayeeType.setText(String.format("[ %s ]", data[13]));
										lblPayee.setText(String.format("[ %s ]", data[11]));
										lblPayeeType.setText(String.format("[ %s ]", data[12]));
										
										btnEdit.setEnabled(true);
								
										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtDescription = new JXTextField();
							pnlText.add(txtDescription);
							txtDescription.setHorizontalAlignment(JLabel.LEFT);
							txtDescription.setEditable(false);
						}
						{
							txtAlias = new JXTextField();
							pnlText.add(txtAlias);
							txtAlias.setPreferredSize(new Dimension(200, 20));
							txtAlias.setHorizontalAlignment(JLabel.LEFT);
							txtAlias.setEditable(false);
						}
						{
							cmbClass = new JComboBox(new DefaultComboBoxModel(getClass2()));
							pnlText.add(cmbClass, BorderLayout.WEST);
							cmbClass.setPreferredSize(new Dimension(200, 20));
							cmbClass.setEnabled(false);
						}
						/*{
							txtAmt = new JXTextField();
							pnlText.add(txtAmt);
							txtAmt.setPreferredSize(new Dimension(200, 20));
							txtAmt.setHorizontalAlignment(JLabel.LEFT);
							txtAmt.setEditable(false);
						}*/
						{//**EDITED BY JED 2019-04-23 : CHANGE TEXT FORMAT DUE TO ERROR ON SAVING**//
							txtAmt = new _JXFormattedTextField("0.00");
							pnlText.add(txtAmt);
							txtAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtAmt.setPreferredSize(new Dimension(200, 20));
							txtAmt.setHorizontalAlignment(JLabel.LEFT);
							txtAmt.setEditable(false);
						}
						{
							JPanel pnlAcctid = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlAcctid);
							{
								lookupAcctID = new _JLookup(null, "Acct ID");
								pnlAcctid.add(lookupAcctID, BorderLayout.WEST);
								lookupAcctID.setPreferredSize(new Dimension(150, 20));
								lookupAcctID.setEditable(false);
								lookupAcctID.setReturnColumn(0);
								lookupAcctID.setFilterName(true);
								lookupAcctID.setLookupSQL(SQL_ACCOUNTID());
								lookupAcctID.addActionListener(this);
								lookupAcctID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											lblAcctID.setText(String.format("[ %s ]", data[1]));
											
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
								{
									lblAcctID = new JLabel("[ ]");
									pnlAcctid.add(lblAcctID,BorderLayout.CENTER);
									lblAcctID.setHorizontalAlignment(JLabel.LEFT);
								}
							}
						}
						{
							JPanel pnlRefundAcct = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlRefundAcct);
							{
								lookupRefundAcct = new _JLookup(null, "Refund Acct ID");
								pnlRefundAcct.add(lookupRefundAcct, BorderLayout.WEST);
								lookupRefundAcct.setPreferredSize(new Dimension(150, 20));
								lookupRefundAcct.setReturnColumn(0);
								lookupRefundAcct.setEditable(false);
								lookupRefundAcct.setFilterName(true);
								lookupRefundAcct.setLookupSQL(SQL_ACCOUNTID());
								lookupRefundAcct.addActionListener(this);
								lookupRefundAcct.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											lblRefundAcct.setText(String.format("[ %s ]", data[1]));
											
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
								{
									lblRefundAcct = new JLabel("[ ]");
									pnlRefundAcct.add(lblRefundAcct,BorderLayout.CENTER);
									lblRefundAcct.setHorizontalAlignment(JLabel.LEFT);
								}
							}
						}
						{
							JPanel pnlPayee = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlPayee);
							{
								lookupPayee = new _JLookup(null, "Payee");
								pnlPayee.add(lookupPayee, BorderLayout.WEST);
								lookupPayee.setPreferredSize(new Dimension(150, 20));
								lookupPayee.setReturnColumn(0);
								lookupPayee.setEditable(false);
								lookupPayee.setFilterName(true);
								lookupPayee.setLookupSQL(SQL_PAYEE());
								lookupPayee.addActionListener(this);
								lookupPayee.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											lblPayee.setText(String.format("[ %s ]", (String) data[1]));
											lookupPayeeType.setLookupSQL(SQL_PAYEETYPE((String) data[0]));
											
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
								{
									lblPayee = new JLabel("[ ]");
									pnlPayee.add(lblPayee,BorderLayout.CENTER);
									lblPayee.setHorizontalAlignment(JLabel.LEFT);
								}
							}
						}
						{
							JPanel pnlPayeeType = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlPayeeType);
							{
								lookupPayeeType = new _JLookup(null, "Payee Type");
								pnlPayeeType.add(lookupPayeeType, BorderLayout.WEST);
								lookupPayeeType.setPreferredSize(new Dimension(150, 20));
								lookupPayeeType.setReturnColumn(0);
								lookupPayeeType.setEditable(false);
								lookupPayeeType.setFilterName(true);
								lookupPayeeType.addActionListener(this);
								lookupPayeeType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											lblPayeeType.setText(String.format("[ %s ]", (String) data[1]));
											
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
								{
									lblPayeeType = new JLabel("[ ]");
									pnlPayeeType.add(lblPayeeType,BorderLayout.CENTER);
									lblPayeeType.setHorizontalAlignment(JLabel.LEFT);
								}
							}
						}
						{
							cmbRefundable = new JComboBox(new DefaultComboBoxModel(getClass1()));
							pnlText.add(cmbRefundable, BorderLayout.WEST);
							cmbRefundable.setPreferredSize(new Dimension(200, 20));
							cmbRefundable.setEnabled(false);
						}
						{
							txtRemarks = new JXTextField();
							pnlText.add(txtRemarks);
							txtRemarks.setPreferredSize(new Dimension(200, 20));
							txtRemarks.setHorizontalAlignment(JLabel.LEFT);
							txtRemarks.setEditable(false);
						}
						{
							cmbStatus = new JComboBox(new DefaultComboBoxModel(getClass3()));
							pnlText.add(cmbStatus, BorderLayout.WEST);
							cmbStatus.setPreferredSize(new Dimension(200, 20));
							cmbStatus.setEnabled(false);
						}
						
					}
				}
				{
					JPanel pnlNorth1 = new JPanel(new BorderLayout(5, 5));
					tabCenter.addTab("Transfer Cost", null, pnlNorth1, null);
					pnlNorth1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{

						JPanel pnlWest = new JPanel(new GridLayout(11, 0, 5, 5));
						pnlNorth1.add(pnlWest, BorderLayout.WEST);
						{

							JLabel lblCode = new JLabel("TCost ID");
							pnlWest.add(lblCode);
							lblCode.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblDesc = new JLabel("Description");
							pnlWest.add(lblDesc);
							lblDesc.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblAlias = new JLabel("Alias");
							pnlWest.add(lblAlias);
							lblAlias.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblClass = new JLabel("Class");
							pnlWest.add(lblClass);
							lblClass.setHorizontalAlignment(JLabel.RIGHT);

							JLabel lblAmt = new JLabel("Amt");
							pnlWest.add(lblAmt);
							lblAmt.setHorizontalAlignment(JLabel.RIGHT);
							
							JLabel lblAcct = new JLabel("Acct ID");
							pnlWest.add(lblAcct);
							lblAcct.setHorizontalAlignment(JLabel.RIGHT);
							
							JLabel lblPayee = new JLabel("Payee");
							pnlWest.add(lblPayee);
							lblPayee.setHorizontalAlignment(JLabel.RIGHT);
							
							JLabel lblPayeeType = new JLabel("Payee Type");
							pnlWest.add(lblPayeeType);
							lblPayeeType.setHorizontalAlignment(JLabel.RIGHT);
							
							JLabel lblRefundable = new JLabel("Refundable");
							pnlWest.add(lblRefundable);
							lblRefundable.setHorizontalAlignment(JLabel.RIGHT);
							
							JLabel lblRemarks = new JLabel("Remarks");
							pnlWest.add(lblRemarks);
							lblRemarks.setHorizontalAlignment(JLabel.RIGHT);
							
							JLabel lblStatus = new JLabel("Status");
							pnlWest.add(lblStatus);
							lblStatus.setHorizontalAlignment(JLabel.RIGHT);
						}
						JPanel pnlText = new JPanel(new GridLayout(11, 0, 5, 5));
						pnlNorth1.add(pnlText, BorderLayout.CENTER);
						{
							JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlLookup);
							{
								lookupTCode = new _JLookup(null, "Code");
								pnlLookup.add(lookupTCode, BorderLayout.WEST);
								lookupTCode.setPreferredSize(new Dimension(200, 20));
								lookupTCode.setReturnColumn(1);
								lookupTCode.setFilterName(true);
								//lookupTCode.setLookupSQL(SQL_TCOST());/*COMMENTED BY JED 2021-11-03 : ADDITIONAL FIELDS FOR COMPANY AND PROJECT*/
								lookupTCode.addActionListener(this);
								lookupTCode.setEnabled(false);
								lookupTCode.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											rec_id = (Integer) data[13];
											
											//FncSystem.out("SQL for TCOST", SQL_TCOST());
											txtTDescription.setText(String.format("%s", data[2]));
											txtTAlias.setText(String.format("%s", data[3]));
											//txtTAmt.setText(String.format("%s", data[4]));
											txtTAmt.setValue(data[4]);
											cmbTClass.setSelectedItem(String.format("%s", data[6]));
											lookupTAcctID.setValue(String.format("%s", data[5]));
											//lookupTRefundAcct.setValue(String.format("%s", data[5]));
											lookupTPayee.setValue(String.format("%s", data[7]));
											lookupTPayeeType.setValue(String.format("%s", data[8]));
											cmbTRefundable.setSelectedItem(String.format("%s", data[9]));
											if (data[10] != null){
												txtTRemarks.setText(String.format("%s", data[10]));
											} else {
												txtTRemarks.setText(" ");
											}
											cmbTStatus.setSelectedItem(String.format("%s", data[0]));
											lblTPayee.setText(String.format("[ %s ]", data[11]));
											lblTPayeeType.setText(String.format("[ %s ]", data[12]));
											
											btnEdit.setEnabled(true);
									
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								txtTDescription = new JXTextField();
								pnlText.add(txtTDescription);
								txtTDescription.setHorizontalAlignment(JLabel.LEFT);
								txtTDescription.setEditable(false);
							}
							{
								txtTAlias = new JXTextField();
								pnlText.add(txtTAlias);
								txtTAlias.setPreferredSize(new Dimension(200, 20));
								txtTAlias.setHorizontalAlignment(JLabel.LEFT);
								txtTAlias.setEditable(false);
							}
							{
								cmbTClass = new JComboBox(new DefaultComboBoxModel(getClass2()));
								pnlText.add(cmbTClass, BorderLayout.WEST);
								cmbTClass.setPreferredSize(new Dimension(200, 20));
								cmbTClass.setEnabled(false);
							}
							/*{
								txtTAmt = new JXTextField();
								pnlText.add(txtTAmt);
								txtTAmt.setPreferredSize(new Dimension(200, 20));
								txtTAmt.setHorizontalAlignment(JLabel.LEFT);
								txtTAmt.setEditable(false);
							}*/
							{//**EDITED BY JED 2019-04-23 : CHANGE TEXT FORMAT DUE TO ERROR ON SAVING**//
							txtTAmt = new _JXFormattedTextField("0.00");
							pnlText.add(txtTAmt);
							txtTAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtTAmt.setPreferredSize(new Dimension(200, 20));
							txtTAmt.setHorizontalAlignment(JLabel.LEFT);
							txtTAmt.setEditable(false);
							}
							{
								JPanel pnlAcctid = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlAcctid);
								{
									lookupTAcctID = new _JLookup(null, "Acct ID");
									pnlAcctid.add(lookupTAcctID, BorderLayout.WEST);
									lookupTAcctID.setPreferredSize(new Dimension(150, 20));
									lookupTAcctID.setEditable(false);
									lookupTAcctID.setReturnColumn(0);
									lookupTAcctID.setFilterName(true);
									lookupTAcctID.setLookupSQL(SQL_ACCOUNTID());
									lookupTAcctID.addActionListener(this);
									lookupTAcctID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												lblTAcctID.setText(String.format("[ %s ]", data[1]));
												
												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										lblTAcctID = new JLabel("[ ]");
										pnlAcctid.add(lblTAcctID,BorderLayout.CENTER);
										lblTAcctID.setHorizontalAlignment(JLabel.LEFT);
									}
								}
							}
							{
								JPanel pnlPayee = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlPayee);
								{
									lookupTPayee = new _JLookup(null, "Payee");
									pnlPayee.add(lookupTPayee, BorderLayout.WEST);
									lookupTPayee.setPreferredSize(new Dimension(150, 20));
									lookupTPayee.setReturnColumn(0);
									lookupTPayee.setEditable(false);
									lookupTPayee.setFilterName(true);
									lookupTPayee.setLookupSQL(SQL_PAYEE());
									lookupTPayee.addActionListener(this);
									lookupTPayee.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												lblTPayee.setText(String.format("[ %s ]", (String) data[1]));
												lookupTPayeeType.setLookupSQL(SQL_PAYEETYPE((String) data[0]));
												
												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										lblTPayee = new JLabel("[ ]");
										pnlPayee.add(lblTPayee,BorderLayout.CENTER);
										lblTPayee.setHorizontalAlignment(JLabel.LEFT);
									}
								}
							}
							{
								JPanel pnlPayeeType = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlPayeeType);
								{
									lookupTPayeeType = new _JLookup(null, "Payee Type");
									pnlPayeeType.add(lookupTPayeeType, BorderLayout.WEST);
									lookupTPayeeType.setPreferredSize(new Dimension(150, 20));
									lookupTPayeeType.setReturnColumn(0);
									lookupTPayeeType.setEditable(false);
									lookupTPayeeType.setFilterName(true);
									lookupTPayeeType.addActionListener(this);
									lookupTPayeeType.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												lblTPayeeType.setText(String.format("[ %s ]", (String) data[1]));
												
												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										lblTPayeeType = new JLabel("[ ]");
										pnlPayeeType.add(lblTPayeeType,BorderLayout.CENTER);
										lblTPayeeType.setHorizontalAlignment(JLabel.LEFT);
									}
								}
							}
							{
								cmbTRefundable = new JComboBox(new DefaultComboBoxModel(getClass1()));
								pnlText.add(cmbTRefundable, BorderLayout.WEST);
								cmbTRefundable.setPreferredSize(new Dimension(200, 20));
								cmbTRefundable.setEnabled(false);
							}
							{
								txtTRemarks = new JXTextField();
								pnlText.add(txtTRemarks);
								txtTRemarks.setPreferredSize(new Dimension(200, 20));
								txtTRemarks.setHorizontalAlignment(JLabel.LEFT);
								txtTRemarks.setEditable(false);
							}
							{
								cmbTStatus = new JComboBox(new DefaultComboBoxModel(getClass3()));
								pnlText.add(cmbTStatus, BorderLayout.WEST);
								cmbTStatus.setPreferredSize(new Dimension(200, 20));
								cmbTStatus.setEnabled(false);
							}
							
						}
					}
				}
				}
				{
					JPanel pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					pnlSouth.setPreferredSize(new Dimension(700, 35));// XXX
					{
						btnNew = new JButton("New");
						pnlSouth.add(btnNew);
						btnNew.setActionCommand("New");
						btnNew.addActionListener(this);
						btnNew.setEnabled(false);/*added by jed 2021-11-04 : to correct the status of the fields*/
					}
					{
						btnEdit = new JButton("Edit");
						pnlSouth.add(btnEdit);
						btnEdit.setActionCommand("Edit");
						btnEdit.addActionListener(this);
						btnEdit.setEnabled(false);
					}
					{
						btnSave = new JButton("Save");
						pnlSouth.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.addActionListener(this);
						btnSave.setEnabled(false);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlSouth.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.addActionListener(this);
					}
				}
			}
		}
	}

//	private static String SQL_PCOST() {//**EDITED BY JED 2019-04-10 : ALL PARTICULARS MUST BE DISPLAYED**//
//		return "SELECT (case when status_id = 'A' then 'Active' else 'Inactive' end) as \"Status\", pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_alias as \"PCost Alias\", pcostdtl_amt as \"PCost Amount\", acct_id as \"Acct ID\", (case when pcostdtl_type = 'B' then 'Buyer Related' else 'Block - Lot Related' end) as \"PCost Type\", refund_acct_id as \"Refund Acct ID\", payee_id as \"Payee\", payee_type_id as \"Payee Type ID\", (case when refundable = true then 'Yes' else 'No' end) as \"Refundable\", remarks as \"Remarks\", get_client_name(payee_id) as \"Payee Name\", get_entity_type(payee_type_id) as \"Payee Type\"\n" + 
//				"FROM mf_processing_cost_dl\n" + 
//				//"WHERE status_id = 'A'\n" +
//				//"AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '"+entity+"')\n" +
//				//"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
//				"ORDER BY pcostdtl_desc ASC;";
//	}
	
//	private static String SQL_PCOST(String proj_id) {//**EDITED BY JED 2019-04-10 : ALL PARTICULARS MUST BE DISPLAYED**//
//		return "SELECT (case when status_id = 'A' then 'Active' else 'Inactive' end) as \"Status\", pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_alias as \"PCost Alias\", pcostdtl_amt as \"PCost Amount\", acct_id as \"Acct ID\", (case when pcostdtl_type = 'B' then 'Buyer Related' else 'Block - Lot Related' end) as \"PCost Type\", payee_id as \"Payee\", payee_type_id as \"Payee Type ID\", (case when refundable = true then 'Yes' else 'No' end) as \"Refundable\", remarks as \"Remarks\", get_client_name(payee_id) as \"Payee Name\", get_entity_type(payee_type_id) as \"Payee Type\", rec_id, refund_acct_id as \"Refund Acct ID\"\n" + 
//				"FROM mf_processing_cost_dl\n" + 
//				"WHERE proj_id = '"+proj_id+"'\n" + 
//				"ORDER BY pcostdtl_desc ASC;";
//	}
	
	private static String SQL_PCOST(Integer index) {//**EDITED BY JED 2019-04-10 : ALL PARTICULARS MUST BE DISPLAYED**//
		
		return "SELECT distinct on (trim(pcostdtl_desc), trim(pcostdtl_id))--co_id, proj_id, \n" + 
				"(case when status_id = 'A' then 'Active' else 'Inactive' end) as \"Status\", \n" + 
				"pcostdtl_id as \"PCost ID.\", \n" + 
				"trim(pcostdtl_desc) as \"PCost Desc\", \n" + 
				"pcostdtl_alias as \"PCost Alias\",\n" + 
				"pcostdtl_amt as \"PCost Amount\", \n" + 
				"acct_id as \"Acct ID\", \n" + 
				"(case when pcostdtl_type = 'B' then 'Buyer Related' else 'Block - Lot Related' end) as \"PCost Type\", \n" + 
				"payee_id as \"Payee\",\n" + 
				"payee_type_id as \"Payee Type ID\", \n" + 
				"(case when refundable = true then 'Yes' else 'No' end) as \"Refundable\", \n" + 
				"remarks as \"Remarks\", \n" + 
				"get_client_name(payee_id) as \"Payee Name\", \n" + 
				"get_entity_type(payee_type_id) as \"Payee Type\", \n" + 
				"rec_id, \n" + 
				"refund_acct_id as \"Refund Acct ID\"\n" + 
				"FROM mf_processing_cost_dl\n" + 
				"WHERE co_id = '"+co_id+"'  \n" + 
//				"and (\n" + 
//				"		case\n" + 
//				"				when "+index+" = 1 then proj_id in ('017', '018', '019')\n" + 
//				"	 			when "+index+" = 2 then proj_id = '015'\n" + 
//				"	 			else false \n" + 
//				"		end\n" + 
//				"	)\n" + 
				"ORDER BY trim(pcostdtl_id) ASC";
	}
	
//	private static String SQL_TCOST() {//**EDITED BY JED 2019-04-10 : ALL PARTICULARS MUST BE DISPLAYED**//
//		return "SELECT (case when status_id = 'A' then 'Active' else 'Inactive' end) as \"Status\", tcostdtl_id as \"TCost ID.\", trim(tcostdtl_desc) as \"TCost Desc\", tcostdtl_alias as \"TCost Alias\","
//				+ "tcostdtl_amt as \"TCost Amount\", acct_id as \"Acct ID\","
//				+ "(case when tcostdtl_type = 'B' then 'Buyer Related' else 'Block - Lot Related' end) as \"TCost Type\","
//				+ "payee_id as \"Payee\", payee_type_id as \"Payee Type ID\","
//				+ "(case when refundable = true then 'Yes' else 'No' end) as \"Refundable\", remarks as \"Remarks\","
//				+ "get_client_name(payee_id) as \"Payee Name\","
//				+ "get_entity_type(payee_type_id) as \"Payee Type\"\n" + 
//				"FROM mf_transfer_cost_dl\n" + 
//				//"WHERE status_id = 'A'\n" +
//				"ORDER BY tcostdtl_desc ASC;";
//	}
	
//	private static String SQL_TCOST(String proj_id) {//**EDITED BY JED 2019-04-10 : ALL PARTICULARS MUST BE DISPLAYED**//
//		return "SELECT (case when status_id = 'A' then 'Active' else 'Inactive' end) as \"Status\", tcostdtl_id as \"TCost ID.\", trim(tcostdtl_desc) as \"TCost Desc\", tcostdtl_alias as \"TCost Alias\","
//				+ "tcostdtl_amt as \"TCost Amount\", acct_id as \"Acct ID\","
//				+ "(case when tcostdtl_type = 'B' then 'Buyer Related' else 'Block - Lot Related' end) as \"TCost Type\","
//				+ "payee_id as \"Payee\", payee_type_id as \"Payee Type ID\","
//				+ "(case when refundable = true then 'Yes' else 'No' end) as \"Refundable\", remarks as \"Remarks\","
//				+ "get_client_name(payee_id) as \"Payee Name\","
//				+ "get_entity_type(payee_type_id) as \"Payee Type\", rec_id\n" + 
//				"FROM mf_transfer_cost_dl\n" + 
//				"WHERE proj_id = '"+proj_id+"'\n" +
//				"ORDER BY tcostdtl_desc ASC;";
//	}
	
	private static String SQL_TCOST(Integer index) {//**EDITED BY JED 2019-04-10 : ALL PARTICULARS MUST BE DISPLAYED**//
		return "SELECT distinct on (trim(tcostdtl_desc), trim(tcostdtl_id), TRIM(payee_id))\n" + 
				"--co_id, proj_id, \n" + 
				"(case when status_id = 'A' then 'Active' else 'Inactive' end) as \"Status\", \n" + 
				"tcostdtl_id as \"TCost ID.\", \n" + 
				"trim(tcostdtl_desc) as \"TCost Desc\", \n" + 
				"tcostdtl_alias as \"TCost Alias\",\n" + 
				"tcostdtl_amt as \"TCost Amount\", \n" + 
				"acct_id as \"Acct ID\",\n" + 
				"(case when tcostdtl_type = 'B' then 'Buyer Related' else 'Block - Lot Related' end) as \"TCost Type\",\n" + 
				"payee_id as \"Payee\", \n" + 
				"payee_type_id as \"Payee Type ID\",\n" + 
				"(case when refundable = true then 'Yes' else 'No' end) as \"Refundable\", \n" + 
				"remarks as \"Remarks\",\n" + 
				"get_client_name(payee_id) as \"Payee Name\",\n" + 
				"get_entity_type(payee_type_id) as \"Payee Type\", \n" + 
				"rec_id\n" + 
				"FROM mf_transfer_cost_dl\n" + 
				"WHERE co_id = '"+co_id+"' \n" + 
				"and (\n" + 
				"		case\n" + 
				"				when "+index+" = 2 then proj_id = '015'\n" + 
				"	 			else proj_id != '015' \n" + 
				"		end\n" + 
				"	)\n" + 
				"ORDER BY trim(tcostdtl_id) ASC";
	}
	
	private static String SQL_ACCOUNTID() {
		return "SELECT acct_id as \"Acct ID.\", trim(acct_name) as \"Acct Name\"\n" + 
				"FROM mf_boi_chart_of_accounts\n" + 
				"WHERE status_id = 'A'\n" +
				"ORDER BY acct_name ASC;";
	}
	private static String SQL_PAYEE() {
		return "SELECT trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\", entity_kind as \"Kind\", vat_registered as \"VAT\"  \n" +
						"FROM (select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in \r\n" + 
						"(select entity_id from rf_entity_assigned_type " +
						")) a \n" +
						"ORDER BY a.entity_name  ";		
	}
	private static String SQL_PAYEETYPE(String entity_id) {
		return "SELECT a.entity_type_id, trim(b.entity_type_desc), c.wtax_id, c.wtax_rate \n" + 
						"FROM (select * from rf_entity_assigned_type where trim(entity_id) =  '"+entity_id+"' ) a \r\n" + 
						"LEFT JOIN mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n" + 		
						"LEFT JOIN rf_withholding_tax c on b.wtax_id = c.wtax_id"  ;
	}
	private String[] getClass1() {
		return new String[] {
				"Yes",
				"No",
		};
	}
	private String[] getClass2() {
		return new String[] {
				"Buyer Related",
				"Block - Lot Related",
		};
	}
	private String[] getClass3() {
		return new String[] {
				"Active",
				"Inactive",
		};
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){ cancel();}

		if(e.getActionCommand().equals("New")){
			if(UserInfo.EmployeeCode.equals("900054")||UserInfo.EmployeeCode.equals("900449")||UserInfo.EmployeeCode.equals("900933") 
				|| UserInfo.EmployeeCode.equals("900876") || UserInfo.EmployeeCode.equals("901128"))
			{
				New();
			}
			else 
			{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to add new Processing Cost.",
					"Information",JOptionPane.WARNING_MESSAGE); }
		}

		if(e.getActionCommand().equals("Edit")){
			if(UserInfo.EmployeeCode.equals("900054")||UserInfo.EmployeeCode.equals("900449")||UserInfo.EmployeeCode.equals("900933")||UserInfo.EmployeeCode.equals("901128"))
			{
				edit(); 
			}
			else 
			{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to update Processing Cost.",
					"Information",JOptionPane.WARNING_MESSAGE); }
		}

		if(e.getActionCommand().equals("Save")){ save();}

	}
	private void edit(){
		
		if (tabCenter.getSelectedIndex() == 0) {
		btnSave.setEnabled(true);
		btnEdit.setEnabled(false);
		btnNew.setEnabled(false);
		txtDescription.setEditable(true);
		txtAlias.setEditable(true);
		cmbClass.setEnabled(true);
		txtAmt.setEditable(true);
		lookupAcctID.setEditable(true);
		lookupRefundAcct.setEditable(true);
		lookupPayee.setEditable(true);
		lookupPayeeType.setEditable(true);
		cmbRefundable.setEnabled(true);
		txtRemarks.setEditable(true);
		cmbStatus.setEnabled(true);
		
		to_do = "edit";
		
		/*added by jed 2021-11-04 : to correct the status of the fields*/
		pnlState(true,false);
		} 
		if (tabCenter.getSelectedIndex() == 1) {
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			btnNew.setEnabled(false);
			txtTDescription.setEditable(true);
			txtTAlias.setEditable(true);
			cmbTClass.setEnabled(true);
			txtTAmt.setEditable(true);
			lookupTAcctID.setEditable(true);
			lookupTPayee.setEditable(true);
			lookupTPayeeType.setEditable(true);
			cmbTRefundable.setEnabled(true);
			txtTRemarks.setEditable(true);
			cmbTStatus.setEnabled(true);
			
			to_do = "edit";
			
			/*added by jed 2021-11-04 : to correct the status of the fields*/
			pnlState(false,true);
		}
		
		lookupCompany.setEnabled(false);
		lookupProject.setEnabled(false);
	}
	private void New(){
		
		if (tabCenter.getSelectedIndex() == 0) {
		btnSave.setEnabled(true);		
		btnEdit.setEnabled(false);
		btnNew.setEnabled(false);
		lookupCode.setValue(generatePCostID());
		txtDescription.setText("");
		txtAlias.setText("");
		cmbClass.setSelectedItem("");
		txtAmt.setValue(BigDecimal.valueOf(0.00));
		lookupAcctID.setValue("");
		lblAcctID.setText("[ ]");
		lookupRefundAcct.setValue("");
		lblRefundAcct.setText("[ ]");
		lookupPayee.setValue("");
		lblPayee.setText("[ ]");
		lookupPayeeType.setValue("");
		lblPayeeType.setText("[ ]");
		cmbRefundable.setSelectedItem("");
		txtRemarks.setText("");
		cmbStatus.setSelectedItem("");
//		lookupCompany.setValue(null);/*commented by jed 2021-11-04 : to correct the status of the fields*/
//		txtCompany.setText("");/*commented by jed 2021-11-04 : to correct the status of the fields*/
//		lookupProject.setValue(null);/*commented by jed 2021-11-04 : to correct the status of the fields*/
//		txtProject.setText("");/*commented by jed 2021-11-04 : to correct the status of the fields*/
		lookupCompany.setEnabled(false);
		lookupProject.setEnabled(false);
		
		lookupCode.setEditable(false);
		lookupCode.setEnabled(true);
		txtDescription.setEditable(true);
		txtAlias.setEditable(true);
		cmbClass.setEnabled(true);
		txtAmt.setEditable(true);
		lookupAcctID.setEditable(true);
		lookupRefundAcct.setEditable(true);
		lookupPayee.setEditable(true);
		lookupPayeeType.setEditable(true);
		cmbRefundable.setEnabled(true);
		txtRemarks.setEditable(true);
		cmbStatus.setEnabled(true);
		
		/*added by jed 2021-11-04 : to correct the status of the fields*/
		to_do = "New";
		pnlState(true,false);
		
		}
		if (tabCenter.getSelectedIndex() == 1) {
			btnSave.setEnabled(true);		
			btnEdit.setEnabled(false);
			btnNew.setEnabled(false);
			lookupTCode.setValue(generateTCostID());
			txtTDescription.setText("");
			txtTAlias.setText("");
			cmbTClass.setSelectedItem("");
			txtTAmt.setValue(BigDecimal.valueOf(0.00));
			lookupTAcctID.setValue("");
			lblTAcctID.setText("[ ]");
			lookupTPayee.setValue("");
			lblTPayee.setText("[ ]");
			lookupTPayeeType.setValue("");
			lblTPayeeType.setText("[ ]");
			cmbTRefundable.setSelectedItem("");
			txtTRemarks.setText("");
			cmbTStatus.setSelectedItem("");
//			lookupCompany.setValue(null);/*commented by jed 2021-11-04 : to correct the status of the fields*/
//			txtCompany.setText("");/*commented by jed 2021-11-04 : to correct the status of the fields*/
//			lookupProject.setValue(null);/*commented by jed 2021-11-04 : to correct the status of the fields*/
//			txtProject.setText("");/*commented by jed 2021-11-04 : to correct the status of the fields*/
			lookupCompany.setEnabled(false);
			lookupProject.setEnabled(false);
			
			lookupTCode.setEditable(false);
			lookupTCode.setEnabled(true);
			txtTDescription.setEditable(true);
			txtTAlias.setEditable(true);
			cmbTClass.setEnabled(true);
			txtTAmt.setEditable(true);
			lookupTAcctID.setEditable(true);
			lookupTPayee.setEditable(true);
			lookupTPayeeType.setEditable(true);
			cmbTRefundable.setEnabled(true);
			txtTRemarks.setEditable(true);
			cmbTStatus.setEnabled(true);
			
			/*added by jed 2021-11-04 : to correct the status of the fields*/
			to_do = "New";
			pnlState(false,true);
		}
	}
	private void cancel(){
		
		if (tabCenter.getSelectedIndex() == 0) {
		//btnNew.setEnabled(true);
		btnSave.setEnabled(false);		
		btnEdit.setEnabled(false);	
		lookupCode.setValue("");
		txtDescription.setText("");
		txtAlias.setText("");
		cmbClass.setSelectedItem("");
		txtAmt.setValue(0);
		lookupAcctID.setValue("");
		lblAcctID.setText("[ ]");
		lookupRefundAcct.setValue("");
		lblRefundAcct.setText("[ ]");
		lookupPayee.setValue("");
		lblPayee.setText("[ ]");
		lookupPayeeType.setValue("");
		lblPayeeType.setText("[ ]");
		cmbRefundable.setSelectedItem("");
		txtRemarks.setText("");
		cmbStatus.setSelectedItem("");
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		
		//lookupCode.setEditable(true);/*commented by jed 2021-11-04 : to correct the status of the fields*/
		txtDescription.setEditable(false);
		txtAlias.setEditable(false);
		cmbClass.setEnabled(false);
		txtAmt.setEditable(false);
		lookupAcctID.setEditable(false);
		lookupRefundAcct.setEditable(false);
		lookupPayee.setEditable(false);
		lookupPayeeType.setEditable(false);
		cmbRefundable.setEnabled(false);
		txtRemarks.setEditable(false);
		cmbStatus.setEnabled(false);
		} 
		if (tabCenter.getSelectedIndex() == 1) {
			//btnNew.setEnabled(true);
			btnSave.setEnabled(false);		
			btnEdit.setEnabled(false);	
			lookupTCode.setValue("");
			txtTDescription.setText("");
			txtTAlias.setText("");
			cmbTClass.setSelectedItem("");
			txtTAmt.setValue(0);
			lookupTAcctID.setValue("");
			lblTAcctID.setText("[ ]");
			lookupTPayee.setValue("");
			lblTPayee.setText("[ ]");
			lookupTPayeeType.setValue("");
			lblTPayeeType.setText("[ ]");
			cmbTRefundable.setSelectedItem("");
			txtTRemarks.setText("");
			cmbTStatus.setSelectedItem("");
			lookupCompany.setValue(null);
			txtCompany.setText("");
			lookupProject.setValue(null);
			txtProject.setText("");
			
			//lookupTCode.setEditable(true);/*commented by jed 2021-11-04 : to correct the status of the fields*/
			txtTDescription.setEditable(false);
			txtTAlias.setEditable(false);
			cmbTClass.setEnabled(false);
			txtTAmt.setEditable(false);
			lookupTAcctID.setEditable(false);
			lookupTPayee.setEditable(false);
			lookupTPayeeType.setEditable(false);
			cmbTRefundable.setEnabled(false);
			txtTRemarks.setEditable(false);
			cmbTStatus.setEnabled(false);
		}
		
		/*added by jed 2021-11-04 : to correct the status of the fields*/
		lookupCode.setEditable(false);
		lookupCode.setEnabled(false);
		lookupTCode.setEditable(false);
		lookupTCode.setEnabled(false);
		
		lookupCompany.setEnabled(true);
		lookupProject.setEnabled(true);
		
		btnNew.setEnabled(false);
		
		to_do = "New";
		pnlState(true,true);
		
		txtLocation.setText("");
		
	}
	private void save(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	

			if (to_do.equals("New")) 
			{
				//savePCost(db); 
				savePCost();
				//db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"New PCost/TCost ID was added.","Information",JOptionPane.INFORMATION_MESSAGE);
				cancel();

			} 

			else if (to_do.equals("edit"))
			{				
				//updatePCost(db); 
				updatePCost();
				//db.commit();				
				JOptionPane.showMessageDialog(getContentPane(),"PCost/TCost ID was successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				cancel();
			}			
		}

		else {}	



	}
	private static String generatePCostID() {
		String strSQL = "SELECT to_char(COALESCE(MAX(pcostdtl_id::INT), 0) + 1, 'FM000') FROM mf_processing_cost_dl;";

		FncSystem.out("PCost ID", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	private static String generateTCostID() {
		String strSQL = "SELECT to_char(COALESCE(MAX(tcostdtl_id::INT), 0) + 1, 'FM000') FROM mf_transfer_cost_dl where status_id = 'A';";

		FncSystem.out("TCost ID", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	
	/*COMMENTED BY JED 2021-11-11 : TRANSFER INSERT METHOD TO FUNCTION*/
//	private void savePCost(pgUpdate db){
//		if (tabCenter.getSelectedIndex() == 0) {
//		String code = (String) lookupCode.getValue();
//		String description = (String) txtDescription.getText();
//		String alias = (String) txtAlias.getText();
//		String clas = (String) cmbClass.getSelectedItem();
//		//BigDecimal amt = new BigDecimal (txtAmt.getText());
//		BigDecimal amt = (BigDecimal) txtAmt.getValued();//**edited by jed 2019-05-06**//
//		String acctid = (String) lookupAcctID.getValue();
//		String refundacct = (String) lookupRefundAcct.getValue();
//		String payee = (String) lookupPayee.getValue();
//		String payeetype = (String) lookupPayeeType.getValue();
//		String refund = (String) cmbRefundable.getSelectedItem();
//		String remarks = (String) txtRemarks.getText();
//		String status = (String) cmbStatus.getSelectedItem();
//		
//		/*added by jed 2021-11-04 : additional filter for inserting new particular*/
//		String co_id = lookupCompany.getValue();
//		String proj_id = lookupProject.getValue();
//
//		
//		String sqlDetail = 
//				"INSERT INTO mf_processing_cost_dl(\n" + 
//				"            pcost_id, pcost_type, pcostdtl_id, pcostdtl_type, pcostdtl_desc, \n" + 
//				"            pcostdtl_alias, pcostdtl_amt, refundable, acct_id, remarks, nsp_pctg, \n" + 
//				"            udf_name, transfer_cost, tcost_pctg, refund_acct_id, payee_id, \n" + 
//				"            payee_type_id, validation_udf_name, validation_msg, status_id, \n" + 
//				"            old_acct_id, old_refund_acct_id, with_liquidation, multiple_entry, \n" + 
//				"            created_by, date_created, edited_by, date_edited, co_id, proj_id)\n" + 
//				"    VALUES ('01', '01' , '"+code+"', (case when '"+clas+"' = 'Buyer Related' then 'B' else 'L' end), '"+description+"',\n" + 
//				"            '"+alias+"', "+amt+", (case when '"+refund+"' = 'Yes' then true else false end), '"+acctid+"', NULLIF('"+remarks+"','null'), null, \n" + 
//				"            null, null, null, NULLIF('"+refundacct+"','null'), '"+payee+"', \n" + 
//				"            '"+payeetype+"', null, null, (case when '"+status+"' = 'Active' then 'A' else 'I' end), \n" + 
//				"            null, null, false, false, \n" + 
//				"            '"+UserInfo.EmployeeCode+"', now(), null, null, '"+co_id+"', '"+proj_id+"')";
//		
//		System.out.printf("SQL #1 - NewPCost: %s", sqlDetail);
//		db.executeUpdate(sqlDetail, false);	
//		}
//		if (tabCenter.getSelectedIndex() == 1) {
//			String code = (String) lookupTCode.getValue();
//			String description = (String) txtTDescription.getText();
//			String alias = (String) txtTAlias.getText();
//			String clas = (String) cmbTClass.getSelectedItem();
//			//BigDecimal amt = new BigDecimal (txtTAmt.getText());
//			BigDecimal amt = (BigDecimal) txtTAmt.getValued();//**edited by jed 2019-05-06**//
//			String acctid = (String) lookupTAcctID.getValue();
//			String payee = (String) lookupTPayee.getValue();
//			String payeetype = (String) lookupTPayeeType.getValue();
//			String refund = (String) cmbRefundable.getSelectedItem();
//			String remarks = (String) txtTRemarks.getText();
//			String status = (String) cmbTStatus.getSelectedItem();
//			
//			/*added by jed 2021-11-04 : additional filter for inserting new particular*/
//			String co_id = lookupCompany.getValue();
//			String proj_id = lookupProject.getValue();
//			
//			String sqlDetail = 
//					"INSERT INTO mf_transfer_cost_dl(\n" + 
//					"            tcost_id, tcost_type, tcostdtl_id, tcostdtl_type, tcostdtl_desc, \n" + 
//					"            tcostdtl_alias, tcostdtl_amt, refundable, acct_id, remarks, nsp_pctg, \n" + 
//					"            payee_id, \n" + 
//					"            payee_type_id, status_id, \n" + 
//					"            old_acct_id, multiple_entry, \n" + 
//					"            created_by, date_created, for_tcostcomp, co_id, proj_id)\n" + 
//					"    VALUES ('01', '01' , '"+code+"', (case when '"+clas+"' = 'Buyer Related' then 'B' else 'L' end), '"+description+"',\n" + 
//					"            '"+alias+"', "+amt+", (case when '"+refund+"' = 'Yes' then true else false end), '"+acctid+"', NULLIF('"+remarks+"','null'), null, \n" + 
//					"            '"+payee+"', \n" + 
//					"            '"+payeetype+"', (case when '"+status+"' = 'Active' then 'A' else 'I' end), \n" + 
//					"            null, false, \n" + 
//					"            '"+UserInfo.EmployeeCode+"', now(), false, '"+co_id+"', '"+proj_id+"')";
//			
//		}
//		
//		to_do = "New";
//		pnlState(true,true);
//		lookupCompany.setEnabled(true);
//		lookupProject.setEnabled(true);
//	}
	
	private void savePCost(){
		if (tabCenter.getSelectedIndex() == 0) {
		String code = (String) lookupCode.getValue();
		String description = (String) txtDescription.getText();
		String alias = (String) txtAlias.getText();
		String clas = (String) cmbClass.getSelectedItem();
		//BigDecimal amt = new BigDecimal (txtAmt.getText());
		BigDecimal amt = (BigDecimal) txtAmt.getValued();//**edited by jed 2019-05-06**//
		String acctid = (String) lookupAcctID.getValue();
		String refundacct = (String) lookupRefundAcct.getValue();
		String payee = (String) lookupPayee.getValue();
		String payeetype = (String) lookupPayeeType.getValue();
		String refund = (String) cmbRefundable.getSelectedItem();
		String remarks = (String) txtRemarks.getText();
		String status = (String) cmbStatus.getSelectedItem();
		
		/*added by jed 2021-11-04 : additional filter for inserting new particular*/
		String co_id = lookupCompany.getValue();
		//String proj_id = lookupProject.getValue();
		Integer index = Integer.parseInt(lookupProject.getValue());
		String type = "PCOST";
		
//		String sqlDetail = 
//				"INSERT INTO mf_processing_cost_dl(\n" + 
//				"            pcost_id, pcost_type, pcostdtl_id, pcostdtl_type, pcostdtl_desc, \n" + 
//				"            pcostdtl_alias, pcostdtl_amt, refundable, acct_id, remarks, nsp_pctg, \n" + 
//				"            udf_name, transfer_cost, tcost_pctg, refund_acct_id, payee_id, \n" + 
//				"            payee_type_id, validation_udf_name, validation_msg, status_id, \n" + 
//				"            old_acct_id, old_refund_acct_id, with_liquidation, multiple_entry, \n" + 
//				"            created_by, date_created, edited_by, date_edited, co_id, proj_id)\n" + 
//				"    VALUES ('01', '01' , '"+code+"', (case when '"+clas+"' = 'Buyer Related' then 'B' else 'L' end), '"+description+"',\n" + 
//				"            '"+alias+"', "+amt+", (case when '"+refund+"' = 'Yes' then true else false end), '"+acctid+"', NULLIF('"+remarks+"','null'), null, \n" + 
//				"            null, null, null, NULLIF('"+refundacct+"','null'), '"+payee+"', \n" + 
//				"            '"+payeetype+"', null, null, (case when '"+status+"' = 'Active' then 'A' else 'I' end), \n" + 
//				"            null, null, false, false, \n" + 
//				"            '"+UserInfo.EmployeeCode+"', now(), null, null, '"+co_id+"', '"+proj_id+"')";

		String sqlDetail = 
				"select * from sp_save_transaction_particulars ('"+type+"', \n" +
						""+index+", \n" +
						"'"+code+"', \n" +
						"'"+clas+"', \n" +
						"'"+description+"', \n" +
						"'"+alias+"', \n" +
						""+amt+", \n" +
						"'"+refund+"', \n" +
						"'"+acctid+"', \n" +
						"'"+remarks+"', \n" +
						"'"+refundacct+"', \n" +
						"'"+payee+"', \n" +
						"'"+payeetype+"', \n" +
						"'"+status+"', \n" +
						"'"+UserInfo.EmployeeCode+"')";
		
		System.out.printf("SQL #1 - NewPCost: %s", sqlDetail);
		pgSelect db = new pgSelect();
		db.select(sqlDetail);

		}
		if (tabCenter.getSelectedIndex() == 1) {
			String code = (String) lookupTCode.getValue();
			String description = (String) txtTDescription.getText();
			String alias = (String) txtTAlias.getText();
			String clas = (String) cmbTClass.getSelectedItem();
			//BigDecimal amt = new BigDecimal (txtTAmt.getText());
			BigDecimal amt = (BigDecimal) txtTAmt.getValued();//**edited by jed 2019-05-06**//
			String acctid = (String) lookupTAcctID.getValue();
			String payee = (String) lookupTPayee.getValue();
			String payeetype = (String) lookupTPayeeType.getValue();
			String refund = (String) cmbRefundable.getSelectedItem();
			String remarks = (String) txtTRemarks.getText();
			String status = (String) cmbTStatus.getSelectedItem();
			
			/*added by jed 2021-11-04 : additional filter for inserting new particular*/
			String co_id = lookupCompany.getValue();
			//String proj_id = lookupProject.getValue();
			Integer index = Integer.parseInt(lookupProject.getValue());
			String type = "TCOST";
			
//			String sqlDetail = 
//					"INSERT INTO mf_transfer_cost_dl(\n" + 
//					"            tcost_id, tcost_type, tcostdtl_id, tcostdtl_type, tcostdtl_desc, \n" + 
//					"            tcostdtl_alias, tcostdtl_amt, refundable, acct_id, remarks, nsp_pctg, \n" + 
//					"            payee_id, \n" + 
//					"            payee_type_id, status_id, \n" + 
//					"            old_acct_id, multiple_entry, \n" + 
//					"            created_by, date_created, for_tcostcomp, co_id, proj_id)\n" + 
//					"    VALUES ('01', '01' , '"+code+"', (case when '"+clas+"' = 'Buyer Related' then 'B' else 'L' end), '"+description+"',\n" + 
//					"            '"+alias+"', "+amt+", (case when '"+refund+"' = 'Yes' then true else false end), '"+acctid+"', NULLIF('"+remarks+"','null'), null, \n" + 
//					"            '"+payee+"', \n" + 
//					"            '"+payeetype+"', (case when '"+status+"' = 'Active' then 'A' else 'I' end), \n" + 
//					"            null, false, \n" + 
//					"            '"+UserInfo.EmployeeCode+"', now(), false, '"+co_id+"', '"+proj_id+"')";
			
			String sqlDetail = 
					"select * from sp_save_transaction_particulars ('"+type+"', \n" +
							""+index+", \n" +
							"'"+code+"', \n" +
							"'"+clas+"', \n" +
							"'"+description+"', \n" +
							"'"+alias+"', \n" +
							""+amt+", \n" +
							"'"+refund+"', \n" +
							"'"+acctid+"', \n" +
							"'"+remarks+"', \n" +
							"'', \n" +
							"'"+payee+"', \n" +
							"'"+payeetype+"', \n" +
							"'"+status+"', \n" +
							"'"+UserInfo.EmployeeCode+"')";

			System.out.printf("SQL #1 - NewTCost: %s", sqlDetail);
			pgSelect db = new pgSelect();
			db.select(sqlDetail);
		}
		
		to_do = "New";
		pnlState(true,true);
		lookupCompany.setEnabled(true);
		lookupProject.setEnabled(true);
	}
	
	/*COMMENTED BY JED 2021-11-11 : TRANSFER UPDATE METHOD TO FUNCTION*/
//	private void updatePCost(pgUpdate db){
//		if (tabCenter.getSelectedIndex() == 0) {
//		String code = (String) lookupCode.getValue();
//		String description = (String) txtDescription.getText();
//		String alias = (String) txtAlias.getText();
//		String clas = (String) cmbClass.getSelectedItem();
//		//BigDecimal amt = new BigDecimal (txtAmt.getText());
//		BigDecimal amt = (BigDecimal) txtAmt.getValued();//**edited by jed 2019-05-06**//
//		String acctid = (String) lookupAcctID.getValue();
//		String refundacct = (String) lookupRefundAcct.getValue();
//		String payee = (String) lookupPayee.getValue();
//		String payeetype = (String) lookupPayeeType.getValue();
//		String refund = (String) cmbRefundable.getSelectedItem();
//		String remarks = (String) txtRemarks.getText();
//		String status = (String) cmbStatus.getSelectedItem();
//		
//		/*added by jed 2021-11-04 : additional filter for updating particular*/
//		String co_id = lookupCompany.getValue();
//		String proj_id = lookupProject.getValue(); 
//		
//		String sqlDetail = 
//				"UPDATE mf_processing_cost_dl\n" + 
//				"   SET pcostdtl_type = (case when '"+clas+"' = 'Buyer Related' then 'B' else 'L' end), pcostdtl_desc = '"+description+"', \n" + 
//				"       pcostdtl_alias = '"+alias+"', pcostdtl_amt = "+amt+",\n" +
//				"		refundable = (case when '"+refund+"' = 'Yes' then true else false end), acct_id = '"+acctid+"', remarks = NULLIF('"+remarks+"','null'), \n" + 
//				"       refund_acct_id = NULLIF('"+refundacct+"','null'), payee_id = '"+payee+"', payee_type_id = '"+payeetype+"', \n" + 
//				"       status_id = (case when '"+status+"' = 'Active' then 'A' else 'I' end), edited_by='"+UserInfo.EmployeeCode+"', \n" + 
//				"       date_edited = now(), \n" + 
//				"       co_id = '"+co_id+"', \n" + 
//				"       proj_id = '"+proj_id+"' \n" + 
//				" WHERE pcostdtl_id = '"+code+"' and rec_id = "+rec_id+"";
//			
//			
//		System.out.printf("SQL #1 - updateAcctgPeriod: %s", sqlDetail);
//		db.executeUpdate(sqlDetail, false);	
//		}
//		if (tabCenter.getSelectedIndex() == 1) {
//			String code = (String) lookupTCode.getValue();
//			String description = (String) txtTDescription.getText();
//			String alias = (String) txtTAlias.getText();
//			String clas = (String) cmbTClass.getSelectedItem();
//			//BigDecimal amt = new BigDecimal (txtTAmt.getText());
//			BigDecimal amt = (BigDecimal) txtTAmt.getValued(); //**edited by jed 2019-05-06**//
//			String acctid = (String) lookupTAcctID.getValue();
//			String payee = (String) lookupTPayee.getValue();
//			String payeetype = (String) lookupTPayeeType.getValue();
//			String refund = (String) cmbTRefundable.getSelectedItem();
//			String remarks = (String) txtTRemarks.getText();
//			String status = (String) cmbTStatus.getSelectedItem();
//			
//			/*added by jed 2021-11-04 : additional filter for updating particular*/
//			String co_id = lookupCompany.getValue();
//			String proj_id = lookupProject.getValue(); 
//			
//			String sqlDetail = 
//					"UPDATE mf_transfer_cost_dl\n" + 
//					"   SET tcostdtl_type = (case when '"+clas+"' = 'Buyer Related' then 'B' else 'L' end), tcostdtl_desc = '"+description+"', \n" + 
//					"       tcostdtl_alias = '"+alias+"', tcostdtl_amt = "+amt+",\n" +
//					"		refundable = (case when '"+refund+"' = 'Yes' then true else false end), acct_id = '"+acctid+"', remarks = NULLIF('"+remarks+"','null'), \n" + 
//					"       payee_id = '"+payee+"', payee_type_id = '"+payeetype+"', \n" + 
//					"       status_id = (case when '"+status+"' = 'Active' then 'A' else 'I' end), edited_by='"+UserInfo.EmployeeCode+"', \n" + 
//					"       date_edited = now(), \n" + 
//					"       co_id = '"+co_id+"', \n" + 
//					"       proj_id = '"+proj_id+"' \n" + 
//					" WHERE tcostdtl_id = '"+code+"' and rec_id = "+rec_id+"";
//				
//				
//			System.out.printf("SQL #1 - updateAcctgPeriod: %s", sqlDetail);
//			db.executeUpdate(sqlDetail, false);	
//		}
//		
//		to_do = "New";
//		pnlState(true,true);
//	}
	
	private void updatePCost(){
		if (tabCenter.getSelectedIndex() == 0) {
		String code = (String) lookupCode.getValue();
		String description = (String) txtDescription.getText();
		String alias = (String) txtAlias.getText();
		String clas = (String) cmbClass.getSelectedItem();
		//BigDecimal amt = new BigDecimal (txtAmt.getText());
		BigDecimal amt = (BigDecimal) txtAmt.getValued();//**edited by jed 2019-05-06**//
		String acctid = (String) lookupAcctID.getValue();
		String refundacct = (String) lookupRefundAcct.getValue();
		String payee = (String) lookupPayee.getValue();
		String payeetype = (String) lookupPayeeType.getValue();
		String refund = (String) cmbRefundable.getSelectedItem();
		String remarks = (String) txtRemarks.getText();
		String status = (String) cmbStatus.getSelectedItem();
		
		/*added by jed 2021-11-04 : additional filter for updating particular*/
		String co_id = lookupCompany.getValue();
		//String proj_id = lookupProject.getValue();
		Integer index = Integer.parseInt(lookupProject.getValue());
		String type = "PCOST";
		
		String sqlDetail = 
				"select * from sp_update_transaction_particulars ('"+type+"', \n" +
						""+index+", \n" +
						"'"+code+"', \n" +
						"'"+clas+"', \n" +
						"'"+description+"', \n" +
						"'"+alias+"', \n" +
						""+amt+", \n" +
						"'"+refund+"', \n" +
						"'"+acctid+"', \n" +
						"'"+remarks+"', \n" +
						"'"+refundacct+"', \n" +
						"'"+payee+"', \n" +
						"'"+payeetype+"', \n" +
						"'"+status+"', \n" +
						"'"+UserInfo.EmployeeCode+"')";
			
			
		System.out.printf("SQL #1 - updatePCOST: %s", sqlDetail);
		pgSelect db = new pgSelect();
		db.select(sqlDetail);

		}
		if (tabCenter.getSelectedIndex() == 1) {
			String code = (String) lookupTCode.getValue();
			String description = (String) txtTDescription.getText();
			String alias = (String) txtTAlias.getText();
			String clas = (String) cmbTClass.getSelectedItem();
			//BigDecimal amt = new BigDecimal (txtTAmt.getText());
			BigDecimal amt = (BigDecimal) txtTAmt.getValued(); //**edited by jed 2019-05-06**//
			String acctid = (String) lookupTAcctID.getValue();
			String payee = (String) lookupTPayee.getValue();
			String payeetype = (String) lookupTPayeeType.getValue();
			String refund = (String) cmbTRefundable.getSelectedItem();
			String remarks = (String) txtTRemarks.getText();
			String status = (String) cmbTStatus.getSelectedItem();
			
			/*added by jed 2021-11-04 : additional filter for updating particular*/
			String co_id = lookupCompany.getValue();
			//String proj_id = lookupProject.getValue();
			Integer index = Integer.parseInt(lookupProject.getValue());
			String type = "TCOST";
			
			String sqlDetail = 
					"select * from sp_update_transaction_particulars ('"+type+"', \n" +
							""+index+", \n" +
							"'"+code+"', \n" +
							"'"+clas+"', \n" +
							"'"+description+"', \n" +
							"'"+alias+"', \n" +
							""+amt+", \n" +
							"'"+refund+"', \n" +
							"'"+acctid+"', \n" +
							"'"+remarks+"', \n" +
							"'', \n" +
							"'"+payee+"', \n" +
							"'"+payeetype+"', \n" +
							"'"+status+"', \n" +
							"'"+UserInfo.EmployeeCode+"')";
				
				
			System.out.printf("SQL #1 - updateTCOST: %s", sqlDetail);
			pgSelect db = new pgSelect();
			db.select(sqlDetail);
		}
		
		to_do = "New";
		pnlState(true,true);
	}
	
	private static String company() {
		String SQL = "select co_id as \"ID\", trim(company_name) as \"Company Name\", trim(company_alias) as \"Alias\", '' as logo from mf_company";
		return SQL;
	}
	
	private static String sql_project(String co_id) {
		String SQL = "select proj_id as \"ID\", trim(proj_name) as \"Project Name\", trim(proj_alias) as \"Alias\"\n"
				+ "from mf_project where trim(co_id) = '" + co_id + "' order by proj_id\n";
		return SQL;
	}
	
	private static String sql_Location(String co_id) {
		
		String sql = "";
		
		if(co_id.equals("01") || co_id.equals("04") || co_id.equals("05")) {
			sql = "select ROW_NUMBER () OVER (ORDER BY province_id DESC)::int as \"No\", trim(province_name) as \"Location\" from mf_province where province_id = '064'";
		}
		
		if(co_id.equals("02")) {
			sql = "select ROW_NUMBER () OVER (ORDER BY province_id DESC)::int as \"No\", trim(province_name) as \"Location\" from mf_province where province_id in ('064', '024')";
		}
		
		return sql;
		
		
	}
	
	private static void pnlState (Boolean PCOST, Boolean TCOST) {/*ADDED BY JED 2021-11-04 : for controlling adding and editing particulars*/
		
		tabCenter.setEnabledAt(0, PCOST);
		tabCenter.setEnabledAt(1, TCOST);
		
	}
}


