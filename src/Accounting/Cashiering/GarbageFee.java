package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXTextField;
import org.jdom.Attribute;
import org.jopendocument.dom.spreadsheet.Column;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.CheckBoxHeader;
import Functions.FncGlobal;
import Functions.FncODSFileFilter;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable.RolloverMouseAdapter;
import Renderer.DateRenderer;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_garbage_fee_for_issuance;

public class GarbageFee extends _JInternalFrame implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6511322593907862696L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	JPanel pnlSelect;
	JPanel pnlDate;
	JPanel pnllblDateTo;
	JPanel pnlDateTo;
	JPanel pnlUploading;
	JPanel pnlFileUploading;

	JButton btnProcess;
	JButton btnPreview;
	JButton btnImport;

	JCheckBox chkPBL;

	JLabel lblDateTo;
	JLabel lblDateFr;
	JLabel lblWithPBL;

	JXTextField tagSelectFile;

	_JDateChooser dateTo;
	_JDateChooser dateFr;

	_JTableMain tblGarbageFee;
	JTabbedPane tabCenter;

	JScrollPane scrollGarbageFee;

	JList rowheaderGarbageFee;

	JFileChooser fileChooser;

	DefaultTableModel modelGarbageFee;
	
	private model_garbage_fee_for_issuance model_garbage_fee_for_issuance;
	JList row_header_garbage_fee_for_issuance;

	_JLookup lookupProj;
	JTextField txtProj;
	
	_JLookup lookupBatch;
	JTextField txtBatch;

	String pbl_id = null;

	protected Boolean pressedShift = false;

	static String title = "Garbage Fee";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTableMain tbl_garbage_fee_for_isuance;

	private JLabel lblForIssuance;

	private JTextField txtForIssuance;

	private JLabel lblForIssuanceCount;

	private JLabel lblForIssuanceDetails;

	private String batch_no;

	private JButton btnViewRemaining;

	private Object count;

	public GarbageFee() {
		super(title, false, true, false, true);
		initGUI();
	}

	public GarbageFee(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public GarbageFee(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			// pnlMain.setPreferredSize(new java.awt.Dimension(798, 85));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setBorder(null);
				pnlNorth.setPreferredSize(new java.awt.Dimension(1000, 80));
				{
					pnlDate = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlDate, BorderLayout.WEST);
					pnlDate.setBorder(JTBorderFactory.createTitleBorder("Transaction Date"));
					pnlDate.setPreferredSize(new java.awt.Dimension(197, 80));
					{
						pnllblDateTo = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlDate.add(pnllblDateTo, BorderLayout.WEST);
						{
							lblDateFr = new JLabel("From:");
							pnllblDateTo.add(lblDateFr);
						}
						{
							lblDateTo = new JLabel("To:");
							pnllblDateTo.add(lblDateTo);
						}
						
						{
							lblForIssuanceCount = new JLabel("0");
							pnllblDateTo.add(lblForIssuanceCount);
							forIssuanceCount();
						}
						
					}
					{
						pnlDateTo = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlDate.add(pnlDateTo, BorderLayout.CENTER);
						{
							dateFr = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlDateTo.add(dateFr);
							dateFr.setDate(FncGlobal.getDateToday());
							dateFr.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor) dateFr.getDateEditor()).setEditable(false);
							

						}
						{
							dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlDateTo.add(dateTo);
							dateTo.setDate(FncGlobal.getDateToday());
							dateTo.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor) dateTo.getDateEditor()).setEditable(false);
							dateTo.addDateListener(new DateListener() {
								
								@Override
								public void datePerformed(DateEvent event) {
									displayGarbageFeeForIssuance(dateFr.getDate(), dateTo.getDate());
								}
							});
						}
						{
							JPanel pnlRemaining = new JPanel(new BorderLayout(5, 5));
							lblForIssuanceDetails = new JLabel(" Remaining");
							pnlRemaining.add(lblForIssuanceDetails, BorderLayout.WEST);
							btnViewRemaining = new JButton("View");
							pnlRemaining.add(btnViewRemaining, BorderLayout.CENTER);
							
							btnViewRemaining.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									String date_fr = dateFr.getDateString();
									String date_to = dateTo.getDateString();
									int confirmation = JOptionPane.showConfirmDialog(null,
											"View Pending " + count +" Garbage ?");
									if (confirmation == 0) {
										System.out.println("Preview");
										Map<String, Object> mapParameters = new HashMap<String, Object>();
//										System.out.println("p_date: "+date);
										FncReport.generateReport("/Reports/rpt_for_issuance_garbage_fee.jasper", "Pending Garbage Fee", "", mapParameters);
									} else {
										System.out.println("Cancel");
									}
								}
							});
							
							pnlDateTo.add(pnlRemaining);
						}
						
					}
					{
						pnlFileUploading = new JPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlFileUploading, BorderLayout.CENTER);
						pnlNorth.setPreferredSize(new Dimension(0, 100));
						pnlFileUploading.setBorder(JTBorderFactory.createTitleBorder("Upload File"));
						pnlFileUploading.setPreferredSize(new java.awt.Dimension(504, 80));
						
						// File Upload Labels
						JPanel pnlLabels = new JPanel();
						{
							pnlLabels = new JPanel(new GridLayout(3, 1, 10, 3));
							pnlFileUploading.add(pnlLabels, BorderLayout.WEST);
							pnlLabels.add(new JLabel("Project ID"));
							pnlLabels.add(new JLabel("Select File"));
							pnlLabels.add(new JLabel("Select Batch"));
						}
						
						// File Upload Center
						{
							pnlUploading = new JPanel(new GridLayout(3, 1, 10, 3));
							pnlFileUploading.add(pnlUploading, BorderLayout.CENTER);
							
							// Project
							{
								JPanel pnlProj = new JPanel(new BorderLayout(5, 5));
								pnlUploading.add(pnlProj);
								
								{
									lookupProj = new _JLookup();
									pnlProj.add(lookupProj, BorderLayout.WEST);
									lookupProj.setPreferredSize(new Dimension(50, 100));
									lookupProj.setReturnColumn(0);
									lookupProj.setLookupSQL(ProjectSQL());
									lookupProj.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												String proj_id = (String) data[0];
												String proj_name = (String) data[1];
												lookupProj.setText(proj_id);
												txtProj.setText(proj_name);
											}

										}
									});
								}
								{
									txtProj = new JTextField();
									pnlProj.add(txtProj, BorderLayout.CENTER);
								}
								{
									tagSelectFile = new JXTextField();
									pnlUploading.add(tagSelectFile);
									tagSelectFile.setEditable(false);
									tagSelectFile.setPreferredSize(new java.awt.Dimension(415, 0));
								}
							}
							
							// Batch
							{
								JPanel pnlBatch = new JPanel(new BorderLayout(5, 5));
								pnlUploading.add(pnlBatch);
								{
									lookupBatch = new _JLookup();
									pnlBatch.add(lookupBatch, BorderLayout.WEST);
									lookupBatch.setPreferredSize(new Dimension(50, 100));
									lookupBatch.setReturnColumn(0);
									lookupBatch.setLookupSQL(BatchSQL());
									lookupBatch.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												String batch_no = (String) data[0];
												String batch_date = "From " + (String) data[1] + " to " + (String) data[2];
												lookupBatch.setText(batch_no);
												txtBatch.setText(batch_date);
											}

										}
									});
								}
								{
									txtBatch = new JTextField();
									pnlBatch.add(txtBatch, BorderLayout.CENTER);
								}
							}
						}
						
						// File Upload Buttons
						{
							pnlSelect = new JPanel(new GridLayout(3, 1, 10, 3));
							pnlFileUploading.add(pnlSelect, BorderLayout.EAST);
							{
								pnlSelect.add(Box.createVerticalBox());

							}
							{
								JButton btnSelectFile = new JButton("Search");
								pnlSelect.add(btnSelectFile);
								btnSelectFile.setActionCommand("Search");
								btnSelectFile.addActionListener(this);
								btnSelectFile.setPreferredSize(new java.awt.Dimension(65, 0));
							}
							{
								JButton btnSelectFile = new JButton("View");
								pnlSelect.add(btnSelectFile);
								btnSelectFile.setActionCommand("previewBatch");
								btnSelectFile.addActionListener(this);
								btnSelectFile.setPreferredSize(new java.awt.Dimension(65, 0));
							}
						}
					}
					{
						btnProcess = new JButton("Process");
						pnlNorth.add(btnProcess, BorderLayout.EAST);
						btnProcess.setActionCommand("Process");
						btnProcess.setMnemonic(KeyEvent.VK_R);
						btnProcess.addActionListener(this);
						btnProcess.setPreferredSize(new java.awt.Dimension(80, 40));
					}
				}
				{
					pnlCenter = new JPanel();
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(5, 5));
					pnlCenter.setBorder(lineBorder);
					{
						tabCenter = new JTabbedPane();
						pnlCenter.add(tabCenter, BorderLayout.CENTER);
						//displayGarbageFeeForIssuance();
						
						{
							JPanel pnlForIssuance = new JPanel(new BorderLayout(3, 3));
							tabCenter.addTab("For Issuance", pnlForIssuance);
							{
								scrollGarbageFee = new JScrollPane();
								pnlForIssuance.add(scrollGarbageFee, BorderLayout.CENTER);
								scrollGarbageFee.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
								scrollGarbageFee.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
								scrollGarbageFee.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								{
									model_garbage_fee_for_issuance = new model_garbage_fee_for_issuance();
									tbl_garbage_fee_for_isuance = new _JTableMain(model_garbage_fee_for_issuance);
									row_header_garbage_fee_for_issuance = tbl_garbage_fee_for_isuance.getRowHeader();
									scrollGarbageFee.setViewportView(tbl_garbage_fee_for_isuance);
									
									
								}
								{
									row_header_garbage_fee_for_issuance = tbl_garbage_fee_for_isuance.getRowHeader();
									row_header_garbage_fee_for_issuance.setModel(new DefaultListModel());
									scrollGarbageFee.setRowHeaderView(row_header_garbage_fee_for_issuance);
									scrollGarbageFee.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						
						
						
						tabCenter.addTab("Garbage Fee", null, new JScrollPane(tblGarbageFee), null);
						System.out.println("Current tab index: "+tabCenter.getSelectedIndex());
						
						tabCenter.addChangeListener(new ChangeListener() {
							
							@Override
							public void stateChanged(ChangeEvent e) {
								System.out.println(tabCenter.getSelectedIndex());
								{
									if(tabCenter.getSelectedIndex() == 0) {
										btnImport.setText("Issue");
										pnlSouth.add(btnImport);
										btnImport.setActionCommand("Issue");
										btnImport.setMnemonic(KeyEvent.VK_I);
									}if(tabCenter.getSelectedIndex() == 1) {
										btnImport.setText("Import");
										pnlSouth.add(btnImport);
										btnImport.setActionCommand("Import");
										btnImport.setMnemonic(KeyEvent.VK_I);
									}
								}
								pnlSouth.repaint();
							}
						});
					}
				}
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					// pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
					{
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setActionCommand("Preview");
						btnPreview.setMnemonic(KeyEvent.VK_V);
						btnPreview.addActionListener(this);
					}
					{
						btnImport = new JButton("Issue");
						pnlSouth.add(btnImport);
						btnImport.setActionCommand("Issue");
						btnImport.setMnemonic(KeyEvent.VK_I);
						btnImport.addActionListener(this);
					}
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("Search")) {
			if (fileChooser == null) {
				fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(new FncODSFileFilter(new String[] { "ods", "xlsx" },
						"Spreadsheets (*.ods, *.odc, *.ots, *.xlsx)"));

				Action details = fileChooser.getActionMap().get("viewTypeDetails");
				details.actionPerformed(null);
			}

			fileChooser.setSelectedFile(new File(""));
			int status = fileChooser.showOpenDialog(this.getTopLevelAncestor());
			if (status == JFileChooser.APPROVE_OPTION) {
				if (fileChooser.getSelectedFile().equals(null)) {
					JOptionPane.showMessageDialog(getParent(), "No Selected Document");
					return;
				}

				System.out.printf("Selected File: %s%n", fileChooser.getSelectedFile().toString());
				String selectedFile = fileChooser.getSelectedFile().toString();
				tagSelectFile.setText(selectedFile);
			}
		}
		if (actionCommand.equals("Process")) {
			String selectedFile = fileChooser.getSelectedFile().toString();
			File fileSelected = new File(selectedFile);
			System.out.printf("Display selected file: %s%n", selectedFile);
			
			tabCenter.remove(1);

			try {
				int sheetCount = SpreadSheet.createFromFile(fileSelected).getSheetCount();
				System.out.printf("Display Sheet Count: %s%n", sheetCount);
				for (int x = 0; x < sheetCount; x++) {
					System.out.printf("Display loop value: %s%n", x);

					Sheet sheet = SpreadSheet.createFromFile(fileSelected).getSheet(x);

					String sheetName = sheet.getName().toString();
					System.out.printf("Sheet Name: %s%n", sheetName);

					streamSheet(sheet);

					System.out.println("Returned Rows: " + tblGarbageFee.getRowCount());
					tabCenter.addTab("Garbage Fee", null, new JScrollPane(tblGarbageFee), null);
					tabCenter.setSelectedIndex(1);

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (actionCommand.equals("Import")) {
			batch_no = null;
			int entity_column = 0;
			int amount_column = 0;
			int phase_column = 0;
			int block_column = 0;
			int lot_column = 0;
			int ordate_column = 0;
			int balance_group_column = 0;
			for (int x = 0; x < modelGarbageFee.getColumnCount(); x++) {
				if(modelGarbageFee.getColumnName(x).equals("Payee") || modelGarbageFee.getColumnName(x).equals("PAYEE")) {
					entity_column = x;
				}
				if(modelGarbageFee.getColumnName(x).equals("Amount") || modelGarbageFee.getColumnName(x).equals("AMOUNT") || modelGarbageFee.getColumnName(x).equals("TOTAL AMOUNT") || modelGarbageFee.getColumnName(x).equals("Total Amount")) {
					amount_column = x;
				}
				if(modelGarbageFee.getColumnName(x).equals("Phase") || modelGarbageFee.getColumnName(x).equals("PHASE")) {
					phase_column = x;
				}
				if(modelGarbageFee.getColumnName(x).equals("Block") || modelGarbageFee.getColumnName(x).equals("BLOCK")) {
					block_column = x;
				}
				if(modelGarbageFee.getColumnName(x).equals("Lot") || modelGarbageFee.getColumnName(x).equals("LOT")) {
					lot_column = x;
				}
				if(modelGarbageFee.getColumnName(x).equals("OR Date") || modelGarbageFee.getColumnName(x).equals("OR DATE")) {
					ordate_column = x;
				}
				if(modelGarbageFee.getColumnName(x).equals("Balance Group") || modelGarbageFee.getColumnName(x).equals("BALANCE GROUP")) {
					balance_group_column = x;
				}
			}
			
			int confirmation = JOptionPane.showConfirmDialog(null,
					"Import " + modelGarbageFee.getRowCount() + " rows?");
			if (confirmation == 0) {
				FncGlobal.startProgress("Importing from spreadsheet...");
				/*
				 * commented by jari cruz asof oct 26 2022
				 * deleteTmpGarbageFee();*/
				// delete tmp_garbage_fee rows
				// System.out.println("Import");
				System.out.printf("value of block column: %s%n", block_column);
				
				Date period_from = dateFr.getDate();
				Date period_to = dateTo.getDate();
				
				for (int x = 0; x < modelGarbageFee.getRowCount(); x++) {
					String entity_name = (String) modelGarbageFee.getValueAt(x, entity_column);
					String phase = String.valueOf(modelGarbageFee.getValueAt(x, phase_column));
					String block = String.valueOf(modelGarbageFee.getValueAt(x, block_column));
					String lot = String.valueOf(modelGarbageFee.getValueAt(x, lot_column));
					BigDecimal amount = (BigDecimal) modelGarbageFee.getValueAt(x, amount_column);
					String or_date = (String) modelGarbageFee.getValueAt(x, ordate_column);
					String balance_group = (String) modelGarbageFee.getValueAt(x, balance_group_column);
					String proj_id = lookupProj.getValue();
					System.out.println("Row "+x+"\nPayee: "+entity_name+"\nPhase: "+phase+"\nBlock: "+block+"\nLot: "+lot+"\nAmount: "+amount+"\nOR Date: "+or_date+"\nProj ID: "+proj_id+"\n");
					
					String SQL = "";
					
					if(balance_group.equals("Adjusment")) {
						SQL = "select sp_upload_garbage_adjustments('"+entity_name+"', '"+phase+"', '"+block+"', '"+lot+"', '"+proj_id+"', '"+amount+"', '"+or_date+"', '"+UserInfo.EmployeeCode+"', '"+period_from+"'::dATE, '"+period_to+"'::DATE)";
					}else {
						SQL = "select sp_garbage_fee_uploader_v2('"+entity_name+"','"+phase+"','"+block+"','"+lot+"','"+proj_id+"','"+amount+"','"+or_date+"','"+UserInfo.EmployeeCode+"','"+batch_no+"', '"+period_from+"'::DATE, '"+period_to+"')";
					}
					
					pgSelect db = new pgSelect();
					db.select(SQL);
					
					FncSystem.out("Display value of SQL", SQL);
					
					batch_no = (String) db.getResult()[0][0];
				}
				System.out.println("Tapos na " + modelGarbageFee.getRowCount());
				FncGlobal.stopProgress();
				
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Upload Successful", "Import", JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("Cancel");
			}
		}
		if (actionCommand.equals("Issue")) {
			int count = 0;
			for (int x = 0; x < tbl_garbage_fee_for_isuance.getRowCount(); x++) {
				boolean tag = (boolean) tbl_garbage_fee_for_isuance.getValueAt(x, 0);
				if(tag) {
					count += 1;
				}
			}
			int confirmation = JOptionPane.showConfirmDialog(null,
					"Issue Payments for " + count + " rows?");
			if (confirmation == 0) {
				
				String batch_no = sqlBatchNo();
				
				FncGlobal.startProgress("Issuing payments...");
				System.out.println("Issue");
				for (int x = 0; x < tbl_garbage_fee_for_isuance.getRowCount(); x++) {
					boolean tag = (boolean) tbl_garbage_fee_for_isuance.getValueAt(x, 0);
					if(tag) {
						String client_seqno = (String) tbl_garbage_fee_for_isuance.getValueAt(x, 1);
						System.out.println(client_seqno);
						String SQL = "SELECT sp_ir_post_ordered('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
						issuedGarbageFee(x, batch_no);
						pgSelect db = new pgSelect();
						db.select(SQL);
					}
				}
				
				pgSelect db = new pgSelect();
				String SQL = "SELECT sp_garbage_fee_jv_creation('"+batch_no+"', '"+UserInfo.EmployeeCode+"')";
				db.select(SQL);
				
				FncGlobal.stopProgress();
				//displayGarbageFeeForIssuance();
				forIssuanceCount();
			} else {
				System.out.println("Cancel");
			}
		}

		if (actionCommand.equals("Preview")) {
			String date_fr = dateFr.getDateString();
			String date_to = dateTo.getDateString();
			int confirmation = JOptionPane.showConfirmDialog(null,
					"View Issued Payments for " + date_fr + " to "+ ""+date_to+"?");
			if (confirmation == 0) {
				System.out.println("Preview");
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("p_date_fr", date_fr);
				mapParameters.put("p_date_to", date_to);
//				System.out.println("p_date: "+date);
				FncReport.generateReport("/Reports/rpt_issued_garbage_fee.jasper", "Garbage Fee", "", mapParameters);
			} else {
				System.out.println("Cancel");
			}
		}
		
		if (actionCommand.equals("previewBatch")) {
			if (lookupBatch.getValue() != null) {
				int confirmation = JOptionPane.showConfirmDialog(null,
						"View Issued Payments for batch " + lookupBatch.getValue() + " ?");
				if (confirmation == 0) {
					System.out.println("previewBatch");
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("p_batch_no", lookupBatch.getValue());
					FncReport.generateReport("/Reports/rpt_issued_garbage_fee_batch.jasper", "Garbage Fee Batch "+lookupBatch.getValue()+"", "", mapParameters);
				} else {
					System.out.println("Cancel");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "You haven't selected a batch yet !");
			}
		}

	}

	protected JTable streamSheet(Sheet sheet) {
		int colCount = sheet.getColumnCount();
		int rowCount = sheet.getRowCount();

		ArrayList<Integer> deleteColumn = new ArrayList<Integer>();
		ArrayList<Integer> deleteRow = new ArrayList<Integer>();

		Object[] columnNames = null;
		Object[][] rowValue = null;

		rowValue = new Object[rowCount][colCount + 1];

		int colnameRow = 0;
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				if (sheet.getValueAt(c, r).toString().trim().toUpperCase().equals("OR NO")) {
					colnameRow = r;
					System.out.println("Dumaan dito sa 1");
				}
			}
		}
		ArrayList<Integer> listHiddenColumns = new ArrayList<Integer>();
		for (int x = 0; x < colCount; x++) {
			Column<SpreadSheet> col = sheet.getColumn(x);

			for (Object obj : col.getElement().getAttributes()) {
				Attribute att = (Attribute) obj;
			}
		}

		// TO DELETE EMPTY COLUMNS
		deleteColumn.clear();
		for (int c = 0; c < colCount; c++) {
			if (sheet.getValueAt(c, colnameRow).toString().equals("") || sheet.getValueAt(c, colnameRow) == null
					|| sheet.getValueAt(c, colnameRow).toString().isEmpty()) {
				deleteColumn.add((c + 1));
			} else
				deleteColumn.clear();
		}
		colCount = (deleteColumn.get(0) - 1);
		columnNames = new Object[colCount + 1];

		for (int c = 0; c < colCount; c++) {

			columnNames[c + 1] = sheet.getValueAt(c, colnameRow).toString().trim();
			
		}
		for (int r = (colnameRow + 1); r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				rowValue[0][0] = false;
				rowValue[r][0] = true;
				// filter row that have no OR NO
				if (!sheet.getValueAt(0, r).equals("")) {
					rowValue[r][c + 1] = sheet.getValueAt(c, r);
				}
			}
		}

		modelGarbageFee = new DefaultTableModel(rowValue, columnNames) {
			private static final long serialVersionUID = 3174178548239382080L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				Boolean to = null;
				if (columnIndex == 0)
					to = false;
				else
					to = false;
				return to;

				/*
				 * Boolean to = null; if(columnIndex==0) to = true; else to = false; return to;
				 */
			}
		};

		tblGarbageFee = new _JTableMain(modelGarbageFee);
		tblGarbageFee.getColumnModel().getColumn(0).setCellEditor(tblGarbageFee.getDefaultEditor(Boolean.class));
		tblGarbageFee.getColumnModel().getColumn(0).setCellRenderer(tblGarbageFee.getDefaultRenderer(Boolean.class));
		tblGarbageFee.getColumnModel().getColumn(0).setWidth(35);
		tblGarbageFee.getColumnModel().getColumn(0).setMinWidth(35);
		tblGarbageFee.getColumnModel().getColumn(0).setMaxWidth(35);

		tblGarbageFee.setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
		tblGarbageFee.setDefaultRenderer(Time.class, DateRenderer.getTimeRenderer());
		tblGarbageFee.setDefaultRenderer(Timestamp.class, DateRenderer.getDateRenderer());
		tblGarbageFee.setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		tblGarbageFee.setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));
		tblGarbageFee.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
		tblGarbageFee.setDefaultRenderer(_JLookup.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));

		RolloverMouseAdapter rolloverAdapter = new RolloverMouseAdapter(tblGarbageFee);

		CheckBoxHeader.RolloverAdapter ma = new CheckBoxHeader.RolloverAdapter(tblGarbageFee);
		tblGarbageFee.getTableHeader().addMouseListener(ma);
		tblGarbageFee.getTableHeader().addMouseMotionListener(ma);

		tblGarbageFee.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(tblGarbageFee, ma));
		tblGarbageFee.getColumnModel().getColumn(0).setMaxWidth(35);
		tblGarbageFee.getColumnModel().getColumn(0).setMinWidth(35);
		tblGarbageFee.getColumnModel().getColumn(0).setPreferredWidth(35);

		tblGarbageFee.addMouseListener(rolloverAdapter);
		tblGarbageFee.addMouseMotionListener(rolloverAdapter);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblGarbageFee.getModel());
		tblGarbageFee.setRowSorter(sorter);
		sorter.setSortable(0, false);

		for (int x = (modelGarbageFee.getRowCount() - 1); x >= 0; x--) {
			Object value = modelGarbageFee.getValueAt(x, 1);
			if (value == null) {
				modelGarbageFee.removeRow(x);
			}
		}

		// Remove hidden columns
		for (int x = tblGarbageFee.getRowCount() - 1; x >= 0; x--) {
			if (listHiddenColumns.contains(x)) {
				tblGarbageFee.removeColumn(tblGarbageFee.getColumn(x + 1));
			}
		}

		for (int x = 0; x < tblGarbageFee.getColumnCount(); x++) {

		}

		tblGarbageFee.packAll();

		return tblGarbageFee;

	}

	private static String ProjectSQL() {
		return "SELECT proj_id, proj_name, proj_alias FROM mf_project\n" + "ORDER BY proj_name";
	}
	
	private static String BatchSQL() {
		return "SELECT * FROM(\n"
				+ "select batch_no as \"Batch\",\n"
				+ "MIN(trim('OR DATE' from remarks)::date)::VARCHAR as \"From Date\"\n"
				+ ",MAX(trim('OR DATE' from remarks)::date)::VARCHAR as \"To Date\"\n"
				+ ",to_char(MIN(trim('OR DATE' from remarks)::date), 'Month') as \"From Month\"\n"
				+ ",to_char(MAX(trim('OR DATE' from remarks)::date), 'Month') as \"To Month\"\n"
				+ "from for_issuance_garbage_fee\n"
				+ "where status_id = 'A' \n"
				+ "and batch_no is not null\n"
				+ "and period_from is null\n"
				+ "group by batch_no\n"
				+ "UNION\n"
				+ "select batch_no as \"Batch\", \n"
				+ "period_from::DATE::VARCHAR as \"From Date\",\n"
				+ "period_to::DATE::VARCHAR  as \"To Date\",\n"
				+ "to_char(period_from::dATE, 'Month')  as \"From Month\",\n"
				+ "to_char(period_to::dATE, 'Month') as \"To Month\"\n"
				+ "from for_issuance_garbage_fee\n"
				+ "where status_id = 'A' \n"
				+ "and batch_no is not null\n"
				+ "and period_from is not null\n"
				+ "and period_to is not null\n"
				+ "group by batch_no, period_from, period_to) a\n"
				+ "order by a.\"Batch\"";
	}

	private void deleteTmpGarbageFee() {
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_garbage_fee where created_by = '"+UserInfo.EmployeeCode+"'", true);
		dbExec.commit();
	}
	
	private void displayGarbageFeeForIssuance(Date period_from, Date period_to) {
		
//		tbl_garbage_fee_for_isuance = new _JTableMain(model_garbage_fee_for_issuance);
//		row_header_garbage_fee_for_issuance = tbl_garbage_fee_for_isuance.getRowHeader();
//		scrollGarbageFee.setViewportView(tbl_garbage_fee_for_isuance);
		if(model_garbage_fee_for_issuance.getRowCount() > 1) {
			FncTables.clearTable(model_garbage_fee_for_issuance);
		}
		
		DefaultListModel listModel = new DefaultListModel();
//		Date period_from = dateFr.getDate();
//		Date period_to = dateTo.getDate();
		
		pgSelect db = new pgSelect();
		String SQL = "select true as tag, * from view_garbage_fee_for_issuance_v2('"+UserInfo.EmployeeCode+"', '"+period_from+"'::DATE, '"+period_to+"'::DATE)";
		db.select(SQL);
		FncSystem.out("Display SQL for issuance", SQL);
		
		if(db.isNotNull()) {
//			for(Object[] forIssuance : db.getResult()) {
//				model_garbage_fee_for_issuance.addRow(forIssuance);
//				listModel.addElement(model_garbage_fee_for_issuance.getRowCount());
//			}
			
			for(int x=0; x<db.getRowCount(); x++) {
				model_garbage_fee_for_issuance.addRow(db.getResult()[x]);
				listModel.addElement(model_garbage_fee_for_issuance.getRowCount());
			}
		}
		row_header_garbage_fee_for_issuance.setModel(listModel);
		tbl_garbage_fee_for_isuance.packAll();
	}
	
	private void issuedGarbageFee(int row, String batch_no) {
		String client_seqno = (String) tbl_garbage_fee_for_isuance.getValueAt(row, 1);
		String SQL = "select sp_issued_garbage_fee_v2('"+client_seqno+"', '"+batch_no+"' ,'"+UserInfo.EmployeeCode+"');";
		pgSelect db = new pgSelect();
		db.select(SQL);
	}
	
	private String sqlBatchNo() {
		String batch_no = "";
		
		pgSelect db = new pgSelect();
		String SQL = "(select to_char((SELECT COALESCE(max(batch_no)::int,0) + 1 from for_issuance_garbage_fee),'FM000000'))";
		db.select(SQL);
		
		batch_no = (String) db.getResult()[0][0];
	
		return batch_no;
	}
	
	private void forIssuanceCount() {
		String SQL = "SELECT count(*)::varchar \n"
				+ "FROM rf_pay_header a \n"
				+ "left join rf_pay_detail b on a.client_seqno = b.client_seqno and a.entity_id = b.entity_id\n"
				+ "WHERE a.op_status = 'O'\n"
				+ "and a.status_id != 'I'\n"
				+ "AND a.branch_id = '01'\n"
				+ "and b.part_type = '268'\n"
				+ "and exists(select trim(client_seqno) as client_seqno from for_issuance_garbage_fee where status_id = 'A' and trim(client_seqno) = trim(a.client_seqno))";
		pgSelect db = new pgSelect();
		db.select(SQL);
		count = db.getResult()[0][0];
		lblForIssuanceCount.setText((String) count);
	}
}
