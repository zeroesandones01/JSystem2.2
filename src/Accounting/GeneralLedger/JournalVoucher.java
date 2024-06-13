package Accounting.GeneralLedger;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.DatePickerCellEditor;

import tablemodel.modelJV_SubLedger2;
import tablemodel.modelJV_acct_entries;
import Accounting.Disbursements.CheckVoucher;
import Accounting.Disbursements.DocsProcessing;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
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
import Renderer.DateRenderer;
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

public class JournalVoucher extends _JInternalFrame implements _GUI, ActionListener, MouseListener, KeyListener {

	/*
	 * AS OF 2021-12-02
	 * 
	 * 1. ADDITIONAL CONTROL FOR FAD PROCESS - UNTAGGING OF PAYMENTS DCRF NO. 1875
	 * 2021-12-02 2. ADDITIONAL COLUMN IN LOOKUP (getEntityList) DCRF NO. 1930 :
	 * INCLUDE TIN NUMBER IN DRF, CA AND JV FOR REPORTING PURPOSES 2022-01-26
	 * 
	 */
	/**
	 * 
	 */
	//Samples Edit
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Journal Voucher (JV)";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlJVDtl_1;
	private JPanel pnlJVDtl_2;
	private JPanel pnlJVDtl_1a;
	private JPanel pnlJVDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlJV;
	private JPanel pnlJV_a;
	private JPanel pnlJV_a1;
	private JPanel pnlJV_a2;
	private JPanel pnlJV_a2_1;
	private JPanel pnlJV_a2_2;
	private JPanel pnlJVDtl;
	private JPanel pnlJV_a2_3;
	private JPanel pnlJVInfo;
	private JPanel pnlJVInfo_1;
	private JPanel pnlJVDtl_2a;
	private JPanel pnlJVDtl_2b;
	private JPanel pnlJV_notes;
	private JPanel pnlDate;
	private JPanel pnlRemarks;
	private JPanel pnlJV_SL;
	private JPanel pnlJV_account;
	private JPanel pnlSouthCenterb;
	private JPanel pnlSouthCenter;

	private JLabel lblCompany;
	private static JLabel lblDateEdited;
	public static JLabel lblJV_no;
	private static JLabel lblStatus;
	private static JLabel lblFiscalyear;
	private static JLabel lblPeriod;
	private JLabel lblDate;
	private static JLabel lblDateTrans;
	private static JLabel lblTransType;
	private static JLabel lblDatePosted;

	public static _JLookup lookupCompany;
	public static _JLookup lookupJV;
	private static _JLookup lookupFiscalYr;
	private static _JLookup lookupPeriod;
	public static _JLookup lookupTranType;

	public static modelJV_SubLedger2 modelJV_SL_total;
	public static modelJV_acct_entries modelJV_account;
	public static modelJV_SubLedger2 modelJV_SL;
	public static modelJV_acct_entries modelJV_accounttotal;

	private static _JTabbedPane tabCenter;

	public static _JTagLabel tagCompany;
	public static _JTagLabel tagTranType;
	private _JTagLabel tagBlank;
	private static _JTagLabel tagPeriod;
	private _JTagLabel tagDetail;

	private static JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnAddNew;
	private static JButton btnRefresh;
	private static JButton btnEdit;
	private JButton btnOK;
	private static JButton btnTag;
	private static JButton btnDelete;
	private static JButton btnUntag;
	private static JButton btnPost;
	private static JButton btnPreview;
	private static JButton btnAddAcct;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private static _JDateChooser dteTransaction;
	private static _JDateChooser dteEdited;
	private _JDateChooser dteRefDate;
	private static _JDateChooser dtePosted;

	private JPopupMenu menu;
	private JScrollPane scpJV_Remark;
	private static _JScrollPaneMain scrollJV_SL;
	private static _JScrollPaneMain scrollJV_account;
	private static _JScrollPaneTotal scrollJV_SLtotal;
	private static _JScrollPaneTotal scrollJV_accounttotal;

	public static JTextArea txtJV_Remark;
	private static JXTextField txtStatus;

	public static JMenuItem mnidelete;
	public static JMenuItem mniaddrow;

	private static _JTableMain tblJV_SL;
	public static _JTableMain tblJV_account;
	public static JList rowHeaderJV_SL;
	public static JList rowHeaderJV_account;

	private static _JTableTotal tblJV_SLtotal;
	private static _JTableTotal tblJV_accounttotal;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public static String jv_no = "";
	public static String co_id = "";
	public static String company = "";
	public static String company_logo;
	Integer next_seqno = 0;
	private static JMenuItem mnicopy;
	private static JMenuItem mnipaste;
	private static JMenuItem mnicopyRow;
	private static JMenuItem mnipasteRow;
	private JPopupMenu menu2;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenCV;
	private JMenuItem mniopenDP;
	private JMenuItem mniopenRPLF;
	static String posted_by = "";
	String table = "";
	private static String item = "";
	public static Boolean writeoff_jv = false;
	public static String drf_no = "";
	public static String copy_acct_id, copy_div, copy_dept, copy_proj, copy_subproj, copy_ibu, copy_acct_desc = "";

	public static String tran_desc = "";
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");

	int seq_no = 1;
	private Integer fiscalyear;
	private JCheckBox chkCurrentYear;
	private static JButton btnRemoveAcct;
	static String isCurrentYear = getCurrentYear();

	public JournalVoucher() {
		super(title, true, true, true, true);
		initGUI();
	}

	public JournalVoucher(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public JournalVoucher(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces._GUI#initGUI()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(935, 550));
		this.setBounds(0, 0, 935, 550);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Row      ");
			mniaddrow = new JMenuItem("Add Row");
			mnicopy = new JMenuItem("Copy");
			mnipaste = new JMenuItem("Paste");
			mnicopyRow = new JMenuItem("Copy Row");
			mnipasteRow = new JMenuItem("Paste Row");
			menu.add(mnidelete);
			menu.add(mniaddrow);
			JSeparator sp = new JSeparator();
			menu.add(sp);
			menu.add(mnicopy);
			menu.add(mnipaste);
			JSeparator sp2 = new JSeparator();
			menu.add(sp2);
			menu.add(mnicopyRow);
			menu.add(mnipasteRow);
			mnipaste.setEnabled(false);
			mnipasteRow.setEnabled(false);
			mnidelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					removeRow();
				}
			});
			mniaddrow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AddRow();
				}
			});
			mnicopy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					copy();
				}
			});
			mnipaste.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					paste();
				}
			});
			mnicopyRow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					copyRow();
				}
			});
			mnipasteRow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					pasteRow();
				}
			});
		}
		{
			menu2 = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenPV = new JMenuItem("Open Payable Voucher");
			mniopenCV = new JMenuItem("Open Disbursement Voucher");
			mniopenDP = new JMenuItem("Open Docs Processing");
			menu2.add(mniopenRPLF);
			menu2.add(mniopenPV);
			menu2.add(mniopenCV);
			menu2.add(mniopenDP);

			mniopenRPLF.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openDRF();
				}
			});
			mniopenPV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openPV();
				}
			});
			mniopenCV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openCV();
				}
			});
			mniopenDP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openDP();
				}
			});

		}

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 177));

			pnlComp = new JPanel(new BorderLayout(5, 0));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);

			// company
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
				lblCompany.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
			}
			{
				lookupCompany = new _JLookup(null, "Company", 0, 2);
				pnlComp_a1.add(lookupCompany);
				lookupCompany.setLookupSQL(SQL_COMPANY());
				lookupCompany.setReturnColumn(0);
				lookupCompany.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						if (data != null) {

							co_id = (String) data[0];
							company = (String) data[1];
							tagCompany.setTag((String) data[1]);
							company_logo = (String) data[3];

							lblJV_no.setEnabled(true);
							lookupJV.setEnabled(true);

							lookupJV.setLookupSQL(getJV_no());

							{
								enableButtons(true, false, false, false, false, false, false, false, false, false);
							}
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
				tagCompany.addMouseListener(new PopupTriggerListener_panel2());
			}

			pnlJV = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlJV, BorderLayout.CENTER);
			pnlJV.setPreferredSize(new java.awt.Dimension(921, 233));
			pnlJV.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlJV.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlJV_a = new JPanel(new BorderLayout(5, 5));
			pnlJV.add(pnlJV_a, BorderLayout.NORTH);
			pnlJV_a.setPreferredSize(new java.awt.Dimension(911, 42));
			pnlJV_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlJV_a.setBorder(lineBorder);

			pnlJV_a1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlJV_a.add(pnlJV_a1, BorderLayout.WEST);
			pnlJV_a1.setPreferredSize(new java.awt.Dimension(92, 40));
			pnlJV_a1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				lblJV_no = new JLabel("Journal No.", JLabel.TRAILING);
				pnlJV_a1.add(lblJV_no);
				lblJV_no.setEnabled(false);
				lblJV_no.setPreferredSize(new java.awt.Dimension(86, 40));
				lblJV_no.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
				lblJV_no.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
			}

			pnlJV_a2 = new JPanel(new BorderLayout(5, 5));
			pnlJV_a.add(pnlJV_a2, BorderLayout.CENTER);
			pnlJV_a2.setPreferredSize(new java.awt.Dimension(814, 40));
			pnlJV_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			pnlJV_a2_1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlJV_a2.add(pnlJV_a2_1, BorderLayout.WEST);
			// pnlJV_a2_1.setPreferredSize(new java.awt.Dimension(197, 22));
			pnlJV_a2_1.setPreferredSize(new java.awt.Dimension(286, 24));

			{
				lookupJV = new _JLookup(null, "JV No.", 2, 2);
				pnlJV_a2_1.add(lookupJV);
				lookupJV.setBounds(20, 27, 20, 25);
				lookupJV.setReturnColumn(0);
				lookupJV.setEnabled(false);
				// lookupJV.setEditable(false);
				lookupJV.setPreferredSize(new java.awt.Dimension(157, 22));
				lookupJV.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						// int row = tblJV_account.getRowCount();
						if (data != null) {

							refresh_fields();

							jv_no = (String) data[0];
							fiscalyear = (Integer) data[3];

							System.out.println("lookupJV");
							System.out.println("jv_no = " + jv_no);
							System.out.println("fiscalyear = " + fiscalyear);

							lookupJV.setValue(jv_no);
							lookupPeriod.setText(String.format("%s", data[4]));

							setJV_header(jv_no);
							displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, jv_no);
							displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);

							mnidelete.setEnabled(false);
							mniaddrow.setEnabled(false);

							tagDetail.setText(null);

						}
					}
				});
			}
			{// Added by ERICK 2021-09-01 AS REQUESTED BY ORLY OF GFC FOR FASTER LOADING OF
				// LOOKUP
				chkCurrentYear = new JCheckBox("current year only?");
				pnlJV_a2_1.add(chkCurrentYear);
				chkCurrentYear.setEnabled(true);
				chkCurrentYear.setSelected(true);
				chkCurrentYear.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 9));
				chkCurrentYear.setPreferredSize(new java.awt.Dimension(148, 24));
				chkCurrentYear.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						if (chkCurrentYear.isSelected()) {
							isCurrentYear = getCurrentYear();
							lookupJV.setLookupSQL(getJV_no());

						} else {
							isCurrentYear = "";
							lookupJV.setLookupSQL(getJV_no());
						}
					}
				});
			}
			/*
			 * pnlJV_a2_2 = new JPanel(new GridLayout(1, 2, 5, 0)); pnlJV_a2.add(pnlJV_a2_2,
			 * BorderLayout.CENTER); pnlJV_a2_2.setPreferredSize(new java.awt.Dimension(357,
			 * 25)); pnlJV_a2_2.addMouseListener(new PopupTriggerListener_panel2());
			 */
			pnlJV_a2_3 = new JPanel(new GridLayout(1, 2, 5, 0));
			pnlJV_a2.add(pnlJV_a2_3, BorderLayout.EAST);
			pnlJV_a2_3.setPreferredSize(new java.awt.Dimension(310, 20));
			pnlJV_a2_3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				lblStatus = new JLabel("Status", JLabel.TRAILING);
				pnlJV_a2_3.add(lblStatus);
				lblStatus.setEnabled(false);
				lblStatus.addMouseListener(new PopupTriggerListener_panel2());
			}
			{
				txtStatus = new JXTextField("");
				pnlJV_a2_3.add(txtStatus);
				txtStatus.setEnabled(false);
				txtStatus.setEditable(false);
				txtStatus.setBounds(120, 25, 300, 22);
				txtStatus.setHorizontalAlignment(JTextField.CENTER);
				txtStatus.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
				txtStatus.addMouseListener(new PopupTriggerListener_panel2());
			}
			{
				pnlJVDtl = new JPanel(new BorderLayout(0, 5));
				pnlJV.add(pnlJVDtl, BorderLayout.WEST);
				pnlJVDtl.setPreferredSize(new java.awt.Dimension(911, 187));

				pnlJVDtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlJVDtl.add(pnlJVDtl_1, BorderLayout.WEST);
				pnlJVDtl_1.setPreferredSize(new java.awt.Dimension(221, 116));
				pnlJVDtl_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlJVDtl_1a = new JPanel(new GridLayout(3, 1, 0, 5));
				pnlJVDtl_1.add(pnlJVDtl_1a, BorderLayout.WEST);
				pnlJVDtl_1a.setPreferredSize(new java.awt.Dimension(99, 116));
				pnlJVDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				{
					lblDateTrans = new JLabel("Trans. Date", JLabel.TRAILING);
					pnlJVDtl_1a.add(lblDateTrans);
					lblDateTrans.setEnabled(false);
				}
				{
					lblDateEdited = new JLabel("Date Edited", JLabel.TRAILING);
					pnlJVDtl_1a.add(lblDateEdited);
					lblDateEdited.setEnabled(false);
				}
				{
					lblDatePosted = new JLabel("Date Posted", JLabel.TRAILING);
					pnlJVDtl_1a.add(lblDatePosted);
					lblDatePosted.setEnabled(false);
				}

				pnlJVDtl_1b = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlJVDtl_1.add(pnlJVDtl_1b, BorderLayout.CENTER);
				pnlJVDtl_1b.setPreferredSize(new java.awt.Dimension(135, 119));
				pnlJVDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					dteTransaction = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlJVDtl_1b.add(dteTransaction);
					dteTransaction.setBounds(485, 7, 125, 21);
					dteTransaction.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteTransaction.setEnabled(true);
					dteTransaction.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor) dteTransaction.getDateEditor()).setEditable(false);
					dteTransaction.addPropertyChangeListener(new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {

							Object[] month_year = getMonthYear();
							lookupFiscalYr.setText((String) month_year[4]);

							System.out.println("dteTransaction:");
							System.out.println("lookupFiscalYr = " + ((String) month_year[4]));
							System.out.println("JV = " + (lookupJV.getValue()));

							if (isYearMonthOpen((String) month_year[4], (String) month_year[0]) == true) {
							} else {
								JOptionPane.showMessageDialog(null,
										"Year [" + (String) month_year[4] + "] ; " + "Month [" + (String) month_year[0]
												+ "] is closed." + "\n" + "Please ask your Department Head to open.",
										"Information", JOptionPane.WARNING_MESSAGE);
								// System.out.println("Year:"+(String) month_year[4]);
								// System.out.println("Year:"+(String) month_year[0]);

							}
						}
					});
				}
				{
					dteEdited = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlJVDtl_1b.add(dteEdited);
					dteEdited.setBounds(485, 7, 125, 21);
					dteEdited.setDate(null);
					dteEdited.setEnabled(false);
					dteEdited.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor) dteEdited.getDateEditor()).setEditable(false);
				}
				{
					dtePosted = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlJVDtl_1b.add(dtePosted);
					dtePosted.setBounds(485, 7, 125, 21);
					dtePosted.setDate(null);
					dtePosted.setEnabled(false);
					dtePosted.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor) dtePosted.getDateEditor()).setEditable(false);
				}

				// Start of Left Panel
				pnlJVInfo = new JPanel(new BorderLayout(0, 0));
				pnlJVDtl.add(pnlJVInfo, BorderLayout.EAST);
				pnlJVInfo.setPreferredSize(new java.awt.Dimension(694, 113));
				pnlJVInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlJVInfo_1 = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlJVInfo.add(pnlJVInfo_1, BorderLayout.WEST);
				pnlJVInfo_1.setPreferredSize(new java.awt.Dimension(112, 113));
				pnlJVInfo_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblTransType = new JLabel("Transaction Type", JLabel.TRAILING);
					pnlJVInfo_1.add(lblTransType);
					lblTransType.setEnabled(false);
				}
				{
					lblFiscalyear = new JLabel("Fiscal Year", JLabel.TRAILING);
					pnlJVInfo_1.add(lblFiscalyear);
					lblFiscalyear.setEnabled(false);
				}
				{
					lblPeriod = new JLabel("Period", JLabel.TRAILING);
					pnlJVInfo_1.add(lblPeriod);
					lblPeriod.setEnabled(false);
				}

				pnlJVDtl_2 = new JPanel(new BorderLayout(5, 0));
				pnlJVInfo.add(pnlJVDtl_2, BorderLayout.CENTER);
				pnlJVDtl_2.setPreferredSize(new java.awt.Dimension(203, 118));
				pnlJVDtl_2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

				pnlJVDtl_2a = new JPanel(new GridLayout(3, 1, 0, 5));
				pnlJVDtl_2.add(pnlJVDtl_2a, BorderLayout.WEST);
				pnlJVDtl_2a.setPreferredSize(new java.awt.Dimension(119, 119));
				pnlJVDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lookupTranType = new _JLookup(null, "Transaction Type", 2, 2);
					pnlJVDtl_2a.add(lookupTranType);
					lookupTranType.setBounds(20, 27, 20, 25);
					lookupTranType.setReturnColumn(0);
					lookupTranType.setFilterName(true);
					lookupTranType.setEnabled(false);
					lookupTranType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupTranType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								tran_desc = (String) data[1];
								tagTranType.setTag(tran_desc);
							}
						}
					});
				}
				{
					lookupFiscalYr = new _JLookup(null, "Fiscal Year", 2, 2);
					pnlJVDtl_2a.add(lookupFiscalYr);
					lookupFiscalYr.setBounds(20, 27, 20, 25);
					lookupFiscalYr.setReturnColumn(0);
					lookupFiscalYr.setEnabled(false);
					// lookupFiscalYr.setEditable(false);
					lookupFiscalYr.setPreferredSize(new java.awt.Dimension(157, 22));
				}
				{
					lookupPeriod = new _JLookup(null, "Period", 2, 2);
					pnlJVDtl_2a.add(lookupPeriod);
					lookupPeriod.setBounds(20, 27, 20, 25);
					lookupPeriod.setReturnColumn(0);
					lookupPeriod.setEnabled(false);
					// lookupPeriod.setEditable(false);
					lookupPeriod.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPeriod.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								String period = (String) data[1];
								tagPeriod.setTag(period);

								if (isYearMonthOpen(lookupFiscalYr.getText(), (String) data[0]) == true) {
								} else {
									JOptionPane.showMessageDialog(null,
											"Year [" + lookupFiscalYr.getText() + "] ; " + "Month [" + (String) data[0]
													+ "] is closed." + "\n"
													+ "Please ask your Department Head to open.",
											"Information", JOptionPane.WARNING_MESSAGE);
								}
							}
						}
					});
				}
				pnlJVDtl_2b = new JPanel(new GridLayout(3, 1, 0, 5));
				pnlJVDtl_2.add(pnlJVDtl_2b, BorderLayout.CENTER);
				pnlJVDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
				pnlJVDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				pnlJVDtl_2b.addMouseListener(new PopupTriggerListener_panel2());

				{
					tagTranType = new _JTagLabel("[ ]");
					pnlJVDtl_2b.add(tagTranType);
					tagTranType.setBounds(209, 27, 700, 22);
					tagTranType.setEnabled(false);
					tagTranType.setPreferredSize(new java.awt.Dimension(27, 33));
					tagTranType.addMouseListener(new PopupTriggerListener_panel2());
				}
				{
					tagBlank = new _JTagLabel("[ ]");
					pnlJVDtl_2b.add(tagBlank);
					tagBlank.setBounds(209, 27, 700, 22);
					tagBlank.setEnabled(false);
					tagBlank.setVisible(false);
					tagBlank.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagPeriod = new _JTagLabel("[ ]");
					pnlJVDtl_2b.add(tagPeriod);
					tagPeriod.setBounds(209, 27, 700, 22);
					tagPeriod.setEnabled(false);
					tagPeriod.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPeriod.addMouseListener(new PopupTriggerListener_panel2());
				}
			}
		}
		{
			pnlDate = new JPanel();
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
				((JTextFieldDateEditor) dteRefDate.getDateEditor()).setEditable(false);
				dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				dteRefDate.addPropertyChangeListener(new PropertyChangeListener() {
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
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));
			pnlTable.addMouseListener(new PopupTriggerListener_panel2());

			pnlJV = new JPanel();
			pnlTable.add(pnlJV, BorderLayout.CENTER);
			pnlJV.setLayout(new BorderLayout(5, 5));
			pnlJV.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlJV.setBorder(lineBorder);
			// pnlJV.addMouseListener(new PopupTriggerListener_panel());

			tabCenter = new _JTabbedPane();
			pnlJV.add(tabCenter, BorderLayout.CENTER);

			{
				pnlJV_account = new JPanel(new BorderLayout());
				tabCenter.addTab("Account Entries", null, pnlJV_account, null);
				pnlJV_account.setPreferredSize(new java.awt.Dimension(1183, 365));
				{
					tagDetail = new _JTagLabel("");
					pnlJV_account.add(tagDetail, BorderLayout.NORTH);
					tagDetail.getDocument().addDocumentListener(new DocumentListener() {
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
					scrollJV_account = new _JScrollPaneMain();
					pnlJV_account.add(scrollJV_account, BorderLayout.CENTER);
					{
						modelJV_account = new modelJV_acct_entries();

						tblJV_account = new _JTableMain(modelJV_account);
						scrollJV_account.setViewportView(tblJV_account);
						tblJV_account.addMouseListener(this);
						tblJV_account.setSortable(false);
						tblJV_account.addMouseListener(new PopupTriggerListener_panel());
						tblJV_account.getColumnModel().getColumn(0).setPreferredWidth(90);
						tblJV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(2).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(3).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(4).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(5).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(6).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(8).setPreferredWidth(100);
						tblJV_account.getColumnModel().getColumn(9).setPreferredWidth(100);

						tblJV_account.getColumnModel().getColumn(0)
								.setCellRenderer(new DateRenderer(new SimpleDateFormat("MM-dd-yyyy")));

						tblJV_account.addKeyListener(new KeyAdapter() {
							public void keyTyped(KeyEvent evt) {
								checkCostCenter_manual(evt);
								checkDivDept(evt);
							}

							public void keyReleased(KeyEvent evt) {
								computeJV_amount();
								checkCostCenter_manual(evt);
								checkDivDept(evt);
								tblJV_SL.packAll();
								table = "tbl_account";
							}

							public void keyPressed(KeyEvent evt) {
								computeJV_amount();
								checkCostCenter_manual(evt);
								checkDivDept(evt);
								tblJV_SL.packAll();
								table = "tbl_account";
							}

						});
						tblJV_account.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblJV_account.rowAtPoint(e.getPoint()) == -1) {
									tblJV_accounttotal.clearSelection();

								} else {
									tblJV_account.setCellSelectionEnabled(true);

								}
							}

							public void mouseReleased(MouseEvent e) {
								if (tblJV_account.rowAtPoint(e.getPoint()) == -1) {
									tblJV_accounttotal.clearSelection();

								} else {
									tblJV_account.setCellSelectionEnabled(true);
								}
							}
						});

						tblJV_account.addKeyListener(this);

						tblJV_account.hideColumns("div");
						tblJV_account.hideColumns("dep");
						tblJV_account.hideColumns("proj");
						tblJV_account.hideColumns("sub");

						tblJV_account.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								try {
									if (!arg0.getValueIsAdjusting()) {

										String lineno = (String) modelJV_account
												.getValueAt(tblJV_account.getSelectedRow(), 0);
										generateDetail(lineno);
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						});
						tblJV_account.putClientProperty("terminateEditOnFocusLost", true);
					}
					{
						rowHeaderJV_account = tblJV_account.getRowHeader22();
						scrollJV_account.setRowHeaderView(rowHeaderJV_account);
						scrollJV_account.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
								FncTables.getRowHeader_Header());
					}
				}
				{
					scrollJV_accounttotal = new _JScrollPaneTotal(scrollJV_account);
					pnlJV_account.add(scrollJV_accounttotal, BorderLayout.SOUTH);
					{
						modelJV_accounttotal = new modelJV_acct_entries();
						modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null,
								new BigDecimal(0.00), new BigDecimal(0.00), null });

						tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
						tblJV_accounttotal.setFont(dialog11Bold);
						scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
						((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);
					}
					scrollJV_accounttotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
				}
			}

			{
				pnlJV_notes = new JPanel(new BorderLayout());
				tabCenter.addTab("Particulars", null, pnlJV_notes, null);
				pnlJV_notes.setPreferredSize(new java.awt.Dimension(100, 365));

				pnlRemarks = new JPanel(new BorderLayout(5, 5));
				pnlJV_notes.add(pnlRemarks, BorderLayout.CENTER);
				pnlRemarks.setPreferredSize(new java.awt.Dimension(100, 178));

				scpJV_Remark = new JScrollPane();
				pnlRemarks.add(scpJV_Remark);
				// scpJV_Remark.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				// scpJV_Remark.setBounds(82, 7, 150, 61);
				// scpJV_Remark.setOpaque(true);
				// scpJV_Remark.setPreferredSize(new java.awt.Dimension(100, 285));

				{
					txtJV_Remark = new JTextArea();
					// scpJV_Remark.add(txtJV_Remark);
					scpJV_Remark.setViewportView(txtJV_Remark);
					// txtJV_Remark.setBounds(77, 3, 250, 81);
					txtJV_Remark.setLineWrap(true);
					// txtJV_Remark.setPreferredSize(new java.awt.Dimension(100, 1000));// removed
					// by Erick
					txtJV_Remark.setEditable(false);
					txtJV_Remark.setEnabled(true);
					// txtJV_Remark.setText("");
				}

			}
			{
				pnlJV_SL = new JPanel(new BorderLayout());
				tabCenter.addTab("Subsidiary Ledgers (Tax)", null, pnlJV_SL, null);
				pnlJV_SL.setPreferredSize(new java.awt.Dimension(1183, 365));

				{
					scrollJV_SL = new _JScrollPaneMain();
					pnlJV_SL.add(scrollJV_SL, BorderLayout.CENTER);
					{
						modelJV_SL = new modelJV_SubLedger2();

						tblJV_SL = new _JTableMain(modelJV_SL);
						scrollJV_SL.setViewportView(tblJV_SL);
						tblJV_SL.addMouseListener(this);
						tblJV_SL.setSortable(false);
						// tblJV_SL.setEnabled(false);
						// tblJV_SL.setEditable(false);
						tblJV_SL.addMouseListener(new PopupTriggerListener_panel());
						tblJV_SL.addKeyListener(new KeyAdapter() {
							public void keyTyped(KeyEvent evt) {
								// checkJVSL_vattax(evt);
							}

							public void keyReleased(KeyEvent evt) {
								if (!btnEdit.isEnabled()) {
									computeJVSL_amount();
								}

								// computeJVSL_amount();
								// checkJVSL_vattax(evt);
								tblJV_SL.packAll();
								table = "tbl_SL";
								// autosetnetofvat();

							}

							public void keyPressed(KeyEvent e) {
								if (!btnEdit.isEnabled()) {
									computeJVSL_amount();
								}
								// computeJVSL_amount();
								// checkJVSL_vattax(e);
								tblJV_SL.packAll();
								table = "tbl_SL";
								// autosetnetofvat();
							}

						});
						tblJV_SL.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								// computeJVSL_amount(e);
								if (tblJV_SL.rowAtPoint(e.getPoint()) == -1) {
									tblJV_SLtotal.clearSelection();
									table = "tbl_SL";
								} else {
									tblJV_SL.setCellSelectionEnabled(true);
									table = "tbl_SL";
								}
							}

							public void mouseReleased(MouseEvent e) {
								// computeJVSL_amount(e);
								if (tblJV_SL.rowAtPoint(e.getPoint()) == -1) {
									tblJV_SLtotal.clearSelection();
									table = "tbl_SL";
								} else {
									tblJV_SL.setCellSelectionEnabled(true);
									table = "tbl_SL";
								}
							}
						});

						tblJV_SL.addKeyListener(this);

						tblJV_SL.putClientProperty("terminateEditOnFocusLost", true);

					}
					{
						rowHeaderJV_SL = tblJV_SL.getRowHeader22();
						scrollJV_SL.setRowHeaderView(rowHeaderJV_SL);
						scrollJV_SL.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollJV_SLtotal = new _JScrollPaneTotal(scrollJV_SL);
					pnlJV_SL.add(scrollJV_SLtotal, BorderLayout.SOUTH);
					{
						modelJV_SL_total = new modelJV_SubLedger2();
						modelJV_SL_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00),
								new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null,
								null, null, null, null, null });

						tblJV_SLtotal = new _JTableTotal(modelJV_SL_total, tblJV_SL);
						tblJV_SLtotal.setFont(dialog11Bold);
						scrollJV_SLtotal.setViewportView(tblJV_SLtotal);
						((_JTableTotal) tblJV_SLtotal).setTotalLabel(0);
					}
				}
			}

		}
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(923, 64));

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
				pnlDate = new JPanel();
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
					((JTextFieldDateEditor) dteRefDate.getDateEditor()).setEditable(false);
					dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteRefDate.addPropertyChangeListener(new PropertyChangeListener() {
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
		}

		// added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	// display tables
	public static void displayJV_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String rec_no) {// used

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.
		if (entry() == false ) {
			String sql = "---------display JV details XX \r\n" + "select \r\n" + "\r\n" + "coalesce(a.acct_id,''),\r\n"
					+ "coalesce(a.div_id,''),\r\n" + "coalesce(a.dept_id,''),\r\n" + "coalesce(a.sect_id,''),\r\n"
					+ "coalesce(a.project_id,''),\r\n" + "coalesce(a.sub_projectid,''),\r\n"
					+ "coalesce(a.inter_busunit_id,''),\r\n" + "coalesce(b.acct_name,''),\r\n"
					+ "( case when a.bal_side = 'D' then a.tran_amt else 0.00 end ) as debit,\r\n"
					+ "( case when a.bal_side = 'C' then a.tran_amt else 0.00 end ) as credit,\r\n"
					+ "(coalesce(a.ref_no,'')||coalesce(d.description,'')) as ref_no,  \r\n"
					+ "get_div_alias(a.div_id),			\r\n" + "get_department_alias_new(a.dept_id),			\r\n"
					+ "get_project_alias(a.project_id),			\r\n"
					+ "(select sub_proj_alias from mf_sub_project where trim(proj_id) = trim(a.project_id) and trim(sub_proj_id) = trim(a.sub_projectid) AND status_id = 'A'),			\r\n"
					+ "a.entry_no," + "a.entity_id," + "a.pbl_id," +"e.entity_name/*, other_period*/			\r\n" + "\r\n" + "from rf_jv_detail a\r\n"
					+ "left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n"
					+ "left join rf_jv_header c on a.jv_no = c.jv_no "
					+ "left join mf_unit_info d on a.pbl_id = d.pbl_id  and a.project_id = d.proj_id \n" + "\r\n"
					+ "left join rf_entity e on a.entity_id = e.entity_id \n"
					+ "where trim(a.jv_no) = trim('" + jv_no + "') " + "and a.co_id = '" + co_id + "' and c.co_id = '"
					+ co_id + "' and a.status_id = 'A' \n\n " +
					// "(case when c.remarks like 'To set-up Sales%' then order by a.pbl_id,
					// a.bal_side desc else " +
					"order by a.bal_side desc, a.line_no \n\n ";

			System.out.printf("SQL #2: %s", sql);

			pgSelect db = new pgSelect();
			db.select(sql);
			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					// Adding of row in table
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}

				totalJV(modelMain, modelTotal);
			}

			else {
				modelJV_accounttotal = new modelJV_acct_entries();
				modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null,
						new BigDecimal(0.00), new BigDecimal(0.00), null });

				tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
				tblJV_accounttotal.setFont(dialog11Bold);
				scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
				((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);
			}
		} else {
			String sql = "---------display JV details XX \r\n" + "select \r\n" + "\r\n" + "coalesce(a.acct_id,''),\r\n"
					+ "coalesce(a.div_id,''),\r\n" + "coalesce(a.dept_id,''),\r\n" + "coalesce(a.sect_id,''),\r\n"
					+ "coalesce(a.project_id,''),\r\n" + "coalesce(a.sub_projectid,''),\r\n"
					+ "coalesce(a.inter_busunit_id,''),\r\n" + "coalesce(b.acct_name,''),\r\n"
					+ "( case when a.bal_side = 'D' then a.tran_amt else 0.00 end ) as debit,\r\n"
					+ "( case when a.bal_side = 'C' then a.tran_amt else 0.00 end ) as credit,\r\n"
					+ "(coalesce(a.ref_no,'')||coalesce(d.description,'')) as ref_no,  \r\n"
					+ "get_div_alias(a.div_id),			\r\n" + "get_department_alias_new(a.dept_id),			\r\n"
					+ "get_project_alias(a.project_id),			\r\n"
					+ "get_sub_proj_alias(a.sub_projectid),			\r\n" + "a.entry_no, a.entity_id,a.pbl_id, e.entity_name \r\n" + "\r\n"
					+ "from rf_jv_detail a\r\n" + "left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n"
					+ "left join rf_jv_header c on a.jv_no = c.jv_no "
					+ "left join mf_unit_info d on a.pbl_id = d.pbl_id  and a.project_id = d.proj_id \n" + "\r\n"
					+ "left join rf_entity e on a.entity_id = e.entity_id \n"
					+ "where trim(a.jv_no) = trim('" + jv_no + "') and a.co_id = '" + co_id + "' and c.co_id = '"
					+ co_id + "' and a.status_id = 'A' \n\n " +
					// "(case when c.remarks like 'To set-up Sales%' then order by a.pbl_id,
					// a.bal_side desc else " +
					"order by a.line_no \n\n ";

			System.out.printf("SQL #2: %s", sql);

			pgSelect db = new pgSelect();
			db.select(sql);
			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					// Adding of row in table
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}

				totalJV(modelMain, modelTotal);
			}

			else {
				modelJV_accounttotal = new modelJV_acct_entries();
				modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null,
						new BigDecimal(0.00), new BigDecimal(0.00), null });

				tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
				tblJV_accounttotal.setFont(dialog11Bold);
				scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
				((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);
			}
		}

		setTableWidth();

		enable_fields(false);
		btnCancel.setEnabled(true);
		btnRefresh.setEnabled(true);
		lblJV_no.setEnabled(true);
		lookupJV.setEnabled(true);
		modelJV_account.setEditable(false);
		modelJV_SL.setEditable(false);
		adjustRowHeight_account();

	}

	public static void displayJV_closeTB(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String dte_from, String dte_to) {

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		//String sql = "---------display TB Closing Details \r\n" + "select * from view_trial_balance_forclosing ('"+ co_id + "','','','" + dte_from + "', \n" + "'" + dte_to+ "', '', false,'P','15') ";
		String sql = "---------display TB Closing Details \r\n" + "select * from view_trial_balance_forclosing_debug_erick ('"+ co_id + "','','','" + dte_from + "', \n" + "'" + dte_to+ "', '', false,'P','15') ";
		
		System.out.printf("SQL #2: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalJV(modelMain, modelTotal);
		}

		else {
			modelJV_accounttotal = new modelJV_acct_entries();
			modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null,
					new BigDecimal(0.00), new BigDecimal(0.00), null });

			tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
			tblJV_accounttotal.setFont(dialog11Bold);
			scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
			((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);
		}

		setTableWidth();
		adjustRowHeight_account();

	}

	public static void creatJVtable(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {// used

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 1,'',''union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   union all  \r\n"
				+ "select '', '', '', '', '', '', '', '', 0.00, 0.00, '', '','','','', 0,'',''   \r\n";

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalJV(modelMain, modelTotal);
		}

		else {
			modelJV_accounttotal = new modelJV_acct_entries();
			modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null,
					new BigDecimal(0.00), new BigDecimal(0.00), null });

			tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
			tblJV_accounttotal.setFont(dialog11Bold);
			scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
			((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);
		}

		setTableWidth();

		adjustRowHeight_account();
		modelJV_account.setEditable(true);
	}

	public static void creatSLtable(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {// used

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''  union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''  union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''  union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''  union all  \r\n"
				+ "select '', '','',false, 0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   union all  \r\n"
				+

				"select '', '', '','false',0.00, 0.00, 0.00, 0.00, '', '', '','', '', '', '', '', '', ''   \r\n";

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalJVSL(modelMain, modelTotal);
		}

		else {
			modelJV_SL_total = new modelJV_SubLedger2();
			modelJV_SL_total
					.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00),
							new BigDecimal(0.00), null, null, null, null, null, null, null, null, null, null });

			tblJV_SLtotal = new _JTableTotal(modelJV_SL_total, tblJV_SL);
			tblJV_SLtotal.setFont(dialog11Bold);
			scrollJV_SL.setViewportView(tblJV_SLtotal);
			((_JTableTotal) tblJV_SLtotal).setTotalLabel(0);
		}

		setTableWidth();

		adjustRowHeight_account();
		modelJV_SL.setEditable(true);
	}

	public static void setJV_header(String req_no) {// used

		Object[] jv_hrd = getJVdetails(req_no);
		System.out.println("lookupFiscalYr==" + jv_hrd[5]);
		tran_desc = (String) jv_hrd[9];

		dteTransaction.setDate((Date) jv_hrd[1]);
		dteEdited.setDate((Date) jv_hrd[2]);
		dtePosted.setDate((Date) jv_hrd[3]);
		lookupTranType.setText((String) jv_hrd[4]);
		lookupFiscalYr.setText(((Integer) jv_hrd[5]).toString());
		lookupPeriod.setText((String) jv_hrd[6]);
		txtStatus.setText((String) jv_hrd[7]);
		txtJV_Remark.setText((String) jv_hrd[8]);
		tagTranType.setTag(tran_desc);

		String period = (String) jv_hrd[6];
		posted_by = (String) jv_hrd[10];

		if (period.equals("01")) {
			tagPeriod.setTag("JANUARY");
		} else if (period.equals("02")) {
			tagPeriod.setTag("FEBRUARY");
		} else if (period.equals("03")) {
			tagPeriod.setTag("MARCH");
		} else if (period.equals("04")) {
			tagPeriod.setTag("APRIL");
		} else if (period.equals("05")) {
			tagPeriod.setTag("MAY");
		} else if (period.equals("06")) {
			tagPeriod.setTag("JUNE");
		} else if (period.equals("07")) {
			tagPeriod.setTag("JULY");
		} else if (period.equals("08")) {
			tagPeriod.setTag("AUGUST");
		} else if (period.equals("09")) {
			tagPeriod.setTag("SEPTEMBER");
		} else if (period.equals("10")) {
			tagPeriod.setTag("OCTOBER");
		} else if (period.equals("11")) {
			tagPeriod.setTag("NOVEMBER");
		} else if (period.equals("12")) {
			tagPeriod.setTag("DECEMBER");
		} else if (period.equals("13")) {
			tagPeriod.setTag("13th MONTH");
		} else if (period.equals("14")) {
			tagPeriod.setTag("14th MONTH");
		} else if (period.equals("15")) {
			tagPeriod.setTag("15th MONTH");
		} else if (period.equals("99")) {
			tagPeriod.setTag("CLOSING PERIOD");
		} else {
			tagPeriod.setTag("");
		}

		String status = txtStatus.getText().trim();
		if (status.equals("ACTIVE")) {
			enableButtons(false, true, true, true, true, false, true, false, false, true);
		} else if (status.equals("TAGGED")) {
			enableButtons(false, false, true, true, true, false, false, true, true, false);
		} else if (status.equals("POSTED")) {
			enableButtons(false, false, true, true, true, false, false, false, false, false);
		} else {
			enableButtons(false, false, false, true, true, false, false, false, false, false);
		}

	}

	public static void reversePV(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String pv_no, String coID) {

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "---------reversePV \r\n" + "select * from view_reverse_pv ('" + coID
				+ "','','','','" + pv_no + "') ";

		System.out.printf("SQL #2: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalJV(modelMain, modelTotal);
		}

		else {
			modelJV_accounttotal = new modelJV_acct_entries();
			modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null,
					new BigDecimal(0.00), new BigDecimal(0.00), null });

			tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
			tblJV_accounttotal.setFont(dialog11Bold);
			scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
			((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);
		}

		setTableWidth();
		adjustRowHeight_account();

	}
	//Added by Erick DCRF # 2118 to create jv_sl during reversal of PV
	public static void reversePV_SL(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String pv_no, String coID) {

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select a.entity_id,\n" + 
				"a.entity_type_id, g.wtax_rate,\n" + 
				"(case when coalesce(a.vat_amt,0) = 0 then false else true end) as vatable,\n" + 
				"(coalesce(a.amount,0) *-1 ) as tran_amount,\n" + 
				"((coalesce(a.amount,0) *-1) - (coalesce(a.vat_amt,0) *-1 ) *-1 ) as net_amount,\n" + 
				"coalesce(a.vat_amt,0) * -1 as vat_amt,\n" + 
				"coalesce(a.wtax_amt,0) * -1 as wtax_amt,\n" + 
				"a.acct_id,\n" + 
				"a.project_id,\n" + 
				"a.sub_projectid,\n" + 
				"a.div_id,\n" + 
				"a.dept_id,\n" + 
				"is_taxpaidbyco,\n" + 
				"b.acct_name,\n" + 
				"c.proj_name,\n" + 
				"trim(d.entity_name),\n" + 
				"trim(e.entity_type_desc)\n" + 
				
				"from rf_request_detail a\n" + 
				"left join mf_boi_chart_of_accounts b on a.acct_id=b.acct_id\n" + 
				"left join mf_project c on a.project_id = c.proj_id\n" + 
				"left join rf_entity d on a.entity_id = d.entity_id\n" + 
				"left join mf_entity_type e on a.entity_type_id = e.entity_type_id\n" + 
				"left join mf_sub_project f on a.sub_projectid = f.sub_proj_id and a.project_id = f.proj_id and f.status_id ='A'\n" + 
				"left join rf_withholding_tax g on e.wtax_id = g.wtax_id\n" + 
				"where a.rplf_no = '"+pv_no+"' and a.co_id = '"+coID+"' and a.status_id = 'A'\n" + 
				"order by a.line_no ";

		System.out.printf("ReversePV_SL: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalJVSL(modelMain, modelTotal);
		}

		else {
			
			modelJV_SL_total = new modelJV_SubLedger2();
			modelJV_SL_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null,
					null, null, null, null, null });

			tblJV_SLtotal = new _JTableTotal(modelJV_SL_total, tblJV_SL);
			tblJV_SLtotal.setFont(dialog11Bold);
			scrollJV_SLtotal.setViewportView(tblJV_SLtotal);
			((_JTableTotal) tblJV_SLtotal).setTotalLabel(0);
		}

		setTableWidth();
		adjustRowHeight_account();

	}

	public static void displayJV_subsidiaryledgers(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal, String jv_no) {// used

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.
		/*
		 * String sql =
		 * 
		 * "select * from view_jv_subsidiary_ledger_manual('"+jv_no+"')";
		 * 
		 * pgSelect db = new pgSelect(); db.select(sql);
		 * 
		 * if(db.isNotNull()){ for(int x=0; x < db.getRowCount(); x++){ // Adding of row
		 * in table modelMain.addRow(db.getResult()[x]);
		 * listModel.addElement(modelMain.getRowCount()); }
		 * 
		 * totalJVSL(modelMain, modelTotal); }
		 */

		// Comment by Erick

		// Commented by Lester 2020-06-30
		System.out.println("DisplayJV_subsidiaryledgers");
		if (jv_sl(jv_no, co_id) == true) {

			String sql =

					"select * from view_jv_subsidiary_ledger_manual_v3('" + jv_no + "','" + co_id + "')";
			// "select * from view_jv_subsidiary_ledger_manual_v2('" + jv_no + "','" + co_id
			// + "')";
			// "select * from view_jv_subsidiary_ledger_liq('"+co_id+"','"+jv_no+"')";

			pgSelect db = new pgSelect();
			db.select(sql);
			System.out.println("");
			System.out.println("select * from view_jv_subsidiary_ledger_manual_v3('" + jv_no + "','" + co_id + "')");
			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					// Adding of row in table
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}

				// totalJVSL(modelMain, modelTotal);
			}
			totalJVSL(modelMain, modelTotal);

			System.out.println("Dumaan dito sa else");
			System.out.println("");
			System.out.println("rowcount: " + modelMain.getRowCount());

		} else {
			String sql =

					"select * from view_jv_subsidiary_ledger_liq_v2('" + co_id + "','" + jv_no + "')";
			// "select * from view_jv_subsidiary_ledger_liq('" + co_id + "','" + jv_no +
			// "')";
			// "select * from view_jv_subsidiary_ledger_manual('"+jv_no+"')";--Pinagpalit
			// muna

			System.out.println("");
			System.out.println("select * from view_jv_subsidiary_ledger_liq_v2('" + co_id + "','" + jv_no + "')");
			pgSelect db = new pgSelect();
			db.select(sql);

			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					// Adding of row in table
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}

				// totalJVSL(modelMain, modelTotal);

			}
			totalJVSL(modelMain, modelTotal);
			System.out.println("Dumaan dito sa false");
		} // Commented by Lester 2020-06-30

		adjustRowHeight_SL();
		tblJV_SL.packAll();
	}

	// Enable/Disable all components inside JPanel
	public static void enable_fields(Boolean x) {//

		lblJV_no.setEnabled(x);
		lookupJV.setEnabled(x);
		// lookupJV.setEditable(x);
		lblStatus.setEnabled(x);
		txtStatus.setEnabled(x);

		lblDateTrans.setEnabled(x);
		dteTransaction.setEnabled(x);
		lblDateEdited.setEnabled(x);
		dteEdited.setEnabled(x);
		lblDatePosted.setEnabled(x);
		dtePosted.setEnabled(x);

		lblTransType.setEnabled(x);
		lblFiscalyear.setEnabled(x);
		lblPeriod.setEnabled(x);

		lookupTranType.setEnabled(x);
		// lookupTranType.setEditable(x);
		lookupFiscalYr.setEnabled(x);
		// lookupFiscalYr.setEditable(x);
		lookupPeriod.setEnabled(x);
		// lookupPeriod.setEditable(x);

		tagTranType.setEnabled(x);
		tagPeriod.setEnabled(x);
		txtJV_Remark.setEditable(x);

		mniaddrow.setEnabled(x);
		mnidelete.setEnabled(x);
		mnicopy.setEnabled(x);
		mnicopyRow.setEnabled(x);
	}

	public static void refresh_fields() {// used

		lookupJV.setValue("");
		txtStatus.setText("");

		dteTransaction.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteEdited.setDate(null);
		dtePosted.setDate(null);

		lookupTranType.setValue("");
		lookupFiscalYr.setValue("");
		lookupPeriod.setValue("");

		txtJV_Remark.setText("");
		tagTranType.setText("[ ]");
		tagPeriod.setText("[ ]");
	}

	public static void refresh_tablesMain() {// used

		// reset table 1
		FncTables.clearTable(modelJV_account);
		FncTables.clearTable(modelJV_accounttotal);
		rowHeaderJV_account = tblJV_account.getRowHeader22();
		scrollJV_account.setRowHeaderView(rowHeaderJV_account);
		modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null,
				new BigDecimal(0.00), new BigDecimal(0.00), null });

		// reset table 2
		FncTables.clearTable(modelJV_SL);
		FncTables.clearTable(modelJV_SL_total);
		rowHeaderJV_SL = tblJV_SL.getRowHeader22();
		scrollJV_SL.setRowHeaderView(rowHeaderJV_SL);
		modelJV_SL_total.addRow(
				new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
						new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null, null });

	}

	public static void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, // used
			Boolean f, Boolean g, Boolean h, Boolean i, Boolean j) {

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

	public void initialize_comp() {

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();

		lblJV_no.setEnabled(true);
		lookupJV.setEnabled(true);

		lookupJV.setLookupSQL(getJV_no());

		{
			enableButtons(true, false, false, false, false, false, false, false, false, false);
		}

		lookupCompany.setValue(co_id);
	}

	// action performed
	@Override
	public void actionPerformed(ActionEvent e) {// used

		if (e.getActionCommand().equals("Refresh")) {
			refresh();
		} // ok

		if (e.getActionCommand().equals("Cancel")) {
			cancel();
			tagDetail.setText(null);
		} // ok

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == true) {
			add();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to create a new JV.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Add Acct") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == true) {
			addacct();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Add Acct")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to create a new JV.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Minus Acct")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == true) {
			minusacct();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Minus Acct")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to create a new JV.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == true) {
			edit();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Edit")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to edit JV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == true) {
			saveEntries();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Save")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to post JV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Tag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == true) {
			tagJV();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Tag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to tag JV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		/*
		 * COMMENTED BY JED 2021-12-02 DCRF NO. 1875 : UNTAGGING OF PAYMENTS IS ADDED IN
		 * FAD PROCESS
		 */
//		if (e.getActionCommand().equals("Untag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == true) {
//			untagJV();
//			tagDetail.setText(null);
//		} // ok
//		else if (e.getActionCommand().equals("Untag")
//				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4") == false) {
//			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to tag JV.", "Information",
//					JOptionPane.INFORMATION_MESSAGE);
//		}

		if (e.getActionCommand().equals("Untag") && FncAcounting.EmpCanUntag(UserInfo.EmployeeCode, "4") == true) {
			untagJV();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Untag")
				&& FncAcounting.EmpCanUntag(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to tag JV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Post") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "4") == true) {
			postJV();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Post")
				&& FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to post JV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Delete") && FncAcounting.EmpCanDelete(UserInfo.EmployeeCode, "4") == true) {
			deleteJV();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Delete")
				&& FncAcounting.EmpCanDelete(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to delete JV.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "4") == true) {
			preview();
			tagDetail.setText(null);
		} else if (e.getActionCommand().equals("Preview")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "4") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to preview/print JV.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void mouseClicked(MouseEvent evt) {
		// int row = tblJV_SL.convertRowIndexToModel(tblJV_SL.getSelectedRow());
		int c = tblJV_SL.getSelectedColumn();
		int r = tblJV_SL.getSelectedRow();
		if( c == 8 && c == 9 && (evt.getClickCount() >= 1) && table.equals("tbl_account") ) {
			clickTableColumn_account();
		}else if ((evt.getClickCount() >= 2) && table.equals("tbl_account")) {
			clickTableColumn_account();
		}

//		if ((evt.getClickCount() >= 2) && table.equals("tbl_account")) {
//			clickTableColumn_account();
//		}
		if ((evt.getClickCount() >= 2) && table.equals("tbl_SL")) {
			clickTableColumn_SL();

		}

		if (evt.getSource().equals(tblJV_SL) && c == 3 && modelJV_SL.isEditable()) { // Column Vatable Entity
			System.out.println("Dumaan sa autocompute 3");
			int row = tblJV_SL.getSelectedRow();
			for (int x = 0; x < modelJV_SL.getRowCount(); x++) {
				Boolean isSelected = (Boolean) modelJV_SL.getValueAt(row, 3);
				double tran_amt = Double.parseDouble(modelJV_SL.getValueAt(row, 4).toString());
				BigDecimal tran_amt_bd = new BigDecimal(tran_amt);
				double wtax_rate = Double.parseDouble(modelJV_SL.getValueAt(row, 2).toString()) / 100;
				System.out.println("row: " + row);

				if (isSelected && tran_amt != 0.00) {
					System.out.println("Dumaan sa autocompute 3 isSelected");

					modelJV_SL.setValueAt(autocompute_VatAmount_SL(tran_amt, 0.12), row, 6); // set Vat amt
					// Compute netofvat
					System.out.println("Net Amount :"
							+ new BigDecimal(tran_amt - autocompute_VatAmount_SL(tran_amt, 0.12).doubleValue()));

					modelJV_SL.setValueAt(
							new BigDecimal(tran_amt - autocompute_VatAmount_SL(tran_amt, 0.12).doubleValue()), row, 5);

					double new_netofvat = Double.parseDouble(modelJV_SL.getValueAt(row, 5).toString());
					// recompute wtax if vatable entity is checked
					modelJV_SL.setValueAt(autocompute_WtaxAmount(new_netofvat, 0.00, wtax_rate).doubleValue(), row, 7);

				} else {
					modelJV_SL.setValueAt(new BigDecimal(0.00), row, 6);
					modelJV_SL.setValueAt(modelJV_SL.getValueAt(row, 4), row, 5);
					System.out.println("netamt: " + modelJV_SL.getValueAt(row, 4));
					// recompute wtax when vatable entity is unchecked
					double wtax_amt = autocompute_WtaxAmount(tran_amt, 0.00, wtax_rate).doubleValue();
					BigDecimal wtax_amt_bd = new BigDecimal(wtax_amt);
					modelJV_SL.setValueAt(wtax_amt_bd, row, 7);
				}
				//Comment by Erick 2023-08-03
				totalJVSL(modelJV_SL, modelJV_SL_total);
			}
		}

		// computeJVSL_amount();
		// totalJVSL(modelJV_SL, modelJV_SL_total);
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

	private void autosetnetofvat() {

		System.out.println("Dumaan sa autosetnetofvat");
		double netofvat = 0.00;
		double tran_amt = 0.00;
		String entity_id = (String) modelJV_SL.getValueAt(0, 0);

		if (entity_id == null) {

			modelJV_SL.setValueAt(new BigDecimal(0.00), 0, 5);

		} else {
			netofvat = tran_amt;
			modelJV_SL.setValueAt(netofvat, 0, 5);
		}
	}

	private static String getCurrentYear() {
		pgSelect db = new pgSelect();
		db.select("SELECT EXTRACT(YEAR FROM now())::VARCHAR");

		if (db.getResult() != null) {
			return db.Result[0][0].toString();
		} else {
			return String.format("%s", getCurrentYear());
		}
	}

	private void refresh() {// used

		jv_no = lookupJV.getText().trim();
		setJV_header(jv_no);
		displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, jv_no);
		displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);

		String status = txtStatus.getText().trim();
		if (status.equals("ACTIVE")) {
			enableButtons(false, true, true, true, true, false, true, false, false, true);
		} else if (status.equals("TAGGED")) {
			enableButtons(false, false, true, true, true, false, false, true, true, false);
		} else if (status.equals("POSTED")) {
			enableButtons(false, false, true, true, true, false, false, false, false, false);
		} else {
			enableButtons(false, false, false, true, true, false, false, false, false, false);
		}

		mnidelete.setEnabled(false);
		mniaddrow.setEnabled(false);
		btnAddAcct.setEnabled(true);

		JOptionPane.showMessageDialog(null, "Journal Voucher details refreshed.", "Information",
				JOptionPane.INFORMATION_MESSAGE);

		tabCenter.setSelectedIndex(0);

		tagDetail.setText(null);

	}

	public static void cancel() {// used
		if (!btnSave.isEnabled()) {
			enable_fields(false);
			refresh_tablesMain();
			refresh_fields();
			lookupCompany.setEnabled(true);
			{
				enableButtons(true, false, false, false, false, false, false, false, false, false);
			}
			lblJV_no.setEnabled(true);
			lookupJV.setEnabled(true);
			tabCenter.setSelectedIndex(0);
			modelJV_account.setEditable(false);
			mnipaste.setEnabled(false);
			mnipasteRow.setEnabled(false);
			btnAddAcct.setEnabled(false);
		} else {
			if (JOptionPane.showConfirmDialog(null, "Cancel unsaved data?", "Cancel", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				enable_fields(false);
				refresh_tablesMain();
				refresh_fields();
				lookupCompany.setEnabled(true);
				{
					enableButtons(true, false, false, false, false, false, false, false, false, false);
				}
				lblJV_no.setEnabled(true);
				lookupJV.setEnabled(true);
				tabCenter.setSelectedIndex(0);
				modelJV_account.setEditable(false);
				mnipaste.setEnabled(false);
				mnipasteRow.setEnabled(false);
				btnAddAcct.setEnabled(false);
			} else {
			}
		}
	}

	public static void add() {// used

		refresh_tablesMain();
		lblJV_no.setEnabled(false);
		lookupJV.setEnabled(false);
		lookupCompany.setEnabled(false);
		txtStatus.setText("ACTIVE");

		{
			enableButtons(false, false, false, false, true, true, false, false, false, false);
		}

		lblTransType.setEnabled(true);
		lookupTranType.setEnabled(true);
		tagTranType.setEnabled(true);
		lblPeriod.setEnabled(true);
		lookupPeriod.setEnabled(true);
		tagPeriod.setEnabled(true);
		dteTransaction.setEnabled(true);
		lblDateTrans.setEnabled(true);
		txtJV_Remark.setEditable(true);
		txtJV_Remark.setEnabled(true);
		btnAddAcct.setEnabled(true);
		btnRemoveAcct.setEnabled(true);

		// start - as requested by Rochelle (02-04-2016)
		// lblFiscalyear.setEnabled(true);
		// lookupFiscalYr.setEnabled(true);
		// end

		lookupTranType.setLookupSQL(getAcctTrans());
		lookupPeriod.setLookupSQL(getPeriod());

		Object[] month_year = getMonthYear();
		lookupFiscalYr.setText((String) month_year[4]);
		lookupPeriod.setText((String) month_year[0]);
		tagPeriod.setTag(((String) month_year[3]).toUpperCase());

		creatJVtable(modelJV_account, rowHeaderJV_account, modelJV_accounttotal);
		creatSLtable(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total);

		mnidelete.setEnabled(true);
		mniaddrow.setEnabled(true);
		mnicopy.setEnabled(true);
		mnicopyRow.setEnabled(true);

		if (isYearMonthOpen((String) month_year[4], (String) month_year[0]) == true) {
		} else {
			JOptionPane.showMessageDialog(null,
					"Year [" + (String) month_year[4] + "] ; " + "Month [" + (String) month_year[0] + "] is closed."
							+ "\n" + "Please ask your Department Head to open.",
					"Information", JOptionPane.WARNING_MESSAGE);
		}

		dteTransaction.setEditable(true);

		// modelJV_SL.setEditable(true);
		// modelJV_SL.addRow(new Object[] { null, null, null, null, null, null, "", "",
		// "", "", null, null, null, null, null});

	}

	public void addacct() {// used

		int row = tblJV_account.getSelectedRow();
		if (row == 0) {
			JOptionPane.showMessageDialog(getContentPane(), "First row can only have a value of 1.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}

		else {
			if (checkNetAmount() == false) {
				JOptionPane.showMessageDialog(getContentPane(), "Zero balance", "ERROR", JOptionPane.ERROR_MESSAGE);
			}

			else {

				if (checkDebitCreditAmount_entry() == true) {
					if (checkAcctBalanceIfEqual() == false) {
						JOptionPane.showMessageDialog(getContentPane(),
								"Debit and Credit balance totals are not equal.", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else {

						if (checkDebitCreditAmount_last_entry() == false) {
							JOptionPane.showMessageDialog(getContentPane(), "Subtotals not equal.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						} else {
							modelJV_account.setValueAt(getPreviousEntryNo(), row, 15);
						}
					}
				} else {
					{
						JOptionPane.showMessageDialog(getContentPane(),
								"Subtotals of Debit and Credit \n" + "	are not equal. ", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	public void minusacct() {// used

		int row = tblJV_account.getSelectedRow();
		/*
		 * int seqno = Integer.parseInt(modelJV_account.getValueAt(row,15).toString());
		 * 
		 * if(row==0) { JOptionPane.showMessageDialog(getContentPane(),
		 * "First row can only have a value of 1.", "Information",
		 * JOptionPane.WARNING_MESSAGE); }
		 * 
		 * else { if (checkNetAmount()==false) {
		 * JOptionPane.showMessageDialog(getContentPane(), "Zero balance",
		 * "Information", JOptionPane.WARNING_MESSAGE); }
		 * 
		 * else { if(checkAcctBalanceIfEqual()==false)
		 * {JOptionPane.showMessageDialog(getContentPane(),
		 * "Debit and Credit balance totals are not equal", "Information",
		 * JOptionPane.WARNING_MESSAGE);} else {
		 * modelJV_account.setValueAt(getPreviousEntryNo(), row, 15); } } }
		 */
		modelJV_account.setValueAt(0, row, 15);
	}

	private void edit() {// used

		lblJV_no.setEnabled(true);
		lookupJV.setEnabled(true);
		// lookupJV.setEditable(false);
		lookupCompany.setEnabled(false);
		txtStatus.setText("ACTIVE");

		{
			enableButtons(false, false, false, false, true, true, false, false, false, false);
		}

		lblTransType.setEnabled(true);
		lookupTranType.setEnabled(false);
		// lookupTranType.setEditable(true);
		tagTranType.setEnabled(true);
		lblPeriod.setEnabled(true);
		lookupPeriod.setEnabled(true);
		// lookupPeriod.setEditable(true);
		tagPeriod.setEnabled(true);
		dteTransaction.setEnabled(true);
		lblDateTrans.setEnabled(false);
		txtJV_Remark.setEditable(true);
		txtJV_Remark.setEnabled(true);

		lookupTranType.setLookupSQL(getAcctTrans());
		lookupPeriod.setLookupSQL(getPeriod());

		mnidelete.setEnabled(true);
		mniaddrow.setEnabled(true);
		mnicopy.setEnabled(true);
		mnicopyRow.setEnabled(true);
		btnAddAcct.setEnabled(true);

		dteTransaction.setEditable(true);
		// Added by Erick 05/10/19

		if (txtStatus.getText().equals("ACTIVE")) {
			modelJV_account.setEditable(true);
			modelJV_SL.setEditable(true);

			if (modelJV_SL.getRowCount() == 0) {

				System.out.println("");
				System.out.println("getRowCount= " + modelJV_SL.getRowCount());

				modelJV_SL
						.addRow(new Object[] { "", "", new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
								new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), "", "", "", "", "" });
				totalJVSL(modelJV_SL, modelJV_SL_total);
				((DefaultListModel) rowHeaderJV_SL.getModel()).addElement(modelJV_SL.getRowCount());
			} else {
				tblJV_SL.setEnabled(true);
			}

		} else {
			modelJV_account.setEditable(false);
			modelJV_SL.setEditable(false);

		}
		// Comment by Ercik
		/*
		 * if (lookupTranType.getText().equals("00011")) {--Comment by Erick Comment by
		 * Erick JOptionPane.showMessageDialog(getContentPane(),
		 * "Please edit the account entries to CA Liquidation module..", "Information",
		 * JOptionPane.WARNING_MESSAGE); modelJV_account.setEditable(false);
		 * modelJV_SL.setEditable(false);
		 * 
		 * } else { modelJV_account.setEditable(true); modelJV_SL.setEditable(true);
		 * 
		 * if (modelJV_SL.getRowCount() == 0) { modelJV_SL.addRow(new Object[] { "", "",
		 * new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), "", "", "",
		 * "", "", "", "", "" }); totalJVSL(modelJV_SL, modelJV_SL_total);
		 * ((DefaultListModel)
		 * rowHeaderJV_SL.getModel()).addElement(modelJV_SL.getRowCount()); } else {
		 * 
		 * } }
		 */

	}

	private void saveEntries() {// used
		
		try {
			System.out.println("isYearMonthOpen:" + isYearMonthOpen(lookupFiscalYr.getText(), lookupPeriod.getText()));
			
			if (isYearMonthOpen(lookupFiscalYr.getText(), lookupPeriod.getText()) == true) {

				if (checkCompleteDetails() == false) {
					JOptionPane.showMessageDialog(getContentPane(), "Please enter transaction type.", "Information",
							JOptionPane.WARNING_MESSAGE);
				} else {

					if (checkPeriod() == true) {
						JOptionPane.showMessageDialog(getContentPane(),
								"Month for Transaction Date and Period is not equal.", "Information",
								JOptionPane.WARNING_MESSAGE);
					}

					else {

						if (checkNetAmount() == false) {
							JOptionPane.showMessageDialog(getContentPane(), "Net JV amount must be greater than zero.",
									"Information", JOptionPane.WARNING_MESSAGE);
						} else {

							if (checkAcctID_ifcomplete() == false) {
								JOptionPane.showMessageDialog(getContentPane(), "Please enter a missing account ID.",
										"Information", JOptionPane.WARNING_MESSAGE);
							}

							else {

								if (checkAcctBalanceIfEqual() == false) {
									JOptionPane.showMessageDialog(getContentPane(),
											"Debit and Credit balance totals are not equal", "Information",
											JOptionPane.WARNING_MESSAGE);
								}

								else {

									if (checkDebitCreditAmount() == false) {
										JOptionPane.showMessageDialog(getContentPane(),
												"Debit and Credit amounts cannot both be > 0", "Information",
												JOptionPane.WARNING_MESSAGE);
									}

									else {

										if (checkCostCenter() == false) {
											if (JOptionPane.showConfirmDialog(getContentPane(),
													"Missing Info - project / subproject, proceed anyway?",
													"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
													JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

												/*
												 * if (vatWtax_amounts_equal()==false) {
												 * JOptionPane.showMessageDialog(getContentPane()
												 * ,"VAT and/or WTax SL Amount not matched.","",
												 * JOptionPane.ERROR_MESSAGE); } else {executeSave(); }
												 */
												if (txtJV_Remark.getText().equals("")
														|| txtJV_Remark.getText().isEmpty()) {
													JOptionPane.showMessageDialog(getContentPane(),
															"Please put remarks on the Particular Tab.", "Information",
															JOptionPane.WARNING_MESSAGE);
												} else {
													// added by Erick Bituen to check vat/wtax amount is equal to SL see
													// dcrf 1325
													// executeSave();
													if (vatWtax_amounts_equal() == false) {

														JOptionPane.showMessageDialog(getContentPane(),
																"VAT and/or WTax SL Amount not matched.", "",
																JOptionPane.ERROR_MESSAGE);

													} else {
														executeSave();
														/*if (checkposted(jv_no, co_id)) {
															JOptionPane.showMessageDialog(getContentPane(),
																	"JV is already posted", "",
																	JOptionPane.ERROR_MESSAGE);
														} else {
															executeSave();
														}*/

													}

												}
											}
										}

										else {
											/*
											 * if (vatWtax_amounts_equal()==false) {
											 * JOptionPane.showMessageDialog(getContentPane()
											 * ,"VAT and/or WTax SL Amount not matched.","", JOptionPane.ERROR_MESSAGE);
											 * } else {executeSave(); }
											 */
											if (txtJV_Remark.getText().equals(null)) {
												JOptionPane.showMessageDialog(getContentPane(),
														"Please put remarks on the Particular Tab.", "Information",
														JOptionPane.WARNING_MESSAGE);
											} else {
												if (vatWtax_amounts_equal() == false) {
													JOptionPane.showMessageDialog(getContentPane(),
															"VAT and/or WTax SL Amount not matched.", "",
															JOptionPane.ERROR_MESSAGE);

												} else {
													executeSave();
													/*if (checkposted(jv_no, co_id)) {
														JOptionPane.showMessageDialog(getContentPane(),
																"JV is already posted", "", JOptionPane.ERROR_MESSAGE);
													} else {
														executeSave();
													}*/
												}
											}
										}
									}
								}
							}
						}
					}
				}
				//}
			} else {
				JOptionPane.showMessageDialog(null,
						"Year [" + lookupFiscalYr.getText() + "] ; " + "Month [" + lookupPeriod.getText()
								+ "] is closed." + "\n" + "Please ask your Department Head to open.",
						"Information", JOptionPane.WARNING_MESSAGE);
			}
		} catch (NullPointerException ex) {
			{
				JOptionPane.showMessageDialog(getContentPane(), "Put zero instead of blank amount", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

	private void tagJV() {// used

		if (JOptionPane.showConfirmDialog(getContentPane(), "Tag Journal Voucher?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String jv = lookupJV.getText().trim();
			pgUpdate db = new pgUpdate();
			tagJV_header(jv, db);
			insertAudit_trail("Tag-Journal Voucher", jv, db);
			db.commit();
			setJV_header(jv);
			displayJV_details(modelJV_SL, rowHeaderJV_account, modelJV_SL_total, jv);
			displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);
			tabCenter.setSelectedIndex(0);
			JOptionPane.showMessageDialog(null, "Journal Voucher (JV) transaction tagged.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

		} else {
		}

	}

	private void untagJV() {// used

		if (JOptionPane.showConfirmDialog(getContentPane(), "Untag Journal Voucher?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String jv = lookupJV.getText().trim();
			pgUpdate db = new pgUpdate();
			untagJV_header(jv, db);
			insertAudit_trail("Untag-Journal Voucher", jv, db);
			db.commit();
			setJV_header(jv);
			displayJV_details(modelJV_SL, rowHeaderJV_account, modelJV_SL_total, jv);
			displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);
			tabCenter.setSelectedIndex(0);
			JOptionPane.showMessageDialog(null, "Journal Voucher (JV) transaction untagged.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

		} else {
		}

	}

	private void postJV() {// used
		Object[] month_year = getMonthYear();
		// lookupPeriod.setText((String) month_year[0]);
		tagPeriod.setTag(((String) month_year[3]).toUpperCase());
		// added by Erick Bituen 11/06/18 check if year and month is open before posting
		if (isYearMonthOpen(lookupFiscalYr.getText(), lookupPeriod.getText()) == true) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Post Journal Voucher?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				/* Added by Erick DCRF 1017 */
				if (checkAcctBalanceIfEqual() == false) {
					JOptionPane.showMessageDialog(getContentPane(), "Debit and Credit balance totals are not equal",
							"Information", JOptionPane.WARNING_MESSAGE);
				} else {
					/*
					 * if (vatWtax_amounts_equal()==false){//Added by Erick 10-19-2020 DCRF 1470
					 * 
					 * JOptionPane.showMessageDialog(getContentPane()
					 * ,"VAT and/or WTax SL Amount not matched.","", JOptionPane.ERROR_MESSAGE);
					 * 
					 * }else { String jv = lookupJV.getText().trim(); pgUpdate db = new pgUpdate();
					 * postJV_header(jv, db); postLiq_header(jv, db);
					 * insertAudit_trail("Post-Journal Voucher", jv, db); db.commit();
					 * setJV_header(jv); displayJV_details(modelJV_SL, rowHeaderJV_account,
					 * modelJV_SL_total, jv); displayJV_subsidiaryledgers(modelJV_SL,
					 * rowHeaderJV_SL, modelJV_SL_total, jv_no ); tabCenter.setSelectedIndex(0);
					 * JOptionPane.showMessageDialog(null,"Journal Voucher (JV) transaction posted."
					 * ,"Information",JOptionPane.INFORMATION_MESSAGE); }
					 */
					String jv = lookupJV.getText().trim();
					pgUpdate db = new pgUpdate();
					postJV_header(jv, db);
					postLiq_header(jv, db);
					insertAudit_trail("Post-Journal Voucher", jv, db);
					db.commit();
					setJV_header(jv);
					displayJV_details(modelJV_SL, rowHeaderJV_account, modelJV_SL_total, jv);
					displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);
					tabCenter.setSelectedIndex(0);
					//Added by Erick 2023-09-04 to automatically close accounting period upon closing TB
					if (lookupTranType.getText().equals("00030") && lookupPeriod.getText().equals("99")) {
						CloseAcctgPeriod(); //Added by Erick 2023-09-04 
					}else {}
					
					JOptionPane.showMessageDialog(null, "Journal Voucher (JV) transaction posted.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"Year [" + (String) month_year[4] + "] ; " + "Month [" + lookupPeriod.getValue() + "] is closed."
							+ "\n" + "Please ask your Department Head to open.",
					"Information", JOptionPane.WARNING_MESSAGE);

		}

	}

	private void deleteJV() {// used
		if (JOptionPane.showConfirmDialog(getContentPane(), "Delete Journal Voucher?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String liq_no = getLiquidationNo();

			if (liq_no.equals("")) {
				executeDelete("");
			} else {
				if (JOptionPane.showConfirmDialog(getContentPane(),
						"A liquidation detail is linked to this JV. " + "Inactivate Liquidation?", "Confirmation",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					executeDelete(liq_no);
				} else {
					executeDelete("");
				}
			}
		} else {
		}
	}

	private void preview() {// used
		String criteria = "Journal Voucher";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		double debit_tot = 0.00;
		double credit_tot = 0.00;
		Double jv_amt = Double.parseDouble(modelJV_accounttotal.getValueAt(0, 8).toString());

		for (int x = 0; x < modelJV_account.getRowCount(); x++) {

			// String acctID = modelJV_account.getValueAt(x,0).toString();
			Double debit = Double.parseDouble(modelJV_account.getValueAt(x, 8).toString());
			Double credit = Double.parseDouble(modelJV_account.getValueAt(x, 9).toString());
			if (debit == 0) {
			} else {
				debit_tot = debit_tot + debit;
			}

			if (credit == 0) {
			} else {
				credit_tot = credit_tot + credit;
			}

		}
		System.out.println("debit_tot= " + debit_tot);
		System.out.println("credit_tot= " + credit_tot);
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		//mapParameters.put("co_id", "02");
		System.out.printf("%s", company_logo);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("logo1", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("jv_no", lookupJV.getText().trim());
		//mapParameters.put("jv_no", "23031517");
		mapParameters.put("due_date", dteEdited.getDate());
		mapParameters.put("jv_date", dteTransaction.getDate());
		mapParameters.put("debit_tot", debit_tot);
		mapParameters.put("credit_tot", credit_tot);
		mapParameters.put("status", txtStatus.getText().trim());
		mapParameters.put("particulars", txtJV_Remark.getText().trim());
		mapParameters.put("posted_by", posted_by);
		mapParameters.put("transaction", tran_desc);
		
		System.out.printf("value of JV No: %s%n", lookupJV.getText().trim());
		System.out.printf("value of co id: %s%n", co_id);

		if (entry() == true) {
			System.out.println("Dumaan dito");
			FncReport.generateReport("/Reports/rptJVmul_preview.jasper", "Journal Voucher - Multiple Entry", company,
					mapParameters);
		} else {
			FncReport.generateReport("/Reports/rptJV_preview.jasper", reportTitle, company, mapParameters);
			
			//FncReport.generateReport("/Reports/rptJV_preview.jasper", "Test Report", mapParameters);
		}

		if (checkIfHasWtax() == true) {
			EWT_Remittance.previewJV(jv_no, co_id);
		}
	}

	private void executeSave() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String jv = lookupJV.getText().trim();
			// int row = tblJV_SL.getSelectedRow();
			// String jv_remarks= txtJV_Remark.getText();
			// jv_remarks= jv_remarks + "";

			if (jv.equals("") || jv.equals(null)) {
				setJV_no();
				pgUpdate db = new pgUpdate();

				// save
				insertJV_header(jv_no, db);
				insertJV_detail(jv_no, db);
				insertSL_detail(jv_no, db);

				// Auto post jv if closing//added by Erick
				if (lookupTranType.getValue().equals("00030") && lookupPeriod.getValue().equals("99")) {
					/*
					 * if (JOptionPane.showConfirmDialog(getContentPane(), "Auto Tag JV?",
					 * "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
					 * JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					 * 
					 * tagJV_header(jv, db); setJV_header(jv); }else{}
					 */

					if (JOptionPane.showConfirmDialog(getContentPane(), " Auto Post Journal Voucher?", "Confirmation",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						postJV_header(jv_no, db);
						CloseAcctgPeriod();

					} else {
					}

					/*if (JOptionPane.showConfirmDialog(getContentPane(),
							"Inactive period transaction for '" + lookupFiscalYr.getText() + "'?", "Confirmation",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						CloseAcctgPeriod(db);

					} else {
					}*/

				} else {
				}

				insertAudit_trail("Add-Journal Voucher", jv_no, db);
				if (writeoff_jv == true) {
					updateWriteoffTable(jv_no, db);
				} // applicable only for Write-Off transaction

				db.commit();
				lookupJV.setValue(jv_no);
				setJV_header(jv_no);
				displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, jv_no);
				displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);
				tabCenter.setSelectedIndex(0);
				JOptionPane.showMessageDialog(null, "New Journal Voucher (JV) transaction saved.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				lookupJV.setValue(jv_no); // ADDED BY LESTER FOR SIR DEL 2017-01-12
			}
			/*
			 * else { if (tabCenter.getSelectedIndex() == 2) { pgUpdate db = new pgUpdate();
			 * 
			 * updateSL_detail(jv, db); insertAudit_trail("Edit-Journal Voucher", jv_no,
			 * db);
			 * 
			 * db.commit(); lookupJV.setText(jv); setJV_header(jv);
			 * displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal,
			 * jv); displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL,
			 * modelJV_SL_total, jv_no ); tabCenter.setSelectedIndex(2);
			 * JOptionPane.showMessageDialog(
			 * null,"Journal Voucher (JV) transaction updated.","Information",JOptionPane.
			 * INFORMATION_MESSAGE); lookupJV.setValue(jv_no); //ADDED BY LESTER FOR SIR DEL
			 * 2017-01-12 }
			 */else {
				 if (checkposted(jv_no, co_id)) {
						JOptionPane.showMessageDialog(getContentPane(),
								"JV is already posted", "", JOptionPane.ERROR_MESSAGE);
					} else {
						pgUpdate db = new pgUpdate();

						// save Edit
						updateJV_header(jv, db);
						updateJV_detail(jv, db);
						updateJV_SL(jv, db);
						insertJV_detail(jv, db);
						insertSL_detail(jv, db);

						// Comment by Erick
						/*
						 * if (lookupTranType.getText().equals("00011")) { updateJV_header(jv, db); }
						 * else { updateJV_header(jv, db); updateJV_detail(jv, db); updateJV_SL(jv, db);
						 * insertJV_detail(jv, db); insertSL_detail(jv, db); }
						 */
						insertAudit_trail("Edit-Journal Voucher", jv_no, db);

						db.commit();
						lookupJV.setText(jv);
						setJV_header(jv);
						displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, jv);
						displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);
						tabCenter.setSelectedIndex(0);
						JOptionPane.showMessageDialog(null, "Journal Voucher (JV) transaction updated.", "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
			}
			// }

			lblJV_no.setEnabled(true);
			lookupJV.setEnabled(true);
			// lookupJV.setEditable(true);

			{
				enableButtons(false, true, true, true, true, false, true, false, false, true);
			}

		} else {
		}
	}

	private void executeDelete(String liq_no) {

		if (JOptionPane.showConfirmDialog(getContentPane(), "This will inactivate tagged transaction(s).",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			String jv = lookupJV.getText().trim();
			pgUpdate db = new pgUpdate();

			deleteJV_header(jv, db);
			deleteJV_SL(jv, db);
			insertAudit_trail("Delete-Journal Voucher", jv, db);
			if (!liq_no.equals("")) {
				inactivateLiquidation(liq_no, db);
			}
			db.commit();

			setJV_header(jv);
			displayJV_details(modelJV_SL, rowHeaderJV_account, modelJV_SL_total, jv);
			displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);
			tabCenter.setSelectedIndex(0);

			String remarks = "";
			if (liq_no.equals("")) {
				remarks = "Journal Voucher (JV) transaction deleted.";
			} else {
				remarks = "Journal Voucher (JV) and Liquidation Details transactions deleted.";
			}

			JOptionPane.showMessageDialog(null, remarks, "Information", JOptionPane.INFORMATION_MESSAGE);
		} else {
		}

	}

	public static void executeReversal() {

		String jv = lookupJV.getText().trim();

		if (jv.equals("") || jv.equals(null)) {
			setJV_no();
			pgUpdate db = new pgUpdate();

			// save
			insertJV_header(jv_no, db);
			insertJV_detail(jv_no, db);
			insertSL_detail(jv_no, db);

			insertAudit_trail("Add-Journal Voucher", jv_no, db);

			db.commit();
			System.out.println("Reversal jv_no: "+jv_no);
			lookupJV.setText(jv_no);
			setJV_header(jv_no);
			displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, jv_no);
			displayJV_subsidiaryledgers(modelJV_SL, rowHeaderJV_SL, modelJV_SL_total, jv_no);
			tabCenter.setSelectedIndex(0);
			JOptionPane.showMessageDialog(null, "New Journal Voucher (JV) transaction saved.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			lookupJV.setValue(jv_no);
		}

		lblJV_no.setEnabled(true);
		lookupJV.setEnabled(true);

		{
			enableButtons(false, true, true, true, true, false, true, false, false, true);
		}

	}

	// select, lookup and get statements
	public static String getJV_no() {// used

		return

		"select \n" + "	trim(a.jv_no) as \"JV No\", \n" + "	a.jv_date as \"JV Date\", \n"
				+ "	f.amount as \"Amount\",\n" + "	a.fiscal_yr as \"Fiscal Year\", \n"
				+ "	a.period_id as \"Period\", \n" + "	c.status_desc as \"Status\", \n"
				+ "	e.entity_name as \"Created By\", \n" + "	a.date_created as \"Date Created\"\n" + "\n"
				+ "	from ( select * from rf_jv_header where co_id = '" + co_id + "' and (case when '" + isCurrentYear
				+ "' = '' then true else to_char(jv_date,'yyyy') = '" + isCurrentYear + "' end))  as a \n"
				+ "	left join mf_record_status c on a.status_id = c.status_id \n"
				+ "	left join em_employee d on a.created_by = d.emp_code \n"
				+ "	left join rf_entity e on d.entity_id = e.entity_id\n"
				+ "	left join (select distinct on (jv_no) jv_no, sum(tran_amt) as amount,co_id from rf_jv_detail \n"
				+ "		where status_id != 'I' and bal_side = 'D' and  co_id = '" + co_id
				+ "' group by jv_no,co_id) f on f.jv_no = a.jv_no\n  and f.co_id=a.co_id" + "	where a.co_id = '"
				+ co_id + "' \n" + "	order by a.jv_no desc";

	}

	private static String getAcctTrans() {// used
		return "select \r\n" + "\r\n" + "a.tran_id as \"Tran ID\",\r\n" + "trim(a.tran_desc)  as \"Tran. Desc.\" ,\r\n"
				+ "a.system_id  as \"System ID\", " + "b.status_desc  as \"Status\" \r\n" + "\r\n"
				+ "from mf_acctg_trans a\r\n" + "left join mf_record_status b on a.status_id=b.status_id\r\n";
	}

	private static String getPeriod() {// Used
		return "select '01' as \"ID\", 'JANUARY' as \"Period Name\" union all "
				+ "select '02' as \"ID\", 'FEBRUARY' as \"Period Name\" union all "
				+ "select '03' as \"ID\", 'MARCH' as \"Period Name\" union all "
				+ "select '04' as \"ID\", 'APRIL' as \"Period Name\" union all "
				+ "select '05' as \"ID\", 'MAY' as \"Period Name\" union all "
				+ "select '06' as \"ID\", 'JUNE' as \"Period Name\" union all "
				+ "select '07' as \"ID\", 'JULY' as \"Period Name\" union all "
				+ "select '08' as \"ID\", 'AUGUST' as \"Period Name\" union all "
				+ "select '09' as \"ID\", 'SEPTEMBER' as \"Period Name\" union all "
				+ "select '10' as \"ID\", 'OCTOBER' as \"Period Name\" union all "
				+ "select '11' as \"ID\", 'NOVEMBER' as \"Period Name\" union all "
				+ "select '12' as \"ID\", 'DECEMBER' as \"Period Name\" union all "
				+ "select '13' as \"ID\", '13TH MONTH' as \"Period Name\" union all "
				+ "select '14' as \"ID\", '14TH MONTH' as \"Period Name\" union all "
				+ "select '15' as \"ID\", '15TH MONTH' as \"Period Name\" union all "
				+ "select '99' as \"ID\", 'CLOSING PERIOD' as \"Period Name\"  ";

	}

	// added by erick 2019-02-28
	public static String getChartofAccount() {
		String sql = "select \n" + "acct_id as \"Acct ID\", \n" + "trim(acct_name) as \"Acct Name\",    \n"
				+ "bs_is as \"Balance\" \n" + " from mf_boi_chart_of_accounts \n" + " where status_id='A' \n" +
				// "and acct_grp_id is not null\n" +//original Filter
				"and w_subacct is null \n" + 
//				"or acct_id in \n" + "( \n" + " '01-03-07-000', \n" + // edited by erick
//																									// 2019-03-05 thru
//																									// dcrf 959 and drcf
//																									// 1123 dated
//																									// 2019-06-27
//				" '07-01-00-000',\n" + // edited by erick 2020-01-31 thru DCRF 1337
//				" '02-04-00-000',\n" + " '04-06-00-000' \n" +
//				", '01-02-02-000'\n"+ //Requested by orly 2023-02-06 Account has balance.
//				// " '09-01-00-000' \n"+ //edited by erick 2019-10-22 thru dcrf 1258 and 126
//				", '08-01-07-000'\n"+ //added by Erick 2023-08-09 as requested by Mam weng and coordinated to orly
//				") \n" +
				"OR filtered = false \n"+ //ADDED BY LESTER DCRF 2719
				// "or acct_id='09-01-00-000' \n"+ //edited by erick 2019-10-22 thru dcrf 1258
				// and 1264
				" order by acct_id ";
		return sql;
	}
	/*
	 * private String getChartofAccount(){//used
	 * 
	 * String sql = "select " + "acct_id as \"Acct ID\", " +
	 * "trim(acct_name) as \"Acct Name\",    " + "bs_is as \"Balance\"  " +
	 * "from mf_boi_chart_of_accounts where status_id = 'A' and w_subacct is null ";
	 * return sql;
	 * 
	 * }
	 */

	private String getDivision() {// used

		String sql = "select division_code as \"Div Code\", " + "trim(division_name) as \"Div Name\", "
				+ "division_alias as \"Div Alias\" " + "from mf_division ";
		return sql;

	}

	public String getDepartment(_JTableMain table, DefaultTableModel model, Integer col_no) {// used

		int row = table.getSelectedRow();
		String div = model.getValueAt(row, col_no).toString();

		String sql = "select dept_code as \"Dept Code\", " + "trim(dept_name) as \"Dept Name\", "
				+ "dept_alias as \"Dept Alias\" " + "from mf_department ";
		if (div.equals("")) {
			sql = sql + "";
		} else {
			sql = sql + "where division_code = '" + div + "' ";
		}

		return sql;

	}

	public String getProject() {// used

		String sql = "select a.proj_id as \"Project ID\", " + "a.proj_name as \"Project Name\", "
				+ "a.proj_alias as \"Project Alias\", " + "b.sub_proj_id as \"SubProject ID\", "
				+ "a.vatable as \"Vatable\" " + "from mf_project a "
				+ "left join ( select distinct on (proj_id) proj_id, sub_proj_id from mf_unit_info where status_id ='A') b  on a.proj_id=b.proj_id "
				+ "where a.co_id = '" + co_id + "' and a.status_id ='A'";

		System.out.println("getProject: " + sql);
		return sql;

	}

	public String getSubproject(_JTableMain table, DefaultTableModel model, Integer col_no) {// used
		int row = table.getSelectedRow();
		String proj = model.getValueAt(row, col_no).toString();
		String sql =
				/*
				 * "select \r\n" + "distinct on (a.proj_id, a.sub_proj_id)\r\n" + "\r\n" +
				 * "a.sub_proj_id  as \"Subproj Code\",\r\n" + "a.phase as \"Phase\",  \r\n" +
				 * "a.proj_id as \"Proj Code\",\r\n" + "b.proj_name as \"Proj Name\"  \r\n" +
				 * 
				 * "from mf_unit_info a\r\n" +
				 * "left join mf_project b on a.proj_id = b.proj_id\r\n" +
				 * "where co_id = '"+co_id+"' and a.proj_id = '"+proj+"'   " +
				 * "and sub_proj_id is not null or sub_proj_id != '' " ;
				 */

				"select \r\n" + "distinct on (a.proj_id, a.sub_proj_id)\r\n" + "\r\n"
						+ "a.sub_proj_id  as \"Subproj Code\",\r\n" + "a.phase as \"Phase\",  \r\n"
						+ "a.proj_id as \"Proj Code\",\r\n" + "b.proj_name as \"Proj Name\"  \r\n" +

						"from mf_sub_project a\r\n" + "left join mf_project b on a.proj_id = b.proj_id and b.status_id='A'\r\n"
						+ "where b.co_id = '" + co_id + "' and a.status_id ='A'";

		System.out.println("getSubproject: " + sql);
		if (proj.equals("") || proj.equals(null)) {
		} else {
			sql = sql + "and a.proj_id = '" + proj + "'   ";
			System.out.println("getSubproject: " + sql);
		}
		return sql;
	}

	public static Object[] getJVdetails(String rec_no) {// used

		String strSQL = "--- Display JV Header\r\n" +

				"select \r\n" + "\r\n" + "a.jv_no,\r\n" + "a.jv_date,\r\n" + "a.date_edited,\r\n" + "a.date_posted, \n"
				+ "a.tran_id,\r\n" + "a.fiscal_yr,\r\n" + "a.period_id,\r\n" + "trim(c.status_desc),\r\n"
				+ "a.remarks, \n" +
				// "a.remarks || E'\n' ||e.entity_name||' '|| d.trans_amt, \n" +
				"b.tran_desc, \n" + "a.posted_by   \n" + "\r\n" + "from rf_jv_header a\r\n"
				+ "left join mf_acctg_trans b on a.tran_id = b.tran_id\r\n"
				+ "left join mf_record_status c on a.status_id = c.status_id  \n" +
				// "left join (select * from rf_subsidiary_ledger where status_id='A')d on
				// a.jv_no=d.jv_no \n" +
				// "left join rf_entity e on d.entity_id=e.entity_id \n" +
				"where trim(a.jv_no) = trim('" + rec_no + "') and a.co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '02' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}

	public static Boolean isYearMonthOpen(String year, String month) {

		Boolean isOpen = false;

		String SQL = "select\r\n" + "\r\n" + "(case when status_id = 'A' then true else false end) as open \r\n"
				+ "from mf_acctg_period\r\n" + "where period_year = '" + year + "'  " +
				// "and month_code = '"+month+"' " +
				"and (case when '" + month + "' = '99'  then month_code = '16' else month_code = '" + month + "' end) "
				+ // Added by Erick 2019-07-03
				"and co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			isOpen = (Boolean) db.getResult()[0][0];
		} else {
			isOpen = false;
		}

		return isOpen;

	}

	// check and validate
	private Boolean checkCompleteDetails() {// used

		boolean x = false;

		String transaction_type;

		transaction_type = lookupTranType.getText();

		if (transaction_type.equals("")) {
			x = false;
		} else {
			x = true;
		}

		return x;

	}

	private Boolean checkPeriod() {// used

		boolean x = false;

		Integer month = (Integer) dteTransaction.getDate().getMonth() + 1;
		Integer per = new Integer(lookupPeriod.getText());
		

		if (per.equals(month) || per.equals(13) || per.equals(14) || per.equals(15) || per.equals(99)) {
			// if (per.equals(month) || per.equals(13) || per.equals(14) || per.equals(15)
			// || per.equals(16)) {
			x = false;
		} else {
			x = true;
		}

		return x;

	}

	private Boolean checkNetAmount() {// used

		boolean x = false;

		Double net_amt = Double.parseDouble(modelJV_accounttotal.getValueAt(0, 8).toString());
		if (net_amt > 0) {
			x = true;
		} else if (net_amt < 0) {
			x = false;
		} else {
			x = false;
		}

		return x;

	}

	private Boolean checkAcctID_ifcomplete() {// used

		boolean x = true;

		int rw = tblJV_account.getModel().getRowCount();
		int w = 0;

		while (w < rw) {
			Double debit = Double.parseDouble(modelJV_account.getValueAt(w, 8).toString());
			Double credit = Double.parseDouble(modelJV_account.getValueAt(w, 9).toString());
			Double total = debit + credit;

			if (total > 0) {

				String acct_id = modelJV_account.getValueAt(w, 0).toString().trim();
				if (acct_id.equals("")) {
					x = false;
					break;
				} else {
				}

			} else {
			}
			w++;
		}
		return x;

	}

	private Boolean checkCostCenter() {// used

		boolean x = true;

		int rw = tblJV_account.getModel().getRowCount();
		int w = 0;

		while (w < rw) {

			String acct_id = tblJV_account.getValueAt(w, 0).toString();

			if (!acct_id.equals("")) {

				String project = "";
				String subproj = "";

				try {
					project = tblJV_account.getValueAt(w, 4).toString();
				} catch (NullPointerException e) {
					project = "";
				}
				try {
					subproj = tblJV_account.getValueAt(w, 5).toString();
				} catch (NullPointerException e) {
					subproj = "";
				}

				if (project.equals("") || subproj.equals("")) {
					x = false;
					break;
				} else {
					x = true;
				}

			} else {
			}

			w++;
		}

		return x;

	}

	private Boolean checkAcctBalanceIfEqual() {// used

		boolean x = true;

		Double debit = Double.parseDouble(modelJV_accounttotal.getValueAt(0, 8).toString());
		Double credit = Double.parseDouble(modelJV_accounttotal.getValueAt(0, 9).toString());
		// Double diff = Double.valueOf(debit.doubleValue()) -
		// Double.valueOf(credit.doubleValue()) ;
		System.out.printf("Debit :" + df.format(debit));
		System.out.printf("Credit :" + df.format(credit));

		// if (diff!=0) { x=false; }
		if (df.format(debit).equals(df.format(credit))) {
			x = true;
		} else {
			x = false;
		}

		return x;

	}

	private Boolean checkDebitCreditAmount() {// used

		boolean x = true;

		int rw = tblJV_account.getModel().getRowCount();
		int w = 0;

		while (w < rw) {

			Double debit = Double.parseDouble(modelJV_account.getValueAt(w, 8).toString());
			Double credit = Double.parseDouble(modelJV_account.getValueAt(w, 9).toString());

			if (debit > 0 && credit > 0) {
				x = false;
				break;
			} else {
			}
			w++;
		}
		return x;

	}

	private Boolean checkIfHasWtax() {

		boolean x = false;

		int rw = tblJV_account.getModel().getRowCount();
		int w = 0;

		while (w < rw) {
			String acct = tblJV_account.getValueAt(w, 0).toString();
			if (acct.equals("03-01-06-002")) {
				x = true;
				break;
			} else {
			}
			w++;
		}
		return x;

	}

	/*
	 * private Boolean checkMatch(){//used
	 * 
	 * boolean x = true; int rw = tblJV_account.getModel().getRowCount(); int w = 0;
	 * 
	 * while (w < rw) {
	 * 
	 * String acct_id = tblJV_account.getValueAt(w,0).toString();
	 * 
	 * if (!acct_id.equals("")) {
	 * 
	 * String project = ""; String subproj = ""; //String div = ""; //String group =
	 * "";
	 * 
	 * try { project = tblJV_account.getValueAt(w,4).toString();} catch
	 * (NullPointerException e) { project = ""; } try { subproj =
	 * tblJV_account.getValueAt(w,5).toString();} catch (NullPointerException e) {
	 * subproj = ""; } //try { div = tblJV_account.getValueAt(w,1).toString();}
	 * catch (NullPointerException e) { project = ""; } //try { group =
	 * tblJV_account.getValueAt(w,2).toString();} catch (NullPointerException e) {
	 * subproj = ""; }
	 * 
	 * if (project.equals("")||subproj.equals("")) { x=false; break; } else
	 * {x=true;}
	 * 
	 * } else {}
	 * 
	 * w++; }
	 * 
	 * return x;
	 * 
	 * }
	 */

	private Integer getPreviousEntryNo() {// used

		Integer x = 0;

		int rw = tblJV_account.getSelectedRow();
		int w = rw - 1;
		// int w = 0;

		while (w >= 0) {

			int seqno = Integer.parseInt(modelJV_account.getValueAt(w, 15).toString());

			if (seqno > 0) {
				x = seqno + 1;
				break;
			} else {
				x = 1;
			}
			w--;
		}

		return x;

	}

	/*
	 * private Integer getMaxEntryNo(){//used
	 * 
	 * Integer x = 0;
	 * 
	 * int rw = tblJV_account.getRowCount(); int w = 0; int seqno_1 = 0;
	 * 
	 * while (w < rw) {
	 * 
	 * int seqno = Integer.parseInt(modelJV_account.getValueAt(w,15).toString()); if
	 * (seqno > seqno_1) { x = seqno+1; } else { x = seqno_1+1; }
	 * 
	 * w++; }
	 * 
	 * System.out.printf("\n" + "x (max): " + x + "\n"); return x;
	 * 
	 * }
	 */

	private Boolean checkDebitCreditAmount_entry() {// used

		Boolean check_entry_balance = true;

		// int rw = tblJV_account.getSelectedRow();
		int rw = tblJV_account.getRowCount();
		int x = 0;

		// int w = rw - 1;
		Double debit = 0.00;
		Double credit = 0.00;
		Double total_prev = 0.00;
		Double total = 0.00;

		for (x = 0; rw < tblJV_account.getRowCount(); x++) {
			// while (x < rw){
			int seqno = Integer.parseInt(modelJV_account.getValueAt(x, 15).toString());

			if (seqno > 0) {

				total = total_prev + debit - credit;
				if (!total.equals(0.00)) {
					System.out.printf("\n" + "debit: " + debit + "\n");
					System.out.printf("credit: " + credit + "\n");
					System.out.printf("\n" + "total: " + total + "\n");
					check_entry_balance = false;
					break;
				}

				debit = 0.00;
				credit = 0.00;

				Double debit_no = Double.parseDouble(modelJV_account.getValueAt(x, 8).toString());
				Double credit_no = Double.parseDouble(modelJV_account.getValueAt(x, 9).toString());

				debit = debit + debit_no;
				credit = credit + credit_no;
				total_prev = debit - credit;
			}

			else if (seqno == 0) {
				Double debit_no = Double.parseDouble(modelJV_account.getValueAt(x, 8).toString());
				Double credit_no = Double.parseDouble(modelJV_account.getValueAt(x, 9).toString());

				debit = debit + debit_no;
				credit = credit + credit_no;
				total_prev = debit - credit;

				System.out.printf("\n" + "debit: " + debit + "\n");
				System.out.printf("credit: " + credit + "\n");
			}

			// x++;
		}

		return check_entry_balance;

	}

	private Boolean checkDebitCreditAmount_last_entry() {// used

		Boolean check_entry_balance = true;

		int rw = tblJV_account.getSelectedRow();
		int w = rw - 1;
		Double debit = 0.00;
		Double credit = 0.00;
		Double total = 0.00;

		for (w = rw - 1; w >= 0; w--) {
			// while (w >= 0){--Edited by Erick 07-10 dcrf 1126
			int seqno = Integer.parseInt(modelJV_account.getValueAt(w, 15).toString());

			if (seqno > 0) {
				Double debit_no = Double.parseDouble(modelJV_account.getValueAt(w, 8).toString());
				Double credit_no = Double.parseDouble(modelJV_account.getValueAt(w, 9).toString());

				debit = debit + debit_no;
				credit = credit + credit_no;
				total = (double) (Math.round(debit) - Math.round(credit));
				// total = debit - credit;

				System.out.printf("\n" + "debit: " + debit + "\n");
				System.out.printf("credit: " + credit + "\n");

			} else {
				Double debit_no = Double.parseDouble(modelJV_account.getValueAt(w, 8).toString());
				Double credit_no = Double.parseDouble(modelJV_account.getValueAt(w, 9).toString());

				debit = debit + debit_no;
				credit = credit + credit_no;
				total = (double) (Math.round(debit) - Math.round(credit));
				// total = (double) (Math.round(debit) - Math.round(credit));

				System.out.printf("\n" + "debit: " + debit + "\n");
				System.out.printf("credit: " + credit + "\n");

			}

			// w--;
			// break;
		}

		if (!total.equals(0.00)) {
			System.out.printf("\n" + "total: " + total + "\n");
			check_entry_balance = false;
		}

		return check_entry_balance;

	}

	// table operations
	public static void totalJV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// used

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal debit = new BigDecimal(0.00);
		BigDecimal credit = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				debit = debit.add(((BigDecimal) modelMain.getValueAt(x, 8)));
			} catch (NullPointerException e) {
				debit = debit.add(new BigDecimal(0.00));
			}

			try {
				credit = credit.add(((BigDecimal) modelMain.getValueAt(x, 9)));
			} catch (NullPointerException e) {
				credit = credit.add(new BigDecimal(0.00));
			}
		}
		modelTotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null, debit, credit });
	}

	public static void totalJVSL(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// used

		System.out.println("Dumaan sa totalJVSL");
		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		/*
		 * BigDecimal vat = new BigDecimal(0.00); BigDecimal wtax = new
		 * BigDecimal(0.00); BigDecimal tran_amt = new BigDecimal(0.00); BigDecimal
		 * net_amt = new BigDecimal(0.00);
		 */
		Double vat_amt = 0.00;
		Double wtax_amt = 0.00;
		Double tran_amt = 0.00;
		Double net_amt = 0.00;

		for (int x = 0; x < modelMain.getRowCount(); x++) {
			Double vat = 0.00;
			Double wtax = 0.00;
			Double tran = 0.00;
			Double net = 0.00;

			try {
				tran = Double.parseDouble(modelMain.getValueAt(x, 4).toString().trim());
			} catch (NullPointerException e) {
				tran = 0.00;
			}
			try {
				net = Double.parseDouble(modelMain.getValueAt(x, 5).toString().trim());
			} catch (NullPointerException e) {
				net = 0.00;
			}
			try {
				vat = Double.parseDouble(modelMain.getValueAt(x, 6).toString().trim());
			} catch (NullPointerException e) {
				vat = 0.00;
			}
			try {
				wtax = Double.parseDouble(modelMain.getValueAt(x, 7).toString().trim());
			} catch (NullPointerException e) {
				wtax = 0.00;
			}

			// System.out.println("");
			// System.out.println("net = "+net);

			tran_amt = tran_amt + tran;
			net_amt = net_amt + net;
			vat_amt = vat_amt + vat;
			wtax_amt = wtax_amt + wtax;

			/*
			 * try { tran_amt = tran_amt.add(((BigDecimal) modelMain.getValueAt(x, 4))); }
			 * catch (NullPointerException e) { tran_amt = tran_amt.add(new
			 * BigDecimal(0.00)); System.out.println("tran_amt:%s%" + tran_amt); } try {
			 * net_amt = net_amt.add(((BigDecimal) modelMain.getValueAt(x, 5))); } catch
			 * (NullPointerException e) { net_amt = net_amt.add(new BigDecimal(0.00)); }
			 * 
			 * try { vat = vat.add(((BigDecimal) modelMain.getValueAt(x, 6))); } catch
			 * (NullPointerException e) { vat = vat.add(new BigDecimal(0.00)); }
			 * 
			 * try { wtax = wtax.add(((BigDecimal) modelMain.getValueAt(x, 7))); } catch
			 * (NullPointerException e) { wtax = wtax.add(new BigDecimal(0.00)); }
			 */

		}
		BigDecimal tran_amt_bd = new BigDecimal(tran_amt);
		BigDecimal net_amt_bd = new BigDecimal(net_amt);
		BigDecimal vat_amt_bd = new BigDecimal(vat_amt);
		BigDecimal wtax_amt_bd = new BigDecimal(wtax_amt);

		modelTotal.addRow(new Object[] { "Total", null, null, null,tran_amt_bd, net_amt_bd, vat_amt_bd, wtax_amt_bd });

		System.out.println("tran_amt_total: " + tran_amt_bd);
		System.out.println("net_amt_total: " + net_amt_bd);
		System.out.println("vat_amt_total: " + vat_amt_bd);
		System.out.println("wtax_amt_total: " + wtax_amt_bd);
		System.out.println("modelTotalrow " + modelTotal.getRowCount());
	}

	private void computeJV_amount() {// used

		int rw = tblJV_account.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			Double debit = Double.parseDouble(modelJV_account.getValueAt(x, 8).toString());
			Double credit = Double.parseDouble(modelJV_account.getValueAt(x, 9).toString());

			BigDecimal debit_bd = new BigDecimal(debit);
			BigDecimal credit_bd = new BigDecimal(credit);

			modelJV_account.setValueAt(debit_bd, x, 8);
			modelJV_account.setValueAt(credit_bd, x, 9);
			x++;
		}
		totalJV(modelJV_account, modelJV_accounttotal);
	}

	private void computeJVSL_amount() {// used

		System.out.println("Dumaan sa computeJVSL_amount evt.");
		int rw = tblJV_SL.getModel().getRowCount();
		// int x = 0;
		int row = tblJV_SL.getSelectedRow();
		System.out.println("selected row: " + row);
		double vat_rate = 0.00;
		Boolean isSelected = false;
		if (tblJV_SL.getSelectedRows().length == 1) {
			
			int selected_row = tblJV_SL.getSelectedRow();
			Double trans =  (Double) modelJV_SL.getValueAt(selected_row, 4);
			Double vat = Double.parseDouble(modelJV_SL.getValueAt(selected_row, 6).toString());
			isSelected = (Boolean) modelJV_SL.getValueAt(selected_row, 3);
			
			System.out.println("selected_row: " + selected_row);
			System.out.println("value of column 3: " + modelJV_SL.getValueAt(selected_row, 3));
			System.out.println("Tran Amount: "+ modelJV_SL.getValueAt(selected_row, 4));

			if (isSelected) {
				vat_rate = 0.12;
			} else {
				vat_rate = 0.00;
			}

			BigDecimal trans_bd = new BigDecimal(trans);
			BigDecimal vat_bd = new BigDecimal(vat);

			modelJV_SL.setValueAt(modelJV_SL.getValueAt(selected_row, 4), selected_row, 4);
			// modelJV_SL.setValueAt(trans_bd, x, 5);

			System.out.println("");
			System.out.println("True");
			System.out.println("Trans :" + trans_bd);
			System.out.println("autocompute_VatAmount_SL :" + autocompute_VatAmount_SL(trans_bd.doubleValue(), vat_rate).doubleValue());
			System.out.println("vat_rate :" + vat_rate);

			modelJV_SL.setValueAt(new BigDecimal(trans.doubleValue() - autocompute_VatAmount_SL(trans_bd.doubleValue(), vat_rate).doubleValue()), row,
					5);
			// modelJV_SL.setValueAt(vat_bd, x, 6);
			modelJV_SL.setValueAt(autocompute_VatAmount_SL(trans_bd.doubleValue(), vat_rate), row, 6);
			double netofvat = Double.parseDouble(modelJV_SL.getValueAt(selected_row, 5).toString());
			double wtax_rate = 0.00;
			try {
				wtax_rate = Double.parseDouble(modelJV_SL.getValueAt(selected_row, 2).toString()) / 100;
			} catch (NumberFormatException e) {
			}
			double wtax_amt = autocompute_WtaxAmount(netofvat, 0.00, wtax_rate).doubleValue();
			BigDecimal wtax_amt_bd = new BigDecimal(wtax_amt);
			modelJV_SL.setValueAt(wtax_amt_bd, selected_row, 7);

		} else {
			// if(tblJV_SL.getSelectedRows().length == 1) {

			/*
			 * for (int x = 0;x < modelJV_SL.getRowCount();x++) {
			 * 
			 * Double trans = Double.parseDouble(modelJV_SL.getValueAt(x, 4).toString());
			 * Double vat = Double.parseDouble(modelJV_SL.getValueAt(x, 6).toString());
			 * isSelected = (Boolean) modelJV_SL.getValueAt(x, 3);
			 * System.out.println("x: "+x);
			 * System.out.println("value of column 3: "+modelJV_SL.getValueAt(x, 3));
			 * 
			 * if(isSelected) { vat_rate = 0.12; }else { vat_rate = 0.00; }
			 * 
			 * BigDecimal trans_bd = new BigDecimal(trans); BigDecimal vat_bd = new
			 * BigDecimal(vat);
			 * 
			 * modelJV_SL.setValueAt(trans_bd, x, 4); //modelJV_SL.setValueAt(trans_bd, x,
			 * 5);
			 * 
			 * System.out.println(""); System.out.println("false");
			 * System.out.println("Trans :"+ trans);
			 * System.out.println("autocompute_VatAmount_SL :"+autocompute_VatAmount_SL(
			 * trans, vat_rate).doubleValue()); System.out.println("vat_rate :"+vat_rate);
			 * System.out.println("Net Amount :"+ new BigDecimal(trans -
			 * autocompute_VatAmount_SL(trans, vat_rate).doubleValue()));
			 * 
			 * modelJV_SL.setValueAt(new BigDecimal(trans - autocompute_VatAmount_SL(trans,
			 * vat_rate).doubleValue()), row, 5); //modelJV_SL.setValueAt(vat_bd, x, 6);
			 * modelJV_SL.setValueAt(autocompute_VatAmount_SL(trans, vat_rate), row, 6);
			 * double netofvat = Double.parseDouble(modelJV_SL.getValueAt(x, 5).toString());
			 * System.out.println("netofvat :"+netofvat);
			 * 
			 * double wtax_rate= 0.00; try { wtax_rate =
			 * Double.parseDouble(modelJV_SL.getValueAt(x,2).toString())/100;
			 * }catch(NumberFormatException e){} double wtax_amt =
			 * autocompute_WtaxAmount(netofvat, 0.00, wtax_rate).doubleValue(); BigDecimal
			 * wtax_amt_bd = new BigDecimal(wtax_amt); modelJV_SL.setValueAt(wtax_amt_bd, x,
			 * 7);
			 * 
			 * 
			 * }
			 */
			// }

		}

		totalJVSL(modelJV_SL, modelJV_SL_total);
	}

	/*
	 * private void computeJVSL_amount() {// used
	 * 
	 * System.out.println("Dumaan sa computeJVSL_amount."); int rw =
	 * tblJV_SL.getModel().getRowCount(); int x = 0;
	 * 
	 * while (x < rw) {
	 * 
	 * Double trans = Double.parseDouble(modelJV_SL.getValueAt(x, 4).toString());
	 * Double vat = Double.parseDouble(modelJV_SL.getValueAt(x, 6).toString());
	 * Double wtax = Double.parseDouble(modelJV_SL.getValueAt(x, 7).toString());
	 * //double wtax_rate =
	 * Double.parseDouble(modelJV_SL.getValueAt(x,2).toString())/100; //double
	 * wtax_amt = autocompute_WtaxAmount(trans.doubleValue(), 0.12,
	 * wtax_rate).doubleValue();
	 * 
	 * Double netofvat = vat - trans;
	 * 
	 * BigDecimal trans_bd = new BigDecimal(trans); BigDecimal vat_bd = new
	 * BigDecimal(vat); BigDecimal wtax_bd = new BigDecimal(wtax);
	 * 
	 * modelJV_SL.setValueAt(trans_bd, x, 4); modelJV_SL.setValueAt(trans_bd, x, 5);
	 * modelJV_SL.setValueAt(vat_bd, x, 6); //modelJV_SL.setValueAt(wtax_amt, x, 7);
	 * 
	 * x++; } totalJVSL(modelJV_SL, modelJV_SL_total); }
	 */

	private void clickTableColumn_account() {// used

		int column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn());
		int row = tblJV_account.getSelectedRow();

		Integer x[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		String sql[] = { getChartofAccount(), getDivision(), getDepartment(tblJV_account, modelJV_account, 1), "",
				getProject(), getSubproject(tblJV_account, modelJV_account, 4), "", "", "", "", "", "", "", "", "", "",
				getEntityList(), getPBL_List() };
		String lookup_name[] = { "Chart of Account", "Division", "Department", "", "Project", "Subproject", "", "", "",
				"", "", "", "", "", "", "", "Entity", "PBL" };

		if (column == x[column] && modelJV_account.isEditable() && sql[column] != "") {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setFilterClientName(true);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0) {
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt(data[1], row, column + 7);
				if (modelJV_account.getValueAt(row, 0).equals("01-99-03-000")
						|| modelJV_account.getValueAt(row, 0).equals("03-01-06-002")) {

				} else {
					modelJV_SL.setValueAt(data[0], row, column + 8);
					modelJV_SL.setValueAt(data[1], row, column + 14);
				}
			}
			if (data != null && column == 1) { // column Division id
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt("", row, column + 1);
				modelJV_account.setValueAt(data[2], row, 11);
				String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
				generateDetail(lineno);
				if (modelJV_account.getValueAt(row, 0).equals("01-99-03-000")
						|| modelJV_account.getValueAt(row, 0).equals("03-01-06-002")) {

				} else {
					modelJV_SL.setValueAt(data[0], row, column + 10);
				}
			} else if (data != null && column == 2) { // column Department id
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt(RequestForPayment.sql_getDiv(data[0].toString()), row, column - 1);
				modelJV_account.setValueAt(data[2], row, 12);
				String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
				generateDetail(lineno);
				if (modelJV_account.getValueAt(row, 0).equals("01-99-03-000")
						|| modelJV_account.getValueAt(row, 0).equals("03-01-06-002")) {

				} else {
					modelJV_SL.setValueAt(data[0], row, column + 11);
				}
			} else if (data != null && column == 4) {// column Project ID
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt("", row, column + 1);
				modelJV_account.setValueAt(data[2], row, 13);
				String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
				generateDetail(lineno);
				if (modelJV_account.getValueAt(row, 0).equals("01-99-03-000")
						|| modelJV_account.getValueAt(row, 0).equals("03-01-06-002")) {

				} else {
					modelJV_SL.setValueAt(data[0], row, column + 5);
					modelJV_SL.setValueAt(data[1], row, column + 11);
					modelJV_SL.setValueAt("", row, column + 6);
				}
			} else if (data != null && column == 5) {// column Sub Project ID
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt(data[2], row,
						column - 1); /* 2016-10-04: Jessa Herrera: Auto displaying of proj_id: ChangesJsystem2.2 */
				modelJV_account.setValueAt(data[1], row, 14);
				String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
				generateDetail(lineno);
				if (modelJV_account.getValueAt(row, 0).equals("01-99-03-000")
						|| modelJV_account.getValueAt(row, 0).equals("03-01-06-002")) {

				} else {
					modelJV_SL.setValueAt(data[0], row, column + 5);
					modelJV_SL.setValueAt(data[2], row, column + 4);
					modelJV_SL.setValueAt(data[3], row, column + 10);
				}
			} else if (data != null && column == 16) {
				modelJV_account.setValueAt(data[0], row, column);//entity_id column
				modelJV_account.setValueAt(data[1], row, column + 2);//entity_name column //Added by erick DCRF 2781
				System.out.println("column: "+column);
				System.out.println("entity name: "+data[1]);
			} else if (data != null && column == 17) { //pbl_id column
				modelJV_account.setValueAt(data[0], row, column);
				
				
				
			}else if (data != null && column == 18) {
				
			}
		}

		else {
		}

		setTableWidth();
	}

	private static void adjustRowHeight_account() {// used
		int rw = tblJV_account.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			tblJV_account.setRowHeight(x, 22);
			x++;
		}

	}

	private static void adjustRowHeight_accountSL() {// used
		int rw = tblJV_SL.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			tblJV_SL.setRowHeight(x, 22);
			x++;
		}

	}

	private void AddRow() {// used

		// int seq = tblJV_account.getSelectedRow() + 1;

		if (tabCenter.getSelectedIndex() == 0) {
			modelJV_account.addRow(new Object[] { "", "", "", "", "", "", "", "", new BigDecimal(0.00),
					new BigDecimal(0.00), "", "", "", "", "", 0 });
			totalJV(modelJV_account, modelJV_accounttotal);
			((DefaultListModel) rowHeaderJV_account.getModel()).addElement(modelJV_account.getRowCount());
			// totalJV(modelJV_account, modelJV_accounttotal);
			adjustRowHeight_account();
		}
		if (tabCenter.getSelectedIndex() == 2) {
			// int seq = tblJV_account.getSelectedRow() + 1;

			modelJV_SL.addRow(new Object[] { "", "", new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					"", "", "", "", "", "", "", "" });
			totalJV(modelJV_SL, modelJV_SL_total);
			((DefaultListModel) rowHeaderJV_SL.getModel()).addElement(modelJV_SL.getRowCount());
			// totalJV(modelJV_account, modelJV_accounttotal);
			adjustRowHeight_accountSL();
		}
	}

	private void removeRow() {// used

		/*
		 * int r = tblJV_account.getSelectedRow();
		 * 
		 * if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?",
		 * "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
		 * JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
		 * ((DefaultTableModel) tblJV_account.getModel()).removeRow(r);
		 * modelJV_account.addRow(new Object[] { "", "", "", "", "", "", "", "", new
		 * BigDecimal(0.00), new BigDecimal(0.00), "", "", "", "", "", ""});
		 * totalJV(modelJV_account, modelJV_accounttotal); adjustRowHeight_account(); }
		 */
		if (tabCenter.getSelectedIndex() == 0) {
			int[] selectedRows = tblJV_account.getSelectedRows();

			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Work Item(s).",
						"Remove Row", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (int x = selectedRows.length - 1; x > -1; x--) {
				int row = selectedRows[x];

				modelJV_account.removeRow(row);
				((DefaultListModel) rowHeaderJV_account.getModel()).removeElementAt(row);
				totalJV(modelJV_account, modelJV_accounttotal);
				adjustRowHeight_account();
			}
		}
		if (tabCenter.getSelectedIndex() == 2) {
			int[] selectedRows = tblJV_SL.getSelectedRows();

			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Work Item(s).",
						"Remove Row", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (int x = selectedRows.length - 1; x > -1; x--) {
				int row = selectedRows[x];

				modelJV_SL.removeRow(row);
				((DefaultListModel) rowHeaderJV_SL.getModel()).removeElementAt(row);
				// totalJV(modelJV_SL, modelJV_SL_total);
				totalJVSL(modelJV_SL, modelJV_SL_total);
				adjustRowHeight_accountSL();
			}
		}

		// updateWorkItem();
		// computeTotal();

	}

	public static void setTableWidth() {
		tblJV_account.packAll();
		tblJV_account.getColumnModel().getColumn(0).setPreferredWidth(90);
		tblJV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(3).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(4).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(5).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(6).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(8).setPreferredWidth(100);
		tblJV_account.getColumnModel().getColumn(9).setPreferredWidth(100);
	}

	public static String getAvailer(String entity_id) {
		String strsql = "select entity_name from rf_entity  where entity_id='" + entity_id + "'";
		return strsql;
	}

	// save and insert
	private static void insertJV_header(String jv, pgUpdate db) {// used

		int x = 0;
		int y = 1;
		int rw = tblJV_SL.getModel().getRowCount();

		// String entity_id = "";
		String add_remarks1 = "";

		// Added by Erick date 2019-08-19 DCRF 1174

		while (x < rw) {

			// try {entity_id = modelJV_SL.getValueAt(x, 0).toString().trim();} catch
			// (NullPointerException e) {entity_id = "";}
			String entity_id = modelJV_SL.getValueAt(x, 0).toString().trim();
			String tax_id = modelJV_SL.getValueAt(x, 1).toString().trim();
			Boolean vatable = (Boolean) modelJV_SL.getValueAt(x, 3);
			// Double vat = Double.parseDouble(modelJV_SL.getValueAt(x,3).toString());
			// Double wtax = Double.parseDouble(modelJV_SL.getValueAt(x,4).toString());
			//BigDecimal trans_amt = (BigDecimal) modelJV_SL.getValueAt(x, 4);
			//Double trans_amt = (Double) modelJV_SL.getValueAt(x, 4);
			//System.out.println("trans_amt: "+trans_amt);
			//BigDecimal trans_amt_db = new BigDecimal(trans_amt);
			
			
			if(new BigDecimal("0.00").equals(modelJV_SL.getValueAt(x, 4))) {
			//if (modelJV_SL.getValueAt(x, 4) == new BigDecimal("0.00")) {

			} else {
				add_remarks1 = add_remarks1.concat("\n") + modelJV_SL.getValueAt(x, 4) + " - " + FncGlobal.GetString("select '('||case when  "+vatable+"   then 'V' else 'NV' end || ' '  || '" //DCRF 2627
						+ tax_id + "' || ')' || ' ' ||entity_name from rf_entity  where entity_id='" + entity_id + "'");// Edited by Erick 2021-11-22 added tax id DCRF 1832
			}
			x++;
		}

		Integer fiscal_yr_4digit = 0;
		String period_id = "";
		String tran_id = "";
		String posted_by = "";
		Date posted_date = null;
		String doc_id = "";
		Integer proc_id = 0;
		Boolean is_for_rev = false;
		Date rev_date = null;
		String remarks = "";
		String status_id = "";
		String created_by = "";
		String edited_by = "";
		Date date_edited = null;

		fiscal_yr_4digit = (Integer) getMonthYear()[2];
		period_id = lookupPeriod.getText().trim();
		tran_id = lookupTranType.getText().trim();
		posted_by = "";
		posted_date = null;
		doc_id = "11";
		proc_id = 0;
		is_for_rev = false;
		rev_date = null;
		remarks = txtJV_Remark.getText().trim().replace("'", "''").concat(add_remarks1.replace("'", "''"));
		// remarks = txtJV_Remark.getText().trim().replace("'", "''") ;
		status_id = "A";
		created_by = UserInfo.EmployeeCode;
		edited_by = "";
		date_edited = null;

		String sqlDetail = "INSERT into rf_jv_header values (" + "'" + co_id + "',  \n" + // 1
				"'" + co_id + "',  \n" + // 2
				"'" + jv_no + "',  \n" + // 3
				"'" + dateFormat.format(dteTransaction.getDate()) + "',  \n" + // 5 jv_date
				"" + fiscal_yr_4digit + ",  \n" + // 5
				"'" + period_id + "',  \n" + // 6
				"'" + tran_id + "',  \n" + // 7
				"'" + posted_by + "',  \n" + // 8
				"" + posted_date + ",  \n" + // 9
				"'" + doc_id + "',  \n" + // 10
				"" + proc_id + ",  \n" + // 11
				"" + is_for_rev + ",  \n" + // 12
				"" + rev_date + ",  \n" + // 13
				"'" + remarks + "',  \n" + // 14
				"'" + status_id + "',  \n" + // 15
				"'" + created_by + "',  \n" + // 16
				"'" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  \n" + // 17 date_created
				"'" + edited_by + "',  \n" + // 18
				"" + date_edited + "  \n" + // 19
				")   ";

		System.out.printf("SQL #1: %s", sqlDetail);

		db.executeUpdate(sqlDetail, false);

	}

	private static void insertJV_detail(String jv, pgUpdate db) {// used

		int x = 0;
		int y = 1;
		int rw = tblJV_account.getModel().getRowCount();

		while (x < rw) {

			String entity_id = "";
			String pbl_id = "";

			try {
				entity_id = modelJV_account.getValueAt(x, 16).toString().trim();
			} catch (NullPointerException e) {
				entity_id = "";
			}
			try {
				pbl_id = modelJV_account.getValueAt(x, 17).toString().trim();
			} catch (NullPointerException e) {
				pbl_id = "";
			}

			String account_id = modelJV_account.getValueAt(x, 0).toString();
			String bal_side = "";

			Double debit = Double.parseDouble(modelJV_account.getValueAt(x, 8).toString());
			Double credit = Double.parseDouble(modelJV_account.getValueAt(x, 9).toString());
			Double trans_amt = debit + credit;

			if (debit > 0) {
				bal_side = "D";
			} else {
				bal_side = "C";
			}

			String div_id = modelJV_account.getValueAt(x, 1).toString().trim();
			String dept_id = modelJV_account.getValueAt(x, 2).toString().trim();
			String sect_id = modelJV_account.getValueAt(x, 3).toString().trim();
			String project_id = modelJV_account.getValueAt(x, 4).toString().trim();
			String sub_projectid = modelJV_account.getValueAt(x, 5).toString().trim();
			String ibu = modelJV_account.getValueAt(x, 6).toString().trim();
			String ref_no = modelJV_account.getValueAt(x, 10).toString().trim();
			Integer entry_no = (Integer) modelJV_account.getValueAt(x, 15);
			
			//Date other_period = (Date) modelJV_account.getValueAt(x, 18);
			//System.out.println("other_period: "+other_period);

			if (trans_amt == 0.00) {
			} else {
				/*String sqlDetail = "INSERT into rf_jv_detail(\n"
						+ "            co_id, busunit_id, jv_no, entry_no, line_no, acct_id, tran_amt, \n"
						+ "            bal_side, ref_no, project_id, sub_projectid, div_id, dept_id, \n"
						+ "            sect_id, inter_co_id, inter_busunit_id, old_acct_id, \n"
						+ "            entity_id, pbl_id, seq_no, status_id, created_by, date_created, edited_by, \n"
						+ "            date_edited) values (" + "'" + co_id + "',  \n" + // 1 co_id
						"'" + co_id + "',  \n" + // 2 busunit_id
						"'" + jv_no + "',  \n" + // 3 jv_no
						"'" + entry_no + "',  \n" + // 4 entry_no
						"" + y + ",  \n" + // 5 line_no
						"'" + account_id + "',  \n" + // 6 acct_id
						"" + trans_amt + ",  \n" + // 7 tran_amt
						"'" + bal_side + "',  \n" + // 8 bal_side
						"'" + ref_no + "',  \n" + // 9 ref_no
						"'" + project_id + "',  \n" + // 10 project_id
						"'" + sub_projectid + "',  \n" + // 11 sub_projectid
						"'" + div_id + "',  \n" + // 12 div_id
						"'" + dept_id + "',  \n" + // 13 dept_id
						"'" + sect_id + "',  \n" + // 14 sect_id
						"null,  \n" + // 15 inter_co_id
						"'" + ibu + "',  \n" + // 16 inter_busunit_id
						"null,  \n" + // 17 old_acct_id
						"'" + entity_id + "',  \n" + // 18 entity_id
						// "(select entity_id from rf_jv_detail where jv_no = '"+jv+"' and acct_id =
						// '"+account_id+"' and line_no = '"+y+"'), \n" + //17 old_acct_id
						"'" + pbl_id + "' ,  \n" + // 19 pbl_id
						"null,  \n" + // 20 seq_no
						"'A', " + // 21 status_id
						"'" + UserInfo.EmployeeCode + "',  \n" + // 22 created_by
						"'" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  \n" + // 23
																											// date_created
						"'',  \n" + // 24 edited_by
						"null  \n" + // 25 date_edited
						")   ";*/
				String sqlDetail = "INSERT into rf_jv_detail(\n"
						+ "            co_id, busunit_id, jv_no, entry_no, line_no, acct_id, tran_amt, \n"
						+ "            bal_side, ref_no, project_id, sub_projectid, div_id, dept_id, \n"
						+ "            sect_id, inter_co_id, inter_busunit_id, old_acct_id, \n"
						+ "            entity_id, pbl_id, seq_no, status_id, created_by, date_created, edited_by, \n"
						+ "            date_edited/*, other_period*/) values (" + "'" + co_id + "',  \n" + // 1 co_id
						"'" + co_id + "',  \n" + // 2 busunit_id
						"'" + jv_no + "',  \n" + // 3 jv_no
						"'" + entry_no + "',  \n" + // 4 entry_no
						"" + y + ",  \n" + // 5 line_no
						"'" + account_id + "',  \n" + // 6 acct_id
						"" + trans_amt + ",  \n" + // 7 tran_amt
						"'" + bal_side + "',  \n" + // 8 bal_side
						"'" + ref_no + "',  \n" + // 9 ref_no
						"'" + project_id + "',  \n" + // 10 project_id
						"'" + sub_projectid + "',  \n" + // 11 sub_projectid
						"'" + div_id + "',  \n" + // 12 div_id
						"'" + dept_id + "',  \n" + // 13 dept_id
						"'" + sect_id + "',  \n" + // 14 sect_id
						"null,  \n" + // 15 inter_co_id
						"'" + ibu + "',  \n" + // 16 inter_busunit_id
						"null,  \n" + // 17 old_acct_id
						"'" + entity_id + "',  \n" + // 18 entity_id
						// "(select entity_id from rf_jv_detail where jv_no = '"+jv+"' and acct_id =
						// '"+account_id+"' and line_no = '"+y+"'), \n" + //17 old_acct_id
						"'" + pbl_id + "' ,  \n" + // 19 pbl_id
						"null,  \n" + // 20 seq_no
						"'A', " + // 21 status_id
						"'" + UserInfo.EmployeeCode + "',  \n" + // 22 created_by
						"'" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  \n" + // 23
																											// date_created
						"'',  \n" + // 24 edited_by
						"null \n" +
						//"null/*, '"+dateFormat.format(other_period)+"'*/  \n" + // 25 date_edited
						")   ";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);
				y++;
			}

			x++;
		}
	}

	private static void insertSL_detail(String jv, pgUpdate db) {// used

		int x = 0;
		int y = 1;
		int rw = tblJV_SL.getModel().getRowCount();

		while (x < rw) {

			String entity_id = "";
			String entity_type = "";

			try {
				entity_id = modelJV_SL.getValueAt(x, 0).toString().trim();
			} catch (NullPointerException e) {
				entity_id = "";
			}
			try {
				entity_type = modelJV_SL.getValueAt(x, 1).toString().trim();
			} catch (NullPointerException e) {
				entity_type = "";
			}

			Double vat = Double.parseDouble(modelJV_SL.getValueAt(x, 6).toString());
			Double wtax = Double.parseDouble(modelJV_SL.getValueAt(x, 7).toString());
			Double trans_amt = Double.parseDouble(modelJV_SL.getValueAt(x, 4).toString());

			// if(vat>0) {bal_side="D";} else {bal_side="C";}

			String sundry = modelJV_SL.getValueAt(x, 8).toString().trim();

			String div = modelJV_SL.getValueAt(x, 11).toString().trim();
			String dept = modelJV_SL.getValueAt(x, 12).toString().trim();
			String proj = modelJV_SL.getValueAt(x, 9).toString().trim();
			String sub_proj = modelJV_SL.getValueAt(x, 10).toString().trim();

			String tran_type = lookupTranType.getText();

			if (trans_amt == 0.00) {
			} else {
				String sqlDetail = "INSERT INTO rf_subsidiary_ledger(\n"
						+ "            jv_no, tran_type, entity_id, entity_type_id, trans_amt, \n"
						+ "            vat_amt, wtax_amt, sundry_acct, proj_id, sub_proj, div_id, dep_id, \n"
						+ "            status_id, created_by, date_created, busunit_id, co_id\n" + "            )\n"
						+ "    VALUES ('" + jv + "', '" + tran_type + "', '" + entity_id + "', '" + entity_type + "', "
						+ trans_amt + ", \n" + "            " + vat + ", " + wtax + ", '" + sundry + "', NULLIF('"
						+ proj + "','null'), NULLIF('" + sub_proj + "','null'), NULLIF('" + div + "','null'), NULLIF('"
						+ dept + "','null'), 'A',\n" + "            '" + UserInfo.EmployeeCode + "', '"
						+ dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  '" + co_id + "','"
						+ co_id + "'\n" + "            );\n" + "";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);
				y++;
			}

			x++;
		}
	}

	private static void insertAudit_trail(String activity, String remarks, pgUpdate db) {

		String user_code = UserInfo.EmployeeCode;

		String sqlDetail = "INSERT INTO mf_audit_trail(\n"
				+ "	              system_id, activity, user_code, date_upd, entity_id, \n"
				+ "	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n"
				+ "	      VALUES ('JV', '" + activity + "', '" + user_code + "', NOW(), NULL, \n"
				+ "	              NULL, NULL, NULL, NULL, NULL, '" + remarks + "');";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	private void updateJV_header(String jv, pgUpdate db) {// used
		Integer fiscal_yr_4digit = 0;
		fiscal_yr_4digit = (Integer) getMonthYear()[2];

		/*
		 * int x = 0; int y = 1; int rw = tblJV_SL.getModel().getRowCount(); String
		 * entity_id = ""; String remarks = ""; String add_remarks1 = ""; //String
		 * add_remarks2 = ""; //String availer =
		 * FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"
		 * +entity_id+"'");
		 * 
		 * while(x<rw){
		 * 
		 * try {entity_id = modelJV_SL.getValueAt(x, 0).toString().trim();} catch
		 * (NullPointerException e) {entity_id = "";} //Double vat =
		 * Double.parseDouble(modelJV_SL.getValueAt(x,3).toString()); //Double wtax =
		 * Double.parseDouble(modelJV_SL.getValueAt(x,4).toString()); Double trans_amt =
		 * Double.parseDouble(modelJV_SL.getValueAt(x,2).toString()); if (trans_amt ==
		 * 0){}else{add_remarks1 =add_remarks1.concat("\n") +
		 * FncGlobal.GetString("select entity_name from rf_entity  where entity_id='"
		 * +entity_id+"'") + " - " + trans_amt ; } //if(trans_amt ==
		 * 0){}else{add_remarks2 = availer + trans_amt; }
		 * System.out.println("UPDATE DUMAAN DITO"); System.out.println(x);
		 * System.out.println(rw); System.out.println(entity_id);
		 * System.out.println(trans_amt); System.out.println(availer);
		 * System.out.println(remarks); System.out.println(add_remarks1); x++; }
		 */
		int x = 0;
		int y = 1;
		int rw = tblJV_SL.getModel().getRowCount();

		// String entity_id = "";
		String edit_remarks1 = txtJV_Remark.getText().trim().replace("'", "''");

		// Added by Erick date 2019-08-19 DCRF 1174

		while (x < rw) {

			String entity_id = modelJV_SL.getValueAt(x, 0).toString().trim();
			String tax_id = modelJV_SL.getValueAt(x, 1).toString().trim();
			Double trans_amt = Double.parseDouble(modelJV_SL.getValueAt(x, 4).toString());
			
			
			if (trans_amt == 0) {
			} else {
				Boolean vatable = (Boolean) modelJV_SL.getValueAt(x, 3);
				edit_remarks1 = edit_remarks1.concat("\n") + trans_amt + " - "+ FncGlobal.GetString("select '('||case when "+vatable+" then 'V' else 'NV' end || ' '  || '" //DCRF 2627
						+ tax_id + "' || ')' || ' ' ||entity_name from rf_entity  where entity_id='" + entity_id + "'")
								.replace("'", "''");// Edited by Erick 2021-11-22 added tax id DCRF 1832

			}
			x++;
		}

		String sqlDetail = "update rf_jv_header set " + "jv_date = '" + dteTransaction.getDate() + "', " + "fiscal_yr='"
				+ fiscal_yr_4digit + "'," + "period_id = '" + lookupPeriod.getText().trim() + "', " + "tran_id = '"
				+ lookupTranType.getText().trim() + "', " + "remarks = '" + edit_remarks1 + "', " +
				// + txtJV_Remark.getText().trim().replace("'", "''") + "', " +
				// "remarks = '"+remarks +"'" +
				"edited_by = '" + UserInfo.EmployeeCode + "', " + "date_edited = '"
				+ FncGlobal.dateFormat(FncGlobal.getDateSQL()) + "' " + "where trim(jv_no) = trim('" + jv
				+ "') and co_id = '" + co_id + "'   ";

		System.out.println("*********");
		System.out.println("edit_remarks1:" + edit_remarks1);
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	private void updateJV_detail(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_jv_detail set status_id = 'I' " + "where trim(jv_no) = '" + jv
				+ "' and trim(co_id) = '" + co_id + "' " + "and status_id = 'A' ";

		db.executeUpdate(sqlDetail, false);

	}

	private void updateJV_SL(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_subsidiary_ledger set status_id = 'I', edited_by = '" + UserInfo.EmployeeCode
				+ "', date_edited = now() where trim(jv_no) = '" + jv + "' " + "and status_id = 'A'  and co_id = '"
				+ co_id + "' ";
		System.out.println("updateJV_SL: " + sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	private boolean checkposted(String jv_no, String co_id) {
		Boolean posted = null;
		String sql = "select case when status_id = 'P' then true else false end  from rf_jv_header where jv_no = '"
				+ jv_no + "' and co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {
			posted = (boolean) (db.getResult()[0][0]);
		}

		return posted;

	}

	/*
	 * private void updateSL_detail(String jv, pgUpdate db) {//used
	 * 
	 * //int row = tblJV_SL.convertRowIndexToModel(tblJV_SL.getSelectedRow());
	 * 
	 * for(int x =0; x < modelJV_SL.getRowCount(); x++){ String entity = (String)
	 * modelJV_SL.getValueAt(x, 0); String acct = (String) modelJV_SL.getValueAt(x,
	 * 5);
	 * 
	 * String sqlDetail = "update rf_jv_detail set entity_id = (case when '"
	 * +entity+"' != 'null' then '"+entity+"' else '' end), " +
	 * "edited_by = '"+UserInfo.EmployeeCode+"', " +
	 * "date_edited = '"+FncGlobal.dateFormat(FncGlobal.getDateSQL())+"' " +
	 * "where trim(jv_no) = '"+jv+"' and trim(co_id) = '"+co_id+"' " +
	 * "and status_id = 'A' and acct_id = '"+acct+"'" ;
	 * 
	 * db.executeUpdate(sqlDetail, false); }
	 * 
	 * }
	 */

	private void tagJV_header(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_jv_header set status_id = 'F', proc_id = 1 " + "where trim(jv_no) = '" + jv
				+ "' and trim(co_id) = '" + co_id + "' " + "and status_id = 'A' ";

		db.executeUpdate(sqlDetail, false);

	}

	private void untagJV_header(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_jv_header set status_id = 'A', proc_id = 0 " + "where trim(jv_no) = '" + jv
				+ "' and trim(co_id) = '" + co_id + "' " + "and status_id = 'F' ";

		db.executeUpdate(sqlDetail, false);

	}
//	//Added by Erick 2023-09-04 to close accting period after closing trial balance
//	private void close_acct_period (pgUpdate db) {
//		String strsql = "update mf_acctg_period set status_id = 'I', date_close = now() closed_by = '"+UserInfo.EmployeeCode+"' \n"
//				+ "where co_id = '"+co_id+"' and period_year = "+lookupFiscalYr.getText()+" and status_id = 'A' ";
//		
//		System.out.printf("close_acct_period: %s", strsql);
//		db.executeUpdate(strsql, false);
//	}
	

	private void postJV_header(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_jv_header set " + "status_id = 'P'," + "proc_id = 2, " + "posted_by = '"
				+ UserInfo.EmployeeCode + "', " + "date_posted = '" + FncGlobal.dateFormat(FncGlobal.getDateSQL())
				+ "' " + "where trim(jv_no) = trim('" + jv + "') and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	private void postLiq_header(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_liq_header set " + "status_id = 'P'," + "proc_id = 2, " + "edited_by = '"
				+ UserInfo.EmployeeCode + "', " + "date_edited = '" + FncGlobal.dateFormat(FncGlobal.getDateSQL())
				+ "' " + "where trim(jv_no) = trim('" + jv + "') and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	private void deleteJV_header(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_jv_header set status_id = 'D' " + "where trim(jv_no) = '" + jv
				+ "' and trim(co_id) = '" + co_id + "' " + "and status_id = 'A' ";

		db.executeUpdate(sqlDetail, false);

	}

	private void deleteJV_SL(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_subsidiary_ledger set status_id = 'I' " + "where trim(jv_no) = '" + jv
				+ "'  and trim(co_id) = '" + co_id + "' ";

		db.executeUpdate(sqlDetail, false);

	}

	private void updateWriteoffTable(String jv, pgUpdate db) {// used

		String sqlDetail = "update rf_ca_writeoff \n" + "set jv_no = '" + jv_no + "', " + "status_id = 'F' "
				+ "where trim(rplf_no) = '" + drf_no + "' " + "and trim(co_id) = '" + co_id + "' "
				+ "and status_id = 'A' ";

		db.executeUpdate(sqlDetail, false);

	}

	private void inactivateLiquidation(String liq_no, pgUpdate db) {// used

		String sqlDetail = "update rf_liq_header set " + "status_id = 'D',  \n" + // 1
				"deleted_by = '" + UserInfo.EmployeeCode + "' , \n" + // 10
				"deleted_date = '" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "' \n" + // 11
				"where liq_no = '" + liq_no + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public static void untagPV_header_reversalOfPV(String pv_no, String co_id) {// used
		pgUpdate db = new pgUpdate();
		String sqlDetail = "update rf_pv_header set reversal_date = now(), reversal_jv_no = '" + jv_no + "',"
				+ "remarks = 'This hase been auto-reversed; See JV No. '||'" + jv_no + "'||' \n'||remarks  "
				+ "where trim(pv_no) = '" + pv_no + "' and trim(co_id) = '" + co_id + "' ";
		db.executeUpdate(sqlDetail, false);
		db.commit();
	}

	// others
	public static Object[] getMonthYear() {// used

		DateFormat df = new SimpleDateFormat("yyyy-dd-MM");
		String month = df.format(dteTransaction.getDate());
		String month_sub = month.substring(8);
		System.out.println("getMonthYear/month= " + month_sub);

		String month_word[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
		String year = df2.format(dteTransaction.getDate());
		Integer year_sub = Integer.parseInt(year.substring(6)) - 2000;
		Integer year_full = Integer.parseInt(year.substring(6));
		System.out.println("getMonthYear/year= " + year_full);

		Object x[] = { month_sub, year_sub, year_full, month_word[Integer.parseInt(month_sub) - 1], year.substring(6) };

		return x;

	}

	public static void setJV_no() {// used

		Integer fiscal_yr = 0;
		Integer fiscal_yr_full = 0;
		String period_id = "";

		fiscal_yr = (Integer) getMonthYear()[1];
		fiscal_yr_full = (Integer) getMonthYear()[2];
		period_id = lookupPeriod.getText().trim();

		// next_seqno = sql_getNextJV_seqno();
		// Integer last_seq_no = sql_getLastJV_seqno();
		// Integer next_srlno = sql_getNextJV_srlno(fiscal_yr_full, period_id);
		// //comment by Erick 2021-06-04

		// jv_no = getJVno(fiscal_yr, period_id, next_srlno); //comment by Erick
		// 2021-06-04
		jv_no = getJVno_v2(fiscal_yr_full, co_id, Integer.parseInt(period_id));

	}

	private static String getJVno_v2(Integer fiscal_yr, String co_id, Integer period_id) {// used
		String SQL = "select  fn_get_jv_no(" + fiscal_yr + ", '" + co_id + "', " + period_id + ") as next_jvno";

		FncSystem.out("Get JV No", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			jv_no = (String) db.getResult()[0][0];
		} else {
			jv_no = null;
		}

		return jv_no;

	}

	/*
	 * public Integer sql_getNextJV_seqno() {//used Integer next_no = 0; String SQL
	 * = "select nextval('tf_jv_no') " ;
	 * 
	 * pgSelect db = new pgSelect(); db.select(SQL);
	 * 
	 * if(db.isNotNull()){ next_no = (Integer)
	 * Integer.parseInt(db.getResult()[0][0].toString()); }else{ next_no = null; }
	 * 
	 * return next_no; }
	 * 
	 * public Integer sql_getLastJV_seqno() {//used
	 * 
	 * Integer next_no = 0; String SQL =
	 * "select max(trans_seq_no) from rf_jv_header  " ;
	 * 
	 * pgSelect db = new pgSelect(); db.select(SQL);
	 * 
	 * if(db.isNotNull()){ next_no = (Integer)
	 * Integer.parseInt(db.getResult()[0][0].toString()); }else{ next_no = null; }
	 * 
	 * return next_no; }
	 */

	public static Integer sql_getNextJV_srlno(Integer jv_fiscal_yr, String period_id) {// used

		Integer next_no = 0;
		String SQL = "select coalesce(max(jv_no),0) + 1 from \r\n" + "("
				+ "select substring(jv_no, 5)::int as jv_no \r\n" + "  from rf_jv_header \r\n" + "  where fiscal_yr = "
				+ jv_fiscal_yr + " " + "  and co_id = '" + co_id + "'	\r\n"
				+ "  and (select right(left(jv_no, 4), 2)) = '" + period_id + "' \r\n"
				+ "  order by substring(jv_no, 5)::int desc  ) a";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			next_no = (Integer) Integer.parseInt(db.getResult()[0][0].toString());
		} else {
			next_no = 1;
		}

		return next_no;
	}

	private static String getJVno(Integer fiscal_yr, String period_id, Integer next_srlno) {// used
		String SQL = "select ('" + fiscal_yr + "'||'" + period_id + "'||trim(to_char(" + next_srlno
				+ ",'0000'))) as next_jvno ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			jv_no = (String) db.getResult()[0][0];
		} else {
			jv_no = null;
		}

		return jv_no;

	}

	// righ-click
	private void copy() {

		int column = tblJV_account.getSelectedColumn();
		int row = tblJV_account.getSelectedRow();

		item = modelJV_account.getValueAt(row, column).toString().trim();
		mnipaste.setEnabled(true);
	}

	private void paste() {

		int column = tblJV_account.getSelectedColumn();
		int row = tblJV_account.getSelectedRow();

		if (column == 0) {
			if (RequestForPayment.isItemAnAcct(item) == true) {
				modelJV_account.setValueAt(item, row, column);
				setTableWidth();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Pasted item is not a valid Account ID.", "Information",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (column == 1) {
			if (RequestForPayment.isItemDiv(item) == true) {
				modelJV_account.setValueAt(item, row, column);
				setTableWidth();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Pasted item is not a valid Division ID.",
						"Information", JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (column == 2) {
			if (RequestForPayment.isItemDept(item) == true) {
				modelJV_account.setValueAt(item, row, column);
				setTableWidth();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Pasted item is not a valid Department ID.",
						"Information", JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (column == 4) {
			if (RequestForPayment.isItemProject(item) == true) {
				modelJV_account.setValueAt(item, row, column);
				setTableWidth();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Pasted item is not a valid Project ID.", "Information",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (column == 5) {
			if (RequestForPayment.isItemSubProject(item) == true) {
				modelJV_account.setValueAt(item, row, column);
				setTableWidth();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Pasted item is not a valid Sub-project ID.",
						"Information", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void copyRow() {

		int row = tblJV_account.getSelectedRow();

		copy_acct_id = modelJV_account.getValueAt(row, 0).toString().trim();
		copy_div = modelJV_account.getValueAt(row, 1).toString().trim();
		copy_dept = modelJV_account.getValueAt(row, 2).toString().trim();
		copy_proj = modelJV_account.getValueAt(row, 4).toString().trim();
		copy_subproj = modelJV_account.getValueAt(row, 5).toString().trim();
		copy_ibu = modelJV_account.getValueAt(row, 6).toString().trim();
		copy_acct_desc = modelJV_account.getValueAt(row, 7).toString().trim();
		mnipasteRow.setEnabled(true);
	}

	private void pasteRow() {

		int row = tblJV_account.getSelectedRow();

		modelJV_account.setValueAt(copy_acct_id, row, 0);
		modelJV_account.setValueAt(copy_div, row, 1);
		modelJV_account.setValueAt(copy_dept, row, 2);
		modelJV_account.setValueAt(copy_proj, row, 4);
		modelJV_account.setValueAt(copy_subproj, row, 5);
		modelJV_account.setValueAt(copy_ibu, row, 6);
		modelJV_account.setValueAt(copy_acct_desc, row, 7);

		setTableWidth();

	}

	private void openPV() {

		PayableVoucher pv = new PayableVoucher();
		Home_JSystem.addWindow(pv);

		if (co_id.equals("")) {

		} else {
			PayableVoucher.co_id = co_id;
			PayableVoucher.company = company;
			PayableVoucher.lookupCompany.setValue(co_id);
			PayableVoucher.tagCompany.setTag(company);
			PayableVoucher.company_logo = company_logo;
			PayableVoucher.btnAddNew.setEnabled(true);
			PayableVoucher.btnCancel.setEnabled(true);

			PayableVoucher.lblPV_no.setEnabled(true);
			PayableVoucher.lookupPV_no.setEnabled(true);
			// PayableVoucher.lookupPV_no.setEditable(true);
			PayableVoucher.lookupPV_no.setLookupSQL(PayableVoucher.getPV_no());
			PayableVoucher.lookupDRF_no.setLookupSQL(PayableVoucher.getDRF_no());

		}
	}

	private void openDRF() {

		RequestForPayment drf = new RequestForPayment();
		Home_JSystem.addWindow(drf);

		if (co_id.equals("")) {

		} else {
			RequestForPayment.co_id = co_id;
			RequestForPayment.company = company;
			RequestForPayment.lookupCompany.setValue(co_id);
			RequestForPayment.tagCompany.setTag(company);
			RequestForPayment.company_logo = company_logo;

			RequestForPayment.lblDRF_no.setEnabled(true);
			RequestForPayment.lookupDRF_no.setEnabled(true);
			RequestForPayment.lookupDRF_no.setLookupSQL(RequestForPayment.getDRF_no());

			RequestForPayment.btnAddNew.setEnabled(true);
			RequestForPayment.btnCancel.setEnabled(true);
		}
	}

	private void openCV() {

		CheckVoucher chk_vchr = new CheckVoucher();
		Home_JSystem.addWindow(chk_vchr);

		if (co_id.equals("")) {
		} else {
			CheckVoucher.co_id = co_id;
			CheckVoucher.tagCompany.setTag(company);
			CheckVoucher.company = company;
			CheckVoucher.company_logo = company_logo;
			CheckVoucher.lookupCompany.setValue(co_id);

			CheckVoucher.lblDV_no.setEnabled(true);
			CheckVoucher.lookupDV_no.setEnabled(true);
			CheckVoucher.lookupDV_no.setLookupSQL(CheckVoucher.getDV_no(co_id));
			CheckVoucher.enableButtons(true, false, false, false, false, false, false, false, false, false);
		}

	}

	private void openDP() {

		DocsProcessing doc_proc = new DocsProcessing();
		Home_JSystem.addWindow(doc_proc);

		DocsProcessing.co_id = co_id;
		DocsProcessing.company = company;
		DocsProcessing.tagCompany.setTag(company);
		DocsProcessing.company_logo = company_logo;

		DocsProcessing.lblDoc_type.setEnabled(true);
		DocsProcessing.lookupDocType.setEnabled(true);
		// DocsProcessing.lookupDocType.setEditable(true);
		DocsProcessing.tagDocType.setEnabled(true);

		DocsProcessing.lookupDocType.setLookupSQL(DocsProcessing.getDocType());
		DocsProcessing.enableButtons(false, false, false, true);

		DocsProcessing.lookupCompany.setValue(co_id);
		DocsProcessing.lookupDocType.setValue("11");
		DocsProcessing.tagDocType.setTag("GENERAL JOURNAL VOUCHER");

		DocsProcessing.lblProcess.setEnabled(true);
		DocsProcessing.lookupProcess.setEnabled(true);
		// DocsProcessing.lookupProcess.setEditable(true);
		DocsProcessing.tagProcess.setEnabled(true);
		DocsProcessing.lookupProcess.setValue("");
		DocsProcessing.tagProcess.setText("[ ]");

		if (DocsProcessing.lookupDocType.getText().trim().equals("11")) {
			DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getJV_processes());
			DocsProcessing.doc_no = "11";
		}
		if (DocsProcessing.lookupDocType.getText().trim().equals("12")) {
			DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getPV_processes());
			DocsProcessing.doc_no = "12";
		}
		if (DocsProcessing.lookupDocType.getText().trim().equals("13")) {
			DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getDV_processes());
			DocsProcessing.doc_no = "13";
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_F2 && table.equals("tbl_SL")) {
			clickTableColumn_SL();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_F2 && table.equals("tbl_account")) {
			clickTableColumn_account();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	// connections to other module
	private String getLiquidationNo() {

		String liq_no = "";

		String SQL = "select liq_no from rf_liq_header where jv_no = '" + jv_no + "' and status_id = 'A' and co_id ='"
				+ co_id + "'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				liq_no = "";
			} else {
				liq_no = (String) db.getResult()[0][0];
			}

		} else {
			liq_no = "";
		}

		return liq_no;

	}

	private void generateDetail(String lineno) {
		Object[] listCode = new Object[4];
		int row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow());

		listCode[0] = modelJV_account.getValueAt(row, 1).equals("") ? null : modelJV_account.getValueAt(row, 11);
		listCode[1] = modelJV_account.getValueAt(row, 2).equals("") ? null : modelJV_account.getValueAt(row, 12);
		listCode[2] = modelJV_account.getValueAt(row, 4).equals("") ? null : modelJV_account.getValueAt(row, 13);
		listCode[3] = modelJV_account.getValueAt(row, 5).equals("") ? null : modelJV_account.getValueAt(row, 14);

		Object[] newListCode = new Object[4];
		newListCode[0] = listCode[0];
		newListCode[1] = listCode[1];
		newListCode[2] = listCode[2];
		newListCode[3] = listCode[3];

		tagDetail.setText(String.format("[ Div: %s - Dep: %s - Proj: %s - Sub: %s ]", newListCode));
	}

	private static void adjustRowHeight_SL() {// used
		int rw = tblJV_SL.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			tblJV_SL.setRowHeight(x, 22);
			x++;
		}

	}

	private void clickTableColumn_SL() {// used
		int column = tblJV_SL.getSelectedColumn();
		int row = tblJV_SL.getSelectedRow();
		String entity_id = "";
		BigDecimal tran_amt = null;
		if (modelJV_SL.isEditable()) {
			try {
				entity_id = tblJV_SL.getValueAt(row, 0).toString().trim();
				tran_amt = (BigDecimal) modelJV_SL.getValueAt(row, 4);
			} catch (NullPointerException e) {
				entity_id = "";
			}

			Integer x[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
			String sql[] = { getEntityList(), getEntity_type(entity_id), "", "", "", "", "", "", getChartofAccount(),
					getProject(), getSubproject(tblJV_SL, modelJV_SL, 6), getDivision(),
					getDepartment(tblJV_SL, modelJV_SL, 7), "", "", "", "", "" };
			String lookup_name[] = { "Entity ID", "Entity Type", "", "", "", "", "Proj", "SProj", "Div ID", "Dept ID",
					"", "", "", "", "", "" };

			if (column == x[column] && modelJV_SL.isEditable() && sql[column] != "") {
				_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
				dlg.setLocationRelativeTo(FncGlobal.homeMDI);
				if (column == 0) {
					dlg.setFilterIndex(2);
				} else {
					dlg.setFilterIndex(0);
				}
				dlg.setFilterClientName(true);
				dlg.setVisible(true);

				Object[] data = dlg.getReturnDataSet();
				if (data != null && column == 0) { // Entity
					if (entity_id == data[0]) {
						System.out.println("entity_id = data[0]");
					} else {
						System.out.println("entity_id != data[0]");

						/*
						 * txtJV_Remark.setText( txtJV_Remark.getText().concat("\n") + trans_amt + " - "
						 * + FncGlobal.GetString("select '(' || '"
						 * +tax_id+"' || ')' || ' ' ||entity_name from rf_entity  where entity_id='" +
						 * entity_id + "'").replace("'", "''");//Edited by Erick 2021-11-22 added tax id
						 * DCRF 1832 );
						 */
					}
					modelJV_SL.setValueAt(data[0], row, column); // Entity ID
					// modelJV_SL.setValueAt(data[1], row, column + 16); /*commented by jed
					// 2022-01-26 dcrf no. 1930 : change index of entity name in lookup*/
					modelJV_SL.setValueAt(data[2], row, column + 16); // Entity Name
				} else if (data != null && column == 3) {
					if ((boolean) data[4] ) {System.out.println("The entity is  vatable1");}
					else {
						System.out.println("The entity is not vatable2");
					}
					//JOptionPane.showMessageDialog(getTopLevelAncestor(), "", entity_id, row, frameIcon);
				}
				else if (data != null && column == 1) { // Entity_type

					modelJV_SL.setValueAt(data[0], row, column); // Entity_type_id
					modelJV_SL.setValueAt(data[3], row, column + 1); // wtax rate
					modelJV_SL.setValueAt(data[1], row, column + 16);
					// modelJV_SL.setValueAt(autocompute_WtaxAmount(tran_amt.doubleValue(), 0.12,
					// (Double) data[3]),row,column + 6);
					// modelJV_SL.setValueAt(autocompute_WtaxAmount(tran_amt.doubleValue(), 0.12,
					// wtax_rate),x,7);
				}

				if (data != null && column == 8) { // sundry
					modelJV_SL.setValueAt(data[0], row, column); // acct_id
					modelJV_SL.setValueAt(data[1], row, column + 6); // acct_desc
					System.out.print("acct_desc: " + data[1]);
				} else if (data != null && column == 9) { // proj
					modelJV_SL.setValueAt(data[0], row, column); // proj_id
					modelJV_SL.setValueAt("", row, column + 1); // sub_proj
					modelJV_SL.setValueAt(data[1], row, column + 6); // proj_name
				} else if (data != null && column == 10) { // sub_proj
					modelJV_SL.setValueAt(data[0], row, column);
					modelJV_SL.setValueAt(data[2], row, column - 1);
					modelJV_SL.setValueAt(data[3], row, column + 5);

				} else if (data != null && column == 11) { // Division
					// insertStringToAllRows(data[0].toString(),column);
					// removeToAllRows(column+1);
					modelJV_SL.setValueAt(data[0], row, column);
					modelJV_SL.setValueAt("", row, column + 1);
				} else if (data != null && column == 12) { // dept_id
					// insertStringToAllRows(data[0].toString(),column);
					modelJV_SL.setValueAt(data[0], row, column);
				}
				System.out.println("");
				System.out.println("Column: " + column);

			}

			else {
			}
		}
		tblJV_SL.packAll();
	}

	private String getEntity_type(String entity_id) {

		return

		"select a.entity_type_id ,  " + "trim(b.entity_type_desc), " + "c.wtax_id, " + "c.wtax_rate \n"
				+ "from (select * from rf_entity_assigned_type where trim(entity_id) =  '" + entity_id + "' ) a \r\n"
				+ "left join mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n"
				+ "left join rf_withholding_tax c on b.wtax_id = c.wtax_id   ";

	}

	private String getEntityList() {// **EDITED BY JED 2022-01-26 DCRF NO.1930 : INCLUDE TIN NO IN LOOKUP FOR
									// REPORTING PURPOSES**//

//		String sql = "select " + "trim(entity_id) as \"Entity ID\",  \n" + "trim(entity_name) as \"Name\",  \n"
//				+ "entity_kind as \"Kind\",  \n" + "vat_registered as \"VAT\"  \n" + "from ("
//				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where status_id = 'A') union \n"
//				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in (select entity_id  from em_employee)) union \n"
//				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where vat_registered = true )) a \n"
//				+ "order by a.entity_name  ";
//		return sql;

		String sql = "\n" + "select \n" + "trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\",\n" + "trim(\n" + "	case\n"
				+ "			when a.tin_no = '' or a.tin_no is null then ''\n" + "			else\n"
				+ "					(\n" + "						concat (		\n"
				+ "									substr(trim(replace(a.tin_no,'-','')), 1, 3), '-',\n"
				+ "									substr(trim(replace(a.tin_no,'-','')), 4, 3), '-',\n"
				+ "									substr(trim(replace(a.tin_no,'-','')), 7, 3), '-',\n"
				+ "									substr(trim(replace(a.tin_no,'-','')), 10, 3),\n"
				+ "									(case \n"
				+ "										when substr(trim(replace(a.tin_no,'-','')), 10, 3) = '' or substr(trim(replace(a.tin_no,'-','')), 10, 3) is null  then '000' else '' end)\n"
				+ "								)\n" + "					) end\n" + ")as \"TIN\",\n"
				+ " \n" + "entity_kind as \"Kind\",\n" + "vat_registered as \"VAT\" \n"
				+ "from (\n" + "		(\n" + "			select \n" + "			a.entity_id, \n"
				+ "			a.entity_name, \n" + "			a.entity_kind, \n" + "			a.vat_registered,\n"
				+ "			replace(b.tin_no,'-','') as tin_no\n" + "			from rf_entity a\n"
				+ "			left join rf_entity_id_no b on a.entity_id = b.entity_id\n"
				+ "			where a.status_id = 'A'\n" + "		) union\n" + "		(\n" + "			select \n"
				+ "			a.entity_id, \n" + "			a.entity_name, \n" + "			a.entity_kind, \n"
				+ "			a.vat_registered,\n" + "			replace(b.tin_no,'-','') as tin_no\n"
				+ "			from rf_entity a\n" + "			left join rf_entity_id_no b on a.entity_id = b.entity_id\n"
				+ "			where a.entity_id in (select entity_id  from em_employee)\n" + "		) union\n"
				+ "		(\n" + "			select \n" + "			a.entity_id, \n" + "			a.entity_name,\n"
				+ "			a.entity_kind,\n" + "			a.vat_registered,\n"
				+ "			replace(b.tin_no,'-','') as tin_no\n" + "			from rf_entity a\n"
				+ "			left join rf_entity_id_no b on a.entity_id = b.entity_id\n"
				+ "			where vat_registered = true )\n" + "		) a\n" + "order by a.entity_name";

		return sql;

	}

	private String getPBL_List() {

		String sql = "select pbl_id, description from mf_unit_info where status_id != 'I' order by pbl_id::int ";
		return sql;

	}

	private JPanel displayTableNavigation() {

		btnAddAcct = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddAcct.setActionCommand("Add Acct");
		btnAddAcct.setToolTipText("For multiple entry");
		btnAddAcct.setEnabled(false);
		btnAddAcct.addActionListener(this);

		btnRemoveAcct = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveAcct.setActionCommand("Minus Acct");
		btnRemoveAcct.setToolTipText("For multiple entry");
		btnRemoveAcct.setEnabled(false);
		btnRemoveAcct.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnAddAcct);
		pnl.add(btnRemoveAcct);

		return pnl;
	}

	private static boolean entry() {

		boolean x = false;
		String pv_no = "";

		String strSQL = "select jv_no from rf_jv_detail where jv_no = '" + lookupJV.getValue()
				//+ "' and entry_no in (2) and status_id = 'A' and co_id = '"+lookupCompany.getValue()+"'";
		+ "' and entry_no > 1 and status_id = 'A' and co_id = '"+lookupCompany.getValue()+"'"; //Edited by Erick 2023-12-03 to check all entry no
		
		
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()) {
			pv_no = (String) db.getResult()[0][0];
		}

		if (pv_no.equals(null) || pv_no.isEmpty()) {
			x = false;
			System.out.println(x);
		} else {
			x = true;
		}

		return x;
	}

	private void checkCostCenter_manual(KeyEvent evt) {

		int row = 0;
		int column = 0;

		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn()) - 1;
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow());
		} else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn()) + 1;
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow());
		} else if (evt.getKeyCode() == KeyEvent.VK_UP) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn());
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow()) + 1;
		} else if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_ENTER) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn());
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow()) - 1;
		}

		column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn());
		row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow());

		String div_id = "";
		String dept_id = "";
		String proj_id = "";
		String subproj_id = "";

		System.out.printf("Value of row: %s%n", row);
		System.out.printf("Value of column: %s%n", column);
		//Date o_period = (Date)modelJV_account.getValueAt(row, 18);
		//System.out.printf("Value of: %s%n", modelJV_account.getValueAt(row, 18).toString());
		//System.out.printf("Value of: %s%n", o_period);

		if (row == -1) {
		} else {
			try {
				div_id = modelJV_account.getValueAt(row, 1).toString().trim();
			} catch (NullPointerException e) {
				div_id = "";
			}
			try {
				dept_id = modelJV_account.getValueAt(row, 2).toString().trim();
			} catch (NullPointerException e) {
				dept_id = "";
			}
			try {
				proj_id = modelJV_account.getValueAt(row, 4).toString().trim();
			} catch (NullPointerException e) {
				proj_id = "";
			}
			try {
				subproj_id = modelJV_account.getValueAt(row, 5).toString().trim();
			} catch (NullPointerException e) {
				subproj_id = "";
			}

			if (column == 1 || column == 2) {
				if (div_id.equals("") || dept_id.equals("")) {
				} else {
					if (sql_getDiv(dept_id).equals(div_id)) {

					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Dept ID / Div ID not matched.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
						modelJV_account.setValueAt("", row, column);
					}
				}
			}

			else if (column == 4 /* || column == 5 */) {
				if (proj_id.equals("") || subproj_id.equals("")) {
				} else {
					if (sql_getProj(subproj_id).equals(proj_id)) {

					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Proj ID / Phase ID not matched.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
						modelJV_account.setValueAt("", row, column);
					}
				}
			}
		}
	}

	/*
	 * private void checkJVSL_vattax(KeyEvent evt){
	 * 
	 * int column = 0; int row = 0;
	 * 
	 * int row2 =
	 * tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow());
	 * 
	 * if (evt.getKeyCode()== KeyEvent.VK_RIGHT){ column =
	 * tblJV_SL.convertColumnIndexToModel(tblJV_SL.getSelectedColumn())-1; row =
	 * tblJV_SL.convertRowIndexToModel(tblJV_SL.getSelectedRow());} else if
	 * (evt.getKeyCode()== KeyEvent.VK_LEFT){ column =
	 * tblJV_SL.convertColumnIndexToModel(tblJV_SL.getSelectedColumn())+1; row =
	 * tblJV_SL.convertRowIndexToModel(tblJV_SL.getSelectedRow());} else if
	 * (evt.getKeyCode()== KeyEvent.VK_UP){ column =
	 * tblJV_SL.convertColumnIndexToModel(tblJV_SL.getSelectedColumn()); row =
	 * tblJV_SL.convertRowIndexToModel(tblJV_SL.getSelectedRow())+1;} else if
	 * (evt.getKeyCode()== KeyEvent.VK_DOWN||evt.getKeyCode()== KeyEvent.VK_ENTER){
	 * column = tblJV_SL.convertColumnIndexToModel(tblJV_SL.getSelectedColumn());
	 * row = tblJV_SL.convertRowIndexToModel(tblJV_SL.getSelectedRow())-1;}
	 * 
	 * 
	 * Double JV_tran_amt = new Double (0.00); Double JV_vat = new Double (0.00);
	 * Double JV_wtax = new Double (0.00); Double SL_tran_amt = new Double (0.00);
	 * Double SL_vat = new Double (0.00); Double SL_wtax = new Double (0.00);
	 * 
	 * if (row==-1){} else { if (modelJV_account.getValueAt(row2,
	 * 0).equals("01-99-03-000")) { try { JV_vat =
	 * Double.parseDouble(modelJV_account.getValueAt(row2,8).toString()); } catch
	 * (NullPointerException e) { JV_vat = new Double (0.00); } } else if
	 * (modelJV_account.getValueAt(row2, 0).equals("03-01-06-002")) { try { JV_wtax
	 * = Double.parseDouble(modelJV_account.getValueAt(row2,9).toString()); } catch
	 * (NullPointerException e) { JV_wtax = new Double (0.00); } } try { JV_tran_amt
	 * = Double.parseDouble(modelJV_accounttotal.getValueAt(0,8).toString()); }
	 * catch (NullPointerException e) { JV_tran_amt = new Double (0.00); }
	 * 
	 * try { SL_vat = Double.parseDouble(modelJV_SL.getValueAt(row,3).toString()); }
	 * catch (NullPointerException e) { SL_vat = new Double (0.00); } try { SL_wtax
	 * = Double.parseDouble(modelJV_SL.getValueAt(row,4).toString()); } catch
	 * (NullPointerException e) { SL_wtax = new Double (0.00); } try { SL_tran_amt =
	 * Double.parseDouble(modelJV_SL.getValueAt(row,2).toString()); } catch
	 * (NullPointerException e) { SL_wtax = new Double (0.00); }
	 * 
	 * if(column==2) { if(SL_tran_amt.equals(0.00)){} else { if
	 * (JV_tran_amt.equals(SL_tran_amt)) { } else {
	 * JOptionPane.showMessageDialog(getContentPane()
	 * ,"Transaction amount not matched.","ERROR",JOptionPane.ERROR_MESSAGE);
	 * modelJV_SL.setValueAt(0.00, row, column); } } } else if(column==3) {
	 * if(SL_vat.equals(0.00)){} else { if (modelJV_account.getValueAt(row2,
	 * 0).equals("01-99-03-000") && !JV_vat.equals(SL_vat)) {
	 * JOptionPane.showMessageDialog(getContentPane(),"Vat amount not matched."
	 * ,"ERROR",JOptionPane.ERROR_MESSAGE); modelJV_SL.setValueAt(0.00, row,
	 * column); } else {
	 * 
	 * } } } else if(column==4) { if(SL_wtax.equals(0.00)){} else { if
	 * (modelJV_account.getValueAt(row2, 0).equals("03-01-06-002") &&
	 * !JV_wtax.equals(SL_wtax)) {
	 * JOptionPane.showMessageDialog(getContentPane(),"WTax amount not matched."
	 * ,"ERROR",JOptionPane.ERROR_MESSAGE); modelJV_SL.setValueAt(0.00, row,
	 * column); } else {
	 * 
	 * } } } } }
	 */

	private boolean vatWtax_amounts_equal() {

		boolean vatWtax_amounts_equal = false;

		double vatAmt = 0.00;
		double wtaxAmt = 0.00;
		double vatAmt_sl = 0.00;
		double wtaxAmt_sl = 0.00;
		double vatamount = 0.00;
		double vatamount_credit = 0.00;
		double vatamount_debit = 0.00;
		double wtaxamount = 0.00;
		double wtaxamount_debit = 0.00;
		double wtaxamount_credit = 0.00;
		Double trans_amt = 0.00;
		double vatAmt_sl_x = 0.00;
		double wtaxAmt_sl_x = 0.00;
		double vatamount_sl = 0.00;
		double wtaxamount_sl = 0.00;

		/*
		 * for(int x=0; x < modelJV_account.getRowCount(); x++){
		 * System.out.println("start"); String acctID =
		 * modelJV_account.getValueAt(x,0).toString();
		 * 
		 * Double vatAmt_x =
		 * Double.parseDouble(modelJV_account.getValueAt(x,8).toString());
		 * if(acctID.equals("01-99-03-000")){vatAmt = vatAmt + vatAmt_x;}
		 * //if(acctID.equals("01-99-03-000")){
		 * vatamount=getVatAmount(vatAmt,vatAmt_x).doubleValue();};
		 * 
		 * Double wtaxAmt_x =
		 * Double.parseDouble(modelJV_account.getValueAt(x,9).toString());
		 * if(acctID.equals("03-01-06-002")){wtaxAmt = wtaxAmt + wtaxAmt_x;}
		 * //if(acctID.equals("03-01-06-002")){ wtaxamount =getWtaxAmount(wtaxAmt ,
		 * wtaxAmt_x).doubleValue();} System.out.println(vatAmt);
		 * 
		 * System.out.println("end"); //System.out.println("wtaxAmt_x=" +wtaxAmt_x);
		 * 
		 * }
		 * 
		 * Double trans_amt =
		 * Double.parseDouble(modelJV_SL_total.getValueAt(0,2).toString());
		 * 
		 * Double vatAmt_sl_x =
		 * Double.parseDouble(modelJV_SL_total.getValueAt(0,3).toString()); vatAmt_sl =
		 * vatAmt_sl + vatAmt_sl_x; //double vatamount_sl = getvatAmt_sl(vatAmt_sl ,
		 * vatAmt_sl_x).doubleValue();
		 * 
		 * Double wtaxAmt_sl_x =
		 * Double.parseDouble(modelJV_SL_total.getValueAt(0,4).toString()); wtaxAmt_sl =
		 * wtaxAmt_sl + wtaxAmt_sl_x; //double wtaxamount_sl =getWtaxAmt_sl( wtaxAmt_sl
		 * , wtaxAmt_sl_x).doubleValue();
		 * 
		 * if (trans_amt == 0) {vatWtax_amounts_equal = true;} else if
		 * (vatAmt!=vatAmt_sl && wtaxAmt==wtaxAmt_sl ) {vatWtax_amounts_equal = false;
		 * JOptionPane.showMessageDialog(getContentPane(),slNotMatchPopUp(vatAmt,wtaxAmt
		 * ,vatAmt_sl,wtaxAmt_sl),"Error", JOptionPane.ERROR_MESSAGE); } else if
		 * (vatAmt==vatAmt_sl && wtaxAmt!=wtaxAmt_sl ) {vatWtax_amounts_equal = false;
		 * JOptionPane.showMessageDialog(getContentPane(),slNotMatchPopUp(vatAmt,wtaxAmt
		 * ,vatAmt_sl,wtaxAmt_sl),"Error", JOptionPane.ERROR_MESSAGE); } else if
		 * (vatAmt!=vatAmt_sl && wtaxAmt!=wtaxAmt_sl ) {vatWtax_amounts_equal = false;
		 * JOptionPane.showMessageDialog(getContentPane(),slNotMatchPopUp(vatAmt,wtaxAmt
		 * ,vatAmt_sl,wtaxAmt_sl),"Error", JOptionPane.ERROR_MESSAGE); } else
		 * {vatWtax_amounts_equal = true;}
		 */

		for (int x = 0; x < modelJV_account.getRowCount(); x++) {
			// System.out.println("start");
			String acctID = modelJV_account.getValueAt(x, 0).toString();

			// Double vatAmt_x =
			// Double.parseDouble(modelJV_account.getValueAt(x,8).toString());
			Double vatAmt_x = Math.abs(Double.parseDouble(modelJV_account.getValueAt(x, 8).toString()));
			Double vatAmt_x_credit = Math.abs(Double.parseDouble(modelJV_account.getValueAt(x, 9).toString()));// reversal
																												// wtax
																												// added
																												// by
																												// Erick
																												// 09-17-2020
			// if(acctID.equals("01-99-03-000")){vatAmt = vatAmt + vatAmt_x;}
			if (acctID.equals("01-99-03-000")) {//Input VAT

				if (vatAmt_x == 0) {
					// vatamount_credit=
					// getVatAmount(vatamount,vatAmt_x_credit).doubleValue();//reverse vat added by
					// Erick 09-17-2020
					vatamount_credit = Math
							.abs(Double.parseDouble(getVatAmount(vatamount_credit, vatAmt_x_credit).toString())); // .doubleValue();//reverse
																													// vat
																													// Edited
																													// by
																													// Erick
																													// 10-15-2020

				} else {
					// vatamount_debit=getVatAmount(vatamount,vatAmt_x).doubleValue();//normal vat
					//Normal Balance
					vatamount_debit = Math.abs(Double.parseDouble(getVatAmount(vatamount_debit, vatAmt_x).toString()));// normal
																														// vat
																														// //adited
																														// by
																														// Erick
																														// 10-15-2020
				}

				// vatamount= Math.abs(vatamount_debit - vatamount_credit);//added by Erick
				// 10-15-2020

			}
			;

			// Double wtaxAmt_x =
			// Double.parseDouble(modelJV_account.getValueAt(x,9).toString());
			Double wtaxAmt_x = Math.abs(Double.parseDouble(modelJV_account.getValueAt(x, 9).toString()));
			Double wtaxAmt_x_debit = Math.abs(Double.parseDouble(modelJV_account.getValueAt(x, 8).toString())); // reversal
																												// wtax
																												// added
																												// by
																												// Erick
																												// 09-17-2020
			// if(acctID.equals("03-01-06-002")){wtaxAmt = wtaxAmt + wtaxAmt_x;}
			if (acctID.equals("03-01-06-002")) {//Withholding Tax Payable - Expanded
				if (wtaxAmt_x == 0) {
					// wtaxamount = wtaxamount+getWtaxAmount(wtaxAmt ,
					// wtaxAmt_x_debit).doubleValue();//reverse wtax added by Erick 09-17-2020
					wtaxamount_debit = Math
							.abs(Double.parseDouble(getWtaxAmount(wtaxamount_debit, wtaxAmt_x_debit).toString()));// reverse
																													// wtax
																													// added
																													// by
																													// Erick
																													// 10-15-2020
				} else {
					// wtaxamount = wtaxamount+getWtaxAmount(wtaxAmt ,
					// wtaxAmt_x).doubleValue();//normal wtax
					//Normal Balance
					wtaxamount_credit = Math
							.abs(Double.parseDouble(getWtaxAmount(wtaxamount_credit, wtaxAmt_x).toString()));// normal
																												// wtax
																												// //adited
																												// by
																												// Erick
																												// 10-15-2020
				}
				// wtaxamount = Math.abs(wtaxamount_credit - wtaxamount_debit);//added by Erick
				// 10-15-2020
			}
			// System.out.println("end");

		}
		//vatamount = Math.abs((vatamount_debit - vatamount_credit));//Comment by Erick 2023/02/02
		if (vatamount_debit == 0.0) {// Added by Erick 2023/02/02 to compute abnormal balance side on vat entries
			
			vatamount =(vatamount_credit * -1);
			System.out.println("vatamount_debit == 0.0");
		
		}else if(vatamount_debit != 0.0 && vatamount_credit != 0.0) {//with abnormal balance side on both debit and credit on vat
			
			vatamount =(vatamount_debit ) -((vatamount_credit * -1) * -1);
			System.out.println("vatamount_debit != 0.0 && vatamount_credit != 0.0");
		
		}else {
			
			vatamount = Math.abs((vatamount_debit - vatamount_credit));
			System.out.println("Normal Balance Side of Vat");
		
		}
				
		// added by Erick 10-15-2020
		System.out.println("vatamount_credit: " + vatamount_credit*-1);
		System.out.println("vatamount_debit: " + vatamount_debit);
		System.out.println("vatamount(Entries): " + vatamount);

		//wtaxamount = Math.abs(wtaxamount_credit - wtaxamount_debit);//Comment by Erick 2023/02/02
		if (wtaxamount_credit == 0.0) {// Added by Erick 2023/02/02 to compute abnormal balance side on wtax entries
			
			wtaxamount = (wtaxamount_debit *-1);
			System.out.println("wtaxamount_credit == 0.0");
		
		}else if(wtaxamount_credit != 0.0 && wtaxamount_debit != 0.0) {	//with abnormal balance side on both debit and credit on wtax
			
			wtaxamount = (wtaxamount_credit ) - ((wtaxamount_debit *-1) * -1 );
			System.out.println("wtaxamount_credit != 0.0 && wtaxamount_debit != 0.0");
		
		}else {
			
			wtaxamount = Math.abs(wtaxamount_credit - wtaxamount_debit);
			System.out.println("Normal Balance Side of Wtax");
		
		}
		System.out.println("wtaxamount_credit= " + wtaxamount_credit);
		System.out.println("wtaxamount_debit: " + wtaxamount_debit*-1);
		System.out.println("wtaxamount(Entries)= " + wtaxamount);

		try {
			// Double trans_amt =
			// Double.parseDouble(modelJV_SL_total.getValueAt(0,2).toString());
			// trans_amt =
			// Math.abs(Double.parseDouble(modelJV_SL_total.getValueAt(0,2).toString()));

			if (modelJV_SL_total.getValueAt(0, 4).toString().equals("0.00")) {
				trans_amt = 0.00;
				System.out.printf("trans_amt(sl)= %s%n", trans_amt);
			} else {
				trans_amt = Math.abs(Double.parseDouble(modelJV_SL_total.getValueAt(0, 4).toString()));
			}
			// Double vatAmt_sl_x =
			// Double.parseDouble(modelJV_SL_total.getValueAt(0,3).toString());
			//vatAmt_sl_x = Math.abs(Double.parseDouble(modelJV_SL_total.getValueAt(0, 6).toString()));//Comment by Erick 2023/02/02
			vatAmt_sl_x = Double.parseDouble(modelJV_SL_total.getValueAt(0, 6).toString());
			// vatAmt_sl = vatAmt_sl + vatAmt_sl_x;
			vatamount_sl = getvatAmt_sl(vatAmt_sl, vatAmt_sl_x).doubleValue();
			System.out.printf("vatamount(sl)= %s%n", vatamount_sl);

			//wtaxAmt_sl_x = Math.abs(Double.parseDouble(modelJV_SL_total.getValueAt(0, 7).toString()));//Comment by Erick 2023/02/02
			wtaxAmt_sl_x = Double.parseDouble(modelJV_SL_total.getValueAt(0, 7).toString());
			// wtaxAmt_sl = wtaxAmt_sl + wtaxAmt_sl_x;

			wtaxamount_sl = getWtaxAmt_sl(wtaxAmt_sl, wtaxAmt_sl_x).doubleValue();
			System.out.printf("wtaxamount(sl)= %s%n", wtaxamount_sl);
			System.out.printf("trans_amt(sl)= %s%n", trans_amt);

		} catch (NullPointerException e) {
			trans_amt = 0.00;
			vatAmt_sl_x = 0.00;
			wtaxAmt_sl_x = 0.00;

		}

		if (trans_amt == 0 && vatamount == 0 && wtaxamount == 0) {

			vatWtax_amounts_equal = true;

		} else if (vatamount != vatamount_sl && wtaxamount == wtaxamount_sl) {

			vatWtax_amounts_equal = false;
			JOptionPane.showMessageDialog(getContentPane(),
					slNotMatchPopUp(vatamount, wtaxamount, vatamount_sl, wtaxamount_sl), "Error",
					JOptionPane.ERROR_MESSAGE);

		} else if (vatamount == vatamount_sl && wtaxamount != wtaxamount_sl) {

			vatWtax_amounts_equal = false;
			JOptionPane.showMessageDialog(getContentPane(),
					slNotMatchPopUp(vatamount, wtaxamount, vatamount_sl, wtaxamount_sl), "Error",
					JOptionPane.ERROR_MESSAGE);

		} else if (vatamount != vatamount_sl && wtaxamount != wtaxamount_sl) {

			vatWtax_amounts_equal = false;
			JOptionPane.showMessageDialog(getContentPane(),
					slNotMatchPopUp(vatamount, wtaxamount, vatamount_sl, wtaxamount_sl), "Error",
					JOptionPane.ERROR_MESSAGE);

		} else {
			vatWtax_amounts_equal = true;
		}

		return vatWtax_amounts_equal;

	}

	private static BigDecimal getVatAmount(Double vatAmt, Double vatAmt_x) {// compute vat amount on account entries

		BigDecimal vat_amount = BigDecimal.valueOf(0.00);

		/*
		 * String SQL =
		 * "SELECT * FROM compute_vat_amount('"+gr_amt+"', '"+vat_rate+"')";
		 */
		String SQL = "select * from compute_jv_vatAmt_acc_entries('" + vatAmt + "','" + vatAmt_x + "')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((BigDecimal) db.getResult()[0][0] == BigDecimal.valueOf(0.00)) {
				vat_amount = BigDecimal.valueOf(0.00);
			} else {
				vat_amount = (BigDecimal) db.getResult()[0][0];
			}

		} else {
			vat_amount = BigDecimal.valueOf(0.00);
		}

		return vat_amount;

	}

	private static BigDecimal autocompute_VatAmount_SL(Double gr_amt, Double vat_rate) {// compute vat amount

		BigDecimal vat_amt = BigDecimal.valueOf(0.00);

		String SQL = "SELECT * FROM compute_vat_amount('" + gr_amt + "', '" + vat_rate + "')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((BigDecimal) db.getResult()[0][0] == BigDecimal.valueOf(0.00)) {
				vat_amt = BigDecimal.valueOf(0.00);
			} else {
				vat_amt = (BigDecimal) db.getResult()[0][0];
			}

		} else {
			vat_amt = BigDecimal.valueOf(0.00);
		}

		return vat_amt;

	}

	private static BigDecimal autocompute_WtaxAmount(Double gr_amt, Double vat_rate, Double tax_rate) {// compute wtax
																										// amount

		BigDecimal wtax_amt = BigDecimal.valueOf(0.00);

		String SQL = "SELECT * FROM compute_wtax_amount('" + gr_amt + "', '" + vat_rate + "', '" + tax_rate + "')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((BigDecimal) db.getResult()[0][0] == BigDecimal.valueOf(0.00)) {
				wtax_amt = BigDecimal.valueOf(0.00);
			} else {
				wtax_amt = (BigDecimal) db.getResult()[0][0];
			}

		} else {
			wtax_amt = BigDecimal.valueOf(0.00);
		}

		return wtax_amt;

	}

	private static BigDecimal getWtaxAmount(Double wtaxAmt, Double wtaxAmt_x) {// compute wtax amount on account entries

		BigDecimal wtax_amount = BigDecimal.valueOf(0.00);

		/*
		 * String SQL =
		 * "SELECT * FROM compute_wtax_amount('"+gr_amt+"', '"+vat_rate+"', '"+tax_rate+
		 * "')";
		 */
		String SQL = "select * from compute_jv_vatAmt_acc_entries('" + wtaxAmt + "','" + wtaxAmt_x + "')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((BigDecimal) db.getResult()[0][0] == BigDecimal.valueOf(0.00)) {
				wtax_amount = BigDecimal.valueOf(0.00);
			} else {
				wtax_amount = (BigDecimal) db.getResult()[0][0];
			}

		} else {
			wtax_amount = BigDecimal.valueOf(0.00);
		}

		return wtax_amount;

	}

	private static BigDecimal getvatAmt_sl(Double vatAmt_sl, Double vatAmt_sl_x) {// compute vat amount on SL

		BigDecimal vatsl_amount = BigDecimal.valueOf(0.00);

		/*
		 * String SQL =
		 * "SELECT * FROM compute_wtax_amount('"+gr_amt+"', '"+vat_rate+"', '"+tax_rate+
		 * "')";
		 */
		String SQL = "select * from compute_jv_vatAmt_sl('" + vatAmt_sl + "','" + vatAmt_sl_x + "')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((BigDecimal) db.getResult()[0][0] == BigDecimal.valueOf(0.00)) {
				vatsl_amount = BigDecimal.valueOf(0.00);
			} else {
				vatsl_amount = (BigDecimal) db.getResult()[0][0];
			}

		} else {
			vatsl_amount = BigDecimal.valueOf(0.00);
		}

		return vatsl_amount;

	}

	private static BigDecimal getWtaxAmt_sl(Double wtaxAmt_sl, Double wtaxAmt_sl_x) {// compute wtax amount on SL

		BigDecimal wtaxsl_amount = BigDecimal.valueOf(0.00);

		/*
		 * String SQL =
		 * "SELECT * FROM compute_wtax_amount('"+gr_amt+"', '"+vat_rate+"', '"+tax_rate+
		 * "')";
		 */
		String SQL = "select * from compute_jv_wtaxAmt_sl('" + wtaxAmt_sl + "','" + wtaxAmt_sl_x + "')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((BigDecimal) db.getResult()[0][0] == BigDecimal.valueOf(0.00)) {
				wtaxsl_amount = BigDecimal.valueOf(0.00);
			} else {
				wtaxsl_amount = (BigDecimal) db.getResult()[0][0];
			}

		} else {
			wtaxsl_amount = BigDecimal.valueOf(0.00);
		}

		return wtaxsl_amount;

	}

	private String slNotMatchPopUp(Double a, Double b, Double c, Double d) {
		String x = "VAT Amt. (Entries) =" + a + "\n" + "WTax Amt. (Entries) =" + b + "\n" + "VAT Amt. (SL) =" + c + "\n"
				+ "WTax Amt. (SL) =" + d;
		return x;
	}

	public static String sql_getProj(String sub_proj) {

		String proj_id = "";

		String SQL = "select proj_id from mf_sub_project where sub_proj_id = '" + sub_proj + "' and status_id ='A' ";

		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("SQL Project", SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				proj_id = "";
			} else {
				proj_id = (String) db.getResult()[0][0];
			}
		} else {
			proj_id = "";
		}

		return proj_id;
	}

	public static String sql_getDiv(String dept_id) {

		String div = "";

		String SQL = "select division_code from mf_department where dept_code = '" + dept_id + "' ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				div = "";
			} else {
				div = (String) db.getResult()[0][0];
			}
		} else {
			div = "";
		}

		return div;
	}

	private static boolean jv_sl(String jv_no, String co_id) {

		boolean x = false;
		String jv = "";

		String strSQL = "SELECT jv_no FROM rf_subsidiary_ledger where jv_no = '" +jv_no+ "' and co_id = '"+co_id+"'";
		// String strSQL = "SELECT jv_no FROM rf_liq_header where jv_no =
		// '"+lookupJV.getValue()+"'";

		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()) {
			jv = (String) db.getResult()[0][0];
		}

		if (jv.equals(null) || jv.isEmpty()) {
			x = false;
			System.out.println(x);
		} else {
			x = true;
		}

		return x;
	}

	private void checkDivDept(KeyEvent evt) {

		int row = 0;
		int column = 0;

		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn()) - 1;
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow());
		} else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn()) + 1;
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow());
		} else if (evt.getKeyCode() == KeyEvent.VK_UP) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn());
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow()) + 1;
		} else if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_ENTER) {
			column = tblJV_account.convertColumnIndexToModel(tblJV_account.getSelectedColumn());
			row = tblJV_account.convertRowIndexToModel(tblJV_account.getSelectedRow()) - 1;
		}

		String div_id = "";
		String dept_id = "";
		String proj_id = "";
		String subproj_id = "";

		if (row == -1) {
		} else {
			try {
				div_id = modelJV_account.getValueAt(row, 1).toString().trim();
			} catch (NullPointerException e) {
				div_id = "";
			}
			try {
				dept_id = modelJV_account.getValueAt(row, 2).toString().trim();
			} catch (NullPointerException e) {
				dept_id = "";
			}
			try {
				proj_id = modelJV_account.getValueAt(row, 4).toString().trim();
			} catch (NullPointerException e) {
				proj_id = "";
			}
			try {
				subproj_id = modelJV_account.getValueAt(row, 5).toString().trim();
			} catch (NullPointerException e) {
				subproj_id = "";
			}

			if (column == 1 && !div_id.equals("")) {
				if (!isDivCorrect(div_id).equals("")) {
					modelJV_account.setValueAt(isDivCorrect(div_id), row, 11);
					String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
					generateDetail(lineno);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "Div ID does not exist.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					modelJV_account.setValueAt("", row, column);
				}
			}

			else if (column == 2 && !dept_id.equals("")) {
				if (!isDeptCorrect(dept_id).equals("")) {
					modelJV_account.setValueAt(isDeptCorrect(dept_id), row, 12);
					String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
					generateDetail(lineno);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "Dept ID does not exist.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					modelJV_account.setValueAt("", row, column);
				}
			}

			else if (column == 4 && !proj_id.equals("")) {
				if (!isProjCorrect(proj_id).equals("")) {
					modelJV_account.setValueAt(isProjCorrect(proj_id), row, 13);
					String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
					generateDetail(lineno);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "Project ID does not exist.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					modelJV_account.setValueAt("", row, column);
				}
			}

			else if (column == 5 && !subproj_id.equals("")) {
				if (!isSubProjCorrect(subproj_id, proj_id).equals("")) {
					modelJV_account.setValueAt(isSubProjCorrect(subproj_id, proj_id), row, 14);
					String lineno = (String) modelJV_account.getValueAt(tblJV_account.getSelectedRow(), 0);
					generateDetail(lineno);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "Subproject ID does not exist.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					modelJV_account.setValueAt("", row, column);
				}
			}
		}
	}

	private static String isDivCorrect(String div) {

		String isDivCorrect = "";

		String SQL = "select division_alias from mf_division where division_code = '" + div + "' ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				isDivCorrect = (String) db.getResult()[0][0];
			}
		} else {
			isDivCorrect = "";
		}

		return isDivCorrect;

	}

	private static String isDeptCorrect(String dept) {

		String isDeptCorrect = "";

		String SQL = "select dept_alias from mf_department where dept_code = '" + dept + "' ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				isDeptCorrect = (String) db.getResult()[0][0];
			}
		} else {
			isDeptCorrect = "";
		}

		return isDeptCorrect;

	}

	private static String isProjCorrect(String proj) {

		String isProjCorrect = "";

		String SQL = "select proj_alias from mf_project where proj_id = '" + proj + "' ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				isProjCorrect = (String) db.getResult()[0][0];
			}
		} else {
			isProjCorrect = "";
		}

		return isProjCorrect;

	}

	private static String isSubProjCorrect(String subproj, String proj_id) {

		String isSubProjCorrect = "";

		String SQL = "select phase from mf_sub_project where sub_proj_id = '" + subproj + "' and proj_id ='" + proj_id
				+ "' and status_id ='A'";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				isSubProjCorrect = (String) db.getResult()[0][0];
			}
		} else {
			isSubProjCorrect = "";
		}

		return isSubProjCorrect;

	}

	private void CloseAcctgPeriod() {
		pgUpdate db = new pgUpdate();

		String sqlDetail = "update mf_acctg_period set \n" + "status_id='I',\n" + "date_closed= now(), \n"
				+ "closed_by = '" + UserInfo.EmployeeCode + "'   \n" + " where period_year='" + lookupFiscalYr.getText()
				+ "' \n" + " and co_id='" + co_id + "' \n" + " and status_id='A'";

		System.out.printf("SQL #1 - updateAcctgPeriod: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

}