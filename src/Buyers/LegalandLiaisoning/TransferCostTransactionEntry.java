package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.LookupTable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import Buyers.LegalandLiaisoning.ProcessingCostTransactionEntry.PopupTriggerListener_panel;
import Database.pgSelect;
import Database.pgUpdate;
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

public class TransferCostTransactionEntry extends _JInternalFrame implements _GUI, MouseListener {

	/*
	 * CHANGES AS OF 2021-09-20
	 * 
	 * 1. SAVING - IMPROVE SAVING TO AVOID SAME BATCHING 2021-08-20 2. FUNCTION -
	 * SAVING OF BATCH NUMBER TO TEMP TABLE 2021-08-20 3. DCRF NO. 1726 - AUTO
	 * TAGGING OF TRANSFER TAX WHEN DOCSTAMP SALE IS TAG 2021-09-20
	 */

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
	JLabel lblctrNo;

	_JXFormattedTextField txtAmount;
	JXTextField txtRemarks;
	JXTextField txtRemarks_Batch;
	_JXFormattedTextField txtAmount_Batch;
	JXTextField txtSearch;
	JXTextField txtRequestedBy;
	JXTextField txtRequestedBy_Batch;

	// JButton btnClear;
	// JButton btnClear2;
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
	_JLookup lookupControlNo;
	_JLookup lookupAvailer;
	_JLookup lookupAvailerType;
	_JLookup lookupCode_Batch;

	_JDateChooser dateSched;
	_JDateChooser dateSched_Batch;

	private static JTabbedPane tabCenter;
	private JMenuItem mniDelete;
	private JPopupMenu menu;

	_JScrollPaneTotal scrollTCTTotal;
	_JTableTotal tblTCTTotal;
	model_tcost modelTCTTotal;

	Object[] clientDetails;

	model_tcost modelTCT;
	_JScrollPaneMain scrollTCT;
	_JTableMain tblTCT;
	JList rowHeaderTCT;

	model_client modelTCT_Batch;
	// _JScrollPaneMain scrollTCT_Batch;
	JScrollPane scrollTCT_Batch;
	_JTableMain tblTCT_Batch;
	JList rowHeaderTCT_Batch;

	static String title = "Transfer Cost Transaction Entry";
	Dimension frameSize = new Dimension(900, 600);// 510, 250
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
	private String pbl_id;
	private Integer seq;

	private String to_do = "";

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	DecimalFormat df = new DecimalFormat("#,###,##0.00");

	private JButton btnCancel;

	private _JLookup lookupcompany;

	private JTextField txtcompany;

	private _JLookup lookupproject;

	private JTextField txtproj;

	protected String emp_code;

	protected String emp_name;

	protected String tcostid_dl;

	public static String co_id;

	public static String company;

	public TransferCostTransactionEntry() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TransferCostTransactionEntry(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public TransferCostTransactionEntry(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
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
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			{
				menu = new JPopupMenu("Popup");
				mniDelete = new JMenuItem("Delete");
				menu.add(mniDelete);
				mniDelete.setEnabled(false);
				mniDelete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						delete();

					}
				});
			}
			{
				pnlCreateRPLF = new JPanel(new BorderLayout(5, 5));
				pnlCreateRPLF.setPreferredSize(new java.awt.Dimension(500, 125));

				{
					JPanel pnlCRPLFWest = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlCreateRPLF.add(pnlCRPLFWest, BorderLayout.WEST);
					{

						JLabel lblReqType = new JLabel("Request Type:");
						pnlCRPLFWest.add(lblReqType);

						JLabel lblControlNo = new JLabel("Control No.:");
						pnlCRPLFWest.add(lblControlNo);

						JLabel avail = new JLabel("Availer:");
						pnlCRPLFWest.add(avail);

						JLabel availType = new JLabel("Availer Type:");
						pnlCRPLFWest.add(availType);

					}
				}
				{
					JPanel pnlCRPLFCenter = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlCreateRPLF.add(pnlCRPLFCenter, BorderLayout.CENTER);
					{
						JPanel pnlRequestType = new JPanel(new BorderLayout(5, 5));
						pnlCRPLFCenter.add(pnlRequestType);
						{
							JPanel pnllookRequestType = new JPanel(new BorderLayout(5, 5));
							pnlRequestType.add(pnllookRequestType, BorderLayout.WEST);
							pnllookRequestType.setPreferredSize(new Dimension(100, 0));
							{
								lookupRequestType = new _JLookup(null, "Request Type");
								pnllookRequestType.add(lookupRequestType);
								lookupRequestType.setReturnColumn(0);
								lookupRequestType.setLookupSQL(SQL_REQUESTTYPE());
								lookupRequestType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										String typeId = (String) data[0];
										if (data != null) {

											lblRequestType.setText(String.format("[ %s ]", (String) data[1]));

											KEYBOARD_MANAGER.focusNextComponent();
										}
										if (typeId.equals("06")) {
											lookupControlNo.setEnabled(true);

										} else {
											lookupControlNo.setEnabled(false);
											lookupControlNo.setText("");
											lblctrNo.setText("[ ]");
										}
									}
								});
							}
						}
						{
							JPanel pnllblRequest = new JPanel(new BorderLayout(5, 5));
							pnlRequestType.add(pnllblRequest, BorderLayout.CENTER);

							{
								lblRequestType = new JLabel("[ ]");
								pnllblRequest.add(lblRequestType);
								lblRequestType.setHorizontalAlignment(JLabel.LEFT);

							}
						}
					}
					{
						JPanel pnlControlNo = new JPanel(new BorderLayout(5, 5));
						pnlCRPLFCenter.add(pnlControlNo);
						{
							JPanel pnllookControl = new JPanel(new BorderLayout(5, 5));
							pnlControlNo.add(pnllookControl, BorderLayout.WEST);
							pnllookControl.setPreferredSize(new Dimension(100, 0));
							{
								lookupControlNo = new _JLookup(null, "Control No.");
								pnllookControl.add(lookupControlNo);
								lookupControlNo.setReturnColumn(0);
								;
								lookupControlNo.setEnabled(false);
								lookupControlNo.setLookupSQL(ControlNoLookUpValue());
								lookupControlNo.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											lblctrNo.setText(String.format("[%s]", data[0]));
										}

									}
								});

							}
						}
						{
							JPanel pnllblControlNo = new JPanel(new BorderLayout(5, 5));
							pnlControlNo.add(pnllblControlNo, BorderLayout.CENTER);
							{
								lblctrNo = new JLabel("[ ]");
								pnllblControlNo.add(lblctrNo);
							}
						}
					}
					{
						JPanel pnlAvailer = new JPanel(new BorderLayout(5, 5));
						pnlCRPLFCenter.add(pnlAvailer);
						{
							JPanel pnllookAvailer = new JPanel(new BorderLayout(5, 5));
							pnlAvailer.add(pnllookAvailer, BorderLayout.WEST);
							pnllookAvailer.setPreferredSize(new Dimension(100, 0));
							{
								lookupAvailer = new _JLookup(null, "Availer");
								pnllookAvailer.add(lookupAvailer);
								lookupAvailer.setReturnColumn(0);
								lookupAvailer.setLookupSQL(SQL_AVAILER());
								lookupAvailer.setFilterName(true);
								lookupAvailer.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										FncSystem.out("Display SQL for Availer", lookupAvailer.getLookupSQL());
										if (data != null) {

											lookupAvailerType.setText("");
											lblAvailerType.setText("");
											lblAvailer.setText(String.format("[ %s ]", (String) data[1]));
											lookupAvailerType.setLookupSQL(SQL_AVAILERTYPE((String) data[0]));

											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
						}
						{
							JPanel pnllblAvailer = new JPanel(new BorderLayout(5, 5));
							pnlAvailer.add(pnllblAvailer, BorderLayout.CENTER);
							pnllblAvailer.setPreferredSize(new Dimension(100, 0));
							{

								lblAvailer = new JLabel("[ ]");
								pnllblAvailer.add(lblAvailer);
							}

						}
					}
					{
						JPanel pnlavailerType = new JPanel(new BorderLayout(5, 5));
						pnlCRPLFCenter.add(pnlavailerType);
						{
							JPanel pnllookAvailerType = new JPanel(new BorderLayout(5, 5));
							pnlavailerType.add(pnllookAvailerType, BorderLayout.WEST);
							pnllookAvailerType.setPreferredSize(new Dimension(100, 0));
							{
								lookupAvailerType = new _JLookup(null, "Availer Type");
								pnllookAvailerType.add(lookupAvailerType);
								lookupAvailerType.setReturnColumn(0);
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
						}
						{
							JPanel pnllblAvailerType = new JPanel(new BorderLayout(5, 5));
							pnlavailerType.add(pnllblAvailerType, BorderLayout.CENTER);
							{

								lblAvailerType = new JLabel("[ ]");
								pnllblAvailerType.add(lblAvailerType);
							}
						}

					}
				}
				{
					JPanel pnlCRPLFSouth = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlCreateRPLF.add(pnlCRPLFSouth, BorderLayout.SOUTH);
					pnlCRPLFSouth.setPreferredSize(new Dimension(0, 30));

					{
						{

							pnlCRPLFSouth.add(Box.createHorizontalBox());
						}

						{
							btnSave = new JButton("Create RPLF");
							pnlCRPLFSouth.add(btnSave);
							btnSave.setActionCommand("Create RPLF");
							btnSave.addActionListener(this);
						}
						{
							btnCancelRPLF = new JButton("Cancel");
							pnlCRPLFSouth.add(btnCancelRPLF);
							btnCancelRPLF.setActionCommand("Cancel RPLF");
							btnCancelRPLF.addActionListener(this);
						}

					}

				}
			}

			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.NORTH);
				{
					pnlBuyer = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Buyer Cost - Individual", null, pnlBuyer, null); // XXX Buyer Cost - Individual
					pnlBuyer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						pnlNorth = new JPanel();
						pnlBuyer.add(pnlNorth, BorderLayout.NORTH);
						pnlNorth.setLayout(new BorderLayout(5, 5));
						pnlNorth.setPreferredSize(new Dimension(800, 90));
						{
							pnlClient = new JPanel();
							pnlNorth.add(pnlClient, BorderLayout.WEST);
							pnlClient.setLayout(null);
							pnlClient.setPreferredSize(new Dimension(775, 100));
							{
								lblCenter = new JLabel("Client");
								pnlClient.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(10, 10, 120, 22);
							}
							{
								lookupClient = new _JLookup(null, "Client");
								pnlClient.add(lookupClient);
								lookupClient.setReturnColumn(1);
								lookupClient.setLookupSQL(lookupClients());
								lookupClient.setFilterCardName(true);
								lookupClient.setBounds(70, 10, 100, 22);
								lookupClient.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											String entity_id = (String) data[1];
											String proj_id = (String) data[7];
											pbl_id = (String) data[4];
											Integer seq_no = (Integer) data[5];
											Boolean isSelected = (Boolean) data[11];

											if (isSelected) {
												System.out.println("Dumaan sa true");

												String pbl_id2 = checkPBL(pbl_id);

												displayClientDetails(entity_id, proj_id, pbl_id2, seq_no, true);

												lblPblDescription.setText(String.format("[ %s ]", (String) data[3]));

											} else {
												System.out.println("Dumaan sa else");

												displayClientDetails(entity_id, proj_id, pbl_id, seq_no, true);
											}

											displayTCostDetails(pbl_id);

											lookupCode.setLookupSQL(sqlTcost(entity_id, pbl_id));
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								lblClient = new JLabel("[ ]");
								pnlClient.add(lblClient);
								lblClient.setHorizontalAlignment(JLabel.LEFT);
								lblClient.setBounds(185, 10, 300, 22);
							}
							{
								lblCenter = new JLabel("Project");
								pnlClient.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(10, 35, 120, 22);
							}
							{
								lblProject = new JLabel("[ ]");
								pnlClient.add(lblProject);
								lblProject.setHorizontalAlignment(JLabel.LEFT);
								lblProject.setBounds(70, 35, 300, 22);
							}
							{
								lblCenter = new JLabel("Unit");
								pnlClient.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(10, 60, 120, 22);
							}
							{
								lblPblDescription = new JLabel("[ ]");
								pnlClient.add(lblPblDescription);
								lblPblDescription.setHorizontalAlignment(JLabel.LEFT);
								lblPblDescription.setBounds(70, 60, 300, 22);
							}
							{
								lblCenter = new JLabel("Selling Agent");
								pnlClient.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(440, 10, 120, 22);
							}
							{
								lblSellingAgent = new JLabel("[ ]");
								pnlClient.add(lblSellingAgent);
								lblSellingAgent.setHorizontalAlignment(JLabel.LEFT);
								lblSellingAgent.setBounds(540, 10, 350, 22);
							}
							{
								lblCenter = new JLabel("Date Reserved");
								pnlClient.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(440, 35, 120, 22);
							}
							{
								lblDateReserved = new JLabel("[ ]");
								pnlClient.add(lblDateReserved);
								lblDateReserved.setHorizontalAlignment(JLabel.LEFT);
								lblDateReserved.setBounds(540, 35, 300, 22);
							}
							{
								lblCenter = new JLabel("Status");
								pnlClient.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(440, 60, 120, 22);
							}
							{
								lblStatus = new JLabel("[ ]");
								pnlClient.add(lblStatus);
								lblStatus.setHorizontalAlignment(JLabel.LEFT);
								lblStatus.setBounds(540, 60, 300, 22);
							}
						}
					}
					{
						pnlCenter = new JPanel();
						pnlBuyer.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Transfer Cost Details"));
						pnlCenter.setPreferredSize(new Dimension(800, 340));
						{
							pnlTable = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlTable, BorderLayout.WEST);
							pnlTable.setPreferredSize(new Dimension(460, 200));
							pnlTable.setBorder(lineBorder);
							{
								scrollTCT = new _JScrollPaneMain();
								pnlTable.add(scrollTCT, BorderLayout.CENTER);
								modelTCT = new model_tcost();
								modelTCT.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if (e.getType() == TableModelEvent.DELETE) {
											rowHeaderTCT.setModel(new DefaultListModel());
										}
										if (e.getType() == TableModelEvent.INSERT) {
											((DefaultListModel) rowHeaderTCT.getModel())
													.addElement(modelTCT.getRowCount());
										}
									}
								});

								tblTCT = new _JTableMain(modelTCT);
								tblTCT.packAll();
								tblTCT.setAlignmentX(LEFT_ALIGNMENT);
								scrollTCT.setViewportView(tblTCT);
								tblTCT.addMouseListener(new PopupTriggerListener_panel());

								tblTCT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent arg0) {
										try {
											if (!arg0.getValueIsAdjusting()) {

												String rplf_no = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(),5);
												String cost_desc = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(),1);
												String entity_id = lookupClient.getValue();
												String batch = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(), 8);
												// displayTransferCostDetails(rplf_no);
												displayTransferCostDetails_IND(cost_desc, batch, entity_id);

												if (tblTCT.getSelectedRows().length > 0) {

													if (batch == (null) || batch == ("")) {
														txtAmount.setEditable(true);
														mniDelete.setEnabled(false);
													} else {
														txtAmount.setEditable(false);
														mniDelete.setEnabled(true);
													}
												}
											}
										} catch (ArrayIndexOutOfBoundsException e) {
										}
									}
								});
								tblTCT.putClientProperty("terminateEditOnFocusLost", true);
								{
									rowHeaderTCT = tblTCT.getRowHeader();
									rowHeaderTCT.setModel(new DefaultListModel());
									scrollTCT.setRowHeaderView(rowHeaderTCT);
									scrollTCT.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}

							}

							{
								scrollTCTTotal = new _JScrollPaneTotal(scrollTCT);
								pnlTable.add(scrollTCTTotal, BorderLayout.SOUTH);
								{
									modelTCTTotal = new model_tcost();
									modelTCTTotal.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00),
											null, null, null, null, null, null, null });

									tblTCTTotal = new _JTableTotal(modelTCTTotal, tblTCT);
									scrollTCTTotal.setViewportView(tblTCTTotal);

									tblTCTTotal.setTotalLabel(2);
								}
								scrollTCTTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
										displayTableNavigation());
							}
						}
						{
							pnlDetail = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlDetail, BorderLayout.EAST);
							pnlDetail.setPreferredSize(new Dimension(400, 335));
							{
								pnlInfo = new JPanel(new BorderLayout(5, 5));
								pnlDetail.add(pnlInfo, BorderLayout.NORTH);
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
													lookupCode = new _JLookup(null, "Code", "Please select client.");
													pnlLookup.add(lookupCode, BorderLayout.WEST);
													lookupCode.setPreferredSize(new Dimension(50, 20));
													lookupCode.setReturnColumn(0);
													lookupCode.addActionListener(this);
													lookupCode.setFilterName(true);
													lookupCode.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															String emp_code = (String) UserInfo.EmployeeCode;
															String emp_name = (String) UserInfo.FullName;
															if (data != null) {
																FncSystem.out("SQL for LookupCOde",
																		lookupCode.getLookupSQL());
																FncTables.clearTable(modelTCT);// added by erick 2757

																lblCode.setText(String.format(
																		"<html><font size = 2>[ %s ]</html>",
																		(String) data[1]));
																System.out.println("lblSetupAmount1" + data[2]);

																lblSetupAmount1.setText(String.format("%s", data[2]));
																lblBalanceAmount.setText(
																		String.format("%s", lblSetupAmount1.getText()));
																txtRequestedBy.setText(emp_code);
																// txtAmount.setEditable(true);
																lblReqBy1.setText(String.format("[ %s ]", emp_name));
																pnlState(true, false);

																if (data[3] != null) {
																	txtRemarks.setText(String.format("%s", data[3]));
																} else {
																	txtRemarks.setText(" ");
																}

																if (data != null) {
																	BigDecimal amount = new BigDecimal(String.format(
																			"%s", reg_amount2(entity, pbl, buyertype)));
																	System.out.println("OTIDS1 dumaan");
																	BigDecimal amount2 = new BigDecimal(String.format(
																			"%s", reg_amount(entity, pbl, buyertype)));
																	System.out.println("OTIDS2 dumaan");
																	BigDecimal amount3 = new BigDecimal(String.format(
																			"%s", reg_amount3(entity, pbl, buyertype)));
																	System.out.println("OTIDS3 dumaan");
																	BigDecimal amount4 = new BigDecimal(
																			String.format("100.00"));
																	System.out.println("OTIDS4 dumaan");

																	BigDecimal computed_tcost_amt = getTCost_Dtl_amt(
																			lookupCode.getValue(), entity, projid,
																			pbl_id, seq);

																	if (lookupCode.getValue().equals("008")
																			|| lookupCode.getValue().equals("011")
																			|| lookupCode.getValue().equals("012")
																			|| lookupCode.getValue().equals("021")
																			|| lookupCode.getValue().equals("169")) {// **SALES**/
																		addTCostEntries(entity, projid, pbl_id, seq,
																				modelTCT);
																		// tblTCT.setEnabled(false);
																	} else if (lookupCode.getValue().equals("123")) {// **ADDED
																														// BY
																														// JED
																														// 2019-05-03
																														// DCRF
																														// NO.
																														// 1039
																														// :
																														// FORMULA
																														// FOR
																														// DOCUMENTARY
																														// STAMP
																														// MORT**//
																		BigDecimal doc_stamp_mort_amt = getDocStampMortgage(
																				lookupCode.getValue(), entity, projid,
																				pbl, seq);
																		modelTCT.addRow(new Object[] { true, data[1],
																				Calendar.getInstance().getTime(),
																				doc_stamp_mort_amt, data[3], null });
																	}
																	// **ADDED BY JED VICEDO 2019-07-03 : TRIGGER OTHER
																	// PARTICULARS IF TAGGED 009, 013, 193, 012**//
																	else if (lookupCode.getValue().equals("009")
																			|| lookupCode.getValue().equals("013")
																			|| lookupCode.getValue().equals("193")) {// **MORTGAGE**//
																		addTCostEntries_v2(entity, projid, pbl_id, seq,
																				modelTCT);
																		// tblTCT.setEnabled(false);
																	} else {

																		System.out.println("Dumaan sa else");
																		System.out.println("computed_tcost_amt: "
																				+ computed_tcost_amt);
																		modelTCT.addRow(new Object[] { true, data[1],
																				FncGlobal.getDateToday(),
																				computed_tcost_amt, data[3], null });
																		// tblTCT.setEnabled(false);
																	}

																	rowHeaderTCT.setModel(new DefaultListModel());

																	for (int y = 1; y <= modelTCT.getRowCount(); y++) {
																		((DefaultListModel) rowHeaderTCT.getModel())
																				.addElement(y);
																	}

																	scrollTCT.setCorner(
																			ScrollPaneConstants.LOWER_LEFT_CORNER,
																			FncTables.getRowHeader_Footer(Integer
																					.toString(tblTCT.getRowCount())));
																	tblTCT.packAll();

																	if (tblTCT.getColumnModel().getColumn(1)
																			.getPreferredWidth() >= 200) {
																		tblTCT.getColumnModel().getColumn(1)
																				.setPreferredWidth(200);
																	}

																	btnDRF.setEnabled(true);
																	btnTSave.setEnabled(false);
																	txtRemarks.setEditable(true);
																	if (batch() == false) {
																		lblBatch.setText(String.format(
																				"<html><b>Batch No: %s</html>",
																				generateBatchNo()));
																	} else {
																		// lblBatch.setText(String.format("<html><b>Batch
																		// No: %s</html>", generateBatchNo()));
																	}

																}

																// KEYBOARD_MANAGER.focusNextComponent();
															}
														}
													});
													{

														lblCode = new JLabel("[ ]");
														pnlLookup.add(lblCode, BorderLayout.CENTER);
														lblCode.setHorizontalAlignment(JLabel.LEFT);
													}
												}
												{
													dateSched = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
													pnlText.add(dateSched);
													dateSched.setDate(null);
													// dateSched.setEditable(false);
													dateSched.setEnabled(false);
													dateSched.setDateFormatString("yyyy-MM-dd");
													((JTextFieldDateEditor) dateSched.getDateEditor())
															.setEditable(false);
													dateSched.setDate(Calendar.getInstance().getTime());
													dateSched.addDateListener(new DateListener() {
														public void datePerformed(DateEvent event) {
															int row = tblTCT
																	.convertRowIndexToModel(tblTCT.getSelectedRow());
															modelTCT.setValueAt(dateSched.getDate(), row, 2);

														}
													});
												}
												{
													txtAmount = new _JXFormattedTextField("0.00");
													pnlText.add(txtAmount);
													txtAmount.setHorizontalAlignment(JLabel.RIGHT);
													txtAmount.setBounds(215, 10, 280, 22);
													txtAmount.setEditable(false);
													// txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtAmount.addKeyListener(new KeyAdapter() {
														public void keyReleased(KeyEvent arg0) {

															BigDecimal value1 = new BigDecimal(
																	lblSetupAmount1.getText());
															BigDecimal value2 = new BigDecimal(
																	lblRunningTotal1.getText());
															System.out.println("value1: " + value1);
															System.out.println("value2: " + value2);
															System.out.println("keyReleased: txtAmount");

															System.out.println(
																	"selected row:" + modelTCT.getSelectedRow());
															int row = tblTCT
																	.convertRowIndexToModel(modelTCT.getSelectedRow());

															if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {

																lblBalanceAmount.setText(
																		String.format("%s", value1.subtract(value2)));

																modelTCT.setValueAt(value2, row, 3);
																computeTotal();
															}
														}
													});
													txtAmount.getDocument().addDocumentListener(new DocumentListener() {
														public void insertUpdate(DocumentEvent e) {
															lblRunningTotal1.setText(txtAmount.getText());
														}

														public void removeUpdate(DocumentEvent e) {
															lblRunningTotal1.setText(txtAmount.getText());
														}

														public void changedUpdate(DocumentEvent e) {

														}
													});
												}

												JPanel pnlReqBy = new JPanel(new BorderLayout(3, 3));
												pnlText.add(pnlReqBy);
												{
													{
														txtRequestedBy = new JXTextField();
														pnlReqBy.add(txtRequestedBy, BorderLayout.WEST);
														txtRequestedBy.setEditable(false);
														txtRequestedBy.setPreferredSize(new Dimension(100, 20));
														{
															lblReqBy1 = new JLabel("[ ]");
															pnlReqBy.add(lblReqBy1, BorderLayout.CENTER);
															lblReqBy1.setHorizontalAlignment(JLabel.LEFT);
														}
													}
												}
												{
													txtRemarks = new JXTextField();
													pnlText.add(txtRemarks);
													txtRemarks.setHorizontalAlignment(JLabel.LEFT);
													txtRemarks.setBounds(215, 10, 280, 22);
													txtRemarks.setEditable(false);
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
											lblSetupAmount1 = new JLabel("0.00");
											pnlLabelTotal.add(lblSetupAmount1);
										}
									}
									{
										JLabel lblRunningTotal = new JLabel("Running Total  :");
										pnlLabelTotal.add(lblRunningTotal);
										{
											lblRunningTotal1 = new JLabel("0.00");
											pnlLabelTotal.add(lblRunningTotal1);
										}
									}
									{
										JLabel lblBalance = new JLabel("Balance +/(-)   :");
										pnlLabelTotal.add(lblBalance);
										{
											lblBalanceAmount = new JLabel("0.00");
											pnlLabelTotal.add(lblBalanceAmount);
										}
									}
								}
							}
						}
					}
				}
				{
					pnlBuyerBatch = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Buyer Cost - Batch", null, pnlBuyerBatch, null); // XXX Buyer Cost - Batch
					pnlBuyerBatch.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						pnlCenter = new JPanel();
						pnlBuyerBatch.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Transfer Cost Details"));
						// pnlCenter.setPreferredSize(new Dimension(800, 340));
						pnlCenter.setPreferredSize(new Dimension(800, 480));

						// Added by Erick 2023-05-28
						{
							JPanel pnlnorth_company = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlnorth_company, BorderLayout.NORTH);
							// pnlnorth_company.setBackground(Color.BLUE);
							pnlnorth_company.setPreferredSize(new Dimension(0, 60));
							{
								JPanel pnl_comp_proj = new JPanel(new GridLayout(2, 1, 5, 5));
								pnlnorth_company.add(pnl_comp_proj);
								{
									JPanel pnlcompany = new JPanel(new BorderLayout(5, 5));
									pnl_comp_proj.add(pnlcompany);
									{
										JLabel lblcompany = new JLabel("Company");
										pnlcompany.add(lblcompany, BorderLayout.WEST);
										lblcompany.setPreferredSize(new Dimension(70, 0));
									}
									{
										JPanel pnlcomapny_center = new JPanel(new BorderLayout(5, 5));
										pnlcompany.add(pnlcomapny_center, BorderLayout.CENTER);
										{
											lookupcompany = new _JLookup(null, "Company", 0, 2);
											pnlcomapny_center.add(lookupcompany, BorderLayout.WEST);
											lookupcompany.setLookupSQL(SQL_COMPANY());
											lookupcompany.setReturnColumn(0);
											lookupcompany.setPreferredSize(new Dimension(100, 0));
											lookupcompany.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet();
													if (data != null) {
														co_id = (String) data[0];
														company = (String) data[1];

														txtcompany.setText(company);
														System.out.println("Company: " + company);

														if (lookupCode_Batch.getValue() != null) {
															// displayTCostClient(null, tcostid_dl);
															lookupcompany.setEditable(false);
															displayTCostClient(lookupcompany.getValue(), tcostid_dl);

															lblCode_Batch.setText(
																	String.format("<html><font size = 2>[ %s ]</html>",
																			(String) data[1]));
															lblSetupAmount1_Batch.setText(String.format("%s", data[2]));
															lblBalanceAmount_Batch.setText(String.format("%s",
																	lblSetupAmount1_Batch.getText()));
															txtRemarks_Batch.setText(String.format("%s", data[3]));
															txtAmount_Batch.setText(String.format("%s", data[2]));
															txtAmount_Batch.setValue((BigDecimal) data[2]);
															txtRequestedBy_Batch.setText(emp_code);
															lblReqBy1_Batch.setText(String.format("[ %s ]", emp_name));

															btnDRF.setEnabled(false);
															btnTSave.setEnabled(false);
															String batch_no = lblBatch.getText()
																	.replace("<html><b>Batch No:</html>", "");
															System.out.println(batch_no);

															// if(batch_no.equals(null) || batch_no.equals("")){
															// btnTSave.setEnabled(false);
															// }else{
															// btnTSave.setEnabled(true);
															// }

															txtRemarks_Batch.setEditable(true);
															if (getParticulars().contains(tcostid_dl)) {// ADDED BY
																										// JED
																										// 2020-03-04
																										// : AS PER
																										// REQUEST
																										// OF MAM
																										// ELA,
																										// TAGGING
																										// OF SALES
																										// AND MORT
																										// ON BATCH
																txtAmount_Batch.setEditable(false);
															} else {
																txtAmount_Batch.setEditable(true);
															}

															// displayTCostClient(lookupCode_Batch.getValue());
															// lblBatch.setText(String.format("<html><b>Batch No:
															// %s</html>", generateBatchNo2()));

															// KEYBOARD_MANAGER.focusNextComponent();

														} else {
														}
													}
												}
											});
										}
										{
											txtcompany = new JTextField();
											pnlcomapny_center.add(txtcompany, BorderLayout.CENTER);
										}
									}
									{
										JPanel pnleast = new JPanel();
										pnlcompany.add(pnleast, BorderLayout.EAST);
										pnleast.setPreferredSize(new Dimension(200, 0));
									}
								}
								{
									JPanel pnlproject = new JPanel(new BorderLayout(5, 5));
									pnl_comp_proj.add(pnlproject);
//									{
//										JLabel lblproject = new JLabel("Project");
//										pnlproject.add(lblproject, BorderLayout.WEST);
//										lblproject.setPreferredSize(new Dimension(70, 0));
//									}
//									{
//										JPanel pnlproj_center = new JPanel(new BorderLayout(5, 5));
//										pnlproject.add(pnlproj_center, BorderLayout.CENTER);
//										{
//											lookupproject = new _JLookup(null,"Project",2,2);
//											pnlproj_center.add(lookupproject, BorderLayout.WEST);
//											lookupproject.setPreferredSize(new Dimension(100, 0));
//										}
//										{
//											txtproj = new JTextField();
//											pnlproj_center.add(txtproj, BorderLayout.CENTER);
//										}
//									}
//									{
//										JPanel pnleast = new JPanel();
//										pnlproject.add(pnleast, BorderLayout.EAST);
//										pnleast.setPreferredSize(new Dimension(200, 0));
//									}
								}
							}
						}
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
											if (e.getType() == TableModelEvent.DELETE) {
												rowHeaderTCT_Batch.setModel(new DefaultListModel());
											}
											if (e.getType() == TableModelEvent.INSERT) {
												((DefaultListModel) rowHeaderTCT_Batch.getModel())
														.addElement(modelTCT_Batch.getRowCount());
											}
										}
									});

									tblTCT_Batch = new _JTableMain(modelTCT_Batch);
									tblTCT_Batch.addMouseListener(this);
									scrollTCT_Batch.setViewportView(tblTCT_Batch);

									tblTCT_Batch.hideColumns("Unit ID");
									tblTCT_Batch.hideColumns("Batch No");
								}

								tblTCT_Batch.addMouseListener(this);
								{
									rowHeaderTCT_Batch = tblTCT_Batch.getRowHeader();
									rowHeaderTCT_Batch.setModel(new DefaultListModel());
									scrollTCT_Batch.setRowHeaderView(rowHeaderTCT_Batch);
									scrollTCT_Batch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
							}
							tblTCT_Batch.addMouseListener(this);
							// displayTCostClient(null); //---removed by jed 2018-10-16: DCRF no. 797 to put
							// control on tagging of particulars in TCOST to avoid double tagging---//
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
													// lookupCode_Batch.setEnabled(false);
													lookupCode_Batch.setFilterName(true);
													lookupCode_Batch.setLookupSQL(SQL_TCOST(entity));
													lookupCode_Batch.addActionListener(this);
													lookupCode_Batch.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															emp_code = (String) UserInfo.EmployeeCode;
															emp_name = (String) UserInfo.FullName;
															tcostid_dl = lookupCode_Batch.getValue();
															if (data != null) {
																if (lookupcompany.getValue() == null
																		|| lookupcompany.getValue() == "") {
																	JOptionPane.showMessageDialog(getTopLevelAncestor(),
																			"Please select Company.", "Code Batch",
																			JOptionPane.PLAIN_MESSAGE);

																	// JOptionPane.showConfirmDialog(getTopLevelAncestor(),
																	// "Please select Company.", "Code Batch",
																	// JOptionPane.WARNING_MESSAGE );

																} else {
																	// displayTCostClient(null, tcostid_dl);
																	displayTCostClient(lookupcompany.getValue(),
																			tcostid_dl);

																	lblCode_Batch.setText(String.format(
																			"<html><font size = 2>[ %s ]</html>",
																			(String) data[1]));
																	lblSetupAmount1_Batch
																			.setText(String.format("%s", data[2]));
																	lblBalanceAmount_Batch.setText(String.format("%s",
																			lblSetupAmount1_Batch.getText()));
																	txtRemarks_Batch
																			.setText(String.format("%s", data[3]));
																	txtAmount_Batch
																			.setText(String.format("%s", data[2]));
																	txtAmount_Batch.setValue((BigDecimal) data[2]);
																	txtRequestedBy_Batch.setText(emp_code);
																	lblReqBy1_Batch
																			.setText(String.format("[ %s ]", emp_name));

																	btnDRF.setEnabled(false);
																	btnTSave.setEnabled(false);
																	String batch_no = lblBatch.getText()
																			.replace("<html><b>Batch No:</html>", "");
																	System.out.println(batch_no);

																	// if(batch_no.equals(null) || batch_no.equals("")){
																	// btnTSave.setEnabled(false);
																	// }else{
																	// btnTSave.setEnabled(true);
																	// }

																	txtRemarks_Batch.setEditable(true);
																	if (getParticulars().contains(tcostid_dl)) {// ADDED
																												// BY
																												// JED
																												// 2020-03-04
																												// : AS
																												// PER
																												// REQUEST
																												// OF
																												// MAM
																												// ELA,
																												// TAGGING
																												// OF
																												// SALES
																												// AND
																												// MORT
																												// ON
																												// BATCH
																		txtAmount_Batch.setEditable(false);
																	} else {
																		txtAmount_Batch.setEditable(true);
																	}

																	if (lookupcompany.getValue() != null) {
																		lookupcompany.setEditable(false);
																	} else {
																	}

																	// displayTCostClient(lookupCode_Batch.getValue());
																	// lblBatch.setText(String.format("<html><b>Batch
																	// No:
																	// %s</html>", generateBatchNo2()));

																	KEYBOARD_MANAGER.focusNextComponent();
																}
															}
														}
													});
													{
														lblCode_Batch = new JLabel("[ ]");
														pnlLookup.add(lblCode_Batch, BorderLayout.CENTER);
														lblCode_Batch.setHorizontalAlignment(JLabel.LEFT);
													}
												}
												{
													dateSched_Batch = new _JDateChooser("MM/dd/yyyy", "##/##/#####",
															'_');
													pnlText.add(dateSched_Batch);
													dateSched_Batch.setDate(null);
													dateSched_Batch.setEnabled(false);
													dateSched_Batch.setDateFormatString("yyyy-MM-dd");
													((JTextFieldDateEditor) dateSched_Batch.getDateEditor())
															.setEditable(false);
													dateSched_Batch.setDate(Calendar.getInstance().getTime());
													dateSched_Batch.addDateListener(new DateListener() {
														public void datePerformed(DateEvent event) {
															int row = tblTCT_Batch.convertRowIndexToModel(
																	tblTCT_Batch.getSelectedRow());
															modelTCT_Batch.setValueAt(dateSched_Batch.getDate(), row,
																	2);

														}
													});
												}
												{
													txtAmount_Batch = new _JXFormattedTextField("0.00");
													pnlText.add(txtAmount_Batch);
													txtAmount_Batch.setHorizontalAlignment(JLabel.RIGHT);
													txtAmount_Batch.setBounds(215, 10, 280, 22);
													txtAmount_Batch.setEditable(false);
													// txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtAmount_Batch.addKeyListener(new KeyAdapter() {
														public void keyReleased(KeyEvent arg0) {
															BigDecimal value1 = new BigDecimal(
																	lblSetupAmount1_Batch.getText());
															BigDecimal value2 = new BigDecimal(
																	lblRunningTotal1_Batch.getText());

															int row = tblTCT_Batch.convertRowIndexToModel(
																	tblTCT_Batch.getSelectedRow());

															if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
																lblBalanceAmount_Batch.setText(
																		String.format("%s", value1.subtract(value2)));

																modelTCT_Batch.setValueAt(value2, row, 3);
																computeTotal();
															}
														}
													});
													txtAmount_Batch.getDocument()
															.addDocumentListener(new DocumentListener() {
																public void insertUpdate(DocumentEvent e) {
																	lblRunningTotal1_Batch
																			.setText(txtAmount_Batch.getText());
																}

																public void removeUpdate(DocumentEvent e) {
																	lblRunningTotal1_Batch
																			.setText(txtAmount_Batch.getText());
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
															pnlReqBy.add(lblReqBy1_Batch, BorderLayout.CENTER);
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
					tabCenter.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							int selectedTab = ((JTabbedPane) arg0.getSource()).getSelectedIndex();

							if (selectedTab == 0) {
								lblBatch.setText("<html><b>Batch No:</html>");
								btnDRF.setEnabled(false);
								btnTSave.setEnabled(false);
								btnBatch.setEnabled(false);
							}
							if (selectedTab == 1) {
								lblBatch.setText("<html><b>Batch No:</html>");
								btnDRF.setEnabled(false);
								btnTSave.setEnabled(false);
								btnBatch.setEnabled(true);
								to_do = "new";
							}
						}
					});
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
					btnBatch.setEnabled(false);
					// btnDelete.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
	}

	private static String lookupClients() {

		String SQL = "SELECT\n" + "				a.status_id as status,\n"
				+ "				CASE WHEN c.entity_id IS NOT NULL THEN btrim(c.entity_id::text)::character varying ELSE f.entity_id end AS \"Entity ID\",\n"
				+ "				case when b.entity_name is not null then btrim(b.entity_name::text) else g.entity_name END AS \"Client\",\n"
				+ "				format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\",\n"
				+ "				btrim(a.pbl_id::text)::character varying AS \"PBL\",\n"
				+ "				case when c.seq_no is not null then c.seq_no ELSE f.seq_no end AS \"Seq\",\n"
				+ "				case when e.type_card_display is not null then btrim(e.type_card_display::text) else (select type_card_display from rf_sold_unit a LEFT JOIN mf_buyer_type b ON b.type_id::text = a.buyertype::text where entity_id = f.entity_id limit 1) end AS \"Buyer Type\",\n"
				+ "			    btrim(a.proj_id::text)::character varying AS \"Project\",\n"
				+ "				btrim(d.proj_name::text) AS \"Proj. Name\",\n"
				+ "				c.unit_id AS \"Unit ID\",  \n" + "				''::varchar AS \"Batch\",\n"
				+ "				(case when (select pbl_id from hs_sold_other_lots x where x.proj_id = a.proj_id and x.oth_pbl_id = a.pbl_id and x.status_id = 'A' and coalesce(a.server_id, '') = coalesce(x.server_id, '')) is not null then true else false end) as boolean\n"
				+ "				FROM mf_unit_info a\n"
				+ "				LEFT JOIN rf_sold_unit c ON a.proj_id::text = c.projcode::text AND a.pbl_id::text = c.pbl_id::text and c.status_id in ('A') --and coalesce(a.server_id, '') = coalesce(c.server_id, '')\n"
				+ "				LEFT JOIN rf_entity b ON b.entity_id::text = c.entity_id::text \n"
				+ "				LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text --and coalesce(a.server_id, '') = coalesce(d.server_id, '')\n"
				+ "				LEFT JOIN mf_buyer_type e ON e.type_id::text = c.buyertype::text\n"
				+ "				LEFT JOIN hs_sold_other_lots f on f.proj_id = a.proj_id and f.oth_pbl_id = a.pbl_id --and f.status_id = 'A' --and coalesce(a.server_id, '') = coalesce(f.server_id, '')\n"
				+ "				LEFT join rf_entity g on g.entity_id = f.entity_id \n" + "				WHERE \n"
				+ "                a.status_id in ('R')\n"
				+ "				ORDER BY btrim(b.entity_name::text), getinteger(a.phase), getinteger(a.block), getinteger(a.lot)";

		FncSystem.out("CLIENTS HERE!", SQL);

		return SQL;

	}

	private void refreshBuyer() {
		if (lookupClient.getText().equals("")) {
			lblClient.setText("[ ]");
			lblProject.setText("[ ]");
			lblPblDescription.setText("[ ]");
			lblSellingAgent.setText("[ ]");
			lblDateReserved.setText("[ ]");
			lblStatus.setText("[ ]");
			// lblBatch.setText("<html><b>Batch No:</html>");
			lblCode.setText("[ ]");
			lookupCode.setText(null);
			txtAmount.setText("0.00");
			txtRequestedBy.setText(null);
			lblReqBy1.setText("[ ]");
			txtRemarks.setText(null);
			btnDRF.setEnabled(false);
			btnTSave.setEnabled(false);
			// btnBatch.setEnabled(false);
			dateSched.setDate(Calendar.getInstance().getTime());
			lblSetupAmount1.setText("0.00");
			lblRunningTotal1.setText("0.00");
			lblBalanceAmount.setText("0.00");
			modelTCT.setRowCount(0);
			// modelTCTTotal.setValueAt(new BigDecimal(0.00), 0, 3);
			txtAmount.setEditable(false);
			txtRequestedBy.setEditable(false);
			dateSched.setEnabled(false);
		}
	} // refreshTO()

	private void refreshBatch() {

		if (lookupCode_Batch.getText().equals("")) {
			modelTCT_Batch.clear();
			scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER,
					FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
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
			// btnBatch.setEnabled(false);
			modelTCT_Batch.setRowCount(0);
			// displayTCostClient(lookupCode_Batch.getValue());
			lblBatch.setText("<html><b>Batch No: </html>");
		}
	} // refreshTO()

	private static String SQL_TCOST(String entity) {
		return "SELECT distinct on(tcostdtl_id) tcostdtl_id as \"TCost ID.\", trim(tcostdtl_desc) as \"TCost Desc\", tcostdtl_amt as \"TCost Amount\", remarks as \"Remarks\"\n"
				+ "				FROM mf_transfer_cost_dl\n" + "				WHERE status_id = 'A'\n"
				// + " AND tcostdtl_id NOT IN (SELECT tcostid_dl FROM rf_transfer_cost WHERE
				// entity_id = '"+entity+"' and status_id = 'A')\n"
				// Comment by Erick 2023-06-01
				/*
				 * +
				 * "				AND CASE WHEN COALESCE(tcostdtl_amt, 0.00) = 0 THEN tcostdtl_id NOT IN (SELECT tcostid_dl FROM rf_transfer_cost \n"
				 * +
				 * "                                                                                        WHERE entity_id = '' \n"
				 * +
				 * "                                                                                        and status_id = 'A') ELSE TRUE END\n"
				 */
				+ "				GROUP BY tcostdtl_id, tcostdtl_desc, tcostdtl_amt, remarks \n"
				+ "				--ORDER BY tcostdtl_desc desc;";
	}

	private String sqlTCost(String entity_id) {
		return "SELECT tcostdtl_id as \"TCost ID.\", trim(tcostdtl_desc) as \"TCost Desc\", tcostdtl_amt as \"TCost Amount\", remarks as \"Remarks\"\n"
				+ "FROM mf_transfer_cost_dl\n" + "WHERE status_id = 'A'\n" +
				// "AND tcostdtl_id NOT IN (SELECT tcostid_dl FROM rf_transfer_cost WHERE
				// entity_id = '"+entity+"')\n" +
				"AND CASE WHEN COALESCE(tcostdtl_amt, 0.00) = 0 THEN tcostdtl_id NOT IN (SELECT tcostid_dl FROM rf_transfer_cost WHERE entity_id = '"
				+ entity + "' and status_id = 'A') ELSE TRUE END\n"
				+ "GROUP BY tcostdtl_id, tcostdtl_desc, tcostdtl_amt, remarks\n" + "ORDER BY tcostdtl_desc ASC;";
	}

	private String sqlTcost(String entity_id, String pbl_id) {
		return "SELECT * FROM get_tcost_sql('" + entity_id + "', '" + pbl_id + "')";
	}

//	private String sqlTcost(String entity_id, String pbl_id, String proj_id){
//		return "SELECT * FROM get_tcost_sql_v2('"+entity_id+"', '"+pbl_id+"', '"+proj_id+"')";
//	}

	private String generateTAG(Object text) {
		return String.format("[ %s ]", text);
	}

	private void displayClientDetails(String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		
		clientDetails = displayClientInformation(entity_id, proj_id, pbl_id, seq_no, toPrint);
		if (clientDetails != null) {
			
			lookupClient.setText((String) clientDetails[0]);
			lblClient.setText(generateTAG((String) clientDetails[1]));

			lblProject.setText(generateTAG((String) clientDetails[3]));

			lblPblDescription.setText(String.format("[ %s ]", (String) clientDetails[5]));

			lblStatus.setText(generateTAG((String) clientDetails[14]));

			lblSellingAgent.setText(String.format("[ %s ]", (String) clientDetails[31]));

			// lblBatch.setText(String.format("<html><b>Batch No: %s</html>", (String)
			// clientDetails[37]));

			lblDateReserved.setText(String.format("[ %s ]", (String) sdf.format(clientDetails[38])));

			batch_no = (String) clientDetails[37];
			pbl = (String) clientDetails[4];
			entity = (String) clientDetails[0];
			projid = (String) clientDetails[2];
			seq = seq_no;
			buyertype = (String) clientDetails[9];
		}
	}

	private static Object[] displayClientInformation(String entity_id, String proj_id, String pbl_id, int seq_no,
			Boolean toPrint) {
		String SQL = "SELECT TRIM(a.entity_id) AS client_id, get_client_coapp(a.entity_id) AS client_name, TRIM(a.projcode) AS proj_id, TRIM(b.proj_name) AS proj_name, TRIM(a.pbl_id) AS pbl_id,\n"
				+ "	TRIM(c.description) AS description, a.seq_no, TRIM(a.model_id) AS model_id, TRIM(d.model_desc) AS model_desc, TRIM(a.buyertype) AS buyertype_id,\n"
				+ "	TRIM(e.type_card_display) AS buyertype_name, TRIM(a.pmt_scheme_id) AS pmt_scheme_id, TRIM(f.pmt_scheme_desc) AS pmt_scheme_desc, TRIM(a.currentstatus) AS status_id,\n"
				+ "	TRIM(g.status_desc) AS status_desc, a.sellingprice AS selling_price,\n"
				+ "	(CASE WHEN e.type_group_id = '04' THEN h.disc_amount ELSE i.disc_amount END) AS disc_amount,\n"
				+ "	(CASE WHEN e.type_group_id = '04' THEN h.dp ELSE i.dp END) AS dp_equity,\n"
				+ "	(CASE WHEN e.type_group_id = '04' THEN h.loanable_amount ELSE (a.sellingprice - i.dp) END) AS loanable_amount,\n"
				+ "	(CASE WHEN e.type_group_id = '04' THEN h.loanable_amount ELSE (a.sellingprice - i.dp) END) -\n"
				+ "	 (SELECT COALESCE(sum(amount), 0)\n" + "          FROM rf_client_ledger\n"
				+ "          WHERE entity_id = '" + entity_id + "' AND proj_id = '" + proj_id + "' AND pbl_id = '"
				+ pbl_id + "' AND seq_no = " + seq_no + " AND status_id = 'A'\n" + "          AND part_id in ('002')\n"
				+ "          AND status_id = 'A') AS balance,\n"
				+ "        (CASE WHEN e.type_group_id = '04' THEN NULL ELSE i.dp_rate END) AS dp_rate,\n"
				+ "        (CASE WHEN e.type_group_id = '04' THEN h.dp_term ELSE i.dp_term END) AS dp_term,\n"
				+ "        (CASE WHEN e.type_group_id = '04' THEN h.total_ma ELSE i.total_ma END) AS ma_amount,\n"
				+ "        (CASE WHEN e.type_group_id = '04' THEN NULL ELSE (100 - i.dp_rate) END) AS ma_rate,\n"
				+ "        (CASE WHEN e.type_group_id = '04' THEN h.term ELSE i.ma_term END) AS ma_term,\n"
				+ "        e.type_group_id, a.sellingagent as agent_id, get_client_name(a.sellingagent) as agent_name,\n"
				+ "        (SELECT TRIM(dept_name) as sales_group FROM mf_department WHERE dept_code IN (SELECT TRIM(dept_id) FROM mf_sellingagent_info WHERE agent_id = a.sellingagent) AND status_id = 'A'),\n"
				+ "        (CASE WHEN e.type_group_id = '04' THEN h.comp_factor ELSE i.comp_factor END)::VARCHAR AS comp_factor\n,\n"
				+ "        a.sellingagent, get_client_name(a.sellingagent), COALESCE(l.dept_alias, 'N/A'), COALESCE(get_employee_name(j.coordinator_id), 'N/A'),\n"
				+ "        get_payment_stage(a.entity_id, a.projcode, a.pbl_id, a.seq_no),\n"
				//+ "        (CASE WHEN e.type_group_id = '04' THEN ((h.dp / h.dp_term) + h.proc_fee) ELSE (i.dp / i.dp_term) END) AS dp_amount,\n"
				+ "        (CASE WHEN e.type_group_id = '04' THEN ((h.dp / h.dp_term) + h.proc_fee) ELSE (i.dp / coalesce(nullif(i.dp_term,0),1)) END) AS dp_amount,\n" //Edited by erick 2023-09-20 - fix computation if i.dp_term = 0
				+ "        (CASE WHEN e.type_group_id = '04' THEN h.int_rate ELSE i.int_rate END) AS int_rate,\n m.batch_no, a.datersvd, a.entity_id, a.unit_id"
				+ "\n" + "FROM rf_sold_unit a\n"
				+ "LEFT JOIN mf_project b on b.proj_id = a.projcode --and coalesce(a.server_id,'') = coalesce(b.server_id, '')\n"
				+ "LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id and coalesce(a.server_id,'') = coalesce(c.server_id, '')\n"
				+ "LEFT JOIN mf_product_model d on d.model_id = a.model_id and coalesce(a.server_id,'') = coalesce(d.server_id, '') and coalesce(a.proj_server,'') = coalesce(d.proj_server, '')\n"
				+ "LEFT JOIN mf_buyer_type e on e.type_id = a.buyertype\n"
				+ "LEFT JOIN mf_payment_scheme f on f.pmt_scheme_id = a.pmt_scheme_id and coalesce(a.server_id,'') = coalesce(f.server_id, '')\n"
				+ "LEFT JOIN mf_buyer_status g on g.byrstatus_id = a.currentstatus and coalesce(a.server_id,'') = coalesce(g.server_id, '')\n"
				+ "LEFT JOIN rf_pagibig_computation h ON h.entity_id = a.entity_id AND h.proj_id = a.projcode AND h.pbl_id = a.pbl_id AND h.seq_no = a.seq_no AND h.status_id = 'A' and coalesce(a.server_id,'') = coalesce(h.server_id, '')\n"
				+ "LEFT JOIN rf_ihf_computation i ON i.entity_id = a.entity_id AND i.proj_id = a.projcode AND i.pbl_id = a.pbl_id AND i.seq_no = a.seq_no AND i.status_id = 'A' and coalesce(a.server_id,'') = coalesce(i.server_id, '')\n"
				+ "LEFT JOIN rf_sellingagent_info j ON j.agent_id = a.sellingagent AND j.status_id = 'A'\n"
				+ "LEFT JOIN mf_division k ON k.division_code = j.div_code AND k.status_id = 'A'\n"
				+ "LEFT JOIN mf_department l ON l.dept_code = j.dept_id AND l.status_id = 'A'\n"
				+ "LEFT JOIN rf_client_notices m ON m.entity_id = a.entity_id AND m.projcode = a.projcode AND m.pbl_id = a.pbl_id AND m.seq_no = a.seq_no AND m.status_id = 'A'\n"
				+ "\n" + "WHERE a.entity_id = '" + entity_id + "'\n" + "AND a.projcode = '" + proj_id + "'\n"
				+ "AND a.pbl_id = '" + pbl_id + "'\n" + "AND a.seq_no = " + seq_no + ";";

		FncSystem.out(SQL, "CLIENT INFO");

		if (toPrint) {
			FncSystem.out("Client Information", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private void computeTotal() {
		BigDecimal totalAmountCommitted = new BigDecimal("0.00");

		if (tabCenter.getSelectedIndex() == 0) {

			for (int x = 0; x < modelTCT.getRowCount(); x++) {
				BigDecimal totalamount = (BigDecimal) modelTCT.getValueAt(x, 3);

				try {
					totalAmountCommitted = totalAmountCommitted.add(totalamount);
				} catch (Exception e1) {
				}
			}

			modelTCTTotal.setValueAt(totalAmountCommitted, 0, 3);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if (actionCommand.equals("Create Disbursement Request")) {
			if (tabCenter.getSelectedIndex() == 0) {
				if (withZeroAmount()) {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Amount cannot be zero", "Save",
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
				}
			}
			if (tabCenter.getSelectedIndex() == 1) {
				/*
				 * BigDecimal amount_to_save = (BigDecimal) txtAmount_Batch.getValued();
				 * 
				 * if(amount_to_save.compareTo(new BigDecimal("0.00")) == 0){
				 * JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),
				 * "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE); }else{
				 */
				JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
				// }
			}
		}
		if (actionCommand.equals("Create RPLF")) {
			// Create rplf for individual tagging
			if (tabCenter.getSelectedIndex() == 0) {
				if (validateSaving()) {

					int response = JOptionPane.showConfirmDialog(getContentPane(), "Are you all fields correct? ",
							"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {

						// String rplf_no = rplfNo();
						for (int x = 0; x < tblTCT.getRowCount(); x++) {
							Boolean isSelected = (Boolean) modelTCT.getValueAt(x, 0);
							Date trans_date = (Date) modelTCT.getValueAt(x, 2);
							BigDecimal amount = (BigDecimal) modelTCT.getValueAt(x, 3);
							String remarks = (String) txtRemarks.getText();
							String desc = (String) modelTCT.getValueAt(x, 1);
							String entity_id = (String) clientDetails[39];
							String unit_id = (String) clientDetails[40];
							String projcode = (String) clientDetails[2];
							// String pbl_id = (String) clientDetails[4];
							String pbl_id2 = (String) pbl_id;
							Integer seq_no = (Integer) clientDetails[6];
							String req_id = (String) txtRequestedBy.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							String client_id = (String) lookupClient.getText();
							String request_type = (String) lookupRequestType.getText();
							String availer = (String) lookupAvailer.getText();
							String availer_type = (String) lookupAvailerType.getText();
							// String batch_no = batchNo();
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "")
									.replace("</html>", "");
							System.out.println("Amount: "+amount);
//							System.out.println("Amount: "+amount);
//							
//							
//								if (isSelected) {
//									String cost_desc = (String) modelTCT.getValueAt(x, 1);
//									Object[] particulars = getTransferCostDetails1(cost_desc);
//									String costid = (String) particulars[0];
//									BigDecimal setup_amount = (BigDecimal) particulars[1];
//									System.out.println("setup_amount: "+setup_amount);

							if (isSelected) {
								String cost_desc = (String) modelTCT.getValueAt(x, 1);
								Object[] particulars = getTransferCostDetails1(cost_desc);
								String costid = (String) particulars[0];
								BigDecimal setup_amount = (BigDecimal) particulars[1];
									System.out.println("setup_amount: "+setup_amount);

								String SQL = "SELECT sp_save_transfer_cost_new_v2('" + entity_id + "', '" + projcode
										+ "', '" + pbl_id2 + "', '" + costid + "', '" + trans_date + "'::date," + ""
										+ amount + ", " + setup_amount + ", '" + req_id + "', NULLIF('" + remarks
										+ "','null'), '" + emp_code + "', '" + batch_no + "', NULLIF('" + unit_id
										+ "','null')," + "'" + desc + "', " + seq_no + ", '" + client_id + "', '"
										+ request_type + "', '" + availer + "', '" + availer_type + "')";

								pgSelect db = new pgSelect();
								FncSystem.out("SQL", SQL);
								db.select(SQL);

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
							}
						}

						JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF",
								JOptionPane.INFORMATION_MESSAGE);
						lblBatch.setText("<html><b>Batch No:</html>");
						pnlState(true, true);
						tblTCT.setEnabled(true);

						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
						optionPaneWindow.dispose();

						Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
						if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
							optionPaneWindowMain.getFocusOwner();
							// lookupClient.setText(""); DCRF 2757
							refreshBuyer();
						}
					}
				}
			}
			// Create rplf for batch tagging
			if (tabCenter.getSelectedIndex() == 1) {

				int response = JOptionPane.showConfirmDialog(getContentPane(), "Are you all fields correct? ",
						"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {

					String batchno = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>",
							"");
					// String rplf_no = rplfNo();
					// for(int x = 0; x < tblPCD_Batch.getRowCount(); x++){
					// Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
					String emp_code = (String) UserInfo.EmployeeCode;
					String request_type = (String) lookupRequestType.getText();
					String availer = (String) lookupAvailer.getText();
					String availer_type = (String) lookupAvailerType.getText();
					String control_no = (String) lookupControlNo.getText();

					// if(isSelected){

					String SQL = "SELECT sp_save_transfer_cost_rplf_jervin('" + emp_code + "', '" + batchno + "', '"
							+ request_type + "', '" + availer + "', '" + availer_type + "','" + control_no + "')";

					pgSelect db = new pgSelect();
					FncSystem.out("SQL", SQL);
					db.select(SQL);
					// }
					// }
					JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF",
							JOptionPane.INFORMATION_MESSAGE);
					lblBatch.setText("<html><b>Batch No:</html>");
					pnlState(true, true);

					modelTCT_Batch.setRowCount(0);
					to_do = "new";
					// displayTCostClient(lookupCode_Batch.getValue());

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

		if (actionCommand.equals("Save")) {
			if (tabCenter.getSelectedIndex() == 0) {
				if (withZeroAmount()) {
					JOptionPane.showMessageDialog(getContentPane(), "Amount cannot be zero", "Save",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String rplf_no = "";
					for (int x = 0; x < tblTCT.getRowCount(); x++) {
						String cost_desc = (String) modelTCT.getValueAt(x, 1);
						Object[] particulars = getTransferCostDetails1(cost_desc);
						Boolean isSelected = (Boolean) modelTCT.getValueAt(x, 0);
						Date trans_date = (Date) dateSched.getDate();
						BigDecimal amount = (BigDecimal) modelTCT.getValueAt(x, 3);
						String remarks = (String) modelTCT.getValueAt(x, 4);
						// String desc = (String) modelTCT.getValueAt(x, 1);
						String entity_id = (String) clientDetails[39];
						String unit_id = (String) clientDetails[40];
						String projcode = (String) clientDetails[2];
						String pbl_id = (String) clientDetails[4];
						Integer seq_no = (Integer) clientDetails[6];
						String costid = (String) lookupCode.getValue();
						BigDecimal setup_amount = (BigDecimal) particulars[1];
						String req_id = (String) txtRequestedBy.getText();
						String emp_code = (String) UserInfo.EmployeeCode;
						// String client_id = (String) lookupClient.getText();
						// String request_type = (String) lookupRequestType.getText();
						String availer = (String) lookupAvailer.getText();
						// String availer_type = (String) lookupAvailerType.getText();
						String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "")
								.replace("</html>", "");

						if (isSelected) {

							String SQL = "SELECT sp_save_transfer_cost_woutrplf('" + entity_id + "', '" + projcode
									+ "', '" + pbl_id + "', '" + costid + "', '" + trans_date + "'::date," + "" + amount
									+ ", " + setup_amount + ", '" + req_id + "', NULLIF('" + remarks + "','null'), '"
									+ emp_code + "', NULLIF('" + batch_no + "','null'), NULLIF('" + unit_id
									+ "','null')," + "" + seq_no + ", '" + availer + "', '" + rplf_no + "')";

							pgSelect db = new pgSelect();
							FncSystem.out("SQL", SQL);
							db.select(SQL);

						}
					}

					JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					modelTCT.setRowCount(0);
					lookupClient.setText(" ");
					refreshBuyer();
				}
			}
			if (tabCenter.getSelectedIndex() == 1) {
				/*
				 * EDITED BY JED DCRF 1726 2021-09-20: AUTO TAGGING OF TRANSFER TAX WHEN
				 * DOCSTAMP SALE IS TAG(FUNCTION IS MODIFIED)
				 */
				if (to_do.equals("old")) {
					if (lookupCode_Batch.getText().equals(null) || lookupCode_Batch.getText().equals("")) {
						JOptionPane.showMessageDialog(getContentPane(), "Please select a particular.", "Save",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (getParticulars().contains(lookupCode_Batch.getValue())) {// ADDED BY JED FOR SEPARATE SAVING
																						// FOR DOCUMENTARY MORTGAGE
																						// 2020-27-2020
							if (JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?",
									"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {

								String rplf_no = "";
								for (int x = 0; x < tblTCT_Batch.getRowCount(); x++) {
									Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
									Date trans_date = (Date) dateSched_Batch.getDate();
									BigDecimal amount = (BigDecimal) txtAmount_Batch.getValued(); // new BigDecimal
																									// (txtAmount_Batch.getText());
									String remarks = (String) txtRemarks_Batch.getText();
									// String desc = (String) lblCode_Batch.getText().replace("<html><font size =
									// 2>[ ", "").replace(" ]</html>","");
									String entity_id = (String) modelTCT_Batch.getValueAt(x, 1);
									String unit_id = (String) modelTCT_Batch.getValueAt(x, 9);
									String projcode = (String) modelTCT_Batch.getValueAt(x, 7);
									String pbl_id = (String) modelTCT_Batch.getValueAt(x, 4);
									Integer seq_no = (Integer) modelTCT_Batch.getValueAt(x, 5);
									String costid = (String) lookupCode_Batch.getValue();
									BigDecimal setup_amount = (BigDecimal) txtAmount_Batch.getValued(); // new
																										// BigDecimal

									String req_id = (String) txtRequestedBy_Batch.getText();
									String emp_code = (String) UserInfo.EmployeeCode;
									// String client_id = (String) lookupClient.getText();
									// String request_type = (String) lookupRequestType.getText();
									String availer = (String) lookupAvailer.getText();
									// String availer_type = (String) lookupAvailerType.getText();
									String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "")
											.replace("</html>", "");

									if (isSelected) {

										/*
										 * String SQL =
										 * "SELECT sp_save_transfer_cost_woutrplf('"+entity_id+"', '"+projcode+"', '"
										 * +pbl_id+"', '"+costid+"', '"+trans_date+"'::date," +
										 * ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"
										 * +emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null')," +
										 * ""+seq_no+", '"+availer+"', '"+rplf_no+"')";
										 */
										String SQL = "SELECT sp_save_transfer_cost_woutrplf_batch_v2('" + entity_id
												+ "', '" + projcode + "', '" + pbl_id + "', '" + costid + "', '"
												+ trans_date + "'::date," + "" + amount + ", " + setup_amount + ", '"
												+ req_id + "', NULLIF('" + remarks + "','null'), '" + emp_code
												+ "', NULLIF('" + batch_no + "','null'), NULLIF('" + unit_id
												+ "','null')," + "" + seq_no + ", '" + availer + "', '" + rplf_no
												+ "')";

										pgSelect db = new pgSelect();
										FncSystem.out("SQL", SQL);
										db.select(SQL);
									}
								}

								JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information",
										JOptionPane.INFORMATION_MESSAGE);
								// modelTCT_Batch.setRowCount(0);

								// -------added by jed 9-4-18 to avoid multiple saving

								modelTCT_Batch.clear();
								scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER,
										FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
								tblTCT_Batch.packAll();

								pnlState(true, true);
								btnTSave.setEnabled(false);
								btnDRF.setEnabled(true);
								lookupCode_Batch.setText("");
								lblCode_Batch.setText("[ ]");
								lblSetupAmount1_Batch.setText("0.00");
								lblBalanceAmount_Batch.setText("0.00");
								txtRemarks_Batch.setEditable(false);
								txtRemarks_Batch.setText("");
								txtAmount_Batch.setEditable(false);
								txtAmount_Batch.setValue(0.00);
								txtRequestedBy_Batch.setText("");
								lblReqBy1_Batch.setText("[ ]");
							}
						} else {
							BigDecimal amount_to_save = (BigDecimal) txtAmount_Batch.getValued();
							if (amount_to_save.compareTo(new BigDecimal("0.00")) == 0) {
								JOptionPane.showMessageDialog(getContentPane(), "Amount cannot be zero", "Save",
										JOptionPane.WARNING_MESSAGE);
							} else {
								if (JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?",
										"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {

									String rplf_no = "";
									for (int x = 0; x < tblTCT_Batch.getRowCount(); x++) {
										Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
										Date trans_date = (Date) dateSched_Batch.getDate();
										BigDecimal amount = (BigDecimal) txtAmount_Batch.getValued(); // new BigDecimal
																										// (txtAmount_Batch.getText());
										String remarks = (String) txtRemarks_Batch.getText();
										// String desc = (String) lblCode_Batch.getText().replace("<html><font size =
										// 2>[ ", "").replace(" ]</html>","");
										String entity_id = (String) modelTCT_Batch.getValueAt(x, 1);
										String unit_id = (String) modelTCT_Batch.getValueAt(x, 9);
										String projcode = (String) modelTCT_Batch.getValueAt(x, 7);
										String pbl_id = (String) modelTCT_Batch.getValueAt(x, 4);
										Integer seq_no = (Integer) modelTCT_Batch.getValueAt(x, 5);
										String costid = (String) lookupCode_Batch.getValue();
										BigDecimal setup_amount = (BigDecimal) txtAmount_Batch.getValued(); // new
																											// BigDecimal
																											// (txtAmount_Batch.getText());
										String req_id = (String) txtRequestedBy_Batch.getText();
										String emp_code = (String) UserInfo.EmployeeCode;
										// String client_id = (String) lookupClient.getText();
										// String request_type = (String) lookupRequestType.getText();
										String availer = (String) lookupAvailer.getText();
										// String availer_type = (String) lookupAvailerType.getText();
										String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "")
												.replace("</html>", "");

										if (isSelected) {

											String SQL = "SELECT sp_save_transfer_cost_woutrplf_batch_v2('" + entity_id
													+ "', '" + projcode + "', '" + pbl_id + "', '" + costid + "', '"
													+ trans_date + "'::date," + "" + amount + ", " + setup_amount
													+ ", '" + req_id + "', NULLIF('" + remarks + "','null'), '"
													+ emp_code + "', NULLIF('" + batch_no + "','null'), NULLIF('"
													+ unit_id + "','null')," + "" + seq_no + ", '" + availer + "', '"
													+ rplf_no + "')";

											pgSelect db = new pgSelect();
											FncSystem.out("SQL", SQL);
											db.select(SQL);
										}
									}

									JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information",
											JOptionPane.INFORMATION_MESSAGE);
									// modelTCT_Batch.setRowCount(0);

									// -------added by jed 9-4-18 to avoid multiple saving

									modelTCT_Batch.clear();
									scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables
											.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
									tblTCT_Batch.packAll();

									pnlState(true, true);
									btnTSave.setEnabled(false);
									btnDRF.setEnabled(true);
									lookupCode_Batch.setText("");
									lblCode_Batch.setText("[ ]");
									lblSetupAmount1_Batch.setText("0.00");
									lblBalanceAmount_Batch.setText("0.00");
									txtRemarks_Batch.setEditable(false);
									txtRemarks_Batch.setText("");
									txtAmount_Batch.setEditable(false);
									txtAmount_Batch.setValue(0.00);
									txtRequestedBy_Batch.setText("");
									lblReqBy1_Batch.setText("[ ]");
								}
							}
						}
					}
				} else {
					if (lookupCode_Batch.getText().equals(null) || lookupCode_Batch.getText().equals("")) {
						JOptionPane.showMessageDialog(getContentPane(), "Please select a particular.", "Save",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (getParticulars().contains(lookupCode_Batch.getValue())) {// ADDED BY JED FOR SEPARATE SAVING
																						// FOR DOCUMENTARY MORTGAGE
																						// 2020-27-2020
							if (JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?",
									"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {

								String checkBatch = (String) lblBatch.getText().replace("<html><b>Batch No: ", "")
										.replace("</html>", "");
								if (checkBatch(checkBatch).equals("")) {// **ADDED BY JED 2019-09-03 TO AVOID SAME
																		// BATCHING**//
									String rplf_no = "";
									for (int x = 0; x < tblTCT_Batch.getRowCount(); x++) {

										Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
										Date trans_date = (Date) dateSched_Batch.getDate();
										BigDecimal amount = (BigDecimal) txtAmount_Batch.getValued(); // new BigDecimal
																										// (txtAmount_Batch.getText());
										String remarks = (String) txtRemarks_Batch.getText();
										// String desc = (String) lblCode_Batch.getText().replace("<html><font size =
										// 2>[ ", "").replace(" ]</html>","");
										String entity_id = (String) modelTCT_Batch.getValueAt(x, 1);
										String unit_id = (String) modelTCT_Batch.getValueAt(x, 9);
										String projcode = (String) modelTCT_Batch.getValueAt(x, 7);
										String pbl_id = (String) modelTCT_Batch.getValueAt(x, 4);
										Integer seq_no = (Integer) modelTCT_Batch.getValueAt(x, 5);
										String costid = (String) lookupCode_Batch.getValue();
										BigDecimal setup_amount = (BigDecimal) txtAmount_Batch.getValued(); // new
																											// BigDecimal
																											// (txtAmount_Batch.getText());
										String req_id = (String) txtRequestedBy_Batch.getText();
										String emp_code = (String) UserInfo.EmployeeCode;
										// String client_id = (String) lookupClient.getText();
										// String request_type = (String) lookupRequestType.getText();
										String availer = (String) lookupAvailer.getText();
										// String availer_type = (String) lookupAvailerType.getText();
										String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "")
												.replace("</html>", "");

										if (isSelected) {

											/*
											 * String SQL = "SELECT sp_save_transfer_cost_woutrplf('"+entity_id+"', '"
											 * +projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date," +
											 * ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"
											 * +remarks+"','null'), '"+emp_code+"', NULLIF('"
											 * +batch_no+"','null'), NULLIF('"+unit_id+"','null')," +
											 * ""+seq_no+", '"+availer+"', '"+rplf_no+"')";
											 */
											String SQL = "SELECT sp_save_transfer_cost_woutrplf_batch_v2('" + entity_id
													+ "', '" + projcode + "', '" + pbl_id + "', '" + costid + "', '"
													+ trans_date + "'::date," + "" + amount + ", " + setup_amount
													+ ", '" + req_id + "', NULLIF('" + remarks + "','null'), '"
													+ emp_code + "', NULLIF('" + batch_no + "','null'), NULLIF('"
													+ unit_id + "','null')," + "" + seq_no + ", '" + availer + "', '"
													+ rplf_no + "')";

											pgSelect db = new pgSelect();
											FncSystem.out("SQL", SQL);
											db.select(SQL);
										}
									}

									JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information",
											JOptionPane.INFORMATION_MESSAGE);
									// modelTCT_Batch.setRowCount(0);

									// -------added by jed 9-4-18 to avoid multiple saving

									modelTCT_Batch.clear();
									scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables
											.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
									tblTCT_Batch.packAll();

									pnlState(true, true);
									btnTSave.setEnabled(false);
									btnDRF.setEnabled(true);
									lookupCode_Batch.setText("");
									lblCode_Batch.setText("[ ]");
									lblSetupAmount1_Batch.setText("0.00");
									lblBalanceAmount_Batch.setText("0.00");
									txtRemarks_Batch.setEditable(false);
									txtRemarks_Batch.setText("");
									txtAmount_Batch.setEditable(false);
									txtAmount_Batch.setValue(0.00);
									txtRequestedBy_Batch.setText("");
									lblReqBy1_Batch.setText("[ ]");
									to_do = "old";
								} else {

									if (JOptionPane.showConfirmDialog(getContentPane(),
											"Batch no. " + checkBatch(checkBatch) + " is already used." + "\n"
													+ "Generate new batch?",
											"Confirmation",
											JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {

										String new_batch = generateBatchNo_v2();

										lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));

									}
								}
							}
						} else {
							BigDecimal amount_to_save = (BigDecimal) txtAmount_Batch.getValued();
							if (amount_to_save.compareTo(new BigDecimal("0.00")) == 0) {
								JOptionPane.showMessageDialog(getContentPane(), "Amount cannot be zero", "Save",
										JOptionPane.WARNING_MESSAGE);
							} else {
								if (JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?",
										"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
									String checkBatch = (String) lblBatch.getText().replace("<html><b>Batch No: ", "")
											.replace("</html>", "");
									if (checkBatch(checkBatch).equals("")) {// **ADDED BY JED 2019-09-03 TO AVOID SAME
																			// BATCHING**//

										String rplf_no = "";
										for (int x = 0; x < tblTCT_Batch.getRowCount(); x++) {
											Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
											Date trans_date = (Date) dateSched_Batch.getDate();
											BigDecimal amount = (BigDecimal) txtAmount_Batch.getValued(); // new
																											// BigDecimal
																											// (txtAmount_Batch.getText());
											String remarks = (String) txtRemarks_Batch.getText();
											// String desc = (String) lblCode_Batch.getText().replace("<html><font size
											// = 2>[ ", "").replace(" ]</html>","");
											String entity_id = (String) modelTCT_Batch.getValueAt(x, 1);
											String unit_id = (String) modelTCT_Batch.getValueAt(x, 9);
											String projcode = (String) modelTCT_Batch.getValueAt(x, 7);
											String pbl_id = (String) modelTCT_Batch.getValueAt(x, 4);
											Integer seq_no = (Integer) modelTCT_Batch.getValueAt(x, 5);
											String costid = (String) lookupCode_Batch.getValue();
											BigDecimal setup_amount = (BigDecimal) txtAmount_Batch.getValued(); // new
																												// BigDecimal
																												// (txtAmount_Batch.getText());
											String req_id = (String) txtRequestedBy_Batch.getText();
											String emp_code = (String) UserInfo.EmployeeCode;
											// String client_id = (String) lookupClient.getText();
											// String request_type = (String) lookupRequestType.getText();
											String availer = (String) lookupAvailer.getText();
											// String availer_type = (String) lookupAvailerType.getText();
											String batch_no = (String) lblBatch.getText()
													.replace("<html><b>Batch No: ", "").replace("</html>", "");

											if (isSelected) {

												String SQL = "SELECT sp_save_transfer_cost_woutrplf_batch_v2('"
														+ entity_id + "', '" + projcode + "', '" + pbl_id + "', '"
														+ costid + "', '" + trans_date + "'::date," + "" + amount + ", "
														+ setup_amount + ", '" + req_id + "', NULLIF('" + remarks
														+ "','null'), '" + emp_code + "', NULLIF('" + batch_no
														+ "','null'), NULLIF('" + unit_id + "','null')," + "" + seq_no
														+ ", '" + availer + "', '" + rplf_no + "')";

												pgSelect db = new pgSelect();
												FncSystem.out("SQL", SQL);
												db.select(SQL);
											}
										}

										JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information",
												JOptionPane.INFORMATION_MESSAGE);
										// modelTCT_Batch.setRowCount(0);

										// -------added by jed 9-4-18 to avoid multiple saving

										modelTCT_Batch.clear();
										scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables
												.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
										tblTCT_Batch.packAll();

										pnlState(true, true);
										btnTSave.setEnabled(false);
										btnDRF.setEnabled(true);
										lookupCode_Batch.setText("");
										lblCode_Batch.setText("[ ]");
										lblSetupAmount1_Batch.setText("0.00");
										lblBalanceAmount_Batch.setText("0.00");
										txtRemarks_Batch.setEditable(false);
										txtRemarks_Batch.setText("");
										txtAmount_Batch.setEditable(false);
										txtAmount_Batch.setValue(0.00);
										txtRequestedBy_Batch.setText("");
										lblReqBy1_Batch.setText("[ ]");
										to_do = "old";
									} else {

										if (JOptionPane.showConfirmDialog(getContentPane(),
												"Batch no. " + checkBatch(checkBatch) + " is already used." + "\n"
														+ "Generate new batch?",
												"Confirmation",
												JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {

											String new_batch = generateBatchNo_v2();

											lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));

										}
									}
								}
							}
						}
					}
				}
			}
			// }
		}
		if (actionCommand.equals("Cancel RPLF")) {
			lookupRequestType.setValue(null);
			lblRequestType.setText("[ ]");
			lookupAvailer.setValue(null);
			lblAvailer.setText("[ ]");
			lookupAvailerType.setValue(null);
			lblAvailerType.setText("[ ] ");
			lookupControlNo.setText("");
			;
			lblctrNo.setText("[ ]");
			lookupControlNo.setEnabled(false);
			to_do = "new";
		}
		if (actionCommand.equals("Batch w/ out RPLF")) {
			to_do = "old";
			getBatchList();
			btnTSave.setEnabled(true); // ADDED BY LESTER 2016-12-19
			btnDRF.setEnabled(true); // ADDED BY LESTER 2016-12-19
			// CHANGE DISPLAY OF PCOST DETAILS HERE BASED ON retrieved batch no

			// ------added by jed 9-4-18 due to multiple saving

			modelTCT_Batch.clear();
			scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER,
					FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
			tblTCT_Batch.packAll();

			pnlState(false, true);
			// displayTCostClient(null);
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

		if (actionCommand.equals("Remove Row")) {
			if (tabCenter.getSelectedIndex() == 0) {
				int[] selectedRows = tblTCT.getSelectedRows();

				if (selectedRows.length == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select row.", "Remove Row",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				for (int x = selectedRows.length - 1; x > -1; x--) {
					int row = selectedRows[x];

					modelTCT.removeRow(row);
				}

				updatePCD();
				computeTotal();
			}
		}

		if (actionCommand.equals("Cancel")) {
			cancel();

		}
	}

	// ---added by jed 2018-10-15 : for refreshing all fields---//
	private void cancel() {

		if (tabCenter.getSelectedIndex() == 0) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information",
						JOptionPane.INFORMATION_MESSAGE);

				lookupClient.setText("");
				lblClient.setText("[ ]");
				lblProject.setText("[ ]");
				lblPblDescription.setText("[ ]");
				lblSellingAgent.setText("[ ]");
				lblDateReserved.setText("[ ]");
				lblStatus.setText("[ ]");
				lblCode.setText("[ ]");
				lblBatch.setText("<html><b>Batch No:</html>");
				lookupCode.setText(null);
				txtAmount.setText("0.00");
				txtRequestedBy.setText(null);
				lblReqBy1.setText("[ ]");
				txtRemarks.setText(null);
				btnDRF.setEnabled(false);
				btnTSave.setEnabled(false);
				dateSched.setDate(Calendar.getInstance().getTime());
				lblSetupAmount1.setText("0.00");
				lblRunningTotal1.setText("0.00");
				lblBalanceAmount.setText("0.00");
				tblTCT.setEnabled(true);
				modelTCT.setRowCount(0);
				modelTCTTotal.setValueAt(new BigDecimal(0.00), 0, 3);
				txtAmount.setEditable(false);
				txtRequestedBy.setEditable(false);
				dateSched.setEnabled(false);

				pnlState(true, true);

			}
		}

		if (tabCenter.getSelectedIndex() == 1) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information",
						JOptionPane.INFORMATION_MESSAGE);

				modelTCT_Batch.clear();
				scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER,
						FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
				tblTCT_Batch.packAll();

				lookupCode_Batch.setText("");
				lblCode_Batch.setText("[ ]");
				dateSched_Batch.setDate(Calendar.getInstance().getTime());
				txtAmount_Batch.setEditable(false);
				txtAmount_Batch.setText("0.00");
				txtRequestedBy_Batch.setText(null);
				txtRequestedBy_Batch.setEditable(false);
				lblReqBy1_Batch.setText("[ ]");
				txtRemarks_Batch.setEditable(false);
				txtRemarks_Batch.setText(null);
				btnDRF.setEnabled(false);
				btnTSave.setEnabled(false);
				btnBatch.setEnabled(true);
				modelTCT_Batch.setRowCount(0);
				// displayTCostClient(lookupCode_Batch.getValue());
				lblBatch.setText("<html><b>Batch No:</html>");
				lblSetupAmount1_Batch.setText("0.00");
				lblRunningTotal1_Batch.setText("0.00");
				lblBalanceAmount_Batch.setText("0.00");
				lookupcompany.setValue(null);
				txtcompany.setText("");
				lookupcompany.setEditable(true);
				pnlState(true, true);
			}
		}
	}

	public void displayTCostDetails(String pbl_id) {

		if (tabCenter.getSelectedIndex() == 0) {
			FncTables.clearTable(modelTCT);// Code to clear modelMain.
			DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.

			String sql = "SELECT false, trim(b.tcostdtl_desc), a.tran_date, a.tcost_amt, a.remarks, a.rplf_no, null, null, a.batch_no \n"
					+ "FROM rf_transfer_cost a \n"
					+ "LEFT JOIN (SELECT * FROM mf_transfer_cost_dl ) b ON a.tcostid_dl = b.tcostdtl_id and a.co_id = b.co_id and a.projcode = b.proj_id and b.status_id ='A' \n"
					+ "WHERE a.status_id ='A' \n" + "AND a.entity_id = '" + lookupClient.getValue() + "'\n"
					+ "AND a.pbl_id = '" + pbl_id + "' \n" + "ORDER BY a.tcostid_dl";

			FncSystem.out("Display TCOST details", sql);
			pgSelect db = new pgSelect();
			db.select(sql);

			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					modelTCT.addRow(db.getResult()[x]);
					listModel.addElement(modelTCT.getRowCount());
				}
				scrollTCT.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
						FncTables.getRowHeader_Footer(Integer.toString(tblTCT.getRowCount())));
			}
			BigDecimal total = new BigDecimal(0.00);
			for (int y = 0; y < modelTCT.getRowCount(); y++) {
				total = total.add((BigDecimal) modelTCT.getValueAt(y, 3));
				modelTCTTotal.setValueAt(total, 0, 3);
			}
			tblTCT.packAll();
		}

	}

	private void displayTCostClient(String code_id, String tcostid_dl) { // --- 1st modified by JED 2018-09-20 : for
																			// displaying clients with two lots---//
																			// ---2nd modified by JED 2018-10-16 : DCRF
																			// no. 797 put control on tagging
																			// particulars in TCOST to avoid double
																			// tagging---//

		FncTables.clearTable(modelTCT_Batch);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.

		/*
		 * String sql = "SELECT * from view_tcost_detail (NULLIF ('" + code_id +
		 * "' , 'null'), NULLIF ('" + tcostid_dl + "' , 'null'))";
		 */
		// add retag batch
		String sql = "SELECT * from view_tcost_detail_debug ('" + code_id + "', '" + tcostid_dl + "')";

		FncSystem.out("displayTCostClient", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display SQL For TCost", sql);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelTCT_Batch.addRow(db.getResult()[x]);
				listModel.addElement(modelTCT_Batch.getRowCount());
			}
		}
		scrollTCT_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblTCT_Batch.getRowCount())));
		tblTCT_Batch.packAll();
	}

	private static String SQL_REQUESTTYPE() {
		return "SELECT rplf_type_id as \"Type ID\", trim(rplf_type_desc) as \"Description\"  \n"
				+ "FROM mf_rplf_type where status_id = 'A' " + "ORDER BY rplf_type_id ";
	}

	private static String ControlNoLookUpValue() {
		return "SELECT TRIM(control_no) AS \"Control Number\",TRIM(tcost_desc) AS \"TCost Description\" FROM rf_tcost_revolving_funds";
	}

	private static String SQL_AVAILER() {
		String SQL = "SELECT trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\", entity_kind as \"Kind\", vat_registered as \"VAT\"  \n"
				+ "FROM (select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in \r\n"
				+ "(select entity_id from rf_entity_assigned_type "
				+ " ) and status_id ='A' and server_id is null) a \n" + "ORDER BY a.entity_name  ";

		FncSystem.out("DISPLAY AVAILER", SQL);
		return SQL;

	}

	private static String SQL_AVAILERTYPE(String entity_id) {
		return "SELECT a.entity_type_id, trim(b.entity_type_desc), c.wtax_id, c.wtax_rate \n"
				+ "FROM (select * from rf_entity_assigned_type where trim(entity_id) =  '" + entity_id + "' ) a \r\n"
				+ "LEFT JOIN mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n"
				+ "LEFT JOIN rf_withholding_tax c on b.wtax_id = c.wtax_id";
	}

	private static Object[] getTransferCostDetails(String rplf_no) {
		String SQL = "SELECT a.tcostid_dl, a.tran_date, a.tcost_amt, get_client_name(b.entity_id), a.requested_by, a.remarks, a.tcost_amt, a.batch_no\n"
				+ "FROM rf_transfer_cost a\n" + "LEFT JOIN em_employee b on a.requested_by = b.emp_code\n"
				+ "WHERE rplf_no = '" + rplf_no + "' and status_id = 'A' and server_id is null;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private static Object[] getTransferCostDetails_IND(String cost_desc, String batch, String entity_id) {
		String SQL = "SELECT a.tcostid_dl, a.tran_date, a.tcost_amt, get_employee_name(a.requested_by), a.requested_by, a.remarks, a.setup_amt, a.batch_no\n"
				+ "from rf_transfer_cost a\n" + "left join em_employee b on a.created_by = b.entity_id \n"
				+ "left join mf_transfer_cost_dl c on a.tcostid_dl = c.tcostdtl_id and a.projcode = c.proj_id and coalesce(a.server_id, '') = coalesce(c.server_id, '') and c.status_id ='A' \n"
				+ "WHERE trim(tcostdtl_desc) = trim('" + cost_desc + "') and batch_no = '" + batch + "' and a.entity_id = '"
				+ entity_id + "' and a.status_id = 'A' ";

		FncSystem.out("TRANSFER COST DETAILS: ", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private static Object[] getTransferCostDetails1_IND(String cost_desc, String requester) {
		String SQL = "select tcostdtl_id, tcostdtl_desc, tcostdtl_amt, get_employee_name('" + requester + "')\n"
				+ "from mf_transfer_cost_dl\n" + "where tcostdtl_desc = '" + cost_desc + "' and status_id = 'A'";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private static Object[] getTransferCostDetails1(String cost_desc) {
		String SQL = "SELECT tcostdtl_id, tcostdtl_amt, remarks\n" + "FROM mf_transfer_cost_dl \n"
				+ "WHERE trim(tcostdtl_desc) = '" + cost_desc + "' and status_id = 'A'";

		FncSystem.out("SQL for TCOST Cost ID", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private void displayTransferCostDetails_IND(String cost_desc, String batch, String entity_id) {

		System.out.println("Dumaan dito NUMBER 2!!!!!!!!!!!!!!!!!!!!!");

		Object[] data = getTransferCostDetails_IND(cost_desc, batch, entity_id);

		if (tabCenter.getSelectedIndex() == 0) {
			if (data != null) {
				int row = tblTCT.convertRowIndexToModel(tblTCT.getSelectedRow());
				String code = (String) data[0];
				Date tran_date = (Date) modelTCT.getValueAt(row, 2);
				BigDecimal amount = (BigDecimal) modelTCT.getValueAt(row, 3);
				String remarks = (String) modelTCT.getValueAt(row, 4);
				BigDecimal setup_amt = (BigDecimal) data[6];
				String reqby = (String) data[4];
				String reqbyname = (String) data[3];

				lookupCode.setValue(code);
				lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", modelTCT.getValueAt(row, 1)));
				dateSched.setDate(tran_date);
				txtRequestedBy.setText(reqby);
				lblReqBy1.setText(String.format("[ %s ]", reqbyname));
				txtAmount.setValue(amount);
				txtRemarks.setText(remarks);
				txtRemarks.setToolTipText(remarks);
				lblSetupAmount1.setText(String.format("%s", setup_amt));
			} else {
				String batch_no = (String) generateBatchNo();
				String requester = UserInfo.EmployeeCode;

				Object[] data1 = getTransferCostDetails1_IND(cost_desc, requester);

				int row1 = tblTCT.convertRowIndexToModel(tblTCT.getSelectedRow());
				String code = (String) data1[0];
				Date tran_date1 = (Date) modelTCT.getValueAt(row1, 2);
				BigDecimal amount1 = (BigDecimal) modelTCT.getValueAt(row1, 3);
				String remarks1 = (String) modelTCT.getValueAt(row1, 4);
				BigDecimal setup_amt1 = (BigDecimal) data1[2];
				String reqby1 = requester;
				String reqbyname1 = (String) data1[3];

				lookupCode.setText(code);
				lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", modelTCT.getValueAt(row1, 1)));
				dateSched.setDate(tran_date1);
				txtAmount.setValue(amount1);
				txtRequestedBy.setText(reqby1);
				lblReqBy1.setText(String.format("[ %s ]", reqbyname1));
				txtRemarks.setText(remarks1);
				lblSetupAmount1.setText(String.format("%s", setup_amt1));
				lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));

			}
		}
	}

	private JPanel displayTableNavigation() {

		btnRemoveRow = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnRemoveRow);

		return pnl;
	}

	private void updatePCD() {
		scrollTCTTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblTCT.getRowCount())));
		tblTCT.packAll();
		if (tblTCT.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
			tblTCT.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		for (int row = 0; row < tblTCT.getRowCount(); row++) {
			((DefaultListModel) rowHeaderTCT.getModel()).addElement(row + 1);
		}
	}

	private static String rplfNo() {
		String rplf = "";

		String SQL = "select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '02' ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				rplf = "000000001";
			} else {
				rplf = (String) db.getResult()[0][0];
			}

		} else {
			rplf = "000000001";
		}

		return rplf;
	}

	private boolean validateSaving() {
		if (tabCenter.getSelectedIndex() == 0) {
			if (lookupClient.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Client", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupCode.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Code", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupRequestType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Request Type", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailer.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailerType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer Type", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (tabCenter.getSelectedIndex() == 1) {
			if (lookupCode_Batch.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Code", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupRequestType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Request Type", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailer.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailerType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer Type", "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;

	}

	private static Object[] reg_amount(String entity, String pbl, String buyertype) {
		String SQL = "SELECT get_lra_amount_tcost_transfercost('" + entity + "', '" + pbl + "', '" + buyertype + "');";

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display amount", SQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private static Object[] reg_amount2(String entity, String pbl, String buyertype) {
		String SQL = "SELECT get_lra_amount_tcost('" + entity + "', '" + pbl + "', '" + buyertype + "');";

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display amount", SQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private static Object[] reg_amount3(String entity, String pbl, String buyertype) {
		String SQL = "SELECT get_lra_amount('" + entity + "', '" + pbl + "', '" + buyertype + "');";

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display amount", SQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private BigDecimal getTCost_Dtl_amt(String tcost_dtl_id, String entity_id, String proj_id, String pbl_id,
			Integer seq_no) {
		String SQL = "SELECT get_tcost_dtl_amt('" + tcost_dtl_id + "', '" + entity_id + "', '" + proj_id + "', '"
				+ pbl_id + "', " + seq_no + ")";
		FncSystem.out("getTCost_Dtl_amt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			return (BigDecimal) db.getResult()[0][0];
		} else {
			return new BigDecimal("0.00");
		}
	}

	private void addTCostEntries(String entity_id, String proj_id, String pbl_id, Integer seq_no,
			DefaultTableModel model) {

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM add_tcost_rows('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', " + seq_no
				+ ")";
		db.select(SQL);

		FncSystem.out("SQL for adding TCOST entries", SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
			}
		}
	}

	private void addTCostEntries_v2(String entity_id, String proj_id, String pbl_id, Integer seq_no,
			DefaultTableModel model) {

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM add_tcost_rows_v2('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', "
				+ seq_no + ")";
		db.select(SQL);

		FncSystem.out("SQL for adding TCOST entries", SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
			}
		}
	}

	private boolean withZeroAmount() {
		Boolean with_zero = false;

		for (int x = 0; x < modelTCT.getRowCount(); x++) {
			Boolean isSelected = (Boolean) modelTCT.getValueAt(x, 0);

			if (isSelected) {
				BigDecimal amount = (BigDecimal) modelTCT.getValueAt(x, 3);
				if (amount.compareTo(new BigDecimal("0.00")) == 0) {
					with_zero = true;
				}
			}
		}

		return with_zero;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int index = tabCenter.getSelectedIndex();

		if (index == 0) {
			if (e.getSource().equals(modelTCT)) {
				int row = tblTCT_Batch.convertRowIndexToModel(modelTCT.getSelectedRow());
				System.out.println("selected row: " + row);
				delete();
			}
			if (e.getSource().equals(tblTCT_Batch)) {
				int row = tblTCT_Batch.convertRowIndexToModel(tblTCT_Batch.getSelectedRow());
				Boolean isSelected2 = (Boolean) modelTCT_Batch.getValueAt(row, 0);

				for (int x = 0; x < modelTCT_Batch.getRowCount(); x++) {
					Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
					if (isSelected) {

						// ----if batch statement added by jed 9-4-18 to for tagging of clients in
						// existing batch no----//

						if (batch() == false) {
							// lookupCode_Batch.setEnabled(true);
							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
							pnlState(true, false);
							btnTSave.setEnabled(true);
						}
					} else {
						// lookupCode_Batch.setEnabled(false);
						pnlState(true, false);
						btnTSave.setEnabled(true);
					}
				}

				if (isSelected2) {
					// lookupCode_Batch.setEnabled(true);
				}
			}

		}

		if (index == 1) {
			if (e.getSource().equals(tblTCT_Batch)) {
				int row = tblTCT_Batch.convertRowIndexToModel(tblTCT_Batch.getSelectedRow());
				Boolean isSelected2 = (Boolean) modelTCT_Batch.getValueAt(row, 0);

				for (int x = 0; x < modelTCT_Batch.getRowCount(); x++) {
					Boolean isSelected = (Boolean) modelTCT_Batch.getValueAt(x, 0);
					if (isSelected) {

						// ----if batch statement added by jed 9-4-18 to for tagging of clients in
						// existing batch no----//

						if (batch() == false) {
							// lookupCode_Batch.setEnabled(true);
							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
							pnlState(false, true);
							btnTSave.setEnabled(true);
						}
					} else {
						// lookupCode_Batch.setEnabled(false);
						pnlState(false, true);
						btnTSave.setEnabled(true);
					}
				}

				if (isSelected2) {
					// lookupCode_Batch.setEnabled(true);
				}
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

	private void checkAllClientList() {//

		int rw = tblTCT_Batch.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String name = "";

			try {
				name = tblTCT_Batch.getValueAt(x, 2).toString().toUpperCase();
			} catch (NullPointerException e) {
				name = "";
			}

			String acct_name = txtSearch.getText().trim().toUpperCase();
			Boolean match = name.indexOf(acct_name) > 0;
			Boolean start = name.startsWith(acct_name);
			Boolean end = name.endsWith(acct_name);

			if (match == true || start == true || end == true) {
				tblTCT_Batch.setRowSelectionInterval(x, x);
				tblTCT_Batch.changeSelection(x, 2, false, false);
				break;
			} else {
				tblTCT_Batch.setRowSelectionInterval(0, 0);
				tblTCT_Batch.changeSelection(0, 2, false, false);
			}

			x++;

		}
	}

	private static String generateBatchNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(NULLIF(trim(batch_no), '')::INT), 0) + 1, 'FM0000000000') FROM rf_transfer_cost WHERE NULLIF(TRIM(batch_no), 'null') IS not null";

		FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	private static String generateBatchNo_v2() {// **ADDED BY JED 2019-09-03 : GENERATE NEW BATCH TO AVOID SAME
												// BATCHING**//
		String strSQL = "SELECT to_char(COALESCE(MAX(NULLIF(trim(batch_no), '')::INT), 0) + 1, 'FM0000000000') FROM tmp_tcost_batch_no WHERE NULLIF(TRIM(batch_no), 'null') IS not null and status_id = 'A';";

		// FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	private void getBatchList() {// XXX Preview Report
		if (lookupcompany.getValue() != null) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Units",
					SQL_BatchList(lookupcompany.getValue()), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				String batch = (String) data[0];
				lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch));
			}
		} else {
			JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select company first", "",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private static String SQL_BatchList(String co_id) {
		String strSQL = "SELECT batch_no as \"Batch No\", sum(setup_amt) as \"Amount\"\n" + "FROM rf_transfer_cost\n" +
		// "WHERE (case when '"+UserInfo.EmployeeCode+"' = '900449' then true else
		// created_by = '"+ UserInfo.EmployeeCode +"' end) \n" +
				"WHERE COALESCE (rplf_no, '') = '' \n" + "AND status_id = 'A' and co_id = '" + co_id + "' \n" +
				// "AND server_id is null \n" +
				"GROUP BY batch_no \n" + "ORDER BY batch_no desc";

		FncSystem.out("Batch No", strSQL);
		return strSQL;
	}

	private boolean batch() {

		boolean x = false;
		String batch = "";

		String strSQL = "select batch_no from rf_transfer_cost where status_id = 'A' and batch_no = '"
				+ lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "")
				+ "' and rplf_no is not null and created_by = '" + UserInfo.EmployeeCode + "'";

		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()) {
			batch = (String) db.getResult()[0][0];
		}

		if (batch.equals(null) || batch.isEmpty()) {
			x = false;
			System.out.println(x);
		} else {
			x = true;
		}

		return x;
	}

	private static void pnlState(Boolean IND, Boolean BATCH) {

		tabCenter.setEnabledAt(0, IND);
		tabCenter.setEnabledAt(1, BATCH);

	}

	private static String checkPBL(String oth_pbl_id) {

		String pbl_id = "";
		String SQL = "select\n" + "pbl_id \n" + "from hs_sold_other_lots x \n" + "where x.oth_pbl_id = '" + oth_pbl_id
				+ "' and x.status_id = 'A' and server_id is null";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				pbl_id = (String) db.getResult()[0][0].toString();
			}
		} else {
			pbl_id = "";
		}

		return pbl_id;

	}

	private static BigDecimal getDocStampMortgage(String tcostid_dl, String entity_id, String proj_id, String pbl_id,
			Integer seq_no) {

		BigDecimal doc_amt = BigDecimal.valueOf(0.00);

		String SQL = "SELECT * FROM get_tcost_doc_stamp_mort ('" + tcostid_dl + "', '" + entity_id + "', '" + proj_id
				+ "', '" + pbl_id + "', '" + seq_no + "')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((BigDecimal) db.getResult()[0][0] == BigDecimal.valueOf(0.00)) {
				doc_amt = BigDecimal.valueOf(0.00);
			} else {
				doc_amt = (BigDecimal) db.getResult()[0][0];
			}

		} else {
			doc_amt = BigDecimal.valueOf(0.00);
		}

		return doc_amt;

	}

	private static String checkBatch(String batch_no) {

		String batch = "";

		String SQL = "select\n" + "batch_no\n" + "from tmp_tcost_batch_no\n" + "where batch_no = '" + batch_no
				+ "' and status_id = 'A'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				batch = (String) db.getResult()[0][0];
			}
		} else {
			batch = "";
		}

		return batch;
	}

	private ArrayList<String> getParticulars() {// ADDED BY JED 2020-03-04 : AS PER REQUEST OF MAM ELA, TAGGING OF SALES
												// AND MORT ON BATCH
		ArrayList<String> listParticulars = new ArrayList<String>();
		listParticulars.add("123"); // DOC STAMP MORTGAGE
		listParticulars.add("034"); // DOC STAMP SALE
		listParticulars.add("193"); // REGISTRATION OF MORTGAGE
		listParticulars.add("009"); // ENTRY FEE MORTGAGE
		listParticulars.add("012"); // JUDICIAL FORM FEE
		listParticulars.add("013"); // LEGAL RESEARCH FEE OF MORTGAGE
		listParticulars.add("008"); // ENTRY FEE
		listParticulars.add("011"); // ISSUANCE OF TITLE
		listParticulars.add("021"); // RESEARCH FEE
		listParticulars.add("169"); // REGISTRATION FEE TRANSFER OF TCT

		return listParticulars;
	}

	private void delete() {

		if (ifTrue() == true) {

			JOptionPane.showMessageDialog(getContentPane(), "You are currently tagging a particular!", "Error",
					JOptionPane.ERROR_MESSAGE);

		} else {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this particular?",
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)

				if (checkingRPLF()) {
					JOptionPane.showMessageDialog(getContentPane(),
							"Only particular without rplf number can be deleted!", "Information",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					pgUpdate db = new pgUpdate();
					deleteTCOSTParticular(db);
					db.commit();
					JOptionPane.showMessageDialog(getContentPane(), "Successfully deleted!", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					displayTCostDetails(pbl_id);
					lblCode.setText("[ ]");
					lookupCode.setText(null);
					lookupCode.setEnabled(true);
					txtAmount.setText("0.00");
					txtRequestedBy.setText(null);
					lblReqBy1.setText("[ ]");
					txtRemarks.setText(null);
					dateSched.setDate(FncGlobal.getDateToday());
					lblSetupAmount1.setText("0.00");
					lblRunningTotal1.setText("0.00");
					lblBalanceAmount.setText("0.00");
					txtAmount.setEditable(false);
					txtRequestedBy.setEditable(false);
					dateSched.setEnabled(false);
					mniDelete.setEnabled(false);

				}

		}
	}

	private Boolean ifTrue() {

		Boolean a = false;

		for (int x = 0; x < modelTCT.getRowCount(); x++) {

			Boolean isSelected = (Boolean) modelTCT.getValueAt(x, 0);

			if (isSelected) {
				a = true;
			} else
				a = false;
		}
		return a;
	}

	private boolean checkingRPLF() {

		Boolean existing = true;

		int rw = modelTCT.getSelectedRow();

		if (tblTCT.getSelectedRows().length > 0) {

			String batch_no = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(), 8);
			String tcostid_dl = (String) lookupCode.getText();
			String entity_id = (String) lookupClient.getValue();

			String sql = "select * from rf_transfer_cost \n" + "where entity_id = '" + entity_id + "' \n"
					+ "and batch_no = '" + batch_no + "' \n" + "and tcostid_dl = '" + tcostid_dl + "' \n"
					+ "and NULLIF(rplf_no,'') is null  \n" +
					// "and server_id is null \n" +
					"and status_id = 'A'";

			pgSelect db = new pgSelect();
			db.select(sql);

			if (db.isNotNull()) {
				existing = false;
			}

			FncSystem.out("CHECKING RPLF NO HERE!!!!", sql);

		}
		return existing;
	}

	private void deleteTCOSTParticular(pgUpdate db) {

		int rw = modelTCT.getSelectedRow();

		if (tblTCT.getSelectedRows().length > 0) {

			String batch_no = (String) modelTCT.getValueAt(tblTCT.getSelectedRow(), 8);
			String tcostid_dl = (String) lookupCode.getText();
			String entity_id = (String) lookupClient.getValue();
			String user_code = UserInfo.EmployeeCode;

			String sql = "update rf_transfer_cost set status_id = 'I', edited_by = '" + user_code
					+ "', date_edited = NOW()::varchar \n" + "where entity_id = '" + entity_id + "' \n"
					+ "and batch_no = '" + batch_no + "' \n" + "and tcostid_dl = '" + tcostid_dl + "' \n"
					+ "and status_id = 'A'";

			FncSystem.out("SQL for deleting particular", sql);
			db.executeUpdate(sql, false);
			FncSystem.out("UPDATE DELETED", sql);
		}
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}

		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	public static String SQL_COMPANY() {// XXX Company
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", "
				+ "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return SQL;
	}
}