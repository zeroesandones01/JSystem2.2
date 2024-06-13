package Accounting.Commissions;

import interfaces._GUI;

import java.awt.BorderLayout;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelComm_agentCA_process;
import tablemodel.modelComm_agentCommQualifier;
import tablemodel.modelComm_qualify;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
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
import Reports.Accounting.CommPayoutForm;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class ProcessCommission extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Commission Processing";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlSouth;
	private JPanel pnlTable;
	private JPanel pnlSouthCenterb;
	private JPanel pnlSubTable;
	private JPanel pnlCommList;
	private JPanel pnlCommListHeader;
	private JPanel pnlCommListHeader_a;
	private JPanel pnlCommListHeader_b;
	private JPanel pnlCommListHeader_b_sub1;
	private JPanel pnlCommListHeader_b_sub2;
	private JPanel pnlCommListHeader_b_sub3;
	private JPanel pnlCommListTable;
	private JPanel pnlCashAdvance;
	private JPanel pnlCommQualifier;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlCommListHeader_b_sub4;
	private JPanel pnlCommListHeader_b_sub_buttons;
	private JPanel pnlComp_a2;

	static JLabel lblAgentName;
	private JLabel lblCompany;

	static modelComm_qualify modelCommListTbl;
	static modelComm_agentCA_process modelCashAdv;
	static modelComm_agentCA_process modelCashAdv_total;
	static modelComm_qualify modelCommListTotal;
	private modelComm_agentCommQualifier modelAgent_CommQualif;


	private _JScrollPaneMain scrollCommListTbl;
	private _JScrollPaneMain scrollCashAdv;
	private _JScrollPaneMain scrollAgent_CommQualif;
	static _JScrollPaneTotal scrollCashAdv_total;
	private static _JScrollPaneTotal scrollCommListTotal;

	static JXTextField txtAgentName;
	private JXTextField txtCPFno_fr;
	private JXTextField txtCPFno_to;

	static JCheckBox chkCancAccounts;

	private static _JTableMain tblCommList;
	static _JTableMain tblCashAdv;
	private _JTableMain tblAgent_CommQualif;

	static JList rowHeaderCommList;
	static JList rowHeaderCashAdv;
	private JList rowHeaderAgent_CommQualif;

	static _JTableTotal tblCashAdv_total;
	private static _JTableTotal tblCommListTotal;
	static _JTagLabel tagCompany;

	static JButton btnQualify;
	static JButton btnRefresh;
	static JButton btnProcess;
	static JButton btnSetupCA;
	static JButton btnPreview;
	private JRadioButton rbtCDFnumber;
	private JRadioButton rbtnCDFdate;

	static _JLookup lookupCompany;
	static JCheckBox chkAcctsWithCA;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");

	static String co_id = "";
	static String company = "";
	static String company_logo;

	String agent_id = "";
	String agent = ""; // for display of Cash Advance
	String pbl_id = "";
	String seq_no = "";
	String proj_alias = "";
	Double gross_amt = 0.00;
	Double vat_amt = 0.00;
	Double wtax_amt = 0.00;
	Double net_amt = 0.00;
	Double liq_amt = 0.00;
	Double atm_fee = 0.00;

	String prev_agent_id = "";
	String prev_pbl_id = "";
	String prev_seq_no = "";
	Double tot_wtax_amt = 0.00;
	Double tot_net_amt = 0.00;
	Double tot_liq_amt = 0.00;

	String entity_id = "";
	String comm_no = "";
	String cdf_no = "";
	String drf_no = "";
	String prev_drf_no = "";
	String liq_no = "";
	String jv_no = "";
	String rplf_no = "";
	Integer row_num = 0;
	String rec_id = "";
	Double wTaxrate = null;

	Integer next_seqno = 0;
	private JPopupMenu menu;
	private JMenuItem mniHold;
	private JPanel pnlHoldRemarks;
	private JLabel lblHold;
	private JXTextField txtHold;
	private JButton btnOK;
	private JMenuItem mniUnhold;
	private JSplitPane splitPanel;

	static boolean withCA = false;
	static boolean includeCancAcct = false;

	public ProcessCommission() {
		super(title, true, true, true, true);
		initGUI();
	}

	private ProcessCommission(String title) {
		super(title);

	}

	private ProcessCommission(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(1063, 587));
		this.setBounds(0, 0, 1063, 587);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));
		{
			{
				JPanel panPage = new JPanel(new BorderLayout(5, 5)); 
				//pnlMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 60));
				panPage.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5)); 
						panPage.add(panLabel, BorderLayout.LINE_START); 
						panLabel.setPreferredSize(new Dimension(200, 0));
						{
							{
								JLabel lblCompany = new JLabel("Company");
								panLabel.add(lblCompany);
								lblCompany.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								lookupCompany = new _JLookup(null, "Company", 0, 2);
								panLabel.add(lookupCompany);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setReturnColumn(0);
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
				
											co_id = (String) data[0];
											tagCompany.setTag((String) data[1]);
											company = (String) data[1];

											displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList, modelCommListTotal);
											FncTables.clearTable(modelCashAdv);	
											DefaultListModel listModel = new DefaultListModel(); 
											rowHeaderCashAdv.setModel(listModel);
											modelCashAdv_total = new modelComm_agentCA_process();
											modelCashAdv_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00),
													new BigDecimal(0.00), new BigDecimal(0.00), null, null });
				
											tblCashAdv_total = new _JTableTotal(modelCashAdv_total, tblCashAdv);
											tblCashAdv_total.setFont(dialog11Bold);
											scrollCashAdv_total.setViewportView(tblCashAdv_total);
											((_JTableTotal) tblCashAdv_total).setTotalLabel(0);
				
											btnProcess.setEnabled(true);
											btnSetupCA.setEnabled(false);
											btnRefresh.setEnabled(true);
											btnPreview.setEnabled(true);
				
											lblAgentName.setEnabled(true);
											txtAgentName.setEnabled(true);
											chkAcctsWithCA.setEnabled(true);
											chkCancAccounts.setEnabled(true);
											btnQualify.setEnabled(true);
				
											chkAcctsWithCA.setSelected(false);
											chkCancAccounts.setSelected(false);
				
											modelCashAdv.setEditable(false);
				
											initialize_variables();
										}
									}
								});
							}
						}
					}
					{
						tagCompany = new _JTagLabel("[]");
						panPage.add(tagCompany, BorderLayout.CENTER);
					}
				}
			}
		}
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));

			splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			pnlSubTable.add(splitPanel);
			splitPanel.setOneTouchExpandable(true);
			splitPanel.setResizeWeight(.7d);

			{
				pnlCommList = new JPanel();
				splitPanel.add(pnlCommList, JSplitPane.LEFT);
				pnlCommList.setLayout(new BorderLayout(5, 5));
				pnlCommList.setPreferredSize(new java.awt.Dimension(889, 251));
				pnlCommList.setBorder(JTBorderFactory.createTitleBorder("Commission for Processing"));

				pnlCommListHeader = new JPanel(new BorderLayout(5, 5));
				pnlCommList.add(pnlCommListHeader, BorderLayout.NORTH);
				pnlCommListHeader.setPreferredSize(new Dimension(0, 42));
				{
					
				}

				pnlCommListHeader_a = new JPanel(new GridLayout(1, 1, 5, 10));
				pnlCommListHeader.add(pnlCommListHeader_a, BorderLayout.WEST);
				pnlCommListHeader_a.setPreferredSize(new java.awt.Dimension(92, 40));
				pnlCommListHeader_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblAgentName = new JLabel("Agent/Broker:", JLabel.TRAILING);
					pnlCommListHeader_a.add(lblAgentName);
					lblAgentName.setEnabled(false);
					lblAgentName.setPreferredSize(new java.awt.Dimension(86, 40));
				}

				pnlCommListHeader_b = new JPanel(new BorderLayout(5, 5));
				pnlCommListHeader.add(pnlCommListHeader_b, BorderLayout.CENTER);
				pnlCommListHeader_b.setPreferredSize(new java.awt.Dimension(918, 42));
				pnlCommListHeader_b.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

				pnlCommListHeader_b_sub1 = new JPanel(new GridLayout(1, 1, 5, 10));
				pnlCommListHeader_b.add(pnlCommListHeader_b_sub1, BorderLayout.WEST);
				pnlCommListHeader_b_sub1.setPreferredSize(new java.awt.Dimension(235, 26));
				{
					txtAgentName = new JXTextField("");
					pnlCommListHeader_b_sub1.add(txtAgentName);
					txtAgentName.setEnabled(false);
					txtAgentName.setEditable(true);
					txtAgentName.setBounds(120, 25, 300, 22);
					txtAgentName.setHorizontalAlignment(JTextField.LEFT);
					txtAgentName.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
					txtAgentName.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {

							checkAllClientList();

						}
					});
				}

				pnlCommListHeader_b_sub_buttons = new JPanel(new GridLayout(1, 2, 5, 0));
				pnlCommListHeader_b.add(pnlCommListHeader_b_sub_buttons, BorderLayout.CENTER);
				pnlCommListHeader_b_sub_buttons.setPreferredSize(new java.awt.Dimension(499, 26));

				pnlCommListHeader_b_sub2 = new JPanel(new GridLayout(1, 2, 5, 0));
				pnlCommListHeader_b_sub_buttons.add(pnlCommListHeader_b_sub2, BorderLayout.CENTER);
				pnlCommListHeader_b_sub2.setPreferredSize(new java.awt.Dimension(419, 26));

				{
					chkAcctsWithCA = new JCheckBox("Accounts w/ CA");
					pnlCommListHeader_b_sub2.add(chkAcctsWithCA);
					chkAcctsWithCA.setEnabled(false);
					chkAcctsWithCA.setPreferredSize(new java.awt.Dimension(383, 26));
					chkAcctsWithCA.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

							if (selected) {
								withCA = true;

								pnlCashAdvance = new JPanel();
								ShowCA_table(true);

								refreshTable();
								displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList,
										modelCommListTotal);

							} else {
								withCA = false;

								pnlCashAdvance = new JPanel();
								ShowCA_table(false);

								refreshTable();
								displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList,
										modelCommListTotal);

							}
						}
					});
				}

				pnlCommListHeader_b_sub4 = new JPanel(new GridLayout(1, 3, 5, 0));
				pnlCommListHeader_b_sub_buttons.add(pnlCommListHeader_b_sub4, BorderLayout.CENTER);
				pnlCommListHeader_b_sub4.setPreferredSize(new java.awt.Dimension(419, 26));

				{
					chkCancAccounts = new JCheckBox("Include Cancelled Accts.");
					pnlCommListHeader_b_sub4.add(chkCancAccounts);
					chkCancAccounts.setEnabled(false);
					chkCancAccounts.setPreferredSize(new java.awt.Dimension(383, 26));
					chkCancAccounts.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

							if (selected) {
								includeCancAcct = true;
								refreshTable();
								displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList,
										modelCommListTotal);
							}

							else {
								includeCancAcct = false;
								refreshTable();
								displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList,
										modelCommListTotal);
							}
						}
					});
				}

				pnlCommListHeader_b_sub3 = new JPanel(new GridLayout(1, 2, 5, 0));
				pnlCommListHeader_b.add(pnlCommListHeader_b_sub3, BorderLayout.EAST);
				pnlCommListHeader_b_sub3.setPreferredSize(new java.awt.Dimension(263, 26));
				pnlCommListHeader_b_sub3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					btnQualify = new JButton("Show Commission Qualifiers");
					pnlCommListHeader_b_sub3.add(btnQualify);
					btnQualify.setActionCommand("Qualify");
					btnQualify.addActionListener(this);
					btnQualify.setEnabled(false);
				}

				pnlCommListTable = new JPanel(new BorderLayout(0, 0));
				pnlCommList.add(pnlCommListTable, BorderLayout.CENTER);
				pnlCommListTable.setPreferredSize(new java.awt.Dimension(1005, 42));

				{
					scrollCommListTbl = new _JScrollPaneMain();
					pnlCommListTable.add(scrollCommListTbl, BorderLayout.CENTER);
					{
						modelCommListTbl = new modelComm_qualify();

						tblCommList = new _JTableMain(modelCommListTbl);
						scrollCommListTbl.setViewportView(tblCommList);
						modelCommListTbl.setEditable(true);
						tblCommList.addMouseListener(this);
						tblCommList.addMouseListener(new PopupTriggerListener_panel());
						tblCommList.setSortable(false);
						tblCommList.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
							}

							public void keyPressed(KeyEvent e) {
							}

						});
						tblCommList.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblCommList.rowAtPoint(e.getPoint()) == -1) {
								} else {
									tblCommList.setCellSelectionEnabled(true);
								}

								int row = tblCommList.getSelectedRow();
								String tran_type = tblCommList.getValueAt(row, 6).toString().trim();
								String comm_type = tblCommList.getValueAt(row, 7).toString().trim();
								agent = tblCommList.getValueAt(row, 18).toString().trim();
								pbl_id = tblCommList.getValueAt(row, 16).toString().trim();
								try {
									seq_no = tblCommList.getValueAt(row, 17).toString().trim();
								} catch (NullPointerException ev) {
									seq_no = null;
								}

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

								mniHold.setText("Hold " + tran_type);
								mniUnhold.setText("Unhold " + tran_type);

								if (chkAcctsWithCA.isSelected()) {
									displayClient_All_withCA(modelCashAdv, rowHeaderCashAdv, modelCashAdv_total, agent);
									btnSetupCA.setEnabled(true);

									if (multipleEntity(agent) == true) {
										JOptionPane.showMessageDialog(getContentPane(),
												"Multiple entity tagging is not allowed for Commission with CA",
												"Error", JOptionPane.ERROR_MESSAGE);
										displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList,
												modelCommListTotal);
										setQualFalse();
									}
								}

								btnQualify.setEnabled(true);
							}

							public void mouseReleased(MouseEvent e) {
								if (tblCommList.rowAtPoint(e.getPoint()) == -1) {
								} else {
									tblCommList.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderCommList = tblCommList.getRowHeader22();
						scrollCommListTbl.setRowHeaderView(rowHeaderCommList);
						scrollCommListTbl.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
								FncTables.getRowHeader_Header());
					}
					{
						scrollCommListTotal = new _JScrollPaneTotal(scrollCommListTbl);
						pnlCommListTable.add(scrollCommListTotal, BorderLayout.SOUTH);
						{
							modelCommListTotal = new modelComm_qualify();
							modelCommListTotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null,
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
									new BigDecimal(0.00), new BigDecimal(0.00), null, null });

							tblCommListTotal = new _JTableTotal(modelCommListTotal, tblCommList);
							tblCommListTotal.setFont(dialog11Bold);
							scrollCommListTotal.setViewportView(tblCommListTotal);
							((_JTableTotal) tblCommListTotal).setTotalLabel(1);
						}
					}
				}
			}
			{
				{
					pnlCashAdvance = new JPanel();

					{
						ShowCA_table(false);
					}
				}
			}
			{
				pnlCommQualifier = new JPanel();
				pnlCommQualifier.setLayout(new BorderLayout(5, 5));
				pnlCommQualifier.setPreferredSize(new java.awt.Dimension(500, 300));

				{
					{
						scrollAgent_CommQualif = new _JScrollPaneMain();
						pnlCommQualifier.add(scrollAgent_CommQualif, null);
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
		}
		{
			JPanel panEnd = new JPanel(new GridLayout(1, 4, 5, 5));
			pnlMain.add(panEnd, BorderLayout.SOUTH);
			panEnd.setPreferredSize(new Dimension(0, 30));
			{
				{
					btnProcess = new JButton("Process");
					panEnd.add(btnProcess);
					btnProcess.setActionCommand("Process");
					btnProcess.addActionListener(this);
					btnProcess.setEnabled(false);
				}
				{
					btnSetupCA = new JButton("Set-up CA Liquidation");
					panEnd.add(btnSetupCA);
					btnSetupCA.setActionCommand("setupCA");
					btnSetupCA.addActionListener(this);
					btnSetupCA.setEnabled(false);
				}
				{
					btnRefresh = new JButton("Refresh Comm. List");
					panEnd.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.addActionListener(this);
					btnRefresh.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview CPPF");
					panEnd.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
			}
		}
		{
			menu = new JPopupMenu("Popup");
			mniHold = new JMenuItem("Hold");
			mniUnhold = new JMenuItem("Unhold");
			menu.add(mniHold);
			menu.add(mniUnhold);
			mniHold.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlHoldRemarks, "Holding Remarks",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

					if (scanOption == JOptionPane.CLOSED_OPTION) {
						try {
						} catch (java.lang.ArrayIndexOutOfBoundsException e) {
						}
					}
				}
			});
			mniUnhold.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					saveUnhold();
				}
			});
		}
		{
			pnlHoldRemarks = new JPanel();
			pnlHoldRemarks.setLayout(null);
			pnlHoldRemarks.setPreferredSize(new java.awt.Dimension(359, 69));
			{
				lblHold = new JLabel();
				pnlHoldRemarks.add(lblHold);
				lblHold.setBounds(5, 15, 125, 20);
				lblHold.setText("Holding Remarks :");
			}
			{
				txtHold = new JXTextField("");
				pnlHoldRemarks.add(txtHold);
				txtHold.setEnabled(true);
				txtHold.setEditable(true);
				txtHold.setBounds(110, 15, 243, 21);
				txtHold.setHorizontalAlignment(JTextField.LEFT);
				txtHold.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
			}
			{
				btnOK = new JButton();
				pnlHoldRemarks.add(btnOK);
				btnOK.setBounds(138, 42, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveHold();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlHoldRemarks);
						optionPaneWindow.dispose();
					}
				});
			}
		}

		initialize_comp();
	}

	static void displayClient_All_agentsCommission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String sql = "select * from get_qualified_comm_promo("+withCA+", "+includeCancAcct+") ORDER BY c_proj_alias, c_agent_name";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalAllComm(modelMain, modelTotal);
		} else {
			modelCommListTotal = new modelComm_qualify();
			modelCommListTotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null,
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), null, null });

			tblCommListTotal = new _JTableTotal(modelCommListTotal, tblCommList);
			tblCommListTotal.setFont(dialog11Bold);
			scrollCommListTotal.setViewportView(tblCommListTotal);
			((_JTableTotal) tblCommListTotal).setTotalLabel(1);
		}

		tblCommList.packAll();
		tblCommList.setSortable(false);
		tblCommList.getColumnModel().getColumn(8).setPreferredWidth(80);
		tblCommList.getColumnModel().getColumn(9).setPreferredWidth(80);
		tblCommList.getColumnModel().getColumn(10).setPreferredWidth(80);
		tblCommList.getColumnModel().getColumn(11).setPreferredWidth(80);
		tblCommList.getColumnModel().getColumn(12).setPreferredWidth(80);
		tblCommList.getColumnModel().getColumn(13).setPreferredWidth(80);

		adjustRowHeight_account(tblCommList);
	}

	private void displayClientCommissionConditions(DefaultTableModel modelMain, JList rowHeader, String pbl_id,
			String seq_no, String type) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql =

				"-----------------displayClientCommissionConditions \n" + "select\r\n" + "d.func_id, \n"
						+ "d.func_desc, \r\n" + "''," + "null" +

						"\r\n"
						+ "from ( select distinct on (pbl_id, seq_no) * from cm_schedule_hd where status_id = 'A' ) a \r\n"
						+ "left join cm_scheme_dl b on a.scheme_code = b.scheme_id\r\n"
						+ "left join cm_conditions_dl c on b.cond_id = c.cond_id\r\n"
						+ "left join cm_functions d on c.func_id = d.func_id\r\n" + "\r\n" + "where a.pbl_id = '"
						+ pbl_id + "' \r\n" + "and a.seq_no = " + seq_no + " \r\n" + "and b.status_id = 'A'  \n"
						+ "and c.status_id = 'A'  \n" + "and trim(b.comm_type) = '" + type + "' ";

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

	private void displayClient_All_withCA(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal,
			String agent) {//

		FncTables.clearTable(modelMain);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);// Setting of DefaultListModel into rowHeader.

		String sql = "-----------------displayClient_all_CA \n" + "select * from get_broker_agent_unliquidated_ca('"
				+ agent + "') \n";

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
			modelCashAdv_total = new modelComm_agentCA_process();
			modelCashAdv_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null });

			tblCashAdv_total = new _JTableTotal(modelCashAdv_total, tblCashAdv);
			tblCashAdv_total.setFont(dialog11Bold);
			scrollCashAdv_total.setViewportView(tblCashAdv_total);
			((_JTableTotal) tblCashAdv_total).setTotalLabel(0);
		}

		tblCashAdv.packAll();
		tblCashAdv.getColumnModel().getColumn(4).setPreferredWidth(80);
		tblCashAdv.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblCashAdv.getColumnModel().getColumn(6).setPreferredWidth(60);
		tblCashAdv.getColumnModel().getColumn(7).setPreferredWidth(60);
		tblCashAdv.getColumnModel().getColumn(8).setPreferredWidth(60);

		adjustRowHeight_account(tblCashAdv);
	}

	// action performed
	@Override
	public void actionPerformed(ActionEvent e) {//

		if (e.getActionCommand().equals("Refresh")) {
			refresh();
			modelCashAdv.setEditable(false);
		} // ok

		if (e.getActionCommand().equals("Preview")) {
			CommPayoutForm comm_cpf = new CommPayoutForm();
			Home_JSystem.addWindow(comm_cpf);
		} // ok

		if (e.getActionCommand().equals("Qualify")) {
			qualify();
		} // ok

		if (e.getActionCommand().equals("Process") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8") == true) {
			process();
		} // ok
		else if (e.getActionCommand().equals("Process")
				&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8") == false) {
			JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to process commission.",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}

		if (e.getActionCommand().equals("Cancel")) {
			txtCPFno_fr.setText("");
			txtCPFno_to.setText("");
			dteDateFrom.setDate(Calendar.getInstance().getTime());
			dateDateTo.setDate(Calendar.getInstance().getTime());
		} // ok

		if (e.getActionCommand().equals("PreviewCPF")) {
			previewCPF();
		}

		if (e.getActionCommand().equals("setupCA")) {
			modelCashAdv.setEditable(true);
			btnSetupCA.setEnabled(false);
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

	private void refreshTable() {//

		FncTables.clearTable(modelCashAdv);// Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();// Creating DefaultListModel for rowHeader.
		rowHeaderCashAdv.setModel(listModel);// Setting of DefaultListModel into rowHeader.
		modelCashAdv_total = new modelComm_agentCA_process();
		modelCashAdv_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), null, null });

		tblCashAdv_total = new _JTableTotal(modelCashAdv_total, tblCashAdv);
		tblCashAdv_total.setFont(dialog11Bold);
		scrollCashAdv_total.setViewportView(tblCashAdv_total);
		((_JTableTotal) tblCashAdv_total).setTotalLabel(0);

		btnSetupCA.setEnabled(false);
	}

	private void previewCPF() {

		String criteria = "CPF";
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(Calendar.getInstance().getTime()));

		if (rbtCDFnumber.isSelected()) {
			mapParameters.put("sort_by", "cdf_no");
			mapParameters.put("date_from", null);
			mapParameters.put("date_to", null);
			mapParameters.put("cdf_from", Integer.parseInt(txtCPFno_fr.getText()));
			mapParameters.put("cdf_to", Integer.parseInt(txtCPFno_to.getText()));
			mapParameters.put("proj_id", "All");
			mapParameters.put("co_id", co_id);
		}

		else if (rbtnCDFdate.isSelected()) {
			mapParameters.put("sort_by", "cdf_date");
			mapParameters.put("date_from", dteDateFrom.getDate());
			mapParameters.put("date_to", dateDateTo.getDate());
			mapParameters.put("cdf_from", "");
			mapParameters.put("cdf_to", "");
			mapParameters.put("proj_id", "All");
			mapParameters.put("co_id", co_id);
		}

		FncReport.generateReport("/Reports/rptCPF_preview.jasper", reportTitle, company, mapParameters);
	}

	private void qualify() {

		displayClientCommissionConditions(modelAgent_CommQualif, rowHeaderAgent_CommQualif, pbl_id, seq_no, comm_no);

		int rw = tblAgent_CommQualif.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String func_id = tblAgent_CommQualif.getValueAt(x, 0).toString().trim();

			if (Qualify_Commission.commQualified(pbl_id, seq_no, func_id) == true) {
				modelAgent_CommQualif.setValueAt("yes", x, 2);
			} else {
				modelAgent_CommQualif.setValueAt("no", x, 2);
			}
			x++;
		}

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlCommQualifier, "Commission Qualifiers",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

		if (scanOption == JOptionPane.CLOSED_OPTION) {

		} // CLOSED_OPTION

	}

	private void process() {

		if (nothingtoProcess() == false) {

			if (checkForHoldComm() == true) {
				JOptionPane.showMessageDialog(getContentPane(),
						"Item(s) selected is/are on Hold. Please un-check before processing.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {

				if (checkForMissingTIN() == true) {
					JOptionPane.showMessageDialog(getContentPane(),
							"Agent(s) selected has/have missing TIN. "
									+ "You may tag the TIN in TIN-Tagging utility or " + "\n"
									+ "you can also un-check the item to begin batch processing.",
									"Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					if (chkAcctsWithCA.isSelected() == true) {
						if (liqui_amounts_equal() == false) {
							JOptionPane.showMessageDialog(getContentPane(), "Liquidation amounts not equal.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							processCDF_v2();
						}
					} else {
						processCDF_v2();
					}
				}
			}
		} else if (nothingtoProcess() == true) {
			JOptionPane.showMessageDialog(getContentPane(), "Please select item(s) to process.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "8") == true) {
					mniHold.setEnabled(true);
					mniUnhold.setEnabled(true);
				} else {
					mniHold.setEnabled(false);
					mniUnhold.setEnabled(false);
				}
			}
		}

		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "8") == true) {
					mniHold.setEnabled(true);
					mniUnhold.setEnabled(true);
				} else {
					mniHold.setEnabled(false);
					mniUnhold.setEnabled(false);
				}
			}
		}
	}

	private void initialize_variables() {

		agent_id = "";
		agent = ""; // for display of Cash Advance
		pbl_id = "";
		seq_no = "";
		proj_alias = "";
		gross_amt = 0.00;
		vat_amt = 0.00;
		wtax_amt = 0.00;
		net_amt = 0.00;
		liq_amt = 0.00;

		prev_agent_id = "";
		prev_pbl_id = "";
		prev_seq_no = "";
		tot_wtax_amt = 0.00;
		tot_net_amt = 0.00;
		tot_liq_amt = 0.00;

		entity_id = "";
		comm_no = "";
		cdf_no = "";
		drf_no = ""; // for CA liquidation purpose
		prev_drf_no = ""; // for CA liquidation purpose
		liq_no = ""; // for CA liquidation purpose
		jv_no = ""; // for CA liquidation purpose
		rplf_no = "";
		row_num = 0;
		rec_id = "";

		next_seqno = 0;

		withCA = false;
		includeCancAcct = false;

	}

	// table operations
	private void totalCA(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal ca_amt = new BigDecimal(0.00);
		BigDecimal liq_amt = new BigDecimal(0.00);
		BigDecimal for_liq_amt = new BigDecimal(0.00);
		BigDecimal os_amt = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				ca_amt = ca_amt.add(((BigDecimal) modelMain.getValueAt(x, 5)));
			} catch (NullPointerException e) {
				ca_amt = ca_amt.add(new BigDecimal(0.00));
			}

			try {
				liq_amt = liq_amt.add(((BigDecimal) modelMain.getValueAt(x, 6)));
			} catch (NullPointerException e) {
				liq_amt = liq_amt.add(new BigDecimal(0.00));
			}

			try {
				for_liq_amt = for_liq_amt.add(((BigDecimal) modelMain.getValueAt(x, 7)));
			} catch (NullPointerException e) {
				for_liq_amt = for_liq_amt.add(new BigDecimal(0.00));
			}

			try {
				os_amt = os_amt.add(((BigDecimal) modelMain.getValueAt(x, 8)));
			} catch (NullPointerException e) {
				os_amt = os_amt.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(
				new Object[] { "Total", null, null, null, null, ca_amt, liq_amt, for_liq_amt, os_amt, null, null });
	}

	private static void totalAllComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {// ok

		FncTables.clearTable(modelTotal);// Code to clear modelMain.

		BigDecimal gross_comm = new BigDecimal(0.00);
		BigDecimal vat_amt = new BigDecimal(0.00);
		BigDecimal wtax_amt = new BigDecimal(0.00);
		BigDecimal for_liq_amt = new BigDecimal(0.00);
		BigDecimal atm_fee_amt = new BigDecimal(0.00);
		BigDecimal net_amt = new BigDecimal(0.00);

		for (int x = 0; x < modelMain.getRowCount(); x++) {

			try {
				gross_comm = gross_comm.add(((BigDecimal) modelMain.getValueAt(x, 8)));
			} catch (NullPointerException e) {
				gross_comm = gross_comm.add(new BigDecimal(0.00));
			}

			try {
				vat_amt = vat_amt.add(((BigDecimal) modelMain.getValueAt(x, 9)));
			} catch (NullPointerException e) {
				vat_amt = vat_amt.add(new BigDecimal(0.00));
			}

			try {
				wtax_amt = wtax_amt.add(((BigDecimal) modelMain.getValueAt(x, 10)));
			} catch (NullPointerException e) {
				wtax_amt = wtax_amt.add(new BigDecimal(0.00));
			}

			try {
				for_liq_amt = for_liq_amt.add(((BigDecimal) modelMain.getValueAt(x, 11)));
			} catch (NullPointerException e) {
				for_liq_amt = for_liq_amt.add(new BigDecimal(0.00));
			}

			try {
				atm_fee_amt = atm_fee_amt.add(((BigDecimal) modelMain.getValueAt(x, 12)));
			} catch (NullPointerException e) {
				atm_fee_amt = atm_fee_amt.add(new BigDecimal(0.00));
			}

			try {
				net_amt = net_amt.add(((BigDecimal) modelMain.getValueAt(x, 13)));
			} catch (NullPointerException e) {
				net_amt = net_amt.add(new BigDecimal(0.00));
			}

		}

		modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null, gross_comm, vat_amt,
				wtax_amt, for_liq_amt, atm_fee_amt, net_amt, null, null });
	}

	private void checkAllClientList() {//

		int rw = tblCommList.getModel().getRowCount();
		int x = 0;

		while (x < rw) {

			String name = "";

			try {
				name = tblCommList.getValueAt(x, 1).toString().toUpperCase();
			} // make sure it's not modelAgentAccount, otherwise, row number will be fixed
			catch (NullPointerException e) {
				name = "";
			}

			String acct_name = txtAgentName.getText().trim().toUpperCase();
			Boolean match = name.indexOf(acct_name) > 0;
			Boolean start = name.startsWith(acct_name);
			Boolean end = name.endsWith(acct_name);

			if (match == true || start == true || end == true) {
				tblCommList.setRowSelectionInterval(x, x);
				tblCommList.changeSelection(x, 1, false, false);
				break;
			} else {
				tblCommList.setRowSelectionInterval(0, 0);
				tblCommList.changeSelection(0, 1, false, false);
			}

			x++;

		}
	}

	private static void adjustRowHeight_account(_JTableMain table) {//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x < rw) {
			table.setRowHeight(x, 22);
			x++;
		}

	}

	private String sql_getPromoID(String promo_desc) {

		String promo_id = "";

		String SQL = "select promo_code from cm_promo_bonus where trim(promo_desc) like '%" + promo_desc + "%'   ";

		// System.out.printf("Get Promo ID - " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			promo_id = (String) db.getResult()[0][0];
		} else {
			promo_id = "";
		}

		return promo_id;
	}

	private void ShowCA_table(boolean x) {

		splitPanel.add(pnlCashAdvance, JSplitPane.RIGHT);
		pnlCashAdvance.setLayout(new BorderLayout(5, 5));
		pnlCashAdvance.setVisible(x);
		pnlCashAdvance.setPreferredSize(new java.awt.Dimension(911, 214));
		pnlCashAdvance.setBorder(JTBorderFactory.createTitleBorder("Cash Advances"));

		// start of Promo (by agent)
		{
			{
				{

					scrollCashAdv = new _JScrollPaneMain();
					pnlCashAdvance.add(scrollCashAdv, BorderLayout.CENTER);
					{
						modelCashAdv = new modelComm_agentCA_process();

						tblCashAdv = new _JTableMain(modelCashAdv);
						scrollCashAdv.setViewportView(tblCashAdv);
						tblCashAdv.addMouseListener(this);
						tblCashAdv.setSortable(false);
						tblCashAdv.getColumnModel().getColumn(4).setPreferredWidth(80);
						tblCashAdv.getColumnModel().getColumn(5).setPreferredWidth(60);
						tblCashAdv.getColumnModel().getColumn(6).setPreferredWidth(60);
						tblCashAdv.getColumnModel().getColumn(7).setPreferredWidth(60);
						tblCashAdv.getColumnModel().getColumn(8).setPreferredWidth(60);

						tblCashAdv.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								if (chkAcctsWithCA.isSelected()) {
									computeCAAmounts(evt);
								}
							}

							public void keyPressed(KeyEvent e) {
								if (chkAcctsWithCA.isSelected()) {
									computeCAAmounts(e);
								}
							}
						});
						tblCashAdv.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if (tblCashAdv.rowAtPoint(e.getPoint()) == -1) {
									tblCashAdv_total.clearSelection();
								} else {
									tblCashAdv.setCellSelectionEnabled(true);
								}
							}

							public void mouseReleased(MouseEvent e) {
								if (tblCashAdv.rowAtPoint(e.getPoint()) == -1) {
									tblCashAdv_total.clearSelection();
								} else {
									tblCashAdv.setCellSelectionEnabled(true);
								}
							}
						});

					}
					{
						rowHeaderCashAdv = tblCashAdv.getRowHeader22();
						scrollCashAdv.setRowHeaderView(rowHeaderCashAdv);
						scrollCashAdv.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollCashAdv_total = new _JScrollPaneTotal(scrollCashAdv);
					pnlCashAdvance.add(scrollCashAdv_total, BorderLayout.SOUTH);
					{
						modelCashAdv_total = new modelComm_agentCA_process();
						modelCashAdv_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00),
								new BigDecimal(0.00), new BigDecimal(0.00), null, null });

						tblCashAdv_total = new _JTableTotal(modelCashAdv_total, tblCashAdv);
						tblCashAdv_total.setFont(dialog11Bold);
						scrollCashAdv_total.setViewportView(tblCashAdv_total);
						((_JTableTotal) tblCashAdv_total).setTotalLabel(0);
					}
				}

			}
		}
		// end of Promo (by agent)

	}

	// save and insert
	/*
	 * private void processCDF(){
	 * 
	 * if (JOptionPane.showConfirmDialog(getContentPane(),
	 * "<html>To ensure that the system will generate one disbursement per agent/payee,<html>"
	 * + "\n" + "<html>It is advised that list be<html>" +
	 * "<b><i><u> Arranged Alphabetically<html></i></b></u>  " +
	 * "<html>. Proceed? <html> \n" , "Alphabetical Confirmation",
	 * JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) ==
	 * JOptionPane.YES_OPTION) {
	 * 
	 * if (JOptionPane.showConfirmDialog(getContentPane(),
	 * "<html>This process will create <html>" +
	 * "<html><b><i><u>Commission Payment Request(s)<html></b></i></u>." + "\n" +
	 * "<html>Are you sure all tagged accounts are correct?<html>", "Confirmation",
	 * JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) ==
	 * JOptionPane.YES_OPTION) {
	 * 
	 * 
	 * int v=1; //counter for creation of jv_no and liq_no int w=0; //counter for
	 * creation of JV and liquidation rec_id int x=0; //counter for creation of CDF
	 * int y=0; //counter for next cdf_no int z=1; //counter for rplf_detail line_no
	 * int a=0; //counter for transaction that does not require disbursement
	 * processing String old_agent_id = ""; //Added 09-27-2016 : Del G. Purpose : To
	 * generate different JV No. when the RPLF No. of CA is of the next Agent is the
	 * same as the previous
	 * 
	 * pgUpdate db = new pgUpdate();
	 * 
	 * //create CDF while ( x < modelCommListTbl.getRowCount()) { Boolean isTrue =
	 * false; if(tblCommList.getValueAt(x,0) instanceof String){isTrue = new
	 * Boolean((String)tblCommList.getValueAt(x,0));} if(tblCommList.getValueAt(x,0)
	 * instanceof Boolean){isTrue = (Boolean)tblCommList.getValueAt(x,0);}
	 * 
	 * System.out.printf("Until Here!" + "\n");
	 * 
	 * if(isTrue){
	 * 
	 * agent_id = tblCommList.getValueAt(x,18).toString().trim(); String agent_name
	 * = ""; try { agent_name = tblCommList.getValueAt(x,1).toString().trim(); }
	 * catch (NullPointerException ev) { agent_name = ""; } String acct_code = "";
	 * try { acct_code = tblCommList.getValueAt(x,20).toString().trim(); } catch
	 * (NullPointerException ev) { acct_code = ""; } try { proj_alias =
	 * tblCommList.getValueAt(x,4).toString().trim(); } catch (NullPointerException
	 * ev) { proj_alias = ""; } String unit_desc = ""; try { unit_desc =
	 * tblCommList.getValueAt(x,5).toString().trim(); } catch (NullPointerException
	 * ev) { unit_desc = ""; } co_id =
	 * tblCommList.getValueAt(x,19).toString().trim(); pbl_id =
	 * tblCommList.getValueAt(x,16).toString().trim(); try { seq_no =
	 * tblCommList.getValueAt(x,17).toString().trim(); } catch (NullPointerException
	 * ev) { seq_no = null; } gross_amt =
	 * Double.parseDouble(tblCommList.getValueAt(x,8).toString()); vat_amt =
	 * Double.parseDouble(tblCommList.getValueAt(x,9).toString()); wtax_amt =
	 * Double.parseDouble(tblCommList.getValueAt(x,10).toString()); liq_amt =
	 * Double.parseDouble(tblCommList.getValueAt(x,11).toString()); atm_fee =
	 * Double.parseDouble(tblCommList.getValueAt(x,12).toString()); net_amt =
	 * Double.parseDouble(tblCommList.getValueAt(x,13).toString());
	 * 
	 * rec_id = tblCommList.getValueAt(x,22).toString(); String tran_type_1 =
	 * tblCommList.getValueAt(x,6).toString().trim(); String tran_type_2 = "AA"; if
	 * (tran_type_1.equals("Commission")) {} else {tran_type_2 = "BB";} String
	 * comm_type_1 = tblCommList.getValueAt(x,7).toString().trim(); String
	 * comm_type_2 = ""; String promo_code = ""; if (comm_type_1.equals("1st CA"))
	 * {comm_type_2 = "1"; } else if (comm_type_1.equals("2nd CA")) {comm_type_2 =
	 * "2";} else if (comm_type_1.equals("3rd CA")) {comm_type_2 = "3";} else if
	 * (comm_type_1.equals("4th CA")) {comm_type_2 = "4";} else if
	 * (comm_type_1.equals("Full Comm")) {comm_type_2 = "FC";} else {comm_type_2 =
	 * "BB"; promo_code = sql_getPromoID(comm_type_1); }
	 * 
	 * if (agent_id.equals(prev_agent_id)) //if same agent and same unit
	 * [removed:&&pbl_id.equals(prev_pbl_id)&&seq_no.equals(prev_seq_no] {
	 * 
	 * tot_net_amt = tot_net_amt + net_amt; tot_wtax_amt = tot_wtax_amt + wtax_amt;
	 * 
	 * updateCDF_header(db, tot_net_amt, tot_wtax_amt, cdf_no); createCDF_detail(db,
	 * x, acct_code, proj_alias, pbl_id, seq_no, tran_type_2, comm_type_2,
	 * promo_code, net_amt, wtax_amt, agent_id, cdf_no, x, liq_amt, vat_amt,
	 * rplf_no, atm_fee ); updateCDFschedule(db, rec_id);
	 * 
	 * 
	 * //FOR RPLF : Start z = z + 1 - a; //for rplf line_no if((Boolean)
	 * modelCommListTbl.getValueAt(x,25)==true) { if (z==1) { insertRPLF_header(db,
	 * rplf_no, agent_id); insertRPLF_detail(db, rplf_no, cdf_no, unit_desc,
	 * agent_name, tran_type_2, promo_code, gross_amt-liq_amt, agent_id, proj_alias,
	 * pbl_id, wtax_amt, net_amt, z, acct_code, vat_amt ); z = 2; } else if (z>1) {
	 * insertRPLF_detail(db, rplf_no, cdf_no, unit_desc, agent_name, tran_type_2,
	 * promo_code, gross_amt-liq_amt-atm_fee, agent_id, proj_alias, pbl_id,
	 * wtax_amt, net_amt, z, acct_code, vat_amt ); } } else //NO DRF if for_disbproc
	 * (cm_schedule_dl) is false { a = a + 1; } //FOR RPLF : End
	 * 
	 * } else //FOR - (1)Start; OR (2)Different Agent { cdf_no =
	 * sql_getNextCDF_no(y); rplf_no = sql_getNextRPLFno(y); String true_drf = "";
	 * // 04-01-2016 : I needed this variable because if transaction is not for DRF
	 * proc., DRF no. in CDF header is filled with the supposed next DRF No.
	 * 
	 * //DRF Creation - Start if((Boolean) modelCommListTbl.getValueAt(x,25)==true)
	 * { z = 1; insertRPLF_header(db, rplf_no, agent_id); insertRPLF_detail(db,
	 * rplf_no, cdf_no, unit_desc, agent_name, tran_type_2, promo_code,
	 * gross_amt-liq_amt, agent_id, proj_alias, pbl_id, wtax_amt, net_amt, z,
	 * acct_code, vat_amt ); true_drf = rplf_no; } else //NO DRF if for_disbproc
	 * (cm_schedule_dl) is false { a = 1; true_drf = ""; } //DRF Creation - End
	 * 
	 * createCDF_header(db, x, agent_id, pbl_id, seq_no, wtax_amt, net_amt,
	 * true_drf, cdf_no, y); createCDF_detail(db, x, acct_code, proj_alias, pbl_id,
	 * seq_no, tran_type_2, comm_type_2, promo_code, net_amt, wtax_amt, agent_id,
	 * cdf_no, x, liq_amt, vat_amt, true_drf, atm_fee );
	 * 
	 * updateCDFschedule(db, rec_id);
	 * 
	 * //auto-JV of ATM Fee if (atm_fee>0) { setJV_no(v); insertJV_header_forATM(db,
	 * cdf_no, agent_name); insertJV_detail_forATM(db, w); updateATM_table(db,
	 * agent_id, cdf_no, jv_no); v = v + 1; }
	 * 
	 * tot_net_amt = net_amt; //reset to 0.00 tot_wtax_amt = wtax_amt; //reset to
	 * 0.00
	 * 
	 * y++; }
	 * 
	 * prev_agent_id = agent_id; prev_pbl_id = pbl_id; prev_seq_no = seq_no;
	 * 
	 * } x++; }
	 * 
	 * 
	 * //for Cash Advances if (chkAcctsWithCA.isSelected()){
	 * 
	 * //create CM_liquidation, JV, Liquidation while ( w <
	 * modelCashAdv.getRowCount()) {
	 * 
	 * //agent_id = tblCommList.getValueAt(x,18).toString().trim(); double
	 * for_liq_amt = Double.parseDouble(modelCashAdv.getValueAt(w,7).toString());
	 * drf_no = modelCashAdv.getValueAt(w,1).toString(); String drf_remarks =
	 * modelCashAdv.getValueAt(w,10).toString(); Integer drf_line =
	 * Integer.parseInt(modelCashAdv.getValueAt(w,2).toString()); String cv_no =
	 * modelCashAdv.getValueAt(w,14).toString();
	 * 
	 * System.out.printf("DRF_NO : " + drf_no + "\n");
	 * 
	 * if (for_liq_amt>0) {
	 * 
	 * if (drf_no.equals(prev_drf_no) && agent_id.equals(old_agent_id)) {
	 * System.out.printf("PREV_DRF_NO is same with DRF_NO !" + "\n");
	 * insertLiquiDetails(db, liq_no, cdf_no, agent_id, w );
	 * insertJV_detail_ver2(db, w); createCAliquidation(db, liq_no, cdf_no,
	 * agent_id, w); }
	 * 
	 * else //create new jv_no and liq_no for each drf_no {
	 * System.out.printf("PREV_DRF_NO is not the same with DRF_NO !" + "\n");
	 * 
	 * setJV_no(v);
	 * 
	 * if (rplfAlreadyInLiquiTable(drf_no,drf_line)) //applicable only for
	 * previously, partially liquidated rplf { liq_no =
	 * getPreviousLiqNo(drf_no,drf_line); insertLiquiDetails(db, liq_no, cdf_no,
	 * agent_id, w);} else { liq_no = sql_getNextLIQno(v); insertLiquiHeader(db,
	 * liq_no, w ); insertLiquiDetails(db, liq_no, cdf_no, agent_id, w); }
	 * 
	 * insertJV_header(db, drf_remarks, cv_no, cdf_no); insertJV_detail_ver2(db, w);
	 * createCAliquidation(db, liq_no, cdf_no, agent_id, w);
	 * 
	 * prev_drf_no = drf_no;
	 * 
	 * System.out.printf("PREV_DRF_NO : " + drf_no + "\n"); v = v + 1; }
	 * 
	 * 
	 * old_agent_id = agent_id; }
	 * 
	 * w++; }
	 * 
	 * }
	 * 
	 * db.commit(); JOptionPane.showMessageDialog(getContentPane()
	 * ,"<html><b><i>Commission/Promo Payment Requests <html></b></i>" +
	 * "<html>successfully created.<html>","", JOptionPane.INFORMATION_MESSAGE);
	 * 
	 * 
	 * displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList,
	 * modelCommListTotal);
	 * 
	 * } } }
	 */

	private void processCDF_v2() {

		if (JOptionPane.showConfirmDialog(getContentPane(),
				"<html>To ensure that the system will generate one disbursement per agent/payee,<html>" + "\n"
						+ "<html>It is advised that list be<html>"
						+ "<b><i><u> Arranged Alphabetically<html></i></b></u>  " + "<html>. Proceed? <html> \n",
						"Alphabetical Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			if (JOptionPane.showConfirmDialog(getContentPane(),
					"<html>This process will create <html>"
							+ "<html><b><i><u>Commission Payment Request(s)<html></b></i></u>." + "\n"
							+ "<html>Are you sure all tagged accounts are correct?<html>",
							"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				/*----------COMMISSION/PROMO TABLE--------------------*/
				int x = 0;

				ArrayList<Object> listAgentID = new ArrayList<Object>();
				ArrayList<Object> listAgentName = new ArrayList<Object>();
				ArrayList<Object> listAcctCode = new ArrayList<Object>();
				ArrayList<Object> listProjAlias = new ArrayList<Object>();
				ArrayList<Object> listUnitDesc = new ArrayList<Object>();
				ArrayList<Object> listCoID = new ArrayList<Object>();
				ArrayList<Object> listPBL_ID = new ArrayList<Object>();
				ArrayList<Object> listSeqNo = new ArrayList<Object>();
				ArrayList<Object> listRecID = new ArrayList<Object>();
				ArrayList<Object> listTranType1 = new ArrayList<Object>();
				ArrayList<Object> listTranType2 = new ArrayList<Object>();
				ArrayList<Object> listCommType1 = new ArrayList<Object>();
				ArrayList<Object> listCommType2 = new ArrayList<Object>();
				ArrayList<Object> listPromoCode = new ArrayList<Object>();

				ArrayList<Double> listGrossAmt = new ArrayList<Double>();
				ArrayList<Double> listVATAmt = new ArrayList<Double>();
				ArrayList<Double> listWTaxAmt = new ArrayList<Double>();
				ArrayList<Double> listLiqAmt = new ArrayList<Double>();
				ArrayList<Double> listATMAmt = new ArrayList<Double>();
				ArrayList<Double> listNetAmt = new ArrayList<Double>();

				ArrayList<Boolean> listForDisb = new ArrayList<Boolean>();

				while (x < modelCommListTbl.getRowCount()) {

					Boolean isTrue = false;
					if (tblCommList.getValueAt(x, 0) instanceof String) {
						isTrue = new Boolean((String) tblCommList.getValueAt(x, 0));
					}
					if (tblCommList.getValueAt(x, 0) instanceof Boolean) {
						isTrue = (Boolean) tblCommList.getValueAt(x, 0);
					}

					if (isTrue) {
						String agent_name = "";
						String acct_code = "";
						String unit_desc = "";
						Boolean for_disb = true;

						agent_id = tblCommList.getValueAt(x, 18).toString().trim();
						try {
							agent_name = tblCommList.getValueAt(x, 1).toString().trim().replaceAll("'", "''");
						} catch (NullPointerException ev) {
							agent_name = "";
						}
						try {
							acct_code = tblCommList.getValueAt(x, 20).toString().trim();
						} catch (NullPointerException ev) {
							acct_code = "";
						}
						try {
							proj_alias = tblCommList.getValueAt(x, 4).toString().trim();
						} catch (NullPointerException ev) {
							proj_alias = "";
						}
						try {
							unit_desc = tblCommList.getValueAt(x, 5).toString().trim();
						} catch (NullPointerException ev) {
							unit_desc = "";
						}
						co_id = tblCommList.getValueAt(x, 19).toString().trim();
						pbl_id = tblCommList.getValueAt(x, 16).toString().trim();
						try {
							seq_no = tblCommList.getValueAt(x, 17).toString().trim();
						} catch (NullPointerException ev) {
							seq_no = "0";
						}
						gross_amt = Double.parseDouble(tblCommList.getValueAt(x, 8).toString());
						vat_amt = Double.parseDouble(tblCommList.getValueAt(x, 9).toString());
						wtax_amt = Double.parseDouble(tblCommList.getValueAt(x, 10).toString());
						liq_amt = Double.parseDouble(tblCommList.getValueAt(x, 11).toString());
						atm_fee = Double.parseDouble(tblCommList.getValueAt(x, 12).toString());
						net_amt = Double.parseDouble(tblCommList.getValueAt(x, 13).toString());
						rec_id = tblCommList.getValueAt(x, 22).toString();

						String tran_type_1 = modelCommListTbl.getValueAt(x, 6).toString().trim();
						String tran_type_2 = "AA";
						if (tran_type_1.equals("Commission")) {
							
						} else {
							tran_type_2 = "BB";
						}
						String comm_type_1 = tblCommList.getValueAt(x, 7).toString().trim();
						String comm_type_2 = "";
						String promo_code = "";
						if (comm_type_1.equals("1st CA")) {
							comm_type_2 = "1";
						} else if (comm_type_1.equals("2nd CA")) {
							comm_type_2 = "2";
						} else if (comm_type_1.equals("3rd CA")) {
							comm_type_2 = "3";
						} else if (comm_type_1.equals("4th CA")) {
							comm_type_2 = "4";
						} else if (comm_type_1.equals("Full Comm")) {
							comm_type_2 = "FC";
						} else {
							comm_type_2 = "BB";
							promo_code = sql_getPromoID(comm_type_1);
						}
						for_disb = (Boolean) modelCommListTbl.getValueAt(x, 25);

						listAgentID.add(String.format("'%s'", agent_id));
						listAgentName.add(String.format("'%s'", agent_name));
						listAcctCode.add(String.format("'%s'", acct_code));
						listProjAlias.add(String.format("'%s'", proj_alias));
						listUnitDesc.add(String.format("'%s'", unit_desc));
						listCoID.add(String.format("'%s'", co_id));
						listPBL_ID.add(String.format("'%s'", pbl_id));
						listSeqNo.add(String.format("'%s'", seq_no));
						listRecID.add(String.format("'%s'", rec_id));
						listTranType1.add(String.format("'%s'", tran_type_1));
						listTranType2.add(String.format("'%s'", tran_type_2));
						listCommType1.add(String.format("'%s'", comm_type_1));
						listCommType2.add(String.format("'%s'", comm_type_2));
						listPromoCode.add(String.format("'%s'", promo_code));

						listGrossAmt.add(gross_amt);
						listVATAmt.add(vat_amt);
						listWTaxAmt.add(wtax_amt);
						listLiqAmt.add(liq_amt);
						listATMAmt.add(atm_fee);
						listNetAmt.add(net_amt);
						listForDisb.add(for_disb);

					}
					x++;
				}

				String agentID = listAgentID.toString().replaceAll("\\[|\\]", "");
				String agentName = listAgentName.toString().replaceAll("\\[|\\]", "");
				String acctCode = listAcctCode.toString().replaceAll("\\[|\\]", "");
				String projAlias = listProjAlias.toString().replaceAll("\\[|\\]", "");
				String unitDesc = listUnitDesc.toString().replaceAll("\\[|\\]", "");
				String coID = listCoID.toString().replaceAll("\\[|\\]", "");
				String pblID = listPBL_ID.toString().replaceAll("\\[|\\]", "");
				String seqNo = listSeqNo.toString().replaceAll("\\[|\\]", "");
				String recID = listRecID.toString().replaceAll("\\[|\\]", "");
				String tranType1 = listTranType1.toString().replaceAll("\\[|\\]", "");
				String tranType2 = listTranType2.toString().replaceAll("\\[|\\]", "");
				String commtype1 = listCommType1.toString().replaceAll("\\[|\\]", "");
				String commtype2 = listCommType2.toString().replaceAll("\\[|\\]", "");
				String promoCode = listPromoCode.toString().replaceAll("\\[|\\]", "");

				String grossAmt = listGrossAmt.toString().replaceAll("\\[|\\]", "");
				String vatAmt = listVATAmt.toString().replaceAll("\\[|\\]", "");
				String wtaxAmt = listWTaxAmt.toString().replaceAll("\\[|\\]", "");
				String liqAmt = listLiqAmt.toString().replaceAll("\\[|\\]", "");
				String atmFee = listATMAmt.toString().replaceAll("\\[|\\]", "");
				String netAmt = listNetAmt.toString().replaceAll("\\[|\\]", "");
				String for_disb = listForDisb.toString().replaceAll("\\[|\\]", "");

				/*----------CASH ADVANCE TABLE--------------------*/
				int w = 0;

				ArrayList<Object> listCA_rplfNo = new ArrayList<Object>();
				ArrayList<Object> listCA_drfLineNo = new ArrayList<Object>();
				ArrayList<Object> listCA_drfRemarks = new ArrayList<Object>();
				ArrayList<Object> listCA_cvNo = new ArrayList<Object>();
				ArrayList<Object> listCA_acctID = new ArrayList<Object>();
				ArrayList<Object> listCA_expID = new ArrayList<Object>();

				ArrayList<Double> listCA_GrossAmt = new ArrayList<Double>();
				ArrayList<Double> listCA_LiqAmt = new ArrayList<Double>();
				ArrayList<Double> listCA_forLiqAmt = new ArrayList<Double>();

				while (w < modelCashAdv.getRowCount()) {

					String ca_rplf_no = "", ca_drf_line_no = "", ca_drfRemarks = "", ca_cvNo = "", ca_acctID = "",
							ca_expID = "";
					Double ca_grossAmt = 0.00, ca_liqAmt = 0.00, ca_forLiqAmt = 0.00;

					try {
						ca_rplf_no = modelCashAdv.getValueAt(w, 1).toString().trim();
					} catch (NullPointerException ev) {
						ca_rplf_no = "";
					}
					try {
						ca_drf_line_no = modelCashAdv.getValueAt(w, 2).toString().trim();
					} catch (NullPointerException ev) {
						ca_drf_line_no = "";
					}
					try {
						ca_drfRemarks = modelCashAdv.getValueAt(w, 10).toString().trim().replace("'", "''");
					} catch (NullPointerException ev) {
						ca_drfRemarks = "";
					}
					try {
						ca_cvNo = modelCashAdv.getValueAt(w, 14).toString().trim();
					} catch (NullPointerException ev) {
						ca_cvNo = "";
					}
					try {
						ca_acctID = modelCashAdv.getValueAt(w, 11).toString().trim();
					} catch (NullPointerException ev) {
						ca_acctID = "";
					}
					try {
						ca_expID = modelCashAdv.getValueAt(w, 12).toString().trim();
					} catch (NullPointerException ev) {
						ca_expID = "";
					}

					try {
						ca_grossAmt = Double.parseDouble(modelCashAdv.getValueAt(w, 5).toString());
					} catch (NullPointerException ev) {
						ca_grossAmt = 0.00;
					}
					try {
						ca_liqAmt = Double.parseDouble(modelCashAdv.getValueAt(w, 6).toString());
					} catch (NullPointerException ev) {
						ca_liqAmt = 0.00;
					}
					try {
						ca_forLiqAmt = Double.parseDouble(modelCashAdv.getValueAt(w, 7).toString());
					} catch (NullPointerException ev) {
						ca_forLiqAmt = 0.00;
					}

					listCA_rplfNo.add(String.format("'%s'", ca_rplf_no));
					listCA_drfLineNo.add(String.format("'%s'", ca_drf_line_no));
					listCA_drfRemarks.add(String.format("'%s'", ca_drfRemarks));
					listCA_cvNo.add(String.format("'%s'", ca_cvNo));
					listCA_acctID.add(String.format("'%s'", ca_acctID));
					listCA_expID.add(String.format("'%s'", ca_expID));

					listCA_GrossAmt.add(ca_grossAmt);
					listCA_LiqAmt.add(ca_liqAmt);
					listCA_forLiqAmt.add(ca_forLiqAmt);

					w++;
				}

				String caRPLFno = listCA_rplfNo.toString().replaceAll("\\[|\\]", "");
				String caDRFlineNo = listCA_drfLineNo.toString().replaceAll("\\[|\\]", "");
				String caDRFremarks = listCA_drfRemarks.toString().replaceAll("\\[|\\]", "");
				String caCVno = listCA_cvNo.toString().replaceAll("\\[|\\]", "");
				String caAcctID = listCA_acctID.toString().replaceAll("\\[|\\]", "");
				String caExpID = listCA_expID.toString().replaceAll("\\[|\\]", "");
				String caGrossAmt = listCA_GrossAmt.toString().replaceAll("\\[|\\]", "");
				String caLiqAmt = listCA_LiqAmt.toString().replaceAll("\\[|\\]", "");
				String caForLiqAmt = listCA_forLiqAmt.toString().replaceAll("\\[|\\]", "");

				Boolean with_CA = chkAcctsWithCA.isSelected();

				/*----------PROCESS COMMISSION--------------------*/
				String SQL = "SELECT comm_process_cppf(\n" +

				// Commission/Promo Table
				"   ARRAY[" + agentID + "]::VARCHAR[],\n" + "   ARRAY[" + agentName + "]::VARCHAR[],\n"
				+ "   ARRAY[" + acctCode + "]::VARCHAR[],\n" + "   ARRAY[" + projAlias + "]::VARCHAR[],\n"
				+ "   ARRAY[" + unitDesc + "]::VARCHAR[],\n" + "   ARRAY[" + coID + "]::VARCHAR[],\n"
				+ "   ARRAY[" + pblID + "]::VARCHAR[],\n" + "   ARRAY[" + seqNo + "]::VARCHAR[],\n"
				+ "   ARRAY[" + recID + "]::VARCHAR[],\n" + "   ARRAY[" + tranType1 + "]::VARCHAR[],\n"
				+ "   ARRAY[" + tranType2 + "]::VARCHAR[],\n" + "   ARRAY[" + commtype1 + "]::VARCHAR[],\n"
				+ "   ARRAY[" + commtype2 + "]::VARCHAR[],\n" + "   ARRAY[" + promoCode + "]::VARCHAR[],\n"
				+ "   ARRAY[" + grossAmt + "]::NUMERIC[],\n" + "   ARRAY[" + vatAmt + "]::NUMERIC[],\n"
				+ "   ARRAY[" + wtaxAmt + "]::NUMERIC[],\n" + "   ARRAY[" + liqAmt + "]::NUMERIC[],\n"
				+ "   ARRAY[" + atmFee + "]::NUMERIC[],\n" + "   ARRAY[" + netAmt + "]::NUMERIC[],\n"
				+ "   ARRAY[" + for_disb + "]::BOOLEAN[]," +

						// Cash Advance Table
						"   ARRAY[" + caRPLFno + "]::VARCHAR[],\n" + "   ARRAY[" + caDRFlineNo + "]::VARCHAR[],\n"
						+ "   ARRAY[" + caDRFremarks + "]::VARCHAR[],\n" + "   ARRAY[" + caCVno + "]::VARCHAR[],\n"
						+ "   ARRAY[" + caAcctID + "]::VARCHAR[],\n" + "   ARRAY[" + caExpID + "]::VARCHAR[],\n"
						+ "   ARRAY[" + caGrossAmt + "]::NUMERIC[],\n" + "   ARRAY[" + caLiqAmt + "]::NUMERIC[],\n"
						+ "   ARRAY[" + caForLiqAmt + "]::NUMERIC[],\n" +

						"	'" + UserInfo.EmployeeCode + "'::VARCHAR,\n" + "   " + with_CA + "::BOOLEAN " +

						");";

				Boolean bln = false;
				FncSystem.out("Pricelist: %s%n", SQL);
				pgSelect sdb = new pgSelect();
				sdb.select(SQL, "Save", true);
				if (sdb.isNotNull()) {
					bln = (Boolean) sdb.getResult()[0][0];
				} else {
				}

				if (bln == true) {
					JOptionPane.showMessageDialog(getContentPane(),
							"<html><b><i>Commission/Promo Payment Requests <html></b></i>"
									+ "<html>successfully created.<html>",
									"", JOptionPane.INFORMATION_MESSAGE);
					displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList, modelCommListTotal);
				} else {
					JOptionPane.showMessageDialog(getContentPane(),
							"<html><b><i>Commission/Promo Payment Requests <html></b></i>" + "<html>failed.<html>", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void saveHold() {

		int row = tblCommList.getSelectedRow();
		String tran_type = tblCommList.getValueAt(row, 6).toString().trim();

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to hold this " + tran_type + "?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String rec_id = tblCommList.getValueAt(row, 22).toString();
			String remarks = "";
			try {
				remarks = tblCommList.getValueAt(row, 14).toString();
			} catch (NullPointerException e) {
				remarks = "";
			}

			pgUpdate db = new pgUpdate();

			String sqlDetail = "update cm_schedule_dl set " + "on_hold = true ," + "remarks = '" + txtHold.getText()
			+ "'||';'||'" + remarks + "' " + "where rec_id = '" + rec_id + "' ";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), tran_type + " holding details saved.", "",
					JOptionPane.INFORMATION_MESSAGE);

			displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList, modelCommListTotal);

		}
	}

	private void saveUnhold() {

		int row = tblCommList.getSelectedRow();
		String tran_type = tblCommList.getValueAt(row, 6).toString().trim();

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to unhold this " + tran_type + "?",
				"Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String rec_id = tblCommList.getValueAt(row, 22).toString();
			String remarks = "";
			try {
				remarks = tblCommList.getValueAt(row, 14).toString();
			} catch (NullPointerException e) {
				remarks = "";
			}

			pgUpdate db = new pgUpdate();

			String sqlDetail = "update cm_schedule_dl set " + "on_hold = false ," + "remarks = 'Unhold'||';'||'"
					+ remarks + "' " + "where rec_id = '" + rec_id + "' ";

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), tran_type + " unholding details saved.", "",
					JOptionPane.INFORMATION_MESSAGE);

			displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList, modelCommListTotal);
		}
	}

	// saving validation
	private boolean nothingtoProcess() {

		boolean nothingtoProcess = true;

		for (int x = 0; x < modelCommListTbl.getRowCount(); x++) {

			Boolean isTrue = false;
			if (modelCommListTbl.getValueAt(x, 0) instanceof String) {
				isTrue = new Boolean((String) modelCommListTbl.getValueAt(x, 0));
			}
			if (modelCommListTbl.getValueAt(x, 0) instanceof Boolean) {
				isTrue = (Boolean) modelCommListTbl.getValueAt(x, 0);
			}

			if (isTrue) {
				nothingtoProcess = false;
				break;
			} else {
			}
		}
		return nothingtoProcess;

	}

	private boolean multipleEntity(String agent) {

		boolean multipleEntity = false;

		String entity = "";

		for (int x = 0; x < modelCommListTbl.getRowCount(); x++) {

			Boolean isTrue = false;
			if (tblCommList.getValueAt(x, 0) instanceof String) {
				isTrue = new Boolean((String) tblCommList.getValueAt(x, 0));
			}
			if (tblCommList.getValueAt(x, 0) instanceof Boolean) {
				isTrue = (Boolean) tblCommList.getValueAt(x, 0);
			}

			if (isTrue) {
				entity = tblCommList.getValueAt(x, 18).toString().trim();

				if (agent.equals(entity)) {
					multipleEntity = false;
				}

				else {
					multipleEntity = true;
					break;
				}
			} else {
			}
		}
		return multipleEntity;

	}

	private void setQualFalse() {

		Integer rw = modelCommListTbl.getRowCount();
		Integer x = 0;

		while (x < rw) {

			modelCommListTbl.setValueAt(false, x, 0);

			x++;
		}

	}

	private boolean checkForHoldComm() {

		Boolean theres_hold_comm = false;

		for (int x = 0; x < modelCommListTbl.getRowCount(); x++) {

			if ((Boolean) modelCommListTbl.getValueAt(x, 21) == true
					&& (Boolean) modelCommListTbl.getValueAt(x, 0) == true) {
				theres_hold_comm = true;
				break;
			} else {
				theres_hold_comm = false;
			}
		}
		return theres_hold_comm;

	}

	private boolean checkForMissingTIN() {

		Boolean theres_missing_tin = false;

		for (int x = 0; x < modelCommListTbl.getRowCount(); x++) {

			String tin = "";

			try {
				tin = tblCommList.getValueAt(tblCommList.convertRowIndexToModel(x), 23).toString();
			} catch (NullPointerException e) {
				tin = "";
			}

			if ((Boolean) modelCommListTbl.getValueAt(x, 0) == true && tin.equals("")) {
				theres_missing_tin = true;
				break;
			} else {
				theres_missing_tin = false;
			}
		}
		return theres_missing_tin;

	}

	// computation
	private void computeCAAmounts(KeyEvent evt) {

		int r = tblCashAdv.getSelectedRow();

		double ca_amt = Double.parseDouble(modelCashAdv.getValueAt(r, 5).toString());
		double liq_amt = Double.parseDouble(modelCashAdv.getValueAt(r, 6).toString());
		double for_liq_amt = Double.parseDouble(modelCashAdv.getValueAt(r, 7).toString());
		double os_ca = ca_amt - for_liq_amt - liq_amt;

		if (for_liq_amt + liq_amt > ca_amt) {
			JOptionPane.showMessageDialog(getContentPane(),
					"Amount indicated for liquidation is greater than CA amount. \n", "Error",
					JOptionPane.ERROR_MESSAGE);
			modelCashAdv.setValueAt(new BigDecimal(0.00), r, 7);
		}

		else {

			BigDecimal for_liq_amt_bd = new BigDecimal(for_liq_amt);
			BigDecimal os_ca_bd = new BigDecimal(os_ca);

			modelCashAdv.setValueAt(for_liq_amt_bd, r, 7);
			modelCashAdv.setValueAt(os_ca_bd, r, 8);
			totalCA(modelCashAdv, modelCashAdv_total);

			distributeLiqAmt();
		}
	}

	private void distributeLiqAmt() {

		double liq_amt = Double.parseDouble(modelCashAdv_total.getValueAt(0, 7).toString());
		System.out.printf("liq_amt :" + liq_amt);

		for (int x = 0; x < modelCommListTbl.getRowCount(); x++) {

			Boolean isTrue = false;
			if (modelCommListTbl.getValueAt(x, 0) instanceof String) {
				isTrue = new Boolean((String) modelCommListTbl.getValueAt(x, 0));
			}
			if (modelCommListTbl.getValueAt(x, 0) instanceof Boolean) {
				isTrue = (Boolean) modelCommListTbl.getValueAt(x, 0);
			}

			if (isTrue) {

				double net = 0.00;

				if (liq_amt > 0) {
					double grs = Double.parseDouble(modelCommListTbl.getValueAt(x, 8).toString());
					double tax = Double.parseDouble(modelCommListTbl.getValueAt(x, 10).toString());
					double atm = Double.parseDouble(modelCommListTbl.getValueAt(x, 12).toString());
					net = grs - tax - atm;
					double liq = 0.00;

					if (net <= liq_amt) {
						liq = net;
					} else {
						liq = liq_amt;
					}

					double net_new = grs - tax - liq;

					BigDecimal liq_bd = new BigDecimal(liq);
					BigDecimal net_new_bd = new BigDecimal(net_new);

					modelCommListTbl.setValueAt(liq_bd, x, 11);
					modelCommListTbl.setValueAt(net_new_bd, x, 13);

					// enable/disable creation of request for payment (10/20/2016 : Del G)
					if (df.format(net_new).toString().equals("0.00")) {
						modelCommListTbl.setValueAt(false, x, 25);
						System.out.printf("net_new_bd :" + net_new_bd);
					} else {
						modelCommListTbl.setValueAt(true, x, 25);
						System.out.printf("net_new_bd :" + net_new_bd);
					}

					totalAllComm(modelCommListTbl, modelCommListTotal);

					liq_amt = liq_amt - liq;
				}

			} else {
			}
		}

		if (liq_amt > 0) {
			JOptionPane
			.showMessageDialog(getContentPane(),
					"Amount indicated for liquidation \n" + "is greater than the indicated commission. \n"
							+ "Please indicate a lower liquidation amount.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private boolean liqui_amounts_equal() {

		boolean liqui_amounts_equal = false;
		double liq_amt = 0.00;
		
		try {
			liq_amt = Double.parseDouble(modelCashAdv_total.getValueAt(0, 7).toString()); //ADDED BY LESTER TO CATCH COMM W/O LIQUIDATION FOR SAME AGENT BUT DIFFERENT UNITS
		} catch (NullPointerException ev) {
			liq_amt = 0.00;
		}
		
		double liq = 0.00;
		for (int x = 0; x < modelCommListTbl.getRowCount(); x++) {

			Boolean isTrue = false;
			if (modelCommListTbl.getValueAt(x, 0) instanceof String) {
				isTrue = new Boolean((String) modelCommListTbl.getValueAt(x, 0));
			}
			if (modelCommListTbl.getValueAt(x, 0) instanceof Boolean) {
				isTrue = (Boolean) modelCommListTbl.getValueAt(x, 0);
			}

			if (isTrue) {

				if (liq_amt > 0) {
					Double liq_x = Double.parseDouble(modelCommListTbl.getValueAt(x, 11).toString());
					liq = liq + liq_x;
				}

			}

			if (liq_amt == liq) {
				liqui_amounts_equal = true;
			} else {
			}

		}
		return liqui_amounts_equal;

	}

	// SQL
	private static Double sql_wtaxRate(String pblID, String seqNo) {

		double a = 0.00;

		String SQL = "select entity_id from rf_entity_tax_waiver where status_id = 'A' and entity_id in \n"
				+ "(select sellingagent from rf_sold_unit where pbl_id = '" + pblID + "' and seq_no::text = '" + seqNo
				+ "') ";

		System.out.printf("SQL #1: sql_wtaxRate", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0].toString() == null || db.getResult()[0][0].toString().equals("null")) {
				a = 0.15;
			} else {
				a = 0.10;
			}

		} else {
			a = 0.15;
		}

		return a;
	}

	private void initialize_comp() {
		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION";
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();
		
		SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");
				lookupCompany.setEnabled(false);
				
				pgUpdate dbExec = new pgUpdate(); 
				dbExec.executeUpdate("call sp_comm_check_qualifications(); ", false);
				dbExec.commit();
				
				displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList, modelCommListTotal);
				refreshTable();

				lookupCompany.setEnabled(true);
				btnProcess.setEnabled(true);
				btnSetupCA.setEnabled(false);
				btnRefresh.setEnabled(true);
				btnPreview.setEnabled(true);

				lblAgentName.setEnabled(true);
				txtAgentName.setEnabled(true);
				chkAcctsWithCA.setEnabled(true);
				chkCancAccounts.setEnabled(true);
				btnQualify.setEnabled(false);

				chkAcctsWithCA.setSelected(false);
				chkCancAccounts.setSelected(false);

				modelCashAdv.setEditable(false);

				lookupCompany.setValue(co_id);
				initialize_variables();

				tblCommList.setSortable(false);
				FncGlobal.stopProgress();
				return null;
			}
		}; 
		sw.execute(); 
	}
	
	private void refresh() {

		SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");

				displayClient_All_agentsCommission(modelCommListTbl, rowHeaderCommList, modelCommListTotal);
				FncTables.clearTable(modelCashAdv);
				DefaultListModel listModel = new DefaultListModel();
				rowHeaderCashAdv.setModel(listModel);
				modelCashAdv_total = new modelComm_agentCA_process();
				modelCashAdv_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00),
						new BigDecimal(0.00), null, null });

				tblCashAdv_total = new _JTableTotal(modelCashAdv_total, tblCashAdv);
				tblCashAdv_total.setFont(dialog11Bold);
				scrollCashAdv_total.setViewportView(tblCashAdv_total);
				((_JTableTotal) tblCashAdv_total).setTotalLabel(0);

				chkCancAccounts.setSelected(false);
				chkAcctsWithCA.setSelected(false);
				btnQualify.setEnabled(false);

				JOptionPane.showMessageDialog(getContentPane(), "List of commission for processing has been refreshed." + "\n", "Information", JOptionPane.INFORMATION_MESSAGE);
				
				FncGlobal.stopProgress();
				return null;
			}
		}; 
		sw.execute(); 
	}

}