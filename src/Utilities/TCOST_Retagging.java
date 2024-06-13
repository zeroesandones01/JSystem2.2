package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import Buyers.ClientServicing._CARD;
import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.model_client;
import tablemodel.model_tcost;

public class TCOST_Retagging extends _JInternalFrame implements _GUI, MouseListener {

	private static final long serialVersionUID = -6517018477299344044L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlCenter1;
	JPanel pnlSouth;
	JPanel pnlTable;
	JPanel pnlDetail;
	JPanel pnlInfo;
	JPanel pnlInfo1;
	JPanel pnlAmount;
	JPanel pnlCreateRPLF;
	JPanel pnlBuyer;
	JPanel pnlBuyerBatch;
	JPanel pnlClient;
	JPanel pnlButton;
	JPanel pnlSearch;

	JLabel lblCenter;
	JLabel lblClient;
	JLabel lblProject;
	JLabel lblDateReserved;
	JLabel lblPblDescription;
	JLabel lblStatus;
	JLabel lblSellingAgent;
	JLabel lblPblID;
	JLabel lblNorth;
	JLabel lblBatch;
	JLabel lblCode;
	JLabel lblRunningTotal1;
	JLabel lblSetupAmount1;
	JLabel lblBalanceAmount;
	JLabel lblProject2;
	JLabel lblUnit;
	JLabel lblBuyer;
	JLabel lblRequestType;
	JLabel lblAvailer;
	JLabel lblAvailerType;
	JLabel lblRunningTotal1_Batch;
	JLabel lblSetupAmount1_Batch;
	JLabel lblBalanceAmount_Batch;
	JLabel lblCode_Batch;

	_JXFormattedTextField txtAmount;
	JXTextField txtRemarks;
	JXTextField txtRemarks_Batch;
	_JXFormattedTextField txtAmount_Batch;
	JXTextField txtSearch;
	JXTextField txtRequestedBy;
	JXTextField txtRequestedBy_Batch;


	//JButton btnClear;
	//JButton btnClear2;
	JButton btnEntry;
	JButton btnDRF;
	JButton btnRemoveRow;
	JButton btnAddRow;
	JButton btnSave;
	JButton btnCancelRPLF;
	JButton btnTSave;
	JButton btnBatch;

	_JLookup lookupClient;
	_JLookup lookupCode;
	_JLookup lookupProject;
	_JLookup lookupUnit;
	_JLookup lookupRequestType;
	_JLookup lookupAvailer;
	_JLookup lookupAvailerType;
	_JLookup lookupCode_Batch;

	_JDateChooser dateSched;
	_JDateChooser dateSched_Batch;

	private static JTabbedPane tabCenter;

	_JScrollPaneTotal scrollTCTTotal;
	_JTableTotal tblTCTTotal;
	model_tcost modelTCTTotal;

	Object[] clientDetails;

	model_tcost modelTCT;
	_JScrollPaneMain scrollTCT;
	_JTableMain tblTCT;
	JList rowHeaderTCT;

	model_client modelTCT_Batch;
	//_JScrollPaneMain scrollTCT_Batch;
	JScrollPane scrollTCT_Batch;
	_JTableMain tblTCT_Batch;
	JList rowHeaderTCT_Batch;

	static String title = "TCOST (For Retagging)";
	Dimension frameSize = new Dimension(900, 550);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblDate;               
	private JLabel lblAmount;
	private JLabel lblReqBy;
	private JLabel lblReqBy1;
	private JLabel lblReqBy1_Batch;
	private JLabel lblRemarks;
	private JLabel lblInfo;
	private String batch_no;
	private String pbl;
	private String entity;
	private String projid;
	private String buyertype;
	private Integer seq;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	DecimalFormat df = new DecimalFormat("#,###,##0.00");

	private JButton btnCancel;

	public TCOST_Retagging() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TCOST_Retagging(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public TCOST_Retagging(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			{
				pnlCreateRPLF= new JPanel();
				pnlCreateRPLF.setLayout(null);
				pnlCreateRPLF.setPreferredSize(new java.awt.Dimension(500, 125));
				{
					lblNorth = new JLabel("Request Type");
					pnlCreateRPLF.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 10, 120, 22);
				}
				{
					lookupRequestType = new _JLookup(null, "Request Type");
					pnlCreateRPLF.add(lookupRequestType);
					lookupRequestType.setReturnColumn(0);
					lookupRequestType.setLookupSQL(SQL_REQUESTTYPE());
					lookupRequestType.setBounds(110, 10, 90, 22);
					lookupRequestType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								lblRequestType.setText(String.format("[ %s ]", (String) data[1]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblRequestType = new JLabel("[ ]");
					pnlCreateRPLF.add(lblRequestType);
					lblRequestType.setHorizontalAlignment(JLabel.LEFT);
					lblRequestType.setBounds(205, 10, 200, 22);
				}

				{
					lblNorth = new JLabel("Availer");
					pnlCreateRPLF.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 35, 120, 22);
				}
				{
					lookupAvailer = new _JLookup(null, "Availer");
					pnlCreateRPLF.add(lookupAvailer);
					lookupAvailer.setReturnColumn(0);
					lookupAvailer.setLookupSQL(SQL_AVAILER());
					lookupAvailer.setBounds(110, 35, 90, 22);
					lookupAvailer.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							FncSystem.out("Display SQL for Availer", lookupAvailer.getLookupSQL());
							if (data != null) {

								lblAvailer.setText(String.format("[ %s ]", (String) data[1]));
								lookupAvailerType.setLookupSQL(SQL_AVAILERTYPE((String) data[0]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblAvailer = new JLabel("[ ]");
					pnlCreateRPLF.add(lblAvailer);
					lblAvailer.setHorizontalAlignment(JLabel.LEFT);
					lblAvailer.setBounds(205, 35, 255, 22);
				}

				{
					lblNorth = new JLabel("Availer Type");
					pnlCreateRPLF.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 60, 120, 22);
				}
				{
					lookupAvailerType = new _JLookup(null, "Availer Type");
					pnlCreateRPLF.add(lookupAvailerType);
					lookupAvailerType.setReturnColumn(0);
					lookupAvailerType.setBounds(110, 60, 90, 22);
					lookupAvailerType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {

								lblAvailerType.setText(String.format("[ %s ]", (String) data[1]));

								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblAvailerType = new JLabel("[ ]");
					pnlCreateRPLF.add(lblAvailerType);
					lblAvailerType.setHorizontalAlignment(JLabel.LEFT);
					lblAvailerType.setBounds(205, 60, 200, 22);
				}

				{
					btnSave = new JButton("Create RPLF");
					pnlCreateRPLF.add(btnSave);
					btnSave.setActionCommand("Create RPLF");
					btnSave.setBounds(130, 90, 100, 30);
					btnSave.addActionListener(this);
				}
				{
					btnCancelRPLF = new JButton("Cancel");
					pnlCreateRPLF.add(btnCancelRPLF);
					btnCancelRPLF.setActionCommand("Cancel RPLF");
					btnCancelRPLF.setBounds(235, 90, 100, 30);
					btnCancelRPLF.addActionListener(this);
				}
			}
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.NORTH);
//				{
//					pnlBuyer = new JPanel(new BorderLayout(3, 3));
//					tabCenter.addTab("Buyer Cost - Individual", null, pnlBuyer, null); //XXX Buyer Cost - Individual
//					pnlBuyer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
//					{
//						pnlNorth = new JPanel();
//						pnlBuyer.add(pnlNorth, BorderLayout.NORTH);
//						pnlNorth.setLayout(new BorderLayout(5, 5));
//						pnlNorth.setPreferredSize(new Dimension(800, 90));
//						{
//							pnlClient = new JPanel();
//							pnlNorth.add(pnlClient, BorderLayout.WEST);
//							pnlClient.setLayout(null);
//							pnlClient.setPreferredSize(new Dimension(775, 100));
//							{
//								lblCenter = new JLabel("Client");
//								pnlClient.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(10, 10, 120, 22);
//							}
//							{
//								lookupClient = new _JLookup(null, "Client");
//								pnlClient.add(lookupClient);
//								lookupClient.setReturnColumn(1);
//								lookupClient.setLookupSQL(_CARD.sqlClients());
//								lookupClient.setFilterCardName(true);
//								lookupClient.setBounds(70, 10, 100, 22);
//								lookupClient.addLookupListener(new LookupListener() {
//									public void lookupPerformed(LookupEvent event) {
//										Object[] data = ((_JLookup)event.getSource()).getDataSet();
//										if(data != null){
//
//											String entity_id = (String) data[1];
//											String proj_id = (String) data[7];
//											String pbl_id = (String) data[4];
//											Integer seq_no = (Integer) data[5];
//
//											displayClientDetails(entity_id, proj_id, pbl_id, seq_no, true);
//
//											displayTCostDetails();
//
//											lookupCode.setLookupSQL(sqlTcost(entity_id));
//											KEYBOARD_MANAGER.focusNextComponent();
//										}
//									}
//								});
//							}
//							{
//								lblClient = new JLabel("[ ]");
//								pnlClient.add(lblClient);
//								lblClient.setHorizontalAlignment(JLabel.LEFT);
//								lblClient.setBounds(185, 10, 300, 22);
//							}
//							{
//								lblCenter = new JLabel("Project");
//								pnlClient.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(10, 35, 120, 22);
//							}
//							{
//								lblProject = new JLabel("[ ]");
//								pnlClient.add(lblProject);
//								lblProject.setHorizontalAlignment(JLabel.LEFT);
//								lblProject.setBounds(70, 35, 300, 22);
//							}
//							{
//								lblCenter = new JLabel("Unit");
//								pnlClient.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(10, 60, 120, 22);
//							}
//							{
//								lblPblDescription = new JLabel("[ ]");
//								pnlClient.add(lblPblDescription);
//								lblPblDescription.setHorizontalAlignment(JLabel.LEFT);
//								lblPblDescription.setBounds(70, 60, 300, 22);
//							}
//							{
//								lblCenter = new JLabel("Selling Agent");
//								pnlClient.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(440, 10, 120, 22);
//							}
//							{
//								lblSellingAgent = new JLabel("[ ]");
//								pnlClient.add(lblSellingAgent);
//								lblSellingAgent.setHorizontalAlignment(JLabel.LEFT);
//								lblSellingAgent.setBounds(540, 10, 350, 22);
//							}
//							{
//								lblCenter = new JLabel("Date Reserved");
//								pnlClient.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(440, 35, 120, 22);
//							}
//							{
//								lblDateReserved = new JLabel("[ ]");
//								pnlClient.add(lblDateReserved);
//								lblDateReserved.setHorizontalAlignment(JLabel.LEFT);
//								lblDateReserved.setBounds(540, 35, 300, 22);
//							}
//							{
//								lblCenter = new JLabel("Status");
//								pnlClient.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(440, 60, 120, 22);
//							}
//							{
//								lblStatus = new JLabel("[ ]");
//								pnlClient.add(lblStatus);
//								lblStatus.setHorizontalAlignment(JLabel.LEFT);
//								lblStatus.setBounds(540, 60, 300, 22);
//							}
//						}
//					}
//					{
//						pnlCenter = new JPanel();
//						pnlBuyer.add(pnlCenter, BorderLayout.CENTER);
//						pnlCenter.setLayout(new BorderLayout(5, 5));
//						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Transfer Cost Details"));
//						pnlCenter.setPreferredSize(new Dimension(800, 340));
//						{
//							pnlTable = new JPanel(new BorderLayout(5, 5));
//							pnlCenter.add(pnlTable, BorderLayout.WEST);
//							pnlTable.setPreferredSize(new Dimension(460, 200));
//							pnlTable.setBorder(lineBorder);
//							{
//								scrollTCT = new _JScrollPaneMain();
//								pnlTable.add(scrollTCT, BorderLayout.CENTER);
//								modelTCT = new model_tcost();
//								modelTCT.addTableModelListener(new TableModelListener() {
//									public void tableChanged(TableModelEvent e) {
//										if(e.getType() == TableModelEvent.DELETE){
//											rowHeaderTCT.setModel(new DefaultListModel());
//										}
//										if(e.getType() == TableModelEvent.INSERT){
//											((DefaultListModel)rowHeaderTCT.getModel()).addElement(modelTCT.getRowCount());
//										}
//									}
//								});
//
//								tblTCT = new _JTableMain(modelTCT);
//								tblTCT.packAll();
//								tblTCT.setAlignmentX(LEFT_ALIGNMENT);
//								scrollTCT.setViewportView(tblTCT);
//
//								tblTCT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//									public void valueChanged(ListSelectionEvent arg0) {
//										try {
//											if(!arg0.getValueIsAdjusting()){
//
//												String rplf_no = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(), 5);
//												String cost_desc = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(), 1);
//												String entity_id = lookupClient.getValue();
//												String batch = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(), 8);
//												//displayTransferCostDetails(rplf_no);
//												displayTransferCostDetails_IND(cost_desc, batch, entity_id);
//												
//												if(tblTCT.getSelectedRows().length > 0){
//													
//													if (batch == (null) || batch == ("")){
//														txtAmount.setEditable(true);
//													}else{
//														txtAmount.setEditable(false);
//													}
//												}
//											}
//										} catch (ArrayIndexOutOfBoundsException e) { }
//									}
//								});
//								tblTCT.putClientProperty("terminateEditOnFocusLost", true);
//								{
//									rowHeaderTCT = tblTCT.getRowHeader();
//									rowHeaderTCT.setModel(new DefaultListModel());
//									scrollTCT.setRowHeaderView(rowHeaderTCT);
//									scrollTCT.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
//								}
//
//							}
//							{
//								scrollTCTTotal = new _JScrollPaneTotal(scrollTCT);
//								pnlTable.add(scrollTCTTotal, BorderLayout.SOUTH);
//								{
//									modelTCTTotal = new model_tcost();
//									modelTCTTotal.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00), null, null, null, null, null, null, null });
//
//									tblTCTTotal = new _JTableTotal(modelTCTTotal, tblTCT);
//									scrollTCTTotal.setViewportView(tblTCTTotal);
//
//									tblTCTTotal.setTotalLabel(2);
//								}
//								scrollTCTTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
//							}
//						}
//						{
//							pnlDetail = new JPanel(new BorderLayout(5, 5));
//							pnlCenter.add(pnlDetail, BorderLayout.EAST);
//							pnlDetail.setPreferredSize(new Dimension(400, 335));
//							{
//								pnlInfo = new JPanel(new BorderLayout(5, 5));
//								pnlDetail.add(pnlInfo, BorderLayout.NORTH);
//								pnlInfo.setBorder(JTBorderFactory.createTitleBorder("Detail INFO"));
//								pnlInfo.setPreferredSize(new Dimension(400, 205));
//								{
//									JPanel pnlLabel = new JPanel(new GridLayout(5, 0, 5, 5));
//									pnlInfo.add(pnlLabel, BorderLayout.WEST);
//									{
//										lblInfo = new JLabel("Code");
//										pnlLabel.add(lblInfo);
//										lblInfo.setHorizontalAlignment(JLabel.RIGHT);
//
//										lblDate = new JLabel("Date");
//										pnlLabel.add(lblDate);
//										lblDate.setHorizontalAlignment(JLabel.RIGHT);
//
//										lblAmount = new JLabel("Amount");
//										pnlLabel.add(lblAmount);
//										lblAmount.setHorizontalAlignment(JLabel.RIGHT);
//
//
//										lblReqBy = new JLabel("Request By");
//										pnlLabel.add(lblReqBy);
//										lblReqBy.setHorizontalAlignment(JLabel.RIGHT);
//
//										lblRemarks = new JLabel("Remarks");
//										pnlLabel.add(lblRemarks);
//										lblRemarks.setHorizontalAlignment(JLabel.RIGHT);
//									}
//
//									JPanel pnlText = new JPanel(new GridLayout(5, 0, 5, 5));
//									pnlInfo.add(pnlText, BorderLayout.CENTER);
//									{
//
//										{
//											JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
//											pnlText.add(pnlLookup);
//											{
//												{
//													lookupCode = new _JLookup(null, "Code", "Please select client.");
//													pnlLookup.add(lookupCode, BorderLayout.WEST);
//													lookupCode.setPreferredSize(new Dimension(50, 20));
//													lookupCode.setReturnColumn(0);
//													lookupCode.addActionListener(this);
//													lookupCode.setFilterName(true);
//													lookupCode.addLookupListener(new LookupListener() {
//														public void lookupPerformed(LookupEvent event) {
//															Object[] data = ((_JLookup) event.getSource()).getDataSet();
//															String emp_code = (String) UserInfo.EmployeeCode;
//															String emp_name = (String) UserInfo.FullName;
//															if (data != null) {
//																FncSystem.out("SQL for LookupCOde", lookupCode.getLookupSQL());
//																
//																lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
//																lblSetupAmount1.setText(String.format("%s", data[2]));
//																lblBalanceAmount.setText(String.format("%s", lblSetupAmount1.getText()));
//																txtRequestedBy.setText(emp_code);
//																//txtAmount.setEditable(true);
//																lblReqBy1.setText(String.format("[ %s ]", emp_name));
//																pnlState (true, false);
//
//																if (data[3] != null){
//																	txtRemarks.setText(String.format("%s", data[3]));
//																} else {
//																	txtRemarks.setText(" ");
//																}
//
//
//																if(data != null){
//																	BigDecimal amount = new BigDecimal (String.format("%s", reg_amount2(entity, pbl, buyertype)));
//																	BigDecimal amount2 = new BigDecimal (String.format("%s", reg_amount(entity, pbl, buyertype)));
//																	BigDecimal amount3 = new BigDecimal (String.format("%s", reg_amount3(entity, pbl, buyertype)));
//																	BigDecimal amount4 = new BigDecimal (String.format("100.00"));
//																	
//																	BigDecimal computed_tcost_amt = getTCost_Dtl_amt(lookupCode.getValue(), entity, projid, pbl, seq);
//																	
//																	/*if (lookupCode.getValue().equals("034")){
//																		modelTCT.addRow(new Object []{true, "DOCUMENTARY STAMP (60 days allowance for LIQUIDATION)", Calendar.getInstance().getTime(), amount, "TCost Amt = NetSP * 1.50%", null});
//
//																	} else if (lookupCode.getValue().equals("166")){
//																		modelTCT.addRow(new Object []{true, "TRANSFER TAX", Calendar.getInstance().getTime(), amount2, "TCost Amt = NetSP * 0.50%", null});
//																	} else if (lookupCode.getValue().equals("008") || lookupCode.getValue().equals("011") || lookupCode.getValue().equals("012") || lookupCode.getValue().equals("021") || lookupCode.getValue().equals("169")){
//																		modelTCT.addRow(new Object []{true, "ENTRY FEE FOR TRANSFER TCT  FROM CDC TO BUYER", Calendar.getInstance().getTime(), new BigDecimal(90.00), "Fixed Amt", null});
//																		modelTCT.addRow(new Object []{true, "ISSUANCE OF TITLE", Calendar.getInstance().getTime(), new BigDecimal(60.00), "MEMO DATED 6/4/12.", null});
//																		modelTCT.addRow(new Object []{true, "JUDICIAL FORM FEE", Calendar.getInstance().getTime(), new BigDecimal(30.00), "DUE TO RD COMPUTERIZATION.  (originally created as it fee, with advise from sir alan / weng to adjust 7/26/10)", null});
//																		modelTCT.addRow(new Object []{true, "REGISTRATION FEE TRANSFER OF TCT", Calendar.getInstance().getTime(), amount3, null, null});
//																		modelTCT.addRow(new Object []{true, "RESEARCH FEE (LRF) TRANSFER OF TCT FROM CDC TO BUYER", Calendar.getInstance().getTime(), amount3.divide(amount4), null, null});
//																	}															
//																	else {
//																		if (data[3] == null) {
//																			modelTCT.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], "", null});
//																		} else {
//																			if (data[3].equals("Fixed Amt")) {
//																				modelTCT.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null});
//																			} else {
//																				modelTCT.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null});
//																			}
//																		}
//																	}*/
//																	
//																	if(lookupCode.getValue().equals("008") || lookupCode.getValue().equals("011") || lookupCode.getValue().equals("012") || lookupCode.getValue().equals("021") || lookupCode.getValue().equals("169")){
//																		addTCostEntries(entity, projid, pbl, seq, modelTCT);
//																		//tblTCT.setEnabled(false);
//																	}else{
//																		modelTCT.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), computed_tcost_amt, data[3], null});
//																		//tblTCT.setEnabled(false);
//																	}
//																	
//																	rowHeaderTCT.setModel(new DefaultListModel());
//
//																	for(int y =1; y<=modelTCT.getRowCount(); y++){
//																		((DefaultListModel) rowHeaderTCT.getModel()).addElement(y);
//																	}
//
//																	scrollTCT.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT.getRowCount())));
//																	tblTCT.packAll();
//																	
//																	if (tblTCT.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
//																		tblTCT.getColumnModel().getColumn(1).setPreferredWidth(200);
//																	}
//
//																	btnDRF.setEnabled(true);
//																	btnTSave.setEnabled(false);
//																	txtRemarks.setEditable(true);
//																	if (batch() == false) {
//																		lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
//																	} else {
//																		//lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
//																	}
//
//																}
//
//																KEYBOARD_MANAGER.focusNextComponent();
//															}
//														}
//													});
//													{
//														
//														lblCode = new JLabel("[ ]");
//														pnlLookup.add(lblCode,BorderLayout.CENTER);
//														lblCode.setHorizontalAlignment(JLabel.LEFT);
//													}
//												}
//												{
//													dateSched = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
//													pnlText.add(dateSched);
//													dateSched.setDate(null);
//													//dateSched.setEditable(false);
//													dateSched.setEnabled(false);
//													dateSched.setDateFormatString("yyyy-MM-dd");
//													((JTextFieldDateEditor)dateSched.getDateEditor()).setEditable(false);
//													dateSched.setDate(Calendar.getInstance().getTime());
//													dateSched.addDateListener(new DateListener() {
//														public void datePerformed(DateEvent event) {
//															int row = tblTCT.convertRowIndexToModel(tblTCT.getSelectedRow());
//															modelTCT.setValueAt(dateSched.getDate(), row, 2);
//
//														}
//													});
//												}
//												{
//													txtAmount = new _JXFormattedTextField("0.00");
//													pnlText.add(txtAmount);
//													txtAmount.setHorizontalAlignment(JLabel.RIGHT);
//													txtAmount.setBounds(215, 10, 280, 22);
//													txtAmount.setEditable(false);
//													//txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
//													txtAmount.addKeyListener(new KeyAdapter() {
//														public void keyReleased(KeyEvent arg0) {
//															BigDecimal value1 = new BigDecimal (lblSetupAmount1.getText());
//															BigDecimal value2 = new BigDecimal (lblRunningTotal1.getText());
//
//															int row = tblTCT.convertRowIndexToModel(tblTCT.getSelectedRow());
//
//															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
//																lblBalanceAmount.setText(String.format("%s", value1.subtract(value2)));
//
//																modelTCT.setValueAt(value2, row, 3);
//																computeTotal();
//															}
//														}
//													});
//													txtAmount.getDocument().addDocumentListener(new DocumentListener() {
//														public void insertUpdate(DocumentEvent e) {
//															lblRunningTotal1.setText(txtAmount.getText());
//														}
//														public void removeUpdate(DocumentEvent e) {
//															lblRunningTotal1.setText(txtAmount.getText());
//														}
//														public void changedUpdate(DocumentEvent e) {
//
//														}
//													});
//												}
//
//												JPanel pnlReqBy = new JPanel(new BorderLayout(3, 3));
//												pnlText.add(pnlReqBy);
//												{
//													{
//														txtRequestedBy = new JXTextField();
//														pnlReqBy.add(txtRequestedBy, BorderLayout.WEST);
//														txtRequestedBy.setEditable(false);
//														txtRequestedBy.setPreferredSize(new Dimension(100, 20));
//														{
//															lblReqBy1 = new JLabel("[ ]");
//															pnlReqBy.add(lblReqBy1,BorderLayout.CENTER);
//															lblReqBy1.setHorizontalAlignment(JLabel.LEFT);
//														}
//													}
//												}
//												{
//													txtRemarks = new JXTextField();
//													pnlText.add(txtRemarks);
//													txtRemarks.setHorizontalAlignment(JLabel.LEFT);
//													txtRemarks.setBounds(215, 10, 280, 22);
//													txtRemarks.setEditable(false);
//												}
//											}
//										}
//									}
//								}
//							}
//							{
//								pnlAmount = new JPanel(new BorderLayout(5, 5));
//								pnlDetail.add(pnlAmount, BorderLayout.SOUTH);
//								pnlAmount.setBorder(JTBorderFactory.createTitleBorder("Detail AMOUNT"));
//								pnlAmount.setPreferredSize(new Dimension(450, 95));
//								{
//									JPanel pnlLabelTotal1 = new JPanel(new GridLayout(4, 2, 3, 3));
//									pnlAmount.add(pnlLabelTotal1, BorderLayout.WEST);
//									{
//										JLabel lblSetupAmount2 = new JLabel("                     ");
//										pnlLabelTotal1.add(lblSetupAmount2, BorderLayout.WEST);
//									}
//								}
//								{
//									JPanel pnlLabelTotal = new JPanel(new GridLayout(4, 2, 3, 3));
//									pnlAmount.add(pnlLabelTotal, BorderLayout.CENTER);
//									{
//										JLabel lblSetupAmount = new JLabel("Setup Amount :");
//										pnlLabelTotal.add(lblSetupAmount);
//										{
//											lblSetupAmount1 = new JLabel("0.00");
//											pnlLabelTotal.add(lblSetupAmount1);
//										}
//									}
//									{
//										JLabel lblRunningTotal = new JLabel("Running Total  :");
//										pnlLabelTotal.add(lblRunningTotal);
//										{
//											lblRunningTotal1 = new JLabel("0.00");
//											pnlLabelTotal.add(lblRunningTotal1);
//										}
//									}
//									{
//										JLabel lblBalance = new JLabel("Balance +/(-)   :");
//										pnlLabelTotal.add(lblBalance);
//										{
//											lblBalanceAmount = new JLabel("0.00");
//											pnlLabelTotal.add(lblBalanceAmount);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
				{
					pnlBuyerBatch = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Buyer Cost - Batch", null, pnlBuyerBatch, null); //XXX Buyer Cost - Batch
					pnlBuyerBatch.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						pnlCenter = new JPanel();
						pnlBuyerBatch.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Transfer Cost Details"));
						pnlCenter.setPreferredSize(new Dimension(800, 440));
						{
							pnlTable = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlTable, BorderLayout.WEST);
							pnlTable.setPreferredSize(new Dimension(460, 200));
							pnlTable.setBorder(lineBorder);
							{
								scrollTCT_Batch = new JScrollPane();
								pnlTable.add(scrollTCT_Batch, BorderLayout.CENTER);
								scrollTCT_Batch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
								{
									modelTCT_Batch = new model_client();
									modelTCT_Batch.addTableModelListener(new TableModelListener() {

										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderTCT_Batch.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowHeaderTCT_Batch.getModel()).addElement(modelTCT_Batch.getRowCount());
											}
										}
									});

									tblTCT_Batch = new _JTableMain(modelTCT_Batch);
									tblTCT_Batch.addMouseListener(this);
									scrollTCT_Batch.setViewportView(tblTCT_Batch);

									tblTCT_Batch.hideColumns("Unit ID");
									tblTCT_Batch.hideColumns("Batch No");
								}
								{
									rowHeaderTCT_Batch = tblTCT_Batch.getRowHeader();
									rowHeaderTCT_Batch.setModel(new DefaultListModel());
									scrollTCT_Batch.setRowHeaderView(rowHeaderTCT_Batch);
									scrollTCT_Batch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
							//displayTCostClient(null);	//---removed by jed 2018-10-16: DCRF no. 797 to put control on tagging of particulars in TCOST to avoid double tagging---//
						}
						{
							pnlDetail = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlDetail, BorderLayout.EAST);
							pnlDetail.setPreferredSize(new Dimension(400, 335));
							{
								pnlSearch = new JPanel(new BorderLayout(5, 5));
								pnlDetail.add(pnlSearch, BorderLayout.NORTH);
								pnlSearch.setBorder(JTBorderFactory.createTitleBorder("Search Client"));
								pnlSearch.setPreferredSize(new Dimension(450, 95));
								{
									JPanel pnlUploading = new JPanel(new GridLayout(2, 1, 10, 0));
									pnlSearch.add(pnlUploading, BorderLayout.WEST);
									{
										txtSearch = new JXTextField();
										pnlUploading.add(txtSearch);
										txtSearch.setPreferredSize(new java.awt.Dimension(383, 0));
										txtSearch.setHorizontalAlignment(JTextField.CENTER);	
										txtSearch.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent e) {	
												checkAllClientList();
											}				 
										});	
									}
									{
										JLabel lblDateTo = new JLabel("*Search Client Name");
										pnlUploading.add(lblDateTo);
									}
								}
							}
							{
								pnlInfo = new JPanel(new BorderLayout(5, 5));
								pnlDetail.add(pnlInfo, BorderLayout.CENTER);
								pnlInfo.setBorder(JTBorderFactory.createTitleBorder("Detail INFO"));
								pnlInfo.setPreferredSize(new Dimension(400, 205));
								{
									JPanel pnlLabel = new JPanel(new GridLayout(5, 0, 5, 5));
									pnlInfo.add(pnlLabel, BorderLayout.WEST);
									{
										lblInfo = new JLabel("Code");
										pnlLabel.add(lblInfo);
										lblInfo.setHorizontalAlignment(JLabel.RIGHT);

										lblDate = new JLabel("Date");
										pnlLabel.add(lblDate);
										lblDate.setHorizontalAlignment(JLabel.RIGHT);

										lblAmount = new JLabel("Amount");
										pnlLabel.add(lblAmount);
										lblAmount.setHorizontalAlignment(JLabel.RIGHT);


										lblReqBy = new JLabel("Request By");
										pnlLabel.add(lblReqBy);
										lblReqBy.setHorizontalAlignment(JLabel.RIGHT);

										lblRemarks = new JLabel("Remarks");
										pnlLabel.add(lblRemarks);
										lblRemarks.setHorizontalAlignment(JLabel.RIGHT);
									}

									JPanel pnlText = new JPanel(new GridLayout(5, 0, 5, 5));
									pnlInfo.add(pnlText, BorderLayout.CENTER);
									{

										{
											JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
											pnlText.add(pnlLookup);
											{
												{
													lookupCode_Batch = new _JLookup(null, "Code");
													pnlLookup.add(lookupCode_Batch, BorderLayout.WEST);
													lookupCode_Batch.setPreferredSize(new Dimension(50, 20));
													lookupCode_Batch.setReturnColumn(0);
													//lookupCode_Batch.setEnabled(false);
													lookupCode_Batch.setFilterName(true);
													lookupCode_Batch.setLookupSQL(SQL_TCOST(entity));
													lookupCode_Batch.addActionListener(this);
													lookupCode_Batch.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															String emp_code = (String) UserInfo.EmployeeCode;
															String emp_name = (String) UserInfo.FullName;
															String tcostid_dl = lookupCode_Batch.getValue(); 
															if (data != null) {
																displayTCostClient(null, tcostid_dl);
																
																lblCode_Batch.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
																lblSetupAmount1_Batch.setText(String.format("%s", data[2]));
																lblBalanceAmount_Batch.setText(String.format("%s", lblSetupAmount1_Batch.getText()));
																txtRemarks_Batch.setText(String.format("%s", data[3]));
																txtAmount_Batch.setText(String.format("%s", data[2]));
																txtAmount_Batch.setValue((BigDecimal) data[2]);
																txtRequestedBy_Batch.setText(emp_code);
																lblReqBy1_Batch.setText(String.format("[ %s ]", emp_name));

																btnDRF.setEnabled(false);
																btnTSave.setEnabled(false);
																String batch_no = lblBatch.getText().replace("<html><b>Batch No:</html>", "");
																System.out.println(batch_no);
																
																//if(batch_no.equals(null) || batch_no.equals("")){
																//	btnTSave.setEnabled(false);
																//}else{
																//	btnTSave.setEnabled(true);
																//}
																
																txtRemarks_Batch.setEditable(true);
																txtAmount_Batch.setEditable(true);

																//displayTCostClient(lookupCode_Batch.getValue());
																//lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo2()));

																KEYBOARD_MANAGER.focusNextComponent();
															}
														}
													});
													{
														lblCode_Batch = new JLabel("[ ]");
														pnlLookup.add(lblCode_Batch,BorderLayout.CENTER);
														lblCode_Batch.setHorizontalAlignment(JLabel.LEFT);
													}
												}
												{
													dateSched_Batch = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
													pnlText.add(dateSched_Batch);
													dateSched_Batch.setDate(null);
													dateSched_Batch.setEnabled(false);
													dateSched_Batch.setDateFormatString("yyyy-MM-dd");
													((JTextFieldDateEditor)dateSched_Batch.getDateEditor()).setEditable(false);
													dateSched_Batch.setDate(Calendar.getInstance().getTime());
													dateSched_Batch.addDateListener(new DateListener() {
														public void datePerformed(DateEvent event) {
															int row = tblTCT_Batch.convertRowIndexToModel(tblTCT_Batch.getSelectedRow());
															modelTCT_Batch.setValueAt(dateSched_Batch.getDate(), row, 2);

														}
													});
												}
												{
													txtAmount_Batch = new _JXFormattedTextField("0.00");
													pnlText.add(txtAmount_Batch);
													txtAmount_Batch.setHorizontalAlignment(JLabel.RIGHT);
													txtAmount_Batch.setBounds(215, 10, 280, 22);
													txtAmount_Batch.setEditable(false);
													//txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtAmount_Batch.addKeyListener(new KeyAdapter() {
														public void keyReleased(KeyEvent arg0) {
															BigDecimal value1 = new BigDecimal (lblSetupAmount1_Batch.getText());
															BigDecimal value2 = new BigDecimal (lblRunningTotal1_Batch.getText());

															int row = tblTCT_Batch.convertRowIndexToModel(tblTCT_Batch.getSelectedRow());

															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
																lblBalanceAmount_Batch.setText(String.format("%s", value1.subtract(value2)));

																modelTCT_Batch.setValueAt(value2, row, 3);
																//computeTotal();
															}
														}
													});
													txtAmount_Batch.getDocument().addDocumentListener(new DocumentListener() {
														public void insertUpdate(DocumentEvent e) {
															lblRunningTotal1_Batch.setText(txtAmount_Batch.getText());
														}
														public void removeUpdate(DocumentEvent e) {
															lblRunningTotal1_Batch.setText(txtAmount_Batch.getText());
														}
														public void changedUpdate(DocumentEvent e) {

														}
													});
												}

												JPanel pnlReqBy = new JPanel(new BorderLayout(3, 3));
												pnlText.add(pnlReqBy);
												{
													{
														txtRequestedBy_Batch = new JXTextField();
														pnlReqBy.add(txtRequestedBy_Batch, BorderLayout.WEST);
														txtRequestedBy_Batch.setEditable(false);
														txtRequestedBy_Batch.setPreferredSize(new Dimension(100, 20));
														{
															lblReqBy1_Batch = new JLabel("[ ]");
															pnlReqBy.add(lblReqBy1_Batch,BorderLayout.CENTER);
															lblReqBy1_Batch.setHorizontalAlignment(JLabel.LEFT);
														}
													}
												}
												{
													txtRemarks_Batch = new JXTextField();
													pnlText.add(txtRemarks_Batch);
													txtRemarks_Batch.setHorizontalAlignment(JLabel.LEFT);
													txtRemarks_Batch.setBounds(215, 10, 280, 22);
													txtRemarks_Batch.setEditable(false);
												}
											}
										}
									}
								}
							}
							{
								pnlAmount = new JPanel(new BorderLayout(5, 5));
								pnlDetail.add(pnlAmount, BorderLayout.SOUTH);
								pnlAmount.setBorder(JTBorderFactory.createTitleBorder("Detail AMOUNT"));
								pnlAmount.setPreferredSize(new Dimension(450, 95));
								{
									JPanel pnlLabelTotal1 = new JPanel(new GridLayout(4, 2, 3, 3));
									pnlAmount.add(pnlLabelTotal1, BorderLayout.WEST);
									{
										JLabel lblSetupAmount2 = new JLabel("                     ");
										pnlLabelTotal1.add(lblSetupAmount2, BorderLayout.WEST);
									}
								}
								{
									JPanel pnlLabelTotal = new JPanel(new GridLayout(4, 2, 3, 3));
									pnlAmount.add(pnlLabelTotal, BorderLayout.CENTER);
									{
										JLabel lblSetupAmount = new JLabel("Setup Amount :");
										pnlLabelTotal.add(lblSetupAmount);
										{
											lblSetupAmount1_Batch = new JLabel("0.00");
											pnlLabelTotal.add(lblSetupAmount1_Batch);
										}
									}
									{
										JLabel lblRunningTotal = new JLabel("Running Total  :");
										pnlLabelTotal.add(lblRunningTotal);
										{
											lblRunningTotal1_Batch = new JLabel("0.00");
											pnlLabelTotal.add(lblRunningTotal1_Batch);
										}
									}
									{
										JLabel lblBalance = new JLabel("Balance +/(-)   :");
										pnlLabelTotal.add(lblBalance);
										{
											lblBalanceAmount_Batch = new JLabel("0.00");
											pnlLabelTotal.add(lblBalanceAmount_Batch);
										}
									}
								}
							}
						}
					}
//					tabCenter.addChangeListener(new ChangeListener() {
//						public void stateChanged(ChangeEvent arg0) {
//							int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();
//
//							if(selectedTab == 0){
//								lblBatch.setText("<html><b>Batch No:</html>");
//								btnDRF.setEnabled(false);
//								btnTSave.setEnabled(false);
//								btnBatch.setEnabled(false);
//							}
//							if(selectedTab == 1){
//								lblBatch.setText("<html><b>Batch No:</html>");
//								btnDRF.setEnabled(false);
//								btnTSave.setEnabled(false);
//								btnBatch.setEnabled(true);
//							}
//						}
//					});
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				pnlSouth.setPreferredSize(new Dimension(700, 35));// XXX
				{
					lblBatch = new JLabel("<html><b>Batch No:</html>");
					pnlSouth.add(lblBatch);
				}
				{
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnDRF = new JButton("<html><center><font size = 2>Create Disbursement\n Request</html>");
					pnlSouth.add(btnDRF);
					btnDRF.setActionCommand("Create Disbursement Request");
					btnDRF.addActionListener(this);
					btnDRF.setEnabled(false);
				}
				{
					btnTSave = new JButton("Save");
					pnlSouth.add(btnTSave);
					btnTSave.setActionCommand("Save");
					btnTSave.addActionListener(this);
					btnTSave.setEnabled(false);
				}
				{
					btnBatch = new JButton("Batch w/ out RPLF");
					pnlSouth.add(btnBatch);
					btnBatch.setActionCommand("Batch w/ out RPLF");
					btnBatch.addActionListener(this);
					btnBatch.setEnabled(true);
					//btnDelete.setEnabled(false);
				}
				{
					btnCancel = new JButton ("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
	}
	
	private void refreshBatch() {

		if (lookupCode_Batch.getText().equals("")) {
			modelTCT_Batch.clear();
			scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
			tblTCT_Batch.packAll();
			
			lblCode_Batch.setText("[ ]");
			dateSched_Batch.setDate(Calendar.getInstance().getTime());
			txtAmount_Batch.setText("0.00");
			txtRequestedBy_Batch.setText(null);
			txtRequestedBy_Batch.setEditable(false);
			lblReqBy1_Batch.setText("[ ]");
			txtRemarks_Batch.setText(null);
			btnDRF.setEnabled(false);
			btnTSave.setEnabled(false);
			//btnBatch.setEnabled(false);
			modelTCT_Batch.setRowCount(0);
			//displayTCostClient(lookupCode_Batch.getValue());
			lblBatch.setText("<html><b>Batch No: </html>");
		}
	} // refreshTO()
	private static String SQL_TCOST(String entity) {
		return "SELECT tcostdtl_id as \"TCost ID.\", trim(tcostdtl_desc) as \"TCost Desc\", tcostdtl_amt as \"TCost Amount\", remarks as \"Remarks\"\n" + 
				"FROM mf_transfer_cost_dl\n" + 
				"WHERE status_id = 'A'\n" +
				//"AND tcostdtl_id NOT IN (SELECT tcostid_dl FROM rf_transfer_cost WHERE entity_id = '"+entity+"')\n" +
				"AND CASE WHEN COALESCE(tcostdtl_amt, 0.00) = 0 THEN tcostdtl_id NOT IN (SELECT tcostid_dl FROM rf_transfer_cost WHERE entity_id = '"+entity+"' and status_id = 'A') ELSE TRUE END\n" +
				"GROUP BY tcostdtl_id, tcostdtl_desc, tcostdtl_amt, remarks\n" + 
				"ORDER BY tcostdtl_desc ASC;";
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Create Disbursement Request")){
			if(tabCenter.getSelectedIndex() == 0){
				/*BigDecimal amount_to_save = (BigDecimal) txtAmount_Batch.getValued();
				
				if(amount_to_save.compareTo(new BigDecimal("0.00")) == 0){
					JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
				}else{*/
					JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
				//}
			}
		}
		if(actionCommand.equals("Create RPLF")){

			if (tabCenter.getSelectedIndex() == 0) {

				int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {

					String batchno = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
					//String rplf_no = rplfNo();
					//for(int x = 0; x < tblPCD_Batch.getRowCount(); x++){
					//Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
					String emp_code = (String) UserInfo.EmployeeCode;
					String request_type = (String) lookupRequestType.getText();
					String availer = (String) lookupAvailer.getText();
					String availer_type = (String) lookupAvailerType.getText();


					//if(isSelected){

					String SQL = "SELECT sp_save_transfer_cost_rplf('"+emp_code+"', '"+batchno+"', '"+request_type+"', '"+availer+"', '"+availer_type+"')";

					pgSelect db = new pgSelect();
					FncSystem.out("SQL", SQL);
					db.select(SQL);
					//}
					//}
					JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF", JOptionPane.INFORMATION_MESSAGE);
					lblBatch.setText("<html><b>Batch No:</html>");
					//pnlState(true, true);

					modelTCT_Batch.setRowCount(0);
					//displayTCostClient(lookupCode_Batch.getValue());

					Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
					optionPaneWindow.dispose();

					Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
					if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
						optionPaneWindowMain.getFocusOwner();
						lookupCode_Batch.setText("");
						refreshBatch();
					}
				}
			}
		}
		
		if(actionCommand.equals("Save")){
			if (tabCenter.getSelectedIndex() == 0 ) {
				if(lookupCode_Batch.getText().equals(null) || lookupCode_Batch.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select a particular.", "Save", JOptionPane.ERROR_MESSAGE);	
				}else{

					BigDecimal amount_to_save = (BigDecimal) txtAmount_Batch.getValued();

					if(amount_to_save.compareTo(new BigDecimal("0.00")) == 0){
						JOptionPane.showMessageDialog(getContentPane(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
					}else{
						if(JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?", "Confirmation", 
								JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){
							String rplf_no = "";
							for(int x = 0; x < tblTCT_Batch.getRowCount(); x++){
								Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
								Date trans_date = (Date) dateSched_Batch.getDate();
								BigDecimal amount = (BigDecimal) txtAmount_Batch.getValued(); //new BigDecimal (txtAmount_Batch.getText());
								String remarks = (String) txtRemarks_Batch.getText();
								//String desc = (String) lblCode_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
								String entity_id = (String) modelTCT_Batch.getValueAt(x, 1);
								String unit_id = (String) modelTCT_Batch.getValueAt(x, 9);
								String projcode = (String) modelTCT_Batch.getValueAt(x, 7);
								String pbl_id = (String) modelTCT_Batch.getValueAt(x, 4);
								Integer seq_no = (Integer) modelTCT_Batch.getValueAt(x, 5);
								String costid = (String) lookupCode_Batch.getValue();
								BigDecimal setup_amount = (BigDecimal) txtAmount_Batch.getValued(); //new BigDecimal (txtAmount_Batch.getText());
								String req_id = (String) txtRequestedBy_Batch.getText();
								String emp_code = (String) UserInfo.EmployeeCode;
								//String request_type = (String) lookupRequestType.getText();
								String availer = (String) lookupAvailer.getText();
								//String availer_type = (String) lookupAvailerType.getText();
								String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");



								if(isSelected){

									String SQL = "SELECT sp_save_transfer_cost_woutrplf('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
											+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
											+ ""+seq_no+", '"+availer+"', '"+rplf_no+"')";

									pgSelect db = new pgSelect();
									FncSystem.out("SQL", SQL);
									db.select(SQL);
								}

							}

							JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
							//modelTCT_Batch.setRowCount(0);

							//-------added by jed 9-4-18 to avoid multiple saving
							
							modelTCT_Batch.clear();
							scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
							tblTCT_Batch.packAll();

							//pnlState(true, true);	
							btnTSave.setEnabled(false);
							btnDRF.setEnabled(true);
							lookupCode_Batch.setText("");
							lblCode_Batch.setText("[ ]");
							lblSetupAmount1_Batch.setText("0.00");
							lblBalanceAmount_Batch.setText("0.00");
							txtRemarks_Batch.setText("");
							txtAmount_Batch.setValue(0.00);
							txtRequestedBy_Batch.setText("");
							lblReqBy1_Batch.setText("[ ]");
						}
					}
				}
			}
			//}
		}
		if(actionCommand.equals("Cancel RPLF")){
			lookupRequestType.setValue(null);
			lblRequestType.setText("[ ]");
			lookupAvailer.setValue(null);
			lblAvailer.setText("[ ]");
			lookupAvailerType.setValue(null);
			lblAvailerType.setText("[ ] ");
		}
		if(actionCommand.equals("Batch w/ out RPLF")){
			getBatchList();
			btnTSave.setEnabled(true); //ADDED BY LESTER 2016-12-19
			btnDRF.setEnabled(true); //ADDED BY LESTER 2016-12-19
			//CHANGE DISPLAY OF PCOST DETAILS HERE BASED ON retrieved batch no
			
			//------added by jed 9-4-18 due to multiple saving
			
			modelTCT_Batch.clear();
			scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
			tblTCT_Batch.packAll();
			
			//pnlState(false, true);
			//displayTCostClient(null);
			btnTSave.setEnabled(false);
			lookupCode_Batch.setText("");
			lblCode_Batch.setText("[ ]");
			lblSetupAmount1_Batch.setText("0.00");
			lblBalanceAmount_Batch.setText("0.00");
			txtRemarks_Batch.setText("");
			txtAmount_Batch.setValue(0.00);
			txtRequestedBy_Batch.setText("");
			lblReqBy1_Batch.setText("[ ]");
		}
		
		if (actionCommand.equals("Cancel")) { cancel();
		
		}
	}

	//---added by jed 2018-10-15 : for refreshing all fields---//
	private void cancel(){
		
		if (tabCenter.getSelectedIndex() == 0){
			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
				
				modelTCT_Batch.clear();
				scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
				tblTCT_Batch.packAll();
				
				lookupCode_Batch.setText("");
				lblCode_Batch.setText("[ ]");
				dateSched_Batch.setDate(Calendar.getInstance().getTime());
				txtAmount_Batch.setText("0.00");
				txtRequestedBy_Batch.setText(null);
				txtRequestedBy_Batch.setEditable(false);
				lblReqBy1_Batch.setText("[ ]");
				txtRemarks_Batch.setText(null);
				btnDRF.setEnabled(false);
				btnTSave.setEnabled(false);
				btnBatch.setEnabled(true);
				modelTCT_Batch.setRowCount(0);
				//displayTCostClient(lookupCode_Batch.getValue());
				lblBatch.setText("<html><b>Batch No:</html>");
				lblSetupAmount1_Batch.setText("0.00");
				lblRunningTotal1_Batch.setText("0.00");
				lblBalanceAmount_Batch.setText("0.00");
				//pnlState(true, true);
			}
		}
	}
	
	public void displayTCostDetails() {

		if (tabCenter.getSelectedIndex() == 0) {
			FncTables.clearTable(modelTCT);//Code to clear modelMain.		
			DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.

			String sql = "SELECT false, trim(b.tcostdtl_desc), a.tran_date, a.tcost_amt, a.remarks, a.rplf_no, null, null, a.batch_no \n" + 
					"FROM rf_transfer_cost a \n" + 
					"LEFT JOIN mf_transfer_cost_dl b ON a.tcostid_dl = b.tcostdtl_id  \n" +
					"WHERE a.status_id = 'A'\n" +
					"AND a.entity_id = '"+lookupClient.getValue()+"'\n" +
					"AND a.pbl_id = '"+clientDetails[4]+"' \n" +
					"ORDER BY b.tcostdtl_desc\n" +
					"--group by b.tcostdtl_desc, a.tran_date, a.tcost_amt, a.remarks, a.rplf_no, a.tcostid_dl;";
			
			FncSystem.out("Display TCOST details", sql);
			pgSelect db = new pgSelect();
			db.select(sql);

			if(db.isNotNull()){ 
				for(int x=0; x < db.getRowCount(); x++){
					modelTCT.addRow(db.getResult()[x]);
					listModel.addElement(modelTCT.getRowCount());
				}
				scrollTCT.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT.getRowCount())));
			}
			BigDecimal total = new BigDecimal(0.00);
			for(int y=0; y < modelTCT.getRowCount(); y++){
				total = total.add((BigDecimal) modelTCT.getValueAt(y, 3));
				modelTCTTotal.setValueAt(total, 0, 3);
			}
			tblTCT.packAll();
		}

	}
	private void displayTCostClient(String code_id, String tcostid_dl) { //--- 1st modified by JED 2018-09-20 : for displaying clients with two lots---//
																		//---2nd modified by JED 2018-10-16 : DCRF no. 797 put control on tagging particulars in TCOST to avoid double tagging---//

		FncTables.clearTable(modelTCT_Batch);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.

//		String sql = "SELECT false, btrim(a.entity_id::text)::character varying AS \"Client ID\",\n" + 
//				"    btrim(b.entity_name::text) AS \"Client Name\",\n" + 
//				"    format('%s-%s-%s'::text, btrim(c.phase::text), btrim(c.block::text), btrim(c.lot::text))::character varying AS \"Ph-Bl-Lt\",\n" + 
//				"    btrim(a.pbl_id::text)::character varying AS \"PBL ID\",\n" + 
//				"    a.seq_no AS \"Sequence\",\n" + 
//				"    btrim(e.type_card_display::text) AS \"BuyerType\",\n" + 
//				"    btrim(a.projcode::text)::character varying AS \"Project ID\",\n" + 
//				"    btrim(d.proj_name::text) AS \"Project Name\", a.unit_id AS \"Unit ID\", "
//				//+ "  m.batch_no AS \"Batch No\" \n" + 
//				+ " '' AS \"Batch No\" \n" + 
//				"   FROM rf_sold_unit a\n" + 
//				"     LEFT JOIN rf_entity b ON b.entity_id::text = a.entity_id::text\n" + 
//				"     LEFT JOIN mf_unit_info c ON c.proj_id::text = a.projcode::text AND c.pbl_id::text = a.pbl_id::text\n" + 
//				"     LEFT JOIN mf_project d ON d.proj_id::text = a.projcode::text\n" + 
//				"     LEFT JOIN mf_buyer_type e ON e.type_id::text = a.buyertype::text\n" + 
//				//"	  LEFT JOIN rf_processing " +
//				//"	  LEFT JOIN rf_client_notices m ON m.entity_id = a.entity_id AND m.projcode = a.projcode AND m.pbl_id = a.pbl_id AND m.seq_no = a.seq_no AND m.status_id = 'A'\n" +
//				"  WHERE a.currentstatus IS NOT NULL\n" + 
//				" AND (CASE WHEN NULLIF('"+code_id+"', 'null') is null then true \n" + 
//				"	    else (case when (select COALESCE(tcostdtl_amt, 0) = 0 from mf_transfer_cost_dl where tcostdtl_id = '"+code_id+"') then not exists (select * \n" + 
//				"														    FROM rf_transfer_cost\n" + 
//				"														    where entity_id = a.entity_id \n" + 
//				"														    and projcode = a.projcode \n" + 
//				"														    and pbl_id = a.pbl_id \n" + 
//				"														    and seq_no = a.seq_no \n" + 
//				"														    and tcostid_dl = '"+code_id+"'\n" + 
//				"														    AND status_id = 'A') ELSE TRUE END) END) \n"+
//				//"  AND a.status_id = 'A'\n" + 
//				//"  AND b.entity_id NOT IN (select entity_id from rf_processing_cost where pcostid_dl = '"+lookupCode_Batch.getValue()+"' and status_id = 'A')\n" +
//				"ORDER BY getinteger(c.phase)::INT, c.block::INT, c.lot::INT";
//		//"  ORDER BY btrim(b.entity_name::text), getinteger(c.phase::text), gettext(c.phase::text), getinteger(c.block::text), gettext(c.block::text), getinteger(c.lot::text), gettext(c.lot::text), a.seq_no;";
		
		
		String sql =
				"SELECT * from view_tcost_detail_retag (NULLIF ('"+code_id+"' , 'null'), NULLIF ('"+tcostid_dl+"' , 'null'))" ;

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display SQL For TCost", sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelTCT_Batch.addRow(db.getResult()[x]);
				listModel.addElement(modelTCT_Batch.getRowCount());
			}	
		}
		scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
		tblTCT_Batch.packAll();
	}
	private static String SQL_REQUESTTYPE() {
		return "SELECT rplf_type_id as \"Type ID\", trim(rplf_type_desc) as \"Description\"  \n" +
				"FROM mf_rplf_type where status_id = 'A' " +
				"ORDER BY rplf_type_id ";		
	}
	private static String SQL_AVAILER() {
		return "SELECT trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\", entity_kind as \"Kind\", vat_registered as \"VAT\"  \n" +
				"FROM (select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in \r\n" + 
				"(select entity_id from rf_entity_assigned_type " +
				" )) a \n" +
				"ORDER BY a.entity_name  ";		
	}
	private static String SQL_AVAILERTYPE(String entity_id) {
		return "SELECT a.entity_type_id, trim(b.entity_type_desc), c.wtax_id, c.wtax_rate \n" + 
				"FROM (select * from rf_entity_assigned_type where trim(entity_id) =  '"+entity_id+"' ) a \r\n" + 
				"LEFT JOIN mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n" + 		
				"LEFT JOIN rf_withholding_tax c on b.wtax_id = c.wtax_id"  ;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblTCT_Batch)) {
			int row = tblTCT_Batch.convertRowIndexToModel(tblTCT_Batch.getSelectedRow());
			Boolean isSelected2 = (Boolean) modelTCT_Batch.getValueAt(row, 0);

			for(int x = 0; x < modelTCT_Batch.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
				if (isSelected) {
					
					//----if batch statement added by jed 9-4-18 to for tagging of clients in existing batch no----//
					
					if(batch() == false){
					//lookupCode_Batch.setEnabled(true);
					lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
					//pnlState (false, true);
					btnTSave.setEnabled(true);
					}
				}else{
					//lookupCode_Batch.setEnabled(false);
					//pnlState (false, true);
					btnTSave.setEnabled(true);
				}
			}

			if (isSelected2) {
				//lookupCode_Batch.setEnabled(true);
			}
		}
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	private void checkAllClientList(){//

		int rw = tblTCT_Batch.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";

			try { name	= tblTCT_Batch.getValueAt(x,2).toString().toUpperCase();}
			catch (NullPointerException e) { name	= ""; }

			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			Boolean	end			= name.endsWith(acct_name);

			if (match==true||start==true||end==true) {
				tblTCT_Batch.setRowSelectionInterval(x, x); 
				tblTCT_Batch.changeSelection(x, 2, false, false);				
				break;			
			}
			else {
				tblTCT_Batch.setRowSelectionInterval(0, 0); 
				tblTCT_Batch.changeSelection(0, 2, false, false);					
			}

			x++;

		}		
	}
	private static String generateBatchNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(batch_no::INT), 0) + 1, 'FM0000000000') FROM rf_transfer_cost;";

		FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	private void getBatchList() {//XXX Preview Report

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Units", SQL_BatchList(), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();
		String batch = (String) data[0];
		lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch));
		}
	private static String SQL_BatchList() {
		String strSQL = "SELECT batch_no as \"Batch No\", sum(setup_amt) as \"Amount\"\n" +
				"FROM rf_transfer_cost\n" + 
				"WHERE created_by = '"+ UserInfo.EmployeeCode +"'\n" + 
				"AND rplf_no = '' \n" + 
				"AND status_id = 'A' \n" +
				"GROUP BY batch_no";

		FncSystem.out("Batch No", strSQL);
		return strSQL;
	}
	private boolean batch() {

		boolean x = false;
		String batch = "";

		String strSQL = "select batch_no from rf_transfer_cost where status_id = 'A' and batch_no = '"+lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "")+"' and rplf_no is not null and created_by = '"+ UserInfo.EmployeeCode +"'";

		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			batch = (String) db.getResult()[0][0];
		}

		if (batch.equals(null) || batch.isEmpty()) {
			x=false;
			System.out.println(x);
		} else {x=true;}

		return x;
	}
	
//	private static void pnlState (Boolean IND, Boolean BATCH){
//		
//		tabCenter.setEnabledAt(0, IND);
//		tabCenter.setEnabledAt(1, BATCH);
//		
//	}
}
