package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelBankAccount;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class BankAccounts extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Bank Accounts";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlBankAcctList;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlBankAcctDtls;
	private JPanel pnlBankAcctDtls_b;
	private JPanel pnlAgentDtl_1;
	private JPanel pnlAgentDtl_1b;
	private JPanel pnlBankAcctDtlsAmt;
	private JPanel pnlBankAcctDtlsAmt_1;
	private JPanel pnlBankAcctName;
	private JPanel pnlAcctLabel;
	private JPanel pnlBankAcct;
	private JPanel pnlBankAcct_1;
	private JPanel pnlBankAcct_2;	
	private JPanel pnlAgentPosition;
	private JPanel pnlBankAcctDtlsAmt_2;
	private JPanel pnlBankAcctDtlsAmt_2a;
	private JPanel pnlBankAcctDtlsAmt2;
	private JPanel pnlBankAcct_3;
	private JPanel pnlComp_a1_a;
	private JPanel pnlComp_a1_b;

	private modelBankAccount modelBankAcct;
	private modelBankAccount modelBankAcct_total;

	private _JLookup lookupCompany;
	private _JLookup lookupBankAcct;
	private _JLookup lookupBankBranch;
	private _JLookup lookupAccount;
	private _JLookup lookupBank;

	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnAddNew;
	private JButton btnEdit;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblCompany;
	private JLabel lblBankAcctID;
	private JLabel lblBank;
	private JLabel lblStatus;
	private JLabel lblBankBranch;
	private JLabel lblAcctID;
	private JLabel lblCashDeposit;
	private JLabel lblFundClass;
	private JLabel lblType;

	private _JTagLabel tagCompany;
	private _JTagLabel tagBank;
	private _JTagLabel tagAccount;
	private _JTagLabel tagBankBranch;

	private _JScrollPaneMain scrollBankAcct;
	private _JScrollPaneTotal scrollBankAcct_total;
	private _JTableMain tblBankAcct;
	private JList rowHeaderBankAcct;
	private _JTableTotal tblBankAcct_total;

	private JXTextField TxtBankAcctDesc;
	private JXTextField TxtBankAcctNo;

	private JComboBox cmbStatus;
	private JComboBox cmbCashDep;
	private JComboBox cmbFundClass;
	private JComboBox cmbType;
	
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		

	String co_id 	= "";		
	String company 	= "";
	String company_logo;	
	String to_do 	= "";  //to distinguish whether to add new agent or new sched.
	private JLabel lblCommDisb;
	private JComboBox cmbCommDisb;

	public BankAccounts() {
		super(title, true, true, true, true);
		initGUI();
	}

	public BankAccounts(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public BankAccounts(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(747, 501));
		this.setBounds(0, 0, 747, 501);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(745, 487));

		{ //start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(905, 40));	
			pnlComp.setBorder(lineBorder);

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(926, 38));	
				pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));			

				{
					pnlComp_a1 = new JPanel(new BorderLayout(0, 0));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(152, 33));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						pnlComp_a1_a = new JPanel(new BorderLayout(0, 5));
						pnlComp_a1.add(pnlComp_a1_a, BorderLayout.WEST);	
						pnlComp_a1_a.setPreferredSize(new java.awt.Dimension(75, 33));
						pnlComp_a1_a.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

						lblCompany = new JLabel("COMPANY  ", JLabel.TRAILING);
						pnlComp_a1_a.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(115, 25));
						lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD|Font.ITALIC,12));
					}
					{
						pnlComp_a1_b = new JPanel(new BorderLayout(0, 5));
						pnlComp_a1.add(pnlComp_a1_b, BorderLayout.CENTER);	
						pnlComp_a1_b.setPreferredSize(new java.awt.Dimension(186, 33));
						pnlComp_a1_b.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

						lookupCompany = new _JLookup(null, "Company",0,2);
						pnlComp_a1_b.add(lookupCompany);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setReturnColumn(0);
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id 		= (String) data[0];	
									String name = (String) data[1];						
									tagCompany.setTag(name);									

									enableButtons(true, false, false, false);	
									displayBankAccounts(modelBankAcct,rowHeaderBankAcct,modelBankAcct_total);
								}
							}
						});
					}	
				}
				{
					pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(8, 0, 5, 5));

					{
						tagCompany = new _JTagLabel("[ ]");
						pnlComp_a2.add(tagCompany);
						tagCompany.setBounds(209, 27, 700, 22);
						tagCompany.setEnabled(true);	
						tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
				}
			}				
		} //end of Company

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlSubTable.setBorder(lineBorder);

			{						

				pnlBankAcctList = new JPanel();
				pnlSubTable.add(pnlBankAcctList, BorderLayout.CENTER);
				pnlBankAcctList.setLayout(new BorderLayout(0,0));
				pnlBankAcctList.setBorder(lineBorder);		
				pnlBankAcctList.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlBankAcctList.setBorder(JTBorderFactory.createTitleBorder("List of Bank Accounts"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollBankAcct = new _JScrollPaneMain();
						pnlBankAcctList.add(scrollBankAcct, BorderLayout.CENTER);
						{
							modelBankAcct = new modelBankAccount();

							tblBankAcct = new _JTableMain(modelBankAcct);
							scrollBankAcct.setViewportView(tblBankAcct);
							tblBankAcct.addMouseListener(this);								
							tblBankAcct.setSortable(false);
							tblBankAcct.getColumnModel().getColumn(0).setPreferredWidth(80);
							tblBankAcct.getColumnModel().getColumn(1).setPreferredWidth(80);
							tblBankAcct.getColumnModel().getColumn(2).setPreferredWidth(250);
							tblBankAcct.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {clickTable();}							
								public void keyPressed(KeyEvent e) {clickTable();}	

							}); 
							tblBankAcct.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblBankAcct.rowAtPoint(e.getPoint()) == -1){}
									else{tblBankAcct.setCellSelectionEnabled(true);}
									clickTable();
								}
								public void mouseReleased(MouseEvent e) {
									if(tblBankAcct.rowAtPoint(e.getPoint()) == -1){}
									else{tblBankAcct.setCellSelectionEnabled(true);}
									clickTable();
								}
							});

						}
						{
							rowHeaderBankAcct = tblBankAcct.getRowHeader22();
							scrollBankAcct.setRowHeaderView(rowHeaderBankAcct);
							scrollBankAcct.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollBankAcct_total = new _JScrollPaneTotal(scrollBankAcct);
							pnlBankAcctList.add(scrollBankAcct_total, BorderLayout.SOUTH);
							{
								modelBankAcct_total = new modelBankAccount();
								modelBankAcct_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

								tblBankAcct_total = new _JTableTotal(modelBankAcct_total, tblBankAcct);
								tblBankAcct_total.setFont(dialog11Bold);
								scrollBankAcct_total.setViewportView(tblBankAcct_total);
								((_JTableTotal) tblBankAcct_total).setTotalLabel(0);
							}
						}
					}
				} 
				//end 
			}
			{
				pnlBankAcctDtls = new JPanel(new BorderLayout(5, 5));
				pnlSubTable.add(pnlBankAcctDtls, BorderLayout.SOUTH);	
				pnlBankAcctDtls.setPreferredSize(new java.awt.Dimension(733, 174));	
				pnlBankAcctDtls.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
				pnlBankAcctDtls.setBorder(lineBorder);

				{
					pnlBankAcctName = new JPanel(new BorderLayout(5, 5));
					pnlBankAcctDtls.add(pnlBankAcctName, BorderLayout.NORTH);	
					pnlBankAcctName.setPreferredSize(new java.awt.Dimension(901, 36));	
					pnlBankAcctName.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						pnlAcctLabel = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlBankAcctName.add(pnlAcctLabel, BorderLayout.WEST);	
						pnlAcctLabel.setPreferredSize(new java.awt.Dimension(82, 36));	
						pnlAcctLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblBankAcctID = new JLabel("Bank Acct. ID", JLabel.TRAILING);
							pnlAcctLabel.add(lblBankAcctID);
							lblBankAcctID.setEnabled(false);	
							lblBankAcctID.setPreferredSize(new java.awt.Dimension(107, 40));
							lblBankAcctID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}						

						pnlBankAcct = new JPanel(new BorderLayout(5, 5));
						pnlBankAcctName.add(pnlBankAcct, BorderLayout.CENTER);	
						pnlBankAcct.setPreferredSize(new java.awt.Dimension(814, 40));	
						pnlBankAcct.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

						{
							pnlBankAcct_1 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlBankAcct.add(pnlBankAcct_1, BorderLayout.WEST);	
							pnlBankAcct_1.setPreferredSize(new java.awt.Dimension(67, 28));					

							{
								lookupBankAcct = new _JLookup(null, "Bank Acct ID",0,2);
								pnlBankAcct_1.add(lookupBankAcct);
								//lookupBankAcct.setLookupSQL(getAccount());
								lookupBankAcct.setReturnColumn(0);
								lookupBankAcct.setEnabled(false);
								lookupBankAcct.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											
											
										}
									}
								});
							}																
						}
						{
							pnlBankAcct_3 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlBankAcct.add(pnlBankAcct_3, BorderLayout.CENTER);	
							pnlBankAcct_3.setPreferredSize(new java.awt.Dimension(684, 24));

							{
								TxtBankAcctNo = new JXTextField("");
								pnlBankAcct_3.add(TxtBankAcctNo);
								TxtBankAcctNo.setEnabled(false);	
								TxtBankAcctNo.setEditable(false);
								TxtBankAcctNo.setBounds(120, 25, 300, 22);	
								TxtBankAcctNo.setHorizontalAlignment(JTextField.CENTER);
								TxtBankAcctNo.setToolTipText("Bank Acount No.");
								TxtBankAcctNo.setPreferredSize(new java.awt.Dimension(292, 24));
							}
						}
						{
							pnlBankAcct_2 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlBankAcct.add(pnlBankAcct_2, BorderLayout.EAST);	
							pnlBankAcct_2.setPreferredSize(new java.awt.Dimension(380, 28));					

							{
								TxtBankAcctDesc = new JXTextField("");
								pnlBankAcct_2.add(TxtBankAcctDesc);
								TxtBankAcctDesc.setEnabled(false);	
								TxtBankAcctDesc.setEditable(false);
								TxtBankAcctDesc.setBounds(120, 25, 300, 22);	
								TxtBankAcctDesc.setToolTipText("Bank Acount Description");
								TxtBankAcctDesc.setHorizontalAlignment(JTextField.CENTER);
								TxtBankAcctDesc.setPreferredSize(new java.awt.Dimension(417, 28));
							}											
						}
					}

				}
				{
					pnlBankAcctDtls_b = new JPanel(new BorderLayout(5, 5));
					pnlBankAcctDtls.add(pnlBankAcctDtls_b, BorderLayout.CENTER);	
					pnlBankAcctDtls_b.setPreferredSize(new java.awt.Dimension(926, 114));	
					pnlBankAcctDtls_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));					

					{
						pnlAgentDtl_1 = new JPanel(new BorderLayout(0, 5));
						pnlBankAcctDtls_b.add(pnlAgentDtl_1, BorderLayout.WEST);	
						pnlAgentDtl_1.setPreferredSize(new java.awt.Dimension(210, 130));
						pnlAgentDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						{
							pnlAgentPosition = new JPanel(new GridLayout(4, 1, 0, 5));
							pnlAgentDtl_1.add(pnlAgentPosition, BorderLayout.WEST);	
							pnlAgentPosition.setPreferredSize(new java.awt.Dimension(91, 107));
							pnlAgentPosition.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

							{
								{
									lblBank = new JLabel("Bank ", JLabel.TRAILING);
									pnlAgentPosition.add(lblBank);
									lblBank.setEnabled(false);	
									lblBank.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblBankBranch = new JLabel("Bank Branch ", JLabel.TRAILING);
									pnlAgentPosition.add(lblBankBranch);
									lblBankBranch.setEnabled(false);	
									lblBankBranch.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblAcctID = new JLabel("Account ID", JLabel.TRAILING);
									pnlAgentPosition.add(lblAcctID);
									lblAcctID.setEnabled(false);	
									lblAcctID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblCommDisb = new JLabel("Comm. Disb?", JLabel.TRAILING);
									pnlAgentPosition.add(lblCommDisb);
									lblCommDisb.setEnabled(false);	
									lblCommDisb.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
							}

							pnlAgentDtl_1b = new JPanel(new GridLayout(4, 1, 0, 5));
							pnlAgentDtl_1.add(pnlAgentDtl_1b, BorderLayout.CENTER);	
							pnlAgentDtl_1b.setPreferredSize(new java.awt.Dimension(127, 107));
							pnlAgentDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

							{
								lookupBank = new _JLookup(null, "Bank",0,2);
								pnlAgentDtl_1b.add(lookupBank);
								lookupBank.setLookupSQL(getBank());
								lookupBank.setReturnColumn(0);
								lookupBank.setEnabled(false);
								lookupBank.setFilterName(true);
								lookupBank.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											tagBank.setTag((String) data[1]);	
											lookupBankBranch.setLookupSQL(getBankBranch((String) data[0]));
											lookupBankBranch.setValue(null);
											
											lblBankBranch.setEnabled(true);	
											lookupBankBranch.setEnabled(true);
											tagBankBranch.setEnabled(true);
											tagBankBranch.setTag("");
										}
									}
								});
							}			
							{
								lookupBankBranch = new _JLookup(null, "Bank Branch",0,2);
								pnlAgentDtl_1b.add(lookupBankBranch);								
								lookupBankBranch.setReturnColumn(0);
								lookupBankBranch.setEnabled(false);
								lookupBankBranch.setFilterName(true);
								lookupBankBranch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											tagBankBranch.setTag((String) data[1]);	
										}
									}
								});
							}			
							{
								lookupAccount = new _JLookup(null, "Account",0,2);
								pnlAgentDtl_1b.add(lookupAccount);
								lookupAccount.setLookupSQL(getAccount());
								lookupAccount.setReturnColumn(0);
								lookupAccount.setEnabled(false);
								lookupAccount.setFilterName(true);
								lookupAccount.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){											
											tagAccount.setTag((String) data[1]);	
										}
									}
								});
							}
							{
								String type[] = {"Yes","No"};					
								cmbCommDisb = new JComboBox(type);
								pnlAgentDtl_1b.add(cmbCommDisb);
								cmbCommDisb.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								cmbCommDisb.setBounds(537, 15, 190, 21);	
								cmbCommDisb.setEnabled(false);
								cmbCommDisb.setEditable(false);
								cmbCommDisb.setPreferredSize(new java.awt.Dimension(217, 60));
								cmbCommDisb.setSelectedIndex(0);	
							}	
						}
					}
					{
						pnlBankAcctDtlsAmt2 = new JPanel(new GridLayout(4, 1, 0, 5));
						pnlBankAcctDtls_b.add(pnlBankAcctDtlsAmt2, BorderLayout.CENTER);
						pnlBankAcctDtlsAmt2.setPreferredSize(new java.awt.Dimension(675, 116));
						pnlBankAcctDtlsAmt2.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

						{
							tagBank = new _JTagLabel("[ ]");
							pnlBankAcctDtlsAmt2.add(tagBank);
							tagBank.setBounds(209, 27, 700, 22);
							tagBank.setEnabled(false);	
							tagBank.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagBankBranch = new _JTagLabel("[ ]");
							pnlBankAcctDtlsAmt2.add(tagBankBranch);
							tagBankBranch.setBounds(209, 27, 700, 22);
							tagBankBranch.setEnabled(false);	
							tagBankBranch.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagAccount = new _JTagLabel("[ ]");
							pnlBankAcctDtlsAmt2.add(tagAccount);
							tagAccount.setBounds(209, 27, 700, 22);
							tagAccount.setEnabled(false);	
							tagAccount.setPreferredSize(new java.awt.Dimension(27, 33));
						}
					}
					{
						//Start of Left Panel 
						pnlBankAcctDtlsAmt = new JPanel(new BorderLayout(0,0));
						pnlBankAcctDtls_b.add(pnlBankAcctDtlsAmt, BorderLayout.EAST);
						pnlBankAcctDtlsAmt.setPreferredSize(new java.awt.Dimension(235, 98));
						pnlBankAcctDtlsAmt.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

						{
							pnlBankAcctDtlsAmt_1 = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlBankAcctDtlsAmt.add(pnlBankAcctDtlsAmt_1, BorderLayout.WEST);
							pnlBankAcctDtlsAmt_1.setPreferredSize(new java.awt.Dimension(97, 113));
							pnlBankAcctDtlsAmt_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{
								{
									lblType = new JLabel("Type", JLabel.TRAILING);
									pnlBankAcctDtlsAmt_1.add(lblType);
									lblType.setEnabled(false);
									lblType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblCashDeposit = new JLabel("Cash Deposit", JLabel.TRAILING);
									pnlBankAcctDtlsAmt_1.add(lblCashDeposit);
									lblCashDeposit.setEnabled(false);	
									lblCashDeposit.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblFundClass = new JLabel("Fund Class", JLabel.TRAILING);
									pnlBankAcctDtlsAmt_1.add(lblFundClass);
									lblFundClass.setEnabled(false);	
									lblFundClass.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblStatus = new JLabel("Status", JLabel.TRAILING);
									pnlBankAcctDtlsAmt_1.add(lblStatus);
									lblStatus.setEnabled(false);
									lblStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
							}

							pnlBankAcctDtlsAmt_2 = new JPanel(new BorderLayout(5,0));
							pnlBankAcctDtlsAmt.add(pnlBankAcctDtlsAmt_2, BorderLayout.CENTER);
							pnlBankAcctDtlsAmt_2.setPreferredSize(new java.awt.Dimension(265, 98));
							pnlBankAcctDtlsAmt_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

							{
								{
									pnlBankAcctDtlsAmt_2a = new JPanel(new GridLayout(4, 1, 0, 5));
									pnlBankAcctDtlsAmt_2.add(pnlBankAcctDtlsAmt_2a, BorderLayout.WEST);
									pnlBankAcctDtlsAmt_2a.setPreferredSize(new java.awt.Dimension(119, 119));
									pnlBankAcctDtlsAmt_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{
										String type[] = {"S","C"};					
										cmbType = new JComboBox(type);
										pnlBankAcctDtlsAmt_2a.add(cmbType);
										cmbType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbType.setBounds(537, 15, 190, 21);	
										cmbType.setEnabled(false);
										cmbType.setEditable(false);
										cmbType.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbType.setSelectedIndex(0);	
									}	
									{
										String cash_dep[] = {"True","False"};					
										cmbCashDep = new JComboBox(cash_dep);
										pnlBankAcctDtlsAmt_2a.add(cmbCashDep);
										cmbCashDep.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbCashDep.setBounds(537, 15, 190, 21);	
										cmbCashDep.setEnabled(false);
										cmbCashDep.setEditable(false);
										cmbCashDep.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbCashDep.setSelectedIndex(0);	
									}					
									{
										String fund_cls[] = {"01","02","03"};					
										cmbFundClass = new JComboBox(fund_cls);
										pnlBankAcctDtlsAmt_2a.add(cmbFundClass);
										cmbFundClass.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbFundClass.setBounds(537, 15, 190, 21);	
										cmbFundClass.setEnabled(false);
										cmbFundClass.setEditable(false);
										cmbFundClass.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbFundClass.setSelectedIndex(0);	
										cmbFundClass.setToolTipText("01-COLLECTING ACCOUNT ; 02-DISBURSING ACCOUNT ; 03-MONEY MARKET ");										
									}	
									{
										String status[] = {"Active","Inactive"};					
										cmbStatus = new JComboBox(status);
										pnlBankAcctDtlsAmt_2a.add(cmbStatus);
										cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbStatus.setBounds(537, 15, 190, 21);	
										cmbStatus.setEnabled(false);
										cmbStatus.setEditable(false);
										cmbStatus.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbStatus.setSelectedIndex(0);	
									}
								}							
							}
						}
					}
				}			
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

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
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
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
	public void displayBankAccounts(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"a.bank_acct_id,\r\n" + 
			"trim(a.bank_acct_no) as acct_no,\r\n" + 
			"trim(a.acct_desc) as acct_desc,\r\n" + 
			"trim(c.bank_alias) as bank,\r\n" + 
			"upper(trim(b.bank_branch_location)) as bank_branch,\r\n" + 
			"a.acct_type,\r\n" + 
			"a.acct_id,\r\n" + 
			"a.fund_class_id,\r\n" + 
			"(case when a.cash_deposit = true then 'True' else 'False' end) as cash_dep,  \n" +
			"(upper(trim(dd.first_name))||' '||upper(trim(dd.last_name))) as created_by,\r\n" + 
			"to_char(a.date_created,'MM-dd-yyyy') as date_created,\r\n" + 
			"(upper(trim(ee.first_name))||' '||upper(trim(ee.last_name))) as edited_by,\r\n" + 
			"to_char(a.date_edited,'MM-dd-yyyy') as date_edited," +
			"a.status_id," +
			"(case when is_comm_disb is null or is_comm_disb = false then 'No' else 'Yes' end)   \r\n" + 
			"\r\n" + 
			"from mf_bank_account a\r\n" + 
			"left join mf_bank_branch b on a.bank_branch_id = b.bank_branch_id\r\n" + 
			"left join mf_bank c on b.bank_id = c.bank_id\r\n" + 
			"left join em_employee d on a.created_by = d.emp_code\r\n" + 
			"left join rf_entity dd on d.entity_id = dd.entity_id  \n" +
			"left join em_employee e on a.edited_by = e.emp_code\r\n" + 
			"left join rf_entity ee on e.entity_id = ee.entity_id  \n" +
			"where a.co_id = '"+lookupCompany.getValue()+"' \n"+
			"\r\n" + 
			"order by a.bank_acct_id" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalAllComm(modelMain, modelTotal);			
		}		


		else {
			modelBankAcct_total = new modelBankAccount();
			modelBankAcct_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

			tblBankAcct_total = new _JTableTotal(modelBankAcct_total, tblBankAcct);
			tblBankAcct_total.setFont(dialog11Bold);
			scrollBankAcct_total.setViewportView(tblBankAcct_total);
			((_JTableTotal) tblBankAcct_total).setTotalLabel(0);}

		tblBankAcct.packAll();				
		tblBankAcct.getColumnModel().getColumn(0).setPreferredWidth(80);

		int row_tot = tblBankAcct.getRowCount();			
		modelBankAcct_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		adjustRowHeight_account(tblBankAcct);
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){cancel();}

		if(e.getActionCommand().equals("Add")){addAccount(); }
		
		if(e.getActionCommand().equals("Edit")){edit(); }

		if(e.getActionCommand().equals("Save")){save();}

	}

	public void mouseClicked(MouseEvent evt) {
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

	private void cancel(){

		if (btnSave.isEnabled()) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}

		}

		else {	execute_cancel(); 	}
	}

	private void execute_cancel(){

		enableFields(false);		
		enableButtons(true, false, false, false);
		refreshFields();

	}

	private void save(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete bank account details.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}

		else if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	

			if (to_do.equals("addnew")) 
			{
				saveNewBankAcct(db); 
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"A new bank account was added.","Information",JOptionPane.INFORMATION_MESSAGE);
				execute_cancel();
				displayBankAccounts(modelBankAcct,rowHeaderBankAcct,modelBankAcct_total);
				
			} 

			else if (to_do.equals("edit"))
			{				
				updateBankAccount(db); 
				db.commit();				
				JOptionPane.showMessageDialog(getContentPane(),"A bank account was successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				execute_cancel();
				displayBankAccounts(modelBankAcct,rowHeaderBankAcct,modelBankAcct_total);
			}			
		}

		else {}	



	}

	private void edit(){

		enableFields(true);
		enableButtons(false, false, true, true);
		lookupBankAcct.setEnabled(false);
		//lookupBankAcct.setEditable(false);
		
		to_do = "edit";
	}
	
	private void addAccount(){

		enableFields(true);		
		enableButtons(false, false, true, true);
		refreshFields();
		//lookupBankAcct.setEditable(false);
		lookupBankAcct.setValue(sql_getNextBankAcctID());
		lblBankBranch.setEnabled(false);	
		lookupBankBranch.setEnabled(false);
		tagBankBranch.setEnabled(false);	
		
		to_do = "addnew";
	}


	//enable, disable
	private void enableFields(boolean x){

		lblBankAcctID.setEnabled(x);	
		//lookupBankAcct.setEnabled(x);
		TxtBankAcctNo.setEnabled(x);	
		TxtBankAcctNo.setEditable(x);
		TxtBankAcctDesc.setEnabled(x);	
		TxtBankAcctDesc.setEditable(x);
		lblBank.setEnabled(x);	
		lookupBank.setEnabled(x);
		tagBank.setEnabled(x);	
		lblBankBranch.setEnabled(x);	
		lookupBankBranch.setEnabled(x);
		tagBankBranch.setEnabled(x);
		lblAcctID.setEnabled(x);	
		lookupAccount.setEnabled(x);
		tagAccount.setEnabled(x);	
		lblCashDeposit.setEnabled(x);	
		cmbCashDep.setEnabled(x);
		lblFundClass.setEnabled(x);	
		cmbFundClass.setEnabled(x);
		lblStatus.setEnabled(x);
		cmbStatus.setEnabled(x);
		lblType.setEnabled(x);
		cmbType.setEnabled(x);
		lblCommDisb.setEnabled(x);	
		cmbCommDisb.setEnabled(x);
		
	}
	
	private void refreshFields(){

		lookupBankAcct.setValue("");
		TxtBankAcctNo.setText("");
		TxtBankAcctDesc.setText("");
		lookupBank.setValue("");
		tagBank.setTag("");
		lookupBankBranch.setValue("");
		tagBankBranch.setTag("");
		lookupAccount.setValue("");
		tagAccount.setTag("");
		cmbCashDep.setSelectedIndex(0);	
		cmbFundClass.setSelectedIndex(0);	
		cmbStatus.setSelectedIndex(0);	
		cmbType.setSelectedIndex(0);	
		cmbCommDisb.setSelectedIndex(0);	
	}

	public void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);	
		btnCancel.setEnabled(d);			
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();						

		enableButtons(true, false, false, false);	
		displayBankAccounts(modelBankAcct,rowHeaderBankAcct,modelBankAcct_total);
		
		lookupCompany.setValue(co_id);
	}
	
	
	
	//select, lookup and get statements		
	public static String sql_getBankID(String bank_alias) {

		String bank_id = "";

		String SQL = 
			"select bank_id from mf_bank where trim(bank_alias) = '"+bank_alias+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			bank_id = (String) db.getResult()[0][0];
		}else{
			bank_id = "";
		}

		return bank_id;
	}

	public static String sql_getBankBranchID(String bank_branch) {

		String branch_id = "";

		String SQL = 
			"select bank_branch_id from mf_bank_branch where upper(trim(bank_branch_location)) = '"+bank_branch+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			branch_id = (String) db.getResult()[0][0];
		}else{
			branch_id = "";
		}

		return branch_id;
	}

	public static String sql_getAcct(String acct_id) {

		String acct_name = "";

		String SQL = 
			"select acct_name from mf_boi_chart_of_accounts where trim(acct_id) = '"+acct_id.trim()+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			acct_name = (String) db.getResult()[0][0];
		}else{
			acct_name = "";
		}

		return acct_name;
	}

	public String getAccount(){

		String sql = 
		"select acct_id as \"Acct ID\", " +
		"trim(acct_name) as \"Acct Name\" " +
		"from mf_boi_chart_of_accounts " +
		"where acct_id like '01-01-04%'  and status_id = 'A' " +
		"or acct_id like '01-01-05%' and status_id = 'A' or filtered = false " +//ADDED FILTER BY LESTER DCRF 2719
		"order by acct_id\r\n" ;
		return sql;

	}	
	
	public String getBank(){

		String sql = 
		"select trim(bank_id) as \"Bank ID\", \n" + 
		"		trim(bank_alias) as \"Bank Alias\",\n" + 
		"		trim(bank_name) as \"Bank Name\" \n" + 
		"		from mf_bank \n" + 
		"		where status_id = 'A' \n" + 
		"        and server_id is null\n" + 
		"		order by trim(bank_id)" ;
		return sql;

	}	
	
	public String getBankBranch(String bank){

		String sql = 
		"select bank_branch_id as \"Bank Branch ID\", " +
		"trim(bank_branch_location) as \"Address\" " +
		"from mf_bank_branch " +
		"where status_id = 'A' " +
		"and trim(bank_id) = '"+bank+"' " +
		"order by bank_branch_id \r\n" ;
		return sql;

	}	
	
	public static String sql_getNextBankAcctID() {//ok	

		String bnk_acct_id = "";
		String SQL = 
			"select distinct (trim(to_char(( select max(bank_acct_id::int) + 1 " +
			"from mf_bank_account),'00000'))) from mf_bank_account \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {bnk_acct_id = "00001";}
			else {bnk_acct_id = (String) db.getResult()[0][0]; }			
			//bnk_acct_id = (String) db.getResult()[0][0];
		}else{
			bnk_acct_id = "00001";
		}

		return bnk_acct_id;
	}
	

	//table operations	
	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	private void displayRowDetails(){

		Integer row = tblBankAcct.getSelectedRow();

		String bank_acct_id 	= "";	
		String bank_acct_no 	= "";	
		String bank_acct_name 	= "";	
		String acct_id 			= "";	
		String bank 			= "";	
		String branch 			= "";	
		String cash_dep 		= "";	
		String fund_class 		= "";	
		String status 			= "";	
		String type 			= "";	
		String is_comm_disb		= "";	

		try { bank_acct_id 	= tblBankAcct.getValueAt(row,0).toString().trim();} catch (NullPointerException e) { bank_acct_id 	= ""; }
		try { bank_acct_no 	= tblBankAcct.getValueAt(row,1).toString().trim();} catch (NullPointerException e) { bank_acct_no 	= ""; }
		try { bank_acct_name = tblBankAcct.getValueAt(row,2).toString().trim();} catch (NullPointerException e) { bank_acct_name 	= ""; }
		try { acct_id 		= tblBankAcct.getValueAt(row,6).toString().trim();} catch (NullPointerException e) { acct_id 	= ""; }
		try { bank 			= tblBankAcct.getValueAt(row,3).toString().trim();} catch (NullPointerException e) { bank 	= ""; }
		try { branch 		= tblBankAcct.getValueAt(row,4).toString().trim();} catch (NullPointerException e) { branch 	= ""; }
		try { cash_dep 		= tblBankAcct.getValueAt(row,8).toString().trim();} catch (NullPointerException e) { cash_dep 	= ""; }
		try { fund_class 	= tblBankAcct.getValueAt(row,7).toString().trim();} catch (NullPointerException e) { fund_class 	= ""; }
		try { status 		= tblBankAcct.getValueAt(row,13).toString().trim();} catch (NullPointerException e) { status 	= ""; }
		try { type 			= tblBankAcct.getValueAt(row,5).toString().trim();} catch (NullPointerException e) { type 	= ""; }
		try { is_comm_disb 	= tblBankAcct.getValueAt(row,14).toString().trim();} catch (NullPointerException e) { is_comm_disb 	= ""; }

		lookupBankAcct.setValue(bank_acct_id);
		TxtBankAcctNo.setText(bank_acct_no);
		TxtBankAcctDesc.setText(bank_acct_name);
		lookupBank.setValue(sql_getBankID(bank));
		tagBank.setTag(bank);
		lookupBankBranch.setValue(sql_getBankBranchID(branch));
		tagBankBranch.setTag(branch);
		lookupAccount.setValue(acct_id);
		tagAccount.setTag(sql_getAcct(acct_id));
		cmbCashDep.setSelectedItem(cash_dep);
		cmbFundClass.setSelectedItem(fund_class);
		cmbType.setSelectedItem(type);
		if (status.equals("A")) {cmbStatus.setSelectedIndex(0);} else {cmbStatus.setSelectedIndex(1);}
		if (is_comm_disb.equals("Yes")) {cmbCommDisb.setSelectedIndex(0);} else {cmbCommDisb.setSelectedIndex(1);}

	}

	private void clickTable(){		
		displayRowDetails();
		enableFields(false);
		enableButtons(true, true, false, true);
	}
		

	//processes and validation
	private Boolean checkCompleteDetails(){

		boolean x = false;		

		String acct_no, acct_name, bank, branch, acctid   ;

		acct_no 	= TxtBankAcctNo.getText();
		acct_name 	= TxtBankAcctDesc.getText();	
		bank 		= lookupBank.getText();	
		branch 		= lookupBankBranch.getText();	
		acctid 		= lookupAccount.getText();	

		if (acct_no.equals("") || acct_name.equals("")|| bank.equals("")|| branch.equals("")|| acctid.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}


	//save
	private void saveNewBankAcct(pgUpdate db){

		String sqlDetail = 
			"insert into mf_bank_account \n" +
			"values ( \n" +
			"'"+lookupBankBranch.getText().trim()+"',   \n" +   //1 bank_branch_id
			"'"+lookupBankAcct.getText().trim()+"',   \n" +		//2 bank_acct_id
			"'"+TxtBankAcctDesc.getText().trim()+"',   \n" +	//3 acct_desc
			"'"+cmbType.getSelectedItem()+"',   \n" +			//4 acct_type
			"'"+lookupAccount.getText().trim()+"',   \n" +		//5 acct_id
			"'"+TxtBankAcctNo.getText().trim()+"',   \n" +		//6 bank_acct_no
			"'"+cmbFundClass.getSelectedItem()+"',   \n" +		//7 fund_class_id
			"0,  \n"  +			//8 last_check_no
			"null,  " +			//9 last_deposit_no
			"''," ;			//10 old_acct_id
			if (cmbCashDep.getSelectedItem().equals("True")) {sqlDetail = sqlDetail + "true, \n";} else {sqlDetail = sqlDetail + "false, \n";}//11 cash_deposit		
			if (cmbStatus.getSelectedItem().equals("Active")) {sqlDetail = sqlDetail + "'A', \n";} else {sqlDetail = sqlDetail + "'I', \n";} //12 status_id

			sqlDetail = sqlDetail +
			"'"+UserInfo.EmployeeCode+"', \n" +  //13 created_by
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//14 date_created
			"null,  \n" +	//15 edited_by
			"null, \n" ;		//16 date_edited
			if (cmbCommDisb.getSelectedItem().equals("Yes")) {sqlDetail = sqlDetail + "true, \n";} else {sqlDetail = sqlDetail + "false, \n";} //17 is_comm_disb

			sqlDetail = sqlDetail +
			"'"+lookupCompany.getValue()+"') " ;

		System.out.printf("SQL #1 - NewAccount: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void updateBankAccount(pgUpdate db){

		String sqlDetail = 
			"update mf_bank_account set \n" +
			"bank_branch_id = '"+lookupBankBranch.getText().trim()+"',   \n" +
			"acct_desc = '"+TxtBankAcctDesc.getText().trim()+"',   \n" +
			"acct_type = '"+cmbType.getSelectedItem()+"',   \n" +
			"acct_id = '"+lookupAccount.getText().trim()+"',   \n" +
			"bank_acct_no = '"+TxtBankAcctNo.getText().trim()+"',   \n" +
			"fund_class_id = '"+cmbFundClass.getSelectedItem()+"',   \n" ;
		
			if (cmbStatus.getSelectedItem().equals("Active")) {sqlDetail = sqlDetail + "status_id = 'A', \n";} 
			else {sqlDetail = sqlDetail + "status_id = 'I', \n";}

			sqlDetail = sqlDetail +
			"edited_by = '"+UserInfo.EmployeeCode+"', \n" +
			"date_edited = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" ;
			
			if (cmbCashDep.getSelectedItem().equals("True")) {sqlDetail = sqlDetail + "cash_deposit = true, \n";} 
			else {sqlDetail = sqlDetail + "cash_deposit = false, \n";}
			
			if (cmbCommDisb.getSelectedItem().equals("Yes")) {sqlDetail = sqlDetail + "is_comm_disb = true \n";} 
			else {sqlDetail = sqlDetail + "is_comm_disb =  false \n";}
			
			sqlDetail = sqlDetail +		
			
			"where bank_acct_id = '"+lookupBankAcct.getText().trim()+"'  ";
			

		System.out.printf("SQL #1 - updateBankAccount: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}






}
