package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelBrokers;
import tablemodel.modelComm_addAgent_list;
import tablemodel.modelSalesAgentAccreditationHistory;
import tablemodel.modelSalesAgentDownline;
import tablemodel.modelSalesAgentRate;
import tablemodel.modelSalesAgentSubmittedDocs;
import tablemodel.modelSalesAgent_addRate;
import Accounting.Disbursements.RequestForPayment;
import Buyers.ClientServicing.ClientInformation;
import Buyers.ClientServicing.pnlClientInformation;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Lookup.lookupMethods;
import Projects.BiddingandAwarding._NoticeToProceed;

import com.toedter.calendar.JTextFieldDateEditor;

import components.JTBorderFactory;
import components._JCheckListItem;
import components._JCheckListRenderer;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class SalesAgent extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Sales Agent/Broker Maintenance Module";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlAgentMainInfo_b;
	private JPanel pnlComp_a1_a;
	private JPanel pnlComp_a1_b;
	private JPanel pnlSubTable_1;
	private JPanel pnlSubTable_2;
	private JPanel pnlDept;
	private JPanel pnlDept_a;
	private JPanel pnlDept_b;
	private JPanel pnlDept_c;
	private JPanel pnlSearch_a;
	private JPanel pnlSearch_a1;
	private JPanel pnlSearch_a2;
	private JPanel pnlAgentMainInfo_a;
	private JPanel pnlAgentMainInfo;
	private JPanel pnlAgentMainInfo2;
	private JPanel pnlDRFDtl_2a;
	private JPanel pnlDRFDtl_2b;
	private JPanel pnlOverride;
	private JPanel pnlOverride_a;
	private JPanel pnlOverride_b;
	private JPanel pnlOverride_a1;
	private JPanel pnlOverride_a2;
	private JPanel pnlOverride_a2_sub1;
	private JPanel pnlOverride_a2_sub2;
	private JPanel pnlAccreditation;
	private JPanel pnlAccreditation_b;
	private JPanel pnlAccreditation_b1;
	private JPanel pnlAccreditation_b2;
	private JPanel pnlAccreditation_b1_sub1;
	private JPanel pnlAccreditation_b1_sub2;
	private JPanel pnlATM_details;
	private JPanel pnlATM_details_a;
	private JPanel pnlATM_details_b;
	private JPanel pnlATM_details_a1;
	private JPanel pnlATM_details_a2;
	private JPanel pnlATM_details_b1;
	private JPanel pnlATM_details_b2;
	private JPanel pnlATM_details_b1_sub1;
	private JPanel pnlATM_details_b1_sub2;
	private JPanel pnlATM_details_b1_sub1a;
	private JPanel pnlATM_details_b1_sub1b;
	private JPanel pnlATM_x;
	private JPanel pnlATM_y;
	private JPanel pnlATM_m;
	private JPanel pnlATM_details_b2_sub1;
	private JPanel pnlATM_details_b2_sub2;
	private JPanel pnlAgentList;
	private JPanel pnlAddNewRate;
	private JPanel pnlAddNewRate_a;
	private JPanel pnlAddNewRate_a1;
	private JPanel pnlAddNewRate_a2;
	// private JPanel pnlAddNewRate_b;
	private JPanel pnlAddNewRate_c;
	private JPanel pnlAddNewRate_a3;
	private JPanel pnlDate;
	private JPanel pnlProject;
	private JPanel pnlAccreditation_c;
	private JPanel pnlATM_details_c;
	private JPanel pnlOverride_c;
	private JPanel pnlEntityOption;
	private JPanel pnlIndividual;
	private JPanel pnlIndividualInfo;
	private JPanel pnlIndividualEast;
	private JPanel pnlIndividualContactInfo;
	private JPanel pnlIndividualSouth;
	private JPanel pnlIndividualGovernmentInfo;
	private JPanel pnlDownline;
	private JPanel pnlDownline_a;
	private JPanel pnlDownline_b;
	private JPanel pnlDocuments;
	private JPanel pnlDocuments_a;
	private JPanel pnlDocuments_b;
	private JPanel pnlAddNewDocument;
	private JPanel pnlAddNewDoc_a;
	private JPanel pnlAddNewDoc_a1;
	private JPanel pnlAddNewDoc_a2;
	private JPanel pnlAddNewDoc_c;
	private JPanel pnlPersonalInfo;
	private JPanel pnlPersonalInfo_a;
	private JPanel pnlPersonalInfo_a1;
	private JPanel pnlPersonalInfo_a2;
	private JPanel pnlPersonalInfo_a2_sub1;
	private JPanel pnlPersonalInfo_a2_sub2;
	private JPanel pnlPersonalInfo_c;
	private JPanel pnlPersonalInfo_b1;
	private JPanel pnlPersonalInfo_a2_sub2a;
	private JPanel pnlPersonalInfo_a2_sub2b;
	private JPanel pnlPersonalInfo_a2_sub2c;
	private JPanel pnlAddNewDownline;
	private JPanel pnlAddNewDownline_a;
	private JPanel pnlAddNewDownline_a1;
	private JPanel pnlAddNewDownline_a2;
	private JPanel pnlAddNewDownline_c;
	private JPanel pnlUpdateValidityPeriod_a;
	private JPanel pnlUpdateValidityPeriod_a1;
	private JPanel pnlUpdateValidityPeriod_a2;
	private JPanel pnlUpdateValidityPeriod_c;
	private JPanel pnlUpdateValidityPeriod;
	private JPanel pnlBrokers;
	private JPanel pnlBrokers_a;
	private JPanel pnlBrokers_b;

	private JLabel lblDepartment;
	private JLabel lblSearch;
	private JLabel lblAgentName;
	private JLabel lblSalesDiv;
	private JLabel lblSalesDept;
	private JLabel lblPosition;
	private JLabel lblSalesType;
	private JLabel lblOverrideName;
	private JLabel lblAccreditation;
	private JLabel lblX;
	private static JLabel lblBrokerNo;
	private JLabel lblBDO;
	private JLabel lblATM_no;
	private JLabel lblATM_acct_no;
	private JLabel lblCompany;
	private JLabel lblStatus;
	private JLabel lblRateAgentID;
	private JLabel lblRateAgentName;
	// private JLabel lblRateProject;
	// private JLabel lblRatePhase;
	private JLabel lblDate;
	private JLabel lblProject;
	private JLabel lblAccreditation_from;
	private static JLabel lblRealtyName;
	private static JLabel lblLicNo;
	private JLabel lblDateReg;
	private JLabel lblLastName;
	private JLabel lblMiddleName;
	private JLabel lblGender;
	private JLabel lblBirthDate;
	private JLabel lblCivilStatus;
	private JLabel lblCitizenship;
	private JLabel lblTelephoneNo;
	private JLabel lblFirstName;
	private JLabel lblCellphoneNo;
	private JLabel lblEmail;
	private JLabel lblSSSNo;
	private JLabel lblTINNo;
	private JLabel lblFaxNo;
	private JLabel lblBirthPlace;
	private JLabel lblDocID;
	private JLabel lblDocName;
	private JLabel lblDocStatus;
	private JLabel lblDocRemarks;
	private JLabel lblPRC_no;
	private JLabel lblHLURB_no;
	private JLabel lblPI_FirstName;
	private JLabel lblPI_MiddleName;
	private JLabel lblPI_LastName;
	private JLabel lblPI_Type;
	private JLabel lblTIN_no;
	private JLabel lblSSS;
	private JLabel lblDownlineID;
	private JLabel lblDownlineName;
	private JLabel lblDlinePosition;
	private JLabel lblDlineOverride;
	private JLabel lblVatable;
	private JLabel lblPRC;
	private JLabel lblHLURB;
	private JLabel lblPRCvalid;
	private JLabel lblHLURBvalid;
	private JLabel lblPRCValid;
	private JLabel lblHLURBValid;

	private _JTagLabel tagCompany;
	private _JTagLabel tagDepartment;
	private static _JTagLabel tagAgentName;
	private static _JTagLabel tagSalesDiv;
	private static _JTagLabel tagSalesDept;
	private static _JTagLabel tagPosition;
	private static _JTagLabel tagSalesType;
	private static _JTagLabel tagBDO;
	private static _JTagLabel tagOverride;
	private static _JTagLabel tagStatus;
	private static _JTagLabel tagBranch;

	public static modelComm_addAgent_list modelAgentList;
	private modelSalesAgentRate modelRate;
	public static modelComm_addAgent_list modelAgentList_total;
	private modelSalesAgent_addRate modelAddRate;
	private modelSalesAgent_addRate modelAddRate_total;
	private static modelSalesAgentDownline modelDownline;
	private static modelSalesAgentDownline modelDownline_total;
	private static modelSalesAgentSubmittedDocs modelDocuments;
	private static modelSalesAgentSubmittedDocs modelDocuments_total;
	private static modelSalesAgentAccreditationHistory modelAccredHistory;
	private static modelSalesAgentAccreditationHistory modelAccredHistory_total;
	private static modelBrokers modelBroker;
	private static modelBrokers modelBroker_total;

	private _JLookup lookupCompany;
	private _JLookup lookupDepartment;
	private static _JLookup lookupAgentName;
	private static _JLookup lookupSalesDiv;
	private static _JLookup lookupSalesDept;
	private static _JLookup lookupPosition;
	private static _JLookup lookupSalesType;
	private static _JLookup lookupStatus;
	private static _JLookup lookupOverride;
	private static _JLookup lookupBDO;
	private static _JLookup lookupBranch;
	private _JLookup lookupRateProject;
	private _JLookup lookupRatePhase;
	private _JLookup lookupProject;
	private static _JLookup lookupATMAcctNo;
	private _JLookup lookupDoc;
	private _JLookup lookupDownline;

	private JXTextField txtSearch;
	private static JXTextField txtBrokerNo;
	private static JXTextField txtATM_No;
	private static JXTextField txtRealtyName;
	private JXTextField txtRateAgentID;
	private JXTextField txtRateAgentName;
	private JTextField txtLastName;
	private JTextField txtFirstName;
	private JTextField txtMiddleName;
	private JTextField txtTelephoneNo;
	private JTextField txtCellphoneNo;
	private JTextField txtFaxNo;
	private JTextField txtEmail;
	private JTextField txtSSSNo;
	private JTextField txtTINNo;
	private JTextField txtBirthPlace;
	private JXTextField txtDocName;
	private JXTextField txtDocRemarks;
	private static JXTextField txtHLURB_no;
	private static JXTextField txtPRC_no;
	private static JXTextField txtPI_firstname;
	private static JXTextField txtPI_midname;
	private static JXTextField txtPI_lastname;
	private static JXTextField txtTIN;
	private static JXTextField txtSSS;
	private JXTextField txtDownlineName;
	private JXTextField txtDownlinePosn;
	private JXTextField txtDownlineOverride;
	private JTextField txtPRC;
	private JTextField txtHLURB;

	private static JButton btnCancel;
	private static JButton btnSave;
	private static JButton btnAddNew;
	private static JButton btnEdit;
	private JButton btnOK;
	private JButton btnSaveRate;
	private JButton btnCancelRate;
	private JButton btnAddNewRate;
	private JButton btnEditRate;
	private JButton btnSaveAgentRate;
	private JButton btnEditAccreditation;
	private JButton btnSaveAccreditation;
	private JButton btnCancelAccreditation;
	private JButton btnEditATM;
	private JButton btnSaveATM;
	private JButton btnCancelATM;
	private JButton btnEditOverride;
	private JButton btnSaveOverride;
	private JButton btnCancelOverride;
	private JButton btnSaveEntityInd;
	private JButton btnAddNewDownline;
	private JButton btnAddNewDocument;
	private JButton btnEditDocument;
	private JButton btnSaveDocument;
	private JButton btnCancelDocument;
	private JButton btnSaveAgentDoc;
	private JButton btnEditPI;
	private JButton btnSavePI;
	private JButton btnCancelPI;
	private JButton btnSaveAgentDownline;
	private JButton btnPrintContract;
	private JButton btnRemoveDownline;
	private JButton btnSaveUpdateValidity;
	private JButton btnAddNewBroker;
	private static JButton btnRemoveBroker;

	private static _JDateChooser dteAccredFrom;
	private static _JDateChooser dteAccredTo;
	private static _JDateChooser dteDateReg;
	private _JDateChooser dteRefDate;
	private _JDateChooser dateBirthDate;
	private static _JDateChooser dteHLURB_validity;
	private static _JDateChooser dtePRC_validity;
	private _JDateChooser dteUpdatePRC;
	private _JDateChooser dteUpdateHLURB;
	private _JDateChooser dtePRCvalid;
	private _JDateChooser dteHLURBvalid;

	private static JCheckBox chkRelThruATM;

	private JComboBox cmbCivilStatus;
	private JComboBox cmbCitizenship;
	private JComboBox cmbGender;
	private JComboBox cmbDocStatus;
	private static JComboBox cmbPI_type;
	private static JComboBox cmbVatable;

	private static JRadioButton rbtn_with_ATM;
	private static JRadioButton rbtnEndorseATM;
	private static JRadioButton rbtn_no_ATM;
	private JRadioButton rbtIndividual;
	private JRadioButton rbtCorporate;
	private static JRadioButton rbtnATM_onProcess;
	private static JRadioButton rbtnATM_forRelease;
	private static JRadioButton rbtnATM_onHand;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JScrollPaneMain scrollAgentList;
	private _JScrollPaneMain scrollAddRate;
	private _JScrollPaneMain scrollDownline;
	private _JScrollPaneMain scrollDocuments;
	private _JScrollPaneMain scrollAccrdHistory;
	private _JScrollPaneMain scrollBroker;

	private static _JScrollPaneTotal scrollAgentList_total;
	private static _JScrollPaneTotal scrollDownline_total;
	private static _JScrollPaneTotal scrollDocuments_total;
	private static _JScrollPaneTotal scrollBroker_total;

	public static _JTableMain tblAgentList;
	private _JTableMain tblRate;
	private _JTableMain tblAddRate;
	private static _JTableMain tblDownline;
	private static _JTableMain tblDocuments;
	private static _JTableMain tblAccredHistory;
	private static _JTableMain tblBroker;

	public static JList rowHeaderAgentList;
	private JList rowHeaderAddRate;
	private static JList rowHeaderDownline;
	private static JList rowHeaderDocuments;
	private static JList rowHeaderAccredHistory;
	private static JList rowHeaderBroker;

	private static _JTableTotal tblAgentList_total;
	private static _JTableTotal tblDownline_total;
	private static _JTableTotal tblDocuments_total;
	private static _JTableTotal tblBroker_total;

	private _JTabbedPane tabCenter;

	private JSplitPane splitPanel;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	private JPopupMenu menu;
	private JMenuItem mniOpenEntityInfo;
	private JMenuItem mniInactiveAgent;

	String co_id = "";
	String company = "";
	String company_logo;
	String to_do = ""; // to distinguish whether to add new agent or update existing.
	String div_id = "";
	public static String agent_id = "";
	static String agent_name = "";
	String proj_name = "";
	String proj_id = "";
	String phase = "";
	String table = "";
	String override_id = "";
	String broker_name = "";

	private JPanel pnlDocuments_north;
	private JPanel pnlAddNewBroker;
	private JPanel pnlAddNewBroker_a;
	private JPanel pnlAddNewBroker_a1;
	private JLabel lblBrokerName;
	private JLabel lblBrokerPRC_no;
	private JLabel lblBrokerPRC_validity;
	private JLabel lblBrokerHLURB_no;
	private JLabel lblBrokerHLURB_validity;
	private JPanel pnlAddNewBroker_a2;
	private JXTextField txtBrokerName;
	private JXTextField txtBrokerPRC_no;
	private _JDateChooser dtePRC_Validity;
	private JXTextField txtPRCHLURB_no;
	private _JDateChooser dteHLURB_Validity;
	private JPanel pnlAddNewBroker_c;
	private JButton btnSaveBroker;
	private JPanel pnlAgents;
	private JPanel pnlAgents_a;
	private _JScrollPaneMain scrollAgents;
	private static modelSalesAgentDownline modelAgents;
	private static _JTableMain tblAgents;
	private static JList rowHeaderAgents;
	private static _JScrollPaneTotal scrollAgents_total;
	private static modelSalesAgentDownline modelAgents_total;
	private static _JTableTotal tblAgent_total;
	private JPanel pnlAgents_b;
	private JButton btnAddNewAgent;
	private static JButton btnRemoveAgent;
	private JPanel pnlAddNewAgent;
	private JPanel pnlAddNewAgent_a;
	private JPanel pnlAddNewAgent_a1;
	private JPanel pnlAddNewAgent_a2;
	private JPanel pnlAddNewAgent_c;
	private JButton btnSaveAgent;
	private JButton btnEditAgentRate;
	private static JLabel lblRate;
	private static _JXFormattedTextField txtRate;
	private JPanel pnlDRFDtl_2b_1;
	private JLabel lblAgentRate;
	private _JXFormattedTextField txtRate2;
	private JLabel lblBrokerDefaultCheckPayee;
	private JCheckBox chkCheckPayee;
	private static _JLookup lookupIH_BrokerID;
	private static JLabel lblBroker;
	// private static JXTextField lookupIH_BrokerID;
	private static _JTagLabel txtIHBrokerName;
	private static JButton btnEditBroker;
	private static JCheckBox chkContractIn;

	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);

	private static _JLookup txtCoID;
	private static _JLookup txtEntityTypeID;
	private static JTextField txtCo;
	private static JTextField txtEntityType;
	private static JList listCom; 
	private static CardLayout card; 
	private static JPanel panelIO; 

	public SalesAgent() {
		super(title, true, true, true, true);
		initGUI();
	}

	public SalesAgent(String title) {
		super(title);

	}

	public SalesAgent(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(1033, 614));
		this.setBounds(0, 0, 1033, 614);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(745, 487));

		{
			menu = new JPopupMenu("Popup");
			mniOpenEntityInfo = new JMenuItem("Open Agent's Info.");
			mniInactiveAgent = new JMenuItem("Change Status to Inactive");
			menu.add(mniOpenEntityInfo);
			menu.add(mniInactiveAgent);
		}
		{ // start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);
			pnlComp.setPreferredSize(new java.awt.Dimension(905, 40));
			pnlComp.setBorder(lineBorder);

			{
				// company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);
				pnlComp_a.setPreferredSize(new java.awt.Dimension(926, 38));
				pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
				{
					pnlComp_a1 = new JPanel(new BorderLayout(0, 0));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(197, 33));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						pnlComp_a1_a = new JPanel(new BorderLayout(0, 5));
						pnlComp_a1.add(pnlComp_a1_a, BorderLayout.WEST);
						pnlComp_a1_a.setPreferredSize(new java.awt.Dimension(112, 33));
						pnlComp_a1_a.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

						lblCompany = new JLabel("COMPANY  ", JLabel.TRAILING);
						pnlComp_a1_a.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(85, 28));
					}
					{
						pnlComp_a1_b = new JPanel(new BorderLayout(0, 5));
						pnlComp_a1.add(pnlComp_a1_b, BorderLayout.CENTER);
						pnlComp_a1_b.setPreferredSize(new java.awt.Dimension(186, 33));
						pnlComp_a1_b.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

						lookupCompany = new _JLookup(null, "Company", 0, 2);
						pnlComp_a1_b.add(lookupCompany);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setReturnColumn(0);
						lookupCompany.setPreferredSize(new java.awt.Dimension(100, 25));
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									co_id = (String) data[0];
									String name = (String) data[1];
									tagCompany.setTag(name);

									lblDepartment.setEnabled(true);
									lookupDepartment.setEnabled(true);
									tagDepartment.setEnabled(true);
									lblSearch.setEnabled(true);
									txtSearch.setEnabled(true);
									lookupDepartment.setLookupSQL(getDivisionMain());
									
									enableMainButtons(true, false, false, true);
								}
							}
						});
					}
				}
				{
					pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(458, 33));
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(8, 0, 5, 5));

					{
						tagCompany = new _JTagLabel("[ ]");
						pnlComp_a2.add(tagCompany);
						tagCompany.setBounds(209, 27, 700, 22);
						tagCompany.setEnabled(true);
						tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
					}
				}
				{
					pnlSearch_a = new JPanel(new BorderLayout(0, 5));
					pnlComp_a.add(pnlSearch_a, BorderLayout.EAST);
					pnlSearch_a.setPreferredSize(new java.awt.Dimension(394, 33));
					pnlSearch_a.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));

					pnlSearch_a1 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlSearch_a.add(pnlSearch_a1, BorderLayout.WEST);
					pnlSearch_a1.setPreferredSize(new java.awt.Dimension(110, 28));
					pnlSearch_a1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						lblSearch = new JLabel("Search Name : ", JLabel.TRAILING);
						pnlSearch_a1.add(lblSearch);
						lblSearch.setEnabled(false);
						lblSearch.setPreferredSize(new java.awt.Dimension(134, 26));
						lblSearch.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
					}

					pnlSearch_a2 = new JPanel(new BorderLayout(0, 0));
					pnlSearch_a.add(pnlSearch_a2, BorderLayout.CENTER);
					pnlSearch_a2.setPreferredSize(new java.awt.Dimension(778, 36));
					pnlSearch_a2.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

					{
						txtSearch = new JXTextField("");
						pnlSearch_a2.add(txtSearch);
						txtSearch.setEnabled(false);
						txtSearch.setEditable(true);
						txtSearch.setBounds(120, 25, 300, 22);
						txtSearch.setHorizontalAlignment(JTextField.CENTER);
						txtSearch.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								searchAgentList(modelAgentList, rowHeaderAgentList, modelAgentList_total,
										txtSearch.getText());
							}
						});
					}
				}
			}
		} // end of Company
		{
			CreateList(true);
		}
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					btnAddNew = new JButton("Add New Agent/Broker");
					pnlSouthCenterb.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
					btnAddNew.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (withAccess() == true) {
								refreshNewEntityScreen();
								enableFields(true);
								enableMainButtons(false, false, true, true);
								refreshFields();
								disableAllButtons();

								splitPanel.setDividerLocation(0.0);

								to_do = "addnew";
								@SuppressWarnings("deprecation")
								Date date1 = new Date(2016, Calendar.DECEMBER, 31);
								dteAccredFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteAccredTo.setDate(date1);

								int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlEntityOption,
										"Select Type", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
										new Object[] {}, null);

								if (scanOption == JOptionPane.CLOSED_OPTION) {
									try {

									} catch (java.lang.ArrayIndexOutOfBoundsException ex) {

									}
								}

								lookupAgentName.setLookupSQL(getEntity());
								lookupSalesDiv.setLookupSQL(getDivisionMain());
								lookupSalesDiv.setEnabled(true);
								lookupSalesDept.setEnabled(false);
								lookupSalesDept.setLookupSQL(getSalesGroup());
								lookupPosition.setLookupSQL(getSalesPosition(lookupSalesType.getText()));
								lookupSalesType.setLookupSQL(getSalesType());
								lookupStatus.setLookupSQL(getStatus());
								lookupOverride.setLookupSQL(getOverride(lookupSalesType.getText(),
										lookupPosition.getText(), lookupSalesDept.getText()));
								lookupBDO.setLookupSQL(getBDO(lookupSalesDept.getText()));

								rbtn_no_ATM.setSelected(true);
								tabCenter.setSelectedIndex(2);

								lookupBranch.setValue("01");
								tagBranch.setTag("SUMMIT");
							} else {
								JOptionPane.showMessageDialog(getContentPane(),
										"Sorry, you are not authorized to edit Agent's Info..", "Information",
										JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});
				}
				{
					btnEdit = new JButton("Edit Main Info.");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
					btnEdit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (withAccess() == true) {
								executeEdit();
							} else {
								JOptionPane.showMessageDialog(getContentPane(),
										"Sorry, you are not authorized to edit Agent's Info..", "Information",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
				}

				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							validateMainInfoCompleteness();
						}
					});
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (btnSave.isEnabled()) {

								if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel",
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
									execute_cancel();
								}

							}

							else {
								execute_cancel();
							}
							// refresh_tablesMain();
						}
					});
				}
			}
		}

		{

			pnlAddNewRate = new JPanel();
			pnlAddNewRate.setLayout(new BorderLayout(5, 5));
			pnlAddNewRate.setBorder(lineBorder);
			pnlAddNewRate.setPreferredSize(new java.awt.Dimension(500, 300));

			{
				pnlAddNewRate_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewRate.add(pnlAddNewRate_a, BorderLayout.NORTH);
				pnlAddNewRate_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewRate_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewRate_a.setPreferredSize(new java.awt.Dimension(380, 140));

				{
					pnlAddNewRate_a1 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewRate_a.add(pnlAddNewRate_a1, BorderLayout.WEST);
					pnlAddNewRate_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewRate_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewRate_a1.setPreferredSize(new java.awt.Dimension(90, 120));

					{
						lblRateAgentID = new JLabel("Agent ID", JLabel.TRAILING);
						pnlAddNewRate_a1.add(lblRateAgentID);
						lblRateAgentID.setEnabled(true);
						lblRateAgentID.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblRateAgentName = new JLabel("Agent Name", JLabel.TRAILING);
						pnlAddNewRate_a1.add(lblRateAgentName);
						lblRateAgentName.setEnabled(true);
						lblRateAgentName.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblRate = new JLabel("Agent Rate", JLabel.TRAILING);
						pnlAddNewRate_a1.add(lblRate);
						lblRate.setEnabled(true);
						lblRate.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					/*
					 * { lblRateProject = new JLabel("Project", JLabel.TRAILING);
					 * pnlAddNewRate_a1.add(lblRateProject); lblRateProject.setEnabled(true);
					 * lblRateProject.setPreferredSize(new java.awt.Dimension(136, 24)); } {
					 * lblRatePhase = new JLabel("Phase", JLabel.TRAILING);
					 * pnlAddNewRate_a1.add(lblRatePhase); lblRatePhase.setEnabled(true);
					 * lblRatePhase.setPreferredSize(new java.awt.Dimension(136, 24)); }
					 */
				}
				{
					pnlAddNewRate_a2 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewRate_a.add(pnlAddNewRate_a2, BorderLayout.CENTER);
					pnlAddNewRate_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewRate_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewRate_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						txtRateAgentID = new JXTextField("");
						pnlAddNewRate_a2.add(txtRateAgentID);
						txtRateAgentID.setEnabled(true);
						txtRateAgentID.setEditable(true);
						txtRateAgentID.setBounds(120, 25, 300, 22);
						txtRateAgentID.setHorizontalAlignment(JTextField.CENTER);
						txtRateAgentID.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
					}
					{
						txtRateAgentName = new JXTextField("");
						pnlAddNewRate_a2.add(txtRateAgentName);
						txtRateAgentName.setEnabled(true);
						txtRateAgentName.setEditable(true);
						txtRateAgentName.setBounds(120, 25, 300, 22);
						txtRateAgentName.setHorizontalAlignment(JTextField.CENTER);
						txtRateAgentName.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
					}
					{

						txtRate2 = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlAddNewRate_a2.add(txtRate2);
						txtRate2.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtRate2.setText("0.00");
						txtRate2.setEnabled(false);
						txtRate2.setBounds(120, 0, 72, 22);
					}
					/*
					 * { lookupRateProject = new _JLookup(null, "Project", 2, 2);
					 * pnlAddNewRate_a2.add(lookupRateProject); lookupRateProject.setBounds(20, 27,
					 * 20, 25); lookupRateProject.setReturnColumn(0);
					 * lookupRateProject.setEnabled(true); lookupRateProject.setPreferredSize(new
					 * java.awt.Dimension(157, 22)); lookupRateProject.addLookupListener(new
					 * LookupListener() { public void lookupPerformed(LookupEvent event) { Object[]
					 * data = ((_JLookup)event.getSource()).getDataSet(); if(data != null){
					 * 
					 * lookupRatePhase.setValue(""); refresh_agentRateTable(); proj_id = (String)
					 * data[0]; proj_name = (String) data[1];
					 * lookupRatePhase.setLookupSQL(getPhase());
					 * 
					 * } } }); } { lookupRatePhase = new _JLookup(null, "Phase", 2, 2);
					 * pnlAddNewRate_a2.add(lookupRatePhase); lookupRatePhase.setBounds(20, 27, 20,
					 * 25); lookupRatePhase.setReturnColumn(1); lookupRatePhase.setEnabled(true);
					 * lookupRatePhase.setPreferredSize(new java.awt.Dimension(157, 22));
					 * lookupRatePhase.addLookupListener(new LookupListener() { public void
					 * lookupPerformed(LookupEvent event) { Object[] data =
					 * ((_JLookup)event.getSource()).getDataSet(); if(data != null){ phase =
					 * (String) data[1]; displayAgentHouseModelRate(modelAddRate, rowHeaderAddRate,
					 * modelAddRate_total); btnSaveAgentRate.setEnabled(true); } } }); }
					 */
				}
				{
					pnlAddNewRate_a3 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewRate_a.add(pnlAddNewRate_a3, BorderLayout.EAST);
					pnlAddNewRate_a3.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewRate_a3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewRate_a3.setPreferredSize(new java.awt.Dimension(80, 120));
				}

				/*
				 * pnlAddNewRate_b = new JPanel(new BorderLayout(5, 5));
				 * pnlAddNewRate.add(pnlAddNewRate_b, BorderLayout.CENTER);
				 * pnlAddNewRate_b.setPreferredSize(new java.awt.Dimension(921, 41));
				 * pnlAddNewRate_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				 * pnlAddNewRate_b.setPreferredSize(new java.awt.Dimension(500, 150)); { { {
				 * scrollAddRate = new _JScrollPaneMain(); pnlAddNewRate_b.add(scrollAddRate,
				 * BorderLayout.CENTER); { modelAddRate = new modelSalesAgent_addRate();
				 * 
				 * tblAddRate = new _JTableMain(modelAddRate);
				 * scrollAddRate.setViewportView(tblAddRate); tblAddRate.addMouseListener(this);
				 * tblAddRate.setSortable(false);
				 * tblAddRate.getColumnModel().getColumn(0).setPreferredWidth(80);
				 * tblAddRate.getColumnModel().getColumn(1).setPreferredWidth(80);
				 * tblAddRate.getColumnModel().getColumn(2).setPreferredWidth(250);
				 * tblAddRate.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent
				 * evt) {} public void keyPressed(KeyEvent e) {}
				 * 
				 * }); tblAddRate.addMouseListener(new MouseAdapter() { public void
				 * mousePressed(MouseEvent e) { if(tblAddRate.rowAtPoint(e.getPoint()) == -1){}
				 * else{tblAddRate.setCellSelectionEnabled(true);} tagRateTable(); table =
				 * "add_rate"; } public void mouseReleased(MouseEvent e) {
				 * if(tblAddRate.rowAtPoint(e.getPoint()) == -1){}
				 * else{tblAddRate.setCellSelectionEnabled(true);} tagRateTable(); table =
				 * "add_rate"; } });
				 * 
				 * } { rowHeaderAddRate = tblAddRate.getRowHeader();
				 * scrollAddRate.setRowHeaderView(rowHeaderAddRate);
				 * scrollAddRate.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
				 * FncTables.getRowHeader_Header()); } { scrollAddRate_total = new
				 * _JScrollPaneTotal(scrollAddRate); pnlAddNewRate_b.add(scrollAddRate_total,
				 * BorderLayout.SOUTH); { modelAddRate_total = new modelSalesAgent_addRate();
				 * modelAddRate_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
				 * 
				 * tblAddRate_total = new _JTableTotal(modelAddRate_total, tblAddRate);
				 * tblAddRate_total.setFont(dialog11Bold);
				 * scrollAddRate_total.setViewportView(tblAddRate_total); ((_JTableTotal)
				 * tblAddRate_total).setTotalLabel(0); } } } } }
				 */

				pnlAddNewRate_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewRate.add(pnlAddNewRate_c, BorderLayout.SOUTH);
				pnlAddNewRate_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewRate_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewRate_c.setPreferredSize(new java.awt.Dimension(500, 40));

				{
					btnSaveAgentRate = new JButton("Save Agent Rate");
					pnlAddNewRate_c.add(btnSaveAgentRate);
					btnSaveAgentRate.setActionCommand("SaveAgentRate");
					btnSaveAgentRate.addActionListener(this);
					btnSaveAgentRate.setEnabled(false);
				}

			}
		}
		{
			pnlDate = new JPanel();
			pnlDate.setLayout(null);
			pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
			{
				lblDate = new JLabel();
				pnlDate.add(lblDate);
				lblDate.setBounds(5, 15, 160, 20);
				lblDate.setText("Reference Doc. Date :");
				lblDate.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
			}
			{
				dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlDate.add(dteRefDate);
				dteRefDate.setBounds(130, 15, 125, 21);
				dteRefDate.setDate(null);
				dteRefDate.setEnabled(true);
				dteRefDate.setDateFormatString("yyyy-MM-dd");
				((JTextFieldDateEditor) dteRefDate.getDateEditor()).setEditable(false);
				dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				dteRefDate.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
					}
				});
			}
			{
				btnOK = new JButton();
				pnlDate.add(btnOK);
				btnOK.setBounds(95, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
						optionPaneWindow.dispose();
					}
				});
			}
		}
		{
			pnlProject = new JPanel();
			pnlProject.setLayout(null);
			pnlProject.setPreferredSize(new java.awt.Dimension(200, 90));

			{
				lblProject = new JLabel();
				pnlProject.add(lblProject);
				lblProject.setBounds(5, 15, 70, 23);
				lblProject.setText("Project :");
				lblProject.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
			}
			{
				lookupProject = new _JLookup(null, "Project", 0, 2);
				pnlProject.add(lookupProject);
				lookupProject.setReturnColumn(0);
				lookupProject.setBounds(75, 15, 160, 23);
				lookupProject.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						if (data != null) {

							proj_id = (String) data[0];
							proj_name = (String) data[1];
						}
					}
				});
			}
			{
				btnOK = new JButton();
				pnlProject.add(btnOK);
				btnOK.setBounds(95, 58, 69, 25);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlProject);
						optionPaneWindow.dispose();
					}
				});
			}
		}
		{
			pnlEntityOption = new JPanel();
			pnlEntityOption.setLayout(null);
			pnlEntityOption.setPreferredSize(new java.awt.Dimension(280, 80));

			{
				rbtIndividual = new JRadioButton();
				pnlEntityOption.add(rbtIndividual);
				rbtIndividual.setText("INDIVIDUAL");
				rbtIndividual.setBounds(10, 15, 130, 20);
				rbtIndividual.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
				rbtIndividual.setSelected(true);
				rbtIndividual.setEnabled(true);
				rbtIndividual.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (rbtIndividual.isSelected() == true) {
							rbtCorporate.setSelected(false);
						} else if (rbtIndividual.isSelected() == false) {
							rbtCorporate.setSelected(true);
						}
					}
				});
			}
			{
				rbtCorporate = new JRadioButton();
				pnlEntityOption.add(rbtCorporate);
				rbtCorporate.setText("CORPORATE");
				rbtCorporate.setBounds(140, 15, 125, 21);
				rbtCorporate.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
				rbtCorporate.setSelected(false);
				rbtCorporate.setEnabled(true);
				rbtCorporate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (rbtCorporate.isSelected() == true) {
							rbtIndividual.setSelected(false);
						} else if (rbtCorporate.isSelected() == false) {
							rbtIndividual.setSelected(true);
						}
					}
				});
			}
			{
				btnOK = new JButton();
				pnlEntityOption.add(btnOK);
				btnOK.setBounds(105, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlEntityOption);
						optionPaneWindow.dispose();
						if (rbtIndividual.isSelected() == true) {
							addNewEntityIndividual();
						} else {

							ClientInformation ci = new ClientInformation();
							Home_JSystem.addWindow(ci);
							ClientInformation.btnNew.doClick();
							pnlClientInformation.tabEntityKind.setSelectedIndex(1);

							/*
							 * JOptionPane.showMessageDialog(getContentPane()
							 * ,"Adding of corporate type agent is not yet functioning.\n"+
							 * "Please seek IT assistance." , "Error",JOptionPane.ERROR_MESSAGE);
							 */
						}
					}
				});
			}
		}
		{
			pnlIndividual = new JPanel(new BorderLayout(3, 3));
			pnlIndividual.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlIndividual.setPreferredSize(new Dimension(800, 400));

			{
				pnlIndividualInfo = new JPanel(new SpringLayout());
				pnlIndividual.add(pnlIndividualInfo, BorderLayout.CENTER);
				pnlIndividualInfo
						.setBorder(components.JTBorderFactory.createTitleBorder("Individual Info. (*required fields)"));
				{
					lblLastName = new JLabel("* Last Name", JLabel.TRAILING);
					pnlIndividualInfo.add(lblLastName);
				}
				{
					txtLastName = new JTextField();
					pnlIndividualInfo.add(txtLastName);
					txtLastName.setPreferredSize(new Dimension(120, 20));
				}
				{
					lblFirstName = new JLabel("* First Name", JLabel.TRAILING);
					pnlIndividualInfo.add(lblFirstName);
				}
				{
					txtFirstName = new JTextField();
					pnlIndividualInfo.add(txtFirstName);
					txtFirstName.setPreferredSize(new Dimension(120, 20));
				}
				{
					lblMiddleName = new JLabel("* Middle Name", JLabel.TRAILING);
					pnlIndividualInfo.add(lblMiddleName);
				}
				{
					txtMiddleName = new JTextField();
					pnlIndividualInfo.add(txtMiddleName);
					txtMiddleName.setPreferredSize(new Dimension(120, 20));
				}
				{
					lblGender = new JLabel("Gender", JLabel.TRAILING);
					pnlIndividualInfo.add(lblGender);
				}
				{
					JPanel pnlGender = new JPanel(new BorderLayout());
					pnlIndividualInfo.add(pnlGender);
					{
						cmbGender = new JComboBox(new String[] { "Male", "Female" });
						pnlGender.add(cmbGender, BorderLayout.WEST);
						cmbGender.setPreferredSize(new Dimension(120, 20));
					}
				}
				{
					lblBirthDate = new JLabel("Birth Date", JLabel.TRAILING);
					pnlIndividualInfo.add(lblBirthDate);
				}
				{
					JPanel pnlBirthDate = new JPanel(new BorderLayout());
					pnlIndividualInfo.add(pnlBirthDate);
					{
						dateBirthDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlBirthDate.add(dateBirthDate, BorderLayout.WEST);
						dateBirthDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dateBirthDate.setPreferredSize(new Dimension(120, 20));
					}
				}
				{
					lblBirthPlace = new JLabel("Birth Place", JLabel.TRAILING);
					pnlIndividualInfo.add(lblBirthPlace);
				}
				{
					txtBirthPlace = new JTextField();
					pnlIndividualInfo.add(txtBirthPlace);
					txtBirthPlace.setPreferredSize(new Dimension(120, 20));
				}
				{
					lblCivilStatus = new JLabel("Civil Status", JLabel.TRAILING);
					pnlIndividualInfo.add(lblCivilStatus);
				}
				{
					JPanel pnlCivilStatus = new JPanel(new BorderLayout());
					pnlIndividualInfo.add(pnlCivilStatus);
					{
						cmbCivilStatus = new JComboBox(_JInternalFrame.CIVIL_STATUS().values().toArray());
						pnlCivilStatus.add(cmbCivilStatus, BorderLayout.WEST);
						cmbCivilStatus.setPreferredSize(new Dimension(200, 20));
					}
				}
				{
					lblCitizenship = new JLabel("Citizenship", JLabel.TRAILING);
					pnlIndividualInfo.add(lblCitizenship);
				}
				{
					JPanel pnlCitizenship = new JPanel(new BorderLayout());
					pnlIndividualInfo.add(pnlCitizenship);
					{
						cmbCitizenship = new JComboBox(_JInternalFrame.CITIZENSHIP().values().toArray());
						pnlCitizenship.add(cmbCitizenship, BorderLayout.WEST);
						cmbCitizenship.setSelectedItem("FILIPINO");
						cmbCitizenship.setPreferredSize(new Dimension(200, 20));
					}
				}
				SpringUtilities.makeCompactGrid(pnlIndividualInfo, 8, 2, 5, 5, 5, 5, false);
			}
			{
				pnlIndividualEast = new JPanel(new SpringLayout());
				pnlIndividual.add(pnlIndividualEast, BorderLayout.EAST);
				pnlIndividualEast.setPreferredSize(new Dimension(300, 0));
				{
					pnlIndividualContactInfo = new JPanel(new SpringLayout());
					pnlIndividualEast.add(pnlIndividualContactInfo);
					pnlIndividualContactInfo.setBorder(components.JTBorderFactory.createTitleBorder("Contact Info."));
					{
						lblTelephoneNo = new JLabel("Tel. No.", JLabel.TRAILING);
						pnlIndividualContactInfo.add(lblTelephoneNo);
					}
					{
						txtTelephoneNo = new JTextField();
						pnlIndividualContactInfo.add(txtTelephoneNo);
						txtTelephoneNo.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								if (txtTelephoneNo.getText().length() > 9) {
									txtTelephoneNo.setText(txtTelephoneNo.getText().substring(0,
											txtTelephoneNo.getText().length() - 1));
									JOptionPane.showMessageDialog(getContentPane(), "Character cannot exceed 9.",
											"Warning", JOptionPane.WARNING_MESSAGE);

								}
							}
						});
					}
					{
						lblCellphoneNo = new JLabel("* Cell. No.", JLabel.TRAILING);
						pnlIndividualContactInfo.add(lblCellphoneNo);
					}
					{
						txtCellphoneNo = new JTextField();
						pnlIndividualContactInfo.add(txtCellphoneNo);
						txtCellphoneNo.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								if (txtCellphoneNo.getText().length() > 11) {
									txtCellphoneNo.setText(txtCellphoneNo.getText().substring(0,
											txtCellphoneNo.getText().length() - 1));
									JOptionPane.showMessageDialog(getContentPane(), "Character cannot exceed 11.",
											"Warning", JOptionPane.WARNING_MESSAGE);
								}
							}
						});
					}
					{
						lblFaxNo = new JLabel("Fax No.", JLabel.TRAILING);
						pnlIndividualContactInfo.add(lblFaxNo);
					}
					{
						txtFaxNo = new JTextField(10);
						pnlIndividualContactInfo.add(txtFaxNo);
					}
					{
						lblEmail = new JLabel("Email", JLabel.TRAILING);
						pnlIndividualContactInfo.add(lblEmail);
					}
					{
						txtEmail = new JTextField();
						pnlIndividualContactInfo.add(txtEmail);
					}
					SpringUtilities.makeCompactGrid(pnlIndividualContactInfo, 4, 2, 5, 5, 5, 5, false);
				}
				{
					pnlIndividualGovernmentInfo = new JPanel(new SpringLayout());
					pnlIndividualEast.add(pnlIndividualGovernmentInfo);
					pnlIndividualGovernmentInfo
							.setBorder(components.JTBorderFactory.createTitleBorder("Government Info."));
					{
						lblSSSNo = new JLabel("SSS No.", JLabel.TRAILING);
						pnlIndividualGovernmentInfo.add(lblSSSNo);
					}
					{
						txtSSSNo = new JTextField();
						pnlIndividualGovernmentInfo.add(txtSSSNo);
						txtSSSNo.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								if (txtSSSNo.getText().length() > 10) {
									txtSSSNo.setText(txtSSSNo.getText().substring(0, txtSSSNo.getText().length() - 1));
									JOptionPane.showMessageDialog(getContentPane(), "Character cannot exceed 10.",
											"Warning", JOptionPane.WARNING_MESSAGE);

								}
							}
						});
					}
					{
						lblTINNo = new JLabel("* TIN No.", JLabel.TRAILING);
						pnlIndividualGovernmentInfo.add(lblTINNo);
					}
					{
						txtTINNo = new JTextField();
						pnlIndividualGovernmentInfo.add(txtTINNo);
						txtTINNo.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								if (txtTINNo.getText().length() > 12) {
									txtTINNo.setText(txtTINNo.getText().substring(0, txtTINNo.getText().length() - 1));
									JOptionPane.showMessageDialog(getContentPane(), "Character cannot exceed 12.",
											"Warning", JOptionPane.WARNING_MESSAGE);

								}
							}
						});
					}
					{
						lblPRC = new JLabel("* PRC No.", JLabel.TRAILING);
						pnlIndividualGovernmentInfo.add(lblPRC);
					}
					{
						txtPRC = new JTextField();
						pnlIndividualGovernmentInfo.add(txtPRC);
						txtPRC.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								if (txtPRC.getText().length() > 7) {
									txtPRC.setText(txtPRC.getText().substring(0, txtPRC.getText().length() - 1));
									JOptionPane.showMessageDialog(getContentPane(), "Character cannot exceed 7.",
											"Warning", JOptionPane.WARNING_MESSAGE);

								}
							}
						});
					}
					{
						lblPRCvalid = new JLabel("* PRC/Valid Until", JLabel.TRAILING);
						pnlIndividualGovernmentInfo.add(lblPRCvalid);
					}
					{
						dtePRCvalid = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlIndividualGovernmentInfo.add(dtePRCvalid);
						dtePRCvalid.setBounds(485, 7, 125, 21);
						dtePRCvalid.setDate(null);
						dtePRCvalid.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor) dtePRCvalid.getDateEditor()).setEditable(true);
					}
					{
						lblHLURB = new JLabel("* HLURB No.", JLabel.TRAILING);
						pnlIndividualGovernmentInfo.add(lblHLURB);
					}
					{
						txtHLURB = new JTextField();
						pnlIndividualGovernmentInfo.add(txtHLURB);
						txtHLURB.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								if (txtHLURB.getText().length() > 6) {
									txtHLURB.setText(txtHLURB.getText().substring(0, txtHLURB.getText().length() - 1));
									JOptionPane.showMessageDialog(getContentPane(), "Character cannot exceed 6.",
											"Warning", JOptionPane.WARNING_MESSAGE);

								}
							}
						});
					}
					{
						lblHLURBvalid = new JLabel("* HLURB/Valid Until", JLabel.TRAILING);
						pnlIndividualGovernmentInfo.add(lblHLURBvalid);
					}
					{
						dteHLURBvalid = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlIndividualGovernmentInfo.add(dteHLURBvalid);
						dteHLURBvalid.setBounds(485, 7, 125, 21);
						dteHLURBvalid.setDate(null);
						dteHLURBvalid.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor) dteHLURBvalid.getDateEditor()).setEditable(true);
					}
					SpringUtilities.makeCompactGrid(pnlIndividualGovernmentInfo, 6, 2, 5, 5, 5, 5, false);
				}
				SpringUtilities.makeCompactGrid(pnlIndividualEast, 2, 1, 0, 0, 5, 5, false);
			}
			{
				pnlIndividualSouth = new JPanel(new BorderLayout(5, 5));
				pnlIndividual.add(pnlIndividualSouth, BorderLayout.SOUTH);
				pnlIndividualSouth.setPreferredSize(new Dimension(0, 35));

				{
					JSeparator sep = new JSeparator();
					pnlIndividualSouth.add(sep, BorderLayout.NORTH);

					{
						btnSaveEntityInd = new JButton();
						pnlIndividualSouth.add(btnSaveEntityInd);
						btnSaveEntityInd.setText("Save");
						btnSaveEntityInd.setFocusable(false);
						btnSaveEntityInd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								saveIndEntity();

								// setAgent_mainDtls(agent_id);
								lookupStatus.setText("A");
								tagStatus.setTag("ACTIVE");
							}
						});
					}
				}
			}
		}
		{
			pnlAddNewDocument = new JPanel();
			pnlAddNewDocument.setLayout(new BorderLayout(5, 5));
			pnlAddNewDocument.setBorder(lineBorder);
			pnlAddNewDocument.setPreferredSize(new java.awt.Dimension(380, 200));

			{
				pnlAddNewDoc_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewDocument.add(pnlAddNewDoc_a, BorderLayout.NORTH);
				pnlAddNewDoc_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewDoc_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewDoc_a.setPreferredSize(new java.awt.Dimension(380, 140));

				{
					pnlAddNewDoc_a1 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewDoc_a.add(pnlAddNewDoc_a1, BorderLayout.WEST);
					pnlAddNewDoc_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewDoc_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewDoc_a1.setPreferredSize(new java.awt.Dimension(90, 120));

					{
						lblDocID = new JLabel("Doc. ID", JLabel.TRAILING);
						pnlAddNewDoc_a1.add(lblDocID);
						lblDocID.setEnabled(true);
						lblDocID.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDocName = new JLabel("Doc. Name", JLabel.TRAILING);
						pnlAddNewDoc_a1.add(lblDocName);
						lblDocName.setEnabled(true);
						lblDocName.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDocStatus = new JLabel("Status", JLabel.TRAILING);
						pnlAddNewDoc_a1.add(lblDocStatus);
						lblDocStatus.setEnabled(true);
						lblDocStatus.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDocRemarks = new JLabel("Remarks", JLabel.TRAILING);
						pnlAddNewDoc_a1.add(lblDocRemarks);
						lblDocRemarks.setEnabled(true);
						lblDocRemarks.setPreferredSize(new java.awt.Dimension(136, 24));
					}
				}
				{
					pnlAddNewDoc_a2 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewDoc_a.add(pnlAddNewDoc_a2, BorderLayout.CENTER);
					pnlAddNewDoc_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewDoc_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewDoc_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						lookupDoc = new _JLookup(null, "Document", 2, 2);
						pnlAddNewDoc_a2.add(lookupDoc);
						lookupDoc.setBounds(20, 27, 20, 25);
						lookupDoc.setReturnColumn(0);
						lookupDoc.setEnabled(true);
						lookupDoc.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupDoc.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									txtDocName.setText("");
									// refresh_agentRateTable();
									String doc_name = (String) data[1];
									txtDocName.setText(doc_name);
									btnSaveAgentDoc.setEnabled(true);
								}
							}
						});
					}
					{
						txtDocName = new JXTextField("");
						pnlAddNewDoc_a2.add(txtDocName);
						txtDocName.setEnabled(true);
						txtDocName.setEditable(true);
						txtDocName.setBounds(120, 25, 300, 22);
						txtDocName.setHorizontalAlignment(JTextField.CENTER);
						txtDocName.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 10));
					}
					{
						cmbDocStatus = new JComboBox(new String[] { "Active", "Inactive" });
						pnlAddNewDoc_a2.add(cmbDocStatus, BorderLayout.WEST);
						cmbDocStatus.setSelectedIndex(0);
						cmbDocStatus.setPreferredSize(new Dimension(120, 20));
					}
					{
						txtDocRemarks = new JXTextField("");
						pnlAddNewDoc_a2.add(txtDocRemarks);
						txtDocRemarks.setEnabled(true);
						txtDocRemarks.setEditable(true);
						txtDocRemarks.setBounds(120, 25, 300, 22);
						txtDocRemarks.setHorizontalAlignment(JTextField.CENTER);
						txtDocRemarks.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
					}
				}

				pnlAddNewDoc_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewDocument.add(pnlAddNewDoc_c, BorderLayout.SOUTH);
				pnlAddNewDoc_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewDoc_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewDoc_c.setPreferredSize(new java.awt.Dimension(500, 40));

				{
					btnSaveAgentDoc = new JButton("Save Agent Doc.");
					pnlAddNewDoc_c.add(btnSaveAgentDoc);
					btnSaveAgentDoc.setActionCommand("SaveAgentDoc");
					btnSaveAgentDoc.addActionListener(this);
					btnSaveAgentDoc.setEnabled(false);
					btnSaveAgentDoc.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							saveAgentDoc();
						}
					});
				}
			}
		}
		{
			pnlUpdateValidityPeriod = new JPanel();
			pnlUpdateValidityPeriod.setLayout(new BorderLayout(5, 5));
			pnlUpdateValidityPeriod.setBorder(lineBorder);
			pnlUpdateValidityPeriod.setPreferredSize(new java.awt.Dimension(380, 120));

			{
				pnlUpdateValidityPeriod_a = new JPanel(new BorderLayout(0, 0));
				pnlUpdateValidityPeriod.add(pnlUpdateValidityPeriod_a, BorderLayout.NORTH);
				pnlUpdateValidityPeriod_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlUpdateValidityPeriod_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				pnlUpdateValidityPeriod_a.setPreferredSize(new java.awt.Dimension(378, 80));

				{
					pnlUpdateValidityPeriod_a1 = new JPanel(new GridLayout(2, 5, 5, 5));
					pnlUpdateValidityPeriod_a.add(pnlUpdateValidityPeriod_a1, BorderLayout.WEST);
					pnlUpdateValidityPeriod_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlUpdateValidityPeriod_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlUpdateValidityPeriod_a1.setPreferredSize(new java.awt.Dimension(120, 120));

					{
						lblPRCValid = new JLabel("PRC - Valid Until", JLabel.TRAILING);
						pnlUpdateValidityPeriod_a1.add(lblPRCValid);
						lblPRCValid.setEnabled(true);
						lblPRCValid.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblHLURBValid = new JLabel("HLURB - Valid Until", JLabel.TRAILING);
						pnlUpdateValidityPeriod_a1.add(lblHLURBValid);
						lblHLURBValid.setEnabled(true);
						lblHLURBValid.setPreferredSize(new java.awt.Dimension(136, 24));
					}
				}
				{
					pnlUpdateValidityPeriod_a2 = new JPanel(new GridLayout(2, 5, 5, 5));
					pnlUpdateValidityPeriod_a.add(pnlUpdateValidityPeriod_a2, BorderLayout.CENTER);
					pnlUpdateValidityPeriod_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlUpdateValidityPeriod_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlUpdateValidityPeriod_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						dteUpdatePRC = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlUpdateValidityPeriod_a2.add(dteUpdatePRC);
						dteUpdatePRC.setBounds(485, 7, 125, 21);
						dteUpdatePRC.setDate(null);
						dteUpdatePRC.setEnabled(true);
						dteUpdatePRC.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor) dteUpdatePRC.getDateEditor()).setEditable(false);
					}
					{
						dteUpdateHLURB = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlUpdateValidityPeriod_a2.add(dteUpdateHLURB);
						dteUpdateHLURB.setBounds(485, 7, 125, 21);
						dteUpdateHLURB.setDate(null);
						dteUpdateHLURB.setEnabled(true);
						dteUpdateHLURB.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor) dteUpdateHLURB.getDateEditor()).setEditable(false);
					}
				}

				pnlUpdateValidityPeriod_c = new JPanel(new BorderLayout(5, 5));
				pnlUpdateValidityPeriod.add(pnlUpdateValidityPeriod_c, BorderLayout.CENTER);
				pnlUpdateValidityPeriod_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlUpdateValidityPeriod_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlUpdateValidityPeriod_c.setPreferredSize(new java.awt.Dimension(500, 40));

				{
					btnSaveUpdateValidity = new JButton("Update Validity Period");
					pnlUpdateValidityPeriod_c.add(btnSaveUpdateValidity);
					btnSaveUpdateValidity.addActionListener(this);
					btnSaveUpdateValidity.setEnabled(true);
					btnSaveUpdateValidity.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							updateValidityPeriod();
						}
					});
				}
			}
		}
		{
			pnlAddNewDownline = new JPanel();
			pnlAddNewDownline.setLayout(new BorderLayout(5, 5));
			pnlAddNewDownline.setBorder(lineBorder);
			pnlAddNewDownline.setPreferredSize(new java.awt.Dimension(400, 200));

			{
				pnlAddNewDownline_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewDownline.add(pnlAddNewDownline_a, BorderLayout.NORTH);
				pnlAddNewDownline_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewDownline_a.setPreferredSize(new java.awt.Dimension(380, 140));

				{
					pnlAddNewDownline_a1 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewDownline_a.add(pnlAddNewDownline_a1, BorderLayout.WEST);
					pnlAddNewDownline_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewDownline_a1.setPreferredSize(new java.awt.Dimension(90, 150));

					{
						lblDownlineID = new JLabel("Agent ID", JLabel.TRAILING);
						pnlAddNewDownline_a1.add(lblDownlineID);
						lblDownlineID.setEnabled(true);
						lblDownlineID.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDownlineName = new JLabel("Agent Name", JLabel.TRAILING);
						pnlAddNewDownline_a1.add(lblDownlineName);
						lblDownlineName.setEnabled(true);
						lblDownlineName.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDlinePosition = new JLabel("Position", JLabel.TRAILING);
						pnlAddNewDownline_a1.add(lblDlinePosition);
						lblDlinePosition.setEnabled(true);
						lblDlinePosition.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDlineOverride = new JLabel("Override", JLabel.TRAILING);
						pnlAddNewDownline_a1.add(lblDlineOverride);
						lblDlineOverride.setEnabled(true);
						lblDlineOverride.setPreferredSize(new java.awt.Dimension(136, 24));
					}
				}
				{
					pnlAddNewDownline_a2 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewDownline_a.add(pnlAddNewDownline_a2, BorderLayout.CENTER);
					pnlAddNewDownline_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewDownline_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewDownline_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						lookupDownline = new _JLookup(null, "Downline", 2, 2);
						pnlAddNewDownline_a2.add(lookupDownline);
						lookupDownline.setBounds(20, 27, 20, 25);
						lookupDownline.setReturnColumn(0);
						lookupDownline.setEnabled(true);
						lookupDownline.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupDownline.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									String name = "";
									String posn = "";
									String ovride = "";

									try {
										name = (String) data[1];
									} catch (NullPointerException e) {
										name = "";
									}
									try {
										posn = (String) data[2];
									} catch (NullPointerException e) {
										posn = "";
									}
									try {
										ovride = (String) data[3];
									} catch (NullPointerException e) {
										ovride = "";
									}

									txtDownlineName.setText(name);
									txtDownlinePosn.setText(posn);
									txtDownlineOverride.setText(ovride);
									btnSaveAgentDownline.setEnabled(true);
								}
							}
						});
					}
					{
						txtDownlineName = new JXTextField("");
						pnlAddNewDownline_a2.add(txtDownlineName);
						txtDownlineName.setEnabled(true);
						txtDownlineName.setEditable(true);
						txtDownlineName.setBounds(120, 25, 300, 22);
						txtDownlineName.setHorizontalAlignment(JTextField.CENTER);
						txtDownlineName.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 10));
					}
					{
						txtDownlinePosn = new JXTextField("");
						pnlAddNewDownline_a2.add(txtDownlinePosn);
						txtDownlinePosn.setEnabled(true);
						txtDownlinePosn.setEditable(true);
						txtDownlinePosn.setBounds(120, 25, 300, 22);
						txtDownlinePosn.setHorizontalAlignment(JTextField.CENTER);
						txtDownlinePosn.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
					}
					{
						txtDownlineOverride = new JXTextField("");
						pnlAddNewDownline_a2.add(txtDownlineOverride);
						txtDownlineOverride.setEnabled(true);
						txtDownlineOverride.setEditable(true);
						txtDownlineOverride.setBounds(120, 25, 300, 22);
						txtDownlineOverride.setHorizontalAlignment(JTextField.CENTER);
						txtDownlineOverride.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
					}
				}

				pnlAddNewDownline_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewDownline.add(pnlAddNewDownline_c, BorderLayout.SOUTH);
				pnlAddNewDownline_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewDownline_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewDownline_c.setPreferredSize(new java.awt.Dimension(500, 40));

				{
					btnSaveAgentDownline = new JButton("Save Downline");
					pnlAddNewDownline_c.add(btnSaveAgentDownline);
					btnSaveAgentDownline.addActionListener(this);
					btnSaveAgentDownline.setEnabled(false);
					btnSaveAgentDownline.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (btnSaveAgentDownline.getText().equals("Save Agent")) {
								if (txtDownlineOverride.getText().equals("")) {
									saveNewDownline();
								} else if (!txtDownlineOverride.getText().equals("")) {
									JOptionPane.showMessageDialog(getContentPane(),
											"Agent selected has an existing override.", "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								insertBroker();
							}

						}
					});
				}
			}
		}
		{
			pnlAddNewBroker = new JPanel();
			pnlAddNewBroker.setLayout(new BorderLayout(5, 5));
			pnlAddNewBroker.setBorder(lineBorder);
			pnlAddNewBroker.setPreferredSize(new java.awt.Dimension(450, 210));

			{
				pnlAddNewBroker_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewBroker.add(pnlAddNewBroker_a, BorderLayout.NORTH);
				pnlAddNewBroker_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewBroker_a.setPreferredSize(new java.awt.Dimension(450, 170));

				{
					pnlAddNewBroker_a1 = new JPanel(new GridLayout(6, 5, 5, 5));
					pnlAddNewBroker_a.add(pnlAddNewBroker_a1, BorderLayout.WEST);
					pnlAddNewBroker_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewBroker_a1.setPreferredSize(new java.awt.Dimension(140, 150));

					{
						lblBrokerName = new JLabel("Broker Name", JLabel.TRAILING);
						pnlAddNewBroker_a1.add(lblBrokerName);
						lblBrokerName.setEnabled(true);
						lblBrokerName.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblBrokerPRC_no = new JLabel("PRC No.", JLabel.TRAILING);
						pnlAddNewBroker_a1.add(lblBrokerPRC_no);
						lblBrokerPRC_no.setEnabled(true);
						lblBrokerPRC_no.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblBrokerPRC_validity = new JLabel("PRC Validity", JLabel.TRAILING);
						pnlAddNewBroker_a1.add(lblBrokerPRC_validity);
						lblBrokerPRC_validity.setEnabled(true);
						lblBrokerPRC_validity.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblBrokerHLURB_no = new JLabel("HLURB No.", JLabel.TRAILING);
						pnlAddNewBroker_a1.add(lblBrokerHLURB_no);
						lblBrokerHLURB_no.setEnabled(true);
						lblBrokerHLURB_no.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblBrokerHLURB_validity = new JLabel("HLURB Validity", JLabel.TRAILING);
						pnlAddNewBroker_a1.add(lblBrokerHLURB_validity);
						lblBrokerHLURB_validity.setEnabled(true);
						lblBrokerHLURB_validity.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblBrokerDefaultCheckPayee = new JLabel("Default Check Payee", JLabel.TRAILING);
						pnlAddNewBroker_a1.add(lblBrokerDefaultCheckPayee);
						lblBrokerDefaultCheckPayee.setEnabled(true);
						lblBrokerDefaultCheckPayee.setPreferredSize(new java.awt.Dimension(136, 24));
					}
				}
				{
					pnlAddNewBroker_a2 = new JPanel(new GridLayout(6, 5, 5, 5));
					pnlAddNewBroker_a.add(pnlAddNewBroker_a2, BorderLayout.CENTER);
					pnlAddNewBroker_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewBroker_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewBroker_a2.setPreferredSize(new java.awt.Dimension(220, 150));

					{
						txtBrokerName = new JXTextField("");
						pnlAddNewBroker_a2.add(txtBrokerName);
						txtBrokerName.setEnabled(true);
						txtBrokerName.setEditable(true);
						txtBrokerName.setBounds(120, 25, 300, 22);
						txtBrokerName.setHorizontalAlignment(JTextField.CENTER);
						txtBrokerName.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
						txtBrokerName.setText("");
					}
					{
						txtBrokerPRC_no = new JXTextField("");
						pnlAddNewBroker_a2.add(txtBrokerPRC_no);
						txtBrokerPRC_no.setEnabled(true);
						txtBrokerPRC_no.setEditable(true);
						txtBrokerPRC_no.setBounds(120, 25, 300, 22);
						txtBrokerPRC_no.setHorizontalAlignment(JTextField.CENTER);
						txtBrokerPRC_no.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
						txtBrokerPRC_no.setText("");
					}
					{
						dtePRC_Validity = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlAddNewBroker_a2.add(dtePRC_Validity);
						dtePRC_Validity.setBounds(485, 7, 125, 21);
						dtePRC_Validity.setDate(null);
						dtePRC_Validity.setEnabled(true);
						dtePRC_Validity.setDateFormatString("yyyy-MM-dd");
						// dteNeeded.setSelectableDateRange( Calendar.getInstance().getTime(), date);
						((JTextFieldDateEditor) dtePRC_Validity.getDateEditor()).setEditable(false);
						dtePRC_Validity.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
					{
						txtPRCHLURB_no = new JXTextField("");
						pnlAddNewBroker_a2.add(txtPRCHLURB_no);
						txtPRCHLURB_no.setEnabled(true);
						txtPRCHLURB_no.setEditable(true);
						txtPRCHLURB_no.setBounds(120, 25, 300, 22);
						txtPRCHLURB_no.setHorizontalAlignment(JTextField.CENTER);
						txtPRCHLURB_no.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
						txtPRCHLURB_no.setText("");
					}
					{
						dteHLURB_Validity = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlAddNewBroker_a2.add(dteHLURB_Validity);
						dteHLURB_Validity.setBounds(485, 7, 125, 21);
						dteHLURB_Validity.setDate(null);
						dteHLURB_Validity.setEnabled(true);
						dteHLURB_Validity.setDateFormatString("yyyy-MM-dd");
						// dteNeeded.setSelectableDateRange( Calendar.getInstance().getTime(), date);
						((JTextFieldDateEditor) dteHLURB_Validity.getDateEditor()).setEditable(false);
						dteHLURB_Validity.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
					{
						chkCheckPayee = new JCheckBox("");
						pnlAddNewBroker_a2.add(chkCheckPayee);
						chkCheckPayee.setEnabled(true);
						chkCheckPayee.setSelected(false);
						chkCheckPayee.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
						chkCheckPayee.setPreferredSize(new java.awt.Dimension(131, 26));
						chkCheckPayee.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent arg0) {
								boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

								if (selected) {

								} else {

								}
							}
						});
					}
				}

				pnlAddNewBroker_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewBroker.add(pnlAddNewBroker_c, BorderLayout.SOUTH);
				pnlAddNewBroker_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBroker_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewBroker_c.setPreferredSize(new java.awt.Dimension(500, 40));

				{
					btnSaveBroker = new JButton("Save Broker");
					pnlAddNewBroker_c.add(btnSaveBroker);
					btnSaveBroker.addActionListener(this);
					btnSaveBroker.setEnabled(true);
					btnSaveBroker.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (broker_name.equals("")) {
								insertBroker();
							} else {
								editBroker();
							}

						}
					});
				}
			}
		}
		{
			pnlAddNewAgent = new JPanel();
			pnlAddNewAgent.setLayout(new BorderLayout(5, 5));
			pnlAddNewAgent.setBorder(lineBorder);
			pnlAddNewAgent.setPreferredSize(new java.awt.Dimension(400, 200));

			{
				pnlAddNewAgent_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewAgent.add(pnlAddNewAgent_a, BorderLayout.NORTH);
				pnlAddNewAgent_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewAgent_a.setPreferredSize(new java.awt.Dimension(380, 140));

				{
					pnlAddNewAgent_a1 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewAgent_a.add(pnlAddNewAgent_a1, BorderLayout.WEST);
					pnlAddNewAgent_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewAgent_a1.setPreferredSize(new java.awt.Dimension(90, 150));

					{
						lblDownlineID = new JLabel("Agent ID", JLabel.TRAILING);
						pnlAddNewAgent_a1.add(lblDownlineID);
						lblDownlineID.setEnabled(true);
						lblDownlineID.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDownlineName = new JLabel("Agent Name", JLabel.TRAILING);
						pnlAddNewAgent_a1.add(lblDownlineName);
						lblDownlineName.setEnabled(true);
						lblDownlineName.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDlinePosition = new JLabel("Position", JLabel.TRAILING);
						pnlAddNewAgent_a1.add(lblDlinePosition);
						lblDlinePosition.setEnabled(true);
						lblDlinePosition.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblDlineOverride = new JLabel("Override", JLabel.TRAILING);
						pnlAddNewAgent_a1.add(lblDlineOverride);
						lblDlineOverride.setEnabled(true);
						lblDlineOverride.setPreferredSize(new java.awt.Dimension(136, 24));
					}
				}
				{
					pnlAddNewAgent_a2 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewAgent_a.add(pnlAddNewAgent_a2, BorderLayout.CENTER);
					pnlAddNewAgent_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewAgent_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewAgent_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						lookupDownline = new _JLookup(null, "Downline", 2, 2);
						pnlAddNewAgent_a2.add(lookupDownline);
						lookupDownline.setBounds(20, 27, 20, 25);
						lookupDownline.setReturnColumn(0);
						lookupDownline.setEnabled(true);
						lookupDownline.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupDownline.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									String name = "";
									String posn = "";
									String ovride = "";

									try {
										name = (String) data[1];
									} catch (NullPointerException e) {
										name = "";
									}
									try {
										posn = (String) data[2];
									} catch (NullPointerException e) {
										posn = "";
									}
									try {
										ovride = (String) data[3];
									} catch (NullPointerException e) {
										ovride = "";
									}

									txtDownlineName.setText(name);
									txtDownlinePosn.setText(posn);
									txtDownlineOverride.setText(ovride);
									btnSaveAgentDownline.setEnabled(true);
								}
							}
						});
					}
					{
						txtDownlineName = new JXTextField("");
						pnlAddNewAgent_a2.add(txtDownlineName);
						txtDownlineName.setEnabled(true);
						txtDownlineName.setEditable(true);
						txtDownlineName.setBounds(120, 25, 300, 22);
						txtDownlineName.setHorizontalAlignment(JTextField.CENTER);
						txtDownlineName.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 10));
					}
					{
						txtDownlinePosn = new JXTextField("");
						pnlAddNewAgent_a2.add(txtDownlinePosn);
						txtDownlinePosn.setEnabled(true);
						txtDownlinePosn.setEditable(true);
						txtDownlinePosn.setBounds(120, 25, 300, 22);
						txtDownlinePosn.setHorizontalAlignment(JTextField.CENTER);
						txtDownlinePosn.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
					}
					{
						txtDownlineOverride = new JXTextField("");
						pnlAddNewAgent_a2.add(txtDownlineOverride);
						txtDownlineOverride.setEnabled(true);
						txtDownlineOverride.setEditable(true);
						txtDownlineOverride.setBounds(120, 25, 300, 22);
						txtDownlineOverride.setHorizontalAlignment(JTextField.CENTER);
						txtDownlineOverride.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
					}
				}

				pnlAddNewAgent_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewAgent.add(pnlAddNewAgent_c, BorderLayout.SOUTH);
				pnlAddNewAgent_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewAgent_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewAgent_c.setPreferredSize(new java.awt.Dimension(500, 40));

				{
					btnSaveAgent = new JButton("Save Agent");
					pnlAddNewAgent_c.add(btnSaveAgent);
					btnSaveAgent.addActionListener(this);
					btnSaveAgent.setEnabled(true);
					btnSaveAgent.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							saveNewAgent();

						}
					});
				}
			}
		}

		// added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	// display tables
	public void displayAgentList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String div) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "\r\n" + "select\r\n" + "\r\n" + "trim(a.agent_id) as agent,\r\n" + //
				"trim(b.entity_name) as agent_name,\r\n" + //
				"trim(c.division_alias) as div,\r\n" + "trim(d.dept_alias) as dept,\r\n"
				+ "trim(e.position_abbv) as posn,\r\n" + "(case when a.salestype_id = '001' then 'Agent' else \r\n"
				+ "	(case when a.salestype_id = '002' then 'Broker' end ) end) as type,\r\n" + "a.status_id\r\n"
				+ "\r\n" + "\r\n" + "from mf_sellingagent_info a\r\n"
				+ "left join rf_entity b on a.agent_id = b.entity_id\r\n"
				+ "left join mf_division c on a.sales_div_id = c.division_code\r\n"
				+ "left join mf_department d on a.dept_id = d.dept_code\r\n"
				+ "left join mf_sales_position e on a.salespos_id = e.position_code \r\n" + "\r\n"
				+ "where a.sales_div_id = '" + div + "' \n" + "order by a.agent_id ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			// totalAllComm(modelMain, modelTotal);
		}

		else {
			modelAgentList_total = new modelComm_addAgent_list();
			modelAgentList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

			tblAgentList_total = new _JTableTotal(modelAgentList_total, tblAgentList);
			tblAgentList_total.setFont(dialog11Bold);
			scrollAgentList_total.setViewportView(tblAgentList_total);
			((_JTableTotal) tblAgentList_total).setTotalLabel(0);
		}

		tblAgentList.packAll();
		tblAgentList.getColumnModel().getColumn(0).setPreferredWidth(80);

		int row_tot = tblAgentList.getRowCount();
		modelAgentList_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		/* adjustRowHeight_account(tblAgentList); */
	}

	/*
	 * public void displayAgentRate(DefaultTableModel modelMain, JList rowHeader,
	 * DefaultTableModel modelTotal) {//
	 * 
	 * FncTables.clearTable(modelMain);//Code to clear modelMain. DefaultListModel
	 * listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
	 * rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
	 * 
	 * String sql = "select \r\n" + "\r\n" + "a.rate_id, " +
	 * "trim(b.proj_alias) as project,\r\n" + "trim(a.phase) as phase,\r\n" +
	 * "trim(c.model_desc),\r\n" + "a.rate," + "a.eff_date, \r\n" +
	 * "a.status_id,\r\n" + "trim(upper(e.entity_name)) as created_by,\r\n" +
	 * "a.edit_date\r\n" + "\r\n" + "\r\n" + "from cm_rate_per_project_phase a\r\n"
	 * + "left join mf_project b on a.proj_code =  b.proj_id\r\n" +
	 * "left join mf_product_model c on a.model_id = c.model_id\r\n" +
	 * "left join em_employee d on a.edit_by = d.emp_code\r\n" +
	 * "left join rf_entity e on d.entity_id = e.entity_id\r\n" + "\r\n" +
	 * "where a.agent_code = '"+agent_id+"'  " + "order by a.rate_id " ;
	 * 
	 * System.out.printf("SQL #1: %s", sql); pgSelect db = new pgSelect();
	 * db.select(sql); if(db.isNotNull()){ for(int x=0; x < db.getRowCount(); x++){
	 * // Adding of row in table modelMain.addRow(db.getResult()[x]);
	 * listModel.addElement(modelMain.getRowCount()); } }
	 * 
	 * 
	 * else { modelRate_total = new modelSalesAgentRate();
	 * modelRate_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
	 * 
	 * tblRate_total = new _JTableTotal(modelRate_total, tblRate);
	 * tblRate_total.setFont(dialog11Bold);
	 * scrollRate_total.setViewportView(tblRate_total); ((_JTableTotal)
	 * tblRate_total).setTotalLabel(0);}
	 * 
	 * tblRate.packAll();
	 * tblRate.getColumnModel().getColumn(0).setPreferredWidth(80);
	 * 
	 * int row_tot = tblRate.getRowCount(); modelRate_total.setValueAt(new
	 * BigDecimal(row_tot), 0, 1);
	 * 
	 * //adjustRowHeight_account(tblRate);
	 * 
	 * modelRate.setEditable(false); tblRate.setEditable(false); }
	 */

	/*
	 * private void displayAgentHouseModelRate(DefaultTableModel modelMain, JList
	 * rowHeader, DefaultTableModel modelTotal) {//
	 * 
	 * FncTables.clearTable(modelMain);//Code to clear modelMain. DefaultListModel
	 * listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
	 * rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
	 * 
	 * String sql = "select \r\n" + "\r\n" + "false as tag,\r\n" +
	 * "trim(a.model_id) as model,\r\n" + "trim(a.model_desc),\r\n" +
	 * "0.00 as rate," + "null as eff_date, \r\n" + "'' as status_id\r\n" + "\r\n" +
	 * "from mf_product_model a  " +
	 * "where a.model_id not in (select model_id from cm_rate_per_project_phase " +
	 * "	where agent_code = '"+agent_id+"' and proj_code = '"
	 * +proj_id+"' and trim(phase) = '"+phase+"' ) \r\n" +
	 * 
	 * "order by a.model_id" ;
	 * 
	 * System.out.printf("SQL #1: %s", sql); pgSelect db = new pgSelect();
	 * db.select(sql); if(db.isNotNull()){ for(int x=0; x < db.getRowCount(); x++){
	 * // Adding of row in table modelMain.addRow(db.getResult()[x]);
	 * listModel.addElement(modelMain.getRowCount()); } }
	 * 
	 * 
	 * else { modelAddRate_total = new modelSalesAgent_addRate();
	 * modelAddRate_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
	 * 
	 * tblAddRate_total = new _JTableTotal(modelAddRate_total, tblAddRate);
	 * tblAddRate_total.setFont(dialog11Bold);
	 * scrollAddRate_total.setViewportView(tblAddRate_total); ((_JTableTotal)
	 * tblAddRate_total).setTotalLabel(0);}
	 * 
	 * tblAddRate.packAll();
	 * tblAddRate.getColumnModel().getColumn(0).setPreferredWidth(50);
	 * tblAddRate.getColumnModel().getColumn(4).setPreferredWidth(100);
	 * 
	 * int row_tot = tblAddRate.getRowCount(); modelAddRate_total.setValueAt(new
	 * BigDecimal(row_tot), 0, 1);
	 * 
	 * //adjustRowHeight_account(tblAddRate);
	 * 
	 * modelAddRate.setEditable(true); tblAddRate.setEditable(true); }
	 */

	private static void displayAgentDownline(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select \r\n" + "\r\n" + "a.agent_id,\r\n" + "trim(b.entity_name) as agent_name,\r\n"
				+ "trim(c.division_alias) as div_alias,\r\n" + "trim(d.dept_alias) as dept_alias,\r\n"
				+ "trim(e.position_abbv) as position,\r\n"
				+ "(case when salestype_id = '001' then 'IN-HOUSE' else 'EXTERNAL' end) as sales_type,\r\n"
				+ "a.starting_rate,\r\n" + "a.accredit_from,\r\n" + "a.accredit_to,\r\n"
				+ "(case when atm_on_hand = true then 'T' else 'F' end) as atm_on_hand\r\n" + "\r\n"
				+ "from mf_sellingagent_info a \r\n" + "left join rf_entity b on a.agent_id = b.entity_id \r\n"
				+ "left join mf_division c on a.sales_div_id = c.division_code\r\n"
				+ "left join mf_department d on a.dept_id = d.dept_code	\r\n"
				+ "left join mf_sales_position e on a.salespos_id = e.position_code\r\n" + "\r\n" + "\r\n"
				+ "where trim(a.override_id) in ( '" + agent_id + "')   " + "order by b.entity_name ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}

		else {
			modelDownline_total = new modelSalesAgentDownline();
			modelDownline_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

			tblDownline_total = new _JTableTotal(modelDownline_total, tblDownline);
			tblDownline_total.setFont(dialog11Bold);
			scrollDownline_total.setViewportView(tblDownline_total);
			((_JTableTotal) tblDownline_total).setTotalLabel(0);
		}

		tblDownline.packAll();
		// tblDownline.getColumnModel().getColumn(0).setPreferredWidth(50);
		// tblDownline.getColumnModel().getColumn(4).setPreferredWidth(100);

		int row_tot = tblDownline.getRowCount();
		modelDownline_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		modelDownline.setEditable(true);
		tblDownline.setEditable(true);
	}

	public static void setAgent_mainDtls(String agent) {

		displayPersonalInfo();

		if (getAgentMainDtls() == null) {

		} else {
			Object[] agent_dtl = getAgentMainDtls();

			refreshFields();

			agent_name = "";
			String div_id = "";
			String div_name = "";
			String dept_id = "";
			String dept_name = "";
			String pos_id = "";
			String pos_name = "";
			String sales_type_id = "";
			String sales_type_name = "";
			String status_name = "";
			String status_id = "";
			String override_id = "";
			String override_name = "";
			String broker_no = "";
			String realty_name = "";
			String coord_id = "";
			String cord_name = "";
			Date date_fr = null;
			Date date_to = null;
			String atm_no = "";
			Date atm_reg = null;
			String atm_bank_acct_id = "";
			String atm_onproc = "";
			String pay_thruatm = "";
			String atm_endorse_for_processing = "";
			String atm_on_hand = "";
			// String in_house_broker_prc = "";
			String in_house_broker_name = "";
			String in_house_broker_idno = "";
			Double agentRate = 0.00;
			String strBranchID = "";

			String strCoID = "";
			String strCo = "";
			String strTypeID = "";
			String strType = "";

			try {
				agent_name = agent_dtl[1].toString();
			} catch (NullPointerException e) {
				agent_name = "";
			}
			try {
				div_id = agent_dtl[2].toString();
			} catch (NullPointerException e) {
				div_id = "";
			}
			try {
				div_name = agent_dtl[3].toString();
			} catch (NullPointerException e) {
				div_name = "";
			}
			try {
				dept_id = agent_dtl[4].toString();
			} catch (NullPointerException e) {
				dept_id = "";
			}
			try {
				dept_name = agent_dtl[5].toString();
			} catch (NullPointerException e) {
				dept_name = "";
			}
			try {
				pos_id = agent_dtl[6].toString();
			} catch (NullPointerException e) {
				pos_id = "";
			}
			try {
				pos_name = agent_dtl[7].toString();
			} catch (NullPointerException e) {
				pos_name = "";
			}
			try {
				sales_type_id = agent_dtl[8].toString();
			} catch (NullPointerException e) {
				sales_type_id = "";
			}
			try {
				sales_type_name = agent_dtl[9].toString();
			} catch (NullPointerException e) {
				sales_type_name = "";
			}
			try {
				status_id = agent_dtl[10].toString();
			} catch (NullPointerException e) {
				status_id = "";
			}
			try {
				strBranchID = agent_dtl[32].toString();
			} catch (NullPointerException e) {
				strBranchID = "";
			}

			String strBranch = FncGlobal.GetString("select branch_alias from mf_office_branch where branch_id = '" + strBranchID + "'");

			try {
				override_id = agent_dtl[11].toString();
			} catch (NullPointerException e) {
				override_id = "";
			}
			try {
				override_name = agent_dtl[12].toString();
			} catch (NullPointerException e) {
				override_name = "";
			}
			try {
				broker_no = agent_dtl[13].toString();
			} catch (NullPointerException e) {
				broker_no = "";
			}
			try {
				realty_name = agent_dtl[14].toString();
			} catch (NullPointerException e) {
				realty_name = "";
			}
			try {
				coord_id = agent_dtl[15].toString();
			} catch (NullPointerException e) {
				coord_id = "";
			}
			try {
				cord_name = agent_dtl[16].toString();
			} catch (NullPointerException e) {
				cord_name = "";
			}
			try {
				date_fr = (Date) agent_dtl[18];
			} catch (NullPointerException e) {
				date_fr = null;
			}
			try {
				date_to = (Date) agent_dtl[19];
			} catch (NullPointerException e) {
				date_to = null;
			}
			try {
				atm_no = agent_dtl[20].toString();
			} catch (NullPointerException e) {
				atm_no = "";
			}
			try {
				atm_reg = (Date) agent_dtl[21];
			} catch (NullPointerException e) {
				atm_reg = null;
			}
			try {
				atm_bank_acct_id = agent_dtl[22].toString();
			} catch (NullPointerException e) {
				atm_bank_acct_id = "";
			}
			try {
				atm_onproc = agent_dtl[23].toString();
			} catch (NullPointerException e) {
				atm_onproc = "";
			}
			try {
				pay_thruatm = agent_dtl[24].toString();
			} catch (NullPointerException e) {
				pay_thruatm = "";
			}
			try {
				status_name = agent_dtl[25].toString();
			} catch (NullPointerException e) {
				status_name = "";
			}
			try {
				atm_endorse_for_processing = agent_dtl[26].toString();
			} catch (NullPointerException e) {
				atm_endorse_for_processing = "";
			}
			try {
				atm_on_hand = agent_dtl[27].toString();
			} catch (NullPointerException e) {
				atm_on_hand = "";
			}

			try {
				in_house_broker_name = agent_dtl[28].toString();
			} catch (NullPointerException e) {
				in_house_broker_name = "";
			}
			try {
				in_house_broker_idno = agent_dtl[30].toString();
			} catch (NullPointerException e) {
				in_house_broker_idno = "";
			}
			try {
				agentRate = Double.parseDouble(agent_dtl[31].toString());
			} catch (NullPointerException e) {
				agentRate = 0.00;
			}

			lookupAgentName.setValue(agent);
			tagAgentName.setTag(agent_name);
			lookupSalesDiv.setValue(div_id);
			tagSalesDiv.setTag(div_name);
			lookupSalesDept.setValue(dept_id);
			tagSalesDept.setTag(dept_name);
			lookupPosition.setValue(pos_id);
			tagPosition.setTag(pos_name);
			lookupSalesType.setValue(sales_type_id);
			tagSalesType.setTag(sales_type_name);
			lookupStatus.setValue(status_id);
			lookupBranch.setValue(strBranchID);

			lookupOverride.setValue(override_id);
			tagOverride.setTag(override_name);
			txtBrokerNo.setText(broker_no);
			txtRealtyName.setText(realty_name);
			lookupBDO.setValue(coord_id);
			tagBDO.setTag(cord_name);
			dteDateReg.setDate(atm_reg);
			lookupIH_BrokerID.setText(in_house_broker_idno);
			txtIHBrokerName.setTag(in_house_broker_name);
			txtRate.setText(df.format(agentRate));
			tagBranch.setTag(strBranch);

			if (status_id.equals("A")) {
				dteAccredFrom.setDate(date_fr);
				dteAccredTo.setDate(date_to);
			} else {
				dteAccredFrom.setDate(null);
				dteAccredTo.setDate(null);
			}
			
			displayAgentAccreditationHistory(modelAccredHistory, rowHeaderAccredHistory, modelAccredHistory_total);
			refreshATM_buttons();
			
			txtATM_No.setText(atm_no);
			if (!atm_no.equals("")) {
				rbtn_with_ATM.setSelected(true);
			} else {
				rbtn_with_ATM.setSelected(false);
			}

			if (atm_onproc.equals("true") && atm_no.equals("")) {
				rbtnATM_onProcess.setSelected(true);
			} else {
				rbtnATM_onProcess.setSelected(false);
			}
			if (atm_endorse_for_processing.equals("true") && !atm_onproc.equals("true")) {
				rbtnEndorseATM.setSelected(true);
				rbtnATM_onProcess.setSelected(false);
			} else {
				rbtnEndorseATM.setSelected(false);
			}

			if (atm_no.equals("") && atm_onproc.equals("") && atm_endorse_for_processing.equals("")
					|| atm_no.equals("") && atm_onproc.equals("false") && atm_endorse_for_processing.equals("")) {
				rbtn_no_ATM.setSelected(true);
			} else {
				rbtn_no_ATM.setSelected(false);
			}

			if (pay_thruatm.equals("true")) {
				chkRelThruATM.setSelected(true);
			} else {
				chkRelThruATM.setSelected(false);
			}
			lookupATMAcctNo.setText(atm_bank_acct_id);

			if (!atm_no.equals("")) {
				if (atm_on_hand.equals("true")) {
					rbtnATM_forRelease.setSelected(false);
					rbtnATM_onHand.setSelected(true);
				} else {
					rbtnATM_forRelease.setSelected(true);
					rbtnATM_onHand.setSelected(false);
				}
			} else {
				rbtnATM_forRelease.setSelected(false);
				rbtnATM_onHand.setSelected(false);
			}

			tagStatus.setTag(status_name);
			displayAgentDownline(modelDownline, rowHeaderDownline, modelDownline_total);
			displayAgentDocuments(modelDocuments, rowHeaderDocuments, modelDocuments_total);
			displayBroker(modelBroker, rowHeaderBroker, modelBroker_total);
			displayAgent(modelAgents, rowHeaderAgents, modelAgents_total);

			if (sales_type_id.equals("001")) {
				lblBrokerNo.setEnabled(false);
				lblLicNo.setEnabled(false);
				txtBrokerNo.setEnabled(false);
				lblRealtyName.setEnabled(false);
				txtRealtyName.setEnabled(false);
				lblBroker.setEnabled(true);
				lookupIH_BrokerID.setEnabled(true);
				txtIHBrokerName.setEnabled(true);
			} else if (sales_type_id.equals("002")) {
				lblBrokerNo.setEnabled(true);
				lblLicNo.setEnabled(true);
				txtBrokerNo.setEnabled(true);
				lblRealtyName.setEnabled(true);
				txtRealtyName.setEnabled(true);
				lblBroker.setEnabled(false);
				lookupIH_BrokerID.setEnabled(false);
				txtIHBrokerName.setEnabled(false);
			}
		}
	}

	public static void searchAgentList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String str) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "\r\n" + "select\r\n" + "\r\n" + "trim(a.agent_id) as agent,\r\n"
				+ "trim(b.entity_name) as agent_name,\r\n" + //
				"trim(c.division_alias) as div,\r\n" + "trim(d.dept_alias) as dept,\r\n"
				+ "trim(e.position_abbv) as posn,\r\n" + "(case when a.salestype_id = '001' then 'Agent' else \r\n"
				+ "	(case when a.salestype_id = '002' then 'Broker' end ) end) as type,\r\n" + "a.status_id\r\n"
				+ "\r\n" + "\r\n" + "from mf_sellingagent_info a\r\n"
				+ "left join rf_entity b on a.agent_id = b.entity_id\r\n"
				+ "left join mf_division c on a.sales_div_id = c.division_code\r\n"
				+ "left join mf_department d on a.dept_id = d.dept_code\r\n"
				+ "left join mf_sales_position e on a.salespos_id = e.position_code \r\n" + "\r\n"
				+ "where a.agent_id like '%" + str + "%' or upper(b.entity_name) like '%" + str.toUpperCase() + "%' \n"
				+ //
				"order by a.agent_id ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			// totalAllComm(modelMain, modelTotal);
		}

		else {
			modelAgentList_total = new modelComm_addAgent_list();
			modelAgentList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

			tblAgentList_total = new _JTableTotal(modelAgentList_total, tblAgentList);
			tblAgentList_total.setFont(dialog11Bold);
			scrollAgentList_total.setViewportView(tblAgentList_total);
			((_JTableTotal) tblAgentList_total).setTotalLabel(0);
		}

		tblAgentList.packAll();
		tblAgentList.getColumnModel().getColumn(0).setPreferredWidth(80);

		int row_tot = tblAgentList.getRowCount();
		modelAgentList_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		// adjustRowHeight_account(tblAgentList);
	}

	private static void displayAgentDocuments(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal) {//

		if (isContractIn() == true) {
			chkContractIn.setSelected(true);
		} else {
			chkContractIn.setSelected(false);
		}

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select \r\n" + "agent_doc_rec_id, \r\n" + "trim(a.doc_id),\r\n" + "trim(b.agent_doc_name),\r\n"
				+ "a.date_submitted,\r\n" + "trim(d.entity_name),\r\n" + "a.remarks,\r\n" + "a.status_id\r\n" + "\r\n"
				+ "from cm_agent_documents a\r\n" + "left join cm_agent_docs b on a.doc_id::int = b.agent_doc_id \r\n"
				+ "left join em_employee c on a.received_by = c.emp_code\r\n"
				+ "left join rf_entity d on c.entity_id = d.entity_id " + "where a.agent_code = '" + agent_id + "'  \n"
				+ "order by a.agent_doc_rec_id  ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}

		else {
			modelDocuments_total = new modelSalesAgentSubmittedDocs();
			modelDocuments_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

			tblDocuments_total = new _JTableTotal(modelDocuments_total, tblDocuments);
			tblDocuments_total.setFont(dialog11Bold);
			scrollDocuments_total.setViewportView(tblDocuments_total);
			((_JTableTotal) tblDocuments_total).setTotalLabel(0);
		}

		tblDocuments.packAll();
		// tblDownline.getColumnModel().getColumn(0).setPreferredWidth(50);
		// tblDownline.getColumnModel().getColumn(4).setPreferredWidth(100);

		int row_tot = tblDocuments.getRowCount();
		modelDocuments_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		modelDocuments.setEditable(false);
		tblDocuments.setEditable(false);
	}

	private static void displayPersonalInfo() {

		Object[] agent_prsnl_dtl = getAgentPersonalDtls();

		String first_name = "";
		String mid_name = "";
		String last_name = "";
		String type = "";
		String tin = "";
		String sss = "";
		String prc_no = "";
		String hlurb_no = "";
		Date prc_valid_date = null;
		Date hlurb_valid_date = null;
		String vatable = "";

		try {
			first_name = agent_prsnl_dtl[0].toString();
		} catch (NullPointerException e) {
			first_name = "";
		}
		try {
			mid_name = agent_prsnl_dtl[1].toString();
		} catch (NullPointerException e) {
			mid_name = "";
		}
		try {
			last_name = agent_prsnl_dtl[2].toString();
		} catch (NullPointerException e) {
			last_name = "";
		}
		try {
			type = agent_prsnl_dtl[3].toString();
		} catch (NullPointerException e) {
			type = "";
		}
		try {
			tin = agent_prsnl_dtl[4].toString();
		} catch (NullPointerException e) {
			tin = "";
		}
		try {
			sss = agent_prsnl_dtl[5].toString();
		} catch (NullPointerException e) {
			sss = "";
		}
		try {
			prc_no = agent_prsnl_dtl[6].toString();
		} catch (NullPointerException e) {
			prc_no = "";
		}
		try {
			hlurb_no = agent_prsnl_dtl[7].toString();
		} catch (NullPointerException e) {
			hlurb_no = "";
		}
		try {
			prc_valid_date = (Date) agent_prsnl_dtl[8];
		} catch (NullPointerException e) {
			prc_valid_date = null;
		}
		try {
			hlurb_valid_date = (Date) agent_prsnl_dtl[9];
		} catch (NullPointerException e) {
			hlurb_valid_date = null;
		}
		try {
			vatable = agent_prsnl_dtl[10].toString();
		} catch (NullPointerException e) {
			vatable = "";
		}

		txtPI_firstname.setText(first_name);
		txtPI_midname.setText(mid_name);
		txtPI_lastname.setText(last_name);
		if (type.equals("INDIVIDUAL")) {
			cmbPI_type.setSelectedIndex(0);
		} else {
			cmbPI_type.setSelectedIndex(1);
		}
		txtTIN.setText(tin);
		txtSSS.setText(sss);
		txtPRC_no.setText(prc_no);
		txtHLURB_no.setText(hlurb_no);
		dtePRC_validity.setDate(prc_valid_date);
		dteHLURB_validity.setDate(hlurb_valid_date);
		if (vatable.equals("YES")) {
			cmbVatable.setSelectedIndex(0);
		} else {
			cmbVatable.setSelectedIndex(1);
		}
	}

	private static void displayAgentAccreditationHistory(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "\r\n" + "select \r\n" + "\r\n" + "trim(b.dept_alias) as dept,\r\n"
				+ "trim(c.division_alias) as div,\r\n" + "trim(d.position_desc) as position,\r\n"
				+ "(case when trim(a.salestype_id) = '001' then 'In-House' else\r\n"
				+ "	(case when trim(a.salestype_id) = '002' then 'External' else '' end ) end) as type,\r\n"
				+ "a.accredit_from,\r\n" + "a.accredit_to,\r\n" + "upper(trim(f.entity_name)) as override,\r\n"
				+ "upper(trim(h.entity_name)) as inactive_by,\r\n" + "a.date_inactive\r\n" + "\r\n" + "\r\n"
				+ "from cm_agent_accreditation_history a\r\n"
				+ "left join mf_department b on a.dept_id = b.dept_code\r\n"
				+ "left join mf_division c on a.sales_div_id = c.division_code\r\n"
				+ "left join mf_sales_position d on a.salespos_id = d.position_code\r\n"
				+ "left join em_employee e on a.override_id = e.emp_code\r\n"
				+ "left join rf_entity f on e.entity_id = f.entity_id\r\n"
				+ "left join em_employee g on a.created_by = g.emp_code\r\n"
				+ "left join rf_entity h on g.entity_id = h.entity_id\r\n" + "\r\n" + "where a.agent_code = '"
				+ agent_id + "'\r\n" + "and a.status_id = 'I'  \r\n" + "order by a.date_inactive\r\n" + "\r\n" + " ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}

		else {
			/*
			 * modelDownline_total = new modelSalesAgentDownline();
			 * modelDownline_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
			 * 
			 * tblDownline_total = new _JTableTotal(modelDownline_total, tblDownline);
			 * tblDownline_total.setFont(dialog11Bold);
			 * scrollDownline_total.setViewportView(tblDownline_total); ((_JTableTotal)
			 * tblDownline_total).setTotalLabel(0);
			 */
		}

		tblAccredHistory.packAll();

	}

	private static void displayBroker(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select \r\n" + "\r\n" + "a.broker_name,\r\n" + "a.prc_no,\r\n" + "a.prc_validity,\r\n"
				+ "a.hlurb_no,\r\n" + "a.hlurb_validity,\r\n" + "a.status_id,\r\n" + "c.entity_name,\r\n"
				+ "a.date_created, \r\n" + "a.default_check_payee \r\n" + "\r\n" + "\r\n"
				+ "from mf_brokers_details a\r\n" + "left join em_employee b on a.created_by = b.emp_code\r\n"
				+ "left join rf_entity c on b.entity_id = c.entity_id " + "where a.agent_id = '" + agent_id + "' "
				+ "and a.status_id = 'A' " + "order by a.broker_name ";
		/*
		 * "select \r\n" + "\r\n" + "a.agent_id,\r\n" +
		 * "trim(b.entity_name) as agent_name,\r\n" +
		 * "trim(c.division_alias) as div_alias,\r\n" +
		 * "trim(d.dept_alias) as dept_alias,\r\n" +
		 * "trim(e.position_abbv) as position,\r\n" +
		 * "(case when salestype_id = '001' then 'IN-HOUSE' else 'EXTERNAL' end) as sales_type,\r\n"
		 * + "a.starting_rate,\r\n" + "a.accredit_from,\r\n" + "a.accredit_to,\r\n" +
		 * "(case when atm_on_hand = true then 'T' else 'F' end) as atm_on_hand\r\n" +
		 * "\r\n" + "from mf_sellingagent_info a \r\n" +
		 * "left join rf_entity b on a.agent_id = b.entity_id \r\n" +
		 * "left join mf_division c on a.sales_div_id = c.division_code\r\n" +
		 * "left join mf_department d on a.dept_id = d.dept_code	\r\n" +
		 * "left join mf_sales_position e on a.salespos_id = e.position_code\r\n" +
		 * "\r\n" + "\r\n" +
		 * "where trim(a.agent_id) in ( select agent_id from mf_brokers_details \n" +
		 * "	where brokerage_id = '"+agent_id+"' and status_id = 'A' )   \n" +
		 * "order by b.entity_name " ;
		 */

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}

		else {
			modelBroker_total = new modelBrokers();
			modelBroker_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

			tblBroker_total = new _JTableTotal(modelBroker_total, tblBroker);
			tblBroker_total.setFont(dialog11Bold);
			scrollBroker_total.setViewportView(tblBroker_total);
			((_JTableTotal) tblBroker_total).setTotalLabel(0);
		}

		tblBroker.packAll();
		// tblDownline.getColumnModel().getColumn(0).setPreferredWidth(50);
		// tblDownline.getColumnModel().getColumn(4).setPreferredWidth(100);

		/*
		 * int row_tot = tblBroker.getRowCount(); modelBroker_total.setValueAt(new
		 * BigDecimal(row_tot), 0, 1);
		 * 
		 * modelBroker.setEditable(true); tblBroker.setEditable(true);
		 */

		btnEditBroker.setEnabled(false);
		btnRemoveBroker.setEnabled(false);
	}

	private static void displayAgent(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "select \r\n" + "\r\n" + "a.agent_id,\r\n" + "trim(b.entity_name) as agent_name,\r\n"
				+ "trim(c.division_alias) as div_alias,\r\n" + "trim(d.dept_alias) as dept_alias,\r\n"
				+ "trim(e.position_abbv) as position,\r\n"
				+ "(case when salestype_id = '001' then 'IN-HOUSE' else 'EXTERNAL' end) as sales_type,\r\n"
				+ "aa.starting_rate,\r\n" + "aa.accredit_from,\r\n" + "aa.accredit_to,\r\n"
				+ "(case when atm_on_hand = true then 'T' else 'F' end) as atm_on_hand\r\n" + "\r\n"
				+ "from (select * from mf_inhouse_brokers_agents where status_id = 'A') a \r\n"
				+ "left join mf_sellingagent_info aa on a.agent_id = aa.agent_id \n"
				+ "left join rf_entity b on a.agent_id = b.entity_id \r\n"
				+ "left join mf_division c on aa.sales_div_id = c.division_code\r\n"
				+ "left join mf_department d on aa.dept_id = d.dept_code	\r\n"
				+ "left join mf_sales_position e on aa.salespos_id = e.position_code\r\n" + "\r\n" + "\r\n"
				+ "where trim(a.broker_id) in ( '" + agent_id + "')   " + "order by b.entity_name ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}

		else {
			modelAgents_total = new modelSalesAgentDownline();
			modelAgents_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

			tblAgent_total = new _JTableTotal(modelAgents_total, tblAgents);
			tblAgent_total.setFont(dialog11Bold);
			scrollAgents_total.setViewportView(tblAgent_total);
			((_JTableTotal) tblAgent_total).setTotalLabel(0);
		}

		tblAgents.packAll();
		// tblDownline.getColumnModel().getColumn(0).setPreferredWidth(50);
		// tblDownline.getColumnModel().getColumn(4).setPreferredWidth(100);

		int row_tot = tblAgents.getRowCount();
		modelAgents_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		modelAgents.setEditable(true);
		tblAgents.setEditable(true);
	}

	// reset, enable, disable
	public void refresh_agentRateTable() {

		// reset table 1
		FncTables.clearTable(modelAddRate);
		FncTables.clearTable(modelAddRate_total);
		rowHeaderAddRate = tblAddRate.getRowHeader();
		scrollAddRate.setRowHeaderView(rowHeaderAddRate);
		modelAddRate_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

	}

	public static void enableMainInfoFields(Boolean x) {

		txtPI_firstname.setEnabled(x);
		txtPI_midname.setEnabled(x);
		txtPI_lastname.setEnabled(x);
		cmbPI_type.setEnabled(x);
		cmbVatable.setEnabled(x);
		txtTIN.setEnabled(x);
		txtSSS.setEnabled(x);
		txtPRC_no.setEnabled(x);
		txtHLURB_no.setEnabled(x);

		dtePRC_validity.setEnabled(x);
		dteHLURB_validity.setEnabled(x);
	}

	private static void refreshATM_buttons() {
		rbtn_with_ATM.setSelected(false);
		rbtnATM_onProcess.setSelected(false);
		rbtn_no_ATM.setSelected(false);
		rbtnEndorseATM.setSelected(false);
		chkRelThruATM.setSelected(false);
		rbtnATM_forRelease.setSelected(false);
		rbtnATM_onHand.setSelected(false);
	}

	private void disableAllButtons() {

		btnEditAccreditation.setEnabled(false);
		btnPrintContract.setEnabled(false);
		btnEditPI.setEnabled(false);
		btnCancelAccreditation.setEnabled(false);
		btnCancelATM.setEnabled(false);
		btnCancelOverride.setEnabled(false);
		btnCancelDocument.setEnabled(false);
		btnCancelPI.setEnabled(false);
		btnPrintContract.setEnabled(false);
		btnEditPI.setEnabled(false);
		btnEditATM.setEnabled(false);
		btnAddNewDownline.setEnabled(false);
		btnRemoveDownline.setEnabled(false);
		btnAddNewDocument.setEnabled(false);
		btnEditDocument.setEnabled(false);
		btnAddNewBroker.setEnabled(false);
		btnRemoveBroker.setEnabled(false);
		btnEditOverride.setEnabled(false);

	}

	private void refreshNewEntityScreen() {

		txtLastName.setText("");
		txtFirstName.setText("");
		txtMiddleName.setText("");
		cmbGender.setSelectedIndex(0);
		dateBirthDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		txtBirthPlace.setText("");
		cmbCivilStatus.setSelectedIndex(0);
		cmbCitizenship.setSelectedIndex(7);
		txtTelephoneNo.setText("");
		txtCellphoneNo.setText("");
		txtFaxNo.setText("");
		txtEmail.setText("");
		txtSSSNo.setText("");
		txtTINNo.setText("");
		txtPRC.setText("");
		dtePRCvalid.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		txtHLURB.setText("");
		dteHLURBvalid.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
	}

	public static void enableFields(boolean x) {

		lookupAgentName.setEnabled(x);
		lookupSalesDiv.setEnabled(x);
		lookupSalesDept.setEnabled(x);
		lookupPosition.setEnabled(x);
		lookupSalesType.setEnabled(x);
		lookupStatus.setEnabled(x);
		lookupOverride.setEnabled(x);
		lookupIH_BrokerID.setEnabled(x);
		txtBrokerNo.setEnabled(x);
		txtBrokerNo.setEnabled(x);
		txtRealtyName.setEnabled(x);
		lookupBDO.setEnabled(x);
		dteAccredFrom.setEnabled(x);
		dteAccredTo.setEnabled(x);
		txtATM_No.setEnabled(x);
		rbtn_with_ATM.setEnabled(x);
		dteDateReg.setEnabled(x);
		lookupATMAcctNo.setEnabled(x);
		rbtnEndorseATM.setEnabled(x);
		chkRelThruATM.setEnabled(x);
		rbtn_no_ATM.setEnabled(x);
		rbtnATM_forRelease.setEnabled(x);
		rbtnATM_onHand.setEnabled(x);
		txtRate.setEnabled(x);
		lblRate.setEnabled(x);
		
		txtCoID.setValue("");
		txtCo.setText("");
		
		txtEntityTypeID.setValue("");
		txtEntityType.setText("");
		
		txtCoID.setEnabled(x);
		txtEntityTypeID.setEnabled(x);
		
		if (x) {
			switcher("input"); 
		} else {
			switcher("output"); 
		}
	}

	private static void refreshFields() {

		lookupAgentName.setValue("");
		tagAgentName.setTag("");
		lookupSalesDiv.setValue("");
		tagSalesDiv.setTag("");
		lookupSalesDept.setValue("");
		tagSalesDept.setTag("");
		lookupPosition.setValue("");
		tagPosition.setTag("");
		lookupSalesType.setValue("");
		tagSalesType.setTag("");
		lookupStatus.setValue("");
		lookupBranch.setValue("");
		lookupOverride.setValue("");
		tagOverride.setTag("");
		txtBrokerNo.setText("");
		txtRealtyName.setText("");
		lookupIH_BrokerID.setText("");
		txtIHBrokerName.setTag("");
		lookupBDO.setValue("");
		tagBDO.setTag("");
		dteAccredFrom.setDate(null);
		dteAccredTo.setDate(null);
		txtATM_No.setText("");
		rbtn_with_ATM.setSelected(false);
		dteDateReg.setDate(null);
		lookupATMAcctNo.setText("");
		rbtnEndorseATM.setSelected(false);
		chkRelThruATM.setSelected(false);
		rbtn_no_ATM.setSelected(false);
		tagStatus.setTag("");
		tagBranch.setTag("");
		rbtnATM_forRelease.setSelected(false);
		rbtnATM_onHand.setSelected(false);
		chkContractIn.setSelected(false);
		txtRate.setText("0.00");

		listCom.removeAll();
		
		/**/

	}

	private void refreshOthers() {
		txtPI_firstname.setText("");
		txtPI_midname.setText("");
		txtPI_lastname.setText("");
		cmbPI_type.setSelectedIndex(0);
		cmbVatable.setSelectedIndex(0);
		txtTIN.setText("");
		txtSSS.setText("");
		txtPRC_no.setText("");
		dtePRC_validity.setDate(null);
		dteHLURB_validity.setDate(null);

		FncTables.clearTable(modelDocuments);
		FncTables.clearTable(modelDocuments_total);
		rowHeaderDocuments = tblDocuments.getRowHeader();
		scrollDocuments.setRowHeaderView(rowHeaderDocuments);
		modelDocuments_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

		FncTables.clearTable(modelDownline);
		FncTables.clearTable(modelDownline_total);
		rowHeaderDownline = tblDownline.getRowHeader();
		scrollDownline.setRowHeaderView(rowHeaderDownline);
		modelDownline_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

		FncTables.clearTable(modelBroker);
		FncTables.clearTable(modelBroker_total);
		rowHeaderBroker = tblBroker.getRowHeader();
		scrollBroker.setRowHeaderView(rowHeaderBroker);
		modelBroker_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
	}

	private void refreshVariables() {

		to_do = ""; // to distinguish whether to add new agent or update existing.
		div_id = "";
		agent_id = "";
		agent_name = "";
		proj_name = "";
		proj_id = "";
		phase = "";
		table = "";
		override_id = "";
	}

	public void initialize_comp() {

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		lblDepartment.setEnabled(true);
		lookupDepartment.setEnabled(true);
		tagDepartment.setEnabled(true);
		lblSearch.setEnabled(true);
		txtSearch.setEnabled(true);
		enableMainButtons(true, false, false, true);

		lookupCompany.setValue(co_id);
	}

	private void refreshButtons() {

		/*
		 * btnAddNewRate.setEnabled(true); btnEditRate.setEnabled(true);
		 * btnSaveRate.setEnabled(false); btnCancelRate.setEnabled(false);
		 */

		btnEditAccreditation.setEnabled(true);
		btnSaveAccreditation.setEnabled(false);
		btnPrintContract.setEnabled(true);
		btnCancelAccreditation.setEnabled(false);

		btnEditATM.setEnabled(true);
		btnSaveATM.setEnabled(false);
		btnCancelATM.setEnabled(false);

		btnEditOverride.setEnabled(true);
		btnSaveOverride.setEnabled(false);
		btnCancelOverride.setEnabled(false);

		btnAddNewDocument.setEnabled(true);
		btnEditDocument.setEnabled(true);
		btnSaveDocument.setEnabled(false);
		btnCancelDocument.setEnabled(false);

		btnAddNewDownline.setEnabled(true);
		btnRemoveDownline.setEnabled(true);

		btnEditPI.setEnabled(true);
		btnSavePI.setEnabled(false);
		btnCancelPI.setEnabled(false);

		btnAddNewBroker.setEnabled(true);
		// btnRemoveBroker.setEnabled(true);

		btnAddNewAgent.setEnabled(true);

	}

	// action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("AddNewRate")) {
			addAgentRate();
		}

		if (e.getActionCommand().equals("EditRate")) {
			editAgentRate();
			table = "edit_rate";
		}

		if (e.getActionCommand().equals("SaveAgentRate")) {
			saveAgentRate();
		}

		if (e.getActionCommand().equals("UpdateRate")) {
			updateAgentRate();
		}

		if (e.getActionCommand().equals("CancelRate")) {
			cancelRate();
		}
	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2) && table.equals("edit_rate")) {
			clickTableColumn_edit();
			System.out.printf("Nag-run ang edit_table!!!  \n");
		}

		if ((evt.getClickCount() >= 2) && table.equals("add_rate")) {
			clickTableColumn();
		}

		if ((evt.getClickCount() >= 2) && table.equals("edit_docs")) {
			clickTableColumn_editDoc();
			System.out.printf("Nag-run ang edit doc table!!!  \n");
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	private void execute_cancel() {

		enableFields(false);
		enableMainButtons(true, false, false, false);
		refreshFields();
		refreshOthers();
		refreshVariables();
		tblAgentList.setEnabled(true);

		ShowList_table(true);
	}

	private void saveAgentRate() {

		if (checkCompleteDetails() == false) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Please enter the following details (Agent, Project and Phase).", "Incomplete Detail",
					JOptionPane.WARNING_MESSAGE);
		} else {

			if (checkAgentRate() == false) {
				JOptionPane.showMessageDialog(getContentPane(), "Please enter agent rate.", "Incomplete Detail",
						JOptionPane.WARNING_MESSAGE);
			} else {

				if (checkAgentEffectDate() == false) {
					JOptionPane.showMessageDialog(getContentPane(), "Please enter effectivity date.",
							"Incomplete Detail", JOptionPane.WARNING_MESSAGE);
				} else {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();
						insertAgentRate(db);
						db.commit();
						JOptionPane.showMessageDialog(getContentPane(), "Agent's commission rate added.", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						btnSaveAgentRate.setEnabled(false);
						lookupRateProject.setValue("");
						lookupRatePhase.setValue("");
						refresh_agentRateTable();
						// displayAgentRate(modelRate, rowHeaderRate, modelRate_total);

					}
				}
			}
		}

	}

	private void updateAgentRate() {

		if (checkAgentRate_edit() == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Please check your agent's rate(s).", "Agent Rate Error",
					JOptionPane.ERROR_MESSAGE);
		} else {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();
				updateAgentRate(db);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Agent's commission rate updated.", "Information",
						JOptionPane.INFORMATION_MESSAGE);

				btnAddNewRate.setEnabled(true);
				btnEditRate.setEnabled(true);
				btnSaveRate.setEnabled(false);
				btnCancelRate.setEnabled(false);
				// displayAgentRate(modelRate, rowHeaderRate, modelRate_total);

			}
		}
	}

	/*
	 * private void selectProject(){
	 * 
	 * lookupProject.setLookupSQL(getProject());
	 * 
	 * int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlProject,
	 * "Select Project", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
	 * new Object[] {}, null);
	 * 
	 * if ( scanOption == JOptionPane.CLOSED_OPTION ) { try {
	 * 
	 * addAgentRate();
	 * 
	 * } catch ( java.lang.ArrayIndexOutOfBoundsException e ) {} } // CLOSED_OPTION
	 * }
	 */

	private void addAgentRate() {

		txtRateAgentID.setText(agent_id);
		txtRateAgentName.setText(agent_name);
		lookupRateProject.setLookupSQL(getProject());

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewRate, "Add New Agent Rate",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

		if (scanOption == JOptionPane.CLOSED_OPTION) {
			try {

			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			}
		} // CLOSED_OPTION
	}

	private void editAgentRate() {

		btnAddNewRate.setEnabled(false);
		btnEditRate.setEnabled(false);
		btnSaveRate.setEnabled(true);
		btnCancelRate.setEnabled(true);

		modelRate.setEditable(true);
		tblRate.setEditable(true);

	}

	private void cancelRate() {

		btnAddNewRate.setEnabled(true);
		btnEditRate.setEnabled(true);
		btnSaveRate.setEnabled(false);
		btnCancelRate.setEnabled(false);

		modelRate.setEditable(false);
		tblRate.setEditable(false);

	}

	private void addNewEntityIndividual() {
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlIndividual,
				"Enter Individual Entity Details", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				new Object[] {}, null);

		if (scanOption == JOptionPane.CLOSED_OPTION) {
			try {

			} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
			}

		} // CLOSED_OPTION
	}

	private void saveAgentDoc() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();
			insertAgentDoc(db);
			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), "Agent document added.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			btnSaveAgentRate.setEnabled(false);
			lookupDoc.setValue("");
			txtDocName.setText("");
			cmbDocStatus.setSelectedIndex(0);
			txtDocRemarks.setText("");
			displayAgentDocuments(modelDocuments, rowHeaderDocuments, modelDocuments_total);

		}

	}

	private void updateAgentDoc() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to update agent's docs.?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();
			updateAgentDoc(db);
			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), "Agent document updated.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			displayAgentDocuments(modelDocuments, rowHeaderDocuments, modelDocuments_total);
			btnAddNewDocument.setEnabled(true);
			btnEditDocument.setEnabled(true);
			btnSaveDocument.setEnabled(false);
			btnCancelDocument.setEnabled(false);
			modelDocuments.setEditable(false);
			tblDocuments.setEditable(false);
		}
	}

	/*
	 * private void setAgentInactive(){
	 * 
	 * if (JOptionPane.showConfirmDialog(getContentPane(),
	 * "Are you sure you want to change status to inactive?", "Confirmation",
	 * JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) ==
	 * JOptionPane.YES_OPTION) {
	 * 
	 * pgUpdate db = new pgUpdate(); updateAgentStatus_toInactive(db);
	 * insertAgentAccredHistory(db); db.commit();
	 * JOptionPane.showMessageDialog(getContentPane(),"Status set to inactive." +
	 * "\n" + "Accreditation history added."
	 * ,"Information",JOptionPane.INFORMATION_MESSAGE);
	 * 
	 * Integer rw = tblAgentList.getSelectedRow(); agent_id =
	 * tblAgentList.getValueAt(rw,0).toString(); setAgent_mainDtls(agent_id);
	 * //displayAgentDocuments(modelDocuments, rowHeaderDocuments,
	 * modelDocuments_total);
	 * 
	 * } }
	 */

	/*
	 * private void edit(){ if (UserInfo.EmployeeCode.equals("900028")||
	 * UserInfo.EmployeeCode.equals("900449")||
	 * UserInfo.EmployeeCode.equals("900383")) { executeEdit(); } else {
	 * JOptionPane.showMessageDialog(getContentPane()
	 * ,"Sorry, you are not authorized to edit Agent's Info..","Information",
	 * JOptionPane.INFORMATION_MESSAGE); }
	 * 
	 * }
	 */

	private void executeEdit() {
		enableFields(true);
		enableMainButtons(false, false, true, true);

		to_do = "edit";
		lookupAgentName.setLookupSQL(getEntity());
		lookupSalesDiv.setLookupSQL(getDivision());
		lookupSalesDept.setLookupSQL(getSalesGroup());
		lookupPosition.setLookupSQL(getSalesPosition(lookupSalesType.getText()));
		lookupSalesType.setLookupSQL(getSalesType());
		lookupStatus.setLookupSQL(getStatus());
		lookupOverride.setLookupSQL(
				getOverride(lookupSalesType.getText(), lookupPosition.getText(), lookupSalesDept.getText()));
		lookupBDO.setLookupSQL(getBDO(lookupSalesDept.getText()));

		/*
		 * if (lookupStatus.getText().equals("A")) { lookupSalesDiv.setEnabled(false);
		 * lookupSalesDept.setEnabled(false); } else if
		 * (lookupStatus.getText().equals("I")) { lookupSalesDiv.setEnabled(true);
		 * lookupSalesDept.setEnabled(true); } lookupStatus.setEnabled(false);
		 */
	}

	// lookup, select, get statements
	private String getEntity() {

		String sql = "select " + "entity_id as \"Entity ID\", " + "trim(entity_name) as \"Entity Name\" "
				+ "from rf_entity " + "where entity_id not in (select agent_id  from mf_sellingagent_info) "
				+ "order by entity_id\r\n" + "";

		return sql;
	}

	private String getProject() {

		String sql = "select " + "proj_id as \"Project ID\", " + "proj_alias as \"Project Alias\", "
				+ "proj_name as \"Project Name\" " + "from mf_project where status_id = 'A' and co_id = '" + co_id
				+ "' ";

		return sql;
	}

	/*
	 * private String getPhase(){
	 * 
	 * String sql = "select \r\n" + "distinct on (a.proj_id, a.sub_proj_id)\r\n" +
	 * "\r\n" + "a.sub_proj_id  as \"Subproj Code\",\r\n" +
	 * "a.phase as \"Phase\",  \r\n" + "a.proj_id as \"Proj Code\",\r\n" +
	 * "b.proj_name as \"Proj Name\"  \r\n" +
	 * 
	 * "from mf_unit_info a\r\n" +
	 * "left join mf_project b on a.proj_id = b.proj_id\r\n" +
	 * "where co_id = '"+co_id+"' and a.proj_id = '"+proj_id+"'   " +
	 * "and sub_proj_id is not null or sub_proj_id != '' " ; return sql; }
	 */

	private String getDivisionMain() {

		String sql = "select " + "trim(b.division_code) as \"Div Code\", " + "trim(b.division_name) as \"Div Name\", "
				+ "trim(b.division_alias) as \"Div Alias\" " + "from mf_division b "
				+ "where b.division_code in ('06','07','08','09','29') " + "and status_id = 'A' "; // '04',

		return sql;
	}

	private String getDivision() {

		String sql = "select " + "trim(b.division_code) as \"Div Code\", " + "trim(b.division_name) as \"Div Name\", "
				+ "trim(b.division_alias) as \"Div Alias\" " + "from ( select distinct on (sales_div_id) sales_div_id "
				+ "from mf_sellingagent_info where sales_div_id is not null or sales_div_id != ''  ) a \r\n"
				+ "left join mf_division b on trim(a.sales_div_id) = trim(b.division_code) "
				+ "where b.division_code is not null ";

		return sql;
	}

	private String getSalesGroup() {// used
		return "select \r\n" + "\r\n" + "a.dept_code as \"Dept. ID\",\r\n" + "a.dept_alias  as \"Dept. Alias\",\r\n"
				+ "b.division_name as \"Division\" \r\n" + "\r\n" + "\r\n" + "from mf_department a\r\n"
				+ "left join mf_division b on a.division_code = b.division_code\r\n" + "\r\n" + "where "
				+ "(case when '" + div_id + "' = '' then a.division_code in ('06','07','08','09','29') else "
				+ "a.division_code = '" + div_id + "' end)";

	}

	private String getSalesPosition(String type) {// used
		return "select \r\n" + "\r\n" + "a.position_code as \"Code\",\r\n" + "a.position_desc as \"Description\",\r\n"
				+ "a.position_abbv as \"Alias\"\r\n" + "\r\n" + "from mf_sales_position a \n" + "where (case when '"
				+ type + "' = '' then a.position_code is not null " + "else a.salestype_code = '" + type + "' end)   ";

	}

	private String getSalesType() {// used
		return "\r\n" + "select distinct on (salestype_code) \r\n" + "salestype_code,\r\n"
				+ "(case when salestype_code = '001' then 'In-House' else 'External' end)\r\n"
				+ "from mf_sales_position";

	}

	private String getStatus() {

		String sql = "select " + "status_id as \"Status ID\"," + "status_desc as \"Status Desc\" "
				+ "from mf_record_status " + "where status_id in ('A','I','D')";
		return sql;
	}

	private String getBankAcct() {

		String sql = "select \r\n" + "bank_acct_id,\r\n" + "acct_desc,\r\n" + "bank_acct_no\r\n" + "\r\n"
				+ "from mf_bank_account \r\n" + "\r\n" + "order by bank_acct_id";
		return sql;
	}

	private String getOverride(String type, String pos, String dept) {

		String sql = "select\r\n" + "\r\n" + "agent_id as \"Agent ID\", \r\n"
				+ "upper(trim(b.entity_name)) as \"Agent name\",\r\n" + "c.position_desc as \"Position\" \r\n" + "\r\n"
				+ "\r\n" + "from mf_sellingagent_info a\r\n" + "left join rf_entity b on a.agent_id = b.entity_id\r\n"
				+ "left join mf_sales_position c on a.salespos_id = c.position_code\r\n" + "\r\n"
				+ "where a.salestype_id = '" + type + "'\r\n" + "and a.salespos_id != '" + pos + "'\r\n"
				+ "and a.status_id = 'A'\r\n" + "and a.dept_id = '" + dept + "'";
		return sql;
		// System.out.printf("SQL #1:");
	}

	private String getBDO(String dept) {

		String sql = "select \r\n" + "\r\n" + "b.entity_id as \"Entity ID\",\r\n"
				+ "upper(trim(b.entity_name)) as \"Name\"," + "a.div_code as \"Div. Code\", \r\n"
				+ "c.division_alias as \"Division\"  \r\n" + "\r\n" + "from em_employee a \r\n"
				+ "left join rf_entity b on a.entity_id = b.entity_id "
				+ "left join mf_division c on a.div_code = c.division_code  \n";

		if (div_id.equals("")) {
			sql = sql + "where trim(emp_pos) like 'Bus. Devt.%' \n";
		} else {
			sql = sql + "where div_code = '" + div_id + "' and trim(emp_pos) like 'Bus. Devt.%' \n";
		}

		if (dept.equals("")) {
		} else {
			sql = sql + "and dept_code = '" + dept + "'  \n";
		}

		sql = sql + "\r\n";
		// "where a.dept_code = '"+dept+"'";
		return sql;
	}

	private String getIn_HouseBrokers() {

		String sql = "select \r\n" + "\r\n" + "b.entity_id as \"Entity ID\",\r\n"
				+ "upper(trim(b.entity_name)) as \"Name\"," + "a.div_code as \"Div. Code\", \r\n"
				+ "c.division_alias as \"Division\"  \r\n" + "\r\n" + "from em_employee a \r\n"
				+ "left join rf_entity b on a.entity_id = b.entity_id "
				+ "left join mf_division c on a.div_code = c.division_code  \n"
				+ "left join mf_sellingagent_info d on a.entity_id = d.agent_id \n"
				+ "left join rf_entity_id_no e on d.agent_id = e.entity_id \n" + "where d.salespos_id = '009' \n"
				+ "and e.prc_id is not null \n" + "and e.hlurb_regist_no is not null \n" + "order by b.entity_name \n";
		return sql;
	}

	private static Object[] getAgentMainDtls() {

		String strSQL = "select\r\n" + "\r\n" + "trim(a.agent_id) as agent,\r\n" + // 0
				"trim(b.entity_name) as agent_name,\r\n" + "a.sales_div_id,\r\n" + "trim(c.division_alias) as div,\r\n"
				+ "a.dept_id,\r\n" + "trim(d.dept_alias) as dept,\r\n" + // 5
				"a.salespos_id,\r\n" + "trim(e.position_desc) as posn,\r\n" + "a.salestype_id,\r\n"
				+ "(case when trim(a.salestype_id) = '001' then 'In-House' else \r\n"
				+ "	(case when trim(a.salestype_id) = '002' then 'External' else '' end ) end) as type,\r\n"
				+ "a.status_id," + // 10
				"(case when a.override_id = '' or a.override_id is null then '-' else a.override_id end), \n"
				+ "(case when f.entity_name = '' or f.entity_name is null then '-' else f.entity_name end), \n"
				+ "(case when a.broker_lic_no = '' or a.broker_lic_no is null then '-' else a.broker_lic_no end), \n"
				+ "(case when a.realty_name = '' or a.realty_name is null then '-' else a.realty_name end), \n"
				+ "trim(a.bdo_id), \n" + // 15
				"trim(h.entity_name) as coord_name, \n" + "a.is_dummy_agent::text, \n" + "a.accredit_from, \n"
				+ "a.accredit_to, \n" + "trim(a.atm_no), \n" + // 20
				"a.atm_regdate, \n" + "a.atm_bank_acct_id, \n" + "a.atm_onproc::text , \n" + "a.paythruatm::text, \n"
				+ "i.status_desc, \n" + // 25
				"a.atm_endorse_for_processing::text, \n" + "a.atm_on_hand, \n " + "upper(k.entity_name) \n,"
				+ "(case when l.prc_id = '' or l.prc_id is null then '-' else l.prc_id end)," + "j.broker_id, \n " + // 30
				"(case when m.rate is null then 0 else m.rate end) as rate, \n" + "a.branch_id \n"
				+ "from mf_sellingagent_info a\r\n" + "left join rf_entity b on a.agent_id = b.entity_id\r\n"
				+ "left join mf_division c on a.sales_div_id = c.division_code\r\n"
				+ "left join mf_department d on a.dept_id = d.dept_code\r\n"
				+ "left join mf_sales_position e on a.salespos_id = e.position_code \r\n"
				+ "left join rf_entity f on a.override_id = f.entity_id\r\n" +
				// "left join em_employee g on a.bdo_id = g.emp_code \r\n" +
				"left join rf_entity h on a.bdo_id = h.entity_id "
				+ "left join mf_record_status i on a.status_id = i.status_id \r\n"
				+ "left join mf_inhouse_brokers_agents j on a.agent_id = j.agent_id  \n"
				+ "left join rf_entity k on j.broker_id = k.entity_id \n"
				+ "left join rf_entity_id_no l on j.broker_id = l.entity_id \n"
				+ "left join (select * from cm_agent_rate_history where status_id = 'A') m on a.agent_id = m.agent_code \n"
				+ "where a.agent_id = '" + agent_id + "'  \r\n" + "order by b.entity_name ";

		System.out.printf("SQL #1 getAgentMainDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	private static Object[] getAgentPersonalDtls() {

		String strSQL = "select \r\n" + "\r\n" + "upper(trim(a.first_name)),\r\n" + // 0
				"upper(trim(a.middle_name)),\r\n" + // 1
				"upper(trim(a.last_name)),\r\n" + // 2
				"(case when trim(entity_kind) = 'I' then 'INDIVIDUAL' else 'CORPORATION' end) as type,\r\n" + // 3
				"trim(b.tin_no),\r\n" + // 4
				"trim(b.sss_no),\r\n" + // 5
				"trim(b.prc_id),\r\n" + // 6
				"trim(b.hlurb_regist_no),\r\n" + // 7
				"b.date_prc_id,\r\n" + // 8
				"b.hlurb_reg_no_valid_date,"
				+ "(case when a.vat_registered = true then 'YES' else 'NO' end) as vatable \r\n" + // 9
				"\r\n" + "from rf_entity a\r\n" + "left join rf_entity_id_no b on a.entity_id = b.entity_id\r\n"
				+ "\r\n" + "where a.entity_id = '" + agent_id + "' ";

		System.out.printf("SQL #1 getAgentMainDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	/*
	 * private String sql_getProjID(String proj_alias) {
	 * 
	 * String prj = "";
	 * 
	 * String SQL = "select proj_id from mf_project " +
	 * "where trim(proj_alias) = '"+proj_alias+"'  " + "and status_id != 'I' \n" ;
	 * 
	 * System.out.printf("SQL #1 getCV_no : %s", SQL); pgSelect db = new pgSelect();
	 * db.select(SQL);
	 * 
	 * 
	 * if(db.isNotNull()){ prj = (String) db.getResult()[0][0]; }else{ prj = ""; }
	 * 
	 * return prj; }
	 */

	private Integer sql_getLastRecID() {

		Integer rec_id = 0;

		String SQL = "select max(coalesce(rate_id::int,0)) from cm_rate_per_project_phase ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				rec_id = 0;
			} else {
				rec_id = (Integer) db.getResult()[0][0];
			}

		} else {
			rec_id = 0;
		}

		return rec_id;
	}

	public static Integer sql_getLastRecID_contact() {

		Integer rec_id = 0;

		String SQL = "select max(coalesce(contact_id::int,0)) + 1 from rf_contacts ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				rec_id = 0;
			} else {
				rec_id = (Integer) db.getResult()[0][0];
			}

		} else {
			rec_id = 0;
		}

		return rec_id;
	}

	public static String sql_getEntityID() {

		String rec_id = "";

		String SQL = "select get_new_entity_id()";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				rec_id = "";
			} else {
				rec_id = (String) db.getResult()[0][0];
			}

		} else {
			rec_id = "";
		}

		return rec_id;
	}

	public static Boolean isAgenthasCAwriteoff(String agent) {

		Boolean isAgenthasCAwriteoff = false;

		String SQL = "select\r\n" + "\r\n" + "writeoff_no\r\n" + "\r\n" + "from rf_ca_writeoff\r\n" + "\r\n"
				+ "where payee_id = '" + agent + "'";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			isAgenthasCAwriteoff = true;
		} else {
			isAgenthasCAwriteoff = false;
		}

		return isAgenthasCAwriteoff;

	}

	public String getDocuments() {

		String sql = "select \r\n" + "agent_doc_id as \"Doc ID\",\r\n" + "trim(agent_doc_name) as \"Doc Name\" \r\n"
				+ "from cm_agent_docs \r\n" + "where status_id = 'A'\r\n" + "order by agent_doc_id ";

		return sql;
	}

	public static Integer sql_getNextDocRecID() {

		Integer rec_id = 0;

		String SQL = "select max(coalesce(agent_doc_rec_id::int,0)) + 1 from cm_agent_documents ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				rec_id = 1;
			} else {
				rec_id = (Integer) db.getResult()[0][0];
			}

		} else {
			rec_id = 1;
		}

		return rec_id;
	}

	public String sql_getCompAddress() {

		String co_address = "";

		String SQL = "select trim(company_address) from mf_company where co_id = '" + co_id + "'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				co_address = "";
			} else {
				co_address = (String) db.getResult()[0][0];
			}

		} else {
			co_address = "";
		}

		return co_address;
	}

	public String sql_geBrkrAddress() {

		String co_address = "";

		String SQL = "select ( a.addr_no || ' ' || (trim(a.street)) || ', ' || (trim(a.barangay)) || ', ' || \r\n"
				+ " (case when a.city_id is null or trim(a.city_id) = '' then (trim(c.municipality_name)) else (trim(b.city_name)) end ) || \r\n"
				+ " (case when a.province_id is null or trim(a.province_id) = '' then '' else ', ' || (trim(d.province_name)) end ) \r\n"
				+ " ) as address \r\n" + "\r\n" + " from rf_entity_address a\r\n"
				+ " left join mf_city b on a.city_id = b.city_id\r\n"
				+ " left join mf_municipality c on a.municipality_id = c.municipality_id\r\n"
				+ " left join mf_province d on a.province_id = d.province_id\r\n" + "\r\n" + " where a.entity_id = '"
				+ agent_id + "'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				co_address = "";
			} else {
				co_address = (String) db.getResult()[0][0];
			}

		} else {
			co_address = "";
		}

		return co_address;
	}

	public String getAgentDownline() {

		String sql = "select " + "a.agent_id as \"Agent ID\", " + "trim(aa.entity_name) as \"Agent Name\", "
				+ "b.position_code as \"Position\",  " + "upper(trim(c.entity_name)) as \"Override\"  " +

				"from mf_sellingagent_info a " + "left join rf_entity aa on a.agent_id = aa.entity_id "
				+ "left join mf_sales_position b on a.salespos_id = b.position_code "
				+ "left join rf_entity c on a.override_id = c.entity_id " +

				"where a.override_id is null or trim(a.override_id) = '' " + "order by a.agent_id \r\n" + "";

		return sql;
	}

	public String getAgent() {

		String sql = "select " + "a.agent_id as \"Agent ID\", " + "trim(aa.entity_name) as \"Agent Name\", "
				+ "b.position_code as \"Position\",  " + "upper(trim(c.entity_name)) as \"Override\"  " +

				"from mf_sellingagent_info a " + "left join rf_entity aa on a.agent_id = aa.entity_id "
				+ "left join mf_sales_position b on a.salespos_id = b.position_code "
				+ "left join rf_entity c on a.override_id = c.entity_id " +

				"where a.salestype_id = '001' " + "and a.status_id = 'A' " + "and a.salespos_id not in ('004','009') "
				+ "and a.agent_id not in (select agent_id from mf_inhouse_brokers_agents where status_id = 'A')"
				+ "order by a.agent_id \r\n" + "";

		return sql;
	}

	public String getBroker() {

		String sql = "select " + "a.agent_id as \"Agent ID\", " + "trim(aa.entity_name) as \"Agent Name\", "
				+ "b.position_code as \"Position\",  " + "upper(trim(c.entity_name)) as \"Override\"  " +

				"from mf_sellingagent_info a " + "left join rf_entity aa on a.agent_id = aa.entity_id "
				+ "left join mf_sales_position b on a.salespos_id = b.position_code "
				+ "left join rf_entity c on a.override_id = c.entity_id " +

				"where a.salespos_id = '005'  \n" + // '005' : Brokers
				"and a.agent_id  not in (select agent_id from mf_brokers_details where status_id = 'A')\r\n" + // not
																												// under
																												// a
																												// brokerage
																												// yet
				"and a.agent_id != '" + agent_id + "' "
				+ "and a.agent_id not in (select brokerage_id from mf_brokers_details )\n" + "order by a.agent_id \r\n"
				+ "";

		return sql;
	}

	public static Boolean isEntityNameExisting(String first_name, String mid_name, String last_name) {

		Boolean isAgenthasCAwriteoff = false;

		String SQL = "select\r\n" + "\r\n" + "writeoff_no\r\n" + "\r\n" + "from rf_ca_writeoff\r\n" + "\r\n"
				+ "where payee_id = '" + first_name + "'";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			isAgenthasCAwriteoff = true;
		} else {
			isAgenthasCAwriteoff = false;
		}

		return isAgenthasCAwriteoff;

	}

	public Object[] getBDO_details(String dept) {

		String strSQL = "select c.entity_id, c.entity_name\r\n" + "from mf_department a\r\n"
				+ "left join em_employee b on a.dept_head_code = emp_code\r\n"
				+ "left join rf_entity c on b.entity_id = c.entity_id\r\n" + "where a.dept_code = '" + dept + "' ";

		/*
		 * "select \r\n" + "\r\n" + "a.entity_id,\r\n" +
		 * "upper(trim(b.entity_name)) as name\r\n" + "\r\n" + "from em_employee a\r\n"
		 * + "left join rf_entity b on a.entity_id = b.entity_id\r\n" + "\r\n" +
		 * "where emp_pos like 'Bus. Devt.%' \r\n" + "and dept_code = '"+dept+"'" ;
		 */

		System.out.printf("SQL #1 getBDO_details: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	public String sql_getBrokerRep() {

		String broker_rep = "";

		String SQL = "select upper(trim(contactperson)) from rf_contacts where entity_id = '" + agent_id + "'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				broker_rep = "";
			} else {
				broker_rep = (String) db.getResult()[0][0];
			}

		} else {
			broker_rep = "";
		}

		return broker_rep;
	}

	public static Boolean isContractIn() {

		Boolean isContractIn = false;

		String SQL = "select agent_id  from mf_agent_contract where agent_id = '" + agent_id
				+ "' and status_id = 'A'  ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			isContractIn = true;
		} else {
			isContractIn = false;
		}

		return isContractIn;

	}

	public static Boolean hasDefaultPayee() {

		Boolean defPayee = false;

		String SQL = "select agent_id from mf_brokers_details where agent_id = '" + agent_id + "' "
				+ "and default_check_payee = true and status_id = 'A'  ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			defPayee = true;
		} else {
			defPayee = false;
		}

		return defPayee;

	}

	// table operations
	private void clickTable() {

		Integer rw = tblAgentList.getSelectedRow();
		agent_id = tblAgentList.getValueAt(rw, 0).toString();
		setAgent_mainDtls(agent_id);
		
		try {
			listCom.setModel(new DefaultComboBoxModel(getCompany(agent_id).toArray()));
		} catch (Exception e) {
	        DefaultComboBoxModel model = (DefaultComboBoxModel) listCom.getModel();
	        model.removeAllElements();
		}
		
		listCom.repaint();
		listCom.revalidate();
		
		refreshButtons();
		enableFields(false);
		enableMainButtons(true, true, false, true);
		enableMainInfoFields(false);

	}

	private void clickTableColumn() {

		int column = tblAddRate.getSelectedColumn();
		int row = tblAddRate.getSelectedRow();

		Integer x[] = { 0, 1, 2, 3, 4, 5 };
		String sql[] = { "", "", "", "", "", getStatus() };
		String lookup_name[] = { "", "", "", "", "", "Status" };

		if (column == 4 && modelAddRate.isEditable()) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Effectivity Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

			if (scanOption == JOptionPane.CLOSED_OPTION) {
				try {
					System.out.printf("Nag-run ang date!!!  \n");
					modelAddRate.setValueAt(dteRefDate.getDate(), row, 4);
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				}
			} // CLOSED_OPTION

		}

		if (column == x[column] && modelAddRate.isEditable() && sql[column] != "") {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 5) {
				modelAddRate.setValueAt(data[0], row, 5);
			}
		}

		else {
		}
	}

	private void clickTableColumn_edit() {

		System.out.printf("Nag-run ang edit_table!!!  \n");

		int column = tblRate.getSelectedColumn();
		int row = tblRate.getSelectedRow();

		Integer x[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		String sql[] = { "", "", "", "", "", "", getStatus(), "", "" };
		String lookup_name[] = { "", "", "", "", "", "", "Status", "", "" };

		if (column == 5 && modelRate.isEditable()) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Effectivity Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

			if (scanOption == JOptionPane.CLOSED_OPTION) {
				try {
					System.out.printf("Nag-run ang date!!!  \n");
					modelRate.setValueAt(dteRefDate.getDate(), row, 5);
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				}
			} // CLOSED_OPTION

		}

		if (column == x[column] && modelRate.isEditable() && sql[column] != "") {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 6) {

				modelRate.setValueAt(data[0], row, 6);
			}
		}

		else {
		}
	}

	/*
	 * private void tagRateTable(){
	 * 
	 * int x = tblAddRate.getSelectedRow();
	 * 
	 * Boolean isTrue = false; if(modelAddRate.getValueAt(x,0) instanceof String){
	 * isTrue = new Boolean((String)modelAddRate.getValueAt(x,0)); }
	 * if(modelAddRate.getValueAt(x,0) instanceof Boolean){ isTrue =
	 * (Boolean)modelAddRate.getValueAt(x,0); }
	 * 
	 * if(isTrue){
	 * 
	 * modelAddRate.setValueAt("A", x, 5);
	 * 
	 * } else { modelAddRate.setValueAt("", x, 5); }
	 * 
	 * 
	 * }
	 */

	public static void enableMainButtons(Boolean a, Boolean b, Boolean c, Boolean d) {
		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnCancel.setEnabled(d);
	}

	private void clickTableColumn_editDoc() {

		int column = tblDocuments.getSelectedColumn();
		int row = tblDocuments.getSelectedRow();

		Integer x[] = { 0, 1, 2, 3, 4, 5, 6 };
		String sql[] = { "", getDocuments(), "", "", "", "", getStatus() };
		String lookup_name[] = { "", "Document", "", "", "", "", "Status" };

		if (column == 3 && modelDocuments.isEditable()) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Effectivity Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

			if (scanOption == JOptionPane.CLOSED_OPTION) {
				try {
					modelDocuments.setValueAt(dteRefDate.getDate(), row, 3);
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				}
			} // CLOSED_OPTION

		}

		else if (column == x[column] && modelDocuments.isEditable() && sql[column] != "") {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();

			if (data != null && column == 6) {
				modelDocuments.setValueAt(data[0], row, 6);
			}

			else if (data != null && column == 1) {
				modelDocuments.setValueAt(data[0], row, 1);
				modelDocuments.setValueAt(data[1], row, 2);
				tblDocuments.packAll();
			}
		}

		else {
		}
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (lookupStatus.getText().equals("A")) {
					mniInactiveAgent.setEnabled(true);
				} else {
					mniInactiveAgent.setEnabled(false);
				}
			}
		}

		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (lookupStatus.getText().equals("A")) {
					mniInactiveAgent.setEnabled(true);
				} else {
					mniInactiveAgent.setEnabled(false);
				}
			}
		}
	}

	private void CreateList(Boolean showDivList) {

		{
			pnlTable = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlTable, BorderLayout.CENTER);
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(0, 0));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(521, 443));

			splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			pnlSubTable.add(splitPanel);
			splitPanel.setOneTouchExpandable(true);
			ShowList_table(showDivList);
			{

				pnlSubTable_2 = new JPanel(new GridLayout(1, 1, 0, 0));
				splitPanel.add(pnlSubTable_2, JSplitPane.RIGHT);
				pnlSubTable_2.setLayout(new BorderLayout(0, 0));
				pnlSubTable_2.setPreferredSize(new java.awt.Dimension(477, 441));
				{
					pnlAgentMainInfo_a = new JPanel(new BorderLayout(0, 0));
					pnlSubTable_2.add(pnlAgentMainInfo_a, BorderLayout.NORTH);
					pnlAgentMainInfo_a.setPreferredSize(new java.awt.Dimension(446, 208));
					pnlAgentMainInfo_a.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
					pnlAgentMainInfo_a.setBorder(components.JTBorderFactory.createTitleBorder("Main Info."));
					{
						pnlAgentMainInfo = new JPanel(new GridLayout(7, 1, 5, 5));
						pnlAgentMainInfo_a.add(pnlAgentMainInfo, BorderLayout.WEST);
						pnlAgentMainInfo.setPreferredSize(new java.awt.Dimension(125, 188));
						pnlAgentMainInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
						{
							{
								lblAgentName = new JLabel("Agent", JLabel.TRAILING);
								pnlAgentMainInfo.add(lblAgentName);
								lblAgentName.setEnabled(true);
								lblAgentName.setFont(font11);
							}
							{
								lblSalesDiv = new JLabel("Bus. Devt. Team", JLabel.TRAILING);
								pnlAgentMainInfo.add(lblSalesDiv);
								lblSalesDiv.setEnabled(true);
								lblSalesDiv.setFont(font11);
							}
							{
								lblSalesDept = new JLabel("Sales Dept.", JLabel.TRAILING);
								pnlAgentMainInfo.add(lblSalesDept);
								lblSalesDept.setEnabled(true);
								lblSalesDept.setText("Bus. Devt. Grp.");
								lblSalesDept.setFont(font11);
							}
							{
								lblSalesType = new JLabel("Sales Type", JLabel.TRAILING);
								pnlAgentMainInfo.add(lblSalesType);
								lblSalesType.setEnabled(true);
								lblSalesType.setFont(font11);
							}
							{
								lblPosition = new JLabel("Position", JLabel.TRAILING);
								pnlAgentMainInfo.add(lblPosition);
								lblPosition.setEnabled(true);
								lblPosition.setFont(font11);
							}
							{
								lblStatus = new JLabel("Status", JLabel.TRAILING);
								pnlAgentMainInfo.add(lblStatus);
								lblStatus.setEnabled(true);
								lblStatus.setFont(font11);
							}
							{
								JLabel lblBranch = new JLabel("Branch", JLabel.TRAILING);
								pnlAgentMainInfo.add(lblBranch);
								lblBranch.setEnabled(true);
								lblBranch.setFont(font11);
							}
						}
					}
					{
						pnlAgentMainInfo2 = new JPanel(new BorderLayout(5, 0));
						pnlAgentMainInfo_a.add(pnlAgentMainInfo2, BorderLayout.CENTER);
						pnlAgentMainInfo2.setPreferredSize(new java.awt.Dimension(203, 118));
						pnlAgentMainInfo2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
						{
							pnlDRFDtl_2a = new JPanel(new GridLayout(7, 1, 0, 5));
							pnlAgentMainInfo2.add(pnlDRFDtl_2a, BorderLayout.WEST);
							pnlDRFDtl_2a.setPreferredSize(new java.awt.Dimension(86, 213));
							pnlDRFDtl_2a.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
							{
								{
									lookupAgentName = new _JLookup(null, "Agent Name", 2, 2);
									pnlDRFDtl_2a.add(lookupAgentName);
									lookupAgentName.setReturnColumn(0);
									lookupAgentName.setEnabled(false);
									lookupAgentName.setFilterName(true);
									lookupAgentName.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												agent_id = (String) data[0];
												agent_name = (String) data[1];
												tagAgentName.setTag(agent_name.trim());
											}
										}
									});
								}
								{
									lookupSalesDiv = new _JLookup(null, "Sales Division", 2, 2);
									pnlDRFDtl_2a.add(lookupSalesDiv);
									lookupSalesDiv.setReturnColumn(0);
									lookupSalesDiv.setEnabled(false);
									lookupSalesDiv.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												div_id = (String) data[0];
												String div_name = (String) data[1];
												tagSalesDiv.setTag(div_name);
												lookupSalesDept.setLookupSQL(getSalesGroup());

												lookupSalesDept.setValue("");
												tagSalesDept.setTag("");

												lookupOverride.setValue("");
												tagOverride.setTag("");

												lookupBDO.setValue("");
												tagBDO.setTag("");

												lookupBDO.setLookupSQL(getBDO(lookupSalesDept.getText()));
												lookupSalesDept.setEnabled(true);
											}
										}
									});
								}
								{
									lookupSalesDept = new _JLookup(null, "Sales Department", 2, 2);
									pnlDRFDtl_2a.add(lookupSalesDept);
									lookupSalesDept.setReturnColumn(0);
									lookupSalesDept.setEnabled(false);
									lookupSalesDept.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												String dept_name = (String) data[1];
												tagSalesDept.setTag(dept_name);

												lookupOverride.setLookupSQL(getOverride(lookupSalesType.getText(),
														lookupPosition.getText(), lookupSalesDept.getText()));
												lookupBDO.setLookupSQL(getBDO(lookupSalesDept.getText()));

												lookupOverride.setValue("");
												tagOverride.setTag("");

												// lookupBDO.setValue("");
												// tagBDO.setTag("");

												Object[] bdo_dtl = getBDO_details((String) data[0]);
												String bdo_id = "";
												String bdo_name = "";
												try {
													bdo_id = bdo_dtl[0].toString();
												} catch (NullPointerException e) {
													bdo_id = "";
												}
												try {
													bdo_name = bdo_dtl[1].toString();
												} catch (NullPointerException e) {
													bdo_name = "";
												}
												lookupBDO.setText(bdo_id);
												lookupBDO.setEnabled(false);
												tagBDO.setTag(bdo_name);

											}
										}
									});
								}
								{
									lookupSalesType = new _JLookup(null, "Sales Type", 2, 2);
									pnlDRFDtl_2a.add(lookupSalesType);
									lookupSalesType.setReturnColumn(0);
									lookupSalesType.setEnabled(false);
									lookupSalesType.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												String type_name = (String) data[1];
												tagSalesType.setTag(type_name);

												lookupOverride.setLookupSQL(getOverride(lookupSalesType.getText(),
														lookupPosition.getText(), lookupSalesDept.getText()));
												lookupPosition
														.setLookupSQL(getSalesPosition(lookupSalesType.getText()));

												lookupPosition.setValue("");
												tagPosition.setTag("");

												if (data[0].toString().equals("001")) {
													lblBrokerNo.setEnabled(false);
													lblLicNo.setEnabled(false);
													lblRealtyName.setEnabled(false);
													txtBrokerNo.setEnabled(false);
													txtRealtyName.setEnabled(false);
													lblBroker.setEnabled(true);
													lookupIH_BrokerID.setEnabled(true);
													txtIHBrokerName.setEnabled(true);
												} else if (data[0].toString().equals("002")) {
													lblBrokerNo.setEnabled(true);
													lblLicNo.setEnabled(true);
													lblRealtyName.setEnabled(true);
													txtBrokerNo.setEnabled(true);
													txtRealtyName.setEnabled(true);
													lblBroker.setEnabled(false);
													lookupIH_BrokerID.setEnabled(false);
													txtIHBrokerName.setEnabled(false);
												}

											}
										}
									});
								}
								{
									lookupPosition = new _JLookup(null, "Position", 2, 2);
									pnlDRFDtl_2a.add(lookupPosition);
									lookupPosition.setReturnColumn(0);
									lookupPosition.setEnabled(false);
									lookupPosition.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												String posn_name = (String) data[1];
												tagPosition.setTag(posn_name);

												lookupSalesType.setLookupSQL(getSalesType());
												lookupStatus.setLookupSQL(getStatus());
												lookupOverride.setLookupSQL(getOverride(lookupSalesType.getText(),
														lookupPosition.getText(), lookupSalesDept.getText()));
												lookupBDO.setLookupSQL(getBDO(lookupSalesDept.getText()));
											}
										}
									});
								}
								{
									lookupStatus = new _JLookup(null, "Status", 2, 2);
									pnlDRFDtl_2a.add(lookupStatus);
									lookupStatus.setReturnColumn(0);
									lookupStatus.setEnabled(false);
									lookupStatus.setPreferredSize(new java.awt.Dimension(157, 22));
									lookupStatus.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												String status_name = (String) data[1];
												tagStatus.setTag(status_name);
											}
										}
									});
								}
								{
									lookupBranch = new _JLookup(null, "Branch", 2, 2);
									pnlDRFDtl_2a.add(lookupBranch);
									lookupBranch.setReturnColumn(0);
									lookupBranch.setEnabled(false);
									lookupBranch.setLookupSQL(
											"select branch_id, branch_alias from mf_office_branch order by branch_id");
									lookupBranch.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												tagBranch.setTag((String) data[1]);
											}
										}
									});
								}
							}
						}
						{
							pnlDRFDtl_2b = new JPanel(new GridLayout(7, 1, 0, 5));
							pnlAgentMainInfo2.add(pnlDRFDtl_2b, BorderLayout.CENTER);
							pnlDRFDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
							pnlDRFDtl_2b.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
							{
								{
									tagAgentName = new _JTagLabel("[ ]");
									pnlDRFDtl_2b.add(tagAgentName);
									tagAgentName.setBounds(209, 27, 700, 22);
									tagAgentName.setEnabled(true);
									tagAgentName.setPreferredSize(new java.awt.Dimension(314, 32));
								}
								{
									tagSalesDiv = new _JTagLabel("[ ]");
									pnlDRFDtl_2b.add(tagSalesDiv);
									tagSalesDiv.setBounds(209, 27, 700, 22);
									tagSalesDiv.setEnabled(true);
									tagSalesDiv.setPreferredSize(new java.awt.Dimension(314, 32));
								}
								{
									tagSalesDept = new _JTagLabel("[ ]");
									pnlDRFDtl_2b.add(tagSalesDept);
									tagSalesDept.setBounds(209, 27, 700, 22);
									tagSalesDept.setEnabled(true);
									tagSalesDept.setPreferredSize(new java.awt.Dimension(314, 32));
								}
								{
									tagSalesType = new _JTagLabel("[ ]");
									pnlDRFDtl_2b.add(tagSalesType);
									tagSalesType.setBounds(209, 27, 700, 22);
									tagSalesType.setEnabled(true);
									tagSalesType.setPreferredSize(new java.awt.Dimension(314, 32));
								}
								{
									tagPosition = new _JTagLabel("[ ]");
									pnlDRFDtl_2b.add(tagPosition);
									tagPosition.setBounds(209, 27, 700, 22);
									tagPosition.setEnabled(true);
									tagPosition.setPreferredSize(new java.awt.Dimension(314, 32));
								}
								{
									tagStatus = new _JTagLabel("[ ]");
									pnlDRFDtl_2b.add(tagStatus);
									tagStatus.setBounds(209, 27, 700, 22);
									tagStatus.setEnabled(true);
									tagStatus.setPreferredSize(new java.awt.Dimension(314, 32));
								}
								{
									pnlDRFDtl_2b_1 = new JPanel(new GridLayout(1, 3, 0, 0));
									pnlDRFDtl_2b.add(pnlDRFDtl_2b_1, BorderLayout.CENTER);
									pnlDRFDtl_2b_1.setPreferredSize(new java.awt.Dimension(140, 118));
									pnlDRFDtl_2b_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
									{
										{
											tagBranch = new _JTagLabel("[ ]");
											pnlDRFDtl_2b_1.add(tagBranch);
											tagBranch.setBounds(209, 27, 700, 22);
											tagBranch.setEnabled(true);
											tagBranch.setPreferredSize(new java.awt.Dimension(314, 32));
										}
										{
											lblAgentRate = new JLabel("Rate  ", JLabel.TRAILING);
											pnlDRFDtl_2b_1.add(lblAgentRate);
											lblAgentRate.setEnabled(true);
										}
										{
											txtRate = new _JXFormattedTextField(JXFormattedTextField.CENTER);
											pnlDRFDtl_2b_1.add(txtRate);
											txtRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRate.setText("0.00");
											txtRate.setEnabled(false);
											txtRate.setBounds(120, 0, 72, 22);
										}
									}
								}
							}
						}

					}
				}
				{
					pnlAgentMainInfo_b = new JPanel(new BorderLayout(0, 0));
					pnlSubTable_2.add(pnlAgentMainInfo_b, BorderLayout.CENTER);
					pnlAgentMainInfo_b.setPreferredSize(new java.awt.Dimension(926, 114));
					pnlAgentMainInfo_b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					tabCenter = new _JTabbedPane();
					pnlAgentMainInfo_b.add(tabCenter, BorderLayout.CENTER);
					tabCenter.setPreferredSize(new java.awt.Dimension(446, 239));

					{

						pnlPersonalInfo = new JPanel(new BorderLayout(5, 5));
						tabCenter.addTab("Personal Info.", null, pnlPersonalInfo, null);
						pnlPersonalInfo.setPreferredSize(new java.awt.Dimension(901, 36));
						pnlPersonalInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

						{
							pnlPersonalInfo_a = new JPanel(new BorderLayout(0, 0));
							pnlPersonalInfo.add(pnlPersonalInfo_a, BorderLayout.NORTH);
							pnlPersonalInfo_a.setPreferredSize(new java.awt.Dimension(534, 194));
							pnlPersonalInfo_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{
								pnlPersonalInfo_a1 = new JPanel(new GridLayout(8, 1, 5, 5));
								pnlPersonalInfo_a.add(pnlPersonalInfo_a1, BorderLayout.WEST);
								pnlPersonalInfo_a1.setPreferredSize(new java.awt.Dimension(126, 194));
								pnlPersonalInfo_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

								{
									lblPI_FirstName = new JLabel("First Name", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblPI_FirstName);
									lblPI_FirstName.setEnabled(true);
								}
								{
									lblPI_MiddleName = new JLabel("Middle Name", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblPI_MiddleName);
									lblPI_MiddleName.setEnabled(true);
								}
								{
									lblPI_LastName = new JLabel("Last Name", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblPI_LastName);
									lblPI_LastName.setEnabled(true);
								}
								{
									lblPI_Type = new JLabel("Type", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblPI_Type);
									lblPI_Type.setEnabled(true);
								}
								{
									lblTIN_no = new JLabel("TIN", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblTIN_no);
									lblTIN_no.setEnabled(true);
								}
								{
									lblSSS = new JLabel("SSS", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblSSS);
									lblSSS.setEnabled(true);
								}
								{
									lblPRC_no = new JLabel("PRC No.", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblPRC_no);
									lblPRC_no.setEnabled(true);
								}
								{
									lblHLURB_no = new JLabel("HLURB Reg. No.", JLabel.TRAILING);
									pnlPersonalInfo_a1.add(lblHLURB_no);
									lblHLURB_no.setEnabled(true);
								}

							}
							{
								pnlPersonalInfo_a2 = new JPanel(new BorderLayout(5, 0));
								pnlPersonalInfo_a.add(pnlPersonalInfo_a2, BorderLayout.CENTER);
								pnlPersonalInfo_a2.setPreferredSize(new java.awt.Dimension(424, 200));
								pnlPersonalInfo_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

								{
									pnlPersonalInfo_a2_sub1 = new JPanel(new GridLayout(8, 1, 0, 5));
									pnlPersonalInfo_a2.add(pnlPersonalInfo_a2_sub1, BorderLayout.WEST);
									pnlPersonalInfo_a2_sub1.setPreferredSize(new java.awt.Dimension(145, 194));
									pnlPersonalInfo_a2_sub1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

									{
										txtPI_firstname = new JXTextField("");
										pnlPersonalInfo_a2_sub1.add(txtPI_firstname);
										txtPI_firstname.setEnabled(false);
										txtPI_firstname.setEditable(true);
										txtPI_firstname.setBounds(120, 25, 300, 22);
										txtPI_firstname.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										txtPI_midname = new JXTextField("");
										pnlPersonalInfo_a2_sub1.add(txtPI_midname);
										txtPI_midname.setEnabled(false);
										txtPI_midname.setEditable(true);
										txtPI_midname.setBounds(120, 25, 300, 22);
										txtPI_midname.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										txtPI_lastname = new JXTextField("");
										pnlPersonalInfo_a2_sub1.add(txtPI_lastname);
										txtPI_lastname.setEnabled(false);
										txtPI_lastname.setEditable(true);
										txtPI_lastname.setBounds(120, 25, 300, 22);
										txtPI_lastname.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										String status[] = { "INDIVIDUAL", "CORPORATE" };
										cmbPI_type = new JComboBox(status);
										pnlPersonalInfo_a2_sub1.add(cmbPI_type);
										cmbPI_type.setSelectedItem(0);
										cmbPI_type.setBounds(537, 15, 160, 21);
										cmbPI_type.setEnabled(false);
										cmbPI_type.setSelectedIndex(0);
										cmbPI_type.setPreferredSize(new java.awt.Dimension(89, 26));
										cmbPI_type.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent evt) {

											}
										});
									}
									{
										txtTIN = new JXTextField("");
										pnlPersonalInfo_a2_sub1.add(txtTIN);
										txtTIN.setEnabled(false);
										txtTIN.setEditable(true);
										txtTIN.setBounds(120, 25, 300, 22);
										txtTIN.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										txtSSS = new JXTextField("");
										pnlPersonalInfo_a2_sub1.add(txtSSS);
										txtSSS.setEnabled(false);
										txtSSS.setEditable(true);
										txtSSS.setBounds(120, 25, 300, 22);
										txtSSS.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										txtPRC_no = new JXTextField("");
										pnlPersonalInfo_a2_sub1.add(txtPRC_no);
										txtPRC_no.setEnabled(false);
										txtPRC_no.setEditable(true);
										txtPRC_no.setBounds(120, 25, 300, 22);
										txtPRC_no.setHorizontalAlignment(JTextField.CENTER);
									}

									{
										txtHLURB_no = new JXTextField("");
										pnlPersonalInfo_a2_sub1.add(txtHLURB_no);
										txtHLURB_no.setEnabled(false);
										txtHLURB_no.setEditable(true);
										txtHLURB_no.setBounds(120, 25, 300, 22);
										txtHLURB_no.setHorizontalAlignment(JTextField.CENTER);
									}
								}
								{
									pnlPersonalInfo_a2_sub2 = new JPanel(new BorderLayout(0, 0));
									pnlPersonalInfo_a2.add(pnlPersonalInfo_a2_sub2, BorderLayout.CENTER);
									pnlPersonalInfo_a2_sub2.setPreferredSize(new java.awt.Dimension(101, 200));
									pnlPersonalInfo_a2_sub2.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
									{
										pnlPersonalInfo_a2_sub2a = new JPanel(new GridLayout(8, 1, 5, 5));
										pnlPersonalInfo_a2_sub2.add(pnlPersonalInfo_a2_sub2a, BorderLayout.WEST);
										pnlPersonalInfo_a2_sub2a.setPreferredSize(new java.awt.Dimension(67, 195));
										pnlPersonalInfo_a2_sub2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblVatable = new JLabel("Vatable?", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblVatable);
											lblVatable.setEnabled(true);
										}
										{
											lblX = new JLabel("Valid Until", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblX);
											lblX.setEnabled(true);
											lblX.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
										}
										{
											lblX = new JLabel("Valid Until", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2a.add(lblX);
											lblX.setEnabled(true);
											lblX.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
										}
									}
									{
										pnlPersonalInfo_a2_sub2b = new JPanel(new GridLayout(8, 1, 5, 5));
										pnlPersonalInfo_a2_sub2.add(pnlPersonalInfo_a2_sub2b, BorderLayout.CENTER);
										pnlPersonalInfo_a2_sub2b.setPreferredSize(new java.awt.Dimension(112, 200));
										pnlPersonalInfo_a2_sub2b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2b.add(lblX);
											lblX.setEnabled(true);
										}

										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2b.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2b.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2b.add(lblX);
											lblX.setEnabled(true);
										}
										{
											lblX = new JLabel("", JLabel.TRAILING);
											pnlPersonalInfo_a2_sub2b.add(lblX);
											lblX.setEnabled(true);
										}
										{
											String status[] = { "YES", "NO" };
											cmbVatable = new JComboBox(status);
											pnlPersonalInfo_a2_sub2b.add(cmbVatable);
											cmbVatable.setSelectedItem(0);
											cmbVatable.setBounds(537, 15, 160, 21);
											cmbVatable.setEnabled(false);
											cmbVatable.setSelectedIndex(0);
											cmbVatable.setPreferredSize(new java.awt.Dimension(89, 26));
											cmbVatable.addItemListener(new ItemListener() {
												public void itemStateChanged(ItemEvent evt) {

												}
											});
										}
										{
											dtePRC_validity = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
											pnlPersonalInfo_a2_sub2b.add(dtePRC_validity);
											dtePRC_validity.setBounds(485, 7, 125, 21);
											dtePRC_validity.setDate(null);
											dtePRC_validity.setEnabled(false);
											dtePRC_validity.setDateFormatString("yyyy-MM-dd");
											((JTextFieldDateEditor) dtePRC_validity.getDateEditor()).setEditable(false);
										}
										{
											dteHLURB_validity = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
											pnlPersonalInfo_a2_sub2b.add(dteHLURB_validity);
											dteHLURB_validity.setBounds(485, 7, 125, 21);
											dteHLURB_validity.setDate(null);
											dteHLURB_validity.setEnabled(false);
											dteHLURB_validity.setDateFormatString("yyyy-MM-dd");
											((JTextFieldDateEditor) dteHLURB_validity.getDateEditor())
													.setEditable(false);
										}
									}
									{
										pnlPersonalInfo_a2_sub2c = new JPanel(new GridLayout(8, 1, 5, 5));
										pnlPersonalInfo_a2_sub2.add(pnlPersonalInfo_a2_sub2c, BorderLayout.EAST);
										pnlPersonalInfo_a2_sub2c.setPreferredSize(new java.awt.Dimension(66, 189));
										pnlPersonalInfo_a2_sub2c.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
									}

								}
							}
						}
						{
							pnlPersonalInfo_b1 = new JPanel(new GridLayout(8, 1, 5, 5));
							pnlPersonalInfo.add(pnlPersonalInfo_b1, BorderLayout.CENTER);
							pnlPersonalInfo_b1.setPreferredSize(new java.awt.Dimension(95, 180));
							pnlPersonalInfo_b1.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
						}
						{
							pnlPersonalInfo_c = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlPersonalInfo.add(pnlPersonalInfo_c, BorderLayout.SOUTH);
							pnlPersonalInfo_c.setPreferredSize(new java.awt.Dimension(494, 28));
							pnlPersonalInfo_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

							{
								btnEditPI = new JButton("Edit Info.");
								pnlPersonalInfo_c.add(btnEditPI);
								btnEditPI.addActionListener(this);
								btnEditPI.setEnabled(false);
								btnEditPI.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (withAccess() == true) {
											btnEditPI.setEnabled(false);
											btnSavePI.setEnabled(true);
											btnCancelPI.setEnabled(true);
											enableMainInfoFields(true);
										} else {
											JOptionPane.showMessageDialog(getContentPane(),
													"Sorry, you are not authorized to edit Agent's Info..",
													"Information", JOptionPane.INFORMATION_MESSAGE);
										}
									}
								});
							}
							{
								btnSavePI = new JButton("Save Info.");
								pnlPersonalInfo_c.add(btnSavePI);
								btnSavePI.addActionListener(this);
								btnSavePI.setEnabled(false);
								btnSavePI.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										saveMainInfo();
									}
								});
							}
							{
								btnCancelPI = new JButton("Cancel Editing");
								pnlPersonalInfo_c.add(btnCancelPI);
								btnCancelPI.setActionCommand("CancelEditing");
								btnCancelPI.addActionListener(this);
								btnCancelPI.setEnabled(false);
								btnCancelPI.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										btnEditPI.setEnabled(true);
										btnSavePI.setEnabled(false);
										btnCancelPI.setEnabled(false);
										enableMainInfoFields(false);
									}
								});
							}
						}
					}
					{

						pnlOverride = new JPanel(new BorderLayout(5, 5));
						tabCenter.addTab("Override/BDO", null, pnlOverride, null);
						pnlOverride.setPreferredSize(new java.awt.Dimension(901, 36));
						pnlOverride.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

						{
							pnlOverride_a = new JPanel(new BorderLayout(0, 0));
							pnlOverride.add(pnlOverride_a, BorderLayout.NORTH);
							pnlOverride_a.setPreferredSize(new java.awt.Dimension(581, 160));
							pnlOverride_a.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

							{
								pnlOverride_a1 = new JPanel(new GridLayout(5, 1, 5, 5));
								pnlOverride_a.add(pnlOverride_a1, BorderLayout.WEST);
								pnlOverride_a1.setPreferredSize(new java.awt.Dimension(125, 121));
								pnlOverride_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

								{
									lblOverrideName = new JLabel("Override", JLabel.TRAILING);
									pnlOverride_a1.add(lblOverrideName);
									lblOverrideName.setEnabled(true);
								}
								{
									lblBDO = new JLabel("BDO", JLabel.TRAILING);
									pnlOverride_a1.add(lblBDO);
									lblBDO.setEnabled(true);
								}
								{
									lblBroker = new JLabel("In-House Broker", JLabel.TRAILING);
									pnlOverride_a1.add(lblBroker);
									lblBroker.setEnabled(true);
								}
								{
									lblX = new JLabel("", JLabel.TRAILING);
									pnlOverride_a1.add(lblX);
									lblX.setVisible(false);
									lblX.setEnabled(true);
								}
								{
									lblBrokerNo = new JLabel("External Broker", JLabel.TRAILING);
									pnlOverride_a1.add(lblBrokerNo);
									lblBrokerNo.setEnabled(true);
									lblBrokerNo.setVisible(false);
								}
							}
							{
								pnlOverride_a2 = new JPanel(new BorderLayout(5, 0));
								pnlOverride_a.add(pnlOverride_a2, BorderLayout.CENTER);
								pnlOverride_a2.setPreferredSize(new java.awt.Dimension(449, 191));
								pnlOverride_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

								{
									pnlOverride_a2_sub1 = new JPanel(new GridLayout(5, 1, 0, 5));
									pnlOverride_a2.add(pnlOverride_a2_sub1, BorderLayout.WEST);
									pnlOverride_a2_sub1.setPreferredSize(new java.awt.Dimension(101, 200));
									pnlOverride_a2_sub1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

									{
										lookupOverride = new _JLookup(null, "Override", 2, 2);
										pnlOverride_a2_sub1.add(lookupOverride);
										lookupOverride.setBounds(20, 27, 20, 25);
										lookupOverride.setReturnColumn(0);
										lookupOverride.setEnabled(false);
										lookupOverride.setFilterName(true);
										lookupOverride.setPreferredSize(new java.awt.Dimension(157, 22));
										lookupOverride.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {

													override_id = data[0].toString();
													String override_name = "";

													try {
														override_name = data[1].toString();
													} catch (NullPointerException e) {
														override_name = "";
													}

													tagOverride.setTag(override_name);
												}
											}
										});
									}
									{
										lookupBDO = new _JLookup(null, "BDO", 2, 2);
										pnlOverride_a2_sub1.add(lookupBDO);
										lookupBDO.setBounds(20, 27, 20, 25);
										lookupBDO.setReturnColumn(0);
										lookupBDO.setEnabled(false);
										lookupBDO.setFilterName(true);
										lookupBDO.setPreferredSize(new java.awt.Dimension(157, 22));
										lookupBDO.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {
													String coord_name = (String) data[1];
													tagBDO.setTag(coord_name);
												}
											}
										});
									}
									{
										lookupIH_BrokerID = new _JLookup(null, "In-House Broker", 2, 2);
										pnlOverride_a2_sub1.add(lookupIH_BrokerID);
										lookupIH_BrokerID.setBounds(20, 27, 20, 25);
										lookupIH_BrokerID.setReturnColumn(0);
										lookupIH_BrokerID.setEnabled(false);
										lookupIH_BrokerID.setFilterName(true);
										lookupIH_BrokerID.setPreferredSize(new java.awt.Dimension(157, 22));
										lookupIH_BrokerID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												if (data != null) {

													String in_house_broker_id = data[0].toString();
													String inhouse_broker_name = "";

													try {
														inhouse_broker_name = data[1].toString();
													} catch (NullPointerException e) {
														inhouse_broker_name = "";
													}

													txtIHBrokerName.setTag(inhouse_broker_name);
												}
											}
										});
									}
									{
										lblLicNo = new JLabel("PRC License No.");
										pnlOverride_a2_sub1.add(lblLicNo);
										lblLicNo.setEnabled(true);
										lblLicNo.setFont(new java.awt.Font("Arial", Font.ITALIC, 10));
										lblLicNo.setVisible(false);
									}
									{
										txtBrokerNo = new JXTextField("");
										pnlOverride_a2_sub1.add(txtBrokerNo);
										txtBrokerNo.setEnabled(false);
										txtBrokerNo.setEditable(true);
										txtBrokerNo.setBounds(120, 25, 300, 22);
										txtBrokerNo.setHorizontalAlignment(JTextField.CENTER);
										txtBrokerNo.setVisible(false);
									}
								}
								{
									pnlOverride_a2_sub2 = new JPanel(new GridLayout(5, 1, 0, 5));
									pnlOverride_a2.add(pnlOverride_a2_sub2, BorderLayout.CENTER);
									pnlOverride_a2_sub2.setPreferredSize(new java.awt.Dimension(140, 118));
									pnlOverride_a2_sub2.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));

									{
										tagOverride = new _JTagLabel("[ ]");
										pnlOverride_a2_sub2.add(tagOverride);
										tagOverride.setBounds(209, 27, 700, 22);
										tagOverride.setEnabled(true);
										tagOverride.setPreferredSize(new java.awt.Dimension(314, 32));
									}
									{
										tagBDO = new _JTagLabel("[ ]");
										pnlOverride_a2_sub2.add(tagBDO);
										tagBDO.setBounds(209, 27, 700, 22);
										tagBDO.setEnabled(true);
										tagBDO.setPreferredSize(new java.awt.Dimension(314, 32));
									}
									{
										txtIHBrokerName = new _JTagLabel("[ ]");
										pnlOverride_a2_sub2.add(txtIHBrokerName);
										txtIHBrokerName.setEnabled(true);
										txtIHBrokerName.setEditable(true);
										txtIHBrokerName.setBounds(120, 25, 300, 22);
										// txtIHBrokerName.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										lblRealtyName = new JLabel("Broker/Brokerage Name");
										pnlOverride_a2_sub2.add(lblRealtyName);
										lblRealtyName.setEnabled(true);
										lblRealtyName.setVisible(false);
										lblRealtyName.setFont(new java.awt.Font("Arial", Font.ITALIC, 10));
									}

									{
										txtRealtyName = new JXTextField("");
										pnlOverride_a2_sub2.add(txtRealtyName);
										txtRealtyName.setEnabled(false);
										txtRealtyName.setVisible(false);
										txtRealtyName.setEditable(true);
										txtRealtyName.setBounds(120, 25, 300, 22);
										txtRealtyName.setHorizontalAlignment(JTextField.CENTER);
										txtRealtyName.setToolTipText("Realty Name");
									}

								}
							}
						}
						{
							pnlOverride_b = new JPanel(new BorderLayout(0, 0));
							pnlOverride.add(pnlOverride_b, BorderLayout.CENTER);
							pnlOverride_b.setPreferredSize(new java.awt.Dimension(534, 200));
							pnlOverride_b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						}
						{
							pnlOverride_c = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlOverride.add(pnlOverride_c, BorderLayout.SOUTH);
							pnlOverride_c.setPreferredSize(new java.awt.Dimension(494, 28));
							pnlOverride_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

							{
								btnEditOverride = new JButton("Edit Override");
								pnlOverride_c.add(btnEditOverride);
								btnEditOverride.setActionCommand("EditOverride");
								btnEditOverride.addActionListener(this);
								btnEditOverride.setEnabled(false);
								btnEditOverride.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (withAccess() == true) {
											String sales_type = lookupSalesType.getText();

											lookupOverride.setEnabled(true);
											txtBrokerNo.setEnabled(true);
											txtRealtyName.setEnabled(true);
											lookupBDO.setEnabled(true);
											lookupIH_BrokerID.setEnabled(true);
											tagBDO.setEnabled(true);

											btnEditOverride.setEnabled(false);
											btnSaveOverride.setEnabled(true);
											btnCancelOverride.setEnabled(true);
											lookupOverride.setLookupSQL(getOverride(sales_type,
													lookupPosition.getText(), lookupSalesDept.getText()));
											lookupBDO.setLookupSQL(getBDO(lookupSalesDept.getText()));
											lookupIH_BrokerID.setLookupSQL(getIn_HouseBrokers());

											if (sales_type.equals("001")) {
												lblBrokerNo.setEnabled(false);
												lblLicNo.setEnabled(false);
												txtBrokerNo.setEnabled(false);
												lblRealtyName.setEnabled(false);
												txtRealtyName.setEnabled(false);
												lblBroker.setEnabled(true);
												lookupIH_BrokerID.setEnabled(true);
												txtIHBrokerName.setEnabled(true);

											} else if (sales_type.equals("002")) {
												lblBrokerNo.setEnabled(true);
												lblLicNo.setEnabled(true);
												txtBrokerNo.setEnabled(true);
												lblRealtyName.setEnabled(true);
												txtRealtyName.setEnabled(true);
												lblBroker.setEnabled(false);
												lookupIH_BrokerID.setEnabled(false);
												txtIHBrokerName.setEnabled(false);
											}
										} else {
											JOptionPane.showMessageDialog(getContentPane(),
													"Sorry, you are not authorized to edit Agent's Info..",
													"Information", JOptionPane.INFORMATION_MESSAGE);
										}
									}
								});
							}
							{
								btnSaveOverride = new JButton("Save Override");
								pnlOverride_c.add(btnSaveOverride);
								btnSaveOverride.setActionCommand("UpdateOverride");
								btnSaveOverride.addActionListener(this);
								btnSaveOverride.setEnabled(false);
								btnSaveOverride.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										saveOverride();
									}
								});
							}
							{
								btnCancelOverride = new JButton("Cancel Editing");
								pnlOverride_c.add(btnCancelOverride);
								btnCancelOverride.setActionCommand("CancelEditing");
								btnCancelOverride.addActionListener(this);
								btnCancelOverride.setEnabled(false);
								btnCancelOverride.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										clickTable();
									}
								});
							}
						}
					}
					{
						tabCenter.addTab("    Accreditation     ", null, accreditation(), null);
					}
					{
						pnlATM_details = new JPanel(new BorderLayout(5, 5));
						tabCenter.addTab("    ATM     ", null, pnlATM_details, null);
						pnlATM_details.setPreferredSize(new java.awt.Dimension(901, 36));
						pnlATM_details.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

						{
							pnlATM_details_a = new JPanel(new BorderLayout(5, 5));
							pnlATM_details.add(pnlATM_details_a, BorderLayout.NORTH);
							pnlATM_details_a.setPreferredSize(new java.awt.Dimension(441, 35));
							pnlATM_details_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{
								pnlATM_details_a1 = new JPanel(new GridLayout(1, 3, 5, 5));
								pnlATM_details_a.add(pnlATM_details_a1, BorderLayout.WEST);
								pnlATM_details_a1.setPreferredSize(new java.awt.Dimension(408, 47));
								pnlATM_details_a1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

								{
									rbtn_with_ATM = new JRadioButton();
									pnlATM_details_a1.add(rbtn_with_ATM);
									rbtn_with_ATM.setText("With ATM");
									rbtn_with_ATM.setBounds(277, 12, 77, 18);
									rbtn_with_ATM.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
									rbtn_with_ATM.setSelected(false);
									rbtn_with_ATM.setEnabled(false);
									rbtn_with_ATM.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent ae) {
											if (rbtn_with_ATM.isSelected() == true) {
												rbtnEndorseATM.setSelected(false);
												rbtn_no_ATM.setSelected(false);
												rbtn_no_ATM.setSelected(false);
											}
										}
									});
								}
								{
									lblX = new JLabel("", JLabel.TRAILING);
									pnlATM_details_a1.add(lblX);
									lblX.setEnabled(false);
								}
								{
									lblX = new JLabel("", JLabel.TRAILING);
									pnlATM_details_a1.add(lblX);
									lblX.setEnabled(false);
								}
							}
							{
								pnlATM_details_a2 = new JPanel(new BorderLayout(0, 0));
								pnlATM_details_a.add(pnlATM_details_a2, BorderLayout.CENTER);
								pnlATM_details_a2.setPreferredSize(new java.awt.Dimension(59, 47));
								pnlATM_details_a2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							}
						}
						{
							pnlATM_details_b = new JPanel(new BorderLayout(0, 0));
							pnlATM_details.add(pnlATM_details_b, BorderLayout.CENTER);
							pnlATM_details_b.setPreferredSize(new java.awt.Dimension(441, 41));
							pnlATM_details_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

							{
								pnlATM_details_b1 = new JPanel(new BorderLayout(0, 0));
								pnlATM_details_b.add(pnlATM_details_b1, BorderLayout.NORTH);
								pnlATM_details_b1.setPreferredSize(new java.awt.Dimension(441, 59));
								pnlATM_details_b1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
								pnlATM_details_b1.setBorder(lineBorder);

								{
									pnlATM_details_b1_sub1 = new JPanel(new GridLayout(1, 2, 5, 5));
									pnlATM_details_b1.add(pnlATM_details_b1_sub1, BorderLayout.WEST);
									pnlATM_details_b1_sub1.setPreferredSize(new java.awt.Dimension(397, 93));
									pnlATM_details_b1_sub1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

									{
										pnlATM_details_b1_sub1a = new JPanel(new BorderLayout(0, 0));
										pnlATM_details_b1_sub1.add(pnlATM_details_b1_sub1a, BorderLayout.WEST);
										pnlATM_details_b1_sub1a.setPreferredSize(new java.awt.Dimension(397, 93));
										pnlATM_details_b1_sub1a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

										{
											pnlATM_x = new JPanel(new GridLayout(2, 1, 5, 5));
											pnlATM_details_b1_sub1a.add(pnlATM_x, BorderLayout.WEST);
											pnlATM_x.setPreferredSize(new java.awt.Dimension(97, 93));
											pnlATM_x.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

											{
												lblATM_no = new JLabel("ATM No. ", JLabel.TRAILING);
												pnlATM_x.add(lblATM_no);
												lblATM_no.setEnabled(true);
											}
											{
												lblATM_acct_no = new JLabel("Bank Acct. No. ", JLabel.TRAILING);
												pnlATM_x.add(lblATM_acct_no);
												lblATM_acct_no.setEnabled(true);
											}
										}
										{
											pnlATM_y = new JPanel(new GridLayout(2, 1, 5, 5));
											pnlATM_details_b1_sub1a.add(pnlATM_y, BorderLayout.CENTER);
											pnlATM_y.setPreferredSize(new java.awt.Dimension(397, 93));
											pnlATM_y.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

											{
												txtATM_No = new JXTextField("");
												pnlATM_y.add(txtATM_No);
												txtATM_No.setEnabled(false);
												txtATM_No.setEditable(true);
												txtATM_No.setBounds(120, 25, 300, 22);
												txtATM_No.setHorizontalAlignment(JTextField.CENTER);
											}
											{
												lookupATMAcctNo = new _JLookup(null, "Payee", 2, 2);
												pnlATM_y.add(lookupATMAcctNo);
												lookupATMAcctNo.setBounds(20, 27, 20, 25);
												lookupATMAcctNo.setReturnColumn(0);
												lookupATMAcctNo.setEnabled(false);
												lookupATMAcctNo.setLookupSQL(getBankAcct());
												lookupATMAcctNo.setPreferredSize(new java.awt.Dimension(157, 22));
											}
										}
									}
									{
										pnlATM_details_b1_sub1b = new JPanel(new GridLayout(2, 1, 5, 5));
										pnlATM_details_b1_sub1.add(pnlATM_details_b1_sub1b, BorderLayout.CENTER);
										pnlATM_details_b1_sub1b.setPreferredSize(new java.awt.Dimension(397, 93));
										pnlATM_details_b1_sub1b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

										pnlATM_m = new JPanel(new BorderLayout(0, 0));
										pnlATM_details_b1_sub1b.add(pnlATM_m, BorderLayout.NORTH);
										pnlATM_m.setPreferredSize(new java.awt.Dimension(397, 93));
										pnlATM_m.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

										{
											pnlATM_x = new JPanel(new GridLayout(1, 1, 5, 5));
											pnlATM_m.add(pnlATM_x, BorderLayout.WEST);
											pnlATM_x.setPreferredSize(new java.awt.Dimension(79, 44));
											pnlATM_x.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

											{
												lblDateReg = new JLabel("Date Reg. ", JLabel.TRAILING);
												pnlATM_x.add(lblDateReg);
												lblDateReg.setEnabled(true);
											}
										}
										{
											pnlATM_y = new JPanel(new GridLayout(1, 1, 5, 5));
											pnlATM_m.add(pnlATM_y, BorderLayout.CENTER);
											pnlATM_y.setPreferredSize(new java.awt.Dimension(397, 93));
											pnlATM_y.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

											{
												dteDateReg = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
												pnlATM_y.add(dteDateReg);
												dteDateReg.setBounds(485, 7, 125, 21);
												dteDateReg.setDate(null);
												dteDateReg.setEnabled(false);
												dteDateReg.setDateFormatString("yyyy-MM-dd");
												((JTextFieldDateEditor) dteDateReg.getDateEditor()).setEditable(false);
											}
										}

										pnlATM_m = new JPanel(new BorderLayout(0, 0));
										pnlATM_details_b1_sub1b.add(pnlATM_m, BorderLayout.CENTER);
										pnlATM_m.setPreferredSize(new java.awt.Dimension(397, 93));
										pnlATM_m.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

										{
											chkRelThruATM = new JCheckBox("Release thru ATM? ");
											pnlATM_m.add(chkRelThruATM);
											chkRelThruATM.setEnabled(false);
											chkRelThruATM.setHorizontalAlignment(JTextField.CENTER);
											chkRelThruATM.setPreferredSize(new java.awt.Dimension(383, 26));
										}
									}
								}
								{
									pnlATM_details_b1_sub2 = new JPanel(new GridLayout(2, 1, 5, 5));
									pnlATM_details_b1.add(pnlATM_details_b1_sub2, BorderLayout.CENTER);
									pnlATM_details_b1_sub2.setPreferredSize(new java.awt.Dimension(441, 93));
									pnlATM_details_b1_sub2.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 0));
									// pnlATM_details_b1_sub2.setBorder(lineBorder);

									{
										rbtnATM_forRelease = new JRadioButton();
										pnlATM_details_b1_sub2.add(rbtnATM_forRelease);
										rbtnATM_forRelease.setText("ATM For Release");
										rbtnATM_forRelease.setBounds(277, 12, 77, 18);
										rbtnATM_forRelease.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
										rbtnATM_forRelease.setSelected(false);
										rbtnATM_forRelease.setEnabled(false);
										rbtnATM_forRelease.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent ae) {
												if (rbtnATM_forRelease.isSelected() == true) {
													rbtnATM_onHand.setSelected(false);
												} else {
													rbtnATM_onHand.setSelected(true);
												}
											}
										});
									}
									{
										rbtnATM_onHand = new JRadioButton();
										pnlATM_details_b1_sub2.add(rbtnATM_onHand);
										rbtnATM_onHand.setText("ATM on Hand");
										rbtnATM_onHand.setBounds(277, 12, 77, 18);
										rbtnATM_onHand.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
										rbtnATM_onHand.setSelected(false);
										rbtnATM_onHand.setEnabled(false);
										rbtnATM_onHand.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent ae) {
												if (rbtnATM_onHand.isSelected() == true) {
													rbtnATM_forRelease.setSelected(false);
												} else {
													rbtnATM_forRelease.setSelected(true);
												}
											}
										});
									}
								}
							}
							{
								pnlATM_details_b2 = new JPanel(new BorderLayout(0, 0));
								pnlATM_details_b.add(pnlATM_details_b2, BorderLayout.CENTER);
								pnlATM_details_b2.setPreferredSize(new java.awt.Dimension(441, 41));
								pnlATM_details_b2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

								{
									pnlATM_details_b2_sub1 = new JPanel(new BorderLayout(0, 0));
									pnlATM_details_b2.add(pnlATM_details_b2_sub1, BorderLayout.NORTH);
									pnlATM_details_b2_sub1.setPreferredSize(new java.awt.Dimension(441, 41));
									pnlATM_details_b2_sub1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{
										pnlATM_details_a = new JPanel(new BorderLayout(5, 5));
										pnlATM_details_b2_sub1.add(pnlATM_details_a, BorderLayout.NORTH);
										pnlATM_details_a.setPreferredSize(new java.awt.Dimension(441, 47));
										pnlATM_details_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

										{

											pnlATM_details_a1 = new JPanel(new GridLayout(1, 3, 5, 5));
											pnlATM_details_a.add(pnlATM_details_a1, BorderLayout.WEST);
											pnlATM_details_a1.setPreferredSize(new java.awt.Dimension(477, 47));
											pnlATM_details_a1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

											{
												rbtnEndorseATM = new JRadioButton();
												pnlATM_details_a1.add(rbtnEndorseATM);
												rbtnEndorseATM.setText("Endorse to FAD for processing");
												rbtnEndorseATM.setBounds(277, 12, 77, 18);
												rbtnEndorseATM.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
												rbtnEndorseATM.setSelected(false);
												rbtnEndorseATM.setEnabled(false);
												rbtnEndorseATM.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent ae) {
														rbtn_with_ATM.setSelected(false);
														rbtn_no_ATM.setSelected(false);
														txtATM_No.setText("");
														lookupATMAcctNo.setText("");
														dteDateReg.setDate(null);
														chkRelThruATM.setSelected(false);
														rbtnATM_forRelease.setSelected(false);
														rbtnATM_onHand.setSelected(false);
													}
												});
											}
											{
												rbtnATM_onProcess = new JRadioButton();
												pnlATM_details_a1.add(rbtnATM_onProcess);
												rbtnATM_onProcess.setText("ATM On Process");
												rbtnATM_onProcess.setBounds(277, 12, 77, 18);
												rbtnATM_onProcess
														.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
												rbtnATM_onProcess.setSelected(false);
												rbtnATM_onProcess.setEnabled(false);
												rbtnATM_onProcess.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent ae) {
														rbtn_with_ATM.setSelected(false);
														rbtn_no_ATM.setSelected(false);
														txtATM_No.setText("");
														lookupATMAcctNo.setText("");
														dteDateReg.setDate(null);
														chkRelThruATM.setSelected(false);
														rbtnATM_forRelease.setSelected(false);
														rbtnATM_onHand.setSelected(false);
													}
												});
											}
										}
										{
											pnlATM_details_a2 = new JPanel(new BorderLayout(0, 0));
											pnlATM_details_a.add(pnlATM_details_a2, BorderLayout.CENTER);
											pnlATM_details_a2.setPreferredSize(new java.awt.Dimension(59, 47));
											pnlATM_details_a2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

										}
									}

									pnlATM_details_b2_sub2 = new JPanel(new BorderLayout(0, 0));
									pnlATM_details_b2.add(pnlATM_details_b2_sub2, BorderLayout.CENTER);
									pnlATM_details_b2_sub2.setPreferredSize(new java.awt.Dimension(441, 41));
									pnlATM_details_b2_sub2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{
										pnlATM_details_a = new JPanel(new BorderLayout(5, 5));
										pnlATM_details_b2_sub2.add(pnlATM_details_a, BorderLayout.NORTH);
										pnlATM_details_a.setPreferredSize(new java.awt.Dimension(441, 47));
										pnlATM_details_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

										{

											pnlATM_details_a1 = new JPanel(new GridLayout(1, 3, 5, 5));
											pnlATM_details_a.add(pnlATM_details_a1, BorderLayout.WEST);
											pnlATM_details_a1.setPreferredSize(new java.awt.Dimension(408, 47));
											pnlATM_details_a1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

											{
												rbtn_no_ATM = new JRadioButton();
												pnlATM_details_a1.add(rbtn_no_ATM);
												rbtn_no_ATM.setText("No ATM");
												rbtn_no_ATM.setBounds(277, 12, 77, 18);
												rbtn_no_ATM.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
												rbtn_no_ATM.setSelected(false);
												rbtn_no_ATM.setEnabled(false);
												rbtn_no_ATM.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent ae) {
														rbtn_with_ATM.setSelected(false);
														rbtnEndorseATM.setSelected(false);
														txtATM_No.setText("");
														lookupATMAcctNo.setText("");
														dteDateReg.setDate(null);
														chkRelThruATM.setSelected(false);
														rbtnATM_forRelease.setSelected(false);
														rbtnATM_onHand.setSelected(false);
													}
												});
											}
											{
												lblX = new JLabel("", JLabel.TRAILING);
												pnlATM_details_a1.add(lblX);
												lblX.setEnabled(false);
											}
											{
												lblX = new JLabel("", JLabel.TRAILING);
												pnlATM_details_a1.add(lblX);
												lblX.setEnabled(false);
											}
										}
										{
											pnlATM_details_a2 = new JPanel(new BorderLayout(0, 0));
											pnlATM_details_a.add(pnlATM_details_a2, BorderLayout.CENTER);
											pnlATM_details_a2.setPreferredSize(new java.awt.Dimension(59, 47));
											pnlATM_details_a2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
										}
									}
								}
							}
						}
						{
							pnlATM_details_c = new JPanel(new GridLayout(1, 1, 5, 5));
							pnlATM_details.add(pnlATM_details_c, BorderLayout.SOUTH);
							pnlATM_details_c.setPreferredSize(new java.awt.Dimension(494, 28));
							pnlATM_details_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

							{
								btnEditATM = new JButton("Edit ATM Info.");
								pnlATM_details_c.add(btnEditATM);
								btnEditATM.setActionCommand("EditATM");
								btnEditATM.addActionListener(this);
								btnEditATM.setEnabled(false);
								btnEditATM.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (withAccess() == true) {
											if (isAgenthasCAwriteoff(agent_id) == true) {
												JOptionPane.showMessageDialog(getContentPane(),
														"Agent has an active Cash Advance write-off. \n"
																+ "Please coordinate with Accounting for reversal, \n"
																+ "before adding new accreditation information.",
														"Information", JOptionPane.INFORMATION_MESSAGE);
											} else {
												if (rbtn_with_ATM.isSelected() == true) {
													lblDateReg.setEnabled(true);
													rbtn_with_ATM.setEnabled(false);
													lblATM_no.setEnabled(true);
													lblATM_acct_no.setEnabled(true);
													txtATM_No.setEnabled(true);
													lookupATMAcctNo.setEnabled(true);
													dteDateReg.setEnabled(true);
													chkRelThruATM.setEnabled(true);
													rbtnEndorseATM.setEnabled(false);
													rbtn_no_ATM.setEnabled(false);
													rbtnATM_forRelease.setEnabled(true);
													rbtnATM_onHand.setEnabled(true);
												} else if (rbtn_no_ATM.isSelected() == true) {
													lblDateReg.setEnabled(false);
													rbtn_with_ATM.setEnabled(false);
													lblATM_no.setEnabled(false);
													lblATM_acct_no.setEnabled(false);
													txtATM_No.setEnabled(false);
													lookupATMAcctNo.setEnabled(false);
													dteDateReg.setEnabled(false);
													chkRelThruATM.setEnabled(false);
													rbtnATM_forRelease.setEnabled(false);
													rbtnATM_onHand.setEnabled(false);
													rbtnEndorseATM.setEnabled(true);
													rbtn_no_ATM.setEnabled(true);
												} else {
													lblDateReg.setEnabled(false);
													rbtn_with_ATM.setEnabled(false);
													lblATM_no.setEnabled(false);
													lblATM_acct_no.setEnabled(false);
													txtATM_No.setEnabled(false);
													lookupATMAcctNo.setEnabled(false);
													dteDateReg.setEnabled(false);
													chkRelThruATM.setEnabled(false);
													rbtnATM_forRelease.setEnabled(false);
													rbtnATM_onHand.setEnabled(false);
													rbtnEndorseATM.setEnabled(false);
													rbtn_no_ATM.setEnabled(false);
												}

												btnEditATM.setEnabled(false);
												btnSaveATM.setEnabled(true);
												btnCancelATM.setEnabled(true);
											}
										} else {
											JOptionPane.showMessageDialog(getContentPane(),
													"Sorry, you are not authorized to edit Agent's Info..",
													"Information", JOptionPane.INFORMATION_MESSAGE);
										}

									}
								});
							}
							{
								btnSaveATM = new JButton("Save ATM Info.");
								pnlATM_details_c.add(btnSaveATM);
								btnSaveATM.setActionCommand("UpdateAccred");
								btnSaveATM.addActionListener(this);
								btnSaveATM.setEnabled(false);
								btnSaveATM.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										saveATM();
									}
								});
							}
							{
								btnCancelATM = new JButton("Cancel Editing");
								pnlATM_details_c.add(btnCancelATM);
								btnCancelATM.setActionCommand("CancelATM");
								btnCancelATM.addActionListener(this);
								btnCancelATM.setEnabled(false);
								btnCancelATM.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										clickTable();
									}
								});
							}
						}
					}
					{
						pnlDownline = new JPanel(new BorderLayout(5, 5));
						tabCenter.addTab("Downline", null, pnlDownline, null);
						pnlDownline.setPreferredSize(new java.awt.Dimension(463, 441));
						pnlDownline.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

						pnlDownline_a = new JPanel(new BorderLayout(0, 0));
						pnlDownline.add(pnlDownline_a, BorderLayout.CENTER);
						pnlDownline_a.setPreferredSize(new java.awt.Dimension(446, 208));
						pnlDownline_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

						{
							{
								scrollDownline = new _JScrollPaneMain();
								pnlDownline_a.add(scrollDownline, BorderLayout.CENTER);
								{
									modelDownline = new modelSalesAgentDownline();

									tblDownline = new _JTableMain(modelDownline);
									scrollDownline.setViewportView(tblDownline);
									tblDownline.addMouseListener(this);
									tblDownline.setSortable(false);
									tblDownline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblDownline.getColumnModel().getColumn(0).setPreferredWidth(80);
									tblDownline.getColumnModel().getColumn(1).setPreferredWidth(80);
									tblDownline.getColumnModel().getColumn(2).setPreferredWidth(250);
									tblDownline.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent evt) {
											btnEditAgentRate.setEnabled(true);
										}

										public void keyPressed(KeyEvent e) {
											btnEditAgentRate.setEnabled(true);
										}

									});
									tblDownline.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if (tblDownline.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblDownline.setCellSelectionEnabled(true);
											}
											table = "edit_downline";
											btnEditAgentRate.setEnabled(true);
										}

										public void mouseReleased(MouseEvent e) {
											if (tblDownline.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblDownline.setCellSelectionEnabled(true);
											}
											table = "edit_downline";
											btnEditAgentRate.setEnabled(true);
										}
									});

								}
								{
									rowHeaderDownline = tblDownline.getRowHeader();
									scrollDownline.setRowHeaderView(rowHeaderDownline);
									scrollDownline.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
								{
									scrollDownline_total = new _JScrollPaneTotal(scrollDownline);
									pnlDownline_a.add(scrollDownline_total, BorderLayout.SOUTH);
									{
										modelDownline_total = new modelSalesAgentDownline();
										modelDownline_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

										tblDownline_total = new _JTableTotal(modelDownline_total, tblDownline);
										tblDownline_total.setFont(dialog11Bold);
										scrollDownline_total.setViewportView(tblDownline_total);
										((_JTableTotal) tblDownline_total).setTotalLabel(0);
									}
								}
							}
						}

						pnlDownline_b = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlDownline.add(pnlDownline_b, BorderLayout.SOUTH);
						pnlDownline_b.setPreferredSize(new java.awt.Dimension(494, 28));
						pnlDownline_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							btnAddNewDownline = new JButton("Add New Downline");
							pnlDownline_b.add(btnAddNewDownline);
							btnAddNewDownline.setActionCommand("AddNewDownline");
							btnAddNewDownline.addActionListener(this);
							btnAddNewDownline.setEnabled(false);
							btnAddNewDownline.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										lookupDownline.setLookupSQL(getAgentDownline());
										lookupDownline.setLookupSQL(getAgentDownline());
										lblDownlineID.setText("Agent ID");
										lblDownlineName.setText("Agent Name");
										btnSaveAgentDownline.setText("Save Agent");

										int scanOption = JOptionPane.showOptionDialog(getContentPane(),
												pnlAddNewDownline, "Add New Downline", JOptionPane.PLAIN_MESSAGE,
												JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

										if (scanOption == JOptionPane.CLOSED_OPTION) {
											try {

											} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
											}
										} // CLOSED_OPTION
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
						{
							btnRemoveDownline = new JButton("Remove Downline");
							pnlDownline_b.add(btnRemoveDownline);
							btnRemoveDownline.addActionListener(this);
							btnRemoveDownline.setEnabled(false);
							btnRemoveDownline.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										int x = tblDownline.getSelectedRow();
										try {
											x = tblDownline.getSelectedRow();
										} catch (NullPointerException ev) {
											x = -1;
										}

										if (x < 0) {
											JOptionPane.showMessageDialog(getContentPane(),
													"Select row from the table first.", "ERROR",
													JOptionPane.ERROR_MESSAGE);
										}
										{
											removeDownline(tblDownline.getValueAt(x, 0).toString().trim());
										}
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
						{
							btnEditAgentRate = new JButton("Edit Downline's Rate");
							pnlDownline_b.add(btnEditAgentRate);
							btnEditAgentRate.setActionCommand("EditDownlineRate");
							btnEditAgentRate.addActionListener(this);
							btnEditAgentRate.setEnabled(false);
							btnEditAgentRate.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										// lookupDownline.setLookupSQL(getAgentDownline());
										// lookupDownline.setLookupSQL(getAgentDownline());
										// lblDownlineID.setText("Agent ID");
										// lblDownlineName.setText("Agent Name");
										// btnSaveAgentDownline.setText("Save Agent");

										int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewRate,
												"Edit Downline's Rate", JOptionPane.PLAIN_MESSAGE,
												JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

										if (scanOption == JOptionPane.CLOSED_OPTION) {
											try {

											} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
											}
										} // CLOSED_OPTION
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
					}
					{
						pnlDocuments = new JPanel(new BorderLayout(5, 5));
						tabCenter.addTab("Documents", null, pnlDocuments, null);
						pnlDocuments.setPreferredSize(new java.awt.Dimension(463, 441));
						pnlDocuments.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

						pnlDocuments_north = new JPanel(new BorderLayout(0, 0));
						pnlDocuments.add(pnlDocuments_north, BorderLayout.NORTH);
						pnlDocuments_north.setPreferredSize(new java.awt.Dimension(581, 34));
						pnlDocuments_north.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						{
							{
								chkContractIn = new JCheckBox("Accreditation Contract In? ");
								pnlDocuments_north.add(chkContractIn);
								chkContractIn.setEnabled(false);
								chkContractIn.setForeground(null);
								chkContractIn.setHorizontalAlignment(JTextField.CENTER);
								chkContractIn.setPreferredSize(new java.awt.Dimension(581, 26));
							}
						}

						pnlDocuments_a = new JPanel(new BorderLayout(0, 0));
						pnlDocuments.add(pnlDocuments_a, BorderLayout.CENTER);
						pnlDocuments_a.setPreferredSize(new java.awt.Dimension(446, 208));
						pnlDocuments_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						{
							{
								scrollDocuments = new _JScrollPaneMain();
								pnlDocuments_a.add(scrollDocuments, BorderLayout.CENTER);
								{
									modelDocuments = new modelSalesAgentSubmittedDocs();

									tblDocuments = new _JTableMain(modelDocuments);
									scrollDocuments.setViewportView(tblDocuments);
									tblDocuments.addMouseListener(this);
									tblDocuments.setSortable(false);
									modelDocuments.setEditable(false);
									tblDocuments.setEditable(false);
									tblDocuments.getColumnModel().getColumn(0).setPreferredWidth(80);
									tblDocuments.getColumnModel().getColumn(1).setPreferredWidth(80);
									tblDocuments.getColumnModel().getColumn(2).setPreferredWidth(250);
									tblDocuments.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent evt) {
										}

										public void keyPressed(KeyEvent e) {
										}

									});
									tblDocuments.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if (tblDocuments.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblDocuments.setCellSelectionEnabled(true);
											}
											table = "edit_docs";
										}

										public void mouseReleased(MouseEvent e) {
											if (tblDocuments.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblDocuments.setCellSelectionEnabled(true);
											}
											table = "edit_docs";
										}
									});

								}
								{
									rowHeaderDocuments = tblDocuments.getRowHeader();
									scrollDocuments.setRowHeaderView(rowHeaderDocuments);
									scrollDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
								{
									scrollDocuments_total = new _JScrollPaneTotal(scrollDocuments);
									pnlDocuments_a.add(scrollDocuments_total, BorderLayout.SOUTH);
									{
										modelDocuments_total = new modelSalesAgentSubmittedDocs();
										modelDocuments_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

										tblDocuments_total = new _JTableTotal(modelDocuments_total, tblDocuments);
										tblDocuments_total.setFont(dialog11Bold);
										scrollDocuments_total.setViewportView(tblDocuments_total);
										((_JTableTotal) tblDocuments_total).setTotalLabel(0);
									}
								}
							}
						}

						pnlDocuments_b = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlDocuments.add(pnlDocuments_b, BorderLayout.SOUTH);
						pnlDocuments_b.setPreferredSize(new java.awt.Dimension(494, 28));
						pnlDocuments_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							btnAddNewDocument = new JButton("Add New Document");
							pnlDocuments_b.add(btnAddNewDocument);
							btnAddNewDocument.setActionCommand("AddNewDocument");
							btnAddNewDocument.addActionListener(this);
							btnAddNewDocument.setEnabled(false);
							btnAddNewDocument.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										lookupDoc.setLookupSQL(getDocuments());

										int scanOption = JOptionPane.showOptionDialog(getContentPane(),
												pnlAddNewDocument, "Add Agent Doc.", JOptionPane.PLAIN_MESSAGE,
												JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

										if (scanOption == JOptionPane.CLOSED_OPTION) {
											try {

											} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
											}
										} // CLOSED_OPTION
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
						{
							btnEditDocument = new JButton("Edit Document");
							pnlDocuments_b.add(btnEditDocument);
							btnEditDocument.setActionCommand("EditDocument");
							btnEditDocument.addActionListener(this);
							btnEditDocument.setEnabled(false);
							btnEditDocument.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										btnAddNewDocument.setEnabled(false);
										btnEditDocument.setEnabled(false);
										btnSaveDocument.setEnabled(true);
										btnCancelDocument.setEnabled(true);
										modelDocuments.setEditable(true);
										tblDocuments.setEditable(true);
										table = "edit_doc";
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
						{
							btnSaveDocument = new JButton("Save Document");
							pnlDocuments_b.add(btnSaveDocument);
							btnSaveDocument.setActionCommand("UpdateDocument");
							btnSaveDocument.addActionListener(this);
							btnSaveDocument.setEnabled(false);
							btnSaveDocument.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									updateAgentDoc();
								}
							});
						}
						{
							btnCancelDocument = new JButton("Cancel Editing");
							pnlDocuments_b.add(btnCancelDocument);
							btnCancelDocument.setActionCommand("CancelDocument");
							btnCancelDocument.addActionListener(this);
							btnCancelDocument.setEnabled(false);
							btnCancelDocument.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									btnAddNewDocument.setEnabled(true);
									btnEditDocument.setEnabled(true);
									btnSaveDocument.setEnabled(false);
									btnCancelDocument.setEnabled(false);
									modelDocuments.setEditable(false);
									tblDocuments.setEditable(false);
								}
							});
						}
					}
					{
						pnlBrokers = new JPanel(new BorderLayout(5, 5));
						tabCenter.addTab("Brokers", null, pnlBrokers, null);
						pnlBrokers.setPreferredSize(new java.awt.Dimension(463, 441));
						pnlBrokers.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

						pnlBrokers_a = new JPanel(new BorderLayout(0, 0));
						pnlBrokers.add(pnlBrokers_a, BorderLayout.CENTER);
						pnlBrokers_a.setPreferredSize(new java.awt.Dimension(446, 208));
						pnlBrokers_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

						{
							{
								scrollBroker = new _JScrollPaneMain();
								pnlBrokers_a.add(scrollBroker, BorderLayout.CENTER);
								{
									modelBroker = new modelBrokers();

									tblBroker = new _JTableMain(modelBroker);
									scrollBroker.setViewportView(tblBroker);
									tblBroker.addMouseListener(this);
									tblBroker.setSortable(false);
									tblBroker.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblBroker.getColumnModel().getColumn(0).setPreferredWidth(250);
									tblBroker.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent evt) {
										}

										public void keyPressed(KeyEvent e) {
										}

									});
									tblBroker.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if (tblBroker.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblBroker.setCellSelectionEnabled(true);
											}
											table = "edit_broker";
											btnEditBroker.setEnabled(true);
											btnRemoveBroker.setEnabled(true);
										}

										public void mouseReleased(MouseEvent e) {
											if (tblBroker.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblBroker.setCellSelectionEnabled(true);
											}
											table = "edit_broker";
											btnEditBroker.setEnabled(true);
											btnRemoveBroker.setEnabled(true);
										}
									});

								}
								{
									rowHeaderBroker = tblBroker.getRowHeader();
									scrollBroker.setRowHeaderView(rowHeaderBroker);
									scrollBroker.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
								{
									scrollBroker_total = new _JScrollPaneTotal(scrollBroker);
									pnlBrokers_a.add(scrollBroker_total, BorderLayout.SOUTH);
									{
										modelBroker_total = new modelBrokers();
										modelBroker_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

										tblBroker_total = new _JTableTotal(modelBroker_total, tblBroker);
										tblBroker_total.setFont(dialog11Bold);
										scrollBroker_total.setViewportView(tblBroker_total);
										((_JTableTotal) tblBroker_total).setTotalLabel(0);
									}
								}
							}
						}

						pnlBrokers_b = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlBrokers.add(pnlBrokers_b, BorderLayout.SOUTH);
						pnlBrokers_b.setPreferredSize(new java.awt.Dimension(494, 28));
						pnlBrokers_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							btnAddNewBroker = new JButton("Add New Broker");
							pnlBrokers_b.add(btnAddNewBroker);
							btnAddNewBroker.setActionCommand("AddNewBroker");
							btnAddNewBroker.addActionListener(this);
							btnAddNewBroker.setEnabled(false);
							btnAddNewBroker.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										broker_name = "";
										lookupDownline.setLookupSQL(getBroker());
										lblDownlineID.setText("Broker ID");
										lblDownlineName.setText("Broker Name");
										btnSaveAgentDownline.setText("Save Broker");

										int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBroker,
												"Add New Broker", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,
												null, new Object[] {}, null);

										if (scanOption == JOptionPane.CLOSED_OPTION) {
											try {

											} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
											}
										} // CLOSED_OPTION
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to add Broker..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
						{
							btnEditBroker = new JButton("Edit Broker");
							pnlBrokers_b.add(btnEditBroker);
							btnEditBroker.addActionListener(this);
							btnEditBroker.setEnabled(false);
							btnEditBroker.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {

										int rw = tblBroker.getSelectedRow();
										broker_name = modelBroker.getValueAt(rw, 0).toString();
										String broker_prc_no = modelBroker.getValueAt(rw, 1).toString();
										String broker_hlurb_no = modelBroker.getValueAt(rw, 3).toString();
										Date broker_prc_valid = null;
										Date broker_hlurb_valid = null;

										txtBrokerName.setText(broker_name);
										txtBrokerPRC_no.setText(broker_prc_no);
										txtPRCHLURB_no.setText(broker_hlurb_no);
										btnSaveAgentDownline.setText("Save Broker");
										if (modelBroker.getValueAt(rw, 2) == null) {
										} else {
											broker_prc_valid = (Date) modelBroker.getValueAt(rw, 2);
										}
										dtePRC_Validity.setDate(broker_prc_valid);
										if (modelBroker.getValueAt(rw, 4) == null) {
										} else {
											broker_hlurb_valid = (Date) modelBroker.getValueAt(rw, 4);
										}
										dteHLURB_Validity.setDate(broker_hlurb_valid);

										Boolean isTrue = false;
										if (modelBroker.getValueAt(rw, 8) instanceof String) {
											isTrue = new Boolean((String) modelBroker.getValueAt(rw, 8));
										}
										if (modelBroker.getValueAt(rw, 8) instanceof Boolean) {
											isTrue = (Boolean) modelBroker.getValueAt(rw, 8);
										}
										chkCheckPayee.setSelected(isTrue);

										int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBroker,
												"Edit Broker", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,
												null, new Object[] {}, null);

										if (scanOption == JOptionPane.CLOSED_OPTION) {
											try {

											} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
											}
										} // CLOSED_OPTION
									}

									else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
						{
							btnRemoveBroker = new JButton("Remove Broker");
							pnlBrokers_b.add(btnRemoveBroker);
							btnRemoveBroker.addActionListener(this);
							btnRemoveBroker.setEnabled(false);
							btnRemoveBroker.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									int rw = tblBroker.getSelectedRow();
									broker_name = modelBroker.getValueAt(rw, 0).toString();
									removeBroker();
								}
							});
						}
					}
					{
						pnlAgents = new JPanel(new BorderLayout(5, 5));
						tabCenter.addTab("Agents", null, pnlAgents, null);
						pnlAgents.setPreferredSize(new java.awt.Dimension(463, 441));
						pnlAgents.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

						pnlAgents_a = new JPanel(new BorderLayout(0, 0));
						pnlAgents.add(pnlAgents_a, BorderLayout.CENTER);
						pnlAgents_a.setPreferredSize(new java.awt.Dimension(446, 208));
						pnlAgents_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

						{
							{
								scrollAgents = new _JScrollPaneMain();
								pnlAgents_a.add(scrollAgents, BorderLayout.CENTER);
								{
									modelAgents = new modelSalesAgentDownline();

									tblAgents = new _JTableMain(modelAgents);
									scrollAgents.setViewportView(tblAgents);
									tblAgents.addMouseListener(this);
									tblAgents.setSortable(false);
									tblAgents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tblAgents.getColumnModel().getColumn(0).setPreferredWidth(80);
									tblAgents.getColumnModel().getColumn(1).setPreferredWidth(80);
									tblAgents.getColumnModel().getColumn(2).setPreferredWidth(250);
									tblAgents.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent evt) {
										}

										public void keyPressed(KeyEvent e) {
										}

									});
									tblAgents.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if (tblAgents.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblAgents.setCellSelectionEnabled(true);
											}
											btnRemoveAgent.setEnabled(true);
										}

										public void mouseReleased(MouseEvent e) {
											if (tblAgents.rowAtPoint(e.getPoint()) == -1) {
											} else {
												tblAgents.setCellSelectionEnabled(true);
											}
											btnRemoveAgent.setEnabled(true);
										}
									});

								}
								{
									rowHeaderAgents = tblAgents.getRowHeader();
									scrollAgents.setRowHeaderView(rowHeaderAgents);
									scrollAgents.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
								{
									scrollAgents_total = new _JScrollPaneTotal(scrollAgents);
									pnlAgents_a.add(scrollAgents_total, BorderLayout.SOUTH);
									{
										modelAgents_total = new modelSalesAgentDownline();
										modelAgents_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

										tblAgent_total = new _JTableTotal(modelAgents_total, tblAgents);
										tblAgent_total.setFont(dialog11Bold);
										scrollAgents_total.setViewportView(tblAgent_total);
										((_JTableTotal) tblAgent_total).setTotalLabel(0);
									}
								}
							}
						}

						pnlAgents_b = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlAgents.add(pnlAgents_b, BorderLayout.SOUTH);
						pnlAgents_b.setPreferredSize(new java.awt.Dimension(494, 28));
						pnlAgents_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							btnAddNewAgent = new JButton("Add New Agent");
							pnlAgents_b.add(btnAddNewAgent);
							btnAddNewAgent.setActionCommand("AddNewAgent");
							btnAddNewAgent.addActionListener(this);
							btnAddNewAgent.setEnabled(false);
							btnAddNewAgent.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										lookupDownline.setLookupSQL(getAgent());
										lblDownlineID.setText("Agent ID");
										lblDownlineName.setText("Agent Name");
										btnSaveAgentDownline.setText("Save Agent");

										int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewAgent,
												"Add New Agent", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,
												null, new Object[] {}, null);

										if (scanOption == JOptionPane.CLOSED_OPTION) {
											try {

											} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
											}
										} 
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
						{
							btnRemoveAgent = new JButton("Remove Agent");
							pnlAgents_b.add(btnRemoveAgent);
							btnRemoveAgent.addActionListener(this);
							btnRemoveAgent.setEnabled(false);
							btnRemoveAgent.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (withAccess() == true) {
										int rw = tblAgents.getSelectedRow();
										String agent = modelAgents.getValueAt(rw, 0).toString();
										removeAgent(agent);
									} else {
										JOptionPane.showMessageDialog(getContentPane(),
												"Sorry, you are not authorized to edit Agent's Info..", "Information",
												JOptionPane.INFORMATION_MESSAGE);
									}

								}
							});
						}
					}
				}
			}
		}
	}

	private void ShowList_table(boolean x) {

		{
			pnlSubTable_1 = new JPanel(new GridLayout(1, 1, 0, 0));
			splitPanel.add(pnlSubTable_1, JSplitPane.LEFT);
			// pnlSubTable.add(pnlSubTable_1, BorderLayout.WEST);
			pnlSubTable_1.setLayout(new BorderLayout(0, 0));
			pnlSubTable_1.setVisible(x);
			pnlSubTable_1.setPreferredSize(new java.awt.Dimension(488, 492));
			pnlSubTable_1.setBorder(lineBorder);

			{
				pnlDept = new JPanel();
				pnlSubTable_1.add(pnlDept, BorderLayout.NORTH);
				pnlDept.setLayout(new BorderLayout(0, 0));
				pnlDept.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
				pnlDept.setPreferredSize(new java.awt.Dimension(476, 42));

				{
					pnlDept_a = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlDept.add(pnlDept_a, BorderLayout.WEST);
					pnlDept_a.setPreferredSize(new java.awt.Dimension(105, 37));
					pnlDept_a.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

					{
						lblDepartment = new JLabel("Sales Div. :", JLabel.TRAILING);
						pnlDept_a.add(lblDepartment);
						lblDepartment.setEnabled(true);
						lblDepartment.setPreferredSize(new java.awt.Dimension(92, 25));
						lblDepartment.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
					}

					pnlDept_b = new JPanel(new BorderLayout(0, 5));
					pnlDept.add(pnlDept_b, BorderLayout.CENTER);
					pnlDept_b.setPreferredSize(new java.awt.Dimension(639, 41));
					pnlDept_b.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 0));

					{
						lookupDepartment = new _JLookup(null, "Company", 0, 2);
						pnlDept_b.add(lookupDepartment);
						lookupDepartment.setEnabled(true);
						lookupDepartment.setReturnColumn(0);
						lookupDepartment.setLookupSQL(getDivisionMain());
						lookupDepartment.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									// refresh_tablesMain();

									div_id = (String) data[0];
									String div = (String) data[1];
									tagDepartment.setTag(div);
									enableMainButtons(true, false, false, true);
									refreshOthers();
									displayAgentList(modelAgentList, rowHeaderAgentList, modelAgentList_total, div_id);
								}
							}

						});
					}

					pnlDept_c = new JPanel(new BorderLayout(5, 5));
					pnlDept.add(pnlDept_c, BorderLayout.EAST);
					pnlDept_c.setPreferredSize(new java.awt.Dimension(224, 37));
					pnlDept_c.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 5));

					{
						tagDepartment = new _JTagLabel("[ ]");
						pnlDept_c.add(tagDepartment);
						tagDepartment.setBounds(209, 27, 700, 22);
						tagDepartment.setEnabled(true);
						tagDepartment.setPreferredSize(new java.awt.Dimension(206, 24));
					}
				}
			}
			{

				pnlAgentList = new JPanel();
				pnlSubTable_1.add(pnlAgentList, BorderLayout.CENTER);
				pnlAgentList.setLayout(new BorderLayout(0, 0));
				pnlAgentList.setBorder(lineBorder);
				pnlAgentList.setPreferredSize(new java.awt.Dimension(484, 441));
				pnlAgentList.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

				// start of Commission Schedule (by client)
				{

					{
						scrollAgentList = new _JScrollPaneMain();
						pnlAgentList.add(scrollAgentList, BorderLayout.CENTER);
						{
							modelAgentList = new modelComm_addAgent_list();

							tblAgentList = new _JTableMain(modelAgentList);
							scrollAgentList.setViewportView(tblAgentList);
							// tblAgentList.addMouseListener(this);
							tblAgentList.setSortable(false);
							tblAgentList.getColumnModel().getColumn(0).setPreferredWidth(80);
							tblAgentList.getColumnModel().getColumn(1).setPreferredWidth(80);
							tblAgentList.getColumnModel().getColumn(2).setPreferredWidth(250);
							tblAgentList.addMouseListener(new PopupTriggerListener_panel());
							/*
							 * tblAgentList.addKeyListener(new KeyAdapter() { public void
							 * keyReleased(KeyEvent evt) {clickTable();} public void keyPressed(KeyEvent e)
							 * {clickTable();}
							 * 
							 * }); tblAgentList.addMouseListener(new MouseAdapter() { public void
							 * mousePressed(MouseEvent e) { if(tblAgentList.rowAtPoint(e.getPoint()) ==
							 * -1){} else{tblAgentList.setCellSelectionEnabled(true);} clickTable();
							 * 
							 * System.out.printf("Display Row Point Pressed: %s%n",
							 * tblAgentList.rowAtPoint(e.getPoint())); System.out.println("Mouse Pressed");
							 * } public void mouseReleased(MouseEvent e) {
							 * if(tblAgentList.rowAtPoint(e.getPoint()) == -1){}
							 * else{tblAgentList.setCellSelectionEnabled(true);} clickTable();
							 * System.out.printf("Display Row Point Released: %s%n",
							 * tblAgentList.rowAtPoint(e.getPoint())); System.out.println("Mouse Released");
							 * } });
							 */
							tblAgentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if (!e.getValueIsAdjusting()) {

										if (tblAgentList.getSelectedRows().length != 0) {
											clickTable();
										}
									}
								}
							});

						}
						{
							rowHeaderAgentList = tblAgentList.getRowHeader();
							scrollAgentList.setRowHeaderView(rowHeaderAgentList);
							scrollAgentList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
						{
							scrollAgentList_total = new _JScrollPaneTotal(scrollAgentList);
							pnlAgentList.add(scrollAgentList_total, BorderLayout.SOUTH);
							{
								modelAgentList_total = new modelComm_addAgent_list();
								modelAgentList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

								tblAgentList_total = new _JTableTotal(modelAgentList_total, tblAgentList);
								tblAgentList_total.setFont(dialog11Bold);
								scrollAgentList_total.setViewportView(tblAgentList_total);
								((_JTableTotal) tblAgentList_total).setTotalLabel(0);
							}
						}
					}
				}
				// end
			}
		}
	}

	// check and validate
	private Boolean checkCompleteDetails() {

		boolean x = false;

		proj_id = lookupRateProject.getText();
		phase = lookupRatePhase.getText();
		agent_id = txtRateAgentID.getText();

		if (proj_id.equals("") || phase.equals("") || agent_id.equals("")) {
			x = false;
		} else {
			x = true;
		}

		return x;
	}

	private Boolean checkAgentRate() {

		boolean go = true;
		int row = modelAddRate.getRowCount();
		int x = 0;
		Double rate = 0.00;

		while (x < row) {

			Boolean tagged = (Boolean) tblAddRate.getValueAt(x, 0);

			if (tagged == true) {

				try {
					rate = Double.parseDouble(modelAddRate.getValueAt(x, 3).toString());
				} catch (NullPointerException e) {
					rate = 0.00;
				}

				if (rate == 0.00) {
					go = false;
					break;
				}

				else {
				}
			}

			x++;
		}

		return go;

	}

	private Boolean checkAgentEffectDate() {

		boolean go = true;
		int row = modelAddRate.getRowCount();
		int x = 0;

		while (x < row) {

			Boolean tagged = (Boolean) tblAddRate.getValueAt(x, 0);

			if (tagged == true) {

				if (tblAddRate.getValueAt(x, 4) == null) {
					go = false;
					break;
				}

			}

			else {
			}

			x++;
		}

		return go;

	}

	private Boolean checkAgentRate_edit() {

		boolean go = true;
		int row = modelRate.getRowCount();
		int x = 0;
		Double rate = 0.00;

		while (x < row) {

			try {
				rate = Double.parseDouble(modelRate.getValueAt(x, 4).toString());
			} catch (NullPointerException e) {
				rate = 0.00;
			}

			if (rate == 0.00 || rate > 7.00 || rate < 0.00) {
				go = false;
				break;
			}

			else {
			}

			x++;
		}

		return go;

	}

	private Boolean withAccess() {

		Boolean withAccess = true;

		if (UserInfo.EmployeeCode.equals("900028") || UserInfo.EmployeeCode.equals("900449")
				|| UserInfo.EmployeeCode.equals("900383") || UserInfo.EmployeeCode.equals("900965")
				|| UserInfo.EmployeeCode.equals("900606") || UserInfo.EmployeeCode.equals("900748") || UserInfo.ADMIN) {
			withAccess = true;
		} else {
			withAccess = false;
		}

		return withAccess;
	}

	private void validateMainInfoCompleteness() {

		if (lookupAgentName.getText().equals("")) {
			JOptionPane.showMessageDialog(getContentPane(), "Please select agent/entity.", "Incomplete Detail",
					JOptionPane.WARNING_MESSAGE);
		} else {

			if (lookupSalesDiv.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select agent's current sales division.",
						"Incomplete Detail", JOptionPane.WARNING_MESSAGE);
			} else {

				if (lookupSalesDept.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select agent's current sales group/dept.",
							"Incomplete Detail", JOptionPane.WARNING_MESSAGE);
				} else {

					if (lookupSalesType.getText().equals("")) {
						JOptionPane.showMessageDialog(getContentPane(), "Please select agent's current sales type.",
								"Incomplete Detail", JOptionPane.WARNING_MESSAGE);
					} else {

						if (lookupPosition.getText().equals("")) {
							JOptionPane.showMessageDialog(getContentPane(),
									"Please select agent's current sales position.", "Incomplete Detail",
									JOptionPane.WARNING_MESSAGE);
						} else {

							if (lookupStatus.getText().equals("")) {
								JOptionPane.showMessageDialog(getContentPane(), "Please select agent's current status.",
										"Incomplete Detail", JOptionPane.WARNING_MESSAGE);
							} else {

								if (dteAccredFrom.getDate() == null) {
									JOptionPane.showMessageDialog(getContentPane(),
											"Please select agent's accreditation start date.", "Incomplete Detail",
											JOptionPane.WARNING_MESSAGE);
								} else {

									if (dteAccredTo.getDate() == null) {
										JOptionPane.showMessageDialog(getContentPane(),
												"Please select agent's accreditation end date.", "Incomplete Detail",
												JOptionPane.WARNING_MESSAGE);
									} else {
										if (to_do.equals("addnew")) {
											saveIndEntity_agentBasicInfo();
										} else if (to_do.equals("edit")) {
											update_agentBasicInfo();
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}

	// save and insert
	public void insertAgentRate(pgUpdate db) {

		int rw = tblAddRate.getModel().getRowCount();
		int x = 0;
		int y = 1;
		int last_rec_id = sql_getLastRecID();
		int next_rec_id = 0;

		while (x < rw) {

			Boolean tagged = (Boolean) tblAddRate.getValueAt(x, 0);
			String hse_model_id = tblAddRate.getValueAt(x, 1).toString().trim();
			Double rate = Double.parseDouble(tblAddRate.getValueAt(x, 3).toString());
			String status_id = tblAddRate.getValueAt(x, 5).toString().trim();
			String effect_date = "";
			if (tblAddRate.getValueAt(x, 4) == null) {
			} else {
				effect_date = tblAddRate.getValueAt(x, 4).toString().trim();
			}

			if (tagged == false) {
			} else {

				next_rec_id = last_rec_id + y;

				String sqlDetail = "INSERT into cm_rate_per_project_phase values (" +

						"" + next_rec_id + ",  \n" + // 1
						"'" + agent_id + "',  \n" + // 2
						"'" + proj_id + "',  \n" + // 3
						"'" + phase + "',  \n" + // 4
						"'" + hse_model_id + "',  \n"; // 5

				if (tblAddRate.getValueAt(x, 4) == null) {
					sqlDetail = sqlDetail + "null,";
				} else {
					sqlDetail = sqlDetail + "'" + effect_date + "',  \n";
				} // 6
				sqlDetail = sqlDetail +

						"" + rate + ",  \n" + // 7
						"'" + status_id + "',  \n" + // 8
						"'" + UserInfo.EmployeeCode + "',  \n" + // 9
						"'" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "'  \n" + // 10

						")   ";

				System.out.printf("SQL #1: %s", sqlDetail);

				db.executeUpdate(sqlDetail, false);

				y++;
			}

			x++;
		}

	}

	public void updateAgentRate(pgUpdate db) {

		int rw = tblRate.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String rec_id = tblRate.getValueAt(x, 0).toString().trim();
			Double rate = Double.parseDouble(tblRate.getValueAt(x, 4).toString());
			String status_id = tblRate.getValueAt(x, 6).toString().trim();
			String effect_date = "";

			if (tblRate.getValueAt(x, 5) == null) {
			} else {
				effect_date = tblRate.getValueAt(x, 5).toString().trim();
			}

			String sqlDetail = "update cm_rate_per_project_phase set " +

					"rate = " + rate + ",  \n" + // 1
					"eff_date = '" + effect_date + "',  \n" + // 2
					"status_id = '" + status_id + "'  \n" + // 3
					"where rate_id = " + rec_id + "  \n";

			System.out.printf("SQL #1: %s", sqlDetail);

			db.executeUpdate(sqlDetail, false);

			x++;
		}

	}

	public void saveAccreditation() {

		if (lookupPosition.getText().equals("003") && lookupOverride.getText().equals("")
				|| lookupPosition.getText().equals("003") && lookupOverride.getText().equals("-")) {
			JOptionPane.showMessageDialog(getContentPane(), "An override is required for a Sales Associate (SA).",
					"Incomplete Detail", JOptionPane.WARNING_MESSAGE);
		} else {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Update agent's info.?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();

				String sqlDetail = "update mf_sellingagent_info set \n" + "accredit_from = '" + dteAccredFrom.getDate()
						+ "',  \n" + "accredit_to = '" + dteAccredTo.getDate() + "', \n" + "edited_by = '"
						+ UserInfo.EmployeeCode + "', \n" + "date_edited = now()," + "override_id = '"
						+ lookupOverride.getText() + "', \n" + "status_id = 'A', \n" + "co_id = '" + txtCoID.getValue()
						+ "' \n" + "where agent_id = '" + agent_id + "'  \n";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);

				insertAgentAccredHistory(db);

				db.commit();

				JOptionPane.showMessageDialog(getContentPane(), "Agent's new accreditation added.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				clickTable();
			}
		}

	}

	public void saveATM() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Update agent's info.?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			Boolean relthruatm = false;
			Boolean atm_on_hand = false;
			if (chkRelThruATM.isSelected() == true) {
				relthruatm = true;
			} else {
				relthruatm = false;
			}

			if (rbtnATM_forRelease.isSelected() == true) {
				atm_on_hand = false;
			} else {
				atm_on_hand = true;
			}

			pgUpdate db = new pgUpdate();

			if (rbtn_with_ATM.isSelected() == true) {
				String sqlDetail = "update mf_sellingagent_info set " + "atm_no = '" + txtATM_No.getText().trim()
						+ "',  \n" + // 1
						"atm_bank_acct_id = '" + lookupATMAcctNo.getText().trim() + "',  \n" + // 2
						"atm_on_hand = " + atm_on_hand + ",\n" + "atm_onproc = true, \n" + "paythruatm = " + relthruatm
						+ "," + "atm_regdate = '" + dteDateReg.getDate() + "', \n" + "edited_by = '"
						+ UserInfo.EmployeeCode + "', \n" + "date_edited = now()  \n" + // 3
						"where agent_id = '" + agent_id + "'  \n";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Agent's new ATM info. added.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (rbtnEndorseATM.isSelected() == true) {
				String sqlDetail = "update mf_sellingagent_info set " + "atm_endorse_for_processing = true,"
						+ "atm_endorse_tag_date = now(), " + "atm_endorse_tagged_by = '" + UserInfo.EmployeeCode
						+ "',  \n" + // 3
						"atm_no = '',  \n" + // 1
						"atm_bank_acct_id = '',  \n" + // 2
						"atm_on_hand = false, \n" + "paythruatm = false," + "atm_regdate = null, \n" + "edited_by = '"
						+ UserInfo.EmployeeCode + "', \n" + "date_edited = now()  \n" + // 3
						"where agent_id = '" + agent_id + "'  \n";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Agent has been endorsed to FAD for processing.",
						"Information", JOptionPane.INFORMATION_MESSAGE);
			} else if (rbtn_no_ATM.isSelected() == true) {
				String sqlDetail = "update mf_sellingagent_info set " + "atm_onproc = false,"
						+ "atm_onproc_tagdate = null, " + "atm_no = '',  \n" + // 1
						"atm_bank_acct_id = '',  \n" + // 2
						"atm_on_hand = false, \n" + "paythruatm = false," + "atm_regdate = null, "
						+ "atm_endorse_for_processing = null," + "atm_endorse_tag_date = null, \n" + "edited_by = '"
						+ UserInfo.EmployeeCode + "', \n" + "date_edited = now()  \n" + // 3
						"where agent_id = '" + agent_id + "'  \n";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Agent's new ATM status updated.", "Information",
						JOptionPane.INFORMATION_MESSAGE);

			}
			clickTable();
		}

	}

	public void saveOverride() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Update agent's override/broker/coordinator details.?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();

			String sqlDetail = "update mf_sellingagent_info set \n" + "override_id = '" + override_id + "',  \n" + // 1
			// "broker_lic_no = '"+txtBrokerNo.getText().trim()+"', \n" + //2
			// "realty_name = '"+txtRealtyName.getText().trim()+"', \n" +
					"bdo_id = '" + lookupBDO.getText().trim() + "', \n" + "edited_by = '" + UserInfo.EmployeeCode
					+ "', \n" + "date_edited = now()  \n" + // 3
					"where agent_id = '" + agent_id + "'  \n";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			// INATIVATE FIRST BEFORE INSERTING NEW INHOUSE BROKER
			String sqlDetail2 = "update mf_inhouse_brokers_agents set status_id = 'I' where agent_id = '" + agent_id
					+ "' \n";
			System.out.printf("SQL #2: %s", sqlDetail2);
			db.executeUpdate(sqlDetail2, false);
			System.out.printf("DI KUMAGAT UPDATE!!!");
			System.out.printf("DI KUMAGAT UPDATE!!!");
			System.out.printf("DI KUMAGAT UPDATE!!!");

			String sqlDetail3 = "insert into mf_inhouse_brokers_agents values ( \n" + "'"
					+ lookupIH_BrokerID.getText().trim() + "' ," + "'" + agent_id + "' ," + "'A',  \n" + // 6
					"'" + UserInfo.EmployeeCode + "',  \n" + // 7
					"now(),  \n" + // 8
					"'')  \n" + // 9
					"\n";

			System.out.printf("SQL #3: %s", sqlDetail3);
			db.executeUpdate(sqlDetail3, false);
			System.out.printf("DI KUMAGAT INSERT!!!");
			System.out.printf("DI KUMAGAT INSERT!!!");
			System.out.printf("DI KUMAGAT INSERT!!!");

			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),
					"Agent's new  override/broker/in-house broker details updated.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			clickTable();
		}

	}

	public void saveIndEntity() {

		String l_name = "";
		String f_name = "";
		String m_name = "";
		String cell_no = "";
		String PRCno = "";
		String HLRUBno = "";
		String TINno = "";
		Date PRCvalid = null;
		Date HLURBvalid = null;

		f_name = txtFirstName.getText().trim().toUpperCase();
		m_name = txtMiddleName.getText().trim().toUpperCase();
		l_name = txtLastName.getText().trim().toUpperCase();
		TINno = txtTINNo.getText().trim().toUpperCase();
		cell_no = txtCellphoneNo.getText();
		PRCno = txtPRC.getText();
		HLRUBno = txtHLURB.getText();
		PRCvalid = dtePRCvalid.getDate();
		HLURBvalid = dteHLURBvalid.getDate();

		if (l_name.equals("") || f_name.equals("") || m_name.equals("") || cell_no.equals("") || PRCno.equals("")
				|| HLRUBno.equals("") || TINno.equals("") || PRCvalid == null || HLURBvalid == null) {
			JOptionPane.showMessageDialog(getContentPane(), "Please enter information in the required fields (*).",
					"Incomplete Detail", JOptionPane.WARNING_MESSAGE);
		} else {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entity details correct?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();
				String entity_id = sql_getEntityID();
				String first_name = txtFirstName.getText().trim().toUpperCase();
				String mid_name = txtMiddleName.getText().trim().toUpperCase();
				String last_name = txtLastName.getText().trim().toUpperCase();
				String last_name_intl = last_name.substring(0, 1);
				String mid_name_intl = mid_name.substring(0, 1);
				String first_name_intl = first_name.substring(0, 1);
				String full_name = last_name + ", " + first_name + " " + mid_name;
				String alias = first_name_intl + mid_name_intl + last_name_intl;
				String gender = "";
				String civil_st = "";
				String nationality = "";
				if (cmbGender.getSelectedIndex() == 0) {
					gender = "M";
				} else {
					gender = "F";
				}
				if (cmbCivilStatus.getSelectedIndex() == 0) {
					civil_st = "S";
				} else if (cmbCivilStatus.getSelectedIndex() == 1) {
					civil_st = "M";
				} else if (cmbCivilStatus.getSelectedIndex() == 2) {
					civil_st = "W";
				} else if (cmbCivilStatus.getSelectedIndex() == 3) {
					civil_st = "X";
				} else if (cmbCivilStatus.getSelectedIndex() == 4) {
					civil_st = "D";
				} else if (cmbCivilStatus.getSelectedIndex() == 5) {
					civil_st = "L";
				}
				if (cmbCitizenship.getSelectedIndex() == 0) {
					nationality = "FIL";
				} else {
					nationality = cmbCitizenship.getSelectedItem().toString();
				}

				// insert basic information
				String sqlDetail = "insert into rf_entity values ( \n" + "'" + entity_id + "', \n" + "'I', \n" + "'"
						+ full_name + "',   \n" + "'" + first_name + "',   \n" + "'" + last_name + "',   \n" + "'"
						+ mid_name + "',   \n" + "'" + alias + "',   \n" + "'"
						+ dateFormat.format(dateBirthDate.getDate()) + "',   \n" + "null, \n" + "'" + gender + "',   \n"
						+ "'" + civil_st + "', \n" + "null, \n" + "'" + nationality + "', \n" + "null, \n" + "false, \n"
						+ "null, \n" + "null, \n" + "'A', \n" + "null, \n" + "'" + UserInfo.EmployeeCode + "', \n" + "'"
						+ dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "', \n" + "null, \n"
						+ "null  \n" + // 3
						") \n\n";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);

				String sqlDetail2 = "insert into rf_contacts values ( \n" + "" + sql_getLastRecID_contact() + ", \n"
						+ "'" + entity_id + "',   \n" + "array['" + txtTelephoneNo.getText() + "']::varchar[],  \n"
						+ "array['" + txtCellphoneNo.getText() + "']::varchar[],   \n" + "array['" + txtFaxNo.getText()
						+ "']::varchar[],   \n" + "array['" + txtEmail.getText() + "']::varchar[],   \n" + "'A', \n"
						+ "'" + UserInfo.EmployeeCode + "', \n" + "now(), \n" + "null, \n" + "null,  \n" + "null, \n"
						+ "null  \n" + ") \n\n";

				System.out.printf("SQL #2: %s", sqlDetail2);
				db.executeUpdate(sqlDetail2, false);

				String sqlDetail3 = "insert into rf_entity_id_no values ( \n" + "'" + entity_id + "',   \n" + "'"
						+ txtSSSNo.getText() + "',   \n" + "null, \n" + "null, \n" + "null, \n" + "null, \n"
						+ "null, \n" + "'" + txtTINNo.getText() + "',   \n" + "null, \n" + "null, \n" + "null, \n"
						+ "null, \n" + "null, \n" + "null, \n" + "null, \n" + "null, \n" + "null, \n" + "null, \n"
						+ "null, \n" + "null, \n" + "'" + txtPRC.getText().trim() + "', \n" + // PRC
						"'" + PRCvalid + "', \n" + // PRC
						"null, \n" + "null, \n" + "null, \n" + "'A', \n" + "null, \n" + "'" + UserInfo.EmployeeCode
						+ "', \n" + "now(), \n" + "'', \n" + "null, \n" + "null, \n" + "'" + txtHLURB.getText().trim()
						+ "', \n" + // HLRUB
						"'" + HLURBvalid + "' \n" + ") \n\n";

				System.out.printf("SQL #3: %s", sqlDetail3);
				db.executeUpdate(sqlDetail3, false);

				/*
				 * String sqlDetail4 = "insert into rf_entity_assigned_type values ( \n" +
				 * "'"+entity_id+"',   \n" + "'04',   \n" + "'A', \n" +
				 * "'"+UserInfo.EmployeeCode+"', \n" + "now(), \n" + "'', \n" + "null \n" +
				 * ") \n\n" ;
				 * 
				 * System.out.printf("SQL #4: %s", sqlDetail4); db.executeUpdate(sqlDetail4,
				 * false);
				 */ // Removed by DG on 03/02/2017 - due to change in default Entity Type

				db.commit();
				
				JOptionPane.showMessageDialog(getContentPane(), "New entity's basic information added.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				lookupAgentName.setText(entity_id);
				tagAgentName.setTag(full_name);
				lookupSalesDiv.setLookupSQL(getDivision());
				lookupSalesDept.setLookupSQL(getSalesGroup());
				lookupPosition.setLookupSQL(getSalesPosition(lookupSalesType.getText()));
				lookupSalesType.setLookupSQL(getSalesType());
				lookupStatus.setLookupSQL(getStatus());
				lookupOverride.setLookupSQL(
						getOverride(lookupSalesType.getText(), lookupPosition.getText(), lookupSalesDept.getText()));
				lookupBDO.setLookupSQL(getBDO(lookupSalesDept.getText()));
				// JOptionPane.dispose();

				Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlIndividual);
				optionPaneWindow.dispose();
			}

		}

	}

	public void saveIndEntity_agentBasicInfo() {

		Boolean need_override = false;

		if (lookupPosition.getText().equals("003") || lookupPosition.getText().equals("008")
				|| lookupPosition.getText().equals("002") || lookupPosition.getText().equals("006")
				|| lookupPosition.getText().equals("007")) {
			need_override = true;
		} else {
			need_override = false;
		}

		if (need_override == true && lookupOverride.getText().equals("")
				|| need_override == true && lookupOverride.getText().equals("-")) {
			JOptionPane.showMessageDialog(getContentPane(), "An override is required.", "Incomplete Detail",
					JOptionPane.WARNING_MESSAGE);
			tabCenter.setSelectedIndex(1);
		} else {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all agent details correct?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				Boolean relthruatm = false;
				if (chkRelThruATM.isSelected() == true) {
					relthruatm = true;
				} else {
					relthruatm = false;
				}

				pgUpdate db = new pgUpdate();

				// insert agent's basic information
				String sqlDetail = "insert into mf_sellingagent_info values ( \n" + "'" + lookupAgentName.getText()
						+ "', \n" + "'" + lookupSalesDept.getText() + "', \n" + "'" + lookupSalesDiv.getText() + "', \n"
						+ "'00', \n" + "'" + lookupOverride.getText() + "', \n" + "'" + lookupBDO.getText() + "', \n"
						+ "'" + lookupPosition.getText() + "', \n" + "'" + lookupSalesType.getText() + "', \n"
						+ "0.00, \n" + "'" + txtBrokerNo.getText() + "', \n" + "'" + txtRealtyName.getText() + "', \n"
						+ "'" + dateFormat.format(dteAccredFrom.getDate()) + "',   \n" + "'"
						+ dateFormat.format(dteAccredTo.getDate()) + "',   \n" + "null, \n";

				if (rbtn_with_ATM.isSelected() == true) {
					sqlDetail = sqlDetail + "'" + txtATM_No.getText() + "', '" + dateFormat.format(dteDateReg.getDate())
							+ "', \n";
				} else {
					sqlDetail = sqlDetail + "null, null, \n";
				}

				if (rbtnEndorseATM.isSelected() == true) {
					sqlDetail = sqlDetail + "true, '" + dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))
							+ "',  \n";
				} else {
					sqlDetail = sqlDetail + "false, null, \n";
				}

				sqlDetail = sqlDetail + "'" + lookupATMAcctNo.getText() + "', \n" + "" + relthruatm + ", false,"
						+ "null," + "'A'," + "'" + UserInfo.EmployeeCode + "', " + "'"
						+ dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "', \n" + "null, \n"
						+ "null, \n" +

						/* Added by Mann2x; Date Added: July 10, 2019; DCRF#1134; */
						"null, \n" + /* atm_endorse_for_processing */
						"null, \n" + /* atm_endorse_tag_date */
						"null, \n" + /* atm_endorse_tagged_by */
						"null, \n" + /* atm_rlsd_by */
						"null, \n" + /* atm_rlsd_date */
						"null, \n" + /* with_income_waiver */
						"null, \n" + /* default_check_payee */
						"'" + lookupBranch.getValue() + "' \n" + /* branch_id */

						"); ";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);

				// save new accreditation history
				insertAgentAccredHistory(db);

				// save agent/broker rate
				if (lookupSalesType.getText().equals("002") && Double.parseDouble(txtRate.getText()) > 0) {
					saveNewAgentRate(db);
				}

				/*
				 * //set default Entity Type ; added by DG on 03/02/2017 String sqlDetail2 =
				 * "insert into rf_entity_assigned_type values ( \n" +
				 * "'"+lookupAgentName.getText()+"',   \n" ;
				 * 
				 * if (lookupPosition.getText().equals("005")||lookupPosition.getText().equals(
				 * "006")||lookupPosition.getText().equals("007")) { sqlDetail2 = sqlDetail2 +
				 * "'35',   \n" ; } else { sqlDetail2 = sqlDetail2 + "'04',   \n" ; }
				 * 
				 * sqlDetail2 = sqlDetail2 + "'A', \n" + "'"+UserInfo.EmployeeCode+"', \n" +
				 * "now(), \n" + "'', \n" + "null \n" + ") \n\n" ;
				 * 
				 * System.out.printf("SQL #2: %s", sqlDetail2); db.executeUpdate(sqlDetail2,
				 * false);
				 */

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Agent's basic information added.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				displayAgentList(modelAgentList, rowHeaderAgentList, modelAgentList_total, div_id);
				displayAgentAccreditationHistory(modelAccredHistory, rowHeaderAccredHistory, modelAccredHistory_total);
				enableFields(false);
				enableMainButtons(true, true, false, true);
				agent_id = lookupAgentName.getText();
				displayPersonalInfo();
				btnPrintContract.setEnabled(true);
			}
		}
	}

	public void update_agentBasicInfo() {

		Boolean need_override = false;
		if (lookupPosition.getText().equals("003") || lookupPosition.getText().equals("008")
				|| lookupPosition.getText().equals("002") || lookupPosition.getText().equals("006")
				|| lookupPosition.getText().equals("007")) {
			need_override = true;
		} else {
			need_override = false;
		}

		if (need_override == true && lookupOverride.getText().equals("")
				|| need_override == true && lookupOverride.getText().equals("-")) {
			JOptionPane.showMessageDialog(getContentPane(), "An override is required.", "Incomplete Detail",
					JOptionPane.WARNING_MESSAGE);
			tabCenter.setSelectedIndex(1);
		} else {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Update existing agent details?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				Boolean relthruatm = false;
				if (chkRelThruATM.isSelected() == true) {
					relthruatm = true;
				} else {
					relthruatm = false;
				}

				pgUpdate db = new pgUpdate();
				String sqlDetail = "update mf_sellingagent_info set \n" + "dept_id 	  = '" + lookupSalesDept.getText()
						+ "', \n" + "sales_div_id = '" + lookupSalesDiv.getText() + "', \n" + "override_id  = '"
						+ lookupOverride.getText() + "', \n" + "bdo_id = '" + lookupBDO.getText() + "', \n"
						+ "salespos_id  = '" + lookupPosition.getText() + "', \n" + "salestype_id = '"
						+ lookupSalesType.getText() + "', \n" + "broker_lic_no= '" + txtBrokerNo.getText() + "', \n"
						+ "realty_name  = '" + txtRealtyName.getText() + "', \n" + "accredit_from= '"
						+ dateFormat.format(dteAccredFrom.getDate()) + "',   \n" + "accredit_to  = '"
						+ dateFormat.format(dteAccredTo.getDate()) + "',   \n";

				if (rbtn_with_ATM.isSelected() == true) {
					sqlDetail = sqlDetail + "atm_no = '" + txtATM_No.getText() + "', " + "atm_regdate = '"
							+ dateFormat.format(dteDateReg.getDate()) + "', \n";
				} else {
					sqlDetail = sqlDetail + "atm_no = null, atm_regdate = null, \n";
				}

				if (rbtnEndorseATM.isSelected() == true) {
					sqlDetail = sqlDetail + "atm_onproc = true, " + "atm_onproc_tagdate = '"
							+ dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  \n";
				} else {
					sqlDetail = sqlDetail + "atm_onproc = false, atm_onproc_tagdate = null, \n";
				}

				sqlDetail = sqlDetail + "atm_bank_acct_id = '" + lookupATMAcctNo.getText() + "', \n" + "paythruatm = "
						+ relthruatm + ", \n";

				if (lookupStatus.getText().equals("I")) {
					sqlDetail = sqlDetail + "agent_inactive_date = '"
							+ dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "',  \n";
				} else {
					sqlDetail = sqlDetail + "agent_inactive_date = null, \n";
				}

				sqlDetail = sqlDetail + "status_id = '" + lookupStatus.getText() + "', \n" + "edited_by = '"
						+ UserInfo.EmployeeCode + "', \n" + "date_edited = '"
						+ dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())) + "', \n" + "branch_id = '"
						+ lookupBranch.getValue() + "' \n" + "where agent_id = '" + agent_id + "'";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);

				insertAgentAccredHistory(db);
				if (lookupSalesType.getText().equals("002") && Double.parseDouble(txtRate.getText()) > 0) {
					saveNewAgentRate(db);
				}

				db.commit();
				
				pgUpdate dbExec = new pgUpdate(); 
				if (FncGlobal.GetBoolean("select exists(select * from mf_sellingagent_info where agent_id = '"+agent_id+"' and array_position(co_id, '02') is not null); ")) {
					dbExec.executeUpdate("update mf_sellingagent_info \n" + 
							"set co_id[array_position(co_id, '"+txtCoID.getValue()+"')] = '"+txtCoID.getValue()+"', entity_type_id[array_position(co_id, '"+txtCoID.getValue()+"')] = '"+txtEntityTypeID.getValue()+"' \n" + 
							"where agent_id = '"+agent_id+"'; ", true);
				} else {
					dbExec.executeUpdate("update mf_sellingagent_info \n" + 
							"set co_id[array_length(co_id)] = '"+txtCoID.getValue()+"', entity_type_id[array_length(co_id)] = '"+txtEntityTypeID.getValue()+"' \n" + 
							"where agent_id = '"+agent_id+"'; ", true);	
				}
				dbExec.commit();

				JOptionPane.showMessageDialog(getContentPane(), "Agent's basic information updated.", "Information", JOptionPane.INFORMATION_MESSAGE);
				displayAgentList(modelAgentList, rowHeaderAgentList, modelAgentList_total, div_id);
				displayAgentAccreditationHistory(modelAccredHistory, rowHeaderAccredHistory, modelAccredHistory_total);
				enableFields(false);
				enableMainButtons(true, true, false, true);

				DefaultComboBoxModel model = (DefaultComboBoxModel) listCom.getModel();
			    model.removeAllElements();
				
				listCom.repaint();
				listCom.revalidate();
				
			}
		}

	}

	public void insertAgentDoc(pgUpdate db) {

		String sqlDetail =

				"INSERT into cm_agent_documents values (" +

						"" + sql_getNextDocRecID() + ",  \n" + // 0
						"'" + agent_id + "',  \n" + // 1
						"'" + lookupDoc.getText().trim() + "',  \n" + // 2
						"now(),  \n" + // 3
						"'" + UserInfo.EmployeeCode + "',  \n" + // 4
						"'',  \n" + // 5
						"null,  \n" + // 6
						"'" + txtDocRemarks.getText().trim().replace("'", "''") + "',  \n" + // 7
						"'A')  \n"; // 8

		System.out.printf("SQL #1 insertAgentDoc: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	public void updateAgentDoc(pgUpdate db) {

		int rw = tblDocuments.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String date_submitted = "";
			if (tblDocuments.getValueAt(x, 3) == null) {
			} else {
				date_submitted = tblDocuments.getValueAt(x, 3).toString().trim();
			}

			String sqlDetail = "update cm_agent_documents set " + "doc_id = '"
					+ tblDocuments.getValueAt(x, 1).toString().trim() + "',  \n" + // 2
					"date_submitted = '" + date_submitted + "',   \n" + "date_edited = now(),  \n" + // 3
					"edited_by = '" + UserInfo.EmployeeCode + "',  \n" + // 4
					"remarks = '" + tblDocuments.getValueAt(x, 5).toString().trim().replace("'", "''") + "',  \n" + // 7
					"status_id = '" + tblDocuments.getValueAt(x, 6).toString().trim() + "'  \n" + // 7
					"where agent_code = '" + agent_id + "'  " + "and agent_doc_rec_id = "
					+ tblDocuments.getValueAt(x, 0).toString().trim() + " \n"; // 8

			System.out.printf("SQL #1 updateAgentDoc: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);
			x++;
		}
	}

	public void saveMainInfo() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Update agent's personal information?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();

			String type = "";
			String vatable = "";
			if (cmbPI_type.getSelectedIndex() == 0) {
				type = "I";
			} else {
				type = "C";
			}
			if (cmbVatable.getSelectedIndex() == 0) {
				vatable = "true";
			} else {
				vatable = "false";
			}
			String full_name = txtPI_lastname.getText().trim().toUpperCase() + ", "
					+ txtPI_firstname.getText().trim().toUpperCase() + " "
					+ txtPI_midname.getText().trim().toUpperCase();
			Date hlurb_date = dteHLURB_validity.getDate();
			Date prc_date = dtePRC_validity.getDate();

			String sqlDetail = "update rf_entity set \n";

			if (txtPI_lastname.getText().trim().equals("") || txtPI_lastname.getText().trim().equals(null)) {

			} else {
				sqlDetail = sqlDetail + " entity_name = '" + full_name + "',  \n";
			}

			sqlDetail = sqlDetail + "first_name = '" + txtPI_firstname.getText().trim().toUpperCase() + "',  \n" + // 1
					"last_name = '" + txtPI_lastname.getText().trim().toUpperCase() + "',  \n" + // 2
					"middle_name = '" + txtPI_midname.getText().trim().toUpperCase() + "', \n" + "entity_kind = '"
					+ type + "', \n" + "vat_registered = " + vatable + ", \n" + "edited_by = '" + UserInfo.EmployeeCode
					+ "', \n" + "date_edited = now()  \n" + // 3
					"where entity_id = '" + agent_id + "'  \n";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			String sqlDetail2 = "update rf_entity_id_no set \n" + "sss_no = '" + txtSSS.getText().trim() + "',  \n" + // 1
					"tin_no = '" + txtTIN.getText().trim() + "',  \n" + // 2
					"prc_id = '" + txtPRC_no.getText().trim() + "', \n" + "hlurb_regist_no = '"
					+ txtHLURB_no.getText().trim() + "', \n";
			// "prc_valid_date = '"+dtePRC_validity.getDate()+"', \n" ;

			if (txtTIN.getText().trim() == null || txtTIN.getText().trim().equals("")) {
				sqlDetail2 = sqlDetail2 + "date_tin_no = null, \n";
			} else {
				sqlDetail2 = sqlDetail2 + "date_tin_no = now(), \n";
			}

			if (hlurb_date == null) {
				sqlDetail2 = sqlDetail2 + "hlurb_reg_no_valid_date = null, \n";
			} else {
				sqlDetail2 = sqlDetail2 + "hlurb_reg_no_valid_date = '" + dteHLURB_validity.getDate() + "', \n";
			}

			if (prc_date == null) {
				sqlDetail2 = sqlDetail2 + "date_prc_id = null, \n";
			} else {
				sqlDetail2 = sqlDetail2 + "date_prc_id = '" + dtePRC_validity.getDate() + "', \n";
			}

			sqlDetail2 = sqlDetail2 + "edited_by = '" + UserInfo.EmployeeCode + "', \n" + "date_edited = now()  \n" + // 3
					"where entity_id = '" + agent_id + "'  \n";

			System.out.printf("SQL #1: %s", sqlDetail2);

			db.executeUpdate("update mf_sellingagent_info \n" + "set co_id = '" + txtCoID.getValue() + "' \n"
					+ "where agent_id = '" + agent_id + "'", false);

			db.executeUpdate(sqlDetail2, false);
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(), "Agent's personal information updated.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			clickTable();

		}
	}

	public void saveNewDownline() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to add new downline?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();
			String sqlDetail = "update mf_sellingagent_info \n" +
					"set override_id = '"+agent_id+"' \n" +
					"where agent_id = '"+lookupDownline.getText().trim()+"'; ";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();

			JOptionPane.showMessageDialog(getContentPane(), "New downline added.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			clickTable();
		}

	}

	public void removeDownline(String agent) {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove downline?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();

			String sqlDetail = "update mf_sellingagent_info set \n" + "override_id = ''  \n" + "where agent_id = '"
					+ agent + "'  \n";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();

			JOptionPane.showMessageDialog(getContentPane(), "Downline removed.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			clickTable();
		}

	}

	public void updateAgentStatus_toInactive(pgUpdate db) {

		// insert agent's basic information
		String sqlDetail = "update mf_sellingagent_info set \n" +

				"status_id 	  = 'I', \n" + "agent_inactive_date  = '" + dteAccredTo.getDate() + "'   \n" +

				"where agent_id = '" + agent_id + "' \n" +

				"\n\n";

		System.out.printf("SQL #1 updateAgentStatus_toInactive: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}

	public void insertAgentAccredHistory(pgUpdate db) {

		// inactivate all past accreditation
		String sql = "update cm_agent_accreditation_history set " + "status_id = 'I' " + "where agent_code = '"
				+ lookupAgentName.getText() + "' \n";

		System.out.printf("SQL #1 insertAgentAccredHistory1: %s", sql);
		db.executeUpdate(sql, false);

		// save new accreditation history
		String sqlDetail =

				"INSERT into cm_agent_accreditation_history values (" + "'" + lookupAgentName.getText() + "',  \n" + "'"
						+ dteAccredFrom.getDate() + "', \n" + "'" + dteAccredTo.getDate() + "', \n" + "'"
						+ lookupSalesDept.getText() + "', \n" + "'" + lookupSalesDiv.getText() + "', \n" + "'"
						+ lookupSalesType.getText() + "', \n" + "'" + lookupPosition.getText() + "', \n" + "'"
						+ lookupOverride.getText() + "', \n" + "'A',  \n" + // 2
						"'" + UserInfo.EmployeeCode + "',  \n" + "now(),  \n" + "now()  " + ") \n";

		System.out.printf("SQL #2 insertAgentAccredHistory2: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	public void updateValidityPeriod() {

		if (dteUpdateHLURB.getDate() == null || dteUpdatePRC.getDate() == null) {
			JOptionPane.showMessageDialog(getContentPane(), "Please indicate validity date(s).", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}

		else {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Update PRC/HLURB ID Validity Period?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();

				Date hlurb_date = dteUpdateHLURB.getDate();
				Date prc_date = dteUpdatePRC.getDate();

				String sqlDetail = "update rf_entity_id_no set \n" + "prc_id = '" + txtPRC_no.getText().trim() + "', \n"
						+ "hlurb_regist_no = '" + txtHLURB_no.getText().trim() + "', \n";

				if (hlurb_date == null) {
					sqlDetail = sqlDetail + "hlurb_reg_no_valid_date = null, \n";
				} else {
					sqlDetail = sqlDetail + "hlurb_reg_no_valid_date = '" + hlurb_date + "', \n";
				}

				if (prc_date == null) {
					sqlDetail = sqlDetail + "date_prc_id = null \n";
				} else {
					sqlDetail = sqlDetail + "date_prc_id = '" + prc_date + "' \n";
				}

				sqlDetail = sqlDetail +

						"where entity_id = '" + agent_id + "'  \n";

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);
				db.commit();

				JOptionPane.showMessageDialog(getContentPane(), "Agent's new PRC/HLURB ID updated.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlUpdateValidityPeriod);
				optionPaneWindow.dispose();
				saveAccreditation();
			}

		}

	}

	public void insertBroker() {

		if (hasDefaultPayee() == true && chkCheckPayee.isSelected() == true) {
			JOptionPane.showMessageDialog(getContentPane(), "Agent has a default check payee already.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();

				String sqlDetail =

						"INSERT into mf_brokers_details values (" +

								"'" + lookupAgentName.getText() + "',  \n" + // 0
								"'" + txtBrokerName.getText() + "',  \n" + // 1
								"'" + txtBrokerPRC_no.getText() + "',  \n" + // 2
								"'" + dtePRC_Validity.getDate() + "',  \n" + // 3
								"'" + txtPRCHLURB_no.getText() + "',  \n" + // 4
								"'" + dteHLURB_Validity.getDate() + "',  \n" + // 5
								"'A',  \n" + // 6
								"'" + UserInfo.EmployeeCode + "',  \n" + // 7
								"now(),  \n" + // 8
								"'',  \n" + "null, \n" + // 10
								"" + chkCheckPayee.isSelected() + " ) \n"; // 9

				System.out.printf("SQL #1 insertBroker: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Broker successfully added.", "Information",
						JOptionPane.INFORMATION_MESSAGE);

				/*
				 * btnSaveAgentDownline.setEnabled(false); lookupDownline.setValue("");
				 * txtDownlineName.setText(""); txtDownlinePosn.setText("");
				 * txtDownlineOverride.setText("");
				 */
				displayBroker(modelBroker, rowHeaderBroker, modelBroker_total);

			}
		}
	}

	public void editBroker() {

		if (hasDefaultPayee() == true && chkCheckPayee.isSelected() == true) {
			JOptionPane.showMessageDialog(getContentPane(), "Agent has a default check payee already.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();

				String sqlDetail =

						"update mf_brokers_details set \n" + "broker_name = '" + txtBrokerName.getText() + "',  \n" + // 1
								"prc_no = '" + txtBrokerPRC_no.getText() + "',  \n" + // 2
								"prc_validity = '" + dtePRC_Validity.getDate() + "',  \n" + // 3
								"hlurb_no = '" + txtPRCHLURB_no.getText() + "',  \n" + // 4
								"hlurb_validity = '" + dteHLURB_Validity.getDate() + "',  \n" + // 5
								"edited_by = '" + UserInfo.EmployeeCode + "',  \n" + // 7
								"date_edited = now()," + "default_check_payee = " + chkCheckPayee.isSelected() + "  \n"
								+ // 8
								"where broker_name = '" + broker_name + "'  \n"; // 10

				System.out.printf("SQL #1 updateBroker: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(), "Broker successfully edited.", "Information",
						JOptionPane.INFORMATION_MESSAGE);

				/*
				 * btnSaveAgentDownline.setEnabled(false); lookupDownline.setValue("");
				 * txtDownlineName.setText(""); txtDownlinePosn.setText("");
				 * txtDownlineOverride.setText("");
				 */
				displayBroker(modelBroker, rowHeaderBroker, modelBroker_total);
			}
		}
	}

	public void removeBroker() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove this broker?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();

			String sqlDetail =

					"update mf_brokers_details set \n" +

							"status_id = 'I'  \n" + // 1
							"where broker_name = '" + broker_name + "'  \n"; // 10

			System.out.printf("SQL #1 updateBroker: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), "Broker successfully removed.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			/*
			 * btnSaveAgentDownline.setEnabled(false); lookupDownline.setValue("");
			 * txtDownlineName.setText(""); txtDownlinePosn.setText("");
			 * txtDownlineOverride.setText("");
			 */
			displayBroker(modelBroker, rowHeaderBroker, modelBroker_total);

		}
	}

	public void removeBroker(String agent) {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove broker?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();

			String sqlDetail = "update mf_brokers_details set \n" + "status_id = 'I',  \n" + "edited_by = '"
					+ UserInfo.EmployeeCode + "',  \n" + // 3
					"date_edited = now()  \n" + // 4
					"where agent_id = '" + agent + "' ";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();

			JOptionPane.showMessageDialog(getContentPane(), "Broker removed.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			clickTable();
		}

	}

	public void saveNewAgent() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to add a new agent?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();

			String sqlDetail = "insert into mf_inhouse_brokers_agents values ( \n" + "'"
					+ lookupAgentName.getText().trim() + "' ," + "'" + lookupDownline.getText().trim() + "' ,"
					+ "'A',  \n" + // 6
					"'" + UserInfo.EmployeeCode + "',  \n" + // 7
					"now(),  \n" + // 8
					"'')  \n" + // 9
					"\n";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();

			JOptionPane.showMessageDialog(getContentPane(), "New agent added.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			displayAgent(modelAgents, rowHeaderAgents, modelAgents_total);

		}

	}

	public void removeAgent(String agent) {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove this agent?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();

			String sqlDetail =

					"update mf_inhouse_brokers_agents set \n" +

							"status_id = 'I'  \n" + // 1
							"where agent_id = '" + agent + "' \n" + "and broker_id = '"
							+ lookupAgentName.getText().trim() + "' ";

			System.out.printf("SQL #1 updateAgent: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), "Agent successfully removed.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			displayAgent(modelAgents, rowHeaderAgents, modelAgents_total);

		}
	}

	public void saveNewAgentRate(pgUpdate db) {

		String sqlDetail = "update cm_agent_rate_history set status_id = 'I' where agent_code = '" + agent_id + "'\n\n";
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

		String sqlDetail2 = "insert into cm_agent_rate_history values ( \n"
				+ "(select (case when max(coalesce(rate_id,1)) is null then 1 \n"
				+ "		else max(coalesce(rate_id,1))+1 end) from cm_agent_rate_history),   \n" + "'" + agent_id + "' ,"
				+ "now()," + "" + Double.parseDouble(txtRate.getText().replace(",", "")) + ", \n" + "'A',  \n" + // 6
				"'" + UserInfo.EmployeeCode + "',  \n" + // 7
				"now(),  \n" + // 8
				"'', \n" + "null )  \n" + // 9
				"\n";

		System.out.printf("SQL #1: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}

	// preview
	private void previewContractBroker_external() {

		String criteria = "External Broker Accreditation Contract";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		String comp = "";
		if (lookupCompany.getText().trim().equals("02")) {
			comp = "CENQHOMES";
		} else {
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("comp", comp);
		mapParameters.put("comp_address", sql_getCompAddress());
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("agent_address", sql_geBrkrAddress());
		mapParameters.put("co_representative", "JOHNNY CORPUZ");
		mapParameters.put("broker_representative", sql_getBrokerRep());
		mapParameters.put("accred_date_from", dateFormat.format(dteAccredFrom.getDate()));
		mapParameters.put("accred_date_to", dateFormat.format(dteAccredTo.getDate()));
		FncReport.generateReport("/Reports/rptBrokerContract2.jasper", reportTitle, company, mapParameters);

	}

	private void previewContractBroker_inhouse() {

		String criteria = "In-House Broker Accreditation Contract";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		String comp = "";
		if (lookupCompany.getText().trim().equals("02")) {
			comp = "CENQHOMES";
		} else {
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("comp", comp);
		mapParameters.put("comp_address", sql_getCompAddress());
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("agent_address", sql_geBrkrAddress());
		mapParameters.put("co_representative", "JOHNNY CORPUZ");

		FncReport.generateReport("/Reports/rptInHouseAgntContract.jasper", reportTitle, company, mapParameters);
	}

	private void previewContractSalesperson() {

		String criteria = "In-House Broker Accreditation Contract";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		String comp = "";
		if (lookupCompany.getText().trim().equals("02")) {
			comp = "CENQHOMES";
		} else {
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("comp", comp);
		mapParameters.put("comp_address", sql_getCompAddress());
		mapParameters.put("agent_name", agent_name);
		mapParameters.put("agent_address", sql_geBrkrAddress());
		mapParameters.put("co_representative", "JOHNNY CORPUZ");
		mapParameters.put("prc_no", txtPRC_no.getText());

		FncReport.generateReport("/Reports/rptSalesperonContract.jasper", reportTitle, company, mapParameters);
	}

	// right-click implementation
	// remove this when editing as this causes eclipse to hang.
	/*
	 * public void openCLientInfo(){
	 * 
	 * ClientInformation client_info = new ClientInformation();
	 * Home_JSystem.addWindow(client_info);
	 * 
	 * String entity_id = agent_id;
	 * 
	 * //CLEARS THE FIELDS WHEN SELECTING NEW CLIENT
	 * ClientInformation.pnlCI.clearCIFields();
	 * ClientInformation.pnlConnect.clearConnectionFields();
	 * ClientInformation.pnlADDRESS.clearAddressFields();
	 * ClientInformation.pnlWorkExp.clearFieldsWorkExp();
	 * ClientInformation.pnlDepend.clearDependentFields();
	 * ClientInformation.pnlFinanceInfo.clearFields();
	 * ClientInformation.lookupClient.setValue(entity_id); //pnlRefOther.cl
	 * 
	 * //DISPLAYS THE DETAILS AFTER CLEARING FOR THE SELECTED ENTITY
	 * ClientInformation.displayClientInformation(entity_id);
	 * ClientInformation.displayConnectionList(entity_id);
	 * ClientInformation.displayAddressList(entity_id);
	 * ClientInformation.displayWorkExpList(entity_id);
	 * ClientInformation.displayDependentList(entity_id);
	 * ClientInformation.displayReferencesOther(entity_id);
	 * ClientInformation.displayFinancialInfo(entity_id);
	 * 
	 * //ClientInformation.this.setTitle("Client Information - "+entity_name+"");
	 * 
	 * ClientInformation.pnlState(true, true, true, true, true, true, true, true);
	 * Integer selectedTab = tabCenter.getSelectedIndex(); if(selectedTab ==
	 * 0){//Enabling of buttons for the Client Info Panel
	 * ClientInformation.btnState(false, true, false, false, false); }
	 * if(selectedTab == 1){ // Enabling of buttons for the Address Panel
	 * ClientInformation.btnState(true, false, false, false, false); }
	 * if(selectedTab == 2){ //Enabling of buttons for the Connections Panel
	 * ClientInformation.btnState(true, false, false, false, false); } if
	 * (selectedTab == 3){ //Enabling of buttons for the Dependents Panel
	 * ClientInformation.btnState(true, false, false, false, false); } if
	 * (selectedTab == 4){ //Enabling of buttons for the Work Exp Panel
	 * ClientInformation.btnState(true, false, false, false, false); } if
	 * (selectedTab == 5){// Enabling of buttons for the Financial Info Panel
	 * ClientInformation.btnState(false, true, false, false, false); } if
	 * (selectedTab == 6){//Enabling of buttons for the References/Other Info Panel
	 * ClientInformation.btnState(true, false, false, false, false); }
	 * 
	 * }
	 */

	private JPanel accreditation() {
		pnlAccreditation = new JPanel(new BorderLayout(5, 5));
		pnlAccreditation.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JPanel panPage = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlAccreditation.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 80));
				panPage.setBorder(JTBorderFactory.createTitleBorder("Current Accreditation", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JPanel panDate = new JPanel(new GridLayout(2, 1, 5, 5)); 
						panPage.add(panDate); 
						{
							{
								JPanel from = new JPanel(new BorderLayout(5, 5));
								panDate.add(from);
								{
									{
										JLabel label = new JLabel("Accredited From ", JLabel.TRAILING);
										from.add(label, BorderLayout.LINE_START);
										label.setHorizontalAlignment(JLabel.LEFT);
										label.setPreferredSize(new Dimension(150, 0));
									}
									{
										dteAccredFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										from.add(dteAccredFrom, BorderLayout.CENTER);
										dteAccredFrom.getCalendarButton().setVisible(true);
										dteAccredFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
								}
							}
							{
								JPanel to = new JPanel(new BorderLayout(5, 5));
								panDate.add(to);
								{
									{
										JLabel label = new JLabel("Accredited To", JLabel.TRAILING);
										to.add(label, BorderLayout.LINE_START);
										label.setHorizontalAlignment(JLabel.LEFT);
										label.setPreferredSize(new Dimension(150, 0));
									}
									{
										dteAccredTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										to.add(dteAccredTo, BorderLayout.CENTER);
										dteAccredTo.getCalendarButton().setVisible(true);
										dteAccredTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
								}
							}
						}
					}
					{
						card = new CardLayout(); 
						
						panelIO = new JPanel(card);
						panPage.add(panelIO); 
						{
							panelIO.add("output", panComDisplay()); 
							panelIO.add("input", panComInput());
						} 
					}
				}
			}
			{
				JPanel panCenter = new JPanel(new BorderLayout(5, 5));
				pnlAccreditation.add(panCenter, BorderLayout.CENTER);
				panCenter.setBorder(JTBorderFactory.createTitleBorder("Accreditation History",
						FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					scrollAccrdHistory = new _JScrollPaneMain();
					panCenter.add(scrollAccrdHistory, BorderLayout.CENTER);
					{
						modelAccredHistory = new modelSalesAgentAccreditationHistory();

						tblAccredHistory = new _JTableMain(modelAccredHistory);
						scrollAccrdHistory.setViewportView(tblAccredHistory);
						tblAccredHistory.addMouseListener(this);
						tblAccredHistory.setSortable(false);
						tblAccredHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblAccredHistory.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}

							public void keyPressed(KeyEvent e) {
							}

						});
						tblAccredHistory.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblAccredHistory.rowAtPoint(e.getPoint()) == -1) {
								} else {
									tblAccredHistory.setCellSelectionEnabled(true);
								}
								table = "edit_downline";
							}

							public void mouseReleased(MouseEvent e) {
								if (tblAccredHistory.rowAtPoint(e.getPoint()) == -1) {
								} else {
									tblAccredHistory.setCellSelectionEnabled(true);
								}
								table = "edit_downline";
							}
						});

					}
					{
						rowHeaderAccredHistory = tblAccredHistory.getRowHeader();
						scrollAccrdHistory.setRowHeaderView(rowHeaderAccredHistory);
						scrollAccrdHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 4, 5, 5));
				pnlAccreditation.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnEditAccreditation = new JButton("Add New Accreditation");
						panEnd.add(btnEditAccreditation);
						btnEditAccreditation.setActionCommand("EditAccred");
						btnEditAccreditation.addActionListener(this);
						btnEditAccreditation.setEnabled(false);
						btnEditAccreditation.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								if (withAccess() == true) {
									if (isAgenthasCAwriteoff(agent_id) == true) {
										JOptionPane.showMessageDialog(getContentPane(),
												"Agent has an active Cash Advance write-off. \n"
														+ "Please coordinate with Accounting for reversal, \n"
														+ "before adding new accreditation information.",
												"Information", JOptionPane.INFORMATION_MESSAGE);
									} else {
										dteAccredFrom.setEnabled(true);
										dteAccredTo.setEnabled(true);
										btnEditAccreditation.setEnabled(false);
										btnSaveAccreditation.setEnabled(true);
										btnCancelAccreditation.setEnabled(true);

										switcher("input");
										
										@SuppressWarnings("deprecation")
										Date date1 = new Date(2016, Calendar.DECEMBER, 31);
										dteAccredFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										dteAccredTo.setDate(date1);
									}
								} else {
									JOptionPane.showMessageDialog(getContentPane(),
											"Sorry, you are not authorized to edit Agent's Info..", "Information",
											JOptionPane.INFORMATION_MESSAGE);
								}

							}
						});
					}
					{
						btnSaveAccreditation = new JButton("Save New Accreditation");
						panEnd.add(btnSaveAccreditation);
						btnSaveAccreditation.setActionCommand("UpdateAccred");
						btnSaveAccreditation.addActionListener(this);
						btnSaveAccreditation.setEnabled(false);
						btnSaveAccreditation.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlUpdateValidityPeriod,
										"Update PRC/HLURB ID validity period", JOptionPane.PLAIN_MESSAGE,
										JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

								if (scanOption == JOptionPane.CLOSED_OPTION) {
									try {

									} catch (java.lang.ArrayIndexOutOfBoundsException ev) {
										
									}
								}
							}
						});
					}
					{
						btnPrintContract = new JButton("Print Contract");
						panEnd.add(btnPrintContract);
						btnPrintContract.addActionListener(this);
						btnPrintContract.setEnabled(false);
						btnPrintContract.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								if (withAccess() == true) {
									if (lookupPosition.getText().equals("009")) {
										previewContractBroker_inhouse();
									} else if (lookupPosition.getText().equals("005")) {
										previewContractBroker_external();
									} else {
										previewContractSalesperson();
									}
								} else {
									JOptionPane.showMessageDialog(getContentPane(),
											"Sorry, you are not authorized to print Contracts.", "Information",
											JOptionPane.INFORMATION_MESSAGE);
								}

							}
						});
					}
					{
						btnCancelAccreditation = new JButton("Cancel Editing");
						panEnd.add(btnCancelAccreditation);
						btnCancelAccreditation.setActionCommand("CancelAccreditation");
						btnCancelAccreditation.addActionListener(this);
						btnCancelAccreditation.setEnabled(false);
						btnCancelAccreditation.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								clickTable();
							}
						});
					}
				}
			}
		}
		
		return pnlAccreditation;
	}
	
	private JPanel panComDisplay() {
		JPanel panCom = new JPanel(new BorderLayout(5, 5)); 
		{
			JScrollPane scroll = new JScrollPane();
			panCom.add(scroll, BorderLayout.CENTER);
			{
				listCom = new JList();
				scroll.setViewportView(listCom);
				listCom.setCellRenderer(new _JCheckListRenderer());
				listCom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			}
		}
		
		
		
		return panCom; 
	}
	
	public static ArrayList<_JCheckListItem> getCompany(String agent_id) {
		ArrayList<_JCheckListItem> listCheckItem = new ArrayList<_JCheckListItem>();

		String sql = "select concat(b.company_alias, ' - ', c.entity_type_desc) \n" + 
				"from (select unnest(a.co_id) as co_id, unnest(a.entity_type_id) as entity_type_id from mf_sellingagent_info a where agent_id = '"+agent_id+"') a \n" + 
				"left join mf_company b on a.co_id = b.co_id \n" + 
				"left join mf_entity_type c on a.entity_type_id = c.entity_type_id; ";

		System.out.println("");
		System.out.println("sql: "+sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				String phase_no = (String) db.getResult()[x][0];
				listCheckItem.add(new _JCheckListItem(phase_no, true));
			}
			return listCheckItem;
		}else{
			return null;
		}
	}
	
	private JPanel panComInput() {
		
		JPanel panOther = new JPanel(new GridLayout(2, 1, 5, 5)); 
		{
			{
				JPanel company = new JPanel(new BorderLayout(5, 5));
				panOther.add(company);
				{
					{
						JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5));
						company.add(panLabel, BorderLayout.LINE_START);
						panLabel.setPreferredSize(new Dimension(150, 0));
						{
							{
								JLabel label = new JLabel("Company", JLabel.TRAILING);
								panLabel.add(label, BorderLayout.LINE_START);
								label.setHorizontalAlignment(JLabel.CENTER);
								label.setPreferredSize(new Dimension(200, 0));
							}
							{
								txtCoID = new _JLookup("");
								panLabel.add(txtCoID, BorderLayout.LINE_START);
								txtCoID.setReturnColumn(0);
								txtCoID.setEnabled(false);
								txtCoID.setHorizontalAlignment(_JLookup.CENTER);
								txtCoID.setLookupSQL(lookupMethods.getCompany());
								txtCoID.setPreferredSize(new Dimension(50, 0));
								txtCoID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if (data != null) {
											txtCoID.setValue(data[0].toString());
											txtCo.setText(data[2].toString());
										}
									}
								});
								txtCoID.setValue("");
							}
						}
					}
					{
						txtCo = new JTextField("");
						company.add(txtCo, BorderLayout.CENTER);
						txtCo.setHorizontalAlignment(JTextField.CENTER);
						txtCo.setEditable(false);
					}
				}
			}
			{
				JPanel entity_type = new JPanel(new BorderLayout(5, 5));
				panOther.add(entity_type);
				{
					{
						JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5));
						entity_type.add(panLabel, BorderLayout.LINE_START);
						panLabel.setPreferredSize(new Dimension(150, 0));
						{
							{
								JLabel label = new JLabel("Type", JLabel.TRAILING);
								panLabel.add(label, BorderLayout.LINE_START);
								label.setHorizontalAlignment(JLabel.CENTER);
								label.setPreferredSize(new Dimension(200, 0));
							}
							{
								txtEntityTypeID = new _JLookup("");
								panLabel.add(txtEntityTypeID, BorderLayout.LINE_START);
								txtEntityTypeID.setReturnColumn(0);
								txtEntityTypeID.setHorizontalAlignment(_JLookup.CENTER);
								txtEntityTypeID.setLookupSQL(lookupMethods.getEntityType());
								txtEntityTypeID.setPreferredSize(new Dimension(50, 0));
								txtEntityTypeID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();

										if (data != null) {
											txtEntityTypeID.setValue(data[0].toString());
											txtEntityType.setText(data[1].toString());
										}
									}
								});
								txtEntityTypeID.setValue("");
							}
						}
					}
					{
						txtEntityType = new JTextField("");
						entity_type.add(txtEntityType, BorderLayout.CENTER);
						txtEntityType.setHorizontalAlignment(JTextField.CENTER);
						txtEntityType.setEditable(false);
					}
				}
			} 
		}
		return panOther;
	}

	private static void switcher(String strKey) {
		card.show(panelIO, strKey);
	}
	
}