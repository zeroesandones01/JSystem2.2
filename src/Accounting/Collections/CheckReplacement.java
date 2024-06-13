package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import tablemodel.modelMBTC_2;
import tablemodel.model_CheckReplacement;
import Buyers.ClientServicing._ChecksReplacementAndWithdrawal;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class CheckReplacement extends _JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 2039498068602979354L;
	static String title = "Check Replacement";
	Dimension frameSize = new Dimension(750, 405);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private JLabel lblName;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeq;
	
	static _JLookup txtID;
	
	static JTextField txtProjID;
	static JTextField txtUnitID;
	static JTextField txtName;
	static JTextField txtProject;
	static JTextField txtUnit;
	static JTextField txtSeq;
	
	static _JLookup txtBankID;
	static _JLookup txtBranchID;
	
	static JTextField txtBank;
	static JTextField txtBranch;
	static JTextField txtCheckNo;
	static _JDateChooser dteCheckDate;
	static JTextField txtAcctNo;
	static JTextField txtBRSTN;
	
	private JLabel lblBank;
	private JLabel lblBranch;
	private JLabel lblCheckNo;
	private JLabel lblCheckDate;
	private JLabel lblAcctNo;
	private JLabel lblBRSTN;
	
	private JButton btnReplace;
	private JButton btnSave;
	private JButton btnCancel;
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	private model_CheckReplacement modelCRPL;	
	public static _JTableMain tblCRPL;
	public static JList rowCRPL;
	
	private _ChecksReplacementAndWithdrawal crw;
	
	public CheckReplacement() {
		super(title, true, true, false, true);
		InitGUI();
	}

	public CheckReplacement(String title) {
		super(title);
		InitGUI();
	}

	public CheckReplacement(String title, boolean resizable, boolean closable,
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
			JXPanel panZero = new JXPanel(new BorderLayout(5, 5));
			pnlMain.add(panZero, BorderLayout.PAGE_START);
			{
				JXPanel pnlPageStart = new JXPanel(new BorderLayout(5, 5));
				panZero.add(pnlPageStart, BorderLayout.LINE_START);
				pnlPageStart.setPreferredSize(new Dimension(550, 120));
				pnlPageStart.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel pnlPSLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
					pnlPageStart.add(pnlPSLabel, BorderLayout.LINE_START);
					pnlPSLabel.setPreferredSize(new Dimension(190, 0));
					{
						lblName = new JLabel("Name");
						pnlPSLabel.add(lblName);
						lblName.setHorizontalAlignment(JTextField.LEADING);
						
						txtID = new _JLookup("");
						pnlPSLabel.add(txtID);
						txtID.setHorizontalAlignment(JTextField.CENTER);
						txtID.setReturnColumn(0);
						txtID.setLookupSQL(crw.Client());
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
										
										if (LoadChecks(modelCRPL, rowCRPL, data[0].toString())) {
											bState(1);
										} else {
											bState(2);
										}
									}
								}
								
								catch(NullPointerException e) {
									System.out.println("Null pointer caught during lookup.");
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
							pnlSeq.setPreferredSize(new Dimension(200, 0));
							{
								lblSeq = new JLabel("Sequence");
								pnlSeq.add(lblSeq, BorderLayout.CENTER);
								lblSeq.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								txtSeq = new JTextField("");
								pnlSeq.add(txtSeq, BorderLayout.LINE_END);
								txtSeq.setPreferredSize(new Dimension(100, 0));
								txtSeq.setHorizontalAlignment(JTextField.CENTER);
								txtSeq.setEditable(false);
							}
						}
					}
				}	
			}
			{
				btnReplace = new JButton("Replace");
				panZero.add(btnReplace);
				btnReplace.setEnabled(false);
				btnReplace.setActionCommand("Replace");
				btnReplace.addActionListener(this);
			}
		}
		{
			JXPanel pnlPageEnd = new JXPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlPageEnd, BorderLayout.CENTER);
			pnlPageEnd.setPreferredSize(new Dimension(0, 125));
			{
				JXPanel pnlBank = new JXPanel(new BorderLayout(5, 5));
				pnlPageEnd.add(pnlBank, BorderLayout.CENTER);
				pnlBank.setBorder(JTBorderFactory.createTitleBorder("Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					CreateTable();
					pnlBank.add(pnlTab);
				}
				JXPanel pnlNewDet = new JXPanel(new GridLayout(6, 1, 5, 5));
				pnlPageEnd.add(pnlNewDet, BorderLayout.LINE_END);
				pnlNewDet.setBorder(JTBorderFactory.createTitleBorder("New Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				pnlNewDet.setPreferredSize(new Dimension(300, 0));
				{
					JXPanel pnlNewBank = new JXPanel(new BorderLayout(5, 5));
					pnlNewDet.add(pnlNewBank, BorderLayout.CENTER);
					{
						JXPanel pnlNewBankLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
						pnlNewBank.add(pnlNewBankLabel, BorderLayout.LINE_START);
						pnlNewBankLabel.setPreferredSize(new Dimension(110, 0));
						{
							lblBank = new JLabel("Bank: ");
							pnlNewBankLabel.add(lblBank, BorderLayout.CENTER);
							lblBank.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtBankID = new _JLookup();
							pnlNewBankLabel.add(txtBankID);
							txtBankID.setHorizontalAlignment(JTextField.CENTER);
							txtBankID.setLookupSQL("");
							txtBankID.setReturnColumn(0);
							txtBankID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									
									if (data != null) {
										
									}
								}
							});
							txtBankID.setEnabled(false);
						}
						{
							txtBank = new JTextField();
							pnlNewBank.add(txtBank, BorderLayout.CENTER);
							txtBank.setHorizontalAlignment(JTextField.CENTER);
							txtBank.setEditable(false);
						}
					}
					JXPanel pnlNewBranch = new JXPanel(new BorderLayout(5, 5));
					pnlNewDet.add(pnlNewBranch, BorderLayout.CENTER);
					{
						JXPanel pnlNewBranchLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
						pnlNewBranch.add(pnlNewBranchLabel, BorderLayout.LINE_START);
						pnlNewBranchLabel.setPreferredSize(new Dimension(110, 0));
						{
							lblBranch = new JLabel("Branch: ");
							pnlNewBranchLabel.add(lblBranch, BorderLayout.CENTER);
							lblBranch.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtBranchID = new _JLookup();
							pnlNewBranchLabel.add(txtBranchID);
							txtBranchID.setHorizontalAlignment(JTextField.CENTER);
							txtBranchID.setLookupSQL("");
							txtBranchID.setReturnColumn(0);
							txtBranchID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									
									if (data != null) {
										
									}
								}
							});
							txtBranchID.setEnabled(false);
						}
						{
							txtBranch = new JTextField();
							pnlNewBranch.add(txtBranch, BorderLayout.CENTER);
							txtBranch.setHorizontalAlignment(JTextField.CENTER);
							txtBranch.setEditable(false);
						}
					}
					JXPanel pnlCheck = new JXPanel(new BorderLayout(5, 5));
					pnlNewDet.add(pnlCheck, BorderLayout.CENTER);
					{
						lblCheckNo = new JLabel("Check:");
						pnlCheck.add(lblCheckNo, BorderLayout.LINE_START);
						lblCheckNo.setHorizontalAlignment(JTextField.LEADING);
						lblCheckNo.setPreferredSize(new Dimension(51, 0));
					}
					{
						txtCheckNo = new JTextField();
						pnlCheck.add(txtCheckNo, BorderLayout.CENTER);
						txtCheckNo.setHorizontalAlignment(JTextField.CENTER);
						txtCheckNo.setEditable(false);
					}
					JXPanel pnlCheckDate = new JXPanel(new BorderLayout(5, 5));
					pnlNewDet.add(pnlCheckDate, BorderLayout.CENTER);
					pnlCheckDate.setPreferredSize(new Dimension(60, 0));
					{
						lblCheckDate = new JLabel("Date:");
						pnlCheckDate.add(lblCheckDate, BorderLayout.LINE_START);
						lblCheckDate.setHorizontalAlignment(JTextField.LEADING);
						lblCheckDate.setPreferredSize(new Dimension(51, 0));
					}
					{
						dteCheckDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlCheckDate.add(dteCheckDate, BorderLayout.CENTER);
						dteCheckDate.getCalendarButton().setVisible(true);
						dteCheckDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dteCheckDate.setEnabled(false);
					}
					JXPanel pnlAcctNo = new JXPanel(new BorderLayout(5, 5));
					pnlNewDet.add(pnlAcctNo, BorderLayout.CENTER);
					pnlAcctNo.setPreferredSize(new Dimension(60, 0));
					{
						lblAcctNo = new JLabel("Acct:");
						pnlAcctNo.add(lblAcctNo, BorderLayout.LINE_START);
						lblAcctNo.setHorizontalAlignment(JTextField.LEADING);
						lblAcctNo.setPreferredSize(new Dimension(51, 0));
					}
					{
						txtAcctNo = new JTextField();
						pnlAcctNo.add(txtAcctNo, BorderLayout.CENTER);
						txtAcctNo.setHorizontalAlignment(JTextField.CENTER);
						txtAcctNo.setEditable(false);
					}
					JXPanel pnlBRSTN = new JXPanel(new BorderLayout(5, 5));
					pnlNewDet.add(pnlBRSTN, BorderLayout.CENTER);
					pnlBRSTN.setPreferredSize(new Dimension(60, 0));
					{
						lblBRSTN = new JLabel("BRSTN:");
						pnlBRSTN.add(lblBRSTN, BorderLayout.LINE_START);
						lblBRSTN.setHorizontalAlignment(JTextField.LEADING);
						lblBRSTN.setPreferredSize(new Dimension(51, 0));
					}
					{
						txtBRSTN = new JTextField();
						pnlBRSTN.add(txtBRSTN, BorderLayout.CENTER);
						txtBRSTN.setHorizontalAlignment(JTextField.CENTER);
						txtBRSTN.setEditable(false);
					}
				}
			}
			{
				JXPanel pnlButtons = new JXPanel(new GridLayout(1, 2, 5, 5));
				pnlMain.add(pnlButtons, BorderLayout.PAGE_END);
				pnlButtons.setPreferredSize(new Dimension(0, 30));
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
				bState(2);
			}
		}
	}
	
	private void bState(Integer i) {
		if (i==1) {										/*		With Record			*/
			btnReplace.setEnabled(true);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		} else if (i==2) {								/*		Without Record		*/
			btnReplace.setEnabled(false);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		} else if (i==3) {								/*		Add Or Edit			*/
			btnReplace.setEnabled(false);
			btnSave.setEnabled(true);
			btnCancel.setEnabled(true);
		} else if (i==4) {								/*		Initial State		*/
			btnReplace.setEnabled(false);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		}
	}
	
	public void CreateTable() {
		pnlTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			pnlTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setBorder(lineGray);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelCRPL = new model_CheckReplacement();
				modelCRPL.setEditable(false);
				
				tblCRPL = new _JTableMain(modelCRPL);
				tblCRPL.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblCRPL.getSelectedRow());
							
							FirstRow(tblCRPL.getSelectedRow());
						}
					}
				});
				
				rowCRPL = tblCRPL.getRowHeader();
				scrTab.setViewportView(tblCRPL);
				
				tblCRPL.getColumnModel().getColumn(0).setPreferredWidth(100);
				tblCRPL.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblCRPL.getColumnModel().getColumn(2).setPreferredWidth(100);
				tblCRPL.getColumnModel().getColumn(3).setPreferredWidth(100);
				tblCRPL.getColumnModel().getColumn(4).setPreferredWidth(100);
				tblCRPL.getColumnModel().getColumn(5).setPreferredWidth(100);
				tblCRPL.getColumnModel().getColumn(6).setPreferredWidth(100);
				tblCRPL.getColumnModel().getColumn(7).setPreferredWidth(100);
				tblCRPL.getColumnModel().getColumn(11).setPreferredWidth(255);

				rowCRPL = tblCRPL.getRowHeader();
				rowCRPL.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowCRPL);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
	private Boolean LoadChecks(DefaultTableModel modelMain, JList rowHeader, String strID) {
		Boolean blnRet = false;
		String SQL = "select a.check_no, false as tag, a.trans_date::date, b.partdesc, c.bank_name, d.bank_branch_location, \n" + 
			"a.check_date::date, a.acct_no, e.brstn, a.amount, f.checkstat_desc, a.remarks, a.pay_rec_id, a.client_seqno \n" +
			"from rf_payments a \n" +
			"inner join mf_pay_particular b on a.pay_part_id = b.pay_part_id \n" +
			"inner join mf_bank c on a.bank_id = c.bank_id \n" +
			"inner join mf_bank_branch d on a.bank_id = d.bank_id and a.bank_branch_id = d.bank_branch_id \n" +
			"left join rf_pay_detail e on a.client_seqno = e.client_seqno and a.check_no = e.check_no and e.status_id != 'I' \n" +
			"inner join mf_check_status f on a.check_stat_id = f.checkstat_id \n" +
			"inner join rf_entity g on a.entity_id = g.entity_id \n" +
			"where a.pymnt_type = 'B' and g.entity_id = '"+strID+"'\n" +
			"order by a.trans_date::date";
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			blnRet = true;
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			FirstRow(0);
		} else {
			blnRet = true;
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
		};
		
		return blnRet;
	}
	
	private void FirstRow(Integer intRow) {
		if (tblCRPL.getRowCount() > 0) {
			txtBankID.setValue(_RealTimeDebit.GetValue("select bank_id from mf_bank where bank_name = '"+tblCRPL.getValueAt(intRow, 4)+"'"));
			txtBank.setText((String) tblCRPL.getValueAt(intRow, 4));
			txtBranchID.setValue(_RealTimeDebit.GetValue("select bank_branch_id from mf_bank_branch where bank_id = '"+txtBankID.getText()+"' and bank_branch_location = '"+tblCRPL.getValueAt(intRow, 5)+"' and status_id = 'A'"));
			txtBranch.setText((String) tblCRPL.getValueAt(intRow, 5));
			txtCheckNo.setText((String) tblCRPL.getValueAt(intRow, 0));
			dteCheckDate.setDate((Date) tblCRPL.getValueAt(intRow, 6));
			txtAcctNo.setText((String) tblCRPL.getValueAt(intRow, 7));
			txtBRSTN.setText((String) tblCRPL.getValueAt(intRow, 8));
		} else {
			txtBankID.setValue("");
			txtBank.setText("");
			txtBranchID.setValue("");
			txtBranch.setText("");
			txtCheckNo.setText("");
			txtAcctNo.setText("");
			txtBRSTN.setText("");			
		}
	}
	
	public void actionPerformed(ActionEvent act) {
		if (act.getActionCommand().equals("Replace")) {
			Integer intCount = 0;
			for (int x = 0; x < tblCRPL.getRowCount(); x++) {
				if ((Boolean) tblCRPL.getValueAt(x, 1)) {
					intCount = intCount + 1;
				}
			}
			
			if (intCount.equals(0)) {
				JOptionPane.showMessageDialog(getContentPane(), "No payment was selected.");
			} else {
				if (intCount > 1) {
					JOptionPane.showMessageDialog(getContentPane(), "Only one check per client can be replaced at a time.");
				} else {
					for (int x = 0; x < tblCRPL.getRowCount(); x++) {
						if (!(Boolean) tblCRPL.getValueAt(x, 1)) {
							System.out.println("");
							System.out.println("x: " + x);
							modelCRPL.removeRow(x);
							x = -1;
						}
					}
					
					bState(3);
					EnableBox(true);	
				}	
			}
		} else if (act.getActionCommand().equals("Save")) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to replace this check?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
				Replace();
				LoadChecks(modelCRPL, rowCRPL, txtID.getText());
				
				if (tblCRPL.getRowCount() > 0) {
					bState(1);
				} else {
					bState(2);
				}
				
				EnableBox(false);
			}
		} else if (act.getActionCommand().equals("Cancel")) {
			LoadChecks(modelCRPL, rowCRPL, txtID.getText());
			
			if (tblCRPL.getRowCount() > 0) {
				bState(1);
			} else {
				bState(2);
			}
			
			EnableBox(false);
		}
	}
	
	private void EnableBox(Boolean blnEn) {
		txtBankID.setEnabled(blnEn);
		txtBranchID.setEnabled(blnEn);
		txtCheckNo.setEditable(blnEn);
		dteCheckDate.setEnabled(blnEn);
		txtAcctNo.setEditable(blnEn);
		txtBRSTN.setEditable(blnEn);
	}
	
	private void Replace() {
		if (tblCRPL.getRowCount() > 0) {
			for (int x = 0; x < tblCRPL.getRowCount(); x++) {
				if ((Boolean) tblCRPL.getValueAt(x, 1)) {
					Integer intRec = (Integer) tblCRPL.getValueAt(x, 12);
					String strClientNo = (String) tblCRPL.getValueAt(x, 13);
					String strCheckOld = (String) tblCRPL.getValueAt(x, 0);
					String strBank = txtBankID.getText();
					String strBranch = txtBranchID.getText();
					String strCheckNo = txtCheckNo.getText();
					String strCheckDate = dteCheckDate.getDate().toString();
					String strAcctNo = txtAcctNo.getText();
					String strBRSTN = txtBRSTN.getText();
					
					System.out.println("");
					System.out.println("intRec: " + intRec.toString()); 
					System.out.println("strClientNo: " + strClientNo);
					System.out.println("strCheckOld: " + strCheckOld);
					System.out.println("strBank: " + strBank);
					System.out.println("strBranch: " + strBranch);
					System.out.println("strCheckNo: " + strCheckNo);
					System.out.println("strCheckDate: " + strCheckDate);
					System.out.println("strAcctNo: " + strAcctNo);
					System.out.println("strBRSTN: " + strBRSTN);
					
					pgUpdate updPay = new pgUpdate();
					String SQLPay = "update rf_payments \n" +
						"set bank_id = '"+strBank+"', bank_branch_id = '"+strBranch+"', check_no = '"+strCheckNo+"', \n" +
						"check_date = '"+strCheckDate+"'::timestamp, acct_no = '"+strAcctNo+"', check_stat_id = '06', \n" +
						"remarks = trim(remarks) || case when length(remarks) > 0 then ' ' else '' end || 'Check no "+strCheckOld+" replaced with "+strCheckNo+";' \n" +
						"where pay_rec_id::int = '"+intRec+"'::int and status_id != 'I' and check_no = '"+strCheckOld+"'";
					updPay.executeUpdate(SQLPay, false);
					updPay.commit();
					
					pgUpdate updPayDet = new pgUpdate();
					String SQLPayDet = "update rf_pay_detail \n" +
							"set bank = '"+strBank+"', branch = '"+strBranch+"', check_no = '"+strCheckNo+"', \n" +
							"check_date = '"+strCheckDate+"', acct_no = '"+strAcctNo+"', brstn = '"+strBRSTN+"' \n" +
							"where client_Seqno = '"+strClientNo+"' and check_no = '"+strCheckOld+"' and status_id != 'I'";
					updPayDet.executeUpdate(SQLPayDet, false);
					updPayDet.commit();
				}
			}
		}
		
		JOptionPane.showMessageDialog(getContentPane(), "Check successfully replaced!");
	}
}
