/**
 * 
 */
package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Dialogs.dlg_CR_PW_Entry;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._ButtonGroup;
import components._JInternalFrame;
import components._JRadioButton;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelROPSchedule;
import tablemodel.modelROP_Allocation;
import tablemodel.modelROP_Holding;
import tablemodel.modelROP_Miscellaneous;
import tablemodel.modelROP_OtherPmt;
import tablemodel.modelRefundofPayment;

/**
 * @author John Lester Fatallo
 */
public class RefundofPayment extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 4470630997801077903L;

	private static String title = "Refund of Payment";
	static Dimension SIZE = new Dimension(1000, 437);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	@SuppressWarnings("rawtypes")
	
	private JPanel pnlNorth;

	private JPanel pnlNorthWest;
	private JPanel pnlNWLabel;
	private JLabel lblClientName;
	private JLabel lblUnit;
	private JLabel lblProject;
	private JLabel lblSeqNo;
	private JLabel lblARNo;

	private JPanel pnlNWCenter;
	private JComboBox cmbRefundType;
	private JComboBox cmbClientType;
	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClientName;
	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;
	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProject;

	private JPanel pnlUnitStatus;
	private _JXTextField txtSeqNo;
	private JLabel lblUnitStat;
	private _JXTextField txtUnitStat;
	private _JXTextField txtARNo;

	private JPanel pnlNorthEast;
	private JPanel pnlNELabel;
	private JLabel lblRequestDate;
	private JLabel lblRequestNo;
	private JLabel lblRequestStat;
	private JLabel lblRemarks;
	private JPanel pnlNECenter;
	private _JXTextField txtRequestBy;
	private _JDateChooser requestDate;
	private _JXTextField txtRequestNo;
	private _JXTextField txtRequestStat;
	private _JXTextField txtRemarks;
	private _JXTextField txtRPLFNo;
	private _JDateChooser dteApprovedRPLF;
	
	private JPanel pnlCenter;
	private _JTabbedPane tabCenter;
	private JPanel pnlPaymentDetails;
	private JLabel lblRefundPaymentFrom;
	private ButtonGroup btngrpPayment;
	private _JRadioButton rbtnMiscellaneous;
	private _JRadioButton rbtnOtherPmt;
	private _JRadioButton rbtnHoldingPayments;

	private modelRefundofPayment modelRefPymnt;
	private JList rowHeaderRefundPayment;
	private _JTableMain tblRefundPayment;
	private _JScrollPaneMain scrollRefundPayment;
	private JLabel lblDateNeeded;
	private _JDateChooser dateNeeded;
	private JPanel pnlRefundAmt;
	private  JLabel lblAmtRefund;
	private _JXFormattedTextField txtAmtRefund;
	private _JXFormattedTextField txtCreditAmt;

	private _JScrollPaneTotal scrollROPTotal;
	private modelRefundofPayment modelROPTotal;
	private _JTableTotal tblROPTotal;

	private modelROP_OtherPmt modelOtherPmtTotal;
	private modelROP_Miscellaneous modelMiscTotal;
	private modelROP_Holding modelHoldingTotal;
	//private modelMiscellaneous modelMiscTotal;

	private JPanel pnlSchedule;
	private modelROPSchedule modelSchedule;
	private _JTableMain tblSchedule;
	private JScrollPane scrollSchedule;
	private JList rowHeaderSchedule;

	private JPanel pnlROP_Allocation;
	private _JTableMain tblROP_Allocation;
	private JScrollPane scrollROP_Allocation;
	
	private JList rowHeaderROP_Allocation;
	private modelROP_Allocation modelROP_Allocation;
	private JPanel pnlCreditTo;

	private JPanel pnlCreditToWest;
	private JLabel lblCreditClient;
	private JLabel lblCreditUnit;
	private JLabel lblCreditProj;
	private JLabel lblCreditSeq;
	private JPanel pnlCreditToCenter;
	private _JLookup lookupCreditClient;
	private _JXTextField txtCreditClient;
	private _JLookup lookupCreditUnit;
	private _JXTextField txtCreditUnit;
	private _JXTextField txtCreditProjID;
	private _JXTextField txtCreditProjName;
	private _JXTextField txtCreditSeqNo;

	private JPanel pnlScrollPanel;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancelReq;
	private JButton btnClear;
	private JButton btnSearch;
	private JButton btnPost;
	private JButton btnPreview;
	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	
	private modelROP_OtherPmt modelOtherPmt;
	private JList rowHeaderOtherPmt;

	private modelROP_Miscellaneous modelMisc;
	private JList rowHeaderMisc;

	private modelROP_Holding modelHolding;
	private JList rowHeaderHolding;

	private String entity_id;
	private String unit_id;
	private String proj_id;
	private String seq_no;

	private Map<String, BigDecimal> mapParticulars= new HashMap<String, BigDecimal>();

	public RefundofPayment() {
		super(title, true, true, true, true);
		initGUI();
	}

	public RefundofPayment(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//pnlMain.setLayout(new SpringLayout());
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			//pnlNorth.setBorder(lineBorder);
			//pnlNorth.setLayout(new SpringLayout());
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Refund Details"));
			pnlNorth.setLayout(new GridLayout(1, 2, 3, 3));
			{
				pnlNorthWest = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthWest);
				{
					pnlNWLabel = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorthWest.add(pnlNWLabel, BorderLayout.WEST);
					{
						JXLabel lblRefundType = new JXLabel("Refund From");
						pnlNWLabel.add(lblRefundType);
					}
					{
						lblClientName = new JLabel("Client");
						pnlNWLabel.add(lblClientName);
					}
					{
						lblUnit = new JLabel("Unit");
						pnlNWLabel.add(lblUnit);
					}
					{
						lblProject = new JLabel("Project");
						pnlNWLabel.add(lblProject);
					}
					{
						lblSeqNo = new JLabel("Seq. No");
						pnlNWLabel.add(lblSeqNo);
					}
				}
				{
					pnlNWCenter = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorthWest.add(pnlNWCenter, BorderLayout.CENTER);
					{
						JPanel pnlRefundFrom = new JPanel(new BorderLayout(3, 3));
						pnlNWCenter.add(pnlRefundFrom);
						{
							cmbRefundType = new JComboBox(new String [] {"Other Payments", "Miscellaneous Payments", "Holding Payments", "Subdivision Amenities"});
							pnlRefundFrom.add(cmbRefundType, BorderLayout.EAST);
							cmbRefundType.setSelectedItem(null);
							cmbRefundType.setEnabled(false);
							cmbRefundType.setPreferredSize(new Dimension(300, 0));
							cmbRefundType.addItemListener(new ItemListener() {
								
								@Override
								public void itemStateChanged(ItemEvent e) {
									int selected_item = cmbRefundType.getSelectedIndex();
									int client_type = cmbClientType.getSelectedIndex();
									
									if(selected_item == 0){
										//lookupClient.setLookupSQL(_RefundofPayment.sqlClients(false));
										if(client_type == 0) {
											lookupClient.setLookupSQL(_RefundofPayment.sqlOtherPmtClients());
										}else {
											lookupClient.setLookupSQL(_RefundofPayment.sqlOtherPmtClientsItsReal());
										}
										lookupClient.setEditable(true);
									}
									if(selected_item == 1){
										//lookupClient.setLookupSQL(_RefundofPayment.sqlClients(false));
										
										if(client_type == 0) {
											lookupClient.setLookupSQL(_RefundofPayment.sqlMiscPmtClients());
										}else {
											lookupClient.setLookupSQL(_RefundofPayment.sqlMiscConsbondItsreal());
										}
										lookupClient.setEditable(true);
									}
									if(selected_item == 2){
										//lookupClient.setLookupSQL(_RefundofPayment.sqlClients(true));
										lookupClient.setLookupSQL(_RefundofPayment.sqlHoldingClients());
										lookupClient.setEditable(true);
									}
									if(selected_item == 3) {
										lookupClient.setLookupSQL(_RefundofPayment.sqlClubhousePmts());
										lookupClient.setEditable(true);
									}
								}
							});
						}
						{
							cmbClientType = new JComboBox(new String [] {"JSystem", "ItsReal"});
							pnlRefundFrom.add(cmbClientType, BorderLayout.CENTER);
							cmbClientType.setEnabled(false);
							cmbClientType.addItemListener(new ItemListener() {
								
								@Override
								public void itemStateChanged(ItemEvent e) {
									int selected_item = cmbClientType.getSelectedIndex();
									
									if(selected_item == 0) {
										cmbRefundType.setEnabled(true);
									}
									
									if(selected_item == 1) {
										cmbRefundType.setSelectedIndex(1);
										//cmbRefundType.setEnabled(false);
									}
								}
							});
						}
					}
					{
						pnlClient = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlClient);
						{
							lookupClient = new _JLookup(null, "Select Client", 0);
							pnlClient.add(lookupClient, BorderLayout.WEST);
							lookupClient.setPreferredSize(new Dimension(100, 0));
							//lookupClient.setLookupSQL(_RefundofPayment.sqlClients());
							lookupClient.setFilterName(true);
							lookupClient.addLookupListener(new LookupListener() {

								@Override
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									FncSystem.out("Display sql for lookup of Client", lookupClient.getLookupSQL());

									if(data != null){

										String entity_id = (String) data[0];
										String entity_name = (String) data[1];
										String receipt_no = (String) data[2];
										String unit_desc = (String) data[3];
										String proj_name = (String) data[4];
										String unit_id = (String) data[5];
										String proj_id = (String) data[6];
										String seq_no = (String) data[7];
										//String pmt_scheme = (String) data[8];
										String unit_status = (String) data[9];
										/*String unit_id = (String) data[2];
										String proj_id = (String) data[3];
										String seq_no = (String) data[4];
										//System.out.printf("Display seq no: %s%n", seq_no);
										String unit_status = (String) data[6];
										String proj_name = (String) data[7];*/

										txtClientName.setText(entity_name);
										txtUnitID.setText(unit_id);
										txtUnitDesc.setText(unit_desc);
										txtProjID.setText(proj_id);
										txtProject.setText(proj_name);
										txtSeqNo.setText(seq_no);
										txtUnitStat.setText(unit_status);
										txtRemarks.setEditable(true);
										txtRequestBy.setEditable(true);
										cmbRefundType.setEnabled(false);
										lookupClient.setEditable(false);
										cmbClientType.setEnabled(false);
										
										if(cmbRefundType.getSelectedItem() != null){
											if(cmbRefundType.getSelectedIndex() == 0){
												_RefundofPayment.displayLedgerPayment(modelRefPymnt, rowHeaderRefundPayment ,lookupClient.getValue(), txtProjID.getText() ,txtUnitID.getText(), txtSeqNo.getText(), "", false);
												scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
												tblRefundPayment.packAll();
												//displaySchedule(entity_id, proj_id, unit_id, seq_no.toString(), unit_status);
												computeTotalPmt();
												pnlState(true, true, true);
											}
											
											if(cmbRefundType.getSelectedIndex() == 1){
												_RefundofPayment.displayOtherPayment(modelRefPymnt, rowHeaderRefundPayment, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText() ,txtSeqNo.getText(), "", false);
												scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
												tblRefundPayment.hideColumns("RPLF No", "Released By", "Released Date", "Received By", "Received Date");
												tblRefundPayment.packAll();
												//displaySchedule(entity_id, proj_id, unit_id, seq_no.toString(), unit_status);
												computeTotalPmt();
												pnlState(true, true, true);
											}
											
											if(cmbRefundType.getSelectedIndex() == 2){
												_RefundofPayment.displayHoldingPayments(modelRefPymnt, rowHeaderRefundPayment, lookupClient.getValue(), "", false);
												scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
												tblRefundPayment.packAll();
												//displaySchedule(entity_id, proj_id, unit_id, seq_no.toString(), unit_status);
												computeTotalPmt();
												pnlState(true, true, false);
											}
											
											if(cmbRefundType.getSelectedIndex() == 3) {
												_RefundofPayment.displaySubdivisionPmts(modelRefPymnt, rowHeaderRefundPayment, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), "", false);
												scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
												tblRefundPayment.packAll();
												computeTotalPmt();
												pnlState(true, true, false);
											}
										}
										
										_RefundofPayment.displayRefundAllocation(modelROP_Allocation, proj_id);
										scrollROP_Allocation.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblROP_Allocation.getRowCount())));
										tblROP_Allocation.packAll();
									}
								}
							});
							lookupClient.addFocusListener(new FocusListener() {
								
								@Override
								public void focusLost(FocusEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void focusGained(FocusEvent e) {
									FncSystem.out("Display value of SQL:", lookupClient.getLookupSQL());
									
								}
							});
						}
						{
							txtClientName = new _JXTextField("Name");
							pnlClient.add(txtClientName, BorderLayout.CENTER);
						}
					}
					{
						pnlUnit = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlUnit);
						{
							txtUnitID = new _JXTextField("Unit ID");
							pnlUnit.add(txtUnitID, BorderLayout.WEST);
							txtUnitID.setPreferredSize(new Dimension(100, 0));
							txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtUnitDesc = new _JXTextField("Unit Desc");
							pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
						}
					}
					{
						pnlProject = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlProject);
						{
							txtProjID = new _JXTextField("ID");
							pnlProject.add(txtProjID, BorderLayout.WEST);
							txtProjID.setPreferredSize(new Dimension(50, 0));
							txtProjID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtProject = new _JXTextField("Name");
							pnlProject.add(txtProject, BorderLayout.CENTER);
						}
					}
					{
						pnlUnitStatus = new JPanel(new BorderLayout(5, 5));
						pnlNWCenter.add(pnlUnitStatus);
						{
							txtSeqNo = new _JXTextField("Seq");
							pnlUnitStatus.add(txtSeqNo, BorderLayout.WEST);
							txtSeqNo.setPreferredSize(new Dimension(50, 0));
							txtSeqNo.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblUnitStat = new JLabel("Unit Status", JLabel.TRAILING);
							pnlUnitStatus.add(lblUnitStat);
						}
						{
							txtUnitStat = new _JXTextField("Unit Status");
							pnlUnitStatus.add(txtUnitStat, BorderLayout.EAST);
							txtUnitStat.setPreferredSize(new Dimension(200, 0));
						}
					}
				}
			}
			{
				pnlNorthEast = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthEast);
				{
					pnlNELabel = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorthEast.add(pnlNELabel, BorderLayout.WEST);
					/*{
						pnlNELabel.add(Box.createHorizontalBox());
					}*/
					{
						JLabel lblRequestby = new JLabel("Request By");
						pnlNELabel.add(lblRequestby);
					}
					{
						lblRequestDate = new JLabel("Request Date");
						pnlNELabel.add(lblRequestDate);
					}
					{
						lblRequestNo = new JLabel("Request No");
						pnlNELabel.add(lblRequestNo);
					}
					{
						lblRequestStat = new JLabel("Request Status");
						pnlNELabel.add(lblRequestStat);
					}
					{
						lblRemarks = new JLabel("Remarks");
						pnlNELabel.add(lblRemarks);
					}
				}
				{
					pnlNECenter = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorthEast.add(pnlNECenter, BorderLayout.CENTER);
					/*{
						pnlNECenter.add(Box.createHorizontalBox());
					}*/
					{
						txtRequestBy = new _JXTextField();
						pnlNECenter.add(txtRequestBy);
					}
					{
						requestDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlNECenter.add(requestDate);
						requestDate.setEnabled(false);
					}
					{
						txtRequestNo = new _JXTextField("Request No");
						pnlNECenter.add(txtRequestNo);
					}
					{
						txtRequestStat = new _JXTextField("Request Status");
						pnlNECenter.add(txtRequestStat);
					}
					{
						txtRemarks = new _JXTextField("Remarks");
						pnlNECenter.add(txtRemarks);
					}
				}
			}
		}
		{
			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			{
				pnlPaymentDetails = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("Payment Details", pnlPaymentDetails);
				{
					pnlScrollPanel = new JPanel(new BorderLayout(5, 5));
					pnlPaymentDetails.add(pnlScrollPanel, BorderLayout.CENTER);
					{
						scrollRefundPayment = new _JScrollPaneMain();
						pnlScrollPanel.add(scrollRefundPayment, BorderLayout.CENTER);
						//scrollRefundPayment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						{
							modelRefPymnt = new modelRefundofPayment();

							tblRefundPayment = new _JTableMain(modelRefPymnt);
							scrollRefundPayment.setViewportView(tblRefundPayment);
							tblRefundPayment.hideColumns("Pay Part ID", "Pay Rec ID", "RPLF No", "Approved By", "Approved Date");
							
							modelRefPymnt.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									/*if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderRefundPayment.getModel()).addElement(modelRefPymnt.getRowCount());
									}*/

									if(modelRefPymnt.getRowCount() == 0){
										rowHeaderRefundPayment.setModel(new DefaultListModel());
									}
								}
							});
						}
						{
							rowHeaderRefundPayment = tblRefundPayment.getRowHeader();
							rowHeaderRefundPayment.setModel(new DefaultListModel());
							scrollRefundPayment.setRowHeaderView(rowHeaderRefundPayment);
							scrollRefundPayment.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollROPTotal = new _JScrollPaneTotal(scrollRefundPayment);
						pnlScrollPanel.add(scrollROPTotal, BorderLayout.SOUTH);
						{
							modelROPTotal = new modelRefundofPayment();
							modelROPTotal.addRow(new Object[] {"Total", null, null, null});

							tblROPTotal = new _JTableTotal(modelROPTotal, tblRefundPayment);
							scrollROPTotal.setViewportView(tblROPTotal);

							tblROPTotal.setTotalLabel(0);
						}
					}
				}
				{

					pnlRefundAmt = new JPanel(new BorderLayout(5, 5)); 
					pnlPaymentDetails.add(pnlRefundAmt, BorderLayout.SOUTH);
					Font fontRefund = new Font(FncLookAndFeel.font_name, Font.BOLD, 20);
					{ 
						lblAmtRefund = new JLabel("*Amount for Refund", JLabel.TRAILING);
						pnlRefundAmt.add(lblAmtRefund);
						lblAmtRefund.setFont(fontRefund);
					}
					{
						txtAmtRefund = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlRefundAmt.add(txtAmtRefund, BorderLayout.EAST);
						txtAmtRefund.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAmtRefund.setPreferredSize(new Dimension(400, 0));
						txtAmtRefund.setText("0.00");
						txtAmtRefund.setFont(fontRefund);
						txtAmtRefund.addKeyListener(new KeyAdapter() {

							public void keyReleased(KeyEvent e){
								BigDecimal refund_amt = (BigDecimal) ((_JXFormattedTextField)e.getSource()).getValued();
								
								if(cmbRefundType.getSelectedItem() != null){
									if(refund_amt.doubleValue() > 0){
										System.out.printf("Display refund amt: %s%n", refund_amt);
										System.out.printf("Display Total Pmt Amt: %s%n", computeTotalPmt());
										
										if(computeTotalPmt().compareTo(refund_amt.setScale(2, BigDecimal.ROUND_HALF_EVEN)) == -1){
											JOptionPane.showMessageDialog(pnlCenter, "Amount for Refund cannot be larger than total payments");
											txtAmtRefund.setValue(new BigDecimal("0.00"));

										}
										String request_remarks = "";
										
									}
								}
							}
						});
					}
				}
			}
			/*{
				pnlSchedule = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("Schedule", pnlSchedule);
				{
					scrollSchedule = new JScrollPane();
					pnlSchedule.add(scrollSchedule, BorderLayout.CENTER);
					scrollSchedule.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelSchedule = new modelROPSchedule();
						tblSchedule = new _JTableMain(modelSchedule);
						scrollSchedule.setViewportView(tblSchedule);
						tblSchedule.hideColumns("Part ID");
						modelSchedule.addTableModelListener(new TableModelListener() {

							public void tableChanged(TableModelEvent arg0) {
								((DefaultListModel) rowHeaderSchedule.getModel()).addElement(modelSchedule.getRowCount());

								if(modelSchedule.getRowCount() == 0){
									rowHeaderSchedule.setModel(new DefaultListModel());
								}
							}
						});
					}
					{
						rowHeaderSchedule = tblSchedule.getRowHeader();
						rowHeaderSchedule.setModel(new DefaultListModel());
						scrollSchedule.setRowHeaderView(rowHeaderSchedule);
						scrollSchedule.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}*/
			{
				pnlROP_Allocation = new JPanel(new BorderLayout(3, 3));
				tabCenter.add("Refund Allocation", pnlROP_Allocation);
				{
					scrollROP_Allocation = new JScrollPane();
					pnlROP_Allocation.add(scrollROP_Allocation, BorderLayout.CENTER);
					scrollROP_Allocation.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					//scrollCreditPmt.setBorder(components.JTBorderFactory.createTitleBorder("Credit"));
					{
						modelROP_Allocation = new modelROP_Allocation();
						tblROP_Allocation = new _JTableMain(modelROP_Allocation);
						scrollROP_Allocation.setViewportView(tblROP_Allocation);
						tblROP_Allocation.hideColumns("Pay Part ID", "Part ID", "Apply to Ledger");
						
						tblROP_Allocation.addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								
							}
							
							@Override
							public void mousePressed(MouseEvent e) {
								
							}
							
							@Override
							public void mouseExited(MouseEvent e) {
								
							}
							
							@Override
							public void mouseEntered(MouseEvent e) {
								
							}
							
							@Override
							public void mouseClicked(MouseEvent e) {
								autoSetRemarks();
								
							}
						});

						tblROP_Allocation.addPropertyChangeListener(new PropertyChangeListener() {

							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								_JTableMain table = (_JTableMain) evt.getSource();
								String propertyName = evt.getPropertyName();

								if (!propertyName.equals("swingx.rollover")) {
									// System.out.printf("%n%n%s%n",
									// propertyName);
								}
								if (propertyName.equals("tableCellEditor")) {
									int column = table.convertColumnIndexToModel(table.getEditingColumn());

									if (column != -1 && modelROP_Allocation.getColumnClass(column).equals(BigDecimal.class)) {
										Object oldValue = null;
										try {
											oldValue = ((NumberEditorExt) evt.getOldValue()).getCellEditorValue();
										} catch (NullPointerException e) {
										}

										if (oldValue != null) {
											//System.out.printf("%n%n%s: (%s, %s) %s%n", propertyName, oldValue, newValue, oldValue.getClass().getSimpleName());
											
											  if (oldValue instanceof Double) { 
												  //table.setValueAt(new BigDecimal((Double) oldValue), table.getEditingRow(), table.getEditingColumn());
												  table.setValueAt(BigDecimal.valueOf(((Double) oldValue) ), table.getEditingRow(), table.getEditingColumn());
											  } 
											  if (oldValue instanceof Long) {
												  //table.setValueAt(new BigDecimal(((Long) oldValue)), table.getEditingRow(), table.getEditingColumn()); 
												  table.setValueAt(BigDecimal.valueOf(((Long) oldValue)), table.getEditingRow(), table.getEditingColumn());
											  }
											 
											
											/*
											 * if(oldValue instanceof BigDecimal) { table.setValueAt((BigDecimal)
											 * BigDecimal.valueOf((Long)oldValue), table.getEditingRow(),
											 * table.getEditingColumn()); }
											 */
										}
									}
									
									if(tblROP_Allocation.getSelectedRows().length > 0){
										autoSetRemarks();
										tblROP_Allocation.packAll();
									}
								}
							}
						});

						modelROP_Allocation.addTableModelListener(new TableModelListener() {

							public void tableChanged(TableModelEvent arg0) {
								if(arg0.getType() == TableModelEvent.INSERT){
									((DefaultListModel) rowHeaderROP_Allocation.getModel()).addElement(modelROP_Allocation.getRowCount());
								}

								if(modelROP_Allocation.getRowCount() == 0){
									rowHeaderROP_Allocation.setModel(new DefaultListModel());
								}
							}
						});
					}
				}
				{
					rowHeaderROP_Allocation = tblROP_Allocation.getRowHeader();
					rowHeaderROP_Allocation.setModel(new DefaultListModel());
					scrollROP_Allocation.setRowHeaderView(rowHeaderROP_Allocation);
					scrollROP_Allocation.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new GridLayout(1, 7));
			pnlSouth.setPreferredSize(new Dimension(300, 30));
			{
				btnNew = new JButton("New");
				pnlSouth.add(btnNew);
				btnNew.setActionCommand(btnNew.getText());
				btnNew.setMnemonic(KeyEvent.VK_N);
				btnNew.addActionListener(this);
				grpNewEdit.add(btnNew);
			}
			{
				btnEdit = new JButton("Edit");
				pnlSouth.add(btnEdit);
				btnEdit.setActionCommand(btnEdit.getText());
				btnEdit.setMnemonic(KeyEvent.VK_E);
				btnEdit.addActionListener(this);
				grpNewEdit.add(btnEdit);
			}
			{
				btnSave = new JButton("Save");
				pnlSouth.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.setMnemonic(KeyEvent.VK_S);
				btnSave.addActionListener(this);
			}
			{
				btnCancelReq = new JButton("Cancel Request");
				pnlSouth.add(btnCancelReq);
				btnCancelReq.setActionCommand(btnCancelReq.getText());
				btnCancelReq.setMnemonic(KeyEvent.VK_C);
				btnCancelReq.addActionListener(this);
			}
			{
				btnClear = new JButton("Clear");
				pnlSouth.add(btnClear);
				btnClear.setActionCommand(btnClear.getText());
				btnClear.setMnemonic(KeyEvent.VK_R);
				btnClear.addActionListener(this);
			}
			{
				btnSearch = new JButton("Search");
				pnlSouth.add(btnSearch);
				btnSearch.setActionCommand(btnSearch.getText());
				btnSearch.setMnemonic(KeyEvent.VK_A);
				btnSearch.addActionListener(this);
			}
			{
				btnPost = new JButton("Post");
				pnlSouth.add(btnPost);
				btnPost.setActionCommand(btnPost.getText());
				btnPost.setMnemonic(KeyEvent.VK_P);
				btnPost.addActionListener(this);
			}
			{
				btnPreview = new JButton("Preview");
				pnlSouth.add(btnPreview);
				btnPreview.setActionCommand(btnPreview.getText());
				btnPreview.setMnemonic(KeyEvent.VK_V);
				btnPreview.addActionListener(this);
			}
		}
		//SpringUtilities.makeCompactGrid(pnlMain, 3, 1, 3, 3, 3, 3, false);
		this.setComponentsEditable(pnlNorth, false);
		this.setComponentsEditable(pnlPaymentDetails, false);
		//this.btnState(true, false, false, false, true, false, false);
		this.btnState(true, false, false, false, false, true, false, false);
		this.pnlState(true, true, false);
		//loadComponents();
	}///XXX END OF INIT GUI

	public void btnState(Boolean sNew, Boolean sEdit ,Boolean sSave, Boolean sCancelReq, Boolean sClear, Boolean sSearch, Boolean sPost, Boolean sPreview){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancelReq.setEnabled(sCancelReq);
		btnClear.setEnabled(sClear);
		btnSearch.setEnabled(sSearch);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
	}
	
	public void pnlState(Boolean sRefund, Boolean sSchedule, Boolean sCredit){
		tabCenter.setEnabledAt(0, sRefund);
		tabCenter.setEnabledAt(1, sCredit);
		//tabCenter.setEnabledAt(2, sCredit);
	}

	public void loadComponents(){

		this.setComponentsEditable(pnlNorth, false);
		this.setComponentsEditable(pnlPaymentDetails, false);
		//this.btnState(sNew, sEdit, sSave, sCancelReq, sClear, sSearch, sPost, sPreview);
		//this.btnState(true, false, false, true, true, false, false);
		//tabCenter.setSelectedIndex(0);
	}

	private Boolean creditHasValue(){
		int count  =0;
		String particulars = "";

		for (int x = 0; x<modelROP_Allocation.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelROP_Allocation.getValueAt(x, 0);
			BigDecimal credit_amt = (BigDecimal) modelROP_Allocation.getValueAt(x, 4);

			if(isSelected && credit_amt.equals(new BigDecimal("0.00"))){
				particulars = particulars +"\n" + tblROP_Allocation.getValueAt(x, 2).toString();
				count++;
			}
		}

		if(count == 0){
			return true;
		}

		JOptionPane.showMessageDialog(null, "The ff. particulars selected must have value :\n"+particulars,"Save",JOptionPane.WARNING_MESSAGE);
		return false;
	}

	public void newROP(){
		
		cmbRefundType.setEnabled(true);
		cmbClientType.setEnabled(true);
		this.setComponentsEditable(pnlPaymentDetails, true);
		btnState(false, false, true, false, true, false, false, false);
		
		/*rbtnOtherPmt.setEnabled(true);
		rbtnMiscellaneous.setEnabled(true);
		rbtnHoldingPayments.setEnabled(true);*/
	}
	
	private void editROP(){
		
		txtAmtRefund.setEditable(true);
		txtRemarks.setEditable(true);
		txtRequestBy.setEditable(true);
		tblROP_Allocation.setEditable(true);
		btnState(false, false, true, false, true, false, false, false);
		
		if(cmbRefundType.getSelectedIndex() == 1){
			pnlState(true, true, true);
		}else{
			pnlState(true, true, false);
		}
		
		//modelROP_Allocation.s
	}

	public boolean toSave(){//CHECKS THE REQUIRED FIELDS BEFORE SAVING

		BigDecimal refund_amt = (BigDecimal) txtAmtRefund.getValued();

		if(lookupClient.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Cannot Save, Please select Client First"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		/*if(btngrpPayment.isSelected(null)){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Cannot Save, Please select refund payment from"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		
		if(cmbRefundType.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Cannot Save, Please select refund payment from"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if(txtAmtRefund.getText().equals("0.00")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Cannot Save, Amount for Refund Cannot be Zero"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		/*if(getRefundType().equals("A") || getRefundType().equals("B")){
			Double sum_ledger_amt = Double.valueOf(computeLedgerAmtPaid(lookupClient.getValue(), txtUnitID.getText(), txtProjID.getText(), txtSeqNo.getText()).trim().replace(",", ""));
		}
		Double amount_refund = Double.valueOf(txtAmtRefund.getText().trim().replace(",","")).doubleValue();*/
		
		if(creditHasValue() == false){
			return false;
		}

		/*
		 * if(totalRefundAllocation().compareTo(refund_amt) == 1){
		 * JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
		 * "Total Refund Allocation Cannot be larger than Refund Amount", "Save",
		 * JOptionPane.WARNING_MESSAGE); return false; }
		 */

		if(tblRefundPayment.getRowCount() == 0){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Amount for refund cannot be greater than amount paid \nAmount Paid: %s", 
					computeLedgerAmtPaid(lookupClient.getValue(), txtUnitID.getText(), txtProjID.getText(), txtSeqNo.getText())), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	public String getRequestNo(){
		pgSelect db = new pgSelect();

		String sql = "select trim(get_new_request_no())";

		db.select(sql);

		return (String) db.getResult()[0][0];
	}

	private BigDecimal computeTotalPmt(){
		BigDecimal total_amt = new BigDecimal("0.00");

		for(int x = 0; x<modelRefPymnt.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelRefPymnt.getValueAt(x, 0);
			BigDecimal amount = (BigDecimal) modelRefPymnt.getValueAt(x, 4);
			
			if(isSelected){
				total_amt = total_amt.add(amount);
			}
		}
		modelROPTotal.setValueAt(total_amt, 0, 3);
		
		return total_amt;
	}

	private void AutoApplyCredit(String entity_id, String unit_id, String proj_id, String seq_no ,String pmt_scheme_id){//Auto Apply the Credit Amount
		BigDecimal refund_amt = (BigDecimal) txtAmtRefund.getValued();
		BigDecimal remaint_amt = computeTotalPmt().subtract(refund_amt);

		pgSelect db = new pgSelect();

		if(remaint_amt.doubleValue() != 0){

			String sql = "SELECT a.part_id, \n"+
					"COALESCE(SUM(amount) - (SELECT COALESCE(SUM(amount), 0.00) \n" + 
					"				 		  FROM rf_client_ledger \n" + 
					"				 		  WHERE entity_id = a.entity_id\n" + 
					"				 		  AND proj_id = a.proj_id\n" + 
					"				 		  AND pbl_id = a.pbl_id\n" + 
					"				 		  AND seq_no = a.seq_no\n" + 
					"				 		  AND part_id = a.part_id \n" + 
					"				 		  AND status_id = 'A'), 0.00)\n" + 
					"FROM rf_client_schedule a\n" + 
					"WHERE a.entity_id = '"+entity_id+"' \n" + 
					"AND a.proj_id = '"+proj_id+"' \n" + 
					"AND a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n" + 
					"AND a.seq_no = "+seq_no+" \n" + 
					"AND a.status_id = 'A' \n" + 
					"GROUP BY a.part_id, a.entity_id, a.proj_id, a.pbl_id, a.seq_no\n" + 
					"ORDER BY a.part_id";

			db.select(sql);

			FncSystem.out("Display Credit Payments", sql);

			if(db.isNotNull()){
				for(int x= 0; x<modelROP_Allocation.getRowCount(); x++){

					String part_id = (String) db.getResult()[x][0];
					System.out.printf("Display part_id from sql:%s%n", part_id);

					if(part_id != null){
						BigDecimal amount = (BigDecimal) db.getResult()[x][1];

						mapParticulars.put(part_id, amount);
					}
				}
			}

			for(int x= 0; x < modelROP_Allocation.getRowCount(); x++){
				String part_id = (String) modelROP_Allocation.getValueAt(x, 1);
				System.out.printf("Display part_id: %s%n", part_id);

				BigDecimal deduction = mapParticulars.get(part_id);

				if(remaint_amt.compareTo(deduction) > 0){
					modelROP_Allocation.setValueAt(deduction, x, 3);
					remaint_amt = remaint_amt.subtract(deduction);
				}else{
					modelROP_Allocation.setValueAt(remaint_amt, x, 3);
					remaint_amt = remaint_amt.subtract(remaint_amt);
				}
			}
			
			tblROP_Allocation.packAll();
		}
	}
	
	public String getNextRPLFno() {

		String rplf_no = "";

		String SQL ="select trim(to_char(max(rplf_no::int +1), '000000000')) from rf_request_header; \n";

		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Display next rplf_no", SQL);

		if(db.isNotNull()){
			rplf_no = (String) db.getResult()[0][0];
		}else{
			rplf_no = null;
		}
		return rplf_no;
	}

	public String computeLedgerAmtPaid(String entity_id, String unit_id, String proj_id, String seq_no){
		String sql = "select cast(coalesce(sum(amount), 0.00) as char(20)) \n"+
				"from rf_client_ledger \n"+
				"where entity_id = '"+entity_id+"' \n"+
				"and pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
				"and proj_id = '"+proj_id+"' \n"+
				"and seq_no = "+seq_no+" \n";
		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display Compute Ledger Amount Paid", sql);
		return (String) db.getResult()[0][0];
	}

	public void clearROP(){
		btnState(true, false, false, false, false, true, false, false);
		pnlState(true, true, false);
		
		grpNewEdit.clearSelection();
		cmbRefundType.setSelectedItem(null);
		cmbRefundType.setEnabled(false);
		cmbClientType.setSelectedItem(null);
		cmbClientType.setEnabled(false);
		lookupClient.setValue(null);
		lookupClient.setEditable(false);
		txtClientName.setText("");
		txtProjID.setText("");
		txtProject.setText("");
		txtUnitID.setText("");
		txtUnitDesc.setText("");
		txtUnitStat.setText("");
		txtRequestNo.setText("");
		txtRequestStat.setText("");
		txtSeqNo.setText("");
		requestDate.setDate(null);
		txtRemarks.setText("");

		txtAmtRefund.setText("0.00");
		txtAmtRefund.setEditable(false);
		tabCenter.setSelectedIndex(0);
		try{

			tblROP_Allocation.setEditable(true);
			modelROP_Allocation.clear();
			scrollROP_Allocation.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			tblRefundPayment.hideColumns("RPLF No", "Approved By", "Approved Date");
			/*modelSchedule.clear();
			scrollSchedule.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));*/

			scrollRefundPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderRefundPayment = tblRefundPayment.getRowHeader();
			scrollRefundPayment.setRowHeaderView(rowHeaderRefundPayment);
			
			modelRefPymnt.clear();
			System.out.println("Dumaan dito sa Clear ng Refund of Payment");
			
			scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			modelROPTotal.setValueAt(new BigDecimal("0.00"), 0, 3);

		}catch (NullPointerException e) {}
	}
	
	private String getRefundType(){ 
		String ref_type = "";
		
		if(cmbRefundType.getSelectedIndex() == 0){
			ref_type = "A";
		}
		if(cmbRefundType.getSelectedIndex() == 1){
			ref_type = "B";
		}
		if(cmbRefundType.getSelectedIndex() == 2){
			ref_type = "C";
		}
		
		if(cmbRefundType.getSelectedIndex() == 3) {
			ref_type = "D";
		}

		return ref_type;
	}
	
	private void displaySchedule(String entity_id, String proj_id, String unit_id, String seq_no, String unit_status){
		modelSchedule.clear();

		pgSelect db = new pgSelect();

		String sql = "";

		if(unit_status.equals("Cancelled")){
			sql = "select trim(part_id), trim(get_particular(part_id)), \n"+
					"scheddate, amount, proc_fee, coalesce(mri, 0.00), fire, \n"+
					"interest, principal, coalesce(vat, 0.00), balance, \n"+
					"coalesce(interest_rate, 0.00) \n"+
					"from rf_req_schedule_old \n"+
					"where entity_id = '"+entity_id+"' \n"+
					"and unit_id = '"+unit_id+"' \n"+
					"and proj_id = '"+proj_id+"' \n"+
					"and seq_no = "+seq_no+" \n"+
					"and request_no ~*'Cancelled' \n"+
					"order by scheddate";

		}else{
			sql = "select trim(part_id), trim(get_particular(part_id)), \n" + 
					"scheddate, amount, proc_fee, coalesce(mri, 0.00), fire, \n" + 
					"interest, principal, coalesce(vat, 0.00), balance, \n" + 
					"coalesce(interest_rate, 0.00)\n" + 
					"from rf_client_schedule \n" + 
					"where entity_id = '"+entity_id+"' \n"+
					"and unit_id = '"+unit_id+"' \n"+
					"and proj_id = '"+proj_id+"' \n"+
					"and seq_no = "+seq_no+" \n"+
					"order by scheddate";

		}

		db.select(sql);
		FncSystem.out("Display Schedule", sql);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelSchedule.addRow(db.getResult()[x]);
			}
			scrollSchedule.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule.getRowCount())));
			tblSchedule.packAll();
		}
	}

	private BigDecimal totalRefundAllocation(){
		BigDecimal refund_allocation = new BigDecimal("0.00");

		for(int x= 0; x<modelROP_Allocation.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelROP_Allocation.getValueAt(x, 0);

			if(isSelected){
				BigDecimal allocate_particular = ((BigDecimal) modelROP_Allocation.getValueAt(x, 4))/*.setScale(2, RoundingMode.DOWN)*/;
				refund_allocation = refund_allocation.add(allocate_particular);
			}
		}
		return refund_allocation;
	}
	
	private void autoSetRemarks(){
		
		String request_remarks = "";
		
		BigDecimal refund_amt = (BigDecimal) txtAmtRefund.getValued();
		BigDecimal refund_allocation = new BigDecimal("0.00");
		
		if(tblROP_Allocation.getSelectedRows().length > 0){
			
			for(int x = 0; x< modelROP_Allocation.getRowCount(); x++){
				int selected_row = tblROP_Allocation.convertRowIndexToModel(x);
				
				Boolean isSelected = (Boolean) modelROP_Allocation.getValueAt(selected_row, 0);
				
				if(isSelected){
					String part_alias = (String) modelROP_Allocation.getValueAt(selected_row, 2);
					
					BigDecimal amount = (BigDecimal) (modelROP_Allocation.getValueAt(selected_row, 4))/*.setScale(2, RoundingMode.UP)*/;
					
					refund_allocation = refund_allocation.add(amount);
					
					System.out.printf("Display selected_row: %s%n", selected_row);
					System.out.printf("Display part alias: %s%n", part_alias);
					System.out.printf("Display amount: %s%n", amount);
					
					if(request_remarks.equals("")){
						request_remarks = "Will allocate "+amount+" to "+part_alias+"";
					}else{
						request_remarks = ""+request_remarks+" and "+amount+" to "+part_alias+"";
					}
					//System.out.printf("Display Request Remarks sa Selected Row: %s-%s%n", selected_row, request_remarks);
				}
			}
		}
		
		refund_amt = refund_amt.subtract(refund_allocation);
		
		//System.out.printf("Display total refund allocation: %s%n", refund_allocation);
		
		if(refund_amt.equals(new BigDecimal("0.00"))){
			txtRemarks.setText(request_remarks);
		}else{
			txtRemarks.setText(""+request_remarks+" and "+refund_amt+"");
		}
	}
	
	private void previewROP(String request_no){
		ArrayList<TD_RefundofPayment_PaymentsMade> listPaymentsMade = new ArrayList<TD_RefundofPayment_PaymentsMade>();
		
		for(int x = 0; x<modelRefPymnt.getRowCount(); x++){
			Date actual_date = (Date) modelRefPymnt.getValueAt(x, 1);
			String particulars = (String) modelRefPymnt.getValueAt(x, 3);
			BigDecimal amount = (BigDecimal) modelRefPymnt.getValueAt(x, 4);
			String receipt_no = (String) modelRefPymnt.getValueAt(x, 6);
			
			listPaymentsMade.add(new TD_RefundofPayment_PaymentsMade(actual_date, particulars, amount, receipt_no));
		}
		
		ArrayList<TD_ROP_RefundAllocation> listRefundAllocation = new ArrayList<TD_ROP_RefundAllocation>();
		
		for(int x = 0; x<modelROP_Allocation.getRowCount(); x++){
			String part_desc = (String) modelROP_Allocation.getValueAt(x, 3);
			BigDecimal amount = (BigDecimal) modelROP_Allocation.getValueAt(x, 4);
			
			listRefundAllocation.add(new TD_ROP_RefundAllocation(part_desc, amount));
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("client_code", lookupClient.getValue());
		mapParameters.put("client_name", txtClientName.getText());
		mapParameters.put("project", txtProject.getText());
		mapParameters.put("description", txtUnitDesc.getText());
		mapParameters.put("request_no", request_no);
		mapParameters.put("request_date", requestDate.getTimestamp() == null ? FncGlobal.getDateToday():requestDate.getTimestamp());
		mapParameters.put("requested_by", "");
		//mapParameters.put("rplf_no", txtRemarks.getText());
		mapParameters.put("remarks", txtRemarks.getText());
		mapParameters.put("refund_type", getRefundType());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", txtSeqNo.getText().equals("") ? null:Integer.parseInt(txtSeqNo.getText()));
		mapParameters.put("refund_amount", txtAmtRefund.getValued());
		mapParameters.put("deduction", totalRefundAllocation());
		mapParameters.put("prepared_by", ""+UserInfo.FullName+"");
		mapParameters.put("listPaymentMade", listPaymentsMade);
		mapParameters.put("listRefundAllocation", listRefundAllocation);
		mapParameters.put("connection", FncGlobal.connection);
		
		/*mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptRefundofPayment.jasper"));
		mapParameters.put("SUBREPORT_TotalExpenses", this.getClass().getResourceAsStream("/Reports/subrptRefundofPayment_TotalExpenses.jasper"));*/
		System.out.println("Dumaan dito sa Before Generation");
		FncReport.generateReport("/Reports/rptRefundofPayment.jasper", "Refund of Payment", mapParameters);
		System.out.println("Dumaan dito sa End of Preview sa Report");
	}
	
	private void previewROP2(){
		ArrayList<TD_RefundofPayment_PaymentsMade> listPaymentsMade = new ArrayList<TD_RefundofPayment_PaymentsMade>();
		
		for(int x = 0; x<modelRefPymnt.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelRefPymnt.getValueAt(x, 0);
			Date actual_date = (Date) modelRefPymnt.getValueAt(x, 1);
			String particulars = (String) modelRefPymnt.getValueAt(x, 3);
			BigDecimal amount = (BigDecimal) modelRefPymnt.getValueAt(x, 4);
			String receipt_no = (String) modelRefPymnt.getValueAt(x, 6);
			
			if(isSelected){
				listPaymentsMade.add(new TD_RefundofPayment_PaymentsMade(actual_date, particulars, amount, receipt_no));
			}
		}
		
		ArrayList<TD_ROP_RefundAllocation> listRefundAllocation = new ArrayList<TD_ROP_RefundAllocation>();
		
		for(int x = 0; x<modelROP_Allocation.getRowCount(); x++){
			String part_desc = (String) modelROP_Allocation.getValueAt(x, 3);
			BigDecimal amount = (BigDecimal) modelROP_Allocation.getValueAt(x, 4);
			
			listRefundAllocation.add(new TD_ROP_RefundAllocation(part_desc, amount));
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("client_code", lookupClient.getValue());
		mapParameters.put("client_name", txtClientName.getText());
		mapParameters.put("project", txtProject.getText());
		mapParameters.put("description", txtUnitDesc.getText());
		mapParameters.put("request_no", txtRequestNo.getText());
		mapParameters.put("request_date", requestDate.getDate());
		mapParameters.put("requested_by", txtRequestBy.getText());
		//mapParameters.put("rplf_no", txtRemarks.getText());
		mapParameters.put("remarks", txtRemarks.getText());
		mapParameters.put("refund_type", getRefundType());
		mapParameters.put("unit_id", txtUnitID.getText());
		mapParameters.put("seq_no", txtSeqNo.getText().equals("") ? null:Integer.parseInt(txtSeqNo.getText()));
		mapParameters.put("refund_amount", txtAmtRefund.getValued());
		mapParameters.put("deduction", totalRefundAllocation());
		mapParameters.put("prepared_by", ""+UserInfo.FullName+"");
		mapParameters.put("listPaymentMade", listPaymentsMade);
		mapParameters.put("listRefundAllocation", listRefundAllocation);
		mapParameters.put("connection", FncGlobal.connection);
		mapParameters.put("sum_pmts_made", computeTotalPmt());
		
		/*mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptRefundofPayment.jasper"));
		mapParameters.put("SUBREPORT_TotalExpenses", this.getClass().getResourceAsStream("/Reports/subrptRefundofPayment_TotalExpenses.jasper"));*/
		FncReport.generateReport("/Reports/rptRefundofPayment.jasper", "Refund of Payment", mapParameters);
	}
	
	/*private void{
		
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("New")){
			grpNewEdit.setSelectedButton(e);
			newROP();
		}
		
		if(actionCommand.equals("Edit")){
			grpNewEdit.setSelectedButton(e);
			editROP();
		}

		if(actionCommand.equals("Cancel Request")){//TRY sp_cancel_request here
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to cancel this request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				_OtherRequest.cancelRequest(txtRequestNo.getText());
				clearROP();
				loadComponents();
			}
		}

		if(actionCommand.equals("Save")){
			if(toSave()){
				String req_no = "";
				
				if(txtRequestNo.getText().isEmpty() || txtRequestNo.getText().equals("")){
					req_no = getRequestNo();
				}else{
					req_no = txtRequestNo.getText();
				}
				BigDecimal alloc = new BigDecimal("0.00");
				String msg = "";
				System.out.println("totalRefundAllocation().compareTo(alloc): "+totalRefundAllocation().compareTo(alloc));
				if (totalRefundAllocation().compareTo(alloc) == 1) {
					msg = "Are you sure to save request?";
				} else {
					msg = "You have not allocated any amount for this refund are you sure to save request?";
					
				}
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), msg, actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					if(cmbRefundType.getSelectedIndex() == 0){
						
						_RefundofPayment.saveROP_Header(req_no, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtClientName.getText(), (BigDecimal) txtAmtRefund.getValued(), totalRefundAllocation() ,getRefundType(), txtRemarks.getText().replace("'", "''"), txtRequestBy.getText());
						if(_RefundofPayment.isRequestExisting(req_no) == false){
							_RefundofPayment.saveRtReqHeader(req_no, txtClientName.getText(), lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtRemarks.getText().replace("'", "''"));
						}
						_RefundofPayment.saveROP_Detail(modelRefPymnt, req_no);
						_RefundofPayment.saveCreditAmt(req_no, modelROP_Allocation);
						previewROP(req_no);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Request has been Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						clearROP();
						loadComponents();
					}

					if(cmbRefundType.getSelectedIndex() == 1){
						_RefundofPayment.saveROP_Header(req_no, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtClientName.getText(), (BigDecimal) txtAmtRefund.getValued(), totalRefundAllocation() ,getRefundType(), txtRemarks.getText().replace("'", "''"), txtRequestBy.getText());
						_RefundofPayment.saveROP_Detail(modelRefPymnt, req_no);
						if(_RefundofPayment.isRequestExisting(req_no) == false){
							_RefundofPayment.saveRtReqHeader(req_no, txtClientName.getText(), lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtRemarks.getText().replace("'", "''"));
						}
						_RefundofPayment.saveCreditAmt(req_no, modelROP_Allocation);	
						previewROP(req_no);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Request has been Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						clearROP();
						loadComponents();
					}

					if(cmbRefundType.getSelectedIndex() == 2){
						
						_RefundofPayment.saveROP_Header(req_no, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtClientName.getText(), (BigDecimal) txtAmtRefund.getValued(), totalRefundAllocation() ,getRefundType(), txtRemarks.getText().replace("'", "''"), txtRequestBy.getText());
						_RefundofPayment.saveROP_Detail(modelRefPymnt, req_no);
						if(_RefundofPayment.isRequestExisting(req_no) == false){
							_RefundofPayment.saveRtReqHeader(req_no, txtClientName.getText(), lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtRemarks.getText().replace("'", "''"));
						}
						previewROP(req_no);
						
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Request has been Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						clearROP();
						loadComponents();
					}
					
					if(cmbRefundType.getSelectedIndex() == 3) {
						_RefundofPayment.saveROP_Header(req_no, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtClientName.getText(), (BigDecimal) txtAmtRefund.getValued(), totalRefundAllocation() ,getRefundType(), txtRemarks.getText().replace("'", "''"), txtRequestBy.getText());
						_RefundofPayment.saveROP_Detail(modelRefPymnt, req_no);
						if(_RefundofPayment.isRequestExisting(req_no) == false){
							_RefundofPayment.saveRtReqHeader(req_no, txtClientName.getText(), lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), txtRemarks.getText().replace("'", "''"));
						}
						previewROP(req_no);
						
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Request has been Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						clearROP();
						loadComponents();
					}
				}
			}
		}

		if(actionCommand.equals("Clear")){
			clearROP();
			grpNewEdit.clearSelection();
		}

		if(actionCommand.equals("Search")){

			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Request", _RefundofPayment.sqlRequest(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			FncSystem.out("Display sql Search", _RefundofPayment.sqlRequest());

			if(data != null){
				
				String req_no = ((String) data[0]); 
				String rplf_no = (String) data[1];
				String entity_id = ((String) data[1]);
				String client_name = ((String) data[2]);
				String unit_id = (String) data[3];
				Integer seq_no = (Integer) data[4];
				String proj_id = (String) data[5];
				String proj_name = (String) data[6];
				String unit_desc = (String) data[7];
				String req_status = (String) data[8];
				Date req_date = (Date) data[9];
				Date date_needed = (Date) data[10];
				String unit_status = (String) data[11];
				String refund_type = (String) data[12];
				BigDecimal amt_refund = (BigDecimal) data[13];
				String remarks = (String) data[14];
				String request_by = (String) data[15];

				txtRequestNo.setText(req_no);
				txtRemarks.setText(remarks);
				lookupClient.setValue(entity_id);
				txtClientName.setText(client_name);
				txtUnitID.setText(unit_id);
				txtUnitDesc.setText(unit_desc);
				txtProjID.setText(proj_id);
				txtProject.setText(proj_name);
				txtSeqNo.setText(seq_no == null ? "" : seq_no.toString());
				txtUnitStat.setText(unit_status);
				txtRequestStat.setText(req_status);
				requestDate.setDate(req_date);
				txtAmtRefund.setValue(amt_refund);
				txtRequestBy.setText(request_by);
				txtAmtRefund.setEditable(false);
				
				if(refund_type.equals("A")){
					cmbRefundType.setSelectedIndex(0);
					_RefundofPayment.displayLedgerPayment(modelRefPymnt, rowHeaderRefundPayment, entity_id, proj_id ,unit_id, seq_no.toString(), req_no, true);
					tblRefundPayment.packAll();
					scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
					computeTotalPmt();
					pnlState(true, true, true);
				}

				if(refund_type.equals("B")){
					cmbRefundType.setSelectedIndex(1);
					_RefundofPayment.displayOtherPayment(modelRefPymnt, rowHeaderRefundPayment, entity_id, proj_id ,unit_id, seq_no.toString(), req_no, true);
					tblRefundPayment.hideColumns("RPLF No", "Released By", "Released Date", "Received By", "Received Date");
					tblRefundPayment.packAll();
					scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
					computeTotalPmt();
					
					pnlState(true, true, true);
				}

				if(refund_type.equals("C")){
					
					cmbRefundType.setSelectedIndex(2);
					//_RefundofPayment.displayHoldingPayments(modelRefPymnt, rowHeaderRefundPayment, entity_id);
					_RefundofPayment.displayHoldingRefundDetails(modelRefPymnt, rowHeaderRefundPayment, req_no);
					tblRefundPayment.packAll();
					scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
					computeTotalPmt();
				}
				
				if(refund_type.equals("D")) {
					cmbRefundType.setSelectedIndex(3);
					System.out.println("Before Display Method");
					_RefundofPayment.displaySubdivision_Amenities(modelRefPymnt, rowHeaderRefundPayment, req_no);
					//try {
						//_RefundofPayment.displaySubdivisionPmts(modelRefPymnt, rowHeaderRefundPayment, entity_id, proj_id, unit_id, seq_no.toString(),req_no, true);
					/*} catch (NullPointerException e2) {
						// TODO: handle exception
					}*/
					
					tblRefundPayment.packAll();
					scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
					computeTotalPmt();
				}
				
				lookupClient.setEditable(false);
				//Display Schedule
				if(refund_type.equals("C") == false){
					//displaySchedule(entity_id, proj_id, unit_id, seq_no.toString(), unit_status);
				}
				
				//Display Refund Allocation
				if(refund_type.equals("C") == false){
					_RefundofPayment.displayAllocatedRefund(req_no, modelROP_Allocation);
					scrollROP_Allocation.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblROP_Allocation.getRowCount())));
					tblROP_Allocation.packAll();
					tblROP_Allocation.setEditable(false);
				}

				if(req_status.equals("ACTIVE")){
					//btnState(false, false, true, true, false, true, true);
					btnState(false, true, false, true, true, false, true, true);
				}else if(req_status.equals("POSTED")){
					if (cmbRefundType.getSelectedIndex() != 1) {
						tblRefundPayment.showColumns("RPLF No", "Approved By", "Approved Date");
					}
					btnState(false, false, false, false, true, false, false, true);
				}else{
					btnState(false, false, false, false, true, false, false, true);
					//btnState(false, false, false, true, false, false, true);
				}
			}
		}

		if(actionCommand.equals("Post")){//CHECK REQUEST NO HERE
			BigDecimal refund_amt = (BigDecimal) txtAmtRefund.getValued();

			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to post this request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(getContentPane(), "Please input password to post request", "Post", JOptionPane.INFORMATION_MESSAGE);

				dlg_CR_PW_Entry pw = new dlg_CR_PW_Entry(FncGlobal.homeMDI, "Password");
				pw.setLocationRelativeTo(null);
				pw.setVisible(true);

				String pw_entered = pw.getPassword();

				if(pw_entered.equals(FncGlobal.connectionPassword)){


					_RefundofPayment.postRefundOfPaymnent(txtRequestNo.getText());
					previewROP2();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Request Posted", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					txtAmtRefund.setEditable(false);

					if(cmbRefundType.getSelectedIndex() == 0){
						System.out.println("Before display Ledger sa Post");
						System.out.printf("Display value of Entity ID: %s%n", entity_id);
						System.out.printf("Display value of Proj. ID: %s%n", proj_id);
						System.out.printf("Display value of PBL ID: %s%n", unit_id);
						System.out.printf("Display value of Seq No: %s%n",seq_no);
						if(modelRefPymnt == null){
							System.out.println("Model is Null");
						}
						if(rowHeaderRefundPayment == null){

						}
						_RefundofPayment.displayLedgerPayment(modelRefPymnt, rowHeaderRefundPayment, lookupClient.getValue(), txtProjID.getText() ,txtUnitID.getText(), txtSeqNo.getText(), "", true);
						System.out.println("After Displat Ledger Sa Post");
						tblRefundPayment.packAll();
						scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
						computeTotalPmt();
					}

					if(cmbRefundType.getSelectedIndex() == 1){
						cmbRefundType.setSelectedIndex(1);
						_RefundofPayment.displayOtherPayment(modelRefPymnt, rowHeaderRefundPayment, lookupClient.getValue(), txtProjID.getText() ,txtUnitID.getText(), txtSeqNo.getText().toString(), "" ,true);
						tblRefundPayment.packAll();
						scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
						computeTotalPmt();

						pnlState(true, true, true);
					}

					if(cmbRefundType.getSelectedIndex() == 2){

						cmbRefundType.setSelectedIndex(2);
						//_RefundofPayment.displayHoldingPayments(modelRefPymnt, rowHeaderRefundPayment, entity_id);
						_RefundofPayment.displayHoldingRefundDetails(modelRefPymnt, rowHeaderRefundPayment, txtRequestNo.getText());
						tblRefundPayment.packAll();
						scrollROPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundPayment.getRowCount())));
						computeTotalPmt();
					}

					lookupClient.setEditable(false);

					//Display Refund Allocation
					_RefundofPayment.displayAllocatedRefund(txtRequestNo.getText(), modelROP_Allocation);
					scrollROP_Allocation.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblROP_Allocation.getRowCount())));
					tblROP_Allocation.packAll();
					tblROP_Allocation.setEditable(false);

					tblRefundPayment.showColumns("RPLF No", "Approved By", "Approved Date");
					btnState(false, false, false, false, true, false, false, true);

					//clearROP();
					loadComponents();
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "Invalid password", "Post", JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		if(actionCommand.equals("Preview")){//PREVIEW OF THE REPORT FOR THE REFUND OF PAYMENT

			ArrayList<TD_RefundofPayment_PaymentsMade> listPaymentsMade = new ArrayList<TD_RefundofPayment_PaymentsMade>();
			
			for(int x = 0; x<modelRefPymnt.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelRefPymnt.getValueAt(x, 0);
				Date actual_date = (Date) modelRefPymnt.getValueAt(x, 1);
				String particulars = (String) modelRefPymnt.getValueAt(x, 3);
				BigDecimal amount = (BigDecimal) modelRefPymnt.getValueAt(x, 4);
				String receipt_no = (String) modelRefPymnt.getValueAt(x, 6);
				
				if(isSelected){
					listPaymentsMade.add(new TD_RefundofPayment_PaymentsMade(actual_date, particulars, amount, receipt_no));
				}
			}
			
			ArrayList<TD_ROP_RefundAllocation> listRefundAllocation = new ArrayList<TD_ROP_RefundAllocation>();
			
			for(int x = 0; x<modelROP_Allocation.getRowCount(); x++){
				String part_desc = (String) modelROP_Allocation.getValueAt(x, 3);
				BigDecimal amount = (BigDecimal) modelROP_Allocation.getValueAt(x, 4);
				
				listRefundAllocation.add(new TD_ROP_RefundAllocation(part_desc, amount));
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("client_code", lookupClient.getValue());
			mapParameters.put("client_name", txtClientName.getText());
			mapParameters.put("project", txtProject.getText());
			mapParameters.put("description", txtUnitDesc.getText());
			mapParameters.put("request_no", txtRequestNo.getText());
			mapParameters.put("request_date", requestDate.getDate());
			mapParameters.put("requested_by", txtRequestBy.getText());
			//mapParameters.put("rplf_no", txtRemarks.getText());
			mapParameters.put("remarks", txtRemarks.getText());
			mapParameters.put("refund_type", getRefundType());
			mapParameters.put("unit_id", txtUnitID.getText());
			mapParameters.put("seq_no", txtSeqNo.getText().equals("") ? null:Integer.parseInt(txtSeqNo.getText()));
			mapParameters.put("refund_amount", txtAmtRefund.getValued());
			mapParameters.put("deduction", totalRefundAllocation());
			mapParameters.put("prepared_by", ""+UserInfo.FullName+"");
			mapParameters.put("listPaymentMade", listPaymentsMade);
			mapParameters.put("listRefundAllocation", listRefundAllocation);
			mapParameters.put("connection", FncGlobal.connection);
			mapParameters.put("sum_pmts_made", computeTotalPmt());
			
			/*mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptRefundofPayment.jasper"));
			mapParameters.put("SUBREPORT_TotalExpenses", this.getClass().getResourceAsStream("/Reports/subrptRefundofPayment_TotalExpenses.jasper"));*/
			FncReport.generateReport("/Reports/rptRefundofPayment.jasper", "Refund of Payment", mapParameters);
		}
	}
}
