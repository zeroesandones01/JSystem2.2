package Accounting.Liquidation;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
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
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelLiq_particular_total;
import tablemodel.modelLiq_particulars;
import Accounting.Disbursements.CheckVoucher;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Accounting.GeneralLedger.JournalVoucher;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
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
import TaxesAndPermits.EWT_Remittance;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class CALiquidation extends _JInternalFrame implements _GUI, ActionListener, MouseListener, KeyListener {
	/*
	 * CHANGES AS OF 2021-09-23
	 * 
	 * 1. EDIT THE METHOD displayRequest_particulars - CHANGE PAYEE ID AND PAYEE TYPE 2021-09-23
	 * 2. CORRECT THE PERIOD ID - ADD A METHOD TO GET THE PERIOD ID THRU JV HEADER 2022-01-18 (REVISED AGAIN 2022-01-21)
	 * 3. CORRECT REVISION NO.2 - INCLUDE IN THE QUERY THE VALUES OF PERIOD ID AND GL YEAR 2022-01-21
	 * 4. ADDITIONAL COLUMN IN LOOKUP (getEntityList) DCRF NO. 1930 : INCLUDE TIN NUMBER IN DRF, CA AND JV FOR REPORTING PURPOSES 2022-01-26
	 * 
	 * 
	 * */
	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	protected static final Home_JSystem Home_JSystem = null;
	static String title = "Cash Advance (CA) Liquidation";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlLiqDtl_1;
	private JPanel pnlLiqDtl_2;
	private JPanel pnlLiqDtl_1a;
	private JPanel pnlLiqDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlLiq;
	private JPanel pnlLiq_a;
	private JPanel pnlLiq_a1;
	private JPanel pnlLiq_a2;
	private JPanel pnlLiq_a2_1;
	private JPanel pnlLiq_a2_2;
	private JPanel pnlLiq_part;
	private JPanel pnlLiqDtl;
	private JPanel pnlLiq_a2_3;
	private JPanel pnlLiqInfo;
	private JPanel pnlLiqInfo_1;
	private JPanel pnlLiqDtl_2a;
	private JPanel pnlLiqDtl_2b;
	private JPanel pnlLiq_particular;
	private JPanel pnlRemarks;
	private JPanel pnlSouthCenter;
	private JPanel pnlSouthCenterb;
	private JPanel pnlDate;

	private JLabel lblCompany;
	private JLabel lblRequestNo;
	private JLabel lblLiqNo;
	private JLabel lblStatus;
	private JLabel lblJV_no;
	private JLabel lblGL_year;
	private JLabel lblPeriod;
	private JLabel lblLiqDate;
	private JLabel lblDV_no;
	private JLabel lblDate;

	private _JLookup lookupCompany;
	private _JLookup lookupLiqNo;
	private _JLookup lookupGL_year;
	private _JLookup lookupPeriod;
	private static _JLookup lookupRequest;

	private _JScrollPaneMain scrollLiq_part;
	private _JScrollPaneTotal scrollLiq_part_total;

	private static modelLiq_particulars modelLiq_part;
	private modelLiq_particular_total modelLiq_part_total;

	private _JTableTotal tblLiq_part_total;	
	private static _JTableMain tblLiq_part;	

	private JList rowHeaderLiq_part;

	private _JTabbedPane tabCenter;	

	private _JTagLabel tagCompany;
	private _JTagLabel tagJVstatus;
	private _JTagLabel tagPayee2;
	private _JTagLabel tagPeriod;
	private _JTagLabel tagDetail;

	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnAddNew;
	private JButton btnRefresh;	
	private JButton btnEdit;
	private JButton btnTag;
	private JButton btnDelete;
	private JButton btnOK;
	private JButton btnEditRef;
	private JButton btnPreview;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dteLiqui;	
	private _JDateChooser dteRefDate;

	private JXTextField txtStatus;
	public JXTextField txtDV_no;
	private static JXTextField txtJV_no;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JScrollPane scpDRFRemark;	
	private JTextArea txtDV_particular;
	private JPopupMenu menu2;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenCV;
	private JMenuItem mniopenJV;
	private JMenuItem mniopenSOA;
	private JMenuItem mniopenRPLF;	
	private JMenuItem mnisetup2307;

	String next_liq_no = "";
	String rplf_no = "";   //this is used to denote the RPLF number used for saving
	static String co_id = "";	
	String acct_id = "";
	String next_chk_no = "";
	String last_chk_no = "";
	String jv_no = "";
	String jv_status = "";
	String refund_rplf_no = "";

	Integer next_chk_no_int = 0;
	Integer last_chk_no_int = 0;
	Integer next_rec_id = 0;
	static String company = "";
	static String company_logo = "";
	String liq_no = "";		
	//String strJV_no = "";	
	Integer next_seqno = 0;	
	Integer row_num = 0;
	Integer lineno = 0;
	private _JTagLabel tagCAtype;
	Double tax_rate		= 0.00;
	
	//String liq_remarks = "";
	//String rplf_particular = "";
	private JPopupMenu menu;
	private JMenuItem mnidelete;
	private JMenuItem mniaddrow;


	public CALiquidation() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CALiquidation(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public CALiquidation(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(935, 543));
		this.setBounds(0, 0, 935, 543);

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
			mniopenCV = new JMenuItem("Open Disbursement Voucher");
			mniopenJV = new JMenuItem("Open Journal Voucher");
			mniopenSOA = new JMenuItem("Open Liquidation SOA");
			mnisetup2307 = new JMenuItem("Open EWT (2307)");
			JSeparator sp = new JSeparator();
			menu2.add(sp);	
			menu2.add(mniopenRPLF);
			menu2.add(mniopenPV);
			menu2.add(mniopenCV);
			menu2.add(mniopenJV);
			menu2.add(sp);	
			menu2.add(mniopenSOA);
			menu2.add(mnisetup2307);

			mniopenRPLF.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					if(FncGlobal.homeMDI.isNotExisting("RequestForPayment")){
						openDRF();	
					}
				}
			});
			
			mniopenPV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					if(FncGlobal.homeMDI.isNotExisting("PayableVoucher")){
						openPV();	
					}
				}
			});
			mniopenCV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					if(FncGlobal.homeMDI.isNotExisting("CheckVoucher")){
						openCV();
					}
				}
			});			
			mniopenJV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){	
					if(FncGlobal.homeMDI.isNotExisting("JournalVoucher")){
						openJV();
					}
				}
			});
			mniopenSOA.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openSOA();			
				}
			});
			mnisetup2307.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){					
					open2307();
				}
			});


		}

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 175));

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

							lblLiqNo.setEnabled(true);	
							lookupLiqNo.setEnabled(true);	
							lookupLiqNo.setLookupSQL(getLiquiList());
							enableButtons(true, false, false, false, false, false, false, false, false );
						}
					}
				});
			}			

			pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
			pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
			pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlComp_a2.addMouseListener(new PopupTriggerListener_panel2());

			{
				tagCompany = new _JTagLabel("[ ]");
				pnlComp_a2.add(tagCompany);
				tagCompany.setBounds(209, 27, 700, 22);
				tagCompany.setEnabled(true);	
				tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
				tagCompany.addMouseListener(new PopupTriggerListener_panel2());
			}	

			pnlLiq = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlLiq, BorderLayout.CENTER);				
			pnlLiq.setPreferredSize(new java.awt.Dimension(921, 233));
			pnlLiq.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlLiq.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlLiq_a = new JPanel(new BorderLayout(5, 5));
			pnlLiq.add(pnlLiq_a, BorderLayout.NORTH);	
			pnlLiq_a.setPreferredSize(new java.awt.Dimension(911, 42));
			pnlLiq_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlLiq_a.setBorder(lineBorder);

			pnlLiq_a1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlLiq_a.add(pnlLiq_a1, BorderLayout.WEST);	
			pnlLiq_a1.setPreferredSize(new java.awt.Dimension(92, 40));	
			pnlLiq_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

			{
				lblLiqNo = new JLabel("Liquidation No.", JLabel.TRAILING);
				pnlLiq_a1.add(lblLiqNo);
				lblLiqNo.setEnabled(false);	
				lblLiqNo.setPreferredSize(new java.awt.Dimension(86, 40));
				lblLiqNo.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}						

			pnlLiq_a2 = new JPanel(new BorderLayout(5, 5));
			pnlLiq_a.add(pnlLiq_a2, BorderLayout.CENTER);	
			pnlLiq_a2.setPreferredSize(new java.awt.Dimension(814, 40));	
			pnlLiq_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
			pnlLiq_a2.addMouseListener(new PopupTriggerListener_panel2());

			pnlLiq_a2_1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlLiq_a2.add(pnlLiq_a2_1, BorderLayout.WEST);	
			pnlLiq_a2_1.setPreferredSize(new java.awt.Dimension(197, 22));					

			{
				lookupLiqNo = new _JLookup(null, "Liquidation No.", 2, 2);
				pnlLiq_a2_1.add(lookupLiqNo);
				lookupLiqNo.setBounds(20, 27, 20, 25);
				lookupLiqNo.setReturnColumn(0);
				lookupLiqNo.setEnabled(false);	
				lookupLiqNo.setPreferredSize(new java.awt.Dimension(157, 22));
				lookupLiqNo.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){								
							liq_no = (String) data[0];
							
//							//ADDED BY JED 2022-01-18 : TO GET THE RIGHT PERIOD ID
//							strJV_no = (String) data[3];
//							
//							System.out.printf("strJV_no lookup:%s\n", strJV_no);
//							System.out.printf("co_id:%s\n", co_id);
//							
//							//ADDED BY JED 2022-01-18 : TO GET THE CORRECT PERIOD ID IN CA
//							String period_id = getPeriod(strJV_no, co_id);
//
//							Object[] month_year = getMonthYear();									
//							//lookupGL_year.setText((String) month_year[4]);
//							lookupGL_year.setValue((String) month_year[4]);
//							
//							//COMMENTED BY JED 2022-01-18 : TO GET THE CORRECT PERIOD ID IN CA
//							//lookupPeriod.setText((String) month_year[0]);
//							if(period_id.equals("")) {
//								System.out.println("Dumaan 1");
//								//lookupPeriod.setText((String) month_year[0]);
//								lookupPeriod.setValue((String) month_year[0]);
//							}else {
//								System.out.println("Dumaan 2");
//								//lookupPeriod.setText(period_id);
//								lookupPeriod.setValue(period_id);
//							}
							
							refresh_fields();
							refresh_tablesMain();
							setLiqui_header(liq_no);	
							displayLiq_particulars(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, liq_no );	
							generateDetail();
							displayLiq_particulars(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, liq_no );
							tagDetail.setText(null);

							String status = txtStatus.getText().trim();

							if(status.equals("ACTIVE")&&jv_status.equals("ACTIVE")) 
							{enableButtons(false, true,  true,  true, false, false, true,  true, true );} 
							else if(status.equals("ACTIVE")&&!jv_status.equals("ACTIVE")) 
							{enableButtons(false, false, true,  true, false, true,  true,  false, true );} 
							else if(status.equals("PROCESSED")&&jv_status.equals("ACTIVE"))
							{enableButtons(false, false, true,  true, false, false, false, true, true );} 
							else if(status.equals("PROCESSED")&&!jv_status.equals("ACTIVE"))
							{enableButtons(false, false, true,  true, false, false, false, false, true );} 
							else {enableButtons(false, false, true,  true, false, false, false, false, true );} 
						}
					}
				});
			}	

			pnlLiq_a2_2 = new JPanel(new GridLayout(1, 2, 5, 0));
			pnlLiq_a2.add(pnlLiq_a2_2, BorderLayout.CENTER);	
			pnlLiq_a2_2.setPreferredSize(new java.awt.Dimension(357, 25));	
			
			{
				tagCAtype = new _JTagLabel("[ ]");
				pnlLiq_a2_2.add(tagCAtype);
				tagCAtype.setBounds(209, 27, 700, 22);
				tagCAtype.setEnabled(false);						
				tagCAtype.setPreferredSize(new java.awt.Dimension(27, 33));
				tagCAtype.addMouseListener(new PopupTriggerListener_panel2());
			}	
			

			pnlLiq_a2_3 = new JPanel(new GridLayout(1, 2,5,0));
			pnlLiq_a2.add(pnlLiq_a2_3, BorderLayout.EAST);	
			pnlLiq_a2_3.setPreferredSize(new java.awt.Dimension(310, 20));	
			pnlLiq_a2_3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));			

			{
				lblStatus = new JLabel("Status", JLabel.TRAILING);
				pnlLiq_a2_3.add(lblStatus);
				lblStatus.setEnabled(false);	
			}	
			{
				txtStatus = new JXTextField("");
				pnlLiq_a2_3.add(txtStatus);
				txtStatus.setEnabled(false);	
				txtStatus.setEditable(false);
				txtStatus.setBounds(120, 25, 300, 22);	
				txtStatus.setHorizontalAlignment(JTextField.CENTER);	
				txtStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				txtStatus.addMouseListener(new PopupTriggerListener_panel2());
			}	
			{
				pnlLiqDtl = new JPanel(new BorderLayout(0, 5));
				pnlLiq.add(pnlLiqDtl, BorderLayout.WEST);	
				pnlLiqDtl.setPreferredSize(new java.awt.Dimension(911, 187));
				pnlLiqDtl.addMouseListener(new PopupTriggerListener_panel2());

				pnlLiqDtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlLiqDtl.add(pnlLiqDtl_1, BorderLayout.WEST);	
				pnlLiqDtl_1.setPreferredSize(new java.awt.Dimension(237, 116));
				pnlLiqDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

				pnlLiqDtl_1a = new JPanel(new GridLayout(3, 1, 0, 5));
				pnlLiqDtl_1.add(pnlLiqDtl_1a, BorderLayout.WEST);	
				pnlLiqDtl_1a.setPreferredSize(new java.awt.Dimension(110, 116));
				pnlLiqDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));							
				{
					lblLiqDate = new JLabel("Liquidation Date", JLabel.TRAILING);
					pnlLiqDtl_1a.add(lblLiqDate);
					lblLiqDate.setEnabled(false);	
				}	
				{
					lblRequestNo = new JLabel("Request No.", JLabel.TRAILING);
					pnlLiqDtl_1a.add(lblRequestNo);
					lblRequestNo.setEnabled(false);	
				}	
				{
					lblDV_no = new JLabel("DV No.", JLabel.TRAILING);
					pnlLiqDtl_1a.add(lblDV_no);
					lblDV_no.setEnabled(false);	
				}

				pnlLiqDtl_1b = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlLiqDtl_1.add(pnlLiqDtl_1b, BorderLayout.CENTER);	
				pnlLiqDtl_1b.setPreferredSize(new java.awt.Dimension(135, 119));
				pnlLiqDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					dteLiqui = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlLiqDtl_1b.add(dteLiqui);
					dteLiqui.setBounds(485, 7, 125, 21);
					//dteLiqui.setDate(null);
					dteLiqui.setEnabled(false);
					//dteLiqui.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteLiqui.getDateEditor()).setEditable(false);
					dteLiqui.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));				
					dteLiqui.addPropertyChangeListener( new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
							
							Object[] month_year = getMonthYear();	
							
							lookupGL_year.setValue(((String) month_year[4]).trim());
							
							lookupPeriod.setText((String) month_year[0]);
							
//							System.out.printf("strJV_no:%s\n", txtJV_no.getText());
//							System.out.printf("co_id:%s\n", co_id);
//							
//							//ADDED BY JED 2022-01-18 : TO GET THE CORRECT PERIOD ID IN CA
//							String period_id = getPeriod(strJV_no, co_id);
//
//							Object[] month_year = getMonthYear();									
//							//lookupGL_year.setText((String) month_year[4]);
//							lookupGL_year.setValue(((String) month_year[4]).trim());
//							
//							//COMMENTED BY JED 2022-01-18 : TO GET THE CORRECT PERIOD ID IN CA
//							//lookupPeriod.setText((String) month_year[0]);
//							if(period_id.equals("")) {
//								System.out.println("Dumaan 1");
//								//lookupPeriod.setText((String) month_year[0]);
//								lookupPeriod.setValue(((String) month_year[0]).trim());
//							}else {
//								System.out.println("Dumaan 2");
//								//lookupPeriod.setText(period_id);
//								lookupPeriod.setValue(period_id.trim());
//							}
//							
//							System.out.printf("period_id:%s\n", period_id);
//							System.out.printf("month_year:%s\n", month_year[4]);
												
							tagPeriod.setTag(((String) month_year[3]).toUpperCase());
							
							if (isYearMonthOpen((String) month_year[4], (String) month_year[0])==true){}
							else {JOptionPane.showMessageDialog(null,
									"Year [" + (String) month_year[4] + "] ; " + "Month [" + (String) month_year[0] + "] is closed." + "\n" +
									"Please ask your Department Head to open.", 
									"Information", 
									JOptionPane.WARNING_MESSAGE);
							}

						}
					});	
				}		
				{
					lookupRequest = new _JLookup(null, "Request No.", 2, 2);
					pnlLiqDtl_1b.add(lookupRequest);
					lookupRequest.setBounds(20, 27, 20, 25);
					lookupRequest.setReturnColumn(0);
					lookupRequest.setEnabled(false);	
					lookupRequest.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupRequest.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){									
								rplf_no = (String) data[0];
								txtDV_no.setText((String) data[3]);

								Object[] month_year = getMonthYear();									
								lookupGL_year.setText((String) month_year[4]);
								lookupPeriod.setText((String) month_year[0]);
								tagPeriod.setTag(((String) month_year[3]).toUpperCase());

								displayRequest_particulars(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, liq_no );	
							}
						}
					});	
				}		
				{
					txtDV_no = new JXTextField("");
					pnlLiqDtl_1b.add(txtDV_no);
					txtDV_no.setEnabled(false);	
					txtDV_no.setEditable(false);
					txtDV_no.setBounds(120, 25, 300, 22);	
					txtDV_no.setHorizontalAlignment(JTextField.CENTER);	
				}	

				//Start of Left Panel 
				pnlLiqInfo = new JPanel(new BorderLayout(0,0));
				pnlLiqDtl.add(pnlLiqInfo, BorderLayout.EAST);
				pnlLiqInfo.setPreferredSize(new java.awt.Dimension(674, 140));
				pnlLiqInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlLiqInfo_1 = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlLiqInfo.add(pnlLiqInfo_1, BorderLayout.WEST);
				pnlLiqInfo_1.setPreferredSize(new java.awt.Dimension(84, 140));
				pnlLiqInfo_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblJV_no = new JLabel("JV No.", JLabel.TRAILING);
					pnlLiqInfo_1.add(lblJV_no);
					lblJV_no.setEnabled(false);	
				}	
				{
					lblGL_year = new JLabel("GL Year", JLabel.TRAILING);
					pnlLiqInfo_1.add(lblGL_year);
					lblGL_year.setEnabled(false);	
				}
				{
					lblPeriod = new JLabel("Period", JLabel.TRAILING);
					pnlLiqInfo_1.add(lblPeriod);
					lblPeriod.setEnabled(false);	
				}

				pnlLiqDtl_2 = new JPanel(new BorderLayout(5,0));
				pnlLiqInfo.add(pnlLiqDtl_2, BorderLayout.CENTER);
				pnlLiqDtl_2.setPreferredSize(new java.awt.Dimension(203, 118));
				pnlLiqDtl_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

				pnlLiqDtl_2a = new JPanel(new GridLayout(3, 1, 0, 5));
				pnlLiqDtl_2.add(pnlLiqDtl_2a, BorderLayout.WEST);
				pnlLiqDtl_2a.setPreferredSize(new java.awt.Dimension(119, 119));
				pnlLiqDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					txtJV_no = new JXTextField("");
					pnlLiqDtl_2a.add(txtJV_no);
					txtJV_no.setEnabled(false);	
					txtJV_no.setEditable(false);
					txtJV_no.setBounds(120, 25, 300, 22);	
					txtJV_no.setHorizontalAlignment(JTextField.CENTER);	
				}		
				{
					lookupGL_year = new _JLookup(null, "GL Year", 2, 2);
					pnlLiqDtl_2a.add(lookupGL_year);
					lookupGL_year.setBounds(20, 27, 20, 25);
					lookupGL_year.setReturnColumn(0);
					lookupGL_year.setEnabled(false);	
					lookupGL_year.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupGL_year.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){						
								String entity_name = (String) data[1];						
								tagPayee2.setTag(entity_name);
							}
						}
					});	
				}
				{
					lookupPeriod= new _JLookup(null, "Payee Type", 2, 2);
					pnlLiqDtl_2a.add(lookupPeriod);
					lookupPeriod.setBounds(20, 27, 20, 25);
					lookupPeriod.setReturnColumn(0);
					lookupPeriod.setEnabled(false);	
					lookupPeriod.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPeriod.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){						
								String payee_type = (String) data[1];						
								tagPeriod.setTag(payee_type);
							}
						}
					});	
				}	

				pnlLiqDtl_2b = new JPanel(new GridLayout(3, 1, 0, 5));
				pnlLiqDtl_2.add(pnlLiqDtl_2b, BorderLayout.CENTER);
				pnlLiqDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
				pnlLiqDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					tagJVstatus = new _JTagLabel("[ ]");
					pnlLiqDtl_2b.add(tagJVstatus);
					tagJVstatus.setBounds(209, 27, 700, 22);
					tagJVstatus.setEnabled(false);						
					tagJVstatus.setPreferredSize(new java.awt.Dimension(27, 33));
					tagJVstatus.addMouseListener(new PopupTriggerListener_panel2());
				}		
				{
					tagPayee2 = new _JTagLabel("[ ]");
					pnlLiqDtl_2b.add(tagPayee2);
					tagPayee2.setBounds(209, 27, 700, 22);
					tagPayee2.setEnabled(false);	
					tagPayee2.setVisible(false);	
					tagPayee2.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPayee2.addMouseListener(new PopupTriggerListener_panel2());
				}		
				{
					tagPeriod = new _JTagLabel("[ ]");
					pnlLiqDtl_2b.add(tagPeriod);
					tagPeriod.setBounds(209, 27, 700, 22);
					tagPeriod.setEnabled(false);	
					tagPeriod.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPeriod.addMouseListener(new PopupTriggerListener_panel2());
				}	
			}				
		}		
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlLiq = new JPanel();
			pnlTable.add(pnlLiq, BorderLayout.CENTER);
			pnlLiq.setLayout(new BorderLayout(5, 5));
			pnlLiq.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlLiq.setBorder(lineBorder);		
			pnlLiq.addMouseListener(new PopupTriggerListener_panel2());

			tabCenter = new _JTabbedPane();
			pnlLiq.add(tabCenter, BorderLayout.CENTER);
			tabCenter.addMouseListener(new PopupTriggerListener_panel2());

			{			
				pnlLiq_part = new JPanel(new BorderLayout());
				tabCenter.addTab("Particulars", null, pnlLiq_part, null);
				pnlLiq_part.setPreferredSize(new java.awt.Dimension(1183, 365));	
				
				{
					tagDetail = new _JTagLabel("");
					pnlLiq_part.add(tagDetail, BorderLayout.NORTH);
					tagDetail.getDocument().addDocumentListener(
							new DocumentListener() {
								public void changedUpdate(DocumentEvent e) {
									warn();
								}

								public void removeUpdate(DocumentEvent e) {
									warn();
								}

								public void insertUpdate(DocumentEvent e) {
									warn();
								}

								public void warn() {
									if (tagDetail.getText().contains("null")) {
										tagDetail.setForeground(Color.RED);
									} else {
										tagDetail.setForeground(Color.BLACK);
									}
								}
							});
					
				}
				{
					scrollLiq_part = new _JScrollPaneMain();
					pnlLiq_part.add(scrollLiq_part, BorderLayout.CENTER);
					{
						modelLiq_part = new modelLiq_particulars();

						tblLiq_part = new _JTableMain(modelLiq_part);
						scrollLiq_part.setViewportView(tblLiq_part);
						tblLiq_part.addMouseListener(new PopupTriggerListener_panel());
						tblLiq_part.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblLiq_part.getColumnModel().getColumn(1).setPreferredWidth(300);
						tblLiq_part.getColumnModel().getColumn(28).setMinWidth(0);
						tblLiq_part.getColumnModel().getColumn(28).setMaxWidth(0);

						tblLiq_part.addMouseListener(this);							
						tblLiq_part.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								System.out.println("Dumaan sa keyReleased");
								tblLiq_part.packAll();
								computeBillAmounts(evt);
								//computeBillAmounts();
							}							
							public void keyPressed(KeyEvent e) {
								System.out.println("Dumaan sa keyPressed");
								tblLiq_part.packAll();
								computeBillAmounts(e);	
								//computeBillAmounts();
							}

						});
						tblLiq_part.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblLiq_part.rowAtPoint(e.getPoint()) == -1){
									tblLiq_part_total.clearSelection();
									tblLiq_part.getToolTipText(e);
								}else{
									tblLiq_part.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblLiq_part.rowAtPoint(e.getPoint()) == -1){
									tblLiq_part_total.clearSelection();
								}else{
									tblLiq_part.setCellSelectionEnabled(true);
									tblLiq_part.getToolTipText(e);
								}
							}
						});
						
						tblLiq_part.addKeyListener(this);
						
						tblLiq_part.hideColumns("div");
						tblLiq_part.hideColumns("dep");
						tblLiq_part.hideColumns("proj");
						tblLiq_part.hideColumns("sub");
						tblLiq_part.hideColumns("availer");
						
						tblLiq_part.hideColumns("div");
						tblLiq_part.hideColumns("dep");
						tblLiq_part.hideColumns("proj");
						tblLiq_part.hideColumns("sub");
						tblLiq_part.hideColumns("availer");
						
						tblLiq_part.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								try {
									if(!arg0.getValueIsAdjusting()){
										
										System.out.println("Dumaan sa addListSelectionListener");
							
										lineno = (Integer) modelLiq_part.getValueAt(tblLiq_part.getSelectedRow(), 0);
										generateDetail(lineno);
									}
								} catch (ArrayIndexOutOfBoundsException e) { }
							}
						});
						tblLiq_part.putClientProperty("terminateEditOnFocusLost", true);
						
					}
					{
						rowHeaderLiq_part = tblLiq_part.getRowHeader();
						scrollLiq_part.setRowHeaderView(rowHeaderLiq_part);
						scrollLiq_part.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollLiq_part_total = new _JScrollPaneTotal(scrollLiq_part);
					pnlLiq_part.add(scrollLiq_part_total, BorderLayout.SOUTH);
					{
						modelLiq_part_total = new modelLiq_particular_total();
						modelLiq_part_total.addRow(new Object[] { "Total", null,null,null,null,null,null,null,null,
								new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),null,null,null,null,null
								,null,null,null,null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});

						tblLiq_part_total = new _JTableTotal(modelLiq_part_total, tblLiq_part);
						tblLiq_part_total.setFont(dialog11Bold);
						scrollLiq_part_total.setViewportView(tblLiq_part_total);
						((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
					}
				}
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
					btnRefresh= new JButton("Refresh");
					pnlSouthCenterb.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.addActionListener(this);
					btnRefresh.setEnabled(false);
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
				pnlDate= new JPanel();
				pnlDate.setLayout(null);
				pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
				{
					lblDate = new JLabel();
					pnlDate.add(lblDate);
					lblDate.setBounds(5, 15, 160, 20);
					lblDate.setText("Reference Doc. Date :");
				}
				{
					dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDate.add(dteRefDate);
					dteRefDate.setBounds(130, 15, 125, 21);
					dteRefDate.setDate(null);
					dteRefDate.setEnabled(true);
					dteRefDate.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteRefDate.getDateEditor()).setEditable(false);
					dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteRefDate.addPropertyChangeListener( new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
						}
					});	
				}
				{
					btnOK = new JButton();
					pnlDate.add(btnOK);
					btnOK.setBounds(95, 58, 69, 22);
					btnOK.setText("OK");
					btnOK.setFocusable(false);
					btnOK.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
							optionPaneWindow.dispose();
						}
					});
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
					btnPreview = new JButton("Preview");
					pnlSouthCenter.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnEditRef = new JButton("Edit References");
					pnlSouthCenter.add(btnEditRef);
					btnEditRef.setActionCommand("EditReferences");
					btnEditRef.addActionListener(this);
					btnEditRef.setEnabled(false);
				}
				{
					btnTag = new JButton("Tag");
					pnlSouthCenter.add(btnTag);
					btnTag.setActionCommand("Tag");
					btnTag.addActionListener(this);
					btnTag.setEnabled(false);
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
				pnlLiq_particular = new JPanel(new BorderLayout());
				tabCenter.addTab("Notes", null, pnlLiq_particular, null);
				pnlLiq_particular.setPreferredSize(new java.awt.Dimension(100, 365));		

				pnlRemarks = new JPanel(new BorderLayout(5, 5));
				pnlLiq_particular.add(pnlRemarks, BorderLayout.CENTER);			
				pnlRemarks.setPreferredSize(new java.awt.Dimension(100, 178));

				scpDRFRemark = new JScrollPane();
				pnlRemarks.add(scpDRFRemark);
				scpDRFRemark.setBounds(82, 7, 150, 61);
				scpDRFRemark.setOpaque(true);
				scpDRFRemark.setPreferredSize(new java.awt.Dimension(100, 285));

				{
					txtDV_particular = new JTextArea();
					scpDRFRemark.add(txtDV_particular);
					scpDRFRemark.setViewportView(txtDV_particular);
					txtDV_particular.setBounds(77, 3, 250, 81);
					txtDV_particular.setLineWrap(true);
					txtDV_particular.setPreferredSize(new java.awt.Dimension(100, 133));
					txtDV_particular.setEditable(false);
					txtDV_particular.setEnabled(true);	
					txtDV_particular.setText("");	
				}		
			}
		}
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
		
	}
	
	//ADDED BY JED 2022-01-18 : TO GET THE CORRECT PERIOD ID IN CA
	private String getPeriod(String jv_no, String co_id) {
		
		String period_id = "";
		
		String sql = "select period_id from rf_jv_header where jv_no = '"+jv_no+"' and status_id in ('A', 'P', 'F') and co_id = '"+co_id+"'";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) { period_id = "";}
			else {period_id = (String) db.getResult()[0][0]; }

		}else{
			period_id = "";
		}
		
		return period_id;
		
	}
	
	private String getMonth(String liq_no, String co_id) {
		
		String month = "";
		
		String sql = "select right(left((liq_date)::varchar, 7),2) from rf_liq_header where liq_no = '"+liq_no+"' and co_id = '"+co_id+"'";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) { month = "";}
			else {month = (String) db.getResult()[0][0]; }

		}else{
			month = "";
		}
		
		return month;
		
	}


	//display tables
	private void displayLiq_particulars(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String rec_no) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"-----display Liquidation particulars\r\n" + 
			"			select \r\n" + 
			"			\r\n" + 
			"			a.rplf_line_no,\r\n" + 
			"			a.part_desc,\r\n" + 
			"			a.acct_id,\r\n" + 
			"			a.div_id,\r\n" + 
			"			a.dept_id,\r\n" + 
			"			a.sect_id,\r\n" + 
			"			a.project_id,\r\n" + 
			"			a.sub_projectid,\r\n" + 
			"			b.acct_name,\r\n" + 
			"			a.tran_amt,\r\n" + 
			"			a.return_amt,\r\n" + 
			"			a.refund_amt,\r\n" + 
			"			a.entity_id,\r\n" + 
			"			a.entity_type_id,\r\n" + 
			"			a.ref_doc_id,\r\n" + 
			"			a.ref_doc_no,\r\n" + 
			"			a.ref_doc_date,\r\n" + 
			"			a.is_vatproject,\r\n" + 
			"			a.is_vatentity,\r\n" + 
			"			a.is_taxpaidbyco,\r\n" + 
			"			a.is_gross,\r\n" + 
			"			a.vat_rate,\r\n" + 
			"			a.wtax_id,\r\n" + 
			"			a.wtax_rate,\r\n" + 
			"			a.wtax_amt,\r\n" + 
			"			a.vat_amt,\r\n" + 
			"			a.exp_amt,\r\n" + 
			"			a.ca_amt,			\r\n" + 
			"			'',			\r\n" + 
			"			get_div_alias(a.div_id),			\r\n" + 
			"			get_department_alias_new(a.dept_id),			\r\n" + 
			"			get_project_alias(a.project_id),			\r\n" + 
			"			get_sub_proj_alias(a.sub_projectid),			\r\n" + 
			"			get_client_name(a.entity_id)			\r\n" + 
			"			\r\n" + 
			"			from (select * from rf_liq_detail where co_id = '"+co_id+"') a\r\n" + 
			"			left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n" + 
			"			\r\n" + 
			"			where a.liq_no = '"+rec_no+"' \r\n" + 
			"			and a.status_id = 'A'  \r\n" + 
			"			order by a.rplf_line_no  " ;

		FncSystem.out("Liquidation particulars", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalLiqui(modelMain, modelTotal);			
		}		

		else {
			modelLiq_part_total = new modelLiq_particular_total();
			modelLiq_part_total.addRow(new Object[] { "Total", null, null,null,null,null,null,null,null,
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),null,null,null,null,null
					,null,null,null,null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});


			tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
			tblLiq_part_total.setFont(dialog11Bold);
			scrollLiq_part_total.setViewportView(tblLiq_part_total);
			((_JTableTotal) tblLiq_part_total).setTotalLabel(0);}

		adjustRowHeight();
		tblLiq_part.packAll();
		if (tblLiq_part.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
			tblLiq_part.getColumnModel().getColumn(1).setPreferredWidth(200);
		}
		
	}	

	private void displayRequest_particulars(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String rec_no) {//used
		/*EDITED BY JED 2021-09-23 : CORRECT THE PAYEE ID AND PAYEE TYPE*/
		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"-----------display Request particulars\r\n" + 			
			"select \r\n" + 

			"a.line_no,\r\n" + 
			"a.part_desc,\r\n" + 
			"a.acct_id,\r\n" + 
			"COALESCE(a.div_id, ''),\r\n" + 
			"COALESCE(a.dept_id, ''),\r\n" + 
			"COALESCE(a.sect_id, ''),\r\n" + 
			"a.project_id,\r\n" + 
			"a.sub_projectid,\r\n" + 
			"b.acct_name,\r\n" + 
			"a.amount,\r\n" + 
			"0.00,\r\n" + 
			"0.00,\r\n" + 
			"--a.entity_id,/**commented by jed 2021-09-23 : payee id instead of availer/\r\n" + 
			"(\n" + 
			"	select \n" + 
			"	x.entity_id1\n" + 
			"	from rf_pv_header x\n" + 
			"	where x.pv_no = '"+rplf_no+"'\n" + 
			"	and x.status_id = 'P'\n" + 
			"	and x.co_id = '"+co_id+"'\n" + 
			") as entity_id,\r\n" +
			"--a.entity_type_id,/**commented by jed 2021-09-23 : payee type from pv instead of availer type in request detail/\r\n" +
			"(\n" + 
			"	select \n" + 
			"	x.entity_type_id\n" + 
			"	from rf_pv_header x\n" + 
			"	where x.pv_no = '"+rplf_no+"'\n" + 
			"	and x.status_id = 'P'\n" + 
			"	and x.co_id = '"+co_id+"'\n" + 
			") as entity_id_type_id,\r\n"+
			"a.ref_doc_id,\r\n" + 
			"trim(a.ref_doc_no),\r\n" + 
			"a.ref_doc_date,\r\n" + 
			"a.is_vatproject,\r\n" + 
			"a.is_vatentity,\r\n" + 
			"a.is_taxpaidbyco,\r\n" + 
			"a.is_gross,\r\n" + 
			"a.vat_rate,\r\n" + 
			"COALESCE(a.wtax_id, ''),\r\n" + 
			"a.wtax_rate,\r\n" + 
			"a.wtax_amt,\r\n" + 
			"a.vat_amt,\r\n" + 
			"a.exp_amt,\r\n" + 
			"a.pv_amt," +
			"a.amount \r\n" + 

			"from (select * from rf_request_detail where co_id = '"+co_id+"') a\r\n" + 
			"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id  " +
			"where a.status_id = 'A' \r\n" + 			
			"and a.rplf_no = '"+rplf_no+"' " +
			"order by a.rplf_no " ;

		FncSystem.out("display Request particulars", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalLiqui(modelMain, modelTotal);			
		}		

		else {
			modelLiq_part_total = new modelLiq_particular_total();
			modelLiq_part_total.addRow(new Object[] { "Total", null, null,null,null,null,null,null,null,
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),null,null,null,null,null
					,null,null,null,null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});


			tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
			tblLiq_part_total.setFont(dialog11Bold);
			scrollLiq_part_total.setViewportView(tblLiq_part_total);
			((_JTableTotal) tblLiq_part_total).setTotalLabel(0);}

		adjustRowHeight();
		row_num = tblLiq_part.getModel().getRowCount();				
		tblLiq_part.packAll();
		tblLiq_part.getColumnModel().getColumn(28).setMinWidth(0);
		tblLiq_part.getColumnModel().getColumn(28).setMaxWidth(0);
		AddExtraRows();
	}	

	private void setLiqui_header(String req_no){//used

		Object[] liq_hdr = getLiquiHeaderDetails();	

		dteLiqui.setDate((Date) liq_hdr[1]);
		lookupRequest.setText((String) liq_hdr[2]);
		txtDV_no.setText((String) liq_hdr[4]);
		txtJV_no.setText((String) liq_hdr[3]);
		txtStatus.setText((String) liq_hdr[5]);	
		txtDV_particular.setText((String) liq_hdr[6]);	
		tagJVstatus.setTag((String) liq_hdr[7]);
		jv_status = (String) liq_hdr[7];
		tagCAtype.setTag((String) liq_hdr[8]);
		lookupGL_year.setText((String) liq_hdr[10]);
		lookupPeriod.setText((String) liq_hdr[9]);

	}


	//Enable/Disable all components inside JPanel
	private void enable_fields(Boolean x){//used

		txtStatus.setEnabled(x);
		lblStatus.setEnabled(x);	

		lblLiqDate.setEnabled(x);	
		lblRequestNo.setEnabled(x);	
		lblDV_no.setEnabled(x);

		dteLiqui.setEnabled(x);	
		lookupRequest.setEnabled(x);	
		//lookupRequest.setEditable(x);
		txtDV_no.setEnabled(x);	

		lblJV_no.setEnabled(x);	
		lblGL_year.setEnabled(x);	
		lblPeriod.setEnabled(x);	

		txtJV_no.setEnabled(x);	
		lookupGL_year.setEnabled(x);	
		//lookupGL_year.setEditable(x);
		lookupPeriod.setEnabled(x);	
		//lookupPeriod.setEditable(x);
		tagPeriod.setEnabled(x);
		tagJVstatus.setEnabled(x);		

		txtDV_particular.setEnabled(x);	
		txtDV_particular.setEditable(x);
	}

	private void enable_fields_to_addNew(){//used

		lblLiqNo.setEnabled(false);	
		lookupLiqNo.setEnabled(false);	

		txtStatus.setEnabled(true);
		lblStatus.setEnabled(true);	

		lblLiqDate.setEnabled(true);	
		lblRequestNo.setEnabled(true);	
		lblDV_no.setEnabled(false);

		dteLiqui.setEnabled(true);	
		lookupRequest.setEnabled(true);	
		//lookupRequest.setEditable(true);
		txtDV_no.setEnabled(false);	

		lblJV_no.setEnabled(false);	
		lblGL_year.setEnabled(true);	
		lblPeriod.setEnabled(true);	

		txtJV_no.setEnabled(false);	
		lookupGL_year.setEnabled(true);
		lookupPeriod.setEnabled(true);	
		tagPeriod.setEnabled(false);
		tagJVstatus.setEnabled(false);		

		txtDV_particular.setEnabled(true);
		txtDV_particular.setEditable(true);
		modelLiq_part.setEditable(false, false);
	}

	private void refresh_fields(){//used

		txtStatus.setText("");	
		dteLiqui.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lookupRequest.setValue("");		
		txtDV_no.setText("");	
		txtJV_no.setText("");	
		lookupGL_year.setValue("");	
		lookupPeriod.setValue("");	
		tagPeriod.setText("[ ]");
		txtDV_particular.setText("");	
		tagJVstatus.setText("[ ]");
	}

	private void refresh_tablesMain(){

		//reset table 1
		FncTables.clearTable(modelLiq_part);	
		FncTables.clearTable(modelLiq_part_total);			
		rowHeaderLiq_part = tblLiq_part.getRowHeader();
		scrollLiq_part.setRowHeaderView(rowHeaderLiq_part);	
		modelLiq_part_total.addRow(new Object[] { "Total", null, null,null,null,null,null,null,null,
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),null,null,null,null,null
				,null,null,null,null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});

	}

	private void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, 
			Boolean f, Boolean g, Boolean h, Boolean i ){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnRefresh.setEnabled(c);
		btnCancel.setEnabled(d);

		btnSave.setEnabled(e);
		btnEditRef.setEnabled(f);
		btnTag.setEnabled(g);		
		btnDelete.setEnabled(h);	
		btnPreview.setEnabled(i);
	}

	private void initialize_comp(){
		
		co_id 		= "";	
		company		= "";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		lblLiqNo.setEnabled(true);	
		lookupLiqNo.setEnabled(true);	
		lookupLiqNo.setLookupSQL(getLiquiList());
		enableButtons(true, false, false, false, false, false, false, false, false );
		
		lookupCompany.setValue(co_id);
}
	

	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Refresh")){ refresh();	} //ok

		if(e.getActionCommand().equals("Cancel")){ cancel();  } //ok
		
		if(e.getActionCommand().equals("Preview")){ preview();  } //ok

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==true) {addnew();  } //ok
		else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process liquidation.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==true) { edit(); } //ok  
		else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit liquidation.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("EditReferences") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==true) { edit_references(); } //ok  
		else if (e.getActionCommand().equals("EditReferences") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit references.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==true) {
			save(); 
		}else if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==false ) { 
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process liquidation.","Information",JOptionPane.INFORMATION_MESSAGE); 
		}
		if (e.getActionCommand().equals("Tag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==true) {tagLiqui(); } //ok
		else if (e.getActionCommand().equals("Tag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "10")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to tag liquidation.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Delete") && FncAcounting.EmpCanDelete(UserInfo.EmployeeCode, "10")==true) {delete();} //ok
		else if (e.getActionCommand().equals("Delete") && FncAcounting.EmpCanDelete(UserInfo.EmployeeCode, "10")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to delete liquidation.","Information",JOptionPane.INFORMATION_MESSAGE); }


	}

	public void mouseClicked(MouseEvent evt) {
		
		int row = tblLiq_part.convertRowIndexToModel(tblLiq_part.getSelectedRow());
		
		if ((evt.getClickCount() >= 2)) {
			System.out.println("Dumaan sa mouseClicked double click");
			clickTableColumn();
		}	
		
		else if ((evt.getClickCount() == 1) && row== 19)  {
			
			System.out.println("Dumaan sa mouseClicked 1");

			for(int x = 0; x < modelLiq_part.getRowCount(); x++){
					Boolean isSelected1 = (Boolean) modelLiq_part.getValueAt(x, 19);
				
				if (isSelected1) {
					if (tax_rate>=0.00) 
					{
						modelLiq_part.setValueAt(new BigDecimal(tax_rate), x, 23);	
					}
				}else{
					modelLiq_part.setValueAt(new BigDecimal(0.00), x, 23);
				}
				
			}

		}
		if (evt.getSource().equals(tblLiq_part)) {
			System.out.println("Dumaan sa mouseClicked 2");
			for(int x = 0; x < modelLiq_part.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelLiq_part.getValueAt(x, 18);
				if (isSelected) {
					modelLiq_part.setValueAt(new BigDecimal(12.00), x, 21);
				}else{
					modelLiq_part.setValueAt(new BigDecimal(0.00), x, 21);
				}
				
				computeBillAmounts();
			}

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
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	
	//action performed continued
	private void addnew(){//used
		String co_id = lookupCompany.getValue();
		refresh_fields();
		tagDetail.setText(null);
		enable_fields_to_addNew();
		txtStatus.setText("ACTIVE");
		enableButtons(false, false, false, true, true, false, false, false, false );	
		lookupPeriod.setLookupSQL(getPeriod());	
		lookupRequest.setLookupSQL(getRequestList(co_id));
		modelLiq_part.setEditable(true, true);
		dteLiqui.setEditable(true);
		

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Request No.", getRequestList(co_id), false);
		dlg.setLocationRelativeTo(getContentPane());
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();
		if(data != null){
			FncSystem.out("Search", getRequestList(co_id));
			rplf_no = (String) data[0];
			txtDV_no.setText((String) data[3]);
			lookupRequest.setValue(rplf_no);
			//rplf_particular = data[4].toString();

			Object[] month_year = getMonthYear();									
			lookupGL_year.setText((String) month_year[4]);
			lookupPeriod.setText((String) month_year[0]);
			tagPeriod.setTag(((String) month_year[3]).toUpperCase());
			
			if (isYearMonthOpen((String) month_year[4], (String) month_year[0])==true){}
			else {JOptionPane.showMessageDialog(null,
					"Year [" + (String) month_year[4] + "] ; " + "Month [" + (String) month_year[0] + "] is closed." + "\n" +
					"Please ask your Department Head to open.", 
					"Information", 
					JOptionPane.WARNING_MESSAGE);
			}
			//String check_no= "Check No."+CheckVoucher.txtCheckNo.getText()+"";
			
			
			
			
			String liq_remarks = " TO LIQUIDATE " + "RPLF No. : " + rplf_no + "; " + "\n" +
					" WITH DV No." + data[3].toString()+ "\n" ;
					//+check_no ;
			
			txtDV_particular.setText(liq_remarks );
			displayRequest_particulars(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, liq_no );	
		}
	}
	//Added by Erick
	private Boolean checktag(String rplf_no, String co_id) {
		Boolean with_tag = false;
		String sql = "select rplf_no from rf_request_header a\n"
				+ "where rplf_no = '"+rplf_no+"' and co_id = '"+co_id+"' and rplf_type_id in ('02','03','07','08','09')\n" //Added by erick 2023-09-14 rplf_type_id DCRF 2739
				+ "and (exists (select rplf_no from rf_transfer_cost where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"')\n"
				+ "OR exists (select rplf_no  from rf_processing_cost where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"'))\n"
				+ "";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()) {
			with_tag = true;
		}else {
			with_tag = false;
		}
		
		return with_tag;
	}

	private void edit(){//used

		enable_fields_to_addNew();
		lblLiqNo.setEnabled(false);	
		lookupLiqNo.setEnabled(false);	
		//lookupLiqNo.setEditable(false);	
		lblRequestNo.setEnabled(false);	
		lookupRequest.setEnabled(false);	
		//lookupRequest.setEditable(false);	
		//lookupLiqNo.setEditable(false);	
		modelLiq_part.setEditable(true, true);
		enableButtons(false, false, false, true, true, false, false, false, false );
		tagDetail.setText(null);
		dteLiqui.setEditable(true);
	}

	private void edit_references(){

		lblLiqNo.setEnabled(false);	
		lookupLiqNo.setEnabled(false);	
		//lookupLiqNo.setEditable(false);	
		modelLiq_part.setEditable(true, false);
		enableButtons(false, false, false, true, 
				true,  false, false, false, false );
		tagDetail.setText(null);
		dteLiqui.setEditable(true);

	}

	private void delete(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this Liquidation entries?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "This will inactivate liquidation JV.", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String liq_no = lookupLiqNo.getText().trim();
				String jv_no  = txtJV_no.getText().trim();

				pgUpdate db = new pgUpdate();
				deleteLiqui_JVdetails(db, liq_no, jv_no);
				db.commit();			
				enable_fields(false);								
				tabCenter.setSelectedIndex(0);				
				JOptionPane.showMessageDialog(getContentPane(),"Liquidation / JV details deleted","Information",JOptionPane.INFORMATION_MESSAGE);
				enableButtons(false, false, true, true, false, false, false, false, true );
				txtStatus.setText("DELETED");
				tagJVstatus.setText("[ DELETED ]");

			} else {}
		} else {}
		tagDetail.setText(null);

	}

	private void cancel(){//used

		if(!btnSave.isEnabled())  {
			enable_fields(false);
			refresh_tablesMain();
			refresh_fields();			
			lblLiqNo.setEnabled(true);	
			lookupLiqNo.setEnabled(true);	
			//lookupLiqNo.setEditable(true);	
			tabCenter.setSelectedIndex(0);
			lookupLiqNo.setText("");
			enableButtons(true, false, false, false, false, false, false, false, false );
			modelLiq_part.setEditable(false, false);
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				enable_fields(false);
				refresh_tablesMain();
				refresh_fields();			
				lblLiqNo.setEnabled(true);	
				lookupLiqNo.setEnabled(true);
				//lookupLiqNo.setEditable(true);	
				tabCenter.setSelectedIndex(0);
				lookupLiqNo.setText("");
				enableButtons(true, false, false, false, false, false, false, false, false );
				modelLiq_part.setEditable(false, false);
			}}
		tagDetail.setText(null);
	}
	
	private void preview(){//used

		String criteria = "CA Liquidation";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		String SQL ="";
		
		for (int i = 0; i < tblLiq_part.getRowCount(); i++) {
			
				SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +

						"SELECT \n" +
						"'"+modelLiq_part.getValueAt(i, 1)+"' AS particulars, \n" +
						"'"+modelLiq_part.getValueAt(i, 8)+"' AS account, \n" +
						"'"+modelLiq_part.getValueAt(i, 27)+"' AS amount, \n" +
						"get_client_name('"+modelLiq_part.getValueAt(0, 12)+"') AS payee \n";
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("printby", UserInfo.Alias);
		mapParameters.put("status", txtStatus.getText());
		mapParameters.put("jvno", txtJV_no.getText());
		mapParameters.put("trandate", dteLiqui.getDate());
		mapParameters.put("reqno", lookupRequest.getText());
		mapParameters.put("liqno", lookupLiqNo.getValue());

		FncReport.generateReport("/Reports/rptCALiq_preview.jasper", reportTitle, mapParameters, SQL);
		
	}

	private void refresh(){//used
		liq_no = lookupLiqNo.getText().trim();
		refresh_fields();
		generateDetail();
		
		refresh_tablesMain();
		setLiqui_header(liq_no);
		displayLiq_particulars(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, liq_no );	

		String status = txtStatus.getText().trim();

		System.out.printf("status : " + status  + "\n");
		System.out.printf("jv_status : " + jv_status);

		if(status.equals("ACTIVE")&&jv_status.equals("ACTIVE")) 
		{enableButtons(false, true,  true,  true, 
				false, false, true,  true, true );} 
		else if(status.equals("ACTIVE")&&!jv_status.equals("ACTIVE")) 
		{enableButtons(false, false, true,  true, 
				false, true,  true,  false, true );} 
		else if(status.equals("PROCESSED")&&jv_status.equals("ACTIVE"))
		{enableButtons(false, false, true,  true, 
				false, false, false, true, true );} 
		else if(status.equals("PROCESSED")&&!jv_status.equals("ACTIVE"))
		{enableButtons(false, false, true,  true, 
				false, false, false, false, true );} 
		else								 				
		{enableButtons(false, false, true,  true, 
				false, false, false, false, true );} 
		
		JOptionPane.showMessageDialog(getContentPane(),"Data refreshed.","Information",JOptionPane.INFORMATION_MESSAGE);
		
		tagDetail.setText(null);


	}

	private void save(){//used

		if (isYearMonthOpen(lookupGL_year.getText(), lookupPeriod.getText())==true){
			if(checkPeriod()==true)
				{JOptionPane.showMessageDialog(getContentPane(), "Month for Transaction Date and Period is not equal.", "Information", 
						JOptionPane.WARNING_MESSAGE);}
			else {
				if(checkOR_submission()==false)
					{if (JOptionPane.showConfirmDialog(getContentPane(), "Liquidation references (ID, Number and Date) are incomplete, \n" +
						"proceed anyway?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
						{if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
								save_main();}
						} else {}
					}
				else {
					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						if(jv_status.equals("POSTED")){  //for inserting references only
							save_references();}
						else {save_main();}   //if new liquidation
						}
					}	
			}
		}
		else {JOptionPane.showMessageDialog(null,
				"Year [" + lookupGL_year.getText() + "] ; " + "Month [" + lookupPeriod.getText() + "] is closed." + "\n" +
				"Please ask your Department Head to open.", 
				"Information", 
				JOptionPane.WARNING_MESSAGE);
		}
	}
	
//	private void save_main(){//used
//		int rw = tblLiq_part.getModel().getRowCount();	
//		int x = 0;
//		String add_remarks1 = "";
//		int liq_line_no = 1;
//		String liq = lookupLiqNo.getText().trim();
//		
//		//New Liquidation
//		if(liq.equals("")) {							
//			next_liq_no = sql_getNextLIQno();
//			pgUpdate db = new pgUpdate();	
//			setJV_no();	
//			//Added by Erick date 2019-08-19 DCRF 1174
//			while(x<rw){
//				
//				String entity_id	= modelLiq_part.getValueAt(x,12).toString().trim();	
//				Double tran_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,9).toString());	
//				//if (tran_amt == 0){}else{add_remarks1 =add_remarks1.concat("\n") + FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"+entity_id+"'") + " - " +  tran_amt ; }
//				if (tran_amt == 0){}else{add_remarks1 =add_remarks1.concat("\n")  +   tran_amt + " - " +  FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"+entity_id+"'")  ; }
//				x++;
//			}
//
//			//Auto-creation of payment request when there is a refund
//			Double refund_amt	= Double.parseDouble(modelLiq_part_total.getValueAt(0,11).toString());	
//			refund_rplf_no = sql_getNextRPLFno();	
//			
//			String cv_no= txtDV_no.getText();
//			//String strCheck = FncGlobal.GetString("select check_no from rf_check values where cv_no='"+cv_no+"'");
//			String strCheck = getCheckNo(cv_no, rplf_no, co_id);//ADDED BY JED 2020-02-04 : TO GET THE CORRECT CHECK NO
//			String liq_remarks = 
//				txtDV_particular.getText().trim() + "; " + "\n" + 
//				" THRU LIQ No. " + next_liq_no + "; " +  "\n" +
//			"Check No."+strCheck ;
//			
//			
//			//if(PayableVoucher.getPayment_type()==B)?"":"Check No.+(CheckVoucher.txtCheckNo.getText())");
//				if(refund_amt>0) {liq_remarks = liq_remarks + " Auto-Reimbursement RPLF No. : " + refund_rplf_no + "\n";} else {}
//				liq_remarks = liq_remarks +
//				" c/o : "+ getRequestAvailerIDName() + "; " +  "\n" +
//				getParticular_n_ReceiptNos().concat(add_remarks1) ;
//			
//				
//			
//			insertLiquiHeader(next_liq_no, db, liq_remarks);
//			insertLiquiDetails(next_liq_no, db);
//			insertJV_header(db, liq_remarks);
//			insertJV_detail(db, next_liq_no);
//			insertAudit_trail("Add-CA Liquidation", next_liq_no, db);
//			
//			txtDV_particular.setText("Liquidation of Cash Advance \n" + "RPLF No. : " + rplf_no + "\n" + "Reimbursement RPLF No. :" + refund_rplf_no );
//			
//			if(refund_amt>0)
//			{
//				insertRPLF_header(db, refund_amt);  
//				insertRPLF_detail(db);
//			}			
//			
//			
//			db.commit();	
//			lookupLiqNo.setText(next_liq_no);
//			txtJV_no.setText(jv_no);
//			enable_fields(false);						
//			tabCenter.setSelectedIndex(0);	
//			JOptionPane.showMessageDialog(getContentPane(),"Liquidation details saved.","Information",JOptionPane.INFORMATION_MESSAGE);		
//			jv_status = "ACTIVE";
//		}
//		
//		//Edit Liquidation
//		else {
//
//			liq_no = lookupLiqNo.getText().trim();
//			jv_no  = txtJV_no.getText().trim();
//			pgUpdate db = new pgUpdate();			
//
//			updateLiq_header(liq_no, db);
//			updateLiq_detail(liq_no, db);
//			updateJV_detail(jv_no, db);
//			insertLiquiDetails(liq_no, db);
//			insertJV_detail(db, liq_no);
//			insertAudit_trail("Edit-CA Liquidation", liq_no, db);
//
//			db.commit();	
//			lookupLiqNo.setText(liq_no);
//			enable_fields(false);								
//			tabCenter.setSelectedIndex(0);								
//			JOptionPane.showMessageDialog(getContentPane(),"Liquidation details updated.","Information",JOptionPane.INFORMATION_MESSAGE);
//			jv_status = "ACTIVE";
//		}
//
//		lblLiqNo.setEnabled(true);	
//		lookupLiqNo.setEnabled(true);
//		enableButtons(false, true, true, true, false, false, true, true, true );
//		modelLiq_part.setEditable(false, false);
//		generateDetail();
//		tagDetail.setText(null);
//	}

	private void save_main(){//used
		int rw = tblLiq_part.getModel().getRowCount();	
		int x = 0;
		String add_remarks1 = "";
		int liq_line_no = 1;
		String liq = lookupLiqNo.getText().trim();
		String or_no_ref,mc_no;
		
		//New Liquidation
		if(liq.equals("")) {							
			next_liq_no = sql_getNextLIQno();
			//pgUpdate db = new pgUpdate();	
			setJV_no();	
			//Added by Erick date 2019-08-19 DCRF 1174
			while(x<rw){
				
				String entity_id	= modelLiq_part.getValueAt(x,12).toString().trim();	
				Double tran_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,9).toString());	
				//if (tran_amt == 0){}else{add_remarks1 =add_remarks1.concat("\n") + FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"+entity_id+"'") + " - " +  tran_amt ; }
				if (tran_amt == 0){}else{add_remarks1 =add_remarks1.concat("\n")  +   tran_amt + " - " +  FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"+entity_id+"'")  ; }
				x++;
			}

			//Auto-creation of payment request when there is a refund
			Double refund_amt	= Double.parseDouble(modelLiq_part_total.getValueAt(0,11).toString());	
			refund_rplf_no = sql_getNextRPLFno();	
			
			String cv_no= txtDV_no.getText();
			//String strCheck = FncGlobal.GetString("select check_no from rf_check values where cv_no='"+cv_no+"'");
			String strCheck = getCheckNo(cv_no, rplf_no, co_id);//ADDED BY JED 2020-02-04 : TO GET THE CORRECT CHECK NO
			
			//Added by Erick 2023-08-04 DCRF 2683
			System.out.println("checktag: "+checktag(rplf_no, co_id));
			if(checktag(rplf_no, co_id)) {
				//Added by Erick 2023-08-04 (or_no_ref,mc_no)
				 or_no_ref  = FncGlobal.GetString ("select 'OR NO.: ' || coalesce(string_agg(remarks, ','), '') \n"
				 		+ "from (\n"
				 		+ "	select (case when c.pcostid_dl in ('215','216') \n"
				 		+ "					then c.rpt_or_no\n"
				 		+ "				else case when b.or_no_reference is not null then b.or_no_reference else  c.or_no_reference end\n"
				 		+ "			end) as remarks,\n"
				 		+ "	a.rplf_no \n"
				 		+ "	from rf_request_header a\n"
				 		+ "	left join rf_transfer_cost b on a.rplf_no = b.rplf_no and a.co_id = b.co_id\n"
				 		+ "	left join rf_processing_cost c on a.rplf_no = c.rplf_no and a.co_id = c.co_id\n"
				 		+ "	where a.rplf_no = '"+rplf_no+"' \n"
				 		+ "	and a.co_id = '"+co_id+"' \n"
				 		+ "	group by a.rplf_no,\n"
				 		+ "	b.or_no_reference , \n"
				 		+ "	c.or_no_reference, \n"
				 		+ "	rpt_or_no,\n"
				 		+ "	c.pcostid_dl \n"
				 		+ ") x group by x.rplf_no");
				 mc_no = FncGlobal.GetString("select 'Mc No.: ' || coalesce(string_agg(c.mc_no, ','), 'No MC') as mc_no\n"
						+ "from ( select distinct on (pv_no) pv_no, co_id  from rf_pv_detail\n"
						+ "where co_id = '"+co_id+"' and pv_no in ( select pv_no from  rf_pv_header where pv_no = '"+rplf_no+"'  and co_id = '"+co_id+"'  and status_id != 'I' and NULLIF(pv_no, '') is not null) \n"
						+ "and status_id = 'A' and bal_side = 'D' group by pv_no, co_id) a\n"
						+ "left join rf_pv_header b on a.pv_no = b.pv_no and a.co_id = b.co_id\n"
						+ "left join rf_mc_detail c on a.pv_no = c.pv_no and a.co_id = c.co_id\n"
						+ "and a.co_id = b.co_id");
			}else {
				 or_no_ref  = "";
				 mc_no = "";
			}
			
			String liq_remarks = 
				txtDV_particular.getText().trim().replace("'", "''") + "; " + "\n" + 
				" THRU LIQ No. " + next_liq_no + "; " +  "\n" +
			"Check No."+strCheck ;
					
			//if(PayableVoucher.getPayment_type()==B)?"":"Check No.+(CheckVoucher.txtCheckNo.getText())");
				if(refund_amt>0) {
					liq_remarks = liq_remarks + " Auto-Reimbursement RPLF No. : " + refund_rplf_no + "\n";
				} else {
					
				}
				liq_remarks = liq_remarks +
				" c/o : "+ getRequestAvailerIDName() + "; " +  "\n" +
				getParticular_n_ReceiptNos() + "\n"+or_no_ref +"\n"+mc_no.concat (add_remarks1).replace("'", "''");
			
			//**ADDED BY JED 2020-11-09 : TO AVOID SAVING OF UNBALANCED JV**//
			insertJV_detail_tmp(liq_no);
			
			System.out.printf("debit:%s\n", getDebitSide(co_id, jv_no));
			System.out.printf("credit:%s\n", getCreditSide(co_id, jv_no));
				
			if(getDebitSide(co_id, jv_no).equals(getCreditSide(co_id, jv_no))) {
				
				System.out.printf("debit:%s\n", getDebitSide(co_id, jv_no));
				System.out.printf("credit:%s\n", getCreditSide(co_id, jv_no));
				
				insertLiquiHeader(next_liq_no, liq_remarks);
				insertLiquiDetails(next_liq_no);
				insertJV_header(liq_remarks);
				insertJV_detail(next_liq_no);
				insertAudit_trail("Add-CA Liquidation", next_liq_no);
				updateJVTempTable(UserInfo.EmployeeCode, co_id, jv_no);
				
				txtDV_particular.setText("Liquidation of Cash Advance \n" + "RPLF No. : " + rplf_no + "\n" + "Reimbursement RPLF No. :" + refund_rplf_no );
				
				if(refund_amt>0)
				{
					insertRPLF_header(refund_rplf_no, refund_amt);  
					insertRPLF_detail(refund_rplf_no, next_liq_no);
				}			
				
				//db.commit();	
				lookupLiqNo.setText(next_liq_no);
				txtJV_no.setText(jv_no);
				enable_fields(false);						
				tabCenter.setSelectedIndex(0);	
				JOptionPane.showMessageDialog(getContentPane(),"Liquidation details saved.","Information",JOptionPane.INFORMATION_MESSAGE);		
				jv_status = "ACTIVE";
				
				lblLiqNo.setEnabled(true);	
				lookupLiqNo.setEnabled(true);
				enableButtons(false, true, true, true, false, false, true, true, true );
				modelLiq_part.setEditable(false, false);
				generateDetail();
				tagDetail.setText(null);
				
				refresh();
			}else {
				JOptionPane.showMessageDialog(getContentPane(),"Debit and credit side is not equal!","Error",JOptionPane.ERROR_MESSAGE);
				updateJVTempTable(UserInfo.EmployeeCode, co_id, jv_no);
				enableButtons(false, false, false, true, true, false, false, false, false );
			}	
		}
		
//		else {
//
//			liq_no = lookupLiqNo.getText().trim();
//			jv_no  = txtJV_no.getText().trim();
//			//pgUpdate db = new pgUpdate();	
//			
//			//**ADDED BY JED 2020-11-09 : TO AVOID SAVING OF UNBALANCED JV**//
//			insertJV_detail_tmp(liq_no);
//			
//			System.out.printf("debit:%s\n", getDebitSide(co_id, jv_no));
//			System.out.printf("credit:%s\n", getCreditSide(co_id, jv_no));
//			
//			if(getDebitSide(co_id, jv_no).equals(getCreditSide(co_id, jv_no))) {
//				
//				/*ADDED BY JED 2021-01-15 : FOR UPDATING REMARKS IF REFUND IS PRESENT*/
//				//Auto-creation of payment request when there is a refund
//				Double refund_amt	= Double.parseDouble(modelLiq_part_total.getValueAt(0,11).toString());	
//				refund_rplf_no = sql_getNextRPLFno();
//				
//				String liq_remarks = txtDV_particular.getText().trim();
//				
//				if(refund_amt>0)
//				{
//					insertRPLF_header(refund_amt);  
//					insertRPLF_detail();
//					
//					liq_remarks = "Auto-Reimbursement RPLF No. : " + refund_rplf_no + "\n" + liq_remarks;
//				}
//				
//				//updateLiq_header(liq_no);
//				updateLiq_header(liq_no, liq_remarks);
//				updateLiq_detail(liq_no);
//				updateJVHeader(jv_no, liq_remarks);/*ADDED BY JED 2021-01-15 : FOR UPDATING JV HEADER*/
//				updateJV_detail(jv_no);
//				insertLiquiDetails(liq_no);
//				insertJV_detail(liq_no);
//				insertAudit_trail("Edit-CA Liquidation", liq_no);
//
//				lookupLiqNo.setText(liq_no);
//				enable_fields(false);								
//				tabCenter.setSelectedIndex(0);								
//				JOptionPane.showMessageDialog(getContentPane(),"Liquidation details updated.","Information",JOptionPane.INFORMATION_MESSAGE);
//				jv_status = "ACTIVE";
//				
//				lblLiqNo.setEnabled(true);	
//				lookupLiqNo.setEnabled(true);
//				enableButtons(false, true, true, true, false, false, true, true, true );
//				modelLiq_part.setEditable(false, false);
//				generateDetail();
//				tagDetail.setText(null);	
//			}else {
//				JOptionPane.showMessageDialog(getContentPane(),"Debit and credit side is not equal!","Error",JOptionPane.ERROR_MESSAGE);
//				updateJVTempTable(UserInfo.EmployeeCode, co_id, jv_no);
//				enableButtons(false, false, false, true, true, false, false, false, false );
//			}
//		}
		
		//Edit Liquidation
		else {
			
			liq_no = lookupLiqNo.getText().trim();
			jv_no  = txtJV_no.getText().trim();
			String cv_no = txtDV_no.getText();
			String old_rplf_no = lookupRequest.getText();
			String strCheck = getCheckNo(cv_no, old_rplf_no, co_id);
			String old_remarks = txtDV_particular.getText().replace("'", "''");
			rplf_no = lookupRequest.getText();
			//pgUpdate db = new pgUpdate();	
			
			//**ADDED BY JED 2020-11-09 : TO AVOID SAVING OF UNBALANCED JV**//
			insertJV_detail_tmp(liq_no);
			
			System.out.printf("debit:%s\n", getDebitSide(co_id, jv_no));
			System.out.printf("credit:%s\n", getCreditSide(co_id, jv_no));
			
			if(getDebitSide(co_id, jv_no).equals(getCreditSide(co_id, jv_no))) {
				
				while(x<rw){
					
					String entity_id	= modelLiq_part.getValueAt(x,12).toString().trim();	
					Double tran_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,9).toString());	
					//if (tran_amt == 0){}else{add_remarks1 =add_remarks1.concat("\n") + FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"+entity_id+"'") + " - " +  tran_amt ; }
					if (tran_amt == 0){}else{add_remarks1 =add_remarks1.concat("\n")  +   tran_amt + " - " +  FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"+entity_id+"'")  ; }
					x++;
				}
				
				String liq_remarks = "";
				
				//if refund is present
				Double refund_amt	= Double.parseDouble(modelLiq_part_total.getValueAt(0,11).toString());
				if(refund_amt>0) {
					if(isTrue(liq_no)) {
						System.out.println("Already refund!!!");
						liq_remarks = txtDV_particular.getText();
						String gnrtd_rplf = getRplfNoforRefund(liq_no);
						
						updateRPLF_header(gnrtd_rplf, refund_amt);
						updateRPLF_detail(gnrtd_rplf, refund_amt);
						
					}else {	
						System.out.println("First refund!!!");
						refund_rplf_no = sql_getNextRPLFno();

						if(refund_amt>0){
							liq_remarks = " TO LIQUIDATE " + "RPLF No. : " + old_rplf_no + "; " + "\n" +
							" WITH DV No." +cv_no+"\n" +
							" THRU LIQ No. " + liq_no + "; " +  "\n" +
							"Check No."+strCheck+ " Auto-Reimbursement RPLF No. : " + refund_rplf_no + "\n" +
							" c/o : "+ getRequestAvailerIDName() + "; " +  "\n" +
							getParticular_n_ReceiptNos().concat(add_remarks1).replace("'", "''");
							
							insertRPLF_header(refund_rplf_no, refund_amt);  
							insertRPLF_detail(refund_rplf_no, liq_no);
						} else {}
					}
				}else {
//					liq_remarks = " TO LIQUIDATE " + "RPLF No. : " + old_rplf_no + "; " + "\n" +
//					" WITH DV No." +cv_no+"\n" +
//					" THRU LIQ No. " + liq_no + "; " +  "\n" +
//					"Check No."+strCheck+ 
//					" c/o : "+ getRequestAvailerIDName() + "; " +  "\n" +
//					getParticular_n_ReceiptNos().concat(add_remarks1);
					
					liq_remarks = old_remarks;
				}
				
				//updateLiq_header(liq_no);
				updateLiq_header(liq_no, liq_remarks);
				updateLiq_detail(liq_no);
				updateJVHeader(jv_no, liq_remarks);/*ADDED BY JED 2021-01-15 : FOR UPDATING JV HEADER*/
				updateJV_detail(jv_no);
				insertLiquiDetails(liq_no);
				insertJV_detail(liq_no);
				insertAudit_trail("Edit-CA Liquidation", liq_no);

				lookupLiqNo.setText(liq_no);
				enable_fields(false);								
				tabCenter.setSelectedIndex(0);								
				JOptionPane.showMessageDialog(getContentPane(),"Liquidation details updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				jv_status = "ACTIVE";
				
				lblLiqNo.setEnabled(true);	
				lookupLiqNo.setEnabled(true);
				enableButtons(false, true, true, true, false, false, true, true, true );
				modelLiq_part.setEditable(false, false);
				generateDetail();
				tagDetail.setText(null);	
				
				refresh();
			}else {
				JOptionPane.showMessageDialog(getContentPane(),"Debit and credit side is not equal!","Error",JOptionPane.ERROR_MESSAGE);
				updateJVTempTable(UserInfo.EmployeeCode, co_id, jv_no);
				enableButtons(false, false, false, true, true, false, false, false, false );
			}
		}

//		lblLiqNo.setEnabled(true);	
//		lookupLiqNo.setEnabled(true);
//		enableButtons(false, true, true, true, false, false, true, true, true );
//		modelLiq_part.setEditable(false, false);
//		generateDetail();
//		tagDetail.setText(null);
	}
	
	private String getRplfNoforRefund(String liq_no) {
		
		String rplf_no = "";
		
		String sql = "select \n" + 
				"left(trim(right(remarks, length(remarks)-(position('Auto-Reimbursement RPLF No.' in remarks)+29))),9) \n" + 
				"--position('Auto-Reimbursement RPLF No.' in remarks)+30\n" + 
				"from rf_liq_header\n" + 
				"where liq_no = '"+liq_no+"' and status_id = 'A'";
		
		FncSystem.out("Get RPLF No Refund:", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			rplf_no = db.getResult()[0][0].toString();
		}else {
			rplf_no = "";
		}
		
		return rplf_no;
	}
	
	private Boolean isTrue(String liq_no) {
		
		Boolean x = false;
		
		String sql = "select liq_no from rf_liq_header where remarks ~* 'Auto-Reimbursement' and status_id = 'A' and liq_no = '"+liq_no+"'";
		
		FncSystem.out("If already refund:", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			x = true;
		}else {
			x = false;
		}
		
		return x;
	}
	
	private static String getDebitSide(String co_id, String jv_no) {//**ADDED BY JED 2020-11-09 : TO AVOID SAVING OF UNBALANCED JV**//
		
		String debit_amt = "";
		
		String sql = "select\n" + 
				"sum(tran_amt)::varchar as debit\n" + 
				"from tmp_jv_detail\n" + 
				"where jv_no = '"+jv_no+"'\n" + 
				"and bal_side = 'D'\n" + 
				"and status_id = 'A'\n" + 
				"and co_id = '"+co_id+"'";
		
		FncSystem.out("Get Debit side", sql);
		pgSelect db1 = new pgSelect();
		db1.select(sql);
		
		if(db1.isNotNull()){
			debit_amt = (String) db1.getResult()[0][0];
		}else{
			debit_amt = "";
		}
		return debit_amt;
	}
	
	private static String getCreditSide(String co_id, String jv_no) {//**ADDED BY JED 2020-11-09 : TO AVOID SAVING OF UNBALANCED JV**//
		
		String credit_amt = "";
		
		String sql = "select\n" + 
				"sum(tran_amt)::varchar as debit\n" + 
				"from tmp_jv_detail\n" + 
				"where jv_no = '"+jv_no+"'\n" + 
				"and bal_side = 'C'\n" + 
				"and status_id = 'A'\n" + 
				"and co_id = '"+co_id+"'";
		
		FncSystem.out("Get Credit side", sql);
		pgSelect db2 = new pgSelect();
		db2.select(sql);
		
		if(db2.isNotNull()){
			credit_amt = (String) db2.getResult()[0][0];
		}else{
			credit_amt = "";
		}
		return credit_amt;
	}
	
	private static String getCheckNo(String cv_no, String pv_no, String co_id) {//ADDED BY JED 2020-02-04 : TO GET THE CORRECT CHECK NO
		
		String check_no = "";
		String sql =
				"select c.check_no\n" + 
				"from rf_pv_detail a \n" + 
				"inner join rf_pv_header b on a.pv_no = b.pv_no \n" + 
				"left join rf_check c on b.cv_no = c.cv_no /*and b.oth_ref_no = c.check_no*/ and b.co_id = c.co_id  \n" + 
				"where b.cv_no = '"+cv_no+"' and b.co_id = '"+co_id+"' and a.pv_no = '"+pv_no+"'\n" + 
				"and a.status_id = 'A' and a.bal_side = 'D' \n" + 
				"group by a.pv_no, b.pv_date, c.check_no, c.date_due limit 1";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			check_no = (String) db.getResult()[0][0];
		}else{
			check_no = "";
		}
		return check_no;
	}

	private void save_references(){//used
		pgUpdate db = new pgUpdate();	
		updateReferences(lookupLiqNo.getText().trim(), db);
		insertAudit_trail("Edit References-CA Liquidation", lookupLiqNo.getText().trim());
		db.commit();
		JOptionPane.showMessageDialog(getContentPane(),"Liquidation reference details updated.","Information",JOptionPane.INFORMATION_MESSAGE);
		enableButtons(false, false, true, true, false, true, true, false, true );
		modelLiq_part.setEditable(false, false);
		tagDetail.setText(null);
	}

	private void tagLiqui(){//used

		if(checkOR_submission()==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Please tag the complete references (ID, Number and Date)","Information",JOptionPane.INFORMATION_MESSAGE);} 		
		else {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();	
				tagLiquiheader(db);
				insertAudit_trail("Tag-CA Liquidation", lookupLiqNo.getText().trim());
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"Liquidation details successfully tagged.","Information",JOptionPane.INFORMATION_MESSAGE);
				enableButtons(false, false, true, true, false, false, false, false, true );
				modelLiq_part.setEditable(false, false);	
				txtStatus.setText("PROCESSED");
			}
		}

	}


	//select, lookup and get statements	
	/*private String getDV_no(String co_id){//used
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
		"rf_cv_header a\r\n" + 
		"left join rf_entity b on a.entity_id1=b.entity_id\r\n" + 
		"left join rf_cv_detail c on a.cv_no = c.cv_no and a.co_id = c.co_id " +
		"left join mf_record_status d on a.status_id = d.status_id  " +
		"where a.co_id = '"+co_id+"' " +
		"order by a.cv_no desc "  ;

	}*/

	private String getRequestList(String co_id){//used
		//**edited by jed 2020-02-11 : proc_id 13 & 14 was added on filter
		
		String sql = 
		
		"-----------Display Unliquidated Cash Advances\r\n" + 		
		"select a.pv_no  as \"RPLF No.\" ,\n" + 
		"a.rplf_type_id  as \"Type\"  ,\n" + 
		"a.pv_date as \"PV Date\",  \n" + 
		"a.cv_no  as \"DV No.\", a.remarks as \"Remarks\", \n" + 
		"a.status_desc  as \"Status\"  \n" + 
		" from ( \n" + 
		"select distinct on (a.pv_no)\n" + 
		"a.pv_no ,\n" + 
		"e.rplf_type_id   ,\n" + 
		"a.pv_date ,  \n" + 
		"a.cv_no 	,\n" + 
		"c.status_desc,d.proc_id,trim(a.remarks) as remarks \n" + 
		"from ( select * from rf_pv_header where cv_no is not null or trim(cv_no) != '' ) a\n" + 
		"left join rf_pv_detail b on a.pv_no = b.pv_no and a.co_id = b.co_id\n" + 
		"left join mf_record_status c on a.status_id = c.status_id  \n" + 
		"left join rf_cv_header d on a.cv_no = d.cv_no and a.co_id =d.co_id  \n" + 
		"left join rf_request_header e on a.rplf_no = e.rplf_no and a.co_id = e.co_id\n" + 
		"where b.acct_id in ( \n" + 
		"'01-02-04-000') \n" + 
		"and a.status_id = 'P'\n" + 
		"and b.bal_side = 'D'\n" + 
		"and a.pv_no not in ( select rplf_no from rf_liq_header where status_id in ('A', 'G', 'P' ) and co_id = '"+co_id+"' )\n" + 
		"and a.co_id = '"+co_id+"' \n" + 
		"and e.rplf_type_id in ('02', '07', '14') ) a   \n" + 
		"where trim(a.cv_no) != '' and a.proc_id in ('5', '13', '14','3')	\n" + // To show/view pv that has proc.id ='03' ADDED by Tim 10-19-2022
		"order by a.pv_date desc" ;
		System.out.printf("getRequestList : " + sql);
		
		return sql;
	}

	private String getDivision(){//used

		String sql = "select trim(division_code) as \"Div Code\", " +
		"trim(division_name) as \"Div Name\", " +
		"division_alias as \"Div Alias\" " +
		"from mf_division " ;		
		return sql;

	}	

	private String getLiquiList(){//**EDITED BY JED VICEDO 2019-07-29 : ONLY CASH ADVANCE AND CASH FUND ADVANCE ARE ALLOWED TO BE LIQUIDATED**//

		String sql = 
			"select   			 \n" + 
			"a.liq_no as \"Liqui. No.\",\n" + 
			"a.liq_date as \" Liqui. Date\"    ,\n" + 
			"a.rplf_no  as \" RPLF No.\"  ,\n" + 
			"a.jv_no   as \" JV No.\"   ,\n" + 
			"b.status_desc  as \" Status\" \n" + 
			"from rf_liq_header a\n" + 
			"left join mf_record_status b on a.status_id = b.status_id\n" + 
			"left join rf_request_header c on a.rplf_no = c.rplf_no and c.status_id = 'A' and c.co_id = a.co_id\n" + 
			"where a.co_id = '"+co_id+"'\n" + 
			"and c.rplf_type_id in ('02', '07', '14')\n" + 
			"order by a.liq_no desc" ;	

		FncSystem.out("Liquidation SQL", sql);
	
		return sql;
		
		
	}

	private Object [] getLiquiHeaderDetails() {//EDITED BY JED 2022-01-21 - ADDITIONAL COLUMNS FOR GL YEAR AND PERIOD ID

		String strSQL = 
			"---display Liquidation header\r\n" + 
			"select \r\n" + 

			"a.liq_no,\r\n" + 
			"a.liq_date,\r\n" + 
			"a.rplf_no,\r\n" + 
			"a.jv_no,\r\n" + 
			"c.cv_no,\r\n" + 
			"b.status_desc,\r\n" + 
			"a.remarks, \r\n" + 
			"trim(e.status_desc), \n " +
			"trim(g.rplf_type_desc) as liq_type, \n" +
			"nullif(right(left((liq_date)::varchar, 7),2),'') as month,\n" + 
			"nullif(left((liq_date)::varchar, 4),'') as gl_year\n" +

			"from (select * from rf_liq_header where co_id = '"+co_id+"') a\r\n" + 			
			"left join mf_record_status b on a.status_id = b.status_id\r\n" + 			
			"left join (select * from rf_pv_header where co_id = '"+co_id+"') c on a.rplf_no = c.rplf_no and a.co_id = c.co_id \n" +
			"left join (select * from rf_jv_header where co_id = '"+co_id+"') d on a.jv_no = d.jv_no and a.co_id = d.co_id \n" +
			"left join mf_record_status e on d.status_id = e.status_id \r\n" + 		
			"left join (select * from rf_request_header where co_id = '"+co_id+"') f on a.rplf_no = f.rplf_no\r\n" + 
			"left join mf_rplf_type g on f.rplf_type_id = g.rplf_type_id \r\r " +
			"where a.liq_no = '"+liq_no+"' "  ;

		FncSystem.out("getLiquiHeaderDetails", strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	private String sql_getNextLIQno() {//used

		String SQL = 
			"select trim(to_char(max(coalesce(liq_no::int,0))+1,'000000000')) from rf_liq_header where co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {next_liq_no = "000000001";}
			else {next_liq_no = (String) db.getResult()[0][0]; }
		}else{
			next_liq_no = "000000001";
		}

		return next_liq_no;
	}

	private String getChartofAccount(){//used

		/*String sql = "select " +
		"acct_id as \"Acct ID\", " +
		"acct_name as \"Acct Name\",    " +
		"bs_is as \"Balance\"  " +
		"from mf_boi_chart_of_accounts where status_id = 'A' and w_subacct is null ";		
		return sql;*/
		
		String sql = "select " +
		"acct_id as \"Acct ID\", " +
		"trim(acct_name) as \"Acct Name\",    " +
		"bs_is as \"Balance\"  " +
		"from mf_boi_chart_of_accounts " +
		"where status_id = 'A' " +
		"and (w_subacct is null OR filtered = false)" + //ADDED FILTERED BY LESTER DCRF 2719
		"order by acct_id ";		

		System.out.printf("getChartofAccount : " + sql);

		return sql;

	}

	private String getDepartment(){//used

		int row    = tblLiq_part.getSelectedRow();
		String div = modelLiq_part.getValueAt(row,3).toString();

		String sql = "select dept_code as \"Dept Code\", " +
		"trim(dept_name) as \"Dept Name\", " +
		"dept_alias as \"Dept Alias\" " +
		"from mf_department " ;
		if (div.equals("")){sql = sql + "";} 
		else {sql = sql + "where division_code = '"+div+"' ";}

		return sql;

	}	

	private String getProject(String co_id){//used

		String sql = "select a.proj_id as \"Project ID\", " +
		"trim(a.proj_name) as \"Project Name\", " +
		"a.proj_alias as \"Project Alias\", " +
		"b.sub_proj_id as \"SubProject ID\", " +
		"a.vatable as \"Vatable\" " +
		"from mf_project a " +
		"left join ( select distinct on (proj_id) proj_id, sub_proj_id from mf_unit_info ) b  on a.proj_id=b.proj_id where a.status_id='A'"+ 
		"and a.co_id = '"+co_id+"'";
		
		FncSystem.out("PROJECT DITO",sql);
		
		return sql;

	}	

	private String getSubproject(_JTableMain table, DefaultTableModel model, Integer col_no){//used
		int row = table.getSelectedRow();
		String proj = model.getValueAt(row,col_no).toString();
		String sql = 
			"select \r\n" + 
			"distinct on (a.proj_id, a.sub_proj_id)\r\n" + 
			"\r\n" + 
			"a.sub_proj_id  as \"Subproj Code\",\r\n" + 
			"a.phase as \"Phase\",  \r\n" + 
			"a.proj_id as \"Proj Code\",\r\n" + 
			"b.proj_name as \"Proj Name\"  \r\n" + 

			"from mf_sub_project a\r\n" + 
			"left join mf_project b on a.proj_id = b.proj_id\r\n and b.status_id='A'\r\n" + 
			"where co_id = '"+co_id+"' and a.proj_id = '"+proj+"' and phase != '' AND a.status_id = 'A'" +//ADDED STATUS ID BY LESTER DCRF 2718
			"--and sub_proj_id is not null or sub_proj_id != '' " ;	/*EDITED BY JED 2021-08-18 : TO FILTER SUB PROJECTS ACCDNG TO COID AND PROJID*/
		return sql;

	}	

	private String getOrigTransAmount(Integer line_no){//used

		String orig_trans_amt = "0.00";

		String SQL = 
			"select amount::text from rf_request_detail " +
			"where rplf_no = '"+lookupRequest.getText().trim()+"' " +
			"and line_no = "+line_no+" " +
			"and co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			orig_trans_amt = (String) db.getResult()[0][0];
		}else{
			orig_trans_amt = "0.00";
		}

		return orig_trans_amt;

	}	
	
	/*private String getEntityID(String emp_code){//used

		String emp_entity_id = "";

		String SQL = 
			"select entity_id from em_employee where trim(emp_code) = '"+emp_code+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			emp_entity_id = (String) db.getResult()[0][0];
		}else{
			emp_entity_id = "";
		}

		return emp_entity_id;

	}	*/
	
	private String getRequestAvailerID(){//used

		String request_availer = "";

		String SQL = 
			"select entity_id2 from rf_request_header \n" +
			"where trim(rplf_no) = '"+rplf_no+"' and co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			request_availer = (String) db.getResult()[0][0];
		}else{
			request_availer = "";
		}

		return request_availer;

	}	
	
	private String getRequestAvailerIDName(){//used

		String request_availer_name = "";

		String SQL = 
			"select \r\n" + 
			"trim(b.entity_name) as availer \r\n" + 
			"from rf_request_header a \r\n" + 
			"left join rf_entity b on a.entity_id2 = b.entity_id \n" +
			"where trim(a.rplf_no) = '"+rplf_no+"' and a.co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			request_availer_name = (String) db.getResult()[0][0];
		}else{
			request_availer_name = "";
		}

		return request_availer_name;

	}	
	
	private String getReceiptTypeAlias(String x){//used

		String recpt_alias = "";

		String SQL = 
			"select doc_alias from mf_system_doc where doc_id = '"+x+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			recpt_alias = (String) db.getResult()[0][0];
		}else{
			recpt_alias = "";
		}

		return recpt_alias;

	}	

	private String sql_getNextRPLFno() {//EDITED BY JED 2021-05-17 : CHANGE THE GENERATION OF RPLF INSIDE THE FUNCTION

		String rplf = "";

//		String SQL = 
//			"select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+co_id+"' " ;
		String SQL = 
				"select * from fn_get_rplf_no('"+lookupCompany.getValue()+"')" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {rplf = "000000001";}
			else {rplf = (String) db.getResult()[0][0]; }

		}else{
			rplf = "000000001";
		}

		return rplf;
	}
	
	private String getPV_remarks(){//used

		String request_availer = "";

		String SQL = 
			"select remarks from rf_pv_header \n" +
			"where trim(rplf_no) = '"+rplf_no+"' and co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			request_availer = (String) db.getResult()[0][0];
		}else{
			request_availer = "";
		}

		return request_availer;

	}	
	
	private String getRequestParticular(){//used

		String request_particular = "";

		String SQL = 
			"select remarks from rf_pv_header \n" +
			"where trim(pv_no) = '"+rplf_no+"' and co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			request_particular = (String) db.getResult()[0][0];
		}else{
			request_particular = "";
		}

		return request_particular;

	}
	
	
	//check and validate	
	private Boolean checkOR_submission(){//used

		boolean boo = false;	

		int rw = tblLiq_part.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {	

			String doc_no 	= "";
			String doc_id 	= "";
			String entity_id = "";
			String entity_typeid = "";
			Double amount = null;

			try { entity_id 	= modelLiq_part.getValueAt(x,12).toString();	} 
			catch (NullPointerException e) { entity_id = ""; }
			try { entity_typeid 	= modelLiq_part.getValueAt(x,13).toString();	} 
			catch (NullPointerException e) { entity_typeid = ""; }
			try { doc_no 	= modelLiq_part.getValueAt(x,15).toString();	} 
			catch (NullPointerException e) { doc_no = ""; }
			try { doc_id 	= modelLiq_part.getValueAt(x,14).toString();	} 
			catch (NullPointerException e) { doc_id = ""; }
			try { doc_id 	= modelLiq_part.getValueAt(x,16).toString();	} 
			catch (NullPointerException e) { doc_id = ""; }
			try { amount 	= Double.parseDouble(modelLiq_part.getValueAt(x,9).toString());	} 
			catch (NullPointerException e) { amount = 0.00; }

			if (amount==0){}
			else 
			{
				if (doc_no.equals("")||doc_id.equals("")||
						doc_no.equals(null)||doc_id.equals(null)||
						entity_id.equals("")||entity_id.equals(null)||
						entity_typeid.equals("")||entity_typeid.equals(null)||
						modelLiq_part.getValueAt(x,16)==null) 
				{boo=false; break;} 
				else {boo=true;}	
			}			
			
			x++;
		}		

		return boo;

	}	


	//table operations
	private void totalLiqui(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		
		
		Double trans_amt 	= 0.00;
		Double return_amt 	= 0.00;
		Double refund_amt 	= 0.00;
		Double wtax_amt 	= 0.00;	
		Double vat_amt 		= 0.00;
		Double exp_amt 		= 0.00;
		Double ca_amt 		= 0.00;		

		for(int x=0; x < modelMain.getRowCount(); x++){	
			
			Double trans 	= 0.00;
			Double retrn 	= 0.00;
			Double refund 	= 0.00;
			Double wtax 	= 0.00;	
			Double vat 		= 0.00;
			Double exp 		= 0.00;
			Double ca		= 0.00;		
			
			try { trans = Double.parseDouble(modelLiq_part.getValueAt(x,9).toString().trim());} catch (NullPointerException e) { trans = 0.00; }
			try { retrn = Double.parseDouble(modelLiq_part.getValueAt(x,10).toString().trim());} catch (NullPointerException e) { retrn = 0.00; }
			try { refund = Double.parseDouble(modelLiq_part.getValueAt(x,11).toString().trim());} catch (NullPointerException e) { refund = 0.00; }
			try { wtax = Double.parseDouble(modelLiq_part.getValueAt(x,24).toString().trim());} catch (NullPointerException e) { wtax = 0.00; }
			try { vat = Double.parseDouble(modelLiq_part.getValueAt(x,25).toString().trim());} catch (NullPointerException e) { vat = 0.00; }
			try { exp = Double.parseDouble(modelLiq_part.getValueAt(x,26).toString().trim());} catch (NullPointerException e) { exp = 0.00; }
			try { ca = Double.parseDouble(modelLiq_part.getValueAt(x,27).toString().trim());} catch (NullPointerException e) { ca = 0.00; }
			
			trans_amt 	= trans_amt + trans;
			return_amt 	= return_amt + retrn;
			refund_amt 	= refund_amt + refund;
			wtax_amt 	= wtax_amt + wtax;	
			vat_amt 	= vat_amt + vat;
			exp_amt 	= exp_amt + exp;
			ca_amt 		= ca_amt + ca;					
			
			/*try { trans_amt 	= trans_amt.add(((BigDecimal) modelMain.getValueAt(x,10)));} 
			catch (NullPointerException e) { trans_amt 	= trans_amt.add(new BigDecimal(0.00)); }

			try { return_amt 	= return_amt.add(((BigDecimal) modelMain.getValueAt(x,10)));} 
			catch (NullPointerException e) { return_amt 	= return_amt.add(new BigDecimal(0.00)); }

			try { refund_amt 	= refund_amt.add(((BigDecimal) modelMain.getValueAt(x,11)));} 
			catch (NullPointerException e) { refund_amt 	= refund_amt.add(new BigDecimal(0.00)); }

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,24)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { vat_amt 	= vat_amt.add(((BigDecimal) modelMain.getValueAt(x,25)));} 
			catch (NullPointerException e) { vat_amt 	= vat_amt.add(new BigDecimal(0.00)); }

			try { exp_amt 	= exp_amt.add(((BigDecimal) modelMain.getValueAt(x,26)));} 
			catch (NullPointerException e) { exp_amt 	= exp_amt.add(new BigDecimal(0.00)); }

			try { ca_amt 	= ca_amt.add(((BigDecimal) modelMain.getValueAt(x,27)));} 
			catch (NullPointerException e) { ca_amt 	= ca_amt.add(new BigDecimal(0.00)); }	*/

		}		
		
		BigDecimal trans_amt_bd 	= new BigDecimal(trans_amt);	
		BigDecimal return_amt_bd 	= new BigDecimal(return_amt);	
		BigDecimal refund_amt_bd 	= new BigDecimal(refund_amt);	
		BigDecimal wtax_amt_bd 		= new BigDecimal(wtax_amt);	
		BigDecimal vat_amt_bd 		= new BigDecimal(vat_amt);	
		BigDecimal exp_amt_bd 		= new BigDecimal(exp_amt);	
		BigDecimal ca_amt_bd 		= new BigDecimal(ca_amt);	

		modelTotal.addRow(new Object[] { "Total", null,  null,null,null,null,null,null,null, trans_amt_bd, return_amt_bd,refund_amt_bd
				,null,null,null,null,null,null,null,null,null,null,null,null,wtax_amt_bd, vat_amt_bd,exp_amt_bd,ca_amt_bd});
	}

	private void clickTableColumn() {//used
		
		System.out.println("Dumaan sa clickTableColumn");

		int column = tblLiq_part.getSelectedColumn();
		int row = tblLiq_part.getSelectedRow();		
		String entity_id = "";
		try {  entity_id = modelLiq_part.getValueAt(row,12).toString().trim(); } catch (NullPointerException e) { entity_id = ""; }
		

		Integer x[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
		String sql[] = {"","",getChartofAccount(),getDivision(), getDepartment(),"",getProject(co_id),getSubproject(tblLiq_part, modelLiq_part,6),
				"","","","",getEntityList(), getEntity_type(entity_id), RequestForPayment.getReferenceDoc(),
				"","","","","","","",getWTaxID(entity_id),"","","","","",""};
		String lookup_name[] = {"","","Chart of Account","Division","Department","","Project","SubProject",
				"","","","","Entity","Entity Type", "Reference Doc.",
				"","","","","","","","Withholding Tax","","","","","",""};	

		if (column == 16 && modelLiq_part.isEditable() ) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Reference Doc. Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

			if ( scanOption == JOptionPane.CLOSED_OPTION ) {
				try {						
					modelLiq_part.setValueAt(dteRefDate.getDate(), tblLiq_part.getSelectedRow(), 16);
				} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
			} // CLOSED_OPTION

		}

		if (column == x[column] && modelLiq_part.isEditable() && sql[column]!="" && !jv_status.equals("POSTED")) {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			if(column == 2) {
				dlg.setFilterIndex(1);
			}else if(column == 12) {
				dlg.setFilterIndex(2);
			}else {
				dlg.setFilterIndex(0);
			}
			
			dlg.setFilterClientName(true);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 2 ) {	
				System.out.println("Dumaan sa clickTableColumn 2");
				modelLiq_part.setValueAt(data[0], row, column);
				modelLiq_part.setValueAt(data[1], row, column+6);
			} else if (data != null && column == 3 )  {
				System.out.println("Dumaan sa clickTableColumn 3");
				//insertStringToAllRows(data[0].toString(),column);
				//removeToAllRows(column+1);
				modelLiq_part.setValueAt(data[0], row, column);
				modelLiq_part.setValueAt("", row, column+1);
				tagDetail.setText(String.format("%s-%s-%s", data[1]));
			} else if (data != null && column == 4 )  {	
				System.out.println("Dumaan sa clickTableColumn 4");
				//insertStringToAllRows(data[0].toString(),column);
				modelLiq_part.setValueAt(data[0], row, column);
			} else if (data != null && column == 6 )  {		
				System.out.println("Dumaan sa clickTableColumn 5");
				//insertStringToAllRows(data[0].toString(),column);
				//removeToAllRows(column+1);
				modelLiq_part.setValueAt(data[0], row, column);
				modelLiq_part.setValueAt("", row, column+1);
			} else if (data != null && column == 7 )  {	
				System.out.println("Dumaan sa clickTableColumn 6");
				//insertStringToAllRows(data[0].toString(),column);
				modelLiq_part.setValueAt(data[0], row, column);
			} else if (data != null && column == 12)  {
				System.out.println("Dumaan sa clickTableColumn 7");	
				//insertStringToAllRows(data[0].toString(),column);
				modelLiq_part.setValueAt(data[0], row, column);				
				/*entity_id = (String) data[0];
				Boolean isVatable = (Boolean) data[3];
				if(isVatable==true)
				{
					//modelLiq_part.setValueAt(true, row, 18);
					insertBooleanToAllRows(true,18);
					//modelLiq_part.setValueAt(12.0, row, 21); 
					insertStringToAllRows("12.0",column);
				}
				else 
				{
					//modelLiq_part.setValueAt(false, row, 18);
					insertBooleanToAllRows(false,18);
					//modelLiq_part.setValueAt(0.00, row, 21);
					insertStringToAllRows("0.00",column);
				}
				computeBillAmounts();*/
			} else if (data != null && column == 13)  {	
				System.out.println("Dumaan sa clickTableColumn 8");
				//insertStringToAllRows(data[0].toString(),column);
				modelLiq_part.setValueAt(data[0], row, column);
			} else if (data != null && column == 14)  {	
				modelLiq_part.setValueAt(data[0], row, column);
			}
			else if (data != null && column == 22)  {	
				System.out.println("Dumaan sa clickTableColumn 9");
				modelLiq_part.setValueAt(data[0], row, column);
				modelLiq_part.setValueAt(data[2], row, column+1);
				computeBillAmounts();
			}				
			else if (data != null)  {
				System.out.println("Dumaan sa clickTableColumn 10");
				modelLiq_part.setValueAt(data[0], row, column);
			}
		}		

		else if (column == x[14] && modelLiq_part.isEditable() && sql[column]!="" && jv_status.equals("POSTED")) {  
			System.out.println("Dumaan sa clickTableColumn 11");
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			if(column == 2) {
				dlg.setFilterIndex(1);
			}else if(column == 12) {
				dlg.setFilterIndex(2);
			}else {
				dlg.setFilterIndex(0);
			}
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();		
			if (data != null && column == 14)  {	
				System.out.println("Dumaan sa clickTableColumn 12");
				modelLiq_part.setValueAt(data[0], row, column);
			}else if (data != null && column == 12)  {		
				System.out.println("Dumaan sa clickTableColumn 13");
				modelLiq_part.setValueAt(data[0], row, column);
			} else if (data != null && column == 13)  {		
				System.out.println("Dumaan sa clickTableColumn 14");
				modelLiq_part.setValueAt(data[0], row, column);
			}
		}	

		else {}

		//totalLiqui(modelLiq_part, modelLiq_part_total);		
		tblLiq_part.packAll();
	}

	private void computeBillAmounts(KeyEvent evt){//used
		
		System.out.println("Dumaan sa computeBillAmounts");

		if (modelLiq_part.isEditable() && !jv_status.equals("POSTED")) {

			int c  = tblLiq_part.getSelectedColumn();
			int r  = tblLiq_part.getSelectedRow();
			int selected_row = tblLiq_part.convertRowIndexToModel(tblLiq_part.getSelectedRow());
			
			/**COMMENTED BY JED**/
			//for (int x = 0; x < modelLiq_part.getRowCount(); x++) {
			/*double trans_amt	= 0.00;
			if(txtJV_no.getText().trim().equals("")) // meaning - new liquidation is being created
			{trans_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,28).toString());}
			else // meaning - liquidation is for editing
			{Integer line = Integer.parseInt(modelLiq_part.getValueAt(x,0).toString());
			trans_amt = Double.parseDouble(getOrigTransAmount(line));}

			double tranamt	= Double.parseDouble(modelLiq_part.getValueAt(x,9).toString());
			double return_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,10).toString());	
			double refund_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString());		
			double wtax_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,24).toString());
			double vat_rate		= Double.parseDouble(modelLiq_part.getValueAt(x,21).toString())/100;
			double tranamt2	= Double.parseDouble(modelLiq_part.getValueAt(r,9).toString());*/
			/**COMMENTED BY JED**/
			
			
			double trans_amt	= 0.00;
			if(txtJV_no.getText().trim().equals("")) // meaning - new liquidation is being created
			{trans_amt	= Double.parseDouble(modelLiq_part.getValueAt(selected_row,28).toString());}
			else // meaning - liquidation is for editing
			{Integer line = Integer.parseInt(modelLiq_part.getValueAt(selected_row,0).toString());
			trans_amt = Double.parseDouble(getOrigTransAmount(line));}

			double tranamt		= Double.parseDouble(modelLiq_part.getValueAt(selected_row,9).toString());
			double return_amt	= Double.parseDouble(modelLiq_part.getValueAt(selected_row,10).toString());	
			double refund_amt	= Double.parseDouble(modelLiq_part.getValueAt(selected_row,11).toString());		
			double wtax_amt		= Double.parseDouble(modelLiq_part.getValueAt(selected_row,24).toString());
			double vat_rate		= Double.parseDouble(modelLiq_part.getValueAt(selected_row,21).toString())/100;
			double tranamt2		= Double.parseDouble(modelLiq_part.getValueAt(selected_row,9).toString());
			
			System.out.printf("tranamt:%s\n", tranamt);
			System.out.printf("return_amt:%s\n", return_amt);
			System.out.printf("refund_amt:%s\n", refund_amt);
			System.out.printf("wtax_amt:%s\n", wtax_amt);
			System.out.printf("vat_rate:%s\n", vat_rate);
			System.out.printf("tranamt2:%s\n", tranamt2);


			//double reduced_amt	= tranamt - return_amt;
			//double added_amt	= tranamt + refund_amt;
			//double trans_amt_new= tranamt - return_amt + refund_amt;		

			double cv_amount	= tranamt2 - wtax_amt + return_amt-refund_amt;
			double vat_amount	= ( tranamt2 / (1+vat_rate) ) * vat_rate;
			//double exp_amount	= trans_amt_new - vat_amount + return_amt - refund_amt; 
			
			
			/**COMMENTED BY JED**/
			/*if (evt.getKeyChar() == KeyEvent.VK_ENTER && c == 10 && return_amt > tranamt2  )  //
			{										
				JOptionPane.showMessageDialog(getContentPane(), "Amount for return cannot be greater than \n " +
						"the transaction amount", "Not Applicable", 
						JOptionPane.WARNING_MESSAGE);
				modelLiq_part.setValueAt(0, r, c);
			}
			else if (evt.getKeyChar() == KeyEvent.VK_ENTER && return_amt >0 && refund_amt >0  )  //
			{										
				JOptionPane.showMessageDialog(getContentPane(), "Amounts for return and refund \n " +
						"cannot be both greater than zero", "Not Applicable", 
						JOptionPane.WARNING_MESSAGE);
				modelLiq_part.setValueAt(trans_amt, r, 9);				
				modelLiq_part.setValueAt(0, r, 10);
				modelLiq_part.setValueAt(0, r, 11);			
				modelLiq_part.setValueAt(tranamt-vat_amount, r, 26);
				modelLiq_part.setValueAt(cv_amount, r, 27);
			}
			else if (evt.getKeyChar() == KeyEvent.VK_ENTER && c == 10)  //
			{										
				modelLiq_part.setValueAt(tranamt2-return_amt, r, 9);				
				modelLiq_part.setValueAt(tranamt-vat_amount, r, 26);
				//modelLiq_part.setValueAt(cv_amount, r, 27);
			}
			else if (evt.getKeyChar() == KeyEvent.VK_ENTER && c == 11)  //
			{										
				modelLiq_part.setValueAt(tranamt2+refund_amt, r, 9);				
				modelLiq_part.setValueAt(tranamt-vat_amount, r, 26);
				//modelLiq_part.setValueAt(cv_amount, r, 27);
			}*/
			/**COMMENTED BY JED**/
			
			if (evt.getKeyChar() == KeyEvent.VK_ENTER && c == 10 && return_amt > tranamt2  )  //
			{										
				JOptionPane.showMessageDialog(getContentPane(), "Amount for return cannot be greater than \n " +
						"the transaction amount", "Not Applicable", 
						JOptionPane.WARNING_MESSAGE);
				modelLiq_part.setValueAt(0, selected_row, c);
			}
			else if (evt.getKeyChar() == KeyEvent.VK_ENTER && return_amt >0 && refund_amt >0  )  //
			{										
				JOptionPane.showMessageDialog(getContentPane(), "Amounts for return and refund \n " +
						"cannot be both greater than zero", "Not Applicable", 
						JOptionPane.WARNING_MESSAGE);
				modelLiq_part.setValueAt(trans_amt, selected_row, 9);				
				modelLiq_part.setValueAt(0, selected_row, 10);
				modelLiq_part.setValueAt(0, selected_row, 11);			
				modelLiq_part.setValueAt(tranamt-vat_amount, selected_row, 26);
				modelLiq_part.setValueAt(cv_amount, selected_row, 27);
			}
			else if (evt.getKeyChar() == KeyEvent.VK_ENTER && c == 10)  //
			{
				System.out.println("Dumaan dito no.1");
				modelLiq_part.setValueAt(tranamt2-return_amt, selected_row, 9);				
				modelLiq_part.setValueAt(tranamt-vat_amount, selected_row, 26);
				//modelLiq_part.setValueAt(cv_amount, r, 27);
			}
			else if (evt.getKeyChar() == KeyEvent.VK_ENTER && c == 11)  //
			{	
				System.out.println("Dumaan dito no.2");
				modelLiq_part.setValueAt(tranamt2+refund_amt, selected_row, 9);				
				modelLiq_part.setValueAt(tranamt-vat_amount, selected_row, 26);
				//modelLiq_part.setValueAt(cv_amount, r, 27);
			}
			/*else if (evt.getKeyChar() == KeyEvent.VK_ENTER && c == 9 )  //
			{					
				Integer rw = tblLiq_part.getSelectedRow();	
				Double amount	= 0.00;
				Double wtax_rate= 0.00;
				try { amount	= Double.parseDouble(modelLiq_part.getValueAt(rw,26).toString()); } catch (NullPointerException e) { amount	= 0.00; }
				try { wtax_rate	= Double.parseDouble(modelLiq_part.getValueAt(rw,23).toString())/100; } catch (NullPointerException e) { wtax_rate	= 0.00; }
				BigDecimal grossAmt_bd 	= new BigDecimal(amount);	
				BigDecimal watx_amt_bd 	= new BigDecimal(amount*wtax_rate);	
				
				modelLiq_part.setValueAt(grossAmt_bd, r, 9);
				modelLiq_part.setValueAt(grossAmt_bd, r, 28);				
				//modelLiq_part.setValueAt(0, r, 10);
				//modelLiq_part.setValueAt(0, r, 11);	
				modelLiq_part.setValueAt(watx_amt_bd, r, 24);
				modelLiq_part.setValueAt(grossAmt_bd, r, 26);
				modelLiq_part.setValueAt(cv_amount, r, 27);
			}			
			else {			
				if(return_amt>0) 
				{
					Integer rw = tblLiq_part.getSelectedRow();	
					Double wtax_rate= 0.00;
					try { wtax_rate	= Double.parseDouble(modelLiq_part.getValueAt(rw,23).toString())/100; } catch (NullPointerException e) { wtax_rate	= 0.00; }
					BigDecimal watx_amt_bd 	= new BigDecimal(trans_amt*wtax_rate);	
					
					modelLiq_part.setValueAt(0, r, 11);
					modelLiq_part.setValueAt(reduced_amt, r, 9);
					modelLiq_part.setValueAt(watx_amt_bd, r, 24);
					modelLiq_part.setValueAt(vat_amount, r, 25);	
					modelLiq_part.setValueAt(exp_amount, r, 26);	
					modelLiq_part.setValueAt(cv_amount, r, 27);	
				}
				else if(refund_amt>0) 
				{
					Integer rw = tblLiq_part.getSelectedRow();	
					Double wtax_rate= 0.00;
					try { wtax_rate	= Double.parseDouble(modelLiq_part.getValueAt(rw,23).toString())/100; } catch (NullPointerException e) { wtax_rate	= 0.00; }
					BigDecimal watx_amt_bd 	= new BigDecimal(exp_amount*wtax_rate);	
					
					modelLiq_part.setValueAt(0,r, 10);
					modelLiq_part.setValueAt(added_amt, r, 9);	
					modelLiq_part.setValueAt(watx_amt_bd, r, 24);
					modelLiq_part.setValueAt(vat_amount, r, 25);
					modelLiq_part.setValueAt(exp_amount, r, 26);	
					modelLiq_part.setValueAt(cv_amount, r, 27);
				}
			  }*/
		} 	
		//}
		totalLiqui(modelLiq_part, modelLiq_part_total);	
		computeBillAmounts();
	}
	
	private static BigDecimal getVatAmount(Double gr_amt, Double vat_rate){//compute vat amount

		BigDecimal vat_amt = BigDecimal.valueOf(0.00);

		String SQL =
				"SELECT * FROM compute_vat_amount('"+gr_amt+"', '"+vat_rate+"')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((BigDecimal) db.getResult()[0][0]== BigDecimal.valueOf(0.00)) {vat_amt = BigDecimal.valueOf(0.00);}
			else {vat_amt = (BigDecimal) db.getResult()[0][0]; }

		}else{
			vat_amt = BigDecimal.valueOf(0.00);
		}

		return vat_amt;

	}
	
	private static BigDecimal getExpAmount(Double gr_amt, Double vatAmt, Double return_amt, Double refund_amt){//compute expense amount

		BigDecimal exp_amt = BigDecimal.valueOf(0.00);

		String SQL =
				"SELECT * FROM compute_expense_amount_withretrefamt('"+gr_amt+"', '"+vatAmt+"', '"+return_amt+"', '"+refund_amt+"' )";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((BigDecimal) db.getResult()[0][0]== BigDecimal.valueOf(0.00)) {exp_amt = BigDecimal.valueOf(0.00);}
			else {exp_amt = (BigDecimal) db.getResult()[0][0]; }

		}else{
			exp_amt =  BigDecimal.valueOf(0.00);
		}

		return exp_amt;


	}
	
	private static BigDecimal getWtaxAmount(Double gr_amt, Double vat_rate, Double tax_rate){//compute wtax amount

		BigDecimal wtax_amt = BigDecimal.valueOf(0.00);

		String SQL =
				"SELECT * FROM compute_wtax_amount('"+gr_amt+"', '"+vat_rate+"', '"+tax_rate+"')";
		
		FncSystem.out("SQL", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((BigDecimal) db.getResult()[0][0]== BigDecimal.valueOf(0.00)) {wtax_amt = BigDecimal.valueOf(0.00);}
			else {wtax_amt = (BigDecimal) db.getResult()[0][0]; }

		}else{
			wtax_amt =  BigDecimal.valueOf(0.00);
		}

		return wtax_amt;


	}

	private void computeBillAmounts (){//used
		/*EDITEDB BY JED 2021-08-19 : CHANGE THE PROCESS OF GETTING WTAX, EXP AND VAT AMOUNT THRU POSTGRES TO CONTROL ROUNDING OFF*/
		
		System.out.println("Dumaan sa computeBillAmounts 2");  

		if (modelLiq_part.isEditable() && !jv_status.equals("POSTED")) {			

			int r  = tblLiq_part.getSelectedRow();

			double trans_amt	= 0.00;
			if(txtJV_no.getText().trim().equals("")) // meaning - new liquidation is being created
			{trans_amt	= Double.parseDouble(modelLiq_part.getValueAt(r,28).toString());}
			else // meaning - liquidation is for editing
			{Integer line = Integer.parseInt(modelLiq_part.getValueAt(r,0).toString());
			trans_amt = Double.parseDouble(getOrigTransAmount(line));}

			double tranamt		= Double.parseDouble(modelLiq_part.getValueAt(r,9).toString());
			double return_amt	= Double.parseDouble(modelLiq_part.getValueAt(r,10).toString());	
			double refund_amt	= Double.parseDouble(modelLiq_part.getValueAt(r,11).toString());		
			//double wtax_amt		= Double.parseDouble(modelLiq_part.getValueAt(r,24).toString());/**COMMENTED BY JED 2020-07-30 : fix unbalanced debit credit amt on jv**/
			double vat_rate		= Double.parseDouble(modelLiq_part.getValueAt(r,21).toString())/100;
			double wtax_rate	= Double.parseDouble(modelLiq_part.getValueAt(r,23).toString())/100;
			double tranamt2		= Double.parseDouble(modelLiq_part.getValueAt(r,9).toString());
			double trans_amt_new=  tranamt - return_amt + refund_amt;		

			//double cv_amount	= tranamt2 - wtax_amt+return_amt-refund_amt;/**COMMENTED BY JED 2020-07-30 : fix unbalanced debit credit amt on jv**/
			//double vat_amount	= ( tranamt2 / (1+vat_rate) ) * vat_rate;
			double vat_amount	= getVatAmount(tranamt, vat_rate).doubleValue();
			//double exp_amount	= trans_amt_new - vat_amount+return_amt-refund_amt; 
			double exp_amount	= getExpAmount(trans_amt_new, vat_amount, return_amt, refund_amt).doubleValue();
			//double wtax_amt	= Double.parseDouble(modelLiq_part.getValueAt(r,24).toString());
			//double wtax_amt		= getWtaxAmount(tranamt, vat_rate, wtax_rate).doubleValue();
			
			BigDecimal wtax_amt = getWtaxAmount(tranamt, vat_rate, wtax_rate);
			
			System.out.printf("tranamt:%s\n", tranamt);
			System.out.printf("vat_amount:%s\n", vat_amount);
			System.out.printf("exp_amount:%s\n", exp_amount);
			System.out.printf("return_amt:%s\n", return_amt);
			System.out.printf("refund_amt%s\n", refund_amt);
			System.out.printf("vat_rate:%s\n", vat_rate);
			System.out.printf("wtax_rate:%s\n", wtax_rate);
			System.out.printf("tranamt2:%s\n", tranamt2);
			System.out.printf("trans_amt_new:%s\n", trans_amt_new);
			System.out.printf("wtax_amt:%s\n", wtax_amt);
			
			modelLiq_part.setValueAt(wtax_amt, r, 24);	
			modelLiq_part.setValueAt(vat_amount, r, 25);	
			modelLiq_part.setValueAt(exp_amount, r, 26);	
			//modelLiq_part.setValueAt(cv_amount, r, 27);/**COMMENTED BY JED 2020-07-30 : fix unbalanced debit credit amt on jv**/	
	
			//double cv_amount	= tranamt2 - wtax_amt + return_amt - refund_amt;
			
			BigDecimal cv_amt = new BigDecimal(tranamt2).subtract(wtax_amt).add(new BigDecimal(return_amt)).subtract(new BigDecimal(refund_amt));
			modelLiq_part.setValueAt(cv_amt, r, 27);	
			
			System.out.printf("cv_amount:%s\n", cv_amt);
		}	
		totalLiqui(modelLiq_part, modelLiq_part_total);		
	}
 
	private void adjustRowHeight(){//used

		int rw = tblLiq_part.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblLiq_part.setRowHeight(x, 20);			
			x++;
		}
	}

	private void AddRow(){//used	
		
		String payee_id = "";
		String div_id = "";
		String dept_id = "";
		String proj_id = "";
		String sub_proj_id = "";
		String payee_type_id = "";
		Boolean vatable_proj = false;
		Boolean vatable_entity = false;		
		Double vat_rate = null;
		String wtax_id = "";
		Double wtax_rate = null;	
		
		try { payee_id = modelLiq_part.getValueAt(0,12).toString().trim(); } catch (NullPointerException e) { payee_id = ""; }
		try { div_id = modelLiq_part.getValueAt(0,3).toString().trim();} catch (NullPointerException e) { div_id = ""; }		
		try { dept_id = modelLiq_part.getValueAt(0,4).toString().trim();} catch (NullPointerException e) { dept_id = ""; }
		try { proj_id = modelLiq_part.getValueAt(0,5).toString().trim();} catch (NullPointerException e) { proj_id = ""; }
		try { sub_proj_id = modelLiq_part.getValueAt(0,6).toString().trim();} catch (NullPointerException e) { sub_proj_id = ""; }
		try { payee_type_id = modelLiq_part.getValueAt(0,13).toString().trim();} catch (NullPointerException e) { payee_type_id = ""; }
		try { wtax_id = modelLiq_part.getValueAt(0,22).toString().trim();} catch (NullPointerException e) { wtax_id = ""; }
		try { vatable_proj = (Boolean)modelLiq_part.getValueAt(0,17);} catch (NullPointerException e) { vatable_proj = false; }
		try { vatable_entity = (Boolean)modelLiq_part.getValueAt(0,18);} catch (NullPointerException e) { vatable_entity = false; }
		try { vat_rate = Double.parseDouble(modelLiq_part.getValueAt(0,21).toString().trim());} catch (NullPointerException e) { vat_rate = 0.00; }
		try { wtax_rate = Double.parseDouble(modelLiq_part.getValueAt(0,23).toString().trim());} catch (NullPointerException e) { wtax_rate = 0.00; }

		modelLiq_part.addRow(new Object[] { modelLiq_part.getRowCount() + 1,"","",div_id,dept_id,proj_id,sub_proj_id,"","",
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),payee_id,payee_type_id,"","",null,
				vatable_proj,vatable_entity,null,null,vat_rate,wtax_id,wtax_rate, new BigDecimal(0.00), 
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});

		totalLiqui(modelLiq_part, modelLiq_part_total);			
		((DefaultListModel) rowHeaderLiq_part.getModel()).addElement(modelLiq_part.getRowCount());
		adjustRowHeight();
		
		tblLiq_part.setEditable(true);
		adjustRowModel();		
	}
	
	private void removeRow(){//used

		/*int r  = tblLiq_part.getSelectedRow();		

		if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblLiq_part.getModel()).removeRow(r);  
			totalLiqui(modelLiq_part, modelLiq_part_total);					
			adjustRowModel();		
		}*/
		int[] selectedRows = tblLiq_part.getSelectedRows();

		if (selectedRows.length == 0) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row.", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		for (int x = selectedRows.length - 1; x > - 1; x--) {
			int row = selectedRows[x];

			modelLiq_part.removeRow(row);
		}
		
		totalLiqui(modelLiq_part, modelLiq_part_total);					
		adjustRowModel();
	}
	
	private void AddExtraRows(){//used		
		
		String payee_id = "";
		String div_id = "";
		String dept_id = "";
		String proj_id = "";
		String sub_proj_id = "";
		String payee_type_id = "";
		Boolean vatable_proj = false;
		Boolean vatable_entity = false;		
		Double vat_rate = null;
		String wtax_id = "";
		Double wtax_rate = null;	
		
		try { payee_id = modelLiq_part.getValueAt(0,12).toString().trim(); } catch (NullPointerException e) { payee_id = ""; }
		try { div_id = modelLiq_part.getValueAt(0,3).toString().trim();} catch (NullPointerException e) { div_id = ""; }		
		try { dept_id = modelLiq_part.getValueAt(0,4).toString().trim();} catch (NullPointerException e) { dept_id = ""; }
		try { proj_id = modelLiq_part.getValueAt(0,6).toString().trim();} catch (NullPointerException e) { proj_id = ""; }
		try { sub_proj_id = modelLiq_part.getValueAt(0,7).toString().trim();} catch (NullPointerException e) { sub_proj_id = ""; }
		try { payee_type_id = modelLiq_part.getValueAt(0,13).toString().trim();} catch (NullPointerException e) { payee_type_id = ""; }
		try { wtax_id = modelLiq_part.getValueAt(0,22).toString().trim();} catch (NullPointerException e) { wtax_id = ""; }
		try { vatable_proj = (Boolean)modelLiq_part.getValueAt(0,17);} catch (NullPointerException e) { vatable_proj = false; }
		try { vatable_entity = (Boolean)modelLiq_part.getValueAt(0,18);} catch (NullPointerException e) { vatable_entity = false; }
		try { vat_rate = Double.parseDouble(modelLiq_part.getValueAt(0,21).toString().trim());} catch (NullPointerException e) { vat_rate = 0.00; }
		try { wtax_rate = Double.parseDouble(modelLiq_part.getValueAt(0,23).toString().trim());} catch (NullPointerException e) { wtax_rate = 0.00; }

		int x = 0;
		
		while (x<=9)
		{
			modelLiq_part.addRow(new Object[] { modelLiq_part.getRowCount() + 1 ,"","",div_id,dept_id,"",proj_id,sub_proj_id,"",
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),payee_id,payee_type_id,"","",null,
					vatable_proj,vatable_entity,false,false,vat_rate,wtax_id,wtax_rate, new BigDecimal(0.00), 
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});
			
			x++;
		}
		
		adjustRowModel();		
	}
	
	private void adjustRowModel(){
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderLiq_part.setModel(listModel);
		Integer r = tblLiq_part.getRowCount();
		Integer s = 1;
		while (s<=r)
		{
			listModel.addElement(s);		
			s++;
		}		
		adjustRowHeight();
	}
	
	/*private void insertStringToAllRows(String x, Integer column){
		
		Integer r = tblLiq_part.getRowCount();
		Integer s = 0;
		while (s<r)
		{
			modelLiq_part.setValueAt(x, s, column);
			s++;
		}
	}*/
	
	/*private void removeToAllRows(Integer column){
		
		Integer r = tblLiq_part.getRowCount();
		Integer s = 0;
		while (s<r)
		{
			modelLiq_part.setValueAt("", s, column);
			s++;
		}
	}
	
	private void insertBooleanToAllRows(Boolean x, Integer column){
		
		Integer r = tblLiq_part.getRowCount();
		Integer s = 0;
		while (s<r)
		{
			modelLiq_part.setValueAt(x, s, column);
			s++;
		}
	}*/
	
	
	//save and insert		
	private void insertLiquiHeader(String rec_no, /*pgUpdate db,*/ String liq_remarks){//used

		String doc_id		= "";
		Integer proc_id		= null;
		String status_id	= "";	
		String created_by	= "";	
		String edited_by	= "";	
		Date edited_date	= null;	

		doc_id		= "22";
		proc_id		= 1;				
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	= null;		

		String sqlDetail = 
			"INSERT into rf_liq_header values (" +
			"'"+co_id+"',  \n" +  		//1 co_id
			"'"+co_id+"',  \n" +		//2 busunit_id
			"'"+rec_no+"',  \n" +		//3 liq_no
			"'"+dateFormat.format(dteLiqui.getDate())+"',  \n" +	//4 liq_date
			"'"+rplf_no+"' , \n" +		//5 rplf_no		
			"'"+jv_no+"',  \n" +		//6 jv_no
			"'"+doc_id+"' , \n" +		//7 doc_id	
			""+proc_id+",  \n" +		//8 proc_id  (ask Emer where this comes from)
			"'"+liq_remarks.replace("'","''")+"' , \n" +	//9 remarks
			"'"+status_id+"' , \n" +	//10 status_id
			"'"+created_by+"',  \n" +	//11 created_by
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//12 date_created
			"'"+edited_by+"', \n" +		//13 edited_by
			""+edited_date+", \n" +		//14 date_edited
			"''," +						//15 deleted_by
			"'',  \n" +					//16 tagged_by			
			"null," +					//17 date_tagged
			"null " +					//18 deleted_date

			")   " ;
		
		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sqlDetail, false);	
		db.commit();	
	}
	
//	private void insertLiquiDetails(String rec_no, pgUpdate db){//used
//
//		int rw = tblLiq_part.getModel().getRowCount();	
//		int x = 0;
//		int liq_line_no = 1;		
//
//		while (x < rw) {	
//
//			String ref_doc_no  	= "";
//			String div_id  		= "";
//			String dept_id 		= "";
//			String tran_type    ="";
//			tran_type="00011";
//			
//			Integer rplf_line_no= Integer.parseInt(modelLiq_part.getValueAt(x,0).toString());	
//			String ref_doc_id	= modelLiq_part.getValueAt(x,14).toString();	
//			try { ref_doc_no	= modelLiq_part.getValueAt(x,15).toString(); } 	catch (NullPointerException e) { ref_doc_no = "" ; }				
//			String ref_doc_date	= "";
//			if (modelLiq_part.getValueAt(x,16)==null)  {} else {ref_doc_date= modelLiq_part.getValueAt(x,16).toString().trim();}
//			String part_desc	= modelLiq_part.getValueAt(x,1).toString().replace("'","''");	
//			String acct_id		= modelLiq_part.getValueAt(x,2).toString().trim();
//			String entity_id	= modelLiq_part.getValueAt(x,12).toString().trim();
//			String entity_type_id	= modelLiq_part.getValueAt(x,13).toString().trim();
//			try { div_id		= modelLiq_part.getValueAt(x,3).toString().trim(); } 	catch (NullPointerException e) { div_id = "" ; }
//			try { dept_id		= modelLiq_part.getValueAt(x,4).toString().trim(); } 	catch (NullPointerException e) { dept_id = "" ; }			
//			//String sect_id		= modelLiq_part.getValueAt(x,5).toString().trim();
//			String project_id	= modelLiq_part.getValueAt(x,6).toString().trim();
//			String sub_projectid= modelLiq_part.getValueAt(x,7).toString().trim();
//			Double tran_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,9).toString());	
//			Double return_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,10).toString());	
//			Double refund_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString());	
//			Boolean is_vatproject 	= (Boolean) modelLiq_part.getValueAt(x,17);	
//			Boolean is_vatentity 	= (Boolean) modelLiq_part.getValueAt(x,18);	
//			//Boolean is_taxpaidbyco 	= (Boolean) modelLiq_part.getValueAt(x,19);
//			//Boolean is_gross 		= (Boolean) modelLiq_part.getValueAt(x,20);
//			String wtax_id			= modelLiq_part.getValueAt(x,22).toString().trim();	
//			String vat_acct_id		= "";	
//
//			Double wtax_rate	= Double.parseDouble(modelLiq_part.getValueAt(x,23).toString());	
//			Double wtax_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,24).toString());
//			Double vat_rate		= Double.parseDouble(modelLiq_part.getValueAt(x,21).toString());
//			Double vat_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,25).toString());
//			Double exp_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,26).toString());
//			Double ca_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());	
//			
//			/*if (modelLiq_part.getValueAt(x,24) != null) {
//				is_taxpaidbyco = true;
//			} else {
//				is_taxpaidbyco = false;
//			}*/
//
//			if (tran_amt==0.00){}
//			else 
//			{
//				String sqlDetail = 
//					"INSERT into rf_liq_detail values (" +
//					"'"+co_id+"',  \n" +  		//1 co_id
//					"'"+co_id+"',  \n" +		//2 busunit_id
//					"'"+rec_no+"',  \n" +		//3 liq_no
//					""+liq_line_no+",  \n" +	//4 line_no
//					""+rplf_line_no+",  \n" +	//5 rplf_line_no
//					"'"+ref_doc_id+"',  \n" +	//6 ref_doc_id
//					"'"+ref_doc_no+"',  \n" ;	//7 ref_doc_no
//				if (modelLiq_part.getValueAt(x,16)==null) {sqlDetail = sqlDetail + "null,";} else {sqlDetail = sqlDetail + "'"+ref_doc_date+"',  \n"  ; } //8 ref_doc_date					
//				sqlDetail = sqlDetail +		
//				"'"+part_desc+"',  \n" +	//9 part_desc
//				"'"+acct_id+"',  \n" +		//10 acct_id
//				"'"+entity_id+"',  \n" +	//11 entity_id
//				"'"+entity_type_id+"',  \n" +	//12 entity_type_id
//				"'"+div_id+"',  \n" +		//13 div_id
//				"'"+dept_id+"',  \n" +		//14 dept_id
//				"'',  \n" +		//15 sect_id
//				"'"+project_id+"',  \n" +	//16 project_id
//				"'"+sub_projectid+"',  \n" +//17 sub_projectid
//				"'"+co_id+"',  \n" +		//18 inter_busunit_id
//				""+tran_amt+",  \n" + 		//19 tran_amt		
//				""+return_amt+",  \n" + 	//20 return_amt		
//				""+refund_amt+",  \n" + 	//21 refund_amt	
//				""+is_vatproject+",  \n" + 	//22 is_vatproject	
//				""+is_vatentity+",  \n" + 	//23 is_vatentity	
//				"false,  \n" + //24 is_taxpaidbyco	
//				"false,  \n" + 		//25 is_gross	
//				"'"+wtax_id+"',  \n" + 		//26 wtax_id	
//				""+wtax_rate+",  \n" + 		//27 wtax_rate	
//				""+wtax_amt+",  \n" + 		//28 wtax_amt	
//				"'"+vat_acct_id+"',  \n" + 	//29 vat_acct_id	
//				""+vat_rate+",  \n" + 		//30 vat_rate	
//				""+vat_amt+",  \n" + 		//31 vat_amt	
//				""+exp_amt+",  \n" + 		//32 exp_amt	
//				""+ca_amt+",  \n" + 		//33 ca_amt					
//				"'',  \n" + 				//34 old_acct_id	
//				"'A'  \n" + 				//35 status_id
//				")   " ;
//
//				System.out.printf("SQL #1: %s", sqlDetail);
//				db.executeUpdate(sqlDetail, false);	
//				
//				//added by Erick DCRF 997
//				String strsql = 
//						"INSERT INTO rf_subsidiary_ledger(\n" + 
//						"            co_id, jv_no, tran_type, entity_id, entity_type_id, trans_amt, \n" + 
//						"            vat_amt, wtax_amt, sundry_acct, proj_id, sub_proj, div_id, dep_id, \n" + 
//						"            status_id, created_by, date_created\n" +
//						"            )\n" + 
//						"    VALUES ('"+co_id+"', '"+jv_no+"', '"+tran_type+"', '"+entity_id+"', '"+entity_type_id+"', "+tran_amt+", \n" + 
//						"            "+vat_amt+", "+wtax_amt+", '"+acct_id+"', NULLIF('"+project_id+"','null'), NULLIF('"+sub_projectid+"','null'), NULLIF('"+div_id+"','null'), NULLIF('"+dept_id+"','null'), 'A',\n" +
//						"            '"+UserInfo.EmployeeCode+"', '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"'\n" + 
//						"            );\n" + 
//						"" ;
//				System.out.printf("SQL #1: %s", strsql);
//				db.executeUpdate(strsql, false);
//				liq_line_no++;
//			}
//			
//			x++;
//		}
//
//	}

	@SuppressWarnings("unlikely-arg-type")
	private void insertLiquiDetails(String rec_no/*, pgUpdate db*/){//used
	//**EDITED BY JED 2020-11-18 : FIX THE SAVING OF SL ON JV**//
		int rw = tblLiq_part.getModel().getRowCount();	
		int x = 0;
		int liq_line_no = 1;
		//pgUpdate db = new pgUpdate();
		pgSelect db = new pgSelect();

		while (x < rw) {	

			String ref_doc_no  	= "";
			String div_id  		= "";
			String dept_id 		= "";
			String tran_type    ="";
			tran_type="00011";
			
			Integer rplf_line_no= Integer.parseInt(modelLiq_part.getValueAt(x,0).toString());	
			String ref_doc_id	= modelLiq_part.getValueAt(x,14).toString();	
			try { ref_doc_no	= modelLiq_part.getValueAt(x,15).toString(); } 	catch (NullPointerException e) { ref_doc_no = "" ; }				
			
			//java.util.Date ref_doc_date = (Date) modelLiq_part.getValueAt(x,16);
			//try {ref_doc_date = (Date) modelLiq_part.getValueAt(x,16); } 	catch (NullPointerException e) { ref_doc_date = null ; }
			//if (ref_doc_date.equals("null")){ref_doc_date = null;} 
			//else {ref_doc_date = (Date) modelLiq_part.getValueAt(x,16);}
			
			String ref_doc_date = ""; 
			if (modelLiq_part.getValueAt(x,16)==null){} 
			else {ref_doc_date= modelLiq_part.getValueAt(x,16).toString().trim();}
			 
			
			String part_desc	= modelLiq_part.getValueAt(x,1).toString().replace("'","''");	
			String acct_id		= modelLiq_part.getValueAt(x,2).toString().trim();
			String entity_id	= modelLiq_part.getValueAt(x,12).toString().trim();
			String entity_type_id	= modelLiq_part.getValueAt(x,13).toString().trim();
			try { div_id		= modelLiq_part.getValueAt(x,3).toString().trim(); } 	catch (NullPointerException e) { div_id = "" ; }
			try { dept_id		= modelLiq_part.getValueAt(x,4).toString().trim(); } 	catch (NullPointerException e) { dept_id = "" ; }			
			//String sect_id		= modelLiq_part.getValueAt(x,5).toString().trim();
			String project_id	= modelLiq_part.getValueAt(x,6).toString().trim();
			String sub_projectid= modelLiq_part.getValueAt(x,7).toString().trim();
			Double tran_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,9).toString());	
			Double return_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,10).toString());	
			Double refund_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString());	
			Boolean is_vatproject 	= (Boolean) modelLiq_part.getValueAt(x,17);	
			Boolean is_vatentity 	= (Boolean) modelLiq_part.getValueAt(x,18);	
			//Boolean is_taxpaidbyco 	= (Boolean) modelLiq_part.getValueAt(x,19);
			//Boolean is_gross 		= (Boolean) modelLiq_part.getValueAt(x,20);
			String wtax_id			= modelLiq_part.getValueAt(x,22).toString().trim();	
			String vat_acct_id		= "";	

			Double wtax_rate	= Double.parseDouble(modelLiq_part.getValueAt(x,23).toString());	
			Double wtax_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,24).toString());
			Double vat_rate		= Double.parseDouble(modelLiq_part.getValueAt(x,21).toString());
			Double vat_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,25).toString());
			Double exp_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,26).toString());
			Double ca_amt		= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());	
			
			/*if (modelLiq_part.getValueAt(x,24) != null) {
				is_taxpaidbyco = true;
			} else {
				is_taxpaidbyco = false;
			}*/

			if (tran_amt==0.00){}
			else 
			{
//				String sqlDetail = 
//					"INSERT into rf_liq_detail values (" +
//					"'"+co_id+"',  \n" +  		//1 co_id
//					"'"+co_id+"',  \n" +		//2 busunit_id
//					"'"+rec_no+"',  \n" +		//3 liq_no
//					""+liq_line_no+",  \n" +	//4 line_no
//					""+rplf_line_no+",  \n" +	//5 rplf_line_no
//					"'"+ref_doc_id+"',  \n" +	//6 ref_doc_id
//					"'"+ref_doc_no+"',  \n" ;	//7 ref_doc_no
//				if (modelLiq_part.getValueAt(x,16)==null) {sqlDetail = sqlDetail + "null,";} else {sqlDetail = sqlDetail + "'"+ref_doc_date+"',  \n"  ; } //8 ref_doc_date					
//				sqlDetail = sqlDetail +		
//				"'"+part_desc+"',  \n" +	//9 part_desc
//				"'"+acct_id+"',  \n" +		//10 acct_id
//				"'"+entity_id+"',  \n" +	//11 entity_id
//				"'"+entity_type_id+"',  \n" +	//12 entity_type_id
//				"'"+div_id+"',  \n" +		//13 div_id
//				"'"+dept_id+"',  \n" +		//14 dept_id
//				"'',  \n" +		//15 sect_id
//				"'"+project_id+"',  \n" +	//16 project_id
//				"'"+sub_projectid+"',  \n" +//17 sub_projectid
//				"'"+co_id+"',  \n" +		//18 inter_busunit_id
//				""+tran_amt+",  \n" + 		//19 tran_amt		
//				""+return_amt+",  \n" + 	//20 return_amt		
//				""+refund_amt+",  \n" + 	//21 refund_amt	
//				""+is_vatproject+",  \n" + 	//22 is_vatproject	
//				""+is_vatentity+",  \n" + 	//23 is_vatentity	
//				"false,  \n" + //24 is_taxpaidbyco	
//				"false,  \n" + 		//25 is_gross	
//				"'"+wtax_id+"',  \n" + 		//26 wtax_id	
//				""+wtax_rate+",  \n" + 		//27 wtax_rate	
//				""+wtax_amt+",  \n" + 		//28 wtax_amt	
//				"'"+vat_acct_id+"',  \n" + 	//29 vat_acct_id	
//				""+vat_rate+",  \n" + 		//30 vat_rate	
//				""+vat_amt+",  \n" + 		//31 vat_amt	
//				""+exp_amt+",  \n" + 		//32 exp_amt	
//				""+ca_amt+",  \n" + 		//33 ca_amt					
//				"'',  \n" + 				//34 old_acct_id	
//				"'A'  \n" + 				//35 status_id
//				")   " ;
				
				String sqlDetail = 
						"SELECT sp_save_liq_detail (" +
						"'"+co_id+"',  \n" +  			//1 co_id
						"'"+co_id+"',  \n" +			//2 busunit_id
						"'"+rec_no+"',  \n" +			//3 liq_no
						""+liq_line_no+",  \n" +		//4 line_no
						""+rplf_line_no+",  \n" +		//5 rplf_line_no
						"'"+ref_doc_id+"',  \n" +		//6 ref_doc_id
						"'"+ref_doc_no+"',  \n" +		//7 ref_doc_no
						"NULLIF('"+ref_doc_date+"', '')::date, \n" +		//8 ref_doc_date						
						"'"+part_desc+"',  \n" +		//9 part_desc
						"'"+acct_id+"',  \n" +			//10 acct_id
						"'"+entity_id+"',  \n" +		//11 entity_id
						"'"+entity_type_id+"',  \n" +	//12 entity_type_id
						"'"+div_id+"',  \n" +			//13 div_id
						"'"+dept_id+"',  \n" +			//14 dept_id
						"'',  \n" +						//15 sect_id
						"'"+project_id+"',  \n" +		//16 project_id
						"'"+sub_projectid+"',  \n" +	//17 sub_projectid
						"'"+co_id+"',  \n" +			//18 inter_busunit_id
						""+tran_amt+",  \n" + 			//19 tran_amt		
						""+return_amt+",  \n" + 		//20 return_amt		
						""+refund_amt+",  \n" + 		//21 refund_amt	
						""+is_vatproject+",  \n" + 		//22 is_vatproject	
						""+is_vatentity+",  \n" + 		//23 is_vatentity	
						"false,  \n" + 					//24 is_taxpaidbyco	
						"false,  \n" + 					//25 is_gross	
						"'"+wtax_id+"',  \n" + 			//26 wtax_id	
						""+wtax_rate+",  \n" + 			//27 wtax_rate	
						""+wtax_amt+",  \n" + 			//28 wtax_amt	
						"'"+vat_acct_id+"',  \n" + 		//29 vat_acct_id	
						""+vat_rate+",  \n" + 			//30 vat_rate	
						""+vat_amt+",  \n" + 			//31 vat_amt	
						""+exp_amt+",  \n" + 			//32 exp_amt	
						""+ca_amt+",  \n" + 			//33 ca_amt					
						"'',  \n" + 					//34 old_acct_id	
						"'A'  \n" + 					//35 status_id
						")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				//db.executeUpdate(sqlDetail, false);	
				db.select(sqlDetail);
				insertSubsidiaryLedger(rplf_line_no, rec_no, tran_type, entity_id, entity_type_id, tran_amt, vat_amt, wtax_amt, acct_id, project_id, sub_projectid, div_id, dept_id);
				
//				Integer liq_row = getLiqRow(rplf_line_no, rec_no);
//				System.out.printf("liq_row:%s\n", liq_row);
				
				//added by Erick DCRF 997
//				String strsql = 
//						"INSERT INTO rf_subsidiary_ledger(\n" + 
//						"            co_id, jv_no, tran_type, entity_id, entity_type_id, trans_amt, \n" + 
//						"            vat_amt, wtax_amt, sundry_acct, proj_id, sub_proj, div_id, dep_id, \n" + 
//						"            status_id, created_by, date_created, liq_row\n" +
//						"            )\n" + 
//						"    VALUES ('"+co_id+"', '"+jv_no+"', '"+tran_type+"', '"+entity_id+"', '"+entity_type_id+"', "+tran_amt+", \n" + 
//						"            "+vat_amt+", "+wtax_amt+", '"+acct_id+"', NULLIF('"+project_id+"','null'), NULLIF('"+sub_projectid+"','null'), NULLIF('"+div_id+"','null'), NULLIF('"+dept_id+"','null'), 'A',\n" +
//						"            '"+UserInfo.EmployeeCode+"', '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"', '"+liq_row+"'\n" + 
//						"            );\n" + 
//						"" ;
//				System.out.printf("SQL #1: %s", strsql);
//				db.executeUpdate(strsql, false);
				liq_line_no++;
			}	
			x++;
		}	
		//db.commit();	
	}		
	
	//**ADDED BY JED 2020-11-18 : INSERTING SL ON JV**//
	private void insertSubsidiaryLedger(Integer rplf_line_no, String liq_no, String tran_type, String entity_id, String entity_type_id, Double tran_amt, Double vat_amt, Double wtax_amt, String acct_id, String project_id, String sub_projectid, String div_id, String dept_id) {
		Integer liq_row = getLiqRow(rplf_line_no, liq_no);
		System.out.printf("liq_row:%s\n", liq_row);
		
		String strsql = 
				"INSERT INTO rf_subsidiary_ledger(\n" + 
				"            co_id, busunit_id, jv_no, tran_type, entity_id, entity_type_id, trans_amt, \n" + 
				"            vat_amt, wtax_amt, sundry_acct, proj_id, sub_proj, div_id, dep_id, \n" + 
				"            status_id, created_by, date_created, liq_row\n" +
				"            )\n" + 
				"    VALUES ('"+co_id+"', '"+co_id+"', '"+jv_no+"', '"+tran_type+"', '"+entity_id+"', '"+entity_type_id+"', "+tran_amt+", \n" + 
				"            "+vat_amt+", "+wtax_amt+", '"+acct_id+"', NULLIF('"+project_id+"','null'), NULLIF('"+sub_projectid+"','null'), NULLIF('"+div_id+"','null'), NULLIF('"+dept_id+"','null'), 'A',\n" +
				"            '"+UserInfo.EmployeeCode+"', '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"', '"+liq_row+"'\n" + 
				"            );\n" + 
				"" ;
		System.out.printf("SQL #1: %s", strsql);
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(strsql, false);
		db.commit();
	}
	
	private Integer getLiqRow(Integer rplf_line_no, String liq_no) {
		
		Integer liq_row = 0;
		
		String sql = "select liq_row from rf_liq_detail where rplf_line_no = "+rplf_line_no+" and liq_no = '"+liq_no+"' and status_id = 'A'";
		
		System.out.printf("SQL FOR LIQ ROW: %s\n", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			System.out.print("DUMAAN DITO 11111111");
			liq_row = (Integer)db.getResult()[0][0];
		}else {
			System.out.print("DUMAAN DITO 222222222");
			liq_row = 0;
		}
		
		return liq_row;
	}

	private void insertJV_header(/*pgUpdate db,*/ String jv_rmrks){//used

		Integer fiscal_yr_4digit = 0;
		String period_id 	= "";
		String tran_id 		= "";
		String posted_by	= "";
		Date posted_date	= null;
		String doc_id		= "";
		Integer proc_id		= 0;
		Boolean is_for_rev	= false;
		Date rev_date		= null;
		String status_id	= "";
		String created_by	= "";
		String edited_by	= "";
		Date date_edited	= null;				

		fiscal_yr_4digit 	= (Integer) getMonthYear()[2];
		period_id 	= lookupPeriod.getText().trim();
		tran_id 	= "00011";  // Liquidation of Cash Advance
		posted_by	= "";
		posted_date	= null;
		doc_id		= "11";
		proc_id		= 0;
		is_for_rev	= false;
		rev_date	= null;
		status_id	= "A";
		created_by	= UserInfo.EmployeeCode;
		edited_by	= "";
		date_edited	= null;				

		String sqlDetail = 
			"INSERT into rf_jv_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +  		//2			
			"'"+jv_no+"',  \n" +  		//3
			"'"+dateFormat.format(dteLiqui.getDate())+"',  \n" +	//5 jv_date
			""+fiscal_yr_4digit+",  \n" +  	//5
			"'"+period_id+"',  \n" +  	//6
			"'"+tran_id+"',  \n" +  	//7
			"'"+posted_by+"',  \n" +  	//8
			""+posted_date+",  \n" +  	//9
			"'"+doc_id+"',  \n" +  		//10
			""+proc_id+",  \n" +  		//11
			""+is_for_rev+",  \n" +  	//12
			""+rev_date+",  \n" +  		//13
			"'"+jv_rmrks.replace("'","''")+"',  \n" +  //14
			"'"+status_id+"',  \n" +  	//15
			"'"+created_by+"',  \n" +  	//16
			"now(),  \n" +				//17 date_created			
			"'"+edited_by+"',  \n" + 	//18
			""+date_edited+"  \n" + 	//19
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sqlDetail, false);	
		db.commit();
	}
	
//	private void insertJV_detail(pgUpdate db, String rec_no){//used
//
//		int x  = 0;	
//		int y  = 1;
//		int rw = tblLiq_part.getModel().getRowCount();	
//
//		while(x < rw ) {	
//			
//			String div_id  		= "";
//			String dept_id  		= "";
//
//			String account_id 	= modelLiq_part.getValueAt(x,2).toString();
//			double trans_amt	= 0.00;
//			if(txtJV_no.getText().trim().equals("")) // meaning - new liquidation is being created
//			{trans_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());}
//			else // meaning - liquidation is for editing
//			{Integer line = Integer.parseInt(modelLiq_part.getValueAt(x,0).toString());
//			trans_amt = Double.parseDouble(getOrigTransAmount(line));}
//			Double exp_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,26).toString());
//			Double vat_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,25).toString());	
//			Double tax_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,24).toString());
//			Double return_amt 	= Double.parseDouble(modelLiq_part.getValueAt(x,10).toString());
//			Double refund_amt 	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString());			
//			Double ca_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());			
//
//			Double amount [] = {return_amt,exp_amt,vat_amt,tax_amt,ca_amt,refund_amt};			
//			String acct_id [] = {"01-02-99-003",account_id,"01-99-03-000","03-01-06-002","01-02-04-000","03-01-09-000" };			
//			String bal_side [] = {"D","D","D","C","C","C" };
//
//			try { div_id		= modelLiq_part.getValueAt(x,3).toString().trim(); } 	catch (NullPointerException e) { div_id = "" ; }
//			try { dept_id		= modelLiq_part.getValueAt(x,4).toString().trim(); } 	catch (NullPointerException e) { dept_id = "" ; }
//			//String sect_id		= modelLiq_part.getValueAt(x,5).toString().trim();
//			String project_id	= modelLiq_part.getValueAt(x,6).toString().trim();
//			String sub_projectid= modelLiq_part.getValueAt(x,7).toString().trim();
//
//			int num = 0;
//			while (num <= 5) {
//
//				if (amount[num] == 0.00) {		
//
//				}
//				else
//				{	
//					String sqlDetail = 
//						"INSERT into rf_jv_detail values (" +
//						"'"+co_id+"',  \n" +  		//1
//						"'"+co_id+"',  \n" +  		//2
//						"'"+jv_no+"',  \n" +  		//3			
//						"1,  \n" +  				//4
//						""+y+",  \n" +  			//5			
//						"'"+acct_id [num]+"',  \n" +//6
//						""+amount[num]+",  \n" +  	//7
//						"'"+bal_side[num]+"',  \n" +//8
//						"'"+rec_no+"',  \n" +		//9
//						"'"+project_id+"',  \n" +  	//10
//						"'"+sub_projectid+"',  \n" +//11		
//						"'"+div_id+"',  \n" +		//12	
//						"'"+dept_id+"',  \n" +		//13		
//						"'',  \n" +		//14		
//						"null,  \n" +  				//15
//						"null,  \n" +  				//16	
//						"null,  \n" +  				//17	
//						"null,  \n" +  				//18 entity_id
//						"null,  \n" +  				//19 pbl_id
//						"null,  \n" +  				//20 seq_no				
//						"'A', " +					//21
//						"'"+UserInfo.EmployeeCode+"',  \n" +  	//22 created_by
//						"now(),  \n" +				//23 date_created			
//						"'',  \n" + 				//24 edited_by
//						"null  \n" + 				//25 date_edited			
//						")   " ;
//
//					System.out.printf("SQL #1: %s", sqlDetail);
//					db.executeUpdate(sqlDetail, false);					
//					y++;
//				}
//
//				num++;
//			}
//
//			x++;
//		}
//	}
	
	private void insertJV_detail(/*pgUpdate db,*/ String rec_no){//used

		int x  = 0;	
		int y  = 1;
		int rw = tblLiq_part.getModel().getRowCount();	
		pgUpdate db = new pgUpdate();

		while(x < rw ) {	
			
			String div_id  		= "";
			String dept_id  		= "";

			String account_id 	= modelLiq_part.getValueAt(x,2).toString();
			double trans_amt	= 0.00;
			if(txtJV_no.getText().trim().equals("")) // meaning - new liquidation is being created
			{trans_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());}
			else // meaning - liquidation is for editing
			{Integer line = Integer.parseInt(modelLiq_part.getValueAt(x,0).toString());
			trans_amt = Double.parseDouble(getOrigTransAmount(line));}
			Double exp_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,26).toString());
			Double vat_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,25).toString());	
			Double tax_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,24).toString());
			Double return_amt 	= Double.parseDouble(modelLiq_part.getValueAt(x,10).toString());
			Double refund_amt 	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString());			
			Double ca_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());			

			Double amount [] = {return_amt,exp_amt,vat_amt,tax_amt,ca_amt,refund_amt};			
			String acct_id [] = {"01-02-99-003",account_id,"01-99-03-000","03-01-06-002","01-02-04-000","03-01-09-000" };			
			String bal_side [] = {"D","D","D","C","C","C" };

			try { div_id		= modelLiq_part.getValueAt(x,3).toString().trim(); } 	catch (NullPointerException e) { div_id = "" ; }
			try { dept_id		= modelLiq_part.getValueAt(x,4).toString().trim(); } 	catch (NullPointerException e) { dept_id = "" ; }
			//String sect_id		= modelLiq_part.getValueAt(x,5).toString().trim();
			String project_id	= modelLiq_part.getValueAt(x,6).toString().trim();
			String sub_projectid= modelLiq_part.getValueAt(x,7).toString().trim();

			int num = 0;
			while (num <= 5) {

				if (amount[num] == 0.00) {		

				}
				else
				{	
					String sqlDetail = 
						"INSERT into rf_jv_detail values (" +
						"'"+co_id+"',  \n" +  		//1
						"'"+co_id+"',  \n" +  		//2
						"'"+jv_no+"',  \n" +  		//3			
						"1,  \n" +  				//4
						""+y+",  \n" +  			//5			
						"'"+acct_id [num]+"',  \n" +//6
						""+amount[num]+",  \n" +  	//7
						"'"+bal_side[num]+"',  \n" +//8
						"'"+rec_no+"',  \n" +		//9
						"'"+project_id+"',  \n" +  	//10
						"'"+sub_projectid+"',  \n" +//11		
						"'"+div_id+"',  \n" +		//12	
						"'"+dept_id+"',  \n" +		//13		
						"'',  \n" +		//14		
						"null,  \n" +  				//15
						"null,  \n" +  				//16	
						"null,  \n" +  				//17	
						"null,  \n" +  				//18 entity_id
						"null,  \n" +  				//19 pbl_id
						"null,  \n" +  				//20 seq_no				
						"'A', " +					//21
						"'"+UserInfo.EmployeeCode+"',  \n" +  	//22 created_by
						"now(),  \n" +				//23 date_created			
						"'',  \n" + 				//24 edited_by
						"null  \n" + 				//25 date_edited			
						")   " ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);				
					
					y++;
				}
				num++;
			}
			x++;
		}
		db.commit();
	}

	private void insertJV_detail_tmp(/*pgUpdate db,*/ String rec_no){//insert to temp table first

		int x  = 0;	
		int y  = 1;
		int rw = tblLiq_part.getModel().getRowCount();	
		NumberFormat formatter = new DecimalFormat("#0.00");   

		while(x < rw ) {	
			
			String div_id  		= "";
			String dept_id  		= "";

			String account_id 	= modelLiq_part.getValueAt(x,2).toString();
			double trans_amt	= 0.00;
			if(txtJV_no.getText().trim().equals("")) // meaning - new liquidation is being created
			{trans_amt	= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());}
			else // meaning - liquidation is for editing
			{Integer line = Integer.parseInt(modelLiq_part.getValueAt(x,0).toString());
			trans_amt = Double.parseDouble(getOrigTransAmount(line));}
			Double exp_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,26).toString());
			Double vat_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,25).toString());
			//Double vat_amt 		= Double.parseDouble(formatter.format(modelLiq_part.getValueAt(x,25).toString()));	
			Double tax_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,24).toString());
			Double return_amt 	= Double.parseDouble(modelLiq_part.getValueAt(x,10).toString());
			Double refund_amt 	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString());			
			Double ca_amt 		= Double.parseDouble(modelLiq_part.getValueAt(x,27).toString());			

			Double amount [] = {return_amt,exp_amt,vat_amt,tax_amt,ca_amt,refund_amt};			
			String acct_id [] = {"01-02-99-003",account_id,"01-99-03-000","03-01-06-002","01-02-04-000","03-01-09-000" };			
			String bal_side [] = {"D","D","D","C","C","C" };

			try { div_id		= modelLiq_part.getValueAt(x,3).toString().trim(); } 	catch (NullPointerException e) { div_id = "" ; }
			try { dept_id		= modelLiq_part.getValueAt(x,4).toString().trim(); } 	catch (NullPointerException e) { dept_id = "" ; }
			//String sect_id		= modelLiq_part.getValueAt(x,5).toString().trim();
			String project_id	= modelLiq_part.getValueAt(x,6).toString().trim();
			String sub_projectid= modelLiq_part.getValueAt(x,7).toString().trim();

			int num = 0;
			while (num <= 5) {

				if (amount[num] == 0.00) {		

				}
				else
				{	
					String sqlDetail = 
						"INSERT into tmp_jv_detail values (" +
						"'"+co_id+"',  \n" +  		//1
						"'"+co_id+"',  \n" +  		//2
						"'"+jv_no+"',  \n" +  		//3			
						"1,  \n" +  				//4
						""+y+",  \n" +  			//5			
						"'"+acct_id [num]+"',  \n" +//6
						""+amount[num]+",  \n" +  	//7
						"'"+bal_side[num]+"',  \n" +//8
						"'"+rec_no+"',  \n" +		//9
						"'"+project_id+"',  \n" +  	//10
						"'"+sub_projectid+"',  \n" +//11		
						"'"+div_id+"',  \n" +		//12	
						"'"+dept_id+"',  \n" +		//13		
						"'',  \n" +		//14		
						"null,  \n" +  				//15
						"null,  \n" +  				//16	
						"null,  \n" +  				//17	
						"null,  \n" +  				//18 entity_id
						"null,  \n" +  				//19 pbl_id
						"null,  \n" +  				//20 seq_no				
						"'A', " +					//21
						"'"+UserInfo.EmployeeCode+"',  \n" +  	//22 created_by
						"now(),  \n" +				//23 date_created			
						"'',  \n" + 				//24 edited_by
						"null  \n" + 				//25 date_edited			
						")   " ;

					System.out.printf("SQL #1: %s", sqlDetail);
					pgUpdate db = new pgUpdate();
					db.executeUpdate(sqlDetail, false);	
					db.commit();
					y++;
				}

				num++;
			}

			x++;
		}
	}
	
	private void updateJVTempTable(String emp_code, String co_id, String jv_no) {
		
		String sql = "update tmp_jv_detail set status_id = 'I' where jv_no = '"+jv_no+"' and co_id = '"+co_id+"' and status_id = 'A' and created_by = '"+emp_code+"'";
		
		FncSystem.out("Update temp table", sql);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();
		
	}
//	
//	private void insertAudit_trail(String activity, String remarks, pgUpdate db){
//
//		String user_code	= UserInfo.EmployeeCode;	
//
//		String sqlDetail = 
//				"INSERT INTO mf_audit_trail(\n" + 
//				"	              system_id, activity, user_code, date_upd, entity_id, \n" + 
//				"	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n" + 
//				"	      VALUES ('CA', '"+activity+"', '"+user_code+"', NOW(), NULL, \n" + 
//				"	              NULL, NULL, NULL, NULL, NULL, '"+remarks+"');" ;
//
//		System.out.printf("SQL #1: %s", sqlDetail);
//		db.executeUpdate(sqlDetail, false);		
//
//
//	}
	
	private void insertAudit_trail(String activity, String remarks/*, pgUpdate db*/){

		String user_code	= UserInfo.EmployeeCode;	

		String sqlDetail = 
				"INSERT INTO mf_audit_trail(\n" + 
				"	              system_id, activity, user_code, date_upd, entity_id, \n" + 
				"	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n" + 
				"	      VALUES ('CA', '"+activity+"', '"+user_code+"', NOW(), NULL, \n" + 
				"	              NULL, NULL, NULL, NULL, NULL, '"+remarks+"');" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sqlDetail, false);
		db.commit();


	}

//	private void updateLiq_header(String rec_no, pgUpdate db){//used
//
//		String remarks		= "";	
//		String edited_by	= "";	
//
//		remarks		= txtDV_particular.getText().trim().replace("'", "''");	
//		edited_by	= UserInfo.EmployeeCode;			
//
//		String sqlDetail = 
//			"update rf_liq_header set " +
//			"co_id = '"+co_id+"',  \n" +  		//1
//			"busunit_id = '"+co_id+"',  \n" +	//2		
//			"liq_date = '"+dateFormat.format(dteLiqui.getDate())+"',  \n" +	//4
//			"remarks = '"+remarks+"' , \n" +		//9
//			"edited_by = '"+edited_by+"' , \n" +	//10
//			"date_edited = now() \n" +		//11				
//			"where liq_no = '"+rec_no+"' and co_id = '"+co_id+"'   " ;
//
//		System.out.printf("SQL #1: %s", sqlDetail);
//		db.executeUpdate(sqlDetail, false);		
//	}
	
	private void updateLiq_header(String rec_no, String remarks){//used

		//String remarks		= "";/*COMMENTED BY JED : FOR AUTO RPLF IF THE LIQ NO IS EDITED*/	
		String edited_by	= "";	

		//remarks		= txtDV_particular.getText().trim().replace("'", "''");	/*COMMENTED BY JED : FOR AUTO RPLF IF THE LIQ NO IS EDITED*/
		edited_by	= UserInfo.EmployeeCode;			

		String sqlDetail = 
			"update rf_liq_header set " +
			"co_id = '"+co_id+"',  \n" +  		//1
			"busunit_id = '"+co_id+"',  \n" +	//2		
			"liq_date = '"+dateFormat.format(dteLiqui.getDate())+"',  \n" +	//4
			"remarks = '"+remarks+"' , \n" +		//9
			"edited_by = '"+edited_by+"' , \n" +	//10
			"date_edited = now() \n" +		//11				
			"where liq_no = '"+rec_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sqlDetail, false);	
		db.commit();
	}
	
//	private void updateLiq_header(String rec_no){
//
//		String edited_by	= UserInfo.EmployeeCode;			
//
//		String sqlDetail = 
//			"update rf_liq_header set " +
//			"status_id = 'I', \n" +
//			"edited_by = '"+edited_by+"' , \n" +	//10
//			"date_edited = now() \n" +		//11				
//			"where liq_no = '"+rec_no+"' and co_id = '"+co_id+"'   " ;
//
//		System.out.printf("SQL #1: %s", sqlDetail);
//		pgUpdate db = new pgUpdate();
//		db.executeUpdate(sqlDetail, false);	
//		db.commit();
//	}
	
	private void updateJVHeader(String jv_no, String remarks) {
		
		String year = lookupGL_year.getText();
		String period = lookupPeriod.getText();
		
		String sqlDetail = "UPDATE rf_jv_header\n" + 
				"SET co_id='"+co_id+"', busunit_id='"+co_id+"', jv_date='"+dateFormat.format(dteLiqui.getDate())+"', fiscal_yr='"+year+"', period_id='"+period+"', remarks='"+ remarks+"'\n" +
				"WHERE jv_no = '"+jv_no+"' and co_id = '"+co_id+"' and status_id = 'A'";
		
		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sqlDetail, false);
		db.commit();
		
	}
	
//	private void updateJVHeader(String jv_no) {
//
//		String sqlDetail = "UPDATE rf_jv_header\n" + 
//				"SET status_id = 'I'\n" +
//				"WHERE jv_no = '"+jv_no+"' and co_id = '"+co_id+"' and status_id = 'A'";
//		
//		System.out.printf("SQL #1: %s", sqlDetail);
//		pgUpdate db = new pgUpdate();
//		db.executeUpdate(sqlDetail, false);
//		db.commit();
//		
//	}

//	private void updateLiq_detail(String rec_no, pgUpdate db) {//used
//
//		String sqlDetail = 
//			"update rf_liq_detail set status_id = 'I' where trim(liq_no) = '"+rec_no+"' and co_id = '"+co_id+"'   " ;
//
//		System.out.printf("SQL #1: %s", sqlDetail);
//		db.executeUpdate(sqlDetail, false);	
//	}
	
	private void updateLiq_detail(String rec_no) {//used

		String sqlDetail = 
			"update rf_liq_detail set status_id = 'I' where trim(liq_no) = '"+rec_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sqlDetail, false);
		db.commit();
	}

//	private void updateJV_detail(String rec_no, pgUpdate db) {//used
//
//		String sqlDetail = 
//			"update rf_jv_detail set status_id = 'I' " +
//			"where trim(jv_no) = '"+rec_no+"' and trim(co_id) = '"+co_id+"' " +
//			"and status_id = 'A' " ;
//
//		db.executeUpdate(sqlDetail, false);	
//		//Added by Erick 2019-05-23 DCRF 997 
//		String strsql = 
//				"update rf_subsidiary_ledger set status_id = 'I' " +
//						"where trim(jv_no) = '"+jv_no+"' " +
//						"and status_id = 'A' " ;
//
//			db.executeUpdate(strsql, false);
//
//	}
	
	private void updateJV_detail(String rec_no) {//used
		
		pgUpdate db = new pgUpdate();

		String sqlDetail = 
			"update rf_jv_detail set status_id = 'I' " +
			"where trim(jv_no) = '"+rec_no+"' and trim(co_id) = '"+co_id+"' " +
			"and status_id = 'A' " ;

		db.executeUpdate(sqlDetail, false);	
		//Added by Erick 2019-05-23 DCRF 997 
		String strsql = 
				"update rf_subsidiary_ledger set status_id = 'I' " +
						"where trim(jv_no) = '"+jv_no+"' and TRIM(co_id) = '"+co_id+"' " +
						"and status_id = 'A' " ;

			db.executeUpdate(strsql, false);
			
		db.commit();

	}

	private void updateReferences(String rec_no, pgUpdate db){

		int rw = tblLiq_part.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {	

			Integer line_no		= Integer.parseInt(modelLiq_part.getValueAt(x,0).toString());			
			String ref_doc_date	= "";

			if (modelLiq_part.getValueAt(x,16)==null)  {} else {ref_doc_date= modelLiq_part.getValueAt(x,16).toString().trim();}

			String sqlDetail = 
				"update rf_liq_detail set status_id = 'A', " ;

			if (modelLiq_part.getValueAt(x,12)==null||modelLiq_part.getValueAt(x,12).equals(""))
			{sqlDetail = sqlDetail + "entity_id = '',  "; } //entity_id
			else {sqlDetail = sqlDetail +  "entity_id = '"+modelLiq_part.getValueAt(x,12).toString()+"',  \n" ; }	

			if (modelLiq_part.getValueAt(x,13)==null||modelLiq_part.getValueAt(x,13).equals(""))
			{sqlDetail = sqlDetail + "entity_type_id = '',  "; } //entity_type_id
			else {sqlDetail = sqlDetail +  "entity_type_id = '"+modelLiq_part.getValueAt(x,13).toString()+"',  \n" ; }	

			if (modelLiq_part.getValueAt(x,14)==null||modelLiq_part.getValueAt(x,14).equals(""))
			{sqlDetail = sqlDetail + "ref_doc_id = '',  "; } //ref_doc_id
			else {sqlDetail = sqlDetail +  "ref_doc_id = '"+modelLiq_part.getValueAt(x,14).toString()+"',  \n" ; }	

			if (modelLiq_part.getValueAt(x,15)==null||modelLiq_part.getValueAt(x,15).equals(""))
			{sqlDetail = sqlDetail + "ref_doc_no = '',  "; } //ref_doc_no
			else {sqlDetail = sqlDetail +  "ref_doc_no = '"+modelLiq_part.getValueAt(x,15).toString()+"',  \n" ;	}	//ref_doc_no

			if (modelLiq_part.getValueAt(x,16)==null){sqlDetail = sqlDetail + "ref_doc_date = null ";} //ref_doc_date	
			else {sqlDetail = sqlDetail + "ref_doc_date = '"+ref_doc_date+"'  \n"  ; } 	

			sqlDetail = sqlDetail +				
			"where liq_no = '"+rec_no+"' \n" +
			"and rplf_line_no = "+line_no+" \n" +
			"and status_id = 'A'  " ;

			System.out.printf("SQL #1: %s", sqlDetail);

			db.executeUpdate(sqlDetail, false);	

			x++;
		}
	}

	private void tagLiquiheader(pgUpdate db){//used

		String tagged_by	= UserInfo.EmployeeCode;			

		String sqlDetail = 
			"update rf_liq_header set " +
			"status_id = 'G',  \n" +  		//1		
			"tagged_by = '"+tagged_by+"' , \n" +	//10
			"date_tagged = now() \n" +		//11				
			"where liq_no = '"+lookupLiqNo.getText().trim()+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void deleteLiqui_JVdetails(pgUpdate db, String liq_no, String jv_no){//used

		String tagged_by	= UserInfo.EmployeeCode;			

		String sqlDetail = 
			"update rf_liq_header set " +
			"status_id = 'D',  \n" +  		//1		
			"deleted_by = '"+tagged_by+"' , \n" +	//10
			"deleted_date = now() \n" +		//11				
			"where liq_no = '"+liq_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

		String sqlDetail2 = 
			"update rf_jv_header set " +
			"status_id = 'D'  \n" +  		//1		
			//"deleted_by = '"+tagged_by+"' , \n" +	//10
			//"date_deleted = '"+Calendar.getInstance().getTime()+"' \n" +		//11				
			"where jv_no = '"+jv_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);	
	}
	
	private void updateRPLF_header(String rplf_no, Double refund_amt) {
	
		String entity_id1	= "";
		String ent_type_id 	= "";	
		String remarks		= "";	
		String edited_by	= "";
		String pay_type		= "";			

		entity_id1	= getRequestAvailerID();
		ent_type_id = "02"; //02-Employee
		if (refund_amt>2000) {pay_type	= "B";} else {pay_type	= "A";}				
		remarks		= //"Auto-reimbursement from liquidation. \n" + "Liquidation No. : " + next_liq_no + "\n" +
						"JV No. : " + jv_no + "\n" + getRequestParticular().replace("'", "''");		
		edited_by	= UserInfo.EmployeeCode;	
		
		String sql = "UPDATE public.rf_request_header\n" + 
				"	SET co_id = '"+co_id+"', \n" + 
				"	busunit_id = '"+co_id+"', \n" + 
				"	rplf_date = now(), \n" + 
				"	date_needed = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"', \n" + 
				"	entity_id1 = '"+entity_id1+"', \n" + 
				"	entity_id2 = '"+entity_id1+"', \n" + 
				"	entity_type_id = '"+ent_type_id+"', \n" + 
				"	remarks = '"+remarks+"', \n" + 
				"	edited_by = '"+edited_by+"', \n" + 
				"	date_edited = now(), \n" +
				"	pay_type_id = '"+pay_type+"' \n" +	
				"WHERE rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"'";
		
		FncSystem.out("Update rf_request_header", sql);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();

	}
	
	private void updateRPLF_detail(String rplf_no, Double refund_amt) {
		
		int rw = tblLiq_part.getModel().getRowCount();
		int x = 0;
		
		while (x < rw) {
			
			String entity_id = "";
			String entity_type_id = "";
			String proj_id		= "";
			String subproj_id	= "";
			String div_id		= "";
			String dept_id		= "";
			String wtax_id		= "";
			String edited_by = "";
			
			Double exp_amount	= 0.00;	
			Double pv_amount	= 0.00;	
			
			try { entity_id	= getRequestAvailerID(); } catch (NullPointerException e) { entity_id	= ""; }
			try { entity_type_id = "02"; } catch (NullPointerException e) { entity_type_id	= ""; }
			try { proj_id	= modelLiq_part.getValueAt(x,6).toString().trim();} catch (NullPointerException e) { proj_id	= ""; }
			try { subproj_id= modelLiq_part.getValueAt(x,7).toString().trim();} catch (NullPointerException e) { subproj_id	= ""; }	
			try { div_id	= modelLiq_part.getValueAt(x,3).toString().trim();} catch (NullPointerException e) { div_id	= ""; }	
			try { dept_id	= modelLiq_part.getValueAt(x,4).toString().trim();} catch (NullPointerException e) { dept_id	= ""; }		
			//try { sec_id	= modelLiq_part.getValueAt(x,5).toString().trim();} catch (NullPointerException e) { sec_id	= ""; }	
			try { wtax_id	= modelLiq_part.getValueAt(x,22).toString().trim();	} catch (NullPointerException e) { wtax_id	= ""; }	
			try { exp_amount	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString()); } catch (NullPointerException e) { exp_amount	= 0.00; }	
			try { pv_amount		= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString()); } catch (NullPointerException e) { pv_amount	= 0.00; }	
			
			edited_by	= UserInfo.EmployeeCode;
			
			String sql = "UPDATE public.rf_request_detail\n" + 
					"	SET co_id = '"+co_id+"', \n" + 
					"	busunit_id = '"+co_id+"', \n" +  
					"	amount = '"+refund_amt+"', \n" + 
					"	entity_id = '"+entity_id+"', \n" + 
					"	entity_type_id = '"+entity_type_id+"', \n" + 
					"	project_id = '"+proj_id+"', \n" + 
					"	sub_projectid = '"+subproj_id+"', \n" + 
					"	div_id = '"+div_id+"', \n" + 
					"	dept_id = '"+dept_id+"', \n" + 
					"	wtax_id = '"+wtax_id+"', \n" + 
					"	exp_amt = '"+exp_amount+"', \n" + 
					"	pv_amt = '"+pv_amount+"', \n" + 
					"	edited_by = '"+edited_by+"', \n" + 
					"	date_edited = now() \n" +
					"WHERE rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"'";
			
			FncSystem.out("Update rf_request_detail", sql);
			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			x++;
		}

	}

	private void insertRPLF_header(/*pgUpdate db,*/ String rplf_no, Double refund_amt){

		Date date_liq_ext	= null;
		String rplf_type_id = "";
		String entity_id1	= "";
		//String entity_id2	= "";
		String ent_type_id 	= "";
		String sd_no		= "";
		String doc_id		= "";
		Integer proc_id		= null;	
		String branch_id	= "";	
		String justif		= "";	
		String remarks		= "";	
		String status_id	= "";	
		String created_by	= "";	
		String edited_by	= "";
		String pay_type		= "";
		Date edited_date	= null;					

		date_liq_ext= null;
		rplf_type_id= "11"; //11-Cash Fund Reimbursement
		entity_id1	= getRequestAvailerID();
		//entity_id2	= "";
		ent_type_id = "02"; //02-Employee
		if (refund_amt>2000) {pay_type	= "B";} else {pay_type	= "A";}			
		sd_no		= null;
		doc_id		= "09"; //09-Request for Payment
		proc_id		= 1;	
		branch_id	= null;	
		justif		= "";
		remarks		= //"Auto-reimbursement from liquidation. \n" + "Liquidation No. : " + next_liq_no + "\n" +
						"JV No. : " + jv_no + "\n" + getRequestParticular().replace("'", "''");	
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	= null;		

		String sqlDetail = 
			"INSERT into rf_request_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +		//2
			"'"+rplf_no+"',  \n" +		//3
			"now(),  \n" +	//4
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +  //5
			""+date_liq_ext+",  \n" + 	//6
			"'"+rplf_type_id+"' , \n" +	//7
			"'"+entity_id1+"',  \n" +	//8
			"'"+entity_id1+"',  \n" +	//9
			"'"+ent_type_id+"',  \n" +	//10
			""+sd_no+",  \n" +			//11
			"'"+doc_id+"' , \n" +		//12
			""+proc_id+",  \n" +		//13
			""+branch_id+" , \n" +		//14
			"'"+justif+"',  \n" +			//15
			"'"+remarks+"' , \n" +		//16
			"'"+status_id+"' , \n" +	//17
			"'"+created_by+"',  \n" +	//18
			"now(),  \n" +				//19
			"'"+edited_by+"' , \n" +	//20
			""+edited_date+",  \n" +	//21	
			"'"+pay_type+"'  \n" +		//22
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sqlDetail, false);
		db.commit();


	}
	
	private void insertRPLF_detail(/*pgUpdate db*/ String rplf_no, String liq_no){		

		int rw = tblLiq_part.getModel().getRowCount();	
		int x = 0;
		int y = 1;

		while (x < rw) {		

			Double amount	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString());	

			if (amount==0)  {}
			else {							
				String ref_doc_id 	= "";
				String ref_doc_no 	= "";
				String ref_doc_date	= "";
				Boolean	with_budget	= false;
				Boolean	isprojVatable	= false;
				Boolean	entityVatable	= false;
				Boolean	istaxPaidbyco	= false;
				String part_desc	= "";
				String acct_id		= "";
				String remarks		= "";
				String entity_id	= "";
				String entity_type_id	= "";
				String proj_id		= "";
				String subproj_id	= "";
				String div_id		= "";
				String dept_id		= "";
				//String sec_id		= "";
				String inter_bus_id	= "";
				String inter_co_id	= "";
				String sd_no		= "";
				String asset_no		= "";
				String old_acct_id	= "";	
				String wtax_id		= "";						

				try { ref_doc_id 	= modelLiq_part.getValueAt(x,14).toString(); } catch (NullPointerException e) { ref_doc_id = ""; }
				try { ref_doc_no 	= modelLiq_part.getValueAt(x,15).toString(); } catch (NullPointerException e) { ref_doc_no 	= ""; }

				if (modelLiq_part.getValueAt(x,16)==null)  {} else {ref_doc_date= modelLiq_part.getValueAt(x,16).toString().trim();}

				with_budget	= false;	
				isprojVatable = false;	
				entityVatable = false;	
				istaxPaidbyco = false;

				try { part_desc	= "Auto-reimbursement from liquidation. \n" + "Liquidation No. : " + liq_no ; } catch (NullPointerException e) { part_desc	= ""; }
				try { acct_id	= "03-01-09-000";} catch (NullPointerException e) { acct_id	= ""; }
				try { remarks	= "Auto-reimbursement from liquidation. \n" + "Liquidation No. : " + liq_no ; } catch (NullPointerException e) { remarks	= ""; }
				try { entity_id	= getRequestAvailerID(); } catch (NullPointerException e) { entity_id	= ""; }
				try { entity_type_id = "02"; } catch (NullPointerException e) { entity_type_id	= ""; }
				try { proj_id	= modelLiq_part.getValueAt(x,6).toString().trim();} catch (NullPointerException e) { proj_id	= ""; }
				try { subproj_id= modelLiq_part.getValueAt(x,7).toString().trim();} catch (NullPointerException e) { subproj_id	= ""; }	
				try { div_id	= modelLiq_part.getValueAt(x,3).toString().trim();} catch (NullPointerException e) { div_id	= ""; }	
				try { dept_id	= modelLiq_part.getValueAt(x,4).toString().trim();} catch (NullPointerException e) { dept_id	= ""; }		
				//try { sec_id	= modelLiq_part.getValueAt(x,5).toString().trim();} catch (NullPointerException e) { sec_id	= ""; }	
				try { wtax_id	= modelLiq_part.getValueAt(x,22).toString().trim();	} catch (NullPointerException e) { wtax_id	= ""; }	

				inter_bus_id= "";
				inter_co_id	= "";	

				sd_no		= null;
				asset_no	= null;
				old_acct_id	= null;	

				Double wtax_rate	= 0.00;	
				Double wtax_amount	= 0.00;	
				Double vat_rate		= 0.00;	
				Double vat_amount	= 0.00;	
				Double exp_amount	= 0.00;	
				Double pv_amount	= 0.00;	
			
				try { exp_amount	= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString()); } catch (NullPointerException e) { exp_amount	= 0.00; }	
				try { pv_amount		= Double.parseDouble(modelLiq_part.getValueAt(x,11).toString()); } catch (NullPointerException e) { pv_amount	= 0.00; }		
				

				String sqlDetail = 
					"INSERT into rf_request_detail values (" +
					"'"+co_id+"',  \n" +  		//1
					"'"+co_id+"',  \n" +		//2
					"'"+rplf_no+"',  \n" +		//3
					""+y+",  \n" +				//4
					"'"+ref_doc_id+"',  \n" + 	//5					
					"'"+ref_doc_no+"',  \n" ; 	//6				
				if (modelLiq_part.getValueAt(x,16)==null) {sqlDetail = sqlDetail + "null,";} else {sqlDetail = sqlDetail + "'"+ref_doc_date+"',  \n"  ; } //7					
				sqlDetail = sqlDetail +					
				""+with_budget+" , \n" +	//8
				"'"+part_desc+"',  \n" +	//9
				"'"+acct_id+"',  \n" +		//10
				"'"+remarks+"',  \n" +		//11	
				""+amount+",  \n" +			//12
				"'"+entity_id+"',  \n" +	//13			
				"'"+entity_type_id+"' , \n" +	//14	
				"'"+proj_id+"',  \n" +		//15			
				"'"+subproj_id+"',  \n" +	//16
				"'"+div_id+"',  \n" +		//17
				"'"+dept_id+"' , \n" +		//18
				"'',  \n" +		//19	
				"'"+inter_bus_id+"' , \n" +	//20
				"'"+inter_co_id+"' , \n" +	//21
				""+isprojVatable+" , \n" +	//22
				""+entityVatable+" , \n" +	//23
				""+istaxPaidbyco+" , \n" +	//24
				"false, \n" +				//25
				"'"+wtax_id+"' , \n" +		//26
				""+wtax_rate+", \n" +		//27
				""+wtax_amount+", \n" +		//28
				"null, \n" +				//29
				""+vat_rate+", \n" +		//30
				""+vat_amount+", \n" +		//31
				""+exp_amount+", \n" +		//32		
				""+pv_amount+", \n" +		//33
				""+sd_no+", \n" +			//34	
				"'', \n" +					//35
				""+asset_no+", \n" +		//36
				""+old_acct_id+"," +		//37	
				"'A',   \n"  +				//38
				"'"+UserInfo.EmployeeCode+"',  \n" +	//39
				"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//40
				"'' , \n" +					//41
				"null  \n" +				//42	

				")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				pgUpdate db = new pgUpdate();
				db.executeUpdate(sqlDetail, false);	
				db.commit();

				y++;
			}

			x++;
		}

	}
	
	private String getParticular_n_ReceiptNos(){
		
		/*EDITED BY JED 2021-03-22 : AVOID DOUBLE REMARKS IN JV*/
		
		/*String particular = "";
		String rec_type = "";
		String rec_no = "";	
		String remarks = "";	
		Integer r = tblLiq_part.getRowCount();
		Integer s = 0;
		
		while (s<r)
		{
			try { particular = modelLiq_part.getValueAt(s,1).toString().trim(); } catch (NullPointerException e) { particular = ""; }
			try { rec_type = modelLiq_part.getValueAt(s,14).toString().trim(); } catch (NullPointerException e) { rec_type = ""; }
			try { rec_no = modelLiq_part.getValueAt(s,15).toString().trim(); } catch (NullPointerException e) { rec_no = ""; }
			
			if (rec_type.equals("")){}
			else 
			{
				remarks = remarks + " " + particular + "-" + getReceiptTypeAlias(rec_type) + ": " + rec_no + "; " + "\n "  ;
			}
			
			s++;
		}
		
		remarks = remarks + "\n " + getPV_remarks();*/
		
		String remarks = getPV_remarks();
		
		return remarks;
	}
	
	
	
	//others
	private Object [] getMonthYear() {//used		
		
		//String liq_no = lookupLiqNo.getValue();

		DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");					
		String month  	 = df.format(dteLiqui.getDate());		
		//String month  	 = df.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		String month_sub = month.substring(8);
		//String month_sub = getMonth(liq_no, co_id);

		String month_word [] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"  };

		DateFormat df2   = new SimpleDateFormat("MM-dd-yyyy");	
		String year 	 = df2.format(dteLiqui.getDate());
		//String year 	 = df2.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		Integer year_sub = Integer.parseInt(year.substring(6))-2000;
		Integer year_full = Integer.parseInt(year.substring(6));

		Object x [] 	 = {month_sub, year_sub, year_full, month_word[Integer.parseInt(month_sub)-1], year.substring(6)};

		return x;

	}

	private void setJV_no(){//used

		Integer fiscal_yr 		= 0;
		Integer fiscal_yr_full 	= 0;
		String period_id 		= "";		

		fiscal_yr 		= (Integer) getMonthYear()[1];
		fiscal_yr_full 	= (Integer) getMonthYear()[2];
		period_id 		= lookupPeriod.getText().trim();

		/*next_seqno  = sql_getNextJV_seqno();
		Integer last_seq_no = sql_getLastJV_seqno();*/
		Integer next_srlno	= sql_getNextJV_srlno(fiscal_yr_full, period_id );  
		
		/*COMMENTED BY JED VICEDO 2021-05-24 : CHANGE GENERATION OF JV NUMBER INSIDE FUNCTION*/
		//jv_no = getJVno(fiscal_yr,period_id,next_srlno);
		jv_no = getJVno_v2(fiscal_yr_full, co_id, Integer.parseInt(period_id));

	}

	private Integer sql_getNextJV_srlno(Integer jv_fiscal_yr, String period_id ) {//used

		Integer next_no = 0;
		String SQL = 
			"select coalesce(max(jv_no),0) + 1 from \r\n" + 
			"(" +
			"select substring(jv_no, 5)::int as jv_no \r\n" + 
			"  from rf_jv_header \r\n" + 
			"  where fiscal_yr = "+jv_fiscal_yr+" \r\n" + 
			"  and co_id = '"+co_id+"'	\r\n" + 
			"  and (select right(left(jv_no, 4), 2)) = '"+period_id+"' \r\n" + 
			"  order by substring(jv_no, 5)::int desc  ) a";	
		
		//select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			next_no = (Integer) Integer.parseInt(db.getResult()[0][0].toString());
		}else{
			next_no = 1;
		}

		return next_no;
	}

	private String getJVno(Integer fiscal_yr, String period_id, Integer next_srlno){//used
		String SQL = 
				"select ('"+fiscal_yr+"'||'"+period_id+"'||trim(to_char("+next_srlno+",'0000'))) as next_jvno " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			jv_no = (String) db.getResult()[0][0];
		}else{
			jv_no = null;
		}

		return jv_no;

		/*
		 * private String sql_getNextLIQno() {//used

		String SQL = 
			"select trim(to_char(max(coalesce(liq_no::int,0))+1,'000000000')) from rf_liq_header where co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {next_liq_no = "000000001";}
			else {next_liq_no = (String) db.getResult()[0][0]; }
		}else{
			next_liq_no = "000000001";
		}

		return next_liq_no;
	}
		 * 
		 */


	}
	
	private String getJVno_v2(Integer fiscal_yr, String co_id, Integer period_id){//used
		String SQL = 
				"select  fn_get_jv_no("+fiscal_yr+", '"+co_id+"', "+period_id+") as next_jvno" ;
		
		FncSystem.out("Get JV No", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			jv_no = (String) db.getResult()[0][0];
		}else{
			jv_no = null;
		}

		return jv_no;

		/*
		 * private String sql_getNextLIQno() {//used

		String SQL = 
			"select trim(to_char(max(coalesce(liq_no::int,0))+1,'000000000')) from rf_liq_header where co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {next_liq_no = "000000001";}
			else {next_liq_no = (String) db.getResult()[0][0]; }
		}else{
			next_liq_no = "000000001";
		}

		return next_liq_no;
	}
		 * 
		 */


	}

		
	@SuppressWarnings("static-access")
	private void openDRF(){

		RequestForPayment drf = new RequestForPayment();
		Home_JSystem.addWindow(drf);

		if(co_id.equals(""))
		{

		} 
		else 	
		{

			RequestForPayment.co_id 		= co_id;	
			RequestForPayment.company		= company;	
			RequestForPayment.lookupCompany.setValue(co_id);
			RequestForPayment.tagCompany.setTag(company);
			RequestForPayment.company_logo = company_logo;

			RequestForPayment.lblDRF_no.setEnabled(true);	
			RequestForPayment.lookupDRF_no.setEnabled(true);	

			RequestForPayment.lookupDRF_no.setLookupSQL(RequestForPayment.getDRF_no());

			RequestForPayment.btnAddNew.setEnabled(true);
			RequestForPayment.btnCancel.setEnabled(true);

			if (lookupRequest.getText().equals("")) {}
			else {

				RequestForPayment.drf_no  = lookupRequest.getText();
				RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);	

				RequestForPayment.setDRF_header(RequestForPayment.drf_no);
				RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF, RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no );	
				RequestForPayment.btnAddNew.setEnabled(false);
				RequestForPayment.btnRefresh.setEnabled(true);
				if(RequestForPayment.isPVcreated()==true) {RequestForPayment.btnEdit.setEnabled(false);} 
				else {RequestForPayment.btnEdit.setEnabled(true);}
				RequestForPayment.mnidelete.setEnabled(false);
				RequestForPayment.mniaddrow.setEnabled(false);
			}
		}		
	}
	
	@SuppressWarnings("static-access")
	private void openPV(){

		PayableVoucher pv = new PayableVoucher();
		Home_JSystem.addWindow(pv);

		if(co_id.equals(""))
		{

		} 
		else 
		{
			PayableVoucher.co_id 		= co_id;	
			PayableVoucher.company		= company;	
			PayableVoucher.lookupCompany.setValue(co_id);
			PayableVoucher.tagCompany.setTag(company);
			PayableVoucher.company_logo = company_logo;
			PayableVoucher.btnAddNew.setEnabled(true);
			PayableVoucher.btnCancel.setEnabled(true);

			PayableVoucher.lblPV_no.setEnabled(true);	
			PayableVoucher.lookupPV_no.setEnabled(true);	
			//PayableVoucher.lookupPV_no.setEditable(true);	
			PayableVoucher.lookupPV_no.setLookupSQL(PayableVoucher.getPV_no());	
			PayableVoucher.lookupDRF_no.setLookupSQL(PayableVoucher.getDRF_no());

			if(!lookupRequest.getText().equals(""))
			{
				String pv_no = lookupRequest.getText();
				PayableVoucher.refresh_fields();							

				PayableVoucher.pv_no = pv_no;	
				PayableVoucher.lookupPV_no.setValue(pv_no);

				PayableVoucher.setPV_header(pv_no);
				PayableVoucher.displayPV_details(PayableVoucher.modelPV_account,PayableVoucher.rowHeaderPV_account, PayableVoucher.modelPV_accounttotal, pv_no );	
				PayableVoucher.displayPV_subsidiaryledgers(PayableVoucher.modelPV_SL, PayableVoucher.rowHeaderPV_SL, PayableVoucher.modelPV_SL_total, pv_no );	
				PayableVoucher.btnAddNew.setEnabled(false);
				PayableVoucher.btnRefresh.setEnabled(true);

				//set particulars							
				Integer w = 1;
				Integer line_count = PayableVoucher.sql_getDRF_line_count(pv_no);
				String part_desc;
				String particulars = "";
				//COMMENTED BY LESTER 2023-05-25 FOR REPEAT DISPLAY OF REMARKS
				
//				while (w<=line_count){	
//					part_desc = PayableVoucher.getDRF_particulars(w, pv_no).toString().trim();
//					particulars = ""+particulars+"  "+part_desc+"  \n" ;
//
//					w++; 
//				}
//				PayableVoucher.txtDRFRemark.setText(particulars);	

				if (PayableVoucher.getPV_status(pv_no).equals("P")&&PayableVoucher.lookupPV_no.isEnabled()) 
				{
					PayableVoucher.btnEdit.setEnabled(false); 
					PayableVoucher.btnSave.setText("Save");  
					PayableVoucher.btnSave.setEnabled(false); 	
					PayableVoucher.mniInactivate.setEnabled(false);
				} 
				else if (PayableVoucher.getPV_status(pv_no).equals("F")) 
				{
					PayableVoucher.btnEdit.setEnabled(false); 
					PayableVoucher.btnSave.setText("Post");  
					PayableVoucher.btnSave.setEnabled(true); 
					PayableVoucher.mniInactivate.setEnabled(false);
				}
				else if (PayableVoucher.getPV_status(pv_no).equals("A")) 
				{
					PayableVoucher.btnEdit.setEnabled(true); 
					PayableVoucher.btnSave.setText("Post");  
					PayableVoucher.btnSave.setEnabled(false); 
					PayableVoucher.mniInactivate.setEnabled(true);
				}

				PayableVoucher.modelPV_account.setEditable(false);
				PayableVoucher.tblPV_account.setEditable(false);
				PayableVoucher.btnPreview.setEnabled(true);				
			}
		}
	}

	@SuppressWarnings("static-access")
	private void openCV(){

		CheckVoucher chk_vchr = new CheckVoucher();
		Home_JSystem.addWindow(chk_vchr);

		if(co_id.equals("")) {}
		else 
		{
			CheckVoucher.co_id 		= co_id;
			CheckVoucher.tagCompany.setTag(company);
			CheckVoucher.company	= company;
			CheckVoucher.company_logo = company_logo;
			CheckVoucher.lookupCompany.setValue(co_id);

			CheckVoucher.lblDV_no.setEnabled(true);	
			CheckVoucher.lookupDV_no.setEnabled(true);	
			CheckVoucher.lookupDV_no.setLookupSQL(CheckVoucher.getDV_no(co_id));
			CheckVoucher.enableButtons(true, false, false, false, false, false, false, false, false, false );

			String pv_no = lookupRequest.getText();

			if (!RequestForPayment.sql_getCVno(pv_no, co_id).equals("")) 
			{
				String cv_no = RequestForPayment.sql_getCVno(pv_no, co_id);
				CheckVoucher.cv_no = cv_no;
				CheckVoucher.refresh_fields();
				CheckVoucher.refresh_tablesMain();
				CheckVoucher.setDV_header(cv_no);	
				CheckVoucher.lookupDV_no.setValue(cv_no);

				String status = RequestForPayment.sql_getCVstatus(cv_no, co_id);
				if(status.equals("A")) 
				{CheckVoucher.enableButtons(false, true, true, true, true, false, true, false, false, true );
				CheckVoucher.displayDV_account_entries(CheckVoucher.modelDV_acct_entries, CheckVoucher.rowHeaderDV_acct_entries, CheckVoucher.modelDV_accttotal, cv_no );	
				CheckVoucher.displayDV_pv(CheckVoucher.modelDV_pv, CheckVoucher.rowHeaderDV_pv, CheckVoucher.modelDV_pvtotal, cv_no );	
				} 
				else if (status.equals("F")) 
				{ CheckVoucher.enableButtons(false, false, true, true, true, false, false, true, true, true );
				CheckVoucher.displayDV_account_entries(CheckVoucher.modelDV_acct_entries, CheckVoucher.rowHeaderDV_acct_entries, CheckVoucher.modelDV_accttotal, cv_no );	
				CheckVoucher.displayDV_pv(CheckVoucher.modelDV_pv, CheckVoucher.rowHeaderDV_pv,CheckVoucher. modelDV_pvtotal, cv_no );	
				}
				else if (status.equals("P")) 
				{ CheckVoucher.enableButtons(false, false, true, true, true, false, false, false, false, false );
				CheckVoucher.displayDV_account_entries(CheckVoucher.modelDV_acct_entries, CheckVoucher.rowHeaderDV_acct_entries, CheckVoucher.modelDV_accttotal, cv_no );	
				CheckVoucher.displayDV_pv(CheckVoucher.modelDV_pv, CheckVoucher.rowHeaderDV_pv, CheckVoucher.modelDV_pvtotal, cv_no );	
				}
				else 
				{ CheckVoucher.enableButtons(false, false, false, true, true, false, false, false, false, false ); }

			}


		}

	}
	
	@SuppressWarnings("static-access")
	private static void openJV(){

		JournalVoucher jv = new JournalVoucher();
		Home_JSystem.addWindow(jv);

		if(co_id.equals(""))
		{

		} 
		else 	
		{

			JournalVoucher.co_id 		= co_id;	
			JournalVoucher.company		= company;	
			JournalVoucher.lookupCompany.setValue(co_id);
			JournalVoucher.tagCompany.setTag(company);
			JournalVoucher.company_logo = company_logo;

			JournalVoucher.lblJV_no.setEnabled(true);	
			JournalVoucher.lookupJV.setEnabled(true);	
			//JournalVoucher.lookupJV.setEditable(true);	

			JournalVoucher.lookupJV.setLookupSQL(JournalVoucher.getJV_no());	

			JournalVoucher.enableButtons(true,  false,  false,  false, false, 
					false, false,  false,  false, false );

			String jv_no = txtJV_no.getText().trim();

			if (!jv_no.equals("")) {


				JournalVoucher.refresh_fields();							

				JournalVoucher.jv_no = jv_no;	
				JournalVoucher.lookupJV.setValue(jv_no);

				JournalVoucher.setJV_header(jv_no);
				JournalVoucher.displayJV_details(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account, JournalVoucher.modelJV_accounttotal, jv_no );								
				JournalVoucher.displayJV_subsidiaryledgers(JournalVoucher.modelJV_SL, JournalVoucher.rowHeaderJV_SL, JournalVoucher.modelJV_SL_total, jv_no );
				
				JournalVoucher.mnidelete.setEnabled(false);
				JournalVoucher.mniaddrow.setEnabled(false);

			
			}
/*			else {

				JournalVoucher.refresh_fields();							

				JournalVoucher.jv_no = jv_no;	
				JournalVoucher.lookupJV.setValue(jv_no);

				JournalVoucher.setJV_header(jv_no);
				JournalVoucher.displayJV_details(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account, JournalVoucher.modelJV_accounttotal, jv_no );								

				JournalVoucher.mnidelete.setEnabled(false);
				JournalVoucher.mniaddrow.setEnabled(false);

			}*/			
		}		
	}

	@SuppressWarnings("static-access")
	private void openSOA(){

		LiquidationSOA liq_SOA = new LiquidationSOA();
		Home_JSystem.addWindow(liq_SOA);

		if(co_id.equals(""))
		{

		} 
		else 	
		{
			LiquidationSOA.co_id 		= co_id;
			LiquidationSOA.tagCompany.setTag(company);
			LiquidationSOA.company		= company;
			LiquidationSOA.company_logo = company_logo;
			LiquidationSOA.lookupCompany.setValue(co_id);

			LiquidationSOA.enableButtons(true, true, false, true, false);
			LiquidationSOA.enable_fields(true);

			LiquidationSOA.lookupDiv.setLookupSQL(LiquidationSOA.getDivision());
			LiquidationSOA.lookupDep.setLookupSQL(LiquidationSOA.getDepartment());
			LiquidationSOA.lookupAvailerType.setLookupSQL(LiquidationSOA.getAvailerType());
			LiquidationSOA.lookupAvailer.setLookupSQL(LiquidationSOA.getEntityList());

		}		
	}
	
	@SuppressWarnings("static-access")
	private void open2307(){

		EWT_Remittance cwt = new EWT_Remittance();
		Home_JSystem.addWindow(cwt);

		if(co_id.equals(""))
		{

		} 
		else 
		{
			EWT_Remittance.co_id = co_id;
			EWT_Remittance.lookupCompany.setValue(co_id);
			EWT_Remittance.tagCompany.setTag(company);
			EWT_Remittance.company_logo = company_logo;
			EWT_Remittance.lookupCompany.setValue(co_id);
			EWT_Remittance.tagCompany.setTag(company);
			EWT_Remittance.rbtnAllPayee.setEnabled(true);
			EWT_Remittance.btnGenerate.setEnabled(false);
			EWT_Remittance.displayEWT_forRemittance_all(EWT_Remittance.modelLiq_part, EWT_Remittance.rowHeaderLiq_part,
					EWT_Remittance.modelLiq_part_total, "", lookupRequest.getText(), "", "", "", "" );	
			EWT_Remittance.displayEWT_remitted(EWT_Remittance.modelRemitted, EWT_Remittance.rowHeaderRemitted, EWT_Remittance.modelRemitted_total, 
					"", lookupRequest.getText(), "", "", "", "" );
			EWT_Remittance.btnGenerate.setEnabled(true);
			EWT_Remittance.btnCancel.setEnabled(true);
			EWT_Remittance.lookupPayeeType.setLookupSQL(EWT_Remittance.getAvailerType());
			EWT_Remittance.lookupPayee.setLookupSQL(EWT_Remittance.getEntityList());
			EWT_Remittance.lblYear.setEnabled(true);
			EWT_Remittance.lblPeriod.setEnabled(true);
			EWT_Remittance.lblMonth.setEnabled(true);	
			EWT_Remittance.cmbYear.setEnabled(true);
			EWT_Remittance.cmbPeriod.setEnabled(true);
			EWT_Remittance.cmbMonth.setEnabled(true);
					
			/*String entity_id = modelLiq_part.getValueAt(0,12).toString().trim();
			
			EWT_2307.lookupPayee.setText(entity_id);
			EWT_2307.tagPayee.setTag(sql_getPayeeName(entity_id));*/
			EWT_Remittance.cmbYear.setEnabled(false);
			EWT_Remittance.cmbPeriod.setEnabled(false);
			EWT_Remittance.cmbMonth.setEnabled(false);
		}


	}

	
	//key listener
	@Override
	public void keyPressed(KeyEvent arg0) { }

	@Override
	public void keyReleased(KeyEvent arg0) {
		/*_JTableMain table = (_JTableMain) arg0.getSource();
		Integer row = table.getSelectedRow();
		Integer column = table.getSelectedColumn();*/

		if(arg0.getKeyCode() == KeyEvent.VK_F2){
			clickTableColumn();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) { }		

	public static String getEntity_type(String entity_id){

		return

				"select a.entity_type_id ,  " +
				"trim(b.entity_type_desc), " +
				"c.wtax_id, " +
				"c.wtax_rate \n" + 
				"from (select * from rf_entity_assigned_type where trim(entity_id) =  '"+entity_id+"' ) a \r\n" + 
				"left join mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n" + 		
				"left join rf_withholding_tax c on b.wtax_id = c.wtax_id   "  ;


	}
	public static String getEntityList(){//**EDITED BY JED 2022-01-26 DCRF NO. 1930 : INCLUDE TIN NO. IN LOOKUP FOR REPORTING PURPOSES**//

//		String sql = 
//				"select " +
//						"trim(entity_id) as \"Entity ID\",  \n" +
//						"trim(entity_name) as \"Name\",  \n" +
//						"entity_kind as \"Kind\",  \n" +
//						"vat_registered as \"VAT\"  \n" +
//						"from rf_entity where status_id = 'A' \n" +
//						//"(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where vat_registered = true )) a \n" +
//						"order by entity_name  ";		
//		return sql;
		
		String sql =
				"select\n" + 
				"trim(a.entity_id) as \"Entity ID\",\n" + 
				"trim(\n" + 
				"	case\n" + 
				"			when b.tin_no = '' or b.tin_no is null then ''\n" + 
				"			else\n" + 
				"					(\n" + 
				"						concat (		\n" + 
				"									substr(trim(replace(b.tin_no,'-','')), 1, 3), '-',\n" + 
				"									substr(trim(replace(b.tin_no,'-','')), 4, 3), '-',\n" + 
				"									substr(trim(replace(b.tin_no,'-','')), 7, 3), '-',\n" + 
				"									substr(trim(replace(b.tin_no,'-','')), 10, 3),\n" + 
				"									(case \n" + 
				"										when substr(trim(replace(b.tin_no,'-','')), 10, 3) = '' or substr(trim(replace(b.tin_no,'-','')), 10, 3) is null  then '000' else '' end)\n" + 
				"								)\n" + 
				"					) end\n" + 
				")as \"TIN\",\n" + 
				"trim(a.entity_name) as \"Name\",\n" + 
				"a.entity_kind as \"Kind\",\n" + 
				"a.vat_registered as \"VAT\"\n" + 
				"from rf_entity a\n" + 
				"left join rf_entity_id_no b on a.entity_id = b.entity_id\n" + 
				"where a.status_id = 'A'\n" + 
				"--(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where vat_registered = true )) a \n" + 
				"order by entity_name";
		
		return sql;

	}
	public String getWTaxID(String entity_id){//**EDITED BY JED 2019-05-24**//

		/*String sql = "select wtax_id as \"WTax ID\", " +
				"trim(wtax_desc) as \"Description\", " +
				"wtax_rate as \"Rate (%)\", " +
				"wtax_bir_code as \"Code\" " +
				"from rf_withholding_tax " +
				"order by wtax_id" ;
		return sql;*/
		String sql =
				"select \n" + 
				"				c.wtax_id as \"WTax ID\",\n" + 
				"				trim(wtax_desc) as \"Description\",\n" + 
				"				c.wtax_rate as \"Rate (%)\",\n" + 
				"				c.wtax_bir_code as \"Code\"\n" + 
				"				from (select * from rf_entity_assigned_type where trim(entity_id) =  '"+entity_id+"' and status_id = 'A' ) a\n" + 
				"				left join mf_entity_type b on a.entity_type_id = b.entity_type_id\n" + 
				"				left join rf_withholding_tax c on b.wtax_id = c.wtax_id   "  ;
		return sql;

	}
	private void generateDetail() {
		Object[] listCode = new Object[5];
		int row = 0;	

		listCode[0] = modelLiq_part.getValueAt(row, 3).equals("") ? null : modelLiq_part.getValueAt(row, 29);
		listCode[1] = modelLiq_part.getValueAt(row, 4).equals("") ? null : modelLiq_part.getValueAt(row, 30);
		listCode[2] = modelLiq_part.getValueAt(row, 6).equals("") ? null : modelLiq_part.getValueAt(row, 31);
		listCode[3] = modelLiq_part.getValueAt(row, 7).equals("") ? null : modelLiq_part.getValueAt(row, 32);
		listCode[4] = modelLiq_part.getValueAt(row, 12).equals("") ? null : modelLiq_part.getValueAt(row, 33);

		/*String listSelectedDivision = getDivision();
		if (listSelectedDivision != null) {
			listCode[2] = listSelectedDivision.toString().replaceAll("\\[|\\]", "").replace(", ", "-");
			tagDetail.setText(String.format("[Div: %s - Dep: %s - Proj: %s - Availer: %s]", listCode));
		} else {*/
			//listCode[2] = null;
			Object[] newListCode = new Object[5];
			newListCode[0] = listCode[0];
			newListCode[1] = listCode[1];
			newListCode[2] = listCode[2];
			newListCode[3] = listCode[3];
			newListCode[4] = listCode[4];

			tagDetail.setText(String.format("[ Div: %s - Dep: %s - Proj: %s - Sub: %s - Availer: %s ]", newListCode));
		//}
	}
	private void generateDetail(Integer lineno) {
		
		System.out.println("Dumaan dito sa generateDetail");
		Object[] listCode = new Object[5];
		int row = tblLiq_part.convertRowIndexToModel(tblLiq_part.getSelectedRow());

		listCode[0] = modelLiq_part.getValueAt(row, 3).equals("") ? null : modelLiq_part.getValueAt(row, 29);
		listCode[1] = modelLiq_part.getValueAt(row, 4).equals("") ? null : modelLiq_part.getValueAt(row, 30);
		listCode[2] = modelLiq_part.getValueAt(row, 6).equals("") ? null : modelLiq_part.getValueAt(row, 31);
		listCode[3] = modelLiq_part.getValueAt(row, 7).equals("") ? null : modelLiq_part.getValueAt(row, 32);
		listCode[4] = modelLiq_part.getValueAt(row, 12).equals("") ? null : modelLiq_part.getValueAt(row, 33);

		Object[] newListCode = new Object[5];
		newListCode[0] = listCode[0];
		newListCode[1] = listCode[1];
		newListCode[2] = listCode[2];
		newListCode[3] = listCode[3];
		newListCode[4] = listCode[4];

			tagDetail.setText(String.format("[ Div: %s - Dep: %s - Proj: %s - Sub: %s - Availer: %s ]", newListCode));
	}
	private static Boolean isYearMonthOpen(String year, String month){

		Boolean isOpen = false;		

		String SQL = 
			"select\r\n" + 
			"\r\n" + 
			"(case when status_id = 'A' then true else false end) as open \r\n" + 
			"from mf_acctg_period\r\n" + 
			"where period_year = '"+year+"'  " +
			"and month_code = '"+month+"' " ;

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
	private Boolean checkPeriod(){//used

		boolean x = false;		

		Integer month = (Integer) dteLiqui.getDate().getMonth()+1;
		Integer per = new Integer (lookupPeriod.getText());

		if (per.equals(month) || per.equals(13) || per.equals(14) || per.equals(15) || per.equals(99)) {
			x=false;
		} else {
			x=true;
		}		

		return x;

	}
	private static String getPeriod(){//Used
		return 
		"select '01' as \"ID\", 'JANUARY' as \"Period Name\" union all "  +
		"select '02' as \"ID\", 'FEBRUARY' as \"Period Name\" union all "  +
		"select '03' as \"ID\", 'MARCH' as \"Period Name\" union all "  +
		"select '04' as \"ID\", 'APRIL' as \"Period Name\" union all "  +
		"select '05' as \"ID\", 'MAY' as \"Period Name\" union all "  +
		"select '06' as \"ID\", 'JUNE' as \"Period Name\" union all "  +
		"select '07' as \"ID\", 'JULY' as \"Period Name\" union all "  +
		"select '08' as \"ID\", 'AUGUST' as \"Period Name\" union all "  +
		"select '09' as \"ID\", 'SEPTEMBER' as \"Period Name\" union all "  +
		"select '10' as \"ID\", 'OCTOBER' as \"Period Name\" union all "  +
		"select '11' as \"ID\", 'NOVEMBER' as \"Period Name\" union all "  +
		"select '12' as \"ID\", 'DECEMBER' as \"Period Name\" union all "  +
		"select '13' as \"ID\", '13TH MONTH' as \"Period Name\" union all "  +
		"select '14' as \"ID\", '14TH MONTH' as \"Period Name\" union all "  +
		"select '15' as \"ID\", '15TH MONTH' as \"Period Name\" union all "  +
		"select '99' as \"ID\", 'CLOSING PERIOD' as \"Period Name\"  "  ;

	}
}
