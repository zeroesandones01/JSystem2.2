package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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

import interfaces._GUI;

import org.jdesktop.swingx.JXPanel;

import tablemodel.modelMBTC;
import tablemodel.modelPisoDebit;
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
import components._JCheckBox;
import components._JTableMain;

public class panMBTC_Debit extends JXPanel implements _GUI {

	private static final long serialVersionUID = -8214624127199360721L;
	private RealTimeDebitPiso rtdMain;
	Dimension size = new Dimension(750, 450);

	JFileChooser Chooser;
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	static Border lineYellow = BorderFactory.createLineBorder(Color.YELLOW);
	static Border lineGreen = BorderFactory.createLineBorder(Color.GREEN);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlack = BorderFactory.createLineBorder(Color.BLACK);
	
	private JXPanel pnlTab;
	private JScrollPane scrTab;
	private JScrollPane scrLog;
	private modelPisoDebit modelRTD;
	public static _JTableMain tblRTD;
	public static JList rowRTD;
	
	private JButton btnExport;
	private JButton btnRead;
	private JButton btnRefresh;
	private JButton btnPreview;
	private JButton btnPost;
	private JButton btnOpen;
	
	private JLabel lblCo;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JLabel lblDir;
	
	private _JCheckBox chkShaw;
	
	private static _JLookup txtCoID;
	private static _JLookup txtBranchID;
	
	private static JTextField txtCo;	
	private static JTextField txtDir;
	private static JTextField txtBranch;
	
	private static _JDateChooser dteFrom;
	private static _JDateChooser dteTo;
	
	private static String strBranch = "";

	private JTextArea txtLog;
	private final static String newline = "\n";
	
	private static JProgressBar progressBar;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public panMBTC_Debit(RealTimeDebitPiso rtdPiso) {
		this.rtdMain = rtdPiso;
		initGUI();
	}

	public panMBTC_Debit(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public panMBTC_Debit(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public panMBTC_Debit(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			{
				JXPanel panPgStart = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panPgStart, BorderLayout.PAGE_START);
				panPgStart.setPreferredSize(new Dimension(450, 200));
				{
					JXPanel panSubMain1 = new JXPanel(new BorderLayout(5, 5));
					panPgStart.add(panSubMain1, BorderLayout.LINE_START);
					panSubMain1.setPreferredSize(new Dimension(450, 0));
					{
						JXPanel pnlMainParam1 = new JXPanel(new BorderLayout(5, 5));
						panSubMain1.add(pnlMainParam1, BorderLayout.CENTER);
						{
							JXPanel pnlLabelBox = new JXPanel(new GridLayout(1, 1, 5, 10));
							pnlMainParam1.add(pnlLabelBox, BorderLayout.PAGE_START);
							pnlLabelBox.setPreferredSize(new Dimension(100, 60));
							pnlLabelBox.setBorder(JTBorderFactory.createTitleBorder("Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								JXPanel pnlCoLabel = new JXPanel(new BorderLayout(5, 5));
								pnlLabelBox.add(pnlCoLabel, BorderLayout.CENTER);
								{
									lblCo = new JLabel("Company:");
									pnlCoLabel.add(lblCo, BorderLayout.LINE_START);
									lblCo.setPreferredSize(new Dimension(80, 0));
									lblCo.setHorizontalAlignment(JTextField.RIGHT);
								}
								{
									JXPanel pnlIDName = new JXPanel(new BorderLayout(5, 5));
									pnlCoLabel.add(pnlIDName, BorderLayout.CENTER);
									{
										txtCoID = new _JLookup("");
										pnlIDName.add(txtCoID, BorderLayout.LINE_START);
										txtCoID.setHorizontalAlignment(JTextField.CENTER);
										txtCoID.setPreferredSize(new Dimension(60, 0));
										txtCoID.setLookupSQL(sqlCo());
										txtCoID.setReturnColumn(0);
										txtCoID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtCo.setText(data[1].toString());
												}
											}
										});
									}
									{
										txtCo = new JTextField("---");
										pnlIDName.add(txtCo, BorderLayout.CENTER);
										txtCo.setHorizontalAlignment(JTextField.CENTER);
										txtCo.setEditable(false);
									}
								}
							}
							JXPanel pnlLabelOth = new JXPanel(new GridLayout(2, 1, 5, 10));
							pnlMainParam1.add(pnlLabelOth, BorderLayout.CENTER);
							pnlLabelOth.setBorder(JTBorderFactory.createTitleBorder("Date Activated", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								JXPanel pnlDateSep1 = new JXPanel(new BorderLayout(5, 5));
								pnlLabelOth.add(pnlDateSep1);
								{
									lblFrom = new JLabel("Date From:");
									pnlDateSep1.add(lblFrom, BorderLayout.LINE_START);
									lblFrom.setPreferredSize(new Dimension(80, 0));
									lblFrom.setHorizontalAlignment(JTextField.RIGHT);
								}
								{
									dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlDateSep1.add(dteFrom, BorderLayout.CENTER);
									dteFrom.setPreferredSize(new Dimension(150, 0));
									dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
								
								JXPanel pnlDateSep2 = new JXPanel(new BorderLayout(5, 5));
								pnlLabelOth.add(pnlDateSep2);
								{
									lblTo = new JLabel("Date To:");
									pnlDateSep2.add(lblTo, BorderLayout.LINE_START);
									lblTo.setHorizontalAlignment(JTextField.RIGHT);
									lblTo.setPreferredSize(new Dimension(80, 0));
								}
								{
									dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlDateSep2.add(dteTo, BorderLayout.CENTER);
									dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
							JXPanel pnlButton = new JXPanel(new GridLayout(1, 6, 5, 5));
							pnlMainParam1.add(pnlButton, BorderLayout.PAGE_END);
							pnlButton.setPreferredSize(new Dimension(0, 30));
							pnlButton.setBorder(new EmptyBorder(2, 5, 2, 5));
							{
								JXPanel pnlChk1 = new JXPanel(new BorderLayout(5, 5));
								pnlButton.add(pnlChk1, BorderLayout.CENTER);
								pnlChk1.setPreferredSize(new Dimension(100, 0));
								{
									lblDir = new JLabel("Directory:");
									pnlChk1.add(lblDir, BorderLayout.LINE_START);
									lblDir.setPreferredSize(new Dimension(80, 0));
									lblDir.setHorizontalAlignment(JTextField.RIGHT);
								}
								{
									JXPanel pnlIDName = new JXPanel(new BorderLayout(5, 5));
									pnlChk1.add(pnlIDName, BorderLayout.CENTER);
									{
										txtDir = new JTextField("C:/");
										pnlIDName.add(txtDir, BorderLayout.CENTER);
										txtDir.setHorizontalAlignment(JTextField.LEADING);
										txtDir.setPreferredSize(new Dimension(40, 0));
										txtDir.setEditable(false);
									}
									{
										btnOpen = new JButton("Set");
										pnlIDName.add(btnOpen, BorderLayout.LINE_END);
										btnOpen.setPreferredSize(new Dimension(60, 0));
										btnOpen.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												SaveDir(_RealTimeDebit.OpenDir("Folder"));
											}
										});
									}
								}
							}
						}
					}
					JXPanel panSubMain2 = new JXPanel(new BorderLayout(5, 5));
					panPgStart.add(panSubMain2, BorderLayout.CENTER);
					panSubMain2.setPreferredSize(new Dimension(500, 0));
					{
						JXPanel panLogChk1 = new JXPanel(new GridLayout(1, 1, 5, 5));
						panSubMain2.add(panLogChk1, BorderLayout.PAGE_START);
						panLogChk1.setPreferredSize(new Dimension(0, 60));
						panLogChk1.setBorder(JTBorderFactory.createTitleBorder("Bank Branch", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel pnlChk2 = new JXPanel(new BorderLayout(5, 5));
							panLogChk1.add(pnlChk2, BorderLayout.CENTER);
							pnlChk2.setPreferredSize(new Dimension(0, 24));
							{
								txtBranchID = new _JLookup("254");
								pnlChk2.add(txtBranchID, BorderLayout.CENTER);
								txtBranchID.setHorizontalAlignment(JTextField.CENTER);
								txtBranchID.setLookupSQL(_RealTimeDebit.sql_Branch(""));
								txtBranchID.setReturnColumn(0);
								txtBranchID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											txtBranch.setText(data[1].toString());
										}
									}
								});
							}
							{
								txtBranch = new JTextField("METROBANK DIRECT - WACK-WACK");
								pnlChk2.add(txtBranch, BorderLayout.LINE_END);
								txtBranch.setPreferredSize(new Dimension(220, 0));
								txtBranch.setHorizontalAlignment(JTextField.CENTER);
								txtBranch.setEditable(false);
							}
							strBranch = "10";
						}
						JXPanel panLogChk2 = new JXPanel(new GridLayout(1, 1, 5, 5));
						panSubMain2.add(panLogChk2, BorderLayout.CENTER);
						panLogChk2.setBorder(JTBorderFactory.createTitleBorder("Log", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtLog = new JTextArea("");
							txtLog.setFont(new Font("Tahoma", Font.PLAIN, 8));
							txtLog.setBackground(Color.BLACK);
							txtLog.setForeground(Color.WHITE);
							txtLog.setEditable(false);
							
							scrLog = new JScrollPane(txtLog);
							panLogChk2.add(scrLog);
						}
						
					}	
				}
				Setdefaults();
				JXPanel panPgEnd = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panPgEnd, BorderLayout.CENTER);
				{
					JXPanel panTable = new JXPanel(new BorderLayout(5, 5));
					panPgEnd.add(panTable, BorderLayout.CENTER);
					{
						CreateTable();
						panTable.add(pnlTab);
					}
					JXPanel panFinal = new JXPanel(new GridLayout(1, 4, 5, 5));
					panPgEnd.add(panFinal, BorderLayout.PAGE_END);
					panFinal.setPreferredSize(new Dimension(0, 30));
					{
						btnRefresh = new JButton("Generate");
						panFinal.add(btnRefresh);
						btnRefresh.setEnabled(false);
						btnRefresh.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								FncGlobal.startProgress("Displaying list...");
								rtdDisplay(modelRTD, rowRTD);
								FncGlobal.stopProgress();								
							}
						});
					}
					{
						btnExport = new JButton("Export");
						panFinal.add(btnExport);
						btnExport.setEnabled(false);
						btnExport.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								FncGlobal.startProgress("Creating file...");
								
								try {
									scribeRTD();
								} catch (NullPointerException np1) {
									System.out.println("");
									System.out.println("Scribing");
								}
								
								FncGlobal.stopProgress();
								JOptionPane.showMessageDialog(null, "Export successful.");
							}
						});
					}
					{
						btnPreview = new JButton("Preview");
						panFinal.add(btnPreview);
						btnPreview.setEnabled(false);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Boolean blnPrint = false;
								for (int x = 0; x < modelRTD.getRowCount(); x++) {
									if ((Boolean) modelRTD.getValueAt(x, 1)) {
										blnPrint = true;
									}
								}
								
								if (blnPrint) {
									Print();	
								} else {
									JOptionPane.showMessageDialog(null, "No row was selected.");
								}
							}
						});
					}
					{
						JXPanel pnlProg = new JXPanel(new BorderLayout(5, 5));
						panFinal.add(pnlProg, BorderLayout.CENTER);
						pnlProg.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							progressBar = new JProgressBar();
							pnlProg.add(progressBar, BorderLayout.CENTER);
							progressBar.setValue(0);	
						}
					}
					ButtonLock(1);
				}
			}
			autoDir();
			CheckDir();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Open")) {
			
		}
		else if (e.getActionCommand().equals("Export")) {

		}
		else if (e.getActionCommand().equals("Generate")) {

		}
		else if (e.getActionCommand().equals("Preview")) {
			
		}
		else {
			JOptionPane.showMessageDialog(null, "This part is not finished yet.");
		}
	}
	
	public void CreateTable(){
		pnlTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			pnlTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setBorder(lineGray);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelRTD = new modelPisoDebit();
				modelRTD.setEditable(false);
				tblRTD = new _JTableMain(modelRTD);
				
				rowRTD = tblRTD.getRowHeader();
				scrTab.setViewportView(tblRTD);
				
				tblRTD.getColumnModel().getColumn(0).setPreferredWidth(250);
				tblRTD.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblRTD.getColumnModel().getColumn(2).setPreferredWidth(120);
				tblRTD.getColumnModel().getColumn(3).setPreferredWidth(120);
				tblRTD.getColumnModel().getColumn(4).setPreferredWidth(100);
				tblRTD.getColumnModel().getColumn(5).setPreferredWidth(88);
				tblRTD.getColumnModel().getColumn(5).setPreferredWidth(88);
				
				rowRTD = tblRTD.getRowHeader();
				rowRTD.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowRTD);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblRTD.hideColumns("OR Date");
			}
		}
	}
	
	public static String sqlCo(){
		String SQL = "";
		
		SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			  "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		
		return SQL;
	}
	    
    public static void CheckDir() {
       	String strDir = "";
    	String strHostName = _RealTimeDebit.getComputerName();
		String SQL = "SELECT def_dir \n" +
					 "FROM rf_piso_details \n" +
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
			RealTimeDebitPiso.blnWithDir = false;
		}
		else {
			txtDir.setText(strDir);
		}
    }
    
	private static String GetNextPrime() {
		String rec_id = "";
		String SQL = "select trim(to_char(max(coalesce(rtd_id::int,0))+1)) from rf_piso_details" ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			rec_id = (String)db.getResult()[0][0];
			
			if(rec_id==null){
				rec_id = "1";
			}
		}
		else {
			rec_id = "1";
		}
		
		return rec_id;
	}
	
	private void rtdDisplay(DefaultTableModel modelMain, JList rowHeader){
		String strSQL = "";
		
		/* Update temporary amount	*/
		pgUpdate db_Zero = new pgUpdate();
		String SQL_Rev = "UPDATE rf_rtd_accounts SET ave_balance = 0.00";
		db_Zero.executeUpdate(SQL_Rev, false);
		db_Zero.commit();
		
		try {
			strSQL = "SELECT *, 'DP' as Particular FROM view_rtd_piso ('"+txtCoID.getText()+"', '"+strBranch+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"')";
			
			try {
				FncTables.clearTable(modelMain);
			}
			catch (IndexOutOfBoundsException e) {
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
				ButtonLock(2);
			}
			else {
				ButtonLock(1);
				JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			};
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some details may not have been supplied. Check if the date is set.");
		}
		
		System.out.println("");
		System.out.println("RTD List");
		System.out.println(strSQL);
	}
	
	public void Print() {
		String strID = "";
		String strUnit = "";
		
		if (tblRTD.getRowCount()>0) {
			String strFilter = "";
			
			for (int x = 0; x < tblRTD.getRowCount(); x++) {
				if((Boolean)tblRTD.getValueAt(x, 1).equals(true)){
					System.out.println("Row 			-	" + x);
					strFilter = strFilter + "'" + (String)tblRTD.getValueAt(x, 0).toString() + "', ";
				}					
			}
			
			pgUpdate dbZero = new pgUpdate();
			String SQL_Zero = "UPDATE rf_rtd_accounts SET tobeprinted = '0'::bit";
			dbZero.executeUpdate(SQL_Zero, false);
			dbZero.commit();
			
			strFilter = strFilter.substring(0, strFilter.length() - 2);
			
			System.out.println("");
			System.out.println("strFilter		-	" + strFilter);
			
			pgUpdate db = new pgUpdate();
			String SQL_Out = "UPDATE rf_rtd_accounts SET tobeprinted = '1'::bit WHERE entity_id IN\n" +
					"(SELECT B.entity_id FROM rf_rtd_enrolled A INNER JOIN rf_entity B ON A.c_entity_id = B.entity_id WHERE concat_ws(' ', concat_ws(', ', B.Last_Name, B.First_Name), B.Middle_Name) IN ("+strFilter+"))";
			db.executeUpdate(SQL_Out, false);
			db.commit();
			
			System.out.println("");
			System.out.println(SQL_Out);
			
			/*	Set temporary amount in case it is changed	*/
			for (int i = 0; i < tblRTD.getRowCount(); i++) {
				strID = _RealTimeDebit.GetValue("SELECT entity_id FROM rf_entity WHERE TRIM(entity_name) = TRIM('"+tblRTD.getValueAt(i, 0)+"')");
				strUnit = _RealTimeDebit.GetValue("SELECT pbl_id FROM mf_unit_info WHERE TRIM(description) = ('"+tblRTD.getValueAt(i, 3)+"')");
				
				System.out.println("");
				System.out.println("strID: " + strID);
				System.out.println("strUnit: " + strUnit);
				
				pgUpdate db_Amt = new pgUpdate();
				String SQL = "UPDATE rf_rtd_accounts SET ave_balance = "+tblRTD.getValueAt(i, 6)+"::NUMERIC(19, 2)\n" +
							"WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnit+"'";
				db_Amt.executeUpdate(SQL, false);
				db_Amt.commit();
			}
			
			String strCompany = txtCo.getText().toUpperCase();
			String strDate = "";
			
			SimpleDateFormat sdfFrom = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
			
			String strFrom = (String)sdfFrom.format(dteFrom.getDate());
			String strTo = (String)sdfTo.format(dteTo.getDate());
			
			if(strFrom.equals(strTo)){
				strDate = "Report as of: " + strFrom;
			}
			else{
				strDate = "As Of: " + strFrom + " to " + strTo;
			};
			
			System.out.println("");
			System.out.println("Company: " + strCompany);
			System.out.println("AsOf: " + strDate);
			System.out.println("From: " + strFrom);
			System.out.println("To: " + strTo);
			System.out.println("CoID: " + txtCoID.getText());
			System.out.println("Branch: " + strBranch);
			System.out.println("User: " + GetName(UserInfo.EmployeeCode));
			
			try{
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", strCompany);
				mapParameters.put("02_AsOfDate", strDate);
				mapParameters.put("03_dFrom", strFrom);
				mapParameters.put("04_dTo", strTo);
				mapParameters.put("05_CoID", txtCoID.getText());
				mapParameters.put("06_Branch", strBranch);
				mapParameters.put("07_User", GetName(UserInfo.EmployeeCode));
				FncReport.generateReport("/Reports/rptRTD_Piso.jasper", "MBTC Accounts", "", mapParameters);
			} catch(NullPointerException e){
				JOptionPane.showMessageDialog(null, "Null pointer exception caught.\nContact the program author.");
			};
		}
		else {
			JOptionPane.showMessageDialog(null, "There is nothing to print.");
		}
		
	}
	
	public static String GetName(String emp_code){
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + 
		"FROM em_employee A\n" + 
		"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + 
		"WHERE a.emp_code = '"+emp_code+"'";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			entityid = (String) sqlExec.getResult()[0][0];
		}else{
			entityid = "";
		}

		return entityid;
	}
	
    public void scribeRTD() {
		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		String strParamDate = (String) sdfTo.format(dateObj);
		
		/*
		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		String strParamDate = (String) sdfTo.format(dteTo.getDate());
    	*/
		
    	String strFile = "";
    	String strWrite = "";
    	
    	File f = new File(txtDir.getText());
    	
    	if (!f.exists() && !f.isDirectory()) {
    		JOptionPane.showMessageDialog(this, "Folder does not exist.\nSet default directory.");
    	}
    	else {
    		/*
    		if (chkShaw.isSelected()) {
        		strFile = "Shaw_RTD" + txtCoID.getText() + strParamDate + ".txt";
        	}
        	else {
        		strFile = "Orti_RTD" + txtCoID.getText() + strParamDate + ".txt";
        	}
        	*/
    		strFile = "WackWack_RTD" + txtCoID.getText() + strParamDate + ".txt";
    		
        	File FileText = new File(txtDir.getText() + "/" + strFile);
    		
        	Boolean blnProceed = false;
        	
    		if(FileText.exists()) {
    			if(JOptionPane.showConfirmDialog(null, "A file with the same name exists in the specified directory.\nOverwrite?", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
    				blnProceed = true;
    			}
    		}
    		else {
    			blnProceed = true;
    		}
    		
    		if (blnProceed) {
    			FileText.delete();
    			AppendProgress(20);
    			strWrite = glyphRTD(strBranch);
            	
                try {
                    FileOutputStream is = new FileOutputStream(FileText);
                    OutputStreamWriter osw = new OutputStreamWriter(is);    
                    Writer w = new BufferedWriter(osw);
                    w.write(strWrite);
                    w.close();
                } 
                catch (IOException e) {
                    System.err.println("Problem writing to the file statsTest.txt");
                }
    		}
    	}
    }
    
    public void peruser(String strDir) {
    	File peruse = null;
    	System.out.println("Reading");
    	
    	try {
    		peruse = new File(strDir);
    	}
    	catch (NullPointerException e) {
    		JOptionPane.showMessageDialog(null, "No directory was selected.");
    	}
    	
    	Scanner scan = null;
    	
		try {
			scan = new Scanner(peruse);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
    	
		txtLog.setText("");
    	while(scan.hasNextLine()) {
    		txtLog.append(scan.nextLine() + newline);
    	}
    	
    	scan.close();
    	txtLog.moveCaretPosition(0);
    }
	
	private static String White(Integer i) {
		String strSpace = "";
		
		while (i > 0) {
			strSpace = strSpace + " ";
			i--;
		}
		
		return strSpace;
	}
	
	public static String GetID(Integer i, String strName, String strUnit){
		String ID = "";

		String SQL = "SELECT b.entity_id, LPAD(c.pbl_id::text, 5, '0')\n" +
				 "FROM rf_sold_unit a\n" +
				 "INNER JOIN rf_entity b ON a.entity_id = b.entity_id\n" +
				 "INNER JOIN mf_unit_info c ON a.projcode = c.proj_id\n" +
				 "WHERE b.entity_name = '"+strName+"' and c.description = '"+strUnit+"'";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			ID = (String) sqlExec.getResult()[0][i];
			System.out.println(ID);
		}else{
			ID = "";
		}

		return ID;
	}
	
	public static String Stringer(BigDecimal bd, Integer iLen) {
		String strAmt = "";
		String strAmtLeft = "";
		String strAmtRight = "";
		
		BigDecimal Amt = bd;
		
		strAmt = Amt.toString();
		
		DecimalFormat nf = new DecimalFormat("###,###.##");
		nf.setParseBigDecimal(true);
		
		try {
			/* Modified by Mann2x; Modified Date: November 21, 2016; Trial and error. Export simulations are being conducted;	*/
			bd = ((BigDecimal)nf.parse(strAmt)).setScale(2,BigDecimal.ROUND_HALF_UP);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/*
		strAmtLeft = String.valueOf(bd.doubleValue());
		strAmtRight = String.valueOf(bd.doubleValue());
		*/
		strAmtLeft = String.valueOf(bd);
		strAmtRight = String.valueOf(bd);
		
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("strAmtLeft: " + strAmtLeft);
		System.out.println("strAmtRight: " + strAmtRight);
		
		strAmtLeft = strAmtLeft.substring(0, strAmtLeft.indexOf("."));
		strAmtRight = strAmtRight.substring(strAmtLeft.length() + 1); 
		
		System.out.println("strAmtLeft: " + strAmtLeft);
		System.out.println("strAmtRight: " + strAmtRight);
		
		strAmt = strAmtLeft + strAmtRight;
		
		System.out.println("strAmt: " + strAmt);
		
		strAmt = _RealTimeDebit.Padme(strAmt, iLen);
		
		System.out.println("strAmt: " + strAmt);
		
		System.out.println("");
		System.out.println("Big Decimal: " + bd);
		System.out.println("Big Decimal: " + strAmtLeft);
		System.out.println("Amount Left: " + strAmtLeft);
		System.out.println("Amount Right: " + strAmtRight);
		System.out.println("Amount: " + strAmt);
		
		return strAmt;
	}
    
    public static String glyphRTD(String othBranch) {
    	String strWrite = "";
    	String strPlace = "";
    	
    	String nl = System.getProperty("line.separator");
    	
    	BigDecimal Total = null;
    	Integer intCount = 0;
    	
    	Date date = new Date();

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		SimpleDateFormat sdfNow = new SimpleDateFormat("MMddyy");
		
		String strParamDate = (String)sdfTo.format(dteTo.getDate());
		String strNow = (String)sdfNow.format(date);
    	
    	System.out.println("");
    	System.out.println("Param Date: " + strParamDate);
    	System.out.println("Current Date: " + strNow);
    	
    	/*
    	if (othBranch.equals("07")) {
    		strPlace = "03";
    	}
    	else {
    		strPlace = "01";
    	}
    	*/
    	
    	strPlace = "02";
    	
    	AppendProgress(30);
    	
    	String strCreditAcct = _RealTimeDebit.GetAccount(strPlace);
    	
    	Integer intUpload = 1;																/*		Get the number of times			*/
    																						/*		the client used metrobankdirect	*/
    	strWrite = strWrite + "0000000";													/*		Institution Code				*/
    	strWrite = strWrite + _RealTimeDebit.Padme(intUpload.toString(), 2);				/*		Nth Time						*/
    	strWrite = strWrite + White(15);													/*		Space(15)						*/
    	strWrite = strWrite + strParamDate;													/*		Billing Date					*/
    	strWrite = strWrite + "H";															/*		Fixed Value						*/
    	strWrite = strWrite + strNow;														/*		Upload Date						*/
    	strWrite = strWrite + White(49);													/*		Space(49)						*/
    	strWrite = strWrite + "001";														/*		Currency Code					*/
    	strWrite = strWrite + _RealTimeDebit.Padme(strCreditAcct, 13);						/*		Credit Account Number			*/
    	strWrite = strWrite + "2020";														/*		Constant						*/
    	strWrite = strWrite + nl;
    	
    	AppendProgress(40);
    	for(int x = 0;x < tblRTD.getRowCount();x++) {
    		if ((Boolean) tblRTD.getValueAt(x, 1)) {
    			String strName = (String)tblRTD.getValueAt(x, 0);
    			strName = strName.substring(0, 15);
    			    			
    			BigDecimal Amt;
    			
    			try {
    				Amt = (BigDecimal)tblRTD.getValueAt(x, 6);
    			} catch(ClassCastException ex) {
    				try {
    					Amt = new BigDecimal((Long)tblRTD.getValueAt(x, 6));
    				} catch (ClassCastException ey) {
    					Amt = new BigDecimal((Double)tblRTD.getValueAt(x, 6));
    				}
    			} 
				
    			try {
    				Total = Total.add(Amt);
    			} catch (NullPointerException e) {
    				Total = Amt;
    			}
    			
    			intCount = intCount + 1;
    			
    			String strID = GetID(0, (String)tblRTD.getValueAt(x, 0), (String)tblRTD.getValueAt(x, 3));
    			String strUnit = GetID(1, (String)tblRTD.getValueAt(x, 0), (String)tblRTD.getValueAt(x, 3));
    			String strAcct = (String) tblRTD.getValueAt(x, 4);
    			
    			strWrite = strWrite + "0000000";																	/*		Institution Code			*/
    			strWrite = strWrite + _RealTimeDebit.Padme(intUpload.toString(), 2);								/*		Batch No.					*/
    			strWrite = strWrite + _RealTimeDebit.Padme(GetSubscriber(strID), 15);								/*		Subscriber Number<?>		*/
    			strWrite = strWrite + strParamDate;																	/*		Billing Date				*/
    			strWrite = strWrite + "D";																			/*		Record Type					*/
    			strWrite = strWrite + "001";																		/*		Currency Code				*/
    			strWrite = strWrite + _RealTimeDebit.Padme(strAcct, 13);											/*		Account Number				*/
    			strWrite = strWrite + Stringer(Amt, 13);															/*		Debit Amount				*/
    			
    			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    			System.out.println("Amt: " + Amt);
    			
    			strWrite = strWrite + "+";																			/*		Fixed Value					*/
    			strWrite = strWrite + White(5);																		/*		Customer Trailing Space		*/
    			strWrite = strWrite + strName;																		/*		Customer Store				*/
    			strWrite = strWrite + White(10);																	/*		Customer Trailing Space		*/
    			strWrite = strWrite + GetID(0, (String)tblRTD.getValueAt(x, 0), (String)tblRTD.getValueAt(x, 3));	/*		Client ID					*/
    			strWrite = strWrite + _RealTimeDebit.Padme(strUnit, 5);												/*		Unit ID						*/
    			strWrite = strWrite + nl;
    		}
    	}
    	
    	AppendProgress(50);
    	strWrite = strWrite + "T";													/*		Fixed Value					*/
    	strWrite = strWrite + _RealTimeDebit.Padme(intCount.toString(), 6);			/*		Total No. Of Records		*/
    	strWrite = strWrite + Stringer(Total, 15);									/*		Total Amount				*/
    	
    	System.out.println("Total: " + Total.toString());
    	System.out.println("Count: " + intCount);
    	
    	return strWrite;
    }
    
    public void scribeAD() {
		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		String strParamDate = (String)sdfTo.format(dteTo.getDate());
    	
    	String strFile = "";
    	String strWrite = "";
    	
    	File f = new File(txtDir.getText());
    	
    	if (!f.exists() && !f.isDirectory()) {
    		JOptionPane.showMessageDialog(this, "Folder does not exist.\nSet default directory.");
    	}
    	else {
    		if (chkShaw.isSelected()) {
        		strFile = "AD_" + txtCoID.getText() + strParamDate + ".txt";
        	}
        	else {
        		strFile = "AD_" + txtCoID.getText() + strParamDate + ".txt";
        	}
    		
        	File FileText = new File(txtDir.getText() + "/" + strFile);
    		
        	Boolean blnProceed = false;
        	
    		if(FileText.exists()) {
    			if(JOptionPane.showConfirmDialog(null, "A file with the same name exists in the specified directory.\nOverwrite?", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
    				blnProceed = true;
    			}
    		}
    		else {
    			blnProceed = true;
    		}
    		
    		if (blnProceed) {
    			FileText.delete();
    			strWrite = glyphAD(strBranch);
            	
                try {
                    FileOutputStream is = new FileOutputStream(FileText);
                    OutputStreamWriter osw = new OutputStreamWriter(is);    
                    Writer w = new BufferedWriter(osw);
                    w.write(strWrite);
                    w.close();
                } 
                catch (IOException e) {
                    System.err.println("Problem writing to the file statsTest");
                }
    		}
    	}
    }
    
    public static String glyphAD(String othBranch) {
    	String strWrite = "";
    	String strPlace = "";
    	
    	BigDecimal Total = null;
    	Integer intCount = 0;
    	
    	Date date = new Date();

		SimpleDateFormat sdfTo = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
		
		String strParamDate = (String)sdfTo.format(dteTo.getDate());
		String strNow = (String)sdfNow.format(date);
    	
    	System.out.println("");
    	System.out.println("Param Date: " + strParamDate);
    	System.out.println("Current Date: " + strNow);

    	if (othBranch.equals("07")) {
    		strPlace = "03";
    	}
    	else {
    		strPlace = "01";
    	}
    	
    	AppendProgress(60);
    	strWrite = strWrite + "H";								/*		Constant<?>					*/
    	strWrite = strWrite + White(1);							/*		Space(1)					*/
    	strWrite = strWrite + "000023";							/*		Constant<?>					*/
    	strWrite = strWrite + White(4);							/*		Constant<?>					*/
    	strWrite = strWrite + strParamDate;						/*		Constant<?>					*/
    	strWrite = strWrite + White(60);						/*		Space(60)					*/
    	strWrite = strWrite + newline;
    	
    	AppendProgress(70);
    	for(int x = 0;x < tblRTD.getRowCount();x++) {
    		if ((Boolean)tblRTD.getValueAt(x, 1)) {
    			String strName = (String)tblRTD.getValueAt(x, 0);
    			strName = strName.substring(0, 15);
    			    			
    			BigDecimal Amt;
				Amt = (BigDecimal)tblRTD.getValueAt(x, 6);
    			
    			try {
    				Total = Total.add(Amt);
    			} catch (NullPointerException e) {
    				Total = Amt;
    			}
    			
    			intCount = intCount + 1;
    			
    			strWrite = strWrite + "OW";								/*		Constant<?>					*/
    	    	strWrite = strWrite + "000023";							/*		Constant<?>Idk<?>			*/
    	    	strWrite = strWrite + White(12);						/*		Space(12)					*/
    	    	strWrite = strWrite + "8680";							/*		Constant<?>Idk<?>			*/
    	    	strWrite = strWrite + _RealTimeDebit.Padme(strPlace, 3);				/*		Constant<?>Idk<?>			*/
    	    	strWrite = strWrite + "26";								/*		Constant<?>Idk<?>			*/
    	    	strWrite = strWrite + (String)tblRTD.getValueAt(x, 4);	/*		Client Account No			*/
    	    	strWrite = strWrite + Stringer(Amt, 15);				/*		Amortization Amount			*/
    	    	strWrite = strWrite + Stringer(Amt, 15);				/*		Constant<?>Idk<?>Lol<?>		*/
    	    	strWrite = strWrite + White(11);
    	    	strWrite = strWrite + newline;
    		}    		
    	}
    	
    	AppendProgress(80);
    	strWrite = strWrite + "T";										/*		Constant<?>					*/
    	strWrite = strWrite + White(1);									/*		Space(1)					*/
    	strWrite = strWrite + "000023";									/*		Same for every line			*/
    	strWrite = strWrite + White(4);									/*		Space(4)					*/
    	strWrite = strWrite + _RealTimeDebit.Padme(intCount.toString(), 8);			/*		Constant<?>					*/
    	strWrite = strWrite + White(19);								/*		Space(19)					*/
    	strWrite = strWrite + Stringer(Total, 15);						/*		Constant<?>					*/
    	strWrite = strWrite + White(26);								/*		Space(19)					*/
    	
    	System.out.println("Total: " + Total.toString());
    	System.out.println("Count: " + intCount);
    	
    	return strWrite;
    }
    
    
    public static void AppendProgress(Integer intProgress) {
    	if (intProgress.equals(100)) {
    		progressBar.setValue(0);
    	}
    	else {
    		progressBar.setValue(intProgress);
    	}
    }
    
	
	public static String GetRPLF() {
		String strRPLF = "";
		String SQL = "SELECT TRIM(to_char(max(COALESCE(rplf_no, '0')::INT) + 1, '000000000')) FROM rf_request_header" ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			strRPLF = (String)db.getResult()[0][0];
		}
		else {
			strRPLF = "000000000";
		}
		
		System.out.println("");
		System.out.println("RPLF Number: " + strRPLF);
		return strRPLF;
	}
    
    
	public void Post() {
    	String strRPLF = GetRPLF();
    	String strDesc = "MA REMITTANCE FOR THE PERIOD " + dteFrom.getDate().toString() + " to " + dteTo.getDate().toString();
    	String strSQL_loop = "";
    	String strSQL_header = "";
    	String strSQL_detail = "";
    	
    	strSQL_header = "INSERT INTO rf_request_header\n" +
    			"(co_id, busunit_id, rplf_no, rplf_date, date_needed, rplf_type_id, entity_id1, entity_type_id, status_id, created_by) \n" +
    			"VALUES\n" +
    			"('"+txtCoID.getText()+"', '"+txtCoID.getText()+"', '"+strRPLF+"', '"+dateFormat.format(Calendar.getInstance().getTime())+"', \n" +
    			"'"+dateFormat.format(Calendar.getInstance().getTime())+"', '01', '5153446583', '13', \n" + 
    			"'A', '"+UserInfo.EmployeeCode+"')\n";
    	
		System.out.println("");
		pgUpdate db_InsrtHead = new pgUpdate();
		db_InsrtHead.executeUpdate(strSQL_header, false);
		db_InsrtHead.commit();
    	
    	strSQL_loop = "SELECT SUM(A.Due) as Due, A.proj_id, A.sub_proj_id FROM (\n" +
    			"SELECT (SELECT SUM(x.c_amount) FROM view_card_dues(a.entity_id, a.proj_id, a.pbl_id, a.seq_no, Now()::TIMESTAMP, FALSE) x) as Due, a.proj_id, f.sub_proj_id,\n" +
    			"FROM rf_client_schedule a\n" +
    			"INNER JOIN rf_entity b ON a.entity_id = b.entity_id\n" +
    			"INNER JOIN rf_rtd_accounts c ON a.entity_id = c.entity_id and a.proj_id = c.projcode and a.pbl_id = c.pbl_id and a.seq_no = c.seq_no\n" +
    			"INNER JOIN mf_project d ON a.proj_id = d.proj_id\n" +
    			"INNER JOIN mf_unit_info e ON a.proj_id = e.proj_id and a.pbl_id = e.pbl_id\n" +
    			"INNER JOIN mf_sub_project f ON a.proj_id = f.proj_id and e.phase = f.phase AND f.status_id = 'A'\n" +//ADDED STATUS ID BY LESTER DCRF 2718
    			"WHERE d.co_id = '02' and a.scheddate::DATE >= '2016-9-1'::DATE\n" +
    			"AND a.scheddate::DATE <= '2016-9-30'::DATE AND c.accttype_id = '07') A\n" +
    			"WHERE A.Due > 0 GROUP BY A.proj_id, A.sub_proj_id";

		pgSelect db = new pgSelect();
		db.select(strSQL_loop);
				
		if(db.isNotNull()){
			for(Integer x = 0; x < db.getRowCount(); x++) {
				BigDecimal bd = (BigDecimal)db.getResult()[x][0];
				String project = (String)db.getResult()[x][1];
				String sub_project = (String)db.getResult()[x][2];
				
				System.out.println("");
				System.out.println("Amount: " + bd);
				System.out.println("Project: " + project);
				System.out.println("Sub Project: " + sub_project);
				
				strSQL_detail = "INSERT INTO rf_request_detail\n" +
						"(co_id, busunit_id, rplf_no, line_no, with_budget, part_desc, acct_id, amount, entity_id,\n" +
						"entity_type_id, project_id, sub_projectid, exp_amt, pv_amt, status_id, created_by, date_created) VALUES (\n" +
						"'"+txtCoID.getText()+"', \n" +
						"'"+txtCoID.getText()+"', \n" +
						"'"+strRPLF+"', \n" +
						"'"+x+"', \n" +
						"false, \n" +
						"'"+strDesc+"', \n" +
						"'03-02-00-000', \n" +
						bd+", \n" +
						"'5153446583', \n" +
						"'13', \n" +
						"'"+project+"', \n" +
						"'"+sub_project+"', \n" +
						bd+", \n" +
						bd+",\n" +
						"'A',\n" +
						"'"+UserInfo.EmployeeCode+"',\n" +
						"'"+dateFormat.format(Calendar.getInstance().getTime())+"')";
				
				System.out.println("");
				pgUpdate db_InsrtDet = new pgUpdate();
				db_InsrtDet.executeUpdate(strSQL_detail, false);
				db_InsrtDet.commit();
			}
		}
    }
    
	public static String GetSubscriber(String strID) {
    	String strNo = "";
    	
    	System.out.println("");
    	System.out.println("strID: " + strID);
		
    	String SQL = "SELECT TRIM(bank_acct_rec_id::CHAR(20)) FROM rf_rtd_accounts WHERE entity_id = '"+strID+"'";
	
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);
	
		if(sqlExec.isNotNull()){
			strNo = (String)sqlExec.getResult()[0][0];
		}
		
    	return strNo;
    }
	
    private void SaveDir(String strDir) {
    	if(!strDir.equals("")) {
        	String InsertSQL = "";
    		String SQL = "SELECT def_dir FROM rf_piso_details WHERE created_by = '"+UserInfo.EmployeeCode+"' AND def_dir = '"+strDir+"'";
    		pgSelect db = new pgSelect();
    		db.select(SQL);
    		
    		System.out.println("");
    		System.out.println(SQL);
    		
    		if (!db.isNotNull()) {
    			InsertSQL = "INSERT INTO rf_piso_details (rtd_id, def_dir, created_by, host_name, status_id)\n" +
    					"VALUES\n" +
    					"("+GetNextPrime()+", '"+strDir+"', '"+UserInfo.EmployeeCode+"', '"+_RealTimeDebit.getComputerName()+"', 'A')";
    		}
    		/*
    		else {
    			InsertSQL = "UPDATE rf_piso_details\n" +
    					   "SET def_dir = '"+strDir+"'\n" +
    					   "WHERE created_by = '"+UserInfo.EmployeeCode+"'";
    		}
    		*/
    		
    		System.out.println("");
    		System.out.println(InsertSQL);
    		
    		pgUpdate InsDb = new pgUpdate();
    		InsDb.executeUpdate(InsertSQL, false);
    		InsDb.commit();
    	}
    	txtDir.setText(strDir);
    }
    
    private void ButtonLock(Integer i) {
    	if (i==1) {
    		btnRefresh.setEnabled(true);
    		btnExport.setEnabled(false);
    		btnPreview.setEnabled(false);
    	} else if (i==2) {
    		btnRefresh.setEnabled(true);
    		btnExport.setEnabled(true);
    		btnPreview.setEnabled(true);
    	} else if (i==3) {
			btnRefresh.setEnabled(true);
			btnExport.setEnabled(false);
			btnPreview.setEnabled(true);
		}
    }
    
    public static void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;
		
		/*	Company Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtCoID.setValue("02");
			txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");
		} else {
			txtCoID.setValue(_RealTimeDebit.GetValue("SELECT co_id FROM mf_company LIMIT 1"));
			txtCo.setText(_RealTimeDebit.GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		}
		
		txtBranchID.setValue("254");
		txtBranch.setText("METROBANK DIRECT - WACK-WACK");
	}
	
	public static void autoDir() {
		String strDir = "";
		Boolean blnDir = false;
		String strComName = _RealTimeDebit.getComputerName();
		
		System.out.println("");
		System.out.println(strComName);

		String SQL = "SELECT def_dir FROM rf_piso_details";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				strDir = (String) db.getResult()[x][0];
				
				System.out.println((String) db.getResult()[x][0]);
				
				File f = new File((String) db.getResult()[x][0]);
				if(f.exists() && f.isDirectory()) {
					blnDir = true;
					
					String SQL_Rev = "UPDATE rf_piso_details \n" +
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
}
