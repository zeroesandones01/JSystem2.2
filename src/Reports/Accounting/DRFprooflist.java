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

public class DRFprooflist extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "DRF Prooflist";
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
	private JPanel pnlDRF;
	private JPanel pnlCreatedBy;
	private JPanel pnlCenterDRF;
	private JPanel pnlCenterCreated;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private static JLabel lblDateFrom;
	private static JLabel lblDateTo;
	private static JLabel lblDateRequest;
	private static JLabel lblDRFFrom;
	private static JLabel lblDRFTo;
	private static JLabel lblPayee;
	private static JLabel lblAvailer;
	private static JLabel lblAccount;
	private static JLabel lblPreparedby;
	private static JLabel lblStatus;
	private static JLabel lblBranch;	
	
	private static _JTagLabel tagReqType;
	private static _JTagLabel tagPayee1;
	private static _JTagLabel tagPayee2;
	private static _JTagLabel tagAccount;
	private static _JTagLabel tagStatus;
	private static _JTagLabel tagPreparedBy;
	private static _JTagLabel tagBranch;

	private _JLookup lookupProject;
	public static _JLookup lookupCompany;
	private static _JLookup lookupRequestType;
	private static _JLookup lookupPayee1;
	private static _JLookup lookupPayee2;	
	private static _JLookup lookupAccount;
	private static _JLookup lookupStatus;
	private static _JLookup lookupPreparedby;
	private static _JLookup lookupBranch;
	
	public static JTextField txtCompany;
	private static JXTextField txtDRFfrom;		
	private static JXTextField txtDRFto;	
	
	private static JButton btnPreview;
	private static JButton btnCancel;

	String company;
	public static String company_logo;		

	private static _JDateChooser dteDateFrom;
	private static _JDateChooser dateDateTo;	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	
	public String req_type_id 		= "";
	public String req_type_name		= "";
	public String payee_id 			= "";
	public String payee_name		= "";
	public String availer_id 		= "";
	public String availer_name 		= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String branch_id			= "";
	public String branch_name		= "";
	public String acct_id			= "";
	public String acct_name			= "";
	public String status_id			= "";
	public String status_name		= "";
	String co_id = "";
	
	public DRFprooflist() {
		super(title, true, true, true, true);
		initGUI();
	}

	public DRFprooflist(String title) {
		super(title);
		initGUI();
	}

	public DRFprooflist(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
//	public void initGUI() {
//		this.setTitle(title);
//		this.setSize(frameSize);
//		this.setPreferredSize(new java.awt.Dimension(624, 506));
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
//										lookupRequestType.setLookupSQL(getRequestType());	
//										lookupPayee1.setLookupSQL(getPayee());	
//										lookupPayee2.setLookupSQL(getAvailer());	
//										lookupAccount.setLookupSQL(getAccount());	
//										lookupPreparedby.setLookupSQL(getPreparedBy());	
//										lookupBranch.setLookupSQL(getBranch());	
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
//					pnlCenterDRF = new JPanel(new GridLayout(1,1,5, 5));
//					pnlNorth.add(pnlCenterDRF, BorderLayout.CENTER);
//					pnlCenterDRF.setPreferredSize(new java.awt.Dimension(499, 60));
//					pnlCenterDRF.setBorder(components.JTBorderFactory.createTitleBorder("DRF No."));
//					
//					{
//						pnlDRF = new JPanel(new GridLayout(1, 2, 3, 3));
//						pnlCenterDRF.add(pnlDRF, BorderLayout.WEST);					
//
//						{
//							lblDRFFrom = new JLabel("DRF From   ", JLabel.TRAILING);
//							pnlDRF.add(lblDRFFrom, BorderLayout.CENTER);
//							lblDRFFrom.setEnabled(false);							
//						}
//						{
//							txtDRFfrom = new JXTextField("");
//							pnlDRF.add(txtDRFfrom);
//							txtDRFfrom.setEnabled(false);	
//							txtDRFfrom.setEditable(true);
//							txtDRFfrom.setBounds(120, 25, 300, 22);	
//							txtDRFfrom.setHorizontalAlignment(JTextField.CENTER);
//							txtDRFfrom.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}	
//						{	
//
//							lblDRFTo = new JLabel("DRF To   ", JLabel.TRAILING);
//							pnlDRF.add(lblDRFTo);
//							lblDRFTo.setEnabled(false);	
//						}					
//						{
//							txtDRFto = new JXTextField("");
//							pnlDRF.add(txtDRFto);
//							txtDRFto.setEnabled(false);	
//							txtDRFto.setEditable(true);
//							txtDRFto.setBounds(120, 25, 300, 22);	
//							txtDRFto.setHorizontalAlignment(JTextField.CENTER);
//							txtDRFto.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
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
//					pnlCenter_a = new JPanel(new GridLayout(7, 1, 0, 5));
//					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
//					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 185));
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
//						lblAccount = new JLabel("Account", JLabel.TRAILING);
//						pnlCenter_a.add(lblAccount);
//						lblAccount.setEnabled(false);	
//					}
//					{
//						lblStatus = new JLabel("Prepared by", JLabel.TRAILING);
//						pnlCenter_a.add(lblStatus);
//						lblStatus.setEnabled(false);	
//					}
//					{
//						lblPreparedby = new JLabel("DRF Status", JLabel.TRAILING);
//						pnlCenter_a.add(lblPreparedby);
//						lblPreparedby.setEnabled(false);	
//					}
//					{
//						lblBranch = new JLabel("Office Branch", JLabel.TRAILING);
//						pnlCenter_a.add(lblBranch);
//						lblBranch.setEnabled(false);	
//					}					
//
//					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
//					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
//					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 118));
//					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
//
//					pnlDRFDtl_2a = new JPanel(new GridLayout(7, 1, 0, 5));
//					pnlCenter_2.add(pnlDRFDtl_2a, BorderLayout.WEST);
//					pnlDRFDtl_2a.setPreferredSize(new java.awt.Dimension(100, 185));
//					pnlDRFDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//
//					{
//						lookupRequestType = new _JLookup(null, "Request Type", 2, 2);
//						pnlDRFDtl_2a.add(lookupRequestType);
//						lookupRequestType.setBounds(20, 27, 20, 25);
//						lookupRequestType.setReturnColumn(0);
//						lookupRequestType.setEnabled(false);
//						lookupRequestType.setFilterName(true);
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
//						pnlDRFDtl_2a.add(lookupPayee2);
//						lookupPayee2.setBounds(20, 27, 20, 25);
//						lookupPayee2.setReturnColumn(0);
//						lookupPayee2.setFilterName(true);
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
//					{
//						lookupAccount= new _JLookup(null, "Account", 2, 2);
//						pnlDRFDtl_2a.add(lookupAccount);
//						lookupAccount.setBounds(20, 27, 20, 25);
//						lookupAccount.setReturnColumn(0);
//						lookupAccount.setEnabled(false);
//						lookupAccount.setFilterName(true);
//						//lookupAccount.setEditable(false);
//						lookupAccount.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupAccount.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){						
//									acct_id 	= (String) data[0];		
//									acct_name 	= (String) data[1];			
//									tagAccount.setTag(acct_name);									
//								}
//							}
//						});	
//					}	
//					{
//						lookupPreparedby = new _JLookup(null, "DRF Status", 2, 2);
//						pnlDRFDtl_2a.add(lookupPreparedby);
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
//						pnlDRFDtl_2a.add(lookupStatus);
//						lookupStatus.setBounds(20, 27, 20, 25);
//						lookupStatus.setReturnColumn(0);
//						lookupStatus.setEnabled(false);	
//						lookupStatus.setFilterName(true);
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
//					{
//						lookupBranch = new _JLookup(null, "Office Branch", 2, 2);
//						pnlDRFDtl_2a.add(lookupBranch);
//						lookupBranch.setBounds(20, 27, 20, 25);
//						lookupBranch.setReturnColumn(0);
//						lookupBranch.setEnabled(false);	
//						lookupBranch.setFilterName(true);
//						//lookupBranch.setEditable(false);
//						lookupBranch.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupBranch.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									branch_id 		= (String) data[0];		
//									branch_name 	= (String) data[1];			
//									tagBranch.setTag(branch_name);	
//								}
//							}
//						});	
//					}		
//
//					pnlDRFDtl_2b = new JPanel(new GridLayout(7, 1, 0, 5));
//					pnlCenter_2.add(pnlDRFDtl_2b, BorderLayout.CENTER);
//					pnlDRFDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
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
//						tagAccount = new _JTagLabel("[ ]");
//						pnlDRFDtl_2b.add(tagAccount);
//						tagAccount.setBounds(209, 27, 700, 22);
//						tagAccount.setEnabled(false);	
//						tagAccount.setPreferredSize(new java.awt.Dimension(27, 33));
//					}	
//					
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
//					{
//						tagBranch = new _JTagLabel("[ ]");
//						pnlDRFDtl_2b.add(tagBranch);
//						tagBranch.setBounds(209, 27, 700, 22);
//						tagBranch.setEnabled(false);	
//						tagBranch.setPreferredSize(new java.awt.Dimension(27, 33));
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
//		this.setBounds(0, 0, 624, 506);  //174
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
											lookupRequestType.setLookupSQL(getRequestType());	
											lookupPayee1.setLookupSQL(getPayee());	
											lookupPayee2.setLookupSQL(getAvailer());	
											lookupAccount.setLookupSQL(getAccount());	
											lookupPreparedby.setLookupSQL(getPreparedBy());	
											lookupBranch.setLookupSQL(getBranch());	
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
							GridBagConstraints cons_drfno = new GridBagConstraints();
							cons_drfno.fill = GridBagConstraints.BOTH;
							cons_drfno.insets = new Insets(5, 5, 5, 5);
							
							{
								cons_drfno.weightx = 0.5;
								cons_drfno.weighty = 1;

								cons_drfno.gridx = 0; 
								cons_drfno.gridy = 0; 
								
								lblDRFFrom = new JLabel("DRF From", JLabel.CENTER);
								pnlDRF.add(lblDRFFrom, cons_drfno);
								lblDRFFrom.setEnabled(false);
								lblDRFFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_drfno.weightx = 1;
								cons_drfno.weighty = 1;

								cons_drfno.gridx = 1; 
								cons_drfno.gridy = 0; 
								

								txtDRFfrom = new JXTextField("");
								pnlDRF.add(txtDRFfrom, cons_drfno);
								txtDRFfrom.setEnabled(false);	
								txtDRFfrom.setEditable(true);
								txtDRFfrom.setHorizontalAlignment(JTextField.CENTER);
								txtDRFfrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_drfno.weightx = 0.5;
								cons_drfno.weighty = 1;

								cons_drfno.gridx = 2; 
								cons_drfno.gridy = 0; 
								
								lblDRFTo = new JLabel("DRF To", JLabel.CENTER);
								pnlDRF.add(lblDRFTo, cons_drfno);
								lblDRFTo.setEnabled(false);	
								lblDRFTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_drfno.weightx = 1;
								cons_drfno.weighty = 1;

								cons_drfno.gridx = 3; 
								cons_drfno.gridy = 0;
								
								txtDRFto = new JXTextField("");
								pnlDRF.add(txtDRFto, cons_drfno);
								txtDRFto.setEnabled(false);	
								txtDRFto.setEditable(true);
								txtDRFto.setHorizontalAlignment(JTextField.CENTER);
								txtDRFto.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 2;
						
						JPanel pnlCreate = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCreate, c);
						pnlCreate.setBorder(components.JTBorderFactory.createTitleBorder("Created Date"));
						{
							GridBagConstraints cons_dtecreated = new GridBagConstraints();
							cons_dtecreated.fill = GridBagConstraints.BOTH;
							cons_dtecreated.insets = new Insets(5, 5, 5, 5);
							
							{
								cons_dtecreated.weightx = 0.5;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 0; 
								cons_dtecreated.gridy = 0; 
								
								lblDateFrom = new JLabel("Date From", JLabel.CENTER);
								pnlCreate.add(lblDateFrom, cons_dtecreated);
								lblDateFrom.setEnabled(false);
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_dtecreated.weightx = 1;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 1; 
								cons_dtecreated.gridy = 0; 
								
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCreate.add(dteDateFrom, cons_dtecreated);						
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(false);
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_dtecreated.weightx = 0.5;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 2; 
								cons_dtecreated.gridy = 0; 
								
								lblDateTo = new JLabel("Date To", JLabel.CENTER);
								pnlCreate.add(lblDateTo, cons_dtecreated);
								lblDateTo.setEnabled(false);
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_dtecreated.weightx = 1;
								cons_dtecreated.weighty = 1;

								cons_dtecreated.gridx = 3; 
								cons_dtecreated.gridy = 0;
								
								dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCreate.add(dateDateTo, cons_dtecreated);
								dateDateTo.setDate(null);
								dateDateTo.setEnabled(false);
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
							GridBagConstraints cons_searchopt = new GridBagConstraints();
							cons_searchopt.fill = GridBagConstraints.BOTH;
							cons_searchopt.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1*/
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
								lookupRequestType.setFilterName(true);
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
							
							/*LINE 2*/
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
								lookupPayee1.setFilterName(true);
								//lookupPayee1.setEditable(false);
								lookupPayee1.setFont(FncGlobal.sizeTextValue);
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
							
							/*LINE 3*/
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
								lookupPayee2.setFilterName(true);
								lookupPayee2.setEnabled(false);	
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
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 2;

								tagPayee2 = new _JTagLabel("[ ]");
								pnlSearch.add(tagPayee2, cons_searchopt);
								tagPayee2.setEnabled(false);	
								tagPayee2.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 4*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 3;
								
								lblAccount = new JLabel("Account", JLabel.CENTER);
								pnlSearch.add(lblAccount, cons_searchopt);
								lblAccount.setEnabled(false);
								lblAccount.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 3;							

								lookupAccount= new _JLookup(null, "Account", 2, 2);
								pnlSearch.add(lookupAccount, cons_searchopt);
								lookupAccount.setReturnColumn(0);
								lookupAccount.setEnabled(false);
								lookupAccount.setFilterName(true);
								lookupAccount.setFont(FncGlobal.sizeTextValue);
								//lookupAccount.setEditable(false);
								lookupAccount.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){						
											acct_id 	= (String) data[0];		
											acct_name 	= (String) data[1];			
											tagAccount.setTag(acct_name);									
										}
									}
								});						
							}
							{
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 3;
								
								tagAccount = new _JTagLabel("[ ]");
								pnlSearch.add(tagAccount, cons_searchopt);
								tagAccount.setEnabled(false);	
								tagAccount.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 5*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 4;
								
								lblStatus = new JLabel("Prepared by", JLabel.CENTER);
								pnlSearch.add(lblStatus, cons_searchopt);
								lblStatus.setEnabled(false);	
								lblStatus.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 4;
								
								lookupPreparedby = new _JLookup(null, "Prepared by", 2, 2);
								pnlSearch.add(lookupPreparedby, cons_searchopt);
								lookupPreparedby.setReturnColumn(0);
								lookupPreparedby.setEnabled(false);	
								lookupPreparedby.setFilterName(true);
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
								cons_searchopt.gridy = 4;

								tagPreparedBy = new _JTagLabel("[ ]");
								pnlSearch.add(tagPreparedBy, cons_searchopt);
								tagPreparedBy.setEnabled(false);	
								tagPreparedBy.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 6*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 5;

								lblPreparedby = new JLabel("DRF Status", JLabel.CENTER);
								pnlSearch.add(lblPreparedby, cons_searchopt);
								lblPreparedby.setEnabled(false);	
								lblPreparedby.setFont(FncGlobal.sizeLabel);								
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 5;
								
								lookupStatus = new _JLookup(null, "Prepared by", 2, 2);
								pnlSearch.add(lookupStatus, cons_searchopt);
								lookupStatus.setReturnColumn(0);
								lookupStatus.setEnabled(false);	
								lookupStatus.setFilterName(true);
								//lookupStatus.setEditable(false);
								lookupStatus.setFont(FncGlobal.sizeTextValue);
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
								cons_searchopt.gridy = 5;
								
								tagStatus = new _JTagLabel("[ ]");
								pnlSearch.add(tagStatus, cons_searchopt);
								tagStatus.setEnabled(false);	
								tagStatus.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 7*/
							{
								cons_searchopt.weightx = 0;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 6;
								
								lblBranch = new JLabel("Office Branch", JLabel.CENTER);
								pnlSearch.add(lblBranch, cons_searchopt);
								lblBranch.setEnabled(false);	
								lblBranch.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 1;
								cons_searchopt.gridy = 6;
								

								lookupBranch = new _JLookup(null, "Office Branch", 2, 2);
								pnlSearch.add(lookupBranch, cons_searchopt);
								lookupBranch.setReturnColumn(0);
								lookupBranch.setEnabled(false);	
								lookupBranch.setFilterName(true);
								lookupBranch.setFont(FncGlobal.sizeTextValue);
								//lookupBranch.setEditable(false);
								lookupBranch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											branch_id 		= (String) data[0];		
											branch_name 	= (String) data[1];			
											tagBranch.setTag(branch_name);	
										}
									}
								});								
							}
							{
								cons_searchopt.weightx = 1.5;
								cons_searchopt.weighty = 1;
								
								cons_searchopt.gridx = 2;
								cons_searchopt.gridy = 6;

								tagBranch = new _JTagLabel("[ ]");
								pnlSearch.add(tagBranch, cons_searchopt);
								tagBranch.setEnabled(false);	
								tagBranch.setFont(FncGlobal.sizeTextValue);
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
					btnPreview.setMaximumSize(new Dimension(100, 30));
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
		//this.setBounds(0, 0, 624, 506);  //174
		
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
		acct_id 		= lookupAccount.getText().trim();
		prepared_by_id 	= lookupPreparedby.getText().trim();
		status_id 		= lookupStatus.getText().trim();
		branch_id 		= lookupBranch.getText().trim();
		
		Integer drf_fr = 0;
		Integer drf_to = 0;
		
		Date date_fr = null;
		Date date_to = null;
		
		try {date_fr = dateFormat.parse("2000-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		try {date_to = dateFormat.parse("2100-01-01 00:00:00");} catch (ParseException e1) {e1.printStackTrace();}
		
		if (txtDRFfrom.getText().equals("")) { } else { drf_fr = Integer.parseInt(txtDRFfrom.getText()); }
		if (txtDRFto.getText().equals("")) { } else { drf_to = Integer.parseInt(txtDRFto.getText()); }
		if (dteDateFrom.getDate()==null) { } else { date_fr = dteDateFrom.getDate(); }
		if (dateDateTo.getDate()==null) { } else { date_to = dateDateTo.getDate(); }
		
		System.out.printf("DRF Fr.: " + txtDRFfrom.getText() + "\n");
		System.out.printf("DRF To.: " + txtDRFto.getText() + "\n");
			
		if(req_type_id.equals("")) { req_type_name = "All"; } else { }
		if(payee_id.equals("")) { payee_name = "All"; } else { }
		if(availer_id.equals("")) { availer_name = "All"; } else { }
		if(acct_id.equals("")) { acct_name = "All"; } else { }
		if(prepared_by_id.equals("")) { prepared_by_name = "All"; } else { }
		if(status_id.equals("")) { status_name = "All"; } else { }
		if(branch_id.equals("")) { branch_name = "All"; } else { }
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "DRF Prooflist";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id", co_id);
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
			mapParameters.put("acct_id", acct_id);	
			mapParameters.put("acct_name", acct_name);	
			mapParameters.put("prepared_id", prepared_by_id);	
			mapParameters.put("prepared_name", prepared_by_name);	
			mapParameters.put("status_id", status_id);	
			mapParameters.put("status_name", status_name);
			mapParameters.put("branch_id", branch_id);	
			mapParameters.put("branch_name", branch_name);
			mapParameters.put("drf_fr", drf_fr);	
			mapParameters.put("drf_to", drf_to);

			FncReport.generateReport("/Reports/rptDRF_prooflist.jasper", reportTitle, "", mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			refreshFields();
		}

	}

	public static void enabledFields(boolean x){
		
		lblDRFFrom.setEnabled(x);		
		txtDRFfrom.setEnabled(x);	
		lblDRFTo.setEnabled(x);	
		txtDRFto.setEnabled(x);	
		lblDateFrom.setEnabled(x);			
		dteDateFrom.setEnabled(x);
		lblDateTo.setEnabled(x);	
		dateDateTo.setEnabled(x);
		
		lblDateRequest.setEnabled(x);	
		lblPayee.setEnabled(x);	
		lblAccount.setEnabled(x);	
		lblAvailer.setEnabled(x);	
		lblStatus.setEnabled(x);	
		lblPreparedby.setEnabled(x);	
		lblBranch.setEnabled(x);
		
		lookupRequestType.setEnabled(x);	
		lookupPayee1.setEnabled(x);	
		lookupPayee2.setEnabled(x);	
		lookupAccount.setEnabled(x);	
		lookupStatus.setEnabled(x);	
		lookupPreparedby.setEnabled(x);	
		lookupBranch.setEnabled(x);	
		
		//lookupRequestType.setEditable(x);	
		//lookupPayee1.setEditable(x);	
		//lookupPayee2.setEditable(x);	
		//lookupAccount.setEditable(x);	
		//lookupStatus.setEditable(x);	
		//lookupPreparedby.setEditable(x);	
		//lookupBranch.setEditable(x);	
		
		tagReqType.setEnabled(x);	
		tagPayee1.setEnabled(x);	
		tagPayee2.setEnabled(x);	
		tagAccount.setEnabled(x);	
		tagStatus.setEnabled(x);	
		tagPreparedBy.setEnabled(x);
		tagBranch.setEnabled(x);		
		
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
		
		txtDRFfrom.setEnabled(x);
		txtDRFto.setEnabled(x);
		
		
	}
	
	public void refreshFields(){
		
		lookupRequestType.setValue("");	
		//lookupCompany.setValue("");	
		lookupPayee1.setValue("");	
		lookupPayee2.setValue("");	
		lookupAccount.setValue("");		
		lookupStatus.setValue("");	
		lookupPreparedby.setValue("");	
		lookupBranch.setValue("");	
		//txtCompany.setText("");	
		
		tagReqType.setTag("");
		tagPayee1.setTag("");
		tagPayee2.setTag("");	
		tagAccount.setTag("");
		tagStatus.setTag("");	
		tagPreparedBy.setTag("");
		tagBranch.setTag("");		
		
		dteDateFrom.setDate(null);
		dateDateTo.setDate(null);
		
		req_type_id 	= "";
		req_type_name	= "";
		payee_id 		= "";
		payee_name		= "";
		availer_id 		= "";
		availer_name 	= "";
		prepared_by_id	= "";
		prepared_by_name= "";
		branch_id		= "";
		branch_name		= "";
		acct_id			= "";
		acct_name		= "";
		status_id		= "";
		status_name		= "";
		
		txtDRFfrom.setText("");
		txtDRFto.setText("");
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);

		enabledFields(true);
		lookupRequestType.setLookupSQL(getRequestType());	
		lookupPayee1.setLookupSQL(getPayee());	
		lookupPayee2.setLookupSQL(getAvailer());	
		lookupAccount.setLookupSQL(getAccount());	
		lookupPreparedby.setLookupSQL(getPreparedBy());	
		lookupBranch.setLookupSQL(getBranch());	
		lookupStatus.setLookupSQL(getStatus());	
		
		lookupCompany.setValue(co_id);
	}
	
	
	//lookup
	public String getRequestType(){

		String sql = 
		"select " +
		"rplf_type_id as \"Type ID\",  \n" +
		"trim(rplf_type_desc) as \"Description\"  \n" +
		"from mf_rplf_type where status_id = 'A' " +
		"order by rplf_type_id    ";		
		return sql;

	}	
	
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
	
	public String getAccount(){

		String sql = 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.acct_id,\r\n" + 
		"upper(trim(b.acct_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_request_detail a \r\n" + 
		"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id ) a\r\n" + 
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
		"from rf_request_header a \r\n" + 
		"left join em_employee b on a.created_by = b.emp_code\r\n" + 
		"left join rf_entity c on b.entity_id = c.entity_id    \n" +
		"\r\n" + 
		") a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
	
	public String getBranch(){

		String sql = 
		"\r\n" + 
		"select distinct on (a.name) * from (\r\n" + 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.branch_id,\r\n" + 
		"upper(trim(b.branch_name)) as name\r\n" + 
		"\r\n" + 
		"from rf_request_header a \r\n" + 
		"left join mf_office_branch b on a.branch_id = b.branch_id\r\n" + 
		"\r\n" + 
		") a\r\n" + 
		"\r\n" + 
		"order by a.name";		
		return sql;

	}	
	
	public String getStatus(){

		String sql = 
		"select distinct on (a.name) * \r\n" + 
		"\r\n" + 
		"from (\r\n" + 
		"		\r\n" + 
		"	select \r\n" + 
		"		\r\n" + 
		"	( case when a.status_id = 'I' then 'I' else \r\n" + 
		"		( case when a.status_id = 'D' then 'D' else \r\n" + 
		"		( case when a.status_id = 'A' and c.pv_no is null then 'A' else \r\n" + 
		"		( case when a.status_id = 'A' and c.pv_no is not null then 'P' else '' end) end) end) end) as status_id, \r\n" + 
		"	( case when a.status_id = 'I' then 'INACTIVE' else \r\n" + 
		"		( case when a.status_id = 'D' then 'DELETED' else \r\n" + 
		"		( case when a.status_id = 'A' and c.pv_no is null then 'ACTIVE' else \r\n" + 
		"		( case when a.status_id = 'A' and c.pv_no is not null then 'PROCESSED' else '' end) end) end) end) as name\r\n" + 
		"		\r\n" + 
		"	from rf_request_header a 		\r\n" + 
		"	left join rf_pv_header c on a.rplf_no = c.pv_no and a.co_id = c.co_id\r\n" + 
		"\r\n" + 
		"	) a\r\n" + 
		"		\r\n" + 
		"order by a.name";		
		return sql;

	}	
	

	//validation
	public void checkPeriod(){
		
		
		
		
		
	}

}
