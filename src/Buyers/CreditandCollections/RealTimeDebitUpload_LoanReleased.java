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

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelMBTC_2;

public class RealTimeDebitUpload_LoanReleased extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -415343465725645775L;
	static String title = "Real-Time Debit Download(Loan Released)";
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
	
	private String global_RPLF; 
	
	public RealTimeDebitUpload_LoanReleased() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public RealTimeDebitUpload_LoanReleased(String title) {
		super(title);
		initGUI(); 
	}

	public RealTimeDebitUpload_LoanReleased(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		String strUser = UserInfo.EmployeeCode;
		/*
		if (strUser.equals("900421")) {
			blnSuper = true;
		}
		*/
		
		blnSuper = true;
		
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
						btnSave = new JButton("Save For Posting");
						panBut.add(btnSave, BorderLayout.CENTER);
						btnSave.setActionCommand("SaveForPosting");
						btnSave.addActionListener(this);
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
		delete();
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
								txtCreditCode.setText(_RealTimeDebit.GetValue("SELECT c_branch_code FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtCreditAcct.setText(_RealTimeDebit.GetValue("SELECT c_acct_no FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtDebitCode.setText(_RealTimeDebit.GetValue("SELECT d_branch_code FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtDebitAcct.setText(_RealTimeDebit.GetValue("SELECT d_acct_no FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								txtSubscriber.setText(_RealTimeDebit.GetValue("SELECT subscriber_no FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'"));
								*/
								System.out.println("");
								System.out.println("SELECT TRIM(debit_amt::CHAR(10)) FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"' AND A.user_id = '"+strUser+"'");
								
								txtDebitAmt.setText(_RealTimeDebit.GetValue("SELECT TRIM(debit_amt::CHAR(10)) FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"'"));
								txtReason.setText(_RealTimeDebit.GetValue("SELECT reason FROM rf_rtd_values_loanreleased A INNER JOIN rf_entity B ON A.entity_id = B.entity_id INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id WHERE TRIM(B.entity_name) = '"+strName+"' AND TRIM(C.Description) = '"+strUnit+"'"));
								
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
			
			delete();
			
			String strCount = "";
			Integer intCount = 0;
			
			try {
				strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(2)) FROM rf_rtd_values_loanreleased WHERE status_id IN ('A')"); 
				intCount = Integer.parseInt(strCount);	
			} catch (NullPointerException e1) {
				intCount = 0;
			}
			
			if (intCount > 0) {
				if (JOptionPane.showConfirmDialog(getContentPane(), "Include this to the transactions for checking?", "With Pending For Checking", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					txtStatus.setText(_RealTimeDebit.peruserLoanReleased(txtDir.getText()));
					_RealTimeDebit.rtdDisplayLoanReleased(modelRTD, rowRTD);
					btnSave.setEnabled(true);
					JOptionPane.showMessageDialog(getContentPane(), "Done!");
				}
			} else {
				txtStatus.setText(_RealTimeDebit.peruserLoanReleased(txtDir.getText()));
				_RealTimeDebit.rtdDisplayLoanReleased(modelRTD, rowRTD);
				btnSave.setEnabled(true);
				JOptionPane.showMessageDialog(getContentPane(), "Done!");
			}
			
			UserClearance(blnSuper);
			global_RPLF = ""; 
		} else if (e.getActionCommand().equals("Preview")) {
			Generate("", global_RPLF);
			if (!btnSave.isEnabled()) {
				GeneratePaymentList();	
			}
		} else if (e.getActionCommand().equals("SaveForChecking")) {
			/*
			pgUpdate db_Rev = new pgUpdate();
			String SQL_Rev = "UPDATE rf_rtd_values_loanreleased SET status_id = 'O', debit_date = Now()::TIMESTAMP WHERE status_id = 'I'";
			db_Rev.executeUpdate(SQL_Rev, false);
			db_Rev.commit();
			
			JOptionPane.showMessageDialog(getContentPane(), "Saved for checking!");
			btnSave.setEnabled(false);
			btnUpload.setEnabled(true);
			*/
		} else if (e.getActionCommand().equals("SaveForPosting")) {
			pgUpdate db_Rev = new pgUpdate();
			String SQL_Rev = "UPDATE rf_rtd_values_loanreleased \n" +
					"SET status_id = 'A', debit_date = (case when debit_date is null then now() else debit_date end) \n" +
					"WHERE status_id IN ('I')";
			db_Rev.executeUpdate(SQL_Rev, false);
			db_Rev.commit();
			
			CreateRPLF(); 
			CreateOP(); 
			Generate("", global_RPLF);
			GeneratePaymentList(); 
			
			JOptionPane.showMessageDialog(getContentPane(), "Saved for posting!");
			btnSave.setEnabled(false);
			btnUpload.setEnabled(true);
		}
	}
	
	private void Clearbox() {
		txtDebitAmt.setText("0.00");
		txtReason.setText("");
	}
	
	private void Generate(String strSequence, String RPLF) {
		System.out.println("");
		System.out.println("RPLF: " + RPLF);
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("user_code", UserInfo.EmployeeCode);
		mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("sequence", strSequence);
		mapParameters.put("rplf_no", RPLF);
		FncReport.generateReport("/Reports/rpt_MBTC_LoanReleased.jasper", "Real-Time Debit List", "", mapParameters);
	}
	
	private void UserClearance(Boolean blnRev) {
		System.out.print("");
		System.out.print("Clearance Level 5");

		_RealTimeDebit.rtdForPostingLoanReleased(modelRTD, rowRTD, blnSuper);
		
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
	
	private void delete() {
		pgUpdate db_Rev = new pgUpdate();
		String SQL_Rev = "DELETE FROM rf_rtd_values_loanreleased WHERE status_id = 'I'";
		db_Rev.executeUpdate(SQL_Rev, false);
		db_Rev.commit();
		
		db_Rev = new pgUpdate();
		SQL_Rev = "UPDATE rf_rtd_values_loanreleased SET status_id = 'I' WHERE status_id = 'O'";
		db_Rev.executeUpdate(SQL_Rev, false);
		db_Rev.commit();
	}
	
	private void CreateRPLF() {
		String strCoID = "";
		String strExec = "";
		
		pgSelect dbLoop = new pgSelect(); 
		pgUpdate dbExec = new pgUpdate(); 
		
		strCoID = "02"; 
		
		global_RPLF = FncGlobal.GetString("select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+strCoID+"'");

		strExec = "INSERT INTO rf_request_header VALUES ("
				+ "'"+strCoID+"', '"+strCoID+"', '"+global_RPLF+"', now(), now(), NULL, '01', "
				+ "'5153446583', '5153446583', '13', NULL, '09', 1, NULL, NULL, "
				+ "'', 'A', '"+UserInfo.EmployeeCode+"', now(), NULL, NULL, 'B')";
		
		dbExec.executeUpdate(strExec, false);
		dbExec.commit();
		
		strExec = "INSERT INTO rf_request_detail \n" +  
				"select b.co_id, b.co_id, '"+global_RPLF+"', ROW_NUMBER() OVER(), NULL, NULL, now(), FALSE, 'MA remittance for the period ' || a.debit_date::date::varchar || '; ', '01-01-04-056', \n" + 
				"a.entity_id || a.projcode || a.pbl_id || a.seq_no, sum(a.debit_amt)::NUMERIC(19, 2) as amount, '5153446583', '13', a.projcode, d.sub_proj_id, NULL, NULL, NULL, NULL, NULL, false, false, false, false, \n" + 
				"'18', 0::NUMERIC(19, 2), 0::NUMERIC(19, 2), NULL, 0::NUMERIC(19, 2), 0::NUMERIC(19, 2), sum(a.debit_amt)::NUMERIC(19, 2) as amount, sum(a.debit_amt)::NUMERIC(19, 2) as amount, \n" + 
				"NULL, NULL, NULL, NULL, 'A', '"+UserInfo.EmployeeCode+"', now(), NULL, NULL, NULL, NULL, NULL, NULL \n" + 
				"from rf_rtd_values_loanreleased a \n" + 
				"inner join mf_project b on a.projcode = b.proj_id \n" + 
				"inner join mf_unit_info c on a.projcode = c.proj_id and a.pbl_id = c.pbl_id \n" + 
				"inner join mf_sub_project d ON a.projcode = d.proj_id AND c.phase = d.phase and d.status_id = 'A'\n" + //ADDED STATUS ID BY LESTER DCRF 2718
				"where a.status_id = 'A' \n" +
				"group by b.co_id, b.co_id, a.debit_date, a.projcode, d.sub_proj_id, a.entity_id, a.projcode, a.pbl_id, a.seq_no";

		dbExec = new pgUpdate();
		dbExec.executeUpdate(strExec, false);
		dbExec.commit();
	}
	
	private void CreateOP() {
		String strID = "";
		String strProject = "";
		String strUnitID = "";
		String strSequence = "";
		
		System.out.println("");
		System.out.println("Creating OP!");
		
		String strOP = "";
		
		pgUpdate dbDelete = new pgUpdate();
		dbDelete.executeUpdate("delete from rf_rtd_report_format", false);
		dbDelete.commit();
		
		for (Integer intRow = 0; intRow < tblRTD.getRowCount(); intRow++) {

			String strOPParam = _RealTimeDebit.CreateOPLoanReleased(tblRTD.getValueAt(intRow, 0).toString(), tblRTD.getValueAt(intRow, 2).toString());
			
			pgUpdate db_Rev = new pgUpdate();
			String SQL_Rev = "UPDATE rf_pay_detail SET part_type = '185', receipt_type  = '08', ar_no = 'MBTC' WHERE client_seqno = '"+strOPParam+"'";
			db_Rev.executeUpdate(SQL_Rev, false);
			db_Rev.commit();
			
			pgUpdate dbDate = new pgUpdate();
			String SQL_debitDate = "UPDATE rf_pay_header SET booking_date = '"+tblRTD.getValueAt(intRow, 6).toString()+"'::timestamp WHERE client_seqno = '"+strOPParam+"'";
			dbDate.executeUpdate(SQL_debitDate, false);
			dbDate.commit();
			
			strOP = strOP + "* " + strOPParam + " - " + tblRTD.getValueAt(intRow, 0).toString() + "\n"
					+ "Project: " + tblRTD.getValueAt(intRow, 1).toString() + "\n"
					+ "Unit: " + tblRTD.getValueAt(intRow, 2).toString() + "\n"
					+ "Particular: " + tblRTD.getValueAt(intRow, 4).toString() + "\n"
					+ "Debit Date: " + tblRTD.getValueAt(intRow, 6).toString() + "\n"
					+ "Debit Amount: " + tblRTD.getValueAt(intRow, 7).toString() + "\n\n";
			
			String strIns = "insert into rf_rtd_report_format (client_seqno, client, project, unit, particular, debit_date, amount) values \n" +
					"( \n" +
					"'"+strOPParam+"', \n" +
					"'"+tblRTD.getValueAt(intRow, 0).toString()+"'::varchar, \n" +
					"'"+tblRTD.getValueAt(intRow, 1).toString()+"'::varchar, \n" +
					"'"+tblRTD.getValueAt(intRow, 2).toString()+"'::varchar, \n" +
					"'"+tblRTD.getValueAt(intRow, 4).toString()+"'::varchar, \n" +
					"'"+tblRTD.getValueAt(intRow, 6).toString()+"'::date, \n" +
					"'"+tblRTD.getValueAt(intRow, 7).toString()+"'::numeric \n" +
					")";

			pgUpdate dbIns = new pgUpdate();
			dbIns.executeUpdate(strIns, true);
			dbIns.commit();
			
			/*	Insert Payments	*/
			strID = FncGlobal.GetString("SELECT entity_id FROM rf_entity WHERE entity_name = '"+tblRTD.getValueAt(intRow, 0).toString()+"'");
			strUnitID = FncGlobal.GetString("SELECT a.pbl_id FROM rf_sold_unit A \n" +
					"INNER JOIN mf_unit_info B ON a.projcode = b.proj_id and a.pbl_id = b.pbl_id \n" +
					"INNER JOIN rf_entity C ON a.entity_id = c.entity_id \n" +
					"WHERE c.entity_name = '"+tblRTD.getValueAt(intRow, 0).toString()+"' and b.description = '"+tblRTD.getValueAt(intRow, 2).toString()+"'");
			strProject = FncGlobal.GetString("SELECT projcode FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnitID+"' and status_id = 'A'");
			strSequence = FncGlobal.GetString("SELECT seq_no::CHAR(1) FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnitID+"' and status_id = 'A'");
			
			Date dteDate = FncGlobal.GetDate("SELECT '"+tblRTD.getValueAt(intRow, 6).toString()+"'::date - cast(extract(dow from '"+tblRTD.getValueAt(intRow, 6).toString()+"'::date) as int) + 1;"); 
			
			strIns = "insert into rf_hdmf_payments (rec_id, entity_id, proj_id, pbl_id, seq_no, client_seqno, trans_date, actual_date, amount, \n" +  
					"receipt_no, rplf_no, pay_type, status_id, branch_id, remarks, co_id, issued_by) values \n" + 
					"(nextval('rf_hdmf_payments_rec_id_seq'), '"+strID+"', '"+strProject+"', '"+strUnitID+"', '"+strSequence+"'::int, '"+strOPParam+"', '"+tblRTD.getValueAt(intRow, 6).toString()+"', '"+dteDate.toString()+"', '"+tblRTD.getValueAt(intRow, 7).toString()+"'::numeric(19, 2), \n" +  
					"'', coalesce((select rplf_no from rf_request_detail where remarks::varchar = ('"+strID+"' || '"+strProject+"' || '"+strUnitID+"' || '"+strSequence+"')::varchar \n" + 
					"order by ref_doc_date desc limit 1), ''), 'A', 'A', '01', 'Payment from MBTC', '02', '"+UserInfo.EmployeeCode+"'); ";
			
			dbIns = new pgUpdate();
			dbIns.executeUpdate(strIns, true);
			dbIns.commit();
		}
		
		System.out.println("");
		System.out.println(strOP);
	}
	
	private void GeneratePaymentList() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("user_code", UserInfo.EmployeeCode);
		mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		FncReport.generateReport("/Reports/rpt_MBTC_LoanReleased_v2.jasper", "Real-Time Debit Payment List", "", mapParameters);
	}
}
