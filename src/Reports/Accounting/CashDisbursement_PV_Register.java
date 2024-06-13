package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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
import components._JTagLabel;

public class CashDisbursement_PV_Register extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Cash / Check Disbursement / Payable Voucher Register";
	static Dimension frameSize = new Dimension(700, 550);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlNorth_comp;
	private JPanel pnlDRFDtl_2a;
	private JPanel pnlDRFDtl_2b;
	private JPanel pnlCreatedBy;
	private JPanel pnlCenterCreated;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;
	private JPanel pnlCenter_b;

	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblDateRequest;
	private JLabel lblPayee;
	private JLabel lblAvailer;
	private JLabel lblPVStatus;
	private JLabel lblStatus;
	
	private _JTagLabel tagReqType;
	private _JTagLabel tagPayee1;
	private _JTagLabel tagPayee2;
	private _JTagLabel tagStatus;
	private _JTagLabel tagPreparedBy;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupRequestType;
	private _JLookup lookupPayee1;
	private _JLookup lookupPayee2;
	private _JLookup lookupStatus;
	private _JLookup lookupPreparedby;
	
	private JTextField txtCompany;	
	private JButton btnPreview;
	private JButton btnCancel;

	String company;
	private String company_logo;		

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;	
	private _JDateChooser dteCVDateFrom;
	private _JDateChooser dateCVDateTo;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	
	String req_type_id 		= "";
	String req_type_name	= "";
	String payee_id 		= "";
	String payee_name		= "";	
	String availer_id 		= "";
	String availer_name 	= "";
	String prepared_by_id	= "";
	String prepared_by_name	= "";
	String status_id		= "";
	String status_name		= "";
	String co_id 			= "";
	String proj_id 			= "";		
	String proj_name 		= "";	
	String sub_proj_id 		= "";		
	String sub_proj_name 	= "";

	private JLabel lblProject;
	private _JLookup lookupPhase;
	private JLabel lblPhase;
	private JTextField txtProject;
	private JTextField txtPhase;
	private JRadioButton rbtnCVDate;
	
	public CashDisbursement_PV_Register() {
		super(title, true, true, true, true);
		initGUI();
	}
	
	@Override
//	public void initGUI() {
//		this.setTitle(title);
//		this.setSize(frameSize);
//		this.setPreferredSize(new java.awt.Dimension(624, 418));
//		this.setLayout(new BorderLayout());
//		this.addAncestorListener(this);
//		{
//			pnlMain = new JPanel(new BorderLayout(5, 5));
//			getContentPane().add(pnlMain, BorderLayout.WEST);
//			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			pnlMain.setPreferredSize(new java.awt.Dimension(621, 462));
//			
//			{
//				pnlNorth = new JPanel(new BorderLayout(5, 5));
//				pnlMain.add(pnlNorth, BorderLayout.NORTH);
//				pnlNorth.setPreferredSize(new java.awt.Dimension(611, 180));
//
//				{
//					pnlNorth_comp = new JPanel(new BorderLayout(5, 5));
//					pnlNorth.add(pnlNorth_comp, BorderLayout.NORTH);
//					pnlNorth_comp.setPreferredSize(new java.awt.Dimension(611, 115));
//					pnlNorth_comp.setBorder(components.JTBorderFactory.createTitleBorder("Company"));// XXX
//
//					{
//						pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
//						pnlNorth_comp.add(pnlNorthWest, BorderLayout.WEST);
//						pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
//						{
//							lblCompany = new JLabel("Company", JLabel.TRAILING);
//							pnlNorthWest.add(lblCompany);
//						}
//						{
//							lookupCompany = new _JLookup(null, "Company");
//							pnlNorthWest.add(lookupCompany);
//							lookupCompany.setReturnColumn(0);
//							lookupCompany.setLookupSQL(SummaryofDeposits.company());
//							lookupCompany.setPreferredSize(new java.awt.Dimension(68, 36));
//							lookupCompany.addLookupListener(new LookupListener() {
//								public void lookupPerformed(LookupEvent event) {
//									Object[] data = ((_JLookup)event.getSource()).getDataSet();
//									if(data != null){
//
//										co_id = (String) data[0];
//										company = (String) data[1];
//										company_logo = (String) data[3];
//
//										String name = (String) data[1];						
//										txtCompany.setText(name);
//
//										enabledFields(true);
//										lookupRequestType.setLookupSQL(getRequestType());	
//										lookupPayee1.setLookupSQL(getPayee());	
//										lookupPayee2.setLookupSQL(getAvailer());	
//										lookupPreparedby.setLookupSQL(getPreparedBy());	
//										lookupStatus.setLookupSQL(getStatus());	
//										
//										lookupProject.setValue("");
//										txtProject.setText("");
//										lookupPhase.setValue("");
//										txtPhase.setText("");
//										proj_id = "";
//										proj_name = "";
//										sub_proj_id 		= "";		
//										sub_proj_name 	= "";
//										lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
//										lookupPhase.setLookupSQL(getPhase());		
//										
//									}
//								}
//							});
//						}
//						{
//							lblProject = new JLabel("Project", JLabel.TRAILING);
//							pnlNorthWest.add(lblProject);
//							lblProject.setEnabled(true);	
//						}
//						{
//							lookupProject = new _JLookup(null, "Project");
//							pnlNorthWest.add(lookupProject);
//							lookupProject.setReturnColumn(0);
//							lookupProject.setEnabled(true);
//							lookupProject.addLookupListener(new LookupListener() {
//								public void lookupPerformed(LookupEvent event) {
//									Object[] data = ((_JLookup)event.getSource()).getDataSet();
//									if(data != null){
//
//										proj_id = (String) data[0];		
//										proj_name = (String) data[1];	
//										txtProject.setText(proj_name);
//										btnPreview.setEnabled(true);
//
//										KEYBOARD_MANAGER.focusNextComponent();
//										btnCancel.setEnabled(true);
//										
//										lookupPhase.setValue("");
//										txtPhase.setText("");
//										sub_proj_id 	= "";		
//										sub_proj_name 	= "";
//										lookupPhase.setLookupSQL(getPhase());		
//									}
//								}
//							});
//						}
//						{
//							lblPhase = new JLabel("Phase", JLabel.TRAILING);
//							pnlNorthWest.add(lblPhase);
//							lblPhase.setEnabled(true);	
//						}
//						{
//							lookupPhase = new _JLookup(null, "Phase");
//							pnlNorthWest.add(lookupPhase);
//							lookupPhase.setReturnColumn(0);
//							lookupPhase.setEnabled(true);
//							lookupPhase.addLookupListener(new LookupListener() {
//								public void lookupPerformed(LookupEvent event) {
//									Object[] data = ((_JLookup)event.getSource()).getDataSet();
//									if(data != null){
//
//										sub_proj_id = (String) data[0];		
//										sub_proj_name = (String) data[1];	
//										txtPhase.setText(sub_proj_name);
//										btnPreview.setEnabled(true);
//
//										KEYBOARD_MANAGER.focusNextComponent();
//										btnCancel.setEnabled(true);
//									}
//								}
//							});
//						}						
//					}
//					pnlNorthEast = new JPanel(new GridLayout(3, 1, 5, 5));
//					pnlNorth_comp.add(pnlNorthEast, BorderLayout.CENTER);
//					pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
//					{
//						txtCompany = new JTextField();
//						pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
//						txtCompany.setEditable(false);
//					}
//					{
//						txtProject = new JTextField();
//						pnlNorthEast.add(txtProject, BorderLayout.CENTER);
//						txtProject.setEditable(false);
//						txtProject.setEnabled(true);
//					}
//					{
//						txtPhase = new JTextField();
//						pnlNorthEast.add(txtPhase, BorderLayout.CENTER);
//						txtPhase.setEditable(false);
//						txtPhase.setEnabled(true);
//					}
//				}				
//				{
//					pnlCenterCreated = new JPanel(new GridLayout(1,1,5, 5));
//					pnlNorth.add(pnlCenterCreated, BorderLayout.CENTER);
//					pnlCenterCreated.setPreferredSize(new java.awt.Dimension(547, 59));
//					pnlCenterCreated.setBorder(components.JTBorderFactory.createTitleBorder("PV Date"));
//					
//					{
//						pnlCreatedBy = new JPanel(new GridLayout(1, 2, 3, 3));
//						pnlCenterCreated.add(pnlCreatedBy, BorderLayout.WEST);					
//
//						{
//							lblDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
//							pnlCreatedBy.add(lblDateFrom, BorderLayout.CENTER);
//							lblDateFrom.setEnabled(false);							
//						}
//						{
//							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//							pnlCreatedBy.add(dteDateFrom, BorderLayout.CENTER);		
//							dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
//							dteDateFrom.setEnabled(false);
//						}	
//						{	
//
//							lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
//							pnlCreatedBy.add(lblDateTo);
//							lblDateTo.setEnabled(false);	
//						}					
//						{
//							dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//							pnlCreatedBy.add(dateDateTo, BorderLayout.EAST);
//							dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//							dateDateTo.setBounds(485, 7, 125, 21);
//							dateDateTo.setEnabled(false);
//						}
//					}
//				}
//
//			}	
//			{
//				pnlCenter =  new JPanel(new BorderLayout(5, 5));
//				pnlMain.add(pnlCenter, BorderLayout.CENTER);
//				pnlCenter.setPreferredSize(new java.awt.Dimension(499, 90));
//				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
//				
//				{		
//					pnlCenter_a = new JPanel(new GridLayout(5, 1, 0, 5));
//					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
//					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 210));
//					
//					{
//						lblDateRequest = new JLabel("Request Type", JLabel.TRAILING);
//						pnlCenter_a.add(lblDateRequest);
//						lblDateRequest.setEnabled(false);	
//					}	
//					{
//						lblPayee = new JLabel("Payee", JLabel.TRAILING);
//						pnlCenter_a.add(lblPayee);
//						lblPayee.setEnabled(false);	
//					}	
//					{
//						lblAvailer= new JLabel("Availer", JLabel.TRAILING);
//						pnlCenter_a.add(lblAvailer);
//						lblAvailer.setEnabled(false);	
//					}	
//					{
//						lblStatus = new JLabel("Prepared by", JLabel.TRAILING);
//						pnlCenter_a.add(lblStatus);
//						lblStatus.setEnabled(false);	
//					}
//					{
//						lblPVStatus = new JLabel("PV Status", JLabel.TRAILING);
//						pnlCenter_a.add(lblPVStatus);
//						lblPVStatus.setEnabled(false);	
//					}		
//
//					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
//					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
//					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 210));
//					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
//
//					pnlDRFDtl_2a = new JPanel(new GridLayout(5, 1, 0, 5));
//					pnlCenter_2.add(pnlDRFDtl_2a, BorderLayout.WEST);
//					pnlDRFDtl_2a.setPreferredSize(new java.awt.Dimension(100, 205));
//					pnlDRFDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//
//					{
//						lookupRequestType = new _JLookup(null, "Request Type", 2, 2);
//						pnlDRFDtl_2a.add(lookupRequestType);
//						lookupRequestType.setBounds(20, 27, 20, 25);
//						lookupRequestType.setReturnColumn(0);
//						lookupRequestType.setEnabled(false);
//						//lookupRequestType.setEditable(false);
//						lookupRequestType.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupRequestType.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									req_type_id 	= (String) data[0];		
//									req_type_name 	= (String) data[1];			
//									tagReqType.setTag(req_type_name);
//								}
//							}
//						});	
//					}		
//					{
//						lookupPayee1 = new _JLookup(null, "Payee", 2, 2);
//						pnlDRFDtl_2a.add(lookupPayee1);
//						lookupPayee1.setBounds(20, 27, 20, 25);
//						lookupPayee1.setReturnColumn(0);
//						lookupPayee1.setEnabled(false);	
//						//lookupPayee1.setEditable(false);
//						lookupPayee1.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupPayee1.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){	
//									payee_id 	= (String) data[0];		
//									payee_name 	= (String) data[1];			
//									tagPayee1.setTag(payee_name);
//								}
//							}
//						});	
//					}		
//					{
//						lookupPayee2 = new _JLookup(null, "Availer", 2, 2);
//						pnlDRFDtl_2a.add(lookupPayee2);
//						lookupPayee2.setBounds(20, 27, 20, 25);
//						lookupPayee2.setReturnColumn(0);
//						lookupPayee2.setEnabled(false);	
//						//lookupPayee2.setEditable(false);
//						lookupPayee2.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupPayee2.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){						
//									availer_id 		= (String) data[0];		
//									availer_name 	= (String) data[1];			
//									tagPayee2.setTag(availer_name);
//								}
//							}
//						});	
//					}		
//					
//					{
//						lookupPreparedby = new _JLookup(null, "DRF Status", 2, 2);
//						pnlDRFDtl_2a.add(lookupPreparedby);
//						lookupPreparedby.setBounds(20, 27, 20, 25);
//						lookupPreparedby.setReturnColumn(0);
//						lookupPreparedby.setEnabled(false);	
//						//lookupPreparedby.setEditable(false);
//						lookupPreparedby.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupPreparedby.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									prepared_by_id 		= (String) data[0];		
//									prepared_by_name 	= (String) data[1];			
//									tagPreparedBy.setTag(prepared_by_name);	
//								}
//							}
//						});	
//					}
//					{
//						lookupStatus = new _JLookup(null, "Prepared by", 2, 2);
//						pnlDRFDtl_2a.add(lookupStatus);
//						lookupStatus.setBounds(20, 27, 20, 25);
//						lookupStatus.setReturnColumn(0);
//						lookupStatus.setEnabled(false);	
//						//lookupStatus.setEditable(false);
//						lookupStatus.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupStatus.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									status_id 		= (String) data[0];		
//									status_name 	= (String) data[1];			
//									tagStatus.setTag(status_name);	
//								}
//							}
//						});	
//					}	
//
//					pnlDRFDtl_2b = new JPanel(new GridLayout(5, 1, 0, 5));
//					pnlCenter_2.add(pnlDRFDtl_2b, BorderLayout.CENTER);
//					pnlDRFDtl_2b.setPreferredSize(new java.awt.Dimension(140, 210));
//					pnlDRFDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//
//					{
//						tagReqType = new _JTagLabel("[ ]");
//						pnlDRFDtl_2b.add(tagReqType);
//						tagReqType.setBounds(209, 27, 700, 22);
//						tagReqType.setEnabled(false);	
//						tagReqType.setPreferredSize(new java.awt.Dimension(27, 33));
//					}	
//					{
//						tagPayee1 = new _JTagLabel("[ ]");
//						pnlDRFDtl_2b.add(tagPayee1);
//						tagPayee1.setBounds(209, 27, 700, 22);
//						tagPayee1.setEnabled(false);	
//						tagPayee1.setPreferredSize(new java.awt.Dimension(27, 33));
//					}		
//					{
//						tagPayee2 = new _JTagLabel("[ ]");
//						pnlDRFDtl_2b.add(tagPayee2);
//						tagPayee2.setBounds(209, 27, 700, 22);
//						tagPayee2.setEnabled(false);	
//						tagPayee2.setPreferredSize(new java.awt.Dimension(27, 33));
//					}						
//					{
//						tagPreparedBy = new _JTagLabel("[ ]");
//						pnlDRFDtl_2b.add(tagPreparedBy);
//						tagPreparedBy.setBounds(209, 27, 700, 22);
//						tagPreparedBy.setEnabled(false);	
//						tagPreparedBy.setPreferredSize(new java.awt.Dimension(27, 33));
//					}	
//					{
//						tagStatus = new _JTagLabel("[ ]");
//						pnlDRFDtl_2b.add(tagStatus);
//						tagStatus.setBounds(209, 27, 700, 22);
//						tagStatus.setEnabled(false);	
//						tagStatus.setPreferredSize(new java.awt.Dimension(27, 33));
//					}
//				}
//				{
//					pnlCenter_b = new JPanel(new GridLayout(1, 1, 0, 5));
//					pnlCenter.add(pnlCenter_b, BorderLayout.SOUTH);	
//					pnlCenter_b.setPreferredSize(new java.awt.Dimension(50, 25));
//					{
//						rbtnCVDate = new JRadioButton();
//						pnlCenter_b.add(rbtnCVDate);
//						rbtnCVDate.setText("by Paid-Out");
//						rbtnCVDate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
//						rbtnCVDate.setPreferredSize(new java.awt.Dimension(164, 23));
//						rbtnCVDate.setSelected(false);
//						rbtnCVDate.addActionListener(new ActionListener() {
//							public void actionPerformed(ActionEvent ae){									
//								if (rbtnCVDate.isSelected()==false)
//								{
//									dteCVDateFrom.setEnabled(false);
//									dateCVDateTo.setEnabled(false);	
//								} 
//								else 
//								{
//									dteCVDateFrom.setEnabled(true);
//									dateCVDateTo.setEnabled(true);
//								}				
//							}});
//					}
//					{
//						JLabel lblCVDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
//						pnlCenter_b.add(lblCVDateFrom);
//					}
//					{
//						dteCVDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//						pnlCenter_b.add(dteCVDateFrom);		
//						dteCVDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
//						dteCVDateFrom.setEnabled(false);
//					}	
//					{	
//
//						JLabel lblCVDateTo = new JLabel("Date To   ", JLabel.TRAILING);
//						pnlCenter_b.add(lblCVDateTo);
//					}					
//					{
//						dateCVDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//						pnlCenter_b.add(dateCVDateTo);
//						dateCVDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//						dateCVDateTo.setEnabled(false);
//					}
//				}
//			}
//
//			{				
//				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
//				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
//				pnlSouth.setPreferredSize(new Dimension(300, 30));
//				{
//					btnPreview = new JButton("Preview");
//					pnlSouth.add(btnPreview);
//					btnPreview.setAlignmentX(0.5f);
//					btnPreview.setEnabled(false);
//					btnPreview.setAlignmentY(0.5f);
//					btnPreview.setMaximumSize(new Dimension(100, 30));
//					btnPreview.setMnemonic(KeyEvent.VK_P);
//					btnPreview.addActionListener(this);
//				}
//				{
//					btnCancel = new JButton("Cancel");
//					pnlSouth.add(btnCancel);
//					btnCancel.setAlignmentX(0.5f);
//					btnCancel.setAlignmentY(0.5f);
//					btnCancel.setMaximumSize(new Dimension(100, 30));
//					btnCancel.setMnemonic(KeyEvent.VK_P);
//					btnCancel.setEnabled(false);
//					btnCancel.addActionListener(this);
//				}
//			}
//		}
//		
//		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
//				dteDateFrom, dateDateTo, btnPreview));
//		this.setBounds(0, 0, 624, 450);  //174
//		
//		//added 01/26/2016 - purpose - set CENQHOMES as default company
//		initialize_comp();
//	}
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					{
						c.weightx = 1;
						c.weighty = 0.5;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JPanel pnlCompany = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCompany, c);
						pnlCompany.setBorder(components.JTBorderFactory.createTitleBorder("Company" ));// XXX
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1 company*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 0; 
								
								lblCompany = new JLabel("Company", JLabel.CENTER);
								pnlCompany.add(lblCompany, cons_com);
								lblCompany.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 0; 
								
								lookupCompany = new _JLookup(null, "Company");
								pnlCompany.add(lookupCompany, cons_com);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SummaryofDeposits.company());
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								lookupCompany.setPreferredSize(new Dimension(60,0));
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											co_id = (String) data[0];
											company = (String) data[1];
											company_logo = (String) data[3];

											String name = (String) data[1];						
											txtCompany.setText(name);

											enabledFields(true);
											lookupRequestType.setLookupSQL(getRequestType());	
											lookupPayee1.setLookupSQL(getPayee());	
											lookupPayee2.setLookupSQL(getAvailer());	
											lookupPreparedby.setLookupSQL(getPreparedBy());	
											lookupStatus.setLookupSQL(getStatus());	
											
											lookupProject.setValue("");
											txtProject.setText("");
											lookupPhase.setValue("");
											txtPhase.setText("");
											proj_id = "";
											proj_name = "";
											sub_proj_id 		= "";		
											sub_proj_name 	= "";
											lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
											lookupPhase.setLookupSQL(getPhase());		
											
										}
									}
								});								
							}
							{
								cons_com.weightx = 1.5;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 0; 
								
								txtCompany = new JTextField();
								pnlCompany.add(txtCompany, cons_com);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 2 project*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 1; 
								
								lblProject = new JLabel("Project", JLabel.CENTER);
								pnlCompany.add(lblProject, cons_com);
								lblProject.setEnabled(true);
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 1; 
								
								lookupProject = new _JLookup(null, "Project");
								pnlCompany.add(lookupProject, cons_com);
								lookupProject.setReturnColumn(0);
								lookupProject.setFont(FncGlobal.sizeTextValue);
								lookupProject.setPreferredSize(new Dimension(60,0));
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
											txtPhase.setText("");
											sub_proj_id 	= "";		
											sub_proj_name 	= "";
											lookupPhase.setLookupSQL(getPhase());		
										}
									}
								});						
							}
							{
								cons_com.weightx = 1.5;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 1; 
								
								txtProject = new JTextField();
								pnlCompany.add(txtProject, cons_com);
								txtProject.setEditable(false);
								txtProject.setEnabled(true);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 3 phase*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 2;
								
								lblPhase = new JLabel("Phase", JLabel.CENTER);
								pnlCompany.add(lblPhase, cons_com);
								lblPhase.setEnabled(true);	
								lblPhase.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 2; 
								
								lookupPhase = new _JLookup(null, "Phase");
								pnlCompany.add(lookupPhase, cons_com);
								lookupPhase.setReturnColumn(0);
								lookupPhase.setFont(FncGlobal.sizeTextValue);
								lookupPhase.setPreferredSize(new Dimension(60,0));
								lookupPhase.setEnabled(true);
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
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 2;
								
								txtPhase = new JTextField();
								pnlCompany.add(txtPhase, cons_com);
								txtPhase.setEditable(false);
								txtPhase.setEnabled(true);
								txtPhase.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 1;
						
						JPanel pnlPVdate = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlPVdate, c);
						pnlPVdate.setBorder(components.JTBorderFactory.createTitleBorder("PV Date"));
						{
							GridBagConstraints cons_pvdate = new GridBagConstraints();
							cons_pvdate.fill = GridBagConstraints.BOTH;
							cons_pvdate.insets = new Insets(5, 5, 5, 5);
							cons_pvdate.ipady = 3;
							
							{
								cons_pvdate.weightx = 0.5;
								cons_pvdate.weighty = 1;

								cons_pvdate.gridx = 0; 
								cons_pvdate.gridy = 0; 
								
								lblDateFrom = new JLabel("Date From", JLabel.CENTER);
								pnlPVdate.add(lblDateFrom, cons_pvdate);
								lblDateFrom.setEnabled(false);
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_pvdate.weightx = 1;
								cons_pvdate.weighty = 1;

								cons_pvdate.gridx = 1; 
								cons_pvdate.gridy = 0;
								
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlPVdate.add(dteDateFrom, cons_pvdate);		
								dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
								dteDateFrom.setEnabled(false);
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_pvdate.weightx = 0.5;
								cons_pvdate.weighty = 1;

								cons_pvdate.gridx = 2; 
								cons_pvdate.gridy = 0;
								
								lblDateTo = new JLabel("Date To", JLabel.CENTER);
								pnlPVdate.add(lblDateTo, cons_pvdate);
								lblDateTo.setEnabled(false);
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_pvdate.weightx = 1;
								cons_pvdate.weighty = 1;

								cons_pvdate.gridx = 3; 
								cons_pvdate.gridy = 0;
								
								dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlPVdate.add(dateDateTo, cons_pvdate);
								dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dateDateTo.setEnabled(false);
								dateDateTo.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 1.5;

						c.gridx = 0; 
						c.gridy = 2;
						
						JPanel pnlSearch = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlSearch, c);
						pnlSearch.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
						{
							GridBagConstraints cons_searchopt = new GridBagConstraints();
							cons_searchopt.fill = GridBagConstraints.BOTH;
							cons_searchopt.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1 request type*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 0;
								
								lblDateRequest = new JLabel("Request Type", JLabel.CENTER);
								pnlSearch.add(lblDateRequest, cons_searchopt);
								lblDateRequest.setEnabled(false);
								lblDateRequest.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 0;
								
								lookupRequestType = new _JLookup(null, "Request Type", 2, 2);
								pnlSearch.add(lookupRequestType, cons_searchopt);
								lookupRequestType.setReturnColumn(0);
								lookupRequestType.setEnabled(false);
								lookupRequestType.setFont(FncGlobal.sizeTextValue);
								//lookupRequestType.setEditable(false);
								lookupRequestType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											req_type_id 	= (String) data[0];		
											req_type_name 	= (String) data[1];			
											tagReqType.setTag(req_type_name);
										}
									}
								});						
							}
							{
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 0;
								
								tagReqType = new _JTagLabel("[ ]");
								pnlSearch.add(tagReqType, cons_searchopt);
								tagReqType.setEnabled(false);
								tagReqType.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 2 payee*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 1;
								
								lblPayee = new JLabel("Payee", JLabel.CENTER);
								pnlSearch.add(lblPayee, cons_searchopt);
								lblPayee.setEnabled(false);
								lblPayee.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 1;
								
								lookupPayee1 = new _JLookup(null, "Payee", 2, 2);
								pnlSearch.add(lookupPayee1, cons_searchopt);
								lookupPayee1.setReturnColumn(0);
								lookupPayee1.setEnabled(false);	
								lookupPayee1.setFont(FncGlobal.sizeTextValue);
								//lookupPayee1.setEditable(false);
								lookupPayee1.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){	
											payee_id 	= (String) data[0];		
											payee_name 	= (String) data[1];			
											tagPayee1.setTag(payee_name);
										}
									}
								});							
							}
							{
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 1;
								
								tagPayee1 = new _JTagLabel("[ ]");
								pnlSearch.add(tagPayee1, cons_searchopt);
								tagPayee1.setEnabled(false);	
								tagPayee1.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 3 availer*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 2;
								
								lblAvailer= new JLabel("Availer", JLabel.CENTER);
								pnlSearch.add(lblAvailer, cons_searchopt);
								lblAvailer.setEnabled(false);
								lblAvailer.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 2;
								
								lookupPayee2 = new _JLookup(null, "Availer", 2, 2);
								pnlSearch.add(lookupPayee2, cons_searchopt);
								lookupPayee2.setReturnColumn(0);
								lookupPayee2.setEnabled(false);	
								lookupPayee2.setFont(FncGlobal.sizeTextValue);
								//lookupPayee2.setEditable(false);
								lookupPayee2.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){						
											availer_id 		= (String) data[0];		
											availer_name 	= (String) data[1];			
											tagPayee2.setTag(availer_name);
										}
									}
								});								
							}
							{
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 2;
								
								tagPayee2 = new _JTagLabel("[ ]");
								pnlSearch.add(tagPayee2, cons_searchopt);
								tagPayee2.setEnabled(false);
								tagPayee2.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 4 prepared by*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 3;
								
								lblStatus = new JLabel("Prepared by", JLabel.CENTER);
								pnlSearch.add(lblStatus, cons_searchopt);
								lblStatus.setEnabled(false);
								lblStatus.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 3;
								
								lookupPreparedby = new _JLookup(null, "DRF Status", 2, 2);
								pnlSearch.add(lookupPreparedby, cons_searchopt);
								lookupPreparedby.setReturnColumn(0);
								lookupPreparedby.setEnabled(false);	
								lookupPreparedby.setFont(FncGlobal.sizeTextValue);
								//lookupPreparedby.setEditable(false);
								lookupPreparedby.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											prepared_by_id 		= (String) data[0];		
											prepared_by_name 	= (String) data[1];			
											tagPreparedBy.setTag(prepared_by_name);	
										}
									}
								});								
							}
							{
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 3;
								
								tagPreparedBy = new _JTagLabel("[ ]");
								pnlSearch.add(tagPreparedBy, cons_searchopt);
								tagPreparedBy.setEnabled(false);	
								tagPreparedBy.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 5 pv status*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 4;
								
								lblPVStatus = new JLabel("PV Status", JLabel.CENTER);
								pnlSearch.add(lblPVStatus, cons_searchopt);
								lblPVStatus.setEnabled(false);
								lblPVStatus.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 4;
								

								lookupStatus = new _JLookup(null, "Prepared by", 2, 2);
								pnlSearch.add(lookupStatus, cons_searchopt);
								lookupStatus.setReturnColumn(0);
								lookupStatus.setEnabled(false);	
								lookupStatus.setFont(FncGlobal.sizeTextValue);
								//lookupStatus.setEditable(false);
								lookupStatus.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											status_id 		= (String) data[0];		
											status_name 	= (String) data[1];			
											tagStatus.setTag(status_name);	
										}
									}
								});							
							}
							{
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 4;
								
								tagStatus = new _JTagLabel("[ ]");
								pnlSearch.add(tagStatus, cons_searchopt);
								tagStatus.setEnabled(false);	
								tagStatus.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 3;
						
						JPanel pnlPaidOut = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlPaidOut, c);
						pnlPaidOut.setBorder(components.JTBorderFactory.createTitleBorder("By Paid Out"));
						{
							GridBagConstraints cons_paidout = new GridBagConstraints();
							cons_paidout.fill = GridBagConstraints.BOTH;
							cons_paidout.insets = new Insets(5, 5, 5, 5);
							{
								cons_paidout.weightx = 0.75;
								cons_paidout.weighty = 1;

								cons_paidout.gridx = 0; 
								cons_paidout.gridy = 0;
								
								rbtnCVDate = new JRadioButton();
								pnlPaidOut.add(rbtnCVDate, cons_paidout);
								rbtnCVDate.setText("by Paid-Out");
								rbtnCVDate.setFont(FncGlobal.sizeTextValue);
								rbtnCVDate.setSelected(false);
								rbtnCVDate.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){									
										if (rbtnCVDate.isSelected()==false)
										{
											dteCVDateFrom.setEnabled(false);
											dateCVDateTo.setEnabled(false);	
										} 
										else 
										{
											dteCVDateFrom.setEnabled(true);
											dateCVDateTo.setEnabled(true);
										}				
									}});						
							}
							{
								cons_paidout.weightx = 0;
								cons_paidout.weighty = 1;

								cons_paidout.gridx = 1; 
								cons_paidout.gridy = 0;
								
								JLabel lblCVDateFrom = new JLabel("Date From", JLabel.CENTER);
								pnlPaidOut.add(lblCVDateFrom, cons_paidout);
								lblCVDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_paidout.weightx = 1;
								cons_paidout.weighty = 1;

								cons_paidout.gridx = 2; 
								cons_paidout.gridy = 0;
								
								dteCVDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlPaidOut.add(dteCVDateFrom, cons_paidout);		
								dteCVDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
								dteCVDateFrom.setEnabled(false);
								dteCVDateFrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_paidout.weightx = 0;
								cons_paidout.weighty = 1;

								cons_paidout.gridx = 3; 
								cons_paidout.gridy = 0;
								
								JLabel lblCVDateTo = new JLabel("Date To", JLabel.CENTER);
								pnlPaidOut.add(lblCVDateTo, cons_paidout);
								lblCVDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_paidout.weightx = 1;
								cons_paidout.weighty = 1;

								cons_paidout.gridx = 4; 
								cons_paidout.gridy = 0;
								
								dateCVDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlPaidOut.add(dateCVDateTo, cons_paidout);
								dateCVDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dateCVDateTo.setEnabled(false);
								dateCVDateTo.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
				}
			}
			{
				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setEnabled(false);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
					btnCancel.setFont(FncGlobal.sizeControls);
				}
			}
		}
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				dteDateFrom, dateDateTo, btnPreview));
		//this.setBounds(0, 0, 624, 450);  //174

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}
		
	@Override
	public void ancestorAdded(AncestorEvent event) {
		
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		req_type_id 	= lookupRequestType.getText().trim();
		payee_id 		= lookupPayee1.getText().trim();
		availer_id 		= lookupPayee2.getText().trim();
		prepared_by_id 	= lookupPreparedby.getText().trim();
		status_id 		= lookupStatus.getText().trim();
		
		Integer pv_fr = 0;
		Integer pv_to = 0;
		
		Date date_fr = null;
		Date date_to = null;
		Date date_fr_cv = null;
		Date date_to_cv = null;
		
		try {date_fr = dateFormat.parse("2000-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		try {date_to = dateFormat.parse("2100-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}	
		
		try {date_fr_cv = dateFormat.parse("2000-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		try {date_to_cv = dateFormat.parse("2100-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}	
		
		if (dteDateFrom.getDate()==null) { } else { date_fr = dteDateFrom.getDate(); }
		if (dateDateTo.getDate()==null) { } else { date_to = dateDateTo.getDate(); }
		
		if (dteCVDateFrom.getDate()==null) { } else { date_fr_cv = dteCVDateFrom.getDate(); }
		if (dateCVDateTo.getDate()==null) { } else { date_to_cv = dateCVDateTo.getDate(); }
			
		if(req_type_id.equals("")) { req_type_name = "All"; } else { }
		if(payee_id.equals("")) { payee_name = "All"; } else { }
		if(availer_id.equals("")) { availer_name = "All"; } else { }
		if(prepared_by_id.equals("")) { prepared_by_name = "All"; } else { }
		if(status_id.equals("")) { status_name = "All"; } else { }
		if(proj_id.equals("")) { proj_name = "All"; } else { }
		if(sub_proj_id.equals("")) { sub_proj_name = "All"; } else { }
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "Cash Disbursement / PV Register";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_from", date_fr);	
			mapParameters.put("date_to", date_to);		
			mapParameters.put("req_type_id", req_type_id);	
			mapParameters.put("req_type_name", req_type_name);	
			mapParameters.put("payee_id", payee_id);	
			mapParameters.put("payee_name", payee_name);	
			mapParameters.put("availer_id", availer_id);	
			mapParameters.put("availer_name", availer_name);
			mapParameters.put("prepared_id", prepared_by_id);	
			mapParameters.put("prepared_name", prepared_by_name);	
			mapParameters.put("status_id", status_id);	
			mapParameters.put("status_name", status_name);
			mapParameters.put("pv_from", pv_fr);	
			mapParameters.put("pv_to", pv_to);
			mapParameters.put("co_id", co_id);
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("phase_no", lookupPhase.getValue());
			mapParameters.put("proj_name", proj_name);
			mapParameters.put("phase_name", sub_proj_name);
			mapParameters.put("cv_date_from", date_fr_cv);	
			mapParameters.put("cv_date_to", date_to_cv);
			
			if (rbtnCVDate.isSelected() == false)
			{
				FncReport.generateReport("/Reports/rptCashDisb_PV_Register.jasper", reportTitle, "", mapParameters);	
			} 
			else 
			{
				FncReport.generateReport("/Reports/rptCashDisb_PV_Register_CV.jasper", reportTitle, "", mapParameters);
			}
			
		}
		if(e.getActionCommand().equals("Cancel")){	
			refreshFields();
		}

	}

	private void enabledFields(boolean x){
		
		lblDateFrom.setEnabled(x);			
		dteDateFrom.setEnabled(x);
		lblDateTo.setEnabled(x);	
		dateDateTo.setEnabled(x);
		
		lblDateRequest.setEnabled(x);	
		lblPayee.setEnabled(x);	
		lblAvailer.setEnabled(x);	
		lblStatus.setEnabled(x);	
		lblPVStatus.setEnabled(x);	
		
		lookupRequestType.setEnabled(x);	
		lookupPayee1.setEnabled(x);	
		lookupPayee2.setEnabled(x);	
		lookupStatus.setEnabled(x);	
		lookupPreparedby.setEnabled(x);
		
		tagReqType.setEnabled(x);	
		tagPayee1.setEnabled(x);	
		tagPayee2.setEnabled(x);
		tagStatus.setEnabled(x);	
		tagPreparedBy.setEnabled(x);
		
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);		
	}
	
	private void refreshFields(){
		
		lookupRequestType.setValue("");	
		lookupPayee1.setValue("");	
		lookupPayee2.setValue("");		
		lookupStatus.setValue("");	
		lookupPreparedby.setValue("");	
		tagReqType.setTag("");
		tagPayee1.setTag("");
		tagPayee2.setTag("");	
		tagStatus.setTag("");	
		tagPreparedBy.setTag("");
		lookupProject.setValue("");
		txtProject.setText("");
		lookupPhase.setValue("");
		txtPhase.setText("");
		
		dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		dteCVDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
		dateCVDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		req_type_id 	= "";
		req_type_name	= "";
		payee_id 		= "";
		payee_name		= "";
		availer_id 		= "";
		availer_name 	= "";
		prepared_by_id	= "";
		prepared_by_name= "";
		status_id		= "";
		status_name		= "";
		proj_id 		= "";
		proj_name 		= "";
		sub_proj_id 	= "";		
		sub_proj_name 	= "";
		
		rbtnCVDate.setSelected(false);
		dteCVDateFrom.setEnabled(false);
		dateCVDateTo.setEnabled(false);
	}
	
	private void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);

		enabledFields(true);
		lookupRequestType.setLookupSQL(getRequestType());	
		lookupPayee1.setLookupSQL(getPayee());	
		lookupPayee2.setLookupSQL(getAvailer());
		lookupPreparedby.setLookupSQL(getPreparedBy());	
		lookupStatus.setLookupSQL(getStatus());	
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getPhase());		
		lookupCompany.setValue(co_id);
		
		refreshFields();
	}
	
	
	//lookup
	private String getRequestType(){

		String sql = 
		"select " +
		"rplf_type_id as \"Type ID\",  \n" +
		"trim(rplf_type_desc) as \"Description\"  \n" +
		"from mf_rplf_type where status_id = 'A' " +
		"order by rplf_type_id    ";		
		return sql;

	}	
	
	private String getPayee(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.entity_id1,\r\n" + 
		"upper(trim(b.entity_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_request_header a \r\n" + 
		"left join rf_entity b on a.entity_id1 = b.entity_id ) a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
	
	private String getAvailer(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.entity_id,\r\n" + 
		"upper(trim(b.entity_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_request_detail a \r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id ) a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
		
	private String getPreparedBy(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.created_by,\r\n" + 
		"upper(trim(c.last_name))||', '||upper(trim(c.first_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_pv_header a \r\n" + 
		"left join em_employee b on a.created_by = b.emp_code\r\n" + 
		"left join rf_entity c on b.entity_id = c.entity_id    \n" +
		"\r\n" + 
		") a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
		
	private String getStatus(){

		String sql = 
		"select \n" +

		"a.status_id, \n" +
		"b.status_desc \n" +

        "from (select distinct on(status_id) status_id from rf_pv_header) a  \n" +
        "left join mf_record_status b on a.status_id = b.status_id \n" ;	
        		
		return sql;

	}	

	private String getPhase(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(a.sub_proj_id) as \"Phase ID\",\r\n" + 
		"trim(a.phase) as \"Phase\", \r\n" +
		"trim(b.proj_alias) as \"Project\"," +
		"trim(c.company_alias) as \"Company\" " + 
		"\r\n" + 
		"from mf_sub_project a \r\n" +
		"left join mf_project b on a.proj_id = b.proj_id " +
		"left join mf_company c on b.co_id = c.co_id " + 
		"\r\n" + 
		"where (case when a.proj_id = '' then true else a.proj_id = '"+proj_id+"' end) AND a.status_id = 'A' " +//ADDED STATUS ID BY LESTER DCRF 2718
		"order by a.sub_proj_id "  ;

	}
}
