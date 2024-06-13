package TaxesAndPermits;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelEWT_forRemittance;
import tablemodel.modelEWT_remitted;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Accounting.GeneralLedger.JournalVoucher;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncExport;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
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

public class EWT_Remittance extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "EWT Remittance Monitoring";
	static Dimension SIZE = new Dimension(1000, 600);
	
	static Border lineBorderRed = BorderFactory.createLineBorder(Color.RED);
	
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
	private JPanel pnlForRemittance;
	private JPanel pnlRemitted;

	private JLabel lblCompany;
	private static JLabel lblPayeeType;
	private static JLabel lblPayee;
	public static JLabel lblYear;
	public static JLabel lblPeriod;
	public static JLabel lblMonth;
	public static JLabel lblPV_no;
	private JLabel lblX;	
	private JLabel lblRequestType;

	public static _JLookup lookupCompany;
	public static _JLookup lookupPayeeType;
	static _JLookup lookupDiv;
	static _JLookup lookupDep;
	public static _JLookup lookupPayee;
	public static _JLookup lookupPV_no;

	private _JScrollPaneMain scrollLiq_part;
	private _JScrollPaneMain scrollRemitted;

	private static _JScrollPaneTotal scrollLiq_part_total;

	public static modelEWT_forRemittance modelLiq_part;
	public static modelEWT_forRemittance modelLiq_part_total;
	public static modelEWT_remitted modelRemitted;
	public static modelEWT_remitted modelRemitted_total;

	private static _JTableTotal tblLiq_part_total;	
	private static _JTableTotal tblRemitted_total;

	private static _JTableMain tblLiq_part;	
	private static _JTableMain tblRemitted;

	public static JList rowHeaderLiq_part;
	public static JList rowHeaderRemitted;

	public static _JTabbedPane tabCenter;	

	private static _JScrollPaneTotal scrollRemitted_total;

	public static _JTagLabel tagCompany;	
	private _JTagLabel tagBlank;
	public static _JTagLabel tagPayee;
	private _JTagLabel tagPayeeType;
	public static _JTagLabel tagPV_no;

	public static JButton btnCancel;	
	public static JButton btnGenerate;
	private static JButton btnRemit;
	private static JButton btnPreview;
	private static JButton btnExport;
	
	public static JComboBox cmbYear;
	public static JComboBox cmbPeriod;
	public static JComboBox cmbMonth;
	private JComboBox cmbPmtType;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public static JRadioButton rbtnAllPayee;

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
	static String payee_name = "";	
	String overdue_deduction;	

	public static String period = "";
	public static String period1 = "";
	public static String period2 = "";
	public static String period3 = "";
	public static String month_from = "";
	public static String month_to = "";	
	public static String day_from = "";
	public static String day_to = "";
	public static String year 	= "";
	public static String month 	= "";
	public static String PV_num 	= "";
	public static String pmt_req_type_id	= "";
	public static String acct_id = "";
	public static String entity_type_id = "";
	public static String entity_type_name = "";

	private JCheckBox chkVersion2;
	
	public EWT_Remittance() {
		super(title, true, true, true, true);
		initGUI();
	}

	public EWT_Remittance(String title) {
		super(title);

	}

	public EWT_Remittance(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

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
		pnlNorth.setPreferredSize(new java.awt.Dimension(923, 210));
		{
			pnlNorth_a = new JPanel();
			pnlNorth.add(pnlNorth_a, BorderLayout.CENTER);
			pnlNorth_a.setLayout(new BorderLayout(5, 5));	
			pnlNorth_a.setPreferredSize(new java.awt.Dimension(923, 134));

			pnlComp = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlNorth_a.add(pnlComp, BorderLayout.NORTH);
			pnlComp.setBorder(JTBorderFactory.createTitleBorder("Company Detail", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				{
					pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(202, 30));
					{
						lblCompany = new JLabel("Company", JLabel.LEADING);
						pnlComp_a1.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new Dimension(87, 25));
						lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}
					{
						lookupCompany = new _JLookup(null, "Company", 0, 2);
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
				}
			}
			pnlEWT = new JPanel(new BorderLayout(5, 5));
			pnlNorth_a.add(pnlEWT, BorderLayout.CENTER);				
			pnlEWT.setPreferredSize(new java.awt.Dimension(921, 120));
			pnlEWT.setBorder(JTBorderFactory.createTitleBorder("Contract Detail", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			pnlEWT.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			{	
				pnlEWTDtl_1 = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlEWT.add(pnlEWTDtl_1, BorderLayout.WEST);	
				pnlEWTDtl_1.setPreferredSize(new Dimension(230, 90));
				//pnlEWTDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));
				{
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
				}
				pnlEWTDtl_1b = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlEWTDtl_1.add(pnlEWTDtl_1b, BorderLayout.CENTER);	
				pnlEWTDtl_1b.setPreferredSize(new java.awt.Dimension(130, 110));
				pnlEWTDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				{
					rbtnAllPayee = new JRadioButton();
					pnlEWTDtl_1b.add(rbtnAllPayee);
					rbtnAllPayee.setText("All Payees/PV");
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
								lblPV_no.setEnabled(true);	
								lookupPV_no.setEnabled(true);	
								tagPV_no.setEnabled(true);	
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
								lblRequestType.setEnabled(false);	
								cmbPmtType.setEnabled(false);
								cmbPmtType.setSelectedIndex(0);	

							}				
						}});
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
								entity_type_name = data[1].toString();
								tagPayeeType.setTag(entity_type_name);
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
					lookupPayee.setEnabled(false);	
					lookupPayee.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPayee.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){			
								payee = (String) data[0];
								payee_name = data[1].toString();
								tagPayee.setTag(payee_name);								
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
					String status[] = {"All","Commission","Contractors","MAF/SOE","GAE/SME"};					
					cmbPmtType = new JComboBox(status);
					pnlEWTDtl_1b.add(cmbPmtType);
					cmbPmtType.setSelectedItem(null);
					cmbPmtType.setBounds(537, 15, 160, 21);	
					cmbPmtType.setEnabled(false);
					cmbPmtType.setSelectedIndex(0);	
					((JLabel)cmbPmtType.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
					cmbPmtType.setPreferredSize(new java.awt.Dimension(89, 22));
					cmbPmtType.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
							if (cmbPmtType.getSelectedIndex()==0)
							{
								acct_id = "";
							}
							else if (cmbPmtType.getSelectedIndex()==1)
							{
								acct_id = "01-02-09%";
							}
							else if (cmbPmtType.getSelectedIndex()==2)
							{
								acct_id = "01-03%";
							}
							else if (cmbPmtType.getSelectedIndex()==3)
							{
								acct_id = "08-03%";
							}
							else if (cmbPmtType.getSelectedIndex()==4)
							{
								acct_id = "08-01%";
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
					String status[] = {"2014","2015","2016","2017","2018","2019", "2020", "2021", "2022", "2023", "2024"};		
					cmbYear = new JComboBox(status);
					pnlEWTDtl_2.add(cmbYear);
					cmbYear.setSelectedItem(null);
					cmbYear.setBounds(537, 15, 160, 21);	
					cmbYear.setSelectedItem(getMonthYear());
					cmbYear.setEnabled(false);
					//cmbYear.setSelectedIndex(2);	
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

			tabCenter = new _JTabbedPane();
			pnlEWT.add(tabCenter, BorderLayout.CENTER);
			tabCenter.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {setButtonsStatus2();}
			});
			tabCenter.addPropertyChangeListener( new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {setButtonsStatus2();}
			});		

			{		

				pnlForRemittance = new JPanel(new BorderLayout());
				tabCenter.addTab("For Remittance", null, pnlForRemittance, null);
				pnlForRemittance.setPreferredSize(new java.awt.Dimension(1183, 365));		

				{
					scrollLiq_part = new _JScrollPaneMain();
					pnlForRemittance.add(scrollLiq_part, BorderLayout.CENTER);
					{
						modelLiq_part = new modelEWT_forRemittance();

						tblLiq_part = new _JTableMain(modelLiq_part);
						scrollLiq_part.setViewportView(tblLiq_part);
						tblLiq_part.addMouseListener(new PopupTriggerListener_panel());
						tblLiq_part.getColumnModel().getColumn(0).setPreferredWidth(50);
						tblLiq_part.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblLiq_part.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblLiq_part.getColumnModel().getColumn(3).setPreferredWidth(250);

						tblLiq_part.addMouseListener(this);	
						tblLiq_part.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblLiq_part.rowAtPoint(e.getPoint()) == -1){}
								else{}
								setButtonsStatus2();
							}
							public void mouseReleased(MouseEvent e) {
								if(tblLiq_part.rowAtPoint(e.getPoint()) == -1){}
								else{}
							}
						});
					}
					{
						rowHeaderLiq_part = tblLiq_part.getRowHeader();
						scrollLiq_part.setRowHeaderView(rowHeaderLiq_part);
						scrollLiq_part.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollLiq_part_total = new _JScrollPaneTotal(scrollLiq_part);
					pnlForRemittance.add(scrollLiq_part_total, BorderLayout.SOUTH);
					{
						modelLiq_part_total = new modelEWT_forRemittance();
						modelLiq_part_total.addRow(new Object[] { "Total", null, null, null, null, null, null, null, new BigDecimal(0.00),new BigDecimal(0.00)});

						tblLiq_part_total = new _JTableTotal(modelLiq_part_total, tblLiq_part);
						tblLiq_part_total.setFont(dialog11Bold);
						scrollLiq_part_total.setViewportView(tblLiq_part_total);
						((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
					}
				}

			}			
			{		

				pnlRemitted = new JPanel(new BorderLayout());
				tabCenter.addTab(" Remitted to BIR ", null, pnlRemitted, null);
				pnlRemitted.setPreferredSize(new java.awt.Dimension(1183, 365));		

				{
					scrollRemitted = new _JScrollPaneMain();
					pnlRemitted.add(scrollRemitted, BorderLayout.CENTER);
					{
						modelRemitted = new modelEWT_remitted();

						tblRemitted = new _JTableMain(modelRemitted);
						scrollRemitted.setViewportView(tblRemitted);
						tblRemitted.addMouseListener(new PopupTriggerListener_panel());
						tblRemitted.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblRemitted.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblRemitted.getColumnModel().getColumn(2).setPreferredWidth(300);

						tblRemitted.addMouseListener(this);	
						tblRemitted.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblRemitted.rowAtPoint(e.getPoint()) == -1){
									tblRemitted_total.clearSelection();
								}else{
									tblRemitted.setCellSelectionEnabled(true);
									setButtonsStatus2();
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblRemitted.rowAtPoint(e.getPoint()) == -1){
									tblRemitted_total.clearSelection();
								}else{
									tblRemitted.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderRemitted = tblRemitted.getRowHeader();
						scrollRemitted.setRowHeaderView(rowHeaderRemitted);
						scrollRemitted.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollRemitted_total = new _JScrollPaneTotal(scrollRemitted);
					pnlRemitted.add(scrollRemitted_total, BorderLayout.SOUTH);
					{
						modelRemitted_total = new modelEWT_remitted();
						modelRemitted_total.addRow(new Object[] { "Total", null, null,  null,  null,  null, new BigDecimal(0.00),new BigDecimal(0.00)});

						tblRemitted_total = new _JTableTotal(modelRemitted_total, tblRemitted);
						tblRemitted_total.setFont(dialog11Bold);
						scrollRemitted_total.setViewportView(tblRemitted_total);
						((_JTableTotal) tblRemitted_total).setTotalLabel(0);
					}
				}
			}				

		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setPreferredSize(new java.awt.Dimension(923, 35));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 4, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					chkVersion2 = new JCheckBox("Print Version 2");
					pnlSouthCenterb.add(chkVersion2);
					chkVersion2.setSelected(true);
					chkVersion2.setEnabled(true);
				}
				{
					btnRemit= new JButton("Remit");
					pnlSouthCenterb.add(btnRemit);
					btnRemit.setActionCommand("Remit");
					btnRemit.addActionListener(this);
					btnRemit.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenterb.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
				{
					btnExport = new JButton("Export");
					//pnlSouthCenterb.add(btnExport);
					btnExport.setActionCommand("Export");
					btnExport.addActionListener(this);
					btnExport.setEnabled(false);
				}
			}			
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	public static void displayEWT_forRemittance_all(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, 
			String payee_id, String rplf_no, String year, String period, String month, String jv_no) {

		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 
		
		/*EDITED BY JED 2021-12-06 : MODIFY FUNCTION OF GENERATING EWT)*/
//		String strSQL = "select * \n" + 
//				"from view_EWT_forRemittance_all ('"+co_id+"', '"+payee_id+"','"+rplf_no+"','"+PV_num+"','"+jv_no+"', " +
//				"'"+year+"','"+period+"','"+period1+"','"+period2+"','"+period3+"','"+month+"','"+acct_id+"','"+entity_type_id+"', '"+UserInfo.EmployeeCode+"')" ;

		String strSQL = "select * \n" + 
				"from view_EWT_forRemittance_all_v2 ('"+co_id+"', '"+payee_id+"','"+rplf_no+"','"+PV_num+"','"+jv_no+"', " +
				"'"+year+"','"+period+"','"+period1+"','"+period2+"','"+period3+"','"+month+"','"+acct_id+"','"+entity_type_id+"', '"+UserInfo.EmployeeCode+"')" ;

		System.out.println("SQL #1: "+strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalEWT_forRemittance(modelMain, modelTotal);				
		} else {

			JOptionPane.showMessageDialog(null,"No EWT for Remittance for the selected parameters.","Information",JOptionPane.INFORMATION_MESSAGE);

			modelLiq_part_total = new modelEWT_forRemittance();
			modelLiq_part_total.addRow(new Object[] { "Total", null, null, null, null, null, null, null, new BigDecimal(0.00),new BigDecimal(0.00)});

			tblLiq_part_total = new _JTableTotal(modelLiq_part_total, tblLiq_part);
			tblLiq_part_total.setFont(dialog11Bold);
			scrollLiq_part_total.setViewportView(tblLiq_part_total);
			((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
		}

		tblLiq_part.packAll();
		tblLiq_part.getColumnModel().getColumn(0).setPreferredWidth(50);
		modelLiq_part.setEditable(true);
	}	

	public static void displayEWT_remitted(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, 
			String payee_id, String rplf_no, String year, String period, String month, String jv_no) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"--displayEWT_remitted \n" +
			"select * from ( \n\n" +
			"select \r\n" + 
			"\r\n" + 
			"a.entity_id,\r\n" + 
			"trim(bb.tin_no),\r\n" + 
			"upper(trim(b.entity_name)) as payee_name,\r\n" + 
			"a.rplf_no,\r\n" + 
			"aa.jv_no,\r\n" + 
			//"(case when f.rplf_date is null then g.jv_date else f.rplf_date end) as tran_date,\r\n" + 
			"d.pv_date as tran_date,\r\n" + 
			
			/*	Modified by Mann2: Modified Date: December 7, 2016; JVs were added so some parameters should be changed.	*/
			/*	"a.wtax_amt, \r\n" +	*/ 
			/*	"a.amount - a.wtax_amt,\r\n" +	*/
			"(case when coalesce(g.jv_no, '') <> '' then (select sum(tran_amt) from rf_jv_detail where acct_id = '03-01-06-002' and jv_no = aa.jv_no and status_id = 'A') else a.wtax_amt end) as wtax_amt, \n" +
			"(case when coalesce(aa.jv_no, '') <> '' then \n" +
			"(case when (select sum(tran_amt) from rf_jv_detail where acct_id = '01-99-03-000' and jv_no = aa.jv_no and status_id = 'A') != 0 then \n" +
			"((select sum(tran_amt) from rf_jv_detail where jv_no = aa.jv_no and status_id = 'A' and bal_side = 'C' and acct_id != '01-99-03-000' and acct_id != '03-01-06-002') - \n" +
			"(select sum(tran_amt) from rf_jv_detail where acct_id = '01-99-03-000' and jv_no = aa.jv_no and status_id = 'A')) else \n" +
			"(select sum(tran_amt) from rf_jv_detail where jv_no = aa.jv_no and status_id = 'A' and bal_side = 'C') end) else a.exp_amt end) \n" +
			"- \n" +
			"(case when coalesce(aa.jv_no, '') <> '' then (select sum(tran_amt) from rf_jv_detail where acct_id = '03-01-06-002' and jv_no = aa.jv_no and status_id = 'A') else a.wtax_amt end), \n" + 

			"aa.date_remitted,\r\n" + 
			"upper(trim(cc.first_name))||' '||upper(trim(cc.last_name)),\r\n" + 
			"aa.rem_rplf_no,\r\n" + 
			"(case when e.date_paid is not null then 'Released' else\r\n" + 
			"  (case when d.status_id = 'P' then 'Processed' else \r\n" + 
			"     (case when d.status_id = 'I' then 'Inactive' else 'Active'  end) end) end) as check_status\r\n" + 
			"\r\n" + 
			"from ( select * from rf_request_detail where status_id = 'A') a\r\n" + 
			"left join rf_request_header f on a.rplf_no = f.rplf_no and a.co_id = f.co_id\r\n" + 
			"left join ( select distinct on (rplf_no) * from rf_ewt_remittance where status_id = 'A' ) aa on a.rplf_no = aa.rplf_no \n" +
			//"left join rf_entity b on a.entity_id = b.entity_id \r\n" +
			"left join rf_entity b on \n"  +
			"	(case when trim(f.rplf_type_id) = '04' then (a.entity_id) else trim(f.entity_id1) end) = b.entity_id \r\n" +
			"left join rf_entity_id_no bb on  a.entity_id = bb.entity_id \n" +
			"left join em_employee c on a.created_by = c.emp_code\r\n" + 
			"left join rf_entity cc on c.entity_id = cc.entity_id \r\n" + 
			"left join (select * from rf_pv_header where status_id != 'I') d on a.rplf_no = d.rplf_no and a.co_id = d.co_id and a.entity_id = d.entity_id1   \r\n" + 
			"left join (select * from rf_cv_header where status_id != 'I') e on d.cv_no = e.cv_no and d.co_id = e.co_id\r\n" + 			
			"left join rf_jv_header g on aa.jv_no = g.jv_no and aa.co_id = g.co_id \r\n" +
			"left join (select y.rplf_no, z.entity_id, x.* " +
			"	from rf_jv_header x " +
			"	left join (select * from rf_liq_header where status_id != 'I') y on x.jv_no = y.jv_no " +
			"	inner join (select * from rf_liq_detail where status_id != 'I') z on y.liq_no = z.liq_no " +
			"	where x.status_id != 'I') h \r\n" + 
			"on f.rplf_no = h.rplf_no and f.co_id = h.co_id and a.entity_id = h.entity_id\r\n" +
			"where (a.wtax_amt > 0 or (case when coalesce(aa.jv_no, '') <> '' then (select sum(tran_amt) from rf_jv_detail where acct_id = '03-01-06-002' and jv_no = aa.jv_no and status_id = 'A') else a.wtax_amt end) > 0) \r\n" ;

		if (payee_id.equals("")) {} 
		else {sql = sql + "and a.entity_id = '"+payee_id.trim()+"' \n";} 

		if (rplf_no.equals("")) {} 
		else {sql = sql + "and a.rplf_no = '"+rplf_no+"' \n";} 

		if (jv_no.equals("")) {} 
		else {sql = sql + "and a.jv_no is not null \n";} 

		if (year.equals("")) {} 
		else {sql = sql + "and trim(to_char(aa.date_remitted, 'yyyy')) = '"+year+"' \n";} 

		if (period.equals("All")) {} 
		else {sql = sql + "and substr(trim(to_char(aa.date_remitted, 'MM-dd-yyyy')),0,3) in ('"+period1+"', '"+period2+"', '"+period3+"') \n";} 

		if (month.equals("")) {} 
		else {sql = sql + "and substr(trim(to_char(aa.date_remitted, 'MM-dd-yyyy')),0,3) = '"+month+"' \n\n";} 

		if (PV_num.equals("")) {} 
		else {sql = sql + "and a.rplf_no = '"+PV_num+"' \n\n";} 
		
		if (acct_id.equals("")) {} 
		else {sql = sql + "and a.acct_id like '"+acct_id+"' \n\n";} 
		
		if (entity_type_id.equals("")) {} 
		else {sql = sql + "and b.entity_type_id = '"+entity_type_id+"' \n\n";}

		/*sql = sql +			


		"UNION ALL \n\n" + 			

		"select \r\n" + 
		"\r\n" + 
		"a.entity_id,\r\n" + 
		"trim(bb.tin_no),\r\n" + 
		"upper(trim(b.entity_name)) as payee_name,\r\n" + 
		"a.rplf_no,\r\n" + 
		"aa.jv_no,\r\n" + 
		"(case when f.rplf_date is null then g.jv_date else f.rplf_date end) as tran_date,\r\n" + 
		"a.wtax_amt, \r\n" + 
		"a.amount - a.wtax_amt,\r\n" + 
		"aa.date_remitted,\r\n" + 
		"upper(trim(cc.first_name))||' '||upper(trim(cc.last_name)),\r\n" + 
		"aa.rem_rplf_no,\r\n" + 
		"(case when e.date_paid is not null then 'Released' else\r\n" + 
		"  (case when d.status_id = 'P' then 'Processed' else \r\n" + 
		"     (case when d.status_id = 'I' then 'Inactive' else 'Active'  end) end) end) as check_status\r\n" + 
		"\r\n" + 
		"from (select * from rf_request_detail where status_id = 'A') a\r\n" + 
		"left join rf_request_header f on a.rplf_no = f.rplf_no and a.co_id = f.co_id\r\n" + 
		"left join ( select distinct on (rplf_no) * from rf_ewt_remittance ) aa on a.rplf_no = aa.rplf_no \n" +
		//"left join rf_entity b on a.entity_id = b.entity_id \r\n" +
		"left join rf_entity b on \n"  +
		"	(case when trim(f.rplf_type_id) = '04' then (a.entity_id) else trim(f.entity_id1) end) = b.entity_id \r\n" +
		"left join rf_entity_id_no bb on  a.entity_id = bb.entity_id \n" +
		"left join em_employee c on a.created_by = c.emp_code\r\n" + 
		"left join rf_entity cc on c.entity_id = cc.entity_id \r\n" + 
		"left join rf_pv_header d on a.rplf_no = d.rplf_no and a.co_id = d.co_id  and a.entity_id = d.entity_id1 \r\n" + 
		"left join rf_cv_header e on d.cv_no = e.cv_no and d.co_id = e.co_id\r\n" + 		
		"left join rf_jv_header g on aa.jv_no = g.jv_no and aa.co_id = g.co_id \r\n" +
		"where a.wtax_amt > 0 \r\n" ;

		if (payee_id.equals("")) {} 
		else {sql = sql + "and a.entity_id = '"+payee_id.trim()+"' \n";} 

		if (rplf_no.equals("")) {} 
		else {sql = sql + "and a.rplf_no is not null \n";} 

		if (jv_no.equals("")) {} 
		else {sql = sql + "and a.jv_no = '"+jv_no+"' \n";} 

		if (year.equals("")) {} 
		else {sql = sql + "and trim(to_char(aa.date_remitted, 'yyyy')) = '"+year+"' \n";} 

		if (period.equals("")) {} 
		else {sql = sql + "and substr(trim(to_char(aa.date_remitted, 'MM-dd-yyyy')),0,3) in ( "+period+" ) \n";} 

		if (month.equals("")) {} 
		else {sql = sql + "and substr(trim(to_char(aa.date_remitted, 'MM-dd-yyyy')),0,3) = '"+month+"' \n\n";} 

		if (PV_num.equals("")) {} 
		else {sql = sql + "and a.rplf_no = '"+PV_num+"' \n\n";} 
		
		if (acct_id.equals("")) {} 
		else {sql = sql + "and a.acct_id like '"+acct_id+"' \n\n";} 
		
		if (entity_type_id.equals("")) {} 
		else {sql = sql + "and b.entity_type_id = '"+entity_type_id+"' \n\n";}
*/
		sql = sql +		

		") a " +
		"\r\n" + 
		"where a.date_remitted is not null \n" +
		"order by a.payee_name \n\n" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalEWT_forRemitted(modelMain, modelTotal);				
		}		

		else {

			JOptionPane.showMessageDialog(null,"No Remitted EWT for the selected parameters.","Information",JOptionPane.INFORMATION_MESSAGE);

			modelRemitted_total = new modelEWT_remitted();
			modelRemitted_total.addRow(new Object[] { "Total", null, null,  null,  null,  null, new BigDecimal(0.00),new BigDecimal(0.00)});

			tblRemitted_total = new _JTableTotal(modelRemitted_total, tblRemitted);
			tblRemitted_total.setFont(dialog11Bold);
			scrollRemitted_total.setViewportView(tblRemitted_total);
			((_JTableTotal) tblRemitted_total).setTotalLabel(0);
			//JOptionPane.showMessageDialog(null,"No EWT Remitted.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		tblRemitted.packAll();		
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

		//reset table 1
		FncTables.clearTable(modelLiq_part);
		FncTables.clearTable(modelLiq_part_total);			
		rowHeaderLiq_part = tblLiq_part.getRowHeader();
		scrollLiq_part.setRowHeaderView(rowHeaderLiq_part);	
		modelLiq_part_total.addRow(new Object[] { "Total", null, null, null, null, null, null, null, new BigDecimal(0.00),new BigDecimal(0.00)});	
		tblLiq_part_total = new _JTableTotal(modelLiq_part_total, tblLiq_part);
		tblLiq_part_total.setFont(dialog11Bold);
		scrollLiq_part_total.setViewportView(tblLiq_part_total);
		((_JTableTotal) tblLiq_part_total).setTotalLabel(0);

		//reset table 2
		FncTables.clearTable(modelRemitted);
		FncTables.clearTable(modelRemitted_total);			
		rowHeaderRemitted = tblRemitted.getRowHeader();
		scrollRemitted.setRowHeaderView(rowHeaderRemitted);	
		modelRemitted_total.addRow(new Object[] { "Total", null, null,  null,  null,  null, new BigDecimal(0.00),new BigDecimal(0.00)});
		tblRemitted_total = new _JTableTotal(modelRemitted_total, tblRemitted);
		tblRemitted_total.setFont(dialog11Bold);
		scrollRemitted_total.setViewportView(tblRemitted_total);
		((_JTableTotal) tblRemitted_total).setTotalLabel(0);
	}

	public static void setButtonsStatus(Boolean a, Boolean b, Boolean c, Boolean d){
		btnRemit.setEnabled(a);
		btnPreview.setEnabled(b);
		btnCancel.setEnabled(c);
		btnExport.setEnabled(d);
	}

	public static void setButtonsStatus2(){
		if (tabCenter.getSelectedIndex()==0) {
			setButtonsStatus(true, true, true, true);
		} else if (tabCenter.getSelectedIndex()==1) {
			setButtonsStatus(false, false, true, false);
		}
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

		lookupPayeeType.setLookupSQL(getAvailerType());
		lookupPayee.setLookupSQL(getEntityList());
		lookupPV_no.setLookupSQL(getPV_no());

		rbtnAllPayee.setEnabled(true);

		lookupCompany.setValue(co_id);
		setButtonsStatus(false, false, false, false);
	}

	public void actionPerformed(ActionEvent e) {
		String rplfno = lookupPV_no.getText().trim();
		
		if (cmbPeriod.getSelectedItem().equals("ALL")) {
			period = "All";
		}
		
		if (cmbMonth.getSelectedItem().equals("ALL")) {
			month = "All";
		}
		
		if (payee.equals("")) {
			payee_name = "All";
		}
		
		if (rplfno.equals("")) {
			rplfno = "All";
		}
		
		if (entity_type_id.equals("")) {
			entity_type_name = "All";
		}	
		
		if (year.equals("")) {
			year = "All";
		}
		
		if (e.getActionCommand().equals("GenerateEWT")) {
			generateAll();
		} else if (e.getActionCommand().equals("Cancel")) {
			cancel();
		} else if (e.getActionCommand().equals("Remit")) {
			remit();
		} else if (e.getActionCommand().equals("Preview")) {
			String criteria = "EWT - For Remittance";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("co_id", co_id);
			mapParameters.put("year", year);
			mapParameters.put("period", cmbPeriod.getSelectedIndex());
			mapParameters.put("month", month);
			mapParameters.put("payee_id", payee);	
			mapParameters.put("payee_name", payee_name);	
			mapParameters.put("rplf_no", rplfno);	
			mapParameters.put("entity_type_id", entity_type_id);	
			mapParameters.put("entity_type_name", entity_type_name);	
			mapParameters.put("acct_id", acct_id);	
			mapParameters.put("pmt_type_name", cmbPmtType.getSelectedItem());	
			mapParameters.put("prepared_name", UserInfo.FullName);
			mapParameters.put("emp_code", UserInfo.EmployeeCode);

			System.out.println("");

			System.out.println("");
			System.out.println("co_id: " + co_id);
			System.out.println("year: " + year);
			System.out.println("period_1: " + period1);
			System.out.println("period_2: " + period2);
			System.out.println("period_3: " + period3);
			System.out.println("month: " + month);
			System.out.println("acct_id: " + acct_id);
			System.out.println("entity_type_id: " + entity_type_id);
			
			System.out.println("select * " +
					"from view_EWT_forRemittance_all_v2 " +
					"('"+co_id+"', '', '', '', '', '"+year+"', '', '"+period1+"', '"+period2+"', " +
					"'"+period3+"', '"+month+"', '"+acct_id+"', '"+entity_type_id+"') a");
			
			mapParameters.put("period_1", period1);
			mapParameters.put("period_2", period2);
			mapParameters.put("period_3", period3);
			
			if(chkVersion2.isSelected()) {
				/*commented by jed 2021-12-06 : modify the report*/
				//FncReport.generateReport("/Reports/rptEWT_forRemittanceV2.jasper", reportTitle, "", mapParameters);
				FncReport.generateReport("/Reports/rptEWT_forRemittanceV3.jasper", reportTitle, "", mapParameters);
			} else {
				FncReport.generateReport("/Reports/rptEWT_forRemittance.jasper", reportTitle, "", mapParameters);
			}
			FncReport.generateReport("/Reports/rptEWT_forRemittanceV2_summary.jasper", reportTitle + " Summary", "", mapParameters);
		} else if (e.getActionCommand().equals("Export")) {
			FncGlobal.startProgress("Generating spreadsheets...");
	    	new Thread(new Runnable() {
				public void run() {
					ExportDetailed();
					ExportSummary();
				}
			}).start();
	    	FncGlobal.stopProgress();
			
			/*	Modified by Mann2x; Date Modified: February 13, 2016; Modified in accordance with the refinement of the primary code as to update the columns and filters;
			String col_names [] = {"TIN", "Name", "PV No.", "CV No.", "Return Period", "BIR Code", "Nature Of Income Payment", "Tax Base Amount", "Tax Rate", "Tax Withheld", "Debit Amount"};
			*/
			
			
			/*
			String strSQL = 
				
				"select " +
					"a.tin_no, a.client, a.rplf_no, a.cv_no, \n" +
					"concat_ws('/', LPAD(DATE_PART('Month', a.rplf_date)::CHAR(2), 2, '0'), " +
					"	RIGHT(DATE_PART('YEAR', a.rplf_date)::CHAR(4), 2)) as RetPer, \n" +
					"a.wtax_bir_code, a.income_payment_desc, a.Net, a.Rate, a.wTax, Debit from " +
					
					"( \n" +
					"select (case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(bb.entity_id2) end) as entity_id, " +
					"cc.tin_no, upper(trim(c.entity_name)) as client, a.rplf_no,\n" + 
					"d.cv_no, j.jv_no as jv_no, b.rplf_date, a.wtax_amt, (coalesce(a.exp_amt,0) - coalesce(a.wtax_amt,0)) net_exp_amt, e.date_paid, \n" +
					"(case when a.sub_projectid is not null or a.sub_projectid = '' then true else \n" +
					"(case when f.lts_date is null then false else true end) end) as with_lts, \n" +
					"g.wtax_bir_code, g.income_payment_desc, \n" +					
					"((case when coalesce(j.jv_no, '') <> '' then \n" +
					"	(case when (select sum(tran_amt) from rf_jv_detail where acct_id = '01-99-03-000' " +
					"	and jv_no = j.jv_no and status_id = 'A') != 0 then \n" +
					"	((select sum(tran_amt) from rf_jv_detail where jv_no = j.jv_no and status_id = 'A' and bal_side = 'C' " +
					"	and acct_id != '01-99-03-000' and acct_id != '03-01-06-002') - \n" +
					"	(select sum(tran_amt) from rf_jv_detail where acct_id = '01-99-03-000' and jv_no = j.jv_no and status_id = 'A')) else \n" +
					"	(select sum(tran_amt) from rf_jv_detail where jv_no = j.jv_no and status_id = 'A' and bal_side = 'C') end) else a.exp_amt end) \n" +
					"	- \n" +
					"	(case when coalesce(j.jv_no, '') <> '' then (select sum(tran_amt) from rf_jv_detail " +
					"	where acct_id = '03-01-06-002' and jv_no = j.jv_no and status_id = 'A') else a.wtax_amt end)) as Net, \n" +					
					"ROUND(g.wtax_rate::DECIMAL, 2) as Rate, \n" +					
					"(case when coalesce(j.jv_no, '') <> '' then (select sum(tran_amt) from rf_jv_detail " +
					"	where acct_id = '03-01-06-002' and jv_no = j.jv_no and status_id = 'A') else a.wtax_amt end) as wtax, \n" +					
					"concat_ws('-',i.proj_alias, h.sub_proj_alias) as Project, \n" +					
					"(SELECT SUM(x.tran_amt) FROM rf_pv_detail x WHERE x.pv_no = d.pv_no AND status_id = 'A' AND bal_side = 'D') as Debit \n" +
					
					"from (select * from rf_request_detail where status_id != 'I') a \n" +
					"left join (select * from rf_request_header where status_id != 'I') b on a.rplf_no =b.rplf_no and a.co_id = b.co_id \n" +
					"left join (select * from rf_pv_header where status_id != 'I') bb on a.rplf_no =bb.rplf_no and a.co_id = bb.co_id \n" +
					"left join rf_entity c on (case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(bb.entity_id2) end) = c.entity_id \n" +
					"left join rf_entity_id_no cc on  (case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(bb.entity_id2) end) = cc.entity_id \n" +
					"left join (select * from rf_pv_header where status_id != 'I') d on b.rplf_no = d.rplf_no and b.co_id = d.co_id \n" +
					"left join (select * from rf_cv_header where status_id = 'P') e on d.cv_no = e.cv_no and d.co_id = e.co_id \n" +
					"left join (select * from mf_sub_project where status_id != 'I') f on a.sub_projectid = f.sub_proj_id \n" +
					"LEFT JOIN rf_withholding_tax g ON (CASE WHEN a.wtax_id = '04' THEN '24' WHEN a.wtax_id = '15' THEN '25' ELSE a.wtax_id END) = g.wtax_id \n" +
					"LEFT JOIN mf_sub_project h ON a.sub_projectid = h.sub_proj_id \n" +
					"LEFT JOIN mf_project i ON h.proj_id = i.proj_id \n" +
					"left join (select y.rplf_no, z.entity_id, x.* " +
					"	from rf_jv_header x " +
					"	left join (select * from rf_liq_header where status_id != 'I') y on x.jv_no = y.jv_no " +
					"	inner join (select * from rf_liq_detail where status_id != 'I') z on y.liq_no = z.liq_no " +
					"	where x.status_id != 'I') j on b.rplf_no = j.rplf_no and b.co_id = j.co_id and a.entity_id = j.entity_id\r\n" +
					
					"where g.wtax_rate > 0 and bb.pv_date >= '2014-01-01 00:00:00' and a.co_id = '"+co_id+"' \n" +
					"and (case when '"+year+"' = 'All' then a.rplf_no is not null else trim(to_char(bb.pv_date, 'yyyy')) = '"+year+"' end) \n" +
					"and (case when "+cmbPeriod.getSelectedIndex()+" = 0 then a.rplf_no is not null else \n" +
					"	  case when "+cmbPeriod.getSelectedIndex()+" = 1 then substr(trim(to_char(bb.pv_date, 'MM-dd-yyyy')),0,3) in ('01','02','03') else \n" +
					"	  case when "+cmbPeriod.getSelectedIndex()+" = 2 then substr(trim(to_char(bb.pv_date, 'MM-dd-yyyy')),0,3) in ('04','05','06') else \n" +
					"	  case when "+cmbPeriod.getSelectedIndex()+" = 3 then substr(trim(to_char(bb.pv_date, 'MM-dd-yyyy')),0,3) in ('07','08','09') else \n" +
					"	  case when "+cmbPeriod.getSelectedIndex()+" = 4 then substr(trim(to_char(bb.pv_date, 'MM-dd-yyyy')),0,3) in ('10','11','12') else a.rplf_no is not null \n" +
					"	  end end end end end) \n" +
					"and (case when '"+month+"' = 'All' then a.rplf_no is not null else substr(trim(to_char(bb.pv_date, 'MM-dd-yyyy')), 0, 3) = '"+month+"' end) \n" +
					"and (case when '"+payee+"' = '' then a.rplf_no is not null else (case when trim(b.rplf_type_id) = '04' then (a.entity_id) else trim(b.entity_id1) end) = '"+payee+"' end) \n" +
					"and (case when '"+rplfno+"' = 'All' then a.rplf_no is not null else a.rplf_no = '"+rplfno+"' end) \n" +
					"and (case when '"+entity_type_id+"' = '' then a.rplf_no is not null else b.entity_type_id = '"+entity_type_id+"' end) \n" +
					"and (case when '"+acct_id+"' = '' then a.rplf_no is not null else a.acct_id like '"+acct_id+"' end)) a \n" +
					
					"where a.wtax_amt > 0 " +
					"and a.rplf_no not in (select rplf_no from rf_ewt_remittance) order by a.Project, a.client";
			*/
		}
	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {

		}	
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
		
	}

	private void cancel() {
		if(isThereAnItemtoRemit()==true)
		{
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				executeCancel();
			}
		} else {	
			executeCancel(); 
		}		
	}
	
	public void executeCancel(){
		
		refresh_fields(); 		
		enable_fields(false); 
		rbtnAllPayee.setEnabled(true);
		refresh_tablesMain(); 
		cmbYear.setEnabled(true);
		cmbPeriod.setEnabled(true);
		cmbMonth.setEnabled(true);
		btnPreview.setEnabled(false);		
		btnRemit.setEnabled(false);		
		cmbYear.setSelectedItem(getMonthYear());
		lblRequestType.setEnabled(false);	
		cmbPmtType.setEnabled(false);
		cmbPmtType.setSelectedIndex(0);
		
		acct_id = "";
		payee_name = "";		
		entity_type_id = "";
		entity_type_name = "";	
		period = "";
		period1 = "";
		period2 = "";
		period3 = "";
		month_from = "";
		month_to = "";	
		day_from = "";
		day_to = "";
		year 	= "";
		month 	= "";
		
	}

	public static void previewJV(String jv_no, String comp_id){

		String criteria = "Form EWT (2307)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", comp_id);
		mapParameters.put("jv_no", jv_no);

		FncReport.generateReport("/Reports/rptForm2307_JV.jasper", reportTitle, company, mapParameters);	

	}

	public void remit(){

		if(tabCenter.getSelectedIndex()==0){		

			if(isThereAnItemtoRemit()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "No item for remittance.", "Information", 
					JOptionPane.INFORMATION_MESSAGE);}
			else {			

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remit the tagged items?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					String rplf = sql_getNextRPLFno();

					pgUpdate db = new pgUpdate();	
					insertRPLF_header(rplf, db);				
					insertRPLF_detail(rplf, db);
					insertRemittanceData(rplf, db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"New payment request for BIR remittance was saved.","Information",JOptionPane.INFORMATION_MESSAGE);
					displayEWT_forRemittance_all(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, payee, "", year, period, month, "" );
					displayEWT_remitted(modelRemitted, rowHeaderRemitted, modelRemitted_total, payee, "", year, period, month, "" );
					btnRemit.setEnabled(false);
				}		
			}
		}

		else {JOptionPane.showMessageDialog(getContentPane(),"Selected items cannot be remitted","ERROR",JOptionPane.ERROR_MESSAGE);}

	}

	public class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {				

				if(tabCenter.getSelectedIndex()==0)
				{				
					int column 	= tblLiq_part.getSelectedColumn();
					if (column==4) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
					if (column==6) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; mniopenJV.setEnabled(true) ;}
				}
				else if(tabCenter.getSelectedIndex()==1)
				{				
					int column 	= tblRemitted.getSelectedColumn();
					if (column==3||column==10) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
					if (column==4) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; mniopenJV.setEnabled(true) ;}
				}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				if(tabCenter.getSelectedIndex()==0)
				{				
					int column 	= tblLiq_part.getSelectedColumn();
					if (column==4) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
					if (column==6) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; mniopenJV.setEnabled(true) ;}
				}
				else if(tabCenter.getSelectedIndex()==1)
				{				
					int column 	= tblRemitted.getSelectedColumn();
					if (column==3||column==10) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false) ;}
					if (column==4) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; mniopenJV.setEnabled(true) ;}
				}
			}
		}
	}


	//lookup and search
	public static String sql_getEntityID(String emp_code) {

		String entityid = "";

		String SQL = 
			"select entity_id from em_employee where emp_code = '"+emp_code+"'  \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			entityid = (String) db.getResult()[0][0];
		}else{
			entityid = "";
		}

		return entityid;
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

	public static String sql_getNextRPLFno() {//EDITED BY JED 2021-05-17 : CHANGE THE GENERATION OF RPLF INSIDE THE FUNCTION

		String rplf ="";

//		String SQL = 
//			"select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+co_id+"' " ;
		String SQL = 
				"select * from fn_get_rplf_no('"+co_id+"')" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			rplf = (String) db.getResult()[0][0];
		}else{
			rplf = "000000001";
		}

		return rplf;
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

	public static String getMonthYear() {//used		

		String x = "";

		DateFormat df    = new SimpleDateFormat("MM-dd-yyyy");	
		String year_str	 = df.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()));	
		String year_full=  year_str.substring(6);

		x = year_full;

		return x;

	}
	
	
	//table operations
	public
	static void totalEWT_forRemittance(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal wtax_amt 	= new BigDecimal(0.00);	
		BigDecimal gross_amt 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null,  null,  null,  null, null,  null, wtax_amt, gross_amt});
	}	

	public static void totalEWT_forRemittance_LTS(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal wtax_amt 	= new BigDecimal(0.00);	
		BigDecimal gross_amt 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { null, "Total",  null,  null,  null,  null, null,  null, wtax_amt, gross_amt});
	}	

	public static void totalEWT_forRemitted(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal wtax_amt 	= new BigDecimal(0.00);	
		BigDecimal gross_amt 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,7)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null,  null,  null,  null,  wtax_amt, gross_amt});
	}


	//validate saving
	public Boolean isThereAnItemtoRemit(){

		boolean x = false;		
		int w = 0;

		//create CDF 
		while ( w < tblLiq_part.getRowCount()) {
			Boolean isTrue = false;
			if(tblLiq_part.getValueAt(w,0) instanceof String){isTrue = new Boolean((String)tblLiq_part.getValueAt(w,0));}
			if(tblLiq_part.getValueAt(w,0) instanceof Boolean){isTrue = (Boolean)tblLiq_part.getValueAt(w,0);}
			if(isTrue){	x = true; break; }

			w++;
		}
		return x;		
	}


	//save and insert
	public void insertRPLF_header(String rplf_no, pgUpdate db){

		Date date_liq_ext	= null;
		String rplf_type_id = "";
		String entity_id1	= "";
		String entity_id2	= "";
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
		rplf_type_id= "01";
		entity_id1	= "0000044930";  //BIR-Mandaluyong
		entity_id2	= sql_getEntityID(UserInfo.EmployeeCode);
		ent_type_id = "13";
		pay_type	= "B"; //govt agency	
		sd_no		= null;
		doc_id		= "09";
		proc_id		= 1;	
		branch_id	= null;	
		justif		= "";
		remarks		= "EWT Remittance to BIR-Mandaluyong.";	
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	= null;		

		String sqlDetail = 
			"INSERT into rf_request_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +		//2
			"'"+rplf_no+"',  \n" +		//3
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//4
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +  //5
			""+date_liq_ext+",  \n" + 	//6
			"'"+rplf_type_id+"' , \n" +	//7
			"'"+entity_id1+"',  \n" +	//8
			"'"+entity_id2+"',  \n" +	//9
			"'"+ent_type_id+"',  \n" +	//10
			""+sd_no+",  \n" +			//11
			"'"+doc_id+"' , \n" +		//12
			""+proc_id+",  \n" +		//13
			""+branch_id+" , \n" +		//14
			"'"+justif+"',  \n" +			//15
			"'"+remarks+"' , \n" +		//16
			"'"+status_id+"' , \n" +	//17
			"'"+created_by+"',  \n" +	//18
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//19
			"'"+edited_by+"' , \n" +	//20
			""+edited_date+",  \n" +	//21	
			"'"+pay_type+"'  \n" +		//22
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		


	}

	public void insertRPLF_detail(String rplf_no, pgUpdate db){					

		int w = 0;
		int x = 1;

		//create CDF 
		while ( w < tblLiq_part.getRowCount()) {

			Boolean isTrue = false;
			if(tblLiq_part.getValueAt(w,0) instanceof String){isTrue = new Boolean((String)tblLiq_part.getValueAt(w,0));}
			if(tblLiq_part.getValueAt(w,0) instanceof Boolean){isTrue = (Boolean)tblLiq_part.getValueAt(w,0);}
			if(isTrue){ 

				Double  amount	= Double.parseDouble(modelLiq_part.getValueAt(w,8).toString());

				String ref_doc_id 	= "";
				String ref_doc_no 	= "";
				Boolean	with_budget	= false;
				Boolean	isprojVatable	= false;
				Boolean	entityVatable	= false;
				Boolean	istaxPaidbyco	= false;
				String part_desc	= "EWT Remittance";
				String acct_id		= "";
				String remarks		= "";
				String entity_id	= "";
				String entity_type_id	= "";
				String proj_id		= "";
				String subproj_id	= "";
				String div_id		= "";
				String dept_id		= "";
				String sec_id		= "";
				String inter_bus_id	= "";
				String inter_co_id	= "";
				String sd_no		= "";
				String asset_no		= "";
				String old_acct_id	= "";	
				String wtax_id		= "";	

				acct_id		= "03-01-06-002"; //withholding tax payable-expanded
				entity_id	= sql_getEntityID(UserInfo.EmployeeCode);			
				entity_type_id	= "02";	
				Double wtax_rate	= 0.00;	
				Double wtax_amount	= 0.00;
				Double vat_rate		= 0.00;
				Double vat_amount	= 0.00;
				Double exp_amount	= amount;
				Double pv_amount	= amount;

				String sqlDetail = 
					"INSERT into rf_request_detail values (" +
					"'"+co_id+"',  \n" +  		//1
					"'"+co_id+"',  \n" +		//2
					"'"+rplf_no+"',  \n" +		//3
					""+x+",  \n" +				//4
					"'"+ref_doc_id+"',  \n" + 	//5					
					"'"+ref_doc_no+"',  \n" + 	//6				
					"null,  \n" +				//7								
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
					"'"+sec_id+"',  \n" +		//19	
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
					"'"+sd_no+"', \n" +			//34
					"'',   \n"  +				//35
					"'"+asset_no+"', \n" +		//36
					"'"+old_acct_id+"'," +		//37				
					"'A',   \n"  +				//38
					"'"+UserInfo.EmployeeCode+"',  \n" +	//39
					"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//40
					"'' , \n" +					//41
					"null  \n" +				//42	
					")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);

				db.executeUpdate(sqlDetail, false);	

				x++;

			}

			else {}

			w++;
		}

	}	

	public void insertRemittanceData(String rplf_no, pgUpdate db){					

		int x = 0;

		while ( x < tblLiq_part.getRowCount()) {
			Boolean isTrue = false;
			if(tblLiq_part.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)tblLiq_part.getValueAt(x,0));}
			if(tblLiq_part.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)tblLiq_part.getValueAt(x,0);}
			if(isTrue){					

				String rplf		= tblLiq_part.getValueAt(x,4).toString();	
				String jvno		= tblLiq_part.getValueAt(x,6).toString();
				String entity	= tblLiq_part.getValueAt(x,1).toString();	
				Double wtax		= Double.parseDouble(tblLiq_part.getValueAt(x,8).toString());	
				Double gr_amt	= Double.parseDouble(tblLiq_part.getValueAt(x,9).toString());	

				String sqlDetail = 
					"INSERT into rf_ewt_remittance values (" +
					"'"+co_id+"',  \n" +  		//1 co_id
					"'"+rplf+"',  \n" +			//2 rplf_no
					"'"+jvno+"',  \n" +			//3 jv_no
					"'"+entity+"',  \n" +		//4 entity
					""+wtax+",  \n" +			//5 wtax
					""+gr_amt+",  \n" +			//6 gr_amt
					"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//7 date_remitted
					"false,  \n"+				//8 cwt_printed
					"'"+UserInfo.EmployeeCode+"', \n"+ //9 created_by
					"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//10 date_created
					"'',  \n" +					//11 edited by
					"null,  \n" +				//12 date_edited
					"'A',  \n" +				//13 status_id
					"'"+rplf_no+"')  \n" ;			//14 rem_rplf_no						

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

			if (rplf.equals("")) {}
			else {			
				RequestForPayment.drf_no  = rplf;
				RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);	

				RequestForPayment.setDRF_header(RequestForPayment.drf_no);
				RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF, RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no );	
				RequestForPayment.btnAddNew.setEnabled(false);
				RequestForPayment.btnRefresh.setEnabled(true);
				RequestForPayment.btnPreview.setEnabled(true);
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

	private void ExportDetailed() {
		String[] strHead = {
				"DEVELOPER'S NAME: CEQHOMES DEVELOPMENT CORPORATION", 
				"PROJECT NAME: TERRAVERDE RESIDENCES", 
				"DATE: " + FncGlobal.getDateSQL().toString()
		};

		String col_names[] = 
			{
				"TIN", "Name", "PV No.", "CV No.", "JV No.", "Return Period", "BIR Code", "Nature Of Income Payment", 
				"Tax Base Amount", "Tax Rate", "Tax Withheld", "Account Name"
			};
		
		String strSQL = "SELECT a.c_tin_no, a.c_client, a.c_rplf_no, a.c_cv_no, a.c_jv_no, a.c_retper, a.c_bircode, \n" +
				"a.c_income_payment_desc, a.c_net_paid, a.c_taxrate, a.c_wtax_amt, a.c_acct_name \n" +
				"FROM view_EWT_forRemittance_all ('"+co_id+"', '"+payee+"', '', '"+PV_num+"', '', '"+year+"', '"+period+"', '"+period1+"', '"+period2+"', '"+period3+"', '"+month+"', '"+acct_id+"', '"+entity_type_id+"') a";
		

		FncGlobal.startProgress("Creating spreadsheet.");
		FncExport.CreateXLSwithHeaders(col_names, strSQL, "EWTReport Detailed", 3, strHead);
		FncGlobal.stopProgress();
	}

	private void ExportSummary() {
		String[] strHead = {
				"DEVELOPER'S NAME: CEQHOMES DEVELOPMENT CORPORATION", 
				"PROJECT NAME: TERRAVERDE RESIDENCES", 
				"DATE: " + FncGlobal.getDateSQL().toString()
		};

		String col_names[] = 
			{
				"TIN", "Name", "Return Period", "BIR Code", "Nature Of Income Payment", "Tax Base Amount", "Tax Rate", "Tax Withheld", "Project"
			};
		
		String strSQL = "select x.c_tin_no, x.c_client, x.c_retper, x.c_bircode, x.c_income_payment_desc, \n" +
				"sum(x.c_net_paid) as c_net_paid, x.c_taxrate, sum(x.c_wtax_amt) as c_wtax_amt, x.c_project_alias || ' ' || x.c_phase as c_project_phase \n" +
				"from (select (select distinct y.proj_alias from mf_sub_project x left join mf_project y on x.proj_id = y.proj_id left join rf_request_detail z on x.sub_proj_id = z.sub_projectid where z.rplf_no = a.c_rplf_no limit 1) as c_project_alias, \n" +
				"(select distinct x.sub_proj_alias from mf_sub_project x left join mf_project y on x.proj_id = y.proj_id left join rf_request_detail z on x.sub_proj_id = z.sub_projectid where z.rplf_no = a.c_rplf_no limit 1) as c_phase, a.* \n" +
				"from view_EWT_forRemittance_all ('"+co_id+"', '"+payee+"', '', '"+PV_num+"', '', '"+year+"', '"+period+"', '"+period1+"', '"+period2+"', '"+period3+"', '"+month+"', '"+acct_id+"', '"+entity_type_id+"') a) x \n" +
				"group by x.c_project_alias || ' ' || x.c_phase, x.c_tin_no, x.c_client, x.c_retper, \n" +
				"x.c_with_lts, x.c_bircode, x.c_income_payment_desc, x.c_taxrate \n" +
				"order by x.c_project_alias || ' ' || x.c_phase, x.c_client, x.c_client";

		FncGlobal.startProgress("Creating spreadsheet.");
		FncExport.CreateXLSwithGroup(col_names, strSQL, "EWTReport Summary", 3, strHead, 8);
		FncGlobal.stopProgress();
	}
	
    private static void generateAll() {
    	
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				btnGenerate.setEnabled(false);
				setButtonsStatus(false, false, false, false);
				FncGlobal.startProgress("Priming report...");

				year = cmbYear.getSelectedItem().toString();
				month_from = "01"; month_to = "12";
				day_from = "01"; day_to = "31";

				if (cmbPeriod.getSelectedIndex()==0) {
					period1 = ""; period2 = ""; period3 = ""; 
					month_from = "01"; month_to = "12"; day_from = "01"; day_to = "31";
				} else if (cmbPeriod.getSelectedIndex()==1) {
					period1 = "01"; period2 = "02"; period3 = "03";
					month_from = "01"; month_to = "03"; day_from = "01"; day_to = "31"; 
				}else if (cmbPeriod.getSelectedIndex()==2) {
					period1 = "04"; period2 = "05"; period3 = "06"; 
					month_from = "04"; month_to = "06"; day_from = "01"; day_to = "30"; 
				}else if (cmbPeriod.getSelectedIndex()==3) {
					period1 = "07"; period2 = "08"; period3 = "09"; 
					month_from = "07"; month_to = "09"; day_from = "01"; day_to = "31"; 
				}else if (cmbPeriod.getSelectedIndex()==4) {
					period1 = "10"; period2 = "11"; period3 = "12"; 
					month_from = "10"; month_to = "12"; day_from = "01"; day_to = "31";  
				} 

				if (cmbMonth.getSelectedIndex()==0 ) {
					month = "";
				}else if (cmbPeriod.getSelectedIndex()==1 && cmbMonth.getSelectedIndex()==1 ) {
					month = "01"; month_from = "01"; month_to = "01"; day_from = "01"; day_to = "31";
				}else if (cmbPeriod.getSelectedIndex()==1 && cmbMonth.getSelectedIndex()==2 ) {
					month = "02"; month_from = "02"; month_to = "02"; day_from = "01"; day_to = "28";
				}else if (cmbPeriod.getSelectedIndex()==1 && cmbMonth.getSelectedIndex()==3 ) {
					month = "03"; month_from = "03"; month_to = "03"; day_from = "01"; day_to = "31";
				}else if (cmbPeriod.getSelectedIndex()==2 && cmbMonth.getSelectedIndex()==1 ) {
					month = "04"; month_from = "04"; month_to = "04"; day_from = "01"; day_to = "30";
				}else if (cmbPeriod.getSelectedIndex()==2 && cmbMonth.getSelectedIndex()==2 ) {
					month = "05"; month_from = "05"; month_to = "05"; day_from = "01"; day_to = "31";
				}else if (cmbPeriod.getSelectedIndex()==2 && cmbMonth.getSelectedIndex()==3 ) {
					month = "06"; month_from = "06"; month_to = "06"; day_from = "01"; day_to = "30";
				}else if (cmbPeriod.getSelectedIndex()==3 && cmbMonth.getSelectedIndex()==1 ) {
					month = "07"; month_from = "07"; month_to = "07"; day_from = "01"; day_to = "31";
				}else if (cmbPeriod.getSelectedIndex()==3 && cmbMonth.getSelectedIndex()==2 ) {
					month = "08"; month_from = "08"; month_to = "08"; day_from = "01"; day_to = "31";
				}else if (cmbPeriod.getSelectedIndex()==3 && cmbMonth.getSelectedIndex()==3 ) {
					month = "09"; month_from = "09"; month_to = "09"; day_from = "01"; day_to = "30";
				}else if (cmbPeriod.getSelectedIndex()==4 && cmbMonth.getSelectedIndex()==1 ) {
					month = "10"; month_from = "10"; month_to = "10"; day_from = "01"; day_to = "31";
				}else if (cmbPeriod.getSelectedIndex()==4 && cmbMonth.getSelectedIndex()==2 ) {
					month = "11"; month_from = "11"; month_to = "11"; day_from = "01"; day_to = "30";
				}else if (cmbPeriod.getSelectedIndex()==4 && cmbMonth.getSelectedIndex()==3 ) {
					month = "12"; month_from = "12"; month_to = "12"; day_from = "01"; day_to = "31";
				} 		

				displayEWT_forRemittance_all(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, payee, "", year, period, month, "" );
				
				btnGenerate.setEnabled(true);
				setButtonsStatus2();
				FncGlobal.stopProgress();
				return null;
			}
			
    	}; 
    	
    	sw.execute(); 
    }

}