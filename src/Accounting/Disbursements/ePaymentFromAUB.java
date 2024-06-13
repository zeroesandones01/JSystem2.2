package Accounting.Disbursements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
import DateChooser._JDateChooser;
import Functions.CheckBoxHeader;
import Functions.FncGlobal;
import Functions.FncODSFileFilter;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup._JLookup;
import Lookup._JLookupTable.RolloverMouseAdapter;
import Renderer.DateRenderer;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_house_accomplishment;

public class ePaymentFromAUB extends _JInternalFrame implements _GUI {
	
	private static final long serialVersionUID = 9032673356061487100L;
	
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
	JLabel lblWithPBL;	
	JXTextField tagSelectFile;	
	_JDateChooser dateTo;	
	_JTableMain tblHouseAccomplishment;
	JTabbedPane tabCenter;	
	JScrollPane scrollHouseAccomplishment;	
	JList rowheaderHouseAccomplishment;	
	JFileChooser fileChooser;	
	model_house_accomplishment modelHouseAccomplishment;	
	DefaultTableModel modelAddUnit;
	
	String pbl_id = null;
	
	protected Boolean pressedShift = false;
	
	static String title = "ePayment - For AUB (Downloading)";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public ePaymentFromAUB() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ePaymentFromAUB(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public ePaymentFromAUB(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

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
					pnlDate.setBorder(JTBorderFactory.createTitleBorder("Date Paid"));	
					pnlDate.setPreferredSize(new java.awt.Dimension(197, 80));	
					{
						pnllblDateTo = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlDate.add(pnllblDateTo, BorderLayout.WEST);
						{
							lblDateTo = new JLabel("Date:");
							pnllblDateTo.add(lblDateTo);
						}
					}
					{
						pnlDateTo = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlDate.add(pnlDateTo, BorderLayout.CENTER);
						{
							dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlDateTo.add(dateTo);
							dateTo.setDate(null);
							dateTo.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dateTo.getDateEditor()).setEditable(false);
							dateTo.setDate(Calendar.getInstance().getTime());
						}
					}
				{
					pnlFileUploading = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlFileUploading, BorderLayout.CENTER);
					pnlFileUploading.setBorder(JTBorderFactory.createTitleBorder("Upload File"));	
					pnlFileUploading.setPreferredSize(new java.awt.Dimension(504, 80));	
					{
						pnlUploading = new JPanel(new GridLayout(2, 1, 10, 0));
						pnlFileUploading.add(pnlUploading, BorderLayout.WEST);
						{
							tagSelectFile = new JXTextField();
							pnlUploading.add(tagSelectFile);
							tagSelectFile.setEditable(false);
							tagSelectFile.setPreferredSize(new java.awt.Dimension(415, 0));
						}
						{
							lblDateTo = new JLabel("*Please select AUB Excel File");
							pnlUploading.add(lblDateTo);
						}
					}
					{
						pnlSelect = new JPanel(new GridLayout(2, 1, 10, 0));
						pnlFileUploading.add(pnlSelect, BorderLayout.EAST);
						{
							JButton btnSelectFile = new JButton("Search");
							pnlSelect.add(btnSelectFile);
							btnSelectFile.setActionCommand("Search");
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
					tabCenter.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
						}
					});
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				pnlSouth.setPreferredSize(new Dimension(500, 30));
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
					btnPreview.setVisible(false);
				}
				{
					btnImport = new JButton("Import");
					pnlSouth.add(btnImport);
					btnImport.setActionCommand("Import");
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
				fileChooser.addChoosableFileFilter(new FncODSFileFilter(new String[] { "ods" }, "Spreadsheets (*.ods, *.odc, *.ots)"));
	
				Action details = fileChooser.getActionMap().get("viewTypeDetails");
				details.actionPerformed(null);
			}
			
			fileChooser.setSelectedFile(new File(""));
			int status = fileChooser.showOpenDialog(this.getTopLevelAncestor());

			if (status == JFileChooser.APPROVE_OPTION) {
				if(fileChooser.getSelectedFile().equals(null)){
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
			
			tabCenter.removeAll();
			
			try {
				int sheetCount = SpreadSheet.createFromFile(fileSelected).getSheetCount();
				System.out.printf("Display Sheet Count: %s%n", sheetCount);
				for(int x=0; x < sheetCount; x++){
					System.out.printf("Display loop value: %s%n", x);
					
					Sheet sheet = SpreadSheet.createFromFile(fileSelected).getSheet(x);

					String sheetName = sheet.getName().toString();
					System.out.printf("Sheet Name: %s%n", sheetName);

					JTable table = streamSheet(sheet);
					
					tabCenter.addTab(sheetName, null, new JScrollPane(table), null);
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (actionCommand.equals("Import")) {
			Import();
			
			/*
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			
				
				pgUpdate db = new pgUpdate();

				for(int x=0; x < modelAddUnit.getRowCount(); x++){
				
				Boolean isSelected = (Boolean) modelAddUnit.getValueAt(x, 0);
				String pv_no = (String) modelAddUnit.getValueAt(x, 3);
				String check_no = (String) modelAddUnit.getValueAt(x, 2);
				//String due_date = (String) modelAddUnit.getValueAt(x, 1);
				String amount = (String) modelAddUnit.getValueAt(x, 6).toString().replace(",","");
				String payee = (String) modelAddUnit.getValueAt(x, 5);
				
				if(isSelected){

					String sqlDetail = 
						"update rf_cv_header set " +
						"date_paid = '"+dateFormat.format(dateTo.getDate())+"',  \n" +	
						"status_id = 'P' , \n" +	
						"proc_id = 5," +
						"remarks = remarks || '\n ***Released through AUB E-payment Facility' \n" +				
						"where cv_no = (select distinct on (cv_no) cv_no from rf_pv_header " +
						"	where pv_no = '"+pv_no+"' and co_id = '02' and status_id != 'I')  " ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);		
					
					String sqlDetail2 = 
						"insert into rf_check values ('02','02'," +
						"(select distinct on (cv_no) cv_no from rf_pv_header \n" + 
						"	where pv_no = '"+pv_no+"' and co_id = '02' and status_id != 'I')," +
						"'"+check_no+"'," +
						"'"+dateFormat.format(dateTo.getDate())+"'," +  //changed from due_date to date_paid; see DCRF No. 582
						"'00073'," +
						""+amount+"," +
						"'"+payee+"'," +
						"'A'," +
						"'01') " ;		
					
					System.out.printf("SQL #2: %s", sqlDetail2);
					db.executeUpdate(sqlDetail2, false);	
					
					String sqlDetail3 = 
						"update rf_aub_epayment set " +
						"status_id = 'P', " +
						"posted_by = '"+UserInfo.EmployeeCode+"', " +
						"posted_date = now() " +
						"where inv_no = '"+pv_no+"' and co_id = '02' and status_id != 'I'" ;		
					
					System.out.printf("SQL #3: %s", sqlDetail3);
					db.executeUpdate(sqlDetail3, false);	
					
					}				
				}
				
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "AUB Check details saving completed.", "Save", JOptionPane.INFORMATION_MESSAGE);
				tabCenter.removeAll();			
			}
			*/
		}
		
		ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";
		
		if(actionCommand.equals("Preview")){
			
			for (int i = 0; i < modelAddUnit.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelAddUnit.getValueAt(i, 0);
				
				if (SelectedItem) {
					iftrue.add(modelAddUnit.getValueAt(i, 1).toString());
					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +	
							
							"SELECT \n" +
							"'"+modelAddUnit.getValueAt(i, 1)+"' AS unit,\n" + 
							"'"+modelAddUnit.getValueAt(i, 2)+"' AS phase, \n" +
							"'"+modelAddUnit.getValueAt(i, 3)+"' AS block, \n" +
							"'"+modelAddUnit.getValueAt(i, 4)+"' AS lot, \n" +
							"'"+modelAddUnit.getValueAt(i, 5)+"' AS contractor, \n" +
							"'"+modelAddUnit.getValueAt(i, 7)+"' AS inspector, \n" +
							"'"+modelAddUnit.getValueAt(i, 13)+"' AS reading, \n" +
							"'"+modelAddUnit.getValueAt(i, 20)+"' AS remarks, \n" +
							"'"+modelAddUnit.getValueAt(i, 6)+"' AS contract_no \n";
				}
			}
			
			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
				return;
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("company", "Cenqhomes Development Corp.");
			mapParameters.put("proj_name", "TV");
			
			FncReport.generateReport("/Reports/rptHouseAccomplishment.jasper", "House Accomplishment", mapParameters, SQL);
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
		for(int r=0;r<rowCount;r++){
			for(int c=0;c<colCount;c++){
				if(sheet.getValueAt(c, r).toString().trim().toUpperCase().equals("ISSUE DATE")) {
					colnameRow = r;
					System.out.println("Dumaan dito sa 1");
				}
			}
		}
		ArrayList<Integer> listHiddenColumns = new ArrayList<Integer>();
		for(int x=0; x < colCount; x++){
			Column<SpreadSheet> col = sheet.getColumn(x);

			for(Object obj : col.getElement().getAttributes()){
				Attribute att = (Attribute) obj;

				if(att.getName().equals("# OF MANPOWER")){
					listHiddenColumns.add(x);
					System.out.println("Dumaan dito sa 2");
				}
			}
		}

		//buburahin ung may mga walang laman na column
		deleteColumn.clear();
		for(int c=0;c<colCount;c++){
			if(sheet.getValueAt(c, colnameRow).toString().equals("") || sheet.getValueAt(c, colnameRow)==null || sheet.getValueAt(c, colnameRow).toString().isEmpty()){
				deleteColumn.add((c+1));
			}else deleteColumn.clear();
		}
		colCount = (deleteColumn.get(0) - 1);
		columnNames = new Object[colCount + 1];
		
		for(int c=0;c<colCount;c++){

			columnNames[c+1] = sheet.getValueAt(c, colnameRow).toString().trim();

			if(!((String)columnNames[c+1]).equals("")){
			}
		}
		for(int r=1;r<rowCount;r++){
				for(int c=0;c<colCount;c++){
					rowValue[0][0] = false;
					rowValue[r][0] = true;
					rowValue[r][c+1] = sheet.getValueAt(c, r);
				}
		}

		modelAddUnit = new DefaultTableModel(rowValue, columnNames){
			private static final long serialVersionUID = 3174178548239382080L;
			public boolean isCellEditable(int rowIndex, int columnIndex) { 
				Boolean to = null;
				if(columnIndex==0) to = true;
				else to = false;
				return to;
			}
		};

		final _JTableMain tblAddUnit = new _JTableMain(modelAddUnit);
		tblAddUnit.getColumnModel().getColumn(0).setCellEditor(tblAddUnit.getDefaultEditor(Boolean.class));
		tblAddUnit.getColumnModel().getColumn(0).setCellRenderer(tblAddUnit.getDefaultRenderer(Boolean.class));
		tblAddUnit.getColumnModel().getColumn(0).setWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setMinWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setMaxWidth(35);
		
		tblAddUnit.setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
		tblAddUnit.setDefaultRenderer(Time.class, DateRenderer.getTimeRenderer());
		tblAddUnit.setDefaultRenderer(Timestamp.class, DateRenderer.getDateRenderer());
		tblAddUnit.setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		tblAddUnit.setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));
		tblAddUnit.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
		tblAddUnit.setDefaultRenderer(_JLookup.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		
		RolloverMouseAdapter rolloverAdapter = new RolloverMouseAdapter(tblAddUnit);

		CheckBoxHeader.RolloverAdapter ma = new CheckBoxHeader.RolloverAdapter(tblAddUnit);
		tblAddUnit.getTableHeader().addMouseListener(ma);
		tblAddUnit.getTableHeader().addMouseMotionListener(ma);

		tblAddUnit.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(tblAddUnit, ma));
		tblAddUnit.getColumnModel().getColumn(0).setMaxWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setMinWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setPreferredWidth(35);

		tblAddUnit.addMouseListener(rolloverAdapter);
		tblAddUnit.addMouseMotionListener(rolloverAdapter);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblAddUnit.getModel());
		tblAddUnit.setRowSorter(sorter);
		sorter.setSortable(0, false);
		
		//modelAddUnit.removeRow(colnameRow);
		
		
		for(int x = (modelAddUnit.getRowCount() -1); x >= 0; x--){
			Object value = modelAddUnit.getValueAt(x, 5);
			Object value2 = modelAddUnit.getValueAt(x, 1);
			Object value3 = modelAddUnit.getValueAt(x, 3);
			Integer lngth_rplf = 9;
			if(value3==null||value3.equals("")) {} else {lngth_rplf = modelAddUnit.getValueAt(x, 3).toString().length();}
			if(value == null||value.equals("")||value2.equals("Issue Date")||lngth_rplf!=9){
				modelAddUnit.removeRow(x);	
				System.out.println("Dumaan dito sa 6");
			}
		}
		
		//uncheck ItsReal PV
		int lngth_rplf = 0;
		for(int x = (modelAddUnit.getRowCount() -1); x >= 0; x--){			
			lngth_rplf = modelAddUnit.getValueAt(x, 3).toString().length();
			System.out.println("lngth_rplf :" + lngth_rplf);
			if (lngth_rplf==10){modelAddUnit.setValueAt(false, x, 0);	}
			else {}				
			System.out.println("Dumaan dito sa 6.A");
		}
		
		//Remove hidden columns
		for(int x = tblAddUnit.getRowCount() -1; x >= 0 ; x--){
			if(listHiddenColumns.contains(x)){
				tblAddUnit.removeColumn(tblAddUnit.getColumn(x + 1));
				System.out.println("Dumaan dito sa 7");
			}
		}

		for(int x=0; x < tblAddUnit.getColumnCount(); x++){
			System.out.printf("Column #%s: %s%n", x, tblAddUnit.getColumnClass(x));
		}
		//evaluate();
		
		tblAddUnit.packAll();
		

		return tblAddUnit;
		
	}
	
	private void Import() {	
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
			
			if (!checkValues(modelAddUnit)) {
				pgUpdate dbExec = new pgUpdate(); 
				dbExec.executeUpdate("delete from tmp_aub_downloading", false); 
				dbExec.commit();
				
				for (int x=0; x<modelAddUnit.getRowCount(); x++) {
					if ((Boolean) modelAddUnit.getValueAt(x, 0)) {
						dbExec = new pgUpdate();
						dbExec.executeUpdate("insert into tmp_aub_downloading \n" + 
								"values ('"+modelAddUnit.getValueAt(x, 1)+"'::date, '"+modelAddUnit.getValueAt(x, 2)+"', '"+modelAddUnit.getValueAt(x, 3)+"', \n" + 
								"'"+modelAddUnit.getValueAt(x, 4)+"', '"+modelAddUnit.getValueAt(x, 5)+"', '"+modelAddUnit.getValueAt(x, 6).toString().replace(",", "")+"'::numeric(19, 2), \n" + 
								"'"+modelAddUnit.getValueAt(x, 7)+"', '"+modelAddUnit.getValueAt(x, 8)+"'); ", false);
						dbExec.executeUpdate("update rf_pv_header set oth_ref_no = '"+modelAddUnit.getValueAt(x, 2).toString()+"' where pv_no = '"+modelAddUnit.getValueAt(x, 3).toString()+"'", false);
						dbExec.commit();	
					}
				}
				
				pgSelect dbQuery = new pgSelect(); 
				dbQuery.select("select distinct vendor_code from tmp_aub_downloading");
				
				for (int x=0; x<dbQuery.getRowCount(); x++) {
					String strCV = createCV(dbQuery.getResult()[x][0].toString()); 
					updateCV(dbQuery.getResult()[x][0].toString(), strCV); 
				}

				for (int x=0; x<modelAddUnit.getRowCount(); x++) {
					if ((Boolean) modelAddUnit.getValueAt(x, 0)) {
						dbExec = new pgUpdate();

						dbExec.executeUpdate("insert into rf_check values ('02','02', \n" +
								"(select distinct on (cv_no) cv_no from rf_pv_header where pv_no = '"+modelAddUnit.getValueAt(x, 3).toString()+"' and co_id = '02' and status_id != 'I'), \n" + 
								"'"+modelAddUnit.getValueAt(x, 2)+"', '"+dateFormat.format(dateTo.getDate())+"', '00073', "+modelAddUnit.getValueAt(x, 6).toString().replace(",","")+", '"+modelAddUnit.getValueAt(x, 5)+"',  'A', '01')", true);
						
						dbExec.executeUpdate("update rf_aub_epayment set \n" +
								"status_id = 'P', posted_by = '"+UserInfo.EmployeeCode+"', posted_date = now() \n" +
								"where inv_no = '"+modelAddUnit.getValueAt(x, 3).toString()+"' and co_id = '02' and status_id != 'I'", false);
						
						dbExec.commit();
					}
				}
				
				JOptionPane.showMessageDialog(getContentPane(), "Import successful!");
				
				deleteUnusedRows(); 
				deleteUnusedRows(); 
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Import did not proceed!");
			}
		}
	}
	
	private boolean checkValues(DefaultTableModel model) {
		String strPV = ""; 
		Boolean blnRev = false; 
		
		for (int x=0; x<model.getRowCount(); x++) {
			if ((Boolean) model.getValueAt(x, 0)) {
				if (FncGlobal.GetBoolean("select exists(select y.*\n" + 
						"from rf_pv_header x\n" + 
						"inner join rf_cv_header y on x.cv_no = y.cv_no\n" + 
						"where x.pv_no = '"+model.getValueAt(x, 3).toString()+"' and coalesce(y.remarks, '') ~* 'Released through AUB E-payment Facility'\n" + 
						"and y.status_id = 'P')")) {
					blnRev = true; 
					strPV = strPV + "*          " + model.getValueAt(x, 3).toString() + "\n"; 
				}
			}
		}
		
		if (blnRev) {
			JOptionPane.showMessageDialog(getContentPane(), "Some of the selected rows have already been posted. \nPV No(s). \n"+strPV);
		}
		
		return blnRev; 
	}
	
	private String createCV(String strVendorCode) {
		String strCV = FncGlobal.GetString("select trim(to_char(max(coalesce(y.cv_no::int,0))+1,'000000000')) from rf_cv_header y where y.status_id != 'I'");
		
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("strCV: "+strCV);
		
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("insert into rf_cv_header \n" +
				"select x.co_id, x.busunit_id, '"+strCV+"' as cv_no, \n" + 
				"x.cv_date, x.entity_id1, x.entity_id2, x.entity_type_id, '"+dateFormat.format(dateTo.getDate())+"', \n" + 
				"x.pay_type_id, x.print_counter, x.printed_by, x.doc_id, 5, x.date_posted, x.posted_by, \n" + 
				"x.remarks || ' ***Released through AUB E-payment Facility', 'P', x.created_by, x.date_created, x.edited_by, x.date_edited\n" + 
				"from rf_cv_header x\n" + 
				"where x.cv_no in (select b.cv_no from rf_cv_header a inner join rf_pv_header b on a.cv_no = b.cv_no \n" + 
				"where b.pv_no in (select x.doc_no from tmp_aub_downloading x where x.vendor_code = '"+strVendorCode+"' limit 1))", false);
		dbExec.commit();
		
		System.out.println("");
		System.out.println("insert into rf_cv_header \n" +
				"select x.co_id, x.busunit_id, '"+strCV+"' as cv_no, \n" + 
				"x.cv_date, x.entity_id1, x.entity_id2, x.entity_type_id, '"+dateFormat.format(dateTo.getDate())+"', \n" + 
				"x.pay_type_id, x.print_counter, x.printed_by, x.doc_id, 5, x.date_posted, x.posted_by, \n" + 
				"x.remarks || ' ***Released through AUB E-payment Facility', 'P', x.created_by, x.date_created, x.edited_by, x.date_edited\n" + 
				"from rf_cv_header x\n" + 
				"where x.cv_no in (select b.cv_no from rf_cv_header a inner join rf_pv_header b on a.cv_no = b.cv_no \n" + 
				"where b.pv_no in (select x.doc_no from tmp_aub_downloading x where x.vendor_code = '"+strVendorCode+"' limit 1))");
		
		pgSelect dbQuery = new pgSelect(); 
		dbQuery.select("select c.co_id, c.tran_amt \n" + 
				"from rf_cv_header a \n" + 
				"inner join rf_pv_header b on a.cv_no = b.cv_no and a.co_id = b.co_id\n" + 
				"inner join (select * from rf_pv_detail where status_id != 'I') c on b.pv_no = c.pv_no and b.co_id = c.co_id\n" + 
				"where b.pv_no in (select x.doc_no from tmp_aub_downloading x where x.vendor_code = '"+strVendorCode+"') \n" + 
				"and c.bal_side = 'D'");
		
		System.out.println("");
		System.out.println("select c.co_id, c.tran_amt \n" + 
				"from rf_cv_header a \n" + 
				"inner join rf_pv_header b on a.cv_no = b.cv_no and a.co_id = b.co_id \n" + 
				"inner join (select * from rf_pv_detail where status_id != 'I') c on b.pv_no = c.pv_no and b.co_id = c.co_id \n" + 
				"where b.pv_no in (select x.doc_no from tmp_aub_downloading x where x.vendor_code = '\"+strVendorCode+\"') \n" + 
				"and c.bal_side = 'D'");
		
		for (int x=0; x<dbQuery.getRowCount(); x++) {
			dbExec = new pgUpdate(); 
			dbExec.executeUpdate("insert into rf_cv_detail(co_id, busunit_id, cv_no, tran_amt, bal_side, acct_id, old_acct_id, status_id)\n" + 
					"values ('"+dbQuery.getResult()[x][0].toString()+"', '"+dbQuery.getResult()[x][0].toString()+"', \n" +
					"'"+strCV+"', "+dbQuery.getResult()[x][1].toString()+", 'D', '03-01-01-001', '', 'A');", false);
			dbExec.executeUpdate("insert into rf_cv_detail(co_id, busunit_id, cv_no, tran_amt, bal_side, acct_id, old_acct_id, status_id)\n" + 
					"values ('"+dbQuery.getResult()[x][0].toString()+"', '"+dbQuery.getResult()[x][0].toString()+"', \n" +
					"'"+strCV+"', "+dbQuery.getResult()[x][1].toString()+", 'C', '01-01-04-053', '', 'A');", false);
			dbExec.commit();
			
			System.out.println("");
			System.out.println("insert into rf_cv_detail(co_id, busunit_id, cv_no, tran_amt, bal_side, acct_id, old_acct_id, status_id)\n" + 
					"values ('"+dbQuery.getResult()[x][0].toString()+"', '"+dbQuery.getResult()[x][0].toString()+"', \n" +
					"'"+strCV+"', "+dbQuery.getResult()[x][1].toString()+", 'D', '03-01-01-001', '', 'A');");
			System.out.println("insert into rf_cv_detail(co_id, busunit_id, cv_no, tran_amt, bal_side, acct_id, old_acct_id, status_id)\n" + 
					"values ('"+dbQuery.getResult()[x][0].toString()+"', '"+dbQuery.getResult()[x][0].toString()+"', \n" +
					"'"+strCV+"', "+dbQuery.getResult()[x][1].toString()+", 'C', '01-01-04-053', '', 'A');");
		}
		
		return strCV; 
	}
	
	private void updateCV(String strVendorCode, String strCV) {
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("update rf_pv_header \n" +
				"set cv_no = '"+strCV+"' \n" +
				"where pv_no in (select doc_no from tmp_aub_downloading where vendor_code = '"+strVendorCode+"')", false); 
		dbExec.commit();
	}
	
	private void deleteUnusedRows() {
		for (int x=0; x<modelAddUnit.getRowCount(); x++) {
			if (!(Boolean) modelAddUnit.getValueAt(x, 0)) {
				modelAddUnit.removeRow(x);
				x=0; 
			}
		}
	}
}

