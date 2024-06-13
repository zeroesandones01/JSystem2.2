package Accounting.Collections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import java.util.Calendar;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import tablemodel.modelCashDeposit;
import tablemodel.modelCheckDeposit;
import tablemodel.modelDepositAccountEntries;
import Accounting.Disbursements.RequestForPayment;
import Accounting.GeneralLedger.JournalVoucher;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class Deposits extends _JInternalFrame implements _GUI, ActionListener, MouseListener {
	
	/*
	 * AS OF 2022-02-04
	 * 
	 * 1. EDIT PROCEDURE IN CREATING JV : EDIT THE PROCEDURE TO CORRECT JV CREATOR 2022-02-04
	 * 
	 * */

	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Deposits";
	static Dimension SIZE = new Dimension(1000, 600);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlCheckDetails;
	private JPanel pblCheckTable;
	private JPanel pnlSouth;
	private JPanel pnlBank;
	private JPanel pnlA;
	private JPanel pnlB;
	private JPanel pnlA1;
	private JPanel pnlA2;
	private JPanel pnlA1_a;
	private JPanel pnlA1_b;
	private JPanel pnlA2_a;
	private JPanel pnlA2_b;
	private JPanel pnlB1;
	private JPanel pnlBank_1;
	private JPanel pnlBank_2;
	private JPanel pnlBank_1a;
	private JPanel pnlBank_1b;
	private JPanel pnlBank_2a;
	private JPanel pnlBank_2b;
	private JPanel pnlTable;
	private JPanel pblTableTotal;
	private JPanel panelJV;
	private JPanel pnlSample4;

	private JLabel lblCompany;
	private JLabel lblDepositNo;
	private JLabel lblDepositDate;
	private JLabel lblBankAcctNo;
	private JLabel lblBankAcct;
	private JLabel lblBankAcctAlias;
	private JLabel lblOfficeBranch;
	private JLabel lblBankBranch;
	private JLabel lblTotal;
	private JLabel lblJVlabel;

	private JXTextField txtBankName;
	private JXTextField txtBankAccount;
	private JXTextField txtBankBranch;
	private JTextArea txtJVRemark;
	private _JXFormattedTextField txtTotal;

	private _JTabbedPane tabCenter;

	/*
	 * reserved for other checks inventory tab private _JTableMain tblSample3Main;
	 * private _JScrollPaneMain scrollSample3Main; private _JScrollPaneTotal
	 * scrollSample3Total; private modelOtherCheckInventory modelSample3Main;
	 * private modelOtherCheckInventory modelSample3Total; private _JTableTotal
	 * tblSample3Total; private JList rowHeaderSample3Main;
	 */

	private _JTableMain tblSample2Main;
	private _JTableMain tblSample4Main;
	private _JTableMain tblSample1Main;

	private JScrollPane scpJVRemark;

	private _JScrollPaneMain scrollSample2Main;
	private _JScrollPaneMain scrollSample1Main;
	private _JScrollPaneMain scrollSample4Main;

	private _JScrollPaneTotal scrollSample2Total;
	private _JScrollPaneTotal scrollSample4Total;
	private _JScrollPaneTotal scrollSample1Total;

	private modelDepositAccountEntries modelSample4Main;
	private modelDepositAccountEntries modelSample4Total;
	private modelCashDeposit modelSample1Main;
	private modelCheckDeposit modelSample2Total;
	private modelCashDeposit modelSample1Total;
	private modelCheckDeposit modelSample2Main;

	private _JTableTotal tblSample2Total;
	private _JTableTotal tblSample1Total;
	private _JTableTotal tblSample4Total;

	private _JLookup lookupCompany;
	private _JLookup lookupDepositNo;
	private _JLookup lookupOfficeBranch;
	private _JLookup lookupBankAcctNo;

	private _JTagLabel tagCompany;
	private _JTagLabel tagBranch;
	private _JTagLabel tagJV;

	private JList rowHeaderSample2Main;
	private JList rowHeaderSample4Main;
	private JList rowHeaderSample1Main;

	private _JDateChooser dteDeposit;

	private JButton btnPost;
	private JButton btnCancel;
	private JButton btnAddNew;
	private JButton btnEdit;
	private JButton btnRefresh;
	private JButton btnSave;
	private JButton btnDelete;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private String dep_no = "";
	private String co_id = "";
	private String bank_acct_id = "";
	private String jv_no = "";
	private String period = "";
	private Integer year_full = 2000;
	private String to_do = "";
	static String company = "";
	static String company_logo = "";

	DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
	private JPopupMenu menu2;
	private JMenuItem mniopenJV;
	private JButton btnPreview;
	private JLabel lblCollectionDate;
	private _JDateChooser dteCollection;

	private JXPanel panConfirm;
	private JLabel lblCollection;
	private JLabel lblCashCount;
	private JLabel lblVariance;
	private JTextField txtCollection;
	private JTextField txtCashCount;
	private JTextField txtVariance;

	private static double variance = 0.00;

	private JTextField txtJV;
	private JTextField txtJVStatus;
	private JDateChooser dteJV; 
	
	public Deposits() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Deposits(String title) {
		super(title);

	}

	public Deposits(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(1034, 603));
		this.setBounds(0, 0, 1034, 603);

		{
			menu2 = new JPopupMenu("Popup");
			mniopenJV = new JMenuItem("Open Journal Voucher");
			JSeparator sp = new JSeparator();
			menu2.add(sp);
			menu2.add(mniopenJV);

			mniopenJV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openJV();
				}
			});
		}

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			// pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(1022, 237));

			pnlA = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlA, BorderLayout.NORTH);
			pnlA.setPreferredSize(new java.awt.Dimension(1119, 32));

			pnlA1 = new JPanel(new BorderLayout(5, 5));
			pnlA.add(pnlA1, BorderLayout.WEST);
			pnlA1.setPreferredSize(new java.awt.Dimension(534, 32));

			pnlA1_a = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlA1.add(pnlA1_a, BorderLayout.WEST);
			pnlA1_a.setPreferredSize(new java.awt.Dimension(168, 30));
			pnlA1_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

			{
				lblCompany = new JLabel("  COMPANY");
				pnlA1_a.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(101, 16));
			}
			{
				lookupCompany = new _JLookup(null, "Company", 0, 2);
				pnlA1_a.add(lookupCompany);
				lookupCompany.setLookupSQL(SQL_COMPANY());
				lookupCompany.setReturnColumn(0);
				lookupCompany.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						if (data != null) {

							co_id = (String) data[0];
							String name = (String) data[1];
							company = (String) data[1];
							company_logo = (String) data[3];

							tagCompany.setTag(name);
							lblDepositNo.setEnabled(true);
							lookupDepositNo.setEnabled(true);
							lblOfficeBranch.setEnabled(true);
							lookupOfficeBranch.setEnabled(true);
							tagBranch.setEnabled(true);
							lookupBankAcctNo.setLookupSQL(sql_bankacct(co_id));
							lookupDepositNo.setLookupSQL(sql_deposit_no(co_id));

							enableButtons(true, false, false, false, false, false, false, false);
							refreshTables();
							refreshFields();

							KEYBOARD_MANAGER.focusNextComponent();
						}
					}
				});
			}

			pnlA1_b = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlA1.add(pnlA1_b, BorderLayout.CENTER);
			pnlA1_b.setPreferredSize(new java.awt.Dimension(387, 32));
			pnlA1_b.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

			{
				tagCompany = new _JTagLabel("[ ]");
				pnlA1_b.add(tagCompany);
				tagCompany.setBounds(209, 27, 700, 22);
				tagCompany.setEnabled(true);
				tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
			}

			pnlA2 = new JPanel(new BorderLayout(5, 5));
			pnlA.add(pnlA2, BorderLayout.CENTER);
			pnlA2.setPreferredSize(new java.awt.Dimension(544, 32));

			pnlA2_a = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlA2.add(pnlA2_a, BorderLayout.WEST);
			pnlA2_a.setPreferredSize(new java.awt.Dimension(145, 32));
			pnlA2_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

			{
				lblOfficeBranch = new JLabel("BRANCH");
				pnlA2_a.add(lblOfficeBranch);
				lblOfficeBranch.setEnabled(false);
				lblOfficeBranch.setPreferredSize(new java.awt.Dimension(74, 22));
			}
			{
				lookupOfficeBranch = new _JLookup(null, "Phase", 0, 2);
				pnlA2_a.add(lookupOfficeBranch);
				lookupOfficeBranch.setPreferredSize(new java.awt.Dimension(445, 20));
				lookupOfficeBranch.setEnabled(false);
				lookupOfficeBranch.setLookupSQL(SQL_OFFICE_BRANCH());
				lookupOfficeBranch.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						if (data != null) {

							String name = (String) data[1];
							tagBranch.setTag(name);

							KEYBOARD_MANAGER.focusNextComponent();
						}
					}
				});
			}

			pnlA2_b = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlA2.add(pnlA2_b, BorderLayout.CENTER);
			pnlA2_b.setPreferredSize(new java.awt.Dimension(452, 30));
			pnlA2_b.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
			{
				tagBranch = new _JTagLabel("[ ]");
				pnlA2_b.add(tagBranch);
				tagBranch.setBounds(209, 27, 700, 22);
				tagBranch.setEnabled(false);
				tagBranch.setPreferredSize(new java.awt.Dimension(27, 33));
			}

			pnlB = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlB, BorderLayout.CENTER);
			pnlB.setPreferredSize(new java.awt.Dimension(1020, 171));
			pnlB.setBorder(JTBorderFactory.createTitleBorder("Deposit Details"));

			pnlB1 = new JPanel(new BorderLayout(5, 5));
			pnlB.add(pnlB1, BorderLayout.WEST);
			pnlB1.setPreferredSize(new java.awt.Dimension(730, 79));

			{
				pnlBank = new JPanel(new BorderLayout(5, 5));
				pnlB1.add(pnlBank, BorderLayout.CENTER);
				pnlBank.setPreferredSize(new java.awt.Dimension(730, 127));
				pnlBank.setBorder(JTBorderFactory.createTitleBorder("Bank Info."));

				pnlBank_1 = new JPanel(new BorderLayout(5, 5));
				pnlBank.add(pnlBank_1, BorderLayout.WEST);
				pnlBank_1.setPreferredSize(new java.awt.Dimension(342, 197));
				pnlBank_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				pnlBank_1a = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlBank_1.add(pnlBank_1a, BorderLayout.WEST);
				pnlBank_1a.setPreferredSize(new java.awt.Dimension(114, 127));
				pnlBank_1a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
				{
					lblDepositNo = new JLabel("Deposit Slip No.", JLabel.TRAILING);
					pnlBank_1a.add(lblDepositNo);
					lblDepositNo.setEnabled(false);
				}
				{
					lblBankAcctNo = new JLabel("Bank Acct. No.", JLabel.TRAILING);
					pnlBank_1a.add(lblBankAcctNo);
					lblBankAcctNo.setEnabled(false);
				}
				{
					lblBankAcctAlias = new JLabel("Bank Acct.", JLabel.TRAILING);
					pnlBank_1a.add(lblBankAcctAlias);
					lblBankAcctAlias.setEnabled(false);
				}
				{
					pnlBank_1b = new JPanel(new GridLayout(4, 1, 5, 5));
					pnlBank_1.add(pnlBank_1b, BorderLayout.CENTER);
					pnlBank_1b.setPreferredSize(new java.awt.Dimension(333, 122));
					pnlBank_1b.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
					{
						lookupDepositNo = new _JLookup(null, "Deposit No.", 0, 2);
						pnlBank_1b.add(lookupDepositNo);
						lookupDepositNo.setEnabled(false);
						lookupDepositNo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									dep_no = (String) data[0];
									jv_no = (String) data[1];
									lookupDepositNo.setValue(dep_no);
									String jv_status = (String) data[9];
									
									setJV(jv_no, lookupCompany.getValue());
									Date dep_date = (Date) data[2];
									Date coll_date = (Date) data[3];
									dteDeposit.setDate(dep_date);
									dteCollection.setDate(coll_date);
									txtTotal.setValue(data[4]);
									txtJVRemark.setText((String) data[7]);
									String status = (String) data[8];

									Object[] branch = getBranch((String) data[0]);
									lookupOfficeBranch.setValue((String) branch[0]);
									tagBranch.setText((String) branch[1]);

									Object[] bank = getBankAcct((String) data[0]);
									lookupBankAcctNo.setValue((String) bank[0]);
									txtBankAccount.setText((String) bank[1]);
									txtBankBranch.setText((String) bank[2]);
									txtBankName.setText((String) bank[3]);

									KEYBOARD_MANAGER.focusNextComponent();

									displayDepositedCheckList(modelSample2Main, rowHeaderSample2Main, modelSample2Total, (String) data[0]);
									displayDepositedCashList(modelSample1Main, rowHeaderSample1Main, modelSample1Total, (String) data[0]);
									displayJV_details(modelSample4Main, rowHeaderSample4Main, modelSample4Total);

									if (status.equals("ACTIVE")) {
										System.out.println("IF");
										enableButtons(false, true, false, true, true, true, true, true);
									} else if (status.equals("ACTIVE")) {
										System.out.println("Else IF");
										enableButtons(false, false, false, true, true, true, true, true);
									} else {
										System.out.println("Else");
										enableButtons(false, false, false, false, true, true, true, false);
									}

									pnlBank.setBorder(JTBorderFactory.createTitleBorder(
											"Bank Info.                                                                           Status : "
													+ status));
									enable_fields(false);
									dteDeposit.setEnabled(false);

								}
							}
						});
					}
				}
				{
					lookupBankAcctNo = new _JLookup(null, "Unit", 2, 2);
					pnlBank_1b.add(lookupBankAcctNo);
					lookupBankAcctNo.setBounds(20, 27, 20, 25);
					lookupBankAcctNo.setReturnColumn(0);
					lookupBankAcctNo.setEnabled(false);
					lookupBankAcctNo.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								bank_acct_id = (String) data[0];
								txtBankAccount.setText((String) data[1]);
								txtBankName.setText((String) data[3]);
								txtBankBranch.setText((String) data[4]);
								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					txtBankAccount = new JXTextField("");
					pnlBank_1b.add(txtBankAccount);
					txtBankAccount.setEnabled(false);
					txtBankAccount.setBounds(120, 25, 300, 22);
					txtBankAccount.setHorizontalAlignment(JTextField.CENTER);
				}

				pnlBank_2 = new JPanel(new BorderLayout(5, 5));
				pnlBank.add(pnlBank_2, BorderLayout.CENTER);
				pnlBank_2.setPreferredSize(new java.awt.Dimension(700, 148));
				pnlBank_2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{

				}

				pnlBank_2a = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlBank_2.add(pnlBank_2a, BorderLayout.WEST);
				pnlBank_2a.setPreferredSize(new java.awt.Dimension(94, 127));
				pnlBank_2a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
				{
					lblDepositDate = new JLabel("Deposit Date", JLabel.TRAILING);
					pnlBank_2a.add(lblDepositDate);
					lblDepositDate.setEnabled(false);
				}
				{
					lblCollectionDate = new JLabel("Collection Date", JLabel.TRAILING);
					pnlBank_2a.add(lblCollectionDate);
					lblCollectionDate.setEnabled(false);
				}
				{
					lblBankAcct = new JLabel("Bank", JLabel.TRAILING);
					pnlBank_2a.add(lblBankAcct);
					lblBankAcct.setEnabled(false);
				}
				{
					lblBankBranch = new JLabel("Bank Branch", JLabel.TRAILING);
					pnlBank_2a.add(lblBankBranch);
					lblBankBranch.setEnabled(false);
				}

				pnlBank_2b = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlBank_2.add(pnlBank_2b, BorderLayout.CENTER);
				pnlBank_2b.setPreferredSize(new java.awt.Dimension(142, 122));
				pnlBank_2b.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

				{
					dteDeposit = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlBank_2b.add(dteDeposit, BorderLayout.CENTER);
					dteDeposit.setDate(null);
					dteDeposit.setEnabled(false);
					dteDeposit.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteDeposit.addDateListener(new DateListener() {
						public void datePerformed(DateEvent event) {
							SwingWorker sw = new SwingWorker() {
								protected Object doInBackground()
										throws FileNotFoundException, IOException, InterruptedException {
									FncGlobal.startProgress(
											"Please don't save until after this progressbar disappears..");
									txtJVRemark.setText("TO RECORD DEPOSIT FOR "
											+ dateFormat.format(dteDeposit.getDate()) + " \n" + "FOR COLLECTION DATE "
											+ dateFormat.format(dteCollection.getDate()));
									FncGlobal.stopProgress();
									return null;
								}
							};
							sw.execute();
						}
					});

				}
				{
					dteCollection = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlBank_2b.add(dteCollection, BorderLayout.CENTER);
					dteCollection.setDate(null);
					dteCollection.setEnabled(false);
					dteCollection.setPreferredSize(new java.awt.Dimension(248, 38));
					dteCollection.addDateListener(new DateListener() {
						public void datePerformed(DateEvent event) {
							SwingWorker sw = new SwingWorker() {
								protected Object doInBackground()
										throws FileNotFoundException, IOException, InterruptedException {
									FncGlobal.startProgress(
											"Please don't save until after this progressbar disappears..");
									txtJVRemark.setText("TO RECORD DEPOSIT FOR "
											+ dateFormat.format(dteDeposit.getDate()) + " \n" + "FOR COLLECTION DATE "
											+ dateFormat.format(dteCollection.getDate()));
									FncGlobal.stopProgress();
									return null;
								}
							};
							sw.execute();
						}
					});
				}
				{
					txtBankName = new JXTextField("");
					pnlBank_2b.add(txtBankName);
					txtBankName.setEnabled(false);
					txtBankName.setHorizontalAlignment(JTextField.CENTER);
					txtBankName.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 10));
					txtBankName.setBounds(120, 25, 300, 22);
				}
				{
					txtBankBranch = new JXTextField("");
					pnlBank_2b.add(txtBankBranch);
					txtBankBranch.setEnabled(false);
					txtBankBranch.setHorizontalAlignment(JTextField.CENTER);
					txtBankBranch.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 10));
					txtBankBranch.setBounds(120, 25, 300, 22);
				}
			}
			{
				pnlCheckDetails = new JPanel(new BorderLayout(5, 5));
				pnlB.add(pnlCheckDetails, BorderLayout.CENTER);
				pnlCheckDetails.setPreferredSize(new java.awt.Dimension(372, 178));
				pnlCheckDetails.setBorder(JTBorderFactory.createTitleBorder("Note"));

				scpJVRemark = new JScrollPane();
				pnlCheckDetails.add(scpJVRemark);
				scpJVRemark.setBounds(82, 7, 309, 61);
				scpJVRemark.setOpaque(true);
				scpJVRemark.setPreferredSize(new java.awt.Dimension(375, 159));

				{
					txtJVRemark = new JTextArea();
					scpJVRemark.add(txtJVRemark);
					scpJVRemark.setViewportView(txtJVRemark);
					txtJVRemark.setBounds(77, 3, 250, 81);
					txtJVRemark.setLineWrap(true);
					txtJVRemark.setPreferredSize(new java.awt.Dimension(366, 133));
					txtJVRemark.setEditable(false);
					txtJVRemark.setEnabled(false);
					txtJVRemark.setText("");
					txtJVRemark.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {
							// Acctg_Fcns.checkiftextarea_isnull_notrequired(txtJVRemark,lblJVRemark,12);
						}
					});
				}
			}

		}

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));

			pblCheckTable = new JPanel();
			pnlTable.add(pblCheckTable, BorderLayout.CENTER);
			pblCheckTable.setLayout(new BorderLayout(5, 5));
			pblCheckTable.setPreferredSize(new java.awt.Dimension(1186, 404));
			pblCheckTable.setBorder(JTBorderFactory.createTitleBorder("Checks/Cash Details"));

			tabCenter = new _JTabbedPane();
			pblCheckTable.add(tabCenter, BorderLayout.CENTER);
			tabCenter.setPreferredSize(new java.awt.Dimension(1192, 433));
			{
				JPanel pnlSample2 = new JPanel(new BorderLayout());
				tabCenter.addTab("Check Deposit", null, pnlSample2, null);
				pnlSample2.setPreferredSize(new java.awt.Dimension(1183, 365));
				{
					scrollSample2Main = new _JScrollPaneMain();
					pnlSample2.add(scrollSample2Main, BorderLayout.CENTER);
					{
						modelSample2Main = new modelCheckDeposit();

						tblSample2Main = new _JTableMain(modelSample2Main);
						scrollSample2Main.setViewportView(tblSample2Main);
						tblSample2Main.addMouseListener(this);
						tblSample2Main.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblSample2Main.rowAtPoint(e.getPoint()) == -1) {
									tblSample2Total.clearSelection();
								} else {
									tblSample2Main.setCellSelectionEnabled(true);
								}
							}

							public void mouseReleased(MouseEvent e) {
								if (tblSample2Main.rowAtPoint(e.getPoint()) == -1) {
									tblSample2Total.clearSelection();
								} else {
									tblSample2Main.setCellSelectionEnabled(true);
								}
							}
						});

						tblSample2Main.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent arg0) {

								totalCheckEntries(modelSample2Main, modelSample2Total);
								totalForDeposit(modelSample1Total, modelSample2Total);
							}
						});

					}
					{
						rowHeaderSample2Main = tblSample2Main.getRowHeader22();
						scrollSample2Main.setRowHeaderView(rowHeaderSample2Main);
						scrollSample2Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
								FncTables.getRowHeader_Header());
					}
				}
				{
					scrollSample2Total = new _JScrollPaneTotal(scrollSample2Main);
					pnlSample2.add(scrollSample2Total, BorderLayout.SOUTH);
					{
						modelSample2Total = new modelCheckDeposit();
						modelSample2Total.addRow(
								new Object[] { null, "Total", null, null, null, null, new BigDecimal(0.00), null });

						tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
						scrollSample2Total.setViewportView(tblSample2Total);
						tblSample2Total.setFont(dialog11Bold);
						((_JTableTotal) tblSample2Total).setTotalLabel(1);
					}
				}
			}

			// Cash Deposit
			{
				JPanel pnlSample1 = new JPanel(new BorderLayout());
				tabCenter.addTab("Cash Deposit", null, pnlSample1, null);
				pnlSample1.setPreferredSize(new java.awt.Dimension(1183, 365));
				{
					scrollSample1Main = new _JScrollPaneMain();
					pnlSample1.add(scrollSample1Main, BorderLayout.CENTER);
					{
						modelSample1Main = new modelCashDeposit();

						tblSample1Main = new _JTableMain(modelSample1Main);
						scrollSample1Main.setViewportView(tblSample1Main);
						tblSample1Main.addMouseListener(this);
						tblSample1Main.setSortable(false);

						tblSample1Main.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblSample1Main.rowAtPoint(e.getPoint()) == -1) {
									tblSample1Total.clearSelection();
								} else {
									tblSample1Main.setCellSelectionEnabled(true);
								}
							}

							public void mouseReleased(MouseEvent e) {
								if (tblSample1Main.rowAtPoint(e.getPoint()) == -1) {
									tblSample1Total.clearSelection();
								} else {
									tblSample1Main.setCellSelectionEnabled(true);
								}
							}
						});

						tblSample1Main.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent arg0) {
								_JTableMain table = (_JTableMain) arg0.getSource();

								Object oldValue = null;
								try {
									oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
								} catch (NullPointerException e) {
								}

								if (oldValue != null) {

									int row = table.getEditingRow();

									int denominationColumn = table.convertColumnIndexToModel(0);
									int amountColumn = table.convertColumnIndexToModel(2);

									BigDecimal denomination = (BigDecimal) table.getValueAt(row, denominationColumn);

									modelSample1Main.setValueAt(
											denomination.multiply(new BigDecimal((Integer) oldValue)), row,
											amountColumn);
									totalEntries1(modelSample1Main, modelSample1Total);
									totalForDeposit(modelSample1Total, modelSample2Total);
								}
							}
						});

					}
					{
						rowHeaderSample1Main = tblSample1Main.getRowHeader22();
						scrollSample1Main.setRowHeaderView(rowHeaderSample1Main);
						scrollSample1Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
								FncTables.getRowHeader_Header());
					}
				}
				{
					scrollSample1Total = new _JScrollPaneTotal(scrollSample1Main);
					pnlSample1.add(scrollSample1Total, BorderLayout.SOUTH);
					{
						modelSample1Total = new modelCashDeposit();
						modelSample1Total.addRow(new Object[] { null, "Total", new BigDecimal(0.00) });

						tblSample1Total = new _JTableTotal(modelSample1Total, tblSample1Main);
						scrollSample1Total.setViewportView(tblSample1Total);
						tblSample1Total.setFont(dialog11Bold);
						((_JTableTotal) tblSample1Total).setTotalLabel(1);
					}
				}
			}

			{
				pnlSample4 = new JPanel(new BorderLayout(5, 5));
				tabCenter.addTab("   JV Entries    ", null, pnlSample4, null);
				pnlSample4.setPreferredSize(new Dimension(1183, 365));
				pnlSample4.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					{
						JPanel panJV = new JPanel(new GridLayout(4, 1, 5, 5));
						pnlSample4.add(panJV, BorderLayout.LINE_END);
						panJV.setPreferredSize(new Dimension(300, 0));
						panJV.setBorder(JTBorderFactory.createTitleBorder("JV Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JPanel panLine1 = new JPanel(new BorderLayout(5, 5)); 
								panJV.add(panLine1); 
								{
									{
										JLabel label = new JLabel("#");
										panLine1.add(label, BorderLayout.LINE_START);
										label.setHorizontalAlignment(JLabel.TRAILING);
										label.setPreferredSize(new Dimension(75, 0));
									}
									{
										txtJV = new JTextField(""); 
										panLine1.add(txtJV, BorderLayout.CENTER);
										txtJV.setHorizontalAlignment(JLabel.CENTER);
										txtJV.setEditable(false);
										txtJV.addMouseListener(new PopupTriggerListener_panel2());
									}
								}
							}
							{
								JPanel panLine2 = new JPanel(new BorderLayout(5, 5)); 
								panJV.add(panLine2); 
								{
									{
										JLabel label = new JLabel("Status");
										panLine2.add(label, BorderLayout.LINE_START);
										label.setHorizontalAlignment(JLabel.TRAILING);
										label.setPreferredSize(new Dimension(75, 0));
									}
									{
										txtJVStatus = new JTextField(""); 
										panLine2.add(txtJVStatus, BorderLayout.CENTER);
										txtJVStatus.setHorizontalAlignment(JLabel.CENTER);
										txtJVStatus.setEditable(false);
									}
								}
							}
							{
								JPanel panLine3 = new JPanel(new BorderLayout(5, 5)); 
								panJV.add(panLine3); 
								{
									{
										JLabel label = new JLabel("Date");
										panLine3.add(label, BorderLayout.LINE_START);
										label.setHorizontalAlignment(JLabel.TRAILING);
										label.setPreferredSize(new Dimension(75, 0));
									}
									{
										dteJV = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panLine3.add(dteJV);
										dteJV.getCalendarButton().setVisible(false);
										dteJV.setDate(null);
									}
								}
							}
							{
								panJV.add(new JLabel("")); 
							}
						}

					}
					{
						JPanel panTable = new JPanel(new BorderLayout(5, 5)); 
						pnlSample4.add(panTable, BorderLayout.CENTER);
						{
							{
								scrollSample4Main = new _JScrollPaneMain();
								panTable.add(scrollSample4Main, BorderLayout.CENTER);
								{
									modelSample4Main = new modelDepositAccountEntries();
									tblSample4Main = new _JTableMain(modelSample4Main);
									scrollSample4Main.setViewportView(tblSample4Main);
									tblSample4Main.addMouseListener(this);
									tblSample4Main.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if (tblSample4Main.rowAtPoint(e.getPoint()) == -1) {
												tblSample4Total.clearSelection();
											} else {
												tblSample4Main.setCellSelectionEnabled(true);
											}
										}

										public void mouseReleased(MouseEvent e) {
											if (tblSample4Main.rowAtPoint(e.getPoint()) == -1) {
												tblSample4Total.clearSelection();
											} else {
												tblSample4Main.setCellSelectionEnabled(true);
											}
										}
									});
									
									tblSample4Main.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
									tblSample4Main.getColumnModel().getColumn(0).setMaxWidth(150);
									tblSample4Main.getColumnModel().getColumn(1).setMaxWidth(150);
									tblSample4Main.getColumnModel().getColumn(2).setMaxWidth(150);
									tblSample4Main.getColumnModel().getColumn(4).setMaxWidth(150);
									tblSample4Main.getColumnModel().getColumn(5).setMaxWidth(150);
									tblSample4Main.hideColumns("Sect", "Div", "Dept");
								}
								{
									rowHeaderSample4Main = tblSample4Main.getRowHeader22();
									scrollSample4Main.setRowHeaderView(rowHeaderSample4Main);
									scrollSample4Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}

							}
							{
								scrollSample4Total = new _JScrollPaneTotal(scrollSample4Main);
								panTable.add(scrollSample4Total, BorderLayout.SOUTH);
								{
									modelSample4Total = new modelDepositAccountEntries();
									modelSample4Total.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00) });
									tblSample4Total = new _JTableTotal(modelSample4Total, tblSample4Main);
									scrollSample4Total.setViewportView(tblSample4Total);
									tblSample4Total.setFont(dialog11Bold);
									tblSample4Total.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
									tblSample4Total.getColumnModel().getColumn(0).setMaxWidth(150);
									tblSample4Total.getColumnModel().getColumn(1).setMaxWidth(150);
									tblSample4Total.getColumnModel().getColumn(2).setMaxWidth(150);
									tblSample4Total.getColumnModel().getColumn(4).setMaxWidth(150);
									tblSample4Total.getColumnModel().getColumn(5).setMaxWidth(150);
									tblSample4Total.hideColumns("Sect", "Div", "Dept");
									((_JTableTotal) tblSample4Total).setTotalLabel(0);
								}
							}
						}
					}
				}
			}

			pblTableTotal = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlTable.add(pblTableTotal, BorderLayout.SOUTH);
			pblTableTotal.setPreferredSize(new java.awt.Dimension(1121, 35));

			{
				lblTotal = new JLabel("TOTAL DEPOSIT :", JLabel.TRAILING);
				pblTableTotal.add(lblTotal);
				lblTotal.setBounds(8, 11, 62, 12);
				lblTotal.setEnabled(false);
				lblTotal.setPreferredSize(new java.awt.Dimension(101, 16));
			}
			{
				txtTotal = new _JXFormattedTextField(JXFormattedTextField.CENTER);
				pblTableTotal.add(txtTotal);
				txtTotal.setValue(0.00);
				txtTotal.setEnabled(false);
				txtTotal.setEditable(false);
				txtTotal.setBounds(120, 25, 300, 22);
				txtTotal.setHorizontalAlignment(JTextField.CENTER);
				txtTotal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				txtTotal.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
			}

		}
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setPreferredSize(new Dimension(55, 30));
			{
				JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 7, 5, 5));
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));
				{
					btnAddNew = new JButton("Add New");
					pnlSouthCenter.add(btnAddNew);
					btnAddNew.setActionCommand("AddNew");
					btnAddNew.setEnabled(false);
					btnAddNew.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenter.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnDelete = new JButton("Delete");
					pnlSouthCenter.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.addActionListener(this);
					btnDelete.setEnabled(false);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenter.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnPost = new JButton("Post");
					pnlSouthCenter.add(btnPost);
					btnPost.setActionCommand("Post");
					btnPost.addActionListener(this);
					btnPost.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenter.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.setEnabled(false);
					btnPreview.addActionListener(this);
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

		FncTables.bindColumnTables(tblSample2Main, tblSample2Total);
		setComponentEnabled(pnlCheckDetails, true);
		initialize_comp();

		{
			panConfirm = new JXPanel(new GridLayout(3, 1, 5, 4));
			panConfirm.setPreferredSize(new Dimension(300, 90));
			{
				{
					JXPanel pan1 = new JXPanel(new GridLayout(1, 2, 5, 5));
					panConfirm.add(pan1, BorderLayout.CENTER);
					{
						{
							lblCollection = new JLabel("Collection Amount:");
							pan1.add(lblCollection, BorderLayout.CENTER);
							lblCollection.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtCollection = new JTextField("0.00");
							pan1.add(txtCollection, BorderLayout.CENTER);
							txtCollection.setHorizontalAlignment(JTextField.TRAILING);
							txtCollection.setEditable(false);
						}
					}
				}
				{
					JXPanel pan2 = new JXPanel(new GridLayout(1, 2, 5, 5));
					panConfirm.add(pan2, BorderLayout.CENTER);
					{
						{
							lblCashCount = new JLabel("Deposit Amount:");
							pan2.add(lblCashCount, BorderLayout.CENTER);
							lblCashCount.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtCashCount = new JTextField("0.00");
							pan2.add(txtCashCount, BorderLayout.CENTER);
							txtCashCount.setHorizontalAlignment(JTextField.TRAILING);
							txtCashCount.setEditable(false);
						}
					}
				}
				{
					JXPanel pan3 = new JXPanel(new GridLayout(1, 2, 5, 5));
					panConfirm.add(pan3, BorderLayout.CENTER);
					{
						{
							lblVariance = new JLabel("Over Deposit:");
							pan3.add(lblVariance, BorderLayout.CENTER);
							lblVariance.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtVariance = new JTextField("0.00");
							pan3.add(txtVariance, BorderLayout.CENTER);
							txtVariance.setHorizontalAlignment(JTextField.TRAILING);
							txtVariance.setEditable(true);
						}
					}
				}
			}
		}
	}

	private void displayCheckList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String co_id, Date checkdate) {

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select * from view_deposit_list('" + co_id + "', '" + checkdate + "')";
		
		/*** Modified by Mann2x; Date Modified: June 29, 2017; DCRF# 160 ***/
		System.out.printf("SQL displayCheckList " + sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries2(modelMain, modelTotal);
			totalCheckEntries(modelSample2Main, modelSample2Total);
		}

		else {
			modelSample2Total = new modelCheckDeposit();
			modelSample2Total
			.addRow(new Object[] { null, "Total", null, null, null, null, new BigDecimal(0.00), null });

			tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
			scrollSample2Total.setViewportView(tblSample2Total);
			tblSample2Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample2Total).setTotalLabel(1);
		}

		tblSample2Main.packAll();
		adjustRowHeight_account(tblSample2Main);
	}

	private void displayCheckList_forEditing(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String co_id, Date checkdate) {
		String sql = "select \n " + "false,  \n " + // tag
				"coalesce(b.bank_name,'') as bank_name, \n " + // bank name
				"coalesce(c.bank_branch_location,'') as bank_name, \n " + // bank branch
				"a.check_no, \n " + // check no
				"case when a.pay_rec_id in ( select pay_rec_id::int from rf_check_history \n"
				+ "	where prev_checkstat_id = '04' ) then 'T' else 'F' end,  \n" + // PDC
				"to_char(a.check_date,'MM/dd/yy')as check_date , \n " + // check date
				"a.amount, \n " + // amount
				"trim(f.entity_name)as entity_name, \n " + // client name
				"i.checkstat_desc, \n" + // current status
				"h.status_desc,  \n " + // ledger status id
				"a.pay_rec_id  \n\n " + // rec_id

				"from \n" + "(select * from rf_payments where pymnt_type = 'B' \n"
				+ "and check_date::date <= get_next_bank_day('" + checkdate + "'::timestamp)::date "
				+ "and receipt_id is null \n" + // --08 10 2016 - I removed this condition after I adjusted the Credit
				// of Payment process;
				// once a check payment is lifted in OP; the resulting receipt shall bear a cash
				// payment with
				// a note that it is Credited From Receipt No. #; with an amount of (see
				// receipt)
				"and status_id != 'I' " + "and check_no != '3132051066'\n" + // 08 10 2016 - a special condition; just
				// to screen out the check from Candari,
				// Jacquelyn
				"/*and (check_no, pay_rec_id) not in (select check_no, pay_rec_id from cs_dp_chk_detail where status_id not in ('I', 'X'))*/) a \n "
				+

				"left join mf_bank b on a.bank_id = b.bank_id \n "
				+ "left join mf_bank_branch c \n on a.bank_branch_id = c.bank_branch_id \n "
				+ "left join rf_entity f on a.entity_id = f.entity_id \n "
				+ "left join (select distinct on (a.pbl_id, a.seq_no) * from (select * \n"
				+ "	from rf_buyer_status where status_id ='A' order by tran_date desc) a ) g \n"
				+ "	on a.pbl_id = g.pbl_id and a.seq_no = g.seq_no and a.entity_id =g.entity_id \n"
				+ "left join mf_buyer_status h on g.byrstatus_id = h.byrstatus_id \n\n"
				+ "left join mf_check_status i on a.check_stat_id = i.checkstat_id \n\n" +

				"where a.co_id = '" + co_id + "' \n" + "and a.check_stat_id in ('04','05')  \n" + // 09-17-2016 : Del G.
				// - I changed the
				// condition from
				// '05' to '' after
				// I changed the
				// process of not
				// inserting check
				// status upon
				// payment of Dated
				// Check
				// 09-20-2016 : Del G. - I brough back the '05' status as per observation of
				// M'Ela that DC should have 'For Deposit' status upon payment

				"order by f.entity_name, a.check_date 	\n ";

		System.out.println("");
		System.out.println(sql);

		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries2(modelMain, modelTotal);
		} else {
			modelSample2Total = new modelCheckDeposit();
			modelSample2Total
			.addRow(new Object[] { null, "Total", null, null, null, null, new BigDecimal(0.00), null });

			tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
			scrollSample2Total.setViewportView(tblSample2Total);
			tblSample2Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample2Total).setTotalLabel(1);
		}

		tblSample2Main.packAll();
		adjustRowHeight_account(tblSample2Main);
	}

	private void displayDepositedCheckList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String dep_no) {
		FncTables.clearTable(modelMain); 
		listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 
		
		String strSQL = "select * from view_deposit_check_list where dep_no = '"+lookupDepositNo.getText().trim()+"'; ";
		System.out.printf("SQL displayDepositedCheckList: "+strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries2(modelMain, modelTotal);
		} else {
			modelSample2Total = new modelCheckDeposit();
			modelSample2Total.addRow(new Object[] 
					{null, "Total", null, null, null, null, new BigDecimal(0.00), null }
			);

			tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
			scrollSample2Total.setViewportView(tblSample2Total);
			tblSample2Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample2Total).setTotalLabel(1);
		}

		tblSample2Main.packAll();
		adjustRowHeight_account(tblSample2Main);

	}

	private void displayDepositedCashList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String dep_no) {

		modelTotal.addRow(new Object[] { null, "Total", 0.00 });
		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select \n "+ 
				"a.value_id, a.dep_qty, a.value_id*a.dep_qty \n" + 
				"from (select * from cs_dp_csh_detail where dep_no = '" + dep_no + "' and status_id = 'A') a";

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries1(modelMain, modelTotal);
		} else {
			modelSample1Total = new modelCashDeposit();
			modelSample1Total.addRow(new Object[] { null, "Total", 0.00 });
			tblSample1Total = new _JTableTotal(modelSample1Total, tblSample1Main);
			scrollSample1Total.setViewportView(tblSample1Total);
			tblSample1Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample1Total).setTotalLabel(1);
		}

		tblSample1Main.packAll();
		adjustRowHeight_account(tblSample1Main);

	}

	private void createCashList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select 1000, 0, 0.00  union all  \r\n" + "select 500, 0, 0.00  union all  \r\n"
				+ "select 200, 0, 0.00  union all  \r\n" + "select 100, 0, 0.00  union all  \r\n"
				+ "select 50, 0, 0.00  union all  \r\n" + "select 20, 0, 0.00  union all  \r\n"
				+ "select 10, 0, 0.00  union all  \r\n" + "select 5, 0, 0.00  union all  \r\n"
				+ "select 1, 0, 0.00  union all  \r\n" + "select 0.50, 0, 0.00  union all  \r\n"
				+ "select 0.25, 0, 0.00  union all  \r\n" + "select 0.10, 0, 0.00  union all  \r\n"
				+ "select 0.01, 0, 0.00   \r\n" +

				" ";

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalEntries1(modelMain, modelTotal);
		}

		modelSample1Main.setEditable(true);
		tblSample1Main.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblSample1Main.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblSample1Main.getColumnModel().getColumn(2).setPreferredWidth(100);

		adjustRowHeight_account(tblSample1Main);
	}

	private void displayJV_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String sql = "select coalesce(a.acct_id,''), coalesce(a.div_id,''), coalesce(a.dept_id,''), coalesce(a.sect_id,''), \n" + 
				"coalesce(a.project_id,''), coalesce(a.sub_projectid,''), coalesce(b.acct_name,''), \n" + 
				"(case when a.bal_side = 'D' then a.tran_amt else 0.00 end) as debit, \n" + 
				"(case when a.bal_side = 'C' then a.tran_amt else 0.00 end) as credit, \n" + 
				"a.ref_no\n" + 
				"from rf_jv_detail a\n" + 
				"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\n" + 
				"inner join cs_dp_header c on a.jv_no = c.jv_no and a.co_id = c.co_id\n" + 
				"where c.dep_no = '"+lookupDepositNo.getValue()+"' and c.co_id = '"+lookupCompany.getValue()+"' and a.status_id = 'A'\n" + 
				"order by a.bal_side desc, a.entry_no, a.line_no";
		
		System.out.println("select coalesce(a.acct_id,''), coalesce(a.div_id,''), coalesce(a.dept_id,''), coalesce(a.sect_id,''), \n" + 
				"coalesce(a.project_id,''), coalesce(a.sub_projectid,''), coalesce(b.acct_name,''), \n" + 
				"(case when a.bal_side = 'D' then a.tran_amt else 0.00 end) as debit, \n" + 
				"(case when a.bal_side = 'C' then a.tran_amt else 0.00 end) as credit, \n" + 
				"a.ref_no\n" + 
				"from rf_jv_detail a\n" + 
				"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\n" + 
				"inner join cs_dp_header c on a.jv_no = c.jv_no and a.co_id = c.co_id\n" + 
				"where c.dep_no = '"+lookupDepositNo.getValue()+"' and c.co_id = '"+lookupCompany.getValue()+"' and a.status_id = 'A'\n" + 
				"order by a.bal_side desc, a.entry_no, a.line_no");
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalJV(modelMain, modelTotal);
		} else {
			modelSample4Total = new modelDepositAccountEntries();
			modelSample4Total.addRow(new Object[] { "Total", null, null, null, null, null, 0.00, null });

			tblSample4Total = new _JTableTotal(modelSample4Total, tblSample4Main);
			scrollSample4Total.setViewportView(tblSample4Total);
			tblSample4Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample4Total).setTotalLabel(0);
		}

		tblSample4Main.packAll();
		adjustRowHeight_account(tblSample4Main);

	}

	public void setComponentEnabled(JPanel panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			comp.setEnabled(enable);
		}
	}

	public void enable_fields(Boolean x) {

		/* lblDepositDate.setEnabled(x); */
		dteDeposit.setEnabled(x);
		((JTextFieldDateEditor) dteDeposit.getDateEditor()).setEditable(false);
		lblBankAcctNo.setEnabled(x);
		lblBankAcctAlias.setEnabled(x);
		lookupBankAcctNo.setEnabled(x);
		lblBankAcct.setEnabled(x);
		txtBankName.setEnabled(x);
		lblBankAcctAlias.setEnabled(x);
		txtBankAccount.setEnabled(x);
		lblBankBranch.setEnabled(x);
		txtBankBranch.setEnabled(x);
		txtJVRemark.setEnabled(x);
		txtTotal.setEnabled(x);
		lblTotal.setEnabled(x);
		txtJVRemark.setEnabled(x);
		txtJVRemark.setEditable(x);
	}

	public void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f, Boolean g, Boolean h) {
		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnPost.setEnabled(d);
		btnPreview.setEnabled(e);
		btnRefresh.setEnabled(f);
		btnCancel.setEnabled(g);
		btnDelete.setEnabled(h);
	}

	public void refreshTables() {

		// reset table 1
		FncTables.clearTable(modelSample1Main);
		FncTables.clearTable(modelSample1Total);
		rowHeaderSample1Main = tblSample1Main.getRowHeader22();
		scrollSample1Main.setRowHeaderView(rowHeaderSample1Main);
		modelSample1Total.addRow(new Object[] { null, "Total", new BigDecimal(0.00) });

		// reset table 2
		FncTables.clearTable(modelSample2Main);
		FncTables.clearTable(modelSample2Total);
		rowHeaderSample2Main = tblSample2Main.getRowHeader22();
		scrollSample2Main.setRowHeaderView(rowHeaderSample2Main);
		modelSample2Total.addRow(new Object[] { null, "Total", null, null, null, null, new BigDecimal(0.00), null });

		// reset table 4
		FncTables.clearTable(modelSample4Main);
		FncTables.clearTable(modelSample4Total);
		rowHeaderSample4Main = tblSample4Main.getRowHeader22();
		scrollSample4Main.setRowHeaderView(rowHeaderSample4Main);
		modelSample4Total.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00),
				new BigDecimal(0.00) });

	}

	public void refreshFields() {

		lookupDepositNo.setValue("");
		lookupOfficeBranch.setValue("");
		tagBranch.setTag("");
		lookupBankAcctNo.setText("");
		txtBankAccount.setText("");
		txtBankAccount.setText("");
		txtBankName.setText("");
		txtBankBranch.setText("");
		txtJVRemark.setText("");
		dteDeposit.setDate(null);
		pnlBank.setBorder(JTBorderFactory.createTitleBorder("Bank Info."));
		txtTotal.setValue(0.00);
		dep_no = "";
		bank_acct_id = "";
		jv_no = "";
		period = "";
		year_full = 2000;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("AddNew") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == true) {
			addnew();
		} else if (e.getActionCommand().equals("AddNew")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Sorry, you are not authorized to add a new deposit. \n"
							+ "Please ask for an access from your department head.",
							"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Refresh")) {
			refresh();
		}

		if (e.getActionCommand().equals("Cancel")) {
			cancel();
		}

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == true) {
			edit();
		} else if (e.getActionCommand().equals("Edit") && to_do.equals("edit")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Sorry, you are not authorized to edit deposit. \n"
							+ "Please ask for an access from your department head.",
							"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Save") && to_do.equals("addnew") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11")) {
			/* Modified by Mann2x; Date Modified: July 31, 2017; DCRF# 199; */
			Boolean blnReDep = false;
			ValidateList();

			for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
				if ((Boolean) modelSample2Main.getValueAt(x, 0)) {
					if (FncGlobal.GetBoolean("select get_if_bounced("
							+ modelSample2Main.getValueAt(x, 12).toString().trim() + "::int)")) {
						blnReDep = true;
					} else {
						blnReDep = false;
					}
				}
			}

			if (blnReDep) {
				System.out.println("");
				System.out.println("save_redep");
				save_redep();
			} else {
				System.out.println("");
				System.out.println("save");

				variance = 0.00;

				String strPhase = "";
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				String strColDate = dateFormat.format(dteCollection.getDate());

				if (txtBankAccount.getText().equals("001-01-009841-6")) {
					strPhase = "1";
				} else if (txtBankAccount.getText().equals("001-01-009940-7")) {
					strPhase = "2";
				} else if (txtBankAccount.getText().equals("001-01-009959-5")) {
					strPhase = "4A";
				} else {
					strPhase = "";
				}

				if (variance > 0) {
					if (JOptionPane.showConfirmDialog(getContentPane(), panConfirm,
							"Collection for Phase " + strPhase + " (" + strColDate + ")", JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
						variance = new Double(txtVariance.getText());
						save();
					} else {
						variance = 0.00;
					}
				} else {
					save();
				}
			}
		} else if (e.getActionCommand().equals("Save") && to_do.equals("addnew")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Sorry, you are not authorized to add a new deposit. \n"
							+ "Please ask for an access from your department head.",
							"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Save") && to_do.equals("edit")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == true) {
			update();
		} else if (e.getActionCommand().equals("Save") && to_do.equals("edit")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Sorry, you are not authorized to edit deposit. \n"
							+ "Please ask for an access from your department head.",
							"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Post") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "11") == true) {
			post();
		} else if (e.getActionCommand().equals("Post")
				&& FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Sorry, you are not authorized to post deposit. \n"
							+ "Please ask for an access from your department head.",
							"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11") == true) {
			preview();
		} else if (e.getActionCommand().equals("Preview")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Sorry, you are not authorized to preview deposit. \n"
							+ "Please ask for an access from your department head.",
							"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("Delete") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11") == true) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this deposit?",
					"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Delete();
			}
		} else if (e.getActionCommand().equals("Delete")
				&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11") == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Sorry, you are not authorized to delete deposit. \n"
							+ "Please ask for an access from your department head.",
							"Information", JOptionPane.INFORMATION_MESSAGE);
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

	private void addnew() {
		refreshTables();

		Calendar cal = Calendar.getInstance();
		cal.setTime(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		java.util.Date date = cal.getTime();

		lblDepositNo.setEnabled(false);
		lookupDepositNo.setEnabled(false);
		lookupDepositNo.setText("");
		lookupDepositNo.setValue("");

		lblDepositDate.setEnabled(true);
		dteDeposit.setDate(date);
		dteDeposit.setEnabled(true);

		lblCollectionDate.setEnabled(true);
		cal.add(Calendar.DATE, -1);
		java.util.Date date2 = cal.getTime();
		dteCollection.setDate(date2);
		dteCollection.setEnabled(true);

		lblBankAcctNo.setEnabled(true);
		lookupBankAcctNo.setEnabled(true);
		lookupBankAcctNo.setText("");
		lookupBankAcctNo.setValue(null);

		lblBankAcct.setEnabled(true);
		txtBankName.setEnabled(true);
		txtBankName.setText("");

		lblBankAcctAlias.setEnabled(true);
		txtBankAccount.setEnabled(true);
		txtBankAccount.setText("");

		lblBankBranch.setEnabled(true);
		txtBankBranch.setEnabled(true);
		txtBankBranch.setText("");

		txtJVRemark.setEditable(true);
		txtJVRemark.setEnabled(true);

		/*
		 * Date date_now = dteDeposit.getDate(); Date day = addDays(date_now,-1);
		 */

		txtJVRemark.setText("TO RECORD DEPOSIT FOR " + dateFormat.format(dteDeposit.getDate()) + " \n"
				+ "FOR COLLECTION DATE " + dateFormat.format(dteCollection.getDate()));

		lblTotal.setEnabled(true);
		txtTotal.setValue(0.00);
		txtTotal.setEnabled(true);

		enableButtons(false, false, true, false, false, false, true, false);

		displayCheckList(modelSample2Main, rowHeaderSample2Main, modelSample2Total, lookupCompany.getText(),
				dteDeposit.getDate());
		createCashList(modelSample1Main, rowHeaderSample1Main, modelSample1Total);
		pnlBank.setBorder(JTBorderFactory.createTitleBorder("Bank Info."));

		modelSample2Main.setEditable(true);
		tblSample2Main.setEditable(true);
		modelSample1Main.setEditable(true);
		tblSample1Main.setEditable(true);

		dep_no = "";
		jv_no = "";
		bank_acct_id = "";
		jv_no = "";
		period = "";
		to_do = "addnew";

		setJV(jv_no, lookupCompany.getValue());
		verifyCollectionDate();
	}

	private void refresh() {

		if (lookupDepositNo.getText() == "" || lookupDepositNo.getText().equals("")) {
			displayCheckList(modelSample2Main, rowHeaderSample2Main, modelSample2Total, lookupCompany.getText(), dteDeposit.getDate());
		} else {
			displayDepositedCheckList(modelSample2Main, rowHeaderSample2Main, modelSample2Total, lookupDepositNo.getText());
			displayDepositedCashList(modelSample1Main, rowHeaderSample1Main, modelSample1Total, lookupDepositNo.getText());
			displayJV_details(modelSample4Main, rowHeaderSample4Main, modelSample4Total);
			
			setJV(FncGlobal.GetString("select jv_no from cs_dp_header where dep_no = '"+lookupDepositNo.getValue()+"' and co_id = '"+lookupCompany.getValue()+"';"), lookupCompany.getValue());
		}

		JOptionPane.showMessageDialog(null, "Data refreshed.", "", JOptionPane.INFORMATION_MESSAGE);

	}

	private void cancel() {

		if (!btnSave.isEnabled()) {
			execute_cancel();
		} else {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}
		}
	}

	private void execute_cancel() {

		lblDepositNo.setEnabled(true);
		lookupDepositNo.setEnabled(true);
		lookupDepositNo.setValue(null);

		lblDepositDate.setEnabled(false);
		dteDeposit.setDate(null);
		dteDeposit.setEnabled(false);

		lblCollectionDate.setEnabled(false);
		dteCollection.setDate(null);
		dteCollection.setEnabled(false);

		lblBankAcctNo.setEnabled(false);
		lookupBankAcctNo.setEnabled(false);
		lookupBankAcctNo.setValue(null);

		lblBankAcct.setEnabled(false);
		txtBankName.setEnabled(false);

		lblBankAcctAlias.setEnabled(false);
		txtBankAccount.setEnabled(false);

		lblBankBranch.setEnabled(false);
		txtBankBranch.setEnabled(false);

		txtJVRemark.setEditable(false);
		txtJVRemark.setEnabled(false);

		btnEdit.setEnabled(false);
		btnRefresh.setEnabled(false);
		btnCancel.setEnabled(false);
		btnPost.setEnabled(false);
		btnAddNew.setEnabled(true);
		btnSave.setEnabled(false);

		lblTotal.setEnabled(false);
		txtTotal.setValue(0.00);
		txtTotal.setEnabled(false);

		modelSample2Main.setEditable(false);
		tblSample2Main.setEditable(false);
		modelSample1Main.setEditable(false);
		tblSample1Main.setEditable(false);

		refreshTables();
		refreshFields();
	}

	private void edit() {
		modelSample2Main.setEditable(true);
		tblSample2Main.setEditable(true);
		modelSample1Main.setEditable(true);
		tblSample1Main.setEditable(true);
		lookupDepositNo.setEnabled(false);

		enable_fields(true);

//		displayCheckList_forEditing(modelSample2Main, rowHeaderSample2Main, modelSample2Total, lookupCompany.getText(),
//				dteDeposit.getDate());
		createCashList(modelSample1Main, rowHeaderSample1Main, modelSample1Total);

		loadCashTable_forEditing();
		enableButtons(false, false, true, false, false, true, true, false);
		to_do = "edit";

		lblCollectionDate.setEnabled(true);
		dteCollection.setEnabled(true);
	}

	private void post() {// used

		if (JOptionPane.showConfirmDialog(getContentPane(), "Post Deposit?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();
			String sqlDetail2 = "UPDATE cs_dp_header values set \n" + "status_id = 'P', " + "post_date = now() \n"
					+ "where dep_no = '" + dep_no + "' and co_id = '" + co_id + "'";

			System.out.printf("SQL #2: %s", sqlDetail2);
			db.executeUpdate(sqlDetail2, false);

			// POST INDIVIDUAL CHECKS
			// save check details
			String check_no = "";
			String check_date = "";
			String pay_rec_id = "";
			String prev_chkstatus = "";
			String pdc = "";

			for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
				Boolean isTrue = false;
				if (tblSample2Main.getValueAt(x, 0) instanceof String) {
					isTrue = new Boolean((String) tblSample2Main.getValueAt(x, 0));
				}
				if (tblSample2Main.getValueAt(x, 0) instanceof Boolean) {
					isTrue = (Boolean) tblSample2Main.getValueAt(x, 0);
				}
				if (isTrue) {
					try {
						check_no = tblSample2Main.getValueAt(x, 3).toString().trim();
					} catch (NullPointerException e) {
						check_no = "";
					}

					try {
						check_date = tblSample2Main.getValueAt(x, 5).toString().trim();
					} catch (NullPointerException e) {
						check_date = "";
					}

					try {
						pay_rec_id = tblSample2Main.getValueAt(x, 12).toString().trim();
					} catch (NullPointerException e) {
						pay_rec_id = "";
					}

					try {
						pdc = tblSample2Main.getValueAt(x, 4).toString().trim();
					} catch (NullPointerException e) {
						pdc = "";
					}

					prev_chkstatus = "05";
					/*
					 * Modified by Mann2x; Date Modified: 2017-04-21; The check status must already
					 * be in the rf_check_status table before the ledger application procedure
					 * especially for PDCs because the created date serves as a reference for the
					 * ledger application date; insertCheckStatus_posted(check_date, check_no,
					 * pay_rec_id, dep_no, prev_chkstatus, db );
					 */

					insertCheckStatus_posted(check_date, check_no, pay_rec_id, dep_no, prev_chkstatus);
					/** "db" parameter is omitted **/
					updateCheckStatus(check_no, pay_rec_id, db, "02");

				}
			}

			// db.commit();
			pnlBank.setBorder(JTBorderFactory.createTitleBorder(
					"Bank Info.                                                                           Status : POSTED"));
			enableButtons(true, false, false, false, true, true, true, true);

			modelSample2Main.setEditable(false);
			tblSample2Main.setEditable(false);
			modelSample1Main.setEditable(false);
			tblSample1Main.setEditable(false);

			JOptionPane.showMessageDialog(getContentPane(), "Deposit transaction posted.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			db.commit();

			// applyPaymentToBuyersLedger();
			if (applyPDC_pmts_to_ledger(dep_no) == true) {
				JOptionPane.showMessageDialog(getContentPane(), "PDC payment(s) applied to Buyers Ledger.",
						"Information", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {

		}
	}

	private void preview() {

		String criteria = "Deposit Slip";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("branch_id", lookupOfficeBranch.getValue());
		mapParameters.put("co_id", lookupCompany.getText());
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_name", UserInfo.FullName);
		mapParameters.put("deposit_no", lookupDepositNo.getText());
		mapParameters.put("bank_alias", txtBankName.getText());
		mapParameters.put("bank_branch", txtBankBranch.getText());
		mapParameters.put("deposit_date", dteDeposit.getDate());
		mapParameters.put("bank_acct_no", txtBankAccount.getText());

		FncReport.generateReport("/Reports/rptDepositPreview.jasper", reportTitle, company, mapParameters);

	}

	// save and insert
	private boolean validateSaving() {

		if (lookupOfficeBranch.getText().equals("") || txtTotal.getText() == "" || txtTotal.getText() == null) {
			JOptionPane.showMessageDialog(getContentPane(), "Please select Branch", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupBankAcctNo.getValue() == null) {
			JOptionPane.showMessageDialog(getContentPane(), "Please select Bank Account", "Save",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtTotal.getText().equals("0.00") || txtTotal.getText() == "0.00") {
			JOptionPane.showMessageDialog(getContentPane(), "No Data to Save", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	private void insertDepositHeader(String strRemarks) {
		/* Modified Date; Date Modified: July 26, 2017; DCRF# 199; */

		String strSQL = "insert into cs_dp_header (co_id, dep_no, dep_date, cash_date, bank_acct_id, branch_id, remarks, status_id, created_by, date_created) \n" +
				"values ('"+lookupCompany.getValue()+"', '"+sql_getNextDepNo()+"', '"+dteDeposit.getDate()+"'::timestamp, '"+dteCollection.getDate()+"'::timestamp, '"+lookupBankAcctNo.getValue()+"', '"+lookupOfficeBranch.getText()+"', '"+strRemarks+"', 'A', '"+UserInfo.EmployeeCode+"', now())"; 

		System.out.printf("strSQL: "+strSQL);
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(strSQL, false);
		db.commit();
	}

	private void insertDepositHeader() {
		String strSQL = "insert into cs_dp_header (co_id, dep_no, dep_date, cash_date, bank_acct_id, branch_id, remarks, status_id, created_by, date_created) \n" +
				"values ('"+lookupCompany.getValue()+"', '"+sql_getNextDepNo()+"', '"+dteDeposit.getDate()+"'::timestamp, '"+dteCollection.getDate()+"'::timestamp, '"+lookupBankAcctNo.getValue()+"', '"+lookupOfficeBranch.getText()+"', '"+txtJVRemark.getText()+"', 'A', '"+UserInfo.EmployeeCode+"', now())"; 

		System.out.printf("strSQL: "+strSQL);
		
		pgUpdate db = new pgUpdate(); 
		db.executeUpdate(strSQL, false);
		db.commit();
	}

	private void updateDepositHeader(pgUpdate db) {

		String sqlDetail2 = "UPDATE cs_dp_header values set \n" + " 	dep_date = '" + dteDeposit.getDate()
		+ "',								--dep_date	 \n" + " 	cash_date = '" + dteCollection.getDate()
		+ "',								--cash_date	 \n" + // added by Del Gonzales on 06-07-2016 for
		// Deposit Listing Report purposes
		" 	bank_acct_id = '" + lookupBankAcctNo.getText().trim() + "',				--bank_acct	 \n"
		+ " 	branch_id = '" + lookupOfficeBranch.getText() + "',						--branch_id	 \n"
		+ "	remarks = '" + txtJVRemark.getText().trim().replace("'", "''") + "',		--remarks 	 \n"
		+ "	edited_by = '" + UserInfo.EmployeeCode + "',						--edited_by  \n"
		+ "	date_edited = now()													--editd_date \n"
		+ " 	where dep_no = '" + dep_no + "'  and co_id = '" + co_id + "'	\n";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void insertCheckDeposit(int line, Double amt, String bank_id, String bank_branch_id, String check_no,
			String check_date, String pay_rec_id, pgUpdate db) {

		String sqlDetail2 = "INSERT into cs_dp_chk_detail values (" + " 	'" + dep_no
				+ "',				--dep_no		\n" + " 	" + line + ",					--line_no		\n"
				+ " 	" + amt + ",					--tran_amt		\n" + " 	'" + bank_id
				+ "',				--bank_id		\n" + " 	'" + bank_branch_id
				+ "',		--bank_branch_id		\n" + " 	'" + check_no + "',				--check_no		\n"
				+ " 	'" + check_date + "',			--check_date		\n" + " 	'" + pay_rec_id
				+ "',			--pay_rec_id		\n" + " 	'A',						--status_id		\n" + "	'"
				+ UserInfo.EmployeeCode + "',	--created_by    \n"
				+ "	now(),						--created_date		\n"
				+ " 	'',							--edited_by     \n"
				+ " 	null )						--edited_Date	\n";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void deactivateCheckDeposit(int line, Double amt, String bank_id, String bank_branch_id, String check_no,
			String check_date, String pay_rec_id, pgUpdate db) {
		String sqlDetail2 = "UPDATE cs_dp_chk_detail \n" + "SET status_id = 'I' \n" + "WHERE dep_no = '" + dep_no
				+ "' AND pay_rec_id::INT = '" + pay_rec_id + "'::INT AND check_no = '" + check_no
				+ "' AND check_date = '" + check_date + "'";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void updateCheckStatus(String check_no, String rec_id, pgUpdate db, String statID) {

		String sqlDetail = "UPDATE rf_payments set " + "check_stat_id = '" + statID + "'  " + "where trim(check_no) = '"
				+ check_no + "' " + "and pay_rec_id = " + rec_id + "  ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	private void insertCheckStatus(String status_dte, String check_no, String rec_id, String dep_no,
			String prev_checkstat_id, pgUpdate db) {

		String sqlDetail2 = "INSERT into rf_check_history values (" + " 	'" + rec_id
				+ "',				--pay_rec_id		\n" + // pay_rec_id
				"	'" + prev_checkstat_id + "',	--prev_checkstat_id \n" + // prev_checkstat_id
				"	'05',						--new_checkstat_id 	\n" + // new_checkstat_id
				"	'',							--bouncereasonid 	\n" + // bouncereasonid
				"	now(),						--trans_date    	\n" + // trans_date
				"	'',							--dep_no	    	\n" + // dep_no
				"	null,						--inactive_date    	\n" + // inactive_date
				"	'A',						--status_id  		\n" + // status_id
				"	'" + UserInfo.EmployeeCode + "',	--created_by\n" + // created_by
				"	now()						--created_date \n" + // created_date
				")  \n\n";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void deactivateCheckStatusHistory(String status_dte, String check_no, String rec_id, String dep_no,
			String prev_checkstat_id) {
		pgUpdate dbExec = new pgUpdate();
		String sqlDetail2 = "UPDATE rf_check_history \n" + "SET status_id = 'I' \n" + "WHERE pay_rec_id::INT = '"
				+ rec_id + "'::INT AND new_checkstat_id = '05' and status_id = 'A' \n"
				+ "AND trans_date::DATE = (SELECT x.trans_date::DATE FROM rf_check_history x WHERE x.pay_rec_id::INT = '"
				+ rec_id + "'::INT and x.new_checkstat_id = '05' ORDER BY trans_date::DATE DESC LIMIT 1)";

		System.out.println("Check History Entry Deactivation: " + sqlDetail2);
		dbExec.executeUpdate(sqlDetail2, false);

		dbExec.commit();
	}

	private void insertCheckStatus_posted(String status_dte, String check_no, String rec_id, String dep_no,
			String prev_checkstat_id, pgUpdate db) {

		String sqlDetail2 = "INSERT into rf_check_history values (" + "'" + rec_id
				+ "',						--pay_rec_id		\n" + // pay_rec_id
				"'05',								--prev_checkstat_id \n" + // prev_checkstat_id
				"'02',								--new_checkstat_id 	\n" + // new_checkstat_id
				"'',								--bouncereasonid 	\n" + // bouncereasonid
				"now(),								--trans_date    	\n" + // trans_date -- this is the created_date
				"'" + dep_no + "',						--dep_no	    	\n" + // dep_no
				"null,								--inactive_date    	\n" + // inactive_date
				"'A',								--status_id  		\n" + // status_id
				"'" + UserInfo.EmployeeCode + "',	--created_by		\n" + // created_by
				/*
				 * Modified by Mann2x; Date Modified: February 15, 2017; Time part is not
				 * included in the date_created value;
				 */
				 /* "'"+dteDeposit.getDate()+"' \n" + */
				 /*
				  * Modified by Mann2x; Date Modified: April 20, 2017; Check history deposited
				  * date must be the same as the deposited date in the deposits module;
				  */
				  /* "now() \n" + */
				  "('" + dteDeposit.getDate() + "'::date || ' ' || now()::time)::timestamp \n" + ")";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void insertCheckStatus_posted(String status_dte, String check_no, String rec_id, String dep_no,
			String prev_checkstat_id) {
		pgUpdate dbExec = new pgUpdate();

		String sqlDetail2 = "INSERT into rf_check_history values (" + "'" + rec_id
				+ "',						--pay_rec_id		\n" + // pay_rec_id
				"'05',								--prev_checkstat_id \n" + // prev_checkstat_id
				"'02',								--new_checkstat_id 	\n" + // new_checkstat_id
				"'',								--bouncereasonid 	\n" + // bouncereasonid
				"now(),								--trans_date    	\n" + // trans_date -- this is the created_date
				"'" + dep_no + "',						--dep_no	    	\n" + // dep_no
				"null,								--inactive_date    	\n" + // inactive_date
				"'A',								--status_id  		\n" + // status_id
				"'" + UserInfo.EmployeeCode + "',	--created_by		\n" + // created_by
				/*
				 * Modified by Mann2x; Date Modified: February 15, 2017; Time part is not
				 * included in the date_created value;
				 */
				 /* "'"+dteDeposit.getDate()+"' \n" + */
				 /*
				  * Modified by Mann2x; Date Modified: April 20, 2017; Check history deposited
				  * date must be the same as the deposited date in the deposits module;
				  */
				  /* "now() \n" + */
				  "('" + dteDeposit.getDate() + "'::date || ' ' || now()::time)::timestamp \n" + ")";

		System.out.printf("SQL #2: %s", sqlDetail2);
		dbExec.executeUpdate(sqlDetail2, false);
		dbExec.commit();
	}

	private void insertCashDeposit(String denom, String qty, pgUpdate db) {

		String sqlDetail3 = "INSERT into cs_dp_csh_detail values (" + " 	'" + dep_no + "',	--dep_no		\n"
				+ " 	'" + denom + "',	--value_id		\n" + " 	'" + qty + "', 		--dep_qty		\n"
				+ " 	'A'," + "	'" + UserInfo.EmployeeCode + "',	--created_by    \n"
				+ "	now(),			--created_date \n" + " 	''," + " 	null" + "    )			\n";

		System.out.printf("SQL #2: %s", sqlDetail3);
		db.executeUpdate(sqlDetail3, false);
	}

	private void insertJV_header(String jv, pgUpdate db) {// used

		String sqlDetail = "INSERT into rf_jv_header values (" + "'" + co_id + "',  \n" + // 1
				"'" + co_id + "',  \n" + // 2
				"'" + jv_no + "',  \n" + // 3
				"'" + dateFormat.format(dteDeposit.getDate()) + "',  \n" + // 4 jv_date
				"" + year_full + ",  \n" + // 5
				"'" + period + "',  \n" + // 6
				"'00028',  \n" + // 7 00028 - Accounting of Deposits
				"'',  \n" + // 8
				"null,  \n" + // 9
				"'11',  \n" + // 10
				"0,  \n" + // 11
				"false,  \n" + // 12
				"null,  \n" + // 13
				"'" + txtJVRemark.getText().trim().replace("'", "''") + "',  \n" + // 14
				"'A',  \n" + // 15
				"'" + UserInfo.EmployeeCode + "',  \n" + // 16
				"now(),  \n" + // 17 date_created
				"'',  \n" + // 18
				"null  \n" + // 19
				")   ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	private void updateCheckDetails_setInactive(pgUpdate db) {

		String sqlDetail2 = "UPDATE cs_dp_chk_detail values set \n" + " 	status_id = 'I'					\n"
				+ " 	where dep_no = '" + dep_no + "'  	\n";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void updateCashDetails_setInactive(pgUpdate db) {

		String sqlDetail2 = "UPDATE cs_dp_csh_detail values set \n" + " 	status_id = 'I'					\n"
				+ " 	where trim(dep_no) = '" + dep_no + "'  	\n";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void updateJVDetails_setInactive(pgUpdate db) {

		String sqlDetail2 = "UPDATE rf_jv_detail values set \n" + " 	status_id = 'I'					\n"
				+ " 	where jv_no = '" + jv_no + "'  	\n" + "	and co_id = '" + co_id + "'   		\n";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void reverseCheckStatus(String check_no, String rec_id, String prev_check_stat, pgUpdate db) {

		String sqlDetail = "UPDATE rf_payments set " + "check_stat_id = '" + prev_check_stat + "'  "
				+ "where trim(check_no) = '" + check_no + "' " + "and pay_rec_id = " + rec_id + "  " + "and dep_no = '"
				+ lookupDepositNo.getText() + "'  ";

		System.out.printf("SQL #1: %s", sqlDetail);
		System.out.printf("Dumaan dito!!!");

		db.executeUpdate(sqlDetail, false);
	}

	private void reverseCheckHistory(String check_no, String rec_id, String dep_no, String prev_checkstat_id,
			pgUpdate db) {

		String sqlDetail2 = "UPDATE rf_check_history set " + " 	new_checkstat_id =  '" + prev_checkstat_id + "' 	\n"
				+ "	where dep_no = '" + dep_no + "'  " + "	and pay_rec_id::int = " + rec_id + "  " + "	and dep_no = '"
				+ lookupDepositNo.getText() + "'  ";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private Boolean applyPDC_pmts_to_ledger(String depNo) {

		Boolean bln = false;

		/*
		 * Modified by Mann2x; Date Modified: June 7, 2020; ECQ adjustments; String
		 * strSQL =
		 * "select sp_dep_apply_pdc_payments_to_ledger ( '"+depNo+"', '"+co_id+"'," +
		 * "'"+UserInfo.EmployeeCode+"','"+dteDeposit.getDate()+"'  ) ";
		 */

		String strSQL = "select sp_dep_apply_pdc_payments_to_ledger_with_moratorium ( '" + depNo + "', '" + co_id + "',"
				+ "'" + UserInfo.EmployeeCode + "','" + dteDeposit.getDate() + "'  ) ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			bln = (Boolean) db.getResult()[0][0];
		} else {
			bln = true;
		}

		return bln;
	}

	// select statements
	private String sql_getNextDepNo() {

		String SQL = " select trim(to_char(max(dep_no::int) + 1,'00000000')) from cs_dp_header ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {

			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				dep_no = "00000001";
			} else {
				dep_no = (String) db.getResult()[0][0];
			}

		} else {
			dep_no = null;
		}

		return dep_no;
	}

	private Object[] getBankAcct(String dep_no) {

		String strSQL = "select a.bank_acct_id, a.bank_acct_no, b.bank_branch_location, c.bank_name,  \n"
				+ "( select remarks from cs_dp_header where dep_no = '" + dep_no + "'  )   \n"
				+ "from mf_bank_account a \n" + "left join mf_bank_branch b on a.bank_branch_id = b.bank_branch_id   \n"
				+ "left join mf_bank c on b.bank_id = c.bank_id   \n"
				+ "where a.bank_acct_id in ( select bank_acct_id from cs_dp_header where trim(dep_no) = '" + dep_no
				+ "' )  ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private Object[] getBranch(String dep_no) {

		String strSQL = "select a.branch_id, \n" + "b.branch_name \n "
				+ "from (select * from cs_dp_header where trim(dep_no) = '" + dep_no + "' ) a \n"
				+ "left join mf_office_branch b on a.branch_id = b.branch_id   \n";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private Object[] getProjPhase(String pay_rec_id) {

		String strSQL = "select a.proj_id, \n" + "b.sub_proj_id \n "
				+ "from (select distinct on (pay_rec_id) proj_id, pbl_id from rf_payments "
				+ "	where status_id = 'A' and pay_rec_id = '" + pay_rec_id + "') a \n"
				+ "left join mf_unit_info b on a.pbl_id = b.pbl_id   \n";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private String getAcctID(String bank_acct_id) {

		String strSQL = "select acct_id from mf_bank_account where bank_acct_id = '" + bank_acct_id + "' ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	private Integer sql_getCashPiece(String dep_no, Double denom) {

		String SQL = " select dep_qty from cs_dp_csh_detail where dep_no = '" + dep_no + "' and value_id = " + denom
				+ " \n";

		System.out.printf("SQL #1 sql_getCashPiece: " + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {

			if ((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				return 0;
			} else {
				return (Integer) db.getResult()[0][0];
			}

		} else {
			return 0;
		}
	}

	private String getCheckPreviousStatus(String pay_rec_id) {

		String strSQL = "select prev_checkstat_id from rf_check_history " + "where dep_no = '" + dep_no
				+ "' and pay_rec_id::int = " + pay_rec_id + " and status_id = 'A' ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {

			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				return "";
			} else {
				return (String) db.getResult()[0][0];
			}
		} else {
			return "";
		}
	}

	private void initialize_comp() {
		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		lblDepositNo.setEnabled(true);
		lookupDepositNo.setEnabled(true);
		lblOfficeBranch.setEnabled(true);
		lookupOfficeBranch.setEnabled(true);
		tagBranch.setEnabled(true);
		lookupBankAcctNo.setLookupSQL(sql_bankacct(co_id));
		lookupDepositNo.setLookupSQL(sql_deposit_no(co_id));

		enableButtons(true, false, false, false, false, false, false, false);
		refreshTables();
		refreshFields();

		KEYBOARD_MANAGER.focusNextComponent();

		lookupCompany.setValue(co_id);
	}

	private String sql_deposit_no(String co_id) {

		String SQL = "select " + "A.dep_no as \"Deposit No.\", " + // 0
				"A.jv_no as \"JV No\", \r\n" + // 1
				"A.dep_date as \"Dep. Date\", \r\n" + // 2
				"A.cash_date as \"Coll. Date\", \r\n" + // 3
				"coalesce((SELECT SUM(value_id*dep_qty)FROM cs_dp_csh_detail WHERE dep_no = A.dep_no and status_id = 'A' ),0) \n"
				+ "	+ coalesce((SELECT SUM(tran_amt) FROM cs_dp_chk_detail WHERE dep_no = A.dep_no and status_id = 'A') ,0) \n"
				+ "	as \"Amount\", \r\n" + // 4
				"D.bank_alias as \"Bank\", \r\n" + // 5
				"C.bank_branch_location as \"Branch\", \r\n" + // 6
				"A.remarks as \"Remarks\"," + // 7
				"H.status_desc  as \"Dep Status\", \r\n" + // 8
				"G.status_desc  as \"JV Status\" \r\n" + // 9

				"FROM cs_dp_header A \r\n" + "left JOIN mf_bank_account B ON A.bank_acct_id = B.bank_acct_id \r\n"
				+ "left JOIN mf_bank_branch C ON B.bank_branch_id = C.bank_branch_id \r\n"
				+ "left JOIN mf_bank D ON C.bank_id = D.bank_id \r\n"
				+ "left JOIN mf_record_status E ON A.status_id = E.status_id \r\n"
				+ "left JOIN rf_jv_header F ON A.jv_no = F.jv_no and A.co_id = F.co_id \r\n"
				+ "left JOIN mf_record_status G ON f.status_id = G.status_id \r\n"
				+ "left JOIN mf_record_status H ON a.status_id = H.status_id \r\n" +

				"WHERE A.co_id = '" + co_id + "'" + "ORDER by A.dep_no desc";

		System.out.println(SQL); 
		
		return SQL;
	}

	private String sql_bank_id(String bank) {

		String SQL = "SELECT \r\n" + "trim(bank_id) " + "FROM mf_bank WHERE upper(trim(bank_name)) = '" + bank
				+ "' \r\n";

		System.out.printf("SQL #1 sql_bank_id: " + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	private String sql_bank_branchid(String bank_id, String bank_branch_location) {

		String SQL = "SELECT \r\n" + "trim(bank_branch_id) " + "FROM mf_bank_branch WHERE trim(bank_id) = '" + bank_id
				+ "' " + "and upper(trim(bank_branch_location)) = '" + bank_branch_location + "'  \r\n";

		System.out.printf("SQL #1 sql_bank_branchid: " + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	private String sql_bankacct(String co_id) {

		String SQL = "SELECT \r\n" + "b.bank_acct_id,\r\n" + "b.bank_acct_no,\r\n" + "b.acct_desc,\r\n"
				+ "d.bank_name,\r\n" + "c.bank_branch_location\r\n" + "\r\n" + "FROM mf_bank_account B \r\n"
				+ "INNER JOIN mf_bank_branch C ON B.bank_branch_id = C.bank_branch_id \r\n"
				+ "INNER JOIN mf_bank D ON C.bank_id = D.bank_id \n" +
				"WHERE case when b.bank_acct_id = '00120' THEN TRUE ELSE B.co_id = '"+co_id+"' END \n"+
				"AND B.status_id = 'A' \n"
				+"order by b.bank_acct_id \n";
				

		return SQL;
	}

	private void totalJV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// used

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
	}

	private void totalEntries1(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_check_amt = new BigDecimal(0.00);
		FncTables.clearTable(modelTotal);
		for (int x = 0; x < modelMain.getRowCount(); x++) {
			try {
				total_check_amt = total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e) {
				total_check_amt = total_check_amt.add(new BigDecimal(0.00));
			}
		}
		modelTotal.addRow(new Object[] { null, "Total", total_check_amt });
	}

	private void totalEntries2(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_check_amt = new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);// Code to clear modelMain.
		for (int x = 0; x < modelMain.getRowCount(); x++) {
			/*
			 * Modified by Mann2x; Date Modified: Apr. 7, 2017; The total values upon
			 * pressing edit inlcudes even the rows without tag. This may cause confusion;
			 */
			if ((Boolean) modelMain.getValueAt(x, 0)) {
				try {
					System.out.println("");
					System.out.println(
							"(BigDecimal) modelMain.getValueAt(x, 6): " + (BigDecimal) modelMain.getValueAt(x, 6));

					total_check_amt = total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 6)));
					System.out.printf("total_check_amt: %s%n", total_check_amt);
				} catch (NullPointerException e) {
					total_check_amt = total_check_amt.add(new BigDecimal(0.00));
					modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, 0.00, null });
				}
			}
		}
		modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, total_check_amt, null });
	}

	private void totalEntries3(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_check_amt = new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);// Code to clear modelMain.
		for (int x = 0; x < modelMain.getRowCount(); x++) {
			try {
				total_check_amt = total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 6)));
				System.out.printf("total_check_amt: %s%n", total_check_amt);
			} catch (NullPointerException e) {
				total_check_amt = total_check_amt.add(new BigDecimal(0.00));
				modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, 0.00, null });
			}

		}
		modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, total_check_amt, null });
	}

	private void totalForDeposit(DefaultTableModel modelTotal, DefaultTableModel modelTotal2) {

		BigDecimal total_check_amt = new BigDecimal(0.00);
		BigDecimal total_cash_amount = new BigDecimal(0.00);

		total_check_amt = total_check_amt.add(((BigDecimal) modelTotal.getValueAt(0, 2))); // Dont forget to adjust
		// column number
		total_cash_amount = total_cash_amount.add(((BigDecimal) modelTotal2.getValueAt(0, 6))); // Dont forget to adjust
		// column number

		System.out.printf("total_check_amt: %s%n", total_check_amt);
		System.out.printf("total_cash_amount: %s%n", total_cash_amount);

		txtTotal.setValue(total_check_amt.add(total_cash_amount));
	}

	private void totalCheckEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_check_amt = new BigDecimal(0.00);
		int a = 0;
		FncTables.clearTable(modelTotal);// Code to clear modelMain.
		for (int x = 0; x < modelMain.getRowCount(); x++) {
			try {

				Boolean isTrue = false;
				if (modelMain.getValueAt(x, 0) instanceof String) {
					isTrue = new Boolean((String) modelMain.getValueAt(x, 0));
				}
				if (modelMain.getValueAt(x, 0) instanceof Boolean) {
					isTrue = (Boolean) modelMain.getValueAt(x, 0);
				}

				if (isTrue) {
					total_check_amt = total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 6)));// Dont forget to
					// adjust column
					// number
				} else {
				}

				System.out.printf("Check Amount: %s%n", total_check_amt);
			} catch (NullPointerException e) {
				total_check_amt = total_check_amt.add(new BigDecimal(0.00));
				modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, 0.00, null });
			}
			a = a + 1;
		}
		modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, total_check_amt, null });
	}

	private void adjustRowHeight_account(_JTableMain table) {// used
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			table.setRowHeight(x, 22);
			x++;
		}

	}

	private void loadCashTable_forEditing() {

		for (int x = 0; x < modelSample1Main.getRowCount(); x++) {

			Double denom = Double.parseDouble(modelSample1Main.getValueAt(x, 0).toString().trim());
			Integer piece = sql_getCashPiece(dep_no, denom);
			Double amount = denom * piece;
			// Object oldValue = null;

			if (piece == 0) {

			} else {
				/*
				 * int denominationColumn = tblSample1Main.convertColumnIndexToModel(0); int
				 * amountColumn = tblSample1Main.convertColumnIndexToModel(2); BigDecimal
				 * denomination = (BigDecimal) tblSample1Main.getValueAt(x, denominationColumn);
				 */

				modelSample1Main.setValueAt(piece, x, 1);
				modelSample1Main.setValueAt(new BigDecimal(amount), x, 2);
			}
		}

		totalEntries1(modelSample1Main, modelSample1Total);
		totalForDeposit(modelSample1Total, modelSample2Total);
	}

	private Object[] getMonthYear() {

		DateFormat df = new SimpleDateFormat("yyyy-dd-MM");
		String month = df.format(dteDeposit.getDate());
		String month_sub = month.substring(8);

		String month_word[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
		String year = df2.format(dteDeposit.getDate());
		Integer year_sub = Integer.parseInt(year.substring(6)) - 2000;
		Integer year_full = Integer.parseInt(year.substring(6));

		Object x[] = { month_sub, year_sub, year_full, month_word[Integer.parseInt(month_sub) - 1], year.substring(6) };

		return x;

	}

	private void openJV() {

		JournalVoucher jv = new JournalVoucher();
		Home_JSystem.addWindow(jv);

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

			JournalVoucher.enableButtons(true, false, false, false, false, false, false, false, false, false);

			if (jv_no.equals("")) {
			} else {

				JournalVoucher.refresh_fields();

				JournalVoucher.jv_no = jv_no;
				JournalVoucher.lookupJV.setValue(jv_no);

				JournalVoucher.setJV_header(jv_no);
				JournalVoucher.displayJV_details(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account,
						JournalVoucher.modelJV_accounttotal, jv_no);

				JournalVoucher.mnidelete.setEnabled(false);
				JournalVoucher.mniaddrow.setEnabled(false);

			}
		}
	}

	// apply payment to buyer's ledger
	private void applyPaymentToBuyersLedger() {

		for (int x = 0; x < tblSample2Main.getRowCount(); x++) {

			String pay_rec_id = "";
			try {
				pay_rec_id = tblSample2Main.getValueAt(x, 12).toString().trim();
			} catch (NullPointerException e) {
				pay_rec_id = "";
			}

			Object[] payment_dtl = getPmtDetails(pay_rec_id);
			String entity_id = "";
			String proj_id = "";
			String pbl_id = "";
			Integer seq_no = 0;
			String unit_id = "";
			String part_id = "";
			Double amount = 0.00;

			try {
				entity_id = (String) payment_dtl[0];
			} catch (NullPointerException e) {
				entity_id = "";
			}
			try {
				proj_id = (String) payment_dtl[1];
			} catch (NullPointerException e) {
				proj_id = "";
			}
			try {
				pbl_id = (String) payment_dtl[2];
			} catch (NullPointerException e) {
				pbl_id = "";
			}
			try {
				seq_no = (Integer) payment_dtl[3];
			} catch (NullPointerException e) {
				seq_no = 0;
			}
			try {
				unit_id = (String) payment_dtl[4];
			} catch (NullPointerException e) {
				unit_id = "";
			}
			try {
				part_id = (String) payment_dtl[5];
			} catch (NullPointerException e) {
				part_id = "";
			}
			try {
				amount = Double.parseDouble(payment_dtl[6].toString());
			} catch (NullPointerException e) {
				amount = 0.00;
			}

			String SQL = "select sp_apply_to_ledger(" + "'" + entity_id + "', " + "'" + proj_id + "', " + "'" + pbl_id
					+ "', " + "" + seq_no + ", " + "'" + unit_id + "', " + "'" + part_id + "', " + "" + amount + ", "
					+ "'" + pay_rec_id + "', " + "'" + UserInfo.EmployeeCode + "', " + "false)";

			pgSelect db = new pgSelect();
			db.select(SQL);
		}
	}

	private Object[] getPmtDetails(String pay_rec_id) {

		String strSQL = "select \r\n" + "\r\n" + "entity_id,\r\n" + // 0
				"proj_id,\r\n" + // 1
				"pbl_id, \r\n" + // 2
				"seq_no,\r\n" + // 3
				"unit_id, \r\n" + // 4
				"pay_part_id,\r\n" + // 5
				"amount\r\n" + // 6
				"\r\n" + "from rf_payments\r\n" + "\r\n" + "where pay_rec_id = " + pay_rec_id + "  ";

		System.out.printf("SQL #1 getPmtDetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private void deactivateCheckHistory(String check_no, String rec_id, String dep_no, String prev_checkstat_id,
			pgUpdate db) {

		String sqlDetail2 = "UPDATE rf_check_history set " + " 	new_checkstat_id =  '" + prev_checkstat_id + "' 	\n"
				+ "	where dep_no = '" + dep_no + "'  " + "	and pay_rec_id::int = " + rec_id + "  " + "	and dep_no = '"
				+ lookupDepositNo.getText() + "'  ";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	private void Delete() {
		String strCheck = "";
		String strRecID = "";

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
			if ((Boolean) modelSample2Main.getValueAt(x, 0)) {
				try {
					strCheck = tblSample2Main.getValueAt(x, 3).toString().trim();
				} catch (NullPointerException e) {
					strCheck = "";
				}

				try {
					strRecID = tblSample2Main.getValueAt(x, 12).toString().trim();
				} catch (NullPointerException e) {
					strRecID = "";
				}

				reversePaymentCheckStatus(strCheck, strRecID);
				deleteCheckHistory(strRecID);
			}
		}

		deleteJournalVoucher(lookupDepositNo.getValue().toString(), lookupCompany.getValue());
		deleteCheckDeposit(lookupDepositNo.getValue().toString());

		refreshTables();
		refreshFields();
		enableButtons(true, false, false, false, false, false, false, false);
		JOptionPane.showMessageDialog(getContentPane(), "Deposit deleted!");
	}

	private void deleteCheckDeposit(String strDep) {
		pgUpdate dbExec1 = new pgUpdate();
		pgUpdate dbExec2 = new pgUpdate();
		pgUpdate dbExec3 = new pgUpdate();

		String strSQL = "";

		strSQL = "update cs_dp_chk_detail set status_id = 'I' where dep_no = '" + strDep + "' and status_id = 'A'";
		dbExec1.executeUpdate(strSQL, false);
		dbExec1.commit();

		strSQL = "update cs_dp_header set status_id = 'I' where dep_no = '" + strDep + "' and status_id = 'A'";
		dbExec2.executeUpdate(strSQL, false);
		dbExec2.commit();

		strSQL = "update cs_dp_csh_detail set status_id = 'I' where dep_no = '" + strDep + "' and status_id = 'A'";
		dbExec3.executeUpdate(strSQL, false);
		dbExec3.commit();
	}

	private void deleteCheckHistory(String strRecID) {
		Boolean blnPDC = false;
		pgUpdate dbExec = new pgUpdate();

		String strSQL = "";
		String strDate = "";

		if (FncGlobal.GetInteger("select count(*)::int from rf_check_history where pay_rec_id::int = '" + strRecID
				+ "'::int and status_id = 'A' and (prev_checkstat_id = '04' or new_checkstat_id = '04')") > 0) {
			blnPDC = true;
		}

		if (blnPDC) {
			strDate = FncGlobal.GetString("select trans_date::date::varchar(10) \n" + "from rf_check_history \n"
					+ "where pay_rec_id::int = '" + strRecID + "'::int and new_checkstat_id = '05' \n"
					+ "order by trans_date::date desc limit 1");

			strSQL = "update rf_check_history \n" + "set status_id = 'I' \n" + "where pay_rec_id::int = '" + strRecID
					+ "'::int and new_checkstat_id = '05' and trans_date::date = '" + strDate + "'::date;";

			try {
				dbExec.executeUpdate(strSQL, false);
				dbExec.commit();
			} catch (Exception e) {

			}
		}
	}

	private void reversePaymentCheckStatus(String strCheck, String strRecID) {
		pgUpdate dbExec = new pgUpdate();

		String strSQL = "";
		String strPrev = "";
		String strDate = "";

		strDate = FncGlobal.GetString("select trans_date::date::varchar(10) \n" + "from rf_check_history \n"
				+ "where pay_rec_id::int = '" + strRecID + "'::int and new_checkstat_id = '05' \n"
				+ "order by trans_date::date desc limit 1");

		if (!strDate.equals("")) {
			strPrev = FncGlobal.GetString("select new_checkstat_id \n" + "from rf_check_history \n"
					+ "where pay_rec_id::int = '" + strRecID + "'::int \n" + "and trans_date::DATE < '" + strDate
					+ "'::DATE \n" + "order by trans_date::date desc limit 1");
		} else {
			strPrev = "";
		}

		if (!strPrev.equals("")) {
			strSQL = "update rf_payments \n" + "set check_stat_id = '" + strPrev + "' \n" + "where pay_rec_id::int = '"
					+ strRecID + "'::int and check_no = '" + strCheck + "' and status_id = 'A'";
			dbExec.executeUpdate(strSQL, false);
			dbExec.commit();
		}
	}

	private void deleteJournalVoucher(String strDep, String co_id) {
		pgUpdate dbExec1 = new pgUpdate();
		pgUpdate dbExec2 = new pgUpdate();

		String strSQL = "";
		String strJV = FncGlobal
				.GetString("select jv_no from cs_dp_header where dep_no = '" + strDep + "' and status_id = 'A'");
		
		//ADDED STATUS F FOR INACTIVE OF JV NO
		//ADDED FILTER BY LESTER FOR COMPANY 2021-11-05
		strSQL = "update rf_jv_header set status_id = 'I' where jv_no = '" + strJV + "' and status_id IN ('A', 'F') AND co_id = '"+co_id+"'";
		dbExec1.executeUpdate(strSQL, true);
		dbExec1.commit();

		//ADDED FILTER BY LESTER FOR COMPANY 2021-11-05
		strSQL = "update rf_jv_detail set status_id = 'I' where jv_no = '" + strJV + "' and status_id = 'A' and co_id = '"+co_id+"'";
		dbExec2.executeUpdate(strSQL, true);
		dbExec2.commit();
	}

	private void verifyCollectionDate() {
		Integer intCount = 0;
		Date dteDate = null;

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
			if (dteDate != FncGlobal.GetDate("select trans_date::date from rf_payments " + "where pay_rec_id::int = '"
					+ modelSample2Main.getValueAt(x, 12) + "' " + "and check_no = '" + modelSample2Main.getValueAt(x, 3)
					+ "' and status_id = 'A'")) {
				intCount = intCount + 1;
				dteDate = FncGlobal.GetDate("select trans_date::date from rf_payments " + "where pay_rec_id::int = '"
						+ modelSample2Main.getValueAt(x, 12) + "' " + "and check_no = '"
						+ modelSample2Main.getValueAt(x, 3) + "' and status_id = 'A'");
			}
		}

		if (intCount > 1) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Multiple transaction dates for the check entries are found.\nThe collection date will not be automatically set.\nPlease decide on the collection date yourself.",
					"Caution", JOptionPane.INFORMATION_MESSAGE);
			dteDate = cal.getTime();
			dteCollection.setDate(dteDate);
		}

		if (dteDate == null) {
			dteDate = cal.getTime();
			dteCollection.setDate(dteDate);
		} else {
			dteCollection.setDate(dteDate);
		}

		txtJVRemark.setText("TO RECORD DEPOSIT FOR " + dateFormat.format(dteDeposit.getDate()) + " \n"
				+ "FOR COLLECTION DATE " + dateFormat.format(dteCollection.getDate()));

		System.out.println("");
		System.out.println("dteCollection: " + dteCollection.getDate().toString());
	}

	private void ValidateList() {
		/* 8 - Client Name */
		/* 4 - Check Number */

		Boolean blnReg = false;
		Boolean blnRedep = false;

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
			System.out.println("");
			System.out.println("pay_rec_id: " + modelSample2Main.getValueAt(x, 12).toString().trim());

			if ((Boolean) modelSample2Main.getValueAt(x, 0)) {
				if (FncGlobal.GetBoolean(
						"select get_if_bounced(" + modelSample2Main.getValueAt(x, 12).toString().trim() + "::int)")) {
					blnRedep = true;
				} else {
					blnReg = true;
				}
			}
		}

		if (blnReg && blnRedep) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Regular deposits cannot be posted together with redeposits. \n"
							+ "Redeposit's tag will be removed.",
							"Caution", JOptionPane.INFORMATION_MESSAGE);

			for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
				System.out.println("");
				System.out.println("pay_rec_id: " + modelSample2Main.getValueAt(x, 12).toString().trim());

				if ((Boolean) modelSample2Main.getValueAt(x, 0)) {
					if (FncGlobal.GetBoolean("select get_if_bounced("
							+ modelSample2Main.getValueAt(x, 12).toString().trim() + "::int)")) {
						modelSample2Main.setValueAt(false, x, 0);
					}
				}
			}
		}
	}

	private String GetNameUnit() {
		String strRemarks = "To record deposit of bounced check(s) \n";
		String strName = "";
		String strUnit = "";

		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
			System.out.println("");
			System.out.println("pay_rec_id: " + modelSample2Main.getValueAt(x, 12).toString().trim());

			if ((Boolean) modelSample2Main.getValueAt(x, 0)) {
				if (FncGlobal.GetBoolean(
						"select get_if_bounced(" + modelSample2Main.getValueAt(x, 12).toString().trim() + "::int)")) {
					System.out.println("");
					System.out.println("pay_rec_id: " + modelSample2Main.getValueAt(x, 12).toString().trim());

					strName = FncGlobal
							.GetString("select get_client_name(entity_id) from rf_payments where pay_rec_id::int = '"
									+ modelSample2Main.getValueAt(x, 12).toString().trim() + "'::int");
					strUnit = FncGlobal.GetString(
							"select (select y.description from mf_unit_info y where y.proj_id = x.proj_id and y.pbl_id = x.pbl_id) from rf_payments x where x.pay_rec_id::int = '"
									+ modelSample2Main.getValueAt(x, 12).toString().trim() + "'::int");
					strRemarks = strRemarks + "FAO: " + strName + "(" + strUnit + ")\n";
				}
			}
		}

		return strRemarks;
	}

	private void insertJV_detail_redep(String jv, pgUpdate db) {
		int x = 0;
		int y = 1;

		Double total_amt = Double.valueOf(txtTotal.getText().trim().replace(",", "")).doubleValue();
		Double cash_total = Double.parseDouble(modelSample1Total.getValueAt(0, 2).toString().trim());

		String acct_id[] = { getAcctID(lookupBankAcctNo.getText().trim()), "01-01-01-001" };

		String bal_side[] = { "D", "C" };

		Double amount[] = { total_amt, cash_total };

		while (x <= 1) {

			if (amount[x] == 0.00) {

			} else {
				String sqlDetail = "INSERT into rf_jv_detail values (" + "'" + co_id + "', \n" + // 1 co_id
						"'" + co_id + "', \n" + // 2 busunit_id
						"'" + jv_no + "', \n" + // 3 jv_no
						"1, \n" + // 4 entry_no
						"" + y + ", \n" + // 5 line_no
						"'" + acct_id[x] + "', \n" + // 6 acct_id
						"" + amount[x] + ", \n" + // 7 tran_amt
						"'" + bal_side[x] + "', \n" + // 8 bal_side
						"'" + dep_no + "', \n" + // 9 ref_no -- deposit_no ?
						"'', \n" + // 10 project_id
						"'', \n" + // 11 sub_projectid
						"'', \n" + // 12 div_id
						"'', \n" + // 13 dept_id
						"'', \n" + // 14 sect_id
						"null, \n" + // 15 inter_co_id
						"null, \n" + // 16 inter_busunit_id
						"null, \n" + // 17 old_acct_id
						"null, \n" + // 18 entity_id
						"null, \n" + // 19 pbl_id
						"null, \n" + // 20 seq_no
						"'A', " + // 21 status_id
						"'" + UserInfo.EmployeeCode + "',  \n" + // 22 created_by
						"'" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  \n" + // 23
						// date_created
						"'',  \n" + // 24 edited_by
						"null  \n" + // 25 date_edited
						")   ";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);
				y++;
			}
			x++;
		}

		int xx = 0;
		Integer check_item_no = tblSample2Main.getRowCount();

		while (xx < check_item_no) {

			Boolean is_tagged = (Boolean) tblSample2Main.getValueAt(xx, 0);

			if (is_tagged == true) {

				String is_pdc = tblSample2Main.getValueAt(xx, 4).toString().trim();
				Double chk_amt = Double.parseDouble(tblSample2Main.getValueAt(xx, 6).toString().trim());
				String pay_rec_id = tblSample2Main.getValueAt(xx, 12).toString().trim();
				Object[] pbl_info = getProjPhase(pay_rec_id);

				String projID = "", subProj = "";
				try {
					projID = pbl_info[0].toString();
				} catch (NullPointerException e) {
					projID = "";
				}

				try {
					subProj = pbl_info[1].toString();
				} catch (NullPointerException e) {
					subProj = "";
				}

				String account_id = "";
				if (is_pdc.equals("F")) {
					account_id = "01-01-02-000";
				} else {
					account_id = "03-02-16-000";
				}

				{
					String sqlDetail = "INSERT into rf_jv_detail values (" + "'" + co_id + "',  \n" + // 1 co_id
							"'" + co_id + "',  \n" + // 2 busunit_id
							"'" + jv_no + "',  \n" + // 3 jv_no
							"1,  \n" + // 4 entry_no
							"" + y + ",  \n" + // 5 line_no
							"'" + account_id + "',  \n" + // 6 acct_id
							"" + chk_amt + ",  \n" + // 7 tran_amt
							"'C',  \n" + // 8 bal_side
							"'" + dep_no + "',  \n" + // 9 ref_no -- deposit_no ?
							"'" + projID + "',  \n" + // 10 project_id
							"'" + subProj + "',  \n" + // 11 sub_projectid
							"'',  \n" + // 12 div_id
							"'',  \n" + // 13 dept_id
							"'',  \n" + // 14 sect_id
							"null,  \n" + // 15 inter_co_id
							"null,  \n" + // 16 inter_busunit_id
							"null,  \n" + // 17 old_acct_id
							"null,  \n" + // 18 entity_id
							"null,  \n" + // 19 pbl_id
							"null,  \n" + // 20 seq_no
							"'A', " + // 21 status_id
							"'" + UserInfo.EmployeeCode + "',  \n" + // 22 created_by
							"'" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  \n" + // 23
							// date_created
							"'',  \n" + // 24 edited_by
							"null  \n" + // 25 date_edited
							")   ";

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);
					y++;
				}

				/*
				 * if (is_pdc.equals("T")) { String pdc_acct_id [] = { "11-02-00-000",
				 * "11-01-00-000" };
				 * 
				 * String pdc_bal_side [] = { "D", "C" };
				 * 
				 * int pdc_row = 0;
				 * 
				 * while (pdc_row<=1) {
				 * 
				 * String sqlDetail = "INSERT into rf_jv_detail values (" + "'"+co_id+"',  \n" +
				 * //1 co_id "'"+co_id+"',  \n" + //2 busunit_id "'"+jv_no+"',  \n" + //3 jv_no
				 * "1,  \n" + //4 entry_no ""+y+",  \n" + //5 line_no
				 * "'"+pdc_acct_id[pdc_row]+"',  \n" + //6 acct_id ""+chk_amt+",  \n" + //7
				 * tran_amt "'"+pdc_bal_side[pdc_row] +"', \n"+ //8 bal_side "'"+dep_no+"',  \n"
				 * + //9 ref_no -- deposit_no ? "'',  \n" + //10 project_id "'',  \n" + //11
				 * sub_projectid "'',  \n" + //12 div_id "'',  \n" + //13 dept_id "'',  \n" +
				 * //14 sect_id "null,  \n" + //15 inter_co_id "null,  \n" + //16
				 * inter_busunit_id "null,  \n" + //17 old_acct_id "null,  \n" + //18 entity_id
				 * "null,  \n" + //19 pbl_id "null,  \n" + //20 seq_no "'A', " + //21 status_id
				 * "'"+UserInfo.EmployeeCode+"',  \n" + //22 created_byedapostol
				 * "'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n"
				 * + //23 date_created "'',  \n" + //24 edited_by "null  \n" + //25 date_edited
				 * ")   " ;
				 * 
				 * System.out.printf("SQL #1: %s", sqlDetail); db.executeUpdate(sqlDetail,
				 * false); y++; pdc_row++; } }
				 */
			}

			xx++;
		}
	}
	
	private void AddTrail(String strActivity, String strDepNo) {
		/*EDITED BY JED 2021 : ADD JV NO ON AUDIT TRAIL*/
		
		String strRemarks = "";
		strRemarks = strRemarks + "CoID: " + lookupCompany.getValue() + "; ";
		strRemarks = strRemarks + "Branch: " + lookupOfficeBranch.getValue() + "; ";
		strRemarks = strRemarks + "Deposit#: " + lookupDepositNo.getValue() + "; ";
		strRemarks = strRemarks + "BankAccount#: " + lookupBankAcctNo.getValue() + "; ";
		strRemarks = strRemarks + "DepositDate: " + dteDeposit.getDate().toString() + "; ";
		strRemarks = strRemarks + "CollectionDate: " + dteCollection.getDate().toString() + "; ";
		strRemarks = strRemarks + "JV No: " + FncGlobal.GetString("select jv_no from cs_dp_header where dep_no = '"+dep_no+"' and co_id = '"+lookupCompany.getValue()+"'") + "; ";

		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("insert into mf_audit_trail (system_id, activity, user_code, date_upd, doc_no, remarks)\n"
				+ "values ('DEP', '" + strActivity + "', '" + UserInfo.EmployeeCode + "', now(), '" + strDepNo + "', '"
				+ strRemarks + "'); ", true);
		dbExec.commit();
	}

	private void save_redep() {
		if (validateSaving()) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				Double tran_amt = 0.00;
				String denom = "";
				String qty = "";
				String piece = "";
				String bank_id = "";
				String bank_branch_id = "";
				String check_no = "";
				String check_date = "";
				String pay_rec_id = "";
				String prev_chkstatus = "";
				String pdc = "";

				pgUpdate db = new pgUpdate();

				/** save deposit header **/
				/* Added by Mann2x; Date Added: July 31, 2017; DCRF# 199 */

				txtJVRemark.setText(GetNameUnit());
				insertDepositHeader(GetNameUnit());

				int y = 0;

				/** save check details **/
				for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
					Boolean isTrue = false;

					if (tblSample2Main.getValueAt(x, 0) instanceof String) {
						isTrue = new Boolean((String) tblSample2Main.getValueAt(x, 0));
					}

					if (tblSample2Main.getValueAt(x, 0) instanceof Boolean) {
						isTrue = (Boolean) tblSample2Main.getValueAt(x, 0);
					}

					if (isTrue) {
						y = y + 1;

						try {
							tran_amt = Double.parseDouble(tblSample2Main.getValueAt(x, 6).toString().trim());
						} catch (NullPointerException e) {
							tran_amt = 0.00;
						}

						try {
							bank_id = sql_bank_id(tblSample2Main.getValueAt(x, 1).toString().trim().toUpperCase());
						} catch (NullPointerException e) {
							bank_id = "";
						}

						try {
							bank_branch_id = sql_bank_branchid(bank_id,
									tblSample2Main.getValueAt(x, 2).toString().trim().toUpperCase());
						} catch (NullPointerException e) {
							bank_branch_id = "";
						}

						try {
							check_no = tblSample2Main.getValueAt(x, 3).toString().trim();
						} catch (NullPointerException e) {
							check_no = "";
						}

						try {
							check_date = tblSample2Main.getValueAt(x, 5).toString().trim();
						} catch (NullPointerException e) {
							check_date = "";
						}

						try {
							pay_rec_id = tblSample2Main.getValueAt(x, 12).toString().trim();
						} catch (NullPointerException e) {
							pay_rec_id = "";
						}

						try {
							pdc = tblSample2Main.getValueAt(x, 4).toString().trim();
						} catch (NullPointerException e) {
							pdc = "";
						}

						/** implement saving and update **/
						insertCheckDeposit(y, tran_amt, bank_id, bank_branch_id, check_no, check_date, pay_rec_id, db);

						if (pdc.equals("F") || pdc == "F") {
							prev_chkstatus = "05";
						} else {
							prev_chkstatus = "04";
							insertCheckStatus(check_date, check_no, pay_rec_id, dep_no, prev_chkstatus, db);
							updateCheckStatus(check_no, pay_rec_id, db, "05");
						}
					}
				}

				/** save cash details **/
				for (int x = 0; x < modelSample1Main.getRowCount(); x++) {
					piece = tblSample1Main.getValueAt(x, 1).toString().trim();

					if (piece.equals("0") || piece == "0" || piece.equals("")) {

					} else {
						qty = tblSample1Main.getValueAt(x, 1).toString().trim();
						denom = tblSample1Main.getValueAt(x, 0).toString().trim();
						insertCashDeposit(denom, qty, db);
					}
				}

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Deposit successfully saved.", "", JOptionPane.INFORMATION_MESSAGE);
				
				
				/*	Create JV	*/
				pgUpdate dbExec = new pgUpdate(); 
				dbExec.executeUpdate("call sp_create_jv_deposit('"+lookupCompany.getValue()+"', '"+dep_no+"', true); ", true);
				dbExec.commit();
				
				setJV(FncGlobal.GetString("select jv_no from cs_dp_header where dep_no = '"+dep_no+"' and co_id = '"+lookupCompany.getValue()+"';"), lookupCompany.getValue());
				
				displayJV_details(modelSample4Main, rowHeaderSample4Main, modelSample4Total);
				
				lookupDepositNo.setValue(dep_no);
				modelSample2Main.setEditable(false);
				tblSample2Main.setEditable(false);
				modelSample1Main.setEditable(false);
				tblSample1Main.setEditable(false);
				enableButtons(true, true, false, true, true, true, true, true);
				enable_fields(false);
				lblCollectionDate.setEnabled(false);
				dteCollection.setEnabled(false);
			}
		}
	}
	
	private void save() {
		if (validateSaving()) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				Double tran_amt = 0.00;
				String denom = "";
				String qty = "";
				String piece = "";
				String bank_id = "";
				String bank_branch_id = "";
				String check_no = "";
				String check_date = "";
				String pay_rec_id = "";
				String prev_chkstatus = "";
				String pdc = "";

				pgUpdate db = new pgUpdate();

				/** save deposit header **/
				insertDepositHeader();

				int y = 0;

				/** save check details **/
				for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
					Boolean isTrue = false;

					if (tblSample2Main.getValueAt(x, 0) instanceof String) {
						isTrue = new Boolean((String) tblSample2Main.getValueAt(x, 0));
					}

					if (tblSample2Main.getValueAt(x, 0) instanceof Boolean) {
						isTrue = (Boolean) tblSample2Main.getValueAt(x, 0);
					}

					if (isTrue) {
						y = y + 1;

						try {
							tran_amt = Double.parseDouble(tblSample2Main.getValueAt(x, 6).toString().trim());
						} catch (NullPointerException e) {
							tran_amt = 0.00;
						}

						try {
							bank_id = sql_bank_id(tblSample2Main.getValueAt(x, 1).toString().trim().toUpperCase());
						} catch (NullPointerException e) {
							bank_id = "";
						}

						try {
							bank_branch_id = sql_bank_branchid(bank_id,
									tblSample2Main.getValueAt(x, 2).toString().trim().toUpperCase());
						} catch (NullPointerException e) {
							bank_branch_id = "";
						}

						try {
							check_no = tblSample2Main.getValueAt(x, 3).toString().trim();
						} catch (NullPointerException e) {
							check_no = "";
						}

						try {
							check_date = tblSample2Main.getValueAt(x, 5).toString().trim();
						} catch (NullPointerException e) {
							check_date = "";
						}

						try {
							pay_rec_id = tblSample2Main.getValueAt(x, 12).toString().trim();
						} catch (NullPointerException e) {
							pay_rec_id = "";
						}

						try {
							pdc = tblSample2Main.getValueAt(x, 4).toString().trim();
						} catch (NullPointerException e) {
							pdc = "";
						}

						/** implement saving and update **/
						insertCheckDeposit(y, tran_amt, bank_id, bank_branch_id, check_no, check_date, pay_rec_id, db);

						if (pdc.equals("F") || pdc == "F") {
							prev_chkstatus = "05";
						} else {
							prev_chkstatus = "04";
							insertCheckStatus(check_date, check_no, pay_rec_id, dep_no, prev_chkstatus, db);
							updateCheckStatus(check_no, pay_rec_id, db, "05");
						}
					}
				}

				/** save cash details **/
				for (int x = 0; x < modelSample1Main.getRowCount(); x++) {
					piece = tblSample1Main.getValueAt(x, 1).toString().trim();

					if (piece.equals("0") || piece == "0" || piece.equals("")) {

					} else {
						qty = tblSample1Main.getValueAt(x, 1).toString().trim();
						denom = tblSample1Main.getValueAt(x, 0).toString().trim();
						insertCashDeposit(denom, qty, db);
					}
				}

				db.commit();
				createJV(dep_no, lookupCompany.getValue());
				
				JOptionPane.showMessageDialog(getContentPane(), "Deposit successfully saved.", "", JOptionPane.INFORMATION_MESSAGE);				
				lookupDepositNo.setValue(dep_no);
				
				setJV(FncGlobal.GetString("select jv_no from cs_dp_header where dep_no = '"+dep_no+"' and co_id = '"+lookupCompany.getValue()+"';"), lookupCompany.getValue());
				displayJV_details(modelSample4Main, rowHeaderSample4Main, modelSample4Total);
				
				modelSample2Main.setEditable(false);
				tblSample2Main.setEditable(false);
				modelSample1Main.setEditable(false);
				tblSample1Main.setEditable(false);
				enableButtons(true, true, false, true, true, true, true, true);
				enable_fields(false);
				lblCollectionDate.setEnabled(false);
				dteCollection.setEnabled(false);

				AddTrail("Add-Deposit", dep_no);
			}
		}
	}

	private void save_cash() {
		if (validateSaving()) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				Double tran_amt = 0.00;
				String denom = "";
				String qty = "";
				String piece = "";
				String bank_id = "";
				String bank_branch_id = "";
				String check_no = "";
				String check_date = "";
				String pay_rec_id = "";
				String prev_chkstatus = "";
				String pdc = "";

			}
		}
	}
	
	private void update() {

		if (validateSaving()) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Update",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				Double tran_amt = 0.00;
				String denom = "";
				String qty = "";
				String piece = "";
				String bank_id = "";
				String bank_branch_id = "";
				String check_no = "";
				String check_date = "";
				String pay_rec_id = "";
				String prev_chkstatus = "";
				String pdc = "";

				pgUpdate db = new pgUpdate();
				updateDepositHeader(db);

				int y = 0;
				updateCheckDetails_setInactive(db);
				updateCashDetails_setInactive(db);
				updateJVDetails_setInactive(db);

				for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
					try {
						pay_rec_id = tblSample2Main.getValueAt(x, 12).toString().trim();
					} catch (NullPointerException e) {
						pay_rec_id = tblSample2Main.getValueAt(x, 10).toString().trim();
					}

					try {
						check_no = tblSample2Main.getValueAt(x, 3).toString().trim();
					} catch (NullPointerException e) {
						check_no = "";
					}

					Boolean isTrue = false;
					if (tblSample2Main.getValueAt(x, 0) instanceof String) {
						isTrue = new Boolean((String) tblSample2Main.getValueAt(x, 0));
					}
					if (tblSample2Main.getValueAt(x, 0) instanceof Boolean) {
						isTrue = (Boolean) tblSample2Main.getValueAt(x, 0);
					}

					if (!isTrue) {
						String prev_chk_stat = getCheckPreviousStatus(pay_rec_id);

						if (prev_chk_stat.equals("")) {
						} else {
							reverseCheckStatus(check_no, pay_rec_id, prev_chk_stat, db);
							reverseCheckHistory(check_no, pay_rec_id, dep_no, prev_chk_stat, db);
						}
					} else if (isTrue) {
						y = y + 1;

						try {
							tran_amt = Double.parseDouble(tblSample2Main.getValueAt(x, 6).toString().trim());
						} catch (NullPointerException e) {
							tran_amt = 0.00;
						}

						try {
							bank_id = sql_bank_id(tblSample2Main.getValueAt(x, 1).toString().trim().toUpperCase());
						} catch (NullPointerException e) {
							bank_id = "";
						}

						try {
							bank_branch_id = sql_bank_branchid(bank_id,
									tblSample2Main.getValueAt(x, 2).toString().trim().toUpperCase());
						} catch (NullPointerException e) {
							bank_branch_id = "";
						}

						try {
							check_date = tblSample2Main.getValueAt(x, 5).toString().trim();
						} catch (NullPointerException e) {
							check_date = "";
						}

						try {
							pdc = tblSample2Main.getValueAt(x, 4).toString().trim();
						} catch (NullPointerException e) {
							pdc = "";
						}
						if (pdc.equals("F") || pdc == "F") {
							prev_chkstatus = "05";
						} else {
							prev_chkstatus = "04";
						}

						deactivateCheckStatusHistory(check_date, check_no, pay_rec_id, dep_no, prev_chkstatus);
						insertCheckDeposit(y, tran_amt, bank_id, bank_branch_id, check_no, check_date, pay_rec_id, db);
						updateCheckStatus(check_no, pay_rec_id, db, "05");
						insertCheckStatus(check_date, check_no, pay_rec_id, dep_no, prev_chkstatus, db);
					}
				}

				for (int x = 0; x < modelSample1Main.getRowCount(); x++) {

					try {
						piece = tblSample1Main.getValueAt(x, 1).toString().trim();
					} catch (NullPointerException e) {
						piece = "0";
					}

					if (piece.equals("0") || piece == "0" || piece.equals("")) {
					} else {
						qty = tblSample1Main.getValueAt(x, 1).toString().trim();
						denom = tblSample1Main.getValueAt(x, 0).toString().trim();
						insertCashDeposit(denom, qty, db);
					}
				}

				db.commit();
				createJV(dep_no, lookupCompany.getValue());
				JOptionPane.showMessageDialog(getContentPane(), "Deposit successfully updated.", "", JOptionPane.INFORMATION_MESSAGE);				
				lookupDepositNo.setValue(dep_no);
				
				setJV(FncGlobal.GetString("select jv_no from cs_dp_header where dep_no = '"+dep_no+"' and co_id = '"+lookupCompany.getValue()+"';"), lookupCompany.getValue());
				displayJV_details(modelSample4Main, rowHeaderSample4Main, modelSample4Total);
				
				
				modelSample2Main.setEditable(false);
				tblSample2Main.setEditable(false);
				modelSample1Main.setEditable(false);
				tblSample1Main.setEditable(false);
				enableButtons(true, true, false, true, true, true, false, true);
				enable_fields(false);
				lblCollectionDate.setEnabled(false);
				dteCollection.setEnabled(false);

				AddTrail("Add-Deposit", dep_no);
			}
		}
	}
	
	private void setJV(String jv_no, String co_id) {
		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select jv_date, (case when status_id = 'A' then 'ACTIVE' when status_id = 'I' then 'INACTIVE' when status_id = 'P' then 'POSTED' when status_id = 'D' then 'DELETED' else 'TAGGED' end) as status \n" + 
				"from rf_jv_header \n" + 
				"where jv_no = '"+jv_no+"' \n" + 
				"and co_id = '"+co_id+"'; ");
		
		if (!dbExec.isNull()) {
			txtJV.setText(jv_no);
			txtJVStatus.setText(dbExec.Result[0][1].toString());			
			dteJV.setDate((Date) dbExec.Result[0][0]);
		} else {
			txtJV.setText("");
			txtJVStatus.setText("");			
			dteJV.setDate(null);			
		}
	}
	
	private boolean isCash(String deposit, String co_id) {
		return FncGlobal.GetBoolean("select exists(select b.* \n" + 
				"from cs_dp_header a \n" + 
				"inner join cs_dp_csh_detail b on a.dep_no = b.dep_no \n" + 
				"where a.dep_no = '"+deposit+"' and a.co_id = '"+co_id+"'); "); 
	}
	
	private void createJV(String deposit, String co_id) {
		pgUpdate dbExec = new pgUpdate(); 
		if (isCash(dep_no, lookupCompany.getValue())) {//**EDITED BY JED 2022-02-04 : EDIT THE FUNCTION TO CORRECT JV CREATOR 
			String strOver = JOptionPane.showInputDialog("Indicate over deposit amount: ", "0.00"); 
			//dbExec.executeUpdate("call sp_create_jv_deposit_cash('"+co_id+"', '"+deposit+"', "+((strOver.equals(""))?"0":strOver)+"::numeric(19, 2)); ", true);
			dbExec.executeUpdate("call sp_create_jv_deposit_cash_v2('"+co_id+"', '"+deposit+"', "+((strOver.equals(""))?"0":strOver)+"::numeric(19, 2)); ", true);
		} else {
			//dbExec.executeUpdate("call sp_create_jv_deposit_check('"+co_id+"', '"+deposit+"'); ", true);
			dbExec.executeUpdate("call sp_create_jv_deposit_check_v2('"+co_id+"', '"+deposit+"'); ", true);
		}
		dbExec.commit();
	}
}