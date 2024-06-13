
package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import tablemodel.modelCreditPmtRequest;
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_CreditPayment extends JDialog implements _GUI, ActionListener{

	private static final long serialVersionUID = 4838884898512468107L;
	private Dimension size = new Dimension(600, 600);
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;

	private JPanel pnlClientDetails;
	private JLabel lblOldClient;
	private JLabel lblOldUnit;
	private JLabel lblOldProj;
	
	private JLabel lblNewCLient;
	private JLabel lblNewUnit;
	private JLabel lblNewProj;
	private _JLookup lookupRequest;

	private JPanel pnlOldData;
	private JPanel pnlOldDataLabel;
	private JPanel pnlOldDataCenter;
	private JPanel pnlOldClient;
	private _JXTextField txtOldClientID;
	private _JXTextField txtOldClient;
	
	private JPanel pnlOldUnit;
	private JPanel pnlOldUnitCenter;
	private _JXTextField txtOldUnitID;
	private _JXTextField txtOldUnitDesc;
	private JPanel pnlOldUnitEast;
	private JLabel lblOldSeq;
	private _JXTextField txtOldSeqNo;
	
	private JPanel pnlOldProject;
	private _JXTextField txtOldProjID;
	private _JXTextField txtOldProject;
	
	private JPanel pnlNewData;
	private JPanel pnlNewDataLabel;
	private JPanel pnlNewDataCenter;
	
	private JPanel pnlNewClient;
	private _JXTextField txtNewClientID;
	private _JXTextField txtNewClient;
	
	private JPanel pnlNewUnit;
	private JPanel pnlNewUnitCenter;
	private _JXTextField txtNewUnitID;
	private _JXTextField txtNewUnitDesc;
	private JPanel pnlNewUnitEast;

	private JPanel pnlNewProject;
	private _JXTextField txtNewProjID;
	private _JXTextField txtNewProject;
	private JLabel lblNewSeq;
	private _JXTextField txtNewSeq;

	private _JXFormattedTextField txtTotDPCO;
	private _JXFormattedTextField txtTotalMAAmt;
	private JPanel pnlPmtDetails;
	private JPanel pnlPDNorth;
	private JPanel pnlPDNorthWest;
	private JLabel lblTotalPmtAmt;
	private JLabel lblAmtForCredit;
	private JPanel pnlPDNorthCenter;
	private _JXFormattedTextField txtTotalPmtAmt;
	private _JXFormattedTextField txtAmtForCredit;
	private _JXFormattedTextField txtCreditAmt;
	private JScrollPane scrollCreditPmt;
	private JList rowHeaderCreditPmt;
	private _JTableMain tblCreditPmt;
	private modelCreditPmtRequest modelCrdtPmt;
	
	private JPanel pnlPDCenter;
	private _JXFormattedTextField txtReservation;
	private _JXFormattedTextField txtDPCashLayout;
	private _JXFormattedTextField txtMA;
	private _JXFormattedTextField txtLumpSumDPCO;
	private _JXFormattedTextField txtLumpSumMA;
	private _JXFormattedTextField txtTotal;
	private JPanel pnlSouth;
	private JButton btnSave;
	private JButton btnCancel;
	
	private String request_no;
	
	private Map<String, BigDecimal> mapParticulars = new HashMap<String, BigDecimal>();

	public pnlOtherReq_CreditPayment() {
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Frame owner) {
		super(owner);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Dialog owner) {
		super(owner);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Window owner) {
		super(owner);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Frame owner, String title) {
		super(owner, title);
		initGUI();
		/*setCreditdetails(req_no);
		displayCreditDocuments();*/
	}

	public pnlOtherReq_CreditPayment(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Window owner, String title, String req_no) {
		super(owner, title);
		initGUI();
		/*setCreditdetails(req_no);
		displayCreditDocuments();*/
	}

	public pnlOtherReq_CreditPayment(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public pnlOtherReq_CreditPayment(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	/*public pnlOtherReq_CreditPayment(String title) { 
		//super(title);
		initGUI();
	}*/

	/*public pnlOtherReq_CreditPayment(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}*/

	@Override
	public void initGUI() {
		/*this.setLayout(new GridLayout(3, 1, 5, 5));*/
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(EMPTY_BORDER);
			{
				pnlClientDetails = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlMain.add(pnlClientDetails, BorderLayout.NORTH);
				{
					pnlOldData = new JPanel(new BorderLayout(5, 5));
					pnlClientDetails.add(pnlOldData);
					pnlOldData.setBorder(JTBorderFactory.createTitleBorder("Old Details"));
					{
						pnlOldDataLabel = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlOldData.add(pnlOldDataLabel, BorderLayout.WEST);
						{
							lblOldClient = new JLabel("Old Client");
							pnlOldDataLabel.add(lblOldClient);
						}
						{
							lblOldProj = new JLabel("Old Proj");
							pnlOldDataLabel.add(lblOldProj);
						}
						{
							lblOldUnit = new JLabel("Old Unit ID");
							pnlOldDataLabel.add(lblOldUnit);
						}
					}
					{
						pnlOldDataCenter = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlOldData.add(pnlOldDataCenter, BorderLayout.CENTER);
						{
							pnlOldClient = new JPanel(new BorderLayout(3, 3));
							pnlOldDataCenter.add(pnlOldClient);
							{
								txtOldClientID = new _JXTextField();
								pnlOldClient.add(txtOldClientID, BorderLayout.WEST);
								txtOldClientID.setHorizontalAlignment(JXTextField.CENTER);
								txtOldClientID.setPreferredSize(new Dimension(100, 0));
							}
							{
								txtOldClient = new _JXTextField();
								pnlOldClient.add(txtOldClient, BorderLayout.CENTER);
							}
						}
						{
							pnlOldProject = new JPanel(new BorderLayout(3, 3));
							pnlOldDataCenter.add(pnlOldProject);
							{
								txtOldProjID = new _JXTextField();
								pnlOldProject.add(txtOldProjID, BorderLayout.WEST);
								txtOldProjID.setHorizontalAlignment(JXTextField.CENTER);
								txtOldProjID.setPreferredSize(new Dimension(50, 0));
							}
							{
								txtOldProject = new _JXTextField();
								pnlOldProject.add(txtOldProject, BorderLayout.CENTER);
							}
						}
						{
							pnlOldUnit = new JPanel(new BorderLayout(3, 3));
							pnlOldDataCenter.add(pnlOldUnit);
							{
								pnlOldUnitCenter = new JPanel(new BorderLayout(3, 3));
								pnlOldUnit.add(pnlOldUnitCenter, BorderLayout.CENTER);
								{
									txtOldUnitID = new _JXTextField();
									pnlOldUnitCenter.add(txtOldUnitID, BorderLayout.WEST);
									txtOldUnitID.setHorizontalAlignment(JXTextField.CENTER);
									txtOldUnitID.setPreferredSize(new Dimension(100, 0));
								}
								{
									txtOldUnitDesc = new _JXTextField();
									pnlOldUnitCenter.add(txtOldUnitDesc, BorderLayout.CENTER);
									//txtOldUnitDesc.setHorizontalAlignment(JXTextField.CENTER);
								}
							}
							{
								pnlOldUnitEast = new JPanel(new BorderLayout(3, 3));
								pnlOldUnit.add(pnlOldUnitEast, BorderLayout.EAST);
								{
									lblOldSeq = new JLabel("Old Seq No", JLabel.TRAILING);
									pnlOldUnitEast.add(lblOldSeq);
								}
								{
									txtOldSeqNo = new _JXTextField();
									pnlOldUnitEast.add(txtOldSeqNo, BorderLayout.EAST);
									txtOldSeqNo.setHorizontalAlignment(JXTextField.CENTER);
									txtOldSeqNo.setPreferredSize(new Dimension(50, 0));
								}
							}
						}
					}
				}
				{
					pnlNewData = new JPanel(new BorderLayout(5, 5));
					pnlClientDetails.add(pnlNewData);
					pnlNewData.setBorder(JTBorderFactory.createTitleBorder("New Details"));
					{
						pnlNewDataLabel = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNewData.add(pnlNewDataLabel, BorderLayout.WEST);
						{
							lblNewCLient = new JLabel("New Client");
							pnlNewDataLabel.add(lblNewCLient);
						}
						{
							lblNewProj = new JLabel("New Proj");
							pnlNewDataLabel.add(lblNewProj);
						}
						{
							lblNewUnit = new JLabel("New Unit");
							pnlNewDataLabel.add(lblNewUnit);
						}
					}
					{
						pnlNewDataCenter = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNewData.add(pnlNewDataCenter, BorderLayout.CENTER);
						{
							pnlNewClient = new JPanel(new BorderLayout(3, 3));
							pnlNewDataCenter.add(pnlNewClient);
							{
								txtNewClientID = new _JXTextField();
								pnlNewClient.add(txtNewClientID, BorderLayout.WEST);
								txtNewClientID.setHorizontalAlignment(JXTextField.CENTER);
								txtNewClientID.setPreferredSize(new Dimension(100, 0));
							}
							{
								txtNewClient = new _JXTextField();
								pnlNewClient.add(txtNewClient, BorderLayout.CENTER);
							}
						}
						{
							pnlNewProject = new JPanel(new BorderLayout(3, 3));
							pnlNewDataCenter.add(pnlNewProject);
							{
								txtNewProjID = new _JXTextField();
								pnlNewProject.add(txtNewProjID, BorderLayout.WEST);
								txtNewProjID.setHorizontalAlignment(JXTextField.CENTER);
								txtNewProjID.setPreferredSize(new Dimension(50, 0));
							}
							{
								txtNewProject = new _JXTextField();
								pnlNewProject.add(txtNewProject, BorderLayout.CENTER);
							}
						}
						{
							pnlNewUnit = new JPanel(new BorderLayout(3, 3));
							pnlNewDataCenter.add(pnlNewUnit);
							{
								pnlNewUnitCenter = new JPanel(new BorderLayout(3, 3));
								pnlNewUnit.add(pnlNewUnitCenter, BorderLayout.CENTER);
								{
									txtNewUnitID = new _JXTextField();
									pnlNewUnitCenter.add(txtNewUnitID, BorderLayout.WEST);
									txtNewUnitID.setHorizontalAlignment(JXTextField.CENTER);
									txtNewUnitID.setPreferredSize(new Dimension(100, 0));
								}
								{
									txtNewUnitDesc = new _JXTextField();
									pnlNewUnitCenter.add(txtNewUnitDesc, BorderLayout.CENTER);
								}
							}
							{
								pnlNewUnitEast = new JPanel(new BorderLayout(3, 3));
								pnlNewUnit.add(pnlNewUnitEast, BorderLayout.EAST);
								{
									lblNewSeq = new JLabel("New Seq No", JLabel.TRAILING);
									pnlNewUnitEast.add(lblNewSeq);
								}
								{
									txtNewSeq = new _JXTextField();
									pnlNewUnitEast.add(txtNewSeq, BorderLayout.EAST);
									txtNewSeq.setHorizontalAlignment(JXTextField.CENTER);
									txtNewSeq.setPreferredSize(new Dimension(50, 0));
								}
							}
						}
					}
				}
			}
			{
				pnlPmtDetails = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlPmtDetails,BorderLayout.CENTER);
				pnlPmtDetails.setBorder(components.JTBorderFactory.createTitleBorder("Payment Details"));
				{
					pnlPDNorth = new JPanel(new BorderLayout(3, 3));
					pnlPmtDetails.add(pnlPDNorth, BorderLayout.NORTH);
					{
						pnlPDNorthWest = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlPDNorth.add(pnlPDNorthWest, BorderLayout.WEST);
						{
							lblTotalPmtAmt = new JLabel("Total Payment Amount");
							pnlPDNorthWest.add(lblTotalPmtAmt);
						}
						{
							lblAmtForCredit = new JLabel("Amount for Credit");
							pnlPDNorthWest.add(lblAmtForCredit);
						}
					}
					{
						pnlPDNorthCenter = new JPanel(new GridLayout(2, 1, 3, 3));
						pnlPDNorth.add(pnlPDNorthCenter, BorderLayout.CENTER);
						{
							txtTotalPmtAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlPDNorthCenter.add(txtTotalPmtAmt);
							txtTotalPmtAmt.setEditable(false);
							txtTotalPmtAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtTotalPmtAmt.setValue(new BigDecimal("0.00"));
						}
						{
							txtCreditAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlPDNorthCenter.add(txtCreditAmt);
							txtCreditAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtCreditAmt.setValue(new BigDecimal("0.00"));
							txtCreditAmt.addKeyListener(new KeyAdapter() {

								public void keyReleased(KeyEvent evt){
									BigDecimal approved_credit_amt = (BigDecimal) ((_JXFormattedTextField) evt.getSource()).getValued();
									BigDecimal total_pmt_amt = (BigDecimal) txtTotalPmtAmt.getValued();
									
									if(approved_credit_amt.compareTo(total_pmt_amt) > 1){
										System.out.printf("Display approved credit amt: %s%n", approved_credit_amt);
									}
									
									if(approved_credit_amt.compareTo(total_pmt_amt) <= 0){
										for(int x=0; x<modelCrdtPmt.getRowCount(); x++){
											String part_id = (String) modelCrdtPmt.getValueAt(x, 1);
											BigDecimal deduction = mapParticulars.get(part_id);
											System.out.printf("Display deduction: %s%n", deduction);
											System.out.printf("Display Credit Amount sa For Loop: %s(%s)%n", approved_credit_amt, deduction);
											
											if(deduction != null) {
												if(approved_credit_amt.compareTo(deduction) > 0){
													if(part_id.equals("014")){
														modelCrdtPmt.setValueAt(approved_credit_amt, x, 3);
														approved_credit_amt = approved_credit_amt.subtract(approved_credit_amt);
													}else{
														modelCrdtPmt.setValueAt(deduction, x, 3);
														approved_credit_amt = approved_credit_amt.subtract(deduction);
													}
												}else{
													modelCrdtPmt.setValueAt(approved_credit_amt, x, 3);
													approved_credit_amt = approved_credit_amt.subtract(approved_credit_amt);
												}
											}
										}
										System.out.printf("Display Approved Credit Amt Before Prompt: %s%n", approved_credit_amt);
									}/*else{
										System.out.printf("Display Approved Credit Amt: %s%n", approved_credit_amt);
										
										JOptionPane.showMessageDialog(pnlOtherReq_CreditPayment.this, "Amount to credit must not be larger than total payments", "Credit", JOptionPane.WARNING_MESSAGE);
										txtCreditAmt.setValue(new BigDecimal("0.00"));
										
										for(int x =0; x<modelCrdtPmt.getRowCount(); x++){
											modelCrdtPmt.setValueAt(new BigDecimal("0.00"), x, 3);
										}
									}*/
								}
							});
						}
					}
				}
				{
					pnlPDCenter = new JPanel(new BorderLayout(3, 3));
					pnlPmtDetails.add(pnlPDCenter, BorderLayout.CENTER);
					{
						scrollCreditPmt = new JScrollPane();
						pnlPDCenter.add(scrollCreditPmt, BorderLayout.CENTER);
						{
							modelCrdtPmt = new modelCreditPmtRequest();
							modelCrdtPmt.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {

									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderCreditPmt.getModel()).addElement(modelCrdtPmt.getRowCount());
									}

									if(modelCrdtPmt.getRowCount() == 0){
										rowHeaderCreditPmt.setModel(new DefaultListModel());
									}
								}
							});

							tblCreditPmt = new _JTableMain(modelCrdtPmt);
							scrollCreditPmt.setViewportView(tblCreditPmt);
							scrollCreditPmt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							tblCreditPmt.hideColumns("Part ID");
							tblCreditPmt.setSortable(false);
						}
						{
							rowHeaderCreditPmt = tblCreditPmt.getRowHeader();
							rowHeaderCreditPmt.setModel(new DefaultListModel());
							scrollCreditPmt.setRowHeaderView(rowHeaderCreditPmt);
							scrollCreditPmt.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}

						tblCreditPmt.addPropertyChangeListener(new PropertyChangeListener() {

							public void propertyChange(PropertyChangeEvent arg0) {
								_JTableMain table = (_JTableMain) arg0.getSource();
								String propertyName = arg0.getPropertyName();

								if (!propertyName.equals("swingx.rollover")) {
									// System.out.printf("%n%n%s%n",
									// propertyName);
								}

								if (propertyName.equals("tableCellEditor")) {
									int column = table.convertColumnIndexToModel(table.getEditingColumn());

									if (column != -1 && modelCrdtPmt.getColumnClass(column).equals(BigDecimal.class)) {
										Object oldValue = null;
										try {
											oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
										} catch (NullPointerException e) {
										}

										if (oldValue != null) {
											//System.out.printf("%n%n%s: (%s, %s) %s%n", propertyName, oldValue, newValue, oldValue.getClass().getSimpleName());
											if (oldValue instanceof Double) {
												table.setValueAt(new BigDecimal((Double) oldValue), table.getEditingRow(), table.getEditingColumn());
											}
											if (oldValue instanceof Long) {
												table.setValueAt(new BigDecimal((Long) oldValue), table.getEditingRow(), table.getEditingColumn());
											}
											//isAmountLargerThanCredit();
										}
									}
								}
							}
						});
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 2, 3, 3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				//pnlSouth.setBorder(_EMPTY_BORDER);
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.setMnemonic(KeyEvent.VK_L);
					btnCancel.addActionListener(this);
				}
			}
		}
		//this.pack();
	}//XXX END OF INIT GUI
	
	public void setCreditdetails(String req_no){//SETS THE CREDIT DETAILS BASED ON THE REQUEST NO
		
		pgSelect db = new pgSelect();
		String sql = "select old_entity_id,get_client_name(old_entity_id) as old_client_name, \n" + 
					 "old_unit_id ,get_unit_description(old_unit_id) as old_unit_desc, \n" + 
					 "old_proj_id, get_project_name(old_proj_id) as old_proj_name, old_seq_no,\n" + 
					 "new_entity_id, _get_client_name(new_entity_id) as new_entity_id, \n" + 
					 "new_unit_id, get_unit_description(new_unit_id), \n"+
					 "new_proj_id, get_project_name(new_proj_id) ,new_seq_no, \n" + 
					 "new_sprice, new_dp_amount + new_availed as nsp,\n" + 
					 "get_dp_total_amounts_sched(new_entity_id, new_unit_id, new_proj_id, new_seq_no),\n" + 
					 "get_ma_total_amount_sched(new_entity_id, new_unit_id, new_proj_id, new_seq_no), \n"+
					 "req_type_id \n" + 
					 "from req_rt_request_header where request_no  = '"+req_no+"';";
		
		/*String sql = "SELECT get_client_name('"+old_entity_id+"'), get_unit_description('"+old_unit_id+"'), get_project_name('"+old_proj_id+"'), \n" + 
					   "get_client_name('"+final_entity_id+"'), get_unit_description('"+final_unit_id+"'), get_project_name('"+final_proj_id+"');\n";*/

		db.select(sql);
		FncSystem.out("Display Credit Details", sql);

		if(db.isNotNull()){

			//lookupRequest.setValue(req_no);
			request_no = req_no;
			txtOldClientID.setText((String) db.getResult()[0][0]);
			txtOldClient.setText((String) db.getResult()[0][1]);
			txtOldUnitID.setText((String) db.getResult()[0][2]);
			txtOldUnitDesc.setText((String) db.getResult()[0][3]);
			txtOldProjID.setText((String) db.getResult()[0][4]);
			txtOldProject.setText((String) db.getResult()[0][5]);
			txtOldSeqNo.setText(db.getResult()[0][6].toString());
			txtNewClientID.setText((String) db.getResult()[0][7]);
			txtNewClient.setText((String) db.getResult()[0][8]);
			txtNewUnitID.setText((String) db.getResult()[0][9]);
			txtNewUnitDesc.setText((String) db.getResult()[0][10]);
			txtNewProjID.setText((String) db.getResult()[0][11]);
			txtNewProject.setText((String) db.getResult()[0][12]);
			txtNewSeq.setText(db.getResult()[0][13].toString());
			
			/*txtSellPrice.setValue(db.getResult()[0][14]);
			txtNSP.setValue(db.getResult()[0][15]);*/
			
			/*txtTotDPCO.setValue(db.getResult()[0][16]);
			txtTotalMAAmt.setValue(db.getResult()[0][17]);
			chkAutomatic.setSelected(true);*/
		}
		
		String sqlUnit = "select coalesce(sum(a.amount), 0.00) as  amount\n" + 
						 "from rf_payments a\n" + 
						 "left join req_rt_request_header b on b.old_entity_id = a.entity_id and b.old_proj_id = a.proj_id and getinteger(b.old_unit_id)::VARCHAR = a.pbl_id and b.old_seq_no = a.seq_no\n" + 
						 "left join mf_pay_particular c on c.pay_part_id = a.pay_part_id\n" + 
						 "where b.request_no = '"+req_no+"'\n" + 
						 "and c.apply_ledger\n" + 
						 "and not exists (SELECT from_pay_rec_id FROM rf_credit_payments where from_pay_rec_id = a.pay_rec_id)\n"+
						 "AND (CASE WHen a.pymnt_type = 'B' THEN a.check_stat_id NOT IN ('07', '11', '03', '09') ELSE TRUE END) \n"+
						 "AND a.amount - COALESCE(a.applied_amt, 0) != 0 \n"+
						 //"and a.request_no IS NULL \n"+
						 
						 "AND EXISTS (SELECT *\n" + 
						 "	    FROM rf_client_ledger \n" + 
						 "	    where entity_id = a.entity_id \n" + 
						 "	    and proj_id = a.proj_id \n" + 
						 "	    and pbl_id = a.pbl_id \n" + 
						 "	    and seq_no = a.seq_no \n" + 
						 "	    and pay_rec_id = a.pay_rec_id \n" + 
						 "	    and status_id = 'A') \n"+
						 
						 "and a.status_id = 'A' \n" + 
						 "group by b.request_no";
		
		/*String sqlUnit = "SELECT COALESCE(SUM(a.amount), 0.00) \n"+
						 "FROM rf_payments a \n"+
						 "LEFT JOIN mf_pay_particular b on b.pay_part_id = a.pay_part_id \n"+
						 "WHERE b.apply_ledger \n"+
						 "AND a.entity_id = '"+old_entity_id+"' \n"+
						 "AND a.proj_id = '"+old_proj_id+"' \n"+
						 "AND a.pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n"+
						 "AND a.seq_no = "+old_seq_no+" \n"+
						 "AND a.status_id = 'A'";*/
		
		db.select(sqlUnit);
		
		FncSystem.out("Display Total Payment Amount", sqlUnit);

		txtTotalPmtAmt.setValue(db.getResult()[0][0]);
		txtCreditAmt.setValue(db.getResult()[0][0]);
		//txtCreditAmt.setValue(new BigDecimal("50168.50"));
	}

	private BigDecimal getResScheme(String entity_id, String unit_id){
		pgSelect db = new pgSelect();
		String sql = "select get_res_of_scheme(get_pmt_scheme_id('"+entity_id+"', '"+unit_id+"'))";
		db.select(sql);
		return (BigDecimal) db.getResult()[0][0];
	}

	private void setUpCreditAutomatically(){//SETS THE 
		BigDecimal remain_amt = new BigDecimal("0.00");
		BigDecimal tot_pmt_amt = (BigDecimal) txtTotalPmtAmt.getValued();
		BigDecimal dp_amt = (BigDecimal) txtTotDPCO.getValued();
		BigDecimal loan_amt = (BigDecimal) txtTotalMAAmt.getValued();
		String new_entity_id = txtNewClientID.getText();
		String new_unit_id = txtNewUnitID.getText();
		String new_seq_no = txtNewSeq.getText();
		String new_proj_id = txtNewProjID.getText();

		pgSelect db = new pgSelect();
		//GETS THE RESERVATION AMOUNT OF THE PAYMENT SCHEME FROM THE rf_sold_unit
		String sql = "select get_res_of_scheme(get_pmt_scheme_id('"+new_entity_id+"', getinteger('"+new_unit_id+"')::VARCHAR, "+new_seq_no+", '"+new_proj_id+"'))";
		db.select(sql);
		FncSystem.out("Display reservation of scheme", sql);
		BigDecimal reservation_amt = (BigDecimal) db.getResult()[0][0];
		System.out.printf("Display total payment amt %s%n", tot_pmt_amt);

		if(tot_pmt_amt.doubleValue() <= reservation_amt.doubleValue()){
			txtReservation.setValue(tot_pmt_amt);
		}else{
			txtReservation.setValue(reservation_amt);
		}

		remain_amt = tot_pmt_amt.subtract(reservation_amt);

		if(remain_amt.doubleValue() > 0){
			if(remain_amt.doubleValue() <= dp_amt.doubleValue()){
				txtDPCashLayout.setValue(remain_amt);
				System.out.printf("Display remain amt = %s%n", remain_amt);
				txtMA.setValue(new BigDecimal("0.00"));
			}else{
				txtDPCashLayout.setValue(dp_amt);
				System.out.printf("Display dp amt %s%n", dp_amt);

				remain_amt = remain_amt.subtract(dp_amt);
				System.out.printf("Display remain amount %s%n", remain_amt);

				if(remain_amt.doubleValue() <= loan_amt.doubleValue()){
					txtMA.setValue(remain_amt);

				}else{
					txtMA.setValue(loan_amt);
					remain_amt = remain_amt.subtract(loan_amt);
					JOptionPane.showMessageDialog(null, "Account has a refundable amount of : "+ remain_amt);
				}
			}
		}else{
			txtDPCashLayout.setValue(new BigDecimal("0.00"));
			txtMA.setValue(new BigDecimal("0.00"));
		}
		computeTotal();
	}

	private void computeTotal(){
		
		BigDecimal res_credit = (BigDecimal) txtReservation.getValued();
		BigDecimal dp_credit = (BigDecimal) txtDPCashLayout.getValued();
		BigDecimal ma_credit = (BigDecimal) txtMA.getValued();
		BigDecimal lump_dp_co = (BigDecimal) txtLumpSumDPCO.getValued();
		BigDecimal lump_ma = (BigDecimal) txtLumpSumMA.getValued();
		BigDecimal total_for_credit = res_credit.add(dp_credit).add(ma_credit).add(lump_dp_co).add(lump_ma);
		txtTotal.setValue(total_for_credit);
		
	}

	private Boolean toSave(){//TO SAVE
		
		if(isAmountLargerThanCredit()){
			return false;
		}
		
		return true;
	}
	
	public void displayCreditDocuments(String request_no){//Displays the Documents to be Credited
		pgSelect db = new pgSelect();

		//Setting of deductions for the buyer application
		
		/*String sqlParticular = "SELECT a.part_id, \n" + 
							   "SUM(case when a.part_id = '012' THEN a.amount \n" + 
							   "	 WHEN a.part_id = '013' THEN b.new_dp_amount + sp_compute_request_proc_fee(b.request_no) \n" + 
							   "	 WHEN a.part_id = '014' THEN b.new_availed ELSE 0.00 end) as amount\n" + 
							   "FROM mf_pay_scheme_detail a\n" + 
							   "LEFT JOIN req_rt_request_header b on b.new_pmt_scheme_id = a.pmt_scheme_id\n" + 
							   "WHERE b.request_no = '"+request_no+"'\n" + 
							   "AND a.part_id in ('012', '013', '014')\n" + 
							   "GROUP BY a.part_id\n" + 
							   "ORDER BY a.part_id;";*/
		
		String sqlParticular = "SELECT * FROM sp_auto_distribute_credit('"+request_no+"')"; 
				
		FncSystem.out("Display sql for deductions", sqlParticular);
		db.select(sqlParticular);
	
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				String part_id = (String) db.getResult()[x][0];
				BigDecimal amount = (BigDecimal) db.getResult()[x][1]; 
				System.out.printf("DIsplay part_id %s%n", part_id);
				System.out.printf("Display amount %s%n", amount);

				mapParticulars.put(part_id, amount);
			}
		}

		String sql = "SELECT a.pay_part_id,b.part_id, a.partdesc, 0.00, a.apply_ledger--b.part_desc\n" +  
					 "FROM mf_pay_particular a\n" + 
					 "LEFT JOIN mf_client_ledger_part b on b.pay_part_id = a.pay_part_id\n" + 
					 "WHERE a.apply_ledger\n" +
					 "AND b.part_id in ('012', '013', '014') \n"+
					 "ORDER BY a.apply_order \n";

		db.select(sql);
		
		FncSystem.out("Display Payments to Credit", sql);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelCrdtPmt.addRow(db.getResult()[x]);
			}
			scrollCreditPmt.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditPmt.getRowCount())));
			tblCreditPmt.packAll();
		}

		BigDecimal credit_amt = (BigDecimal) txtCreditAmt.getValued();

		//DITO YUNG LAKTAW NG SPLITTING OF PAYMENTS BASED SA TOTAL SA SCHEDULE
		for(int x=0; x<modelCrdtPmt.getRowCount(); x++){
			String part_id = (String) modelCrdtPmt.getValueAt(x, 1);
			String pay_part_id = (String) modelCrdtPmt.getValueAt(x, 0);
			
			BigDecimal deduction = mapParticulars.get(part_id);
			System.out.printf("Display deduction: %s%n", deduction);
			System.out.printf("Display Credit Amount: %s-%s-(%s)%n", part_id ,credit_amt, deduction);
			System.out.printf("Display x: %s%n", x);
			
			if(deduction != null){
				if(credit_amt.compareTo(deduction) > 0){
					
					if(part_id.equals("014")){
						modelCrdtPmt.setValueAt(credit_amt, x, 3);
						credit_amt = credit_amt.subtract(credit_amt);
					}else{
						modelCrdtPmt.setValueAt(deduction, x, 3);
						credit_amt = credit_amt.subtract(deduction);
					}
				}else{
					modelCrdtPmt.setValueAt(credit_amt, x, 3);
					credit_amt = credit_amt.subtract(credit_amt);
				} 
			}
		}
		
		tblCreditPmt.packAll();
	}

	public void displayCreditedPayments(String req_no){//CHECK DISPLAY OF PART_ID HERE
		pgSelect db = new pgSelect();
		BigDecimal credit_amt = new BigDecimal("0.00");
		String sql = "select a.apply_to_part_id, c.part_id ,b.partdesc, a.amount_credited, a.applied_to_ledger\n" + 
					 "from rf_credit_payments a\n" + 
					 "left join mf_pay_particular b on b.pay_part_id = a.apply_to_part_id\n" + 
					 "left join mf_client_ledger_part c on c.pay_part_id = a.apply_to_part_id\n" + 
					 "where a.request_no = '"+req_no+"'\n" + 
					 "and c.part_id in ('012', '013', '014') order by c.part_id;";
		db.select(sql);
		
		//FncSystem.out("Display Credited Payments", sql);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){//CHECK THE ADDITION OF THE CREDITED PAYMENTS HERE
				modelCrdtPmt.addRow(db.getResult()[x]);
				BigDecimal credited_amt = (BigDecimal) modelCrdtPmt.getValueAt(x, 3);
				credit_amt = credit_amt.add(credited_amt);
			}
			scrollCreditPmt.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditPmt.getRowCount())));
			tblCreditPmt.packAll();
			tblCreditPmt.setEditable(false);
			lookupRequest.setEditable(false);
			btnSave.setEnabled(false);
			txtCreditAmt.setEditable(false);
			txtCreditAmt.setValue(credit_amt);
		}
	}

	//CHECKS IF THE CREDITED AMOUNTS
	private boolean isAmountLargerThanCredit(){//CHECKS if the amount is larger than the credit
		BigDecimal amount_computed = new BigDecimal("0.00");
		
		BigDecimal total_pmt = (BigDecimal) txtTotalPmtAmt.getValued();
		BigDecimal approved_credit_amt = (BigDecimal) txtCreditAmt.getValued();
		
		for (int x =0; x<modelCrdtPmt.getRowCount(); x++){
			
			BigDecimal credit_amt = (BigDecimal) modelCrdtPmt.getValueAt(x, 3);
			System.out.printf("Display credit amt: %s%n", credit_amt);
			
			amount_computed = amount_computed.add(credit_amt);
		}
		
		System.out.printf("Display total pmt: %s%n", total_pmt);
		System.out.printf("Display approved credit amt: %s%n", approved_credit_amt);
		
		amount_computed = amount_computed.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		System.out.printf("Display amount computed: %s%n", amount_computed);
		
		if(amount_computed.doubleValue() > approved_credit_amt.doubleValue()){
			JOptionPane.showMessageDialog(pnlMain, "Approved Credit Amount cannot be larger than total payments", "Credit Payment", JOptionPane.WARNING_MESSAGE);
			return true;
		}
		return false;
	}

	private void cancel(){
		//lookupRequest.setValue(null);
		request_no = null;
		txtOldClientID.setText("");
		txtOldClient.setText("");
		txtOldUnitID.setText("");
		txtOldUnitDesc.setText("");
		txtOldProjID.setText("");
		txtOldProject.setText("");
		txtOldSeqNo.setText("");
		txtNewClientID.setText("");
		txtNewClient.setText("");
		txtNewUnitID.setText("");
		txtNewUnitDesc.setText("");
		txtNewProjID.setText("");
		txtNewProject.setText("");
		txtNewSeq.setText("");
		
		/*txtSellPrice.setValue(new BigDecimal("0.00"));
		txtNSP.setValue(new BigDecimal("0.00"));*/
		txtTotalPmtAmt.setValue(new BigDecimal("0.00"));
		//txtAmtForCredit.setValue(new BigDecimal("0.00"));
		modelCrdtPmt.clear();

	}
	
	private void saveCreditPmt(){
		
		pgSelect db = new pgSelect();
		
		ArrayList<String> listPayPartID = new ArrayList<String>();
		ArrayList<BigDecimal> listCrediAmt = new ArrayList<BigDecimal>();
		ArrayList<Boolean> listApplyLedger = new ArrayList<Boolean>();
		
		for(int x= 0; x<modelCrdtPmt.getRowCount(); x++){
			BigDecimal amount = (BigDecimal) modelCrdtPmt.getValueAt(x, 3);
			
			if(amount.doubleValue() != 0){
				String pay_part_id = (String) modelCrdtPmt.getValueAt(x, 0);
				Boolean apply_ledger= (Boolean) modelCrdtPmt.getValueAt(x, 4);
				
				listPayPartID.add(String.format("'%s'", pay_part_id));
				listCrediAmt.add(amount);
				listApplyLedger.add(apply_ledger);
				
			}
		}
		
		String pay_part_id = listPayPartID.toString().replaceAll("\\[|\\]", "");
		String amount = listCrediAmt.toString().replaceAll("\\[|\\]", "");
		String apply_ledger = listApplyLedger.toString().replaceAll("\\[|\\]", "");
		
		String sql = "SELECT sp_request_credit_pmt('"+request_no+"', ARRAY["+pay_part_id+"]::VARCHAR[], ARRAY["+amount+"]::NUMERIC[], ARRAY["+apply_ledger+"]::BOOLEAN[], '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for credting of payment", sql);
		
	}
	
	private void AutoApplyCredit(){//auto apply the credit
		BigDecimal amt_for_credit = (BigDecimal) txtAmtForCredit.getValued();

		Boolean end = false;
		for(int x= 0; x<modelCrdtPmt.getRowCount(); x++){

			String part_id = (String) modelCrdtPmt.getValueAt(x, 1);
			BigDecimal deduction = mapParticulars.get(part_id);

			if(amt_for_credit.compareTo(deduction) > 0 && end == false){
				amt_for_credit = amt_for_credit.subtract(deduction);
				modelCrdtPmt.setValueAt(deduction, x, 3);
			}else if(deduction.compareTo(amt_for_credit) > 0 && end == false){
				System.out.printf("Display x: %s%n", x);
				modelCrdtPmt.setValueAt(amt_for_credit, x, 3);
				end = true;
			}else if(end){
				System.out.println("Dumaan dito sa setting ng values sa zero");
				modelCrdtPmt.setValueAt(new BigDecimal("0.00"), x, 3);
			}
			//System.out.printf("Display part_id: %s-%s (%s, %s)%n", x, part_id, amt_for_credit, deduction);
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("Save")){
			if(toSave()){
				//if(isTotalValid() && isDPValid()){
				if (JOptionPane.showConfirmDialog(null, "Credit Payment?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					//saveCredit();
					saveCreditPmt();
					JOptionPane.showMessageDialog(null, "Finished crediting of payment.");
					this.dispose();
				}
				//}
			}
		}

		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(null, "Cancel Editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancel();
			}
		}
	}
}
