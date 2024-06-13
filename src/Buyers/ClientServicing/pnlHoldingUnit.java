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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncDate;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup.ResetEvent;
import Lookup.ResetListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JInternalFrame;
import components._JXTextField;

public class pnlHoldingUnit extends JPanel implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7179374196951209049L;

	private JSplitPane splitNorth;

	private JLabel lblTransaction;
	private JComboBox cmbTransaction;
	private _JXTextField lblClientSeqNo;

	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;

	private JLabel lblProject;
	private _JLookup lookupProject;
	private _JXTextField txtProject;

	private JLabel lblPBL;
	private _JLookup lookupPBL;
	private _JXTextField txtPBL;

	private JLabel lblModel;
	private _JXTextField txtModel;
	private _JXTextField txtModelName;

	private JLabel lblSalesAgent;
	private _JLookup lookupSalesAgent;
	private _JXTextField txtSalesAgent;

	private JLabel lblSalesDivision;
	private _JLookup lookupSalesDivision;
	private _JXTextField txtSalesDivisionName;

	private JLabel lblSalesDivisionGroup;
	private _JLookup lookupSalesDivisionGroup;
	private _JXTextField txtSalesDivisionGroupName;

	private JLabel lblValidFrom;
	private _JDateChooser dateValidFrom;

	private JLabel lblValidTo;
	private _JDateChooser dateValidTo;

	private JLabel lblExtendedUntil;
	private _JDateChooser dateExtendedUntil;

	private JLabel lblSellingPrice;
	private _JXFormattedTextField txtSellingPrice;

	private JXTextArea txtRemarks;

	private JPanel pnlCenter;

	private JLabel lblParticular;
	private _JLookup lookupParticular;
	private _JXTextField txtParticularName;
	private _JXTextField txtParticularAlias;

	private JLabel lblCashAmount;
	private _JXFormattedTextField txtCashAmount;

	private String receipt_id = "03";
	//private String receipt_type = "ACKNOWLEDGEMENT RECEIPT";
	//private String receipt_alias = "AR";
	
	private String previous_unit = null;
	private String new_unit = null;
	
	private String entity_id = null;

	private HoldingAndReservation har;

	public pnlHoldingUnit(HoldingAndReservation har) {
		this.har = har;

		initGUI();
		setHoldingEnabled(false);
	}

	public pnlHoldingUnit(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlHoldingUnit(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlHoldingUnit(LayoutManager layout, boolean isDoubleBuffered) {
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
				/*splitNorth = new JSplitPane();
				pnlHoldingDetails.add(splitNorth, BorderLayout.CENTER);
				splitNorth.setOneTouchExpandable(true);
				splitNorth.setResizeWeight(.7d);*/
				
				JPanel pnlHoldingDetailsNorth = new JPanel(new BorderLayout(3, 3));
				pnlHoldingDetails.add(pnlHoldingDetailsNorth, BorderLayout.CENTER);
				{
					JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
					//splitNorth.add(pnlNorth, JSplitPane.LEFT);
					pnlHoldingDetailsNorth.add(pnlNorth, BorderLayout.CENTER);
					{
						JPanel pnlNorthWest = new JPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
						//pnlNorthWest.setPreferredSize(new Dimension(245, 0));//XXX
						{
							JPanel pnlLabels = new JPanel(new GridLayout(10, 1, 3, 3));
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
								lblValidFrom = new JLabel("Valid From");
								pnlLabels.add(lblValidFrom);
							}
							{
								lblValidTo = new JLabel("Valid To");
								pnlLabels.add(lblValidTo);
							}
						}
						{
							JPanel pnlLookups = new JPanel(new GridLayout(10, 1, 3, 3));
							pnlNorthWest.add(pnlLookups, BorderLayout.CENTER);
							{
								cmbTransaction = new JComboBox(new DefaultComboBoxModel(getTransactions()));
								pnlLookups.add(cmbTransaction);
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
											if(selectedTransaction.equals("Unhold")){
												if(UserInfo.ADMIN || UserInfo.EmployeeCode.equals("900395") || UserInfo.EmployeeCode.contentEquals("900298") || UserInfo.EmployeeCode.equals("987120")) {
													newUnhold();
												}else {
													JOptionPane.showMessageDialog(pnlHoldingUnit.this.getTopLevelAncestor(), "Sorry you do not have access to unhold", "Unhold", JOptionPane.WARNING_MESSAGE);
												}
											}
											if(selectedTransaction.equals("Float")){
												newFloating(false);
											}
											if(selectedTransaction.equals("Extension")){
												newExtension(false);
											}

											if(selectedTransaction.equals("Management Hold")){//XXX Management Hold
												if(isAllowedToManagementHold()){ 
													if(entity_id.equals("0000000002") || entity_id.equals("0000000003") || entity_id.equals("0000000005") || entity_id.equals("0000000006") || entity_id.equals("0000000021")){
														newManagementHolding(false);
													}else{
														JOptionPane.showMessageDialog(pnlHoldingUnit.this.getTopLevelAncestor(), "Selected client is not allowed to make this transaction.\n"
																+ "Only this person are allowed:\n"
																+ "     • Mr. CHING-HAI LIAO\n"
																+ "     • Mr. JOHNNY CORPUZ\n"
																+ "     • Ms. MARIA RHEA LIM", "Transaction", JOptionPane.INFORMATION_MESSAGE);
													}
												}else{
													combo.setSelectedIndex(0);
													newHolding(false);
													return;
												};
											}
											combo.setToolTipText(selectedTransaction);
											
											if(selectedTransaction.equals("Commitment Fee")){
												lookupParticular.setValue("229");
												txtParticularName.setText("COMMITMENT FEE");
												newCommitmentFee(false);
											}
											
											lblTransaction.setEnabled(true);
											cmbTransaction.setEnabled(true);
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
								//lookupPBL = new _JLookup(null, "Ph/Bl/Lt", "Please select project.");
								lookupPBL = new _JLookup(null, "Ph/Bl/Lt");
								pnlLookups.add(lookupPBL);
								lookupPBL.setSizeDialog(new Dimension(800, 420));
								//lookupPBL.setReturnColumn(0);
								lookupPBL.setPrompt("Unit ID");
								lookupPBL.setFilterName(true);
								lookupPBL.addFocusListener(new FocusListener() {
									
									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void focusGained(FocusEvent e) {
										//lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
										if(lookupPBL.getValue() == null){
											String selected_transaction = (String) cmbTransaction.getSelectedItem();
											
											//clearFields();
											setHoldingEnabled(false);
											setHoldingEditable(false);
											
											System.out.printf("Display selected: %s%n", selected_transaction);
											
											if(selected_transaction.equals("Hold")){
												newHolding(false);
											}
											if(selected_transaction.equals("Unhold")){
												newUnhold();
											}
											if(selected_transaction.equals("Float")){
												newFloating(false);
											}
											if(selected_transaction.equals("Extension")){
												newExtension(false);
											}

											if(selected_transaction.equals("Management Hold")){//XXX Management Hold
												if(isAllowedToManagementHold()){ 
													if(entity_id.equals("0000000002") || entity_id.equals("0000000003") || entity_id.equals("0000000005") || entity_id.equals("0000000006") || entity_id.equals("0000000021")){
														newManagementHolding(false);
													}else{
														JOptionPane.showMessageDialog(pnlHoldingUnit.this.getTopLevelAncestor(), "Selected client is not allowed to make this transaction.\n"
																+ "Only this person are allowed:\n"
																+ "     • Mr. CHING-HAI LIAO\n"
																+ "     • Mr. JOHNNY CORPUZ\n"
																+ "     • Ms. MARIA RHEA LIM", "Transaction", JOptionPane.INFORMATION_MESSAGE);
													}
												}else{
													cmbTransaction.setSelectedIndex(0);
													newHolding(false);
													return;
												};
											}
											
											if(selected_transaction.equals("Commitment Fee")){
												lookupParticular.setValue("229");
												txtParticularName.setText("COMMITMENT FEE");
												newCommitmentFee(false);
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
											
											//if(_HoldingAndReservation.isOpen(lookupProject.getValue(), unit_id)){
												
												/*if(cmbTransaction.getSelectedIndex() != 4){
													_HoldingAndReservation.updateStatus(lookupProject.getValue(), previous_unit, "A");
												}*/
												
												lookupPBL.setValue(unit_id);
												String unit_description = (String) data[1];
												String model_id = (String) data[2];
												String model_description = (String) data[3];
												BigDecimal gsp = (BigDecimal) data[6];

												txtPBL.setText(unit_description);
												txtModel.setText(model_id);
												txtModelName.setText(model_description);
												txtSellingPrice.setValue(gsp);
												
												previous_unit = unit_id;
												new_unit = unit_id;
												
												/*if(cmbTransaction.getSelectedIndex() != 4){
													_HoldingAndReservation.updateStatus(lookupProject.getValue(), new_unit, "O");
												}*/
												
												try {
													String company_id = (String) data[11];
													String company_name = (String) data[12];
													String project_id = (String) data[4];
													String project_name = (String) data[5];
													String division_id = (String) data[7];
													String division_name = (String) data[8];
													Date validFrom = (Date) data[9];
													Date validTo = (Date) data[10];
													Date extensionDate = (Date) data[15];
													String client_seqno = (String) data[14];
													String remarks = (String) data[16];
													String tran_type = (String) data[17];
													
													lblClientSeqNo.setText(String.format("[ %s ]", client_seqno));
													lookupCompany.setValue(company_id);
													txtCompany.setText(company_name);
													lookupProject.setValue(project_id);
													txtProject.setText(project_name);
													lookupSalesDivision.setValue(division_id);
													txtSalesDivisionName.setText(division_name);
													dateValidFrom.setDate(validFrom);
													dateValidTo.setDate(validTo);
													txtRemarks.setText(remarks);
													
													if(tran_type.equals("4")){
														lookupParticular.setValue(null);
														txtParticularAlias.setText("");
														txtParticularName.setText("");
														txtCashAmount.setValue(new BigDecimal("0.00"));
													}
													
													
												} catch (ClassCastException e) {
												} catch (ArrayIndexOutOfBoundsException e2) { }
											/*}else{
												lookupPBL.setValue(null);
												JOptionPane.showMessageDialog(null, "Unit has been hold already", "Unit", JOptionPane.WARNING_MESSAGE);
											}*/
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
											//String agent_id = (String) data[0];
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
								//lookupSalesDivisionGroup.setLookupSQL(_HoldingAndReservation.DIVISION());
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
								dateValidFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlLookups.add(dateValidFrom);
							}
							{
								dateValidTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlLookups.add(dateValidTo);
							}
						}
					}
					{
						JPanel pnlNorthCenter = new JPanel(new GridLayout(10, 1, 3, 3));
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
						{
							JPanel pnlSellingPrice = new JPanel(new BorderLayout(3, 3));
							pnlNorthCenter.add(pnlSellingPrice);
							{
								JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
								pnlSellingPrice.add(pnlWest, BorderLayout.WEST);
								pnlWest.setPreferredSize(new Dimension(250, 0));
								{
									lblSellingPrice = new JLabel("Selling Price", JLabel.TRAILING);
									pnlWest.add(lblSellingPrice, BorderLayout.WEST);
									lblSellingPrice.setPreferredSize(new Dimension(110, 0));
								}
								{
									txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlWest.add(txtSellingPrice, BorderLayout.CENTER);
									txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtSellingPrice.setEditable(false);
								}
							}
						}
						{
							JPanel pnlExtendedUntil = new JPanel(new BorderLayout());
							pnlNorthCenter.add(pnlExtendedUntil);
							{
								JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
								pnlExtendedUntil.add(pnlWest, BorderLayout.WEST);
								pnlWest.setPreferredSize(new Dimension(250, 0));
								{
									lblExtendedUntil = new JLabel(" Extended Until", JLabel.CENTER);
									pnlWest.add(lblExtendedUntil, BorderLayout.WEST);
									lblExtendedUntil.setPreferredSize(new Dimension(110, 0));
								}
								{
									dateExtendedUntil = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlWest.add(dateExtendedUntil, BorderLayout.CENTER);
								}
							}
						}
					}
				}
				{//XXX
					JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
					//splitNorth.add(pnlEast, JSplitPane.RIGHT);
					//pnlEast.setBorder(_LINE_BORDER);
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
			pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Payment"));
			{
				JPanel pnlCenterNorth = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlCenterNorth, BorderLayout.NORTH);
				pnlCenterNorth.setPreferredSize(new Dimension(0, 53));
				{
					JPanel pnlCenterWest = new JPanel(new BorderLayout(3, 3));
					pnlCenterNorth.add(pnlCenterWest, BorderLayout.WEST);
					pnlCenterWest.setPreferredSize(new Dimension(245, 0));
					{
						JPanel pnlLabels = new JPanel(new GridLayout(2, 2, 3, 3));
						pnlCenterWest.add(pnlLabels, BorderLayout.WEST);
						pnlLabels.setPreferredSize(new Dimension(100, 0));
						{
							lblParticular = new JLabel("Particular");
							pnlLabels.add(lblParticular);
						}
						{
							lblCashAmount = new JLabel("*Cash Amount");
							pnlLabels.add(lblCashAmount);
						}
					}
					{
						JPanel pnlLookups = new JPanel(new GridLayout(2, 2, 3, 3));
						pnlCenterWest.add(pnlLookups, BorderLayout.CENTER);
						{
							lookupParticular = new _JLookup(null, "Particular", 0);
							pnlLookups.add(lookupParticular);
							lookupParticular.setPrompt("Part ID");
							lookupParticular.setValue("168");
							//lookupParticular.setLookupSQL(_OrderOfPayment.getParticulars());
							lookupParticular.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if(data != null){
										String desciption = (String) data[1];
										String alias = (String) data[2];
										receipt_id = (String) data[5];
										//receipt_type = (String) data[6];
										//receipt_alias = (String) data[7];

										txtParticularName.setText(desciption);
										txtParticularAlias.setText(alias);
									}
								}
							});
						}
						{
							txtCashAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlLookups.add(txtCashAmount);
							txtCashAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtCashAmount.setValue(new BigDecimal(3000.00));
						}
					}
				}
				{
					JPanel pnlCenterCenter = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlCenterNorth.add(pnlCenterCenter, BorderLayout.CENTER);
					{
						JPanel pnlParticularName = new JPanel(new BorderLayout(3, 3));
						pnlCenterCenter.add(pnlParticularName);
						{
							txtParticularName = new _JXTextField("Particular Name");
							pnlParticularName.add(txtParticularName, BorderLayout.CENTER);
							txtParticularName.setText("ACCOUNTS PAYABLE-RESERVED");
						}
						{
							txtParticularAlias = new _JXTextField("Alias");
							pnlParticularName.add(txtParticularAlias, BorderLayout.EAST);
							txtParticularAlias.setHorizontalAlignment(JXTextField.CENTER);
							txtParticularAlias.setText("APR");
							//txtParticularAlias.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						pnlCenterCenter.add(Box.createHorizontalGlue());
					}
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbTransaction, lookupCompany, txtCompany, lookupProject, txtProject, lookupPBL, txtPBL,
				txtModel, txtModelName, lookupSalesAgent, txtSalesAgent, lookupSalesDivision, txtSalesDivisionName, dateValidFrom, dateValidTo, dateExtendedUntil, txtSellingPrice));

		//splitNorth.setDividerLocation(1.0);
	}

	public static Object[] getTransactions() {//XXX
		ArrayList<String> trans = new ArrayList<String>();
		trans.add("Hold");
		trans.add("Unhold");
		trans.add("Float");
		trans.add("Extension");
		

		ArrayList<String> authorizedEmployee = new ArrayList<String>();
		authorizedEmployee.add("000605");//CORPUZ, JOHNNY LIWANAG
		authorizedEmployee.add("000605");//FLORES, NELIA D
		authorizedEmployee.add("000645");//LIAO, CHING-HAI
		authorizedEmployee.add("900028");//LIM, MARIA RHEA FORTALEZA

		//if(authorizedEmployee.contains(UserInfo.EmployeeCode)){
		trans.add("Management Hold");
		trans.add("Commitment Fee");
		//}

		return trans.toArray();
	}

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
		dateValidFrom.setEditable(false);
		dateValidFrom.getCalendarButton().setEnabled(false);
		dateValidTo.setEditable(false);
		dateValidTo.getCalendarButton().setEnabled(false);
		dateExtendedUntil.setEditable(false);
		dateExtendedUntil.getCalendarButton().setEnabled(false);
		txtCashAmount.setEditable(false);
	}

	public void clearFields() {
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
		dateValidFrom.setDate(null);
		dateValidTo.setDate(null);
		dateExtendedUntil.setDate(null);
		txtSellingPrice.setValue(null);

		txtCashAmount.setValue(new BigDecimal(3000.00));
	}

	public void newHolding(Boolean isEdit) {
		setHoldingEnabled(true);
		setHoldingEditable(true);
		//splitNorth.setDividerLocation(1.0);

		lblCompany.setText("*Company");
		lblProject.setText("*Project");
		lblPBL.setText("*Ph/Bl/Lt");
		lblExtendedUntil.setText("Extended Until");
		lblSalesDivision.setText("*Sales Division");
		lblSalesDivisionGroup.setText("*Sales Group");
		lblCashAmount.setText("*Cash Amount");

		lookupParticular.setEditable(false);
		txtCashAmount.setEditable(true);
		dateValidFrom.setEditable(false);
		dateValidFrom.getCalendarButton().setEnabled(false);
		dateValidTo.setEditable(false);
		dateValidTo.getCalendarButton().setEnabled(false);
		dateExtendedUntil.setEditable(false);
		dateExtendedUntil.getCalendarButton().setEnabled(false);

		if(!isEdit){//XXX
			dateValidFrom.setDate(Calendar.getInstance().getTime());
			dateValidTo.setDate(FncDate.add(5));
			txtCashAmount.setValue(new BigDecimal(3000.00));
			
			lookupParticular.setValue("168");
			txtParticularName.setText("ACCOUNTS PAYABLE-RESERVED");
			txtParticularAlias.setText("APR");
			
			/*lookupCompany.setValue("02");
			txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
			
			lookupProject.setValue("015");
			txtProject.setText("TERRAVERDE RESIDENCES");*/
			
			txtRemarks.setText("");
			
			System.out.println("Not Edit");
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
		}else{
			System.out.println("May Lookup Na");
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
		}

		//lookupCompany.requestFocus();
	}
	
	public void newCommitmentFee(Boolean isEdit) {
		/*setHoldingEnabled(true);
		setHoldingEditable(true);*/
		//splitNorth.setDividerLocation(1.0);
		
		setHoldingEnabled(true);
		setHoldingEditable(true);

		lblCompany.setText("*Company");
		lblProject.setText("*Project");
		lblPBL.setText("Ph/Bl/Lt");
		lblExtendedUntil.setText("Extended Until");
		lblSalesDivision.setText("Sales Division");
		lblSalesDivisionGroup.setText("Sales Group");
		lblCashAmount.setText("*Cash Amount");

		lookupParticular.setEditable(false);
		txtCashAmount.setEditable(true);
		dateValidFrom.setEditable(false);
		dateValidFrom.getCalendarButton().setEnabled(false);
		dateValidTo.setEditable(false);
		dateValidTo.getCalendarButton().setEnabled(false);
		dateExtendedUntil.setEditable(false);
		dateExtendedUntil.getCalendarButton().setEnabled(false);
		lookupPBL.setEditable(false);

		if(!isEdit){//XXX
			dateValidFrom.setDate(Calendar.getInstance().getTime());
			dateValidTo.setDate(FncDate.add(5));
			//txtCashAmount.setValue(new BigDecimal(3000.00));
			txtCashAmount.setValue(new BigDecimal(0.00));
			
			lookupCompany.setValue("02");
			txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
			
			lookupProject.setValue("015");
			txtProject.setText("TERRAVERDE RESIDENCES");
			txtRemarks.setText("");
			
			System.out.println("Not Edit");
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
		}else{
			System.out.println("May Lookup Na");
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
			lookupPBL.requestFocus();
		}
		
		
		//lookupCompany.requestFocus();
	}

	public void newUnhold(/*Integer transaction*/) {
		setHoldingEnabled(true);
		setHoldingEditable(true);
		//splitNorth.setDividerLocation(1.0);

		lblCompany.setText("Company");
		lblProject.setText("Project");
		lblPBL.setText("*Ph/Bl/Lt");
		lblExtendedUntil.setText("Extended Until");
		lblSalesDivision.setText("Sales Division");
		lblSalesDivisionGroup.setText("Sales Group");
		lblCashAmount.setText("Cash Amount");

		lookupCompany.setEditable(false);
		lookupProject.setEditable(false);
		lookupSalesAgent.setEditable(false);
		lookupSalesDivision.setEditable(false);
		lookupSalesDivisionGroup.setEditable(false);
		dateValidFrom.setEditable(false);
		dateValidFrom.getCalendarButton().setEnabled(false);
		dateValidTo.setEditable(false);
		dateValidTo.getCalendarButton().setEnabled(false);
		dateExtendedUntil.setEditable(false);
		dateExtendedUntil.getCalendarButton().setEnabled(false);

		setFieldsEnabled(pnlCenter, false);

		lookupPBL.setLookupSQL(_HoldingAndReservation.getExtensionUnits(getEntityID()));
		/*if(transaction == 4){

		}else{
			lookupPBL.setLookupSQL(_HoldingAndReservation.getExtensionUnits(getEntityID()));
		}*/
		txtCashAmount.setValue(null);

		lookupPBL.requestFocus();
	}

	public void newFloating(Boolean isEdit) {
		setHoldingEnabled(true);
		setHoldingEditable(true);
		//splitNorth.setDividerLocation(1.0);

		lblCompany.setText("*Company");
		lblProject.setText("*Project");
		lblPBL.setText("Ph/Bl/Lt");
		lblExtendedUntil.setText("Extended Until");
		lblSalesDivision.setText("*Sales Division"); //2016-08-19 Set to Required Field by John Lester Fatallo
		lblSalesDivisionGroup.setText("*Sales Group");//2016-08-19 Set to Required Field by John Lester Fatallo
		lblCashAmount.setText("*Cash Amount");

		lookupPBL.setEditable(false);
		lookupParticular.setEditable(false);
		dateValidFrom.setEditable(false);
		dateValidFrom.getCalendarButton().setEnabled(false);
		dateValidTo.setEditable(false);
		dateValidTo.getCalendarButton().setEnabled(false);
		dateExtendedUntil.setEditable(false);
		dateExtendedUntil.getCalendarButton().setEnabled(false);

		if(!isEdit){
			dateValidFrom.setDate(Calendar.getInstance().getTime());
			dateValidTo.setDate(FncDate.add(5));
			txtCashAmount.setValue(null);
		}else{
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
		}

		lookupCompany.requestFocus();
	}

	public void newExtension(Boolean isEdit) {
		setHoldingEnabled(true);
		setHoldingEditable(true);
		//splitNorth.setDividerLocation(1.0);

		lblCompany.setText("Company");
		lblProject.setText("Project");
		lblPBL.setText("*Ph/Bl/Lt");
		lblExtendedUntil.setText("*Extended Until");
		lblSalesDivision.setText("Sales Division");
		lblSalesDivisionGroup.setText("Sales Group");
		lblCashAmount.setText("Cash Amount");

		lookupCompany.setEditable(false);
		lookupProject.setEditable(false);
		lookupSalesAgent.setEditable(false);
		lookupSalesDivision.setEditable(false);
		lookupSalesDivisionGroup.setEditable(false);

		dateValidFrom.setEditable(false);
		dateValidFrom.getCalendarButton().setEnabled(false);
		dateValidTo.setEditable(false);
		dateValidTo.getCalendarButton().setEnabled(false);
		dateExtendedUntil.setEditable(true);
		dateExtendedUntil.getCalendarButton().setEnabled(true);
		txtCashAmount.setEditable(true);

		lookupPBL.setLookupSQL(_HoldingAndReservation.getExtensionUnits(getEntityID()));
		txtCashAmount.setValue(new BigDecimal(2000.00));

		//lookupPBL.requestFocus();
	}

	public void newManagementHolding(Boolean isEdit) {
		setHoldingEnabled(true);
		setHoldingEditable(true);
		//System.out.printf("Split Width: %s%n", this.getWidth());
		//splitNorth.setDividerLocation(this.getWidth() - 180);

		lblCompany.setText("*Company");
		lblProject.setText("*Project");
		lblPBL.setText("*Ph/Bl/Lt");
		lblExtendedUntil.setText("Extended Until");
		lblSalesDivision.setText("Sales Division");
		lblSalesDivisionGroup.setText("Sales Group");
		lblCashAmount.setText("Cash Amount");

		dateValidFrom.setEditable(false);
		dateValidFrom.getCalendarButton().setEnabled(false);
		dateValidTo.setEditable(false);
		dateValidTo.getCalendarButton().setEnabled(false);
		dateExtendedUntil.setEditable(false);
		dateExtendedUntil.getCalendarButton().setEnabled(false);

		if(!isEdit){
			dateValidFrom.setDate(Calendar.getInstance().getTime());
			dateValidTo.setDate(FncDate.add(5));
			txtCashAmount.setValue(null);
		}else{
			lookupProject.setLookupSQL(_JInternalFrame.SQL_PROJECT(lookupCompany.getValue()));
			lookupPBL.setLookupSQL(_HoldingAndReservation.getUnits(lookupProject.getValue()));
		}

		panelLooper(pnlCenter, false);

		lookupCompany.requestFocus();
	}

	public void editHolding() {
		String transaction = (String) cmbTransaction.getSelectedItem();
		if(transaction.equals("Hold")){
			newHolding(true);
			txtCashAmount.setEditable(true);
		}
		if(transaction.equals("Float")){
			newFloating(true);
			txtCashAmount.setEditable(true);
		}
		if(transaction.equals("Extension")){//XXX Extension
			newExtension(true);
			txtCashAmount.setEditable(true);
		}
		if(transaction.equals("Management Hold")){
			newManagementHolding(true);
		}
		if(transaction.equals("Commitmment Fee")){
			newCommitmentFee(true);
		}
		cmbTransaction.setEnabled(false);
	}

	public void cancelHolding() {
		setHoldingEnabled(false);
		
		txtCashAmount.setValue(null);
		
		/*if(lookupProject.getValue() != null && lookupPBL.getValue() != null && cmbTransaction.getSelectedIndex() != 4){
			//if(_HoldingAndReservation.isOpen(lookupProject.getValue().trim(), lookupPBL.getValue().trim())){
				_HoldingAndReservation.updateStatus(lookupProject.getValue(), lookupPBL.getValue(), "A");
				//System.out.printf("Display value of selected index: %s%n", cmbTransaction.getSelectedIndex());
			//}else{
				//JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Unit has been hold already", "Unit", JOptionPane.WARNING_MESSAGE);
			//}
			System.out.println("Dumaan dito sa Reopen ng Unit dito sa Holding and update ng unit status");
		}*/
		
		cmbTransaction.setSelectedIndex(0);
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
			if(transaction.equals("Unhold")){
				newUnhold();
			}
			if(transaction.equals("Float")){
				newFloating(false);
			}
			if(transaction.equals("Extension")){
				newExtension(false);
			}
			if(transaction.equals("Management Hold")){
				newManagementHolding(false);
			}
			
			if(transaction.equals("Commitment Fee")){
				newCommitmentFee(true);
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
			Timestamp valid_from = (Timestamp) data[21];
			Timestamp valid_to = (Timestamp) data[22];
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
			dateValidFrom.setDate(valid_from);
			dateValidTo.setDate(valid_to);
			txtCashAmount.setValue(amount_paid);

			setFieldsEditable();
			cmbTransaction.setEnabled(false);
			har.btnState(true, (credited ? false:status.equals("O")), true, false, true);

			return true;
		}else{
			return false;
		}
	}
	
	private void afterTransaction() {
		setFieldsEditable();
		cmbTransaction.setEnabled(false);
		
		har.btnState(true, true, true, false, true);
	}

	public void setHoldingEnabled(boolean enable) {
		panelLooper(this, enable);
	}

	public void setFieldsEnabled(JPanel panel, boolean enable) {
		panelLooper(panel, enable);
	}

	private void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				panelLooper((JScrollPane) comp, enable);
			} else if (comp instanceof JSplitPane) {
				panelLooper((JSplitPane) comp, enable);
			} else {
				if (panel instanceof JScrollPane) {
					((JScrollPane) panel).getViewport().getView().setEnabled(enable);
				} else {
					comp.setEnabled(enable);
				}
			}
		}
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

		//Check if sales agent is blank
		if(lblSalesDivision.getText().contains("*") && lookupSalesDivision.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select sales division", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if sales agent is blank
		if(lblSalesDivision.getText().contains("*") && lookupSalesDivisionGroup.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select sales group", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if extended until is blank
		if(lblExtendedUntil.getText().contains("*") && dateExtendedUntil.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input extended until.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		//Check if cash amount is blank
		if(lblCashAmount.getText().contains("*") && txtCashAmount.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input cash amount", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			if(lblCashAmount.getText().contains("*")){
				String transaction = (String) cmbTransaction.getSelectedItem();

				if(transaction.equals("Hold")){
					//Check if cash amount is less than 3,000
					if(((BigDecimal)txtCashAmount.getValued()).compareTo(new BigDecimal("3000")) == -1){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Holding transaction amount must be 3,000 pesos and above. ", "Save", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}

				if(transaction.equals("Extension")){
					//Check if cash amount is less than 2,000
					if(((BigDecimal)txtCashAmount.getValued()).compareTo(new BigDecimal("2000")) == -1){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Extension transaction amount must be 2,000 pesos and above.", "Save", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}

				if(transaction.equals("Float")){
					//Check if cash amount is less than 1,000
					if(((BigDecimal)txtCashAmount.getValued()).compareTo(new BigDecimal("1000")) == -1){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Float transaction amount must be 1,000 pesos and above.", "Save", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}
			}
		}

		return true;
	}

	public Boolean saveHolding(String entity_id) {//XXX SAVE
		if(toSave()){

			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String transaction = (String) cmbTransaction.getSelectedItem();
				String tran_type = Integer.toString(cmbTransaction.getSelectedIndex());

				if(cmbTransaction.isEnabled()){
					String client_seqno = lblClientSeqNo.getText().replaceAll("[\\D]", "").trim();
					String branch_id = UserInfo.Branch;
					String proj_id = lookupProject.getValue();
					String model_id = txtModel.getText();
					BigDecimal sellingprice = (BigDecimal) txtSellingPrice.getValued();
					String selling_agent = lookupSalesAgent.getValue();
					String dept_code = lookupSalesDivisionGroup.getText();
					Date hold_until = dateValidTo.getDate();
					Date extended_until = dateExtendedUntil.getDate();
					String remarks = txtRemarks.getText();

					String co_id = lookupCompany.getValue();
					String op_user_id = UserInfo.EmployeeCode;
					String unit_id = lookupPBL.getValue();

					BigDecimal amount = (BigDecimal) txtCashAmount.getValued();

					if(transaction.equals("Hold") || transaction.equals("Float") || transaction.equals("Management Hold") || transaction.equals("Commitment Fee")){
						String SQL = "SELECT * FROM sp_tag_holding('"+ branch_id +"', '"+ entity_id +"', '"+ proj_id +"', '"+ unit_id +"', '"+ model_id +"',\n" + 
								"  "+ sellingprice +", "+ amount +", '"+ selling_agent +"', '"+ dept_code +"', '"+ receipt_id +"', '"+ hold_until +"',\n" + 
								"  '"+ tran_type +"', '"+ remarks.trim().replace("'", "''") +"', '"+ co_id +"', '"+ op_user_id +"')";

						FncSystem.out("Hold", SQL);
						pgSelect db = new pgSelect();
						db.select(SQL, "Client Sequence No.", true, false);
						if(db.isNotNull()){
							Boolean isSaved = (Boolean) db.getResult()[0][0];
							client_seqno = (String) db.getResult()[0][1];
							String message = (String) db.getResult()[0][2];

							if(isSaved){
								//Return Successfully
								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), message, "Save", JOptionPane.INFORMATION_MESSAGE);

								lblClientSeqNo.setText(String.format("[ %s ]", client_seqno));
								afterTransaction();
							}
							return isSaved;
						}else{
							return false;
						}
					}

					if(transaction.equals("Unhold")){
						String pbl_id = unit_id.replaceAll("[^\\d]", "");
						pbl_id = Integer.toString(Integer.parseInt(pbl_id));
						System.out.printf("Display value of pbl id: %s%n", pbl_id);
						
						//String SQL = "SELECT sp_save_unholding(ARRAY['"+ (client_seqno.equals("") ? pbl_id:client_seqno) +"']::VARCHAR[], '"+ UserInfo.EmployeeCode +"');";
						String SQL = "SELECT sp_save_unholding(ARRAY['"+ client_seqno +"']::VARCHAR[], '"+proj_id+"' ,ARRAY['"+pbl_id+"']::VARCHAR[] ,'"+ UserInfo.EmployeeCode +"');";
						pgSelect db = new pgSelect();
						db.select(SQL);

						//Return Successfully
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Transaction has been unhold successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);

						cancelHolding();
					}

					if(transaction.equals("Extension")){
						String SQL = "SELECT * FROM sp_tag_extension('"+ client_seqno +"', '"+ branch_id +"', '"+ entity_id +"', '"+ proj_id +"', '"+ unit_id +"', '"+ model_id +"',\n" + 
								"  "+ sellingprice +", "+ amount +", '"+ selling_agent +"', '"+ dept_code +"', '"+ receipt_id +"', '"+ extended_until +"',\n" + 
								"  '"+ tran_type +"', '"+ co_id +"', '"+ remarks.trim().replace("'", "''") +"', '"+ op_user_id +"');";

						FncSystem.out("Extension", SQL);
						pgSelect db = new pgSelect();
						db.select(SQL, "Save", true);

						if(db.isNotNull()){
							Boolean isSaved = (Boolean) db.getResult()[0][0];

							if(isSaved){
								//Return Successfully
								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Transaction has been extended successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);

								cancelHolding();

								return (Boolean) db.getResult()[0][0];
							}else{
								return false;
							}
						}else{
							return false;
						}
					}
				}else{
					String SQL = "SELECT sp_update_hold('"+ entity_id +"', "+ cmbTransaction.getSelectedIndex() +", '"+ cmbTransaction.getSelectedItem() +"', '"+ lblClientSeqNo.getText().replaceAll("[\\D]", "").trim() +"',\n" + 
							"  '"+ lookupCompany.getValue() +"', '"+ lookupProject.getValue() +"', '"+ lookupPBL.getValue() +"', '"+ txtModel.getText() +"', "+ txtSellingPrice.getValued() +", '"+ lookupSalesAgent.getValue() +"',\n" + 
							"  '"+ lookupSalesDivisionGroup.getValue() +"', NULLIF('"+ dateValidFrom.getDate() +"', 'null')::TIMESTAMP, NULLIF('"+ dateValidTo.getDate() +"', 'null')::TIMESTAMP, NULLIF('"+ dateExtendedUntil.getDate() +"', 'null')::TIMESTAMP, '"+ lookupParticular.getValue() +"', "+ txtCashAmount.getValued() +", '"+ UserInfo.Branch +"',\n" + 
							"  NULLIF('"+ txtRemarks.getText().trim().replace("'", "''") +"', '')::TIMESTAMP::VARCHAR, '"+ UserInfo.EmployeeCode +"');";
					System.out.printf("Update: %s", SQL);

					pgSelect db = new pgSelect();
					db.select(SQL, "Save", true);
					if(db.isNotNull()){


						if((Boolean) db.getResult()[0][0]) {
							//Return Successfully
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Transaction has been updated.", "Save", JOptionPane.INFORMATION_MESSAGE);

							cancelHolding();
						}
						return (Boolean) db.getResult()[0][0];
					}else{
						return false;
					}
				}

				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * @return the entity_id
	 */
	public String getEntityID() {
		return entity_id;
	}

	/**
	 * @param entity_id the entity_id to set
	 */
	public void setEntityID(String entity_id) {
		this.entity_id = entity_id;
	}


	/**
	 * @author Alvin Gonzales
	 */
	private Boolean isAllowedToManagementHold() {
		pgSelect db = new pgSelect();
		db.select("SELECT is_allowed_to_management_hold('"+ UserInfo.EmployeeCode +"');", "Management Hold", true, true);
		return (Boolean) db.getResult()[0][0];
	}

}
