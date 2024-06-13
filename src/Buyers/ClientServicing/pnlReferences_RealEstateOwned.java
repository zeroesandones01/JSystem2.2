package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

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
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelRealEstateOwned;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import components._JTableMain;

/**
 * @author JOHN LESTER FATALLO
 */

public class pnlReferences_RealEstateOwned extends JPanel implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -388702988740383453L;

	private JPanel pnlNorth;

	private JPanel pnlLocationUpper;
	private JPanel pnlLocationUpperWest;
	private JLabel lblLocation;
	private JPanel pnlLocationUpperCenter;
	private JTextField txtLocation;

	private JPanel pnlNorthLower;
	private JPanel pnlPropertyType;

	private JPanel pnlPropertyTypeWest;
	private JLabel lblPropertyType;
	private JLabel lblAcquisitionCost;
	private JLabel lblMortgageBal;
	private JLabel lblStatus;

	private JPanel pnlPropertyTypeCenter;
	private JTextField txtPropertyType;
	private _JXFormattedTextField txtAcquisitionCost;
	private _JXFormattedTextField txtMortgageBal;
	private JComboBox cmbStatus;

	private JPanel pnlTCTNum;
	private JPanel pnlTCTNumWest;
	private JLabel lblTCTNum;
	private JLabel lblMarketValue;
	private JLabel lblRentalIncome;
	private JLabel lblSeparator1;
	private JLabel lblSeparator2;
	private JLabel lblSeparator3;
	private JLabel lblSeparator4;
	private JLabel lblSeparator5;
	private JLabel lblSeparator6;

	private JPanel pnlTCTNumCenter;
	private JTextField txtTCTNum;
	private _JXFormattedTextField txtMarketValue;
	private _JXFormattedTextField txtRentalIncome;

	private JScrollPane scrollRealEstateList;
	private modelRealEstateOwned modelRealEstate;
	private _JTableMain tblRealEstate;
	private JList rowHeaderRef_RealEstateOwned;

	private ClientInformation ci;

	public pnlReferences_RealEstateOwned(ClientInformation ci) {
		this.ci=ci;
		initGUI();
	}

	public pnlReferences_RealEstateOwned(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlReferences_RealEstateOwned(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlReferences_RealEstateOwned(LayoutManager layout,
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
			pnlNorth.setPreferredSize(new Dimension(648, 161));
			{
				pnlLocationUpper = new JPanel(new BorderLayout(3, 3));
				pnlNorth.add(pnlLocationUpper, BorderLayout.NORTH);
				pnlLocationUpper.setPreferredSize(new Dimension(648, 30));
				{
					pnlLocationUpperWest = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlLocationUpper.add(pnlLocationUpperWest, BorderLayout.WEST);
					pnlLocationUpperWest.setPreferredSize(new Dimension(121, 30));
					{
						lblLocation = new JLabel(" *Location");
						pnlLocationUpperWest.add(lblLocation);
						//lblLocation.setPreferredSize(new Dimension(123, 30));
					}
				}
				{
					pnlLocationUpperCenter = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlLocationUpper.add(pnlLocationUpperCenter, BorderLayout.CENTER);
					{
						txtLocation = new JTextField();
						pnlLocationUpperCenter.add(txtLocation);
					}
				}
			}
			{//Start of Edit
				pnlNorthLower = new JPanel(new GridLayout(1, 2, 3, 3));
				pnlNorth.add(pnlNorthLower, BorderLayout.SOUTH);
				pnlNorthLower.setPreferredSize(new Dimension(648, 125));
				{
					JPanel pnlLeft= new JPanel(new SpringLayout());
					pnlNorthLower.add(pnlLeft, BorderLayout.WEST);
					{
						lblPropertyType = new JLabel("*Property Type");
						pnlLeft.add(lblPropertyType);
					}
					{
						txtPropertyType = new JTextField();
						pnlLeft.add(txtPropertyType);
					}
					{
						lblAcquisitionCost = new JLabel("Acquisition Cost");
						pnlLeft.add(lblAcquisitionCost);
					}
					{
						JPanel pnlAcquisition = new JPanel(new BorderLayout());
						pnlLeft.add(pnlAcquisition);
						{
							txtAcquisitionCost = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlAcquisition.add(txtAcquisitionCost, BorderLayout.WEST);
							txtAcquisitionCost.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtAcquisitionCost.setText("0.00");
							txtAcquisitionCost.setPreferredSize(new Dimension(120, 0));
						}
					}
					{
						lblMortgageBal = new JLabel("Mortgage Balance");
						pnlLeft.add(lblMortgageBal);
					}
					{
						JPanel pnlMortBal = new JPanel(new BorderLayout());
						pnlLeft.add(pnlMortBal);
						{
							txtMortgageBal = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlMortBal.add(txtMortgageBal, BorderLayout.WEST);
							txtMortgageBal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtMortgageBal.setText("0.00");
							txtMortgageBal.setPreferredSize(new Dimension(120, 0));
						}
					}
					{
						lblStatus = new JLabel("Status");
						pnlLeft.add(lblStatus);
					}
					{
						JPanel pnlRealEstateStatus = new JPanel(new BorderLayout());
						pnlLeft.add(pnlRealEstateStatus);
						{
							cmbStatus = new JComboBox(new String[] { "Active", "Inactive" });
							pnlRealEstateStatus.add(cmbStatus, BorderLayout.WEST);
							cmbStatus.setPreferredSize(new Dimension(120, 0));
							cmbStatus.setEnabled(false);
						}
					}
					SpringUtilities.makeCompactGrid(pnlLeft, 4, 2, 5, 5, 5, 5, false);
				}
				{
					JPanel pnlRight= new JPanel(new SpringLayout());
					pnlNorthLower.add(pnlRight);
					{
						lblTCTNum = new JLabel("*TCT No.");
						pnlRight.add(lblTCTNum);
					}
					{
						txtTCTNum = new JTextField();
						pnlRight.add(txtTCTNum);
					}
					{
						lblMarketValue = new JLabel("Market Value");
						pnlRight.add(lblMarketValue);
					}
					{
						JPanel pnlMarketValue = new JPanel(new BorderLayout());
						pnlRight.add(pnlMarketValue);
						{
							txtMarketValue = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlMarketValue.add(txtMarketValue, BorderLayout.WEST);
							txtMarketValue.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtMarketValue.setText("0.00");
							txtMarketValue.setPreferredSize(new Dimension(120, 0));
						}
					}
					{
						lblRentalIncome = new JLabel("Rental Income");
						pnlRight.add(lblRentalIncome);
					}
					{
						JPanel pnlRentalIncome = new JPanel(new BorderLayout());
						pnlRight.add(pnlRentalIncome);
						{
							txtRentalIncome = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlRentalIncome.add(txtRentalIncome, BorderLayout.WEST);
							txtRentalIncome.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtRentalIncome.setText("0.00");
							txtRentalIncome.setPreferredSize(new Dimension(120, 0));
						}
					}
					{
						JPanel pnlSeparator1 = new JPanel();
						pnlRight.add(pnlSeparator1);
					}
					{
						JPanel pnlSeparator2 = new JPanel();
						pnlRight.add(pnlSeparator2);
					}

					SpringUtilities.makeCompactGrid(pnlRight, 4, 2, 5, 5, 5, 5, false);
				}
			}//End of Edit
		}
		{
			scrollRealEstateList = new JScrollPane();
			this.add(scrollRealEstateList, BorderLayout.CENTER);
			scrollRealEstateList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelRealEstate = new modelRealEstateOwned();
				tblRealEstate = new _JTableMain(modelRealEstate);
				scrollRealEstateList.setViewportView(tblRealEstate);
				tblRealEstate.hideColumns("Rec. ID");
				tblRealEstate.setSortable(false);
				tblRealEstate.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				modelRealEstate.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						//_OrderOfPayment.totalPayments(modelConnList, modelPaymentListTotal);

						((DefaultListModel)rowHeaderRef_RealEstateOwned.getModel()).addElement(modelRealEstate.getRowCount());
						scrollRealEstateList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRealEstate.getRowCount())));


						if(modelRealEstate.getRowCount() == 0){
							rowHeaderRef_RealEstateOwned.setModel(new DefaultListModel());
						}
					}
				});
				tblRealEstate.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if(!arg0.getValueIsAdjusting()){
							if(tblRealEstate.getSelectedRows().length ==1){
								int row = tblRealEstate.getSelectedRow();

								String location = (String) modelRealEstate.getValueAt(row, 1);
								String property_type = (String) modelRealEstate.getValueAt(row, 2);
								String tct_no = (String) modelRealEstate.getValueAt(row, 3);
								BigDecimal acquisition_cost = (BigDecimal) modelRealEstate.getValueAt(row, 4);
								BigDecimal market_value = (BigDecimal) modelRealEstate.getValueAt(row, 5);
								BigDecimal mortgage_balance = (BigDecimal) modelRealEstate.getValueAt(row, 6);
								BigDecimal rental_income = (BigDecimal) modelRealEstate.getValueAt(row, 7);
								String real_estate_owned_stat = (String) modelRealEstate.getValueAt(row, 8);

								txtLocation.setText(location);
								txtPropertyType.setText(property_type);
								txtTCTNum.setText(tct_no);
								txtAcquisitionCost.setValue(acquisition_cost);
								txtMarketValue.setValue(market_value);
								txtMortgageBal.setValue(mortgage_balance);
								txtRentalIncome.setValue(rental_income);

								if (real_estate_owned_stat.equals("A")){
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
				rowHeaderRef_RealEstateOwned = tblRealEstate.getRowHeader();
				rowHeaderRef_RealEstateOwned.setModel(new DefaultListModel());
				scrollRealEstateList.setRowHeaderView(rowHeaderRef_RealEstateOwned);
				scrollRealEstateList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtLocation, txtPropertyType, txtAcquisitionCost, txtMortgageBal, 
				cmbStatus, txtTCTNum, txtMarketValue, txtRentalIncome));
		ci.setComponentsEditable(pnlNorth, false);
	}//END OF INIT GUI

	public void displayRealEstateOwned(String entity_id){//DISPLAYS THE REAL ESTATE OWNED FOR THE SELECTED ENTITY ID
		modelRealEstate.clear();

		String sql = "SELECT rec_id, location, property_type, tct_no, acquisition_cost, \n" + 
					 "market_value, mortgage_bal, rental_income, status_id \n" + 
					 "FROM rf_real_estate_owned where entity_id = '"+entity_id +"'";

		FncSystem.out("Display Real Estate Owned", sql);	
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact number of columns and column types.
				modelRealEstate.addRow(db.getResult()[x]);
			}
			scrollRealEstateList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRealEstate.getRowCount())));
			tblRealEstate.packAll();
		}
	}

	public boolean toSave(){//CHECKING OF THE REQUIRED FIELDS BEFORE SAVING
		if (txtLocation.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblLocation.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtPropertyType.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblPropertyType.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtTCTNum.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblTCTNum.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	//CHECKS IF REAL ESTATE IS ALREADY EXISTING
	public boolean isRealEstateExisting(String entity_id, Integer rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_real_estate_owned where entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	public void newRealEstate(){//NEW REAL ESTATE
		ci.setComponentsEditable(pnlNorth, true);
		cmbStatus.setEnabled(true);
		try{
			tblRealEstate.clearSelection();
		}catch (ArrayIndexOutOfBoundsException e){}
		tblRealEstate.setEnabled(false);
		rowHeaderRef_RealEstateOwned.setEnabled(false);
	}

	public void editRealEstate(){//EDITING FOR THE REAL ESTATE
		if(tblRealEstate.getSelectedRows().length == 1){
			ci.setComponentsEditable(pnlNorth, true);
			cmbStatus.setEnabled(true);
			tblRealEstate.setEnabled(false);
			rowHeaderRef_RealEstateOwned.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollRealEstateList, "Please select only one row to edit");
			tblRealEstate.clearSelection();
		}
	}

	public boolean saveRealEstateOwned(String entity_id){//SAVING AND UPDATING OF THE REAL ESTATE
		pgUpdate db = new pgUpdate();

		String location = txtLocation.getText().trim().replace("'", "''");
		String property_type = txtPropertyType.getText().trim().replace("'", "''");
		String tct_no = txtTCTNum.getText();
		BigDecimal acquisition_cost = (BigDecimal) txtAcquisitionCost.getValued();
		BigDecimal market_value = (BigDecimal) txtMarketValue.getValued();
		BigDecimal mortgage_bal = (BigDecimal) txtMortgageBal.getValued();
		BigDecimal rental_income = (BigDecimal) txtRentalIncome.getValued();
		String real_estate_owned_status = (String) cmbStatus.getSelectedItem();

		//UPDATE
		if(tblRealEstate.getSelectedRows().length ==1){
			int row = tblRealEstate.getSelectedRow();
			Integer rec_id = (Integer) modelRealEstate.getValueAt(row, 0);
			if(isRealEstateExisting(entity_id, rec_id)){
				String sql = "UPDATE rf_real_estate_owned\n" + 
							 "SET location='"+location+"', property_type='"+property_type+"', tct_no= '"+tct_no+"', \n" + 
							 "acquisition_cost= "+acquisition_cost+", market_value= "+market_value+", mortgage_bal= "+mortgage_bal+", rental_income= "+rental_income+", \n" + 
							 "status_id= (case when '"+real_estate_owned_status+"' = 'Active' then 'A' else 'I' end)\n" + 
							 "WHERE entity_id = '"+entity_id+"' and rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Real Estate has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_real_estate_owned(entity_id, location, property_type, tct_no, acquisition_cost, \n" + 
						 "market_value, mortgage_bal, rental_income, status_id, rowguid)\n" + 
						 "VALUES ('"+ entity_id +"', '"+ location +"', '"+ property_type +"', '"+ tct_no +"', coalesce("+ acquisition_cost +", 0.00), \n" + 
						 "coalesce("+ market_value +", 0.00), coalesce("+ mortgage_bal +", 0.00), coalesce("+ rental_income +",0.00) , (case when '"+ real_estate_owned_status +"' = 'Active' then 'A' else 'I' end), '')";

			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Real Estate has been Saved");
		}
		db.commit();

		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);

		tblRealEstate.clearSelection();
		tblRealEstate.setEnabled(true);
		rowHeaderRef_RealEstateOwned.setEnabled(true);
		return true;
	}

	public void cancelRealEstate(){//CANCELATION OF THE REAL ESTATE
		ci.setComponentsEditable(pnlNorth, false);
		cmbStatus.setEnabled(false);
		clearFields();
		tblRealEstate.clearSelection();
		tblRealEstate.setEnabled(true);
		rowHeaderRef_RealEstateOwned.setEnabled(true);
	}

	public void clearFields(){//CLEARS THE FIELDS IN THIS PANEL
		txtLocation.setText("");
		txtPropertyType.setText("");
		txtAcquisitionCost.setText("0.00");
		txtTCTNum.setText("");
		txtMortgageBal.setText("0.00");
		txtMarketValue.setText("0.00");
		txtRentalIncome.setText("0.00");
		tblRealEstate.clearSelection();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
