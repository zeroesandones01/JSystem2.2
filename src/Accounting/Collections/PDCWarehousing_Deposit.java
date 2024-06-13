package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.channels.SelectableChannel;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelDepositAccountEntries;
import tablemodel.model_checks_pdc_warehousing;

public class PDCWarehousing_Deposit extends JXPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = 7281223722968175346L;
	private static Font font9 = FncLookAndFeel.systemFont_Plain.deriveFont(8f);
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	static checkStatusMonitoring_pdcWarehousing pdcw; 
	
	private static _JLookup txtDepNo;
	private static _JLookup txtBranch;
	private static _JLookup txtBankAcctNo; 
	
	private static JTextField txtDepStatus;  
	private static JTextField txtBankAcct; 
	private static JTextField txtBank;
	private static JTextField txtBankBranch;
	
	private static _JDateChooser dteDep;
	private static _JDateChooser dteCol;  
	
	private static JXPanel panMain; 
	
	private static JScrollPane scrTab;
	private static model_checks_pdc_warehousing modelWith;	
	public static _JTableMain tblWith;
	public static JList rowWith;
	
	private static JScrollPane scrJV;
	private static modelDepositAccountEntries modelJV;	
	public static _JTableMain tblJV;
	public static JList rowJV;
	
	private static JXPanel panPage; 
	private static JXPanel panTabList;
	private static JXPanel panJV;
	
	private static String strCurr = "";
	private static String strReference = "";
	
	private static JButton[] btnDep; 
	
	private Boolean blnEdit = false; 
	
	public PDCWarehousing_Deposit(checkStatusMonitoring_pdcWarehousing main_pdcw) {
		this.pdcw = main_pdcw; 
		initGUI(); 
	}

	public PDCWarehousing_Deposit(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public PDCWarehousing_Deposit(LayoutManager layout) {
		super(layout);

	}

	public PDCWarehousing_Deposit(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		
		panMain = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		{
			{
				panPage = new JXPanel(new GridLayout(4, 2, 5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 150));
				panPage.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panDeposit = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panDeposit); 
						{
							{
								JLabel lblDep = new JLabel("Deposit No."); 
								panDeposit.add(lblDep, BorderLayout.LINE_START);
								lblDep.setHorizontalAlignment(JLabel.LEFT);
								lblDep.setPreferredSize(new Dimension(100, 0));
								lblDep.setFont(font11);
							}
							{
								JXPanel panDep = new JXPanel(new BorderLayout(5, 5)); 
								panDeposit.add(panDep, BorderLayout.CENTER); 
								{
									{
										txtDepNo = new _JLookup(""); 
										panDep.add(txtDepNo, BorderLayout.CENTER); 
										txtDepNo.setHorizontalAlignment(_JLookup.CENTER);
										txtDepNo.setFont(font11);
										txtDepNo.setReturnColumn(0);
										try {
											txtDepNo.setLookupSQL(lookupMethods.getDepositNo(checkStatusMonitoring_pdcWarehousing.txtCoID.getValue()));
										} catch (NullPointerException ex) {
											txtDepNo.setLookupSQL(lookupMethods.getDepositNo(""));
										}
										txtDepNo.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												
												if (data!=null) {
													dteDep.setDate(FncGlobal.dateFormat(data[2].toString()));
													dteCol.setDate(FncGlobal.dateFormat(data[3].toString()));
													txtBank.setText(data[5].toString());
													txtBankBranch.setText(data[6].toString());
													
													pgSelect dbExec = new pgSelect(); 
													dbExec.select("select a.bank_acct_id, b.bank_acct_no, c.branch_name\n" + 
															"from cs_dp_header a\n" + 
															"inner join mf_bank_account b on a.bank_acct_id = b.bank_acct_id\n" + 
															"inner join mf_office_branch c on a.branch_id = c.branch_id\n" + 
															"where a.dep_no = '"+txtDepNo.getValue()+"'");
													
													txtBankAcctNo.setValue(dbExec.Result[0][0].toString());
													txtBankAcct.setText(dbExec.Result[0][1].toString());
													txtBranch.setValue(dbExec.Result[0][2].toString());
													txtDepStatus.setText(data[8].toString());
													
													if (data[8].toString().equals("ACTIVE")) {
														Control("not_posted"); 
													} else if (data[8].toString().equals("POSTED")) {
														Control("posted"); 
													}
													
													LoadDeposit(); 
												}
											}
										});
									}
									{
										txtDepStatus = new JTextField(""); 
										panDep.add(txtDepStatus, BorderLayout.LINE_END); 
										txtDepStatus.setHorizontalAlignment(_JLookup.CENTER);
										txtDepStatus.setFont(font11);
										txtDepStatus.setEditable(false);
										txtDepStatus.setPreferredSize(new Dimension(75, 0));
									}
								}
							}
						}
					}
					{
						JXPanel panBankAcctNo = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panBankAcctNo); 
						{
							{
								JLabel lblBankAcctNo = new JLabel("Bank Acct. No."); 
								panBankAcctNo.add(lblBankAcctNo, BorderLayout.LINE_START);
								lblBankAcctNo.setHorizontalAlignment(JLabel.RIGHT);
								lblBankAcctNo.setPreferredSize(new Dimension(100, 0));
								lblBankAcctNo.setFont(font11);
							}
							{
								txtBankAcctNo = new _JLookup(""); 
								panBankAcctNo.add(txtBankAcctNo, BorderLayout.CENTER); 
								txtBankAcctNo.setHorizontalAlignment(_JLookup.CENTER);
								txtBankAcctNo.setFont(font11);
								txtBankAcctNo.setReturnColumn(0);
								txtBankAcctNo.setLookupSQL(lookupMethods.getBankAcct());
								txtBankAcctNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if (data!=null) {
											pgSelect dbExec = new pgSelect();
											dbExec.select("select a.bank_acct_no, c.bank_name, b.bank_branch_location\n" + 
													"from mf_bank_account a\n" + 
													"inner join mf_bank_branch b on a.bank_branch_id = b.bank_branch_id\n" + 
													"inner join mf_bank c on b.bank_id = c.bank_id\n" + 
													"where a.bank_acct_id = '"+txtBankAcctNo.getValue()+"'");
											
											txtBankAcct.setText(dbExec.Result[0][0].toString());
											txtBank.setText(dbExec.Result[0][1].toString());
											txtBankBranch.setText(dbExec.Result[0][2].toString());
										}
									}
								});
							}
						}
					}
					{
						JXPanel panBranch = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panBranch); 
						{
							{
								JLabel lblBranch = new JLabel("Branch"); 
								panBranch.add(lblBranch, BorderLayout.LINE_START);
								lblBranch.setHorizontalAlignment(JLabel.LEFT);
								lblBranch.setPreferredSize(new Dimension(100, 0));
								lblBranch.setFont(font11);
							}
							{
								txtBranch = new _JLookup(""); 
								panBranch.add(txtBranch, BorderLayout.CENTER); 
								txtBranch.setHorizontalAlignment(_JLookup.CENTER);
								txtBranch.setFont(font11);
								txtBranch.setReturnColumn(1);
								txtBranch.setLookupSQL(lookupMethods.getOfficeBranch());
								txtBranch.setHorizontalAlignment(_JLookup.CENTER);
								txtBranch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if (data!=null) {

										}
									}
								});
								txtBranch.getDocument().addDocumentListener(new DocumentListener() {
									public void removeUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									public void insertUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									public void changedUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									void ChangeFont() {
										if (txtBranch.getValue().length()>30) {
											txtBranch.setFont(font9);
										} else {
											txtBranch.setFont(font11);
										}
									}
								});
							}
						}
					}
					{
						JXPanel panBankAcct = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panBankAcct); 
						{
							{
								JLabel lblBankAcct = new JLabel("Bank Acct."); 
								panBankAcct.add(lblBankAcct, BorderLayout.LINE_START);
								lblBankAcct.setHorizontalAlignment(JLabel.RIGHT);
								lblBankAcct.setPreferredSize(new Dimension(100, 0));
								lblBankAcct.setFont(font11);
							}
							{
								txtBankAcct = new JTextField(""); 
								panBankAcct.add(txtBankAcct, BorderLayout.CENTER); 
								txtBankAcct.setHorizontalAlignment(_JLookup.CENTER);
								txtBankAcct.setFont(font11);
								txtBankAcct.setEditable(false);
							}
						}
					}
					{
						JXPanel panDepositDate = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panDepositDate); 
						{
							{
								JLabel lblDepositDate = new JLabel("Deposit Date"); 
								panDepositDate.add(lblDepositDate, BorderLayout.LINE_START);
								lblDepositDate.setHorizontalAlignment(JLabel.LEFT);
								lblDepositDate.setPreferredSize(new Dimension(100, 0));
								lblDepositDate.setFont(font11);
							}
							{
								dteDep = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panDepositDate.add(dteDep);
								dteDep.getCalendarButton().setVisible(true);
								dteDep.getCalendarButton().setEnabled(false);
								dteDep.setFont(font11);
							}
						}
					}
					{
						JXPanel panBank = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panBank); 
						{
							{
								JLabel lblBankAcct = new JLabel("Bank"); 
								panBank.add(lblBankAcct, BorderLayout.LINE_START);
								lblBankAcct.setHorizontalAlignment(JLabel.RIGHT);
								lblBankAcct.setPreferredSize(new Dimension(100, 0));
								lblBankAcct.setFont(font11);
							}
							{
								txtBank = new JTextField(""); 
								panBank.add(txtBank, BorderLayout.CENTER); 
								txtBank.setHorizontalAlignment(_JLookup.CENTER);
								txtBank.setFont(font11);
								txtBank.setEditable(false);
								txtBank.getDocument().addDocumentListener(new DocumentListener() {
									public void removeUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									public void insertUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									public void changedUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									void ChangeFont() {
										if (txtBank.getText().length()>30) {
											txtBank.setFont(font9);
										} else {
											txtBank.setFont(font11);
										}
									}
								});
							}
						}
					}
					{
						JXPanel panCollectionDate = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panCollectionDate); 
						{
							{
								JLabel lblCollectionDate = new JLabel("Collection Date"); 
								panCollectionDate.add(lblCollectionDate, BorderLayout.LINE_START);
								lblCollectionDate.setHorizontalAlignment(JLabel.LEFT);
								lblCollectionDate.setPreferredSize(new Dimension(100, 0));
								lblCollectionDate.setFont(font11);
							}
							{
								dteCol = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panCollectionDate.add(dteCol);
								dteCol.getCalendarButton().setVisible(true);
								dteCol.getCalendarButton().setEnabled(false);
								dteCol.setFont(font11);
							}
						}
					}
					{
						JXPanel panBankBranch = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panBankBranch); 
						{
							{
								JLabel lblBankBranch = new JLabel("Bank Branch"); 
								panBankBranch.add(lblBankBranch, BorderLayout.LINE_START);
								lblBankBranch.setHorizontalAlignment(JLabel.RIGHT);
								lblBankBranch.setPreferredSize(new Dimension(100, 0));
								lblBankBranch.setFont(font11);
							}
							{
								txtBankBranch = new JTextField(""); 
								panBankBranch.add(txtBankBranch, BorderLayout.CENTER); 
								txtBankBranch.setHorizontalAlignment(_JLookup.CENTER);
								txtBankBranch.setFont(font11);
								txtBankBranch.setEditable(false);
								txtBankBranch.getDocument().addDocumentListener(new DocumentListener() {
									public void removeUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									public void insertUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									public void changedUpdate(DocumentEvent e) {
										ChangeFont(); 
									}
									
									void ChangeFont() {
										if (txtBankBranch.getText().length()>30) {
											txtBankBranch.setFont(font9);
										} else {
											txtBankBranch.setFont(font11);
										}
									}
								});
							}
						}
					}
				}
			}
			{
				JTabbedPane tabDeposit = new JTabbedPane(); 
				panMain.add(tabDeposit, BorderLayout.CENTER); 
				tabDeposit.setFont(font11);
				tabDeposit.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					CreateTable();
					CreateJVTable(); 
					tabDeposit.add("           Check List           ", panTabList);
					tabDeposit.add("           JV Entries           ", panJV); 
				}
			}
			{
				JXPanel panButton = new JXPanel(new GridLayout(1, 5, 5, 5)); 
				panMain.add(panButton, BorderLayout.PAGE_END); 
				panButton.setPreferredSize(new Dimension(0, 30));
				{
					String[] strButtons = new String[] {
						"Add", 
						"Edit",
						"Delete",
						"Save",
						"Preview", 
						"Post",
						"Cancel"
					}; 
					
					btnDep = new JButton[strButtons.length]; 
					
					for (int x=0; x<strButtons.length; x++) {
						btnDep[x] = new JButton(strButtons[x]); 
						panButton.add(btnDep[x]);
						btnDep[x].setFont(font11);
						btnDep[x].setActionCommand(strButtons[x]);
						btnDep[x].addActionListener(this);
					}
				}
			}
		}
		
		Control("default"); 
		Input(false);
	}
	
	private static KeyListener keyListener = new KeyListener() {

		public void keyTyped(KeyEvent e) {

		}
		
		public void keyReleased(KeyEvent e) {

		}
		
		public void keyPressed(KeyEvent e) {
			LoadList(); 
			tblWith.changeSelection(0, 0, false, false);
		}
	};

    public static void LoadList() {
		FncGlobal.startProgress("Refreshing...");

		FncTables.clearTable(modelWith); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowWith.setModel(listModel); 

		String strSQL = "select false as tag, * \n" +
				"from view_check_for_pdc_warehousing('"+checkStatusMonitoring_pdcWarehousing.txtCoID.getValue()+"', '"+checkStatusMonitoring_pdcWarehousing.txtProID.getValue()+"', '', '"+05+"')";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelWith.addRow(db.getResult()[x]);
				listModel.addElement(modelWith.getRowCount());
			}			
		}

		FncGlobal.stopProgress();
    }

	public void actionPerformed(ActionEvent e) {
		System.out.println("e: "+e.getActionCommand());
		
		switch(e.getActionCommand()) {
			case "Add":
				blnEdit = false; 
				Input(true); 
				Control("add"); 
				ClearInput(); 
				
				LoadDeposit();
				break;
			case "Edit":
				blnEdit = true; 
				JOptionPane.showMessageDialog(null, "For changes involving the selected checks, \nplease call the JST. Inclusion and/or removal of checks \nalready included in the deposit is not yet allowed.");
				
				Input(true); 
				Control("edit"); 
				break;
			case "Delete":
				blnEdit = false; 
				
				if ((JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?", "Confirm Delete", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)) {
					Delete(); 
				}
				
				Input(false); 
				Control("default");
				ClearInput(); 
				break;
			case "Save":
				
				Boolean blnSelected = false;
				
				for (int x=0; x<modelWith.getRowCount(); x++) {
					if ((Boolean) modelWith.getValueAt(x, 0)) {
						blnSelected = true;
					}
				}
				
				if (blnSelected) {
					try {
						if ((JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?", "Confirm Save", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)) {
							Save(); 
							LoadDeposit();
							Input(false); 
							Control("not_posted");
							
							if (blnEdit) {
								JOptionPane.showMessageDialog(null, "Deposit modified");
							} else {
								JOptionPane.showMessageDialog(null, "Deposit created");	
							}	
						}
					} catch (NullPointerException ex) {
						JOptionPane.showMessageDialog(null, "Some values may not have been supplied.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "You did not select any row.");
				}
				
				blnEdit = false; 
				break;
			case "Post":
				blnEdit = false; 

				if ((JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?", "Confirm Post", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)) {
					Post();
				}
				
				Input(false); 
				Control("posted"); 
				break;
			default:
				blnEdit = false; 
				
				Input(false);
				Control("default");
				ClearInput(); 
				LoadList();
				break;
		}
	}
	
	private void Control(String strState) {
		if (strState.equals("default")) {
			btnDep[0].setEnabled(true);
			btnDep[1].setEnabled(false);
			btnDep[2].setEnabled(false);
			btnDep[3].setEnabled(false);
			btnDep[4].setEnabled(false);
			btnDep[5].setEnabled(false);
			btnDep[6].setEnabled(false);
		} else if (strState.equals("add")) {
			btnDep[0].setEnabled(false);
			btnDep[1].setEnabled(false);
			btnDep[2].setEnabled(false);
			btnDep[3].setEnabled(true);
			btnDep[4].setEnabled(false);
			btnDep[5].setEnabled(false);
			btnDep[6].setEnabled(true);
		} else if (strState.equals("edit")) {
			btnDep[0].setEnabled(false);
			btnDep[1].setEnabled(false);
			btnDep[2].setEnabled(false);
			btnDep[3].setEnabled(true);
			btnDep[4].setEnabled(false);
			btnDep[5].setEnabled(false);
			btnDep[6].setEnabled(true);			
		} else if (strState.equals("not_posted")) {
			btnDep[0].setEnabled(true);
			btnDep[1].setEnabled(true);
			btnDep[2].setEnabled(true);
			btnDep[3].setEnabled(false);
			btnDep[4].setEnabled(true);
			btnDep[5].setEnabled(true);
			btnDep[6].setEnabled(false);
		} else if (strState.equals("posted")) {
			btnDep[0].setEnabled(true);
			btnDep[1].setEnabled(false);
			btnDep[2].setEnabled(false);
			btnDep[3].setEnabled(false);
			btnDep[4].setEnabled(true);
			btnDep[5].setEnabled(false);
			btnDep[6].setEnabled(false);
		}
	}
	
	private void Input(Boolean blnRev) {
		txtDepNo.setEnabled(!blnRev);
		txtBankAcctNo.setEnabled(blnRev);
		txtBranch.setEnabled(blnRev);
		dteDep.getCalendarButton().setEnabled(blnRev);
		dteCol.getCalendarButton().setEnabled(blnRev);
	}
	
	public void CreateTable() {
		panTabList = new JXPanel(new GridLayout(1, 1, 0, 0));
		panTabList.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			panTabList.add(scrTab, BorderLayout.CENTER);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelWith = new model_checks_pdc_warehousing();
				modelWith.setEditable(false);
				
				tblWith = new _JTableMain(modelWith);
				tblWith.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							try {
								//FirstRow(tblWith.getSelectedRow());
								strCurr = FncGlobal.GetString("select b.checkstat_desc \n" + 
										"from rf_check_history a \n" + 
										"inner join mf_check_status b on a.new_checkstat_id = b.checkstat_id \n" + 
										"where a.pay_rec_id::int = '"+modelWith.getValueAt(tblWith.getSelectedRow(), 11)+"'::int \n" + 
										"order by a.date_created desc, b.sequence desc");  
								strReference = (String) modelWith.getValueAt(tblWith.getSelectedRow(), 10); 
							} catch (ArrayIndexOutOfBoundsException ex) {
								
							}
						}
					}
				});

				//tblWith.addMouseListener(this);
				rowWith = tblWith.getRowHeader();
				scrTab.setViewportView(tblWith);
				
				tblWith.getColumnModel().getColumn(0).setPreferredWidth(30);
				tblWith.getColumnModel().getColumn(1).setPreferredWidth(200);
				tblWith.getColumnModel().getColumn(2).setPreferredWidth(105);
				tblWith.getColumnModel().getColumn(3).setPreferredWidth(105);
				tblWith.getColumnModel().getColumn(4).setPreferredWidth(85);
				tblWith.getColumnModel().getColumn(8).setPreferredWidth(85);
				tblWith.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));
				
				rowWith = tblWith.getRowHeader();
				rowWith.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowWith);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblWith.hideColumns("Bank", "Branch", "Date", "Account No", "BRSTN", "ref_no");
				
				tblWith.addKeyListener(keyListener);
			}
		}
	}
	
	
	public void CreateJVTable() {
		panJV = new JXPanel(new GridLayout(1, 1, 0, 0));
		panJV.setOpaque(isOpaque());
		{
			scrJV = new JScrollPane();
			panJV.add(scrJV, BorderLayout.CENTER);
			scrJV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelJV = new modelDepositAccountEntries();
				modelJV.setEditable(false);
				
				tblJV = new _JTableMain(modelJV);
				tblJV.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							try {
								//FirstRow(tblWith.getSelectedRow());
								strCurr = FncGlobal.GetString("select b.checkstat_desc \n" + 
										"from rf_check_history a \n" + 
										"inner join mf_check_status b on a.new_checkstat_id = b.checkstat_id \n" + 
										"where a.pay_rec_id::int = '"+modelWith.getValueAt(tblWith.getSelectedRow(), 11)+"'::int \n" + 
										"order by a.date_created desc, b.sequence desc");  
								strReference = (String) modelWith.getValueAt(tblWith.getSelectedRow(), 10); 
							} catch (ArrayIndexOutOfBoundsException ex) {
								
							}
						}
					}
				});

				//tblWith.addMouseListener(this);
				rowJV = tblJV.getRowHeader();
				scrJV.setViewportView(tblJV);
				
				tblJV.getColumnModel().getColumn(0).setPreferredWidth(125);
				tblJV.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblJV.getColumnModel().getColumn(2).setPreferredWidth(50);
				tblJV.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblJV.getColumnModel().getColumn(4).setPreferredWidth(50);
				tblJV.getColumnModel().getColumn(5).setPreferredWidth(50);
				tblJV.getColumnModel().getColumn(6).setPreferredWidth(240);
				tblJV.getColumnModel().getColumn(7).setPreferredWidth(100);
				tblJV.getColumnModel().getColumn(8).setPreferredWidth(100);
				
				rowJV = tblJV.getRowHeader();
				rowJV.setModel(new DefaultListModel());
				scrJV.setRowHeaderView(rowJV);
				scrJV.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblJV.hideColumns("Bank", "Branch", "Check Date", "Account No", "BRSTN", "ref_no");
				tblJV.addKeyListener(keyListener);
			}
		}
	}
	
	private void ClearInput() {
		txtDepNo.setValue("");
		txtDepStatus.setText("");
		txtBranch.setValue("");
		txtBankAcctNo.setValue("");
		
		txtBankAcct.setText("");
		txtBank.setText("");
		txtBankBranch.setText("");
		
		dteDep.setDate(null);
		dteCol.setDate(null);
		
		LoadDeposit(); 
	}
	
	private void LoadDeposit() {
		FncGlobal.startProgress("Refreshing...");

		FncTables.clearTable(modelWith); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowWith.setModel(listModel); 

		String strSQL = "select (case when '"+txtDepNo.getValue()+"' != '' then true else false end) as tag, * \n" +
				"from view_check_for_pdc_warehousing('"+txtDepNo.getValue()+"')";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelWith.addRow(db.getResult()[x]);
				listModel.addElement(modelWith.getRowCount());
			}			
		}

		FncGlobal.stopProgress();
	}
	
	private void Save() {
		pgUpdate dbExec = new pgUpdate(); 
		
		String strJV = lookupMethods.getJV(dteDep.getDate().toString()); 
		String strDep = FncGlobal.GetString("select trim(to_char(max(dep_no::int) + 1,'00000000')) from cs_dp_header"); 
		String strOfficeBranch = FncGlobal.GetString("select branch_id from mf_office_branch where branch_name = '"+txtBranch.getValue()+"'");

		pgSelect dbBank = new pgSelect();
		dbBank.select("select b.bank_id, a.bank_branch_id\n" + 
				"from mf_bank_account a\n" + 
				"inner join mf_bank_branch b on a.bank_branch_id = b.bank_branch_id\n" + 
				"inner join mf_bank c on b.bank_id = c.bank_id\n" + 
				"where a.bank_acct_id = '"+txtBankAcctNo.getValue()+"'");
		
		String strBankID = dbBank.Result[0][0].toString(); 
		String strBankBranch = dbBank.Result[0][1].toString(); 
		
		if (!blnEdit) {
			txtDepNo.setValue(strDep);
			dbExec.executeUpdate("insert into cs_dp_header values (" +
					"'"+checkStatusMonitoring_pdcWarehousing.txtCoID.getValue()+"', \n" +
					"'"+strDep+"', '"+dteDep.getDate()+"', '"+dteCol.getDate()+"', \n" + 
					"'"+txtBankAcctNo.getValue()+"', '"+strOfficeBranch+"', '"+strJV+"', \n" +
					"'', null, 'A', '"+UserInfo.EmployeeCode+"', now(), '', null);", true);
				
			for (int x=0; x<modelWith.getRowCount(); x++) {
				if ((Boolean) modelWith.getValueAt(x, 0)) {
					dbExec.executeUpdate("insert into cs_dp_chk_detail values (\n" + 
						"'"+strDep+"', "+x+1+", " + 
						modelWith.getValueAt(x, 4).toString()+", \n" + 
						"'"+strBankID+"', '"+strBankBranch+"', \n" + 
						"'"+modelWith.getValueAt(x, 2).toString()+"', \n" + 
						"'"+modelWith.getValueAt(x, 8).toString()+"', \n" + 
						"'"+modelWith.getValueAt(x, 11).toString()+"', \n" + 
						"'A', '"+UserInfo.EmployeeCode+"', now(), '', null);", true);
					
					dbExec.executeUpdate("update rf_payments \n" + 
							"set check_stat_id = '02' \n" + 
							"where pay_rec_id::int = '"+modelWith.getValueAt(x, 11)+"'::int", true); 
					dbExec.executeUpdate("insert into rf_check_history (pay_rec_id, prev_checkstat_id, new_checkstat_id, trans_date, \n" + 
							"dep_no, status_id, created_by, date_created) \n" + 
							"values ('"+modelWith.getValueAt(x, 11)+"', '05', '02', '"+dteDep.getDate()+"', '"+strDep+"', 'A', '"+UserInfo.EmployeeCode+"', '"+dteDep.getDate()+"'); ", true); 
				}
			}	
		} else {
			dbExec.executeUpdate("update cs_dp_header \n" + 
					"set dep_date = '"+dteDep.getDate()+"'::date, cash_date = '"+dteCol.getDate()+"'::date, bank_acct_id = '"+txtBankAcctNo.getValue()+"', \n" +
					"branch_id = '"+strOfficeBranch+"', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" + 
					"where dep_no = '"+txtDepNo.getValue()+"' and co_id = '"+checkStatusMonitoring_pdcWarehousing.txtCoID.getValue()+"'; ", true);
		}
		
		dbExec.commit();
	}
	
	private void Post() {
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("update cs_dp_header\n" + 
				"set post_date = now(), status_id = 'P'\n" + 
				"where dep_no = '"+txtDepNo.getValue()+"' \n" +
				"and co_id = '"+checkStatusMonitoring_pdcWarehousing.txtCoID.getValue()+"'", false);
		dbExec.commit();
	}
	
	private void Delete() {
		pgUpdate dbExec = new pgUpdate();
		
		try {
			dbExec.executeUpdate("update cs_dp_chk_detail \n" +
					"set status_id = 'I' \n" +
					"where dep_no = '"+txtDepNo.getValue()+"'", true);
			
			dbExec.executeUpdate("update cs_dp_header \n" +
					"set status_id = 'I' \n" +
					"where dep_no = '"+txtDepNo.getValue()+"'", true);
			
			for (int x=0; x<modelWith.getRowCount(); x++) {
				dbExec.executeUpdate("update rf_payments \n" + 
						"set check_stat_id = '05' \n" + 
						"where pay_rec_id::int = '"+modelWith.getValueAt(x, 11).toString()+"'::int \n" +
						"and check_no = '"+modelWith.getValueAt(x, 2).toString()+"'", true);
				
				dbExec.executeUpdate("delete from rf_check_history x\n" + 
						"where x.pay_rec_id::int = '"+modelWith.getValueAt(x, 11).toString()+"'::int and x.new_checkstat_id = '02'\n" + 
						"and x.date_created::date = (select max(date_created::date) from rf_check_history y \n" + 
						"where y.pay_rec_id::int = x.pay_rec_id::int and y.new_checkstat_id = x.new_checkstat_id)", true);
			}
			dbExec.commit();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Something went wrong. Call the JST.");
			dbExec.rollback();
		}
	}
}
