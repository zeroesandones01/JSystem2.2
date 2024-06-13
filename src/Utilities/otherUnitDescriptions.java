package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class otherUnitDescriptions extends _JInternalFrame implements _GUI {
	static String title = "Other Unit Descriptions";
	Dimension frame = new Dimension(600, 400); 
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	private static JTextArea txtLog;
	private JScrollPane scrLog;
	private static JTextField txtDir;
	private static JProgressBar pBar; 
	
	private JButton btnOpen;
	private JButton btnPrime;
	private JButton btnExecute; 
	
	private static _JLookup txtProjID; 
	
	private static JTextField txtProject; 
	
	private static long millis = 10; 
	
	public otherUnitDescriptions() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public otherUnitDescriptions(String title) {
		super(title);
		initGUI(); 
	}

	public otherUnitDescriptions(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setSize(frame);
		this.setTitle(title);
		
		JXPanel panMain = new JXPanel(new BorderLayout(3, 3)); 
		this.add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(3, 3, 3, 3));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panPage, BorderLayout.CENTER);
				{
					{
						JXPanel panLog = new JXPanel(new BorderLayout(3, 3)); 
						panPage.add(panLog, BorderLayout.CENTER);
						{
							{
								JXPanel panDir = new JXPanel(new GridLayout(2, 1, 5, 5));
								panLog.add(panDir, BorderLayout.PAGE_START); 
								panDir.setPreferredSize(new Dimension(0, 120));
								{
									{
										JXPanel panProject = new JXPanel(new BorderLayout(5, 5)); 
										panDir.add(panProject); 
										panProject.setBorder(JTBorderFactory.createTitleBorder("Project", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
												panProject.add(panLabel, BorderLayout.LINE_START); 
												panLabel.setPreferredSize(new Dimension(160, 0));
												{
													{
														JLabel lblProject = new JLabel("Project"); 
														panLabel.add(lblProject); 
														lblProject.setHorizontalAlignment(JLabel.LEFT);
													}
													{
														txtProjID = new _JLookup("015"); 
														panLabel.add(txtProjID); 
														txtProjID.setHorizontalAlignment(JTextField.CENTER);
														txtProjID.setLookupSQL(ProjectSQL(""));
														txtProjID.setReturnColumn(0);
														txtProjID.addLookupListener(new LookupListener() {
															public void lookupPerformed(LookupEvent e) {
																Object[] data = ((_JLookup) e.getSource()).getDataSet();
																if (data != null) {
																	txtProject.setText(data[2].toString());
																}
															}
														});
														txtProjID.setValue("015");
													}
												}
												{
													txtProject = new JTextField("TERRAVERDE RESIDENCES"); 
													panProject.add(txtProject, BorderLayout.CENTER); 
													txtProject.setHorizontalAlignment(JTextField.CENTER);
													txtProject.setEditable(false);
												}
											}
										}
									}
									{
										JXPanel panDirectory = new JXPanel(new BorderLayout(5, 5)); 
										panDir.add(panDirectory); 
										panDirectory.setBorder(JTBorderFactory.createTitleBorder("Directory", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												btnOpen = new JButton("Open"); 
												panDirectory.add(btnOpen, BorderLayout.LINE_START); 
												btnOpen.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) {
														txtDir.setText(FncGlobal.OpenDir("File"));
														InitiateProgress(); 
													}
												});
												btnOpen.setPreferredSize(new Dimension(80, 0));
											}
											{
												txtDir = new JTextField(""); 
												panDirectory.add(txtDir, BorderLayout.CENTER);
												txtDir.setHorizontalAlignment(JTextField.LEADING);
												txtDir.setEditable(false);
											}
										}
									}
								}
							}
							{
								txtLog = new JTextArea("");
								txtLog.setFont(new Font("Tahoma", Font.PLAIN, 10));
								txtLog.setBackground(Color.BLACK);
								txtLog.setForeground(Color.WHITE);
								txtLog.setEditable(false);
								txtLog.setLineWrap(true);
							}
							{
								scrLog = new JScrollPane(txtLog);
								panLog.add(scrLog, BorderLayout.CENTER);
							}
						}
					}
					{
						JXPanel panProgress = new JXPanel(new BorderLayout(3, 3)); 
						//panPage.add(panProgress, BorderLayout.PAGE_END);
						panProgress.setPreferredSize(new Dimension(0, 25));
						{
							pBar = new JProgressBar();
							//panProgress.add(pBar, BorderLayout.CENTER); 
							pBar.setStringPainted(true);
						}
						InitiateProgress();
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new GridLayout(1, 3, 5, 5)); 
				panMain.add(panCenter, BorderLayout.PAGE_END);
				panCenter.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPrime = new JButton("Prime"); 
						panCenter.add(btnPrime);
						btnPrime.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								startThread(); 
							}
						});
					}
					{
						btnExecute = new JButton("Execute"); 
						panCenter.add(btnExecute);
					}
				}
			}
		}
	}

	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
			   "ORDER BY proj_name";
	}
	
	private static void AppendProgress() {
		if (pBar.getValue()<=pBar.getMaximum()) {
			pBar.setValue(pBar.getValue() + 1);
		} else {
			InitiateProgress(); 
		}
	}
	
	private static void InitiateProgress() {
		pBar.setValue(0);
	}
	
	private static void AddLog(String strLog) {
		if (txtLog.getDocument().getLength()+strLog.length()<=Integer.MAX_VALUE) {
			txtLog.append(strLog+"\n\n");
			txtLog.setCaretPosition(txtLog.getDocument().getLength());	
		} else {
			txtLog.setText("");
		}
	}
	
	private static void Detail() {
		AddLog("Detailing uploaded records...");
		
		pgUpdate dbExec = new pgUpdate(); 
	    dbExec.executeUpdate("update mf_unit_other_descriptions \n" + 
	    		"set phase = replace(phase, '.0', ''), block = replace(block, '.0', ''), lot = replace(lot, '.0', ''), status_id = 'A'; \n" + 
	    		"update mf_unit_other_descriptions c \n" + 
	    		"set unit_id = b.unit_id, proj_id = b.proj_id, pbl_id = b.pbl_id, \n" +
	    		"description = b.description, sub_proj_id = b.sub_proj_id \n" + 
	    		"from mf_unit_other_descriptions a \n" + 
	    		"inner join mf_unit_info b on coalesce(a.phase, '') = coalesce(b.phase, '') and coalesce(a.block, '') = coalesce(b.block, '') and coalesce(a.lot, '') = coalesce(b.lot, '') \n" + 
	    		"where a.rec_id = c.rec_id and a.status_id = 'A'; ", false);
	    dbExec.commit();
	    
	    AddLog("Uploaded records detailed...");
	}
	
	private static void DeactivatePrevious() throws InterruptedException {
		pgUpdate dbExec = new pgUpdate(); 
		pgSelect dbRow = new pgSelect(); 
		
		AddLog("Deactivating duplicate records...");
		
		dbRow.select("select *\n" + 
			"from mf_unit_other_descriptions\n" +
			"where date_created::date = now()::date and status_id = 'A'");
		
		for (int x=0; x<dbRow.getRowCount(); x++) {
			
			AddLog("Checking phase "+dbRow.getResult()[x][5].toString()+" block "+dbRow.getResult()[x][6].toString()+" lot "+dbRow.getResult()[x][7].toString()+"...");
			
			dbExec = new pgUpdate();
			dbExec.executeUpdate("update mf_unit_other_descriptions \n" + 
					"set status_id = 'I' \n" + 
					"where rec_id in (select x.rec_id from mf_unit_other_descriptions x \n" +
					"where x.phase = '"+dbRow.getResult()[x][5].toString()+"' and x.block = '"+dbRow.getResult()[x][6].toString()+"' \n" +
					"and x.lot = '"+dbRow.getResult()[x][7].toString()+"' and rec_id < "+dbRow.getResult()[x][11].toString()+")", false);
			dbExec.commit();
			
			Thread.sleep(millis);
		}
		
		AddLog("Duplicate records deactivated...");
	}
	
	private static void DeatailPhaseLot() throws InterruptedException {
		String strPBL = "";  
				
		pgUpdate dbExec = new pgUpdate(); 
		pgSelect dbRow = new pgSelect(); 
		
		AddLog("Detailing phase-lot only records...");
		
		dbRow.select("select *\n" + 
			"from mf_unit_other_descriptions\n" + 
			"where date_created::date = now()::date and unit_id is null and status_id = 'A'\n" + 
			"order by date_created");
		
		AddLog(dbRow.getRowCount()+" rows doesn't have pbl and unit IDs..."); 
		
		for (int x=0; x<dbRow.getRowCount(); x++) {
			dbExec = new pgUpdate();
			
			AddLog("Detailing phase "+dbRow.getResult()[x][5].toString()+" block "+dbRow.getResult()[x][6].toString()+" lot "+dbRow.getResult()[x][7].toString()+"...");
			AddLog("select exists(select *\n" + 
					"from mf_unit_other_descriptions\n" + 
					"where phase = '"+dbRow.getResult()[x][5].toString()+"' and block = '"+dbRow.getResult()[x][6].toString()+"' \n" +
					"and lot = '"+dbRow.getResult()[x][7].toString()+"' \n" + 
					"and coalesce(pbl_id, '') != '')");
			AddLog("Exists: "+FncGlobal.GetBoolean("select exists(select *\n" + 
					"from mf_unit_other_descriptions\n" + 
					"where phase = '"+dbRow.getResult()[x][5].toString()+"' and block = '"+dbRow.getResult()[x][6].toString()+"' \n" +
					"and lot = '"+dbRow.getResult()[x][7].toString()+"' \n" + 
					"and coalesce(pbl_id, '') != '')"));
			
			/*	Check PBL ID if existing	*/
			if (FncGlobal.GetBoolean("select exists(select *\n" + 
					"from mf_unit_other_descriptions\n" + 
					"where phase = '"+dbRow.getResult()[x][5].toString()+"' and block = '"+dbRow.getResult()[x][6].toString()+"' \n" +
					"and lot = '"+dbRow.getResult()[x][7].toString()+"' \n" + 
					"and coalesce(pbl_id, '') != '')")) {
				
				AddLog("PBL ID exists...");
				
				strPBL = FncGlobal.GetString("select pbl_id \n" + 
						"from mf_unit_other_descriptions\n" + 
						"where phase = '"+dbRow.getResult()[x][5].toString()+"' and block = '"+dbRow.getResult()[x][6].toString()+"' \n" +
						"and lot = '"+dbRow.getResult()[x][7].toString()+"' and coalesce(pbl_id, '') != '' limit 1"); 
			} else {
				AddLog("PBL ID does not exist...");
				
				/*
				 * strPBL =
				 * FncGlobal.GetString("select (coalesce(max(a.pbl_id::int), 0)+1)::varchar \n"
				 * + "from (select pbl_id from mf_unit_info union select pbl_id \n" +
				 * "from mf_unit_other_descriptions where pbl_id is not null) a");
				 */ 
				
				strPBL = FncGlobal.GetString("select (coalesce(max(a.pbl_id::int), 0)+1)::varchar \n" + 
						"from (select pbl_id from mf_unit_info \n" + 
						"      union \n" + 
						"      select pbl_id from mf_unit_other_descriptions where pbl_id is not null\n" + 
						"      union\n" + 
						"      select pbl_id from mf_unit_info_pending where unit_id not in ('TVP000001', 'TVP000002', 'TVP000003')) a");
			
			}
			
			AddLog("PBL ID : "+strPBL);
			AddLog("Unit ID : "+"TV"+String.format("%07d", Integer.parseInt(strPBL)));
			
			dbExec.executeUpdate("update mf_unit_other_descriptions \n" +
					"set pbl_id = '"+strPBL+"', unit_id = '"+"TV"+String.format("%07d", Integer.parseInt(strPBL))+"' \n" +
					"where rec_id = "+dbRow.getResult()[x][11].toString()+"::int", false);
			dbExec.commit();
			
			AddLog("PBL ID updated...");

		}
		
		AddLog("Detailing finished...");

	}
	
	private static void DeatailProjectID() throws InterruptedException {
		String strPBL = "";  
				
		pgUpdate dbExec = new pgUpdate(); 
		
		AddLog("Updating project details..");
		
		dbExec.executeUpdate("update mf_unit_other_descriptions y \n" + 
				"set proj_id = '"+txtProjID.getValue()+"', sub_proj_id = (select y.sub_proj_id from mf_sub_project y where y.proj_id = '"+txtProjID.getValue()+"' and y.phase = x.phase and status_id = 'A'), \n" +//ADDED STATUS ID BY LESTER DCRF 2718
				"description = CONCAT('PH ', x.phase, ' ', 'BK ', x.block, ' ', 'LT ', x.lot) \n" + 
				"from mf_unit_other_descriptions x \n" + 
				"where x.date_created::date = now()::date and x.proj_id is null \n" + 
				"and x.status_id = 'A' and x.rec_id = y.rec_id", false);
		dbExec.commit();
		
		AddLog("Project details updated...");
	}
	
    private static void startThread() {
    	
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws Exception {
				String strCell = "";
				String strExec = "";
				String strParams = "";
				String strDir = txtDir.getText();
				
				try {
				    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(strDir));
				    HSSFWorkbook wb = new HSSFWorkbook(fs);
				    HSSFSheet sheet = wb.getSheetAt(0);
				    HSSFRow row;
				    HSSFCell cell;

				    int rows; 
				    rows = sheet.getPhysicalNumberOfRows();
				    pBar.setMaximum(rows);
				    
				    int cols = 0; 
				    int tmp = 0;

				    for(int i = 0; i < 10 || i < rows; i++) {
				        row = sheet.getRow(i);
				        if(row != null) {
				            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				            if(tmp > cols) cols = tmp;
				        }
				        
				        AddLog("Reading Row" + i + "...");
				        Thread.sleep(millis);
				    }
				    
				    for (int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        
				        strParams = ""; 
				        strExec = ""; 
				        
				        if(row != null) {
				            for(int c = 0; c < cols; c++) {
				                cell = row.getCell((short)c);
				                 
			                	try {
			                		strCell = cell.toString();	
			                	} catch (NullPointerException ex) {
			                		strCell = "null";
			                	} finally {
			                		
								}
			                	
			                	AddLog("Row: " + r + " || Column: " + c + "\t\t-->\t" + strCell);
			                	
			                	if (c==3) {
			                		strParams = strParams + strCell+ "::numeric(19, 0)::int, ";
			                	} else {
			                		strParams = strParams + "'"+strCell+"', ";
			                	}
				            }
				            
				            AppendProgress(); 
				        }
				        
				        strParams = strParams+"now(), '"+UserInfo.EmployeeCode+"');";  
				        strExec = "insert into mf_unit_other_descriptions (phase, block, lot, lotarea, other_description, date_created, created_by) values (" + strParams;

				        AddLog("insert into mf_unit_other_descriptions (phase, block, lot, lotarea, other_description) values (" + strParams);
				        AddLog("Updating table...");
				        
				        pgUpdate dbExec = new pgUpdate();
				        dbExec.executeUpdate(strExec, false);
					    dbExec.commit();
					    
				        Thread.sleep(millis);
				    }
				    
				    AddLog("table updated...");
				    
				    Detail(); 
				    DeactivatePrevious(); 
				    DeatailPhaseLot(); 
				    DeatailProjectID(); 
				    
				    InitiateProgress(); 
				} catch(Exception ioe) {
					ioe.printStackTrace();
				    JOptionPane.showMessageDialog(null, "Something went wrong. " + ioe.getMessage());
				}
				
				return null;
			}
			
    	}; 
    	
    	sw.execute(); 
    }
}
