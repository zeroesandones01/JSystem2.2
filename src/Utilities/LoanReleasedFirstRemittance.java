package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class LoanReleasedFirstRemittance extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = 4161008909537545821L;
	static String title = "HDMF Schedule Creation";
	Dimension frame = new Dimension(600, 400); 
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private static JTextArea txtLog;
	private JScrollPane scrLog;
	private static JTextField txtDir;
	private JProgressBar pBar; 
	
	private static JButton btnOpen;
	private static JButton btnPrime;
	private static JButton btnDetail;
	private static JButton btnExecute; 
	
	private static JRadioButton radSched = new JRadioButton("");
    /*
    birdButton.setMnemonic(KeyEvent.VK_B);
    birdButton.setActionCommand(birdString);
    birdButton.setSelected(true);
    */

	private static JRadioButton radArrear = new JRadioButton("");
    /*
    catButton.setMnemonic(KeyEvent.VK_C);
    catButton.setActionCommand(catString);
    */
	
	public LoanReleasedFirstRemittance() {
		super(title, false, true, false, true);
		initGUI();
	}

	public LoanReleasedFirstRemittance(String title) {
		super(title);
		initGUI();
	}

	public LoanReleasedFirstRemittance(String title, boolean resizable, boolean closable, boolean maximizable,
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
								JXPanel panDir = new JXPanel(new BorderLayout(3, 3));
								panLog.add(panDir, BorderLayout.PAGE_START); 
								panDir.setBorder(JTBorderFactory.createTitleBorder("Directory", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								panDir.setPreferredSize(new Dimension(0, 60));
								{
									{
										btnOpen = new JButton("Open"); 
										panDir.add(btnOpen, BorderLayout.LINE_START); 
										btnOpen.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												txtDir.setText(FncGlobal.OpenDir("File"));
												InitiateProgress(); 
											}
										});
										btnOpen.setPreferredSize(new Dimension(80, 0));
									}
									{
										txtDir = new JTextField("/home/PC-112l/Desktop/Work Files/Think Tank (HDMF First MA)/2017 Dec/CENQHOMES-uploading of MA for Dec 2017[main].xls"); 
										panDir.add(txtDir, BorderLayout.CENTER);
										txtDir.setHorizontalAlignment(JTextField.LEADING);
										txtDir.setEditable(false);
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
							panProgress.add(pBar, BorderLayout.CENTER); 
							pBar.setStringPainted(true);
						}
						InitiateProgress();
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(3, 3)); 
				panMain.add(panCenter, BorderLayout.PAGE_END);
				panCenter.setPreferredSize(new Dimension(0, 30));
				{
					{
						{
							radSched = new JRadioButton("Schedule");
							radSched.setMnemonic(KeyEvent.VK_1);
						}
						{
							radArrear = new JRadioButton("Arrear");
							radArrear.setMnemonic(KeyEvent.VK_2);
							radSched.setSelected(true);
						}
						{
							ButtonGroup group = new ButtonGroup();
					        group.add(radSched);
					        group.add(radArrear);
						}
						{
							JXPanel panRad = new JXPanel(new GridLayout(1, 2, 5, 5)); 
							panCenter.add(panRad, BorderLayout.CENTER); 
							panRad.setPreferredSize(new Dimension(0, 30));
							{
								panRad.add(radSched);
								panRad.add(radArrear);
							}
						}
					}
					{
						JXPanel panButtons = new JXPanel(new GridLayout(1, 3, 5, 5)); 
						panCenter.add(panButtons, BorderLayout.LINE_END); 
						panButtons.setPreferredSize(new Dimension(390, 0));
						{
							{
								btnPrime = new JButton("Prime"); 
								panButtons.add(btnPrime);
								btnPrime.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										txtLog.setText("");
										ReadExcel(); 
									}
								});
							}
							{
								btnDetail = new JButton("Detail"); 
								panButtons.add(btnDetail);
								btnDetail.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										ReadUpdateIDs(); 
									}
								});
							}
							{
								btnExecute = new JButton("Execute"); 
								panButtons.add(btnExecute);
								btnExecute.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										CreateIndividualSchedule(); 
									}
								});
							}
						}
					}
				}
			}
		}
	}
	
	private void AppendProgress() {
		if (pBar.getValue()<pBar.getMaximum()) {
			pBar.setValue(pBar.getValue() + 1);
		} else {
			InitiateProgress(); 
		}
	}
	
	private void InitiateProgress() {
		pBar.setValue(0);
	}
	
	private class ReadExcel2 extends Thread {
		
		@SuppressWarnings("deprecation")
		public void run () {

			String strCell = "";
			String strExec = "";
			String strParams = "";
			String strDir = txtDir.getText();
			pgUpdate dbExec = new pgUpdate(); 
			
			dbExec.executeUpdate("DELETE FROM rf_hdmf_first_ma_arrears WHERE status_id = 'D'", false);
			dbExec.commit();
			
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
			            //if(tmp > cols) cols = tmp;
			        }
			    }

			    cols = 10;
			    
			    for(int r = 1; r < rows; r++) {
			        row = sheet.getRow(r);
			        
			        strParams = ""; 
			        
			        if(row != null) {
			            for(int c = 0; c < cols; c++) {
			                cell = row.getCell((short)c);
			                 
		                	try {
		                		strCell = cell.toString();	
		                	} catch (NullPointerException ex) {
		                		strCell = "null";
		                	} finally {
		                		
							}
		                	
		                	txtLog.setText(txtLog.getText() + "Row: " + r + " || Column: " + c + "\t\t-->\t" + strCell + "\n\n");
		                	
		                	if (c==1 || c==2 || c==3 || c==4 || c==5 || c==6 || c==7) {
		                		strParams = strParams + strCell + "::numeric(19, 2), ";
		                	} else if (c==9) {
		                		strParams = strParams + strCell+ "::numeric(19, 0)::int, ";
		                	} else if (c==8) {
		                		strParams = strParams + "'"+strCell+"'::timestamp, ";
		                	} else {
		                		strParams = strParams + "'"+strCell+"', ";
		                	}
			            }
			            
			            AppendProgress(); 
			        }
			        
			        strParams = strParams + "'A', now(), (coalesce((select max(arrears_rec_id) from rf_hdmf_first_ma_arrears), 0) + 1));";  
			        strExec = "insert into rf_hdmf_first_ma_arrears (hlid_no, underprin, underint, undermri, underfire, undermaf, underhfc, underhcf, datebeg, age, status_id, date_updated, arrears_rec_id) \n" +
			        "values (" + strParams;

			        txtLog.setText(txtLog.getText() + strExec + "\n\n"); 
			        txtLog.setText(txtLog.getText() + "Updating table..." + "\n\n");
			        
			        dbExec = new pgUpdate(); 
			        dbExec.executeUpdate(strExec, false);
			        dbExec.commit();
			        
			        txtLog.setText(txtLog.getText() + "table updated..." + "\n\n");
			    }
			    JOptionPane.showMessageDialog(getContentPane(), "Arrears updated!");
			} catch(Exception ioe) {
			    JOptionPane.showMessageDialog(getContentPane(), "Something went wrong. " + ioe.getMessage());
			}
		}	
	}
	
    private static void ReadExcel() {
    	
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws Exception {
				String strCell = "";
				String strExec = "";
				String strParams = "";
				String strDir = txtDir.getText();
				pgUpdate dbExec = new pgUpdate(); 
				
				ControlMode(false); 
				
				dbExec.executeUpdate("DELETE FROM rf_hdmf_first_ma_remittance WHERE status_id = 'D'", false);
				dbExec.commit();
				
				try {
				    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(strDir));
				    HSSFWorkbook wb = new HSSFWorkbook(fs);
				    HSSFSheet sheet = wb.getSheetAt(0);
				    HSSFRow row;
				    HSSFCell cell;

				    int rows; 
				    rows = sheet.getPhysicalNumberOfRows();

				    int cols = 0; 
				    int tmp = 0;

				    for(int i = 0; i < 10 || i < rows; i++) {
				        row = sheet.getRow(i);
				        if(row != null) {
				            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				            if(tmp > cols) cols = tmp;
				        }
				    }

				    for(int r = 1; r < rows; r++) {
				        row = sheet.getRow(r);
				        
				        strParams = ""; 
				        
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
			                	
			                	if (c==4 || c==6 || c==8 || c==9 || c==10 || c==11 || c==12) {
			                		strParams = strParams + (strCell.equals("") || strCell==null?"0":strCell) + "::numeric(19, 2), ";
			                	} else if (c==5) {
			                		strParams = strParams + strCell+ "::numeric(19, 0)::int, ";
			                	} else if (c==7) {
			                		strParams = strParams + "'"+strCell+"'::timestamp, ";
			                	} else {
			                		strParams = strParams + "'"+strCell+"', ";
			                	}
				            }
				        }
				        
				        strParams = strParams + "now(), '"+strDir+"', (select coalesce(max(hdmf_uploading_id), 0) + 1 from rf_hdmf_first_ma_remittance)::int, 'D');";  
				        strExec = "INSERT INTO rf_hdmf_first_ma_remittance (hlid_no, last_name, first_name, middle_name, loanable_amount, term, int_rate, date_start, ma, mri, fire, hdmf_contribution, total_ma, date_uploaded, filename, hdmf_uploading_id, status_id) values (" + strParams;

				        AddLog(strExec);
				        AddLog("Updating table...");
				        
				        dbExec = new pgUpdate(); 
				        dbExec.executeUpdate(strExec, false);
				        dbExec.commit();
				        
				        
				        AddLog("table updated...");
				    }
				} catch(Exception ioe) {
				    JOptionPane.showMessageDialog(null, "Something went wrong. " + ioe.getMessage());
				}
				
				ControlMode(true);
				return null;
			}
			
    	}; 
    	
    	sw.execute(); 
    }
    
    private static void ReadUpdateIDs() {
    	
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws Exception {
		        AddLog("table updated...");
		        AddLog("updating IDs...");
		        
		        ControlMode(false);
		        
		        txtLog.setText(txtLog.getText() + "select count(*)::int from sp_loan_released_update_remittance_table('"+txtDir.getText()+"')" + "\n\n");
		        Integer intCount = FncGlobal.GetInteger("select count(*)::int from sp_loan_released_update_remittance_table('"+txtDir.getText()+"')");
		        
		        if (intCount > 0) {
		        	AddLog("some IDs were not updated..."); 
		        } else {
		        	AddLog("IDs are all updated...");
		        }
				
		        ControlMode(true);
		        
				return null;
			}
    	};
    	
    	sw.execute(); 
    }
    
    private static void CreateIndividualSchedule() {
    	
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws Exception {
		        String strExec = "";
		        pgSelect sqlRow = new pgSelect();
		        pgSelect sqlExec = new pgSelect();
				
		        ControlMode(false); 
		        
				AddLog("Creating Schedule...");
				
				sqlRow = new pgSelect();
				
				txtLog.setText(txtLog.getText() + "select 'select sp_create_pagibig_schedule_remittance (' ||\n" + 
						"quote_nullable(entity_id) || ', ' || \n" + 
						"quote_nullable(proj_id) || ', ' || \n" + 
						"quote_nullable(pbl_id) || ', ' || \n" + 
						"quote_nullable(seq_no) || ', ' || \n" + 
						"quote_nullable(loanable_amount) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(term) || '::numeric(19, 0)::int, ' || \n" + 
						"quote_nullable(int_rate) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(date_start) || '::timestamp, ' || \n" + 
						"quote_nullable(ma) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(mri) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(fire) || '::numeric(19, 2), ' || \n" + 
						"'coalesce(' || quote_nullable(hdmf_contribution) || '::numeric(19, 2), 0)::numeric(19, 2), ' || \n" + 
						"quote_nullable(total_ma) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(hlid_no) || ', ' || \n" + 
						"quote_nullable('900748') || ')' \n" + 
						"from rf_hdmf_first_ma_remittance \n" +
						"where status_id = 'A' and filename = '"+txtDir.getText()+"'; ");
				
				sqlRow.select("select 'select sp_create_pagibig_schedule_remittance (' ||\n" + 
						"quote_nullable(entity_id) || ', ' || \n" + 
						"quote_nullable(proj_id) || ', ' || \n" + 
						"quote_nullable(pbl_id) || ', ' || \n" + 
						"quote_nullable(seq_no) || ', ' || \n" + 
						"quote_nullable(loanable_amount) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(term) || '::numeric(19, 0)::int, ' || \n" + 
						"quote_nullable(int_rate) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(date_start) || '::timestamp, ' || \n" + 
						"quote_nullable(ma) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(mri) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(fire) || '::numeric(19, 2), ' || \n" + 
						"'coalesce(' || quote_nullable(hdmf_contribution) || '::numeric(19, 2), 0)::numeric(19, 2), ' || \n" + 
						"quote_nullable(total_ma) || '::numeric(19, 2), ' || \n" + 
						"quote_nullable(hlid_no) || ', ' || \n" + 
						"quote_nullable('900748') || ')' \n" + 
						"from rf_hdmf_first_ma_remittance \n" +
						"where status_id = 'A' and filename = '"+txtDir.getText()+"'; ");
				
				if (sqlRow.isNotNull()) {
					for (int x = 0; x < sqlRow.getRowCount(); x++) {
						sqlExec = new pgSelect();
						strExec = (String) sqlRow.getResult()[x][0];
						
						AddLog(strExec);
						AddLog(x+"/"+sqlRow.getRowCount()+" rows processed...");
						
						sqlExec.select(strExec);
						
						AddLog("Schedule created...");
					}
				}
				
				AddLog("All schedules created...");
				
				ControlMode(true); 
				
				return null;
			}
    	};
    	
    	sw.execute(); 
    }
    
	private static void AddLog(String strLog) {
		if (txtLog.getDocument().getLength()+strLog.length()<=Integer.MAX_VALUE) {
			txtLog.append(strLog+"\n\n");
			txtLog.setCaretPosition(txtLog.getDocument().getLength());	
		} else {
			txtLog.setText("");
		}
	}
	
	private static void ControlMode(Boolean blnRev) {
		btnOpen.setEnabled(blnRev);
		btnPrime.setEnabled(blnRev);
		btnDetail.setEnabled(blnRev);
		btnExecute.setEnabled(blnRev);
		
		radSched.setEnabled(blnRev);
		radArrear.setEnabled(blnRev);
	}
}
