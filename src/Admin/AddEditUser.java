package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelAddEditUser;
import DateChooser._JDateChooser;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class AddEditUser extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -8903978691250085540L;

	//Dimension frameSize = new Dimension(790, 500);
	static String title = "Add/Edit User";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;

	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JLabel lblEmployee;
	private JLabel lblEmpRank;
	private JLabel lblEntity;
	private JLabel lblCompany;
	private JLabel lblDivision;
	private JLabel lblDepartment;
	private JLabel lblDateHired;

	private JPanel pnlNorthEast;
	private JPanel pnlEmployee;
	private _JXTextField txtEmpCode;
	private JLabel lblSearchName;
	private _JXTextField txtSearch;

	private JPanel pnlEmpRank;
	private _JLookup lookupEmpRank;
	private _JXTextField txtEmpRank;

	private JPanel pnlEntity;
	private _JLookup lookupEntity;
	private _JXTextField txtEntityName;

	private JPanel pnlCompany;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;

	private JPanel pnlDivision;
	private _JLookup lookupDivision;
	private _JXTextField txtDivision;

	private JPanel pnlDepartment;
	private _JLookup lookupDepartment;
	private _JXTextField txtDepartment;

	private JPanel pnlDateHired;
	private _JDateChooser dteHired;
	private JCheckBox chkIncludeInactive;
	private JLabel lblEmpStatus;
	private JComboBox cmbEmpStat;

	private JPanel pnlCenter;
	private modelAddEditUser modelAddUser;
	private _JTableMain tblAddEdiUser;
	private JScrollPane scrollAddEditUser;
	private JList rowHeaderAddEditUser;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnInactive;
	private JButton btnCancel;

	/*public AddEditUser() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddEditUser(String title) {
		super(title, false, true, false, true);
		initGUI();
	}*/
	
	public AddEditUser() {
		initGUI();
	}

	public AddEditUser(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public AddEditUser(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public AddEditUser(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		//this.setTitle(title);
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(0, 0));
		//this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(EMPTY_BORDER);
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("User Details"));
				{
					pnlNorthWest = new JPanel(new GridLayout(7, 1, 3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						lblEmployee = new JLabel("Employee ID");
						pnlNorthWest.add(lblEmployee);
					}
					{
						lblEmpRank = new JLabel("Employee Rank");
						pnlNorthWest.add(lblEmpRank);
					}
					{
						lblEntity = new JLabel("Entity");
						pnlNorthWest.add(lblEntity);
					}
					{
						lblCompany = new JLabel("Company");
						pnlNorthWest.add(lblCompany);
					}
					{
						lblDivision = new JLabel("Division");
						pnlNorthWest.add(lblDivision);
					}
					{
						lblDepartment = new JLabel("Department");
						pnlNorthWest.add(lblDepartment);
					}
					{
						lblDateHired = new JLabel("Date Hired");
						pnlNorthWest.add(lblDateHired);
					}
				}
				{
					pnlNorthEast = new JPanel(new GridLayout(7, 1, 3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
					{
						pnlEmployee = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlEmployee);
						{
							txtEmpCode = new _JXTextField("Emp Code");
							pnlEmployee.add(txtEmpCode, BorderLayout.WEST);
							txtEmpCode.setPreferredSize(new Dimension(100, 0));
							txtEmpCode.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblSearchName = new JLabel("Search Name", JLabel.TRAILING);
							pnlEmployee.add(lblSearchName, BorderLayout.CENTER);
						}
						{
							txtSearch = new _JXTextField("Search Name");
							pnlEmployee.add(txtSearch, BorderLayout.EAST);
							txtSearch.setPreferredSize(new Dimension(300, 0));
							txtSearch.setEditable(true);
							txtSearch.setHorizontalAlignment(JXTextField.CENTER);
							txtSearch.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {	
									displayEmployees();
								}
							});
						}
					}
					{
						pnlEmpRank = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlEmpRank);
						{
							lookupEmpRank = new _JLookup(null, "Select Employee Rank", 0);
							pnlEmpRank.add(lookupEmpRank, BorderLayout.WEST);
							lookupEmpRank.setPreferredSize(new Dimension(100, 0));
							lookupEmpRank.setLookupSQL(sqlEmpRank());
							lookupEmpRank.setEditable(false);
							lookupEmpRank.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String emp_rank_desc = (String) data[1];
										txtEmpRank.setText(emp_rank_desc);
									}
								}
							});
						}
						{
							txtEmpRank = new _JXTextField("Employment Rank");
							pnlEmpRank.add(txtEmpRank);
						}
					}
					{
						pnlEntity = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlEntity);
						{
							lookupEntity = new _JLookup(null, "Select Employee", 0);
							pnlEntity.add(lookupEntity, BorderLayout.WEST);
							lookupEntity.setPreferredSize(new Dimension(100, 0));
							lookupEntity.setLookupSQL(sqlEntity());
							lookupEntity.setEditable(false);
							lookupEntity.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String entity_name = (String) data[1];
										txtEntityName.setText(entity_name);
									}
								}
							});
						}
						{
							txtEntityName = new _JXTextField("Entity Name");
							pnlEntity.add(txtEntityName);
						}
					}
					{
						pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlCompany);
						{
							lookupCompany = new _JLookup(null, "Select Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(100, 0));
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.setEditable(false);
							lookupCompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String company_name = (String) data[1];
										txtCompany.setText(company_name);
										//lookupDivision.setValue(null);
										//txtDivision.setText("");
										//lookupDepartment.setValue(null);
										//txtDepartment.setText("");
										
										lookupDivision.setLookupSQL(sqlDivision((String) data[0]));
									}
								}
							});
						}
						{
							txtCompany = new _JXTextField("Company Name");
							pnlCompany.add(txtCompany);
						}
					}
					{
						pnlDivision = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlDivision);
						{
							lookupDivision = new _JLookup(null, "Select Division", 0);
							pnlDivision.add(lookupDivision, BorderLayout.WEST);
							lookupDivision.setPreferredSize(new Dimension(100, 0));
							lookupDivision.setEditable(false);
							lookupDivision.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String div_name = (String) data[1];
										txtDivision.setText(div_name);
										lookupDepartment.setLookupSQL(sqlDepartment(lookupCompany.getValue(), (String) data[0]));
									}
								}
							});
						}
						{
							txtDivision = new _JXTextField("Division Name");
							pnlDivision.add(txtDivision);
						}
					}
					{
						pnlDepartment = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlDepartment);
						{
							lookupDepartment = new _JLookup(null, "Select Department", 0, "Please Select Division First");
							pnlDepartment.add(lookupDepartment, BorderLayout.WEST);
							lookupDepartment.setPreferredSize(new Dimension(100, 0));
							lookupDepartment.setEditable(false);
							lookupDepartment.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String dept_name = (String) data[1];
										txtDepartment.setText(dept_name);
									}
								}
							});
						}
						{
							txtDepartment = new _JXTextField("Department Name");
							pnlDepartment.add(txtDepartment);
						}
					}
					{
						pnlDateHired = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlDateHired);
						pnlDateHired.setLayout(new GridLayout(1, 4, 3, 3));
						{
							dteHired = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlDateHired.add(dteHired, BorderLayout.WEST);
							dteHired.setPreferredSize(new Dimension(100, 0));
							dteHired.setEnabled(false);
						}
						{
							chkIncludeInactive = new JCheckBox("Include Inactive Employees");
							pnlDateHired.add(chkIncludeInactive, BorderLayout.CENTER);
							//chkIncludeInactive.setEnabled(false);
							chkIncludeInactive.setEnabled(true);
							chkIncludeInactive.addItemListener(new ItemListener() {

								public void itemStateChanged(ItemEvent arg0) {
									displayEmployees();
									txtSearch.setText("");
								}
							});
						}
						{
							lblEmpStatus = new JLabel("Status", JLabel.TRAILING);
							pnlDateHired.add(lblEmpStatus);
						}
						{
							cmbEmpStat = new JComboBox(new DefaultComboBoxModel(listEmpStatus()));
							pnlDateHired.add(cmbEmpStat);
							cmbEmpStat.setPreferredSize(new Dimension(150, 0));
							cmbEmpStat.setEnabled(false);
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("User List"));
				{
					scrollAddEditUser = new JScrollPane();
					pnlCenter.add(scrollAddEditUser, BorderLayout.CENTER);
					scrollAddEditUser.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelAddUser = new modelAddEditUser();
						tblAddEdiUser = new _JTableMain(modelAddUser);
						scrollAddEditUser.setViewportView(tblAddEdiUser);
						tblAddEdiUser.hideColumns("Rank ID", "Entity ID", "Co. ID", "Div. ID", "Dept. ID", "Date Hired");

						modelAddUser.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {

								((DefaultListModel)rowHeaderAddEditUser.getModel()).addElement(modelAddUser.getRowCount());

								if(modelAddUser.getRowCount() == 0){
									rowHeaderAddEditUser.setModel(new DefaultListModel());
								}
							}
						});

						tblAddEdiUser.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								if(!arg0.getValueIsAdjusting()){
									try {
										if(tblAddEdiUser.getSelectedRows().length == 1){
											int row = tblAddEdiUser.getSelectedRow();
											String emp_code = (String) modelAddUser.getValueAt(row, 0);
											String emp_status = (String) modelAddUser.getValueAt(row, 1);
											String emp_rank_code = (String) modelAddUser.getValueAt(row, 2);
											String emp_rank_desc = (String) modelAddUser.getValueAt(row, 3);
											String entity_id = (String) modelAddUser.getValueAt(row, 4);
											String entity_name = (String) modelAddUser.getValueAt(row, 5);
											String co_id = (String) modelAddUser.getValueAt(row, 6);
											String company_name = (String) modelAddUser.getValueAt(row, 7);
											String div_id = (String) modelAddUser.getValueAt(row, 8);
											String div_name = (String) modelAddUser.getValueAt(row, 9);
											String dept_id = (String) modelAddUser.getValueAt(row, 10);
											String dept_name = (String) modelAddUser.getValueAt(row, 11);
											Date date_hired = (Date) modelAddUser.getValueAt(row, 12);

											txtEmpCode.setText(emp_code);
											cmbEmpStat.setSelectedItem(emp_status);
											lookupEmpRank.setValue(emp_rank_code);
											txtEmpRank.setText(emp_rank_desc);
											lookupEntity.setValue(entity_id);
											txtEntityName.setText(entity_name);
											lookupCompany.setValue(co_id);
											txtCompany.setText(company_name);
											lookupDivision.setValue(div_id);
											txtDivision.setText(div_name);
											lookupDepartment.setValue(dept_id);
											txtDepartment.setText(dept_name);
											dteHired.setDate(date_hired);
											btnState(true, true, false, false);
										}else{
											btnState(true, false, false, false);
										}
									} catch (ArrayIndexOutOfBoundsException e) { }
								}
							}
						});
					}
					{
						rowHeaderAddEditUser = tblAddEdiUser.getRowHeader();
						rowHeaderAddEditUser.setModel(new DefaultListModel());
						scrollAddEditUser.setRowHeaderView(rowHeaderAddEditUser);
						scrollAddEditUser.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlSouth.setLayout(new GridLayout(1, 4, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setBorder(EMPTY_BORDER);
				{
					btnNew = new JButton("New");
					pnlSouth.add(btnNew);
					btnNew.setActionCommand(btnNew.getText());
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand(btnEdit.getText());
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				/*{
				btnInactive = new JButton("Inactive");
				pnlSouth.add(btnInactive);
				btnInactive.setActionCommand(btnInactive.getText());
				btnInactive.setMnemonic(KeyEvent.VK_I);
				btnInactive.addActionListener(this);
				}*/
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		displayEmployees();
		btnState(true, false, false, false);
		//this.setComponentsEditable(pnlNorth, false);
	}//END OF INIT GUI

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private Object [] listEmpStatus(){
		//String sql = "SELECT FORMAT('%s - %s' ,TRIM(empstatus_code),TRIM(empstatus_desc)) from mf_employee_status";
		String sql = "SELECT TRIM(empstatus_desc) from mf_employee_status";
		pgSelect db = new pgSelect();
		db.select(sql);
		ArrayList<Object> listEmpStatus = new ArrayList<Object>();
		if(db.isNotNull()){
			listEmpStatus.add("");
			for(int x=0; x < db.getRowCount(); x++){
				listEmpStatus.add(db.getResult()[x][0]);
			}
			return listEmpStatus.toArray();
		}else{
			return null;
		}
	}

	private void displayEmployees(){
		String search = txtSearch.getText().toUpperCase().replace("'", "");
		try{
			modelAddUser.clear();
		} catch (ArrayIndexOutOfBoundsException e){}
		String sql = "select TRIM(a.emp_code) as \"Emp Code\", TRIM(g.empstatus_desc) as \"Emp Status\",\n" + 
				"TRIM(a.emp_rank) as \"Emp Rank\", TRIM(f.level_desc) as \"Emp Rank Desc\",\n" + 
				"TRIM(a.entity_id) as \"Entity ID\", TRIM(b.entity_name) as \"Entity Name\", \n" + 
				"TRIM(a.co_id) as \"Company ID\", TRIM(c.company_name) as \"Company Name\",\n" + 
				"TRIM(a.div_code) as \"Div. ID\", TRIM(d.division_name) as \"Div. Name\",\n" + 
				"TRIM(a.dept_code) as \"Dept.ID\", TRIM(e.dept_name) as \"Dept. Name\", \n"+
				"a.hired_date as \"Date Hired\"" + 
				"from em_employee a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"left join mf_company c on c.co_id = a.co_id\n" + 
				"left join mf_division d on d.division_code = a.div_code\n" + 
				"left join mf_department e on e.dept_code = a.dept_code\n" + 
				"left join mf_rank_level f on f.level_code = a.emp_rank\n" + 
				"left join mf_employee_status g on g.empstatus_code = a.emp_status\n"+
				"where b.entity_id != '' \n";

		if(chkIncludeInactive.isSelected()){
			sql = sql + "and a.emp_status in ('A', 'C', 'E', 'J', 'P', 'R', 'I') ";
		}else{
			sql = sql + "and a.emp_status in ('A', 'C', 'E', 'J', 'P', 'R')";
		}
		if(txtSearch.getText().isEmpty() == false){
			sql = sql + "and b.first_name like '%"+search+"%' \n"+
					"or b.last_name like '%"+search+"%' order by b.entity_name \n";
		}else{
			sql = sql + "order by b.entity_name";
		}

		pgSelect db = new pgSelect();
		db.select(sql);
		System.out.println("Display Employees "+ sql);
		if(db.isNotNull()){
			for (int x = 0; x<db.getRowCount(); x++){
				modelAddUser.addRow(db.getResult()[x]);
			}
			scrollAddEditUser.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAddEdiUser.getRowCount())));
			tblAddEdiUser.packAll();
		}
	}

	private String sqlEmpRank(){
		return "SELECT TRIM(level_code) as \"ID\", TRIM(level_desc) as \"Rank Desc\" FROM mf_rank_level;";
	}

	private String sqlEntity(){
		return "select TRIM(entity_id) as \"Entity ID\", TRIM(entity_name) as \"Entity Name\" \n"+
				"from rf_entity where entity_kind = 'I'\n" + 
				"and entity_id not in (select entity_id from em_employee);";
	}

	private String sqlCompany(){
		return "select TRIM(co_id) as \"Company ID\", \n" + 
				"TRIM(company_name) as \"Company Name\", \n" + 
				"TRIM(company_alias) as \"Company Alias\" \n" + 
				"from mf_company \n" + 
				"order by company_name";
	}

	private String sqlDivision(String co_id){
		return "select TRIM(division_code) as \"ID\", \n"+
				"TRIM(division_name) as \"Division Name\" \n"+
				"from mf_division where co_id = '"+co_id+"'";
	}

	private String sqlDepartment(String co_id ,String div_id){
		return "select dept_code as \"ID\", \n" + 
				"dept_name as \"Department Name\", \n" + 
				"dept_alias as \"Department Alias\" \n" + 
				"from mf_department \n" + 
				"where co_id = '"+co_id+"' \n"+
				"and division_code='"+div_id+"'";
	}

	private boolean isEmpExisting(String emp_code){
		pgSelect db = new pgSelect();
		String sql = "select * from em_employee where emp_code = '"+emp_code+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean toSave(){
		if(txtEmpCode.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Employee Code"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(cmbEmpStat.getSelectedItem().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Employee Status"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupEmpRank.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Employee Rank"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupEntity.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Employee"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupCompany.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Company"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupDivision.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Division"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if(lookupDepartment.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Department"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		return true;
	}

	private void newEmployee(){
		cancelEmployee();
		btnState(false, false, true, true);
		//XXX EDIT THIS this.setComponentsEditable(pnlNorth, true);
		txtEmpRank.setEditable(false);
		txtEntityName.setEditable(false);
		txtCompany.setEditable(false);
		txtDivision.setEditable(false);
		txtDepartment.setEditable(false);

		cmbEmpStat.setEnabled(true);
		dteHired.setEnabled(true);
		chkIncludeInactive.setEnabled(false);
		txtSearch.setEditable(false);
		tblAddEdiUser.setEnabled(false);
		rowHeaderAddEditUser.setEnabled(false);
		tblAddEdiUser.clearSelection();
	}

	private void editEmployee(){
		if(tblAddEdiUser.getSelectedRows().length == 1){

			btnState(false, false, true, true);
			cmbEmpStat.setEnabled(true);
			txtEmpCode.setEditable(false);
			lookupEntity.setEditable(false);
			lookupCompany.setEditable(true);
			lookupDepartment.setEditable(true);
			lookupDivision.setEditable(true);
			lookupEmpRank.setEditable(true);
			dteHired.setEnabled(false);
			chkIncludeInactive.setEnabled(true);
			tblAddEdiUser.setEnabled(false);
			rowHeaderAddEditUser.setEnabled(false);
			txtSearch.setEditable(false);
			chkIncludeInactive.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollAddEditUser, "Please select one row to edit from the table");
			tblAddEdiUser.clearSelection();
		}
	}

	private void addEmployee(){
		String emp_code = txtEmpCode.getText();
		String entity_id = lookupEntity.getValue();
		String emp_stat = (String) cmbEmpStat.getSelectedItem();
		String emp_rank = lookupEmpRank.getValue();
		String co_id = lookupCompany.getValue();
		String div_id = lookupDivision.getValue();
		String dept_id = lookupDepartment.getValue();
		Date date_hired = dteHired.getDate();

		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO em_employee(\n" + 
				"emp_code, entity_id, emp_status, hired_date, regular_date, \n" + 
				"terminate_date, emp_rank, emp_pos, co_id, div_code, dept_code, \n" + 
				"created_by, date_created, edit_by, date_edited)\n" + 
				"VALUES ('"+emp_code+"', '"+entity_id+"', \n"+
				"(select empstatus_code from mf_employee_status where empstatus_desc = '"+emp_stat+"'), \n"+
				"'"+date_hired+"', null, null, '"+emp_rank+"', null, '"+co_id+"', '"+div_id+"', '"+dept_id+"', \n" + 
				"'"+UserInfo.EmployeeCode+"', CURRENT_DATE, null, null);";
		db.executeUpdate(sql, true);
		db.commit();
	}

	private void updateEmployee(){
		String emp_code = txtEmpCode.getText();
		String entity_id = lookupEntity.getValue();
		String emp_stat = (String) cmbEmpStat.getSelectedItem();
		String emp_rank = lookupEmpRank.getValue();
		String co_id = lookupCompany.getValue();
		String div_id = lookupDivision.getValue();
		String dept_id = lookupDepartment.getValue();
		Date date_hired = dteHired.getDate();

		pgUpdate db = new pgUpdate();
		String sql = "UPDATE em_employee SET entity_id='"+entity_id+"', \n"+
				"emp_status= (select empstatus_code from mf_employee_status where empstatus_desc = '"+emp_stat+"'), \n"+
				"emp_rank='"+emp_rank+"', co_id='"+co_id+"', \n" + 
				"div_code= '"+div_id+"', dept_code= '"+dept_id+"', edit_by= '"+UserInfo.EmployeeCode+"', date_edited= CURRENT_DATE\n" + 
				"WHERE emp_code = '"+emp_code+"';\n";
		db.executeUpdate(sql, true);
		db.commit();
	}

	private void cancelEmployee(){
		txtEmpCode.setText("");
		lookupEmpRank.setValue(null);
		txtEmpRank.setText("");
		lookupEntity.setValue(null);
		txtEntityName.setText("");
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupDivision.setValue(null);
		txtDivision.setText("");
		lookupDepartment.setValue(null);
		txtDepartment.setText("");
		btnState(true, false, false, false);
		//XXX EDIT THIS this.setComponentsEditable(pnlNorth, false);
		
		cmbEmpStat.setEnabled(false);
		cmbEmpStat.setSelectedItem("");
		dteHired.setDate(null);
		dteHired.setEnabled(false);
		tblAddEdiUser.clearSelection();
		chkIncludeInactive.setSelected(false);
		chkIncludeInactive.setEnabled(false);
		txtSearch.setText("");
		displayEmployees();
		tblAddEdiUser.setEnabled(true);
		rowHeaderAddEditUser.setEnabled(true);
		txtSearch.setEditable(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("New")){
			newEmployee();
		}
		if(actionCommand.equals("Edit")){
			editEmployee();
		}
		
		if(actionCommand.equals("Save")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save New User?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					if(isEmpExisting(txtEmpCode.getText())){
						updateEmployee();
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Employee Info has been updated");
					}else{
						addEmployee();
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Employee Info has been saved");
					}
					cancelEmployee();
					displayEmployees();
				}
			}
		}
		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to cancel editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancelEmployee();
			}
		}
	}
}
