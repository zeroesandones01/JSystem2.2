/**
 * 
 */
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import tablemodel.modelCARD_Dues;
import tablemodel.modelHoldingAndReservation_Requests;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;

/**
 * @author Alvin gonzales
 *
 */
public class pnlOfficialReservation extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -352354794508370337L;

	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JPanel pnlNorthCenter;

	private JLabel lblProject;
	private _JLookup lookupProject;
	private _JXTextField txtProjectName;

	private JLabel lblUnit;
	private _JLookup lookupUnit;
	private _JXTextField txtUnit;

	private JLabel lblSequence;
	private _JXTextField txtSequence;

	private JLabel lblModel;
	private _JXTextField txtModelID;
	private _JXTextField txtModelName;

	private JLabel lblStatus;
	private _JXTextField txtStatusID;
	private _JXTextField txtStatusName;

	private JPanel pnlSouth;

	private JPanel pnlNorthEast;

	private JLabel lblGSP;
	private _JXFormattedTextField txtGSP;

	private JLabel lblNSP;
	private _JXFormattedTextField txtNSP;

	private JLabel lblVAT;
	private _JXFormattedTextField txtVAT;

	private JTabbedPane tabCenter;

	private JPanel pnlClientRequest;

	private JPanel pnlPaymentInfo;
	private JPanel pnlPIWest;
	private JLabel lblBuyerType;
	private JLabel lblPaymentScheme;
	private _JXTextField txtPaymentSchemeName;
	private _JXTextField txtBuyerTypeName;
	private JPanel pnlPICenterCenter;
	private JPanel pnlPICenterWest;
	private JLabel lblTRDate;
	/*private JLabel lblORDate;
	private JLabel lblRESAmountPaid;
	private JLabel lblRecquiedRESFee;
	private JLabel lblRESBalance;*/


	private _JXTextField txtBuyerTypeID;
	private _JXTextField txtPaymentSchemeID;
	private _JXTextField txtTRDate;
	/*private _JXTextField txtORDate;
	private _JXFormattedTextField txtRESAmountPaid;
	private _JXFormattedTextField txtRecquiredRESFee;
	private _JXFormattedTextField txtRESBalance;*/

	private JScrollPane scrollRequests;
	private _JTableMain tblRequests;
	private modelHoldingAndReservation_Requests modelRequests;
	private JList rowHeaderRequests;

	private _JScrollPaneMain scrollDues;
	private _JTableMain tblDues;
	private modelCARD_Dues modelDues;
	private JList rowheaderDues;

	private _JScrollPaneTotal scrollDuesTotal;
	private _JTableTotal tblDuesTotal;
	private modelCARD_Dues modelDuesTotal;

	private JPanel pnlPICenter;

	private String entity_id;
	private String entity_name;

	private HoldingAndReservation har;

	public pnlOfficialReservation(HoldingAndReservation har) {
		this.har = har;
		initGUI();
	}

	public pnlOfficialReservation(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOfficialReservation(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOfficialReservation(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(600, 400));
		{
			pnlNorth = new JPanel(new BorderLayout());
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
			pnlNorth.setPreferredSize(new Dimension(590, 135));
			{
				pnlNorthWest = new JPanel(new SpringLayout());
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				//pnlNorthWest.setBorder(_LINE_BORDER);
				pnlNorthWest.setPreferredSize(new Dimension(150, 0));
				{
					lblProject = new JLabel("Project");
					pnlNorthWest.add(lblProject);
				}
				{
					lookupProject = new _JLookup(null, "Project", 0, "Client has no account to OR.");
					pnlNorthWest.add(lookupProject);
					lookupProject.setPrompt("Project ID");
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								clearOR();

								((_JLookup) event.getSource()).setValue((String) data[0]);
								txtProjectName.setText((String) data[1]);

								lookupUnit.setLookupSQL(_HoldingAndReservation.sqlORUnits(entity_id, (String) data[0]));
							}
						}
					});
				}
				{
					lblUnit = new JLabel("Unit");
					pnlNorthWest.add(lblUnit);
				}
				{
					lookupUnit = new _JLookup(null, "Unit", 0, "Please select project.");
					pnlNorthWest.add(lookupUnit);
					lookupUnit.setPrompt("Unit ID");
					lookupUnit.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Units
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {//XXX Unit
								txtUnit.setText((String) data[1]);
								txtSequence.setText(Integer.toString((Integer) data[2]));
								txtModelID.setText((String) data[3]);
								txtModelName.setText((String) data[4]);
								txtStatusID.setText((String) data[5]);
								txtStatusName.setText((String) data[6]);
								txtGSP.setValue((BigDecimal) data[7]);
								txtNSP.setValue((BigDecimal) data[8]);
								txtVAT.setValue(null);

								displayPaymentInformation(entity_id, lookupProject.getValue(), (String) data[0], (Integer) data[2]);
								displayClientRequestsPastDues();

								har.btnState(false, false, false, true, true);
							}
						}
					});
				}
				{
					lblModel = new JLabel("Model");
					pnlNorthWest.add(lblModel);
				}
				{
					txtModelID = new _JXTextField("Model ID");
					pnlNorthWest.add(txtModelID);
					txtModelID.setHorizontalAlignment(JXTextField.CENTER);
				}
				{
					lblStatus = new JLabel("Status");
					pnlNorthWest.add(lblStatus);
				}
				{
					txtStatusID = new _JXTextField("Status ID");
					pnlNorthWest.add(txtStatusID);
					txtStatusID.setHorizontalAlignment(JXTextField.CENTER);
				}
				SpringUtilities.makeCompactGrid(pnlNorthWest, 4, 2, 5, 0, 3, 3, false);
			}
			{
				pnlNorthCenter = new JPanel(new SpringLayout());
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				{
					txtProjectName = new _JXTextField();
					pnlNorthCenter.add(txtProjectName);
				}
				{
					JPanel pnlUnit = new JPanel(new BorderLayout());
					pnlNorthCenter.add(pnlUnit);
					{
						txtUnit = new _JXTextField();
						pnlUnit.add(txtUnit, BorderLayout.CENTER);
					}
					{
						JPanel pnlSequence = new JPanel(new BorderLayout(5, 5));
						pnlUnit.add(pnlSequence, BorderLayout.EAST);
						pnlSequence.setPreferredSize(new Dimension(150, 0));
						{
							lblSequence = new JLabel("Sequence No.", JLabel.TRAILING);
							pnlSequence.add(lblSequence, BorderLayout.WEST);
							lblSequence.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtSequence = new _JXTextField();
							pnlSequence.add(txtSequence, BorderLayout.CENTER);
							txtSequence.setHorizontalAlignment(JXTextField.CENTER);
						}
					}
				}
				{
					txtModelName = new _JXTextField();
					pnlNorthCenter.add(txtModelName);
				}
				{
					txtStatusName = new _JXTextField();
					pnlNorthCenter.add(txtStatusName);
				}
				SpringUtilities.makeCompactGrid(pnlNorthCenter, 4, 1, 0, 0, 5, 3, false);
			}
			{
				pnlNorthEast = new JPanel(new SpringLayout());
				pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
				pnlNorthEast.setPreferredSize(new Dimension(170, 0));
				{
					lblGSP = new JLabel("GSP");
					pnlNorthEast.add(lblGSP);
				}
				{
					txtGSP = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlNorthEast.add(txtGSP);
					txtGSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtGSP.setEditable(false);
				}
				{
					lblNSP = new JLabel("NSP");
					pnlNorthEast.add(lblNSP);
				}
				{
					txtNSP = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlNorthEast.add(txtNSP);
					txtNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtNSP.setEditable(false);
				}
				{
					lblVAT = new JLabel("VAT");
					pnlNorthEast.add(lblVAT);
				}
				{
					txtVAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlNorthEast.add(txtVAT);
					txtVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtVAT.setEditable(false);
				}
				{
					pnlNorthEast.add(new JLabel(" "));

					_JXTextField box = new _JXTextField();
					pnlNorthEast.add(box);
					box.setOpaque(false);
					box.setBorder(BorderFactory.createEmptyBorder());
				}
				SpringUtilities.makeCompactGrid(pnlNorthEast, 4, 2, 5, 0, 5, 3, false);
			}
		}
		{
			tabCenter = new JTabbedPane();
			this.add(tabCenter, BorderLayout.CENTER);
			{
				pnlPaymentInfo = new JPanel(new BorderLayout(3, 3));
				tabCenter.addTab("  Payment Info.  ", null, pnlPaymentInfo, null);
				pnlPaymentInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
				{
					JPanel pnlPINorth = new JPanel(new BorderLayout(3, 3));
					pnlPaymentInfo.add(pnlPINorth, BorderLayout.NORTH);
					pnlPINorth.setPreferredSize(new Dimension(0, 75));
					{
						pnlPIWest = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlPINorth.add(pnlPIWest, BorderLayout.WEST);
						pnlPIWest.setPreferredSize(new Dimension(127, 228));
						{
							lblBuyerType = new JLabel("Buyer Type");
							pnlPIWest.add(lblBuyerType);
						}
						{
							lblPaymentScheme = new JLabel("Payment Scheme");
							pnlPIWest.add(lblPaymentScheme);
						}
						{
							lblTRDate = new JLabel("TR Date");
							pnlPIWest.add(lblTRDate);
						}
						/*{
							lblORDate = new JLabel("OR Date");
							pnlPIWest.add(lblORDate);
						}
						{
							lblRESAmountPaid = new JLabel("RES Amount Paid");
							pnlPIWest.add(lblRESAmountPaid);
						}
						{
							lblRecquiedRESFee = new JLabel("Recquired RES Fee");
							pnlPIWest.add(lblRecquiedRESFee);
						}
						{
							lblRESBalance = new JLabel("RES Balance");
							pnlPIWest.add(lblRESBalance);
						}*/
					}
					{
						pnlPICenter = new JPanel(new BorderLayout(3, 3));
						pnlPINorth.add(pnlPICenter, BorderLayout.CENTER);
						{
							pnlPICenterWest = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlPICenter.add(pnlPICenterWest, BorderLayout.WEST);
							pnlPICenterWest.setPreferredSize(new Dimension(90, 0));
							{
								txtBuyerTypeID = new _JXTextField("ID");
								pnlPICenterWest.add(txtBuyerTypeID);
								txtBuyerTypeID.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								txtPaymentSchemeID = new _JXTextField("ID");
								pnlPICenterWest.add(txtPaymentSchemeID);
								txtPaymentSchemeID.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								txtTRDate = new _JXTextField();
								pnlPICenterWest.add(txtTRDate);
								txtTRDate.setHorizontalAlignment(JXTextField.CENTER);
							}
							/*{
								txtORDate = new _JXTextField();
								pnlPICenterWest.add(txtORDate);
								txtORDate.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								txtRESAmountPaid = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlPICenterWest.add(txtRESAmountPaid);
								txtRESAmountPaid.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtRESAmountPaid.setEditable(false);
							}
							{
								txtRecquiredRESFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlPICenterWest.add(txtRecquiredRESFee);
								txtRecquiredRESFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtRecquiredRESFee.setEditable(false);
							}
							{
								txtRESBalance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlPICenterWest.add(txtRESBalance);
								txtRESBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtRESBalance.setEditable(false);
							}*/
						}
						{
							pnlPICenterCenter = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlPICenter.add(pnlPICenterCenter, BorderLayout.CENTER);
							{
								txtBuyerTypeName = new _JXTextField();
								pnlPICenterCenter.add(txtBuyerTypeName);
							}
							{
								txtPaymentSchemeName = new _JXTextField();
								pnlPICenterCenter.add(txtPaymentSchemeName);
							}
						}
					}
				}
				{
					JPanel pnlPICenter = new JPanel(new BorderLayout());
					pnlPaymentInfo.add(pnlPICenter, BorderLayout.CENTER);
					pnlPICenter.setBorder(components.JTBorderFactory.createTitleBorder("Dues"));
					{
						scrollDues = new _JScrollPaneMain();
						pnlPICenter.add(scrollDues, BorderLayout.CENTER);
						{
							modelDues = new modelCARD_Dues();

							tblDues = new _JTableMain(modelDues);
							scrollDues.setViewportView(tblDues);
							tblDues.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblDues.rowAtPoint(e.getPoint()) == -1){
										tblDuesTotal.clearSelection();
									}else{
										tblDues.setCellSelectionEnabled(true);
									}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblDues.rowAtPoint(e.getPoint()) == -1){
										tblDuesTotal.clearSelection();
									}else{
										tblDues.setCellSelectionEnabled(true);
									}
								}
							});

							//Process after row add in tables
							modelDues.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									((DefaultListModel)rowheaderDues.getModel()).addElement(modelDues.getRowCount());
									scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelDues.getRowCount())));

									if(modelDues.getRowCount() == 0){
										rowheaderDues.setModel(new DefaultListModel());
									}
								}
							});
						}
						{
							rowheaderDues = tblDues.getRowHeader();
							rowheaderDues.setModel(new DefaultListModel());
							scrollDues.setRowHeaderView(rowheaderDues);
							scrollDues.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollDuesTotal = new _JScrollPaneTotal(scrollDues);
						pnlPICenter.add(scrollDuesTotal, BorderLayout.SOUTH);
						{
							modelDuesTotal = new modelCARD_Dues();
							modelDuesTotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null, null, null, null, null, null, null, 0.00 });

							tblDuesTotal = new _JTableTotal(modelDuesTotal, tblDues);
							scrollDuesTotal.setViewportView(tblDuesTotal);

							tblDuesTotal.setTotalLabel(1);
						}
					}
				}
			}
			{
				pnlClientRequest = new JPanel(new BorderLayout());
				tabCenter.addTab("  Client Request  ", null, pnlClientRequest, null);
				pnlClientRequest.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				{
					scrollRequests = new JScrollPane();
					pnlClientRequest.add(scrollRequests, BorderLayout.CENTER);
					scrollRequests.setBorder(components.JTBorderFactory.createTitleBorder("Requests"));
					scrollRequests.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblRequests.clearSelection();
						}
					});
					{
						modelRequests = new modelHoldingAndReservation_Requests();
						modelRequests.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeaderRequests.getModel()).addElement(modelRequests.getRowCount());

									if(modelRequests.getRowCount() == 0){
										rowHeaderRequests.setModel(new DefaultListModel());
									}
								}
							}
						});

						tblRequests = new _JTableMain(modelRequests);
						scrollRequests.setViewportView(tblRequests);
						tblRequests.hideColumns("ID");
					}
					{
						rowHeaderRequests = tblRequests.getRowHeader();
						rowHeaderRequests.setModel(new DefaultListModel());
						scrollRequests.setRowHeaderView(rowHeaderRequests);
						scrollRequests.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollRequests.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRequests.getRowCount())));
					}
				}
			}
		}
		{
			pnlSouth = new JPanel();
			//this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setPreferredSize(new Dimension(590, 30));
		}
		setComponentsEnabled(this, false);

		FncTables.bindColumnTables(tblDues, tblDuesTotal);
		tblDues.hideColumns("Part. ID", "CBP", "Adjustments");
	}

	public void initializeThis(String entity_id) {
		this.entity_id = entity_id.intern();

		lookupProject.setLookupSQL(_HoldingAndReservation.sqlORProject(entity_id));
		setComponentsEnabled(this, true);
		clearOR();
	}

	public void newOR(String entity_id, String entity_name) {
		this.entity_id = entity_id.intern();
		this.entity_name = entity_name.intern();

		lookupProject.setLookupSQL(_HoldingAndReservation.sqlORProject(entity_id));
		lookupProject.requestFocus();

		setComponentsEnabled(this, true);
		clearOR();
	}

	public void cancelOR() {
		setComponentsEnabled(this, false);
		clearOR();
	}

	public void clearOR() {
		lookupProject.setValue(null);
		txtProjectName.setText("");
		lookupUnit.setValue(null);
		txtUnit.setText("");
		txtSequence.setText("");
		txtModelID.setText("");
		txtModelName.setText("");
		txtStatusID.setText("");
		txtStatusName.setText("");
		txtGSP.setValue(null);
		txtNSP.setValue(null);
		txtVAT.setValue(null);

		txtBuyerTypeID.setText("");
		txtBuyerTypeName.setText("");
		txtPaymentSchemeID.setText("");
		txtPaymentSchemeName.setText("");
		txtTRDate.setText("");
		/*txtORDate.setText("");
		txtRESAmountPaid.setValue(null);
		txtRecquiredRESFee.setValue(null);
		txtRESBalance.setValue(null);*/

		rowHeaderRequests.setModel(new DefaultListModel());
		modelRequests.clear();
		rowheaderDues.setModel(new DefaultListModel());
		modelDues.clear();
	}

	private void displayPaymentInformation(String entity_id, String proj_id, String unit_id, Integer seq_no) {
		Object[] data = _HoldingAndReservation.getPaymentInformation(entity_id, proj_id, unit_id, seq_no);

		if(data != null){
			txtBuyerTypeID.setText((String) data[0]);
			txtBuyerTypeName.setText((String) data[1]);
			txtPaymentSchemeID.setText((String) data[2]);
			txtPaymentSchemeName.setText((String) data[3]);
			txtTRDate.setText((String) data[4]);
			/*txtORDate.setText((String) db.getResult()[0][5]);
			txtRESAmountPaid.setValue((BigDecimal) db.getResult()[0][6]);
			txtRecquiredRESFee.setValue((BigDecimal) db.getResult()[0][7]);
			txtRESBalance.setValue(((BigDecimal) db.getResult()[0][7]).subtract((BigDecimal) db.getResult()[0][6]));*/
		}
	}

	private void displayClientRequestsPastDues() {
		rowHeaderRequests.setModel(new DefaultListModel());
		_HoldingAndReservation.displayRequests(modelRequests, entity_id, lookupProject.getValue(), lookupUnit.getValue(), txtSequence.getText());
		scrollRequests.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRequests.getRowCount())));
		tblRequests.packAll();

		rowheaderDues.setModel(new DefaultListModel());
		_HoldingAndReservation.displayDues(modelDues, entity_id, lookupProject.getValue(), lookupUnit.getValue(), txtSequence.getText(), modelDuesTotal);
		scrollDues.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDues.getRowCount())));
		tblDues.packAll();
	}

	public Boolean toSave() {// XXX To Save
		if(lookupProject.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupUnit.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Unit.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if(tblRequests.getRowCount() > 0){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please kindly settle all Requests.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(tblDues.getRowCount() > 0){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please kindly settle all Past Dues.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		return true;
	}

	public Boolean saveOR(String entity_id, String entity_name) {//XXX Save OR
		if(toSave()){
			String SQL = "SELECT is_qualified_for_or('"+ entity_id +"', '"+ lookupProject.getValue() +"', getinteger('"+ lookupUnit.getValue() +"')::VARCHAR, "+ txtSequence.getText() +", '"+ txtBuyerTypeID.getText() +"');";


			FncSystem.out("OR Qualification", SQL);

			pgSelect dbSelect = new pgSelect();
			dbSelect.select(SQL, "Save", true);

			if((Boolean) dbSelect.getResult()[0][0]){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					String branch_id = UserInfo.Branch;
					String proj_id = lookupProject.getValue();
					String unit_id = lookupUnit.getValue();
					String seq_no = txtSequence.getText();
					BigDecimal selling_price = (BigDecimal) txtGSP.getValued();
					//String selling_agent = lookupSalesAgent.getValue();
					String type_id = txtBuyerTypeID.getText();
					String pmt_scheme_id = txtPaymentSchemeID.getText();
					String model_id = txtModelID.getText();
					String createdby = UserInfo.EmployeeCode;

					//BigDecimal dp_amount = (BigDecimal) txtDPEquity.getValued();
					//BigDecimal loan_availed = (BigDecimal) txtAvailedDiscAmount.getValued();
					//BigDecimal disc_rate = (BigDecimal) txtDiscRate.getValued();

					/*String rf_pay_header = "SELECT sp_tag_reservation('"+ branch_id +"', '"+ entity_id +"', '"+ proj_id +"', '"+ unit_id +"', "+ seq_no +", \n" + 
							"	"+ selling_price +", null, '"+ type_id +"', '"+ pmt_scheme_id +"', '"+ model_id +"', '"+ createdby +"', \n" + 
							"	null, null, null, null, null, false)";*/

					String rf_pay_header = "SELECT sp_tag_reservation('"+ branch_id +"', '"+ entity_id +"', '"+ proj_id +"',\n" + 
							"	'"+ unit_id +"', "+ seq_no +", "+ selling_price +", NULL, '"+ type_id +"',\n" + 
							"	'"+ pmt_scheme_id +"', '"+ model_id +"', '"+ createdby +"', NULL, NULL, \n" + 
							"	NULL, NULL, NULL, NULL, false);";

					FncSystem.out("Reservation", rf_pay_header);
					dbSelect.select(rf_pay_header);

					if(dbSelect.isNotNull() && (Boolean) dbSelect.getResult()[0][0]){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client has been officially reserved.", "Save", JOptionPane.INFORMATION_MESSAGE);

						return (Boolean) dbSelect.getResult()[0][0];
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@SuppressWarnings("unused")
	private void displayDocumentsMonitoring() {
		if(FncGlobal.homeMDI.isNotExisting("DocumentsMonitoring")){
			String buyer_type_id = txtBuyerTypeID.getText();
			String buyer_type_name = txtBuyerTypeName.getText();
			String current_status_id = txtStatusID.getText();
			String current_status_name = txtStatusName.getText();
			String company_id = null;
			String company_name = null;
			String project_id = lookupProject.getValue();
			String project_name = txtProjectName.getText();
			String unit_id = lookupUnit.getValue();
			String unit_description = txtUnit.getText();
			String model_id = txtModelID.getText();
			String model_name = txtModelName.getText();
			Integer seq_no = Integer.parseInt(txtSequence.getText());
			String res_date = txtTRDate.getText();

			Pattern pattern = Pattern.compile("\\d+(?:\\.\\d+)?"); // Match int or float
			Matcher matcher = pattern.matcher(unit_id);
			String pbl_id = "";
			if(matcher.find()){
				pbl_id = Integer.toString(Integer.parseInt(matcher.group()));
			}

			Object[] data = new Object[]{entity_id, entity_name, buyer_type_id, buyer_type_name, current_status_id, current_status_name, company_id, company_name,
					project_id, project_name, unit_id, unit_description, model_id, model_name, pbl_id, seq_no, res_date};

			DocumentsMonitoring dm = new DocumentsMonitoring(data);
			Home_JSystem.addWindow(dm);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { }

	/**
	 * Added 2014-11-06 by Alvin Gonzales
	 *
	 */
	public void setComponentsEnabled(JPanel panel, boolean enable) {
		panelLooper(panel, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				panelLooper((JScrollPane) comp, enable);
			} else if (comp instanceof JTabbedPane) {
				panelLooper((JTabbedPane) comp, enable);
			} else {
				comp.setEnabled(enable);
			}
		}
	}

	/**
	 * Added 2014-11-07 by Alvin Gonzales
	 *
	 */
	public void setComponentsEditable(JPanel panel, boolean editable) {
		panelLooperEditable(panel, editable);
	}

	public void panelLooperEditable(Container panel, boolean editable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooperEditable((JPanel) comp, editable);
			} else if (comp instanceof JScrollPane) {
				panelLooperEditable((JScrollPane) comp, editable);
			} else {
				if(comp instanceof JTextField){
					((JTextField)comp).setEditable(editable);
				}
			}
		}
	}

}
