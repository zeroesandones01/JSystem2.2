/**
 * 
 */
package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import tablemodel.modelDivisionList;
import Functions.FncSystem;
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
public class AddDivision extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = 3999391366722661712L;

	Dimension frameSize = new Dimension(500, 500);// 510, 250
	static String title = "Add Division";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlDivisionWest;
	private JLabel lblCompany;
	private JLabel lblDivisionID;
	private JLabel lblDivisionName;

	private JPanel pnlDivisionEast;
	private JPanel pnlCompany;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;

	private JPanel pnlDivisionCode;
	private _JXTextField txtDivisionCode;
	private JLabel lblDivisionAlias;
	private _JXTextField txtDivisionAlias;

	private _JXTextField txtDivisionName;

	private JPanel pnlCenter;
	private _JTableMain tblDivisionList;
	private JScrollPane scrollDivisionList;
	private modelDivisionList modelDivision;
	private JList rowHeaderDivisionList;

	private JPanel pnlSouth;

	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	/*public AddDivision() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddDivision(String title) {
		super(title, false, true, false, true);
		initGUI();
	}*/
	
	public AddDivision() {
		initGUI();
	}

	public AddDivision(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public AddDivision(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public AddDivision(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		//this.setTitle(title);
		//this.setSize(frameSize);
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(0, 0));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(EMPTY_BORDER);
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Division Details"));
				{
					pnlDivisionWest = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlNorth.add(pnlDivisionWest, BorderLayout.WEST);

					{
						lblDivisionID = new JLabel("Division ID");
						pnlDivisionWest.add(lblDivisionID);
					}
					{
						lblDivisionName = new JLabel("*Division Name");
						pnlDivisionWest.add(lblDivisionName);
					}
					{
						lblCompany = new JLabel("*Company");
						pnlDivisionWest.add(lblCompany);
					}
				}
				{
					pnlDivisionEast = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlNorth.add(pnlDivisionEast, BorderLayout.CENTER);
					{
						pnlDivisionCode = new JPanel(new BorderLayout());
						pnlDivisionEast.add(pnlDivisionCode);
						{
							txtDivisionCode = new _JXTextField("ID");
							pnlDivisionCode.add(txtDivisionCode, BorderLayout.WEST);
							txtDivisionCode.setPreferredSize(new Dimension(75, 0));
							txtDivisionCode.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblDivisionAlias = new JLabel("*Division Alias", JLabel.TRAILING);
							pnlDivisionCode.add(lblDivisionAlias, BorderLayout.CENTER);
						}
						{
							txtDivisionAlias = new _JXTextField("Alias");
							pnlDivisionCode.add(txtDivisionAlias, BorderLayout.EAST);
							txtDivisionAlias.setPreferredSize(new Dimension(75, 0));
						}
					}
					{
						txtDivisionName = new _JXTextField("Division Name");
						pnlDivisionEast.add(txtDivisionName);
					}
					{
						pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlDivisionEast.add(pnlCompany);
						{
							lookupCompany = new _JLookup(null, "Select Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(75, 0));
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if (data != null){
										String company_name = (String) data[1];
										txtCompany.setText(company_name);
									}
								}
							});
						}
						{
							txtCompany = new _JXTextField("Company Name");
							pnlCompany.add(txtCompany);
						}
					}
				}
				{
					pnlCenter = new JPanel(new BorderLayout(5, 5));
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Division List"));
					{
						scrollDivisionList = new JScrollPane();
						pnlCenter.add(scrollDivisionList, BorderLayout.CENTER);
						scrollDivisionList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						{
							modelDivision = new modelDivisionList();
							tblDivisionList = new _JTableMain(modelDivision);
							scrollDivisionList.setViewportView(tblDivisionList);
							tblDivisionList.hideColumns("CO ID", "Company Name");

							modelDivision.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {

									((DefaultListModel)rowHeaderDivisionList.getModel()).addElement(modelDivision.getRowCount());

									if(modelDivision.getRowCount() == 0){
										rowHeaderDivisionList.setModel(new DefaultListModel());
									}
								}
							});

							tblDivisionList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent arg0) {
									if(!arg0.getValueIsAdjusting()){
										try {
											if(tblDivisionList.getSelectedRows().length == 1){
												int row = tblDivisionList.getSelectedRow();

												String div_code = (String) modelDivision.getValueAt(row, 0);
												String div_name = (String) modelDivision.getValueAt(row, 1);
												String div_alias = (String) modelDivision.getValueAt(row, 2);
												String co_id = (String) modelDivision.getValueAt(row, 3);
												String company_name = (String) modelDivision.getValueAt(row, 4);

												lookupCompany.setValue(co_id);
												txtCompany.setText(company_name);
												txtDivisionCode.setText(div_code);
												txtDivisionAlias.setText(div_alias);
												txtDivisionName.setText(div_name);

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
							rowHeaderDivisionList = tblDivisionList.getRowHeader();
							rowHeaderDivisionList.setModel(new DefaultListModel());
							scrollDivisionList.setRowHeaderView(rowHeaderDivisionList);
							scrollDivisionList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
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
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		displayDivisionList();
		btnState(true, false, false, false);
		//this.setComponentsEditable(pnlDivisionEast, false);
	}//END OF INIT GUI


	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displayDivisionList(){
		try{
			modelDivision.clear();
		}catch (ArrayIndexOutOfBoundsException e) {}

		String sql = "SELECT TRIM(a.division_code), TRIM(a.division_name), TRIM(a.division_alias),\n" + 
				"TRIM(b.co_id), b.company_name\n" + 
				"FROM mf_division a\n" + 
				"left JOIN mf_company b on b.co_id = a.co_id \n"+
				"ORDER BY a.division_name";
		pgSelect db = new pgSelect();

		db.select(sql);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelDivision.addRow(db.getResult()[x]);
			}
			scrollDivisionList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDivisionList.getRowCount())));
			tblDivisionList.packAll();
		}
	}

	private String sqlCompany(){
		return "select TRIM(co_id) as \"Company ID\", \n" + 
				"TRIM(company_name) as \"Company Name\", \n" + 
				"TRIM(company_alias) as \"Company Alias\" \n" + 
				"from mf_company \n" + 
				"order by company_name";
	}

	private boolean isDivisionExisting(String div_id){
		pgSelect db = new pgSelect();
		String sql = "select division_code from mf_division where trim(division_code) = '"+div_id+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean isDivisionNameExisting(String div_name){
		pgSelect db = new pgSelect();
		String sql = "select division_code from mf_division where trim(division_name) = '"+div_name+"' ";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean toSave(){
		if(lookupCompany.getValue() == null || lookupCompany.getValue().isEmpty()){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Company"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtDivisionAlias.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Division Alias"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtDivisionName.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Division Name"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void newDivision(){
		cancelDivision();

		pgSelect db = new pgSelect();
		String sql = "select max(division_code::integer) + 1 from mf_division"; 
		db.select(sql);

		FncSystem.out("Display new Division", sql);
		txtDivisionCode.setText(Integer.toString((Integer)db.getResult()[0][0]));
		//this.setComponentsEditable(pnlDivisionEast, true);
		txtDivisionCode.setEditable(false);
		tblDivisionList.setEnabled(false);
		txtCompany.setEditable(false);
		btnState(false, false, true, true);
		tblDivisionList.clearSelection();
		rowHeaderDivisionList.setEnabled(false);
	}

	private void editDivision(){
		if(tblDivisionList.getSelectedRows().length ==1){
			//this.setComponentsEditable(pnlDivisionEast, true);
			txtDivisionCode.setEditable(false);
			btnState(false, false, true, true);
			tblDivisionList.setEnabled(false);
			rowHeaderDivisionList.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollDivisionList, "Please select one row to edit from the table");
			tblDivisionList.clearSelection();
		}
	}

	private void addDivision(String div_code, String div_name, String div_alias, String co_id){
		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO mf_division(\n" + 
				"division_code, division_name, division_alias, div_head_code, \n" + 
				"head_count, co_id, ams_dept_code, status_id, created_by, date_created, \n" + 
				"edited_by, date_edited)\n" + 
				"VALUES ('"+div_code+"', '"+div_name+"', '"+div_alias+"', null, \n" + 
				"null, '"+co_id+"', null, 'A', '"+UserInfo.EmployeeCode+"', CURRENT_DATE, \n" + 
				"null, null);\n";
		db.executeUpdate(sql, true);
		db.commit();
		cancelDivision();
		displayDivisionList();
	}

	private void updateDivision(String div_name, String div_alias, String co_id, String div_code){
		pgUpdate db = new pgUpdate();
		String sql = "UPDATE mf_division\n" + 
				"SET division_name='"+div_name+"', division_alias= '"+div_alias+"', \n" + 
				"co_id = '"+co_id+"', edited_by= '"+UserInfo.EmployeeCode+"', date_edited=CURRENT_DATE\n" + 
				"WHERE division_code = '"+div_code+"';";
		db.executeUpdate(sql, true);
		db.commit();
		cancelDivision();
		displayDivisionList();
	}

	private void cancelDivision(){
		lookupCompany.setValue(null);
		txtCompany.setText("");
		txtDivisionCode.setText("");
		txtDivisionAlias.setText("");
		txtDivisionName.setText("");

		//this.setComponentsEditable(pnlDivisionEast, false);
		btnState(true, false, false, false);
		tblDivisionList.setEnabled(true);
		rowHeaderDivisionList.setEnabled(true);
		tblDivisionList.clearSelection();
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("New")){
			newDivision();
		}
		if(actionCommand.equals("Edit")){
			editDivision();
		}
		
		if(actionCommand.equals("Save")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save New Division?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					if(isDivisionExisting(txtDivisionCode.getText())){
						updateDivision(txtDivisionName.getText().replace("'", "").toUpperCase(), txtDivisionAlias.getText().toUpperCase(), lookupCompany.getValue(), txtDivisionCode.getText());
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Division has been updated");
					}else{
						if(isDivisionNameExisting(txtDivisionName.getText().replace("'", "").toUpperCase())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Division is Already Existing please input another name");
							txtDivisionName.setText("");
						}else{
							addDivision(txtDivisionCode.getText(), txtDivisionName.getText().replace("'", "").toUpperCase(), txtDivisionAlias.getText().toUpperCase() , lookupCompany.getValue());
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Division has been saved");
						}
					}
				}
			}
		}
		
		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Cancel editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancelDivision();
			}
		}
	}

}
