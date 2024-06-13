package Accounting.GeneralLedger;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelCV_acct_entries;
import tablemodel.modelCV_pv;
import Accounting.Cashiering.CashReceiptBook;
import Accounting.Cashiering._CashRecieptBooks;
import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.CheckVoucher;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class GeneralLedger extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "General Ledger (GL)";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlPeriodTo_a;
	private JPanel pnlPeriodTo_b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlGLtable;
	private JPanel pnlGLtable_acct_entries;
	private JPanel pnlSouthCenterb;
	private JPanel pnlNorth_main;
	private JPanel pnlNorth_center;
	private JPanel pnlPeriod;
	private JPanel pnlPeriodFr;
	private JPanel pnlPeriodFr_a;
	private JPanel pnlPeriodFr_b;
	private JPanel pnlPeriodTo;
	private JPanel pnlPeriodFr_c;

	private JLabel lblCompany;
	public static JLabel lbl_no;
	static JLabel lblProject;
	static JLabel lblPhase;
	private static JLabel lblAccount;
	static JLabel lbl_date_fr;
	static JLabel lblDate_to;

	static _JLookup lookupCompany;
	public static _JLookup lookupDV_no;	
	static _JLookup lookupProject;	
	static _JLookup lookupPhase;
	static _JLookup lookupAccount;

	public static modelCV_acct_entries modelDV_acct_enties;
	public static modelCV_acct_entries modelDV_accttotal;
	public static modelCV_pv modelGL_summarytotal;
	public static modelCV_pv modelGL_summary;

	private static _JTableTotal tblGL_details_total;	
	private static _JTableMain tblGL_details;		

	public static JList rowHeaderDV_acct_entries;
	public static JList rowHeader_summary;
	static JList rowHeaderGL_details;

	private _JTabbedPane tabCenter;	

	public static _JTagLabel tagCompany;
	static _JTagLabel tagProject;
	static _JTagLabel tagPhase;
	static _JTagLabel tagAccount;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy");

	private JMenuItem mniopenAR;
	private JMenuItem mniopenJV;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenCV;
	private JMenuItem mniopenOR;	

	static _JDateChooser dte_from;
	static _JDateChooser dte_to;

	private static JButton btnGenerate;
	static JButton btnCancel;
	static JButton btnPreview;

	private _JScrollPaneMain scrollGL_detail;
	static tablemodel.modelGL_details modelGL_details;
	static tablemodel.modelGL_details modelGL_details_total;
	private static _JScrollPaneTotal scrollGL_details_total;

	private JPopupMenu menu2;

	public static String co_id = "";
	public static String proj_id = "";
	public static String ph_no = "";
	public static String phase = "";
	public static String acct_id = "";
	public static String company = "";
	public static String proj_name = "";
	public static String company_logo = "";
	static String pay_type = "";
	public static Double beg_bal;

	private ArrayList<Object[]> originalEntries = new ArrayList<Object[]>();

	String entity_id= "";
	String branch_id= "";
	String rec_id 	= "";
	String crb_no 	= "";
	String pbl_id 	= "";
	Integer seq_no 	= 0;

	String rb_fiscal_yr = "";
	String rb_month	= "";
	String ar_no	= "";
	String proj		= "";
	String doc_id 	= "";
	private JPanel pnlPeriodFr_c1;
	static JLabel lblInclude;
	private JPanel pnlPeriodFr_c2;
	public static JRadioButton rbtnDate;
	private JPanel pnlPeriod_2;
	private JPanel pnlPeriod_Fr2;
	public static JRadioButton rbtnPeriod;
	private JPanel pnlPeriodFr_a2;
	static JLabel lbl_period_fr2;
	private JPanel pnlPeriodFr_b2;
	static JComboBox cmbPeriodFr;
	private JPanel pnlPeriodTo2;
	private JPanel pnlPeriodTo_a2;
	static JLabel lblPeriod_to2;
	static JComboBox cmbYear;
	static JComboBox cmbPeriodTo;
	private JPanel pnlPeriodTo_b2;
	private JButton btnExport;
	public static String phasename;
	static JComboBox cmbInclude;
	static JLabel lblYear;
	
	public static String status_id = "A";
	
	static JCheckBox chkIncludeActive;
	static String acct_name 	= "";
	static Integer acct_nrml_bal = 1;
	static String include_month = "";
	
	static Integer period_from = null;
	static Integer period_to = null;	
	static java.util.Date date_from = null;
	static java.util.Date date_to = null;

	public GeneralLedger() {
		super(title, true, true, true, true);
		initGUI();
	}

	public GeneralLedger(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public GeneralLedger(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(932, 586));
		this.setBounds(0, 0, 932, 586);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		//pnlReq_Main = new JPanel();		

		{
			menu2 = new JPopupMenu("Popup");
			mniopenJV = new JMenuItem("Open Journal Voucher");
			mniopenCV = new JMenuItem("Open Disbursement Voucher");
			mniopenPV = new JMenuItem("Open Payable Voucher");			
			mniopenAR = new JMenuItem("Open AR");		
			mniopenOR = new JMenuItem("Open OR");		
			menu2.add(mniopenJV);
			menu2.add(mniopenCV);
			menu2.add(mniopenPV);
			menu2.add(mniopenOR);
			menu2.add(mniopenAR);			
			/*JSeparator sp = new JSeparator();
			menu2.add(sp);	
			menu2.add(mniopenAR);*/
			mniopenJV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openJV();				
				}
			});
			mniopenPV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openPV();				
				}
			});
			mniopenCV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openCV();
				}
			});
			mniopenOR.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openOR();
				}
			});
			mniopenAR.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openAR();
				}
			});
		}


		pnlNorth_main = new JPanel();
		pnlMain.add(pnlNorth_main, BorderLayout.NORTH);
		pnlNorth_main.setLayout(new BorderLayout(5, 5));
		pnlNorth_main.setBorder(lineBorder);		
		pnlNorth_main.setPreferredSize(new java.awt.Dimension(920, 183));


		//start of north_main
		{
			pnlNorth = new JPanel();
			pnlNorth_main.add(pnlNorth, BorderLayout.CENTER);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(516, 236));

			pnlComp = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);	
			pnlComp.setPreferredSize(new java.awt.Dimension(658, 178));

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(514, 122));	

				{
					pnlComp_a1 = new JPanel(new GridLayout(4, 2, 5, 5));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(184, 94));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

					{
						lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
						pnlComp_a1.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(81, 25));
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

									enable_fields(true);
									lookupProject.setLookupSQL(getProject());									
									lookupAccount.setLookupSQL(getChartofAccount());

									lblPhase.setEnabled(false);	
									lookupPhase.setEnabled(false);	
									tagPhase.setEnabled(false);	

									btnPreview.setEnabled(false);
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblProject = new JLabel("        Project", JLabel.TRAILING);
						pnlComp_a1.add(lblProject);
						lblProject.setBounds(8, 11, 62, 12);
						lblProject.setEnabled(false);	
						lblProject.setPreferredSize(new java.awt.Dimension(81, 25));
						lblProject.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}					
					{
						lookupProject = new _JLookup(null, "Project",0,2);
						pnlComp_a1.add(lookupProject);
						lookupProject.setLookupSQL(SQL_COMPANY());
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);	
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									proj_id 	= (String) data[0];	
									proj_name   = (String) data[1];
									tagProject.setTag((String) data[1]);	

									lblPhase.setEnabled(true);	
									lookupPhase.setEnabled(true);
									tagPhase.setEnabled(true);	

									lookupPhase.setLookupSQL(getSubproject());
									btnExport.setEnabled(false);
									refreshTable();
								}
							}
						});
					}
					{
						lblPhase = new JLabel("       Phase", JLabel.TRAILING);
						pnlComp_a1.add(lblPhase);
						lblPhase.setBounds(8, 11, 62, 12);
						lblPhase.setEnabled(false);	
						lblPhase.setPreferredSize(new java.awt.Dimension(81, 25));
						lblPhase.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lookupPhase = new _JLookup(null, "Phase",0,2);
						pnlComp_a1.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(false);	
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									ph_no 		= (String) data[0];	
									phase 		= (String) data[1];	
									phasename  = (String) data[4];
									//tagPhase.setTag(phase); comment by Erick Bituen 09-23-2020
									tagPhase.setTag(phasename); //ericted by Erick Bituen 09-23-2020
									refreshTable();
								}
							}
						});
					}
					{
						lblAccount = new JLabel("Account", JLabel.TRAILING);
						pnlComp_a1.add(lblAccount);
						lblAccount.setEnabled(false);	
						lblAccount.setPreferredSize(new java.awt.Dimension(88, 42));
						lblAccount.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lookupAccount = new _JLookup(null, "Account", 2, 2);
						pnlComp_a1.add(lookupAccount);
						lookupAccount.setBounds(20, 27, 20, 25);
						lookupAccount.setReturnColumn(0);
						lookupAccount.setFilterName(true);
						lookupAccount.setEnabled(false);	
						lookupAccount.setPreferredSize(new java.awt.Dimension(112, 26));
						lookupAccount.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){								
									acct_id 		= (String) data[0];	
									acct_name 		= (String) data[1];	
									tagAccount.setTag((String) data[1]);
									refreshTable();
								}
							}
						});
					}

					pnlComp_a2 = new JPanel(new GridLayout(4, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
					{
						{
							tagCompany = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagCompany);
							tagCompany.setBounds(209, 27, 700, 22);
							tagCompany.setEnabled(true);	
							tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
						{
							tagProject = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagProject);
							tagProject.setBounds(209, 27, 700, 22);
							tagProject.setEnabled(false);							
							tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
						{
							tagPhase = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagPhase);
							tagPhase.setBounds(209, 27, 700, 22);
							tagPhase.setEnabled(false);	
							tagPhase.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
						{
							tagAccount = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagAccount);
							tagAccount.setBounds(209, 27, 700, 22);
							tagAccount.setEnabled(false);	
							tagAccount.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
					}
				}
			}	

			{
				pnlPeriod = new JPanel(new BorderLayout(5,5));
				pnlComp.add(pnlPeriod, BorderLayout.CENTER);	
				pnlPeriod.setPreferredSize(new java.awt.Dimension(658, 24));

				{
					pnlPeriodFr = new JPanel(new BorderLayout(5,5));
					pnlPeriod.add(pnlPeriodFr, BorderLayout.WEST);	
					pnlPeriodFr.setPreferredSize(new java.awt.Dimension(263, 23));
					pnlPeriodFr.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					{
						pnlPeriodFr_a = new JPanel(new GridLayout(1, 1, 0, 0));
						pnlPeriodFr.add(pnlPeriodFr_a, BorderLayout.WEST);	
						pnlPeriodFr_a.setPreferredSize(new java.awt.Dimension(146, 23));
						pnlPeriodFr_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));		

						{
							rbtnDate = new JRadioButton();
							pnlPeriodFr_a.add(rbtnDate);
							rbtnDate.setText("    Date");
							rbtnDate.setHorizontalTextPosition(2);
							//rbtnDate.setBounds(277, 12, 77, 18);
							rbtnDate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							rbtnDate.setSelected(true);
							rbtnDate.setEnabled(true);
							rbtnDate.setPreferredSize(new java.awt.Dimension(79, 23));
							rbtnDate.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){	
									
								if (rbtnDate.isSelected()==true){
									lbl_date_fr.setEnabled(true);	
									dte_from.setEnabled(true);
									lblDate_to.setEnabled(true);
									dte_to.setEnabled(true);
									lblInclude.setEnabled(true);
									cmbInclude.setEnabled(true);
									
									rbtnPeriod.setSelected(false);
									lbl_period_fr2.setEnabled(false);	
									cmbPeriodFr.setEnabled(false);
									lblPeriod_to2.setEnabled(false);
									cmbPeriodTo.setEnabled(false);
									lblYear.setEnabled(false);
									cmbYear.setEnabled(false);
								}
								else 
								{									
									lbl_date_fr.setEnabled(false);	
									dte_from.setEnabled(false);
									lblDate_to.setEnabled(false);
									dte_to.setEnabled(false);
									lblInclude.setEnabled(false);
									cmbInclude.setEnabled(false);
									
									rbtnPeriod.setSelected(true);
									lbl_period_fr2.setEnabled(true);	
									cmbPeriodFr.setEnabled(true);
									lblPeriod_to2.setEnabled(true);
									cmbPeriodTo.setEnabled(true);
									lblYear.setEnabled(true);
									cmbYear.setEnabled(true);
								}									

								}});					
						}			
						{
							lbl_date_fr = new JLabel("Date From", JLabel.TRAILING);
							pnlPeriodFr_a.add(lbl_date_fr);
							lbl_date_fr.setEnabled(false);	
							lbl_date_fr.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							lbl_date_fr.setPreferredSize(new java.awt.Dimension(66, 23));
						}
					}
					{
						pnlPeriodFr_b = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlPeriodFr.add(pnlPeriodFr_b, BorderLayout.CENTER);	
						pnlPeriodFr_b.setPreferredSize(new java.awt.Dimension(129, 23));
						pnlPeriodFr_b.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));

						{
							dte_from = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlPeriodFr_b.add(dte_from);
							dte_from.setBounds(485, 7, 125, 21);
							dte_from.setDate(null);
							dte_from.setEnabled(true);  //Modified by Erick through DCRF 1101 enable manual input of date
							dte_from.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dte_from.getDateEditor()).setEditable(true); //Modified by Erick through DCRF 1101 enable manual input of date
							//dte_from.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));   --Comment by Erick through DCRF 1101
						}	
					}
				}
				{
					pnlPeriodTo = new JPanel(new BorderLayout(0, 0));
					pnlPeriod.add(pnlPeriodTo, BorderLayout.CENTER);	
					pnlPeriodTo.setPreferredSize(new java.awt.Dimension(181, 23));
					pnlPeriodTo.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					pnlPeriodTo_a = new JPanel(new GridLayout(1, 1, 0, 5));
					pnlPeriodTo.add(pnlPeriodTo_a, BorderLayout.WEST);	
					pnlPeriodTo_a.setPreferredSize(new java.awt.Dimension(94, 55));
					pnlPeriodTo_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));		

					{
						lblDate_to = new JLabel("Date To", JLabel.TRAILING);
						pnlPeriodTo_a.add(lblDate_to);
						lblDate_to.setEnabled(false);
						lblDate_to.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						lblDate_to.setPreferredSize(new java.awt.Dimension(67, 55));
					}

					pnlPeriodTo_b = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlPeriodTo.add(pnlPeriodTo_b, BorderLayout.CENTER);	
					pnlPeriodTo_b.setPreferredSize(new java.awt.Dimension(135, 119));
					pnlPeriodTo_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

					{
						dte_to = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlPeriodTo_b.add(dte_to);
						dte_to.setBounds(485, 7, 125, 21);
						dte_to.setDate(null);
						dte_to.setEnabled(true); //Modified by Erick through DCRF 1101 enable manual input of date
						dte_to.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dte_to.getDateEditor()).setEditable(true); //Modified by Erick through DCRF 1101 enable manual input of date
						//dte_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));    --Comment by Erick through DCRF 1101
						dte_to.addPropertyChangeListener( new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent e) {
								//Modified by Erick through DCRF 1101
								try {
									DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");					
									String end_date	 = df.format(dte_to.getDate());	
									String end_date_sub = end_date.substring(5);	
									
									if(end_date_sub.equals("31-12"))
									{
										cmbInclude.setEnabled(true);
									}
									else
									{
										cmbInclude.setEnabled(false);
										cmbInclude.setSelectedIndex(0);
									}
								}catch (NullPointerException ex){
									
								}
								
							}
						});	
					}
				}
				{
					pnlPeriodFr_c = new JPanel(new BorderLayout(5, 5));
					pnlPeriod.add(pnlPeriodFr_c, BorderLayout.EAST);	
					pnlPeriodFr_c.setPreferredSize(new java.awt.Dimension(158, 23));
					pnlPeriodFr_c.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));
					
					
					pnlPeriodFr_c1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlPeriodFr_c.add(pnlPeriodFr_c1, BorderLayout.CENTER);	
					pnlPeriodFr_c1.setPreferredSize(new java.awt.Dimension(82, 23));
					pnlPeriodFr_c1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));
					
					{
						lblInclude = new JLabel("Until", JLabel.TRAILING);
						pnlPeriodFr_c1.add(lblInclude);
						lblInclude.setEnabled(true);
						lblInclude.setPreferredSize(new java.awt.Dimension(67, 55));
					}
					
					pnlPeriodFr_c2 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlPeriodFr_c.add(pnlPeriodFr_c2, BorderLayout.EAST);	
					pnlPeriodFr_c2.setPreferredSize(new java.awt.Dimension(96, 23));
					pnlPeriodFr_c2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));
					
					{
						String status2[] = { "(None)", "13th Month","14th Month","15th Month","99th Month" };
						cmbInclude = new JComboBox(status2);
						pnlPeriodFr_c2.add(cmbInclude);
						cmbInclude.setSelectedItem(null);
						cmbInclude.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
						cmbInclude.setBounds(537, 15, 180, 21);
						cmbInclude.setEnabled(true);
						cmbInclude.setSelectedIndex(0);
						cmbInclude.setPreferredSize(new java.awt.Dimension(119, 26));
						
						System.out.println(cmbInclude.getSelectedIndex());
					}
					
				}

			}
			{
				pnlPeriod_2 = new JPanel(new BorderLayout(5,5));
				pnlComp.add(pnlPeriod_2, BorderLayout.SOUTH);	
				pnlPeriod_2.setPreferredSize(new java.awt.Dimension(514, 23));

				{
					pnlPeriod_Fr2 = new JPanel(new BorderLayout(5,5));
					pnlPeriod_2.add(pnlPeriod_Fr2, BorderLayout.WEST);	
					pnlPeriod_Fr2.setPreferredSize(new java.awt.Dimension(263, 23));
					pnlPeriod_Fr2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					{
						pnlPeriodFr_a2 = new JPanel(new GridLayout(1, 1, 0, 0));
						pnlPeriod_Fr2.add(pnlPeriodFr_a2, BorderLayout.WEST);	
						pnlPeriodFr_a2.setPreferredSize(new java.awt.Dimension(146, 23));
						pnlPeriodFr_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));		

						{
							rbtnPeriod = new JRadioButton();
							pnlPeriodFr_a2.add(rbtnPeriod);
							rbtnPeriod.setText(" Period");
							rbtnPeriod.setHorizontalTextPosition(2);
							rbtnPeriod.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							rbtnPeriod.setSelected(false);
							rbtnPeriod.setEnabled(true);
							rbtnPeriod.setPreferredSize(new java.awt.Dimension(58, 23));
							rbtnPeriod.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){	
									
									if (rbtnDate.isSelected()==true){
										rbtnDate.setSelected(false);
										lbl_date_fr.setEnabled(false);	
										dte_from.setEnabled(false);
										lblDate_to.setEnabled(false);
										dte_to.setEnabled(false);
										lblInclude.setEnabled(false);
										cmbInclude.setEnabled(false);
										
										lbl_period_fr2.setEnabled(true);	
										cmbPeriodFr.setEnabled(true);
										lblPeriod_to2.setEnabled(true);
										cmbPeriodTo.setEnabled(true);
										lblYear.setEnabled(true);
										cmbYear.setEnabled(true);
									}
									else 
									{
										rbtnDate.setSelected(true);
										lbl_date_fr.setEnabled(true);	
										dte_from.setEnabled(true);
										lblDate_to.setEnabled(true);
										dte_to.setEnabled(true);
										lblInclude.setEnabled(true);
										cmbInclude.setEnabled(true);
										
										lbl_period_fr2.setEnabled(false);	
										cmbPeriodFr.setEnabled(false);
										lblPeriod_to2.setEnabled(false);
										cmbPeriodTo.setEnabled(false);
										lblYear.setEnabled(false);
										cmbYear.setEnabled(false);
									}
								}});					
						}	
						{
							lbl_period_fr2 = new JLabel("Period From", JLabel.TRAILING);
							pnlPeriodFr_a2.add(lbl_period_fr2);
							lbl_period_fr2.setEnabled(false);	
							lbl_period_fr2.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							lbl_period_fr2.setPreferredSize(new java.awt.Dimension(84, 55));
						}
					}
					{
						pnlPeriodFr_b2 = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlPeriod_Fr2.add(pnlPeriodFr_b2, BorderLayout.CENTER);	
						pnlPeriodFr_b2.setPreferredSize(new java.awt.Dimension(135, 119));
						pnlPeriodFr_b2.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));

						{
							String status2[] = { "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15" };
							cmbPeriodFr = new JComboBox(status2);
							pnlPeriodFr_b2.add(cmbPeriodFr);
							cmbPeriodFr.setSelectedItem(null);
							cmbPeriodFr.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
							cmbPeriodFr.setBounds(537, 15, 180, 21);
							cmbPeriodFr.setEnabled(false);
							cmbPeriodFr.setSelectedIndex(0);
							cmbPeriodFr.setPreferredSize(new java.awt.Dimension(95, 23));
							cmbPeriodFr.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent evt) 
								{
									
								}
							});		
						}	
					}
				}
				{
					pnlPeriodTo2 = new JPanel(new BorderLayout(0, 0));
					pnlPeriod_2.add(pnlPeriodTo2, BorderLayout.CENTER);	
					pnlPeriodTo2.setPreferredSize(new java.awt.Dimension(420, 23));
					pnlPeriodTo2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					pnlPeriodTo_a2 = new JPanel(new GridLayout(1, 1, 0, 5));
					pnlPeriodTo2.add(pnlPeriodTo_a2, BorderLayout.WEST);	
					pnlPeriodTo_a2.setPreferredSize(new java.awt.Dimension(95, 23));
					pnlPeriodTo_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));		

					{
						lblPeriod_to2 = new JLabel("Period To", JLabel.TRAILING);
						pnlPeriodTo_a2.add(lblPeriod_to2);
						lblPeriod_to2.setEnabled(false);
						lblPeriod_to2.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						lblPeriod_to2.setPreferredSize(new java.awt.Dimension(89, 23));
					}

					pnlPeriodTo_b2 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlPeriodTo2.add(pnlPeriodTo_b2, BorderLayout.CENTER);	
					pnlPeriodTo_b2.setPreferredSize(new java.awt.Dimension(135, 119));
					pnlPeriodTo_b2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

					{
						String status2[] = { "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15" };
						cmbPeriodTo = new JComboBox(status2);
						pnlPeriodTo_b2.add(cmbPeriodTo);
						cmbPeriodTo.setSelectedItem(null);
						cmbPeriodTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
						cmbPeriodTo.setBounds(537, 15, 180, 21);
						cmbPeriodTo.setEnabled(false);
						cmbPeriodTo.setSelectedIndex(0);
						cmbPeriodTo.setPreferredSize(new java.awt.Dimension(95, 23));
						cmbPeriodTo.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{
								}
						});		
					}	
				}
				{
					pnlPeriodFr_c = new JPanel(new BorderLayout(5, 5));
					pnlPeriod_2.add(pnlPeriodFr_c, BorderLayout.EAST);	
					pnlPeriodFr_c.setPreferredSize(new java.awt.Dimension(158, 23));
					pnlPeriodFr_c.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					pnlPeriodFr_c1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlPeriodFr_c.add(pnlPeriodFr_c1, BorderLayout.CENTER);	
					pnlPeriodFr_c1.setPreferredSize(new java.awt.Dimension(82, 23));
					pnlPeriodFr_c1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					{
						lblYear = new JLabel("Year", JLabel.TRAILING);
						pnlPeriodFr_c1.add(lblYear);
						lblYear.setEnabled(true);
						lblYear.setPreferredSize(new java.awt.Dimension(67, 55));
					}

					pnlPeriodFr_c2 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlPeriodFr_c.add(pnlPeriodFr_c2, BorderLayout.EAST);	
					pnlPeriodFr_c2.setPreferredSize(new java.awt.Dimension(95, 23));
					pnlPeriodFr_c2.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

					{
						String status2[] = { "2023","2022","2021","2020","2019","2018", "2017","2016" };
						cmbYear = new JComboBox(status2);
						pnlPeriodFr_c2.add(cmbYear);
						cmbYear.setSelectedItem(null);
						cmbYear.setFont(new java.awt.Font("Segoe UI",Font.PLAIN, 13));
						cmbYear.setBounds(537, 15, 180, 21);
						cmbYear.setEnabled(false);
						cmbYear.setSelectedIndex(0);
						cmbYear.setPreferredSize(new java.awt.Dimension(95, 23));
					}
				}
			}
		}
		{
			pnlNorth_center = new JPanel();
			pnlNorth_main.add(pnlNorth_center, BorderLayout.EAST);
			pnlNorth_center.setLayout(new BorderLayout(5, 5));
			pnlNorth_center.setBorder(lineBorder);		
			pnlNorth_center.setPreferredSize(new java.awt.Dimension(253, 161));
			{
				btnGenerate = new JButton("Generate New Ledger");
				pnlNorth_center.add(btnGenerate);
				btnGenerate.setActionCommand("Generate");
				btnGenerate.setPreferredSize(new java.awt.Dimension(244, 159));
				btnGenerate.addActionListener(this);
				btnGenerate.setEnabled(false);
			}
		}

		//end of north_main




		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlGLtable = new JPanel();
			pnlTable.add(pnlGLtable, BorderLayout.CENTER);
			pnlGLtable.setLayout(new BorderLayout(5, 5));
			pnlGLtable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlGLtable.setBorder(lineBorder);	

			tabCenter = new _JTabbedPane();
			pnlGLtable.add(tabCenter, BorderLayout.CENTER);


			{			
				pnlGLtable_acct_entries = new JPanel(new BorderLayout());
				tabCenter.addTab("   GL Details   ", null, pnlGLtable_acct_entries, null);
				pnlGLtable_acct_entries.setPreferredSize(new java.awt.Dimension(1183, 365));					

				{
					scrollGL_detail = new _JScrollPaneMain();
					pnlGLtable_acct_entries.add(scrollGL_detail, BorderLayout.CENTER);
					{
						modelGL_details = new tablemodel.modelGL_details();

						tblGL_details = new _JTableMain(modelGL_details);
						scrollGL_detail.setViewportView(tblGL_details);
						tblGL_details.setSortable(false);
						tblGL_details.addMouseListener(new PopupTriggerListener_panel());
						tblGL_details.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblGL_details.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblGL_details.getColumnModel().getColumn(3).setPreferredWidth(100);
						tblGL_details.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblGL_details.addMouseListener(this);							
						tblGL_details.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}							
							public void keyPressed(KeyEvent e) {
							}

						}); 
						tblGL_details.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblGL_details.rowAtPoint(e.getPoint()) == -1){
									tblGL_details_total.clearSelection();
								}else{
									tblGL_details.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblGL_details.rowAtPoint(e.getPoint()) == -1){
									tblGL_details_total.clearSelection();
								}else{
									tblGL_details.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderGL_details = tblGL_details.getRowHeader22();
						scrollGL_detail.setRowHeaderView(rowHeaderGL_details);
						scrollGL_detail.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollGL_details_total = new _JScrollPaneTotal(scrollGL_detail);
					pnlGLtable_acct_entries.add(scrollGL_details_total, BorderLayout.SOUTH);
					{
						modelGL_details_total = new tablemodel.modelGL_details();
						modelGL_details_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00) });

						tblGL_details_total = new _JTableTotal(modelGL_details_total, tblGL_details);
						tblGL_details_total.setFont(dialog11Bold);
						scrollGL_details_total.setViewportView(tblGL_details_total);
						((_JTableTotal) tblGL_details_total).setTotalLabel(0);
					}
				}
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(813, 31));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			
			{
				chkIncludeActive = new JCheckBox("Include Active Entries");
				pnlSouthCenterb.add(chkIncludeActive);
				chkIncludeActive.setEnabled(true);	
				chkIncludeActive.setSelected(true);
				chkIncludeActive.setPreferredSize(new java.awt.Dimension(383, 26));
				chkIncludeActive.setHorizontalAlignment(JTextField.CENTER);	
				chkIncludeActive.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

						if (selected) {
							status_id = "A";	
							DefaultListModel listModel = new DefaultListModel();
							generateBeginningBalance(modelGL_details, rowHeaderGL_details, modelGL_details_total, listModel); 
							//generateGL(modelGL_details, rowHeaderGL_details, modelGL_details_total, listModel); 
							btnPreview.setEnabled(true);
							btnCancel.setEnabled(true);	 
							computeRunningBal();
						} else {
							status_id = "P";	
							DefaultListModel listModel = new DefaultListModel();
							generateBeginningBalance(modelGL_details, rowHeaderGL_details, modelGL_details_total, listModel); 
							//generateGL(modelGL_details, rowHeaderGL_details, modelGL_details_total, listModel); 
							btnPreview.setEnabled(true);
							btnCancel.setEnabled(true);	 
							computeRunningBal();
						}
					}
				});
			}
			{

				btnPreview = new JButton("Preview");
				pnlSouthCenterb.add(btnPreview);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
				btnPreview.setEnabled(false);
				//btnPreview.setVisible(false);
			}
			{/*ADDED BY JED VICEDO 2021-08-17 : FOR EXPORTING GENERAL LEDGER C/O ROMUALDO*/
				btnExport = new JButton("Export");
				pnlSouthCenterb.add(btnExport);
				btnExport.setMnemonic(KeyEvent.VK_E);
				btnExport.addActionListener(this);
				btnExport.setActionCommand("Export");
				btnExport.setEnabled(false);
			}
			{

				btnCancel = new JButton("Cancel");
				pnlSouthCenterb.add(btnCancel);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(this);
				btnCancel.setEnabled(false);
			}
			/*{			
				pnlGL_summary = new JPanel(new BorderLayout());
				tabCenter.addTab("  Summary  ", null, pnlGL_summary, null);
				pnlGL_summary.setPreferredSize(new java.awt.Dimension(1183, 365));
				pnlGL_summary.setVisible(false);

				{
					scrollGL_summary = new _JScrollPaneMain();
					pnlGL_summary.add(scrollGL_summary, BorderLayout.CENTER);
					{
						modelGL_summary = new modelCV_pv();

						tblGL_summary = new _JTableMain(modelGL_summary);
						scrollGL_summary.setViewportView(tblGL_summary);
						tblGL_summary.addMouseListener(this);	
						tblGL_summary.setSortable(false);
						tblGL_summary.getColumnModel().getColumn(0).setPreferredWidth(100);
						tblGL_summary.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblGL_summary.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblGL_summary.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}							
							public void keyPressed(KeyEvent e) {
							}

						}); 
						tblGL_summary.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblGL_summary.rowAtPoint(e.getPoint()) == -1){
									tblGL_summarytotal.clearSelection();
								}else{
									tblGL_summary.setCellSelectionEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblGL_summary.rowAtPoint(e.getPoint()) == -1){
									tblGL_summarytotal.clearSelection();
								}else{
									tblGL_summary.setCellSelectionEnabled(true);
								}
							}
						});

					}
					{
						rowHeader_summary = tblGL_summary.getRowHeader22();
						scrollGL_summary.setRowHeaderView(rowHeader_summary);
						scrollGL_summary.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollGL_summarytotal = new _JScrollPaneTotal(scrollGL_summary);
					pnlGL_summary.add(scrollGL_summarytotal, BorderLayout.SOUTH);
					{
						modelGL_summarytotal = new modelCV_pv();
						modelGL_summarytotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

						tblGL_summarytotal = new _JTableTotal(modelGL_summarytotal, tblGL_summary);
						tblGL_summarytotal.setFont(dialog11Bold);
						scrollGL_summarytotal.setViewportView(tblGL_summarytotal);
						((_JTableTotal) tblGL_summarytotal).setTotalLabel(0);
					}
				}	
			}*/
		}
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display tables
	static void generateBeginningBalance(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, DefaultListModel listModel) {		

		if(cmbInclude.getSelectedIndex()==0){include_month = "";} 
		else if(cmbInclude.getSelectedIndex()==1){include_month = "13";}
		else if(cmbInclude.getSelectedIndex()==2){include_month = "14";}
		else if(cmbInclude.getSelectedIndex()==3){include_month = "15";}
		else if(cmbInclude.getSelectedIndex()==4){include_month = "99";}
		
		//DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		FncTables.clearTable(modelMain);
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		String sql = "";
		
//		if(UserInfo.EmployeeCode.equals("900876")) {
//			//ORIGINAL FUNCTION DISPLAYED IS BELOW
//			sql = "select * from view_gen_ledger_detailed_includeActive_v3(" +
//			"'"+acct_id+"','"+co_id+"', " +
//			"'"+date_from+"', '"+date_to+"', " +
//			"'"+lookupProject.getText().trim()+"', " +
//			"'"+lookupPhase.getText().trim()+"', '"+status_id+"', '"+include_month+"',"+period_from+","+period_to+" \n"+
//			")";
//		}else {
			sql = "select * from view_gen_ledger_detailed_includeactive_v4_debug_erick(" +
					"'"+acct_id+"','"+co_id+"', " +
					"'"+date_from+"', '"+date_to+"', " +
					"'"+lookupProject.getText().trim()+"', " +
					"'"+lookupPhase.getText().trim()+"', '"+status_id+"', '"+include_month+"',"+period_from+","+period_to+" \n"+
					")";
		//}
		
		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();	
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalGL(modelMain, modelTotal);			
		}		

		tblGL_details.packAll();
		tblGL_details.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblGL_details.getColumnModel().getColumn(6).setPreferredWidth(100);
		tblGL_details.getColumnModel().getColumn(7).setPreferredWidth(100);
		tblGL_details.getColumnModel().getColumn(8).setPreferredWidth(100);
		tblGL_details.getColumnModel().getColumn(15).setPreferredWidth(150);
		tblGL_details.getColumnModel().getColumn(16).setPreferredWidth(300); // Added by Erick to minimize the with of the remarks column  as requested by orly 2023-04-17
		adjustRowHeight();

	}	

	public static double getBeginningBalance() {

		double begBal = 0.00;

		String sql = 			

				"select '***', 'Beginnig Bal.', " +
						"case when coalesce(sum(a.balance),00) >= 0 then coalesce(sum(a.balance),00) else 0.00 end, " +
						"case when coalesce(sum(a.balance),00) < 0 then coalesce(sum(a.balance),00)*-1 else 0.00 end, " +
						"0.00 from ( \n\n" + 

				//JV 
				"select " +
				//	"a.jv_no,\r\n" + 
				"( case when c.debit is null then 0 else c.debit end ) - ( case when b.credit is null then 0 else b.credit end ) as balance \r\n" + 
				//"( case when b.credit is null then 0 else b.credit end ) as credit\r\n" + 
				"" +

				"from \r\n" + 
				"\r\n" + 
				"		( select distinct on (b.jv_no) \r\n" + 
				"			a.jv_date, b.entry_no, a.fiscal_yr, \r\n" + 
				"			a.period_id, b.tran_amt, b.jv_no, b.line_no, b.bal_side, a.status_id, b.co_id  \r\n" + 
				"			from rf_jv_header a, rf_jv_detail b \r\n" + 
				"			where a.jv_no = b.jv_no and trim(b.acct_id) = '"+lookupAccount.getText().trim()+"' \n" +
				"			and a.date_posted < '"+dateFormat.format(dte_from.getDate())+"' \n" +
				"			and a.status_id ='P' and a.co_id = '"+co_id+"' and b.status_id = 'A' \n" ;

		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and b.project_id = '"+lookupProject.getText().trim()+"' \n"; }
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and b.sub_projectid = '"+lookupPhase.getText().trim()+"' \n"; }		
		sql = sql +	

				"\r\n" + 
				"			order by b.jv_no, b.entry_no \r\n" + 
				"		) as a \n" + 
				"\r\n" + 
				"		left join ( select distinct on (jv_no, co_id) jv_no, bal_side, sum(tran_amt) as credit, co_id \r\n" + 
				"			from rf_jv_detail where bal_side = 'C' and trim(acct_id) = '"+lookupAccount.getText().trim()+"' \r\n" +
				"			and co_id = '"+co_id+"' and status_id = 'A'  \n" ;

		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and project_id = '"+lookupProject.getText().trim()+"' \n"; }
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and sub_projectid = '"+lookupPhase.getText().trim()+"' \n"; }		
		sql = sql +	

				"			 group by jv_no, co_id, bal_side ) as b \r\n" + 
				"			on a.jv_no = b.jv_no and a.co_id = b.co_id \r\n" + 
				"\r\n" + 
				"		left join (select distinct on (jv_no, co_id) jv_no, bal_side, sum(tran_amt) as debit, co_id  \r\n" + 
				"			from rf_jv_detail where bal_side = 'D' and trim(acct_id) = '"+lookupAccount.getText().trim()+"' \r\n" +
				"			and co_id = '"+co_id+"' and status_id = 'A'  \n" ;

		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and project_id = '"+lookupProject.getText().trim()+"' \n"; }
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and sub_projectid = '"+lookupPhase.getText().trim()+"' \n"; }		
		sql = sql +	

				"			group by jv_no, co_id, bal_side) as c \r\n" + 
				"			on a.jv_no = c.jv_no and a.co_id = c.co_id \n\n" +

			"UNION ALL \n\n" +

			//CV
			"select \r\n" + 
			//"a.cv_no,\r\n" + 
			"( case when c.debit is null then 0 else c.debit end ) - ( case when b.credit is null then 0 else b.credit end ) as balance \r\n" + 
			/*"( case when c.debit is null then 0 else c.debit end ) as debit,  \r\n" + 
			"( case when b.credit is null then 0 else b.credit end ) as credit\r\n" + */
			""+

			"from \r\n" + 
			"\r\n" + 
			"		( select a.cv_date, b.tran_amt, b.cv_no, b.bal_side, a.status_id, a.co_id\r\n" + 
			"			from rf_cv_header a, rf_cv_detail b \r\n" + 
			"			where a.cv_no = b.cv_no and trim(b.acct_id) = '"+lookupAccount.getText().trim()+"' \r\n" + 
			"			and a.date_posted < '"+dateFormat.format(dte_from.getDate())+"'  \r\n" ;
		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and b.acct_id = '00' \n"; }  //this is included to remove all CV (since CV does not proj/subproj details) 
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and b.acct_id = '00' \n"; }	 //this is included to remove all CV (since CV does not proj/subproj details) 
		sql = sql +	
				"			and a.status_id ='P' and a.co_id = '"+co_id+"' and b.status_id = 'A' ) as a \r\n" + 
				"			\r\n" + 
				"		left join (select distinct on (cv_no, co_id) cv_no, bal_side, sum(tran_amt) as credit, co_id  \r\n" + 
				"			from rf_cv_detail \r\n" + 
				"			where bal_side = 'C' and trim(acct_id) = '"+lookupAccount.getText().trim()+"' and co_id = '"+co_id+"' and status_id = 'A' \r\n" +
				"			group by cv_no, co_id, bal_side) as b  \r\n" + 
				"			on a.cv_no = b.cv_no and a.co_id = b.co_id \r\n" + 
				"			\r\n" + 
				"		left join (select distinct on (cv_no, co_id) cv_no, bal_side, sum(tran_amt) as debit, co_id  \r\n" + 
				"			from rf_cv_detail \r\n" + 
				"			where bal_side = 'D' and trim(acct_id) = '"+lookupAccount.getText().trim()+"' and co_id = '"+co_id+"' and status_id = 'A' " +
				"			group by cv_no, co_id, bal_side) as c \n\n" + 
				"			on a.cv_no = c.cv_no and a.co_id = c.co_id \r\n" + 

			"UNION ALL \n\n " +

			//PV
			"select \r\n" + 
			//"a.pv_no,\r\n" + 
			"( case when c.debit is null then 0 else c.debit end ) - ( case when b.credit is null then 0 else b.credit end ) as balance \r\n" + 
			/*"( case when c.debit is null then 0 else c.debit end ) as debit,  \r\n" + 
			"( case when b.credit is null then 0 else b.credit end ) as credit\r\n" + */
			"" + 
			"from \r\n" + 
			"\r\n" + 
			"		( select a.pv_date, b.tran_amt, b.pv_no, b.bal_side, a.remarks, a.status_id, a.co_id, b.project_id, b.sub_projectid \r\n" + 
			"			from rf_pv_header a, rf_pv_detail b \r\n" + 
			"			where a.pv_no = b.pv_no and trim(b.acct_id) = '"+lookupAccount.getText().trim()+"' \r\n" + 
			"			and a.date_posted < '"+dateFormat.format(dte_from.getDate())+"' \r\n" + 
			"			and a.status_id ='P' and a.co_id = '"+co_id+"' and b.status_id = 'A' \n" ;

		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and b.project_id = '"+lookupProject.getText().trim()+"' \n"; }
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and b.sub_projectid = '"+lookupPhase.getText().trim()+"' \n"; }		
		sql = sql +	

				" 		) as a \r\n" + 
				"			\r\n" + 
				"		left join (select distinct on (pv_no, co_id) pv_no, bal_side, sum(tran_amt) as credit, co_id  \r\n" + 
				"			from rf_pv_detail \r\n" + 	
				"			where bal_side = 'C' and trim(acct_id) = '"+lookupAccount.getText().trim()+"' and co_id = '"+co_id+"' and status_id = 'A' \n" ;

		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and project_id = '"+lookupProject.getText().trim()+"' \n"; }
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and sub_projectid = '"+lookupPhase.getText().trim()+"' \n"; }		
		sql = sql +	

				"			group by pv_no, co_id, bal_side  ) as b \r\n" + 
				"			on a.pv_no = b.pv_no and a.co_id = b.co_id \r\n" + 
				"			\r\n" + 
				"		left join (select distinct on (pv_no, co_id) pv_no, bal_side, sum(tran_amt) as debit, co_id   \r\n" + 
				"			from rf_pv_detail \r\n" + 
				"			where bal_side = 'D' and trim(acct_id) = '"+lookupAccount.getText().trim()+"' and co_id = '"+co_id+"' and status_id = 'A' \n" ;

		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and project_id = '"+lookupProject.getText().trim()+"' \n"; }
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and sub_projectid = '"+lookupPhase.getText().trim()+"' \n"; }		
		sql = sql +	

				"			group by pv_no, co_id, bal_side) as c \r\n" + 
				"			on a.pv_no = c.pv_no and a.co_id = c.co_id \n\n" +

			"UNION ALL \n\n" +

			//CRB
			"select total as crb_amount from (\r\n" + 
			"\r\n" + 
			"  select distinct on (a.acct_id) a.acct_id, sum(a.crb_amt) as total  \r\n" + 
			"	from (select distinct on (rb_id, doc_id, acct_id) rb_id, doc_id, acct_id, sum(trans_amt) as crb_amt from rf_crb_detail \r\n" + 
			"	where status_id = 'A' and co_id = '"+co_id+"' and trim(acct_id) = '"+lookupAccount.getText().trim()+"' \n" +
			"	group by rb_id, doc_id, acct_id) a\r\n" + 
			"	join rf_crb_header b on a.rb_id = b.rb_id and a.doc_id = b.doc_id\r\n" + 
			"	where b.posted_date < '"+dateFormat.format(dte_from.getDate())+"'  \r\n" ;
		if (lookupProject.getText().trim().equals("")) {} else {sql = sql +	 "			and b.proj_id = '"+lookupProject.getText().trim()+"' \n"; }
		if (lookupPhase.getText().trim().equals("")) {} else {sql = sql +	 "			and b.phase = '"+lookupPhase.getText().trim()+"' \n"; }	
		sql = sql +	

				"	group by acct_id \r\n" +
				") a \n\n"  +
				") a  \n\n" ;

		System.out.printf("SQL #2: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			begBal = Double.parseDouble(db.getResult()[0][0].toString());
		}else{
			begBal = 0.00;
		}

		return begBal;

	}	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Generate"))
		{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					FncGlobal.startProgress("Loading..");
					// TODO Auto-generated method stub
					if (dte_from.getDate()==null||dte_to.getDate()==null)  
					{
						JOptionPane.showMessageDialog(getContentPane(),"Select GL period.","Information",JOptionPane.INFORMATION_MESSAGE);
					} 
					else if (lookupAccount.getText().equals(""))  
					{
						JOptionPane.showMessageDialog(getContentPane(),"Select GL account.","Information",JOptionPane.INFORMATION_MESSAGE);
					}
					else 
					{	
						/*txtBegBal.setText("0");
						beg_bal = getBeginningBalance();
						txtBegBal.setText(nf.format(beg_bal));*/
						getDate();
						DefaultListModel listModel = new DefaultListModel();
						generateBeginningBalance(modelGL_details, rowHeaderGL_details, modelGL_details_total, listModel); 
						//generateGL(modelGL_details, rowHeaderGL_details, modelGL_details_total, listModel); 
						btnPreview.setEnabled(true);
						btnCancel.setEnabled(true);	 
						btnExport.setEnabled(true);
						computeRunningBal();
						
					}
					FncGlobal.stopProgress();
				}
			}).start();
			
			
		}
		if(e.getActionCommand().equals("Cancel"))
		{ 			
			lblPhase.setEnabled(false);	
			lookupPhase.setEnabled(false);	
			tagPhase.setEnabled(false);	
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
			refresh_fields();
			refreshTable();
			chkIncludeActive.setSelected(true);
		}
		if(e.getActionCommand().equals("Preview")){preview();}
		
		if(e.getActionCommand().equals("Export")){/*ADDED BY JED VICEDO 2021-08-17 : FOR EXPORTING GENERAL LEDGER C/O ROMUALDO*/
			String col_names [] = {"Tran. Date.", "PBL Desc.", "Debit", "Credit", "Running Bal.","Division","Department","Project","Phase","JV No.","CV No.", "PV No.", "OR No.", "AR No.", "PFR No.", "Remarks", "Payee", "Status"};

			String SQL = 			
					//Replace by Del Gonzales on Aug. 06, 2016 by a function 
					"select\n" +
					"c_t_date,\n" +
					"c_description,\n" +
					"c_debit,\n" +
					"c_credit,\n" +
					"c_run_bal,\n" +
					"(SELECT division_alias FROM mf_division where division_code = a.c_div),\n"+ 
					"(SELECT dept_alias FROM mf_department WHERE dept_code = a.c_dept),\n"+
					"(SELECT proj_alias FROM mf_project where proj_id = a.c_project_id),\n" +
					//"c.sub_proj_alias,\n" +
					"(SELECT FORMAT('%s%s', 'PH', phase) as sub_proj_alias FROM mf_sub_project WHERE proj_id = a.c_project_id and (case when c_description = '' then TRIM(sub_proj_id) = TRIM(a.c_sub_projectid) else TRIM(phase) = TRIM(a.c_sub_projectid) end) AND status_id = 'A' limit 1),\n" + //ADDED STATUS ID BY LESTER DCRF 2718
					"\n"+//Added by erick dated 2021-11-12 for preview/export purpose only 
					"c_jv_no, \n" +
					"c_cv_no, \n" +
					"c_pv_no, \n" +
					"c_or_no, \n" +
					"c_ar_no, \n" +
					"c_pfr_no, \n" +
					"c_remarks, \n" +
					"c_payee, \n" +
					"c_status\n" +
					//"from view_gen_ledger_detailed_includeactive_v3_debug_erick(" +Comment by Erick 2023-10-11
					"from view_gen_ledger_detailed_includeactive_v4_debug_erick(" +
					"'"+acct_id+"','"+co_id+"', " +
					"'"+date_from+"', '"+date_to+"', " +
					"'"+lookupProject.getText().trim()+"', " +
					"'"+lookupPhase.getText().trim()+"', '"+status_id+"', '"+include_month+"',"+period_from+","+period_to+" \n"+
					")a\n";
//					"left join mf_project b on b.proj_id = a.c_project_id\n" + 
//					"left join mf_sub_project c on c.proj_id = a.c_project_id and (case when c_description = '' then c.sub_proj_id = a.c_sub_projectid else c.phase = a.c_sub_projectid end)\n"+
//					//"left join mf_division d on a.c_div = d.division_code \n"+
//					//"left join mf_department e on a.c_dept =e.dept_code \n"+
//					"order by c_t_date;";
			
			FncSystem.out("Export SQL", SQL);

			FncGlobal.startProgress("Creating Spreadsheet");
			FncGlobal.CreateXLS(col_names, SQL, "General Ledger");
			FncGlobal.stopProgress();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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

	public void preview(){
		String criteria = "General Ledger";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);		
		mapParameters.put("prepared_by", UserInfo.FullName);	
		mapParameters.put("date_from", date_from);	
		mapParameters.put("date_to", date_to);	
		mapParameters.put("project", proj_name);	
		//mapParameters.put("phase", phase);	//comment by Erick 09-23-2020 phase = proj_id
		mapParameters.put("phase", ph_no);		//Added by Erick 09-23-2020 to preview report by Phase
		mapParameters.put("ph_code", ph_no);	
		mapParameters.put("ph_no", ph_no);	
		mapParameters.put("proj_id", lookupProject.getText().trim());
		mapParameters.put("acct_id", acct_id);	
		mapParameters.put("acct_name", acct_name);
		mapParameters.put("status_id", status_id);
		mapParameters.put("include_month", include_month);	
		mapParameters.put("period_from", period_from);	
		mapParameters.put("period_to", period_to);
		
		System.out.printf("Date from: %s%n", date_from);
		System.out.printf("Display date to: %s%n", date_to);
		System.out.printf("Display phase: %s%n", phase);
		System.out.printf("DIsplay status_id: %s%n", status_id);
		System.out.printf("Display include month: %s%n", include_month);
		System.out.printf("period_from: %s%n", period_from);
		System.out.printf("Display period to: %s%n", period_to);
		
		System.out.println("Preview Report ********* \n"+
				"select (SELECT proj_alias FROM mf_project where proj_id = a.c_project_id),\n" + 
				"(SELECT FORMAT('%s%s', 'PH', phase) as sub_proj_alias FROM mf_sub_project WHERE proj_id = a.c_project_id and (case when c_description = '' then TRIM(sub_proj_id) = TRIM(a.c_sub_projectid) else TRIM(phase) = TRIM(a.c_sub_projectid) end) AND status_id = 'A' limit 1),\n" + //ADDED STATUS ID BY LESTER DCRF 2718
				"(SELECT division_alias FROM mf_division where division_code = a.c_div),\n" + 
				"(SELECT dept_alias FROM mf_department WHERE dept_code = a.c_dept),  * from view_gen_ledger_detailed_includeactive_v4_debug_erick(\n" + 
				"'"+acct_id+"',\n" + 
				"'"+co_id+"',\n" + 
				"'"+date_from+"',\n" + 
				"'"+date_to+"',\n" + 
				"'"+lookupProject.getText().trim()+"',\n" + 
				"'"+lookupPhase.getText().trim()+"',\n" + 
				"'"+status_id+"',\n" + 
				"'"+include_month+"',\n" + 
				""+period_from+",\n" + 
				""+period_to+"\n" + 
				") a");
		
		FncReport.generateReport("/Reports/rptGL_preview.jasper", reportTitle, company, mapParameters);
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				int column 	= tblGL_details.getSelectedColumn();
				if (column==9) {
					mniopenJV.setEnabled(true);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(false) ; 					
				}
				if (column==10) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(true) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(false) ; 	
				}
				if (column==11) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(true) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(false) ; 	
				}
				if (column==12) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(true) ; 
					mniopenAR.setEnabled(false) ; 	
				}
				if (column==13) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(true) ; 	
				}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				int column 	= tblGL_details.getSelectedColumn();
				if (column==9) {
					mniopenJV.setEnabled(true);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(false) ; 					
				}
				if (column==10) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(true) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(false) ; 	
				}
				if (column==11) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(true) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(false) ; 	
				}
				if (column==12) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(true) ; 
					mniopenAR.setEnabled(false) ; 	
				}
				if (column==13) {
					mniopenJV.setEnabled(false);  
					mniopenCV.setEnabled(false) ; 
					mniopenPV.setEnabled(false) ; 
					mniopenOR.setEnabled(false) ; 
					mniopenAR.setEnabled(true) ; 	
				}
			}
		}
	}



	//components
	public static void enable_fields(Boolean x){

		lblProject.setEnabled(x);	
		lookupProject.setEnabled(x);	
		tagProject.setEnabled(x);	

		lblPhase.setEnabled(x);	
		lookupPhase.setEnabled(x);	
		tagPhase.setEnabled(x);	

		lblAccount.setEnabled(x);	
		lookupAccount.setEnabled(x);	
		tagAccount.setEnabled(x);	

		lbl_date_fr.setEnabled(x);	
		dte_from.setEnabled(x);
		lblDate_to.setEnabled(x);
		dte_to.setEnabled(x);

		//lblBegBal.setEnabled(x);
		//txtBegBal.setEnabled(x);	

		btnGenerate.setEnabled(x);
		btnPreview.setEnabled(x);

	}

	public void refresh_fields(){

		lookupProject.setValue("");
		tagProject.setTag("");
		lookupPhase.setValue("");
		tagPhase.setTag("");
		lookupAccount.setValue("");
		tagAccount.setTag("");
		//dte_from.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));  ----Comment by Erick through DCRF 1101
		//dte_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));    ----Comment by Erick through DCRF 1101
		chkIncludeActive.setSelected(false);

		modelGL_details_total = new tablemodel.modelGL_details();
		modelGL_details_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00) });

		tblGL_details_total = new _JTableTotal(modelGL_details_total, tblGL_details);
		tblGL_details_total.setFont(dialog11Bold);
		scrollGL_details_total.setViewportView(tblGL_details_total);
		((_JTableTotal) tblGL_details_total).setTotalLabel(0);

		entity_id= "";
		branch_id= "";
		rec_id 	= "";
		crb_no 	= "";
		pbl_id 	= "";
		seq_no 	= 0;

		rb_fiscal_yr = "";
		rb_month= "";
		ar_no	= "";
		proj	= "";
		doc_id 	= "";

		proj_id = "";
		ph_no 	= "";
		phase 	= "";
		acct_id = "";
		proj_name = "";
		pay_type  = "";
		status_id= "P";
	}

	public void refreshTable(){
		FncTables.clearTable(modelGL_details);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderGL_details.setModel(listModel);//Setting of DefaultListModel into rowHeader
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();

		enable_fields(true);
		lookupProject.setLookupSQL(getProject());									
		lookupAccount.setLookupSQL(getChartofAccount());

		lblPhase.setEnabled(false);	
		lookupPhase.setEnabled(false);	
		tagPhase.setEnabled(false);	

		btnPreview.setEnabled(false);
		btnCancel.setEnabled(true);
		lookupCompany.setValue(co_id);
		chkIncludeActive.setSelected(true);
}
	
	
	//lookup
	
	public static String getProject(){

		String sql = 
				"select a.proj_id as \"Project ID\", " +
						"a.proj_name as \"Project Name\", " +
						"a.proj_alias as \"Project Alias\", " +
						"b.sub_proj_id as \"SubProject ID\", " +
						"a.vatable as \"Vatable\" " +
						"from mf_project a " +
						"left join ( select distinct on (proj_id) proj_id, sub_proj_id from mf_unit_info ) b  on a.proj_id=b.proj_id " ;
		return sql;

	}	

	public static String getSubproject(){
		String sql = "\n" + 
				"select distinct on (proj_id,sub_proj_id)\n" + 
				"a.sub_proj_id,\n" + 
				"a.proj_id,\n" + 
				"a.phase,\n" + 
				"c.proj_name,\n" + 
				"a.sub_proj_name \n"+
				"from mf_sub_project a\n" + 
				"left join mf_unit_info b on a.proj_id=b.proj_id\n" + 
				"left join mf_project c on b.proj_id=c.proj_id\n" + 
				"where c.co_id='"+co_id+"'\n" + 
				"and a.proj_id = '"+proj_id+"'\n" + 
				"and a.sub_proj_id != ''"
				+ "AND a.status_id = 'A'";  
				//"and a.sub_proj_id not in ('004','016')"; //Added by Erick to include ph3 in lookup--01-21-2019
		
				/*"select \r\n" + 
						"distinct on (a.proj_id, a.sub_proj_id)\r\n" + 
						"\r\n" + 
						"a.sub_proj_id  as \"Subproj Code\",\r\n" + 
						"a.phase as \"Phase\",  \r\n" + 
						"a.proj_id as \"Proj Code\",\r\n" + 
						"b.proj_name as \"Proj Name\"  \r\n" + 

			"from mf_unit_info a\r\n" + 
			"left join mf_project b on a.proj_id = b.proj_id \r\n" + 
			"where co_id = '"+co_id+"' and a.proj_id = '"+proj_id+"' and a.sub_proj_id != ''   " ;	*/
		
		System.out.printf("SQL #1: getSubproject" + sql);
		
		return sql;
	}	
	
	//Added by Erick 04-13-23
	public static String getChartofAccount(){
		
		String sql ="select * from get_chartofaccount()";
		System.out.println("getChartofAccount: "+ sql);
		return sql;
	}
	//Comment by Erick 04-13-23
	//added by erick 2019-02-28
	/*public static String getChartofAccount(){
		
		String sql ="select \n" + 
				"acct_id as \"Acct ID\", \n" + 
				"trim(acct_name) as \"Acct Name\",    \n" + 
				"bs_is as \"Balance\" \n" + 
				" from mf_boi_chart_of_accounts \n" + 
				" where status_id='A' \n"+
				//"and acct_grp_id is not  null \n" + 
				"and w_subacct is null \n"+
				"or acct_id in \n"+
				"(  \n"+
				" '08-01-04-000',\n"+   //added by Erick 2023/01/20 due to Migration 
				" '07-01-00-000',\n"+   //edited  by erick 2020-01-31 thru DCRF 1337
				" '01-03-07-000', \n"+  //edited  by erick 2019-03-07 thru DCRF 962	to reverse dcrf 959
				" '02-04-00-000',\n"+
				" '04-06-00-000', \n"+
				//" '09-01-00-000' \n"+ //edited  by erick 2019-10-22 thru  dcrf 1258 and 1264
				"'01-03-04-001', '01-03-03-001', '01-01-04-016',  \n"+ //for viewing only as requested by Rico 
				"'01-02-14-003', \n"+     // Requested 2023/03/15 by orly to close the balance.
				"'01-01-04-059'\n"+ // Requested by7 lester 2023-04-13 the acct has balance.
				")  \n"+
				" order by acct_id ";
		System.out.println("getChartofAccount: "+ sql);
		return sql;
	}*/
	//original sql
	/*public static String getChartofAccount(){

		String sql = "select " +
				"acct_id as \"Acct ID\", " +
				"trim(acct_name) as \"Acct Name\",    " +
				"bs_is as \"Balance\"  " +
				"from mf_boi_chart_of_accounts " +
				"where status_id = 'A' " +
				"and w_subacct is null " +
				"order by acct_id ";		
		return sql;

	}*/

	private static String sql_getAcctGrpID() {

		String acct_grp_id = "";

		String SQL = 
				"select acct_grp_id from mf_boi_chart_of_accounts where acct_id = '"+acct_id+"'" ;

		System.out.printf("SQL #1: sql_getAcctGrpID" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {acct_grp_id = "";}
			else {acct_grp_id = (String) db.getResult()[0][0]; }
		}else{
			acct_grp_id = "";
		}

		return acct_grp_id;
	}

	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = 
			"select company_logo from mf_company \n" + 
			"where co_id = '02' " ;

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
	


	//table
	public static void totalGL(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		Double debit			= 0.00;
		Double credit			= 0.00;
		//Double end_bal			= 0.00;

		BigDecimal debit_bd 	= new BigDecimal(0.00);	
		BigDecimal credit_bd 	= new BigDecimal(0.00);	
		BigDecimal end_bal_bd   = new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { debit_bd 	= debit_bd.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { debit_bd 	= debit_bd.add(new BigDecimal(0.00)); }

			try { credit_bd = credit_bd.add(((BigDecimal) modelMain.getValueAt(x,7)));} 
			catch (NullPointerException e) { credit_bd 	= credit_bd.add(new BigDecimal(0.00)); }

			try { debit 	= debit + Double.parseDouble(modelMain.getValueAt(x,6).toString().replace(",", ""));} 
			catch (NullPointerException e) { debit 	= debit + 0.00; }

			try { credit = credit + Double.parseDouble(modelMain.getValueAt(x,7).toString().replace(",", ""));} 
			catch (NullPointerException e) { credit 	= credit + 0.00; }

		}		

		end_bal_bd = new BigDecimal(debit - credit);
		modelTotal.addRow(new Object[] { "Total", null, "","","","",debit_bd, credit_bd, end_bal_bd, null, null });

		//end_bal

	}

	private static void adjustRowHeight(){		

		int rw = tblGL_details.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblGL_details.setRowHeight(x, 22);			
			x++;
		}
	}

	public static void computeRunningBal() {			

		String acct_grp = sql_getAcctGrpID();

		if (acct_grp.equals("01-00-00-000")||acct_grp.equals("02-00-00-000")||acct_grp.equals("07-00-00-000")||acct_grp.equals("08-00-00-000")) 
		{acct_nrml_bal = 1;}else {acct_nrml_bal = -1;}		

		Double debit 	= 0.00;	
		Double credit 	= 0.00;	
		Double runBal 	= 0.00;	

		for(int x=0; x < modelGL_details.getRowCount(); x++){		

			if (acct_nrml_bal == 1)
			{
				debit  = Double.parseDouble(tblGL_details.getValueAt(x,6).toString().replace(",",""));
				credit = Double.parseDouble(tblGL_details.getValueAt(x,7).toString().replace(",",""));
				runBal = (runBal + debit - credit) ;		
			}
			else if (acct_nrml_bal == -1)
			{
				debit  = Double.parseDouble(tblGL_details.getValueAt(x,6).toString().replace(",",""));
				credit = Double.parseDouble(tblGL_details.getValueAt(x,7).toString().replace(",",""));
				runBal = (runBal + credit - debit) ;		
			}

			modelGL_details.setValueAt(new BigDecimal(runBal), x, 8);
		}		


	}


	//right-click
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
			JournalVoucher.lookupJV.setLookupSQL(JournalVoucher.getJV_no());	

			JournalVoucher.enableButtons(true,  false,  false,  false, false, 
					false, false,  false,  false, false );	

			int x = tblGL_details.getSelectedRow();
			String jv_no = "";

			try { jv_no = modelGL_details.getValueAt(x,9).toString(); } catch (NullPointerException e) { jv_no = ""; }

			if (jv_no.equals("")) {}
			else {

				JournalVoucher.refresh_fields();							

				JournalVoucher.jv_no = jv_no;	
				JournalVoucher.lookupJV.setValue(jv_no);

				JournalVoucher.setJV_header(jv_no);
				JournalVoucher.displayJV_details(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account, JournalVoucher.modelJV_accounttotal, jv_no );								

				JournalVoucher.mnidelete.setEnabled(false);
				JournalVoucher.mniaddrow.setEnabled(false);

			}			
		}		
	}

	public static void openCV(){

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
			CheckVoucher.payee = "";

			int x = tblGL_details.getSelectedRow();
			String cv_no = "";			
			try { cv_no = modelGL_details.getValueAt(x,10).toString(); } catch (NullPointerException e) { cv_no = ""; }

			if (!cv_no.equals("")) 
			{								
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

	public static void openPV(){

		if(Home_JSystem.isNotExisting("PayableVoucher")){
			PayableVoucher pv = new PayableVoucher();
			Home_JSystem.addWindow(pv);
		}		

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
			PayableVoucher.lookupPV_no.setLookupSQL(PayableVoucher.getPV_no());	
			PayableVoucher.lookupDRF_no.setLookupSQL(PayableVoucher.getDRF_no());
			PayableVoucher.insertAudit_trail2("Open-PV from GL",PayableVoucher.pv_no);			

			int x = tblGL_details.getSelectedRow();
			String pv_no = "";			
			try { pv_no = modelGL_details.getValueAt(x,11).toString(); } catch (NullPointerException e) { pv_no = ""; }

			if(!pv_no.equals(""))
			{				
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
//					part_desc = PayableVoucher.getDRF_particulars(lookupCompany.getValue(), pv_no).toString().trim();
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

	public void openOR(){

		CashReceiptBook crb = new CashReceiptBook();
		Home_JSystem.addWindow(crb);

		/*if(co_id.equals(""))
		{

		} 
		else 	
		{
			CashReceiptBook.lookupCompany.setValue(co_id);	
			CashReceiptBook.txtCompany.setText(company);

			if (proj_id.equals("")){}
			else 
			{
				CashReceiptBook.lookupProject.setValue(proj_id);
				CashReceiptBook.txtProject.setText(proj_name);
				CashReceiptBook.lookupProject.setEnabled(true);
				CashReceiptBook.txtProject.setEnabled(true);
				CashReceiptBook.lblProject.setEnabled(true);
			}

			if (ph_no.equals("")){}
			else 
			{
				CashReceiptBook.lookupPhase.setValue(phase);
				CashReceiptBook.txtPhase.setText("Phase "+phase);
				CashReceiptBook.lookupPhase.setEnabled(true);
				CashReceiptBook.txtPhase.setEnabled(true);
				CashReceiptBook.lblPhase.setEnabled(true);
			}

			CashReceiptBook.lblReceiptType.setEnabled(true);
			CashReceiptBook.rbtnAR.setEnabled(true);
			CashReceiptBook.rbtnOR.setEnabled(true);
			CashReceiptBook.rbtnAR.setSelected(false);
			CashReceiptBook.rbtnOR.setSelected(true);

			int x = tblGL_details.getSelectedRow();
			String crb_no = "";			
			try { crb_no = modelGL_details.getValueAt(x,8).toString(); } catch (NullPointerException e) { crb_no = ""; }	

			if (crb_no.equals("")){}
			else {
				CashReceiptBook.lookupReceipt.setValue(crb_no);
				CashReceiptBook.lblReceipt.setEnabled(true);
				CashReceiptBook.lookupReceipt.setEnabled(true);

				Object[] crb_dtl = getReceiptDtls_OR(crb_no);	

				String particular = "";
				String pbl_id = "";
				String description = "";
				String entity_id = "";
				String entity_name = "";
				String status = "";
				Date receipt_date = null;

				try { particular = crb_dtl[2].toString();} 	catch (NullPointerException e) { particular = "" ; }
				try { pbl_id = crb_dtl[5].toString();} 	catch (NullPointerException e) { pbl_id = "" ; }
				try { description = crb_dtl[6].toString();} 	catch (NullPointerException e) { description = "" ; }
				try { entity_id = crb_dtl[8].toString();} 	catch (NullPointerException e) { entity_id = "" ; }
				try { entity_name = crb_dtl[9].toString();} 	catch (NullPointerException e) { entity_name = "" ; }
				try { status = crb_dtl[10].toString();} 	catch (NullPointerException e) { status = "" ; }
				try { receipt_date = (Date) crb_dtl[4];} 	catch (NullPointerException e) { receipt_date = null ; }

				CashReceiptBook.enableReceiptDtls(true);
				CashReceiptBook.enableButtons(true, true, false, false, false, false);			

				if (status.trim().equals("ACTIVE")) {CashReceiptBook.enableButtons(true, true, false, true, true, false);}
				else if (status.trim().equals("POSTED")) {CashReceiptBook.enableButtons(true, true, false, false, false, false);}
				else if (status.trim().equals("INACTIVE")) {CashReceiptBook.enableButtons(true, true, true, false, false, false);}								

				CashReceiptBook.txtReceipt.setText(crb_no);
				CashReceiptBook.dateReceiptDate.setDate(receipt_date);
				CashReceiptBook.txtStatus.setText(status);

				if(particular.equals("LNREL")){
					CashReceiptBook.txtUnitID.setText("");
					CashReceiptBook.txtUnitDescription.setText("");
					CashReceiptBook.txtSequence.setText("");
					CashReceiptBook.txtClientID.setText("0000000921");
					CashReceiptBook.txtClientName.setText("PAG-IBIG FUND");
				}else{
					CashReceiptBook.txtUnitID.setText(pbl_id);
					CashReceiptBook.txtUnitDescription.setText(description);
					CashReceiptBook.txtSequence.setText(crb_dtl[7].toString());
					CashReceiptBook.txtClientID.setText(entity_id);
					CashReceiptBook.txtClientName.setText(entity_name);
				}

				originalEntries = new ArrayList<Object[]>();
				_CashRecieptBooks.displayAccountEntries("OR", CashReceiptBook._modelMain, 
						CashReceiptBook._modelTotal, CashReceiptBook.rowHeader, pbl_id, crb_dtl[7].toString(), crb_no, originalEntries, false);
				CashReceiptBook.scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(CashReceiptBook._tblMain.getRowCount())));
				CashReceiptBook._tblMain.packColumns("Particulars", "Account ID", "Account Name", "Debit", "Credit", "Remarks", "Rec. ID");
				CashReceiptBook.btnExpand.setText("Group Entries");
				CashReceiptBook.btnExpand.setActionCommand("Collapse");
				CashReceiptBook.btnExpand.setDisplayedMnemonicIndex(6);
			}
		}*/		
	}

	public void openAR(){

		CashReceiptBook crb = new CashReceiptBook();
		Home_JSystem.addWindow(crb);

		if(co_id.equals(""))
		{

		} 
		else 	
		{
			CashReceiptBook.lookupCompany.setValue(co_id);	
			CashReceiptBook.txtCompany.setText(company);

			if (proj_id.equals("")){}
			else 
			{
				CashReceiptBook.lookupProject.setValue(proj_id);
				CashReceiptBook.txtProject.setText(proj_name);
				CashReceiptBook.lookupProject.setEnabled(true);
				CashReceiptBook.txtProject.setEnabled(true);
				CashReceiptBook.lblProject.setEnabled(true);
			}

			if (ph_no.equals("")){}
			else 
			{
				CashReceiptBook.lookupPhase.setValue(phase);
				CashReceiptBook.txtPhase.setText("Phase "+phase);
				CashReceiptBook.lookupPhase.setEnabled(true);
				CashReceiptBook.txtPhase.setEnabled(true);
				CashReceiptBook.lblPhase.setEnabled(true);
			}

			CashReceiptBook.lblReceiptType.setEnabled(true);
			CashReceiptBook.rbtnAR.setEnabled(true);
			CashReceiptBook.rbtnOR.setEnabled(true);
			CashReceiptBook.rbtnAR.setSelected(true);
			CashReceiptBook.rbtnOR.setSelected(false);
			CashReceiptBook.lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

			int x = tblGL_details.getSelectedRow();
			String crb_no = "";			
			try { crb_no = modelGL_details.getValueAt(x,13).toString(); } catch (NullPointerException e) { crb_no = ""; }	

			if (crb_no.equals("")){}
			else {
				CashReceiptBook.lookupReceipt.setValue(crb_no);
				CashReceiptBook.lblReceipt.setEnabled(true);
				CashReceiptBook.lookupReceipt.setEnabled(true);

				Object[] crb_dtl = getReceiptDtls_AR(crb_no);	

				String particular = "";
				String pbl_id = "";
				String description = "";
				String entity_id = "";
				String entity_name = "";
				String status = "";
				Date receipt_date = null;

				try { particular = crb_dtl[2].toString();} 	catch (NullPointerException e) { particular = "" ; }
				try { pbl_id = crb_dtl[5].toString();} 	catch (NullPointerException e) { pbl_id = "" ; }
				try { description = crb_dtl[6].toString();} 	catch (NullPointerException e) { description = "" ; }
				try { entity_id = crb_dtl[8].toString();} 	catch (NullPointerException e) { entity_id = "" ; }
				try { entity_name = crb_dtl[9].toString();} 	catch (NullPointerException e) { entity_name = "" ; }
				try { status = crb_dtl[10].toString();} 	catch (NullPointerException e) { status = "" ; }
				try { receipt_date = (Date) crb_dtl[4];} 	catch (NullPointerException e) { receipt_date = null ; }

				CashReceiptBook.enableReceiptDtls(true);
				CashReceiptBook.enableButtons(true, true, false, false, false, false);			

				if (status.trim().equals("ACTIVE")) {CashReceiptBook.enableButtons(true, true, false, true, true, false);}
				else if (status.trim().equals("POSTED")) {CashReceiptBook.enableButtons(true, true, false, false, false, false);}
				else if (status.trim().equals("INACTIVE")) {CashReceiptBook.enableButtons(true, true, true, false, false, false);}								

				CashReceiptBook.txtReceipt.setText(crb_no);
				CashReceiptBook.dateReceiptDate.setDate(receipt_date);
				CashReceiptBook.txtStatus.setText(status);

				if(particular.equals("LNREL")){
					CashReceiptBook.txtUnitID.setText("");
					CashReceiptBook.txtUnitDescription.setText("");
					CashReceiptBook.txtSequence.setText("");
					CashReceiptBook.txtClientID.setText("0000000921");
					CashReceiptBook.txtClientName.setText("PAG-IBIG FUND");
				}else{
					CashReceiptBook.txtUnitID.setText(pbl_id);
					CashReceiptBook.txtUnitDescription.setText(description);
					CashReceiptBook.txtSequence.setText(crb_dtl[7].toString());
					CashReceiptBook.txtClientID.setText(entity_id);
					CashReceiptBook.txtClientName.setText(entity_name);
				}

				originalEntries = new ArrayList<Object[]>();
				_CashRecieptBooks.displayAccountEntries("AR", CashReceiptBook._modelMain, 
						CashReceiptBook._modelTotal, CashReceiptBook.rowHeader, pbl_id, crb_dtl[7].toString(), crb_no, lookupCompany.getValue() ,originalEntries, false);
				CashReceiptBook.scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(CashReceiptBook._tblMain.getRowCount())));
				CashReceiptBook._tblMain.packColumns("Particulars", "Account ID", "Account Name", "Debit", "Credit", "Remarks", "Rec. ID");
				CashReceiptBook.btnExpand.setText("Group Entries");
				CashReceiptBook.btnExpand.setActionCommand("Collapse");
				CashReceiptBook.btnExpand.setDisplayedMnemonicIndex(6);
			}
		}
	}

	public Object [] getReceiptDtls_OR(String rb_id) {

		String strSQL = 
				"select distinct\n" +
						"rtrim(a.rb_id) as \"CRB ID\",\n" +
						"rtrim(e.doc_desc) as \"Type\",\n" +
						"f.particulars as \"Particulars\",\n" +
						"sum(a.amount) as \"Amount\",\n" +
						"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else coalesce(b.OR_Date, b.Trans_Date) end) as \"Date Issued\",\n" +
						"rtrim(b.pbl_id) as \"PBL ID\",\n" +
						"c.Description as \"Description\",\n" +
						"b.seq_no as \"Seq.\",\n" +
						"rtrim(b.Entity_ID) as \"Client ID\",\n" +
						"rtrim(d.entity_name) as \"Client Name\", \n\n" +
						"upper(trim(h.status_desc)) \n" +

			"from rf_crb_header a --with(nolock)\n\n" +

			"left join rf_payments b --with(nolock)\n" +
			"on a.pay_rec_id::int = b.pay_Rec_ID --and b.pbl_id = d.PBL_ID and b.seq_no = d.seq_no\n" +
			"and b.status_id in ('A', 'P')\n\n" +

			"left join mf_unit_info c --with(nolock)\n" +
			"on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id --and a.phase = c.Phase\n\n" +

			"left join rf_entity d --with(nolock)\n" +
			"on b.Entity_ID = d.entity_id\n\n" +

			"left join mf_system_doc e --with(nolock)\n" +
			"on a.doc_id = e.doc_id\n\n" +

			"left join (select rb_id, particulars \n" +
			"	from rf_crb_header aa\n" +
			"	group by rb_id, particulars) f\n" +
			"on a.rb_id = f.rb_id\n\n" +

			"left join rf_pay_header g --with(nolock)\n" +
			"on b.client_seqno = g.Client_seqno\n\n" +

			"left join mf_record_status h on a.status_id =  h.status_id \n" +

			"where a.rb_id = '"+ rb_id +"'\n" +
			"and a.phase like '"+ phase +"%'\n" +
			"and c.Description is not null\n" +
			"and a.doc_id = '01' \n\n" +

			"group by\n" +
			"rtrim(a.rb_id),\n" +
			"rtrim(e.doc_desc),\n" +
			"f.particulars,\n" +
			"rtrim(b.pbl_id),\n" +
			"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else coalesce(b.OR_Date, b.Trans_Date) end),\n" +
			"c.Description,\n" +
			"b.seq_no,\n" +
			"rtrim(b.Entity_ID),\n" +
			"rtrim(d.entity_name)," +
			"h.status_desc ;";

		System.out.printf("SQL #1 getCRBDtls_OR: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public Object [] getReceiptDtls_AR(String rb_id) {

		String strSQL = 
				"select distinct\n" +
						"ltrim(rtrim(a.rb_id)) as \"CRB ID\",\n" +
						"ltrim(rtrim(e.doc_desc)) as \"Type\",\n" +
						"rtrim(a.particulars) as \"Particulars\",\n" +
						"sum(a.amount) as \"Amount\",\n" +
						"coalesce(b.OR_Date, b.Trans_Date) as \"Date Issued\",\n" +
						//"getDate() as \"Date Issued\",\n" +
						"coalesce(ltrim(rtrim(b.pbl_id)),'none') as \"PBL ID\",\n" +
						"coalesce(c.Description, 'none') as \"Description\",\n" +
						"coalesce (b.seq_no, 0) as \"Seq.\",\n" +
						"ltrim(rtrim(b.Entity_ID)) as \"Client ID\",\n" +
						"ltrim(rtrim(d.entity_name)) as \"Client Name\", \n\n" +
						"upper(trim(f.status_desc)) \n" +

			"from rf_crb_header a --with(nolock)\n" +
			"left join " +
			"(" +
			"	select or_date, trans_date, pbl_id, seq_no, entity_id, pay_Rec_ID, status_id, proj_id, client_seqno from rf_payments union all \n" +
			"	select a.trans_date, a.booking_date, a.pbl_id, a.seq_no, a.entity_id, a.pay_header_ID, a.status_id, a.proj_id, a.client_seqno \r\n" + 
			"			from rf_pay_header a\r\n" + 
			"			left join rf_pay_detail b on a.client_seqno = b.client_seqno\r\n" + 
			"			where b.receipt_type = '03' and b.part_type = '054' union all \n" +
			"	select trans_date, actual_date, pbl_id, seq_no, entity_id, tra_header_ID, status_id, proj_id, client_seqno from rf_tra_header " +
			") b on a.pay_rec_id::int = b.pay_Rec_ID " +
			" and to_char(a.issued_date,'yyyy-MM-dd')= to_char(b.trans_date,'yyyy-MM-dd')" +
			" and a.reference_no = b.client_seqno  " +
			"--and b.pbl_id = d.PBL_ID and b.seq_no = d.seq_no  -- with(nolock)\n" +
			"left join mf_unit_info c  on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id  --with(nolock)\n" +
			"left join rf_entity d  on b.Entity_ID = d.entity_id --with(nolock)\n" +
			"left join mf_system_doc e on a.doc_id = e.doc_id --with(nolock) \n" +
			"left join mf_record_status f on a.status_id =  f.status_id \n" +

			"where a.rb_id = '"+ rb_id +"'\n" +
			"and (case when '"+ proj_id +"' = 'null' or '"+ proj_id +"' = '' then a.rb_id is not null else a.proj_id = '"+ proj_id +"' end ) " +
			"and (case when '"+ phase +"' = 'null' or '"+ phase.trim()+"' = '' then a.rb_id is not null else a.phase = '"+ phase +"' end ) " +
			"and a.doc_id = '03' \n\n" +

			"group by ltrim(rtrim(a.rb_id)), ltrim(rtrim(e.doc_desc)), a.particulars, ltrim(rtrim(b.pbl_id)), " +
			"c.Description, b.seq_no, ltrim(rtrim(b.Entity_ID)), ltrim(rtrim(d.entity_name)), coalesce(b.OR_Date, b.Trans_Date)," +
			"f.status_desc ;";

		System.out.printf("SQL #1 getCRBDtls_AR: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}


	//get Date
	static void getDate(){
		
		if (rbtnDate.isSelected()==true){
			date_from = dte_from.getDate();		
			date_to = dte_to.getDate();	
			period_from = null;
			period_to = null;
		}
		else {
			
			period_from = cmbPeriodFr.getSelectedIndex() + 1;
			period_to = cmbPeriodTo.getSelectedIndex() + 1;
			include_month 	= "";
			
			if(cmbPeriodFr.getSelectedIndex()==0){try {date_from = dateFormat2.parse((("01-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==1){try {date_from = dateFormat2.parse((("02-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==2){try {date_from = dateFormat2.parse((("03-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==3){try {date_from = dateFormat2.parse((("04-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==4){try {date_from = dateFormat2.parse((("05-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==5){try {date_from = dateFormat2.parse((("06-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==6){try {date_from = dateFormat2.parse((("07-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==7){try {date_from = dateFormat2.parse((("08-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==8){try {date_from = dateFormat2.parse((("09-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==9){try {date_from = dateFormat2.parse((("10-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==10){try {date_from = dateFormat2.parse((("11-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==11){try {date_from = dateFormat2.parse((("12-01-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==12){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==13){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodFr.getSelectedIndex()==14){try {date_from = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}						
		
			if(cmbPeriodTo.getSelectedIndex()==0){try {date_to = dateFormat2.parse((("01-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==1&&cmbYear.getSelectedIndex()==2){try {date_to = dateFormat2.parse((("02-29-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==1&&cmbYear.getSelectedIndex()!=2){try {date_to = dateFormat2.parse((("02-28-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==2){try {date_to = dateFormat2.parse((("03-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==3){try {date_to = dateFormat2.parse((("04-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==4){try {date_to = dateFormat2.parse((("05-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==5){try {date_to = dateFormat2.parse((("06-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==6){try {date_to = dateFormat2.parse((("07-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==7){try {date_to = dateFormat2.parse((("08-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==8){try {date_to = dateFormat2.parse((("09-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==9){try {date_to = dateFormat2.parse((("10-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==10){try {date_to = dateFormat2.parse((("11-30-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==11){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==12){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==13){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}
			if(cmbPeriodTo.getSelectedIndex()==14){try {date_to = dateFormat2.parse((("12-31-"+cmbYear.getSelectedItem())));} catch (ParseException e) {e.printStackTrace();}}							
		}
		
		
	}


}