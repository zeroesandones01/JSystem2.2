package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXSearchField;

import com.cloudgarden.layout.AnchorLayout;
import com.lowagie.text.Rectangle;

import Accounting.CWTRemittance.CWTRemittance;
import Accounting.Cashiering.CashCountSummary;
import Accounting.Cashiering.CashCountSummary2;
import Accounting.Cashiering.CashReceiptBook;
import Accounting.Cashiering.GarbageFee;
import Accounting.Cashiering.IssuanceOfReceipt;
import Accounting.Cashiering.IssuanceOfReceipt_Montalban;
import Accounting.Cashiering.IssuanceOfReceipt_v2;
import Accounting.Cashiering.LateOrAutoJv;
import Accounting.Cashiering.LoanReleasedIssuance;
//import Accounting.Cashiering.LoanReleasedIssuance;
import Accounting.Cashiering.RealTimeDebitPosting;
import Accounting.Cashiering.RealTimeDebitPosting_LoanReleased;
import Accounting.Cashiering.Unidentified_Identified_Deposits;
import Accounting.Cashiering.Unidentified_Identified_Deposits_v2;
import Accounting.Cashiering.UploadAUBPayments;
import Accounting.Cashiering.ud_tag;
import Accounting.Collections.BankFinancingLoanTakeout;
import Accounting.Collections.BuyersLedger;
import Accounting.Collections.BuyersPaymentPosting;
import Accounting.Collections.CheckReplacement;
import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Collections.Deposits;
import Accounting.Collections.Deposits_v2;
import Accounting.Collections.HDMFCollectionReport;
import Accounting.Collections.IssueOR_toGoodChecks;
import Accounting.Collections.IssueOR_toLateLTS;
import Accounting.Collections.RetFeeOnlineMultiplePayments;
import Accounting.Collections.RetentionFee;
import Accounting.Collections.RetentionFeeBOI;
import Accounting.Collections.RetentionFee_Online;
import Accounting.Collections.checkStatusMonitoring_others;
import Accounting.Collections.checkStatusMonitoring_pdcWarehousing;
import Accounting.Disbursements.ePaymentForAUB;
import Accounting.Commissions.ATM_processing;
import Accounting.Commissions.AgentTIN_tagging;
import Accounting.Commissions.CommissionInquiry;
import Accounting.Commissions.Commission_Schedule_Generator;
import Accounting.Commissions.ProcessCommission;
import Accounting.Commissions.Promo_Incentive_ManualProcessing_v2;
import Accounting.Commissions.QualifiedCommission;
import Accounting.Commissions.ReleaseCommThruATM;
import Accounting.ContractorsPayment.BackchargeUtilities;
import Accounting.ContractorsPayment.ChangeOrder;
import Accounting.ContractorsPayment.ContractorsBilling;
import Accounting.ContractorsPayment.HouseRepair;
import Accounting.Disbursements.AUB_Status_Monitoring;
import Accounting.Disbursements.ChangePaymentType;
import Accounting.Disbursements.CheckVoucher;
import Accounting.Disbursements.CheckVoucherMC;
import Accounting.Disbursements.DocsProcessing;
import Accounting.Disbursements.DocsProcessing_v2;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Accounting.Disbursements.RequestForPaymentv2;
import Accounting.Disbursements.TagMCCVnumber;
import Accounting.Disbursements.ePaymentFromAUB;
import Accounting.Disbursements.reversalProccessing;
import Accounting.FixedAssets.AddItem;
import Accounting.FixedAssets.AssetCard;
import Accounting.FixedAssets.AssetMonitoring;
import Accounting.FixedAssets.IssuanceOfSupplies;
//import Accounting.FixedAssets.DepreciationofAsset;
import Accounting.FixedAssets.MasterList;
import Accounting.FixedAssets.NotFoundAsset;
import Accounting.FixedAssets.PrintAssetSticker;
import Accounting.FixedAssets.Request_Stationary_supplies;
import Accounting.FixedAssets.Supplier;
//import Accounting.FixedAssets.RepairMonitoring;
import Accounting.FixedAssets.addSupplier;
import Accounting.FixedAssets.canvassing;
import Accounting.FixedAssets.procurement;
import Accounting.FixedAssets.scrap_monitoring;
import Accounting.GeneralLedger.DebitCreditMemo;
import Accounting.GeneralLedger.GeneralLedger;
import Accounting.GeneralLedger.JournalVoucher;
import Accounting.GeneralLedger.TrialBalance;
import Accounting.GeneralLedger.TrialBalance_wBDown;
import Accounting.Liquidation.CALiquidation;
import Accounting.Liquidation.LiquidationSOA;
import Accounting.NonFixedAsset.NonFixedAsset;
import Admin.AddEditEntityType;
import Admin.AddReceiptNumber;
import Admin.receiptMaintenance;
import Buyers.ClientServicing.BuyersCheckMonitoring;
import Buyers.ClientServicing.BuyersRequestforTechnicalDocuments;
import Buyers.ClientServicing.CARD;
import Buyers.ClientServicing.ChecksForWithdrawal;
import Buyers.ClientServicing.ClientFeedback;
import Buyers.ClientServicing.ClientFollowUp;
import Buyers.ClientServicing.ClientInformation;
import Buyers.ClientServicing.ClientRequestOldDetails;
import Buyers.ClientServicing.CreditOfPayment;
import Buyers.ClientServicing.CreditPaymentItsReal;
import Buyers.ClientServicing.DocumentsMonitoring;
import Buyers.ClientServicing.HoldingAndReservation;
import Buyers.ClientServicing.IHF_FS_Discount_Tagging;
import Buyers.ClientServicing.Add_Edit_Holidays;
import Buyers.ClientServicing.OrderOfPayment;
import Buyers.ClientServicing.OtherRequest;
import Buyers.ClientServicing.PaymentBreakdown;
import Buyers.ClientServicing.PreDocsEvaluation;
import Buyers.ClientServicing.RefundofPayment;
import Buyers.ClientServicing.SCDMonitoring;
import Buyers.ClientServicing.Special_Holding;
import Buyers.ClientServicing.TagClientMailsForExport;
import Buyers.ClientServicing.TaggingOfCashSurrenderValue;
import Buyers.ClientServicing.UnholdingOfUnitsByBatch;
import Buyers.ClientServicing.WaivePenalty;
import Buyers.CreditandCollections.AcquiredAsset;
import Buyers.CreditandCollections.Cancellation;
import Buyers.CreditandCollections.CancelledFullsettledByPagibig;
import Buyers.CreditandCollections.DirectDepositAssignment;
import Buyers.CreditandCollections.FullSettled;
import Buyers.CreditandCollections.PastDueProcessing_v2;
import Buyers.CreditandCollections.PostPCopyTCTRPTReceipt;
import Buyers.CreditandCollections.Post_Office_Utility;
import Buyers.CreditandCollections.PromissryNote_v2;
import Buyers.CreditandCollections.RTaxBillingStatement;
import Buyers.CreditandCollections.RealTimeDebitPiso;
import Buyers.CreditandCollections.RealTimeDebitPiso_LoanReleased;
import Buyers.CreditandCollections.RealTimeDebitPostedwithEmail;
import Buyers.CreditandCollections.RealTimeDebitUnposted;
import Buyers.CreditandCollections.RealTimeDebitUpload_LoanReleased;
import Buyers.CreditandCollections.RealTimeDebit_LoanReleased;
//import Buyers.CreditandCollections.RealTimeDebitPiso;
import Buyers.CreditandCollections.RegularBillingAndNotices;
import Buyers.CreditandCollections.TagCancelledByPAGIBIG;
import Buyers.CreditandCollections.Transmittal;
import Buyers.CreditandCollections.pctReports;
import Buyers.CreditandCollections.rtdDocuments;
import Buyers.CreditandCollections.UDOASPrinting;
import Buyers.LegalandLiaisoning.ForEditRealPropertyHouse;
import Buyers.LegalandLiaisoning.OccupancyMonitoring;
import Buyers.LegalandLiaisoning.ProcessingCostTransactionEntry;
import Buyers.LegalandLiaisoning.RevolvingFund;
import Buyers.LegalandLiaisoning.TCTTaxDecProcessing;
import Buyers.LegalandLiaisoning.TagRMCnumberPcost;
import Buyers.LegalandLiaisoning.TagRMCnumberTcost;
import Buyers.LegalandLiaisoning.TransferCostTransactionEntry;
import Buyers.LoansManagement.BankFinancingMonitoring;
import Buyers.LoansManagement.BankInformation;
import Buyers.LoansManagement.BankPayments;
import Buyers.LoansManagement.BankREM;
import Buyers.LoansManagement.Buyback;
import Buyers.LoansManagement.CI_Fee_Payments;
import Buyers.LoansManagement.CTS_Notarization;
import Buyers.LoansManagement.FireInsurance;
import Buyers.LoansManagement.HouseAppraisal;
import Buyers.LoansManagement.LoanReleased;
import Buyers.LoansManagement.LoanReleasedOnline;
import Buyers.LoansManagement.MortgageRedemptionInsurance;
import Buyers.LoansManagement.NOATagging;
import Buyers.LoansManagement.PagibigStatusMonitoring_v2;
import Buyers.LoansManagement.SalesOfReceivables;
import Buyers.LoansManagement.TagAccountsQualifiedForNoticeToConstruct;
import Buyers.LoansManagement.cancelledTCTAnnotation;
import Buyers.LoansManagement.mbtcLoanReleased;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import File.ChangePassword;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.ImagePanel;
import Functions.UserInfo;
import Lookup._JLookupTable;
import Projects.BiddingandAwarding.ConstructionAccomplishment;
import Projects.BiddingandAwarding.ContractorsSupplementaryDetails;
import Projects.BiddingandAwarding.FixedHousingAwardingCost;
import Projects.BiddingandAwarding.NoticeToProceed;
import Projects.ConstructionManagement.GenerateForUnitTurnOverOrientation;
import Projects.ConstructionManagement.GenerateQRCode;
import Projects.ConstructionManagement.TagTurnOverOrientationAttendees;
//import Projects.ConstructionManagement.HouseRepair;
import Projects.ConstructionManagement.UnitStatusMonitoring;
import Projects.PropertyManagement.ClientNotices;
import Projects.PropertyManagement.CompletedRenovation;
import Projects.PropertyManagement.EndorsementHouseTurnOver;
import Projects.PropertyManagement.EndorsementHouseTurnoverReport;
import Projects.PropertyManagement.FacilitiesTagging;
import Projects.PropertyManagement.FacilityTagWaterReading;
import Projects.PropertyManagement.HOADuesStartDate;
import Projects.PropertyManagement.HappyWell;
import Projects.PropertyManagement.MeralcoSIN;
import Projects.PropertyManagement.TagMoveInAccounts;
import Projects.PropertyManagement.TagMoveOutAccounts;
import Projects.PropertyManagement.TagRenovationAccount;
import Projects.PropertyManagement.TagTurnedOverAccounts;
import Projects.PropertyManagement.TaggingWaterMeterInstallation;
import Projects.PropertyManagement.UploadFacilitiesPayments;
import Projects.PropertyManagement.WaterBilling_v2;
import Projects.PropertyManagement.WaterPromoTagging;
import Projects.PropertyManagement.WaterServiceConnection;
import Projects.SalesandMarketing.PaymentScheme;
import Projects.SalesandMarketing.Pricelist;
import Projects.SalesandMarketing.Request_Payment_Trip;
import Projects.SalesandMarketing.SalesContractMonitoring;
import Projects.SalesandMarketing.Trip_Ticket_Entry;
import Projects.SalesandMarketing.Tripping_Cost;
import Projects.SalesandMarketing.prcDocMon;
import Reports.Accounting.Accounting_Books;
import Reports.Accounting.AdvancesToContractors_BC;
import Reports.Accounting.AdvancesToContractors_DP;
import Reports.Accounting.AdvancesToContractors_Utilities;
import Reports.Accounting.BatchPrinting;
import Reports.Accounting.BayadCenter_ToUpdate;
import Reports.Accounting.CDF_listing;
import Reports.Accounting.CRES_GAE_MAF_INT_VAT;
import Reports.Accounting.CVprooflist;
import Reports.Accounting.CanceledReceipts;
import Reports.Accounting.CashDisbursement_PV_Register;
import Reports.Accounting.Cashflow;
import Reports.Accounting.CheckSeries;
import Reports.Accounting.CheckStatusListing;
import Reports.Accounting.ChecksPreviouslyonHold;
import Reports.Accounting.CommPayoutForm;
import Reports.Accounting.Commission_Reports;
import Reports.Accounting.Commission_Reports_FR;
import Reports.Accounting.Commission_Schedule;
import Reports.Accounting.ConstructionInProgress;
import Reports.Accounting.ContractorsBillingSummary;
import Reports.Accounting.ContractorsChangeOrder;
import Reports.Accounting.ContractorsPayable_Unbilled;
import Reports.Accounting.ContractorsRetentionsPayables;
import Reports.Accounting.DRFprooflist;
import Reports.Accounting.DailyCRB_entries;
import Reports.Accounting.DailyCollectionReport;
import Reports.Accounting.DailySalesReport;
import Reports.Accounting.DepositListing;
import Reports.Accounting.DepositSlip;
import Reports.Accounting.FinancialReport;
import Reports.Accounting.House_Construction_Status;
import Reports.Accounting.IHFAmortizationCollection;
import Reports.Accounting.JVprooflist;
import Reports.Accounting.ManagementReportSchedule;
import Reports.Accounting.PDCWarehousing_Reports;
import Reports.Accounting.PDCduefortheday;
import Reports.Accounting.PV_without_DV;
import Reports.Accounting.PVprooflist;
import Reports.Accounting.PagibigClientsStatusReportMR;
import Reports.Accounting.PaidOutDisbVoucher;
import Reports.Accounting.RealTimeDebitTransactions;
import Reports.Accounting.ReturnCheckMBTCPcost;
import Reports.Accounting.ReturnCheckMBTCPcost_v2;
import Reports.Accounting.ReturnCheckMBTCTcost;
import Reports.Accounting.ReturnCheckMBTCTcost_v2;
import Reports.Accounting.SOR_ACCOUNTS;
import Reports.Accounting.Sales_Books;
import Reports.Accounting.ScheduleCD_Forfeiture;
import Reports.Accounting.ScheduleOfAdvancesOfficersAndEmployees;
import Reports.Accounting.ScheduleOfAdvancesToBrokers;
import Reports.Accounting.ScheduleOfDPCollectionPmtSchedule;
import Reports.Accounting.Schedule_AP_Others;
import Reports.Accounting.ScheduleofTradeReceivables;
import Reports.Accounting.SubsidiaryLedgerReport;
import Reports.Accounting.SummaryBouncedChecks;
import Reports.Accounting.SummaryOfReturnedChecks;
import Reports.Accounting.SummaryReportOfCollectiblesFromContractors;
import Reports.Accounting.SummaryofDeposits;
import Reports.Accounting.otherReports;
import Reports.Accounting.summaryCashCheck;
import Reports.BiddingandAwarding.ConstructionMonitoring_BAT;
import Reports.BiddingandAwarding.ContractorCreditBalance;
import Reports.BiddingandAwarding.ContractorsANDF;
//import Reports.Accounting.mbtcDocuments;
import Reports.BiddingandAwarding.ContractsMonitoring;
import Reports.BiddingandAwarding.SuretyBondMonitoring;
import Reports.BiddingandAwarding.WeeklyConstructionAccomplishment;
import Reports.BiddingandAwarding.weeklyContractorsAndAwardingMonitoring;
import Reports.BiddingandAwarding.ContractsSequenceMonitoring;
import Reports.COO.BuyerDemographics;
import Reports.COO.LandDevtCostMonitoring;
import Reports.COO.UnitStatusMonitoring_COO;
import Reports.COO.WeeklySalesReport;
import Reports.ClientServicing.ActiveComplaints;
import Reports.ClientServicing.AgingReport;
import Reports.ClientServicing.BankFinance_ClientStatusReport;
import Reports.ClientServicing.HoldUnits;
import Reports.ClientServicing.ListOfDeficientAccounts;
import Reports.ClientServicing.ListofSCD_InTaggedAccounts;
import Reports.ClientServicing.OpenUnits;
import Reports.ClientServicing.PreDocsEvaluationReport;
import Reports.ClientServicing.Refundable_Amenities_Report;
import Reports.ClientServicing.StatusFloating_Commitment_APR_Report;
import Reports.ClientServicing.StatusListing;
import Reports.ClientServicing.SubmittedID;
import Reports.ClientServicing.SwornAffidavitRowHouseReport;
import Reports.ClientServicing.TemporaryReservedUnits;
import Reports.ConstructionManagement.ConstructionMonitoring;
import Reports.ConstructionManagement.ConstructionMonitoringTSD;
import Reports.ConstructionManagement.HouseAccomplishmentCMDApp;
import Reports.ConstructionManagement.constructionManagementReports;
import Reports.CreditAndCollections.AR_CreditPaymentReport;
import Reports.CreditAndCollections.CancellationActiveReport;
import Reports.CreditAndCollections.FSAcctsMonitoring;
import Reports.CreditAndCollections.LoanRealeasedAccountsWithPN;
import Reports.CreditAndCollections.RTSNoticesReport;
import Reports.CreditAndCollections.StatusCancellationAccnts;
import Reports.CreditAndCollections.rptReport;
import Reports.CreditAndCollections.rptReport_v3;
import Reports.LegalAndLiaisoning.DOA_Monitoring;
import Reports.LegalAndLiaisoning.DocumentTransmittalOptions;
import Reports.LegalAndLiaisoning.Form_1904;
import Reports.LegalAndLiaisoning.GTG_TaxClearance;
import Reports.LegalAndLiaisoning.LRA_Forms;
import Reports.LegalAndLiaisoning.LandBankPaymentSlip;
import Reports.LegalAndLiaisoning.Loan_Released_EPEB;
import Reports.LegalAndLiaisoning.PCOSTTCOSTBatchPrinting;
import Reports.LegalAndLiaisoning.PagIbigRemConversionReport;
import Reports.LegalAndLiaisoning.ProcessingCostLiquidationMonitoring;
import Reports.LegalAndLiaisoning.ProcessingTransferCostReport;
import Reports.LegalAndLiaisoning.ProcessingTransferCostReport_v2;
import Reports.LegalAndLiaisoning.REM_Status_List_Report;
import Reports.LegalAndLiaisoning.RPTComputationReport;
import Reports.LegalAndLiaisoning.RevolvingFundsReports;
import Reports.LegalAndLiaisoning.TCTTaxDecForLiquidation;
import Reports.LegalAndLiaisoning.TransferTaxReceipt;
import Reports.LegalAndLiaisoning.TransferTaxDecMonitoring;
import Reports.LegalAndLiaisoning.claimstub;
import Reports.LoansAndReceivable.FI_Reports;
import Reports.LoansAndReceivable.ListOfClientsWithPendingMSVS;
import Reports.LoansAndReceivable.ListOfOfficiallyReservedAccounts;
import Reports.LoansAndReceivable.LoanProcessingAgingReport;
import Reports.LoansAndReceivable.LoanReleasedReport;
import Reports.LoansAndReceivable.MRI_Reports;
import Reports.LoansAndReceivable.NOAReleased;
import Reports.LoansAndReceivable.NoticeToConstructList;
import Reports.LoansAndReceivable.PagibigClientStatusWithNoaNotLR;
import Reports.LoansAndReceivable.PagibigClientsStatusReport;
import Reports.LoansAndReceivable.PagibigClientsStatusReportSummary;
import Reports.LoansAndReceivable.PagibigDocuments;
import Reports.LoansAndReceivable.PagibigNotices;
import Reports.LoansAndReceivable.PagibigReports;
import Reports.LoansAndReceivable.PagibigReports_GtoG;
import Reports.LoansAndReceivable.SORReports;
import Reports.LoansAndReceivable.StatusOfNTCIssuance;
import Reports.LoansAndReceivable.StatusTitleTransfer_FullSettled;
import Reports.PropertyManagement.GeneralAssemblyHOA;
import Reports.PropertyManagement.MeralcoIndividualization_Report;
import Reports.PropertyManagement.UnitStatusListing;
import Reports.PropertyManagement.WaterMeterInstallation_Report;
import Reports.PropertyManagement.pmd_agingreport;
import Reports.PropertyManagement.propertyManagementReports;
//import Reports.LoansAndReceivable.lrmdReports;
import Reports.SalesAndMarketing.ListOfEndorsedATM;
import Reports.SalesAndMarketing.ListOfSellingUnits;
import Reports.SalesAndMarketing.SalesPerformanceRpt;
import Reports.SalesAndMarketing.TrippingReports;
import System.AccessMaintenance;
import System.AddandEditPosition;
import System.CopyorTransferAccess;
import System.DCRF_Reports;
import System.DataChangeRequest;
import System.DcrfInactivePayments;
//import System.DcrfInactivePayments;
import System.DisplayQueuingSystem;
import System.JSystemModule;
import System.ListOfQueueNumbers;
import System.MyQueuingSystem;
import System.Preferences;
import System.TransferJournalEntries;
import System.TransferJournalEntries_iTsReal;
import System.UploadEmployee;
import System.changeBackground;
import TaxesAndPermits.EWT_Remittance;
import TaxesAndPermits.Form2307_Monitoring;
import Utilities.AcctgPeriod;
import Utilities.AddBank;
import Utilities.AddCheckNumber;
import Utilities.AddEditTransactionRemarks;
import Utilities.AddEditTransactionRemarks2;
import Utilities.AddPromo;
import Utilities.AssetInventory;
import Utilities.BankAccounts;
import Utilities.CancelCPF_comm;
import Utilities.CancelHolding;
import Utilities.ChartofAccounts;
import Utilities.Comm_QualifOverride;
import Utilities.Comm_Reprocessing;
import Utilities.Comm_RestructureCommSchedule;
import Utilities.Comm_Sched_Transfer;
import Utilities.Comm_Scheme;
import Utilities.ConstructionAccomplishmentStage;
import Utilities.DateCheckerTest;
import Utilities.DeletePCostTCostEntries;
import Utilities.DeletePostedNTCAccount;
import Utilities.DocumentByBatch;
import Utilities.DriverEntry;
import Utilities.Driver_Vehicles;
import Utilities.EditAvailer;
import Utilities.EditPayee;
import Utilities.EditRevolvingFunds;
import Utilities.EditTaxPayments;
import Utilities.FAD_process_admin;
import Utilities.Generate_Water_Disconnection;
import Utilities.ImportHouseAccomplishments_V2;
import Utilities.InactivateAssets;
import Utilities.LoanReleasedDetails;
import Utilities.LoanReleasedFirstRemittance;
import Utilities.Meeting_Place;
import Utilities.NTP_Notes;
import Utilities.OfficialReceiptReleasing;
import Utilities.PCOST_Retagging;
import Utilities.PCost_Update;
import Utilities.Printed_Documents;
import Utilities.ProForma_Entries;
//import Utilities.ProForma_Entries;
import Utilities.Processing_Cost_Table;
import Utilities.ReceiptPrinting;
import Utilities.ReceiptPrinting_GoodCheck;
import Utilities.ReceiptPrinting_LateLTS_BOI;
import Utilities.ReconOfAsset;
import Utilities.RequiredDocuments;
import Utilities.SalesAgent;
import Utilities.SpecialProjectsMonitoring;
import Utilities.TCOST_Retagging;
import Utilities.TagDisposalForApproval;
import Utilities.Tax_Rate_Discrepancy;
import Utilities.Trip_Purpose;
import Utilities.Tripping_Rate;
import Utilities.UploadChartOfAccounts;
import Utilities.UploadHappyWellCollection;
import Utilities.UploadPayment;
import Utilities.UploadTechnicalDesc;
import Utilities.Water_Reading_Adjustment;
import Utilities.Withoutassetnumber;
import Utilities.WtaxRateTagging;
import Utilities.ZeroOutHOADues;
import Utilities.ZipCodes;
import Utilities.code_break;
import Utilities.fadresp_lateORIssuance;
import Utilities.iutil_payment_template;
import Utilities.jSMS;
import Utilities.jSMS_pool;
import Utilities.otherUnitDescriptions;
import Utilities.salesAgentBranch;
import Utilities.techDescBreaker;
//import Utilities.techDescEntry;
import Window.DesktopScrollPane;
import Window.MDIDesktopPane;
import Window.WindowMenu;
import components.JTBorderFactory;
import components._BorderFactory;
import components._JButton;
import components._JInternalFrame;
import components._JMenuToolbarButton;

@SuppressWarnings("unused")
public class Home_JSystem extends JXFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;

	private static MDIDesktopPane DesktopPane;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JXPanel pnlNorth;
	private JToolBar toolbar;
	private JMenuBar menuBar;

	private JXSearchField searchField;

	private ImageIcon MINIMIZE_ICON = new ImageIcon(this.getClass().getClassLoader().getResource("Images/minimize.png"));
	private ImageIcon MAXIMIZE_ICON = new ImageIcon(this.getClass().getClassLoader().getResource("Images/maximized.png"));
	private ImageIcon CLOSE_ICON = new ImageIcon(this.getClass().getClassLoader().getResource("Images/close.png"));
	//private ImageIcon DELETE_TAB_ICON = new ImageIcon(this.getClass().getClassLoader().getResource("Images/delete-16.png"));

	private _JMenuToolbarButton btnMinimize;
	private _JMenuToolbarButton btnMaximize;
	private _JMenuToolbarButton btnClose;

	private JXPanel pnlSouthCenter;
	private JProgressBar progressBar;
	private JMenu menuBookmark;

	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMMMM dd, yyyy (EEEEE - hh:mm a) ");
	private JColorChooser colorChooser = new JColorChooser(FncLookAndFeel.defaultColor);
	private pnlAnnouncement pnlAnnouncement;

	private pnlDcrf pnlDcrf;
	private Lolo_Dcrf_Checker pnlLolo;
	private Heads_Dcrf_Checker pnlHeadDep;
	private User_Dcrf_Checker pnlUser;
	private DivHead_Dcrf_Checker pnlDivHead;

	private String Image;
	private JXPanel panCutOff; 
	private _JDateChooser[] dteDate; 

	private TimerTask tmr;
	private Timer timerDCRF;

	private JXLabel lblDCRFList; 
	private JXLabel lblDateTime; 
	private Boolean dept_code;

	private static Integer intDCRFCount = 0; 

	private Integer intHomeWidth;
	private Integer intHomeHeight; 

	private DCRFMonitoring_COO2 dcrf; 
	final Toolkit toolkit = Toolkit.getDefaultToolkit();

	private static JLabel lblDCRF; 

	//private User_Queuing pnlQueue;

	public Home_JSystem() throws HeadlessException {
		initGUI();
		DesktopPane.requestFocus();
	}

	public Home_JSystem(GraphicsConfiguration gc) {
		super(gc);
		initGUI();
		DesktopPane.requestFocus();
	}

	public Home_JSystem(String title,String Image) throws HeadlessException {
		super(title);
		this.Image = Image;
		initGUI();
	}

	public Home_JSystem(String title) throws HeadlessException {
		super(title);
		initGUI();
	}

	public Home_JSystem(String title, GraphicsConfiguration gc) {
		super(title, gc);
		initGUI();
		DesktopPane.requestFocus();
	}

	private void initGUI() {
		//System.out.println("Test");
		try {
			this.getContentPane().setLayout(new BorderLayout());
			this.setIconImage(FncLookAndFeel.iconSystem);
			this.setExtendedState(MAXIMIZED_BOTH);

			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(800, 600);

			this.addWindowListener(this);
			this.getRootPane().registerKeyboardAction(this, "Console", KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			{
				ImagePanel pnlMain = new ImagePanel(Image);
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setLayout(new BorderLayout());
				pnlMain.setBorder( BorderFactory.createEmptyBorder());
				{
					pnlNorth = new JXPanel(new BorderLayout());
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(0, 30));
					{
						toolbar = new JToolBar(/*"Pwede", JToolBar.HORIZONTAL*/); 
						pnlNorth.add(toolbar, BorderLayout.CENTER);
						toolbar.setFloatable(false);
						toolbar.setRollover(true);
					}
					{
						searchField = new JXSearchField("Search..."); 
						pnlNorth.add(searchField, BorderLayout.EAST);
						searchField.setBorder(new _BorderFactory(Color.DARK_GRAY, 8));
						searchField.setPreferredSize(new Dimension(250, 0));
						searchField.setMinimumSize(new Dimension(250, 0));
						searchField.setMaximumSize(new Dimension(250, 0));
						searchField.setFindAction(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String text = searchField.getText();

								if(UserInfo.EmployeeCode.equals("901100") == false) {

									_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Clients", "SELECT * FROM view_jsearch('"+ text +"');", false);
									dlg.setPreferredSize(new Dimension(800, 420));
									dlg.setSize(new Dimension(800, 420));
									dlg.setLocationRelativeTo(FncGlobal.homeMDI);
									dlg.setVisible(true);

									Object[] data = dlg.getReturnDataSet();
									if (data != null) {
										String entity_id = (String) data[0];
										String proj_id = (String) data[6];
										String pbl_id = (String) data[3];
										Integer seq_no = (Integer) data[4];

										if(isNotExisting("CARD")){
											CARD c = new CARD(entity_id, proj_id, pbl_id, seq_no);
											addWindow(c, e);
										}
									}
								}
							}
						});

						searchField.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent arg0) {
								if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
									JXSearchField field = ((JXSearchField) arg0.getSource());
									field.getFindButton().doClick();
								}
							}
						});
					}
				}
				{
					{
						DesktopPane = new MDIDesktopPane();
						DesktopPane.setLayout(new AnchorLayout());
						DesktopPane.setOpaque(false);
						DesktopPane.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT); 

						{
							{
								final JPanel panCard = new JPanel(new GridLayout(3, 1, 5, 10)); 
								DesktopPane.add(panCard);
								panCard.setOpaque(false);
								move(panCard, screenSize.width-25, screenSize.height-25, 150, 400); 
								{
									{
										final JLabel lblCard = new JLabel(FncLookAndFeel.iconCARD); 
										panCard.add(lblCard, BorderLayout.CENTER);
										lblCard.setToolTipText("<html><body><h3>Customers Account Record Details</h3></body></html>");
										lblCard.addMouseListener(new MouseListener() {
											public void mouseReleased(MouseEvent e) {

											}

											public void mousePressed(MouseEvent e) {
												lblCard.setBorder(BorderFactory.createLoweredBevelBorder());
												redo();
											}

											public void mouseExited(MouseEvent e) {
												lblCard.setBorder(null);
												redo();
											}

											public void mouseEntered(MouseEvent e) {
												lblCard.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
												redo();
											}

											public void mouseClicked(MouseEvent e) {
												if(isNotExisting("CARD")){
													CARD frmCard = new CARD();
													frmCard.setTitleMenu("CARD");
													DesktopPane.add(frmCard);
													frmCard.moveToFront();

													try {
														frmCard.setSelected(true);
													} catch (PropertyVetoException ex) {
														ex.printStackTrace();
													}

													lblCard.setBorder(null);
													redo();
												}
											}

											void redo() {
												lblCard.repaint();
												lblCard.revalidate();
											}
										});
									}
									{
										final JLabel lblSMS = new JLabel(FncLookAndFeel.iconSMS); 
										panCard.add(lblSMS, BorderLayout.CENTER);
										lblSMS.setToolTipText("<html><body><h3>SMS</h3></body></html>");
										lblSMS.addMouseListener(new MouseListener() {
											public void mouseReleased(MouseEvent e) {

											}

											public void mousePressed(MouseEvent e) {
												lblSMS.setBorder(BorderFactory.createLoweredBevelBorder());
												redo();
											}

											public void mouseExited(MouseEvent e) {
												lblSMS.setBorder(null);
												redo();
											}

											public void mouseEntered(MouseEvent e) {
												lblSMS.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
												redo();
											}

											public void mouseClicked(MouseEvent e) {
												jSMS frmSMS = new jSMS("SMS");
												frmSMS.setTitleMenu("SMS");
												DesktopPane.add(frmSMS);
												frmSMS.moveToFront();

												try {
													frmSMS.setSelected(true);
												} catch (PropertyVetoException ex) {
													ex.printStackTrace();
												}

												lblSMS.setBorder(null);
												redo();
											}

											void redo() {
												lblSMS.repaint();
												lblSMS.revalidate();
											}
										});
									}
									{
										lblDCRF = new JLabel(FncLookAndFeel.iconDCRF); 
										panCard.add(lblDCRF, BorderLayout.CENTER);
										lblDCRF.setToolTipText("<html><body><h3>Data Change Request</h3></body></html>");
										lblDCRF.addMouseListener(new MouseListener() {
											public void mouseReleased(MouseEvent e) {

											}

											public void mousePressed(MouseEvent e) {
												lblDCRF.setBorder(BorderFactory.createLoweredBevelBorder());
												redo();
											}

											public void mouseExited(MouseEvent e) {
												lblDCRF.setBorder(null);
												redo();
											}

											public void mouseEntered(MouseEvent e) {
												lblDCRF.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
												redo();
											}

											public void mouseClicked(MouseEvent e) {
												JOptionPane.showMessageDialog(null, "Quick DCRF feature not yet ready");

												lblDCRF.setBorder(null);
												redo();
											}

											void redo() {
												lblDCRF.repaint();
												lblDCRF.revalidate();
											}
										});
										withPending(); 
									}

									//									{
									//										final JLabel lblDCRF = new JLabel(FncLookAndFeel.iconDCRF); 
									//										panCard.add(lblDCRF, BorderLayout.CENTER);
									//										lblDCRF.setToolTipText("<html><body><h3>Data Change Request</h3></body></html>");
									//										lblDCRF.addMouseListener(new MouseListener() {
									//											public void mouseReleased(MouseEvent e) {
									//
									//											}
									//
									//											public void mousePressed(MouseEvent e) {
									//												lblDCRF.setBorder(BorderFactory.createLoweredBevelBorder());
									//												redo();
									//											}
									//
									//											public void mouseExited(MouseEvent e) {
									//												lblDCRF.setBorder(null);
									//												redo();
									//											}
									//
									//											public void mouseEntered(MouseEvent e) {
									//												lblDCRF.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
									//												redo();
									//											}
									//
									//											public void mouseClicked(MouseEvent e) {
									//												JOptionPane.showMessageDialog(null, "Quick DCRF feature not yet ready");
									//												
									//												lblDCRF.setBorder(null);
									//												redo();
									//											}
									//											
									//											void redo() {
									//												lblDCRF.repaint();
									//												lblDCRF.revalidate();
									//											}
									//										});
									//									}
								}

							}

							if (UserInfo.EmployeeCode.equals("000645") || (deptHead(UserInfo.EmployeeCode) && UserInfo.Department.equals("98"))) {
								dcrf = new DCRFMonitoring_COO2();
								DesktopPane.add(dcrf);
								dcrf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
								move(dcrf, 675, 250, 675, 250); 
								//System.out.println("Dumaan dito sa If");
								//dcrf.setBounds(0, 0, 675, 200);
							}
							else if (!UserInfo.EmployeeCode.equals("000539") && deptHead(UserInfo.EmployeeCode)) {

								pnlAnnouncement  = new pnlAnnouncement();
								DesktopPane.add(pnlAnnouncement,BorderLayout.NORTH);
								move(pnlAnnouncement, 470, screenSize.width, 470, 200); 

								pnlAnnouncement.setToolTipText("<html><font size=2>Minimize");
								pnlAnnouncement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

								//XXX UNCOMMENT IF OK WITH CONNECTION
								pnlHeadDep  = new Heads_Dcrf_Checker();
								DesktopPane.add(pnlHeadDep);
								move(pnlHeadDep, 470, 250, 470, 250); 
								pnlHeadDep.setToolTipText("<html><font size=2>Minimize");
								pnlHeadDep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

								/*ADDED BY JED VICEDO 2021-03-09 : ADDITIONAL JFRAME FOR QUEUING SYSTEM*/
								if(UserInfo.EmployeeCode.equals("900267") || UserInfo.EmployeeCode.equals("987120") || UserInfo.EmployeeCode.equals("900298")) {
									/*pnlQueue  = new User_Queuing();
									DesktopPane.add(pnlQueue);
									//move(pnlQueue, 1290, 330, 600, 300);
									move(pnlQueue, screenSize.width-100, 330, 600, 300);
									System.out.printf("the value screen: %s", screenSize.width);
									pnlQueue.setToolTipText("<html><font size=2>Minimize");
									pnlQueue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));*/
								}
								//System.out.println("1st Else IF");
							} else if (DivHead(UserInfo.EmployeeCode)) {

								pnlAnnouncement  = new pnlAnnouncement();
								DesktopPane.add(pnlAnnouncement,BorderLayout.NORTH);
								move(pnlAnnouncement, 470, screenSize.width, 470, 200); 

								pnlAnnouncement.setToolTipText("<html><font size=2>Minimize");
								pnlAnnouncement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

								//XXX UNCOMMENT IF OK WITH CONNECTION
								pnlDivHead  = new DivHead_Dcrf_Checker();
								DesktopPane.add(pnlDivHead);
								move(pnlDivHead, 470, 250, 470, 250); 
								pnlDivHead.setToolTipText("<html><font size=2>Minimize");
								pnlDivHead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								//System.out.println("2nd else if");
							}else if(UserInfo.ADMIN && UserInfo.EmployeeCode.equals("901128")){

								pnlAnnouncement  = new pnlAnnouncement();
								DesktopPane.add(pnlAnnouncement,BorderLayout.NORTH);
								move(pnlAnnouncement, 470, screenSize.width, 470, 200); 
								pnlAnnouncement.setToolTipText("<html><font size=2>Minimize");
								pnlAnnouncement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

								//								/*ADDED BY JED VICEDO 2021-03-09 : ADDITIONAL JFRAME FOR QUEUING SYSTEM*/
								//								pnlQueue  = new User_Queuing();
								//								DesktopPane.add(pnlQueue);
								//								//move(pnlQueue, 1290, 330, 600, 300);
								//								move(pnlQueue, screenSize.width-100, 330, 600, 300);
								//								System.out.printf("the value screen: %s", screenSize.width);
								//								pnlQueue.setToolTipText("<html><font size=2>Minimize");
								//								pnlQueue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								//System.out.println("3rd else if");
							}else if(UserInfo.ADMIN){

								pnlAnnouncement  = new pnlAnnouncement();
								DesktopPane.add(pnlAnnouncement,BorderLayout.NORTH);
								move(pnlAnnouncement, 470, screenSize.width, 470, 200); 
								pnlAnnouncement.setToolTipText("<html><font size=2>Minimize");
								pnlAnnouncement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								//System.out.println("4th else if");
							}else if(UserInfo.Department.equals("02") || UserInfo.Department.equals("54") || UserInfo.Department.equals("08") || UserInfo.Department.equals("52") || UserInfo.EmployeeCode.equals("900876")){

								pnlAnnouncement  = new pnlAnnouncement();
								DesktopPane.add(pnlAnnouncement,BorderLayout.NORTH);
								move(pnlAnnouncement, 470, screenSize.width, 470, 200); 

								pnlAnnouncement.setToolTipText("<html><font size=2>Minimize");
								pnlAnnouncement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

								//XXX UNCOMMENT IF OK WITH CONNECTION
								/*pnlUser  = new User_Dcrf_Checker();
								DesktopPane.add(pnlUser);
								move(pnlUser, 470, 250, 470, 250); 
								System.out.printf("the value screen: %s", screenSize.width);
								pnlUser.setToolTipText("<html><font size=2>Minimize");
								pnlUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));*/

								/*ADDED BY JED VICEDO 2021-03-09 : ADDITIONAL JFRAME FOR QUEUING SYSTEM*/
								/*pnlQueue  = new User_Queuing();
								DesktopPane.add(pnlQueue);
								//move(pnlQueue, 1290, 330, 600, 300);
								move(pnlQueue, screenSize.width-100, 330, 600, 300);
								System.out.printf("the value screen: %s", screenSize.width);
								pnlQueue.setToolTipText("<html><font size=2>Minimize");
								pnlQueue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));*/
								//System.out.println("5th else if");
							}
							else {

								pnlAnnouncement  = new pnlAnnouncement();
								DesktopPane.add(pnlAnnouncement,BorderLayout.NORTH);
								move(pnlAnnouncement, 470, screenSize.width, 470, 200); 

								pnlAnnouncement.setToolTipText("<html><font size=2>Minimize");
								pnlAnnouncement.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

								//XXX UNCOMMENT IF OK WITH CONNECTION
								/*pnlUser  = new User_Dcrf_Checker();
								DesktopPane.add(pnlUser);
								move(pnlUser, 470, 250, 470, 250); 
								System.out.printf("the value screen: %s", screenSize.width);
								pnlUser.setToolTipText("<html><font size=2>Minimize");
								pnlUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));*/
								//System.out.println("Else");
							}
						}

						DesktopPane.addComponentListener(new ComponentListener() {

							public void componentShown(ComponentEvent e) {

							}

							public void componentResized(ComponentEvent e) {
								intHomeWidth = ((MDIDesktopPane) e.getSource()).getWidth(); 
								intHomeHeight = ((MDIDesktopPane) e.getSource()).getHeight();

								/*if (pnlAnnouncement!=null) {
									move(pnlAnnouncement, 470, intHomeHeight, 470, 400);
								}*/

								if (pnlUser!=null) {
									move(pnlUser, 470, 250, 470, 250);
									//move(pnlUser, 1290, 220, 470, 220);
								}

								/*if (pnlQueue!=null) {
									//move(pnlQueue, 1290, 330, 600, 300);
									move(pnlQueue, screenSize.width-100, 330, 600, 300);
								}*/

								if(UserInfo.EmployeeCode.equals("900449")) {
									if (pnlHeadDep!=null) {
										//									move(pnlHeadDep, 470, 250, 470, 250); 
										//									move(pnlHeadDep, 600, 250, 600, 250); 
										move(pnlHeadDep, 600, 300, 600, 250); 

									}
								}
								else {
									if (pnlHeadDep!=null) {
										//										move(pnlHeadDep, 470, 250, 470, 250); 
										move(pnlHeadDep, 600, 250, 600, 250); 

									}
								}

								if (pnlDivHead!=null) {
									move(pnlDivHead, 470, 250, 470, 250);
								}

								if (dcrf!=null) {
									move(dcrf, 675, 250, 675, 250);
									//dcrf.setBounds(0, 0, 675, 200);
								}
							}

							public void componentMoved(ComponentEvent e) {

							}

							public void componentHidden(ComponentEvent e) {

							}
						});
					}

					DesktopScrollPane scrollCenter = new DesktopScrollPane(DesktopPane);
					pnlMain.add(scrollCenter, BorderLayout.CENTER);
					scrollCenter.setOpaque(false);
					scrollCenter.getViewport().setOpaque(false);
				}
				{
					JXPanel pnlSouth = new JXPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new BorderLayout(1, 1));
					pnlSouth.setPreferredSize(new Dimension(790, 22));
					{
						{
							JXPanel panDivider = new JXPanel(new BorderLayout(5, 5)); 
							pnlSouth.add(panDivider, BorderLayout.LINE_START);

							try {
								panDivider.setPreferredSize(new Dimension(((UserInfo.Department_Alias.equals("JST") || UserInfo.Department_Alias.equals("RDT"))?400:200), 0));
							} catch (NullPointerException ex) {
								panDivider.setPreferredSize(new Dimension(200, 0));
							}

							{
								{
									JXLabel lblUser = new JXLabel(String.format(" %s, %s | %s ", UserInfo.LastName, UserInfo.FirstName, UserInfo.Department_Alias));
									panDivider.add(lblUser, BorderLayout.CENTER);
									lblUser.setFont(FncLookAndFeel.systemFont_Bold.deriveFont(10f));
									lblUser.setBorder(BorderFactory.createLoweredSoftBevelBorder());
									lblUser.setToolTipText("User | Department");

									lblUser.setHorizontalAlignment(JLabel.CENTER);
								}
								{
									lblDCRFList = new JXLabel("");



									try {
										System.out.println("UserInfo.Department_Alias: "+UserInfo.Department_Alias.equals("JST"));

										System.out.println("UserInfo.Department_Alias: "+UserInfo.Department_Alias.equals("JST"));
										if (UserInfo.Department_Alias.equals("JST") || UserInfo.Department_Alias.equals("RDT")) {
											panDivider.add(lblDCRFList, BorderLayout.LINE_END);
										}
									} catch (NullPointerException ex) {

									}

									lblDCRFList.setFont(FncLookAndFeel.systemFont_Bold.deriveFont(10f));
									lblDCRFList.setHorizontalAlignment(JLabel.CENTER);
									lblDCRFList.setBorder(BorderFactory.createLoweredSoftBevelBorder());
									lblDCRFList.setToolTipText("Server | Date");
									Blink(lblDCRFList);
									lblDCRFList.setPreferredSize(new Dimension(200, 0));
									lblDCRFList.addMouseListener(new MouseListener() {
										public void mouseReleased(MouseEvent e) {

										}

										public void mousePressed(MouseEvent e) {

										}

										public void mouseExited(MouseEvent e) {

										}

										public void mouseEntered(MouseEvent e) {

										}

										public void mouseClicked(MouseEvent e) {
											JDialog dialog = new JDialog(FncGlobal.homeMDI, "DCRF Monitoring", false); 
											dialog.setLayout(new BorderLayout(0, 0));

											final Toolkit toolkit = Toolkit.getDefaultToolkit();
											final Dimension screenSize = toolkit.getScreenSize();


//											if(UserInfo.EmployeeCode.equals("900876")) {
//												dialog.setSize(screenSize.width-1500, screenSize.height-50);
//												dialog.setResizable(true);
//											}else {
												dialog.setSize(screenSize.width-100, screenSize.height-100);
												dialog.setResizable(false);
											//}


											/*
											DCRFMonitoring2 dcrfMon = new DCRFMonitoring2();
											JScrollPane scr = new JScrollPane(dcrfMon); 
											dialog.add(scr, BorderLayout.CENTER);
											 */

											DCRFMonitoring3 dcrfMon = new DCRFMonitoring3();
											//JScrollPane scr = new JScrollPane(dcrfMon); 
											dialog.add(dcrfMon, BorderLayout.CENTER);

											int x = 0;
											int y = 0;

											if(UserInfo.EmployeeCode.equals("900876")) {
												x = ((screenSize.width - dialog.getWidth()) / 4);
												y = ((screenSize.height - dialog.getHeight()) / 4);
											}else {
												x = ((screenSize.width - dialog.getWidth()) / 2);
												y = ((screenSize.height - dialog.getHeight()) / 2);
											}
											System.out.println("");
											System.out.println("((screenSize.width - dialog.getWidth()) / 2): "+((screenSize.width - dialog.getWidth()) / 2));
											System.out.println("((screenSize.height - dialog.getHeight()) / 2): "+((screenSize.height - dialog.getHeight()) / 2));

											dialog.setBounds(x, y, dialog.getWidth(), dialog.getHeight());
											dialog.setVisible(true);
										}
									});
								}
							}
						}

						{
							pnlSouthCenter = new JXPanel();
							pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
							pnlSouthCenter.setLayout(new BorderLayout());
							//pnlSouthCenter.setOpaque(false);
							pnlSouthCenter.setBorder(BorderFactory.createLoweredSoftBevelBorder());
							{
								progressBar = new JProgressBar(); 
								pnlSouthCenter.add(progressBar, BorderLayout.CENTER);
								progressBar.setVisible(false);

								ActionMap mapAction = new ActionMap();
								mapAction.put(JProgressBar.UNDEFINED_CONDITION, new AbstractAction() {
									private static final long serialVersionUID = 1L;

									public void actionPerformed(ActionEvent e) {
										System.out.println("**********Sample ActionMap!");
									}
								});
							}
						}
						{
							JXPanel panDateAndCutOff = new JXPanel(new BorderLayout(5, 5)); 
							pnlSouth.add(panDateAndCutOff, BorderLayout.LINE_END);
							panDateAndCutOff.setPreferredSize(new Dimension(425, 0));
							{
								{
									lblDateTime = new JXLabel(String.format(" %s ", dateTimeFormat.format(Calendar.getInstance().getTime())));
									panDateAndCutOff.add(lblDateTime, BorderLayout.CENTER);
									lblDateTime.setFont(FncLookAndFeel.systemFont_Bold.deriveFont(10f));
									lblDateTime.setHorizontalAlignment(JLabel.CENTER);
									lblDateTime.setBorder(BorderFactory.createLoweredSoftBevelBorder());
									lblDateTime.setToolTipText("Server | Date");
									setTime(lblDateTime);
								}
								{
									JXPanel panCutOffControl = new JXPanel(new BorderLayout(5, 5)); 
									panDateAndCutOff.add(panCutOffControl, BorderLayout.LINE_END);
									panCutOffControl.setBorder(BorderFactory.createLoweredSoftBevelBorder());
									panCutOffControl.setPreferredSize(new Dimension(100, 0));
									{
										JLabel lblCutOff = new JLabel("View Cut-Off"); 
										panCutOffControl.add(lblCutOff, BorderLayout.CENTER); 
										lblCutOff.setFont(FncLookAndFeel.systemFont_Bold.deriveFont(10f));
										lblCutOff.setHorizontalAlignment(JLabel.CENTER);
										lblCutOff.addMouseListener(new MouseListener() {
											public void mouseReleased(MouseEvent e) {

											}

											public void mousePressed(MouseEvent e) {

											}

											public void mouseExited(MouseEvent e) {

											}

											public void mouseEntered(MouseEvent e) {

											}

											public void mouseClicked(MouseEvent e) {
												CreateCutOffControl();
												JOptionPane.showOptionDialog(getContentPane(), panCutOff, "Cut-Off", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {"			CONFIRM			"}, JOptionPane.OK_OPTION);
											}
										});
									}
								}
							}
						}
					}
				}
			}
			{
				menuBar = new JMenuBar(); 
				setJMenuBar(menuBar);
				{
					JMenu menuFile = new JMenu("File");
					menuBar.add(menuFile);
					menuFile.setMnemonic(KeyEvent.VK_F);
					{
						JMenuItem menuitemOptions = new JMenuItem("Options");
						menuFile.add(menuitemOptions);
						menuitemOptions.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
									public void stateChanged(ChangeEvent e) {
										//pnlMain.setBackgroundPainter(FncPanelPainter.paint(colorChooser.getColor()));
										//pnlMain.repaint();
									}
								});

								ActionListener actionOK = new ActionListener() {
									public void actionPerformed(ActionEvent event) {
										//FncLookAndFeel.windowString_Dark = colorChooser.getColor();
										Color selectedColor = colorChooser.getColor();

										if(selectedColor == FncLookAndFeel.defaultColor){
											FncLookAndFeel.setDefaultColor();
											return;
										}
										int red = selectedColor.getRed();
										int green = selectedColor.getGreen();
										int blue = selectedColor.getBlue();

										FncLookAndFeel.setColor(red, green, blue);
									}
								};

								ActionListener actionCANCEL = new ActionListener() {
									public void actionPerformed(ActionEvent event) {
										//pnlMain.setBackgroundPainter(FncPanelPainter.paint(FncLookAndFeel.grayColor));
										//pnlMain.repaint();
									}
								};

								JDialog dialog = JColorChooser.createDialog(FncGlobal.homeMDI, "Options", true, colorChooser, actionOK, actionCANCEL);
								dialog.setVisible(true);
							}
						});
					}
					{

						JMenuItem menuitemChangeBackground = new JMenuItem("Change Background");
						menuFile.add(menuitemChangeBackground);
						menuitemChangeBackground.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								if(isNotExisting("changeBackground")){
									changeBackground cb = new changeBackground();
									addWindow(cb, e);
								}
							}
						});

					}
					{
						JMenuItem menuitemChangePassword = new JMenuItem("Change Password");
						menuFile.add(menuitemChangePassword);
						menuitemChangePassword.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("ChangePassword")){
									ChangePassword cp = new ChangePassword();
									addWindow(cp, e);

									int width = (getWidth() - cp.getWidth()) / 2;
									int height = (getHeight() - cp.getHeight()) / 3;
									cp.setLocation(width, height);
								}
							}
						});
					}
					{
						JMenuItem menuitemUserManual = new JMenuItem("User Manual");
						menuFile.add(menuitemUserManual);
						menuitemUserManual.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if (Desktop.isDesktopSupported()) {
									InputStream jarPdf = getClass().getClassLoader().getResourceAsStream("File/JSystem Beginners Guide.pdf");

									try {
										File pdfTemp = File.createTempFile("User_Guide", ".pdf");
										pdfTemp.deleteOnExit();

										FileOutputStream fos = new FileOutputStream(pdfTemp);
										while (jarPdf.available() > 0) {
											fos.write(jarPdf.read());
										} // while (pdfInJar.available() > 0)
										fos.close();
										Desktop.getDesktop().open(pdfTemp);
									} // try

									catch (IOException ex) {
									} // catch (IOException e)
								}
							}
						});
					}
					{
						menuFile.add(new JSeparator());
					}
					{
						JMenuItem menuitemLogout = new JMenuItem("Logout");
						menuFile.add(menuitemLogout);
						menuitemLogout.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								for(JInternalFrame comp : DesktopPane.getAllFrames()){
									System.out.println(comp.getClass().getSimpleName());
								}
								System.out.println("Dumaan dito sa Logout");
								if(JOptionPane.showConfirmDialog(Home_JSystem.this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION){
									pgSelect db = new pgSelect();
									String SQL = "SELECT sp_audit_log_details('"+UserInfo.EmployeeCode+"', false)";
									db.select(SQL);
									System.exit(0);
									System.out.println("Dumaan dito sa System Exit");
								}
							}
						});
					}
					{
						JMenuItem menuitemExit = new JMenuItem("Exit");
						menuFile.add(menuitemExit);
						menuitemExit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								pgSelect db = new pgSelect();
								String SQL = "SELECT sp_audit_log_details('"+UserInfo.EmployeeCode+"', false)";
								db.select(SQL);
								System.exit(0);
							}
						});
					}
				}
				{
					JMenu menuTransaction = new JMenu("Transaction");
					menuBar.add(menuTransaction);
					menuTransaction.setMnemonic(KeyEvent.VK_T);
					{
						JMenu menuAccounting = new JMenu("Accounting");
						menuTransaction.add(menuAccounting);
						{
							JMenu menuCashiering = new JMenu("Cashiering");
							menuAccounting.add(menuCashiering);
							{
								JMenuItem menuitemReceiptDetails = new JMenuItem("Receipt Details");
								menuCashiering.add(menuitemReceiptDetails);
								menuitemReceiptDetails.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CashReceiptBook")){
											CashReceiptBook crb = new CashReceiptBook();
											addWindow(crb, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemIssueanceOfReceipt = new JMenuItem("Issuance of Receipt");
								menuCashiering.add(menuitemIssueanceOfReceipt);
								menuitemIssueanceOfReceipt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("IssuanceOfReceipt")){
											IssuanceOfReceipt ior = new IssuanceOfReceipt();
											addWindow(ior, e);
										}
									}
								});
							}
							if(UserInfo.ADMIN){
								{
									JMenuItem menuitemIssueanceOfReceipt2 = new JMenuItem("Issuance of Receipt 2");
									menuCashiering.add(menuitemIssueanceOfReceipt2);
									menuitemIssueanceOfReceipt2.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("IssuanceOfReceipt")){
												IssuanceOfReceipt_v2 ior2 = new IssuanceOfReceipt_v2();
												addWindow(ior2, e);
											}
										}
									});
								}
							}
							{
								JMenuItem menuitemRTDPosting = new JMenuItem("Real-Time Debit Posting");
								menuCashiering.add(menuitemRTDPosting);
								menuitemRTDPosting.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RealTimeDebitPosting")){
											RealTimeDebitPosting rtdp = new RealTimeDebitPosting();
											addWindow(rtdp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRTDPosting = new JMenuItem("Real-Time Debit Posting(Loan Released)");
								menuCashiering.add(menuitemRTDPosting);
								menuitemRTDPosting.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RealTimeDebitPosting_LoanReleased")){
											RealTimeDebitPosting_LoanReleased rtdpLR = new RealTimeDebitPosting_LoanReleased();
											addWindow(rtdpLR, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCashCount = new JMenuItem("Cash Count");
								menuCashiering.add(menuitemCashCount);
								menuitemCashCount.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CashCountSummary")){
											CashCountSummary cash_count = new CashCountSummary();
											addWindow(cash_count, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCashCount2 = new JMenuItem("Cash Count 2");
								menuCashiering.add(menuitemCashCount2);
								menuitemCashCount2.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CashCountSummary")){
											CashCountSummary2 cash_count2 = new CashCountSummary2();
											addWindow(cash_count2, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemUnidenDeposits = new JMenuItem("Direct Deposits");
								menuCashiering.add(menuitemUnidenDeposits);
								menuitemUnidenDeposits.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Unidentified_Identified_Deposits")){
											Unidentified_Identified_Deposits unident_iden_dep = new Unidentified_Identified_Deposits();
											addWindow(unident_iden_dep, e);
										}
									}
								});
							}
							if(UserInfo.EmployeeCode.equals("900876")){
								JMenuItem menuitemUnidenDeposits_v2 = new JMenuItem("Direct Deposits 2");
								menuCashiering.add(menuitemUnidenDeposits_v2);
								menuitemUnidenDeposits_v2.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Unidentified_Identified_Deposits_v2")){
											Unidentified_Identified_Deposits_v2 unident_iden_dep_v2 = new Unidentified_Identified_Deposits_v2();
											addWindow(unident_iden_dep_v2, e);
										}
									}
								});
							}
							{

								JMenuItem menuitemIssueOR_toGoodChecks = new JMenuItem("Issuance of OR for Good Checks");
								menuCashiering.add(menuitemIssueOR_toGoodChecks);
								menuitemIssueOR_toGoodChecks.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("IssueOR_toGoodChecks")){
											IssueOR_toGoodChecks dep = new IssueOR_toGoodChecks();
											addWindow(dep, e);
										}
									}
								});
							}		
							{

								JMenuItem menuitemIssueOR_LateLTS = new JMenuItem("Issuance of OR - Late LTS");
								menuCashiering.add(menuitemIssueOR_LateLTS);
								menuitemIssueOR_LateLTS.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("IssueOR_toLateLTS")){
											IssueOR_toLateLTS dep = new IssueOR_toLateLTS();
											addWindow(dep, e);
										}
									}
								});
							}
//							{
//
//								JMenuItem menuitemDirectDepositAssignment = new JMenuItem("Direct Deposit Assignment");
//								menuCashiering.add(menuitemDirectDepositAssignment);
//								menuitemDirectDepositAssignment.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("IssueOR_toLateLTS")){
//											DirectDepositAssignment dep = new DirectDepositAssignment();
//											addWindow(dep, e);
//										}
//									}
//								});
//							}
//							{
//
//								JMenuItem menuitemUDIssuance = new JMenuItem("Direct Deposit Issuance");
//								menuCashiering.add(menuitemUDIssuance);
//								menuitemUDIssuance.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("IssueOR_toLateLTS")){
//											ud_tag udtag = new ud_tag();
//											addWindow(udtag, e);
//										}
//									}
//								});
//							}	
							{

								JMenuItem menuitemLateOrAutoJv = new JMenuItem("Late OR Auto JV");
								menuCashiering.add(menuitemLateOrAutoJv);
								menuitemLateOrAutoJv.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LateOrAutoJv")){
											LateOrAutoJv loa = new LateOrAutoJv();
											addWindow(loa, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemIssuanceMontalban = new JMenuItem("Issuance Of Receipt(Montalban)");
								menuCashiering.add(menuitemIssuanceMontalban);
								menuitemIssuanceMontalban.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("IssuanceOfReceipt_Montalban")) {
											IssuanceOfReceipt_Montalban iorm = new IssuanceOfReceipt_Montalban();
											addWindow(iorm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemGarbageFee = new JMenuItem("Garbage Fee");
								menuCashiering.add(menuitemGarbageFee);
								menuitemGarbageFee.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("GarbageFee")) {
											GarbageFee gf = new GarbageFee();
											addWindow(gf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLoanReleasedIssuance = new JMenuItem("Loan Released Issuance");
								menuCashiering.add(menuitemLoanReleasedIssuance);
								menuitemLoanReleasedIssuance.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LoanReleasedIssuance")) {
											LoanReleasedIssuance lri = new LoanReleasedIssuance();
											addWindow(lri, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAUBUploadPayments = new JMenuItem("Upload AUB Payments");
								menuCashiering.add(menuitemAUBUploadPayments);
								menuitemAUBUploadPayments.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UploadAUBPayments")) {
											UploadAUBPayments uap = new UploadAUBPayments();
											addWindow(uap);
										}
									}
								});
							}
						}						
						{
							JMenu menuCollections = new JMenu("Collections");
							menuAccounting.add(menuCollections);
							{
								JMenuItem menuitemCRBposting = new JMenuItem("Buyers Payment Posting");
								menuCollections.add(menuitemCRBposting);
								menuitemCRBposting.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BuyersPaymentPosting")){
											BuyersPaymentPosting bpp = new BuyersPaymentPosting();
											addWindow(bpp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBuyersPmtLedger = new JMenuItem("Buyers Payment Ledger");
								menuCollections.add(menuitemBuyersPmtLedger);
								menuitemBuyersPmtLedger.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BuyersLedger")){
											BuyersLedger bpl = new BuyersLedger();
											addWindow(bpl, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckMonitoring = new JMenuItem("Check Status Monitoring");
								menuCollections.add(menuitemCheckMonitoring);
								menuitemCheckMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CheckStatusMonitoring")){
											CheckStatusMonitoring csm = new CheckStatusMonitoring();
											addWindow(csm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckMonitoringFloating = new JMenuItem("Check Status Monitoring (Floating Payments)");
								menuCollections.add(menuitemCheckMonitoringFloating);
								menuitemCheckMonitoringFloating.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("checkStatusMonitoring_others")){
											checkStatusMonitoring_others cmo = new checkStatusMonitoring_others();
											addWindow(cmo, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckReplacement = new JMenuItem("Check Replacement");
								menuCollections.add(menuitemCheckReplacement);
								menuitemCheckReplacement.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CheckReplacement")){
											CheckReplacement crp = new CheckReplacement();
											addWindow(crp, e);
										}
									}
								});
							}
							{

								JMenuItem menuitemDeposit = new JMenuItem("Deposits");
								menuCollections.add(menuitemDeposit);
								menuitemDeposit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Deposits")){
											Deposits dep = new Deposits();
											addWindow(dep, e);
										}
									}
								});
							}
							if(UserInfo.ADMIN){
								JMenuItem menuitemDeposit = new JMenuItem("Deposits 2");
								menuCollections.add(menuitemDeposit);
								menuitemDeposit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Deposits")){
											Deposits_v2 dep2 = new Deposits_v2();
											addWindow(dep2, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRetentionFee = new JMenuItem("Retention Fee");
								menuCollections.add(menuitemRetentionFee);
								menuitemRetentionFee.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RetentionFee")){
											RetentionFee retFee = new RetentionFee();
											addWindow(retFee, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRetentionFeeBOI = new JMenuItem("Retention Fee (BOI)");
								menuCollections.add(menuitemRetentionFeeBOI);
								menuitemRetentionFeeBOI.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RetentionFeeBOI")){
											RetentionFeeBOI retFee = new RetentionFeeBOI();
											addWindow(retFee, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRetentionOnline = new JMenuItem("Retention Fee(Online)");
								menuCollections.add(menuitemRetentionOnline);
								menuitemRetentionOnline.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RetentionFee_Online")) {
											RetentionFee_Online retfee_ol = new RetentionFee_Online();
											addWindow(retfee_ol, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRetOnMultiplePayments = new JMenuItem("RetFee Online (Multiple Payments)");
								menuCollections.add(menuitemRetOnMultiplePayments);
								menuitemRetOnMultiplePayments.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RetFeeOnlineMultiplePayments")) {
											RetFeeOnlineMultiplePayments retfee_olmp = new RetFeeOnlineMultiplePayments(); 
											addWindow(retfee_olmp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBankFinancingLonaTakeout = new JMenuItem("Bank Financing Loan Takeout");
								menuCollections.add(menuitemBankFinancingLonaTakeout);
								menuitemBankFinancingLonaTakeout.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BankFinancingLoanTakeout")){
											BankFinancingLoanTakeout bflt = new BankFinancingLoanTakeout();
											addWindow(bflt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckStatusMonitoringPDCWarehousing = new JMenuItem("Check Status Monitoring (PDC Warehousing)");
								menuCollections.add(menuitemCheckStatusMonitoringPDCWarehousing);
								menuitemCheckStatusMonitoringPDCWarehousing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("checkStatusMonitoring_pdcWarehousing")){
											checkStatusMonitoring_pdcWarehousing csm_pdc = new checkStatusMonitoring_pdcWarehousing();
											addWindow(csm_pdc, e);
										}
									}
								});
							}
						}
						{
							JMenu menuCommission = new JMenu("Commission");
							menuAccounting.add(menuCommission);

							{
								JMenuItem menuitemATM_processing = new JMenuItem("ATM Processing");
								menuCommission.add(menuitemATM_processing);
								menuitemATM_processing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ATM_processing")){
											ATM_processing atm_proc = new ATM_processing();
											addWindow(atm_proc, e);
										}
									}
								});
							}	
							{
								JMenuItem menuitemCommSchedGenerator = new JMenuItem("Commission Schedule Generator");
								menuCommission.add(menuitemCommSchedGenerator);
								menuitemCommSchedGenerator.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Commission_Sample_Schedule_Generator")){
											Commission_Schedule_Generator comm_sched = new Commission_Schedule_Generator();
											addWindow(comm_sched, e);
										}
									}
								});
							}	
							{
								JMenuItem menuitemAgentInquiry = new JMenuItem("Commission Inquiry");
								menuCommission.add(menuitemAgentInquiry);
								menuitemAgentInquiry.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CommissionInquiry")){
											CommissionInquiry comm_inq = new CommissionInquiry();
											addWindow(comm_inq, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommQualify = new JMenuItem("Qualify Commission");
								menuCommission.add(menuitemCommQualify);
								menuitemCommQualify.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("QualifiedCommission")){
											QualifiedCommission comm_qual = new QualifiedCommission();
											addWindow(comm_qual, e);
										}
									}
								});
							}	
							{
								JMenuItem menuitemCommProcess = new JMenuItem("Process Commission");
								menuCommission.add(menuitemCommProcess);
								menuitemCommProcess.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ProcessCommission")){
											ProcessCommission proc_comm = new ProcessCommission();
											addWindow(proc_comm, e);
										}
									}
								});
							}
//							{
//								JMenuItem menuitemPromoManualEntry = new JMenuItem("Promo / Incentive Manual Entry");
//								menuCommission.add(menuitemPromoManualEntry);
//								menuitemPromoManualEntry.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("Promo_Incentive_ManualProcessing")){
//											Promo_Incentive_ManualProcessing promo_manual = new Promo_Incentive_ManualProcessing();
//											addWindow(promo_manual, e);
//										}
//									}
//								});
//							}
//							if(UserInfo.ADMIN){
								{
									JMenuItem menuitemPromoManualEntry2 = new JMenuItem("Promo / Incentive Manual Entry");
									menuCommission.add(menuitemPromoManualEntry2);
									menuitemPromoManualEntry2.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("Promo_Incentive_ManualProcessing")){
												Promo_Incentive_ManualProcessing_v2 promo_manual2 = new Promo_Incentive_ManualProcessing_v2();
												addWindow(promo_manual2, e);
											}
										}
									});
								}
							//}
							{
								JMenuItem menuitemReleaseCommThruATM = new JMenuItem("Release Commission Thru ATM");
								menuCommission.add(menuitemReleaseCommThruATM);
								menuitemReleaseCommThruATM.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ReleaseCommThruATM")){
											ReleaseCommThruATM rel_comm_atm = new ReleaseCommThruATM();
											addWindow(rel_comm_atm, e);
										}
									}
								});
							}							
						}
						{
							JMenu menuContractors = new JMenu("Contractors");
							menuAccounting.add(menuContractors);
							{
								JMenuItem menuitemContractorsBilling = new JMenuItem("Contractors Progress Billing");
								menuContractors.add(menuitemContractorsBilling);
								menuitemContractorsBilling.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorsBilling")){
											ContractorsBilling cont_bill = new ContractorsBilling();
											addWindow(cont_bill, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemChangeOrder = new JMenuItem("Change Order");
								menuContractors.add(menuitemChangeOrder);
								menuitemChangeOrder.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ChangeOrder")){
											ChangeOrder ch_order = new ChangeOrder();
											addWindow(ch_order, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemHouseRepair = new JMenuItem("House Repair");
								menuContractors.add(menuitemHouseRepair);
								menuitemHouseRepair.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HouseRepair")){
											HouseRepair ch_order = new HouseRepair();
											addWindow(ch_order, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBachargeUtilities = new JMenuItem("BackCharge Utilities");
								menuContractors.add(menuitemBachargeUtilities);
								menuitemBachargeUtilities.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BackchargeUtilities")){
											BackchargeUtilities bu = new BackchargeUtilities();
											addWindow(bu, e);
										}
									}
								});
							}
						}
						{
							JMenu menuDisbursement = new JMenu("Disbursements");
							menuAccounting.add(menuDisbursement);
							{
								JMenuItem menuitemRequestForPayment = new JMenuItem("Request for Payments");
								menuDisbursement.add(menuitemRequestForPayment);
								menuitemRequestForPayment.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RequestForPayment")){
											RequestForPayment drf = new RequestForPayment();
											addWindow(drf, e);
										}
									}
								});
							}
							if(UserInfo.ADMIN){
								{
									JMenuItem menuitemRequestForPayment = new JMenuItem("Request for Payments 2");
									menuDisbursement.add(menuitemRequestForPayment);
									menuitemRequestForPayment.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RequestForPaymentv2")){
												RequestForPaymentv2 drf2 = new RequestForPaymentv2();
												addWindow(drf2, e);
											}
										}
									});
								}
							}
							{
								JMenuItem menuitemPayableVoucher = new JMenuItem("Payable Voucher");
								menuDisbursement.add(menuitemPayableVoucher);
								menuitemPayableVoucher.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PayableVoucher")){
											PayableVoucher pv = new PayableVoucher();
											addWindow(pv, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckVoucher = new JMenuItem("Check Voucher");
								menuDisbursement.add(menuitemCheckVoucher);
								menuitemCheckVoucher.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {										
										if(isNotExisting("CheckVoucher")){
											CheckVoucher chk_vchr = new CheckVoucher();
											addWindow(chk_vchr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckVoucherMC = new JMenuItem("Check Voucher (MC)");
								menuDisbursement.add(menuitemCheckVoucherMC);
								menuitemCheckVoucherMC.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {										
										if(isNotExisting("CheckVoucherMC")){
											CheckVoucherMC chk_vchr = new CheckVoucherMC();
											addWindow(chk_vchr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemChangePaymentType = new JMenuItem("Change Payment Type of Posted PV");
								menuDisbursement.add(menuitemChangePaymentType);
								menuitemChangePaymentType.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ChangePaymentType")){
											ChangePaymentType drf = new ChangePaymentType();
											addWindow(drf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDocsProcessing = new JMenuItem("Documents Processing");
								menuDisbursement.add(menuitemDocsProcessing);
								menuitemDocsProcessing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {										
										if(isNotExisting("DocsProcessing")){
											DocsProcessing doc_proc = new DocsProcessing();
											addWindow(doc_proc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDocsProcessing_v2 = new JMenuItem("Documents Processing (Reform)");
								menuDisbursement.add(menuitemDocsProcessing_v2);
								menuitemDocsProcessing_v2.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {										
										if(isNotExisting("DocsProcessing_v2")){
											DocsProcessing_v2 doc_proc = new DocsProcessing_v2();
											addWindow(doc_proc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemEPayment_for_AUB = new JMenuItem("ePayment - For AUB (Uploading)");
								menuDisbursement.add(menuitemEPayment_for_AUB);
								menuitemEPayment_for_AUB.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ePaymentForAUB")){
											ePaymentForAUB for_aub = new ePaymentForAUB();
											addWindow(for_aub, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemEPayment_from_AUB = new JMenuItem("ePayment - For AUB (Downloading)");
								menuDisbursement.add(menuitemEPayment_from_AUB);
								menuitemEPayment_from_AUB.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ePaymentFromAUB")){
											ePaymentFromAUB from_aub = new ePaymentFromAUB();
											addWindow(from_aub, e);
										}
									}
								});

							}
							{
								JMenuItem menuitemDocsProcessing = new JMenuItem("AUB Status Tagging/Monitoring");
								menuDisbursement.add(menuitemDocsProcessing);
								menuitemDocsProcessing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {										
										if(isNotExisting("AUB_Status_Monitoring")){
											AUB_Status_Monitoring aub_status = new AUB_Status_Monitoring();
											addWindow(aub_status, e);
										}
									}
								});
							}

							{
								JMenuItem menuitemMCnumverCV = new JMenuItem("Tag MC number - Check Voucher");
								menuDisbursement.add(menuitemMCnumverCV);
								menuitemMCnumverCV.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {										
										if(isNotExisting("Tag MC number - Check Voucher")){
											TagMCCVnumber tagmccvn = new TagMCCVnumber();
											addWindow(tagmccvn, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemreversal_proc = new JMenuItem("Reversal Processing");
								menuDisbursement.add(menuitemreversal_proc);
								menuitemreversal_proc.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {										
										if(isNotExisting("DocsProcessing")){
											reversalProccessing rev_proc = new reversalProccessing();
											addWindow(rev_proc, e);
										}
									}
								});
							}
						}
						{
							JMenu menuFixedAssets = new JMenu("Fixed Assets");
							menuAccounting.add(menuFixedAssets);
							{
								{
									JMenuItem menuitemAssetMonitoring = new JMenuItem("Asset Monitoring");
									menuFixedAssets.add(menuitemAssetMonitoring);
									menuitemAssetMonitoring.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("AssetMonitoring")){
												AssetMonitoring am = new AssetMonitoring();
												addWindow(am, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemAddItem = new JMenuItem("Add Item");
									menuFixedAssets.add(menuitemAddItem);
									menuitemAddItem.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("AddItem")){
												AddItem ai = new AddItem(); 
												addWindow(ai, e);
											}
										}
									});
								}
								{
									JMenuItem menuaddsupplier = new JMenuItem("Add Supplier");
									menuFixedAssets.add(menuaddsupplier);
									menuaddsupplier.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(isNotExisting("addSupplier")){
												addSupplier as= new addSupplier();
												addWindow(as,arg0);
											}
										}
									});
								}
								{
									JMenuItem menuaddprocurement = new JMenuItem("Procurement");
									menuFixedAssets.add(menuaddprocurement);
									menuaddprocurement.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(isNotExisting("procurement")){
												procurement proc= new procurement();
												addWindow(proc,arg0);
											}
										}
									});
								}
								{
									JMenuItem menuaddcanvassing = new JMenuItem("Canvassing");
									menuFixedAssets.add(menuaddcanvassing);
									menuaddcanvassing.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(isNotExisting("canvassing")){
												canvassing canvass= new canvassing();
												addWindow(canvass,arg0);
											}
										}
									});
								}
								{
									JMenuItem menuaddsupplier_v2= new JMenuItem("Supplier");
									menuFixedAssets.add(menuaddsupplier_v2);
									menuaddsupplier_v2.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(isNotExisting("Supplier")){
												Supplier supp= new Supplier();
												addWindow(supp,arg0);
											}
										}
									});
								}
								{
									JMenuItem menuaddissuanceofsupplies= new JMenuItem("Issuance Of Supplies");
									menuFixedAssets.add(menuaddissuanceofsupplies);
									menuaddissuanceofsupplies.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(isNotExisting("IssuanceOfSupplies")){
												IssuanceOfSupplies issue_supplies= new IssuanceOfSupplies();
												addWindow(issue_supplies,arg0);
											}
										}
									});
								}
								{
									JMenuItem menuaddrequest_stationay_supplies= new JMenuItem("Request Stationary and Supplies");
									menuFixedAssets.add(menuaddrequest_stationay_supplies);
									menuaddrequest_stationay_supplies.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(isNotExisting("Request_Stationary_supplies")){
												Request_Stationary_supplies req_stationay_supplies= new Request_Stationary_supplies();
												addWindow(req_stationay_supplies,arg0);
											}
										}
									});
								}
								{
									JMenuItem menuaddscrap_monitoring= new JMenuItem("Scrap Monitoring");
									menuFixedAssets.add(menuaddscrap_monitoring);
									menuaddscrap_monitoring.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(isNotExisting("scrap_monitoring")){
												scrap_monitoring scrapmonitoring= new scrap_monitoring();
												addWindow(scrapmonitoring,arg0);
											}
										}
									});
								}
								{
									JMenu menuNonFixedAsset = new JMenu("Non Fixed Asset");
									menuAccounting.add(menuNonFixedAsset);
									{
										JMenuItem menuitemNonFixedAsset = new JMenuItem("GAE(Charge To Direct Expense)");
										menuNonFixedAsset.add(menuitemNonFixedAsset);
										menuitemNonFixedAsset.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												if(isNotExisting("NonFixedAsset")) {
													NonFixedAsset nfa = new NonFixedAsset();
													addWindow(nfa, e);
												}
											}
										});
									}
								}

							}

						}
						{
							JMenu menuGL = new JMenu("General Ledger");
							menuAccounting.add(menuGL);
							{
								JMenuItem menuitemGL = new JMenuItem("General Ledger");
								menuGL.add(menuitemGL);
								menuitemGL.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("GeneralLedger")){
											GeneralLedger gl = new GeneralLedger();
											addWindow(gl, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemJV = new JMenuItem("Journal Voucher");
								menuGL.add(menuitemJV);
								menuitemJV.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("JournalVoucher")){
											JournalVoucher jv = new JournalVoucher();
											addWindow(jv, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTB = new JMenuItem("Trial Balance");
								menuGL.add(menuitemTB);
								menuitemTB.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TrialBalance")){
											TrialBalance trial_bal = new TrialBalance();
											addWindow(trial_bal, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTB_new = new JMenuItem("Trial Balance (with Breakdown)");
								menuGL.add(menuitemTB_new);
								menuitemTB_new.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TrialBalance_wBDown")){
											TrialBalance_wBDown trial_bal_v2 = new TrialBalance_wBDown();
											addWindow(trial_bal_v2, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDCM = new JMenuItem("Debit/Credit Memo");
								menuGL.add(menuitemDCM);
								menuitemDCM.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DebitCreditMemo")){
											DebitCreditMemo deb_cre_memo = new DebitCreditMemo();
											addWindow(deb_cre_memo, e);
										}
									}
								});
							}
						}
						{
							JMenu menuLiquidation = new JMenu("Liquidation");
							menuAccounting.add(menuLiquidation);
							{
								JMenuItem menuitemLiquidation = new JMenuItem("CA Liquidation");
								menuLiquidation.add(menuitemLiquidation);
								menuitemLiquidation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CALiquidation")){
											CALiquidation CA_liq = new CALiquidation();
											addWindow(CA_liq, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLiquidationSOA = new JMenuItem("Liquidation SOA");
								menuLiquidation.add(menuitemLiquidationSOA);
								menuitemLiquidationSOA.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LiquidationSOA")){
											LiquidationSOA liq_SOA = new LiquidationSOA();
											addWindow(liq_SOA, e);
										}
									}
								});
							}
						}
						{
							JMenu menuTaxes = new JMenu("Taxes and Permits");
							menuAccounting.add(menuTaxes);
							{
								JMenuItem menuitemCWT = new JMenuItem("CWT Remittance Monitoring");
								menuTaxes.add(menuitemCWT);
								menuitemCWT.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CWTRemittance")){
											CWTRemittance cwt = new CWTRemittance();
											addWindow(cwt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemEWT_remittance = new JMenuItem("EWT Remittance Monitoring");
								menuTaxes.add(menuitemEWT_remittance);
								menuitemEWT_remittance.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("EWT_Remittance")){
											EWT_Remittance cwt = new EWT_Remittance();
											addWindow(cwt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemForm2307 = new JMenuItem("Form 2307 Monitoring");
								menuTaxes.add(menuitemForm2307);
								menuitemForm2307.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Form2307_Monitoring")){
											Form2307_Monitoring form_2307 = new Form2307_Monitoring();
											addWindow(form_2307, e);
										}
									}
								});
							}
//							{
//								JMenu menuBayadCenter = new JMenu("Bayad Center");
//								menuAccounting.add(menuBayadCenter);
//								{
//									JMenuItem menuitemManagementRepor_toUpdate = new JMenuItem("Generate ToUpdate");
//									menuBayadCenter.add(menuitemManagementRepor_toUpdate);
//									menuitemManagementRepor_toUpdate.addActionListener(new ActionListener() {
//										public void actionPerformed(ActionEvent e) {
//											if(isNotExisting("BayadCenter_ToUpdate")){
//												BayadCenter_ToUpdate BC_toupdate = new BayadCenter_ToUpdate();
//												addWindow(BC_toupdate, e);
//											}
//										}
//									});
//								}
//							}
						}
					}	
					{
						JMenu menuBuyers = new JMenu("Buyers");
						menuTransaction.add(menuBuyers);
						{
							JMenu menuClientServicing = new JMenu("Client Servicing");
							menuBuyers.add(menuClientServicing);
							{ //ADDED BY JOHN LESTER FATALLO 11-27-14
								JMenuItem menuitemClientFeedback = new JMenuItem("Client Feedback");
								menuClientServicing.add(menuitemClientFeedback);
								menuitemClientFeedback.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ClientFeedback")){
											ClientFeedback cf = new ClientFeedback();
											addWindow(cf, e);
										}
									}
								});
							}
							{//02-18-16 JOHN LESTER FATALLO
								JMenuItem menuItemClientFollowUp = new JMenuItem("Client Follow Up");
								menuClientServicing.add(menuItemClientFollowUp);
								menuItemClientFollowUp.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ClientFollowUp")){
											ClientFollowUp cfu = new ClientFollowUp();
											addWindow(cfu, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemClientInformation = new JMenuItem("Client Information");
								menuClientServicing.add(menuitemClientInformation);
								menuitemClientInformation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ClientInformation")){
											ClientInformation ci = new ClientInformation();
											addWindow(ci, e);
										}
									}
								});
							}
							{
								JMenu menuClientRequest = new JMenu("Client Request");
								menuClientServicing.add(menuClientRequest);
								{ //ADDED BY JOHN LESTER FATALLO 12-04-14
									JMenuItem menuitemRefundofPayment = new JMenuItem("Refund of Payment");
									menuClientRequest.add(menuitemRefundofPayment);
									menuitemRefundofPayment.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RefundofPayment")){
												RefundofPayment rop = new RefundofPayment();
												addWindow(rop, e);
											}
										}
									});
								}
								{ //ADDED BY JOHN LESTER FATALLO 12-09-14
									JMenuItem menuitemCreditofPayment = new JMenuItem("Credit of Payment");
									menuClientRequest.add(menuitemCreditofPayment);
									menuitemCreditofPayment.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("CreditOfPayment")){
												CreditOfPayment cop = new CreditOfPayment();
												addWindow(cop, e);
											}
										}
									});
								}
//								{
//									JMenuItem menuitemCreditofPaymentItsReal = new JMenuItem("Credit of Payment (ItsReal)");
//									menuClientRequest.add(menuitemCreditofPaymentItsReal);
//									menuitemCreditofPaymentItsReal.addActionListener(new ActionListener() {
//										public void actionPerformed(ActionEvent e) {
//											if(isNotExisting("CreditOfPaymentItsReal")){
//												CreditPaymentItsReal copir = new CreditPaymentItsReal();
//												addWindow(copir, e);
//											}
//										}
//									});
//								}
								{ //ADDED BY JOHN LESTER FATALLO 12-04-14
									JMenuItem menuitemBuyersRequestforTechDoc = new JMenuItem("Technical Documents");
									menuClientRequest.add(menuitemBuyersRequestforTechDoc);
									menuitemBuyersRequestforTechDoc.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("BuyersRequestforTechnicalDocuments")){
												BuyersRequestforTechnicalDocuments brt = new BuyersRequestforTechnicalDocuments();
												addWindow(brt, e);
											}
										}
									});
								}
								{ // ADDED BY JOHN LESTER FATALLO 12-09-14
									JMenuItem menuitemOtherRequest = new JMenuItem("Other Client Request");
									menuClientRequest.add(menuitemOtherRequest);
									menuitemOtherRequest.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("OtherRequest")){
												OtherRequest otr = new OtherRequest();
												addWindow(otr, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemWaivePenalty = new JMenuItem("Waive Penalty");
									menuClientRequest.add(menuitemWaivePenalty);
									menuitemWaivePenalty.addActionListener(new ActionListener() {

										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("WaivePenalty")){
												WaivePenalty wp = new WaivePenalty();
												addWindow(wp, e);
											}
										}
									});
								}
								/*{ //ADDED BY JOHN LESTER FATALLO 12-09-14
									JMenuItem menuitemOtherRequest2 = new JMenuItem("Other Request");
									menuClientRequest.add(menuitemOtherRequest2);
									menuitemOtherRequest2.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("OtherRequest2")){
												OtherRequest2 otr2 = new OtherRequest2();
												addWindow(otr2);
											}
										}
									});
								}*/
							}
							{
								JMenuItem menuitemHoldingReservation = new JMenuItem("Holding / Reservation");
								menuClientServicing.add(menuitemHoldingReservation);
								menuitemHoldingReservation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HoldingAndReservation")){
											HoldingAndReservation har = new HoldingAndReservation();
											addWindow(har, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemOrderOfPayments = new JMenuItem("Order of Payments");
								menuClientServicing.add(menuitemOrderOfPayments);
								menuitemOrderOfPayments.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("OrderOfPayment")){
											OrderOfPayment oop = new OrderOfPayment();
											addWindow(oop, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDocumentsMonitoring = new JMenuItem("Documents Monitoring");
								menuClientServicing.add(menuitemDocumentsMonitoring);
								menuitemDocumentsMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DocumentsMonitoring")){
											DocumentsMonitoring dm = new DocumentsMonitoring();
											addWindow(dm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRegularBillingandNotices = new JMenuItem("Regular Billing and Notices_");
								menuClientServicing.add(menuitemRegularBillingandNotices);
								menuitemRegularBillingandNotices.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RegularBillingAndNotices")){
											RegularBillingAndNotices rbn = new RegularBillingAndNotices();
											addWindow(rbn, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemUnholdingOfUnitsByBatch = new JMenuItem("Unholding of Units by batch");
								menuClientServicing.add(menuitemUnholdingOfUnitsByBatch);
								menuitemUnholdingOfUnitsByBatch.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UnholdingOfUnitsByBatch")){
											UnholdingOfUnitsByBatch uoubb = new UnholdingOfUnitsByBatch();
											addWindow(uoubb, e);
										}
									}
								});
							}
							/*{ //JOHN LESTER FATALLO 05-18-15
								JMenuItem menuitemClientSubmittedID = new JMenuItem("Submitted ID's");
								menuClientServicing.add(menuitemClientSubmittedID);
								menuitemClientSubmittedID.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ClientSubmittedID")){
											ClientSubmittedID csi = new ClientSubmittedID();
											addWindow(csi, e);
										}
									}
								});
							}*/
							{ //DEL GONZALES 07-14-2015
								JMenuItem menuitemBuyerCheckMonitoring = new JMenuItem("Buyers Check Holding & Withdrawal");
								menuClientServicing.add(menuitemBuyerCheckMonitoring);
								menuitemBuyerCheckMonitoring.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BuyersCheckMonitoring")){
											BuyersCheckMonitoring bcm = new BuyersCheckMonitoring();
											addWindow(bcm, e);
										}
									}
								});
							}
							{ //ALVIN GONZALES 07-23-2015
								JMenuItem menuitemSCDMonitoring = new JMenuItem("SCD Monitoring");
								menuClientServicing.add(menuitemSCDMonitoring);
								menuitemSCDMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SCDMonitoring")){
											SCDMonitoring scdm = new SCDMonitoring();
											addWindow(scdm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPmtBreakdown = new JMenuItem("Payment Breakdown");
								menuClientServicing.add(menuitemPmtBreakdown);
								menuitemPmtBreakdown.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PaymentBreakdown")){
											PaymentBreakdown PmtBrk = new PaymentBreakdown();
											addWindow(PmtBrk, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckWithdrawal = new JMenuItem("Checks For Withdrawal");
								menuClientServicing.add(menuitemCheckWithdrawal);
								menuitemCheckWithdrawal.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ChecksForWithdrawal")){
											ChecksForWithdrawal cfw = new ChecksForWithdrawal();
											addWindow(cfw, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSpecialHolding = new JMenuItem("Special Holding");
								menuClientServicing.add(menuitemSpecialHolding);
								menuitemSpecialHolding.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Special_Holding")){
											Special_Holding spl_holding = new Special_Holding();
											addWindow(spl_holding, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemClientRequestOldDetails = new JMenuItem("Client Request Old Details");
								menuClientServicing.add(menuitemClientRequestOldDetails);
								menuitemClientRequestOldDetails.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ClientRequestOldDetails")){
											ClientRequestOldDetails crod = new ClientRequestOldDetails();
											addWindow(crod, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPreDocsEvaluation = new JMenuItem("Pre-Docs Evaluation");
								menuClientServicing.add(menuitemPreDocsEvaluation);
								menuitemPreDocsEvaluation.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PreDocsEvaluation")){
											PreDocsEvaluation de = new PreDocsEvaluation();
											addWindow(de, e);
										}
									}
								});
							}

						}
						{
							JMenu menuCreditCollection = new JMenu("Credit and Collections");
							menuBuyers.add(menuCreditCollection);
							{
								JMenuItem menuitemPastDueProcessing = new JMenuItem("Past Due Processing"); 
								menuCreditCollection.add(menuitemPastDueProcessing);
								menuitemPastDueProcessing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PastDueProcessing_v2")){
											PastDueProcessing_v2 pdp = new PastDueProcessing_v2();
											addWindow(pdp, e);
										}
									}
								});
							}
							{

								JMenuItem menuitemPromissoryNoteCommitment = new JMenuItem("Promissory Note / Commitment"); 
								menuCreditCollection.add(menuitemPromissoryNoteCommitment);
								menuitemPromissoryNoteCommitment.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PromissryNote_v2")){ 
											//	PromissoryNote pnc = new PromissoryNote();
											PromissryNote_v2 pnc = new PromissryNote_v2();
											addWindow(pnc, e);
										}
									}
								});

							}
							{

								JMenuItem menuitemCancellationProcessing= new JMenuItem("Cancellation Processing"); 
								menuCreditCollection.add(menuitemCancellationProcessing);
								menuitemCancellationProcessing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Cancellation")){

											//_CancellationProcessing cp = new _CancellationProcessing();
											System.out.println("Dumaan dito Sa cancellation");
											Cancellation cp = new Cancellation();
											addWindow(cp, e);
										}
									}
								});
							}
							{ 

								JMenuItem menuitemNoticesTaggingForCourier = new JMenuItem("Transmittal"); 
								menuCreditCollection.add(menuitemNoticesTaggingForCourier);
								menuitemNoticesTaggingForCourier.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Transmittal")){

											Transmittal Transmittal = new Transmittal();
											addWindow(Transmittal, e);
										}
									}
								});

							}
							{

								JMenuItem menuitemPost_Office_Utility = new JMenuItem("Post Office Utility"); 
								menuCreditCollection.add(menuitemPost_Office_Utility);
								menuitemPost_Office_Utility.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Post_Office_Utility")){

											Post_Office_Utility pou = new Post_Office_Utility();
											addWindow(pou, e);
										}
									}
								});

							}
							{
								JMenuItem menuitemUnidenDeposits = new JMenuItem("Unidentified/Identified Deposits");
								menuCreditCollection.add(menuitemUnidenDeposits);
								menuitemUnidenDeposits.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Unidentified_Identified_Deposits")){
											Unidentified_Identified_Deposits unident_iden_dep = new Unidentified_Identified_Deposits();
											addWindow(unident_iden_dep, e);
										}
									}
								});
							}

							{
								JMenuItem menuitemRegularBillingandNotices = new JMenuItem("Regular Billing and Notices");
								menuCreditCollection.add(menuitemRegularBillingandNotices);
								menuitemRegularBillingandNotices.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RegularBillingAndNotices")){
											RegularBillingAndNotices rbn = new RegularBillingAndNotices();
											addWindow(rbn, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPostPCopyTCTRPTReceipt = new JMenuItem("Post Photocopy of TCT/RPT Receipt");
								menuCreditCollection.add(menuitemPostPCopyTCTRPTReceipt);
								menuitemPostPCopyTCTRPTReceipt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Post Photocopy of TCT/RPT Receipt")){
											PostPCopyTCTRPTReceipt pptrr = new PostPCopyTCTRPTReceipt();
											addWindow(pptrr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRTaxBillingStatement= new JMenuItem("Real Property Tax Billing Statment");
								menuCreditCollection.add(menuitemRTaxBillingStatement);
								menuitemRTaxBillingStatement.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Real Property Tax Billing Statment")){
											RTaxBillingStatement rtbs = new RTaxBillingStatement();
											addWindow(rtbs, e);
										}
									}
								});
							}
							JMenu menuMBTC = new JMenu("Real-Time Debit");
							menuCreditCollection.add(menuMBTC);
							{
								{
									JMenuItem menuitemMBTCDoc = new JMenuItem("MBTC Enrollment");
									menuMBTC.add(menuitemMBTCDoc);
									menuitemMBTCDoc.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("rtdDocuments")){
												rtdDocuments mbtd = new rtdDocuments();
												addWindow(mbtd, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemCBA = new JMenuItem("Client Bank Account");
									menuMBTC.add(menuitemCBA);
									menuitemCBA.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("ClientBankAccountt")){
												Buyers.CreditandCollections.ClientBankAccount cba = new Buyers.CreditandCollections.ClientBankAccount();
												addWindow(cba, e);
											}
										}
									});
								}
								{
									menuMBTC.addSeparator();
								}
								{
									JMenuItem menuitemRTD = new JMenuItem("Real-Time Debit(IHF)");
									menuMBTC.add(menuitemRTD);
									menuitemRTD.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebit")){
												Buyers.CreditandCollections.RealTimeDebit rtd = new Buyers.CreditandCollections.RealTimeDebit();
												addWindow(rtd, e);
											}
										}
									});
								}
								{
									menuMBTC.addSeparator();
								}
								{
									JMenuItem menuitemRTDHDMF = new JMenuItem("Real-Time Debit(HDMF)");
									menuMBTC.add(menuitemRTDHDMF);
									menuitemRTDHDMF.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebit_HDMF")){
												Buyers.CreditandCollections.RealTimeDebit_HDMF rtd_hdmf = new Buyers.CreditandCollections.RealTimeDebit_HDMF();
												addWindow(rtd_hdmf, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemRTDU = new JMenuItem("Real-Time Debit Download");
									menuMBTC.add(menuitemRTDU);
									menuitemRTDU.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebitUpload")){
												Buyers.CreditandCollections.RealTimeDebitUpload rtdu = new Buyers.CreditandCollections.RealTimeDebitUpload();
												addWindow(rtdu, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemRTDPiso = new JMenuItem("MBTC Piso Debit");
									menuMBTC.add(menuitemRTDPiso);
									menuitemRTDPiso.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebitPiso")){
												RealTimeDebitPiso rtdp = new RealTimeDebitPiso();
												addWindow(rtdp, e);
											}
										}
									});
								}
								{
									menuMBTC.addSeparator();
								}
								{
									JMenuItem menuitemRTDHDMFLoanReleased = new JMenuItem("Real-Time Debit(Loan Released)");
									menuMBTC.add(menuitemRTDHDMFLoanReleased);
									menuitemRTDHDMFLoanReleased.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebit_LoanReleased")){
												RealTimeDebit_LoanReleased rtdLR = new RealTimeDebit_LoanReleased();
												addWindow(rtdLR, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemRTDULoanReleased = new JMenuItem("Real-Time Debit Download(Loan Released)");
									menuMBTC.add(menuitemRTDULoanReleased);
									menuitemRTDULoanReleased.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebitUpload_LoanReleased")){
												RealTimeDebitUpload_LoanReleased rtduLR = new RealTimeDebitUpload_LoanReleased();
												addWindow(rtduLR, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemRTDPisoLoanReleased = new JMenuItem("MBTC Piso Debit(Loan Released)");
									menuMBTC.add(menuitemRTDPisoLoanReleased);
									menuitemRTDPisoLoanReleased.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebitPiso_LoanReleased")){
												RealTimeDebitPiso_LoanReleased rtdpLR = new RealTimeDebitPiso_LoanReleased();
												addWindow(rtdpLR, e);
											}
										}
									});
								}
								{
									menuMBTC.addSeparator();
								}
								{
									JMenuItem menuitemRTDUP = new JMenuItem("Real-Time Debit Unposted");
									menuMBTC.add(menuitemRTDUP);
									menuitemRTDUP.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebitUnposted")){
												RealTimeDebitUnposted rtdup = new RealTimeDebitUnposted();
												addWindow(rtdup, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemRTDP = new JMenuItem("Real-Time Debit Posted (E-mail)");
									menuMBTC.add(menuitemRTDP);
									menuitemRTDP.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("RealTimeDebitPostedwithEmail")){
												RealTimeDebitPostedwithEmail rtdp = new RealTimeDebitPostedwithEmail();
												addWindow(rtdp, e);
											}
										}
									});
								}	
							}
							{
								JMenuItem menuitemCancelledpagibig = new JMenuItem("Cancelled Pagibig");
								menuCreditCollection.add(menuitemCancelledpagibig);
								menuitemCancelledpagibig.addActionListener(new ActionListener(){
									public void	actionPerformed(ActionEvent e) {
										if(isNotExisting("TagCancelledByPAGIBIG")){
											TagCancelledByPAGIBIG tcbp = new TagCancelledByPAGIBIG();
											addWindow(tcbp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAcquiredAsset = new JMenuItem("Acquired Asset");
								menuCreditCollection.add(menuitemAcquiredAsset);
								menuitemAcquiredAsset.addActionListener(new ActionListener(){
									public void	actionPerformed(ActionEvent e) {
										if(isNotExisting("AcquiredAsset")){
											AcquiredAsset aa = new AcquiredAsset();
											addWindow(aa, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemFullSettled = new JMenuItem("Full Settled HDMF");
								menuCreditCollection.add(menuitemFullSettled);
								menuitemFullSettled.addActionListener(new ActionListener(){
									public void	actionPerformed(ActionEvent e) {
										if(isNotExisting("FullSettled")){
											FullSettled fshdmf = new FullSettled();
											addWindow(fshdmf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPreDocsEvaluation = new JMenuItem("Tagging of Cash Surrender Value");
								menuCreditCollection.add(menuitemPreDocsEvaluation);
								menuitemPreDocsEvaluation.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TaggingOfCashSurrenderValue")){
											TaggingOfCashSurrenderValue tocsv = new TaggingOfCashSurrenderValue();
											addWindow(tocsv, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemIHFFSPromo = new JMenuItem("IHF FS Promo");
								menuCreditCollection.add(menuitemIHFFSPromo);
								menuitemIHFFSPromo.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("IHF_FS_Discount_Tagging")) {
											IHF_FS_Discount_Tagging ihf_fs_disc = new IHF_FS_Discount_Tagging();
											addWindow(ihf_fs_disc);
										}
									}
								});
							}
							{
								JMenuItem menuitemUDOASPrinting = new JMenuItem("UDOAS Printing");
								menuCreditCollection.add(menuitemUDOASPrinting);
								menuitemUDOASPrinting.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UDOASPrinting")) {
											UDOASPrinting udoasp= new UDOASPrinting();
											addWindow(udoasp);
										}
									}
								});
							}
						}
						{//ADDED BY JESSA HERRERA 04-15-16 
							JMenu menuLegalAndLiaisoning = new JMenu("Legal and Liaisoning");
							menuBuyers.add(menuLegalAndLiaisoning);
							{
								JMenuItem menuitemOccupancyMonitoring = new JMenuItem("Occupancy Monitoring");
								menuLegalAndLiaisoning.add(menuitemOccupancyMonitoring);
								menuitemOccupancyMonitoring.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("OccupancyMonitoring")){
											OccupancyMonitoring om = new OccupancyMonitoring();
											addWindow(om, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemProcessingCostTransactionEntry = new JMenuItem("Processing Cost Transaction Entry");
								menuLegalAndLiaisoning.add(menuitemProcessingCostTransactionEntry);
								menuitemProcessingCostTransactionEntry.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ProcessingCostTransactionEntry")){
											ProcessingCostTransactionEntry pcte = new ProcessingCostTransactionEntry();
											addWindow(pcte, e);
										}
									}
								});
							}
							/*
							{
								JMenuItem menuitemProcessingCostTransactionEntry = new JMenuItem("Processing Cost Transaction Entry");
								menuLegalAndLiaisoning.add(menuitemProcessingCostTransactionEntry);
								menuitemProcessingCostTransactionEntry.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ProcessingCostTransactionEntry")){
											ProcessingCostTransactionEntry pcte = new ProcessingCostTransactionEntry();
											addWindow(pcte, e);
										}
									}
								});
							}
							 */
							{
								JMenuItem menuitemTransferCostTransactionEntry = new JMenuItem("Transfer Cost Transaction Entry");
								menuLegalAndLiaisoning.add(menuitemTransferCostTransactionEntry);
								menuitemTransferCostTransactionEntry.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TransferCostTransactionEntry")){
											TransferCostTransactionEntry tcte = new TransferCostTransactionEntry();
											addWindow(tcte, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTCTTaxDecProcessing = new JMenuItem("TCT/Tax Dec Processing (Mother/Individual)");
								menuLegalAndLiaisoning.add(menuitemTCTTaxDecProcessing);
								menuitemTCTTaxDecProcessing.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TCTTaxDecProcessing")){
											TCTTaxDecProcessing tcttax = new TCTTaxDecProcessing();
											addWindow(tcttax, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRevolvingFund = new JMenuItem("Revolving Funds");
								menuLegalAndLiaisoning.add(menuitemRevolvingFund);
								menuitemRevolvingFund.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TCTTaxDecProcessing")){
											RevolvingFund rf = new RevolvingFund();
											addWindow(rf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagRMCnumberPcost = new JMenuItem("Tag Request MC number TCOST - Disbursement Voucher");
								menuLegalAndLiaisoning.add(menuitemTagRMCnumberPcost);
								menuitemTagRMCnumberPcost.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TCTTaxDecProcessing")){
											TagRMCnumberTcost tagrmcp = new TagRMCnumberTcost();
											addWindow(tagrmcp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagRMCnumberPcost = new JMenuItem("Tag Request MC number PCOST - Disbursement Voucher");
								menuLegalAndLiaisoning.add(menuitemTagRMCnumberPcost);
								menuitemTagRMCnumberPcost.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TCTTaxDecProcessing")){
											TagRMCnumberPcost tagrmcp = new TagRMCnumberPcost();
											addWindow(tagrmcp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemForEditRealPropertyHouse = new JMenuItem("For Edit Real Property Tax House");
								menuLegalAndLiaisoning.add(menuitemForEditRealPropertyHouse);
								menuitemForEditRealPropertyHouse.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ForEditRealPropertyHouse")){
											ForEditRealPropertyHouse ferph = new ForEditRealPropertyHouse();
											addWindow(ferph, e);
										}
									}
								});
							}

						}
						{//ADDED BY JOHN LESTER FATALLO 08-13-15 
							JMenu menuLoansManagement = new JMenu("Loans Management");
							menuBuyers.add(menuLoansManagement);
							{
								JMenuItem menuitemBankInfo = new JMenuItem("Bank Insurance Information");
								menuLoansManagement.add(menuitemBankInfo);
								menuitemBankInfo.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BankInformation")){
											BankInformation bank_info = new BankInformation();
											addWindow(bank_info, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBankPayments = new JMenuItem("Bank Payments");
								menuLoansManagement.add(menuitemBankPayments);
								menuitemBankPayments.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BankPayments")){
											BankPayments bank_payments = new BankPayments();
											addWindow(bank_payments, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBankPayments = new JMenuItem("Buyback");
								menuLoansManagement.add(menuitemBankPayments);
								menuitemBankPayments.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Buyback")){
											Buyback buyback = new Buyback();
											addWindow(buyback, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBankREM = new JMenuItem("Bank REM");
								menuLoansManagement.add(menuitemBankREM);
								menuitemBankREM.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BankREM")){
											BankREM bank_rem = new BankREM();
											addWindow(bank_rem, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemFireInsurance = new JMenuItem("Fire Insurance (FI)");
								menuLoansManagement.add(menuitemFireInsurance);
								menuitemFireInsurance.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("FireInsurance")){
											FireInsurance fi = new FireInsurance();
											addWindow(fi, e);
										}
									}
								});

							}
							{ //ALVIN GONZALES 2015-10-20
								JMenuItem menuitemLoanReleased = new JMenuItem("Loan Released");
								menuLoansManagement.add(menuitemLoanReleased);
								menuitemLoanReleased.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LoanReleased")){
											LoanReleased lt = new LoanReleased();
											addWindow(lt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLoanReleasedOnline = new JMenuItem("Loan Released (Online)");
								menuLoansManagement.add(menuitemLoanReleasedOnline);
								menuitemLoanReleasedOnline.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LoanReleasedOnline")){
											LoanReleasedOnline lro = new LoanReleasedOnline();
											addWindow(lro, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemMortgageRedemptionInsurance = new JMenuItem("Mortgage Redemption Insurance (MRI)");
								menuLoansManagement.add(menuitemMortgageRedemptionInsurance);
								menuitemMortgageRedemptionInsurance.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("MortgageRedemptionInsurance")){
											MortgageRedemptionInsurance mri = new MortgageRedemptionInsurance();
											addWindow(mri, e);
										}
									}
								});
							}
							/*{
								JMenuItem menuitemPagibigStatusMonitoring = new JMenuItem("Pag-IBIG Status Monitoring");
								menuLoansManagement.add(menuitemPagibigStatusMonitoring);
								menuitemPagibigStatusMonitoring.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigStatusMonitoring")){
											PagibigStatusMonitoring psm = new PagibigStatusMonitoring();
											addWindow(psm, e);
										}
									}
								});
							}*/
							{
								JMenuItem menuitemPagibigStatusMonitoring_V2 = new JMenuItem("Pag-IBIG Status Monitoring");
								menuLoansManagement.add(menuitemPagibigStatusMonitoring_V2);
								menuitemPagibigStatusMonitoring_V2.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigStatusMonitoring_v2")){
											PagibigStatusMonitoring_v2 psm_v2 = new PagibigStatusMonitoring_v2();
											addWindow(psm_v2, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBankFinanceMonitoring = new JMenuItem("Bank Finance Monitoring");
								menuLoansManagement.add(menuitemBankFinanceMonitoring);
								menuitemBankFinanceMonitoring.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BankFinancingMonitoring")){
											BankFinancingMonitoring bfm = new BankFinancingMonitoring();
											addWindow(bfm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSalesofReceivables = new JMenuItem("Sales of Receivables (SOR)");
								menuLoansManagement.add(menuitemSalesofReceivables);
								menuitemSalesofReceivables.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SalesOfReceivables")){
											SalesOfReceivables sor = new SalesOfReceivables();
											addWindow(sor, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagAccountsQualifiedForNoticeToConstruct = new JMenuItem("Tag Accounts Qualified For Notice To Construct");
								menuLoansManagement.add(menuitemTagAccountsQualifiedForNoticeToConstruct);
								menuitemTagAccountsQualifiedForNoticeToConstruct.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TagAccountsQualifiedForNoticeToConstruct")){
											TagAccountsQualifiedForNoticeToConstruct taqnc = new TagAccountsQualifiedForNoticeToConstruct();
											addWindow(taqnc, e);
										}
									}
								});
							}
							{ //ALVIN GONZALES 2015-10-01
								JMenuItem menuitemNOATagging = new JMenuItem("NOA Tagging");
								menuLoansManagement.add(menuitemNOATagging);
								menuitemNOATagging.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("NOATagging")){
											NOATagging nt = new NOATagging();
											addWindow(nt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCTSNotarization = new JMenuItem("CTS Notarization");
								menuLoansManagement.add(menuitemCTSNotarization);
								menuitemCTSNotarization.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CTS_Notarization")){
											CTS_Notarization cts = new CTS_Notarization();
											addWindow(cts, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemHouseAppraisal = new JMenuItem("House Appraisal");
								menuLoansManagement.add(menuitemHouseAppraisal);
								menuitemHouseAppraisal.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HouseAppraisal")){
											HouseAppraisal hap = new HouseAppraisal();
											addWindow(hap, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCI_fee = new JMenuItem("CI Fee Payment");
								menuLoansManagement.add(menuitemCI_fee);
								menuitemCI_fee.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CI_Fee_Payments")){
											CI_Fee_Payments ci_fee = new CI_Fee_Payments();
											addWindow(ci_fee, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemMBTCLoanReleased = new JMenuItem("Loan Released MBTC Accounts");
								menuLoansManagement.add(menuitemMBTCLoanReleased); 
								menuitemMBTCLoanReleased.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("mbtcLoanReleased")){
											mbtcLoanReleased mbtclr = new mbtcLoanReleased();
											addWindow(mbtclr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCancelledTCTAnnotation = new JMenuItem("Tagging of Clients Due for Cancellation of Annotation");
								menuLoansManagement.add(menuitemCancelledTCTAnnotation); 
								menuitemCancelledTCTAnnotation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("cancelledTCTAnnotation")) {
											cancelledTCTAnnotation ctcta = new cancelledTCTAnnotation();
											addWindow(ctcta, e);
										}
									}
								});
							}
						}
					}
					{
						JMenu menuProjects = new JMenu("Projects");
						menuTransaction.add(menuProjects);
						{
							JMenu menuBiddingAndAwarding = new JMenu("Bidding & Awarding");
							menuProjects.add(menuBiddingAndAwarding);
							{
								JMenuItem menuitemNoticeToProceed = new JMenuItem("Notice to Proceed");
								menuBiddingAndAwarding.add(menuitemNoticeToProceed);
								menuitemNoticeToProceed.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("NoticeToProceed")){
											NoticeToProceed np = new NoticeToProceed();
											addWindow(np, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemFixedHousingAwardingCost = new JMenuItem("Fixed Housing Awarding Cost");
								menuBiddingAndAwarding.add(menuitemFixedHousingAwardingCost);
								menuitemFixedHousingAwardingCost.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("FixedHousingAwardingCost")){
											FixedHousingAwardingCost fhac = new FixedHousingAwardingCost();
											addWindow(fhac, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemConstructionAccomplishment = new JMenuItem("Construction Accomplishment");
								menuBiddingAndAwarding.add(menuitemConstructionAccomplishment);
								menuitemConstructionAccomplishment.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ConstructionAccomplishment")){
											ConstructionAccomplishment ca = new ConstructionAccomplishment();
											addWindow(ca, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemConstructionAccomplishment = new JMenuItem("Contractor Supplementary Details");
								menuBiddingAndAwarding.add(menuitemConstructionAccomplishment);
								menuitemConstructionAccomplishment.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorsSupplementaryDetails")){
											ContractorsSupplementaryDetails cont_sup = new ContractorsSupplementaryDetails();
											addWindow(cont_sup, e);
										}
									}
								});
							}
						}
						{
							JMenu menuConstructionManagement = new JMenu("Construction Management");
							menuProjects.add(menuConstructionManagement);
							{
								JMenuItem menuitemGenerateForUnitTurnOverOrientation = new JMenuItem("Generate For Unit TurnOver/Orientation");
								menuConstructionManagement.add(menuitemGenerateForUnitTurnOverOrientation);
								menuitemGenerateForUnitTurnOverOrientation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("GenerateForUnitTurnOverOrientation")){
											GenerateForUnitTurnOverOrientation gfut = new GenerateForUnitTurnOverOrientation();
											addWindow(gfut, e);
										}
									}
								});
							}
							/*{
								JMenuItem menuitemHouseRepair = new JMenuItem("House Repairs");
								menuConstructionManagement.add(menuitemHouseRepair);
								menuitemHouseRepair.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HouseRepair")){
											HouseRepair hp = new HouseRepair();
											addWindow(hp, e);
										}
									}
								});
							}*/
							{
								JMenuItem menuitemTagTurnOverOrientationAttendees = new JMenuItem("Tag TurnOver Orientation Attendees");
								menuConstructionManagement.add(menuitemTagTurnOverOrientationAttendees);
								menuitemTagTurnOverOrientationAttendees.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TagTurnOverOrientationAttendees")){
											TagTurnOverOrientationAttendees ttooa = new TagTurnOverOrientationAttendees();
											addWindow(ttooa, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemUnitStatusMonitoring = new JMenuItem("Unit Status Monitoring");
								menuConstructionManagement.add(menuitemUnitStatusMonitoring);
								menuitemUnitStatusMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UnitStatusMonitoring")){
											UnitStatusMonitoring usm = new UnitStatusMonitoring();
											addWindow(usm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemGenerateQR = new JMenuItem("Generate QR Code");
								menuConstructionManagement.add(menuitemGenerateQR);
								menuitemGenerateQR.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("GenerateQRCode")){
											GenerateQRCode gc = new GenerateQRCode();
											addWindow(gc, e);
										}
									}
								});
							}
						}
						{
							JMenu menuPropertyManagement = new JMenu("Property Management");
							menuProjects.add(menuPropertyManagement);
							{
								JMenuItem menuitemEndorsementForHouseTurnover = new JMenuItem("Endorsement for House Turn-Over");
								menuPropertyManagement.add(menuitemEndorsementForHouseTurnover);
								menuitemEndorsementForHouseTurnover.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("EndorsementHouseTurnOver")){
											EndorsementHouseTurnOver eht = new EndorsementHouseTurnOver();
											addWindow(eht, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemEndorsementForHouseTurnoverReport = new JMenuItem("Endorsement for Turn-Over Orientation Report");
								menuPropertyManagement.add(menuitemEndorsementForHouseTurnoverReport);
								menuitemEndorsementForHouseTurnoverReport.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("EndorsementHouseTurnoverReport")){
											EndorsementHouseTurnoverReport eto = new EndorsementHouseTurnoverReport();
											addWindow(eto, e);
										}
									}
								});

							}
							{
								JMenuItem menuitemMeralcoSIN = new JMenuItem("Assign Meralco Service ID No. (SIN)");
								menuPropertyManagement.add(menuitemMeralcoSIN);
								menuitemMeralcoSIN.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("MeralcoSIN")){
											MeralcoSIN hdsd = new MeralcoSIN();
											addWindow(hdsd, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemHOADuesStartDate = new JMenuItem("HOA Dues Start Date");
								menuPropertyManagement.add(menuitemHOADuesStartDate);
								menuitemHOADuesStartDate.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HOADuesStartDate")){
											HOADuesStartDate hdsd = new HOADuesStartDate();
											addWindow(hdsd, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagMoveInAccounts = new JMenuItem("Tag Move In Accounts");
								menuPropertyManagement.add(menuitemTagMoveInAccounts);
								menuitemTagMoveInAccounts.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TagMoveInAccounts")){
											TagMoveInAccounts ttmi = new TagMoveInAccounts();
											addWindow(ttmi, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagMoveInAccounts = new JMenuItem("Tag Move Out Accounts");
								menuPropertyManagement.add(menuitemTagMoveInAccounts);
								menuitemTagMoveInAccounts.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TagMoveOutAccounts")){
											TagMoveOutAccounts tmoa = new TagMoveOutAccounts();
											addWindow(tmoa, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagUnderRenovation = new JMenuItem("Tag Under Renovation");
								menuPropertyManagement.add(menuitemTagUnderRenovation);
								menuitemTagUnderRenovation.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TagRenovationAccount")) {
											TagRenovationAccount tra = new TagRenovationAccount();
											addWindow(tra, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagTurnedOverAccounts = new JMenuItem("Tag Turned Over Accounts");
								menuPropertyManagement.add(menuitemTagTurnedOverAccounts);
								menuitemTagTurnedOverAccounts.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TagTurnedOverAccounts")){
											TagTurnedOverAccounts ttoa = new TagTurnedOverAccounts();
											addWindow(ttoa, e);
										}
									}
								});
							}
							/*{
								JMenuItem menuitemWATERBilling = new JMenuItem("WATER Billing");
								menuPropertyManagement.add(menuitemWATERBilling);
								menuitemWATERBilling.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WATERBilling")){
											WATERBilling wb = new WATERBilling();
											addWindow(wb);
										}
									}
								});
							}*/
							{
								JMenuItem menuitemClientNotices = new JMenuItem("Client Notice");
								menuPropertyManagement.add(menuitemClientNotices);
								menuitemClientNotices.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ClientNotices")){
											ClientNotices cn = new ClientNotices();
											addWindow(cn, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemWaterBilling = new JMenuItem("Water Reading");
								menuPropertyManagement.add(menuitemWaterBilling);
								menuitemWaterBilling.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WaterBilling_v2")){
											WaterBilling_v2 wb_v2 = new WaterBilling_v2();
											addWindow(wb_v2, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemWaterServiceConnection = new JMenuItem("Water Service Connection");
								menuPropertyManagement.add(menuitemWaterServiceConnection);
								menuitemWaterServiceConnection.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WaterServiceConnection")){
											WaterServiceConnection wsc = new WaterServiceConnection();
											addWindow(wsc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemWaterServiceConnection = new JMenuItem("Tag Completed Renovation");
								menuPropertyManagement.add(menuitemWaterServiceConnection);
								menuitemWaterServiceConnection.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CompletedRenovation")){
											CompletedRenovation cr = new CompletedRenovation();
											addWindow(cr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTagWaterMeterInstallation = new JMenuItem("Tag Water Meter Installation");
								menuPropertyManagement.add(menuitemTagWaterMeterInstallation);
								menuitemTagWaterMeterInstallation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TaggingWaterMeterInstallation")){
											TaggingWaterMeterInstallation twmi = new TaggingWaterMeterInstallation();
											addWindow(twmi, e);
										}
									}
								});
							}
//							{
//								JMenuItem menuitemFacilitiesTagging = new JMenuItem("Facilities Tagging");
//								menuPropertyManagement.add(menuitemFacilitiesTagging);
//								menuitemFacilitiesTagging.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("FacilitiesTagging")){
//											FacilitiesTagging ft = new FacilitiesTagging();
//											addWindow(ft, e);
//										}
//									}
//								});
//							}
							{

								JMenuItem menuitemFacilityTagWaterReading= new JMenuItem("Water Reading Facility");
								menuPropertyManagement.add(menuitemFacilityTagWaterReading);
								menuitemFacilityTagWaterReading.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TagWaterReadingFacility")){
											FacilityTagWaterReading ftwr = new FacilityTagWaterReading();
											addWindow(ftwr, e);
										}
									}
								});
							}
							{

								JMenuItem menuitemHappyWell= new JMenuItem("Happy Well");
								menuPropertyManagement.add(menuitemHappyWell);
								menuitemHappyWell.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HappyWell")){
											HappyWell hw = new HappyWell();
											addWindow(hw, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemWaterPromoTagging = new JMenuItem("Water Promo Tagging");
								menuPropertyManagement.add(menuitemWaterPromoTagging);
								menuitemWaterPromoTagging.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WaterPromoTagging")) {
											WaterPromoTagging wpt = new WaterPromoTagging();
											addWindow(wpt, e);
										}
									}
								});						
							}
						}
						{
							JMenu menuSalesAndMarketing = new JMenu("Sales and Marketing");
							menuProjects.add(menuSalesAndMarketing);
							{
								JMenuItem menuitemPricelist = new JMenuItem("Pricelist");
								menuSalesAndMarketing.add(menuitemPricelist);
								menuitemPricelist.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Pricelist")){
											Pricelist pi = new Pricelist();
											addWindow(pi, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPaymentScheme = new JMenuItem("Payment Scheme");
								menuSalesAndMarketing.add(menuitemPaymentScheme);
								menuitemPaymentScheme.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PaymentScheme")){
											PaymentScheme ps = new PaymentScheme();
											addWindow(ps, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommAgentModule = new JMenuItem("Sales Agent/Broker");
								menuSalesAndMarketing.add(menuitemCommAgentModule);
								menuitemCommAgentModule.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SalesAgent")){
											SalesAgent agent_brkr_mod = new SalesAgent();
											addWindow(agent_brkr_mod, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSalesCont = new JMenuItem("Sales Contract Monitoring");
								menuSalesAndMarketing.add(menuitemSalesCont);
								menuitemSalesCont.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SalesContractMonitoring")){
											SalesContractMonitoring sales_cont_monit = new SalesContractMonitoring();
											addWindow(sales_cont_monit, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommScheme = new JMenuItem("Commission Scheme");
								menuSalesAndMarketing.add(menuitemCommScheme);
								menuitemCommScheme.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Comm_Scheme")){
											Comm_Scheme comm_sch = new Comm_Scheme();
											addWindow(comm_sch, e);
										}
									}
								});
							}
							{
								JMenu menuTripping_ = new JMenu("Tripping");
								menuSalesAndMarketing.add(menuTripping_);
								menuTripping_.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {/*
										if(isNotExisting("Trip_Ticket_Entry")){
											Trip_Ticket_Entry tte = new Trip_Ticket_Entry();
											addWindow(tte, e);
										}
									 */}
								});


								JMenuItem menuitemTicket_Entry = new JMenuItem("Ticket Entry");
								menuTripping_.add(menuitemTicket_Entry);
								menuitemTicket_Entry.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Trip_Ticket_Entry")){
											Trip_Ticket_Entry tte = new Trip_Ticket_Entry();
											addWindow(tte, e);
										}
									}
								});

								JMenuItem menuitemTripping_Cost = new JMenuItem("Tripping Cost");
								menuTripping_.add(menuitemTripping_Cost);
								menuitemTripping_Cost.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Tripping_Cost")){
											Tripping_Cost tc = new Tripping_Cost();
											addWindow(tc, e);
										}
									}
								});

								JMenuItem menuitemRP_Tripping = new JMenuItem("Request for Payment (Tripping)");
								menuTripping_.add(menuitemRP_Tripping);
								menuitemRP_Tripping.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Request_Payment_Trip")){
											Request_Payment_Trip rpt = new Request_Payment_Trip();
											addWindow(rpt, e);
										}
									}
								});


							}
							{
								JMenuItem menuitemPRC = new JMenuItem("PRC-HLURB Monitoring");
								menuSalesAndMarketing.add(menuitemPRC);
								menuitemPRC.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("prcDocMon")){
											prcDocMon prc = new prcDocMon();
											addWindow(prc, e);
											/*Comm_Scheme comm_sch = new Comm_Scheme();
											addWindow(comm_sch, e);*/
										}
									}
								});
							}
						}
					}
					{
						JMenuItem menuitemCARD = new JMenuItem("CARD");
						menuTransaction.add(menuitemCARD);
						menuitemCARD.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("CARD")){
									CARD c = new CARD();
									addWindow(c, e);
								}
							}
						});
					}
				}
				{
					JMenu menuReports = new JMenu("Reports");
					menuBar.add(menuReports);
					menuReports.setMnemonic(KeyEvent.VK_R);
					{
						//Added by Del Gonzales 01-04-2016
						JMenu menuAccountingRpt = new JMenu("Accounting");
						menuReports.add(menuAccountingRpt);
						{
							JMenu menuBOIRpt = new JMenu("BOI/BIR");
							menuAccountingRpt.add(menuBOIRpt);
							{
								JMenuItem menuitemJV_book = new JMenuItem("Books (CRB, CDB, JV)");
								menuBOIRpt.add(menuitemJV_book);
								menuitemJV_book.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Accounting_Books")){
											Accounting_Books jv_bk = new Accounting_Books();
											addWindow(jv_bk, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSales_book = new JMenuItem("Sales Reports");
								menuBOIRpt.add(menuitemSales_book);
								menuitemSales_book.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Sales_Books")){
											Sales_Books sales_bk = new Sales_Books();
											addWindow(sales_bk, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCRES = new JMenuItem("CRES/Expense/VAT Reports");
								menuBOIRpt.add(menuitemCRES);
								menuitemCRES.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CRES_GAE_MAF_INT_VAT")){
											CRES_GAE_MAF_INT_VAT cres_rpt = new CRES_GAE_MAF_INT_VAT();
											addWindow(cres_rpt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSubsidiaryLedgerReport = new JMenuItem("Subsidiary Ledger Report");
								menuBOIRpt.add(menuitemSubsidiaryLedgerReport);
								menuitemSubsidiaryLedgerReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SubsidiaryLedgerReport")){
											SubsidiaryLedgerReport slr = new SubsidiaryLedgerReport();
											addWindow(slr, e);
										}
									}
								});
							}
						}
						{
							JMenu menuCashieringRpt = new JMenu("Cashiering");
							menuAccountingRpt.add(menuCashieringRpt);
							{
								JMenuItem menuitemDailyCollectionReport = new JMenuItem("Daily Collection Report");
								menuCashieringRpt.add(menuitemDailyCollectionReport);
								menuitemDailyCollectionReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DailyCollectionReport")){
											DailyCollectionReport dcr = new DailyCollectionReport();
											addWindow(dcr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCDR_entries = new JMenuItem("CRB (Daily Entries)");
								menuCashieringRpt.add(menuitemCDR_entries);
								menuitemCDR_entries.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DailyCRB_entries")){
											DailyCRB_entries crb_daily = new DailyCRB_entries();
											addWindow(crb_daily, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCanceledReceipts = new JMenuItem("List of Canceled Receipts");
								menuCashieringRpt.add(menuitemCanceledReceipts);
								menuitemCanceledReceipts.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CanceledReceipts")){
											CanceledReceipts canc_rcpt = new CanceledReceipts();
											addWindow(canc_rcpt, e);
										}
									}
								});
							}
						}						
						{
							JMenu menuCollectionsRpt = new JMenu("Collections");
							menuAccountingRpt.add(menuCollectionsRpt);
							{
								JMenuItem menuitemListofHoldChecks = new JMenuItem("Checks on Hold");
								menuCollectionsRpt.add(menuitemListofHoldChecks);
								menuitemListofHoldChecks.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ChecksPreviouslyonHold")){
											ChecksPreviouslyonHold choh = new ChecksPreviouslyonHold();
											addWindow(choh, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCheckStatusListing = new JMenuItem("Check Status Listing");
								menuCollectionsRpt.add(menuitemCheckStatusListing);
								menuitemCheckStatusListing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CheckStatusListing")){
											CheckStatusListing csl = new CheckStatusListing();
											addWindow(csl, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPDCduefortheday = new JMenuItem("PDC Due for the Day");
								menuCollectionsRpt.add(menuitemPDCduefortheday);
								menuitemPDCduefortheday.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PDCDuefortheDay")){
											PDCduefortheday pdc = new PDCduefortheday();
											addWindow(pdc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSummaryBncdChecks = new JMenuItem("Summary of Bounced Checks");
								menuCollectionsRpt.add(menuitemSummaryBncdChecks);
								menuitemSummaryBncdChecks.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SummaryofBouncedChecks")){
											SummaryBouncedChecks sbc = new SummaryBouncedChecks();
											addWindow(sbc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemrtdTran = new JMenuItem("Real-Time Debit Transactions");
								menuCollectionsRpt.add(menuitemrtdTran);
								menuitemrtdTran.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RealTimeDebitTransactions")){
											RealTimeDebitTransactions rtd = new RealTimeDebitTransactions();
											addWindow(rtd, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemhdmfCollection = new JMenuItem("HDMF Collection Report");
								menuCollectionsRpt.add(menuitemhdmfCollection);
								menuitemhdmfCollection.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HDMFCollectionReport")){
											HDMFCollectionReport hdmfcr = new HDMFCollectionReport();
											addWindow(hdmfcr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSummaryCashCheck = new JMenuItem("Cash and Check Summary Report");
								menuCollectionsRpt.add(menuitemSummaryCashCheck);
								menuitemSummaryCashCheck.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("summaryCashCheck")){
											summaryCashCheck scc = new summaryCashCheck();
											addWindow(scc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSummaryReturnedCheck = new JMenuItem("Summary of Returned Checks");
								menuCollectionsRpt.add(menuitemSummaryReturnedCheck);
								menuitemSummaryReturnedCheck.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SummaryOfReturnedChecks")){
											SummaryOfReturnedChecks src = new SummaryOfReturnedChecks();
											addWindow(src, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPDCWarehousingDue = new JMenuItem("PDC Warehousing Reports");
								menuCollectionsRpt.add(menuitemPDCWarehousingDue);
								menuitemPDCWarehousingDue.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PDCWarehousing_Reports")){
											PDCWarehousing_Reports pdcw_rep = new PDCWarehousing_Reports();
											addWindow(pdcw_rep, e);
										}
									}
								});
							}
						}
						{
							JMenu menuCommissionRpt = new JMenu("Commission");
							menuAccountingRpt.add(menuCommissionRpt);
							{
								JMenuItem menuitemCPF = new JMenuItem("Commission Payout Form (CPF)");
								menuCommissionRpt.add(menuitemCPF);
								menuitemCPF.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CommPayoutForm")){
											CommPayoutForm comm_cpf = new CommPayoutForm();
											addWindow(comm_cpf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommReports = new JMenuItem("Commission Reports");
								menuCommissionRpt.add(menuitemCommReports);
								menuitemCommReports.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Commission_Reports")){
											Commission_Reports comm_rpt = new Commission_Reports();
											addWindow(comm_rpt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCDF_listing = new JMenuItem("CDF Listing");
								menuCommissionRpt.add(menuitemCDF_listing);
								menuitemCDF_listing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CDF_listing")){
											CDF_listing comm_list = new CDF_listing();
											addWindow(comm_list, e);
										}
									}
								});
							}
						}
						{
							JMenu menuContractorsRpt = new JMenu("Contractors");
							menuAccountingRpt.add(menuContractorsRpt);
							{
								JMenuItem menuitemContractorsBilling = new JMenuItem("Contractors Progress Billing Report");
								menuContractorsRpt.add(menuitemContractorsBilling);
								menuitemContractorsBilling.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ConstructionInProgress")){
											ConstructionInProgress cip_rpt = new ConstructionInProgress();
											addWindow(cip_rpt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemContractorsUnbilled = new JMenuItem("Contractors Unbilled Progress Billing");
								menuContractorsRpt.add(menuitemContractorsUnbilled);
								menuitemContractorsUnbilled.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorsPayable_Unbilled")){
											ContractorsPayable_Unbilled contr_unbilled = new ContractorsPayable_Unbilled();
											addWindow(contr_unbilled, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBillingSummary = new JMenuItem("Contractors Billing Summary");
								menuContractorsRpt.add(menuitemBillingSummary);
								menuitemBillingSummary.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorsBillingSummary")){
											ContractorsBillingSummary contr_bill_sum = new ContractorsBillingSummary();
											addWindow(contr_bill_sum, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemContractorsChangeOrder = new JMenuItem("Contractor's Change Order Report");
								menuContractorsRpt.add(menuitemContractorsChangeOrder);
								menuitemContractorsChangeOrder.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorsChangeOrder")){
											ContractorsChangeOrder contr_change_order = new ContractorsChangeOrder();
											addWindow(contr_change_order, e);
										}
									}
								});
							}

						}
						{
							JMenu menuDepositsRpt = new JMenu("Deposits");
							menuAccountingRpt.add(menuDepositsRpt);
							{
								JMenuItem menuitemDepositSlip = new JMenuItem("Deposit Slip");
								menuDepositsRpt.add(menuitemDepositSlip);
								menuitemDepositSlip.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DepositSlip")){
											DepositSlip dslip = new DepositSlip();
											addWindow(dslip, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSummaryDailyDeposit = new JMenuItem("Summary of Daily Deposits");
								menuDepositsRpt.add(menuitemSummaryDailyDeposit);
								menuitemSummaryDailyDeposit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SummaryofDeposits")){
											SummaryofDeposits sdp = new SummaryofDeposits();
											addWindow(sdp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDepositListing = new JMenuItem("Deposit Listing");
								menuDepositsRpt.add(menuitemDepositListing);
								menuitemDepositListing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DepositListing")){
											DepositListing dpe_listing = new DepositListing();
											addWindow(dpe_listing, e);
										}
									}
								});
							}

						}
						{
							JMenu menuDisbursementRpt = new JMenu("Disbursements");
							menuAccountingRpt.add(menuDisbursementRpt);
							{
								JMenuItem menuitemCheckSeries = new JMenuItem("Check Series");
								menuDisbursementRpt.add(menuitemCheckSeries);
								menuitemCheckSeries.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CheckSeries")){
											CheckSeries cs = new CheckSeries();
											addWindow(cs, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDRFprooflist = new JMenuItem("DRF Prooflist");
								menuDisbursementRpt.add(menuitemDRFprooflist);
								menuitemDRFprooflist.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DRFprooflist")){
											DRFprooflist drf_proof = new DRFprooflist();
											addWindow(drf_proof, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPVprooflist = new JMenuItem("PV Prooflist");
								menuDisbursementRpt.add(menuitemPVprooflist);
								menuitemPVprooflist.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PVprooflist")){
											PVprooflist pv_proof = new PVprooflist();
											addWindow(pv_proof, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCVprooflist = new JMenuItem("CV Prooflist");
								menuDisbursementRpt.add(menuitemCVprooflist);
								menuitemCVprooflist.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CVprooflist")){
											CVprooflist cv_proof = new CVprooflist();
											addWindow(cv_proof, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPaidOutVoucher = new JMenuItem("Paid Out Disbursement Voucher");
								menuDisbursementRpt.add(menuitemPaidOutVoucher);
								menuitemPaidOutVoucher.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PaidOutDisbVoucher")){
											PaidOutDisbVoucher drf_proof = new PaidOutDisbVoucher();
											addWindow(drf_proof, e);
										}
									}
								});
							}
							/*COMMENTED BY JED 2020-12-11 : MERGE WITH PV PROOFLIST MODULE*/
							//							{
							//								JMenuItem menuitemPV_withoutDV = new JMenuItem("PV Without DV");
							//								menuDisbursementRpt.add(menuitemPV_withoutDV);
							//								menuitemPV_withoutDV.addActionListener(new ActionListener() {
							//									public void actionPerformed(ActionEvent e) {
							//										if(isNotExisting("PV_without_DV")){
							//											PV_without_DV pv_wo_dv = new PV_without_DV();
							//											addWindow(pv_wo_dv, e);
							//										}
							//									}
							//								});
							//							}
							{
								JMenuItem menuitemBatchPrinting = new JMenuItem("Batch Printing");
								menuDisbursementRpt.add(menuitemBatchPrinting);
								menuitemBatchPrinting.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BatchPrinting")){
											BatchPrinting batch_pr = new BatchPrinting();
											addWindow(batch_pr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBatchPrinting = new JMenuItem("Cash / Check Disbursement / Payable Voucher Register");
								menuDisbursementRpt.add(menuitemBatchPrinting);
								menuitemBatchPrinting.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CashDisbursement_PV_Register")){
											CashDisbursement_PV_Register cd_pv_reg = new CashDisbursement_PV_Register();
											addWindow(cd_pv_reg, e);
										}
									}
								});
							}
						}	
						{
							JMenu menuGL_report = new JMenu("General Ledger");
							menuAccountingRpt.add(menuGL_report);
							{
								JMenuItem menuitemJVprooflist = new JMenuItem("JV Prooflist");
								menuGL_report.add(menuitemJVprooflist);
								menuitemJVprooflist.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("JVprooflist")){
											JVprooflist jv_plist = new JVprooflist();
											addWindow(jv_plist, e);
										}
									}
								});
							}
						}
						{
							JMenu menuManagementRpt = new JMenu("Management Reports");
							menuAccountingRpt.add(menuManagementRpt);
							{
								JMenuItem menuitemCommSched = new JMenuItem("Commission Schedules");
								menuManagementRpt.add(menuitemCommSched);
								menuitemCommSched.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Commission_Schedule")){
											Commission_Schedule comm_sched = new Commission_Schedule();
											addWindow(comm_sched, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAP_others = new JMenuItem("Schedule of AP Others");
								menuManagementRpt.add(menuitemAP_others);
								menuitemAP_others.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Schedule_AP_Others")){
											Schedule_AP_Others ap_others = new Schedule_AP_Others();
											addWindow(ap_others, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemsrc_contractors = new JMenuItem("Summary Report Of Collectibles From Contractors");
								menuManagementRpt.add(menuitemsrc_contractors);
								menuitemsrc_contractors.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SummaryReportOfCollectiblesFromContractors")){
											SummaryReportOfCollectiblesFromContractors src_contractors = new SummaryReportOfCollectiblesFromContractors();
											addWindow(src_contractors, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAdvContrDP = new JMenuItem("Schedule of Advances to Contractors - DP");
								menuManagementRpt.add(menuitemAdvContrDP);
								menuitemAdvContrDP.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AdvancesToContractors_DP")){
											AdvancesToContractors_DP adv_cont_dp = new AdvancesToContractors_DP();
											addWindow(adv_cont_dp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAdContrBC = new JMenuItem("Schedule of Advances to Contractors - BC");
								menuManagementRpt.add(menuitemAdContrBC);
								menuitemAdContrBC.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AdvancesToContractors_BC")){
											AdvancesToContractors_BC adv_cont_bc = new AdvancesToContractors_BC();
											addWindow(adv_cont_bc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAdContrUtilities = new JMenuItem("Schedule of Advances to Contractors - Utilities");
								menuManagementRpt.add(menuitemAdContrUtilities);
								menuitemAdContrUtilities.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AdvancesToContractors_Utilities")){
											AdvancesToContractors_Utilities adv_cont_util = new AdvancesToContractors_Utilities();
											addWindow(adv_cont_util, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemContractorsUnbilled = new JMenuItem("Schedule of Contractors Payable - Retention");
								menuManagementRpt.add(menuitemContractorsUnbilled);
								menuitemContractorsUnbilled.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorsRetentionsPayables")){
											ContractorsRetentionsPayables contr_ret_pybl = new ContractorsRetentionsPayables();
											addWindow(contr_ret_pybl, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAdvToBroker = new JMenuItem("Schedule of Advances to Brokers");
								menuManagementRpt.add(menuitemAdvToBroker);
								menuitemAdvToBroker.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ScheduleOfAdvancesToBrokers")){
											ScheduleOfAdvancesToBrokers sch_adv_brkr = new ScheduleOfAdvancesToBrokers();
											addWindow(sch_adv_brkr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAdvToOffEmp = new JMenuItem("Schedule of Advances to Officers and Employees");
								menuManagementRpt.add(menuitemAdvToOffEmp);
								menuitemAdvToOffEmp.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ScheduleOfAdvancesOfficersAndEmployees")){
											ScheduleOfAdvancesOfficersAndEmployees sch_adv_off_emp = new ScheduleOfAdvancesOfficersAndEmployees();
											addWindow(sch_adv_off_emp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTradeReceivables = new JMenuItem("Schedule of Trade Receivables");
								menuManagementRpt.add(menuitemTradeReceivables);
								menuitemTradeReceivables.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ScheduleofTradeReceivables")){
											ScheduleofTradeReceivables sch_trade_rec = new ScheduleofTradeReceivables();
											addWindow(sch_trade_rec, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemForfeiture = new JMenuItem("Schedule of Customer Deposit Forfeiture");
								menuManagementRpt.add(menuitemForfeiture);
								menuitemForfeiture.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ScheduleCD_Forfeiture")){
											ScheduleCD_Forfeiture forf = new ScheduleCD_Forfeiture();
											addWindow(forf, e);
										}
									}
								});
							}
							{
								JSeparator sp = new JSeparator();
								menuManagementRpt.add(sp);	
							}
							{
								JMenuItem menuitemTradeReceivables = new JMenuItem("Schedule of DP Collection and Payment Schedule");
								menuManagementRpt.add(menuitemTradeReceivables);
								menuitemTradeReceivables.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ScheduleOfDPCollectionPmtSchedule")){
											ScheduleOfDPCollectionPmtSchedule dp_coll = new ScheduleOfDPCollectionPmtSchedule();
											addWindow(dp_coll, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCashflow = new JMenuItem("Cashflow Monitoring");
								menuManagementRpt.add(menuitemCashflow);
								menuitemCashflow.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Cashflow")){
											Cashflow casf_flow = new Cashflow();
											addWindow(casf_flow, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDailySalesReport = new JMenuItem("Daily Sales Report");
								menuManagementRpt.add(menuitemDailySalesReport);
								menuitemDailySalesReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DailySalesReport")){
											DailySalesReport daily_sr = new DailySalesReport();
											addWindow(daily_sr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemListHDMFClientsStatusReportMR = new JMenuItem("PAGIBIG Clients Status Report (MR Format)");
								menuManagementRpt.add(menuitemListHDMFClientsStatusReportMR);
								menuitemListHDMFClientsStatusReportMR.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigClientsStatusReportMR")){
											PagibigClientsStatusReportMR hdmfMR = new PagibigClientsStatusReportMR();
											addWindow(hdmfMR, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemListHDMFClientsStatusReportSummaryMR = new JMenuItem("PAGIBIG Clients Status Report Summary");
								menuManagementRpt.add(menuitemListHDMFClientsStatusReportSummaryMR);
								menuitemListHDMFClientsStatusReportSummaryMR.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigClientsStatusReportSummary")){
											PagibigClientsStatusReportSummary hdmf_summ_mr = new PagibigClientsStatusReportSummary();
											addWindow(hdmf_summ_mr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemHouseConstReport = new JMenuItem("House Construction Status Report");
								menuManagementRpt.add(menuitemHouseConstReport);
								menuitemHouseConstReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("House_Construction_Status")){
											House_Construction_Status hse_const_rpt = new House_Construction_Status();
											addWindow(hse_const_rpt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemOtherReports = new JMenuItem("Project Closing Reports");
								menuManagementRpt.add(menuitemOtherReports);
								menuitemOtherReports.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("otherReports")){
											otherReports or = new otherReports();
											addWindow(or, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemManagementReportSched = new JMenuItem("Due for House Construction");
								menuManagementRpt.add(menuitemManagementReportSched);
								menuitemManagementReportSched.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ManagementReportSchedule")){
											ManagementReportSchedule mrs = new ManagementReportSchedule();
											addWindow(mrs, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReturnCheckMBTCTcost = new JMenuItem("Returned Checks-MBTC For TCOST");
								menuManagementRpt.add(menuitemReturnCheckMBTCTcost);
								menuitemReturnCheckMBTCTcost.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Returned Checks-MBTC For TCOST")){
											ReturnCheckMBTCTcost_v2 rcmbtc = new ReturnCheckMBTCTcost_v2();
											addWindow(rcmbtc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReturnCheckMBTCPcost = new JMenuItem("Returned Checks-MBTC For PCOST");
								menuManagementRpt.add(menuitemReturnCheckMBTCPcost);
								menuitemReturnCheckMBTCPcost.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Returned Checks-MBTC For PCOST")){
											ReturnCheckMBTCPcost_v2 rcmbtcp = new ReturnCheckMBTCPcost_v2();
											addWindow(rcmbtcp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReturnCheckMBTCPcost = new JMenuItem("List Of OB Of SOR Accounts");
								menuManagementRpt.add(menuitemReturnCheckMBTCPcost);
								menuitemReturnCheckMBTCPcost.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("List Of OB Of SOR Accounts\"")){
											SOR_ACCOUNTS soracc = new SOR_ACCOUNTS();
											addWindow(soracc, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReturnCheckMBTCPcost = new JMenuItem("In-House Monthly Amortization Report");
								menuManagementRpt.add(menuitemReturnCheckMBTCPcost);
								menuitemReturnCheckMBTCPcost.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("IH Monthly Amortization Report")){
											IHFAmortizationCollection ihmamor = new IHFAmortizationCollection();
											addWindow(ihmamor, e);
										}
									}
								});
							}
						}
						{
							JMenu menuFinancialReport = new JMenu("Financial Reports");
							menuAccountingRpt.add(menuFinancialReport);
							{
								{
									JMenuItem menuitemCommReportFR = new JMenuItem("Commission Report (FR)");
									menuFinancialReport.add(menuitemCommReportFR);
									menuitemCommReportFR.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("Commission_Reports_FR")){
												Commission_Reports_FR comm_sched_rpt = new Commission_Reports_FR();
												addWindow(comm_sched_rpt, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemFinancialReport = new JMenuItem("Financial Report (Main)");
									menuFinancialReport.add(menuitemFinancialReport);
									menuitemFinancialReport.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("FinancialReport")){
												FinancialReport fr = new FinancialReport();
												addWindow(fr, e);
											}
										}
									});
								}
							}
						}
						{
							JMenu menuFixedAssets = new JMenu("Fixed Assets");
							menuAccountingRpt.add(menuFixedAssets);
							{
								{
									JMenuItem menuitemAssetCardPrinting = new JMenuItem("Asset Card Printing");
									menuFixedAssets.add(menuitemAssetCardPrinting);
									menuitemAssetCardPrinting.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("AssetCard")){
												AssetCard as = new AssetCard();
												addWindow(as, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemPrintAssetSticker= new JMenuItem("Print Asset Sticker");
									menuFixedAssets.add(menuitemPrintAssetSticker);
									menuitemPrintAssetSticker.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("PrintAssetSticker")){
												PrintAssetSticker pas = new PrintAssetSticker();
												addWindow(pas, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemMasterList= new JMenuItem("Master List");
									menuFixedAssets.add(menuitemMasterList);
									menuitemMasterList.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("MasterList")){
												MasterList ml = new MasterList();
												addWindow(ml, e);
											}
										}
									});
								}
								{
									{
										JMenuItem menuitemNotFoundAsset= new JMenuItem("Not Found Asset");
										menuFixedAssets.add(menuitemNotFoundAsset);
										menuitemNotFoundAsset.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												if(isNotExisting("NotFoundAsset")){
													NotFoundAsset nfa = new NotFoundAsset();
													addWindow(nfa, e);
												}
											}
										});
									}
								}
								/*{
									{
										JMenuItem menuitemRepairmonitoring= new JMenuItem("Repair Monitoring");
										menuFixedAssets.add(menuitemRepairmonitoring);
										menuitemRepairmonitoring.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												if(isNotExisting("RepairMonitoring")){
													RepairMonitoring rm = new RepairMonitoring();
													addWindow(rm, e);
												}
											}
										});
									}
								}*/
								/*{
									{
										JMenuItem menuitemDepreciation= new JMenuItem("Depreciation");
										menuFixedAssets.add(menuitemDepreciation);
										menuitemDepreciation.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												if(isNotExisting("Depreciation")){
													DepreciationofAsset dep = new DepreciationofAsset();
													addWindow(dep, e);
												}
											}
										});
									}
								}*/
							}							
						}
					}
					{
						JMenu menuReportBuyers = new JMenu("Buyers");
						menuReports.add(menuReportBuyers);
						{
							JMenu menuReportClientServicing = new JMenu("Client Servicing");
							menuReportBuyers.add(menuReportClientServicing);
							{
								JMenuItem menuitemListOfOpenUnits = new JMenuItem("Open Units");
								menuReportClientServicing.add(menuitemListOfOpenUnits);
								menuitemListOfOpenUnits.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("OpenUnits")){
											OpenUnits ou = new OpenUnits();
											addWindow(ou, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemHoldUnits = new JMenuItem("Hold Units");
								menuReportClientServicing.add(menuitemHoldUnits);
								menuitemHoldUnits.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HoldUnits")){
											HoldUnits hu = new HoldUnits();
											addWindow(hu, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTemporaryReservedUnits = new JMenuItem("Temporary Reserved Units");
								menuReportClientServicing.add(menuitemTemporaryReservedUnits);
								menuitemTemporaryReservedUnits.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TemporaryReservedUnits")){
											TemporaryReservedUnits tru = new TemporaryReservedUnits();
											addWindow(tru, e);
										}
									}
								});
							}
							{ //ADDED BY JOHN LESTER FATALLO 05-12-15
								JMenuItem menuitemActiveComplaints = new JMenuItem("List of Active Complaints");
								menuReportClientServicing.add(menuitemActiveComplaints);
								menuitemActiveComplaints.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ActiveComplaints")){
											ActiveComplaints ac = new ActiveComplaints();
											addWindow(ac, e);
										}
									}
								});
							}
							{
								JMenuItem menuItemSubmittedID = new JMenuItem("List of Expiring ID");
								menuReportClientServicing.add(menuItemSubmittedID);
								menuItemSubmittedID.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SubmittedID")){
											SubmittedID si = new SubmittedID();
											addWindow(si, e);
										}
									}
								});
							}
							{
								JMenuItem menuAgingReport = new JMenuItem("Aging Report");
								menuReportClientServicing.add(menuAgingReport);
								menuAgingReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										/*if(isNotExisting("AgingReport_OLD")){
											AgingReport_OLD aro = new AgingReport_OLD();
											addWindow(aro, e);
										}*/
										if(isNotExisting("AgingReport")){
											AgingReport ar = new AgingReport();
											addWindow(ar, e);
										}
									}
								});
							}
							{
								JMenuItem menuSCDIn_Accounts = new JMenuItem("List of SCD In Accounts");
								menuReportClientServicing.add(menuSCDIn_Accounts);
								menuSCDIn_Accounts.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ListofSCD_InTaggedAccounts")){
											ListofSCD_InTaggedAccounts scd_in = new ListofSCD_InTaggedAccounts();
											addWindow(scd_in, e);
										}
									}
								});
							}
							{
								JMenuItem menuList_DeficientAccts = new JMenuItem("OR Accounts Not yet Docs Complete");
								menuReportClientServicing.add(menuList_DeficientAccts);
								menuList_DeficientAccts.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ListOfDeficientAccounts")){
											ListOfDeficientAccounts def_accts = new ListOfDeficientAccounts();
											addWindow(def_accts, e);
										}
									}
								});
							}
							{
								JMenuItem menuStatusListing = new JMenuItem("Status Listing");
								menuReportClientServicing.add(menuStatusListing);
								menuStatusListing.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("StatusListing")){
											StatusListing sl = new StatusListing();
											addWindow(sl, e);
										}
									}
								});
							}
							{
								JMenuItem menuStatusFloatingCommitAPR = new JMenuItem("Status of Floating/Commitment/APR");
								menuReportClientServicing.add(menuStatusFloatingCommitAPR);
								menuStatusFloatingCommitAPR.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("StatusFloating_Commitment_APR_Report")){
											StatusFloating_Commitment_APR_Report sfcar = new StatusFloating_Commitment_APR_Report();
											addWindow(sfcar, e);
										}
									}
								});
							}
							{
								JMenuItem menuDocsEvalReport = new JMenuItem("Pre-Docs Evaluation Report");
								menuReportClientServicing.add(menuDocsEvalReport);
								menuDocsEvalReport.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PreDocsEvaluationReport")){
											PreDocsEvaluationReport der = new PreDocsEvaluationReport();
											addWindow(der, e);

										}
									}
								});
							}
							{
								JMenuItem menuRefundable_Amenities = new JMenuItem("Refundable Amenities Report");
								menuReportClientServicing.add(menuRefundable_Amenities);
								menuRefundable_Amenities.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Refundable_Amenities_Report")) {
											Refundable_Amenities_Report ra = new Refundable_Amenities_Report();
											addWindow(ra, e);
										}
									}
								});
							}
							{
								JMenuItem menuSwornAffidavitSubmitted = new JMenuItem("Sworn Affidavit Client List");
								menuReportClientServicing.add(menuSwornAffidavitSubmitted);
								menuSwornAffidavitSubmitted.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SwornAffidavitRowHouseReport")) {
											SwornAffidavitRowHouseReport sarh = new SwornAffidavitRowHouseReport();
											addWindow(sarh, e);
										}
									}
								});
							}
						}
						{
							JMenu menuReportCreditCollections = new JMenu("Credit and Collections");
							menuReportBuyers.add(menuReportCreditCollections);
							{

								JMenuItem menuitemCancelActive = new JMenuItem("Client Docket Invetory Report");
								menuReportCreditCollections.add(menuitemCancelActive);
								menuitemCancelActive.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CancellationActiveReport")){
											CancellationActiveReport car = new CancellationActiveReport();
											addWindow(car, e);
										}
									}
								});


							}
							{
								JMenuItem menuitemStatusCancelAccnts = new JMenuItem("List of Canceled Accounts Status");
								menuReportCreditCollections.add(menuitemStatusCancelAccnts);
								menuitemStatusCancelAccnts.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("StatusCancellationAccnts")){
											StatusCancellationAccnts sca = new StatusCancellationAccnts();
											addWindow(sca, e);
										}
									}
								});

							}
							{
								JMenuItem menuitemAR_creditPaymentReport = new JMenuItem("Credit of Payment Report");
								menuReportCreditCollections.add(menuitemAR_creditPaymentReport);
								menuitemAR_creditPaymentReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AR_CreditPaymentReport")){
											AR_CreditPaymentReport ar_cr_pmt = new AR_CreditPaymentReport();
											addWindow(ar_cr_pmt, e);
										}
									}
								});

							}
							{
								JMenuItem menuitemRTSNoticesReport = new JMenuItem("Return to Sender Notices Report");
								menuReportCreditCollections.add(menuitemRTSNoticesReport);
								menuitemRTSNoticesReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RTSNoticesReport")){
											RTSNoticesReport rnr = new RTSNoticesReport();
											addWindow(rnr, e);
										}
									}
								});

							}
							{
								JMenuItem menuitemLoanReleasedAccountsWithPN = new JMenuItem("Loan Released Accounts with PN");
								menuReportCreditCollections.add(menuitemLoanReleasedAccountsWithPN);
								menuitemLoanReleasedAccountsWithPN.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Loan Released Accounts with PN")){
											LoanRealeasedAccountsWithPN lrawpn = new LoanRealeasedAccountsWithPN();
											addWindow(lrawpn, e);
										}
									}
								});

							}
							/*{

								JMenuItem menuitemrptReport = new JMenuItem("Real Property Tax Report");
								menuReportCreditCollections.add(menuitemrptReport);
								menuitemrptReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("rptReport")){
											rptReport rptR = new rptReport();
											addWindow(rptR, e);
										}
									}
								});


							}*/

							{

								JMenuItem menuitemrptReportv3 = new JMenuItem("Real Property Tax Report");
								menuReportCreditCollections.add(menuitemrptReportv3);
								menuitemrptReportv3.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("rptReport_v3")){
											rptReport_v3 rptR3 = new rptReport_v3();
											addWindow(rptR3, e);
										}
									}
								});


							}
							
							{
								JMenuItem menuCancelledFullsettledByPagibig = new JMenuItem("Cancelled and Full settled by Pagibig");
								menuReportCreditCollections.add(menuCancelledFullsettledByPagibig);
								menuCancelledFullsettledByPagibig.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CancelledFullsettledByPagibig")){
											CancelledFullsettledByPagibig sl = new CancelledFullsettledByPagibig();
											addWindow(sl, e);
										}
									}
								});
							}
							{
								JMenuItem menuFSAcctsMonitoring = new JMenuItem("Full Settled Accts Monitoring");
								menuReportCreditCollections.add(menuFSAcctsMonitoring);
								menuFSAcctsMonitoring.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("FSAcctsMonitoring")){
											FSAcctsMonitoring fsam = new FSAcctsMonitoring();
											addWindow(fsam, e);
										}
										
									}
								}
								);				
							}
						}
						{
							JMenu menuReportLegalAndLiaisoning = new JMenu("Legal and Liaisoning");
							menuReportBuyers.add(menuReportLegalAndLiaisoning);
							/*COMMENTED BY JED 2021-11-24 : MODIFY THE MODULE WHERE USER CAN PREVIEW TRANSACTION REPORTS PER BUYER*/
							//							{
							//								JMenuItem menuitemProcessingTransferCostReport = new JMenuItem("Processing/Transfer Cost Report");
							//								menuReportLegalAndLiaisoning.add(menuitemProcessingTransferCostReport);
							//								menuitemProcessingTransferCostReport.addActionListener(new ActionListener() {
							//
							//									public void actionPerformed(ActionEvent e) {
							//										if(isNotExisting("ProcessingTransferCostReport")){
							//											ProcessingTransferCostReport ptcr = new ProcessingTransferCostReport();
							//											addWindow(ptcr, e);
							//										}
							//									}
							//								});
							//							}
							{
								JMenuItem menuitemProcessingTransferCostReport = new JMenuItem("Processing/Transfer Cost Report");
								menuReportLegalAndLiaisoning.add(menuitemProcessingTransferCostReport);
								menuitemProcessingTransferCostReport.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ProcessingTransferCostReport_v2")){
											ProcessingTransferCostReport_v2 ptcr = new ProcessingTransferCostReport_v2();
											addWindow(ptcr, e);
										}
									}
								});
							}
							{//ADDED BY JED 2021-05-03 DCRF NO. 1650 : PRINTING OF TRANS. REPORT BY BATCH
								JMenuItem menuitemPCOSTTCOSTBatch = new JMenuItem("PCOST/TCOST Batch Printing");
								menuReportLegalAndLiaisoning.add(menuitemPCOSTTCOSTBatch);
								menuitemPCOSTTCOSTBatch.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PCOSTTCOSTBatchPrinting")){
											PCOSTTCOSTBatchPrinting bp = new PCOSTTCOSTBatchPrinting();
											addWindow(bp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemProcessingCostLiquidationMonitoring = new JMenuItem("Processing Cost Liquidation Monitoring");
								menuReportLegalAndLiaisoning.add(menuitemProcessingCostLiquidationMonitoring);
								menuitemProcessingCostLiquidationMonitoring.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ProcessingCostLiquidationMonitoring")){
											ProcessingCostLiquidationMonitoring pclm = new ProcessingCostLiquidationMonitoring();
											addWindow(pclm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDocumentTransmittalOptions = new JMenuItem("Document Transmittal Options");
								menuReportLegalAndLiaisoning.add(menuitemDocumentTransmittalOptions);
								menuitemDocumentTransmittalOptions.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DocumentTransmittalOptions")){
											DocumentTransmittalOptions dto = new DocumentTransmittalOptions();
											addWindow(dto, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLRA_Forms = new JMenuItem("LRA Form");
								menuReportLegalAndLiaisoning.add(menuitemLRA_Forms);
								menuitemLRA_Forms.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LRA_Forms")){
											LRA_Forms lra = new LRA_Forms();
											addWindow(lra, e);
										}
									}
								});
							}
							{
								//JMenuItem menuitemForm1904 = new JMenuItem("Form 1904");
								/*CHANGED BY JED 2021-09-27 DCRF NO. 1726 - ADDITIONAL BIR DOCS*/
								JMenuItem menuitemForm1904 = new JMenuItem("Form 1904/2000OT/2000/RT Slip");
								menuReportLegalAndLiaisoning.add(menuitemForm1904);
								menuitemForm1904.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Form_1904")){
											Form_1904 form1904 = new Form_1904();
											addWindow(form1904, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDOA_Monitoring = new JMenuItem("DOA Monitoring");
								menuReportLegalAndLiaisoning.add(menuitemDOA_Monitoring);
								menuitemDOA_Monitoring.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DOA_Monitoring")){
											DOA_Monitoring doa = new DOA_Monitoring();
											addWindow(doa, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemREM_StatListRep = new JMenuItem("REM Status List Report");
								menuReportLegalAndLiaisoning.add(menuitemREM_StatListRep);
								menuitemREM_StatListRep.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("REM_Status_List_Report")){
											REM_Status_List_Report rem = new REM_Status_List_Report();
											addWindow(rem, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRevolving_Reports = new JMenuItem("Revolving Funds Reports");
								menuReportLegalAndLiaisoning.add(menuitemRevolving_Reports);
								menuitemRevolving_Reports.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RevolvingFundsReports")){
											RevolvingFundsReports rfp = new RevolvingFundsReports();
											addWindow(rfp, e);
										}
									}
								});
							}

							{//ADDED BY JED 2021-02-04 DCRF NO. 1560
								JMenuItem menuitemRPTComputationReport = new JMenuItem("RPT Computation Report");
								menuReportLegalAndLiaisoning.add(menuitemRPTComputationReport);
								menuitemRPTComputationReport.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RPTComputationReport")){
											RPTComputationReport rcr = new RPTComputationReport();
											addWindow(rcr, e);
										}
									}
								});
							}
							{//ADDED BY JED 2021-02-10 DCRF NO. 1570
								JMenuItem menuitemTCTTaxDecForLiqui = new JMenuItem("TCT/Tax Dec For Liquidation");
								menuReportLegalAndLiaisoning.add(menuitemTCTTaxDecForLiqui);
								menuitemTCTTaxDecForLiqui.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TCTTaxDecForLiquidation")){
											TCTTaxDecForLiquidation tdfl = new TCTTaxDecForLiquidation();
											addWindow(tdfl, e);
										}
									}
								});
							}
							{//ADDED BY JED 2021-04-23 DCRF NO. __
								JMenuItem menuitemORTransferTax = new JMenuItem("OR Transfer Tax");
								menuReportLegalAndLiaisoning.add(menuitemORTransferTax);
								menuitemORTransferTax.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TransferTaxReceipt")){
											TransferTaxReceipt ttr = new TransferTaxReceipt();
											addWindow(ttr, e);
										}
									}
								});
							}
							{//ADDED BY JED 2021-05-20 DCRF NO. 1661
								JMenuItem menuitemLoanReleasedEPEB = new JMenuItem("Loan Released (EPEB)");
								menuReportLegalAndLiaisoning.add(menuitemLoanReleasedEPEB);
								menuitemLoanReleasedEPEB.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Loan_Released_EPEB")){
											Loan_Released_EPEB lre = new Loan_Released_EPEB();
											addWindow(lre, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLandBankPaymentSlip = new JMenuItem("Land Bank Payment Slip");
								menuReportLegalAndLiaisoning.add(menuitemLandBankPaymentSlip);
								menuitemLandBankPaymentSlip.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LandBankPaymentSlip")){
											LandBankPaymentSlip lbps = new LandBankPaymentSlip();
											addWindow(lbps, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLandBankPaymentSlip = new JMenuItem("Pag-Ibig Rem Conversion Report");
								menuReportLegalAndLiaisoning.add(menuitemLandBankPaymentSlip);
								menuitemLandBankPaymentSlip.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagIbigRemConversionReport")){
											PagIbigRemConversionReport pircr = new PagIbigRemConversionReport();
											addWindow(pircr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTransferTaxDecMonitoring = new JMenuItem("Transfer Tax Dec Monitoring");
								menuReportLegalAndLiaisoning.add(menuitemTransferTaxDecMonitoring);
								menuitemTransferTaxDecMonitoring.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TransferTaxDecMonitoring")){
											TransferTaxDecMonitoring ttdc = new TransferTaxDecMonitoring();
											addWindow(ttdc, e);
										}
									}

								});
							}				
							{
								JMenuItem menuitemclaimstub = new JMenuItem("Claim Stub Form");
								menuReportLegalAndLiaisoning.add(menuitemclaimstub);
								menuitemclaimstub.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("claimstub")){
											claimstub csf = new claimstub();
											addWindow(csf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemGtGTaxClearance = new JMenuItem("G-to-G Tax Clearance");
								menuReportLegalAndLiaisoning.add(menuitemGtGTaxClearance);
								menuitemGtGTaxClearance.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("GTG_TaxClearance")){
											GTG_TaxClearance g2gtc = new GTG_TaxClearance();
											addWindow(g2gtc, e);
										}
									}
								});
							}
//							{
//								JMenuItem menuitemTransferTaxDecMonitoring = new JMenuItem("Transfer Tax Dec Monitoring");
//								menuReportLegalAndLiaisoning.add(menuitemTransferTaxDecMonitoring);
//								menuitemTransferTaxDecMonitoring.addActionListener(new ActionListener() {
//
//									@Override
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("TransferTaxDecMonitoring")){
//											TransferTaxDecMonitoring ttdc = new TransferTaxDecMonitoring();
//											addWindow(ttdc, e);
//										}
//									}
//
//								});
//							}						
						}
						
						
						{
							JMenu menuReportLoansManagement = new JMenu("Loans Management");
							menuReportBuyers.add(menuReportLoansManagement);
							{
								JMenuItem menuitemFireInsurance = new JMenuItem("FI Reports");
								menuReportLoansManagement.add(menuitemFireInsurance);
								menuitemFireInsurance.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("FI_Reports")){
											FI_Reports fi = new FI_Reports();
											addWindow(fi, e);
										}
									}
								});
							}
							/*{
								JMenuItem menuitemNoticeToConstructList = new JMenuItem("Notice To Construct List");
								menuReportLoansManagement.add(menuitemNoticeToConstructList);
								menuitemNoticeToConstructList.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("NoticeToConstructList")){
											NoticeToConstructList ntcl = new NoticeToConstructList();
											addWindow(ntcl, e);
										}
									}
								});
							}*/
							{
								JMenuItem menuitemStatusOfNTCIssuance = new JMenuItem("Status of NTC Issuance");
								menuReportLoansManagement.add(menuitemStatusOfNTCIssuance);
								menuitemStatusOfNTCIssuance.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("StatusOfNTCIssuance")){
											StatusOfNTCIssuance ntci = new StatusOfNTCIssuance();
											addWindow(ntci, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemMortgageRedemptionInsurance = new JMenuItem("MRI Reports");
								menuReportLoansManagement.add(menuitemMortgageRedemptionInsurance);
								menuitemMortgageRedemptionInsurance.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("MRI_Reports")){
											MRI_Reports mri = new MRI_Reports();
											addWindow(mri, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSalesofReceivables = new JMenuItem("Sales of Receivables");
								menuReportLoansManagement.add(menuitemSalesofReceivables);
								menuitemSalesofReceivables.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SORReports")){
											SORReports sor = new SORReports();
											addWindow(sor, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemNoticeOfApprovalNOAReleased = new JMenuItem("Notice Of Approval (NOA) Released");
								menuReportLoansManagement.add(menuitemNoticeOfApprovalNOAReleased);
								menuitemNoticeOfApprovalNOAReleased.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("NOAReleased")){
											NOAReleased noar = new NOAReleased();
											addWindow(noar, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemListOf_OR_accounts = new JMenuItem("List of Officially Reserved Accounts");
								menuReportLoansManagement.add(menuitemListOf_OR_accounts);
								menuitemListOf_OR_accounts.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ListOfOfficiallyReservedAccounts")){
											ListOfOfficiallyReservedAccounts loracct = new ListOfOfficiallyReservedAccounts();
											addWindow(loracct, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemListHDMFClientsStatusReport = new JMenuItem("PAGIBIG Clients Status Report");
								menuReportLoansManagement.add(menuitemListHDMFClientsStatusReport);
								menuitemListHDMFClientsStatusReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigClientsStatusReport")) {
											PagibigClientsStatusReport hdmfstatreport = new PagibigClientsStatusReport();
											addWindow(hdmfstatreport, e);
										}
									}
								});								
							}
							{
								JMenuItem menuitemWithNOANotLR = new JMenuItem("PAGIBIG Accounts with NOA(Not Loan Released)");
								menuReportLoansManagement.add(menuitemWithNOANotLR);
								menuitemWithNOANotLR.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigClientStatusWithNoaNotLR")){
											PagibigClientStatusWithNoaNotLR pnoa = new PagibigClientStatusWithNoaNotLR();
											addWindow(pnoa, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemListHDMFClientsStatusReportSummary = new JMenuItem("PAGIBIG Clients Status Summary Report");
								menuReportLoansManagement.add(menuitemListHDMFClientsStatusReportSummary);
								menuitemListHDMFClientsStatusReportSummary.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigClientsStatusReportSummary")){
											PagibigClientsStatusReportSummary hdmfstatreportsumm = new PagibigClientsStatusReportSummary();
											addWindow(hdmfstatreportsumm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPAGIBIGNotice = new JMenuItem("PAGIBIG Notices");
								menuReportLoansManagement.add(menuitemPAGIBIGNotice);
								menuitemPAGIBIGNotice.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigNotices")){
											PagibigNotices pbn = new PagibigNotices();
											addWindow(pbn, e);
										}
									}
								});
							}
							{
								JMenuItem menuItemNoticetoConstructList = new JMenuItem("Notice to Construct List");
								menuReportLoansManagement.add(menuItemNoticetoConstructList);
								menuItemNoticetoConstructList.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("NoticeToConstructList")) {
											NoticeToConstructList ntcList = new NoticeToConstructList();
											addWindow(ntcList, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLRMDReports = new JMenuItem("LRMD Reports");
								menuReportLoansManagement.add(menuitemLRMDReports);
								menuitemLRMDReports.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LRMD Reports")) {
											/*lrmdReports lrRep = new lrmdReports();
											addWindow(lrRep, e);*/
										}
									}
								});								
							}
							{
								JMenuItem menuitemHDMFDocs = new JMenuItem("PAGIBIG Documents");
								menuReportLoansManagement.add(menuitemHDMFDocs);
								menuitemHDMFDocs.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigDocuments")) {
											PagibigDocuments hdmfDoc = new PagibigDocuments();
											addWindow(hdmfDoc, e);
										}
									}
								});								
							}
							{
								JMenuItem menuitemBankFinanceStatus = new JMenuItem("Bank Financing Client Status Report");
								menuReportLoansManagement.add(menuitemBankFinanceStatus);
								menuitemBankFinanceStatus.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BankFinance_ClientStatusReport")){
											BankFinance_ClientStatusReport bf = new BankFinance_ClientStatusReport();
											addWindow(bf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemLoanProcessingAging = new JMenuItem("Loan Processing Aging Report");
								menuReportLoansManagement.add(menuitemLoanProcessingAging);
								menuitemLoanProcessingAging.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LoanProcessingAgingReport")) {
											LoanProcessingAgingReport aging = new LoanProcessingAgingReport();
											addWindow(aging, e);
										}
									}
								});								
							}
							{
								JMenuItem menuitemListOfClientsWithPendingMSVS = new JMenuItem("List Of Clients With Pending MSVS Application");
								menuReportLoansManagement.add(menuitemListOfClientsWithPendingMSVS);
								menuitemListOfClientsWithPendingMSVS.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ListOfClientsWithPendingMSVS")) {
											ListOfClientsWithPendingMSVS lcwp = new ListOfClientsWithPendingMSVS();
											addWindow(lcwp, e);
										}
									}
								});								
							}
							{
								JMenuItem menuitemLoanReleasedReport = new JMenuItem("List of Loan Released Accounts");
								menuReportLoansManagement.add(menuitemLoanReleasedReport);
								menuitemLoanReleasedReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LoanReleasedReport")) {
											LoanReleasedReport lrRep = new LoanReleasedReport();
											addWindow(lrRep, e);
										}
									}
								});								
							}
							{
								JMenuItem menuitemPagibigReports = new JMenuItem("REM Conversion Reports");
								menuReportLoansManagement.add(menuitemPagibigReports);
								menuitemPagibigReports.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigReports")){
											PagibigReports pir = new PagibigReports();
											addWindow(pir, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTitleTransferStatus_FullSettled = new JMenuItem("Title Transfer Status - Full Settled");
								menuReportLoansManagement.add(menuitemTitleTransferStatus_FullSettled);
								menuitemTitleTransferStatus_FullSettled.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("StatusTitleTransfer_FullSettled")){
											StatusTitleTransfer_FullSettled st_fs = new StatusTitleTransfer_FullSettled();
											addWindow(st_fs, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPagibigReports_GtoG = new JMenuItem("REM G TO G FACILITY REPORT");
								menuReportLoansManagement.add(menuitemPagibigReports_GtoG);
								menuitemPagibigReports_GtoG.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("PagibigReports_GtoG")){
											PagibigReports_GtoG pir_gg = new PagibigReports_GtoG();
											addWindow(pir_gg, e);
										}
									}
								});
							}

						}
						{
							JMenu menuHDMFCollection = new JMenu("PAGIBIG Collection");
							menuReportBuyers.add(menuHDMFCollection);
							{
								JMenuItem menuitemPCTCollections = new JMenuItem("PCT Reports");
								menuHDMFCollection.add(menuitemPCTCollections);
								menuitemPCTCollections.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("pctReports")){
											pctReports pctr = new pctReports();
											addWindow(pctr, e);
										}
									}
								});
							}
						}
					}
					{
						JMenu menuProject = new JMenu("Projects");
						menuReports.add(menuProject);
						{
							JMenu menuBiddingAndAwarding = new JMenu("Bidding and Awarding");
							menuProject.add(menuBiddingAndAwarding);
							{
								JMenuItem menuitemWeeklyConstructionAccomplishment = new JMenuItem("Weekly Construction Accomplishment");
								menuBiddingAndAwarding.add(menuitemWeeklyConstructionAccomplishment);
								menuitemWeeklyConstructionAccomplishment.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WeeklyConstructionAccomplishment")){
											WeeklyConstructionAccomplishment wca = new WeeklyConstructionAccomplishment();
											addWindow(wca, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSuretyBondMonitoring = new JMenuItem("Surety Bond Monitoring");
								menuBiddingAndAwarding.add(menuitemSuretyBondMonitoring);
								menuitemSuretyBondMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SuretyBondMonitoring")){
											SuretyBondMonitoring sbm = new SuretyBondMonitoring();
											addWindow(sbm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemContractsMonitoring = new JMenuItem("Contracts Monitoring");
								menuBiddingAndAwarding.add(menuitemContractsMonitoring);
								menuitemContractsMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractsMonitoring")){
											ContractsMonitoring cm = new ContractsMonitoring();
											addWindow(cm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemContractsMonitoring = new JMenuItem("Contractors Credit Balance");
								menuBiddingAndAwarding.add(menuitemContractsMonitoring);
								menuitemContractsMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorCreditBalance")){
											ContractorCreditBalance ccb = new ContractorCreditBalance();
											addWindow(ccb, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemContractorsANDF = new JMenuItem("Contractor's ANDF Monitoring");
								menuBiddingAndAwarding.add(menuitemContractorsANDF);
								menuitemContractorsANDF.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractorsANDF")){
											ContractorsANDF candf = new ContractorsANDF();
											addWindow(candf, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemConstructionMonitoring = new JMenuItem("Construction Monitoring (BAT)");
								menuBiddingAndAwarding.add(menuitemConstructionMonitoring);
								menuitemConstructionMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ConstructionMonitoring_BAT")){
											ConstructionMonitoring_BAT cmr = new ConstructionMonitoring_BAT();
											addWindow(cmr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemWeeklyContractorsAndAwardingMonitoring = new JMenuItem("Weekly Contractors And Awarding Monitoring");
								menuBiddingAndAwarding.add(menuitemWeeklyContractorsAndAwardingMonitoring);
								menuitemWeeklyContractorsAndAwardingMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("weeklyContractorsAndAwardingMonitoring")){
											weeklyContractorsAndAwardingMonitoring wcam = new weeklyContractorsAndAwardingMonitoring();
											addWindow(wcam, e);
										}
									}
								});
							}
							{
								JMenuItem ContractsSequenceMonitoring = new JMenuItem("Contracts Sequence Monitoring");
								menuBiddingAndAwarding.add(ContractsSequenceMonitoring);
								ContractsSequenceMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractsSequenceMonitoring")){
											ContractsSequenceMonitoring csm = new ContractsSequenceMonitoring();
											addWindow(csm, e);
										}
									}
								});
							}
						}
						{
							JMenu menuConstructionManagement = new JMenu("Construction Management");
							menuProject.add(menuConstructionManagement);
							{
								JMenuItem menuitemConstructionMonitoring = new JMenuItem("Construction Monitoring");
								menuConstructionManagement.add(menuitemConstructionMonitoring);
								menuitemConstructionMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ConstructionMonitoring")){
											ConstructionMonitoring cmr = new ConstructionMonitoring();
											addWindow(cmr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemConstructionMonitoringTSD = new JMenuItem("Construction Monitoring (TSD)");
								menuConstructionManagement.add(menuitemConstructionMonitoringTSD);
								menuitemConstructionMonitoringTSD.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ConstructionMonitoringTSD")){
											ConstructionMonitoringTSD cmrtsd = new ConstructionMonitoringTSD();
											addWindow(cmrtsd, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemConstructionManagementReports = new JMenuItem("Construction Management Reports");
								menuConstructionManagement.add(menuitemConstructionManagementReports);
								menuitemConstructionManagementReports.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("menuitemConstructionManagementReports")){
											constructionManagementReports cmr = new constructionManagementReports();
											addWindow(cmr, e);
										}
									}
								});									
							}
							{
								JMenuItem menuitemHouseAccompCMDApp = new JMenuItem("House Accomplishment (from CMD App)");
								menuConstructionManagement.add(menuitemHouseAccompCMDApp);
								menuitemHouseAccompCMDApp.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("HouseAccomplishmentCMDApp")){
											HouseAccomplishmentCMDApp haca = new HouseAccomplishmentCMDApp();
											addWindow(haca, e);
										}
									}
								});									
							}
						}
						{
							JMenu menuReportPropertyManagement = new JMenu("Property Management");
							menuProject.add(menuReportPropertyManagement);
							{
								JMenuItem menuitempmd_agingreport = new JMenuItem("PMD Aging Report");
								menuReportPropertyManagement.add(menuitempmd_agingreport);
								menuitempmd_agingreport.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("pmd_agingreport")){
											pmd_agingreport pmdar = new pmd_agingreport();
											addWindow(pmdar, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPropertyManagementReports = new JMenuItem("Property Management Reports");
								menuReportPropertyManagement.add(menuitemPropertyManagementReports);
								menuitemPropertyManagementReports.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("propertyManagementReports")){
											propertyManagementReports pmdr = new propertyManagementReports();
											addWindow(pmdr, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemMeralcoIndividual = new JMenuItem("Meralco Individualization Report");
								menuReportPropertyManagement.add(menuitemMeralcoIndividual);
								menuitemMeralcoIndividual.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("MeralcoIndividualization_Report")){
											MeralcoIndividualization_Report mir = new MeralcoIndividualization_Report();
											addWindow(mir, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemUnitStatusListing = new JMenuItem("Unit Status Listing");
								menuReportPropertyManagement.add(menuitemUnitStatusListing);
								menuitemUnitStatusListing.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UnitStatusListing")){
											UnitStatusListing usl = new UnitStatusListing();
											addWindow(usl, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemWaterMeterInstallationReport = new JMenuItem("Water Meter Installation Report");
								menuReportPropertyManagement.add(menuitemWaterMeterInstallationReport);
								menuitemWaterMeterInstallationReport.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WaterMeterInstallation_Report")){
											WaterMeterInstallation_Report wmi = new WaterMeterInstallation_Report();
											addWindow(wmi, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemGeneralAssemblyHOA = new JMenuItem("General Assembly for Creation of HOA");
								menuReportPropertyManagement.add(menuitemGeneralAssemblyHOA);
								menuitemGeneralAssemblyHOA.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("GeneralAssemblyHOA")) {
											GeneralAssemblyHOA gahoa = new GeneralAssemblyHOA();
											addWindow(gahoa, e);
										}
									}
								});
							}

						}
						{
							JMenu menuSalesAndMktgReport = new JMenu("Sales and Marketing");
							menuProject.add(menuSalesAndMktgReport);
							/*{
								JMenuItem menuitemSalesPerformance= new JMenuItem("Sales Performance");
								menuSalesAndMktgReport.add(menuitemSalesPerformance);
								menuitemSalesPerformance.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ContractsMonitoring")){
											ContractsMonitoring cm = new ContractsMonitoring();
											addWindow(cm, e);
										}
									}
								});
							}*/
							{
								JMenuItem menuitemCommReports = new JMenuItem("Sales Performance");
								menuSalesAndMktgReport.add(menuitemCommReports);
								menuitemCommReports.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SalesPerformanceRpt")){
											SalesPerformanceRpt sales_perf_rpt = new SalesPerformanceRpt();
											addWindow(sales_perf_rpt, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemEndorsedATM = new JMenuItem("List of Endorsed ATM for Processing");
								menuSalesAndMktgReport.add(menuitemEndorsedATM);
								menuitemEndorsedATM.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ListOfEndorsedATM")){
											ListOfEndorsedATM endorsed_atm = new ListOfEndorsedATM();
											addWindow(endorsed_atm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemListOfSellingUnit = new JMenuItem("List of Selling Unit");
								menuSalesAndMktgReport.add(menuitemListOfSellingUnit);
								menuitemListOfSellingUnit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ListOfSellingUnits")){
											//ListOfEndorsedATM endorsed_atm = new ListOfEndorsedATM();
											ListOfSellingUnits lsu = new ListOfSellingUnits();
											addWindow(lsu, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTrippingReport = new JMenuItem("Tripping Reports");
								menuSalesAndMktgReport.add(menuitemTrippingReport);
								menuitemTrippingReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("TrippingReports")){
											TrippingReports trip_rpt = new TrippingReports();
											addWindow(trip_rpt, e);
										}
									}
								});
							}
						}
						{
							JMenu menuCOOReport = new JMenu("COO Reports");
							menuProject.add(menuCOOReport);
							{
								JMenuItem menuLandDevCostMonit= new JMenuItem("Unit Status Monitoring (COO)");
								menuCOOReport.add(menuLandDevCostMonit);
								menuLandDevCostMonit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UnitStatusMonitoring_COO")){
											UnitStatusMonitoring_COO unit_status = new UnitStatusMonitoring_COO();
											addWindow(unit_status, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDemographic= new JMenuItem("Buyer Demographics");
								menuCOOReport.add(menuitemDemographic);
								menuitemDemographic.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BuyerDemographics")){
											BuyerDemographics byr_demo = new BuyerDemographics();
											addWindow(byr_demo, e);
										}
									}
								});
							}
							{
								JMenuItem menuLandDevCostMonit= new JMenuItem("Land Development Cost Monitoring");
								menuCOOReport.add(menuLandDevCostMonit);
								menuLandDevCostMonit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("LandDevtCostMonitoring")){
											LandDevtCostMonitoring landev_cost = new LandDevtCostMonitoring();
											addWindow(landev_cost, e);
										}
									}
								});
							}
							{
								JMenuItem menuWeeklySalesReport= new JMenuItem("Weekly Sales Report");
								menuCOOReport.add(menuWeeklySalesReport);
								menuWeeklySalesReport.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WeeklySalesReport")){
											WeeklySalesReport byr_demo = new WeeklySalesReport();
											addWindow(byr_demo, e);
										}
									}
								});
							}
						}
					}
				}
				{
					JMenu menuUtilities = new JMenu("Utilities"); 
					menuBar.add(menuUtilities);
					menuUtilities.setMnemonic(KeyEvent.VK_U);
					{
						JMenu menuAccountingUtil	 = new JMenu("Accounting");
						menuUtilities.add(menuAccountingUtil);
						{
							JMenu menuAccounts = new JMenu("Accounts");
							menuAccountingUtil.add(menuAccounts);

							{
								JMenuItem menuitemAcctgPeriod= new JMenuItem("Accounting Period");
								menuAccounts.add(menuitemAcctgPeriod);
								menuitemAcctgPeriod.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AcctgPeriod")){
											AcctgPeriod acc_prd = new AcctgPeriod();
											addWindow(acc_prd, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAddAccount= new JMenuItem("Chart of Accounts");
								menuAccounts.add(menuitemAddAccount);
								menuitemAddAccount.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ChartofAccounts")){
											ChartofAccounts chart_accts = new ChartofAccounts();
											addWindow(chart_accts, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAddAccount= new JMenuItem("Pro-Forma Entries");
								menuAccounts.add(menuitemAddAccount);
								menuitemAddAccount.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ProForma_Entries")){
											ProForma_Entries pf_entries = new ProForma_Entries();
											addWindow(pf_entries, e);
										}
									}
								});
							}

							{
								JMenuItem menuitemBankAccount= new JMenuItem("Bank Accounts");
								menuAccounts.add(menuitemBankAccount);
								menuitemBankAccount.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("BankAccounts")){
											BankAccounts bank_acct = new BankAccounts();
											addWindow(bank_acct, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemBank= new JMenuItem("Bank / Bank Branch");
								menuAccounts.add(menuitemBank);
								menuitemBank.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AddBank")){
											AddBank add_bank = new AddBank();
											addWindow(add_bank, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemUploadChartOfAccounts= new JMenuItem("Upload Chart of Accounts");
								menuAccounts.add(menuitemUploadChartOfAccounts);
								menuitemUploadChartOfAccounts.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UploadChartOfAccounts")){
											UploadChartOfAccounts upload_coa = new UploadChartOfAccounts();
											addWindow(upload_coa, e);
										}
									}
								});
							}
						}
						{
							JMenu menuCahieringUtility = new JMenu("Cashiering");
							menuAccountingUtil.add(menuCahieringUtility);
							{
								JMenuItem menuitemAddReceiptNumber = new JMenuItem("Add Receipt Number");
								menuCahieringUtility.add(menuitemAddReceiptNumber);
								menuitemAddReceiptNumber.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AddReceiptNumber")){
											AddReceiptNumber add_receipt_no = new AddReceiptNumber();
											addWindow(add_receipt_no, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReceiptMaintenance = new JMenuItem("Receipt Maintenance");
								menuCahieringUtility.add(menuitemReceiptMaintenance);
								menuitemReceiptMaintenance.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("receiptMaintenance")){
											receiptMaintenance rm = new receiptMaintenance();
											addWindow(rm, e);
										}
									}
								});
							}
							/*{
								JMenuItem menuitemReceiptMaintenance = new JMenuItem("Receipt Maintenance");
								menuCahieringUtility.add(menuitemReceiptMaintenance);
								menuitemReceiptMaintenance.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AddReceiptNumber")){
											receiptMaintenance rm = new receiptMaintenance();
											addWindow(rm, e);
										}
									}
								});
							}*/
							{
								JMenuItem menuitemBank= new JMenuItem("Receipt Printing");
								menuCahieringUtility.add(menuitemBank);
								menuitemBank.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ReceiptPrinting")){
											ReceiptPrinting rp = new ReceiptPrinting();
											addWindow(rp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReceiptPrintingGoodCheck= new JMenuItem("Receipt Printing (Issuance of OR - Good Check)");
								menuCahieringUtility.add(menuitemReceiptPrintingGoodCheck); 
								menuitemReceiptPrintingGoodCheck.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ReceiptPrinting_GoodCheck")){
											ReceiptPrinting_GoodCheck rp = new ReceiptPrinting_GoodCheck();
											addWindow(rp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReceiptPrintingGoodCheck= new JMenuItem("Receipt Printing (Issuance of OR - Late LTS/BOI)");
								menuCahieringUtility.add(menuitemReceiptPrintingGoodCheck); 
								menuitemReceiptPrintingGoodCheck.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ReceiptPrinting_LateLTS_BOI")){
											ReceiptPrinting_LateLTS_BOI rp = new ReceiptPrinting_LateLTS_BOI();
											addWindow(rp, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemReleaseOR = new JMenuItem("Releasing Of Official Receipts");
								menuCahieringUtility.add(menuitemReleaseOR); 
								menuitemReleaseOR.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("OfficialReceiptReleasing")){
											OfficialReceiptReleasing rop = new OfficialReceiptReleasing();
											addWindow(rop, e);
										}
									}
								});								
							}
							{
								JMenuItem menuitemFADOR = new JMenuItem("SPOT Cash OR Issuance");
								menuCahieringUtility.add(menuitemFADOR); 
								menuitemFADOR.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("fadresp_lateORIssuance")){
											fadresp_lateORIssuance fadresp = new fadresp_lateORIssuance();
											addWindow(fadresp, e);
										}
									}
								});			
							}
							{
								JMenuItem menuitemUploadFacilitesPmt = new JMenuItem("Upload Facilites Payment");
								menuCahieringUtility.add(menuitemUploadFacilitesPmt); 
								menuitemUploadFacilitesPmt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("UploadFacilitiesPayments")){
											UploadFacilitiesPayments ufp = new UploadFacilitiesPayments();
											addWindow(ufp, e);
										}
									}
								});			
							}
						}
						{
							JMenu menuCommissionUtil = new JMenu("Commission");
							menuAccountingUtil.add(menuCommissionUtil);
							{
								JMenuItem menuitemCommAgentModule = new JMenuItem("Sales Agent/Broker");
								menuCommissionUtil.add(menuitemCommAgentModule);
								menuitemCommAgentModule.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SalesAgent")){
											SalesAgent agent_brkr_mod = new SalesAgent();
											addWindow(agent_brkr_mod, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAgentBrokerBranch = new JMenuItem("Sales Agent/Broker Branch");
								menuCommissionUtil.add(menuitemAgentBrokerBranch);
								menuitemAgentBrokerBranch.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("salesAgentBranch")){
											salesAgentBranch sab = new salesAgentBranch();
											addWindow(sab, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommQualOverride = new JMenuItem("Commission Qualification Override");
								menuCommissionUtil.add(menuitemCommQualOverride);
								menuitemCommQualOverride.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Comm_QualifOverride")){
											Comm_QualifOverride qualif_override = new Comm_QualifOverride();
											addWindow(qualif_override, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRestructureCommSched = new JMenuItem("Restructure Commission Schedule");
								menuCommissionUtil.add(menuitemRestructureCommSched);
								menuitemRestructureCommSched.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Comm_RestructureCommSchedule")){
											Comm_RestructureCommSchedule restruc_comm = new Comm_RestructureCommSchedule();
											addWindow(restruc_comm, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCPFcancellation = new JMenuItem("CPF Cancellation");
								menuCommissionUtil.add(menuitemCPFcancellation);
								menuitemCPFcancellation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CancelCPF_comm")){
											CancelCPF_comm cpf_cancel = new CancelCPF_comm();
											addWindow(cpf_cancel, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommSchedTransfer = new JMenuItem("Commission Schedule Transfer");
								menuCommissionUtil.add(menuitemCommSchedTransfer);
								menuitemCommSchedTransfer.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Comm_Sched_Transfer")){
											Comm_Sched_Transfer comm_sched_trans = new Comm_Sched_Transfer();
											addWindow(comm_sched_trans, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemTIN_tagging = new JMenuItem("TIN Tagging");
								menuCommissionUtil.add(menuitemTIN_tagging);
								menuitemTIN_tagging.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AgentTIN_tagging")){
											AgentTIN_tagging tin_tag = new AgentTIN_tagging();
											addWindow(tin_tag, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommScheme = new JMenuItem("Commission Scheme");
								menuCommissionUtil.add(menuitemCommScheme);
								menuitemCommScheme.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Comm_Scheme")){
											Comm_Scheme comm_sch = new Comm_Scheme();
											addWindow(comm_sch, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemPromo = new JMenuItem("Promo");
								menuCommissionUtil.add(menuitemPromo);
								menuitemPromo.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AddPromo")){
											AddPromo add_promo = new AddPromo();
											addWindow(add_promo, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemWTaxTagging = new JMenuItem("WTax Rate Waiver Tagging");
								menuCommissionUtil.add(menuitemWTaxTagging);
								menuitemWTaxTagging.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("WtaxRateTagging")){
											WtaxRateTagging wtax_tag = new WtaxRateTagging();
											addWindow(wtax_tag, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemCommReprocessing = new JMenuItem("Commission Reprocessing");
								menuCommissionUtil.add(menuitemCommReprocessing);
								menuitemCommReprocessing.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Comm_Reprocessing")){
											Comm_Reprocessing comm_reproc = new Comm_Reprocessing();
											addWindow(comm_reproc, e);
										}
									}
								});
							}
							/*{
								JMenuItem menuitemSpecCommAllocation = new JMenuItem("Special Commission Allocation");
								menuCommissionUtil.add(menuitemSpecCommAllocation);
								menuitemSpecCommAllocation.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Comm_QualifOverride")){
											Comm_QualifOverride qualif_override = new Comm_QualifOverride();
											addWindow(qualif_override);
										}
									}
								});
							}*/
						}	
						{
							JMenu menuDisbursementUtil = new JMenu("Disbursements");
							menuAccountingUtil.add(menuDisbursementUtil);
							{
								JMenuItem menuitemAddReceipt= new JMenuItem("Add Check Number");
								menuDisbursementUtil.add(menuitemAddReceipt);
								menuitemAddReceipt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AddCheckNumber")){
											AddCheckNumber add_receipt = new AddCheckNumber();
											addWindow(add_receipt, e);
										}
									}
								});
							}
						}
						{
							JMenu menuFADprocess = new JMenu("Process");
							menuAccountingUtil.add(menuFADprocess);
							{
								JMenuItem menuitemProcessAdmin= new JMenuItem("FAD Process Admin");
								menuFADprocess.add(menuitemProcessAdmin);
								menuitemProcessAdmin.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("FAD_process_admin")){
											FAD_process_admin fad_process_admin = new FAD_process_admin();
											addWindow(fad_process_admin, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemProcessingCostTable= new JMenuItem("Processing Cost Table");
								menuFADprocess.add(menuitemProcessingCostTable);
								menuitemProcessingCostTable.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Processing_Cost_Table")){
											Processing_Cost_Table pct = new Processing_Cost_Table();
											addWindow(pct, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDocStatusByBatch= new JMenuItem("Document Status by Batch");
								menuFADprocess.add(menuitemDocStatusByBatch);
								menuitemDocStatusByBatch.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DocumentByBatch")){
											DocumentByBatch docbybatch = new DocumentByBatch();
											addWindow(docbybatch, e);
										}
									}
								});
							}

						}
						{
							JMenu menuFADTaxes = new JMenu("Taxes");
							menuAccountingUtil.add(menuFADTaxes);
							{
								{
									JMenuItem menuitemTaxRateDiscrepancy = new JMenuItem("List of Entities With WTax Rate Discrepancy");
									menuFADTaxes.add(menuitemTaxRateDiscrepancy);
									menuitemTaxRateDiscrepancy.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("Tax_Rate_Discrepancy")){
												Tax_Rate_Discrepancy trd = new Tax_Rate_Discrepancy();
												addWindow(trd, e);
											}
										}
									});
								}
							}
						}
					}
					{
						JMenu menuUtilitiesBuyers = new JMenu("Buyers");
						menuUtilities.add(menuUtilitiesBuyers);
						{
							JMenu menuUtilitiesClientServicing = new JMenu("Client Servicing");
							menuUtilitiesBuyers.add(menuUtilitiesClientServicing);
//							{
//								JMenuItem menutiemTagClientMailsForExport = new JMenuItem("Tag Client Mails for Export");
//								menuUtilitiesClientServicing.add(menutiemTagClientMailsForExport);
//								menutiemTagClientMailsForExport.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("TagClientMailsForExport")){
//											TagClientMailsForExport tcmfe = new TagClientMailsForExport();
//											addWindow(tcmfe, e);
//										}
//									}
//								});
//							}
							{
								JMenuItem menutieRequiredDocumentsMaitenance = new JMenuItem("Required Documents Maintenance");
								menuUtilitiesClientServicing.add(menutieRequiredDocumentsMaitenance);
								menutieRequiredDocumentsMaitenance.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("RequiredDocuments")){
											RequiredDocuments rd = new RequiredDocuments();
											addWindow(rd, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemAddEditEntityType = new JMenuItem("Add/Edit Entity Type");
								menuUtilitiesClientServicing.add(menuitemAddEditEntityType);
								menuitemAddEditEntityType.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("AddEditEntityType")){
											AddEditEntityType add_edit_entity_type = new AddEditEntityType();
											addWindow(add_edit_entity_type, e);
										}
									}
								});
							}
							{
								JMenuItem menuItemZipCodes = new JMenuItem("Zip Codes");
								menuUtilitiesClientServicing.add(menuItemZipCodes);
								menuItemZipCodes.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ZipCodes")){
											ZipCodes zip_codes = new ZipCodes();
											addWindow(zip_codes, e);
										}
									}
								});
							}
							{
								JMenuItem menuItemPrintedDocs = new JMenuItem("Printed Docs");
								menuUtilitiesClientServicing.add(menuItemPrintedDocs);
								menuItemPrintedDocs.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Printed_Documents")){
											Printed_Documents pd = new Printed_Documents();
											addWindow(pd, e);
										}
									}
								});
							}
							{
								JMenuItem menuCancelHOlding = new JMenuItem("Cancel Holding");
								menuUtilitiesClientServicing.add(menuCancelHOlding);
								menuCancelHOlding.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("CancelHolding")){
											CancelHolding ch = new CancelHolding();
											addWindow(ch, e);
										}
									}
								});
							}
							{
								JMenuItem menuOtherUnitDescription = new JMenuItem("Other Unit Descriptions");
								menuUtilitiesClientServicing.add(menuOtherUnitDescription);
								menuOtherUnitDescription.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("otherUnitDescriptions")){
											otherUnitDescriptions ch = new otherUnitDescriptions();
											addWindow(ch, e);
										}
									}
								});
							}
						}
						{
							JMenu menuUtilitieLoansAndReceivable = new JMenu("Loans And Receivable");
							menuUtilitiesBuyers.add(menuUtilitieLoansAndReceivable);
							{
								JMenuItem menuDeletePCostTCostEntries = new JMenuItem("Delete PCost/TCost Entries");
								menuUtilitieLoansAndReceivable.add(menuDeletePCostTCostEntries);
								menuDeletePCostTCostEntries.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DeletePCostTCostEntries")){
											DeletePCostTCostEntries dpte = new DeletePCostTCostEntries();
											addWindow(dpte, e);
										}
									}
								});
							}
						}
						{
							JMenu menuUtilitieCreditAndCollections = new JMenu("Credit And Collections");
							menuUtilitiesBuyers.add(menuUtilitieCreditAndCollections);
							{
								JMenuItem menuZeroOutHOADues = new JMenuItem("Zero-Out Homeowner's Account Dues");
								menuUtilitieCreditAndCollections.add(menuZeroOutHOADues);
								menuZeroOutHOADues.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("ZeroOutHOADues")){
											ZeroOutHOADues zohd = new ZeroOutHOADues();
											addWindow(zohd, e);
										}
									}
								});
							}
						}
						{
							JMenu menuUtilitieLegalAndLiaisoning = new JMenu("Legal and Liaisoning");
							menuUtilitiesBuyers.add(menuUtilitieLegalAndLiaisoning);
							{
								{
									JMenuItem menuEditAvailerType = new JMenuItem("Edit Availer");
									menuUtilitieLegalAndLiaisoning.add(menuEditAvailerType);
									menuEditAvailerType.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("EditAvailer")){
												EditAvailer eat = new EditAvailer();
												addWindow(eat, e);
											}
										}
									});
								}
								{
									JMenuItem menuEditPayee = new JMenuItem("Edit Payee");
									menuUtilitieLegalAndLiaisoning.add(menuEditPayee);
									menuEditPayee.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("EditPayee")){
												EditPayee eatp = new EditPayee();
												addWindow(eatp, e);
											}
										}
									});
								}
								{
									JMenuItem menuEditRevolvingFunds = new JMenuItem("Edit Revolving Funds");
									menuUtilitieLegalAndLiaisoning.add(menuEditRevolvingFunds);
									menuEditRevolvingFunds.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("EditRevolvingFunds")){
												EditRevolvingFunds editRevolve = new EditRevolvingFunds();
												addWindow(editRevolve, e);
											}
										}
									});
								}
								{
									JMenuItem menuEditTaxPayments = new JMenuItem("Edit Tax Payments");
									menuUtilitieLegalAndLiaisoning.add(menuEditTaxPayments);
									menuEditTaxPayments.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("EditTaxPayments")){
												EditTaxPayments editTax = new EditTaxPayments();
												addWindow(editTax, e);
											}
										}
									});
								}
								{
									JMenuItem menuAddEditTransactionRemarks = new JMenuItem("Add/Edit Remarks");
									menuUtilitieLegalAndLiaisoning.add(menuAddEditTransactionRemarks);
									menuAddEditTransactionRemarks.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("AddEditTransactionRemarks")){
												AddEditTransactionRemarks editrem = new AddEditTransactionRemarks();
												addWindow(editrem, e);
											}
										}
									});
								}
								{
									JMenuItem menuAddEditTransactionRemarks2 = new JMenuItem("Add/Edit Remarks2");
									menuUtilitieLegalAndLiaisoning.add(menuAddEditTransactionRemarks2);
									menuAddEditTransactionRemarks2.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("AddEditTransactionRemarks")){
												AddEditTransactionRemarks2 editrem2 = new AddEditTransactionRemarks2();
												addWindow(editrem2, e);
											}
										}
									});
								}
							}
						}

						JMenu menuUtilitiesLoansManagement = new JMenu("Loans Management");
						menuUtilitiesBuyers.add(menuUtilitiesLoansManagement);
						{
							JMenuItem menuDeletePostedNTCAccount = new JMenuItem("Delete Posted NTC Account");
							menuUtilitiesLoansManagement.add(menuDeletePostedNTCAccount);
							menuDeletePostedNTCAccount.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if(isNotExisting("DeletePostedNTCAccount")){
										DeletePostedNTCAccount dpntc = new DeletePostedNTCAccount();
										addWindow(dpntc, e);
									}
								}
							});
						}
						{
							JMenuItem menuLoanReleasedDetails = new JMenuItem("Loan Released Details");
							menuUtilitiesLoansManagement.add(menuLoanReleasedDetails);
							menuLoanReleasedDetails.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent e) {
									if(isNotExisting("LoanReleasedDetails")){
										LoanReleasedDetails lrd = new LoanReleasedDetails();
										addWindow(lrd, e);
									}
								}
							});
						}
						{
							JMenuItem menuCodeBreak = new JMenuItem("Code Breaker");
							menuUtilitiesLoansManagement.add(menuCodeBreak);
							menuCodeBreak.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if(isNotExisting("code_break")){
										code_break cbrk = new code_break();
										addWindow(cbrk, e);
									}
								}
							});
						}
						{
							JMenuItem menuTDBreak = new JMenuItem("Technical Description Breaker");
							menuUtilitiesLoansManagement.add(menuTDBreak);
							menuTDBreak.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if(isNotExisting("techDescBreaker")){
										techDescBreaker breaker = new techDescBreaker();
										addWindow(breaker, e);
									}
								}
							});
						}



						{
							JMenuItem menuLoanReleasedFirstRemittance = new JMenuItem("Loan Released First Remittance");
							menuUtilitiesLoansManagement.add(menuLoanReleasedFirstRemittance);
							menuLoanReleasedFirstRemittance.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if(isNotExisting("LoanReleasedFirstRemittance")){
										LoanReleasedFirstRemittance lrfr = new LoanReleasedFirstRemittance();
										addWindow(lrfr, e);
									}
								}
							});
						}
						{
							JMenuItem menuPCost_Update = new JMenuItem("PCost Update");
							menuUtilitiesLoansManagement.add(menuPCost_Update);
							menuPCost_Update.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent e) {
									if(isNotExisting("PCost_Update")){
										PCost_Update pcu = new PCost_Update();
										addWindow(pcu, e);
									}
								}
							});
						}
					}
					{
						JMenu menuUtilitiesProjects = new JMenu("Projects");
						menuUtilities.add(menuUtilitiesProjects);
						{
							{
								JMenu menuUtilitiesPropertyManagement = new JMenu("Property Management");
								menuUtilitiesProjects.add(menuUtilitiesPropertyManagement);
								{
									JMenuItem menuitemWaterBillingAdjustment = new JMenuItem("Water Reading Adjustment");
									menuUtilitiesPropertyManagement.add(menuitemWaterBillingAdjustment);
									menuitemWaterBillingAdjustment.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("Water_Reading_Adjustment")){
												Water_Reading_Adjustment wra = new Water_Reading_Adjustment();
												addWindow(wra, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemWaterDisconnection = new JMenuItem("Generate Water Disconnection Notice");
									menuUtilitiesPropertyManagement.add(menuitemWaterDisconnection);
									menuitemWaterDisconnection.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("Generate_Water_Disconnection")){
												Generate_Water_Disconnection gwd = new Generate_Water_Disconnection();
												addWindow(gwd, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemNtpNotes = new JMenuItem("PMD NTP Notes");
									menuUtilitiesPropertyManagement.add(menuitemNtpNotes);
									menuitemNtpNotes.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("NTP_Notes")){
												NTP_Notes n_n = new NTP_Notes();
												addWindow(n_n, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemUploadHappyWellCollection = new JMenuItem("Upload Happy Well Collection");
									menuUtilitiesPropertyManagement.add(menuitemUploadHappyWellCollection);
									menuitemUploadHappyWellCollection.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("UploadHappyWellCollection")) {
												UploadHappyWellCollection uhw = new UploadHappyWellCollection();
												addWindow(uhw, e);
											}
											
										}
									});
								}
							}
							{
								JMenu menuUtilitiesConstructionManagement = new JMenu("Construction Management");
								menuUtilitiesProjects.add(menuUtilitiesConstructionManagement);
								{
									JMenuItem menuHouseAccomplishments = new JMenuItem("Import House Accomplishments");
									menuUtilitiesConstructionManagement.add(menuHouseAccomplishments);
									menuHouseAccomplishments.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("ImportHouseAccomplishments_V2")){
												//ImportHouseAccomplishments iha = new ImportHouseAccomplishments();
												ImportHouseAccomplishments_V2 iha2 = new ImportHouseAccomplishments_V2();
												addWindow(iha2, e);
											}
										}
									});
								}
								{
									JMenuItem menuTechnicalDescriptionImportUtility = new JMenuItem("Technical Description Import Utility");
									menuUtilitiesConstructionManagement.add(menuTechnicalDescriptionImportUtility);
									menuTechnicalDescriptionImportUtility.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("UploadTechnicalDesc")){
												UploadTechnicalDesc utd = new UploadTechnicalDesc();
												addWindow(utd, e);
											}
										}
									});
								}
								{
									JMenuItem menuConstructionAccomplishmentStage = new JMenuItem("Construction Accomplishment Stage");
									menuUtilitiesConstructionManagement.add(menuConstructionAccomplishmentStage);
									menuConstructionAccomplishmentStage.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("ConstructionAccomplishmentStage")){
												ConstructionAccomplishmentStage cas= new ConstructionAccomplishmentStage();
												addWindow(cas, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemNtpNotes = new JMenuItem("CMD NTP Notes");
									menuUtilitiesConstructionManagement.add(menuitemNtpNotes);
									menuitemNtpNotes.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("NTP_Notes")){
												NTP_Notes nn = new NTP_Notes();
												addWindow(nn, e);
											}
										}
									});
								}

							}
							{
								JMenuItem menuUtilitiesSpecialProjectsMonitoring = new JMenuItem("Special Projects Monitoring");
								menuUtilitiesProjects.add(menuUtilitiesSpecialProjectsMonitoring);	
								menuUtilitiesSpecialProjectsMonitoring.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("SpecialProjectsMonitoring")){
											SpecialProjectsMonitoring spm= new SpecialProjectsMonitoring();
											addWindow(spm, e);
										}
									}
								});
							}
						}
					}
					{
						JMenu menuTripping = new JMenu("Tripping");
						menuUtilities.add(menuTripping);
						{
							{

								JMenuItem menuitemDriveEntry= new JMenuItem("Driver Entry");
								menuTripping.add(menuitemDriveEntry);
								menuitemDriveEntry.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DriverEntry")){
											DriverEntry de = new DriverEntry();
											addWindow(de, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemDriverVehicles = new JMenuItem("Driver Vehicles");
								menuTripping.add(menuitemDriverVehicles);
								menuitemDriverVehicles.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("DateCheckerTest")){
											Driver_Vehicles dv = new Driver_Vehicles();
											addWindow(dv, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemRate = new JMenuItem("Tripping Rate");
								menuTripping.add(menuitemRate);
								menuitemRate.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Tripping_Rate")){
											Tripping_Rate tr = new Tripping_Rate();
											addWindow(tr, e);
										}
									}
								});
							}
							{

								JMenuItem menuitemTripPurpose = new JMenuItem("Trip Purpose");
								menuTripping.add(menuitemTripPurpose);
								menuitemTripPurpose.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Trip_Purpose")){
											Trip_Purpose tp = new Trip_Purpose();
											addWindow(tp, e);
										}
									}
								});
							}
							{

								JMenuItem menuitemMeetingPlace = new JMenuItem("Meeting Place");
								menuTripping.add(menuitemMeetingPlace);
								menuitemMeetingPlace.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("Meeting_Place")){
											Meeting_Place mp = new Meeting_Place();
											addWindow(mp, e);
										}
									}
								});
							}
						}
					}
					{
						JMenu menuSMS = new JMenu("SMS");
						menuUtilities.add(menuSMS);
						{
							{
								JMenuItem menuitemSMS = new JMenuItem("Terraverde SMS");
								menuSMS.add(menuitemSMS);
								menuitemSMS.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("jSMS")){
											jSMS sms = new jSMS();
											addWindow(sms, e);
										}
									}
								});
							}
							{
								JMenuItem menuitemSMSPool = new JMenuItem("SMS Pool");
								menuSMS.add(menuitemSMSPool);
								menuitemSMSPool.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if(isNotExisting("jSMS_pool")){
											jSMS_pool sms = new jSMS_pool();
											addWindow(sms, e);
										}
									}
								});
							}
							//Added by Erick 2019-09-27
							{
								JMenu menufixedasset = new JMenu("Asset Inventory");
								menuUtilities.add(menufixedasset);
								{
									JMenuItem menuitemAssetInventory = new JMenuItem("Inventory");
									menufixedasset.add(menuitemAssetInventory);
									menuitemAssetInventory.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("AssetInventory")){
												AssetInventory Ai = new AssetInventory();
												addWindow(Ai, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemreconofasset = new JMenuItem("Asset Recon");
									menufixedasset.add(menuitemreconofasset);
									menuitemreconofasset.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("ReconOfAsset")){
												ReconOfAsset ra = new ReconOfAsset();
												addWindow(ra, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemtagdisposalforapproval = new JMenuItem("Tag disposal for approval");
									menufixedasset.add(menuitemtagdisposalforapproval);
									menuitemtagdisposalforapproval.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("TagDisposalForApproval")){
												TagDisposalForApproval tda = new TagDisposalForApproval();
												addWindow(tda, e);
											}
										}
									});
								}
								{
									JMenuItem menuitemtwithoutassetnumber = new JMenuItem("Without Asset Number");
									menufixedasset.add(menuitemtwithoutassetnumber);
									menuitemtwithoutassetnumber.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if(isNotExisting("Withoutassetnumber")){
												Withoutassetnumber wan = new Withoutassetnumber();
												addWindow(wan, e);
											}
										}
									});
								}
								{
									JMenuItem menuiteminactivateasset = new JMenuItem("Inactivate Asset");
									menufixedasset.add(menuiteminactivateasset);
									menuiteminactivateasset.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if (isNotExisting("InactivateAssets")) {
												InactivateAssets ia = new InactivateAssets();
												addWindow(ia, e);
											}
										}
									});
								}
							}
//							{
//								JMenuItem menuDateChecker = new JMenuItem("Date Checker");
//								menuUtilities.add(menuDateChecker);
//								menuDateChecker.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("DateCheckerTest")){
//											DateCheckerTest dc = new DateCheckerTest();
//											addWindow(dc, e);
//										}
//									}
//								});
//							}
//							{
//								JMenu menuLegacy = new JMenu("iTsReal Utilities");
//								menuUtilities.add(menuLegacy);
//								{
//									{
//										JMenuItem menuItsReal_PaymentTemplate = new JMenuItem("Payment Template");
//										menuLegacy.add(menuItsReal_PaymentTemplate);
//										menuItsReal_PaymentTemplate.addActionListener(new ActionListener() {
//											public void actionPerformed(ActionEvent e) {
//												if(isNotExisting("iutil_payment_template")){
//													iutil_payment_template iutil_pt = new iutil_payment_template();
//													addWindow(iutil_pt, e);
//												}
//											}
//										});
//									}
//								}
//							}
//							{
//								JMenuItem menuDateChecker = new JMenuItem("Upload Payment");
//								menuUtilities.add(menuDateChecker);
//								menuDateChecker.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("UploadPayment")){
//											UploadPayment up = new UploadPayment();
//											addWindow(up, e);
//										}
//									}
//								});
//							}
						}
					}
//					{
//						JMenu menuLegacy = new JMenu("iTsReal Utilities");
//						menuUtilities.add(menuLegacy);
//						{
//							{
//								JMenuItem menuItsReal_PaymentTemplate = new JMenuItem("Payment Template");
//								menuLegacy.add(menuItsReal_PaymentTemplate);
//								menuItsReal_PaymentTemplate.addActionListener(new ActionListener() {
//									public void actionPerformed(ActionEvent e) {
//										if(isNotExisting("iutil_payment_template")){
//											iutil_payment_template iutil_pt = new iutil_payment_template();
//											addWindow(iutil_pt, e);
//										}
//									}
//								});
//							}
//						}
//					}
				}
				{
					WindowMenu menuWindow = new WindowMenu(DesktopPane, pnlNorth);
					menuBar.add(menuWindow);
				}
				{
					JMenu menuSystem = new JMenu("System");
					menuBar.add(menuSystem);
					menuSystem.setMnemonic(KeyEvent.VK_S);
					{
						JMenuItem menuitemTransferJournalEntries = new JMenuItem("Transfer Journal Entries (Postbook)");
						menuSystem.add(menuitemTransferJournalEntries);
						menuitemTransferJournalEntries.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("TransferJournalEntries")){
									TransferJournalEntries tje = new TransferJournalEntries();
									addWindow(tje, e);
								}
							}
						});
					}
					{
						JMenuItem menuitemTransferJournalEntries_iTsReal = new JMenuItem("Transfer Journal Entries (iTs Real)");
						menuSystem.add(menuitemTransferJournalEntries_iTsReal);
						menuitemTransferJournalEntries_iTsReal.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("menuitemTransferJournalEntries_iTsReal")){
									TransferJournalEntries_iTsReal tje = new TransferJournalEntries_iTsReal();
									addWindow(tje, e);
								}
							}
						});
					}
					{
						menuSystem.add(new JSeparator());
					}
					{
						JMenuItem menuitemPreferences = new JMenuItem("Preferences");
						menuSystem.add(menuitemPreferences);
						menuitemPreferences.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("Preferences")){
									Preferences p = new Preferences();
									addWindow(p, e);
								}
							}
						});
					}
					{
						JMenuItem menuitemAccessMaintenance = new JMenuItem("Access Maintenance");
						menuSystem.add(menuitemAccessMaintenance);
						menuitemAccessMaintenance.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AccessMaintenance")){
									AccessMaintenance am = new AccessMaintenance();
									addWindow(am, e);
								}
							}
						});
					}
					{
						JMenuItem menuitemAddEditPosition = new JMenuItem("Add/Edit Position");
						menuSystem.add(menuitemAddEditPosition);
						menuitemAddEditPosition.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddandEditPosition")){
									AddandEditPosition ae = new AddandEditPosition();
									addWindow(ae, e);
								}
							}
						});
					}
					{
						menuSystem.add(new JSeparator());
					}
					{
						JMenuItem menuitemDCRF = new JMenuItem("Data Change Request");
						menuSystem.add(menuitemDCRF);
						menuitemDCRF.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("DataChangeRequest")){
									DataChangeRequest dcrf = new DataChangeRequest();
									addWindow(dcrf, e);
								}
							}
						});
					}
					{
						JMenuItem menuitemDCRF_Reports = new JMenuItem("DCRF Reports");
						menuSystem.add(menuitemDCRF_Reports);
						menuitemDCRF_Reports.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("DCRF_Reports")){
									DCRF_Reports dcrf_rpt = new DCRF_Reports();
									addWindow(dcrf_rpt, e);
								}
							}
						});
					}
					{
						JMenuItem menuitemHolidaySetter = new JMenuItem("Add/Edit Holidays");
						menuSystem.add(menuitemHolidaySetter);
						menuitemHolidaySetter.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("Add_Edit_Holidays")){
									Add_Edit_Holidays holiday_setter = new Add_Edit_Holidays();
									addWindow(holiday_setter, e);
								}
							}
						});
					}
					{
						JMenuItem menuUploadEmployee = new JMenuItem("Upload Employee");
						menuSystem.add(menuUploadEmployee);
						menuUploadEmployee.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("UploadEmployee")){
									UploadEmployee ue = new UploadEmployee();
									addWindow(ue, e);
								}
							}
						});
					}
					{
						JMenuItem menuUploadDcrfInactivePayments = new JMenuItem("Dcrf Inactive Payments");
						menuSystem.add(menuUploadDcrfInactivePayments);
						menuUploadDcrfInactivePayments.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("DcrfInactivePayments")){
									DcrfInactivePayments dip = new DcrfInactivePayments();
									addWindow(dip, e);
								}
							}
						});
					}
					{
						JMenuItem menuCopyorTransferAccess = new JMenuItem("Copy or Transfer Access");
						menuSystem.add(menuCopyorTransferAccess);
						menuCopyorTransferAccess.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("CopyorTransferAccess")){
									CopyorTransferAccess cta = new CopyorTransferAccess();
									addWindow(cta, e);
								}
							}
						});
					}
					{
						JMenuItem menuJSystemModules = new JMenuItem("JSystem Modules");
						menuSystem.add(menuJSystemModules);
						menuJSystemModules.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("JSystemModule")) {
									JSystemModule jm = new JSystemModule();
									addWindow(jm, e);
								}
							}
						});
					}
				}
				{
					menuBookmark = new JMenu("Bookmark");
					menuBar.add(menuBookmark);
					menuBookmark.setMnemonic(KeyEvent.VK_B);
				}
				{
					JMenu menuHelp = new JMenu("Help");
					menuBar.add(menuHelp);
					menuHelp.setMnemonic(KeyEvent.VK_H);
					{
						JMenuItem menuitemConsole = new JMenuItem("Console");
						menuHelp.add(menuitemConsole);
						menuitemConsole.addActionListener(this);
					}
					{
						JMenuItem menuitemAboutBOIModule = new JMenuItem("About BOI Module");
						menuHelp.add(menuitemAboutBOIModule);
						menuitemAboutBOIModule.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									Class clsJiFrame1 = Class.forName("Buyers.ClientServicing.CARD");
									_JInternalFrame JiFrame1 = (_JInternalFrame)(clsJiFrame1.newInstance());

									if(isNotExisting("CARD")){
										addWindow(JiFrame1, e);
									}
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								} catch (InstantiationException e1) {
									e1.printStackTrace();
								} catch (IllegalAccessException e1) {
									e1.printStackTrace();
								}
							}
						});
					}
				}
				{
					JMenu menuAdmin = new JMenu("Admin");
					//menuBar.add(menuAdmin);
					menuAdmin.setMnemonic(KeyEvent.VK_A);
					/*{
						JMenuItem menuitemAddEditUser = new JMenuItem("Add/Edit User");
						menuAdmin.add(menuitemAddEditUser);
						menuitemAddEditUser.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddEditUser")){
									AddEditUser aeu = new AddEditUser();
									addWindow(aeu);
								}
							}
						});
					}*/
					/*{
						JMenuItem menuitemUserAccess = new JMenuItem("User Access");
						menuAdmin.add(menuitemUserAccess);
						menuitemUserAccess.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("UserAccess")){
									UserAccess ua = new UserAccess();
									addWindow(ua);
								}
							}
						});
					}*/
					{
						menuAdmin.add(new JSeparator());
					}
					/*{
						JMenuItem menuitemAddDivision = new JMenuItem("Add Division");
						menuAdmin.add(menuitemAddDivision);
						menuitemAddDivision.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddDivision")){
									AddDivision add_div = new AddDivision();
									addWindow(add_div, e);
								}
							}
						});
					}*/
					/*{
						JMenuItem menuitemAddDepartment = new JMenuItem("Add Department");
						menuAdmin.add(menuitemAddDepartment);
						menuitemAddDepartment.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddDepartment")){
									AddDepartment add_dept = new AddDepartment();
									addWindow(add_dept, e);
								}
							}
						});
					}*/
					/*{
						JMenuItem menuitemAddProject = new JMenuItem("Add Project");
						menuAdmin.add(menuitemAddProject);
						menuitemAddProject.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddProject")){
									AddProject add_proj = new AddProject();
									addWindow(add_proj, e);
								}
							}
						});
					}
					{
						JMenuItem menuitemAddSubProject = new JMenuItem("Add Sub Project");
						menuAdmin.add(menuitemAddSubProject);
						menuitemAddSubProject.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddSubProject")){
									AddSubProject add_sub_proj = new AddSubProject();
									addWindow(add_sub_proj, e);
								}
							}
						});
					}
					{
						JMenuItem menuitemAddCompany = new JMenuItem("Add Company");
						menuAdmin.add(menuitemAddCompany);
						menuitemAddCompany.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddCompany")){
									AddCompany add_comp = new AddCompany();
									addWindow(add_comp, e);
								}

							}
						});
					}*/
					/*{
						menuAdmin.add(new JSeparator());
					}
					{
						JMenuItem menuitemAddEditPF = new JMenuItem("Add/ Edit PF Entries");
						menuAdmin.add(menuitemAddEditPF);
						menuitemAddEditPF.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								if(isNotExisting("AddEditPF_Entries")){
									AddEditPF_Entries add_pf = new AddEditPF_Entries();
									addWindow(add_pf);
								}

							}
						});
					}*/
					{
						menuAdmin.add(new JSeparator());
					}					
					{
						menuAdmin.add(new JSeparator());
					}
				}
				{
					menuBar.add(Box.createGlue());
				}
				{
					//menuBar.add(searchField);
				}
				{
					btnMinimize = new _JMenuToolbarButton(MINIMIZE_ICON);
					menuBar.add(btnMinimize);
					btnMinimize.setActionCommand("Minimize");
					btnMinimize.setToolTipText("Minimize");
					btnMinimize.setFocusable(false);
					btnMinimize.addActionListener(this);
				}
				{
					btnMaximize = new _JMenuToolbarButton(MAXIMIZE_ICON);
					menuBar.add(btnMaximize);
					btnMaximize.setActionCommand("Maximize");
					btnMaximize.setToolTipText("Restore Window");
					btnMaximize.setFocusable(false);
					btnMaximize.addActionListener(this);
				}
				{
					btnClose = new _JMenuToolbarButton(CLOSE_ICON);
					menuBar.add(btnClose);
					btnClose.setActionCommand("Close");
					btnClose.setToolTipText("Close");
					btnClose.setFocusable(false);
					btnClose.addActionListener(this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*ArrayList<String> listAdmin = new ArrayList<String>();
		listAdmin.add("900668");//aagonzales
		listAdmin.add("900449");//eagonzales
		listAdmin.add("900834");//ccpaquibot
		listAdmin.add("900523");//eeesto
		listAdmin.add("900876");//jffatallo
		listAdmin.add("900462");//jffatallo

		if(!listAdmin.contains(UserInfo.EmployeeCode)){
			_Home_JSystem.menuAccess(menuBar);
		}*/

		//for testing purposes - remove comment when deployed; (purpose - this automatically displays a newly-created menuitem upon log-in)
		_Home_JSystem.menuAccess(menuBar);
		FncGlobal.menuBar = menuBar;

		/**
		 * 
		 */
		menuBookmark.setVisible(true);
		reloadBookmark();
		//setTime();
		//menuitemOtherRequest.doClick();
		//menuitemOtherRequest2.doClick();

		if(UserInfo.ADMIN) {
			Count();
		}
	} 

	public static void addWindow(_JInternalFrame internalFrame) {
		internalFrame.setTitleMenu(null);
		DesktopPane.add(internalFrame);
		internalFrame.moveToFront();
		try {
			internalFrame.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static void addWindow(_JInternalFrame internalFrame, ActionEvent event) {
		if(internalFrame.getTitleMenu() == null){
			if(event != null){

				if((event.getSource() instanceof JButton)){
					internalFrame.setTitleMenu(((JButton)event.getSource()).getText());
					FncGlobal.AuditLogs(UserInfo.EmployeeCode, ((JButton)event.getSource()).getText().replace("'", "''"), "Opened Module");
				}
				if((event.getSource() instanceof JMenuItem)){
					internalFrame.setTitleMenu(((JMenuItem)event.getSource()).getText());
					FncGlobal.AuditLogs(UserInfo.EmployeeCode, ((JMenuItem)event.getSource()).getText().replace("'", "''"), "Opened Module");
				}
			}
		}

		DesktopPane.add(internalFrame);
		internalFrame.moveToFront();

		try {
			internalFrame.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static void addWindowMouse(_JInternalFrame internalFrame, MouseEvent event) {
		if(internalFrame.getTitleMenu() == null){
			if(event != null){
				if((event.getSource() instanceof JButton)){
					internalFrame.setTitleMenu(((JButton)event.getSource()).getText());
				}
				if((event.getSource() instanceof JMenuItem)){
					internalFrame.setTitleMenu(((JMenuItem)event.getSource()).getText());
				}
			}
		}

		DesktopPane.add(internalFrame);
		internalFrame.moveToFront();
		try {
			internalFrame.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static Boolean isNotExisting(String simpleName) {
		boolean isExisting = true;
		for(JInternalFrame frame : DesktopPane.getAllFrames()){
			if(frame.getClass().getSimpleName().equals(simpleName)){
				frame.moveToFront();
				try {
					frame.setSelected(true);
				} catch (PropertyVetoException e) { }
				isExisting = false;
				break;
			}
		}
		return isExisting;
	}

	public void maximizeAllFrames(_JInternalFrame selectedFrame) {
		for(Component comp : DesktopPane.getComponents()){
			if(comp instanceof _JInternalFrame){
				_JInternalFrame frame = (_JInternalFrame) comp;
				if(frame.isMaximizable()){
					if(selectedFrame != frame){
						//System.out.printf("JInternalFrame: %s%n", frame.getName());

						InternalFrameUI ui = frame.getUI();
						if (ui instanceof BasicInternalFrameUI){
							((BasicInternalFrameUI) ui).setNorthPane(null);
							frame.setBorder(BorderFactory.createEmptyBorder());
						}
						//frame.setPreferredSize(selectedFrame.getPreferredSize());
						frame.setSize(selectedFrame.getSize());
					}
				}
			}
		}
		setMenubarButtonVisible(true);
	}

	public void startProgress(String caption) {
		pnlSouthCenter.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		progressBar.setVisible(true);
		progressBar.setStringPainted(true);
		progressBar.setString(caption);
		progressBar.setIndeterminate(true);
	}

	public void stopProgress() {
		progressBar.setIndeterminate(false);
		progressBar.setVisible(false);
		pnlSouthCenter.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}

	public void setMenubarButtonVisible(Boolean isVisible) {
		btnMinimize.setVisible(isVisible);
		btnMaximize.setVisible(isVisible);
		btnClose.setVisible(isVisible);
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
			return;
		}
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_audit_log_details('"+UserInfo.EmployeeCode+"', false)";
		db.select(SQL);

		FncSystem.out("Logged Out of System", SQL);

		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("UPDATE rf_queuing_system SET status_id = 'A', processed_by = null WHERE status_id = 'P' and processed_by = '"+UserInfo.EmployeeCode+"' and date_created::date = current_date", true);
		dbExec.commit();

		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) { }

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowDeiconified(WindowEvent e) { }

	@Override
	public void windowActivated(WindowEvent e) { }

	@Override
	public void windowDeactivated(WindowEvent e) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() != null){
			/*if(e.getActionCommand().equals("Console")){
			if(FncGlobal.lpsOut != null){
				Console console  = new Console(FncGlobal.homeMDI, "Console");
				console.setLocationRelativeTo(null);
				console.setVisible(true);
			}
		}*/

			if(e.getActionCommand().equals("Minimize")){
				try {
					_JInternalFrame oldFrame = (_JInternalFrame) DesktopPane.getSelectedFrame();
					oldFrame.setIcon(true);
					oldFrame.requestFocus();

					setTitle(FncGlobal.ORIGINAL_TITLE);
					setMenubarButtonVisible(DesktopPane.hasMaximizeFrame());
				} catch (PropertyVetoException e1) {

				}
			}

			if(e.getActionCommand().equals("Maximize")){
				System.out.println("Dumaan dito sa maximize");
				List<_JInternalFrame> listFrame = new ArrayList<_JInternalFrame>();
				for(int x = DesktopPane.getComponents().length -1; x >= 0; x--){
					Component comp = DesktopPane.getComponents()[x];
					if(comp instanceof _JInternalFrame){

						if(comp.getClass().getSimpleName().equals("CARD") || comp.getClass().getSimpleName().equals("ClientInformation")){

							if(comp.getClass().getSimpleName().equals("CARD")){
								System.out.println("CARD");
								CARD oldFrame = (CARD) comp;
								DesktopPane.remove(oldFrame);
								if(oldFrame.getTimerTitle() != null){
									oldFrame.getTimerTitle().stop();
								}
								oldFrame.dispose();

								if(oldFrame.isMaximizable()){
									CARD card = new CARD(oldFrame.getTitle(), true, true, true, true);
									card.setContentPane(oldFrame.getContentPane());
									card.setPrimaryTitle(oldFrame.getPrimaryTitle());
									card.setSecondaryTitle(oldFrame.getSecondaryTitle());

									if(oldFrame.getSecondaryTitle() != null){
										card.startTimerStatus();
									}

									listFrame.add(card);

									try {
										card.setSize(oldFrame.getSIZE_OLD());
										card.setPreferredSize(oldFrame.getSIZE_OLD());
									} catch (NullPointerException e1) { }
								}
							}
							if(comp.getClass().getSimpleName().equals("ClientInformation")){
								System.out.println("Client Information");
								ClientInformation oldFrame = (ClientInformation) comp;
								DesktopPane.remove(oldFrame);
								if(oldFrame.getTimerTitle() != null){
									oldFrame.getTimerTitle().stop();
								}
								oldFrame.dispose();

								if(oldFrame.isMaximizable()){
									ClientInformation ci = new ClientInformation(oldFrame.getTitle(), true, true, true, true);
									ci.setContentPane(oldFrame.getContentPane());
									ci.setPrimaryTitle(oldFrame.getPrimaryTitle());
									ci.setSecondaryTitle(oldFrame.getSecondaryTitle());

									if(oldFrame.getSecondaryTitle() != null){
										ci.startTimerStatus();
									}

									listFrame.add(ci);

									try {
										ci.setSize(oldFrame.getSIZE_OLD());
										ci.setPreferredSize(oldFrame.getSIZE_OLD());
									} catch (NullPointerException e1) { }
								}
							}

						}else{
							System.out.println("Maximize");
							_JInternalFrame oldFrame = (_JInternalFrame) comp;
							DesktopPane.remove(oldFrame);
							if(oldFrame.isMaximizable()){

								_JInternalFrame newFrame = new _JInternalFrame(oldFrame.getTitle(), true, true, true, true);
								newFrame.setContentPane(oldFrame.getContentPane());
								newFrame.setPrimaryTitle(oldFrame.getPrimaryTitle());
								newFrame.setSecondaryTitle(oldFrame.getSecondaryTitle());

								listFrame.add(newFrame);

								try {
									newFrame.setSize(oldFrame.getSIZE_OLD());
									newFrame.setPreferredSize(oldFrame.getSIZE_OLD());
								} catch (NullPointerException e1) { }
							}
						}
					}
				}
				setTitle(FncGlobal.ORIGINAL_TITLE);
				setMenubarButtonVisible(false);

				for(_JInternalFrame frame : listFrame){
					addWindow(frame, null);
					frame.requestFocus();
				}

				DesktopPane.revalidate();
				DesktopPane.repaint();
			}

			if(e.getActionCommand().equals("Close")){
				_JInternalFrame oldFrame = (_JInternalFrame) DesktopPane.getSelectedFrame();
				oldFrame.dispose();

				pgSelect db = new pgSelect();
				String SQL = "SELECT sp_audit_log_details('"+UserInfo.EmployeeCode+"', false)";
				db.select(SQL);

				FncSystem.out("Logged Out of System", SQL);

				setTitle(FncGlobal.ORIGINAL_TITLE);
				setMenubarButtonVisible(DesktopPane.hasMaximizeFrame());
			}
		}
	}

	/**
	 * @return the desktopPane
	 */
	public MDIDesktopPane getDesktopPane() {
		return DesktopPane;
	}

	/**
	 * @param desktopPane the desktopPane to set
	 */
	public void setDesktopPane(MDIDesktopPane desktopPane) {
		DesktopPane = desktopPane;
	}

	public void reloadBookmark() {
		menuBookmark.removeAll();
		toolbar.removeAll();

		pgSelect db = new pgSelect();
		db.select("SELECT * FROM rf_bookmarks WHERE emp_code = '"+ UserInfo.EmployeeCode +"' ORDER BY module_name;");

		//if(UserInfo.Branch.trim().equals("10") == false){

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				final String class_location = (String) db.getResult()[x][1];
				final String module_name = (String) db.getResult()[x][2];
				final String class_name = class_location.split("\\.")[class_location.split("\\.").length - 1];

				JMenuItem menuitemAboutBOIModule = new JMenuItem(module_name);
				menuBookmark.add(menuitemAboutBOIModule);
				menuitemAboutBOIModule.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addWindowFromBookmark(class_location, module_name, class_name, e);
					}
				});

				_JButton btnToolbar = new _JButton(module_name);//module_name
				btnToolbar.setToolTipText(module_name);
				btnToolbar.setAdditionalInfo(class_location);
				btnToolbar.setForeground(Color.BLACK);
				btnToolbar.setFont(UIManager.getFont("MenuItem.font").deriveFont(10f));
				btnToolbar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//addWindowFromBookmark(class_location, module_name, class_name, e);
					}
				});

				btnToolbar.addMouseListener(new MouseListener() {

					public void mouseReleased(MouseEvent e) {

					}

					public void mousePressed(MouseEvent e) {

					}

					public void mouseExited(MouseEvent e) {

					}

					public void mouseEntered(MouseEvent e) {

					}

					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount() == 1){
							System.out.println("Dumaan dito");

							if(isNotExisting(class_name)){
								System.out.printf("Display class location: %s%n", class_location);
								System.out.printf("Display module name: %s%n", module_name);
								System.out.printf("Display class_name: %s%n", class_name);
								addWindowFromBookmark(class_location, module_name, class_name, e);
							}else{
								JOptionPane.showMessageDialog(Home_JSystem.this, String.format("%s is already open.", module_name), module_name, JOptionPane.INFORMATION_MESSAGE);
							}

						}
					}
				});

				btnToolbar.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						if(e.isPopupTrigger()){
							try {
								initializeMenu(e).show((_JButton)e.getSource(), e.getX(), e.getY());
							} catch (NullPointerException e1) { }
						}
					}
					public void mousePressed(MouseEvent e) {
						if(e.isPopupTrigger()){
							try {
								initializeMenu(e).show((_JButton)e.getSource(), e.getX(), e.getY());
							} catch (NullPointerException e1) { }
						}
					}

					public JPopupMenu initializeMenu(final MouseEvent e) {
						JMenuItem menuViewRequests = new JMenuItem("Delete");
						menuViewRequests.setFont(menuViewRequests.getFont().deriveFont(10f));
						menuViewRequests.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								_JButton btn = (_JButton) e.getSource();

								String module = btn.getText();
								String class_name = btn.getAdditionalInfo();

								System.out.printf("Module: %s (%s)%n", btn.getText(), btn.getAdditionalInfo());

								pgUpdate db = new pgUpdate();
								db.executeUpdate("DELETE FROM rf_bookmarks WHERE TRIM(emp_code) = '"+ UserInfo.EmployeeCode +"' AND TRIM(module_name) = '"+ module +"' AND TRIM(class_name) = '"+ class_name +"';", false);
								db.commit();
								FncGlobal.homeMDI.reloadBookmark();
							}
						});

						JPopupMenu menu = new JPopupMenu();
						menu.add(menuViewRequests);
						return menu;
					}
				});

				toolbar.add(btnToolbar);
				toolbar.setLayout(new GridLayout(1, 0));
			}
		}
		//}
	}

	private void addWindowFromBookmark(String class_location, String module_name, String class_name, ActionEvent e) {
		try {
			Class classInternalFrame = Class.forName(class_location);
			_JInternalFrame internalFrame = (_JInternalFrame)(classInternalFrame.newInstance());

			if(isNotExisting(class_name)){
				addWindow(internalFrame, e);
			}else{
				JOptionPane.showMessageDialog(Home_JSystem.this, String.format("%s is already open.", module_name), module_name, JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}

	private void addWindowFromBookmark(String class_location, String module_name, String class_name, MouseEvent e) {
		try {
			Class classInternalFrame = Class.forName(class_location);
			_JInternalFrame internalFrame = (_JInternalFrame)(classInternalFrame.newInstance());

			System.out.println("Mouseevent");
			System.out.printf("Display value of is existing: %s", isNotExisting(class_name));


			if(isNotExisting(class_name)){
				addWindowMouse(internalFrame, e);
			}else{
				JOptionPane.showMessageDialog(Home_JSystem.this, String.format("%s is already open.", module_name), module_name, JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}

	private void CreateCutOffControl() {
		final JLabel[] lblBranch;
		final JLabel[] lblActive; 

		final pgSelect dbExec = new pgSelect(); 
		dbExec.select("select y.branch_alias, now()::date::varchar || ' ' || (cut_off_time + interval '1 second')::varchar \n" + 
				"from mf_cut_off x\n" + 
				"inner join mf_office_branch y on x.branch = y.branch_id\n" + 
				"where dow::int = extract(dow from now())::int\n" + 
				"order by y.branch_id"); 

		panCutOff = new JXPanel(new BorderLayout(5, 5)); 
		panCutOff.setPreferredSize(new Dimension(500, 150));
		{	
			{
				JXPanel panBranch = new JXPanel(new GridLayout(dbExec.getRowCount(), 1, 5, 5)); 
				panCutOff.add(panBranch, BorderLayout.LINE_START); 
				panBranch.setPreferredSize(new Dimension(100, 0));
				{
					lblBranch = new JLabel[dbExec.getRowCount()]; 
					for (int x=0; x<dbExec.getRowCount(); x++) {
						lblBranch[x] = new JLabel(dbExec.getResult()[x][0].toString());
						panBranch.add(lblBranch[x]); 
						lblBranch[x].setHorizontalAlignment(JLabel.LEFT);
						lblBranch[x].setFont(FncLookAndFeel.systemFont_Bold.deriveFont(10f));
					}
				}
			}
			{
				JXPanel panStatus = new JXPanel(new GridLayout(1, 2, 5, 5)); 
				panCutOff.add(panStatus, BorderLayout.CENTER);
				{
					{
						JXPanel panTime = new JXPanel(new GridLayout(dbExec.getRowCount(), 1, 5, 5));
						panStatus.add(panTime);
						{
							dteDate = new _JDateChooser[dbExec.getRowCount()];
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

							for (int x=0; x<dbExec.getRowCount(); x++) { 
								dteDate[x] = new _JDateChooser("hh:mm:ss", "##:##:##", '_');
								panTime.add(dteDate[x]); 
								try {
									if (FncGlobal.GetBoolean("select cut_off_active \n" + 
											"from mf_cut_off x \n" + 
											"inner join mf_office_branch y on x.branch = y.branch_id \n" + 
											"where dow::int = extract(dow from now())::int and y.branch_alias = '"+dbExec.getResult()[x][0].toString()+"'")) {
										dteDate[x].setDate(formatter.parse(dbExec.getResult()[x][1].toString()));
									} else {
										dteDate[x].setDate(null);
									}

								} catch (ParseException e) {
									e.printStackTrace();
								}
								dteDate[x].getCalendarButton().setVisible(false);
								dteDate[x].setEditable(FncGlobal.GetBoolean("select exists(select * from (select distinct unnest(x.accessed_by) as \"user\" from mf_cut_off x) a where a.\"user\" = '"+UserInfo.EmployeeCode+"')"));
								dteDate[x].getDateEditor().getUiComponent().setFocusable(false);
							}
						}
					}
					{
						JXPanel panDivActive = new JXPanel(new GridLayout(dbExec.getRowCount(), 1, 5, 5));
						panStatus.add(panDivActive);
						{
							final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							lblActive = new JLabel[dbExec.getRowCount()]; 
							for (int x=0; x<dbExec.getRowCount(); x++) {
								final Integer intIdx = x; 
								lblActive[x] = new JLabel("Active");
								panDivActive.add(lblActive[x]);
								lblActive[x].setToolTipText(dbExec.getResult()[x][0].toString());
								if (FncGlobal.GetBoolean("select cut_off_active \n" + 
										"from mf_cut_off x \n" + 
										"inner join mf_office_branch y on x.branch = y.branch_id \n" + 
										"where dow::int = extract(dow from now())::int and y.branch_alias = '"+dbExec.getResult()[intIdx][0].toString()+"'")) {
									lblActive[x].setBorder(BorderFactory.createLoweredSoftBevelBorder());
									lblActive[intIdx].setText("Active");
								} else {
									lblActive[x].setBorder(BorderFactory.createRaisedBevelBorder());
									lblActive[intIdx].setText("Inactive");
								} 
								lblActive[x].setHorizontalAlignment(JLabel.CENTER);
								lblActive[x].setToolTipText("Click the date field to change date. Allowed only when cut-off is active.");
								lblActive[x].addMouseListener(new MouseListener() {
									public void mouseReleased(MouseEvent e) {

									}

									public void mousePressed(MouseEvent e) {

									}

									public void mouseExited(MouseEvent e) {

									}

									public void mouseEntered(MouseEvent e) {

									}

									public void mouseClicked(MouseEvent e) {
										if (FncGlobal.GetBoolean("select exists(select * from (select distinct unnest(x.accessed_by) as \"user\" from mf_cut_off x) a where a.\"user\" = '"+UserInfo.EmployeeCode+"')")) {
											pgUpdate dbUpdate = new pgUpdate(); 
											if (lblActive[intIdx].getBorder()==BorderFactory.createLoweredSoftBevelBorder()) {
												lblActive[intIdx].setBorder(BorderFactory.createRaisedBevelBorder());
												lblActive[intIdx].setText("Inactive");

												dbUpdate.executeUpdate("update mf_cut_off z \n" + 
														"set cut_off_active = false \n" + 
														"from mf_cut_off x \n" + 
														"inner join mf_office_branch y on x.branch = y.branch_id \n" + 
														"where x.dow::int = extract(dow from now())::int and y.branch_alias = '"+dbExec.getResult()[intIdx][0].toString()+"' \n" + 
														"and x.dow = z.dow and x.branch = z.branch", true);

												dteDate[intIdx].setDate(null);

												dbUpdate.executeUpdate("insert into mf_audit_trail (system_id, activity, user_code, date_upd, remarks)\n" + 
														"values ('HOME', 'CUT-OFF CHANGE', '"+UserInfo.EmployeeCode+"', now(), 'cut-off deactivated; branch: "+lblBranch[intIdx].getText()+"; dow: ' || extract(dow from now())::int::varchar || '; ')", true); 
											} else {
												lblActive[intIdx].setBorder(BorderFactory.createLoweredSoftBevelBorder());
												lblActive[intIdx].setText("Active");

												dbUpdate.executeUpdate("update mf_cut_off z \n" + 
														"set cut_off_active = true \n" + 
														"from mf_cut_off x \n" + 
														"inner join mf_office_branch y on x.branch = y.branch_id \n" + 
														"where x.dow::int = extract(dow from now())::int and y.branch_alias = '"+dbExec.getResult()[intIdx][0].toString()+"' \n" + 
														"and x.dow = z.dow and x.branch = z.branch", true);

												try {
													dteDate[intIdx].setDate(formatter.parse(dbExec.getResult()[intIdx][1].toString()));
												} catch (ParseException e1) {
													e1.printStackTrace();
												}

												dbUpdate.executeUpdate("insert into mf_audit_trail (system_id, activity, user_code, date_upd, remarks)\n" + 
														"values ('HOME', 'CUT-OFF CHANGE', '"+UserInfo.EmployeeCode+"', now(), 'cut-off activated; branch: "+lblBranch[intIdx].getText()+"; dow: ' || extract(dow from now())::int::varchar || '; ')", true);
											}

											dbUpdate.commit();	
										}
									}
								});
							}
						}
					}
				}
			}
		}
	}

	private void setTime(final JXLabel label) {

		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String time = String.format(" %s ", dateTimeFormat.format(Calendar.getInstance().getTime()));
				label.setText(time);
			}
		};

		Timer timer = new Timer(1000, timerListener);
		timer.setInitialDelay(0);
		timer.start();

	}

	private void Blink(final JXLabel label){

		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (intDCRFCount>0) {
					if (label.getForeground()==Color.RED) {
						label.setForeground(lblDateTime.getForeground());
					} else {
						label.setForeground(Color.RED);
					}

					label.setText(intDCRFCount+" DCRF(s) need(s) attention");
				} else {
					try {
						label.setForeground(lblDateTime.getForeground());							
					} catch (NullPointerException ex) {

					}

					label.setText("---");
				}
			}

		};

		Timer timer = new Timer(1000, timerListener);
		timer.setInitialDelay(0);
		timer.start();
	}

	private void Count(){

		ActionListener timerListener = new ActionListener() {

			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				doCount(); 
			}

		};

		Timer timer = new Timer(30000, timerListener);
		timer.setInitialDelay(0);
		timer.start();
	}

	private static void doCount() {
		SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				pgSelect dbExec = new pgSelect(); 
				dbExec.select("select * from view_dcrf_monitoring('"+UserInfo.EmployeeCode+"')");
				intDCRFCount = dbExec.getRowCount()-1; 
				return null;
			}
		}; 
		sw.execute(); 
	}

	public static boolean deptHead(String emp_code){
		pgSelect db = new pgSelect();
		String query = "select a.dept_alias,c.entity_name from mf_department a \n"+
				"left join em_employee b on  a.dept_head_code = b.emp_code \n"+
				"left join rf_entity c on  b.entity_id = c.entity_id where dept_head_code ='"+emp_code+"'";
		db.select(query);

		return db.getRowCount()>0; 
	}
	private boolean DivHead(String emp_code){
		pgSelect db = new pgSelect();
		String query = "select a.division_alias,c.entity_name from mf_division a "
				+ "left join em_employee b on  a.div_head_code = b.emp_code "
				+ "left join rf_entity c on  b.entity_id = c.entity_id where div_head_code = '"+UserInfo.EmployeeCode+"'";
		db.select(query);

		return db.getRowCount()>0; 
	}

	private void move(JXPanel panel, Integer x, Integer y, Integer intPrefWidth, Integer intPrefHeight) {
		intHomeWidth = (int) ((intHomeWidth==null)?screenSize.getWidth():intHomeWidth); 
		intHomeHeight = (int) ((intHomeHeight==null)?screenSize.getHeight():intHomeHeight); 

		try {
			panel.setBounds(intHomeWidth-x, intHomeHeight-y, intPrefWidth, intPrefHeight);
			panel.repaint();
			panel.revalidate();   
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	private void move(JPanel panel, Integer x, Integer y, Integer intPrefWidth, Integer intPrefHeight) {
		intHomeWidth = (int) ((intHomeWidth==null)?screenSize.getWidth():intHomeWidth); 
		intHomeHeight = (int) ((intHomeHeight==null)?screenSize.getHeight():intHomeHeight); 

		try {
			panel.setBounds(intHomeWidth-x, intHomeHeight-y, intPrefWidth, intPrefHeight);
			panel.repaint();
			panel.revalidate();   
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	private void withPending(){

		ActionListener timerListener = new ActionListener() {

			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				switchIcon(); 
			}

		};

		Timer timer = new Timer(500, timerListener);
		timer.setInitialDelay(0);
		timer.start();
	}

	private void switchIcon() {
		if (lblDCRF.getIcon()==FncLookAndFeel.iconDCRF2) {
			lblDCRF.setIcon(FncLookAndFeel.iconDCRF);
		} else {
			lblDCRF.setIcon(FncLookAndFeel.iconDCRF2);
		}
	}
}
