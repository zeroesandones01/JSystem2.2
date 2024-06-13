package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import interfaces._GUI;

import org.jdesktop.swingx.JXPanel;

import tablemodel.modelMBTC_pisoDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;

import com.toedter.calendar.JDateChooser;

import components.JTBorderFactory;
import components._JTableMain;

public class panMBTC_Credit extends JXPanel implements _GUI {

	private static final long serialVersionUID = -1476057661659066875L;
	private RealTimeDebitPiso rtdMain;
	Dimension size = new Dimension(810, 500);
	Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private JLabel lblPro;
	private JLabel lblDivider;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JLabel lblDir;
	
	private JComboBox cboPro;
	
	private JDateChooser dteFrom;
	private JDateChooser dteTo;
	
	private JButton btnProcess;
	private JButton btnSave;
	private JButton btnPreview;
	private JButton btnExport;
	private JButton btnDir;
	
	private JXPanel panGrid;
	
	/***	Table Declarations		***/
	private JScrollPane scrPiso;
	public static JList rowPiso;
	private static _JTableMain tblPiso;
	private modelMBTC_pisoDebit modelPiso;
	/***	Table Declarations		***/
	
	private Boolean blnGen = false;
	
	private static JTextField txtDir;
	
	private static _RealTimeDebit rtd;
	
	public panMBTC_Credit(RealTimeDebitPiso rtdPiso) {
		this.rtdMain = rtdPiso;
		initGUI();
	}

	public panMBTC_Credit(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public panMBTC_Credit(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public panMBTC_Credit(LayoutManager layout, boolean isDoubleBuffered) {
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
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 170));
				//panPage.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel panLabel = new JXPanel(new GridLayout(4, 1, 5, 5));
					panPage.add(panLabel, BorderLayout.LINE_START);					
					panLabel.setPreferredSize(new Dimension(400, 0));
					panLabel.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						/***	Project		***/
						JXPanel panPro = new JXPanel(new BorderLayout(5, 0));
						panLabel.add(panPro, BorderLayout.CENTER);
						{
							lblPro = new JLabel("Project:");
							panPro.add(lblPro, BorderLayout.LINE_START);
							lblPro.setHorizontalAlignment(JTextField.LEFT);
							lblPro.setPreferredSize(new Dimension(100, 0));
						}
						{
							cboPro = new JComboBox();
							panPro.add(cboPro, BorderLayout.CENTER);
							FillCombobox();
							cboPro.setSelectedIndex(0);
						}
					}
					{
						/***	Label		***/
						lblDivider = new JLabel("MBTC Activation Date:");
						panLabel.add(lblDivider, BorderLayout.CENTER);
						lblDivider.setFont(FncLookAndFeel.systemFont_Bold);
						lblDivider.setHorizontalAlignment(JTextField.LEADING);	
					}
					{
						/***	From		***/
						JXPanel panFrom = new JXPanel(new BorderLayout(5, 0));
						panLabel.add(panFrom, BorderLayout.CENTER);
						{
							lblFrom = new JLabel("From:");
							panFrom.add(lblFrom, BorderLayout.LINE_START);
							lblFrom.setHorizontalAlignment(JTextField.LEFT);
							lblFrom.setPreferredSize(new Dimension(100, 0));
						}
						{
							dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							panFrom.add(dteFrom, BorderLayout.CENTER);
							dteFrom.getCalendarButton().setVisible(true);
							dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}
					}
					{
						/***	From		***/
						JXPanel panTo = new JXPanel(new BorderLayout(5, 0));
						panLabel.add(panTo, BorderLayout.CENTER);
						{
							lblTo = new JLabel("To:");
							panTo.add(lblTo, BorderLayout.LINE_START);
							lblTo.setHorizontalAlignment(JTextField.LEFT);
							lblTo.setPreferredSize(new Dimension(100, 0));
						}
						{
							dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							panTo.add(dteTo, BorderLayout.CENTER);
							dteTo.getCalendarButton().setVisible(true);
							dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}
					}
					JXPanel panButton = new JXPanel(new GridLayout(2, 1, 5, 5));
					panPage.add(panButton, BorderLayout.CENTER);
					panButton.setPreferredSize(new Dimension(345, 0));
					{
						JXPanel panGrp1 = new JXPanel(new GridLayout(1, 1, 5, 5));
						panButton.add(panGrp1, BorderLayout.CENTER);
					}
					{
						JXPanel panGrp2 = new JXPanel(new GridLayout(1, 4, 5, 5));
						panButton.add(panGrp2, BorderLayout.CENTER);
						{
							btnProcess = new JButton("Generate");
							panGrp2.add(btnProcess, BorderLayout.CENTER);
							btnProcess.setEnabled(true);
							btnProcess.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if (Generate()) {
										buttonState(true, true, false);
									} else {
										buttonState(false, false, false);
									}									
								}
							});
						}
						{
							btnPreview = new JButton("Preview");
							panGrp2.add(btnPreview, BorderLayout.CENTER);
							btnPreview.setEnabled(false);
							btnPreview.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									Preview();
								}
							});
						}
						{
							btnSave = new JButton("Save");
							panGrp2.add(btnSave, BorderLayout.CENTER);
							btnSave.setEnabled(false);
							btnSave.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									String strCount = "";
									String strCountSQL = "";
									String strTrans = GetTransNo();
									Boolean blnDo = false;
									
									for (int x = 0; x < modelPiso.getRowCount(); x++) {
										if ((Boolean) modelPiso.getValueAt(x, 0)) {
											blnDo = true;
											String strSQL = "";
											pgUpdate db = new pgUpdate();
											
											strCountSQL = "select COUNT(*)::varchar from rf_rtd_piso_debit where entity_id = '"+modelPiso.getValueAt(x, 9)+"' \n" +
													"and proj_id = '"+modelPiso.getValueAt(x, 10)+"' \n" +
													"and pbl_id = '"+modelPiso.getValueAt(x, 11)+"' \n" +
													"and seq_no = "+modelPiso.getValueAt(x, 12);
											
											System.out.println("");
											System.out.println(strCountSQL);
											
											strCount = rtd.GetValue(strCountSQL);
											Integer intCount = Integer.parseInt(strCount); 
											
											System.out.println("");
											System.out.println("Name: " + modelPiso.getValueAt(x, 1));
											System.out.println("strCount: " + modelPiso.getValueAt(x, 1));
											
											/*
											if (intCount!=0) {
												strSQL = "update rf_rtd_piso_debit \n" +
														"set amount = "+modelPiso.getValueAt(x, 8)+" \n" +
														"where entity_id = '"+modelPiso.getValueAt(x, 9)+"' \n" +
														"and proj_id = '"+modelPiso.getValueAt(x, 10)+"' \n" +
														"and pbl_id = '"+modelPiso.getValueAt(x, 11)+"' \n" +
														"and seq_no = "+modelPiso.getValueAt(x, 12);
											} else {
												strSQL = "insert into rf_rtd_piso_debit (debit_rec_id, entity_id, proj_id, pbl_id, seq_no, accountno, trans_no, amount, date_created, created_by) \n" +
														"values ("+
														GetRecID() +", \n" +
														"'"+modelPiso.getValueAt(x, 9)+"', \n" +
														"'"+modelPiso.getValueAt(x, 10)+"', \n" +
														"'"+modelPiso.getValueAt(x, 11)+"', \n" +
														modelPiso.getValueAt(x, 12)+", \n" +
														"'"+modelPiso.getValueAt(x, 7)+"', \n" +
														"'"+strTrans+"', \n" +
														modelPiso.getValueAt(x, 8)+", \n" +
														"current_date, \n" +
														"'"+UserInfo.EmployeeCode+"')";	
											}
											*/
											strSQL = "insert into rf_rtd_piso_debit (debit_rec_id, entity_id, proj_id, pbl_id, seq_no, accountno, trans_no, amount, date_created, created_by) \n" +
													"values ("+
													GetRecID() +", \n" +
													"'"+modelPiso.getValueAt(x, 9)+"', \n" +
													"'"+modelPiso.getValueAt(x, 10)+"', \n" +
													"'"+modelPiso.getValueAt(x, 11)+"', \n" +
													modelPiso.getValueAt(x, 12)+", \n" +
													"'"+modelPiso.getValueAt(x, 7)+"', \n" +
													"'"+strTrans+"', \n" +
													modelPiso.getValueAt(x, 8)+", \n" +
													"current_date, \n" +
													"'"+UserInfo.EmployeeCode+"')";	
											
											db.executeUpdate(strSQL, false);
											db.commit();	
										}
									}
									
									if (blnDo) {
										buttonState(true, false, true);
										blnGen = true;
										DeleteRows();
										JOptionPane.showMessageDialog(null, "Saved!");
										
										SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
										Date dateObj = new Date();
										String strParamDate = "Piso Debit " + (String) sdfTo.format(dateObj);
										
										scribeRTD(CreateString(), strParamDate);
									} else {
										buttonState(true, true, false);
										blnGen = false;
										JOptionPane.showMessageDialog(null, "No row was selected!");
										Generate();
									}						
								}
							});
						}
						{
							btnExport = new JButton("Export");
							panGrp2.add(btnExport, BorderLayout.CENTER);
							btnExport.setEnabled(false);
							btnExport.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									Boolean blnWith = false;
									for (int x = 0; x < modelPiso.getRowCount(); x++) {
										if ((Boolean) modelPiso.getValueAt(x, 0)) {
											blnWith = true;
										}
									}
									
									if (blnWith) {
										try {
											scribeRTD(glyphRTD(), "PAYROLL.DAT");
											buttonState(true, false, false);
										} catch (NullPointerException np1) {
											System.out.println("");
											System.out.println("Error Scribing!");
										}	
									} else {
										JOptionPane.showMessageDialog(null, "No row was selected.");
									}
									
									DeleteRows();
								}
							});
						}
					}
				}
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					CreatePage();
					panCenter.add(panGrid, BorderLayout.CENTER);
				}
				JXPanel panEnd = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5));
					panEnd.add(panDiv2, BorderLayout.LINE_END);
					panDiv2.setPreferredSize(new Dimension(600, 0));
					{
						lblDir = new JLabel("Directory:");
						panDiv2.add(lblDir, BorderLayout.LINE_START);
						lblDir.setHorizontalAlignment(JTextField.LEFT);
						lblDir.setPreferredSize(new Dimension(100, 0));
					}
					{
						txtDir = new JTextField("C:/");
						panDiv2.add(txtDir, BorderLayout.CENTER);
						txtDir.setHorizontalAlignment(JTextField.LEFT);
						txtDir.setEditable(false);
					}
					{
						btnDir = new JButton("Set");
						panDiv2.add(btnDir, BorderLayout.LINE_END);
						btnDir.setPreferredSize(new Dimension(60, 0));
						btnDir.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								SaveDir(_RealTimeDebit.OpenDir("Folder"));								
							}
						});
					}
				}
			}
			autoDir();
			CheckDir();
		}
	}
	
	private void FillCombobox() {
		pgSelect dbCbo = new pgSelect();
		String SQL = "select 'All Projects' as Project union select trim(proj_name) as Project from mf_project order by Project";
		dbCbo.select(SQL);
		cboPro.removeAllItems();
		
		if (dbCbo.isNotNull()) {
			for (int x = 0; x < dbCbo.getRowCount(); x++) {
				cboPro.addItem((String) dbCbo.getResult()[x][0]);
			}
		}
	}
	
	private void CreatePage() {
		panGrid = new JXPanel(new BorderLayout(5, 5));
		panGrid.setOpaque(isOpaque());
		{
			scrPiso = new JScrollPane();
			panGrid.add(scrPiso, BorderLayout.CENTER);
			scrPiso.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrPiso.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrPiso.setBorder(lineGray);
			{
				modelPiso = new modelMBTC_pisoDebit();
				tblPiso = new _JTableMain(modelPiso);
				
				rowPiso = tblPiso.getRowHeader();
				scrPiso.setViewportView(tblPiso);

				tblPiso.getColumnModel().getColumn(0).setPreferredWidth(30);
				tblPiso.getColumnModel().getColumn(1).setPreferredWidth(200);
				
				tblPiso.getColumnModel().getColumn(2).setPreferredWidth(60);
				tblPiso.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblPiso.getColumnModel().getColumn(4).setPreferredWidth(50);
				tblPiso.getColumnModel().getColumn(5).setPreferredWidth(50);
				
				tblPiso.getColumnModel().getColumn(6).setPreferredWidth(100);
				tblPiso.getColumnModel().getColumn(7).setPreferredWidth(101);
				tblPiso.setSortable(false);
			}
			{
				rowPiso = tblPiso.getRowHeader();
				rowPiso.setModel(new DefaultListModel());
				scrPiso.setRowHeaderView(rowPiso);
				scrPiso.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
			}
		}
	}
	
	public Boolean Generate() {
		blnGen = false;
		
		FncTables.clearTable(modelPiso);
		DefaultListModel listModel = new DefaultListModel();
		rowPiso.setModel(listModel); 
		
		String strProID = "";
		if ((Boolean) cboPro.getSelectedItem().equals("All Projects")) {
			strProID = "";	
		} else {
			strProID = _RealTimeDebit.GetValue("select proj_id from mf_project where proj_name = '"+cboPro.getSelectedItem()+"'");
		}
		
		Boolean blnRet = false;
		FncGlobal.startProgress("Generating Qualified Accounts");
		
		String strSQL = "select * from view_rtd_piso_debit('"+strProID+"', '"+dteFrom.getDate()+"', '"+dteTo.getDate()+"') order by name";
		pgSelect db = new pgSelect();
		db.select(strSQL);
		
		System.out.println("");
		System.out.println(strSQL);
		
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelPiso.addRow(db.getResult()[x]);
				listModel.addElement(modelPiso.getRowCount());
			}
			blnRet = true;
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
			blnRet = false;
		};
		
		FncGlobal.stopProgress();
		return blnRet;
	}
	
	private void buttonState(Boolean blnPreview, Boolean blnSave, Boolean blnExport) {
		btnPreview.setEnabled(blnPreview);
		btnSave.setEnabled(blnSave);
		btnExport.setEnabled(blnExport);
	}
	
	private Integer GetRecID() {
		String strSQL = "";
		Integer intRec = 0;
		pgSelect dbRec = new pgSelect();
		
		System.out.println("");
		System.out.println("Inspection");
		
		strSQL = "SELECT max(COALESCE(debit_rec_id::INT, 0)) + 1 FROM rf_rtd_piso_debit";
		dbRec.select(strSQL);
		
		if (dbRec.isNotNull()) {
			intRec = (Integer) dbRec.getResult()[0][0];
		} else {
			intRec = 1;
		}
		
		if (intRec==null) {
			intRec = 1;
		}
		
		return intRec;
	}
	
	private String GetTransNo() {
		String strSQL = "";
		String strTransNo = "";
		pgSelect dbRec = new pgSelect();
		
		System.out.println("");
		System.out.println("Inspection");
		
		strSQL = "SELECT TRIM(to_char(max(COALESCE(trans_no::INT, 0)) + 1, '000000')) FROM rf_rtd_piso_debit";
		dbRec.select(strSQL);
		
		if (dbRec.isNotNull()) {
			strTransNo = (String) dbRec.getResult()[0][0];
		} else {
			strTransNo = "000001";
		}
		
		if (strTransNo==null) {
			strTransNo = "000001";
		}
		
		return strTransNo;
	}
	
    public void scribeRTD(String strMain, String strFileName) {
    	String strFile = "";
    	String strWrite = "";
    	
    	File f = new File(txtDir.getText());
    	
    	if (!f.exists() && !f.isDirectory()) {
    		JOptionPane.showMessageDialog(this, "Folder does not exist.\nSet default directory.");
    	} else {
    		strFile = strFileName;
        	File FileText = new File(txtDir.getText() + "/" + strFile);
        	Boolean blnProceed = false;
        	
    		if(FileText.exists()) {
    			if(JOptionPane.showConfirmDialog(null, "A file with the same name exists in the specified directory.\nOverwrite?", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
    				blnProceed = true;
    			}
    		} else {
    			blnProceed = true;
    		}
    		
    		if (blnProceed) {
    			FileText.delete();
    			strWrite = strMain;
            	
                try {
                    FileOutputStream is = new FileOutputStream(FileText);
                    OutputStreamWriter osw = new OutputStreamWriter(is);    
                    Writer w = new BufferedWriter(osw);
                    w.write(strWrite);
                    w.close();
                } catch (IOException e) {
                    System.err.println("Problem writing to the file statsTest.txt");
                }
    		}

    		JOptionPane.showMessageDialog(null, strFileName + " textfile created!", "Export Successful", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    public static void autoDir() {
		String strDir = "";
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
		String SQL = "select trim(to_char(max(coalesce(rtd_id::int,0))+1)) from rf_piso_details" ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			rec_id = (String)db.getResult()[0][0];
			
			if (rec_id==null) {
				rec_id = "1";
			}
		} else {
			rec_id = "1";
		}
		
		return rec_id;
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
	
    public static String glyphRTD() {
    	Integer intCount = 0;
    	String strWrite = "";
    	BigDecimal Total = null;
    	String nl = System.getProperty("line.separator");
		
    	for(int x = 0;x < tblPiso.getRowCount();x++) {
    		if ((Boolean) tblPiso.getValueAt(x, 0)) {
    			BigDecimal Amt;
    			String strAcct = "";

    			Date dteDate = FncGlobal.GetDate("select date_opened::date from rf_rtd_accounts where entity_id = '"+(String) tblPiso.getValueAt(x, 9).toString()+"' and projcode = '"+(String) tblPiso.getValueAt(x, 10).toString()+"' and pbl_id = '"+(String) tblPiso.getValueAt(x, 11).toString()+"' and seq_no::INT = '"+(String) tblPiso.getValueAt(x, 12).toString()+"'::INT");
    			SimpleDateFormat dteFormat = new SimpleDateFormat("MMddyyyy");
    			String strDate = dteFormat.format(dteDate);
    			
    			try {
    				Amt = (BigDecimal) tblPiso.getValueAt(x, 8);
    			} catch(ClassCastException ex) {
    				try {
    					Amt = new BigDecimal((Long) tblPiso.getValueAt(x, 8));
    				} catch (ClassCastException ey) {
    					Amt = new BigDecimal((Double) tblPiso.getValueAt(x, 8));
    				}
    			} 
    			
    			strAcct = (String) tblPiso.getValueAt(x, 7);
    			strAcct = strAcct.substring(3, strAcct.length()).trim();
    			
    			try {
    				Total = Total.add(Amt);
    			} catch (NullPointerException e) {
    				Total = Amt;
    			}
    			
    			intCount = intCount + 1;
    			
    			strWrite = strWrite + "2";																			/*		Fixed Value							*/
    			strWrite = strWrite + "254";																		/*		Company's Depository Branch Code	*/
    			strWrite = strWrite + "26";																			/*		Bank Code							*/
    			strWrite = strWrite + "001";																		/*		Currency							*/
    			strWrite = strWrite + "254";																		/*		Payroll account branch code			*/
    			strWrite = strWrite + "0000000";																	/*		Fixed Value							*/
    			//strWrite = strWrite + _RealTimeDebit.Padme("CENQHOMES DEVELOPMENT CORP", 40);					  	/*		Company Name						*/
    			strWrite = strWrite + "CENQHOMES DEVELOPMENT CORP              ";	  								/*		Company Name						*/
    			strWrite = strWrite + strAcct;																		/*		Fixed Value							*/
    			strWrite = strWrite + Stringer(Amt, 15);															/*		Amount								*/
    			strWrite = strWrite + "9";																			/*		Fixed Value							*/
    			strWrite = strWrite + "00102";																		/*		Company Code<?>						*/
    			strWrite = strWrite + strDate;
    			strWrite = strWrite + nl;
    		}
    	}
    	
    	/*
    	strWrite = strWrite + _RealTimeDebit.Padme(intCount.toString(), 5);
    	strWrite = strWrite + Stringer(Total, 15);
    	*/
    	
    	System.out.println("Total: " + Total.toString());
    	System.out.println("Count: " + intCount);
    	
    	return strWrite;
    }
    
    private void Preview() {
		String strSQL = "";
		pgUpdate dbDelete = new pgUpdate();
		
		dbDelete.executeUpdate("DELETE FROM rf_mbtc_piso_printables;", false);
		dbDelete.commit();

		Boolean blnWith = false;
		for (int x = 0; x < tblPiso.getRowCount(); x++) {
			if ((Boolean) tblPiso.getValueAt(x, 0)) {
				blnWith = true;
			}
		}
		
		if (!blnWith) {
			JOptionPane.showMessageDialog(null, "No row was selected.");
		} else {
			for (int x = 0; x < tblPiso.getRowCount(); x++) {
				if ((Boolean) tblPiso.getValueAt(x, 0)) {
					strSQL = "insert into rf_mbtc_piso_printables (name, phase, block, lot, accountno, amount) \n" +
							"values ('"+tblPiso.getValueAt(x, 1).toString()+"', '"+tblPiso.getValueAt(x, 3).toString()+"', '"+tblPiso.getValueAt(x, 4).toString()+"', '"+tblPiso.getValueAt(x, 5).toString()+"', '"+tblPiso.getValueAt(x, 7).toString()+"', '"+tblPiso.getValueAt(x, 8).toString()+"'::NUMERIC(19, 2))";
					pgUpdate dbInsert = new pgUpdate();
					dbInsert.executeUpdate(strSQL, true);
					dbInsert.commit();
				}	
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("user_code", UserInfo.EmployeeCode);
			mapParameters.put("user_name", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("dteFrom", dteFrom.getDate().toString());
			mapParameters.put("dteTo", dteTo.getDate().toString());
			FncReport.generateReport("/Reports/rpt_MBTC_PisoDebit.jasper", "Real-Time Debit List", "", mapParameters);
			
			System.out.println("");
			System.out.println(CreateString());
		}
    }
    
    private String CreateString() {
    	String strSave = "";
    	String strTemp = "";
    	String nl = System.getProperty("line.separator");
    	
    	for (int x = 0; x < modelPiso.getRowCount(); x++) {
    		if ((Boolean) modelPiso.getValueAt(x, 0)) {
    			strTemp = strTemp + modelPiso.getValueAt(x, 1).toString() + 
    					nl +
    					modelPiso.getValueAt(x, 2).toString() + 
    					" " +
    					modelPiso.getValueAt(x, 3).toString() +
    					"-" +
    					modelPiso.getValueAt(x, 4).toString() +
    					"-" +
    					modelPiso.getValueAt(x, 5).toString() +
    					nl +
    					"Account Number: " +
    					modelPiso.getValueAt(x, 7).toString() +
    					nl +
    					"Amount: " +
    					modelPiso.getValueAt(x, 8).toString() +    					
    					nl + nl;
    		}
    	}
    	
    	strSave = strTemp;
    	return strSave;
    }
    
    private void DeleteRows() {
		for (int x = 0; x < modelPiso.getRowCount(); x++) {
			if (!(Boolean) modelPiso.getValueAt(x, 0)) {
				modelPiso.removeRow(x);
				x = -1;
			}
		}
    }
}
