package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import com.csvreader.CsvWriter;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_checks_pdc_warehousing;
import tablemodel.model_checks_pdc_warehousing_export;

public class PDCWarehousing_Export extends JXPanel implements _GUI {

	private static final long serialVersionUID = 7850702470258262834L;
	private static Font font9 = FncLookAndFeel.systemFont_Plain.deriveFont(8f);
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
	private static SimpleDateFormat sdf_export = new SimpleDateFormat("MMddyyyy");
	
	static checkStatusMonitoring_pdcWarehousing pdcw; 
	
	private static JXPanel panMain;
	private static JXPanel panExport; 
	
	private static JButton btnPreview;
	private static JButton btnExport;
	private static JButton btnOpen; 
	
	private static JScrollPane scrExport;
	private static model_checks_pdc_warehousing_export modelExport;	
	public static _JTableMain tblExport;
	public static JList rowExport;
	
	private static JTextField txtDir;
	
	public PDCWarehousing_Export(checkStatusMonitoring_pdcWarehousing main_pdcw) {
		this.pdcw = main_pdcw; 
		initGUI(); 
	}

	public PDCWarehousing_Export(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		
	}

	public PDCWarehousing_Export(LayoutManager layout) {
		super(layout);
		
	}

	public PDCWarehousing_Export(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		
		panMain = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		{
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				panCenter.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					CreateTable();
					panCenter.add(panExport); 
				}
			}
			{
				JXPanel panEnd = new JXPanel(new GridLayout(1, 2, 15, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						JXPanel panDir = new JXPanel(new BorderLayout(5, 5)); 
						panEnd.add(panDir); 
						panDir.setBorder(new EmptyBorder(2, 0, 2, 0));
						{
							{
								btnOpen = new JButton("Directory"); 
								panDir.add(btnOpen, BorderLayout.LINE_START);
								btnOpen.setPreferredSize(new Dimension(75, 0));
								btnOpen.setFont(font11);
								btnOpen.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										SaveDir(_RealTimeDebit.OpenDir("Folder"));
									}
								});
							}
							{
								txtDir = new JTextField("C:/");
								panDir.add(txtDir, BorderLayout.CENTER);
								txtDir.setHorizontalAlignment(JTextField.LEADING);
								txtDir.setEditable(false);
							}
						}
					}
					{
						JXPanel panButtons = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panEnd.add(panButtons); 
						{
							{
								btnPreview = new JButton("Preview"); 
								panButtons.add(btnPreview, BorderLayout.LINE_START); 
								btnPreview.setPreferredSize(new Dimension(200, 0));
								btnPreview.setFont(font11);
								btnPreview.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										pgUpdate dbExec = new pgUpdate(); 
										
								    	Boolean blnProceed = false; 
								    	
										for (int x=0; x<modelExport.getRowCount(); x++) {
											if ((Boolean) modelExport.getValueAt(x, 0)) {
												blnProceed = true;
											}
										}
										
										if (blnProceed) {
											dbExec.executeUpdate("delete from tmp_pdc_warehousing where c_emp_code = '"+UserInfo.EmployeeCode+"'", false);
											
											Integer intRow = 1;
											
											for (int x=0; x<modelExport.getRowCount(); x++) {
												if ((Boolean) modelExport.getValueAt(x, 0)) {
													dbExec.executeUpdate("insert into tmp_pdc_warehousing values\n" + 
															"("+intRow+", '"+modelExport.getValueAt(x, 1).toString()+"', '"+modelExport.getValueAt(x, 2).toString()+"', \n" +
															"'"+modelExport.getValueAt(x, 3).toString()+"', '"+modelExport.getValueAt(x, 4).toString()+"', \n" +
															"'"+modelExport.getValueAt(x, 5).toString()+"', '"+modelExport.getValueAt(x, 6).toString()+"', \n" +
															"'"+modelExport.getValueAt(x, 7).toString()+"', '"+modelExport.getValueAt(x, 8).toString()+"', \n" +
															"'"+UserInfo.EmployeeCode+"')", false);
													intRow++; 
												}
											}
											
											dbExec.commit();
											
											Map<String, Object> mapParameters = new HashMap<String, Object>();
											mapParameters.put("emp_code", UserInfo.EmployeeCode);
											FncReport.generateReport("/Reports/rpt_pdcwarehousing_export.jasper", "PDC Warehousing Export", "", mapParameters);	
										} else {
											JOptionPane.showMessageDialog(null, "No row was selected.");
										}
									}
								});
							}
							{
								btnExport = new JButton("Export"); 
								panButtons.add(btnExport, BorderLayout.LINE_END); 
								btnExport.setPreferredSize(new Dimension(200, 0));
								btnExport.setFont(font11);
								btnExport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (JOptionPane.showConfirmDialog(null, "Proceed?", "Confirm Export", 
												JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
											Export(); 
										}
									}
								});
							}
						}
					}
				}
			}
		}
		
		autoDir();
		CheckDir();
		LoadList(); 
	}

	public void CreateTable() {
		panExport = new JXPanel(new GridLayout(1, 1, 0, 0));
		panExport.setOpaque(isOpaque());
		{
			scrExport = new JScrollPane();
			panExport.add(scrExport, BorderLayout.CENTER);
			scrExport.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelExport = new model_checks_pdc_warehousing_export();
				modelExport.setEditable(false);
				
				tblExport = new _JTableMain(modelExport);
				rowExport = tblExport.getRowHeader();
				scrExport.setViewportView(tblExport);
				
				tblExport.getColumnModel().getColumn(1).setPreferredWidth(100);
				tblExport.getColumnModel().getColumn(2).setPreferredWidth(100);
				tblExport.getColumnModel().getColumn(3).setPreferredWidth(100);
				tblExport.getColumnModel().getColumn(4).setPreferredWidth(100);
				tblExport.getColumnModel().getColumn(5).setPreferredWidth(100);
				tblExport.getColumnModel().getColumn(6).setPreferredWidth(100);
				tblExport.getColumnModel().getColumn(7).setPreferredWidth(225);
				tblExport.getColumnModel().getColumn(8).setPreferredWidth(150);
				
				tblExport.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
				tblExport.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(sdf));
				
				tblExport.hideColumns("BRSTN");
				
				rowExport = tblExport.getRowHeader();
				rowExport.setModel(new DefaultListModel());
				scrExport.setRowHeaderView(rowExport);
				scrExport.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
    public static void LoadList() {
		FncGlobal.startProgress("Refreshing...");

		FncTables.clearTable(modelExport); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowExport.setModel(listModel); 

		String strSQL = "select false as tag, c_brstn, c_acct_no, c_check_no, c_amount, \n" + 
				"c_check_date::date, c_due_date::date, c_entity_name, c_description \n" + 
				"from view_check_for_pdc_warehousing_export()";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelExport.addRow(db.getResult()[x]);
				listModel.addElement(modelExport.getRowCount());
			}			
		}

		FncGlobal.stopProgress();
    }
    
    private void Export() {
    	Integer intRow = 0; 
    	String strCSV = sdf_export.format(FncGlobal.getDateToday()).toString().concat(".csv");  
    	
    	Boolean blnProceed = false; 
    	
		for (int x=0; x<modelExport.getRowCount(); x++) {
			if ((Boolean) modelExport.getValueAt(x, 0)) {
				blnProceed = true;
			}
		}
    	
		if (blnProceed) {
			try {
	    		FileWriter csvWriter = new FileWriter("/home/jfatallo/Desktop/"+strCSV);
	    		csvWriter.append("");
	    		csvWriter.append(",");
	    		
	    		csvWriter.append("Bank Name (BRSTN)");
	    		csvWriter.append(",");
	    		
	    		csvWriter.append("Account No.");
	    		csvWriter.append(",");

	    		csvWriter.append("Check No.");
	    		csvWriter.append(",");
	    		
	    		csvWriter.append("Amount");
	    		csvWriter.append(",");
	    		
	    		csvWriter.append("Check Date");
	    		csvWriter.append(",");
	    		
	    		csvWriter.append("Due Date");
	    		csvWriter.append(",");
	    		
	    		csvWriter.append("Remarks 1");
	    		csvWriter.append(",");
	    		
	    		csvWriter.append("Remarks 2");
	    		csvWriter.append("\n");

	    		for (int x=0; x<modelExport.getRowCount(); x++) {
	    			if ((Boolean) modelExport.getValueAt(x, 0)) {
	        			csvWriter.append((++intRow).toString());
	            		csvWriter.append(",");
	            		
	            		csvWriter.append(modelExport.getValueAt(x, 1).toString());
	            		csvWriter.append(",");
	            		
	            		csvWriter.append(modelExport.getValueAt(x, 2).toString());
	            		csvWriter.append(",");

	            		csvWriter.append(modelExport.getValueAt(x, 3).toString());
	            		csvWriter.append(",");
	            		
	            		csvWriter.append(modelExport.getValueAt(x, 4).toString());
	            		csvWriter.append(",");
	            		
	            		csvWriter.append(sdf.format((Date) modelExport.getValueAt(x, 5)).toString());
	            		csvWriter.append(",");
	            		
	            		csvWriter.append(sdf.format((Date) modelExport.getValueAt(x, 6)).toString());
	            		csvWriter.append(",");
	            		
	            		csvWriter.append("\""+modelExport.getValueAt(x, 7).toString()+"\"");
	            		csvWriter.append(",");
	            		
	            		csvWriter.append(modelExport.getValueAt(x, 8).toString());
	            		csvWriter.append("\n");	
	    			}
	    		}
	    		
	    		csvWriter.flush();
	    		csvWriter.close();
	    		
	    		JOptionPane.showMessageDialog(null, "CSV file created!");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Something went wrong.\nCall the JST.");
			}	
		} else {
			JOptionPane.showMessageDialog(null, "No row was selected.");
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
}
