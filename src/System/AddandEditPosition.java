package System;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_AddEditPosition_EmpList;
import tablemodel.model_AddEditPosition_PositionList;
import tablemodel.model_AddEditPosition_PrivilegeList;

public class AddandEditPosition extends _JInternalFrame implements _GUI, MouseListener {
	
	
	private static final long serialVersionUID = 1L;
	static String title = "Add and Edit Position";
	static Dimension SIZE = new Dimension (1000,1000);
	
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthLabel;
	private JLabel lblDivision;
	private JLabel lblDepartment;
	private JPanel pnlNorthComponents;
	private _JLookup lookupDivision;
	private JLabel lblDiv;
	private JLabel lblDept;
	private _JLookup lookupDepartment;
	private JPanel pnlCenter;
	private JPanel pnlDivisionfields;
	private JPanel pnlDepartmentfields;
	private JLabel lblEmployeeID;
	private JLabel lblEntityID;
	private JLabel lblEmployeeRank;
	private JTextField txtEmployeeID;
	private JPanel pnlEmployeeID;
	private JPanel pnlEntityID;
	private _JXTextField txtEntityID;
	private JPanel pnlEmployeeRank;
	private _JXTextField txtEmployeeRank;
	private _JXTextField txtEntityName;
	private _JXTextField txtEmployeePosition;
	private JPanel pnlEmployeeListTable;
	private JScrollPane scrollEmployeeListTable;
	private model_AddEditPosition_EmpList modelEmployeeList;
	private _JTableMain tblEmployeeList;
	private JList rowHeaderEmployeeList;
	private JPanel pnlSouth;
	private JButton saveButton;
	private JButton editButton;
	private JButton addButton;
	private JButton refreshButton;
	private JScrollPane scrollPositionList;
	private _JTableMain tblPositionList;
	private JList rowHeaderPositionList;
	private JScrollPane scrollPrivilegeList;
	private model_AddEditPosition_PrivilegeList modelPrivilegeList;
	private _JTableMain tblPrivilegeList;
	private JList rowHeaderPrivilegeList;
	private JPanel pnlPositionList;
	private JPanel pnlPrivilegeList;
	
	private String div_code;
	private String div_name;
	private String dept_code;
	private String dept_name;
	private JPanel pnlSouthTables;
	private JPanel pnlSouthButton;
	private JPanel pnlSearch;
	private JLabel lblSearch;
	private _JXTextField txtSearch;
	private pgUpdate db;
	private String to_do;
	private model_AddEditPosition_PositionList modelPositionList;



	public AddandEditPosition() {
		super(title, true, true,true,true);
		initGUI();
	
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new Dimension (500,500));
		this.setBounds(0,0,600,600);
		{
			pnlMain = new JPanel (new BorderLayout (5,5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			{                                                                                                                                                                                                                                                                                
				pnlNorth = new JPanel (new BorderLayout (5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
				//pnlNorth.setBorder(BorderFactory.createEmptyBorder(0,3,3,3));
				pnlNorth.setPreferredSize(new Dimension(0,150));
				{
					pnlNorthLabel = new JPanel (new GridLayout(5,1,5,5));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					{
						lblDivision = new JLabel ("Division:");
						pnlNorthLabel.add(lblDivision);
					}
					{
						lblDepartment = new JLabel ("Department:");
						pnlNorthLabel.add(lblDepartment);
					}
					{
						lblEmployeeID = new JLabel ("Employee ID:");
						pnlNorthLabel.add(lblEmployeeID);
					}
					{
						lblEntityID =  new JLabel ("Entity ID:");
						pnlNorthLabel.add(lblEntityID);
					}
					{
						lblEmployeeRank = new JLabel ("Employee Rank:");
						pnlNorthLabel.add(lblEmployeeRank);
					}
					{
						pnlNorthComponents = new JPanel (new GridLayout (5,1,5,5));
						pnlNorth.add(pnlNorthComponents, BorderLayout.CENTER);
						{
							pnlDivisionfields = new JPanel (new BorderLayout(5,5));
							pnlNorthComponents.add(pnlDivisionfields, BorderLayout.CENTER);
							{
								lookupDivision = new _JLookup(null, "Division", 0, 2);
								pnlDivisionfields.add(lookupDivision, BorderLayout.WEST);
								lookupDivision.setLookupSQL(sqlDivision());
								lookupDivision.setReturnColumn(0);
								lookupDivision.setPreferredSize(new Dimension(50,0));
								lookupDivision.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											div_code = (String) data [0];
											div_name = (String) data [1];
											
											FncSystem.out("Display SQL for description:", lookupDivision.getLookupSQL());
											lookupDepartment.setLookupSQL(sqlDepartment(div_code));
											lblDiv.setText(String.format("[ %s ]", div_name));
											lookupDepartment.setEnabled(true);
										}
									}
								});
							}
							{
								lblDiv = new JLabel ("[ ]");
								pnlDivisionfields.add(lblDiv, BorderLayout.CENTER);
							}
							{
								pnlDepartmentfields = new JPanel (new BorderLayout(5,5));
								pnlNorthComponents.add(pnlDepartmentfields, BorderLayout.CENTER);
								{
									lookupDepartment = new _JLookup();
									pnlDepartmentfields.add(lookupDepartment, BorderLayout.WEST);
									lookupDepartment.setReturnColumn(0);
									lookupDepartment.setEnabled(false);
									lookupDepartment.setPreferredSize(new Dimension(50,0));
									lookupDepartment.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null){
												dept_code = (String) data[0];
												dept_name = (String) data [1];
												
												FncSystem.out("Display SQL for description:", lookupDepartment.getLookupSQL());
												lblDept.setText(String.format("[ %s ]", dept_name));
												
												displayEmployeeList(modelEmployeeList, rowHeaderEmployeeList, div_code, dept_code);
												
												txtSearch.setEditable(true);
												txtEmployeeID.setText("");
												txtEntityID.setText("");
												txtEntityName.setText("");
												txtEmployeeRank.setText("");
												txtEmployeePosition.setText("");
												
												lookupDivision.setEnabled(false);
												lookupDepartment.setEnabled(false);
											}
										}
									});
								}
								{
									lblDept = new JLabel ("[ ]");
									pnlDepartmentfields.add(lblDept, BorderLayout.CENTER);
								}
							}
							{
								pnlEmployeeID = new JPanel (new BorderLayout(5,5));
								pnlNorthComponents.add(pnlEmployeeID, BorderLayout.CENTER);
								{
									txtEmployeeID = new _JXTextField ("Employee ID");
									pnlEmployeeID.add(txtEmployeeID, BorderLayout.WEST);
									txtEmployeeID.setPreferredSize(new Dimension(100,0));
									txtEmployeeID.setHorizontalAlignment(JXTextField.CENTER);
									txtEmployeeID.setEditable(false);
								}
							}
							{
								pnlEntityID = new JPanel (new BorderLayout (5,5));
								pnlNorthComponents.add(pnlEntityID, BorderLayout.CENTER);
								{
									txtEntityID = new _JXTextField ("Entity ID");
									pnlEntityID.add(txtEntityID, BorderLayout.WEST);
									txtEntityID.setPreferredSize(new Dimension(100,0));
									txtEntityID.setHorizontalAlignment(JXTextField.CENTER);
									txtEntityID.setEditable(false);
								}
								{
									txtEntityName = new _JXTextField("Entity Name");
									pnlEntityID.add(txtEntityName, BorderLayout.CENTER);
									txtEntityName.setEditable(false);
								}
							}
							{
								pnlEmployeeRank = new JPanel (new BorderLayout (5,5));
								pnlNorthComponents.add(pnlEmployeeRank, BorderLayout.CENTER);
								{
									txtEmployeeRank = new _JXTextField("Employee Rank");
									pnlEmployeeRank.add(txtEmployeeRank, BorderLayout.WEST);
									txtEmployeeRank.setPreferredSize(new Dimension (100,0));
									txtEmployeeRank.setHorizontalAlignment(JXTextField.CENTER);
									txtEmployeeRank.setEditable(false);
								}
								{
									txtEmployeePosition = new _JXTextField("Employee Position");
									pnlEmployeeRank.add(txtEmployeePosition, BorderLayout.CENTER);
									txtEmployeePosition.setEditable(false);
								}
							}
						}
					}
				}
				{
					pnlCenter = new JPanel (new BorderLayout(7,7));
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					{
						pnlSearch = new JPanel (new BorderLayout(5,5));
						pnlCenter.add(pnlSearch, BorderLayout.NORTH);
						pnlSearch.setBorder(BorderFactory.createEmptyBorder(0,0,0,3));
						pnlSearch.setPreferredSize(new Dimension(0,25));
						{
							lblSearch = new JLabel ("*Search:");
							pnlSearch.add(lblSearch, BorderLayout.WEST);
							Font font = new Font ("SanSerif", Font.BOLD, 13);
							lblSearch.setFont(font);
						}
						{
							txtSearch = new _JXTextField("Search Name");
							pnlSearch.add(txtSearch, BorderLayout.CENTER);
							txtSearch.addKeyListener(new KeyAdapter() {
								public void keyReleased (KeyEvent e){
									searchEmployeeName(modelEmployeeList, rowHeaderEmployeeList, txtSearch.getText(), div_code, dept_code);
								}
							});
						}

					}
					{
						pnlEmployeeListTable = new JPanel (new BorderLayout (5,5));
						pnlCenter.add(pnlEmployeeListTable, BorderLayout.CENTER);
						pnlEmployeeListTable.setPreferredSize(new Dimension(0,100));
						pnlEmployeeListTable.setBorder(JTBorderFactory.createTitleBorder("List of Employees"));
						{
							scrollEmployeeListTable = new JScrollPane();
							pnlEmployeeListTable.add(scrollEmployeeListTable);
							scrollEmployeeListTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							modelEmployeeList = new model_AddEditPosition_EmpList();
							tblEmployeeList = new _JTableMain(modelEmployeeList);
							
							scrollEmployeeListTable.setViewportView(tblEmployeeList);
							tblEmployeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblEmployeeList.setSortable(false);
							tblEmployeeList.getColumn(0).setPreferredWidth(100);
							tblEmployeeList.getColumn(1).setPreferredWidth(100);
							tblEmployeeList.getColumn(2).setPreferredWidth(100);
							tblEmployeeList.getColumn(3).setPreferredWidth(100);
							tblEmployeeList.getColumn(4).setPreferredWidth(100);
							tblEmployeeList.getColumn(5).setPreferredWidth(100);
							tblEmployeeList.getColumn(6).setPreferredWidth(100);
							tblEmployeeList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if(e.getValueIsAdjusting()){
										displayEmployeeDetails();
										
									}
								}
							});
							tblEmployeeList.addKeyListener(new KeyAdapter() {
								public void keyReleased (KeyEvent e){
									displayEmployeeDetails();
								}
								
								public void keyPressed (KeyEvent e){
									
								}
							});
						}
						{
							rowHeaderEmployeeList = tblEmployeeList.getRowHeader();
							rowHeaderEmployeeList.setModel(new DefaultListModel());
							scrollEmployeeListTable.setRowHeaderView(rowHeaderEmployeeList);
							scrollEmployeeListTable.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
						}
					}
				}
				{
					pnlSouth = new JPanel (new BorderLayout(5,5));
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					//pnlSouth.setPreferredSize(new Dimension(0, 150));
					{
						pnlSouthTables = new JPanel (new GridLayout(1,2,5,5));
						pnlSouth.add(pnlSouthTables, BorderLayout.NORTH);
						pnlSouthTables.setPreferredSize(new Dimension(0,150));
							{
								pnlPositionList = new JPanel (new BorderLayout (5,5));
								pnlSouthTables.add(pnlPositionList, BorderLayout.WEST);
								pnlPositionList.setBorder(BorderFactory.createTitledBorder("List of Position"));
								//pnlPositionList.setBorder(JTBorderFactory.createTitleBorder("List of Position"));
								{
									scrollPositionList = new JScrollPane();
									pnlPositionList.add(scrollPositionList);
									scrollPositionList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								}
								{
									modelPositionList = new model_AddEditPosition_PositionList();
									tblPositionList = new _JTableMain (modelPositionList);

									scrollPositionList.setViewportView(tblPositionList);
									tblPositionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblPositionList.setSortable(false);
									tblPositionList.getColumn(0).setPreferredWidth(80);
									tblPositionList.getColumn(1).setPreferredWidth(120);
									tblPositionList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(e.getValueIsAdjusting()){
												if(tblPositionList.getSelectedRows().length>0){
													int rw = tblPositionList.getSelectedRow();
													String pos_name = tblPositionList.getValueAt(rw, 1).toString();
													
													displayPrivileges(modelPrivilegeList, rowHeaderPrivilegeList, div_code, dept_code, pos_name);
													
													saveButton.setEnabled(true);
												}
											}
										}
									});
								}
								{
									rowHeaderPositionList = tblPositionList.getRowHeader();
									rowHeaderPositionList.setModel(new DefaultListModel());
									scrollPositionList.setRowHeaderView(rowHeaderPositionList);
									scrollPositionList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
								}
							}
							{
								pnlPrivilegeList = new JPanel (new BorderLayout (5,5));
								pnlSouthTables.add(pnlPrivilegeList, BorderLayout.EAST);
								pnlPrivilegeList.setBorder(BorderFactory.createTitledBorder("List of Privileges"));
								//pnlPrivilegeList.setBorder(JTBorderFactory.createTitleBorder("List of Modules"));
								{
									scrollPrivilegeList = new JScrollPane();
									pnlPrivilegeList.add(scrollPrivilegeList);
									scrollPrivilegeList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								}
								{
									modelPrivilegeList = new model_AddEditPosition_PrivilegeList();
									tblPrivilegeList = new _JTableMain (modelPrivilegeList);

									scrollPrivilegeList.setViewportView(tblPrivilegeList);
									tblPrivilegeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblPrivilegeList.setSortable(false);
									tblPrivilegeList.setEnabled(false);
									tblPrivilegeList.hideColumns("Parent");
									tblPrivilegeList.hideColumns("Module");
									tblPrivilegeList.getColumn(1).setPreferredWidth(80);
									tblPrivilegeList.getColumn(2).setPreferredWidth(120);
								}
								{
									rowHeaderPrivilegeList = tblPrivilegeList.getRowHeader();
									rowHeaderPrivilegeList.setModel(new DefaultListModel());
									scrollPrivilegeList.setRowHeaderView(rowHeaderPrivilegeList);
									scrollPrivilegeList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
								}
							}
						}
					{
						pnlSouthButton = new JPanel (new GridLayout(1,4,5,5));
						pnlSouth.add(pnlSouthButton, BorderLayout.SOUTH);
						pnlSouthButton.setPreferredSize(new Dimension(0,30));
						{
							saveButton = new JButton ("Save");
							pnlSouthButton.add(saveButton);
							saveButton.setActionCommand("Save");
							saveButton.addActionListener(this);
							saveButton.setEnabled(false);
						}
						{
							editButton = new JButton ("Edit");
							pnlSouthButton.add(editButton);
							editButton.setEnabled(false);
							editButton.setActionCommand("Edit");
							editButton.addActionListener(this);
						}
						{
							addButton = new JButton ("Add");
							pnlSouthButton.add(addButton);
							addButton.setEnabled(false);
							addButton.setActionCommand("Add");
							addButton.addActionListener(this);
						}
						{
							refreshButton = new JButton ("Refresh");
							pnlSouthButton.add(refreshButton);
							refreshButton.setActionCommand("Refresh");
							refreshButton.addActionListener(this);
						}
					}
				}
			}
		}
		
	}
	
	private String sqlDivision(){
		String sql = "Select\n" + 
				"division_code as \"ID\",\n" + 
				"division_name as \"Division Name\",\n" + 
				"division_alias as \"Alias\"\n" + 
				"from mf_division\n" + 
				"where status_id = 'A' ";

		return sql;
	}
	
	private String sqlDepartment(String div_code){
		String sql = "select \n" + 
				"dept_code as \"ID\",\n" + 
				"dept_name as \"Department Name\",\n" + 
				"dept_alias as \"Alias\"\n" + 
				"from mf_department\n" + 
				"where status_id = 'A'\n" +
				"and division_code = '"+div_code+"' ";

		return sql;
	}
	
	private void searchEmployeeName(DefaultTableModel modelMain, JList rowHeader, String str, String div_code, String dept_code){
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		String sql = "select\n" + 
				"trim(a.emp_code) as emp_code,\n" + 
				"trim(a.entity_id) as entity_id,\n" + 
				"trim(b.entity_name) as entity_name,\n" + 
				"trim(c.empstatus_desc) as empstatus_desc,\n" + 
				"trim(emp_pos) as emp_pos,\n" + 
				"trim(emp_rank) as emp_rank,\n" + 
				"trim(a.div_code) as div_code,\n" + 
				"trim(a.dept_code) as dept_code\n" + 
				"from em_employee a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"left join mf_employee_status c on a.emp_status = c.empstatus_code\n" + 
				"where b.entity_name ~* '"+str.toUpperCase()+"'\n" +
				"and a.div_code = '"+div_code+"' and a.dept_code = '"+dept_code+"' and not c.empstatus_desc = 'Inactive' and not c.empstatus_desc = 'End of Contract'";
		
		FncSystem.out("Display SQL for description", sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());	
			}
		}
	}
	
	public void actionPerformed (ActionEvent e){
		
		if(e.getActionCommand().equals("Save")) { Save();}
		
		if(e.getActionCommand().equals("Edit")) { Edit();}
		
		if(e.getActionCommand().equals("Add")) { Add();}
		
		if(e.getActionCommand().equals("Refresh")) { Refresh();}
		
	}
	
	private void Save(){
		db = new pgUpdate();
		if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			
			if (to_do.equals("change") || to_do.equals("add")){

				if(tblPositionList.getSelectedRows().length>0){

					int rw = tblEmployeeList.getSelectedRow();
					int rw1 = tblPositionList.getSelectedRow();
					String emp_code = modelEmployeeList.getValueAt(rw, 0).toString();
					String entity_id = modelEmployeeList.getValueAt(rw, 1).toString();
					String emp_pos = modelPositionList.getValueAt(rw1, 1).toString();
				
					//UPDATE POSITION OF THE EMPLOYEE........
					
					String sqlDetail =
							"UPDATE em_employee set\n" +
							"emp_pos = '"+emp_pos+"'\n" +
							"where emp_code = '"+emp_code+"' and entity_id = '"+entity_id+"' ";
					
					FncSystem.out("Sql for updating present position:", sqlDetail);
					db.executeUpdate(sqlDetail, false);
					
					//REMOVE ALL PRIVILEGES OF THE EMPLOYEE BASED ON PREVIOUS POSITION.........
					
					String sqlDetail1 =
							"DELETE FROM mf_privileges \n"+
							"where emp_code = '"+emp_code+"' ";
					
					FncSystem.out("Sql for removing previous position:", sqlDetail1);
					db.executeUpdate(sqlDetail1, false);
					
					//INSERTING THE NEW PRIVILEGES OF THE EMPLOYEE........
					
					for(int x = 0; x < modelPrivilegeList.getRowCount(); x++ ){

						String module = modelPrivilegeList.getValueAt(x, 4).toString();
						String privilege = modelPrivilegeList.getValueAt(x, 2).toString().replace("'", "''");
						String parent = modelPrivilegeList.getValueAt(x, 3).toString();
						Boolean isSelected = (Boolean) modelPrivilegeList.getValueAt(x, 0);

						if(isSelected){

							String sql =
									"INSERT into mf_privileges (module, privileges, parent, emp_code, in_charge) values(" + 
											"'"+module+"', \n" +
											"'"+privilege+"', \n" +
											"'"+parent+"', \n" +
											"'"+emp_code+"', \n" +
											"'"+UserInfo.EmployeeCode+"') ";

							FncSystem.out("SQL description:", sql);
							db.executeUpdate(sql, false);
						}
					}
				}
			}
			
			db.commit();
			JOptionPane.showMessageDialog(getContentPane(),"All data saved.","Information",JOptionPane.INFORMATION_MESSAGE);
			
			displayEmployeeList(modelEmployeeList, rowHeaderEmployeeList, div_code, dept_code);
			txtSearch.setEnabled(true);
			txtSearch.setEditable(true);
			tblEmployeeList.setEnabled(true);
			modelPositionList.clear();
			modelPrivilegeList.clear();
			saveButton.setEnabled(false);
		}
	}
	
	private void Edit(){
		
		to_do = "change";
		
		if(tblEmployeeList.getSelectedRows().length>0){
			int rw = tblEmployeeList.getSelectedRow();
			String emp_name = tblEmployeeList.getValueAt(rw, 2).toString();

			
			JOptionPane.showMessageDialog(getContentPane(), String.format("Please select a position for %s. ", emp_name), "Information", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		tblEmployeeList.setEnabled(false);
		tblEmployeeList.setEditable(false);
		txtSearch.setEnabled(false);
		editButton.setEnabled(false);
		
		displayPositionList(modelPositionList, rowHeaderPositionList, div_code, dept_code);
		
	}
	
	private void Add(){
		
		to_do = "add";
		
		if(tblEmployeeList.getSelectedRows().length>0){
			int rw = tblEmployeeList.getSelectedRow();
			String emp_name = tblEmployeeList.getValueAt(rw, 2).toString();

			
			JOptionPane.showMessageDialog(getContentPane(), String.format("Please add a position for %s. ", emp_name), "Information", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		tblEmployeeList.setEnabled(false);
		tblEmployeeList.setEditable(false);
		txtSearch.setEnabled(false);
		editButton.setEnabled(false);
		
		displayPositionList(modelPositionList, rowHeaderPositionList, div_code, dept_code);
		
	}
	
	private void Refresh(){
		
		modelEmployeeList.clear();
		modelPositionList.clear();
		modelPrivilegeList.clear();
		tblEmployeeList.setEnabled(true);
		tblEmployeeList.setEditable(true);
		lookupDivision.setText("");
		lookupDepartment.setText("");
		lookupDivision.setEnabled(true);
		lookupDepartment.setEnabled(false);
		lblDiv.setText("[ ]");
		lblDept.setText("[ ]");
		txtEmployeeID.setText("");
		txtEntityID.setText("");
		txtEntityName.setText("");
		txtEmployeeRank.setText("");
		txtEmployeePosition.setText("");
		txtSearch.setText("");
		txtSearch.setEditable(false);
		txtSearch.setEnabled(true);
		editButton.setEnabled(false);
		saveButton.setEnabled(false);

	}
	
	private void displayEmployeeList (DefaultTableModel modelMain, JList rowHeader, String div_code, String dept_code){
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		String sql = "select\n" + 
				"trim(a.emp_code) as emp_code,\n" + 
				"trim(a.entity_id) as entity_id,\n" + 
				"trim(b.entity_name) as entity_name,\n" + 
				"trim(c.empstatus_desc) as empstatus_desc,\n" + 
				"trim(emp_pos) as emp_pos,\n" + 
				"trim(emp_rank) as emp_rank,\n" + 
				"trim(a.div_code) as div_code,\n" + 
				"trim(a.dept_code) as dept_code\n" + 
				"from em_employee a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"left join mf_employee_status c on a.emp_status = c.empstatus_code\n" + 
				"where a.div_code = '"+div_code+"' and a.dept_code = '"+dept_code+"' and not c.empstatus_desc = 'Inactive' and not c.empstatus_desc = 'End of Contract'";
		
		FncSystem.out("Display SQL for description", sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}	
		}
		
	tblEmployeeList.packAll();
		
	}
	
	private void displayPositionList (DefaultTableModel modelMain, JList rowHeader, String div_code, String dept_code){

		FncTables.clearTable(modelMain); //Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel(); //Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); //Setting of DefaultListModel into rowHeader.

		String sql = "select distinct on (a.position_name)\n" + 
				"position_code,\n" + 
				"TRIM(a.position_name)\n" + 
				"from mf_position a\n" + 
				"left join em_employee b on a.position_name = b.emp_pos\n" + 
				"where b.div_code = '"+div_code+"' and b.dept_code = '"+dept_code+"' and status_id = 'A' ";

		FncSystem.out("Display sql for description", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}
		tblPositionList.packAll();
	}
	
	private void displayPrivileges (DefaultTableModel modelMain, JList rowHeader, String div_code, String dept_code, String pos_name){

		FncTables.clearTable(modelMain); //Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel(); //Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel); //Setting of DefaultListModel into rowHeader.

		String sql = "select distinct on (submodule_desc, submodule_id)\n" + 
				"true, \n" +
				"submodule_id,\n" +
				"submodule_desc,\n" + 
				"b.parent,\n" + 
				"b.module\n" + 
				"from mf_position_modules a\n" + 
				"left join mf_privileges b on a.submodule_desc = b.privileges\n" + 
				"where position_name = '"+pos_name+"'\n" +
				"and a.status_id = 'A' \n" +
				"and div_code = '"+div_code+"'\n" + 
				"and dept_code = '"+dept_code+"'\n" + 
				"order by submodule_id ";

		FncSystem.out("Display sql for description", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}
		
		tblPrivilegeList.packAll();
	}
	
	private void displayEmployeeDetails(){

		if(tblEmployeeList.getSelectedRows().length>0){
			int rw = tblEmployeeList.getSelectedRow();
			String emp_code = tblEmployeeList.getValueAt(rw, 0).toString();
			
			Object [] ch_ord = getEmployeeDetails(div_code, dept_code, emp_code);
			
			txtEmployeeID.setText((String) ch_ord [0]);
			txtEntityID.setText((String) ch_ord [1]);
			txtEntityName.setText((String) ch_ord [2]);
			txtEmployeePosition.setText((String) ch_ord [4]);
			if(ch_ord [4] == (null)){
				addButton.setEnabled(true);
				editButton.setEnabled(false);
				txtEmployeePosition.setText("n/a");
			}else{
				addButton.setEnabled(false);
				editButton.setEnabled(true);
			}
			txtEmployeeRank.setText((String) ch_ord [5]);
			//System.out.println("The value is: " + ch_ord[4]);
			
		}
	}
	
	private static Object [] getEmployeeDetails(String div_code, String dept_code, String emp_code){
		
		String sql = "select\n" + 
				"trim(a.emp_code) as emp_code,\n" + 
				"trim(a.entity_id) as entity_id,\n" + 
				"trim(b.entity_name) as entity_name,\n" + 
				"trim(c.empstatus_desc) as empstatus_desc,\n" + 
				"trim(emp_pos) as emp_pos,\n" + 
				"trim(emp_rank) as emp_rank,\n" + 
				"trim(a.div_code) as div_code,\n" + 
				"trim(a.dept_code) as dept_code\n" + 
				"from em_employee a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"left join mf_employee_status c on a.emp_status = c.empstatus_code\n" + 
				"where a.div_code = '"+div_code+"' and a.dept_code = '"+dept_code+"' and a.emp_code = '"+emp_code+"'\n" + 
				"and not c.empstatus_desc = 'Inactive' and not c.empstatus_desc = 'End of Contract'" ;
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){
			return db.getResult()[0];}
		else{
			return null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
