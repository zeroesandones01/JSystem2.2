package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelCharacterReference;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup._JLookup;
import components._JTableMain;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlReferences_CharacterReference extends JPanel implements _GUI,
ActionListener {

	private static final long serialVersionUID = -8700922178685915288L;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblName;

	private JLabel lblStatus;
	private JTextField txtName;
	private JScrollPane scrollCharacterRef;
	private JComboBox cmbStatus;
	private JTextField txtContactNum;
	private _JLookup lookupRelation;
	private JTextField txtAddress;
	private JPanel pnlNorthCenter;
	private JLabel lblContactNum;
	private JLabel lblAddress;
	private JLabel lblRelation;

	private modelCharacterReference modelCharacterRef;
	private _JTableMain tblCharacterRef;
	private JList rowHeaderRef_CharRef;

	private ClientInformation ci;

	public pnlReferences_CharacterReference(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}

	public pnlReferences_CharacterReference(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlReferences_CharacterReference(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferences_CharacterReference(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(658, 391));
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(648, 132));
			{
				pnlNorthWest = new JPanel(new GridLayout(5, 1, 3, 3));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				pnlNorthWest.setPreferredSize(new Dimension(123, 132));
				{
					lblName = new JLabel("*Name");
					pnlNorthWest.add(lblName);
				}
				{
					lblRelation = new JLabel("Relation");
					pnlNorthWest.add(lblRelation);
				}
				{
					lblAddress = new JLabel("*Address");
					pnlNorthWest.add(lblAddress);
				}
				{
					lblContactNum = new JLabel("*Contact No./s");
					pnlNorthWest.add(lblContactNum);
				}
				{
					lblStatus = new JLabel("Status");
					pnlNorthWest.add(lblStatus);
				}
			}
			{
				pnlNorthCenter = new JPanel(new GridLayout(5, 1, 3, 3));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				{
					txtName = new JTextField();
					pnlNorthCenter.add(txtName);
				}
				{
					JPanel pnlRelation = new JPanel(new BorderLayout());
					pnlNorthCenter.add(pnlRelation);
					{
						lookupRelation = new _JLookup(null, "Relation to Applicant", 1);
						lookupRelation.setLookupSQL(sqlRelation());
						lookupRelation.setFilterName(true);
						pnlRelation.add(lookupRelation, BorderLayout.WEST);
						lookupRelation.setPreferredSize(new Dimension(200, 0));
					}
				}
				{
					txtAddress = new JTextField();
					pnlNorthCenter.add(txtAddress);
				}
				{
					JPanel pnlContactnum = new JPanel(new BorderLayout());
					pnlNorthCenter.add(pnlContactnum);
					{
						txtContactNum = new JTextField();
						pnlContactnum.add(txtContactNum, BorderLayout.WEST);
						txtContactNum.setPreferredSize(new Dimension(120, 0));
					}
				}
				{
					JPanel pnlCharRefStatus = new JPanel(new BorderLayout());
					pnlNorthCenter.add(pnlCharRefStatus);
					{
						cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
						pnlCharRefStatus.add(cmbStatus, BorderLayout.WEST);
						cmbStatus.setPreferredSize(new Dimension(120, 0));
						cmbStatus.setEnabled(false);
					}
				}
			}
		}
		{
			scrollCharacterRef = new JScrollPane();
			this.add(scrollCharacterRef, BorderLayout.CENTER);
			scrollCharacterRef.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelCharacterRef = new modelCharacterReference();
				tblCharacterRef = new _JTableMain(modelCharacterRef);
				scrollCharacterRef.setViewportView(tblCharacterRef);
				tblCharacterRef.hideColumns("Rec. ID");
				tblCharacterRef.setSortable(false);
				tblCharacterRef.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				modelCharacterRef.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {

						((DefaultListModel)rowHeaderRef_CharRef.getModel()).addElement(modelCharacterRef.getRowCount());
						scrollCharacterRef.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCharacterRef.getRowCount())));


						if(modelCharacterRef.getRowCount() == 0){
							rowHeaderRef_CharRef.setModel(new DefaultListModel());
						}
					}
				});
				tblCharacterRef.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){
							try{
								if(tblCharacterRef.getSelectedRows().length ==1){
									int row = tblCharacterRef.getSelectedRow();

									String name = (String) modelCharacterRef.getValueAt(row, 1);
									String relation = (String) modelCharacterRef.getValueAt(row, 2);
									String addr = (String) modelCharacterRef.getValueAt(row, 3);
									String contact_no = (String) modelCharacterRef.getValueAt(row, 4);
									String char_ref_stat = (String) modelCharacterRef.getValueAt(row, 5);

									txtName.setText(name);
									lookupRelation.setValue(relation);
									txtAddress.setText(addr);
									txtContactNum.setText(contact_no);

									if (char_ref_stat.equals("A")){
										cmbStatus.setSelectedItem("Active");
									}else{
										cmbStatus.setSelectedItem("Inactive");
									}
									ci.btnState(true, true, false, false, false);
									
									if(ci.canEdit() == false){
										ci.btnState(false, false, false, false, false);
									}
								}

							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					}
				});
			}
			{
				rowHeaderRef_CharRef = tblCharacterRef.getRowHeader();
				rowHeaderRef_CharRef.setModel(new DefaultListModel());
				scrollCharacterRef.setRowHeaderView(rowHeaderRef_CharRef);
				scrollCharacterRef.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtName, txtAddress, txtContactNum, cmbStatus));
		ci.setComponentsEditable(pnlNorth, false);
	}//END OF INIT GUI

	public void displayCharacterRef(String entity_id){//DISPLAYS THE CHARACTER REFERENCE BASED ON THE SELECTED ENTITY
		modelCharacterRef.clear();

		String sql = "SELECT a.rec_id, a.reference_name, b.relation_desc,  a.address, a.tel_no, a.status_id "+
				"FROM rf_character_references a "+
				"left join mf_relation b on b.relation_code = a.relation "+
				"where a.entity_id = '"+ entity_id +"'";

		FncSystem.out("Display Character Reference", sql);	
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact number of columns and column types.
				modelCharacterRef.addRow(db.getResult()[x]);
			}
			scrollCharacterRef.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCharacterRef.getRowCount())));
			tblCharacterRef.packAll();
		}
	}

	private String sqlRelation(){//SQL FOR THE RELATION
		return "select relation_code as \"ID\", \n"+
				"relation_desc as \"Description\" \n"+
				"from mf_relation \n"+
				"where status_id = 'A' \n"+
				"order by relation_desc";
	}

	public boolean toSave() {//CHECKS FOR THE REQUIRED FIELDS BEFORE SAVING
		if(txtName.getText().trim().equals("") || txtAddress.getText().trim().equals("") || txtContactNum.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s, %s, %s", lblName.getText(), lblAddress.getText(), lblContactNum.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	//CHECKS IF CHARACTER REFERENCE IS ALREADY EXISTING
	public boolean isCharacterRefExisting(String entity_id, Integer rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_character_references where entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	public void newCharRef(){//NEW CHARACTER REFERENCE
		ci.setComponentsEditable(pnlNorth, true);
		cmbStatus.setEnabled(true);
		cmbStatus.setSelectedIndex(0);
		
		try{
			tblCharacterRef.clearSelection();
		} catch (ArrayIndexOutOfBoundsException e) {}
		tblCharacterRef.setEnabled(false);
		rowHeaderRef_CharRef.setEnabled(false);
	}

	public void editCharRef(){//EDITING FOR THE CHARACTER REFERENCE
		if(tblCharacterRef.getSelectedRows().length ==1){
			ci.setComponentsEditable(pnlNorth, true);
			cmbStatus.setEnabled(true);
			tblCharacterRef.setEnabled(false);
			rowHeaderRef_CharRef.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollCharacterRef, "Please select only one row to edit");
			tblCharacterRef.clearSelection();
		}
	}

	public boolean saveCharRef(String entity_id){//SAVING AND UPDATING OF THE CHARACTER REFERENCE
		pgUpdate db = new pgUpdate();

		String ref_name = txtName.getText().trim().replace("'", "''");
		String relation = lookupRelation.getValue();
		String ref_addr = txtAddress.getText().replace("'", "''");
		String ref_tel_no = txtContactNum.getText();
		String ref_status = (String) cmbStatus.getSelectedItem();

		//UPDATING
		if(tblCharacterRef.getSelectedRows().length ==1){
			int row = tblCharacterRef.getSelectedRow();
			Integer rec_id = (Integer) modelCharacterRef.getValueAt(row, 0);
			if(isCharacterRefExisting(entity_id, rec_id)){
				String sql = "UPDATE rf_character_references SET reference_name='"+ref_name+"', relation=coalesce((select relation_code from mf_relation where relation_desc =  '"+relation+"'), '21'), address='"+ref_addr+"', \n" + 
							 "tel_no= '"+ref_tel_no+"', status_id= (case when '"+ref_status+"' = 'Active' then 'A' else 'I' end)\n" + 
							 "WHERE entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Character Reference has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_character_references(entity_id, reference_name, relation, address, tel_no, status_id, rowguid)\n" + 
						 "VALUES ('"+ entity_id +"', '"+ ref_name +"', \n"+
						 "coalesce((select relation_code from mf_relation where relation_desc =  '"+relation+"'), '21'), \n"+
						 "'" + ref_addr + "', '"+ ref_tel_no +"', \n"+
						 "(case when '"+ ref_status +"' = 'Active' then 'A' else 'I' end), '')";
			
			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Trade Reference has been Saved");
		}
		db.commit();
		
		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);
		tblCharacterRef.clearSelection();
		tblCharacterRef.setEnabled(true);
		rowHeaderRef_CharRef.setEnabled(true);
		return true;
	}

	public void cancelCharRef(){//CANCELATION OF THE CHARACTER REFERENCE
		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);
		tblCharacterRef.clearSelection();
		clearFields();
		tblCharacterRef.clearSelection();
		tblCharacterRef.setEnabled(true);
		rowHeaderRef_CharRef.setEnabled(true);
	}

	public void clearFields(){//CLEARS THE FIELDS IN THIS PANEL
		txtName.setText("");
		txtContactNum.setText("");
		txtAddress.setText("");
		lookupRelation.setValue(null);
		tblCharacterRef.clearSelection();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
