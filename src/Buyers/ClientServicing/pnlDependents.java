/**
 * 
 */
package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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
import components._JTableMain;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.FncTables;
import DateChooser._JDateChooser;
import tablemodel.modelDependentList;

/**
 * @author John Lester Fatallo
 */
public class pnlDependents extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -2731816454083057425L;
	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JLabel lblDependentName;
	private JLabel lblDOB;
	private JLabel lblOccupation;
	private JLabel lblCompanyName;

	private JPanel pnlNorthCenter;
	private JTextField txtDependent;
	private _JDateChooser dateDateOfBirth;
	private JTextField txtOccupation;
	private JTextField txtCompanyName;

	private _JTableMain tblDependentList;
	private JScrollPane scrollDependentList;
	private modelDependentList modelDependList;
	private JList rowHeaderDependList;

	private ClientInformation ci;

	public pnlDependents(ClientInformation ci) {
		this.ci = ci;
		initGUI();
	}

	public pnlDependents(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlDependents(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlDependents(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		{
			this.setLayout(new BorderLayout(5, 5));
			this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			this.setPreferredSize(new Dimension(796, 386));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				this.add(pnlNorth ,BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Dependent Details"));
				pnlNorth.setPreferredSize(new Dimension(786, 145));
				{
					pnlNorthWest = new JPanel(new GridLayout(4, 1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(200, 137));
					{
						lblDependentName = new JLabel("*Dependent Name");
						pnlNorthWest.add(lblDependentName);
					}
					{
						lblDOB = new JLabel(" Date of Birth");
						pnlNorthWest.add(lblDOB);
					}
					{
						lblOccupation = new JLabel(" Occupation");
						pnlNorthWest.add(lblOccupation);
					}
					{
						lblCompanyName = new JLabel(" Name of School / Company");
						pnlNorthWest.add(lblCompanyName);
					}

				}
				{
					pnlNorthCenter = new JPanel(new GridLayout(4, 1, 5, 5));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						txtDependent = new JTextField();
						pnlNorthCenter.add(txtDependent);
					}
					{
						JPanel pnlDOB = new JPanel(new BorderLayout());
						pnlNorthCenter.add(pnlDOB);
						{
							dateDateOfBirth = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlDOB.add(dateDateOfBirth, BorderLayout.WEST);
							dateDateOfBirth.setPreferredSize(new Dimension(120, 0));
							dateDateOfBirth.setEnabled(false);
						}
					}
					{
						txtOccupation = new JTextField();
						pnlNorthCenter.add(txtOccupation);
					}
					{
						txtCompanyName = new JTextField();
						pnlNorthCenter.add(txtCompanyName);
					}

				}
			}
			{//Scroll Pane for the Dependent List
				scrollDependentList = new JScrollPane();
				this.add(scrollDependentList, BorderLayout.CENTER);
				scrollDependentList.setBorder(components.JTBorderFactory.createTitleBorder("Dependent List"));
				scrollDependentList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					modelDependList = new modelDependentList();

					tblDependentList = new _JTableMain(modelDependList);
					scrollDependentList.setViewportView(tblDependentList);
					tblDependentList.setSortable(false);
					tblDependentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

					modelDependList.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {

							((DefaultListModel)rowHeaderDependList.getModel()).addElement(modelDependList.getRowCount());
							scrollDependentList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDependentList.getRowCount())));

							if(modelDependList.getRowCount() == 0){
								rowHeaderDependList.setModel(new DefaultListModel());
							}
						}
					});
					tblDependentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent arg0) {
							if(!arg0.getValueIsAdjusting()){
								try {
									int row = tblDependentList.getSelectedRow();

									String dependent_name = (String) modelDependList.getValueAt(row, 0);
									Date dependent_dob = (Date) modelDependList.getValueAt(row, 1);
									String occupation = (String) modelDependList.getValueAt(row, 2);
									String comp_name = (String) modelDependList.getValueAt(row, 3);

									txtDependent.setText(dependent_name);
									dateDateOfBirth.setDate(dependent_dob);
									txtOccupation.setText(occupation);
									txtCompanyName.setText(comp_name);

									ci.btnState(true, true, false, false, false);
									
									if(ci.canEdit() == false){
										ci.btnState(false, false, false, false, false);
									}
								} catch (ArrayIndexOutOfBoundsException e) { }
							}
						}
					});
				}
			}
			{
				rowHeaderDependList = tblDependentList.getRowHeader();
				rowHeaderDependList.setModel(new DefaultListModel());
				scrollDependentList.setRowHeaderView(rowHeaderDependList);
				scrollDependentList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		ci.setComponentsEditable(pnlNorth, false);
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtDependent, dateDateOfBirth, txtOccupation, txtCompanyName));
	}//XXX END OF INIT GUI
	
	public void displayDependentList(String entity_id){
		modelDependList.clear();
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM view_ci_dependents('"+entity_id+"')";
		db.select(SQL);
		
		FncSystem.out("Display Client Dependents", SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelDependList.addRow(db.getResult()[x]);
			}
			scrollDependentList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDependentList.getRowCount())));
			tblDependentList.packAll();
		}
	}

	//CHECKS IF THE DEPENDENT IS ALREADY EXISTING
	public boolean isDependentExisting(String entity_id, String dependent_name, Date dob, String occupation, String company){
		pgSelect db = new pgSelect();
		db.select("select * from rf_client_depedent \n"+
				"where entity_id = '"+ entity_id +"' \n"+
				"and nameofdependents = '"+dependent_name+"' \n"+
				"and date_of_birth = nullif('"+dob+"', 'null')::TIMESTAMP and occupation = '"+occupation+"' and company = '"+company+"' ");
		return db.isNotNull();
	}

	//CHECKS WHETHER THE NO OF DEPENDENTS HAS EXCEEDED FOR THE SELECTED ENTITY
	public Boolean hasNoOfDependentsExceeded(String entity_id){
		pgSelect db = new pgSelect();
		String sql = "select \n" + 
					 "case when (select count(*) from rf_client_depedent where entity_id = '"+entity_id+"') >= coalesce((select dependent_no from rf_entity where entity_id = '"+entity_id+"'), 0)\n" + 
					 "then true else false end";
		db.select(sql);
		
		return (Boolean) db.getResult()[0][0];
	}

	public boolean tosave(){//CHECKS REQUIRED FIELD BEFORE SAVING
		if (txtDependent.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Dependent Name"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void newDependent(){//NEW DEPENDENT
		clearDependentFields();
		ci.setComponentsEditable(pnlNorth, true);
		dateDateOfBirth.setEnabled(true);
		tblDependentList.clearSelection();
		tblDependentList.setEnabled(false);
		rowHeaderDependList.setEnabled(false);
	}

	public void editDependet() { //Enabling of Components when edit button is clicked
		if(tblDependentList.getSelectedRows().length == 1){
			ci.setComponentsEditable(pnlNorth, true);
			dateDateOfBirth.setEnabled(true);
			tblDependentList.setEnabled(false);
			rowHeaderDependList.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollDependentList, "Please select one row to edit");
		}
	} 

	public boolean saveDependent(String entity_id){//SAVING AND UPDATING OF DEPENDENT
		pgUpdate db = new pgUpdate();
		String dependent_name = txtDependent.getText().trim().replace("'", "''");
		Date dob = dateDateOfBirth.getDate();
		String occupation = txtOccupation.getText().trim().replace("'", "''");
		String company = txtCompanyName.getText().trim().replace("'", "''");
		//UPDATE
		if(tblDependentList.getSelectedRows().length ==1){
			
			int row = tblDependentList.getSelectedRow();
			String prev_dependent_name = (String) modelDependList.getValueAt(row, 0);
			Date prev_dob = (Date) modelDependList.getValueAt(row, 1);
			String prev_occupation = (String) modelDependList.getValueAt(row, 2);
			String prev_company = (String) modelDependList.getValueAt(row, 3);
			
			if(isDependentExisting(entity_id, prev_dependent_name, prev_dob, prev_occupation, prev_company)){
				String sql = "UPDATE rf_client_depedent SET date_of_birth = nullif('"+dob+"', 'null')::TIMESTAMP,  \n"+
							 "occupation='"+occupation+"', company='"+company+"', nameofdependents = '"+dependent_name+"'\n" + 
							 "WHERE entity_id = '"+entity_id+"' "+
							 "and nameofdependents = '"+prev_dependent_name+"' and date_of_birth = '"+prev_dob+"' and \n"+
							 "occupation = '"+prev_occupation+"' and company = '"+prev_company+"'";
				db.executeUpdate(sql, true);
				db.commit();
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Dependent has been Updated");
			}
		}else{//SAVING
			if(hasNoOfDependentsExceeded(entity_id)){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "No of Dependents for this person has exceeded \nEdit No. of Dependents from Client Information to add new here");
				clearDependentFields();
			}else{
				String sql = "INSERT INTO rf_client_depedent(entity_id,noofdep,nameofdependents,date_of_birth, occupation, company)\n" + 
							 "VALUES ('"+ entity_id +"', coalesce((select dependent_no from rf_entity where entity_id = '"+entity_id+"'), 0), "+
							 "'"+ dependent_name +"',nullif('"+dob+"', 'null')::TIMESTAMP, \n"+
							 "'" + occupation +"', '"+ company +"')";
				
				db.executeUpdate(sql, true);
				db.commit();
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Dependent has been Saved");
			}
		}
		ci.setComponentsEditable(pnlNorth, false);
		dateDateOfBirth.setEnabled(false);
		tblDependentList.clearSelection();
		tblDependentList.setEnabled(true);
		rowHeaderDependList.setEnabled(true);
		return true;
	}

	public void cancelDependents(){//CANCELATION FOR THE DEPENDENTS
		ci.setComponentsEditable(pnlNorth, false);
		dateDateOfBirth.setEnabled(false);
		clearDependentFields();
		tblDependentList.clearSelection();
		tblDependentList.setEnabled(true);
		rowHeaderDependList.setEnabled(true);
	}

	public void clearDependentFields(){//CLEARS THE FIELD IN THIS PANEL
		txtDependent.setText("");
		dateDateOfBirth.setText("");
		txtOccupation.setText("");
		txtCompanyName.setText("");
		tblDependentList.clearSelection();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
}
