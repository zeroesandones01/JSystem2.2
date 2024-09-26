package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Accounting.Cashiering.CashReceiptBook;
import Buyers.CreditandCollections.PromissryNote_v2;
import Buyers.CreditandCollections.RegularBillingAndNotices;
import Buyers.CreditandCollections.pnlRB_Monitoring_v2;
import Buyers.LoansManagement.LoanReleased;
import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Dialogs.dlg_CARD_PreviewSOA;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
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
import components._JTBorderFactory;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTextAreaEditor;
import components._JTextAreaRenderer2;
import components._JVerticalTextIcon;
import components._JXTextField;
import interfaces._GUI;
import search.SearchEvent;
import search.SearchListener;
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
import tablemodel.model_pcost_CARD;
import tablemodel.model_tcost;

/**
 * @author Alvin Gonzales
 *
 */
public class CARD extends _JInternalFrame implements _GUI, AncestorListener, SearchListener, MouseListener, ActionListener {
// Test Comment 2023-06-29
	
	
	//Changes for Card NOt to be commited
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9098902667584990384L;

	static String title = "Customers Account Record Details (CARD)";
	Dimension frameSize = new Dimension(1100, 600);// 510, 250
	Dimension frameSizeSmall = new Dimension(800, 450);
	//Dimension frameSizeSmall = new Dimension(800, 600);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;
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
	private JXTextField txtHousingLoan;
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

	private JTabbedPane tabClientDetailsEast;
	private JSplitPane splitClientDetailsEast;
	
	private _JXFormattedTextField txtGSPVAT;
	private _JXFormattedTextField txtDiscountVAT;
	private _JXFormattedTextField txtNSP_GOV;
	private _JXFormattedTextField txtVATEast;
	private _JXFormattedTextField txtNSP_NOV;
	private _JXFormattedTextField txtDPVat;
	private _JXFormattedTextField txtOSBalance_VAT;
	private _JXFormattedTextField txtLoanableAmount_VAT;
	private _JXFormattedTextField txtDPAmt_VAT;
	private _JXFormattedTextField txtDPRate_VAT;
	private _JXFormattedTextField txtDP_Term_VAT;
	private _JXFormattedTextField txtMARate_VAT;
	private _JXFormattedTextField txtMAAmt_VAT;
	private _JXFormattedTextField txtMA_Term_VAT;
	private _JXTextField txtFactor_VAT;
	private _JXFormattedTextField txtInt_RateVAT;
	
	private _JDateChooser dteBIRDate_VAT;
	private _JDateChooser dteBIRExpDate_VAT;
	private JXTextField txtHLID_VAT;
	private JXTextField txtCWT_TaxExempt_VAT;
	//
	//private _JDateChooser txtWithNTC;
	//private _JDateChooser txtHouseConstructed;
	//private _JDateChooser txtTurnOver;
	//private _JDateChooser txtMovedIn;
	//private _JDateChooser txtMovedOut;

	private JTabbedPane tabCenter;
	private JTabbedPane tabSchedule;
	private JTabbedPane tabTD;
	private JTabbedPane tabAccomplishment;

	private JScrollPane scrollSchedule;
	//private JScrollPane scrollSchedule2;
	//private JScrollPane scrollSchedule3;
	//private JScrollPane scrollSchedule4;
	//private JScrollPane scrollSchedule5;
	private _JTableMain tblSchedule;
	private _JTableMain tblSchedule2;
	private _JTableMain tblSchedule3;
	private _JTableMain tblSchedule4;
	//private _JTableMain tblSchedule5;
	private modelCARD_Schedule modelSchedule;
	private modelCARD_Schedule modelSchedule2;
	private modelCARD_Schedule modelSchedule3;
	private modelCARD_Schedule modelSchedule4;
	//private modelCARD_Schedule modelSchedule5;
	private JList rowheaderSchedule;
	private JList rowheaderSchedule2;
	private JList rowheaderSchedule3;
	private JList rowheaderSchedule4;
	//private JList rowheaderSchedule5;

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

	private model_pcost_CARD modelPCD;
	private _JScrollPaneMain scrollPCD;
	private _JTableMain tblPCD;
	private JList rowHeaderPCD;

	private _JScrollPaneTotal scrollPCDTotal;
	private _JTableTotal tblPCDTotal;
	private model_pcost_CARD modelPCDTotal;

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

	private _JDateChooser dteBIRDate;
	private JXTextField txtHLID;
	private JXTextField txtCWT_Exempt;
	private JComboBox cmbPreviewLot;

	private JPanel pnlRemarks;
	//Font fontSmallTab = new Font("Tahoma", Font.BOLD, 8);
	Font fontSmallTab = new Font("Tahoma", Font.BOLD, 7);
	Font fontTable = new Font("Tahoma", Font.BOLD, 7);

	hdmfInfo_maintab hdmftab = new hdmfInfo_maintab(this);
	PropertyManagement PM = new PropertyManagement(this);
	panCard_unitinfostatus unitInfoStatus = new panCard_unitinfostatus(this);  

	private JTextField txtBOIExempt;
	private _JDateChooser dteBIRExpDate;

	private JComboBox cmblot; 
	private Integer seq_no;

	private JComboBox cmblot1;
	private JComboBox cmbTOMSLot;
	private JScrollPane scrollTCTTax_Loc;
	private modelDocStatus modelTCTTax_Loc;
	private _JTableMain tblTCTTax_Loc;
	private JList rowheaderTCTTax_Loc;
	private JTextArea txtNotes,txtBat,txtPmd,txtCmd;

	private JButton btnOptions;

	private JTextField txtRDO; 
	private JTextField txtBank; 			
	private JTextField txtBranch; 
	private JTextField txtAccntNo;

	private JTextField txtMTPPClass;

	private JTextField txtMTPPClass_VAT;

	private _CARD_REQUESTS pnlRequest;

	private JLabel lblRPTOffset;
	private JPanel pnlCreditRPTOffset;
	private JLabel lblRPTCredit;
	private JButton btnCreditedTo;
	private JTextArea clientList;
	private JScrollPane scrollCreditTo;
	private String rptCreditRemarks;

	public static String server; 
	
	public CARD() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CARD(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		super(title, true, true, true, true);
		initGUI();

		lookupClient.setValue(entity_id);
		displayClientDetails(entity_id, proj_id, pbl_id, seq_no, true);
		//displayTabs(entity_id, proj_id, pbl_id, seq_no, true);
		displayTabDetails(entity_id, proj_id, pbl_id, seq_no, true);
	}

	public CARD(String title) {
		super(title);
		initGUI();
	}

	public CARD(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		UIManager.put("TabbedPane.tabInsets", new Insets(2, 5, 1, 5));
		UIManager.put("TabbedPane.font", UIManager.getFont("TabbedPane.font").deriveFont(11f));

		this.setTitle(title);
		this.setPrimaryTitle(title);
		if(UserInfo.EmployeeCode.equals("000645")){
			this.setSize(frameSizeSmall);
			this.setPreferredSize(new Dimension(frameSizeSmall));
			System.out.printf("Display Screen Resolution: %s%n", screenSize);
			/*this.setBounds(0, 0, screenSize.width, screenSize.height);
			this.setSize(screenSize.width, screenSize.height);
			pack();*/
		}else{
			this.setSize(frameSize);
			this.setPreferredSize(new Dimension(frameSize));
		}

		this.setLayout(new BorderLayout());

		this.addSearchListener(this);
		this.addAncestorListener(this);
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent arg0) {
				if(timerStatus != null){
					timerStatus.stop();
				}
			}

			public void internalFrameClosed(InternalFrameEvent arg0) {
				//System.out.println("internalFrameClosed");
				executor.shutdown();
				if(timerStatus != null){
					timerStatus.stop();
				}
			}
		});
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(3, 0));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setPreferredSize(new Dimension(788, 160));
				
				if(UserInfo.EmployeeCode.equals("000645") == false){
					pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					pnlNorth.setPreferredSize(new Dimension(788, 250));
					//pnlNorth.setPreferredSize(new Dimension(900, 230));
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
						pnlWest.setPreferredSize(new Dimension(140, 15));
						{
							JXLabel lblClient = new JXLabel("Client");
							pnlWest.add(lblClient, BorderLayout.WEST);
							lblClient.setPreferredSize(new Dimension(60, 15));
						}
						{
							lookupClient = new _JLookup(null, "Clients", 1);
							pnlWest.add(lookupClient, BorderLayout.CENTER);
							lookupClient.setReturnColumn(1);
							lookupClient.setLookupSQL(_CARD.sqlClients());
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
										seq_no = (Integer) data[5];

										System.out.printf("Display Entity ID: %s%n", entity_id);
										System.out.printf("Display Entity ID: %s%n", proj_id);
										System.out.printf("Display PBL ID: %s%n", pbl_id);
										System.out.printf("Display Seq No: %s%n", seq_no);

										displayClientDetails(entity_id, proj_id, pbl_id, seq_no, true);
										dateDues.setDate(FncGlobal.getDateToday());
										displayTabDetails(entity_id, proj_id, pbl_id, seq_no, true);

										hdmfInfo_maintab.displayLoanFilingStatus(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFUnitInspection(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFPayments(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFSchedule(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayHDMFLoanReleasedDetail(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.displayEPEBG2G(entity_id, proj_id, pbl_id, seq_no.toString());
										hdmfInfo_maintab.setLotsToPreview(entity_id, proj_id, pbl_id, seq_no.toString());
										tab_remConversion.DisplayHDMFREM(entity_id, proj_id, pbl_id, seq_no.toString());
										
										// ADDED BY MONIQUE DTD 10-23-2023; REFER TO DCRF#2766
										if(isER1B(proj_id, pbl_id) == true) {
//											System.out.printf("isER1B: %s%n", "ER-1B");
											hdmfInfo_maintab.Note("ER1-B");
										}

										btnOptions.setText("Regular");
										btnOptions.setEnabled(!_CARD.afterECQ(entity_id, proj_id, pbl_id, seq_no) && !entity_id.equals("5935348691"));
										if(proj_id.equals("019")) {
											tabSchedule.setSelectedIndex(3);
										}
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
				GridLayout grid = new GridLayout(11, 1, 1, 1);
				{
					pnlNorthCenter = new JPanel(new GridLayout(10, 1, 1, 1));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
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
					{
						JPanel pnlLoanExists = new JPanel(new BorderLayout(3, 3));
						pnlNorthCenter.add(pnlLoanExists);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlLoanExists.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(140, 20));
							{
								JXLabel lblLoanExists = new JXLabel("w/ Housing Loan");
								pnlWest.add(lblLoanExists, BorderLayout.WEST);
								lblLoanExists.setPreferredSize(new Dimension(100, 20));
							}
							{
								txtHousingLoan = new _JXTextField();
								pnlLoanExists.add(txtHousingLoan, BorderLayout.CENTER);

							}
						}
					}
				}
				{
					splitClientDetailsEast = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
					pnlNorth.add(splitClientDetailsEast, BorderLayout.EAST);
					splitClientDetailsEast.setOneTouchExpandable(true);
					splitClientDetailsEast.setPreferredSize(new Dimension(450, 200));
					{
						JPanel pnlNorthEast = new JPanel(new BorderLayout());
						splitClientDetailsEast.add(pnlNorthEast, JSplitPane.LEFT);
						pnlNorthEast.setPreferredSize(new Dimension(450, 200));
						{
							JPanel pnlNorthEastCenter = new JPanel(new BorderLayout(1, 1));
							pnlNorthEast.add(pnlNorthEastCenter, BorderLayout.CENTER);
							{
								JPanel pnlLabels = new JPanel(grid);
								pnlNorthEastCenter.add(pnlLabels, BorderLayout.WEST);
								pnlLabels.setPreferredSize(new Dimension(150, 185));
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
								{//ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
									JXLabel lblMTPPClass = new JXLabel("MTPP Classification   ");
									pnlLabels.add(lblMTPPClass);
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
								{//ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
									txtMTPPClass = new JTextField();
									pnFields.add(txtMTPPClass);
									txtMTPPClass.setHorizontalAlignment(JTextField.CENTER);
									
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
								JPanel pnlLabels = new JPanel(new GridLayout(10, 1, 1, 1));
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
									JXLabel lblBIRDate = new JXLabel("BOI Expiration");
									pnlLabels.add(lblBIRDate);
								}
								{
									JXLabel lblHLID = new JXLabel("HLID No.");
									pnlLabels.add(lblHLID);
								}
//								{
//									JXLabel lblCWT_TaxExempt = new JXLabel("CWT");
//									pnlLabels.add(lblCWT_TaxExempt);
//								}

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
								JPanel pnFields = new JPanel(new GridLayout(10, 1, 1, 1));
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
								{
									dteBIRDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnFields.add(dteBIRDate);
									dteBIRDate.getCalendarButton().setVisible(false);
								}
								{
									dteBIRExpDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnFields.add(dteBIRExpDate);
									dteBIRExpDate.getCalendarButton().setVisible(false);
								}
								{
									txtHLID = new JXTextField();
									pnFields.add(txtHLID, BorderLayout.CENTER);
									txtHLID.setHorizontalAlignment(JTextField.CENTER);
								}
//								{
//									txtCWT_Exempt = new JXTextField();
//									pnFields.add(txtCWT_Exempt);
//									txtCWT_Exempt.setHorizontalAlignment(JTextField.CENTER);
//								}
								/*
									{
										txtBOIExempt = new JTextField("---"); 
										pnFields.add(txtBOIExempt, BorderLayout.CENTER);
										txtBOIExempt.setHorizontalAlignment(JTextField.CENTER);
										txtBOIExempt.setEditable(false);
										txtBOIExempt.setFont(new Font(txtBOIExempt.getFont().getName(), Font.BOLD, txtBOIExempt.getFont().getSize()));
										txtBOIExempt.addMouseListener(new MouseListener() {

											public void mouseReleased(MouseEvent e) {
												txtBOIExempt.setText("---");
											}

											public void mousePressed(MouseEvent e) {
												txtBOIExempt.setText(FncGlobal.GetString("select (case when sp_is_boi_exempt('"+lookupClient.getValue()+"', \n" +
														"'"+txtProjectID.getText()+"', '"+txtPblID.getText()+"', "+FncGlobal.GetString("select seq_no::varchar from rf_sold_unit \n" +
														"where entity_id = '"+lookupClient.getValue()+"' and projcode = '"+txtProjectID.getText()+"' and pbl_id = '"+txtPblID.getText()+"'")+") = true then 'BOI Exempt' else 'Not BOI Exempt' end)"));
											}

											public void mouseExited(MouseEvent e) {

											}

											public void mouseEntered(MouseEvent e) {

											}

											public void mouseClicked(MouseEvent e) {
												txtBOIExempt.setText("---");
											}

										});
									}
								 */
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
						JPanel pnlClientDetailsVAT = new JPanel(new GridLayout(1, 2, 3, 3));
						splitClientDetailsEast.add(pnlClientDetailsVAT,JSplitPane.RIGHT);
						{
							JPanel pnlCDVNorthEastCenter = new JPanel(new BorderLayout(1, 1));
							pnlClientDetailsVAT.add(pnlCDVNorthEastCenter, BorderLayout.CENTER);
							{
								JPanel pnlLabels_VAT = new JPanel(new GridLayout(9, 1, 3, 3));
								pnlCDVNorthEastCenter.add(pnlLabels_VAT, BorderLayout.WEST);
								//pnlLabels_VAT.setPreferredSize(new Dimension(120, 200));
								
								{
									JXLabel lblGSP = new JXLabel("GSP");
									pnlLabels_VAT.add(lblGSP);
								}
								{
									JXLabel lblDiscount = new JXLabel("Discount");
									pnlLabels_VAT.add(lblDiscount);
								}
								{
									JXLabel lblNSP = new JXLabel(String.format("<html>NSP (Gross of VAT)<sup>a</sup></html>"));
									pnlLabels_VAT.add(lblNSP);
								}
								{
									JXLabel lblVAT = new JXLabel("<html>VAT<sup>(a/1.12)*.12</sup></html>");
									pnlLabels_VAT.add(lblVAT);
								}
								{
									JXLabel lblNSP = new JXLabel("<html>NSP(Net of VAT)</html>");
									pnlLabels_VAT.add(lblNSP);
								}
								{
									JXLabel lblDP = new JXLabel("<html>DP Equity<sup>b</sup></html>");
									pnlLabels_VAT.add(lblDP);
								}
								
								{
									JXLabel lblBalance = new JXLabel("O/S Balance");
									pnlLabels_VAT.add(lblBalance);
								}
								
								{
									JXLabel lblLoanableAmount = new JXLabel("<html>Loanable Amt<sup>(a-b)</sup></html>");
									pnlLabels_VAT.add(lblLoanableAmount);
								}
								{//ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
									JXLabel lblMTPPClass = new JXLabel("MTPP Classification   ");
									pnlLabels_VAT.add(lblMTPPClass);
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
								JPanel pnFields = new JPanel(new GridLayout(9, 1, 3, 3));
								pnlCDVNorthEastCenter.add(pnFields, BorderLayout.CENTER);
								/*{
										pnFields.add(Box.createHorizontalBox());
									}*/
								{
									txtGSPVAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtGSPVAT);
									txtGSPVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtDiscountVAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtDiscountVAT);
									txtDiscountVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtNSP_GOV = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtNSP_GOV);
									txtNSP_GOV.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtVATEast = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtVATEast);
									txtVATEast.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtNSP_NOV = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtNSP_NOV);
									txtNSP_NOV.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtDPVat = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtDPVat);
									txtDPVat.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtOSBalance_VAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtOSBalance_VAT);
									txtOSBalance_VAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									txtLoanableAmount_VAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnFields.add(txtLoanableAmount_VAT);
									txtLoanableAmount_VAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{//ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
									txtMTPPClass_VAT = new JTextField();
									pnFields.add(txtMTPPClass_VAT);
									txtMTPPClass_VAT.setHorizontalAlignment(JTextField.CENTER);
									
								}
								
							}
						}
						{
							JPanel pnlCDNorthCenterEast = new JPanel(new BorderLayout(3, 3));
							pnlClientDetailsVAT.add(pnlCDNorthCenterEast, BorderLayout.EAST);
							{
								JPanel pnlCDNCELabel = new JPanel(new GridLayout(8, 1, 3, 3));
								pnlCDNorthCenterEast.add(pnlCDNCELabel, BorderLayout.WEST);
								{
									JLabel lblDPAmt_VAT = new JLabel("DP Amt");
									pnlCDNCELabel.add(lblDPAmt_VAT);
								}
								{
									JLabel lblDPRate_Term_VAT = new JLabel("DP Rate/Term");
									pnlCDNCELabel.add(lblDPRate_Term_VAT);
								}
								{
									JLabel lblMAAmt_VAT = new JLabel("MA Amt");
									pnlCDNCELabel.add(lblMAAmt_VAT);
								}
								{
									JLabel lblMA_Rate_Term_VAT = new JLabel("MA Rate/Term");
									pnlCDNCELabel.add(lblMA_Rate_Term_VAT);
								}
								{
									JLabel lblIntRate_Factor = new JLabel("Int Rate/ Factor");
									pnlCDNCELabel.add(lblIntRate_Factor);
								}
								{
									JLabel lblBOIRuling_VAT = new JLabel("BOI Ruling Date");
									pnlCDNCELabel.add(lblBOIRuling_VAT);
								}
								{
									JLabel lblBOIExp_VAT = new JLabel("BOI Expiration");
									pnlCDNCELabel.add(lblBOIExp_VAT);
								}
								{
									JLabel lblHLID_No_Vat = new JLabel("HLID No.");
									pnlCDNCELabel.add(lblHLID_No_Vat);
								}
//								{
//									JLabel lblCWT_TaxExempt = new JLabel("CWT");
//									pnlCDNCELabel.add(lblCWT_TaxExempt);
//								}
							}
							{
								JPanel pnlCDNCE_Comp = new JPanel(new GridLayout(9, 1, 3, 3));
								pnlCDNorthCenterEast.add(pnlCDNCE_Comp);
								{
									txtDPAmt_VAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlCDNCE_Comp.add(txtDPAmt_VAT);
									txtDPAmt_VAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									JPanel pnlDPRateTerm = new JPanel(new GridLayout(1, 2, 1, 1));
									pnlCDNCE_Comp.add(pnlDPRateTerm);
									{
										txtDPRate_VAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlDPRateTerm.add(txtDPRate_VAT);
										txtDPRate_VAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
									{
										txtDP_Term_VAT = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlDPRateTerm.add(txtDP_Term_VAT);
										txtDP_Term_VAT.setFormatterFactory(_JXFormattedTextField.INTEGER);
									}
								}
								{
									txtMAAmt_VAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlCDNCE_Comp.add(txtMAAmt_VAT);
									txtMAAmt_VAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									JPanel pnlMARateTerm = new JPanel(new GridLayout(1, 2, 1, 1));
									pnlCDNCE_Comp.add(pnlMARateTerm);
									{
										txtMARate_VAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlMARateTerm.add(txtMARate_VAT);
										txtMARate_VAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
									{
										txtMA_Term_VAT = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlMARateTerm.add(txtMA_Term_VAT);
										txtMA_Term_VAT.setFormatterFactory(_JXFormattedTextField.INTEGER);
									}
								}
								{
									JPanel pnlIntRate_Factor_VAT = new JPanel(new GridLayout(1, 2, 3, 3));
									pnlCDNCE_Comp.add(pnlIntRate_Factor_VAT);
									{
										txtInt_RateVAT = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlIntRate_Factor_VAT.add(txtInt_RateVAT);
										txtInt_RateVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
									{
										txtFactor_VAT = new _JXTextField();
										pnlIntRate_Factor_VAT.add(txtFactor_VAT);
										txtFactor_VAT.setFont(UIManager.getFont("FormattedTextField.background"));
										txtFactor_VAT.setBackground(UIManager.getColor("FormattedTextField.background"));
										txtFactor_VAT.setHorizontalAlignment(JXTextField.RIGHT);
										txtFactor_VAT.setName("Factor");
									}
								}
								{
									dteBIRDate_VAT = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlCDNCE_Comp.add(dteBIRDate_VAT);
									dteBIRDate_VAT.getCalendarButton().setVisible(false);
								}
								{
									dteBIRExpDate_VAT = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlCDNCE_Comp.add(dteBIRExpDate_VAT);
									dteBIRExpDate_VAT.getCalendarButton().setVisible(false);
								}
								{
									txtHLID_VAT = new JXTextField();
									pnlCDNCE_Comp.add(txtHLID_VAT, BorderLayout.CENTER);
									txtHLID_VAT.setHorizontalAlignment(JTextField.CENTER);
								}
//								{
//									txtCWT_TaxExempt_VAT = new JXTextField();
//									pnlCDNCE_Comp.add(txtCWT_TaxExempt_VAT, BorderLayout.CENTER);
//									txtCWT_TaxExempt_VAT.setHorizontalAlignment(JTextField.CENTER);
//								}
							}
							
						}
					}

				}





				{
					JPanel pnlSellingAgent = new JPanel(new BorderLayout(1, 1));
					pnlNorth.add(pnlSellingAgent, BorderLayout.SOUTH);
					pnlSellingAgent.setPreferredSize(new Dimension(140, 20));
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
						JPanel pnlCenterAgent = new JPanel(new BorderLayout(1, 1));
						pnlSellingAgent.add(pnlCenterAgent, BorderLayout.CENTER);
						{
							txtSellingAgentName = new _JXTextField();
							pnlCenterAgent.add(txtSellingAgentName, BorderLayout.WEST);
							txtSellingAgentName.setToolTipText("Agent / Division Group / BDO");
							//txtSellingAgentName.setBounds(122, 73, 79, 20);
						}
						
					}
					{
						JPanel pnlEastAgent = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlSellingAgent.add(pnlEastAgent, BorderLayout.EAST);
						pnlEastAgent.setPreferredSize(new Dimension(205, 0));
						//pnlEastAgent.setBorder(lineBorder);
						{
							JLabel lblCWT_Exempt = new JLabel("CWT");
							pnlEastAgent.add(lblCWT_Exempt, BorderLayout.WEST);
							lblCWT_Exempt.setHorizontalAlignment(JTextField.LEFT);
						}
						{
							txtCWT_Exempt = new JXTextField();
							pnlEastAgent.add(txtCWT_Exempt, BorderLayout.CENTER);
						}
					}
					
				}
			}
			{
				tabCenter = new JTabbedPane();
				//tabCenter.setPreferredSize(new Dimension(150, 0));
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				if(UserInfo.EmployeeCode.equals("000645")){
					tabCenter.setFont(fontSmallTab.deriveFont(1));
				}

				/**Payment Schedule
				 * 
				 */
				{					


					JPanel pnlSchedLedger = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Schedule/Ledger", null, pnlSchedLedger, null);
					{
						JSplitPane splitSchedule = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
						pnlSchedLedger.add(splitSchedule);
						splitSchedule.setOneTouchExpandable(true);
						splitSchedule.setResizeWeight(.5d);
						if(UserInfo.EmployeeCode.equals("000645")){
							splitSchedule.setResizeWeight(.6d);
						}
						{
							tabSchedule = new JTabbedPane();
							splitSchedule.add(tabSchedule, JSplitPane.LEFT);
							{
								scrollSchedule = new JScrollPane();
								tabSchedule.addTab("   Combined Schedule   ", null, scrollSchedule, null);
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
								}
								{
									rowheaderSchedule = tblSchedule.getRowHeader();
									rowheaderSchedule.setModel(new DefaultListModel());
									scrollSchedule.setRowHeaderView(rowheaderSchedule);
									scrollSchedule.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}

							}

							{
								JScrollPane scrollSchedule4 = new JScrollPane();
								tabSchedule.addTab("Paid Schedule Prior to ECQ", null, scrollSchedule4, null);
								scrollSchedule4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollSchedule4.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblSchedule4.clearSelection();
									}
								});
								{
									modelSchedule4 = new modelCARD_Schedule();
									modelSchedule4.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderSchedule4.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderSchedule4.getModel()).addElement(modelSchedule4.getRowCount());
											}
										}
									});

									tblSchedule4 = new _JTableMain(modelSchedule4);
									scrollSchedule4.setViewportView(tblSchedule4);
									tblSchedule4.setSortable(false);
									tblSchedule4.hideColumns("Part ID", "UPICO");
									tblSchedule4.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
									tblSchedule4.setHorizontalScrollEnabled(true);


									tblSchedule4.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(!e.getValueIsAdjusting()){

											}
										}
									});

								}
								{
									rowheaderSchedule4 = tblSchedule4.getRowHeader();
									rowheaderSchedule4.setModel(new DefaultListModel());
									scrollSchedule4.setRowHeaderView(rowheaderSchedule4);
									scrollSchedule4.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
							{
								JScrollPane scrollSchedule2 = new JScrollPane();
								tabSchedule.addTab("   ECQ Moratorium Schedule  ", null, scrollSchedule2, null);
								scrollSchedule2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollSchedule2.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblSchedule2.clearSelection();
									}
								});
								{
									modelSchedule2 = new modelCARD_Schedule();
									modelSchedule2.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderSchedule2.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderSchedule2.getModel()).addElement(modelSchedule2.getRowCount());
											}
										}
									});

									tblSchedule2 = new _JTableMain(modelSchedule2);
									scrollSchedule2.setViewportView(tblSchedule2);
									tblSchedule2.setSortable(false);
									tblSchedule2.hideColumns("Part ID", "UPICO");
									tblSchedule2.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
									tblSchedule2.setHorizontalScrollEnabled(true);


									tblSchedule2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(!e.getValueIsAdjusting()){

											}
										}
									});

								}
								{
									rowheaderSchedule2 = tblSchedule2.getRowHeader();
									rowheaderSchedule2.setModel(new DefaultListModel());
									scrollSchedule2.setRowHeaderView(rowheaderSchedule2);
									scrollSchedule2.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
							{
								JScrollPane scrollSchedule3 = new JScrollPane();
								tabSchedule.addTab("     **Original Schedule**     ", null, scrollSchedule3, null);
								scrollSchedule3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollSchedule3.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblSchedule3.clearSelection();
									}
								});
								{
									modelSchedule3 = new modelCARD_Schedule();
									modelSchedule3.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderSchedule3.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderSchedule3.getModel()).addElement(modelSchedule3.getRowCount());
											}
										}
									});

									tblSchedule3 = new _JTableMain(modelSchedule3);
									scrollSchedule3.setViewportView(tblSchedule3);
									tblSchedule3.setSortable(false);
									tblSchedule3.hideColumns("Part ID", "UPICO");
									tblSchedule3.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
									tblSchedule3.setHorizontalScrollEnabled(true);


									tblSchedule3.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											if(!e.getValueIsAdjusting()){

											}
										}
									});

								}
								{
									rowheaderSchedule3 = tblSchedule3.getRowHeader();
									rowheaderSchedule3.setModel(new DefaultListModel());
									scrollSchedule3.setRowHeaderView(rowheaderSchedule3);
									scrollSchedule3.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}


						{
							tabLedger = new JTabbedPane();
							splitSchedule.add(tabLedger, JSplitPane.RIGHT);
							{
								JPanel pnlCenterTable = new JPanel(new BorderLayout());
								_JVerticalTextIcon duesIcon = new _JVerticalTextIcon(pnlCenterTable, "Dues", _JVerticalTextIcon.ROTATE_LEFT);
								tabLedger.addTab(null, duesIcon, pnlCenterTable, null);
								tabLedger.setTabPlacement(JTabbedPane.LEFT);
								tabLedger.setPreferredSize(new Dimension(20, 100));
								{
									JPanel pnlDuesNorth = new JPanel(new GridLayout(1, 4, 5, 5));
									pnlCenterTable.add(pnlDuesNorth, BorderLayout.NORTH);
									pnlDuesNorth.setPreferredSize(new Dimension(776, 32));
									pnlDuesNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
									{
										{
											JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
											pnlDuesNorth.add(pnlWest, BorderLayout.WEST);
											pnlWest.setPreferredSize(new Dimension(300, 30));
											{
												{
													JXLabel lblDues = new JXLabel("Dues if CLIENT will pay on", JLabel.TRAILING);
													pnlWest.add(lblDues, BorderLayout.WEST);
													lblDues.setFont(new Font(lblDues.getFont().getName(), lblDues.getFont().getStyle(), 9));
												}
												{
													dateDues = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
													pnlWest.add(dateDues, BorderLayout.CENTER);
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

															switch (btnOptions.getText()) {
															case "Mixed":
																_CARD.displayDuesMixed(modelDues, entity_id, proj_id, pbl_id, seq_no, currentDate, modelDuesTotal, true);
																break;

															case "Regular":
																_CARD.displayDuesRegular(modelDues, entity_id, proj_id, pbl_id, seq_no, currentDate, modelDuesTotal, true, dateExclude.getDate(), chkExcludeLastSched.isSelected());
																break;

															case "Moratorium":
																_CARD.displayDuesMoratorium(modelDues, entity_id, proj_id, pbl_id, seq_no, currentDate, modelDuesTotal, true);                                                                                                                                                                
																break;
															default:
																break;
															}

															scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelDues.getRowCount())));
															tblDues.packAll(10);

															String exclude_date_string = null;
															SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
															chkExcludeLastSched.setSelected(false);
															dateExclude.setDate(null);

															if (modelDues.getRowCount() != 0) {
																chkExcludeLastSched.setEnabled(true);
																Date exclude_date = (Date) modelDues.getValueAt(modelDues.getRowCount() - 1, 2);
																exclude_date_string = formatter.format(exclude_date);
																dateExclude.setDate(exclude_date);
																txtAmtToUpdate.setValue(totalDues());
															} else {
																chkExcludeLastSched.setEnabled(false);
																txtAmtToUpdate.setValue(new BigDecimal("0.00"));
															}
														}
													});
												}	
											}
										}
										{
											JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 3, 3));
											pnlDuesNorth.add(pnlCenter);
											{
												JPanel pnlCenterWest = new JPanel(new BorderLayout(3, 3));
												pnlCenter.add(pnlCenterWest);
												{
													chkExcludeLastSched = new JCheckBox("Exclude Last Sched Date:");
													pnlCenterWest.add(chkExcludeLastSched, BorderLayout.WEST);
													chkExcludeLastSched.setFont(new Font(chkExcludeLastSched.getFont().getName(), chkExcludeLastSched.getFont().getStyle(), 9));
													chkExcludeLastSched.addItemListener(new ItemListener() {

														public void itemStateChanged(ItemEvent e) {
															Boolean selected = e.getStateChange() == ItemEvent.SELECTED;

															String entity_id = lookupClient.getValue();
															String proj_id = txtProjectID.getText();
															String pbl_id = txtPblID.getText();
															Integer seq_no = getSequence();

															txtAmtToUpdate.setValue(new BigDecimal("0.00"));
															chkExcludeLastSched.setText("Exclude Last Sched Date");

															if(lookupClient.getValue() != null) {
																if(modelDues.getRowCount() != 0){
																	if (selected) {																	
																		switch (btnOptions.getText()) {
																		case "Mixed":
																			_CARD.displayDuesMixed(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);
																			break;

																		case "Regular":
																			_CARD.displayDuesRegular(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true, dateExclude.getDate(), chkExcludeLastSched.isSelected());
																			break;

																		case "Moratorium":
																			_CARD.displayDuesMoratorium(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);                                                                                                                                                                
																			break;

																		default:
																			break;
																		}

																		txtAmtToUpdate.setValue(totalDues());
																	} else {
																		txtAmtToUpdate.setValue(totalDues());
																	}
																} else {
																	switch (btnOptions.getText()) {
																	case "Mixed":
																		_CARD.displayDuesMixed(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);
																		break;

																	case "Regular":
																		_CARD.displayDuesRegular(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true, dateExclude.getDate(), chkExcludeLastSched.isSelected());
																		break;

																	case "Moratorium":
																		_CARD.displayDuesMoratorium(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);                                                                                                                                                                
																		break;

																	default:
																		break;
																	}

																	txtAmtToUpdate.setValue(totalDues());
																}

																scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblDues.getRowCount())));
																tblDues.packAll(10);
															} else {
																JOptionPane.showMessageDialog(CARD.this, "Please select client first", "", JOptionPane.INFORMATION_MESSAGE);
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
										}
										{
											JPanel pnlCenterEast = new JPanel(new BorderLayout(3, 3));
											pnlDuesNorth.add(pnlCenterEast);
											{
												{
													lblAmtToUpdate = new JXLabel("Amt to Update");
													pnlCenterEast.add(lblAmtToUpdate, BorderLayout.WEST);
													lblAmtToUpdate.setFont(new Font(lblAmtToUpdate.getFont().getName(), lblAmtToUpdate.getFont().getStyle(), 9));
													lblAmtToUpdate.setPreferredSize(new Dimension(125, 0));
													lblAmtToUpdate.setHorizontalAlignment(JLabel.CENTER);
												}
												{
													txtAmtToUpdate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
													pnlCenterEast.add(txtAmtToUpdate, BorderLayout.CENTER);
													txtAmtToUpdate.setPreferredSize(new Dimension(100, 0));
													txtAmtToUpdate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
												}
											}
										}
										{
											JPanel panOptions = new JPanel(new BorderLayout(3, 3)); 
											pnlDuesNorth.add(panOptions); 
											{
												{
													JLabel lblOptions = new JXLabel("Due Types");
													panOptions.add(lblOptions, BorderLayout.WEST);
													lblOptions.setFont(new Font(lblAmtToUpdate.getFont().getName(), lblAmtToUpdate.getFont().getStyle(), 9));
													lblOptions.setPreferredSize(new Dimension(125, 0));
													lblOptions.setHorizontalAlignment(JLabel.CENTER);
												}
												{
													btnOptions = new JButton("Regular");
													panOptions.add(btnOptions);
													btnOptions.setSelected(true);
													btnOptions.setFont(new Font(btnOptions.getFont().getName(), btnOptions.getFont().getStyle(), 9));
													btnOptions.setFocusable(false);
													btnOptions.setText("Regular");
													btnOptions.addActionListener(new ActionListener() {
														public void actionPerformed(ActionEvent arg0) {
															String entity_id = lookupClient.getValue();
															String proj_id = txtProjectID.getText();
															String pbl_id = txtPblID.getText();
															Integer seq_no = getSequence();

															switch (btnOptions.getText()) {
															case "Mixed":
																btnOptions.setText("Regular");
																_CARD.displayDuesRegular(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true, dateExclude.getDate(), chkExcludeLastSched.isSelected());
																break;

															case "Regular":
																btnOptions.setText("Moratorium");
																_CARD.displayDuesMoratorium(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);
																break;

															case "Moratorium":
																btnOptions.setText("Regular");
																_CARD.displayDuesRegular(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true, dateExclude.getDate(), chkExcludeLastSched.isSelected());
																break;

															default:
																break;
															}

															txtAmtToUpdate.setValue(totalDues());
														}
													});
												}	                                                                                                              
											}
										}
									}
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
													@SuppressWarnings("static-access")
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
															JOptionPane.showMessageDialog(CARD.this, "Please select payment(s) to remove.", "Remove Payment(s)", JOptionPane.INFORMATION_MESSAGE);
															return;
														}

														System.out.printf("Selected Rows: %s (%s)%n", selectedRows[selectedRows.length - 1], table.getRowCount());

														if((table.getRowCount() - 1) > selectedRows[selectedRows.length - 1]){
															JOptionPane.showMessageDialog(CARD.this, "In-Between schedule cannot be remove.", "Remove Payment(s)", JOptionPane.INFORMATION_MESSAGE);
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

												JPopupMenu menu = new JPopupMenu();
												menu.add(menuViewRequests);
												menu.add(menuRemovePayments);
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
								_JVerticalTextIcon iconLedger = new _JVerticalTextIcon(pnlLedgerMain, "Ldgr", _JVerticalTextIcon.ROTATE_LEFT);
								tabLedger.addTab(null, iconLedger, pnlLedgerMain, null);
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
													JOptionPane.showMessageDialog(CARD.this, "Please select client first", "", JOptionPane.INFORMATION_MESSAGE);
												}

											}
										});
									}
								}
								{
									scrollLedger = new JScrollPane();
									pnlLedgerMain.add(scrollLedger, BorderLayout.CENTER);
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
										/*tblLedger2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if(!e.getValueIsAdjusting()){
													try {
														Integer column = tblLedger2.convertColumnIndexToModel(16);
														Integer row = tblLedger2.getSelectedRow();

														Integer pay_rec_id = (Integer) modelPayments.getValueAt(row, 16);

														_CARD.displayCheckHistory(modelCheckHistory, rowheaderCheckHistory, pay_rec_id.toString());
														scrollCheckHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCheckHistory.getRowCount())));
														tblCheckHistory.packColumns("Date", "Status", "Rec", "Deposit", "Reason(for Bounce Checks)");
													} catch (ArrayIndexOutOfBoundsException e1) { }
												}
											}
										});*/
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
				}	
				/**Payment Schedule - End 
				 * 
				 */


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
										if(!e.getValueIsAdjusting()){
											try {
												Integer row = tblPayments.convertRowIndexToModel(tblPayments.getSelectedRow());
												//String remarks = (String) modelPayments.getValueAt(row, 5);
												//String remarks = (String) modelPayments.getValueAt(row, 17);
												//Modified By Mann2x; September 22, 2016;
												String remarks = (String) modelPayments.getValueAt(row, 7);
												Integer pay_rec_id = (Integer) modelPayments.getValueAt(row, 24);
												//Integer pay_rec_id = (Integer) modelPayments.getValueAt(row, 20);

												String request_no = (String) modelPayments.getValueAt(row, 28);

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
											@SuppressWarnings("static-access")
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
											if(!e.getValueIsAdjusting()){ 

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
											if(!e.getValueIsAdjusting()){

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
										if(!e.getValueIsAdjusting()){ 

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
					JXPanel pnlNotices = new JXPanel();
					tabCenter.addTab("Notices", null, pnlNotices, null);
					pnlNotices.setLayout(new BorderLayout(5, 5));
					{
						scrollNotices = new _JScrollPane();
						pnlNotices.add(scrollNotices, BorderLayout.CENTER);
						scrollNotices.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

						{
							modelNotices = new modelCARD_Notices();
							modelNotices.addTableModelListener(new TableModelListener() {

								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowHeaderNotices.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderNotices.getModel()).addElement(modelNotices.getRowCount());
									}
								}
							});

							tblNotices = new _JTableMain(modelNotices);
							scrollNotices.setViewportView(tblNotices);
							tblNotices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblNotices.setSortable(false);
							tblNotices.hideColumns("Notice ID");
							tblNotices.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf));
							tblNotices.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
							tblNotices.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf));

							tblNotices.addMouseListener(new MouseAdapter() {
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
									int selected_row = tblNotices.convertRowIndexToModel(tblNotices.getSelectedRow());

									final String notice_id = (String) modelNotices.getValueAt(selected_row, 0);
									final String batch_no = (String) modelNotices.getValueAt(selected_row, 3);

									JPopupMenu menu = new JPopupMenu();

									JMenuItem menuNotice = new JMenuItem("View Notice");
									menu.add(menuNotice);
									menuNotice.setFont(menuNotice.getFont().deriveFont(10f));
									menuNotice.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {

											String entity_id = lookupClient.getValue();
											String proj_id = txtProjectID.getText();
											String pbl_id = txtPblID.getText();
											Integer seq_no = getSequence();

											pnlRB_Monitoring_v2 rbm = new pnlRB_Monitoring_v2();
											rbm.NoticePreview(notice_id, entity_id, proj_id, pbl_id, seq_no, batch_no, true);
											//pnlRB_Monitoring_v2.NoticePreview(notice_id, entity_id, proj_id, pbl_id, seq_no, batch_no, true);
										}
									});
									return menu;
								}
							});
						}
						{
							rowHeaderNotices = tblNotices.getRowHeader();
							rowHeaderNotices.setModel(new DefaultListModel());
							scrollNotices.setRowHeaderView(rowHeaderNotices);
							scrollNotices.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
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
							tabGeneralHistoryCenter.addTab("  Account Status  ", null, pnlAccountStatusHistory, null);
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
											if(!e.getValueIsAdjusting()){ 

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
						{//02-12-16 John Lester Fatallo
							JXPanel pnlClientReqHistory = new JXPanel();
							tabGeneralHistoryCenter.addTab("Client Request History", null, pnlClientReqHistory, null);
							pnlClientReqHistory.setLayout(new BorderLayout());
							{
								//JSplitPane splitClientRequest = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
								JSplitPane splitClientRequest = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
								pnlClientReqHistory.add(splitClientRequest, BorderLayout.CENTER);
								splitClientRequest.setOneTouchExpandable(true);
								splitClientRequest.setResizeWeight(.5d);
								{
									scrollClientRequestHistory = new _JScrollPane();
									splitClientRequest.add(scrollClientRequestHistory, JSplitPane.LEFT);
									scrollClientRequestHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									scrollClientRequestHistory.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e){
											tblClientRequestHistory.clearSelection();
										}
									});
									{
										modelClientReqHistory = new modelCARD_ClientRequestHistory();
										modelClientReqHistory.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderClientRequestHistory.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel) rowHeaderClientRequestHistory.getModel()).addElement(modelClientReqHistory.getRowCount());
												}
											}
										});

										tblClientRequestHistory = new _JTableMain(modelClientReqHistory);
										scrollClientRequestHistory.setViewportView(tblClientRequestHistory);
										tblClientRequestHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										tblClientRequestHistory.setSortable(false);
										tblClientRequestHistory.hideColumns("ID");
										tblClientRequestHistory.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
										/*tblClientRequestHistory.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
										tblClientRequestHistory.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));*/

										tblClientRequestHistory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

											@Override
											public void valueChanged(ListSelectionEvent e) {
												if(tblClientRequestHistory.getSelectedRows().length == 1){

													Integer selected_index = tblClientRequestHistory.convertRowIndexToModel(tblClientRequestHistory.getSelectedRow());

													String byrstatus_id = (String) modelClientReqHistory.getValueAt(selected_index, 0);

													String request_no = (String) modelClientReqHistory.getValueAt(selected_index, 1);
													String request_desc = (String) modelClientReqHistory.getValueAt(selected_index, 2);

													System.out.printf("Display Request No: %s%n", request_no);
													System.out.printf("Display value of byrstatus_id: %s%n", byrstatus_id);
													//pnlRequestDetails.remove(scrollPWTotal);

													if(request_desc.equals("Refund of Payment")){
														pnlRequestDetails.setBorder(JTBorderFactory.createTitleBorder("Refund of Payment"));
														modelRefundOfPayment = new modelCARD_RefundOfPayment();

														tblRequestDetails = new _JTableMain(modelRefundOfPayment);
														scrollRequestDetails.setViewportView(tblRequestDetails);

														{
															rowHeaderRequestDetails = tblRequestDetails.getRowHeader();
															rowHeaderRequestDetails.setModel(new DefaultListModel());
															scrollRequestDetails.setRowHeaderView(rowHeaderRequestDetails);
															scrollRequestDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
														}

														_CARD.displayRefundHistory(modelRefundOfPayment, request_no, true, rowHeaderRequestDetails);
														scrollRequestDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRequestDetails.getRowCount())));
														scrollPWTotal.setViewportView(null);
														scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblRequestDetails.getRowCount())));
														tblRequestDetails.packAll();

													}

													if(request_desc.equals("Waive Penalty")){

														pnlRequestDetails.setBorder(JTBorderFactory.createTitleBorder("Waive Penalty"));
														//modelPmtsWaived = new modelCARD_PmtsWaived();

														/*if(tblRequestDetails.getModel() == modelPmtsWaived){
															System.out.println("Dumaan dito sa modelPmtsWaived");
														}else{
															System.out.println("Dumaan dito sa hindi modelPmtsWaived");
															tblRequestDetails = new _JTableMain(modelPmtsWaived);
															scrollRequestDetails.setViewportView(tblRequestDetails);
															scrollPWTotal.setViewportView(tblPWTotal);
														}*/

														if(scrollPWTotal.getViewport().getView() == null){

															modelPmtsWaived = new modelCARD_PmtsWaived();
															tblRequestDetails = new _JTableMain(modelPmtsWaived);
															scrollRequestDetails.setViewportView(tblRequestDetails);
															{
																rowHeaderRequestDetails = tblRequestDetails.getRowHeader();
																rowHeaderRequestDetails.setModel(new DefaultListModel());
																scrollRequestDetails.setRowHeaderView(rowHeaderRequestDetails);
																scrollRequestDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
															}

															modelPmtsWaivedTotal = new modelCARD_PmtsWaived();
															modelPmtsWaivedTotal.addRow(new Object[] {0.00, "Total", null});

															tblPWTotal = new _JTableTotal(modelPmtsWaivedTotal, tblRequestDetails);
															scrollPWTotal.setViewportView(tblPWTotal);
															tblPWTotal.setTotalLabel(1);
														}

														_CARD.displayPmtsWaived(modelPmtsWaived, request_no, rowHeaderRequestDetails, modelPmtsWaivedTotal);
														scrollRequestDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRequestDetails.getRowCount())));
														scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblRequestDetails.getRowCount())));
														tblRequestDetails.packAll();
														//tblPWTotal.packAll();
													}

													if(byrstatus_id.equals("118")){
														pnlRequestDetails.setBorder(JTBorderFactory.createTitleBorder("Change Due Date"));
														//modelRefundOfPayment = new modelCARD_RefundOfPayment();
														modelChangeDueDate = new modelCARD_ChangeDueDate();

														tblRequestDetails = new _JTableMain(modelChangeDueDate);
														scrollRequestDetails.setViewportView(tblRequestDetails);

														{
															rowHeaderRequestDetails = tblRequestDetails.getRowHeader();
															rowHeaderRequestDetails.setModel(new DefaultListModel());
															scrollRequestDetails.setRowHeaderView(rowHeaderRequestDetails);
															scrollRequestDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
														}

														//_CARD.displayRefundHistory(modelRefundOfPayment, request_no, true, rowHeaderRequestDetails);
														_CARD.displayChangeDueDate(modelChangeDueDate, rowHeaderRequestDetails, request_no);
														scrollRequestDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRequestDetails.getRowCount())));
														/*scrollPWTotal.setViewportView(null);
														scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblRequestDetails.getRowCount())));*/
														tblRequestDetails.packAll();
													}

													if(request_desc.equals("Credit of Payment")){

													}

													displayRequestDetails(request_no, byrstatus_id);
												}
											}
										});
										tblClientRequestHistory.addMouseListener(new MouseAdapter() {

											public void mouseReleased(MouseEvent e){
												if(e.isPopupTrigger()){
													try {
														initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
													} catch (NullPointerException e1) { }
												}
											}

											public void mousePressed(MouseEvent e){
												if(e.isPopupTrigger()){
													try {
														initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
													} catch (NullPointerException e1) { }
												}
											}

											public JPopupMenu initializeMenu(MouseEvent e){
												_JTableMain table = (_JTableMain) e.getSource();
												//int[] rows = table.getSelectedRows();
												int row = table.getModelRow(table.getSelectedRow());
												int column = table.convertColumnIndexToModel(0);
												//final String status = (String) modelAccountStatusHistory.getValueAt(row, table.convertColumnIndexToModel(1));
												final String byrstatus_id = (String) modelClientReqHistory.getValueAt(row, 0);
												final String request_no = (String) modelClientReqHistory.getValueAt(row, column);
												final String request_status = (String) modelClientReqHistory.getValueAt(row, 6);

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
														mapRequest.put("request_type", byrstatus_id);

														if(byrstatus_id.equals("Waive Penalty") == false){
															FncReport.generateReport("/Reports/rptClientRequest.jasper", String.format("Client Request Approval (%s)", getValue(txtClientName.getText())), mapRequest);
														}else{
															FncReport.generateReport("/Reports/rptWaivePenalty2.jasper", String.format("Waive Penalty"), mapRequest);
														}
													}
												});

												JMenuItem menuViewOldSchedule = new JMenuItem("View Old Schedule");
												if(byrstatus_id.equals("Waive Penalty") == false){
													menu.add(menuViewOldSchedule);
												}
												menuViewOldSchedule.setFont(menuViewOldSchedule.getFont().deriveFont(10f));
												menuViewOldSchedule.addActionListener(new ActionListener() {

													@Override
													public void actionPerformed(ActionEvent e) {

														if(request_status.matches("For Approval")){
															JOptionPane.showMessageDialog(null, "Request is still for Approval", "View Old Schedule", JOptionPane.WARNING_MESSAGE);
														}else{
															//if(byrstatus_id.equals("Waive Penalty") == false){
															pnlOtherReq_PreviewSchedule.previewOldSchedulePosted(request_no);
															//}
														}
													}
												});

												System.out.printf("Display Buyer Status ID: %s%n", byrstatus_id);

												if(byrstatus_id.equals("Refund of Payment")){
													return null;
												}else{
													return menu;
												}
											}
										});
									}
									{
										rowHeaderClientRequestHistory = tblClientRequestHistory.getRowHeader();
										rowHeaderClientRequestHistory.setModel(new DefaultListModel());
										scrollClientRequestHistory.setRowHeaderView(rowHeaderClientRequestHistory);
										scrollClientRequestHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
								{ 
									JPanel pnlClientRequestSouth = new JPanel(new BorderLayout(3, 3));
									splitClientRequest.add(pnlClientRequestSouth);
									//pnlClientRequestSouth.setBorder(JTBorderFactory.createTitleBorder("Client Request Details"));
									{
										splitRequestDetails = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
										pnlClientRequestSouth.add(splitRequestDetails);
										splitRequestDetails.setOneTouchExpandable(true);
										//splitRequestDetails.setResizeWeight(0);
										//splitRequestDetails.setDividerLocation(.5);

										{
											JPanel pnlClientRequestDetails = new JPanel(new GridLayout(1, 2, 3, 3));
											splitRequestDetails.add(pnlClientRequestDetails, JSplitPane.LEFT);
											pnlClientRequestDetails.setBorder(JTBorderFactory.createTitleBorder("Client Request Details"));
											{
												JPanel pnlCRDWest = new JPanel(new BorderLayout(3, 3));
												pnlClientRequestDetails.add(pnlCRDWest, BorderLayout.WEST);
												{
													JPanel pnlCRDWLabel = new JPanel(new GridLayout(3, 1, 3, 3));
													pnlCRDWest.add(pnlCRDWLabel, BorderLayout.WEST);
													{
														lblReqFieldOld = new JXLabel("");
														pnlCRDWLabel.add(lblReqFieldOld);

													}
													{
														lblReqFieldNew = new JXLabel("");
														pnlCRDWLabel.add(lblReqFieldNew);

													}
												}
												{
													pnlReqComponent = new JPanel(new GridLayout(3, 1, 3, 3));
													pnlCRDWest.add(pnlReqComponent, BorderLayout.CENTER);
													{
														txtReqFieldOld = new _JXTextField();
														//pnlReqOldComponent.add(txtReqFieldOld);
													}
													{
														txtReqFieldNew = new _JXTextField();

													}
													{
														txtReqAmtOld = new _JXFormattedTextField();
													}
													{
														txtReqAmtNew = new _JXFormattedTextField();

													}
												}
											}
											/*{
												JPanel pnlCRDEast = new JPanel(new BorderLayout(3, 3));
												pnlClientRequestDetails.add(pnlCRDEast, BorderLayout.EAST);
												{
													JPanel pnlCRDELabel = new JPanel(new GridLayout(3, 1, 3, 3));
													pnlCRDEast.add(pnlCRDELabel, BorderLayout.WEST);
													{
														lblReqFieldNew = new JXLabel("");
														pnlCRDELabel.add(lblReqFieldNew);
													}
												}
											}*/
										}
										{
											pnlRequestDetails = new JPanel(new BorderLayout(3, 3));
											splitRequestDetails.add(pnlRequestDetails, JSplitPane.RIGHT);
											pnlRequestDetails.setBorder(JTBorderFactory.createTitleBorder("Refund of Payment"));
											{
												scrollRequestDetails = new _JScrollPaneMain();
												pnlRequestDetails.add(scrollRequestDetails, BorderLayout.CENTER);
												/*scrollRequestDetails.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
												scrollRequestDetails.addMouseListener(new MouseAdapter() {
													public void mouseClicked(MouseEvent e){
														tblRequestDetails.clearSelection();
													}
												});*/
												{
													modelRefundOfPayment = new modelCARD_RefundOfPayment();
													modelPmtsWaived = new modelCARD_PmtsWaived();

													tblRequestDetails = new _JTableMain(modelPmtsWaived);
													scrollRequestDetails.setViewportView(tblRequestDetails);

													tblRequestDetails.addMouseListener(new MouseAdapter() {
														public void mousePressed(MouseEvent e) {
															if(tblRequestDetails.rowAtPoint(e.getPoint()) == -1){
																tblPWTotal.clearSelection();
															}else{
																tblRequestDetails.setCellSelectionEnabled(true);
															}
														}
														public void mouseReleased(MouseEvent e) {
															if(tblRequestDetails.rowAtPoint(e.getPoint()) == -1){
																tblPWTotal.clearSelection();
															}else{
																tblRequestDetails.setCellSelectionEnabled(true);
															}
														}
													});
													/*modelPmtsWaived.addTableModelListener(new TableModelListener() {

														@Override
														public void tableChanged(TableModelEvent e) {
															if(e.getType() == TableModelEvent.DELETE){
																rowHeaderRequestDetails.setModel(new DefaultListModel());
																((DefaultListModel)rowHeaderRequestDetails.getModel()).addElement(modelPmtsWaived.getRowCount());
																scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPmtsWaived.getRowCount())));
																//_WaivePenalty.totalWPEntries(modelPmtsWaived, modelPmtsWaivedTotal);
																_CARD.totalWPEntries(modelPmtsWaived, modelPmtsWaivedTotal);
															}
															if(e.getType() == TableModelEvent.INSERT){
																((DefaultListModel)rowHeaderRequestDetails.getModel()).addElement(modelPmtsWaived.getRowCount());
																if(modelPmtsWaived.getRowCount() == 0){
																	rowHeaderRequestDetails.setModel(new DefaultListModel());
																	scrollPWTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPmtsWaived.getRowCount())));
																}
															}
														}
													});*/

												}
												{
													rowHeaderRequestDetails = tblRequestDetails.getRowHeader();
													rowHeaderRequestDetails.setModel(new DefaultListModel());
													scrollRequestDetails.setRowHeaderView(rowHeaderRequestDetails);
													scrollRequestDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
												}
											}
											{
												scrollPWTotal = new _JScrollPaneTotal(scrollRequestDetails);
												pnlRequestDetails.add(scrollPWTotal, BorderLayout.SOUTH);
												{
													modelPmtsWaivedTotal = new modelCARD_PmtsWaived();
													modelPmtsWaivedTotal.addRow(new Object[] {0.00, "Total", null});

													tblPWTotal = new _JTableTotal(modelPmtsWaivedTotal, tblRequestDetails);
													scrollPWTotal.setViewportView(tblPWTotal);

													tblPWTotal.setTotalLabel(1);
												}
											}
										}
									}
								}
								{
									JPanel pnlLOG_Details = new JPanel(new BorderLayout(3, 3));
									tabGeneralHistoryCenter.addTab("LOG Details", pnlLOG_Details);
									{
										scrollLOGDetails = new _JScrollPane();
										pnlLOG_Details.add(scrollLOGDetails, BorderLayout.CENTER);
										scrollLOGDetails.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										scrollLOGDetails.addMouseListener(new MouseAdapter() {
											public void mouseClicked(MouseEvent e){
												tblLOGDetails.clearSelection();
											}
										});
										{
											modelLOG_Details = new modelCARD_LOG_Details();
											modelLOG_Details.addTableModelListener(new TableModelListener() {

												@Override
												public void tableChanged(TableModelEvent e) {
													if(e.getType() == TableModelEvent.DELETE){
														rowHeaderLOGDetails.setModel(new DefaultListModel());
													}
													if(e.getType() == TableModelEvent.INSERT){
														((DefaultListModel) rowHeaderLOGDetails.getModel()).addElement(modelLOG_Details.getRowCount());
													}
												}
											});

											tblLOGDetails = new _JTableMain(modelLOG_Details);
											scrollLOGDetails.setViewportView(tblLOGDetails);
										}
										{
											rowHeaderLOGDetails = tblLOGDetails.getRowHeader();
											rowHeaderLOGDetails.setModel(new DefaultListModel());
											scrollLOGDetails.setRowHeaderView(rowHeaderLOGDetails);
											scrollLOGDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										}
									}
								}

								/*{  
									JPanel pnlClientRequestEast = new JPanel(new BorderLayout(3, 3));
									splitClientRequest.add(pnlClientRequestEast, JS
									plitPane.RIGHT);
									pnlClientRequestEast.setBorder(JTBorderFactory.createTitleBorder("Refund of Payment"));
									{
										JPanel pnlCARD_RefundOfPayment = new JPanel(new BorderLayout(3, 3));
										pnlClientRequestEast.add(pnlCARD_RefundOfPayment);
										{
											scrollRefundOfPayments = new _JScrollPane();
											pnlCARD_RefundOfPayment.add(scrollRefundOfPayments, BorderLayout.CENTER);
											scrollRefundOfPayments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
											scrollRefundOfPayments.addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent e){
													tblRefundOfPayments.clearSelection();
												}
											});
											{
												modelRefundOfPayment = new modelCARD_RefundOfPayment();
												modelRefundOfPayment.addTableModelListener(new TableModelListener() {

													@Override
													public void tableChanged(TableModelEvent e) {
														if(e.getType() == TableModelEvent.DELETE){
															rowHeaderRefundOfPayment.setModel(new DefaultListModel());
														}
														if(e.getType() == TableModelEvent.INSERT){
															((DefaultListModel) rowHeaderRefundOfPayment.getModel()).addElement(modelRefundOfPayment.getRowCount());
														}
													}
												});

												tblRefundOfPayments = new _JTableMain(modelRefundOfPayment);
												scrollRefundOfPayments.setViewportView(tblRefundOfPayments);
											}
											{
												rowHeaderRefundOfPayment = tblRefundOfPayments.getRowHeader();
												rowHeaderRefundOfPayment.setModel(new DefaultListModel());
												scrollRefundOfPayments.setRowHeaderView(rowHeaderRefundOfPayment);
												scrollRefundOfPayments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
											}
										}
									}
								}*/  
							}


							/*{
								scrollClientRequestHistory = new _JScrollPane();
								pnlClientReqHistory.add(scrollClientRequestHistory, BorderLayout.CENTER);
								scrollClientRequestHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollClientRequestHistory.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e){
										tblClientRequestHistory.clearSelection();
									}
								});
								{
									modelClientReqHistory = new modelCARD_ClientRequestHistory();
									modelClientReqHistory.addTableModelListener(new TableModelListener() {

										@Override
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderClientRequestHistory.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel) rowHeaderClientRequestHistory.getModel()).addElement(modelClientReqHistory.getRowCount());
											}

										}
									});

									tblClientRequestHistory = new _JTableMain(modelClientReqHistory);
									scrollClientRequestHistory.setViewportView(tblClientRequestHistory);
									tblClientRequestHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblClientRequestHistory.setSortable(false);
									tblClientRequestHistory.addMouseListener(new MouseAdapter() {

										public void mouseReleased(MouseEvent e){
											if(e.isPopupTrigger()){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}

										public void mousePressed(MouseEvent e){
											if(e.isPopupTrigger()){
												try {
													initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
												} catch (NullPointerException e1) { }
											}
										}

										public JPopupMenu initializeMenu(MouseEvent e){
											_JTableMain table = (_JTableMain) e.getSource();
											//int[] rows = table.getSelectedRows();
											int row = table.getModelRow(table.getSelectedRow());
											int column = table.convertColumnIndexToModel(8);
											//final String status = (String) modelAccountStatusHistory.getValueAt(row, table.convertColumnIndexToModel(1));
											final String byrstatus_id = (String) modelClientReqHistory.getValueAt(row, 0);
											final String request_no = (String) modelClientReqHistory.getValueAt(row, column);
											final String request_status = (String) modelClientReqHistory.getValueAt(row, 11);

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

											JMenuItem menuViewOldSchedule = new JMenuItem("View Old Schedule");
											menu.add(menuViewOldSchedule);
											menuViewOldSchedule.setFont(menuViewOldSchedule.getFont().deriveFont(10f));
											menuViewOldSchedule.addActionListener(new ActionListener() {

												@Override
												public void actionPerformed(ActionEvent e) {

													if(request_status.matches("For Approval")){
														JOptionPane.showMessageDialog(null, "Request is still for Approval", "View Old Schedule", JOptionPane.WARNING_MESSAGE);
													}else{
														pnlOtherReq_PreviewSchedule.previewOldSchedulePosted(request_no);
													}
												}
											});

											System.out.printf("Display Buyer Status ID: %s%n", byrstatus_id);

											if(byrstatus_id.equals("Refund of Payment")){
												return null;
											}else{
												return menu;
											}
										}
									});
								}
								{
									rowHeaderClientRequestHistory = tblClientRequestHistory.getRowHeader();
									rowHeaderClientRequestHistory.setModel(new DefaultListModel());
									scrollClientRequestHistory.setRowHeaderView(rowHeaderClientRequestHistory);
									scrollClientRequestHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}*/
						}
						{
							JXPanel pnlTOverMoveIn = new JXPanel();
							tabGeneralHistoryCenter.addTab("Turn-Over/Move-in Status", null, pnlTOverMoveIn, null);
							pnlTOverMoveIn.setLayout(new BorderLayout());
							{//**ADDED BY JED VICEDO 2019-07-17 : FOR PREVIEWING TURN OVER STATUS FOR OTHER LOTS**//
								JPanel pnlLot = new JPanel(new BorderLayout (5,5));
								pnlTOverMoveIn.add(pnlLot, BorderLayout.NORTH);
								pnlLot.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
								{
									JPanel pnlWestLabel = new JPanel(new BorderLayout(5,5));
									pnlLot.add(pnlWestLabel, BorderLayout.WEST);
									{
										JLabel lblLot = new JLabel("Select Lot to Preview");
										pnlWestLabel.add(lblLot);
									}
								}
								{
									JPanel pnlCenterComponents = new JPanel(new GridLayout(1,4,5,5));
									pnlLot.add(pnlCenterComponents, BorderLayout.CENTER);
									{
										cmbTOMSLot = new JComboBox();
										pnlCenterComponents.add(cmbTOMSLot);
										cmbTOMSLot.addItemListener(new ItemListener() {

											@Override
											public void itemStateChanged(ItemEvent e) {

												int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();

												String entity_id = lookupClient.getValue();
												String proj_id = txtProjectID.getText();
												String pbl_id = txtPblID.getText();
												Integer seq_no = getSequence();

												if(selected_index == 0){
													_CARD.displayTOverMoveIn(modelTOverMoveIn, entity_id, proj_id, pbl_id, seq_no, true);	
													tblTOverMoveIn.packAll();
												}

												if(selected_index == 1){
													String other_pbl_id = checkOtherLots(entity_id, pbl_id, proj_id, seq_no);
													_CARD.displayTOverMoveIn(modelTOverMoveIn, entity_id, proj_id, other_pbl_id, seq_no, true);
													tblTOverMoveIn.packAll();
												}
											}
										});
									}
									{
										pnlCenterComponents.add(Box.createHorizontalBox());
										pnlCenterComponents.add(Box.createHorizontalBox());
										pnlCenterComponents.add(Box.createHorizontalBox());
									}
								}
							}
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
								JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
								pnlUnitStatus.add(pnlNorth, BorderLayout.NORTH);
								pnlNorth.setBorder(new EmptyBorder(5, 5, 5, 5));
								{
									JLabel lblPreviewLot = new JLabel("Select Lot to Preview");
									pnlNorth.add(lblPreviewLot, BorderLayout.WEST);
								}
								{
									JPanel pnlPreviewLot = new JPanel(new BorderLayout(3, 3));
									pnlNorth.add(pnlPreviewLot, BorderLayout.CENTER);
									{
										cmbPreviewLot = new JComboBox();
										pnlPreviewLot.add(cmbPreviewLot, BorderLayout.WEST);
										cmbPreviewLot.setPreferredSize(new Dimension(200, 30));
										cmbPreviewLot.addItemListener(new ItemListener() {

											@Override
											public void itemStateChanged(ItemEvent e) {
												int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();

												String entity_id = lookupClient.getValue();
												String proj_id = txtProjectID.getText();
												String pbl_id = txtPblID.getText();
												Integer seq_no = getSequence();

												if(selected_index == 0){
													_CARD.displayUnitStatus(modelUnitStatus, entity_id, proj_id, pbl_id, seq_no, false ,false);
													scrollUnitStatus.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblUnitStatus.getRowCount())));
													tblUnitStatus.packAll();								
												}

												if(selected_index == 1){
													_CARD.displayUnitStatus(modelUnitStatus, entity_id, proj_id, pbl_id, seq_no, true ,true);
													scrollUnitStatus.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblUnitStatus.getRowCount())));
													tblUnitStatus.packAll();
												}

											}
										});
									}
								}
							}
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
						{//08-04-2021 Monique Boriga
							
							JXPanel pnlBIR = new JXPanel();
							tabGeneralHistoryCenter.addTab("BIR", null, pnlBIR, null);
							pnlBIR.setLayout(new BorderLayout(5, 5));
							{
								JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
								pnlBIR.add(pnlNorth, BorderLayout.CENTER);
								pnlNorth.setBorder(new EmptyBorder(20, 20, 20, 20));
								{
									{
										JPanel pnlLineStart = new JPanel(new GridLayout(4, 1, 5, 20));
										pnlNorth.add(pnlLineStart, BorderLayout.LINE_START);
										pnlLineStart.setBorder(new EmptyBorder(5, 5, 5, 5));
										pnlLineStart.setPreferredSize(new Dimension(125, 0));	
										
										{
											JLabel lblRDOCode = new JLabel("RDO"); 
											pnlLineStart.add(lblRDOCode);
											
											JLabel lblBank = new JLabel("Bank"); 
											pnlLineStart.add(lblBank);
											
											JLabel lblBranch = new JLabel("Bank Branch"); 
											pnlLineStart.add(lblBranch);
											
											JLabel lblAcctNo = new JLabel("Account Number"); 
											pnlLineStart.add(lblAcctNo);	
										}
									}
									
									{
										JPanel pnlCenter = new JPanel(new GridLayout(4, 1, 5, 20));
										pnlNorth.add(pnlCenter, BorderLayout.CENTER);
										{
											txtRDO = new JTextField();
											txtRDO.setHorizontalAlignment(JTextField.CENTER); 
											pnlCenter.add(txtRDO, BorderLayout.WEST);
											txtRDO.setEditable(false);
											
											txtBank = new JTextField();
											txtBank.setHorizontalAlignment(JTextField.CENTER); 
											pnlCenter.add(txtBank, BorderLayout.WEST);
											txtBank.setEditable(false);
																
											txtBranch = new JTextField();
											txtBranch.setHorizontalAlignment(JTextField.CENTER); 
											pnlCenter.add(txtBranch, BorderLayout.WEST);
											txtBranch.setEditable(false);
											
											txtAccntNo = new JTextField();
											txtAccntNo.setHorizontalAlignment(JTextField.CENTER);
											pnlCenter.add(txtAccntNo, BorderLayout.WEST);
											txtAccntNo.setEditable(false);
										}

											
									}
									
									{
										JPanel pnlExtra = new JPanel (new BorderLayout(5, 5));
										pnlNorth.add(pnlExtra, BorderLayout.LINE_END);
										pnlExtra.setPreferredSize(new Dimension(400, 0)); 
									
									}
								}
								
								
							}
							
						}
					}
				}
				{
					JXPanel pnlHouseConstructed = new JXPanel(new BorderLayout(5, 5));
					tabCenter.addTab("House Constructed", null, pnlHouseConstructed, null);
					pnlHouseConstructed.setLayout(new BorderLayout(5, 5));
					pnlHouseConstructed.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlNorth = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlHouseConstructed.add(pnlNorth, BorderLayout.NORTH);
						//pnlNorth.setLayout(new BorderLayout(5, 5));
						pnlNorth.setPreferredSize(new Dimension(0, 90));
						{

							scrollNTP_History = new JScrollPane();
							pnlNorth.add(scrollNTP_History, BorderLayout.CENTER);
							scrollNTP_History.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

							modelNTP_History = new modelHouseConstructedHistory();
							modelNTP_History.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowHeaderNTP_History.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderNTP_History.getModel()).addElement(modelNTP_History.getRowCount());
									}
								}
							});

							tblNTP_History = new _JTableMain(modelNTP_History);
							tblNTP_History.addMouseListener(this);
							scrollNTP_History.setViewportView(tblNTP_History);
							tblNTP_History.setSortable(false);

							tblNTP_History.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent arg0) {

									try {
										if(!arg0.getValueIsAdjusting()){
											if(tblNTP_History.getSelectedRows().length != 0){
												txtNotesValue();
												int row = tblNTP_History.convertRowIndexToModel(tblNTP_History.getSelectedRow());

												String ntp_no = (String) modelNTP_History.getValueAt(row, 0);
												String contract_no = (String) modelNTP_History.getValueAt(row, 2);
												_CARD.displayAccomplishment(modelAccomplishment, txtPblID.getText(), ntp_no, contract_no);

												if((boolean) clientDetails[44]){
													tabAccomplishment.setEnabledAt(1, true);
													_CARD.display2ndAccomplishment(modelAccomplishment2, txtPblID.getText());
													tblAccomplishment2.packAll();
												}else{
													tabAccomplishment.setSelectedIndex(0);
													tabAccomplishment.setEnabledAt(1, false);
												}
												scrollAccomplishment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccomplishment.getRowCount())));
												tblAccomplishment.packAll();
												tblAccomplishment.getColumn(1).setPreferredWidth(200);

											}
										}
									} catch (ArrayIndexOutOfBoundsException e) { }
								}
							});

							rowHeaderNTP_History = tblNTP_History.getRowHeader();
							rowHeaderNTP_History.setModel(new DefaultListModel());
							scrollNTP_History.setRowHeaderView(rowHeaderNTP_History);
							scrollNTP_History.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

						}
						//						{
						//							JScrollPane scrollNotes = new JScrollPane();
						//							pnlNorth.add(scrollNotes);
						//							scrollNotes.setBorder(_JTBorderFactory.createTitleBorder(""));
						//							{
						//								txtNotes = new JTextArea();
						//								scrollNotes.setViewportView(txtNotes);
						//								txtNotes.setEnabled(false);
						//							}
						//									
						////							JPanel pnltxtArea = new JPanel(new BorderLayout(5,5));
						////							pnlNorth.add(pnltxtArea);
						////							pnltxtArea.setBorder(_JTBorderFactory.createTitleBorder(""));
						////							{
						////								txtNotes = new JTextArea();
						////								pnltxtArea.add(txtNotes,BorderLayout.CENTER);
						////								JScrollPane scrollNotes = new JScrollPane();
						////								scrollNotes.setViewportView(txtNotes);
						////								
						////							}
						//						}
						/*{
							JPanel pnlColumn3 = new JPanel(new BorderLayout(3, 3));
							pnlNorth.add(pnlColumn3);
							{
								JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlColumn3.add(pnlLabels, BorderLayout.WEST);
								pnlLabels.setPreferredSize(new Dimension(80, 0));
								{
									JXLabel lblLotArea = new JXLabel("NTP #");
									pnlLabels.add(lblLotArea);
								}
								{
									JXLabel lblFloorArea = new JXLabel("Contractor");
									pnlLabels.add(lblFloorArea);
								}
								{
									JXLabel lblDuration = new JXLabel("Duration");
									pnlLabels.add(lblDuration);
								}
								{
									JXLabel lblRemarks = new JXLabel("Remarks");
									pnlLabels.add(lblRemarks);
								}
							}
							{
								JPanel pnlFields = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlColumn3.add(pnlFields, BorderLayout.CENTER);
								{
									txtNTPNo = new JXTextField();
									pnlFields.add(txtNTPNo);
									txtNTPNo.setEditable(false);
								}
								{
									txtContractor = new JXTextField();
									pnlFields.add(txtContractor);
									txtContractor.setFont(FncLookAndFeel.systemFont_Plain.deriveFont(10f));
									txtContractor.setEditable(false);
								}
								{
									txtDuration = new JXTextField();
									pnlFields.add(txtDuration);
									txtDuration.setEditable(false);
								}
								{
									txtRemarks = new JXTextField();
									pnlFields.add(txtRemarks);
									txtRemarks.setEditable(false);
								}
							}
						}
						{
							JPanel pnlColumn1 = new JPanel(new BorderLayout(3, 3));
							pnlNorth.add(pnlColumn1);
							{
								JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlColumn1.add(pnlLabels, BorderLayout.WEST);
								pnlLabels.setPreferredSize(new Dimension(120, 0));
								{
									JXLabel lblHouseConstructed = new JXLabel("NTP Date");
									pnlLabels.add(lblHouseConstructed);
								}
								{
									JXLabel lblTurnOver = new JXLabel("Contract #");
									pnlLabels.add(lblTurnOver);
								}
								{
									JXLabel lblHouseConstructed = new JXLabel("Work Description");
									pnlLabels.add(lblHouseConstructed);
								}
							}
							{
								JPanel pnlFields = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlColumn1.add(pnlFields, BorderLayout.CENTER);
								{
									txtDate1 = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlFields.add(txtDate1);
									txtDate1.getCalendarButton().setVisible(false);
								}
								{
									txtContractNo = new JXTextField();
									pnlFields.add(txtContractNo);
									txtContractNo.setEditable(false);
								}
								{
									txtWorkDesc = new JXTextField();
									pnlFields.add(txtWorkDesc);
									txtWorkDesc.setEditable(false);
								}
							}
						}*/
					}
					{
						tabAccomplishment = new JTabbedPane();
						pnlHouseConstructed.add(tabAccomplishment, BorderLayout.SOUTH);
						tabAccomplishment.setPreferredSize(new Dimension(0, 190)); 
						{
							JPanel pnlAccomplishment = new JPanel(new BorderLayout(5, 5));
							tabAccomplishment.addTab("Accomplishment", null, pnlAccomplishment, null);
							pnlAccomplishment.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
							pnlAccomplishment.setPreferredSize(new Dimension(800, 350));
							{
								JPanel pnlTable = new JPanel(new GridLayout(1,2,3,3));
								pnlAccomplishment.add(pnlTable);
								{
									scrollAccomplishment = new JScrollPane();
									//									pnlTable.add(scrollAccomplishment, BorderLayout.CENTER);
									pnlTable.add(scrollAccomplishment);
									scrollAccomplishment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

									modelAccomplishment = new model_accomplishment();
									modelAccomplishment.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderAccomplishment.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderAccomplishment.getModel()).addElement(modelAccomplishment.getRowCount());
											}
										}
									});

									tblAccomplishment = new _JTableMain(modelAccomplishment);
									tblAccomplishment.addMouseListener(this);
									scrollAccomplishment.setViewportView(tblAccomplishment);
									tblAccomplishment.setSortable(false);

									rowheaderAccomplishment = tblAccomplishment.getRowHeader();
									rowheaderAccomplishment.setModel(new DefaultListModel());
									scrollAccomplishment.setRowHeaderView(rowheaderAccomplishment);
									scrollAccomplishment.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
								{
									JPanel pnlTab = new JPanel(new BorderLayout(5,5));
									pnlTable.add(pnlTab);
									{
										JTabbedPane tabDept = new JTabbedPane();
										pnlTab.add(tabDept,BorderLayout.CENTER);
										tabDept.setPreferredSize(new Dimension(0,190));
										{
											JPanel pnlBat = new JPanel(new BorderLayout(5,5));
											tabDept.addTab("     BAT     ", null, pnlBat, null);


											JScrollPane scrollBat = new JScrollPane();
											pnlBat.add(scrollBat);

											txtBat = new JTextArea();
											scrollBat.setViewportView(txtBat);
											txtBat.setEditable(false);

										}
										{
											JPanel pnlPmd = new JPanel(new BorderLayout(5,5));
											tabDept.addTab("     PMD     ", null, pnlPmd, null);

											JScrollPane scrollPmd = new JScrollPane();
											pnlPmd.add(scrollPmd);

											txtPmd = new JTextArea();
											scrollPmd.setViewportView(txtPmd);
											txtPmd.setEditable(false);
										}
										{
											JPanel pnlCmd = new JPanel(new BorderLayout(5,5));
											tabDept.addTab("     CMD     ", null, pnlCmd, null);

											JScrollPane scrollCmd = new JScrollPane();
											pnlCmd.add(scrollCmd);

											txtCmd = new JTextArea();
											scrollCmd.setViewportView(txtCmd);
											txtCmd.setEditable(false);
										}
									}
									//									JScrollPane scrollNotes = new JScrollPane();
									//									pnlTable.add(scrollNotes);
									//									scrollNotes.setBorder(_JTBorderFactory.createTitleBorder(""));
									//									{
									//										txtNotes = new JTextArea();
									//										scrollNotes.setViewportView(txtNotes);
									//										txtNotes.setEnabled(false);
									//									}
								}
							}
						}
						{
							pnlAccomplishment2 = new JPanel(new BorderLayout(5, 5));
							tabAccomplishment.addTab("Accomplishment for 2nd lot", pnlAccomplishment2);
							tabAccomplishment.setEnabledAt(1, false);
							{
								scrollAccomplishment2 = new JScrollPane();
								pnlAccomplishment2.add(scrollAccomplishment2, BorderLayout.CENTER);
								scrollAccomplishment2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

								modelAccomplishment2 = new model_accomplishment();
								modelAccomplishment2.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowheaderAccomplishment2.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowheaderAccomplishment2.getModel()).addElement(modelAccomplishment2.getRowCount());
										}
									}
								});

								tblAccomplishment2 = new _JTableMain(modelAccomplishment2);
								tblAccomplishment2.addMouseListener(this);
								scrollAccomplishment2.setViewportView(tblAccomplishment2);

								rowheaderAccomplishment2 = tblAccomplishment2.getRowHeader();
								rowheaderAccomplishment2.setModel(new DefaultListModel());
								scrollAccomplishment2.setRowHeaderView(rowheaderAccomplishment2);
								scrollAccomplishment2.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
				}
				{//**EDITED BY JED 2019-09-12 : SEPARATE DISPLAY OF LOCATION FROM STATUS**//
					//JXPanel pnlTCT = new JXPanel(new GridLayout(2, 1, 3, 3));
					JXPanel pnlTCT = new JXPanel(new BorderLayout(3, 3));
					tabCenter.addTab("TCT/Tax Dec Status", null, pnlTCT, null);
					//pnlTCT.setLayout(new BorderLayout(3, 3));
					//pnlTCT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlTableTctTaxdec = new JPanel(new BorderLayout());
						//pnlTCT.add(pnlTableTctTaxdec);
						pnlTCT.add(pnlTableTctTaxdec, BorderLayout.NORTH);
						pnlTableTctTaxdec.setBorder(JTBorderFactory.createTitleBorder("Document Info"));
						pnlTableTctTaxdec.setPreferredSize(new Dimension(0, 150));
						if(UserInfo.EmployeeCode.equals("000645")){
							pnlTableTctTaxdec.setPreferredSize(new Dimension(0, 70));
						}
						{
							/*pnlTable = new JPanel();
							pnlTableTctTaxdec.add(pnlTable);
							//pnlTable.setBorder(lineBorder);
							 * 
							 * 
							{*/
							scrollTctTaxdec = new _JScrollPaneMain();
							pnlTableTctTaxdec.add(scrollTctTaxdec, BorderLayout.CENTER);
							scrollTctTaxdec.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							scrollTctTaxdec.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollTctTaxdec.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblTctTaxdec.clearSelection();
								}
							});
							modelTctTaxdec = new modelTctTaxdec();
							modelTctTaxdec.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowHeaderTctTaxdec.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderTctTaxdec.getModel()).addElement(modelTctTaxdec.getRowCount());
									}
								}
							});

							tblTctTaxdec = new _JTableMain(modelTctTaxdec);
							tblTctTaxdec.packAll();
							tblTctTaxdec.setAlignmentX(LEFT_ALIGNMENT);
							scrollTctTaxdec.setViewportView(tblTctTaxdec);

							tblTctTaxdec.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent arg0) {
									try {
										if(!arg0.getValueIsAdjusting()){
											if(tblTctTaxdec.getSelectedRows().length != 0){

												int row = tblTctTaxdec.convertRowIndexToModel(tblTctTaxdec.getSelectedRow());

												String doc_no = (String) modelTctTaxdec.getValueAt(row, 0);
												String pbl_id = txtPblID.getText();

												_CARD.displayDocStatus(modelTCTTax, pbl_id, doc_no, txtProjectID.getText());
												_CARD.displayDocLocation(modelTCTTax_Loc, pbl_id, doc_no);
												scrollTCTTax.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCTTax.getRowCount())));
												scrollTCTTax_Loc.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCTTax_Loc.getRowCount())));
												tblTCTTax.packAll();
												tblTCTTax_Loc.packAll();
												//tblTctTaxdec.getColumn(1).setPreferredWidth(200);
											}
										}
									} catch (ArrayIndexOutOfBoundsException e) { }
								}
							});

							tblTctTaxdec.putClientProperty("terminateEditOnFocusLost", true);
							{
								rowHeaderTctTaxdec = tblTctTaxdec.getRowHeader();
								rowHeaderTctTaxdec.setModel(new DefaultListModel());
								scrollTctTaxdec.setRowHeaderView(rowHeaderTctTaxdec);
								scrollTctTaxdec.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
							//}
						}
					}
					{
						JPanel pnlMainTableTCT = new JPanel(new BorderLayout(3,3));
						pnlTCT.add(pnlMainTableTCT, BorderLayout.CENTER);
						{
							JPanel subpnlMainTableTCT = new JPanel(new GridLayout(1,2,3,3));
							pnlMainTableTCT.add(subpnlMainTableTCT);
							{//**DOCUMENT LOCATION**//
								JPanel pnlLeftTableTCT = new JPanel(new BorderLayout());
								subpnlMainTableTCT.add(pnlLeftTableTCT);
								pnlLeftTableTCT.setBorder(JTBorderFactory.createTitleBorder("Document Location"));
								//pnlTableTCT.setPreferredSize(new Dimension(450, 0));
								{
									/*pnlTable1 = new JPanel();
									pnlTableTCT.add(pnlTable1);
									//pnlTable1.setBorder(lineBorder);
									{*/
									scrollTCTTax_Loc = new JScrollPane();
									pnlLeftTableTCT.add(scrollTCTTax_Loc, BorderLayout.CENTER);
									scrollTCTTax_Loc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
									scrollTCTTax_Loc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

									modelTCTTax_Loc = new modelDocStatus();
									modelTCTTax_Loc.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderTCTTax_Loc.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderTCTTax_Loc.getModel()).addElement(modelTCTTax_Loc.getRowCount());
											}
										}
									});

									tblTCTTax_Loc = new _JTableMain(modelTCTTax_Loc);
									tblTCTTax_Loc.addMouseListener(this);
									scrollTCTTax_Loc.setViewportView(tblTCTTax_Loc);

									rowheaderTCTTax_Loc = tblTCTTax_Loc.getRowHeader();
									rowheaderTCTTax_Loc.setModel(new DefaultListModel());
									scrollTCTTax_Loc.setRowHeaderView(rowheaderTCTTax_Loc);
									scrollTCTTax_Loc.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									//}
								}
							}
							{//**DOCUMENT STATUS**//
								JPanel pnlRightTableTCT = new JPanel(new BorderLayout());
								subpnlMainTableTCT.add(pnlRightTableTCT);
								pnlRightTableTCT.setBorder(JTBorderFactory.createTitleBorder("Document Status"));
								//pnlTableTCT.setPreferredSize(new Dimension(450, 0));
								{
									/*pnlTable1 = new JPanel();
									pnlTableTCT.add(pnlTable1);
									//pnlTable1.setBorder(lineBorder);
									{*/
									scrollTCTTax = new JScrollPane();
									pnlRightTableTCT.add(scrollTCTTax, BorderLayout.CENTER);
									scrollTCTTax.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
									scrollTCTTax.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

									modelTCTTax = new modelDocStatus();
									modelTCTTax.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowheaderTCTTax.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowheaderTCTTax.getModel()).addElement(modelTCTTax.getRowCount());
											}
										}
									});

									tblTCTTax = new _JTableMain(modelTCTTax);
									tblTCTTax.addMouseListener(this);
									scrollTCTTax.setViewportView(tblTCTTax);

									rowheaderTCTTax = tblTCTTax.getRowHeader();
									rowheaderTCTTax.setModel(new DefaultListModel());
									scrollTCTTax.setRowHeaderView(rowheaderTCTTax);
									scrollTCTTax.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									//}
								}
							}
						}
					}
				}
				/*	Modified by Mann2x; Date Modified: February 14, 2019; Design Improvement;	*/
				{
					pnlUnitStatus = new JXPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Unit Info/Status", null, pnlUnitStatus, null);
					pnlUnitStatus.setLayout(new BorderLayout(5, 5));
					pnlUnitStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						unitInfoStatus = new panCard_unitinfostatus(this); 
						pnlUnitStatus.add(unitInfoStatus);
					}
				}
				/*
				{
					pnlUnitStatus = new JXPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Unit Info/Status", null, pnlUnitStatus, null);
					pnlUnitStatus.setLayout(new BorderLayout(5, 5));
					pnlUnitStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
						pnlUnitStatus.add(pnlNorth, BorderLayout.NORTH);
						//pnlNorth.setLayout(new BorderLayout(5, 5));
						pnlNorth.setPreferredSize(new Dimension(0, 90));
						{
							JPanel pnlColumn3 = new JPanel(new BorderLayout(3, 3));
							pnlNorth.add(pnlColumn3, BorderLayout.WEST);
							pnlColumn3.setPreferredSize(new Dimension(350, 0));
							{
								JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlColumn3.add(pnlLabels, BorderLayout.WEST);
								pnlLabels.setPreferredSize(new Dimension(160, 0));
								{
									JXLabel lblLotArea = new JXLabel("Lot Area");
									pnlLabels.add(lblLotArea);
								}
								{
									JXLabel lblFloorArea = new JXLabel("Floor Area");
									pnlLabels.add(lblFloorArea);
								}
								{
									JXLabel lblPreassignedColor = new JXLabel("Pre-assigned Color Scheme");
									pnlLabels.add(lblPreassignedColor);
								}
								{
									JXLabel lblPreferredColor = new JXLabel("Preferred Color Scheme");
									pnlLabels.add(lblPreferredColor);
								}
							}
							{
								JPanel pnlFields = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlColumn3.add(pnlFields, BorderLayout.CENTER);
								{
									txtLotArea = new JXTextField();
									pnlFields.add(txtLotArea);
								}
								{
									txtFloorArea = new JXTextField();
									pnlFields.add(txtFloorArea);
								}
								{
									txtPreassignedColor = new JXTextField();
									pnlFields.add(txtPreassignedColor);
								}
								{
									txtPreferredColor = new JXTextField();
									pnlFields.add(txtPreferredColor);
								}
							}
						}
						{
							JPanel pnlColumnCenter = new JPanel(new GridLayout(1, 2, 3, 3));
							pnlNorth.add(pnlColumnCenter, BorderLayout.CENTER);
							{
								JPanel pnlColumn1 = new JPanel(new BorderLayout(3, 3));
								pnlColumnCenter.add(pnlColumn1);
								{
									JPanel pnlLabels = new JPanel(new GridLayout(3, 1, 3, 3));
									pnlColumn1.add(pnlLabels, BorderLayout.WEST);
									pnlLabels.setPreferredSize(new Dimension(120, 0));
									{
										JXLabel lblHouseConstructed = new JXLabel("House Constructed");
										pnlLabels.add(lblHouseConstructed);
									}
									{
										JXLabel lblTurnOver = new JXLabel("Turn Over");
										pnlLabels.add(lblTurnOver);
									}
									{
										JXLabel lblHouseConstructed = new JXLabel("With NTC");
										pnlLabels.add(lblHouseConstructed);
									}
								}
								{
									JPanel pnlFields = new JPanel(new GridLayout(3, 1, 3, 3));
									pnlColumn1.add(pnlFields, BorderLayout.CENTER);
									{
										txtHouseConstructed = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlFields.add(txtHouseConstructed);
										txtHouseConstructed.getCalendarButton().setVisible(false);
									}
									{
										txtTurnOver = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlFields.add(txtTurnOver);
										txtTurnOver.getCalendarButton().setVisible(false);
									}
									{
										txtWithNTC = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlFields.add(txtWithNTC);
										txtWithNTC.getCalendarButton().setVisible(false);
									}
								}
							}
							{
								JPanel pnlColumn2 = new JPanel(new BorderLayout(3, 3));
								pnlColumnCenter.add(pnlColumn2);
								{
									JPanel pnlLabels = new JPanel(new GridLayout(3, 1, 3, 3));
									pnlColumn2.add(pnlLabels, BorderLayout.WEST);
									pnlLabels.setPreferredSize(new Dimension(120, 0));
									{
										JXLabel lblHouseConstructed = new JXLabel("Moved-In");
										pnlLabels.add(lblHouseConstructed);
									}
									{
										JXLabel lblTurnOver = new JXLabel("Moved-Out");
										pnlLabels.add(lblTurnOver);
									}
									{
										JXLabel lblTCT = new JXLabel("TCT No.");
										pnlLabels.add(lblTCT);
									}
								}
								{
									JPanel pnlFields = new JPanel(new GridLayout(3, 1, 3, 3));
									pnlColumn2.add(pnlFields, BorderLayout.CENTER);
									{
										txtMovedIn = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlFields.add(txtMovedIn);
										txtMovedIn.getCalendarButton().setVisible(false);
									}
									{
										txtMovedOut = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlFields.add(txtMovedOut);
										txtMovedOut.getCalendarButton().setVisible(false);
									}
									{
										txtTct = new JXTextField();
										pnlFields.add(txtTct);
									}
								}
							}
						}
					}
					{ //Added by Jessa Herrera 2017-03-02
						JPanel pnlCenter = new JPanel(new GridLayout(1, 3, 3, 3));
						pnlUnitStatus.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setPreferredSize(new Dimension(0, 90));
						{
							tabTD = new _JTabbedPane();
							pnlCenter.add(tabTD, BorderLayout.CENTER);
							{
								JPanel pnlTD = new JPanel(new BorderLayout());
								tabTD.addTab("Technical Description", null, pnlTD, null);
								pnlTD.setPreferredSize(new java.awt.Dimension(100, 365));		

								pnlRemarks = new JPanel(new BorderLayout(5, 5));
								pnlTD.add(pnlRemarks, BorderLayout.CENTER);			
								pnlRemarks.setPreferredSize(new java.awt.Dimension(100, 178));

								scpDRFRemark = new JScrollPane();
								pnlRemarks.add(scpDRFRemark);
								{
									txtTD = new JTextArea();
									scpDRFRemark.setViewportView(txtTD);
									txtTD.setLineWrap(true);
									txtTD.setEditable(false);
									txtTD.setEnabled(true);	
								}			
							}
						}
					}
				}
				 */
				{
					JXPanel pnlOtherInfo = new JXPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Other Info", null, pnlOtherInfo, null);
					{
						JTabbedPane tabOtherInfo = new JTabbedPane();
						pnlOtherInfo.add(tabOtherInfo, BorderLayout.CENTER);
						{
							JXPanel pnlMRI_Fire = new JXPanel(new GridLayout(2, 1, 3, 3));
							tabOtherInfo.addTab("MRI/Fire Insurance", null, pnlMRI_Fire, null);
							{
								JPanel pnlMRI = new JPanel(new BorderLayout());
								pnlMRI_Fire.add(pnlMRI);
								//pnlMRI.setLayout(new BorderLayout());
								pnlMRI.setBorder(JTBorderFactory.createTitleBorder("MRI"));
								{
									scrollMRI = new _JScrollPane();
									pnlMRI.add(scrollMRI, BorderLayout.CENTER);
									scrollMRI.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									{
										modelMRI = new modelCARD_MRI();
										modelMRI.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {

												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderMRI.setModel(new DefaultListModel());
												}

												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel) rowHeaderMRI.getModel()).addElement(modelMRI.getRowCount());
												}
											}
										});

										tblMRI = new _JTableMain(modelMRI);
										scrollMRI.setViewportView(tblMRI);
										tblMRI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										tblMRI.setSortable(false);
									}
									{
										rowHeaderMRI = tblMRI.getRowHeader();
										rowHeaderMRI.setModel(new DefaultListModel());
										scrollMRI.setRowHeaderView(rowHeaderMRI);
										scrollMRI.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
							}
							{
								JXPanel pnlFire = new JXPanel(new BorderLayout());
								pnlMRI_Fire.add(pnlFire);
								pnlFire.setBorder(JTBorderFactory.createTitleBorder("Fire"));
								{
									scrollFire = new _JScrollPane();
									pnlFire.add(scrollFire, BorderLayout.CENTER);
									scrollFire.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

									{
										modelFire = new modelCARD_Fire();
										modelFire.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderFire.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel) rowHeaderFire.getModel()).addElement(modelFire.getRowCount());
												}
											}
										});

										tblFire = new _JTableMain(modelFire);
										scrollFire.setViewportView(tblFire);
										tblFire.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										tblFire.setSortable(false);
										tblFire.hideColumns("Rec. ID");
									}
									{
										rowHeaderFire = tblFire.getRowHeader();
										rowHeaderFire.setModel(new DefaultListModel());
										scrollFire.setRowHeaderView(rowHeaderFire);
										scrollFire.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
							}
						}
						{
							JXPanel pnlBankAccounts = new JXPanel(new GridLayout());
							tabOtherInfo.addTab("Bank Accounts", pnlBankAccounts);
							//pnlBankAccounts.setBorder(JTBorderFactory.createTitleBorder("Bank Accounts"));
							{
								scrollBankAccounts = new _JScrollPane();
								pnlBankAccounts.add(scrollBankAccounts, BorderLayout.CENTER);
								scrollBankAccounts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								{
									modelBankAccts = new modelCARD_BankAccounts();
									modelBankAccts.addTableModelListener(new TableModelListener() {

										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderBankAccounts.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel) rowHeaderBankAccounts.getModel()).addElement(modelBankAccts.getRowCount());
											}
										}
									});
									{
										tblBankAccounts = new _JTableMain(modelBankAccts);
										scrollBankAccounts.setViewportView(tblBankAccounts);
										tblBankAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										tblBankAccounts.setSortable(false);
										tblBankAccounts.hideColumns("Rec. ID","Bank ID", "Branch ID", "Account Type ID");
									}
								}
								{
									rowHeaderBankAccounts = tblBankAccounts.getRowHeader();
									rowHeaderBankAccounts.setModel(new DefaultListModel());
									scrollBankAccounts.setRowHeaderView(rowHeaderBankAccounts);
									scrollBankAccounts.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							JXPanel pnlConnections = new JXPanel(new BorderLayout(3, 3));
							tabOtherInfo.addTab("Connections", pnlConnections);
							{
								scrollConnections = new _JScrollPane();
								pnlConnections.add(scrollConnections, BorderLayout.CENTER);
								scrollConnections.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								{
									modelConnections = new modelConnectionList();
									modelConnections.addTableModelListener(new TableModelListener() {

										public void tableChanged(TableModelEvent e) {
											if(e.getType() == TableModelEvent.DELETE){
												rowHeaderConnections.setModel(new DefaultListModel());
											}
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel) rowHeaderConnections.getModel()).addElement(modelConnections.getRowCount());
											}
										}
									});
									{
										tblConnections = new _JTableMain(modelConnections);
										scrollConnections.setViewportView(tblConnections);
										tblConnections.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										tblConnections.setSortable(false);
										tblConnections.hideColumns("Rec. ID", "First Name", "Middle Name", "Last Name", "Gender" ,"Type ID", "Relation ID", "Unit ID");
										//tblBankAccounts.hideColumns("Rec. ID","Bank ID", "Branch ID", "Account Type ID");
									}
								}
								{
									rowHeaderConnections = tblConnections.getRowHeader();
									rowHeaderConnections.setModel(new DefaultListModel());
									scrollConnections.setRowHeaderView(rowHeaderConnections);
									scrollConnections.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						{
							JXPanel pnlPCost = new JXPanel(new BorderLayout(3, 3));
							tabOtherInfo.addTab("Processing Cost", pnlPCost);
							{//**ADDED BY JED 2019-04-16 : TO SELECT AND DISPLAY 2ND LOTS**//
								JPanel pnlPCostNorth = new JPanel(new BorderLayout(5,5));
								pnlPCost.add(pnlPCostNorth, BorderLayout.NORTH);
								pnlPCostNorth.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
								{
									JPanel pnlWestLabel = new JPanel(new BorderLayout(5,5));
									pnlPCostNorth.add(pnlWestLabel, BorderLayout.WEST);
									{
										JLabel lblLot = new JLabel("Select Lot to Preview");
										pnlWestLabel.add(lblLot);
									}
									
									JPanel pnlEastLabel = new JPanel(new BorderLayout(5,5));
									pnlPCostNorth.add(pnlEastLabel, BorderLayout.EAST);
									pnlEastLabel.setPreferredSize(new Dimension(800, 0));
									
									{
										JLabel lblRPTOffset = new JLabel("RPT AMOUNT OFFSET FROM CD EXCESS 150.00 AS OF 02/29/2024");
										pnlEastLabel.add(lblRPTOffset);
									}
								}
								{
									JPanel pnlPCostCenter = new JPanel(new GridLayout(1,4,5,5));
									pnlPCostNorth.add(pnlPCostCenter, BorderLayout.CENTER);
									{
										cmblot = new JComboBox();
										pnlPCostCenter.add(cmblot);
										cmblot.addItemListener(new ItemListener() {

											@Override
											public void itemStateChanged(ItemEvent e) {
												int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();
												String entity_id = lookupClient.getValue();
												String pbl_id = txtPblID.getText();
												String proj_id = txtProjectID.getText();

												if(selected_index == 0){
													/*System.out.printf("The value of seq_no is: %s\n", seq_no);
													System.out.printf("The value of pbl_id is: %s\n", pbl_id);
													System.out.printf("The value of entity_id is: %s\n", entity_id);
													System.out.printf("The value of proj_id is: %s\n", proj_id);*/
													_CARD.displayPCostDetails(modelPCD, modelPCDTotal, entity_id, proj_id, pbl_id, seq_no);
												}

												if(selected_index == 1){
													String other_pbl_id = checkOtherLots(entity_id, pbl_id, proj_id, seq_no);
													/*System.out.printf("The value of seq_no is: %s\n", seq_no);
													System.out.printf("The value of other_pbl_id is: %s\n", other_pbl_id);
													System.out.printf("The value of entity_id is: %s\n", entity_id);
													System.out.printf("The value of proj_id is: %s\n", proj_id);*/
													_CARD.displayPCostDetails(modelPCD, modelPCDTotal, entity_id, proj_id, other_pbl_id, seq_no);
												}
											}
										} );
									}
									//COMMENTED BY MONIQUE DTD 04-23-2024
									//									{
									//										pnlPCostCenter.add(Box.createHorizontalBox());
									//										pnlPCostCenter.add(Box.createHorizontalBox());
									//										pnlPCostCenter.add(Box.createHorizontalBox());
									//									}
								}
								{ // ADDED BY MONIQUE REFER TO DCRF#2950
									JPanel pnlPCostRPTOffset = new JPanel(new GridLayout(1, 2, 5, 5)); 
									pnlPCostNorth.add(pnlPCostRPTOffset, BorderLayout.EAST); 
									pnlPCostRPTOffset.setPreferredSize(new Dimension(800, 0));
									{
										lblRPTOffset = new JLabel(""); 
										pnlPCostRPTOffset.add(lblRPTOffset);
									}
									{
										pnlCreditRPTOffset = new JPanel(new BorderLayout(5, 5));
										pnlPCostRPTOffset.add(pnlCreditRPTOffset); 
										
										{
											lblRPTCredit = new JLabel(""); 
											pnlCreditRPTOffset.add(lblRPTCredit, BorderLayout.CENTER);
											
										}
										{
											btnCreditedTo = new JButton("See Client(s)");
											pnlCreditRPTOffset.add(btnCreditedTo, BorderLayout.EAST); 	
											btnCreditedTo.setPreferredSize(new Dimension(150, 0));
											btnCreditedTo.setVisible(false);
											
											clientList = new JTextArea();
											clientList.setEditable(false);	
											scrollCreditTo = new JScrollPane(clientList);
											scrollCreditTo.setViewportView(clientList);
											scrollCreditTo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
											scrollCreditTo.setPreferredSize(new Dimension(500, 200));
											
											btnCreditedTo.addActionListener(new ActionListener() {
												
												@Override
												public void actionPerformed(ActionEvent e) {
													
													 clientList.setText(rptCreditRemarks);
													 clientList.setCaretPosition(0);  // Set caret position to the start of the text
													 JOptionPane.showMessageDialog(pnlCreditRPTOffset, scrollCreditTo, "CD-Excess Credited to", JOptionPane.PLAIN_MESSAGE);
												}
											});
											
										}
									}
								}
							}
							{
								scrollPCD = new _JScrollPaneMain();
								pnlPCost.add(scrollPCD, BorderLayout.CENTER);
								//scrollPCD.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								modelPCD = new model_pcost_CARD();
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
								tblPCD.hideColumns("Tag");
								tblPCD.hideColumns("Doc No");
								tblPCD.hideColumns("JV No");
								//tblPCD.hideColumns("Date Paid");

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
								pnlPCost.add(scrollPCDTotal, BorderLayout.SOUTH);
								{
									modelPCDTotal = new model_pcost_CARD();
									modelPCDTotal.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00), null, null, null, null, null, null, null });

									tblPCDTotal = new _JTableTotal(modelPCDTotal, tblPCD);
									scrollPCDTotal.setViewportView(tblPCDTotal);

									tblPCDTotal.setTotalLabel(2);
									tblPCDTotal.hideColumns("Tag");
									tblPCDTotal.hideColumns("Doc No");
									tblPCDTotal.hideColumns("JV No");
									//tblPCDTotal.hideColumns("Date Paid");
								}
							}
						}
						{
							JXPanel pnlTCost = new JXPanel(new BorderLayout(3, 3));
							tabOtherInfo.addTab("Transfer Cost", pnlTCost);
							{//**ADDED BY JED 2019-04-16 : TO SELECT AND DISPLAY 2ND LOTS**//
								JPanel pnlTCostNorth = new JPanel(new BorderLayout(5,5));
								pnlTCost.add(pnlTCostNorth, BorderLayout.NORTH);
								pnlTCostNorth.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
								{
									JPanel pnlWestLabel = new JPanel(new BorderLayout(5,5));
									pnlTCostNorth.add(pnlWestLabel, BorderLayout.WEST);
									{
										JLabel lblLot = new JLabel("Select Lot to Preview");
										pnlWestLabel.add(lblLot);
									}
								}
								{
									JPanel pnlTCostCenter = new JPanel(new GridLayout(1,4,5,5));
									pnlTCostNorth.add(pnlTCostCenter, BorderLayout.CENTER);
									{
										cmblot1 = new JComboBox();
										pnlTCostCenter.add(cmblot1);
										cmblot1.addItemListener(new ItemListener() {

											@Override
											public void itemStateChanged(ItemEvent e) {
												int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();
												String entity_id = lookupClient.getValue();
												String pbl_id = txtPblID.getText();
												String proj_id = txtProjectID.getText();

												if(selected_index == 0){
													/*System.out.printf("The value of seq_no is: %s\n", seq_no);
													System.out.printf("The value of pbl_id is: %s\n", pbl_id);
													System.out.printf("The value of entity_id is: %s\n", entity_id);
													System.out.printf("The value of proj_id is: %s\n", proj_id);*/
													_CARD.displayTCostDetails(modelTCT, modelTCTTotal, entity_id, proj_id, pbl_id, seq_no);
												}

												if(selected_index == 1){
													String other_pbl_id = checkOtherLots(entity_id, pbl_id, proj_id, seq_no);
													/*System.out.printf("The value of seq_no is: %s\n", seq_no);
													System.out.printf("The value of other_pbl_id is: %s\n", other_pbl_id);
													System.out.printf("The value of entity_id is: %s\n", entity_id);
													System.out.printf("The value of proj_id is: %s\n", proj_id);*/
													_CARD.displayTCostDetails(modelTCT, modelTCTTotal, entity_id, proj_id, other_pbl_id, seq_no);
												}
											}
										} );
									}
									{
										pnlTCostCenter.add(Box.createHorizontalBox());
										pnlTCostCenter.add(Box.createHorizontalBox());
										pnlTCostCenter.add(Box.createHorizontalBox());
									}
								}
							}
							{
								scrollTCT = new _JScrollPaneMain();
								pnlTCost.add(scrollTCT, BorderLayout.CENTER);
								//scrollPCD.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								modelTCT = new model_tcost();
								modelTCT.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowHeaderTCT.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowHeaderTCT.getModel()).addElement(modelTCT.getRowCount());
										}
									}
								});

								tblTCT = new _JTableMain(modelTCT);
								tblTCT.packAll();
								tblTCT.setAlignmentX(LEFT_ALIGNMENT);
								scrollTCT.setViewportView(tblTCT);
								tblTCT.hideColumns("Tag");
								tblTCT.hideColumns("Doc No");
								tblTCT.hideColumns("JV No");
								//tblTCT.hideColumns("Date Paid");

								tblTCT.putClientProperty("terminateEditOnFocusLost", true);

								{
									rowHeaderTCT = tblTCT.getRowHeader();
									rowHeaderTCT.setModel(new DefaultListModel());
									scrollTCT.setRowHeaderView(rowHeaderTCT);
									scrollTCT.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}

							}
							{
								scrollTCTTotal = new _JScrollPaneTotal(scrollTCT);
								pnlTCost.add(scrollTCTTotal, BorderLayout.SOUTH);
								{
									modelTCTTotal = new model_tcost();
									modelTCTTotal.addRow(new Object[] { null, null, "Total =>", new BigDecimal(0.00), null, null, null, null, null, null, null });

									tblTCTTotal = new _JTableTotal(modelTCTTotal, tblTCT);
									scrollTCTTotal.setViewportView(tblTCTTotal);

									tblTCTTotal.setTotalLabel(2);
									tblTCTTotal.hideColumns("Tag");
									//tblTCTTotal.hideColumns("Doc No");
									//tblTCTTotal.hideColumns("JV No");
									//tblTCTTotal.hideColumns("Date Paid");
								}
							}
						}
						{
							pnlTCostCompu = new TCostComputation();
							tabOtherInfo.addTab("TCost Computation", null, pnlTCostCompu, null);
						}
						{
							JXPanel pnlPM = new JXPanel(new BorderLayout(3, 3));
							tabOtherInfo.addTab(" Property Management  ", pnlPM);

							PM = new PropertyManagement(this);
							pnlPM.add(PM, BorderLayout.CENTER);
						}
						/*{
							pnlHR = new _HouseRepair();
							tabOtherInfo.addTab("House Repair", null, pnlHR, null);
						}*/
						{  //added on Oct.3, 2018 - DCRF #729  
							JXPanel pnlHR = new JXPanel(new BorderLayout(3, 3));
							tabOtherInfo.addTab("House Repair", pnlHR );
							{
								scrHR = new _JScrollPaneMain();
								pnlHR.add(scrHR, BorderLayout.CENTER);
								scrHR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


								modelHR = new model_HouseRepair();
								modelHR.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowHR.setModel(new DefaultListModel());
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowHR.getModel()).addElement(modelHR.getRowCount());
										}
									}
								});

								tblHR = new _JTableMain(modelHR);
								tblHR.packAll();
								tblHR.setAlignmentX(LEFT_ALIGNMENT);
								scrHR.setViewportView(tblHR);

								{
									rowHR = tblHR.getRowHeader();
									rowHR.setModel(new DefaultListModel());
									scrHR.setRowHeaderView(rowHR);
									scrHR.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}

							}

						}
					}
				}
				/*	Modified by Mann2x; Date Modified: October 20, 2017; DCRF# 289;	*/
				{
					JXPanel pnlPagibigInfo = new JXPanel(new BorderLayout(5, 5));
					tabCenter.addTab("   PAGIBIG Info   ", null, pnlPagibigInfo, null);
					{
						JXPanel panHDMFmain = new JXPanel(new BorderLayout(5, 5));
						pnlPagibigInfo.add(panHDMFmain, BorderLayout.CENTER);
						panHDMFmain.setBorder(lineBorder);
						{
							String client_id = "";
							String project_id = "";
							String unit_id = "";
							String seq_no = "";

							if (lookupClient.getValue()==null || lookupClient.getValue()=="null" || lookupClient.getValue()=="") {
								client_id = "";
							} else {
								client_id = lookupClient.getValue();
							}

							if (txtProjectID.getText()==null || txtProjectID.getText()=="null" || txtProjectID.getText()=="") {
								project_id = "";
							} else {
								project_id = txtProjectID.getText();
							}							

							if (txtPblID.getText()==null || txtPblID.getText()=="null" || txtPblID.getText()=="") {
								unit_id = "";
							} else {
								unit_id = txtPblID.getText();
							}

							if (getSequence()==null || getSequence()==0) {
								seq_no = "0";
							} else {
								seq_no = getSequence().toString();
							}

							System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
							System.out.println("client_id: " + client_id);
							System.out.println("project_id: " + project_id); 
							System.out.println("unit_id: " + unit_id); 
							System.out.println("seq_no: " + seq_no); 

							hdmftab = new hdmfInfo_maintab(this);
							panHDMFmain.add(hdmftab, BorderLayout.CENTER);
						}
					}
				}
				{
					JXPanel pnlFollowUp = new JXPanel();
					tabCenter.addTab("Follow-Ups", null, pnlFollowUp, null);
					pnlFollowUp.setLayout(new BorderLayout(5, 5));
					{
						scrollFollowUp = new _JScrollPane();
						pnlFollowUp.add(scrollFollowUp, BorderLayout.CENTER);
						scrollFollowUp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

						{
							modelFollowup = new modelClientFollowup();
							modelFollowup.addTableModelListener(new TableModelListener() {

								@Override
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowHeaderFollowUp.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel) rowHeaderFollowUp.getModel()).addElement(modelFollowup.getRowCount());
									}
								}
							});

							tblFollowUp = new _JTableMain(modelFollowup);
							scrollFollowUp.setViewportView(tblFollowUp);
							tblFollowUp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblFollowUp.setSortable(false);
							tblFollowUp.hideColumns("Rec. ID", "Emp. ID");
							TableColumnModel colModel = tblFollowUp.getColumnModel();
							TableColumn col = colModel.getColumn(1);
							col.setCellRenderer(new _JTextAreaRenderer2());
							col.setCellEditor(new _JTextAreaEditor());
							tblFollowUp.setRowHeight(100);
							tblFollowUp.setCellSelectionEnabled(true);
							tblFollowUp.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
							tblFollowUp.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
							//tblFollowUp.setRowHeight(25);
						}
						{
							rowHeaderFollowUp = tblFollowUp.getRowHeader();
							rowHeaderFollowUp.setFixedCellHeight(100);
							rowHeaderFollowUp.setModel(new DefaultListModel());
							scrollFollowUp.setRowHeaderView(rowHeaderFollowUp);
							scrollFollowUp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
				
				/*ADED BY JARI CRUZ ASOF NOV 24 2022*/
				{
					JXPanel pnlRequests = new JXPanel(new BorderLayout(5, 5));
					tabCenter.addTab("Requests", null, pnlRequests, null);
					{
						JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
						pnlRequests.add(panMain, BorderLayout.CENTER);
						panMain.setBorder(lineBorder);
						{
							pnlRequest = new _CARD_REQUESTS(this);
							panMain.add(pnlRequest, BorderLayout.CENTER);
						}
					}
				}

				tabCenter.addChangeListener(new ChangeListener() {

					public void stateChanged(ChangeEvent e) {

						if(lookupClient.getValue() != null){
							String entity_id = lookupClient.getValue();
							String proj_id = txtProjectID.getText();
							String pbl_id = txtPblID.getText();
							Integer seq_no  = getSequence();

							displayTabDetails(entity_id, proj_id, pbl_id, seq_no, true);
						}
					}
				});
			}
		}

		FncTables.bindColumnTables(tblDues, tblDuesTotal);
		tblDues.hideColumns("Part. ID", "CBP", "Adjustments");

		_CARD.initTAGS(pnlNorth, this);
		_CARD.initTAGS(pnlUnitStatus, this);

		UIManager.put("TabbedPane.tabInsets", new Insets(2, 20, 1, 20));
		UIManager.put("TabbedPane.font", UIManager.getFont("TabbedPane.font").deriveFont(12f));
		splitClientDetailsEast.getRightComponent().setVisible(false);
		/**
		 * added by Alvin Gonzales (2015-09-12) to auto refresh CARD Displays
		 */
		if(UserInfo.EmployeeCode.equals("900668")){
			/*Runnable helloRunnable = new Runnable() {
				public void run() {
					System.out.println("**************************************** AUTO-REFRESH CARD ****************************************");
					refreshCARD(false);
				}
			};
			executor.scheduleAtFixedRate(helloRunnable, 1, 60, TimeUnit.SECONDS);*/
		}
	}

	private String generateTAG(Object text) {
		return String.format("[ %s ]", text);
	}

	//Done
	private void refreshCARD(Boolean toPrint) {
		if(lookupClient.getValue() != null){
			String entity_id = lookupClient.getValue();
			String proj_id = txtProjectID.getText();
			String pbl_id = txtPblID.getText();
			Integer seq_no = getSequence();

			displayClientDetails(entity_id, proj_id, pbl_id, seq_no, toPrint);
			displayTabDetails(entity_id, proj_id, pbl_id, seq_no, toPrint);

			hdmfInfo_maintab.displayLoanFilingStatus(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFUnitInspection(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFPayments(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFSchedule(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFLoanReleasedDetail(entity_id, proj_id, pbl_id, seq_no.toString());
			tab_remConversion.DisplayHDMFREM(entity_id, proj_id, pbl_id, seq_no.toString());
		}
	}

	@SuppressWarnings("null")
	//Done
	private void displayClientDetails(String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		clientDetails = _CARD.displayClientInformation(entity_id, proj_id, pbl_id, seq_no, toPrint);
		
		if(clientDetails != null){
			lookupClient.setText((String) clientDetails[0]);
			txtClientName.setText(generateTAG((String) clientDetails[1])); 
			txtClientName.setCaretPosition(0);
			//txtClientName.setFont(new Font("SansSerif", Font.BOLD, 7));

			txtProjectID.setText((String) clientDetails[2]);
			txtProjectName.setText(generateTAG((String) clientDetails[3]));

			txtPblID.setText((String) clientDetails[4]);
			txtPblDescription.setText(String.format("[ %s ] [ %s ]", (String) clientDetails[5], Integer.toString((Integer) clientDetails[6])));

			txtModelID.setText((String) clientDetails[7]);
			txtModelName.setText(generateTAG(clientDetails[8]));

			//txtModelName.setText(String.format("[ %s ] [ %s ]", (String) clientDetails[8], (String) clientDetails[42]));

			if((boolean) clientDetails[44]){
				System.out.println("Dumaan dito sa with other unit");
				//pnlModelName.setLayout(new GridLayout(2, 1));
				//pnlModelName.add(txtModelName2);
				pnlModelName.setVisible(true);
				txtModelName.setText(String.format("[ %s ]", (String) clientDetails[8]));
				txtModelName2.setText(generateTAG(clientDetails[53]));
				pnlModelName.repaint();
			}else{
				pnlModelName.setVisible(false);
			}

			//txtModelName.setText(String.format("", clientDetails[42]));
			//txtModelName.setText(generateTAG((String) clientDetails[8]));

			txtBuyerTypeID.setText((String) clientDetails[9]);
			txtBuyerTypeID.setToolTipText((String) clientDetails[25]);
			txtBuyerTypeName.setText(generateTAG((String) clientDetails[10]));
			txtBuyerTypeName.setCaretPosition(0);

			txtPmtSchemeID.setText((String) clientDetails[11]);
			txtPmtSchemeName.setText(generateTAG((String) clientDetails[12]));
			txtPmtSchemeName.setCaretPosition(0);

			txtStatusID.setText((String) clientDetails[13]);
			txtStatusName.setText(generateTAG((String) clientDetails[14]));
			

			//setSecondaryTitle((String) clientDetails[14]);
			if(((String) clientDetails[13]).equals("02") || ((String) clientDetails[13]).equals("25") || ((String) clientDetails[13]).equals("32") || ((String) clientDetails[13]).equals("16") || ((String) clientDetails[13]).equals("03") || ((String) clientDetails[13]).equals("135")){
				/*
				 * setSecondaryTitle((String) clientDetails[14]);
				 * 
				 * if(_CARD.isUnitTransferred(entity_id, proj_id, pbl_id, seq_no)){
				 * setSecondaryTitle(String.format("Transferred to %s",
				 * _CARD.getTransferredUnit(entity_id, proj_id, pbl_id, seq_no))); }
				 * 
				 * if(((String) clientDetails[13]).equals("02") &&
				 * _CARD.isUnitTransferred(entity_id, proj_id, pbl_id, seq_no) == false){
				 * setSecondaryTitle(String.format("%s on %s", (String)clientDetails[14],
				 * _CARD.getCancelledDate(entity_id, proj_id, pbl_id, seq_no))); } if(((String)
				 * clientDetails[13]).equals("25")){ setSecondaryTitle(String.format("%s on %s",
				 * (String)clientDetails[14], _CARD.getSoldToBankDate(entity_id, proj_id,
				 * pbl_id, seq_no))); }
				 * 
				 * if(_CARD.isUnitTransferred(entity_id, proj_id, pbl_id, seq_no)){
				 * setSecondaryTitle(String.format("Transferred to %s",
				 * _CARD.getTransferredUnit(entity_id, proj_id, pbl_id, seq_no))); }
				 * 
				 * if(((String) clientDetails[13]).equals("32")){
				 * setSecondaryTitle(String.format("%s on %s", (String)clientDetails[14],
				 * _CARD.getLoanReleasedDate(entity_id, proj_id, pbl_id, seq_no)));
				 * //System.out.printf("Display value of secondary title: %s%n",
				 * String.format("%s on %s", (String)clientDetails[14],
				 * _CARD.getLoanReleasedDate(entity_id, proj_id, pbl_id, seq_no)));
				 * //System.out.println("Dumaan dito sa Loan Released"); }
				 * 
				 * if(((String) clientDetails[13]).equals("135")){
				 * setSecondaryTitle(String.format("%s on %s", (String)clientDetails[14],
				 * _CARD.getBankfFinanceFullSettledDate(entity_id, proj_id, pbl_id, seq_no)));
				 * //System.out.printf("Display value of secondary title: %s%n",
				 * String.format("%s on %s", (String)clientDetails[14],
				 * _CARD.getLoanReleasedDate(entity_id, proj_id, pbl_id, seq_no)));
				 * //System.out.println("Dumaan dito sa Loan Released"); }
				 */


				/*if(timerStatus != null){
					System.out.printf("********************************************************DUMAAN! (%s)%n", timerStatus.isRunning());
					if(timerStatus.isRunning()){
						timerStatus.stop();
						System.out.printf("********************************************************DUMAAN! (%s)%n", timerStatus.isRunning());
						timerStatus = null;
					}
				}*/
				//startTimerStatus();
			}else{
				/*
				 * if(timerStatus != null){ timerStatus.stop(); }
				 */

				/*
				 * if(_CARD.withHousingLoan(entity_id, proj_id, pbl_id, seq_no)){
				 * setSecondaryTitle("With Existing Housing Loan");
				 * setTitle(String.format("%s %s", title, "With Existing Housing Loan")); }else{
				 * setTitle(title); }
				 * 
				 * setSecondaryTitle(null);
				 */
			}

			String blink_status = "";
			blink_status = _CARD.getCARD_Blinking_Status(entity_id, proj_id, pbl_id, seq_no);

			if(blink_status != null || blink_status.trim().equals("") == false){
				setSecondaryTitle("");

				if(timerStatus != null) {
					stopTimerStatus();
				}
				if (blink_status.trim().equals("") == false) {
					System.out.println("Dumaan dito sa Blinking");
					setSecondaryTitle(blink_status);

					txtHousingLoan.setText(generateTAG(""));
				}
				startTimerStatus();
			}

			if(_CARD.withHousingLoan(entity_id, proj_id, pbl_id, seq_no)) {
				txtHousingLoan.setText(generateTAG("With Existing Housing Loan"));
			}else {
				txtHousingLoan.setText(generateTAG(""));
			}

			txtPmtStage.setText((String) clientDetails[34]);
			String pmt_status = (String) clientDetails[43];
			System.out.printf("Display Payment Status: %s%n", pmt_status);

			txtPmtStatusRemarks.setText(String.format("[ %s ]", pmt_status));

			if (pmt_status.equals("PAST DUE") || pmt_status.equals("CANCELLED")) {
				txtPmtStatusRemarks.setForeground(Color.RED);
			}else{
				txtPmtStatusRemarks.setForeground(txtStatusName.getForeground());
			}

			txtSellingAgentID.setText((String) clientDetails[30]);
			txtSellingAgentName.setText(String.format("[ %s ] [ %s ] [ %s ]", (String) clientDetails[31], (String) clientDetails[32], (String) clientDetails[33]));
			txtSellingAgentName.setCaretPosition(0);
			txtMTPPClass.setText((String) clientDetails [74]); //ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
			txtGSP.setValue((BigDecimal) clientDetails[15]);
			txtDiscount.setValue((BigDecimal) clientDetails[16]);

			try {
				txtNSP.setValue(((BigDecimal) clientDetails[15]).subtract((BigDecimal) clientDetails[16]));
			} catch (Exception e) {
				txtNSP.setValue((BigDecimal) clientDetails[15]);
			}

			txtDP.setValue((BigDecimal) clientDetails[17]);

			/*try {
				txtLoanableAmount.setValue(((BigDecimal) clientDetails[18]).subtract((BigDecimal) clientDetails[16]));
			} catch (Exception e) {*/
			txtLoanableAmount.setValue((BigDecimal) clientDetails[18]);
			//}

			try {
				/*if((BigDecimal) clientDetails[16] != null){
					txtBalance.setValue(((BigDecimal) clientDetails[19]).subtract((BigDecimal) clientDetails[16]));
				}else{*/
				txtBalance.setValue(((BigDecimal) clientDetails[19]));
				txtMiscFeeBalance.setValue((BigDecimal) clientDetails[63]);
				txtMiscFee15Pct.setValue((BigDecimal) clientDetails[64]);
				txtTotalCashOutlay.setValue((BigDecimal) clientDetails[65]);
				txtBankTerm.setValue(clientDetails[66]);
				//}
			} catch (Exception e) {
				txtLoanableAmount.setValue((BigDecimal) clientDetails[18]);
			}

			txtDPAmount.setValue((BigDecimal) clientDetails[35]);
			txtDPRate.setValue((BigDecimal) clientDetails[20]);
			txtDPTerm.setValue((Integer) clientDetails[21]);
			txtMAAmount.setValue((BigDecimal) clientDetails[22]);
			txtMARate.setValue((BigDecimal) clientDetails[23]);
			txtMATerm.setValue((Integer) clientDetails[24]);
			txtFactorRate.setText((String) clientDetails[29]);
			txtIntRate.setValue((BigDecimal) clientDetails[36]);
			txtVAT.setValue((BigDecimal) clientDetails[70]);
			
			System.out.printf("Display MTPP Classification: %s%n", txtMTPPClass.getText());

			System.out.printf("Display value of VAT: %s%n", clientDetails[70]);
			System.out.printf("Value of Compare: %s%n", ((BigDecimal) clientDetails[70]) == new BigDecimal("0.00"));
			
			BigDecimal vat = (BigDecimal) clientDetails[70];
			//if(UserInfo.ADMIN) {
				if((BigDecimal)clientDetails[70] == null || vat.compareTo(new BigDecimal("0.00")) == 0) {
					splitClientDetailsEast.getLeftComponent().setVisible(true);
					splitClientDetailsEast.getRightComponent().setVisible(false);
					System.out.println("Left Side no VAT");
					
				}else {
					System.out.println("Right Side w/ VAT");
					splitClientDetailsEast.getLeftComponent().setVisible(false);
					splitClientDetailsEast.getRightComponent().setVisible(true);
					
					txtGSPVAT.setValue((BigDecimal) clientDetails[15]);
					txtDiscountVAT.setValue((BigDecimal) clientDetails[16]);
					txtNSP_GOV.setValue((BigDecimal) clientDetails[72]);//previous 15
					txtVATEast.setValue((BigDecimal)clientDetails[70]);
					txtNSP_NOV.setValue(((BigDecimal) clientDetails[73]));
					txtDPVat.setValue((BigDecimal) clientDetails[17]);
					txtOSBalance_VAT.setValue(((BigDecimal) clientDetails[19]));
					txtLoanableAmount_VAT.setValue((BigDecimal) clientDetails[18]);
					
					txtDPAmt_VAT.setValue((BigDecimal) clientDetails[35]);
					txtDPRate_VAT.setValue((BigDecimal) clientDetails[20]);
					txtDP_Term_VAT.setValue((Integer) clientDetails[21]);
					txtMAAmt_VAT.setValue((BigDecimal) clientDetails[22]);
					txtMARate_VAT.setValue((BigDecimal) clientDetails[23]);
					txtMA_Term_VAT.setValue((Integer) clientDetails[24]);
					txtFactor_VAT.setText((String) clientDetails[29]);
					txtInt_RateVAT.setValue((BigDecimal) clientDetails[36]);
					txtMTPPClass_VAT.setText((String) clientDetails [74]); //ADDED BY MONQUE BASED ON DCRF#2236 DTD 10-20-2022
					
				}
			//}

			/*	Added by Mann2x; Date Added: October 18, 2017; As requested by Mr. Liao;	*/
			try {
				dteBIRDate.setDate(FncGlobal.GetDate("select boi::date \n" + 
						"from mf_sub_project x \n" + 
						"inner join mf_unit_info y on y.proj_id = x.proj_id and y.phase = x.phase \n" + 
						"where y.proj_id = '"+txtProjectID.getText()+"' and y.pbl_id = '"+txtPblID.getText()+"' AND x.status_id = 'A'"));//ADDED STATUS ID BY LESTER DCRF 2718				
			} catch (Exception ex) {

			}

			/*	Added by Mann2x; Date Added: October 18, 2017; As requested by Mr. Liao;	*/
			try {
				dteBIRExpDate.setDate(FncGlobal.GetDate("select ith_expiration::date \n" + 
						"from mf_sub_project x \n" + 
						"inner join mf_unit_info y on y.proj_id = x.proj_id and y.phase = x.phase \n" + 
						"where y.proj_id = '"+txtProjectID.getText()+"' and y.pbl_id = '"+txtPblID.getText()+"' AND x.status_id = 'A'"));//ADDED STATUS ID BY LESTER DCRF 2718				
			} catch (Exception ex) {

			}

			/*	Added by Mann2x; Date Added: March 15, 2018, DCRF# 341; 	*/
			try {
				//txtHLID.setText(FncGlobal.GetString("select hlid_no from rf_entity_id_no where entity_id = '"+lookupClient.getValue().toString()+"'"));
				txtHLID.setText(FncGlobal.GetString("SELECT hlid_no from rf_hlid_no where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and status_id = 'A'"));		} catch (Exception ex) {

			}

			chkShowRefund.setSelected(false);

			//dteWithNTC.setDate((Date) clientDetails[41]);

			NOA_RELEASED = _CARD.isNOAReleased(entity_id, proj_id, pbl_id, seq_no);
			
			
		}
		
		String cwt = _CARD.getCWT_TaxExempt(entity_id, proj_id, pbl_id, seq_no);
		//txtCWT_TaxExempt_VAT.setText(cwt);
		txtCWT_Exempt.setText(cwt);
		
		displayBIR(entity_id); 
	}

	//Done
	private void displayTabs(String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		_CARD.displaySchedule(modelSchedule, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollSchedule.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule.getRowCount())));
		//tblSchedule.packColumns("Part ID", "Particular", "Schedule Date", "Amount", "Processing Fee", "MRI", "Fire", "MAF", "Interest", "Principal", "VAT", "Balance", "Interest Rate", "Settled");
		validateSchedule();
		tblSchedule.packAll(10);		

		_CARD.displayDues(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, toPrint);
		scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblDues.getRowCount())));
		tblDues.packAll();

		_CARD.displayLedger(modelLedger, entity_id, proj_id, pbl_id, seq_no, toPrint);
		//_CARD.displayLedgerRefund(modelLedger, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollLedger.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLedger.getRowCount())));
		tblLedger.packAll();

		_CARD.displayPayments(modelPayments, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollPayments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPayments.getRowCount())));
		tblPayments.packColumns("Trans. Date", "Actual Date", "Particulars", "Type", "**", "Amount", "%", "Bank", "Branch", "Account #", "Check #", "Check Date", "Check Status", "Deposit Date", "OR No.", "OR Date", "AR No.", "Remarks", "OR Pending", "Branch", "Rec ID");
		validatePayments();

		modelCreditOfPayment.clear();
		scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));

		_CARD.displayReservationDocuments(modelReservationDocuments, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollReservationDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReservationDocuments.getRowCount())));
		tblReservationDocuments.packAll();

		_CARD.displayPrintedDocuments(modelPrintedDocuments, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollPrintedDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPrintedDocuments.getRowCount())));
		tblPrintedDocuments.packAll();

		_CARD.displayNotices(modelNotices, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
		tblNotices.packAll();

		_CARD.displayAccountStatus(modelAccountStatusHistory, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollAccountStatusHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccountStatusHistory.getRowCount())));
		tblAccountStatusHistory.packColumns("ID", "Status", "Trans. Date", "Actual Date", "Gross SP", "Discount", "VAT", "Net SP", "Request No.", "Stat");

		_CARD.displayClientReqHistory(modelClientReqHistory, entity_id, proj_id, pbl_id, seq_no, toPrint);
		scrollClientRequestHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblClientRequestHistory.getRowCount())));
		tblClientRequestHistory.packColumns("ID", "Status", "Trans. Date", "Actual Date", "Gross SP", "Discount", "VAT", "Net SP", "Request No", "Stat");
	}

	//Done
	private void displayTabDetails(String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		int selected_tab = tabCenter.getSelectedIndex();

		if(selected_tab == 0){
			tabSchedule.setSelectedIndex(3);
			_CARD.displaySchedule(modelSchedule3, entity_id, proj_id, pbl_id, seq_no, toPrint);
			//scrollSchedule3.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule.getRowCount())));
			//tblSchedule.packColumns("Part ID", "Particular", "Schedule Date", "Amount", "Processing Fee", "MRI", "Fire", "MAF", "Interest", "Principal", "VAT", "Balance", "Interest Rate", "Settled");
			tblSchedule3.packAll();

			/**05-19-20 : Del-Display ECQ Schedule*/
			_CARD.displayScheduleECQ(modelSchedule2, entity_id, proj_id, pbl_id, seq_no, toPrint);
			//scrollSchedule2.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule2.getRowCount())));
			//tblSchedule2.packColumns("Part ID", "Particular", "Schedule Date", "Amount", "Processing Fee", "MRI", "Fire", "MAF", "Interest", "Principal", "VAT", "Balance", "Interest Rate", "Settled");
			//validateSchedule();
			tblSchedule2.packAll(10);			

			/**05-19-20 : Del-Display Combined Schedule*/
			_CARD.displayScheduleCombined(modelSchedule, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollSchedule.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule.getRowCount())));
			//tblSchedule2.packColumns("Part ID", "Particular", "Schedule Date", "Amount", "Processing Fee", "MRI", "Fire", "MAF", "Interest", "Principal", "VAT", "Balance", "Interest Rate", "Settled");
			//validateSchedule();
			tblSchedule.packAll(10);

			/**05-19-20 : Del-Display Paid Schedule prior to ECQ*/
			_CARD.displaySchedulePaidBeforeECQ(modelSchedule4, entity_id, proj_id, pbl_id, seq_no, toPrint);
			//scrollSchedule.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSchedule.getRowCount())));
			//tblSchedule2.packColumns("Part ID", "Particular", "Schedule Date", "Amount", "Processing Fee", "MRI", "Fire", "MAF", "Interest", "Principal", "VAT", "Balance", "Interest Rate", "Settled");
			//validateSchedule();
			tblSchedule4.packAll(10);



			switch (btnOptions.getText()) {
			case "Regular":
				_CARD.displayDuesRegular(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true, dateExclude.getDate(), false);
				break;

			case "Moratorium":
				_CARD.displayDuesMoratorium(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, true);                                                                                                                                                                
				break;

			default:
				_CARD.displayDuesRegular(modelDues, entity_id, proj_id, pbl_id, seq_no, dateDues.getDate(), modelDuesTotal, toPrint, dateExclude.getDate(), chkExcludeLastSched.isSelected());
				break;
			}

			scrollDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblDues.getRowCount())));
			tblDues.setHorizontalScrollEnabled(true);
			tblDues.packAll();
			tblDues.setHorizontalScrollEnabled(false);
			String exclude_date_string = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
			chkExcludeLastSched.setSelected(false);
			dateExclude.setDate(null);

			if(modelDues.getRowCount() != 0){
				chkExcludeLastSched.setEnabled(true);
				Date exclude_date = (Date) modelDues.getValueAt(modelDues.getRowCount() - 1, 2);
				exclude_date_string = formatter.format(exclude_date);
				dateExclude.setDate(exclude_date);

				txtAmtToUpdate.setValue(totalDues());
			}else{
				chkExcludeLastSched.setEnabled(false);
				txtAmtToUpdate.setValue(new BigDecimal("0.00"));
			}


			_CARD.displayLedger(modelLedger, entity_id, proj_id, pbl_id, seq_no, toPrint);
			//_CARD.displayLedgerRefund(modelLedger, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollLedger.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLedger.getRowCount())));
			tblLedger.setHorizontalScrollEnabled(true);
			tblLedger.packAll();
			tblLedger.setHorizontalScrollEnabled(false);

		}

		validateSchedule();

		if(selected_tab == 1){
			_CARD.displayPayments(modelPayments, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollPayments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPayments.getRowCount())));
			tblPayments.packColumns("Trans. Date", "Actual Date", "Particulars", "Type", "**", "Amount", "%", "Bank", "Branch", "Account #", "Check #", "Check Date", "Check Status", "Deposit Date", "OR No.", "OR Date", "AR No.", "Remarks", "OR Pending", "Branch", "Rec ID");
			validatePayments();

			modelCreditOfPayment.clear();
			scrollCreditOfPayment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditOfPayment.getRowCount())));

			modelCheckHistory.clear();
			scrollCheckHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCheckHistory.getRowCount())));
		}

		if(selected_tab == 2){
			_CARD.displayReservationDocuments(modelReservationDocuments, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollReservationDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReservationDocuments.getRowCount())));
			tblReservationDocuments.packAll();

			if(tblReservationDocuments.getSelectedRowCount() == 0){
				modelDocsHistory.clear();
				scrollDocsHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
				rowHeaderDocsHistory.setModel(new DefaultListModel());
			}

			_CARD.displayPrintedDocuments(modelPrintedDocuments, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollPrintedDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPrintedDocuments.getRowCount())));
			tblPrintedDocuments.packAll();

			_CARD.displayDocFindings(modelDocFindings, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollDocFindings.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocFindings.getRowCount())));
			tblDocFindings.packAll();
			tblDocFindings.getColumn(1).setPreferredWidth(200);

			modelReqDocPurpose.clear();
			scrollReqDocPurpose.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderReqDocPurpose.setModel(new DefaultListModel());

			modelRequestedDocs.clear();
			scrollRequestedDocs.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderRequestedDocs.setModel(new DefaultListModel());

			modelReqDocsStatus.clear();
			scrollReqDocStatus.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderReqDocStatus.setModel(new DefaultListModel());

			_CARD.displayReqDocDetails(modelReqDocDetails, entity_id, proj_id, pbl_id, seq_no, true);
			scrollReqDocDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReqDocDetails.getRowCount())));
			tblReqDocDetails.packAll();

		}

		if(selected_tab == 3){
			_CARD.displayNotices(modelNotices, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
			tblNotices.packAll();
		}

		if(selected_tab == 4){
			_CARD.displayAccountStatus(modelAccountStatusHistory, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollAccountStatusHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccountStatusHistory.getRowCount())));
			tblAccountStatusHistory.packColumns("ID", "Status", "Trans. Date", "Actual Date", "Gross SP", "Discount", "VAT", "Net SP", "Request No.", "Stat");
			//tblClientRequestHistory.packColumns("Status", "Trans. Date", "Actual Date", "Gross SP", "Discount", "VAT", "Net SP", "Request No", "Stat");
			//tblClientRequestHistory.hideColumns("ID");

			/*modelRefundOfPayment.clear();
			scrollRefundOfPayments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRefundOfPayments.getRowCount())));
			 */

			modelClientReqHistory.clear();
			scrollClientRequestHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblClientRequestHistory.getRowCount())));

			lblReqFieldOld.setText("");
			lblReqFieldNew.setText("");
			pnlReqComponent.removeAll();

			_CARD.displayClientReqHistory(modelClientReqHistory, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollClientRequestHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblClientRequestHistory.getRowCount())));
			tblClientRequestHistory.packAll();

			tblClientRequestHistory.clearSelection();
			splitRequestDetails.getRightComponent().setVisible(false);

			_CARD.displayLOG_Details(modelLOG_Details, entity_id, proj_id, pbl_id, seq_no, true);
			scrollLOGDetails.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblLOGDetails.getRowCount())));
			tblLOGDetails.packAll();

			_CARD.displayTOverMoveIn(modelTOverMoveIn, entity_id, proj_id, pbl_id, seq_no, true);
			cmbTOMSLot.setModel(new DefaultComboBoxModel(getLots(entity_id, proj_id, pbl_id, Integer.toString(seq_no))));//**ADDED BY JED VICEDO 2019-07-17 : TO SELECT 2ND LOTS**//
			scrollTOverMoveIn.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTOverMoveIn.getRowCount())));
			tblTOverMoveIn.packAll();

			cmbPreviewLot.setModel(new DefaultComboBoxModel(getLots(entity_id, proj_id, pbl_id, Integer.toString(seq_no))));

			_CARD.displayUnitStatus(modelUnitStatus, entity_id, proj_id, pbl_id, seq_no, false ,true);
			scrollUnitStatus.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblUnitStatus.getRowCount())));
			tblUnitStatus.packAll();

		}

		if(selected_tab == 5){

			/*txtNTPNo.setText((String) clientDetails[45]);
			txtContractor.setText(String.format("%s / %s", clientDetails[46], clientDetails[62]));
			txtDate1.setDate((Date) clientDetails[47]);
			txtRemarks.setText((String) clientDetails[48]);
			txtContractNo.setText((String) clientDetails[49]);
			txtWorkDesc.setText((String) clientDetails[50]);
			txtDuration.setText(String.format("%s - %s", clientDetails[51], clientDetails[52]));*/

			//_CARD.displayAccomplishment(modelAccomplishment, pbl_id);


			_CARD.displayNTP_History(modelNTP_History, entity_id, proj_id, pbl_id, seq_no);
			scrollNTP_History.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNTP_History.getRowCount())));
			tblNTP_History.packAll();

			modelAccomplishment.clear();
			scrollAccomplishment.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccomplishment.getRowCount())));

			modelAccomplishment2.clear();
			scrollAccomplishment2.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccomplishment2.getRowCount())));
		}

		if(selected_tab == 6){ //ADDED BY JESSA HERRERA 2017-05-15
			/*txtDocNo.setText((String) clientDetails[55]);
			txtMDocNo.setText((String) clientDetails[56]);
			txtDateCreated.setText(String.format("%s", clientDetails[57]));
			txtFacility.setText((String) clientDetails[58]);
			txtSerialNo.setText((String) clientDetails[59]);
			txtBookNo.setText((String) clientDetails[60]);
			txtPageNo.setText((String) clientDetails[61]);
			_CARD.displayDocStatus(modelTCT, pbl_id);
			scrollTCT.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT.getRowCount())));
			tblTCT.packAll();
			tblTCT.getColumn(1).setPreferredWidth(200);*/

			//------modified by JED 9-12-18 : for display of TCTTax for the other lots (additional String other_pbl_id)-----//
			modelTCTTax.clear();
			modelTCTTax_Loc.clear();

			String other_pbl_id = checkOtherLots(entity_id, pbl_id, proj_id, seq_no);

			System.out.printf("The value of other pbl id is: %s", other_pbl_id );
			
			server = checkserver(entity_id, proj_id, pbl_id, seq_no);
			System.out.println("server_id: "+server);

			_CARD.displayTctTaxdec(modelTctTaxdec, pbl_id, other_pbl_id,proj_id );
			scrollTctTaxdec.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTctTaxdec.getRowCount())));
			tblTctTaxdec.packAll();
			tblTctTaxdec.getColumn(1).setPreferredWidth(200);
		}

		if(selected_tab == 8){

			_CARD.displayMRI(modelMRI, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollMRI.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMRI.getRowCount())));
			tblMRI.packAll();

			_CARD.displayFire(modelFire, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollFire.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFire.getRowCount())));
			tblFire.packAll();

			_CARD.displayCARD_Bank_Accts(modelBankAccts, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollBankAccounts.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBankAccounts.getRowCount())));
			tblBankAccounts.packAll();

			_CARD.displayCARD_Connections(modelConnections, entity_id);
			scrollConnections.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblConnections.getRowCount())));
			tblConnections.packAll();

			_CARD.displayTCostDetails(modelTCT, modelTCTTotal, entity_id, proj_id, pbl_id, seq_no);
			cmblot1.setModel(new DefaultComboBoxModel(getLots(entity_id, proj_id, pbl_id, Integer.toString(seq_no))));//**ADDED BY JED VICEDO 2019-04-16 : TO SELECT 2ND LOTS**//
			scrollTCT.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTCT.getRowCount())));
			tblTCT.packAll();

			_CARD.displayPCostDetails(modelPCD, modelPCDTotal, entity_id, proj_id, pbl_id, seq_no);
			cmblot.setModel(new DefaultComboBoxModel(getLots(entity_id, proj_id, pbl_id, Integer.toString(seq_no))));//**ADDED BY JED VICEDO 2019-04-16 : TO SELECT 2ND LOTS**//
			{
				//ADDED BY MONIQUE DTD 05-15-24 REFER TO DCRF#2950
				if(_CARD.isWithOffsetRPT(entity_id, proj_id, pbl_id, seq_no)) {
					lblRPTOffset.setText(String.format("<html><b>%s</b></html>", _CARD.RPTOffset(entity_id, proj_id, pbl_id, seq_no))); 
				} else {
					lblRPTOffset.setText("");
				}
				
				//ADDED BY MONIQUE DTD 05-15-24 REFER TO DCRF#2966
				if(_CARD.isWithRPTCredit(entity_id, proj_id, pbl_id, seq_no)) {
					
					String[] result = _CARD.RPTCredit(entity_id, proj_id, pbl_id, seq_no);

					// Check if result is not null (to avoid NullPointerException)
					if (result != null) {
					    // Total amount credited
					    String totalAmtRPTCredited = result[0];
					    // Remarks
					    rptCreditRemarks = result[1];
					    
						lblRPTCredit.setText(String.format("<html><b> CD-EXCESS %s CREDIT TO: </b></html>", totalAmtRPTCredited));
						btnCreditedTo.setVisible(true);

//					    System.out.println("Total amount credited: " + totalAmtRPTCredited);
//					    System.out.println("Remarks: " + remarks);
					} else {
					    System.out.println("No result found.");
					}
					
				} else {
					lblRPTCredit.setText("");
					btnCreditedTo.setVisible(false);
				}
			}
			scrollPCD.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPCD.getRowCount())));
			tblPCD.packAll();

			_CARD.display_hrdetails(modelHR , entity_id, pbl_id, proj_id, seq_no);

			TCostComputation.displayTCost(entity_id, proj_id, pbl_id, seq_no, false);
			TCostComputation.displayClient(txtClientName.getText(), txtPblDescription.getText(), txtModelName.getText(), txtNSP.getText(), (String) clientDetails[37]);

			//PropertyManagement.chkPreviewOtherLot.setSelected(false);
			PropertyManagement.cmbPreviewLot.setModel(new DefaultComboBoxModel(getLots(entity_id, proj_id, pbl_id, Integer.toString(seq_no))));

			PropertyManagement.displayWaterMeterReading(entity_id, proj_id, pbl_id, Integer.toString(seq_no), false);
			PropertyManagement.displayMonthlySOA(entity_id, proj_id, pbl_id, Integer.toString(seq_no), false);
			PropertyManagement.displayUCARD(entity_id, proj_id, pbl_id, Integer.toString(seq_no), false);
			PropertyManagement.displaySubdivisionDues(entity_id, proj_id, pbl_id, Integer.toString(seq_no));
			PropertyManagement.displayFacilities(entity_id, proj_id, pbl_id, Integer.toString(seq_no));
			PropertyManagement.displayElectricity(entity_id, proj_id, pbl_id, Integer.toString(seq_no));

		}

		if(selected_tab == 7){
			unitInfoStatus.txtLotArea.setText((String) clientDetails[37]);
			unitInfoStatus.txtFloorArea.setText((String) clientDetails[38]);
			unitInfoStatus.txtPreassignedColor.setText((String) clientDetails[42]);
			unitInfoStatus.dteHouseConstructed.setDate((Date) clientDetails[68]);
			unitInfoStatus.dteTurnOver.setDate((Date) clientDetails[40]);
			unitInfoStatus.dteWithNTC.setDate((Date) clientDetails[41]);
			unitInfoStatus.dteMovedIn.setDate((Date) clientDetails[69]);
			//unitInfoStatus.dteMovedOut.setDate((Date) clientDetails[69]);
			unitInfoStatus.txtTCT.setText((String) clientDetails[71]);
			unitInfoStatus.txtBOIStatus.setText(FncGlobal.GetString("select (case when sp_is_boi_exempt('"+lookupClient.getValue()+"', \n" +
					"'"+txtProjectID.getText()+"', '"+txtPblID.getText()+"', "+FncGlobal.GetString("select seq_no::varchar from rf_sold_unit \n" +
							"where entity_id = '"+lookupClient.getValue()+"' and projcode = '"+txtProjectID.getText()+"' and pbl_id = '"+txtPblID.getText()+"'")+") = true then 'BOI Exempt' else 'Not BOI Exempt' end)"));
			unitInfoStatus.txtTechDesc.setText((String) clientDetails[54]);

			/*
			txtLotArea.setText((String) clientDetails[37]);
			txtFloorArea.setText((String) clientDetails[38]);
			txtHouseConstructed.setDate((Date) clientDetails[68]);
			txtTurnOver.setDate((Date) clientDetails[40]);
			txtWithNTC.setDate((Date) clientDetails[41]);
			txtPreassignedColor.setText((String) clientDetails[42]);
			txtMovedIn.setDate((Date) clientDetails[69]);
			 */

			if(_CARD.withNTC(entity_id, proj_id, pbl_id, seq_no)){

			} else {

			}

		}

		if(selected_tab == 10){
			_CARD.displayFollowUp(modelFollowup, entity_id, proj_id, pbl_id, seq_no, toPrint);
			scrollFollowUp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFollowUp.getRowCount())));
			tblFollowUp.packAll();
			tblFollowUp.getColumn(1).setPreferredWidth(500);
		}

		if(selected_tab == 11){
			_CARD_REQUESTS.displayTaggingOfCsv(entity_id, proj_id, pbl_id, seq_no);
		}

		btnOptions.setEnabled(!_CARD.afterECQ(entity_id, proj_id, pbl_id, seq_no) && !entity_id.equals("5935348691"));
	}

	//Done
	private static String checkOtherLots (String entity_id, String pbl_id, String proj_id, Integer seq_no){

		String other_pbl_id = "";
		String SQL = 
				"SELECT oth_pbl_id FROM hs_sold_other_lots \n" + 
						"where entity_id = '"+entity_id+"' \n" + 
						"and proj_id = '"+proj_id+"' \n" + 
						"and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no = '"+seq_no+"' \n " +
						"and status_id = 'A' ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {	
			} else {
				other_pbl_id = (String) db.getResult()[0][0].toString();
			}
		} else {
			other_pbl_id = "0";
		}

		return other_pbl_id;

	}
	
	private static String checkserver(String entity_id, String proj_id,  String pbl_id, Integer seq_no) {
		String server_id = null;
		
		String sql = "select server_id from rf_sold_unit \n" + 
				"where entity_id = '"+entity_id+"' \n" + 
				"and projcode = '"+proj_id+"'\n" + 
				"and pbl_id = '"+pbl_id+"' \n" + 
				"and seq_no = "+seq_no+" \n" + 
				"and status_id = 'A' \n" + 
				"order by date_created desc limit 1";
		System.out.println("checkserver: "+ sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				server_id = null;
			}else {
				server_id = (String) db.getResult()[0][0];
			}
		}else {
			server_id = null;
		}
		return server_id;
	}

	//Done
	private Object[] getLots(String entity, String proj_id, String pbl_id, String seq_no) {//ARRAYLIST FOR THE CIVIL STATUS
		ArrayList<Object> lots = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		String SQL = "SELECT FORMAT('LOT %s', b.lot)\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_unit_info b on b.proj_id = a.projcode and b.pbl_id = a.pbl_id \n" + 
				"WHERE a.entity_id = '"+entity+"'\n" + 
				"AND a.projcode = '"+proj_id+"'\n" + 
				"AND a.pbl_id = '"+pbl_id+"'\n" + 
				"AND a.seq_no = "+seq_no+"\n" + 
				"UNION \n" + 
				"SELECT FORMAT('LOT %s', b.lot)\n" + 
				"FROM hs_sold_other_lots a\n" + 
				"LEFT JOIN mf_unit_info b on b.proj_id = a.proj_id and b.pbl_id = a.oth_pbl_id \n" + 
				"WHERE a.entity_id = '"+entity+"'\n" + 
				"AND a.proj_id = '"+proj_id+"'\n" + 
				"AND a.pbl_id = '"+pbl_id+"'\n" + 
				"AND a.seq_no = "+seq_no+" ";
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				lots.add(db.getResult()[x][0]);
			}
		}
		return lots.toArray();
	}

	//Done
	private void displayRequestDetails(String request_no, String byrstatus_id){
		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_client_request_details('"+request_no+"', true)";
		db.select(SQL);

		FncSystem.out("Display SQL for Request Details", SQL);

		pnlReqComponent.removeAll();

		if(db.isNotNull()){

			String old_entity_name = (String) db.getResult()[0][0];
			String old_civil_status = (String) db.getResult()[0][1];
			String old_buyer_type = (String) db.getResult()[0][2];
			String old_agent = (String) db.getResult()[0][3];
			String old_project = (String) db.getResult()[0][4];
			String old_unit = (String) db.getResult()[0][5];
			BigDecimal old_gsp = (BigDecimal) db.getResult()[0][6];
			String old_pmt_scheme = (String) db.getResult()[0][7];
			Integer old_dp_term = (Integer) db.getResult()[0][10];
			Integer old_ma_term = (Integer) db.getResult()[0][11];
			BigDecimal old_dp_equity = (BigDecimal) db.getResult()[0][12];
			BigDecimal old_loan_amt = (BigDecimal) db.getResult()[0][15];
			String old_house_model = (String) db.getResult()[0][16];
			BigDecimal old_lot_area = (BigDecimal) db.getResult()[0][17];
			BigDecimal old_floor_area = (BigDecimal) db.getResult()[0][18];
			BigDecimal old_disc_amt = (BigDecimal) db.getResult()[0][19];
			String new_entity_name = (String) db.getResult()[0][20];
			String new_civil_status = (String) db.getResult()[0][21];
			String new_buyer_type = (String) db.getResult()[0][22];
			String new_agent = (String) db.getResult()[0][23];
			String new_project = (String) db.getResult()[0][24];
			String new_unit = (String) db.getResult()[0][25];
			String new_gsp = (String) db.getResult()[0][26];
			String new_pmt_scheme = (String) db.getResult()[0][27];
			String new_dp_term = (String) db.getResult()[0][30];
			String new_ma_term = (String) db.getResult()[0][31];
			String new_dp_amt = (String) db.getResult()[0][32];
			String new_loan_amt = (String) db.getResult()[0][35];
			String new_house_model = (String) db.getResult()[0][36];
			String new_lot_area = (String) db.getResult()[0][37];
			String new_floor_area = (String) db.getResult()[0][38];
			String new_disc_amt = (String) db.getResult()[0][39];
			//splitRequestDetails.setDividerLocation(1);

			splitRequestDetails.getRightComponent().setVisible(false);
			splitRequestDetails.getLeftComponent().setVisible(true);

			if(tblClientRequestHistory.getSelectedRows().length == 1){
				//splitRequestDetails.getRightComponent().setVisible(false);

				if(byrstatus_id.equals("16")){//TRANSFER REAPPLY
					lblReqFieldOld.setText("Old Unit");
					lblReqFieldNew.setText("New Unit");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_unit);
					txtReqFieldNew.setText(new_unit);
				}
				if(byrstatus_id.equals("03")){//TRANSFER OF UNIT
					lblReqFieldOld.setText("Old Unit");
					lblReqFieldNew.setText("New Unit");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_unit);
					txtReqFieldNew.setText(new_unit);
				}
				if(byrstatus_id.equals("09")){//CHANGE OF PRINCIPAL APPLICANT
					lblReqFieldOld.setText("Old Principal Applicant");
					lblReqFieldNew.setText("New Principal Applicant");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_entity_name);
					txtReqFieldNew.setText(new_entity_name);
				}
				if(byrstatus_id.equals("115")){//CHANGE OF CIVIL STATUS
					lblReqFieldOld.setText("Old Civil Status");
					lblReqFieldNew.setText("New Civil Status");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_civil_status);
					txtReqFieldNew.setText(new_civil_status);
				}
				if(byrstatus_id.equals("08")){//REAPPLICATION
					lblReqFieldOld.setText("Old Unit");
					lblReqFieldNew.setText("New Unit");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_unit);
					txtReqFieldNew.setText(new_unit);
				}
				if(byrstatus_id.equals("24")){//CHANGE HOUSE MODEL
					lblReqFieldOld.setText("Old House Model");
					lblReqFieldNew.setText("New House Model");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_house_model);
					txtReqFieldNew.setText(new_house_model);
				}
				if(byrstatus_id.equals("120")){//CHANGE DP TERM
					lblReqFieldOld.setText("Old DP Term");
					lblReqFieldNew.setText("New Dp Term");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_dp_term.toString());
					txtReqFieldNew.setText(new_dp_term.toString());
				}
				if(byrstatus_id.equals("14")){//CHANGE PMT SCHEME
					lblReqFieldOld.setText("Old Pmt Scheme");
					lblReqFieldNew.setText("New Pmt Scheme");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_pmt_scheme);
					txtReqFieldNew.setText(new_pmt_scheme);
				}
				if(byrstatus_id.equals("119")){//CHANGE OF LOANABLE AMT
					lblReqFieldOld.setText("Old Loanable Amt");
					lblReqFieldNew.setText("New Loanable Amt");
					pnlReqComponent.add(txtReqAmtOld);
					pnlReqComponent.add(txtReqAmtNew);
					txtReqAmtOld.setValue(old_loan_amt);
					txtReqAmtNew.setValue(new_loan_amt);
					//splitRequestDetails.setDividerLocation(1);
					splitRequestDetails.setResizeWeight(1);
				}
				if(byrstatus_id.equals("121")){//CHANGE MA TERM
					lblReqFieldOld.setText("Old MA Term");
					lblReqFieldNew.setText("New MA Term");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_ma_term.toString());
					txtReqFieldNew.setText(new_ma_term.toString());
				}
				if(byrstatus_id.equals("12")){//CHANGE OF CLIENT CLASS
					lblReqFieldOld.setText("Old Buyer Type");
					lblReqFieldNew.setText("New Buyer Type");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_buyer_type);
					txtReqFieldNew.setText(new_buyer_type);
				}
				if(byrstatus_id.equals("15")){//RECONSIDERATION OF DISCOUNT OF PROMO
					lblReqFieldOld.setText("Old Disc Amt");
					lblReqFieldNew.setText("New Disc Amt");
					pnlReqComponent.add(txtReqAmtOld);
					pnlReqComponent.add(txtReqAmtNew);
					txtReqAmtOld.setValue(old_disc_amt);
					txtReqAmtNew.setValue(new_disc_amt);
				}
				if(byrstatus_id.equals("11")){//CHANGE OF SELLING PERSON
					lblReqFieldOld.setText("Old Selling Person");
					lblReqFieldNew.setText("New Selling Person");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_agent);
					txtReqFieldNew.setText(new_agent);
				}
				if(byrstatus_id.equals("13")){//CORRECTION OF NAME
					lblReqFieldOld.setText("Old Name");
					lblReqFieldNew.setText("New Name");
					pnlReqComponent.add(txtReqFieldOld);
					pnlReqComponent.add(txtReqFieldNew);
					txtReqFieldOld.setText(old_entity_name);
					txtReqFieldNew.setText(new_entity_name);
				}


				if(byrstatus_id.equals("Refund of Payment")){
					splitRequestDetails.getLeftComponent().setVisible(false);
					splitRequestDetails.getRightComponent().setVisible(true);
				}

				if(byrstatus_id.equals("Credit of Payment")){
					splitRequestDetails.getLeftComponent().setVisible(false);
					splitRequestDetails.getRightComponent().setVisible(true);
				}

				if(byrstatus_id.equals("Waive Penalty")){
					splitRequestDetails.getLeftComponent().setVisible(false);
					splitRequestDetails.getRightComponent().setVisible(true);
				}

				if(byrstatus_id.equals("118")){
					splitRequestDetails.getLeftComponent().setVisible(false);
					splitRequestDetails.getRightComponent().setVisible(true);
				}

			}else{
				lblReqFieldOld.setText("");
				lblReqFieldNew.setText("");
				pnlReqComponent.removeAll();

				/*pnlReqOldComponent.removeAll();
				pnlReqNewComponent.removeAll();*/
			}

			if(tblClientRequestHistory.getRowCount() == 0){
				lblReqFieldOld.setText("");
				lblReqFieldNew.setText("");
				pnlReqComponent.removeAll();
			}

			if(byrstatus_id.equals("Credit of Payment")){
				System.out.println("Credit of Payment");
				//splitRequestDetails.setDividerLocation(0);
			}

			if(byrstatus_id.equals("Refund of Payment")){
				//splitRequestDetails.setDividerLocation(0);
				System.out.println("Refund Payment Details");
			}

			if(byrstatus_id.equals("Waive Penalty")){
				//splitRequestDetails.setDividerLocation(0);
				System.out.println("Waive Penalty Details");
			}

		}
	}

	//Done
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
			if(buyer_type_id.equals("05")){
				tabCenter.setEnabledAt(9, false);
				System.out.println("Not Enabled");
			}else{
				tabCenter.setEnabledAt(9, true);
				System.out.println("Enabled");
			}
			//}
		}

		if(txtVAT.getValued() != null){
			tblSchedule.showColumns("VAT");
		}else{
			tblSchedule.hideColumns("VAT");
		}
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

	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		lookupClient.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) { }

	@Override
	public void ancestorRemoved(AncestorEvent arg0) { }

	@Override
	public void searchPerformed(SearchEvent event) {
		Object[] DATA = CARD.this.getRETURN_DATA();
		if(DATA != null){
			String entity_id = (String) DATA[0];
			String proj_id = (String) DATA[6];
			String pbl_id = (String) DATA[3];
			Integer seq_no = (Integer) DATA[4];

			displayClientDetails(entity_id, proj_id, pbl_id, seq_no, false);
			displayTabDetails(entity_id, proj_id, pbl_id, seq_no, true);

			//displayTabs(entity_id, proj_id, pbl_id, seq_no, false);

			hdmfInfo_maintab.displayLoanFilingStatus(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFUnitInspection(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFPayments(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFSchedule(entity_id, proj_id, pbl_id, seq_no.toString());
			hdmfInfo_maintab.displayHDMFLoanReleasedDetail(entity_id, proj_id, pbl_id, seq_no.toString());
			tab_remConversion.DisplayHDMFREM(entity_id, proj_id, pbl_id, seq_no.toString());
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.isPopupTrigger()){
			//System.out.println("Mouse Pressed");

			/*if(arg0.getSource() instanceof JPanel){
				initializeMenu().show((JPanel)arg0.getSource(), arg0.getX(), arg0.getY());
			}
			if(arg0.getSource() instanceof _JXTextField){
				initializeMenu().show((_JXTextField)arg0.getSource(), arg0.getX(), arg0.getY());
			}
			if(arg0.getSource() instanceof JXLabel){
				initializeMenu().show((JXLabel)arg0.getSource(), arg0.getX(), arg0.getY());
			}
			if(arg0.getSource() instanceof _JXFormattedTextField){
				initializeMenu().show((_JXFormattedTextField)arg0.getSource(), arg0.getX(), arg0.getY());
			}
			if(arg0.getSource() instanceof JXTextField){
				initializeMenu().show((JXTextField)arg0.getSource(), arg0.getX(), arg0.getY());
			}*/

			initializeMenu().show(arg0.getComponent(), arg0.getX(), arg0.getY());

		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.isPopupTrigger()) {
			System.out.println("Mouse Released");
			initializeMenu().show(arg0.getComponent(), arg0.getX(), arg0.getY());
			//initializeMenu().show((JPanel)arg0.getSource(), arg0.getX(), arg0.getY());
		}
	}

	private JPopupMenu initializeMenu() { 
		JPopupMenu menu = new JPopupMenu();

		JMenuItem menuRefresh = new JMenuItem("Refresh");
		menu.add(menuRefresh);
		menuRefresh.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lookupClient.getValue() != null){
					refreshCARD(true);
				}
			}
		});

		menu.add(new JSeparator());

		JMenuItem menuPreviewSOA = new JMenuItem("Preview SOA");
		menu.add(menuPreviewSOA);
		menuPreviewSOA.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuPreviewSOA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lookupClient.getValue() != null){
					/*Map<String, Object> mapSOA = new HashMap<String, Object>();
					mapSOA.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapSOA.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
					mapSOA.put("entity_id", lookupClient.getText());
					mapSOA.put("proj_id", txtProjectID.getText());
					mapSOA.put("pbl_id", txtPblID.getText());
					mapSOA.put("seq_no", getSequence());

					mapSOA.put("entity_name", getValue(txtClientName.getText()));
					mapSOA.put("proj_name", getValue(txtProjectName.getText()));
					mapSOA.put("unit_desc", getValue(txtPblDescription.getText()));

					mapSOA.put("payment_scheme", getValue(txtPmtSchemeName.getText()));
					mapSOA.put("house_model", getValue(txtModelName.getText()));

					mapSOA.put("gsp", (BigDecimal) txtGSP.getValued());
					mapSOA.put("vat", (BigDecimal) txtVAT.getValued());
					mapSOA.put("discount", (BigDecimal) txtDiscount.getValued());
					mapSOA.put("nsp", (BigDecimal) txtNSP.getValued());
					mapSOA.put("dp", (BigDecimal) txtDP.getValued());
					mapSOA.put("availed_amount", (BigDecimal) txtLoanableAmount.getValued());
					mapSOA.put("os_balance", (BigDecimal) txtBalance.getValued());
					mapSOA.put("terms", txtMATerm.getIntegerValue());
					mapSOA.put("int_rate", txtIntRate.getValue());

					mapSOA.put("type_group_id", txtBuyerTypeID.getToolTipText());
					mapSOA.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));

					FncReport.generateReport("/Reports/rptStatementOfAccount.jasper", "Statement of Account", mapSOA);*/

					dlg_CARD_PreviewSOA preview_soa = new dlg_CARD_PreviewSOA((JFrame) CARD.this.getTopLevelAncestor(), "SOA Options", lookupClient.getValue(), txtProjectID.getText(), txtPblID.getText(), getSequence(), dateDues.getTimestamp(), _CARD.isLedgerSpecial(lookupClient.getValue(), txtProjectID.getText(), txtPblID.getText(), getSequence()));
					preview_soa.setLocationRelativeTo(FncGlobal.homeMDI);
					preview_soa.setVisible(true);

				}else{
					JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		if(UserInfo.ADMIN || UserInfo.EmployeeCode.equals("900395") || UserInfo.EmployeeCode.equals("987120") || UserInfo.EmployeeCode.equals("900267") || UserInfo.EmployeeCode.equals("900298") || UserInfo.EmployeeCode.equals("900955") || UserInfo.EmployeeCode.equals("900621") || UserInfo.EmployeeCode.equals("901016") || UserInfo.EmployeeCode.equals("900791") || UserInfo.EmployeeCode.equals("900851")) {
			JMenuItem menuPreviewSOA_RPT = new JMenuItem("Preview SOA w/ RPT");
			menu.add(menuPreviewSOA_RPT);
			menuPreviewSOA_RPT.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuPreviewSOA_RPT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){

						Map<String, Object> mapSOA = new HashMap<String, Object>();
						mapSOA.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
						mapSOA.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
						mapSOA.put("co_id", "02");
						mapSOA.put("entity_id", lookupClient.getValue());
						mapSOA.put("proj_id", txtProjectID.getText());
						mapSOA.put("pbl_id", txtPblID.getText());
						mapSOA.put("seq_no", getSequence());

						mapSOA.put("entity_name", txtClientName.getText());
						mapSOA.put("proj_name", txtProjectName.getText());
						mapSOA.put("unit_desc", txtPblDescription.getText());
						mapSOA.put("payment_scheme", txtPmtSchemeName.getText());
						mapSOA.put("house_model", getValue(txtModelName.getText()));

						mapSOA.put("gsp", txtGSP.getValued());
						mapSOA.put("vat", new BigDecimal("0.00"));
						mapSOA.put("discount", txtDiscount.getValued());

						mapSOA.put("nsp", txtNSP.getValued());
						mapSOA.put("dp", txtDP.getValued());

						mapSOA.put("availed_amount", txtLoanableAmount.getValued());
						mapSOA.put("os_balance", txtBalance.getValued());
						mapSOA.put("terms", txtMATerm.getValued());
						mapSOA.put("int_rate", txtIntRate.getValued() == null ? new BigDecimal("0.00"):txtIntRate.getValued());

						mapSOA.put("type_group_id", txtBuyerTypeID.getToolTipText());
						mapSOA.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
						//mapSOA.put("pmt_nth_month",SOA_LastNthPmt);
						//mapSOA.put("months", months);
						//mapSOA.put("pmt_all", SOA_full);
						mapSOA.put("date_cut_off", dateDues.getTimestamp());
						mapSOA.put("to_update", false);
						mapSOA.put("to_full_settle", false);
						mapSOA.put("pmt_type", "Cash");
						mapSOA.put("img_hand_pointer", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "handpointer.png"));
						mapSOA.put("SOA_BOI_Accnts", false);

						FncReport.generateReport("/Reports/rptStatementOfAccountRPT.jasper", "Statement of Account", mapSOA);
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		JMenuItem menuPreviewSOP = new JMenuItem("Preview SOP");
		menu.add(menuPreviewSOP);
		menuPreviewSOP.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuPreviewSOP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lookupClient.getValue() != null){
					Map<String, Object> mapSOP = new HashMap<String, Object>();
					mapSOP.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapSOP.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
					mapSOP.put("entity_id", lookupClient.getText());
					mapSOP.put("proj_id", txtProjectID.getText());
					mapSOP.put("pbl_id", txtPblID.getText());
					mapSOP.put("seq_no", getSequence());

					mapSOP.put("entity_name", getValue(txtClientName.getText()));
					mapSOP.put("proj_name", getValue(txtProjectName.getText()));
					mapSOP.put("unit_desc", getValue(txtPblDescription.getText()));

					mapSOP.put("payment_scheme", getValue(txtPmtSchemeName.getText()));
					mapSOP.put("sales_group", clientDetails[32]);
					mapSOP.put("selling_unit", clientDetails[27]);
					mapSOP.put("house_model", getValue(txtModelName.getText()));
					mapSOP.put("type_group_id", txtBuyerTypeID.getToolTipText());
					mapSOP.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
					mapSOP.put("with_comm_promo", false);

					FncReport.generateReport("/Reports/rptSummaryOfPayments.jasper", "Summary of Payments", mapSOP);
				}else{
					JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});



		JMenuItem menuPreviewSOPwithCommission = new JMenuItem("Preview SOP with commission/promo-incentives released");
		menu.add(menuPreviewSOPwithCommission);
		menuPreviewSOPwithCommission.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuPreviewSOPwithCommission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lookupClient.getValue() != null){

					Map<String, Object> mapSOP = new HashMap<String, Object>();
					mapSOP.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapSOP.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
					mapSOP.put("entity_id", lookupClient.getText());
					mapSOP.put("proj_id", txtProjectID.getText());
					mapSOP.put("pbl_id", txtPblID.getText());
					mapSOP.put("seq_no", getSequence());

					mapSOP.put("entity_name", getValue(txtClientName.getText()));
					mapSOP.put("proj_name", getValue(txtProjectName.getText()));
					mapSOP.put("unit_desc", getValue(txtPblDescription.getText()));

					mapSOP.put("payment_scheme", getValue(txtPmtSchemeName.getText()));
					mapSOP.put("sales_group", clientDetails[28]);
					mapSOP.put("selling_unit", clientDetails[27]);
					mapSOP.put("house_model", getValue(txtModelName.getText()));
					mapSOP.put("type_group_id", txtBuyerTypeID.getToolTipText());
					mapSOP.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
					mapSOP.put("with_comm_promo", true);

					FncReport.generateReport("/Reports/rptSummaryOfPayments.jasper", "Summary of Payments with commission/promo-incentives released", mapSOP);
				}else{
					JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JMenuItem menuPreviewSchedule = new JMenuItem("Preview Schedule");
		menu.add(menuPreviewSchedule);
		menuPreviewSchedule.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuPreviewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lookupClient.getValue() != null){
					Map<String, Object> mapSOP = new HashMap<String, Object>();
					mapSOP.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapSOP.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
					mapSOP.put("entity_id", lookupClient.getText());
					mapSOP.put("proj_id", txtProjectID.getText());
					mapSOP.put("pbl_id", txtPblID.getText());
					mapSOP.put("seq_no", getSequence());

					mapSOP.put("entity_name", getValue(txtClientName.getText()));
					mapSOP.put("proj_name", getValue(txtProjectName.getText()));
					mapSOP.put("unit_desc", getValue(txtPblDescription.getText()));

					mapSOP.put("payment_scheme", getValue(txtPmtSchemeName.getText()));
					mapSOP.put("sales_group", clientDetails[28]);
					mapSOP.put("selling_unit", clientDetails[27]);
					mapSOP.put("house_model", getValue(txtModelName.getText()));
					mapSOP.put("type_group_id", txtBuyerTypeID.getToolTipText());
					mapSOP.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
					mapSOP.put("with_comm_promo", false);

					mapSOP.put("tcp", txtGSP.getValued());
					mapSOP.put("discount", txtDiscount.getValued());
					mapSOP.put("net_sp", txtNSP.getValued());
					mapSOP.put("reqd_dp", txtDPAmount.getValued());
					mapSOP.put("bal_amt", txtBalance.getValued());
					mapSOP.put("ma_amt", txtMAAmount.getValued());
					mapSOP.put("int_rate", txtIntRate.getValued() == null ? new BigDecimal("0.00"):txtIntRate.getValued());
					mapSOP.put("dp_term", txtDPTerm.getValued());
					mapSOP.put("ma_term", txtMATerm.getValued());

					FncReport.generateReport("/Reports/rptClientSchedule.jasper", "Client Schedule", mapSOP);
				}else{
					JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		if(UserInfo.ADMIN || UserInfo.EmployeeCode.equals("900395") || UserInfo.EmployeeCode.equals("987120") || UserInfo.EmployeeCode.equals("900267") || UserInfo.EmployeeCode.equals("900298") || UserInfo.EmployeeCode.equals("900955") || UserInfo.EmployeeCode.equals("900621")){
			JMenuItem menuPreviewSchedule_RPT = new JMenuItem("Preview Schedule w/ RPT");
			menu.add(menuPreviewSchedule_RPT);
			menuPreviewSchedule_RPT.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuPreviewSchedule_RPT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						Map<String, Object> mapSOP = new HashMap<String, Object>();
						mapSOP.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
						mapSOP.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
						mapSOP.put("entity_id", lookupClient.getText());
						mapSOP.put("proj_id", txtProjectID.getText());
						mapSOP.put("pbl_id", txtPblID.getText());
						mapSOP.put("seq_no", getSequence());

						mapSOP.put("entity_name", getValue(txtClientName.getText()));
						mapSOP.put("proj_name", getValue(txtProjectName.getText()));
						mapSOP.put("unit_desc", getValue(txtPblDescription.getText()));

						mapSOP.put("payment_scheme", getValue(txtPmtSchemeName.getText()));
						mapSOP.put("sales_group", clientDetails[28]);
						mapSOP.put("selling_unit", clientDetails[27]);
						mapSOP.put("house_model", getValue(txtModelName.getText()));
						mapSOP.put("type_group_id", txtBuyerTypeID.getToolTipText());
						mapSOP.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
						mapSOP.put("with_comm_promo", false);

						mapSOP.put("tcp", txtGSP.getValued());
						mapSOP.put("discount", txtDiscount.getValued());
						mapSOP.put("net_sp", txtNSP.getValued());
						mapSOP.put("reqd_dp", txtDPAmount.getValued());
						mapSOP.put("bal_amt", txtBalance.getValued());
						mapSOP.put("ma_amt", txtMAAmount.getValued());
						mapSOP.put("int_rate", txtIntRate.getValued() == null ? new BigDecimal("0.00"):txtIntRate.getValued());
						mapSOP.put("dp_term", txtDPTerm.getValued());
						mapSOP.put("ma_term", txtMATerm.getValued());

						FncReport.generateReport("/Reports/rptClientSchedule_RPT.jasper", "Client Schedule", mapSOP);
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		JMenuItem menuPreviewPayments = new JMenuItem("Preview Payments");
		menu.add(menuPreviewPayments);
		menuPreviewPayments.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuPreviewPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lookupClient.getValue() != null){
					Map<String, Object> mapSOP = new HashMap<String, Object>();
					mapSOP.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapSOP.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
					mapSOP.put("entity_id", lookupClient.getText());
					mapSOP.put("proj_id", txtProjectID.getText());
					mapSOP.put("pbl_id", txtPblID.getText());
					mapSOP.put("seq_no", getSequence());

					mapSOP.put("entity_name", getValue(txtClientName.getText()));
					mapSOP.put("proj_name", getValue(txtProjectName.getText()));
					mapSOP.put("unit_desc", getValue(txtPblDescription.getText()));

					mapSOP.put("payment_scheme", getValue(txtPmtSchemeName.getText()));
					mapSOP.put("sales_group", clientDetails[28]);
					mapSOP.put("selling_unit", clientDetails[27]);
					mapSOP.put("house_model", getValue(txtModelName.getText()));
					mapSOP.put("type_group_id", txtBuyerTypeID.getToolTipText());
					mapSOP.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
					mapSOP.put("with_comm_promo", false);

					FncReport.generateReport("/Reports/rptClientPayments.jasper", "Client Payments", mapSOP);
					System.out.println("Break");

				}else{
					JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		//added: 4/23/2019 - Refer to DCRF #1016
		JMenuItem menuPreviewWaterPayments = new JMenuItem("Preview Water Statement");
		menu.add(menuPreviewWaterPayments);
		menuPreviewWaterPayments.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuPreviewWaterPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lookupClient.getValue() != null){
					Map<String, Object> mapWP = new HashMap<String, Object>();
					mapWP.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapWP.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
					mapWP.put("entity_id", lookupClient.getText());
					mapWP.put("proj_id", txtProjectID.getText());
					mapWP.put("pbl_id", txtPblID.getText());
					mapWP.put("seq_no", getSequence());

					mapWP.put("entity_name", getValue(txtClientName.getText()));
					mapWP.put("proj_name", getValue(txtProjectName.getText()));
					mapWP.put("unit_desc", txtPblDescription.getText());
					mapWP.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
					FncReport.generateReport("/Reports/rptWaterPayments.jasper", "Summary of Water SOA", mapWP);
					//ADDED BY JARI CRUZ ASOF NOV 21 2022
					String oth_pbl_id = _CARD.checkIfTwoLot(lookupClient.getText(), txtProjectID.getText(), txtPblID.getText(), getSequence());
					if(!oth_pbl_id.equals("")) {
						System.out.println("two lots ? " + oth_pbl_id);
						Map<String, Object> mapWP2 = new HashMap<String, Object>();
						mapWP2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
						mapWP2.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
						mapWP2.put("entity_id", lookupClient.getText());
						mapWP2.put("proj_id", txtProjectID.getText());
						mapWP2.put("pbl_id", oth_pbl_id);
						mapWP2.put("seq_no", getSequence());

						mapWP2.put("entity_name", getValue(txtClientName.getText()));
						mapWP2.put("proj_name", getValue(txtProjectName.getText()));
						mapWP2.put("unit_desc", txtPblDescription.getText());
						mapWP2.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
						FncReport.generateReport("/Reports/rptWaterPayments.jasper", "Summary of Water SOA 2nd Lot", mapWP2);
					}
				}else{
					JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		// ADDED BY JARI CRUZ ASOF NOV 23 2022
		JMenuItem menuPreviewWaterBilling = new JMenuItem("Preview Water Billing");
		menu.add(menuPreviewWaterBilling);
		menuPreviewWaterBilling.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
		menuPreviewWaterBilling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				String co_id = _CARD.getClientCompany(txtProjectID.getText());
				String proj_id = txtProjectID.getText();
				String entity_id = lookupClient.getValue();
				String pbl_id = txtPblID.getText();
				Integer seq_no = getSequence();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
				Date reading_date = _CARD.getClientLatestWaterReading(lookupClient.getValue(), txtProjectID.getText(), txtPblID.getText(), getSequence());
		        String reading_date_str = dateFormat.format(reading_date);  
				
				mapParameters.put("co_id", co_id);
				mapParameters.put("proj_id", proj_id);
				mapParameters.put("phase_no", "");
				mapParameters.put("entity_id", entity_id);
				mapParameters.put("phase_no", "");
				mapParameters.put("pbl_id", pbl_id);
				mapParameters.put("seq_no", seq_no);
				mapParameters.put("reading_date", reading_date_str);	
				mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(_CARD.getClientCompany(txtProjectID.getText()))));

				//System.out.print
				String SQL = "select * from view_water_bill_v2('"+co_id+"', '', '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+reading_date+"')";
				FncSystem.out("view_water_bill_v2", SQL);
				FncReport.generateReport("/Reports/rptMonthlyStatementofAccount_v2.jasper", "Transmittal Form", mapParameters);
				
				String oth_pbl_id = checkOtherLots(entity_id, pbl_id, proj_id, seq_no);
				
				if(oth_pbl_id != null) {
					Map<String, Object> mapParameters2 = new HashMap<String, Object>();

					mapParameters2.put("co_id", co_id);
					mapParameters2.put("proj_id", proj_id);
					mapParameters2.put("phase_no", "");
					mapParameters2.put("entity_id", entity_id);
					mapParameters2.put("phase_no", "");
					mapParameters2.put("pbl_id", oth_pbl_id);
					mapParameters2.put("seq_no", seq_no);
					mapParameters2.put("reading_date", reading_date_str);	
					mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(_CARD.getClientCompany(txtProjectID.getText()))));

					//System.out.print
					FncReport.generateReport("/Reports/rptMonthlyStatementofAccount_v2.jasper", "Transmittal Form 2nd lot", mapParameters2);
				}
			}
		});
		{
			// Added by Jessa Herrera 2017-03-02
			JMenuItem menuPreviewTD = new JMenuItem("Preview Technical Description");
			menu.add(menuPreviewTD);
			menuPreviewTD.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuPreviewTD.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						Map<String, Object> mapSOP = new HashMap<String, Object>();
						mapSOP.put("tech_desc", clientDetails[54]);
						mapSOP.put("phase", clientDetails[67]);
						mapSOP.put("buyertype", clientDetails[25]);
						mapSOP.put("tct_no", clientDetails[55]);

						FncReport.generateReport("/Reports/rptTechnicalDescription.jasper", "Technical Description", mapSOP);
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		{
			JMenuItem menuPreviewHDMF_Pmts = new JMenuItem("Preview HDMF Payments");
			menu.add(menuPreviewHDMF_Pmts);
			menuPreviewHDMF_Pmts.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuPreviewHDMF_Pmts.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						Map<String, Object> mapSOP = new HashMap<String, Object>();

						mapSOP.put("entity_id", lookupClient.getText());
						mapSOP.put("proj_id", txtProjectID.getText());
						mapSOP.put("pbl_id", txtPblID.getText());
						mapSOP.put("seq_no", getSequence());
						mapSOP.put("entity_name", getValue(txtClientName.getText()));
						mapSOP.put("proj_name", getValue(txtProjectName.getText()));
						mapSOP.put("unit_desc", getValue(txtPblDescription.getText()));
						mapSOP.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
						mapSOP.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png"));

						FncReport.generateReport("/Reports/rptHDMF_Payments.jasper", "HDMF Payments", mapSOP);
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		{
			JMenuItem menuPreviewSpecialSOA= new JMenuItem("Preview Special SOA");
			menu.add(menuPreviewSpecialSOA);
			menuPreviewSpecialSOA.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuPreviewSpecialSOA.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						Map<String, Object> mapSOA_Special = new HashMap<String, Object>();

						mapSOA_Special.put("entity_id", lookupClient.getText());
						mapSOA_Special.put("proj_id", txtProjectID.getText());
						mapSOA_Special.put("pbl_id", txtPblID.getText());
						mapSOA_Special.put("seq_no", getSequence());
						mapSOA_Special.put("entity_name", getValue(txtClientName.getText()));
						mapSOA_Special.put("proj_name", getValue(txtProjectName.getText()));
						mapSOA_Special.put("unit_desc", getValue(txtPblDescription.getText()));
						mapSOA_Special.put("dp", (BigDecimal) txtDP.getValued());
						mapSOA_Special.put("availed_amount", (BigDecimal) txtLoanableAmount.getValued());
						mapSOA_Special.put("nsp", (BigDecimal) txtNSP.getValued());

						mapSOA_Special.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));

						FncReport.generateReport("/Reports/rptStatementOfAccount_Special.jasper", "Special SOA", mapSOA_Special);
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		{
			JMenuItem menuSeparateSOA_Combined= new JMenuItem("Preview Separate SOA (For Combined Units only)");
			menu.add(menuSeparateSOA_Combined);
			menuSeparateSOA_Combined.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuSeparateSOA_Combined.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						Map<String, Object> mapSeparateSOA_Combined = new HashMap<String, Object>();

						mapSeparateSOA_Combined.put("entity_id", lookupClient.getText());
						mapSeparateSOA_Combined.put("proj_id", txtProjectID.getText());
						mapSeparateSOA_Combined.put("pbl_id", txtPblID.getText());
						mapSeparateSOA_Combined.put("seq_no", getSequence());
						

						mapSeparateSOA_Combined.put("prepared_by", String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));

						FncReport.generateReport("/Reports/rptSOA_Combined1stUnit.jasper", "Separate SOA For Combined Units 1st", mapSeparateSOA_Combined);
						FncReport.generateReport("/Reports/rptSOA_Combined2ndUnit.jasper", "Separate SOA For Combined Units 2nd", mapSeparateSOA_Combined);
						
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		menu.add(new JSeparator());

		if(withModuleAccess("Technical Documents")){
			JMenuItem menuViewTechnicalDocs = new JMenuItem("View Technical Docs");
			menu.add(menuViewTechnicalDocs);
			menuViewTechnicalDocs.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuViewTechnicalDocs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String entity_id = lookupClient.getValue();
						String entity_name = getValue(txtClientName.getText());
						String proj_id = txtProjectID.getText();
						String proj_name = getValue(txtProjectName.getText());
						String pbl_id = txtPblID.getText();
						String unit_desc = getValue(txtPblDescription.getText());
						Integer seq_no = getSequence();
						String model_desc = getValue(txtModelName.getText());

						if(FncGlobal.homeMDI.isNotExisting("BuyersRequestforTechnicalDocuments")){
							BuyersRequestforTechnicalDocuments brt = new BuyersRequestforTechnicalDocuments(entity_id, entity_name, proj_id, proj_name, pbl_id, unit_desc, Integer.toString(seq_no), model_desc, true);
							Home_JSystem.addWindow(brt, null);
						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		if(withModuleAccess("Documents Monitoring")){
			JMenuItem menuViewDocumentsMonitoring = new JMenuItem("View Documents Monitoring");
			menu.add(menuViewDocumentsMonitoring);
			menuViewDocumentsMonitoring.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuViewDocumentsMonitoring.addActionListener(new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String entity_id = lookupClient.getValue();
						String proj_id = txtProjectID.getText();
						String pbl_id = txtPblID.getText();
						Integer seq_no = getSequence();

						if(FncGlobal.homeMDI.isNotExisting("DocumentsMonitoring")){
							DocumentsMonitoring dm = new DocumentsMonitoring(entity_id, proj_id, pbl_id, seq_no);
							Home_JSystem.addWindow(dm, null);
						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		if(withModuleAccess("Regular Billing and Notices")){
			JMenuItem menuViewNoticesMonitoring = new JMenuItem("View Notices Monitoring");
			menu.add(menuViewNoticesMonitoring);
			menuViewNoticesMonitoring.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuViewNoticesMonitoring.addActionListener(new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String entity_id = lookupClient.getValue();
						String proj_id = txtProjectID.getText();
						String pbl_id = txtPblID.getText();
						Integer seq_no = getSequence();

						if(FncGlobal.homeMDI.isNotExisting("DocumentsMonitoring")){
							RegularBillingAndNotices rbn = new RegularBillingAndNotices(entity_id, proj_id, pbl_id, seq_no);
							Home_JSystem.addWindow(rbn, null);
						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		//ADDED BY JOHN LESTER FATALLO 10-14-15
		if(withModuleAccess("Client Information")){
			JMenuItem menuViewClientInfo = new JMenuItem("View Client Info");
			menu.add(menuViewClientInfo);
			menuViewClientInfo.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuViewClientInfo.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String entity_id = lookupClient.getValue();
						String entity_name = getValue(txtClientName.getText());

						if(FncGlobal.homeMDI.isNotExisting("ClientInformation")){
							ClientInformation ci = new ClientInformation("Client Information", entity_id, entity_name);
							Home_JSystem.addWindow(ci, null);
						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		if(withModuleAccess("Client Feedback")){
			JMenuItem menuViewClientsFeedback = new JMenuItem("View Client's Feedback");
			menu.add(menuViewClientsFeedback);
			menuViewClientsFeedback.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuViewClientsFeedback.addActionListener(new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String entity_id = lookupClient.getValue();
						String proj_id = txtProjectID.getText();
						String pbl_id = txtPblID.getText();
						Integer seq_no = getSequence();

						if(FncGlobal.homeMDI.isNotExisting("ClientFeedback")){
							ClientFeedback cf = new ClientFeedback(entity_id, proj_id, pbl_id, Integer.toString(seq_no));
							Home_JSystem.addWindow(cf, null);
						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		if(withModuleAccess("Client Follow Up")){
			JMenuItem menuViewClientFollowUp = new JMenuItem("View Client's Follow Up");
			menu.add(menuViewClientFollowUp);
			menuViewClientFollowUp.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuViewClientFollowUp.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String entity_id = lookupClient.getValue();
						String proj_id = txtProjectID.getText();
						String pbl_id = txtPblID.getText();
						Integer seq_no = getSequence();
						
						System.out.printf("entity_id", entity_id);
						System.out.printf("proj_id: %s%n", proj_id);
						System.out.printf("pbl_id: %s%n", pbl_id);
						System.out.printf("seq_no: %s%n", seq_no);
						
						if(FncGlobal.homeMDI.isNotExisting("ClientFollowUp")){
							System.out.println("Dumaan dito");
							ClientFollowUp cfu = new ClientFollowUp(entity_id, proj_id, pbl_id, seq_no.toString());
							Home_JSystem.addWindow(cfu, null);
						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		/**
		 * 
		 * Added for View PN History
		 * 
		 * Author: Christian Paquibot
		 */

		if(withModuleAccess("Promissory Note / Commitment")){
			JMenuItem menuViewClientPnHistory = new JMenuItem("View PN History");
			menu.add(menuViewClientPnHistory);
			menuViewClientPnHistory.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuViewClientPnHistory.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String  entity_id = lookupClient.getValue();
						String  proj_id = txtProjectID.getText();
						String  pbl_id = txtPblID.getText();
						Integer seq_no = getSequence();
						String buyertypeID = txtBuyerTypeID.getText();
						System.out.println("************CARD" + seq_no);
						if(FncGlobal.homeMDI.isNotExisting("PromissryNote_v2")){
							PromissryNote_v2 pn = new PromissryNote_v2(entity_id,proj_id,pbl_id,seq_no,buyertypeID);
							Home_JSystem.addWindow(pn, null);

						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		menu.add(new JSeparator());

		if(NOA_RELEASED){
			JMenuItem menuTagAsLoanRelease = new JMenuItem("Tag as Loan Released");
			menu.add(menuTagAsLoanRelease);
			menuTagAsLoanRelease.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
			menuTagAsLoanRelease.addActionListener(new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent arg0) {
					if(lookupClient.getValue() != null){
						String entity_id = lookupClient.getValue();
						String proj_id = txtProjectID.getText();
						String pbl_id = txtPblID.getText();
						Integer seq_no = getSequence();

						if(FncGlobal.homeMDI.isNotExisting("LoanReleased")){
							LoanReleased lnrel = new LoanReleased(entity_id, proj_id, pbl_id, seq_no);
							Home_JSystem.addWindow(lnrel, null);
						}
					}else{
						JOptionPane.showMessageDialog(CARD.this, "Please select a client first.", arg0.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		return menu;
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

	public void startTimerStatus() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				blinkState = !blinkState;

				if(blinkState){
					if(getSecondaryTitle().equals("") == false){
						CARD.this.setTitle(String.format("%s *** %s ***", title, getSecondaryTitle()));
					}else {
						CARD.this.setTitle(getPrimaryTitle());
					}
					//System.out.printf("********************************************************************** %s (%s)%n", getSecondaryTitle(), timerStatus.getActionCommand());
				}else{
					CARD.this.setTitle(getPrimaryTitle());
				}
			}
		};

		if(timerStatus == null){
			System.out.println("********** DUMAAN! #1");
			timerStatus = new Timer(1000, action);
			System.out.printf("Display value of Secondary Title: %s%n", getSecondaryTitle());

			timerStatus.setActionCommand(getSecondaryTitle());
			timerStatus.start();
		}else{
			System.out.println("********** DUMAAN! #2");
			timerStatus.stop();
			timerStatus.restart();
		}

		//System.out.printf("Length: %s%n", timerStatus.getActionListeners().length);
	}

	public void stopTimerStatus() {
		timerStatus.stop();
		timerStatus = null;
	}

	public void actionPerformed(ActionEvent evt) {

	}

	private Boolean subprojHasBOI_LTS(String pbl){

		Boolean subprojHasBOI_LTS = false;		

		String SQL = 
				"select sub_project_has_lts_boi(get_subproj_id_given_pbl('"+pbl+"')) " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			subprojHasBOI_LTS = true;
		}else{
			subprojHasBOI_LTS = false;
		}

		return subprojHasBOI_LTS;

	}

	private BigDecimal totalDues(){
		BigDecimal amt_to_update = new BigDecimal("0.00");

		for(int x= 0; x<modelDues.getRowCount(); x++){
			amt_to_update = amt_to_update.add((BigDecimal) modelDues.getValueAt(x, 16));

		}
		return amt_to_update;
	}

	private Boolean withModuleAccess(String module_name) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM mf_privileges WHERE emp_code = '"+UserInfo.EmployeeCode+"' AND privileges = '"+module_name+"'";
		db.select(SQL);

		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	/*ADDED BY JARI CRUZ ASOF NOV 23 2022*/
	private String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}

	private void CeateBankInfo() {

	}
	private void txtNotesValue(){
		pgSelect bat = new pgSelect();
		pgSelect pmd = new pgSelect();
		pgSelect cmd = new pgSelect();

		if(tblNTP_History.getSelectedRows().length==1){
			int row = tblNTP_History.getSelectedRow();
			String ntpNo = (String) modelNTP_History.getValueAt(row, 0);

			String batQuery = "SELECT remarks from rf_ntp_notes where ntp_no = '"+ntpNo+"' AND dept_code = '99'";
			bat.select(batQuery);
			if(bat.isNotNull()){
				txtBat.setText((String) bat.getResult()[0][0]);
			} else
			{
				txtBat.setText("");
			}
			String pmdQuery = "SELECT remarks from rf_ntp_notes where ntp_no = '"+ntpNo+"' AND dept_code = '08'";
			pmd.select(pmdQuery); 
			if(pmd.isNotNull()){
				txtPmd.setText((String) pmd.getResult()[0][0]);
			}else
			{
				txtPmd.setText("");	
			}
			String cmdQuery = "SELECT remarks from rf_ntp_notes where ntp_no = '"+ntpNo+"' AND dept_code = '07'";
			cmd.select(cmdQuery);
			if(cmd.isNotNull()){
				txtCmd.setText((String) cmd.getResult()[0][0]);
			}else
			{
				txtCmd.setText("");
			}
		}

	}
	
	private void displayBIR(String entity_id) {
		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select a.rdo, b.bank_name, c.bank_branch_location, d.acct_no \n" + 
				"from rf_cwt_bir_details a \n" + 
				"inner join mf_bank b on a.bank_id = b.bank_id \n" + 
				"inner join mf_bank_branch c on a.bank_id = c.bank_id and a.bank_branch_id = c.bank_branch_id \n" + 
				"left join mf_bank_account_bir d on a.bank_id = d.bank_id and a.bank_branch_id = d.bank_branch_id \n" + 
				"where a.entity_id = '"+entity_id+"'; ");

		try {
			txtRDO.setText(dbExec.getResult()[0][0].toString());
		} catch (NullPointerException e) {
			txtRDO.setText("");
		}
		
		try {
			txtBank.setText(dbExec.getResult()[0][1].toString());
		} catch (NullPointerException e) {
			txtBank.setText("");
		}
		
		try {
			txtBranch.setText(dbExec.getResult()[0][2].toString());
		} catch (NullPointerException e) {
			txtBranch.setText("");
		}
		
		try {
			txtAccntNo.setText(dbExec.getResult()[0][3].toString());
		} catch (NullPointerException e) {
			txtAccntNo.setText("");
		}
		
		
	}
	
	// ADDED BY MONIQUE DTD 10-23-2023; REFER TO DCRF#2766
	public static Boolean isER1B(String proj_id, String pbl_id) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (Select * from mf_unit_info where proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and phase = '1-B')";
		db.select(SQL);
		
		System.out.printf("Is ER1-B: %s", SQL);

		if (db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		} else {
			return false;
		}
	}
}
