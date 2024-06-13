package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Accounting.Cashiering.CashReceiptBook;
import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPane;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTextAreaEditor;
import components._JTextAreaRenderer2;
import components._JVerticalTextIcon;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelCARD_AccountStatus;
import tablemodel.modelCARD_BankAccounts;
import tablemodel.modelCARD_ChangeDueDate;
import tablemodel.modelCARD_CheckHistory;
import tablemodel.modelCARD_ClientRequestHistory;
import tablemodel.modelCARD_CreditOfPayment;
import tablemodel.modelCARD_DocFindings;
import tablemodel.modelCARD_DocsHistory;
import tablemodel.modelCARD_Dues;
import tablemodel.modelCARD_Fire;
import tablemodel.modelCARD_LOG_Details;
import tablemodel.modelCARD_Ledger;
import tablemodel.modelCARD_MRI;
import tablemodel.modelCARD_Notices;
import tablemodel.modelCARD_Payments;
import tablemodel.modelCARD_PmtsWaived;
import tablemodel.modelCARD_PrintedDocuments;
import tablemodel.modelCARD_RefundOfPayment;
import tablemodel.modelCARD_ReqDocDetails;
import tablemodel.modelCARD_ReqDocPurpose;
import tablemodel.modelCARD_ReqDocStatus;
import tablemodel.modelCARD_RequestedDocs;
import tablemodel.modelCARD_ReservationDocuments;
import tablemodel.modelCARD_Schedule;
import tablemodel.modelClientFollowup;
import tablemodel.modelConnectionList;
import tablemodel.modelDocStatus;
import tablemodel.modelHouseConstructedHistory;
import tablemodel.modelTOverMoveIn;
import tablemodel.modelTctTaxdec;
import tablemodel.modelUnitStatus;
import tablemodel.model_HouseRepair;
import tablemodel.model_accomplishment;
import tablemodel.model_pcost;
import tablemodel.model_tcost;

public class ClientRequestOldDetails extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = 5849237837136995888L;
	private static String title = "Client Request Old Details";
	static Dimension SIZE = new Dimension(1110, 600);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	public static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	Dimension frameSize = new Dimension(1100, 600);// 510, 250
	
	private JPanel pnlNorth;
	
	private JPanel pnlCenter;
	
	private JPanel pnlNorthCenter;

	//1st Column of Client Details Panel
	private _JLookup lookupClient;
	private _JXTextField txtClientName;
	private JXTextField txtProjectID;
	private _JXTextField txtProjectName;
	private JXTextField txtPblID;
	private _JXTextField txtPblDescription;
	private JXTextField txtModelID;
	private _JXTextField txtModelName;
	private JPanel pnlModelName;
	private _JXTextField txtModelName2;
	private JXTextField txtPmtStage;
	private _JXTextField txtPmtStatusRemarks;
	private JXTextField txtSellingAgentID;
	private _JXTextField txtSellingAgentName;
	private JXTextField txtBuyerTypeID;
	private _JXTextField txtBuyerTypeName;
	private JXTextField txtPmtSchemeID;
	private _JXTextField txtPmtSchemeName;
	private JXTextField txtStatusID;
	private _JXTextField txtStatusName;

	//2nd Column of Client Details Panel
	private _JXFormattedTextField txtGSP;
	private _JXFormattedTextField txtDiscount;
	private _JXFormattedTextField txtNSP;
	private _JXFormattedTextField txtVAT;
	private _JXFormattedTextField txtDP;
	private _JXFormattedTextField txtMiscFee15Pct;
	private _JXFormattedTextField txtTotalCashOutlay;
	private _JXFormattedTextField txtLoanableAmount;
	private _JXFormattedTextField txtBalance;
	private _JXFormattedTextField txtMiscFeeBalance;
	private JXTextField txtLotArea;
	private JXTextField txtFloorArea;
	private JXTextField txtPreassignedColor;
	private JXTextField txtPreferredColor;
	private JXTextField txtNTPNo;
	private JXTextField txtContractor;
	private JXTextField txtRemarks;
	private JXTextField txtContractNo;
	private JXTextField txtWorkDesc;
	private JXTextField txtDuration;
	private JXTextField txtTct;

	//3rd Column of Client Details Panel
	private _JXFormattedTextField txtDPAmount;
	private _JXFormattedTextField txtDPRate;
	private _JXFormattedTextField txtDPTerm;
	private _JXFormattedTextField txtMAAmount;
	private _JXFormattedTextField txtMARate;
	private _JXFormattedTextField txtMATerm;
	private _JXFormattedTextField txtBankTerm;
	private _JXTextField txtFactorRate;
	private _JXFormattedTextField txtIntRate;
	private _JDateChooser txtWithNTC;
	private _JDateChooser txtHouseConstructed;
	private _JDateChooser txtTurnOver;
	private _JDateChooser txtMovedIn;
	private _JDateChooser txtMovedOut;
	private _JDateChooser txtDate1;

	private JTabbedPane tabCenter;
	private JTabbedPane tabTD;
	private JTabbedPane tabAccomplishment;

	private JScrollPane scrollSchedule;
	private _JTableMain tblSchedule;
	private modelCARD_Schedule modelSchedule;
	private JList rowheaderSchedule;

	private JTabbedPane tabLedger;

	private _JDateChooser dateDues;
	private _JDateChooser dateExclude;
	private _JScrollPaneMain scrollDues;
	private JXLabel lblAmtToUpdate;
	private JCheckBox chkExcludeLastSched;
	private _JXFormattedTextField txtAmtToUpdate;
	private _JTableMain tblDues;
	private modelCARD_Dues modelDues;
	private JList rowheaderDues;

	private _JScrollPaneTotal scrollDuesTotal;
	private _JTableTotal tblDuesTotal;
	private modelCARD_Dues modelDuesTotal;

	private JCheckBox chkShowRefund;
	private JScrollPane scrollLedger;
	private _JTableMain tblLedger;
	private modelCARD_Ledger modelLedger;
	private JList rowheaderLedger;

	private JScrollPane scrollPayments;
	private _JTableMain tblPayments;
	private modelCARD_Payments modelPayments;
	private JList rowheaderPayments;

	private JScrollPane scrollCheckHistory;
	private _JTableMain tblCheckHistory;
	private modelCARD_CheckHistory modelCheckHistory;
	private JList rowheaderCheckHistory;

	private JScrollPane scrollCreditOfPayment;
	private _JTableMain tblCreditOfPayment;
	private modelCARD_CreditOfPayment modelCreditOfPayment;
	private JList rowheaderCreditOfPayment;

	private _JScrollPane scrollReservationDocuments;
	private _JTableMain tblReservationDocuments;
	private modelCARD_ReservationDocuments modelReservationDocuments;
	private JList rowheaderReservationDocuments;

	private _JScrollPane scrollPrintedDocuments;
	private _JTableMain tblPrintedDocuments;
	private modelCARD_PrintedDocuments modelPrintedDocuments;
	private JList rowheaderPrintedDocuments;

	private _JScrollPane scrollDocsHistory;
	private _JTableMain tblDocsHistory;
	private modelCARD_DocsHistory modelDocsHistory;
	private JList rowHeaderDocsHistory;

	private _JScrollPane scrollDocFindings;
	private _JTableMain tblDocFindings;
	private JList rowHeaderDocFindings;
	private modelCARD_DocFindings modelDocFindings;

	private _JScrollPane scrollReqDocDetails;
	private _JTableMain tblReqDocDetails;
	private JList rowHeaderReqDocDetails;
	private modelCARD_ReqDocDetails modelReqDocDetails;

	private _JScrollPane scrollReqDocPurpose;
	private _JTableMain tblReqDocPurpose;
	private JList rowHeaderReqDocPurpose;
	private modelCARD_ReqDocPurpose modelReqDocPurpose;

	private _JScrollPane scrollRequestedDocs;
	private _JTableMain tblRequestedDocs;
	private JList rowHeaderRequestedDocs;
	private modelCARD_RequestedDocs modelRequestedDocs;

	private _JScrollPane scrollReqDocStatus;
	private _JTableMain tblReqDocStatus;
	private JList rowHeaderReqDocStatus;
	private modelCARD_ReqDocStatus modelReqDocsStatus;

	private _JScrollPane scrollNotices;
	private _JTableMain tblNotices;
	private modelCARD_Notices modelNotices;
	private JList rowHeaderNotices;

	private _JScrollPane scrollAccountStatusHistory;
	private _JTableMain tblAccountStatusHistory;
	private modelCARD_AccountStatus modelAccountStatusHistory;
	private JList rowheaderAccountStatusHistory;

	private _JScrollPane scrollClientRequestHistory;
	private _JTableMain tblClientRequestHistory;
	private modelCARD_ClientRequestHistory modelClientReqHistory;
	private JList rowHeaderClientRequestHistory;

	private JSplitPane splitRequestDetails;

	private _JXFormattedTextField txtReqAmtOld;
	private JXLabel lblReqFieldOld;
	private _JXTextField txtReqFieldOld;

	private _JXFormattedTextField txtReqAmtNew;
	private JXLabel lblReqFieldNew;
	private _JXTextField txtReqFieldNew;

	private JPanel pnlReqComponent;

	private _JScrollPaneMain scrollRequestDetails;
	private _JTableMain tblRequestDetails;
	private JPanel pnlRequestDetails;
	private modelCARD_RefundOfPayment modelRefundOfPayment;
	private modelCARD_PmtsWaived modelPmtsWaived;
	private modelCARD_ChangeDueDate modelChangeDueDate;
	private JList rowHeaderRequestDetails;

	private _JScrollPaneTotal scrollPWTotal;
	private _JTableTotal tblPWTotal;
	private modelCARD_PmtsWaived modelPmtsWaivedTotal;

	private _JScrollPane scrollLOGDetails;
	private _JTableMain tblLOGDetails;
	private JList rowHeaderLOGDetails;
	private modelCARD_LOG_Details modelLOG_Details;

	private _JScrollPane scrollTOverMoveIn;
	private _JTableMain tblTOverMoveIn;
	private JList rowHeaderTOverMoveIn;
	private modelTOverMoveIn modelTOverMoveIn;

	private _JScrollPane scrollUnitStatus;
	private _JTableMain tblUnitStatus;
	private JList rowHeaderUnitStatus;
	private modelUnitStatus modelUnitStatus;

	private _JScrollPane scrollFollowUp;
	private modelClientFollowup modelFollowup;
	private _JTableMain tblFollowUp;
	private JList rowHeaderFollowUp;

	private JXPanel pnlUnitStatus;

	private _JScrollPane scrollMRI;
	private _JTableMain tblMRI;
	private JList rowHeaderMRI;
	private modelCARD_MRI modelMRI;


	private _JScrollPane scrollFire;
	private _JTableMain tblFire;
	private JList rowHeaderFire;
	private modelCARD_Fire modelFire;

	private _JScrollPane scrollBankAccounts;
	private _JTableMain tblBankAccounts;
	private JList rowHeaderBankAccounts;
	private modelCARD_BankAccounts modelBankAccts;

	private _JScrollPane scrollConnections;
	private _JTableMain tblConnections;
	private JList rowHeaderConnections;
	private modelConnectionList modelConnections;
	
	private modelHouseConstructedHistory modelNTP_History;
	private _JTableMain tblNTP_History;
	private JScrollPane scrollNTP_History;
	private JList rowHeaderNTP_History;

	private model_accomplishment modelAccomplishment;
	private _JTableMain tblAccomplishment;
	private JScrollPane scrollAccomplishment;
	private JList rowheaderAccomplishment;
	
	private JPanel pnlAccomplishment2;
	private model_accomplishment modelAccomplishment2;
	private _JTableMain tblAccomplishment2;
	private JScrollPane scrollAccomplishment2;
	private JList rowheaderAccomplishment2;
	
	private model_tcost modelTCT;;
	private _JScrollPaneMain scrollTCT;
	private _JTableMain tblTCT;
	private JList rowHeaderTCT;
	
	private _JScrollPaneTotal scrollTCTTotal;
	private _JTableTotal tblTCTTotal;
	private model_tcost modelTCTTotal;

	private model_pcost modelPCD;
	private _JScrollPaneMain scrollPCD;
	private _JTableMain tblPCD;
	private JList rowHeaderPCD;

	private _JScrollPaneTotal scrollPCDTotal;
	private _JTableTotal tblPCDTotal;
	private model_pcost modelPCDTotal;

	private modelDocStatus modelTCTTax;
	private _JTableMain tblTCTTax;
	private JScrollPane scrollTCTTax;
	private JList rowheaderTCTTax;	

	private modelTctTaxdec modelTctTaxdec;
	private _JScrollPaneMain scrollTctTaxdec;
	private _JTableMain tblTctTaxdec;
	private JList rowHeaderTctTaxdec;
	
	private model_HouseRepair modelHR;
	private _JScrollPaneMain scrHR;
	private _JTableMain tblHR;
	private JList rowHR;

	private TCostComputation pnlTCostCompu;

	private Object[] clientDetails;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	private Boolean NOA_RELEASED = false;

	Timer timerStatus = null;
	boolean blinkState = false;

	private JTabbedPane tabGeneralHistoryCenter;

	private JTextArea txtTD;
	private JScrollPane scpDRFRemark;

	private _JDateChooser txtBIRDate;
	private JXTextField txtHLID;

	private JPanel pnlRemarks;
	//Font fontSmallTab = new Font("Tahoma", Font.BOLD, 8);
	Font fontSmallTab = new Font("Tahoma", Font.BOLD, 7);
	Font fontTable = new Font("Tahoma", Font.BOLD, 7);

	PropertyManagement PM = new PropertyManagement(this);
	
	public ClientRequestOldDetails() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ClientRequestOldDetails(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public ClientRequestOldDetails(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(frameSize));
		UIManager.put("TabbedPane.tabInsets", new Insets(2, 5, 1, 5));
		UIManager.put("TabbedPane.font", UIManager.getFont("TabbedPane.font").deriveFont(11f));
		
		
		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(3, 0));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);

				/*SwingUtilities.invokeLater(new Runnable() {
					public void run() {

					}
				});*/
				pnlNorth.setPreferredSize(new Dimension(788, 160));
				if(UserInfo.EmployeeCode.equals("000645") == false){
					pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					pnlNorth.setPreferredSize(new Dimension(788, 215));
				}
				{
					JPanel pnlClient = new JPanel(new BorderLayout(1, 1));
					pnlNorth.add(pnlClient, BorderLayout.NORTH);
					if(UserInfo.EmployeeCode.equals("000645")){
						pnlClient.setPreferredSize(new Dimension(0, 15));
						pnlMain.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
					}
					{
						JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
						pnlClient.add(pnlWest, BorderLayout.WEST);
						pnlWest.setPreferredSize(new Dimension(180, 15));
						{
							JXLabel lblClient = new JXLabel("Client");
							pnlWest.add(lblClient, BorderLayout.WEST);
							lblClient.setPreferredSize(new Dimension(60, 15));
						}
						{
							lookupClient = new _JLookup(null, "Clients", 1);
							pnlWest.add(lookupClient, BorderLayout.CENTER);
							lookupClient.setReturnColumn(0);
							lookupClient.setLookupSQL(_ClientRequestOldDetails.sqlOldClients());
							//lookupClient.setFilterName(true);
							lookupClient.setFilterCardName(true);
							lookupClient.setFont(FncLookAndFeel.systemFont_Plain.deriveFont(11f));
							lookupClient.setSizeDialog(new Dimension(800, 420));
							if(UserInfo.EmployeeCode.equals("000645")){
								lookupClient.setSizeDialog(new Dimension(600, 420));
							}
							lookupClient.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										
										String request_no = (String) data[0];
										String entity_id = (String) data[1];
										String proj_id = (String) data[7];
										String pbl_id = (String) data[4];
										Integer seq_no = (Integer) data[5];

										System.out.printf("Display Entity ID: %s%n", entity_id);
										System.out.printf("Display Entity ID: %s%n", proj_id);
										System.out.printf("Display PBL ID: %s%n", pbl_id);
										System.out.printf("Display Seq No: %s%n", seq_no);
										
										displayClientDetails(request_no);
										displayTabDetails(request_no, false);
										
										/*displayClientDetails(entity_id, proj_id, pbl_id, seq_no, true);
										displayTabs(entity_id, proj_id, pbl_id, seq_no, true);
										dateDues.setDate(FncGlobal.getDateToday());
										displayTabDetails(entity_id, proj_id, pbl_id, seq_no, true);

										hdmfInfo_maintab.displayLoanFilingStatus(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFUnitInspection(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFPayments(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFSchedule(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFLoanReleasedDetail(entity_id, proj_id, pbl_id, seq_no.toString());
										tab_remConversion.DisplayHDMFREM(entity_id, proj_id, pbl_id, seq_no.toString());*/
									}
								}
							});
						}
					}
					{
						txtClientName = new _JXTextField();
						pnlClient.add(txtClientName, BorderLayout.CENTER);
						//txtClientName.getDocument().addDocumentListener(this);
					}
				}
				//GridLayout grid = new GridLayout(10, 1, 1, 1);
				GridLayout grid = new GridLayout(10, 1, 1, 1);
				{
					pnlNorthCenter = new JPanel(new GridLayout(10, 1, 1, 1));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					//pnlNorthCenter.setBorder(LINE_BORDER);
					/*{
						JPanel pnlClient = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlClient);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlClient.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(140, 20));
							{
								JXLabel lblClient = new JXLabel("Client");
								pnlWest.add(lblClient, BorderLayout.WEST);
								lblClient.setPreferredSize(new Dimension(60, 19));
							}
							{
								lookupClient = new _JLookup(null, "Clients", 1);
								pnlWest.add(lookupClient, BorderLayout.CENTER);
								lookupClient.setReturnColumn(1);
								lookupClient.setLookupSQL(_CARD.sqlClients());
								//lookupClient.setFilterName(true);
								lookupClient.setFilterCardName(true);
								lookupClient.setFont(FncLookAndFeel.systemFont_Plain.deriveFont(11f));
								lookupClient.setSizeDialog(new Dimension(800, 420));
								if(UserInfo.EmployeeCode.equals("000645")){
									lookupClient.setSizeDialog(new Dimension(600, 420));
								}
								lookupClient.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											String entity_id = (String) data[1];
											String proj_id = (String) data[7];
											String pbl_id = (String) data[4];
											Integer seq_no = (Integer) data[5];

											System.out.printf("Display Entity ID: %s%n", entity_id);
											System.out.printf("Display Entity ID: %s%n", proj_id);
											System.out.printf("Display PBL ID: %s%n", pbl_id);
											System.out.printf("Display Seq No: %s%n", seq_no);

											displayClientDetails(entity_id, proj_id, pbl_id, seq_no, true);
											//displayTabs(entity_id, proj_id, pbl_id, seq_no, true);
											dateDues.setDate(Calendar.getInstance().getTime());
											displayTabDetails(entity_id, proj_id, pbl_id, seq_no, true);

											hdmfInfo_maintab.displayLoanFilingStatus(entity_id, proj_id, pbl_id, seq_no.toString());
											hdmfInfo_maintab.displayHDMFUnitInspection(entity_id, proj_id, pbl_id, seq_no.toString());
											hdmfInfo_maintab.displayHDMFPayments(entity_id, proj_id, pbl_id, seq_no.toString());
											hdmfInfo_maintab.displayHDMFSchedule(entity_id, proj_id, pbl_id, seq_no.toString());
											hdmfInfo_maintab.displayHDMFLoanReleasedDetail(entity_id, proj_id, pbl_id, seq_no.toString());
										}
									}
								});
							}
						}
						{
							txtClientName = new _JXTextField();
							pnlClient.add(txtClientName, BorderLayout.CENTER);
							//txtClientName.getDocument().addDocumentListener(this);
						}
					}*/
					{
						JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlProject);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlProject.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(100, 20));
							{
								JXLabel lblProject = new JXLabel("Project");
								pnlWest.add(lblProject, BorderLayout.WEST);
								lblProject.setPreferredSize(new Dimension(60, 20));
							}
							{
								txtProjectID = new JXTextField();
								pnlWest.add(txtProjectID, BorderLayout.CENTER);
								txtProjectID.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							txtProjectName = new _JXTextField();
							pnlProject.add(txtProjectName, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlDesccription = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlDesccription);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlDesccription.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(100, 20));
							{
								JXLabel lblDesccription = new JXLabel("Unit / Seq.");
								pnlWest.add(lblDesccription, BorderLayout.WEST);
								lblDesccription.setToolTipText("Unit / Sequence");
								lblDesccription.setPreferredSize(new Dimension(60, 20));
							}
							{
								txtPblID = new JXTextField();
								pnlWest.add(txtPblID, BorderLayout.CENTER);
								txtPblID.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							txtPblDescription = new _JXTextField();
							pnlDesccription.add(txtPblDescription, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlModel = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlModel);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlModel.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(100, 20));
							{
								JXLabel lblModel = new JXLabel("Model");
								pnlWest.add(lblModel, BorderLayout.WEST);
								lblModel.setBounds(8, 113, 60, 20);
								lblModel.setPreferredSize(new Dimension(60, 20));
							}
							{
								txtModelID = new JXTextField();
								pnlWest.add(txtModelID, BorderLayout.CENTER);
								txtModelID.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							txtModelName = new _JXTextField();
							pnlModel.add(txtModelName, BorderLayout.CENTER);
						}
					}
					{
						pnlModelName = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlModelName);
						pnlModelName.setVisible(false);
						//pnlSeparator.setVisible(false);
						{
							JPanel pnlSeparator = new JPanel(new BorderLayout(3, 3));
							pnlModelName.add(pnlSeparator, BorderLayout.WEST);
							pnlSeparator.setPreferredSize(new Dimension(100, 20));
							{
								pnlSeparator.add(Box.createHorizontalBox(), BorderLayout.WEST);
								pnlSeparator.add(Box.createHorizontalBox(), BorderLayout.CENTER);
							}
						}
						{
							txtModelName2 = new _JXTextField();
							pnlModelName.add(txtModelName2, BorderLayout.CENTER);
							//txtModelName2.setVisible(false);
						}
					}
					{
						JPanel pnlBuyerType = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlBuyerType);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlBuyerType.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(100, 20));
							{
								JXLabel lblBuyerType = new JXLabel("Byr. Type");
								pnlWest.add(lblBuyerType, BorderLayout.WEST);
								lblBuyerType.setToolTipText("Buyer Type");
								lblBuyerType.setPreferredSize(new Dimension(60, 20));
							}
							{
								txtBuyerTypeID = new JXTextField();
								pnlWest.add(txtBuyerTypeID, BorderLayout.CENTER);
								txtBuyerTypeID.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							txtBuyerTypeName = new _JXTextField();
							pnlBuyerType.add(txtBuyerTypeName, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlPmtScheme = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlPmtScheme);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlPmtScheme.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(100, 20));
							{
								JXLabel lblPmtScheme = new JXLabel("Pmt. Sch.");
								pnlWest.add(lblPmtScheme, BorderLayout.WEST);
								lblPmtScheme.setToolTipText("Payment Scheme");
								lblPmtScheme.setPreferredSize(new Dimension(60, 20));
							}
							{
								txtPmtSchemeID = new JXTextField();
								pnlWest.add(txtPmtSchemeID, BorderLayout.CENTER);
								txtPmtSchemeID.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							txtPmtSchemeName = new _JXTextField();
							pnlPmtScheme.add(txtPmtSchemeName, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlStatus = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlStatus);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlStatus.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(100, 20));
							{
								JXLabel lblStatus = new JXLabel("Status");
								pnlWest.add(lblStatus, BorderLayout.WEST);
								lblStatus.setPreferredSize(new Dimension(60, 20));
							}
							{
								txtStatusID = new JXTextField();
								pnlWest.add(txtStatusID, BorderLayout.CENTER);
								txtStatusID.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							txtStatusName = new _JXTextField();
							pnlStatus.add(txtStatusName, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlPmtStatus = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlPmtStatus);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlPmtStatus.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(140, 20));
							{
								JXLabel lblPmtStage = new JXLabel("Pmt. Sta.");
								pnlWest.add(lblPmtStage, BorderLayout.WEST);
								lblPmtStage.setToolTipText("Payment Stage");
								lblPmtStage.setPreferredSize(new Dimension(60, 20));
							}
							{
								txtPmtStage = new JXTextField();
								pnlWest.add(txtPmtStage, BorderLayout.CENTER);
								txtPmtStage.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							txtPmtStatusRemarks = new _JXTextField();
							pnlPmtStatus.add(txtPmtStatusRemarks);
							txtPmtStatusRemarks.setBounds(122, 73, 79, 20);
						}
					}
				}
				{
					JPanel pnlNorthEast = new JPanel(new BorderLayout());
					pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
					//pnlNorthEast.setBorder(LINE_BORDER);
					pnlNorthEast.setPreferredSize(new Dimension(450, 177));
					{
						JPanel pnlNorthEastCenter = new JPanel(new BorderLayout(1, 1));
						pnlNorthEast.add(pnlNorthEastCenter, BorderLayout.CENTER);
						{
							JPanel pnlLabels = new JPanel(grid);
							pnlNorthEastCenter.add(pnlLabels, BorderLayout.WEST);
							pnlLabels.setPreferredSize(new Dimension(150, 177));
							/*{
								pnlLabels.add(Box.createHorizontalBox());
							}*/
							{
								JXLabel lblGSP = new JXLabel("GSP");
								pnlLabels.add(lblGSP);
							}
							{
								JXLabel lblDiscount = new JXLabel("Discount");
								pnlLabels.add(lblDiscount);
							}
							{
								JXLabel lblVAT = new JXLabel("VAT");
								pnlLabels.add(lblVAT);
							}
							{
								JXLabel lblNSP = new JXLabel("NSP");
								pnlLabels.add(lblNSP);
							}
							{
								JXLabel lblDP = new JXLabel("DP Equity");
								pnlLabels.add(lblDP);
							}
							{
								JXLabel lblMiscFee15Pct = new JXLabel("15% Misc Fee");
								pnlLabels.add(lblMiscFee15Pct);
							}
							{
								JXLabel lblTotalCashOutLay = new JXLabel("Total Cash Outlay");
								pnlLabels.add(lblTotalCashOutLay);
							}
							{
								JXLabel lblBalance = new JXLabel("O/S Balance");
								pnlLabels.add(lblBalance);
							}
							{
								JXLabel lblMiscFeeBalance = new JXLabel("O/S Misc Fee Balance");
								pnlLabels.add(lblMiscFeeBalance);
							}
							{
								JXLabel lblLoanableAmount = new JXLabel("Loanable Amt.");
								pnlLabels.add(lblLoanableAmount);
							}
							/*{
								JXLabel lblHouseConstructed = new JXLabel("House Cons");
								pnlLabels.add(lblHouseConstructed);
							}
							{
								JXLabel lblTurnOver = new JXLabel("Turn Over");
								pnlLabels.add(lblTurnOver);
							}*/
						}
						{
							JPanel pnFields = new JPanel(grid);
							pnlNorthEastCenter.add(pnFields, BorderLayout.CENTER);
							/*{
								pnFields.add(Box.createHorizontalBox());
							}*/
							{
								txtGSP = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtGSP);
								txtGSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtDiscount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtDiscount);
								txtDiscount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtVAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtVAT);
								txtVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtNSP = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtNSP);
								txtNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtDP = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtDP);
								txtDP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtMiscFee15Pct = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtMiscFee15Pct);
								txtMiscFee15Pct.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtTotalCashOutlay = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtTotalCashOutlay);
								txtTotalCashOutlay.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtBalance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtBalance);
								txtBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtMiscFeeBalance = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtMiscFeeBalance);
								txtMiscFeeBalance.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								txtLoanableAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtLoanableAmount);
								txtLoanableAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							/*{
								dteHouseConstructed = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnFields.add(dteHouseConstructed);
								dteHouseConstructed.getCalendarButton().setVisible(false);
							}
							{
								dteTurnOver = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnFields.add(dteTurnOver);
								dteTurnOver.getCalendarButton().setVisible(false);
							}*/
						}
					}
					{
						JPanel pnlNorthEastTab = new JPanel(new BorderLayout(1, 1));
						pnlNorthEast.add(pnlNorthEastTab, BorderLayout.EAST);
						pnlNorthEastTab.setPreferredSize(new Dimension(200, 177));
						{
							JPanel pnlLabels = new JPanel(grid);
							pnlNorthEastTab.add(pnlLabels, BorderLayout.WEST);
							pnlLabels.setPreferredSize(new Dimension(95, 177));
							{
								JXLabel lblDPAmount = new JXLabel("DP Amount");
								pnlLabels.add(lblDPAmount);
							}
							{
								JXLabel lblDPRateTerm = new JXLabel("DP Rate/Term");
								pnlLabels.add(lblDPRateTerm);
							}
							{
								JXLabel lblMAAmount = new JXLabel("MA Amount");
								pnlLabels.add(lblMAAmount);
							}
							{
								JXLabel lblMARateTerm = new JXLabel("MA Rate/Term");
								pnlLabels.add(lblMARateTerm);
							}
							{
								JXLabel lblBankTerm = new JXLabel("Bank Loan Term");
								pnlLabels.add(lblBankTerm);
							}
							{
								JXLabel lblFactorRate = new JXLabel("Factor");
								pnlLabels.add(lblFactorRate);
							}
							{
								JXLabel lblIntRate = new JXLabel("Int. Rate");
								pnlLabels.add(lblIntRate);
							}
							/*	Added by Mann2x; Date Added: October 18, 2017; As requested by Mr. Liao;	*/
							{
								JXLabel lblBIRDate = new JXLabel("BOI Ruling Date");
								pnlLabels.add(lblBIRDate);
							}
							{
								JXLabel lblHLID = new JXLabel("HLID No.");
								pnlLabels.add(lblHLID);
							}
							/*{
								JXLabel lblWithNTC = new JXLabel("With NTC");
								pnlLabels.add(lblWithNTC);
							}
							{
								JXLabel lblMovedIn = new JXLabel("Moved In");
								pnlLabels.add(lblMovedIn);
							}
							{
								JXLabel lblMovedOut = new JXLabel("Moved Out");
								pnlLabels.add(lblMovedOut);
							}*/
						}
						{
							JPanel pnFields = new JPanel(grid);
							pnlNorthEastTab.add(pnFields, BorderLayout.CENTER);
							{
								txtDPAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtDPAmount);
								txtDPAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								JPanel pnlDPRateTerm = new JPanel(new GridLayout(1, 2, 1, 1));
								pnFields.add(pnlDPRateTerm);
								{
									txtDPRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlDPRateTerm.add(txtDPRate);
									txtDPRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtDPTerm = new _JXFormattedTextField(JXFormattedTextField.CENTER);
									pnlDPRateTerm.add(txtDPTerm);
									txtDPTerm.setFormatterFactory(_JXFormattedTextField.INTEGER);
								}
							}
							{
								txtMAAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnFields.add(txtMAAmount);
								txtMAAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								JPanel pnlMARateTerm = new JPanel(new GridLayout(1, 2, 1, 1));
								pnFields.add(pnlMARateTerm);
								{
									txtMARate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlMARateTerm.add(txtMARate);
									txtMARate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtMATerm = new _JXFormattedTextField(JXFormattedTextField.CENTER);
									pnlMARateTerm.add(txtMATerm);
									txtMATerm.setFormatterFactory(_JXFormattedTextField.INTEGER);
								}
							}
							{
								txtBankTerm = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
								pnFields.add(txtBankTerm);
								txtBankTerm.setFormatterFactory(_JXFormattedTextField.INTEGER);
							}
							{
								txtFactorRate = new _JXTextField();
								pnFields.add(txtFactorRate);
								txtFactorRate.setFont(UIManager.getFont("FormattedTextField.background"));
								txtFactorRate.setBackground(UIManager.getColor("FormattedTextField.background"));
								txtFactorRate.setHorizontalAlignment(JXTextField.RIGHT);
								txtFactorRate.setName("Factor");
							}
							{
								txtIntRate = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnFields.add(txtIntRate);
								txtIntRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							
							/*{
								dteWithNTC = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnFields.add(dteWithNTC);
								dteWithNTC.getCalendarButton().setVisible(false);
							}
							{
								dteMovedIn = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnFields.add(dteMovedIn);
								dteMovedIn.getCalendarButton().setVisible(false);
							}
							{
								dteMovedOut = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnFields.add(dteMovedOut);
								dteMovedOut.getCalendarButton().setVisible(false);
							}*/
						}
					}
				}
				{
					JPanel pnlSellingAgent = new JPanel(new BorderLayout(1, 1));
					pnlNorth.add(pnlSellingAgent, BorderLayout.SOUTH);
					//pnlSellingAgent.setPreferredSize(new Dimension(0, 19));
					{
						JPanel pnlWest = new JPanel(new BorderLayout(1, 1));
						pnlSellingAgent.add(pnlWest, BorderLayout.WEST);
						pnlWest.setPreferredSize(new Dimension(140, 20));
						if(UserInfo.EmployeeCode.equals("000645")){
							pnlWest.setPreferredSize(new Dimension(140, 15));
						}
						{
							JXLabel lblSellingAgent = new JXLabel("Agent");
							pnlWest.add(lblSellingAgent, BorderLayout.WEST);
							lblSellingAgent.setToolTipText("Payment Status");
							//lblSellingAgent.setPreferredSize(new Dimension(60, 20));
						}
						{
							txtSellingAgentID = new JXTextField();
							pnlWest.add(txtSellingAgentID, BorderLayout.CENTER);
							txtSellingAgentID.setHorizontalAlignment(JTextField.CENTER);
						}
					}
					{
						txtSellingAgentName = new _JXTextField();
						pnlSellingAgent.add(txtSellingAgentName);
						txtSellingAgentName.setToolTipText("Agent / Division Group / BDO");
						//txtSellingAgentName.setBounds(122, 73, 79, 20);
					}
				}
			}
			{//XXX
				tabCenter = new JTabbedPane();
				//tabCenter.setPreferredSize(new Dimension(150, 0));
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				if(UserInfo.EmployeeCode.equals("000645")){
					tabCenter.setFont(fontSmallTab.deriveFont(1));
				}
				{
					JSplitPane splitSchedule = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
					tabCenter.addTab("Sched./Ledger", null, splitSchedule, null);
					//tabCenter.add(splitSchedule);
					/*JLabel lblSche_Ledger = new JLabel("Sched./Ledger");
					lblSche_Ledger.setPreferredSize(new Dimension(50, 20));
					tabCenter.setTabComponentAt(0, lblSche_Ledger);*/
					//splitSchedule.setBounds(getNormalBounds());
					//splitSchedule.setPreferredSize(new Dimension(150, 0));
					//tabCenter.addTab("<html><body><table width='2'>Sched./Ledger</table></body></html>", null, splitSchedule, null);
					//splitPayments.add(splitPaymentOthers, JSplitPane.RIGHT);
					splitSchedule.setOneTouchExpandable(true);
					splitSchedule.setResizeWeight(.7d);
					if(UserInfo.EmployeeCode.equals("000645")){
						splitSchedule.setResizeWeight(.6d);
					}
					{
						scrollSchedule = new JScrollPane();
						splitSchedule.add(scrollSchedule, JSplitPane.LEFT);
						scrollSchedule.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollSchedule.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblSchedule.clearSelection();
							}
						});
						{
							modelSchedule = new modelCARD_Schedule();
							modelSchedule.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderSchedule.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderSchedule.getModel()).addElement(modelSchedule.getRowCount());
									}
								}
							});

							tblSchedule = new _JTableMain(modelSchedule);
							scrollSchedule.setViewportView(tblSchedule);
							tblSchedule.setSortable(false);
							tblSchedule.hideColumns("Part ID", "UPICO");
							tblSchedule.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
							tblSchedule.setHorizontalScrollEnabled(true);


							tblSchedule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){

									}
								}
							});

							/*tblSchedule.addMouseListener(new MouseAdapter() {
								public void mouseReleased(MouseEvent e) {
									if(e.isPopupTrigger()){
										try {
											initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
										} catch (NullPointerException e1) { }
									}
								}
								public void mousePressed(MouseEvent e) {
									if(e.isPopupTrigger()){
										try {
											initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
										} catch (NullPointerException e1) { }
									}
								}

								public JPopupMenu initializeMenu(MouseEvent e) {
									final ArrayList<Boolean> hasSettled = new ArrayList<Boolean>();
									final _JTableMain table = (_JTableMain) e.getSource();
									final int[] rows = table.getSelectedRows();

									final String entity_id = lookupClient.getValue();
									final String entity_name = getValue(txtClientName.getText());
									final String proj_id = txtProjectID.getText();
									final String proj_name = getValue(txtProjectName.getText());
									final String pbl_id = txtPblID.getText();
									final String unit_description = getValue(txtPblDescription.getText());
									final Integer seq_no  = getSequence();
									final String model_id = txtModelID.getText();
									final String model_name = getValue(txtModelName.getText());
									final BigDecimal selling_price = (BigDecimal) txtGSP.getValued();

									final Object[][] data = new Object[rows.length][16];
									for(int x =0; x < rows.length; x++){
										int row = table.getModelRow(rows[x]);

										String part_id = (String) modelSchedule.getValueAt(row, 0);
										String particular = (String) modelSchedule.getValueAt(row, 1);
										BigDecimal amount = (BigDecimal) modelSchedule.getValueAt(row, 3);
										Boolean settled = (Boolean) modelSchedule.getValueAt(row, 13);
										String receipt_id = "";
										String receipt_type = "";
										//System.out.printf("%s: %s%n", part_id, settled);

										if(part_id.equals("012")){
											part_id = "106";
											particular = "RESERVATION";
											receipt_id = "03";
											receipt_type = "AR";
										}
										if(part_id.equals("013")){
											part_id = "033";
											particular = "DOWN PAYMENT";
											if (subprojHasBOI_LTS(pbl_id)==true)
											{
												receipt_id = "01";
												receipt_type = "OR";
											}
											else
											{
												receipt_id = "03";
												receipt_type = "AR";
											}											
										}
										if(part_id.equals("014")){
											part_id = "040";
											particular = "MONTHLY AMORTIZATION";
											if (subprojHasBOI_LTS(pbl_id)==true)
											{
												receipt_id = "01";
												receipt_type = "OR";
											}
											else
											{
												receipt_id = "03";
												receipt_type = "AR";
											}						
										}

										hasSettled.add(settled);
										if(!settled){
											data[x] = new Object[]{part_id, particular, amount, "CASH", null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_type, null, null, null};
										}
									}

									JMenuItem menuViewRequests = new JMenuItem("Proceed to Order of Payments");
									menuViewRequests.setFont(menuViewRequests.getFont().deriveFont(10f));
									menuViewRequests.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(FncGlobal.homeMDI.isNotExisting("OrderOfPayment")){
												OrderOfPayment oop = new OrderOfPayment(entity_id, entity_name, proj_id, proj_name, pbl_id, unit_description, seq_no, model_id, model_name, selling_price, data);
												Home_JSystem.addWindow(oop, null);

												oop.displayFromCARD(entity_id, entity_name, proj_id, proj_name, pbl_id, unit_description, seq_no, model_id, model_name, selling_price, data);
											}
										}
									});

									JPopupMenu menu = new JPopupMenu();
									menu.add(menuViewRequests);
									if(hasSettled.contains(true)){
										return null;
									}else{
										return menu;
									}
								}
							});*/
						}
						{
							rowheaderSchedule = tblSchedule.getRowHeader();
							rowheaderSchedule.setModel(new DefaultListModel());
							scrollSchedule.setRowHeaderView(rowheaderSchedule);
							scrollSchedule.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						//UIManager.put("TabbedPane.tabInsets", new Insets(2, 5, 1, 5));
						tabLedger = new JTabbedPane();
						splitSchedule.add(tabLedger, JSplitPane.RIGHT);
						//tabLedger.setTabPlacement(JTabbedPane.LEFT);
						//UIManager.put("TabbedPane.tabInsets", new Insets(2, 20, 1, 20));
						{
							JPanel pnlCenterTable = new JPanel(new BorderLayout());
							//tabLedger.addTab("<html>D<br>u<br>e<br>s</html>", null, pnlCenterTable, null);
							_JVerticalTextIcon duesIcon = new _JVerticalTextIcon(pnlCenterTable, "Dues", _JVerticalTextIcon.ROTATE_LEFT);
							tabLedger.addTab(null, duesIcon, pnlCenterTable, null);
							tabLedger.setTabPlacement(JTabbedPane.LEFT);
							tabLedger.setPreferredSize(new Dimension(20, 100));
							{
								JPanel pnlDuesNorth = new JPanel(new BorderLayout(3, 3));
								pnlCenterTable.add(pnlDuesNorth, BorderLayout.NORTH);
								pnlDuesNorth.setPreferredSize(new Dimension(776, 32));
								pnlDuesNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
								{
									JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
									pnlDuesNorth.add(pnlWest, BorderLayout.WEST);
									pnlWest.setPreferredSize(new Dimension(300, 30));
									{
										JXLabel lblDues = new JXLabel("Dues if CLIENT will pay on", JLabel.TRAILING);
										pnlWest.add(lblDues, BorderLayout.WEST);
									}
									{
										dateDues = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlWest.add(dateDues, BorderLayout.CENTER);
										//dateDues.setDate(Calendar.getInstance().getTime());
										dateDues.setDate(FncGlobal.getDateToday());
										dateDues.setPreferredSize(new Dimension(100, 30));
										dateDues.addDateListener(new DateListener() {
											public void datePerformed(DateEvent event) {
												_JDateChooser dateChooser = (_JDateChooser) event.getSource();
												Date currentDate = dateChooser.getDate();

												String entity_id = lookupClient.getValue();
												String proj_id = txtProjectID.getText();
												String pbl_id = txtPblID.getText();
												Integer seq_no = getSequence();

												_CARD.displayDues(modelDues, entity_id, proj_id, pbl_id, seq_no, currentDate, modelDuesTotal, true);
												scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelDues.getRowCount())));
												tblDues.packAll(10);
												
												String exclude_date_string = null;
												SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
												chkExcludeLastSched.setSelected(false);
												dateExclude.setDate(null);

												if(modelDues.getRowCount() != 0){
													chkExcludeLastSched.setEnabled(true);
													Date exclude_date = (Date) modelDues.getValueAt(modelDues.getRowCount() - 1, 2);
													exclude_date_string = formatter.format(exclude_date);

													//chkExcludeLastSched.setText(String.format("%s%s", "Exclude Last Sched Date:", exclude_date_string));
													//dateExclude.setDateFormatString(exclude_date_string);
													dateExclude.setDate(exclude_date);

													txtAmtToUpdate.setValue(totalDues());
												}else{
													chkExcludeLastSched.setEnabled(false);
													txtAmtToUpdate.setValue(new BigDecimal("0.00"));
												}
												//tblDuesTotal.packAll();
											}
										});
									}
								}
								{
									JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 3, 3));
									pnlDuesNorth.add(pnlCenter, BorderLayout.CENTER);
									{
										JPanel pnlCenterWest = new JPanel(new BorderLayout(3, 3));
										pnlCenter.add(pnlCenterWest);
										{
											chkExcludeLastSched = new JCheckBox("Exclude Last Sched Date:");
											pnlCenterWest.add(chkExcludeLastSched, BorderLayout.WEST);
											chkExcludeLastSched.addItemListener(new ItemListener() {

												public void itemStateChanged(ItemEvent e) {
													Boolean selected = e.getStateChange() == ItemEvent.SELECTED;

													String entity_id = lookupClient.getValue();
													String proj_id = txtProjectID.getText();
													String pbl_id = txtPblID.getText();
													Integer seq_no = getSequence();

													txtAmtToUpdate.setValue(new BigDecimal("0.00"));
													chkExcludeLastSched.setText("Exclude Last Sched Date");

													if(lookupClient.getValue() != null){
														if(modelDues.getRowCount() != 0){
															//Date exclude_date = (Date) modelDues.getValueAt(modelDues.getRowCount() - 1, 2);

															if(selected){
																_CARD.displayDuesExcludeDate(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), dateExclude.getDate(), modelDuesTotal, true);
																/*String exclude_date_string = null;
																SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
																exclude_date_string = formatter.format(exclude_date);*/

																//chkExcludeLastSched.setText(String.format("%s%s", "Exclude Last Sched Date:", exclude_date_string));
																//lblAmtToUpdate.setText(String.format("%s%s", "Amt to Update: ", totalDues()));
																txtAmtToUpdate.setValue(totalDues());

															}else{
																_CARD.displayDues(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);
																/*String exclude_date_string = null;
																SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
																exclude_date_string = formatter.format(exclude_date);
																chkExcludeLastSched.setText(String.format("%s%s", "Exclude Last Sched Date:", exclude_date_string));*/
																txtAmtToUpdate.setValue(totalDues());
															}
														}else{
															_CARD.displayDues(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);
															/*Date exclude_date = (Date) modelDues.getValueAt(modelDues.getRowCount() - 1, 2);
															String exclude_date_string = null;
															SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
															exclude_date_string = formatter.format(exclude_date);

															//chkExcludeLastSched.setText(String.format("%s%s", "Exclude Last Sched Date:", exclude_date_string));
															 */														txtAmtToUpdate.setValue(totalDues());
														}
														scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblDues.getRowCount())));
														tblDues.packAll(10);
													}else{
														JOptionPane.showMessageDialog(ClientRequestOldDetails.this, "Please select client first", "", JOptionPane.INFORMATION_MESSAGE);
													}
												}
											});
										}
										{
											dateExclude = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
											pnlCenterWest.add(dateExclude, BorderLayout.CENTER);
											dateExclude.setPreferredSize(new Dimension(100, 0));
											dateExclude.getCalendarButton().setVisible(false);
										}
									}
									{
										JPanel pnlCenterEast = new JPanel(new BorderLayout(3, 3));
										pnlCenter.add(pnlCenterEast);
										{
											lblAmtToUpdate = new JXLabel("Amt to Update");
											pnlCenterEast.add(lblAmtToUpdate, BorderLayout.WEST);
										}
										{
											txtAmtToUpdate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlCenterEast.add(txtAmtToUpdate, BorderLayout.CENTER);
											txtAmtToUpdate.setPreferredSize(new Dimension(100, 0));
											txtAmtToUpdate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										}
									}

								}
								/*{
									JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
									pnlDuesNorth.add(pnlEast);

								}*/
							}
							{
								scrollDues = new _JScrollPaneMain();
								pnlCenterTable.add(scrollDues, BorderLayout.CENTER);
								{
									modelDues = new modelCARD_Dues();

									tblDues = new _JTableMain(modelDues);
									scrollDues.setViewportView(tblDues);
									tblDues.setSortable(false);

									tblDues.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
									if(UserInfo.EmployeeCode.equals("000645")){
										tblDues.getTableHeader().setFont(fontTable.deriveFont(1));
									}

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
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderDues.getModel()).addElement(modelDues.getRowCount());
												scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelDues.getRowCount())));
											}
											if(e.getType() == TableModelEvent.DELETE){
												if(modelDues.getRowCount() == 0){
													rowheaderDues.setModel(new DefaultListModel());
													scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelDues.getRowCount())));
												}
											}
										}
									});

									tblDues.addMouseListener(new MouseAdapter() {
										public void mouseReleased(MouseEvent e) {
											if(e.isPopupTrigger()){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}
										public void mousePressed(MouseEvent e) {
											if(e.isPopupTrigger()){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}

										public JPopupMenu initializeMenu(MouseEvent e) {
											final _JTableMain table = (_JTableMain) e.getSource();
											final int[] rows = table.getSelectedRows();

											final String entity_id = lookupClient.getValue();
											final String entity_name = getValue(txtClientName.getText());
											final String proj_id = txtProjectID.getText();
											final String proj_name = getValue(txtProjectName.getText());
											final String pbl_id = txtPblID.getText();
											final String unit_description = getValue(txtPblDescription.getText());
											final Integer seq_no  = getSequence();
											final String model_id = txtModelID.getText();
											final String model_name = getValue(txtModelName.getText());
											final BigDecimal selling_price = (BigDecimal) txtGSP.getValued();

											final Object[][] data = new Object[rows.length][14];
											for(int x =0; x < rows.length; x++){
												int row = table.getModelRow(rows[x]);

												String part_id = (String) modelDues.getValueAt(row, 0);
												String particular = (String) modelDues.getValueAt(row, 1);
												BigDecimal amount = (BigDecimal) modelDues.getValueAt(row, 14);

												if(part_id.equals("012")){
													part_id = "106";
													particular = "RESERVATION";
												}
												if(part_id.equals("013")){
													part_id = "033";
													particular = "DOWN PAYMENT";
												}
												if(part_id.equals("014")){
													part_id = "040";
													particular = "MONTHLY AMORTIZATION";
												}

												data[x] = new Object[]{part_id, particular, amount, "CASH", null, null, null, null, null, null, null, null, null, null, null, null, null};
											}

											JMenuItem menuViewRequests = new JMenuItem("Proceed to Order of Payments");
											menuViewRequests.setFont(menuViewRequests.getFont().deriveFont(10f));
											menuViewRequests.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent arg0) {
													if(FncGlobal.homeMDI.isNotExisting("OrderOfPayment")){
														OrderOfPayment oop = new OrderOfPayment();
														Home_JSystem.addWindow(oop, null);

														oop.displayFromCARD(entity_id, entity_name, proj_id, proj_name, pbl_id, unit_description, seq_no, model_id, model_name, selling_price, data);
													}
												}
											});

											JMenuItem menuRemovePayments = new JMenuItem("Remove selected payment(s)");
											menuRemovePayments.setFont(menuRemovePayments.getFont().deriveFont(10f));
											menuRemovePayments.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent arg0) {
													int[] selectedRows = table.getSelectedRows();

													if (selectedRows.length == 0) {
														JOptionPane.showMessageDialog(ClientRequestOldDetails.this, "Please select payment(s) to remove.", "Remove Payment(s)", JOptionPane.INFORMATION_MESSAGE);
														return;
													}

													System.out.printf("Selected Rows: %s (%s)%n", selectedRows[selectedRows.length - 1], table.getRowCount());

													if((table.getRowCount() - 1) > selectedRows[selectedRows.length - 1]){
														JOptionPane.showMessageDialog(ClientRequestOldDetails.this, "In-Between schedule cannot be remove.", "Remove Payment(s)", JOptionPane.INFORMATION_MESSAGE);
														return;
													}

													rowheaderDues.setModel(new DefaultListModel());
													for (int x = selectedRows.length - 1; x > -1; x--) {
														int row = selectedRows[x];
														modelDues.removeRow(row);
													}

													for(int x=1; x <= tblDues.getRowCount(); x++){
														((DefaultListModel)rowheaderDues.getModel()).addElement(x);
													}

													scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(table.getRowCount())));
													table.packAll();
													_CARD.totalEntries(modelDues, modelDuesTotal);
													table.packAll();
												}
											});

											/*JMenuItem menuApplyLedger = new JMenuItem("Apply on Ledger");
											menuApplyLedger.setFont(menuApplyLedger.getFont().deriveFont(10f));
											menuApplyLedger.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent arg0) {
													BigDecimal amount = new BigDecimal("0.00");

													for(int x=0; x < modelDues.getRowCount(); x++){
														amount = amount.add((BigDecimal) modelDues.getValueAt(x, 14));
													}
													String entity_id = lookupClient.getValue();
													String proj_id = txtProjectID.getText();
													String pbl_id = txtPblID.getText();
													Integer seq_no = getSequence();

													pgSelect db = new pgSelect();
													db.select("SELECT sp_apply_to_ledger('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", NULL, '040', "+ amount +", '99999', '"+ UserInfo.EmployeeCode +"', '"+ dateDues.getDate() +"', TRUE);");

													if(db.isNotNull()){
														JOptionPane.showMessageDialog(CARD.this, "Dues has been applied to ledger.", "Apply Ledger", JOptionPane.INFORMATION_MESSAGE);
													}
												}
											});*/

											JPopupMenu menu = new JPopupMenu();
											menu.add(menuViewRequests);
											menu.add(menuRemovePayments);
											//menu.add(menuApplyLedger);
											return menu;
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
								pnlCenterTable.add(scrollDuesTotal, BorderLayout.SOUTH);
								{
									modelDuesTotal = new modelCARD_Dues();
									modelDuesTotal.addRow(new Object[] { null, "Totals", null, null, null, null, null, null, null, null, null, null, null, null, 0.00 });

									tblDuesTotal = new _JTableTotal(modelDuesTotal, tblDues);
									scrollDuesTotal.setViewportView(tblDuesTotal);

									tblDuesTotal.setTotalLabel(1);
								}
							}
						}
						{
							JPanel pnlLedgerMain = new JPanel(new BorderLayout());
							//lblLedger.setUI(new VerticalLa);
							//tabLedger.setComponentAt(0, lblLedger);
							_JVerticalTextIcon iconLedger = new _JVerticalTextIcon(pnlLedgerMain, "Ldgr", _JVerticalTextIcon.ROTATE_LEFT);
							tabLedger.addTab(null, iconLedger, pnlLedgerMain, null);
							//tabLedger.addTab(lbl, icon, component, tip);
							{
								JPanel pnlLedgerNorth = new JPanel(new BorderLayout());
								pnlLedgerMain.add(pnlLedgerNorth, BorderLayout.NORTH);
								pnlLedgerNorth.setPreferredSize(new Dimension(776, 30));
								pnlLedgerNorth.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 5));
								{
									chkShowRefund = new JCheckBox("Show Ledger Before Refund");
									pnlLedgerNorth.add(chkShowRefund, BorderLayout.WEST);
									chkShowRefund.addItemListener(new ItemListener() {

										public void itemStateChanged(ItemEvent e) {
											Boolean selected = e.getStateChange() == ItemEvent.SELECTED;

											String entity_id = lookupClient.getValue();
											String proj_id = txtProjectID.getText();
											String pbl_id = txtPblID.getText();
											Integer seq_no = getSequence();

											if(lookupClient.getValue() != null){
												if(selected){
													_CARD.displayLedgerRefund(modelLedger, entity_id, proj_id, pbl_id, seq_no, true);
												}else{
													_CARD.displayLedger(modelLedger, entity_id, proj_id, pbl_id, seq_no, true);
												}
												scrollLedger.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLedger.getRowCount())));
												tblLedger.packAll();
											}else{
												//chkShowRefund.setSelected(false);
												
												JOptionPane.showMessageDialog(ClientRequestOldDetails.this, "Please select client first", "", JOptionPane.INFORMATION_MESSAGE);
											}

										}
									});
								}
							}
							{
								scrollLedger = new JScrollPane();
								pnlLedgerMain.add(scrollLedger, BorderLayout.CENTER);
								//tabLedger.addTab("<html>L<br>2</html>", null, scrollLedger2, null);
								//tabLedger.addTab("Ledger", null, scrollLedger, null);
								scrollLedger.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollLedger.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblLedger.clearSelection();
									}
								});
								{
									modelLedger = new modelCARD_Ledger();
									modelLedger.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent arg0) {
											if(arg0.getType() == TableModelEvent.DELETE){
												rowheaderLedger.setModel(new DefaultListModel());
											}

											if(arg0.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderLedger.getModel()).addElement(modelLedger.getRowCount());
											}
										}
									});

									tblLedger = new _JTableMain(modelLedger);
									scrollLedger.setViewportView(tblLedger);
									tblLedger.setSortable(false);
									tblLedger.hideColumns("CBP", "Adjustment");

									tblLedger.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
									tblLedger.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
									tblLedger.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
									if(UserInfo.EmployeeCode.equals("000645")){
										tblLedger.getTableHeader().setFont(fontTable.deriveFont(1));
									}
								}
								{
									rowheaderLedger = tblLedger.getRowHeader();
									rowheaderLedger.setModel(new DefaultListModel());
									scrollLedger.setRowHeaderView(rowheaderLedger);
									scrollLedger.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							//pnlLedger1 = new JPanel();
							//tabLedger.addTab("<html>L<br>1</html>", null, pnlLedger1, null);
							//tabLedger.addTab("Ledger 1", null, pnlLedger1, null);
						}
						tabLedger.setSelectedIndex(1);
					}
				}
				{
					JPanel pnlPayments = new JPanel(new BorderLayout());
					tabCenter.addTab("Payments", null, pnlPayments, null);
					{
						JXLabel lblLegend = new JXLabel("** R - check replacement / C - credited from payment / T - credited to payment / F - forfeited");
						pnlPayments.add(lblLegend, BorderLayout.NORTH);
						lblLegend.setForeground(Color.RED);
						lblLegend.setFont(UIManager.getFont("Table.font"));
						lblLegend.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					}
					{
						JSplitPane splitPayments = new JSplitPane();
						pnlPayments.add(splitPayments, BorderLayout.CENTER);
						splitPayments.setOneTouchExpandable(true);
						splitPayments.setResizeWeight(.7d);
						{
							scrollPayments = new JScrollPane();
							splitPayments.add(scrollPayments, JSplitPane.LEFT);
							scrollPayments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollPayments.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									try {
										tblPayments.clearSelection();
									} catch (IndexOutOfBoundsException e1) { }
								}
							});
							{
								modelPayments = new modelCARD_Payments();
								modelPayments.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowheaderPayments.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowheaderPayments.getModel()).addElement(modelPayments.getRowCount());
										}
									}
								});

								tblPayments = new _JTableMain(modelPayments);
								scrollPayments.setViewportView(tblPayments);
								tblPayments.setSortable(false);
								tblPayments.hideColumns("Rec ID", "Request No");
								tblPayments.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
								tblPayments.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
								tblPayments.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
								tblPayments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent e) {
										if(!e.getValueIsAdjusting()){//XXX Payments
											try {
												Integer row = tblPayments.convertRowIndexToModel(tblPayments.getSelectedRow());
												//String remarks = (String) modelPayments.getValueAt(row, 5);
												//String remarks = (String) modelPayments.getValueAt(row, 17);
												//Modified By Mann2x; September 22, 2016;
												String remarks = (String) modelPayments.getValueAt(row, 7);
												Integer pay_rec_id = (Integer) modelPayments.getValueAt(row, 22);
												//Integer pay_rec_id = (Integer) modelPayments.getValueAt(row, 20);

												String request_no = (String) modelPayments.getValueAt(row, 26);

												System.out.printf("Display value of remarks: %s%n", remarks);

												_CARD.displayCheckHistory(modelCheckHistory, Integer.toString(pay_rec_id));
												scrollCheckHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCheckHistory.getRowCount())));
												tblCheckHistory.packColumns("Date", "Status", "Rec", "Deposit", "Reason(for Bounce Checks)");

												//DISPLAYS PMT HISTORY
												_CARD.displayPmtHistory(modelCreditOfPayment, lookupClient.getValue(), txtProjectID.getText(), txtPblID.getText(), getSequence(), pay_rec_id, request_no, true);
												scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));
												tblCreditOfPayment.packAll();

												try{
													if(remarks.equals("C") || remarks.equals("T")){
														if(request_no.equals("")){
															tblCreditOfPayment.showColumns("Check No.", "Check Date", "OR #", "Acct. #", "Bank", "Branch", "PR #");
														}else{
															tblCreditOfPayment.hideColumns("Check No.", "Check Date", "OR #", "Acct. #", "Bank", "Branch", "PR #");
														}
													}
												}catch (NullPointerException e1){

												}


												/*try {
													if(remarks.equals("C")){
														//_CARD.displayCreditOfPayment(modelCreditOfPayment, pay_rec_id, true);
														scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));
														tblCreditOfPayment.showColumns("Check No.", "Check Date", "OR #", "Acct. #", "Bank", "Branch", "PR #");
													}else if(request_no.equals("") == false){

													}


													if(remarks.equals("C") || remarks.equals("T")){
														if(request_no.equals("")){
															//_CARD.displayCreditOfPayment(modelCreditOfPayment, pay_rec_id, true);
															scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));
															tblCreditOfPayment.showColumns("Check No.", "Check Date", "OR #", "Acct. #", "Bank", "Branch", "PR #");
														}else{
															_CARD.displayCreditOfPmtRequest(modelCreditOfPayment, lookupClient.getValue(), txtProjectID.getText(), txtPblID.getText(), getSequence() , pay_rec_id, request_no, true);
															scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));
															tblCreditOfPayment.hideColumns("Check No.", "Check Date", "OR #", "Acct. #", "Bank", "Branch", "PR #");
														}

														if(refund_date != null){

														}
														tblCreditOfPayment.packAll();
														//tblCreditOfPayment.packColumns("ID", "Status", "Trans. Date", "Actual Date", "Gross SP", "Discount", "VAT", "Net SP", "Request No", "Stat");
													}else{
														//modelCreditOfPayment.clear();
														//scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));
													}
													_CARD.displayPmtHistory(modelCreditOfPayment, lookupClient.getValue(), txtProjectID.getText(), txtPblID.getText(), getSequence(), pay_rec_id, request_no, true);
													scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));
													tblCreditOfPayment.hideColumns("Check No.", "Check Date", "OR #", "Acct. #", "Bank", "Branch", "PR #");

												} catch (NullPointerException e1) {
													modelCreditOfPayment.clear();
													scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));
												}*/
											} catch (ArrayIndexOutOfBoundsException e1) {
											} catch (IndexOutOfBoundsException e2) { }
										}
									}
								});

								tblPayments.addMouseListener(new MouseAdapter() {
									public void mouseReleased(MouseEvent e) {
										_JTableMain table = (_JTableMain) e.getSource();
										int[] rows = table.getSelectedRows();

										if(e.isPopupTrigger()){
											if(rows.length == 1){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}
									}
									public void mousePressed(MouseEvent e) {
										_JTableMain table = (_JTableMain) e.getSource();
										int[] rows = table.getSelectedRows();

										if(e.isPopupTrigger()){
											if(rows.length == 1){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}
									}

									public JPopupMenu initializeMenu(MouseEvent e) {
										final ArrayList<Boolean> hasSettled = new ArrayList<Boolean>();
										final _JTableMain table = (_JTableMain) e.getSource();

										final String pbl_id = txtPblID.getText();
										final Integer seq_no  = getSequence();

										int row = table.convertRowIndexToModel(table.getSelectedRow());
										System.out.printf("Selected: (%s, %s)%n", row, 14);

										String receipt_no = (String) modelPayments.getValueAt(row, 14);
										String receipt_type = "OR";
										if(receipt_no == null){
											receipt_no = (String) modelPayments.getValueAt(row, 16);
											receipt_type = "AR";
										}

										final String final_receipt_no = receipt_no;
										final String final_receipt_type = receipt_type;

										JMenuItem menuViewRequests = new JMenuItem("Preview Receipt Details/Entries");
										menuViewRequests.setFont(menuViewRequests.getFont().deriveFont(10f));
										menuViewRequests.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												if(FncGlobal.homeMDI.isNotExisting("CashReceiptBook")){
													CashReceiptBook crb = new CashReceiptBook(final_receipt_type, final_receipt_no, pbl_id, seq_no);
													Home_JSystem.addWindow(crb, null);
												}
											}
										});

										JPopupMenu menu = new JPopupMenu();
										menu.add(menuViewRequests);
										if(hasSettled.contains(true)){
											return null;
										}else{
											return menu;
										}
									}
								});
							}
							{
								rowheaderPayments = tblPayments.getRowHeader();
								rowheaderPayments.setModel(new DefaultListModel());
								scrollPayments.setRowHeaderView(rowheaderPayments);
								scrollPayments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							JSplitPane splitPaymentOthers = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
							splitPayments.add(splitPaymentOthers, JSplitPane.RIGHT);
							splitPaymentOthers.setOneTouchExpandable(true);
							splitPaymentOthers.setResizeWeight(.5d);
							{
								scrollCheckHistory = new JScrollPane();
								splitPaymentOthers.add(scrollCheckHistory, JSplitPane.LEFT);
								scrollCheckHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollCheckHistory.setBorder(JTBorderFactory.createTitleBorder("Check History", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									modelCheckHistory = new modelCARD_CheckHistory();
									modelCheckHistory.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderCheckHistory.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderCheckHistory.getModel()).addElement(modelCheckHistory.getRowCount());
											}
										}
									});

									tblCheckHistory = new _JTableMain(modelCheckHistory);
									scrollCheckHistory.setViewportView(tblCheckHistory);
									tblCheckHistory.setSortable(false);
									tblCheckHistory.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
								}
								{
									rowheaderCheckHistory = tblCheckHistory.getRowHeader();
									rowheaderCheckHistory.setModel(new DefaultListModel());
									scrollCheckHistory.setRowHeaderView(rowheaderCheckHistory);
									scrollCheckHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
							{

								scrollCreditOfPayment = new JScrollPane();
								splitPaymentOthers.add(scrollCreditOfPayment, JSplitPane.RIGHT);
								scrollCreditOfPayment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollCreditOfPayment.setBorder(JTBorderFactory.createTitleBorder("Payment History", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									modelCreditOfPayment = new modelCARD_CreditOfPayment();
									modelCreditOfPayment.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderCreditOfPayment.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderCreditOfPayment.getModel()).addElement(modelCreditOfPayment.getRowCount());
											}
										}
									});

									tblCreditOfPayment = new _JTableMain(modelCreditOfPayment);
									scrollCreditOfPayment.setViewportView(tblCreditOfPayment);
									tblCreditOfPayment.setSortable(false);
									tblCreditOfPayment.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));

									tblCreditOfPayment.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(!e.getValueIsAdjusting()){//XXX CreditOfPayment

											}
										}
									});
								}
								{
									rowheaderCreditOfPayment = tblCreditOfPayment.getRowHeader();
									rowheaderCreditOfPayment.setModel(new DefaultListModel());
									scrollCreditOfPayment.setRowHeaderView(rowheaderCreditOfPayment);
									scrollCreditOfPayment.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
					}
				}
				/*{
					JSplitPane splitDocuments = new JSplitPane();
					//tabCenter.add(splitDocuments,)
					tabCenter.addTab("Documents", null, splitDocuments, null);
					splitDocuments.setOneTouchExpandable(true);
					splitDocuments.setResizeWeight(.5d);*/
					{
						JTabbedPane tabDocuments = new JTabbedPane();
						tabCenter.addTab("Documents", null, tabDocuments, null);
						//splitDocuments.addTab("Documents", null, tabDocuments, null);
						{
							JXPanel pnlReservationDocuments = new JXPanel();
							tabDocuments.addTab("Reservation Documents", null, pnlReservationDocuments, null);
							//tabCenter.addTab("Documents", null, pnlDocuments, null);
							pnlReservationDocuments.setLayout(new BorderLayout());
							{
								JSplitPane splitReservationDouments = new JSplitPane();
								pnlReservationDocuments.add(splitReservationDouments);
								splitReservationDouments.setOneTouchExpandable(true);
								splitReservationDouments.setResizeWeight(.5d);

								{
									scrollReservationDocuments = new _JScrollPane();
									//pnlReservationDocuments.add(scrollReservationDocuments, BorderLayout.CENTER);
									splitReservationDouments.add(scrollReservationDocuments, JSplitPane.LEFT);
									scrollReservationDocuments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									scrollReservationDocuments.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											tblReservationDocuments.clearSelection();
										}
									});
									{
										modelReservationDocuments = new modelCARD_ReservationDocuments();
										modelReservationDocuments.addTableModelListener(new TableModelListener() {
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowheaderReservationDocuments.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowheaderReservationDocuments.getModel()).addElement(modelReservationDocuments.getRowCount());
												}
											}
										});

										/*modelResDocs = new modelCARD_ReservationDocs();
									modelResDocs.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderReservationDocuments.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderReservationDocuments.getModel()).addElement(modelResDocs.getRowCount());
											}
										}
									});*/

										tblReservationDocuments = new _JTableMain(modelReservationDocuments);
										scrollReservationDocuments.setViewportView(tblReservationDocuments);
										tblReservationDocuments.setSortable(false);
										tblReservationDocuments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

										/*tblReservationDocuments.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
									tblReservationDocuments.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf));
									tblReservationDocuments.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));*/

										tblReservationDocuments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if(!e.getValueIsAdjusting()){//XXX tblJVDetail

													if(tblReservationDocuments.getSelectedRows().length != 0){
														int selected_row = tblReservationDocuments.convertRowIndexToModel(tblReservationDocuments.getSelectedRow());

														String entity_id = lookupClient.getValue();
														String proj_id = txtProjectID.getText();
														String pbl_id = txtPblID.getText();
														Integer seq_no = getSequence();
														String doc_id = (String) modelReservationDocuments.getValueAt(selected_row, 0);
														String by = (String) modelReservationDocuments.getValueAt(selected_row, 1);

														if(by.equals("PA")){
															_CARD.displayDocsHistory(modelDocsHistory, entity_id, proj_id, pbl_id, seq_no, doc_id, true);
														}else{
															_CARD.displayDocsHistoryCoapp(modelDocsHistory, entity_id, proj_id, pbl_id, seq_no, doc_id, true);
														}

														scrollDocsHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocsHistory.getRowCount())));
														tblDocsHistory.packAll();
													}
												}
											}
										});
										tblReservationDocuments.hideColumns("ID" ,"Promisory Due", "Days Granted");
									}
									{
										rowheaderReservationDocuments = tblReservationDocuments.getRowHeader();
										rowheaderReservationDocuments.setModel(new DefaultListModel());
										scrollReservationDocuments.setRowHeaderView(rowheaderReservationDocuments);
										scrollReservationDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
								{
									JSplitPane splitDocsFindings = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
									//splitDocuments.add(splitDocsFindings, JSplitPane.RIGHT);
									splitReservationDouments.add(splitDocsFindings, JSplitPane.RIGHT);
									splitDocsFindings.setOneTouchExpandable(true);
									splitDocsFindings.setResizeWeight(.5d);
									{
										scrollDocsHistory = new _JScrollPane();
										splitDocsFindings.add(scrollDocsHistory, JSplitPane.LEFT);
										scrollDocsHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										scrollDocsHistory.setBorder(JTBorderFactory.createTitleBorder("Docs History"));
										{
											modelDocsHistory = new modelCARD_DocsHistory();
											tblDocsHistory = new _JTableMain(modelDocsHistory);
											scrollDocsHistory.setViewportView(tblDocsHistory);
											tblDocsHistory.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
											//tblDocsHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

											modelDocsHistory.addTableModelListener(new TableModelListener() {

												public void tableChanged(TableModelEvent e) {
													if(e.getType() == TableModelEvent.DELETE){
														rowHeaderDocsHistory.setModel(new DefaultListModel());
													}
													if(e.getType() == TableModelEvent.INSERT){
														((DefaultListModel)rowHeaderDocsHistory.getModel()).addElement(modelDocsHistory.getRowCount());
													}
												}
											});
										}
										{
											rowHeaderDocsHistory = tblDocsHistory.getRowHeader();
											rowHeaderDocsHistory.setModel(new DefaultListModel());
											scrollDocsHistory.setRowHeaderView(rowHeaderDocsHistory);
											scrollDocsHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										}
									}
									{
										scrollDocFindings = new _JScrollPane();
										splitDocsFindings.add(scrollDocFindings, JSplitPane.RIGHT);
										scrollDocFindings.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										scrollDocFindings.setBorder(JTBorderFactory.createTitleBorder("Docs Findings"));
										{
											modelDocFindings = new modelCARD_DocFindings();

											//tblDocsHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

											modelDocFindings.addTableModelListener(new TableModelListener() {

												public void tableChanged(TableModelEvent e) {
													if(e.getType() == TableModelEvent.DELETE){
														rowHeaderDocFindings.setModel(new DefaultListModel());
													}
													if(e.getType() == TableModelEvent.INSERT){
														((DefaultListModel)rowHeaderDocFindings.getModel()).addElement(modelDocFindings.getRowCount());
													}
												}
											});
											tblDocFindings = new _JTableMain(modelDocFindings);
											scrollDocFindings.setViewportView(tblDocFindings);
											tblDocFindings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											tblDocFindings.setSortable(false);
											//tblDocFindings.hideColumns("Rec. ID", "Emp. ID");
											TableColumnModel colModel = tblDocFindings.getColumnModel();
											TableColumn col = colModel.getColumn(1);
											col.setCellRenderer(new _JTextAreaRenderer2());
											col.setCellEditor(new _JTextAreaEditor());

											/*TableColumnModel colModel = tblFollowUp.getColumnModel();
								        TableColumn col = colModel.getColumn(1);
								        col.setCellRenderer(new _JTextAreaRenderer2());
								        col.setCellEditor(new _JTextAreaEditor());*/
											tblDocFindings.setRowHeight(100);
											tblDocFindings.setCellSelectionEnabled(true);
											tblDocFindings.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
											tblDocFindings.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf));
										}
										{
											rowHeaderDocFindings = tblDocFindings.getRowHeader();
											rowHeaderDocFindings.setFixedCellHeight(100);
											rowHeaderDocFindings.setModel(new DefaultListModel());
											scrollDocFindings.setRowHeaderView(rowHeaderDocFindings);
											scrollDocFindings.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										}
									}
								}
							}
						}
						{
							JXPanel pnlPrintedDocuments = new JXPanel();
							tabDocuments.addTab("Printed Documents", null, pnlPrintedDocuments, null);
							//tabCenter.addTab("Documents", null, pnlDocuments, null);
							pnlPrintedDocuments.setLayout(new BorderLayout());
							{
								scrollPrintedDocuments = new _JScrollPane();
								pnlPrintedDocuments.add(scrollPrintedDocuments, BorderLayout.CENTER);
								scrollPrintedDocuments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollPrintedDocuments.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblPrintedDocuments.clearSelection();
									}
								});
								{
									modelPrintedDocuments = new modelCARD_PrintedDocuments();
									modelPrintedDocuments.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderPrintedDocuments.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderPrintedDocuments.getModel()).addElement(modelPrintedDocuments.getRowCount());
											}
										}
									});

									tblPrintedDocuments = new _JTableMain(modelPrintedDocuments);
									scrollPrintedDocuments.setViewportView(tblPrintedDocuments);
									tblPrintedDocuments.setSortable(false);
									tblPrintedDocuments.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));

									tblPrintedDocuments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(!e.getValueIsAdjusting()){//XXX tblJVDetail

											}
										}
									});
									tblPrintedDocuments.hideColumns("ID", "Promisory Due", "Days Granted");
								}
								{
									rowheaderPrintedDocuments = tblPrintedDocuments.getRowHeader();
									rowheaderPrintedDocuments.setModel(new DefaultListModel());
									scrollPrintedDocuments.setRowHeaderView(rowheaderPrintedDocuments);
									scrollPrintedDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							JXPanel pnlRequestedDocs = new JXPanel(new GridLayout(2, 2, 3, 3));
							tabDocuments.addTab("Requested Documents", pnlRequestedDocs);
							{
								JPanel pnlReqDocDetails = new JPanel(new BorderLayout(3, 3));
								pnlRequestedDocs.add(pnlReqDocDetails);
								pnlReqDocDetails.setBorder(JTBorderFactory.createTitleBorder("Requested Docs Detail"));
								{
									scrollReqDocDetails = new _JScrollPane();
									pnlReqDocDetails.add(scrollReqDocDetails, BorderLayout.CENTER);
									scrollReqDocDetails.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									{
										modelReqDocDetails = new modelCARD_ReqDocDetails();
										modelReqDocDetails.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													System.out.println("Dumaan dito sa Select ng row");
													rowHeaderReqDocDetails.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowHeaderReqDocDetails.getModel()).addElement(modelReqDocDetails.getRowCount());
												}
											}
										});

										tblReqDocDetails = new _JTableMain(modelReqDocDetails);
										scrollReqDocDetails.setViewportView(tblReqDocDetails);
										tblReqDocDetails.setSortable(false);
										tblReqDocDetails.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
										tblReqDocDetails.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(sdf));
										tblReqDocDetails.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));

										tblReqDocDetails.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if(!e.getValueIsAdjusting()){
													if(tblReqDocDetails.getSelectedRows().length != 0){
														int selected_row = tblReqDocDetails.convertRowIndexToModel(tblReqDocDetails.getSelectedRow());

														String request_no = (String) modelReqDocDetails.getValueAt(selected_row, 0);

														_CARD.displayReqDocPurpose(modelReqDocPurpose, request_no);
														scrollReqDocPurpose.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReqDocPurpose.getRowCount())));
														tblReqDocPurpose.packAll();

														_CARD.displayRequestedDocuments(modelRequestedDocs, request_no);
														scrollRequestedDocs.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRequestedDocs.getRowCount())));
														tblRequestedDocs.packAll();

														modelReqDocsStatus.clear();
														scrollReqDocStatus.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReqDocStatus.getRowCount())));
														tblReqDocStatus.packAll();

													}
												}
											}
										});
									}
									{
										rowHeaderReqDocDetails = tblReqDocDetails.getRowHeader();
										rowHeaderReqDocDetails.setModel(new DefaultListModel());
										scrollReqDocDetails.setRowHeaderView(rowHeaderReqDocDetails);
										scrollReqDocDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}

								
							}
							{
								JPanel pnlReqPurpose = new JPanel(new BorderLayout(3, 3));
								pnlRequestedDocs.add(pnlReqPurpose);
								pnlReqPurpose.setBorder(JTBorderFactory.createTitleBorder("Purpose of Request"));
								{
									scrollReqDocPurpose = new _JScrollPane();
									pnlReqPurpose.add(scrollReqDocPurpose, BorderLayout.CENTER);
									scrollReqDocPurpose.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									{
										modelReqDocPurpose = new modelCARD_ReqDocPurpose();
										modelReqDocPurpose.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderReqDocPurpose.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowHeaderReqDocPurpose.getModel()).addElement(modelReqDocPurpose.getRowCount());
												}
											}
										});

										tblReqDocPurpose = new _JTableMain(modelReqDocPurpose);
										scrollReqDocPurpose.setViewportView(tblReqDocPurpose);
										tblReqDocPurpose.setSortable(false);

										tblReqDocPurpose.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if(!e.getValueIsAdjusting()){

												}
											}
										});
									}
									{
										rowHeaderReqDocPurpose = tblReqDocPurpose.getRowHeader();
										rowHeaderReqDocPurpose.setModel(new DefaultListModel());
										scrollReqDocPurpose.setRowHeaderView(rowHeaderReqDocPurpose);
										scrollReqDocPurpose.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
							}
							{
								JPanel pnlReqDocs = new JPanel(new BorderLayout(3, 3));
								pnlRequestedDocs.add(pnlReqDocs);
								pnlReqDocs.setBorder(JTBorderFactory.createTitleBorder("Requested Documents"));
								{
									scrollRequestedDocs = new _JScrollPane();
									pnlReqDocs.add(scrollRequestedDocs, BorderLayout.CENTER);
									scrollRequestedDocs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									{
										modelRequestedDocs = new modelCARD_RequestedDocs();
										modelRequestedDocs.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderRequestedDocs.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowHeaderRequestedDocs.getModel()).addElement(modelRequestedDocs.getRowCount());
												}
											}
										});

										tblRequestedDocs = new _JTableMain(modelRequestedDocs);
										scrollRequestedDocs.setViewportView(tblRequestedDocs);
										tblRequestedDocs.setSortable(false);

										tblRequestedDocs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if(!e.getValueIsAdjusting()){
													if(tblReqDocDetails.getSelectedRows().length != 0){
														int selected_row = tblReqDocDetails.convertRowIndexToModel(tblReqDocDetails.getSelectedRow());

														String request_no = (String) modelReqDocDetails.getValueAt(selected_row, 0);
														if(tblRequestedDocs.getSelectedRows().length != 0){
															int selected_row2 = tblRequestedDocs.convertColumnIndexToModel(tblRequestedDocs.getSelectedRow());

															String doc_id = (String) modelRequestedDocs.getValueAt(selected_row2, 0);

															_CARD.displayReqDocStatus(modelReqDocsStatus, request_no, doc_id);
															scrollReqDocStatus.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReqDocStatus.getRowCount())));
															tblReqDocStatus.packAll();
														}
													}
												}
											}
										});
									}
									{
										rowHeaderRequestedDocs = tblRequestedDocs.getRowHeader();
										rowHeaderRequestedDocs.setModel(new DefaultListModel());
										scrollRequestedDocs.setRowHeaderView(rowHeaderRequestedDocs);
										scrollRequestedDocs.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
							}
							{
								JPanel pnlReqDocStatus = new JPanel(new BorderLayout(3, 3));
								pnlRequestedDocs.add(pnlReqDocStatus);
								pnlReqDocStatus.setBorder(JTBorderFactory.createTitleBorder("Document Status"));
								{
									scrollReqDocStatus = new _JScrollPane();
									pnlReqDocStatus.add(scrollReqDocStatus, BorderLayout.CENTER);
									scrollReqDocStatus.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									{
										modelReqDocsStatus = new modelCARD_ReqDocStatus();
										modelReqDocsStatus.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderReqDocStatus.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowHeaderReqDocStatus.getModel()).addElement(modelReqDocsStatus.getRowCount());
												}
											}
										});

										tblReqDocStatus = new _JTableMain(modelReqDocsStatus);
										scrollReqDocStatus.setViewportView(tblReqDocStatus);
										tblReqDocStatus.setSortable(false);
										tblReqDocStatus.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));

										tblReqDocStatus.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if(!e.getValueIsAdjusting()){

												}
											}
										});
									}
									{
										rowHeaderReqDocStatus = tblReqDocStatus.getRowHeader();
										rowHeaderReqDocStatus.setModel(new DefaultListModel());
										scrollReqDocStatus.setRowHeaderView(rowHeaderReqDocStatus);
										scrollReqDocStatus.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}//END OF BRACKET
							}
						}
					//}
				}
				{
					JXPanel pnlGeneralHistory = new JXPanel();
					tabCenter.addTab("General History", null, pnlGeneralHistory, null);
					pnlGeneralHistory.setLayout(new BorderLayout(5, 5));
					/*{
						JXPanel pnlGeneralHistoryNorth = new JXPanel();
						pnlGeneralHistory.add(pnlGeneralHistoryNorth, BorderLayout.NORTH);
						pnlGeneralHistoryNorth.setPreferredSize(new Dimension(783, 28));
						pnlGeneralHistoryNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					}*/
					{
						tabGeneralHistoryCenter = new JTabbedPane();
						pnlGeneralHistory.add(tabGeneralHistoryCenter, BorderLayout.CENTER);
						tabGeneralHistoryCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						{
							JXPanel pnlAccountStatusHistory = new JXPanel(new BorderLayout(5, 5));
							tabGeneralHistoryCenter.addTab("Account Status", null, pnlAccountStatusHistory, null);
							pnlAccountStatusHistory.setLayout(new BorderLayout());
							{
								scrollAccountStatusHistory = new _JScrollPane();
								pnlAccountStatusHistory.add(scrollAccountStatusHistory, BorderLayout.CENTER);
								scrollAccountStatusHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollAccountStatusHistory.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblAccountStatusHistory.clearSelection();
									}
								});
								{
									modelAccountStatusHistory = new modelCARD_AccountStatus();
									modelAccountStatusHistory.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderAccountStatusHistory.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderAccountStatusHistory.getModel()).addElement(modelAccountStatusHistory.getRowCount());
											}
										}
									});

									tblAccountStatusHistory = new _JTableMain(modelAccountStatusHistory);
									scrollAccountStatusHistory.setViewportView(tblAccountStatusHistory);
									tblAccountStatusHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblAccountStatusHistory.setSortable(false);
									tblAccountStatusHistory.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
									tblAccountStatusHistory.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf));
									tblAccountStatusHistory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(!e.getValueIsAdjusting()){//XXX tblJVDetail

											}
										}
									});

									/*tblAccountStatusHistory.addMouseListener(new MouseAdapter() {
										public void mouseReleased(MouseEvent e) {
											if(e.isPopupTrigger()){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}
										public void mousePressed(MouseEvent e) {
											if(e.isPopupTrigger()){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}

										public JPopupMenu initializeMenu(MouseEvent e) {
											_JTableMain table = (_JTableMain) e.getSource();
											//int[] rows = table.getSelectedRows();
											int row = table.getModelRow(table.getSelectedRow());
											int column = table.convertColumnIndexToModel(8);
											//final String status = (String) modelAccountStatusHistory.getValueAt(row, table.convertColumnIndexToModel(1));
											final String request_no = (String) modelAccountStatusHistory.getValueAt(row, column);

											JPopupMenu menu = new JPopupMenu();

											JMenuItem menuViewRequests = new JMenuItem("View Request");
											menu.add(menuViewRequests);
											menuViewRequests.setFont(menuViewRequests.getFont().deriveFont(10f));
											menuViewRequests.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent arg0) {
													//System.out.printf("Request No: %s (%s)%n", request_no, status);
													Map<String, Object> mapRequest = new HashMap<String, Object>();
													mapRequest.put("request_no", request_no);
													mapRequest.put("prepared_by", UserInfo.FullName2);

													FncReport.generateReport("/Reports/rptClientRequest.jasper", String.format("Client Request Approval (%s)", getValue(txtClientName.getText())), mapRequest);
												}
											});

											if(request_no == null){
												return null;
											}else{
												return menu;
											}
										}
									});*/
								}
								{
									rowheaderAccountStatusHistory = tblAccountStatusHistory.getRowHeader();
									rowheaderAccountStatusHistory.setModel(new DefaultListModel());
									scrollAccountStatusHistory.setRowHeaderView(rowheaderAccountStatusHistory);
									scrollAccountStatusHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						
						{
							JXPanel pnlTOverMoveIn = new JXPanel();
							tabGeneralHistoryCenter.addTab("Turn-Over/Move-in Status", null, pnlTOverMoveIn, null);
							pnlTOverMoveIn.setLayout(new BorderLayout());
							{
								scrollTOverMoveIn = new _JScrollPane();
								pnlTOverMoveIn.add(scrollTOverMoveIn, BorderLayout.CENTER);
								scrollTOverMoveIn.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollTOverMoveIn.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e){
										tblTOverMoveIn.clearSelection();
									}
								});
								{
									modelTOverMoveIn = new modelTOverMoveIn();
									modelTOverMoveIn.addTableModelListener(new TableModelListener() {

										@Override
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderTOverMoveIn.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel) rowHeaderTOverMoveIn.getModel()).addElement(modelTOverMoveIn.getRowCount());
											}
										}
									});

									tblTOverMoveIn = new _JTableMain(modelTOverMoveIn);
									scrollTOverMoveIn.setViewportView(tblTOverMoveIn);
								}
								{
									rowHeaderTOverMoveIn = tblTOverMoveIn.getRowHeader();
									rowHeaderTOverMoveIn.setModel(new DefaultListModel());
									scrollTOverMoveIn.setRowHeaderView(rowHeaderTOverMoveIn);
									scrollTOverMoveIn.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							JXPanel pnlUnitStatus = new JXPanel();
							tabGeneralHistoryCenter.addTab("Unit Status History", null, pnlUnitStatus, null);
							pnlUnitStatus.setLayout(new BorderLayout());
							{
								scrollUnitStatus = new _JScrollPane();
								pnlUnitStatus.add(scrollUnitStatus, BorderLayout.CENTER);
								scrollUnitStatus.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollUnitStatus.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e){
										tblUnitStatus.clearSelection();
									}
								});
								{
									modelUnitStatus = new modelUnitStatus();
									modelUnitStatus.addTableModelListener(new TableModelListener() {

										@Override
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderUnitStatus.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel) rowHeaderUnitStatus.getModel()).addElement(modelUnitStatus.getRowCount());
											}
										}
									});

									tblUnitStatus = new _JTableMain(modelUnitStatus);
									scrollUnitStatus.setViewportView(tblUnitStatus);
								}
								{
									rowHeaderUnitStatus = tblUnitStatus.getRowHeader();
									rowHeaderUnitStatus.setModel(new DefaultListModel());
									scrollUnitStatus.setRowHeaderView(rowHeaderUnitStatus);
									scrollUnitStatus.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
					}
				}
				tabCenter.addChangeListener(new ChangeListener() {

					public void stateChanged(ChangeEvent e) {

						if(lookupClient.getValue() != null){

							displayTabDetails(lookupClient.getValue(), true);
						}
					}
				});
			}
			initTAGS(pnlNorth, this);
			//initTAGS(pnlUnitStatus, this);
	}
	
	private String generateTAG(Object text) {
		return String.format("[ %s ]", text);
	}
	
	private void displayClientDetails(String request_no){
		clientDetails = _ClientRequestOldDetails.displayClientDetails(request_no);
		
		if(clientDetails != null){
			//lookupClient.setText((String) clientDetails[0]);
			txtClientName.setText(generateTAG((String) clientDetails[0])); 
			txtClientName.setCaretPosition(0);
			//txtClientName.setFont(new Font("SansSerif", Font.BOLD, 7));

			txtProjectID.setText((String) clientDetails[1]);
			txtProjectName.setText(generateTAG((String) clientDetails[2]));

			txtPblID.setText((String) clientDetails[3]);
			txtPblDescription.setText(String.format("[ %s ] [ %s ]", (String) clientDetails[4], Integer.toString((Integer) clientDetails[5])));

			txtModelID.setText((String) clientDetails[6]);
			txtModelName.setText(generateTAG(clientDetails[7]));
			
			txtBuyerTypeID.setText((String) clientDetails[8]);
			//txtBuyerTypeID.setToolTipText((String) clientDetails[25]);
			txtBuyerTypeName.setText(generateTAG((String) clientDetails[9]));
			txtBuyerTypeName.setCaretPosition(0);

			txtPmtSchemeID.setText((String) clientDetails[10]);
			txtPmtSchemeName.setText(generateTAG((String) clientDetails[11]));
			txtPmtSchemeName.setCaretPosition(0);
			
			txtSellingAgentID.setText((String) clientDetails[14]);
			txtSellingAgentName.setText((String) clientDetails[15]);
			txtSellingAgentName.setCaretPosition(0);

			txtGSP.setValue((BigDecimal) clientDetails[16]);
			txtDiscount.setValue((BigDecimal) clientDetails[17]);
			txtNSP.setValue((BigDecimal) clientDetails[18]);
			
			txtDP.setValue((BigDecimal) clientDetails[19]);
			txtLoanableAmount.setValue((BigDecimal) clientDetails[24]);
			
			txtBalance.setValue(((BigDecimal) clientDetails[22]));
			txtMiscFeeBalance.setValue((BigDecimal) clientDetails[23]);
			txtMiscFee15Pct.setValue((BigDecimal) clientDetails[20]);
			txtTotalCashOutlay.setValue((BigDecimal) clientDetails[21]);
			
			txtBankTerm.setValue(clientDetails[29]);
			
			txtDPAmount.setValue((BigDecimal) clientDetails[25]);
			//txtDPRate.setValue((BigDecimal) clientDetails[20]);
			txtDPTerm.setValue((Integer) clientDetails[26]);
			txtMAAmount.setValue((BigDecimal) clientDetails[27]);
			//txtMARate.setValue((BigDecimal) clientDetails[23]);
			txtMATerm.setValue((Integer) clientDetails[28]);
			txtFactorRate.setText((String) clientDetails[30]);
			txtIntRate.setValue((BigDecimal) clientDetails[31]);
			
		}
	}
	
	private void displayTabDetails(String request_no, Boolean toPrint){
		int selected_tab = tabCenter.getSelectedIndex();

		if(selected_tab == 0){
			_ClientRequestOldDetails.displaySchedule(modelSchedule, request_no, toPrint);
			scrollSchedule.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule.getRowCount())));
			//tblSchedule.packColumns("Part ID", "Particular", "Schedule Date", "Amount", "Processing Fee", "MRI", "Fire", "MAF", "Interest", "Principal", "VAT", "Balance", "Interest Rate", "Settled");
			tblSchedule.packAll();

			//dateDues.setDate(Calendar.getInstance().getTime());
			
			/*_CARD.displayDues(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, toPrint);
			scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblDues.getRowCount())));
			tblDues.setHorizontalScrollEnabled(true);
			tblDues.packAll();
			tblDues.setHorizontalScrollEnabled(false);*/
			
			//System.out.printf("Display value of Dues Row Count: %", args)
			//DateFormat formatter;
			String exclude_date_string = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
			chkExcludeLastSched.setSelected(false);
			dateExclude.setDate(null);

			if(modelDues.getRowCount() != 0){
				chkExcludeLastSched.setEnabled(true);
				Date exclude_date = (Date) modelDues.getValueAt(modelDues.getRowCount() - 1, 2);
				exclude_date_string = formatter.format(exclude_date);

				//chkExcludeLastSched.setText(String.format("%s%s", "Exclude Last Sched Date:", exclude_date_string));
				//dateExclude.setDateFormatString(exclude_date_string);
				dateExclude.setDate(exclude_date);

				txtAmtToUpdate.setValue(totalDues());
			}else{
				chkExcludeLastSched.setEnabled(false);
				txtAmtToUpdate.setValue(new BigDecimal("0.00"));
			}

			_ClientRequestOldDetails.displayOldLedger(modelLedger, request_no, true);
			scrollLedger.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLedger.getRowCount())));
			tblLedger.setHorizontalScrollEnabled(true);
			tblLedger.packAll();
			tblLedger.setHorizontalScrollEnabled(false);

		}

		validateSchedule();

		if(selected_tab == 1){
			//_CARD.displayPayments(modelPayments, entity_id, proj_id, pbl_id, seq_no, toPrint);
			_ClientRequestOldDetails.displayOldPayments(modelPayments, request_no, true);
			scrollPayments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPayments.getRowCount())));
			tblPayments.packColumns("Trans. Date", "Actual Date", "Particulars", "Type", "**", "Amount", "%", "Bank", "Branch", "Account #", "Check #", "Check Date", "Check Status", "Deposit Date", "OR No.", "OR Date", "AR No.", "Remarks", "OR Pending", "Branch", "Rec ID");
			validatePayments();

			modelCreditOfPayment.clear();
			scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));

			modelCheckHistory.clear();
			scrollCheckHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCheckHistory.getRowCount())));
		}

		if(selected_tab == 2){
			

		}

		if(selected_tab == 3){
			
		}

	}
	
	private String getValue(String text) {
		String value = null;

		Pattern patronValidity = Pattern.compile("\\[([^\\]]+)]");
		Matcher m = patronValidity.matcher(text);

		int counter = 1;
		while (m.find()) {
			value = m.group(1).trim();
			if(counter == 1){
				break;
			}
			counter++;
		}

		return value;
	}
	
	private BigDecimal totalDues(){
		BigDecimal amt_to_update = new BigDecimal("0.00");

		for(int x= 0; x<modelDues.getRowCount(); x++){
			amt_to_update = amt_to_update.add((BigDecimal) modelDues.getValueAt(x, 15));

		}
		return amt_to_update;
	}
	
	private void validatePayments() {
		ArrayList<String> listPaymentType = new ArrayList<String>();
		for(int x=0; x < modelPayments.getRowCount(); x++){
			String pymnt_type = (String) modelPayments.getValueAt(x, 3);

			listPaymentType.add(pymnt_type);
		}

		/*if(!listPaymentType.contains("Check")){
			tblPayments.hideColumns("Bank", "Branch", "Account #", "Check #", "Check Date", "Check Status", "Deposit Date");
		}else{
			tblPayments.showColumns("Bank", "Branch", "Account #", "Check #", "Check Date", "Check Status", "Deposit Date");
		}*/
	}
	
	private void validateSchedule() {
		pgSelect db = new pgSelect();
		db.select("SELECT type_group_id FROM mf_buyer_type WHERE type_id = '"+ txtBuyerTypeID.getText() +"';");

		if(db.isNotNull()){
			String buyer_type_id = (String) db.getResult()[0][0];
			if(buyer_type_id.equals("04") || buyer_type_id.equals("05")){
				tblSchedule.showColumns("Proc. Fee");

				tblDues.showColumns("Penalty");
				tblDues.hideColumns("SOI", "SOP");

				tblLedger.showColumns("Penalty");
				tblLedger.hideColumns("SOI", "SOP");

			}else{
				tblSchedule.hideColumns("Proc. Fee");

				/*tblDues.showColumns("SOI", "SOP");
				tblDues.hideColumns("Penalty");

				tblLedger.showColumns("SOI", "SOP");
				tblLedger.hideColumns("Penalty");*/

				tblDues.showColumns("SOI", "SOP" ,"Penalty");
				//tblDues.hideColumns("SOP");

				tblLedger.showColumns("SOI", "SOP","Penalty");
				//tblLedger.hideColumns("SOP");
			}
			//if(tabCenter.getSelectedIndex() != 9){
			//}
		}

		if(txtVAT.getValued() != null){
			tblSchedule.showColumns("VAT");
		}else{
			tblSchedule.hideColumns("VAT");
		}
	}
	
	public static void initTAGS(JPanel pnlNorth, ClientRequestOldDetails crod) {//XXX initTAGS
		for(Component comp : pnlNorth.getComponents()){
			if(comp instanceof _JLookup){
				return;
			}

			if(comp instanceof _JXTextField){
				/*System.out.println("Dumaan dito sa _JXTextfield");
				System.out.println("");*/
				_JXTextField field = (_JXTextField) comp;

				if(field.getName() != null && field.getName().equals("Factor")){

				}else{
					field.setText("[ ]");
					field.setEditable(false);
					field.setForeground(Color.BLUE);
					field.setBorder(BorderFactory.createEmptyBorder());
					field.setOpaque(false);
					field.addMouseListener(crod);
					field.getDocument().addDocumentListener(new DocumentListener() {

						@Override
						public void removeUpdate(DocumentEvent e) { }

						@Override
						public void insertUpdate(DocumentEvent e) { }

						@Override
						public void changedUpdate(DocumentEvent e) {
							Object obj = e.getDocument().getProperty("source");

							if(obj instanceof _JXTextField){
								_JXTextField field = (_JXTextField) obj;

								FontMetrics fm = field.getFontMetrics(font11);
								try {
									String text = e.getDocument().getText(0, e.getDocument().getLength());

									int adv = fm.stringWidth(text);
									Dimension size = new Dimension(adv+5, 20);

									field.setPreferredSize(size);
									field.setSize(size);
									field.repaint();
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
							}

							if(obj instanceof _JXFormattedTextField){
								_JXFormattedTextField field = (_JXFormattedTextField) obj;

								FontMetrics fm = field.getFontMetrics(font11);
								try {
									String text = e.getDocument().getText(0, e.getDocument().getLength());

									int adv = fm.stringWidth(text);
									Dimension size = new Dimension(adv+5, 20);

									field.setPreferredSize(size);
									field.setSize(size);
									field.repaint();
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
							}
						}
					});
				}
			}

			//Added by Alvin Gonzales (2015-05-20)
			if(comp instanceof _JXFormattedTextField){
				_JXFormattedTextField field = (_JXFormattedTextField) comp;
				field.setEditable(false);
				field.addMouseListener(crod);
				field.setBackground(UIManager.getColor("FormattedTextField.background"));
			}

			if(comp instanceof JXTextField){
				JXTextField field = (JXTextField) comp;
				field.setEditable(false);
				field.addMouseListener(crod);
			}

			if(comp instanceof JXLabel){
				JXLabel label = (JXLabel) comp;
				label.addMouseListener(crod);
				//label.setBorder(lineBorder);
				label.setHorizontalAlignment(SwingConstants.RIGHT);
			}

			if(comp instanceof JPanel){
				JPanel panel = (JPanel) comp;
				panel.addMouseListener(crod);

				initTAGS((JPanel) comp, crod);
			}

			//comp.setFont(new Font("Tahoma", Font.PLAIN, 11));
			comp.setFont(font11);
		}
	}
	
	private Integer getSequence() {
		Integer sequence = null;

		Pattern patronValidity = Pattern.compile("\\[([^\\]]+)]");
		Matcher m = patronValidity.matcher(txtPblDescription.getText());

		while (m.find()) {
			try {
				sequence = Integer.parseInt(m.group(1).trim());
			} catch (NumberFormatException e) { }
			//System.out.printf("Match: %s;%n", m.group(1).trim());
		}

		return sequence;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	

}
