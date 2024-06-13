package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
import interfaces._GUI;

public class mbtcLoanReleased_panel2 extends JXPanel implements ActionListener, _GUI {

	private static final long serialVersionUID = 2217044354979396445L;
	private mbtcLoanReleased mbtcLoanReleasedMain;
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private static JTextField txtProjID;
	private static JTextField txtUnitID;
	private static JTextField txtName;
	private static JTextField txtProject;
	private static JTextField txtUnit;
	private static JTextField txtSeq;
	private static JTextField txtBank;
	private static JTextField txtBranch;
	private static JTextField txtAcctType;
	private static JTextField txtAcctNo;
	private static JTextField txtMail;
	
	private static _JLookup txtID;
	private static _JLookup txtBankID;
	private static _JLookup txtBranchID;
	private static _JLookup txtAcctID;
	private static _JLookup txtBatch; 
	
	private static _JDateChooser dteOpened;
	private static _JDateChooser dteClosed;
	
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnPreview;
	
	private JXPanel pnlButtons;
	
	private static JCheckBox chkEndo;
	static Boolean blnEdit = false;
	
	private static JXPanel panMain;
	
	private JTextArea txtMsg1;
	private JTextArea txtMsg2; 
	
    StringBuffer sb_actual; 
    StringBuffer sb_preview; 
	
    private JEditorPane editorMail; 
    
	public mbtcLoanReleased_panel2(mbtcLoanReleased mbtclr) {
		this.mbtcLoanReleasedMain = mbtclr;
		initGUI();
	}

	public mbtcLoanReleased_panel2(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public mbtcLoanReleased_panel2(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public mbtcLoanReleased_panel2(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel pnlPageStart = new JXPanel(new BorderLayout(5, 5));
				panMain.add(pnlPageStart, BorderLayout.PAGE_START);
				pnlPageStart.setPreferredSize(new Dimension(0, 115));
				pnlPageStart.setBorder(JTBorderFactory.createTitleBorder("Account Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel pnlPSLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
						pnlPageStart.add(pnlPSLabel, BorderLayout.LINE_START);
						pnlPSLabel.setPreferredSize(new Dimension(200, 0));
						{
							{
								JLabel lblName = new JLabel("Name");
								pnlPSLabel.add(lblName);
								lblName.setHorizontalAlignment(JTextField.LEADING);
							}
							{
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
												txtMail.setText(FncGlobal.GetString("(SELECT REPLACE(REPLACE(TRIM(X.email::text), '{', ''), '}', '') as email FROM rf_contacts X WHERE X.entity_id = '"+txtID.getValue().toString()+"')"));
											}
										} catch(NullPointerException e) {
											System.out.println("Null pointer caught during lookup.");
										}
										
										if (GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText())==true) {
											bState(1);
										} else {
											bState(2);
										}
									}
								});
							}
							{
								JLabel lblProject = new JLabel("Project");
								pnlPSLabel.add(lblProject);
								lblProject.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								txtProjID = new JTextField("");
								pnlPSLabel.add(txtProjID);
								txtProjID.setHorizontalAlignment(JTextField.CENTER);
								txtProjID.setEditable(false);	
							}
							{
								JLabel lblUnit = new JLabel("Unit");
								pnlPSLabel.add(lblUnit);
								lblUnit.setHorizontalAlignment(JTextField.LEADING);	
							}
							{
								txtUnitID = new JTextField("");
								pnlPSLabel.add(txtUnitID);
								txtUnitID.setHorizontalAlignment(JTextField.CENTER);
								txtUnitID.setEditable(false);
							}
						}
					}
					{
						JXPanel pnlBox = new JXPanel(new GridLayout(3, 1, 5, 5));
						pnlPageStart.add(pnlBox, BorderLayout.CENTER);
						{
							{
								txtName = new JTextField("");
								pnlBox.add(txtName);
								txtName.setHorizontalAlignment(JTextField.CENTER);
								txtName.setEditable(false);
							}
							{
								JXPanel panProjectMail = new JXPanel(new BorderLayout(5, 5));
								pnlBox.add(panProjectMail, BorderLayout.CENTER);
								{
									{
										txtProject = new JTextField("");
										panProjectMail.add(txtProject, BorderLayout.LINE_START);
										txtProject.setHorizontalAlignment(JTextField.CENTER);
										txtProject.setPreferredSize(new Dimension(200, 0));
										txtProject.setEditable(false);
									}
									{
										JLabel lblMail = new JLabel("Email Add.");
										panProjectMail.add(lblMail, BorderLayout.CENTER);
										lblMail.setHorizontalAlignment(JLabel.CENTER);
										lblMail.setEnabled(true);
									}
									{
										txtMail = new JTextField("");
										panProjectMail.add(txtMail, BorderLayout.LINE_END);
										txtMail.setHorizontalAlignment(JTextField.CENTER);
										txtMail.setPreferredSize(new Dimension(200, 0));
										txtMail.setEditable(false);								
									}
								}
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
										JLabel lblSeq = new JLabel("Sequence");
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
				}
			}
			{
				JXPanel pnlPageEnd = new JXPanel(new BorderLayout(5, 5));
				panMain.add(pnlPageEnd, BorderLayout.CENTER);
				{
					{
						JXPanel panBatch = new JXPanel(new GridLayout(1, 2, 5, 5));
						pnlPageEnd.add(panBatch, BorderLayout.PAGE_START);
						panBatch.setPreferredSize(new Dimension(0, 25));
						{
							{
								JXPanel panBatch1 = new JXPanel(new BorderLayout(5, 5)); 
								panBatch.add(panBatch1); 
								{
									
								}
							}
							{
								JXPanel panBatch2 = new JXPanel(new BorderLayout(5, 5)); 
								panBatch.add(panBatch2); 
								{
									{
										JLabel lblBatch = new JLabel("Batch"); 
										panBatch2.add(lblBatch, BorderLayout.LINE_START);
										lblBatch.setHorizontalAlignment(JLabel.LEADING);
										lblBatch.setPreferredSize(new Dimension(50, 0));
									}
									{
										txtBatch = new _JLookup();
										panBatch2.add(txtBatch); 
										txtBatch.setReturnColumn(0);
										txtBatch.setLookupSQL("select distinct batch_no, date_created::date \n" + 
												"from rf_loan_released_bank_details \n" + 
												"where batch_no is not null \n" + 
												"order by date_created::date desc, batch_no desc");
										txtBatch.setHorizontalAlignment(JTextField.CENTER);
										txtBatch.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												
											}
										});
									}
									{
										btnPreview = new JButton("Preview"); 
										panBatch2.add(btnPreview, BorderLayout.LINE_END); 
										btnPreview.setActionCommand("Preview");
										btnPreview.addActionListener(this);
										btnPreview.setPreferredSize(new Dimension(100, 0));
									}
								}
							}
						}
					}
					{
						JXPanel pnlBank = new JXPanel(new BorderLayout(5, 5));
						pnlPageEnd.add(pnlBank, BorderLayout.CENTER);
						pnlBank.setBorder(JTBorderFactory.createTitleBorder("Bank Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel pnlBankLabel = new JXPanel(new GridLayout(6, 1, 5, 5));
							pnlBank.add(pnlBankLabel, BorderLayout.LINE_START);
							pnlBankLabel.setPreferredSize(new Dimension(100, 0));
							{
								JLabel lblBank = new JLabel("Bank");
								pnlBankLabel.add(lblBank);
								lblBank.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								JLabel lblBranch = new JLabel("Branch");
								pnlBankLabel.add(lblBranch);
								lblBranch.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								JLabel lblAcctType = new JLabel("Acct. Type");
								pnlBankLabel.add(lblAcctType);
								lblAcctType.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								JLabel lblAcctNo = new JLabel("Acct. No.");
								pnlBankLabel.add(lblAcctNo);
								lblAcctNo.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								JLabel lblDateOp = new JLabel("Date Opened");
								pnlBankLabel.add(lblDateOp);
								lblDateOp.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								JLabel lblDateClosed = new JLabel("Date Closed");
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
													} catch(NullPointerException e) {
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
													} catch(NullPointerException e) {
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
								{
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
								}
								{
									{
										JXPanel pnlDateEndo = new JXPanel(new BorderLayout(5, 5));
										pnlBankBox.add(pnlDateEndo, BorderLayout.CENTER);
										{
											dteOpened = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
											pnlDateEndo.add(dteOpened, BorderLayout.CENTER);
											dteOpened.setPreferredSize(new Dimension(200, 0));
											dteOpened.setEnabled(false);
										}
									}
									{
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
								}
							}
							BoxLock(true);
						}
					}
				}
				{
					pnlButtons = new JXPanel(new GridLayout(1, 5, 5, 5));
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
	}

	private String Client() {
		return "SELECT a.entity_id, a.entity_name, c.proj_name, d.description, c.proj_id, d.pbl_id, b.seq_no, E.batch_no \n" +
			"FROM rf_entity A \n" +
			"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id \n" +
			"INNER JOIN mf_project C ON b.projcode = c.proj_id \n" +
			"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id \n" +
			"INNER JOIN rf_loan_released_mbtc_account E ON b.entity_id = e.entity_id AND b.projcode = e.proj_id AND b.pbl_id = e.pbl_id AND b.seq_no::INT = e.seq_no::INT \n" +
			"WHERE b.status_id = 'A' AND COALESCE(E.batch_no, '') <> '' \n" +
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
		chkEndo.setEnabled(!blnDo);
		txtBatch.setEnabled(blnDo);
	}
	
	private void ClearBox() {
		txtAcctNo.setText("");
		
		chkEndo.setSelected(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add")) {
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
			
			txtAcctNo.setText("254-3-254-");
			
			chkEndo.setSelected(true);
			txtBatch.setValue("");
		} else if(e.getActionCommand().equals("Edit")) {
			if (txtBankID.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "This client is yet to be endorsed to any bank.");
			}
			else {
				bState(3);
				BoxLock(false);
				blnEdit = true;	
			}
		} else if(e.getActionCommand().equals("Delete")) {
			delete();
			blnEdit = false;
			GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText());
		} else if(e.getActionCommand().equals("Save")) {
			Save();
			BoxLock(true);
			blnEdit = false;
			if (GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText())==true) {
				bState(1);
			} else {
				bState(2);
			}
		} else if (e.getActionCommand().equals("Cancel")) {
			BoxLock(true);
			blnEdit = false;
			if (GetBankDetail(txtID.getText(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText())==true) {
				bState(1);
			} else {
				bState(2);
			}
		} else if (e.getActionCommand().equals("Preview")) {
			System.out.println("");
			System.out.println("mbtcLoanReleasedMain.txtProjectID.getValue(): " + mbtcLoanReleasedMain.txtProjectID.getValue());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_name", FncGlobal.GetString("select company_name from mf_company where co_id = '"+mbtcLoanReleasedMain.txtCoID.getValue()+"'"));
			mapParameters.put("user_name", UserInfo.EmployeeCode);
			mapParameters.put("project", FncGlobal.GetString("select proj_name from mf_project where proj_id = '"+mbtcLoanReleasedMain.txtProjectID.getValue()+"'"));
			mapParameters.put("batch_no", txtBatch.getValue());
			FncReport.generateReport("/Reports/rpt_mbtc_loan_released.jasper", "MBTC Loan Released Accounts", "", mapParameters);
		}
	}
	
	private static Boolean GetBankDetail(String ID, String Project, String Unit, String Sequence) {
		Boolean blnWR = false;
		
		String SQL = "SELECT b.bank_id, b.bank_name, c.bank_branch_id, c.bank_branch_location,\n" +
					 "d.bank_accttype_id, d.bank_accttype_desc, a.account_no, a.ave_balance::text,\n" +
					 "a.date_opened, a.endorsed, a.entity_id, a.proj_id, a.pbl_id, a.seq_no, a.status_id, a.date_closed\n" +
					 "FROM rf_loan_released_bank_details A\n" +
					 "INNER JOIN mf_bank B ON a.bank_id = b.bank_id\n" +
					 "LEFT JOIN mf_bank_branch C ON a.bank_branch_id = c.bank_branch_id\n" +
					 "LEFT JOIN mf_bank_account_type D ON a.accttype_id = d.bank_accttype_id\n" +
					 "WHERE a.entity_id = '"+ID+"' AND a.proj_id = '"+Project+"' AND a.pbl_id = '"+Unit+"' AND a.seq_no = "+Sequence;
		
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
		} else {
			System.out.println("Without Record");
			txtBankID.setText("");
			txtBank.setText("");
			
			txtBranchID.setText("");
			txtBranch.setText("");
			
			txtAcctID.setText("");
			txtAcctType.setText("");
			
			txtAcctNo.setText("");
			
			chkEndo.setEnabled(false);
		}
		
		return blnWR;
	}
	
	private void Save() {
		String strBatch = ""; 
		
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
		System.out.println("Endorsement		-	" + chkEndo.isSelected());
		
		String strMsg = "";
		if (chkEndo.isSelected()) {
			strMsg = "Save the bank details?";
		} else {
			strMsg = "Account is going to be deactivated. Proceed?";
		}
		
		if(JOptionPane.showConfirmDialog(this, strMsg, "Save", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			String SQL = "";
			
			if(!blnEdit) {
				if (FncGlobal.GetBoolean("select exists(select batch_no from rf_loan_released_bank_details where date_created::date = now()::date)")==false) {
					strBatch = FncGlobal.GetString("select LPAD((coalesce(max(batch_no), '0')::int + 1)::text, 10, '0')from rf_loan_released_bank_details"); 
				} else {
					txtBatch.setValue(FncGlobal.GetString("select max(batch_no) from rf_loan_released_bank_details where date_created::date = now()::date and batch_no is not null"));
					
					if (JOptionPane.showConfirmDialog(null, "Add to batch number " + txtBatch.getValue(), 
							"Batch", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
						strBatch = FncGlobal.GetString("select max(batch_no) from rf_loan_released_bank_details where date_created::date = now()::date"); 
					} else {
						strBatch = FncGlobal.GetString("select LPAD((coalesce(max(batch_no), '0')::int + 1)::text, 10, '0')from rf_loan_released_bank_details");
					}
				}
				
				txtBatch.setValue(strBatch);
				
				SQL = "INSERT INTO rf_loan_released_bank_details\n" +
						"(bank_acct_rec_id, entity_id, proj_id, pbl_id, seq_no, bank_id, bank_branch_id, accttype_id, account_no, date_opened, endorsed, status_id, batch_no, date_created)\n" +
						"VALUES\n" +
						"('"+GetNextPrime()+"', '"+txtID.getText()+"', '"+txtProjID.getText()+"', '"+txtUnitID.getText()+"', "+txtSeq.getText()+", " +
						"'"+txtBankID.getText()+"', '"+txtBranchID.getText()+"', '"+txtAcctID.getText()+"', '"+txtAcctNo.getText().trim()+"', " +
						"'"+dteOpened.getDate().toString()+"', "+chkEndo.isSelected()+", 'A', '"+strBatch+"', now())";
			} else {
				if (chkEndo.isSelected()) {
					 SQL = "UPDATE rf_loan_released_bank_details \n" +
							   "SET \n" +
							   "bank_id = '"+txtBankID.getText()+"', \n" +
							   "bank_branch_id = '"+txtBranchID.getText()+"', \n" +
							   "accttype_id = '"+txtAcctID.getText()+"', \n" +
							   "account_no = '"+txtAcctNo.getText()+"', \n" +
							   "date_opened = '"+dteOpened.getDate().toString()+"', \n" +
							   "endorsed = "+chkEndo.isSelected()+", \n" +
							   "date_closed = null, \n" +
							   "closed_by = '' \n" +
							   "WHERE entity_id = '"+txtID.getText()+"' AND proj_id = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();	
				} else {
					 SQL = "UPDATE rf_loan_released_bank_details \n" +
							   "SET \n" +
							   "bank_id = '"+txtBankID.getText()+"', \n" +
							   "bank_branch_id = '"+txtBranchID.getText()+"', \n" +
							   "accttype_id = '"+txtAcctID.getText()+"', \n" +
							   "account_no = '"+txtAcctNo.getText()+"', \n" +
							   "date_opened = '"+dteOpened.getDate().toString()+"', \n" +
							   "endorsed = "+chkEndo.isSelected()+", \n" +
							   "date_closed = '"+dteClosed.getDate().toString()+"', \n" +
							   "closed_by = '"+UserInfo.EmployeeCode+"' \n" +
							   "WHERE entity_id = '"+txtID.getText()+"' AND proj_id = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();
				}
			}
			
			System.out.println(SQL);
			pgUpdate db = new pgUpdate();
			db.executeUpdate(SQL, false);
			db.commit();

			if (txtMail.getText()=="" || txtMail.getText().length() < 3) {
				JOptionPane.showMessageDialog(null, "This account's email address is not set.");
			} else {
				sendEmail();
			}			
		}
	}
	
	private void delete() {
		String SQL = "DELETE FROM rf_loan_released_bank_details " +
				     "WHERE entity_id = '"+txtID.getText()+"' AND proj_id = '"+txtProjID.getText()+"' AND pbl_id = '"+txtUnitID.getText()+"' AND seq_no = "+txtSeq.getText();
		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, false);
		db.commit();
	}
	
	private static String GetNextPrime() {
		String rec_id = "";
		String SQL = "select trim(to_char(max(coalesce(bank_acct_rec_id::int,0))+1,'000000000')) from rf_loan_released_bank_details" ;

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
	

	private void sendEmail() {

		new Thread(new Runnable() {
			public void run() {

				FncGlobal.startProgress("Sending email...");
				/*
				String from = "apostol_emman@yahoo.com";
				String pass = "ishkkhakfeandunull";
				String to = "apostol_emman@yahoo.com";
				*/

		        String from = "cenqhomesdevtcorp@yahoo.com";
		        String pass = "terraverde";
		        String to = txtMail.getText().trim();

		        String subject = "CENQHOMES DEVELOPMENT CORPORATION - METROBANK ACCOUNT NUMBER";
	            sb_actual = new StringBuffer();
	            sb_preview = new StringBuffer(); 
	            
	    	    JXPanel panPreview = new JXPanel(new BorderLayout(5, 5)); 
	    	    panPreview.setPreferredSize(new Dimension(1000, 500));
	    	    panPreview.setBorder(new EmptyBorder(5, 5, 5, 5));
    	    	{
    	    		{
    	    			editorMail = new JEditorPane(); 
    	    			editorMail.setFont(new Font("Tahoma", Font.PLAIN, 9));
    	    			editorMail.setContentType("text/html");
    	    			editorMail.setEditable(false);
    	    			editorMail.setBorder(new EmptyBorder(5, 5, 5, 5));
    	    			editorMail.setBackground(Color.BLACK);
    	    	    	
    	    	    	JScrollPane scrMail = new JScrollPane(editorMail); 
    	    	    	panPreview.add(scrMail, BorderLayout.CENTER);	
    	    		}
    	    		{
    	    			JXPanel panEditor = new JXPanel(new GridLayout(2, 1, 5, 5)); 
    	    			panPreview.add(panEditor, BorderLayout.LINE_END);
    	    			panEditor.setPreferredSize(new Dimension(200, 0));
    	    			{
    	    				{
    	    					JXPanel panMsg1 = new JXPanel(new BorderLayout(5, 5)); 
    	    					panEditor.add(panMsg1, BorderLayout.CENTER);
    	    					panMsg1.setBorder(JTBorderFactory.createTitleBorder("Message Body 1", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
    	    					{
        	    					txtMsg1 = new JTextArea((chkEndo.isSelected()?"This is to confirm your account number with METROBANK.":"This is to inform you that your account with METROBANK has been deactivated.")); 
        	    					panMsg1.add(txtMsg1, BorderLayout.CENTER); 
        	    					txtMsg1.setEditable(true); 
        	    					txtMsg1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        	    					txtMsg1.setLineWrap(true);
        	    					txtMsg1.setWrapStyleWord(true);
        	    					txtMsg1.getDocument().addDocumentListener(new DocumentListener() {
    									public void removeUpdate(DocumentEvent e) {
    										html(); 
    									}
    									
    									public void insertUpdate(DocumentEvent e) {
    										html(); 
    									}
    									
    									public void changedUpdate(DocumentEvent e) {
    										html(); 
    									}
    									
    									private void html() {
    							            if (chkEndo.isSelected()) {
    								            sb_preview = _mbtcLoanReleased.sbActive_preview(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	
    							            } else {
    								            sb_preview = _mbtcLoanReleased.sbCancel_preview(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	            	
    							            } 
    										
    							            editorMail.setText(sb_preview.toString());
    							            editorMail.repaint();
    										
    										System.out.println("Pressed!");
    									}
    								});
        	    					txtMsg1.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {

										}
										
										public void keyReleased(KeyEvent e) {

										}
										
										public void keyPressed(KeyEvent e) {
											if (e.getKeyCode()==10) {
												e.consume();
												
												JOptionPane.showMessageDialog(null, "Avoid using the nextline or `Enter` key.");
											}
										}
									});
        	    				}
    	    				}
    	    				{
    	    					JXPanel panMsg2 = new JXPanel(new BorderLayout(5, 5)); 
    	    					panEditor.add(panMsg2, BorderLayout.CENTER);
    	    					panMsg2.setBorder(JTBorderFactory.createTitleBorder("Message Body 2", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
    	    					{
        	    					txtMsg2 = new JTextArea((chkEndo.isSelected()?"You may now deposit your payment on or before your due date.":"You can no longer make deposits for your payments.")); 
        	    					panMsg2.add(txtMsg2); 
        	    					txtMsg2.setEditable(true); 
        	    					txtMsg2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        	    					txtMsg2.setLineWrap(true);
        	    					txtMsg2.setWrapStyleWord(true);
        	    					txtMsg2.getDocument().addDocumentListener(new DocumentListener() {
    									public void removeUpdate(DocumentEvent e) {
    										html(); 
    									}
    									
    									public void insertUpdate(DocumentEvent e) {
    										html(); 
    									}
    									
    									public void changedUpdate(DocumentEvent e) {
    										html(); 
    									}
    									
    									private void html() {
    							            if (chkEndo.isSelected()) {
    								            sb_preview = _mbtcLoanReleased.sbActive_preview(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	
    							            } else {
    								            sb_preview = _mbtcLoanReleased.sbCancel_preview(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	            	
    							            } 
    										
    							            editorMail.setText(sb_preview.toString());
    							            editorMail.repaint();
    									}
    								});
        	    					txtMsg2.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {

										}
										
										public void keyReleased(KeyEvent e) {

										}
										
										public void keyPressed(KeyEvent e) {
											if (e.getKeyCode()==10) {
												e.consume();
												
												JOptionPane.showMessageDialog(null, "Avoid using the nextline or `Enter` key.");
											}
										}
									});
        	    				}	
    	    				}
    	    			}
    	    		}
    	    		
		            if (chkEndo.isSelected()) {
			            sb_preview = _mbtcLoanReleased.sbActive_preview(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	
		            } else {
			            sb_preview = _mbtcLoanReleased.sbCancel_preview(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	            	
		            } 
    	    		
    	    		editorMail.setText(sb_preview.toString());
    	    		editorMail.repaint();
    	    	} 
	    	    
	    	    int scanOption; 
				scanOption = JOptionPane.showOptionDialog(null, panPreview, "Email Preview", 
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
						new Object[] {"SEND", "CANCEL"}, JOptionPane.CANCEL_OPTION);
				
				if (scanOption==JOptionPane.YES_OPTION) {
		            if (chkEndo.isSelected()) {
			            sb_actual = _mbtcLoanReleased.sbActive_actual(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	
		            } else {
			            sb_actual = _mbtcLoanReleased.sbCancel_actual(txtName.getText(), txtProject.getText(), txtProjID.getText(), txtUnitID.getText(), txtAcctNo.getText(), UserInfo.FullName, txtMsg1.getText(), txtMsg2.getText());	            	
		            } 
					
		            Boolean blnSent = _mbtcLoanReleased.sendFromMailWithoutAttachment(from, pass, to, subject, sb_actual);
		            
		            String strMsg = ""; 
		            if (blnSent) {
		            	strMsg = "Email sent."; 
		            } else {
		            	strMsg = "Sending failed."; 
		            }
		            
		            JOptionPane.showMessageDialog(null, strMsg);
				}
	    	    
	            FncGlobal.stopProgress();
			}
		}).start();
	}
}
