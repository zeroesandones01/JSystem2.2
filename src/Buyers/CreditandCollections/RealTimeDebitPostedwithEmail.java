package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JDateChooser;

import Buyers.ClientServicing._CARD;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.*;
import tablemodel.modelMBTC_3;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class RealTimeDebitPostedwithEmail extends _JInternalFrame implements
		ActionListener, _GUI {

	private static final long serialVersionUID = -6750193546070394818L;
	static String title = "Real-Time Debit Posted (E-mail)";
	Dimension frameSize = new Dimension(700, 360);

	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private static JButton btnRefresh;
	private static JButton btnDir;
	private static JButton btnCreate;
	private static JButton btnSend;
	
	private JTextField txtDir;
	private JTextField txtPrompt;
	
	private JLabel lblDir;
	private JLabel lblDate;
	
	private JXPanel panProgressPrompt;
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	
	private modelMBTC_3 modelRTD;
	private static _JTableMain tblRTD;
	private static JList rowRTD;
	
	private JComboBox cboDate;
	
	private JXPanel panProgress;
	private Boolean blnSuper = false;
	private JProgressBar progressBar;
	private JTextArea taskOutput;
	private _JDateChooser dteDate;
	private Integer intProgress = 0;
	
	String[] strpdfFiles;
	String[] strMail;
	
	public RealTimeDebitPostedwithEmail() {
		super(title, true, true, false, true);
		initGUI();
	}

	public RealTimeDebitPostedwithEmail(String title) {
		super(title);
		initGUI();
	}

	public RealTimeDebitPostedwithEmail(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		String strUser = UserInfo.EmployeeCode;
		if (strUser.equals("900421") || strUser.equals("900748") || strUser.equals("987120")) {
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
				JXPanel panEndDiv = new JXPanel(new BorderLayout(5, 5));
				panPageEnd.add(panEndDiv,BorderLayout.CENTER);
				{
					JXPanel panDirDate = new JXPanel(new GridLayout(2, 1, 5, 5));
					panEndDiv.add(panDirDate, BorderLayout.LINE_START);
					panDirDate.setPreferredSize(new Dimension(400, 0));
					{
						{
							JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
							panDirDate.add(panDate, BorderLayout.CENTER);
							{
								{
									lblDate = new JLabel("Debit Date: ");
									panDate.add(lblDate, BorderLayout.LINE_START);
									lblDate.setPreferredSize(new Dimension(75, 0));
								}
								{
									cboDate = new JComboBox();
									panDate.add(cboDate, BorderLayout.CENTER);
									cboDate.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent event) {
											GenerateList(modelRTD, rowRTD, cboDate.getSelectedItem().toString());
										}
									});
									FillDates();
								}
								{
									btnRefresh = new JButton("Refresh");
									panDate.add(btnRefresh, BorderLayout.LINE_END);
									btnRefresh.setPreferredSize(new Dimension(70, 0));
									btnRefresh.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											FillDates();
										}
									});
								}
							}
						}
						{
							JXPanel panDir = new JXPanel(new BorderLayout(5, 5));
							panDirDate.add(panDir, BorderLayout.CENTER);
							{
								{
									lblDir = new JLabel("Directory: ");
									panDir.add(lblDir, BorderLayout.LINE_START);
									lblDir.setPreferredSize(new Dimension(75, 0));
								}
								{
									txtDir = new JTextField("");
									panDir.add(txtDir, BorderLayout.CENTER);
									txtDir.setHorizontalAlignment(JTextField.LEFT);
									txtDir.setEditable(false);
								}
								{
									btnDir = new JButton("Open");
									panDir.add(btnDir, BorderLayout.LINE_END);
									btnDir.setPreferredSize(new Dimension(70, 0));
									btnDir.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											txtDir.setText(_RealTimeDebit.OpenDir("File"));
										}
									});
								}
								autoDir();
								CheckDir();
							}
						}
					}
					{
						JXPanel panBut = new JXPanel(new GridLayout(1, 2, 5, 5));
						panEndDiv.add(panBut, BorderLayout.CENTER);
						{
							btnCreate = new JButton("Create SOA");
							panBut.add(btnCreate, BorderLayout.CENTER);
							btnCreate.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									new Thread(new Runnable() {
										public void run() {
											btnRefresh.setEnabled(false);
											btnDir.setEnabled(false);
											btnCreate.setEnabled(false);
											btnSend.setEnabled(false);
											
											Integer intCount = 0;
											for (int x = 0; x < modelRTD.getRowCount(); x++) {
												if ((Boolean) modelRTD.getValueAt(x, 0)) {
													intCount++;
												}
											}
											
											progressBar.setMaximum(intCount);
											strpdfFiles = new String[intCount];
											strMail = new String[intCount];
											
											InitiateProgress(true);
											for (int x = 0; x < modelRTD.getRowCount(); x++) {
												if ((Boolean) modelRTD.getValueAt(x, 0)) {
													txtPrompt.setText("Generating SOA for " + modelRTD.getValueAt(x, 2).toString() + "...");
													strpdfFiles[x] = CreateSOA(modelRTD.getValueAt(x, 2).toString(), modelRTD.getValueAt(x, 11).toString(), modelRTD.getValueAt(x, 12).toString(), modelRTD.getValueAt(x, 13).toString(), modelRTD.getValueAt(x, 14).toString());
													
													txtPrompt.setText("Acquiring E-mail address...");
													strMail[x] =  FncGlobal.GetString("select c_email from view_ci_client_info('"+modelRTD.getValueAt(x, 11)+"')");
													//strMail[x] = "penmanship27@gmail.com";
													//strMail[x] = "apostol_emman@yahoo.com";
													AppendProgress();
												}
											}
											
											txtPrompt.setText("Finished creating SOA...");
											JOptionPane.showMessageDialog(null, "SOA pdf files created!");
											
											InitiateProgress(false);
											
											btnCreate.setEnabled(true);
											btnSend.setEnabled(true);
											btnRefresh.setEnabled(false);
											btnDir.setEnabled(false);
										}
									}).start();
								}
							});
						}
						{
							btnSend = new JButton("Send E-mail");
							panBut.add(btnSend, BorderLayout.CENTER);
							btnSend.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent mail) {
									new Thread(new Runnable() {
										public void run() {
											Boolean blnSent = false;

									        String from = "collection.verdantpoint@yahoo.com.ph";
									        String pass = "c0ll3ct10n2017";
									        
									        /*	Mark
									        String from = "apostol_emman@yahoo.com";
									        String pass = "ishkkhakfeandunull";
									        */
									        
									        String subject = "CENQHOMES DEVELOPMENT CORPORATION - STATEMENT OF ACCOUNT";
											
									        String nl = System.getProperty("line.separator");
								            StringBuffer sb = new StringBuffer();
								            sb.append("Please see attached Statement of Account");
								            sb.append(nl);
								            sb.append("Thank you.");
								            sb.append(nl);
								            sb.append(nl);
								            sb.append("Credit and Collection");
								            sb.append(nl);
								            sb.append("Mobile No.: 09209097133");
								            sb.append(nl);
								            sb.append("Tel. No.: 533-8459");
									        
											Integer intCount = 0;
											for (int x = 0; x < modelRTD.getRowCount(); x++) {
												if ((Boolean) modelRTD.getValueAt(x, 0)) {
													intCount++;
												}
											}
											progressBar.setMaximum(intCount);
									        
									        InitiateProgress(true);
											
											btnRefresh.setEnabled(false);
											btnDir.setEnabled(false);
											btnCreate.setEnabled(false);
											btnSend.setEnabled(false);
											
											for (int x = 0; x < strpdfFiles.length; x++) {
												if ((Boolean) strMail[x].equals("")) {
													JOptionPane.showMessageDialog(getContentPane(), modelRTD.getValueAt(x, 2).toString() + " has no recorded E-mail address.");
												} else {
													if ((Boolean) modelRTD.getValueAt(x, 0)) {
												        txtPrompt.setText("Sending E-mail to " + strMail[x] + "...");
												        blnSent = _RealTimeDebit.sendFromMail(from, pass, strMail[x], subject, sb, txtDir.getText() + "/" + strpdfFiles[x], strpdfFiles[x]);
												        AppendProgress();
												        
														System.out.println("");
														System.out.println(strpdfFiles[x]);
														System.out.println(strMail[x]);		
													}
												}
											}

											txtPrompt.setText("done...");
											if (blnSent) {
												JOptionPane.showMessageDialog(getContentPane(), "E-mail Sent!");
											} else {
												JOptionPane.showMessageDialog(getContentPane(), "E-mail sending failed!");
											}
											
											btnRefresh.setEnabled(true);
											btnDir.setEnabled(true);
											btnCreate.setEnabled(true);
											btnSend.setEnabled(false);
											
											InitiateProgress(false);
										}
									}).start();
								}
							});
							btnSend.setEnabled(false);
						}
					}	
				}
			}
			{
				dteDate = new _JDateChooser(FncGlobal.getDateToday());
			}
		}
	}
	
	public void CreateTable() {
		pnlTab = new JXPanel(new BorderLayout(5, 5));
		pnlTab.setOpaque(isOpaque());
		{
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
					tblRTD.getColumnModel().getColumn(2).setPreferredWidth(250);
					tblRTD.getColumnModel().getColumn(3).setPreferredWidth(75);
					tblRTD.getColumnModel().getColumn(4).setPreferredWidth(130);
	
					tblRTD.getColumnModel().getColumn(6).setPreferredWidth(130);
					tblRTD.getColumnModel().getColumn(8).setPreferredWidth(130);
					tblRTD.getColumnModel().getColumn(9).setPreferredWidth(130);
					tblRTD.getColumnModel().getColumn(10).setPreferredWidth(250);
	
					rowRTD = tblRTD.getRowHeader();
					rowRTD.setModel(new DefaultListModel());
					scrTab.setRowHeaderView(rowRTD);
					scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
				{
					tblRTD.hideColumns("Reason", "Sales Group", "Branch", "entity_id", "proj_id", "pbl_id", "seq_no");
				}
			}
			{
				panProgressPrompt = new JXPanel(new GridLayout(1, 2, 5, 5));
				pnlTab.add(panProgressPrompt, BorderLayout.PAGE_END);
				panProgressPrompt.setPreferredSize(new Dimension(0, 30));
				{
					{
						progressBar = new JProgressBar(0, 100);
						panProgressPrompt.add(progressBar, BorderLayout.CENTER);
						progressBar.setValue(0);
						progressBar.setStringPainted(true);						
					}
					{
						txtPrompt = new JTextField();
						panProgressPrompt.add(txtPrompt, BorderLayout.CENTER);
						txtPrompt.setHorizontalAlignment(JTextField.LEADING);
						txtPrompt.setFont(new Font("Tahoma", Font.PLAIN, 10));
						txtPrompt.setBackground(Color.BLACK);
						txtPrompt.setForeground(Color.WHITE);
						txtPrompt.setEditable(false);
					}
				}
				panProgressPrompt.setVisible(false);
			}
		}
	}
	
	private void GenerateList(DefaultTableModel modelMain, JList rowHeader, String dteDate) {
		String strSQL = "select true as tag, '' as reason, a.client as name, a.project as project, a.unit, '', \n" +
				"a.account_no as AccountNumber, '' as Branch, a.debit_date as LastDebitDate, a.debit_amount as LastDebitAmt, \n" + 
				"a.status as status, a.entity_id, a.proj_id, a.pbl_id, a.seq_no \n" +
				"from view_rtd() a \n" +
				"where a.debit_date::DATE = '"+dteDate+"'::DATE \n" +
				"order by a.client";  
		
		try {
			FncTables.clearTable(modelMain);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Out ofStage bounds exception occured.");
		}
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		
		if (db.isNotNull()) {
			progressBar.setMaximum(db.getRowCount());
			
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
		};
	}
	
	private void FillDates() {
		String strSQL = "select distinct trim(debit_date::date::varchar(20)) from rf_rtd_values order by trim(debit_date::date::varchar(20)) desc";
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				System.out.println("");
				System.out.println("Loop Date: " + db.getResult()[x][0].toString());
				
				cboDate.addItem(db.getResult()[x][0].toString());
			}
		}
	}
	
	private void autoDir() {
		String strDir = "";
		String strComName = _RealTimeDebit.getComputerName();
		
		System.out.println("");
		System.out.println(strComName);

		String SQL = "SELECT def_dir FROM rf_rtd_details";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				strDir = (String) db.getResult()[x][0];
				
				System.out.println((String) db.getResult()[x][0]);
				
				File f = new File((String) db.getResult()[x][0]);
				if(f.exists() && f.isDirectory()) {
					String SQL_Rev = "UPDATE rf_rtd_details \n" +
									 "SET host_name = TRIM('"+strComName+"') \n" +
									 "WHERE TRIM(def_dir) = TRIM('"+strDir+"')";
					
					System.out.println("");
					System.out.println(SQL_Rev);
					
					pgUpdate db_Rev = new pgUpdate();
					db_Rev.executeUpdate(SQL_Rev, false);
					db_Rev.commit();	
				}
			}
		}
	}
	
    private void CheckDir() {
       	String strDir = "";
    	String strHostName = _RealTimeDebit.getComputerName();
		String SQL = "SELECT def_dir \n" +
					 "FROM rf_rtd_details \n" +
					 "WHERE created_by = '"+UserInfo.EmployeeCode+"' OR TRIM(COALESCE(host_name, '')) = TRIM('"+strHostName+"') \n" +
					 "ORDER BY rtd_id ASC";

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		System.out.println("");
		System.out.println(SQL);
		
		File f = new File(strDir);
		
		if (db.isNotNull()) {
			System.out.println("");
			System.out.println("With directory set.");
			System.out.println("db.getRowCount(): " + db.getRowCount());
			
			/*	Changes as of November 24, 2016	*/
			if (db.getRowCount()>1) {
				for (int i = 0; i < db.getRowCount(); i++) {
					f = new File((String) db.getResult()[i][0]);
					
					System.out.println("");
					System.out.println((String) db.getResult()[i][0]);
					
					if (f.exists() && f.isDirectory()) {
						strDir = (String) db.getResult()[i][0];
					}
				}
			} else {
				strDir = (String)db.getResult()[0][0];	
			}
		} else {
			strDir = "";
		}
 
		f = new File(strDir);
		
		if(!f.exists() && !f.isDirectory()) {
			JOptionPane.showMessageDialog(null, "Default directory not set or does not exist.\nSet a directory wherein files would be saved.");
		}
		else {
			txtDir.setText(strDir);
		}
    }

	private String CreateSOA(String strName, String strID, String strPro, String strUnit, String strSeq) {
		String strDir = "";
		
		Integer inti = new Integer(strSeq);
		Object [] data = _CARD.displayClientInformation(strID, strPro, strUnit, inti, true);
		
		String entity_name = (String) data[1];
		String proj_name = (String) data[3];
		String unit_desc = (String) data[5];
		String house_model = (String) data[8];
		String buyer_type_id = (String) data[9];
		String pmt_scheme = (String) data[12];
		BigDecimal gsp = (BigDecimal) data[15];
		BigDecimal discount = (BigDecimal) (data[16] == null ? new BigDecimal("0.00"):data[16]);
		BigDecimal nsp = gsp.subtract(discount);
		BigDecimal dp = (BigDecimal) data[17];
		BigDecimal availed_amt = (BigDecimal) data[18];
		BigDecimal os_balance = (BigDecimal) data[19];
		Integer ma_terms = (Integer) data[24];
		BigDecimal int_rate = (BigDecimal) data[36];
		
		Map<String, Object> mapSOA = new HashMap<String, Object>();
		mapSOA.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapSOA.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapSOA.put("entity_id", strID);
		mapSOA.put("proj_id", strPro);
		mapSOA.put("pbl_id", strUnit);
		mapSOA.put("seq_no", inti);

		mapSOA.put("entity_name", entity_name);
		mapSOA.put("proj_name", proj_name);
		mapSOA.put("unit_desc", unit_desc);

		mapSOA.put("payment_scheme", pmt_scheme);
		mapSOA.put("house_model", house_model);

		mapSOA.put("gsp", gsp);
		mapSOA.put("vat", new BigDecimal("0.00"));
		mapSOA.put("discount", discount);
		mapSOA.put("nsp", nsp);
		mapSOA.put("dp", dp);
		mapSOA.put("availed_amount", availed_amt);
		mapSOA.put("os_balance", os_balance);
		mapSOA.put("terms", ma_terms);
		mapSOA.put("int_rate", int_rate);

		mapSOA.put("type_group_id", buyer_type_id);
		mapSOA.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
		mapSOA.put("pmt_nth_month", false);
		mapSOA.put("months", "1");
		mapSOA.put("pmt_all", false);
		
		dteDate.setDate(FncGlobal.getDateToday());
		
		mapSOA.put("date_cut_off", dteDate.getTimestamp());
		mapSOA.put("to_update", false);
		mapSOA.put("to_full_settle", false);
		mapSOA.put("pmt_type", "CASH");
		mapSOA.put("img_hand_pointer", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "handpointer.png"));
		
		FncReport.generatePDF("/Reports/rptStatementOfAccount.jasper", "Statement of Account", mapSOA, txtDir.getText() + "/" + strName + " - SOA.pdf");
		
		strDir = strName + " - SOA.pdf";
		return strDir;
    }
	
	private void AppendProgress() {
		intProgress = intProgress + 1;
		progressBar.setValue(intProgress);
	}
	
	private void InitiateProgress(Boolean blnDo) {
		if (blnDo) {
			progressBar.setValue(0);
			intProgress = 0;
		}
		
		panProgressPrompt.setVisible(blnDo);
	}
	
	/*
	panProgressPrompt.setVisible(true);
	
	txtPrompt.setText("Generating SOA for " + modelRTD.getValueAt(x, 2).toString() + "...");
	String strFile = CreateSOA(modelRTD.getValueAt(x, 2).toString(), modelRTD.getValueAt(x, 11).toString(), modelRTD.getValueAt(x, 12).toString(), modelRTD.getValueAt(x, 13).toString(), modelRTD.getValueAt(x, 14).toString());
	AppendProgress();
	
	txtPrompt.setText("Acquiring E-mail address...");
	String strEmail =  FncGlobal.GetString("select c_email from view_ci_client_info('"+modelRTD.getValueAt(x, 11)+"')");
	
	if ((Boolean) strEmail.equals("")) {
		JOptionPane.showMessageDialog(getContentPane(), modelRTD.getValueAt(x, 2).toString() + " has no recorded E-mail address.");
	}
	
    String from = "collection.verdantpoint@yahoo.com.ph";
    String pass = "c0ll3ct10n2016";
    String[] to = new String[]{"penmanship27@gmail.com"};
    String subject = "CENQHOMES DEVELOPMENT CORPORATION - STATEMENT OF ACCOUNT";
    String body = "";
    
    txtPrompt.setText("Sending E-mail to " + strEmail + "...");
    blnSent = _RealTimeDebit.sendFromMail(from, pass, to, subject, body, txtDir.getText() + "/" + strFile, strFile);
    AppendProgress();
    */
}
