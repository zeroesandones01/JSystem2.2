/**
 * 
 */
package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import tablemodel.modelDepartmentList;
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
public class AddDepartment extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = 4433804011360323439L;

	//Dimension frameSize = new Dimension(500, 500);// 510, 250
	static String title = "Add Department";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblCompany;
	private JLabel lblDivision;
	private JLabel lblDepartmentID;
	private JLabel lblDepartmentName;

	private JPanel pnlNorthEast;
	private JPanel pnlCompany;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private JPanel pnlDivision;
	private _JLookup lookupDivision;
	private _JXTextField txtDivision;
	private JPanel pnlDeptID;
	private _JXTextField txtDeptID;
	private JLabel lblDeptAlias;
	private _JXTextField txtDeptAlias;
	private _JXTextField txtDeptName;

	private JPanel pnlCenter;
	private modelDepartmentList modelDeptList;
	private _JTableMain tblDepList;
	private JScrollPane scrollDeptList;
	private JList rowHeaderDeptList;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	/*public AddDepartment() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddDepartment(String title) {
		super(title, false, true, false, true);
		initGUI();
	}*/
	
	public AddDepartment() {
		initGUI();
	}

	public AddDepartment(LayoutManager layout) {
		super(layout);
	}

	public AddDepartment(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public AddDepartment(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		//this.setTitle(title);
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(0, 0));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(EMPTY_BORDER);
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Department Details"));
				{
					pnlNorthWest = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						lblDepartmentID = new JLabel("Dept. ID");
						pnlNorthWest.add(lblDepartmentID);
					}
					{
						lblDepartmentName = new JLabel("*Dept. Name");
						pnlNorthWest.add(lblDepartmentName);
					}
					{
						lblCompany = new JLabel("*Company");
						pnlNorthWest.add(lblCompany);
					}
					{
						lblDivision = new JLabel("*Division");
						pnlNorthWest.add(lblDivision);
					}
				}
				{
					pnlNorthEast = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);

					{
						pnlDeptID = new JPanel(new BorderLayout());
						pnlNorthEast.add(pnlDeptID);
						{
							txtDeptID = new _JXTextField("ID");
							pnlDeptID.add(txtDeptID, BorderLayout.WEST);
							txtDeptID.setPreferredSize(new Dimension(75, 0));
							txtDeptID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblDeptAlias = new JLabel("*Dept. Alias", JLabel.TRAILING);
							pnlDeptID.add(lblDeptAlias, BorderLayout.CENTER);
						}
						{
							txtDeptAlias = new _JXTextField("Alias");
							pnlDeptID.add(txtDeptAlias, BorderLayout.EAST);
							txtDeptAlias.setPreferredSize(new Dimension(75, 0));
						}
					}
					{
						txtDeptName = new _JXTextField("Dept. Name");
						pnlNorthEast.add(txtDeptName);
					}
					{
						pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlCompany);
						{
							lookupCompany = new _JLookup(null, "Select Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(75, 0));
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String company_name = (String) data[1];
										txtCompany.setText(company_name);
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
							lookupDivision = new _JLookup(null, "Select Division", 0, "Please Select Company First");
							pnlDivision.add(lookupDivision, BorderLayout.WEST);
							lookupDivision.setPreferredSize(new Dimension(75, 0));
							lookupDivision.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String div_name = (String) data[1];
										txtDivision.setText(div_name);
									}
								}
							});
						}
						{
							txtDivision = new _JXTextField("Division Name");
							pnlDivision.add(txtDivision);
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Department List"));
				{
					scrollDeptList = new JScrollPane();
					pnlCenter.add(scrollDeptList, BorderLayout.CENTER);
					scrollDeptList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelDeptList = new modelDepartmentList();
						tblDepList = new _JTableMain(modelDeptList);
						scrollDeptList.setViewportView(tblDepList);
						tblDepList.hideColumns("CO ID", "Company Name", "Div. ID", "Division Name");

						modelDeptList.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {

								((DefaultListModel)rowHeaderDeptList.getModel()).addElement(modelDeptList.getRowCount());

								if(modelDeptList.getRowCount() == 0){
									rowHeaderDeptList.setModel(new DefaultListModel());
								}
							}
						});

						tblDepList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								if(!arg0.getValueIsAdjusting()){
									try {
										if(tblDepList.getSelectedRows().length == 1){

											int row = tblDepList.getSelectedRow();
											String dept_id = (String) modelDeptList.getValueAt(row, 0);
											String dept_name = (String) modelDeptList.getValueAt(row, 1);
											String dept_alias = (String) modelDeptList.getValueAt(row, 2);
											String co_id = (String) modelDeptList.getValueAt(row, 3);
											String company_name = (String) modelDeptList.getValueAt(row, 4);
											String div_id = (String) modelDeptList.getValueAt(row, 5);
											String div_name = (String) modelDeptList.getValueAt(row, 6);

											lookupCompany.setValue(co_id);
											txtCompany.setText(company_name);
											lookupDivision.setValue(div_id);
											txtDivision.setText(div_name);
											txtDeptID.setText(dept_id);
											txtDeptName.setText(dept_name);
											txtDeptAlias.setText(dept_alias);

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
						rowHeaderDeptList = tblDepList.getRowHeader();
						rowHeaderDeptList.setModel(new DefaultListModel());
						scrollDeptList.setRowHeaderView(rowHeaderDeptList);
						scrollDeptList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlSouth.setLayout(new GridLayout(1, 4, 5, 5));
				this.add(pnlSouth, BorderLayout.SOUTH);
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
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		displayDepartmentList();
		btnState(true, false, false, false);
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, false);
	}//END OF INITGUI

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displayDepartmentList(){
		try{
			modelDeptList.clear();
		}catch (ArrayIndexOutOfBoundsException e) {}

		String sql = "select TRIM(a.dept_code), TRIM(a.dept_name), TRIM(a.dept_alias),\n" + 
				"TRIM(a.co_id), TRIM(b.company_name), TRIM(a.division_code), TRIM(c.division_name)\n" + 
				"from mf_department a\n" + 
				"left join mf_company b on b.co_id = a.co_id\n" + 
				"left join mf_division c on c.division_code = a.division_code \n"+
				"order by a.dept_name\n";

		pgSelect db = new pgSelect();

		db.select(sql);
		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				modelDeptList.addRow(db.getResult()[x]);
			}
			scrollDeptList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDepList.getRowCount())));
			tblDepList.packAll();
		}
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

	private boolean isDepartmentExisting(String dept_id){
		pgSelect db = new pgSelect();
		String sql = "select * from mf_department where dept_code = '"+dept_id+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean isDepartmentNameExisting(String dept_name){
		pgSelect db = new pgSelect();
		String sql = "select * from mf_department where dept_name = '"+dept_name+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean toSave(){
		if(lookupCompany.getValue() == null || lookupCompany.getValue().isEmpty()){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Company"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupDivision.getValue() == null || lookupDivision.getValue().isEmpty()){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Division"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtDeptAlias.getText().equals("") || txtDeptAlias.getText().isEmpty()){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Dept. Alias"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtDeptName.getText().equals("") || txtDeptName.getText().isEmpty()){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Dept. Name"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean checkRequiredFields(){
		String required_fields = "";
		Boolean toSave = true;
		
		if(txtDeptAlias.getText().equals("") || txtDeptAlias.getText().isEmpty()){
			required_fields = required_fields + "Dept. Alias\n";
			toSave = false;
		}
		if(txtDeptName.getText().equals("") || txtDeptName.getText().isEmpty()){
			required_fields = required_fields + "Department Name\n";
			toSave = false;
		}
		if(lookupCompany.getValue() == null || lookupCompany.getValue().isEmpty()){
			required_fields = required_fields + "Company \n";
			toSave = false;
		}
		if(lookupDivision.getValue() == null || lookupDivision.getValue().isEmpty()){
			required_fields = required_fields + "Division\n";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	private void newDepartment(){
		cancelDepartment();

		pgSelect db = new pgSelect();
		String sql = "select max(dept_code::integer) + 1 from mf_department";
		db.select(sql);

		txtDeptID.setText(Integer.toString((Integer)db.getResult()[0][0]));
		btnState(false, false, true, true);
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, true);
		txtDeptID.setEditable(false);
		txtCompany.setEditable(false);
		txtDivision.setEditable(false);
		tblDepList.setEnabled(false);
		tblDepList.clearSelection();
		rowHeaderDeptList.setEnabled(false);
		
		txtDeptAlias.setEditable(true);
		txtDeptName.setEditable(true);
		
	}

	private void editDepartment(){
		if(tblDepList.getSelectedRows().length == 1){
			//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, true);
			btnState(false, false, true, true);
			tblDepList.setEnabled(false);
			txtCompany.setEditable(false);
			txtDivision.setEditable(false);	
			rowHeaderDeptList.setEnabled(false);
			
			txtDeptAlias.setEditable(true);
			txtDeptName.setEditable(true);
		}else{
			JOptionPane.showMessageDialog(scrollDeptList, "Please select one row to edit");
			tblDepList.clearSelection();
		}
	}

	private void addDepartment(String dept_id, String dept_name, String dept_alias, String co_id, String div_id){
		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO mf_department(\n" + 
				"dept_code, dept_name, dept_alias, dept_head_code, dept_rank, \n" + 
				"co_id, division_code, head_count, ams_dept_code, ams_sect_code, \n" + 
				"status_id, created_by, date_created, edited_by, date_edited)\n" + 
				"VALUES ('"+dept_id+"', '"+dept_name+"', '"+dept_alias+"', null, null, \n" + 
				"'"+co_id+"', '"+div_id+"', null, null, null, \n" + 
				"'A', '"+UserInfo.EmployeeCode+"', CURRENT_DATE, null, null);";
		db.executeUpdate(sql, true);
		db.commit();
	}

	private void updateDepartment(String dept_name, String dept_alias, String co_id, String div_id, String dept_id){
		pgUpdate db = new pgUpdate();
		String sql = "UPDATE mf_department\n" + 
				"SET dept_name='"+dept_name+"', dept_alias='"+dept_alias+"', \n" + 
				"co_id='"+co_id+"', division_code='"+div_id+"',  edited_by='"+UserInfo.EmployeeCode+"', date_edited= CURRENT_DATE\n" + 
				"WHERE dept_code = '"+dept_id+"';";
		db.executeUpdate(sql, true);
		db.commit();
	}

	private void cancelDepartment(){
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupDivision.setValue(null);
		txtDivision.setText("");
		txtDeptID.setText("");
		txtDeptAlias.setText("");
		txtDeptName.setText("");

		tblDepList.clearSelection();
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, false);
		btnState(true, false, false, false);
		tblDepList.setEnabled(true);
		rowHeaderDeptList.setEnabled(true);
		
		txtDeptAlias.setEditable(false);
		txtDeptName.setEditable(false);
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("New")){
			newDepartment();
		}
		if(actionCommand.equals("Edit")){
			editDepartment();
		}
		if(actionCommand.equals("Save")){
			if(checkRequiredFields()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save New Department?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					if(isDepartmentExisting(txtDeptID.getText())){
						updateDepartment(txtDeptName.getText().replace("'", "").toUpperCase(), txtDeptAlias.getText().toUpperCase(), lookupCompany.getText(), lookupDivision.getValue(), txtDeptID.getText());
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Department Information has been updated");
					}else{
						if(isDepartmentNameExisting(txtDeptName.getText().replace("'", "").toUpperCase())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Department is Already Existing please input another name");
							txtDeptName.setText("");
						}else{
							addDepartment(txtDeptID.getText(), txtDeptName.getText().replace("'", "").toUpperCase(), txtDeptAlias.getText().toUpperCase(), lookupCompany.getValue(), lookupDivision.getValue());
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Department has been saved");
						}
						displayDepartmentList();
						cancelDepartment();
					}
				}
			}
		}
		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to cancel editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancelDepartment();
			}
		}
	}
}
