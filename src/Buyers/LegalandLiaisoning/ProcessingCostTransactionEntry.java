package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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

import org.jdesktop.swingx.JXPanel;
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
import Functions.FncLookAndFeel;
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
import tablemodel.model_Block_client;
import tablemodel.model_Block_client_RPT;
import tablemodel.model_client;
import tablemodel.model_pcost;

public class ProcessingCostTransactionEntry extends _JInternalFrame implements _GUI, MouseListener {

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
	JPanel	pnlContent1;

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
	JLabel lblctrNo;
	JLabel lblCompany;
	JLabel lblProj;

	private JTextField txtProjectName;
	private JTextField txtCoName;
	private static _JLookup lookupCoID;
	private static _JLookup lookupProjectID;

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
	_JLookup lookupControlNo;
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

	static String title = "Processing Cost Transaction Entry";
	Dimension frameSize = new Dimension(900, 630);// 510, 250
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
	private String oth_pbl_id;
	private String pbl_id;
	private String entity_id;





	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	DecimalFormat df = new DecimalFormat("#,###,##0.00");
	private JButton btnCancel;
	private JPopupMenu menu;
	private JMenuItem mniDelete;
	private String selected_batch;
	private JRadioButton radMerge;
	private JRadioButton radSingle;
	private JPanel pnlBlockBatch;
	private JScrollPane scrollPCD_Block_Batch;
	private model_Block_client modelPCD_Block_Batch;
	private JList rowHeaderPCD_Block_Batch;
	private _JTableMain tblPCD_Block_Batch;
	private JPanel pnlBlockCenter;
	private JPanel pnlBlockTable;
	private JPanel pnlBlockDetail;
	private JPanel pnlBlockSearch;
	private JXTextField txtBlockSearch;
	private JPanel pnlBlockInfo;
	private JLabel lblBlockType;
	private JLabel lblBlockInfo;
	private JLabel lblBlockDate;
	private JLabel lblBlockAmount;
	private JLabel lblBlockReqBy;
	private JLabel lblBlockRemarks;
	private JComboBox cmbType_Block_Batch;
	private _JLookup lookupCode_Block_Batch;
	private JLabel lblCode_Block_Batch;
	private _JDateChooser dateSched_Block_Batch;
	private _JXFormattedTextField txtAmount_Block_Batch;
	private JLabel lblRunningTotal1_Block_Batch;
	private JLabel lblBalanceAmount_Block_Batch;
	private JLabel lblSetupAmount1_Block_Batch;
	private JPanel pnlBlockAmount;
	private JXTextField txtRequestedBy_Block_Batch;
	private JLabel lblReqBy1_Block_Batch;
	private JXTextField txtRemarks_Block_Batch;
	private String remarks_block;
	private String remarks_indi;

	private JCheckBox chkOld;

	private JCheckBox chkcancellled;

	private JPanel pnlrpt;
	private JScrollPane scrollPCD_Block_Batch_RPT;
	private JList rowHeaderPCD_Block_Batch_RPT;
	private model_Block_client_RPT modelPCD_Block_Batch_RPT;
	private _JTableMain tblPCD_Block_Batch_RPT;
	private JPanel pnlBlockBatch_RPT;
	private JPanel pnlBlockCenter_rpt;
	private JPanel pnlBlockTable_rpt;
	private JPanel pnlBlockSearch_rpt;
	private JPanel pnlBlockDetail_rpt;
	private JXTextField txtBlockSearch_rpt;
	private JPanel pnlBlockInfo_rpt;
	private JLabel lblBlockType_rpt;
	private JLabel lblBlockInfo_rpt;
	private JComboBox cmbType_Block_Batch_rpt;
	private _JLookup lookupCode_Block_Batch_rpt;
	private JPanel pnlBlockAmount_rpt;
	private JLabel lblCode_Block_Batch_rpt;
	private JPanel pnlConnectRPLF;
	private JButton btnsave_rpt;
	private JButton btnrplf;
	private JButton btncancel_rpt;
	private JButton btnnew_rpt;
	private JButton btnbatch_wout_rplf_rpt;

	private JButton btnsave_w_amount;

//	private String maraming_entity;


	public ProcessingCostTransactionEntry() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ProcessingCostTransactionEntry(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public ProcessingCostTransactionEntry(String title, boolean resizable,
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
			this.getContentPane().add(pnlMain);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			{//--added by JED 2018-11-21 : for double tagging--//
				menu = new JPopupMenu("Popup");
				mniDelete = new JMenuItem("Delete       ");
				menu.add(mniDelete);
				mniDelete.setEnabled(false);
				mniDelete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						delete();

					}
				});
			}
			//Connect batch to rplf
			{
				pnlConnectRPLF = new JPanel(new BorderLayout(5,5));
				pnlConnectRPLF.setPreferredSize(new java.awt.Dimension(500, 125));
			}
			{
				pnlCreateRPLF= new JPanel(new BorderLayout(5,5));
				//				pnlCreateRPLF.setLayout(null);
				pnlCreateRPLF.setPreferredSize(new java.awt.Dimension(500, 125));

				{
					JPanel pnlCRPLFWest = new JPanel(new GridLayout(4,1,3,3));
					pnlCreateRPLF.add(pnlCRPLFWest,BorderLayout.WEST);
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
					JPanel pnlCRPLFCenter = new JPanel(new GridLayout(4,1,3,3));
					pnlCreateRPLF.add(pnlCRPLFCenter,BorderLayout.CENTER);
					{
						JPanel pnlRequestType = new JPanel(new BorderLayout(5,5));
						pnlCRPLFCenter.add(pnlRequestType);
						{
							JPanel pnllookRequestType = new JPanel(new BorderLayout(5,5));
							pnlRequestType.add(pnllookRequestType,BorderLayout.WEST);
							pnllookRequestType.setPreferredSize(new Dimension(100,0));
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
										if(typeId.equals("06")){
											lookupControlNo.setEnabled(true);

										}
										else
										{ 
											lookupControlNo.setEnabled(false);
											lookupControlNo.setText("");
											lblctrNo.setText("[ ]");
										}
									}
								});
							}
						}

						{
							JPanel pnllblRequest = new JPanel(new BorderLayout(5,5));	
							pnlRequestType.add(pnllblRequest,BorderLayout.CENTER);

							{
								lblRequestType = new JLabel("[ ]");
								pnllblRequest.add(lblRequestType);
								lblRequestType.setHorizontalAlignment(JLabel.LEFT);


							}
						}	
					}
					{
						JPanel pnlControlNo = new JPanel(new BorderLayout(5,5));
						pnlCRPLFCenter.add(pnlControlNo);
						{
							JPanel pnllookControl = new JPanel(new BorderLayout(5,5));
							pnlControlNo.add(pnllookControl,BorderLayout.WEST);
							pnllookControl.setPreferredSize(new Dimension(100,0));
							{
								lookupControlNo = new _JLookup(null,"Control No.");
								pnllookControl.add(lookupControlNo);
								lookupControlNo.setReturnColumn(0);;
								lookupControlNo.setEnabled(false);
								lookupControlNo.setLookupSQL(ControlNoLookUpValue());
								lookupControlNo.addLookupListener(new LookupListener() {


									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){

											lblctrNo.setText(String.format("[%s]", data[0]));
										}

									}
								});

							}
						}
						{
							JPanel pnllblControlNo = new JPanel(new BorderLayout(5,5));
							pnlControlNo.add(pnllblControlNo,BorderLayout.CENTER);
							{
								lblctrNo = new JLabel("[ ]");
								pnllblControlNo.add(lblctrNo);
							}
						}
					}
					{
						JPanel pnlAvailer = new JPanel(new BorderLayout(5,5));
						pnlCRPLFCenter.add(pnlAvailer);
						{
							JPanel pnllookAvailer = new JPanel(new BorderLayout(5,5));
							pnlAvailer.add(pnllookAvailer,BorderLayout.WEST);
							pnllookAvailer.setPreferredSize(new Dimension(100,0));
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

											lblAvailer.setText(String.format("[ %s ]", (String) data[1]));
											lookupAvailerType.setLookupSQL(SQL_AVAILERTYPE((String) data[0]));

											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
						}
						{
							JPanel pnllblAvailer = new JPanel(new BorderLayout(5,5));
							pnlAvailer.add(pnllblAvailer,BorderLayout.CENTER);
							pnllblAvailer.setPreferredSize(new Dimension(100,0));
							{
								lblAvailer = new JLabel("[ ]");
								pnllblAvailer.add(lblAvailer);
							}

						}
					}
					{
						JPanel pnlavailerType = new JPanel(new BorderLayout(5,5));
						pnlCRPLFCenter.add(pnlavailerType);
						{
							JPanel pnllookAvailerType = new JPanel(new BorderLayout(5,5));
							pnlavailerType.add(pnllookAvailerType,BorderLayout.WEST);
							pnllookAvailerType.setPreferredSize(new Dimension(100,0));
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
							JPanel pnllblAvailerType = new JPanel(new BorderLayout(5,5));
							pnlavailerType.add(pnllblAvailerType,BorderLayout.CENTER);
							{
								lblAvailerType = new JLabel("[ ]");
								pnllblAvailerType.add(lblAvailerType);
							}
						}

					}
				}
				{
					JPanel pnlCRPLFSouth = new JPanel(new GridLayout(1,3,3,3));
					pnlCreateRPLF.add(pnlCRPLFSouth,BorderLayout.SOUTH);
					pnlCRPLFSouth.setPreferredSize(new Dimension(0,30));

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
				JPanel pnlDetails = new JPanel (new GridLayout(2, 1, 5, 5));
				pnlMain.add(pnlDetails, BorderLayout.NORTH);
				pnlDetails.setPreferredSize(new Dimension(0,95));
				pnlDetails.setBorder(JTBorderFactory.createTitleBorder("Details"));
				{
					//COMPANY

					JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
					pnlDetails.add(pnlCompany);
					{
						JLabel lblCompany = new JLabel("Company: ");
						pnlCompany.add(lblCompany, BorderLayout.WEST);
						lblCompany.setPreferredSize(new Dimension(70, 0));

					}
					{

						lookupCoID = new _JLookup(null, "Company");
						pnlCompany.add(lookupCoID, BorderLayout.CENTER);
						lookupCoID.setHorizontalAlignment(JTextField.CENTER);
						lookupCoID.setReturnColumn(0);
						lookupCoID.setLookupSQL(SQL_COMPANY());
						lookupCoID.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									String co_name = (String)data[1];
									txtCoName.setText(co_name);
									lookupProjectID.setLookupSQL(SQL_PROJECT());
									lookupProjectID.setValue("");
									modelPCD_Block_Batch_RPT.clear();
									modelPCD_Block_Batch_RPT.setRowCount(0);
								}
							}
						});


						{
							JPanel xtra = new JPanel(new BorderLayout(5, 5));
							pnlCompany.add(xtra, BorderLayout.EAST);
							xtra.setPreferredSize(new Dimension(750, 0));
							{
								txtCoName = new JTextField();
								xtra.add(txtCoName, BorderLayout.CENTER);
								txtCoName.setHorizontalAlignment(JTextField.CENTER);
								txtCoName.setEditable(false);

							}

							{
								JLabel xtralbl = new JLabel("");
								xtra.add(xtralbl, BorderLayout.EAST);
								xtralbl.setBackground(Color.cyan);
								xtralbl.setPreferredSize(new Dimension(250, 0));
							}
						}
					}
				}
				{
					JPanel pnlProj = new JPanel(new BorderLayout(5, 5));
					pnlDetails.add(pnlProj);
					{	
						//Project					
						JLabel lblProj = new JLabel("Project:");
						pnlProj.add(lblProj, BorderLayout.WEST);
						lblProj.setPreferredSize(new Dimension(70, 0));
					}
					{
						lookupProjectID = new _JLookup(null, "Project");
						pnlProj.add(lookupProjectID, BorderLayout.CENTER);
						lookupProjectID.setHorizontalAlignment(JTextField.CENTER);
						lookupProjectID.setReturnColumn(0);
						lookupProjectID.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									txtProjectName.setText(data[1].toString());		
									//cmbType_Batch.setEnabled(true);
									if (tabCenter.getSelectedIndex() == 4) {
										btnnew_rpt.setEnabled(true);
										btnbatch_wout_rplf_rpt.setEnabled(true);
										
										//displayAvailablePCostClients(modelPCD_Block_Batch_RPT, rowHeaderPCD_Block_Batch_RPT, null, 1);
									}
								}

								String proj_id = (String) data[0];
								lookupClient.setLookupSQL(SQL_Clients(proj_id));

							}

						});

						{
							JPanel xtra2 = new JPanel(new BorderLayout(5, 5));
							pnlProj.add(xtra2, BorderLayout.EAST);
							xtra2.setPreferredSize(new Dimension(750, 0));
							{
								txtProjectName = new JTextField();
								xtra2.add(txtProjectName, BorderLayout.CENTER);
								txtProjectName.setHorizontalAlignment(JTextField.CENTER);
								txtProjectName.setEditable(false);

							}

							{
								JLabel xtralbl2 = new JLabel("");
								xtra2.add(xtralbl2, BorderLayout.EAST);
								xtralbl2.setBackground(Color.cyan);
								xtralbl2.setPreferredSize(new Dimension(250, 0));
							}
						}
					}
				}

			}
			Setdefaults();
			{


				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				//pnlMain.setBackground(Color.blue);

				{
					pnlBuyer = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Buyer Cost - Individual", null, pnlBuyer, null); //XXX Buyer Cost - Individual
					pnlBuyer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					//pnlBuyer.setBackground(Color.blue);
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
								//lookupClient.setLookupSQL(_CARD.sqlClients());
								//lookupClient.setLookupSQL(lookupClients());
								lookupClient.setFilterCardName(true);
								lookupClient.setBounds(70, 10, 100, 22);
								lookupClient.addLookupListener(new LookupListener() {


									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											entity_id = (String) data[1];
											String proj_id = (String) data[7];
											pbl_id = (String) data[4];
											Integer seq_no = (Integer) data[5];
											Boolean isSelected = (Boolean) data[11];
											String emp_code = UserInfo.EmployeeCode;

											if(isSelected){
												System.out.println("Dumaan dito");
												//oth_pbl_id = (String) data[4];
												String pbl_id2 = checkPBL(pbl_id);

												displayClientDetails(entity_id, proj_id, pbl_id2, seq_no, true);

												lookupCode.setLookupSQL(PCOST_BuyerInd(entity_id, pbl_id));

												lblPblDescription.setText(String.format("[ %s ]", (String) data[3]));

												displayPCostDetails_v2(pbl_id);

											}else{

												System.out.println("Dumaan dito 2");

												displayClientDetails(entity_id, proj_id, pbl_id, seq_no, true);

												lookupCode.setLookupSQL(PCOST_BuyerInd(entity_id, pbl_id));

												displayPCostDetails();

											}

											lblBatch.setText("<html><b>Batch No:</html>");
											lblCode.setText("[ ]");
											lookupCode.setText("");
											txtAmount.setText("0.00");
											txtRequestedBy.setText(null);
											lblReqBy1.setText("[ ]");
											txtRemarks.setText(null);
											cmbType.setSelectedItem("Payment");
											//dateSched.setDate(Calendar.getInstance().getTime());
											dateSched.setDate(FncGlobal.getDateToday());
											lblSetupAmount1.setText("0.00");
											lblRunningTotal1.setText("0.00");
											lblBalanceAmount.setText("0.00");

											btnBatch.setEnabled(false);
											btnTSave.setEnabled(false);


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
						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details"));
						pnlCenter.setPreferredSize(new Dimension(800, 340));
						{
							pnlTable = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlTable, BorderLayout.WEST);
							pnlTable.setPreferredSize(new Dimension(460, 200));
							pnlTable.setBorder(lineBorder);
							{
								scrollPCD = new _JScrollPaneMain();
								pnlTable.add(scrollPCD, BorderLayout.CENTER);
								modelPCD = new model_pcost();
								modelPCD.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowHeaderPCD.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowHeaderPCD.getModel()).addElement(modelPCD.getRowCount());
										}
									}
								});

								tblPCD = new _JTableMain(modelPCD);
								tblPCD.packAll();
								tblPCD.setAlignmentX(LEFT_ALIGNMENT);
								scrollPCD.setViewportView(tblPCD);
								tblPCD.addMouseListener(new PopupTriggerListener_panel());

								tblPCD.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent arg0) {
										try {
											if(!arg0.getValueIsAdjusting()){

												String rplf_no = (String) modelPCD.getValueAt(tblPCD.getSelectedRow(), 6);
												String cost_desc = (String) modelPCD.getValueAt(tblPCD.getSelectedRow(), 1);
												String entity_id = lookupClient.getValue();
												String batch = (String) modelPCD.getValueAt(tblPCD.getSelectedRow(), 11);
												Boolean isSelected = (Boolean) modelPCD.getValueAt(tblPCD.getSelectedRow(), 0);
												 
												System.out.printf("The value of rplf is: %s\n", rplf_no);
												System.out.printf("The value of cost_dec is: %s\n", cost_desc);

											
												displayProcessingCostDetails_IND(cost_desc, entity_id, batch);
											
												if(tblPCD.getSelectedRows().length > 0){

													if (isSelected){
														txtAmount.setEditable(true);
														mniDelete.setEnabled(false);
													}else{
														txtAmount.setEditable(false);
														mniDelete.setEnabled(true);
													}
												}
											}
										} 
										catch (ArrayIndexOutOfBoundsException e) { }
									}
								});
								tblPCD.putClientProperty("terminateEditOnFocusLost", true);
								{
									rowHeaderPCD = tblPCD.getRowHeader();
									rowHeaderPCD.setModel(new DefaultListModel());
									scrollPCD.setRowHeaderView(rowHeaderPCD);
									scrollPCD.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}

							}
							{
								scrollPCDTotal = new _JScrollPaneTotal(scrollPCD);
								pnlTable.add(scrollPCDTotal, BorderLayout.SOUTH);
								{
									modelPCDTotal = new model_pcost();
									modelPCDTotal.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00), null, null, null, null, null, null, null });

									tblPCDTotal = new _JTableTotal(modelPCDTotal, tblPCD);
									scrollPCDTotal.setViewportView(tblPCDTotal);

									tblPCDTotal.setTotalLabel(2);
								}
								scrollPCDTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
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
																lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
																txtAmount.setValue(data [2]);
																lblSetupAmount1.setText(String.format("%s", data[2]));
																lblBalanceAmount.setText(String.format("%s", lblSetupAmount1.getText()));
																txtRequestedBy.setText(emp_code);
																lblReqBy1.setText(String.format("[ %s ]", emp_name));
																pnlState(true, false, false, false, false);

																if (data[3] != null){
																	txtRemarks.setText(String.format("%s", data[3]));
																} else {
																	txtRemarks.setText(" ");
																}

																if(data != null){
																	BigDecimal amount = new BigDecimal (String.format("%s", reg_amount2(entity, pbl, buyertype)));
																	BigDecimal amount1 = new BigDecimal (String.format("100.00"));
																	if (lookupCode.getValue().equals("109") || lookupCode.getValue().equals("210") || lookupCode.getValue().equals("105") || lookupCode.getValue().equals("108")){
																		//																		modelPCD.addRow(new Object []{true, "ENTRY FEE DEED OF ASSIGNMENT", Calendar.getInstance().getTime(), new BigDecimal(30.00), "Fixed Amt", null, null, null, "Payment", null, null});
																		//																		modelPCD.addRow(new Object []{true, "JUDICIAL FORM FEE", Calendar.getInstance().getTime(), new BigDecimal(30.00), "DUE TO RD COMPUTERIZATION (originally encoded as it fee, revised by sir alan 7/26/10)", null, null, null, "Payment", null, null});
																		//																		modelPCD.addRow(new Object []{true, "REGISTRATION FEE OF ASSIGNMENT", Calendar.getInstance().getTime(), amount, "Refer to RD Table", null, null, null, "Payment", null, null});
																		//																		modelPCD.addRow(new Object []{true, "RESEARCH FEE  ASSIGNMENT", Calendar.getInstance().getTime(), amount.divide(amount1), null, null, null, null, "Payment", null, null});
																		modelPCD.addRow(new Object []{true, "ENTRY FEE DEED OF ASSIGNMENT", FncGlobal.getDateToday(), new BigDecimal(30.00), "Fixed Amt", null, null, null, "Payment", null, null});
																		modelPCD.addRow(new Object []{true, "JUDICIAL FORM FEE", FncGlobal.getDateToday(), new BigDecimal(30.00), "DUE TO RD COMPUTERIZATION (originally encoded as it fee, revised by sir alan 7/26/10)", null, null, null, "Payment", null, null});
																		modelPCD.addRow(new Object []{true, "REGISTRATION FEE OF ASSIGNMENT", FncGlobal.getDateToday(), amount, "Refer to RD Table", null, null, null, "Payment", null, null});
																		modelPCD.addRow(new Object []{true, "RESEARCH FEE  ASSIGNMENT", FncGlobal.getDateToday(), amount.divide(amount1), null, null, null, null, "Payment", null, null});
																	} else {
																		if (data[3] == null) {
																			//																			modelPCD.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], "", null, null, null, "Payment", null, null});
																			modelPCD.addRow(new Object []{true, data[1], FncGlobal.getDateToday(), data[2], "", null, null, null, "Payment", null, null});
																		} else {
																			if (data[3].equals("Fixed Amt")) {
																				//modelPCD.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, null});
																				modelPCD.addRow(new Object []{true, data[1], FncGlobal.getDateToday(), data[2], data[3], null, null, null, "Payment", null, null});
																			} else {
																				//modelPCD.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, null});
																				modelPCD.addRow(new Object []{true, data[1], FncGlobal.getDateToday(), data[2], data[3], null, null, null, "Payment", null, null});
																			}
																		}
																	}
																	if (lookupCode.getValue().equals("215") || lookupCode.getValue().equals("216")) {
																		btnOR.setEnabled(true);
																	} else {
																		btnOR.setEnabled(false);
																	}
																	rowHeaderPCD.setModel(new DefaultListModel());

																	for(int y =1; y<=modelPCD.getRowCount(); y++){
																		((DefaultListModel) rowHeaderPCD.getModel()).addElement(y);
																	}

																	scrollPCD.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD.getRowCount())));
																	tblPCD.packAll();
																	if (tblPCD.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
																		tblPCD.getColumnModel().getColumn(1).setPreferredWidth(200);
																	}

																	btnDRF.setEnabled(true);
																	//txtAmount.setEditable(true);
																	lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
																	txtRemarks.setEditable(true);
																	computeTotal();

																	if (UserInfo.EmployeeCode.equals("900705") || UserInfo.EmployeeCode.equals("900933") || UserInfo.EmployeeCode.equals("900841") || UserInfo.EmployeeCode.equals("900876")) {
																		//txtAmount.setEditable(true);
																	}

																}

																KEYBOARD_MANAGER.focusNextComponent();
															}
														}
													});
													{
														lblCode = new JLabel("[ ]");
														pnlLookup.add(lblCode,BorderLayout.CENTER);
														lblCode.setHorizontalAlignment(JLabel.LEFT);
													}
												}
												{
													cmbType = new JComboBox(new DefaultComboBoxModel(getClass2()));
													pnlText.add(cmbType, BorderLayout.WEST);

												}
												{
													dateSched = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
													pnlText.add(dateSched);
													dateSched.setDate(null);
													//dateSched.setEditable(false);
													dateSched.setEnabled(false);
													dateSched.setDateFormatString("yyyy-MM-dd");
													((JTextFieldDateEditor)dateSched.getDateEditor()).setEditable(false);
													//dateSched.setDate(Calendar.getInstance().getTime());
													dateSched.setDate(FncGlobal.getDateToday());
													dateSched.addDateListener(new DateListener() {
														public void datePerformed(DateEvent event) {
															int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
															modelPCD.setValueAt(dateSched.getDate(), row, 2);

														}
													});
												}
												{
													txtAmount = new _JXFormattedTextField("0.00");
													pnlText.add(txtAmount);
													txtAmount.setHorizontalAlignment(JLabel.RIGHT);
													txtAmount.setBounds(215, 10, 280, 22);
													txtAmount.setEditable(false);
													//txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtAmount.addKeyListener(new KeyAdapter() {
														public void keyReleased(KeyEvent arg0) {
															BigDecimal value1 = new BigDecimal (lblSetupAmount1.getText());
															BigDecimal value2 = new BigDecimal (lblRunningTotal1.getText());

															int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());

															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){

																if(tblPCD.getSelectedRows().length > 0){
																	lblBalanceAmount.setText(String.format("%s", value1.subtract(value2)));

																	modelPCD.setValueAt(value2, row, 3);
																	computeTotal();
																}

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
															pnlReqBy.add(lblReqBy1,BorderLayout.CENTER);
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
								pnlAmount.setPreferredSize(new Dimension(450, 80));
								{
									JPanel pnlLabelTotal1 = new JPanel(new GridLayout(4, 2, 3, 3));
									pnlAmount.add(pnlLabelTotal1, BorderLayout.WEST);
									{
										JLabel lblSetupAmount2 = new JLabel("                     ");
										pnlLabelTotal1.add(lblSetupAmount2, BorderLayout.WEST);
									}
								}
								{
									JPanel pnlLabelTotal = new JPanel(new GridLayout(3, 2, 3, 3));
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
					tabCenter.addTab("Buyer Cost - Batch", null, pnlBuyerBatch, null); //XXX Buyer Cost - Batch
					pnlBuyerBatch.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

					//--NORTH
					{

					}

					{
						JPanel pnlChkBox = new JPanel(new BorderLayout(5,5));
						pnlBuyerBatch.add(pnlChkBox, BorderLayout.NORTH);
						pnlChkBox.setPreferredSize(new Dimension(0, 20));
						{
							/*ADDED BY JED 2021-08-31 : TO FILTER THE TABLE ACCORDING TO THEIR OLD CLIENTS*/
							chkOld = new JCheckBox("Show Old Clients");
							pnlChkBox.add(chkOld, BorderLayout.WEST);
							chkOld.setEnabled(false);
							chkOld.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent arg0) {
									System.out.println("checkboxlistener");
									String pcostid_dl = lookupCode_Batch.getText();
									int type = cmbType_Batch.getSelectedIndex();
									if(chkOld.isSelected()) {
										displayOldClients(modelPCD_Batch, rowHeaderPCD_Batch, pcostid_dl, type);
									}else {
										displayAvailablePCostClients(modelPCD_Batch, rowHeaderPCD_Batch, pcostid_dl, type);
									}
								}
							});	
						} 
						{
							JPanel pnlcenter = new JPanel(new BorderLayout(5, 5));
							pnlChkBox.add(pnlcenter, BorderLayout.CENTER);
							{
								chkcancellled = new JCheckBox("Show Cancelled Clients");
								pnlcenter.add(chkcancellled, BorderLayout.WEST);
								chkcancellled.setEnabled(false);
								chkcancellled.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										if (chkcancellled.isSelected()){
											
										}else {
											
										}
									}
								});
							}
							
							
						}
					}
					{
						pnlCenter = new JPanel();
						pnlBuyerBatch.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details"));
						pnlCenter.setPreferredSize(new Dimension(800, 340));
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
								pnlSearch.setPreferredSize(new Dimension(450, 90));
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
										lblType = new JLabel("Type");
										pnlLabel.add(lblType);
										lblType.setHorizontalAlignment(JLabel.RIGHT);

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

									JPanel pnlText = new JPanel(new GridLayout(6, 0, 5, 5));
									pnlInfo.add(pnlText, BorderLayout.CENTER);
									{
										{
											cmbType_Batch = new JComboBox(new DefaultComboBoxModel(getType()));
											pnlText.add(cmbType_Batch, BorderLayout.WEST);
											cmbType_Batch.addItemListener(new ItemListener() {
												public void itemStateChanged(ItemEvent e) {
													int state = e.getStateChange();
													if(state == ItemEvent.SELECTED){
														lookupCode_Batch.setEnabled(true);
														lookupCode_Batch.setValue(null);
														lblCode_Batch.setText("[ ]");
													}
												}
											});
											//											cmbType_Batch.addActionListener(new ActionListener() {
											//												public void actionPerformed(ActionEvent e) {
											//													int row = tblPCD_Batch.convertRowIndexToModel(tblPCD_Batch.getSelectedRow());
											//													modelPCD_Batch.setValueAt(cmbType_Batch.getSelectedItem(), row, 8);
											//													modelPCD_Batch.setValueAt(batch_no, row, 10);
											//												}
											//											});
										}
										{
											JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
											pnlText.add(pnlLookup);
											{
												{
													lookupCode_Batch = new _JLookup(null, "Code");
													pnlLookup.add(lookupCode_Batch, BorderLayout.WEST);
													lookupCode_Batch.setPreferredSize(new Dimension(50, 20));
													lookupCode_Batch.setReturnColumn(0);
													lookupCode_Batch.setEnabled(false);
													lookupCode_Batch.setFilterName(true);
													lookupCode_Batch.setLookupSQL(PCOST_BuyerBatch());
													lookupCode_Batch.addActionListener(this);
													lookupCode_Batch.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															String emp_code = (String) UserInfo.EmployeeCode;
															String emp_name = (String) UserInfo.FullName;
															String pcostid_dl = lookupCode_Batch.getValue();
															int type = cmbType_Batch.getSelectedIndex();
															if (data != null) {
																//displayPCostClient();

																/*ADDED BY JED 2021-08-31 : TO FILTER THE TABLE ACCORDING TO THEIR OLD CLIENTS*/
																if(chkOld.isSelected()) {
																	displayOldClients(modelPCD_Batch, rowHeaderPCD_Batch, pcostid_dl, type);
																}else {
																	//---added by JED : DCRF no. 797 : DCRF no. 797 for control of tagging particulars to avoid double tagging---//
																	displayAvailablePCostClients(modelPCD_Batch, rowHeaderPCD_Batch, pcostid_dl, type);
																}

																FncSystem.out("SQL for lookup of Code Batch", lookupCode_Batch.getLookupSQL());

																lblCode_Batch.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
																lblSetupAmount1_Batch.setText(String.format("%s", data[2]));
																lblBalanceAmount_Batch.setText(String.format("%s", lblSetupAmount1_Batch.getText()));
																txtRemarks_Batch.setText(String.format("%s", data[3]));
																//txtAmount_Batch.setText(String.format("%s", data[2]));
																txtAmount_Batch.setValue((BigDecimal)data[2]);
																txtRequestedBy_Batch.setText(emp_code);
																lblReqBy1_Batch.setText(String.format("[ %s ]", emp_name));

																chkOld.setEnabled(true);
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
													dateSched_Batch = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
													pnlText.add(dateSched_Batch);
													dateSched_Batch.setDate(null);
													dateSched_Batch.setEnabled(false);
													dateSched_Batch.setDateFormatString("yyyy-MM-dd");
													((JTextFieldDateEditor)dateSched_Batch.getDateEditor()).setEditable(false);
													//dateSched_Batch.setDate(Calendar.getInstance().getTime());
													dateSched_Batch.setDate(FncGlobal.getDateToday());
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

																//modelPCD_Batch.setValueAt(value2, row, 3);
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
								pnlAmount.setPreferredSize(new Dimension(450, 80));
								{
									JPanel pnlLabelTotal1 = new JPanel(new GridLayout(4, 2, 3, 3));
									pnlAmount.add(pnlLabelTotal1, BorderLayout.WEST);
									{
										JLabel lblSetupAmount2 = new JLabel("                     ");
										pnlLabelTotal1.add(lblSetupAmount2, BorderLayout.WEST);
									}
								}
								{
									JPanel pnlLabelTotal = new JPanel(new GridLayout(3, 2, 3, 3));
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
				{
					pnlBlockLot = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Block-Lot Related Cost", null, pnlBlockLot, null); //XXX Block-Lot Related Cost
					pnlBlockLot.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						pnlCenter1 = new JPanel();
						pnlBlockLot.add(pnlCenter1, BorderLayout.CENTER);
						pnlCenter1.setLayout(null);
						pnlCenter1.setBorder(lineBorder);
						pnlCenter1.setPreferredSize(new Dimension(700, 50));
						{
							{
								lblCenter = new JLabel("Project");
								pnlCenter1.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(10, 10, 120, 22);
							}
							{
								lookupProject = new _JLookup(null, "Project");
								pnlCenter1.add(lookupProject);
								lookupProject.setReturnColumn(0);
								lookupProject.setLookupSQL(SQL_PROJECT());
								lookupProject.setBounds(70, 10, 100, 22);
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];

											lblProject2.setText(String.format("[ %s ]", proj_name));
											lookupUnit.setLookupSQL(SQL_UNIT(proj_id));

											FncSystem.out("Lookup SQL for all Units Block Lot Related", SQL_UNIT(proj_id));

											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								lblProject2 = new JLabel("[ ]");
								pnlCenter1.add(lblProject2);
								lblProject2.setHorizontalAlignment(JLabel.LEFT);
								lblProject2.setBounds(185, 10, 300, 22);
							}
							{
								lblCenter = new JLabel("Unit");
								pnlCenter1.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(10, 35, 120, 22);
							}
							{
								lookupUnit = new _JLookup(null, "Unit");
								pnlCenter1.add(lookupUnit);
								lookupUnit.setReturnColumn(0);
								lookupUnit.setBounds(70, 35, 100, 22);
								lookupUnit.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											lblUnit.setText(String.format("[ %s ]", (String) data[1]));
											lblBuyer.setText(String.format("[ %s ]", (String) data[2]));
											//lblBatch.setText(String.format("<html><b>[ %s ]</html>", (String) data[3]));
											entity2 = (String) data[3];
											//unitid = (String) data[5];
											seqno = (Integer) data[5];
											String pbl_id = (String) data[0];

											displayPCostDetails();

											KEYBOARD_MANAGER.focusNextComponent();

											lookupCode_BlockLot.setEnabled(true);
											lookupCode_BlockLot.setLookupSQL(SQL_PCOST_BLOCK(pbl_id));
										}
									}
								});
							}
							{
								lblUnit = new JLabel("[ ]");
								pnlCenter1.add(lblUnit);
								lblUnit.setHorizontalAlignment(JLabel.LEFT);
								lblUnit.setBounds(185, 35, 300, 22);
							}
							{
								lblCenter = new JLabel("Buyer (if any)");
								pnlCenter1.add(lblCenter);
								lblCenter.setHorizontalAlignment(JLabel.LEFT);
								lblCenter.setBounds(10, 58, 120, 22);
							}
							{
								lblBuyer = new JLabel("[ ]");
								pnlCenter1.add(lblBuyer);
								lblBuyer.setHorizontalAlignment(JLabel.LEFT);
								lblBuyer.setBounds(100, 58, 300, 22);
							}
						}
					}
					{
						pnlSouth = new JPanel();
						pnlBlockLot.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setLayout(new BorderLayout(5, 5));
						pnlSouth.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details"));
						pnlSouth.setPreferredSize(new Dimension(800, 335));
						{
							pnlTable = new JPanel(new BorderLayout(5, 5));
							pnlSouth.add(pnlTable, BorderLayout.WEST);
							pnlTable.setPreferredSize(new Dimension(460, 200));
							pnlTable.setBorder(lineBorder);
							{
								scrollPCD_BlockLot = new _JScrollPaneMain();
								pnlTable.add(scrollPCD_BlockLot, BorderLayout.CENTER);
								modelPCD_BlockLot = new model_pcost();
								modelPCD_BlockLot.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowHeaderPCD_BlockLot.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowHeaderPCD_BlockLot.getModel()).addElement(modelPCD_BlockLot.getRowCount());
										}
									}
								});

								tblPCD_BlockLot = new _JTableMain(modelPCD_BlockLot);
								tblPCD_BlockLot.packAll();
								tblPCD_BlockLot.setAlignmentX(LEFT_ALIGNMENT);
								scrollPCD_BlockLot.setViewportView(tblPCD_BlockLot);

								tblPCD_BlockLot.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent arg0) {
										try {
											if(!arg0.getValueIsAdjusting()){

												String batch = (String) modelPCD_BlockLot.getValueAt(tblPCD_BlockLot.getSelectedRow(), 11);
												String cost_desc = (String) modelPCD_BlockLot.getValueAt(tblPCD_BlockLot.getSelectedRow(), 1);
												String pbl_id = lookupUnit.getValue();
												displayProcessingCostDetails(pbl_id, cost_desc, batch);
												//displayProcessingCostDetails1(cost_desc);
												Boolean isSelected = (Boolean) modelPCD_BlockLot.getValueAt(tblPCD_BlockLot.getSelectedRow(), 0);

												if(tblPCD_BlockLot.getSelectedRows().length > 0){

													if (isSelected){
														txtAmount_BlockLot.setEditable(true);
													}else{
														txtAmount_BlockLot.setEditable(false);
													}
												}
											}
										} catch (ArrayIndexOutOfBoundsException e) { }
									}
								});
								tblPCD_BlockLot.putClientProperty("terminateEditOnFocusLost", true);
								{
									rowHeaderPCD_BlockLot = tblPCD_BlockLot.getRowHeader();
									rowHeaderPCD_BlockLot.setModel(new DefaultListModel());
									scrollPCD_BlockLot.setRowHeaderView(rowHeaderPCD_BlockLot);
									scrollPCD_BlockLot.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}

							}
							{
								scrollPCDTotal_BlockLot = new _JScrollPaneTotal(scrollPCD_BlockLot);
								pnlTable.add(scrollPCDTotal_BlockLot, BorderLayout.SOUTH);
								{
									modelPCDTotal_BlockLot = new model_pcost();
									modelPCDTotal_BlockLot.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00), null, null, null, null, null, null, null });

									tblPCDTotal_BlockLot = new _JTableTotal(modelPCDTotal_BlockLot, tblPCD_BlockLot);
									scrollPCDTotal_BlockLot.setViewportView(tblPCDTotal_BlockLot);

									tblPCDTotal_BlockLot.setTotalLabel(2);
								}
								scrollPCDTotal_BlockLot.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation_BlockLot());
							}
						}
						{
							pnlDetail = new JPanel(new BorderLayout(5, 5));
							pnlSouth.add(pnlDetail, BorderLayout.EAST);
							pnlDetail.setPreferredSize(new Dimension(400, 335));
							{
								pnlInfo = new JPanel(new BorderLayout(5, 5));
								pnlDetail.add(pnlInfo, BorderLayout.NORTH);
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
													lookupCode_BlockLot = new _JLookup(null, "Code");
													pnlLookup.add(lookupCode_BlockLot, BorderLayout.WEST);
													lookupCode_BlockLot.setPreferredSize(new Dimension(50, 20));
													lookupCode_BlockLot.setEnabled(false);
													lookupCode_BlockLot.setReturnColumn(0);
													lookupCode_BlockLot.setLookupSQL(SQL_PCOST(entity));
													lookupCode_BlockLot.addActionListener(this);
													lookupCode_BlockLot.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															String code = (String) data[0];
															String emp_code = (String) UserInfo.EmployeeCode;
															String emp_name = (String) UserInfo.FullName;
															if (data != null) {

																FncSystem.out("Display SQL for lookup", lookupCode_BlockLot.getLookupSQL());

																lblCode_BlockLot.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
																lblSetupAmount1_BlockLot.setText(String.format("%s", data[2]));
																lblBalanceAmount_BlockLot.setText(String.format("%s", lblSetupAmount1_BlockLot.getText()));
																txtAmount_BlockLot.setValue(data[2]);
																txtRequestedBy_BlockLot.setText(emp_code);
																lblReqBy1_BlockLot.setText(String.format("[ %s ]", emp_name));
																if (data[3] != null){
																	txtRemarks_BlockLot.setText(String.format("%s", data[3]));
																} else {
																	txtRemarks_BlockLot.setText(" ");
																}


																if(data != null){
																	//BigDecimal amount = new BigDecimal (String.format("%s", reg_amount1(lookupUnit.getValue())));
																	BigDecimal amount = new BigDecimal (String.format("%s", reg_amount3(lookupUnit.getValue(), lookupProject.getValue())));
																	BigDecimal amount1 = new BigDecimal (String.format("100.00"));
																	if (lookupCode_BlockLot.getValue().equals("109") || lookupCode_BlockLot.getValue().equals("210") || lookupCode_BlockLot.getValue().equals("105") || lookupCode_BlockLot.getValue().equals("108")){
																		modelPCD_BlockLot.addRow(new Object []{true, "ENTRY FEE DEED OF ASSIGNMENT", Calendar.getInstance().getTime(), new BigDecimal(30.00), "Fixed Amt", null, null, null, "Payment", null, batch_no});
																		modelPCD_BlockLot.addRow(new Object []{true, "JUDICIAL FORM FEE", Calendar.getInstance().getTime(), new BigDecimal(30.00), "DUE TO RD COMPUTERIZATION (originally encoded as it fee, revised by sir alan 7/26/10)", null, null, null, "Payment", null, batch_no});
																		modelPCD_BlockLot.addRow(new Object []{true, "REGISTRATION FEE OF ASSIGNMENT", Calendar.getInstance().getTime(), amount, "Refer to RD Table", null, null, null, "Payment", null, batch_no});
																		modelPCD_BlockLot.addRow(new Object []{true, "RESEARCH FEE  ASSIGNMENT", Calendar.getInstance().getTime(), amount.divide(amount1), null, null, null, null, "Payment", null, batch_no});
																	} else {
																		if (data[3] == null) {
																			modelPCD_BlockLot.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], "", null, null, null, "Payment", null, batch_no});
																		} else {
																			if (data[3].equals("Fixed Amt")) {
																				modelPCD_BlockLot.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, batch_no});
																			} else {
																				modelPCD_BlockLot.addRow(new Object []{true, data[1], Calendar.getInstance().getTime(), data[2], data[3], null, null, null, "Payment", null, batch_no});
																			}
																		}
																	}
																	rowHeaderPCD_BlockLot.setModel(new DefaultListModel());

																	for(int y =1; y<=modelPCD_BlockLot.getRowCount(); y++){
																		((DefaultListModel) rowHeaderPCD_BlockLot.getModel()).addElement(y);
																	}

																	scrollPCD_BlockLot.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_BlockLot.getRowCount())));
																	tblPCD_BlockLot.packAll();
																	if (tblPCD_BlockLot.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
																		tblPCD_BlockLot.getColumnModel().getColumn(1).setPreferredWidth(200);
																	}

																	btnDRF.setEnabled(true);
																	//txtAmount_BlockLot.setEditable(true);
																	txtRemarks_BlockLot.setEditable(true);

																	lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));

																}

																if(tabCenter.getSelectedIndex() == 2 || tabCenter.getSelectedIndex() == 3) {
																	if(code.equals("215") || code.equals("216")) {
																		btnOR.setEnabled(true);
																	}
																}

																KEYBOARD_MANAGER.focusNextComponent();
															}
														}
													});
													{
														lblCode_BlockLot = new JLabel("[ ]");
														pnlLookup.add(lblCode_BlockLot,BorderLayout.CENTER);
														lblCode_BlockLot.setHorizontalAlignment(JLabel.LEFT);
													}
												}
												{
													cmbType_BlockLot = new JComboBox(new DefaultComboBoxModel(getClass2()));
													pnlText.add(cmbType_BlockLot, BorderLayout.WEST);
													/*cmbType_BlockLot.addActionListener(new ActionListener() {
														public void actionPerformed(ActionEvent e) {
															int row = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
															modelPCD_BlockLot.setValueAt(cmbType_BlockLot.getSelectedItem(), row, 8);
															modelPCD_BlockLot.setValueAt(batch_no, row, 10);
														}
													});*/
												}
												{
													dateSched_BlockLot = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
													pnlText.add(dateSched_BlockLot);
													dateSched_BlockLot.setDate(null);
													dateSched_BlockLot.setEditable(false);
													dateSched_BlockLot.setDateFormatString("yyyy-MM-dd");
													((JTextFieldDateEditor)dateSched_BlockLot.getDateEditor()).setEditable(false);
													dateSched_BlockLot.setDate(Calendar.getInstance().getTime());
													dateSched_BlockLot.addDateListener(new DateListener() {
														public void datePerformed(DateEvent event) {
															int row = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
															modelPCD_BlockLot.setValueAt(sdf.format(dateSched_BlockLot.getDate()), row, 2);

														}
													});
												}
												{
													txtAmount_BlockLot = new _JXFormattedTextField("0.00");
													pnlText.add(txtAmount_BlockLot);
													txtAmount_BlockLot.setEditable(false);
													txtAmount_BlockLot.setHorizontalAlignment(JLabel.RIGHT);
													txtAmount_BlockLot.setBounds(215, 10, 280, 22);
													//txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtAmount_BlockLot.addKeyListener(new KeyAdapter() {
														public void keyReleased(KeyEvent arg0) {
															BigDecimal value1 = new BigDecimal (lblSetupAmount1_BlockLot.getText());
															BigDecimal value2 = new BigDecimal (lblRunningTotal1_BlockLot.getText());

															int row = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());

															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
																lblBalanceAmount_BlockLot.setText(String.format("%s", value1.subtract(value2)));

																modelPCD_BlockLot.setValueAt(value2, row, 3);
																computeTotal();
															}
														}
													});
													txtAmount_BlockLot.getDocument().addDocumentListener(new DocumentListener() {
														public void insertUpdate(DocumentEvent e) {
															lblRunningTotal1_BlockLot.setText(txtAmount_BlockLot.getText());
														}
														public void removeUpdate(DocumentEvent e) {
															lblRunningTotal1_BlockLot.setText(txtAmount_BlockLot.getText());
														}
														public void changedUpdate(DocumentEvent e) {

														}
													});
												}

												JPanel pnlReqBy = new JPanel(new BorderLayout(3, 3));
												pnlText.add(pnlReqBy);
												{
													{
														txtRequestedBy_BlockLot = new JXTextField();
														pnlReqBy.add(txtRequestedBy_BlockLot, BorderLayout.WEST);
														txtRequestedBy_BlockLot.setEditable(false);
														txtRequestedBy_BlockLot.setPreferredSize(new Dimension(100, 20));
														{
															lblReqBy1_BlockLot = new JLabel("[ ]");
															pnlReqBy.add(lblReqBy1_BlockLot,BorderLayout.CENTER);
															lblReqBy1_BlockLot.setHorizontalAlignment(JLabel.LEFT);
														}
													}
												}
												{
													txtRemarks_BlockLot = new JXTextField();
													pnlText.add(txtRemarks_BlockLot);
													txtRemarks_BlockLot.setHorizontalAlignment(JLabel.LEFT);
													txtRemarks_BlockLot.setBounds(215, 10, 280, 22);
													txtRemarks_BlockLot.setEditable(false);
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
								pnlAmount.setPreferredSize(new Dimension(450, 80));
								{
									JPanel pnlLabelTotal1 = new JPanel(new GridLayout(4, 2, 3, 3));
									pnlAmount.add(pnlLabelTotal1, BorderLayout.WEST);
									{
										JLabel lblSetupAmount2 = new JLabel("                     ");
										pnlLabelTotal1.add(lblSetupAmount2, BorderLayout.WEST);
									}
								}
								{
									JPanel pnlLabelTotal = new JPanel(new GridLayout(3, 2, 3, 3));
									pnlAmount.add(pnlLabelTotal, BorderLayout.CENTER);
									{
										JLabel lblSetupAmount = new JLabel("Setup Amount :");
										pnlLabelTotal.add(lblSetupAmount);
										{
											lblSetupAmount1_BlockLot = new JLabel("0.00");
											pnlLabelTotal.add(lblSetupAmount1_BlockLot);
										}
									}
									{
										JLabel lblRunningTotal = new JLabel("Running Total  :");
										pnlLabelTotal.add(lblRunningTotal);
										{
											lblRunningTotal1_BlockLot = new JLabel("0.00");
											pnlLabelTotal.add(lblRunningTotal1_BlockLot);
										}
									}
									{
										JLabel lblBalance = new JLabel("Balance +/(-)   :");
										pnlLabelTotal.add(lblBalance);
										{
											lblBalanceAmount_BlockLot = new JLabel("0.00");
											pnlLabelTotal.add(lblBalanceAmount_BlockLot);
										}
									}
								}
							}
						}
					}
				}
				{
					pnlBlockBatch = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Block-Lot Related Cost - Batch", null, pnlBlockBatch, null); //XXX Block-Lot Related Cost - Batch
					pnlBlockBatch.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						pnlBlockCenter = new JPanel();
						pnlBlockBatch.add(pnlBlockCenter, BorderLayout.CENTER);
						pnlBlockCenter.setLayout(new BorderLayout(5, 5));
						pnlBlockCenter.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details(Block-Lot)"));
						pnlBlockCenter.setPreferredSize(new Dimension(800, 340));
						{
							pnlBlockTable = new JPanel(new BorderLayout(5, 5));
							pnlBlockCenter.add(pnlBlockTable, BorderLayout.WEST);
							pnlBlockTable.setPreferredSize(new Dimension(460, 200));
							pnlBlockTable.setBorder(lineBorder);
							{
								scrollPCD_Block_Batch = new JScrollPane();
								pnlBlockTable.add(scrollPCD_Block_Batch, BorderLayout.CENTER);
								scrollPCD_Block_Batch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
								{
									modelPCD_Block_Batch = new model_Block_client();
									modelPCD_Block_Batch.addTableModelListener(new TableModelListener() {

										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderPCD_Block_Batch.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowHeaderPCD_Block_Batch.getModel()).addElement(modelPCD_Block_Batch.getRowCount());
											}
										}
									});

									tblPCD_Block_Batch = new _JTableMain(modelPCD_Block_Batch);
									tblPCD_Block_Batch.addMouseListener(this);
									scrollPCD_Block_Batch.setViewportView(tblPCD_Block_Batch);

									//tblPCD_Block_Batch.hideColumns("Client Name");
									tblPCD_Block_Batch.setHorizontalScrollEnabled(true);

								}
								{
									rowHeaderPCD_Block_Batch = tblPCD_Block_Batch.getRowHeader();
									rowHeaderPCD_Block_Batch.setModel(new DefaultListModel());
									scrollPCD_Block_Batch.setRowHeaderView(rowHeaderPCD_Block_Batch);
									scrollPCD_Block_Batch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							pnlBlockDetail = new JPanel(new BorderLayout(5, 5));
							pnlBlockCenter.add(pnlBlockDetail, BorderLayout.EAST);
							pnlBlockDetail.setPreferredSize(new Dimension(400, 335));
							{
								pnlBlockSearch = new JPanel(new BorderLayout(5, 5));
								pnlBlockDetail.add(pnlBlockSearch, BorderLayout.NORTH);
								pnlBlockSearch.setBorder(JTBorderFactory.createTitleBorder("Search Client"));
								pnlBlockSearch.setPreferredSize(new Dimension(450, 95));
								{
									JPanel pnlUploading = new JPanel(new GridLayout(2, 1, 10, 0));
									pnlBlockSearch.add(pnlUploading, BorderLayout.WEST);
									{
										txtBlockSearch = new JXTextField();
										pnlUploading.add(txtBlockSearch);
										txtBlockSearch.setPreferredSize(new java.awt.Dimension(383, 0));
										txtBlockSearch.setHorizontalAlignment(JTextField.CENTER);	
										txtBlockSearch.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent e) {	
												checkAllUnitList();
											}				 
										});
									}
									{
										JLabel lblDateTo = new JLabel("*Search Unit");
										pnlUploading.add(lblDateTo);
									}
								}
							}
							{
								pnlBlockInfo = new JPanel(new BorderLayout(5, 5));
								pnlBlockDetail.add(pnlBlockInfo, BorderLayout.CENTER);
								pnlBlockInfo.setBorder(JTBorderFactory.createTitleBorder("Detail INFO"));
								pnlBlockInfo.setPreferredSize(new Dimension(400, 205));
								{
									JPanel pnlLabel = new JPanel(new GridLayout(6, 0, 5, 5));
									pnlBlockInfo.add(pnlLabel, BorderLayout.WEST);
									{
										lblBlockType = new JLabel("Type");
										pnlLabel.add(lblBlockType);
										lblBlockType.setHorizontalAlignment(JLabel.RIGHT);

										lblBlockInfo = new JLabel("Code");
										pnlLabel.add(lblBlockInfo);
										lblBlockInfo.setHorizontalAlignment(JLabel.RIGHT);

										lblBlockDate = new JLabel("Date");
										pnlLabel.add(lblBlockDate);
										lblBlockDate.setHorizontalAlignment(JLabel.RIGHT);

										lblBlockAmount = new JLabel("Amount");
										pnlLabel.add(lblBlockAmount);
										lblBlockAmount.setHorizontalAlignment(JLabel.RIGHT);


										lblBlockReqBy = new JLabel("Request By");
										pnlLabel.add(lblBlockReqBy);
										lblBlockReqBy.setHorizontalAlignment(JLabel.RIGHT);

										lblBlockRemarks = new JLabel("Remarks");
										pnlLabel.add(lblBlockRemarks);
										lblBlockRemarks.setHorizontalAlignment(JLabel.RIGHT);
									}

									JPanel pnlText = new JPanel(new GridLayout(6, 0, 5, 5));
									pnlBlockInfo.add(pnlText, BorderLayout.CENTER);
									{
										{
											cmbType_Block_Batch = new JComboBox(new DefaultComboBoxModel(getType()));
											pnlText.add(cmbType_Block_Batch, BorderLayout.WEST);
											cmbType_Block_Batch.addItemListener(new ItemListener() {
												public void itemStateChanged(ItemEvent e) {
													int state = e.getStateChange();
													if(state == ItemEvent.SELECTED){
														lookupCode_Block_Batch.setEnabled(true);
														lookupCode_Block_Batch.setValue(null);
														lblCode_Block_Batch.setText("[ ]");
													}
												}
											});
											//											cmbType_Batch.addActionListener(new ActionListener() {
											//												public void actionPerformed(ActionEvent e) {
											//													int row = tblPCD_Batch.convertRowIndexToModel(tblPCD_Batch.getSelectedRow());
											//													modelPCD_Batch.setValueAt(cmbType_Batch.getSelectedItem(), row, 8);
											//													modelPCD_Batch.setValueAt(batch_no, row, 10);
											//												}
											//											});
										}
										{
											JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
											pnlText.add(pnlLookup);
											{
												{
													lookupCode_Block_Batch = new _JLookup(null, "Code");
													pnlLookup.add(lookupCode_Block_Batch, BorderLayout.WEST);
													lookupCode_Block_Batch.setPreferredSize(new Dimension(50, 20));
													lookupCode_Block_Batch.setReturnColumn(0);
													lookupCode_Block_Batch.setEnabled(false);
													lookupCode_Block_Batch.setFilterName(true);
													lookupCode_Block_Batch.setLookupSQL(PCOST_BlockBatch());
													lookupCode_Block_Batch.addActionListener(this);
													lookupCode_Block_Batch.addLookupListener(new LookupListener() {
														public void lookupPerformed(LookupEvent event) {
															Object[] data = ((_JLookup) event.getSource()).getDataSet();
															String code = (String) data[0];
															String emp_code = (String) UserInfo.EmployeeCode;
															String emp_name = (String) UserInfo.FullName;
															String pcostid_dl = lookupCode_Block_Batch.getValue();
															int type = cmbType_Block_Batch.getSelectedIndex();
															if (data != null) {

																displayAvailablePCostClients(modelPCD_Block_Batch, rowHeaderPCD_Block_Batch, pcostid_dl, type);

																FncSystem.out("SQL for lookup of Code Batch", lookupCode_Block_Batch.getLookupSQL());

																lblCode_Block_Batch.setText(String.format("<html><font size = 2>[ %s ]</html>", (String) data[1]));
																lblSetupAmount1_Block_Batch.setText(String.format("%s", data[2]));
																lblBalanceAmount_Block_Batch.setText(String.format("%s", lblSetupAmount1_Block_Batch.getText()));
																txtRemarks_Block_Batch.setText(String.format("%s", data[3]));
																//txtAmount_Batch.setText(String.format("%s", data[2]));
																txtAmount_Block_Batch.setValue((BigDecimal)data[2]);
																txtRequestedBy_Block_Batch.setText(emp_code);
																lblReqBy1_Block_Batch.setText(String.format("[ %s ]", emp_name));

																btnDRF.setEnabled(false);
																btnTSave.setEnabled(false);
																txtRemarks_Block_Batch.setEditable(true);
																String batch_no = lblBatch.getText().replace("<html><b>Batch No:</html>", "");
																System.out.println("Index3 batch_no: "+batch_no);
																

																txtAmount_Block_Batch.setEditable(true);

																//COMMENTED DCRF 2032
																/*if (lookupCode_Block_Batch.getValue().equals("215") || lookupCode_Block_Batch.getValue().equals("216")) {
																	btnOR.setEnabled(true);
																	btnSave.setEnabled(false);
																} else {*/
																btnOR.setEnabled(false);
																btnSave.setEnabled(true);
																//}

																KEYBOARD_MANAGER.focusNextComponent();

																if(tabCenter.getSelectedIndex() == 2 || tabCenter.getSelectedIndex() == 3) {
																	if(code.equals("215") || code.equals("216")) {
																		btnOR.setEnabled(true);
																	}
																}
															}
														}
													});
													{
														lblCode_Block_Batch = new JLabel("[ ]");
														pnlLookup.add(lblCode_Block_Batch,BorderLayout.CENTER);
														lblCode_Block_Batch.setHorizontalAlignment(JLabel.LEFT);
													}
												}
												{
													dateSched_Block_Batch = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
													pnlText.add(dateSched_Block_Batch);
													dateSched_Block_Batch.setDate(null);
													dateSched_Block_Batch.setEnabled(false);
													dateSched_Block_Batch.setDateFormatString("yyyy-MM-dd");
													((JTextFieldDateEditor)dateSched_Block_Batch.getDateEditor()).setEditable(false);
													dateSched_Block_Batch.setDate(Calendar.getInstance().getTime());
													dateSched_Block_Batch.addDateListener(new DateListener() {
														public void datePerformed(DateEvent event) {
															int row = tblPCD_Block_Batch.convertRowIndexToModel(tblPCD_Block_Batch.getSelectedRow());
															modelPCD_Block_Batch.setValueAt(dateSched_Block_Batch.getDate(), row, 2);

														}
													});
												}
												{
													txtAmount_Block_Batch = new _JXFormattedTextField("0.00");
													pnlText.add(txtAmount_Block_Batch);
													txtAmount_Block_Batch.setHorizontalAlignment(JLabel.RIGHT);
													txtAmount_Block_Batch.setBounds(215, 10, 280, 22);
													txtAmount_Block_Batch.setEditable(false);
													txtAmount_Block_Batch.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													//txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtAmount_Block_Batch.addKeyListener(new KeyAdapter() {
														public void keyReleased(KeyEvent arg0) {
															BigDecimal value1 = new BigDecimal (lblSetupAmount1_Block_Batch.getText());
															BigDecimal value2 = new BigDecimal (txtAmount_Block_Batch.getValue().toString());
															
															//Comment by Erick because it is not used.
															//int row = tblPCD_Block_Batch.convertRowIndexToModel(tblPCD_Block_Batch.getSelectedRow());

															//int row = tblPCD_Block_Batch.convertRowIndexToModel(tblPCD_Block_Batch.getSelectedRow());

															if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
																lblBalanceAmount_Block_Batch.setText(String.format("%s", value1.subtract(value2)));

																//modelPCD_Block_Batch.setValueAt(value2, row, 3);
																computeTotal();
															}
														}
													});
													txtAmount_Block_Batch.getDocument().addDocumentListener(new DocumentListener() {
														public void insertUpdate(DocumentEvent e) {
															lblRunningTotal1_Block_Batch.setText(txtAmount_Block_Batch.getText());
														}
														public void removeUpdate(DocumentEvent e) {
															lblRunningTotal1_Block_Batch.setText(txtAmount_Block_Batch.getText());
														}
														public void changedUpdate(DocumentEvent e) {

														}
													});
												}

												JPanel pnlReqBy = new JPanel(new BorderLayout(3, 3));
												pnlText.add(pnlReqBy);
												{
													{
														txtRequestedBy_Block_Batch = new JXTextField();
														pnlReqBy.add(txtRequestedBy_Block_Batch, BorderLayout.WEST);
														txtRequestedBy_Block_Batch.setEditable(false);
														txtRequestedBy_Block_Batch.setPreferredSize(new Dimension(100, 20));
														{
															lblReqBy1_Block_Batch = new JLabel("[ ]");
															pnlReqBy.add(lblReqBy1_Block_Batch,BorderLayout.CENTER);
															lblReqBy1_Block_Batch.setHorizontalAlignment(JLabel.LEFT);
														}
													}
												}
												{
													txtRemarks_Block_Batch = new JXTextField();
													pnlText.add(txtRemarks_Block_Batch);
													txtRemarks_Block_Batch.setHorizontalAlignment(JLabel.LEFT);
													txtRemarks_Block_Batch.setBounds(215, 10, 280, 22);
													txtRemarks_Block_Batch.setEditable(false);
												}
											}
										}
									}
								}
							}
							{
								pnlBlockAmount = new JPanel(new BorderLayout(5, 5));
								pnlBlockDetail.add(pnlBlockAmount, BorderLayout.SOUTH);
								pnlBlockAmount.setBorder(JTBorderFactory.createTitleBorder("Detail AMOUNT"));
								pnlBlockAmount.setPreferredSize(new Dimension(450, 80));
								{
									JPanel pnlLabelTotal1 = new JPanel(new GridLayout(4, 2, 3, 3));
									pnlBlockAmount.add(pnlLabelTotal1, BorderLayout.WEST);
									{
										JLabel lblSetupAmount2 = new JLabel("                     ");
										pnlLabelTotal1.add(lblSetupAmount2, BorderLayout.WEST);
									}
								}
								{
									JPanel pnlLabelTotal = new JPanel(new GridLayout(3, 2, 3, 3));
									pnlBlockAmount.add(pnlLabelTotal, BorderLayout.CENTER);
									{
										JLabel lblSetupAmount = new JLabel("Setup Amount :");
										pnlLabelTotal.add(lblSetupAmount);
										{
											lblSetupAmount1_Block_Batch = new JLabel("0.00");
											pnlLabelTotal.add(lblSetupAmount1_Block_Batch);
										}
									}
									{
										JLabel lblRunningTotal = new JLabel("Running Total  :");
										pnlLabelTotal.add(lblRunningTotal);
										{
											lblRunningTotal1_Block_Batch = new JLabel("0.00");
											pnlLabelTotal.add(lblRunningTotal1_Block_Batch);
										}
									}
									{
										JLabel lblBalance = new JLabel("Balance +/(-)   :");
										pnlLabelTotal.add(lblBalance);
										{
											lblBalanceAmount_Block_Batch = new JLabel("0.00");
											pnlLabelTotal.add(lblBalanceAmount_Block_Batch);
										}
									}
								}
							}
						}
					}
				}
				//DCRF 2714
				{

					pnlBlockBatch_RPT = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Block-Lot Related Cost - Batch-RPT", null, pnlBlockBatch_RPT, null); //XXX Block-Lot Related Cost - Batch - RPT
					pnlBlockBatch_RPT.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					{
						pnlBlockCenter_rpt = new JPanel();
						pnlBlockBatch_RPT.add(pnlBlockCenter_rpt, BorderLayout.CENTER);
						pnlBlockCenter_rpt.setLayout(new BorderLayout(5, 5));
						pnlBlockCenter_rpt.setBorder(JTBorderFactory.createTitleBorder("Processing Cost Details(Block-Lot-RPT)"));
						pnlBlockCenter_rpt.setPreferredSize(new Dimension(800, 340));
						{
							pnlBlockTable_rpt = new JPanel(new BorderLayout(5, 5));
							pnlBlockCenter_rpt.add(pnlBlockTable_rpt, BorderLayout.WEST);
							//pnlBlockTable_rpt.setPreferredSize(new Dimension(460, 200));
							pnlBlockTable_rpt.setPreferredSize(new Dimension(600, 200));
							pnlBlockTable_rpt.setBorder(lineBorder);
							{
								scrollPCD_Block_Batch_RPT = new JScrollPane();
								pnlBlockTable_rpt.add(scrollPCD_Block_Batch_RPT, BorderLayout.CENTER);
								scrollPCD_Block_Batch_RPT.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
								{
									modelPCD_Block_Batch_RPT = new model_Block_client_RPT();


									tblPCD_Block_Batch_RPT = new _JTableMain(modelPCD_Block_Batch_RPT);
									tblPCD_Block_Batch_RPT.addMouseListener(this);
									scrollPCD_Block_Batch_RPT.setViewportView(tblPCD_Block_Batch_RPT);
									
									//tblPCD_Block_Batch_RPT.hideColumns("Description", "Client Id", "Seq No","Unit Id","Pbl Id");

									tblPCD_Block_Batch_RPT.setHorizontalScrollEnabled(true);
									
									tblPCD_Block_Batch_RPT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(e.getValueIsAdjusting()) {
												int r = tblPCD_Block_Batch_RPT.getSelectedRow();
												//String pbl_id = (String) tblPCD_Block_Batch_RPT.getValueAt(r, 15);
												
												//System.out.println("Tab4 pbl_id: "+pbl_id);
											}
										}
									});
									tblPCD_Block_Batch_RPT.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent arg0) {
											if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
												System.out.println("keyboard is pressed.");
												computerpt_total();
											}
										}
									});
								}
								{
									rowHeaderPCD_Block_Batch_RPT = tblPCD_Block_Batch_RPT.getRowHeader22();
									//rowHeaderPCD_Block_Batch_RPT.setModel(new DefaultListModel());
									scrollPCD_Block_Batch_RPT.setRowHeaderView(rowHeaderPCD_Block_Batch_RPT);
									scrollPCD_Block_Batch_RPT.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							pnlBlockDetail_rpt = new JPanel(new BorderLayout(5, 5));
							pnlBlockCenter_rpt.add(pnlBlockDetail_rpt, BorderLayout.CENTER);
							
							{
								pnlBlockInfo_rpt = new JPanel(new BorderLayout(5, 5));
								pnlBlockDetail_rpt.add(pnlBlockInfo_rpt, BorderLayout.CENTER);
								pnlBlockInfo_rpt.setBorder(JTBorderFactory.createTitleBorder(""));
								pnlBlockInfo_rpt.setPreferredSize(new Dimension(400, 205));
								
							}
							{
								pnlBlockAmount_rpt = new JPanel(new BorderLayout(5, 5));
								pnlBlockDetail_rpt.add(pnlBlockAmount_rpt, BorderLayout.SOUTH);
								pnlBlockAmount_rpt.setPreferredSize(new Dimension(450, 200));
								pnlBlockAmount_rpt.setBackground(Color.black);
								{
									JPanel pnlbuttons = new JPanel(new GridLayout(6, 1, 3, 3));
									pnlBlockAmount_rpt.add(pnlbuttons, BorderLayout.CENTER);
									pnlbuttons.setPreferredSize(new Dimension(0, 50));
									{
										btnnew_rpt = new JButton("Add New");
										pnlbuttons.add(btnnew_rpt);
										btnnew_rpt.setEnabled(false);
										btnnew_rpt.setActionCommand("addnew");
										btnnew_rpt.addActionListener(this);
									}
									{
										btnsave_rpt = new JButton("Save");
										pnlbuttons.add(btnsave_rpt);
										btnsave_rpt.setEnabled(false);
										btnsave_rpt.setActionCommand("savetag");
										btnsave_rpt.addActionListener(this);
									}
									{
										btnrplf = new JButton("Connect RPLF");
										pnlbuttons.add(btnrplf);
										btnrplf.setEnabled(false);
										btnrplf.setActionCommand("connectrplf");
										btnrplf.addActionListener(this);
									}
									{
										btnbatch_wout_rplf_rpt = new JButton("Batch w/out RPLF");
										pnlbuttons.add(btnbatch_wout_rplf_rpt);
										btnbatch_wout_rplf_rpt.setEnabled(false);
										btnbatch_wout_rplf_rpt.setActionCommand("wout_batch");
										btnbatch_wout_rplf_rpt.addActionListener(this);
									}
									{
										btnsave_w_amount = new JButton("Save with Amount");
										pnlbuttons.add(btnsave_w_amount);
										btnsave_w_amount.setEnabled(false);
										btnsave_w_amount.setActionCommand("save_wamount");
										btnsave_w_amount.addActionListener(this);
									}
									{
										btncancel_rpt = new JButton("Cancel");
										pnlbuttons.add(btncancel_rpt);
										btncancel_rpt.setActionCommand("cancel_rpt");
										btncancel_rpt.addActionListener(this);
									}
								}
							}
						}
					}
				}
				
				tabCenter.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();

						if(selectedTab == 0){
							lookupCoID.setEnabled(true);
							lookupProjectID.setEnabled(true);
							//cmbType_Batch.setEnabled(false);
							btnBatch.setEnabled(false);
							btnTSave.setEnabled(false);
							btnDRF.setEnabled(false);
							lblBatch.setText("<html><b>Batch No:</html>");
						}
						if(selectedTab == 1){
							lookupCoID.setEnabled(false);
							lookupProjectID.setEnabled(false);
							//cmbType_Batch.setEnabled(false);
							btnBatch.setEnabled(true);
							btnTSave.setEnabled(false);
							btnDRF.setEnabled(false);
							lblBatch.setText("<html><b>Batch No:</html>");
						}
						if(selectedTab == 2){ 
							lookupCoID.setEnabled(false);
							lookupProjectID.setEnabled(false);
							//cmbType_Batch.setEnabled(false);
							//lookupProject.setValue("015");
							//lblProject2.setText("[ TERRAVERDE RESIDENCES ]");
							btnBatch.setEnabled(false);
							btnTSave.setEnabled(false);
							btnDRF.setEnabled(false);
							lblBatch.setText("<html><b>Batch No:</html>");  
						}
						if(selectedTab == 3){
							lookupCoID.setEnabled(false);
							lookupProjectID.setEnabled(false);
							//cmbType_Batch.setEnabled(false);
							btnBatch.setEnabled(true);
							btnTSave.setEnabled(false);
							btnDRF.setEnabled(false);
							lblBatch.setText("<html><b>Batch No:</html>");
						}
						if(selectedTab == 4){
							lookupCoID.setEnabled(true);
							lookupProjectID.setEnabled(true);
							lookupCoID.setValue(null);
							lookupProjectID.setValue(null);
							txtCoName.setText("");
							txtProjectName.setText("");
							btnBatch.setEnabled(false);
							btnTSave.setEnabled(false);
							btnDRF.setEnabled(false);
							btnBatch.setEnabled(false);
							btnCancel.setEnabled(false);
							lblBatch.setText("<html><b>Batch No:</html>");
							
						}
					}
				});
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
				}
			}
		}
		btnBatch.setEnabled(false);
		btnTSave.setEnabled(false);
//		if (UserInfo.EmployeeCode.equals("900705") || UserInfo.EmployeeCode.equals("900767")) {
//			pnlState(true, true, true, true, true);
//		}else {
//			pnlState(true, true, true, true, false);
//		}
	}


	//display
	public void displayPCostDetails() {

		if (tabCenter.getSelectedIndex() == 0) {
			FncTables.clearTable(modelPCD);//Code to clear modelMain.		
			DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.

			String sql = "SELECT false, trim(b.pcostdtl_desc), a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.tran_type, null, null, a.batch_no, a.rpt_or_no, a.rpt_year, proc_cost_id  \n" + 
					"FROM rf_processing_cost a \n" + 
					"LEFT JOIN mf_processing_cost_dl b ON a.pcostid_dl = b.pcostdtl_id and a.co_id = b.co_id and a.projcode = b.proj_id and b.status_id ='A' \n" +
					"WHERE a.status_id = 'A'\n" +
					"AND a.entity_id = '"+lookupClient.getValue()+"'\n" +
					"AND a.pbl_id = '"+clientDetails[2]+"' \n" +
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
					"SELECT false, trim(b.pcostdtl_desc), a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.tran_type, null, null, a.batch_no, a.rpt_or_no, a.rpt_year   \n" + 
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

	public void displayPCostDetails_v2(String pbl_id) {//**ADDED BY JED 2019-03-14 : FOR DISPLAYING 2ND LOT**//

		if (tabCenter.getSelectedIndex() == 0) {
			FncTables.clearTable(modelPCD);//Code to clear modelMain.		
			DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.

			String sql = "SELECT false, trim(b.pcostdtl_desc), a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.tran_type, null, null, a.batch_no, a.rpt_or_no, a.rpt_year,proc_cost_id  \n" + 
					"FROM rf_processing_cost a \n" + 
					"LEFT JOIN mf_processing_cost_dl b ON a.pcostid_dl = b.pcostdtl_id and a.co_id = b.co_id and a.projcode = b.proj_id \n" +
					"WHERE a.status_id = 'A'\n" +
					"AND a.entity_id = '"+lookupClient.getValue()+"'\n" +
					"AND a.pbl_id = '"+pbl_id+"' \n" +
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

	private void displayClientDetails(String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		clientDetails = displayClientInformation_v2(entity_id, proj_id, pbl_id, seq_no, toPrint);
		if(clientDetails != null){
			lookupClient.setText((String) clientDetails[0]);
			lblClient.setText(generateTAG((String) clientDetails[1])); 
			lblProject.setText(generateTAG((String) clientDetails[6]));
			lblPblDescription.setText(String.format("[ %s ]", (String) clientDetails[3]));
			lblStatus.setText(generateTAG((String) clientDetails[10]));
			lblSellingAgent.setText(String.format("[ %s ]", (String) clientDetails[12]));
			//lblBatch.setText(String.format("<html><b>Batch No: %s</html>", (String) clientDetails[37]));
			lblDateReserved.setText(String.format("[ %s ]", (String) sdf.format(clientDetails[13])));
			//batch_no = (String)clientDetails[37];
			pbl = (String)clientDetails[2];
			entity = (String)clientDetails[0];
			buyertype = (String)clientDetails[7];
			
		}
	}


	private static Object[] displayClientInformation_v2(String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		String SQL = "SELECT \n" + 
				"TRIM(a.entity_id) AS entity_id,\n" + 
				"TRIM(g.entity_name) AS client_name,\n" + 
				"TRIM(a.pbl_id) AS pbl_id,\n" + 
				"TRIM(c.description) AS description, \n" + 
				"TRIM(a.projcode) AS proj_id, \n" + 
				"a.seq_no, \n" + 
				"TRIM(b.proj_name) AS proj_name, \n" + 
				"TRIM(a.buyertype) AS buyertype_id,\n" + 
				"TRIM(d.type_card_display) AS buyertype_name, \n" + 
				"TRIM(a.currentstatus) AS status_id,\n" + 
				"TRIM(e.status_desc) AS status_desc,\n" + 
				"a.sellingagent as agent_id,\n" + 
				"h.entity_name as agent_name,\n" + 
				"a.datersvd, \n" + 
				"a.unit_id\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_project b on b.proj_id = a.projcode\n" + 
				"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_buyer_type d on d.type_id = a.buyertype\n" + 
				"LEFT JOIN mf_buyer_status e on e.byrstatus_id = a.currentstatus\n" + 
				"LEFT JOIN rf_sellingagent_info f ON f.agent_id = a.sellingagent AND f.status_id = 'A'\n" + 
				"LEFT JOIN rf_entity g on a.entity_id = g.entity_id\n" + 
				"LEFT JOIN rf_entity h on a.sellingagent = h.entity_id\n" + 
				"\n" + 
				"WHERE a.entity_id = '"+entity_id+"'\n" + 
				"AND a.projcode = '"+proj_id+"'\n" + 
				"AND a.pbl_id = '"+pbl_id+"'\n" + 
				"AND a.seq_no = "+seq_no+" ";

		if(toPrint){
			FncSystem.out("Client Information", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private void displayPCostClient(String code_id) {//---modified by JED 2018-09-20 : for displaying clients with two lots---//

		FncTables.clearTable(modelPCD_Batch);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.

		String sql = 
				"\n" + 
						"SELECT false, \n" + 
						"CASE WHEN c.entity_id IS NOT NULL THEN btrim(c.entity_id::text)::character varying ELSE f.entity_id end AS \"Client ID\",\n" + 
						"case when b.entity_name is not null then btrim(b.entity_name::text) else g.entity_name END AS \"Client Name\",\n" + 
						"format('%s-%s-%s'::text, btrim(a.phase::text), btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\",\n" + 
						"btrim(a.pbl_id::text)::character varying AS \"PBL ID\",\n" + 
						"case when c.seq_no is not null then c.seq_no ELSE f.seq_no end AS \"Sequence\",\n" + 
						"case when e.type_card_display is not null then btrim(e.type_card_display::text) else (select type_card_display from rf_sold_unit a LEFT JOIN mf_buyer_type b ON b.type_id::text = a.buyertype::text where entity_id = f.entity_id limit 1) end AS \"BuyerType\",\n" + 
						"btrim(a.proj_id::text)::character varying AS \"Project ID\",\n" + 
						"btrim(d.proj_name::text) AS \"Project Name\", c.unit_id AS \"Unit ID\",  '' AS \"Batch No\" \n" + 
						"FROM mf_unit_info a\n" + 
						"LEFT JOIN rf_sold_unit c ON a.proj_id::text = c.projcode::text AND a.pbl_id::text = c.pbl_id::text AND c.status_id = 'A'\n" + 
						"LEFT JOIN rf_entity b ON b.entity_id::text = c.entity_id::text\n" + 
						"LEFT JOIN mf_project d ON d.proj_id::text = a.proj_id::text\n" + 
						"LEFT JOIN mf_buyer_type e ON e.type_id::text = c.buyertype::text\n" + 
						"LEFT JOIN hs_sold_other_lots f on f.proj_id = a.proj_id and f.oth_pbl_id = a.pbl_id and f.status_id = 'A'\n" + 
						"LEFT join rf_entity g on g.entity_id = f.entity_id\n" + 
						"WHERE a.status_id = 'R' --and c.entity_id is not null and b.entity_name is not null \n" + 
						"--AND a.pbl_id in ('390', '392')\n" + 
						"ORDER BY btrim(b.entity_name::text), getinteger(a.phase), getinteger(a.block), getinteger(a.lot)  " ;

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display SQL For PCost", sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelPCD_Batch.addRow(db.getResult()[x]);
				listModel.addElement(modelPCD_Batch.getRowCount());
			}	
		}
		scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
		tblPCD_Batch.packAll();
	}

	//---ADDED BY JED VICEDO 9-4-18 : FILTERING CLIENTS WITH EXISTING TAG (PARTICULAR: MERALCO FOR PMD)---//
	//---modified by jed 2018-10-17 : DCRF no. 797 to put control in tagging particulars in PCOST to avoid double tagging except the following particulars---//
	//---modified by jed 2019-01-09 : additional filter to preview cancelled accounts as per request by nezza---//
	private void displayAvailablePCostClients(DefaultTableModel modelMain, JList rowHeader, String pcostid_dl, Integer type){

		Integer index = tabCenter.getSelectedIndex();

		//		if (pcostid_dl.equals("050") || pcostid_dl.equals("049") || pcostid_dl.equals("040") || pcostid_dl.equals("041") || 
		//				pcostid_dl.equals("042") || pcostid_dl.equals("003") || pcostid_dl.equals("004") || pcostid_dl.equals("045") || pcostid_dl.equals("218") ||
		//				 	pcostid_dl.equals("001") || pcostid_dl.equals("002") || pcostid_dl.equals("048") || pcostid_dl.equals("221") || pcostid_dl.equals("222") ||
		//				 		pcostid_dl.equals("223") || pcostid_dl.equals("224") || pcostid_dl.equals("225") || pcostid_dl.equals("216")){

		if(index == 1){//**BUYER RELATED BATCH**//
			

			if(checkType(pcostid_dl)==false && type==(1)){

				FncTables.clearTable(modelMain);
				DefaultListModel listModel = new DefaultListModel();
				rowHeader.setModel(listModel);

				String strSQL = 

						//"select * from view_pcost_detail_all ('"+pcostid_dl+"')";
						//Add retag batch
						"select * from view_pcost_detail_all_v2 ('"+pcostid_dl+"')";//Edited by Erick 2023-05-02

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

				//scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
				tblPCD_Batch.packAll();
			}

			else if(checkType(pcostid_dl)==false && type==(2)){

				FncTables.clearTable(modelMain);
				DefaultListModel listModel = new DefaultListModel();
				//rowHeader.setModel(listModel);

				String strSQL = 

						"SELECT * FROM view_pcost_detail_all_single ('"+pcostid_dl+"')";

				FncSystem.out("List of the available clients (filtered)", strSQL);

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

			}
		}

		if(index == 3){//**BLOCK-LOT RELATED BATCH**//
			System.out.println("checkType : "+checkType(pcostid_dl));

			if(checkType(pcostid_dl)==true && type==(1)){

				FncTables.clearTable(modelMain);
				DefaultListModel listModel = new DefaultListModel();
				//rowHeader.setModel(listModel);

				String strSQL = 

						"SELECT * FROM view_pcost_detail (NULLIF ('"+pcostid_dl+"' , 'null'))";

				FncSystem.out("List of the available clients (filtered)", strSQL);

				pgSelect db = new pgSelect();
				db.select(strSQL);
				if (db.isNotNull()) {
					for (int x = 0; x < db.getRowCount(); x++) { 
						// Adding of row in table
						modelMain.addRow(db.getResult()[x]);
						listModel.addElement(modelMain.getRowCount());
					}
				}

				scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Block_Batch.getRowCount())));
				tblPCD_Block_Batch.packAll();

			}

			else if(checkType(pcostid_dl)==true && type==(2)){

				FncTables.clearTable(modelMain);
				DefaultListModel listModel = new DefaultListModel();
				//rowHeader.setModel(listModel);

				String strSQL = 

						"SELECT * FROM view_pcost_detail_single (NULLIF ('"+pcostid_dl+"' , 'null'))";

				FncSystem.out("List of the available clients (filtered)", strSQL);

				pgSelect db = new pgSelect();
				db.select(strSQL);
				if (db.isNotNull()) {
					for (int x = 0; x < db.getRowCount(); x++) { 
						// Adding of row in table
						modelMain.addRow(db.getResult()[x]);
						listModel.addElement(modelMain.getRowCount());
					}
				}

				scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Block_Batch.getRowCount())));
				tblPCD_Block_Batch.packAll();

			}

		}
		//Display clients tab4
		if(index == 4){
			if(type==(1)){

				FncTables.clearTable(modelMain);
				DefaultListModel listModel = new DefaultListModel();
				rowHeader.setModel(listModel);

				String strSQL = 

						"SELECT * FROM view_pcost_detail_rpt ('"+lookupProjectID.getValue()+"')";

				FncSystem.out("List of the available clients For RPT (filtered)", strSQL);

				pgSelect db = new pgSelect();
				db.select(strSQL);
				if (db.isNotNull()) {
					for (int x = 0; x < db.getRowCount(); x++) { 
						// Adding of row in table
						modelMain.addRow(db.getResult()[x]);
						listModel.addElement(modelMain.getRowCount());
					}
				}

				//scrollPCD_Block_Batch_RPT.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Block_Batch_RPT.getRowCount())));
				tblPCD_Block_Batch_RPT.packAll();

			}
		}

	}
	
	private void displayPCostClients_batch(DefaultTableModel modelMain, JList rowHeader, String batch, String co_id, String proj_id){
		Integer index = tabCenter.getSelectedIndex();
		
		if(index == 4){

				FncTables.clearTable(modelMain);
				DefaultListModel listModel = new DefaultListModel();
				rowHeader.setModel(listModel);

				String strSQL = 

						"SELECT * FROM display_pcost_detail_rpt_batch_v2 ('"+batch+"','"+co_id+"','"+proj_id+"')";

				FncSystem.out("List of the available clients For RPT (filtered)", strSQL);

				pgSelect db = new pgSelect();
				db.select(strSQL);
				if (db.isNotNull()) {
					for (int x = 0; x < db.getRowCount(); x++) { 
						// Adding of row in table
						modelMain.addRow(db.getResult()[x]);
						listModel.addElement(modelMain.getRowCount());
					}
				}

				//scrollPCD_Block_Batch_RPT.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Block_Batch_RPT.getRowCount())));
				tblPCD_Block_Batch_RPT.packAll();

		}
	}

	private void displayOldClients(DefaultTableModel modelMain, JList rowHeader, String pcostid_dl, Integer type){

		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		//rowHeader.setModel(listModel);

		String strSQL = 

				"select * from view_pcost_detail_all_old ('"+pcostid_dl+"')";

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

	}


	private static Boolean checkType(String pcostid_dl){

		Boolean x = false;

		String SQL =
				"select \n" + 
						"pcostdtl_id \n" + 
						"from mf_processing_cost_dl \n" + 
						"where status_id = 'A'\n" + 
						"and pcostdtl_id = '"+pcostid_dl+"'\n" +
						"and pcostdtl_type in ('L') limit 1";
		
		FncSystem.out("PCOST ID", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				x=true;
			}else{
				x=true;
			}
		}else{
			x=false;
		}

		return x;

	}

	private void displayProcessingCostDetails(String pbl_id, String cost_desc, String batch) {

		Object[] data = getProcessingCostDetails(pbl_id, cost_desc, batch);
		String requestor = UserInfo.EmployeeCode;

		//		if (tabCenter.getSelectedIndex() == 0) {
		//		
		//		if (data != null) {
		//			int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
		//			String code = (String) data[0];
		//			Date tran_date = (Date) data[1];
		//			BigDecimal amount = (BigDecimal) data[2];
		//			String reqby = (String) data[4];
		//			String reqbyname = (String) data[3];
		//			String remarks = (String) data[5];
		//			BigDecimal setup_amt = (BigDecimal) data[6];
		//			String batch_no = (String) data[7];
		//		
		//			lookupCode.setValue(code);
		//			lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD.getValueAt(row, 1)));
		//			cmbType.setSelectedItem("Payment");
		//			dateSched.setDate(tran_date);
		//			txtAmount.setValue(amount);
		//			txtRequestedBy.setText(reqby);
		//			lblReqBy1.setText(String.format("[ %s ]", reqbyname));
		//			txtRemarks.setText(remarks);
		//			lblSetupAmount1.setText(String.format("%s", setup_amt));
		//			lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
		//			} else {
		//				String batch_no = (String) generateBatchNo();
		//			
		//				lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
		//			}
		//		}
		if (tabCenter.getSelectedIndex() == 2) {
			if (data != null) {
				System.out.println("Dumaan dito sa first step");
				int row = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
				String code = (String) data[0];
				Date tran_date = (Date) data[1];
				BigDecimal amount = (BigDecimal) data[2];
				//BigDecimal amount = (BigDecimal) modelPCD_BlockLot.getValueAt(row2, 3);
				String reqby = (String) data[3];
				String reqbyname = (String) data[4];
				String remarks = (String) data[5];
				BigDecimal setup_amt = (BigDecimal) data[6];
				//String batch_no = (String) data[7];

				lookupCode_BlockLot.setValue(code);
				lblCode_BlockLot.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD_BlockLot.getValueAt(row, 1)));
				cmbType_BlockLot.setSelectedItem("Payment");
				dateSched_BlockLot.setDate(tran_date);
				txtAmount_BlockLot.setValue(amount);
				txtRequestedBy_BlockLot.setText(reqby);
				lblReqBy1_BlockLot.setText(String.format("[ %s ]", reqbyname));
				txtRemarks_BlockLot.setText(remarks);
				txtRemarks_BlockLot.setToolTipText(remarks);
				lblSetupAmount1_BlockLot.setText(String.format("%s", setup_amt));
				//lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
			} else {//no batch//
				System.out.println("Dumaan dito sa second step");

				int row2 = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
				String check_batch = (String) modelPCD_BlockLot.getValueAt(row2, 11);

				Object[] data1 = getProcessingCostDetails1(cost_desc, requestor);

				String code1 = (String) data1[0];
				Date tran_date1 = (Date) modelPCD_BlockLot.getValueAt(row2, 2);
				BigDecimal amount1 = (BigDecimal) modelPCD_BlockLot.getValueAt(row2, 3);
				String reqby1 = requestor;
				String reqbyname1 = (String) data1[3];
				String remarks1 = (String) modelPCD_BlockLot.getValueAt(row2, 4);
				BigDecimal setup_amt1 = (BigDecimal) data1[2];

				lookupCode_BlockLot.setValue(code1);
				lblCode_BlockLot.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD_BlockLot.getValueAt(row2, 1)));
				cmbType_BlockLot.setSelectedItem("Payment");
				dateSched_BlockLot.setDate(tran_date1);
				txtAmount_BlockLot.setValue(amount1);
				txtRequestedBy_BlockLot.setText(reqby1);
				lblReqBy1_BlockLot.setText(String.format("[ %s ]", reqbyname1));
				txtRemarks_BlockLot.setText(remarks1);
				lblSetupAmount1_BlockLot.setText(String.format("%s", setup_amt1));
			}
		}
	}
	//---edited by jed 2018-10-23 : getting of pcost detail in individual no dcrf---//
	private void displayProcessingCostDetails_IND(String cost_desc, String entity_id, String batch) {

		Object[] data = getProcessingCostDetails_IND(cost_desc, entity_id, batch);
		String requestor = UserInfo.EmployeeCode;

		if (tabCenter.getSelectedIndex() == 0) {

			if (data != null){//--with batch no--//
				int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
				String code = (String) data[0];
				Date tran_date = (Date) modelPCD.getValueAt(row, 2);
				BigDecimal amount = (BigDecimal) modelPCD.getValueAt(row, 3);
				String remarks = (String) modelPCD.getValueAt(row, 4);
				BigDecimal setup_amt = (BigDecimal) data[6];
				String reqby = (String) data [4];
				String reqbyname = (String) data [3];

				lookupCode.setValue(code);
				lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD.getValueAt(row, 1)));
				cmbType.setSelectedItem("Payment");
				dateSched.setDate(tran_date);
				txtAmount.setValue(amount);
				txtRequestedBy.setText(reqby);
				lblReqBy1.setText(String.format("[ %s ]", reqbyname));
				txtRemarks.setText(remarks);
				txtRemarks.setToolTipText(remarks);
				lblSetupAmount1.setText(String.format("%s", setup_amt));
				//lblRunningTotal1.setText(String.format("%s", amount));

			}else{//--no batch no--//
				int row1 = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
				String check_batch = (String) modelPCD.getValueAt(row1, 11);

				Object[] data1 = getProcessingCostDetails1_IND(cost_desc, requestor);

				if(check_batch != null){//---meaning TAG AGAIN---//

					String code2 = (String) data1[0];
					Date tran_date2 = (Date) modelPCD.getValueAt(row1, 2);
					BigDecimal amount2 = (BigDecimal) modelPCD.getValueAt(row1, 3);
					String remarks2 = (String) modelPCD.getValueAt(row1, 4);
					BigDecimal setup_amt2 = (BigDecimal) data1[2];
					String reqby2 = requestor;
					String reqbyname2 = (String) data1 [3];

					lookupCode.setValue(code2);
					lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD.getValueAt(row1, 1)));
					cmbType.setSelectedItem("Payment");
					dateSched.setDate(tran_date2);
					txtAmount.setValue(amount2);
					txtRequestedBy.setText(reqby2);
					lblReqBy1.setText(String.format("[ %s ]", reqbyname2));
					txtRemarks.setText(remarks2);
					lblSetupAmount1.setText(String.format("%s", setup_amt2));

				}else{//--meaning additional particular for the client---//

					String batch_no = (String) generateBatchNo();

					String code1 = (String) data1[0];
					Date tran_date1 = (Date) modelPCD.getValueAt(row1, 2);
					BigDecimal amount1 = (BigDecimal) modelPCD.getValueAt(row1, 3);
					String remarks1 = (String) modelPCD.getValueAt(row1, 4);
					BigDecimal setup_amt1 = (BigDecimal) data1[2];
					String reqby1 = requestor;
					String reqbyname1 = (String) data1 [3];

					lookupCode.setValue(code1);
					lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD.getValueAt(row1, 1)));
					cmbType.setSelectedItem("Payment");
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

		if (tabCenter.getSelectedIndex() == 2) {
			int row2 = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
			String code = (String) data[0];
			Date tran_date2 = (Date) modelPCD_BlockLot.getValueAt(row2, 2);
			BigDecimal amount2 = (BigDecimal) modelPCD_BlockLot.getValueAt(row2, 3);
			String remarks2 = (String) modelPCD.getValueAt(row2, 4);
			BigDecimal setup_amt2 = (BigDecimal) data[1];

			lookupCode_BlockLot.setValue(code);
			lblCode_BlockLot.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD_BlockLot.getValueAt(row2, 1)));
			cmbType_BlockLot.setSelectedItem("Payment");
			dateSched_BlockLot.setDate(tran_date2);
			txtAmount_BlockLot.setValue(amount2);
			txtRemarks_BlockLot.setText(remarks2);
			txtRemarks_BlockLot.setToolTipText(remarks2);
			lblSetupAmount1_BlockLot.setText(String.format("%s", setup_amt2));
		}
	}

	private void displayProcessingCostDetails1(String cost_desc) {

		Object[] data1 = getProcessingCostDetails1(cost_desc, lookupCoID.getValue(), lookupProjectID.getValue());


		if (tabCenter.getSelectedIndex() == 0) {
			if (data1 != null) {
				int row = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());
				String code = (String) data1[0];
				Date tran_date2 = (Date) modelPCD.getValueAt(row, 2);
				BigDecimal amount2 = (BigDecimal) modelPCD.getValueAt(row, 3);
				String remarks2 = (String) modelPCD.getValueAt(row, 4);
				BigDecimal setup_amt2 = (BigDecimal) data1[1];

				lookupCode.setValue(code);
				lblCode.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD.getValueAt(row, 1)));
				cmbType.setSelectedItem("Payment");
				dateSched.setDate(tran_date2);
				txtAmount.setValue(amount2);
				txtRemarks.setText(remarks2);
				lblSetupAmount1.setText(String.format("%s", setup_amt2));
			}
		}
		if (tabCenter.getSelectedIndex() == 2) {
			if (data1 != null) {
				int row2 = tblPCD_BlockLot.convertRowIndexToModel(tblPCD_BlockLot.getSelectedRow());
				String code = (String) data1[0];
				Date tran_date2 = (Date) modelPCD_BlockLot.getValueAt(row2, 2);
				BigDecimal amount2 = (BigDecimal) modelPCD_BlockLot.getValueAt(row2, 3);
				String remarks2 = (String) modelPCD.getValueAt(row2, 4);
				BigDecimal setup_amt2 = (BigDecimal) data1[1];

				lookupCode_BlockLot.setValue(code);
				lblCode_BlockLot.setText(String.format("<html><font size = 2>[ %s ]</html>", modelPCD_BlockLot.getValueAt(row2, 1)));
				cmbType_BlockLot.setSelectedItem("Payment");
				dateSched_BlockLot.setDate(tran_date2);
				txtAmount_BlockLot.setValue(amount2);
				txtRemarks_BlockLot.setText(remarks2);
				lblSetupAmount1_BlockLot.setText(String.format("%s", setup_amt2));
			}
		}
	}


	//action	
	private void refreshBuyer() {
		if (lookupClient.getText().equals("")) {
			lblClient.setText("[ ]");
			lblProject.setText("[ ]");
			lblPblDescription.setText("[ ]");
			lblSellingAgent.setText("[ ]");
			lblDateReserved.setText("[ ]");
			lblStatus.setText("[ ]");
			lblBatch.setText("<html><b>Batch No:</html>");
			lblCode.setText("[ ]");
			lookupCode.setText(null);
			txtAmount.setText("0.00");
			txtRequestedBy.setText(null);
			lblReqBy1.setText("[ ]");
			txtRemarks.setText(null);
			btnDRF.setEnabled(false);
			btnTSave.setEnabled(false);
			//btnEdit.setEnabled(false);
			btnBatch.setEnabled(false);
			cmbType.setSelectedItem("Payment");
			//dateSched.setDate(Calendar.getInstance().getTime());
			dateSched.setDate(FncGlobal.getDateToday());
			lblSetupAmount1.setText("0.00");
			lblRunningTotal1.setText("0.00");
			lblBalanceAmount.setText("0.00");
			modelPCD.setRowCount(0);
			modelPCDTotal.setValueAt(new BigDecimal(0.00), 0, 3);
			txtAmount.setEditable(false);
			txtRequestedBy.setEditable(false);
			dateSched.setEnabled(false);
			btnOR.setEnabled(false);
		}
	} // refreshTO()

	private void refreshBatch() {

		if (lookupCode_Batch.getText().equals("")) {
			lblCode_Batch.setText("[ ]");
			//cmbType_Batch.setSelectedItem("Payment");
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

	private void refreshBlockLot() {

		if (lookupProject.getText().equals("")) {
			lblProject2.setText("[ ]");
			lookupUnit.setValue(null);
			lblUnit.setText("[ ]");
			lblBuyer.setText("[ ]");
			lblCode_BlockLot.setText("[ ]");
			lookupCode_BlockLot.setText(null);
			txtAmount_BlockLot.setText("0.00");
			txtRequestedBy_BlockLot.setText(null);
			lblReqBy1_BlockLot.setText("[ ]");
			txtRemarks_BlockLot.setText(null);
			btnDRF.setEnabled(false);
			btnTSave.setEnabled(false);
			//btnEdit.setEnabled(false);
			btnBatch.setEnabled(false);
			cmbType_BlockLot.setSelectedItem("Payment");
			dateSched_BlockLot.setDate(Calendar.getInstance().getTime());
			lblSetupAmount1_BlockLot.setText("0.00");
			lblRunningTotal1_BlockLot.setText("0.00");
			lblBalanceAmount_BlockLot.setText("0.00");
			modelPCD_BlockLot.setRowCount(0);
			modelPCDTotal_BlockLot.setValueAt(new BigDecimal(0.00), 0, 3);
			txtAmount_BlockLot.setEditable(false);
			txtRequestedBy_BlockLot.setEditable(false);
			dateSched_BlockLot.setEnabled(false);
			lblBatch.setText("<html><b>Batch No: </html>");
			btnOR.setEnabled(false);
		}
	} // refreshTO()

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

		if(actionCommand.equals("Create Disbursement Request")){

			if(tabCenter.getSelectedIndex() == 0){
				if(withZeroAmountIndividual()){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
				}else{
					if(checkBatch(batch_no).equals("")) {//**ADDED BY JED 2019-09-03 TO AVOID SAME BATCHING**//
						JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
					}else {
						if(JOptionPane.showConfirmDialog(getContentPane(), "Batch no. " + checkBatch(batch_no) + " is already used." +
								"\n" + "Generate new batch?", "Confirmation", 
								JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

							String new_batch = generateBatchNo_v2();

							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));
						}	
					}
				}
			}
			if(tabCenter.getSelectedIndex() == 1){
				JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}

			if(tabCenter.getSelectedIndex() == 2){
				if(withZeroAmtBlkLot()){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
				}else{
					if(checkBatch(batch_no).equals("")) {//**ADDED BY JED 2019-09-03 TO AVOID SAME BATCHING**//
						JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
								JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
					}else {
						if(JOptionPane.showConfirmDialog(getContentPane(), "Batch no. " + checkBatch(batch_no) + " is already used." +
								"\n" + "Generate new batch?", "Confirmation", 
								JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

							String new_batch = generateBatchNo_v2();

							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));
						}	
					}
				}

			}
			if(tabCenter.getSelectedIndex() == 3){
				JOptionPane.showOptionDialog(getContentPane(), pnlCreateRPLF, "Create RPLF",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}
		}

		if(actionCommand.equals("OR")){
			JOptionPane.showOptionDialog(getContentPane(), pnlCreateOR, "Create OR",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		
		}

		if(actionCommand.equals("Create RPLF")){ createRPLF(); }

		if(actionCommand.equals("Save")){ save(); }

		if(actionCommand.equals("Save to Existing")){ save2(); }//**ADDED BY JED 2019-09-03 TO AVOID SAME BATCHING**//

		if(actionCommand.equals("Save OR")){ saveOR(); }

		if(actionCommand.equals("Save OR Existing")){ saveOR2(); }//**ADDED BY JED 2019-09-03 TO AVOID SAME BATCHING**//

		if(actionCommand.equals("Cancel RPLF")){
			lookupRequestType.setValue(null);
			lblRequestType.setText("[ ]");
			lookupAvailer.setValue(null);
			lblAvailer.setText("[ ]");
			lookupAvailerType.setValue(null);
			lblAvailerType.setText("[ ] ");
			lookupControlNo.setText("");;
			lblctrNo.setText("[ ]");
			lookupControlNo.setEnabled(false);
		}
		if(actionCommand.equals("Cancel OR")){
			txtORNo.setText("");
			txtYear.setText("");
		}
		if(actionCommand.equals("Batch w/ out RPLF")){
			int index = tabCenter.getSelectedIndex();
			if(index == 1) {//**BUYER RELATED**//
				getBatchList();
				btnTSave.setActionCommand("Save to Existing");
				btnTSave.setEnabled(false); //ADDED BY LESTER 2016-12-19 ------edited by JED VICEDO 2018-08-06
				btnDRF.setEnabled(true); //ADDED BY LESTER 2016-12-19
				//CHANGE DISPLAY OF PCOST DETAILS HERE BASED ON retrieved batch no

				//---added by jed 2018-8-6---//
				pnlState(false, true, false, false, false);
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

			if(index == 3) {//**UNIT RELATED**//
				getBatchList();
				btnORSave.setActionCommand("Save OR Existing");
				btnTSave.setActionCommand("Save to Existing");
				btnTSave.setEnabled(false); //ADDED BY LESTER 2016-12-19 ------edited by JED VICEDO 2018-08-06
				btnDRF.setEnabled(true); //ADDED BY LESTER 2016-12-19
				//CHANGE DISPLAY OF PCOST DETAILS HERE BASED ON retrieved batch no

				//---added by jed 2018-8-6---//
				pnlState(false, false, false, true, false);
				//displayPCostClient(null);

				modelPCD_Block_Batch.clear();
				scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
				tblPCD_Block_Batch.packAll();

				lookupCode_Block_Batch.setText("");
				lblCode_Block_Batch.setText("[ ]");
				lblSetupAmount1_Block_Batch.setText("0.00");
				lblBalanceAmount_Block_Batch.setText("0.00");
				txtRemarks_Block_Batch.setText("");
				txtAmount_Block_Batch.setValue(0.00);
				txtRequestedBy_Block_Batch.setText("");
				lblReqBy1_Block_Batch.setText("[ ]");
			}
			
//			if(index == 4) {
//				
//				pnlState(false, false, false, false, true);
//				getBatchList_rpt();
//				//btnDRF.setEnabled(true); 
//				String batch_rpt = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
//				System.out.println("batch_rpt: "+ (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "") );
//				
//				displayPCostClients_batch(modelPCD_Block_Batch_RPT, rowHeaderPCD_Block_Batch_RPT, batch_rpt, lookupCoID.getValue(), lookupProjectID.getValue());
//				modelPCD_Block_Batch_RPT.setEditable(true);
//				scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
//				btnrplf.setEnabled(true);
//			}
		}

		//		if (actionCommand.equals("Remove Row")) { removeRow();
		//
		//		}

		if (actionCommand.equals("Cancel")) { cancel(); 

		}
		if(actionCommand.equals("addnew")) { //Display available clients fro rpt only
			displayAvailablePCostClients(modelPCD_Block_Batch_RPT, rowHeaderPCD_Block_Batch_RPT, null, 1);
			btnsave_rpt.setEnabled(true);
			btnnew_rpt.setEnabled(false);
			btnbatch_wout_rplf_rpt.setEnabled(false);
			pnlState(false, false, false, false, true);
			
		}
		if(actionCommand.equals("savetag")) { //save tagged account without amounts.
			if(tabCenter.getSelectedIndex() == 4 ) {
				if(JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

					String checkBatch = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
					if(checkBatch(checkBatch).equals("")) {

						String rplf_no = "";
						for(int x = 0; x < modelPCD_Block_Batch_RPT.getRowCount(); x++){
							
							Boolean isSelected = (Boolean) modelPCD_Block_Batch_RPT.getValueAt(x, 0);
							String client_name = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 1);
							String projcode = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 2);
							//BigDecimal amountlot = (BigDecimal) modelPCD_Block_Batch_RPT.getValueAt(x, 4);
							//String yearlot = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 5);
							//String remarkslot = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 6);
							//BigDecimal amounthouse = (BigDecimal) modelPCD_Block_Batch_RPT.getValueAt(x, 7);
							//String yearhouse = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 8);
							//String remarkshouse = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 9);
							String rpt_or = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 10);
							String entity_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 13);
							Integer seq_no = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 14);
							String unit_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 15);
							String pbl_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 16);
							String batch_no_rpt = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
							//Date trans_date = (Date) dateSched_Block_Batch.getDate();
//							String costid = (String) lookupCode_Block_Batch.getValue();
//							BigDecimal setup_amount = (BigDecimal) txtAmount_Block_Batch.getValued(); //new BigDecimal (txtAmount_Batch.getText());
							String req_id = UserInfo.EmployeeCode;
//							String emp_code = (String) UserInfo.EmployeeCode;
							System.out.println("Dumaan sa saving");

							if(isSelected){
								String SQL = "SELECT sp_save_processing_cost_woutrplf_rpt('"+entity_id+"', '"+projcode+"', '"+pbl_id+"',"
										+ "'"+req_id+"',  '"+req_id+"', NULLIF('"+batch_no_rpt+"','null'), NULLIF('"+unit_id+"','null'),"
										+ ""+seq_no+", '"+rplf_no+"', '"+lookupCoID.getValue()+"', '"+rpt_or+"')";

								pgSelect db = new pgSelect();
								FncSystem.out("SQL", SQL);
								db.select(SQL);

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no_rpt));
								
							}
						}

						JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
						lblBatch.setText("<html><b>Batch No:</html>");

						modelPCD_Block_Batch_RPT.clear();
						scrollPCD_Block_Batch_RPT.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
						tblPCD_Block_Batch_RPT.packAll();

						btnsave_rpt.setEnabled(false); 
						btnnew_rpt.setEnabled(true);
						btnrplf.setEnabled(false);
						btnbatch_wout_rplf_rpt.setEnabled(true);
						pnlState(true, true, true, true, true);

					}else {

						if(JOptionPane.showConfirmDialog(getContentPane(), "Batch no. " + checkBatch(checkBatch) + " is already used." +
								"\n" + "Generate new batch?", "Confirmation", 
								JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

							String new_batch = generateBatchNo_v2();

							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));

						}
					}
				}
			}
		}
		if(actionCommand.equals("connectrplf")) {
			getBatchList_rplf_mto();
			
		}
		
		if(actionCommand.equals("wout_batch")) {
			if(lookupCoID.getValue().equals(null)|| lookupCoID.getValue().equals("")) {
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select Company and Project.");
			}else {
				pnlState(false, false, false, false, true);
				getBatchList_rpt();
				//btnDRF.setEnabled(true); 
				String batch_rpt = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
				System.out.println("batch_rpt: "+ (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "") );
				
				displayPCostClients_batch(modelPCD_Block_Batch_RPT, rowHeaderPCD_Block_Batch_RPT, batch_rpt, lookupCoID.getValue(), lookupProjectID.getValue());
				modelPCD_Block_Batch_RPT.setEditable(true);
				scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
				
//				btnnew_rpt.setEnabled(false);
//				btnsave_rpt.setEnabled(false);
//				btnrplf.setEnabled(true);
//				btnbatch_wout_rplf_rpt.setEnabled(false);
//				btnsave_w_amount.setEnabled(true);
				
			}
		}
		
		if(actionCommand.equals("save_wamount")) {
//			int[] selectedRows = tblPCD_Block_Batch_RPT.getSelectedRows();
//			System.out.println("selectedRows"+selectedRows.length);
			ArrayList<String> clientlist = new ArrayList<String>();
			ArrayList<String> listcost_id = new ArrayList<String>();
			
			for (int x=0; x < modelPCD_Block_Batch_RPT.getRowCount(); x++) {
				Boolean isSelected = (Boolean) modelPCD_Block_Batch_RPT.getValueAt(x, 0);
				if(isSelected) {
					String entity_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 13);
					listcost_id.add(entity_id);
				}
			}
			if(listcost_id.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select row.", "Saving",
						JOptionPane.INFORMATION_MESSAGE);
			}else {
				if(JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					
							for(int x = 0; x < modelPCD_Block_Batch_RPT.getRowCount(); x++){
								
								
								Boolean isSelected = (Boolean) modelPCD_Block_Batch_RPT.getValueAt(x, 0);
								String client_name = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 1);
								String projcode = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 2);
								BigDecimal amountlot = new BigDecimal(String.format("%s", modelPCD_Block_Batch_RPT.getValueAt(x, 4)));
								Integer yearlot = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 5);
								String remarkslot = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 6);
								BigDecimal amounthouse = new BigDecimal(String.format("%s", modelPCD_Block_Batch_RPT.getValueAt(x, 7)));
								Integer yearhouse = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 8);
								String remarkshouse = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 9);
								String rpt_or = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 10);
								String entity_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 13);
								Integer seq_no = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 14);
								String unit_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 15);
								String pbl_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 16);
								String code = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 17);
								Integer rec_id = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 18);
								String batch_no_rpt = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
								String user_id = UserInfo.EmployeeCode;
								System.out.println("Dumaan sa saving");
								
								String costid = null;
								Integer year = null;

								if(isSelected){
									if (amountlot != new BigDecimal(0.00)) {
										costid="216";
										year = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 5);
										System.out.println("year: "+year);
									}else if(amounthouse != new BigDecimal(0.00)) {
										costid = "215";
										year = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 8);
										System.out.println("year: "+year);									
									}
									if(checkTagRPT_v2(entity_id,year , pbl_id, costid).equals("")) {//Check if there is already tag
//										String SQL = "select sp_save_processing_cost_wamount_rpt('"+code+"', "+rec_id+", "+amountlot+", "+yearlot+", '"+remarkslot+"', "+
//										" "+amounthouse+", "+yearhouse+", '"+remarkshouse+"', '"+batch_no_rpt+"', '"+user_id+"', '"+rpt_or+"')";
										String SQL = "select  from sp_save_processing_cost_wamount_rpt_v2('"+code+"', "+amountlot+", "+yearlot+", '"+remarkslot+"', "+amounthouse+", "+yearhouse+", "
												+ "'"+remarkshouse+"', '"+batch_no_rpt+"', '"+user_id+"', '"+rpt_or+"', '"+lookupCoID.getValue()+"', '"+entity_id+"', '"+projcode+"', "+seq_no+")";
										
										pgSelect db = new pgSelect();
										FncSystem.out("SQL", SQL);
										db.select(SQL);
		
										lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no_rpt));
									}else {
										clientlist.add(checkTagRPT_v2(entity_id,year , pbl_id, costid));
									}
									if(clientlist.isEmpty()) {
										btnsave_w_amount.setEnabled(false);
										btnrplf.setEnabled(true);
										
										JOptionPane.showMessageDialog(getContentPane(), "Transaction Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
									}else {
										String tag_clients = clientlist.toString().replaceAll("\\[", "").replaceAll("\\]","");
										JOptionPane.showMessageDialog(getContentPane(), "The client/s are already tagged:" + "\n" + "**" + tag_clients + "\n" /*+ "**" + description*/, "Error", JOptionPane.ERROR_MESSAGE);
									}
								}
							}
				}
				
			}
//			if (selectedRows.length == 0) {
//				JOptionPane.showMessageDialog(getContentPane(), "Please select row.", "Saving",
//						JOptionPane.INFORMATION_MESSAGE);
//				return;
//			}else {
//				
//				if(JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
//						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//					
//							for(int x = 0; x < modelPCD_Block_Batch_RPT.getRowCount(); x++){
//								
//								
//								Boolean isSelected = (Boolean) modelPCD_Block_Batch_RPT.getValueAt(x, 0);
//								String client_name = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 1);
//								String projcode = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 2);
//								BigDecimal amountlot = new BigDecimal(String.format("%s", modelPCD_Block_Batch_RPT.getValueAt(x, 4)));
//								Integer yearlot = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 5);
//								String remarkslot = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 6);
//								BigDecimal amounthouse = new BigDecimal(String.format("%s", modelPCD_Block_Batch_RPT.getValueAt(x, 7)));
//								Integer yearhouse = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 8);
//								String remarkshouse = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 9);
//								String rpt_or = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 10);
//								String entity_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 13);
//								Integer seq_no = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 14);
//								String unit_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 15);
//								String pbl_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 16);
//								String code = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 17);
//								Integer rec_id = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 18);
//								String batch_no_rpt = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
//								String user_id = UserInfo.EmployeeCode;
//								System.out.println("Dumaan sa saving");
//
//								if(isSelected){
////									String SQL = "select sp_save_processing_cost_wamount_rpt('"+code+"', "+rec_id+", "+amountlot+", "+yearlot+", '"+remarkslot+"', "+
////											" "+amounthouse+", "+yearhouse+", '"+remarkshouse+"', '"+batch_no_rpt+"', '"+user_id+"', '"+rpt_or+"')";
//									String SQL = "select  from sp_save_processing_cost_wamount_rpt_v2('"+code+"', "+amountlot+", "+yearlot+", '"+remarkslot+"', "+amounthouse+", "+yearhouse+", "
//											+ "'"+remarkshouse+"', '"+batch_no_rpt+"', '"+user_id+"', '"+rpt_or+"', '"+lookupCoID.getValue()+"', '"+entity_id+"', '"+projcode+"', "+seq_no+")";
//									
//									pgSelect db = new pgSelect();
//									FncSystem.out("SQL", SQL);
//									db.select(SQL);
//
//									lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no_rpt));
//									
//								}
//							}
//							btnsave_w_amount.setEnabled(false);
//							btnrplf.setEnabled(true);
//							
//							JOptionPane.showMessageDialog(getContentPane(), "Transaction Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
//				}
//			}
		}
		
		if(actionCommand.equals("cancel_rpt")) {
			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
				modelPCD_Block_Batch_RPT.clear();
				scrollPCD_Block_Batch_RPT.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Block_Batch.getRowCount())));
				tblPCD_Block_Batch_RPT.packAll();
				//lookupCode_Block_Batch_rpt.setText("");
				//lblCode_Block_Batch_rpt.setText("[ ]");
				//cmbType_Block_Batch_rpt.setSelectedIndex(0);
				//lookupCode_Block_Batch_rpt.setEnabled(false);
				modelPCD_Block_Batch_RPT.setRowCount(0);
				lblBatch.setText("<html><b>Batch No:</html>");
				lookupCoID.setValue("");
				lookupProjectID.setValue("");
				txtCoName.setText("");
				txtProjectName.setText("");
				btnnew_rpt.setEnabled(false);
				btnsave_rpt.setEnabled(false);
				btnrplf.setEnabled(false);
				btnbatch_wout_rplf_rpt.setEnabled(false);
				btnsave_w_amount.setEnabled(false);
				pnlState(true, true, true, true, true);
			}
		}
	}
	private void clickTableColumn_rpt() {
		int column = tblPCD_Block_Batch_RPT.getSelectedColumn();
		int row = tblPCD_Block_Batch_RPT.getSelectedRow();
		System.out.println("column:"+column);
		System.out.println("row:"+row);
		
		if(column == 4) {
			BigDecimal rpt_lot = (BigDecimal) modelPCD_Block_Batch_RPT.getValueAt(row, 4);
			BigDecimal rpt_house = (BigDecimal) modelPCD_Block_Batch_RPT.getValueAt(row, 7);
			BigDecimal rpt_total = rpt_lot.add(rpt_house);
			modelPCD_Block_Batch_RPT.setValueAt(rpt_total, row, 11);
			
			System.out.println("rpt_lot: "+rpt_lot);
			System.out.println("rpt_house: "+rpt_house);
			System.out.println("rpt_total: "+rpt_total);
			
		}
		
	}
	
	private void computerpt_total() {
		/*int rw = tblPCD_Block_Batch_RPT.getModel().getRowCount();
		int row = tblPCD_Block_Batch_RPT.getSelectedRow();*/
		
		if (tblPCD_Block_Batch_RPT.getSelectedRows().length == 1) {
			int column = tblPCD_Block_Batch_RPT.getSelectedColumn();
			int row = tblPCD_Block_Batch_RPT.getSelectedRow();
			
			if(column == 4 || column == 7) {
				Double rpt_lot = Double.parseDouble(modelPCD_Block_Batch_RPT.getValueAt(row, 4).toString());
				Double rpt_house = Double.parseDouble(modelPCD_Block_Batch_RPT.getValueAt(row, 7).toString());
				BigDecimal bd_rpt_lot = new BigDecimal(rpt_lot);
				BigDecimal bd_rpt_house = new BigDecimal(rpt_house);
				
				BigDecimal rpt_total = bd_rpt_lot.add(bd_rpt_house);
				modelPCD_Block_Batch_RPT.setValueAt(rpt_total, row, 11);
				
				System.out.println("rpt_lot: "+rpt_lot);
				System.out.println("rpt_house: "+rpt_house);
				System.out.println("rpt_total: "+rpt_total);
			}
			tblPCD_Block_Batch_RPT.packAll();
		}
	}

	@Override
	//Display Batch no
	public void mouseClicked(MouseEvent e) {
		Integer index = tabCenter.getSelectedIndex();
		if(index == 1){
			if (e.getSource().equals(tblPCD_Batch)) {
				if(batch() == false) {
					String generateBatchNo = generateBatchNo();
					lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo));
					pnlState(false, true, false, false, false);
					btnTSave.setEnabled(true);
				}else {
					btnTSave.setEnabled(true);
				}
			}
		}

		if(index == 3){
			//			if (e.getSource().equals(tblPCD_Block_Batch)) {
			//				int row = tblPCD_Block_Batch.convertRowIndexToModel(tblPCD_Block_Batch.getSelectedRow());
			//				Boolean isSelected2 = (Boolean) modelPCD_Block_Batch.getValueAt(row, 0);
			//
			//				for(int x = 0; x < modelPCD_Block_Batch.getRowCount(); x++){
			//					Boolean isSelected = (Boolean) modelPCD_Block_Batch.getValueAt(x, 0);
			//					//entityid = (String) modelPCD_Block_Batch.getValueAt(x, 4);
			//
			//					if (isSelected) {
			//						if (batch() == false) {
			//							/*if (control()==true) {
			//								JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Particular was already existed to the account", "Tagging", JOptionPane.WARNING_MESSAGE);
			//								modelPCD_Batch.setValueAt(false, x, 0);
			//							} else {*/
			//							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
			//							pnlState(false, false, false, true);
			//							btnTSave.setEnabled(true);
			//							//}
			//						} else {
			//							//pnlState(false, true, false);
			//							btnTSave.setEnabled(true);
			//						}
			//					}
			//				}
			//			}
			if (e.getSource().equals(tblPCD_Block_Batch)) {
				if(batch() == false) {
					lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
					pnlState(false, false, false, true,false);
					btnTSave.setEnabled(true);
				}else {
					btnTSave.setEnabled(true);
				}
			}
		}
		//Added by Erick DCRF 2714
		if(index == 4){
			if(batch() == false) {
				lblBatch.setText(String.format("<html><b>Batch No: %s</html>", generateBatchNo()));
				pnlState(false, false, false,false, true);
				btnsave_w_amount.setEnabled(true);
				if(btnsave_rpt.isEnabled()) {
					btnsave_w_amount.setEnabled(false);
				}else {
					btnsave_w_amount.setEnabled(true);
				}
			}else {
				btnsave_w_amount.setEnabled(true);
			}
			
			if ((e.getClickCount() >= 1) ) {
				System.out.println("Dumaan sa click table");
				clickTableColumn_rpt();
			}
		}
	}

	private void insertBatchToTemp(String generatedBatch) {

		String emp_code = UserInfo.EmployeeCode;

		String SQL =
				"SELECT sp_save_batch_to_temp ('"+generatedBatch+"', '"+emp_code+"', 'A', now()::date)";

		FncSystem.out("Insert batch to temp table", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
	}

	private static String checkBatch(String batch_no) {

		String batch = "";

		String SQL = 
				"select\n" + 
						"batch_no\n" + 
						"from tmp_batch_no\n" + 
						"where batch_no = '"+batch_no+"' and status_id = 'A'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ( (String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {	
			}else {
				batch = (String) db.getResult()[0][0];
			}
		}else {
			batch = "";
		}

		return batch;
	}
	
	private boolean checkbatchtoconnect(String co_id, String batch) {
		
		Boolean x =  null;
		String sql = "select case when sum(pcost_amt) = 0.00 then true else false end from rf_processing_cost where batch_no = '"+batch+"' and co_id = '"+co_id+"' and status_id = 'A' and coalesce(rplf_no,'') = ''";
		
		System.out.println("checkbatchtoconnect: "+sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()) {
				x = (boolean) (db.getResult()[0][0]);
		}
		return x;
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

	//**MODIFIED BY Timothy John Lanuza 2022-05-05 : Sql Funchtion **//
	private static String SQL_Clients(String proj_id){

		String SQL = "select * from view_sql_clients('"+proj_id+"')";

		FncSystem.out("Clients", SQL);
		return SQL;

	}

	//----modified by JED VICEDO 2018-10-15 : DCRF no. 797 Put control in tagging particulars in PCOST and TCOST to avoid double tagging-----//
	private static String SQL_PCOST(String entity) {
		String sql =
				"SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
						"FROM mf_processing_cost_dl\n" + 
						"WHERE status_id = 'A'\n" + 
						"AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '"+entity+"' and status_id = 'A' \n" + 
						"and pcostid_dl not in ('050', '049', '040', '041', '042', '003', '004', '045', '218', '001', '002', '048', '221', '222', '223', '224', '225', '216'))\n" + 
						"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
						"ORDER BY pcostdtl_desc ASC";
		return sql;
	}

	//**ADDED BY JED 2019-03-20 : FOR LIST OF CODE FOR PCOST BLOCK-LOT RELATED**//
	private static String SQL_PCOST_BLOCK(String pbl_id) {
		String sql =
				"SELECT \n" + 
						"pcostdtl_id as \"Code\", \n" + 
						"trim(pcostdtl_desc) as \"Description\", \n" + 
						"pcostdtl_amt as \"Amt\", \n" + 
						"remarks as \"Remarks\"\n" + 
						"FROM mf_processing_cost_dl\n" + 
						"WHERE status_id = 'A'\n" + 
						"and pcostdtl_type in ('L','B')\n" + 
						"AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE pbl_id = '"+pbl_id+"' and status_id = 'A')\n" + 
						"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
						"ORDER BY pcostdtl_id ASC";
		
		FncSystem.out("CODE BLOCK LOT", sql);
		return sql;
	}

	//**ADDED BY JED 2019-03-15 : FOR LIST OF CODE FOR PCOST INDIVIDUAL**//
	private static String PCOST_BuyerInd(String entity, String pbl_id) {
		String sql =
				/*"SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
				"FROM mf_processing_cost_dl\n" + 
				"WHERE status_id = 'A'\n" + 
				"AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '"+entity+"' and status_id = 'A' \n" + 
				"and pcostid_dl not in ('050', '049', '040', '041', '042', '003', '004', '045', '218', '001', '002', '048', '221', '222', '223', '224', '225', '216'))\n" + 
				"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
				"ORDER BY pcostdtl_desc ASC";*/
				"SELECT * FROM get_pcost_sql('"+entity+"', '"+pbl_id+"')";
		return sql;
	}

	//-----modified by JED VICEDO 9-5-18 : filter clients with existing tag on particular no. 219 (for PMD)-----//
	private String PCOST_BuyerBatch() {
		if(checkEmpCode(user)==true || UserInfo.ADMIN){
			String sql =
					"SELECT distinct on (pcostdtl_id) pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
					"							FROM mf_processing_cost_dl\n" + 
					"							WHERE status_id = 'A'\n" + 
					"							AND pcostdtl_type = 'B'\n" + 
					//"							AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '\"+entity+\"' and status_id = 'A')\n" + 
					"							GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
					"							ORDER BY pcostdtl_id ASC";
			System.out.println("PCOST_BuyerBatch: "+sql);
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
							"AND pcostdtl_type = 'B'\n" +
							"--AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '\"entity\"' and status_id = 'A')\n" + 
							"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
							"ORDER BY pcostdtl_desc ASC";

			return sql;
		}
	}

	private String PCOST_BlockBatch() {

		String sql =
				"SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
						"FROM mf_processing_cost_dl\n" + 
						"WHERE status_id = 'A'\n" +
						"AND pcostdtl_type in ('L','B')\n" +
						//"AND pcostdtl_id NOT IN (SELECT pcostid_dl FROM rf_processing_cost WHERE entity_id = '"+entity+"' and status_id = 'A')\n" +
						"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
						"ORDER BY pcostdtl_desc ASC";
		return sql;
	}
	
	private String PCOST_BlockBatch_rpt() {

		String sql =
				"SELECT pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\", pcostdtl_amt as \"PCost Amount\", remarks as \"Remarks\"\n" + 
						"FROM mf_processing_cost_dl\n" + 
						"WHERE status_id = 'A'\n" +
						"AND pcostdtl_type in ('L','B') AND pcostdtl_id in ('215', '216' )\n" +
						"GROUP BY pcostdtl_id, pcostdtl_desc, pcostdtl_amt, remarks\n" + 
						"ORDER BY pcostdtl_desc ASC";
		return sql;
	}
	
	//---added by jed 2018-11-18 : list of clients no dcrf---//
	public static String sqlClients(String proj_id) {

		//**MODIFIED BY Timothy John Lanuza 2022-05-05 : Postgres Funchtion **//

		return "select * from view_sql_clients('"+proj_id+"')";


	}

	private static String SQL_UNIT(String proj_id) {

		//**MODIFIED BY Timothy John Lanuza 2022-05-05 : Postgres Funchtion **//

		return "select * from view_sql_units('"+proj_id+"')";
	}

	private String generateTAG(Object text) {
		return String.format("[ %s ]", text);
	}

	private static String SQL_BatchList() {
		String strSQL = "SELECT batch_no as \"Batch No\", sum(setup_amt) as \"Amount\"\n" +
				"FROM rf_processing_cost\n" + 
				"WHERE --(case when '"+UserInfo.EmployeeCode+"' = '900449' then true else created_by = '"+ UserInfo.EmployeeCode +"' end) \n" + 
				" COALESCE (rplf_no, '') = '' \n" + 
				"AND status_id = 'A' \n" +
				"AND batch_no != '' \n"+
				//"GROUP BY batch_no, date_created \n"+
				"GROUP BY batch_no \n"+
				//"order by date_created desc";
				"order by batch_no::INT desc";

		FncSystem.out("Batch No", strSQL);
		return strSQL;
	}
	
	private static String SQL_BatchList_rpt(String co_id) {
		String strSQL = "SELECT batch_no as \"Batch No\", sum(setup_amt) as \"Amount\"\n" +
				"FROM rf_processing_cost\n" + 
				"WHERE \n" + 
				" COALESCE (rplf_no, '') = '' \n" + 
				"AND status_id = 'A' AND pcostid_dl in ('215', '216') and co_id = '"+co_id+"' \n" +
				"GROUP BY batch_no, date_created \n"+
				"order by date_created desc";

		FncSystem.out("Batch No", strSQL);
		return strSQL;
	}
	private static String SQL_BatchList_rplf_mto(String co_id) {
		String strSQL = "select a.rplf_no, a.co_id, b.entity_name, a.pv_date\n"
				+ "from rf_pv_header a\n"
				+ "left join rf_entity b on a.entity_id1 = b.entity_id\n"
				+ "where a.status_id = 'P' and co_id = '"+co_id+"'\n"
				+ "and a.entity_id1 in ('5524735599', '6050082955')";

		FncSystem.out("Connect rplf list: ", strSQL);
		return strSQL;
	}

	private String[] getClass2() {
		return new String[] {
				"Payment",
				"Refund",
		};
	}

	private String[] getType() {
		return new String[] {
				"",
				"Merge",
				"Single",
		};
	}

	private static String SQL_REQUESTTYPE() {
		return "SELECT rplf_type_id as \"Type ID\", trim(rplf_type_desc) as \"Description\"  \n" +
				"FROM mf_rplf_type where status_id = 'A' and rplf_type_id in ('02','06','11','15','16')  " + //Edited by erick 2024-07-18 DCRF 3050-Added specific rplf_type
				"ORDER BY rplf_type_id ";		
	}
	private static String ControlNoLookUpValue(){
		return "SELECT TRIM(control_no) AS \"Control Number\",TRIM(pcost_desc) AS \"Pcost Description\" FROM rf_revolving_funds";
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

	private static Object[] getProcessingCostDetails_IND(String cost_desc, String entity_id, String batch) {
		String SQL = "SELECT a.pcostid_dl, a.tran_date, a.pcost_amt, get_employee_name(a.requested_by), a.requested_by, a.remarks, a.setup_amt, a.batch_no, proc_cost_id\n" + 
				"FROM rf_processing_cost a\n" + 
				"LEFT JOIN em_employee b on a.requested_by = b.emp_code\n" + 
				"left join mf_processing_cost_dl c on a.pcostid_dl = c.pcostdtl_id\n" + 
				"WHERE a.status_id = 'A' and c.pcostdtl_desc = '"+cost_desc+"' and a.entity_id = '"+entity_id+"' and a.batch_no = '"+batch+"' ";

		FncSystem.out("SQL for Processing cost details with RPLF (individual):", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static Object[] getProcessingCostDetails(String pbl_id, String cost_desc, String batch) {
		String SQL = 
				/*"SELECT a.pcostid_dl, a.tran_date, a.pcost_amt, get_client_name(b.entity_id), a.requested_by, a.remarks, a.setup_amt, a.batch_no\n" + 
					"FROM rf_processing_cost a\n" + 
					"LEFT JOIN em_employee b on a.requested_by = b.emp_code\n" +
					"WHERE rplf_no = '"+ rplf_no +"' and a.status_id = 'A' ";*/
				"select\n" + 
				"a.pcostid_dl,\n" + 
				"a.tran_date,\n" + 
				"a.pcost_amt,\n" + 
				"a.requested_by,\n" + 
				"get_employee_name(a.requested_by),\n" + 
				"a.remarks,\n" + 
				"a.setup_amt,\n" + 
				"b.pcostdtl_desc\n" +
				"from rf_processing_cost a\n" + 
				"inner join mf_processing_cost_dl b on a.pcostid_dl = b.pcostdtl_id\n" + 
				"where pbl_id = '"+pbl_id+"'\n" + 
				"and b.pcostdtl_desc = '"+cost_desc+"'\n" + 
				"and a.batch_no = '"+batch+"'\n" + 
				"and a.status_id = 'A'" ;

		FncSystem.out("SQL for Processing cost details:", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static Object[] getProcessingCostDetails1(String cost_desc, String requester) {
		String SQL = "select pcostdtl_id, pcostdtl_desc, pcostdtl_amt, get_employee_name('"+requester+"')\n" + 
				"from mf_processing_cost_dl\n" + 
				"where pcostdtl_desc ~* '"+cost_desc+"' and status_id = 'A' ";

		FncSystem.out("SQL for Processing cost(Block) without RPLF:", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static Object[] getProcessingCostDetails1_IND(String cost_desc, String requester) {
		String SQL = "select pcostdtl_id, pcostdtl_desc, pcostdtl_amt, get_employee_name('"+requester+"')\n" + 
				"from mf_processing_cost_dl\n" + 
				"where pcostdtl_desc ~* '"+cost_desc+"' and status_id = 'A' ";

		FncSystem.out("SQL for Processing Cost Details w/o RPLF:", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
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

	private static Object[] getProcessingCostDetails1(String cost_desc, String co_id, String proj_id) {//Added parameter co_id and projcode by Erick 2023-10-12 to get accurate data
		String SQL = "SELECT pcostdtl_id, pcostdtl_amt, remarks\n" + 
				"FROM mf_processing_cost_dl \n" + 
				"WHERE TRIM(pcostdtl_desc) ~* '"+cost_desc+"' and status_id = 'A' and co_id = '"+co_id+"' and proj_id = '"+proj_id+"' ";

		FncSystem.out("SQL for Processing Cost Details w/o RPLF:", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static String rplfNo() {
		String rplf = "";

		String SQL = "select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '02' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {rplf = "000000001";}
			else {rplf = (String) db.getResult()[0][0]; }

		}else{
			rplf = "000000001";
		}

		return rplf;
	}

	private static Object[] reg_amount1(String pbl) {
		String SQL = "SELECT get_lra_amount_v2('"+pbl+"')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display amount", SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static Object[] reg_amount3(String pbl, String proj_id) {
		String SQL = "SELECT get_lra_amount_v3('"+pbl+"', '"+proj_id+"')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display amount", SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static Object[] reg_amount2(String entity, String pbl, String buyertype) {
		String SQL = "SELECT get_lra_amount('"+entity+"', '"+pbl+"', '"+buyertype+"');";

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display amount", SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static String generateBatchNo() {
		//ADDED BY TIM 2022-07-20 : GENERATE NEW BATCHING FOR JSYSTEM ONLY
		String strSQL = "SELECT to_char(COALESCE(MAX(NULLIF(trim(batch_no), '')::INT), 0) + 1, 'FM0000000000') FROM rf_processing_cost WHERE NULLIF(TRIM(batch_no), 'null') IS not null";

		//FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	private static String generateBatchNo_v2() {//**ADDED BY JED 2019-09-03 : GENERATE NEW BATCH TO AVOID SAME BATCHING**//
		String strSQL = "SELECT to_char(COALESCE(MAX(batch_no::INT), 0) + 1, 'FM0000000000') FROM tmp_batch_no WHERE status_id = 'A';";

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
		if(data != null) {
			String batch = (String) data[0];
			lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch));
		}
	}
	
	private void getBatchList_rpt() {

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Units", SQL_BatchList_rpt(lookupCoID.getValue()), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();
		System.out.println("data: "+data);
		if(data != null) {
			String batch = (String) data[0];
			lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch));
			
			btnnew_rpt.setEnabled(false);
			btnsave_rpt.setEnabled(false);
			btnbatch_wout_rplf_rpt.setEnabled(true);
			
			System.out.println("checkbatchtoconnect: "+checkbatchtoconnect(lookupCoID.getValue(), batch));
			if(checkbatchtoconnect(lookupCoID.getValue(), batch)) {
				btnsave_w_amount.setEnabled(true);
			}else {
				btnrplf.setEnabled(true);
			}
			
		}else {
			btnnew_rpt.setEnabled(true);
			btnsave_rpt.setEnabled(false);
			btnrplf.setEnabled(false);
			btnbatch_wout_rplf_rpt.setEnabled(true);
			btnsave_w_amount.setEnabled(false);
		}
		
	}
	
	private void getBatchList_rplf_mto() {

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Units", SQL_BatchList_rplf_mto(lookupCoID.getValue()), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();
		if(data != null) {
			String rplf = (String) data[0];
			String co_id = (String) data[1];
			String batch_no_rpt = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
			//updatepcost sp_save_processing_cost_connectrplf_rpt
			if( JOptionPane.showConfirmDialog(getContentPane(),connectrplfPopUp(rplf, batch_no_rpt), "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ) {
				
				String sql = " select sp_save_processing_cost_connectrplf_rpt ('"+rplf+"', '"+co_id+"', '"+batch_no_rpt+"')";
				pgSelect db = new pgSelect();
				FncSystem.out("SQL", sql);
				db.select(sql);
				
				JOptionPane.showMessageDialog(getContentPane(), "Done!","Information", JOptionPane.INFORMATION_MESSAGE);
				
			}
		}
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

	private JPanel displayTableNavigation() {

		btnRemoveRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnRemoveRow);

		return pnl;
	}

	private JPanel displayTableNavigation_BlockLot() {
		btnRemoveRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnRemoveRow);

		return pnl;
	}

	private void updatePCD() {
		scrollPCDTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblPCD.getRowCount())));
		tblPCD.packAll();
		if (tblPCD.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
			tblPCD.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		for (int row = 0; row < tblPCD.getRowCount(); row++) {
			((DefaultListModel) rowHeaderPCD.getModel()).addElement(row + 1);
		}
	}

	private void updatePCD_BlockLot() {
		scrollPCDTotal_BlockLot.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblPCD_BlockLot.getRowCount())));
		tblPCD_BlockLot.packAll();
		if (tblPCD_BlockLot.getColumnModel().getColumn(1).getPreferredWidth() >= 200) {
			tblPCD_BlockLot.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		for (int row = 0; row < tblPCD_BlockLot.getRowCount(); row++) {
			((DefaultListModel) rowHeaderPCD_BlockLot.getModel()).addElement(row + 1);
		}
	}

	private boolean validateSaving() {
		if (tabCenter.getSelectedIndex() == 0) {
			if (lookupClient.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Client", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupCode.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Code", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if (lookupRequestType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Request Type", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailer.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailerType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer Type", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (tabCenter.getSelectedIndex() == 1) {
			if (lookupCode_Batch.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Code", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupRequestType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Request Type", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailer.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupAvailerType.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Availer Type", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (tabCenter.getSelectedIndex() == 2) {
			if (lookupProject.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Code", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupUnit.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Code", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (lookupCode_BlockLot.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Code", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;

	}

	private boolean withZeroAmountIndividual(){
		Boolean with_zero = false;

		for(int x= 0; x<modelPCD.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPCD.getValueAt(x, 0);
			if(isSelected){
				BigDecimal amount = (BigDecimal) modelPCD.getValueAt(x, 3);
				if(amount.compareTo(new BigDecimal("0.00")) == 0){
					with_zero = true;
				}
			}
		}
		return with_zero;
	}

	private boolean withZeroAmtBlkLot(){
		Boolean with_zero = false;

		for(int x= 0; x<modelPCD_BlockLot.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPCD_BlockLot.getValueAt(x, 0);
			if(isSelected){
				BigDecimal amount = (BigDecimal) modelPCD_BlockLot.getValueAt(x, 3);
				if(amount.compareTo(new BigDecimal("0.00")) == 0){
					with_zero = true;
				}
			}
		}
		return with_zero;
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

	private void checkAllUnitList(){//
		
		if(tabCenter.getSelectedIndex() == 4) {
			int rw = tblPCD_Block_Batch_RPT.getModel().getRowCount();	
			int x = 0;

			while (x < rw) {			

				String unit = "";

				try { unit	= tblPCD_Block_Batch_RPT.getValueAt(x,1).toString().toUpperCase();}
				catch (NullPointerException e) { unit	= ""; }

				String unit_name	= txtBlockSearch_rpt.getText().trim().toUpperCase();	
				Boolean	start		= unit.startsWith(unit_name);

				if (start==true) {
					tblPCD_Block_Batch_RPT.setRowSelectionInterval(x, x); 
					tblPCD_Block_Batch_RPT.changeSelection(x, 1, false, false);				
					break;			
				}
				else {
					tblPCD_Block_Batch_RPT.setRowSelectionInterval(0, 0); 
					tblPCD_Block_Batch_RPT.changeSelection(0, 1, false, false);					
				}

				x++;

			}	
			
		}else {
			int rw = tblPCD_Block_Batch.getModel().getRowCount();	
			int x = 0;

			while (x < rw) {			

				String unit = "";

				try { unit	= tblPCD_Block_Batch.getValueAt(x,8).toString().toUpperCase();}
				catch (NullPointerException e) { unit	= ""; }

				String unit_name	= txtBlockSearch.getText().trim().toUpperCase();	
				//Boolean	match		= name.indexOf(acct_name)>0;
				Boolean	start		= unit.startsWith(unit_name);
				//Boolean	end			= name.endsWith(acct_name);

				if (start==true) {
					tblPCD_Block_Batch.setRowSelectionInterval(x, x); 
					tblPCD_Block_Batch.changeSelection(x, 8, false, false);				
					break;			
				}
				else {
					tblPCD_Block_Batch.setRowSelectionInterval(0, 0); 
					tblPCD_Block_Batch.changeSelection(0, 8, false, false);					
				}

				x++;

			}	
		}

			
	}

	private boolean batch() {

		boolean x = false;
		String batch = "";

		String strSQL = "select batch_no from rf_processing_cost where status_id = 'A' and batch_no = '"+lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "")+"' and rplf_no is not null /*and created_by = '"+ UserInfo.EmployeeCode +"'*/"; //COMMENTED BY LESTER 2023-03-07 to enable tagging of batch number

		System.out.println("Dumaan dito!!!!!!!");

		FncSystem.out("Batch sql", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			batch = (String) db.getResult()[0][0];
		}

		if (batch.equals(null) || batch.isEmpty()) {
			x=false;
		} else {x=true;}
		System.out.println(x);
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

		if (tabCenter.getSelectedIndex() == 0) {
			if (validateSaving()) {

				int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {

					//String rplf_no = rplfNo();
				//	maraming_entity = "";
					for(int x = 0; x < tblPCD.getRowCount(); x++){
						
						String client_name = (String) clientDetails[1];
						Boolean isSelected = (Boolean) modelPCD.getValueAt(x, 0);
						Date trans_date = (Date) modelPCD.getValueAt(x, 2);
						BigDecimal amount = (BigDecimal) modelPCD.getValueAt(x, 3);
						String remarks = (String) modelPCD.getValueAt(x, 4);
					//	remarks = remarks+"\n"+maraming_entity;
						String desc = (String) modelPCD.getValueAt(x, 1);
						String entity_id = (String) clientDetails[0];
						String unit_id = (String) clientDetails[14];
						String projcode = (String) clientDetails[4];
						//String pbl_id = (String) clientDetails[4];
						String pbl_id2 = (String) pbl_id;
						Integer seq_no = (Integer) clientDetails[5];
						String req_id = (String) UserInfo.EmployeeCode;
						String emp_code = (String) UserInfo.EmployeeCode;
						String type = (String) cmbType.getSelectedItem();
						String client_id = (String) lookupClient.getText();
						String request_type = (String) lookupRequestType.getText();
						String availer = (String) lookupAvailer.getText();
						String availer_type = (String) lookupAvailerType.getText();
						//String batch_no = batchNo();
						String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

						if(isSelected){
							
							
							String costdesc = (String) modelPCD.getValueAt(x, 1);
							Object[] particulars = getProcessingCostDetails1(costdesc, lookupCoID.getValue(), lookupProjectID.getValue());
							//String costid = (String) particulars[0]; //Comment by Erick 2023-07-03 
							//BigDecimal setup_amount = (BigDecimal) particulars[1]; //Comment by Erick 2023-07-03
							
							//Added by Erick 2023-07-03
							//String costid = (String) lookupCode.getValue(); //Edited by erick 2023-10-12 due to conflict in getting the code during autotagging or tagging more than 1 code.
							String costid;
//							//Comment by erick 2023-10-16 due to wrong code is inserted when tagging individual client with more than one code.
//							if (lookupCode.getValue().equals("109") || lookupCode.getValue().equals("210") || lookupCode.getValue().equals("105") || lookupCode.getValue().equals("108")) {
//								 costid = (String) particulars[0];
//							}else {
//								 costid = (String) lookupCode.getValue();
//							}
							
							//Added b y erick 2023-10-16 for tagging more than one code.
							costid = (String) particulars[0];
							Double db_setup_amount = (Double.valueOf(txtAmount.getText().trim().replace(",","")).doubleValue());
							BigDecimal setup_amount = new BigDecimal (db_setup_amount);
							System.out.println("costid: "+lookupCode.getValue());
							System.out.println("setupamount: "+txtAmount.getText());
							
							
						//	maraming_entity += client_name+"\n";
							String SQL = "SELECT sp_save_processing_cost_new_v2('"+entity_id+"', '"+projcode+"', '"+pbl_id2+"', '"+costid+"', '"+trans_date+"'::date,"
									+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', '"+batch_no+"', NULLIF('"+unit_id+"','null'),"
									+ "'"+desc+"', '"+type+"', "+seq_no+", '"+client_id+"', '"+request_type+"', '"+availer+"', '"+availer_type+"')";

							pgSelect db = new pgSelect(); 
							FncSystem.out("SQL", SQL);
							db.select(SQL);
							
				
							
							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
							
						}
						//TAGGING OF 178 - UNCOMMENT Tim
						
							/*String costdesc = (String) modelPCD.getValueAt(x, 1);
							Object[] particulars = getProcessingCostDetails1(costdesc);
							String costid = (String) particulars[0];
					
							if(costid.equals("178")) {
				
							String SQL = "SELECT sp_save_processing_cost_new_v2('"+entity_id+"', '"+projcode+"', '"+pbl_id2+"', '"+213+"', '"+trans_date+"'::date,"
									+ ""+100.00+", "+100.00+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', '"+batch_no+"', NULLIF('"+unit_id+"','null'),"
									+ "'"+desc+"', '"+type+"', "+seq_no+", '"+client_id+"', '"+request_type+"', '"+availer+"', '"+availer_type+"')";

							pgSelect db = new pgSelect();
							FncSystem.out("SQL", SQL);
							db.select(SQL);
						
							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
							}*/
					}

					JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF", JOptionPane.INFORMATION_MESSAGE);
					lblBatch.setText("<html><b>Batch No:</html>");

					modelPCD.setRowCount(0);
					pnlState(true, true, true, true,true);
					tblPCD.setEnabled(true);
					lookupCode.setEnabled(true);

					Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
					optionPaneWindow.dispose();

					Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
					if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
						optionPaneWindowMain.getFocusOwner();
						lookupClient.setText("");
						refreshBuyer();


					}
				}
			}
		}

		if (tabCenter.getSelectedIndex() == 1) {

			int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				String batchno = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
				String emp_code = (String) UserInfo.EmployeeCode;
				String request_type = (String) lookupRequestType.getText();
				String availer = (String) lookupAvailer.getText();
				String availer_type = (String) lookupAvailerType.getText();
				String control_no = (String) lookupControlNo.getText();

				String SQL = "SELECT sp_save_processing_cost_rplf_jervin('"+emp_code+"', '"+batchno+"', '"+request_type+"', '"+availer+"', '"+availer_type+"','"+control_no+"')";
				
				
				
				pgSelect db = new pgSelect();
				FncSystem.out("SQL", SQL);
				db.select(SQL);
				//}
				//}
				JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF", JOptionPane.INFORMATION_MESSAGE);
				lblBatch.setText("<html><b>Batch No:</html>");

				btnTSave.setActionCommand("Save");
				modelPCD_Batch.setRowCount(0);
				//displayPCostClient(lookupCode_Batch.getValue());  //--removed by jed 2018-10-16 : DCRF no. 797 put control on tagging particulars in PCOST to avoid double tagging--//
				pnlState(true, true, true, true, true);

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

		if (tabCenter.getSelectedIndex() == 2) {
			//if (validateSaving()) {

			int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you all fields correct? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				//lookupProject.setText(" ");
				//refreshBlockLot();
			}else {
				//String rplf_no = rplfNo();
			//	maraming_entity = "";
				for(int x = 0; x < tblPCD_BlockLot.getRowCount(); x++){
					String client_name = (String) clientDetails[1];
					String cost_desc = (String) modelPCD_BlockLot.getValueAt(x, 1);
					Object[] particulars = getProcessingCostDetails1(cost_desc, lookupCoID.getValue(), lookupProjectID.getValue());
					Boolean isSelected = (Boolean) modelPCD_BlockLot.getValueAt(x, 0);
					Date trans_date = (Date) modelPCD_BlockLot.getValueAt(x, 2);
					BigDecimal amount = (BigDecimal) modelPCD_BlockLot.getValueAt(x, 3);
					String remarks = (String) txtRemarks_BlockLot.getText();
				//	remarks = remarks+"\n"+maraming_entity;
					String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
					String desc = (String) modelPCD_BlockLot.getValueAt(x, 1);
					String pbl_id = (String) lookupUnit.getValue();
					String costid = (String) particulars[0];
					BigDecimal setup_amount = (BigDecimal) particulars[1];
					String req_id = (String) txtRequestedBy_BlockLot.getText();
					String emp_code = (String) UserInfo.EmployeeCode;
					String type = (String) cmbType_BlockLot.getSelectedItem();
					String request_type = (String) lookupRequestType.getText();
					String availer = (String) lookupAvailer.getText();
					String availer_type = (String) lookupAvailerType.getText();
					String projcode = lookupProject.getValue(); 
					//					String control_no = (String) lookupControlNo.getText();
					//String batch_no = batchNo();



					if(isSelected){
						//maraming_entity += client_name+"\n";
						String SQL = "SELECT sp_save_processing_cost('"+entity2+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
								+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', '"+batch_no+"', NULLIF('"+unitid+"','null'),"
								+ "'"+desc+"', '"+type+"', "+seqno+", '"+entity2+"', '"+request_type+"', '"+availer+"', '"+availer_type+"')";

						//						String SQL = "SELECT sp_save_processing_cost_jervin('"+entity2+"', '015', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
						//								+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', '"+batch_no+"', NULLIF('"+unitid+"','null'),"
						//								+ "'"+desc+"', '"+type+"', "+seqno+", '"+entity2+"', '"+request_type+"', '"+availer+"', '"+availer_type+"','"+control_no+"')";
						pgSelect db = new pgSelect();
						FncSystem.out("dumaaaananananana", SQL);
						db.select(SQL);
						System.out.println("jervin:"+SQL);
					}
				}

				JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Create RPLF", JOptionPane.INFORMATION_MESSAGE);
				lblBatch.setText("<html><b>Batch No:</html>");

				Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
				optionPaneWindow.dispose();

				Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
				if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
					optionPaneWindowMain.getFocusOwner();
					lookupProject.setText("");
					refreshBlockLot();


				}
			}
		}

		if (tabCenter.getSelectedIndex() == 3) {

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

				btnORSave.setActionCommand("Save OR");
				btnTSave.setActionCommand("Save");
				modelPCD_Block_Batch.setRowCount(0);
				//displayPCostClient(lookupCode_Batch.getValue());  //--removed by jed 2018-10-16 : DCRF no. 797 put control on tagging particulars in PCOST to avoid double tagging--//
				pnlState(true, true, true, true,true);

				Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateRPLF);
				optionPaneWindow.dispose();

				Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
				if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
					optionPaneWindowMain.getFocusOwner();
					lookupCode_Block_Batch.setText("");
					refreshBatch();
					btnDRF.setEnabled(false);

				}
			}
		}
		
		if (tabCenter.getSelectedIndex() == 4) {
			
		}
	}
	//}

	private void insertAudit_trail(String activity, String entity_id, String pbl_id, String remarks){

		pgUpdate db = new pgUpdate();

		String user_code	= UserInfo.EmployeeCode;	

		String sqlDetail = 
				"INSERT INTO mf_audit_trail(\n" + 
						"	              system_id, activity, user_code, date_upd, entity_id, \n" + 
						"	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n" + 
						"	      VALUES ('PCOST', '"+activity+"', '"+user_code+"', NOW(), '"+entity_id+"', \n" + 
						"	              NULL, NULL, '"+pbl_id+"', NULL, NULL, '"+remarks+"');" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
		db.commit();


	}

	private void save(){
		if (tabCenter.getSelectedIndex() == 1) {
			System.out.println("hello ? batch");
			if(lookupCode_Batch.getText().equals(null) || lookupCode_Batch.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a particular.", "Save", JOptionPane.ERROR_MESSAGE);	
			}else{

				BigDecimal amount_to_save = (BigDecimal) txtAmount_Batch.getValued();

				if(amount_to_save.compareTo(new BigDecimal("0.00")) == 0){
					JOptionPane.showMessageDialog(getContentPane(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

						String checkBatch = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
						if(checkBatch(checkBatch).equals("")) {//**ADDED BY JED 2019-09-03 TO AVOID SAME BATCHING**//
							int selected_row = tblPCD_Batch.convertRowIndexToModel(tblPCD_Batch.getSelectedRow());

							String rplf_no = "";
							//maraming_entity = "";
							
							Boolean isSelected1 = (Boolean) modelPCD_Batch.getValueAt(selected_row, 0);
							if (isSelected1) {
								
							for(int x = 0; x < modelPCD_Batch.getRowCount(); x++){
								String client_name = (String) modelPCD_Batch.getValueAt(x, 2);
								Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
								Date trans_date = (Date) dateSched_Batch.getDate();
								BigDecimal amount1 = (BigDecimal) txtAmount_Batch.getValued();
								String remarks = (String) txtRemarks_Batch.getText();
							//	remarks = remarks+"\n"+maraming_entity;
								//String remarks = "";
								//String desc = (String) lblCode_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
								String entity_id = (String) modelPCD_Batch.getValueAt(x, 1);
								String unit_id = (String) modelPCD_Batch.getValueAt(x, 9);
								String projcode = (String) modelPCD_Batch.getValueAt(x, 7);
								String pbl_id = (String) modelPCD_Batch.getValueAt(x, 4);
								Integer seq_no = (Integer) modelPCD_Batch.getValueAt(x, 5);
								String costid = (String) lookupCode_Batch.getValue();
								BigDecimal setup_amount = (BigDecimal) txtAmount_Batch.getValued();
								String req_id = (String) txtRequestedBy_Batch.getText();
								String emp_code = (String) UserInfo.EmployeeCode;
								String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

								if(isSelected){
									//maraming_entity += client_name+"\n";
									
									String SQL = "SELECT sp_save_processing_cost_woutrplf('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
											+ ""+amount1+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
											+ ""+seq_no+", '"+rplf_no+"')";

									pgSelect db = new pgSelect();
									FncSystem.out("SQL", SQL);
									db.select(SQL);

									lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
									
								}
							}
							}
							//System.out.println("Entity ID: \n"+maraming_entity);

							JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
							lblBatch.setText("<html><b>Batch No:</html>");
							/*modelPCD_Batch.setRowCount(0);
								displayPCostClient();*/

							//---added by jed 2018-10-16 : DCRF no. 797 put control in tagging of particulars to avoid double tagging---//
							modelPCD_Batch.clear();
							scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
							tblPCD_Batch.packAll();

							//---added by jed 2018-8-6 due to multiple saving
							btnTSave.setEnabled(false); 
							btnDRF.setEnabled(true);
							pnlState(true, true, true, true, true);
							//displayPCostClient(null);
							lookupCode_Batch.setText("");
							lblCode_Batch.setText("[ ]");
							lblSetupAmount1_Batch.setText("0.00");
							lblBalanceAmount_Batch.setText("0.00");
							txtRemarks_Batch.setText("");
							txtAmount_Batch.setValue(0.00);
							txtRequestedBy_Batch.setText("");
							lblReqBy1_Batch.setText("[ ]");
						}else{

							if(JOptionPane.showConfirmDialog(getContentPane(), "Batch no. " + checkBatch(checkBatch) + " is already used." +
									"\n" + "Generate new batch?", "Confirmation", 
									JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

								String new_batch = generateBatchNo_v2();

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));

							}
						}
					}
				}	
			}
		}

		if (tabCenter.getSelectedIndex() == 3) {
			if(lookupCode_Block_Batch.getText().equals(null) || lookupCode_Block_Batch.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a particular.", "Save", JOptionPane.ERROR_MESSAGE);	
			}else{

				BigDecimal amount_to_save = (BigDecimal) txtAmount_Block_Batch.getValued();

				if(amount_to_save.compareTo(new BigDecimal("0.00")) == 0){
					JOptionPane.showMessageDialog(getContentPane(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

						String checkBatch = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
						if(checkBatch(checkBatch).equals("")) {//**ADDED BY JED 2019-09-03 TO AVOID SAME BATCHING**//

							String rplf_no = "";
						//	maraming_entity = "";
							for(int x = 0; x < modelPCD_Block_Batch.getRowCount(); x++){
								String client_name = (String) modelPCD_Block_Batch.getValueAt(x, 8);
								Boolean isSelected = (Boolean) modelPCD_Block_Batch.getValueAt(x, 0);
								Date trans_date = (Date) dateSched_Block_Batch.getDate();
								BigDecimal amount1 = (BigDecimal) txtAmount_Block_Batch.getValued();
								String remarks = (String) txtRemarks_Block_Batch.getText();
						//		remarks = remarks+"\n"+maraming_entity;
								String entity_id = (String) modelPCD_Block_Batch.getValueAt(x, 4);
								String unit_id = (String) modelPCD_Block_Batch.getValueAt(x, 7);
								String projcode = (String) modelPCD_Block_Batch.getValueAt(x, 5);
								String pbl_id = (String) modelPCD_Block_Batch.getValueAt(x, 1);
								Integer seq_no = (Integer) modelPCD_Block_Batch.getValueAt(x, 6);
								String costid = (String) lookupCode_Block_Batch.getValue();
								BigDecimal setup_amount = (BigDecimal) txtAmount_Block_Batch.getValued(); //new BigDecimal (txtAmount_Batch.getText());
								String req_id = (String) txtRequestedBy_Block_Batch.getText();
								String emp_code = (String) UserInfo.EmployeeCode;
								String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

								if(isSelected){
									//maraming_entity += client_name+"\n";
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
							lblBatch.setText("<html><b>Batch No:</html>");

							modelPCD_Block_Batch.clear();
							scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
							tblPCD_Block_Batch.packAll();

							btnTSave.setEnabled(false); 
							btnDRF.setEnabled(true);
							pnlState(true, true, true, true, true);
							lookupCode_Block_Batch.setText("");
							lblCode_Block_Batch.setText("[ ]");
							lblSetupAmount1_Block_Batch.setText("0.00");
							lblBalanceAmount_Block_Batch.setText("0.00");
							txtRemarks_Block_Batch.setText("");
							txtAmount_Block_Batch.setValue(0.00);
							txtRequestedBy_Block_Batch.setText("");
							lblReqBy1_Block_Batch.setText("[ ]");
						}else {

							if(JOptionPane.showConfirmDialog(getContentPane(), "Batch no. " + checkBatch(checkBatch) + " is already used." +
									"\n" + "Generate new batch?", "Confirmation", 
									JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

								String new_batch = generateBatchNo_v2();

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));

							}
						}
					}
				}	
			}
		}
		//Save Tab4
		if(tabCenter.getSelectedIndex() == 4 ) {
//			if(lookupCode_Block_Batch_rpt.getText().equals(null) || lookupCode_Block_Batch_rpt.getText().equals("")) {
//				JOptionPane.showMessageDialog(getContentPane(), "Please select a particular.", "Save", JOptionPane.ERROR_MESSAGE);	
//			}else{
				if(JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

					String checkBatch = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
					if(checkBatch(checkBatch).equals("")) {

						String rplf_no = "";
						for(int x = 0; x < modelPCD_Block_Batch_RPT.getRowCount(); x++){
							
							Boolean isSelected = (Boolean) modelPCD_Block_Batch_RPT.getValueAt(x, 0);
							String client_name = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 1);
							String projcode = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 2);
							BigDecimal amountlot = (BigDecimal) modelPCD_Block_Batch_RPT.getValueAt(x, 4);
							String yearlot = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 5);
							String remarkslot = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 6);
							BigDecimal amounthouse = (BigDecimal) modelPCD_Block_Batch_RPT.getValueAt(x, 7);
							String yearhouse = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 8);
							String remarkshouse = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 9);
							String entity_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 12);
							Integer seq_no = (Integer) modelPCD_Block_Batch_RPT.getValueAt(x, 13);
							String unit_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 14);
							String pbl_id = (String) modelPCD_Block_Batch_RPT.getValueAt(x, 15);
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
							//Date trans_date = (Date) dateSched_Block_Batch.getDate();
//							String costid = (String) lookupCode_Block_Batch.getValue();
//							BigDecimal setup_amount = (BigDecimal) txtAmount_Block_Batch.getValued(); //new BigDecimal (txtAmount_Batch.getText());
							String req_id = UserInfo.EmployeeCode;
//							String emp_code = (String) UserInfo.EmployeeCode;
							System.out.println("Dumaan sa saving");

							if(isSelected){
								String SQL = "SELECT sp_save_processing_cost_woutrplf_rpt('"+entity_id+"', '"+projcode+"', '"+pbl_id+"',"
										+ "'"+req_id+"',  '"+req_id+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ ""+seq_no+", '"+rplf_no+"', '"+lookupCoID.getValue()+"')";

								pgSelect db = new pgSelect();
								FncSystem.out("SQL", SQL);
								db.select(SQL);

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
							}
						}

						JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
						lblBatch.setText("<html><b>Batch No:</html>");

						modelPCD_Block_Batch.clear();
						scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
						tblPCD_Block_Batch.packAll();

						btnTSave.setEnabled(false); 
						btnDRF.setEnabled(true);
						pnlState(true, true, true, true, true);
//						lookupCode_Block_Batch.setText("");
//						lblCode_Block_Batch.setText("[ ]");
//						lblSetupAmount1_Block_Batch.setText("0.00");
//						lblBalanceAmount_Block_Batch.setText("0.00");
//						txtRemarks_Block_Batch.setText("");
//						txtAmount_Block_Batch.setValue(0.00);
//						txtRequestedBy_Block_Batch.setText("");
//						lblReqBy1_Block_Batch.setText("[ ]");
					}else {

						if(JOptionPane.showConfirmDialog(getContentPane(), "Batch no. " + checkBatch(checkBatch) + " is already used." +
								"\n" + "Generate new batch?", "Confirmation", 
								JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

							String new_batch = generateBatchNo_v2();

							lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));

						}
					}
				}
			//}
		}
	}

	private void save2(){
		if (tabCenter.getSelectedIndex() == 1) {
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
					//	maraming_entity = "";
						for(int x = 0; x < modelPCD_Batch.getRowCount(); x++){
							String client_name = (String) modelPCD_Batch.getValueAt(x, 2);
							Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Batch.getDate();
							BigDecimal amount1 = (BigDecimal) txtAmount_Batch.getValued();
							String remarks = (String) txtRemarks_Batch.getText();
						//	remarks = remarks+"\n"+maraming_entity;
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
							//String type = (String) cmbType.getSelectedItem();
							//String client_id = (String) lookupClient.getText();
							//String request_type = (String) lookupRequestType.getText();
							//String availer = (String) lookupAvailer.getText();
							//String availer_type = (String) lookupAvailerType.getText();
							//String batch_no = batchNo();
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

							if(isSelected){
							//	maraming_entity += client_name+"\n";
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
						pnlState(true, true, true, true, true);
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

		if (tabCenter.getSelectedIndex() == 3) {
			if(lookupCode_Block_Batch.getText().equals(null) || lookupCode_Block_Batch.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select a particular.", "Save", JOptionPane.ERROR_MESSAGE);	
			}else{

				BigDecimal amount_to_save = (BigDecimal) txtAmount_Block_Batch.getValued();

				if(amount_to_save.compareTo(new BigDecimal("0.00")) == 0){
					JOptionPane.showMessageDialog(getContentPane(), "Amount cannot be zero", "Save", JOptionPane.WARNING_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(getContentPane(), "Are all fields correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

						String rplf_no = "";
						//maraming_entity = "";
						for(int x = 0; x < modelPCD_Block_Batch.getRowCount(); x++){
							String client_name = (String) modelPCD_Block_Batch.getValueAt(x, 8);
							Boolean isSelected = (Boolean) modelPCD_Block_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Block_Batch.getDate();
							BigDecimal amount1 = (BigDecimal) txtAmount_Block_Batch.getValued();
							String remarks = (String) txtRemarks_Block_Batch.getText();
					//		remarks = remarks+"\n"+maraming_entity;
							String entity_id = (String) modelPCD_Block_Batch.getValueAt(x, 4);
							String unit_id = (String) modelPCD_Block_Batch.getValueAt(x, 7);
							String projcode = (String) modelPCD_Block_Batch.getValueAt(x, 5);
							String pbl_id = (String) modelPCD_Block_Batch.getValueAt(x, 1);
							Integer seq_no = (Integer) modelPCD_Block_Batch.getValueAt(x, 6);
							String costid = (String) lookupCode_Block_Batch.getValue();
							BigDecimal setup_amount = (BigDecimal) txtAmount_Block_Batch.getValued(); //new BigDecimal (txtAmount_Batch.getText());
							String req_id = (String) txtRequestedBy_Block_Batch.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

							if(isSelected){
								//maraming_entity += client_name+"\n";
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

						modelPCD_Block_Batch.clear();
						scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
						tblPCD_Block_Batch.packAll();

						btnTSave.setEnabled(false); 
						btnDRF.setEnabled(true);
						pnlState(true, true, true, true, true);
						lookupCode_Block_Batch.setText("");
						lblCode_Block_Batch.setText("[ ]");
						lblSetupAmount1_Block_Batch.setText("0.00");
						lblBalanceAmount_Block_Batch.setText("0.00");
						txtRemarks_Block_Batch.setText("");
						txtAmount_Block_Batch.setValue(0.00);
						txtRequestedBy_Block_Batch.setText("");
						lblReqBy1_Block_Batch.setText("[ ]");
					}
				}	
			}
		}
	}

	private void saveOR(){
		if (tabCenter.getSelectedIndex() == 1) {
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
						//maraming_entity = "";
						for(int x = 0; x < tblPCD_Batch.getRowCount(); x++){
							String client_name = (String) modelPCD_Batch.getValueAt(x, 2);
							Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Batch.getDate();
							BigDecimal amount = new BigDecimal (txtAmount_Batch.getValue().toString());
							String remarks = (String) txtRemarks_Batch.getText();
							//remarks = remarks+"\n"+maraming_entity;
							String desc = (String) lblCode_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
							String entity_id = (String) modelPCD_Batch.getValueAt(x, 1);
							String unit_id = (String) modelPCD_Batch.getValueAt(x, 9);
							String projcode = (String) modelPCD_Batch.getValueAt(x, 7);
							String pbl_id = (String) modelPCD_Batch.getValueAt(x, 4);
							Integer seq_no = (Integer) modelPCD_Batch.getValueAt(x, 5);
							String costid = (String) lookupCode_Batch.getValue();
							BigDecimal setup_amount = new BigDecimal (txtAmount_Batch.getValue().toString());
							String req_id = (String) txtRequestedBy_Batch.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							//String type = (String) cmbType.getSelectedItem();
							//String client_id = (String) lookupClient.getText();
							//String request_type = (String) lookupRequestType.getText();
							//String availer = (String) lookupAvailer.getText();
							//String availer_type = (String) lookupAvailerType.getText();
							//String batch_no = batchNo();
							String or = (String) txtORNo.getText();
							String yr = (String) txtYear.getText();
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

							if(isSelected){

								/*String SQL = "SELECT sp_save_processing_cost_woutrplf_withor('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
										+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ "'"+desc+"', '"+type+"', "+seq_no+", '"+client_id+"', '"+request_type+"', '"+availer+"', '"+availer_type+"', '"+rplf_no+"', "+yr+", '"+or+"')";*/
							//	maraming_entity += client_name+"\n";
								String SQL = "SELECT sp_save_processing_cost_woutrplf_withor('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
										+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ "'"+desc+"', "+seq_no+", '"+rplf_no+"', "+yr+", '"+or+"')";

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

		if (tabCenter.getSelectedIndex() == 3) {
			ArrayList<String> listclients = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
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
						String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");
						if(checkBatch(batch_no).equals("")==false) {
							if(JOptionPane.showConfirmDialog(getContentPane(), "Batch no. " + checkBatch(batch_no) + " is already used." +
									"\n" + "Generate new batch?", "Confirmation", 
									JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION){

								String new_batch = generateBatchNo_v2();

								lblBatch.setText(String.format("<html><b>Batch No: %s</html>", new_batch));
								batch_no = new_batch;
							}
						}

						//maraming_entity = "";
						for(int x = 0; x < tblPCD_Block_Batch.getRowCount(); x++){
							String client_name = (String) modelPCD_Block_Batch.getValueAt(x, 8);
							Boolean isSelected = (Boolean) modelPCD_Block_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Block_Batch.getDate();
							BigDecimal amount = new BigDecimal (txtAmount_Block_Batch.getValue().toString());
							String remarks = (String) txtRemarks_Block_Batch.getText();
						//	remarks = remarks+"\n"+maraming_entity;
							String desc = (String) lblCode_Block_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
							String entity_id = (String) modelPCD_Block_Batch.getValueAt(x, 4);
							String unit_id = (String) modelPCD_Block_Batch.getValueAt(x, 7);
							String projcode = (String) modelPCD_Block_Batch.getValueAt(x, 5);
							String pbl_id = (String) modelPCD_Block_Batch.getValueAt(x, 1);
							Integer seq_no = (Integer) modelPCD_Block_Batch.getValueAt(x, 6);
							String costid = (String) lookupCode_Block_Batch.getValue();
							BigDecimal setup_amount = new BigDecimal (txtAmount_Block_Batch.getValue().toString());
							String req_id = (String) txtRequestedBy_Block_Batch.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							String description = (String) modelPCD_Block_Batch.getValueAt(x, 3);
							//String type = (String) cmbType.getSelectedItem();
							//String client_id = (String) lookupClient.getText();
							//String request_type = (String) lookupRequestType.getText();
							//String availer = (String) lookupAvailer.getText();
							//String availer_type = (String) lookupAvailerType.getText();
							//String batch_no = batchNo();
							String or = (String) txtORNo.getText();
							String yr = (String) txtYear.getText();


							if(isSelected){
								//**ADDED BY JED 2019-09-03 TO AVOID SAME BATCHING**//

								//if(checkTagRPT(entity_id, yr, pbl_id, costid).equals("")) {//**ADDED BY JED VICEDO 2019-08-07 : TO AVOID DOUBLE TAGGING OF RPT**//

								System.out.println("DUMAAN DITO");
							//	maraming_entity += client_name+"\n";
								String SQL = "SELECT sp_save_processing_cost_woutrplf_withor('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
										+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ "'"+desc+"', "+seq_no+", '"+rplf_no+"', "+yr+", '"+or+"')";

								FncSystem.out("Saving batch with OR NO!!", SQL);
								pgSelect db = new pgSelect();
								FncSystem.out("SQL", SQL);
								db.select(SQL);

								//lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
								//lblBatch.setText("<html><b>Batch No:</html>");

								/*}else {
										System.out.println("DUMAAN DITO2");
										listclients.add(checkTagRPT(entity_id, yr, pbl_id, costid));
										listPBL.add(description);
										System.out.printf("(%s)", listclients);
										System.out.printf("(%s)", listPBL);
									}*/


							}
						}
						if(listclients.isEmpty()){
							JOptionPane.showMessageDialog(getContentPane(), "Saved.", "", JOptionPane.INFORMATION_MESSAGE);
							modelPCD_Block_Batch.clear();
							scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
							tblPCD_Block_Batch.packAll();

							Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateOR);
							optionPaneWindow.dispose();

							Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
							if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
								optionPaneWindowMain.getFocusOwner();

							}
						}else {
							String tag_clients = listclients.toString().replaceAll("\\[", "").replaceAll("\\]","");
							String pbl = listPBL.toString().replaceAll("\\[", "").replaceAll("\\]","");
							JOptionPane.showMessageDialog(getContentPane(), "The client/s are already tagged:" + "\n" + "**" + tag_clients + "\n" + "**" + pbl, "Error", JOptionPane.ERROR_MESSAGE);
							Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateOR);
							optionPaneWindow.dispose();

							Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
							if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
								optionPaneWindowMain.getFocusOwner();
							}
						}

					}
				}
			}
		}
		if (tabCenter.getSelectedIndex() == 4) {
			
		}
		//}
	}

	private void saveOR2(){
		if (tabCenter.getSelectedIndex() == 1) {
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
						//maraming_entity = "";
						for(int x = 0; x < tblPCD_Batch.getRowCount(); x++){
							Boolean isSelected = (Boolean) modelPCD_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Batch.getDate();
							BigDecimal amount = new BigDecimal (txtAmount_Batch.getValue().toString());
							String remarks = (String) txtRemarks_Batch.getText();
						//	remarks = remarks+"\n"+maraming_entity;
							String desc = (String) lblCode_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
							String entity_id = (String) modelPCD_Batch.getValueAt(x, 1);
							String unit_id = (String) modelPCD_Batch.getValueAt(x, 9);
							String projcode = (String) modelPCD_Batch.getValueAt(x, 7);
							String pbl_id = (String) modelPCD_Batch.getValueAt(x, 4);
							Integer seq_no = (Integer) modelPCD_Batch.getValueAt(x, 5);
							String costid = (String) lookupCode_Batch.getValue();
							BigDecimal setup_amount = new BigDecimal (txtAmount_Batch.getValue().toString());
							String req_id = (String) txtRequestedBy_Batch.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							//String type = (String) cmbType.getSelectedItem();
							//String client_id = (String) lookupClient.getText();
							//String request_type = (String) lookupRequestType.getText();
							//String availer = (String) lookupAvailer.getText();
							//String availer_type = (String) lookupAvailerType.getText();
							//String batch_no = batchNo();
							String or = (String) txtORNo.getText();
							String yr = (String) txtYear.getText();
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

							if(isSelected){

								/*String SQL = "SELECT sp_save_processing_cost_woutrplf_withor('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
										+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ "'"+desc+"', '"+type+"', "+seq_no+", '"+client_id+"', '"+request_type+"', '"+availer+"', '"+availer_type+"', '"+rplf_no+"', "+yr+", '"+or+"')";*/

								String SQL = "SELECT sp_save_processing_cost_woutrplf_withor('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
										+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
										+ "'"+desc+"', "+seq_no+", '"+rplf_no+"', "+yr+", '"+or+"')";

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

		if (tabCenter.getSelectedIndex() == 3) {
			ArrayList<String> listclients = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
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
					//	maraming_entity = "";
						for(int x = 0; x < tblPCD_Block_Batch.getRowCount(); x++){
							Boolean isSelected = (Boolean) modelPCD_Block_Batch.getValueAt(x, 0);
							Date trans_date = (Date) dateSched_Block_Batch.getDate();
							BigDecimal amount = new BigDecimal (txtAmount_Block_Batch.getValue().toString());
							String remarks = (String) txtRemarks_Block_Batch.getText();
						//	remarks = remarks+"\n"+maraming_entity;
							String desc = (String) lblCode_Block_Batch.getText().replace("<html><font size = 2>[ ", "").replace(" ]</html>","");
							String entity_id = (String) modelPCD_Block_Batch.getValueAt(x, 4);
							String unit_id = (String) modelPCD_Block_Batch.getValueAt(x, 7);
							String projcode = (String) modelPCD_Block_Batch.getValueAt(x, 5);
							String pbl_id = (String) modelPCD_Block_Batch.getValueAt(x, 1);
							Integer seq_no = (Integer) modelPCD_Block_Batch.getValueAt(x, 6);
							String costid = (String) lookupCode_Block_Batch.getValue();
							BigDecimal setup_amount = new BigDecimal (txtAmount_Block_Batch.getValue().toString());
							String req_id = (String) txtRequestedBy_Block_Batch.getText();
							String emp_code = (String) UserInfo.EmployeeCode;
							String description = (String) modelPCD_Block_Batch.getValueAt(x, 3);
							//String type = (String) cmbType.getSelectedItem();
							//String client_id = (String) lookupClient.getText();
							//String request_type = (String) lookupRequestType.getText();
							//String availer = (String) lookupAvailer.getText();
							//String availer_type = (String) lookupAvailerType.getText();
							//String batch_no = batchNo();
							String or = (String) txtORNo.getText();
							String yr = (String) txtYear.getText();
							String batch_no = (String) lblBatch.getText().replace("<html><b>Batch No: ", "").replace("</html>", "");

							if(isSelected){
								//**ADDED BY JED VICEDO 2019-08-07 : TO AVOID DOUBLE TAGGING OF RPT**//
								if(checkTagRPT(entity_id, yr, pbl_id, costid).equals("")) {

									System.out.println("DUMAAN DITO");
									String SQL = "SELECT sp_save_processing_cost_woutrplf_withor('"+entity_id+"', '"+projcode+"', '"+pbl_id+"', '"+costid+"', '"+trans_date+"'::date,"
											+ ""+amount+", "+setup_amount+", '"+req_id+"', NULLIF('"+remarks+"','null'), '"+emp_code+"', NULLIF('"+batch_no+"','null'), NULLIF('"+unit_id+"','null'),"
											+ "'"+desc+"', "+seq_no+", '"+rplf_no+"', "+yr+", '"+or+"')";

									FncSystem.out("Saving batch with OR NO!!", SQL);
									pgSelect db = new pgSelect();
									FncSystem.out("SQL", SQL);
									db.select(SQL);

									lblBatch.setText(String.format("<html><b>Batch No: %s</html>", batch_no));
									//listclients.add(checkTagRPT(entity_id, yr, pbl_id, costid));
									//System.out.printf("(%s)", listclients);
								}else {
									System.out.println("DUMAAN DITO2");
									listclients.add(checkTagRPT(entity_id, yr, pbl_id, costid));
									listPBL.add(description);
									System.out.printf("(%s)", listclients);
									System.out.printf("(%s)", listPBL);
								}
							}
						}

						if(listclients.isEmpty()){
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
						}else {
							String tag_clients = listclients.toString().replaceAll("\\[", "").replaceAll("\\]","");
							String description = listPBL.toString().replaceAll("\\[", "").replaceAll("\\]","");
							JOptionPane.showMessageDialog(getContentPane(), "The client/s are already tagged:" + "\n" + "**" + tag_clients + "\n" + "**" + description, "Error", JOptionPane.ERROR_MESSAGE);
							Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlCreateOR);
							optionPaneWindow.dispose();

							Window optionPaneWindowMain = SwingUtilities.getWindowAncestor(pnlMain);
							if (SwingUtilities.getWindowAncestor(pnlMain) != null) {
								optionPaneWindowMain.getFocusOwner();
							}
						}
					}
				}
			}
		}
		//}
	}

	private static String checkTagRPT(String entity_id, String rpt_year, String pbl_id, String code) {

		String entity_name = "";
//Comment by Erick 2023-09-06 replaced with function.
//		String SQL = 
//				"select \n" + 
//						"b.entity_name\n" + 
//						"from rf_processing_cost a\n" + 
//						"left join rf_entity b on a.entity_id = b.entity_id\n" + 
//						"where a.rpt_year = '"+rpt_year+"' \n" + 
//						"and a.entity_id = '"+entity_id+"' \n" +
//						"and a.pbl_id = '"+pbl_id+"' \n" +
//						"and a.pcostid_dl = '"+code+"' \n" +
//						"and a.status_id = 'A'";
		String SQL = "select * from checktagrpt('"+entity_id+"', '"+rpt_year+"', '"+pbl_id+"', '"+code+"');";

		System.out.println("checkTagRPT: "+SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ( (String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				entity_name = "NO CLIENT";//**ADDED BY JED VICEDO 2020-01-21 : TO AVOID DOUBLE TAG FOR UNITS WITH NO CLIENTS**//
			}else {
				entity_name = (String) db.getResult()[0][0];
			}
		}else {
			entity_name = "";
		}

		return entity_name;
	}
	
	private static String checkTagRPT_v2(String entity_id, Integer rpt_year, String pbl_id, String code) {

		String entity_name = "";

		String SQL = 
				"select \n" + 
						"b.entity_name\n" + 
						"from rf_processing_cost a\n" + 
						"left join rf_entity b on a.entity_id = b.entity_id\n" + 
						"where a.rpt_year = '"+rpt_year+"' \n" + 
						"and a.entity_id = '"+entity_id+"' \n" +
						"and a.pbl_id = '"+pbl_id+"' \n" +
						"and a.pcostid_dl = '"+code+"' \n" +
						"and a.status_id = 'A'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ( (String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				entity_name = "NO CLIENT";//**ADDED BY JED VICEDO 2020-01-21 : TO AVOID DOUBLE TAG FOR UNITS WITH NO CLIENTS**//
			}else {
				entity_name = (String) db.getResult()[0][0];
			}
		}else {
			entity_name = "";
		}

		return entity_name;
	}
	
	

	//	private void removeRow(){
	//		if (tabCenter.getSelectedIndex() == 0) {
	//			int[] selectedRows = tblPCD.getSelectedRows();
	//
	//			if (selectedRows.length == 0) {
	//				JOptionPane.showMessageDialog(getContentPane(), "Please select row.", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
	//				return;
	//			}
	//
	//			for (int x = selectedRows.length - 1; x > - 1; x--) {
	//				int row = selectedRows[x];
	//
	//				modelPCD.removeRow(row);
	//			}
	//
	//			updatePCD();
	//			computeTotal();
	//		}
	//		if (tabCenter.getSelectedIndex() == 2) {
	//			int[] selectedRows = tblPCD_BlockLot.getSelectedRows();
	//
	//			if (selectedRows.length == 0) {
	//				JOptionPane.showMessageDialog(getContentPane(), "Please select row.", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
	//				return;
	//			}
	//
	//			for (int x = selectedRows.length - 1; x > - 1; x--) {
	//				int row = selectedRows[x];
	//
	//				modelPCD_BlockLot.removeRow(row);
	//			}
	//
	//			updatePCD_BlockLot();
	//			computeTotal();
	//		}
	//	}

	private void cancel(){//---added by jed 2018-10-15 : for refreshing all fields (no dcrf)---//

		if (tabCenter.getSelectedIndex() == 0) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
				lookupCoID.setText("");
				lookupProjectID.setText("");
				txtCoName.setText("All Companies");
				txtProjectName.setText("All Projects");
				lookupClient.setText("");
				lblClient.setText("[ ]");
				lblProject.setText("[ ]");
				lblPblDescription.setText("[ ]");
				lblSellingAgent.setText("[ ]");
				lblDateReserved.setText("[ ]");
				lblStatus.setText("[ ]");
				lblBatch.setText("<html><b>Batch No:</html>");
				lblCode.setText("[ ]");
				lookupCode.setText("");
				lookupCode.setEnabled(true);
				txtAmount.setText("0.00");
				txtRequestedBy.setText(null);
				lblReqBy1.setText("[ ]");
				txtRemarks.setText(null);
				btnDRF.setEnabled(false);
				btnTSave.setEnabled(false);
				btnBatch.setEnabled(false);
				cmbType.setSelectedItem("Payment");
				//dateSched.setDate(Calendar.getInstance().getTime());
				dateSched.setDate(FncGlobal.getDateToday());
				lblSetupAmount1.setText("0.00");
				lblRunningTotal1.setText("0.00");
				lblBalanceAmount.setText("0.00");
				tblPCD.setEnabled(true);
				modelPCD.setRowCount(0);
				modelPCDTotal.setValueAt(new BigDecimal(0.00), 0, 3);
				modelPCD.clear();
				txtAmount.setEditable(false);
				txtRequestedBy.setEditable(false);
				dateSched.setEnabled(false);
				btnOR.setEnabled(false);
				btnDRF.setText("<html><center><font size = 2>Create Disb.\n Request</html>");
				btnDRF.setActionCommand("Create Disbursement Request");
				mniDelete.setEnabled(false);
				pnlState(true, true, true, true,true);

			}

		}

		if (tabCenter.getSelectedIndex() == 1) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);

				lookupCoID.setText("");
				lookupProjectID.setText("");
				modelPCD_Batch.clear();
				scrollPCD_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Batch.getRowCount())));
				tblPCD_Batch.packAll();
				lookupCode_Batch.setText("");
				lblCode_Batch.setText("[ ]");
				cmbType_Batch.setSelectedIndex(0);
				lookupCode_Batch.setEnabled(false);
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
				pnlState(true, true, true, true, true);
				btnTSave.setActionCommand("Save");
				chkOld.setSelected(false);
				chkOld.setEnabled(false);

			}

		}

		if (tabCenter.getSelectedIndex() == 2){
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);

				lookupCoID.setText("");
				lookupProjectID.setText("");
				lookupProject.setText("");
				lblProject2.setText("[ ]");
				lookupUnit.setValue(null);
				lblUnit.setText("[ ]");
				lblBuyer.setText("[ ]");
				lblCode_BlockLot.setText("[ ]");
				lookupCode_BlockLot.setText(null);
				lookupCode_BlockLot.setEnabled(false);
				txtAmount_BlockLot.setText("0.00");
				txtRequestedBy_BlockLot.setText(null);
				lblReqBy1_BlockLot.setText("[ ]");
				txtRemarks_BlockLot.setText(null);
				btnDRF.setEnabled(false);
				btnTSave.setEnabled(false);
				btnBatch.setEnabled(false);
				cmbType_BlockLot.setSelectedItem("Payment");
				dateSched_BlockLot.setDate(Calendar.getInstance().getTime());
				lblSetupAmount1_BlockLot.setText("0.00");
				lblRunningTotal1_BlockLot.setText("0.00");
				lblBalanceAmount_BlockLot.setText("0.00");
				modelPCD_BlockLot.setRowCount(0);
				modelPCDTotal_BlockLot.setValueAt(new BigDecimal(0.00), 0, 3);
				txtAmount_BlockLot.setEditable(false);
				txtRequestedBy_BlockLot.setEditable(false);
				dateSched_BlockLot.setEnabled(false);
				lblBatch.setText("<html><b>Batch No: </html>");
				btnOR.setEnabled(false);
				pnlState(true, true, true, true, true);

			}
		}

		if (tabCenter.getSelectedIndex() == 3) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
				lookupCoID.setText("");
				lookupProjectID.setText("");
				modelPCD_Block_Batch.clear();
				scrollPCD_Block_Batch.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Block_Batch.getRowCount())));
				tblPCD_Block_Batch.packAll();
				lookupCode_Block_Batch.setText("");
				lblCode_Block_Batch.setText("[ ]");
				cmbType_Block_Batch.setSelectedIndex(0);
				lookupCode_Block_Batch.setEnabled(false);
				dateSched_Block_Batch.setDate(Calendar.getInstance().getTime());
				txtAmount_Block_Batch.setText("0.00");
				txtRequestedBy_Block_Batch.setText(null);
				txtRequestedBy_Block_Batch.setEditable(false);
				lblReqBy1_Block_Batch.setText("[ ]");
				txtRemarks_Block_Batch.setText(null);
				lblSetupAmount1_Block_Batch.setText("0.00");
				lblRunningTotal1_Block_Batch.setText("0.00");
				lblBalanceAmount_Block_Batch.setText("0.00");
				btnDRF.setEnabled(false);
				btnTSave.setEnabled(false);
				btnBatch.setEnabled(true);
				modelPCD_Block_Batch.setRowCount(0);
				lblBatch.setText("<html><b>Batch No:</html>");
				btnOR.setEnabled(false);
				pnlState(true, true, true, true, true);
				btnORSave.setActionCommand("Save OR");
				btnTSave.setActionCommand("Save");

			}

		}
		
		if (tabCenter.getSelectedIndex() == 4) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel all data?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(getContentPane(), "All data cancelled.", "Information", JOptionPane.INFORMATION_MESSAGE);
				lookupCoID.setText("");
				lookupProjectID.setText("");
				modelPCD_Block_Batch_RPT.clear();
				scrollPCD_Block_Batch_RPT.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD_Block_Batch.getRowCount())));
				tblPCD_Block_Batch_RPT.packAll();
				lookupCode_Block_Batch_rpt.setText("");
				lblCode_Block_Batch_rpt.setText("[ ]");
				cmbType_Block_Batch.setSelectedIndex(0);
				lookupCode_Block_Batch_rpt.setEnabled(false);
				btnDRF.setEnabled(false);
				btnTSave.setEnabled(false);
				btnBatch.setEnabled(true);
				modelPCD_Block_Batch_RPT.setRowCount(0);
				lblBatch.setText("<html><b>Batch No:</html>");
				btnOR.setEnabled(false);
				pnlState(true, true, true, true,true);
				btnORSave.setActionCommand("Save OR");
				btnTSave.setActionCommand("Save");
			}
		}

	}

	private Boolean ifTrue(){//--added by JED 2018-11-21 : for double tagging--//

		Boolean a = false;

		for (int x = 0; x < modelPCD.getRowCount(); x++){

			Boolean isSelected = (Boolean) modelPCD.getValueAt(x, 0);

			if (isSelected){
				a = true;
			}else
				a = false;
		}
		return a;
	}

	private boolean checkingRPLF(){

		Boolean existing = true;
		
		int rw = tblPCD.getSelectedRow();

		if(tblPCD.getSelectedRows().length>0){

			String batch_no = (String) tblPCD.getValueAt(rw, 11);
			String pcostid_dl = (String) lookupCode.getValue();
			String entity_id = (String) lookupClient.getValue();
			String rplf_no = (String) tblPCD.getValueAt(rw, 6);
			System.out.println("batch_no: "+batch_no);
			
			String sql =
					"select * from rf_processing_cost \n" +
							"where entity_id = '"+entity_id+"' \n" + 
							"and coalesce(batch_no,'null')    = '"+batch_no+"' \n" + 
							"and pcostid_dl  = '"+pcostid_dl+"' \n" + 
							"and CASE WHEN NULLIF('"+rplf_no+"' ,'') is null THEN true ELSE false END \n" +
							"and status_id   = 'A'";
				
			System.out.println("checkingRPLF: "+sql);
			pgSelect db = new pgSelect();
			db.select(sql);
			
			if(db.isNotNull()) {
				existing = false;
			}

			FncSystem.out("dito dito dito", sql);

		}
		return existing;
	}

	private void delete(){

		if (ifTrue() == true){

			JOptionPane.showMessageDialog(getContentPane(), "You are currently tagging a particular!", "Error", JOptionPane.ERROR_MESSAGE);

		}else{

			if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this particular?", "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);

			if(checkingRPLF()) {
				JOptionPane.showMessageDialog(getContentPane(), "Only particular without rplf number can be deleted!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}else{
				pgUpdate db = new pgUpdate();
				deletePCOSTParticular(db);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Successfully deleted!", "Information", JOptionPane.INFORMATION_MESSAGE);
				displayPCostDetails();
				lblCode.setText("[ ]");
				lookupCode.setText(null);
				lookupCode.setEnabled(true);
				txtAmount.setText("0.00");
				txtRequestedBy.setText(null);
				lblReqBy1.setText("[ ]");
				txtRemarks.setText(null);
				cmbType.setSelectedItem("Payment");
				//dateSched.setDate(Calendar.getInstance().getTime());
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

	private void deletePCOSTParticular(pgUpdate db){

		int rw = tblPCD.convertRowIndexToModel(tblPCD.getSelectedRow());

		if(tblPCD.getSelectedRows().length>0){

			String batch_no     = (String) tblPCD.getValueAt(rw, 11);
			String pcostid_dl   = (String) lookupCode.getValue();
			String entity_id    = (String) lookupClient.getValue();
			String user_code	= UserInfo.EmployeeCode;
			Integer pcost_id = (Integer) modelPCD.getValueAt(tblPCD.getSelectedRow(), 14); // Added by Erick 2023-04-20
			
			System.out.println("PCOST_ID : "+pcost_id);

			String sql =
					"update rf_processing_cost set status_id = 'I', edited_by = '"+user_code+"', date_edited = NOW()::DATE \n" +
							"where entity_id = '"+entity_id+"' \n" + 
							"and coalesce(batch_no,'null') = '"+batch_no+"' \n" + 
							"and pcostid_dl = '"+pcostid_dl+"' \n" + 
							"and proc_cost_id = "+pcost_id+" \n"+ // Added by Erick 2023-04-20
							"and status_id = 'A'";


			FncSystem.out("SQL for deleting particular", sql);
			db.executeUpdate(sql, false);

		}
	}

	private static void pnlState (Boolean IND, Boolean BATCH, Boolean BLOCK, Boolean BLOCK2, Boolean BLOCK4){

		tabCenter.setEnabledAt(0, IND);
		tabCenter.setEnabledAt(1, BATCH);
		tabCenter.setEnabledAt(2, BLOCK);
		tabCenter.setEnabledAt(3, BLOCK2);
		tabCenter.setEnabledAt(4, BLOCK4);

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

	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;

		/*	Company Default	*/
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			lookupCoID.setValue("");
			txtCoName.setText("All Companies");
		} else {
			lookupCoID.setValue(FncGlobal.GetString("SELECT co_id FROM mf_company LIMIT 1"));

			try {
				String strCo = FncGlobal.GetString("SELECT company_name FROM mf_company WHERE co_id = '"+lookupCoID.getValue()+"'");
				System.out.println(strCo);
				txtCoName.setText(strCo);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtCoID: " + lookupCoID.getValue());
				System.out.println("txtCoName: " + FncGlobal.GetString("SELECT company_name FROM mf_company WHERE co_id = '"+lookupCoID.getValue()+"'"));
			}
		}

		/*	Project Default	*/
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			lookupProjectID.setValue("");
			txtProjectName.setText("All Projects");
		} else {
//			lookupProjectID.setValue(FncGlobal.GetString("SELECT proj_id FROM mf_project LIMIT 1"));

			try {
				String strPro = FncGlobal.GetString("SELECT proj_name FROM mf_project WHERE proj_id = '"+lookupProjectID.getValue()+"'");
				System.out.println(strPro);
				txtProjectName.setText(strPro);	
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtProjectID: " + lookupProjectID.getValue());
				System.out.println(FncGlobal.GetString("SELECT proj_name FROM mf_project WHERE proj_id = '"+lookupProjectID.getValue()+"'"));
			}
		}
	}
	public static String SQL_PROJECT() {
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\" \n"
				+ "FROM mf_project \n"
				+ "WHERE (co_id = '"
				+ lookupCoID.getText()
				+ "' OR '"
				+ lookupCoID.getText()
				+ "' = '') \n" + " and proj_id not in('008','021') ORDER BY proj_id;";

		System.out.println(SQL);
		return SQL;
	}

	private static String checkPBL(String oth_pbl_id){

		String pbl_id = "";
		String SQL =
				"select\n" + 
						"pbl_id \n" + 
						"from hs_sold_other_lots x \n" + 
						"where x.oth_pbl_id = '"+oth_pbl_id+"' and x.status_id = 'A'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				pbl_id = (String) db.getResult()[0][0].toString();
			}
		}else{
			pbl_id = "";
		}


		return pbl_id;
	}
	
	private String connectrplfPopUp(String rplf, String batch) {
		String x = "Connect rplf =" + rplf + "\n" + " To batch  =" + batch +" ";
		return x;
	}


}