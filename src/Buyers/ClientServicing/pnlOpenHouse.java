package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Dialogs.dlgOpenHouseImport;
import Functions.FncGlobal;
import Functions.FncTables;
import components._JTableMain;

/**
 * @author Alvin Gonzales
 */
public class pnlOpenHouse extends JPanel implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9026187270713207683L;

	JScrollPane scrollImports;
	_JTableMain tblImports;
	modelImports modelImports;
	JList rowheaderImports;

	JScrollPane scrollUsers;
	_JTableMain tblUsers;
	modelUsers modelUsers;
	JList rowheaderUsers;

	JComboBox<String> cmbSearchPath;

	public pnlOpenHouse() {
		initGUI();
	}

	public pnlOpenHouse(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOpenHouse(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOpenHouse(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		//this.setLayout(new BorderLayout(5, 5));
		this.setLayout(new GridLayout(2, 1, 0, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
		{
			JPanel pnlImport = new JPanel(new BorderLayout(0, 5));
			this.add(pnlImport);
			pnlImport.setBorder(components.JTBorderFactory.createTitleBorder("Import/Export"));
			{
				scrollImports = new JScrollPane();
				pnlImport.add(scrollImports, BorderLayout.CENTER);
				scrollImports.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollImports.setPreferredSize(new Dimension(0, 100));
				scrollImports.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						tblImports.clearSelection();
					}
				});
				{
					modelImports = new modelImports();
					modelImports.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == TableModelEvent.DELETE){
								rowheaderImports.setModel(new DefaultListModel());
							}
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowheaderImports.getModel()).addElement(modelImports.getRowCount());
							}
						}
					});

					tblImports = new _JTableMain(modelImports);
					scrollImports.setViewportView(tblImports);
				}
				{
					rowheaderImports = tblImports.getRowHeader();
					rowheaderImports.setModel(new DefaultListModel());
					scrollImports.setRowHeaderView(rowheaderImports);
					scrollImports.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				JPanel pnlSouth = new JPanel(new BorderLayout());
				pnlImport.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JPanel pnlNav = new JPanel(new GridLayout(1, 2, 5, 0));
					pnlSouth.add(pnlNav, BorderLayout.EAST);
					pnlNav.setPreferredSize(new Dimension(205, 30));
					{
						JButton btnImport = new JButton("Import");
						pnlNav.add(btnImport, BorderLayout.EAST);
						btnImport.addActionListener(this);
					}
					{
						JButton btnExport = new JButton("Export");
						pnlNav.add(btnExport, BorderLayout.EAST);
						btnExport.addActionListener(this);
					}
				}
			}
		}
		{
			JPanel pnlExport = new JPanel(new BorderLayout(0, 5));
			this.add(pnlExport);
			pnlExport.setBorder(components.JTBorderFactory.createTitleBorder("Users"));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout());
				pnlExport.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 25));
				{
					JPanel pnlSearchPath = new JPanel(new BorderLayout(5, 0));
					pnlNorth.add(pnlSearchPath, BorderLayout.WEST);
					pnlSearchPath.setPreferredSize(new Dimension(300, 0));
					{
						JLabel lblSearchPath = new JLabel("Search Path");
						pnlSearchPath.add(lblSearchPath, BorderLayout.WEST);
					}
					{
						cmbSearchPath = new JComboBox<String>();
						pnlSearchPath.add(cmbSearchPath, BorderLayout.CENTER);
					}
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout());
				pnlExport.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollUsers = new JScrollPane();
					pnlCenter.add(scrollUsers, BorderLayout.CENTER);
					scrollUsers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollUsers.setPreferredSize(new Dimension(0, 100));
					scrollUsers.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblUsers.clearSelection();
						}
					});
					{
						modelUsers = new modelUsers();
						modelUsers.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == TableModelEvent.DELETE){
									rowheaderUsers.setModel(new DefaultListModel());
								}
								if(e.getType() == TableModelEvent.INSERT){
									((DefaultListModel)rowheaderUsers.getModel()).addElement(modelUsers.getRowCount());
								}
							}
						});

						tblUsers = new _JTableMain(modelUsers);
						scrollUsers.setViewportView(tblUsers);
						tblUsers.hideColumns("OID");
					}
					{
						rowheaderUsers = tblUsers.getRowHeader();
						rowheaderUsers.setModel(new DefaultListModel());
						scrollUsers.setRowHeaderView(rowheaderUsers);
						scrollUsers.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel(new BorderLayout());
				pnlExport.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JPanel pnlNav = new JPanel(new GridLayout(1, 2, 5, 0));
					pnlSouth.add(pnlNav, BorderLayout.EAST);
					pnlNav.setPreferredSize(new Dimension(205, 30));
					{
						JButton btnSave = new JButton("Save");
						pnlNav.add(btnSave, BorderLayout.EAST);
						btnSave.addActionListener(this);
					}
					{
						JButton btnReset = new JButton("Reset");
						pnlNav.add(btnReset, BorderLayout.EAST);
						btnReset.addActionListener(this);
					}
				}
			}
		}

		displayImports();
		displayUsers();
	}

	private void displayImports() {
		modelImports.clear();
		ArrayList<String> listSchemas = new ArrayList<String>();

		pgSelect db = new pgSelect();
		db.select("SELECT * FROM view_open_house_imported_schemas();");
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelImports.addRow(db.getResult()[x]);

				listSchemas.add((String) db.getResult()[x][1]);
			}
		}

		scrollImports.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblImports.getRowCount())));
		tblImports.packAll();

		Collections.reverse(listSchemas);
		cmbSearchPath.setModel(new DefaultComboBoxModel(listSchemas.toArray()));
	}

	private void displayUsers() {
		modelUsers.clear();

		pgSelect db = new pgSelect();
		db.select("SELECT FALSE, * FROM view_open_house_users();");
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelUsers.addRow(db.getResult()[x]);
			}
		}

		scrollUsers.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblUsers.getRowCount())));
		tblUsers.packAll();
	}

	private void saveUsers(String title, Boolean reset) {
		String search_path = (String) cmbSearchPath.getSelectedItem();

		ArrayList<String> listUsername = new ArrayList<String>();
		for(int x=0; x < tblUsers.getRowCount(); x++){
			int row = tblUsers.convertRowIndexToModel(x);
			Boolean selected = (Boolean) modelUsers.getValueAt(row, 0);
			String username = (String) modelUsers.getValueAt(row, 3);
			if(selected){
				System.out.printf("Username: %s%n", username);
				listUsername.add(String.format("'%s'", username));
			}
		}

		if(listUsername.size() > 0){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				String SQL = String.format("SELECT sp_save_open_house_users(ARRAY[%s]::VARCHAR[], '%s', %s);", listUsername.toString().replaceAll("\\[|\\]", ""), search_path, reset);
				System.out.printf("%n%s%n", SQL);

				pgSelect db = new pgSelect();
				db.select(SQL, title, "postgres", "postgres", true);
				
				displayUsers();
			}
		}else{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select user(s) to save.", title, JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("Import")){
			dlgOpenHouseImport dlg_import = new dlgOpenHouseImport(FncGlobal.homeMDI, "Import", ModalityType.APPLICATION_MODAL);
			dlg_import.setLocationRelativeTo(null);
			dlg_import.setVisible(true);

			displayImports();
			displayUsers();
		}

		if(action.equals("Save")){
			saveUsers(action, false);
		}

		if(action.equals("Reset")){
			saveUsers(action, true);
		}
	}


	public class modelImports extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		public modelImports() {
			initThis();
		}

		public modelImports(int rowCount, int columnCount) {
			super(rowCount, columnCount);
		}

		public modelImports(Vector columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public modelImports(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public modelImports(Vector data, Vector columnNames) {
			super(data, columnNames);
		}

		public modelImports(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		private void initThis() {
			setColumnIdentifiers(new String[] { "OID", "Name", "Description" });
		}

		Class[] types = new Class[] { String.class, String.class, Object.class };

		public Class getColumnClass(int columnIndex) {
			return types[columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		public void clear() {
			FncTables.clearTable(this);
		}

	}

	public class modelUsers extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		public modelUsers() {
			initThis();
		}

		public modelUsers(int rowCount, int columnCount) {
			super(rowCount, columnCount);
		}

		public modelUsers(Vector columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public modelUsers(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public modelUsers(Vector data, Vector columnNames) {
			super(data, columnNames);
		}

		public modelUsers(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		private void initThis() {
			setColumnIdentifiers(new String[] { "Select", "ID", "Name", "Username", "Search Path", "OID" });
		}

		Class[] types = new Class[] { Boolean.class, String.class, Object.class, Object.class, Object.class, String.class };

		Boolean[] editable = new Boolean[]{ true, false, false, false, false, false };

		public Class getColumnClass(int columnIndex) {
			return types[columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return editable[columnIndex];
		}

		public void clear() {
			FncTables.clearTable(this);
		}

	}

}
