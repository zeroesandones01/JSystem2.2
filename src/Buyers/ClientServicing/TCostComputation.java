package Buyers.ClientServicing;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup._JLookup;
import components._JTableMain;
import tablemodel.modelTCostCompu;

public class TCostComputation extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8600135017373881213L;
	
	static JButton btnAdd;
	private static JButton btnPreview;
	
	private JPanel pnlNorth;
	
	private static _JLookup lookupDoneBy;
	private static _JLookup lookupRecipient;
	
	private JPanel pnlCenter;
	
	private static JScrollPane scrollCenter;
	private static _JTableMain tblTCost;
	private static modelTCostCompu modelTCost;
	private static JList rowHeaderTCost;

	private JPanel pnlSouth;
	private static String client_name = "";
	private static String unitno = "";
	private static String housemodel = "";
	private static String selling_amt = "";
	private static String lot_area = "";
	public static JCheckBox chkECAR;

	private static String strProject;
	private static String entityid;
	private static String pblid;
	private static Integer seqno;

	public TCostComputation() {
		initThis();
	}

	public TCostComputation(LayoutManager layout) {
		super(layout);
		initThis();
	}

	public TCostComputation(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initThis();
	}

	public TCostComputation(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initThis();
	}
	
	private void initThis() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(800, 250));
		{
			pnlCenter = new JPanel();
			this.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setLayout(new BorderLayout(5, 5));
			{
				pnlNorth = new JPanel();
				pnlCenter.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setLayout(new BorderLayout(3, 3));
				pnlNorth.setPreferredSize(new Dimension(416, 125));
				{
					JPanel pnlForECAR = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlForECAR, BorderLayout.NORTH);
					{
						chkECAR = new JCheckBox("For ECAR");
						pnlForECAR.add(chkECAR);
						chkECAR.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								if (e.getStateChange() == ItemEvent.SELECTED) {
									displayTCost(entityid, strProject, pblid, seqno, true);
								}else {
									displayTCost(entityid, strProject, pblid, seqno, false);									displayStatus(modelTCost, rowHeaderTCost, entityid, strProject, pblid, seqno, false);
								}
							}
						});
					}
				}
				{
					scrollCenter = new JScrollPane();
					pnlNorth.add(scrollCenter, BorderLayout.CENTER);
					{
						modelTCost = new modelTCostCompu();
						
						tblTCost = new _JTableMain(modelTCost);
						scrollCenter.setViewportView(tblTCost);
						tblTCost.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						
						/*tblTCost.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								try {
									if(!arg0.getValueIsAdjusting()){
										int row = tblTCost.convertRowIndexToModel(tblTCost.getSelectedRow());
										
										String insurance_co_id = (String) modelTCost.getValueAt(row, 1);
										String insurance_co_name = (String) modelTCost.getValueAt(row, 2);
										BigDecimal amount = (BigDecimal) modelTCost.getValueAt(row, 5);
										String department_id = (String) modelTCost.getValueAt(row, 6);
										String department_name = (String) modelTCost.getValueAt(row, 7);
										String location_name = (String) modelTCost.getValueAt(row, 9);
										
										btnState(true, true, true, false, false);

										lookupDoneBy.setValue(insurance_co_id);
										txtDoneBy.setText(insurance_co_name);
										txtAmount.setValue(amount);
										lookupRecipient.setValue(department_id);
										txtRecipient.setText(department_name);
										txtRemarks.setText(location_name);
									}
								} catch (ArrayIndexOutOfBoundsException e) { }
							}
						});*/
						tblTCost.setSortable(false);
						
						modelTCost.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {

								((DefaultListModel)rowHeaderTCost.getModel()).addElement(modelTCost.getRowCount());

								if(modelTCost.getRowCount() == 0){
									rowHeaderTCost.setModel(new DefaultListModel());
								}
							}
						});
					}
					{
						rowHeaderTCost = tblTCost.getRowHeader();
						rowHeaderTCost.setModel(new DefaultListModel());
						scrollCenter.setRowHeaderView(rowHeaderTCost);
						scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					pnlSouth = new JPanel(new GridLayout(1,4,3,3));
					pnlCenter.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
					pnlSouth.setPreferredSize(new Dimension(400, 40));
					{
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview, BorderLayout.EAST);
						btnPreview.setActionCommand(btnPreview.getText());
						btnPreview.addActionListener(this);
					}
				}
			}
		}
		btnState(true);
		
		//this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtBondNo, txtAmount, lookupInsuranceCo, lookupDepartmentConcerned, lookupPresentLocation, (JTextField) dateFrom.getDateEditor(), (JTextField) dateTo.getDateEditor()));
	}
	
	public static void btnState(Boolean sPreview) {
		btnPreview.setEnabled(sPreview);
	}
	
	public static void refresh(boolean isNewSuretyBond) {
		
		if(isNewSuretyBond){
			((DefaultListModel)rowHeaderTCost.getModel()).removeAllElements();
			modelTCost.clear();
			scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCost.getRowCount())));
			tblTCost.packAll();
		}
	}

	public void panelLooper(Container panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			if(comp instanceof JPanel){
				panelLooper((JPanel) comp, enable);
			}else if(comp instanceof JScrollPane){
				if(comp.getName().equals("scrollPhase")){
					panelLooper((JScrollPane) comp, enable);
				}
			}else{
				if(comp.getName() != null){
					comp.setEnabled(!enable);
				}else{
					if(panel instanceof JScrollPane){
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					}else{
						comp.setEnabled(enable);
					}
				}
			}
		}
	}
	public void newSuretyBond() {
		refresh(false);
		btnState(true);
	}
	
	public void cancelSuretyBond() {
		refresh(false);
		btnState(true);
	}
	
	public void disableSuretyBond() {
		refresh(true);
		btnState(true);
	}
	public void editSuretyBond() {
		refresh(true);
		btnState(true);
	}
	
	/*public void displaySuretyBonds(String contract_no) {
		this.contract_no = contract_no;
		refreshSuretyBond(true);
		
		_SuretyBond.displaySuretyBondDetails(modelSuretyBondMain, rowHeaderSuretyBondMain, contract_no);
		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSuretyBondMain.getRowCount())));
		tblSuretyBondMain.packAll();
		
		//setComponentsEnabled(true);
		btnState(true, false, false, false, false);
	}*/
	
	public boolean validatingTCost(){
		if(lookupRecipient.getValue() == ""){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select recipient", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupDoneBy.getValue() == ""){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select description.", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		String emp_code = (String) UserInfo.EmployeeCode;
		String emp_name = (String) UserInfo.FullName;
		Boolean for_ecar = chkECAR.isSelected();
		
		if(actionCommand.equals("Preview")){
			ArrayList<String> iftrue = new ArrayList<String>();
			String SQL ="";
			
			BigDecimal total_tcost = new BigDecimal("0.00");
			for (int i = 0; i < modelTCost.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelTCost.getValueAt(i, 0);
				
				if (SelectedItem) {
					iftrue.add(modelTCost.getValueAt(i, 1).toString());
					
					String particular = (String) modelTCost.getValueAt(i, 1);
					
					
					BigDecimal tcost = (BigDecimal) modelTCost.getValueAt(i, 2);
					
					
					if(particular.trim().equals("MISCELLANEOUS") == false) {
						total_tcost = total_tcost.add(tcost);
					}
					
					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +	
							
							"SELECT \n" +
							"'"+modelTCost.getValueAt(i, 1)+"' AS particular,\n" + 
							" case when '"+particular+"' = 'MISCELLANEOUS' then ROUND(("+total_tcost+" * .03), 2) else "+modelTCost.getValueAt(i, 2)+" end AS amount \n";
				}
			}
			
			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(getRootPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
				return;
			}
			
			System.out.printf("Display value of SQL: %s", SQL);
			
			/*ADDED BY JED 2021-03-11 : TO GET THE COMPANY AND PROJECT NAME*/
			Object[] ch_ord = getHeaderTitle(strProject); 
			
			String proj_name = (String) ch_ord[0];
			String company_name = (String) ch_ord[1];
			/*ADDED BY JED 2021-03-11 : TO GET THE COMPANY AND PROJECT NAME*/
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("company", company_name);
			mapParameters.put("proj_name", proj_name);
			mapParameters.put("client_id", client_name.replace("[", "").replace("]", ""));
			mapParameters.put("unit", unitno.replace("[", "").replace("]", ""));
			mapParameters.put("model", housemodel.replace("[", "").replace("]", ""));
			mapParameters.put("amount", selling_amt.replace("[", "").replace("]", ""));
			mapParameters.put("lotarea", lot_area);
			mapParameters.put("prepared_by", UserInfo.FullName);
			mapParameters.put("for_ecar", for_ecar);
			
			FncReport.generateReport("/Reports/rptTCostComputation.jasper", "Transfer Cost Computation", mapParameters, SQL);
		}
	}

	public static void displayTCost(String entity_id, String proj_id ,String pbl, Integer seq_no, Boolean isecar) {
		refresh(true);
		
		entityid = entity_id;
		strProject = proj_id;
		pblid = pbl;
		seqno = seq_no;
		
		displayStatus(modelTCost, rowHeaderTCost, entity_id, proj_id ,pbl, seq_no, isecar);
		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCost.getRowCount())));
		tblTCost.packAll();
		
		//setComponentsEnabled(true);
		btnState(true);
	}
	
	/*ADDED BY JED 2021-03-11 : TO GET THE COMPANY AND PROJECT NAME*/
	private static Object[] getHeaderTitle(String proj_id) {
		
		String sql = "select \n" + 
				"proj_name,\n" + 
				"company_name\n" + 
				"from mf_project a\n" + 
				"left join mf_company b on a.co_id = b.co_id\n" + 
				"where a.proj_id = '"+proj_id+"'";
		

		FncSystem.out("Company/Project", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	public static void displayClient(String client, String unit, String house_model, String amount, String lotarea) {
		client_name = client;
		unitno = unit;
		housemodel = house_model;
		selling_amt = amount;
		lot_area = lotarea;
		
	}
	
	private static void displayStatus(modelTCostCompu model, JList rowHeader, String entity_id ,String proj_id,String pbl, Integer seq_no, Boolean isecar) {
		model.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		//String sql = "select false, tcostdtl_desc, tcostdtl_amt, null, remarks from mf_transfer_cost_dl where status_id = 'A' and for_tcostcomp is true order by tcostdtl_desc;";
		
		String SQL = "SELECT * FROM view_card_tcost_computation_v2('"+entity_id+"', '"+proj_id+"', '"+pbl+"', "+seq_no+", "+isecar+");\n" + 
				"";
		System.out.printf("SQL: %s%n%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				//listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public void cancelStatus() {
		refresh(false);
		btnState(true);
		chkECAR.setSelected(false);
	}
}
