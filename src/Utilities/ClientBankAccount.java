package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.security.Timestamp;

import javax.mail.internet.NewsAddress;
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
import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class ClientBankAccount extends _JInternalFrame implements
		ActionListener {

	
	private static final long serialVersionUID = 4274522042672903139L;
	static String title = "Client Bank Account";
	Dimension frameSize = new Dimension(500, 400);
	
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
	
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	
	static Boolean blnEdit = false;
	
	public ClientBankAccount() {
		super(title, true, true, false, false);
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
							
							bState(true);
							GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText());
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
					JXPanel pnlBankLabel = new JXPanel(new GridLayout(5, 1, 5, 5));
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
					
					JXPanel pnlBankBox = new JXPanel(new GridLayout(5, 1, 5, 5));
					pnlBank.add(pnlBankBox, BorderLayout.CENTER);
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
						txtAcctNo = new JTextField("");
						pnlAcctBal.add(txtAcctNo, BorderLayout.LINE_START);
						txtAcctNo.setHorizontalAlignment(JTextField.CENTER);
						txtAcctNo.setPreferredSize(new Dimension(150, 0));
						txtAcctNo.setEditable(false);
					}
					{
						JXPanel pnlBal = new JXPanel(new BorderLayout(5, 5));
						pnlAcctBal.add(pnlBal, BorderLayout.CENTER);
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
						pnlDateEndo.add(dteOpened, BorderLayout.LINE_START);
						dteOpened.setPreferredSize(new Dimension(150, 0));
						dteOpened.setEnabled(false);
					}
					{
						JXPanel pnlEndo = new JXPanel(new GridLayout(1, 2, 5, 5));
						pnlDateEndo.add(pnlEndo, BorderLayout.CENTER);
						{
							chkEndo = new JCheckBox("Endorsed");
							pnlEndo.add(chkEndo);
							chkEndo.setHorizontalAlignment(JTextField.TRAILING);
							chkEndo.setEnabled(false);
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
					pnlButtons.add(btnDelete);
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
				bState(true);
			}
		}
	}
	
	private String Client() {
		String SQL = "SELECT a.entity_id, a.entity_name, c.proj_name, d.description, c.proj_id, d.pbl_id, b.seq_no\n" +
					 "FROM rf_entity A\n" +
					 "INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id\n" +
					 "INNER JOIN mf_project C ON b.projcode = c.proj_id\n" +
					 "INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id\n" +
					 "WHERE b.status_id = 'A'\n" +
					 "ORDER BY a.entity_name";
		return SQL;
	}
	
	private String Bank(){
		String sql = "SELECT bank_id as \"Bank ID\", " +
					 "bank_alias as \"Bank Alias\", " +
					 "bank_name as \"Bank Name\" " +
					 "FROM mf_bank " +
					 "WHERE status_id = 'A' " +
					 "ORDER BY bank_id\r\n" ;
		return sql;
	}
	
	public String BankBranch(String bank){
		String sql = "SELECT bank_branch_id as \"Bank Branch ID\", " +
					 "trim(bank_branch_location) as \"Address\" " +
					 "FROM mf_bank_branch " +
					 "WHERE status_id = 'A' " +
					 "AND bank_id = '"+bank+"' " +
					 "ORDER BY bank_branch_id \r\n" ;
		return sql;
	}	
	
	public String AccountType(){
		String SQL = "SELECT a.bank_accttype_id, a.bank_accttype_desc\n" +
					 "FROM mf_bank_account_type a\n" +
					 "ORDER BY a.bank_accttype_id";
		return SQL;
	}	
	
	private void bState(Boolean blnDo) {
		btnAdd.setEnabled(blnDo);
		btnEdit.setEnabled(blnDo);
		btnDelete.setEnabled(blnDo);
		btnSave.setEnabled(!blnDo);
		btnCancel.setEnabled(!blnDo);
	}
	
	private void BoxLock(Boolean blnDo) {
		txtBankID.setEnabled(!blnDo);
		txtBranchID.setEnabled(!blnDo);
		txtAcctID.setEnabled(!blnDo);
		txtAcctNo.setEditable(!blnDo);
		dteOpened.setEnabled(!blnDo);
		txtBal.setEditable(!blnDo);
		chkEndo.setEnabled(!blnDo);
	}
	
	private void ClearBox() {
		txtBankID.setText("");
		txtBank.setText("");
		
		txtBranchID.setText("");
		txtBranch.setText("");
		
		txtAcctID.setText("");
		txtAcctType.setText("");
		
		txtAcctNo.setText("");
		
		txtBal.setText("");
		chkEndo.setSelected(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Add")) {
			bState(false);
			BoxLock(false);
			ClearBox();
			blnEdit = false;
		}
		else if(e.getActionCommand().equals("Edit")) {
			if (txtBankID.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "This client is yet to be endorsed to any bank.");
			}
			else {
				bState(false);
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
			bState(true);
			BoxLock(true);
			blnEdit = false;
			GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText());
		}
		else if(e.getActionCommand().equals("Cancel")) {
			bState(true);
			BoxLock(true);
			blnEdit = false;
		}
	}
	
	private void GetBankDetail(String ID, String Project, String Unit, String Sequence) {
		String SQL = "SELECT b.bank_id, b.bank_name, c.bank_branch_id, c.bank_branch_location,\n" +
					 "d.bank_accttype_id, d.bank_accttype_desc, a.account_no, a.ave_balance::text,\n" +
					 "a.date_opened, a.endorsed, a.entity_id, a.projcode, a.pbl_id, a.seq_no\n" +
					 "FROM rf_bank_accounts A\n" +
					 "INNER JOIN mf_bank B ON a.bank_id = b.bank_id\n" +
					 "LEFT JOIN mf_bank_branch C ON a.bank_branch_id = c.bank_branch_id\n" +
					 "LEFT JOIN mf_bank_account_type D ON a.accttype_id = d.bank_accttype_id\n" +
					 "WHERE a.entity_id = '"+ID+"' AND a.projcode = '"+Project+"' AND a.pbl_id = '"+Unit+"' AND a.seq_no = "+Sequence;
		
		System.out.println("");
		System.out.println(SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		System.out.println("");
		if(db.isNotNull()){
			System.out.println("");
			System.out.println("Starting a movement");
			System.out.println("Bank ID		-	" + (String)db.getResult()[0][0]);
			System.out.println("Bank		-	" + (String)db.getResult()[0][1]);
			System.out.println("Branch ID	-	" + (String)db.getResult()[0][2]);
			System.out.println("Branch		-	" + (String)db.getResult()[0][3]);
			System.out.println("Acct ID		-	" + (String)db.getResult()[0][4]);
			System.out.println("Acct Type	-	" + (String)db.getResult()[0][5]);
			System.out.println("Acct No.	-	" + (String)db.getResult()[0][6]);
			System.out.println("Balance		-	" + (String)db.getResult()[0][7]);
			System.out.println("Date Opened	-	" + (java.sql.Timestamp)db.getResult()[0][8]);
			System.out.println("Endorsed	-	" + (Boolean)db.getResult()[0][9]);
			
			txtBankID.setText((String)db.getResult()[0][0]);
			txtBank.setText((String)db.getResult()[0][1]);
			txtBranchID.setText((String)db.getResult()[0][2]);
			txtBranch.setText((String)db.getResult()[0][3]);
			txtAcctID.setText((String)db.getResult()[0][4]);
			txtAcctType.setText((String)db.getResult()[0][5]);
			txtAcctNo.setText((String)db.getResult()[0][6]);
			txtBal.setText((String)db.getResult()[0][7]);
			dteOpened.setDate((java.sql.Timestamp)db.getResult()[0][8]);
			chkEndo.setSelected((Boolean)db.getResult()[0][9]);
			
			btnAdd.setEnabled(false);
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
		System.out.println("Balance			-	" + txtBal.getText());
		System.out.println("Endorsement		-	" + chkEndo.isSelected());
		
		if(JOptionPane.showConfirmDialog(getContentPane(), "Save the bank details?", "Save", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			String SQL = "";
			
			if(!blnEdit) {
				 SQL = "INSERT INTO rf_bank_accounts\n" +
					   "(bank_acct_rec_id, entity_id, projcode, pbl_id, seq_no, bank_id, bank_branch_id, accttype_id, account_no, date_opened, ave_balance, endorsed, status_id)\n" +
					   "VALUES\n" +
					   "('"+GetNextPrime()+"', '"+txtID.getText()+"', '"+txtProjID.getText()+"', '"+txtUnitID.getText()+"', "+txtSeq.getText()+", " +
					   "'"+txtBankID.getText()+"', '"+txtBranchID.getText()+"', '"+txtAcctID.getText()+"', '"+txtAcctNo.getText()+"', " +
					   "'"+dteOpened.getDate().toString()+"', '"+txtBal.getText()+"', "+chkEndo.isSelected()+", 'A')";
			}
			else {
				 SQL = "UPDATE rf_bank_accounts \n" +
					   "SET \n" +
					   "bank_id = '"+txtBankID.getText()+"', \n" +
					   "bank_branch_id = '"+txtBranchID.getText()+"', \n" +
					   "accttype_id = '"+txtAcctID.getText()+"', \n" +
					   "account_no = '"+txtAcctNo.getText()+"', \n" +
					   "date_opened = '"+dteOpened.getDate().toString()+"', \n" +
					   "ave_balance = '"+txtBal.getText()+"', \n" +
					   "endorsed = "+chkEndo.isSelected()+"\n" +
					   "WHERE entity_id = '"+txtID.getText()+"' AND projcode = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();

			}
			
			System.out.println(SQL);
			pgUpdate db = new pgUpdate();
			db.executeUpdate(SQL, false);
			db.commit();
			
			JOptionPane.showMessageDialog(getContentPane(), "Saved!");	
		}
	}
	
	private void delete() {
		String SQL = "DELETE FROM rf_bank_accounts " +
				     "WHERE entity_id = '"+txtID.getText()+"' AND projcode = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();
		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, false);
		db.commit();
	}
	
	private static String GetNextPrime() {
		String rec_id = "";
		String SQL = "select trim(to_char(max(coalesce(bank_acct_rec_id::int,0))+1,'000000000')) from rf_bank_accounts" ;

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
}
