package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelCARD_CheckHistory;
import tablemodel.model_checks_floating;

public class checkStatusMonitoring_others extends _JInternalFrame implements _GUI, MouseListener {

	private static final long serialVersionUID = -6097648613875018980L;
	static String title = "Check Status (Floating Payments)";
	
	private final Dimension frameSize = new Dimension(700, 500);
	@SuppressWarnings("unused")
	
	private static JXPanel panTabList;
	private static JXPanel panTabHistory;

	private JScrollPane scrTab;
	private model_checks_floating modelWith;	
	public static _JTableMain tblWith;
	public static JList rowWith;
	
	private JScrollPane scrHistory;
	private modelCARD_CheckHistory modelHistory;	
	public static _JTableMain tblHistory;
	public static JList rowHistory;
	
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
	
	static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private static Font font = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	
	private static _JLookup txtCoID;
	private static _JLookup txtOfficeBranchID;
	private static _JLookup txtBankAcctNo;
	private static _JLookup txtBounceReasonID;
	
	private static JTextField txtCo;
	private static JTextField txtOfficeBranch;
	private static JTextField txtBankAcct;
	private static JTextField txtAcctBank;
	private static JTextField txtAcctBranch;
	private static JTextField txtBounceReason;
	
	private static _JDateChooser dteDepDate;
	private static _JDateChooser dteCollectionDate; 
	
	private static JTextArea txtNote; 
	
	@SuppressWarnings("unused")
	private static String[] strDepositValues;
	private String strCurr = "";
	private String strReference = "";
	private static _JDateChooser dteGeneral; 
	
	public checkStatusMonitoring_others() {
		super(title, false, true, false, true);
		initGUI();
	}

	public checkStatusMonitoring_others(String title) {
		super(title);
		initGUI(); 
	}

	public checkStatusMonitoring_others(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5)); 
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(2, 2, 2, 2));
		{
			{
				JXPanel panLine = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panLine, BorderLayout.CENTER); 
				{
					{
						CreateTable();
						panLine.add(panTabList, BorderLayout.CENTER);
					}
					{
						JXPanel panEnd = new JXPanel(new BorderLayout(5, 5)); 
						panLine.add(panEnd, BorderLayout.PAGE_END); 
						panEnd.setPreferredSize(new Dimension(0, 200));
						{
							{
								JXPanel panHistory = new JXPanel(new BorderLayout(5, 5)); 
								panEnd.add(panHistory, BorderLayout.CENTER); 
								panHistory.setBorder(JTBorderFactory.createTitleBorder("History", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									CreateHistory(); 
									panHistory.add(panTabHistory, BorderLayout.CENTER);	
								}
							}
							{
								JXPanel pnlNewDet = new JXPanel(new GridLayout(6, 1, 5, 5));
								panEnd.add(pnlNewDet, BorderLayout.LINE_END);
								pnlNewDet.setBorder(JTBorderFactory.createTitleBorder("Check Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								pnlNewDet.setPreferredSize(new Dimension(325, 0));
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
		} 
		
		loadList(modelWith, rowWith); 
	}

	public void CreateTable() {
		panTabList = new JXPanel(new GridLayout(1, 1, 0, 0));
		panTabList.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			panTabList.add(scrTab, BorderLayout.CENTER);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelWith = new model_checks_floating();
				modelWith.setEditable(false);
				
				tblWith = new _JTableMain(modelWith);
				tblWith.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							
							System.out.println("");
							System.out.println("Row: "+tblWith.getSelectedRow());
							
							FirstRow(tblWith.getSelectedRow());
							strCurr = FncGlobal.GetString("select b.checkstat_desc \n" + 
									"from rf_check_history a \n" + 
									"inner join mf_check_status b on a.new_checkstat_id = b.checkstat_id \n" + 
									"where a.ref_no = '"+modelWith.getValueAt(tblWith.getSelectedRow(), 10)+"' \n" + 
									"order by a.date_created desc, b.sequence desc");  
							strReference = (String) modelWith.getValueAt(tblWith.getSelectedRow(), 10); 
						}
					}
				});

				tblWith.addMouseListener(this);
				rowWith = tblWith.getRowHeader();
				scrTab.setViewportView(tblWith);
				
				tblWith.getColumnModel().getColumn(0).setPreferredWidth(214);
				tblWith.getColumnModel().getColumn(1).setPreferredWidth(105);
				tblWith.getColumnModel().getColumn(2).setPreferredWidth(105);
				tblWith.getColumnModel().getColumn(3).setPreferredWidth(103);
				tblWith.getColumnModel().getColumn(4).setPreferredWidth(104);
				tblWith.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
				
				rowWith = tblWith.getRowHeader();
				rowWith.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowWith);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblWith.hideColumns("Bank", "Branch", "Check Date", "Account No", "BRSTN", "ref_no");
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
				
				tblHistory.getColumnModel().getColumn(0).setPreferredWidth(115);
				tblHistory.getColumnModel().getColumn(1).setPreferredWidth(171);
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
				txtBankID.setValue(modelWith.getValueAt(intRow, 5).toString());
				txtBank.setText(FncGlobal.GetString("select bank_name from mf_bank where bank_id = '"+modelWith.getValueAt(intRow, 5).toString()+"'"));
				txtBranchID.setValue(modelWith.getValueAt(intRow, 6).toString());
				txtBranch.setText(FncGlobal.GetString("select bank_branch_location from mf_bank_branch where bank_id = '"+modelWith.getValueAt(intRow, 5).toString()+"' and bank_branch_id = '"+modelWith.getValueAt(intRow, 6).toString()+"' and status_id = 'A'"));
				txtCheckNo.setText((String) modelWith.getValueAt(intRow, 1));
				dteCheckDate.setDate((Date) modelWith.getValueAt(intRow, 7));
				txtAcctNo.setText((String) modelWith.getValueAt(intRow, 8));
				txtBRSTN.setText((String) modelWith.getValueAt(intRow, 9));
				
				loadHist(modelHistory, rowHistory, (String) modelWith.getValueAt(intRow, 10)); 
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
			
		}
	}
	
	public static void loadList(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String strSQL = "select * from view_check_floating()";
		
		System.out.println("strSQL: "+strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}
		
		tblWith.changeSelection(0, 0, false, false);
	}
	
	public static void loadHist(DefaultTableModel modelMain, JList rowHeader, String strRefNo) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String strSQL = "select a.date_created, b.checkstat_desc, a.ref_no, a.dep_no \n" + 
				"from rf_check_history a \n" + 
				"inner join mf_check_status b on a.new_checkstat_id = b.checkstat_id \n" + 
				"where a.ref_no = '"+strRefNo+"'";
		
		System.out.println("\n\nstrSQL: "+strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON3) {
			int row = tblWith.rowAtPoint(e.getPoint()); 
		    if (row > -1) {
		    	tblWith.setRowSelectionInterval(row, row); 
		    }
		    
			System.out.println("Current Status: "+strCurr);
			initializeMenu().show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
	
	private JPopupMenu initializeMenu() {
		String[] strArrStatus = strArr(strCurr); 
		JPopupMenu menu = new JPopupMenu();
		JMenuItem[] menuCheck; 
		strDepositValues = null; 
				
		menuCheck = new JMenuItem[strArrStatus.length]; 
		for (int x=0; x<strArrStatus.length; x++) {
			menuCheck[x] = new JMenuItem("Mark as "+strArrStatus[x]); 
			menu.add(menuCheck[x]);
			menuCheck[x].setFont(UIManager.getFont("MenuItem.font").deriveFont(11));
			menuCheck[x].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg) {
					System.out.println("Command: "+arg.getActionCommand());
					
					switch (arg.getActionCommand()) {
						case "Mark as Deposited":
							JOptionPane.showOptionDialog(getContentPane(), panDeposit(strReference), arg.getActionCommand(),
									JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
							loadHist(modelHistory, rowHistory, strReference); 
							break;
						case "Mark as Bounced":
							JOptionPane.showOptionDialog(getContentPane(), panBounce(strReference), arg.getActionCommand(),
									JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
							loadHist(modelHistory, rowHistory, strReference); 
							break;
						default:
								JOptionPane.showMessageDialog(getContentPane(), "Not finished with this part yet.");
					}	
				}
			});
			menu.add(new JSeparator());
		}

		return menu; 
	}
	
	private String[] strArr(String strStatus) {
		String[] strArr = null; 
		
		if (strStatus.equals("Post Dated")) {
			strArr = new String[1]; 
			strArr[0] = "For Deposit";
		} else if (strStatus.equals("For Deposit")) {
			strArr = new String[3]; 
			strArr[0] = "Deposited";
			strArr[1] = "Replaced";
			strArr[2] = "Hold";
		} else if (strStatus.equals("Deposited")) {
			strArr = new String[2]; 
			strArr[0] = "Good";
			strArr[1] = "Bounced"; 	
		} else if (strStatus.equals("Bounced")) {
			strArr = new String[1];
			strArr[0] = "For Withdrawal";
		} else if (strStatus.equals("For Withdrawal")) {
			strArr = new String[1];
			strArr[0] = "Withdrawn";
		} else if (strStatus.equals("Withdrawn")) {
			strArr = new String[1];
			strArr[0] = "Retrnd to Buyer";
		}
		return strArr; 
	}

	private static JXPanel panDeposit(final String strRefNo) {
		final JXPanel panMain = new JXPanel(new BorderLayout(10, 10)); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		panMain.setPreferredSize(new Dimension(400, 400));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(2, 2)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 250));
				{
					{
						JXPanel panLine = new JXPanel(new GridLayout(8, 1, 10, 10)); 
						panPage.add(panLine, BorderLayout.LINE_START);
						panLine.setPreferredSize(new Dimension(100, 0));
						{
							String[] arrLabel = new String[] {
								"Company", 
								"Branch", 
								"Bank Acct. No.",
								"Bank Acct.", 
								"Bank",
								"Bank Branch",
								"Deposit Date", 
								"Collection Date",
							}; 
							
							JLabel[] lblLabel = new JLabel[8]; 
							
							for (int x=0; x<arrLabel.length; x++) {
								lblLabel[x] = new JLabel(arrLabel[x]); 
								panLine.add(lblLabel[x]); 
								lblLabel[x].setHorizontalAlignment(JLabel.LEADING);
							}
						}
					}
					{
						JXPanel panCenter = new JXPanel(new GridLayout(8, 1, 10, 10));
						panPage.add(panCenter, BorderLayout.CENTER); 
						{
							{
								JXPanel panCom = new JXPanel(new BorderLayout(2, 2)); 
								panCenter.add(panCom);
								{
									{
										txtCoID = new _JLookup(""); 
										panCom.add(txtCoID, BorderLayout.LINE_START); 
										txtCoID.setReturnColumn(0);
										txtCoID.setHorizontalAlignment(_JLookup.CENTER);
										txtCoID.setLookupSQL(lookupMethods.getCompany());
										txtCoID.setPreferredSize(new Dimension(50, 0));
										txtCoID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
												
												if (data!=null) {
													txtCo.setText(data[1].toString());
												}
											}
										});
									}
									{
										txtCo = new JTextField(""); 
										panCom.add(txtCo, BorderLayout.CENTER); 
										txtCo.setHorizontalAlignment(JTextField.CENTER);
									}
									
									txtCoID.setValue("02");
									txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");
								}
							}
							{
								JXPanel panBranch = new JXPanel(new BorderLayout(2, 2)); 
								panCenter.add(panBranch);
								{
									{
										txtOfficeBranchID = new _JLookup(""); 
										panBranch.add(txtOfficeBranchID, BorderLayout.LINE_START); 
										txtOfficeBranchID.setReturnColumn(0);
										txtOfficeBranchID.setHorizontalAlignment(_JLookup.CENTER);
										txtOfficeBranchID.setLookupSQL(lookupMethods.getOfficeBranch());
										txtOfficeBranchID.setPreferredSize(new Dimension(50, 0));
										txtOfficeBranchID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
												
												if (data!=null) {
													txtOfficeBranch.setText(data[1].toString());
												}
											}
										});
									}
									{
										txtOfficeBranch = new JTextField(""); 
										panBranch.add(txtOfficeBranch, BorderLayout.CENTER); 
										txtOfficeBranch.setHorizontalAlignment(JTextField.CENTER);
									}
									
									pgSelect dbExec = new pgSelect(); 
									dbExec.select("select y.branch_id, y.branch_name \n" + 
											"from rf_system_user x \n" + 
											"inner join mf_office_branch y on x.branch_id = y.branch_id \n" + 
											"where x.emp_code = '"+UserInfo.EmployeeCode+"'");
									if (dbExec.isNotNull()) {
										txtOfficeBranchID.setValue(dbExec.getResult()[0][0].toString());
										txtOfficeBranch.setText(dbExec.getResult()[0][1].toString());
									}
								}
							}
							{
								txtBankAcctNo = new _JLookup(""); 
								panCenter.add(txtBankAcctNo, BorderLayout.LINE_START); 
								txtBankAcctNo.setReturnColumn(1);
								txtBankAcctNo.setHorizontalAlignment(_JLookup.CENTER);
								txtBankAcctNo.setLookupSQL(lookupMethods.getBankAcct());
								txtBankAcctNo.setPreferredSize(new Dimension(75, 0));
								txtBankAcctNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
										
										if (data!=null) {
											txtBankAcct.setText(data[2].toString());
											txtAcctBank.setText(data[3].toString());
											txtAcctBranch.setText(data[4].toString());
										}
									}
								});
							}
							{
								txtBankAcct = new JTextField(""); 
								panCenter.add(txtBankAcct, BorderLayout.CENTER); 
							}
							{
								txtAcctBank = new JTextField(""); 
								panCenter.add(txtAcctBank, BorderLayout.CENTER); 
							}
							{
								txtAcctBranch = new JTextField(""); 
								panCenter.add(txtAcctBranch, BorderLayout.CENTER); 
							}
							{
								dteDepDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panCenter.add(dteDepDate);
								dteDepDate.getCalendarButton().setVisible(true);
								dteDepDate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
									public void propertyChange(PropertyChangeEvent evt) {
										String strDepDate = ""; 
										String strColDate = "";
										
										try {
											strDepDate = sdf.format(dteDepDate.getDate());
										} catch (NullPointerException ex) {
											strDepDate = "[Not Specified]"; 
										}
										
										try {
											strColDate = sdf.format(dteCollectionDate.getDate());
										} catch (NullPointerException ex) {
											strColDate = "[Not Specified]"; 
										}										

										txtNote.setText("TO RECORD DEPOSIT FOR "+strDepDate+" \n"+
												"FOR COLLECTION DATE "+strColDate);										
									}
								});
							}
							{
								dteCollectionDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panCenter.add(dteCollectionDate);
								dteCollectionDate.getCalendarButton().setVisible(true);
								dteCollectionDate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
									public void propertyChange(PropertyChangeEvent evt) {
										String strDepDate = ""; 
										String strColDate = "";
										
										try {
											strDepDate = sdf.format(dteDepDate.getDate());
										} catch (NullPointerException ex) {
											strDepDate = "[Not Specified]"; 
										}
										
										try {
											strColDate = sdf.format(dteCollectionDate.getDate());
										} catch (NullPointerException ex) {
											strColDate = "[Not Specified]"; 
										}										

										txtNote.setText("TO RECORD DEPOSIT FOR "+strDepDate+" \n"+
												"FOR COLLECTION DATE "+strColDate);									
									}
								});
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(2, 2)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				panCenter.setBorder(JTBorderFactory.createTitleBorder("Remarks", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					txtNote = new JTextArea();
					panCenter.add(txtNote, BorderLayout.CENTER); 
					txtNote.setEditable(true); 
					txtNote.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtNote.setLineWrap(true);
					txtNote.setWrapStyleWord(true);
					txtNote.setBorder(LINE_BORDER);
				}
			}
			{
				final JButton btnFinal = new JButton("Post"); 
				panMain.add(btnFinal, BorderLayout.PAGE_END); 
				btnFinal.setPreferredSize(new Dimension(0, 30));
				btnFinal.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) { 
						Window window = SwingUtilities.getWindowAncestor(btnFinal);
						
						if (!btnFinal.getText().equals("Press again to proceed")) {
							btnFinal.setText("Press again to proceed"); 
						} else {
							try {
								String[] strArr = new String[11];
								strArr[0] = txtCoID.getValue();
								strArr[1] = txtCo.getText(); 
								strArr[2] = txtOfficeBranchID.getValue(); 
								strArr[3] = txtOfficeBranch.getText(); 
								strArr[4] = txtBankAcctNo.getValue();
								strArr[5] = txtBankAcct.getText(); 
								strArr[6] = txtAcctBank.getText(); 
								strArr[7] = txtAcctBranch.getText(); 
								strArr[8] = dteCollectionDate.getDateString();  
								strArr[9] = dteDepDate.getDateString();
								strArr[10] = txtNote.getText(); 
								
								SaveHistory(strRefNo, "04","02", strArr[9], "");
								
								if (window!=null) {
									window.setVisible(false);
								}	
							} catch (NullPointerException ex) {
								btnFinal.setText("Some values where not supplied"); 
							}
						}
					}
				});
			}
			
		}
		
		SetFont(panMain); 
		return panMain;
	}
	
	private static JXPanel panBounce(final String strRefNo) {
		final JXPanel panMain = new JXPanel(new BorderLayout(10, 10)); 
		panMain.setPreferredSize(new Dimension(275, 100));
		{
			{
				JXPanel panLine = new JXPanel(new GridLayout(2, 1, 7, 7)); 
				panMain.add(panLine, BorderLayout.LINE_START); 
				panLine.setPreferredSize(new Dimension(50, 0));
				{
					panLine.add(new JLabel("Reason", JLabel.LEADING)); 
					panLine.add(new JLabel("Date", JLabel.LEADING));
				}
			}
			{
				JXPanel panCenter = new JXPanel(new GridLayout(2, 1, 7, 7)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					{
						JXPanel panReason = new JXPanel(new BorderLayout(2, 2)); 
						panCenter.add(panReason); 
						{
							{
								txtBounceReasonID = new _JLookup(""); 
								panReason.add(txtBounceReasonID, BorderLayout.LINE_START); 
								txtBounceReasonID.setReturnColumn(0);
								txtBounceReasonID.setHorizontalAlignment(_JLookup.CENTER);
								txtBounceReasonID.setLookupSQL(lookupMethods.getBounceReason());
								txtBounceReasonID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if (data!=null) {
											txtBounceReason.setText(data[1].toString());
										}
									}
								});
								txtBounceReasonID.setPreferredSize(new Dimension(50, 0));
							}
							{
								txtBounceReason = new JTextField(""); 
								panReason.add(txtBounceReason, BorderLayout.CENTER); 
							}
						}
					}
					{
						dteGeneral = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						panCenter.add(dteGeneral);
						dteGeneral.getCalendarButton().setVisible(true);
						dteGeneral.setDate(null);
					}
				}
			}
			{
				final JButton btnFinal = new JButton("Save"); 
				panMain.add(btnFinal, BorderLayout.PAGE_END); 
				btnFinal.setPreferredSize(new Dimension(0, 30));
				btnFinal.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) { 
						Window window = SwingUtilities.getWindowAncestor(btnFinal);
						
						if (!btnFinal.getText().equals("Press again to proceed")) {
							btnFinal.setText("Press again to proceed"); 
						} else {
							try {
								String[] strArr = new String[3];
								strArr[0] = txtBounceReasonID.getValue(); 
								strArr[1] = txtBounceReason.getText();  
								strArr[2] = dteGeneral.getDateString(); 
								
								SaveHistory(strRefNo, "04","03", strArr[2], strArr[0]);
								
								if (window!=null) {
									window.setVisible(false);
								}	
							} catch (NullPointerException ex) {
								btnFinal.setText("Some values where not supplied"); 
							}
						}
					}
				});
			}
		}
		
		SetFont(panMain);
		return panMain;
	}
	
	private static void SetFont(JXPanel panel) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JXPanel) {
				SetFont((JXPanel) comp);
			} else if (comp instanceof _JLookup) {
				((JTextField) comp).setHorizontalAlignment(JTextField.CENTER);
				((JTextField) comp).setFont(font);				
			} else if (comp instanceof JTextField) {
				((JTextField) comp).setHorizontalAlignment(JTextField.CENTER);
				((JTextField) comp).setEditable(false);
				((JTextField) comp).setFocusable(false);
				((JTextField) comp).setFont(font);
			} else {
				(comp.getClass().cast(comp)).setFont(font);
			}
		}
	}
	
	private static void SaveHistory(String refNo, String prevStat, String newStat, String strDate, String strBounceReason) {
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("insert into rf_check_history (pay_rec_id, prev_checkstat_id, new_checkstat_id, bounce_reason_id, trans_date, status_id, created_by, date_created, ref_no) \n" +
				"values (0, '"+prevStat+"', '"+newStat+"', '"+strBounceReason+"', '"+strDate+"'::timestamp, 'A', '"+UserInfo.EmployeeCode+"', now(), '"+refNo+"')", true);
		dbExec.commit();
	}
}
