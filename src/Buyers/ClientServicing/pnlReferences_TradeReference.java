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
import tablemodel.modelTradeReference;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.FncTables;
import components._JTableMain;

/**
 * @author John Lester Fatallo
 */

public class pnlReferences_TradeReference extends JPanel implements _GUI,
ActionListener {

	private static final long serialVersionUID = -4538099272522308967L;

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblName;
	private JLabel lblAddress;
	private JLabel lblContactNum;
	private JLabel lblStatus;

	private JPanel pnlNorthCenter;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtContactNum;
	private JComboBox cmbStatus;

	private JScrollPane scrollTradeRefList;
	private modelTradeReference modelTradeRef;
	private _JTableMain tblTradeRef;
	private JList rowHeaderRef_TradeRef;

	private ClientInformation ci;

	public pnlReferences_TradeReference(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}

	public pnlReferences_TradeReference(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlReferences_TradeReference(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferences_TradeReference(LayoutManager layout,
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
				pnlNorthWest = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				pnlNorthWest.setPreferredSize(new Dimension(123, 132));
				{
					lblName = new JLabel("*Name");
					pnlNorthWest.add(lblName);
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
				pnlNorthCenter = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				{
					txtName = new JTextField();
					pnlNorthCenter.add(txtName);
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
					JPanel pnlConnectionStatus = new JPanel(new BorderLayout());
					pnlNorthCenter.add(pnlConnectionStatus);
					{
						cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
						pnlConnectionStatus.add(cmbStatus, BorderLayout.WEST);
						cmbStatus.setPreferredSize(new Dimension(120, 0));
						cmbStatus.setEnabled(false);
					}
				}
			}
		}
		{
			scrollTradeRefList = new JScrollPane();
			this.add(scrollTradeRefList, BorderLayout.CENTER);
			scrollTradeRefList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelTradeRef = new modelTradeReference();
				tblTradeRef = new _JTableMain(modelTradeRef);
				scrollTradeRefList.setViewportView(tblTradeRef);
				tblTradeRef.hideColumns("Rec. ID");
				tblTradeRef.setSortable(false);
				tblTradeRef.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				modelTradeRef.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {

						((DefaultListModel)rowHeaderRef_TradeRef.getModel()).addElement(modelTradeRef.getRowCount());
						scrollTradeRefList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTradeRef.getRowCount())));

						if(modelTradeRef.getRowCount() == 0){
							rowHeaderRef_TradeRef.setModel(new DefaultListModel());
						}
					}
				});
				tblTradeRef.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){
							if(tblTradeRef.getSelectedRows().length ==1){
								int row = tblTradeRef.getSelectedRow();

								String supp_name = (String) modelTradeRef.getValueAt(row, 1);
								String supp_addr = (String) modelTradeRef.getValueAt(row, 2);
								String supp_tel = (String) modelTradeRef.getValueAt(row, 3);
								String supp_stat = (String) modelTradeRef.getValueAt(row, 4);

								txtName.setText(supp_name);
								txtAddress.setText(supp_addr);
								txtContactNum.setText(supp_tel);

								if (supp_stat.equals("A")){
									cmbStatus.setSelectedItem("Active");
								}else{
									cmbStatus.setSelectedItem("Inactive");
								}
								ci.btnState(true, true, false, false, false);
								
								if(ci.canEdit() == false){
									ci.btnState(false, false, false, false, false);
								}
							}
						}
					}
				});
			}
			{
				rowHeaderRef_TradeRef = tblTradeRef.getRowHeader();
				rowHeaderRef_TradeRef.setModel(new DefaultListModel());
				scrollTradeRefList.setRowHeaderView(rowHeaderRef_TradeRef);
				scrollTradeRefList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtName, txtAddress, txtContactNum, cmbStatus));
		ci.setComponentsEditable(pnlNorth, false);
	}//END OF INIT GUI

	public void displayReferences_TradeRef(String entity_id){//DISPLAYS THE TRADE REFERENCE BASED ON THE SELECTED ENTITY
		modelTradeRef.clear();

		String sql = "Select rec_id, supplier_name, address, tel_no, status_id from rf_trade_references where entity_id = '"+ entity_id +"'";

		FncSystem.out("Display Trade Reference", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact number of columns and column types.
				modelTradeRef.addRow(db.getResult()[x]);
			}
			scrollTradeRefList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTradeRef.getRowCount())));
			tblTradeRef.packAll();
		}
	}

	public boolean toSave() {//CHECKS FOR THE REQUIRED FIELDS BEFORE SAVING
		if(txtName.getText().trim().equals("") || txtAddress.getText().trim().equals("") || txtContactNum.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s, %s, %s", lblName.getText(), lblAddress.getText(), lblContactNum.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean isTradeRefExisting(String entity_id, Integer rec_id){//CHECKS IF TRADE REFERENCE IS ALREADY EXISTING
		pgSelect db =new pgSelect();
		String sql = "select * from rf_trade_references where entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	public void newTradeRef(){//NEW TRADE REFERENCE
		ci.setComponentsEditable(pnlNorth, true);
		cmbStatus.setEnabled(true);
		try{
			tblTradeRef.clearSelection();
		} catch (ArrayIndexOutOfBoundsException e) {}
		tblTradeRef.setEnabled(false);
		rowHeaderRef_TradeRef.setEnabled(false);
	}

	public void editTradeRef(){//EDITING FOR THE TRADE REFERENCE
		if(tblTradeRef.getSelectedRows().length ==1){
			ci.setComponentsEditable(pnlNorth, true);
			cmbStatus.setEnabled(true);
			tblTradeRef.setEnabled(false);
			rowHeaderRef_TradeRef.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollTradeRefList, "Please select only one row to edit");
			tblTradeRef.clearSelection();
		}
	}

	public Boolean saveTradeRef(String entity_id){//SAVING AND UPDATING FOR THE TRADE REFERENCE
		pgUpdate db = new pgUpdate();
		String supp_name = txtName.getText().trim().replace("'", "''");
		String supp_addr = txtAddress.getText().trim().replace("'", "''");
		String supp_contact = txtContactNum.getText();
		String supp_stat = (String) cmbStatus.getSelectedItem();

		//UPDATING
		if(tblTradeRef.getSelectedRows().length ==1){
			int row = tblTradeRef.getSelectedRow();
			Integer rec_id = (Integer) modelTradeRef.getValueAt(row, 0);
			if(isTradeRefExisting(entity_id, rec_id)){
				String sql = "UPDATE rf_trade_references SET supplier_name= '"+supp_name+"', address= '"+supp_addr+"', tel_no= '"+supp_contact+"', \n"+
						   	 "status_id = (case when '"+supp_stat+"' = 'Active' then 'A' else 'I' end) \n" + 
							 "WHERE entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Trade Reference has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_trade_references(entity_id, supplier_name, address, tel_no, status_id, rowguid) \n" + 
						 "VALUES ('"+entity_id+"', '"+ supp_name +"', '"+ supp_addr +"', '"+ supp_contact +"', (case when '"+ supp_stat +"' = 'Active' then 'A' else 'I' end), '')";

			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Trade Reference has been Saved");
		}
		
		db.commit();
		
		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);
		tblTradeRef.clearSelection();
		tblTradeRef.setEnabled(true);
		rowHeaderRef_TradeRef.setEnabled(true);
		return true;
	}
	
	public void cancelTradRef(){//CANCELATION FOR TRADE REFERENCE
		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);
		clearFields();
		tblTradeRef.clearSelection();
		tblTradeRef.setEnabled(true);
		rowHeaderRef_TradeRef.setEnabled(true);
	}

	public void clearFields(){//CLEARS THE FIELDS IN THIS PANEL
		txtName.setText("");
		txtContactNum.setText("");
		txtAddress.setText("");
		tblTradeRef.clearSelection();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
