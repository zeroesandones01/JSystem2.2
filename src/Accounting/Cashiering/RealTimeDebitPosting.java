package Accounting.Cashiering;

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

import tablemodel.modelMBTC_2;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class RealTimeDebitPosting extends _JInternalFrame implements
		ActionListener {

	private static final long serialVersionUID = -7279740896319944977L;
	static String title = "Real-Time Debit Posting";
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
	
	public RealTimeDebitPosting() {
		super(title, true, true, false, true);
		InitGUI();
	}

	public RealTimeDebitPosting(String title) {
		super(title);
		InitGUI();
	}

	public RealTimeDebitPosting(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		InitGUI();
	}

	public void InitGUI() {
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
		
		if (_RealTimeDebit.rtdPosting(modelRTD, rowRTD)) {
			btnUpload.setEnabled(true);
		} else {
			btnUpload.setEnabled(false);
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
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Post")) {
			System.out.println("");
			System.out.println("Creating OP!");
			
			String strOP = "";
			
			pgUpdate dbDelete = new pgUpdate();
			dbDelete.executeUpdate("delete from rf_rtd_report_format", false);
			dbDelete.commit();
			
			for (Integer intRow = 0; intRow < tblRTD.getRowCount(); intRow++) {
				String strOPParam = _RealTimeDebit.CreateOP(tblRTD.getValueAt(intRow, 0).toString(), tblRTD.getValueAt(intRow, 2).toString());
				
				pgUpdate db_Rev = new pgUpdate();
				String SQL_Rev = "UPDATE rf_pay_detail SET part_type = '033', receipt_type  = '03', ar_no = 'MBTC' WHERE client_seqno = '"+strOPParam+"'";
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
			
			Generate();
			JOptionPane.showMessageDialog(getContentPane(), "Sequence numbers created!", "Posting Finished", JOptionPane.INFORMATION_MESSAGE);
			
			try {
			    Thread.sleep(2000);
			} catch (InterruptedException inter) {
				System.out.println("Interrupted!");
			}
			
			_RealTimeDebit.Delete();
			btnUpload.setEnabled(false);
			btnPreview.setEnabled(true);
		} else if (e.getActionCommand().equals("Refresh")) {
			FncGlobal.startProgress("Refreshing");
			if (_RealTimeDebit.rtdPosting(modelRTD, rowRTD)) {
				btnUpload.setEnabled(true);
			} else {
				btnUpload.setEnabled(false);
			}
			btnPreview.setEnabled(false);
			FncGlobal.stopProgress();
		} else if (e.getActionCommand().equals("Preview")) {
			Generate();
		}
	}
	
	private void Generate() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("user_code", UserInfo.EmployeeCode);
		mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		FncReport.generateReport("/Reports/rpt_MBTCv2.jasper", "Real-Time Debit List", "", mapParameters);
	}
}