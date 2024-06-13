package Utilities;

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

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

import org.jdom.Attribute;
import org.jopendocument.dom.spreadsheet.Column;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.CheckBoxHeader;
import Functions.FncGlobal;
import Functions.FncODSFileFilter;
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
import components._JXTextField;
import interfaces._GUI;

public class UploadHappyWellCollection extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 503302989941946756L;
	static String title = "Upload Happy Well Collection";
	Dimension frameSize = new Dimension(700, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dateTo;
	private _JDateChooser dateFr;
	private _JLookup lookupProject;
	private _JXTextField txtProjName;
	private _JXTextField txtFileName;

	private JButton btnProcess;
	private JButton btnSelectFile;
	private JButton btnUpload;
	private JButton btnCancel;
	private JFileChooser fileChooser;
	private _JTableMain tblWaterPmts;
	private JTabbedPane tabCenter;

	private JScrollPane scrollWaterPayments;

	private JList rowheaderWaterPayments;
	private DefaultTableModel modelHappyWellWaterUpload;
	

	public UploadHappyWellCollection() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadHappyWellCollection(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadHappyWellCollection(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			JPanel pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 100));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("File Upload Details"));
				{
					JPanel pnlNorthCenter = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						JPanel pnlNorthCenterLabel = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNorthCenterLabel, BorderLayout.WEST);
						pnlNorthCenterLabel.setPreferredSize(new Dimension(100, 0));
						{
							JLabel lblDateFrom = new JLabel("Date From");
							pnlNorthCenterLabel.add(lblDateFrom);
						}
						{
							JLabel lblProject = new JLabel("Project");
							pnlNorthCenterLabel.add(lblProject);
						}
						{
							JLabel lblSelectFile = new JLabel("Select File");
							pnlNorthCenterLabel.add(lblSelectFile);
						}
					}
					{
						JPanel pnlNorthComponents = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNorthComponents, BorderLayout.CENTER);
						{
							JPanel pnlDateCoverage = new JPanel(new BorderLayout(3, 3));
							pnlNorthComponents.add(pnlDateCoverage);
							{
								dateFr = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDateCoverage.add(dateFr, BorderLayout.WEST);
								dateFr.setPreferredSize(new Dimension(150, 0));
							}

							{
								JLabel lblDateTo = new JLabel("Date to", JLabel.TRAILING);
								pnlDateCoverage.add(lblDateTo);
							}
							{
								dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDateCoverage.add(dateTo, BorderLayout.EAST);
								dateTo.setPreferredSize(new Dimension(150, 0));
							}

						}
						{
							JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
							pnlNorthComponents.add(pnlProject);

							{
								lookupProject = new _JLookup();
								pnlProject.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(50, 100));
								lookupProject.setReturnColumn(0);
								lookupProject.setLookupSQL(sqlProject());
								lookupProject.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];
											lookupProject.setValue(proj_id);;
											txtProjName.setText(proj_name);
										}

									}
								});
							}
							{
								txtProjName = new _JXTextField();
								pnlProject.add(txtProjName, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlSearchFile = new JPanel(new BorderLayout(3, 3));
							pnlNorthComponents.add(pnlSearchFile);
							{
								txtFileName = new _JXTextField();
								pnlSearchFile.add(txtFileName, BorderLayout.CENTER);
							}
							{
								btnSelectFile = new JButton("Browse");
								pnlSearchFile.add(btnSelectFile, BorderLayout.EAST);
								btnSelectFile.setPreferredSize(new Dimension(100, 0));
								btnSelectFile.setActionCommand("Browse");
								btnSelectFile.addActionListener(this);
							}
						}
					}
				}
				{
					btnProcess = new JButton("Process");
					pnlNorth.add(btnProcess, BorderLayout.EAST);
					btnProcess.setPreferredSize(new Dimension(200, 0));
					btnProcess.setActionCommand("Process");
					btnProcess.addActionListener(this);
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					tabCenter = new JTabbedPane();
					pnlCenter.add(tabCenter, BorderLayout.CENTER);
					//displayGarbageFeeForIssuance();
					
					tabCenter.addTab("Garbage Fee", null, new JScrollPane(tblWaterPmts), null);
					System.out.println("Current tab index: "+tabCenter.getSelectedIndex());
					
//					tabCenter.addChangeListener(new ChangeListener() {
//						
//						@Override
//						public void stateChanged(ChangeEvent e) {
//							System.out.println(tabCenter.getSelectedIndex());
//							{
//								if(tabCenter.getSelectedIndex() == 0) {
//									btnImport.setText("Issue");
//									pnlSouth.add(btnImport);
//									btnImport.setActionCommand("Issue");
//									btnImport.setMnemonic(KeyEvent.VK_I);
//								}if(tabCenter.getSelectedIndex() == 1) {
//									btnImport.setText("Import");
//									pnlSouth.add(btnImport);
//									btnImport.setActionCommand("Import");
//									btnImport.setMnemonic(KeyEvent.VK_I);
//								}
//							}
//							pnlSouth.repaint();
//						}
//					});
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1, 7, 3, 3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 50));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnUpload = new JButton("Upload");
					pnlSouth.add(btnUpload);
					btnUpload.setActionCommand("Upload");
					btnUpload.setMnemonic(KeyEvent.VK_I);
					btnUpload.addActionListener(this);
				}
			}
		}

	}//XXX END OF INIT GUI
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if (actionCommand.equals("Browse")) {
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
				txtFileName.setText(selectedFile);
			}
		}
		
		if(actionCommand.equals("Process")) {

			String selectedFile = fileChooser.getSelectedFile().toString();
			File fileSelected = new File(selectedFile);
			System.out.printf("Display selected file: %s%n", selectedFile);
			
			tabCenter.remove(0);

			try {
				int sheetCount = SpreadSheet.createFromFile(fileSelected).getSheetCount();
				System.out.printf("Display Sheet Count: %s%n", sheetCount);
				for (int x = 0; x < sheetCount; x++) {
					System.out.printf("Display loop value: %s%n", x);

					Sheet sheet = SpreadSheet.createFromFile(fileSelected).getSheet(x);

					String sheetName = sheet.getName().toString();
					System.out.printf("Sheet Name: %s%n", sheetName);

					streamSheet(sheet);

					System.out.println("Returned Rows: " + tblWaterPmts.getRowCount());
					tabCenter.addTab("Garbage Fee", null, new JScrollPane(tblWaterPmts), null);
					tabCenter.setSelectedIndex(0);

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(actionCommand.equals("Upload")) {
			
			int entity_column = 0;
			int amount_column = 0;
			int phase_column = 0;
			int block_column = 0;
			int lot_column = 0;
			int unit_column = 0;
			int ordate_column = 0;
			int balance_group_column = 0;
			for (int x = 0; x < modelHappyWellWaterUpload.getColumnCount(); x++) {
				if(modelHappyWellWaterUpload.getColumnName(x).equals("Payee") || modelHappyWellWaterUpload.getColumnName(x).equals("PAYEE")) {
					entity_column = x;
				}
				if(modelHappyWellWaterUpload.getColumnName(x).equals("Amount") || modelHappyWellWaterUpload.getColumnName(x).equals("AMOUNT") || modelHappyWellWaterUpload.getColumnName(x).equals("TOTAL AMOUNT") || modelHappyWellWaterUpload.getColumnName(x).equals("Total Amount")) {
					amount_column = x;
				}
				if(modelHappyWellWaterUpload.getColumnName(x).equals("Phase") || modelHappyWellWaterUpload.getColumnName(x).equals("PHASE")) {
					phase_column = x;
				}
				if(modelHappyWellWaterUpload.getColumnName(x).equals("Block") || modelHappyWellWaterUpload.getColumnName(x).equals("BLOCK")) {
					block_column = x;
				}
				if(modelHappyWellWaterUpload.getColumnName(x).equals("Lot") || modelHappyWellWaterUpload.getColumnName(x).equals("LOT")) {
					lot_column = x;
				}
				if(modelHappyWellWaterUpload.getColumnName(x).equals("OR Date") || modelHappyWellWaterUpload.getColumnName(x).equals("OR DATE")) {
					ordate_column = x;
				}
				if(modelHappyWellWaterUpload.getColumnName(x).equals("Balance Group") || modelHappyWellWaterUpload.getColumnName(x).equals("BALANCE GROUP")) {
					balance_group_column = x;
				}
				if(modelHappyWellWaterUpload.getColumnName(x).equals("Unit") || modelHappyWellWaterUpload.getColumnName(x).equals("UNIT")) {
					unit_column = x;
				}
			}
			
			int confirmation = JOptionPane.showConfirmDialog(null,
					"Import " + modelHappyWellWaterUpload.getRowCount() + " rows?");
			if (confirmation == 0) {
				FncGlobal.startProgress("Importing from spreadsheet...");
				
				System.out.printf("value of block column: %s%n", block_column);
				
				String proj_id = lookupProject.getValue();
				Date period_from = dateFr.getDate();
				Date period_to = dateTo.getDate();
				
				for (int x = 0; x < modelHappyWellWaterUpload.getRowCount(); x++) {
					String entity_name = (String) modelHappyWellWaterUpload.getValueAt(x, entity_column);
					String phase = String.valueOf(modelHappyWellWaterUpload.getValueAt(x, phase_column));
					String block = String.valueOf(modelHappyWellWaterUpload.getValueAt(x, block_column));
					String lot = String.valueOf(modelHappyWellWaterUpload.getValueAt(x, lot_column));
					BigDecimal amount = (BigDecimal) modelHappyWellWaterUpload.getValueAt(x, amount_column);
					String unit = String.valueOf(modelHappyWellWaterUpload.getValueAt(x, unit_column));
					
					System.out.println("Row "+x+"\nPayee: "+entity_name+"\nPhase: "+phase+"\nBlock: "+block+"\nLot: "+lot+"\nAmount: "+amount+"\nProj ID: "+proj_id+"\n");
					
					String SQL = "SELECT sp_upload_happy_well_collection(\n"
							+ "	'"+entity_column+"',\n"
							+ "	'"+phase+"',\n"
							+ "	'"+block+"',\n"
							+ "	'"+lot+"',\n"
							+ "	'"+proj_id+"',\n"
							+ "	"+amount+",\n"
							+ "	'"+UserInfo.EmployeeCode+"',\n"
							+ "	'"+period_from+"',\n"
							+ "	'"+period_to+"')";
			
					pgSelect db = new pgSelect();
					//db.select(SQL);
					
					FncSystem.out("Display value of SQL", SQL);
					
				}
				System.out.println("Tapos na " + modelHappyWellWaterUpload.getRowCount());
				FncGlobal.stopProgress();
				
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Upload Successful", "Import", JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("Cancel");
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

		modelHappyWellWaterUpload = new DefaultTableModel(rowValue, columnNames) {
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

		tblWaterPmts = new _JTableMain(modelHappyWellWaterUpload);
		tblWaterPmts.getColumnModel().getColumn(0).setCellEditor(tblWaterPmts.getDefaultEditor(Boolean.class));
		tblWaterPmts.getColumnModel().getColumn(0).setCellRenderer(tblWaterPmts.getDefaultRenderer(Boolean.class));
		tblWaterPmts.getColumnModel().getColumn(0).setWidth(35);
		tblWaterPmts.getColumnModel().getColumn(0).setMinWidth(35);
		tblWaterPmts.getColumnModel().getColumn(0).setMaxWidth(35);

		tblWaterPmts.setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
		tblWaterPmts.setDefaultRenderer(Time.class, DateRenderer.getTimeRenderer());
		tblWaterPmts.setDefaultRenderer(Timestamp.class, DateRenderer.getDateRenderer());
		tblWaterPmts.setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		tblWaterPmts.setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));
		tblWaterPmts.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
		tblWaterPmts.setDefaultRenderer(_JLookup.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));

		RolloverMouseAdapter rolloverAdapter = new RolloverMouseAdapter(tblWaterPmts);

		CheckBoxHeader.RolloverAdapter ma = new CheckBoxHeader.RolloverAdapter(tblWaterPmts);
		tblWaterPmts.getTableHeader().addMouseListener(ma);
		tblWaterPmts.getTableHeader().addMouseMotionListener(ma);

		tblWaterPmts.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(tblWaterPmts, ma));
		tblWaterPmts.getColumnModel().getColumn(0).setMaxWidth(35);
		tblWaterPmts.getColumnModel().getColumn(0).setMinWidth(35);
		tblWaterPmts.getColumnModel().getColumn(0).setPreferredWidth(35);

		tblWaterPmts.addMouseListener(rolloverAdapter);
		tblWaterPmts.addMouseMotionListener(rolloverAdapter);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblWaterPmts.getModel());
		tblWaterPmts.setRowSorter(sorter);
		sorter.setSortable(0, false);

		for (int x = (modelHappyWellWaterUpload.getRowCount() - 1); x >= 0; x--) {
			Object value = modelHappyWellWaterUpload.getValueAt(x, 1);
			if (value == null) {
				modelHappyWellWaterUpload.removeRow(x);
			}
		}

		// Remove hidden columns
		for (int x = tblWaterPmts.getRowCount() - 1; x >= 0; x--) {
			if (listHiddenColumns.contains(x)) {
				tblWaterPmts.removeColumn(tblWaterPmts.getColumn(x + 1));
			}
		}

		for (int x = 0; x < tblWaterPmts.getColumnCount(); x++) {

		}

		tblWaterPmts.packAll();

		return tblWaterPmts;

	}
	
	
	private static String sqlProject() {
		return "SELECT proj_id, proj_name, proj_alias FROM mf_project where status_id = 'A' \n" + "ORDER BY proj_name";
	}

}
