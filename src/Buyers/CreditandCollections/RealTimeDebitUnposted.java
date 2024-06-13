package Buyers.CreditandCollections;

import interfaces._GUI;

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
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelMBTC_3;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import Buyers.CreditandCollections._RealTimeDebit;

public class RealTimeDebitUnposted extends _JInternalFrame implements
		ActionListener, _GUI {

	private static final long serialVersionUID = -2193552051582929475L;
	static String title = "Real-Time Debit Unposted";
	Dimension frameSize = new Dimension(700, 360);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	private static JButton btnDir;
	private static JButton btnPreview;
	private static JButton btnDeac;
	
	private JTextField txtDir;
	private JTextField txtStatus;
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	
	private modelMBTC_3 modelRTD;
	private static _JTableMain tblRTD;
	private static JList rowRTD;
	
	private Boolean blnSuper = false;
	
	public RealTimeDebitUnposted() {
		super(title, true, true, false, true);
		initGUI();
	}

	public RealTimeDebitUnposted(String title) {
		super(title);
		initGUI();
	}

	public RealTimeDebitUnposted(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		String strUser = UserInfo.EmployeeCode;
		if (strUser.equals("900421")||strUser.equals("900748")) {
			blnSuper = true;
		}
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			panCenter.setPreferredSize(new Dimension(0, 150));
			panCenter.setBorder(JTBorderFactory.createTitleBorder("Client List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				CreateTable();
				panCenter.add(pnlTab, BorderLayout.CENTER);
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
						btnDir.setPreferredSize(new Dimension(70, 0));
						btnDir.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								txtDir.setText(_RealTimeDebit.OpenDir("File"));
								_RealTimeDebit.peruser_alpha(txtDir.getText(), modelRTD, rowRTD);
								GenerateList(modelRTD, rowRTD);
								GenerateReport();
							}
						});
					}
				}
				{
					JXPanel panBut = new JXPanel(new GridLayout(1, 2, 5, 5));
					panEndDiv.add(panBut, BorderLayout.PAGE_START);
					{
						btnPreview = new JButton("Preview");
						panBut.add(btnPreview, BorderLayout.CENTER);
						btnPreview.setEnabled(false);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								GenerateReport();
							}
						});
					}
					{
						btnDeac = new JButton("Deactivate");
						panBut.add(btnDeac, BorderLayout.CENTER);
						btnDeac.setEnabled(false);
						btnDeac.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (blnSuper) {
									DeactivateAccounts();
									GenerateList(modelRTD, rowRTD);			
								} else {
									JOptionPane.showMessageDialog(getContentPane(), "You are not authorized to deactivate accounts.", "Caution", JOptionPane.INFORMATION_MESSAGE);
								}						
							}
						});
					}
				}
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
				modelRTD = new modelMBTC_3();
				modelRTD.setEditable(false);
				tblRTD = new _JTableMain(modelRTD);
				
				rowRTD = tblRTD.getRowHeader();
				scrTab.setViewportView(tblRTD);
				
				tblRTD.getColumnModel().getColumn(0).setPreferredWidth(30);
				tblRTD.getColumnModel().getColumn(1).setPreferredWidth(250);
				tblRTD.getColumnModel().getColumn(2).setPreferredWidth(250);
				tblRTD.getColumnModel().getColumn(3).setPreferredWidth(75);
				tblRTD.getColumnModel().getColumn(4).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(5).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(6).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(7).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(8).setPreferredWidth(130);
				tblRTD.getColumnModel().getColumn(9).setPreferredWidth(130);

				rowRTD = tblRTD.getRowHeader();
				rowRTD.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowRTD);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
			{
				tblRTD.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
			}
		}
	}
	
	private void GenerateList(DefaultTableModel modelMain, JList rowHeader) {
		pgUpdate dbDeac = new pgUpdate();
		dbDeac.executeUpdate("update rf_rtd_unposted set status_id = 'I'", false);
		dbDeac.commit();
		
		pgUpdate dbExec = new pgUpdate();
		String strSQL = "insert into rf_rtd_unposted \n" +
				"select b.reason, a.entity_name, a.proj_alias, a.description, a.salesgroup, a.account_no, a.bankbranch, a.debit_date, \n" + 
				"a.debit_amount, a.file_name, a.entity_id, a.projcode, a.pbl_id, a.seq_no, a.status_id, Now()::date \n" +
				"from view_rtd_unposted() a \n" +
				"inner join tmp_rtd_unposted b on a.entity_name ~* b.entity_name and a.entity_id = b.entity_id and a.pbl_id = b.pbl_id";
		dbExec.executeUpdate(strSQL, false);
		dbExec.commit();
		
		strSQL = "select false as tag, a.status as reason, a.entity_name as name, a.proj_alias as project, \n" +
				"a.description, a.salesgroup, a.account_no, a.bankbranch, a.debit_date, a.debit_amount, \n" +
				"(case when b.endorsed = true then 'ACTIVE' ELSE 'INACTIVE' END) as status, \n" +
				"a.entity_id, a.projcode, a.pbl_id, a.seq_no \n" +
				"from rf_rtd_unposted a \n" +
				"inner join rf_rtd_accounts b on a.entity_id = b.entity_id and a.pbl_id = b.pbl_id \n" +
				"where a.status_id = 'A' \n" +
				"order by a.entity_name;";
		
		try {
			FncTables.clearTable(modelMain);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Out of bounds exception occured.");
		}
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			btnPreview.setEnabled(true);
			btnDeac.setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
		};
	}
	
	private void GenerateReport() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		FncReport.generateReport("/Reports/rpt_MBTCv3.jasper", "Real-Time Debit Unposted List", "", mapParameters);
	}
	
	private void DeactivateAccounts() {
		Boolean blnWith = false;
		Boolean blnProceed = false;
		
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to deactivate these accounts?", "Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			blnProceed = true;
		}
		
		if (blnProceed) {
			for (int x = 0; x < modelRTD.getRowCount(); x++) {
				if ((Boolean) modelRTD.getValueAt(x, 0)) {
					blnWith = true;
					
					String strSQL = "";
					pgUpdate dbExec = new pgUpdate();
					strSQL = "update rf_rtd_accounts a \n" +
							"set endorsed = false, date_closed = '"+FncGlobal.getDateSQL()+"', closed_by = '"+UserInfo.EmployeeCode+"' \n" +
							"where entity_id = '"+modelRTD.getValueAt(x, 11)+"' and pbl_id = '"+modelRTD.getValueAt(x, 13)+"' and replace(account_no, '-', '') = '"+modelRTD.getValueAt(x, 6)+"'";
					dbExec.executeUpdate(strSQL, false);
					dbExec.commit();
				}
			}
			
			if (!blnWith) {
				JOptionPane.showMessageDialog(null, "No row was selected.");
			} else {
				JOptionPane.showMessageDialog(null, "Account(s) successfully deactivated.");
			}
		}
	}
}
