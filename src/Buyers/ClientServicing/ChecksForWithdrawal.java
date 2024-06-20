package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

import tablemodel.model_CheckReplacement;
import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections.RealTimeDebit;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class ChecksForWithdrawal extends _JInternalFrame implements
		ActionListener, _GUI {

	private static final long serialVersionUID = 8330346392826206663L;
	static String title = "Checks For Withdrawal";
	Dimension frameSize = new Dimension(750, 405);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);

	private JLabel lblName;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeq;
	private JLabel lblWithNo;
	
	static _JLookup txtID;
	
	static JTextField txtProjID;
	static JTextField txtUnitID;
	static JTextField txtName;
	static JTextField txtProject;
	static JTextField txtUnit;
	static JTextField txtSeq;
	
	static _JLookup txtBankID;
	static _JLookup txtBranchID;
	static _JLookup txtWithNo;
	
	static JTextField txtBank;
	static JTextField txtBranch;
	static JTextField txtCheckNo;
	static _JDateChooser dteCheckDate;
	static JTextField txtAcctNo;
	static JTextField txtBRSTN;
	static JTextField txtStatus;
	
	private JLabel lblBank;
	private JLabel lblBranch;
	private JLabel lblCheckNo;
	private JLabel lblCheckDate;
	private JLabel lblAcctNo;
	private JLabel lblBRSTN;
	
	private JButton btnWithdraw;
	private JButton btnApprove;
	private JButton btnPreview;
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	private model_CheckReplacement modelWith;	
	public static _JTableMain tblWith;
	public static JList rowWith;
	
	private _ChecksReplacementAndWithdrawal crw;
	
	public ChecksForWithdrawal() {
		super(title, true, true, false, true);
		initGUI();
	}

	public ChecksForWithdrawal(String title) {
		super(title);
		initGUI();
	}

	public ChecksForWithdrawal(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
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
					}
					{
						txtID = new _JLookup("");
						pnlPSLabel.add(txtID);
						txtID.setHorizontalAlignment(JTextField.CENTER);
						txtID.setReturnColumn(1);
						txtID.setLookupSQL(_ChecksReplacementAndWithdrawal.Client());
						txtID.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								try {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtID.setValue(data[0].toString());
										txtName.setText(data[1].toString());
										txtProjID.setText(data[4].toString());
										txtProject.setText(data[2].toString());
										txtUnitID.setText(data[5].toString());
										txtUnit.setText(data[3].toString());
										txtSeq.setText(data[6].toString());
										txtWithNo.setValue("");
										
										if (LoadChecks(modelWith, rowWith, data[0].toString(), data[4].toString(), data[5].toString(), data[6].toString(), "")) {
											bState(1);
										} else {
											bState(4);
										}
									}
								} catch(NullPointerException e) {
									System.out.println("Null pointer caught during lookup.");
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project");
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
						lblUnit = new JLabel("Unit");
						pnlPSLabel.add(lblUnit);
						lblUnit.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						txtUnitID = new JTextField("");
						pnlPSLabel.add(txtUnitID);
						txtUnitID.setHorizontalAlignment(JTextField.CENTER);
						txtUnitID.setEditable(false);
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
					btnWithdraw = new JButton("Tag as For Withdrawal");
					panZero.add(btnWithdraw);
					btnWithdraw.setEnabled(false);
					btnWithdraw.setActionCommand("ForWithdrawal");
					btnWithdraw.addActionListener(this);
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
				pnlBank.setBorder(JTBorderFactory.createTitleBorder("Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					CreateTable();
					pnlBank.add(pnlTab);
				}
				JXPanel pnlNewDet = new JXPanel(new GridLayout(6, 1, 5, 5));
				pnlPageEnd.add(pnlNewDet, BorderLayout.LINE_END);
				pnlNewDet.setBorder(JTBorderFactory.createTitleBorder("Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
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
				JXPanel pnlButtons = new JXPanel(new GridLayout(1, 3, 5, 5));
				pnlMain.add(pnlButtons, BorderLayout.PAGE_END);
				pnlButtons.setPreferredSize(new Dimension(0, 30));
				{
					btnApprove = new JButton("Save");
					pnlButtons.add(btnApprove);
					btnApprove.setActionCommand("Approve");
					btnApprove.addActionListener(this);
				}
				{
					btnPreview = new JButton("Preview");
					pnlButtons.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
				{
					JXPanel panWith = new JXPanel(new BorderLayout(5, 5));
					pnlButtons.add(panWith);
					{
						lblWithNo = new JLabel("w/draw No.");
						panWith.add(lblWithNo, BorderLayout.LINE_START);
						lblWithNo.setHorizontalAlignment(JTextField.CENTER);
						lblWithNo.setPreferredSize(new Dimension(75, 0));
					}
					{
						txtWithNo = new _JLookup("");
						panWith.add(txtWithNo, BorderLayout.CENTER);
						txtWithNo.setHorizontalAlignment(JTextField.CENTER);
						txtWithNo.setReturnColumn(0);
						txtWithNo.setLookupSQL("select distinct withdraw_no, trans_date::date, check_no, check_date, pay_part_id, pay_rec_id from rf_check_forwithdrawal order by withdraw_no desc");
						txtWithNo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								
								String SQL = "select a.entity_id, trim(e.entity_name), a.proj_id, b.proj_name, a.pbl_id, c.description, a.seq_no \n" +
									"from rf_payments a \n" +
									"inner join mf_project b on a.proj_id = b.proj_id \n" +
									"inner join mf_unit_info c on a.proj_id = c.proj_id and a.pbl_id = c.pbl_id \n" +
									"inner join rf_sold_unit d on a.entity_id = d.entity_id and a.proj_id = d.projcode and a.pbl_id = d.pbl_id and a.seq_no = d.seq_no \n" +
									"inner join rf_entity e on a.entity_id = e.entity_id \n" +
									"where d.status_id != 'I' and a.pay_rec_id::int = '"+data[5].toString()+"'::int and a.status_id != 'I' \n" +
									"limit 1";
								
								pgSelect db = new pgSelect();
								db.select(SQL);
								if (db.isNotNull()){
									for (int x = 0; x < db.getRowCount(); x++) {
										
										txtID.setValue(db.getResult()[0][0].toString());
										txtName.setText(db.getResult()[0][1].toString());
										txtProjID.setText(db.getResult()[0][2].toString());
										txtProject.setText(db.getResult()[0][3].toString());
										txtUnitID.setText(db.getResult()[0][4].toString());
										txtUnit.setText(db.getResult()[0][5].toString());
										txtSeq.setText(db.getResult()[0][6].toString());
									}
								}

								if (LoadChecks(modelWith, rowWith, txtID.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText(), data[0].toString())) {
									bState(3);
								} else {
									bState(4);
								}
								
								txtStatus.setText(FncGlobal.GetString("select (case when status_id = 'A' then 'Active' when status_id = 'P' then 'Posted' else 'Inactive' end) from rf_check_forwithdrawal where withdraw_no = '"+txtWithNo.getValue()+"' limit 1"));
								
								if (txtStatus.getText().equals("Posted")) {
									bState(2);
								}
							}
						});
					}
					{
						txtStatus = new JTextField("---");
						panWith.add(txtStatus, BorderLayout.LINE_END);
						txtStatus.setHorizontalAlignment(JTextField.CENTER);
						txtStatus.setPreferredSize(new Dimension(60, 0));
						txtStatus.setEditable(false);
					}
				}
				bState(4);
			}
		}
	}

	private void bState(Integer i) {
		if (i==1) {										/*		With Record			*/
			btnWithdraw.setEnabled(true);
			btnApprove.setEnabled(false);
			btnPreview.setEnabled(false);
		} else if (i==2) {								/*		Without Record		*/
			btnWithdraw.setEnabled(false);
			btnApprove.setEnabled(false);
			btnPreview.setEnabled(true);
		} else if (i==3) {								/*		Add Or Edit			*/
			btnWithdraw.setEnabled(false);
			btnApprove.setEnabled(true);
			btnPreview.setEnabled(true);
		} else if (i==4) {								/*		Initial State		*/
			btnWithdraw.setEnabled(false);
			btnApprove.setEnabled(false);
			btnPreview.setEnabled(false);
		} else if (i==5) {								/*		Pressed Tag			*/
			btnWithdraw.setEnabled(false);
			btnApprove.setEnabled(true);
			btnPreview.setEnabled(false);
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
				modelWith = new model_CheckReplacement();
				modelWith.setEditable(false);
				
				tblWith = new _JTableMain(modelWith);
				tblWith.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblWith.getSelectedRow());
							FirstRow(tblWith.getSelectedRow());
						}
					}
				});
				
				rowWith = tblWith.getRowHeader();
				scrTab.setViewportView(tblWith);
				
				tblWith.getColumnModel().getColumn(0).setPreferredWidth(100);
				tblWith.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblWith.getColumnModel().getColumn(2).setPreferredWidth(100);
				tblWith.getColumnModel().getColumn(3).setPreferredWidth(101);

				rowWith = tblWith.getRowHeader();
				rowWith.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowWith);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblWith.hideColumns("Bank", "Branch", "Check Date", "Account No", "BRSTN", "Amount", "Current Status", "Remarks", "Rec ID", "Sequence No.");
			}
		}
	}
	
	private Boolean LoadChecks(DefaultTableModel modelMain, JList rowHeader, String strID, String strPro, String strUnit, String strSeq, String strWith) {
		Boolean blnRet = false;
		String SQL = "select * from view_check_forwithdrawal('"+strID+"', '"+strPro+"', '"+strUnit+"', '"+strSeq+"', '"+strWith+"');";
		
		System.out.println("");
		System.out.println(SQL);
		
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
			blnRet = false;
			JOptionPane.showMessageDialog(null, "No checks were in line for withdrawal.");
		};
		
		return blnRet;
	}
	
	public static String GetValue(String SQL) {
		String strValue = "";
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			strValue = (String) sqlExec.getResult()[0][0];
		} else {
			strValue = "";
		}
		
		return strValue;
	}
	
	private void FirstRow(Integer intRow) {
		if (tblWith.getRowCount() > 0) {
			txtBankID.setValue(GetValue("select bank_id from mf_bank where bank_name = '"+modelWith.getValueAt(intRow, 4)+"'"));
			txtBank.setText((String) modelWith.getValueAt(intRow, 4));
			txtBranchID.setValue(GetValue("select bank_branch_id from mf_bank_branch where bank_id = '"+txtBankID.getText()+"' and bank_branch_location = '"+modelWith.getValueAt(intRow, 5)+"' and status_id = 'A'"));
			txtBranch.setText((String) modelWith.getValueAt(intRow, 5));
			txtCheckNo.setText((String) modelWith.getValueAt(intRow, 0));
			dteCheckDate.setDate((Date) modelWith.getValueAt(intRow, 6));
			txtAcctNo.setText((String) modelWith.getValueAt(intRow, 7));
			txtBRSTN.setText((String) modelWith.getValueAt(intRow, 8));
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
		if (act.getActionCommand().equals("ForWithdrawal")) {
			Integer intCount = 0;
			for (int x = 0; x < tblWith.getRowCount(); x++) {
				if ((Boolean) tblWith.getValueAt(x, 1)) {
					intCount = intCount + 1;
				}
			}
			
			if (intCount==0) {
				JOptionPane.showMessageDialog(getContentPane(), "No row was selected.");
			} else {
				ForWithdraw();
				LoadChecks(modelWith, rowWith, "", "", "", "0", txtWithNo.getValue());
				bState(5);	
				
				JOptionPane.showMessageDialog(getContentPane(), "Please press save to complete.");
			}
		} else if (act.getActionCommand().equals("Approve")) {
			Approve();
			LoadChecks(modelWith, rowWith, "", "", "", "0", txtWithNo.getValue());
			bState(2);
		} else if (act.getActionCommand().equals("Preview")) {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("02_Project", txtProject.getText());
			mapParameters.put("03_User", UserInfo.FullName);
			mapParameters.put("04_WithdrawNo", txtWithNo.getValue());
			FncReport.generateReport("/Reports/rpt_ChecksForWithdrawal.jasper", "For Withdrawal", "", mapParameters);
		}
	}
	
	private void ForWithdraw() {
		String SQL = "";
		String strWith = "";
		
		if (tblWith.getRowCount() > 0) {
			strWith = crw.GetNo();
			for (int x = 0; x < tblWith.getRowCount(); x++) {
				if ((Boolean) modelWith.getValueAt(x, 1)) {
					Integer intRec = (Integer) modelWith.getValueAt(x, 12);
					String strCheckOld = (String) modelWith.getValueAt(x, 0);
					String strCheckDate = (String) modelWith.getValueAt(x, 6).toString();
					String strDate = (String) modelWith.getValueAt(x, 2).toString();
					String strAmount = (String) modelWith.getValueAt(x, 9).toString();
					String strAccount = (String) modelWith.getValueAt(x, 7).toString();
					
					System.out.println("");
					System.out.println("intRec: " + intRec.toString()); 
					
					pgUpdate updSave = new pgUpdate();
					String strWithRec = crw.GetRec();
					txtWithNo.setValue(strWith);
					
					String strBankID = FncGlobal.GetString("select bank_id from rf_payments where pay_rec_id::int = '"+intRec+"'::int and status_id != 'I' and check_no = '"+strCheckOld+"' and check_date = '"+strCheckDate+"'");
					String strBankBranchID = FncGlobal.GetString("select bank_branch_id from rf_payments where pay_rec_id::int = '"+intRec+"'::int and status_id != 'I' and check_no = '"+strCheckOld+"' and check_date = '"+strCheckDate+"'");
					String strPart = FncGlobal.GetString("select pay_part_id from rf_payments where pay_rec_id::int = '"+intRec+"'::int and status_id != 'I' and check_no = '"+strCheckOld+"' and check_date = '"+strCheckDate+"'");
					String strSeqNo = FncGlobal.GetString("select client_seqno from rf_payments where pay_rec_id::int = '"+intRec+"'::int and status_id != 'I' and check_no = '"+strCheckOld+"' and check_date = '"+strCheckDate+"'");
					
					//FncGlobal.getDateSQL()
					
					SQL = "insert into rf_check_forwithdrawal (withdraw_rec_id, withdraw_no, trans_date, bank_id, bank_branch_id, acct_no, check_no, check_date, \n" + 
						"amount, pay_part_id, pay_rec_id, client_seqno, created_by, date_created, status_id) values (\n" +
						"'"+strWithRec+"', \n" +
						"'"+strWith+"', \n" +
						"'"+strDate+"', \n" +
						"'"+strBankID+"', \n" +
						"'"+strBankBranchID+"', \n" +
						"'"+strAccount+"', \n" +
						"'"+strCheckOld+"', \n" +
						"'"+strCheckDate+"', \n" +
						""+strAmount+", \n" +
						"'"+strPart+"', \n" +
						""+intRec+", \n" +
						"'"+strSeqNo+"', \n" +
						"'"+UserInfo.EmployeeCode+"', \n" +
						"'"+FncGlobal.getDateSQL()+"', \n" +
						"'A')";
					updSave.executeUpdate(SQL, true);
					updSave.commit();
				}
			}
		}
	}
	
	
	
	private void Approve() {
		String SQL = "";
		if (tblWith.getRowCount() > 0) {
			for (int x = 0; x < tblWith.getRowCount(); x++) {
				if ((Boolean) modelWith.getValueAt(x, 1)) {
					Integer intRec = (Integer) modelWith.getValueAt(x, 12);
					String strCheckOld = (String) modelWith.getValueAt(x, 0);
					String strCheckDate = (String) modelWith.getValueAt(x, 6).toString();

					String strPrev = GetValue("select check_stat_id from rf_payments where pymnt_type = 'B' and pay_rec_id::int = '"+intRec+"'::int and check_no = '"+strCheckOld+"' and status_id != 'I'");
					
					pgUpdate updPay = new pgUpdate();
					SQL = "update rf_payments \n" +
						"set check_stat_id = '09' \n" +
						"where pay_rec_id::int = '"+intRec+"'::int and status_id != 'I' and check_no = '"+strCheckOld+"'";
					updPay.executeUpdate(SQL, false);
					updPay.commit();
					
					pgUpdate updStatus = new pgUpdate();
					SQL = "INSERT INTO rf_check_history VALUES ( \n" +			
					"'"+intRec+"', '"+strPrev+"', '09', '', Now(), null, null, 'A', '"+ UserInfo.EmployeeCode + "', Now(), '')";
					updStatus.executeUpdate(SQL, false);
					updStatus.commit();
					
					pgUpdate updSave = new pgUpdate();
					SQL = "update rf_check_forwithdrawal \n" +
						"set posted_by = '"+UserInfo.EmployeeCode+"', date_posted  = '"+FncGlobal.getDateSQL()+"', status_id = 'P' \n" +
						"where withdraw_no = '"+txtWithNo.getValue()+"' and check_no = '"+strCheckOld+"' \n" +
						"and check_date = '"+strCheckDate+"' and pay_rec_id::int = '"+intRec+"'::int";
					updSave.executeUpdate(SQL, false);
					updSave.commit();
				}
			}
		}
		
		JOptionPane.showMessageDialog(getContentPane(), "Approved for withdrawal");
	}
}
