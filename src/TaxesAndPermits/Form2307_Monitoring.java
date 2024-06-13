package TaxesAndPermits;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Accounting.GeneralLedger.JournalVoucher;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;
import interfaces._GUI;
import tablemodel.modelEWT_Form2307_forReceipt;
import tablemodel.modelEWT_Form2307_forSending;
import tablemodel.modelEWT_forRemittance;
import tablemodel.modelEWT_remitted;

public class Form2307_Monitoring extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Form 2307 Monitoring";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlEWTDtl_1;
	private JPanel pnlEWTDtl_2;
	private JPanel pnlEWTDtl_1a;
	private JPanel pnlEWTDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlEWT;
	private JPanel pnlEWTInfo;
	private JPanel pnlEWTInfo_1;
	private JPanel pnlSouthCenterb;
	private JPanel pnlDeptDivAlias;
	private JPanel pnlNorth_b;
	private JPanel pnlNorth_a;
	private JLabel lblBlank;
	private JPanel pnlForSending;
	private JPanel pnl2307Received;

	private JLabel lblCompany;
	public static JLabel lblPayeeType;
	public static JLabel lblPayee;
	public static JLabel lblYear;
	public static JLabel lblPeriod;
	public static JLabel lblMonth;
	public static JLabel lblPV_no;
	private JLabel lblX;		

	public static _JLookup lookupCompany;
	public static _JLookup lookupPayeeType;
	static _JLookup lookupDiv;
	static _JLookup lookupDep;
	public static _JLookup lookupPayee;
	public static _JLookup lookupPV_no;

	private _JScrollPaneMain scrollFor_sending;
	private _JScrollPaneMain scrollReceived;

	public static modelEWT_forRemittance modelLiq_part;
	public static modelEWT_forRemittance modelLiq_part_total;
	public static modelEWT_remitted modelRemitted;
	public static modelEWT_remitted modelRemitted_total;
	public static modelEWT_Form2307_forSending modelFor_sending;
	public static modelEWT_Form2307_forSending modelFor_sending_total;
	public static modelEWT_Form2307_forReceipt modelReceived;
	public static modelEWT_Form2307_forReceipt modelFor_receipt;
	public static modelEWT_Form2307_forReceipt modelFor_receipt_total;
	public static modelEWT_Form2307_forReceipt modelReceived_total;

	private static _JTableTotal tblFor_sending_total;
	private static _JTableTotal tblReceived_total;

	private static _JTableMain tblLiq_part;	
	private static _JTableMain tblRemitted;
	private static _JTableMain tblFor_sending;
	private static _JTableMain tblReceived;

	public static JList rowHeaderLiq_part;
	public static JList rowHeaderRemitted;
	public static JList rowHeaderFor_sending;
	public static JList rowHeaderReceived;
	public static JList rowHeaderFor_receipt;

	public static _JTabbedPane tabCenter;	

	private static _JScrollPaneTotal scrollFor_sending_total;
	private static _JScrollPaneTotal scrollReceived_total;	

	public static _JTagLabel tagCompany;	
	private _JTagLabel tagBlank;
	public static _JTagLabel tagPayee;
	public static _JTagLabel tagPayeeType;
	public static _JTagLabel tagPV_no;

	public static JButton btnCancel;	
	public static JButton btnGenerate;
	private static JButton btnPreview;
	private static JButton btnSendToPayee;
	private static JButton btnReceiveByPayee;

	public static JComboBox cmbYear;
	public static JComboBox cmbPeriod;
	public static JComboBox cmbMonth;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public static JRadioButton rbtnAllPayee;
	public static JRadioButton rbtnDisbursement;
	public static JRadioButton rbtnCommission;
	

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JPopupMenu menu;
	private JMenuItem mniopenRPLF;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenJV;

	public static String co_id = "";	
	public static String company = "";
	public static String company_logo = "";	
	static String payee = "";	
	String overdue_deduction;
	public static JLabel lblRequestType;
	public static JComboBox cmbPmtType;

	//for preview of 2307 purposes
	public static String period = "";
	public static String period_no = "";
	public static String month_from = "";
	public static String month_to = "";	
	public static String day_from = "";
	public static String day_to = "";
	public static String year 	= "";
	public static String month 	= "";
	public static String PV_num 	= "";
	public static String paymnt_type = "";
	
	private static String acct_id = "";
	public static String entity_type_id = "";
	
	String BIR;
	private static JButton btnPreviewAll;

	public Form2307_Monitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Form2307_Monitoring(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Form2307_Monitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(935, 586));
		this.setBounds(0, 0, 935, 586);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenPV = new JMenuItem("Open Payable Voucher");	
			mniopenJV = new JMenuItem("Open Journal Voucher");
			menu.add(mniopenRPLF);
			menu.add(mniopenPV);
			menu.add(mniopenJV);

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
			mniopenJV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openJV();				
				}
			});

		}


		pnlNorth = new JPanel();
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.setLayout(new BorderLayout(5, 5));
		pnlNorth.setBorder(lineBorder);		
		pnlNorth.setPreferredSize(new java.awt.Dimension(923, 190));

		{
			pnlNorth_a = new JPanel();
			pnlNorth.add(pnlNorth_a, BorderLayout.CENTER);
			pnlNorth_a.setLayout(new BorderLayout(5, 5));
			pnlNorth_a.setBorder(lineBorder);		
			pnlNorth_a.setPreferredSize(new java.awt.Dimension(923, 134));

			pnlComp = new JPanel(new BorderLayout(5, 5));
			pnlNorth_a.add(pnlComp, BorderLayout.NORTH);	
			pnlComp.setBorder(lineBorder);

			//company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
			pnlComp_a.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(202, 30));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));

			{
				lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
				pnlComp_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(87, 25));
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

							lblYear.setEnabled(true);
							lblPeriod.setEnabled(true);
							lblMonth.setEnabled(true);	
							cmbYear.setEnabled(true);
							cmbPeriod.setEnabled(true);
							cmbMonth.setEnabled(true);
							btnGenerate.setEnabled(true);
							btnCancel.setEnabled(true);			

							lookupPayeeType.setLookupSQL(getAvailerType());
							lookupPayee.setLookupSQL(getEntityList());
							lookupPV_no.setLookupSQL(getPV_no());

							rbtnAllPayee.setEnabled(true);
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

			pnlEWT = new JPanel(new BorderLayout(5, 5));
			pnlNorth_a.add(pnlEWT, BorderLayout.CENTER);				
			pnlEWT.setPreferredSize(new java.awt.Dimension(921, 100));
			pnlEWT.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlEWT.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			{	
				pnlEWTDtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlEWT.add(pnlEWTDtl_1, BorderLayout.WEST);	
				pnlEWTDtl_1.setPreferredSize(new java.awt.Dimension(209, 87));
				pnlEWTDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

				pnlEWTDtl_1a = new JPanel(new GridLayout(5, 1, 0, 5));
				pnlEWTDtl_1.add(pnlEWTDtl_1a, BorderLayout.WEST);	
				pnlEWTDtl_1a.setPreferredSize(new java.awt.Dimension(87, 110));
				pnlEWTDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));							
				{
					lblBlank = new JLabel("Payee", JLabel.TRAILING);
					pnlEWTDtl_1a.add(lblBlank);
					lblBlank.setEnabled(false);	
					lblBlank.setVisible(false);	
				}
				{
					lblPayeeType = new JLabel("Payee Type", JLabel.TRAILING);
					pnlEWTDtl_1a.add(lblPayeeType);
					lblPayeeType.setEnabled(false);	
				}	
				{
					lblPayee = new JLabel("Payee", JLabel.TRAILING);
					pnlEWTDtl_1a.add(lblPayee);
					lblPayee.setEnabled(false);	
				}
				{
					lblPV_no = new JLabel("PV No.", JLabel.TRAILING);
					pnlEWTDtl_1a.add(lblPV_no);
					lblPV_no.setEnabled(false);	
				}
				{
					lblRequestType = new JLabel("Payment Type", JLabel.TRAILING);
					pnlEWTDtl_1a.add(lblRequestType);
					lblRequestType.setEnabled(false);	
				}


				pnlEWTDtl_1b = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlEWTDtl_1.add(pnlEWTDtl_1b, BorderLayout.CENTER);	
				pnlEWTDtl_1b.setPreferredSize(new java.awt.Dimension(130, 110));
				pnlEWTDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				{
					rbtnAllPayee = new JRadioButton();
					pnlEWTDtl_1b.add(rbtnAllPayee);
					rbtnAllPayee.setText("All Payees / PV");
					rbtnAllPayee.setBounds(277, 12, 77, 18);
					rbtnAllPayee.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtnAllPayee.setPreferredSize(new java.awt.Dimension(164, 23));
					rbtnAllPayee.setSelected(true);
					rbtnAllPayee.setEnabled(false);
					rbtnAllPayee.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							if (rbtnAllPayee.isSelected()==false)
							{
								lblPayeeType.setEnabled(true);	
								lookupPayeeType.setEnabled(true);	
								lblPayee.setEnabled(true);	
								lookupPayee.setEnabled(true);	
								tagPayeeType.setEnabled(true);	
								tagPayee.setEnabled(true);		
								//lblPV_no.setEnabled(true);	
								//lookupPV_no.setEnabled(true);	
								//tagPV_no.setEnabled(true);	
								lblRequestType.setEnabled(true);	
								cmbPmtType.setEnabled(true);
								cmbPmtType.setSelectedIndex(0);	
							} 
							else 
							{
								lblPayeeType.setEnabled(false);	
								lookupPayeeType.setEnabled(false);	
								lblPayee.setEnabled(false);	
								lookupPayee.setEnabled(false);	
								tagPayeeType.setEnabled(false);	
								tagPayee.setEnabled(false);	
								lookupPayeeType.setValue("");
								lookupPayee.setValue("");
								lookupPV_no.setValue("");
								tagPayeeType.setTag("");	
								tagPayee.setTag("");
								tagPV_no.setTag("");
								payee = "";			
								lblPV_no.setEnabled(false);	
								lookupPV_no.setEnabled(false);	
								tagPV_no.setEnabled(false);	
								//lblRequestType.setEnabled(false);	
								//cmbPmtType.setEnabled(false);
								//cmbPmtType.setSelectedIndex(0);	
							}				
						}});
					/*{
						rbtnDisbursement = new JRadioButton();
						pnlEWTDtl_1b.add(rbtnDisbursement);
						rbtnDisbursement.setText("Disbursement");
						rbtnDisbursement.setBounds(300, 12, 77, 18);
						rbtnDisbursement.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						rbtnDisbursement.setPreferredSize(new java.awt.Dimension(164, 23));
						rbtnDisbursement.setSelected(true);
						rbtnDisbursement.setEnabled(false);
					}
					{
						rbtnCommission = new JRadioButton();
						pnlEWTDtl_1b.add(rbtnCommission);
						rbtnCommission.setText("Commission");
						rbtnCommission.setBounds(350, 12, 77, 18);
						rbtnCommission.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						rbtnCommission.setPreferredSize(new java.awt.Dimension(164, 23));
						rbtnCommission.setSelected(true);
						rbtnCommission.setEnabled(false);
					}*/
				}
				{
					lookupPayeeType = new _JLookup(null, "Payee Type", 2, 2);
					pnlEWTDtl_1b.add(lookupPayeeType);
					lookupPayeeType.setBounds(20, 27, 20, 25);
					lookupPayeeType.setReturnColumn(0);
					lookupPayeeType.setEnabled(false);	
					lookupPayeeType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPayeeType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){		
								entity_type_id = data[0].toString();
								tagPayeeType.setTag((String) data[1]);
								lookupPayee.setLookupSQL(getEntityList());
								lookupPayee.setValue("");
								tagPayee.setText("[ ]");
								lookupPayee.setLookupSQL(getEntityList());
							}
						}
					});	
				}
				{
					lookupPayee= new _JLookup(null, "Payee", 2, 2);
					pnlEWTDtl_1b.add(lookupPayee);
					lookupPayee.setBounds(20, 27, 20, 25);
					lookupPayee.setReturnColumn(0);
					lookupPayee.setFilterIndex(1);
					lookupPayee.setEnabled(false);	
					lookupPayee.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPayee.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){			
								payee = (String) data[0];
								tagPayee.setTag((String) data[1]);								
							}
						}
					});	
				}

				{
					lookupPV_no = new _JLookup(null, "PV No.", 2, 2);
					pnlEWTDtl_1b.add(lookupPV_no);
					lookupPV_no.setBounds(20, 27, 20, 25);
					lookupPV_no.setReturnColumn(0);
					lookupPV_no.setEnabled(false);	
					lookupPV_no.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPV_no.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){			
								PV_num = (String) data[0];
								tagPV_no.setTag((String) data[3]);								
							}
						}
					});	
				}
				{
					String status[] = {"All","Comm/Promo","Contractors","MAF/SOE","GAE/SME"};					
					cmbPmtType = new JComboBox(status);
					pnlEWTDtl_1b.add(cmbPmtType);
					cmbPmtType.setSelectedItem(null);
					cmbPmtType.setBounds(537, 15, 160, 21);	
					cmbPmtType.setEnabled(true);
					cmbPmtType.setSelectedIndex(0);	
					((JLabel)cmbPmtType.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
					cmbPmtType.setPreferredSize(new java.awt.Dimension(89, 22));
					cmbPmtType.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
							if (cmbPmtType.getSelectedIndex()==0)
							{
								acct_id = "";
								paymnt_type = "0";
							}
							else if (cmbPmtType.getSelectedIndex()==1)
							{
								acct_id = "01-02-09%";
								paymnt_type = "1";
							}
							else if (cmbPmtType.getSelectedIndex()==2)
							{
								acct_id = "01-03%";
								paymnt_type = "2";
							}
							else if (cmbPmtType.getSelectedIndex()==3)
							{
								acct_id = "08-03%";
								paymnt_type = "3";
							}
							else if (cmbPmtType.getSelectedIndex()==4)
							{
								acct_id = "08-01%";
								paymnt_type = "4";
							}
							
							
						}
					});		
				}


				//Start of Left Panel 
				pnlDeptDivAlias = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlEWT.add(pnlDeptDivAlias, BorderLayout.CENTER);
				pnlDeptDivAlias.setPreferredSize(new java.awt.Dimension(136, 72));
				pnlDeptDivAlias.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));				

				{
					tagBlank = new _JTagLabel("[ ]");
					pnlDeptDivAlias.add(tagBlank);
					tagBlank.setBounds(209, 27, 700, 22);
					tagBlank.setVisible(false);						
					tagBlank.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagPayeeType = new _JTagLabel("[ ]");
					pnlDeptDivAlias.add(tagPayeeType);
					tagPayeeType.setBounds(209, 27, 700, 22);
					tagPayeeType.setEnabled(false);	
					tagPayeeType.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagPayee = new _JTagLabel("[ ]");
					pnlDeptDivAlias.add(tagPayee);
					tagPayee.setBounds(209, 27, 700, 22);
					tagPayee.setEnabled(false);						
					tagPayee.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagPV_no = new _JTagLabel("[ ]");
					pnlDeptDivAlias.add(tagPV_no);
					tagPV_no.setBounds(209, 27, 700, 22);
					tagPV_no.setEnabled(false);						
					tagPV_no.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagBlank = new _JTagLabel("[ ]");
					pnlDeptDivAlias.add(tagBlank);
					tagBlank.setBounds(209, 27, 700, 22);
					tagBlank.setVisible(false);						
					tagBlank.setPreferredSize(new java.awt.Dimension(27, 33));
				}



				pnlEWTInfo = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlEWT.add(pnlEWTInfo, BorderLayout.EAST);
				pnlEWTInfo.setPreferredSize(new java.awt.Dimension(211, 90));
				pnlEWTInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlEWTInfo_1 = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlEWTInfo.add(pnlEWTInfo_1, BorderLayout.WEST);
				pnlEWTInfo_1.setPreferredSize(new java.awt.Dimension(101, 116));
				pnlEWTInfo_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblX = new JLabel("", JLabel.TRAILING);
					pnlEWTInfo_1.add(lblX);
					lblX.setEnabled(false);	
					lblX.setVisible(false);	
				}	
				{
					lblYear = new JLabel("Year", JLabel.TRAILING);
					pnlEWTInfo_1.add(lblYear);
					lblYear.setEnabled(false);	
				}	
				{
					lblPeriod = new JLabel("Period", JLabel.TRAILING);
					pnlEWTInfo_1.add(lblPeriod);
					lblPeriod.setEnabled(false);	
				}	
				{
					lblMonth = new JLabel("Month", JLabel.TRAILING);
					pnlEWTInfo_1.add(lblMonth);
					lblMonth.setEnabled(false);	
				}	


				pnlEWTDtl_2 = new JPanel(new GridLayout(5, 1, 0, 5));
				pnlEWTInfo.add(pnlEWTDtl_2, BorderLayout.CENTER);
				pnlEWTDtl_2.setPreferredSize(new java.awt.Dimension(98, 90));
				pnlEWTDtl_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

				{
					lblX = new JLabel("Cutoff", JLabel.TRAILING);
					pnlEWTDtl_2.add(lblX);
					lblX.setEnabled(true);	
					lblX.setHorizontalAlignment(JLabel.CENTER);
					//lblX.setVisible(false);	
				}	
				{
					String status[] = {"2014","2015","2016","2017","2018", "2019", "2020", "2021"};					
					cmbYear = new JComboBox(status);
					pnlEWTDtl_2.add(cmbYear);
					cmbYear.setSelectedItem(null);
					cmbYear.setBounds(537, 15, 160, 21);	
					cmbYear.setSelectedItem(getMonthYear());
					cmbYear.setEnabled(false);
					//cmbYear.setSelectedIndex(0);	
					((JLabel)cmbYear.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
					cmbYear.setPreferredSize(new java.awt.Dimension(89, 22));
					cmbYear.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
							refresh_tablesMain();
						}
					});		
				}
				{
					String status[] = {"ALL","1st Qtr","2nd Qtr","3rd Qtr","4th Qtr"};					
					cmbPeriod = new JComboBox(status);
					pnlEWTDtl_2.add(cmbPeriod);
					cmbPeriod.setSelectedItem(null);
					cmbPeriod.setBounds(537, 15, 160, 21);	
					cmbPeriod.setEnabled(false);
					cmbPeriod.setSelectedIndex(0);	
					((JLabel)cmbPeriod.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
					cmbPeriod.setPreferredSize(new java.awt.Dimension(89, 22));
					cmbPeriod.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
							refresh_tablesMain();
							if (cmbPeriod.getSelectedIndex()==0)
							{
								lblMonth.setEnabled(false);	
								cmbMonth.setEnabled(false);
								cmbMonth.setSelectedIndex(0);
							}
							else 
							{
								lblMonth.setEnabled(true);	
								cmbMonth.setEnabled(true);
							}
						}
					});		
				}
				{
					String status[] = {"ALL","1","2","3"};					
					cmbMonth = new JComboBox(status);
					pnlEWTDtl_2.add(cmbMonth);
					cmbMonth.setSelectedItem(null);
					cmbMonth.setBounds(537, 15, 160, 21);	
					cmbMonth.setEnabled(false);
					cmbMonth.setSelectedIndex(0);	
					((JLabel)cmbMonth.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
					cmbMonth.setPreferredSize(new java.awt.Dimension(89, 22));
					cmbMonth.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
							refresh_tablesMain();							
						}
					});		
				}
			}
		}	
		{
			pnlNorth_b = new JPanel();
			pnlNorth.add(pnlNorth_b, BorderLayout.EAST);
			pnlNorth_b.setLayout(new BorderLayout(5, 5));
			pnlNorth_b.setBorder(lineBorder);		
			pnlNorth_b.setPreferredSize(new java.awt.Dimension(206, 132));

			{
				btnGenerate = new JButton("Generate EWT");
				pnlNorth_b.add(btnGenerate);
				btnGenerate.setActionCommand("GenerateEWT");
				btnGenerate.addActionListener(this);
				btnGenerate.setEnabled(false);
				btnGenerate.setPreferredSize(new java.awt.Dimension(204, 130));
			}
		}

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlEWT = new JPanel();
			pnlTable.add(pnlEWT, BorderLayout.CENTER);
			pnlEWT.setLayout(new BorderLayout(5, 5));
			pnlEWT.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlEWT.setBorder(lineBorder);	


			tabCenter = new _JTabbedPane();
			pnlEWT.add(tabCenter, BorderLayout.CENTER);
			tabCenter.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {setButtonsStatus2();}
			});
			tabCenter.addPropertyChangeListener( new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {setButtonsStatus2();}
			});		
	
			{		

				pnlForSending = new JPanel(new BorderLayout());
				tabCenter.addTab("Form 2307 - For Sending", null, pnlForSending, null);
				pnlForSending.setPreferredSize(new java.awt.Dimension(1183, 365));		

				{
					scrollFor_sending = new _JScrollPaneMain();
					pnlForSending.add(scrollFor_sending, BorderLayout.CENTER);
					{
						modelFor_sending = new modelEWT_Form2307_forSending();

						tblFor_sending = new _JTableMain(modelFor_sending);
						scrollFor_sending.setViewportView(tblFor_sending);
						tblFor_sending.addMouseListener(new PopupTriggerListener_panel());
						tblFor_sending.getColumnModel().getColumn(0).setPreferredWidth(50);
						tblFor_sending.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblFor_sending.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblFor_sending.getColumnModel().getColumn(3).setPreferredWidth(100);
						tblFor_sending.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblFor_sending.getColumnModel().getColumn(5).setPreferredWidth(300);

						tblFor_sending.addMouseListener(this);	
						tblFor_sending.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblFor_sending.rowAtPoint(e.getPoint()) == -1){}
								else{}
								setButtonsStatus2();
							}
							public void mouseReleased(MouseEvent e) {
								if(tblFor_sending.rowAtPoint(e.getPoint()) == -1){}
								else{}
							}
						});
					}
					{
						tblFor_sending.hideColumns("Entity ID");
						
						rowHeaderFor_sending = tblFor_sending.getRowHeader();
						scrollFor_sending.setRowHeaderView(rowHeaderFor_sending);
						scrollFor_sending.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollFor_sending_total = new _JScrollPaneTotal(scrollFor_sending);
					pnlForSending.add(scrollFor_sending_total, BorderLayout.SOUTH);
					{
						modelFor_sending_total = new modelEWT_Form2307_forSending();
						modelFor_sending_total.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00),new BigDecimal(0.00),null, null});

						tblFor_sending_total = new _JTableTotal(modelFor_sending_total, tblFor_sending);
						tblFor_sending_total.setFont(dialog11Bold);
						scrollFor_sending_total.setViewportView(tblFor_sending_total);
						((_JTableTotal) tblFor_sending_total).setTotalLabel(0);
					}
				}
			}
			{		

				pnl2307Received = new JPanel(new BorderLayout());
				tabCenter.addTab("Form 2307 - Received", null, pnl2307Received, null);
				pnl2307Received.setPreferredSize(new java.awt.Dimension(1183, 365));		

				{
					scrollReceived = new _JScrollPaneMain();
					pnl2307Received.add(scrollReceived, BorderLayout.CENTER);
					{
						modelReceived = new modelEWT_Form2307_forReceipt();

						tblReceived = new _JTableMain(modelReceived);
						scrollReceived.setViewportView(tblReceived);
						tblReceived.addMouseListener(new PopupTriggerListener_panel());
						tblReceived.addMouseListener(this);	
						tblReceived.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblReceived.rowAtPoint(e.getPoint()) == -1){}
								else{}
								setButtonsStatus2();
							}
							public void mouseReleased(MouseEvent e) {
								if(tblReceived.rowAtPoint(e.getPoint()) == -1){}
								else{}
							}
						});
					}
					{
						rowHeaderReceived = tblReceived.getRowHeader();
						scrollReceived.setRowHeaderView(rowHeaderReceived);
						scrollReceived.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollReceived_total = new _JScrollPaneTotal(scrollReceived);
					pnl2307Received.add(scrollReceived_total, BorderLayout.SOUTH);
					{
						modelReceived_total = new modelEWT_Form2307_forReceipt();
						modelReceived_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00),new BigDecimal(0.00)});

						tblReceived_total = new _JTableTotal(modelReceived_total, tblReceived);
						//tblReceived_total.hideColumns("PV No.");
						tblReceived_total.setFont(dialog11Bold);
						scrollReceived_total.setViewportView(tblReceived_total);
						((_JTableTotal) tblReceived_total).setTotalLabel(0);
					}
				}
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(923, 35));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{	
				{
					btnPreview = new JButton("Preview Form 2307");
					pnlSouthCenterb.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnPreviewAll = new JButton("Preview All");
					pnlSouthCenterb.add(btnPreviewAll);
					btnPreviewAll.setActionCommand("All");
					btnPreviewAll.addActionListener(this);
					btnPreviewAll.setEnabled(false);
				}
				{
					btnSendToPayee= new JButton("Send to Payee");
					pnlSouthCenterb.add(btnSendToPayee);
					btnSendToPayee.setActionCommand("SendToPayee");
					btnSendToPayee.addActionListener(this);
					btnSendToPayee.setEnabled(false);
				}
//				{
//					btnReceiveByPayee= new JButton("Refresh");
//					pnlSouthCenterb.add(btnReceiveByPayee);
//					btnReceiveByPayee.setActionCommand("ReceiveByPayee");
//					btnReceiveByPayee.addActionListener(this);
//					btnReceiveByPayee.setEnabled(false);
//					btnReceiveByPayee.setVisible(false);
//				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}			
		}
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display tables	
	public static void displayEWT_for2307Sending(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, 
			String payee_id, String rplf_no, String year, String period, String month, String jv_no) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"--displayEWT_for2307_sending \n" +
			"select * from view_displayEWT_for2307_sending (" +
			"'"+payee_id+"'::text," +
			"'"+rplf_no+"'::text," +
			"'"+jv_no+"'::text," +
			"'"+year+"'::text," +
			"'"+period+"'::text," +
			"'"+month+"'::text," +
			"'"+PV_num+"'::text," +
			"'"+paymnt_type+"'::text," +
			"'"+entity_type_id+"'::text" +			
			")" ;
			
			
			/*"select false, * from (" +
			"\r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"d.pv_no," +
			"d.pv_date," +
			"d.date_posted," +
			"d.cv_no," +
			"upper(trim(c.entity_name)) as client,\r\n" + 
			"a.exp_amt - a.wtax_amt," +
			"a.wtax_amt, \n" +			
			"g.date_remitted,  " +
			"upper(trim(i.first_name))||' '||upper(trim(i.last_name)),\r\n" +
			"g.rem_rplf_no," +
			"j.date_received  " +
			"\r\n" + 
			"from (select * from rf_request_detail where status_id != 'I') a\r\n" + 
			"left join (select * from rf_request_header where status_id != 'I') b on a.rplf_no =b.rplf_no and a.co_id = b.co_id\r\n" + 
			"left join rf_entity c on \n"  +
			"	(case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(b.entity_id1) end) = c.entity_id \r\n" +
			"join  (select * from rf_pv_header where status_id = 'P') d on b.rplf_no = d.rplf_no and b.co_id = d.co_id\r\n" + 
			"left join (select * from rf_cv_header where status_id != 'I') e on d.cv_no = e.cv_no and d.co_id = e.co_id\r\n" + 
			"left join (select distinct on (rplf_no) * from rf_ewt_remittance ) g on a.rplf_no = g.rplf_no  \n" +
			"left join em_employee h on g.created_by = h.emp_code\r\n" + 
			"left join rf_entity i on h.entity_id = i.entity_id \n" +
			"left join (select distinct on (pv_no, co_id) pv_no, co_id, date_received from rf_form2307_monitoring) j on a.rplf_no = j.pv_no and a.co_id = j.co_id   \r\n" + 
			"\r\n" + 
			"where a.wtax_amt > 0 \r\n" + 
			"and b.rplf_date >= '2014-01-01 00:00:00' \r\n" + 
			"and a.co_id = '"+co_id+"' " ;

		if (payee_id.equals("")) {} 
		else {sql = sql + "and (case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(b.entity_id1) end) = '"+payee_id.trim()+"' \n";} 

		if (rplf_no.equals("")) {} 
		else {sql = sql + "and b.rplf_no = '"+rplf_no+"' \n";} 

		if (jv_no.equals("")) {} 
		else {sql = sql + "and b.rplf_no is null \n";} 

		if (year.equals("")) {} 
		else {sql = sql + "and trim(to_char(d.pv_date, 'yyyy')) = '"+year+"' \n";} 

		if (period.equals("")) {} 
		else {sql = sql + "and substr(trim(to_char(d.pv_date, 'MM-dd-yyyy')),0,3) in ( "+period+" ) \n";} 

		if (month.equals("")) {} 
		else {sql = sql + "and substr(trim(to_char(d.pv_date, 'MM-dd-yyyy')),0,3) = '"+month+"' \n\n";} 

		if (PV_num.equals("")) {} 
		else {sql = sql + "and a.rplf_no = '"+PV_num+"' \n\n";} 
		
		if (acct_id.equals("")) {} 
		else {sql = sql + "and a.acct_id like '"+acct_id+"' \n\n";} 
		
		if (entity_type_id.equals("")) {} 
		else {sql = sql + "and b.entity_type_id = '"+entity_type_id+"' \n\n";}
 

		sql = sql +	
				
		") a \n" +
		"where a.date_received is null \n\n" +
		"order by a.client, a.pv_no \n\n"  ;
*/
		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalEWT_forSending(modelMain, modelTotal);				
		}		

		else {
			modelFor_sending_total = new modelEWT_Form2307_forSending();
			modelFor_sending_total.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00),new BigDecimal(0.00),null, null});

			tblFor_sending_total = new _JTableTotal(modelFor_sending_total, tblFor_sending);
			tblFor_sending_total.setFont(dialog11Bold);
			scrollFor_sending_total.setViewportView(tblFor_sending_total);
			((_JTableTotal) tblFor_sending_total).setTotalLabel(0);
			JOptionPane.showMessageDialog(null,"No Form 2307 for sending for the selected paramter(s).","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		tblFor_sending.packAll();
		tblFor_sending.getColumnModel().getColumn(0).setPreferredWidth(50);
		modelFor_sending.setEditable(true);
	}
	
	public static void displayEWT_for2307SendingCommission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, 
			String payee_id, String rplf_no, String year, String period, String month, String jv_no) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"--displayEWT_for2307_sendingcommission \n" +
			"select * from view_displayEWT_for2307_sending (" +
			"'"+payee_id+"'::text," +
			"'"+rplf_no+"'::text," +
			"'"+jv_no+"'::text," +
			"'"+year+"'::text," +
			"'"+period+"'::text," +
			"'"+month+"'::text," +
			"'"+PV_num+"'::text," +
			"'"+paymnt_type+"'::text," +
			"'"+entity_type_id+"'::text" +			
			")" ;
			
		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalEWT_forSending(modelMain, modelTotal);				
		}		

		else {
			modelFor_sending_total = new modelEWT_Form2307_forSending();
			modelFor_sending_total.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00),new BigDecimal(0.00),null, null});

			tblFor_sending_total = new _JTableTotal(modelFor_sending_total, tblFor_sending);
			tblFor_sending_total.setFont(dialog11Bold);
			scrollFor_sending_total.setViewportView(tblFor_sending_total);
			((_JTableTotal) tblFor_sending_total).setTotalLabel(0);
			JOptionPane.showMessageDialog(null,"No Form 2307 for sending for the selected paramter(s).","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		tblFor_sending.packAll();
		tblFor_sending.getColumnModel().getColumn(0).setPreferredWidth(50);
		modelFor_sending.setEditable(true);
	}
	
	public static void displayEWT_for2307Received(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, 
			String payee_id, String rplf_no, String year, String period, String month, String jv_no) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"--displayEWT_for2307Received \n" +
			"select * from (" +
			"\r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"d.pv_no," +
			"d.pv_date," +
			"d.date_posted," +
			"upper(trim(c.entity_name)) as client,\r\n" + 
			"a.exp_amt - a.wtax_amt," +
			"a.wtax_amt, \n" +			
			"g.date_remitted,  " +
			"upper(trim(i.first_name))||' '||upper(trim(i.last_name)),\r\n" +
			"g.rem_rplf_no,  " +
			"upper(trim(l.first_name))||' '||upper(trim(l.last_name)),\r\n" +
			"j.date_received  " +
			"\r\n" + 
			"from (select * from rf_request_detail where status_id != 'I') a\r\n" + 
			"left join  (select * from rf_request_header where status_id != 'I') b on a.rplf_no =b.rplf_no and a.co_id = b.co_id\r\n" + 
			"left join rf_entity c on \n"  +
			"	(case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(b.entity_id1) end) = c.entity_id \r\n" +
			"join  (select * from rf_pv_header where status_id = 'P') d on b.rplf_no = d.rplf_no and b.co_id = d.co_id\r\n" + 
			"left join (select * from rf_cv_header where status_id != 'I') e on d.cv_no = e.cv_no and d.co_id = e.co_id\r\n" + 
			"left join ( select distinct on (rplf_no) * from rf_ewt_remittance ) g on a.rplf_no = g.rplf_no   \n" +
			"left join em_employee h on g.created_by = h.emp_code\r\n" + 
			"left join rf_entity i on h.entity_id = i.entity_id \r\n" + 
			"left join (select distinct on (pv_no, co_id) pv_no, co_id, sent_by, date_received from rf_form2307_monitoring) j on a.rplf_no = j.pv_no and a.co_id = j.co_id   \r\n" + 
			"left join em_employee k on j.sent_by = k.emp_code \r\n" + 
			"left join rf_entity l on k.entity_id = l.entity_id \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"where a.wtax_amt > 0 \r\n" + 
			"and b.rplf_date >= '2014-01-01 00:00:00' \r\n" + 
			"and a.co_id = '"+co_id+"' " ;

		if (payee_id.equals("")) {} 
		else {sql = sql + "and (case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(b.entity_id1) end) = '"+payee_id.trim()+"' \n";} 

		if (rplf_no.equals("")) {} 
		else {sql = sql + "and b.rplf_no = '"+rplf_no+"' \n";} 

		if (jv_no.equals("")) {} 
		else {sql = sql + "and b.rplf_no is null \n";} 

		if (year.equals("")) {} 
		else {sql = sql + "and trim(to_char(d.pv_date, 'yyyy')) = '"+year+"' \n";} 

		if (period.equals("")) {} 
		else {sql = sql + "and substr(trim(to_char(d.pv_date, 'MM-dd-yyyy')),0,3) in ( "+period+" ) \n";} 

		if (month.equals("")) {} 
		else {sql = sql + "and substr(trim(to_char(d.pv_date, 'MM-dd-yyyy')),0,3) = '"+month+"' \n\n";} 

		if (PV_num.equals("")) {} 
		else {sql = sql + "and a.rplf_no = '"+PV_num+"' \n\n";} 
		
		if (acct_id.equals("")) {} 
		else {sql = sql + "and a.acct_id like '"+acct_id+"' \n\n";} 
		
		if (entity_type_id.equals("")) {} 
		else {sql = sql + "and b.entity_type_id = '"+entity_type_id+"' \n\n";}
 
		sql = sql +
		") a " +
				
		"where a.date_received is not null \n\n" +

		"order by a.client, a.pv_no \n\n"  ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalEWT_forReceived(modelMain, modelTotal);				
		}		

		else {
			modelReceived_total = new modelEWT_Form2307_forReceipt();
			modelReceived_total.addRow(new Object[] {  "Total", null, null, null, new BigDecimal(0.00),new BigDecimal(0.00)});

			tblReceived_total = new _JTableTotal(modelReceived_total, tblReceived);
			tblReceived_total.setFont(dialog11Bold);
			scrollReceived_total.setViewportView(tblReceived_total);
			((_JTableTotal) tblReceived_total).setTotalLabel(0);
			JOptionPane.showMessageDialog(null,"No Form 2307 received by payee for the selected parameter(s).","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		tblReceived.packAll();
		//tblReceived.getColumnModel().getColumn(0).setPreferredWidth(50);
	}



	//Enable/Disable all components inside JPanel
	public void enable_fields(Boolean x){//used

		lblPayeeType.setEnabled(x);	
		lblPayee.setEnabled(x);	
		lblPV_no.setEnabled(x);	
		lookupPayeeType.setEnabled(x);	
		lookupPayee.setEnabled(x);	
		lookupPV_no.setEnabled(x);	
		tagPayeeType.setEnabled(x);	
		tagPayee.setEnabled(x);
		tagPV_no.setEnabled(x);
		//btnPreviewSelected.setEnabled(x);

	}

	public void refresh_fields(){//used

		lookupPayeeType.setValue("");
		lookupPayee.setValue("");
		lookupPV_no.setValue("");
		tagPayeeType.setTag("");	
		tagPayee.setTag("");
		tagPV_no.setTag("");
		payee = "";		
		PV_num = "";		

		rbtnAllPayee.setSelected(true);

		cmbYear.setSelectedIndex(0);	
		cmbPeriod.setSelectedIndex(0);		
		cmbMonth.setSelectedIndex(0);		

	}

	public void refresh_tablesMain(){//used

		//reset table 3
		FncTables.clearTable(modelFor_sending);
		FncTables.clearTable(modelFor_sending_total);			
		rowHeaderFor_sending = tblFor_sending.getRowHeader();
		scrollFor_sending.setRowHeaderView(rowHeaderFor_sending);	
		modelFor_sending_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00),new BigDecimal(0.00),null, null});
		tblFor_sending_total = new _JTableTotal(modelFor_sending_total, tblFor_sending);
		tblFor_sending_total.setFont(dialog11Bold);
		scrollFor_sending_total.setViewportView(tblFor_sending_total);
		((_JTableTotal) tblFor_sending_total).setTotalLabel(0);

		//reset table 5
		FncTables.clearTable(modelReceived);
		FncTables.clearTable(modelReceived_total);			
		rowHeaderReceived = tblReceived.getRowHeader();
		scrollReceived.setRowHeaderView(rowHeaderReceived);	
		modelReceived_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00),new BigDecimal(0.00)});
		tblReceived_total = new _JTableTotal(modelReceived_total, tblReceived);
		tblReceived_total.setFont(dialog11Bold);
		scrollReceived_total.setViewportView(tblReceived_total);
		((_JTableTotal) tblReceived_total).setTotalLabel(0);


	}

	public static void setButtonsStatus(Boolean a, Boolean b, Boolean c, Boolean d){
		btnPreview.setEnabled(a);
		btnSendToPayee.setEnabled(b);
		//btnReceiveByPayee.setEnabled(c);
		if(cmbPeriod.getSelectedIndex() == 0 && cmbMonth.getSelectedIndex() == 0 && tabCenter.getSelectedIndex() == 0) {
			btnPreviewAll.setEnabled(true);
		}else {
			btnPreviewAll.setEnabled(c);
		}
		btnCancel.setEnabled(d);
	}

	public void setButtonsStatus2(){
		if (tabCenter.getSelectedIndex()==0) {setButtonsStatus(true, true, false, true);}
		else if (tabCenter.getSelectedIndex()==1) {setButtonsStatus(false, false, false, true);}
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		
		lblYear.setEnabled(true);
		lblPeriod.setEnabled(true);
		lblMonth.setEnabled(false);	
		cmbYear.setEnabled(true);
		cmbPeriod.setEnabled(true);
		cmbMonth.setEnabled(false);
		btnGenerate.setEnabled(true);
		btnCancel.setEnabled(true);			

		lookupPayeeType.setLookupSQL(getAvailerType());
		lookupPayee.setLookupSQL(getEntityList());
		lookupPV_no.setLookupSQL(getPV_no());

		rbtnAllPayee.setEnabled(true);
		
		lookupCompany.setValue(co_id);
}
	
	
	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//used

		if(e.getActionCommand().equals("GenerateEWT")){ generateAll(); }

		if(e.getActionCommand().equals("Cancel")){ cancel(); } //ok

		if(e.getActionCommand().equals("PreviewSelected")){ preview("", co_id); }

		if(e.getActionCommand().equals("Preview")){ preview_batch("02"); }

		if(e.getActionCommand().equals("SendToPayee")){ sendToPayee(); }
		
		if(e.getActionCommand().equals("All")){ previewAll(); }

	}

	private void previewAll() {
		
		String criteria = "Form EWT (2307)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		String pv_no = "";
		String secondPayor = "CARLOS CHENG / PRESIDENT / TIN 100-775-734-000"; //added by jed 8/29/18, change by JED 2018-12-10 from VICTOR H. MANARANG TO CARLOS CHENG dcrf no.879 for CDC//
		String firstPayor = "ANDRES CHUA / PRESIDENT / TIN 147-299-505-000";	//added by jed 8/29/18 for VDC
		String co_id = lookupCompany.getValue();
		String to_do = "All";
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		//System.out.printf("\n%s\n", list_pv_num.toString().replaceAll("\\[|\\]", ""));
		mapParameters.put("co_id", co_id);
		mapParameters.put("p_background", this.getClass().getClassLoader().getResourceAsStream("Images/Form2307new.jpg"));
		mapParameters.put("bir_logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + BIR));
		mapParameters.put("arrow1", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow2", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow3", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow4", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow5", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow6", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow7", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow8", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow9", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow10", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow11", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow12", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("year", cmbYear.getSelectedItem());
		mapParameters.put("month_from", "01");
		mapParameters.put("month_to", "12");
		mapParameters.put("day_from", day_from);
		mapParameters.put("day_to", day_to);
		mapParameters.put("payee", payee);
		//mapParameters.put("pv_no", pv_no_);
		//mapParameters.put("entity", entity_id);
		mapParameters.put("pv_num", pv_no);
		mapParameters.put("acct_id", acct_id);	
		mapParameters.put("entity_type_id", entity_type_id);
		//mapParameters.put("rplf_no", pv_no_);
		mapParameters.put("pymnt_type", paymnt_type);
		//mapParameters.put("jv_no", "");
		mapParameters.put("period_no", period_no);
		mapParameters.put("to_do", to_do);
		
		if(co_id.equals("01")){
			mapParameters.put("payor", firstPayor);/*ADDED by JED 8/29/18 : PAYOR FOR VDC*/
			//System.out.printf("The value of payor is: %s\n", firstPayor);
		}
		else{
			mapParameters.put("payor", secondPayor);/*ADDED by JED 8/29/18 : PAYOR FOR CDC*/
			//System.out.printf("The value of payor is: %s\n", secondPayor);
		}
		
		FncReport.generateReport("/Reports/rptForm2307_PV_new_quarterly.jasper", reportTitle, company, mapParameters);
	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2)) {

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

	private void generateAll(){		

		year = cmbYear.getSelectedItem().toString();
		month_from = "01"; month_to = "12";
		day_from = "01"; day_to = "31";

		if (cmbPeriod.getSelectedIndex()==0) {period_no = ""; period = ""; month_from = "01"; month_to = "12"; day_from = "01"; day_to = "31";} 
		else if (cmbPeriod.getSelectedIndex()==1) {period_no = "1"; period = "'01','02','03'"; month_from = "01"; month_to = "03"; day_from = "01"; day_to = "31"; } 
		else if (cmbPeriod.getSelectedIndex()==2) {period_no = "2"; period = "'04','05','06'"; month_from = "04"; month_to = "06"; day_from = "01"; day_to = "30"; } 
		else if (cmbPeriod.getSelectedIndex()==3) {period_no = "3"; period = "'07','08','09'"; month_from = "07"; month_to = "09"; day_from = "01"; day_to = "31"; } 
		else if (cmbPeriod.getSelectedIndex()==4) {period_no = "4"; period = "'10','11','12'"; month_from = "10"; month_to = "12"; day_from = "01"; day_to = "31";  } 

		if (cmbMonth.getSelectedIndex()==0 ) {month = "";} 
		else if (cmbPeriod.getSelectedIndex()==1 && cmbMonth.getSelectedIndex()==1 ) {month = "01"; month_from = "01"; month_to = "01"; day_from = "01"; day_to = "31";} 
		else if (cmbPeriod.getSelectedIndex()==1 && cmbMonth.getSelectedIndex()==2 ) {month = "02"; month_from = "02"; month_to = "02"; day_from = "01"; day_to = "28";} 
		else if (cmbPeriod.getSelectedIndex()==1 && cmbMonth.getSelectedIndex()==3 ) {month = "03"; month_from = "03"; month_to = "03"; day_from = "01"; day_to = "31";} 
		else if (cmbPeriod.getSelectedIndex()==2 && cmbMonth.getSelectedIndex()==1 ) {month = "04"; month_from = "04"; month_to = "04"; day_from = "01"; day_to = "30";} 
		else if (cmbPeriod.getSelectedIndex()==2 && cmbMonth.getSelectedIndex()==2 ) {month = "05"; month_from = "05"; month_to = "05"; day_from = "01"; day_to = "31";} 
		else if (cmbPeriod.getSelectedIndex()==2 && cmbMonth.getSelectedIndex()==3 ) {month = "06"; month_from = "06"; month_to = "06"; day_from = "01"; day_to = "30";} 
		else if (cmbPeriod.getSelectedIndex()==3 && cmbMonth.getSelectedIndex()==1 ) {month = "07"; month_from = "07"; month_to = "07"; day_from = "01"; day_to = "31";} 
		else if (cmbPeriod.getSelectedIndex()==3 && cmbMonth.getSelectedIndex()==2 ) {month = "08"; month_from = "08"; month_to = "08"; day_from = "01"; day_to = "31";} 
		else if (cmbPeriod.getSelectedIndex()==3 && cmbMonth.getSelectedIndex()==3 ) {month = "09"; month_from = "09"; month_to = "09"; day_from = "01"; day_to = "30";} 
		else if (cmbPeriod.getSelectedIndex()==4 && cmbMonth.getSelectedIndex()==1 ) {month = "10"; month_from = "10"; month_to = "10"; day_from = "01"; day_to = "31";} 
		else if (cmbPeriod.getSelectedIndex()==4 && cmbMonth.getSelectedIndex()==2 ) {month = "11"; month_from = "11"; month_to = "11"; day_from = "01"; day_to = "30";} 
		else if (cmbPeriod.getSelectedIndex()==4 && cmbMonth.getSelectedIndex()==3 ) {month = "12"; month_from = "12"; month_to = "12"; day_from = "01"; day_to = "31";} 	
		
		displayEWT_for2307Sending(modelFor_sending, rowHeaderFor_sending, modelFor_sending_total, payee, "", year, period_no, month, "" );	
		displayEWT_for2307Received(modelReceived, rowHeaderReceived, modelReceived_total, payee, "", year, period, month, "" );		

		/* if (cmbPeriod.getSelectedItem().equals("ALL") && cmbPmtType.getSelectedItem() != "Comm/Promo") {
			displayEWT_for2307Sending(modelFor_sending, rowHeaderFor_sending, modelFor_sending_total, payee, "", year, period_no, month, "" );	
			displayEWT_for2307Received(modelReceived, rowHeaderReceived, modelReceived_total, payee, "", year, period, month, "" );		
		} else {
			if (cmbPeriod.getSelectedItem().equals("ALL") && cmbPmtType.getSelectedItem().equals("Comm/Promo")) {
				displayEWT_for2307SendingCommission(modelFor_sending, rowHeaderFor_sending, modelFor_sending_total, payee, "", year, period_no, month, "" );	
				displayEWT_for2307Received(modelReceived, rowHeaderReceived, modelReceived_total, payee, "", year, period, month, "" );	
			} else {
				if (cmbPeriod.getSelectedItem() != "ALL" && cmbPmtType.getSelectedItem().equals("Comm/Promo")) {
					displayEWT_for2307SendingCommission(modelFor_sending, rowHeaderFor_sending, modelFor_sending_total, payee, "", year, period_no, month, "" );	
					displayEWT_for2307Received(modelReceived, rowHeaderReceived, modelReceived_total, payee, "", year, period, month, "" );	
				}else {
					displayEWT_for2307Sending(modelFor_sending, rowHeaderFor_sending, modelFor_sending_total, payee, "", year, period_no, month, "" );	
					displayEWT_for2307Received(modelReceived, rowHeaderReceived, modelReceived_total, payee, "", year, period, month, "" );	
				}
		}
		} */
		
		{setButtonsStatus2();}
	}

	private void cancel(){
		refresh_fields(); 		
		enable_fields(false); 
		rbtnAllPayee.setEnabled(true);
		refresh_tablesMain(); 
		cmbYear.setEnabled(true);
		cmbPeriod.setEnabled(true);
		cmbMonth.setEnabled(true);
		btnPreview.setEnabled(false);
		cmbYear.setSelectedItem(getMonthYear());
		//lblRequestType.setEnabled(false);	
		//cmbPmtType.setEnabled(false);
		//cmbPmtType.setSelectedIndex(0);	
		
		acct_id = "";		
		entity_type_id = "";
		period = "";
		month_from = "";
		month_to = "";	
		day_from = "";
		day_to = "";
		year 	= "";
		month 	= "";
		period_no = "";
		paymnt_type = "0";
	}

	private void preview(String rplf_no, String comp_id){

		String criteria = "Form EWT (2307)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", comp_id);
		mapParameters.put("rplf_no", rplf_no);
		mapParameters.put("bir_logo", this.getClass().getClassLoader().getResourceAsStream("Images/BIR"));
		mapParameters.put("arrow", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2"));

		FncReport.generateReport("/Reports/rptForm2307_PV.jasper", reportTitle, company, mapParameters);	

	}
	
	private void preview_batch(String comp_id){
		
		int row 	= tblFor_sending.getSelectedRow();
		
		String co_address = "";
		String criteria = "Form EWT (2307)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		String pv_no = "";
		String secondPayor = "CARLOS CHENG / PRESIDENT / TIN 100-775-734-000"; //added by jed 8/29/18, change by JED 2018-12-10 from VICTOR H. MANARANG TO CARLOS CHENG dcrf no.879 for CDC//
		String firstPayor = "ANDRES CHUA / PRESIDENT / TIN 147-299-505-000";	//added by jed 8/29/18 for VDC
		String to_do = "batch";

		//Map<String, Object> mapParameters = new HashMap<String, Object>();
		ArrayList<String> list_pv_num = new ArrayList<String>();
		ArrayList<String> list_entity = new ArrayList<String>();
		ArrayList<String> iftrue = new ArrayList<String>();
		
		for (int i = 0; i < modelFor_sending.getRowCount(); i++) {
			
			BIR = (String) "BIR.jpg";
			Boolean SelectedItem = (Boolean) modelFor_sending.getValueAt(i, 0);
			String pv_no_ = (String) modelFor_sending.getValueAt(i, 1);
			String entity_id = (String) modelFor_sending.getValueAt(i, 11);
			
			if(SelectedItem) {
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				
				System.out.printf("\n%s\n", list_pv_num.toString().replaceAll("\\[|\\]", ""));
				mapParameters.put("co_id", comp_id);
				mapParameters.put("p_background", this.getClass().getClassLoader().getResourceAsStream("Images/Form2307new.jpg"));
				mapParameters.put("bir_logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + BIR));
				mapParameters.put("arrow1", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow2", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow3", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow4", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow5", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow6", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow7", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow8", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow9", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow10", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow11", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("arrow12", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
				mapParameters.put("year", cmbYear.getSelectedItem());
				mapParameters.put("month_from", month_from);
				mapParameters.put("month_to", month_to);
				mapParameters.put("day_from", day_from);
				mapParameters.put("day_to", day_to);
				mapParameters.put("payee", payee);
				mapParameters.put("pv_no", pv_no_);
				mapParameters.put("entity", entity_id);
				mapParameters.put("pv_num", pv_no);
				mapParameters.put("acct_id", acct_id);	
				mapParameters.put("entity_type_id", entity_type_id);
				mapParameters.put("rplf_no", pv_no_);
				mapParameters.put("pymnt_type", paymnt_type);
				mapParameters.put("jv_no", "");
				mapParameters.put("period_no", period_no);
				mapParameters.put("to_do", to_do);
				if(lookupCompany.getValue().equals("01")){
					
					co_address = "Unit 701 7th Floor Summit One Tower, 530 Shaw Boulevard, Highway Hills, Mandaluyong City 1550";
					
					mapParameters.put("payor", firstPayor);/*ADDED by JED 8/29/18 : PAYOR FOR VDC*/
					mapParameters.put("p_co_address", co_address); /*ADDED BY JED 2021-12-28 DCRF NO. 1900 : CHANGE COMPANY ADDRESS*/
					//System.out.printf("The value of payor is: %s\n", firstPayor);
				}
				else{
					
					co_address = "4th Floor Aster Business Center, Mandala Park, 312 Shaw Boulevard, Pleasant Hills, Mandaluyong City 1550";
					
					mapParameters.put("payor", secondPayor);/*ADDED by JED 8/29/18 : PAYOR FOR CDC*/
					mapParameters.put("p_co_address", co_address); /*ADDED BY JED 2021-12-28 DCRF NO. 1900 : CHANGE COMPANY ADDRESS*/
					//System.out.printf("The value of payor is: %s\n", secondPayor);
				}
				
				
				System.out.printf("pv_no_ :%s\n", pv_no_);
				System.out.printf("entity_id :%s\n", entity_id);

				if (cmbPeriod.getSelectedItem().equals("ALL") && cmbPmtType.getSelectedItem() != "Comm/Promo") {
					//FncReport.generateReport("/Reports/rptForm2307_PV_batch_new.jasper", reportTitle, company, mapParameters);
					//FncReport.generateReport("/Reports/rptForm2307_PV_batch_new_v2.jasper", String.format("%s-%s", reportTitle, i), company, mapParameters);
					
					FncReport.generateReport("/Reports/rptForm2307_PV_new.jasper", String.format("%s-%s", reportTitle, i), company, mapParameters);
					System.out.println("Form 2307");
				} else {
					if (cmbPeriod.getSelectedItem().equals("ALL") && cmbPmtType.getSelectedItem().equals("Comm/Promo")) {
						//FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission.jasper", "10%", mapParameters);
						FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission15.jasper", "15%", mapParameters);
						FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission_new.jasper", "10-15%", mapParameters);
						FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission.jasper", "2307", mapParameters);
					} else {
						if (cmbPeriod.getSelectedItem() != "ALL" && cmbPmtType.getSelectedItem().equals("Comm/Promo")) {
							//FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission.jasper", "10%", mapParameters);
							FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission15.jasper", "15%", mapParameters);
							FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission_new.jasper", "10-15%", mapParameters);
							FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission.jasper", "2307", mapParameters);
						}else {
							//FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly.jasper", reportTitle, company, mapParameters);
							FncReport.generateReport("/Reports/rptForm2307_PV_new_quarterly.jasper", reportTitle, company, mapParameters);
						}
				}
				}
				
			}	
			

//			if (SelectedItem) {
//				iftrue.add(modelFor_sending.getValueAt(i, 1).toString());
//				list_pv_num.add(pv_no_);
//				list_entity.add(entity_id);
//				
//			}
		}
		
//		if (iftrue.isEmpty()) {
//			JOptionPane.showMessageDialog(getContentPane(),"Please select first column for preview ","Preview", JOptionPane.OK_OPTION);
//			return;
//		}

	}

	/*private void preview_batch(String comp_id){
		
		int row 	= tblFor_sending.getSelectedRow();

		String criteria = "Form EWT (2307)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		String pv_no = "";

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		ArrayList<String> list_pv_num = new ArrayList<String>();
		ArrayList<String> list_entity = new ArrayList<String>();
		ArrayList<String> iftrue = new ArrayList<String>();
		
		for (int i = 0; i < modelFor_sending.getRowCount(); i++) {
			
			BIR = (String) "BIR.jpg";
			Boolean SelectedItem = (Boolean) modelFor_sending.getValueAt(i, 0);
			String pv_no_ = (String) modelFor_sending.getValueAt(i, 1);
			String entity_id = (String) modelFor_sending.getValueAt(i, 11);
			

			if (SelectedItem) {
				iftrue.add(modelFor_sending.getValueAt(i, 1).toString());
				list_pv_num.add(pv_no_);
				list_entity.add(entity_id);
				
			}
		}
		
		if (iftrue.isEmpty()) {
			JOptionPane.showMessageDialog(getContentPane(),"Please select first column for preview ","Preview", JOptionPane.OK_OPTION);
			return;
		}
		
		System.out.printf("\n%s\n", list_pv_num.toString().replaceAll("\\[|\\]", ""));
		mapParameters.put("co_id", comp_id);		
		mapParameters.put("bir_logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + BIR));
		mapParameters.put("arrow1", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow2", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow3", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow4", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow5", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow6", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow7", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow8", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow9", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow10", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow11", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("arrow12", this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		mapParameters.put("year", cmbYear.getSelectedItem());
		mapParameters.put("month_from", month_from);
		mapParameters.put("month_to", month_to);
		mapParameters.put("day_from", day_from);
		mapParameters.put("day_to", day_to);
		mapParameters.put("payee", payee);
		mapParameters.put("pv_no", list_pv_num);
		mapParameters.put("entity", list_entity);
		mapParameters.put("pv_num", pv_no);
		mapParameters.put("acct_id", acct_id);	
		mapParameters.put("entity_type_id", entity_type_id);
		mapParameters.put("rplf_no", "");
		mapParameters.put("pymnt_type", paymnt_type);
		mapParameters.put("jv_no", "");
		mapParameters.put("period_no", period_no);

		if (cmbPeriod.getSelectedItem().equals("ALL") && cmbPmtType.getSelectedItem() != "Comm/Promo") {
			FncReport.generateReport("/Reports/rptForm2307_PV_batch_new.jasper", reportTitle, company, mapParameters);	
		} else {
			if (cmbPeriod.getSelectedItem().equals("ALL") && cmbPmtType.getSelectedItem().equals("Comm/Promo")) {
				//FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission.jasper", "10%", mapParameters);
				FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission15.jasper", "15%", mapParameters);
				FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission_new.jasper", "10-15%", mapParameters);
				FncReport.generateReport("/Reports/rptForm2307_PV_batch_commission.jasper", "2307", mapParameters);
			} else {
				if (cmbPeriod.getSelectedItem() != "ALL" && cmbPmtType.getSelectedItem().equals("Comm/Promo")) {
					//FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission.jasper", "10%", mapParameters);
					FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission15.jasper", "15%", mapParameters);
					FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission_new.jasper", "10-15%", mapParameters);
					FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly_commission.jasper", "2307", mapParameters);
				}else {
					FncReport.generateReport("/Reports/rptForm2307_PV_batch_quarterly.jasper", reportTitle, company, mapParameters);
				}
		}
		}

	}*/

	public static void previewJV(String jv_no, String comp_id){

		String criteria = "Form EWT (2307)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", comp_id);
		mapParameters.put("jv_no", jv_no);

		FncReport.generateReport("/Reports/rptForm2307_JV.jasper", reportTitle, company, mapParameters);	

	}


	public class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {				

				if(tabCenter.getSelectedIndex()==0)
				{				
					int column 	= tblFor_sending.getSelectedColumn();
					if (column==1) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
					//if (column==6) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; mniopenJV.setEnabled(true) ;}
				}
				else if(tabCenter.getSelectedIndex()==1)
				{				
					int column 	= tblReceived.getSelectedColumn();
					if (column==0) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
					//if (column==4) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; mniopenJV.setEnabled(true) ;}
				}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				if(tabCenter.getSelectedIndex()==0)
				{				
					int column 	= tblFor_sending.getSelectedColumn();
					if (column==1) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
				}
				else if(tabCenter.getSelectedIndex()==1)
				{				
					int column 	= tblReceived.getSelectedColumn();
					if (column==0) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
					//if (column==4) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; mniopenJV.setEnabled(true) ;}
				}
			}
		}
	}

	private void sendToPayee(){

		if(tabCenter.getSelectedIndex()==0){		

			if(isThereAnItemtoSend()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "No item for sending.", "Warning", 
					JOptionPane.WARNING_MESSAGE);}
			else {			

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to send Form 2307 to payee?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					pgUpdate db = new pgUpdate();
					sendForm2307toPayee(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"Form 2307 sent to payee","Information",JOptionPane.INFORMATION_MESSAGE);
					refresh_tablesMain();
					displayEWT_for2307Sending(modelFor_sending, rowHeaderFor_sending, modelFor_sending_total, payee, "", year, period, month, "" );	
					displayEWT_for2307Received(modelReceived, rowHeaderReceived, modelReceived_total, payee, "", year, period, month, "" );	
				}		
			}
		}

		else {JOptionPane.showMessageDialog(getContentPane(),"Selected items cannot be remitted","ERROR",JOptionPane.ERROR_MESSAGE);}

	}


	//select, lookup and get statement		
	public static String getAvailerType(){//used

		return

		"select entity_type_id as \"Entity Type ID\" ,  " +
		"trim(entity_type_desc) as \"Entity Type Desc.\" , " +
		"wtax_id as \"Tax ID\" \n" + 
		"from mf_entity_type where status_id = 'A'" +
		"order by entity_type_id \r\n" ;
	}	

	public static String getEntityList(){//used

		String entity_type_id = lookupPayeeType.getText().trim();

		String sql = 
			"select \r\n" + 

			"distinct on (a.entity_id) " +
			"a.entity_id as \"Entity ID\"  ,\r\n" + 
			"upper(trim(a.entity_name))  as \"Entity Name\",\r\n" + 
			"upper(trim(a.entity_alias)) as \"Entity Alias\" \r\n" + 

			"from rf_entity a\r\n" + 				
			"left join rf_entity_assigned_type b on a.entity_id=b.entity_id\r\n" + 
			"left join em_employee c on a.entity_id = c.entity_id\r\n" + 

			"where a.status_id = 'A'\r\n" ; 
		if (entity_type_id.equals("")){sql = sql + "";} 
		else {sql = sql + "and b.entity_type_id = '"+entity_type_id+"' ";}
		sql = sql +
		"order by a.entity_id, a.entity_name \r\n" + 
		"   ";		
		return sql;

	}

	public static String getPV_no(){//used

		return 

		"select \r\n" + 
		"a.pv_no as \"PV No.\" , \r\n" + 
		"a.pv_date as \"PV Date\"      ,\r\n" + 
		"trim(b.entity_name) as \"Payee\"    ,\r\n" + 
		"c.status_desc  as \"status\"  ,\r\n" + 
		"a.date_posted  as \"Date Posted\"  \r\n" + 

		"from rf_pv_header a  \r\n" + 
		"left join rf_entity b on a.entity_id1 = b.entity_id\r\n" + 
		"left join mf_record_status c on a.status_id = c.status_id \n"  +
		"where a.co_id = '"+co_id+"' " +
		"order by a.pv_no desc" ;		


	}

	private static String getMonthYear() {//used		

		String x = "";

		DateFormat df    = new SimpleDateFormat("MM-dd-yyyy");	
		String year_str	 = df.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()));	
		String year_full=  year_str.substring(6);

		x = year_full;

		return x;

	}
	
	private static void totalEWT_forSending(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal wtax_amt 	= new BigDecimal(0.00);	
		BigDecimal gross_amt 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,7)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null,  null, null,  null, wtax_amt, gross_amt, null,  null});
	}	

	private static void totalEWT_forReceived(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal wtax_amt 	= new BigDecimal(0.00);	
		BigDecimal gross_amt 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] {"Total", null, null,  null,  wtax_amt, gross_amt, null,  null});
	}	


	private Boolean isThereAnItemtoSend(){

		boolean x = false;		
		int w = 0;

		//create CDF 
		while ( w < tblFor_sending.getRowCount()) {
			Boolean isTrue = false;
			if(tblFor_sending.getValueAt(w,0) instanceof String){isTrue = new Boolean((String)tblFor_sending.getValueAt(w,0));}
			if(tblFor_sending.getValueAt(w,0) instanceof Boolean){isTrue = (Boolean)tblFor_sending.getValueAt(w,0);}
			if(isTrue){	x = true; break; }

			w++;
		}
		return x;		
	}

	//save and insert	
	private void sendForm2307toPayee(pgUpdate db){					

		int x = 0;

		while ( x < tblFor_sending.getRowCount()) {
			Boolean isTrue = false;
			if(tblFor_sending.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)tblFor_sending.getValueAt(x,0));}
			if(tblFor_sending.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)tblFor_sending.getValueAt(x,0);}
			if(isTrue){					

				String pv_no	= tblFor_sending.getValueAt(x,1).toString();

				String sqlDetail = 
					"insert into rf_form2307_monitoring values ( " +
					"'"+co_id+"', \n" +
					"'"+pv_no+"', \n" +
					"'', \n" +
					"'"+UserInfo.EmployeeCode+"', \n"+ 
					"now(),  \n" +	
					"now()  \n" +	
					")" ;							

				System.out.printf("SQL #1:" + sqlDetail);
				db.executeUpdate(sqlDetail, false);		
			}

			x++;
		}
	}


	//right-click
	public void openDRF(){

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

			String rplf = "";
			if(tabCenter.getSelectedIndex()==0)
			{
				int column 	= tblFor_sending.getSelectedColumn();
				int row 	= tblFor_sending.getSelectedRow();
				rplf = tblFor_sending.getValueAt(row,column).toString().trim();
			}
			else if(tabCenter.getSelectedIndex()==1)
			{
				int column 	= tblReceived.getSelectedColumn();
				int row 	= tblReceived.getSelectedRow();
				rplf = tblReceived.getValueAt(row,column).toString().trim();
			}

			if (rplf.equals("")) {}
			else {			
				RequestForPayment.drf_no  = rplf;
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

	public void openJV(){

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

			String jvno = "";
			if(tabCenter.getSelectedIndex()==0)
			{
				int column 	= tblLiq_part.getSelectedColumn();
				int row 	= tblLiq_part.getSelectedRow();
				jvno = tblLiq_part.getValueAt(row,column).toString().trim();
			}
			else if(tabCenter.getSelectedIndex()==1)
			{
				int column 	= tblRemitted.getSelectedColumn();
				int row 	= tblRemitted.getSelectedRow();
				jvno = tblRemitted.getValueAt(row,column).toString().trim();
			}

			if (jvno.equals("")) {}
			else {

				JournalVoucher.refresh_fields();							

				JournalVoucher.jv_no = jvno;	
				JournalVoucher.lookupJV.setValue(jvno);

				JournalVoucher.setJV_header(jvno);
				JournalVoucher.displayJV_details(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account, JournalVoucher.modelJV_accounttotal, jvno );								

				JournalVoucher.mnidelete.setEnabled(false);
				JournalVoucher.mniaddrow.setEnabled(false);

			}			
		}		
	}

	public void openPV(){

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

			String rplf = "";
			if(tabCenter.getSelectedIndex()==0)
			{
				int column 	= tblLiq_part.getSelectedColumn();
				int row 	= tblLiq_part.getSelectedRow();
				rplf = tblLiq_part.getValueAt(row,column).toString().trim();
			}
			else if(tabCenter.getSelectedIndex()==1)
			{
				int column 	= tblRemitted.getSelectedColumn();
				int row 	= tblRemitted.getSelectedRow();
				rplf = tblRemitted.getValueAt(row,column).toString().trim();
			}

			if(!rplf.equals(""))			{

				PayableVoucher.refresh_fields();							

				PayableVoucher.pv_no = rplf;	
				PayableVoucher.lookupPV_no.setValue(rplf);

				PayableVoucher.setPV_header(rplf);
				PayableVoucher.displayPV_details(PayableVoucher.modelPV_account,PayableVoucher.rowHeaderPV_account, PayableVoucher.modelPV_accounttotal, rplf );	
				PayableVoucher.displayPV_subsidiaryledgers(PayableVoucher.modelPV_SL, PayableVoucher.rowHeaderPV_SL, PayableVoucher.modelPV_SL_total, rplf );	
				PayableVoucher.btnAddNew.setEnabled(false);
				PayableVoucher.btnRefresh.setEnabled(true);

				//set particulars							
				Integer w = 1;
				Integer line_count = PayableVoucher.sql_getDRF_line_count(rplf);
				String part_desc;
				String particulars = "";
				//COMMENTED BY LESTER 2023-05-25 FOR REPEAT DISPLAY OF REMARKS
//				while (w<=line_count){	
//					part_desc = PayableVoucher.getDRF_particulars(w, rplf).toString().trim();
//					particulars = ""+particulars+"  "+part_desc+"  \n" ;
//
//					w++; 
//				}
//				PayableVoucher.txtDRFRemark.setText(particulars);	

				if (PayableVoucher.getPV_status(rplf).equals("P")&&PayableVoucher.lookupPV_no.isEnabled()) 
				{								
					PayableVoucher.btnSave.setText("Post");  
					PayableVoucher.btnSave.setActionCommand("Post");		
					PayableVoucher.enable_buttons(false, false, false, true, true, true);
					PayableVoucher.mniInactivate.setEnabled(false);
				} 
				else if (PayableVoucher.getPV_status(rplf).equals("F")) 
				{								
					PayableVoucher.btnSave.setText("Post");  
					PayableVoucher.btnSave.setActionCommand("Post");
					PayableVoucher.enable_buttons(false, false, true, true, true, true);
					PayableVoucher.mniInactivate.setEnabled(false);
				}
				else if (PayableVoucher.getPV_status(rplf).equals("A")) 
				{								
					PayableVoucher.btnSave.setText("Tag");  
					PayableVoucher.btnSave.setActionCommand("Tag");
					PayableVoucher.enable_buttons(false, true, true, true, true, true);
					PayableVoucher.mniInactivate.setEnabled(true);
				}

				PayableVoucher.modelPV_account.setEditable(false);
				PayableVoucher.tblPV_account.setEditable(false);
				PayableVoucher.btnPreview.setEnabled(true);				
			}
		}
	}




}