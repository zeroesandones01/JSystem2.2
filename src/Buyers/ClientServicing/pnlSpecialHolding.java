package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup.ResetEvent;
import Lookup.ResetListener;
import Lookup._JLookup;
import Lookup._JLookupTable;

import components._JInternalFrame;
import components._JXTextField;

public class pnlSpecialHolding extends JPanel implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7179374196951209049L;

	private JLabel lblTransaction;
	private JComboBox cmbTransaction;
	private static _JXTextField lblClientSeqNo;
	private JLabel lblCompany;
	private static _JLookup lookupCompany;
	private static _JXTextField txtCompany;
	private JLabel lblProject;
	private static _JLookup lookupProject;
	private static _JXTextField txtProject;
	private JLabel lblPBL;
	private static _JLookup lookupPBL;
	private static _JXTextField txtPBL;
	private JLabel lblModel;
	private static _JXTextField txtModel;
	private static _JXTextField txtModelName;
	private JLabel lblSalesAgent;
	private static _JLookup lookupSalesAgent;
	private static _JXTextField txtSalesAgent;
	private JLabel lblSalesDivision;
	private static _JLookup lookupSalesDivision;
	private static _JXTextField txtSalesDivisionName;
	private JLabel lblSalesDivisionGroup;
	private static _JLookup lookupSalesDivisionGroup;
	private static _JXTextField txtSalesDivisionGroupName;
	private JLabel lblSellingPrice;
	private static _JXFormattedTextField txtSellingPrice;
	private static JXTextArea txtRemarks;
	private JPanel pnlCenter;
	private JLabel lblParticular;
	private static _JLookup lookupPayment1;
	private JLabel lblCashAmount;
	private static _JXFormattedTextField txtCashAmount;
	static String receipt_no = "";
	private static String receipt_no2 = "";
	static String entity_id = "";
	private Special_Holding har;
	private static String pay_rec_id = "";

	static _JLookup lookupPayment2;

	private static _JXFormattedTextField txtCashAmount2;

	public pnlSpecialHolding(Special_Holding har) {
		this.har = har;

		initGUI();
		setHoldingEnabled(false);
	}

	public pnlSpecialHolding(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlSpecialHolding(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlSpecialHolding(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(600, 400));
		{
			JPanel pnlHoldingDetails = new JPanel(new BorderLayout(3, 3));
			this.add(pnlHoldingDetails, BorderLayout.CENTER);
			pnlHoldingDetails.setBorder(components.JTBorderFactory.createTitleBorder("Holding Details"));
			pnlHoldingDetails.setPreferredSize(new Dimension(0, 310));
			{
				JPanel pnlHoldingDetailsNorth = new JPanel(new BorderLayout(3, 3));
				pnlHoldingDetails.add(pnlHoldingDetailsNorth, BorderLayout.CENTER);
				{
					JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
					pnlHoldingDetailsNorth.add(pnlNorth, BorderLayout.CENTER);
					{
						JPanel pnlNorthWest = new JPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
						{
							JPanel pnlLabels = new JPanel(new GridLayout(9, 1, 3, 3));
							pnlNorthWest.add(pnlLabels, BorderLayout.WEST);
							pnlLabels.setPreferredSize(new Dimension(100, 0)); 
							{
								lblTransaction = new JLabel("Transaction");
								pnlLabels.add(lblTransaction);
							}
							{
								lblCompany = new JLabel("*Company");
								pnlLabels.add(lblCompany);
							}
							{
								lblProject = new JLabel("*Project");
								pnlLabels.add(lblProject);
							}
							{
								lblPBL = new JLabel("*Ph/Bl/Lt");
								pnlLabels.add(lblPBL);
							}
							{
								lblModel = new JLabel("Model");
								pnlLabels.add(lblModel);
							}
							{
								lblSalesAgent = new JLabel("Sales Agent");
								pnlLabels.add(lblSalesAgent);
							}
							{
								lblSalesDivision = new JLabel("*Sales Division");
								pnlLabels.add(lblSalesDivision);
							}
							{
								lblSalesDivisionGroup = new JLabel("*Sales Group");
								pnlLabels.add(lblSalesDivisionGroup);
							}
							{
								lblSellingPrice = new JLabel("Selling Price");
								pnlLabels.add(lblSellingPrice, BorderLayout.WEST);
								lblSellingPrice.setPreferredSize(new Dimension(110, 0));
							}
						}
						{
							JPanel pnlLookups = new JPanel(new GridLayout(9, 1, 3, 3));
							pnlNorthWest.add(pnlLookups, BorderLayout.CENTER);
							{
								cmbTransaction = new JComboBox(new DefaultComboBoxModel(getTransactions()));
								pnlLookups.add(cmbTransaction);
								cmbTransaction.setEnabled(false);
								cmbTransaction.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										JComboBox combo = ((JComboBox)arg0.getSource());

										if(combo.isEnabled()){
											clearFields();
											setHoldingEnabled(false);
											setHoldingEditable(false);

											String selectedTransaction = (String) combo.getSelectedItem();
											if(selectedTransaction.equals("Hold")){
												newHolding(false);
											}

											combo.setToolTipText(selectedTransaction);

											lblTransaction.setEnabled(true);
											cmbTransaction.setEnabled(false);
										}
									}
								});
							}
							{
								lookupCompany = new _JLookup(null, "Company", 0);
								pnlLookups.add(lookupCompany);
								lookupCompany.setPrompt("Company ID");
								lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String co_id = (String) data[0];
											String co_name = (String) data[1];

											txtCompany.setText(co_name);
											lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(co_id));
										}
									}
								});
							}
							{
								lookupProject = new _JLookup(null, "Project", "Please select company.");
								pnlLookups.add(lookupProject);
								lookupProject.setPrompt("Project ID");
								lookupProject.setReturnColumn(0);
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];

											txtProject.setText(proj_name);
											lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(proj_id));
										}
									}
								});
								lookupProject.addFocusListener(new FocusListener() {
									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub

									}

									@Override
									public void focusGained(FocusEvent e) {
										lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
									}
								});
							}
							{								
								lookupPBL = new _JLookup(null, "Ph/Bl/Lt");
								pnlLookups.add(lookupPBL);
								lookupPBL.setSizeDialog(new Dimension(800, 420));
								lookupPBL.setPrompt("Unit ID");
								lookupPBL.setFilterName(true);
								lookupPBL.addFocusListener(new FocusListener() {

									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub

									}

									@Override
									public void focusGained(FocusEvent e) {
										if(lookupPBL.getValue() == null){
											String selected_transaction = (String) cmbTransaction.getSelectedItem();											
											setHoldingEnabled(false);
											setHoldingEditable(false);
											System.out.printf("Display selected: %s%n", selected_transaction);
											if(selected_transaction.equals("Hold")){
												newHolding(false);
											}
										}
									}
								});
								lookupPBL.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();

										FncSystem.out("Display SQL for Lookup of PBL", lookupPBL.getLookupSQL());

										if(data != null){
											String unit_id = (String) data[0];
											
											if(_HoldingAndReservation.isOpen(lookupProject.getValue(), unit_id)){												

												lookupPBL.setValue(unit_id);
												String unit_description = (String) data[1];
												String model_id = (String) data[2];
												String model_description = (String) data[3];
												BigDecimal gsp = (BigDecimal) data[6];

												txtPBL.setText(unit_description);
												txtModel.setText(model_id);
												txtModelName.setText(model_description);
												txtSellingPrice.setValue(gsp);

												try {
													String company_id = (String) data[11];
													String company_name = (String) data[12];
													String project_id = (String) data[4];
													String project_name = (String) data[5];
													String division_id = (String) data[7];
													String division_name = (String) data[8];
													String client_seqno = (String) data[14];
													String remarks = (String) data[16];

													lblClientSeqNo.setText(String.format("[ %s ]", client_seqno));
													lookupCompany.setValue(company_id);
													txtCompany.setText(company_name);
													lookupProject.setValue(project_id);
													txtProject.setText(project_name);
													lookupSalesDivision.setValue(division_id);
													txtSalesDivisionName.setText(division_name);
													txtRemarks.setText(remarks);

												} catch (ClassCastException e) {
												} catch (ArrayIndexOutOfBoundsException e2) { }
											}else{
												lookupPBL.setValue(null);
												JOptionPane.showMessageDialog(null, "Unit has been hold already", "Unit", JOptionPane.WARNING_MESSAGE);
											}
										}
									}
								});
							}
							{
								txtModel = new _JXTextField();
								pnlLookups.add(txtModel);
								txtModel.setPrompt("Model ID");
								txtModel.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								lookupSalesAgent = new _JLookup(null, "Sales Agent", 0);
								pnlLookups.add(lookupSalesAgent);
								lookupSalesAgent.setLookupSQL(_HoldingAndReservation.AGENTS());
								lookupSalesAgent.setPrompt("Sales Agent ID");
								lookupSalesAgent.setFilterName(true);
								lookupSalesAgent.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String agent_name = (String) data[1];
											String div_id = (String) data[2];
											String div_name = (String) data[3];
											String div_alias = (String) data[4];
											String dept_id = (String) data[5];
											String dept_name = (String) data[6];

											txtSalesAgent.setText(agent_name);
											lookupSalesDivision.setValue(div_id);
											txtSalesDivisionName.setText(String.format("%s (%s)", div_name, div_alias));
											lookupSalesDivisionGroup.setValue(dept_id);
											lookupSalesDivisionGroup.setLookupSQL(_HoldingAndReservation.DEPARTMENT(div_id));
											txtSalesDivisionGroupName.setText(dept_name);

											FncSystem.out("Display SQL Look For Agents", lookupSalesAgent.getLookupSQL());
										}
									}
								});
							}
							{
								lookupSalesDivision = new _JLookup(null, "Sales Division", 0);
								pnlLookups.add(lookupSalesDivision);
								lookupSalesDivision.setLookupSQL(_HoldingAndReservation.DIVISION());
								lookupSalesDivision.setPrompt("Sales Division ID");
								lookupSalesDivision.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String div_id = (String) data[0];
											String div_name = (String) data[1];

											lookupSalesAgent.setValue(null);
											txtSalesAgent.setText("");
											txtSalesDivisionName.setText(div_name);
											lookupSalesDivisionGroup.setLookupSQL(_HoldingAndReservation.DEPARTMENT(div_id));
											lookupSalesDivisionGroup.setValue(null);
											txtSalesDivisionGroupName.setText("");

											FncSystem.out("Display Lookup For Sales Division", lookupSalesDivision.getLookupSQL());
										}
									}
								});
								lookupSalesDivision.addResetListener(new ResetListener() {
									public void resetPerformed(ResetEvent event) {
										_JLookup lookup = (_JLookup) event.getSource();
										lookup.setValue(null);
										txtSalesDivisionName.setText("");
									}
								});
							}
							{
								lookupSalesDivisionGroup = new _JLookup(null, "Sales Group", 0, "Please select Sales Division first.");
								pnlLookups.add(lookupSalesDivisionGroup);
								lookupSalesDivisionGroup.setPrompt("Sales Group ID");
								lookupSalesDivisionGroup.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String div_name = (String) data[1];

											txtSalesDivisionGroupName.setText(div_name);

											FncSystem.out("Display SQL Lookup for Sales Group", lookupSalesDivisionGroup.getLookupSQL());
										}
									}
								});
								lookupSalesDivisionGroup.addResetListener(new ResetListener() {
									public void resetPerformed(ResetEvent event) {
										_JLookup lookup = (_JLookup) event.getSource();
										lookup.setValue(null);
										txtSalesDivisionGroupName.setText("");
									}
								});
							}
							{
								txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlLookups.add(txtSellingPrice, BorderLayout.CENTER);
								txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSellingPrice.setEditable(false);
							}
						}
					}
					{
						JPanel pnlNorthCenter = new JPanel(new GridLayout(9, 1, 3, 3));
						pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
						{
							lblClientSeqNo = new _JXTextField("");
							pnlNorthCenter.add(lblClientSeqNo);
							lblClientSeqNo.setOpaque(false);
							lblClientSeqNo.setBorder(BorderFactory.createEmptyBorder());
							lblClientSeqNo.setForeground(UIManager.getColor("Label.Foreground"));
						}
						{
							txtCompany = new _JXTextField("Company Name");
							pnlNorthCenter.add(txtCompany);
						}
						{
							txtProject = new _JXTextField("Project Name");
							pnlNorthCenter.add(txtProject);
							txtProject.setEditable(false);
						}
						{
							txtPBL = new _JXTextField("Description");
							pnlNorthCenter.add(txtPBL);
						}
						{
							txtModelName = new _JXTextField("Model Name");
							pnlNorthCenter.add(txtModelName);
						}
						{
							txtSalesAgent = new _JXTextField("Sales Agent Name");
							pnlNorthCenter.add(txtSalesAgent);
						}
						{
							txtSalesDivisionName = new _JXTextField("Sales Division Name");
							pnlNorthCenter.add(txtSalesDivisionName);
						}
						{
							txtSalesDivisionGroupName = new _JXTextField("Sales Group Name");
							pnlNorthCenter.add(txtSalesDivisionGroupName);
						}
					}
				}
				{//XXX
					JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
					pnlHoldingDetailsNorth.add(pnlEast, BorderLayout.EAST);
					pnlEast.setPreferredSize(new Dimension(200, 0));
					{
						JLabel lblRemarks = new JLabel("Remarks");
						pnlEast.add(lblRemarks, BorderLayout.NORTH);
					}
					{
						JScrollPane scrollNotes = new JScrollPane();
						pnlEast.add(scrollNotes, BorderLayout.CENTER);
						{
							txtRemarks = new JXTextArea("Remarks");
							scrollNotes.setViewportView(txtRemarks);
							txtRemarks.setLineWrap(true);
							txtRemarks.setWrapStyleWord(true);
							txtRemarks.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						}
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(3, 3));
			this.add(pnlCenter, BorderLayout.SOUTH);
			pnlCenter.setPreferredSize(new java.awt.Dimension(590, 86));
			pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Floating Payment"));
			{
				JPanel pnlCenterNorth = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlCenterNorth, BorderLayout.NORTH);
				pnlCenterNorth.setPreferredSize(new Dimension(0, 53));
				{
					JPanel pnlCenterWest = new JPanel(new BorderLayout(3, 3));
					pnlCenterNorth.add(pnlCenterWest, BorderLayout.WEST);
					pnlCenterWest.setPreferredSize(new Dimension(290, 0));
					
					{
						JPanel pnlLabels = new JPanel(new GridLayout(2, 2, 3, 3));
						pnlCenterWest.add(pnlLabels, BorderLayout.WEST);
						pnlLabels.setPreferredSize(new Dimension(100, 0));
						{
							lblParticular = new JLabel("Payment 1");
							pnlLabels.add(lblParticular);
						}
						{
							lblCashAmount = new JLabel("Payment 2");
							pnlLabels.add(lblCashAmount);
						}
					}
					{
						JPanel pnlLookups = new JPanel(new GridLayout(2, 2, 3, 3));
						pnlCenterWest.add(pnlLookups, BorderLayout.CENTER);
						
						{
							lookupPayment1 = new _JLookup(null, "Particular", 0);
							pnlLookups.add(lookupPayment1);
							lookupPayment1.setPrompt("AR No.");
							lookupPayment1.setEnabled(false);
							lookupPayment1.setEditable(false);
						}
						{
							txtCashAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlLookups.add(txtCashAmount);
							txtCashAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtCashAmount.setValue(new BigDecimal(0.00));
						}
						{
							lookupPayment2 = new _JLookup(null, "Payment 2", 0);
							pnlLookups.add(lookupPayment2);
							lookupPayment2.setPrompt("AR No.");
							lookupPayment2.setReturnColumn(0);
							lookupPayment2.setValue("");
							lookupPayment2.setEditable(true);
							lookupPayment2.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if(data != null){
										receipt_no2 = (String) data[0];
										String amount = data[3].toString();
										txtCashAmount2.setText(amount);
									}
								}
							});
						}
						
						{
							txtCashAmount2 = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlLookups.add(txtCashAmount2);
							txtCashAmount2.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtCashAmount2.setValue(new BigDecimal(0.00));
						}
					}
					{
						JPanel pnlLookups = new JPanel(new GridLayout(2, 2, 3, 3));
						pnlCenterWest.add(pnlLookups, BorderLayout.EAST);
						
					}
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbTransaction, lookupCompany, txtCompany, lookupProject, txtProject, lookupPBL, txtPBL,
				txtModel, txtModelName, lookupSalesAgent, txtSalesAgent, lookupSalesDivision, txtSalesDivisionName, txtSellingPrice));
		
	}

	public static Object[] getTransactions() {//XXX
		ArrayList<String> trans = new ArrayList<String>();
		trans.add("Hold");
		return trans.toArray();
	}
	
		
	//Validation
	public Boolean toSave() {

		//Check if company is blank
		if(lblCompany.getText().contains("*") && lookupCompany.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select company", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if project is blank
		if(lblProject.getText().contains("*") && lookupProject.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if unit is blank
		if(lblPBL.getText().contains("*") && lookupPBL.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select unit", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		
		Double tot_res_fee = Double.parseDouble(txtCashAmount.getText().replace(",",""))+Double.parseDouble(txtCashAmount2.getText().replace(",",""));
		
		if (tot_res_fee<sql_getRequiredResFee(txtModel.getText()))
		{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Total reservation amount less is than the " + "\n" +
					+ sql_getRequiredResFee(txtModel.getText()) + " required reservation fee.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		

		return true;
	}
	
	//SQL
	/**
	 * @return the entity_id
	 */
	public String getEntityID() {
		return entity_id;
	}

	public static String getFloatingPmt(){
		return 
		"SELECT \r\n" + 
		"a.or_no as \"Receipt No.\",\r\n" + 
		"get_client_name(a.entity_id) as \"Client Name\", \r\n" + 
		"a.trans_date as \"Pmt. Date\",\r\n" + 
		"a.amount as \"Amount\" \r\n" + 
		"	\r\n" + 
		"FROM rf_payments a\r\n" + 
		"\r\n" + 
		"WHERE a.co_id = '02'\r\n" + 
		"AND a.status_id != 'I'\r\n" + 
		"AND a.pay_part_id = '203' --FLOATING PAYMENTS\r\n" + 
		"AND (a.or_no, (case when or_doc_id is not null then or_doc_id else pr_doc_id end)) \r\n" + 
		"	not in (select receipt_id, (case when or_doc_id is not null then or_doc_id else pr_doc_id end) from rf_payments where receipt_id is not null)\r\n" + 
		"\r\n" + 
		"ORDER BY  get_client_name(a.entity_id)"  ;

	}
	
	public static String getFloatingPmt2(){
		return 
		"select * from (" +
		"SELECT \r\n" + 
		"a.or_no as \"Receipt No.\",\r\n" + 
		"get_client_name(a.entity_id) as \"Client Name\", \r\n" + 
		"a.trans_date as \"Pmt. Date\",\r\n" + 
		"a.amount as \"Amount\" \r\n," +
		"a.entity_id \r\n" + 
		"	\r\n" + 
		"FROM rf_payments a\r\n" + 
		"\r\n" + 
		"WHERE a.co_id IN ('02', '01')\r\n" + 
		"AND a.status_id != 'I'\r\n" + 
		"AND a.entity_id = '"+entity_id+"' \r\n" + 
		//"AND a.or_no != '"+receipt_no+"' \r\n" +
		"AND a.pay_rec_id != '"+pay_rec_id+"' \r\n" + 
		"AND a.pay_part_id = '203' --FLOATING PAYMENTS\r\n" + 
		"AND (a.or_no, (case when or_doc_id is not null then or_doc_id else pr_doc_id end)) \r\n" + 
		"	not in (select receipt_id, (case when or_doc_id is not null then or_doc_id else pr_doc_id end) from rf_payments where receipt_id is not null)\r\n" + 
		"\r\n" + 
		
		"UNION ALL \r\n" +
		
		"SELECT \r\n" + 
		"	a.receipt_no ,				\r\n" + 
		"	get_client_name(a.entity_id) , 		\r\n" + 
		"	a.trans_date,\r\n" + 
		"	a.total_amt_paid\r\n," +
		"	a.entity_id \r\n" + 
		"	FROM rf_tra_header a \r\n" + 
		"	left join rf_tra_detail b on a.client_seqno = b.client_seqno\r\n" + 
		"	left join mf_sellingagent_info c on c.agent_id = a.selling_agent \r\n" + 
		"	left join mf_department d on a.dept_code = d.dept_code \r\n" + 
		"	left join mf_division e on d.division_code = e.division_code  \r\n" + 
		"	WHERE a.co_id IN ('02', '01')  \r\n" + 
		"	AND a.status_id != 'I' \r\n" + 
		"   AND a.entity_id = '"+entity_id+"' \r\n" + 
		//"	AND a.receipt_no != '"+receipt_no+"' \r\n" + 
		"	AND b.tra_detail_id != '"+pay_rec_id+"' \r\n" + 
		"	AND (a.receipt_no, '03')   \r\n" + 
		"		not in (select receipt_id, (case when or_doc_id is not null then or_doc_id else pr_doc_id end) from rf_payments where receipt_id is not null)  \r\n" + 
		") a\r\n" + 
		"order by get_client_name(a.entity_id) "  ;

	}
	/**
	 * @return the entity_id
	 */
	/**
	 * @param entity_id the entity_id to set
	 */
	public void setEntityID(String entity_id) {
		this.entity_id = entity_id;
	}

	static void setPayment(String rcpt_no){

		clearFields();
		
		Object[] pmt_hdr = getFloatingdetails(rcpt_no);	
		receipt_no = (String) pmt_hdr[0];
		String amount = pmt_hdr[3].toString();
		lookupPayment1.setText(receipt_no);
		txtCashAmount.setText(amount);		
		lookupSalesAgent.setText((String) pmt_hdr[4]);
		txtSalesAgent.setText((String) pmt_hdr[5]);		
		lookupSalesDivisionGroup.setText((String) pmt_hdr[6]);
		txtSalesDivisionGroupName.setText((String) pmt_hdr[7]);		
		lookupSalesDivision.setText((String) pmt_hdr[8]);
		txtSalesDivisionName.setText((String) pmt_hdr[9]);
		pay_rec_id = pmt_hdr[10].toString();

	}

	public static Object [] getFloatingdetails(String rcpt_no) {

		String strSQL = 
			"SELECT * FROM ( " +
			"SELECT \r\n" + 
			"a.or_no as \"Receipt No.\",\r\n" + 
			"get_client_name(a.entity_id) as \"Client Name\", \r\n" + 
			"a.trans_date as \"Pmt. Date\",\r\n" + 
			"a.amount as \"Amount\", \r\n" +
			"'' as agent_id,\r\n" + 
			"	'' as agent_name, \r\n" + 
			"	'' as dept_code, \r\n" + 
			"	'' as dept_name, \r\n" +
			"	'' as div_code,\r\n" + 
			"	'' as div_name," +
			"a.pay_rec_id  \r\n" +
			"	\r\n" + 
			"FROM rf_payments a\r\n" + 
			"\r\n" + 
			"WHERE a.co_id IN ('02', '01')\r\n" + 
			"AND a.status_id != 'I'\r\n" + 
			"AND a.pay_part_id = '203' --FLOATING PAYMENTS\r\n" +
			"AND a.or_no = '"+rcpt_no+"' " + 
			"AND (a.or_no, (case when or_doc_id is not null then or_doc_id else pr_doc_id end)) \r\n" + 
			"	not in (select receipt_id, (case when or_doc_id is not null then or_doc_id else pr_doc_id end) from rf_payments where receipt_id is not null)\r\n" + 
			"\r\n" + 
			"union all \r\n" + 
			"\r\n" + 
			"SELECT \r\n" + 
			"a.receipt_no ,		\r\n" + 			
			"get_client_name(a.entity_id) , \r\n" + 			
			"a.trans_date,\r\n" + 
			"a.total_amt_paid, \r\n" +
			"a.selling_agent,\r\n" + 
			"get_client_name(c.agent_id),\r\n" + 
			"a.dept_code,\r\n" + 
			"get_department_name_new(a.dept_code)	\r\n," +
			"d.division_code,\r\n" + 
			"get_department_name(e.division_code)," +
			"b.tra_detail_id \r\n" + 
			"FROM rf_tra_header a\r\n" + 
			"left join rf_tra_detail b on a.client_seqno = b.client_seqno\r\n" +
			"left join mf_sellingagent_info c on c.agent_id = a.selling_agent \r\n" +
			"left join mf_department d on a.dept_code = d.dept_code\r\n" + 
			"left join mf_division e on d.division_code = e.division_code \r\n" + 
			"WHERE a.co_id IN ('02', '01') \r\n" + 
			"AND a.status_id != 'I' \r\n" + 
			"AND a.receipt_no = '"+rcpt_no+"' " + 
			"AND (a.receipt_no, '03')  \r\n" + 
			"	not in (select receipt_id, (case when or_doc_id is not null then or_doc_id else pr_doc_id end) from rf_payments where receipt_id is not null)  \r\n" + 
			
			"" +
			") a limit 1";

		System.out.printf("SQL #1 getDRFdetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	public static Double sql_getRequiredResFee(String model_id) {

		double a = 0.00;

		String SQL = 
				"select coalesce(res_fee1,0) + coalesce(res_fee2,0) from mf_product_model where model_id = '"+model_id+"' " ;

		System.out.printf("SQL #1: sql_getRequiredResFee", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if(db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {a = 0.00;}
			else {a = Double.parseDouble(db.getResult()[0][0].toString()); }

		}else{
			a = 0.00;
		}

		return a;
	}
	
	
	//Action Performed
	public void newHolding(Boolean isEdit) {
		setHoldingEnabled(true);
		setHoldingEditable(true);
		lblCompany.setText("*Company");
		lblProject.setText("*Project");
		lblPBL.setText("*Ph/Bl/Lt");
		lookupPayment2.setLookupSQL(getFloatingPmt2());

		if(!isEdit){//XXX
			/*lookupCompany.setValue("02");
			txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
			
			lookupProject.setValue("015");
			txtProject.setText("TERRAVERDE RESIDENCES");
			txtRemarks.setText("");*/

			System.out.println("Not Edit");
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
		}else{
			System.out.println("May Lookup Na");
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
		}
	}

	public Boolean saveHolding(String entity_id) {//XXX SAVE
		
		if(toSave()){

			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				
				pgUpdate db = new pgUpdate();
				
				String sqlDetail = 
					"update rf_payments set \n" +
					"proj_id = '"+lookupProject.getText()+"', " +
					"pbl_id = getinteger('"+lookupPBL.getText()+"'), \n" +
					"unit_id = '"+lookupPBL.getText()+"' " ;					
					if (lookupPayment2.getText().trim().equals("")||lookupPayment2.getText().trim().equals("AR No."))
					{sqlDetail = sqlDetail + "where or_no = '"+lookupPayment1.getText().trim()+"' \n" ;}
					else {sqlDetail = sqlDetail + "where or_no in ('"+lookupPayment1.getText().trim()+"','"+lookupPayment2.getText().trim()+"') \n" ;}					
					sqlDetail = sqlDetail +
					"and or_doc_id is null \n" +
					"and pr_doc_id = '03' \n" +
					"and or_no != '' \n" +
					"and status_id != 'I' \n" +
					"" ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);	
								
				String sqlDetail2 = 
					"insert into rf_special_holding_agent values( \n" +
					"getinteger('"+lookupPBL.getText()+"'), \n" +
					"'"+entity_id+"', " +
					"'"+lookupPayment1.getText().trim()+"', \n" +
					"'A', \n" +
					"'"+lookupSalesAgent.getText()+"', \n" +
					"'"+lookupSalesDivisionGroup.getText()+"'," +
					"'"+txtRemarks.getText().replace("'","''")+"', " +
					"now() \n" +
					") " ;
				System.out.printf("SQL #2: %s", sqlDetail2);
				db.executeUpdate(sqlDetail2, false);	
				
				
				if (lookupPayment2.getText().trim().equals("")||lookupPayment2.getText().trim().equals("AR No.")) {} else				
				{String sqlDetail5 = 
						"insert into rf_special_holding_agent values( \n" +
						"getinteger('"+lookupPBL.getText()+"'), \n" +
						"'"+entity_id+"', " +
						"'"+lookupPayment2.getText().trim()+"', \n" +
						"'A', \n" +
						"'"+lookupSalesAgent.getText()+"', \n" +
						"'"+lookupSalesDivisionGroup.getText()+"'," +
						"'"+txtRemarks.getText().replace("'","''")+"', " +
						"now() \n" +
						") " ;
					System.out.printf("SQL #5: %s", sqlDetail5);
					db.executeUpdate(sqlDetail5, false);
				}
								
				String sqlDetail3 = 
					"update rf_tra_header set \n" +
					"pbl_id = getinteger('"+lookupPBL.getText()+"'), \n" +
					"unit_id = '"+lookupPBL.getText()+"', " +
					"model_id = '"+txtModel.getText()+"', " +
					"sellingprice = "+txtSellingPrice.getText().replace(",","")+"," +
					"tran_type = 0," +
					"selling_agent = '"+lookupSalesAgent.getText()+"', \n" +
					"dept_code = '"+lookupSalesDivisionGroup.getText()+"'," +
					"remarks = '"+txtRemarks.getText().replace("'","''")+"' \n" ;
					if (lookupPayment2.getText().trim().equals("")||lookupPayment2.getText().trim().equals("AR No."))
					{sqlDetail3 = sqlDetail3 + "where receipt_no = '"+lookupPayment1.getText().trim()+"' \n" ;}
					else {sqlDetail3 = sqlDetail3 + "where receipt_no in ('"+lookupPayment1.getText().trim()+"','"+lookupPayment2.getText().trim()+"') \n" ;}					
					sqlDetail3 = sqlDetail3 +
					"AND proj_id = '"+lookupProject.getText()+"' \n" +
					"and entity_id = '"+entity_id+"' \n"+
					"and receipt_no != '' \n" +
					"and status_id != 'I' \n" +
					"" ;

				System.out.printf("SQL #1: %s", sqlDetail3);
				db.executeUpdate(sqlDetail3, false);	
				
				String sqlDetail4 = 
					"UPDATE mf_unit_info SET status_id = 'O' " +
					"WHERE unit_id = '"+lookupPBL.getText()+"' " +
					"AND proj_id = '"+lookupProject.getText()+"' " +
					"AND status_id != 'I' \n" + 
					"" ;

				System.out.printf("SQL #1: %s", sqlDetail4);
				db.executeUpdate(sqlDetail4, false);		

				db.commit();
				
				return true;}

			else{return false;}
		}

		else{return false;}
	}

	public void editHolding() {
		String transaction = (String) cmbTransaction.getSelectedItem();
		if(transaction.equals("Hold")){
			newHolding(true);
			txtCashAmount.setEditable(true);
		}
		cmbTransaction.setEnabled(false);
	}

	public Boolean view(String entity_id) {
		this.entity_id = entity_id;

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Transactions", _HoldingAndReservation.sqlView(entity_id), false);
		dlg.setFilterClientName(true);
		dlg.setSize(new Dimension(800, 420));
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();
		if(data != null){
			clearFields();
			setHoldingEnabled(false);

			String transaction = (String) data[4];
			if(transaction.equals("Hold")){
				newHolding(false);
			}

			Boolean credited = (Boolean) data[0];
			Integer transaction_id = (Integer) data[3];
			String client_seqno = (String) data[5];
			String status = (String) data[6];
			String company_id = (String) data[8];
			String company_name = (String) data[9];
			String project_id = (String) data[10];
			String project_name = (String) data[11];
			String unit_id = (String) data[1];
			String unit_description = (String) data[23];
			String model_id = (String) data[12];
			String model_name = (String) data[13];
			BigDecimal sellig_price = (BigDecimal) data[14];
			String agent_id = (String) data[15];
			String agent_name = (String) data[16];
			String sales_division_id = (String) data[17];
			String sales_division_name = (String) data[18];
			String sales_group_id = (String) data[19];
			String sales_group_name = (String) data[20];
			BigDecimal amount_paid = (BigDecimal) data[24];
			String rec_id = (String) data[25];

			cmbTransaction.setSelectedIndex(transaction_id);
			lblClientSeqNo.setText(String.format("[ %s ]", (client_seqno == null ? rec_id:client_seqno)));
			lookupCompany.setValue(company_id);
			txtCompany.setText(company_name);
			lookupProject.setValue(project_id);
			txtProject.setText(project_name);
			lookupPBL.setValue(unit_id);
			txtPBL.setText(unit_description);
			txtModel.setText(model_id);
			txtModelName.setText(model_name);
			txtSellingPrice.setValue(sellig_price);
			lookupSalesAgent.setValue(agent_id);
			txtSalesAgent.setText(agent_name);
			lookupSalesDivision.setValue(sales_division_id);
			txtSalesDivisionName.setText(sales_division_name);
			lookupSalesDivisionGroup.setValue(sales_group_id);
			txtSalesDivisionGroupName.setText(sales_group_name);
			txtCashAmount.setValue(amount_paid);

			setFieldsEditable();
			cmbTransaction.setEnabled(false);
			har.btnState(true, (credited ? false:status.equals("O")), true, false, true);

			return true;
		}else{
			return false;
		}
	}

	
	//Clear, Refresh, Enable, Disable
	public void setFieldsEditable() {
		cmbTransaction.setEditable(false);
		lookupCompany.setEditable(true);
		lookupCompany.setEditable(false);
		lookupProject.setEditable(true);
		lookupProject.setEditable(true);
		lookupProject.setEditable(false);
		lookupPBL.setEditable(true);
		lookupPBL.setEditable(false);
		lookupSalesAgent.setEditable(true);
		lookupSalesAgent.setEditable(false);
		lookupSalesDivision.setEditable(true);
		lookupSalesDivision.setEditable(false);
		lookupSalesDivisionGroup.setEditable(true);
		lookupSalesDivisionGroup.setEditable(false);
		txtCashAmount.setEditable(false);
	}	
	
	public void setFieldsEnabled(JPanel panel, boolean enable) {
		//panelLooper(panel, enable);
	}
	
	private void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			/*if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				panelLooper((JScrollPane) comp, enable);
			} else if (comp instanceof JSplitPane) {
				panelLooper((JSplitPane) comp, enable);
			} else {
				if (panel instanceof JScrollPane) {
					((JScrollPane) panel).getViewport().getView().setEnabled(enable);
				} else {
					//comp.setEnabled(enable);
				}
			}*/
		}
	}

	
	public static void clearFields() {
		lblClientSeqNo.setText("");
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupPBL.setValue(null);
		txtPBL.setText("");
		txtModel.setText("");
		txtModelName.setText("");
		lookupSalesAgent.setValue(null);
		txtSalesAgent.setText(null);
		lookupSalesDivision.setText("");
		lookupSalesDivisionGroup.setText("");
		txtSalesDivisionName.setText("");
		txtSalesDivisionGroupName.setText("");
		txtSellingPrice.setValue(null);
		txtRemarks.setText("");

	}

	public void setHoldingEditable(boolean editable) {
		panelLooper2(this, editable);
	}

	private void panelLooper2(Container panel, boolean editable) {
		for (Component comp : panel.getComponents()) {
			if(comp instanceof _JLookup) {//XXX
				((_JLookup) comp).setEditable(!editable);
				((_JLookup) comp).setEditable(editable);
			} else if(comp instanceof _JDateChooser) {
				((_JDateChooser) comp).setEditable(editable);
				((_JDateChooser) comp).getCalendarButton().setEnabled(editable);
			} else {
				if (comp instanceof JPanel) {
					panelLooper2((JPanel) comp, editable);
				}
				if (comp instanceof JSplitPane) {
					panelLooper2((JSplitPane) comp, editable);
				}
			}
		}
	}
	
	public static void clearFields2() {
		lookupPayment1.setText("AR No.");
		lookupPayment2.setText("AR No.");
		//txtParticularName.setText("Client Name");
		txtCashAmount.setValue(new BigDecimal(0.00));
		txtCashAmount2.setValue(new BigDecimal(0.00));
		receipt_no = "";
		receipt_no2 = "";
		entity_id = "";
	}

	public void setHoldingEnabled(boolean enable) {
		panelLooper(this, enable);
	}
	
}
