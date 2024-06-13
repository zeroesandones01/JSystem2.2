package Utilities;

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

public class ImportHouseAccomplishments extends _JInternalFrame implements _GUI {
	
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
	
	static String title = "Import House Accomplishments";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public ImportHouseAccomplishments() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ImportHouseAccomplishments(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public ImportHouseAccomplishments(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
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
			//pnlMain.setPreferredSize(new java.awt.Dimension(798, 85));
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
						{
							chkPBL = new JCheckBox("With PBL ID");
							pnlDateTo.add(chkPBL);
							chkPBL.setEnabled(false);
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
							lblDateTo = new JLabel("*Please select House Accomplishment Report");
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
							//JTabbedPane tab = ((JTabbedPane)arg0.getSource());

							try {
								//_JTableMain table = (_JTableMain) ((JScrollPane)tab.getSelectedComponent()).getViewport().getView();
								//lblTotalUnits.setText(String.format("Table Unit(s): %s", table.getRowCount()));
							} catch (NullPointerException e) { }
						}
					});
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				//pnlSouth.setBorder(lineBorder);
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
		if(actionCommand.equals("Search")){
		if(fileChooser == null){
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
		if(actionCommand.equals("Process")){
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
					
					/*for(int y = 0; y < table.getRowCount(); y++){
						System.out.printf("Display Value at Row: %s(%s)%n", y, table.getValueAt(y, 1));
						if(table.getValueAt(y, 1).equals("") || table.getValueAt(y, 1).toString().isEmpty()){
							System.out.println("Pumasok dito sa Remove");
								((DefaultTableModel) table.getModel()).removeRow(y);
								tabCenter.repaint();
								//table.
						}
					}*/
				}
				//tabCenter.addTab(sheetName, null, new JScrollPane(table), null);
				
				//_JTableMain table = (_JTableMain) ((JScrollPane)tabCenter.getSelectedComponent()).getViewport().getView();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(actionCommand.equals("Import")){
			
			ArrayList<Boolean> isValid = new ArrayList<Boolean>();
			ArrayList<Boolean> isSaved = new ArrayList<Boolean>();
			ArrayList<Boolean> isExisting = new ArrayList<Boolean>();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//int pqa_selected_item = lookupProject.getValue();

					FncGlobal.startProgress("Saving New House Accomplishment.");
			String batch = batch();
			for(int x=0; x < modelAddUnit.getRowCount(); x++){
				//*for House Accomplishment
				Boolean isSelected = (Boolean) modelAddUnit.getValueAt(x, 0);
				String unit = (String) modelAddUnit.getValueAt(x, 1);
				BigDecimal phase = (BigDecimal) modelAddUnit.getValueAt(x, 2);
				BigDecimal block = (BigDecimal) modelAddUnit.getValueAt(x, 3);
				BigDecimal lot = (BigDecimal) modelAddUnit.getValueAt(x, 4);
				Date date_from = (Date) modelAddUnit.getValueAt(x, 8);
				Date date_to = (Date) modelAddUnit.getValueAt(x, 9);
				BigDecimal percent = (BigDecimal) modelAddUnit.getValueAt(x, 13);
				String remarks = (String) modelAddUnit.getValueAt(x, 20);
				String contractor = (String) modelAddUnit.getValueAt(x, 5);
				String contract_no = (String) modelAddUnit.getValueAt(x, 6);
				String insp = (String) modelAddUnit.getValueAt(x, 7);

				if(isSelected){

					String SQL = "SELECT sp_save_house_accomplishment('"+unit+"', "+phase+"::varchar, "+block+"::varchar, "+lot+"::varchar, 'New Unit',"
								+ "'"+UserInfo.EmployeeCode+"', now()::timestamp, '"+dateTo.getDate()+"'::timestamp, now()::timestamp,"
								+ "'"+date_from+"', '"+date_to+"', "+percent+"::numeric, '"+remarks+"', '"+contractor+"', '"+contract_no+"', '"+insp+"')";

					pgSelect db = new pgSelect();
					db.select(SQL);
				}

				//*for TO to CMD
				/*Boolean isSelected = (Boolean) modelAddUnit.getValueAt(x, 0);
				BigDecimal phase = (BigDecimal) modelAddUnit.getValueAt(x, 2);
				BigDecimal block = (BigDecimal) modelAddUnit.getValueAt(x, 3);
				BigDecimal lot = (BigDecimal) modelAddUnit.getValueAt(x, 4);
				Date tocmd = (Date) modelAddUnit.getValueAt(x, 8);

				if(isSelected){

					String SQL = "SELECT sp_save_house_accomplishment( "+phase+"::varchar, "+block+"::varchar, "+lot+"::varchar,"
								+ "'"+UserInfo.EmployeeCode+"','"+batch+"', '"+tocmd+"')";

					pgSelect db = new pgSelect();
					db.select(SQL);
				}*/
			}
			
				FncGlobal.stopProgress();
				}
			}).start();
			
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
			
			tabCenter.removeAll();
			
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
	
	protected JTable streamSheet(Sheet sheet) {//XXX streamSheet

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
				if(sheet.getValueAt(c, r).toString().trim().toUpperCase().equals("UNIT")) {
					colnameRow = r;
					//System.out.println("Dumaan dito sa 1");
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
					//System.out.println("Dumaan dito sa 2");
				}
			}
		}

		//buburahin ung mga walang laman na column
		deleteColumn.clear();
		for(int c=0;c<colCount;c++){
			if(sheet.getValueAt(c, colnameRow).toString().equals("") || sheet.getValueAt(c, colnameRow)==null || sheet.getValueAt(c, colnameRow).toString().isEmpty()){
				deleteColumn.add((c+1));
				//deleteColumn.remove(c);
				//System.out.printf("Display Removed Row: %s(%s)%n", c, colnameRow);
				//System.out.printf("Display Removed Row 2: %s(%s)%n", c+1, colnameRow);
				
				//System.out.println("Col: "+(c+1)+" Value: "+columnNames[c+1]);
				//System.out.printf("%s\n", colnameRow);
			}else deleteColumn.clear();
		}
		colCount = (deleteColumn.get(0) - 1);
		columnNames = new Object[colCount + 1];

/*		//buburahin ung may mga walang laman na row
				deleteRow.clear();
				for(int r=0;r<rowCount;r++){
					if(sheet.getValueAt(r, rownameRow).toString().equals("") || sheet.getValueAt(r, rownameRow)==null){
						deleteRow.add((r+1));
						//System.out.println("Row: "+(r+1)+" Value: "+columnNames[r+1]);
					}else deleteRow.clear();
				}
				rowCount = (deleteRow.get(0) - 1);
				columnNames = new Object[rowCount + 1];*/
		
		for(int c=0;c<colCount;c++){

			columnNames[c+1] = sheet.getValueAt(c, colnameRow).toString().trim();
			//sheet.getColumn(c).getWidth();

			if(!((String)columnNames[c+1]).equals("")){
				//System.out.printf("%s (%s)%n", sheet.getValueAt(c, colnameRow).toString().trim(), sheet.getValueAt(c, colnameRow).getClass());
				//System.out.println("Dumaan dito sa 4");
			}
		}
		//System.out.println("Total: "+deleteColumn.size());
		for(int r=7;r<rowCount;r++){
				for(int c=0;c<colCount;c++){
					rowValue[0][0] = false;
					rowValue[r][0] = true;
					rowValue[r][c+1] = sheet.getValueAt(c, r);
					//System.out.println("Dumaan dito sa 5");
					//System.out.printf("%s\n", rowValue[r][c+1]);
				}
			//}
		}

		modelAddUnit = new DefaultTableModel(rowValue, columnNames){
			private static final long serialVersionUID = 3174178548239382080L;
			public boolean isCellEditable(int rowIndex, int columnIndex) { 
				Boolean to = null;
				if(columnIndex==0) to = false;
				else to = false;
				return to;
				
				/*Boolean to = null;
				if(columnIndex==0) to = true;
				else to = false;
				return to;*/
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
		//RolloverBooleanRenderer renderer = new RolloverBooleanRenderer(rolloverAdapter);

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
			Object value = modelAddUnit.getValueAt(x, 1);
			if(value == null){
				modelAddUnit.removeRow(x);
				//System.out.println("Dumaan dito sa 6");
			}
		}
/*		
		tblAddUnit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				tblAddUnit.requestFocus();
				for(int x=0;x<(tblAddUnit.getSelectedRows().length);x++){
					if(pressedShift){
						System.out.printf("Display Value of Row: %s%n", tblAddUnit.getSelectedRows()[x]);
						tblAddUnit.setValueAt(true, tblAddUnit.getSelectedRows()[x], 0);
					}	
				}
			}
			public void mouseReleased(MouseEvent arg0) {
				tblAddUnit.requestFocus();
			}
		});*/

		//Remove hidden columns
		for(int x = tblAddUnit.getRowCount() -1; x >= 0 ; x--){
			if(listHiddenColumns.contains(x)){
				tblAddUnit.removeColumn(tblAddUnit.getColumn(x + 1));
				//System.out.println("Dumaan dito sa 7");
			}
		}

		for(int x=0; x < tblAddUnit.getColumnCount(); x++){
			//System.out.printf("Column #%s: %s%n", x, tblAddUnit.getColumnClass(x));
		}
		//evaluate();
		
		tblAddUnit.packAll();
		

		return tblAddUnit;
		
	}
	private static Boolean checkIfExist(String contract_no, String phase, String block, String lot, BigDecimal accomplishment, String as_of_date){
		String SQL =
				"select rec_id \n" +
				"from mf_ntp_accomplishment \n" +
				"where ntp_no in \n" +
				"	(select b.ntp_no \n" +
				"	from ls_ntp_header a \n" +
				"	left join ls_ntp_detail b \n" +
				"	on b.ntp_no=a.ntp_no \n" +
				"	where a.contract_no='"+contract_no+"' \n" +
				"	and b.unit_id in ( \n" +
				"	select unit_id from mf_unit_info \n" +
				"	where phase='"+phase+"' \n" +
				"	and block='"+block+"' \n" +
				"	and lot='"+lot+"')) \n" +
				"and seq_no in \n" +
				"	(select b.seq_no \n" +
				"	from ls_ntp_header a \n" +
				"	left join ls_ntp_detail b \n" +
				"	on b.ntp_no=a.ntp_no \n" +
				"	where a.contract_no='"+contract_no+"' \n" +
				"	and b.unit_id in ( \n" +
				"	select unit_id from mf_unit_info \n" +
				"	where phase='"+phase+"' \n" +
				"	and block='"+block+"' \n" +
				"	and lot='"+lot+"')) \n" +
				"and percent_accomplish in ("+accomplishment+" / 100::numeric) \n" +
				"and as_of_date in ('"+as_of_date+"') \n" +
				"order by rec_id desc \n";
				
				pgSelect db = new pgSelect();
				db.select(SQL);

		return db.isNull() ? false:true;
	}
	private static String batch() {
		String batch = "";

		String SQL = "SELECT to_char(COALESCE(MAX(batch_no::INT), 0) + 1, 'FM0000000000') FROM rf_unit_status WHERE status_id = 'A' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {batch = "0000000001";}
			else {batch = (String) db.getResult()[0][0]; }

		}else{
			batch = "0000000001";
		}
	
		return batch;
	}
}
