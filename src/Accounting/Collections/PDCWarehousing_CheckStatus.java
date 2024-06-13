package Accounting.Collections;

import java.awt.BorderLayout;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.channels.SelectableChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelCARD_CheckHistory;
import tablemodel.model_checks_pdc_warehousing;

public class PDCWarehousing_CheckStatus extends JXPanel implements _GUI, MouseListener {

	private static final long serialVersionUID = -5491737811545153459L;
	
	public static JXPanel panEnd; 
	public static JXPanel panLine; 

	private static JXPanel panTabList;
	private static JXPanel panTabHistory;

	private static JScrollPane scrTab;
	private static model_checks_pdc_warehousing modelWith;	
	public static _JTableMain tblWith;
	public static JList rowWith;
	
	private static JScrollPane scrHistory;
	private static modelCARD_CheckHistory modelHistory;	
	public static _JTableMain tblHistory;
	public static JList rowHistory;
	
	private static JComboBox cboShow;
	private static JComboBox cboMark;
	
	private static JButton btnSave;
	
	private static Font font = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font10Bold = FncLookAndFeel.systemFont_Bold.deriveFont(10f);
	
	private static _JLookup txtBankID;
	private static _JLookup txtBranchID;
	private static _JLookup txtBatchNo;
	
	private static JTextField txtBank;
	private static JTextField txtBranch;
	private static JTextField txtCheckNo;
	private static JTextField txtAcctNo;
	private static JTextField txtBRSTN;
	
	private static _JDateChooser dteCheckDate;
	
	@SuppressWarnings("unused")
	private static String[] strDepositValues;
	@SuppressWarnings("unused")
	private static String strCurr = "";
	@SuppressWarnings("unused")
	private static String strReference = "";
	@SuppressWarnings("unused")
	private static _JDateChooser dteGeneral; 
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	static checkStatusMonitoring_pdcWarehousing pdcw; 
	
	public PDCWarehousing_CheckStatus(checkStatusMonitoring_pdcWarehousing main_pdcw) {
		this.pdcw = main_pdcw; 
		initGUI(); 
	}

	public PDCWarehousing_CheckStatus(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public PDCWarehousing_CheckStatus(LayoutManager layout) {
		super(layout);

	}

	public PDCWarehousing_CheckStatus(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));

		panLine = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panLine, BorderLayout.CENTER); 
		{
			{
				JXPanel panTable = new JXPanel(new BorderLayout(5, 5)); 
				panLine.add(panTable, BorderLayout.CENTER); 
				{
					{
						CreateTable();
						panTable.add(panTabList, BorderLayout.CENTER);
					}
					{
						JXPanel panEnd = new JXPanel(new BorderLayout(5, 5)); 
						panTable.add(panEnd, BorderLayout.PAGE_START); 
						panEnd.setPreferredSize(new Dimension(0, 60));
						{
							{
								JXPanel panShow = new JXPanel(new BorderLayout(5, 5)); 
								panEnd.add(panShow, BorderLayout.LINE_START);
								panShow.setPreferredSize(new Dimension(250, 0));
								panShow.setBorder(JTBorderFactory.createTitleBorder("Show", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									cboShow = new JComboBox();
									panShow.add(cboShow, BorderLayout.CENTER); 
									cboShow.setFont(font11);
									cboShow.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											try {
												txtBatchNo.setValue("");
												txtBatchNo.setEnabled(cboShow.getSelectedIndex()==4);

												LoadList(); 
												LoadMarkOptions();	
											} catch (Exception ex) {
												
											}											
										}
									});
									
									pgSelect dbSelect = new pgSelect();
									dbSelect.select("select concat(checkstat_id, ' - ', checkstat_desc) \n" + 
											"from mf_check_status\n" + 
											"where checkstat_id in ('04', '05', '15', '12', '9', '10', '11')");
									
									for (int x=0; x<dbSelect.getRowCount(); x++) { 
										cboShow.addItem(dbSelect.Result[x][0]);
									}
								}
							}
							{
								JXPanel panMark = new JXPanel(new BorderLayout(5, 5)); 
								panEnd.add(panMark, BorderLayout.CENTER);
								panMark.setBorder(JTBorderFactory.createTitleBorder("Mark As", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									{
										cboMark = new JComboBox(); 
										panMark.add(cboMark, BorderLayout.CENTER); 
										cboMark.setFont(font11);

										LoadMarkOptions();	
									}
									{
										btnSave = new JButton("Save"); 
										panMark.add(btnSave, BorderLayout.LINE_END); 
										btnSave.setPreferredSize(new Dimension(100, 0));
										btnSave.setFont(font11);
										btnSave.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												Execute(); 
											}
										});
									}
								}
							}
							{
								JXPanel panBatch = new JXPanel(new BorderLayout(5, 5)); 
								panEnd.add(panBatch, BorderLayout.LINE_END);
								panBatch.setPreferredSize(new Dimension(200, 0));
								panBatch.setBorder(JTBorderFactory.createTitleBorder("Turned-over to AUB Batch", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									txtBatchNo = new _JLookup(); 
									panBatch.add(txtBatchNo, BorderLayout.CENTER); 
									txtBatchNo.setReturnColumn(0);
									txtBatchNo.setHorizontalAlignment(_JLookup.CENTER);
									txtBatchNo.setEnabled(false);
									txtBatchNo.setLookupSQL("select distinct a.ref_no, a.date_created::date \n" + 
											"from rf_check_history a \n" + 
											"inner join rf_payments b on a.pay_rec_id::int = b.pay_rec_id::int and a.new_checkstat_id = b.check_stat_id \n" + 
											"inner join rf_entity c on b.entity_id = c.entity_id \n" + 
											"inner join mf_unit_info d on b.proj_id = d.proj_id and b.pbl_id = d.pbl_id \n" + 
											"where a.new_checkstat_id = '12'");
									txtBatchNo.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											LoadList(); 
										}
									});
								}
							}
						}
					}
				}
			}
			{
				panEnd = new JXPanel(new BorderLayout(5, 5)); 
				panLine.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 200));
				{

					{
						JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5)); 
						panEnd.add(panDiv2, BorderLayout.CENTER); 
						panDiv2.setBorder(JTBorderFactory.createTitleBorder("History", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							CreateHistory(); 
							panDiv2.add(panTabHistory, BorderLayout.CENTER);		
						}
					}
					{
						JXPanel pnlNewDet = new JXPanel(new GridLayout(6, 1, 5, 5));
						panEnd.add(pnlNewDet, BorderLayout.LINE_END);
						pnlNewDet.setBorder(JTBorderFactory.createTitleBorder("Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						pnlNewDet.setPreferredSize(new Dimension(375, 0));
						{
							{
								JXPanel pnlNewBank = new JXPanel(new BorderLayout(5, 5));
								pnlNewDet.add(pnlNewBank);
								{
									JXPanel pnlNewBankLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
									pnlNewBank.add(pnlNewBankLabel, BorderLayout.LINE_START);
									pnlNewBankLabel.setPreferredSize(new Dimension(110, 0));
									{
										JLabel lblBank = new JLabel("Bank: ");
										pnlNewBankLabel.add(lblBank, BorderLayout.CENTER);
										lblBank.setHorizontalAlignment(JTextField.LEADING);
										lblBank.setFont(font);
									}
									{
										txtBankID = new _JLookup();
										pnlNewBankLabel.add(txtBankID);
										txtBankID.setHorizontalAlignment(JTextField.CENTER);
										txtBankID.setLookupSQL("");
										txtBankID.setReturnColumn(0);
										txtBankID.setFont(font);
										txtBankID.setFocusable(false);
									}
									{
										txtBank = new JTextField();
										pnlNewBank.add(txtBank, BorderLayout.CENTER);
										txtBank.setHorizontalAlignment(JTextField.CENTER);
										txtBank.setEditable(false);
										txtBank.setFont(font);
									}
								}
							}
							{
								JXPanel pnlNewBranch = new JXPanel(new BorderLayout(5, 5));
								pnlNewDet.add(pnlNewBranch);
								{
									JXPanel pnlNewBranchLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
									pnlNewBranch.add(pnlNewBranchLabel, BorderLayout.LINE_START);
									pnlNewBranchLabel.setPreferredSize(new Dimension(110, 0));
									{
										JLabel lblBranch = new JLabel("Branch: ");
										pnlNewBranchLabel.add(lblBranch, BorderLayout.CENTER);
										lblBranch.setHorizontalAlignment(JTextField.LEADING);
										lblBranch.setFont(font);
									}
									{
										txtBranchID = new _JLookup();
										pnlNewBranchLabel.add(txtBranchID);
										txtBranchID.setHorizontalAlignment(JTextField.CENTER);
										txtBranchID.setLookupSQL("");
										txtBranchID.setReturnColumn(0);
										txtBranchID.setFont(font);
										txtBranchID.setFocusable(false);
									}
									{
										txtBranch = new JTextField();
										pnlNewBranch.add(txtBranch, BorderLayout.CENTER);
										txtBranch.setHorizontalAlignment(JTextField.CENTER);
										txtBranch.setEditable(false);
										txtBranch.setFont(font);
									}
								}
							}
							{
								JXPanel pnlCheck = new JXPanel(new BorderLayout(5, 5));
								pnlNewDet.add(pnlCheck);
								{
									JLabel lblCheckNo = new JLabel("Check:");
									pnlCheck.add(lblCheckNo, BorderLayout.LINE_START);
									lblCheckNo.setHorizontalAlignment(JTextField.LEADING);
									lblCheckNo.setPreferredSize(new Dimension(51, 0));
									lblCheckNo.setFont(font);
								}
								{
									txtCheckNo = new JTextField();
									pnlCheck.add(txtCheckNo, BorderLayout.CENTER);
									txtCheckNo.setHorizontalAlignment(JTextField.CENTER);
									txtCheckNo.setEditable(false);
									txtCheckNo.setFont(font);
								}
							}
							{
								JXPanel pnlCheckDate = new JXPanel(new BorderLayout(5, 5));
								pnlNewDet.add(pnlCheckDate);
								pnlCheckDate.setPreferredSize(new Dimension(60, 0));
								{
									JLabel lblCheckDate = new JLabel("Date:");
									pnlCheckDate.add(lblCheckDate, BorderLayout.LINE_START);
									lblCheckDate.setHorizontalAlignment(JTextField.LEADING);
									lblCheckDate.setPreferredSize(new Dimension(51, 0));
									lblCheckDate.setFont(font);
								}
								{
									dteCheckDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlCheckDate.add(dteCheckDate, BorderLayout.CENTER);
									dteCheckDate.getCalendarButton().setVisible(true);
									dteCheckDate.setDate(null);
									dteCheckDate.setEditable(false);
									dteCheckDate.setFocusable(false);
									dteCheckDate.setFont(font);
									dteCheckDate.getCalendarButton().setVisible(false);
								}
							}
							{
								JXPanel pnlAcctNo = new JXPanel(new BorderLayout(5, 5));
								pnlNewDet.add(pnlAcctNo);
								pnlAcctNo.setPreferredSize(new Dimension(60, 0));
								{
									JLabel lblAcctNo = new JLabel("Acct:");
									pnlAcctNo.add(lblAcctNo, BorderLayout.LINE_START);
									lblAcctNo.setHorizontalAlignment(JTextField.LEADING);
									lblAcctNo.setPreferredSize(new Dimension(51, 0));
									lblAcctNo.setFont(font);
								}
								{
									txtAcctNo = new JTextField();
									pnlAcctNo.add(txtAcctNo, BorderLayout.CENTER);
									txtAcctNo.setHorizontalAlignment(JTextField.CENTER);
									txtAcctNo.setEditable(false);
									txtAcctNo.setFont(font);
								}
							}
							{
								JXPanel pnlBRSTN = new JXPanel(new BorderLayout(5, 5));
								pnlNewDet.add(pnlBRSTN);
								pnlBRSTN.setPreferredSize(new Dimension(60, 0));
								{
									JLabel lblBRSTN = new JLabel("BRSTN:");
									pnlBRSTN.add(lblBRSTN, BorderLayout.LINE_START);
									lblBRSTN.setHorizontalAlignment(JTextField.LEADING);
									lblBRSTN.setPreferredSize(new Dimension(51, 0));
									lblBRSTN.setFont(font);
								}
								{
									txtBRSTN = new JTextField();
									pnlBRSTN.add(txtBRSTN, BorderLayout.CENTER);
									txtBRSTN.setHorizontalAlignment(JTextField.CENTER);
									txtBRSTN.setEditable(false);
									txtBRSTN.setFont(font);
								}
							}
						}	
					}
				}
			}
		}
	}

    public static void LoadList() {
		FncGlobal.startProgress("Refreshing...");
		
		String strStatus = cboShow.getSelectedItem().toString().substring(0, 2); 

		FncTables.clearTable(modelWith); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowWith.setModel(listModel); 

		String strSQL = "select false as tag, * \n" +
				"from view_check_for_pdc_warehousing('"+checkStatusMonitoring_pdcWarehousing.txtCoID.getValue()+"', \n" +
				"'"+checkStatusMonitoring_pdcWarehousing.txtProID.getValue()+"', '', '"+strStatus+"', '"+txtBatchNo.getValue()+"')";

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
    
	private static void loadHist(DefaultTableModel modelMain, JList rowHeader, String strPayRecID) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String strSQL = "select a.date_created, b.checkstat_desc, a.ref_no, a.dep_no \n" + 
				"from rf_check_history a \n" + 
				"inner join mf_check_status b on a.new_checkstat_id = b.checkstat_id \n" + 
				"where a.pay_rec_id::int = '"+strPayRecID+"'::int";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}
	}
	
	private static void LoadMarkOptions() {
		String strItem = cboShow.getSelectedItem().toString().substring(0, 2); 
		String strOpt = ""; 
		
		switch (strItem) {
			case "04":
				strOpt = "'15'"; 
				break;
			case "15":
				strOpt = "'12'"; 
				break;
			case "12":
				strOpt = "'05'"; 
				break;
			default:
				strOpt = "''"; 
		}
		
		
		try {
			cboMark.removeAllItems();
		} catch (Exception ex) {
			
		}
		
		pgSelect dbSelect = new pgSelect();
		dbSelect.select("select concat(checkstat_id, ' - ', checkstat_desc) \n" + 
				"from mf_check_status \n" + 
				"where checkstat_id in ("+strOpt+")");

		try {
			for (int x=0; x<dbSelect.getRowCount(); x++) { 
				cboMark.addItem(dbSelect.Result[x][0]);
			}
		} catch (NullPointerException ex) {
			
		}
	}

	private static void Execute() {
		pgUpdate dbExec = new pgUpdate(); 
		Boolean blnProceed = false; 
		
		for (int x=0; x < modelWith.getRowCount(); x++) {
			if ((Boolean) modelWith.getValueAt(x, 0)) {
				blnProceed = true; 
			}
		}
		
		if (blnProceed) {
			try {
				if (JOptionPane.showConfirmDialog(panLine, "Mark selected checks as "+cboMark.getSelectedItem().toString().substring(5, cboMark.getSelectedItem().toString().length())+"?", 
						"Saving", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
					
					String strBatch = ""; 
					String strNew = cboMark.getSelectedItem().toString().substring(0, 2); 
					
					System.out.println("");
					System.out.println("strNew: "+strNew);
					
					if (strNew.equals("12")) {
						strBatch = FncGlobal.GetString("select concat('TOAUB', \n" + 
								"right(date_part('year', now())::text, 2), \n" + 
								"right(date_part('month', now())::text, 2), \n" + 
								"lpad((coalesce(max(right(a.ref_no, 3)), '0')::int+1)::text, 3, '0')) \n" + 
								"from rf_check_history a \n" + 
								"where a.new_checkstat_id = '12'");
						txtBatchNo.setValue(strBatch);
					}
					
					for (int x=0; x < modelWith.getRowCount(); x++) {
						if ((Boolean) modelWith.getValueAt(x, 0)) {
							String strCurrent = cboShow.getSelectedItem().toString().substring(0, 2);
							
							dbExec.executeUpdate("update rf_payments\n" + 
									"set check_stat_id = '"+strNew+"' \n" + 
									"where pay_rec_id::int = '"+modelWith.getValueAt(x, 12)+"'::int", true);

							dbExec.executeUpdate("insert into rf_check_history (pay_rec_id, prev_checkstat_id, new_checkstat_id, trans_date, \n" + 
									"status_id, created_by, date_created, ref_no)\n" + 
									"values ('"+modelWith.getValueAt(x, 12)+"', '"+strCurrent+"', '"+strNew+"', now(), 'A', '"+UserInfo.EmployeeCode+"', now(), '"+strBatch+"'); ", true);
						}	
					}
					
					dbExec.commit();
					LoadList(); 
					JOptionPane.showMessageDialog(panLine, "New status saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception ex) {
				dbExec.rollback();
				JOptionPane.showMessageDialog(panLine, "Something went wrong. Call JST.", "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(panLine, "No row was selected.", "Caution", JOptionPane.WARNING_MESSAGE);
		}
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
								FirstRow(tblWith.getSelectedRow());
								strCurr = FncGlobal.GetString("select b.checkstat_desc \n" + 
										"from rf_check_history a \n" + 
										"inner join mf_check_status b on a.new_checkstat_id = b.checkstat_id \n" + 
										"where a.pay_rec_id::int = '"+modelWith.getValueAt(tblWith.getSelectedRow(), 12)+"'::int \n" + 
										"order by a.date_created desc, b.sequence desc");  
								strReference = (String) modelWith.getValueAt(tblWith.getSelectedRow(), 10); 
							} catch (ArrayIndexOutOfBoundsException ex) {
								
							}
						}
					}
				});

				tblWith.addMouseListener(this);
				rowWith = tblWith.getRowHeader();
				scrTab.setViewportView(tblWith);
				
				tblWith.getColumnModel().getColumn(0).setPreferredWidth(30);
				tblWith.getColumnModel().getColumn(1).setPreferredWidth(210);
				tblWith.getColumnModel().getColumn(2).setPreferredWidth(120);
				tblWith.getColumnModel().getColumn(3).setPreferredWidth(90);
				tblWith.getColumnModel().getColumn(4).setPreferredWidth(90);
				tblWith.getColumnModel().getColumn(5).setPreferredWidth(85);
				tblWith.getColumnModel().getColumn(9).setPreferredWidth(85);
				tblWith.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf));
				
				rowWith = tblWith.getRowHeader();
				rowWith.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowWith);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblWith.hideColumns("Bank", "Branch", "Date", "Account No", "BRSTN", "ref_no", "pay_rec_id");
				
				tblWith.addKeyListener(keyListener);
			}
		}
	}
	
	public void CreateHistory() {
		panTabHistory = new JXPanel(new GridLayout(1, 1, 0, 0));
		panTabHistory.setOpaque(isOpaque());
		panTabHistory.setPreferredSize(new Dimension(0, 125));
		{
			scrHistory = new JScrollPane();
			panTabHistory.add(scrHistory, BorderLayout.CENTER);
			scrHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelHistory = new modelCARD_CheckHistory();
				
				tblHistory = new _JTableMain(modelHistory);
				rowHistory = tblHistory.getRowHeader();
				scrHistory.setViewportView(tblHistory);
				
				tblHistory.getColumnModel().getColumn(0).setPreferredWidth(80);
				tblHistory.getColumnModel().getColumn(1).setPreferredWidth(150);
				tblHistory.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
				
				tblHistory.hideColumns("Rec", "Deposit", "Reason(for Bounce Checks)");

				rowHistory = tblHistory.getRowHeader();
				rowHistory.setModel(new DefaultListModel());
				scrHistory.setRowHeaderView(rowHistory);
				scrHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
	private void FirstRow(Integer intRow) {

		try {
			if (tblWith.getRowCount() > 0) {
				txtBankID.setValue(modelWith.getValueAt(intRow, 7).toString());
				txtBank.setText(FncGlobal.GetString("select bank_name from mf_bank where bank_id = '"+modelWith.getValueAt(intRow, 7).toString()+"'"));
				txtBranchID.setValue(modelWith.getValueAt(intRow, 8).toString());
				txtBranch.setText(FncGlobal.GetString("select bank_branch_location from mf_bank_branch where bank_id = '"+modelWith.getValueAt(intRow, 7).toString()+"' and bank_branch_id = '"+modelWith.getValueAt(intRow, 7).toString()+"' and status_id = 'A'"));
				txtCheckNo.setText((String) modelWith.getValueAt(intRow, 3));
				dteCheckDate.setDate((Date) modelWith.getValueAt(intRow, 9));
				txtAcctNo.setText((String) modelWith.getValueAt(intRow, 10));
				txtBRSTN.setText((String) modelWith.getValueAt(intRow, 11));
				
				loadHist(modelHistory, rowHistory, (String) modelWith.getValueAt(intRow, 12)); 
			} else {
				txtBankID.setValue("");
				txtBank.setText("");
				txtBranchID.setValue("");
				txtBranch.setText("");
				txtCheckNo.setText("");
				txtAcctNo.setText("");
				txtBRSTN.setText("");
				dteCheckDate.setDate(null);
				
				FncTables.clearTable(modelHistory); 
			}	
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
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

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
