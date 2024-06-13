package Utilities;

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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
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
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import Buyers.ClientServicing._CARD;
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
import tablemodel.model_pcost;

public class PCOST_Retagging extends _JInternalFrame implements _GUI, MouseListener {
	
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
	JPanel pnlCreateOR;
	JPanel pnlBuyer;
	JPanel pnlBlockLot;
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
	JLabel lblCode_BlockLot;
	JLabel lblRunningTotal1_BlockLot;
	JLabel lblSetupAmount1_BlockLot;
	JLabel lblBalanceAmount_BlockLot;
	JLabel lblRunningTotal1_Batch;
	JLabel lblSetupAmount1_Batch;
	JLabel lblBalanceAmount_Batch;
	JLabel lblCode_Batch;
	
	_JXFormattedTextField txtAmount;
	JXTextField txtRemarks;
	_JXFormattedTextField txtAmount_BlockLot;
	JXTextField txtRemarks_BlockLot;
	_JXFormattedTextField txtAmount_Batch;
	JXTextField txtRemarks_Batch;
	JXTextField txtSearch;
	JXTextField txtRequestedBy;
	JXTextField txtRequestedBy_BlockLot;
	JXTextField txtRequestedBy_Batch;
	JXTextField txtORNo;
	JXTextField txtYear;
	
	JComboBox cmbType;
	JComboBox cmbType_BlockLot;
	JComboBox cmbType_Batch;
	

	//JButton btnClear;
	//JButton btnClear2;
	JButton btnEntry;
	JButton btnDRF;
	JButton btnRemoveRow;
	JButton btnAddRow;
	JButton btnSave;
	JButton btnCanceOR;
	JButton btnORSave;
	JButton btnCancelRPLF;
	JButton btnTSave;
	JButton btnBatch;
	JButton btnEdit;
	JButton btnOR;

	_JLookup lookupClient;
	_JLookup lookupCode;
	_JLookup lookupProject;
	_JLookup lookupUnit;
	_JLookup lookupRequestType;
	_JLookup lookupAvailer;
	_JLookup lookupAvailerType;
	_JLookup lookupCode_BlockLot;
	static _JLookup lookupCode_Batch;
	//_JLookup lookupRequestedBy;
	//_JLookup lookupRequestedBy_BlockLot;
	//_JLookup lookupRequestedBy_Batch;
	
	_JDateChooser dateSched;
	_JDateChooser dateSched_BlockLot;
	_JDateChooser dateSched_Batch;
	
	private static JTabbedPane tabCenter;
	
	_JScrollPaneTotal scrollPCDTotal;
	_JTableTotal tblPCDTotal;
	model_pcost modelPCDTotal;
	
	_JScrollPaneTotal scrollPCDTotal_BlockLot;
	_JTableTotal tblPCDTotal_BlockLot;
	model_pcost modelPCDTotal_BlockLot;
	
	Object[] clientDetails;

	model_pcost modelPCD;
	_JScrollPaneMain scrollPCD;
	_JTableMain tblPCD;
	JList rowHeaderPCD;
	
	model_pcost modelPCD_BlockLot;
	_JScrollPaneMain scrollPCD_BlockLot;
	_JTableMain tblPCD_BlockLot;
	JList rowHeaderPCD_BlockLot;
	
	model_client modelPCD_Batch;
	//_JScrollPaneMain scrollPCD_Batch;
	JScrollPane scrollPCD_Batch;
	_JTableMain tblPCD_Batch;
	JList rowHeaderPCD_Batch;
	
	String entityid = "";
	static String user = UserInfo.EmployeeCode;

	static String title = "PCOST (For Retagging)";
	Dimension frameSize = new Dimension(900, 550);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblType;
	private JLabel lblDate;               
	private JLabel lblAmount;
	private JLabel lblReqBy;
	private JLabel lblReqBy1;
	private JLabel lblReqBy1_BlockLot;
	private JLabel lblReqBy1_Batch;
	private JLabel lblRemarks;
	private JLabel lblInfo;
	private String batch_no;
	private String pbl;
	private String entity;
	private String buyertype;
	private String entity2;
	private String unitid;
	private Integer seqno;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	DecimalFormat df = new DecimalFormat("#,###,##0.00");

	private JButton btnCancel;

	private JPopupMenu menu;

	private JMenuItem mniDelete;

	private JMenuItem mniTag;

	private String selected_batch;

	private JSeparator mniBlank;

	private JMenuItem mniTag_2;
	
	public PCOST_Retagging() {
		super(title, false, true, false, true);
		initGUI();
	}

	public PCOST_Retagging(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public PCOST_Retagging(String title, boolean resizable,
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

//			{//--added by JED 2018-11-21 : for double tagging--//
//				menu = new JPopupMenu("Popup");
//				mniTag = new JMenuItem("Tag w/ old Batch");
//				menu.add(mniTag);
//				mniTag.setEnabled(false);
//				mniTag.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						tag();
//						
//					}
//				});
//				
//				mniTag_2 = new JMenuItem("Tag w/ new Batch");
//				menu.add(mniTag_2);
//				mniTag_2.setEnabled(false);
//				mniTag_2.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						tag_new_batch();
//						
//					}
//				});
//				
//				mniBlank = new JSeparator();
//				menu.add(mniBlank);
//				mniDelete = new JMenuItem("Delete       ");
//				menu.add(mniDelete);
//				mniDelete.setEnabled(false);
//				mniDelete.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						delete();
//						
//					}
//				});
//			}
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
					lookupAvailer.setFilterName(true);
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
				pnlCreateOR= new JPanel();
				pnlCreateOR.setLayout(null);
				pnlCreateOR.setPreferredSize(new java.awt.Dimension(290, 100));
				{
					lblNorth = new JLabel("OR No");
					pnlCreateOR.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 10, 120, 22);
				}
				{
					txtORNo = new JXTextField();
					pnlCreateOR.add(txtORNo, BorderLayout.WEST);
					txtORNo.setPreferredSize(new Dimension(150, 20));
					txtORNo.setBounds(110, 10, 150, 22);
				}
				{
					lblNorth = new JLabel("Year");
					pnlCreateOR.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 35, 120, 22);
				}
				{
					txtYear = new JXTextField();
					pnlCreateOR.add(txtYear, BorderLayout.WEST);
					txtYear.setPreferredSize(new Dimension(150, 20));
					txtYear.setBounds(110, 35, 150, 22);
				}
				{
					btnORSave = new JButton("Save OR");
					pnlCreateOR.add(btnORSave);
					btnORSave.setActionCommand("Save OR");
					btnORSave.setBounds(70, 60, 100, 30);
					btnORSave.addActionListener(this);
				}
				{
					btnCanceOR = new JButton("Cancel");
					pnlCreateOR.add(btnCanceOR);
					btnCanceOR.setActionCommand("Cancel OR");
					btnCanceOR.setBounds(175, 60, 100, 30);
					btnCanceOR.addActionListener(this);
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
//											displayPCostDetails();
//											
//											lookupCode.setLookupSQL(SQL_PCOST(entity_id));
//											
//											lblBatch.setText("<html><b>Batch No:</html>");
//											lblCode.setText("[ ]");
//											lookupCode.setText("");
//											txtAmount.setText("0.00");
//											txtRequestedBy.setText(null);
//											lblReqBy1.setText("[ ]");
//											txtRemarks.setText(null);
//											cmbType.setSelectedItem("Payment");
//											dateSched.setDate(Calendar.getInstance().getTime());
//											lblSetupAmount1.setText("0.00");
//											lblRunningTotal1.setText("0.00");
//											lblBalanceAmount.setText("0.00");
//											
//											btnBatch.setEnabled(false);
//											btnTSave.setEnabled(false);
//											
//											
//											KEYBOARD_MANAGER.focusNextComponent();
//									
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
//						}
//					{
//						pnlCenter = new JPanel();
//						pnlBuyer.add(pnlCenter, BorderLayout.CENTER);
//						pnlCenter.setLayout(new BorderLayout(5, 5));
//						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details"));
//						pnlCenter.setPreferredSize(new Dimension(800, 340));
//						{
//							pnlTable = new JPanel(new BorderLayout(5, 5));
//							pnlCenter.add(pnlTable, BorderLayout.WEST);
//							pnlTable.setPreferredSize(new Dimension(460, 200));
//							pnlTable.setBorder(lineBorder);
//							{
//								scrollPCD = new _JScrollPaneMain();
//								pnlTable.add(scrollPCD, BorderLayout.CENTER);
//								modelPCD = new model_pcost();
//								modelPCD.addTableModelListener(new TableModelListener() {
//									public void tableChanged(TableModelEvent e) {
//										if(e.getType() == TableModelEvent.DELETE){
//											rowHeaderPCD.setModel(new DefaultListModel());
//										}
//										if(e.getType() == TableModelEvent.INSERT){
//											((DefaultListModel)rowHeaderPCD.getModel()).addElement(modelPCD.getRowCount());
//										}
//									}
//								});
//
//								tblPCD = new _JTableMain(modelPCD);
//								tblPCD.packAll();
//								tblPCD.setAlignmentX(LEFT_ALIGNMENT);
//								scrollPCD.setViewportView(tblPCD);
//								tblPCD.addMouseListener(new PopupTriggerListener_panel());
//							
//								tblPCD.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//									public void valueChanged(ListSelectionEvent arg0) {
//										try {
//											if(!arg0.getValueIsAdjusting()){
//											
//												String rplf_no = (String) modelPCD.getValueAt(tblPCD.getSelectedRow(), 6);
//												String cost_desc = (String) modelPCD.getValueAt(tblPCD.getSelectedRow(), 1);
//												String entity_id = lookupClient.getValue();
//												String batch = (String) modelPCD.getValueAt(tblPCD.getSelectedRow(), 11);
//												Boolean isSelected = (Boolean) modelPCD.getValueAt(tblPCD.getSelectedRow(), 0);
//												
//												
//												System.out.printf("The value of rplf is: %s\n", rplf_no);
//												System.out.printf("The value of cost_dec is: %s\n", cost_desc);
//												
////												if (rplf_no == ("") || rplf_no == (null))
////												{
////													displayProcessingCostDetails1_IND(cost_desc, entity_id, batch);
////												}
////												else
////												{
////													displayProcessingCostDetails_IND(rplf_no, cost_desc, entity_id, batch);
////												}
//												displayProcessingCostDetails_IND(cost_desc, entity_id, batch);
//												//displayProcessingCostDetails_IND(rplf_no, cost_desc, entity_id, batch);
//												
//												if(tblPCD.getSelectedRows().length > 0){
//													
//													if (isSelected){
//														txtAmount.setEditable(true);
//														mniTag.setEnabled(false);
//														mniTag_2.setEnabled(false);
//														mniDelete.setEnabled(false);
//													}else{
//														txtAmount.setEditable(false);
//														mniTag.setEnabled(true);
//														mniTag_2.setEnabled(true);
//														mniDelete.setEnabled(true);
//													}
//												}
//											}
//										} 
//										catch (ArrayIndexOutOfBoundsException e) { }
//									}
//								});
//								tblPCD.putClientProperty("terminateEditOnFocusLost", true);
//								{
//									rowHeaderPCD = tblPCD.getRowHeader();
//									rowHeaderPCD.setModel(new DefaultListModel());
//									scrollPCD.setRowHeaderView(rowHeaderPCD);
//									scrollPCD.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
//								}
//
//							}
//							{
//								scrollPCDTotal = new _JScrollPaneTotal(scrollPCD);
//								pnlTable.add(scrollPCDTotal, BorderLayout.SOUTH);
//								{
//									modelPCDTotal = new model_pcost();
//									modelPCDTotal.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00), null, null, null, null, null, null, null });
//
//									tblPCDTotal = new _JTableTotal(modelPCDTotal, tblPCD);
//									scrollPCDTotal.setViewportView(tblPCDTotal);
//
//									tblPCDTotal.setTotalLabel(2);
//								}
//								scrollPCDTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
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
//									JPanel pnlLabel = new JPanel(new GridLayout(6, 0, 5, 5));
//									pnlInfo.add(pnlLabel, BorderLayout.WEST);
//									{
//										lblInfo = new JLabel("Code");
//										pnlLabel.add(lblInfo);
//										lblInfo.setHorizontalAlignment(JLabel.RIGHT);
//
//										lblType = new JLabel("Type");
//										pnlLabel.add(lblType);
//										lblType.setHorizontalAlignment(JLabel.RIGHT);
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
//									JPanel pnlText = new JPanel(new GridLayout(6, 0, 5, 5));
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
//																lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
//																txtAmount.setValue(data [2]);
//																lblSetupAmount1.setText(String.format("%s", data[2]));
//																lblBalanceAmount.setText(String.format("%s", lblSetupAmount1.getText()));
//																txtRequestedBy.setText(emp_code);
//																lblReqBy1.setText(String.format("[ %s ]", emp_name));
//																pnlState(true, false, false);
//																
//																if (data[3] != null){
//																	txtRemarks.setText(String.format("%s", data[3]));
//																} else {
//																	txtRemarks.setText(" ");
//																}
//																
//																if(data != null){
//																	BigDecimal amount = new BigDecimal (String.format("%s", reg_amount2(entity, pbl, buyertype)));
//																	BigDecimal amount1 = new BigDecimal (String.format("100.00"));
//																	if (lookupCode.getValue().equals("109") || lookupCode.getValue().equals("210") || lookupCode.getValue().equals("105") || lookupCode.getValue().equals("108")){
//																		modelPCD.addRow(new Object []{true, "ENTRY FEE DEED OF ASSIGNMENT", Calendar.getInstance().getTime(), new BigDecimal(30.00), "Fixed Amt", null, null, null, "Payment", null, null});
//																		modelPCD.addRow(new Object []{true, "JUDICIAL FORM FEE", Calendar.getInstance().getTime(), new BigDecimal(30.00), "DUE TO RD COMPUTERIZATION (originally encoded as it fee, revised by sir alan 7/26/10)", null, null, null, "Payment", null, null});
//																		modelPCD.addRow(new Object []{true, "REGISTRATION FEE OF ASSIGNMENT", Calendar.getInstance().getTime(), amount, "Refer to RD Table", null, null, null, "Payment", null, null});
//																		modelPCD.addRow(new Object []{true, "RESEARCH FEE  ASSIGNMENT", Calendar.getInstance().getTime(), amount.divide(amount1), null, null, null, null, "Payment", null, null});
//																	} else {
//																		if (data[3] == null) {
//																			modelPCD.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], "", null, null, null, "Payment", null, null});
//																			} else {
//																				if (data[3].equals("Fixed Amt")) {
//																					modelPCD.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, null});
//																				} else {
//																					modelPCD.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, null});
//																				}
//																			}
//																	}
//																	if (lookupCode.getValue().equals("215") || lookupCode.getValue().equals("216")) {
//																		btnOR.setEnabled(true);
//																	} else {
//																		btnOR.setEnabled(false);
//																	}
//																	rowHeaderPCD.setModel(new DefaultListModel());
//
//																	for(int y =1; y<=modelPCD.getRowCount(); y++){
//																		((DefaultListModel) rowHeaderPCD.getModel()).addElement(y);
//																	}
//															
//																	scrollPCD.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD.getRowCount())));
//																	tblPCD.packAll();
//																	if (tblPCD.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
//																		tblPCD.getColumnModel().getColumn(1).setPreferredWidth(200);
//																	}
//															
//																	btnDRF.setEnabled(true);
//																	//txtAmount.setEditable(true);
//																	lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
//																	txtRemarks.setEditable(true);
//																	computeTotal();
//																	
//																	if (UserInfo.EmployeeCode.equals("900705") || UserInfo.EmployeeCode.equals("900933") || UserInfo.EmployeeCode.equals("900841") || UserInfo.EmployeeCode.equals("900876")) {
//																		//txtAmount.setEditable(true);
//																	}
//																	
//																}
//														
//																KEYBOARD_MANAGER.focusNextComponent();
//															}
//														}
//													});
//													{
//														lblCode = new JLabel("[ ]");
//														pnlLookup.add(lblCode,BorderLayout.CENTER);
//														lblCode.setHorizontalAlignment(JLabel.LEFT);
//													}
//												}
//												{
//													cmbType = new JComboBox(new DefaultComboBoxModel(getClass2()));
//													pnlText.add(cmbType, BorderLayout.WEST);
////													cmbType.addActionListener(new ActionListener() {
////														public void actionPerformed(ActionEvent e) {
////															int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
////															modelPCD.setValueAt(cmbType.getSelectedItem(), row, 8);
////															modelPCD.setValueAt(batch_no, row, 10);
////														}
////													});
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
//															int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
//															modelPCD.setValueAt(dateSched.getDate(), row, 2);
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
//															int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
//													
//															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
//																
//																if(tblPCD.getSelectedRows().length > 0){
//																	lblBalanceAmount.setText(String.format("%s", value1.subtract(value2)));
//																	
//																	modelPCD.setValueAt(value2, row, 3);
//																	computeTotal();
//																}
//																
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
						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details"));
						pnlCenter.setPreferredSize(new Dimension(800, 440));
						{
							pnlTable = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlTable, BorderLayout.WEST);
							pnlTable.setPreferredSize(new Dimension(460, 200));
							pnlTable.setBorder(lineBorder);
							{
								scrollPCD_Batch = new JScrollPane();
								pnlTable.add(scrollPCD_Batch, BorderLayout.CENTER);
								scrollPCD_Batch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
								{
									modelPCD_Batch = new model_client();
									modelPCD_Batch.addTableModelListener(new TableModelListener() {
										
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderPCD_Batch.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowHeaderPCD_Batch.getModel()).addElement(modelPCD_Batch.getRowCount());
											}
										}
									});
									
									tblPCD_Batch = new _JTableMain(modelPCD_Batch);
									tblPCD_Batch.addMouseListener(this);
									scrollPCD_Batch.setViewportView(tblPCD_Batch);

									tblPCD_Batch.hideColumns("Unit ID");
									tblPCD_Batch.hideColumns("Batch No");
									tblPCD_Batch.setHorizontalScrollEnabled(true);
									
								}
								{
									rowHeaderPCD_Batch = tblPCD_Batch.getRowHeader();
									rowHeaderPCD_Batch.setModel(new DefaultListModel());
									scrollPCD_Batch.setRowHeaderView(rowHeaderPCD_Batch);
									scrollPCD_Batch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
							//displayPCostClient(null); //---removed by JED 2018-10-16 : DCRF no. 797 for control of tagging particulars to avoid double tagging---//
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
									JPanel pnlLabel = new JPanel(new GridLayout(6, 0, 5, 5));
									pnlInfo.add(pnlLabel, BorderLayout.WEST);
									{
										lblInfo = new JLabel("Code");
										pnlLabel.add(lblInfo);
										lblInfo.setHorizontalAlignment(JLabel.RIGHT);

										lblType = new JLabel("Type");
										pnlLabel.add(lblType);
										lblType.setHorizontalAlignment(JLabel.RIGHT);

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

									JPanel pnlText = new JPanel(new GridLayout(6, 0, 5, 5));
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
													lookupCode_Batch.setLookupSQL(SQL_PCOST2());
													lookupCode_Batch.addActionListener(this);
													lookupCode_Batch.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															String emp_code = (String) UserInfo.EmployeeCode;
															String emp_name = (String) UserInfo.FullName;
															String pcostid_dl = lookupCode_Batch.getValue();
															if (data != null) {
																//displayPCostClient();
																
																//---added by JED : DCRF no. 797 : DCRF no. 797 for control of tagging particulars to avoid double tagging---//
																displayAvailablePCostClients(modelPCD_Batch, rowHeaderPCD_Batch, pcostid_dl);
																
																FncSystem.out("SQL for lookup of Code Batch", lookupCode_Batch.getLookupSQL());
																
																lblCode_Batch.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
																lblSetupAmount1_Batch.setText(String.format("%s", data[2]));
																lblBalanceAmount_Batch.setText(String.format("%s", lblSetupAmount1_Batch.getText()));
																txtRemarks_Batch.setText(String.format("%s", data[3]));
																//txtAmount_Batch.setText(String.format("%s", data[2]));
																txtAmount_Batch.setValue((BigDecimal)data[2]);
																txtRequestedBy_Batch.setText(emp_code);
																lblReqBy1_Batch.setText(String.format("[ %s ]", emp_name));
														
																btnDRF.setEnabled(false);
																btnTSave.setEnabled(false);
																txtRemarks_Batch.setEditable(true);
																String batch_no = lblBatch.getText().replace("<html><b>Batch No:</html>", "");
																System.out.println(batch_no);
																
																//if(batch_no.equals(null) || batch_no.equals("")){
																//	btnTSave.setEnabled(false);
																//}else{
																//	btnTSave.setEnabled(true);
																//}
																
																//displayPCostClient(lookupCode_Batch.getValue());
																
																//if (UserInfo.EmployeeCode.equals("900705") || UserInfo.EmployeeCode.equals("900933") || UserInfo.EmployeeCode.equals("900841") || UserInfo.EmployeeCode.equals("900876")) {
																	txtAmount_Batch.setEditable(true);
																//}
																if (lookupCode_Batch.getValue().equals("215") || lookupCode_Batch.getValue().equals("216")) {
																	btnOR.setEnabled(true);
																} else {
																	btnOR.setEnabled(false);
																}
																
																//-------ADDED BY JED VICEDO 9-4-18 : filter clients with exiting tag for particular no. 219 (for PMD)----//
																//if (lookupCode_Batch.getValue().equals("219")){
																	
																//	displayAvailablePCostClients(modelPCD_Batch, rowHeaderPCD_Batch);
																//}
																
																//displayPCostClient();
																
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
													cmbType_Batch = new JComboBox(new DefaultComboBoxModel(getClass2()));
													pnlText.add(cmbType_Batch, BorderLayout.WEST);
//													cmbType_Batch.addActionListener(new ActionListener() {
//														public void actionPerformed(ActionEvent e) {
//															int row = tblPCD_Batch.convertRowIndexToModel(tblPCD_Batch.getSelectedRow());
//															modelPCD_Batch.setValueAt(cmbType_Batch.getSelectedItem(), row, 8);
//															modelPCD_Batch.setValueAt(batch_no, row, 10);
//														}
//													});
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
															int row = tblPCD_Batch.convertRowIndexToModel(tblPCD_Batch.getSelectedRow());
															modelPCD_Batch.setValueAt(dateSched_Batch.getDate(), row, 2);
													
														}
													});
												}
												{
													txtAmount_Batch = new _JXFormattedTextField("0.00");
													pnlText.add(txtAmount_Batch);
													txtAmount_Batch.setHorizontalAlignment(JLabel.RIGHT);
													txtAmount_Batch.setBounds(215, 10, 280, 22);
													txtAmount_Batch.setEditable(false);
													txtAmount_Batch.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													//txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtAmount_Batch.addKeyListener(new KeyAdapter() {
														public void keyReleased(KeyEvent arg0) {
															BigDecimal value1 = new BigDecimal (lblSetupAmount1_Batch.getText());
															BigDecimal value2 = new BigDecimal (lblRunningTotal1_Batch.getText());
													
															int row = tblPCD_Batch.convertRowIndexToModel(tblPCD_Batch.getSelectedRow());
													
															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
																lblBalanceAmount_Batch.setText(String.format("%s", value1.subtract(value2)));
														
																modelPCD_Batch.setValueAt(value2, row, 3);
																computeTotal();
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
				}
//				{
//					pnlBlockLot = new JPanel(new BorderLayout(3, 3));
//					tabCenter.addTab("Block-Lot Related Cost", null, pnlBlockLot, null); //XXX Block-Lot Related Cost
//					pnlBlockLot.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
//					{
//						pnlCenter1 = new JPanel();
//						pnlBlockLot.add(pnlCenter1, BorderLayout.CENTER);
//						pnlCenter1.setLayout(null);
//						pnlCenter1.setBorder(lineBorder);
//						pnlCenter1.setPreferredSize(new Dimension(700, 50));
//						{
//							{
//								lblCenter = new JLabel("Project");
//								pnlCenter1.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(10, 10, 120, 22);
//							}
//							{
//								lookupProject = new _JLookup(null, "Project");
//								pnlCenter1.add(lookupProject);
//								lookupProject.setReturnColumn(0);
//								lookupProject.setLookupSQL(SQL_PROJECT());
//								lookupProject.setBounds(70, 10, 100, 22);
//								lookupProject.addLookupListener(new LookupListener() {
//									public void lookupPerformed(LookupEvent event) {
//										Object[] data = ((_JLookup) event.getSource()).getDataSet();
//										if (data != null) {
//
//											lblProject2.setText(String.format("[ %s ]", (String) data[1]));
//
//											KEYBOARD_MANAGER.focusNextComponent();
//										}
//									}
//								});
//							}
//							{
//								lblProject2 = new JLabel("[ ]");
//								pnlCenter1.add(lblProject2);
//								lblProject2.setHorizontalAlignment(JLabel.LEFT);
//								lblProject2.setBounds(185, 10, 300, 22);
//							}
//							{
//								lblCenter = new JLabel("Unit");
//								pnlCenter1.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(10, 35, 120, 22);
//							}
//							{
//								lookupUnit = new _JLookup(null, "Unit");
//								pnlCenter1.add(lookupUnit);
//								lookupUnit.setReturnColumn(0);
//								lookupUnit.setLookupSQL(SQL_UNIT());
//								lookupUnit.setBounds(70, 35, 100, 22);
//								lookupUnit.addLookupListener(new LookupListener() {
//									public void lookupPerformed(LookupEvent event) {
//										Object[] data = ((_JLookup) event.getSource()).getDataSet();
//										if (data != null) {
//
//											lblUnit.setText(String.format("[ %s ]", (String) data[1]));
//											lblBuyer.setText(String.format("[ %s ]", (String) data[2]));
//											//lblBatch.setText(String.format("<html><b>[ %s ]</html>", (String) data[3]));
//											entity2 = (String) data[4];
//											unitid = (String) data[5];
//											seqno = (Integer) data[6];
//											
//											displayPCostDetails();
//
//											KEYBOARD_MANAGER.focusNextComponent();
//											
//											lookupCode_BlockLot.setLookupSQL(SQL_PCOST(entity2));
//										}
//									}
//								});
//							}
//							{
//								lblUnit = new JLabel("[ ]");
//								pnlCenter1.add(lblUnit);
//								lblUnit.setHorizontalAlignment(JLabel.LEFT);
//								lblUnit.setBounds(185, 35, 300, 22);
//							}
//							{
//								lblCenter = new JLabel("Buyer (if any)");
//								pnlCenter1.add(lblCenter);
//								lblCenter.setHorizontalAlignment(JLabel.LEFT);
//								lblCenter.setBounds(10, 60, 120, 22);
//							}
//							{
//								lblBuyer = new JLabel("[ ]");
//								pnlCenter1.add(lblBuyer);
//								lblBuyer.setHorizontalAlignment(JLabel.LEFT);
//								lblBuyer.setBounds(100, 60, 300, 22);
//							}
//						}
//					}
//					{
//						pnlSouth = new JPanel();
//						pnlBlockLot.add(pnlSouth, BorderLayout.SOUTH);
//						pnlSouth.setLayout(new BorderLayout(5, 5));
//						pnlSouth.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details"));
//						pnlSouth.setPreferredSize(new Dimension(800, 335));
//						{
//							pnlTable = new JPanel(new BorderLayout(5, 5));
//							pnlSouth.add(pnlTable, BorderLayout.WEST);
//							pnlTable.setPreferredSize(new Dimension(460, 200));
//							pnlTable.setBorder(lineBorder);
//							{
//								scrollPCD_BlockLot = new _JScrollPaneMain();
//								pnlTable.add(scrollPCD_BlockLot, BorderLayout.CENTER);
//								modelPCD_BlockLot = new model_pcost();
//								modelPCD_BlockLot.addTableModelListener(new TableModelListener() {
//									public void tableChanged(TableModelEvent e) {
//										if(e.getType() == TableModelEvent.DELETE){
//											rowHeaderPCD_BlockLot.setModel(new DefaultListModel());
//										}
//										if(e.getType() == TableModelEvent.INSERT){
//											((DefaultListModel)rowHeaderPCD_BlockLot.getModel()).addElement(modelPCD_BlockLot.getRowCount());
//										}
//									}
//								});
//
//								tblPCD_BlockLot = new _JTableMain(modelPCD_BlockLot);
//								tblPCD_BlockLot.packAll();
//								tblPCD_BlockLot.setAlignmentX(LEFT_ALIGNMENT);
//								scrollPCD_BlockLot.setViewportView(tblPCD_BlockLot);
//							
//								tblPCD_BlockLot.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//									public void valueChanged(ListSelectionEvent arg0) {
//										try {
//											if(!arg0.getValueIsAdjusting()){
//											
//												String rplf_no = (String) modelPCD_BlockLot.getValueAt(tblPCD_BlockLot.getSelectedRow(), 6);
//												String cost_desc = (String) modelPCD_BlockLot.getValueAt(tblPCD_BlockLot.getSelectedRow(), 1);
//												displayProcessingCostDetails(rplf_no);
//												displayProcessingCostDetails1(cost_desc);
//											}
//										} catch (ArrayIndexOutOfBoundsException e) { }
//									}
//								});
//								tblPCD_BlockLot.putClientProperty("terminateEditOnFocusLost", true);
//								{
//									rowHeaderPCD_BlockLot = tblPCD_BlockLot.getRowHeader();
//									rowHeaderPCD_BlockLot.setModel(new DefaultListModel());
//									scrollPCD_BlockLot.setRowHeaderView(rowHeaderPCD_BlockLot);
//									scrollPCD_BlockLot.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
//								}
//
//							}
//							{
//								scrollPCDTotal_BlockLot = new _JScrollPaneTotal(scrollPCD_BlockLot);
//								pnlTable.add(scrollPCDTotal_BlockLot, BorderLayout.SOUTH);
//								{
//									modelPCDTotal_BlockLot = new model_pcost();
//									modelPCDTotal_BlockLot.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00), null, null, null, null, null, null, null });
//
//									tblPCDTotal_BlockLot = new _JTableTotal(modelPCDTotal_BlockLot, tblPCD_BlockLot);
//									scrollPCDTotal_BlockLot.setViewportView(tblPCDTotal_BlockLot);
//
//									tblPCDTotal_BlockLot.setTotalLabel(2);
//								}
//								scrollPCDTotal_BlockLot.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation_BlockLot());
//							}
//						}
//						{
//							pnlDetail = new JPanel(new BorderLayout(5, 5));
//							pnlSouth.add(pnlDetail, BorderLayout.EAST);
//							pnlDetail.setPreferredSize(new Dimension(400, 335));
//							{
//								pnlInfo = new JPanel(new BorderLayout(5, 5));
//								pnlDetail.add(pnlInfo, BorderLayout.NORTH);
//								pnlInfo.setBorder(JTBorderFactory.createTitleBorder("Detail INFO"));
//								pnlInfo.setPreferredSize(new Dimension(400, 205));
//								{
//									JPanel pnlLabel = new JPanel(new GridLayout(6, 0, 5, 5));
//									pnlInfo.add(pnlLabel, BorderLayout.WEST);
//									{
//										lblInfo = new JLabel("Code");
//										pnlLabel.add(lblInfo);
//										lblInfo.setHorizontalAlignment(JLabel.RIGHT);
//
//										lblType = new JLabel("Type");
//										pnlLabel.add(lblType);
//										lblType.setHorizontalAlignment(JLabel.RIGHT);
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
//									JPanel pnlText = new JPanel(new GridLayout(6, 0, 5, 5));
//									pnlInfo.add(pnlText, BorderLayout.CENTER);
//									{
//
//										{
//											JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
//											pnlText.add(pnlLookup);
//											{
//												{
//													lookupCode_BlockLot = new _JLookup(null, "Code");
//													pnlLookup.add(lookupCode_BlockLot, BorderLayout.WEST);
//													lookupCode_BlockLot.setPreferredSize(new Dimension(50, 20));
//													lookupCode_BlockLot.setReturnColumn(0);
//													lookupCode_BlockLot.setLookupSQL(SQL_PCOST(entity));
//													lookupCode_BlockLot.addActionListener(this);
//													lookupCode_BlockLot.addLookupListener(new LookupListener() {
//														public void lookupPerformed(LookupEvent event) {
//															Object[] data = ((_JLookup) event.getSource()).getDataSet();
//															String emp_code = (String) UserInfo.EmployeeCode;
//															String emp_name = (String) UserInfo.FullName;
//															if (data != null) {
//																
//																FncSystem.out("Display SQL for lookup", lookupCode_BlockLot.getLookupSQL());
//																
//																lblCode_BlockLot.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
//																lblSetupAmount1_BlockLot.setText(String.format("%s", data[2]));
//																lblBalanceAmount_BlockLot.setText(String.format("%s", lblSetupAmount1_BlockLot.getText()));
//																txtRequestedBy_BlockLot.setText(emp_code);
//																lblReqBy1_BlockLot.setText(String.format("[ %s ]", emp_name));
//																if (data[3] != null){
//																	txtRemarks_BlockLot.setText(String.format("%s", data[3]));
//																} else {
//																	txtRemarks_BlockLot.setText(" ");
//																}
//																
//														
//																if(data != null){
//																	BigDecimal amount = new BigDecimal (String.format("%s", reg_amount1(lookupUnit.getValue())));
//																	BigDecimal amount1 = new BigDecimal (String.format("100.00"));
//																	if (lookupCode_BlockLot.getValue().equals("109") || lookupCode_BlockLot.getValue().equals("210") || lookupCode_BlockLot.getValue().equals("105") || lookupCode_BlockLot.getValue().equals("108")){
//																		modelPCD_BlockLot.addRow(new Object []{true, "ENTRY FEE DEED OF ASSIGNMENT", Calendar.getInstance().getTime(), new BigDecimal(30.00), "Fixed Amt", null, null, null, "Payment", null, batch_no});
//																		modelPCD_BlockLot.addRow(new Object []{true, "JUDICIAL FORM FEE", Calendar.getInstance().getTime(), new BigDecimal(30.00), "DUE TO RD COMPUTERIZATION (originally encoded as it fee, revised by sir alan 7/26/10)", null, null, null, "Payment", null, batch_no});
//																		modelPCD_BlockLot.addRow(new Object []{true, "REGISTRATION FEE OF ASSIGNMENT", Calendar.getInstance().getTime(), amount, "Refer to RD Table", null, null, null, "Payment", null, batch_no});
//																		modelPCD_BlockLot.addRow(new Object []{true, "RESEARCH FEE  ASSIGNMENT", Calendar.getInstance().getTime(), amount.divide(amount1), null, null, null, null, "Payment", null, batch_no});
//																	} else {
//																		if (data[3] == null) {
//																			modelPCD_BlockLot.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], "", null, null, null, "Payment", null, batch_no});
//																			} else {
//																				if (data[3].equals("Fixed Amt")) {
//																					modelPCD_BlockLot.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, batch_no});
//																				} else {
//																					modelPCD_BlockLot.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, batch_no});
//																				}
//																			}
//																	}
//																	rowHeaderPCD_BlockLot.setModel(new DefaultListModel());
//
//																	for(int y =1; y<=modelPCD_BlockLot.getRowCount(); y++){
//																		((DefaultListModel) rowHeaderPCD_BlockLot.getModel()).addElement(y);
//																	}
//															
//																	scrollPCD_BlockLot.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_BlockLot.getRowCount())));
//																	tblPCD_BlockLot.packAll();
//																	if (tblPCD_BlockLot.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
//																		tblPCD_BlockLot.getColumnModel().getColumn(1).setPreferredWidth(200);
//																	}
//															
//																	btnDRF.setEnabled(true);
//																	txtAmount_BlockLot.setEditable(true);
//																	txtRemarks_BlockLot.setEditable(true);
//																	
//																	lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
//																	
//																}
//														
//																KEYBOARD_MANAGER.focusNextComponent();
//															}
//														}
//													});
//													{
//														lblCode_BlockLot = new JLabel("[ ]");
//														pnlLookup.add(lblCode_BlockLot,BorderLayout.CENTER);
//														lblCode_BlockLot.setHorizontalAlignment(JLabel.LEFT);
//													}
//												}
//												{
//													cmbType_BlockLot = new JComboBox(new DefaultComboBoxModel(getClass2()));
//													pnlText.add(cmbType_BlockLot, BorderLayout.WEST);
//													cmbType_BlockLot.addActionListener(new ActionListener() {
//														public void actionPerformed(ActionEvent e) {
//															int row = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
//															modelPCD_BlockLot.setValueAt(cmbType_BlockLot.getSelectedItem(), row, 8);
//															modelPCD_BlockLot.setValueAt(batch_no, row, 10);
//														}
//													});
//												}
//												{
//													dateSched_BlockLot = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
//													pnlText.add(dateSched_BlockLot);
//													dateSched_BlockLot.setDate(null);
//													dateSched_BlockLot.setEditable(false);
//													dateSched_BlockLot.setDateFormatString("yyyy-MM-dd");
//													((JTextFieldDateEditor)dateSched_BlockLot.getDateEditor()).setEditable(false);
//													dateSched_BlockLot.setDate(Calendar.getInstance().getTime());
//													dateSched_BlockLot.addDateListener(new DateListener() {
//													public void datePerformed(DateEvent event) {
//														int row = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
//														modelPCD_BlockLot.setValueAt(sdf.format(dateSched_BlockLot.getDate()), row, 2);
//													
//													}
//												});
//												}
//												{
//													txtAmount_BlockLot = new _JXFormattedTextField("0.00");
//													pnlText.add(txtAmount_BlockLot);
//													txtAmount_BlockLot.setEditable(false);
//													txtAmount_BlockLot.setHorizontalAlignment(JLabel.RIGHT);
//													txtAmount_BlockLot.setBounds(215, 10, 280, 22);
//													//txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
//													txtAmount_BlockLot.addKeyListener(new KeyAdapter() {
//														public void keyReleased(KeyEvent arg0) {
//															BigDecimal value1 = new BigDecimal (lblSetupAmount1_BlockLot.getText());
//															BigDecimal value2 = new BigDecimal (lblRunningTotal1_BlockLot.getText());
//													
//															int row = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
//													
//															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
//																lblBalanceAmount_BlockLot.setText(String.format("%s", value1.subtract(value2)));
//														
//																modelPCD_BlockLot.setValueAt(value2, row, 3);
//																computeTotal();
//															}
//														}
//													});
//													txtAmount_BlockLot.getDocument().addDocumentListener(new DocumentListener() {
//														public void insertUpdate(DocumentEvent e) {
//															lblRunningTotal1_BlockLot.setText(txtAmount_BlockLot.getText());
//														}
//														public void removeUpdate(DocumentEvent e) {
//															lblRunningTotal1_BlockLot.setText(txtAmount_BlockLot.getText());
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
//														txtRequestedBy_BlockLot = new JXTextField();
//														pnlReqBy.add(txtRequestedBy_BlockLot, BorderLayout.WEST);
//														txtRequestedBy_BlockLot.setEditable(false);
//														txtRequestedBy_BlockLot.setPreferredSize(new Dimension(100, 20));
//														{
//															lblReqBy1_BlockLot = new JLabel("[ ]");
//															pnlReqBy.add(lblReqBy1_BlockLot,BorderLayout.CENTER);
//															lblReqBy1_BlockLot.setHorizontalAlignment(JLabel.LEFT);
//														}
//													}
//												}
//												{
//													txtRemarks_BlockLot = new JXTextField();
//													pnlText.add(txtRemarks_BlockLot);
//													txtRemarks_BlockLot.setHorizontalAlignment(JLabel.LEFT);
//													txtRemarks_BlockLot.setBounds(215, 10, 280, 22);
//													txtRemarks_BlockLot.setEditable(false);
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
//											lblSetupAmount1_BlockLot = new JLabel("0.00");
//											pnlLabelTotal.add(lblSetupAmount1_BlockLot);
//										}
//									}
//									{
//										JLabel lblRunningTotal = new JLabel("Running Total  :");
//										pnlLabelTotal.add(lblRunningTotal);
//										{
//											lblRunningTotal1_BlockLot = new JLabel("0.00");
//											pnlLabelTotal.add(lblRunningTotal1_BlockLot);
//										}
//									}
//									{
//										JLabel lblBalance = new JLabel("Balance +/(-)   :");
//										pnlLabelTotal.add(lblBalance);
//										{
//											lblBalanceAmount_BlockLot = new JLabel("0.00");
//											pnlLabelTotal.add(lblBalanceAmount_BlockLot);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//				tabCenter.addChangeListener(new ChangeListener() {
//					public void stateChanged(ChangeEvent arg0) {
//						int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();
//
//						if(selectedTab == 0){
//							btnBatch.setEnabled(false);
//							btnTSave.setEnabled(false);
//							btnDRF.setEnabled(false);
//							lblBatch.setText("<html><b>Batch No:</html>");
//						}
//						if(selectedTab == 0){
//							btnBatch.setEnabled(true);
//							btnTSave.setEnabled(false);
//							btnDRF.setEnabled(false);
//							lblBatch.setText("<html><b>Batch No:</html>");
//						}
//						if(selectedTab == 2){ 
//							btnBatch.setEnabled(false);
//							btnTSave.setEnabled(false);
//							btnDRF.setEnabled(false);
//							lblBatch.setText("<html><b>Batch No:</html>");  
//						}
//					}
//				});
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
					btnOR = new JButton("Input OR No");
					pnlSouth.add(btnOR);
					btnOR.setActionCommand("OR");
					btnOR.addActionListener(this);
					btnOR.setEnabled(false);
				}
				{
					btnDRF = new JButton("<html><center><font size = 2>Create Disb.\n Request</html>");
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
					btnBatch = new JButton("<html><center><font size = 2>Batch w/ out\n RPLF</html>");
					pnlSouth.add(btnBatch);
					btnBatch.setActionCommand("Batch w/ out RPLF");
					btnBatch.addActionListener(this);
					//btnDelete.setEnabled(false);
				}
				{
					btnCancel = new JButton ("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(true);
				}
			}
		}
		btnBatch.setEnabled(true);
		btnTSave.setEnabled(false);
	}
	
	
	//display
	public void displayPCostDetails() {
		
		if (tabCenter.getSelectedIndex() == 0) {
			FncTables.clearTable(modelPCD);//Code to clear modelMain.		
			DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		
			String sql = "SELECT false, trim(b.pcostdtl_desc), a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.tran_type, null, null, a.batch_no, a.rpt_or_no, a.rpt_year \n" + 
						"FROM rf_processing_cost a \n" + 
						"LEFT JOIN mf_processing_cost_dl b ON a.pcostid_dl = b.pcostdtl_id  \n" +
						"WHERE a.status_id = 'A'\n" +
						"AND a.entity_id = '"+lookupClient.getValue()+"'\n" +
						"AND a.pbl_id = '"+clientDetails[4]+"' \n" +
						"ORDER BY b.pcostdtl_desc\n" +
						"--group by b.pcostdtl_desc, a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.batch_no, a.pcostid_dl, a.tran_type, a.rpt_or_no, a.rpt_year";
		
		FncSystem.out("Display PCOST details", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelPCD.addRow(db.getResult()[x]);
				listModel.addElement(modelPCD.getRowCount());
			}
			scrollPCD.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD.getRowCount())));
		}
		BigDecimal total = new BigDecimal(0.00);
		for(int y=0; y < modelPCD.getRowCount(); y++){
			total = total.add((BigDecimal) modelPCD.getValueAt(y, 3));
			modelPCDTotal.setValueAt(total, 0, 3);
		}
		tblPCD.packAll();
		}
		
		if (tabCenter.getSelectedIndex() == 2) {
			FncTables.clearTable(modelPCD_BlockLot);//Code to clear modelMain.		
			DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		
//			String sql = "SELECT distinct on (pcostid_dl) false, trim(b.part_desc), a.tran_date, b.amount, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.tran_type, null, a.batch_no \n" + 
//							"FROM rf_processing_cost a \n" + 
//							"LEFT JOIN rf_request_detail b ON a.rplf_no = b.rplf_no  \n" +
//							"WHERE b.status_id = 'A'\n" +
//							"AND a.entity_id = '"+entity2+"'\n" +
//							"AND a.pbl_id = '"+lookupUnit.getValue()+"' \n" +
//							"GROUP BY b.part_desc, a.tran_date, b.amount, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.batch_no, a.pcostid_dl, a.tran_type;";
			
			String sql =
					"SELECT false, trim(b.pcostdtl_desc), a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.tran_type, null, a.batch_no, a.rpt_or_no, a.rpt_year   \n" + 
					"FROM rf_processing_cost a   \n" + 
					"LEFT JOIN mf_processing_cost_dl b ON a.pcostid_dl = b.pcostdtl_id   \n" + 
					"WHERE a.status_id = 'A' and b.status_id = 'A'\n" + 
					"AND a.entity_id = '"+entity2+"' \n" + 
					"AND a.pbl_id = '"+lookupUnit.getValue()+"'\n" + 
					"--order by pcostid_dl\n" + 
					"GROUP BY b.pcostdtl_desc, a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.batch_no, a.pcostid_dl, a.tran_type, a.rpt_or_no, a.rpt_year;";

			pgSelect db = new pgSelect();
			db.select(sql);
			
			FncSystem.out("Display SQL for PCost BLock Lot", sql);
			if(db.isNotNull()){ 
				for(int x=0; x < db.getRowCount(); x++){
					modelPCD_BlockLot.addRow(db.getResult()[x]);
					listModel.addElement(modelPCD_BlockLot.getRowCount());
				}
				scrollPCD_BlockLot.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_BlockLot.getRowCount())));
			}
			BigDecimal total = new BigDecimal(0.00);
			for(int y=0; y < modelPCD_BlockLot.getRowCount(); y++){
				total = total.add((BigDecimal) modelPCD_BlockLot.getValueAt(y, 3));
				modelPCDTotal_BlockLot.setValueAt(total, 0, 3);
			}
			tblPCD_BlockLot.packAll();
			}
	}
	
	//---ADDED BY JED VICEDO 9-4-18 : FILTERING CLIENTS WITH EXISTING TAG (PARTICULAR: MERALCO FOR PMD)---//
	//---modified by jed 2018-10-17 : DCRF no. 797 to put control in tagging particulars in PCOST to avoid double tagging except the following particulars---//
	//---modified by jed 2019-01-09 : additional filter to preview cancelled accounts as per request by nezza---//
	private void displayAvailablePCostClients(DefaultTableModel modelMain, JList rowHeader, String pcostid_dl){

//		if (pcostid_dl.equals("050") || pcostid_dl.equals("049") || pcostid_dl.equals("040") || pcostid_dl.equals("041") || 
//				pcostid_dl.equals("042") || pcostid_dl.equals("003") || pcostid_dl.equals("004") || pcostid_dl.equals("045") || pcostid_dl.equals("218") ||
//				 	pcostid_dl.equals("001") || pcostid_dl.equals("002") || pcostid_dl.equals("048") || pcostid_dl.equals("221") || pcostid_dl.equals("222") ||
//				 		pcostid_dl.equals("223") || pcostid_dl.equals("224") || pcostid_dl.equals("225") || pcostid_dl.equals("216")){

			FncTables.clearTable(modelMain);
			DefaultListModel listModel = new DefaultListModel();
			//rowHeader.setModel(listModel);

			String strSQL = 
			
					"SELECT * from (SELECT false, \n" + 
					"CASE WHEN c.entity_id IS NOT NULL THEN btrim(c.entity_id::text)::character varying ELSE f.entity_id end AS \"Client ID\",\n" + 
					"case when b.entity_name is not null then btrim(b.entity_name::text) else g.entity_name END AS \"Client Name\",\n" + 
					"format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\",\n" + 
					"btrim(a.pbl_id::text)::character varying AS \"PBL ID\",\n" + 
					"case when c.seq_no is not null then c.seq_no ELSE f.seq_no end AS \"Sequence\",\n" + 
					"case when e.type_card_display is not null then btrim(e.type_card_display::text) else (select type_card_display from rf_sold_unit a LEFT JOIN mf_buyer_type b ON b.type_id::text = a.buyertype::text where entity_id = f.entity_id limit 1) end AS \"BuyerType\",\n" + 
					"btrim(a.proj_id::text)::character varying AS \"Project ID\",\n" + 
					"btrim(d.proj_name::text) AS \"Project Name\", c.unit_id AS \"Unit ID\",  ''::varchar AS \"Batch No\" \n" + 
					"FROM mf_unit_info a\n" + 
					"LEFT JOIN rf_sold_unit c ON a.proj_id::text = c.projcode::text AND a.pbl_id::text = c.pbl_id::text AND c.status_id in  ('A', 'I')\n" + //**ADDED BY JED 2019-01-11 TO DISPLAY INACTIVE CLIENTS FOR CANCELLATION C/O NESSA**//
					"LEFT JOIN rf_entity b ON b.entity_id::text = c.entity_id::text\n" + 
					"LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
					"LEFT JOIN mf_buyer_type e ON e.type_id::text = c.buyertype::text\n" + 
					"LEFT JOIN hs_sold_other_lots f on f.proj_id = a.proj_id and f.oth_pbl_id = a.pbl_id and f.status_id = 'A'\n" + 
					"LEFT join rf_entity g on g.entity_id = f.entity_id\n" + 
					"WHERE a.status_id in ('R', 'O') --and c.entity_id is not null and b.entity_name is not null \n" + //**ADDED BY JED 2019-01-11 TO DISPLAY INACTIVE CLIENTS FOR CANCELLATION C/O NESSA**//
					"--AND a.pbl_id in ('390', '392')\n" + 
					"--and (c.entity_id is not null or f.entity_id is not null) \n" + //**ADDED BY JED 2019-01-11 TO DISPLAY INACTIVE CLIENTS FOR CANCELLATION C/O NESSA**//
					"ORDER BY btrim(b.entity_name::text), a.phase, a.block::INT, a.lot::INT  ) j\n" + 
					"\n" + 
					"union all\n" + 
					"\n" + 
					"SELECT * from (select false, \n" + 
					"''::Varchar as \"Client ID\",\n" + 
					"''::varchar as \"Client Name\",\n" + 
					"format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\",\n" + 
					"btrim(a.pbl_id::text)::character varying AS \"PBL ID\",\n" + 
					"null::int AS \"Sequence\",\n" + 
					"''::varchar AS \"BuyerType\",\n" + 
					"btrim(a.proj_id::text)::character varying AS \"Project ID\",\n" + 
					"btrim(d.proj_name::text)::varchar AS \"Project Name\", a.unit_id AS \"Unit ID\",  \n" + 
					"''::varchar AS \"Batch No\" \n" + 
					"FROM mf_unit_info_pending a\n" + 
					"LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
					"--WHERE not exists (select entity_id from rf_processing_cost where pbl_id = a.pbl_id and pcostid_dl = '220' and status_id = 'A')\n" + 
					"--and c.entity_id is not null and b.entity_name is not null \n" + 
					"--AND a.pbl_id in ('390', '392')\n" + 
					"ORDER BY a.phase, a.block::INT, a.lot::INT) k" ;

			FncSystem.out("List of the available clients", strSQL);

			pgSelect db = new pgSelect();
			db.select(strSQL);
			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) { 
					// Adding of row in table
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}
			}

			scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
			tblPCD_Batch.packAll();
//		}
//		else{
//
//			FncTables.clearTable(modelMain);
//			DefaultListModel listModel = new DefaultListModel();
//			//rowHeader.setModel(listModel);
//
//			String strSQL = 
//			
//					"SELECT * from (select false, \n" + 
//					"CASE WHEN c.entity_id IS NOT NULL THEN btrim(c.entity_id::text)::character varying ELSE f.entity_id end AS \"Client ID\",\n" + 
//					"case when b.entity_name is not null then btrim(b.entity_name::text) else g.entity_name END AS \"Client Name\",\n" + 
//					"format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\",\n" + 
//					"btrim(a.pbl_id::text)::character varying AS \"PBL ID\",\n" + 
//					"case when c.seq_no is not null then c.seq_no ELSE f.seq_no end AS \"Sequence\",\n" + 
//					"case when e.type_card_display is not null then btrim(e.type_card_display::text) else (select type_card_display from rf_sold_unit a LEFT JOIN mf_buyer_type b ON b.type_id::text = a.buyertype::text where entity_id = f.entity_id limit 1) end AS \"BuyerType\",\n" + 
//					"btrim(a.proj_id::text)::character varying AS \"Project ID\",\n" + 
//					"btrim(d.proj_name::text) AS \"Project Name\", c.unit_id AS \"Unit ID\",  ''::varchar AS \"Batch No\" \n" + 
//					"FROM mf_unit_info a\n" + 
//					"LEFT JOIN rf_sold_unit c ON a.proj_id::text = c.projcode::text AND a.pbl_id::text = c.pbl_id::text AND c.status_id in  ('A', 'I')\n" + //**ADDED BY JED 2019-01-11 TO DISPLAY INACTIVE CLIENTS FOR CANCELLATION C/O NESSA**//
//					"LEFT JOIN rf_entity b ON b.entity_id::text = c.entity_id::text\n" + 
//					"LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
//					"LEFT JOIN mf_buyer_type e ON e.type_id::text = c.buyertype::text\n" + 
//					"LEFT JOIN hs_sold_other_lots f on f.proj_id = a.proj_id and f.oth_pbl_id = a.pbl_id and f.status_id = 'A'\n" + 
//					"LEFT join rf_entity g on g.entity_id = f.entity_id\n" + 
//					"WHERE a.status_id in ('R', 'O') \n" + //**ADDED BY JED 2019-01-11 TO DISPLAY INACTIVE CLIENTS FOR CANCELLATION C/O NESSA**//
//					"and not exists (select entity_id from rf_processing_cost where pbl_id = a.pbl_id and entity_id = b.entity_id and pcostid_dl = '"+pcostid_dl+"' and status_id = 'A')\n" + 
//					"--and c.entity_id is not null and b.entity_name is not null \n" + 
//					"--AND a.pbl_id in ('390', '392')\n" + 
//					"--and (c.entity_id is not null or f.entity_id is not null) \n" + //**ADDED BY JED 2019-01-11 TO DISPLAY INACTIVE CLIENTS FOR CANCELLATION C/O NESSA**//
//					"ORDER by btrim(b.entity_name::text), a.phase, a.block::INT, a.lot::INT) j\n" + 
//					"\n" + 
//					"union all\n" + 
//					"\n" + 
//					"SELECT * from (select false, \n" + 
//					"''::Varchar as \"Client ID\",\n" + 
//					"''::varchar as \"Client Name\",\n" + 
//					"format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\",\n" + 
//					"btrim(a.pbl_id::text)::character varying AS \"PBL ID\",\n" + 
//					"null::int AS \"Sequence\",\n" + 
//					"''::varchar AS \"BuyerType\",\n" + 
//					"btrim(a.proj_id::text)::character varying AS \"Project ID\",\n" + 
//					"btrim(d.proj_name::text)::varchar AS \"Project Name\", a.unit_id AS \"Unit ID\",  \n" + 
//					"''::varchar AS \"Batch No\" \n" + 
//					"FROM mf_unit_info_pending a\n" + 
//					"LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
//					"WHERE not exists (select entity_id from rf_processing_cost where pbl_id = a.pbl_id and pcostid_dl = '"+pcostid_dl+"' and status_id = 'A')\n" + 
//					"--and c.entity_id is not null and b.entity_name is not null \n" + 
//					"--AND a.pbl_id in ('390', '392')\n" + 
//					"ORDER BY a.phase, a.block::INT, a.lot::INT) k" ;
//
//			FncSystem.out("List of the available clients (filtered)", strSQL);
//
//			pgSelect db = new pgSelect();
//			db.select(strSQL);
//			if (db.isNotNull()) {
//				for (int x = 0; x < db.getRowCount(); x++) { 
//					// Adding of row in table
//					modelMain.addRow(db.getResult()[x]);
//					listModel.addElement(modelMain.getRowCount());
//				}
//			}
//
//			scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
//			tblPCD_Batch.packAll();
//		
//		}
	}

	private void refreshBatch() {
		
		if (lookupCode_Batch.getText().equals("")) {
			lblCode_Batch.setText("[ ]");
			cmbType_Batch.setSelectedItem("Payment");
			dateSched_Batch.setDate(Calendar.getInstance().getTime());
			txtAmount_Batch.setText("0.00");
			txtRequestedBy_Batch.setText(null);
			txtRequestedBy_Batch.setEditable(false);
			lblReqBy1_Batch.setText("[ ]");
			txtRemarks_Batch.setText(null);
			btnDRF.setEnabled(false);
			btnTSave.setEnabled(false);
			//btnEdit.setEnabled(false);
			btnBatch.setEnabled(true);
			modelPCD_Batch.setRowCount(0);
			//displayPCostClient(lookupCode_Batch.getValue());	//--removed by jed 2018-10-16 : DCRF no. 797 put control on tagging particulars in PCOST to avoid double tagging--//
			lblBatch.setText("<html><b>Batch No: </html>");
			btnOR.setEnabled(false);
		}
	} // refreshTO()
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Create Disbursement Request")){
//			if(tabCenter.getSelectedIndex() == 0){
//				if(withZeroAmountIndividual()){
//					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
//				}
//				else{
//					JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
//							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
//				}
//			}
			if(tabCenter.getSelectedIndex() == 0){
				JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}

//			if(tabCenter.getSelectedIndex() == 2){
//				if(withZeroAmtBlkLot()){
//					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
//				}else{
//					JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
//							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
//				}
//			}
		}

		if(actionCommand.equals("OR")){
			JOptionPane.showOptionDialog(getContentPane(), pnlCreateOR, "Create OR",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		
		}

		if(actionCommand.equals("Create RPLF")){ createRPLF(); }

		if(actionCommand.equals("Save")){ save(); }

		if(actionCommand.equals("Save OR")){ saveOR(); }

		if(actionCommand.equals("Cancel RPLF")){
			lookupRequestType.setValue(null);
			lblRequestType.setText("[ ]");
			lookupAvailer.setValue(null);
			lblAvailer.setText("[ ]");
			lookupAvailerType.setValue(null);
			lblAvailerType.setText("[ ] ");
		}
		if(actionCommand.equals("Cancel OR")){
			txtORNo.setText("");
			txtYear.setText("");
		}
		if(actionCommand.equals("Batch w/ out RPLF")){
			getBatchList();
			btnTSave.setEnabled(false); //ADDED BY LESTER 2016-12-19 ------edited by JED VICEDO 2018-08-06
			btnDRF.setEnabled(true); //ADDED BY LESTER 2016-12-19
			//CHANGE DISPLAY OF PCOST DETAILS HERE BASED ON retrieved batch no
			
			//---added by jed 2018-8-6---//
			//pnlState(false, true, false);
			btnTSave.setEnabled(false); 
			//displayPCostClient(null);
			
			modelPCD_Batch.clear();
			scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
			tblPCD_Batch.packAll();
			
			lookupCode_Batch.setText("");
			lblCode_Batch.setText("[ ]");
			lblSetupAmount1_Batch.setText("0.00");
			lblBalanceAmount_Batch.setText("0.00");
			txtRemarks_Batch.setText("");
			txtAmount_Batch.setValue(0.00);
			txtRequestedBy_Batch.setText("");
			lblReqBy1_Batch.setText("[ ]");
		}

//		if (actionCommand.equals("Remove Row")) { removeRow();
//
//		}
		
		if (actionCommand.equals("Cancel")) { cancel(); 
		
		}
		
//		if (actionCommand.equals("Tag")) { executeTag(selected_batch);//--ADDED BY JED 2018-11-21 : NO DCRF; FOR DOUBLE TAGGING--//
//		
//		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblPCD_Batch)) {
			int row = tblPCD_Batch.convertRowIndexToModel(tblPCD_Batch.getSelectedRow());
			Boolean isSelected2 = (Boolean) modelPCD_Batch.getValueAt(row, 0);

			for(int x = 0; x < modelPCD_Batch.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
					entityid = (String) modelPCD_Batch.getValueAt(x, 1);
					
				if (isSelected) {
					if (batch() == false) {
						/*if (control()==true) {
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Particular was already existed to the account", "Tagging", JOptionPane.WARNING_MESSAGE);
							modelPCD_Batch.setValueAt(false, x, 0);
						} else {*/
							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
							//pnlState(false, true, false);
							btnTSave.setEnabled(true);
						//}
					} else {
						//pnlState(false, true, false);
						btnTSave.setEnabled(true);
					}
				}else{
					//lookupCode_Batch.setEnabled(false);
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
	
	
	//SQL
		
//	private String SQL_PCOST(String entity) {
//		if(checkEmpCode(user)==true){
//		String sql=
//				"SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
//				"FROM mf_processing_cost_dl a\n" + 
//				"WHERE status_id = 'A'\n" + 
//				"and not exists (SELECT pcostid_dl FROM rf_processing_cost b WHERe a.pcostdtl_id = b.pcostid_dl and entity_id = '"+entity+"' and status_id = 'A')\n" + 
//				"order by pcostdtl_desc";
//		return sql;
//		}else{
//		String sql=
//				"SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
//				"FROM mf_processing_cost_dl\n" + 
//				"WHERE status_id = 'A'\n" +
//				"AND CASE WHEN COALESCE(pcostdtl_amt, 0.00) = 0 THEN pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '"+entity+"' and status_id = 'A') ELSE TRUE END\n" +
//				"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
//				"ORDER BY pcostdtl_desc ASC;";
//		return sql;
//		}
//	}
	
//	private SQL_PCOST2(){
//		
//		return "SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
//				"FROM mf_processing_cost_dl\n" + 
//				"WHERE status_id = 'A'\n" +
//				//"AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '"+entity+"' and status_id = 'A')\n" +
//				"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
//				"ORDER BY pcostdtl_desc ASC";
//	}
	
	//-----modified by JED VICEDO 9-5-18 : filter clients with existing tag on particular no. 219 (for PMD)-----//
	private String SQL_PCOST2() {
		if(checkEmpCode(user)==true){
			String sql =
					"SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
					"FROM mf_processing_cost_dl\n" + 
					"WHERE status_id = 'A'\n" +
					//"AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '"+entity+"' and status_id = 'A')\n" +
					"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
					"ORDER BY pcostdtl_desc ASC";
			return sql;
		} else {
			String sql =
					"SELECT \n" + 
					"pcostdtl_id as \"PCost ID.\",\n" + 
					"trim(pcostdtl_desc) as \"PCost Desc\",\n" + 
					"pcostdtl_amt as \"PCost Amount\", \n" + 
					"remarks as \"Remarks\" \n" + 
					"FROM mf_processing_cost_dl\n" + 
					"WHERE status_id = 'A' and pcostdtl_id != '219'\n" + 
					"--AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '\"entity\"' and status_id = 'A')\n" + 
					"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
					"ORDER BY pcostdtl_desc ASC";
			
			return sql;
		}
	}
	//---added by jed 2018-11-18 : list of clients no dcrf---//
	public static String sqlClients() {
		return "SELECT\n" + 
				"btrim(c.status_id) as \"Status\",\n" + 
				"CASE WHEN c.entity_id IS NOT NULL THEN btrim(c.entity_id::text)::character varying ELSE f.entity_id end AS \"Client ID\",\n" + 
				"case when b.entity_name is not null then btrim(b.entity_name::text) else g.entity_name END AS \"Client Name\",\n" + 
				"format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\",\n" + 
				"btrim(a.pbl_id::text)::character varying AS \"PBL ID\",\n" + 
				"case when c.seq_no is not null then c.seq_no ELSE f.seq_no end AS \"Sequence\",\n" + 
				"case when e.type_card_display is not null then btrim(e.type_card_display::text) else (select type_card_display from rf_sold_unit a LEFT JOIN mf_buyer_type b ON b.type_id::text = a.buyertype::text where entity_id = f.entity_id limit 1) end AS \"BuyerType\",\n" + 
				"btrim(a.proj_id::text)::character varying AS \"Project ID\",\n" + 
				"btrim(d.proj_name::text) AS \"Project Name\"\n" + 
				"--c.unit_id AS \"Unit ID\",  '' AS \"Batch No\" \n" + 
				"FROM mf_unit_info a\n" + 
				"LEFT JOIN rf_sold_unit c ON a.proj_id::text = c.projcode::text AND a.pbl_id::text = c.pbl_id::text AND c.status_id = 'A'\n" + 
				"LEFT JOIN rf_entity b ON b.entity_id::text = c.entity_id::text\n" + 
				"LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
				"LEFT JOIN mf_buyer_type e ON e.type_id::text = c.buyertype::text\n" + 
				"LEFT JOIN hs_sold_other_lots f on f.proj_id = a.proj_id and f.oth_pbl_id = a.pbl_id and f.status_id = 'A'\n" + 
				"LEFT join rf_entity g on g.entity_id = f.entity_id\n" + 
				"WHERE a.status_id = 'R' \n" + 
				"--and not exists (select entity_id from rf_processing_cost where pbl_id = a.pbl_id and entity_id = b.entity_id and pcostid_dl = '220' and status_id = 'A')\n" + 
				"--and c.entity_id is not null and b.entity_name is not null \n" + 
				"--AND a.pbl_id in ('390', '392')\n" + 
				"ORDER BY btrim(b.entity_name::text), a.phase, a.block::INT, a.lot::INT ";
		
	}

	private static String SQL_BatchList() {
		String strSQL = "SELECT batch_no as \"Batch No\", sum(setup_amt) as \"Amount\"\n" +
				"FROM rf_processing_cost\n" + 
				"WHERE (case when '"+UserInfo.EmployeeCode+"' = '900449' then true else created_by = '"+ UserInfo.EmployeeCode +"' end) \n" + 
				"AND rplf_no = '' \n" + 
				"AND status_id = 'A' \n" +
				"GROUP BY batch_no";

		FncSystem.out("Batch No", strSQL);
		return strSQL;
	}
		
	private String[] getClass2() {
		return new String[] {
				"Payment",
				"Refund",
		};
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

	
//	private static Object[] getProcessingCostDetails1_IND(String cost_desc, String entity_id) {
//		String SQL = "SELECT a.pcostid_dl, a.tran_date, a.pcost_amt, get_client_name(b.entity_id), a.requested_by, a.remarks, a.setup_amt, a.batch_no\n" + 
//				"FROM rf_processing_cost a\n" + 
//				"LEFT JOIN em_employee b on a.requested_by = b.emp_code\n" + 
//				"left join mf_processing_cost_dl c on a.pcostid_dl = c.pcostdtl_id\n" + 
//				"WHERE a.status_id = 'A' and c.pcostdtl_desc = '"+cost_desc+"' and a.entity_id = '"+entity_id+"' ";
//		
//		FncSystem.out("SQL for Processing Cost Details w/o RPLF:", SQL);
//		
//		pgSelect db = new pgSelect();
//		db.select(SQL);
//		if(db.isNotNull()){
//			return db.getResult()[0];
//		}else{
//			return null;
//		}
//	}
	
	private static String generateBatchNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(batch_no::INT), 0) + 1, 'FM0000000000') FROM rf_processing_cost WHERE status_id = 'A';";

		//FncSystem.out("Batch No", strSQL);
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
	
	
	//Compute, Process, Save
	private void computeTotal() {
		BigDecimal totalAmountCommitted = new BigDecimal("0.00");
		
		if (tabCenter.getSelectedIndex() == 0) {

		for (int x = 0; x < modelPCD.getRowCount(); x++) {
			BigDecimal totalamount = (BigDecimal) modelPCD.getValueAt(x, 3);

			try {
				totalAmountCommitted = totalAmountCommitted.add(totalamount);
			} catch (Exception e1) { }
		}

		modelPCDTotal.setValueAt(totalAmountCommitted, 0, 3);
		}
		
		if (tabCenter.getSelectedIndex() == 2) {

			for (int x = 0; x < modelPCD_BlockLot.getRowCount(); x++) {
				BigDecimal totalamount = (BigDecimal) modelPCD_BlockLot.getValueAt(x, 3);

				try {
					totalAmountCommitted = totalAmountCommitted.add(totalamount);
				} catch (Exception e1) { }
			}

			modelPCDTotal_BlockLot.setValueAt(totalAmountCommitted, 0, 3);
			}
	}
	
	private void checkAllClientList(){//

		int rw = tblPCD_Batch.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";
			
			try { name	= tblPCD_Batch.getValueAt(x,2).toString().toUpperCase();}
			 catch (NullPointerException e) { name	= ""; }
			
			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			//Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			//Boolean	end			= name.endsWith(acct_name);

			if (start==true) {
				tblPCD_Batch.setRowSelectionInterval(x, x); 
				tblPCD_Batch.changeSelection(x, 2, false, false);				
				break;			
			}
			else {
				tblPCD_Batch.setRowSelectionInterval(0, 0); 
				tblPCD_Batch.changeSelection(0, 2, false, false);					
			}

			x++;

		}		
	}
	
	private boolean batch() {
		
		boolean x = false;
		String batch = "";
		
		String strSQL = "select batch_no from rf_processing_cost where status_id = 'A' and batch_no = '"+lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "")+"' and rplf_no is not null and created_by = '"+ UserInfo.EmployeeCode +"'";
		
		System.out.println("Dumaan dito!!!!!!!");
		
		FncSystem.out("Batch sql", strSQL);
		
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
	
	private static Boolean checkEmpCode(String user) {//-----added by JED 9-5-18 : filter clients with existing tag for particular no.219 (for PMD)-----//

		Boolean x = false;
		String employee = "";
		
		String strSQL = 
				"select \n" + 
				"a.entity_id,\n" + 
				"b.entity_name,\n" + 
				"a.emp_code\n" + 
				"from em_employee a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"where a.div_code = '05'\n" + 
				"and a.dept_code = '08'\n" + 
				"and emp_code = '"+user+"' ";

		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			employee = (String) db.getResult()[0][0];
		}
		
		if (employee.equals(null) || employee.isEmpty()) {
			x=false;
			System.out.println(x);
			} else {x=true;}
		
		return x;
	}
	
	private void createRPLF(){
		
//		if (tabCenter.getSelectedIndex() == 0) {
//			if (validateSaving()) {
//
//				int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//				if (response == JOptionPane.YES_OPTION) {
//
//					//String rplf_no = rplfNo();
//					for(int x = 0; x < tblPCD.getRowCount(); x++){
//						Boolean isSelected = (Boolean) modelPCD.getValueAt(x, 0);
//						String costdesc = (String) modelPCD.getValueAt(x, 1);
//						Object[] particulars = getProcessingCostDetails1(costdesc);
//						Date trans_date = (Date) modelPCD.getValueAt(x, 2);
//						BigDecimal amount = (BigDecimal) modelPCD.getValueAt(x, 3);
//						String remarks = (String) modelPCD.getValueAt(x, 4);
//						String desc = (String) modelPCD.getValueAt(x, 1);
//						String entity_id = (String) clientDetails[39];
//						String unit_id = (String) clientDetails[40];
//						String projcode = (String) clientDetails[2];
//						String pbl_id = (String) clientDetails[4];
//						Integer seq_no = (Integer) clientDetails[6];
//						String costid = (String) particulars[0];
//						BigDecimal setup_amount = (BigDecimal) particulars[1];
//						String req_id = (String) UserInfo.EmployeeCode;
//						String emp_code = (String) UserInfo.EmployeeCode;
//						String type = (String) cmbType.getSelectedItem();
//						String client_id = (String) lookupClient.getText();
//						String request_type = (String) lookupRequestType.getText();
//						String availer = (String) lookupAvailer.getText();
//						String availer_type = (String) lookupAvailerType.getText();
//						//String batch_no = batchNo();
//						String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
//
//						if(isSelected){
//
//							String SQL = "SELECT sp_save_processing_cost_new('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
//									+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', '"+batch_no+"', NULLIF('"+unit_id+"','null'),"
//									+ "'"+desc+"', '"+type+"', "+seq_no+", '"+client_id+"', '"+request_type+"', '"+availer+"', '"+availer_type+"')";
//
//							pgSelect db = new pgSelect();
//							FncSystem.out("SQL", SQL);
//							db.select(SQL);
//
//							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
//						}
//					}
//
//					JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF", JOptionPane.INFORMATION_MESSAGE);
//					lblBatch.setText("<html><b>Batch No:</html>");
//
//					modelPCD.setRowCount(0);
//					pnlState(true, true, true);
//					tblPCD.setEnabled(true);
//					lookupCode.setEnabled(true);
//
//					Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
//					optionPaneWindow.dispose();
//
//					Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
//					if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
//						optionPaneWindowMain.getFocusOwner();
//						lookupClient.setText("");
//						refreshBuyer();
//
//
//					}
//				}
//			}
//		}
		
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
						
						String SQL = "SELECT sp_save_processing_cost_rplf('"+emp_code+"', '"+batchno+"', '"+request_type+"', '"+availer+"', '"+availer_type+"')";
	
						pgSelect db = new pgSelect();
						FncSystem.out("SQL", SQL);
						db.select(SQL);
					//}
				//}
				JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF", JOptionPane.INFORMATION_MESSAGE);
				lblBatch.setText("<html><b>Batch No:</html>");
				
				modelPCD_Batch.setRowCount(0);
				//displayPCostClient(lookupCode_Batch.getValue());  //--removed by jed 2018-10-16 : DCRF no. 797 put control on tagging particulars in PCOST to avoid double tagging--//
				//pnlState(true, true, true);
				
				Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
				optionPaneWindow.dispose();
	
				Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
				if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
					optionPaneWindowMain.getFocusOwner();
					lookupCode_Batch.setText("");
					refreshBatch();
					btnDRF.setEnabled(false);
					
				}
			}
		}
		
//		if (tabCenter.getSelectedIndex() == 2) {
//			if (validateSaving()) {
//				
//				int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//				if (response == JOptionPane.NO_OPTION) {
//					//lookupProject.setText(" ");
//					//refreshBlockLot();
//				}
//				else {
//				//String rplf_no = rplfNo();
//				for(int x = 0; x < tblPCD_BlockLot.getRowCount(); x++){
//					String cost_desc = (String) modelPCD_BlockLot.getValueAt(x, 1);
//					Object[] particulars = getProcessingCostDetails1(cost_desc);
//					Boolean isSelected = (Boolean) modelPCD_BlockLot.getValueAt(x, 0);
//					Date trans_date = (Date) modelPCD_BlockLot.getValueAt(x, 2);
//					BigDecimal amount = (BigDecimal) modelPCD_BlockLot.getValueAt(x, 3);
//					String remarks = (String) txtRemarks_BlockLot.getText();
//					String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
//					String desc = (String) modelPCD_BlockLot.getValueAt(x, 1);
//					String pbl_id = (String) lookupUnit.getValue();
//					String costid = (String) particulars[0];
//					BigDecimal setup_amount = (BigDecimal) particulars[1];
//					String req_id = (String) txtRequestedBy_BlockLot.getText();
//					String emp_code = (String) UserInfo.EmployeeCode;
//					String type = (String) cmbType_BlockLot.getSelectedItem();
//					String request_type = (String) lookupRequestType.getText();
//					String availer = (String) lookupAvailer.getText();
//					String availer_type = (String) lookupAvailerType.getText();
//					//String batch_no = batchNo();
//					
//					
//					
//					if(isSelected){
//				
//						String SQL = "SELECT sp_save_processing_cost('"+entity2+"', '015', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
//								+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', '"+batch_no+"', NULLIF('"+unitid+"','null'),"
//								+ "'"+desc+"', '"+type+"', "+seqno+", '"+entity2+"', '"+request_type+"', '"+availer+"', '"+availer_type+"')";
//			
//						pgSelect db = new pgSelect();
//						FncSystem.out("SQL", SQL);
//						db.select(SQL);
//					}
//				}
//				
//				JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF", JOptionPane.INFORMATION_MESSAGE);
//				lblBatch.setText("<html><b>Batch No:</html>");
//				
//				Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
//				optionPaneWindow.dispose();
//			
//				Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
//				if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
//					optionPaneWindowMain.getFocusOwner();
//					lookupProject.setText("");
//					refreshBlockLot();
//					
//					
//				}
//				}
//			}
//		}
	}
	
	private void save(){
		if (tabCenter.getSelectedIndex() == 0) {
			/*BigDecimal amount = (BigDecimal) txtAmount_Batch.getValued();
				if(amount.compareTo(new BigDecimal("0.00")) == 0){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
				}else{
							//JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
				}

			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"New Batch No? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				getBatchList();

			}*/

			/*int response2 = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response2 == JOptionPane.NO_OPTION) {
				//lookupCode_Batch.setText(" ");
				//refreshBatch();
			}
			else {*/
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
						for(int x = 0; x < modelPCD_Batch.getRowCount(); x++){
							Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Batch.getDate();
							BigDecimal amount1 = (BigDecimal) txtAmount_Batch.getValued();
							String remarks = (String) txtRemarks_Batch.getText();
							//String remarks = "";
							//String desc = (String) lblCode_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
							String entity_id = (String) modelPCD_Batch.getValueAt(x, 1);
							String unit_id = (String) modelPCD_Batch.getValueAt(x, 9);
							String projcode = (String) modelPCD_Batch.getValueAt(x, 7);
							String pbl_id = (String) modelPCD_Batch.getValueAt(x, 4);
							Integer seq_no = (Integer) modelPCD_Batch.getValueAt(x, 5);
							String costid = (String) lookupCode_Batch.getValue();
							BigDecimal setup_amount = (BigDecimal) txtAmount_Batch.getValued(); //new BigDecimal (txtAmount_Batch.getText());
							String req_id = (String) txtRequestedBy_Batch.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							//String type = (String) cmbType_Batch.getSelectedItem();
							//String client_id = (String) lookupClient.getText();
							//String request_type = (String) lookupRequestType.getText();
							//String availer_type = (String) lookupAvailerType.getText();
							//String batch_no = batchNo();
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

							if(isSelected){

								String SQL = "SELECT sp_save_processing_cost_woutrplf('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
										+ ""+amount1+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ ""+seq_no+", '"+rplf_no+"')";

								pgSelect db = new pgSelect();
								FncSystem.out("SQL", SQL);
								db.select(SQL);

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
							}
						}

						JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
						/*modelPCD_Batch.setRowCount(0);
				displayPCostClient();*/

						//---added by jed 2018-10-16 : DCRF no. 797 put control in tagging of particulars to avoid double tagging---//
						modelPCD_Batch.clear();
						scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
						tblPCD_Batch.packAll();
						
						//---added by jed 2018-8-6 due to multiple saving
						btnTSave.setEnabled(false); 
						btnDRF.setEnabled(true);
						//pnlState(true, true, true);
						//displayPCostClient(null);
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
	
	private void saveOR(){
		if (tabCenter.getSelectedIndex() == 0) {
			/*int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"New Batch No? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				getBatchList();

			}*/

			/*int response2 = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response2 == JOptionPane.NO_OPTION) {
				//lookupCode_Batch.setText(" ");
				//refreshBatch();
			}
			else {*/
			String or_no = txtORNo.getText();
			String or_year = txtYear.getText();

			if(or_no.equals("") || or_no.equals(null)){
				JOptionPane.showMessageDialog(getContentPane(), "Please input OR number!", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				if(or_year.equals("") || or_year.equals(null)){
					JOptionPane.showMessageDialog(getContentPane(), "Please input OR Year!", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

						String rplf_no = "";
						for(int x = 0; x < tblPCD_Batch.getRowCount(); x++){
							Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Batch.getDate();
							BigDecimal amount = new BigDecimal (txtAmount_Batch.getText());
							String remarks = (String) txtRemarks_Batch.getText();
							String desc = (String) lblCode_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
							String entity_id = (String) modelPCD_Batch.getValueAt(x, 1);
							String unit_id = (String) modelPCD_Batch.getValueAt(x, 9);
							String projcode = (String) modelPCD_Batch.getValueAt(x, 7);
							String pbl_id = (String) modelPCD_Batch.getValueAt(x, 4);
							Integer seq_no = (Integer) modelPCD_Batch.getValueAt(x, 5);
							String costid = (String) lookupCode_Batch.getValue();
							BigDecimal setup_amount = new BigDecimal (txtAmount_Batch.getText());
							String req_id = (String) txtRequestedBy_Batch.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							String type = (String) cmbType_Batch.getSelectedItem();
							String request_type = (String) lookupRequestType.getText();
							String availer = (String) lookupAvailer.getText();
							String availer_type = (String) lookupAvailerType.getText();
							//String batch_no = batchNo();
							String or = (String) txtORNo.getText();
							String yr = (String) txtYear.getText();
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

							if(isSelected){

								String SQL = "SELECT sp_save_processing_cost_woutrplf_withor('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
										+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ "'"+desc+"', '"+type+"', "+seq_no+", '"+request_type+"', '"+availer+"', '"+availer_type+"', '"+rplf_no+"', "+yr+", '"+or+"')";

								pgSelect db = new pgSelect();
								FncSystem.out("SQL", SQL);
								db.select(SQL);

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
							}
						}

						JOptionPane.showMessageDialog(getContentPane(), "Saved.", "", JOptionPane.INFORMATION_MESSAGE);
						/*modelPCD_Batch.setRowCount(0);
					displayPCostClient();*/
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateOR);
						optionPaneWindow.dispose();

						Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
						if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
							optionPaneWindowMain.getFocusOwner();
							//lookupProject.setText(" ");
							//refreshBlockLot();
						}
					}
				}
			}
		}
		//}
	}
	
	private void cancel(){//---added by jed 2018-10-15 : for refreshing all fields (no dcrf)---//
		
//		if (tabCenter.getSelectedIndex() == 0) {
//			
//			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
//					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//			
//			JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
//			
//			lookupClient.setText("");
//			lblClient.setText("[ ]");
//			lblProject.setText("[ ]");
//			lblPblDescription.setText("[ ]");
//			lblSellingAgent.setText("[ ]");
//			lblDateReserved.setText("[ ]");
//			lblStatus.setText("[ ]");
//			lblBatch.setText("<html><b>Batch No:</html>");
//			lblCode.setText("[ ]");
//			lookupCode.setText("");
//			lookupCode.setEnabled(true);
//			txtAmount.setText("0.00");
//			txtRequestedBy.setText(null);
//			lblReqBy1.setText("[ ]");
//			txtRemarks.setText(null);
//			btnDRF.setEnabled(false);
//			btnTSave.setEnabled(false);
//			btnBatch.setEnabled(false);
//			cmbType.setSelectedItem("Payment");
//			dateSched.setDate(Calendar.getInstance().getTime());
//			lblSetupAmount1.setText("0.00");
//			lblRunningTotal1.setText("0.00");
//			lblBalanceAmount.setText("0.00");
//			tblPCD.setEnabled(true);
//			modelPCD.setRowCount(0);
//			modelPCDTotal.setValueAt(new BigDecimal(0.00), 0, 3);
//			modelPCD.clear();
//			txtAmount.setEditable(false);
//			txtRequestedBy.setEditable(false);
//			dateSched.setEnabled(false);
//			btnOR.setEnabled(false);
//			btnDRF.setText("<html><center><font size = 2>Create Disb.\n Request</html>");
//			btnDRF.setActionCommand("Create Disbursement Request");
//			mniTag.setEnabled(false);
//			mniTag_2.setEnabled(false);
//			mniDelete.setEnabled(false);
//			pnlState(true, true, true);
//			
//			}
//			
//		}
		
		if (tabCenter.getSelectedIndex() == 0) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			
			JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
			
			modelPCD_Batch.clear();
			scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
			tblPCD_Batch.packAll();
			lookupCode_Batch.setText("");
			lblCode_Batch.setText("[ ]");
			cmbType_Batch.setSelectedItem("Payment");
			dateSched_Batch.setDate(Calendar.getInstance().getTime());
			txtAmount_Batch.setText("0.00");
			txtRequestedBy_Batch.setText(null);
			txtRequestedBy_Batch.setEditable(false);
			lblReqBy1_Batch.setText("[ ]");
			txtRemarks_Batch.setText(null);
			lblSetupAmount1_Batch.setText("0.00");
			lblRunningTotal1_Batch.setText("0.00");
			lblBalanceAmount_Batch.setText("0.00");
			btnDRF.setEnabled(false);
			btnTSave.setEnabled(false);
			btnBatch.setEnabled(true);
			modelPCD_Batch.setRowCount(0);
			lblBatch.setText("<html><b>Batch No:</html>");
			btnOR.setEnabled(false);
			//pnlState(true, true, true);
			
			}
			
		}
		
//		if (tabCenter.getSelectedIndex() == 2){
//			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
//					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//			
//			JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
//			
//			lookupProject.setText("");
//			lblProject2.setText("[ ]");
//			lookupUnit.setValue(null);
//			lblUnit.setText("[ ]");
//			lblBuyer.setText("[ ]");
//			lblCode_BlockLot.setText("[ ]");
//			lookupCode_BlockLot.setText(null);
//			txtAmount_BlockLot.setText("0.00");
//			txtRequestedBy_BlockLot.setText(null);
//			lblReqBy1_BlockLot.setText("[ ]");
//			txtRemarks_BlockLot.setText(null);
//			btnDRF.setEnabled(false);
//			btnTSave.setEnabled(false);
//			btnBatch.setEnabled(false);
//			cmbType_BlockLot.setSelectedItem("Payment");
//			dateSched_BlockLot.setDate(Calendar.getInstance().getTime());
//			lblSetupAmount1_BlockLot.setText("0.00");
//			lblRunningTotal1_BlockLot.setText("0.00");
//			lblBalanceAmount_BlockLot.setText("0.00");
//			modelPCD_BlockLot.setRowCount(0);
//			modelPCDTotal_BlockLot.setValueAt(new BigDecimal(0.00), 0, 3);
//			txtAmount_BlockLot.setEditable(false);
//			txtRequestedBy_BlockLot.setEditable(false);
//			dateSched_BlockLot.setEnabled(false);
//			lblBatch.setText("<html><b>Batch No: </html>");
//			btnOR.setEnabled(false);
//			pnlState(true, true, true);
//			
//			}
//		}
		
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
	
	/*
	private static String SQL_entity(String entity) {
		String strSQL = "select entity_id from rf_processing_cost where '"+ lookupCode_Batch.getValue() +"' in ('047', '109','047','211','210', '032', \n" + 
				"								'217','033','031','030','009','019','016','015','018','017','028','025','027','026','029','008','007','006','011','014', \n" + 
				"								'012','013','010','021','022','024','023','020','215','216','105','214','213','212','108') \n" + 
				"								and status_id = 'A' and entity_id = '"+entity+"' LIMIT 1";

		FncSystem.out("Entity", strSQL);
		return strSQL;
	} 
	
	private boolean control() {
		
		boolean x = false;
		
		
		String strSQL = "select entity_id from rf_processing_cost where '"+ lookupCode_Batch.getValue() +"' in ('047', '109','047','211','210', '032',\n" + 
								"'217','033','031','030','009','019','016','015','018','017','028','025','027','026','029','008','007','006','011','014',\n" + 
								"'012','013','010','021','022','024','023','020','215','216','105','214','213','212','108')\n" + 
								"and status_id = 'A' and entity_id = '"+entityid+"' LIMIT 1";
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			entity = (String) db.getResult()[0][0];
		}
		
		if (entity.equals(null) || entity.isEmpty()) {
			x=false;
			System.out.println(x);
			} else {x=true;}
		
		return x;
	} */
}
