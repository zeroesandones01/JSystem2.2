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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
import tablemodel.modelAddProject;
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
public class AddProject extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -6251329076255632328L;
	Dimension frameSize = new Dimension(600, 500);// 510, 250
	static String title = "Add Project";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);


	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblCompany;
	private JLabel lblProjID;
	private JLabel lblProjName;
	private JLabel lblStreet;
	private JLabel lblBrgy;
	private JLabel lblProvince;
	private JLabel lblMunicipality;
	private JLabel lblVatable;

	private JPanel pnlNorthEast;

	private JPanel pnlCompany;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;

	private JPanel pnlProjectID;
	private _JXTextField txtProjID;
	private JLabel lblProjAlias;
	private _JXTextField txtProjectAlias;

	private JPanel pnlProject;
	private _JXTextField txtProjectName;

	private _JXTextField txtStreet;
	private _JXTextField txtBrgy;

	private JPanel pnlProvince;
	private JComboBox cmbProvince;

	private JPanel pnlCityMunicipality;
	private JComboBox cmbCityMunicipality;

	private JCheckBox chkIsVatable;

	private JPanel pnlCenter;
	private modelAddProject modelProject;
	private _JTableMain tblAddProject;
	private JScrollPane scrollAddProject;
	private JList rowHeaderAddProject;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	/*public AddProject() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddProject(String title) {
		super(title, false, true, false, true);
		initGUI();
	}*/
	
	public AddProject() {
		initGUI();
	}

	public AddProject(LayoutManager layout) {
		super(layout);
	}

	public AddProject(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public AddProject(LayoutManager layout,
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
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Project Details"));
				{
					pnlNorthWest = new JPanel(new GridLayout(8, 1, 3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						lblProjID = new JLabel("Proj. ID");
						pnlNorthWest.add(lblProjID);
					}
					{
						lblProjName = new JLabel("*Project Name");
						pnlNorthWest.add(lblProjName);
					}
					{
						lblCompany = new JLabel("*Company");
						pnlNorthWest.add(lblCompany);
					}
					{
						lblStreet = new JLabel("Street");
						pnlNorthWest.add(lblStreet);
					}
					{
						lblBrgy = new JLabel("Barangay");
						pnlNorthWest.add(lblBrgy);
					}
					{
						lblProvince = new JLabel("Province");
						pnlNorthWest.add(lblProvince);
					}
					{
						lblMunicipality = new JLabel("City/Municipality");
						pnlNorthWest.add(lblMunicipality);
					}
					{
						lblVatable = new JLabel("Vatable");
						pnlNorthWest.add(lblVatable);
					}
				}
				{
					pnlNorthEast = new JPanel(new GridLayout(8, 1, 3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
					{
						pnlProjectID = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlProjectID);
						{
							txtProjID = new _JXTextField("ID");
							pnlProjectID.add(txtProjID, BorderLayout.WEST);
							txtProjID.setPreferredSize(new Dimension(100, 0));
							txtProjID.setHorizontalAlignment(JXTextField.CENTER);

						}
						{
							lblProjAlias = new JLabel("*Proj. Alias", JLabel.TRAILING);
							pnlProjectID.add(lblProjAlias, BorderLayout.CENTER);
						}
						{
							txtProjectAlias = new _JXTextField("Alias");
							pnlProjectID.add(txtProjectAlias, BorderLayout.EAST);
							txtProjectAlias.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						pnlProject = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlProject);
						{
							txtProjectName = new _JXTextField("Project Name");
							pnlProject.add(txtProjectName);
						}
					}
					{
						pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlCompany);
						{
							lookupCompany = new _JLookup(null, "Select Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(50, 0));
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
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

					{
						txtStreet = new _JXTextField("Street Name");
						pnlNorthEast.add(txtStreet);
					}
					{
						txtBrgy = new _JXTextField("Barangay");
						pnlNorthEast.add(txtBrgy);
					}
					{
						pnlProvince = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlProvince);
						{
							cmbProvince = new JComboBox();
							pnlProvince.add(cmbProvince, BorderLayout.WEST);
							cmbProvince.setPreferredSize(new Dimension(200, 0));
							cmbProvince.setEnabled(false);
							cmbProvince.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent e) {
									try{
										String province_name = (String) ((JComboBox)e.getSource()).getSelectedItem();

										String province_id = getProvinceID(province_name);

										mapCityMunicipality(province_id);
										cmbCityMunicipality.setModel(new DefaultComboBoxModel(listCityMunicipality(province_id)));
									}catch (NullPointerException x) {}
								}
							});
						}
					}
					{
						pnlCityMunicipality = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlCityMunicipality);
						{
							cmbCityMunicipality = new JComboBox();
							pnlCityMunicipality.add(cmbCityMunicipality, BorderLayout.WEST);
							cmbCityMunicipality.setPreferredSize(new Dimension(200, 0));
							cmbCityMunicipality.setEnabled(false);
						}
					}
					{
						chkIsVatable = new JCheckBox();
						pnlNorthEast.add(chkIsVatable);
						chkIsVatable.setEnabled(false);
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Project List"));
				{
					scrollAddProject = new JScrollPane();
					pnlCenter.add(scrollAddProject, BorderLayout.CENTER);
					scrollAddProject.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelProject = new modelAddProject();
						tblAddProject = new _JTableMain(modelProject);
						scrollAddProject.setViewportView(tblAddProject);
						tblAddProject.hideColumns("Co. ID", "Street", "Barangay", "Vatable");

						modelProject.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {

								((DefaultListModel)rowHeaderAddProject.getModel()).addElement(modelProject.getRowCount());

								if(modelProject.getRowCount() == 0){
									rowHeaderAddProject.setModel(new DefaultListModel());
								}
							}
						});

						tblAddProject.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								if(!arg0.getValueIsAdjusting()){
									try {
										if(tblAddProject.getSelectedRows().length ==1){
											int row = tblAddProject.getSelectedRow();
											String proj_id = (String) modelProject.getValueAt(row, 0);
											String proj_name = (String) modelProject.getValueAt(row, 1);
											String proj_alias = (String) modelProject.getValueAt(row, 2);
											String co_id = (String) modelProject.getValueAt(row, 3);
											String company_name = (String) modelProject.getValueAt(row, 4);
											String street = (String) modelProject.getValueAt(row, 5);
											String brgy = (String) modelProject.getValueAt(row, 6);
											String prov_name = (String) modelProject.getValueAt(row, 7);
											String city_municipality_name = (String) modelProject.getValueAt(row, 8);
											Boolean vatable = (Boolean) modelProject.getValueAt(row, 9);

											txtProjID.setText(proj_id);
											txtProjectAlias.setText(proj_alias);
											txtProjectName.setText(proj_name);
											lookupCompany.setValue(co_id);
											txtCompany.setText(company_name);
											txtStreet.setText(street);
											txtBrgy.setText(brgy);
											cmbProvince.setSelectedItem(prov_name);
											cmbCityMunicipality.setSelectedItem(city_municipality_name);

											System.out.printf("Display Prov_name %s%n",prov_name);
											System.out.printf("Display City Municipality", city_municipality_name);
											//try{
											if(prov_name == null){
												cmbProvince.setSelectedItem("NONE");
												cmbCityMunicipality.setSelectedItem(city_municipality_name);
											}else{
												cmbProvince.setSelectedItem(prov_name);
												cmbCityMunicipality.setSelectedItem(city_municipality_name);
											}
											//}catch (NullPointerException e) {}

											chkIsVatable.setSelected(vatable);

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
						rowHeaderAddProject = tblAddProject.getRowHeader();
						rowHeaderAddProject.setModel(new DefaultListModel());
						scrollAddProject.setRowHeaderView(rowHeaderAddProject);
						scrollAddProject.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
					btnCancel.addActionListener(this);
				}
			}
		}
		displayProject();
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, false);
		btnState(true, false, false, false);
		mapProvinces();
		cmbProvince.setModel(new DefaultComboBoxModel(listProvince()));

		mapCityMunicipality("NONE");
		cmbCityMunicipality.setModel(new DefaultComboBoxModel(listCityMunicipality("NONE")));

	}//END OF INIT GUI

	private void btnState(Boolean sNew,Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displayProject(){
		try{
			modelProject.clear();
		}catch (ArrayIndexOutOfBoundsException e){}

		pgSelect db = new pgSelect();
		/*String sql = "select \n" + 
				"TRIM(a.proj_id) AS \"Proj. ID\", TRIM(a.proj_name) AS \"Proj. Name\", trim(a.proj_alias) as \"Proj. Alias\", \n" + 
				"TRIM(a.co_id) AS \"Co. ID\", TRIM(b.company_name) AS \"Company Name\",\n" + 
				"TRIM(a.street) AS \"Street\", TRIM(a.barangay) AS \"Barangay\",\n" + 
				"TRIM(c.province_id) AS \"Prov. ID\", TRIM(a.province) AS \"Province\",\n" + 
				"TRIM(d.municipality_id) AS \"Municipality ID\" ,TRIM(a.municipality) AS \"Municipality\",\n" + 
				"a.vatable AS \"Vatable\"\n" + 
				"FROM mf_project a \n" + 
				"LEFT JOIN mf_company b on b.co_id = a.co_id\n" + 
				"LEFT JOIN mf_province c on c.province_name = a.province\n" + 
				"LEFT JOIN mf_municipality d on d.municipality_name = a.municipality and d.province_id = c.province_id";*/
		/*	String sql = "select \n" + 
				"TRIM(a.proj_id) AS \"Proj. ID\", TRIM(a.proj_name) AS \"Proj. Name\", trim(a.proj_alias) as \"Proj. Alias\", \n" + 
				"TRIM(a.co_id) AS \"Co. ID\", TRIM(b.company_name) AS \"Company Name\",\n" + 
				"TRIM(a.street) AS \"Street\", TRIM(a.barangay) AS \"Barangay\",\n" + 
				"TRIM(a.province) AS \"Province\", TRIM(a.municipality) AS \"Municipality\",\n" + 
				"a.vatable AS \"Vatable\"\n" + 
				"FROM mf_project a \n" + 
				"LEFT JOIN mf_company b on b.co_id = a.co_id";*/

		String sql = "select \n" + 
				"TRIM(a.proj_id) AS \"Proj. ID\", TRIM(a.proj_name) AS \"Proj. Name\", trim(a.proj_alias) as \"Proj. Alias\", \n" + 
				"TRIM(a.co_id) AS \"Co. ID\", TRIM(b.company_name) AS \"Company Name\",\n" + 
				"TRIM(a.street) AS \"Street\", TRIM(a.barangay) AS \"Barangay\",\n" + 
				"TRIM(a.province) AS \"Province\", TRIM(a.municipality) AS \"Municipality\",\n" + 
				"a.vatable AS \"Vatable\"\n" + 
				"FROM mf_project a \n" + 
				"LEFT JOIN mf_company b on b.co_id = a.co_id";


		db.select(sql);
		FncSystem.out("Display Project List", sql);
		if(db.isNotNull()){
			for (int x =0; x<db.getRowCount(); x++){
				modelProject.addRow(db.getResult()[x]);
			}
			scrollAddProject.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAddProject.getRowCount())));
			tblAddProject.packAll();
		}
	}

	private String sqlCompany(){
		return "select TRIM(co_id) as \"Company ID\", \n" + 
				"TRIM(company_name) as \"Company Name\", \n" + 
				"TRIM(company_alias) as \"Company Alias\" \n" + 
				"from mf_company \n" + 
				"order by company_name";
	}

	//XXX pnl address

	private LinkedHashMap<String, String> mapProvinces() {
		LinkedHashMap<String, String> mapProvince = new LinkedHashMap<String, String>();

		String sql = "select trim(province_id), trim(province_name) from mf_province where status_id = 'A' order by province_name;";
		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display Province ID", sql);

		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				String province_id = (String) db.getResult()[x][0];
				String province_name = (String) db.getResult()[x][1];

				mapProvince.put(province_id, province_name);
			}
			return mapProvince;
		}else{
			return null;
		}
	}

	private Object[] listProvince() {
		ArrayList<String> arrayProvince = new ArrayList<String>();
		arrayProvince.add("NONE");

		for(Entry<String, String> entry : mapProvinces().entrySet()) {
			//String province_id = entry.getKey();
			String province_name = entry.getValue();

			arrayProvince.add(province_name);

			//System.out.printf("%s - %s%n", province_id, province_name);
		}
		return arrayProvince.toArray();
	}

	private String getProvinceID(String province_name) {
		String province_id = "";
		for(Entry<String, String> entry : mapProvinces().entrySet()){
			if(entry.getValue().equals(province_name)){
				province_id = entry.getKey();
			}
		}
		System.out.printf("Display province name %s%n", province_name);
		System.out.printf("Display province_id", province_id);
		if(province_name.equals("NONE")){
			return province_name;

		}else{
			return province_id;
		}
	}

	private LinkedHashMap<String, String> mapCityMunicipality(String province_id){
		LinkedHashMap<String, String>mapCM = new LinkedHashMap<String, String>();

		String sql = "select TRIM(a.city_id) AS city_id, TRIM(a.city_name) as city_name, TRIM(a.province_id) AS province_id, TRIM(b.province_name) AS province_name, a.cm\n" + 
				"from (select city_id, city_name, province_id, 'City' as cm from mf_city\n" + 
				"	union\n" + 
				"	select municipality_id, municipality_name, province_id, 'Municipality' from mf_municipality) a\n" + 
				"left join mf_province b on b.province_id = a.province_id\n" + 
				"where "+ (province_id.equals("NONE") ? "a.province_id is null":"a.province_id = '"+ province_id +"'") +"\n" + 
				"order by city_name;";

		FncSystem.out("Display HashMap", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x = 0; x < db.getRowCount(); x++){
				String city_id = (String) db.getResult()[x][0];
				String city_name = (String) db.getResult()[x][1];

				mapCM.put(city_id, city_name);
			}
			return mapCM;
		}else{
			return null;
		}
	}

	private Object[] listCityMunicipality(String province_id) {
		ArrayList<String> arrayCM = new ArrayList<String>();

		try {
			if(mapCityMunicipality(province_id) != null){
				for(Entry<String, String> entry : mapCityMunicipality(province_id).entrySet()) {
					//String city_id = entry.getKey();
					String city_name = entry.getValue();

					arrayCM.add(city_name.trim());
					//System.out.printf("%s - %s%n", city_id, city_name);
				}
			}else{
				arrayCM.add("NONE");
			}
		} catch (NullPointerException e) { }

		return arrayCM.toArray();
	}

	//PNL ADDRESS

	private boolean isProjExisting(String proj_id){
		pgSelect db = new pgSelect();
		String sql = "select * from mf_project where proj_id = '"+proj_id+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean isProjNameExisting(String proj_name){
		pgSelect db = new pgSelect();
		String sql = "select * from mf_project where proj_name = '"+proj_name+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean toSave(){
		if(lookupCompany.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Company"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtProjectName.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Project Name"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtProjectAlias.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Project Alias"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void newProject(){
		cancelProject();
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, true);
		chkIsVatable.setEnabled(true);
		btnState(false, false, true, true);

		pgSelect db = new pgSelect();
		String sql = "SELECT to_char(COALESCE(MAX(proj_id::INT), 14) + 1, 'FM000') FROM mf_project;";
		db.select(sql);
		txtProjID.setText((String) db.getResult()[0][0]);
		txtProjID.setEditable(false);
		txtCompany.setEditable(false);
		cmbProvince.setEnabled(true);
		cmbCityMunicipality.setEnabled(true);
		tblAddProject.setEnabled(false);
		tblAddProject.clearSelection();
		rowHeaderAddProject.setEnabled(false);
		
		txtProjectAlias.setEditable(true);
		txtProjectName.setEditable(true);
		txtStreet.setEditable(true);
		txtBrgy.setEditable(true);
		
	}

	private void editProject(){
		if(tblAddProject.getSelectedRows().length ==1){
			//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, true);
			btnState(false, false, true, true);
			txtProjID.setEditable(false);
			chkIsVatable.setEnabled(true);
			tblAddProject.setEnabled(false);
			txtCompany.setEditable(false);
			cmbProvince.setEnabled(true);
			cmbCityMunicipality.setEnabled(true);
			tblAddProject.setEnabled(false);
			rowHeaderAddProject.setEnabled(false);
			
			txtProjectAlias.setEditable(true);
			txtProjectName.setEditable(true);
			txtStreet.setEditable(true);
			txtBrgy.setEditable(true);
		}else{
			JOptionPane.showMessageDialog(scrollAddProject, "Please select one row to edit from the table");
			tblAddProject.clearSelection();
		}
	}

	private void addProject(){
		String proj_id = txtProjID.getText();
		String proj_name = txtProjectName.getText().toUpperCase();
		String proj_alias = txtProjectAlias.getText().toUpperCase();
		String co_id = lookupCompany.getValue();
		String street = txtStreet.getText().replace("'", "");
		String barangay = txtBrgy.getText();
		Boolean vatable = chkIsVatable.isSelected();

		String province = (String) cmbProvince.getSelectedItem();
		String city_municipality = (String) cmbCityMunicipality.getSelectedItem();

		String address = String.format("%s %s, %s %s", street, barangay, province, city_municipality);

		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO mf_project(\n" + 
				"proj_id, proj_name, proj_alias, address, co_id, status_id, created_by, \n" + 
				"date_created, edited_by, date_edited, street, barangay, municipality, \n" + 
				"province, country, vatable)\n" + 
				"VALUES ('"+proj_id+"', '"+proj_name+"', '"+proj_alias+"', '"+address+"', '"+co_id+"', 'A', '"+UserInfo.EmployeeCode+"', \n" + 
				"now(), null, null, '"+street+"', '"+barangay+"', '"+city_municipality+"', \n" + 
				"nullif('"+province+"','NONE'), 'PHIL', '"+vatable+"');";

		db.executeUpdate(sql, true);
		db.commit();
		cancelProject();
		displayProject();
	}

	private void updateProject(){
		String proj_id = txtProjID.getText();
		String proj_name = txtProjectName.getText().toUpperCase().replace("'", "");
		String proj_alias = txtProjectAlias.getText().toUpperCase();
		String co_id = lookupCompany.getValue();
		String street = txtStreet.getText();
		String barangay = txtBrgy.getText().replace("'", "");
		Boolean vatable = chkIsVatable.isSelected();

		String province = (String) cmbProvince.getSelectedItem();
		String city_municipality = (String) cmbCityMunicipality.getSelectedItem();
		System.out.printf("Display city municipality", city_municipality);

		String address = String.format("%s %s, %s %s", street, barangay, province, city_municipality);

		pgUpdate db = new pgUpdate();
		String sql = "UPDATE mf_project\n" + 
				"SET proj_name='"+proj_name+"', proj_alias='"+proj_alias+"', address='"+address+"', co_id='"+co_id+"', \n" + 
				"edited_by='"+UserInfo.EmployeeCode+"', date_edited=now(), street='"+street+"', \n" + 
				"barangay='"+barangay+"', municipality='"+city_municipality+"', province= nullif('"+province+"', 'NONE'), vatable="+vatable+"\n" + 
				"WHERE proj_id = '"+proj_id+"'";
		db.executeUpdate(sql, true);
		db.commit();
		cancelProject();
		displayProject();
	}

	private void cancelProject(){
		txtProjID.setText("");
		lookupCompany.setValue(null);
		txtCompany.setText("");
		txtProjectAlias.setText("");
		txtProjectName.setText("");
		txtStreet.setText("");
		txtBrgy.setText("");
		cmbProvince.setEnabled(false);
		cmbCityMunicipality.setEnabled(false);
		chkIsVatable.setSelected(false);
		chkIsVatable.setEnabled(false);
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, false);
		btnState(true, true, false, false);
		tblAddProject.setEnabled(true);
		tblAddProject.clearSelection();
		rowHeaderAddProject.setEnabled(true);
		
		txtProjectAlias.setEditable(false);
		txtProjectName.setEditable(false);
		txtStreet.setEditable(false);
		txtBrgy.setEditable(false);
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("New")){
			newProject();
		}
		if(actionCommand.equals("Edit")){
			editProject();
		}
		if(actionCommand.equals("Save")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save New Project?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					if(isProjExisting(txtProjID.getText())){
						updateProject();
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Project Info has been updated");
					}else{
						if(isProjNameExisting(txtProjectName.getText().toUpperCase().replace("'", ""))){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Project is Already Existing please input another name");
							txtProjectName.setText("");
						}else{
							addProject();
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Project has been saved");
						}
					}
				}
			}
		}
		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Cancel Editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancelProject();
			}
		}
	}
}
