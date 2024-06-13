package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
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

public class RealTimeDebitPosting_LoanReleased extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -8523330824696650932L;
	static String title = "Real-Time Debit Posting(Loan Released)";
	Dimension frameSize = new Dimension(650, 360);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	private static JButton btnRefresh;
	private static JButton btnUpload;
	private static JButton btnPreview;
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	private modelMBTC_2 modelRTD;	
	public static _JTableMain tblRTD;
	public static JList rowRTD;

	public static JTextField txtReason;
	
	private String[] strRPLF = new String[0]; 
	
	public RealTimeDebitPosting_LoanReleased() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public RealTimeDebitPosting_LoanReleased(String title) {
		super(title);
		initGUI(); 
	}

	public RealTimeDebitPosting_LoanReleased(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {

		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPageStart = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPageStart, BorderLayout.CENTER);
			panPageStart.setPreferredSize(new Dimension(0, 150));
			panPageStart.setBorder(JTBorderFactory.createTitleBorder("Client List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				CreateTable();
				panPageStart.add(pnlTab, BorderLayout.CENTER);
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.PAGE_END);
			panCenter.setPreferredSize(new Dimension(550, 150));
			panCenter.setBorder(JTBorderFactory.createTitleBorder("Account Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				JXPanel panParam = new JXPanel(new GridLayout(2, 1, 5, 5));
				panCenter.add(panParam, BorderLayout.CENTER);
				{

				}
			}
			JXPanel panPageEnd = new JXPanel(new GridLayout(1, 3, 5, 5));
			panMain.add(panPageEnd, BorderLayout.PAGE_END);
			panPageEnd.setPreferredSize(new Dimension(0, 30));
			{
				btnRefresh = new JButton("Refresh");
				panPageEnd.add(btnRefresh, BorderLayout.CENTER);
				btnRefresh.setActionCommand("Refresh");
				btnRefresh.addActionListener(this);
			}
			{
				btnUpload = new JButton("Post");
				panPageEnd.add(btnUpload, BorderLayout.CENTER);
				btnUpload.setActionCommand("Post");
				btnUpload.addActionListener(this);
			}
			{
				btnPreview = new JButton("Preview");
				panPageEnd.add(btnPreview, BorderLayout.CENTER);
				btnPreview.setActionCommand("Preview");
				btnPreview.addActionListener(this);
				btnPreview.setEnabled(false);
			}
		}
		
		if (_RealTimeDebit.rtdPostingLoanReleased(modelRTD, rowRTD)) {
			btnUpload.setEnabled(true);
		} else {
			btnUpload.setEnabled(false);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Post")) {
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
				String SQL_debitDate = "UPDATE rf_pay_header SET booking_date = '"+tblRTD.getValueAt(intRow, 4).toString()+"'::timestamp WHERE client_seqno = '"+strOPParam+"'";
				dbDate.executeUpdate(SQL_debitDate, false);
				dbDate.commit();
				
				strOP = strOP + "* " + strOPParam + " - " + tblRTD.getValueAt(intRow, 0).toString() + "\n"
						+ "Project: " + tblRTD.getValueAt(intRow, 1).toString() + "\n"
						+ "Unit: " + tblRTD.getValueAt(intRow, 2).toString() + "\n"
						+ "Particular: " + tblRTD.getValueAt(intRow, 3).toString() + "\n"
						+ "Debit Date: " + tblRTD.getValueAt(intRow, 4).toString() + "\n"
						+ "Debit Amount: " + tblRTD.getValueAt(intRow, 5).toString() + "\n\n";
				
				String strIns = "insert into rf_rtd_report_format (client_seqno, client, project, unit, particular, debit_date, amount) values \n" +
						"( \n" +
						"'"+strOPParam+"', \n" +
						"'"+tblRTD.getValueAt(intRow, 0).toString()+"'::varchar, \n" +
						"'"+tblRTD.getValueAt(intRow, 1).toString()+"'::varchar, \n" +
						"'"+tblRTD.getValueAt(intRow, 2).toString()+"'::varchar, \n" +
						"'"+tblRTD.getValueAt(intRow, 3).toString()+"'::varchar, \n" +
						"'"+tblRTD.getValueAt(intRow, 4).toString()+"'::date, \n" +
						"'"+tblRTD.getValueAt(intRow, 5).toString()+"'::numeric \n" +
						")";
				pgUpdate dbIns = new pgUpdate();
				dbIns.executeUpdate(strIns, true);
				dbIns.commit();
			}
			
			System.out.println("");
			System.out.println(strOP);
			
			CreateRPLF(); 
			//Generate();

			JOptionPane.showMessageDialog(getContentPane(), "Sequence numbers created!", "Posting Finished", JOptionPane.INFORMATION_MESSAGE);
			
			try {
			    Thread.sleep(2000);
			} catch (InterruptedException inter) {
				System.out.println("Interrupted!");
			}
			
			_RealTimeDebit.Delete_LoanReleased();
			btnUpload.setEnabled(false);
			btnPreview.setEnabled(true);
		} else if (e.getActionCommand().equals("Refresh")) {
			FncGlobal.startProgress("Refreshing");
			if (_RealTimeDebit.rtdPostingLoanReleased(modelRTD, rowRTD)) {
				btnUpload.setEnabled(true);
			} else {
				btnUpload.setEnabled(false);
			}
			btnPreview.setEnabled(false);
			FncGlobal.stopProgress();
		} else if (e.getActionCommand().equals("Preview")) {
			int arrayLength = strRPLF.length;
			
			System.out.println(""); 
			System.out.println("arrayLength: " + arrayLength);
			
			for (int x = 0; x < arrayLength; x++) {
				System.out.println(""); 
				System.out.println("strRPLF: " + strRPLF[x]); 
				
				Generate(strRPLF[x].toString());
			}
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
				modelRTD = new modelMBTC_2();
				modelRTD.setEditable(false);
				tblRTD = new _JTableMain(modelRTD);
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
				tblRTD.hideColumns("Tag", "Sales Group", "Branch");
			}
		}
	}
	
	private String[] CreateRPLF() {
		String strCoID = "";
		String strExec = "";
		strRPLF = new String[FncGlobal.GetInteger("select count(*)::int \n" + 
				"from rf_rtd_values_loanreleased a \n" + 
				"inner join mf_project b on a.projcode = b.proj_id \n" +
				"where a.status_id = 'A' \n" +
				"group by b.co_id, a.projcode")]; 
		
		pgSelect dbLoop = new pgSelect(); 
		pgUpdate dbExec = new pgUpdate(); 
		
		dbLoop.select("select b.co_id, a.projcode \n" + 
				"from rf_rtd_values_loanreleased a \n" + 
				"inner join mf_project b on a.projcode = b.proj_id \n" +
				"where a.status_id = 'A' \n" +
				"group by b.co_id, a.projcode");
		
		if (dbLoop.isNotNull()) {
			for (int x=0; x < dbLoop.getRowCount(); x++) {
				System.out.println("");
				System.out.println("(String) dbLoop.getResult()[x][0].toString(): " + (String) dbLoop.getResult()[x][0].toString());
				
				strCoID = (String) dbLoop.getResult()[x][0].toString(); 
				
				strRPLF[x] = FncGlobal.GetString("select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+(String) dbLoop.getResult()[x][0].toString()+"'");
				
				System.out.println("");
				System.out.println("select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+(String) dbLoop.getResult()[x][0].toString()+"'");
				System.out.println("RPLF: " + strRPLF[x]);
				
				strExec = "INSERT INTO rf_request_header VALUES ("
						+ "'"+strCoID+"', '"+strCoID+"', '"+strRPLF[x]+"', now(), now(), NULL, '01', "
						+ "'5153446583', '5153446583', '13', NULL, '09', 1, NULL, NULL, "
						+ "'', 'A', '"+UserInfo.EmployeeCode+"', now(), NULL, NULL, 'B')";
			
				System.out.println(""); 
				System.out.println(strExec); 
				
				dbExec.executeUpdate(strExec, false);
				dbExec.commit();

				strExec = "INSERT INTO rf_request_detail \n" +  
						"select b.co_id, b.co_id, '"+strRPLF[x]+"', ROW_NUMBER() OVER(), NULL, NULL, now(), FALSE, 'MA remittance for the period ' || a.debit_date::date::varchar || '; ', '01-01-04-056', \n" +
						"a.entity_id || a.projcode || a.pbl_id || a.seq_no, sum(a.debit_amt)::NUMERIC(19, 2) as amount, '5153446583', '13', a.projcode, d.sub_proj_id, NULL, NULL, NULL, NULL, NULL, false, false, false, false, \n" + 
						"'18', 0::NUMERIC(19, 2), 0::NUMERIC(19, 2), NULL, 0::NUMERIC(19, 2), 0::NUMERIC(19, 2), sum(a.debit_amt)::NUMERIC(19, 2) as amount, sum(a.debit_amt)::NUMERIC(19, 2) as amount, \n" + 
						"NULL, NULL, NULL, NULL, 'A', '"+UserInfo.EmployeeCode+"', now(), NULL, NULL, NULL, NULL, NULL, NULL \n" + 
						"from rf_rtd_values_loanreleased a \n" + 
						"inner join mf_project b on a.projcode = b.proj_id \n" + 
						"inner join mf_unit_info c on a.projcode = c.proj_id and a.pbl_id = c.pbl_id \n" + 
						"inner join mf_sub_project d ON a.projcode = d.proj_id AND c.phase = d.phase AND d.status_id = 'A' \n" + //ADDED STATUS ID BY LESTER DCRF 2718
						"where a.status_id = 'A' \n" +
						"group by b.co_id, b.co_id, a.debit_date, a.projcode, d.sub_proj_id, a.entity_id, a.projcode, a.pbl_id, a.seq_no";

				System.out.println(""); 
				System.out.println(strExec);
				
				dbExec = new pgUpdate();
				dbExec.executeUpdate(strExec, false);
				dbExec.commit();
				
				Generate(strRPLF[x]); 
			}
		}
		
		return strRPLF; 
	}
	
	private void Generate(String strRPLF) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("user_code", UserInfo.EmployeeCode);
		mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("rplf_no", strRPLF);
		FncReport.generateReport("/Reports/rpt_MBTC_LoanReleased_v2.jasper", "Real-Time Debit List", "", mapParameters);
	}
}
