package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class ClientBankAccount extends _JInternalFrame implements
		ActionListener {

	
	private static final long serialVersionUID = 4274522042672903139L;
	static String title = "Client Bank Account";
	Dimension frameSize = new Dimension(500, 440);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	private JLabel lblName;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeq;
	private JLabel lblBank;
	private JLabel lblBranch;
	private JLabel lblAcctType;
	private JLabel lblAcctNo;
	private JLabel lblDateOp;
	private JLabel lblDateClosed;
	private JLabel lblBal;
	
	static JCheckBox chkEndo;
	
	static _JLookup txtID;
	
	static JTextField txtProjID;
	static JTextField txtUnitID;
	static JTextField txtName;
	static JTextField txtProject;
	static JTextField txtUnit;
	static JTextField txtSeq;
	static JTextField txtBank;
	static JTextField txtBranch;
	static JTextField txtAcctType;
	static JTextField txtAcctNo;
	static JTextField txtBal;
	
	static _JLookup txtBankID;
	static _JLookup txtBranchID;
	static _JLookup txtAcctID;
	
	static _JDateChooser dteOpened;
	static _JDateChooser dteClosed;
	
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	
	static Boolean blnEdit = false;
	
	public ClientBankAccount() {
		super(title, true, true, false, true);
		InitGUI();
	}

	public ClientBankAccount(String title) {
		super(title);
		InitGUI();
	}

	public ClientBankAccount(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		InitGUI();
	}

	public void InitGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel pnlPageStart = new JXPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlPageStart, BorderLayout.PAGE_START);
			pnlPageStart.setPreferredSize(new Dimension(0, 125));
			pnlPageStart.setBorder(JTBorderFactory.createTitleBorder("Account Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				JXPanel pnlPSLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
				pnlPageStart.add(pnlPSLabel, BorderLayout.LINE_START);
				pnlPSLabel.setPreferredSize(new Dimension(200, 0));
				{
					lblName = new JLabel("Name");
					pnlPSLabel.add(lblName);
					lblName.setHorizontalAlignment(JTextField.LEADING);
					
					txtID = new _JLookup("");
					pnlPSLabel.add(txtID);
					txtID.setHorizontalAlignment(JTextField.CENTER);
					txtID.setReturnColumn(0);
					txtID.setLookupSQL(Client());
					txtID.setFilterName(true);
					txtID.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							try {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									txtName.setText(data[1].toString());
									txtProjID.setText(data[4].toString());
									txtProject.setText(data[2].toString());
									txtUnitID.setText(data[5].toString());
									txtUnit.setText(data[3].toString());
									txtSeq.setText(data[6].toString());
								}
							}
							
							catch(NullPointerException e) {
								System.out.println("Null pointer caught during lookup.");
							}
							
							if (GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText())==true) {
								bState(1);
							} else {
								bState(2);
							}
						}
					});
					
					lblProject = new JLabel("Project");
					pnlPSLabel.add(lblProject);
					lblProject.setHorizontalAlignment(JTextField.LEADING);

					txtProjID = new JTextField("");
					pnlPSLabel.add(txtProjID);
					txtProjID.setHorizontalAlignment(JTextField.CENTER);
					txtProjID.setEditable(false);
					
					lblUnit = new JLabel("Unit");
					pnlPSLabel.add(lblUnit);
					lblUnit.setHorizontalAlignment(JTextField.LEADING);
					
					txtUnitID = new JTextField("");
					pnlPSLabel.add(txtUnitID);
					txtUnitID.setHorizontalAlignment(JTextField.CENTER);
					txtUnitID.setEditable(false);
				}
			}
			{
				JXPanel pnlBox = new JXPanel(new GridLayout(3, 1, 5, 5));
				pnlPageStart.add(pnlBox, BorderLayout.CENTER);
				{
					txtName = new JTextField("");
					pnlBox.add(txtName);
					txtName.setHorizontalAlignment(JTextField.CENTER);
					txtName.setEditable(false);
				}
				{
					txtProject = new JTextField("");
					pnlBox.add(txtProject);
					txtProject.setHorizontalAlignment(JTextField.CENTER);
					txtProject.setEditable(false);
				}
				{
					JXPanel pnlUnitSeq = new JXPanel(new BorderLayout(5, 5));
					pnlBox.add(pnlUnitSeq, BorderLayout.CENTER);
					{
						JXPanel pnlUnitDesc = new JXPanel(new BorderLayout(5, 5));
						pnlUnitSeq.add(pnlUnitDesc, BorderLayout.CENTER);
						{
							txtUnit = new JTextField("");
							pnlUnitDesc.add(txtUnit);
							txtUnit.setHorizontalAlignment(JTextField.CENTER);
							txtUnit.setHorizontalAlignment(JTextField.CENTER);
							txtUnit.setEditable(false);
						}
						JXPanel pnlSeq = new JXPanel(new BorderLayout(5, 5));
						pnlUnitSeq.add(pnlSeq, BorderLayout.LINE_END);
						pnlSeq.setPreferredSize(new Dimension(125, 0));
						{
							lblSeq = new JLabel("Sequence");
							pnlSeq.add(lblSeq, BorderLayout.CENTER);
							lblSeq.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							txtSeq = new JTextField("");
							pnlSeq.add(txtSeq, BorderLayout.LINE_END);
							txtSeq.setPreferredSize(new Dimension(50, 0));
							txtSeq.setHorizontalAlignment(JTextField.CENTER);
							txtSeq.setEditable(false);
						}
					}
				}
			}
			
		}
		{
			JXPanel pnlPageEnd = new JXPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlPageEnd, BorderLayout.CENTER);
			pnlPageEnd.setPreferredSize(new Dimension(0, 125));
			{
				JXPanel pnlBank = new JXPanel(new BorderLayout(5, 5));
				pnlPageEnd.add(pnlBank, BorderLayout.CENTER);
				pnlBank.setBorder(JTBorderFactory.createTitleBorder("Bank Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel pnlBankLabel = new JXPanel(new GridLayout(6, 1, 5, 5));
					pnlBank.add(pnlBankLabel, BorderLayout.LINE_START);
					pnlBankLabel.setPreferredSize(new Dimension(100, 0));
					{
						lblBank = new JLabel("Bank");
						pnlBankLabel.add(lblBank);
						lblBank.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						lblBranch = new JLabel("Branch");
						pnlBankLabel.add(lblBranch);
						lblBranch.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						lblAcctType = new JLabel("Acct. Type");
						pnlBankLabel.add(lblAcctType);
						lblAcctType.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						lblAcctNo = new JLabel("Acct. No.");
						pnlBankLabel.add(lblAcctNo);
						lblAcctNo.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						lblDateOp = new JLabel("Date Opened");
						pnlBankLabel.add(lblDateOp);
						lblDateOp.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						lblDateClosed = new JLabel("Date Closed");
						pnlBankLabel.add(lblDateClosed);
						lblDateClosed.setHorizontalAlignment(JTextField.LEADING);
					}
					JXPanel pnlBankBox = new JXPanel(new GridLayout(6, 1, 5, 5));
					pnlBank.add(pnlBankBox, BorderLayout.CENTER);
					{
						{
							{
								JXPanel pnlBankID = new JXPanel(new BorderLayout(5, 5));
								pnlBankBox.add(pnlBankID, BorderLayout.CENTER);
								{
									txtBankID = new _JLookup("");
									pnlBankID.add(txtBankID, BorderLayout.LINE_START);
									txtBankID.setPreferredSize(new Dimension(75, 0));
									txtBankID.setHorizontalAlignment(JTextField.CENTER);
									txtBankID.setReturnColumn(0);
									txtBankID.setLookupSQL(Bank());
									txtBankID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											try {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {
													txtBank.setText(data[2].toString());
													txtBranchID.setLookupSQL(BankBranch(txtBankID.getText()));
												}
											}
											catch(NullPointerException e) {
												System.out.println("Null pointer caught during Bank lookup.");
											}
										}
									});
								}
								{
									txtBank = new JTextField("");
									pnlBankID.add(txtBank, BorderLayout.CENTER);
									txtBank.setHorizontalAlignment(JTextField.CENTER);
									txtBank.setEditable(false);
								}
							}
						}
						{
							{
								JXPanel pnlBranchID = new JXPanel(new BorderLayout(5, 5));
								pnlBankBox.add(pnlBranchID, BorderLayout.CENTER);
								{
									txtBranchID = new _JLookup("");
									pnlBranchID.add(txtBranchID, BorderLayout.LINE_START);
									txtBranchID.setPreferredSize(new Dimension(75, 0));
									txtBranchID.setHorizontalAlignment(JTextField.CENTER);
									txtBranchID.setReturnColumn(0);
									txtBranchID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											try {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {
													txtBranch.setText(data[1].toString());
												}
											}
											catch(NullPointerException e) {
												System.out.println("Null pointer caught during Bank Branch lookup.");
											}
										}
									});
								}
								{
									txtBranch = new JTextField("");
									pnlBranchID.add(txtBranch, BorderLayout.CENTER);
									txtBranch.setHorizontalAlignment(JTextField.CENTER);
									txtBranch.setEditable(false);
								}
							}
						}
						{
							{
								JXPanel pnlAcct = new JXPanel(new BorderLayout(5, 5));
								pnlBankBox.add(pnlAcct, BorderLayout.CENTER);
								{
									txtAcctID = new _JLookup("");
									pnlAcct.add(txtAcctID, BorderLayout.LINE_START);
									txtAcctID.setPreferredSize(new Dimension(75, 0));
									txtAcctID.setHorizontalAlignment(JTextField.CENTER);
									txtAcctID.setReturnColumn(0);
									txtAcctID.setLookupSQL(AccountType());
									txtAcctID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											try {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {
													txtAcctType.setText(data[1].toString());
												}
											}
											catch(NullPointerException e) {
												System.out.println("Null pointer caught during Account Type lookup.");
											}
										}
									});
								}
								{
									txtAcctType = new JTextField("");
									pnlAcct.add(txtAcctType, BorderLayout.CENTER);
									txtAcctType.setHorizontalAlignment(JTextField.CENTER);
									txtAcctType.setEditable(false);
								}
							}
						}
						JXPanel pnlAcctBal = new JXPanel(new BorderLayout(5, 5));
						pnlBankBox.add(pnlAcctBal, BorderLayout.CENTER);
						{
							{
								txtAcctNo = new JTextField("");
								pnlAcctBal.add(txtAcctNo, BorderLayout.LINE_START);
								txtAcctNo.setHorizontalAlignment(JTextField.CENTER);
								txtAcctNo.setPreferredSize(new Dimension(200, 0));
								txtAcctNo.setEditable(false);
							}
							{
								JXPanel pnlEndo = new JXPanel(new GridLayout(1, 2, 5, 5));
								pnlAcctBal.add(pnlEndo, BorderLayout.CENTER);
								{
									chkEndo = new JCheckBox("Account is Active");
									pnlEndo.add(chkEndo);
									chkEndo.setHorizontalAlignment(JTextField.TRAILING);
									chkEndo.setEnabled(false);
									chkEndo.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											if (chkEndo.isSelected()) {
												dteClosed.setDate(null);
											} else {
												dteClosed.setDate(FncGlobal.getDateToday());
											}
										}
									});
								}
							}
						}
						{
							JXPanel pnlBal = new JXPanel(new BorderLayout(5, 5));
							//pnlAcctBal.add(pnlBal, BorderLayout.CENTER);
							{
								lblBal = new JLabel("Balance");
								pnlBal.add(lblBal, BorderLayout.LINE_START);
								lblBal.setHorizontalAlignment(JTextField.CENTER);
								lblBal.setPreferredSize(new Dimension(75, 0));
							}
							{
								txtBal = new JTextField("");
								pnlBal.add(txtBal, BorderLayout.CENTER);
								txtBal.setHorizontalAlignment(JTextField.CENTER);
								txtBal.setEditable(false);
							}
						}
						JXPanel pnlDateEndo = new JXPanel(new BorderLayout(5, 5));
						pnlBankBox.add(pnlDateEndo, BorderLayout.CENTER);
						{
							dteOpened = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlDateEndo.add(dteOpened, BorderLayout.CENTER);
							dteOpened.setPreferredSize(new Dimension(200, 0));
							dteOpened.setEnabled(false);
						}
						JXPanel pnlDateClosed = new JXPanel(new BorderLayout(5, 5));
						pnlBankBox.add(pnlDateClosed, BorderLayout.CENTER);
						{
							dteClosed = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlDateClosed.add(dteClosed, BorderLayout.CENTER);
							dteClosed.setPreferredSize(new Dimension(200, 0));
							dteClosed.setEnabled(false);
							dteClosed.setDate(null);
						}	
					}
					BoxLock(true);
				}
			}
			{
				JXPanel pnlButtons = new JXPanel(new GridLayout(1, 5, 5, 5));
				pnlPageEnd.add(pnlButtons, BorderLayout.PAGE_END);
				pnlButtons.setPreferredSize(new Dimension(0, 30));
				{
					btnAdd = new JButton("Add");
					pnlButtons.add(btnAdd);
					btnAdd.setActionCommand("Add");
					btnAdd.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlButtons.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
				}
				{
					btnDelete = new JButton("Delete");
					//pnlButtons.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlButtons.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlButtons.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
				bState(4);
			}
		}
	}
	
	private String Client() {
		return "SELECT a.entity_id, a.entity_name, c.proj_name, d.description, c.proj_id, d.pbl_id, b.seq_no, E.batch_no\n" +
			"FROM rf_entity A\n" +
			"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id\n" +
			"INNER JOIN mf_project C ON b.projcode = c.proj_id\n" +
			"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id\n" +
			"LEFT JOIN rf_rtd_enrolled E ON B.entity_id = E.c_entity_id AND B.projcode = E.c_proj_id AND B.pbl_id = E.c_pbl_id AND B.seq_no::INT = E.c_seq_no::INT\n" +
			"WHERE b.status_id = 'A' AND COALESCE(E.batch_no, '') <> ''\n" +
			"ORDER BY a.entity_name";
	}
	
	private String Bank(){
		return "SELECT bank_id as \"Bank ID\", " +
			"bank_alias as \"Bank Alias\", " +
			"bank_name as \"Bank Name\" " +
			"FROM mf_bank " +
			"WHERE status_id = 'A' " +
			"ORDER BY bank_id\r\n" ;
	}
	
	public String BankBranch(String bank){
		return "SELECT bank_branch_id as \"Bank Branch ID\", " +
			"trim(bank_branch_location) as \"Address\" " +
			"FROM mf_bank_branch " +
			"WHERE status_id = 'A' " +
			"AND bank_id = '"+bank+"' " +
			"ORDER BY bank_branch_id \r\n" ;
	}	
	
	public String AccountType(){
		return "SELECT a.bank_accttype_id, a.bank_accttype_desc\n" +
			"FROM mf_bank_account_type a\n" +
			"ORDER BY a.bank_accttype_id";
	}	
	
	private void bState(Integer i) {
		if (i==1) {										/*		With Record			*/
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(true);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		} else if (i==2) {								/*		Without Record		*/
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		} else if (i==3) {								/*		Add Or Edit			*/
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(true);
			btnCancel.setEnabled(true);
		} else if (i==4) {								/*		Initial State		*/
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		}
	}
	
	private void BoxLock(Boolean blnDo) {
		txtBankID.setEnabled(!blnDo);
		txtBranchID.setEnabled(!blnDo);
		txtAcctID.setEnabled(!blnDo);
		txtAcctNo.setEditable(!blnDo);
		dteOpened.setEnabled(!blnDo);
		dteClosed.setEnabled(!blnDo);
		txtBal.setEditable(!blnDo);
		chkEndo.setEnabled(!blnDo);
	}
	
	private void ClearBox() {
		txtAcctNo.setText("");
		
		txtBal.setText("");
		chkEndo.setSelected(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Add")) {
			bState(3);
			BoxLock(false);
			ClearBox();
			blnEdit = false;
			
			System.out.println("");
			txtBankID.setValue("02");
			txtBranchID.setValue("1748");
			txtAcctID.setText("10");
			
			txtBank.setText("METROPOLITAN BANK AND TRUST COMPANY");
			txtBranch.setText("WACK-WACK BRANCH");
			txtAcctType.setText("METROBANK DIRECT - WACK-WACK");
			
			chkEndo.setSelected(true);
		}
		else if(e.getActionCommand().equals("Edit")) {
			if (txtBankID.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "This client is yet to be endorsed to any bank.");
			}
			else {
				bState(3);
				BoxLock(false);
				blnEdit = true;	
			}
		}
		else if(e.getActionCommand().equals("Delete")) {
			delete();
			blnEdit = false;
			GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText());
		}
		else if(e.getActionCommand().equals("Save")) {
			Save();
			BoxLock(true);
			blnEdit = false;
			if (GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText())==true) {
				bState(1);
			} else {
				bState(2);
			}
		}
		else if(e.getActionCommand().equals("Cancel")) {
			BoxLock(true);
			blnEdit = false;
			if (GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText())==true) {
				bState(1);
			} else {
				bState(2);
			}
		}
	}
	
	private static Boolean GetBankDetail(String ID, String Project, String Unit, String Sequence) {
		Boolean blnWR = false;
		
		String SQL = "SELECT b.bank_id, b.bank_name, c.bank_branch_id, c.bank_branch_location,\n" +
					 "d.bank_accttype_id, d.bank_accttype_desc, a.account_no, a.ave_balance::text,\n" +
					 "a.date_opened, a.endorsed, a.entity_id, a.projcode, a.pbl_id, a.seq_no, a.status_id, a.date_closed\n" +
					 "FROM rf_rtd_accounts A\n" +
					 "INNER JOIN mf_bank B ON a.bank_id = b.bank_id\n" +
					 "LEFT JOIN mf_bank_branch C ON a.bank_branch_id = c.bank_branch_id\n" +
					 "LEFT JOIN mf_bank_account_type D ON a.accttype_id = d.bank_accttype_id\n" +
					 "WHERE a.entity_id = '"+ID+"' AND a.projcode = '"+Project+"' AND a.pbl_id = '"+Unit+"' AND a.seq_no = "+Sequence;
		
		System.out.println("");
		System.out.println(SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			System.out.println("");
			System.out.println("Starting a movement");
			System.out.println("Bank ID		-	" + (String) db.getResult()[0][0]);
			System.out.println("Bank		-	" + (String) db.getResult()[0][1]);
			System.out.println("Branch ID	-	" + (String) db.getResult()[0][2]);
			System.out.println("Branch		-	" + (String) db.getResult()[0][3]);
			System.out.println("Acct ID		-	" + (String) db.getResult()[0][4]);
			System.out.println("Acct Type	-	" + (String) db.getResult()[0][5]);
			System.out.println("Acct No.	-	" + (String) db.getResult()[0][6]);
			System.out.println("Balance		-	" + (String) db.getResult()[0][7]);
			System.out.println("Date Opened	-	" + (java.sql.Timestamp) db.getResult()[0][8]);
			System.out.println("Active		-	" + (Boolean) db.getResult()[0][9]);
			System.out.println("Date Closed	-	" + (java.sql.Timestamp) db.getResult()[0][15]);
			
			txtBankID.setText((String) db.getResult()[0][0]);
			txtBank.setText((String) db.getResult()[0][1]);
			txtBranchID.setText((String) db.getResult()[0][2]);
			txtBranch.setText((String) db.getResult()[0][3]);
			txtAcctID.setText((String) db.getResult()[0][4]);
			txtAcctType.setText((String) db.getResult()[0][5]);
			txtAcctNo.setText((String) db.getResult()[0][6]);
			txtBal.setText((String) db.getResult()[0][7]);
			dteOpened.setDate((java.sql.Timestamp) db.getResult()[0][8]);
			dteClosed.setDate((java.sql.Timestamp) db.getResult()[0][15]);
			
			blnWR = true;
			
			if ((Boolean)db.getResult()[0][9]) {
				System.out.println("Set Selected: True");
				chkEndo.setSelected(true);
			} else {
				System.out.println("Set Selected: False");
				chkEndo.setSelected(false);
			}
		}
		else{
			System.out.println("Without Record");
			txtBankID.setText("");
			txtBank.setText("");
			
			txtBranchID.setText("");
			txtBranch.setText("");
			
			txtAcctID.setText("");
			txtAcctType.setText("");
			
			txtAcctNo.setText("");
			
			txtBal.setText("");
			chkEndo.setEnabled(false);
		}
		
		return blnWR;
	}
	
	private void Save() {
		System.out.println("ID				-	" + txtID.getText());
		System.out.println("Project			-	" + txtProjID.getText());
		System.out.println("Unit			-	" + txtUnitID.getText());
		System.out.println("Sequence		-	" + txtSeq.getText());
		System.out.println("Bank			-	" + txtBankID.getText());
		System.out.println("Branch			-	" + txtBranchID.getText());
		System.out.println("Acct ID			-	" + txtAcctID.getText());
		System.out.println("Acct No			-	" + txtAcctNo.getText());
		System.out.println("Date			-	" + dteOpened.getText());
		System.out.println("Date Closed		-	" + dteClosed.getText());
		System.out.println("Balance			-	" + txtBal.getText());
		System.out.println("Endorsement		-	" + chkEndo.isSelected());
		
		String strMsg = "";
		if (chkEndo.isSelected()) {
			strMsg = "Save the bank details?";
		} else {
			strMsg = "Account is going to be deactivated. Proceed?";
		}
		
		if(JOptionPane.showConfirmDialog(getContentPane(), strMsg, "Save", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			String SQL = "";
			
			if(!blnEdit) {
				 SQL = "INSERT INTO rf_rtd_accounts\n" +
					   "(bank_acct_rec_id, entity_id, projcode, pbl_id, seq_no, bank_id, bank_branch_id, accttype_id, account_no, date_opened, endorsed, status_id)\n" +
					   "VALUES\n" +
					   "('"+GetNextPrime()+"', '"+txtID.getText()+"', '"+txtProjID.getText()+"', '"+txtUnitID.getText()+"', "+txtSeq.getText()+", " +
					   "'"+txtBankID.getText()+"', '"+txtBranchID.getText()+"', '"+txtAcctID.getText()+"', '"+txtAcctNo.getText().trim()+"', " +
					   "'"+dteOpened.getDate().toString()+"', "+chkEndo.isSelected()+", 'A')";
			}
			else {
				if (chkEndo.isSelected()) {
					 SQL = "UPDATE rf_rtd_accounts \n" +
							   "SET \n" +
							   "bank_id = '"+txtBankID.getText()+"', \n" +
							   "bank_branch_id = '"+txtBranchID.getText()+"', \n" +
							   "accttype_id = '"+txtAcctID.getText()+"', \n" +
							   "account_no = '"+txtAcctNo.getText()+"', \n" +
							   "date_opened = '"+dteOpened.getDate().toString()+"', \n" +
							   "endorsed = "+chkEndo.isSelected()+", \n" +
							   "date_closed = null, \n" +
							   "closed_by = '' \n" +
							   "WHERE entity_id = '"+txtID.getText()+"' AND projcode = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();	
				} else {
					 SQL = "UPDATE rf_rtd_accounts \n" +
							   "SET \n" +
							   "bank_id = '"+txtBankID.getText()+"', \n" +
							   "bank_branch_id = '"+txtBranchID.getText()+"', \n" +
							   "accttype_id = '"+txtAcctID.getText()+"', \n" +
							   "account_no = '"+txtAcctNo.getText()+"', \n" +
							   "date_opened = '"+dteOpened.getDate().toString()+"', \n" +
							   "endorsed = "+chkEndo.isSelected()+", \n" +
							   "date_closed = '"+dteClosed.getDate().toString()+"', \n" +
							   "closed_by = '"+UserInfo.EmployeeCode+"' \n" +
							   "WHERE entity_id = '"+txtID.getText()+"' AND projcode = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();
				}
			}
			
			System.out.println(SQL);
			pgUpdate db = new pgUpdate();
			db.executeUpdate(SQL, false);
			db.commit();
			
			JOptionPane.showMessageDialog(getContentPane(), "Saved!");	
		}
	}
	
	private void delete() {
		String SQL = "DELETE FROM rf_rtd_accounts " +
				     "WHERE entity_id = '"+txtID.getText()+"' AND projcode = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();
		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, false);
		db.commit();
	}
	
	private static String GetNextPrime() {
		String rec_id = "";
		String SQL = "select trim(to_char(max(coalesce(bank_acct_rec_id::int,0))+1,'000000000')) from rf_rtd_accounts" ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		System.out.println("");
		
		if(db.isNotNull()){
			rec_id = (String)db.getResult()[0][0];
			
			if(rec_id==null){
				rec_id = "1";
			}
		}
		else{
			rec_id = "1";
		}
		
		System.out.println(rec_id);
		return rec_id;
	}
	
	private void GenReport() {
		String strLastName = _RealTimeDebit.GetValue("SELECT TRIM(last_name) FROM rf_entity WHERE entity_id = '"+txtID.getText()+"'");
		String strFirstName = _RealTimeDebit.GetValue("SELECT TRIM(first_name) FROM rf_entity WHERE entity_id = '"+txtID.getText()+"'");
		String strMiddleName = _RealTimeDebit.GetValue("SELECT TRIM(middle_name) FROM rf_entity WHERE entity_id = '"+txtID.getText()+"'");
		
		String strAddress = _RealTimeDebit.GetValue(_RealTimeDebit.sql_MainAddress(txtID.getText()));
		String strPermanentAddress = _RealTimeDebit.GetValue(_RealTimeDebit.sql_OtherAddress(txtID.getText()));
		
		if (strAddress == strPermanentAddress) {
			strPermanentAddress = "";
		}
		else if (strAddress == "") {
			strAddress = strPermanentAddress;
		}
		
		String strMobile = _RealTimeDebit.GetValue(_RealTimeDebit.sql_Contact(txtID.getText()));
		String strEmail = _RealTimeDebit.GetValue("SELECT email FROM rf_contacts WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'");
		String strBirthMonth = _RealTimeDebit.Padme(_RealTimeDebit.GetValue("SELECT DATE_PART('month', date_of_birth)::CHAR(2)::text FROM rf_entity WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'"), 2);
		String strBirthDay = _RealTimeDebit.Padme(_RealTimeDebit.GetValue("SELECT DATE_PART('day', date_of_birth)::CHAR(2)::text FROM rf_entity WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'"), 2);
		String strBirthYear = _RealTimeDebit.Padme(_RealTimeDebit.GetValue("SELECT DATE_PART('year', date_of_birth)::CHAR(4)::text FROM rf_entity WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'"), 2);
		String strBirthPlace = _RealTimeDebit.GetValue("");
		String strTIN = _RealTimeDebit.GetValue("SELECT REPLACE(tin_no, '-', '') FROM rf_entity_id_no WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'");
		String strNationality = _RealTimeDebit.GetValue("SELECT (CASE WHEN COALESCE(citizenship_code, '') <> '' THEN 'Filipino' ELSE '' END) FROM rf_entity WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'");
		String strUnmarried = _RealTimeDebit.GetValue("SELECT (CASE WHEN civil_status_code = 'S' THEN '/' ELSE '' END) FROM rf_entity WHERE entity_id = '' AND status_id = 'A'");
		String strMarried = _RealTimeDebit.GetValue("SELECT (CASE WHEN civil_status_code = 'M' THEN '/' ELSE '' END) FROM rf_entity WHERE entity_id = '' AND status_id = 'A'");
		String strSeparated = _RealTimeDebit.GetValue("");
		String strWidowed = _RealTimeDebit.GetValue("");
		String strDivorced = _RealTimeDebit.GetValue("");
		String strMale = _RealTimeDebit.GetValue("SELECT (CASE WHEN gender = 'M' THEN '/' ELSE '' END) FROM rf_entity WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'");
		String strFemale = _RealTimeDebit.GetValue("SELECT (CASE WHEN gender = 'F' THEN '/' ELSE '' END) FROM rf_entity WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'");
		String strSpouse = _RealTimeDebit.GetValue("SELECT B.entity_name FROM rf_entity_connect A INNER JOIN rf_entity B ON A.connect_id = B.entity_id WHERE A.entity_id = '"+txtID.getText()+"' AND connect_type = 'SP'");
		String strMotherMaidenName = _RealTimeDebit.GetValue("SELECT mother_maiden_lname || ', ' || mother_maiden_fname || ' ' || mother_maiden_mname FROM rf_entity_mother_maiden_name WHERE entity_id = '"+txtID.getText()+"'");
		String strNoChildren = _RealTimeDebit.GetValue("SELECT (CASE WHEN dependent_no > 0 THEN dependent_no WHEN COALESCE(dependent_no::CHAR(1), '') = '' THEN 0 ELSE dependent_no END)::text FROM rf_entity WHERE entity_id = '"+txtID.getText()+"' AND status_id = 'A'");
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("LastName", strLastName);
		mapParameters.put("FirstName", strFirstName);
		mapParameters.put("MiddleName", strMiddleName);
		mapParameters.put("Address", strAddress);
		mapParameters.put("PermanentAddress", strPermanentAddress);
		mapParameters.put("Mobile", strMobile);
		mapParameters.put("Email", strEmail);
		mapParameters.put("BirthMonth", strBirthMonth);
		mapParameters.put("BirthDay", strBirthDay);
		mapParameters.put("BirthYear", strBirthYear);
		mapParameters.put("BirthPlace", strBirthPlace);
		mapParameters.put("TIN", strTIN);
		mapParameters.put("Nationality", strNationality);
		mapParameters.put("Unmarried", strUnmarried);
		mapParameters.put("Married", strMarried);
		mapParameters.put("Separated", strSeparated);
		mapParameters.put("Widowed", strWidowed);
		mapParameters.put("Divorced", strDivorced);
		mapParameters.put("Male", strMale);
		mapParameters.put("Female", strFemale);
		mapParameters.put("Spouse", strSpouse);
		mapParameters.put("MotherMaidenName", strMotherMaidenName);
		mapParameters.put("NoChildren", strNoChildren);
		FncReport.generateReport("/Reports/rpt_mbtcCustomerInfoIndividual.jasper", "Customer Information Individual", "", mapParameters);
	}
}