package Accounting.Commissions;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import tablemodel.modelComm_agentCA_liquidation;
import tablemodel.modelComm_agentCA_list;
import tablemodel.modelComm_agentCommQualifier;
import tablemodel.modelComm_agentComm_schedule;
import tablemodel.modelComm_agentPromo_general;
import tablemodel.modelComm_agentPromo_perAcct;
import tablemodel.modelComm_cancCPF;
import tablemodel.modelComm_client_RelComm;
import tablemodel.modelComm_client_commSchedule;
import tablemodel.modelComm_client_list;
import tablemodel.modelComm_client_promo;
import tablemodel.modelPV_SubLedger;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Utilities.SalesAgent;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class CommissionInquiry extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Commission Inquiry";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlClient_CommSched;
	private JPanel pnlClient_Promo;
	private JPanel pnlClient_Rel_Commission;
	private JPanel pnlClient_Canc_CPF;
	private JPanel pnlbyClient;
	private JPanel pnlSubTable;
	private JPanel pnlbyAgent;
	private JPanel pnlbyClient_north;
	private JPanel pnlClientName;
	private JPanel pnlClientName_a;
	private JPanel pnlClientName_b;
	private JPanel pnlClientName_b_sub1;
	private JPanel pnlClientName_b_sub2;
	private JPanel pnlClientName_b_sub3;
	private JPanel pnlClientTable;
	private JPanel pnlClientDetails;
	private JPanel pnlClient_Canc_CPF_comm;
	private JPanel pnlClient_Canc_CPF_promo;
	private JPanel pnlbyAgent_north;
	private JPanel pnlbyAgent_center;
	private JPanel pnlbyAgent_south;
	private JPanel pnlAgent_north_sub;
	private JPanel pnlAgent_north_sub1;
	private JLabel lblAgentBrokerName;
	private JLabel lblOverridingAgent;
	private JLabel lblAgentDeptGroup;
	private JPanel pnlAgent_north_sub2;
	private JPanel pnlAgent_north_sub3;
	private JPanel pnlAgent_north_south;
	private JPanel pnlAgent_north_south1;
	private JPanel pnlAgent_north_suouth2;
	private JPanel pnlAgent_type_postn_a;
	private JPanel pnlAgent_type_postn_b;
	private JPanel pnlAgent_type_postn;
	private JPanel pnlAgent_center_sub;
	private JPanel pnlAgent_center_sub1;
	private JPanel pnlAgent_center_sub2;
	private JPanel pnlAgent_center_sub3;
	private JPanel pnlAgent_Promo;
	private JPanel pnlAgent_CA;
	private JPanel pnlAgent_Commission;
	private JPanel pnlAgent_Commission_sched;
	private JPanel pnlAgent_Commission_qualifier;
	private JPanel pnlAgent_Promo_perAcct;
	private JPanel pnlAgent_Promo_general;
	private JPanel pnlAgent_CA_list;
	private JPanel pnlAgent_CA_liqui;

	private JLabel lblClientName;
	private JLabel lblType;
	private JLabel lblPosition;
	private JLabel lblAgentAccountName;

	private _JLookup lookupAgentName;

	private modelPV_SubLedger modelJV_SL_total;
	private modelPV_SubLedger modelJV_account;
	private modelPV_SubLedger modelJV_SL;
	private modelPV_SubLedger modelJV_accounttotal;

	private modelComm_client_list modelClientTbl;
	private modelComm_client_commSchedule modelClient_CommSched;
	private modelComm_client_commSchedule modelClient_CommSched_total;
	private modelComm_client_promo modelClient_Promo;
	private modelComm_client_promo modelClient_Promo_total;
	private modelComm_client_RelComm modelClientRel_comm;
	private modelComm_client_RelComm modelClientRel_comm_total;
	private modelComm_cancCPF modelClient_Canc_CPF_comm;
	private modelComm_cancCPF modelClient_Canc_CPF_comm_total;
	private modelComm_cancCPF modelClient_Canc_CPF_promo;
	private modelComm_cancCPF modelClient_Canc_CPF_promo_total;
	private modelComm_client_list modelAgentAccount;
	private modelComm_agentComm_schedule modelAgent_CommSched;
	private modelComm_agentComm_schedule modelAgent_CommSched_total;
	private modelComm_agentCommQualifier modelAgent_CommQualif;
	private modelComm_agentPromo_perAcct modelAgent_Promo_perAcct;
	private modelComm_agentPromo_perAcct modelAgent_Promo_perAcct_total;
	private modelComm_agentPromo_general modelAgent_Promo_general;
	private modelComm_agentPromo_general modelAgent_Promo_general_total;
	private modelComm_agentCA_list modelAgent_CA_list;
	private modelComm_agentCA_list modelAgent_CA_list_total;
	private modelComm_agentCA_liquidation modelAgent_CA_liqui;
	private modelComm_agentCA_liquidation modelAgent_CA_liqui_total;

	private _JTabbedPane tabMain;
	private _JTabbedPane tabClientDetails;
	private _JTabbedPane tabClient_Canc_CPF;
	private _JTabbedPane tabAgent_south;
	private _JTabbedPane tabAgent_Promo;

	private _JTagLabel tagAgentName;

	private JButton btnCancel;
	private JButton btnEdit;
	private JButton btnQualify;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JScrollPaneMain scrollJV_SL;
	private _JScrollPaneMain scrollJV_account;
	private _JScrollPaneMain scrollClientTbl;
	private _JScrollPaneMain scrollClient_CommSched;
	private _JScrollPaneMain scrollClient_Promo;
	private _JScrollPaneMain scrollClientRel_comm;
	private _JScrollPaneMain scrollClient_Canc_CPF_comm;
	private _JScrollPaneMain scrollClient_Canc_CPF_promo;
	private _JScrollPaneMain scrollAgentAccount;
	private _JScrollPaneMain scrollAgent_CommSched;
	private _JScrollPaneMain scrollAgent_CommQualif;
	private _JScrollPaneMain scrollAgent_Promo_perAcct;
	private _JScrollPaneMain scrollAgent_Promo_general;
	private _JScrollPaneMain scrollAgent_CA_list;
	private _JScrollPaneMain scrollAgent_CA_liqui;

	private _JScrollPaneTotal scrollClient_CommSchedtotal;
	private _JScrollPaneTotal scrollClient_Promo_total;
	private _JScrollPaneTotal scrollClientRel_comm_total;
	private _JScrollPaneTotal scrollClient_Canc_CPF_comm_total;
	private _JScrollPaneTotal scrollClient_Canc_CPF_promo_total;
	private _JScrollPaneTotal scrollAgent_Promo_perAcct_total;
	private _JScrollPaneTotal scrollAgent_Promo_general_total;
	private _JScrollPaneTotal scrollAgent_CA_list_total;
	private _JScrollPaneTotal scrollAgent_CA_liqui_total;
	private _JScrollPaneTotal scrollAgent_CommSched_total;

	private JXTextField txtOverridingAgent;
	private JXTextField txtDeptGroup;
	private JXTextField txtType;
	private JXTextField txtPosition;
	private JXTextField txtAccountName;
	private JXTextField txtClientName;

	private JCheckBox chkCancAccounts;
	private JCheckBox chkCancAccounts_clientlist;

	private _JTableMain tblJV_SL;
	private _JTableMain tblJV_account;
	private _JTableMain tblClientList;
	private _JTableMain tblClient_CommSched;
	private _JTableMain tblClient_Promo;
	private _JTableMain tblClientRel_comm;
	private _JTableMain tblClient_Canc_CPF_comm;
	private _JTableMain tblClient_Canc_CPF_promo;
	private _JTableMain tblAgentAccount;
	private _JTableMain tblAgent_CommSched;
	private _JTableMain tblAgent_Promo_perAcct;
	private _JTableMain tblAgent_Promo_general;
	private _JTableMain tblAgent_CommQualif;
	private _JTableMain tblAgent_CA_list;
	private _JTableMain tblAgent_CA_liqui;

	private JList rowHeaderJV_SL;
	private JList rowHeaderJV_account;
	private JList rowHeaderClientTbl;
	private JList rowHeaderClient_CommSched;
	private JList rowHeaderClient_Promo;
	private JList rowHeaderClientRel_comm;
	private JList rowHeaderClient_Canc_CPF_comm;
	private JList rowHeaderClient_Canc_CPF_promo;
	private JList rowHeaderAgentAccount;
	private JList rowHeaderAgent_CommSched;
	private JList rowHeaderAgent_CommQualif;
	private JList rowHeaderAgent_Promo_general;
	private JList rowHeaderAgent_Promo_perAcct;
	private JList rowHeaderAgent_CA_list;
	private JList rowHeaderAgent_CA_liqui;

	private _JTableTotal tblJV_SLtotal;
	private _JTableTotal tblClient_CommSched_total;
	private _JTableTotal tblClient_Promo_total;
	private _JTableTotal tblClientRel_comm_total;
	private _JTableTotal tblClient_Canc_CPF_total;
	private _JTableTotal tblClient_Canc_CPF_comm_total;
	private _JTableTotal tblClient_Canc_CPF_promo_total;
	private _JTableTotal tblAgent_CommSched_total;
	private _JTableTotal tblAgent_Promo_perAcct_total;
	private _JTableTotal tblAgent_Promo_general_total;
	private _JTableTotal tblAgent_CA_list_total;
	private _JTableTotal tblAgent_CA_liqui_total;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);

	String co_id = "";
	String company = "";
	String company_logo;

	String agent_id = "";
	String pbl_id = "";
	String seq_no = "";
	String entity_id = "";
	String entity_name = "";
	String comm_no = "";
	String description = "";
	String proj_id = "";
	Double wTaxrate = null;

	String div_id = "";
	String sales_grp_id = "";

	private JPanel pnlAgent_cancCPF;
	private _JScrollPaneMain scrollAgent_CancCPF_list;
	private _JTableMain tblAgent_CancCPF_list;
	private JList rowHeaderAgent_CancCPF_list;
	private _JScrollPaneTotal scrollAgent_CancCPF_list_total;
	private modelComm_cancCPF modelAgent_CancCPF_list;
	private modelComm_cancCPF modelAgent_CancCPF_list_total;
	private _JTableTotal tblAgent_CancCPF_list_total;

	private JPopupMenu menu;
	private JPopupMenu menu2;
	private JMenuItem mniopenRPLF;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenJV;
	private JMenuItem mniopenCPF;
	private JMenuItem mniOpenAgentInfo;
	private JSplitPane splitPanel_A;
	private JSplitPane splitPanel_B;
	private JSplitPane splitPanel_C;
	private _JScrollPaneTotal scrollClientTbl_total;
	private modelComm_client_list modelClientTbl_total;
	private _JTableTotal tblClientList_total;
	private _JScrollPaneTotal scrollAgentAccount_total;
	private modelComm_client_list modelAgentAccount_total;
	private _JTableTotal tblAgentAccount_total;

	public CommissionInquiry() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CommissionInquiry(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public CommissionInquiry(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces._GUI#initGUI()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {

		{
			menu = new JPopupMenu("Popup");
			mniOpenAgentInfo = new JMenuItem("Open Agent Details");
			menu.add(mniOpenAgentInfo);
			mniOpenAgentInfo.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
			mniOpenAgentInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openAgentInfo();
				}
			});

		}
		{
			menu2 = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenPV = new JMenuItem("Open Payable Voucher");
			mniopenJV = new JMenuItem("Open Journal Voucher");
			mniopenCPF = new JMenuItem("Commission Payout Form (CPF)");
			menu2.add(mniopenRPLF);
			menu2.add(mniopenPV);
			menu2.add(mniopenJV);
			menu2.add(mniopenCPF);
			mniopenRPLF.setEnabled(true);
			mniopenPV.setEnabled(true);
			mniopenJV.setEnabled(true);
			mniopenCPF.setEnabled(true);

			mniopenRPLF.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					openDRF();
				}
			});
			mniopenPV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// openPV();
				}
			});
			mniopenJV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// openJV();
				}
			});
			mniopenJV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// openCPF();
				}
			});

		}

		this.setLayout(new BorderLayout(5, 5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(978, 601));
		this.setBounds(0, 0, 978, 601);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlSubTable.setBorder(lineBorder);

			tabMain = new _JTabbedPane();
			pnlSubTable.add(tabMain, BorderLayout.CENTER);

			{
				pnlbyAgent = new JPanel(new BorderLayout());
				tabMain.addTab("by Selling Agent / Broker", null, pnlbyAgent, null);

				{
					pnlbyAgent_north = new JPanel();
					pnlbyAgent.add(pnlbyAgent_north, BorderLayout.NORTH);
					pnlbyAgent_north.setLayout(new BorderLayout(5, 5));
					pnlbyAgent_north.setBorder(lineBorder);
					pnlbyAgent_north.setPreferredSize(new java.awt.Dimension(1040, 100));
					// pnlbyAgent_north.setBorder(JTBorderFactory.createTitleBorder("Agent
					// / Broker"));

					{
						pnlAgent_north_sub = new JPanel(new BorderLayout(0, 5));
						pnlbyAgent_north.add(pnlAgent_north_sub, BorderLayout.NORTH);
						pnlAgent_north_sub.setPreferredSize(new java.awt.Dimension(1038, 40));
						pnlAgent_north_sub.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

						{
							pnlAgent_north_sub1 = new JPanel(new GridLayout(1, 1, 0, 0));
							pnlAgent_north_sub.add(pnlAgent_north_sub1, BorderLayout.WEST);
							pnlAgent_north_sub1.setPreferredSize(new java.awt.Dimension(141, 79));

							{
								lblAgentBrokerName = new JLabel("Agent/Broker Name :", JLabel.TRAILING);
								pnlAgent_north_sub1.add(lblAgentBrokerName);
								lblAgentBrokerName.setEnabled(true);
								lblAgentBrokerName.setPreferredSize(new java.awt.Dimension(173, 41));
								lblAgentBrokerName.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
								lblAgentBrokerName.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
							}
						}
						{
							pnlAgent_north_sub2 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlAgent_north_sub.add(pnlAgent_north_sub2, BorderLayout.CENTER);
							pnlAgent_north_sub2.setPreferredSize(new java.awt.Dimension(168, 40));
							pnlAgent_north_sub2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

							setUserDivDept();

							{
								lookupAgentName = new _JLookup(null, "Agents / Brokers", 2, 2);
								pnlAgent_north_sub2.add(lookupAgentName);
								lookupAgentName.setBounds(20, 27, 20, 25);
								lookupAgentName.setReturnColumn(0);
								lookupAgentName.setEnabled(true);
								lookupAgentName.setFilterName(true);
								lookupAgentName.setEditable(true);
								lookupAgentName.setLookupSQL(getAgent_list());
								lookupAgentName.addMouseListener(new PopupTriggerListener_panel());
								lookupAgentName.setPreferredSize(new java.awt.Dimension(126, 26));
								lookupAgentName.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											// refresh_fields();
											agent_id = (String) data[0];
											String agent_name = (String) data[1];
											String overriding_agent = (String) data[2];
											String dept_group = (String) data[5];
											String type = (String) data[4];
											String position = (String) data[3];

											tagAgentName.setTag(agent_name);
											txtOverridingAgent.setText(overriding_agent);
											txtDeptGroup.setText(dept_group);
											txtType.setText(type);
											txtPosition.setText(position);

											displayAgent_ClientList(modelAgentAccount, rowHeaderAgentAccount,
													(String) data[0], "A", tblAgentAccount, modelAgentAccount_total);
											displayClientPromo(modelAgent_Promo_perAcct, rowHeaderAgent_Promo_perAcct,
													modelAgent_Promo_perAcct_total, agent_id);
											displayClientCashAdvance(modelAgent_CA_list, rowHeaderAgent_CA_list,
													modelAgent_CA_list_total, agent_id);
											displayAgentCanceledCommission(modelAgent_CancCPF_list,
													rowHeaderAgent_CancCPF_list, modelAgent_CancCPF_list_total,
													agent_id);

											// reset
											// CA
											// Liquidation
											// Table
											FncTables.clearTable(modelAgent_CA_liqui);
											FncTables.clearTable(modelAgent_CA_liqui_total);
											rowHeaderAgent_CA_liqui = tblAgent_CA_liqui.getRowHeader22();
											scrollAgent_CA_liqui.setRowHeaderView(rowHeaderAgent_CA_liqui);
											modelAgent_CA_liqui_total.addRow(
													new Object[] { "Total", null, null, new BigDecimal(0.00), null });
										}
									}
								});
							}
						}
						{
							pnlAgent_north_sub3 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlAgent_north_sub.add(pnlAgent_north_sub3, BorderLayout.EAST);
							pnlAgent_north_sub3.setPreferredSize(new java.awt.Dimension(707, 40));
							pnlAgent_north_sub3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
							{
								tagAgentName = new _JTagLabel("[ ]");
								pnlAgent_north_sub3.add(tagAgentName);
								tagAgentName.setBounds(209, 27, 700, 22);
								tagAgentName.setEnabled(true);
								tagAgentName.setPreferredSize(new java.awt.Dimension(27, 33));
								tagAgentName.addMouseListener(new PopupTriggerListener_panel());
							}
						}
					}
					{
						pnlAgent_north_south = new JPanel(new BorderLayout(0, 5));
						pnlbyAgent_north.add(pnlAgent_north_south, BorderLayout.CENTER);
						pnlAgent_north_south.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
						pnlAgent_north_south.setPreferredSize(new java.awt.Dimension(1038, 94));

						{
							pnlAgent_north_south1 = new JPanel(new GridLayout(2, 1, 0, 0));
							pnlAgent_north_south.add(pnlAgent_north_south1, BorderLayout.WEST);
							pnlAgent_north_south1.setPreferredSize(new java.awt.Dimension(145, 95));
							pnlAgent_north_south1.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
							{
								lblOverridingAgent = new JLabel("Overriding Agent :", JLabel.TRAILING);
								pnlAgent_north_south1.add(lblOverridingAgent);
								lblOverridingAgent.setEnabled(true);
							}
							{
								lblAgentDeptGroup = new JLabel("Dept / Group:", JLabel.TRAILING);
								pnlAgent_north_south1.add(lblAgentDeptGroup);
								lblAgentDeptGroup.setEnabled(true);
								lblAgentDeptGroup.setPreferredSize(new java.awt.Dimension(86, 40));
							}
						}
						{
							pnlAgent_north_suouth2 = new JPanel(new GridLayout(2, 1, 5, 10));
							pnlAgent_north_south.add(pnlAgent_north_suouth2, BorderLayout.CENTER);
							pnlAgent_north_suouth2.setPreferredSize(new java.awt.Dimension(342, 79));
							pnlAgent_north_suouth2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
							{
								txtOverridingAgent = new JXTextField("");
								pnlAgent_north_suouth2.add(txtOverridingAgent);
								txtOverridingAgent.setEnabled(false);
								txtOverridingAgent.setEditable(false);
								txtOverridingAgent.setBounds(120, 25, 300, 22);
								txtOverridingAgent.setHorizontalAlignment(JTextField.CENTER);
								txtOverridingAgent.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
							}
							{
								txtDeptGroup = new JXTextField("");
								pnlAgent_north_suouth2.add(txtDeptGroup);
								txtDeptGroup.setEnabled(false);
								txtDeptGroup.setEditable(false);
								txtDeptGroup.setBounds(120, 25, 300, 22);
								txtDeptGroup.setHorizontalAlignment(JTextField.CENTER);
								txtDeptGroup.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
							}
						}
						{
							pnlAgent_type_postn = new JPanel(new BorderLayout(0, 5));
							pnlAgent_north_south.add(pnlAgent_type_postn, BorderLayout.EAST);
							pnlAgent_type_postn.setPreferredSize(new java.awt.Dimension(552, 86));
							pnlAgent_type_postn.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));

							{
								pnlAgent_type_postn_a = new JPanel(new GridLayout(2, 1, 0, 0));
								pnlAgent_type_postn.add(pnlAgent_type_postn_a, BorderLayout.WEST);
								pnlAgent_type_postn_a.setPreferredSize(new java.awt.Dimension(79, 94));
								pnlAgent_type_postn_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
								{
									lblType = new JLabel("Type :", JLabel.TRAILING);
									pnlAgent_type_postn_a.add(lblType);
									lblType.setEnabled(true);
									lblType.setPreferredSize(new java.awt.Dimension(86, 40));
								}
								{
									lblPosition = new JLabel("Position :", JLabel.TRAILING);
									pnlAgent_type_postn_a.add(lblPosition);
									lblPosition.setEnabled(true);
									lblPosition.setPreferredSize(new java.awt.Dimension(86, 40));
								}
							}
							{
								pnlAgent_type_postn_b = new JPanel(new GridLayout(2, 1, 5, 10));
								pnlAgent_type_postn.add(pnlAgent_type_postn_b, BorderLayout.CENTER);
								pnlAgent_type_postn_b.setPreferredSize(new java.awt.Dimension(342, 79));
								pnlAgent_type_postn_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
								{
									txtType = new JXTextField("");
									pnlAgent_type_postn_b.add(txtType);
									txtType.setEnabled(false);
									txtType.setEditable(false);
									txtType.setBounds(120, 25, 300, 22);
									txtType.setHorizontalAlignment(JTextField.CENTER);
									txtType.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
								}
								{
									txtPosition = new JXTextField("");
									pnlAgent_type_postn_b.add(txtPosition);
									txtPosition.setEnabled(false);
									txtPosition.setEditable(false);
									txtPosition.setBounds(120, 25, 300, 22);
									txtPosition.setHorizontalAlignment(JTextField.CENTER);
									txtPosition.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
								}
							}
						}
					}
				}

				splitPanel_B = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				pnlbyAgent.add(splitPanel_B);
				splitPanel_B.setOneTouchExpandable(true);
				splitPanel_B.setResizeWeight(.7d);

				{
					pnlbyAgent_center = new JPanel();
					// pnlbyAgent.add(pnlbyAgent_center,
					// BorderLayout.CENTER);
					splitPanel_B.add(pnlbyAgent_center, JSplitPane.LEFT);
					pnlbyAgent_center.setLayout(new BorderLayout(5, 5));
					pnlbyAgent_center.setBorder(lineBorder);
					pnlbyAgent_center.setPreferredSize(new java.awt.Dimension(1040, 225));
					// pnlbyAgent_center.setBorder(JTBorderFactory.createTitleBorder("Accounts
					// Handled"));

					{
						pnlAgent_center_sub = new JPanel(new BorderLayout(0, 5));
						pnlbyAgent_center.add(pnlAgent_center_sub, BorderLayout.NORTH);
						pnlAgent_center_sub.setPreferredSize(new java.awt.Dimension(1038, 33));
						pnlAgent_center_sub.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

						{
							pnlAgent_center_sub1 = new JPanel(new GridLayout(1, 1, 0, 0));
							pnlAgent_center_sub.add(pnlAgent_center_sub1, BorderLayout.WEST);
							pnlAgent_center_sub1.setPreferredSize(new java.awt.Dimension(141, 79));
							pnlAgent_center_sub1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

							{
								lblAgentAccountName = new JLabel("Account Name :", JLabel.TRAILING);
								pnlAgent_center_sub1.add(lblAgentAccountName);
								lblAgentAccountName.setEnabled(true);
								lblAgentAccountName.setPreferredSize(new java.awt.Dimension(173, 41));
								lblAgentAccountName.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
								lblAgentAccountName.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
							}
						}
						{
							pnlAgent_center_sub2 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlAgent_center_sub.add(pnlAgent_center_sub2, BorderLayout.CENTER);
							pnlAgent_center_sub2.setPreferredSize(new java.awt.Dimension(323, 35));
							pnlAgent_center_sub2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

							{
								txtAccountName = new JXTextField("");
								pnlAgent_center_sub2.add(txtAccountName);
								txtAccountName.setEnabled(true);
								txtAccountName.setEditable(true);
								txtAccountName.setBounds(120, 25, 300, 22);
								txtAccountName.setHorizontalAlignment(JTextField.LEFT);
								txtAccountName.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
								txtAccountName.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {

										checkAgentClientList();

									}
								});
							}
						}
						{
							pnlAgent_center_sub3 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlAgent_center_sub.add(pnlAgent_center_sub3, BorderLayout.EAST);
							pnlAgent_center_sub3.setPreferredSize(new java.awt.Dimension(556, 35));
							pnlAgent_center_sub3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
							{

								chkCancAccounts_clientlist = new JCheckBox("Cancelled Accounts");
								pnlAgent_center_sub3.add(chkCancAccounts_clientlist);
								chkCancAccounts_clientlist.setEnabled(true);
								chkCancAccounts_clientlist.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {
										boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

										if (selected) {
											displayAgent_ClientList(modelAgentAccount, rowHeaderAgentAccount,
													lookupAgentName.getText().trim(), "I", tblAgentAccount,
													modelAgentAccount_total);
										} else {
											displayAgent_ClientList(modelAgentAccount, rowHeaderAgentAccount,
													lookupAgentName.getText().trim(), "A", tblAgentAccount,
													modelAgentAccount_total);
										}
									}
								});

							}
						}
					}
					{
						scrollAgentAccount = new _JScrollPaneMain();
						pnlbyAgent_center.add(scrollAgentAccount, BorderLayout.CENTER);
						{
							modelAgentAccount = new modelComm_client_list();

							tblAgentAccount = new _JTableMain(modelAgentAccount);
							scrollAgentAccount.setViewportView(tblAgentAccount);
							tblAgentAccount.getColumnModel().getColumn(1).setPreferredWidth(40);
							tblAgentAccount.addMouseListener(this);
							tblAgentAccount.getColumnModel().getColumn(1).setPreferredWidth(40);
							tblAgentAccount.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
							tblAgentAccount.setColumnSelectionAllowed(true);
							tblAgentAccount.setRowSelectionAllowed(true);

							tblAgentAccount.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {
									clickTable();
								}

								public void keyPressed(KeyEvent e) {
									clickTable();
								}

							});
							tblAgentAccount.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									clickTable();
								}

								public void mouseReleased(MouseEvent e) {
									if (tblAgentAccount.rowAtPoint(e.getPoint()) == -1) {
									} else {
										tblAgentAccount.setCellSelectionEnabled(true);
									}
								}
							});
						}
						{
							rowHeaderAgentAccount = tblAgentAccount.getRowHeader22();
							scrollAgentAccount.setRowHeaderView(rowHeaderAgentAccount);
							scrollAgentAccount.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
							scrollAgentAccount.setPreferredSize(new java.awt.Dimension(1038, 124));
						}
						{
							scrollAgentAccount_total = new _JScrollPaneTotal(scrollAgentAccount);
							pnlbyAgent_center.add(scrollAgentAccount_total, BorderLayout.SOUTH);
							{
								modelAgentAccount_total = new modelComm_client_list();
								modelAgentAccount_total.addRow(new Object[] { "Totals", null, null, null, null,
										new BigDecimal(0.00), null, null, null, null, null });

								tblAgentAccount_total = new _JTableTotal(modelAgentAccount_total, tblAgentAccount);
								tblAgentAccount_total.setFont(dialog11Bold);
								scrollAgentAccount_total.setViewportView(tblAgentAccount_total);
								((_JTableTotal) tblAgentAccount_total).setTotalLabel(0);
							}
						}
					}
				}

				{
					pnlbyAgent_south = new JPanel();
					// pnlbyAgent.add(pnlbyAgent_south,
					// BorderLayout.SOUTH);
					splitPanel_B.add(pnlbyAgent_south, JSplitPane.RIGHT);
					pnlbyAgent_south.setLayout(new BorderLayout(5, 5));
					pnlbyAgent_south.setBorder(lineBorder);
					pnlbyAgent_south.setPreferredSize(new java.awt.Dimension(1040, 230));
					// pnlbyAgent_south.setBorder(JTBorderFactory.createTitleBorder("Commissio
					// Details"));

					{
						tabAgent_south = new _JTabbedPane();
						pnlbyAgent_south.add(tabAgent_south, BorderLayout.CENTER);
						tabAgent_south.setPreferredSize(new java.awt.Dimension(1038, 242));

						// start of
						// Commission
						// Schedule (by
						// agent)
						{
							pnlAgent_Commission = new JPanel(new BorderLayout());
							tabAgent_south.addTab("Commission Schedule", null, pnlAgent_Commission, null);
							pnlAgent_Commission.setPreferredSize(new java.awt.Dimension(971, 222));

							splitPanel_C = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
							pnlAgent_Commission.add(splitPanel_C);
							splitPanel_C.setOneTouchExpandable(true);
							splitPanel_C.setResizeWeight(.7d);

							{
								pnlAgent_Commission_sched = new JPanel(new BorderLayout(5, 5));
								splitPanel_C.add(pnlAgent_Commission_sched, JSplitPane.LEFT);
								// pnlAgent_Commission.add(pnlAgent_Commission_sched,
								// BorderLayout.CENTER);
								pnlAgent_Commission_sched.setPreferredSize(new java.awt.Dimension(550, 202));
								pnlAgent_Commission_sched.setBorder(lineBorder);
								pnlAgent_Commission_sched.setBorder(JTBorderFactory.createTitleBorder("Commission"));

								{
									scrollAgent_CommSched = new _JScrollPaneMain();
									pnlAgent_Commission_sched.add(scrollAgent_CommSched, BorderLayout.CENTER);
									{
										modelAgent_CommSched = new modelComm_agentComm_schedule();

										tblAgent_CommSched = new _JTableMain(modelAgent_CommSched);
										scrollAgent_CommSched.setViewportView(tblAgent_CommSched);
										tblAgent_CommSched.addMouseListener(this);
										tblAgent_CommSched.setColumnSelectionAllowed(true);
										tblAgent_CommSched.setSortable(false);
										tblAgent_CommSched.setRowSelectionAllowed(true);
										tblAgent_CommSched.getColumnModel().getColumn(1).setPreferredWidth(60);
										tblAgent_CommSched.getColumnModel().getColumn(2).setPreferredWidth(60);
										tblAgent_CommSched.getColumnModel().getColumn(3).setPreferredWidth(60);
										tblAgent_CommSched.getColumnModel().getColumn(4).setPreferredWidth(60);
										tblAgent_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
										tblAgent_CommSched.getColumnModel().getColumn(6).setPreferredWidth(60);
										tblAgent_CommSched.getColumnModel().getColumn(7).setPreferredWidth(60);
										tblAgent_CommSched.getColumnModel().getColumn(8).setPreferredWidth(60);
										tblAgent_CommSched.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {

											}

											public void keyPressed(KeyEvent e) {

											}

										});
										tblAgent_CommSched.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if (tblAgent_CommSched.rowAtPoint(e.getPoint()) == -1) {

												} else {
													tblAgent_CommSched.setCellSelectionEnabled(true);
												}

												int row = tblAgent_CommSched.getSelectedRow();
												String comm_type = tblAgent_CommSched.getValueAt(row, 0).toString()
														.trim();

												if (comm_type.equals("1st CA")) {
													comm_no = "1";
												} else if (comm_type.equals("1st CA")) {
													comm_no = "1";
												} else if (comm_type.equals("2nd CA")) {
													comm_no = "2";
												} else if (comm_type.equals("3rd CA")) {
													comm_no = "3";
												} else if (comm_type.equals("4th CA")) {
													comm_no = "4";
												} else if (comm_type.equals("Full Comm")) {
													comm_no = "FC";
												}

												displayClientCommissionConditions(modelAgent_CommQualif,
														rowHeaderAgent_CommQualif, agent_id, pbl_id, seq_no, comm_no);

												int rw = tblAgent_CommQualif.getModel().getRowCount();
												int x = 0;

//												int selected_row = tblClientList.getSelectedRow();
//												String entity_id = modelClientTbl.getValueAt(selected_row, 0).toString();
//												String proj_id = modelClientTbl.getValueAt(selected_row, 13).toString();
//												String pbl_id = modelClientTbl.getValueAt(selected_row, 8).toString();
//												String seq_no = modelClientTbl.getValueAt(selected_row, 9).toString();

												while (x < rw) {

													String func_id = tblAgent_CommQualif.getValueAt(x, 0).toString()
															.trim();



													//													if (Qualify_Commission.commQualified(CommissionInquiry.this.pbl_id, CommissionInquiry.this.seq_no,
													//															func_id) == true) {
													//														modelAgent_CommQualif.setValueAt("yes", x, 2);
													//													} else {
													//														modelAgent_CommQualif.setValueAt("no", x, 2);
													//													}

													if (Qualify_Commission.commQualified_v2(entity_id, proj_id ,pbl_id, seq_no,func_id) == true) {
														modelAgent_CommQualif.setValueAt("yes", x, 2);
													} else {
														modelAgent_CommQualif.setValueAt("no", x, 2);
													}
													x++;
												}
											}

											public void mouseReleased(MouseEvent e) {
												if (tblAgent_CommSched.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CommSched.setCellSelectionEnabled(true);
												}
											}
										});

									}
									{
										rowHeaderAgent_CommSched = tblAgent_CommSched.getRowHeader22();
										scrollAgent_CommSched.setRowHeaderView(rowHeaderAgent_CommSched);
										scrollAgent_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
									}
								}
								{
									scrollAgent_CommSched_total = new _JScrollPaneTotal(scrollAgent_CommSched);
									pnlAgent_Commission_sched.add(scrollAgent_CommSched_total, BorderLayout.SOUTH);
									{
										modelAgent_CommSched_total = new modelComm_agentComm_schedule();
										modelAgent_CommSched_total.addRow(new Object[] { "Total", new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), null, null, null, null, null, null, null });

										tblAgent_CommSched_total = new _JTableTotal(modelAgent_CommSched_total,
												tblAgent_CommSched);
										tblAgent_CommSched_total.setFont(dialog11Bold);
										scrollAgent_CommSched_total.setViewportView(tblAgent_CommSched_total);
										((_JTableTotal) tblAgent_CommSched_total).setTotalLabel(0);
									}
								}
							}
							{
								pnlAgent_Commission_qualifier = new JPanel(new BorderLayout(5, 5));
								// pnlAgent_Commission.add(pnlAgent_Commission_qualifier,
								// BorderLayout.EAST);
								splitPanel_C.add(pnlAgent_Commission_qualifier, JSplitPane.RIGHT);
								pnlAgent_Commission_qualifier.setPreferredSize(new java.awt.Dimension(368, 202));
								pnlAgent_Commission_qualifier.setBorder(lineBorder);
								pnlAgent_Commission_qualifier
								.setBorder(JTBorderFactory.createTitleBorder("Commission Qualifiers"));

								{
									scrollAgent_CommQualif = new _JScrollPaneMain();
									pnlAgent_Commission_qualifier.add(scrollAgent_CommQualif, BorderLayout.CENTER);
									{
										modelAgent_CommQualif = new modelComm_agentCommQualifier();

										tblAgent_CommQualif = new _JTableMain(modelAgent_CommQualif);
										scrollAgent_CommQualif.setViewportView(tblAgent_CommQualif);
										tblAgent_CommQualif.addMouseListener(this);
										tblAgent_CommQualif.getColumnModel().getColumn(0).setPreferredWidth(50);
										tblAgent_CommQualif.getColumnModel().getColumn(1).setPreferredWidth(240);
										tblAgent_CommQualif.getColumnModel().getColumn(2).setPreferredWidth(80);
										tblAgent_CommQualif.getColumnModel().getColumn(3).setPreferredWidth(80);
										tblAgent_CommQualif.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {
												tblAgent_CommQualif.packAll();
											}

											public void keyPressed(KeyEvent e) {
												tblAgent_CommQualif.packAll();
											}

										});
										tblAgent_CommQualif.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if (tblAgent_CommQualif.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CommQualif.setCellSelectionEnabled(true);
												}
											}

											public void mouseReleased(MouseEvent e) {
												if (tblAgent_CommQualif.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CommQualif.setCellSelectionEnabled(true);
												}
											}
										});

									}
									{
										rowHeaderAgent_CommQualif = tblAgent_CommQualif.getRowHeader22();
										scrollAgent_CommQualif.setRowHeaderView(rowHeaderAgent_CommQualif);
										scrollAgent_CommQualif.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
										scrollAgent_CommQualif.setPreferredSize(new java.awt.Dimension(505, 220));
									}
								}

							}
						}
						// end of Commission
						// Schedule (by
						// agent)

						// start of Promo
						// (by agent)
						{
							pnlAgent_Promo = new JPanel(new BorderLayout());
							tabAgent_south.addTab("             Promo               ", null, pnlAgent_Promo, null);
							pnlAgent_Promo.setPreferredSize(new java.awt.Dimension(1183, 365));

							tabAgent_Promo = new _JTabbedPane();
							pnlAgent_Promo.add(tabAgent_Promo, BorderLayout.CENTER);

							{
								pnlAgent_Promo_perAcct = new JPanel(new BorderLayout());
								tabAgent_Promo.addTab("        Per Account        ", null, pnlAgent_Promo_perAcct,
										null);
								pnlAgent_Promo_perAcct.setPreferredSize(new java.awt.Dimension(1183, 365));

								{
									scrollAgent_Promo_perAcct = new _JScrollPaneMain();
									pnlAgent_Promo_perAcct.add(scrollAgent_Promo_perAcct, BorderLayout.CENTER);
									{
										modelAgent_Promo_perAcct = new modelComm_agentPromo_perAcct();

										tblAgent_Promo_perAcct = new _JTableMain(modelAgent_Promo_perAcct);
										scrollAgent_Promo_perAcct.setViewportView(tblAgent_Promo_perAcct);
										tblAgent_Promo_perAcct.addMouseListener(this);
										tblAgent_Promo_perAcct.getColumnModel().getColumn(3).setPreferredWidth(60);
										tblAgent_Promo_perAcct.getColumnModel().getColumn(4).setPreferredWidth(60);
										tblAgent_Promo_perAcct.getColumnModel().getColumn(5).setPreferredWidth(60);
										tblAgent_Promo_perAcct.getColumnModel().getColumn(6).setPreferredWidth(60);
										tblAgent_Promo_perAcct.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {
												tblAgent_Promo_perAcct.packAll();
											}

											public void keyPressed(KeyEvent e) {
												tblAgent_Promo_perAcct.packAll();
											}
										});
										tblAgent_Promo_perAcct.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if (tblAgent_Promo_perAcct.rowAtPoint(e.getPoint()) == -1) {
													tblAgent_Promo_perAcct_total.clearSelection();
												} else {
													tblAgent_Promo_perAcct.setCellSelectionEnabled(true);
												}

											}

											public void mouseReleased(MouseEvent e) {
												if (tblAgent_Promo_perAcct.rowAtPoint(e.getPoint()) == -1) {
													tblAgent_Promo_perAcct_total.clearSelection();
												} else {
													tblAgent_Promo_perAcct.setCellSelectionEnabled(true);
												}
											}
										});

									}
									{
										rowHeaderAgent_Promo_perAcct = tblAgent_Promo_perAcct.getRowHeader22();
										scrollAgent_Promo_perAcct.setRowHeaderView(rowHeaderAgent_Promo_perAcct);
										scrollAgent_Promo_perAcct.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
									}
								}
								{
									scrollAgent_Promo_perAcct_total = new _JScrollPaneTotal(scrollAgent_Promo_perAcct);
									pnlAgent_Promo_perAcct.add(scrollAgent_Promo_perAcct_total, BorderLayout.SOUTH);
									{
										modelAgent_Promo_perAcct_total = new modelComm_agentPromo_perAcct();
										modelAgent_Promo_perAcct_total.addRow(new Object[] { "Total", null, null,
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), null, null, null, null, null });

										tblAgent_Promo_perAcct_total = new _JTableTotal(modelAgent_Promo_perAcct_total,
												tblAgent_Promo_perAcct);
										tblAgent_Promo_perAcct_total.setFont(dialog11Bold);
										scrollAgent_Promo_perAcct_total.setViewportView(tblAgent_Promo_perAcct_total);
										((_JTableTotal) tblAgent_Promo_perAcct_total).setTotalLabel(0);
									}
								}

							}
							{
								pnlAgent_Promo_general = new JPanel(new BorderLayout());
								tabAgent_Promo.addTab("        General        ", null, pnlAgent_Promo_general, null);
								pnlAgent_Promo_general.setPreferredSize(new java.awt.Dimension(1183, 365));

								{
									scrollAgent_Promo_general = new _JScrollPaneMain();
									pnlAgent_Promo_general.add(scrollAgent_Promo_general, BorderLayout.CENTER);
									{
										modelAgent_Promo_general = new modelComm_agentPromo_general();

										tblAgent_Promo_general = new _JTableMain(modelAgent_Promo_general);
										scrollAgent_Promo_general.setViewportView(tblAgent_Promo_general);
										tblAgent_Promo_general.addMouseListener(this);
										tblAgent_Promo_general.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {
												tblAgent_Promo_general.packAll();
											}

											public void keyPressed(KeyEvent e) {
												tblAgent_Promo_general.packAll();
											}
										});
										tblAgent_Promo_general.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if (tblAgent_Promo_general.rowAtPoint(e.getPoint()) == -1) {
													tblAgent_Promo_general_total.clearSelection();
												} else {
													tblAgent_Promo_general.setCellSelectionEnabled(true);
												}
											}

											public void mouseReleased(MouseEvent e) {
												if (tblAgent_Promo_general.rowAtPoint(e.getPoint()) == -1) {
													tblAgent_Promo_general_total.clearSelection();
												} else {
													tblAgent_Promo_general.setCellSelectionEnabled(true);
												}
											}
										});

									}
									{
										rowHeaderAgent_Promo_general = tblAgent_Promo_general.getRowHeader22();
										scrollAgent_Promo_general.setRowHeaderView(rowHeaderAgent_Promo_general);
										scrollAgent_Promo_general.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
									}
								}
								{
									scrollAgent_Promo_general_total = new _JScrollPaneTotal(scrollAgent_Promo_general);
									pnlAgent_Promo_general.add(scrollAgent_Promo_general_total, BorderLayout.SOUTH);
									{
										modelAgent_Promo_general_total = new modelComm_agentPromo_general();
										modelAgent_Promo_general_total.addRow(new Object[] { "Total", null, null,
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), null, null, null, null, null });

										tblAgent_Promo_general_total = new _JTableTotal(modelAgent_Promo_general_total,
												tblAgent_Promo_general);
										tblAgent_Promo_general_total.setFont(dialog11Bold);
										scrollAgent_Promo_general_total.setViewportView(tblAgent_Promo_general_total);
										((_JTableTotal) tblAgent_Promo_general_total).setTotalLabel(0);
									}
								}

							}
						}
						// end of Promo (by
						// agent)

						// start of Cash
						// Advances
						{
							pnlAgent_CA = new JPanel(new BorderLayout());
							tabAgent_south.addTab("    Cash Advances    ", null, pnlAgent_CA, null);
							pnlAgent_CA.setPreferredSize(new java.awt.Dimension(1183, 365));
							{
								pnlAgent_CA_list = new JPanel(new BorderLayout(5, 5));
								pnlAgent_CA.add(pnlAgent_CA_list, BorderLayout.CENTER);
								pnlAgent_CA_list.setPreferredSize(new java.awt.Dimension(454, 222));
								pnlAgent_CA_list.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

								{
									scrollAgent_CA_list = new _JScrollPaneMain();
									pnlAgent_CA_list.add(scrollAgent_CA_list, BorderLayout.CENTER);
									{
										modelAgent_CA_list = new modelComm_agentCA_list();

										tblAgent_CA_list = new _JTableMain(modelAgent_CA_list);
										scrollAgent_CA_list.setViewportView(tblAgent_CA_list);
										tblAgent_CA_list.addMouseListener(new PopupTriggerListener_panel2());
										tblAgent_CA_list.addMouseListener(this);
										tblAgent_CA_list.getColumnModel().getColumn(3).setPreferredWidth(60);
										tblAgent_CA_list.getColumnModel().getColumn(4).setPreferredWidth(60);
										tblAgent_CA_list.getColumnModel().getColumn(5).setPreferredWidth(60);
										// tblAgent_CA_list.hideColumns("O/S
										// CA");
										tblAgent_CA_list.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {
												tblAgent_CA_list.packAll();
											}

											public void keyPressed(KeyEvent e) {
												tblAgent_CA_list.packAll();
											}

										});
										tblAgent_CA_list.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if (tblAgent_CA_list.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CA_list.setCellSelectionEnabled(true);
												}
												int row = tblAgent_CA_list.getSelectedRow();
												String rplf_no = modelAgent_CA_list.getValueAt(row, 1).toString()
														.trim();
												displayClientCA_liquidation(modelAgent_CA_liqui,
														rowHeaderAgent_CA_liqui, modelAgent_CA_liqui_total, agent_id,
														rplf_no);
												tblAgent_CA_liqui.packAll();
											}

											public void mouseReleased(MouseEvent e) {
												if (tblAgent_CA_list.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CA_list.setCellSelectionEnabled(true);
												}
											}
										});

									}
									{
										rowHeaderAgent_CA_list = tblAgent_CA_list.getRowHeader22();
										scrollAgent_CA_list.setRowHeaderView(rowHeaderAgent_CA_list);
										scrollAgent_CA_list.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
									}
								}
								{
									scrollAgent_CA_list_total = new _JScrollPaneTotal(scrollAgent_CA_list);
									pnlAgent_CA_list.add(scrollAgent_CA_list_total, BorderLayout.SOUTH);
									{
										modelAgent_CA_list_total = new modelComm_agentCA_list();
										modelAgent_CA_list_total
										.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), null, null });

										tblAgent_CA_list_total = new _JTableTotal(modelAgent_CA_list_total,
												tblAgent_CA_list);
										tblAgent_CA_list_total.setFont(dialog11Bold);
										scrollAgent_CA_list_total.setViewportView(tblAgent_CA_list_total);
										((_JTableTotal) tblAgent_CA_list_total).setTotalLabel(0);
									}
								}
							}
							{
								pnlAgent_CA_liqui = new JPanel(new BorderLayout(5, 5));
								pnlAgent_CA.add(pnlAgent_CA_liqui, BorderLayout.EAST);
								pnlAgent_CA_liqui.setPreferredSize(new java.awt.Dimension(517, 222));
								pnlAgent_CA_liqui.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
								pnlAgent_CA_liqui.setBorder(lineBorder);
								pnlAgent_CA_liqui.setBorder(JTBorderFactory.createTitleBorder("Liquidation Breakdown"));

								{
									scrollAgent_CA_liqui = new _JScrollPaneMain();
									pnlAgent_CA_liqui.add(scrollAgent_CA_liqui, BorderLayout.CENTER);
									{
										modelAgent_CA_liqui = new modelComm_agentCA_liquidation();

										tblAgent_CA_liqui = new _JTableMain(modelAgent_CA_liqui);
										scrollAgent_CA_liqui.setViewportView(tblAgent_CA_liqui);
										tblAgent_CA_liqui.addMouseListener(this);
										tblAgent_CA_liqui.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {
												tblAgent_CA_liqui.packAll();
											}

											public void keyPressed(KeyEvent e) {
												tblAgent_CA_liqui.packAll();
											}

										});
										tblAgent_CA_liqui.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if (tblAgent_CA_liqui.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CA_liqui.setCellSelectionEnabled(true);
												}
											}

											public void mouseReleased(MouseEvent e) {
												if (tblAgent_CA_liqui.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CA_liqui.setCellSelectionEnabled(true);
												}
											}
										});

									}
									{
										rowHeaderAgent_CA_liqui = tblAgent_CA_liqui.getRowHeader22();
										scrollAgent_CA_liqui.setRowHeaderView(rowHeaderAgent_CA_liqui);
										scrollAgent_CA_liqui.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
									}
								}
								{
									scrollAgent_CA_liqui_total = new _JScrollPaneTotal(scrollAgent_CA_liqui);
									pnlAgent_CA_liqui.add(scrollAgent_CA_liqui_total, BorderLayout.SOUTH);
									{
										modelAgent_CA_liqui_total = new modelComm_agentCA_liquidation();
										modelAgent_CA_liqui_total.addRow(
												new Object[] { "Total", null, null, new BigDecimal(0.00), null });

										tblAgent_CA_liqui_total = new _JTableTotal(modelAgent_CA_liqui_total,
												tblAgent_CA_liqui);
										tblAgent_CA_liqui_total.setFont(dialog11Bold);
										scrollAgent_CA_liqui_total.setViewportView(tblAgent_CA_liqui_total);
										((_JTableTotal) tblAgent_CA_liqui_total).setTotalLabel(0);
									}
								}

							}
						}
						// end of CA (by
						// agent)

						// start of Canceled
						// CPF
						{
							pnlAgent_cancCPF = new JPanel(new BorderLayout());
							tabAgent_south.addTab("    Canceled CPF    ", null, pnlAgent_cancCPF, null);
							pnlAgent_cancCPF.setPreferredSize(new java.awt.Dimension(1183, 365));
							{
								{
									scrollAgent_CancCPF_list = new _JScrollPaneMain();
									pnlAgent_cancCPF.add(scrollAgent_CancCPF_list, BorderLayout.CENTER);
									{
										modelAgent_CancCPF_list = new modelComm_cancCPF();

										tblAgent_CancCPF_list = new _JTableMain(modelAgent_CancCPF_list);
										scrollAgent_CancCPF_list.setViewportView(tblAgent_CancCPF_list);
										tblAgent_CancCPF_list.addMouseListener(this);
										tblAgent_CancCPF_list.getColumnModel().getColumn(3).setPreferredWidth(60);
										tblAgent_CancCPF_list.getColumnModel().getColumn(4).setPreferredWidth(60);
										tblAgent_CancCPF_list.getColumnModel().getColumn(5).setPreferredWidth(60);
										tblAgent_CancCPF_list.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {
												tblAgent_CancCPF_list.packAll();
											}

											public void keyPressed(KeyEvent e) {
												tblAgent_CancCPF_list.packAll();
											}

										});
										tblAgent_CancCPF_list.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if (tblAgent_CancCPF_list.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CancCPF_list.setCellSelectionEnabled(true);
												}

											}

											public void mouseReleased(MouseEvent e) {
												if (tblAgent_CancCPF_list.rowAtPoint(e.getPoint()) == -1) {
												} else {
													tblAgent_CancCPF_list.setCellSelectionEnabled(true);
												}
											}
										});

									}
									{
										rowHeaderAgent_CancCPF_list = tblAgent_CancCPF_list.getRowHeader22();
										scrollAgent_CancCPF_list.setRowHeaderView(rowHeaderAgent_CancCPF_list);
										scrollAgent_CancCPF_list.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
									}
								}
								{
									scrollAgent_CancCPF_list_total = new _JScrollPaneTotal(scrollAgent_CancCPF_list);
									pnlAgent_cancCPF.add(scrollAgent_CancCPF_list_total, BorderLayout.SOUTH);
									{
										modelAgent_CancCPF_list_total = new modelComm_cancCPF();
										modelAgent_CancCPF_list_total.addRow(new Object[] { "Total", null, null,
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00) });

										tblAgent_CancCPF_list_total = new _JTableTotal(modelAgent_CancCPF_list_total,
												tblAgent_CancCPF_list);
										tblAgent_CancCPF_list_total.setFont(dialog11Bold);
										scrollAgent_CancCPF_list_total.setViewportView(tblAgent_CancCPF_list_total);
										((_JTableTotal) tblAgent_CancCPF_list_total).setTotalLabel(0);
									}

								}
							}
						}
						// end of Canceled
						// CPF
					}
				}
			}
			{
				pnlbyClient = new JPanel(new BorderLayout());
				tabMain.addTab("             by Client                ", null, pnlbyClient, null);
				pnlbyClient.setPreferredSize(new java.awt.Dimension(1183, 365));

				splitPanel_A = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				pnlbyClient.add(splitPanel_A);
				splitPanel_A.setOneTouchExpandable(true);
				splitPanel_A.setResizeWeight(.7d);

				pnlbyClient_north = new JPanel();
				splitPanel_A.add(pnlbyClient_north, JSplitPane.LEFT);
				pnlbyClient_north.setLayout(new BorderLayout(5, 5));
				pnlbyClient_north.setBorder(lineBorder);
				pnlbyClient_north.setPreferredSize(new java.awt.Dimension(1040, 225));
				pnlbyClient_north.setBorder(JTBorderFactory.createTitleBorder("Client List"));

				pnlClientName = new JPanel(new BorderLayout(5, 5));
				pnlbyClient_north.add(pnlClientName, BorderLayout.NORTH);
				pnlClientName.setPreferredSize(new java.awt.Dimension(1005, 42));

				pnlClientName_a = new JPanel(new GridLayout(1, 1, 5, 10));
				pnlClientName.add(pnlClientName_a, BorderLayout.WEST);
				pnlClientName_a.setPreferredSize(new java.awt.Dimension(92, 40));
				pnlClientName_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblClientName = new JLabel("Client Name :", JLabel.TRAILING);
					pnlClientName_a.add(lblClientName);
					lblClientName.setEnabled(true);
					lblClientName.setPreferredSize(new java.awt.Dimension(86, 40));
					lblClientName.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 12));
					lblClientName.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
				}

				pnlClientName_b = new JPanel(new BorderLayout(5, 5));
				pnlClientName.add(pnlClientName_b, BorderLayout.CENTER);
				pnlClientName_b.setPreferredSize(new java.awt.Dimension(918, 42));
				pnlClientName_b.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

				pnlClientName_b_sub1 = new JPanel(new GridLayout(1, 1, 5, 10));
				pnlClientName_b.add(pnlClientName_b_sub1, BorderLayout.WEST);
				pnlClientName_b_sub1.setPreferredSize(new java.awt.Dimension(235, 26));
				{
					txtClientName = new JXTextField("");
					pnlClientName_b_sub1.add(txtClientName);
					txtClientName.setEnabled(true);
					txtClientName.setEditable(true);
					txtClientName.setBounds(120, 25, 300, 22);
					txtClientName.setHorizontalAlignment(JTextField.LEFT);
					txtClientName.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
					txtClientName.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {

							checkAllClientList();

						}
					});
				}

				pnlClientName_b_sub2 = new JPanel(new GridLayout(1, 2, 5, 0));
				pnlClientName_b.add(pnlClientName_b_sub2, BorderLayout.CENTER);
				pnlClientName_b_sub2.setPreferredSize(new java.awt.Dimension(419, 26));

				{
					chkCancAccounts = new JCheckBox("Cancelled Accounts");
					pnlClientName_b_sub2.add(chkCancAccounts);
					chkCancAccounts.setEnabled(true);
					chkCancAccounts.setPreferredSize(new java.awt.Dimension(383, 26));
					chkCancAccounts.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

							if (selected) {
								displayAgent_ClientList(modelClientTbl, rowHeaderClientTbl, "", "I", tblClientList,
										modelClientTbl_total);
							} else {
								displayAgent_ClientList(modelClientTbl, rowHeaderClientTbl, "", "A", tblClientList,
										modelClientTbl_total);
							}
						}
					});
				}

				pnlClientName_b_sub3 = new JPanel(new GridLayout(1, 2, 5, 0));
				pnlClientName_b.add(pnlClientName_b_sub3, BorderLayout.EAST);
				pnlClientName_b_sub3.setPreferredSize(new java.awt.Dimension(310, 20));
				pnlClientName_b_sub3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					btnQualify = new JButton("Check Comm. Qualification");
					pnlClientName_b_sub3.add(btnQualify);
					btnQualify.setActionCommand("Qualify");
					btnQualify.addActionListener(this);
					btnQualify.setEnabled(true);
				}

				pnlClientTable = new JPanel(new BorderLayout(5, 5));
				pnlbyClient_north.add(pnlClientTable, BorderLayout.CENTER);
				pnlClientTable.setPreferredSize(new java.awt.Dimension(1005, 42));

				{
					scrollClientTbl = new _JScrollPaneMain();
					pnlClientTable.add(scrollClientTbl, BorderLayout.CENTER);
					{
						modelClientTbl = new modelComm_client_list();

						tblClientList = new _JTableMain(modelClientTbl);
						scrollClientTbl.setViewportView(tblClientList);
						tblClientList.addMouseListener(this);
						tblClientList.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								generateClientSchedule();
							}

							public void keyPressed(KeyEvent e) {
								generateClientSchedule();
							}

						});
						tblClientList.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblClientList.rowAtPoint(e.getPoint()) == -1) {
								} else {
									tblClientList.setCellSelectionEnabled(true);
								}
								generateClientSchedule();
							}

							public void mouseReleased(MouseEvent e) {
								if (tblClientList.rowAtPoint(e.getPoint()) == -1) {
								} else {
									tblClientList.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderClientTbl = tblClientList.getRowHeader22();
						scrollClientTbl.setRowHeaderView(rowHeaderClientTbl);
						scrollClientTbl.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
								FncTables.getRowHeader_Header());
					}
					{
						scrollClientTbl_total = new _JScrollPaneTotal(scrollClientTbl);
						pnlClientTable.add(scrollClientTbl_total, BorderLayout.SOUTH);
						{
							modelClientTbl_total = new modelComm_client_list();
							modelClientTbl_total
							.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00) });

							tblClientList_total = new _JTableTotal(modelClientTbl_total, tblClientList);
							tblClientList_total.setFont(dialog11Bold);
							scrollClientTbl_total.setViewportView(tblClientList_total);
							((_JTableTotal) tblClientList_total).setTotalLabel(0);
						}
					}

					displayAgent_ClientList(modelClientTbl, rowHeaderClientTbl, "", "A", tblClientList,
							modelClientTbl_total);
				}

				pnlClientDetails = new JPanel();
				// pnlSplit.add(pnlClientDetails,BorderLayout.CENTER);
				splitPanel_A.add(pnlClientDetails, JSplitPane.RIGHT);
				pnlClientDetails.setLayout(new BorderLayout(5, 5));
				pnlClientDetails.setBorder(lineBorder);
				pnlClientDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlClientDetails.setBorder(JTBorderFactory.createTitleBorder("Commission Details"));

				tabClientDetails = new _JTabbedPane();
				pnlClientDetails.add(tabClientDetails, BorderLayout.CENTER);

				// start of Commission Schedule (by
				// client)
				{
					pnlClient_CommSched = new JPanel(new BorderLayout());
					tabClientDetails.addTab("Commission Schedule", null, pnlClient_CommSched, null);
					pnlClient_CommSched.setPreferredSize(new java.awt.Dimension(1183, 365));

					{
						scrollClient_CommSched = new _JScrollPaneMain();
						pnlClient_CommSched.add(scrollClient_CommSched, BorderLayout.CENTER);
						{
							modelClient_CommSched = new modelComm_client_commSchedule();

							tblClient_CommSched = new _JTableMain(modelClient_CommSched);
							scrollClient_CommSched.setViewportView(tblClient_CommSched);
							tblClient_CommSched.addMouseListener(this);
							tblClient_CommSched.setSortable(false);
							tblClient_CommSched.getColumnModel().getColumn(4).setPreferredWidth(60);
							tblClient_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
							tblClient_CommSched.getColumnModel().getColumn(6).setPreferredWidth(60);
							tblClient_CommSched.getColumnModel().getColumn(7).setPreferredWidth(60);
							tblClient_CommSched.getColumnModel().getColumn(8).setPreferredWidth(60);
							tblClient_CommSched.getColumnModel().getColumn(9).setPreferredWidth(60);
							tblClient_CommSched.getColumnModel().getColumn(10).setPreferredWidth(60);
							tblClient_CommSched.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {
									tblClient_CommSched.packAll();
								}

								public void keyPressed(KeyEvent e) {
									tblClient_CommSched.packAll();
								}

							});
							tblClient_CommSched.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if (tblClient_CommSched.rowAtPoint(e.getPoint()) == -1) {
									} else {
										tblClient_CommSched.setCellSelectionEnabled(true);
									}

									int row = tblClient_CommSched.getSelectedRow();
									String comm_type = tblClient_CommSched.getValueAt(row, 3).toString().trim();

									if (comm_type.equals("1st CA")) {
										comm_no = "1";
									} else if (comm_type.equals("1st CA")) {
										comm_no = "1";
									} else if (comm_type.equals("2nd CA")) {
										comm_no = "2";
									} else if (comm_type.equals("3rd CA")) {
										comm_no = "3";
									} else if (comm_type.equals("4th CA")) {
										comm_no = "4";
									} else if (comm_type.equals("Full Comm")) {
										comm_no = "FC";
									}

								}

								public void mouseReleased(MouseEvent e) {
									if (tblClient_CommSched.rowAtPoint(e.getPoint()) == -1) {
									} else {
										tblClient_CommSched.setCellSelectionEnabled(true);
									}
								}
							});

						}
						{
							rowHeaderClient_CommSched = tblClient_CommSched.getRowHeader22();
							scrollClient_CommSched.setRowHeaderView(rowHeaderClient_CommSched);
							scrollClient_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
					{
						scrollClient_CommSchedtotal = new _JScrollPaneTotal(scrollClient_CommSched);
						pnlClient_CommSched.add(scrollClient_CommSchedtotal, BorderLayout.SOUTH);
						{
							modelClient_CommSched_total = new modelComm_client_commSchedule();
							modelClient_CommSched_total.addRow(new Object[] { "Total", null, null, null,
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
									new BigDecimal(0.00), null, null, null, null, null, null, null, null });

							tblClient_CommSched_total = new _JTableTotal(modelClient_CommSched_total,
									tblClient_CommSched);
							tblClient_CommSched_total.setFont(dialog11Bold);
							scrollClient_CommSchedtotal.setViewportView(tblClient_CommSched_total);
							((_JTableTotal) tblClient_CommSched_total).setTotalLabel(0);
						}
					}
				}
				// end of Commission Schedule (by
				// client)

				// start of Promo (by client)
				{
					pnlClient_Promo = new JPanel(new BorderLayout());
					tabClientDetails.addTab("             Promo               ", null, pnlClient_Promo, null);
					pnlClient_Promo.setPreferredSize(new java.awt.Dimension(1183, 365));

					{
						scrollClient_Promo = new _JScrollPaneMain();
						pnlClient_Promo.add(scrollClient_Promo, BorderLayout.CENTER);
						{
							modelClient_Promo = new modelComm_client_promo();

							tblClient_Promo = new _JTableMain(modelClient_Promo);
							scrollClient_Promo.setViewportView(tblClient_Promo);
							tblClient_Promo.addMouseListener(this);
							tblClient_Promo.getColumnModel().getColumn(3).setPreferredWidth(60);
							tblClient_Promo.getColumnModel().getColumn(4).setPreferredWidth(60);
							tblClient_Promo.getColumnModel().getColumn(5).setPreferredWidth(60);
							tblClient_Promo.getColumnModel().getColumn(6).setPreferredWidth(60);
							tblClient_Promo.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {
									tblClient_Promo.packAll();
								}

								public void keyPressed(KeyEvent e) {
									tblClient_Promo.packAll();
								}
							});
							tblClient_Promo.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if (tblClient_Promo.rowAtPoint(e.getPoint()) == -1) {
										tblClient_Promo_total.clearSelection();
									} else {
										tblClient_Promo.setCellSelectionEnabled(true);
									}
								}

								public void mouseReleased(MouseEvent e) {
									if (tblClient_Promo.rowAtPoint(e.getPoint()) == -1) {
										tblClient_Promo_total.clearSelection();
									} else {
										tblClient_Promo.setCellSelectionEnabled(true);
									}
								}
							});

						}
						{
							rowHeaderClient_Promo = tblClient_Promo.getRowHeader22();
							scrollClient_Promo.setRowHeaderView(rowHeaderClient_Promo);
							scrollClient_Promo.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
					{
						scrollClient_Promo_total = new _JScrollPaneTotal(scrollClient_Promo);
						pnlClient_Promo.add(scrollClient_Promo_total, BorderLayout.SOUTH);
						{
							modelClient_Promo_total = new modelComm_client_promo();
							modelClient_Promo_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00),
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null,
									null, null, null, null });

							tblClient_Promo_total = new _JTableTotal(modelClient_Promo_total, tblClient_Promo);
							tblClient_Promo_total.setFont(dialog11Bold);
							scrollClient_Promo_total.setViewportView(tblClient_Promo_total);
							((_JTableTotal) tblClient_Promo_total).setTotalLabel(0);
						}
					}
				}
				// end of Promo (by client)

				// start of Released Commission (by
				// client)
				{
					pnlClient_Rel_Commission = new JPanel(new BorderLayout());
					tabClientDetails.addTab("  Released Commission  ", null, pnlClient_Rel_Commission, null);
					pnlClient_Rel_Commission.setPreferredSize(new java.awt.Dimension(1183, 365));

					{
						scrollClientRel_comm = new _JScrollPaneMain();
						pnlClient_Rel_Commission.add(scrollClientRel_comm, BorderLayout.CENTER);
						{
							modelClientRel_comm = new modelComm_client_RelComm();

							tblClientRel_comm = new _JTableMain(modelClientRel_comm);
							scrollClientRel_comm.setViewportView(tblClientRel_comm);
							tblClientRel_comm.addMouseListener(this);
							tblClientRel_comm.getColumnModel().getColumn(4).setPreferredWidth(60);
							tblClientRel_comm.getColumnModel().getColumn(5).setPreferredWidth(60);
							tblClientRel_comm.getColumnModel().getColumn(6).setPreferredWidth(60);
							tblClientRel_comm.getColumnModel().getColumn(7).setPreferredWidth(60);
							tblClientRel_comm.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {
									tblClientRel_comm.packAll();
								}

								public void keyPressed(KeyEvent e) {
									tblClientRel_comm.packAll();
								}
							});
							tblClientRel_comm.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if (tblClientRel_comm.rowAtPoint(e.getPoint()) == -1) {
										tblJV_SLtotal.clearSelection();
									} else {
										tblClientRel_comm.setCellSelectionEnabled(true);
									}
								}

								public void mouseReleased(MouseEvent e) {
									if (tblClientRel_comm.rowAtPoint(e.getPoint()) == -1) {
										tblJV_SLtotal.clearSelection();
									} else {
										tblClientRel_comm.setCellSelectionEnabled(true);
									}
								}
							});

						}
						{
							rowHeaderClientRel_comm = tblClientRel_comm.getRowHeader22();
							scrollClientRel_comm.setRowHeaderView(rowHeaderClientRel_comm);
							scrollClientRel_comm.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
					{
						scrollClientRel_comm_total = new _JScrollPaneTotal(scrollClientRel_comm);
						pnlClient_Rel_Commission.add(scrollClientRel_comm_total, BorderLayout.SOUTH);
						{
							modelClientRel_comm_total = new modelComm_client_RelComm();
							modelClientRel_comm_total
							.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00),
									new BigDecimal(0.00), new BigDecimal(0.00), null, null, null });

							tblClientRel_comm_total = new _JTableTotal(modelClientRel_comm_total, tblClientRel_comm);
							tblClientRel_comm_total.setFont(dialog11Bold);
							scrollClientRel_comm_total.setViewportView(tblClientRel_comm_total);
							((_JTableTotal) tblClientRel_comm_total).setTotalLabel(0);
						}
					}
				}
				// end of Released Commission (by
				// client)

				// start of Canceled CPFs (by client)
				{
					pnlClient_Canc_CPF = new JPanel(new BorderLayout());
					tabClientDetails.addTab("        Canceled CPFs        ", null, pnlClient_Canc_CPF, null);
					pnlClient_Canc_CPF.setPreferredSize(new java.awt.Dimension(1183, 365));

					tabClient_Canc_CPF = new _JTabbedPane();
					pnlClient_Canc_CPF.add(tabClient_Canc_CPF, BorderLayout.CENTER);

					{
						pnlClient_Canc_CPF_comm = new JPanel(new BorderLayout());
						tabClient_Canc_CPF.addTab("        Commission        ", null, pnlClient_Canc_CPF_comm, null);
						pnlClient_Canc_CPF_comm.setPreferredSize(new java.awt.Dimension(1183, 365));

						{
							scrollClient_Canc_CPF_comm = new _JScrollPaneMain();
							pnlClient_Canc_CPF_comm.add(scrollClient_Canc_CPF_comm, BorderLayout.CENTER);
							{
								modelClient_Canc_CPF_comm = new modelComm_cancCPF();

								tblClient_Canc_CPF_comm = new _JTableMain(modelClient_Canc_CPF_comm);
								scrollClient_Canc_CPF_comm.setViewportView(tblClient_Canc_CPF_comm);
								tblClient_Canc_CPF_comm.addMouseListener(this);
								tblClient_Canc_CPF_comm.hideColumns("");
								tblClient_Canc_CPF_comm.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent evt) {
										tblClient_Canc_CPF_comm.packAll();
									}

									public void keyPressed(KeyEvent e) {
										tblClient_Canc_CPF_comm.packAll();
									}
								});
								tblClient_Canc_CPF_comm.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if (tblClient_Canc_CPF_comm.rowAtPoint(e.getPoint()) == -1) {
											tblClient_Canc_CPF_comm_total.clearSelection();
										} else {
											tblClient_Canc_CPF_comm.setCellSelectionEnabled(true);
										}
									}

									public void mouseReleased(MouseEvent e) {
										if (tblClient_Canc_CPF_comm.rowAtPoint(e.getPoint()) == -1) {
											tblClient_Canc_CPF_comm_total.clearSelection();
										} else {
											tblClient_Canc_CPF_comm.setCellSelectionEnabled(true);
										}
									}
								});

							}
							{
								rowHeaderClient_Canc_CPF_comm = tblClient_Canc_CPF_comm.getRowHeader22();
								scrollClient_Canc_CPF_comm.setRowHeaderView(rowHeaderClient_Canc_CPF_comm);
								scrollClient_Canc_CPF_comm.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
						{
							scrollClient_Canc_CPF_comm_total = new _JScrollPaneTotal(scrollClient_Canc_CPF_comm);
							pnlClient_Canc_CPF_comm.add(scrollClient_Canc_CPF_comm_total, BorderLayout.SOUTH);
							{
								modelClient_Canc_CPF_comm_total = new modelComm_cancCPF();
								modelClient_Canc_CPF_comm_total.addRow(
										new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

								tblClient_Canc_CPF_comm_total = new _JTableTotal(modelClient_Canc_CPF_comm_total,
										tblClient_Canc_CPF_comm);
								tblClient_Canc_CPF_comm_total.setFont(dialog11Bold);
								scrollClient_Canc_CPF_comm_total.setViewportView(tblClient_Canc_CPF_comm_total);
								((_JTableTotal) tblClient_Canc_CPF_comm_total).setTotalLabel(0);
							}
						}

					}
					{
						pnlClient_Canc_CPF_promo = new JPanel(new BorderLayout());
						tabClient_Canc_CPF.addTab("        Promo/Incentive        ", null, pnlClient_Canc_CPF_promo,
								null);
						pnlClient_Canc_CPF_promo.setPreferredSize(new java.awt.Dimension(1183, 365));

						{
							scrollClient_Canc_CPF_promo = new _JScrollPaneMain();
							pnlClient_Canc_CPF_promo.add(scrollClient_Canc_CPF_promo, BorderLayout.CENTER);
							{
								modelClient_Canc_CPF_promo = new modelComm_cancCPF();

								tblClient_Canc_CPF_promo = new _JTableMain(modelClient_Canc_CPF_promo);
								scrollClient_Canc_CPF_promo.setViewportView(tblClient_Canc_CPF_promo);
								tblClient_Canc_CPF_promo.addMouseListener(this);
								tblClient_Canc_CPF_promo.hideColumns("");
								tblClient_Canc_CPF_promo.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent evt) {
										tblClient_Canc_CPF_promo.packAll();
									}

									public void keyPressed(KeyEvent e) {
										tblClient_Canc_CPF_promo.packAll();
									}
								});
								tblClient_Canc_CPF_promo.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if (tblClient_Canc_CPF_promo.rowAtPoint(e.getPoint()) == -1) {
											tblClient_Canc_CPF_promo_total.clearSelection();
										} else {
											tblClient_Canc_CPF_promo.setCellSelectionEnabled(true);
										}
									}

									public void mouseReleased(MouseEvent e) {
										if (tblClient_Canc_CPF_promo.rowAtPoint(e.getPoint()) == -1) {
											tblClient_Canc_CPF_promo_total.clearSelection();
										} else {
											tblClient_Canc_CPF_promo.setCellSelectionEnabled(true);
										}
									}
								});

							}
							{
								rowHeaderClient_Canc_CPF_promo = tblClient_Canc_CPF_promo.getRowHeader22();
								scrollClient_Canc_CPF_promo.setRowHeaderView(rowHeaderClient_Canc_CPF_promo);
								scrollClient_Canc_CPF_promo.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
						{
							scrollClient_Canc_CPF_promo_total = new _JScrollPaneTotal(scrollClient_Canc_CPF_promo);
							pnlClient_Canc_CPF_promo.add(scrollClient_Canc_CPF_promo_total, BorderLayout.SOUTH);
							{
								modelClient_Canc_CPF_promo_total = new modelComm_cancCPF();
								modelClient_Canc_CPF_promo_total.addRow(
										new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
												new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

								tblClient_Canc_CPF_promo_total = new _JTableTotal(modelClient_Canc_CPF_promo_total,
										tblClient_Canc_CPF_promo);
								tblClient_Canc_CPF_promo_total.setFont(dialog11Bold);
								scrollClient_Canc_CPF_promo_total.setViewportView(tblClient_Canc_CPF_promo_total);
								((_JTableTotal) tblClient_Canc_CPF_promo_total).setTotalLabel(0);
							}
						}

					}
				}
				// end of Promo (by client)
			}

		}
		/*
		 * { pnlSouth = new JPanel(); pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		 * pnlSouth.setLayout(new BorderLayout());
		 * pnlSouth.setBorder(BorderFactory.createLineBorder(Color. GRAY));
		 * pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));
		 * 
		 * pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
		 * pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
		 * pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31)); {
		 * 
		 * { btnEdit = new JButton("Edit"); pnlSouthCenterb.add(btnEdit);
		 * btnEdit.setActionCommand("Edit"); btnEdit.addActionListener(this);
		 * btnEdit.setEnabled(false); } { btnCancel = new JButton("Cancel");
		 * pnlSouthCenterb.add(btnCancel); btnCancel.setActionCommand("Cancel");
		 * btnCancel.addActionListener(this); btnCancel.setEnabled(false); } } }
		 */
	}

	public void displayClientCommission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String agent_id, String pbl_id, String seq_no) {//

		wTaxrate = sql_wtaxRate(pbl_id, seq_no);

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		String sql = "select * \n" +
				"from view_broker_displayClientCommission('"+co_id+"', null, "+wTaxrate+", '"+ pbl_id+ "','" + seq_no + "', '"+agent_id+"'); ";

		/*
		 * "select\r\n" + "\r\n" +
		 * "( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" +
		 * "	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" +
		 * "	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" +
		 * "	case when trim(a.comm_type) = '4' then '4th CA' else\r\n" +
		 * "	case when trim(a.comm_type) = 'FC' then 'Full Comm' else '' end end end end end ) as comm_type,\r\n"
		 * + "a.prev_commamt,\r\n" + "a.comm_amt as sked_amt,\r\n" +
		 * "0.00 as adjustment,\r\n" + "a.comm_amt as gross_amt,\r\n" +
		 * "(case when g.cdf_no is not null then g.wtax_amt else (a.comm_amt-(case when h.vat_registered is true then (a.comm_amt/1.12)*.12 else 0.00 end))*"
		 * +wTaxrate+" end) as wtax_amt,\r\n" +
		 * "(case when g.cdf_no is not null then g.vat_amt else (case when h.vat_registered is true "
		 * + "	then (a.comm_amt/1.12)*.12 else 0.00 end) end) as vat,\r\n" +
		 * "(case when g.cdf_no is not null then g.caliq_amt else adv_amount end) as adv_amount,\r\n"
		 * + "(case when g.cdf_no is not null then g.applied_amt else " +
		 * "		(a.comm_amt-(a.comm_amt-(case when e.vat_registered is true then (a.comm_amt/1.12)*.12 else 0.00 end))*("
		 * +wTaxrate+")) \n" + "		end) as actual_amt ,\r\n" +
		 * "( case when g.applied_amt = 0.00 or (a.status = 'P' and coalesce(b.rplf_no,'') = '') then 'Released' else \n "
		 * + "	case when b.status_id = 'I' then 'Canceled' else" +
		 * "	case when status = 'A' and a.qualified = true then 'Voucher Preparation' else\r\n"
		 * +
		 * "	case when status = 'A' and a.qualified is null then 'Not Yet Qualified' else\r\n"
		 * +
		 * "	case when a.status = 'P' and (c.pv_no is null or cc.status_id in ('I', 'D')) then 'Voucher Preparation' else\r\n"
		 * +
		 * "	case when a.status = 'P' and c.pv_no is not null and cc.cv_no is null then 'Voucher on Process' else\r\n"
		 * +
		 * "	case when a.status = 'P' and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is null and cc.proc_id in (0,1,2) then 'For Check Signature' else \n"
		 * +
		 * "	case when a.status = 'P' and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is null and cc.proc_id in (4,5) then 'For Release' else \n"
		 * +
		 * "	case when a.status = 'P' and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is null and cc.proc_id in (6,7) then 'Released' else \n"
		 * +
		 * "	case when a.status in ('P','R') and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is not null and cc.proc_id in (6) then 'Released to Summit' else \n"
		 * +
		 * "	case when a.status in ('P','R') and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is not null and cc.proc_id = 7 then 'Released to Carmona' else \n"
		 * +
		 * "	case when b.status_id = 'R' then 'Released' end end end end end end end end end end end end) as status, \n"
		 * + "coalesce(a.release_date, null) as rel_sked,\r\n" +
		 * "(case when b.date_released is not null then  b.date_released else \n" +
		 * "	case when g.applied_amt = 0.00 and b.cdf_no is not null then coalesce(b.cdf_date, null) else null end end) as date_rlsd,\r\n"
		 * +
		 * "(case when a.cdf_no is null then a.old_cdfno else a.cdf_no end) as cdf_no,\r\n"
		 * + "b.rplf_no,\r\n" + "c.cv_no,\r\n" +
		 * "(case when b.status_id = 'I' then b.remarks else \n" +
		 * "a.remarks||';'||\r\n" +
		 * "	(case when a.override_code is not null then \r\n" +
		 * "	'Override by '||a.override_code||';'||a.override_date else '' end) end) \n"
		 * + "\r\n" + "from cm_schedule_dl a\r\n" +
		 * "left join cm_cdf_hd b on a.cdf_no = b.cdf_no \r\n" + //and a.agent_code =
		 * b.agent_code "left join rf_pv_header c on b.rplf_no = c.pv_no\r\n" +
		 * "left join rf_cv_header cc on c.cv_no = cc.cv_no\r\n" +
		 * "left join em_employee d on b.canceled_by = d.emp_code\r\n" +
		 * "left join rf_entity e on d.entity_id = e.entity_id\r\n" +
		 * "left join cm_cdf_dl g on b.cdf_no = g.cdf_no and a.tran_type = g.tran_type and a.pbl_id = g.pbl_id and a.comm_type = g.comm_type\n"
		 * + "left join rf_entity h on a.agent_code = h.entity_id " + " \r\n" + "\r\n" +
		 * "where a.pbl_id = '"+pbl_id+"' \n" + "and a.seq_no = "+seq_no+" \n" +
		 * "and a.agent_code = '"+agent_id+"' \n" + "and a.tran_type != 'BB' \n" +
		 * "and status != 'I' \n" + "order by a.comm_type \n\n" ;
		 */
		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalComm(modelMain, modelTotal);
		}

		else {
			modelAgent_CommSched_total = new modelComm_agentComm_schedule();
			modelAgent_CommSched_total.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblAgent_CommSched_total = new _JTableTotal(modelAgent_CommSched_total, tblAgent_CommSched);
			tblAgent_CommSched_total.setFont(dialog11Bold);
			scrollAgent_CommSched_total.setViewportView(tblAgent_CommSched_total);
			((_JTableTotal) tblAgent_CommSched_total).setTotalLabel(0);
		}

		tblAgent_CommSched.packAll();
		tblAgent_CommSched.getColumnModel().getColumn(1).setPreferredWidth(60);
		tblAgent_CommSched.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblAgent_CommSched.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblAgent_CommSched.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblAgent_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblAgent_CommSched.getColumnModel().getColumn(6).setPreferredWidth(60);
		tblAgent_CommSched.getColumnModel().getColumn(7).setPreferredWidth(60);
		tblAgent_CommSched.getColumnModel().getColumn(8).setPreferredWidth(60);

		adjustRowHeight_account(tblAgent_CommSched);
	}

	public void displayClientPromo(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String agent_id) {

		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		String SQL = "select * \n" + "from view_comm_inquiry_broker_promo \n" + "where agent_code = '" + agent_id + "'";

		System.out.println("Display broker promo: " + SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalPromo(modelMain, modelTotal);
		}

		else {
			modelAgent_Promo_perAcct_total = new modelComm_agentPromo_perAcct();
			modelAgent_Promo_perAcct_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblAgent_Promo_perAcct_total = new _JTableTotal(modelAgent_Promo_perAcct_total, tblAgent_Promo_perAcct);
			tblAgent_Promo_perAcct_total.setFont(dialog11Bold);
			scrollAgent_Promo_perAcct_total.setViewportView(tblAgent_Promo_perAcct_total);
			((_JTableTotal) tblAgent_Promo_perAcct_total).setTotalLabel(0);
		}

		tblAgent_Promo_perAcct.packAll();
		tblAgent_Promo_perAcct.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblAgent_Promo_perAcct.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblAgent_Promo_perAcct.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblAgent_Promo_perAcct.getColumnModel().getColumn(5).setPreferredWidth(60);

		adjustRowHeight_account(tblAgent_Promo_perAcct);
	}

	public void displayClientPromo_allAgents(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String pbl_id, String seq_no) { 

		wTaxrate = sql_wtaxRate(pbl_id, seq_no);

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		String sql = "--DISPLAY CLIENT'S AGENTS (ALL) PROMO\r\n" + "\r\n" + "select \r\n" +
				// "a.agent_code,\r\n" +
				"upper(trim(b.entity_name)),\r\n"
				+ "( case when g.position_abbv is null then '' else g.position_abbv end) as postn ,\r\n"
				+ "trim(c.promo_desc) as promo_name,\r\n" + "a.comm_amt,\r\n"
				+ "(case when i.cdf_no is not null then i.wtax_amt else a.comm_amt*" + wTaxrate + " end) as wtax,\r\n"
				+ "(case when i.cdf_no is not null then i.caliq_amt else a.adv_amount end) adv_amount,\r\n"
				+ "(case when i.cdf_no is not null then i.applied_amt else (a.comm_amt*(1-" + wTaxrate
				+ ") - a.adv_amount) end) as net_amt,\r\n"
				+ "( case when i.applied_amt = 0.00 or (a.status = 'P' and coalesce(d.rplf_no,'') = '') then 'Released' else \n "
				+ "	case when d.status_id = 'I' then 'Canceled' else"
				+ "	case when a.status = 'A' and a.qualified = true then 'Voucher Preparation' else\r\n"
				+ "	case when a.status = 'A' and a.qualified is null then 'Not Yet Qualified' else\r\n"
				+ "	case when a.status = 'P' and (e.pv_no is null or h.status_id in ('I', 'D')) then 'Voucher Preparation' else\r\n"
				+ "	case when a.status = 'P' and e.pv_no is not null and h.cv_no is null then 'Voucher on Process' else\r\n"
				+ "	case when a.status = 'P' and e.pv_no is not null and h.cv_no is not null and h.date_paid is null and h.proc_id in (0,1,2) then 'For Check Signature' else \n"
				+ "	case when a.status = 'P' and e.pv_no is not null and h.cv_no is not null and h.date_paid is null and h.proc_id in (4,5) then 'For Release' else \n"
				+ "	case when a.status = 'P' and e.pv_no is not null and h.cv_no is not null and h.date_paid is null and h.proc_id in (6,7) then 'Released' else \n"
				+ "	case when a.status in ('P','R') and e.pv_no is not null and h.cv_no is not null and h.date_paid is not null and h.proc_id in (6) then 'Released to Summit' else \n"
				+ "	case when a.status in ('P','R') and e.pv_no is not null and h.cv_no is not null and h.date_paid is not null and h.proc_id = 7 then 'Released to Carmona' else \n"
				+ "	case when d.status_id = 'R' then 'Released' end end end end end end end end end end end end) as status, \n"
				+ "(case when coalesce(d.rplf_no,'') = '' then a.tran_date else \n"
				+ "	case when d.date_released is not null then d.date_released else \n"
				+ "	case when i.applied_amt = 0.00 and d.cdf_no is not null then coalesce(d.cdf_date, null) else \n"
				+ "	case when a.release_date is null then h.date_paid else a.tran_date end end end end) as date_rlsd,\r\n"
				+ "(case when a.cdf_no is null then a.old_cdfno else a.cdf_no end) as cdf_no,\r\n" + "d.rplf_no,\r\n"
				+ "e.cv_no,\r\n" + "a.remarks\r\n" + "\r\n"
				+ "from (select * from cm_schedule_dl where status != 'I') a\r\n"
				+ "left join rf_entity b on a.agent_code = b.entity_id\r\n"
				+ "left join cm_promo_bonus c on a.promo_code = c.promo_code \r\n"
				+ "left join cm_cdf_hd d on a.cdf_no = d.cdf_no \r\n"
				+ "left join rf_pv_header e on d.rplf_no = e.pv_no and d.co_id = e.co_id\r\n"
				+ "left join (select * from cm_schedule_hd where status_id != 'I') f on a.pbl_id = f.pbl_id and a.seq_no=f.seq_no and a.agent_code=f.agent_code\r\n"
				+ "left join mf_sales_position g on f.orig_position  = g.position_code\r\n"
				+ "left join rf_cv_header h on e.cv_no = h.cv_no and e.co_id = h.co_id \r\n"
				+ "left join cm_cdf_dl i on d.cdf_no = i.cdf_no and a.tran_type = i.tran_type and a.pbl_id = i.pbl_id and a.comm_type = i.comm_type \n"
				+ "\r\n" + "where a.tran_type = 'BB'\r\n" + "and a.pbl_id = '" + pbl_id + "' \r\n" + "and a.seq_no = "
				+ seq_no + " \n" + "and a.status != 'I'  \r\n" + "and i.pbl_id = '" + pbl_id + "' ";
		// "and f.status_id = 'A' " ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalAllPromo(modelMain, modelTotal);
		}

		else {
			modelClient_Promo_total = new modelComm_client_promo();
			modelClient_Promo_total
			.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblClient_Promo_total = new _JTableTotal(modelClient_Promo_total, tblClient_Promo);
			tblClient_Promo_total.setFont(dialog11Bold);
			scrollClient_Promo_total.setViewportView(tblClient_Promo_total);
			((_JTableTotal) tblClient_Promo_total).setTotalLabel(0);
		}

		tblClient_Promo.packAll();
		// tblClient_Promo.getColumnModel().getColumn(2).setPreferredWidth(60);
		// tblClient_Promo.getColumnModel().getColumn(3).setPreferredWidth(60);
		// tblClient_Promo.getColumnModel().getColumn(4).setPreferredWidth(60);
		// tblClient_Promo.getColumnModel().getColumn(5).setPreferredWidth(60);

		adjustRowHeight_account(tblClient_Promo);
	}

	public void displayClientCommission_allAgents_released(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal, String pbl_id, String seq_no) {//

		wTaxrate = sql_wtaxRate(pbl_id, seq_no);

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		/*
		 * String sql = "select\r\n" + "\r\n" + "trim(e.entity_name) as agent_name,\r\n"
		 * + "round((d.gross_amt/v.net_sprice)*100,2) as rate,\r\n" +
		 * "f.position_abbv,\r\n" +
		 * "( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" +
		 * "	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" +
		 * "	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" +
		 * "	case when trim(a.comm_type) = '4' then '4th CA' else\r\n" +
		 * "	case when trim(a.comm_type) = 'FC' then 'Full Comm' else '' end end end end end ) as comm_type,\r\n"
		 * + "a.comm_amt as gross_amt,\r\n" +
		 * "(case when g.cdf_no is not null then g.wtax_amt else a.comm_amt*"
		 * +wTaxrate+" end) as wtax_amt,\r\n" +
		 * "(case when g.cdf_no is not null then g.caliq_amt else adv_amount end) as adv_amount,\r\n"
		 * +
		 * "(case when g.cdf_no is not null then g.applied_amt else  (case when a.actual_amt = 0 then a.comm_amt*(1-"
		 * +wTaxrate+") \n" + "	else a.actual_amt end) end) as actual_amt ,\r\n" +
		 * "coalesce(b.date_released, null),\r\n" +
		 * "(case when a.cdf_no is null then a.old_cdfno else a.cdf_no end) as cdf_no,\r\n"
		 * + "a.remarks\r\n" + "\r\n" +
		 * "from (select * from cm_schedule_dl where status != 'I') a \r\n" +
		 * "left join cm_cdf_hd b on a.cdf_no = b.cdf_no  \r\n" + //and a.agent_code =
		 * b.agent_code "left join rf_pv_header c on b.rplf_no = c.pv_no\r\n" +
		 * "join (select * from rf_cv_header where date_paid is not null) cc on c.cv_no = cc.cv_no\r\n"
		 * +
		 * "left join (select * from cm_schedule_hd where status_id != 'I') d on a.pbl_id = d.pbl_id and a.seq_no=d.seq_no and a.agent_code=d.agent_code\r\n"
		 * + "left join rf_entity e on d.agent_code = e.entity_id\r\n" +
		 * "left join mf_sales_position f on d.orig_position  = f.position_code\r\n" +
		 * "left join (select distinct on (pbl_id, seq_no) * from rf_client_price_history "
		 * +
		 * "	order by pbl_id, seq_no,tran_date desc) v on a.pbl_id = v.pbl_id and a.seq_no = v.seq_no and a.account_code = v.entity_id\r\n"
		 * +
		 * "left join cm_cdf_dl g on b.cdf_no = g.cdf_no and a.tran_type = g.tran_type and a.pbl_id = g.pbl_id and a.pbl_id = g.pbl_id and a.comm_type = g.comm_type \n"
		 * + "\r\n" + "\r\n" + "where a.tran_type != 'BB'\r\n" +
		 * "and a.pbl_id = '"+pbl_id+"' \r\n" + "and a.seq_no = "+seq_no+"  " +
		 * "and a.status in ( 'R' )  \n" + "and d.status_id = 'A' \r\n" + "\r\n" +
		 * "order by  e.entity_name, a.comm_type\r\n" + "" ;
		 */

		String strSQL = "select trim(e.entity_name) as agent_name, round((d.gross_amt / v.net_sprice) * 100, 2) as rate, f.position_abbv,\n"
				+ "(case when trim(a.comm_type) = '1' then '1st CA' when trim(a.comm_type) = '2' then '2nd CA' \n"
				+ "when trim(a.comm_type) = '3' then '3rd CA' when trim(a.comm_type) = '4' then '4th CA' \n"
				+ "when trim(a.comm_type) = 'FC' then 'Full Comm' else '' end) as comm_type, a.comm_amt as gross_amt,\n"
				+ "(case when g.cdf_no is not null then g.wtax_amt else a.comm_amt*0.15 end) as wtax_amt,\n"
				+ "(case when g.cdf_no is not null then g.caliq_amt else adv_amount end) as adv_amount,\n"
				+ "(case when g.cdf_no is not null then g.applied_amt else  (case when a.actual_amt = 0 then a.comm_amt*(1-0.15) else a.actual_amt end) end) as actual_amt,\n"
				+ "coalesce(b.date_released, null), (case when a.cdf_no is null then a.old_cdfno else a.cdf_no end) as cdf_no, a.remarks\n"
				+ "from (select * from cm_schedule_dl where status != 'I') a \n"
				+ "left join cm_cdf_hd b on a.cdf_no = b.cdf_no  \n"
				+ "left join rf_pv_header c on b.rplf_no = c.pv_no and b.co_id = c.co_id \n"
				+ "join (select * from rf_cv_header where date_paid is not null) cc on c.cv_no = cc.cv_no and c.co_id = cc.co_id\n"
				+ "left join (select * from cm_schedule_hd where status_id != 'I') d on a.pbl_id = d.pbl_id and a.seq_no = d.seq_no and a.agent_code = d.agent_code\n"
				+ "left join rf_entity e on d.agent_code = e.entity_id\n"
				+ "left join mf_sales_position f on d.orig_position  = f.position_code\n"
				+ "left join (select distinct on (pbl_id, seq_no) * from rf_client_price_history 	order by pbl_id, seq_no,tran_date desc) v on a.pbl_id = v.pbl_id and a.seq_no = v.seq_no and a.account_code = v.entity_id\n"
				+ "left join cm_cdf_dl g on b.cdf_no = g.cdf_no and a.tran_type = g.tran_type and a.pbl_id = g.pbl_id and a.pbl_id = g.pbl_id and a.comm_type = g.comm_type \n"
				+ "where a.tran_type != 'BB' and a.pbl_id = '" + pbl_id + "' and a.seq_no = " + seq_no
				+ " and d.status_id = 'A' \n"
				+ "and (case when g.applied_amt = 0.00 or (a.status = 'P' and coalesce(b.rplf_no,'') = '') then 'Released' \n"
				+ "when b.status_id = 'I' then 'Canceled' when status = 'A' and a.qualified = true then 'Voucher Preparation' \n"
				+ "when status = 'A' and a.qualified is null then 'Not Yet Qualified' when a.status = 'P' and (c.pv_no is null or cc.status_id in ('I', 'D')) then 'Voucher Preparation' \n"
				+ "when a.status = 'P' and c.pv_no is not null and cc.cv_no is null then 'Voucher on Process' when a.status = 'P' and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is null and cc.proc_id in (0,1,2) then 'For Check Signature' \n"
				+ "when a.status = 'P' and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is null and cc.proc_id in (4,5) then 'For Release' \n"
				+ "when a.status = 'P' and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is null and cc.status_id != 'D' and cc.proc_id in (6,7) then 'Released' \n"
				+ "when a.status = 'R' and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is null and cc.status_id = 'D' then 'Voucher Preparation' \n"
				+ "when a.status in ('P','R') and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is not null and cc.proc_id in (6) then 'Released' \n"
				+ "when a.status in ('P','R') and c.pv_no is not null and cc.cv_no is not null and cc.date_paid is not null and cc.proc_id = 7 then 'Released' \n"
				+ "when b.status_id = 'R' then 'Released' end) = 'Released'\n" + "order by  e.entity_name, a.comm_type";

		System.out.println("");
		System.out.println("Released Comm: " + strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalAllComm_released(modelMain, modelTotal);
		}

		else {
			modelClientRel_comm_total = new modelComm_client_RelComm();
			modelClientRel_comm_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), null, null, null });
			tblClientRel_comm_total = new _JTableTotal(modelClientRel_comm_total, tblClientRel_comm);
			tblClientRel_comm_total.setFont(dialog11Bold);
			scrollClientRel_comm_total.setViewportView(tblClientRel_comm_total);
			((_JTableTotal) tblClientRel_comm_total).setTotalLabel(0);
		}

		tblClientRel_comm.packAll();
		tblClientRel_comm.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblClientRel_comm.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblClientRel_comm.getColumnModel().getColumn(6).setPreferredWidth(60);
		tblClientRel_comm.getColumnModel().getColumn(7).setPreferredWidth(60);

		adjustRowHeight_account(tblClientRel_comm);
	}

	public void displayClientCashAdvance(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String agent_id) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		String sql = "--DISPLAY AGENT CASH ADVANCE  \n" +

				"select * from view_broker_agent_cash_adv_comm_inquiry('" + co_id + "','" + agent_id + "')";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalCA(modelMain, modelTotal);
		}

		else {
			modelAgent_CA_list_total = new modelComm_agentCA_list();
			modelAgent_CA_list_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblAgent_CA_list_total = new _JTableTotal(modelAgent_CA_list_total, tblAgent_CA_list);
			tblAgent_CA_list_total.setFont(dialog11Bold);
			scrollAgent_CA_list_total.setViewportView(tblAgent_CA_list_total);
			((_JTableTotal) tblAgent_CA_list_total).setTotalLabel(0);
		}

		tblAgent_CA_list.packAll();
		tblAgent_CA_list.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblAgent_CA_list.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblAgent_CA_list.getColumnModel().getColumn(5).setPreferredWidth(60);

		adjustRowHeight_account(tblAgent_CA_list);
	}

	public void displayClientCommissionConditions(DefaultTableModel modelMain, JList rowHeader, String agent_id,
			String pbl_id, String seq_no, String type) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		String sql =

				"--DISPLAY COMMISSION CONDITIONS \n" + "select\r\n" + "d.func_id, \n" + "d.func_desc, \r\n" + "'',"
						+ "null" +

						"\r\n" + "from cm_schedule_hd a \r\n"
						+ "left join cm_scheme_dl b on a.scheme_code = b.scheme_id\r\n"
						+ "left join cm_conditions_dl c on b.cond_id = c.cond_id\r\n"
						+ "left join cm_functions d on c.func_id = d.func_id\r\n" + "\r\n" + "where a.pbl_id = '"
						+ pbl_id + "' \r\n" + "and a.seq_no = " + seq_no + " \r\n" + "and a.agent_code = '" + agent_id
						+ "' \r\n" + "and b.status_id = 'A' \r\n" + "and c.status_id = 'A' \n"
						+ "and a.status_id = 'A' \r\n" + "and trim(b.comm_type) = '" + type + "' ";
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
		}

		tblAgent_CommQualif.packAll();

		adjustRowHeight_account(tblAgent_CommQualif);
	}

	public void displayClientCA_liquidation(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String agent_id, String ca_rplf_no) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		String sql =

				"--DISPLAY AGENT CASH ADVANCE LIQUIDAITON BDOWN\r\n" + "\r\n" + "select \r\n" + "\r\n" + "a.cdf_no,\r\n"
						+ "a.ca_liq_no,\r\n" + "a.jv_no,\r\n" + "a.liq_amt,\r\n" + "a.date_liq\r\n" + "\r\n"
						+ "from cm_ca_liquidation a\r\n" + "\r\n"
						+ "where a.cdf_no in ( select cdf_no from cm_cdf_hd where agent_code = '" + agent_id + "' )\r\n"
						+ "and a.ca_rplf_no = '" + ca_rplf_no + "'";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalCA_Liqui(modelMain, modelTotal);
		}

		else {
			modelAgent_CA_liqui_total = new modelComm_agentCA_liquidation();
			modelAgent_CA_liqui_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), null });

			tblAgent_CA_liqui_total = new _JTableTotal(modelAgent_CA_liqui_total, tblAgent_CA_liqui);
			tblAgent_CA_liqui_total.setFont(dialog11Bold);
			scrollAgent_CA_liqui_total.setViewportView(tblAgent_CA_liqui_total);
			((_JTableTotal) tblAgent_CA_liqui_total).setTotalLabel(0);
		}

		tblAgent_CA_liqui.packAll();

		adjustRowHeight_account(tblAgent_CA_liqui);
	}

	public void displayAgentCanceledCommission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String agent_id) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "select * \n" + 
				"from view_comm_inquiry_cancelled_cpf \n" + 
				"where agent_code = '"+agent_id+"'";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalCancComm(modelMain, modelTotal);
		} else {
			modelAgent_CancCPF_list_total = new modelComm_cancCPF();
			modelAgent_CancCPF_list_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

			tblAgent_CancCPF_list_total = new _JTableTotal(modelAgent_CancCPF_list_total, tblAgent_CancCPF_list);
			tblAgent_CancCPF_list_total.setFont(dialog11Bold);
			scrollAgent_CancCPF_list_total.setViewportView(tblAgent_CancCPF_list_total);
			((_JTableTotal) tblAgent_CancCPF_list_total).setTotalLabel(0);
		}

		tblAgent_CancCPF_list.packAll();
		tblAgent_CancCPF_list.getColumnModel().getColumn(0).setPreferredWidth(85);
		tblAgent_CancCPF_list.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(6).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(7).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(8).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(9).setPreferredWidth(60);
		tblAgent_CancCPF_list.getColumnModel().getColumn(10).setPreferredWidth(60);

		adjustRowHeight_account(tblAgent_CancCPF_list);
	}

	public void displayClientCanceledCommission(DefaultTableModel modelMain, JList rowHeader,
			DefaultTableModel modelTotal) {//

		wTaxrate = sql_wtaxRate(pbl_id, seq_no);

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		String sql = "--DISPLAYED CANCELED CPF \n" + "select\r\n" + "\r\n" + "trim(d.description),\r\n" + // 0
				"upper(trim(e.entity_name)), \n" + // 1
				"( case when trim(aa.comm_type) = '1' then '1st CA' else\r\n"
				+ "	case when trim(aa.comm_type) = '2' then '2nd CA' else\r\n"
				+ "	case when trim(aa.comm_type) = '3' then '3rd CA' else\r\n"
				+ "	case when trim(aa.comm_type) = '4' then '4th CA' else\r\n"
				+ "	case when trim(aa.comm_type) = 'FC' then 'Full Comm' else '' end end end end end ) as comm_type,\r\n"
				+ // 2
				"0.00 as prev_commamt,\r\n" + // 3
				"0.00 as sked_amt,\r\n" + // 4
				"0.00 as adjustment,\r\n" + // 5
				"(aa.applied_amt + aa.wtax_amt + aa.caliq_amt) as gross_amt,\r\n" + // 6
				"aa.wtax_amt as wtax_amt,\r\n" + // 7
				"0.00 as vat,\r\n" + // 8
				"aa.caliq_amt as adv_amount,\r\n" + // 9
				"aa.applied_amt as actual_amt ,\r\n" + // 10
				"'Canceled' as status, \n" + // 11
				"null as rel_sked,\r\n" + // 12
				"aa.cdf_no,\r\n" + // 13
				"b.rplf_no,\r\n" + // 14
				"c.cv_no,\r\n" + // 15
				"b.remarks,\r\n" + // 16
				"upper(trim(g.entity_name)),\r\n" + // 17
				"b.canceled_date \n" + // 18
				"\r\n" + "from cm_cdf_dl aa \r\n" + "left join cm_cdf_hd b on aa.cdf_no = b.cdf_no  \r\n"
				+ "left join rf_pv_header c on b.rplf_no = c.pv_no and aa.co_id = c.co_id\r\n"
				+ "left join mf_unit_info d on aa.pbl_id = d.pbl_id\r\n"
				+ "left join rf_entity e on aa.account_code = e.entity_id \n"
				+ "left join em_employee f on b.canceled_by = f.emp_code\r\n"
				+ "left join rf_entity g on f.entity_id = g.entity_id \n" + "\r\n" + "where aa.pbl_id = '" + pbl_id
				+ "' \r\n" + "and aa.seq_no = " + seq_no + "  " + "and b.status_id = 'I' " +
				// "and a.status != 'I' \r\n" +
				"and aa.tran_type = 'AA' " + "order by aa.comm_type  ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			// totalCancComm(modelMain, modelTotal);
		}

		else {
			modelClient_Canc_CPF_comm_total = new modelComm_cancCPF();
			modelClient_Canc_CPF_comm_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

			tblClient_Canc_CPF_comm_total = new _JTableTotal(modelClient_Canc_CPF_comm_total, tblClient_Canc_CPF_comm);
			tblClient_Canc_CPF_comm_total.setFont(dialog11Bold);
			scrollClient_Canc_CPF_comm_total.setViewportView(tblClient_Canc_CPF_comm_total);
			((_JTableTotal) tblClient_Canc_CPF_comm_total).setTotalLabel(0);
		}

		tblClient_Canc_CPF_comm.packAll();
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(0).setPreferredWidth(85);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(6).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(7).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(8).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(9).setPreferredWidth(60);
		tblClient_Canc_CPF_comm.getColumnModel().getColumn(10).setPreferredWidth(60);

		adjustRowHeight_account(tblClient_Canc_CPF_comm);
	}

	public void displayClientCanceledPromo(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		wTaxrate = sql_wtaxRate(pbl_id, seq_no);

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating
		// DefaultListModel
		// for
		// rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel
		// into rowHeader.

		String sql = "select\r\n" + "\r\n" + "trim(d.description),\r\n" + "upper(trim(e.entity_name)), \n"
				+ "( case when trim(aa.comm_type) = '1' then '1st CA' else\r\n"
				+ "	case when trim(aa.comm_type) = '2' then '2nd CA' else\r\n"
				+ "	case when trim(aa.comm_type) = '3' then '3rd CA' else\r\n"
				+ "	case when trim(aa.comm_type) = '4' then '4th CA' else\r\n"
				+ "	case when trim(aa.comm_type) = 'FC' then 'Full Comm' else '' end end end end end ) as comm_type,\r\n"
				+ "0.00 as prev_commamt,\r\n" + "0.00 as sked_amt,\r\n" + "0.00 as adjustment,\r\n"
				+ "(aa.applied_amt + aa.wtax_amt + aa.caliq_amt) as gross_amt,\r\n" + "aa.wtax_amt as wtax_amt,\r\n"
				+ "0.00 as vat,\r\n" + "aa.caliq_amt as adv_amount,\r\n" + "aa.applied_amt as actual_amt ,\r\n"
				+ "'Canceled' as status, \n" + "null as rel_sked,\r\n" + "aa.cdf_no,\r\n" + "b.rplf_no,\r\n"
				+ "c.cv_no,\r\n" + "b.remarks,\r\n" + "upper(trim(g.entity_name)),\r\n" + "b.canceled_date \n" + "\r\n"
				+ "from cm_cdf_dl aa \r\n" + "left join cm_cdf_hd b on aa.cdf_no = b.cdf_no  \r\n"
				+ "left join rf_pv_header c on b.rplf_no = c.pv_no and aa.co_id = c.co_id\r\n"
				+ "left join mf_unit_info d on aa.pbl_id = d.pbl_id\r\n"
				+ "left join rf_entity e on aa.account_code = e.entity_id \n"
				+ "left join em_employee f on b.canceled_by = f.emp_code\r\n"
				+ "left join rf_entity g on f.entity_id = g.entity_id \n" + "\r\n" + "where aa.pbl_id = '" + pbl_id
				+ "' \r\n" + "and aa.seq_no = " + seq_no + "  " + "and b.status_id = 'I' " +
				// "and a.status != 'I' \r\n" +
				"and aa.tran_type = 'BB' " + "order by aa.comm_type  ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalCancComm(modelMain, modelTotal);
		}

		else {
			modelClient_Canc_CPF_promo_total = new modelComm_cancCPF();
			modelClient_Canc_CPF_promo_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

			tblClient_Canc_CPF_promo_total = new _JTableTotal(modelClient_Canc_CPF_promo_total,
					tblClient_Canc_CPF_promo);
			tblClient_Canc_CPF_promo_total.setFont(dialog11Bold);
			scrollClient_Canc_CPF_promo_total.setViewportView(tblClient_Canc_CPF_promo_total);
			((_JTableTotal) tblClient_Canc_CPF_promo_total).setTotalLabel(0);
		}

		tblClient_Canc_CPF_promo.packAll();
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(0).setPreferredWidth(85);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(6).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(7).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(8).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(9).setPreferredWidth(60);
		tblClient_Canc_CPF_promo.getColumnModel().getColumn(10).setPreferredWidth(60);

		adjustRowHeight_account(tblClient_Canc_CPF_promo);
	}

	// Enable/Disable all components inside JPanel
	public void refresh_tablesMain() {//

		// reset table 1
		FncTables.clearTable(modelJV_account);
		FncTables.clearTable(modelJV_accounttotal);
		rowHeaderJV_account = tblJV_account.getRowHeader22();
		scrollJV_account.setRowHeaderView(rowHeaderJV_account);
		modelJV_accounttotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null,
				new BigDecimal(0.00), new BigDecimal(0.00), null });

		// reset table 2
		FncTables.clearTable(modelJV_SL);
		FncTables.clearTable(modelJV_SL_total);
		rowHeaderJV_SL = tblJV_SL.getRowHeader22();
		scrollJV_SL.setRowHeaderView(rowHeaderJV_SL);
		modelJV_SL_total.addRow(new Object[] { null, "Total", new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), null, null, null, null, null, null, null, null });

		// reset CA Liquidation Table
		FncTables.clearTable(modelAgent_CA_liqui);
		FncTables.clearTable(modelAgent_CA_liqui_total);
		rowHeaderAgent_CA_liqui = tblAgent_CA_liqui.getRowHeader22();
		scrollAgent_CA_liqui.setRowHeaderView(rowHeaderAgent_CA_liqui);
		modelAgent_CA_liqui_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), null });

	}

	public void enableButtons(Boolean a, Boolean b) {
		btnCancel.setEnabled(a);
		btnEdit.setEnabled(b);
	}

	// action performed
	@Override
	public void actionPerformed(ActionEvent e) {//

		if (e.getActionCommand().equals("Cancel")) {
			cancel();
		} // ok

		if (e.getActionCommand().equals("Edit")) {
			edit();
		} // ok

		if (e.getActionCommand().equals("Qualify")) {
			Qualify_Commission.qualifyComm(pbl_id, seq_no, comm_no);
		} // ok
	}

	public void mouseClicked(MouseEvent evt) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	private void cancel() {//

		refresh_tablesMain();
		{
			enableButtons(true, false);
		}
		lblClientName.setEnabled(true);

	}

	private void edit() {//

	}

	private void clickTable() {
		int row = tblAgentAccount.getSelectedRow();
		entity_id = tblAgentAccount.getValueAt(row, 0).toString();
		proj_id = tblAgentAccount.getValueAt(row, 13).toString();
		pbl_id = tblAgentAccount.getValueAt(row, 8).toString();
		seq_no = tblAgentAccount.getValueAt(row, 9).toString();
		entity_name = tblAgentAccount.getValueAt(row, 1).toString();
		description = tblAgentAccount.getValueAt(row, 3).toString();
		wTaxrate = sql_wtaxRate(pbl_id, seq_no);

		displayClientCommission(modelAgent_CommSched, rowHeaderAgent_CommSched, modelAgent_CommSched_total, agent_id,
				pbl_id, seq_no);
		pnlbyAgent_south.setBorder(JTBorderFactory
				.createTitleBorder("Commission Details (Buyer - " + entity_name + " | Unit - " + description + ")"));

		tblAgentAccount.packAll();

		System.out.println("");
		System.out.println("pbl_id: " + pbl_id);
		System.out.println("seq_no: " + seq_no);
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (lookupAgentName.getText().equals("")) {
					mniOpenAgentInfo.setEnabled(false);
				} else {
					mniOpenAgentInfo.setEnabled(true);
				}

			}
		}

		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (lookupAgentName.getText().equals("")) {
					mniOpenAgentInfo.setEnabled(false);
				} else {
					mniOpenAgentInfo.setEnabled(true);
				}

			}
		}
	}

	class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				int column = tblAgent_CA_list.getSelectedColumn();
				if (column == 1) {
					mniopenRPLF.setEnabled(true);
					mniopenPV.setEnabled(false);
					mniopenJV.setEnabled(false);
					mniopenCPF.setEnabled(false);
				}
			}
		}

		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				int column = tblAgent_CA_list.getSelectedColumn();
				if (column == 1) {
					mniopenRPLF.setEnabled(true);
					mniopenPV.setEnabled(false);
					mniopenJV.setEnabled(false);
					mniopenCPF.setEnabled(false);
				}
			}
		}
	}

	// select, lookup and get statements
	public String getAgent_list() {// used

		return

				"select \r\n" + "\r\n" + "trim(a.agent_id)  as \"Agent ID\"  ,\r\n" + "trim(b.entity_name) as \"Name\" ,\r\n"
				+ "trim(c.entity_name) as \"Override\"  ,\r\n" + "upper(trim(d.position_desc))  as \"Position\" ,\r\n"
				+ "upper(trim(e.type_desc)) as \"Type\"  ,\r\n" + "trim(f.dept_name)  as \"Dept/Group\" \r\n" + "\r\n"
				+ "from mf_sellingagent_info a\r\n" + "left join rf_entity b on a.agent_id = b.entity_id\r\n"
				+ "left join rf_entity c on a.override_id = c.entity_id\r\n"
				+ "left join mf_sales_position d on a.salespos_id  = d.position_code\r\n"
				+ "left join mf_sales_type e on a.salestype_id = e.type_code\r\n"
				+ "left join mf_department f on a.dept_id = f.dept_code\r\n" + "\r\n" + "where (case when '" + div_id
				+ "' in ('06','07','08','09','10','11','12') then sales_div_id = '" + div_id + "' "
				+ "	else (case when '" + div_id + "' in ('01','02','04') then true else sales_div_id = '" + div_id
				+ "' end) end)" + "order by b.entity_name ";

	}

	public String getClient_list() {// used

		return

				"select \r\n" + "\r\n" + "trim(a.agent_id)  as \"Agent ID\"  ,\r\n" + "trim(b.entity_name) as \"Name\" ,\r\n"
				+ "trim(c.entity_name) as \"Override\"  ,\r\n" + "upper(trim(d.position_desc))  as \"Position\" ,\r\n"
				+ "upper(trim(e.type_desc)) as \"Type\"  ,\r\n" + "trim(f.dept_name)  as \"Dept/Group\" \r\n" + "\r\n"
				+ "from rf_sold_units a\r\n" + "left join rf_entity b on a.agent_id = b.entity_id\r\n"
				+ "left join rf_entity c on a.override_id = c.entity_id\r\n"
				+ "left join mf_sales_position d on a.salespos_id  = d.position_code\r\n"
				+ "left join mf_sales_type e on a.salestype_id = e.type_code\r\n"
				+ "left join mf_department f on a.dept_id = f.dept_code\r\n" + "\r\n" + "where a.status_id = 'A'";

	}

	// table operations
	private void totalAllComm_released(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal gross_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);
		BigDecimal ca_liq = new BigDecimal(0.00);
		BigDecimal net_amt = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				gross_amt = gross_amt.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				gross_amt = gross_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 6)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}

			try {
				net_amt = net_amt.add(((BigDecimal) modelMain.getValueAt(x, 7)));
			} catch (NullPointerException e) {
				net_amt = net_amt.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, null, null, gross_amt, wtax_amt, ca_liq, net_amt, null, null });
	}

	private void totalAllComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal prev_comm = new BigDecimal(0.00);
		BigDecimal sked_amt = new BigDecimal(0.00);
		BigDecimal adjustment = new BigDecimal(0.00);
		BigDecimal gross_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);
		BigDecimal ca_liq = new BigDecimal(0.00);
		BigDecimal net_amt = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				prev_comm = prev_comm.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				prev_comm = prev_comm.add(new BigDecimal(0.00));
			}

			try {
				sked_amt = sked_amt.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				sked_amt = sked_amt.add(new BigDecimal(0.00));
			}

			try {
				adjustment = adjustment.add(((BigDecimal) modelMain.getValueAt(x, 6)));
			} catch (NullPointerException e) {
				adjustment = adjustment.add(new BigDecimal(0.00));
			}

			try {
				gross_amt = gross_amt.add(((BigDecimal) modelMain.getValueAt(x, 7)));
			} catch (NullPointerException e) {
				gross_amt = gross_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 8)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 9)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}

			try {
				net_amt = net_amt.add(((BigDecimal) modelMain.getValueAt(x, 10)));
			} catch (NullPointerException e) {
				net_amt = net_amt.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, null, null, prev_comm, sked_amt, adjustment, gross_amt,
				wtax_amt, ca_liq, net_amt, null, null });
	}

	private void totalComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal prev_comm = new BigDecimal(0.00);
		BigDecimal sked_amt = new BigDecimal(0.00);
		BigDecimal adjustment = new BigDecimal(0.00);
		BigDecimal gross_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);
		BigDecimal vat_amt = new BigDecimal(0.00);
		BigDecimal ca_liq = new BigDecimal(0.00);
		BigDecimal net_amt = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				prev_comm = prev_comm.add(((BigDecimal) modelMain.getValueAt(x, 1)));
			} catch (NullPointerException e) {
				prev_comm = prev_comm.add(new BigDecimal(0.00));
			}

			try {
				sked_amt = sked_amt.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e) {
				sked_amt = sked_amt.add(new BigDecimal(0.00));
			}

			try {
				adjustment = adjustment.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				adjustment = adjustment.add(new BigDecimal(0.00));
			}

			try {
				gross_amt = gross_amt.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				gross_amt = gross_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

			try {
				vat_amt = vat_amt.add(((BigDecimal) modelMain.getValueAt(x, 6)));
			} catch (NullPointerException e) {
				vat_amt = vat_amt.add(new BigDecimal(0.00));
			}

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 7)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}

			try {
				net_amt = net_amt.add(((BigDecimal) modelMain.getValueAt(x, 8)));
			} catch (NullPointerException e) {
				net_amt = net_amt.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", prev_comm, sked_amt, adjustment, gross_amt, wtax_amt, vat_amt, ca_liq,
				net_amt, null, null });
	}

	private void totalCancComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal prev_comm = new BigDecimal(0.00);
		BigDecimal sked_amt = new BigDecimal(0.00);
		BigDecimal adjustment = new BigDecimal(0.00);
		BigDecimal gross_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);
		BigDecimal vat_amt = new BigDecimal(0.00);
		BigDecimal ca_liq = new BigDecimal(0.00);
		BigDecimal net_amt = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				prev_comm = prev_comm.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				prev_comm = prev_comm.add(new BigDecimal(0.00));
			}

			try {
				sked_amt = sked_amt.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				sked_amt = sked_amt.add(new BigDecimal(0.00));
			}

			try {
				adjustment = adjustment.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				adjustment = adjustment.add(new BigDecimal(0.00));
			}

			try {
				gross_amt = gross_amt.add(((BigDecimal) modelMain.getValueAt(x, 6)));
			}

			catch (NullPointerException e) {
				gross_amt = gross_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 7)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

			try {
				vat_amt = vat_amt.add(((BigDecimal) modelMain.getValueAt(x, 8)));
			} catch (NullPointerException e) {
				vat_amt = vat_amt.add(new BigDecimal(0.00));
			}

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 9)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}

			try {
				net_amt = net_amt.add(((BigDecimal) modelMain.getValueAt(x, 10)));
			} catch (NullPointerException e) {
				net_amt = net_amt.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, null, prev_comm, sked_amt, adjustment, gross_amt, wtax_amt,
				vat_amt, ca_liq, net_amt, null, null });
	}

	private void totalPromo(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal gross_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);
		BigDecimal vat_amt = new BigDecimal(0.00);
		BigDecimal ca_liq = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				gross_amt = gross_amt.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e) {
				gross_amt = gross_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

			try {
				vat_amt = vat_amt.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				vat_amt = vat_amt.add(new BigDecimal(0.00));
			}

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, gross_amt, wtax_amt, vat_amt, ca_liq, null, null });

	}

	private void totalAllPromo(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal gross_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);
		BigDecimal vat_amt = new BigDecimal(0.00);
		BigDecimal ca_liq = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				gross_amt = gross_amt.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				gross_amt = gross_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

			try {
				vat_amt = vat_amt.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				vat_amt = vat_amt.add(new BigDecimal(0.00));
			}

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 6)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, null, gross_amt, wtax_amt, vat_amt, ca_liq, null, null });

	}

	private void totalCA(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal ca_liq = new BigDecimal(0.00);
		BigDecimal liqui = new BigDecimal(0.00);
		BigDecimal os_ca = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}

			try {
				liqui = liqui.add(((BigDecimal) modelMain.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				liqui = liqui.add(new BigDecimal(0.00));
			}

			try {
				os_ca = os_ca.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				os_ca = ca_liq.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { "Total", null, null, ca_liq, liqui, os_ca, null, null, null });

	}

	private void totalCA_Liqui(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal ca_liq = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				ca_liq = ca_liq.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				ca_liq = ca_liq.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { "Total", null, null, ca_liq, null });

	}

	private void totalCommClientList(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal comm_client_list = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				comm_client_list = comm_client_list.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				comm_client_list = comm_client_list.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { "Total", null, null, null, null, comm_client_list });

	}

	private void checkAgentClientList() {//

		int rw = tblAgentAccount.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String name = tblAgentAccount.getValueAt(x, 1).toString().toUpperCase(); // make
			// sure
			// it's
			// not
			// modelAgentAccount,
			// otherwise,
			// row
			// number
			// will
			// be
			// fixed
			String acct_name = txtAccountName.getText().trim().toUpperCase();
			Boolean match = name.indexOf(acct_name) > 0;
			Boolean start = name.startsWith(acct_name);
			Boolean end = name.endsWith(acct_name);

			if (match == true || start == true || end == true) {
				tblAgentAccount.setRowSelectionInterval(x, x);
				tblAgentAccount.changeSelection(x, 1, false, false);
				String pbl_id = tblAgentAccount.getValueAt(x, 8).toString();
				String seq_no = tblAgentAccount.getValueAt(x, 9).toString();
				displayClientCommission(modelAgent_CommSched, rowHeaderAgent_CommSched, modelAgent_CommSched_total,
						agent_id, pbl_id, seq_no);
				break;
			} else {
				tblAgentAccount.setRowSelectionInterval(0, 0);
				tblAgentAccount.changeSelection(0, 1, false, false);
				String pbl_id = tblAgentAccount.getValueAt(0, 8).toString();
				String seq_no = tblAgentAccount.getValueAt(0, 9).toString();
				displayClientCommission(modelAgent_CommSched, rowHeaderAgent_CommSched, modelAgent_CommSched_total,
						agent_id, pbl_id, seq_no);
			}

			x++;

		}

		int row = tblAgentAccount.getSelectedRow();
		entity_name = tblAgentAccount.getValueAt(row, 1).toString();
		description = tblAgentAccount.getValueAt(row, 3).toString();
		pnlbyAgent_south.setBorder(JTBorderFactory
				.createTitleBorder("Commission Details (Buyer - " + entity_name + " | Unit - " + description + ")"));
	}

	private void checkAllClientList() {//

		int rw = tblClientList.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String name = "";

			try {
				name = tblClientList.getValueAt(x, 1).toString().toUpperCase();
			} // make sure it's not modelAgentAccount,
			// otherwise, row number will be fixed
			catch (NullPointerException e) {
				name = "";
			}

			String acct_name = txtClientName.getText().trim().toUpperCase();
			Boolean match = name.indexOf(acct_name) > 0;
			Boolean start = name.startsWith(acct_name);
			Boolean end = name.endsWith(acct_name);

			if (match == true || start == true || end == true) {
				tblClientList.setRowSelectionInterval(x, x);
				tblClientList.changeSelection(x, 1, false, false);
				break;
			} else {
				tblClientList.setRowSelectionInterval(0, 0);
				tblClientList.changeSelection(0, 1, false, false);
			}

			x++;

		}
	}

	private void adjustRowHeight_account(_JTableMain table) {//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			table.setRowHeight(x, 22);
			x++;
		}

	}

	// others
	private void generateClientSchedule() {

		int row = tblClientList.getSelectedRow();
		entity_id = tblClientList.getValueAt(row, 0).toString();
		proj_id = tblClientList.getValueAt(row, 13).toString();
		pbl_id = tblClientList.getValueAt(row, 8).toString();
		seq_no = tblClientList.getValueAt(row, 9).toString();
		entity_name = tblClientList.getValueAt(row, 1).toString();
		description = tblClientList.getValueAt(row, 3).toString();
		
		System.out.printf("Display Value of entity_id: %s", entity_id);

		displayClient_All_agentsCommission(modelClient_CommSched, rowHeaderClient_CommSched, modelClient_CommSched_total, pbl_id, seq_no);
		displayClientPromo_allAgents(modelClient_Promo, rowHeaderClient_Promo, modelClient_Promo_total, pbl_id, seq_no);
		displayClientCommission_allAgents_released(modelClientRel_comm, rowHeaderClientRel_comm, modelClientRel_comm_total, pbl_id, seq_no);
		displayClientCanceledCommission(modelClient_Canc_CPF_comm, rowHeaderClient_Canc_CPF_comm, modelClient_Canc_CPF_comm_total);
		displayClientCanceledPromo(modelClient_Canc_CPF_promo, rowHeaderClient_Canc_CPF_promo, modelClient_Canc_CPF_promo_total);
		tblAgentAccount.packAll();

		pnlClientDetails.setBorder(JTBorderFactory
				.createTitleBorder("Commission Details (Buyer - " + entity_name + " | Unit - " + description + ")"));
	}

	private void openAgentInfo() {

		SalesAgent.agent_id = agent_id;

		SalesAgent agent_info = new SalesAgent();
		Home_JSystem.addWindow(agent_info);

		// SalesAgent.setAgent_mainDtls();
		SalesAgent.searchAgentList(SalesAgent.modelAgentList, SalesAgent.rowHeaderAgentList,
				SalesAgent.modelAgentList_total, agent_id);

		SalesAgent.setAgent_mainDtls(agent_id);

		SalesAgent.enableFields(false);
		SalesAgent.enableMainButtons(true, true, false, true);
		SalesAgent.enableMainInfoFields(false);

	}

	private static Double sql_wtaxRate(String pblID, String seqNo) {

		double a = 0.00;

		/*
		 * Modified by Mann2x; Date Modified: November 4, 2019; Withholding tax rates
		 * are hardcoded on the previous script;
		 */
		/*
		 * String SQL = "select " +
		 * "(case when b.entity_id is not null or c.salespos_id != '005' then 0.10 else 0.15 end) "
		 * + "from \n" + "(select * from rf_sold_unit where pbl_id = '"
		 * +pblID+"' and seq_no::text = '"+seqNo+"') a " +
		 * "left join (select * from rf_entity_tax_waiver where status_id = 'A') b on a.sellingagent = b.entity_id \n"
		 * +
		 * "left join (select distinct on (agent_id) * from mf_sellingagent_info ) c on a.sellingagent = c.agent_id \n"
		 * ;
		 */

		/*
		 * Modified by Mann2x; Date Modified: March 15, 2021; Withholding tax rate is
		 * now company dependent; try { SQL = "select d.wtax_rate / 100 \n" +
		 * "from (select * from rf_sold_unit where pbl_id = '"
		 * +pblID+"' and seq_no::varchar = '" +seqNo+"'::varchar) a \n" +
		 * "left join (select * from rf_entity_assigned_type where entity_type_id in ('03', '04', '23', '24', '34', '35', '38', '39', '40', '41') \n"
		 * + "and status_id = 'A') b on a.sellingagent = b.entity_id \n" +
		 * "left join mf_entity_type c on b.entity_type_id = c.entity_type_id \n" +
		 * "left join rf_withholding_tax d on c.wtax_id = d.wtax_id" ; } catch
		 * (Exception ex) {
		 * 
		 * }
		 */

		String SQL = "select fn_commission_get_rate('" + pblID + "', " + seqNo + ")";

		System.out.println();
		System.out.printf("SQL #1: TAX RATE", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0].toString() == null || db.getResult()[0][0].toString().equals("null")) {
				a = 0.15;
			} else {
				a = Double.parseDouble(db.getResult()[0][0].toString());
				;
			}

		} else {
			a = 0.15;
		}

		return a;
	}

	public void openDRF() {

		RequestForPayment drf = new RequestForPayment();
		Home_JSystem.addWindow(drf);

		if (co_id.equals("")) {

		} else {
			RequestForPayment.co_id = "02";
			RequestForPayment.company = "CENQHOMES DEVELOPMENT CORPORATION";
			RequestForPayment.lookupCompany.setValue("02");
			RequestForPayment.tagCompany.setTag(company);
			RequestForPayment.company_logo = company_logo;

			RequestForPayment.lblDRF_no.setEnabled(true);
			RequestForPayment.lookupDRF_no.setEnabled(true);
			RequestForPayment.lookupDRF_no.setLookupSQL(RequestForPayment.getDRF_no());

			RequestForPayment.btnAddNew.setEnabled(true);
			RequestForPayment.btnCancel.setEnabled(true);

			int column = tblAgent_CA_list.getSelectedColumn();
			int row = tblAgent_CA_list.getSelectedRow();
			String rplf = modelAgent_CA_list.getValueAt(row, column).toString().trim();

			if (rplf.equals("")) {
			} else {
				RequestForPayment.drf_no = rplf;
				RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);

				RequestForPayment.setDRF_header(RequestForPayment.drf_no);
				RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF,
						RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no);
				RequestForPayment.btnAddNew.setEnabled(false);
				RequestForPayment.btnRefresh.setEnabled(true);
				RequestForPayment.btnPreview.setEnabled(true);
				if (RequestForPayment.isPVcreated() == true) {
					RequestForPayment.btnEdit.setEnabled(false);
				} else {
					RequestForPayment.btnEdit.setEnabled(true);
				}
				RequestForPayment.mnidelete.setEnabled(false);
				RequestForPayment.mniaddrow.setEnabled(false);
				RequestForPayment.mnisetupPV.setEnabled(true);
			}
		}
	}

	private void setUserDivDept() {

		Object[] div_dtl = sql_getDivId();

		div_id = "";
		sales_grp_id = "";

		try {
			div_id = (String) div_dtl[0];
		} catch (NullPointerException e) {
			div_id = "";
		}
		try {
			sales_grp_id = (String) div_dtl[2];
		} catch (NullPointerException e) {
			sales_grp_id = "";
		}

	}

	private Object[] sql_getDivId() {

		String SQL = "select " + "a.div_code, " + "trim(b.division_name) as div_name,  " + "a.dept_code, "
				+ "trim(c.dept_name) as dept_name  " + "from em_employee a \n"
				+ "left join mf_division b on a.div_code = b.division_code "
				+ "left join mf_department c on a.dept_code = c.dept_code " + "where a.emp_code = '"
				+ UserInfo.EmployeeCode + "' ";

		System.out.printf("SQL #1 sql_getDivId: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}

	public void displayClient_All_agentsCommission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String pbl_id, String seq_no) {

		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "select * from view_broker_displayClient_All_agentsCommission('"+pbl_id+"','"+seq_no+"')";

		System.out.println();
		System.out.println("select * from view_broker_displayClient_All_agentsCommission('"+pbl_id+"','"+seq_no+"')");

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalAllComm(modelMain, modelTotal);
		} else {
			modelClient_CommSched_total = new modelComm_client_commSchedule();
			modelClient_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null, null });

			tblClient_CommSched_total = new _JTableTotal(modelClient_CommSched_total, tblClient_CommSched);
			tblClient_CommSched_total.setFont(dialog11Bold);
			scrollClient_CommSchedtotal.setViewportView(tblClient_CommSched_total);
			((_JTableTotal) tblClient_CommSched_total).setTotalLabel(0);
		}

		tblClient_CommSched.packAll();
		tblClient_CommSched.getColumnModel().getColumn(4).setPreferredWidth(70);
		tblClient_CommSched.getColumnModel().getColumn(5).setPreferredWidth(70);
		tblClient_CommSched.getColumnModel().getColumn(6).setPreferredWidth(70);
		tblClient_CommSched.getColumnModel().getColumn(7).setPreferredWidth(70);
		tblClient_CommSched.getColumnModel().getColumn(8).setPreferredWidth(70);
		tblClient_CommSched.getColumnModel().getColumn(9).setPreferredWidth(70);
		tblClient_CommSched.getColumnModel().getColumn(10).setPreferredWidth(70);

		adjustRowHeight_account(tblClient_CommSched);
	}

	public void displayAgent_ClientList(DefaultTableModel modelMain, JList rowHeader, String agent_id, String status, _JTableMain table, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		System.out.println();
		System.out.println("***************************************************************************************");
		System.out.println("select * \n" +
				"from view_comm_client_list_with_agent \n" +
				"where (agent_code = '"+agent_id+"' or '"+agent_id+"' = '') \n" +
				"and (status_id = '"+status+"' or '"+status+"' = ''); ");

		pgSelect db = new pgSelect();
		db.select("select * \n" +
				"from view_comm_client_list_with_agent \n" +
				"where (agent_code = '"+agent_id+"' or '"+agent_id+"' = '') \n" +
				"and (status_id = '"+status+"' or '"+status+"' = ''); ");

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalCommClientList(modelMain, modelTotal);
		} else {
			if (tabMain.getSelectedIndex() == 0) {
				modelAgentAccount_total = new modelComm_client_list();
				modelAgentAccount_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00) });

				tblAgentAccount_total = new _JTableTotal(modelAgentAccount_total, tblAgentAccount);
				tblAgentAccount_total.setFont(dialog11Bold);
				scrollAgentAccount_total.setViewportView(tblAgentAccount_total);
				((_JTableTotal) tblAgentAccount_total).setTotalLabel(0);
			} else {
				modelClientTbl_total = new modelComm_client_list();
				modelClientTbl_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00) });

				tblClientList_total = new _JTableTotal(modelClientTbl_total, tblClientList);
				tblClientList_total.setFont(dialog11Bold);
				scrollClientTbl_total.setViewportView(tblClientList_total);
				((_JTableTotal) tblClientList_total).setTotalLabel(0);
			}

		}

		table.packAll();
		adjustRowHeight_account(table);
	}
}