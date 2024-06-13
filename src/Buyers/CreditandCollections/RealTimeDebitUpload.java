package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.apache.poi.ss.formula.functions.Now;
import org.jdesktop.swingx.JXPanel;

import tablemodel.modelMBTC;
import tablemodel.modelMBTC_2;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class RealTimeDebitUpload extends _JInternalFrame implements
		ActionListener {

	private static final long serialVersionUID = 3811792406130105992L;
	static String title = "Real-Time Debit Download";
	Dimension frameSize = new Dimension(550, 360);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	private JButton btnDir;
	public static JButton btnUpload;
	public static JButton btnSave;
	
	private JTextField txtDir;
	private JTextField txtStatus;
	private JTextField txtDebitAmt;
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	
	private modelMBTC_2 modelRTD;
	
	public static _JTableMain tblRTD;
	public static JList rowRTD;
	
	private JLabel lblDebitAmt;
	private JLabel lblReason;
	
	public static JTextField txtReason;
	
	private Boolean blnSuper = false;
	
	public RealTimeDebitUpload() {
		super(title, true, true, false, true);
		InitGUI();
	}

	public RealTimeDebitUpload(String title) {
		super(title);
		InitGUI();
	}

	public RealTimeDebitUpload(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		InitGUI();
	}
	
	public void InitGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		String strUser = UserInfo.EmployeeCode;
		if (strUser.equals("900421") || strUser.equals("900748") || strUser.equals("987120") || strUser.equals("900414")) {
			blnSuper = true;
		}
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPageStart = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPageStart, BorderLayout.PAGE_START);
			panPageStart.setPreferredSize(new Dimension(0, 150));
			panPageStart.setBorder(JTBorderFactory.createTitleBorder("Client List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				CreateTable();
				panPageStart.add(pnlTab, BorderLayout.CENTER);
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			panCenter.setBorder(JTBorderFactory.createTitleBorder("Account Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				JXPanel panParam = new JXPanel(new GridLayout(2, 1, 5, 5));
				panCenter.add(panParam, BorderLayout.CENTER);
				{
					/*
					JXPanel panDueAmt = new JXPanel(new GridLayout(1, 1, 10, 5));
					panParam.add(panDueAmt);
					{
						JXPanel panDue = new JXPanel(new BorderLayout(5, 5));
						panDueAmt.add(panDue, BorderLayout.CENTER);
						{
							lblDue = new JLabel("Due Date");
							//panDue.add(lblDue, BorderLayout.LINE_START);
							lblDue.setPreferredSize(new Dimension(95, 0));
						}
						{
							dteDue = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							//panDue.add(dteDue);
							dteDue.getCalendarButton().setVisible(true);
							dteDue.setEditable(false);
						}
					}
					*/
					JXPanel panAmt = new JXPanel(new BorderLayout(5, 5));
					panParam.add(panAmt, BorderLayout.CENTER);
					{
						lblDebitAmt = new JLabel("Debit Amount");
						panAmt.add(lblDebitAmt, BorderLayout.LINE_START);
						lblDebitAmt.setPreferredSize(new Dimension(95, 0));
					}
					{
						txtDebitAmt = new JTextField("0.00");
						panAmt.add(txtDebitAmt, BorderLayout.CENTER);
						txtDebitAmt.setHorizontalAlignment(JTextField.CENTER);
						txtDebitAmt.setEditable(false);
					}
					JXPanel panReason = new JXPanel(new BorderLayout(5, 5));
					panParam.add(panReason);
					{
						lblReason = new JLabel("Reason");
						panReason.add(lblReason, BorderLayout.LINE_START);
						lblReason.setPreferredSize(new Dimension(95, 0));
					}
					{
						txtReason = new JTextField("");
						panReason.add(txtReason, BorderLayout.CENTER);
						txtReason.setHorizontalAlignment(JTextField.LEADING);
						txtReason.setEditable(false);
					}
				}
			}
			JXPanel panPageEnd = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPageEnd, BorderLayout.PAGE_END);
			panPageEnd.setPreferredSize(new Dimension(0, 65));
			{
				JXPanel panEndDiv = new JXPanel(new GridLayout(2, 1, 5, 5));
				panPageEnd.add(panEndDiv,BorderLayout.CENTER);
				{
					JXPanel panDir = new JXPanel(new BorderLayout(5, 5));
					panEndDiv.add(panDir, BorderLayout.CENTER);
					{
						txtStatus = new JTextField("---");
						panDir.add(txtStatus, BorderLayout.LINE_START);
						txtStatus.setEditable(false);
						txtStatus.setHorizontalAlignment(JTextField.CENTER);
						txtStatus.setBackground(Color.BLACK);
						txtStatus.setForeground(Color.WHITE);
						txtStatus.setPreferredSize(new Dimension(90, 0));
					}
					{
						txtDir = new JTextField("");
						panDir.add(txtDir, BorderLayout.CENTER);
						txtDir.setHorizontalAlignment(JTextField.LEADING);
						txtDir.setEditable(false);
					}
					{
						btnDir = new JButton("Open");
						panDir.add(btnDir, BorderLayout.LINE_END);
						btnDir.setActionCommand("Open");
						btnDir.addActionListener(this);
						btnDir.setPreferredSize(new Dimension(70, 0));
					}
				}
				{
					JXPanel panBut = new JXPanel(new GridLayout(1, 2, 5, 5));
					panEndDiv.add(panBut, BorderLayout.PAGE_START);
					{
						btnSave = new JButton("");
						panBut.add(btnSave, BorderLayout.CENTER);
						btnSave.setEnabled(false);
					}
					{
						btnUpload = new JButton("Preview");
						panBut.add(btnUpload, BorderLayout.CENTER);
						btnUpload.setActionCommand("Preview");
						btnUpload.addActionListener(this);
						btnUpload.setEnabled(false);
					}
				}
			}
		}
		deleteI();
		UserClearance(blnSuper);
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
				modelRTD = new modelMBTC_2();
				modelRTD.setEditable(false);
				tblRTD = new _JTableMain(modelRTD);
				tblRTD.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(!e.getValueIsAdjusting()){
							System.out.println("");
							System.out.println("Selected row " + tblRTD.getSelectedRow());
							
							Integer intRow = tblRTD.convertRowIndexToModel(tblRTD.getSelectedRow());
							
							String strName = (String)tblRTD.getValueAt(intRow, 0);
							String strUnit = (String)tblRTD.getValueAt(intRow, 2);
							String strUser = UserInfo.EmployeeCode;
							
							if (intRow!=-1) {
								/*
								txtCreditCode.setText(_RealTimeDebit.GetValue("SELECT c_branch_code FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtCreditAcct.setText(_RealTimeDebit.GetValue("SELECT c_acct_no FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtDebitCode.setText(_RealTimeDebit.GetValue("SELECT d_branch_code FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtDebitAcct.setText(_RealTimeDebit.GetValue("SELECT d_acct_no FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtSubscriber.setText(_RealTimeDebit.GetValue("SELECT subscriber_no FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								*/
								System.out.println("");
								System.out.println("SELECT TRIM(debit_amt::CHAR(10)) FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'");
								
								txtDebitAmt.setText(_RealTimeDebit.GetValue("SELECT TRIM(debit_amt::CHAR(10)) FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"'"));
								txtReason.setText(_RealTimeDebit.GetValue("SELECT reason FROM rf_rtd_values A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"'"));
								
								Date dateValue = (Date)tblRTD.getValueAt(intRow, 6); // What ever column
								String strDate = dateValue.toString();
								java.util.Date date;
								
								System.out.println("");
								System.out.println("dateValue: " + dateValue);
								System.out.println("strDate: " + strDate);
								
								try {
									date = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
									//dteDue.setDate(date);
								} catch (ParseException e1) {
									System.out.print("");
									System.out.print("Parse Exception Caught!");
								}
							}
						}
					}
				});
				
				rowRTD = tblRTD.getRowHeader();
				scrTab.setViewportView(tblRTD);
				
				tblRTD.getColumnModel().getColumn(0).setPreferredWidth(250);
				tblRTD.getColumnModel().getColumn(1).setPreferredWidth(155);
				tblRTD.getColumnModel().getColumn(2).setPreferredWidth(155);
				tblRTD.getColumnModel().getColumn(3).setPreferredWidth(155);
				tblRTD.getColumnModel().getColumn(4).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(5).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(6).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(7).setPreferredWidth(130);

				rowRTD = tblRTD.getRowHeader();
				rowRTD.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowRTD);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
			{
				tblRTD.hideColumns("Tag");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Open")) {
			Clearbox();
			txtDir.setText(_RealTimeDebit.OpenDir("File"));
			
			deleteI();
			
			String strCount = "";
			Integer intCount = 0;
			try {
				strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(2)) FROM rf_rtd_values WHERE status_id IN ('O')"); 
				intCount = Integer.parseInt(strCount);	
			} catch (NullPointerException e1) {
				intCount = 0;
			}
			
			if (intCount > 0) {
				if (JOptionPane.showConfirmDialog(getContentPane(), "Include this to the transactions for checking?", "With Pending For Checking", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					txtStatus.setText(_RealTimeDebit.peruser(txtDir.getText()));
					_RealTimeDebit.rtdDisplay(modelRTD, rowRTD);
					btnSave.setEnabled(true);
					JOptionPane.showMessageDialog(getContentPane(), "Done!");
				}
			} else {
				txtStatus.setText(_RealTimeDebit.peruser(txtDir.getText()));
				_RealTimeDebit.rtdDisplay(modelRTD, rowRTD);
				btnSave.setEnabled(true);
				JOptionPane.showMessageDialog(getContentPane(), "Done!");
			}
			
			UserClearance(blnSuper);
		} else if (e.getActionCommand().equals("Preview")) {
			/*
			for(Integer intRow = 0; intRow < tblRTD.getRowCount(); intRow++) {
				Generate("");
			}
			*/
			Generate("");
		} else if (e.getActionCommand().equals("SaveForChecking")) {
			pgUpdate db_Rev = new pgUpdate();
			String SQL_Rev = "UPDATE rf_rtd_values SET status_id = 'O', debit_date = Now()::TIMESTAMP WHERE status_id = 'I'";
			db_Rev.executeUpdate(SQL_Rev, false);
			db_Rev.commit();
			
			JOptionPane.showMessageDialog(getContentPane(), "Saved for checking!");
			btnSave.setEnabled(false);
			btnUpload.setEnabled(true);
		} else if (e.getActionCommand().equals("SaveForPosting")) {
			pgUpdate db_Rev = new pgUpdate();
			String SQL_Rev = "UPDATE rf_rtd_values SET status_id = 'A', debit_date = (case when debit_date is null then now() else debit_date end) WHERE status_id IN ('I', 'O')";
			db_Rev.executeUpdate(SQL_Rev, false);
			db_Rev.commit();
			
			JOptionPane.showMessageDialog(getContentPane(), "Saved for posting!");
			btnSave.setEnabled(false);
			btnUpload.setEnabled(true);
		}
	}
	
	private void Clearbox() {
		txtDebitAmt.setText("0.00");
		txtReason.setText("");
	}
	
	private void Generate(String strSequence) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("user_code", UserInfo.EmployeeCode);
		mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("sequence", strSequence);
		FncReport.generateReport("/Reports/rpt_MBTC.jasper", "Real-Time Debit List", "", mapParameters);
	}
	
	private void UserClearance(Boolean blnRev) {
		System.out.print("");
		System.out.print("Clearance Level 5");
		
		if (blnRev) {
			btnSave.setText("Save For Posting");
			btnSave.setActionCommand("SaveForPosting");
			btnSave.addActionListener(this);
			//btnSave.setEnabled(true);
		} else {
			btnSave.setText("Save For Checking");
			btnSave.setActionCommand("SaveForChecking");
			btnSave.addActionListener(this);
			//btnSave.setEnabled(true);
		}
		
		_RealTimeDebit.rtdForPosting(modelRTD, rowRTD, blnSuper);
		
		if (blnRev) {
			if (tblRTD.getRowCount() > 0) {
				btnSave.setEnabled(true);
				btnUpload.setEnabled(true);
				btnDir.setEnabled(true);
			} else {
				btnSave.setEnabled(false);
				btnUpload.setEnabled(false);
				btnDir.setEnabled(true);				
			}
		}
		
		if (tblRTD.getRowCount()>0) {
			btnUpload.setEnabled(true);
		}
	}
	
	private void deleteI() {
		pgUpdate db_Rev = new pgUpdate();
		String SQL_Rev = "DELETE FROM rf_rtd_values WHERE status_id = 'I'";
		db_Rev.executeUpdate(SQL_Rev, false);
		db_Rev.commit();
	}
}