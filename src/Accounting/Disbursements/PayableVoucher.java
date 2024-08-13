package Accounting.Disbursements;

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

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelPV_SubLedger;
import tablemodel.modelPVaccount_entries;
import Accounting.GeneralLedger.JournalVoucher;
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
import TaxesAndPermits.Form2307_Monitoring;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class PayableVoucher extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/*
	 * AS OF 2021-11-22
	 * 
	 * 1. ADDITIONAL INFO IN REMARKS(SETUP PV) - INCLUDE AVAILER TYPE DCRF N0. 1830
	 * 2. CHANGE COMPANY ADDRESS FOR CENQ AND VDC DCRF NO. 1900 2021-12-28
	 * 
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Payable Voucher (PV)";

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlPVDtl_1;
	private JPanel pnlPVDtl_2;
	private JPanel pnlPVRequestType;
	private JPanel pnlPVDtl_1a;
	private JPanel pnlPVDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlPV;
	private JPanel pnlPV_a;
	private JPanel pnlPV_a1;
	private JPanel pnlPV_a2;
	private JPanel pnlPV_a2_1;
	private JPanel pnlPVDtl;
	private JPanel pnlPV_a2_3;
	private JPanel pnlPVInfo;
	private JPanel pnlPVInfo_1;
	private JPanel pnlPVDtl_2a;
	private JPanel pnlPVDtl_2b;
	private JPanel pnlPV_notes;
	private JPanel pnlDate;
	private JPanel pnlRemarks;
	private JPanel pnlPV_SL;
	private JPanel pnlPV_account;

	private JLabel lblCompany;
	static JLabel lblDateNeeded;
	public static JLabel lblPV_no;
	private static JLabel lblStatus;
	private static JLabel lblPayeeID1;
	private static JLabel lblPayeeID2;
	private static JLabel lblPayeeType;
	private JLabel lblDate;
	static JLabel lblDateTrans;
	private static JLabel lblRPLF_no;
	private static JLabel lblCV_no;
	static JLabel lblPaymentType;

	public static _JLookup lookupCompany;
	public static _JLookup lookupPV_no;
	public static _JLookup lookupDRF_no;
	private static _JLookup lookupPayee1;
	private static _JLookup lookupPayee2;
	private static _JLookup lookupPayeeType;
	static _JLookup lookupPaymentType;
	private static _JLookup lookupCV_no;

	public static modelPV_SubLedger modelPV_SL_total;
	public static modelPVaccount_entries modelPV_account;
	public static modelPV_SubLedger modelPV_SL;
	public static modelPVaccount_entries modelPV_accounttotal;

	private static _JTabbedPane tabCenter;

	public static _JTagLabel tagCompany;
	static _JTagLabel tagReqType;
	private static _JTagLabel tagPayee1;
	private static _JTagLabel tagPayee2;
	private static _JTagLabel tagPayeeType;
	private static _JTagLabel tagDetail;
	private static _JTagLabel tagDetail_1;

	public static JButton btnSave;
	public static JButton btnCancel;
	public static JButton btnAddNew;
	public static JButton btnRefresh;
	public static JButton btnEdit;
	public static JButton btnOK;
	public static JButton btnPreview;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	static _JDateChooser dteRequest;
	static _JDateChooser dteNeeded;
	private _JDateChooser dteRefDate;

	private JPopupMenu menu;
	private JScrollPane scpDRFRemark;
	private static _JScrollPaneMain scrollPV_SL;
	private static _JScrollPaneMain scrollPV_account;
	private _JScrollPaneTotal scrollPV_SLtotal;
	private static _JScrollPaneTotal scrollPV_accounttotal;

	public static JTextArea txtDRFRemark;
	private static JXTextField txtStatus;

	private JMenuItem mnidelete;
	private JMenuItem mniaddrow;

	private static _JTableMain tblPV_SL;
	public static _JTableMain tblPV_account;
	public static JList rowHeaderPV_SL;
	public static JList rowHeaderPV_account;

	private _JTableTotal tblPV_SLtotal;
	private static _JTableTotal tblPV_accounttotal;

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00");
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JPopupMenu menu2;
	private JMenuItem mniopenCV;
	private JMenuItem mniopenDP;
	private JMenuItem mniopenRPLF;

	String drf_no = ""; // this is used to denote the number upon displaying of existing/saved RPLF No.
	static String rplf_no = ""; // this is used to denote the RPLF number used for saving
	public static String pv_no = ""; // this is used to denote the PV number upon displaying of existing/saved PV No.
	public static String co_id = "02";
	static String availer_id = "";
	public static String availer = "";
	public static String company = "";
	public static String company_logo;
	static String pay_type = "";
	static String payee1 = "";
	Boolean is_payee_vatable = false;
	String table = "";
	public static String year = "";
	public static String month = "";
	private String cv_no = "";

	private JMenuItem mnisetup2307;
	private JMenuItem mniprint2307;
	private JMenuItem mniprintOR;
	private JMenuItem mnieditePVdate;
	// private JMenuItem mnieditePVpayee;
	public static JMenuItem mniInactivate;
	static String rplf_type_id = "";
	static String to_do = "addnew";
	String lineno = "";
	String lineno_1 = "";
	private JMenuItem mnireversePV;
	private JPanel pnlSub;
	private JPanel pnlSubNorth;
	private JLabel lblEntry;
	private JPanel pnlSubCenter;
	private JTextField txtEntry;
	private JPanel pnlSubButton;
	private JButton btnSubPrev;
	private static JCheckBox chkCurrentYear;

	// static String isCurrentYear = "2018";

	static String isCurrentYear = getCurrentYear();// **added by JED 2019-01-03 : to get current year for PV no
													// lookup**//

	public PayableVoucher() {
		super(title, true, true, true, true);
		initGUI();
	}

	public PayableVoucher(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public PayableVoucher(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(935, 538));
		this.setBounds(0, 0, 935, 538);

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
			mnidelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
				}
			});
			mniaddrow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
				}
			});
		}

		{
			menu2 = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenCV = new JMenuItem("Open Disbursement Voucher");
			mniopenDP = new JMenuItem("Open Docs Processing");
			mnisetup2307 = new JMenuItem("Open Form 2307 Monitoring");
			mniprint2307 = new JMenuItem("Preview Form 2307");
			mniprintOR = new JMenuItem("Preview Invoice");
			mniInactivate = new JMenuItem("Inactivate this PV");
			mnieditePVdate = new JMenuItem("Edit PV Paid");
			mnireversePV = new JMenuItem("Reverse PV");
			// mnieditePVpayee = new JMenuItem("Edit PV Payee");
			mniInactivate.setEnabled(false);
			mnireversePV.setEnabled(false);

			JSeparator sp1 = new JSeparator();
			JSeparator sp2 = new JSeparator();

			menu2.add(mniopenRPLF);
			menu2.add(mniopenCV);
			menu2.add(mniopenDP);
			menu2.add(sp1);
			menu2.add(mnisetup2307);
			menu2.add(mniprint2307);
			menu2.add(mniprintOR);
			menu2.add(sp2);
			menu2.add(mniInactivate);
			menu2.add(mnieditePVdate);
			menu2.add(mnireversePV);

			mniopenRPLF.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (FncGlobal.homeMDI.isNotExisting("RequestForPayment")) {
						openDRF();
					}
				}
			});
			mniopenCV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (FncGlobal.homeMDI.isNotExisting("CheckVoucher")) {
						openCV();
					}
				}
			});
			mniopenDP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (FncGlobal.homeMDI.isNotExisting("DocsProcessing")) {
						openDP();
					}
				}
			});
			mnisetup2307.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (FncGlobal.homeMDI.isNotExisting("EWT_2307")) {
						open2307();
					}
				}
			});
			mniprint2307.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					print2307();
				}
			});
			mniprintOR.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					printOR();
				}
			});
			mniInactivate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					inactivatePV();
				}
			});
			mnieditePVdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					editPVPaid();
				}
			});
			mnireversePV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (isAlreadyReversed() == true) {
						{
							JOptionPane.showMessageDialog(getContentPane(),
									"This PV has already been reversed." + "\n" + "See JV No. " + sql_getReversal_jv(),
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {

						if (JOptionPane.showConfirmDialog(null, "Are you sure you want to reverse?", "Confirmation",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							reverse_pv();
						}

					}
				}
			});

		}
		{
			pnlSub = new JPanel(new BorderLayout(5, 5));
			pnlSub.setPreferredSize(new Dimension(300, 75));
			pnlSub.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlSubNorth = new JPanel(new BorderLayout(5, 5));
				pnlSub.add(pnlSubNorth, BorderLayout.WEST);
				// pnlSubNorth.setBorder(BorderFactory.createLineBorder(Color.BLUE));
				{
					lblEntry = new JLabel("Input text here:");
					pnlSubNorth.add(lblEntry);
				}
			}
			{
				pnlSubCenter = new JPanel(new BorderLayout(5, 5));
				pnlSub.add(pnlSubCenter, BorderLayout.CENTER);
				{
					txtEntry = new JTextField();
					pnlSubCenter.add(txtEntry);
				}
			}
			{
				pnlSubButton = new JPanel(new BorderLayout(3, 3));
				pnlSub.add(pnlSubButton, BorderLayout.SOUTH);
				pnlSubButton.setPreferredSize(new Dimension(30, 30));
				{
					btnSubPrev = new JButton("Preview");
					pnlSubButton.add(btnSubPrev);
					btnSubPrev.setActionCommand("Sub Preview");
					btnSubPrev.addActionListener(this);
				}
			}

		}
		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 207));

			pnlComp = new JPanel(new BorderLayout(5, 0));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);
			pnlNorth.addMouseListener(new PopupTriggerListener_panel2());

			// company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlComp.add(pnlComp_a, BorderLayout.NORTH);
			pnlComp_a.setPreferredSize(new java.awt.Dimension(610, 30));
			pnlComp_a.addMouseListener(new PopupTriggerListener_panel2());

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

							cancel();

							co_id = (String) data[0];
							company = (String) data[1];
							tagCompany.setTag((String) data[1]);
							company_logo = (String) data[3];

							lblPV_no.setEnabled(true);
							lookupPV_no.setEnabled(true);
							// lookupPV_no.setEditable(true);
							System.out.printf(getPV_no());
							lookupPV_no.setLookupSQL(getPV_no());
							lookupDRF_no.setLookupSQL(getDRF_no());

							enable_buttons(true, false, false, false, false, true);
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
			}

			pnlPV = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlPV, BorderLayout.CENTER);
			pnlPV.setPreferredSize(new java.awt.Dimension(921, 233));
			pnlPV.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlPV.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlPV_a = new JPanel(new BorderLayout(5, 5));
			pnlPV.add(pnlPV_a, BorderLayout.NORTH);
			pnlPV_a.setPreferredSize(new java.awt.Dimension(911, 42));
			pnlPV_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlPV_a.setBorder(lineBorder);
			pnlPV_a.addMouseListener(new PopupTriggerListener_panel2());

			pnlPV_a1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlPV_a.add(pnlPV_a1, BorderLayout.WEST);
			pnlPV_a1.setPreferredSize(new java.awt.Dimension(92, 40));
			pnlPV_a1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				lblPV_no = new JLabel("PV No.", JLabel.TRAILING);
				pnlPV_a1.add(lblPV_no);
				lblPV_no.setEnabled(false);
				lblPV_no.setPreferredSize(new java.awt.Dimension(86, 40));
				lblPV_no.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
				lblPV_no.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
			}

			pnlPV_a2 = new JPanel(new BorderLayout(5, 5));
			pnlPV_a.add(pnlPV_a2, BorderLayout.CENTER);
			pnlPV_a2.setPreferredSize(new java.awt.Dimension(814, 40));
			pnlPV_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			pnlPV_a2_1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlPV_a2.add(pnlPV_a2_1, BorderLayout.WEST);
			pnlPV_a2_1.setPreferredSize(new java.awt.Dimension(286, 24));

			{
				lookupPV_no = new _JLookup(null, "PV No.", 2, 2);
				pnlPV_a2_1.add(lookupPV_no);
				lookupPV_no.setBounds(20, 27, 20, 25);
				lookupPV_no.setLookupSQL(getPV_no());
				lookupPV_no.setReturnColumn(0);
				lookupPV_no.setEnabled(false);
				lookupPV_no.setPreferredSize(new java.awt.Dimension(157, 22));
				lookupPV_no.addLookupListener(new LookupListener() {

					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						if (data != null) {

							refresh_fields();

							pv_no = (String) data[0];
							lookupPV_no.setValue(pv_no);
							tagDetail.setText(null);

							setPV_header(pv_no);
							displayPV_details(modelPV_account, rowHeaderPV_account, modelPV_accounttotal, pv_no);
							displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, pv_no);

							// ----added by JED 2018-09-27: DCRF no. 786 for disabling auto reversal of
							// Posted PV with active, tagged, posted paid out DV
							cv_no = lookupCV_no.getText();
							mnireversePV.setEnabled(false);

							/*
							 * //set particulars Integer w = 1; Integer line_count =
							 * sql_getDRF_line_count(pv_no); String part_desc; String particulars = "";
							 * while (w<=line_count){ part_desc = getDRF_particulars(w,
							 * pv_no).toString().trim(); particulars = ""+particulars+"  "+part_desc+"  \n"
							 * ;
							 * 
							 * w++; } txtDRFRemark.setText(particulars);
							 */

//							if (getPV_status(pv_no).equals("P")&&lookupPV_no.isEnabled()) 
//							{								
//								btnSave.setText("Post");  
//								btnSave.setActionCommand("Post");		
//								enable_buttons(false, false, false, true, true, true);
//								mniInactivate.setEnabled(false);
//								mnireversePV.setEnabled(true);
//							} 
							if (getPV_status(pv_no).equals("P") && (checkDV_status_id(cv_no, co_id).equals("D")
									|| cv_no.equals(null) || cv_no.equals("")) && lookupPV_no.isEnabled()) {// ----modified
																											// by JED
																											// 2018-09-27:
																											// DCRF no.
																											// 786 for
																											// disabling
																											// auto
																											// reversal
																											// of Posted
																											// PV with
																											// active,
																											// tagged,
																											// posted
																											// paid out
																											// DV
								btnSave.setText("Post");
								btnSave.setActionCommand("Post");
								enable_buttons(false, false, false, true, true, true);
								mniInactivate.setEnabled(false);
								mnireversePV.setEnabled(true);
							} else if (getPV_status(pv_no).equals("P") && lookupPV_no.isEnabled()) {
								btnSave.setText("Post");
								btnSave.setActionCommand("Post");
								enable_buttons(false, false, false, true, true, true);
								mniInactivate.setEnabled(false);
								mnireversePV.setEnabled(false);
							} else if (getPV_status(pv_no).equals("F")) {
								btnEdit.setText("Untag");
								btnEdit.setActionCommand("Untag");
								btnSave.setText("Post");
								btnSave.setActionCommand("Post");
								enable_buttons(false, true, true, true, true, true);
								mniInactivate.setEnabled(false);
								mnireversePV.setEnabled(false);
							} else if (getPV_status(pv_no).equals("A")) {
								btnSave.setText("Tag");
								btnSave.setActionCommand("Tag");
								btnEdit.setText("Edit");
								btnEdit.setActionCommand("Edit");
								enable_buttons(false, true, true, true, true, true);
								mniInactivate.setEnabled(true);
								mnireversePV.setEnabled(false);
							}

							modelPV_account.setEditable(false);
							tblPV_account.setEditable(false);
						}
					}
				});
			}

			{
				chkCurrentYear = new JCheckBox("current year only?");
				pnlPV_a2_1.add(chkCurrentYear);
				chkCurrentYear.setEnabled(true);
				chkCurrentYear.setSelected(true);
				chkCurrentYear.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 9));
				chkCurrentYear.setPreferredSize(new java.awt.Dimension(148, 24));
				chkCurrentYear.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {

						if (chkCurrentYear.isSelected() == true) {
							// isCurrentYear = "2018";
							isCurrentYear = getCurrentYear();
							lookupPV_no.setLookupSQL(getPV_no());
						}

						else {
							isCurrentYear = "";
							lookupPV_no.setLookupSQL(getPV_no());
						}
					}
				});
			}

			pnlPV_a2_3 = new JPanel(new GridLayout(1, 2, 5, 0));
			pnlPV_a2.add(pnlPV_a2_3, BorderLayout.EAST);
			pnlPV_a2_3.setPreferredSize(new java.awt.Dimension(310, 20));
			pnlPV_a2_3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				lblStatus = new JLabel("Status", JLabel.TRAILING);
				pnlPV_a2_3.add(lblStatus);
				lblStatus.setEnabled(false);
			}
			{
				txtStatus = new JXTextField("");
				pnlPV_a2_3.add(txtStatus);
				txtStatus.setEnabled(false);
				txtStatus.setEditable(false);
				txtStatus.setBounds(120, 25, 300, 22);
				txtStatus.setHorizontalAlignment(JTextField.CENTER);
				txtStatus.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
			}
			{
				pnlPVDtl = new JPanel(new BorderLayout(0, 5));
				pnlPV.add(pnlPVDtl, BorderLayout.WEST);
				pnlPVDtl.setPreferredSize(new java.awt.Dimension(911, 187));

				pnlPVDtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlPVDtl.add(pnlPVDtl_1, BorderLayout.WEST);
				pnlPVDtl_1.setPreferredSize(new java.awt.Dimension(221, 116));
				pnlPVDtl_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlPVDtl_1a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlPVDtl_1.add(pnlPVDtl_1a, BorderLayout.WEST);
				pnlPVDtl_1a.setPreferredSize(new java.awt.Dimension(99, 116));
				pnlPVDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				{
					lblDateTrans = new JLabel("Trans. Date", JLabel.TRAILING);
					pnlPVDtl_1a.add(lblDateTrans);
					lblDateTrans.setEnabled(false);
				}
				{
					lblDateNeeded = new JLabel("Due Date", JLabel.TRAILING);
					pnlPVDtl_1a.add(lblDateNeeded);
					lblDateNeeded.setEnabled(false);
				}
				{
					lblRPLF_no = new JLabel("RPLF No.", JLabel.TRAILING);
					pnlPVDtl_1a.add(lblRPLF_no);
					lblRPLF_no.setEnabled(false);
				}
				{
					lblCV_no = new JLabel("CV No.", JLabel.TRAILING);
					pnlPVDtl_1a.add(lblCV_no);
					lblCV_no.setEnabled(false);
				}

				pnlPVDtl_1b = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlPVDtl_1.add(pnlPVDtl_1b, BorderLayout.CENTER);
				pnlPVDtl_1b.setPreferredSize(new java.awt.Dimension(135, 119));
				pnlPVDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					dteRequest = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlPVDtl_1b.add(dteRequest);
					dteRequest.setBounds(485, 7, 125, 21);
					dteRequest.setDate(null);
					dteRequest.setEnabled(false);
					dteRequest.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor) dteRequest.getDateEditor()).setEditable(false);
					dteRequest.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}
				{
					dteNeeded = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlPVDtl_1b.add(dteNeeded);
					dteNeeded.setBounds(485, 7, 125, 21);
					dteNeeded.setDate(null);
					dteNeeded.setEnabled(false);
					dteNeeded.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor) dteNeeded.getDateEditor()).setEditable(false);
					dteNeeded.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}
				{
					lookupDRF_no = new _JLookup(null, "DRF No.", 2, 2);
					pnlPVDtl_1b.add(lookupDRF_no);
					lookupDRF_no.setBounds(20, 27, 20, 25);
					lookupDRF_no.setReturnColumn(0);
					lookupDRF_no.setEnabled(false);
					// lookupDRF_no.setEditable(false);
					lookupDRF_no.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupDRF_no.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								rplf_no = (String) data[0];

								lblDateTrans.setEnabled(true);
								lblDateNeeded.setEnabled(true);
								dteRequest.setEnabled(false);
								dteNeeded.setEnabled(true);
								dteNeeded.setEditable(true);
								lblPaymentType.setEnabled(true);
								lookupPaymentType.setEnabled(true);
								// lookupPaymentType.setEditable(true);
								tagReqType.setEnabled(true);
								lookupPaymentType.setLookupSQL(getPayment_type());
								rplf_type_id = getRPLF_typeID(rplf_no);

								setDRF_header();
								displayDRF_toPVdetails(modelPV_account, rowHeaderPV_account, modelPV_accounttotal);
								displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, rplf_no);

								btnSave.setEnabled(true);
								modelPV_account.setEditable(false);

							}
						}
					});
				}
				{
					lookupCV_no = new _JLookup(null, "CV No.", 2, 2);
					pnlPVDtl_1b.add(lookupCV_no);
					lookupCV_no.setBounds(20, 27, 20, 25);
					lookupCV_no.setReturnColumn(0);
					lookupCV_no.setEnabled(false);
					// lookupCV_no.setEditable(false);
					lookupCV_no.setPreferredSize(new java.awt.Dimension(157, 22));
				}

				// Start of Left Panel
				pnlPVInfo = new JPanel(new BorderLayout(0, 0));
				pnlPVDtl.add(pnlPVInfo, BorderLayout.EAST);
				pnlPVInfo.setPreferredSize(new java.awt.Dimension(694, 113));
				pnlPVInfo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

				pnlPVInfo_1 = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlPVInfo.add(pnlPVInfo_1, BorderLayout.WEST);
				pnlPVInfo_1.setPreferredSize(new java.awt.Dimension(112, 113));
				pnlPVInfo_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblPaymentType = new JLabel("Payment Type", JLabel.TRAILING);
					pnlPVInfo_1.add(lblPaymentType);
					lblPaymentType.setEnabled(false);
				}
				{
					lblPayeeID1 = new JLabel("Payee", JLabel.TRAILING);
					pnlPVInfo_1.add(lblPayeeID1);
					lblPayeeID1.setEnabled(false);
				}
				{
					lblPayeeID2 = new JLabel("Availer", JLabel.TRAILING);
					pnlPVInfo_1.add(lblPayeeID2);
					lblPayeeID2.setEnabled(false);
				}
				{
					lblPayeeType = new JLabel("Payee Type", JLabel.TRAILING);
					pnlPVInfo_1.add(lblPayeeType);
					lblPayeeType.setEnabled(false);
				}

				pnlPVDtl_2 = new JPanel(new BorderLayout(5, 0));
				pnlPVInfo.add(pnlPVDtl_2, BorderLayout.CENTER);
				pnlPVDtl_2.setPreferredSize(new java.awt.Dimension(203, 118));
				pnlPVDtl_2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

				pnlPVDtl_2a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlPVDtl_2.add(pnlPVDtl_2a, BorderLayout.WEST);
				pnlPVDtl_2a.setPreferredSize(new java.awt.Dimension(119, 119));
				pnlPVDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lookupPaymentType = new _JLookup(null, "Payment Type", 2, 2);
					pnlPVDtl_2a.add(lookupPaymentType);
					lookupPaymentType.setBounds(20, 27, 20, 25);
					lookupPaymentType.setReturnColumn(0);
					lookupPaymentType.setEnabled(false);
					lookupPaymentType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPaymentType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								String rplf_type = (String) data[1];
								tagReqType.setTag(rplf_type);
							}
						}
					});
				}
				{
					lookupPayee1 = new _JLookup(null, "Payee", 2, 2);
					pnlPVDtl_2a.add(lookupPayee1);
					lookupPayee1.setBounds(20, 27, 20, 25);
					lookupPayee1.setReturnColumn(0);
					lookupPayee1.setLookupSQL(getEntityList());
					lookupPayee1.setEnabled(false);
					// lookupPayee1.setEditable(false);
					lookupPayee1.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPayee1.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								String entity_name = (String) data[1];

								tagPayee1.setTag(entity_name);
							}
						}
					});
				}
				{
					lookupPayee2 = new _JLookup(null, "Availer", 2, 2);
					pnlPVDtl_2a.add(lookupPayee2);
					lookupPayee2.setBounds(20, 27, 20, 25);
					lookupPayee2.setReturnColumn(0);
					lookupPayee2.setEnabled(false);
					// lookupPayee2.setEditable(false);
					lookupPayee2.setPreferredSize(new java.awt.Dimension(157, 22));
				}
				{
					lookupPayeeType = new _JLookup(null, "Payee Type", 2, 2);
					pnlPVDtl_2a.add(lookupPayeeType);
					lookupPayeeType.setBounds(20, 27, 20, 25);
					lookupPayeeType.setReturnColumn(0);
					lookupPayeeType.setEnabled(false);
					// lookupPayeeType.setEditable(false);
					lookupPayeeType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupPayeeType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								String payee_type = (String) data[1];
								tagPayeeType.setTag(payee_type);
							}
						}
					});
				}

				pnlPVDtl_2b = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlPVDtl_2.add(pnlPVDtl_2b, BorderLayout.CENTER);
				pnlPVDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
				pnlPVDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					tagReqType = new _JTagLabel("[ ]");
					pnlPVDtl_2b.add(tagReqType);
					tagReqType.setBounds(209, 27, 700, 22);
					tagReqType.setEnabled(false);
					tagReqType.setPreferredSize(new java.awt.Dimension(27, 33));
					tagReqType.addMouseListener(new PopupTriggerListener_panel2());
				}
				{
					tagPayee1 = new _JTagLabel("[ ]");
					pnlPVDtl_2b.add(tagPayee1);
					tagPayee1.setBounds(209, 27, 700, 22);
					tagPayee1.setEnabled(false);
					tagPayee1.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPayee1.addMouseListener(new PopupTriggerListener_panel2());
				}
				{
					tagPayee2 = new _JTagLabel("[ ]");
					pnlPVDtl_2b.add(tagPayee2);
					tagPayee2.setBounds(209, 27, 700, 22);
					tagPayee2.setEnabled(false);
					tagPayee2.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPayee2.addMouseListener(new PopupTriggerListener_panel2());
				}
				{
					tagPayeeType = new _JTagLabel("[ ]");
					pnlPVDtl_2b.add(tagPayeeType);
					tagPayeeType.setBounds(209, 27, 700, 22);
					tagPayeeType.setEnabled(false);
					tagPayeeType.setPreferredSize(new java.awt.Dimension(27, 33));
					tagPayeeType.addMouseListener(new PopupTriggerListener_panel2());
				}
				
				pnlPVRequestType = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlPVDtl_2.add(pnlPVRequestType, BorderLayout.EAST);
				pnlPVRequestType.setPreferredSize(new java.awt.Dimension(140, 118));
				pnlPVRequestType.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				
				{
					
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

			pnlPV = new JPanel();
			pnlTable.add(pnlPV, BorderLayout.CENTER);
			pnlPV.setLayout(new BorderLayout(5, 5));
			pnlPV.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlPV.setBorder(lineBorder);
			pnlPV.addMouseListener(new PopupTriggerListener_panel());

			tabCenter = new _JTabbedPane();
			pnlPV.add(tabCenter, BorderLayout.CENTER);

			{
				pnlPV_account = new JPanel(new BorderLayout());
				tabCenter.addTab("Account Entries", null, pnlPV_account, null);
				pnlPV_account.setPreferredSize(new java.awt.Dimension(1183, 365));

				{
					tagDetail = new _JTagLabel("");
					pnlPV_account.add(tagDetail, BorderLayout.NORTH);
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
					scrollPV_account = new _JScrollPaneMain();
					pnlPV_account.add(scrollPV_account, BorderLayout.CENTER);
					{
						modelPV_account = new modelPVaccount_entries();

						tblPV_account = new _JTableMain(modelPV_account);
						scrollPV_account.setViewportView(tblPV_account);
						tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
						tblPV_account.hideColumns("Cor Entry");
						tblPV_account.addMouseListener(this);
						tblPV_account.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								tblPV_account.packAll();
								table = "tbl_account";
								tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
							}

							public void keyPressed(KeyEvent e) {
								tblPV_account.packAll();
								table = "tbl_account";
								tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
							}

						});
						tblPV_account.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblPV_account.rowAtPoint(e.getPoint()) == -1) {
									tblPV_accounttotal.clearSelection();
									table = "tbl_account";
								} else {
									tblPV_account.setCellSelectionEnabled(true);
									table = "tbl_account";
								}
							}

							public void mouseReleased(MouseEvent e) {
								if (tblPV_account.rowAtPoint(e.getPoint()) == -1) {
									tblPV_accounttotal.clearSelection();
									table = "tbl_account";
								} else {
									tblPV_account.setCellSelectionEnabled(true);
									table = "tbl_account";
								}
							}
						});

						tblPV_account.hideColumns("div");
						tblPV_account.hideColumns("dep");
						tblPV_account.hideColumns("proj");
						tblPV_account.hideColumns("sub");
						tblPV_account.hideColumns("availer");

						tblPV_account.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								try {
									if (!arg0.getValueIsAdjusting()) {

										lineno = (String) modelPV_account.getValueAt(tblPV_account.getSelectedRow(), 0);
										generateDetail(lineno);
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						});
						tblPV_account.putClientProperty("terminateEditOnFocusLost", true);
					}
					{
						rowHeaderPV_account = tblPV_account.getRowHeader22();
						scrollPV_account.setRowHeaderView(rowHeaderPV_account);
						scrollPV_account.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
								FncTables.getRowHeader_Header());
					}
				}
				{
					scrollPV_accounttotal = new _JScrollPaneTotal(scrollPV_account);
					pnlPV_account.add(scrollPV_accounttotal, BorderLayout.SOUTH);
					{
						modelPV_accounttotal = new modelPVaccount_entries();
						modelPV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null,
								new BigDecimal(0.00), new BigDecimal(0.00) });

						tblPV_accounttotal = new _JTableTotal(modelPV_accounttotal, tblPV_account);
						tblPV_accounttotal.setFont(dialog11Bold);
						scrollPV_accounttotal.setViewportView(tblPV_accounttotal);
						((_JTableTotal) tblPV_accounttotal).setTotalLabel(0);
					}
				}
			}

			{
				pnlPV_SL = new JPanel(new BorderLayout());
				tabCenter.addTab("Subsidiary Ledgers", null, pnlPV_SL, null);
				pnlPV_SL.setPreferredSize(new java.awt.Dimension(1183, 365));

				{// **ADDED BY JED 2020-08-03 : DISPLAY TAG DETAILS IN SUBSIDIARY LEDGER**//
					tagDetail_1 = new _JTagLabel("");
					pnlPV_SL.add(tagDetail_1, BorderLayout.NORTH);
					tagDetail_1.getDocument().addDocumentListener(new DocumentListener() {
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
							if (tagDetail_1.getText().contains("null")) {
								tagDetail_1.setForeground(Color.RED);
							} else {
								tagDetail_1.setForeground(Color.BLACK);
							}
						}
					});

				}

				{
					scrollPV_SL = new _JScrollPaneMain();
					pnlPV_SL.add(scrollPV_SL, BorderLayout.CENTER);
					{
						modelPV_SL = new modelPV_SubLedger();

						tblPV_SL = new _JTableMain(modelPV_SL);
						scrollPV_SL.setViewportView(tblPV_SL);
						tblPV_SL.addMouseListener(this);
						tblPV_SL.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								tblPV_SL.packAll();
								table = "tbl_SL";
							}

							public void keyPressed(KeyEvent e) {
								tblPV_SL.packAll();
								table = "tbl_SL";
							}

						});
						tblPV_SL.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblPV_SL.rowAtPoint(e.getPoint()) == -1) {
									tblPV_SLtotal.clearSelection();
									table = "tbl_SL";
								} else {
									tblPV_SL.setCellSelectionEnabled(true);
									table = "tbl_SL";
								}
							}

							public void mouseReleased(MouseEvent e) {
								if (tblPV_SL.rowAtPoint(e.getPoint()) == -1) {
									tblPV_SLtotal.clearSelection();
									table = "tbl_SL";
								} else {
									tblPV_SL.setCellSelectionEnabled(true);
									table = "tbl_SL";
								}
							}
						});

						tblPV_SL.getSelectionModel().addListSelectionListener(new ListSelectionListener() {// **ADDED BY
																											// JED
																											// 2020-08-03
																											// : DISPLAY
																											// TAG
																											// DETAILS
																											// IN
																											// SUBSIDIARY
																											// LEDGER**//
							public void valueChanged(ListSelectionEvent arg0) {
								try {
									if (!arg0.getValueIsAdjusting()) {

										lineno_1 = (String) modelPV_SL.getValueAt(tblPV_SL.getSelectedRow(), 0);
										generateDetail_SL(lineno_1);
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						});

					}

					tblPV_SL.hideColumns("div");
					tblPV_SL.hideColumns("dep");
					tblPV_SL.hideColumns("proj");
					tblPV_SL.hideColumns("sub");

					{
						rowHeaderPV_SL = tblPV_SL.getRowHeader22();
						scrollPV_SL.setRowHeaderView(rowHeaderPV_SL);
						scrollPV_SL.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollPV_SLtotal = new _JScrollPaneTotal(scrollPV_SL);
					pnlPV_SL.add(scrollPV_SLtotal, BorderLayout.SOUTH);
					{
						modelPV_SL_total = new modelPV_SubLedger();
						modelPV_SL_total
								.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00),
										new BigDecimal(0.00), null, null, null, null, null, null, null, null });

						tblPV_SLtotal = new _JTableTotal(modelPV_SL_total, tblPV_SL);
						tblPV_SLtotal.setFont(dialog11Bold);
						scrollPV_SLtotal.setViewportView(tblPV_SLtotal);
						((_JTableTotal) tblPV_SLtotal).setTotalLabel(0);
					}
				}
			}
			{
				pnlPV_notes = new JPanel(new BorderLayout());
				tabCenter.addTab("Particulars", null, pnlPV_notes, null);
				pnlPV_notes.setPreferredSize(new java.awt.Dimension(100, 365));

				pnlRemarks = new JPanel(new BorderLayout(5, 5));
				pnlPV_notes.add(pnlRemarks, BorderLayout.CENTER);
				pnlRemarks.setPreferredSize(new java.awt.Dimension(100, 1000));

				scpDRFRemark = new JScrollPane();
				pnlRemarks.add(scpDRFRemark);
				scpDRFRemark.setBounds(82, 7, 150, 61);
				scpDRFRemark.setOpaque(true);
				scpDRFRemark.setPreferredSize(new java.awt.Dimension(100, 10000));

				{
					txtDRFRemark = new JTextArea();
					scpDRFRemark.add(txtDRFRemark);
					scpDRFRemark.setViewportView(txtDRFRemark);
					txtDRFRemark.setBounds(77, 3, 250, 81);
					txtDRFRemark.setLineWrap(true);
					txtDRFRemark.setPreferredSize(new java.awt.Dimension(100, 20000));
					txtDRFRemark.setEditable(true);
					txtDRFRemark.setEnabled(true);
					txtDRFRemark.setText("");
				}

			}

		}
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new Dimension(55, 30));
			{
				JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

				{
					btnAddNew = new JButton("Add New");
					pnlSouthCenter.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenter.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
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
					btnRefresh = new JButton("Refresh");
					pnlSouthCenter.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenter.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}

		// added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	// display tables
	public static void displayPV_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String req_no) {// used

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "---------display PV details\r\n" + "\r\n" + "select\r\n" + "a.acct_id,\r\n" + "a.div_id,\r\n"
				+ "a.dept_id,\r\n" + "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n"
				+ "b.acct_name,\r\n" + "(case when a.bal_side = 'D' then a.tran_amt else 0 end) as debit,\r\n"
				+ "(case when a.bal_side = 'C' then a.tran_amt else 0 end) as credit, a.corollary_entry \r\n"
//				+ "get_div_alias(a.div_id),			\r\n" + "get_department_alias_new(a.dept_id),			\r\n"
//				+ "get_project_alias(a.project_id),			\r\n"
//		//		+ "get_sub_proj_alias(a.sub_projectid),			\r\n"
//				+ "f.sub_proj_id,	\n"
//				+ "(select get_client_name(entity_id2) from rf_pv_header where pv_no = a.pv_no and co_id =  '"
//				+ lookupCompany.getValue() + "')			\r\n" + "\r\n" 
				+ "from rf_pv_detail a\r\n"
				+ "left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n" + "\r\n" 
				+ "left join mf_sub_project f on a.sub_projectid = f.sub_proj_id and a.project_id = f.proj_id and f.status_id ='A'"
				+ "where trim(pv_no) = '"+ req_no + "' and a.status_id = 'A' and a.co_id = '" + lookupCompany.getValue() + "' \r\n"
				+ "order by a.line_no\r\n";

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display SQL", sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalPV(modelMain, modelTotal);
		}

		else {
			modelPV_accounttotal = new modelPVaccount_entries();
			modelPV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null,
					new BigDecimal(0.00), new BigDecimal(0.00) });

			tblPV_accounttotal = new _JTableTotal(modelPV_accounttotal, tblPV_account);
			tblPV_accounttotal.setFont(dialog11Bold);
			scrollPV_accounttotal.setViewportView(tblPV_accounttotal);
			((_JTableTotal) tblPV_accounttotal).setTotalLabel(0);
		}

		tblPV_account.packAll();
		tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);

		enable_fields(false);
		btnCancel.setEnabled(true);
		btnRefresh.setEnabled(true);
		lblPV_no.setEnabled(true);
		lookupPV_no.setEnabled(true);
		// lookupPV_no.setEditable(true);
		modelPV_account.setEditable(false);
		modelPV_SL.setEditable(false);
		adjustRowHeight_account();
		adjustRowHeight_SL();

	}
	//Display Disbursement to PV
	public static void displayDRF_toPVdetails(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal) {// used

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		Integer w = 1;
		Integer u = 1;
		Integer line_count = sql_getDRF_line_count(rplf_no);
		String part_desc, availerNameAmt;
		String particulars = "";

		String drf_remarks = getDRF_remarks(rplf_no);
		String or_no_ref,mc_no;

		/*while (w <= line_count) {

			try {
				part_desc = getDRF_particulars(w, rplf_no).toString().trim();
			} catch (NullPointerException e) {
				part_desc = "";
			}
			if (part_desc.equals("")) {

			} else {
				particulars = "" + particulars + "  " + part_desc + "  \n";
			}
			System.out.println("1st while loop");
			w++;
		}*/
		try {
			part_desc = getDRF_particulars(lookupCompany.getValue(), rplf_no).toString().trim();
		} catch (NullPointerException e) {
			part_desc = "";
		}
		
		if (part_desc.equals("")) {

		} else {
			particulars = "" + particulars + "  " + part_desc + "  \n";
		}
		
		//Added by Erick 2023-08-17
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
			 mc_no = FncGlobal.GetString("select 'Mc No.: ' || coalesce(string_agg(c.mc_no, ','),'No MC') as mc_no\n"
					+ "from ( select distinct on (pv_no) pv_no, co_id  from rf_pv_detail\n"
					+ "where co_id = '"+co_id+"' and pv_no in ( select pv_no from  rf_pv_header where pv_no = '"+rplf_no+"'  and co_id = '04'  and status_id != 'I' and NULLIF(pv_no, '') is not null) \n"
					+ "and status_id = 'A' and bal_side = 'D' group by pv_no, co_id) a\n"
					+ "left join rf_pv_header b on a.pv_no = b.pv_no and a.co_id = b.co_id\n"
					+ "left join rf_mc_detail c on a.pv_no = c.pv_no and a.co_id = c.co_id\n"
					+ "and a.co_id = b.co_id");
		}else {
			 or_no_ref  = "";
			 mc_no = "";
		}
		System.out.println("or_no_ref: "+or_no_ref);
		System.out.println("mc_no: "+mc_no);
		particulars = particulars + " " + drf_remarks + "\n"+"\n"+or_no_ref+ "\n" +mc_no+"\n";

		/* Added on 05/18/2018 - DCRF No, 586 */
		while (u <= line_count) {

			try {
				availerNameAmt = getDRFAvailerAmt(u, rplf_no).toString().trim();
			} catch (NullPointerException e) {
				availerNameAmt = "";
			}
			particulars = particulars + "  \n" + "  " + availerNameAmt;
			System.out.println("2nd while loop");
			u++;
		}

		String sql = "---------display DRF details to PV tables \r\n" +
				"\n" +
				"select distinct on (a.acct_id, a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid) \r\n";

		// if(rplf_type_id.equals("07")) { sql = sql + "'01-02-04-000', \n"; }
		if (rplf_type_id.equals("02") || rplf_type_id.equals("07")) {
			sql = sql + "'01-02-04-000',  \n";
		} else if (rplf_type_id.equals("05")) {
			sql = sql + "'01-02-09-001', \n";
		} else if (rplf_type_id.equals("12")) {
			sql = sql + "'01-02-07-001', \n";
		} else if (rplf_type_id.equals("13")) {
			sql = sql + "'01-02-05-003', \n";
		} else {
			sql = sql + "a.acct_id,\r\n";
		}
		sql = sql +

				"a.div_id,\r\n" + "a.dept_id,\r\n" + "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n";

		// if(rplf_type_id.equals("07")) { sql = sql + "'Advances to Officers &
		// Employees', sum(a.amount) as gr_amt, \n"; }
		if (rplf_type_id.equals("02") || rplf_type_id.equals("07")){
			sql = sql + "'Advances to Officers & Employees', sum(a.pv_amt) as gr_amt,  \n";
		} else if (rplf_type_id.equals("05")) {
			sql = sql + "'Advances to Brokers', sum(a.amount) as gr_amt, \n";
		} else if (rplf_type_id.equals("12")) {
			sql = sql + "'Advances to Contractors - Down Payment', sum(a.amount) as gr_amt, \n";
		} else if (rplf_type_id.equals("13")) {
			sql = sql + "'Advances to OTB', sum(a.amount) as gr_amt,  \n";
		} else {
			sql = sql + "b.acct_name, sum(a.exp_amt) as exp_amt, \n";
		}
		sql = sql +

				"0.00, false  \r\n" + "\r\n" + "from rf_request_detail a \r\n"
				+ "left join mf_boi_chart_of_accounts b on a.acct_id=b.acct_id \n " + "where a.rplf_no = '" + rplf_no
				+ "' and a.status_id = 'A' and a.co_id = '" + lookupCompany.getValue() + "' \r\n"
				+ "group by a.acct_id, a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid, b.acct_name \n";

		if (rplf_type_id.equals("02") || rplf_type_id.equals("07") || rplf_type_id.equals("12")
				|| rplf_type_id.equals("13") || rplf_type_id.equals("05")) {
		} else {
			
			if(rplf_type_id.equals("04") || rplf_type_id.equals("14") || rplf_type_id.equals("16")) {
				sql = sql + "union all\r\n" + "\r\n" + "select \r\n" + "'01-99-06-000',\r\n" + "a.div_id,\r\n"
						+ "a.dept_id,\r\n" + "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n"
						+ "'Input Vat - Clearing',\r\n" + "a.vat_amt, \r\n" + "0, false\r\n" + "from (\r\n"
						+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \r\n"
						+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(vat_amt) as vat_amt\r\n"
						+ "from rf_request_detail   \r\n" + "where rplf_no = '" + rplf_no
						+ "' and status_id = 'A' and vat_amt > 0 and co_id = '" + lookupCompany.getValue() + "' \r\n"
						+ "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a \n";
				if(rplf_type_id.equals("14")) {
//					sql = sql + "union all\r\n" + 
//								"select \n"
//								+ "'01-02-04-000',\n"
//								+ "a.div_id,\n"
//								+ "a.dept_id,\n"
//								+ "a.sect_id,\n"
//								+ "a.project_id,\n"
//								+ "a.sub_projectid,\n"
//								+ "'Advances to Officers and Employees',\n"
//								+ "a.pv_amt as debit,\n"
//								+ "0 as credit\n"
//								+ "from (\n"
//								+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \n"
//								+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(pv_amt) as pv_amt\n"
//								+ "from rf_request_detail \n"
//								+ "where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"' \n"
//								+ "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a ";
					
					
					sql = sql + "UNION ALL \r\n"+
								"select \n"
								+ "'01-02-04-000',\n"
								+ "NULL,\n"
								+ "NULL,\n"
								+ "NULL,\n"
								+ "NULL,\n"
								+ "NULL,\n"
								+ "'Advances to Officers and Employees',\n"
								+ "sum(a.pv_amt) as debit,\n"
								+ "0 as credit, true\n"
								+ "from rf_request_detail a\n"
								+ "where a.rplf_no = '"+rplf_no+"' and a.status_id = 'A' and co_id = '"+co_id+"' ";
				}
			}else{
				sql = sql + "union all\r\n" + "\r\n" + "select \r\n" + "'01-99-03-000',\r\n" + "a.div_id,\r\n"
						+ "a.dept_id,\r\n" + "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n"
						+ "'Input VAT',\r\n" + "a.vat_amt, \r\n" + "0, false\r\n" + "from (\r\n"
						+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \r\n"
						+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(vat_amt) as vat_amt\r\n"
						+ "from rf_request_detail   \r\n" + "where rplf_no = '" + rplf_no
						+ "' and status_id = 'A' and vat_amt > 0 and co_id = '" + lookupCompany.getValue() + "' \r\n"
						+ "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a \n";
				
				if(rplf_type_id.equals("17")) {
					sql = sql + "UNION ALL \r\n"+
							"select \n"
							+ "'01-02-04-000',\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "'Advances to Officers and Employees',\n"
							+ "sum(a.pv_amt) as debit,\n"
							+ "0 as credit, true\n"
							+ "from rf_request_detail a\n"
							+ "where a.rplf_no = '"+rplf_no+"' and a.status_id = 'A' and co_id = '"+co_id+"' ";
				}
			}
		}

		sql = sql +

		// Withholding Tax Payable
		// -----modified by jed 9-10-18: line 17, co_id is added in the where clause and
		// subquery to display wtax------//
				"\n"+ 
				"\n"+ 
				"union all \n"+ 
				"\n"+ 
				"\n" + "select \n" + "'03-01-06-002',\n" + "a.div_id,\n" + "a.dept_id,\n"
				+ "a.sect_id,\n" + "a.project_id,\n" + "a.sub_projectid,\n" + "'Withholding Tax Payable - Expanded',\n"
				+ "0,\n" + "a.wtax_amt, false\n" + "from (\n"
				+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \n"
				+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(wtax_amt) as wtax_amt\n"
				+ "from rf_request_detail   \n" + "where rplf_no = '" + rplf_no
				+ "' and status_id = 'A' and wtax_amt > 0 and (rplf_no, co_id) not in (select rplf_no, co_id from rf_request_header where rplf_type_id in ('02', '07')) and co_id = '"
				+ lookupCompany.getValue() + "'\n" + "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a \n"+
				"\n"+

				// Retentions Payable
				"union all\n"+ 
				"\n"+ 
				"select \r\n" + "'03-01-13-000',\r\n" + "a.div_id,\r\n" + "a.dept_id,\r\n"
				+ "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n" + "'Retentions Payable',\r\n"
				+ "0,\r\n" + "a.retention_amt, false \r\n" + "from (\r\n"
				+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \r\n"
				+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(retention_amt) as retention_amt\r\n"
				+ "from rf_request_detail   \r\n" + "where rplf_no = '" + rplf_no
				+ "' and status_id = 'A' and retention_amt > 0 and co_id = '" + lookupCompany.getValue() + "' \r\n"
				+ "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a \n"+
				"\n"+

				// DP Recoupment
				"union all\n"+ 
				"\n"+ 
				"\n" + "select \r\n" + "'01-02-07-001',\r\n" + "a.div_id,\r\n" + "a.dept_id,\r\n"
				+ "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n"
				+ "'Advances to Contractor - Down Payment',\r\n" + "0,\r\n" + "a.dp_recoup_amt, false \r\n" + "from (\r\n"
				+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \r\n"
				+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(dp_recoup_amt) as dp_recoup_amt\r\n"
				+ "from rf_request_detail   \r\n" + "where rplf_no = '" + rplf_no
				+ "' and status_id = 'A' and dp_recoup_amt > 0 and co_id = '" + lookupCompany.getValue() + "' \r\n"
				+ "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a \n"+
				"\n"+
				
				"union all\n" +
				/*
				 * //Comment by Erick 2021-06-11 "\r\n" + "select \r\n" +
				 * "(case when split_part(remarks,':',1) = 'Backcharge Utilities' then '01-02-07-003' else '01-02-07-002' end),\r\n"
				 * + //'01-02-07-002', "a.div_id,\r\n" + "a.dept_id,\r\n" + "a.sect_id,\r\n" +
				 * "a.project_id,\r\n" + "a.sub_projectid,\r\n" +
				 * "(case when split_part(remarks,':',1) = 'Backcharge Utilities' then 'Advances to Contractors - Utilities' else 'Advances to Contractors - Back Charges' end),\r\n"
				 * + //'Advances to Contractors - Back Charges' "0,\r\n" + "a.bc_liqui_amt \r\n"
				 * + "from (\r\n" +
				 * "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \r\n"
				 * +
				 * "div_id, dept_id, sect_id, project_id, sub_projectid, sum(bc_liqui_amt) as bc_liqui_amt, remarks\r\n"
				 * + "from rf_request_detail   \r\n" + "where rplf_no = '"
				 * +rplf_no+"' and status_id = 'A' and bc_liqui_amt > 0 and co_id = '"
				 * +lookupCompany.getValue()+"' \r\n" +
				 * "group by div_id, dept_id, sect_id, project_id, sub_projectid, remarks ) a "
				 * +
				 */
				// added By Erick 2021/06/11 to display entries when liquidating bacharge of JV
				// and PV
				/*
				 * "select * from(	\n" + "		select\n" +
				 * "		(case when split_part(a.remarks,':',1) = 'Backcharge Utilities' \n"
				 * + "		 	then '01-02-07-003' \n" + "		 		else '01-02-07-002' \n"
				 * + "		end),\n" + "		a.div_id, \n" + "		a.dept_id,  \n" +
				 * "		a.sect_id,  \n" + "		a.project_id,  \n" +
				 * "		a.sub_projectid, \n" +
				 * "		(case when split_part(a.remarks,':',1) = 'Backcharge Utilities' \n"
				 * + "		 	then 'Advances to Contractors - Utilities' \n" +
				 * "		 		else 'Advances to Contractors - Back Charges' \n" +
				 * "		end),\n" + "		0,\r\n" + //"		a.amount\n" +
				 * "		a.bc_liqui_amt" + "		from (\n" + "			\n" +
				 * "		select \n"+
				 * //"		distinct on (a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid)  \n"
				 * + //original code comment by erick 2021-12-06
				 * "		a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid,\n" +
				 * //"		 sum(a.bc_liqui_amt) as bc_liqui_amt,a.rplf_no,\n" + //Comment by
				 * Erick 2021-11-11 //"		sum(c.amount) as bc_liqui_amt, \n" + //Added by
				 * Erick 2021-11-11 "		c.amount as bc_liqui_amt,\n" +
				 * "		a.remarks, a.ref_doc_no, \n"+ //"		sum(c.amount) as amount,\n"
				 * +//original code comment by erick 2021-12-06 "		c.amount as amount,\n" +
				 * 
				 * "		b.bc_rplf_no \n" + "		from rf_request_detail a    \n" +
				 * "		left join co_backcharge_liquidation b on a.ref_doc_no = b.bill_no \n"
				 * + "		left join \n" + "			(\n" +
				 * "				select x.bc_rplf_no, x.bill_no,y.amount, y.remarks, y.acct_id, y.ref_doc_no\n"
				 * + "				from co_backcharge_liquidation x\n" +
				 * "				left join rf_request_detail y on  x.bc_rplf_no = y.rplf_no\n"
				 * + "				where   y.status_id = 'A'\n" +
				 * "				and y.acct_id = '01-02-07-002' \n"+
				 * "				and y.rplf_no is not null\n" + "				\n" +
				 * "			)c on b.bc_rplf_no  = c.bc_rplf_no --and a.ref_doc_no = c.bill_no\n"
				 * + //Added by Erick 2021-11-11 //" )c on a.rplf_no = c.bc_rplf_no
				 * "		where a.rplf_no = '"+rplf_no+"' \n" +
				 * "		and a.status_id = 'A' \n" + "		and a.bc_liqui_amt > 0 \n" +
				 * "		and a.co_id = '"+lookupCompany.getValue()+"'  \n" +
				 * "		AND NOT EXISTS (SELECT * FROM rf_jv_header where jv_no = b.bc_rplf_no) \n"
				 * +
				 * //"		group by div_id, dept_id, sect_id, project_id, sub_projectid,bc_liqui_amt,a.rplf_no, a.remarks,a.ref_doc_no,b.bc_rplf_no,c.amount\n"
				 * + //original code comment by erick 2021-12-06 "			\n" +
				 * "		)a where (case when split_part(remarks,':',1) = 'Backcharge Utilities'  then false else true end) \n"
				 * +
				 */
				 
				 
				// Added by Erick 2021-12-16 for liquidation of backcharge PV
				// BC Liquidation
				"\n" +
				"select * from(	\n"+ 
				 "	select\n"+
				 "	(case when split_part(trim(a.remarks_details),':',1) = 'Backcharge Utilities' \n"+
				 "		then '01-02-07-003' \n"+ 
				 "			else '01-02-07-002' \n"+ 
				 "	end),\n"+ 
				 "	a.div_id, \n"+
				 "	a.dept_id,\n"+ 
				 "	a.sect_id, \n"+ 
				 "	a.project_id,\n" + /*"a.sub_projectid, \n"+*/ //comment by erick 2023-12-11  wrong sub_project is, must be the subproject of the backcharge rplf.
				 //"(select sub_projectid from rf_request_detail where rplf_no = a.bc_rplf_no and status_id = 'A' and co_id = '" + lookupCompany.getValue() + "')as sub_projectid,\n"+  //Added by erick to get the subproj_id of the backcharge rplf
				 "	a.sub_projectid as sub_projectid, \n"+			
				 "	(case when split_part(trim(a.remarks_details),':',1) = 'Backcharge Utilities' \n"+
				 "		then 'Advances to Contractors - Utilities' \n"+
				 "			else 'Advances to Contractors - Back Charges' \n"+ 
				 "	end),\n" + 
				 "	0,\n"+
				 "	a.bc_liqui_amt, false\n"+ 
				 "	from (\n"+ 
				 "	\n" +
				 "		select \n"+ 
				 "		b.div_id, \n"+
				 "		b.dept_id, \n"+ 
				 "		b.sect_id,\n"+ 
				 "		c.project_id, \n"+
				 "		c.sub_projectid,\n"+
				 "		a.liq_amt as bc_liqui_amt,\n"+ 
				 "		trim(b.remarks) as remarks_details,\n"+ 
				 "		b.ref_doc_no, \n"+
				 "		a.liq_amt as amount,\n"+
				 "		a.bc_rplf_no \n" + 
				 "		from co_backcharge_liquidation a \n"+
				 "		left join rf_request_detail b on a.bill_no = b.ref_doc_no \n"+ 
				 "		left join rf_request_detail c on a.bc_rplf_no = c.rplf_no and a.bc_co_id = c.co_id and c.status_id = a.status_id and a.liq_amt = c.amount \n"+  //Added by erick 2024-02-29 to get the correct sub_proj_id of backcharge
				 "		where a.status_id = 'A'\n"+ 
				 "		and b.status_id = 'A'\n"+ 
				 "		and b.co_id = '" + lookupCompany.getValue() + "' \n"+
				 "		and b.bc_liqui_amt > 0\n"+
				 "		and b.rplf_no = '" + rplf_no + "'\n"+
				 "		and EXISTS (select * from rf_request_detail where rplf_no = a.bc_rplf_no and status_id = 'A' and acct_id = '01-02-07-002')\n"+
				 "		AND NOT EXISTS (SELECT * FROM rf_jv_header where jv_no = a.bc_rplf_no)\n"+
				 "		) a \n"+
				 "		where (case when split_part(trim(a.remarks_details),':',1) = 'Backcharge Utilities'  then false else true end) \n"+ 
				 "	\n" +
				
				"		union all\n" + 
				//JV Backcharge Utilities
				"		\n" +
				"		select \n" + 
				"		y.acct_id,\n"+
				"		y.div_id, \n" + 
				"		y.dept_id,  \n" + 
				"		y.sect_id,  \n" + 
				"		y.project_id,  \n"+
				"		y.sub_projectid,\n" + 
				"		z.acct_name,\n" + 
				"		0,\r\n" + 
				"		x.liq_amt, false\n" + // added for partial deduction of backcharge
			   //" 		y.tran_amt\n" + //Replaced due to it always display full amount of JV...
			   //        What if only partial is deducted?
				"		from\n" + "		(\n"+
				"			select b.bc_rplf_no, a.rplf_no,a.status_id, b.bill_no, c.jv_no, b.liq_amt, c.acct_id  from (select distinct on (ref_doc_no) * from rf_request_detail where rplf_no = '" + rplf_no + "' and status_id = 'A') a\n"+
				"			left join co_backcharge_liquidation b on a.ref_doc_no = b.bill_no \n"+
				"			left join rf_jv_detail c on b.bc_rplf_no = c.jv_no and c.status_id = 'A' and c.bal_side = 'D'  \n" +
				// 			and c.acct_id = '01-02-07-003' and c.bal_side = 'D'\n" +
				"			where c.jv_no is not null\n"+ 
				"			and a.rplf_no = '" + rplf_no + "'\n"+
				"			and a.co_id = '"+lookupCompany.getValue()+"' \n"+
				"			and a.status_id = 'A' \n" + 
				"			and c.acct_id in ('01-02-07-003','01-02-07-002') \n"+ //--Added by Erick 2022-10-27 DCRF 2334 
				//"			and (case when '" + rplf_no+ "' IN ('000073450', '000079059', '000079048') then c.acct_id in ('01-02-07-003','01-02-07-002') else c.acct_id in ('01-02-07-003') end) \n"+
				"			and not exists(select ref_doc_no from rf_request_detail where ref_doc_no = a.ref_doc_no and acct_id in ('01-02-09-000', '08-01-15-000', '08-03-03-015') and status_id = 'A' and co_id = '"+lookupCompany.getValue() + "'  )\n" + // added by Erick to exclude ref_doc_no from tripping
				"			and not exists(SELECT ticket_no FROM rf_tripticket_chargedgrp where ticket_no = a.ref_doc_no )\n"+
				// 			added by Erick to exclude ref_doc_no from tripping
				"			and a.rec_id NOT IN (534463, 543478, 554507, 554507)\n"+
				"			and b.bill_no NOT IN ('0000009252', '0000009260', '0000009258', '0000009271', '0000009275')\n"+
				"       	and not exists(select * from rf_jv_header where jv_no = c.jv_no and co_id = c.co_id and status_id = 'D')\n"+ //--Added by Erick 2022-10-27 DCRF 2334 
//				"			and case when c.jv_no = '24010099' then b.liq_amt = c.tran_amt else true end\n"+//Comment by Erick 2024-01-17 For special case only, with backcharge and utilities in 1 JV
				"		) x\n" + 
				"	left join rf_jv_detail y on x.bc_rplf_no =y.jv_no and y.bal_side = 'D' and x.acct_id = y.acct_id \n"+
				"	left join mf_boi_chart_of_accounts z on y.acct_id = z.acct_id\n"+
				"	where y.status_id = 'A'\n" + 
				//"	and (case when '" + rplf_no+ "' IN ('000073450', '000079059', '000079048') then y.acct_id in ('01-02-07-003','01-02-07-002') else y.acct_id in ('01-02-07-003') end) \n"+
				// " and y.acct_id = '01-02-07-003'\n" +
				"	and y.co_id = '" + lookupCompany.getValue() + "'	\n" + ") xx \n" +
				"\n" +
				
				//Comment by erick 2023-10-04 due to conflict when creating entry with other liquidation billing.
				/*//Uncomment by erick 2023-08-10 Display entry from House Repair
				  // //CIP - Housing (Construction Cost) 
				   "union all\r\n" +  "\r\n" + 
				  "select \r\n" +  
				  "'01-03-06-001',\r\n" +  
				  "a.div_id,\r\n" + 
				  "a.dept_id,\r\n" +  
				  "a.sect_id,\r\n" +  
				  "a.project_id,\r\n" + 
				  "a.sub_projectid,\r\n" +  "'CIP - Housing - Construction Cost',\r\n" + 
				  "0,\r\n" + 
				  "a.other_liqui_amt \r\n" + 
				  "from (\r\n" + 
				  "select distinct on (a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid, a.acct_id) \r\n"+
				 "a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid," + 
				  "sum(a.other_liqui_amt) as other_liqui_amt," + //
				  "	a.acct_id as acct_id, trim(c.acct_name) as acct_name  \r\n" + 
				  "	from rf_request_detail a " + //
				  "	left join co_billing_detail b on a.rplf_no = b.rplf_no " + 
				  "	left join co_other_deduction d on b.billing_no = d.bill_no " + 
				  "	left join mf_boi_chart_of_accounts c on a.acct_id = c.acct_id \r\n" + 
				  "	where a.rplf_no = '"
				  +rplf_no+"' and a.status_id = 'A' and a.other_liqui_amt > 0 and a.co_id = '"
				  +lookupCompany.getValue()+"' \r\n" + 
				  "	group by a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid, c.acct_name, a.acct_id ) a "
				  +*/
				 
				// Other Liquidation
				"union all\n"+ 
				"\n"+ 
				"select \r\n" + "a.acct_id,\r\n" + "a.div_id,\r\n" + "a.dept_id,\r\n"
				+ "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n" + "a.acct_name,\r\n" + "0,\r\n"
				+ "sum(a.other_liqui_amt), false \r\n" + "from (\r\n" +
				// "select distinct on (a.div_id, a.dept_id, a.sect_id, a.project_id,
				// a.sub_projectid, d.acct_id) \r\n" +
				"select  \r\n"
				+ "	a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid, sum(d.ded_amt) as other_liqui_amt, "
				+
				// Special case for adjustment of Other Deductions if other_liqui_amt amount in
				// rf_request_detail is not equal to ded_amt of co_other_deduction table.
				// " a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid, (case when
				// sum(d.ded_amt) = sum(a.other_liqui_amt) then sum(d.ded_amt) else
				// sum(a.other_liqui_amt) end )as other_liqui_amt, " +
				"	d.acct_id as acct_id, trim(c.acct_name) as acct_name  \r\n" + "	from rf_request_detail a "
				+ "	left join co_billing_detail b on a.rplf_no = b.rplf_no "
				+ "	left join co_other_deduction d on b.billing_no = d.bill_no "
				+ "	left join mf_boi_chart_of_accounts c on d.acct_id = c.acct_id \r\n" + "	where a.rplf_no = '"
				+ rplf_no + "' and a.status_id = 'A' and a.other_liqui_amt > 0 and a.co_id = '"
				+ lookupCompany.getValue() + "' and b.billing_no is not null \r\n" + "	and a.co_id = b.co_id"
				+ "	group by a.div_id, a.dept_id, a.sect_id, a.project_id, a.sub_projectid,b.other_liqui_acct_id,c.acct_name, d.acct_id, d.ded_amt, a.other_liqui_amt ) a \r\n"
				+ "group by  a.acct_id,a.div_id,a.dept_id,a.sect_id,a.project_id,a.sub_projectid,a.acct_name,a.other_liqui_amt \n"+
				"\n";
				if(rplf_type_id.equals("14")) {
//					sql = sql + "\n\n"+
//							"UNION ALL \n"
//							+ "select \n"
//							+ "'01-02-22-000',\n"
//							+ "a.div_id,\n"
//							+ "a.dept_id,\n"
//							+ "a.sect_id,\n"
//							+ "a.project_id,\n"
//							+ "a.sub_projectid,\n"
//							+ "'Allowance for Uncollectible Advances',\n"
//							+ "0 as debit,\n"
//							+ "a.pv_amt as credit\n"
//							+ "from (\n"
//							+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \n"
//							+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(pv_amt) as pv_amt\n"
//							+ "from rf_request_detail \n"
//							+ "where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"' \n"
//							+ "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a \n";
					
					sql = sql + "\n\n"+
							"UNION ALL \n"+
							"select \n"
							+ "'01-02-22-000',\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "'Allowance for Uncollectible Advances',\n"
							+ "0 as debit,\n"
							+ "sum(a.pv_amt) as credit, true\n"
							+ "from rf_request_detail a\n"
							+ "where a.rplf_no = '"+rplf_no+"' and a.status_id = 'A' and co_id = '"+co_id+"'\n";
				}
				
				if(rplf_type_id.equals("17")) {
					sql = sql + "\n\n"+
							"UNION ALL \n"+
							"select \n"
							+ "'01-02-22-000',\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "NULL,\n"
							+ "'Allowance for Uncollectible Advances',\n"
							+ "0 as debit,\n"
							+ "sum(a.pv_amt) as credit, true\n"
							+ "from rf_request_detail a\n"
							+ "where a.rplf_no = '"+rplf_no+"' and a.status_id = 'A' and co_id = '"+co_id+"'\n";
				}

				// Accounts Payable - Trade
				sql = sql + "union all \n"+ 
				"\n" +
				"select \r\n" + "'03-01-01-001',\r\n" + "a.div_id,\r\n" + "a.dept_id,\r\n"
				+ "a.sect_id,\r\n" + "a.project_id,\r\n" + "a.sub_projectid,\r\n" + "'Accounts Payable - Trade',\r\n"
				+ "0,\r\n" + "a.pv_amt, false\r\n" + "from (\r\n"
				+ "select distinct on (div_id, dept_id, sect_id, project_id, sub_projectid) \r\n"
				+ "div_id, dept_id, sect_id, project_id, sub_projectid, sum(pv_amt) as pv_amt\r\n"
				+ "from rf_request_detail \r\n" + "where rplf_no = '" + rplf_no + "' and status_id = 'A' and co_id = '"
				+ lookupCompany.getValue() + "' \r\n"
				+ "group by div_id, dept_id, sect_id, project_id, sub_projectid ) a \n";
				
		

		System.out.printf("displayDRF_toPVdetails");
		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalPV(modelMain, modelTotal);
		}
		

		txtDRFRemark.setText(particulars);
		adjustRowHeight_account();
		tblPV_account.packAll();
		tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
	}

	public static void displayPV_subsidiaryledgers(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal, String req_no) {// used

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "-------display PV Subsidiary Ledgers\r\n" + "\r\n" + "select \n" + "a.entity_id,\n"
				+ "a.entity_type_id,\n" + "a.amount,\n" + "a.vat_amt,\n" + "a.wtax_amt,\n" + "a.acct_id,\n"
				+ "a.project_id,\n" + "a.sub_projectid,\n" + "a.div_id,\n" + "a.dept_id,\n" + "is_taxpaidbyco,\n"
				+ "b.acct_name,\n" + "c.proj_name,\n" + "trim(d.entity_name),\n" + "trim(e.entity_type_desc),\n"
				+ "get_div_alias(a.div_id),			\n" + "get_department_alias_new(a.dept_id),			\n"
		//		+ "get_project_alias(a.project_id),			\n" + "get_sub_proj_alias(a.sub_projectid)\n" + "\n"
				+ "f.sub_proj_id	\n"
				+ "from rf_request_detail a\n" + "left join mf_boi_chart_of_accounts b on a.acct_id=b.acct_id\n"
				+ "left join mf_project c on a.project_id = c.proj_id\n"
				+ "left join rf_entity d on a.entity_id = d.entity_id\n"
				+ "left join mf_entity_type e on a.entity_type_id = e.entity_type_id\n" + "\n"
				+ "left join mf_sub_project f on a.sub_projectid = f.sub_proj_id and a.project_id = f.proj_id and f.status_id ='A'"
				+ "where a.rplf_no = '"+ req_no + "' and a.co_id = '" + co_id + "' and a.status_id = 'A'\n" + "order by a.line_no ";

		FncSystem.out("Display SQL", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalPV_sl(modelMain, modelTotal);
		}

		adjustRowHeight_SL();
		tblPV_SL.packAll();
	}

	public static void setPV_header(String req_no) {// used

		Object[] pv_hrd = getPVdetails(req_no);

		dteRequest.setDate((Date) pv_hrd[0]);
		dteNeeded.setDate((Date) pv_hrd[1]);
		lookupDRF_no.setText((String) pv_hrd[2]);
		lookupCV_no.setText((String) pv_hrd[3]);
		lookupPaymentType.setText((String) pv_hrd[4]);
		tagReqType.setText((String) pv_hrd[5]);
		lookupPayee1.setText((String) pv_hrd[6]);
		lookupPayee2.setText((String) pv_hrd[8]);
		tagPayee1.setText((String) pv_hrd[7]);
		tagPayee2.setText((String) pv_hrd[9]);
		lookupPayeeType.setText((String) pv_hrd[10]);
		tagPayeeType.setText((String) pv_hrd[11]);
		txtStatus.setText((String) pv_hrd[13]);
		txtDRFRemark.setText(pv_hrd[14].toString());

		pay_type = (String) pv_hrd[5];
		payee1 = (String) pv_hrd[7];
		availer = (String) pv_hrd[9];
	}
	/*
	 * public static void setPV_header_forSetup(String req_no){//used
	 * 
	 * Object[] pv_hrd = getPVdetails_forSetup(req_no);
	 * 
	 * dteRequest.setDate((Date) pv_hrd[0]); dteNeeded.setDate((Date) pv_hrd[1]);
	 * lookupDRF_no.setText((String) pv_hrd[2]); lookupCV_no.setText((String)
	 * pv_hrd[3]); lookupPaymentType.setText((String) pv_hrd[4]);
	 * tagReqType.setText((String) pv_hrd[5]); lookupPayee1.setText((String)
	 * pv_hrd[6]); lookupPayee2.setText((String) pv_hrd[8]);
	 * tagPayee1.setText((String) pv_hrd[7]); tagPayee2.setText((String) pv_hrd[9]);
	 * lookupPayeeType.setText((String) pv_hrd[10]); tagPayeeType.setText((String)
	 * pv_hrd[11]); txtStatus.setText("ACTIVE");
	 * txtDRFRemark.setText(pv_hrd[14].toString());
	 * 
	 * pay_type = (String) pv_hrd[5]; payee1 = (String) pv_hrd[7]; availer =
	 * (String) pv_hrd[9]; }
	 */

	public static void setDRF_header() {// used

		Object[] drf_hrd = getDRF_header_details();

		availer = (String) drf_hrd[4];

		dteNeeded.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lookupPayee1.setText((String) drf_hrd[1]);
		tagPayee1.setTag((String) drf_hrd[2]);
		lookupPayee2.setText((String) drf_hrd[3]);
		tagPayee2.setTag(availer);
		lookupPayeeType.setText((String) drf_hrd[5]);
		tagPayeeType.setText((String) drf_hrd[6]);

		try {
			pay_type = (String) drf_hrd[7];
			lookupPaymentType.setValue((String) drf_hrd[7]);
			tagReqType.setTag((String) drf_hrd[8]);
		} catch (NullPointerException e) {
			pay_type = "";
			lookupPaymentType.setValue("");
			tagReqType.setTag("");
		}

	}

	// Enable/Disable all components inside JPanel
	public static void enable_fields(Boolean x) {// used

		lblPV_no.setEnabled(x);
		lookupPV_no.setEnabled(x);
		// lookupPV_no.setEditable(x);
		lblStatus.setEnabled(x);
		txtStatus.setEnabled(x);

		lblDateTrans.setEnabled(x);
		dteRequest.setEnabled(x);
		lblDateNeeded.setEnabled(x);
		dteNeeded.setEnabled(x);

		lblRPLF_no.setEnabled(x);
		lookupDRF_no.setEnabled(x);
		// lookupDRF_no.setEditable(x);
		lblCV_no.setEnabled(x);
		lookupCV_no.setEnabled(x);

		lblPaymentType.setEnabled(x);
		lookupPaymentType.setEnabled(x);
		// lookupPaymentType.setEditable(x);
		tagReqType.setEnabled(x);

		lblPayeeID1.setEnabled(x);
		lookupPayee1.setEnabled(x);
		// lookupPayee1.setEditable(x);
		tagPayee1.setEnabled(x);

		lblPayeeID2.setEnabled(x);
		lookupPayee2.setEnabled(x);
		// lookupPayee2.setEditable(x);
		tagPayee2.setEnabled(x);

		lblPayeeType.setEnabled(x);
		lookupPayeeType.setEnabled(x);
		// lookupPayeeType.setEditable(x);
		tagPayeeType.setEnabled(x);

		txtDRFRemark.setEditable(x);

	}

	public static void refresh_fields() {// used

		lookupPV_no.setText(""); // ---changed by jed 2018-10-30 from setValue to setText no dcrf---//
		txtStatus.setText("");

		dteRequest.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteNeeded.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lookupDRF_no.setValue("");
		lookupCV_no.setValue("");

		lookupPaymentType.setValue("");
		lookupPayee1.setValue("");
		lookupPayee2.setValue("");
		lookupPayeeType.setValue("");

		tagReqType.setText("[ ]");
		tagPayee1.setText("[ ]");
		tagPayee2.setText("[ ]");
		tagPayeeType.setText("[ ]");

		txtDRFRemark.setText("");
		btnSave.setText("Save");
		btnSave.setActionCommand("Save");
		txtDRFRemark.setText("");

		payee1 = "";
		availer_id = "";
		rplf_no = "";
		to_do = "addnew";
	}

	public static void refresh_tablesMain() {// used

		// reset table 1
		FncTables.clearTable(modelPV_account);
		FncTables.clearTable(modelPV_accounttotal);
		rowHeaderPV_account = tblPV_account.getRowHeader22();
		scrollPV_account.setRowHeaderView(rowHeaderPV_account);
		modelPV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00),
				new BigDecimal(0.00) });

		// reset table 2
		FncTables.clearTable(modelPV_SL);
		FncTables.clearTable(modelPV_SL_total);
		rowHeaderPV_SL = tblPV_SL.getRowHeader22();
		scrollPV_SL.setRowHeaderView(rowHeaderPV_SL);
		modelPV_SL_total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), null, null, null, null, null, null, null, null });

	}

	public static void enable_buttons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f) {

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnPreview.setEnabled(d);
		btnRefresh.setEnabled(e);
		btnCancel.setEnabled(f);

	}

	public void initialize_comp() {

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();

		lblPV_no.setEnabled(true);
		lookupPV_no.setEnabled(true);
		System.out.printf(getPV_no());
		lookupPV_no.setLookupSQL(getPV_no());
		lookupDRF_no.setLookupSQL(getDRF_no());

		enable_buttons(true, false, false, false, false, true);
		lookupCompany.setValue(co_id);
	}

	// action performed
	@Override
	public void actionPerformed(ActionEvent e) {// used

		if (e.getActionCommand().equals("Refresh")) {
			refresh();
		}

		if (e.getActionCommand().equals("Cancel")) {
			cancel();
		}

		if (e.getActionCommand().equals("Sub Preview")) {
			subPreview();
		}

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == true) {
			add();
			tagDetail.setText(null);
		} else if (e.getActionCommand().equals("Add")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to create new PV manually.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == true) {
			edit();
			tagDetail.setText(null);
		} else if (e.getActionCommand().equals("Edit")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to edit PV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Save")) {
			saveEntries();
		}

		if (e.getActionCommand().equals("Post") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2") == true) {
			post();
		} else if (e.getActionCommand().equals("Post")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "2") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to post PV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Tag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == true) {
			tag();
		} else if (e.getActionCommand().equals("Tag")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to tag PV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "2") == true) {
			preview();
		} else if (e.getActionCommand().equals("Preview")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "2") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to preview/print PV.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Untag") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == true) {
			untagPV();
			tagDetail.setText(null);
		} // ok
		else if (e.getActionCommand().equals("Untag")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "2") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to tag PV.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2) && table.equals("tbl_account")) {
			clickTableColumn_account();
		}
		if ((evt.getClickCount() >= 2) && table.equals("tbl_SL")) {
			clickTableColumn_SL();
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
				if (txtStatus.getText().equals("POSTED")
						&& FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2") == true) {
					mnieditePVdate.setEnabled(true);
				} else if (!txtStatus.getText().equals("POSTED")
						|| FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2") == false) {
					mnieditePVdate.setEnabled(false);
				}
				/*
				 * if(txtStatus.getText().equals("POSTED")&&
				 * FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2")==true)
				 * {mnieditePVpayee.setEnabled(true);} else
				 * if(!txtStatus.getText().equals("POSTED") ||
				 * FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2")==false)
				 * {mnieditePVpayee.setEnabled(false);}
				 */ }

		}

		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				if (txtStatus.getText().equals("POSTED")
						&& FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2") == true) {
					mnieditePVdate.setEnabled(true);
				} else if (!txtStatus.getText().equals("POSTED")
						|| FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2") == false) {
					mnieditePVdate.setEnabled(false);
				}

				/*
				 * if(txtStatus.getText().equals("POSTED")&&
				 * FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2")==true)
				 * {mnieditePVpayee.setEnabled(true);} else
				 * if(!txtStatus.getText().equals("POSTED") ||
				 * FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "2")==false)
				 * {mnieditePVpayee.setEnabled(false);}
				 */
			}
		}
	}

	public static void add() {
		// enable_fields(true);
		btnAddNew.setEnabled(false);
		lblPV_no.setEnabled(false);
		lblPV_no.setEnabled(false);
		lookupPV_no.setEnabled(false);

		lblRPLF_no.setEnabled(true);
		lookupDRF_no.setEnabled(true);

		txtStatus.setText("ACTIVE");
		btnCancel.setEnabled(true);

		lookupDRF_no.setLookupSQL(GetDRF_noAddNew()); //REPLACED SQL dcrf 2769

		btnSave.setText("Save");
		btnSave.setActionCommand("Save");
		btnPreview.setEnabled(false);
		mniInactivate.setEnabled(false);

		to_do = "addnew";

		if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "15") == true) {
			dteRequest.setEnabled(true);
			lblDateTrans.setEnabled(true);
		} else {
			dteRequest.setEnabled(false);
			lblDateTrans.setEnabled(false);
		}

		dteRequest.setEditable(true);

	}

	public static void edit() {
		lblDateTrans.setEnabled(true);
		lblDateNeeded.setEnabled(true);
		
		if(UserInfo.EmployeeCode.equals("901284") || UserInfo.EmployeeCode.equals("900655") || UserInfo.ADMIN) {
			dteRequest.setEnabled(true);
			dteNeeded.setEnabled(true);
			dteRequest.setEditable(true);
			dteNeeded.setEditable(true);
		}else {
			dteRequest.setEnabled(false);
			dteNeeded.setEnabled(false);
			dteRequest.setEditable(false);
			dteNeeded.setEditable(false);
		}
		
		
		lblPaymentType.setEnabled(true);
		lookupPaymentType.setEnabled(true);
		// lookupPaymentType.setEditable(true);
		tagReqType.setEnabled(true);
		lookupPaymentType.setLookupSQL(getPayment_type());
		lblRPLF_no.setEnabled(false);
		lookupDRF_no.setEnabled(false);
		// lookupDRF_no.setEditable(false);
		btnEdit.setEnabled(false);
		btnRefresh.setEnabled(false);
		btnSave.setEnabled(true);
		btnSave.setText("Save");
		btnSave.setActionCommand("Save");
		btnPreview.setEnabled(false);

		txtDRFRemark.setEnabled(true);
		txtDRFRemark.setEditable(true);

		modelPV_account.setEditable(false);
		modelPV_SL.setEditable(false);
		to_do = "edit";

		

	}

	public static void cancel() {
		if (!btnSave.isEnabled() || btnSave.getText().equals("Post")) {
			enable_fields(false);
			refresh_tablesMain();
			refresh_fields();
			btnAddNew.setEnabled(true);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnRefresh.setEnabled(false);
			btnCancel.setEnabled(false);
			btnPreview.setEnabled(false);
			lblPV_no.setEnabled(true);
			lookupPV_no.setEnabled(true);
			tabCenter.setSelectedIndex(0);
			tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
			mniInactivate.setEnabled(false);
		} else {
			if (JOptionPane.showConfirmDialog(null, "Cancel unsaved data?", "Cancel", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				enable_fields(false);
				refresh_tablesMain();
				refresh_fields();
				btnAddNew.setEnabled(true);
				btnEdit.setEnabled(false);
				btnSave.setEnabled(false);
				btnRefresh.setEnabled(false);
				btnCancel.setEnabled(false);
				btnPreview.setEnabled(false);
				lblPV_no.setEnabled(true);
				lookupPV_no.setEnabled(true);
				tabCenter.setSelectedIndex(0);
				tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
			} else {
			}
		}

		tagDetail.setText(null);
	}

	public void refresh() {
		setPV_header(lookupPV_no.getText().trim());
		displayPV_details(modelPV_account, rowHeaderPV_account, modelPV_accounttotal, lookupPV_no.getText().trim());
		displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, lookupPV_no.getText().trim());
		cv_no = lookupCV_no.getText();

//		if (getPV_status(pv_no).equals("P")&&lookupPV_no.isEnabled()) 
//		{								
//			btnSave.setText("Post");  
//			btnSave.setActionCommand("Post");		
//			enable_buttons(false, false, false, true, true, true);
//			mniInactivate.setEnabled(false);
//			mnireversePV.setEnabled(true);
//		} 
		if (getPV_status(pv_no).equals("P")
				&& (checkDV_status_id(cv_no, co_id).equals("D") || cv_no.equals(null) || cv_no.equals(""))
				&& lookupPV_no.isEnabled()) {// ----modified by JED 2018-09-27: DCRF no. 786 for disabling auto reversal
												// of Posted PV with active, tagged, posted paid out DV
			btnSave.setText("Post");
			btnSave.setActionCommand("Post");
			enable_buttons(false, false, false, true, true, true);
			mniInactivate.setEnabled(false);
			mnireversePV.setEnabled(true);
		} else if (getPV_status(pv_no).equals("F")) {
			btnEdit.setText("Untag");
			btnEdit.setActionCommand("Untag");
			btnSave.setText("Post");
			btnSave.setActionCommand("Post");
			enable_buttons(false, true, true, true, true, true);
			mniInactivate.setEnabled(false);
			mnireversePV.setEnabled(false);
		} else if (getPV_status(pv_no).equals("A")) {
			btnSave.setText("Tag");
			btnSave.setActionCommand("Tag");
			btnEdit.setText("Edit");
			btnEdit.setActionCommand("Edit");
			enable_buttons(false, true, true, true, true, true);
			mniInactivate.setEnabled(true);
			mnireversePV.setEnabled(false);
		}

		tabCenter.setSelectedIndex(0);
		JOptionPane.showMessageDialog(getContentPane(), "Data refreshed.", "Information",
				JOptionPane.INFORMATION_MESSAGE);

		tagDetail.setText(null);
	}

	public void post() {

		getTransMonthYear();// ---added by jed no dcrf to check if month is open---//

		if (isYearMonthOpen(year, month) == true) {// ---added by jed no dcrf to check if month is open---//

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct for Posting?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				if (checkAcctBalanceIfEqual() == true) {// **ADDED BY JED 2019-04-22 : TO CHECK TOTAL IF BALANCE OR NOT
														// BEFORE POSTING**//

					String pv = lookupPV_no.getText().trim();

					pgUpdate db = new pgUpdate();
					postPV_header(pv, db);
					insertAudit_trail("Post-Payable Voucher(PV Module)", pv, db);
					db.commit();
					lookupPV_no.setText(pv);
					setPV_header(pv);
					displayPV_details(modelPV_SL, rowHeaderPV_account, modelPV_SL_total, pv);
					displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, pv);
					tabCenter.setSelectedIndex(0);

					JOptionPane.showMessageDialog(getContentPane(), "Payable Voucher (PV) transaction posted.",
							"Information", JOptionPane.INFORMATION_MESSAGE);

					lblPV_no.setEnabled(true);
					lookupPV_no.setEnabled(true);
					// lookupPV_no.setEditable(true);

					enable_buttons(false, false, false, true, true, true);
					btnSave.setActionCommand("Post");
					btnSave.setText("Post");

				} else {

					{
						JOptionPane.showMessageDialog(getContentPane(), "Debit and Credit balance totals are not equal",
								"Information", JOptionPane.WARNING_MESSAGE);
					}
				}
			}

		} else {
			JOptionPane.showMessageDialog(null, "Year [" + year + "] ; " + "Month [" + month + "] is closed." + "\n"
					+ "Please ask your Department Head to open.", "Information", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void tag() {
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct for Tagging?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String pv = lookupPV_no.getText().trim();

			pgUpdate db = new pgUpdate();
			tagPV_header(pv, db);
			insertAudit_trail("Tag-Payable Voucher", pv, db);
			db.commit();
			lookupPV_no.setText(pv);
			setPV_header(pv);
			displayPV_details(modelPV_SL, rowHeaderPV_account, modelPV_SL_total, pv);
			displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, pv);
			tabCenter.setSelectedIndex(0);

			JOptionPane.showMessageDialog(getContentPane(), "Payable Voucher (PV) transaction tagged.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			lblPV_no.setEnabled(true);
			lookupPV_no.setEnabled(true);
			// lookupPV_no.setEditable(true);

			enable_buttons(false, true, true, true, true, true);
			btnEdit.setActionCommand("Untag");
			btnEdit.setText("Untag");
			btnSave.setActionCommand("Post");
			btnSave.setText("Post");

		} else {
		}
	}

	private void untagPV() {// used

		if (JOptionPane.showConfirmDialog(getContentPane(), "Untag Payable Voucher?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String pv = lookupPV_no.getText().trim();
			pgUpdate db = new pgUpdate();
			untagPV_header(pv, db);
			insertAudit_trail("Untag-Payable Voucher", pv, db);
			db.commit();
			setPV_header(pv);
			displayPV_details(modelPV_SL, rowHeaderPV_account, modelPV_SL_total, pv);
			displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, pv_no);
			tabCenter.setSelectedIndex(0);
			JOptionPane.showMessageDialog(null, "Payable Voucher (JV) transaction untagged.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			lblPV_no.setEnabled(true);
			lookupPV_no.setEnabled(true);
			// lookupPV_no.setEditable(true);

			enable_buttons(false, true, true, true, true, true);
			btnEdit.setActionCommand("Edit");
			btnEdit.setText("Edit");
			btnSave.setActionCommand("Tag");
			btnSave.setText("Tag");

		} else {
		}

	}

	public void preview() {

		String payee_unliq_ca = "";
		String availer_unliq_ca = "";
		String remark_unliq_ca = "";

		/* COMMENTED BY JED 2021-02-01 : FOR FAST PREVIEW OF PV */
//		if (RequestForPayment.entityhasUnliquidatedCA(lookupPayee1.getText().trim())==true) {payee_unliq_ca = "Payee";}
//		if (RequestForPayment.entityhasUnliquidatedCA(lookupPayee2.getText().trim())==true) {availer_unliq_ca = "Availer";}		

//		if (payee_unliq_ca.equals("")&&availer_unliq_ca.equals("")) {} else {	
//
//			/*if (payee_unliq_ca.equals("")) {} 
//			else {
//				remark_unliq_ca = ""+payee_unliq_ca+"";
//				if (!availer_unliq_ca.equals("")) {remark_unliq_ca = remark_unliq_ca + " and ";} 
//				else {}  
//			}  */
//
//			if (availer_unliq_ca.equals("")) {} 
//			else {remark_unliq_ca = remark_unliq_ca + ""+availer_unliq_ca+"";}  
//
//			/*if (!payee_unliq_ca.equals("")&&!availer_unliq_ca.equals("")) {remark_unliq_ca = remark_unliq_ca + " have ";} 
//			else {remark_unliq_ca = remark_unliq_ca + " has ";} */
//
//			remark_unliq_ca = remark_unliq_ca + " has Unliquidated Cash Advance (CA)." ; 
//
//		}
		/* COMMENTED BY JED 2021-02-01 : FOR FAST PREVIEW OF PV */

		String criteria = "Payable Voucher";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Double pv_amt = Double.parseDouble(getAcctPayableAmount());
		Double pv_amt_tot = Double.parseDouble(modelPV_accounttotal.getValueAt(0, 7).toString());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("pv_no", lookupPV_no.getText().trim());
		mapParameters.put("due_date", dteNeeded.getDate());
		mapParameters.put("pv_date", dteRequest.getDate());
		mapParameters.put("pv_amt", pv_amt);
		mapParameters.put("pv_amt_tot", pv_amt_tot);
		mapParameters.put("pay_type", pay_type);
		mapParameters.put("payee1", payee1);
		mapParameters.put("status", txtStatus.getText().trim());
		mapParameters.put("particulars", txtDRFRemark.getText().trim());
		mapParameters.put("remark_unliq_ca", remark_unliq_ca);
		mapParameters.put("availer", availer);
		mapParameters.put("availer_id", lookupPayee2.getText()); // added 03-17-2016 for Unliquidated CA subtable
		mapParameters.put("payee_id", lookupPayee1.getText()); // added 03-17-2016 for Unliquidated CA subtable

		/*
		 * if (rplfList() == true) {
		 * FncReport.generateReport("/Reports/rptPV_preview.jasper", reportTitle,
		 * company, mapParameters); } else {
		 */
		FncReport.generateReport("/Reports/rptPV_preview_v2.jasper", reportTitle, company, mapParameters);
		// }

		// FncReport.generateReport("/Reports/rptPV_preview_v2.jasper", reportTitle,
		// company, mapParameters);

		// if (checkIfHasWtax()==true){print2307();}
	}

	public void inactivatePV() {
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to set this PV inactive?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			pv_no = lookupPV_no.getText().trim();
			pgUpdate db = new pgUpdate();
			iactivatePV_header(pv_no, db);
			iactivatePV_details(pv_no, db);
			insertAudit_trail("Inactive-Payable Voucher", pv_no, db);
			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), "Payable Voucher (PV) transaction inactivated.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			// openDRF();
			// RequestForPayment drf = new RequestForPayment();
			// drf.this.dispose();

			setPV_header(lookupPV_no.getText().trim());
			displayPV_details(modelPV_account, rowHeaderPV_account, modelPV_accounttotal, lookupPV_no.getText().trim());
			displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, lookupPV_no.getText().trim());
			enable_buttons(false, false, false, false, true, true);

			tabCenter.setSelectedIndex(0);
		}
	}

	// --ADDED BY JED 2018-12-11 DCRF NO. 878 : MANUAL INPUT FOR REQUEST WITH (02)
	// AVAILER TYPE (EMPLOYEE)--//
	private void subPreview() {

		String criteria = "Official Receipt";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		String co_id = lookupCompany.getValue();
		String entity_type = txtEntry.getText();

		System.out.printf("The value of entity_type: %s\n", entity_type);

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", co_id);
		mapParameters.put("co_name", company);
		mapParameters.put("pv_no", lookupPV_no.getText());
		mapParameters.put("paytype", lookupPaymentType.getText());
		mapParameters.put("bg", this.getClass().getClassLoader().getResourceAsStream("Images/bgOR.jpg"));
		mapParameters.put("entity_type", entity_type);

		FncReport.generateReport("/Reports/rptOR.jasper", reportTitle, company, mapParameters);

		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlSub);
		optionPaneWindow.dispose();
		txtEntry.setText("");

	}

	// select, lookup and get statements
	public static String getPV_no() {// used
		/*
		 * if (chkCurrentYear.isSelected()==true) { isCurrentYear = "2018"; }
		 * 
		 * else { isCurrentYear = ""; }
		 */

		return

		"select \r\n" + "a.pv_no as \"PV No.\" , \r\n" + "a.pv_date as \"PV Date\"      ,\r\n"
				+ "d.amt as \"PV Amount\"      ,\r\n" +
				// "(case when a.pay_type_id = 'B' then 'CHECK' else 'CASH' end) as \"Pay
				// Type\", \r\n" +
				"trim(b.entity_name) as \"Payee\"    ,\r\n" + "c.status_desc  as \"status\"  \r\n" +
				// "a.date_posted as \"Date Posted\" \r\n" +

				"from (select pv_no, pv_date, pay_type_id, date_posted, entity_id1, status_id from \n"
				+ "			rf_pv_header \n" + "			where co_id = '" + co_id + "' \n"
				+ "			and (case when '" + isCurrentYear + "' = '' then true else to_char(pv_date,'yyyy') = '"
				+ isCurrentYear + "' end)) a  \r\n" + "left join rf_entity b on a.entity_id1 = b.entity_id\r\n"
				+ "left join mf_record_status c on a.status_id = c.status_id \n"
				+ "left join (select pv_no, sum(tran_amt) as amt from rf_pv_detail where status_id = 'A' and bal_side = 'C' and acct_id != '03-01-06-002' and co_id = '"
				+ co_id + "' group by pv_no) d on a.pv_no in (d.pv_no) \n" +

				"order by a.pv_no desc";

	}

	public static String getDRF_no() {// used
		return "select \r\n" + "a.rplf_no as \"DRF No.\", \n" + "a.rplf_date as \"DRF Date\",\r\n"
				+ "trim(b.entity_name) as \"Payee\"  \r\n" + "from rf_request_header a\r\n"
				+ "left join rf_entity b on a.entity_id1 = b.entity_id  \r\n" + "where a.co_id = '" + co_id + "' \n"
				+ "and rplf_no not in ( select rplf_no from rf_pv_header where co_id = '" + co_id + "' and status_id ='P')  \n"
				+ "and a.status_id = 'A'\n" + "order by a.rplf_no desc ";

	}
	
	public static String GetDRF_noAddNew() {
		return "select \r\n" + "a.rplf_no as \"DRF No.\", \n" + "a.rplf_date as \"DRF Date\",\r\n"
				+ "trim(b.entity_name) as \"Payee\"  \r\n" + "from rf_request_header a\r\n"
				+ "left join rf_entity b on a.entity_id1 = b.entity_id  \r\n" + "where a.co_id = '" + co_id + "' \n"
				+ "and rplf_no not in ( select rplf_no from rf_pv_header where co_id = '" + co_id + "')  \n"
				+ "and a.status_id = 'A'\n" + "order by a.rplf_no desc ";
	}

	public static String getPayment_type() {// used
		return "select 'B' as \"Code\", 'CHECK' as \"Payment Type\" union all "
				+ "select 'A' as \"Code\", 'CASH'  as \"Payment Type\"   ";

	}

	public String getDivision() {

		String sql = "select division_code as \"Div Code\", " + "trim(division_name) as \"Div Name\", "
				+ "division_alias as \"Div Alias\" " + "from division ";
		return sql;

	}

	public String getDepartment(_JTableMain table, DefaultTableModel model, Integer col_no) {

		int row = table.getSelectedRow();
		String div = model.getValueAt(row, col_no).toString();

		String sql = "select dept_code as \"Dept Code\", " + "trim(dept_name) as \"Dept Name\", "
				+ "dept_alias as \"Dept Alias\" " + "from department ";
		if (div.equals("")) {
			sql = sql + "";
		} else {
			sql = sql + "where division_code = '" + div + "' ";
		}

		return sql;

	}

	public String getProject() {

		String sql = "select a.proj_id as \"Project ID\", " + "a.proj_name as \"Project Name\", "
				+ "a.proj_alias as \"Project Alias\", " + "b.sub_proj_id as \"SubProject ID\", "
				+ "a.vatable as \"Vatable\" " + "from mf_project a "
				+ "left join ( select distinct on (proj_id) proj_id, sub_proj_id from mf_unit_info ) b  on a.proj_id=b.proj_id ";
		return sql;

	}

	public String getSubproject(_JTableMain table, DefaultTableModel model, Integer col_no) {
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

						"from mf_sub_project a\r\n" + "left join mf_project b on a.proj_id = b.proj_id\r\n"
						+ "where b.co_id = '" + co_id + "' and phase != '' AND a.status_id = 'A'";//ADDED STATUS ID BY LESTER DCRF 2718

		if (proj.equals("") || proj.equals(null)) {
		} else {
			sql = sql + "and a.proj_id = '" + proj + "'   ";
		}

		return sql;

	}

	public static Integer sql_getDRF_line_count(String req_no) {

		Integer x = 1;

		// COMMENTED CAUSE SOME LINE NUMBERS IN rf_request_detail are causing long loop
		// because of high value 2022-04-27 c/o lester
		/*
		 * String SQL = "select max (line_no) from rf_request_detail where rplf_no = '"
		 * +req_no+"' and co_id = '"+co_id+"'  group by rplf_no   " ;
		 */

		//String SQL = "select count (*)::INT from rf_request_detail where rplf_no = '" + req_no + "' and co_id = '"
		String SQL = "select count (*)::INT from rf_request_detail where rplf_no = '" + req_no + "' and co_id = '"
				+ co_id + "'";
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			x = (Integer) db.getResult()[0][0];
		} else {
			x = null;
		}

		return x;
	}

	public static Object[] getPVdetails(String req_no) {// used

		String strSQL = "--- Display PV Header\r\n" + "select \r\n" + "\r\n" + "a.pv_date,\r\n" + "a.due_date,\r\n"
				+ "a.rplf_no,\r\n" + "a.cv_no,\r\n" + "a.pay_type_id,\r\n"
				+ "(case when a.pay_type_id = 'A' then 'CASH' else 'CHECK' end) as pay_type_desc,\r\n"
				+ "a.entity_id1,\r\n" + "trim(b.entity_name) as payee_1,\r\n" + "a.entity_id2,\r\n"
				+ "trim(c.entity_name) as payee_2,\r\n" + "a.entity_type_id,\r\n" + "d.entity_type_desc,\r\n"
				+ "a.credit_term_id,\r\n" + "e.status_desc," + "trim(a.remarks) \r\n" + ""
				+ "from rf_pv_header a\r\n" + "left join rf_entity b on a.entity_id1 = b.entity_id\r\n"
				+ "left join rf_entity c on a.entity_id2 = c.entity_id\r\n"
				+ "left join mf_entity_type d on a.entity_type_id = d.entity_type_id\r\n"
				+ "left join mf_record_status e on a.status_id = e.status_id\r\n" + "" + "where a.pv_no = '" + req_no
				+ "' and a.co_id = '" + co_id + "'\r\n" + "";

		System.out.printf("SQL #1 PV Header: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	public static Object[] getPVdetails_forSetup(String req_no) {// used

		String strSQL = "--- Display PV Header\r\n" + "select \r\n" + "\r\n" + "a.pv_date,\r\n" + "a.due_date,\r\n"
				+ "a.rplf_no,\r\n" + "a.cv_no,\r\n" + "a.pay_type_id,\r\n"
				+ "(case when a.pay_type_id = 'A' then 'CASH' else 'CHECK' end) as pay_type_desc,\r\n"
				+ "aa.entity_id1,\r\n" + "trim(b.entity_name) as payee_1,\r\n" + "aa.entity_id2,\r\n"
				+ "trim(c.entity_name) as payee_2,\r\n" + "a.entity_type_id,\r\n" + "d.entity_type_desc,\r\n"
				+ "a.credit_term_id,\r\n" + "'ACTIVE'," + "trim(a.remarks) \r\n" + "" + "from rf_pv_header a\r\n"
				+ "left join rf_request_header aa on a.pv_no = aa.rplf_no\r\n"
				+ "left join rf_entity b on aa.entity_id1 = b.entity_id\r\n"
				+ "left join rf_entity c on aa.entity_id2 = c.entity_id\r\n"
				+ "left join mf_entity_type d on a.entity_type_id = d.entity_type_id\r\n"
				+ "left join mf_record_status e on a.status_id = e.status_id\r\n" + "" + "where a.pv_no = '" + rplf_no
				+ "' and a.co_id = '" + co_id + "'\r\n" + "";

		System.out.printf("SQL #1 PV Header: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	public static Object[] getDRF_header_details() {// used

		String strSQL = "--- Display DRF Header\r\n" + "select \r\n" +

				"a.date_needed,\r\n" + "a.entity_id1,\r\n" + "trim(b.entity_name) as payee_1,\r\n" + "a.entity_id2,\r\n"
				+ "trim(c.entity_name) as payee_2,\r\n" + "a.entity_type_id,\r\n" + "d.entity_type_desc,"
				+ "a.pay_type_id," + "(case when a.pay_type_id = 'B' then 'CHECK' else 'CASH' end) as py_type  \r\n" +

				"from rf_request_header a\r\n" + "left join rf_entity b on a.entity_id1 = b.entity_id\r\n"
				+ "left join rf_entity c on a.entity_id2 = c.entity_id\r\n"
				+ "left join mf_entity_type d on a.entity_type_id = d.entity_type_id\r\n"
				+ "left join mf_record_status e on a.status_id = e.status_id\r\n" +

				"where a.rplf_no = '" + rplf_no + "'   and a.co_id = '" + co_id + "' \r\n";

		pgSelect db = new pgSelect();
		db.select(strSQL);
		FncSystem.out("Display get DRF Header", strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	/*public static String getDRF_particulars(Integer line_no, String req_no) {// used

		String strSQL = "select \n " + "trim(part_desc)  \r\n" +

				"from rf_request_detail a \r\n" + "where a.line_no = " + line_no + " and a.rplf_no = '" + req_no
				+ "' and a.co_id = '" + co_id + "' and a.status_id = 'A'  ";

		FncSystem.out("Line 1", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}

	}*/
	public static String getDRF_particulars(String co_id, String req_no) {// used

		String strSQL = "\n" + 
				"SELECT string_agg(part_desc, E' \\n') AS actor_list\n" + 
				"FROM   rf_request_detail where rplf_no = '"+req_no+"' and co_id = '"+co_id+"' and status_id = 'A' ";

		FncSystem.out("Line 1", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}

	}
	//Added by Erick 2023-07-26
	public static String getorreference( String co_id, String rplf_no ) {
		String sql = "select * from (\n"
				+ "	select case when b.or_no_reference is not null then b.or_no_reference else  c.rpt_or_no end as remarks\n"
				+ "	from rf_request_header a\n"
				+ "	left join rf_transfer_cost b on a.rplf_no = b.rplf_no and a.co_id = b.co_id\n"
				+ "	left join rf_processing_cost c on a.rplf_no = c.rplf_no and a.co_id = c.co_id\n"
				+ "	where a.rplf_no = '"+rplf_no+"'\n"
				+ "	and a.co_id = '"+co_id+"'\n"
				+ "	) x group by x.remarks";
		
		FncSystem.out("getorreference", sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	public static String getDRFAvailerAmt(Integer line_no, String req_no) {// used
		/*
		 * EDITED BY JED 2021-11-22 DCRF 1830 : AVAILER TYPE MUST BE INCLUDED IN
		 * PARTICULARS WHEN PV IS SETUP
		 */
		String strSQL = 
				//"select amount||'  -  '|| '(' || a.entity_type_id || ')' || ' ' ||  b.entity_name \n" +
				"SELECT FORMAT('%s - (%s %s) %s', a.amount, CASE WHEN a.is_vatentity THEN 'V' ELSE 'NV' END, a.entity_type_id, b.entity_name)\n"+
		// "select coalesce(sp_add_whitespace_right(15,(case when amount::text is null
		// then null else amount::text end)))::text \n" +
				"from rf_request_detail a\n" + "left join rf_entity b on a.entity_id = b.entity_id\n"
				+ "where rplf_no = '" + req_no + "'  \n" + "and a.status_id != 'I'  \r\n" + "and a.line_no = " + line_no
				+ " " + "and a.co_id = '" + co_id + "' "
				+ "and rplf_no not in (select rplf_no from rf_processing_cost where status_id = 'A' and co_id = '"
				+ co_id + "' and rplf_no is not null)\n"
				+ "and rplf_no not in (select rplf_no from rf_transfer_cost where status_id = 'A' and co_id = '" + co_id
				+ "' and rplf_no is not null and date_created::DATE >= '2022-07-05'::Date)";
		
		System.out.println("getDRFAvailerAmt: "+strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	public static String getPV_status(String req_no) {// used

		String strSQL = "select \n " + "trim(a.status_id)  \r\n" +

				"from rf_pv_header a \r\n" + "where a.pv_no = '" + req_no + "' and a.co_id = '" + co_id + "'  ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	public static String getRPLF_typeID(String req_no) {// used

		String strSQL = "select \n " + "trim(a.rplf_type_id)  \r\n" +

				"from rf_request_header a \r\n" + "where a.rplf_no = '" + req_no + "' " + "and a.co_id = '" + co_id
				+ "'  ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	public static String getAcctPayableAmount() {// used

		String strSQL = "select coalesce(sum(tran_amt),0) from rf_pv_detail where pv_no = '" + pv_no + "'\r\n"
				+ "and acct_id = '03-01-01-001' \n" + "and co_id = '" + co_id + "' and status_id = 'A'  ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0][0].toString();
		} else {
			return "0.00";
		}
	}

	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

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
				+ "from mf_acctg_period\r\n" + "where period_year = '" + year + "'  " + "and month_code = '" + month
				+ "' " + "and co_id = '" + co_id + "' ";

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

	private static Boolean isAlreadyReversed() {

		Boolean isAlreadyReversed = false;

		String SQL = "select (case when reversal_date is null then false else true end)  "
				+ "	from rf_pv_header where pv_no = '" + pv_no + "' and co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			isAlreadyReversed = (Boolean) db.getResult()[0][0];
		} else {
			isAlreadyReversed = false;
		}

		return isAlreadyReversed;

	}

	private static String sql_getReversal_jv() {

		String a = "";

		String SQL = "select reversal_jv_no from rf_pv_header where pv_no = '" + pv_no + "' and co_id = '" + co_id
				+ "' ";

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

	// check and validate
	private Boolean checkCompleteDetails() {

		boolean x = false;

		String payment_type;

		payment_type = lookupPaymentType.getText();

		if (payment_type.equals("")) {
			x = false;
		} else {
			x = true;
		}

		return x;

	}
	/*
	 * private Boolean checkIfHasWtax(){
	 * 
	 * boolean x = false;
	 * 
	 * int rw = tblPV_account.getModel().getRowCount(); int w = 0;
	 * 
	 * while (w < rw) { String acct = tblPV_account.getValueAt(w,0).toString(); if
	 * (acct.equals("03-01-06-002")) {x=true; break;} else {} w++; } return x;
	 * 
	 * }
	 */

	public static void getTransMonthYear() {

		DateFormat df = new SimpleDateFormat("yyyy-dd-MM");
		String month_sub = df.format(dteRequest.getDate());
		month = month_sub.substring(8);
		DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
		String year_sub = df2.format(dteRequest.getDate());
		year = year_sub.substring(6);

	}

	public static void checkRequestDate() {

		if (isYearMonthOpen(year, month) == true) {
		} else {
			JOptionPane.showMessageDialog(null, "Year [" + year + "] ; " + "Month [" + month + "] is closed." + "\n"
					+ "Please ask your Department Head to open.", "Information", JOptionPane.WARNING_MESSAGE);
		}
	}

	public static String getDRF_remarks(String req_no) {// used

		String strSQL =
				
				  "select \n " + "trim(remarks)  \r\n" +
				  
				  "from rf_request_header a \r\n" +
				  "where a.rplf_no = '"+req_no+"' and a.co_id = '"
				  +co_id+"' and a.status_id = 'A'  ";
				 

				/*
				 * "select \n " + "trim(part_desc)   \r\n" +
				 * 
				 * "from rf_request_detail a \r\n" +
				 * "where a.rplf_no = '"+req_no+"' and a.co_id = '"
				 * +co_id+"' and a.status_id = 'A'  ";
				 */

				FncSystem.out("Remarks", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	// table operations
	public static void totalPV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// used

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal debit = new BigDecimal(0.00);
		BigDecimal credit = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				debit = debit.add(((BigDecimal) modelMain.getValueAt(x, 7)));
			} catch (NullPointerException e) {
				debit = debit.add(new BigDecimal(0.00));
			}

			try {
				credit = credit.add(((BigDecimal) modelMain.getValueAt(x, 8)));
			} catch (NullPointerException e) {
				credit = credit.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, null, null, null, null, null, debit, credit });
		tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
	}

	public static void totalPV_sl(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// used

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal gr_amt = new BigDecimal(0.00);
		BigDecimal vat_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				gr_amt = gr_amt.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e) {
				gr_amt = gr_amt.add(new BigDecimal(0.00));
			}

			try {
				vat_amt = vat_amt.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				vat_amt = vat_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, gr_amt, vat_amt, wtax_amt, null, null, null, null, null, null,
				null, null, null, null });
	}

	private void clickTableColumn_account() {// used
		int column = tblPV_account.getSelectedColumn();
		int row = tblPV_account.getSelectedRow();

		Integer x[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		String sql[] = { "", getDivision(), getDepartment(tblPV_account, modelPV_account, 1), "", getProject(),
				getSubproject(tblPV_account, modelPV_account, 4), "", "", "" };
		String lookup_name[] = { "", "Division", "Department", "", "Project", "Subproject", "", "" };

		if (column == x[column] && modelPV_account.isEditable() && sql[column] != "") {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 1) {
				modelPV_account.setValueAt(data[0], row, column);
				modelPV_account.setValueAt("", row, column + 1);
			} else if (data != null && column == 2) {
				modelPV_account.setValueAt(data[0], row, column);
			} else if (data != null && column == 4) {
				modelPV_account.setValueAt(data[0], row, column);
				modelPV_account.setValueAt("", row, column + 1);
			} else if (data != null && column == 5) {
				modelPV_account.setValueAt(data[0], row, column);
			}
		}

		else {
		}

		tblPV_account.packAll();
		tblPV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
	}

	private void clickTableColumn_SL() {// used
		int column = tblPV_SL.getSelectedColumn();
		int row = tblPV_SL.getSelectedRow();

		Integer x[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
		String sql[] = { "", "", "", "", "", "", getProject(), getSubproject(tblPV_SL, modelPV_SL, 6), getDivision(),
				getDepartment(tblPV_SL, modelPV_SL, 7), "", "", "", "", "" };
		String lookup_name[] = { "", "", "", "", "", "", "Project", "Division", "", "Department", "", "", "", "", "",
				"" };

		if (column == x[column] && modelPV_SL.isEditable() && sql[column] != "") {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 6) {
				modelPV_SL.setValueAt(data[0], row, column);
				modelPV_SL.setValueAt("", row, column + 1);
			} else if (data != null && column == 7) {
				modelPV_SL.setValueAt(data[0], row, column);
			} else if (data != null && column == 8) {
				modelPV_SL.setValueAt(data[0], row, column);
				modelPV_SL.setValueAt("", row, column + 1);
			} else if (data != null && column == 9) {
				modelPV_SL.setValueAt(data[0], row, column);
			}
		}

		else {
		}

		tblPV_SL.packAll();
	}

	private static void adjustRowHeight_account() {// used
		int rw = tblPV_account.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			tblPV_account.setRowHeight(x, 22);
			x++;
		}

	}

	private static void adjustRowHeight_SL() {// used
		int rw = tblPV_SL.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			tblPV_SL.setRowHeight(x, 22);
			x++;
		}

	}

	// save and insert
	private void saveEntries() {

		getTransMonthYear();

		if (isYearMonthOpen(year, month) == true) {

			if (checkCompleteDetails() == false) {
				JOptionPane.showMessageDialog(getContentPane(), "Please enter complete request details.", "Information",
						JOptionPane.WARNING_MESSAGE);
			}

			else {
				String pv = lookupPV_no.getText().trim();
				if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					if (to_do.equals("addnew")) {

						/*
						 * pgUpdate db = new pgUpdate(); insertPV_header(rplf_no, db);
						 * insertPV_detail(rplf_no, db); insertAudit_trail("Add-Payable Voucher", pv,
						 * db); updateCDF(pv, db); //to update the status of cm-cdf-table to P-processed
						 * ;
						 * 
						 * db.commit(); lookupPV_no.setText(rplf_no);
						 * 
						 * setPV_header(rplf_no); displayPV_details(modelPV_SL, rowHeaderPV_account,
						 * modelPV_SL_total, rplf_no); displayPV_subsidiaryledgers(modelPV_SL,
						 * rowHeaderPV_SL, modelPV_SL_total, rplf_no );
						 * 
						 * tabCenter.setSelectedIndex(0); JOptionPane.showMessageDialog(getContentPane()
						 * ,"New Payable Voucher (PV) transaction saved.","Information",JOptionPane.
						 * INFORMATION_MESSAGE); lblPV_no.setEnabled(true);
						 * lookupPV_no.setEnabled(true);
						 * 
						 * enable_buttons(false, true, true, true, true, true);
						 * btnSave.setActionCommand("Tag"); btnSave.setText("Tag");
						 * mniInactivate.setEnabled(true);
						 */
						// **ADDED BY JED 2019-04-22 : TO CHECK TOTAL IF BALANCE OR NOT BEFORE SAVING OR
						// POSTING**//
						if (checkAcctBalanceIfEqual() == true) {
							pgUpdate db = new pgUpdate();
							insertPV_header(rplf_no, db);
							insertPV_detail(rplf_no, db);
							insertAudit_trail("Add-Payable Voucher", pv, db);
							updateCDF(pv, db); // to update the status of cm-cdf-table to P-processed ;

							db.commit();
							lookupPV_no.setText(rplf_no);

							setPV_header(rplf_no);
							displayPV_details(modelPV_SL, rowHeaderPV_account, modelPV_SL_total, rplf_no);
							displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, rplf_no);

							tabCenter.setSelectedIndex(0);
							JOptionPane.showMessageDialog(getContentPane(),
									"New Payable Voucher (PV) transaction saved.", "Information",
									JOptionPane.INFORMATION_MESSAGE);
							lblPV_no.setEnabled(true);
							lookupPV_no.setEnabled(true);

							enable_buttons(false, true, true, true, true, true);
							btnSave.setActionCommand("Tag");
							btnSave.setText("Tag");
							mniInactivate.setEnabled(true);
						} else {
							{
								JOptionPane.showMessageDialog(getContentPane(),
										"Debit and Credit balance totals are not equal", "Information",
										JOptionPane.WARNING_MESSAGE);
							}
						}
					} else if (to_do.equals("edit")) {

						pgUpdate db = new pgUpdate();
						updatePV_header(pv, db);
						updateRPLF_detail(pv, db);
						insertPV_detail(pv, db);
						insertAudit_trail("Edit-Payable Voucher", pv, db);
						db.commit();
						lookupPV_no.setText(pv);
						setPV_header(pv);
						displayPV_details(modelPV_SL, rowHeaderPV_account, modelPV_SL_total, rplf_no);
						displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, pv);
						tabCenter.setSelectedIndex(0);
						JOptionPane.showMessageDialog(getContentPane(), "Payable Voucher (PV) transaction updated.",
								"Information", JOptionPane.INFORMATION_MESSAGE);
						lblPV_no.setEnabled(true);
						lookupPV_no.setEnabled(true);

						enable_buttons(false, true, true, true, true, true);
						btnSave.setActionCommand("Tag");
						btnSave.setText("Tag");
						mniInactivate.setEnabled(true);
					} else if (to_do.equals("editpv_paid")) {
						pgUpdate db = new pgUpdate();
						updatePV_header_pvdate(pv, db);
						insertAudit_trail("Edit-PV Date Paid", pv, db);
						db.commit();
						JOptionPane.showMessageDialog(getContentPane(), "PV date updated.", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						lblDateTrans.setEnabled(false);
						dteRequest.setEnabled(false);
						enable_buttons(false, false, false, false, false, true);
					} else if (to_do.equals("editpv_payee")) {
						pgUpdate db = new pgUpdate();
						updatePV_header_pvpayee(pv, db);
						insertAudit_trail("Edit-PV Payee", pv, db);
						db.commit();
						JOptionPane.showMessageDialog(getContentPane(), "PV payee updated.", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						lblDateTrans.setEnabled(false);
						lookupPayee1.setEnabled(false);
						enable_buttons(false, false, false, false, false, true);
					} else if (to_do.equals("set-up")) {
						if (checkAcctBalanceIfEqual() == true) {// **ADDED BY JED 2019-04-22 : TO CHECK TOTAL IF BALANCE
																// OR NOT BEFORE SAVING OR POSTING**//
							pgUpdate db = new pgUpdate();
							updatePV_header_forsetup(rplf_no, db);
							updateRPLF_detail(rplf_no, db);
							insertPV_detail(rplf_no, db);
							insertAudit_trail("Setup-Payable Voucher", rplf_no, db);
							updateCDF(pv, db); // to update the status of cm-cdf-table to P-processed ;

							db.commit();
							lookupPV_no.setText(pv);

							setPV_header(rplf_no);
							displayPV_details(modelPV_SL, rowHeaderPV_account, modelPV_SL_total, rplf_no);
							displayPV_subsidiaryledgers(modelPV_SL, rowHeaderPV_SL, modelPV_SL_total, pv);

							tabCenter.setSelectedIndex(0);
							JOptionPane.showMessageDialog(getContentPane(), "Payable Voucher (PV) transaction updated.",
									"Information", JOptionPane.INFORMATION_MESSAGE);
							lblPV_no.setEnabled(true);
							lookupPV_no.setEnabled(true);

							enable_buttons(false, true, true, true, true, true);
							btnSave.setActionCommand("Tag");
							btnSave.setText("Tag");
							mniInactivate.setEnabled(true);
						} else {
							{
								JOptionPane.showMessageDialog(getContentPane(),
										"Debit and Credit balance totals are not equal", "Information",
										JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				} else {
				}
			}
		}

		else {
			JOptionPane.showMessageDialog(null, "Year [" + year + "] ; " + "Month [" + month + "] is closed." + "\n"
					+ "Please ask your Department Head to open.", "Information", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void insertPV_header(String rplf_no, pgUpdate db) {// ok

		Date pv_date = null;
		String pay_type_id = "";
		String entity_id1 = "";
		String entity_id2 = "";
		String ent_type_id = "";
		String credit_term_id = "";
		Date due_date = null;
		Integer print_counter = 0;
		String printed_by = "";
		String posted_by = "";
		String remarks = "";
		String doc_id = "";
		String created_by = "";
		String edited_by = "";
		Date edited_date = null;
		doc_id = "12";

		pv_date = dteRequest.getDate();
		pay_type_id = lookupPaymentType.getText().trim();
		entity_id1 = lookupPayee1.getText().trim();
		entity_id2 = lookupPayee2.getText().trim();
		ent_type_id = lookupPayeeType.getText().trim();
		due_date = dteNeeded.getDate();
		remarks = txtDRFRemark.getText().trim().replace("'", "''").trim();
		created_by = UserInfo.EmployeeCode;
		edited_by = "";
		edited_date = null;

		String sqlDetail = "INSERT into rf_pv_header values (" + "'" + co_id + "',  \n" + // 1 co_id
				"'" + co_id + "',  \n" + // 2 busunit_id
				"'" + rplf_no + "',  \n" + // 3 pv_no
				"'" + co_id + "',  \n" + // 4 rplf_co_id
				"'" + co_id + "',  \n" + // 5 rplf_busunit_id
				"nullif(trim('" + rplf_no + "'),''),  \n" + // 6 rplf_no
				"'',  \n" + // 7 cv_no
				"'" + dateFormat.format(pv_date) + "',  \n" + // 8 pv_date
				"'" + pay_type_id + "',  \n" + // 9 pay_type_id
				"'" + entity_id1 + "',  \n" + // 10 entity_id1
				"'" + entity_id2 + "',  \n" + // 11 entity_id2
				"'" + ent_type_id + "',  \n" + // 12 entity_type_id
				"'" + credit_term_id + "' , \n" + // 13 credit_term_id
				"'" + dateFormat.format(due_date) + "',  \n" + // 14 due_date
				"" + print_counter + " , \n" + // 15 print_counter
				"'" + printed_by + "' , \n" + // 16 printed_by
				"'" + posted_by + "' , \n" + // 17 posted_by
				"null , \n" + // 18 date_posted
				"'" + remarks + "' , \n" + // 19 remarks
				"'" + doc_id + "' , \n" + // 20 doc_id
				"0 , \n" + // 21 proc_id
				"false , \n" + // 22 is_cancelled
				"false , \n" + // 23 is_for_reversal
				"null , \n" + // 24 reversal_date
				"null , \n" + // 25 reversal_jv_no
				"'A' , \n" + // 26 status_id
				"'" + created_by + "',  \n" + // 27 created_by
				"now(),  \n" + // 28 date_created
				"'" + edited_by + "' , \n" + // 29 edited_by
				"" + edited_date + " \n" + // 30 date_edited
				")   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	public void insertPV_detail(String rplf_no, pgUpdate db) {

		int rw = tblPV_account.getModel().getRowCount();
		int x = 0;
		int y = 1;

		while (x < rw) {

			String bal_side = "";
			Double deb_amt = Double.parseDouble(modelPV_account.getValueAt(x, 7).toString());
			Double cre_amt = Double.parseDouble(modelPV_account.getValueAt(x, 8).toString());
			Boolean isCorollary = (Boolean) modelPV_account.getValueAt(x, 9);
			Double amt = deb_amt + cre_amt;

			if (amt == 0 && !rplf_type_id.equals("04")) {
			} else {

				String acct_id = "";
				String proj_id = "";
				String subproj_id = "";
				String div_id = "";
				String dept_id = "";
				String sec_id = "";

				acct_id = modelPV_account.getValueAt(x, 0).toString().trim();
				if (deb_amt == 0) {
					bal_side = "C";
				} else {
					bal_side = "D";
				}

				try {
					proj_id = modelPV_account.getValueAt(x, 4).toString().trim();
				} catch (NullPointerException e) {
					proj_id = "";
				}
				try {
					subproj_id = modelPV_account.getValueAt(x, 5).toString().trim();
				} catch (NullPointerException e) {
					subproj_id = "";
				}
				try {
					div_id = modelPV_account.getValueAt(x, 1).toString().trim();
				} catch (NullPointerException e) {
					div_id = "";
				}
				try {
					dept_id = modelPV_account.getValueAt(x, 2).toString().trim();
				} catch (NullPointerException e) {
					dept_id = "";
				}
				try {
					sec_id = modelPV_account.getValueAt(x, 3).toString().trim();
				} catch (NullPointerException e) {
					sec_id = "";
				}

				String sqlDetail = "INSERT into rf_pv_detail values (" + "'" + co_id + "',  \n" + // 1 co_id
						"'" + co_id + "',  \n" + // 2 busunit_id
						"'" + rplf_no + "',  \n" + // 3 pv_no
						"" + y + ",  \n" + // 4 line_no
						"'" + acct_id + "',  \n" + // 5 acct_id
						"" + amt + ",  \n" + // 6 tran_amt
						"'" + bal_side + "',  \n" + // 7 bal_side
						"'" + proj_id + "',  \n" + // 8 project_id
						"'" + subproj_id + "',  \n" + // 9 sub_projectid
						"'" + div_id + "',  \n" + // 10 div_id
						"'" + dept_id + "',  \n" + // 11 dept_id
						"'" + sec_id + "',  \n" + // 12 sect_id
						"'" + co_id + "',  \n" + // 13 inter_co_id
						"'" + co_id + "',  \n" + // 14 busunit_id
						"'',   \n" + // 15 old_acct_id
						"'A',  \n" + // 16 status_id
						"'" + UserInfo.EmployeeCode + "',  \n" + // 17 created_by
						"now(),  \n" + // 18 date_created
						"'' , \n" + // 19 edited_by
						"null, \n" + // 20 date_edited
						""+isCorollary+" \n"+	
						")   ";

				System.out.printf("SQL #1: %s", sqlDetail);

				db.executeUpdate(sqlDetail, false);

				y++;
			}

			x++;
		}

	}

	private void insertAudit_trail(String activity, String remarks, pgUpdate db) {

		String user_code = UserInfo.EmployeeCode;

		String sqlDetail = "INSERT INTO mf_audit_trail(\n"
				+ "	              system_id, activity, user_code, date_upd, entity_id, \n"
				+ "	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n"
				+ "	      VALUES ('PV', '" + activity + "', '" + user_code + "', NOW(), NULL, \n"
				+ "	              NULL, NULL, NULL, NULL, '" + lookupPV_no.getText() + "', '" + remarks + "');";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public static void insertAudit_trail2(String activity, String remarks) {

		pgUpdate db = new pgUpdate();
		String user_code = UserInfo.EmployeeCode;

		String sqlDetail = "INSERT INTO mf_audit_trail(\n"
				+ "	              system_id, activity, user_code, date_upd, entity_id, \n"
				+ "	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n"
				+ "	      VALUES ('PV', '" + activity + "', '" + user_code + "', NOW(), NULL, \n"
				+ "	              NULL, NULL, NULL, NULL, '" + lookupPV_no.getText() + "', '" + remarks + "');";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
		db.commit();

	}

	public void updateRPLF_detail(String rplf_no, pgUpdate db) {

		String sqlDetail = "update rf_pv_detail set status_id = 'I' where trim(pv_no) = '" + lookupPV_no.getText()
				+ "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	public void updatePV_header(String rplf_no, pgUpdate db) {

		String pay_type_id = "";
		String edited_by = "";
		String entity_id1 = "";
		String entity_id2 = "";
		String remarks = "";

		pay_type_id = lookupPaymentType.getText().trim();
		edited_by = UserInfo.EmployeeCode;
		entity_id1 = lookupPayee1.getText().trim();
		entity_id2 = lookupPayee2.getText().trim();
		remarks = txtDRFRemark.getText().trim().replace("'", "''").trim();

		String sqlDetail = "update rf_pv_header set " + "pv_date = '" + dateFormat.format(dteRequest.getDate())
				+ "',  \n" + // 4
				"due_date = '" + dateFormat.format(dteNeeded.getDate()) + "',  \n" + // 5
				"pay_type_id = '" + pay_type_id + "' , \n" + // 7
				"entity_id1 = '" + entity_id1 + "' , \n" + // 7
				"entity_id2 = '" + entity_id2 + "' , \n" + // 7
				"edited_by = '" + edited_by + "' , \n" + // 20
				"date_edited = now()," + "remarks = '" + remarks + "' , \n" + // 20
				"status_id = 'A' \n" + // 21
				"where pv_no = '" + lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public void updatePV_header_forsetup(String rplf_no, pgUpdate db) {

		String pay_type_id = "";
		String edited_by = "";
		String entity_id1 = "";
		String entity_id2 = "";
		String ent_type_id = ""; // ---added by jed 2018-10-30 no dcrf---//
		String remarks = "";

		pay_type_id = lookupPaymentType.getText().trim();
		edited_by = UserInfo.EmployeeCode;
		entity_id1 = lookupPayee1.getText().trim();
		entity_id2 = lookupPayee2.getText().trim();
		ent_type_id = lookupPayeeType.getText().trim(); // ---added by jed 2018-10-30 no dcrf---//
		remarks = txtDRFRemark.getText().trim().replace("'", "''");

		String sqlDetail = "update rf_pv_header set " + "pv_date = '" + dateFormat.format(dteRequest.getDate())
				+ "',  \n" + // 4
				"due_date = '" + dateFormat.format(dteNeeded.getDate()) + "',  \n" + // 5
				"pay_type_id = '" + pay_type_id + "' , \n" + // 7
				"entity_id1 = '" + entity_id1 + "' , \n" + // 7
				"entity_id2 = '" + entity_id2 + "' , \n" + // 7
				"entity_type_id = '" + ent_type_id + "' , \n" + // ---added by jed 2018-10-30 no dcrf---//
				"edited_by = '" + edited_by + "' , \n" + // 20
				"date_edited = now()," + "remarks = '" + remarks + "' , \n" + // 20
				"status_id = 'A' \n" + // 21
				"where pv_no = '" + lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public void updatePV_header_pvdate(String rplf_no, pgUpdate db) {

		String sqlDetail = "update rf_pv_header set " + "pv_date = '" + dateFormat.format(dteRequest.getDate())
				+ "',  \n" + "edited_by = '" + UserInfo.EmployeeCode + "' , \n" + "date_edited = now() \n"
				+ "where pv_no = '" + lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	private void updatePV_header_pvpayee(String rplf_no, pgUpdate db) {

		String sqlDetail = "update rf_pv_header set " + "entity_id1 = '" + lookupPayee1.getValue() + "',  \n"
				+ "edited_by = '" + UserInfo.EmployeeCode + "' , \n" + "date_edited = now() \n" + "where pv_no = '"
				+ lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public void iactivatePV_header(String rplf_no, pgUpdate db) {

		String sqlDetail = "update rf_pv_header set " + "status_id = 'I',  \n" + "edited_by = '" + UserInfo.EmployeeCode
				+ "' , \n" + // 20
				"date_edited = now() \n" + // 21
				"where pv_no = '" + lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public void iactivatePV_details(String rplf_no, pgUpdate db) {

		String sqlDetail = "update rf_pv_detail set " + "status_id = 'I'  \n" + "where pv_no = '"
				+ lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public static void postPV_header(String rplf_no, pgUpdate db) {

		String sqlDetail = "update rf_pv_header set status_id = 'P', proc_id = 3 , posted_by = '"
				+ UserInfo.EmployeeCode + "',  \n" + "date_posted = now()  " + "where trim(pv_no) = '"
				+ lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	public static void tagPV_header(String rplf_no, pgUpdate db) {

		String sqlDetail = "update rf_pv_header set status_id = 'F', proc_id = 1  " + "where trim(pv_no) = '"
				+ lookupPV_no.getText() + "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	private void untagPV_header(String pv, pgUpdate db) {// used

		String sqlDetail = "update rf_pv_header set status_id = 'A', proc_id = 0 " + "where trim(pv_no) = '"
				+ lookupPV_no.getText() + "' and trim(co_id) = '" + co_id + "' " + "and status_id = 'F' ";

		db.executeUpdate(sqlDetail, false);

	}

	public void updateCDF(String rplf_no, pgUpdate db) {

		String sqlDetail = "update cm_cdf_hd set " + "status_id = 'P' " + "where trim(rplf_no) = '" + rplf_no
				+ "' and co_id = '" + co_id + "'   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	// open screens implementations
	public void openDRF() {

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

			if (lookupDRF_no.getText().equals("")) {
			} else {
				RequestForPayment.drf_no = pv_no;
				RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);

				RequestForPayment.setDRF_header(RequestForPayment.drf_no);
				RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF,
						RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no);
				RequestForPayment.btnAddNew.setEnabled(false);
				RequestForPayment.btnRefresh.setEnabled(true);
				RequestForPayment.btnPreview.setEnabled(true);
				if (RequestForPayment.isPVcreated() == true) {
					RequestForPayment.btnEdit.setEnabled(false);
				} else {
					RequestForPayment.btnEdit.setEnabled(true);
				}
				RequestForPayment.mnidelete.setEnabled(false);
				RequestForPayment.mniaddrow.setEnabled(false);
			}
		}
	}

	public static void openCV() {

		String pv_no = lookupPV_no.getValue().toString();

		if (checkCVremarks(pv_no) == true) {// **ADDED BY JED 2019-05-21 : FOR PREVIEWING CHECK VOUCHER (MC)**//

			CheckVoucherMC chk_vchr_mc = new CheckVoucherMC();
			Home_JSystem.addWindow(chk_vchr_mc);

			if (co_id.equals("")) {
			} else {

				CheckVoucherMC.co_id = co_id;
				CheckVoucherMC.tagCompany.setTag(company);
				CheckVoucherMC.company = company;
				CheckVoucherMC.company_logo = company_logo;
				CheckVoucherMC.lookupCompany.setValue(co_id);

				CheckVoucherMC.lblDV_no.setEnabled(true);
				CheckVoucherMC.lookupDV_no.setEnabled(true);
				CheckVoucherMC.lookupDV_no.setLookupSQL(CheckVoucherMC.getDV_no(co_id));
				CheckVoucherMC.enableButtons(true, false, false, false, false, false, false, false, false, false);
				CheckVoucherMC.payee = "";

				if (!lookupCV_no.getText().equals("")) {
					String cv_no = lookupCV_no.getText();
					CheckVoucherMC.cv_no = cv_no;
					CheckVoucherMC.refresh_fields();
					CheckVoucherMC.refresh_tablesMain();
					CheckVoucherMC.setDV_header(cv_no);
					CheckVoucherMC.lookupDV_no.setValue(cv_no);

					String status = RequestForPayment.sql_getCVstatus(cv_no, co_id);
					if (status.equals("A")) {
						CheckVoucherMC.enableButtons(false, true, true, true, true, false, true, false, false, true);
						CheckVoucherMC.displayDV_account_entries(CheckVoucher.modelDV_acct_entries,
								CheckVoucher.rowHeaderDV_acct_entries, CheckVoucher.modelDV_accttotal, cv_no);
						CheckVoucherMC.displayDV_pv(CheckVoucher.modelDV_pv, CheckVoucher.rowHeaderDV_pv,
								CheckVoucher.modelDV_pvtotal, cv_no);
					} else if (status.equals("F")) {
						CheckVoucherMC.enableButtons(false, false, true, true, true, false, false, true, true, true);
						CheckVoucherMC.displayDV_account_entries(CheckVoucherMC.modelDV_acct_entries,
								CheckVoucherMC.rowHeaderDV_acct_entries, CheckVoucherMC.modelDV_accttotal, cv_no);
						CheckVoucherMC.displayDV_pv(CheckVoucherMC.modelDV_pv, CheckVoucherMC.rowHeaderDV_pv,
								CheckVoucherMC.modelDV_pvtotal, cv_no);
					} else if (status.equals("P")) {
						CheckVoucherMC.enableButtons(false, false, true, true, true, false, false, false, false, false);
						CheckVoucherMC.displayDV_account_entries(CheckVoucherMC.modelDV_acct_entries,
								CheckVoucherMC.rowHeaderDV_acct_entries, CheckVoucherMC.modelDV_accttotal, cv_no);
						CheckVoucherMC.displayDV_pv(CheckVoucherMC.modelDV_pv, CheckVoucherMC.rowHeaderDV_pv,
								CheckVoucherMC.modelDV_pvtotal, cv_no);
					} else {
						CheckVoucherMC.enableButtons(false, false, false, true, true, false, false, false, false,
								false);
					}

				}

			}

		} else {

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
				CheckVoucher.payee = "";

				if (!lookupCV_no.getText().equals("")) {
					String cv_no = lookupCV_no.getText();
					CheckVoucher.cv_no = cv_no;
					CheckVoucher.refresh_fields();
					CheckVoucher.refresh_tablesMain();
					CheckVoucher.setDV_header(cv_no);
					CheckVoucher.lookupDV_no.setValue(cv_no);

					String status = RequestForPayment.sql_getCVstatus(cv_no, co_id);
					if (status.equals("A")) {
						CheckVoucher.enableButtons(false, true, true, true, true, false, true, false, false, true);
						CheckVoucher.displayDV_account_entries(CheckVoucher.modelDV_acct_entries,
								CheckVoucher.rowHeaderDV_acct_entries, CheckVoucher.modelDV_accttotal, cv_no);
						CheckVoucher.displayDV_pv(CheckVoucher.modelDV_pv, CheckVoucher.rowHeaderDV_pv,
								CheckVoucher.modelDV_pvtotal, cv_no);
					} else if (status.equals("F")) {
						CheckVoucher.enableButtons(false, false, true, true, true, false, false, true, true, true);
						CheckVoucher.displayDV_account_entries(CheckVoucher.modelDV_acct_entries,
								CheckVoucher.rowHeaderDV_acct_entries, CheckVoucher.modelDV_accttotal, cv_no);
						CheckVoucher.displayDV_pv(CheckVoucher.modelDV_pv, CheckVoucher.rowHeaderDV_pv,
								CheckVoucher.modelDV_pvtotal, cv_no);
					} else if (status.equals("P")) {
						CheckVoucher.enableButtons(false, false, true, true, true, false, false, false, false, false);
						CheckVoucher.displayDV_account_entries(CheckVoucher.modelDV_acct_entries,
								CheckVoucher.rowHeaderDV_acct_entries, CheckVoucher.modelDV_accttotal, cv_no);
						CheckVoucher.displayDV_pv(CheckVoucher.modelDV_pv, CheckVoucher.rowHeaderDV_pv,
								CheckVoucher.modelDV_pvtotal, cv_no);
					} else {
						CheckVoucher.enableButtons(false, false, false, true, true, false, false, false, false, false);
					}

				}

			}

		}

	}

	private static Boolean checkCVremarks(String pv_no) {

		Boolean x = false;

		String SQL = "select\n" + "b.remarks\n" + "from rf_pv_header a\n"
				+ "left join rf_cv_header b on b.cv_no = a.cv_no and b.co_id = b.co_id\n" + "where a.pv_no = '" + pv_no
				+ "'\n" + "and b.remarks ~* 'Released through AUB E-payment Facility' ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0].equals("") || db.getResult()[0][0].equals("null")) {
				x = false;
			} else {
				x = true;
			}
		} else {
			x = false;
		}

		return x;

	}

	public void openDP() {

		if (co_id.equals("")) {
			DocsProcessing doc_proc = new DocsProcessing();
			Home_JSystem.addWindow(doc_proc);
		}

		else {
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
			DocsProcessing.lookupDocType.setValue("12");
			DocsProcessing.tagDocType.setTag("PAYABLE VOUCHER");

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
	}

	public static void open2307() {

		Form2307_Monitoring cwt = new Form2307_Monitoring();
		Home_JSystem.addWindow(cwt);

		if (co_id.equals("")) {

		} else {

			if (!lookupDRF_no.getText().equals("")) {

				Form2307_Monitoring.co_id = co_id;
				Form2307_Monitoring.lookupCompany.setValue(co_id);
				Form2307_Monitoring.tagCompany.setTag(company);
				Form2307_Monitoring.company_logo = company_logo;
				Form2307_Monitoring.lookupCompany.setValue(co_id);
				Form2307_Monitoring.tagCompany.setTag(company);

				// set enable/disable fields
				Form2307_Monitoring.rbtnAllPayee.setEnabled(true);
				Form2307_Monitoring.rbtnAllPayee.setSelected(false);
				Form2307_Monitoring.lblPayeeType.setEnabled(true);
				Form2307_Monitoring.lblPayee.setEnabled(true);
				Form2307_Monitoring.lblPV_no.setEnabled(true);
				Form2307_Monitoring.lblRequestType.setEnabled(true);
				Form2307_Monitoring.lookupPayeeType.setEnabled(true);
				Form2307_Monitoring.lookupPayee.setEnabled(true);
				Form2307_Monitoring.lookupPV_no.setEnabled(true);
				Form2307_Monitoring.cmbPmtType.setEnabled(true);
				Form2307_Monitoring.tagPayeeType.setEnabled(true);
				Form2307_Monitoring.tagPayee.setEnabled(true);
				Form2307_Monitoring.tagPV_no.setEnabled(true);
				Form2307_Monitoring.lblYear.setEnabled(true);
				Form2307_Monitoring.lblPeriod.setEnabled(true);
				Form2307_Monitoring.lblMonth.setEnabled(true);
				Form2307_Monitoring.cmbYear.setEnabled(true);
				Form2307_Monitoring.cmbPeriod.setEnabled(true);
				Form2307_Monitoring.cmbMonth.setEnabled(false);
				Form2307_Monitoring.btnGenerate.setEnabled(true);
				Form2307_Monitoring.btnCancel.setEnabled(true);
				Form2307_Monitoring.setButtonsStatus(true, true, false, true);

				// set PV No.
				Form2307_Monitoring.lookupPV_no.setText(lookupDRF_no.getText());
				Form2307_Monitoring.PV_num = lookupDRF_no.getText();

				Form2307_Monitoring.displayEWT_for2307Sending(Form2307_Monitoring.modelFor_sending,
						Form2307_Monitoring.rowHeaderFor_sending, Form2307_Monitoring.modelFor_sending_total, "", "",
						"", "", "", "");
				Form2307_Monitoring.displayEWT_for2307Received(Form2307_Monitoring.modelReceived,
						Form2307_Monitoring.rowHeaderReceived, Form2307_Monitoring.modelReceived_total, "", "", "", "",
						"", "");

				Form2307_Monitoring.month_from = "01";
				Form2307_Monitoring.month_to = "12";
				Form2307_Monitoring.day_from = "01";
				Form2307_Monitoring.day_to = "31";

				Form2307_Monitoring.lookupPayeeType.setLookupSQL(Form2307_Monitoring.getAvailerType());
				Form2307_Monitoring.lookupPayee.setLookupSQL(Form2307_Monitoring.getEntityList());
				Form2307_Monitoring.lookupPV_no.setLookupSQL(Form2307_Monitoring.getPV_no());

			}
		}

	}

	public void print2307() {

		// preview(pv_no, co_id);
		String criteria = "Form 2307";
		String co_address = "";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		String secondPayor = "CARLOS CHENG / PRESIDENT / TIN 100-775-734-000"; // added by jed 8/29/18, change by JED
																				// 2018-12-10 from VICTOR H. MANARANG TO
																				// CARLOS CHENG dcrf no.879 for CDC//
		String firstPayor = "ANDRES CHUA / PRESIDENT / TIN 147-299-505-000"; // added by jed 8/29/18 for VDC
		String thirdPayor = "VICTOR MANARANG / PRESIDENT / TIN 108-728-634-000"; // added by jari cruz july 20, 2022 for
																					// acerhomes
		String fourthPayor = "MYDIE CARISSE M. ARUCAN / COST ACCOUNTING OFFICER / TIN 428-793-727-000"; // added by jari
																										// cruz july 20,
																										// 2022 for
																										// extraordinary

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", co_id);
		mapParameters.put("rplf_no", lookupPV_no.getText());
		/* COMMENTED BY JED 2019-12-4 */
		/*
		 * mapParameters.put("bir_logo",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/BIR.jpg"));
		 * mapParameters.put("arrow1",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow2",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow3",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow4",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow5",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow6",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow7",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow8",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow9",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow10",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow11",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 * mapParameters.put("arrow12",
		 * this.getClass().getClassLoader().getResourceAsStream("Images/aroow2.jpg"));
		 */
		mapParameters.put("p_background",
				this.getClass().getClassLoader().getResourceAsStream("Images/Form2307new.jpg"));

		if (lookupCompany.getValue().equals("01")) {

			co_address = "Unit 701 7th Floor Summit One Tower, 530 Shaw Boulevard, Highway Hills, Mandaluyong City 1550";

			mapParameters.put("payor", firstPayor);/* ADDED by JED 8/29/18 : PAYOR FOR VDC */
			mapParameters.put("p_co_address",
					co_address); /* ADDED BY JED 2021-12-28 DCRF NO. 1900 : CHANGE COMPANY ADDRESS */
			// System.out.printf("The value of payor is: %s\n", firstPayor);
		}
		if (lookupCompany.getValue().equals("02")) {

			mapParameters.put("payor", secondPayor);/* ADDED by JED 8/29/18 : PAYOR FOR CDC */
			// System.out.printf("The value of payor is: %s\n", secondPayor);
		}
		if (lookupCompany.getValue().equals("04")) {

			mapParameters.put("payor", thirdPayor);/* ADDED by JARI CRUZ JULY 20, 2022 : PAYOR FOR ACERHOMES */
		}
		if (lookupCompany.getValue().equals("05")) {

			mapParameters.put("payor", fourthPayor);/* ADDED by JARI CRUZ JULY 20, 2022 : PAYOR FOR EXTRAORDINARY */
		}
		// System.out.printf("BIR: %s%n",
		// this.getClass().getClassLoader().getResourceAsStream("Images/BIR.jpg").toString());

		/* COMMENTED BY JED 2021-05-04 DUE TO PV 871 USE OLD FORMAT BIR */
//		if (lookupPV_no.getText().equals("000000871") || lookupPV_no.getText().equals("000001332")  || lookupPV_no.getText().equals("000002219")) {
//			FncReport.generateReport("/Reports/rptForm2307_PVnew.jasper", reportTitle, company, mapParameters);	
//		} 
		/* COMMENTED BY JED 2021-05-04 DUE TO PV 871 USE OLD FORMAT BIR */

		/*
		 * else if (lookupPayeeType.getText().equals("03") ||
		 * lookupPayeeType.getText().equals("04") ||
		 * lookupPayeeType.getText().equals("23") ||
		 * lookupPayeeType.getText().equals("24")
		 * ||lookupPayeeType.getText().equals("34") ||
		 * lookupPayeeType.getText().equals("35") //added by Del Gonzales 02 08 2017 :
		 * per request ) {
		 * FncReport.generateReport("/Reports/rptForm2307_PV_com.jasper", reportTitle,
		 * company, mapParameters);
		 */

		/* EDITED BY JED 2019-12-04 DCRF NO. 1298 : CHANGE FORMAT OF BIR FORM */
		if (com() == true) {
			// FncReport.generateReport("/Reports/rptForm2307_PV_com.jasper", reportTitle,
			// company, mapParameters);
			FncReport.generateReport("/Reports/rptForm2307_PV_com_new.jasper", reportTitle, company, mapParameters);
		} else if (UserInfo.EmployeeCode.equals("901260")) {
			FncReport.generateReport("/Reports/rptForm2307_PV_new_v2.jasper", reportTitle, company, mapParameters);
		} else {
			// FncReport.generateReport("/Reports/rptForm2307_PV.jasper", reportTitle,
			// company, mapParameters);
			FncReport.generateReport("/Reports/rptForm2307_PV_new.jasper", reportTitle, company, mapParameters);
		}

	}

	public void printOR() {
		// --ADDED BY JED 2018-12-11--//
		String payee_type = lookupPayeeType.getText();

		if (payee_type.equals("02")) {

			JOptionPane.showOptionDialog(getContentPane(), pnlSub, "Payable Voucher", JOptionPane.PLAIN_MESSAGE,
					JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

		} else {

			// preview(pv_no, co_id);
			String criteria = "Official Receipt";
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
			String co_id = lookupCompany.getValue(); // ---added by jed 2018-11-08 : parameter added for previewing
														// OR---//
			String entity_type_id = lookupPayeeType.getText();

			System.out.printf("The value of entity_type_id: %s\n", entity_type_id);

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id", co_id);
			mapParameters.put("co_name", company); // ---added by jed 2018-11-08 : parameter added for previewing
													// OR---//
			mapParameters.put("pv_no", lookupPV_no.getText());
			mapParameters.put("paytype", lookupPaymentType.getText());
			mapParameters.put("bg", this.getClass().getClassLoader().getResourceAsStream("Images/bgOR.jpg"));
			mapParameters.put("entity_type_id", entity_type_id);// --added by JED 2018-12-11 drcf no. 878 : include
																// nature of payment in sample OR--//

			FncReport.generateReport("/Reports/rptOR_v2.jasper", reportTitle, company, mapParameters);

		}
	}

	public void editPVPaid() {

		to_do = "editpv_paid";
		btnSave.setText("Save");
		btnSave.setActionCommand("Save");
		lblDateTrans.setEnabled(true);
		dteRequest.setEnabled(true);
		enable_buttons(false, false, true, false, false, true);

	}

	public void editPVPayee() {

		to_do = "editpv_payee";
		btnSave.setText("Save");
		btnSave.setActionCommand("Save");
		lookupPayee1.setEnabled(true);
		enable_buttons(false, false, true, false, false, true);

	}

	/*
	 * private static Object [] getMonthYear() {//used
	 * 
	 * DateFormat df = new SimpleDateFormat("yyyy-dd-MM"); String month =
	 * df.format(dteRequest.getDate()); String month_sub = month.substring(8);
	 * 
	 * String month_word [] = {"January", "February", "March", "April", "May",
	 * "June", "July", "August", "September", "October", "November", "December" };
	 * 
	 * DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy"); String year =
	 * df2.format(dteRequest.getDate()); Integer year_sub =
	 * Integer.parseInt(year.substring(6))-2000; Integer year_full =
	 * Integer.parseInt(year.substring(6));
	 * 
	 * Object x [] = {month_sub, year_sub, year_full,
	 * month_word[Integer.parseInt(month_sub)-1], year.substring(6)};
	 * 
	 * java.util.Date start_date = Calendar.getInstance().getTime(); Calendar cal =
	 * Calendar.getInstance(); cal.setTime(start_date); cal.add(Calendar.DATE, 10);
	 * // add 10 days java.util.Date date = cal.getTime();
	 * 
	 * return x;
	 * 
	 * }
	 */

	private void generateDetail(String lineno) {
		Object[] listCode = new Object[5];
		int row = tblPV_account.convertRowIndexToModel(tblPV_account.getSelectedRow());

		listCode[0] = modelPV_account.getValueAt(row, 1).equals(null) ? null : modelPV_account.getValueAt(row, 9);
		listCode[1] = modelPV_account.getValueAt(row, 2).equals(null) ? null : modelPV_account.getValueAt(row, 10);
		listCode[2] = modelPV_account.getValueAt(row, 4).equals(null) ? null : modelPV_account.getValueAt(row, 11);
		listCode[3] = modelPV_account.getValueAt(row, 5).equals(null) ? null : modelPV_account.getValueAt(row, 12);
		// listCode[4] = modelPV_account.getValueAt(row, 12).equals(null) ? null :
		// modelPV_account.getValueAt(row, 13);//**EDITED BY JED 2020-08-03 : DISPLAY
		// TAG DETAILS**//
		listCode[4] = lookupPayee2.getValue().equals(null) ? null : modelPV_account.getValueAt(row, 13);

		Object[] newListCode = new Object[5];
		newListCode[0] = listCode[0];
		newListCode[1] = listCode[1];
		newListCode[2] = listCode[2];
		newListCode[3] = listCode[3];
		newListCode[4] = listCode[4];

		tagDetail.setText(String.format("[ Div: %s - Dep: %s - Proj: %s - Sub: %s - Availer: %s ]", newListCode));
	}

	private void generateDetail_SL(String lineno) {// **ADDED BY JED 2020-08-03 : DISPLAY TAG DETAILS IN SUBSIDIARY
													// LEDGER**//
		Object[] listCode = new Object[5];
		int row = tblPV_SL.convertRowIndexToModel(tblPV_SL.getSelectedRow());

		listCode[0] = modelPV_SL.getValueAt(row, 8).equals(null) ? null : modelPV_SL.getValueAt(row, 15);
		listCode[1] = modelPV_SL.getValueAt(row, 9).equals(null) ? null : modelPV_SL.getValueAt(row, 16);
		listCode[2] = modelPV_SL.getValueAt(row, 6).equals(null) ? null : modelPV_SL.getValueAt(row, 17);
		listCode[3] = modelPV_SL.getValueAt(row, 7).equals(null) ? null : modelPV_SL.getValueAt(row, 18);
		listCode[4] = modelPV_SL.getValueAt(row, 0).equals(null) ? null : modelPV_SL.getValueAt(row, 13);

		Object[] newListCode = new Object[5];
		newListCode[0] = listCode[0];
		newListCode[1] = listCode[1];
		newListCode[2] = listCode[2];
		newListCode[3] = listCode[3];
		newListCode[4] = listCode[4];

		tagDetail_1.setText(String.format("[ Div: %s - Dep: %s - Proj: %s - Sub: %s - Availer: %s ]", newListCode));
	}

	private static String getEntityList() {

		String sql = "select " + "trim(entity_id) as \"Entity ID\",  \n" + "trim(entity_name) as \"Name\",  \n"
				+ "entity_kind as \"Kind\",  \n" + "vat_registered as \"VAT\"  \n" + "from ("
				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where status_id = 'A' limit 3000) union \n"
				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in (select entity_id  from em_employee)) union \n"
				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where vat_registered = true )) a \n"
				+ "order by a.entity_name  ";
		return sql;

	}
	/*
	 * private boolean rplfList() {
	 * 
	 * boolean x = false; String pv_no = "";
	 * 
	 * String strSQL =
	 * "select rplf_no from rf_request_header where rplf_type_id in ('02', '07') and rplf_no = '"
	 * +lookupPV_no.getValue()+"'";
	 * 
	 * pgSelect db = new pgSelect(); db.select(strSQL); if(db.isNotNull()){ pv_no =
	 * (String) db.getResult()[0][0]; }
	 * 
	 * if (pv_no.equals(null) || pv_no.isEmpty()) { x=false; System.out.println(x);
	 * } else {x=true;}
	 * 
	 * return x; }
	 */

	private boolean com() {// EDITED BY JED 2021-04-28 : add company for filter

		boolean x = false;
		String pv_no = "";

		String strSQL = "select rplf_no from rf_request_header where rplf_type_id in ('04') and rplf_no = '"
				+ lookupPV_no.getValue() + "' and co_id = '" + co_id + "'";

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

	private void reverse_pv() {

		JournalVoucher jv = new JournalVoucher();
		if (Home_JSystem.isNotExisting("JournalVoucher")) {
			Home_JSystem.addWindow(jv);
		}

		if (co_id.equals("")) {

		} else {
			JournalVoucher.co_id = co_id;
			JournalVoucher.company = company;
			JournalVoucher.lookupCompany.setValue(co_id);
			JournalVoucher.tagCompany.setTag(company);
			JournalVoucher.company_logo = company_logo;
			JournalVoucher.lblJV_no.setEnabled(true);
			JournalVoucher.lookupJV.setEnabled(true);
			JournalVoucher.lookupJV.setLookupSQL(JournalVoucher.getJV_no());
			JournalVoucher.add();
			JournalVoucher.reversePV(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account,
					JournalVoucher.modelJV_accounttotal, pv_no, co_id);
			JournalVoucher.reversePV_SL(JournalVoucher.modelJV_SL, JournalVoucher.rowHeaderJV_SL, JournalVoucher.modelJV_SL_total, pv_no, co_id); //Added by Erick DCRF # 2118 to create jv_sl during reversal of PV
			JournalVoucher.lookupTranType.setValue("00029");
			JournalVoucher.tagTranType.setTag("REVERSAL OF PV");
			JournalVoucher.txtJV_Remark.setText("Reversal of PV No. " + pv_no + "\n" + txtDRFRemark.getText());
			JournalVoucher.executeReversal();
			JournalVoucher.untagPV_header_reversalOfPV(pv_no, co_id);
		}

	}

//	private static String checkDatePaid (String cv_no, String co_id){
//		
//		String dte_paid = "";
//		String sql =
//				"select (date_paid)::varchar from rf_cv_header where cv_no = '"+cv_no+"' and co_id = '"+co_id+"' and status_id in ('A', 'P')" ;
//		
//		pgSelect db = new pgSelect();
//		db.select(sql);
//		
//		if(db.isNotNull()){
//			if((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")){
//				dte_paid = "";
//			}else{
//				dte_paid = (String)db.getResult()[0][0].toString();
//			}
//		}else{
//			dte_paid = "";
//		}
//		
//		return dte_paid;
//	}

	private static String checkDV_status_id(String cv_no, String co_id) {

		String status_id = "";
		String sql = "select status_id from rf_cv_header where cv_no = '" + cv_no + "' and co_id = '" + co_id + "' ";

		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {
			if (db.getResult()[0][0].equals(null) || db.getResult()[0][0].equals("")) {
			} else {
				status_id = (String) db.getResult()[0][0].toString();
			}
		} else {
			status_id = "";
		}

		return status_id;
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

	private Boolean checkAcctBalanceIfEqual() {// used

		boolean x = true;

		Double debit = Double.parseDouble(modelPV_accounttotal.getValueAt(0, 7).toString());
		Double credit = Double.parseDouble(modelPV_accounttotal.getValueAt(0, 8).toString());
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
	
	//Added by Erick
	private static Boolean checktag(String rplf_no, String co_id) {
		Boolean with_tag = false;
		String sql = "select rplf_no from rf_request_header a\n"
				+ "where rplf_no = '"+rplf_no+"' and co_id = '"+co_id+"' and rplf_type_id in ('01','06','11') \n" //Added by erick 2023-09-14 rplf_type_id DCRF 2739
				+ "and (exists (select rplf_no from rf_transfer_cost where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"')\n"
				+ "OR exists (select rplf_no  from rf_processing_cost where rplf_no = '"+rplf_no+"' and status_id = 'A' and co_id = '"+co_id+"'))\n"
				+ "";
		
		System.out.println("checktag: "+sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()) {
			with_tag = true;
		}else {
			with_tag = false;
		}
		
		return with_tag;
	}
}