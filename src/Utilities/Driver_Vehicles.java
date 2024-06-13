package Utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_List_Vehicles;

public class Driver_Vehicles extends _JInternalFrame implements _GUI, ActionListener,KeyListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Driver Vehicles";
	public static Dimension frameSize = new Dimension(500, 450);
	private ButtonGroup grpButton = new ButtonGroup();
	private JXPanel pnlNorth;
	private JLabel lblDriver;
	private _JLookup lookupDriver;
	private _JXTextField txtDriver;
	private JXPanel pnlCenter;
	private JXPanel pnlSouth;
	private _JButton btnNew;
	private _JButton btnEdit;
	private _JButton btnSave;
	private _JButton btnCancel;
	private JLabel lblPlateNo;
	private JLabel lblMake;
	//private Component lblStatus;
	private JXPanel pnlText;
	private _JXTextField txtPlateNo;
	private _JXTextField txtMake;
	private DefaultComboBoxModel cmbStatusModel;
	private JComboBox cmbStatus;
	private String entity_id;
	private String entity_name;
	private JScrollPane scrollVehicles;
	private model_List_Vehicles modelVehiclesModel;
	private _JTableMain tblVehicles;
	private JList rowHeaderVehicles;
	private JXPanel pnlAdd;
	private _JButton btnAdd;
	private _JButton btnRemove;
	private ArrayList<String> listBatches = new ArrayList<String>();
	private DefaultComboBoxModel cmbStatusModel_tbl;
	private JComboBox cmbStatus_tbl;
	private String status_id = "";
	private JLabel lblStatus;
	public Driver_Vehicles() {
		super(title, true, true, false, true);
		initGUI();
		FormLoad();
	}

	public Driver_Vehicles(String title) {
		super(title);
		initGUI();
		FormLoad();
	}

	public Driver_Vehicles(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		FormLoad();
	}

	@Override
	public void initGUI() {
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new java.awt.Dimension(frameSize));
			this.getContentPane().setLayout(new BorderLayout());

			{
				JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{

					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlNorth.setLayout(new BorderLayout(5, 5));
					pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 35));
					{
						JXPanel pnlLabel = new JXPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlLabel,BorderLayout.WEST);
						pnlLabel.setPreferredSize(new Dimension(50, 30));
						{
							lblDriver = new JLabel("Driver :");
							pnlLabel.add(lblDriver,BorderLayout.CENTER);
						}


					}
					{
						JXPanel pnlLookup = new JXPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlLookup,BorderLayout.CENTER);
						pnlLookup.setPreferredSize(new Dimension(50, 30));
						{
							lookupDriver = new _JLookup("", "Driver", 0) ; /// XXX lookupDriver 
							pnlLookup.add(lookupDriver,BorderLayout.WEST);
							lookupDriver.setPreferredSize(new Dimension(100, 30));
							lookupDriver.setReturnColumn(0);
							lookupDriver.setLookupSQL("SELECT a.entity_id AS \"Entity ID \",a.entity_name AS \"Entity Name\" FROM rf_entity a\n" + 
									"LEFT JOIN  rf_entity_assigned_type b ON a.entity_id = b.entity_id\n" + 
									"WHERE a.status_id = 'A'\n" + 
									"AND b.entity_type_id = '39'");
							lookupDriver.addLookupListener(new LookupListener() {
								@Override
								public void lookupPerformed(LookupEvent e) {

									Object[] data = ((_JLookup)e.getSource()).getDataSet();

									if (data != null) {
										entity_id = data[0].toString();
										entity_name = data[1].toString().trim();

										txtDriver.setText(entity_name);

										
										GenerateListVehicles(modelVehiclesModel);
										
										if (modelVehiclesModel.getRowCount() == 0){
											btnState(true, false, false, true);
										}else{
											btnState(false, true, false, true);
										}
											
										
									}

								}
							});

						}
						{

							txtDriver = new _JXTextField("Driver");
							pnlLookup.add(txtDriver,BorderLayout.CENTER);
						}
					}


				}
				{
					pnlCenter = new JXPanel();
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlCenter.setLayout(new BorderLayout(5, 5));
					pnlCenter.setPreferredSize(new Dimension(this.getWidth(), 110));
					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Car Details"));
					{
						JXPanel pnlDetails = new JXPanel();
						pnlCenter.add(pnlDetails,BorderLayout.NORTH);
						pnlDetails.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						pnlDetails.setLayout(new BorderLayout(5, 5));
						pnlDetails.setPreferredSize(new Dimension(this.getWidth(), 110));
						{
							JXPanel pnlLabelName = new JXPanel();
							pnlDetails.add(pnlLabelName,BorderLayout.WEST);
							pnlLabelName.setLayout(new GridLayout(3, 1, 3, 3)) ;
							pnlLabelName.setPreferredSize(new Dimension(80, 22));

							lblPlateNo = new JLabel("Plate No.");
							pnlLabelName.add(lblPlateNo,BorderLayout.WEST);

							lblMake = new JLabel("Make/Brand ");
							pnlLabelName.add(lblMake);

							lblStatus = new JLabel("Status");
							pnlLabelName.add(lblStatus);

						}
						{
							pnlText = new JXPanel();
							pnlDetails.add(pnlText,BorderLayout.CENTER);
							pnlText.setLayout(new GridLayout(3, 1, 3, 3));
							pnlText.setPreferredSize(new Dimension(200, 110));
							{
								txtPlateNo = new _JXTextField("Plate No.");
								pnlText.add(txtPlateNo);


								txtMake = new _JXTextField("Make/Brand");
								pnlText.add(txtMake);
								txtMake.addKeyListener(this);

								cmbStatusModel = new DefaultComboBoxModel(new String[] {"Active", "Inactive"});
								cmbStatus = new JComboBox();
								pnlText.add(cmbStatus);
								cmbStatus.setModel(cmbStatusModel);
								cmbStatus.addActionListener(this);
								cmbStatus.addKeyListener(this);

							}
						}
						{
							pnlAdd = new JXPanel();
							pnlDetails.add(pnlAdd,BorderLayout.SOUTH);
							pnlAdd.setLayout(new GridLayout(1, 2, 3, 3));
							pnlAdd.setPreferredSize(new Dimension(200, 25));
							{
								btnAdd = new _JButton("Add");
								pnlAdd.add(btnAdd);
								btnAdd.addActionListener(this);
								btnAdd.setActionCommand("Add");
							}
							{
								btnRemove = new _JButton("Remove");
								pnlAdd.add(btnRemove);
								btnRemove.addActionListener(this);
								btnRemove.setActionCommand("Remove");

							}
						}
					}
					{
						JXPanel pnlTable= new JXPanel(new BorderLayout(3,3));
						pnlCenter.add(pnlTable,BorderLayout.CENTER);
						pnlTable.setBorder(components.JTBorderFactory.createTitleBorder("List of Vehicles"));
						{

							cmbStatusModel_tbl = new DefaultComboBoxModel(new String[] {"A", "I"});
							cmbStatus_tbl = new JComboBox();
							cmbStatus_tbl.setModel(cmbStatusModel_tbl);
							cmbStatus_tbl.addActionListener(this);

							scrollVehicles = new JScrollPane();
							pnlTable.add(scrollVehicles, BorderLayout.CENTER);
							scrollVehicles.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollVehicles.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblVehicles.clearSelection();
								}
							});

							{

								modelVehiclesModel = new model_List_Vehicles();
								modelVehiclesModel.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowHeaderVehicles.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowHeaderVehicles.getModel()).addElement(modelVehiclesModel.getRowCount());
										}
									}
								});

								tblVehicles = new _JTableMain(modelVehiclesModel);
								scrollVehicles.setViewportView(tblVehicles);
								tblVehicles.setHorizontalScrollEnabled(true);
								modelVehiclesModel.setEditable(true);
								tblVehicles.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
											
												if (tblVehicles.getSelectedColumn() == 3) {
													TableColumn statusColumn = tblVehicles.getColumnModel().getColumn(3);
													statusColumn.setCellEditor(new DefaultCellEditor(cmbStatus_tbl));
													
												}
									}
								});
								tblVehicles.packAll();

								/** Repaint for Highlight **/
								tblVehicles.getTableHeader().addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent evt) {
										tblVehicles.repaint();
									}
								});
							}
							{

								rowHeaderVehicles = tblVehicles.getRowHeader();
								rowHeaderVehicles.setModel(new DefaultListModel());
								scrollVehicles.setRowHeaderView(rowHeaderVehicles);
								scrollVehicles.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}

						}
					}
				}
				{

					pnlSouth = new JXPanel();
					pnlMain.add(pnlSouth,BorderLayout.SOUTH);
					pnlSouth.setLayout(new BorderLayout(3, 3));
					pnlSouth.setPreferredSize(new Dimension(this.getWidth(), 40));
					{
						JXPanel pnlButton= new JXPanel();
						pnlSouth.add(pnlButton,BorderLayout.CENTER);
						pnlButton.setLayout(new GridLayout(1, 4, 3, 3));
						{

							{
								btnNew = new _JButton("New");
								pnlButton.add(btnNew);
								btnNew.addActionListener(this);
								btnNew.setActionCommand("New");
								grpButton.add(btnNew);
							}
							{
								btnEdit = new _JButton("Edit");
								pnlButton.add(btnEdit);
								btnEdit.addActionListener(this);
								btnEdit.setActionCommand("Edit");
								grpButton.add(btnEdit);
							}
							{
								btnSave = new _JButton("Save");
								pnlButton.add(btnSave);
								btnSave.addActionListener(this);
							}
							{
								btnCancel = new _JButton("Cancel");
								pnlButton.add(btnCancel);
								btnCancel.addActionListener(this);
							}

						}
					}

				}

			}
		} catch (Exception e) {
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Add")) {
			Add();
		}

		if (e.getActionCommand().equals("Remove")) {
			RemoveProcess();
		}

		if (e.getActionCommand().equals("New")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			NewProcess();
		}

		if (e.getActionCommand().equals("Edit")) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			EditProcess();
		}
		if (e.getActionCommand().equals("Save")) {

			if (hasSelected()) {
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					SaveProcess();
					CancelProcess();
					FormLoad();
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Successfully saved", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select first the plate no.", "Save", JOptionPane.WARNING_MESSAGE);
				return;
			}

		}

		if (e.getActionCommand().equals("Cancel")) {
			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields?  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				CancelProcess();			
			}

		}


	}//actionPerformed

	private void FormLoad(){
		this.setComponentsEnabled(pnlCenter, false);
		btnState(false, false, false, false);
		lookupDriver.requestFocus();
	}

	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelVehiclesModel.getRowCount(); x++){
			listSelected.add((Boolean) modelVehiclesModel.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	private void btnState(Boolean sAdd, Boolean sEdit, Boolean sSave, Boolean sCance){
		btnNew.setEnabled(sAdd);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCance);

	}

	private void Add(){
		if (checkRequiredFields()) {
			AddProcess();
		}
	}

	private void AddProcess(){
		String plate_no = txtPlateNo.getText().toString().toUpperCase().trim().replaceAll("\\s","");

		for (int i = 0; i < modelVehiclesModel.getRowCount(); i++) {

			if (modelVehiclesModel.getValueAt(i, 1).toString().trim().replaceAll("\\s","").equals(plate_no)) {
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "This plate no. is already exist", "Add", JOptionPane.WARNING_MESSAGE);
				return;
			}

		}

		AddVehicles(modelVehiclesModel);

	}

	private void NewProcess(){
		this.setComponentsEnabled(pnlCenter, true);
		this.setComponentsEditable(pnlCenter, true);

		btnState(false, false, true, true);

	}


	private void EditProcess(){

		this.setComponentsEnabled(pnlCenter, true);
		this.setComponentsEditable(pnlCenter, true);


		btnState(false, false, true, true);

	}

	private void SaveProcess(){

		
		
		pgUpdate dbU = new pgUpdate();
		String entity_id = lookupDriver.getValue().toString();
		for(int x=0; x < modelVehiclesModel.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelVehiclesModel.getValueAt(x, 0);
			String plate_no = (String) modelVehiclesModel.getValueAt(x, 1);
			String makebrand = (String) modelVehiclesModel.getValueAt(x, 2);
			String status_id = (String) modelVehiclesModel.getValueAt(x, 3);

			if(isSelected){

				if (grpButton.getSelection().getActionCommand().equals("New")) {
					dbU.executeUpdate("INSERT INTO rf_tripticket_driver_vehicles(\n" + 
							"            entity_id, \n" + 
							"            plate_no, \n" + 
							"            make_brand, \n" + 
							"            status_id)\n" + 
							"    VALUES ( \n" + 
							"            '"+entity_id+"',---entity_id, \n" + 
							"            '"+plate_no.toUpperCase()+"',---plate_no, \n" + 
							"            '"+makebrand.toUpperCase()+"',---make_brand, \n" + 
							"            '"+status_id+"'---status_id\n" + 
							"            );\n" + 
							"", true,true);

				}
				if (grpButton.getSelection().getActionCommand().equals("Edit")) {
					pgSelect db = new pgSelect();
					db.select("SELECT plate_no FROM rf_tripticket_driver_vehicles WHERE entity_id = '"+entity_id+"'");
					
					for (int i = 0; i < db.getRowCount(); i++) {
						for (int j = 0; j < listBatches.size(); j++) {
							if (db.Result[i][0].toString().replaceAll("\\s","").equals(listBatches.get(i).replaceAll("\\s",""))) {
								dbU.executeUpdate("UPDATE rf_tripticket_driver_vehicles SET status_id ='I' WHERE plate_no = '"+db.Result[i][0].toString()+"' AND entity_id = '"+entity_id+"'", true);
							}
						}

						if (db.Result[i][0].toString().replaceAll("\\s","").equals(plate_no.replaceAll("\\s",""))) {
							dbU.executeUpdate("UPDATE rf_tripticket_driver_vehicles\n" + 
									"   SET plate_no='"+plate_no.toUpperCase()+"', make_brand='"+makebrand.toUpperCase()+"', status_id= '"+status_id+"'\n" + 
									" WHERE entity_id = '"+entity_id+"' \n" + 
									" AND plate_no = '"+plate_no+"'\n" + 
									"", true,true);
						}else{

							dbU.executeUpdate("INSERT INTO rf_tripticket_driver_vehicles(\n" + 
									"            rec_id, \n" + 
									"            entity_id, \n" + 
									"            plate_no, \n" + 
									"            make_brand, \n" + 
									"            status_id)\n" + 
									"    VALUES ((SELECT MAX(rec_id) + 1 FROM rf_tripticket_driver_vehicles),---rec_id, \n" + 
									"            '"+entity_id+"',---entity_id, \n" + 
									"            '"+plate_no.toUpperCase()+"',---plate_no, \n" + 
									"            '"+makebrand.toUpperCase()+"',---make_brand, \n" + 
									"            '"+status_id+"'---status_id\n" + 
									"            );\n" + 
									"", true,true);
						}


					}
				
				}
			}
			
		}


	}

	private void CancelProcess(){
		lookupDriver.setValue("");
		txtDriver.setText("");
		txtPlateNo.setText("");
		txtMake.setText("");
		cmbStatus.setSelectedIndex(0);

		rowHeaderVehicles.setModel(new DefaultListModel());
		modelVehiclesModel.clear();
		scrollVehicles.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblVehicles.getRowCount())));

		FormLoad();
	}



	private void AddVehicles(model_List_Vehicles model){

		String plate_no = txtPlateNo.getText().toString().toUpperCase().trim();
		String make_brand = txtMake.getText().toString().toUpperCase().trim();
		String status = "";


		if (cmbStatus.getSelectedIndex() == 0) {
			status = "A";
		}else{
			status = "I";
		}
		if (grpButton.getSelection().getActionCommand().equals("New")) {
			modelVehiclesModel.addRow(new Object []{false,plate_no,make_brand,status});	
		}else{
			modelVehiclesModel.addRow(new Object []{true,plate_no,make_brand,status});
		}
			
			

		txtPlateNo.setText("");
		txtMake.setText("");
		cmbStatus.setSelectedIndex(0);
		tblVehicles.packAll();
	}

	private void RemoveProcess(){



		int totalrows = modelVehiclesModel.getRowCount();
		int rowdeleted=0;
		if (!hasSelected()) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select plate no to remove", "Remove", JOptionPane.WARNING_MESSAGE);
			return;
		}else{
			if( JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Do you want to remove this plate no?", "Remove", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				for (int row = 0;row <= totalrows - 1; row++)  {
					Boolean isSelected = (Boolean)modelVehiclesModel.getValueAt(row-rowdeleted, 0);
					String plateNo = (String) modelVehiclesModel.getValueAt(row-rowdeleted, 1);
					if (isSelected){
						rowHeaderVehicles.setModel(new DefaultListModel());
						modelVehiclesModel.removeRow(row-rowdeleted);
						rowdeleted++;
						listBatches.add(plateNo);
					}   

					for(int x = 1; x <= modelVehiclesModel.getRowCount(); x++){
						((DefaultListModel) rowHeaderVehicles.getModel()).addElement(x);
					}

					tblVehicles.packAll();
					scrollVehicles.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblVehicles.getRowCount())));

				}

			}
		}





	}

	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toAdd = true;

		if(txtPlateNo.getText().isEmpty() || txtPlateNo.getText().equals("")){
			required_fields = required_fields + "Plate No \n";
			toAdd = false;
		}
		if(txtMake.getText().isEmpty() || txtMake.getText().equals("")){
			required_fields = required_fields + "Make / Brand \n";
			toAdd = false;
		}

		if(toAdd == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Add",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void GenerateListVehicles(model_List_Vehicles model){
		

		pgSelect db = new pgSelect();
		model.clear();

		String SQL = "";

		SQL = "SELECT TRUE, plate_no, make_brand, status_id FROM rf_tripticket_driver_vehicles WHERE entity_id = '"+entity_id+"'";
		FncSystem.out("SQL", SQL);
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}

		tblVehicles.packAll();
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(txtMake)) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER){
				Add();
			}
		}
		if (e.getSource().equals(cmbStatus)) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER){
				Add();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
}

