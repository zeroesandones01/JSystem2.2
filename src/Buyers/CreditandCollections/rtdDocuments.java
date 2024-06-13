package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
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
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.export.oasis.CellStyle;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.BLUE;
import org.apache.poi.hssf.util.HSSFColor.BLUE_GREY;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdesktop.swingx.JXPanel;

import tablemodel.modelMBTC;
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

public class rtdDocuments extends _JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1616288309652680065L;
	static String title = "MBTC Enrollment";
	Dimension frameSize = new Dimension(690, 600);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	
	private JLabel lblCo;
	private JLabel lblProject;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblDir;
	
	private _JLookup txtCoID;
	private _JLookup txtProjectID;
	private _JLookup txtBatch;
	
	private JTextField txtCo;
	private JTextField txtProject;
	private JTextField txtDir;
	
	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;
	
	private JButton btnGen;
	private JButton btnPost;
	private JButton btnExp;
	private JButton btnDir;
	private JButton btnPrevDoc;
	private JButton btnPrevList;
	
	private JXPanel panTab;
	
	private JScrollPane scrTab;
	private modelMBTC modelRTD;
	public static _JTableMain tblRTD;
	public static JList rowRTD;
	
	public rtdDocuments() {
		super(title, true, true, false, true);
		InitGUI();
	}

	public rtdDocuments(String title) {
		super(title);
		InitGUI();
	}

	public rtdDocuments(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
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
			JXPanel panPageStart = new JXPanel(new GridLayout(2, 1, 5, 5));
			panMain.add(panPageStart, BorderLayout.PAGE_START);
			panPageStart.setPreferredSize(new Dimension(0, 195));
			{
				JXPanel panDiv = new JXPanel(new BorderLayout(5, 5));
				panPageStart.add(panDiv, BorderLayout.CENTER);
				panDiv.setPreferredSize(new Dimension(0, 60));
				{
					JXPanel panLabelBox = new JXPanel(new GridLayout(2, 1, 5, 5));
					panDiv.add(panLabelBox, BorderLayout.LINE_START);
					panLabelBox.setPreferredSize(new Dimension(530, 60));
					panLabelBox.setBorder(JTBorderFactory.createTitleBorder("Company And Project", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panCo = new JXPanel(new BorderLayout(5, 5));
						panLabelBox.add(panCo, BorderLayout.CENTER);
						{
							lblCo = new JLabel("Company");
							panCo.add(lblCo, BorderLayout.LINE_START);
							lblCo.setPreferredSize(new Dimension(80, 0));
							lblCo.setHorizontalAlignment(JTextField.LEFT);
						}
						{
							JXPanel panIDName = new JXPanel(new BorderLayout(5, 5));
							panCo.add(panIDName, BorderLayout.CENTER);
							{
								txtCoID = new _JLookup("");
								panIDName.add(txtCoID, BorderLayout.LINE_START);
								txtCoID.setHorizontalAlignment(JTextField.CENTER);
								txtCoID.setPreferredSize(new Dimension(60, 0));
								txtCoID.setLookupSQL(_RealTimeDebit.sqlCo());
								txtCoID.setReturnColumn(0);
								txtCoID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											txtCo.setText(data[1].toString());
											txtProjectID.setLookupSQL(_RealTimeDebit.sqlProject(txtCoID.getText()));
										}
									}
								});
							}
							{
								txtCo = new JTextField("---");
								panIDName.add(txtCo, BorderLayout.CENTER);
								txtCo.setHorizontalAlignment(JTextField.CENTER);
								txtCo.setEditable(false);
							}
						}
						JXPanel panProject = new JXPanel(new BorderLayout(5, 5));
						panLabelBox.add(panProject, BorderLayout.CENTER);
						{
							lblProject = new JLabel("Project");
							panProject.add(lblProject, BorderLayout.LINE_START);
							lblProject.setPreferredSize(new Dimension(80, 0));
							lblProject.setHorizontalAlignment(JTextField.LEFT);
						}
						{
							JXPanel panIDName = new JXPanel(new BorderLayout(5, 5));
							panProject.add(panIDName, BorderLayout.CENTER);
							{
								txtProjectID = new _JLookup("");
								panIDName.add(txtProjectID, BorderLayout.LINE_START);
								txtProjectID.setHorizontalAlignment(JTextField.CENTER);
								txtProjectID.setPreferredSize(new Dimension(60, 0));
								txtProjectID.setReturnColumn(0);
								txtProjectID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											txtProject.setText(data[2].toString());
										}
									}
								});
							}
							{
								txtProject = new JTextField("---");
								panIDName.add(txtProject, BorderLayout.CENTER);
								txtProject.setHorizontalAlignment(JTextField.CENTER);
								txtProject.setEditable(false);
							}
						}
					}
					Setdefaults();
					{
						btnGen = new JButton("Generate");
						panDiv.add(btnGen, BorderLayout.CENTER);
						btnGen.setActionCommand("Generate");
						btnGen.addActionListener(this);
						btnGen.setEnabled(true);
					}
				}
				JXPanel panLabelBox2 = new JXPanel(new BorderLayout(5, 5));
				panPageStart.add(panLabelBox2, BorderLayout.CENTER);
				panLabelBox2.setPreferredSize(new Dimension(475, 0));
				{
					JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5));
					panLabelBox2.add(panDiv1, BorderLayout.PAGE_START);
					panDiv1.setPreferredSize(new Dimension(475, 60));
					{
						JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5));
						panDiv1.add(panDate, BorderLayout.LINE_START);
						panDate.setPreferredSize(new Dimension(475, 60));
						panDate.setBorder(JTBorderFactory.createTitleBorder(/*"Officially Reserved Date"*/"Tagged w/ ATM Application", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							JXPanel panDateDiv1 = new JXPanel(new BorderLayout(5, 5));
							panDate.add(panDateDiv1, BorderLayout.CENTER);
							{
								lblDateFrom = new JLabel("Date From");
								panDateDiv1.add(lblDateFrom, BorderLayout.LINE_START);
								lblDateFrom.setHorizontalAlignment(JTextField.LEFT);
								lblDateFrom.setPreferredSize(new Dimension(80, 0));
							}
							{
								dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panDateDiv1.add(dteFrom, BorderLayout.CENTER);
								dteFrom.getCalendarButton().setVisible(true);
							}
						}
						{
							JXPanel panDateDiv2 = new JXPanel(new BorderLayout(5, 5));
							panDate.add(panDateDiv2, BorderLayout.CENTER);
							{
								lblDateTo = new JLabel("Date To");
								panDateDiv2.add(lblDateTo, BorderLayout.LINE_START);
								lblDateTo.setHorizontalAlignment(JTextField.CENTER);
								lblDateTo.setPreferredSize(new Dimension(80, 0));
							}
							{
								dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panDateDiv2.add(dteTo, BorderLayout.CENTER);
								dteTo.getCalendarButton().setVisible(true);
							}
						}
						JXPanel panBatch = new JXPanel(new GridLayout(1, 2, 5, 5));
						panDiv1.add(panBatch, BorderLayout.CENTER);
						panBatch.setBorder(JTBorderFactory.createTitleBorder("Batch No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtBatch = new _JLookup("Select batch...");
							panBatch.add(txtBatch, BorderLayout.CENTER);
							txtBatch.setReturnColumn(0);
							txtBatch.setLookupSQL(_RealTimeDebit.sqlBatch());
							txtBatch.setHorizontalAlignment(JTextField.CENTER);
							txtBatch.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										rtdDisplay(modelRTD, rowRTD, data[0].toString());
										ButtonLock(2);
									}									
								}
							});
						}
					}
					JXPanel panDir = new JXPanel(new BorderLayout(5, 5));
					panLabelBox2.add(panDir, BorderLayout.CENTER);
					panDir.setBorder(new EmptyBorder(2, 2, 2, 2));
					{
						lblDir = new JLabel("Directory");
						panDir.add(lblDir, BorderLayout.LINE_START);
						lblDir.setHorizontalAlignment(JTextField.LEFT);
						lblDir.setPreferredSize(new Dimension(85, 0));
					}
					{
						txtDir = new JTextField();
						panDir.add(txtDir, BorderLayout.CENTER);
						txtDir.setHorizontalAlignment(JTextField.LEFT);
						txtDir.setEditable(false);
					}
					{
						btnDir = new JButton("Set");
						panDir.add(btnDir, BorderLayout.LINE_END);
						btnDir.addActionListener(this);
						btnDir.setActionCommand("Set");
						btnDir.setPreferredSize(new Dimension(50, 0));
					}
					autoDir();
					CheckDir();

					try {
						java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
						System.out.println("Hostname of local machine: " + localMachine.getHostName());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			{
				CreateTable();
				panCenter.add(panTab, BorderLayout.CENTER);
			}
			JXPanel panPageEnd = new JXPanel(new GridLayout(1, 4, 5, 5));
			panMain.add(panPageEnd, BorderLayout.PAGE_END);
			panPageEnd.setPreferredSize(new Dimension(0, 30));
			{
				{
					btnPost = new JButton("Create Batch");
					panPageEnd.add(btnPost);
					btnPost.addActionListener(this);
					btnPost.setActionCommand("Post");
					btnPost.setEnabled(false);
				}
				{
					btnPrevList = new JButton("Preview Batch");
					panPageEnd.add(btnPrevList);
					btnPrevList.addActionListener(this);
					btnPrevList.setActionCommand("PrevList");
					btnPrevList.setEnabled(false);
				}
				{
					btnPrevDoc = new JButton("Preview Docs");
					panPageEnd.add(btnPrevDoc);
					btnPrevDoc.addActionListener(this);
					btnPrevDoc.setActionCommand("PrevDocs");
					btnPrevDoc.setEnabled(false);
				}
				{
					btnExp = new JButton("Export");
					panPageEnd.add(btnExp);
					btnExp.addActionListener(this);
					btnExp.setActionCommand("Export");
					btnExp.setEnabled(false);
				}
				ButtonLock(1);
			}
		}
	}
	
	public void CreateTable(){
		panTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		panTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			panTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setBorder(lineGray);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelRTD = new modelMBTC();
				modelRTD.setEditable(false);
				tblRTD = new _JTableMain(modelRTD);
				/*				
 				tblRTD.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						Boolean blnMarked = false;
						btnGen.setEnabled(true);
						
						if (!e.getValueIsAdjusting()) {
							for(int i = 0; i < tblRTD.getRowCount(); i++) {
								if ((Boolean)tblRTD.getValueAt(i, 1).equals(true)) {
									blnMarked = true;
								}
							}
						}
						
						if (blnMarked.equals(true)) {
							btnGen.setEnabled(false);
						}
					}
				});*/
				
				rowRTD = tblRTD.getRowHeader();
				scrTab.setViewportView(tblRTD);
				
				tblRTD.getColumnModel().getColumn(0).setPreferredWidth(200);
				tblRTD.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblRTD.getColumnModel().getColumn(2).setPreferredWidth(150);
				tblRTD.getColumnModel().getColumn(3).setPreferredWidth(120);
				tblRTD.getColumnModel().getColumn(8).setPreferredWidth(91);
				
				rowRTD = tblRTD.getRowHeader();
				rowRTD.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowRTD);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblRTD.hideColumns("Account #", "Due Date", "Due Amt", "Particular");
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
	
    public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Set")) {
			SaveDir(_RealTimeDebit.OpenDir("Folder"));
		} else if (e.getActionCommand().equals("Generate")) {
			FncGlobal.startProgress("Displaying list...");
			if (txtCoID.getText().equals("") || txtProjectID.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "The company or project fields are not set.");
			} else {
				txtBatch.setText("");
				txtBatch.setEditable(true);
				
				if (rtdDisplay(modelRTD, rowRTD, "").equals(true)) {
					ButtonLock(4);
				} else {
					ButtonLock(1);
				}	
			}
			FncGlobal.stopProgress();
		} else if (e.getActionCommand().equals("Post")) {
			Boolean blnMarked = false;
			
			txtBatch.setEditable(false);
			
			for (int i = 0; i < tblRTD.getRowCount(); i++) {
				if ((Boolean)tblRTD.getValueAt(i, 1).equals(true)) {
					blnMarked = true;
				}
			}
			
			if (!blnMarked) {
				if (JOptionPane.showConfirmDialog(getContentPane(), "Every row will be included in the batch since\nno row was selected. Proceed?", 
						"Creating Batch", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION) {
					txtBatch.setText(CreateBatch(1));
					ButtonLock(3);
				}
			} else {
				txtBatch.setText(CreateBatch(0));
				ButtonLock(3);
			}
		} else if (e.getActionCommand().equals("PrevList")) {
			String strBatch = "";
			
			if (txtBatch.getText().equals("Select batch...")) {
				strBatch = "";
			} else {
				strBatch = txtBatch.getText();
				
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate("delete from tmpmbtcenrollees", false);
				dbExec.commit();
				
				for (int x = 0; x < modelRTD.getRowCount(); x++) {
					if ((Boolean) modelRTD.getValueAt(x, 1)) {
						dbExec = new pgUpdate();
						dbExec.executeUpdate("insert into tmpmbtcenrollees (name) values ('"+modelRTD.getValueAt(x, 0).toString()+"')", false);
						dbExec.commit();
					}
				}

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("company", txtCo.getText());
				mapParameters.put("batch_no", strBatch);
				mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				//FncReport.generateReport("/Reports/rpt_MBTCEnrollees_v2.jasper", "Real-Time Debit List", "", mapParameters);
				FncReport.generateReport("/Reports/rpt_MBTCEnrollees.jasper", "Real-Time Debit List", "", mapParameters);
			}
		} else if (e.getActionCommand().equals("PrevDocs")) {
			Boolean blnMarked = false;
			
			for (int i = 0; i < tblRTD.getRowCount(); i++) {
				if ((Boolean)tblRTD.getValueAt(i, 1).equals(false)) {
					blnMarked = true;
				}
			}
			
			if (!blnMarked && tblRTD.getRowCount() > 1) {
				pgUpdate db_Rev = new pgUpdate();
				String SQL_Rev = "UPDATE rf_rtd_enrolled SET tobeprinted = '1'";
				db_Rev.executeUpdate(SQL_Rev, false);
				db_Rev.commit();
			} else {
				pgUpdate db_Rev = new pgUpdate();
				String SQL_Rev = "UPDATE rf_rtd_enrolled SET tobeprinted = '0'";
				db_Rev.executeUpdate(SQL_Rev, false);
				db_Rev.commit();
				
				String strFilter = "";
				
				for (int x = 0; x < tblRTD.getRowCount(); x++) {
					if((Boolean)tblRTD.getValueAt(x, 1).equals(true)){
						System.out.println("Row 			-	" + x);
						strFilter = strFilter + "'" + (String)tblRTD.getValueAt(x, 0).toString() + "', ";
					}					
				}
				
				strFilter = strFilter.substring(0, strFilter.length() - 2);
				System.out.println("");
				System.out.println("strFilter		-	" + strFilter);
				
				
				pgUpdate db = new pgUpdate();
				String SQL_Out = "UPDATE rf_rtd_enrolled SET tobeprinted = '1' WHERE c_entity_id IN\n" +
						"(SELECT B.entity_id FROM rf_rtd_enrolled A INNER JOIN rf_entity B ON A.c_entity_id = B.entity_id WHERE concat_ws(' ', concat_ws(', ', B.Last_Name, B.First_Name), B.Middle_Name) IN ("+strFilter+"))";
				
				db.executeUpdate(SQL_Out, false);
				db.commit();
			}
			
			PrimeData(); 
			CIF(txtBatch.getText());
			CMIF(txtBatch.getText());
			ATDA(txtBatch.getText());
			CSC(txtBatch.getText());
			AFFT(txtBatch.getText());
			ATDAv2(txtBatch.getText());
		} else if (e.getActionCommand().equals("Export")) {
			CreateXLS();
		}
    }
    
    private void SaveDir(String strDir) {
    	if(!strDir.equals("")) {
        	String InsertSQL = "";
    		String SQL = "SELECT def_dir FROM rf_rtd_details WHERE created_by = '"+UserInfo.EmployeeCode+"' AND def_dir = '"+strDir+"'";
    		pgSelect db = new pgSelect();
    		db.select(SQL);
    		
    		System.out.println("");
    		System.out.println(SQL);
    		
    		if (!db.isNotNull()) {
    			InsertSQL = "INSERT INTO rf_rtd_details (rtd_id, def_dir, created_by, host_name, status_id)\n" +
    					"VALUES\n" +
    					"("+GetNextPrime()+", '"+strDir+"', '"+UserInfo.EmployeeCode+"', '"+_RealTimeDebit.getComputerName()+"', 'A')";
    		}
    		/*
    		else {
    			InsertSQL = "UPDATE rf_rtd_details\n" +
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
    
	private static String GetNextPrime() {
		String rec_id = "";
		String SQL = "select trim(to_char(max(coalesce(rtd_id::int,0))+1)) from rf_rtd_details" ;

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
	
	private Boolean rtdDisplay(DefaultTableModel modelMain, JList rowHeader, String strBatch) {
		Boolean blnGen = false;
		String strSQL = "";
		
		FncGlobal.startProgress("Displaying list...");
		
		try {
			if (strBatch.equals("")) {
				strSQL = "SELECT A.c_last_name || ', ' || A.c_first_name || ' ' || A.c_middle_name as name, /*(CASE WHEN '"+strBatch+"' <> '' THEN true ELSE false END)*/true as tag, A.c_project as project, A.c_unit as unit, '', null, 0, '', \n"+
						 "(SELECT tran_date::DATE FROM rf_buyer_status X WHERE X.entity_id = B.entity_id and X.proj_id = C.proj_id and X.pbl_id = D.pbl_id and X.seq_no::INT = E.seq_no::INT and X.byrstatus_id = '01' and X.status_id = 'A' LIMIT 1) as OR_Date\n" +
						 "FROM view_rtd_enrollment('"+txtCoID.getText()+"', '"+txtProjectID.getText()+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"') A\n" +
						 "INNER JOIN rf_entity B ON A.c_last_name = B.last_name AND A.c_first_name = B.first_name AND A.c_middle_name = B.middle_name\n" +
						 "INNER JOIN mf_project C ON A.c_project = C.proj_name\n" +
						 "INNER JOIN mf_unit_info D ON C.proj_id = D.proj_id AND TRIM(A.c_unit) = TRIM(D.description)\n" +
						 "INNER JOIN rf_sold_unit E ON B.entity_id = E.entity_id AND C.proj_id = E.projcode AND D.pbl_id = E.pbl_id AND E.status_id = 'A'\n" +
						 "LEFT JOIN rf_rtd_enrolled F ON B.entity_id = F.c_entity_id AND C.proj_id = F.c_proj_id AND D.pbl_id = F.c_pbl_id AND E.seq_no::INT = F.c_seq_no::INT\n" +
						 "LEFT JOIN (SELECT * FROM rf_buyer_documents X WHERE X.doc_id = '215' AND X.status_id = 'A') G ON B.entity_id = G.entity_id AND C.proj_id = G.projcode AND D.pbl_id = G.pbl_id AND E.seq_no::INT = G.seq_no::INT\n" +
						 "WHERE COALESCE(F.batch_no, '') = '"+strBatch+"' AND COALESCE(G.doc_id, '') <> ''\n" +
						 "ORDER BY A.c_last_name, A.c_first_name, A.c_middle_name";
			} else {
				strSQL = "SELECT A.c_last_name || ', ' || A.c_first_name || ' ' || A.c_middle_name as name, /*(CASE WHEN '"+strBatch+"' <> '' THEN true ELSE false END)*/true as tag, A.c_project as project, A.c_unit as unit, '', null, 0, '', \n"+
						 "(SELECT tran_date::DATE FROM rf_buyer_status X WHERE X.entity_id = B.entity_id and X.proj_id = C.proj_id and X.pbl_id = D.pbl_id and X.seq_no::INT = E.seq_no::INT and X.byrstatus_id = '01' and X.status_id = 'A' LIMIT 1) as OR_Date\n" +
						 "FROM view_rtd_enrollment('"+txtCoID.getText()+"', '"+txtProjectID.getText()+"') A\n" +
						 "INNER JOIN rf_entity B ON A.c_last_name = B.last_name AND A.c_first_name = B.first_name AND A.c_middle_name = B.middle_name\n" +
						 "INNER JOIN mf_project C ON A.c_project = C.proj_name\n" +
						 "INNER JOIN mf_unit_info D ON C.proj_id = D.proj_id AND TRIM(A.c_unit) = TRIM(D.description)\n" +
						 "INNER JOIN rf_sold_unit E ON B.entity_id = E.entity_id AND C.proj_id = E.projcode AND D.pbl_id = E.pbl_id AND E.status_id = 'A'\n" +
						 "LEFT JOIN rf_rtd_enrolled F ON B.entity_id = F.c_entity_id AND C.proj_id = F.c_proj_id AND D.pbl_id = F.c_pbl_id AND E.seq_no::INT = F.c_seq_no::INT\n" +
						 "WHERE COALESCE(F.batch_no, '') = '"+strBatch+"'\n" +
						 "ORDER BY A.c_last_name, A.c_first_name, A.c_middle_name";				
			}
			
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
				
				blnGen = true;
			}
			else {
				JOptionPane.showMessageDialog(null, "No records were returned for batch creation.");
			};
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied. Check the dates.");
		}
		
		System.out.println("");
		System.out.println("RTD List");
		System.out.println(strSQL);
		
		FncGlobal.stopProgress();
		
		return blnGen;
	}
	
	private void ButtonLock(Integer i) {
		Boolean blnAccess = false;
		
		if (UserInfo.Department.equals("54") || UserInfo.Department.equals("98")) {
			blnAccess = true;
		}
		
		if (i==1) {					/*		Initial State			*/
			btnPost.setEnabled(false);
			btnPrevDoc.setEnabled(false);
			btnPrevList.setEnabled(false);
			btnExp.setEnabled(false);
		} else if (i==2) {			/*		Batch is retrieved		*/
			btnPost.setEnabled(false);
			btnPrevDoc.setEnabled(true);
			btnPrevList.setEnabled(true);
			btnExp.setEnabled(true);			
		} else if (i==3) {			/* 		Batch is created		*/
			btnPost.setEnabled(false);
			btnPrevDoc.setEnabled(true);
			btnPrevList.setEnabled(true);
			btnExp.setEnabled(true);			
		} else if (i==4) {			/* 		Generate is pressed		*/
			btnPost.setEnabled(true);
			btnPrevDoc.setEnabled(false);
			btnPrevList.setEnabled(false);
			btnExp.setEnabled(false);			
		}
		
		if (!blnAccess) {
			btnPrevDoc.setEnabled(false);
			btnExp.setEnabled(false);
		}
	}
	
	private String CreateBatch(Integer intAll) {
		String strBatch = "";
		String strID = "";
		String strProjectID = "";
		String strUnitID = "";
		String strSequence = "";
		
		FncGlobal.startProgress("Creating batch...");
		strBatch = _RealTimeDebit.GetNextBatch();
		
		for (int i = 0; i < tblRTD.getRowCount(); i++) {
			if ((Boolean)tblRTD.getValueAt(i, 1) || intAll==1) {
				String strName = (String)tblRTD.getValueAt(i, 0);
				String strProject = (String)tblRTD.getValueAt(i, 2);
				String strUnit = (String)tblRTD.getValueAt(i, 3);
				
				strID = _RealTimeDebit.GetValue("SELECT entity_id FROM rf_entity WHERE TRIM(last_name) || ', ' || TRIM(first_name) || ' ' || TRIM(middle_name) = '"+strName+"' AND status_id = 'A'");
				strProjectID = _RealTimeDebit.GetValue("SELECT proj_id FROM mf_project WHERE proj_name = '"+strProject+"' AND status_id = 'A'");
				strUnitID = _RealTimeDebit.GetValue("SELECT pbl_id FROM mf_unit_info WHERE proj_id = '"+strProjectID+"' AND TRIM(DESCRIPTION) = '"+strUnit+"'");
				strSequence = _RealTimeDebit.GetValue("SELECT TRIM(seq_no::VARCHAR(2)) FROM rf_sold_unit WHERE entity_id = '"+strID+"' and projcode = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'");
				
				System.out.println("");
				System.out.println("ID: " + strID);
				System.out.println("Name: " + strName);
				System.out.println("Project: " + strProjectID + " - " + strProject);
				System.out.println("Unit: " + strUnitID + " - " + strUnit);
				System.out.println("Sequence: " + strSequence);
				
				String SQL = "INSERT INTO rf_rtd_enrolled (c_entity_id, c_proj_id, c_pbl_id, c_seq_no, status_id, batch_no, date_created, tobeprinted)\n" +
							 "VALUES ('"+strID+"', '"+strProjectID+"', '"+strUnitID+"', '"+strSequence+"', 'A', '"+strBatch+"', Now()::TIMESTAMP, 0::BIT);";
				
				pgUpdate db = new pgUpdate();
				db.executeUpdate(SQL, false);
				db.commit();	
			}
		}
		
		FncGlobal.stopProgress();
		rtdDisplay(modelRTD, rowRTD, strBatch);
		JOptionPane.showMessageDialog(getContentPane(), "Batch created!");
		return strBatch;
	}
	
	private void CIF(String strBatch) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBatch);
		//mapParameters.put("image", this.getClass().getClassLoader().getResourceAsStream("Images/no415.jpg"));
		
		/*	Modified by: Mann2x; Date Modified: October 17, 2018; DCRF# 794;	*/
		//FncReport.generateReport("/Reports/rpt_mbtcCustomerInfoIndividual.jasper", "Customer Information Form - Individual", "", mapParameters);
		FncReport.generateReport("/Reports/rpt_mbtcCustomerInfoIndividual_page1.jasper", "Customer Information Form - Individual Page 1", "", mapParameters);
		FncReport.generateReport("/Reports/rpt_mbtcCustomerInfoIndividual_page2.jasper", "Customer Information Form - Individual Page 2", "", mapParameters);
	}
	
	private void CMIF(String strBatch) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBatch);
		//mapParameters.put("image", this.getClass().getClassLoader().getResourceAsStream("Images/no417.jpg"));
		FncReport.generateReport("/Reports/rpt_CustomerMaintenanceAndInstruction.jasper", "Customer Maintenance And Instruction Form", "", mapParameters);
	}
	
	private void ATDA(String strBatch) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBatch);
		//mapParameters.put("image", this.getClass().getClassLoader().getResourceAsStream("Images/no418.jpg"));
		FncReport.generateReport("/Reports/rpt_DebitAccountAuthorization.jasper", "Authorization To Debit Account", "", mapParameters);
	}
	
	private void CSC(String strBatch) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBatch);
		//mapParameters.put("image", this.getClass().getClassLoader().getResourceAsStream("Images/no414.jpg"));
		FncReport.generateReport("/Reports/rpt_mbtcCustomerSignCard.jasper", "Customer Signature Card", "", mapParameters);
	}
	
	private void AFFT(String strBatch) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBatch);
		//mapParameters.put("image", this.getClass().getClassLoader().getResourceAsStream("Images/no416.jpg"));
		FncReport.generateReport("/Reports/rpt_FundTransferApplication.jasper", "Application For Fund Transfer", "", mapParameters);
	}
	
	private void ATDAv2(String strBatch) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBatch);
		//mapParameters.put("image", this.getClass().getClassLoader().getResourceAsStream("Images/no418.jpg"));
		FncReport.generateReport("/Reports/rpt_DebitAccountAuthorization_v2.jasper", "Authorization To Debit Account New Format", "", mapParameters);
	}
	
	private void CreateXLS() {
	    Workbook wb = new HSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("new sheet");
	    HSSFFont hSSFFont1 = (HSSFFont) wb.createFont();
	    hSSFFont1.setFontName("Calibri");
	    hSSFFont1.setBoldweight(hSSFFont1.BOLDWEIGHT_BOLD);
	    HSSFFont hSSFFont2 = (HSSFFont) wb.createFont();
	    hSSFFont2.setFontName("Calibri");
	    
	    HSSFCellStyle styleVertCenter = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	    styleVertCenter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleVertCenter_1 = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenter_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenter_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenter_1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    styleVertCenter_1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleHoriCenter = (HSSFCellStyle) wb.createCellStyle();
	    styleHoriCenter.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    
	    HSSFCellStyle styleVertTopBordered = (HSSFCellStyle) wb.createCellStyle();
	    styleVertTopBordered.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
	    styleVertTopBordered.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertTopBordered.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setFont(hSSFFont1);
	    //styleVertTopBordered.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleVertCenterBordered = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenterBordered.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenterBordered.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenterBordered.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setFont(hSSFFont2);
	    //styleVertCenterBordered.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    String strName = "";
	    String strNameCo = "";
	    String strNameDate = "";
	    String strNameSequence = "";
	    
	    if (txtCoID.getText().equals("")) {
	    	JOptionPane.showMessageDialog(getContentPane(), "The company field is not set\nand thus will not be included in the filename.");
	    } else {
	    	strNameCo =  _RealTimeDebit.GetValue("SELECT company_alias FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'");
	    }
	    
		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strNameDate = (String)sdfTo.format(dateObj);
	    
		Integer intSeq = 1;
		strNameSequence = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(txtDir.getText() + "/" + strNameCo + strNameDate + strNameSequence + ".xls");
		
		System.out.println("");
		System.out.println("File Name: " + strNameCo + strNameDate + strNameSequence + ".xls");
		
		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strNameSequence);
			
			intSeq++;
			strNameSequence = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(txtDir.getText() + "/" + strNameCo + strNameDate + strNameSequence + ".xls");
			System.out.println("New Name: " + strNameCo + strNameDate + strNameSequence + ".xls");
		}
		
		strName = strNameCo + strNameDate + strNameSequence + ".xls";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(_RealTimeDebit.Details(txtBatch.getText()));
		
		System.out.println("");
		System.out.println(_RealTimeDebit.Details(txtBatch.getText()));
		
	    Row row = sheet.createRow((short)0);
	    row.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
	    
	    /*	This is the Original Style	*/
	    row.createCell(0).setCellValue("Last Name");
	    row.createCell(1).setCellValue("First Name");
	    row.createCell(2).setCellValue("Middle Name");
	    row.createCell(3).setCellValue("Mother's Maiden Name");
	    row.createCell(4).setCellValue("Marital Status");
	    row.createCell(5).setCellValue("Gender");
	    row.createCell(6).setCellValue("Permanent Address");
	    row.createCell(7).setCellValue("City");
	    row.createCell(8).setCellValue("ZIP Code");
	    row.createCell(9).setCellValue("Residence No.");
	    row.createCell(10).setCellValue("Mobile No.");
	    row.createCell(11).setCellValue("Office No.");
	    row.createCell(12).setCellValue("Fax No.");
	    row.createCell(13).setCellValue("Email Address");
	    row.createCell(14).setCellValue("Birth Place");
	    row.createCell(15).setCellValue("Birth Date");
	    row.createCell(16).setCellValue("Nationality");
	    row.createCell(17).setCellValue("Job Title/Occupation/Profession");
	    row.createCell(18).setCellValue("Employer's Name");
	    row.createCell(19).setCellValue("TIN");
	    row.createCell(20).setCellValue("SSS/GSIS No.");
	    row.createCell(21).setCellValue("Driver's License");
	    row.createCell(22).setCellValue("Passport No.");
	    row.createCell(23).setCellValue("Company ID");
	    row.createCell(24).setCellValue("Branch Code");
	    
	    /*	This is the modified style
	    row.createCell(0).setCellValue("Branch Name");
	    row.createCell(1).setCellValue("Branch RC Code");
	    row.createCell(2).setCellValue("Account Number");
	    row.createCell(3).setCellValue("Company Name");
	    row.createCell(4).setCellValue("Last Name");
	    row.createCell(5).setCellValue("First Name");
	    row.createCell(6).setCellValue("Middle Name");
	    row.createCell(7).setCellValue("Present Address");
	    row.createCell(8).setCellValue("Permanent Address");
	    row.createCell(9).setCellValue("Contact Details (Mobile No. or Home/Office No.)");
	    row.createCell(10).setCellValue("Birth Place");
	    row.createCell(11).setCellValue("Birth Date");
	    row.createCell(12).setCellValue("Nationality");
	    row.createCell(13).setCellValue("TIN/SSS/GSIS No.");
	    row.createCell(14).setCellValue("Source of Funds");
	    row.createCell(15).setCellValue("Nature of Employment/Business");
	    row.createCell(16).setCellValue("Job Title/Occupation");
	    
	    row.createCell(17).setCellValue("Mother's Maiden Name");
	    row.createCell(18).setCellValue("Marital Status");
	    row.createCell(19).setCellValue("Gender");
	    row.createCell(20).setCellValue("City");
	    row.createCell(21).setCellValue("ZIP Code");
	    row.createCell(22).setCellValue("Residence No.");
	    row.createCell(23).setCellValue("Mobile No.");
	    row.createCell(24).setCellValue("Office No.");
	    row.createCell(25).setCellValue("Fax No.");
	    row.createCell(26).setCellValue("Email Address");
	    row.createCell(27).setCellValue("Employer's Name");
	    row.createCell(28).setCellValue("TIN");
	    row.createCell(29).setCellValue("SSS/GSIS No.");
	    row.createCell(30).setCellValue("Driver's License");
	    row.createCell(31).setCellValue("Passport No.");
	    row.createCell(32).setCellValue("Company ID");
	    row.createCell(33).setCellValue("Branch Code");
	    
	    row.getCell(0).setCellStyle(styleVertTopBordered);
	    row.getCell(1).setCellStyle(styleVertTopBordered);
	    row.getCell(2).setCellStyle(styleVertTopBordered);
	    row.getCell(3).setCellStyle(styleVertTopBordered);
	    row.getCell(4).setCellStyle(styleVertTopBordered);
	    row.getCell(5).setCellStyle(styleVertTopBordered);
	    row.getCell(6).setCellStyle(styleVertTopBordered);
	    row.getCell(7).setCellStyle(styleVertTopBordered);
	    row.getCell(8).setCellStyle(styleVertTopBordered);
	    row.getCell(9).setCellStyle(styleVertTopBordered);
	    row.getCell(10).setCellStyle(styleVertTopBordered);
	    row.getCell(11).setCellStyle(styleVertTopBordered);
	    row.getCell(12).setCellStyle(styleVertTopBordered);
	    row.getCell(13).setCellStyle(styleVertTopBordered);
	    row.getCell(14).setCellStyle(styleVertTopBordered);
	    row.getCell(15).setCellStyle(styleVertTopBordered);
	    row.getCell(16).setCellStyle(styleVertTopBordered);
	    row.getCell(17).setCellStyle(styleVertTopBordered);
	    row.getCell(18).setCellStyle(styleVertTopBordered);
	    row.getCell(19).setCellStyle(styleVertTopBordered);
	    row.getCell(20).setCellStyle(styleVertTopBordered);
	    row.getCell(21).setCellStyle(styleVertTopBordered);
	    row.getCell(22).setCellStyle(styleVertTopBordered);
	    row.getCell(23).setCellStyle(styleVertTopBordered);
	    row.getCell(24).setCellStyle(styleVertTopBordered);
	    row.getCell(25).setCellStyle(styleVertTopBordered);
	    row.getCell(26).setCellStyle(styleVertTopBordered);
	    row.getCell(27).setCellStyle(styleVertTopBordered);
	    row.getCell(28).setCellStyle(styleVertTopBordered);
	    row.getCell(29).setCellStyle(styleVertTopBordered);
	    row.getCell(30).setCellStyle(styleVertTopBordered);
	    row.getCell(31).setCellStyle(styleVertTopBordered);
	    row.getCell(32).setCellStyle(styleVertTopBordered);
	    row.getCell(33).setCellStyle(styleVertTopBordered);	    
	    */
	    
	    row.getCell(0).setCellStyle(styleVertCenter);
	    row.getCell(1).setCellStyle(styleVertCenter);
	    row.getCell(2).setCellStyle(styleVertCenter);
	    row.getCell(3).setCellStyle(styleVertCenter);
	    row.getCell(4).setCellStyle(styleVertCenter);
	    row.getCell(5).setCellStyle(styleVertCenter);
	    row.getCell(6).setCellStyle(styleVertCenter);
	    row.getCell(7).setCellStyle(styleVertCenter);
	    row.getCell(8).setCellStyle(styleVertCenter);
	    row.getCell(9).setCellStyle(styleVertCenter);
	    row.getCell(10).setCellStyle(styleVertCenter);
	    row.getCell(11).setCellStyle(styleVertCenter);
	    row.getCell(12).setCellStyle(styleVertCenter);
	    row.getCell(13).setCellStyle(styleVertCenter);
	    row.getCell(14).setCellStyle(styleVertCenter);
	    row.getCell(15).setCellStyle(styleVertCenter);
	    row.getCell(16).setCellStyle(styleVertCenter);
	    row.getCell(17).setCellStyle(styleVertCenter);
	    row.getCell(18).setCellStyle(styleVertCenter);
	    row.getCell(19).setCellStyle(styleVertCenter);
	    row.getCell(20).setCellStyle(styleVertCenter);
	    row.getCell(21).setCellStyle(styleVertCenter);
	    row.getCell(22).setCellStyle(styleVertCenter);
	    row.getCell(23).setCellStyle(styleVertCenter);
	    row.getCell(24).setCellStyle(styleVertCenter_1);
	    
		if (sqlExec.isNotNull()) {
			Integer intCount = sqlExec.getRowCount();
			System.out.println("");
			System.out.println("Total number of rows: " + intCount);
			
		    for (Integer intRow = 0; intRow < intCount; intRow++) {
		    	Integer shortInt = intRow + 1;
		    	Short shortRow = Short.parseShort(shortInt.toString());
			    Row item_row = sheet.createRow((short)shortRow);
			    
			    for (Integer intCol = 0; intCol < 25; intCol ++) {
			    //for (Integer intCol = 0; intCol < 34; intCol ++) {
			    	System.out.println("");
			    	System.out.println("Row: " + intRow);
			    	System.out.println("Column: " + intCol);
			    	
			    	item_row.createCell(intCol).setCellValue((String)sqlExec.getResult()[intRow][intCol]);
			    	//item_row.getCell(intCol).setCellStyle(styleVertCenterBordered);
			    	
			    	System.out.println("Cell Value[" + intRow + "][" + intCol + "] - " + (String)sqlExec.getResult()[intRow][intCol]);
			    }
		    }
		    
		    for (Integer intCol = 0; intCol <= row.getLastCellNum(); intCol++) {
		    	wb.getSheetAt(0).autoSizeColumn(intCol);
		    }
		} else {
			System.out.print("");
			System.out.print("Procedure halted.");
		}
	    
		/* Test Line	*/
		/*	This is the modified style
		sheet.setColumnHidden(17, true);
		sheet.setColumnHidden(18, true);
		sheet.setColumnHidden(19, true);
		
		sheet.setColumnHidden(20, true);
		sheet.setColumnHidden(21, true);
		sheet.setColumnHidden(22, true);
		sheet.setColumnHidden(23, true);
		sheet.setColumnHidden(24, true);
		sheet.setColumnHidden(25, true);
		sheet.setColumnHidden(26, true);
		
		sheet.setColumnHidden(27, true);
		sheet.setColumnHidden(28, true);
		sheet.setColumnHidden(29, true);
		sheet.setColumnHidden(30, true);
		sheet.setColumnHidden(31, true);
		sheet.setColumnHidden(32, true);
		sheet.setColumnHidden(33, true);
		*/
		
	    FileOutputStream fileOut = null;
	    try {
			fileOut = new FileOutputStream(txtDir.getText() + "/" + strName);
		} catch (FileNotFoundException e2) {
			System.out.println("");
			System.out.println("Error Line -- " + strName);
		}
		
	    try {
			wb.write(fileOut);
		} catch (IOException e1) {
			System.out.println("Error Line -- wb.write(fileOut)");
		}
	    
	    try {
			fileOut.close();
		} catch (IOException e1) {
			System.out.println("Error Line -- fileOut.close();");
		}
	    
	    JOptionPane.showMessageDialog(getContentPane(), "Export Successful");
	    
	    Desktop dt = Desktop.getDesktop();
	    f = new File(txtDir.getText() + "/" + strName);
	    
	    try {
			dt.open(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getContentPane(), "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void Setdefaults() {
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
		
		/*	Project Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtProjectID.setText("015");
			txtProject.setText("TERRAVERDE RESIDENCES");
		} else {
			txtProjectID.setValue(_RealTimeDebit.GetValue("SELECT proj_id FROM mf_project LIMIT 1"));
			txtProject.setText(_RealTimeDebit.GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProjectID.getText()+"'"));
		}
	}
	
	private void autoDir() {
		String strDir = "";
		Boolean blnDir = false;
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
					blnDir = true;
					
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
	
	private void PrimeData() {
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_rtd_printing where emp_code = '"+UserInfo.EmployeeCode+"'", true);
		dbExec.commit();
		
		dbExec = new pgUpdate();
		dbExec.executeUpdate("insert into tmp_rtd_printing \n" +
				"select *, '"+UserInfo.EmployeeCode+"' \n" + 
				"from view_rtd_enrollment('', '')", true);
		dbExec.commit();
	}
}