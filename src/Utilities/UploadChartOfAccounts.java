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
import javax.swing.JTextField;
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
import Functions.FncSystem;
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
import tablemodel.model_house_accomplishment;

public class UploadChartOfAccounts extends _JInternalFrame implements _GUI {

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

	JLabel lblSelectFile;
	JLabel lblWithPBL;

	JXTextField tagSelectFile;

	_JDateChooser dateTo;

	_JTableMain tblHouseAccomplishment;
	JTabbedPane tabCenter;

	JScrollPane scrollHouseAccomplishment;

	JList rowheaderHouseAccomplishment;

	JFileChooser fileChooser;

	DefaultTableModel modelUploadChartOfAccounts;

	protected Boolean pressedShift = false;

	static String title = "Upload Chart of Accounts";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public UploadChartOfAccounts() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadChartOfAccounts(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public UploadChartOfAccounts(String title, boolean resizable,
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
					{
						pnlFileUploading = new JPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlFileUploading, BorderLayout.CENTER);
						pnlNorth.setPreferredSize(new Dimension(0,100));
						pnlFileUploading.setBorder(JTBorderFactory.createTitleBorder("Upload File"));	
						pnlFileUploading.setPreferredSize(new java.awt.Dimension(504, 80));	
						{
							pnlUploading = new JPanel(new GridLayout(3, 1, 10, 0));
							pnlFileUploading.add(pnlUploading, BorderLayout.CENTER);
							{
								tagSelectFile = new JXTextField();
								pnlUploading.add(tagSelectFile);
								tagSelectFile.setEditable(false);
								tagSelectFile.setPreferredSize(new java.awt.Dimension(415, 0));
							}
							{
								lblSelectFile = new JLabel("*Please select file to upload");
								pnlUploading.add(lblSelectFile);
							}
						}
						{
							pnlSelect = new JPanel(new GridLayout(3, 1, 10, 0));
							pnlFileUploading.add(pnlSelect, BorderLayout.EAST);
							pnlSelect.setPreferredSize(new Dimension(100, 0));
							{
								pnlSelect.add(Box.createVerticalBox());

							}
							{
								JButton btnSelectFile = new JButton("Search");
								pnlSelect.add(btnSelectFile);
								btnSelectFile.setActionCommand("Search");
								btnSelectFile.addActionListener(this);
								//btnSelectFile.setPreferredSize(new java.awt.Dimension(65, 0));
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
						pnlSouth.add(Box.createHorizontalBox());
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

			new Thread(new Runnable() {

				@Override
				public void run() {

					FncGlobal.startProgress("Saving New House Accomplishment.");
					for(int x=0; x < modelUploadChartOfAccounts.getRowCount(); x++){
						Boolean isSelected = (Boolean) modelUploadChartOfAccounts.getValueAt(x, 0);

						if(isSelected ){
							
							String acct_name = (String) modelUploadChartOfAccounts.getValueAt(x, 1);
							String balance = (String) modelUploadChartOfAccounts.getValueAt(x, 2);
							String acct_type = (String) modelUploadChartOfAccounts.getValueAt(x, 3);
							String parent_account = (String) modelUploadChartOfAccounts.getValueAt(x, 6);
							
							String level= "";
							
							if(parent_account.equals("NONE")) {
								level = "1";
							}else {
								level = "4";
							}
							if(acct_name.equals("") == false) {
								String SQL = "SELECT sp_upload_chart_of_accounts('"+level+"', '"+parent_account+"', '"+acct_name+"', '"+acct_type+"', '"+balance+"', 'A', '', '"+UserInfo.EmployeeCode+"');";
								pgSelect db = new pgSelect();
								db.select(SQL);
								FncSystem.out("Display save Accomplishment", SQL);
							}
						}
					}
					FncGlobal.stopProgress();
				}
			}).start();
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Chart of Account succesfully uploaded.", "Save", JOptionPane.INFORMATION_MESSAGE);
			tabCenter.removeAll();
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

		//TO DELETE EMPTY COLUMNS
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

		/*		//TO DELETE EMPTY ROWS
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
		for(int r=1;r<rowCount;r++){
			for(int c=0;c<colCount;c++){
				rowValue[0][0] = false;
				rowValue[r][0] = true;
				rowValue[r][c+1] = sheet.getValueAt(c, r);
				//System.out.println("Dumaan dito sa 5");
				//System.out.printf("%s\n", rowValue[r][c+1]);
			}
			//}
		}

		modelUploadChartOfAccounts = new DefaultTableModel(rowValue, columnNames){
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

		final _JTableMain tblAddUnit = new _JTableMain(modelUploadChartOfAccounts);
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
		for(int x = (modelUploadChartOfAccounts.getRowCount() -1); x >= 0; x--){
			Object value = modelUploadChartOfAccounts.getValueAt(x, 1);
			if(value == null){
				modelUploadChartOfAccounts.removeRow(x);
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
		//evaluate();
		tblAddUnit.packAll();

		return tblAddUnit;
	}
	
	public static String ProjectSQL(){
		return "SELECT proj_id, proj_name, proj_alias FROM mf_project\n" + 
				"ORDER BY proj_name";
	}
}
