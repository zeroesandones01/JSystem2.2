package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Action;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdom.Attribute;
import org.jopendocument.dom.spreadsheet.Column;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.toedter.calendar.JDateChooser;

import Buyers.ClientServicing._CARD;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.CheckBoxHeader;
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
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelTable_Ledger;


public class ItsReal_SOA_BIR extends _JInternalFrame implements _GUI, ActionListener  {


	static String title = "ItsReal SOA BIR";
	Dimension frameSize = new Dimension(750, 600);

	private JPanel pnlMain; 
	private _JLookup lookupClient;
	private JButton btnCancel,btnSave;
	static _JTableMain tblLedger; 
	private JLabel lblClient,lblProj,lblUnit,lblClient2,lblProj2,lblUnit2;
	private JList rowLedger;
	private static  JTabbedPane tabDesc;
	DefaultTableModel modelTblLedger;
	private JScrollPane scrollLedger;
	private JTextField txtProj,txtUnit;
	private _JXTextField txtFile;
	private JButton btnBrowse;
	private String pbl_id;
	private Integer seq_no;
	private JLabel lblLumpAmt;
	private _JXFormattedTextField txtLumpAmt;
	private JLabel lblParticular;
	private _JLookup lookupParticular;
	private _JXTextField txtParticular;
	private JLabel lblReceiptNo;
	private JTextField txtReceiptNo;
	private JLabel lblDateLump;
	private JDateChooser dteLumpPmt;
	
	private JFileChooser fileChooser;

	public ItsReal_SOA_BIR(){
		super(title, true, true, true,true);
		initGUI();
	}
	public ItsReal_SOA_BIR(String title){
		super(title);
		initGUI();

	}
	public ItsReal_SOA_BIR(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconiflable){
		super(title,resizable,closable,maximizable,iconiflable);
		initGUI();

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));		
		this.setPreferredSize(frameSize);
		this.setSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));
			{
				JPanel pnlMainNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainNorth,BorderLayout.NORTH);
				pnlMainNorth.setBorder(new EmptyBorder(5,5,5,5));
				{
					JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(0,150));
					{
						JPanel pnlNorthWest = new JPanel(new GridLayout(7,1,3,3));
						pnlNorth.add(pnlNorthWest,BorderLayout.WEST);
						{
							lblClient = new JLabel("Client:");
							pnlNorthWest.add(lblClient);
						}
						{
							lblProj = new JLabel("Project:");
							pnlNorthWest.add(lblProj);
						}
						{
							lblUnit = new JLabel("Unit/Seq:");
							pnlNorthWest.add(lblUnit);
						}
						{
							lblReceiptNo = new JLabel("Receipt No");
							pnlNorthWest.add(lblReceiptNo);
						}
						{
							lblDateLump = new JLabel("Date Lumpsum");
							pnlNorthWest.add(lblDateLump);
						}
						{
							lblParticular = new JLabel("Particular");
							pnlNorthWest.add(lblParticular);
						}
						{
							JLabel lblFile = new JLabel("Select file");
							pnlNorthWest.add(lblFile);
						}
					}
					{
						JPanel pnlNorthCenter = new JPanel(new GridLayout(7,1,3,3));
						pnlNorth.add(pnlNorthCenter,BorderLayout.CENTER);
						{
							JPanel pnlCleint = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlCleint);
							{
								JPanel pnlClientWest = new JPanel(new BorderLayout(5,5));
								pnlCleint.add(pnlClientWest,BorderLayout.WEST);
								pnlClientWest.setPreferredSize(new Dimension(100,0));
								{
									lookupClient = new _JLookup(null,"Client");
									pnlClientWest.add(lookupClient);
									lookupClient.setEnabled(true);
									lookupClient.setLookupSQL(_CARD.sqlClients());
									lookupClient.setReturnColumn(0);
									lookupClient.setFilterIndex(2);
									lookupClient.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();


											if(data != null){
												lookupClient.setValue((String) data[1]);
												lblClient2.setText(String.format("[%s]", (String) data[2]));
												txtProj.setText((String) data[7]);
												lblProj2.setText(String.format("[%s]", (String) data[8]));
												txtUnit.setText((String) data[4]);
												lblUnit2.setText(String.format("[%s] [%s]", (String) data[3], Integer.toString((Integer) data[5])));

												pbl_id = (String) data[4];
												seq_no = (Integer) data[5];
												btnCancel.setEnabled(true);	
												//displayValueLedger(modelTblLedger, lookupClient.getValue(), txtProj.getText(), pbl_id, seq_no);

												
												txtReceiptNo.setEditable(true);
												txtLumpAmt.setEditable(true);
												dteLumpPmt.getCalendarButton().setEnabled(true);
												dteLumpPmt.setEnabled(true);
												lookupParticular.setEditable(true);
											}
										}

									});

								}
							}
							{
								JPanel pnlClientCenter = new JPanel(new BorderLayout(5,5));
								pnlCleint.add(pnlClientCenter,BorderLayout.CENTER);
								{
									lblClient2 = new JLabel("[]");
									pnlClientCenter.add(lblClient2);
								}
							}
						}
						{
							JPanel pnlProj = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlProj);
							{
								JPanel pnlProjWest = new JPanel(new BorderLayout(5,5));
								pnlProj.add(pnlProjWest,BorderLayout.WEST);
								pnlProjWest.setPreferredSize(new Dimension(50,0));
								{
									txtProj = new JTextField();
									pnlProjWest.add(txtProj);
									txtProj.setHorizontalAlignment(JTextField.CENTER);
									txtProj.setEnabled(false);
								}
							}
							{
								JPanel pnlProjCenter = new JPanel(new BorderLayout(5,5));
								pnlProj.add(pnlProjCenter,BorderLayout.CENTER);
								{
									lblProj2 = new JLabel("[]");
									pnlProjCenter.add(lblProj2);
								}

							}
						}
						{

							JPanel pnlUnit = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlUnit);
							{
								JPanel pnlUnitWest = new JPanel(new BorderLayout(5,5));
								pnlUnit.add(pnlUnitWest,BorderLayout.WEST);
								pnlUnitWest.setPreferredSize(new Dimension(50,0));
								{
									txtUnit = new JTextField();
									pnlUnitWest.add(txtUnit);
									txtUnit.setHorizontalAlignment(JTextField.CENTER);
									txtUnit.setEnabled(false);
								}
							}
							{
								JPanel pnlUnitCenter = new JPanel(new BorderLayout(5,5));
								pnlUnit.add(pnlUnitCenter,BorderLayout.CENTER);
								{
									lblUnit2 = new JLabel("[]");
									pnlUnitCenter.add(lblUnit2);
								}
							}
						}
						{
							JPanel pnlReceiptNo = new JPanel(new BorderLayout(3, 3));
							pnlNorthCenter.add(pnlReceiptNo);
							{
								txtReceiptNo = new JTextField();
								pnlReceiptNo.add(txtReceiptNo, BorderLayout.WEST);
								txtReceiptNo.setPreferredSize(new Dimension(150, 0));
								txtReceiptNo.setEditable(false);
							}
							{
								JPanel pnlAmt = new JPanel(new BorderLayout(3, 3));
								pnlReceiptNo.add(pnlAmt, BorderLayout.EAST);
								pnlAmt.setPreferredSize(new Dimension(200, 0));
								{
									lblLumpAmt = new JLabel("Lumpsum Amt");
									pnlAmt.add(lblLumpAmt, BorderLayout.WEST);
								}
								{
									txtLumpAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlAmt.add(txtLumpAmt, BorderLayout.CENTER);
									txtLumpAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtLumpAmt.setEditable(false);
								}
							}
						}
						
						
						{
							JPanel pnlDateLump = new JPanel(new BorderLayout(3, 3));
							pnlNorthCenter.add(pnlDateLump);
							{
								dteLumpPmt = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlDateLump.add(dteLumpPmt, BorderLayout.WEST);
								dteLumpPmt.setPreferredSize(new Dimension(200, 0));
								dteLumpPmt.getCalendarButton().setEnabled(false);
								dteLumpPmt.setEnabled(false);
							}
						}
						{
							JPanel pnlParticular = new JPanel(new BorderLayout(3, 3));
							pnlNorthCenter.add(pnlParticular);
							{
								lookupParticular = new _JLookup(null, "Particular", 0);
								pnlParticular.add(lookupParticular, BorderLayout.WEST);
								lookupParticular.setPrompt("Part. ID");
								lookupParticular.setFilterName(true);
								lookupParticular.setEditable(false);
								lookupParticular.setLookupSQL(sqlParticular());
								lookupParticular.setPreferredSize(new Dimension(80, 0));
								lookupParticular.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										
										
										if(data != null) {
											String particular = (String) data[1];
											txtParticular.setText(particular);
										}
									}
								});
							}
							{
								txtParticular = new _JXTextField();
								pnlParticular.add(txtParticular);
							}
						}
						{
							JPanel pnlFile = new JPanel(new BorderLayout(5, 5));
							pnlNorthCenter.add(pnlFile);
							{
								txtFile = new _JXTextField();
								pnlFile.add(txtFile, BorderLayout.CENTER);
							}
							{
								btnBrowse = new JButton("Browse");
								pnlFile.add(btnBrowse, BorderLayout.EAST);
								btnBrowse.setPreferredSize(new Dimension(100, 0));
								btnBrowse.setActionCommand(btnBrowse.getText());
								btnBrowse.addActionListener(this);
							}
						}
					}
				}

			}
			{
				JPanel pnlMainCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
				{
					tabDesc = new JTabbedPane();
					pnlMainCenter.add(tabDesc);

					tabDesc.addTab("Ledger", null, new JScrollPane(tblLedger), null);



				}
			}
			{
				JPanel pnlMainSouth = new JPanel(new GridLayout(1,8,3,3));
				pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
				pnlMainSouth.setPreferredSize(new Dimension(0,30));

				{
					btnSave = new JButton("Save");
					pnlMainSouth.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.addActionListener(this);
				} 

				{
					btnCancel = new JButton("Cancel");
					pnlMainSouth.add(btnCancel);
					btnCancel.setEnabled(false);

					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							lookupClient.setValue(null);
							lblClient2.setText("[]");
							txtProj.setText("");
							lblProj2.setText("[]");
							txtUnit.setText("");
							lblUnit2.setText("[]");

							btnState(false, false, false);
						}
					});
				}
			}
		}
	}//XXX END OF INIT GUI
	
	private String sqlParticular() {
		String SQL = "select pay_part_id as \"Part ID\", partdesc as \"Particular\" from mf_pay_particular where apply_ledger and status_id = 'A';";
		
		return SQL;
	}

	private void save() {
		String entity_id = lookupClient.getValue();
		String proj_id = txtProj.getText();
		String pbl_id = txtUnit.getText();

		String SQL = "";
		pgUpdate db = new pgUpdate();

		for(int x= 0; x<modelTblLedger.getRowCount(); x++) {

			Date actual_pmt_date = (Date) modelTblLedger.getValueAt(x, 1);
			Date pmt_due_date = (Date) modelTblLedger.getValueAt(x, 2);
			String receipt_no = (String) modelTblLedger.getValueAt(x, 3);
			BigDecimal amt_paid = getBigDecimalValue(modelTblLedger.getValueAt(x, 4));
			BigDecimal other_fees = getBigDecimalValue(modelTblLedger.getValueAt(x, 5));
			BigDecimal res = getBigDecimalValue(modelTblLedger.getValueAt(x, 6));
			BigDecimal dp = getBigDecimalValue(modelTblLedger.getValueAt(x, 7));
			BigDecimal mri = getBigDecimalValue(modelTblLedger.getValueAt(x, 8));
			BigDecimal fire = getBigDecimalValue(modelTblLedger.getValueAt(x, 9));
			BigDecimal vat = getBigDecimalValue(modelTblLedger.getValueAt(x, 10));
			BigDecimal soi = getBigDecimalValue(modelTblLedger.getValueAt(x, 11));
			BigDecimal sop = getBigDecimalValue(modelTblLedger.getValueAt(x, 12));
			BigDecimal resdp_pen = getBigDecimalValue(modelTblLedger.getValueAt(x, 13));
			BigDecimal interest = getBigDecimalValue(modelTblLedger.getValueAt(x, 14));
			BigDecimal principal = getBigDecimalValue(modelTblLedger.getValueAt(x, 15));

			SQL = "INSERT INTO public.rf_itsreal_bir_soa(\n"
					+ "	 entity_id, proj_id, pbl_id, seq_no, actual_pmt_date, pmt_due_date, receipt_no, amt_paid, \n"
					+ "other_fees, reservation, dp, mri, fire, \n"
					+ "	vat, soi, sop, resdp_penalty, interest, principal, status_id, created_by, date_created)\n"
					+ "	VALUES ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+actual_pmt_date+"', "
					+ "'"+pmt_due_date+"', '"+receipt_no+"', "+amt_paid+", "+other_fees+", "+res+", "+dp+", "
					+ ""+mri+", "+fire+", "+vat+", "+soi+", "+sop+", "+resdp_pen+", "+interest+", "+principal+", 'A', '"+UserInfo.EmployeeCode+"', now());";

			db.executeUpdate(SQL, true);
		}
		db.commit();
	}
	
	private void saveLumpPayment() {
		
		String entity_id = lookupClient.getValue();
		String proj_id = txtProj.getText();
		String pbl_id = txtUnit.getText();
		String receipt_no = txtReceiptNo.getText().trim().replace("'", "''");
		BigDecimal lump_amt = (BigDecimal) txtLumpAmt.getValued();
		Date date_lump = dteLumpPmt.getDate();
		String pay_part_id = lookupParticular.getValue();
	
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_save_lump_payment_itsreal_soa('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "
				+ "'"+seq_no+"', '"+receipt_no+"', "+lump_amt+", '"+date_lump+"', '"+pay_part_id+"', '"+UserInfo.EmployeeCode+"', "
				+ "'"+UserInfo.Branch+"')";
		db.select(SQL);
		
	}

	private BigDecimal getBigDecimalValue(Object value) {
		if (value instanceof String && ((String) value).isEmpty()) {
			return BigDecimal.ZERO;
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else {
			return BigDecimal.ZERO; // Default value if the type is unexpected
		}
	}

	private void downloadSOA_Template() {
		
		InputStream jarPdf = getClass().getClassLoader().getResourceAsStream("File/ItsRealSOA_Upload_Template.ods");
		
		
	}
	
	private boolean toSave() {
		
		
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Browse")) {
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
				txtFile.setText(selectedFile);

				File fileSelected = new File(selectedFile);
				System.out.printf("Display selected file: %s%n", selectedFile);

				tabDesc.removeAll();

				try {
					int sheetCount = SpreadSheet.createFromFile(fileSelected).getSheetCount();
					System.out.printf("Display Sheet Count: %s%n", sheetCount);
					for (int x = 0; x < sheetCount; x++) {
						System.out.printf("Display loop value: %s%n", x);

						Sheet sheet = SpreadSheet.createFromFile(fileSelected).getSheet(x);

						String sheetName = sheet.getName().toString();
						System.out.printf("Sheet Name: %s%n", sheetName);

						streamSheet(sheet);

						//System.out.println("Returned Rows: " + tblLedger.getRowCount());
						tabDesc.addTab("Ledger", null, new JScrollPane(tblLedger), null);
						//tabDesc.setSelectedIndex(0);

					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				btnState(true, true, true);
			}
		}

		if(actionCommand.equals("Save")) {
			save();
			
			saveLumpPayment();
			
			txtReceiptNo.setText("");
			txtLumpAmt.setValue(new BigDecimal("0.00"));
			dteLumpPmt.setDate(null);
			lookupParticular.setValue(null);
			txtParticular.setText("");
			
			txtReceiptNo.setEditable(false);
			txtLumpAmt.setEditable(false);
			dteLumpPmt.getCalendarButton().setEnabled(false);
			dteLumpPmt.setEnabled(false);
			lookupParticular.setEditable(false);
			
			tabDesc.removeAll();
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Upload Successful", "Upload", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void btnState(Boolean save, Boolean cancel, Boolean browse) {
		btnSave.setEnabled(save);
		btnCancel.setEnabled(cancel);
		btnBrowse.setEnabled(browse);
	}



	protected JTable streamSheet(Sheet sheet) {
		int colCount = sheet.getColumnCount();
		int rowCount = sheet.getRowCount();

		ArrayList<Integer> deleteColumn = new ArrayList<Integer>();
		ArrayList<Integer> deleteRow = new ArrayList<Integer>();

		Object[] columnNames = null;
		Object[][] rowValue = null;

		rowValue = new Object[rowCount][colCount+1];

		int colnameRow = 0;
		for (int r = 0; r < rowCount; r++) {
			Boolean isBreak = false;
			for (int c = 0; c < colCount; c++) {
				if (sheet.getValueAt(c, r).toString().trim().toUpperCase().equals("ACTUAL PMT DATE")) {
					colnameRow = r;
					isBreak = true;
					System.out.println("Dumaan dito sa 1");
				}
			}
			if (isBreak) {
				break;
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
		//			for (int c = 0; c < colCount; c++) {
		//				if (sheet.getValueAt(c, colnameRow).toString().equals("") || sheet.getValueAt(c, colnameRow) == null
		//						|| sheet.getValueAt(c, colnameRow).toString().isEmpty()) {
		//					deleteColumn.add((c + 1));
		//				} else
		//					deleteColumn.clear();
		//			}
		//			colCount = (deleteColumn.get(0) - 1);
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

		modelTblLedger = new DefaultTableModel(rowValue, columnNames) {
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

		tblLedger = new _JTableMain(modelTblLedger);
		tblLedger.getColumnModel().getColumn(0).setCellEditor(tblLedger.getDefaultEditor(Boolean.class));
		tblLedger.getColumnModel().getColumn(0).setCellRenderer(tblLedger.getDefaultRenderer(Boolean.class));
		tblLedger.getColumnModel().getColumn(0).setWidth(35);
		tblLedger.getColumnModel().getColumn(0).setMinWidth(35);
		tblLedger.getColumnModel().getColumn(0).setMaxWidth(35);

		tblLedger.setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
		tblLedger.setDefaultRenderer(Time.class, DateRenderer.getTimeRenderer());
		tblLedger.setDefaultRenderer(Timestamp.class, DateRenderer.getDateRenderer());
		tblLedger.setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		tblLedger.setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));
		tblLedger.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
		tblLedger.setDefaultRenderer(_JLookup.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));

		RolloverMouseAdapter rolloverAdapter = new RolloverMouseAdapter(tblLedger);

		CheckBoxHeader.RolloverAdapter ma = new CheckBoxHeader.RolloverAdapter(tblLedger);
		tblLedger.getTableHeader().addMouseListener(ma);
		tblLedger.getTableHeader().addMouseMotionListener(ma);

		tblLedger.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(tblLedger, ma));
		tblLedger.getColumnModel().getColumn(0).setMaxWidth(35);
		tblLedger.getColumnModel().getColumn(0).setMinWidth(35);
		tblLedger.getColumnModel().getColumn(0).setPreferredWidth(35);

		tblLedger.addMouseListener(rolloverAdapter);
		tblLedger.addMouseMotionListener(rolloverAdapter);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblLedger.getModel());
		tblLedger.setRowSorter(sorter);
		sorter.setSortable(0, false);

		for (int x = (modelTblLedger.getRowCount() - 1); x >= 0; x--) {
			Object value = modelTblLedger.getValueAt(x, 1);
			if (value == null) {
				modelTblLedger.removeRow(x);
			}
		}

		// Remove hidden columns
		for (int x = tblLedger.getRowCount() - 1; x >= 0; x--) {
			if (listHiddenColumns.contains(x)) {
				tblLedger.removeColumn(tblLedger.getColumn(x + 1));
			}
		}

		for (int x = 0; x < tblLedger.getColumnCount(); x++) {

		}

		tblLedger.packAll();

		return tblLedger;

	}




}

