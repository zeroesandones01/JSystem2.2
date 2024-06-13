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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXTextField;

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

public class CVprooflist extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "CV Prooflist";
	static Dimension frameSize = new Dimension(600, 550);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlNorth_comp;
	private JPanel pnlCVdtl_2a;
	private JPanel pnlCVdtl_2b;
	private JPanel pnlDRF;
	private JPanel pnlCreatedBy;
	private JPanel pnlCenterCV;
	private JPanel pnlCenterCreated;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private static JLabel lblDateFrom;
	private static JLabel lblDateTo;
	private static JLabel lblCVFrom;
	private static JLabel lblCVto;
	private static JLabel lblPayee;
	private static JLabel lblAvailer;
	private static JLabel lblCVstatus;
	private static JLabel lblStatus;
	private static JLabel lblPaymentType;	
	
	private static _JTagLabel tagPayee1;
	private static _JTagLabel tagPayee2;
	private static _JTagLabel tagStatus;
	private static _JTagLabel tagPreparedBy;
	private static _JTagLabel tagPmtType;

	private _JLookup lookupProject;
	public static _JLookup lookupCompany;
	private static _JLookup lookupPayee1;
	private static _JLookup lookupPayee2;
	private static _JLookup lookupStatus;
	private static _JLookup lookupPreparedby;
	private static _JLookup lookupPmtType;
	
	public static JTextField txtCompany;
	private static JXTextField txtCVfrom;		
	private static JXTextField txtCVto;	
	
	private static JButton btnPreview;
	private static JButton btnCancel;

	String company;
	public static String company_logo;		

	private static _JDateChooser dteDateFrom;
	private static _JDateChooser dateDateTo;	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	
	public String bank_acct_id 		= "";
	public String bank_acct_name	= "";
	public String payee_id 			= "";
	public String payee_name		= "";
	public String availer_id 		= "";
	public String availer_name 		= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String status_id			= "";
	public String proc_id			= "";
	public String status_name		= "";
	public String pmt_type_id		= "";
	public String pmt_type_name		= "";
	String co_id = "";

	private static JLabel lblBankAcct;

	private static _JLookup lookupBankAcct;

	private static _JTagLabel tagBankAcct;
	
	public CVprooflist() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CVprooflist(String title) {
		super(title);
		initGUI();
	}

	public CVprooflist(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
//	public void initGUI() {
//		this.setTitle(title);
//		this.setSize(frameSize);
//		this.setPreferredSize(new java.awt.Dimension(624, 473));
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
//				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 193));
//
//				{
//					pnlNorth_comp = new JPanel(new BorderLayout(5, 5));
//					pnlNorth.add(pnlNorth_comp, BorderLayout.NORTH);
//					pnlNorth_comp.setPreferredSize(new java.awt.Dimension(547, 63));
//					pnlNorth_comp.setBorder(components.JTBorderFactory.createTitleBorder("Company"));// XXX
//
//					{
//						pnlNorthWest = new JPanel(new GridLayout(1,1, 5, 5));
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
//										lookupBankAcct.setLookupSQL(getBankAcct());	
//										lookupPayee1.setLookupSQL(getPayee());	
//										lookupPayee2.setLookupSQL(getAvailer());	
//										lookupPreparedby.setLookupSQL(getPreparedBy());	
//										lookupPmtType.setLookupSQL(getPayment_type());	
//										lookupStatus.setLookupSQL(getStatus());	
//									}
//								}
//							});
//						}
//					}
//					pnlNorthEast = new JPanel(new GridLayout(1, 1, 5, 5));
//					pnlNorth_comp.add(pnlNorthEast, BorderLayout.CENTER);
//					pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
//					{
//						txtCompany = new JTextField();
//						pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
//						txtCompany.setEditable(false);
//					}
//				}
//				{
//					pnlCenterCV = new JPanel(new GridLayout(1,1,5, 5));
//					pnlNorth.add(pnlCenterCV, BorderLayout.CENTER);
//					pnlCenterCV.setPreferredSize(new java.awt.Dimension(499, 60));
//					pnlCenterCV.setBorder(components.JTBorderFactory.createTitleBorder("DRF No."));
//					
//					{
//						pnlDRF = new JPanel(new GridLayout(1, 2, 3, 3));
//						pnlCenterCV.add(pnlDRF, BorderLayout.WEST);					
//
//						{
//							lblCVFrom = new JLabel("CV From   ", JLabel.TRAILING);
//							pnlDRF.add(lblCVFrom, BorderLayout.CENTER);
//							lblCVFrom.setEnabled(false);							
//						}
//						{
//							txtCVfrom = new JXTextField("");
//							pnlDRF.add(txtCVfrom);
//							txtCVfrom.setEnabled(false);	
//							txtCVfrom.setEditable(true);
//							txtCVfrom.setBounds(120, 25, 300, 22);	
//							txtCVfrom.setHorizontalAlignment(JTextField.CENTER);
//							txtCVfrom.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}	
//						{	
//
//							lblCVto = new JLabel("CV To   ", JLabel.TRAILING);
//							pnlDRF.add(lblCVto);
//							lblCVto.setEnabled(false);	
//						}					
//						{
//							txtCVto = new JXTextField("");
//							pnlDRF.add(txtCVto);
//							txtCVto.setEnabled(false);	
//							txtCVto.setEditable(true);
//							txtCVto.setBounds(120, 25, 300, 22);	
//							txtCVto.setHorizontalAlignment(JTextField.CENTER);
//							txtCVto.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}
//					}
//				}
//				{
//					pnlCenterCreated = new JPanel(new GridLayout(1,1,5, 5));
//					pnlNorth.add(pnlCenterCreated, BorderLayout.SOUTH);
//					pnlCenterCreated.setPreferredSize(new java.awt.Dimension(547, 59));
//					pnlCenterCreated.setBorder(components.JTBorderFactory.createTitleBorder("Created Date"));
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
//							dteDateFrom.setDate(null);
//							dteDateFrom.setEnabled(false);
//							dteDateFrom.setDate(null);
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
//							dateDateTo.setBounds(485, 7, 125, 21);
//							dateDateTo.setDate(null);
//							dateDateTo.setEnabled(false);
//							dateDateTo.setDate(null);
//						}
//					}
//				}
//
//			}	
//			{
//				pnlCenter =  new JPanel(new BorderLayout(5, 5));
//				pnlMain.add(pnlCenter, BorderLayout.CENTER);
//				pnlCenter.setPreferredSize(new java.awt.Dimension(499, 50));
//				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
//				
//				{		
//					pnlCenter_a = new JPanel(new GridLayout(6, 1, 0, 5));
//					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
//					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 185));
//					
//					{
//						lblBankAcct = new JLabel("Bank Acct.", JLabel.TRAILING);
//						pnlCenter_a.add(lblBankAcct);
//						lblBankAcct.setEnabled(false);	
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
//						lblCVstatus = new JLabel("CV Status", JLabel.TRAILING);
//						pnlCenter_a.add(lblCVstatus);
//						lblCVstatus.setEnabled(false);	
//					}
//					{
//						lblPaymentType = new JLabel("Pmt. Type", JLabel.TRAILING);
//						pnlCenter_a.add(lblPaymentType);
//						lblPaymentType.setEnabled(false);	
//					}					
//
//					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
//					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
//					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 118));
//					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
//
//					pnlCVdtl_2a = new JPanel(new GridLayout(6, 1, 0, 5));
//					pnlCenter_2.add(pnlCVdtl_2a, BorderLayout.WEST);
//					pnlCVdtl_2a.setPreferredSize(new java.awt.Dimension(100, 185));
//					pnlCVdtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//
//					{
//						lookupBankAcct = new _JLookup(null, "Bank Acct.", 2, 2);
//						pnlCVdtl_2a.add(lookupBankAcct);
//						lookupBankAcct.setBounds(20, 27, 20, 25);
//						lookupBankAcct.setReturnColumn(1);
//						lookupBankAcct.setEnabled(false);
//						lookupBankAcct.setFilterName(true);
//						lookupBankAcct.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupBankAcct.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									bank_acct_id 	= (String) data[1];		
//									bank_acct_name 	= (String) data[2];			
//									tagBankAcct.setTag(bank_acct_name);
//								}
//							}
//						});	
//					}		
//					{
//						lookupPayee1 = new _JLookup(null, "Payee", 2, 2);
//						pnlCVdtl_2a.add(lookupPayee1);
//						lookupPayee1.setBounds(20, 27, 20, 25);
//						lookupPayee1.setReturnColumn(0);
//						lookupPayee1.setEnabled(false);	
//						lookupPayee1.setFilterName(true);
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
//						pnlCVdtl_2a.add(lookupPayee2);
//						lookupPayee2.setBounds(20, 27, 20, 25);
//						lookupPayee2.setReturnColumn(0);
//						lookupPayee2.setEnabled(false);	
//						lookupPayee2.setFilterName(true);
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
//						pnlCVdtl_2a.add(lookupPreparedby);
//						lookupPreparedby.setBounds(20, 27, 20, 25);
//						lookupPreparedby.setReturnColumn(0);
//						lookupPreparedby.setEnabled(false);	
//						lookupPreparedby.setFilterName(true);
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
//
//					{
//						lookupStatus = new _JLookup(null, "Prepared by", 2, 2);
//						pnlCVdtl_2a.add(lookupStatus);
//						lookupStatus.setBounds(20, 27, 20, 25);
//						lookupStatus.setReturnColumn(0);
//						lookupStatus.setEnabled(false);
//						//lookupStatus.setFilterName(true);
//						//lookupStatus.setEditable(false);
//						lookupStatus.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupStatus.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									status_id 		= (String) data[0];		
//									proc_id 		= (String) data[1];		
//									status_name 	= (String) data[2];			
//									tagStatus.setTag(status_name);	
//								}
//							}
//						});	
//					}	
//					{
//						lookupPmtType = new _JLookup(null, "Office Branch", 2, 2);
//						pnlCVdtl_2a.add(lookupPmtType);
//						lookupPmtType.setBounds(20, 27, 20, 25);
//						lookupPmtType.setReturnColumn(0);
//						lookupPmtType.setEnabled(false);
//						lookupPmtType.setFilterName(true);
//						//lookupPmtType.setEditable(false);
//						lookupPmtType.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupPmtType.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									pmt_type_id 	= (String) data[0];		
//									pmt_type_name 	= (String) data[1];			
//									tagPmtType.setTag(pmt_type_name);	
//								}
//							}
//						});	
//					}		
//
//					pnlCVdtl_2b = new JPanel(new GridLayout(6, 1, 0, 5));
//					pnlCenter_2.add(pnlCVdtl_2b, BorderLayout.CENTER);
//					pnlCVdtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
//					pnlCVdtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//
//					{
//						tagBankAcct = new _JTagLabel("[ ]");
//						pnlCVdtl_2b.add(tagBankAcct);
//						tagBankAcct.setBounds(209, 27, 700, 22);
//						tagBankAcct.setEnabled(false);	
//						tagBankAcct.setPreferredSize(new java.awt.Dimension(27, 33));
//					}	
//					{
//						tagPayee1 = new _JTagLabel("[ ]");
//						pnlCVdtl_2b.add(tagPayee1);
//						tagPayee1.setBounds(209, 27, 700, 22);
//						tagPayee1.setEnabled(false);	
//						tagPayee1.setPreferredSize(new java.awt.Dimension(27, 33));
//					}		
//					{
//						tagPayee2 = new _JTagLabel("[ ]");
//						pnlCVdtl_2b.add(tagPayee2);
//						tagPayee2.setBounds(209, 27, 700, 22);
//						tagPayee2.setEnabled(false);	
//						tagPayee2.setPreferredSize(new java.awt.Dimension(27, 33));
//					}						
//					{
//						tagPreparedBy = new _JTagLabel("[ ]");
//						pnlCVdtl_2b.add(tagPreparedBy);
//						tagPreparedBy.setBounds(209, 27, 700, 22);
//						tagPreparedBy.setEnabled(false);	
//						tagPreparedBy.setPreferredSize(new java.awt.Dimension(27, 33));
//					}	
//					{
//						tagStatus = new _JTagLabel("[ ]");
//						pnlCVdtl_2b.add(tagStatus);
//						tagStatus.setBounds(209, 27, 700, 22);
//						tagStatus.setEnabled(false);	
//						tagStatus.setPreferredSize(new java.awt.Dimension(27, 33));
//					}	
//					{
//						tagPmtType = new _JTagLabel("[ ]");
//						pnlCVdtl_2b.add(tagPmtType);
//						tagPmtType.setBounds(209, 27, 700, 22);
//						tagPmtType.setEnabled(false);	
//						tagPmtType.setPreferredSize(new java.awt.Dimension(27, 33));
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
//		this.setBounds(0, 0, 624, 473);  //174
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
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JPanel pnlCompany = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCompany, c);
						pnlCompany.setBorder(components.JTBorderFactory.createTitleBorder("Company" ));// XXX
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
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
											lookupBankAcct.setLookupSQL(getBankAcct());	
											lookupPayee1.setLookupSQL(getPayee());	
											lookupPayee2.setLookupSQL(getAvailer());	
											lookupPreparedby.setLookupSQL(getPreparedBy());	
											lookupPmtType.setLookupSQL(getPayment_type());	
											lookupStatus.setLookupSQL(getStatus());	
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
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 1;
						
						JPanel pnlDRF = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDRF, c);
						pnlDRF.setBorder(components.JTBorderFactory.createTitleBorder("DRF No."));
						{
							GridBagConstraints cons_drf = new GridBagConstraints();
							cons_drf.fill = GridBagConstraints.BOTH;
							cons_drf.insets = new Insets(5, 5, 5, 5);
							
							{
								cons_drf.weightx = 0.5;
								cons_drf.weighty = 1;
								
								cons_drf.gridx = 0;
								cons_drf.gridy = 0;
								
								lblCVFrom = new JLabel("CV From", JLabel.CENTER);
								pnlDRF.add(lblCVFrom, cons_drf);
								lblCVFrom.setEnabled(false);
								lblCVFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_drf.weightx = 1;
								cons_drf.weighty = 1;
								
								cons_drf.gridx = 1;
								cons_drf.gridy = 0;
								
								txtCVfrom = new JXTextField("");
								pnlDRF.add(txtCVfrom, cons_drf);
								txtCVfrom.setEnabled(false);	
								txtCVfrom.setEditable(true);
								txtCVfrom.setHorizontalAlignment(JTextField.CENTER);
								txtCVfrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_drf.weightx = 0.5;
								cons_drf.weighty = 1;
								
								cons_drf.gridx = 2;
								cons_drf.gridy = 0;
								
								lblCVto = new JLabel("CV To", JLabel.CENTER);
								pnlDRF.add(lblCVto, cons_drf);
								lblCVto.setEnabled(false);
								lblCVto.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_drf.weightx = 1;
								cons_drf.weighty = 1;
								
								cons_drf.gridx = 3;
								cons_drf.gridy = 0;
								
								txtCVto = new JXTextField("");
								pnlDRF.add(txtCVto, cons_drf);
								txtCVto.setEnabled(false);	
								txtCVto.setEditable(true);	
								txtCVto.setHorizontalAlignment(JTextField.CENTER);
								txtCVto.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 2;
						
						JPanel pnlCreatedby = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCreatedby, c);
						pnlCreatedby.setBorder(components.JTBorderFactory.createTitleBorder("Created Date"));
						{
							GridBagConstraints cons_date = new GridBagConstraints();
							cons_date.fill = GridBagConstraints.BOTH;
							cons_date.insets = new Insets(5, 5, 5, 5);
							
							{
								cons_date.weightx = 0.5;
								cons_date.weighty = 1;
								
								cons_date.gridx = 0;
								cons_date.gridy = 0;
								
								lblDateFrom = new JLabel("Date From", JLabel.CENTER);
								pnlCreatedby.add(lblDateFrom, cons_date);
								lblDateFrom.setEnabled(false);	
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_date.weightx = 1;
								cons_date.weighty = 1;
								
								cons_date.gridx = 1;
								cons_date.gridy = 0;
								
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCreatedby.add(dteDateFrom, cons_date);						
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(false);
								dteDateFrom.setDate(null);
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_date.weightx = 0.5;
								cons_date.weighty = 1;
								
								cons_date.gridx = 2;
								cons_date.gridy = 0;
								
								lblDateTo = new JLabel("Date To", JLabel.CENTER);
								pnlCreatedby.add(lblDateTo, cons_date);
								lblDateTo.setEnabled(false);
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_date.weightx = 1;
								cons_date.weighty = 1;
								
								cons_date.gridx = 3;
								cons_date.gridy = 0;
								
								dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCreatedby.add(dateDateTo, cons_date);
								dateDateTo.setDate(null);
								dateDateTo.setEnabled(false);
								dateDateTo.setDate(null);
								dateDateTo.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 1.5;

						c.gridx = 0; 
						c.gridy = 3;
						
						JPanel pnlSearch = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlSearch, c);
						pnlSearch.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
						{
							GridBagConstraints cons_search = new GridBagConstraints();
							cons_search.fill = GridBagConstraints.BOTH;
							cons_search.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1*/
							{
								cons_search.weightx = 0;
								cons_search.weighty = 1;
								
								cons_search.gridx = 0;
								cons_search.gridy = 0;
								
								lblBankAcct = new JLabel("Bank Acct.", JLabel.CENTER);
								pnlSearch.add(lblBankAcct, cons_search);
								lblBankAcct.setEnabled(false);
								lblBankAcct.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_search.weightx = 0.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 1;
								cons_search.gridy = 0;
								

								lookupBankAcct = new _JLookup(null, "Bank Acct.", 2, 2);
								pnlSearch.add(lookupBankAcct, cons_search);
								lookupBankAcct.setReturnColumn(1);
								lookupBankAcct.setEnabled(false);
								lookupBankAcct.setFilterName(true);
								lookupBankAcct.setFont(FncGlobal.sizeTextValue);
								lookupBankAcct.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											bank_acct_id 	= (String) data[1];		
											bank_acct_name 	= (String) data[2];			
											tagBankAcct.setTag(bank_acct_name);
										}
									}
								});	
								{
									cons_search.weightx = 1.5;
									cons_search.weighty = 1;
									
									cons_search.gridx = 2;
									cons_search.gridy = 0;
									
									tagBankAcct = new _JTagLabel("[ ]");
									pnlSearch.add(tagBankAcct, cons_search);
									tagBankAcct.setEnabled(false);	
									tagBankAcct.setFont(FncGlobal.sizeTextValue);
								}
							}
							
							/*LINE 2*/
							{
								cons_search.weightx = 0;
								cons_search.weighty = 1;
								
								cons_search.gridx = 0;
								cons_search.gridy = 1;
								
								lblPayee = new JLabel("Payee", JLabel.CENTER);
								pnlSearch.add(lblPayee, cons_search);
								lblPayee.setEnabled(false);	
								lblPayee.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_search.weightx = 0.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 1;
								cons_search.gridy = 1;
								
								lookupPayee1 = new _JLookup(null, "Payee", 2, 2);
								pnlSearch.add(lookupPayee1, cons_search);
								lookupPayee1.setReturnColumn(0);
								lookupPayee1.setEnabled(false);	
								lookupPayee1.setFilterName(true);
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
								cons_search.weightx = 1.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 2;
								cons_search.gridy = 1;
								
								tagPayee1 = new _JTagLabel("[ ]");
								pnlSearch.add(tagPayee1, cons_search);
								tagPayee1.setEnabled(false);	
								tagPayee1.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 3*/
							{
								cons_search.weightx = 0;
								cons_search.weighty = 1;
								
								cons_search.gridx = 0;
								cons_search.gridy = 2;
								
								lblAvailer= new JLabel("Availer", JLabel.CENTER);
								pnlSearch.add(lblAvailer, cons_search);
								lblAvailer.setEnabled(false);		
								lblAvailer.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_search.weightx = 0.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 1;
								cons_search.gridy = 2;
								
								lookupPayee2 = new _JLookup(null, "Availer", 2, 2);
								pnlSearch.add(lookupPayee2, cons_search);
								lookupPayee2.setReturnColumn(0);
								lookupPayee2.setEnabled(false);	
								lookupPayee2.setFilterName(true);
								//lookupPayee2.setEditable(false);
								lookupPayee2.setFont(FncGlobal.sizeTextValue);
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
								cons_search.weightx = 1.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 2;
								cons_search.gridy = 2;
								
								tagPayee2 = new _JTagLabel("[ ]");
								pnlSearch.add(tagPayee2, cons_search);
								tagPayee2.setEnabled(false);	
								tagPayee2.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 4*/
							{
								cons_search.weightx = 0;
								cons_search.weighty = 1;
								
								cons_search.gridx = 0;
								cons_search.gridy = 3;
								
								lblStatus = new JLabel("Prepared by", JLabel.CENTER);
								pnlSearch.add(lblStatus, cons_search);
								lblStatus.setEnabled(false);	
								lblStatus.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_search.weightx = 0.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 1;
								cons_search.gridy = 3;
								

								lookupPreparedby = new _JLookup(null, "DRF Status", 2, 2);
								pnlSearch.add(lookupPreparedby, cons_search);
								lookupPreparedby.setReturnColumn(0);
								lookupPreparedby.setEnabled(false);	
								lookupPreparedby.setFilterName(true);
								//lookupPreparedby.setEditable(false);
								lookupPreparedby.setFont(FncGlobal.sizeTextValue);
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
								cons_search.weightx = 1.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 2;
								cons_search.gridy = 3;
								
								tagPreparedBy = new _JTagLabel("[ ]");
								pnlSearch.add(tagPreparedBy, cons_search);
								tagPreparedBy.setEnabled(false);	
								tagPreparedBy.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 5*/
							{
								cons_search.weightx = 0;
								cons_search.weighty = 1;
								
								cons_search.gridx = 0;
								cons_search.gridy = 4;
								
								lblCVstatus = new JLabel("CV Status", JLabel.CENTER);
								pnlSearch.add(lblCVstatus, cons_search);
								lblCVstatus.setEnabled(false);	
								lblCVstatus.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_search.weightx = 0.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 1;
								cons_search.gridy = 4;
								

								lookupStatus = new _JLookup(null, "Prepared by", 2, 2);
								pnlSearch.add(lookupStatus, cons_search);
								lookupStatus.setReturnColumn(0);
								lookupStatus.setEnabled(false);
								//lookupStatus.setFilterName(true);
								//lookupStatus.setEditable(false);
								lookupStatus.setFont(FncGlobal.sizeTextValue);
								lookupStatus.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											status_id 		= (String) data[0];		
											proc_id 		= (String) data[1];		
											status_name 	= (String) data[2];			
											tagStatus.setTag(status_name);	
										}
									}
								});						
							}
							{
								cons_search.weightx = 1.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 2;
								cons_search.gridy = 4;
								
								tagStatus = new _JTagLabel("[ ]");
								pnlSearch.add(tagStatus, cons_search);
								tagStatus.setEnabled(false);	
								tagStatus.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 6*/
							{
								cons_search.weightx = 0;
								cons_search.weighty = 1;
								
								cons_search.gridx = 0;
								cons_search.gridy = 5;
								
								lblPaymentType = new JLabel("Pmt. Type", JLabel.CENTER);
								pnlSearch.add(lblPaymentType, cons_search);
								lblPaymentType.setEnabled(false);
								lblPaymentType.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_search.weightx = 0.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 1;
								cons_search.gridy = 5;
		
								lookupPmtType = new _JLookup(null, "Office Branch", 2, 2);
								pnlSearch.add(lookupPmtType, cons_search);
								lookupPmtType.setReturnColumn(0);
								lookupPmtType.setEnabled(false);
								lookupPmtType.setFilterName(true);
								lookupPmtType.setFont(FncGlobal.sizeTextValue);
								//lookupPmtType.setEditable(false);
								lookupPmtType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											pmt_type_id 	= (String) data[0];		
											pmt_type_name 	= (String) data[1];			
											tagPmtType.setTag(pmt_type_name);	
										}
									}
								});						
							}
							{
								cons_search.weightx = 1.5;
								cons_search.weighty = 1;
								
								cons_search.gridx = 2;
								cons_search.gridy = 5;
								
								tagPmtType = new _JTagLabel("[ ]");
								pnlSearch.add(tagPmtType, cons_search);
								tagPmtType.setEnabled(false);	
								tagPmtType.setFont(FncGlobal.sizeTextValue);
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
//		this.setBounds(0, 0, 624, 473);  //174
		
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

		//String status_id = 
		
		bank_acct_id 	= lookupBankAcct.getText().trim();
		payee_id 		= lookupPayee1.getText().trim();
		availer_id 		= lookupPayee2.getText().trim();
		prepared_by_id 	= lookupPreparedby.getText().trim();
		status_id 		= lookupStatus.getText().trim();
		
		Integer cv_fr = 0;
		Integer cv_to = 0;
		
		Date date_fr = null;
		Date date_to = null;
		
		try {date_fr = dateFormat.parse("2000-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		try {date_to = dateFormat.parse("2100-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		
		if (txtCVfrom.getText().equals("")) { } else { cv_fr = Integer.parseInt(txtCVfrom.getText()); }
		if (txtCVto.getText().equals("")) { } else { cv_to = Integer.parseInt(txtCVto.getText()); }
		if (dteDateFrom.getDate()==null) { } else { date_fr = dteDateFrom.getDate(); }
		if (dateDateTo.getDate()==null) { } else { date_to = dateDateTo.getDate(); }
		
		System.out.printf("CV Fr.: " + txtCVfrom.getText() + "\n");
		System.out.printf("CV To.: " + txtCVto.getText() + "\n");
			
		if(bank_acct_id.equals("")) { bank_acct_name = "All"; } else { }
		if(payee_id.equals("")) { payee_name = "All"; } else { }
		if(availer_id.equals("")) { availer_name = "All"; } else { }
		if(prepared_by_id.equals("")) { prepared_by_name = "All"; } else { }
		if(status_id.equals("")) { status_name = "All"; } else { }
		if(pmt_type_id.equals("")) { pmt_type_name = "All"; } else { }
		
		if(proc_id.equals("1")) { proc_id = "0"; } 
		else if(proc_id.equals("2")) { proc_id = "1"; } 
		else if(proc_id.equals("3")) { proc_id = "2"; } 
		else if(proc_id.equals("5")) { proc_id = "3"; } 
		else if(proc_id.equals("6")) { proc_id = "5"; } 
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "CV Prooflist";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id", co_id);
			mapParameters.put("company", company);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_from", date_fr);	
			mapParameters.put("date_to", date_to);		
			mapParameters.put("bank_acct_id", bank_acct_id);	
			mapParameters.put("bank_acct_name", bank_acct_name);	
			mapParameters.put("payee_id", payee_id);	
			mapParameters.put("payee_name", payee_name);	
			mapParameters.put("availer_id", availer_id);	
			mapParameters.put("availer_name", availer_name);
			mapParameters.put("prepared_id", prepared_by_id);	
			mapParameters.put("prepared_name", prepared_by_name);	
			mapParameters.put("status_id", status_id);
			mapParameters.put("proc_id", proc_id);
			mapParameters.put("status_name", status_name);
			mapParameters.put("pmt_type_name", pmt_type_name);	
			mapParameters.put("pmt_type_id", pmt_type_id);	
			mapParameters.put("cv_from", cv_fr);	
			mapParameters.put("cv_to", cv_to);

			FncReport.generateReport("/Reports/rptCV_prooflist.jasper", reportTitle, "", mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			refreshFields();
		}

	}

	public static void enabledFields(boolean x){
		
		lblCVFrom.setEnabled(x);		
		txtCVfrom.setEnabled(x);	
		lblCVto.setEnabled(x);	
		txtCVto.setEnabled(x);	
		lblDateFrom.setEnabled(x);			
		dteDateFrom.setEnabled(x);
		lblDateTo.setEnabled(x);	
		dateDateTo.setEnabled(x);
		
		lblBankAcct.setEnabled(x);	
		lblPayee.setEnabled(x);	
		lblAvailer.setEnabled(x);	
		lblStatus.setEnabled(x);	
		lblCVstatus.setEnabled(x);	
		lblPaymentType.setEnabled(x);
		
		lookupBankAcct.setEnabled(x);	
		lookupPayee1.setEnabled(x);	
		lookupPayee2.setEnabled(x);	
		lookupStatus.setEnabled(x);	
		lookupPreparedby.setEnabled(x);	
		lookupPmtType.setEnabled(x);	
		
		tagBankAcct.setEnabled(x);	
		tagPayee1.setEnabled(x);	
		tagPayee2.setEnabled(x);
		tagStatus.setEnabled(x);	
		tagPreparedBy.setEnabled(x);
		tagPmtType.setEnabled(x);		
		
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
		
		txtCVfrom.setEnabled(x);
		txtCVto.setEnabled(x);
		
		
	}
	
	public void refreshFields(){
		
		lookupBankAcct.setValue("");	
		lookupPayee1.setValue("");	
		lookupPayee2.setValue("");		
		lookupStatus.setValue("");	
		lookupPreparedby.setValue("");	
		lookupPmtType.setValue("");	
		
		tagBankAcct.setTag("");
		tagPayee1.setTag("");
		tagPayee2.setTag("");	
		tagStatus.setTag("");	
		tagPreparedBy.setTag("");
		tagPmtType.setTag("");		
		
		dteDateFrom.setDate(null);
		dateDateTo.setDate(null);
		
		bank_acct_id 	= "";
		bank_acct_name	= "";
		payee_id 		= "";
		payee_name		= "";
		availer_id 		= "";
		availer_name 	= "";
		prepared_by_id	= "";
		prepared_by_name= "";
		status_id		= "";
		proc_id			= "";
		status_name		= "";
		pmt_type_id		= "";
		pmt_type_name	= "";
		
		txtCVfrom.setText("");
		txtCVto.setText("");
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);

		enabledFields(true);
		lookupBankAcct.setLookupSQL(getBankAcct());	
		lookupPayee1.setLookupSQL(getPayee());	
		lookupPayee2.setLookupSQL(getAvailer());
		lookupPreparedby.setLookupSQL(getPreparedBy());	
		lookupPmtType.setLookupSQL(getPayment_type());	
		lookupStatus.setLookupSQL(getStatus());	
		
		lookupCompany.setValue(co_id);
	}
	
	
	//lookup	
	public String getPayee(){

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
	
	public String getAvailer(){

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
		
	public String getPreparedBy(){

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
		
	public String getStatus(){

		String sql = 
		"select\r\n" + 
		"\r\n" + 
		"distinct on (a.status_id, a.proc_id) a.status_id, a.proc_id, a.status_name from \r\n" + 
		"\r\n" + 
		"(\r\n" + 
		"\r\n" + 
		"select\r\n" + 
		"\r\n" + 
		
		"status_id," +
		"(case when status_id = 'D' then '0' else \r\n" + 
		"	case when proc_id = 0 then '1' else \r\n" + 
		"	case when proc_id = 1 then '2' else \r\n" + 
		"	case when proc_id = 2 then '3' else \r\n" + 
		"	case when proc_id = 3 then '5' else \r\n" + 
		"	case when proc_id = 5 then '6' else '' end end end end end end ) as proc_id,\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"(case when status_id = 'D' then 'Deleted' else \r\n" + 
		"	case when proc_id = 0 then 'For Checking' else \r\n" + 
		"	case when proc_id = 1 then 'For Approval' else \r\n" + 
		"	case when proc_id = 2 then 'For Check Signature' else \r\n" + 
		"	case when proc_id = 3 then 'For Release to Payee' else \r\n" + 
		"	case when proc_id = 5 then 'Paid Out' else '' end end end end end end ) as status_name \r\n" + 
		"\r\n" + 
		"from rf_cv_header \r\n" + 
		"\r\n" + 
		") a  order by a.status_id, a.proc_id ";		
		return sql;

	}	
	
	public static String getPayment_type(){//used
		return 
		"select 'B' as \"Code\", 'CHECK' as \"Payment Type\" union all "  +
		"select 'A' as \"Code\", 'CASH'  as \"Payment Type\"   "  ;

	}
	
	public static String getBankAcct(){//used

		String sql = 		

			"select   \r\n" + 
			"\r\n" + 
			"		a.rec_id,\r\n" + 
			"		a.bank_acct_id  ,  \r\n" + 
			"		b.bank_acct_no ,  \r\n" + 
			"		d.bank_alias  ,  \r\n" + 
			"		( case when b.fund_class_id = '01' then 'COLLECTING ACCOUNT' else   \r\n" + 
			"			case when b.fund_class_id = '02' then 'DISBURSING ACCOUNT' else  \r\n" + 
			"				case when b.fund_class_id = '03' then 'MONEY MARKET PLACEMENT ACCOUNT 'end end end) , \r\n" + 
			"		b.acct_id \r\n" + 
			"\r\n" + 
			"		from ( select * from rf_check_book where status_id = 'A' ) a\r\n" + 
			"		left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id  \r\n" + 
			"		left join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id  \r\n" + 
			"		left join mf_bank d on c.bank_id = d.bank_id   \r\n" +

		"		order by a.bank_acct_id \r\n" ;	

		System.out.printf("getBankAcct() sql :" + sql);
		return sql;

	}
	

}
