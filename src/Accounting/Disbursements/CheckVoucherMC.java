package Accounting.Disbursements;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelCV_acct_entries;
import tablemodel.modelCV_pv;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Renderer.DateRenderer;
import Utilities.AddCheckNumber;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class CheckVoucherMC extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Disbursement Voucher (MC)";
	static Dimension SIZE = new Dimension(1000, 750);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlDVDtl_1;
	private JPanel pnlDVDtl_2;
	private JPanel pnlDVDtl_1a;
	private JPanel pnlDVDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlDV;
	private JPanel pnlDV_a;
	private JPanel pnlDV_a1;
	private JPanel pnlDV_a2;
	private JPanel pnlDV_a2_1;
	private JPanel pnlDV_a2_2;
	private JPanel pnlDV_acct_entries;
	private JPanel pnlDVDtl;
	private JPanel pnlDV_a2_3;
	private JPanel pnlDVInfo;
	private JPanel pnlDVInfo_1;
	private JPanel pnlDVDtl_2a;
	private JPanel pnlDVDtl_2b;
	private JPanel pnlDV_particular;
	private JPanel pnlDV_pv;
	private JPanel pnlRemarks;
	private JPanel pnlDV_south;
	private JPanel pnlDV_south1;
	private JPanel pnlDV_south2;
	private JPanel pnlDV_south3;
	private JPanel pnlDV_south2a;
	private JPanel pnlDV_south2b;
	private JPanel pnlDV_south_main;
	private JPanel pnlSouthCenter;
	private JPanel pnlSouthCenterb;

	private JLabel lblCompany;
	private JLabel lblDateNeeded;
	public static JLabel lblDV_no;
	private JLabel lblStatus;
	private JLabel lblCheckPayee;
	private JLabel lblPayeeType;
	private JLabel lblDV_date;
	private JLabel lblDatePaid;
	private JLabel lblCheckNo;
	private JLabel lblChkAmount;
	private JLabel lblPaymentType;
	private JLabel lblBankNo;

	public static _JLookup lookupCompany;
	public static _JLookup lookupDV_no;
	private static _JLookup lookupChkPayee;
	private static _JLookup lookupPayeeType;
	private static _JLookup lookupPaymentType;
	private static _JLookup lookupBankAcctNo;

	private static _JScrollPaneMain scrollDV_acct_entries;
	private static _JScrollPaneTotal scrollDV_acct_entries_total;
	private static _JScrollPaneMain scrollDV_pv;		
	private static _JScrollPaneTotal scrollDV_pvtotal;			

	public static modelCV_acct_entries modelDV_acct_entries;
	public static modelCV_acct_entries modelDV_accttotal;
	public static modelCV_pv modelDV_pvtotal;
	public static modelCV_pv modelDV_pv;

	private static _JTableTotal tblDV_acct_entries_total;	
	private static _JTableTotal tblDV_pvtotal;
	private static _JTableMain tblDV_acct_entries;	
	private static _JTableMain tblDV_pv;		

	public static JList rowHeaderDV_acct_entries;
	public static JList rowHeaderDV_pv;

	private _JTabbedPane tabCenter;	

	public static _JTagLabel tagCompany;
	private static _JTagLabel tagPayeeType;
	private static _JTagLabel tagCheckPayee;
	private static _JTagLabel tagPmtType;

	private static JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnAddNew;
	private static JButton btnRefresh;	
	private static JButton btnEdit;
	private static JButton btnTag;
	private static JButton btnDelete;
	private static JButton btnPreview;
	private static JButton btnUntag;
	private static JButton btnPost;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private static _JDateChooser dteCheck;
	private static _JDateChooser dteDV;	
	private static _JDateChooser dtePaid;	

	private static JXTextField txtStatus;
	private static JXTextField txtBankAlias;
	private static JXTextField txtBankAcct;
	private static JXTextField txtBankAccountType;
	private static JXTextField txtCheckNo;

	private static _JXFormattedTextField txtCheckAmt;

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JPopupMenu menu;	
	private JPopupMenu menu2;
	private JScrollPane scpDRFRemark;	
	private static JTextArea txtDV_particular;
	public static JMenuItem mnidelete;
	public static JMenuItem mniaddrow;
	private JMenuItem mniopenRPLF;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenDP;
	private JMenuItem mniediteDatePaid;
	private JMenuItem mniaddCheck;
	private JMenuItem mnicheckReprocess;

	String drf_no = "";    //this is used to denote the number upon displaying of existing/saved RPLF No.
	public static String cv_no = "";    //this is used to denote the number upon displaying of existing/saved CV no.
	String next_cv_no = "";
	String rplf_no = "";   //this is used to denote the RPLF number used for saving
	public static String co_id = "02";
	String availer_id = "";
	Boolean is_payee_vatable = false;	
	public static String payee = "";
	static String payee_name = "";
	String acct_id = "";
	String next_chk_no = "";
	String last_chk_no = "";
	String last_chk_no_used = "";
	BigInteger next_chk_no_int = null;
	BigInteger last_chk_no_int = null;
	Integer next_rec_id = 0;	
	public static String company = "";
	public static String company_logo = "";
	static String pay_type = "";
	private String remarks = "";	
	private String to_do = "addnew";
	static String rplf_type = "";
	String year 	= "";
	String month 	= "";
	private JCheckBox chkCurrentYear;
	private String emp_code;
	//static String isCurrentYear = "2018";
	static String isCurrentYear = getCurrentYear();//**added by JED 2019-01-03 : to get current year for DV no lookup**//
	
	public CheckVoucherMC() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CheckVoucherMC(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public CheckVoucherMC(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(866, 530));
		this.setBounds(0, 0, 866, 530);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Row      ");
			mniaddrow = new JMenuItem("Add Row");
			menu.add(mnidelete);
			menu.add(mniaddrow);
			mnidelete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					removeRow();				
				}
			});
			mniaddrow.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					AddRow();
				}
			});
		}
		{
			menu2 = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenPV = new JMenuItem("Open Payable Voucher");
			mniopenDP = new JMenuItem("Open Docs Processing");
			mniaddCheck = new JMenuItem("Add Check Number");	
			mniediteDatePaid = new JMenuItem("Edit Date Paid");	
			mnicheckReprocess = new JMenuItem("Reprocess Check");	
			menu2.add(mniopenRPLF);
			menu2.add(mniopenPV);
			menu2.add(mniopenDP);
			JSeparator sp = new JSeparator();
			menu2.add(sp);	
			menu2.add(mniaddCheck);
			menu2.add(mniediteDatePaid);
			JSeparator sp1 = new JSeparator();
			menu2.add(sp1);	
			menu2.add(mnicheckReprocess);
			mnicheckReprocess.setEnabled(false);
			
			mniopenRPLF.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openDRF();					
				}
			});
			mniopenPV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openPV();			
				}
			});
			mniopenDP.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openDP();
				}
			});
			mniaddCheck.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					addCheck();					
				}
			});
			mniediteDatePaid.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					editDatePaid();
				}
			});
		}




		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 240));

			pnlComp = new JPanel(new BorderLayout(5, 0));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);	

			//company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
			pnlComp_a.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(209, 30));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			{
				lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
				pnlComp_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(105, 25));
				lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				lookupCompany = new _JLookup(null, "Company",0,2);
				pnlComp_a1.add(lookupCompany);
				lookupCompany.setLookupSQL(SQL_COMPANY());
				lookupCompany.setReturnColumn(0);
				lookupCompany.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							co_id 		= (String) data[0];	
							tagCompany.setTag((String) data[1]);
							company		= (String) data[1];	
							company_logo = (String) data[3];

							lblDV_no.setEnabled(true);	
							lookupDV_no.setEnabled(true);	
							lookupDV_no.setLookupSQL(getDV_no(co_id));
							enableButtons(true, false, false, false, false, false, false, false, false, false );
						}
					}
				});
			}			

			pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
			pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
			pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			{
				tagCompany = new _JTagLabel("[ ]");
				pnlComp_a2.add(tagCompany);
				tagCompany.setBounds(209, 27, 700, 22);
				tagCompany.setEnabled(true);	
				tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
			}	

			pnlDV = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlDV, BorderLayout.CENTER);				
			pnlDV.setPreferredSize(new java.awt.Dimension(921, 233));
			pnlDV.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlDV.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlDV_a = new JPanel(new BorderLayout(5, 5));
			pnlDV.add(pnlDV_a, BorderLayout.NORTH);	
			pnlDV_a.setPreferredSize(new java.awt.Dimension(911, 42));
			pnlDV_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlDV_a.setBorder(lineBorder);

			pnlDV_a1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlDV_a.add(pnlDV_a1, BorderLayout.WEST);	
			pnlDV_a1.setPreferredSize(new java.awt.Dimension(92, 40));	
			pnlDV_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

			{
				lblDV_no = new JLabel("DV No.", JLabel.TRAILING);
				pnlDV_a1.add(lblDV_no);
				lblDV_no.setEnabled(false);	
				lblDV_no.setPreferredSize(new java.awt.Dimension(86, 40));
				lblDV_no.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}						

			pnlDV_a2 = new JPanel(new BorderLayout(5, 5));
			pnlDV_a.add(pnlDV_a2, BorderLayout.CENTER);	
			pnlDV_a2.setPreferredSize(new java.awt.Dimension(814, 40));	
			pnlDV_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
			pnlDV_a2.addMouseListener(new PopupTriggerListener_panel2());

			pnlDV_a2_1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlDV_a2.add(pnlDV_a2_1, BorderLayout.WEST);	
			pnlDV_a2_1.setPreferredSize(new java.awt.Dimension(197, 22));					

			{
				lookupDV_no = new _JLookup(null, "DV No.", 2, 2);
				pnlDV_a2_1.add(lookupDV_no);
				lookupDV_no.setBounds(20, 27, 20, 25);
				lookupDV_no.setReturnColumn(0);
				lookupDV_no.setEnabled(false);	
				lookupDV_no.setPreferredSize(new java.awt.Dimension(157, 22));
				lookupDV_no.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){								
							cv_no = (String) data[0];
							enable_fields(false);
							refresh_fields();
							refresh_tablesMain();
							setDV_header(cv_no);							

							displayDV_account_entries(modelDV_acct_entries, rowHeaderDV_acct_entries, modelDV_accttotal, cv_no );	
							displayDV_pv(modelDV_pv, rowHeaderDV_pv, modelDV_pvtotal, cv_no );	

							String status = txtStatus.getText().trim();
							if(status.equals("ACTIVE")) 
							{
								enableButtons(false, true, true, true, true, false, true, false, false, true );								
							} 
							else if (status.equals("TAGGED")) 
							{ 
								enableButtons(false, false, true, true, true, false, false, true, true, true );
							}
							else if (status.equals("POSTED")) 
							{ 
								enableButtons(false, false, true, true, true, false, false, false, false, false );
							}
							else 
							{ 								
								enableButtons(false, false, false, true, true, false, false, false, false, false ); 
							}

							mnidelete.setEnabled(false);
							mniaddrow.setEnabled(false);

						}
					}
				});
			}	

			pnlDV_a2_2 = new JPanel(new GridLayout(1, 2, 5, 0));
			pnlDV_a2.add(pnlDV_a2_2, BorderLayout.CENTER);	
			pnlDV_a2_2.setPreferredSize(new java.awt.Dimension(357, 25));	
			pnlDV_a2_2.addMouseListener(new PopupTriggerListener_panel2());
			
			{
				chkCurrentYear = new JCheckBox("current year only?");
				pnlDV_a2_2.add(chkCurrentYear);
				chkCurrentYear.setEnabled(true);	
				chkCurrentYear.setSelected(true);
				chkCurrentYear.setFont(new java.awt.Font("Segoe UI",Font.BOLD,9));
				chkCurrentYear.setPreferredSize(new java.awt.Dimension(148, 24));
				chkCurrentYear.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {

						if (chkCurrentYear.isSelected()==true) {
							//isCurrentYear = "2018";
							isCurrentYear = getCurrentYear();	
							lookupDV_no.setLookupSQL(getDV_no(co_id));	
						} 

						else {
							isCurrentYear = "";		
							lookupDV_no.setLookupSQL(getDV_no(co_id));	
						}
					}
				});
			}

			pnlDV_a2_3 = new JPanel(new GridLayout(1, 2,5,0));
			pnlDV_a2.add(pnlDV_a2_3, BorderLayout.EAST);	
			pnlDV_a2_3.setPreferredSize(new java.awt.Dimension(310, 20));	
			pnlDV_a2_3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));		
			pnlDV_a2_3.addMouseListener(new PopupTriggerListener_panel2());

			{
				lblStatus = new JLabel("Status", JLabel.TRAILING);
				pnlDV_a2_3.add(lblStatus);
				lblStatus.setEnabled(false);	
			}	
			{
				txtStatus = new JXTextField("");
				pnlDV_a2_3.add(txtStatus);
				txtStatus.setEnabled(false);	
				txtStatus.setEditable(false);
				txtStatus.setBounds(120, 25, 300, 22);	
				txtStatus.setHorizontalAlignment(JTextField.CENTER);	
				txtStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				txtStatus.addMouseListener(new PopupTriggerListener_panel2());
			}	
			{
				pnlDVDtl = new JPanel(new BorderLayout(0, 5));
				pnlDV.add(pnlDVDtl, BorderLayout.WEST);	
				pnlDVDtl.setPreferredSize(new java.awt.Dimension(911, 132));

				pnlDVDtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlDVDtl.add(pnlDVDtl_1, BorderLayout.WEST);	
				pnlDVDtl_1.setPreferredSize(new java.awt.Dimension(237, 116));
				pnlDVDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

				pnlDVDtl_1a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlDVDtl_1.add(pnlDVDtl_1a, BorderLayout.WEST);	
				pnlDVDtl_1a.setPreferredSize(new java.awt.Dimension(110, 116));
				pnlDVDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));							
				{
					lblDV_date = new JLabel("DV Date", JLabel.TRAILING);
					pnlDVDtl_1a.add(lblDV_date);
					lblDV_date.setEnabled(false);	
				}	
				{
					lblDateNeeded = new JLabel("Check Date", JLabel.TRAILING);
					pnlDVDtl_1a.add(lblDateNeeded);
					lblDateNeeded.setEnabled(false);
					lblDateNeeded.setVisible(false);
				}	
				{
					lblDatePaid = new JLabel("Date Paid", JLabel.TRAILING);
					pnlDVDtl_1a.add(lblDatePaid);
					lblDatePaid.setEnabled(false);	
				}
				{
					lblCheckNo = new JLabel("Check No.", JLabel.TRAILING);
					pnlDVDtl_1a.add(lblCheckNo);
					lblCheckNo.setEnabled(false);
					lblCheckNo.setVisible(false);
				}

				pnlDVDtl_1b = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlDVDtl_1.add(pnlDVDtl_1b, BorderLayout.CENTER);	
				pnlDVDtl_1b.setPreferredSize(new java.awt.Dimension(135, 119));
				pnlDVDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					dteDV = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDVDtl_1b.add(dteDV);
					dteDV.setBounds(485, 7, 125, 21);
					dteDV.setDate(null);
					dteDV.setEnabled(false);
					dteDV.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteDV.getDateEditor()).setEditable(false);
					dteDV.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}		
				{
					dteCheck = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDVDtl_1b.add(dteCheck);
					dteCheck.setBounds(485, 7, 125, 21);
					dteCheck.setDate(null);
					dteCheck.setEnabled(false);
					dteCheck.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteCheck.getDateEditor()).setEditable(false);
					dteCheck.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteCheck.setVisible(false);
				}	
				{
					dtePaid = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDVDtl_1b.add(dtePaid);
					dtePaid.setBounds(485, 7, 125, 21);
					dtePaid.setDate(null);
					dtePaid.setEnabled(false);
					dtePaid.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dtePaid.getDateEditor()).setEditable(false);
					dtePaid.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}
				{
					txtCheckNo = new JXTextField("");
					pnlDVDtl_1b.add(txtCheckNo);
					txtCheckNo.setEnabled(false);	
					txtCheckNo.setEditable(false);
					txtCheckNo.setBounds(120, 25, 300, 22);	
					txtCheckNo.setHorizontalAlignment(JTextField.CENTER);
					txtCheckNo.setVisible(false);
				}

				//Start of Left Panel 
				pnlDVInfo = new JPanel(new BorderLayout(0,0));
				pnlDVDtl.add(pnlDVInfo, BorderLayout.EAST);
				pnlDVInfo.setPreferredSize(new java.awt.Dimension(674, 140));
				pnlDVInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlDVInfo_1 = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlDVInfo.add(pnlDVInfo_1, BorderLayout.WEST);
				pnlDVInfo_1.setPreferredSize(new java.awt.Dimension(84, 140));
				pnlDVInfo_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblCheckPayee = new JLabel("Payee", JLabel.TRAILING);
					pnlDVInfo_1.add(lblCheckPayee);
					lblCheckPayee.setEnabled(false);	
				}	
				{
					lblPayeeType = new JLabel("Payee Type", JLabel.TRAILING);
					pnlDVInfo_1.add(lblPayeeType);
					lblPayeeType.setEnabled(false);	
				}
				{
					lblPaymentType = new JLabel("Payment Type", JLabel.TRAILING);
					pnlDVInfo_1.add(lblPaymentType);
					lblPaymentType.setEnabled(false);	
				}
				{
					lblChkAmount = new JLabel("Amount", JLabel.TRAILING);
					pnlDVInfo_1.add(lblChkAmount);
					lblChkAmount.setEnabled(false);	
				}

				pnlDVDtl_2 = new JPanel(new BorderLayout(5,0));
				pnlDVInfo.add(pnlDVDtl_2, BorderLayout.CENTER);
				pnlDVDtl_2.setPreferredSize(new java.awt.Dimension(203, 118));
				pnlDVDtl_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

				pnlDVDtl_2a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlDVDtl_2.add(pnlDVDtl_2a, BorderLayout.WEST);
				pnlDVDtl_2a.setPreferredSize(new java.awt.Dimension(119, 119));
				pnlDVDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));


				{
					lookupChkPayee = new _JLookup(null, "Payee", 2, 2);
					pnlDVDtl_2a.add(lookupChkPayee);
					lookupChkPayee.setBounds(20, 27, 20, 25);
					lookupChkPayee.setReturnColumn(0);
					lookupChkPayee.setFilterIndex(1);
					lookupChkPayee.setEnabled(false);	
					lookupChkPayee.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupChkPayee.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){	
								String entity_name = (String) data[1];						
								tagCheckPayee.setTag(entity_name);								
							}
						}
					});	
				}	
				{
					lookupPayeeType= new _JLookup(null, "Payee Type", 2, 2);
					pnlDVDtl_2a.add(lookupPayeeType);
					lookupPayeeType.setBounds(20, 27, 20, 25);
					lookupPayeeType.setReturnColumn(0);
					lookupPayeeType.setEnabled(false);	
					lookupPayeeType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPayeeType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){						
								String payee_type = (String) data[1];						
								tagPayeeType.setTag(payee_type);
							}
						}
					});	
				}	
				{
					lookupPaymentType = new _JLookup(null, "Payment Type", 2, 2);
					pnlDVDtl_2a.add(lookupPaymentType);
					lookupPaymentType.setBounds(20, 27, 20, 25);
					lookupPaymentType.setReturnColumn(0);
					lookupPaymentType.setEnabled(false);	
					lookupPaymentType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPaymentType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){		
								String rplf_type = (String) data[1];						
								tagPmtType.setTag(rplf_type);	
								if (rplf_type.equals("CASH")) {
									lblBankNo.setEnabled(false);	
									lookupBankAcctNo.setEnabled(false);	
									lookupBankAcctNo.setValue("");	
									txtBankAlias.setText("");	
									txtBankAcct.setText("");	
									txtBankAlias.setText("");	
									txtBankAccountType.setText("");	
									modelDV_acct_entries.setValueAt("01-01-01-002", 1, 0);
									modelDV_acct_entries.setValueAt("Cash on Hand - Disbursement", 1, 1);
									lblCheckNo.setEnabled(false);	
									txtCheckNo.setEnabled(false);
									txtCheckNo.setEditable(false);
									txtCheckNo.setText("");
									lblDateNeeded.setEnabled(false);	
									dteCheck.setEnabled(false);	
								}
								else 
								{
									lblBankNo.setEnabled(true);	
									lookupBankAcctNo.setEnabled(true);
									modelDV_acct_entries.setValueAt("", 1, 0);
									modelDV_acct_entries.setValueAt("", 1, 1);
									lblCheckNo.setEnabled(true);	
									txtCheckNo.setEnabled(true);
									txtCheckNo.setEditable(true);
									lblDateNeeded.setEnabled(true);	
									dteCheck.setEnabled(true);	
								}
							}
						}
					});	
				}				
				{					
					txtCheckAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlDVDtl_2a.add(txtCheckAmt);
					txtCheckAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtCheckAmt.setText("0.00");
					txtCheckAmt.setBounds(120, 0, 72, 22);
					txtCheckAmt.setEnabled(false);	
				}	

				pnlDVDtl_2b = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlDVDtl_2.add(pnlDVDtl_2b, BorderLayout.CENTER);
				pnlDVDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
				pnlDVDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					tagCheckPayee = new _JTagLabel("[ ]", JLabel.RIGHT);
					pnlDVDtl_2b.add(tagCheckPayee);
					tagCheckPayee.setBounds(209, 27, 700, 22);
					tagCheckPayee.setEnabled(false);	
					tagCheckPayee.setPreferredSize(new java.awt.Dimension(27, 33));
					tagCheckPayee.addMouseListener(new PopupTriggerListener_panel2());
				}	
				{
					tagPayeeType = new _JTagLabel("[ ]");
					pnlDVDtl_2b.add(tagPayeeType);
					tagPayeeType.setBounds(209, 27, 700, 22);
					tagPayeeType.setEnabled(false);	
					tagPayeeType.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPayeeType.addMouseListener(new PopupTriggerListener_panel2());
				}				
				{
					tagPmtType = new _JTagLabel("[ ]");
					pnlDVDtl_2b.add(tagPmtType);
					tagPmtType.setBounds(209, 27, 700, 22);
					tagPmtType.setEnabled(false);	
					tagPmtType.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPmtType.addMouseListener(new PopupTriggerListener_panel2());
				}	
			}	

			pnlDV_south_main =  new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlDV_south_main, BorderLayout.SOUTH);				
			pnlDV_south_main.setPreferredSize(new java.awt.Dimension(921, 28));
			pnlDV_south_main.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));

			pnlDV_south =  new JPanel(new BorderLayout(5, 5));
			pnlDV_south_main.add(pnlDV_south, BorderLayout.WEST);				
			pnlDV_south.setPreferredSize(new java.awt.Dimension(815, 38));
			pnlDV_south.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlDV_south1 =  new JPanel(new GridLayout(1, 2, 0, 5));
			pnlDV_south.add(pnlDV_south1, BorderLayout.WEST);				
			pnlDV_south1.setPreferredSize(new java.awt.Dimension(225, 22));
			pnlDV_south1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

			{
				lblBankNo = new JLabel("Bank Account  ", JLabel.TRAILING);
				pnlDV_south1.add(lblBankNo);
				lblBankNo.setEnabled(false);	
			}
			{
				lookupBankAcctNo = new _JLookup(null, "Bank Account No.", 2, 2);
				pnlDV_south1.add(lookupBankAcctNo);
				lookupBankAcctNo.setBounds(20, 27, 20, 25);
				lookupBankAcctNo.setReturnColumn(1);
				lookupBankAcctNo.setEnabled(false);	
				lookupBankAcctNo.setPreferredSize(new java.awt.Dimension(157, 22));
				lookupBankAcctNo.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){													

							txtBankAlias.setText((String) data[3]);
							txtBankAcct.setText((String) data[2]);
							txtBankAccountType.setText((String) data[4]);
							txtBankAlias.setEnabled(true);
							txtBankAcct.setEnabled(true);
							txtBankAccountType.setEnabled(true);
							acct_id = (String) data[5];

							next_chk_no 	= sql_getNextCheckNo(data[0].toString());
							last_chk_no 	= sql_getLastCheckNo(data[0].toString());
							last_chk_no_used= sql_getLastCheckNoUsed(data[0].toString());
							next_chk_no_int = new BigInteger (sql_getNextCheckNo(data[0].toString()));
							last_chk_no_int = new BigInteger (sql_getLastCheckNo(data[0].toString()));

							if (last_chk_no_used.equals(last_chk_no_int))
							{
								{JOptionPane.showMessageDialog(getContentPane(), "All check numbers have been used up.     " + "\n" + "Upload new check numbers.", 
										"Information", 
										JOptionPane.INFORMATION_MESSAGE);}								
							} 
							//else if ()
							{
								txtCheckNo.setText(next_chk_no);  
							}

							modelDV_acct_entries.setValueAt(acct_id, 1, 0);
							modelDV_acct_entries.setValueAt(sql_getAcctDesc(), 1, 1);

							lookupPaymentType.setText("B");
							tagPmtType.setText("[ CHECK ]");

						}
					}
				});	
			}

			pnlDV_south2 =  new JPanel(new BorderLayout(5, 5));
			pnlDV_south.add(pnlDV_south2, BorderLayout.CENTER);				
			pnlDV_south2.setPreferredSize(new java.awt.Dimension(188, 22));
			pnlDV_south2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			pnlDV_south2a =  new JPanel(new BorderLayout(5, 5));
			pnlDV_south2.add(pnlDV_south2a, BorderLayout.WEST);				
			pnlDV_south2a.setPreferredSize(new java.awt.Dimension(67, 22));
			pnlDV_south2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				txtBankAlias = new JXTextField("");
				pnlDV_south2a.add(txtBankAlias);
				txtBankAlias.setEnabled(false);	
				txtBankAlias.setEditable(false);
				txtBankAlias.setBounds(120, 25, 300, 22);	
				txtBankAlias.setHorizontalAlignment(JTextField.CENTER);	
				txtBankAlias.setPreferredSize(new java.awt.Dimension(83, 22));
			}	

			pnlDV_south2b = new JPanel(new BorderLayout(5, 5));
			pnlDV_south2.add(pnlDV_south2b, BorderLayout.CENTER);				
			pnlDV_south2b.setPreferredSize(new java.awt.Dimension(921, 33));
			pnlDV_south2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				txtBankAcct = new JXTextField("");
				pnlDV_south2b.add(txtBankAcct);
				txtBankAcct.setEnabled(false);	
				txtBankAcct.setEditable(false);
				txtBankAcct.setBounds(120, 25, 300, 22);	
				txtBankAcct.setHorizontalAlignment(JTextField.CENTER);	
			}	

			pnlDV_south3 = new JPanel(new BorderLayout(5, 5));
			pnlDV_south.add(pnlDV_south3, BorderLayout.EAST);				
			pnlDV_south3.setPreferredSize(new java.awt.Dimension(362, 22));
			pnlDV_south3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			{
				txtBankAccountType = new JXTextField("");
				pnlDV_south3.add(txtBankAccountType);
				txtBankAccountType.setEnabled(false);	
				txtBankAccountType.setEditable(false);
				txtBankAccountType.setBounds(120, 25, 300, 22);	
				txtBankAccountType.setHorizontalAlignment(JTextField.CENTER);	
				txtBankAccountType.setPreferredSize(new java.awt.Dimension(296, 22));
			}	
		}		

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlDV = new JPanel();
			pnlTable.add(pnlDV, BorderLayout.CENTER);
			pnlDV.setLayout(new BorderLayout(5, 5));
			pnlDV.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlDV.setBorder(lineBorder);		
			pnlDV.addMouseListener(new PopupTriggerListener_panel());


			tabCenter = new _JTabbedPane();
			pnlDV.add(tabCenter, BorderLayout.CENTER);


			{			
				pnlDV_acct_entries = new JPanel(new BorderLayout());
				tabCenter.addTab("Account Entries", null, pnlDV_acct_entries, null);
				pnlDV_acct_entries.setPreferredSize(new java.awt.Dimension(1183, 365));					

				{
					scrollDV_acct_entries = new _JScrollPaneMain();
					pnlDV_acct_entries.add(scrollDV_acct_entries, BorderLayout.CENTER);
					{
						modelDV_acct_entries = new modelCV_acct_entries();

						tblDV_acct_entries = new _JTableMain(modelDV_acct_entries);
						scrollDV_acct_entries.setViewportView(tblDV_acct_entries);
						tblDV_acct_entries.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblDV_acct_entries.getColumnModel().getColumn(1).setPreferredWidth(300);
						tblDV_acct_entries.getColumnModel().getColumn(2).setPreferredWidth(120);
						tblDV_acct_entries.getColumnModel().getColumn(3).setPreferredWidth(120);
						tblDV_acct_entries.addMouseListener(this);							
						tblDV_acct_entries.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}							
							public void keyPressed(KeyEvent e) {
							}

						}); 
						tblDV_acct_entries.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblDV_acct_entries.rowAtPoint(e.getPoint()) == -1){
									tblDV_acct_entries_total.clearSelection();
								}else{
									tblDV_acct_entries.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblDV_acct_entries.rowAtPoint(e.getPoint()) == -1){
									tblDV_acct_entries_total.clearSelection();
								}else{
									tblDV_acct_entries.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderDV_acct_entries = tblDV_acct_entries.getRowHeader22();
						scrollDV_acct_entries.setRowHeaderView(rowHeaderDV_acct_entries);
						scrollDV_acct_entries.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollDV_acct_entries_total = new _JScrollPaneTotal(scrollDV_acct_entries);
					pnlDV_acct_entries.add(scrollDV_acct_entries_total, BorderLayout.SOUTH);
					{
						modelDV_accttotal = new modelCV_acct_entries();
						modelDV_accttotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00) });

						tblDV_acct_entries_total = new _JTableTotal(modelDV_accttotal, tblDV_acct_entries);
						tblDV_acct_entries_total.setFont(dialog11Bold);
						scrollDV_acct_entries_total.setViewportView(tblDV_acct_entries_total);
						((_JTableTotal) tblDV_acct_entries_total).setTotalLabel(0);
					}
				}
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setPreferredSize(new java.awt.Dimension(923, 65));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					btnAddNew = new JButton("Add New");
					pnlSouthCenterb.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenterb.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenterb.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");	
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
			{
				pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new java.awt.Dimension(921, 32));
				{
					btnSave = new JButton("Save");
					pnlSouthCenter.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnTag = new JButton("Tag");
					pnlSouthCenter.add(btnTag);
					btnTag.setActionCommand("Tag");
					btnTag.addActionListener(this);
					btnTag.setEnabled(false);
				}
				{
					btnUntag = new JButton("Untag");
					pnlSouthCenter.add(btnUntag);
					btnUntag.setActionCommand("Untag");
					btnUntag.addActionListener(this);
					btnUntag.setEnabled(false);
				}
				{
					btnPost = new JButton("Post");
					pnlSouthCenter.add(btnPost);
					btnPost.setActionCommand("Post");
					btnPost.addActionListener(this);
					btnPost.setEnabled(false);
				}
				{
					btnDelete = new JButton("Delete");
					pnlSouthCenter.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.addActionListener(this);
					btnDelete.setEnabled(false);
				}
			}
			{			
				pnlDV_particular = new JPanel(new BorderLayout());
				tabCenter.addTab("Particulars", null, pnlDV_particular, null);
				pnlDV_particular.setBorder(lineBorder);

				pnlRemarks = new JPanel(new BorderLayout(5, 5));
				pnlDV_particular.add(pnlRemarks, BorderLayout.CENTER);			
				pnlRemarks.setPreferredSize(new java.awt.Dimension(100, 195));
				
				scpDRFRemark = new JScrollPane();
				pnlRemarks.add(scpDRFRemark);
				scpDRFRemark.setBounds(82, 7, 500, 61);
				scpDRFRemark.setOpaque(true);
				scpDRFRemark.setPreferredSize(new java.awt.Dimension(100, 190));

				{
					txtDV_particular = new JTextArea();
					scpDRFRemark.add(txtDV_particular);
					scpDRFRemark.setViewportView(txtDV_particular);
					txtDV_particular.setBounds(77, 3, 250, 81);
					txtDV_particular.setLineWrap(true);
					txtDV_particular.setPreferredSize(new java.awt.Dimension(100, 1000));
					txtDV_particular.setEditable(false);
					txtDV_particular.setEnabled(true);	
					txtDV_particular.setText("");	
				}			

			}
			{			
				pnlDV_pv = new JPanel(new BorderLayout());
				tabCenter.addTab("Payable Voucher", null, pnlDV_pv, null);
				pnlDV_pv.setPreferredSize(new java.awt.Dimension(1183, 365));	
				{
					scrollDV_pv = new _JScrollPaneMain();
					pnlDV_pv.add(scrollDV_pv, BorderLayout.CENTER);
					{
						modelDV_pv = new modelCV_pv();

						tblDV_pv = new _JTableMain(modelDV_pv);
						scrollDV_pv.setViewportView(tblDV_pv);
						tblDV_pv.addMouseListener(this);	
						tblDV_pv.setSortable(false);
						tblDV_pv.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblDV_pv.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblDV_pv.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblDV_pv.getColumnModel().getColumn(3).setPreferredWidth(100);
						tblDV_pv.getColumnModel().getColumn(4).setPreferredWidth(100);
						
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						tblDV_pv.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
						tblDV_pv.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
						
						
						tblDV_pv.addMouseListener(new PopupTriggerListener_panel());
						tblDV_pv.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}							
							public void keyPressed(KeyEvent e) {
							}

						}); 
						tblDV_pv.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblDV_pv.rowAtPoint(e.getPoint()) == -1){
									tblDV_pvtotal.clearSelection();
								}else{
									tblDV_pv.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblDV_pv.rowAtPoint(e.getPoint()) == -1){
									tblDV_pvtotal.clearSelection();
								}else{
									tblDV_pv.setCellSelectionEnabled(true);
								}
							}
						});

					}
					{
						rowHeaderDV_pv = tblDV_pv.getRowHeader22();
						scrollDV_pv.setRowHeaderView(rowHeaderDV_pv);
						scrollDV_pv.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollDV_pvtotal = new _JScrollPaneTotal(scrollDV_pv);
					pnlDV_pv.add(scrollDV_pvtotal, BorderLayout.SOUTH);
					{
						modelDV_pvtotal = new modelCV_pv();
						modelDV_pvtotal.addRow(new Object[] { "Total",null,  new BigDecimal(0.00) });

						tblDV_pvtotal = new _JTableTotal(modelDV_pvtotal, tblDV_pv);
						tblDV_pvtotal.setFont(dialog11Bold);
						scrollDV_pvtotal.setViewportView(tblDV_pvtotal);
						((_JTableTotal) tblDV_pvtotal).setTotalLabel(0);
					}
				}	
			}
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display tables
	public static void displayDV_account_entries(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String req_no) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"-----display DV account entries\r\n" + 
			"\r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"a.acct_id,\r\n" + 
			"b.acct_name,\r\n" + 
			"( case when a.bal_side = 'D' then a.tran_amt else '0' end ) as debit,\r\n" + 
			"( case when a.bal_side = 'C' then a.tran_amt else '0' end ) as credit\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"from rf_cv_detail a\r\n" + 
			"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n" + 
			"\r\n" + 
			"where a.cv_no = '"+req_no+"'  " +
			"and a.co_id = '"+co_id+"' " +
			"and a.status_id != 'I'   " +
			"order by a.bal_side desc" ;

		System.out.printf("sql :" + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalDV(modelMain, modelTotal);			
		}		

		else {
			modelDV_accttotal = new modelCV_acct_entries();
			modelDV_accttotal.addRow(new Object[] { "Total",  null, new BigDecimal(0.00), new BigDecimal(0.00) });

			tblDV_acct_entries_total = new _JTableTotal(modelDV_acct_entries, tblDV_acct_entries);
			tblDV_acct_entries_total.setFont(dialog11Bold);
			scrollDV_acct_entries_total.setViewportView(tblDV_acct_entries_total);
			((_JTableTotal) tblDV_acct_entries_total).setTotalLabel(0);}

		adjustRowHeight();
		tblDV_acct_entries.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblDV_acct_entries.getColumnModel().getColumn(1).setPreferredWidth(300);
		tblDV_acct_entries.getColumnModel().getColumn(2).setPreferredWidth(120);
		tblDV_acct_entries.getColumnModel().getColumn(3).setPreferredWidth(120);

	}	

	public static void displayDV_pv(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String req_no) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "select a.pv_no, b.pv_date, sum(a.tran_amt) as tran_amt, c.check_no, c.date_due \n" + 
				"from rf_pv_detail a \n" + 
				"inner join rf_pv_header b on a.pv_no = b.pv_no \n" + 
				"left join rf_check c on b.cv_no = c.cv_no and b.oth_ref_no = c.check_no \n" + 
				"where b.cv_no = '"+req_no+"' and b.co_id = '"+co_id+"' \n" + 
				"and a.status_id = 'A' and a.bal_side = 'D' \n" + 
				"group by a.pv_no, b.pv_date, c.check_no, c.date_due";

		System.out.println("sql: "+sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalDV_pv(modelMain, modelTotal);			
		}		

		else {

			JOptionPane.showMessageDialog(null,"Payable Voucher(s) was/were transferred to a new CV.","Warning",JOptionPane.INFORMATION_MESSAGE); 

			modelDV_pvtotal = new modelCV_pv();
			modelDV_pvtotal.addRow(new Object[] { "Total",null,  new BigDecimal(0.00) });

			tblDV_pvtotal = new _JTableTotal(modelDV_pvtotal, tblDV_pv);
			tblDV_pvtotal.setFont(dialog11Bold);
			scrollDV_pvtotal.setViewportView(tblDV_pvtotal);
			((_JTableTotal) tblDV_pvtotal).setTotalLabel(0);}


		adjustRowHeight_pv();
		tblDV_pv.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblDV_pv.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblDV_pv.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblDV_pvtotal.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblDV_pvtotal.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblDV_pvtotal.getColumnModel().getColumn(2).setPreferredWidth(100);
	}	

	public static void setDV_header(String req_no){//used

		Object[] dv_hdr = getDVdetails(req_no);	
		dteDV.setDate((Date) dv_hdr[1]);
		dteCheck.setDate((Date) dv_hdr[22]);
		dtePaid.setDate((Date) dv_hdr[2]);
		txtCheckNo.setText((String) dv_hdr[5]);		
		txtCheckAmt.setText(nf.format(Double.parseDouble((String) dv_hdr[6].toString())));	
		txtStatus.setText((String) dv_hdr[16]);	

		payee = (String) dv_hdr[17];
		payee_name 	= (String) dv_hdr[3];
		pay_type	= (String) dv_hdr[20];

		lookupChkPayee.setText((String) dv_hdr[17]);	
		lookupPayeeType.setText((String) dv_hdr[8]);
		lookupPaymentType.setText((String) dv_hdr[20]);

		tagCheckPayee.setTag((String) dv_hdr[3]);
		tagPayeeType.setTag((String) dv_hdr[7]);	
		tagPmtType.setTag((String) dv_hdr[9]);		
		//tagCheckPayee.setTag((String) dv_hdr[10]);		

		lookupBankAcctNo.setText((String) dv_hdr[11]);		
		txtBankAlias.setText((String) dv_hdr[15]);	
		txtBankAcct.setText((String) dv_hdr[12]);	
		txtBankAccountType.setText((String) dv_hdr[14]);	

		txtDV_particular.setText((String) dv_hdr[21]);	

	}

	public void createPVtable(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select '', '', 0.00  \r\n" ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalDV_pv(modelMain, modelTotal);			
		}		

		else {
			modelDV_pv = new modelCV_pv();
			modelDV_pv.addRow(new Object[] { "Total",null,   new BigDecimal(0.00)});

			tblDV_pvtotal = new _JTableTotal(modelDV_pvtotal, tblDV_pv);
			tblDV_pvtotal.setFont(dialog11Bold);
			scrollDV_pvtotal.setViewportView(tblDV_pvtotal);
			((_JTableTotal) tblDV_pvtotal).setTotalLabel(0);}


		modelDV_pv.setEditable(true);
		adjustRowHeight_pv();
		tblDV_pv.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblDV_pv.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblDV_pv.getColumnModel().getColumn(2).setPreferredWidth(100);
	}	


	//Enable/Disable all components inside JPanel
	public void enable_fields(Boolean x){//used

		lblDV_date.setEnabled(x);	
		lblDateNeeded.setEnabled(x);	
		lblDatePaid.setEnabled(x);	
		lblCheckNo.setEnabled(x);	
		lblChkAmount.setEnabled(x);	

		dteDV.setEnabled(x);	
		dteCheck.setEnabled(x);	
		dtePaid.setEnabled(x);	
		txtCheckNo.setEnabled(x);	
		txtCheckAmt.setEnabled(x);	

		lblCheckPayee.setEnabled(x);
		lblPayeeType.setEnabled(x);	
		lblPaymentType.setEnabled(x);

		lookupChkPayee.setEnabled(x);	
		//lookupChkPayee.setEditable(x);
		lookupPayeeType.setEnabled(x);	
		//lookupPayeeType.setEditable(x);
		//lookupPaymentType.setEnabled(x);	
		//lookupPaymentType.setEditable(x);

		tagCheckPayee.setEnabled(x);
		tagPayeeType.setEnabled(x);	
		tagPmtType.setEnabled(x);	
		tagCheckPayee.setEnabled(x);	

		lblBankNo.setEnabled(x);	
		lookupBankAcctNo.setEnabled(x);	
		//lookupBankAcctNo.setEditable(x);
		txtBankAlias.setEnabled(x);	
		txtBankAcct.setEnabled(x);	
		txtBankAccountType.setEnabled(x);	

		txtStatus.setEnabled(x);
		lblStatus.setEnabled(x);			

		txtDV_particular.setEnabled(x);	
	}

	public void enable_fields_to_addNew(){//used

		lblDV_no.setEnabled(false);	
		lookupDV_no.setEnabled(false);	
		//lookupDV_no.setEditable(false);	

		lblDV_date.setEnabled(true);	
		lblDateNeeded.setEnabled(true);	
		lblDatePaid.setEnabled(false);	
		lblCheckNo.setEnabled(false);	
		lblChkAmount.setEnabled(false);	

		dteDV.setEnabled(true);	
		dteCheck.setEnabled(true);	
		dtePaid.setEnabled(false);	
		txtCheckNo.setEnabled(false);	
		txtCheckAmt.setEnabled(false);	

		lblPayeeType.setEnabled(false);	
		lblPaymentType.setEnabled(true);		
		lookupPayeeType.setEnabled(false);	
		//lookupPaymentType.setEnabled(true);	

		tagPayeeType.setEnabled(false);	
		tagPmtType.setEnabled(true);	

		lblBankNo.setEnabled(true);	
		lookupBankAcctNo.setEnabled(true);	
		//lookupBankAcctNo.setEditable(true);
		txtBankAlias.setEnabled(false);	
		txtBankAcct.setEnabled(false);	
		txtBankAccountType.setEnabled(false);	

		txtStatus.setEnabled(false);
		lblStatus.setEnabled(false);

		mnidelete.setEnabled(true);
		mniaddrow.setEnabled(true);

		txtDV_particular.setEnabled(false);	
		modelDV_acct_entries.setEditable(false);
		modelDV_pv.setEditable(true);	
	}

	public static void refresh_fields(){//used

		dteDV.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteCheck.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dtePaid.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		txtCheckNo.setText("");		
		txtCheckAmt.setText("0.00");	

		lookupChkPayee.setValue("");
		lookupPayeeType.setValue("");
		lookupPaymentType.setValue("");

		tagCheckPayee.setText("[ ]");	
		tagPayeeType.setText("[ ]");
		tagPmtType.setText("[ ]");
		tagCheckPayee.setText("[ ]");	

		lookupBankAcctNo.setValue("");		
		txtBankAlias.setText("");		
		txtBankAcct.setText("");			
		txtBankAccountType.setText("");		

		txtDV_particular.setText("");	
		txtStatus.setText("");

		txtDV_particular.setText("");	
	}

	public static void refresh_tablesMain(){//used

		//reset table 1
		FncTables.clearTable(modelDV_acct_entries);	
		FncTables.clearTable(modelDV_accttotal);			
		rowHeaderDV_acct_entries = tblDV_acct_entries.getRowHeader22();
		scrollDV_acct_entries.setRowHeaderView(rowHeaderDV_acct_entries);	
		modelDV_accttotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00) });

		//reset table 2
		FncTables.clearTable(modelDV_pv);		
		FncTables.clearTable(modelDV_pvtotal);	
		rowHeaderDV_pv = tblDV_pv.getRowHeader22();
		scrollDV_pv.setRowHeaderView(rowHeaderDV_pv);	
		modelDV_pvtotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

	}

	public static void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, 
			Boolean f, Boolean g, Boolean h, Boolean i, Boolean j ){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnPreview.setEnabled(c);
		btnRefresh.setEnabled(d);
		btnCancel.setEnabled(e);

		btnSave.setEnabled(f);
		btnTag.setEnabled(g);
		btnUntag.setEnabled(h);		
		btnPost.setEnabled(i);
		btnDelete.setEnabled(j);
	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();	

		lblDV_no.setEnabled(true);	
		lookupDV_no.setEnabled(true);	
		lookupDV_no.setLookupSQL(getDV_no(co_id));
		enableButtons(true, false, false, false, false, false, false, false, false, false );		

		lookupCompany.setValue(co_id);
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//used

		if(e.getActionCommand().equals("Refresh")){ refresh();	}

		if(e.getActionCommand().equals("Cancel")){ cancel(); }

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {addnew(); }
		else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create a new disbursement.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) { edit(); }	
		else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit disbursement.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {save(); }
		else if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create a new disbursement.","Warning",JOptionPane.WARNING_MESSAGE); }

		if (e.getActionCommand().equals("Tag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {tag("F", "Tag"); }
		else if (e.getActionCommand().equals("Tag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to tag disbursement.","Warning",JOptionPane.WARNING_MESSAGE); }

		if (e.getActionCommand().equals("Untag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {tag("A", "Untag"); }	
		else if (e.getActionCommand().equals("Untag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to untag disbursement.","Warning",JOptionPane.WARNING_MESSAGE); }

		if (e.getActionCommand().equals("Post") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==true) {post(); }
		else if (e.getActionCommand().equals("Post") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to post disbursement.","Warning",JOptionPane.WARNING_MESSAGE); }

		if (e.getActionCommand().equals("Delete") && FncAcounting.EmpCanDelete(UserInfo.EmployeeCode, "3")==true) {delete();}
		else if (e.getActionCommand().equals("Delete") && FncAcounting.EmpCanDelete(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to delete disbursement.","Warning",JOptionPane.WARNING_MESSAGE); }

		if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "3")==true)
		{							
			preview_CV();		
			if (lookupPaymentType.getText().trim().equals("B") ) {preview_check();}		
		} 
		else if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "3")==false ) 
		{ 
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to issue check.","Warning",JOptionPane.WARNING_MESSAGE); 
		}

		/*if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "3")==true ) { preview_CV(); }
		else if (e.getActionCommand().equals("Preview") && lookupPaymentType.getText().trim().equals("B") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print CV.","Warning",JOptionPane.WARNING_MESSAGE); }
		 */


	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2)) {
			clickTableColumn();
		}	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				if(txtStatus.getText().equals("POSTED")&& FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==true) {mniediteDatePaid.setEnabled(true);}
				else if(!txtStatus.getText().equals("POSTED") || FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==false) {mniediteDatePaid.setEnabled(false);}




			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				if(txtStatus.getText().equals("POSTED")&& FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==true) {mniediteDatePaid.setEnabled(true);}
				else if(!txtStatus.getText().equals("POSTED") || FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==false) {mniediteDatePaid.setEnabled(false);}


			}
		}
	}


	//action performed
	private void addnew(){

		to_do = "addnew";

		txtStatus.setText("ACTIVE");
		lblCheckNo.setEnabled(true);	
		txtCheckNo.setEnabled(true);
		enableButtons(false, false, false, false, true, true, false, false, false, false );
		//lookupPaymentType.setLookupSQL(getPayment_type());		
		createPVtable(modelDV_pv, rowHeaderDV_pv, modelDV_pvtotal);	
		AddRow_acctEntries(0.00);	
		tabCenter.setSelectedIndex(2);			

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Payable Voucher", getPV_list(), false);
		dlg.setLocationRelativeTo(getContentPane());
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();
		if (data != null) {	
			modelDV_pv.setValueAt(data[0], 0, 0);
			modelDV_pv.setValueAt(data[1], 0, 1);
			modelDV_pv.setValueAt(data[2], 0, 2);
			totalDV_pv(modelDV_pv, modelDV_pvtotal);
			AddRow();
			enable_fields_to_addNew();

			Double amount	= Double.parseDouble(modelDV_pvtotal.getValueAt(0,2).toString());	
			txtCheckAmt.setText(nf.format(amount));	

			Object[] pv_hdr = getPVdetails((String) data[0]);	
			payee 		= (String) pv_hdr[0];
			payee_name 	= (String) pv_hdr[1];
			String rem  = (String) pv_hdr[6];
			rplf_type   = (String) pv_hdr[7];

			lookupChkPayee.setText((String) pv_hdr[0]);
			tagCheckPayee.setTag((String) pv_hdr[1]);
			tagCheckPayee.setTag((String) pv_hdr[1]);	
			lookupPayeeType.setText((String) pv_hdr[4]);
			tagPayeeType.setTag((String) pv_hdr[5]);	
			AddRow_acctEntries(amount);
			
			String default_payee = "";
			if(!sql_getDefaultBroker(payee).equals("")){default_payee  = "\n" + "Agent's default check payee name is " + sql_getDefaultBroker(payee);}
			txtDV_particular.setText(setPVRemarks(rem)+default_payee);

			txtDV_particular.setText(setPVRemarks(rem));

			if(data[5].equals("B")) 
			{
				lookupPaymentType.setValue("B");
				tagPmtType.setTag("CHECK");
				lblBankNo.setEnabled(true);	
				lookupBankAcctNo.setEnabled(true);
				modelDV_acct_entries.setValueAt("", 1, 0);
				modelDV_acct_entries.setValueAt("", 1, 1);
				lblCheckNo.setEnabled(true);	
				txtCheckNo.setEnabled(true);
				txtCheckNo.setEditable(true);
				lblDateNeeded.setEnabled(true);	
				dteCheck.setEnabled(true);	

				lblCheckPayee.setEnabled(true);	
				lookupChkPayee.setEnabled(true);	
				tagCheckPayee.setEnabled(true);
			}
			else 
			{
				lookupPaymentType.setValue("A");
				tagPmtType.setTag("CASH");
				lblBankNo.setEnabled(false);	
				lookupBankAcctNo.setEnabled(false);	
				lookupBankAcctNo.setValue("");	
				txtBankAlias.setText("");	
				txtBankAcct.setText("");	
				txtBankAlias.setText("");	
				txtBankAccountType.setText("");	
				modelDV_acct_entries.setValueAt("01-01-01-002", 1, 0);
				modelDV_acct_entries.setValueAt("Cash on Hand - Disbursement", 1, 1);
				lblCheckNo.setEnabled(false);	
				txtCheckNo.setEnabled(false);
				txtCheckNo.setEditable(false);
				txtCheckNo.setText("");
				lblDateNeeded.setEnabled(false);	
				dteCheck.setEnabled(false);	

				lblCheckPayee.setEnabled(false);	
				lookupChkPayee.setEnabled(false);	
				tagCheckPayee.setEnabled(false);
			}

		}

		lookupBankAcctNo.setLookupSQL(getBankAcct());
		//lookupChkPayee.setLookupSQL(RequestForPayment.getEntityList());
		lookupChkPayee.setLookupSQL(getEntityList());

		if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "15")==true)// fro H.O. Voucher preparation
		{dteDV.setEnabled(true);lblDV_date.setEnabled(true);} else {dteDV.setEnabled(false);lblDV_date.setEnabled(false);}

	}

	private void edit(){

		to_do = "edit";
		enable_fields_to_addNew();
		lblDV_no.setEnabled(false);	
		lookupDV_no.setEnabled(false);	
		//lookupDV_no.setEditable(false);	
		enableButtons(false, false, false, false, true, true, false, false, false, true );

		lblCheckPayee.setEnabled(true);	
		lookupChkPayee.setEnabled(true);	
		//lookupChkPayee.setEditable(true);		
		tagCheckPayee.setEnabled(true);	

		//lblPaymentType.setEnabled(true);	
		//lookupPaymentType.setEnabled(true);	
		//lookupPaymentType.setEditable(true);	
		tagPmtType.setEnabled(true);	

		if (lookupPaymentType.getText().equals("B")) 
		{
			txtCheckNo.setEnabled(true);
			txtCheckNo.setEditable(true);		
			lblBankNo.setEnabled(true);
			lookupBankAcctNo.setEnabled(true);	
			//lookupBankAcctNo.setEditable(true);	
			lookupBankAcctNo.setEnabled(true);
			dteCheck.setEnabled(true);
		}

		else if (lookupPaymentType.getText().equals("A")) 
		{
			txtCheckNo.setEnabled(false);
			txtCheckNo.setEditable(false);		
			lblBankNo.setEnabled(false);
			lookupBankAcctNo.setEnabled(false);	
			//lookupBankAcctNo.setEditable(false);	
			lookupBankAcctNo.setEnabled(false);
			dteCheck.setEnabled(false);
		}

		//lookupPaymentType.setLookupSQL(getPayment_type());
		lookupBankAcctNo.setLookupSQL(getBankAcct());
		//lookupChkPayee.setLookupSQL(RequestForPayment.getEntityList());
		lookupChkPayee.setLookupSQL(getEntityList());
	}

	private void tag(String status, String to_do){

		if (JOptionPane.showConfirmDialog(getContentPane(), to_do + " DV entries?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String dv = lookupDV_no.getText().trim();

			pgUpdate db = new pgUpdate();
			tagCV(dv, db, status);
			insertAudit_trail("Tag-Check Voucher", "Tag cv", db);
			db.commit();			
			enable_fields(false);								
			tabCenter.setSelectedIndex(0);				
			JOptionPane.showMessageDialog(null,"Disbursement Voucher (DV) " + to_do +"gged.","Information",JOptionPane.INFORMATION_MESSAGE);
			if (to_do.equals("Tag")) {enableButtons(false, false, true, true, true, false, false, true, true, true );}
			else if (to_do.equals("Untag")) {enableButtons(false, true, true, true, true, false, true, false, false, true );}
		}		
	}

	private void delete(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this DV entries?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Delete DV", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String dv = lookupDV_no.getText().trim();
				pgUpdate db = new pgUpdate();
				tagCV(dv, db, "D");
				tagPV(dv, db);
				insertAudit_trail("Delete-Check Voucher and Remove-CV in PV", "Delete cv", db);
				db.commit();			
				enable_fields(false);								
				tabCenter.setSelectedIndex(0);				
				JOptionPane.showMessageDialog(null,"Disbursement Voucher (DV) deleted","Information",JOptionPane.INFORMATION_MESSAGE);
				enableButtons(false, false, false, true, true, false, false, false, false, false );
				txtStatus.setText("DELETED");

			} else {}
		} else {}


	}

	private void post(){

		getTransMonthYear();//---added by jed no dcrf to check if month is open---//

		if (isYearMonthOpen(year, month)==true){//---added by jed no dcrf to check if month is open---//	

			if (JOptionPane.showConfirmDialog(getContentPane(), "Post DV entries?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String dv = lookupDV_no.getText().trim();

				pgUpdate db = new pgUpdate();
				postCV(dv, db);
				insertAudit_trail("Post-Check Voucher", "Post cv", db);
				db.commit();			
				enable_fields(false);								
				tabCenter.setSelectedIndex(0);				
				JOptionPane.showMessageDialog(null,"Disbursement Voucher (DV) successfully posted.","Information",JOptionPane.INFORMATION_MESSAGE);
			}

			enableButtons(false, false, true, true, true, false, false, false, false, false ); txtStatus.setText("POSTED"); 
		} 
				else {JOptionPane.showMessageDialog(null,
						"Year [" + year + "] ; " + "Month [" + month + "] is closed." + "\n" +
						"Please ask your Department Head to open.", 
						"Information", 
						JOptionPane.WARNING_MESSAGE);
		}

	}

	private void cancel(){

		if(!btnSave.isEnabled())  {
			enable_fields(false);
			refresh_tablesMain();
			refresh_fields();			
			lblDV_no.setEnabled(true);	
			lookupDV_no.setEnabled(true);	
			//lookupDV_no.setEditable(true);	
			mnidelete.setEnabled(false);
			mniaddrow.setEnabled(false);
			tabCenter.setSelectedIndex(0);
			lookupDV_no.setText("");
			payee = "";
			enableButtons(true, false, false, false, false, false, false, false, false, false );
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				enable_fields(false);
				refresh_tablesMain();
				refresh_fields();			
				lblDV_no.setEnabled(true);	
				lookupDV_no.setEnabled(true);
				//lookupDV_no.setEditable(true);	
				mnidelete.setEnabled(false);
				mniaddrow.setEnabled(false);
				tabCenter.setSelectedIndex(0);
				lookupDV_no.setText("");
				payee = "";
				enableButtons(true, false, false, false, false, false, false, false, false, false );
			}}
		tabCenter.setSelectedIndex(0);
		remarks = "";
	}

	private void refresh(){

		cv_no = lookupDV_no.getText().trim();
		refresh_fields();
		refresh_tablesMain();
		setDV_header(cv_no);
		displayDV_account_entries(modelDV_acct_entries, rowHeaderDV_acct_entries, modelDV_accttotal, cv_no );	
		displayDV_pv(modelDV_pv, rowHeaderDV_pv, modelDV_pvtotal, cv_no );	

		String status = txtStatus.getText().trim();
		if(status.equals("ACTIVE")) 
		{enableButtons(false, true, true, true, true, false, true, false, false, true ); } 
		else if (status.equals("TAGGED")) 
		{ enableButtons(false, false, true, true, true, false, false, true, true, true );}
		else if (status.equals("POSTED")) 
		{ enableButtons(false, false, true, true, true, false, false, false, false, false );}
		else 
		{ enableButtons(false, false, false, true, true, false, false, false, false, false );}

		mnidelete.setEnabled(false);
		mniaddrow.setEnabled(false);
		JOptionPane.showMessageDialog(null,"Refreshed","Information",JOptionPane.INFORMATION_MESSAGE);


	}

	private void save(){

		getTransMonthYear();

		if (isYearMonthOpen(year, month)==true){	


			if(checkCompleteDetails()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete request details.", "Information", 
					JOptionPane.WARNING_MESSAGE);}
			else {			

				if(checkNetAmount()==false)
				{if (JOptionPane.showConfirmDialog(getContentPane(), "CV amount is equals to zero, proceed anyway?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {execute_save();}
				}
				else {execute_save();}
			}
		}

		else {JOptionPane.showMessageDialog(null,
				"Year [" + year + "] ; " + "Month [" + month + "] is closed." + "\n" +
				"Please ask your Department Head to open.", 
				"Information", 
				JOptionPane.WARNING_MESSAGE);
		}
	}

	private void preview_check(){

		String criteria = "Check";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Double pv_amt	= Double.parseDouble(modelDV_accttotal.getValueAt(0,3).toString());	
		
		String check_payee = "";
		if(sql_getDefaultBroker(payee).equals(""))
		{check_payee  = payee_name.trim().toUpperCase();} else
		{check_payee  = sql_getDefaultBroker(payee).toUpperCase();}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("pv_date", dateFormat.format(dteCheck.getDate()));
		mapParameters.put("pv_amt", nf.format(pv_amt));
		mapParameters.put("payee", check_payee.trim().toUpperCase());

		if (txtBankAlias.getText().trim().equals("AUB")) {
			FncReport.generateReport("/Reports/rptCV_checkAUB.jasper", reportTitle, company, mapParameters);
		}
		else if (txtBankAlias.getText().trim().equals("ABC")) {
			FncReport.generateReport("/Reports/rptCV_checkAllied.jasper", reportTitle, company, mapParameters);
		}
		else if (txtBankAlias.getText().trim().equals("MBTC")) {
			FncReport.generateReport("/Reports/rptCV_checkAUB.jasper", reportTitle, company, mapParameters);
		}
		/*	Added by Mann2x; Date Added: March 26, 2018;	*/
		else {
			FncReport.generateReport("/Reports/rptCV_checkAUB.jasper", reportTitle, company, mapParameters);
		}



	}

	private void preview_CV(){
		
		emp_code = UserInfo.EmployeeCode;

		String criteria = "Disbursement Voucher";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Double dv_amt	= Double.parseDouble(modelDV_accttotal.getValueAt(0,3).toString());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("cv_no", lookupDV_no.getText().trim());
		mapParameters.put("co_id", lookupCompany.getText());
		mapParameters.put("bank_alias", txtBankAlias.getText().trim());
		mapParameters.put("check_no", txtCheckNo.getText().trim());
		mapParameters.put("check_no", txtCheckNo.getText().trim());
		mapParameters.put("due_date", dteCheck.getDate());
		mapParameters.put("dv_amt", dv_amt);
		mapParameters.put("dv_date", dteDV.getDate());
		mapParameters.put("payee_", sql_getPV_payee().toUpperCase());		
		mapParameters.put("prepared_by", sql_getCV_preparedBy());
		
		if(checkEmployee(emp_code).equals(null) || checkEmployee(emp_code).equals("")){
			FncReport.generateReport("/Reports/rptCV_preview_mc.jasper", reportTitle, company, mapParameters);
		}else {
			FncReport.generateReport("/Reports/rptCV_preview_mc_v2.jasper", reportTitle, company, mapParameters);
		}
	}
	
	private static String checkEmployee(String emp_code){

		String employee_code = "";
		String sql =
				"select\n" + 
						"emp_code\n" + 
						"from em_employee\n" + 
						"where emp_code = '"+emp_code+"' and\n" + 
						"div_code = '05' and \n" + 
						"dept_code = '18'" ;

		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()){
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")){
			}else{
				employee_code = (String) db.getResult()[0][0].toString();
				System.out.printf("The employee code is: %s", employee_code);
			}
		}else{
			employee_code = "";
		}
		return employee_code;
	}

	private void execute_save(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String dv = lookupDV_no.getText().trim();

			if(to_do.equals("addnew")) {							
				next_cv_no = sql_getNextCVno();
				pgUpdate db = new pgUpdate();	
				insertCV_header(next_cv_no, db);
				insertCV_detail(next_cv_no, db);
				updatePV_header(next_cv_no, db);
				insertAudit_trail("Add-Check Voucher", next_cv_no, db);

				String pay_type = lookupPaymentType.getText();
				if (pay_type.equals("B")) {						
					insertAP_check(next_cv_no, db);						
					updateCheck_book(db);  } 
				else {}

				db.commit();	
				lookupDV_no.setText(next_cv_no);
				enable_fields(false);						
				tabCenter.setSelectedIndex(0);	
				JOptionPane.showMessageDialog(null,"New Disbursement Voucher (DV) saved.","Information",JOptionPane.INFORMATION_MESSAGE);	
				lblDV_no.setEnabled(true);	
				lookupDV_no.setEnabled(true);	
				mnidelete.setEnabled(false);
				mniaddrow.setEnabled(false);
				enableButtons(false, true, true, true, true, false, true, false, false, true );
			}
			else if (to_do.equals("edit")) {
				pgUpdate db = new pgUpdate();
				updateCV_header(dv, db);
				updateCV_detail(dv, db);
				insertCV_detail(dv, db);
				updatePV_header(dv, db);
				insertAudit_trail("Edit-Check Voucher", dv, db);

				String pay_type = lookupPaymentType.getText();
				if (pay_type.equals("B")) {	
					updateAP_check(dv,db);
					updateCheck_book(db); }
				else {}

				db.commit();	
				lookupDV_no.setText(dv);
				enable_fields(false);								
				tabCenter.setSelectedIndex(0);								
				JOptionPane.showMessageDialog(null,"Disbursement Voucher (DV) updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				lblDV_no.setEnabled(true);	
				lookupDV_no.setEnabled(true);	
				mnidelete.setEnabled(false);
				mniaddrow.setEnabled(false);
				enableButtons(false, true, true, true, true, false, true, false, false, true );
			}
			else if (to_do.equals("editdatepaid")) {
				pgUpdate db = new pgUpdate();
				updateCV_headerDatePaid(dv, db);
				insertAudit_trail("Edit-CV Date Paid", dv, db);
				db.commit();	
				lblDatePaid.setEnabled(false);	
				dtePaid.setEnabled(false);
				JOptionPane.showMessageDialog(null,"Date paid updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				enableButtons(false, false, false, false, false, false, false, false, false, false );
			}
		}


	}


	//select, lookup and get statements	
	public static String getEntityList(){//--added by JED 2018-11-13--//

		String sql = 
			"select " +
			"trim(entity_id) as \"Entity ID\",  \n" +
			"trim(entity_name) as \"Name\", \n" +
			"entity_kind as \"Kind\",  \n" +
			"vat_registered as \"VAT\"  \n" +
			"from (" +
			"(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where status_id = 'A' limit 3000) union \n" +
			"(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in (select entity_id  from em_employee)) union \n" +
			"(select entity_id, entity_name, entity_kind, vat_registered from rf_entity)) a \n" +
			"order by a.entity_name  ";		
		return sql;

	}
	
	public static String getDV_no(String co_id){//used
		return 
		"select \r\n" + 
		"distinct on (a.cv_no,a.co_id) " +

		"a.cv_no  as \"CV No.\" ,\r\n" + 
		"a.co_id  as \"Comp ID\",\r\n" + 
		"c.tran_amt  as \"Amount\" ,\r\n" + 
		"a.cv_date   as \"CV Date\" ,\r\n" + 
		"trim(b.entity_name) as \"Payee\", \r\n" + 	
		"d.status_desc as \"Status\"  " +

		"from\r\n" + 
		"(select * from rf_cv_header where /*coalesce(remarks, '') ~* 'Released through AUB E-payment Facility' and */ co_id = '"+co_id+"' " +
		"		and (case when '"+isCurrentYear+"' = '' then true else to_char(cv_date,'yyyy') = '"+isCurrentYear+"' end) \r\n" + 
		"		) a\r\n" + 
		"left join rf_entity b on a.entity_id1=b.entity_id\r\n" + 
		"left join rf_cv_detail c on a.cv_no = c.cv_no and a.co_id = c.co_id " +
		"left join mf_record_status d on a.status_id = d.status_id  " +		
		"order by a.cv_no desc "  ;

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
			"		b.acct_id,\r\n" + 
			"		a.from_check_no,\r\n" + 
			"		a.to_check_no, \n" +
			"		a.last_no_used \r\n" + 
			"\r\n" + 
			"		from ( select * from rf_check_book where rec_id in ( select rec_id from rf_check_book where trim(user_id) = '"+UserInfo.EmployeeCode+"' and status_id = 'A' ) ) a\r\n" + 
			"		left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id  \r\n" + 
			"		left join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id  \r\n" + 
			"		left join mf_bank d on c.bank_id = d.bank_id   \r\n" ;

		if (rplf_type.equals("04")||rplf_type.equals("05")) {sql = sql + "where b.is_comm_disb = true" ;} //from 07 changed to 05 ; Del Gonzales : 03/15/2017
		else {sql = sql + "where b.is_comm_disb is null or b.is_comm_disb is false  " ;}

		sql = sql +

		"		\r\n" + 
		"		order by a.bank_acct_id \r\n" ;	

		System.out.printf("getBankAcct() sql :" + sql);
		return sql;

	}

	public String getPV_list(){//used

		int rw = tblDV_pv.getModel().getRowCount();	
		int x = 0;
		String pv = "";

		String sql = 
			"select \r\n" + 
			"a.pv_no as \"PV No.\" , \r\n" +    //0
			"a.pv_date  as \"PV Date\"  ,\r\n" +//1 
			"b.pv_amt   as \"PV Amount\",  \r\n" + //2
			"trim(c.entity_name) as \"Payee\"," +  //3 
			"trim(cc.entity_name) as \"Availer\", " +  //4
			"a.pay_type_id as \"Type\" ," + //5
			"d.rplf_type_id as \"Request Type\" \n"  + //6

			"from (select * from rf_pv_header where status_id = 'P' and co_id = '"+co_id+"' and proc_id = '3'  " ;
		if (payee.equals("")) {} else {sql = sql + "and entity_id1 = '"+payee+"' and pay_type_id = '"+lookupPaymentType.getText()+"' " ; } 

		while (x < rw) {				

			pv	= modelDV_pv.getValueAt(x,0).toString().trim();

			if (pv.equals(""))  {}
			else {sql = sql + "and pv_no != '"+pv+"'" ;}			
			x++;
		}

		sql = sql +
		") a \r\n" + 
		"left join ( select distinct on (co_id, pv_no) co_id, pv_no, sum(tran_amt) as pv_amt\r\n" + 
		"	from rf_pv_detail where acct_id in ('22010001', '03-01-01-001') and status_id = 'A' group by co_id, pv_no ) b on a.pv_no = b.pv_no and a.co_id = b.co_id\r\n" + 
		"left join rf_entity c on a.entity_id1 = c.entity_id \r\n" +
		"left join rf_entity cc on a.entity_id2 = cc.entity_id \r\n" +
		"left join (select * from rf_request_header where co_id = '"+co_id+"') d on a.pv_no = d.rplf_no \r\n" + 
		"where trim(a.cv_no) not in (select cv_no from rf_cv_header where status_id != 'D' and co_id = '"+co_id+"')  " +
		"order by a.pv_no \r\n" ;	

		System.out.println("sql: "+sql);
		
		return sql;
	}

	public String sql_getAcctDesc() {//used	

		String acct_name = "";

		String SQL = 
			"select trim(acct_name) from mf_boi_chart_of_accounts where acct_id = '"+acct_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			acct_name = (String) db.getResult()[0][0];
		}else{
			acct_name = null;
		}

		return acct_name;
	}

	public String sql_getNextCheckNo(String rec_id) {//used

		String nxt_chk_no = "";

		String SQL = 
			"select ( \r\n" + 
			"case when last_no_used is null or last_no_used = '' then trim(from_check_no) else    \r\n" + 
			"	( repeat('0', length(max(from_check_no))  -  \r\n" + 
			"	length((max(case when trim(last_no_used) = '' then 0 else   trim(last_no_used)::bigint end)+1)::text) ) || \r\n" + 
			"	max(case when trim(last_no_used) = '' then 0 else   trim(last_no_used)::bigint end) +1 ) \r\n" + 
			"end ) " +
			"from rf_check_book where rec_id = "+rec_id+" and trim(user_id) = '"+UserInfo.EmployeeCode+"' " +
			"and status_id = 'A' " +
			"group by last_no_used, from_check_no   \r\n"  ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			nxt_chk_no = (String) db.getResult()[0][0];
		}else{
			nxt_chk_no = null;
		}

		return nxt_chk_no;
	}

	public String sql_getLastCheckNo(String rec_id) {//used

		String last_chk_no = "";

		String SQL = 
			"select trim(to_check_no) " +
			"from rf_check_book where rec_id = "+rec_id+" and trim(user_id) = '"+UserInfo.EmployeeCode+"' " +
			"and status_id = 'A'   \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			last_chk_no = (String) db.getResult()[0][0];
		}else{
			last_chk_no = null;
		}

		return last_chk_no;
	}

	public String sql_getLastCheckNoUsed(String rec_id) {//used

		String last_chk_no = "";

		String SQL = 
			"select trim(coalesce(last_no_used,'')) " +
			"from rf_check_book where rec_id = "+rec_id+" and trim(user_id) = '"+UserInfo.EmployeeCode+"' " +
			"and status_id = 'A'   \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			last_chk_no = (String) db.getResult()[0][0];
		}else{
			last_chk_no = null;
		}

		return last_chk_no;
	}

	public static Object [] getDVdetails(String req_no) {//used

		String strSQL = 
			"---display DV header\r\n" + 
			"select \r\n" + 

			"a.cv_no,\r\n" +  //0
			"a.cv_date,\r\n" +  //1
			"a.date_paid,\r\n" +   //2 
			"trim(f.entity_name) as payee1,\r\n" + //  3  : 03-11-2016 - actual check payee based on rf_check table
			"trim(c.entity_name) as payee2,\r\n" +   //4
			"coalesce(d.check_no, ''),\r\n" +   //5
			"coalesce(d.chk_amt::numeric,k.cv_amt),\r\n" +   //6
			"e.entity_type_desc,\r\n" +   //7
			"a.entity_type_id,\r\n" +   //8
			"( case when a.pay_type_id = 'A' then 'CASH' else 'CHECK' end ) as payment_type,\r\n" +   //9
			"trim(f.entity_name) as ch_payee,\r\n" +   //10
			"coalesce(d.bank_acct_id,''),\r\n" +   //11
			"coalesce(g.bank_acct_no,''),\r\n" +   //12
			"coalesce(g.acct_desc,''),\r\n" +   //13
			"( case when fund_class_id = '01' then 'COLLECTING ACCOUNT' else \r\n" +   
			"	case when fund_class_id = '02' then 'DISBURSING ACCOUNT' else\r\n" +   
			"		case when fund_class_id = '03' then 'MONEY MARKET PLACEMENT ACCOUNT' end end end) as bank_acct_type,\r\n" +   //14
			"coalesce(i.bank_alias,''),\r\n" +   //15
			"j.status_desc, \r\n" + //16
			"(case when d.entity_id is null then a.entity_id1 else d.entity_id end) ,\r\n" + 	//17  03-11-2016 - actual check payee (rf_check)
			"a.entity_id2,\r\n" + 	//18
			"a.entity_id1,  \n" + 	//19
			"a.pay_type_id," +		//20
			"a.remarks,    \n" + 	//21
			"d.date_due    \n" + 	//22

			"from rf_cv_header a\r\n" + 
			"left join rf_entity b on a.entity_id1= b.entity_id\r\n" + 
			"left join rf_entity c on a.entity_id2= c.entity_id\r\n" + 
			"left join rf_check d on a.cv_no = d.cv_no and a.co_id = d.co_id \r\n" + 
			"left join mf_entity_type e on a.entity_type_id = e.entity_type_id\r\n" + 
			"left join rf_entity f on (case when d.entity_id is null then a.entity_id1 else d.entity_id end)= f.entity_id\r\n" + 
			"left join mf_bank_account g on d.bank_acct_id = g.bank_acct_id\r\n" + 
			"left join mf_bank_branch h on g.bank_branch_id = h.bank_branch_id\r\n" + 
			"left join mf_bank i on h.bank_id = i.bank_id\r\n" + 
			"left join mf_record_status j on a.status_id = j.status_id  " +
			"left join (select cv_no, co_id, sum(tran_amt) as cv_amt from rf_cv_detail where bal_side = 'D' \n" +
			"	and acct_id = '03-01-01-001' and status_id = 'A' group by cv_no, co_id) k on a.cv_no = k.cv_no and a.co_id = k.co_id     \r\n" + 

			"where a.cv_no = '"+cv_no+"' and a.co_id = '"+co_id+"'\r\n"  ;
		
		
		FncSystem.out("SQL for DV header", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	public Object [] getPVdetails(String req_no) {//used

		String strSQL = 
			"select \r\n" + 

			"a.entity_id1,\r\n" + 
			"trim(b.entity_name),\r\n" + 
			"a.entity_id2,\r\n" + 
			"trim(c.entity_name),\r\n" + 
			"a.entity_type_id,\r\n" + 
			"d.entity_type_desc,\r\n" +
			"trim(a.remarks)," +
			"e.rplf_type_id  \r\n" + 

			"from \r\n" + 
			"rf_pv_header a\r\n" + 
			"left join rf_entity b on a.entity_id1 = b.entity_id\r\n" + 
			"left join rf_entity c on a.entity_id2 = c.entity_id\r\n" + 
			"left join mf_entity_type d on a.entity_type_id = d.entity_type_id\r\n" + 
			"left join rf_request_header e on a.pv_no = e.rplf_no \r\n" + 

			"where a.pv_no = '"+req_no+"'\r\n" + 
			"and a.co_id = '"+co_id+"'" 
			;


		System.out.printf("getPVdetails() sql :" + strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	private String sql_getNextCVno() {//used

		String SQL = 
			"select trim(to_char(max(coalesce(cv_no::int,0))+1,'000000000')) from rf_cv_header where co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {next_cv_no = "000000001";}
			else {next_cv_no = (String) db.getResult()[0][0]; }
		}else{
			next_cv_no = "000000001";
		}

		return next_cv_no;
	}

	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = 
			"select company_logo from mf_company \n" + 
			"where co_id = '"+co_id+"' " ;

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {a = "";}
			else {a = (String) db.getResult()[0][0]; }

		}else{
			a = "";
		}

		return a;
	}

	public String sql_getPreparedBy() {//used

		String last_chk_no = "";

		String SQL = 
			"select\r\n" + 
			"\r\n" + 
			"'/'||trim(h.entity_name) as prepared_by\r\n" + 
			"\r\n" + 
			"from rf_cv_header a \r\n" + 
			"left join em_employee g on (case when a.edited_by is null or a.edited_by = '' then a.created_by else a.edited_by end) = g.emp_code\r\n" + 
			"left join rf_entity h on g.entity_id = h.entity_id\r\n" + 
			"\r\n" + 
			"where a.cv_no = '"+cv_no+"'\r\n" + 
			"and a.status_id != 'I'\r\n" + 
			"\r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			last_chk_no = (String) db.getResult()[0][0];
		}else{
			last_chk_no = null;
		}

		return last_chk_no;
	}

	public String sql_getPV_payee() {//used

		String pv_payee = "";

		String SQL = 
			"select distinct on (a.cv_no)\r\n" + 
			"\r\n" + 
			"trim(b.entity_name) \r\n" + 
			"\r\n" + 
			"from rf_pv_header a \r\n" + 
			"left join rf_entity b on a.entity_id1 = b.entity_id \r\n" + 
			"\r\n" + 
			"where a.cv_no = '"+lookupDV_no.getText()+"'\r\n" + 
			"and a.status_id != 'I'\r\n" + 
			"\r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			pv_payee = (String) db.getResult()[0][0];
		}else{
			pv_payee = null;
		}

		return pv_payee;
	}

	public String sql_getCV_preparedBy() {//used

		String prep_by = "";

		String SQL = 
			"select\r\n" + 
			"\r\n" + 
			"'/'||trim(c.entity_name) \r\n" + 
			"\r\n" + 
			"from rf_cv_header a \r\n" + 
			"left join em_employee b on (case when a.edited_by is null or a.edited_by = '' then a.created_by else a.edited_by end) = b.emp_code \r\n" + 
			"left join rf_entity c on b.entity_id = c.entity_id \r\n" + 
			"where a.cv_no = '"+cv_no+"'\r\n" + 
			"and a.status_id != 'I'\r\n" + 
			"\r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			prep_by = (String) db.getResult()[0][0];
		}else{
			prep_by = null;
		}

		return prep_by;
	}

	private Boolean isYearMonthOpen(String year, String month){

		Boolean isOpen = false;		

		String SQL = 
			"select\r\n" + 
			"\r\n" + 
			"(case when status_id = 'A' then true else false end) as open \r\n" + 
			"from mf_acctg_period\r\n" + 
			"where period_year = '"+year+"'  " +
			"and month_code = '"+month+"' " +
			"and co_id = '"+co_id+"' " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			isOpen = (Boolean) db.getResult()[0][0];
		}else{
			isOpen = false;
		}

		return isOpen;

	}
	
	private String sql_getDefaultBroker(String agent) {

		String def_broker = "";

		String SQL = 
			"select broker_name from mf_brokers_details where agent_id = '"+agent+"' \n" + 
			"and default_check_payee = true and status_id = 'A'" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {def_broker = "";}
			else {def_broker = (String) db.getResult()[0][0]; }

		}else{
			def_broker = "";
		}

		return def_broker;
	}

	//check and validate		
	private Boolean checkCompleteDetails(){//used

		boolean x = false;		

		String pay_type = lookupPaymentType.getText();
		if (pay_type.equals("B"))
		{ if (lookupPaymentType.getText().trim().equals("") || lookupBankAcctNo.getText().trim().equals("")) {x=false;} 
		else {x=true;}	}
		else if (pay_type.equals("A"))
		{ if (lookupPaymentType.getText().trim().equals("")) {x=false;} 
		else {x=true;}	}

		return x;

	}

	private Boolean checkNetAmount(){//used

		boolean x = false;		

		Double net_amt 	= Double.parseDouble(modelDV_accttotal.getValueAt(0,3).toString());	
		if (net_amt>0) {x=true;} 
		else if (net_amt<0) {x=false;} 
		else {x=false;}

		return x;

	}	

	public Boolean isPVcreated(){

		Boolean isPVcreated = false;

		String SQL = 
			"select trim(status_id) from rf_pv_header " +
			"where pv_no = '"+drf_no+"' and status_id = 'A' and co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			isPVcreated = true;
		}else{
			isPVcreated = false;
		}

		return isPVcreated;

	}

	private void getTransMonthYear(){
		
		DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");					
		String month_sub  = df.format(dteDV.getDate());	
		month 			 = month_sub.substring(8);		
		DateFormat df2   = new SimpleDateFormat("MM-dd-yyyy");	
		String year_sub 	= df2.format(dteDV.getDate());	
		year 			= year_sub.substring(6);
		
	}
	

	//table operations
	public static void totalDV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal debit 	= new BigDecimal(0.00);	
		BigDecimal credit  	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { debit 	= debit.add(((BigDecimal) modelMain.getValueAt(x,2)));} 
			catch (NullPointerException e) { debit 	= debit.add(new BigDecimal(0.00)); }

			try { credit 	= credit.add(((BigDecimal) modelMain.getValueAt(x,3)));} 
			catch (NullPointerException e) { credit 	= credit.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null , debit, credit });
	}

	public static void totalDV_pv(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal pv_total 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { pv_total 	= pv_total.add(((BigDecimal) modelMain.getValueAt(x,2)));} 
			catch (NullPointerException e) { pv_total 	= pv_total.add(new BigDecimal(0.00)); }			
		}		

		modelTotal.addRow(new Object[] { "Total", null,  pv_total });
	}

	private void clickTableColumn() {//used
		int column = tblDV_pv.getSelectedColumn();
		int row = tblDV_pv.getSelectedRow();	

		Integer x[] = {0,1,2};
		String sql[] = {getPV_list(),"",""};
		String lookup_name[] = {"Payable Voucher","",""};			

		if (column == x[column] && modelDV_pv.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0) {	
				modelDV_pv.setValueAt(data[0], row, column);
				modelDV_pv.setValueAt(data[1], row, column+1);
				modelDV_pv.setValueAt(data[2], row, column+2);
				totalDV_pv(modelDV_pv, modelDV_pvtotal);
				AddRow();

				Double amount	= Double.parseDouble(modelDV_pvtotal.getValueAt(0,2).toString());	
				txtCheckAmt.setText(nf.format(amount));	

				Object[] pv_hdr = getPVdetails((String) data[0]);	
				payee 		= (String) pv_hdr[0];
				payee_name 	= (String) pv_hdr[1];
				String rem  = (String) pv_hdr[6];

				lookupChkPayee.setText((String) pv_hdr[0]);
				tagCheckPayee.setTag((String) pv_hdr[1]);
				tagCheckPayee.setTag((String) pv_hdr[1]);	
				lookupPayeeType.setText((String) pv_hdr[4]);
				tagPayeeType.setTag((String) pv_hdr[5]);	
				AddRow_acctEntries(amount);

				txtDV_particular.setText(setPVRemarks(rem));

				if(data[4].equals("B")) 
				{
					//lookupPaymentType.setValue("B");
					//tagPmtType.setTag("CHECK");
					lblBankNo.setEnabled(true);	
					lookupBankAcctNo.setEnabled(true);
					modelDV_acct_entries.setValueAt("", 1, 0);
					modelDV_acct_entries.setValueAt("", 1, 1);
					lblCheckNo.setEnabled(true);	
					txtCheckNo.setEnabled(true);
					txtCheckNo.setEditable(true);
					lblDateNeeded.setEnabled(true);	
					dteCheck.setEnabled(true);	
				}
				else if(data[4].equals("A")) 
				{
					//lookupPaymentType.setValue("A");
					//tagPmtType.setTag("CASH");
					lblBankNo.setEnabled(false);	
					lookupBankAcctNo.setEnabled(false);	
					lookupBankAcctNo.setValue("");	
					txtBankAlias.setText("");	
					txtBankAcct.setText("");	
					txtBankAlias.setText("");	
					txtBankAccountType.setText("");	
					modelDV_acct_entries.setValueAt("01-01-01-002", 1, 0);
					modelDV_acct_entries.setValueAt("Cash on Hand - Disbursement", 1, 1);
					lblCheckNo.setEnabled(false);	
					txtCheckNo.setEnabled(false);
					txtCheckNo.setEditable(false);
					txtCheckNo.setText("");
					lblDateNeeded.setEnabled(false);	
					dteCheck.setEnabled(false);	
				}

			} 
		}		

		else {}
	}	

	private void removeRow(){//used	

		int r  = tblDV_pv.getSelectedRow();

		if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblDV_pv.getModel()).removeRow(r); 
			modelDV_pv.addRow(new Object[] { "", "", new BigDecimal(0.00)});		
			totalDV_pv(modelDV_pv, modelDV_pvtotal);
			Double amount	= Double.parseDouble(modelDV_pvtotal.getValueAt(0,2).toString());	
			txtCheckAmt.setText(nf.format(amount));	
			adjustRowHeight_pv();
		}
	}

	private void AddRow(){//used

		modelDV_pv.addRow(new Object[] { "", "", new BigDecimal(0.00)});
		((DefaultListModel) rowHeaderDV_pv.getModel()).addElement(modelDV_pv.getRowCount());
		totalDV_pv(modelDV_pv, modelDV_pvtotal);
		adjustRowHeight_pv();
	}

	private void AddRow_acctEntries(Double amount){//used

		FncTables.clearTable(modelDV_acct_entries);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderDV_acct_entries.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		modelDV_acct_entries.addRow(new Object[] { "03-01-01-001", "Accounts Payable - Trade", new BigDecimal(amount), new BigDecimal(0.00)});
		listModel.addElement(modelDV_acct_entries.getRowCount());	

		String pay_type = lookupPaymentType.getText();
		if (pay_type.equals("B")) {			
			modelDV_acct_entries.addRow(new Object[] { acct_id, sql_getAcctDesc(), new BigDecimal(0.00), new BigDecimal(amount)});
			listModel.addElement(modelDV_acct_entries.getRowCount());	}
		else if (pay_type.equals("A")) {			
			modelDV_acct_entries.addRow(new Object[] { "01-01-01-002", "Cash on Hand - Disbursement", new BigDecimal(0.00), new BigDecimal(amount)});
			listModel.addElement(modelDV_acct_entries.getRowCount());	}
		else {			
			modelDV_acct_entries.addRow(new Object[] { "", "", new BigDecimal(0.00), new BigDecimal(amount)});
			listModel.addElement(modelDV_acct_entries.getRowCount());	}

		totalDV(modelDV_acct_entries, modelDV_accttotal);
		adjustRowHeight();

	}

	private static void adjustRowHeight(){//used

		int rw = tblDV_acct_entries.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblDV_acct_entries.setRowHeight(x, 22);			
			x++;
		}
	}

	private static void adjustRowHeight_pv(){//used

		int rw = tblDV_pv.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblDV_pv.setRowHeight(x, 22);			
			x++;
		}
	}

	private String setPVRemarks(String rem){

		return

		remarks = remarks + "\n" + rem;


	}


	//save and insert		
	public void insertCV_header(String rec_no, pgUpdate db){//ok

		String pay_type_id = "";
		String entity_id1	= "";
		String entity_id2	= "";
		String ent_type_id 	= "";
		String doc_id		= "";
		Integer proc_id		= null;
		String remarks		= "";	
		String status_id	= "";	
		String created_by	= "";	
		String edited_by	= "";	
		Date edited_date	= null;	

		pay_type_id	= lookupPaymentType.getText().trim();	
		entity_id1  = payee; //03-11-2016  - may not always be the same with lookupChkPayee
		ent_type_id = lookupPayeeType.getText().trim();
		doc_id		= "13";
		proc_id		= 0;			
		remarks		= txtDV_particular.getText().trim().replace("'", "''");	
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	= null;		

		String sqlDetail = 
			"INSERT into rf_cv_header values (" +
			"'"+co_id+"',  \n" +  		//1 co_id
			"'"+co_id+"',  \n" +		//2 busunit_id
			"'"+rec_no+"',  \n" +		//3 cv_no
			"'"+dateFormat.format(dteDV.getDate())+"',  \n" +	//4 cv_date
			"'"+entity_id1+"',  \n" +	//5 entity_id1
			"'"+entity_id2+"',  \n" +	//6 entity_id2
			"'"+ent_type_id+"',  \n" +	//7 entity_type_id
			"null,  \n" + 				//8 date_paid
			"'"+pay_type_id+"' , \n" +	//9 pay_type_id		
			"0,  \n" + 					//10 print_counter
			"null,  \n" + 				//11 printed_by
			"'"+doc_id+"' , \n" +		//12 doc_id	
			""+proc_id+",  \n" +		//13 proc_id
			"null,  \n" + 				//14 date_posted
			"'',  \n" + 				//15 posted_by
			"'"+remarks+"' , \n" +		//16 remarks
			"'"+status_id+"' , \n" +	//17 status_id
			"'"+created_by+"',  \n" +	//18 created_by
			"now(),  \n" +				//19 date_created
			"'"+edited_by+"' , \n" +	//20 edited_by
			""+edited_date+" \n" +		//21 date_edited	 


			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		


	}

	public void insertCV_detail(String rec_no, pgUpdate db){//ok

		int rw = tblDV_acct_entries.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {		

			String bal_side = "D";
			Double debit	= Double.parseDouble(modelDV_acct_entries.getValueAt(x,2).toString());	
			Double credit	= Double.parseDouble(modelDV_acct_entries.getValueAt(x,3).toString());	
			Double total = debit + credit;

			if(debit>0) {bal_side="D";} else {bal_side="C";}

			String acct_id		= "";					
			acct_id		= modelDV_acct_entries.getValueAt(x,0).toString().trim();

			String sqlDetail = 
				"INSERT into rf_cv_detail values (" +
				"'"+co_id+"',  \n" +  		//1 co_id
				"'"+co_id+"',  \n" +		//2 busunit_id
				"'"+rec_no+"',  \n" +		//3 cv_no
				""+total+",  \n" +			//4 tran_amt
				"'"+bal_side+"',  \n" + 	//5	bal_side		
				"'"+acct_id+"',  \n" +		//6 acct_id
				"null,  \n" +				//7 old_acct_id
				"'A'  \n" +					//8 status_id
				")   " ;

			System.out.printf("SQL #1: %s", sqlDetail);

			db.executeUpdate(sqlDetail, false);	

			x++;
		}

	}		

	public void insertAP_check(String rec_no, pgUpdate db){//ok

		String check_no		= "";
		String bank_acct_id = "";
		String entity_id1	= "";
		Double chk_amt 		= null;

		check_no	= txtCheckNo.getText().trim();
		chk_amt		= Double.valueOf(txtCheckAmt.getText().trim().replace(",","")).doubleValue();
		bank_acct_id= lookupBankAcctNo.getText().trim();
		entity_id1	= lookupChkPayee.getText().trim();

		String sqlDetail = 
			"INSERT into rf_check values (" +			
			"'"+co_id+"',  \n" +  		//1 co_id
			"'"+co_id+"',  \n" +		//2 busunit_id	
			"'"+rec_no+"',  \n" +		//3 cv_no
			"'"+check_no+"',  \n" + 	//4 check_no
			"'"+dateFormat.format(dteCheck.getDate())+"',  \n" +	//5 date_due
			"'"+bank_acct_id+"',  \n" +	//6 bank_acct_id
			""+chk_amt+" , \n" +		//7	chk_amt	
			"'"+entity_id1+"',  \n" +	//8 03-11-2016 : entity_id (not PV's entity_id1, processor may change)
			"'A'  \n" +					//9 status_id			
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}
	
	private void insertAudit_trail(String activity, String remarks, pgUpdate db){

		String user_code	= UserInfo.EmployeeCode;	

		String sqlDetail = 
				"INSERT INTO mf_audit_trail(\n" + 
				"	              system_id, activity, user_code, date_upd, entity_id, \n" + 
				"	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n" + 
				"	      VALUES ('CV', '"+activity+"', '"+user_code+"', NOW(), NULL, \n" + 
				"	              NULL, NULL, NULL, NULL, NULL, '"+remarks+"');" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		


	}

	public void updatePV_header(String rec_no, pgUpdate db) {//ok

		int rw = tblDV_pv.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {	

			String pv_no= modelDV_pv.getValueAt(x,0).toString();	

			String sqlDetail = 
				"update rf_pv_header set cv_no = '"+rec_no+"' where trim(pv_no) = '"+pv_no+"' and co_id = '"+co_id+"'   " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	

			x++;
		}
	}

	public void updateCheck_book(pgUpdate db) {//ok

		String sqlDetail = 
			"update rf_check_book set last_no_used = '"+txtCheckNo.getText().trim()+"' " +
			"where trim(bank_acct_id) = '"+lookupBankAcctNo.getText().trim()+"' and trim(user_id) = '"+UserInfo.EmployeeCode+"' " +
			"and status_id = 'A' " ;

		db.executeUpdate(sqlDetail, false);	

	}

	public void updateCV_detail(String rec_no, pgUpdate db) {//ok

		String sqlDetail = 
			"update rf_cv_detail set status_id = 'I' where trim(cv_no) = '"+rec_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);			

	}

	public void updateCV_headerDatePaid(String cv_no, pgUpdate db){//ok	

		String sqlDetail = 
			"update rf_cv_header set " +	
			"proc_id = 5,  " +
			"date_paid = '"+dateFormat.format(dtePaid.getDate())+"',  " +
			"edited_by = '"+UserInfo.EmployeeCode+"'," +
			"date_edited = now()  \n" +	//4		
			"where cv_no = '"+cv_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	public void updateCV_header(String cv_no, pgUpdate db){//ok	

		String pay_type_id = "";
		String entity_id1	= "";
		String entity_id2	= "";
		String ent_type_id 	= "";
		String remarks		= "";	
		String edited_by	= "";			

		pay_type_id	= lookupPaymentType.getText().trim();
		entity_id1	= lookupChkPayee.getText().trim();
		ent_type_id = lookupPayeeType.getText().trim();	
		remarks		= txtDV_particular.getText().trim().replace("'", "''");	
		edited_by	= UserInfo.EmployeeCode;			

		String sqlDetail = 
			"update rf_cv_header set " +
			"co_id = '"+co_id+"',  \n" +  		//1
			"busunit_id = '"+co_id+"',  \n" +	//2			
			"cv_no = '"+cv_no+"',  \n" +		//3
			"cv_date = '"+dateFormat.format(dteDV.getDate())+"',  \n" +	//4
			"entity_id1 = '"+entity_id1+"',  \n" +	//5
			"entity_id2 = '"+entity_id2+"',  \n" +	//6
			"entity_type_id = '"+ent_type_id+"',  \n" +	//7
			"pay_type_id = '"+pay_type_id+"' , \n" +	//8	
			"remarks = '"+remarks+"' , \n" +		//9
			"edited_by = '"+edited_by+"' , \n" +	//10
			"date_edited = now() \n" +		//11				
			"where cv_no = '"+cv_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	public void updateAP_check(String rec_no, pgUpdate db) {//ok

		String sqlDetail = 
			"update rf_check set check_no = '"+txtCheckNo.getText().trim()+"',  \n" +
			"bank_acct_id = '"+lookupBankAcctNo.getText().trim()+"', \n" +
			"chk_amt = "+Double.valueOf(txtCheckAmt.getText().trim().replace(",","")).doubleValue()+", \n" +
			"date_due = '"+dateFormat.format(dteCheck.getDate())+"',  \n" +
			"entity_id = '"+lookupChkPayee.getText().trim()+"'  \n" +
			"where trim(cv_no) = '"+rec_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}

	public void tagCV(String rec_no, pgUpdate db, String status) {//ok

		String sqlDetail = 
			"update rf_cv_header set " +
			"status_id = '"+status+"', proc_id = 1 " +
			"where trim(cv_no) = '"+rec_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}
	
	public void tagPV(String dv, pgUpdate db) {//ok

		String sqlDetail = 
			"update rf_pv_header set " +
			"cv_no = '' " +
			"where trim(cv_no) = '"+dv+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}

	public void postCV(String rec_no, pgUpdate db) {//ok

		String sqlDetail = 
			"update rf_cv_header " +
			"set status_id = 'P', " +
			"posted_by = '"+UserInfo.EmployeeCode+"' , \n" +	
			"date_posted = now(), \n" +
			"proc_id = 3 \n" +		
			"where trim(cv_no) = '"+rec_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}


	//open screens implementations
	public void openDRF(){

		if(Home.Home_JSystem.isNotExisting("RequestForPayment")){
			RequestForPayment drf = new RequestForPayment();
			Home_JSystem.addWindow(drf);					
		}
		
		{JOptionPane.showMessageDialog(null, "This will refresh opened Request for Payment", "Warning", 
				JOptionPane.WARNING_MESSAGE);}

		RequestForPayment.cancel();
		
	}

	public static void openPV(){

		if(Home.Home_JSystem.isNotExisting("PayableVoucher")){
			PayableVoucher pv = new PayableVoucher();
			Home_JSystem.addWindow(pv);					
		}	

		{JOptionPane.showMessageDialog(null, "This will refresh opened Payable Voucher", "Warning", 
				JOptionPane.WARNING_MESSAGE);}

		PayableVoucher.cancel();
		PayableVoucher.refresh_fields();	
		
		if(co_id.equals("")){}
		else{
			
			PayableVoucher.co_id	= co_id;
			PayableVoucher.tagCompany.setTag(company);
			PayableVoucher.company	= company;
			PayableVoucher.company_logo = company_logo;
			PayableVoucher.lookupCompany.setValue(co_id);
			
			 
			
		}
	}

	public void openDP(){

		if(Home.Home_JSystem.isNotExisting("DocsProcessing")){
			DocsProcessing doc_proc = new DocsProcessing();
			Home_JSystem.addWindow(doc_proc);				
		}
		

		DocsProcessing.co_id 		= co_id;	
		DocsProcessing.company		= company;	
		DocsProcessing.tagCompany.setTag(company);
		DocsProcessing.company_logo = company_logo;

		DocsProcessing.lblDoc_type.setEnabled(true);	
		DocsProcessing.lookupDocType.setEnabled(true);	
		//DocsProcessing.lookupDocType.setEditable(true);	
		DocsProcessing.tagDocType.setEnabled(true);	

		DocsProcessing.lookupDocType.setLookupSQL(DocsProcessing.getDocType());		
		DocsProcessing.enableButtons(false, false, false, true);

		DocsProcessing.lookupCompany.setValue(co_id);
		DocsProcessing.lookupDocType.setValue("13");
		DocsProcessing.tagDocType.setTag("DISBURSEMENT VOUCHER");

		DocsProcessing.lblProcess.setEnabled(true);	
		DocsProcessing.lookupProcess.setEnabled(true);	
		//DocsProcessing.lookupProcess.setEditable(true);	
		DocsProcessing.tagProcess.setEnabled(true);
		DocsProcessing.lookupProcess.setValue("");
		DocsProcessing.tagProcess.setText("[ ]");

		if (DocsProcessing.lookupDocType.getText().trim().equals("11")) {DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getJV_processes()); DocsProcessing.doc_no = "11";}
		if (DocsProcessing.lookupDocType.getText().trim().equals("12")) {DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getPV_processes()); DocsProcessing.doc_no = "12";}
		if (DocsProcessing.lookupDocType.getText().trim().equals("13")) {DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getDV_processes()); DocsProcessing.doc_no = "13";}	

	}

	public void addCheck(){

		if(Home.Home_JSystem.isNotExisting("AddCheckNumber")){
			AddCheckNumber add_receipt = new AddCheckNumber();
			Home_JSystem.addWindow(add_receipt);		
		}		

		if(co_id.equals(""))
		{

		} 
		else 
		{
			AddCheckNumber.co_id 		= co_id;	
			AddCheckNumber.company	= company;	
			AddCheckNumber.company_logo = company_logo;
			AddCheckNumber.lookupCompany.setValue(co_id);
			AddCheckNumber.txtCompany.setText(company);
			AddCheckNumber.lblTransNo.setEnabled(true);	
			AddCheckNumber.lookupTransNo.setEnabled(true);	
			AddCheckNumber.enableButtons(true, false, false, true);

			AddCheckNumber.lookupTransNo.setLookupSQL(AddCheckNumber.getTransaction());	
		}

	}

	public void editDatePaid(){

		to_do = "editdatepaid";
		lblDatePaid.setEnabled(true);	
		dtePaid.setEnabled(true);
		enableButtons(false, false, false, false, true, true, false, false, false, false );


	}
	
	private static String getCurrentYear(){
		pgSelect db = new pgSelect(); 
		db.select("SELECT EXTRACT(YEAR FROM now())::VARCHAR"); 

		if (db.getResult()!=null) {
			return db.Result[0][0].toString(); 	
		} else {
			return String.format("%s", getCurrentYear()); 
		}		
	}


}