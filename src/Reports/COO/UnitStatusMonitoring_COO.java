package Reports.COO;

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
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelComm_agentCA_liquidation;
import tablemodel.modelComm_agentCA_list;
import tablemodel.modelComm_cancCPF;
import tablemodel.modelComm_client_RelComm;
import tablemodel.modelComm_client_commSchedule;
import tablemodel.modelComm_client_list;
import tablemodel.modelComm_client_promo;
import tablemodel.modelPV_SubLedger;
import tablemodel.modelUnitStatusCOO_listOfUnits;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Functions.FncTables;
import Home.Home_JSystem;
import Lookup._JLookup;
import Utilities.SalesAgent;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;

public class UnitStatusMonitoring_COO extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Unit Status Monitoring (COO";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlClient_CommSched;
	private JPanel pnlClient_Promo;
	private JPanel pnlClient_Rel_Commission;
	private JPanel pnlClient_Canc_CPF;
	private JPanel pnlSubTable;
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
	private JLabel lblClientName;
	private _JLookup lookupAgentName;

	private modelPV_SubLedger modelJV_SL_total;
	private modelPV_SubLedger modelJV_account;
	private modelPV_SubLedger modelJV_SL;
	private modelPV_SubLedger modelJV_accounttotal;

	private modelUnitStatusCOO_listOfUnits modelClientTbl;
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
	private modelComm_agentCA_list modelAgent_CA_list;
	private modelComm_agentCA_liquidation modelAgent_CA_liqui;
	private modelComm_agentCA_liquidation modelAgent_CA_liqui_total;	

	private _JTabbedPane tabMain;	
	private _JTabbedPane tabClientDetails;	
	private _JTabbedPane tabClient_Canc_CPF;

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
	private _JScrollPaneMain scrollAgent_CA_liqui;		

	private _JScrollPaneTotal scrollClient_CommSchedtotal;
	private _JScrollPaneTotal scrollClient_Promo_total;
	private _JScrollPaneTotal scrollClientRel_comm_total;	
	private _JScrollPaneTotal scrollClient_Canc_CPF_comm_total;
	private _JScrollPaneTotal scrollClient_Canc_CPF_promo_total;
	private JXTextField txtClientName;

	private JCheckBox chkCancAccounts;	

	private _JTableMain tblJV_SL;
	private _JTableMain tblJV_account;
	private _JTableMain tblClientList;
	private _JTableMain tblClient_CommSched;	
	private _JTableMain tblClient_Promo;
	private _JTableMain tblClientRel_comm;
	private _JTableMain tblClient_Canc_CPF_comm;
	private _JTableMain tblClient_Canc_CPF_promo;
	private _JTableMain tblAgentAccount;
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
	private JList rowHeaderAgent_CA_liqui;	

	private _JTableTotal tblJV_SLtotal;
	private _JTableTotal tblClient_CommSched_total;
	private _JTableTotal tblClient_Promo_total;
	private _JTableTotal tblClientRel_comm_total;
	private _JTableTotal tblClient_Canc_CPF_comm_total;
	private _JTableTotal tblClient_Canc_CPF_promo_total;	

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);

	String co_id 	= "";		
	String company 	= "";
	String company_logo;


	String agent_id = "";
	String pbl_id 	= "";
	String seq_no 	= "";
	String entity_id = "";
	String entity_name = "";
	String comm_no 	= "";
	String description 	= "";
	Double wTaxrate 	= null;
	
	String div_id = "";
	String sales_grp_id = "";

	private JPopupMenu menu;
	private JPopupMenu menu2;
	private JMenuItem mniopenRPLF;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenJV;
	private JMenuItem mniopenCPF;
	private JMenuItem mniOpenAgentInfo;
	private JSplitPane splitPanel_A;
	private _JScrollPaneTotal scrollClientTbl_total;
	private modelUnitStatusCOO_listOfUnits modelClientTbl_total;
	private _JTableTotal tblClientList_total;
	private _JScrollPaneTotal scrollAgentAccount_total;
	private modelComm_client_list modelAgentAccount_total;
	private _JTableTotal tblAgentAccount_total;	

	public UnitStatusMonitoring_COO() {
		super(title, true, true, true, true);
		initGUI();
	}

	public UnitStatusMonitoring_COO(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public UnitStatusMonitoring_COO(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {

		{
			menu = new JPopupMenu("Popup");
			mniOpenAgentInfo = new JMenuItem("Open Agent Details");
			menu.add(mniOpenAgentInfo);	
			mniOpenAgentInfo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));			
			mniOpenAgentInfo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					openAgentInfo();
				}
			});

		}
		{
			menu2 = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenPV = new JMenuItem("Open Payable Voucher");
			mniopenJV= new JMenuItem("Open Journal Voucher");	
			mniopenCPF= new JMenuItem("Commission Payout Form (CPF)");
			menu2.add(mniopenRPLF);
			menu2.add(mniopenPV);
			menu2.add(mniopenJV);
			menu2.add(mniopenCPF);
			mniopenRPLF.setEnabled(true);
			mniopenPV.setEnabled(true);
			mniopenJV.setEnabled(true);
			mniopenCPF.setEnabled(true);

			mniopenRPLF.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openDRF();				
				}
			});
			mniopenPV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					//openPV();				
				}
			});
			mniopenJV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					//openJV();				
				}
			});
			mniopenJV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					//openCPF();				
				}
			});

		}

		this.setLayout(new BorderLayout(5,5));
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
				/*pnlbyClient = new JPanel(new BorderLayout());
				pnlSubTable.addTab("             by Client                ", null, pnlbyClient, null);
				pnlbyClient.setPreferredSize(new java.awt.Dimension(1183, 365));	*/

				splitPanel_A = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				pnlSubTable.add(splitPanel_A);
				splitPanel_A.setOneTouchExpandable(true);
				splitPanel_A.setResizeWeight(.7d);		

				pnlbyClient_north = new JPanel();				
				splitPanel_A.add(pnlbyClient_north, JSplitPane.LEFT);
				pnlbyClient_north.setLayout(new BorderLayout(5, 5));
				pnlbyClient_north.setBorder(lineBorder);		
				pnlbyClient_north.setPreferredSize(new java.awt.Dimension(1040, 225));				
				pnlbyClient_north.setBorder(JTBorderFactory.createTitleBorder("Unit List"));

				pnlClientName = new JPanel(new BorderLayout(5, 5));
				pnlbyClient_north.add(pnlClientName, BorderLayout.NORTH);	
				pnlClientName.setPreferredSize(new java.awt.Dimension(1005, 42));	

				pnlClientName_a = new JPanel(new GridLayout(1, 1, 5, 10));
				pnlClientName.add(pnlClientName_a, BorderLayout.WEST);	
				pnlClientName_a.setPreferredSize(new java.awt.Dimension(114, 42));	
				pnlClientName_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					lblClientName = new JLabel("Phase-Block-Lot :", JLabel.TRAILING);
					pnlClientName_a.add(lblClientName);
					lblClientName.setEnabled(true);	
					lblClientName.setPreferredSize(new java.awt.Dimension(86, 40));
					lblClientName.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
					lblClientName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}		

				pnlClientName_b = new JPanel(new BorderLayout(5, 5));
				pnlClientName.add(pnlClientName_b, BorderLayout.CENTER);	
				pnlClientName_b.setPreferredSize(new java.awt.Dimension(918, 42));	
				pnlClientName_b.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

				pnlClientName_b_sub1 = new JPanel(new GridLayout(1, 1, 5, 10));
				pnlClientName_b.add(pnlClientName_b_sub1, BorderLayout.WEST);	
				pnlClientName_b_sub1.setPreferredSize(new java.awt.Dimension(235, 26));					
				{
					txtClientName= new JXTextField("");
					pnlClientName_b_sub1.add(txtClientName);
					txtClientName.setEnabled(true);	
					txtClientName.setEditable(true);
					txtClientName.setBounds(120, 25, 300, 22);	
					txtClientName.setHorizontalAlignment(JTextField.LEFT);	
					txtClientName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
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
								displayAllUnits(modelClientTbl, rowHeaderClientTbl, "", "I", tblClientList, modelClientTbl_total );	
							} else {
								displayAllUnits(modelClientTbl, rowHeaderClientTbl, "", "A", tblClientList, modelClientTbl_total );	
							}
						}
					});
				}	

				pnlClientName_b_sub3 = new JPanel(new GridLayout(1, 2,5,0));
				pnlClientName_b.add(pnlClientName_b_sub3, BorderLayout.EAST);	
				pnlClientName_b_sub3.setPreferredSize(new java.awt.Dimension(310, 20));	
				pnlClientName_b_sub3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));		

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
						modelClientTbl = new modelUnitStatusCOO_listOfUnits();

						tblClientList = new _JTableMain(modelClientTbl);
						scrollClientTbl.setViewportView(tblClientList);
						tblClientList.addMouseListener(this);	
						tblClientList.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {generateClientSchedule();}							
							public void keyPressed(KeyEvent e) {generateClientSchedule();}

						}); 
						tblClientList.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblClientList.rowAtPoint(e.getPoint()) == -1){}
								else{tblClientList.setCellSelectionEnabled(true);}
								generateClientSchedule();
							}
							public void mouseReleased(MouseEvent e) {
								if(tblClientList.rowAtPoint(e.getPoint()) == -1){}
								else{tblClientList.setCellSelectionEnabled(true);}
							}
						});
					}
					{
						rowHeaderClientTbl = tblClientList.getRowHeader22();
						scrollClientTbl.setRowHeaderView(rowHeaderClientTbl);
						scrollClientTbl.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollClientTbl_total = new _JScrollPaneTotal(scrollClientTbl);
						pnlClientTable.add(scrollClientTbl_total, BorderLayout.SOUTH);
						{
							modelClientTbl_total = new modelUnitStatusCOO_listOfUnits();
							modelClientTbl_total.addRow(new Object[] { "Total", null, null, null, null,  new BigDecimal(0.00) });

							tblClientList_total = new _JTableTotal(modelClientTbl_total, tblClientList);
							tblClientList_total.setFont(dialog11Bold);
							scrollClientTbl_total.setViewportView(tblClientList_total);
							((_JTableTotal) tblClientList_total).setTotalLabel(0);
						}
					}

					displayAllUnits(modelClientTbl, rowHeaderClientTbl, "", "A", tblClientList, modelClientTbl_total );	
				}				

				pnlClientDetails = new JPanel();
				//pnlSplit.add(pnlClientDetails,BorderLayout.CENTER);
				splitPanel_A.add(pnlClientDetails, JSplitPane.RIGHT);
				pnlClientDetails.setLayout(new BorderLayout(5, 5));
				pnlClientDetails.setBorder(lineBorder);		
				pnlClientDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlClientDetails.setBorder(JTBorderFactory.createTitleBorder("Unit Details"));

				tabClientDetails = new _JTabbedPane();
				pnlClientDetails.add(tabClientDetails, BorderLayout.CENTER);	

				//start of Commission Schedule (by client)
				{
					pnlClient_CommSched = new JPanel(new BorderLayout());
					tabClientDetails.addTab("Buyers", null, pnlClient_CommSched, null);
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
								public void keyReleased(KeyEvent evt) {tblClient_CommSched.packAll();}							
								public void keyPressed(KeyEvent e) {tblClient_CommSched.packAll();}	

							}); 
							tblClient_CommSched.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblClient_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblClient_CommSched.setCellSelectionEnabled(true);}

									int row = tblClient_CommSched.getSelectedRow();		
									String comm_type = tblClient_CommSched.getValueAt(row,3).toString().trim();

									if (comm_type.equals("1st CA")) {comm_no  = "1";}
									else if (comm_type.equals("1st CA")) {comm_no  = "1";}
									else if (comm_type.equals("2nd CA")) {comm_no  = "2";}
									else if (comm_type.equals("3rd CA")) {comm_no  = "3";}
									else if (comm_type.equals("4th CA")) {comm_no  = "4";}
									else if (comm_type.equals("Full Comm")) {comm_no  = "FC";}

								}
								public void mouseReleased(MouseEvent e) {
									if(tblClient_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblClient_CommSched.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderClient_CommSched = tblClient_CommSched.getRowHeader22();
							scrollClient_CommSched.setRowHeaderView(rowHeaderClient_CommSched);
							scrollClient_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollClient_CommSchedtotal = new _JScrollPaneTotal(scrollClient_CommSched);
						pnlClient_CommSched.add(scrollClient_CommSchedtotal, BorderLayout.SOUTH);
						{
							modelClient_CommSched_total = new modelComm_client_commSchedule();
							modelClient_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null, null });

							tblClient_CommSched_total = new _JTableTotal(modelClient_CommSched_total, tblClient_CommSched);
							tblClient_CommSched_total.setFont(dialog11Bold);
							scrollClient_CommSchedtotal.setViewportView(tblClient_CommSched_total);
							((_JTableTotal) tblClient_CommSched_total).setTotalLabel(0);
						}
					}
				} 
				//end of Commission Schedule (by client)

				//start of Promo (by client)
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
								public void keyReleased(KeyEvent evt) {tblClient_Promo.packAll();}							
								public void keyPressed(KeyEvent e) {tblClient_Promo.packAll();}	
							}); 
							tblClient_Promo.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblClient_Promo.rowAtPoint(e.getPoint()) == -1){tblClient_Promo_total.clearSelection();}
									else{tblClient_Promo.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblClient_Promo.rowAtPoint(e.getPoint()) == -1){tblClient_Promo_total.clearSelection();}
									else{tblClient_Promo.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderClient_Promo = tblClient_Promo.getRowHeader22();
							scrollClient_Promo.setRowHeaderView(rowHeaderClient_Promo);
							scrollClient_Promo.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollClient_Promo_total = new _JScrollPaneTotal(scrollClient_Promo);
						pnlClient_Promo.add(scrollClient_Promo_total, BorderLayout.SOUTH);
						{
							modelClient_Promo_total = new modelComm_client_promo();
							modelClient_Promo_total.addRow(new Object[] { "Total",null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
									new BigDecimal(0.00), null, null, null, null, null, null, null });

							tblClient_Promo_total = new _JTableTotal(modelClient_Promo_total, tblClient_Promo);
							tblClient_Promo_total.setFont(dialog11Bold);
							scrollClient_Promo_total.setViewportView(tblClient_Promo_total);
							((_JTableTotal) tblClient_Promo_total).setTotalLabel(0);
						}
					}
				}
				//end of Promo (by client)

				//start of Released Commission (by client)
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
								public void keyReleased(KeyEvent evt) {tblClientRel_comm.packAll();}							
								public void keyPressed(KeyEvent e) {tblClientRel_comm.packAll();}	
							}); 
							tblClientRel_comm.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblClientRel_comm.rowAtPoint(e.getPoint()) == -1){tblJV_SLtotal.clearSelection();}
									else{tblClientRel_comm.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblClientRel_comm.rowAtPoint(e.getPoint()) == -1){tblJV_SLtotal.clearSelection();}
									else{tblClientRel_comm.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderClientRel_comm = tblClientRel_comm.getRowHeader22();
							scrollClientRel_comm.setRowHeaderView(rowHeaderClientRel_comm);
							scrollClientRel_comm.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollClientRel_comm_total = new _JScrollPaneTotal(scrollClientRel_comm);
						pnlClient_Rel_Commission.add(scrollClientRel_comm_total, BorderLayout.SOUTH);
						{
							modelClientRel_comm_total = new modelComm_client_RelComm();
							modelClientRel_comm_total.addRow(new Object[] {  "Total", null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
									null, null, null });

							tblClientRel_comm_total = new _JTableTotal(modelClientRel_comm_total, tblClientRel_comm);
							tblClientRel_comm_total.setFont(dialog11Bold);
							scrollClientRel_comm_total.setViewportView(tblClientRel_comm_total);
							((_JTableTotal) tblClientRel_comm_total).setTotalLabel(0);
						}
					}
				}
				//end of Released Commission (by client)

				//start of Canceled CPFs (by client)
				{
					pnlClient_Canc_CPF = new JPanel(new BorderLayout());
					tabClientDetails.addTab("        Canceled CPFs        ", null,
 pnlClient_Canc_CPF, null);
					pnlClient_Canc_CPF.setPreferredSize(new java.awt.Dimension(1183, 365));	

					tabClient_Canc_CPF =
 new _JTabbedPane();
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
									public void keyReleased(KeyEvent evt) {tblClient_Canc_CPF_comm.packAll();}							
									public void keyPressed(KeyEvent e) {tblClient_Canc_CPF_comm.packAll();}
								}); 
								tblClient_Canc_CPF_comm.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblClient_Canc_CPF_comm.rowAtPoint(e.getPoint()) == -1){tblClient_Canc_CPF_comm_total.clearSelection();}
										else{tblClient_Canc_CPF_comm.setCellSelectionEnabled(true);}
									}
									public void mouseReleased(MouseEvent e) {
										if(tblClient_Canc_CPF_comm.rowAtPoint(e.getPoint()) == -1){tblClient_Canc_CPF_comm_total.clearSelection();}
										else{tblClient_Canc_CPF_comm.setCellSelectionEnabled(true);}
									}								
								});

							}
							{
								rowHeaderClient_Canc_CPF_comm = tblClient_Canc_CPF_comm.getRowHeader22();
								scrollClient_Canc_CPF_comm.setRowHeaderView(rowHeaderClient_Canc_CPF_comm);
								scrollClient_Canc_CPF_comm.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollClient_Canc_CPF_comm_total = new _JScrollPaneTotal(scrollClient_Canc_CPF_comm);
							pnlClient_Canc_CPF_comm.add(scrollClient_Canc_CPF_comm_total, BorderLayout.SOUTH);
							{
								modelClient_Canc_CPF_comm_total = new modelComm_cancCPF();
								modelClient_Canc_CPF_comm_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
										new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

								tblClient_Canc_CPF_comm_total = new _JTableTotal(modelClient_Canc_CPF_comm_total, tblClient_Canc_CPF_comm);
								tblClient_Canc_CPF_comm_total.setFont(dialog11Bold);
								scrollClient_Canc_CPF_comm_total.setViewportView(tblClient_Canc_CPF_comm_total);
								((_JTableTotal) tblClient_Canc_CPF_comm_total).setTotalLabel(0);
							}
						}

					}
					{
						pnlClient_Canc_CPF_promo = new JPanel(new BorderLayout());
						tabClient_Canc_CPF.addTab("        Promo/Incentive        ", null, pnlClient_Canc_CPF_promo, null);
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
									public void keyReleased(KeyEvent evt) {tblClient_Canc_CPF_promo.packAll();}							
									public void keyPressed(KeyEvent e) {tblClient_Canc_CPF_promo.packAll();}
								}); 
								tblClient_Canc_CPF_promo.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblClient_Canc_CPF_promo.rowAtPoint(e.getPoint()) == -1){tblClient_Canc_CPF_promo_total.clearSelection();}
										else{tblClient_Canc_CPF_promo.setCellSelectionEnabled(true);}
									}
									public void mouseReleased(MouseEvent e) {
										if(tblClient_Canc_CPF_promo.rowAtPoint(e.getPoint()) == -1){tblClient_Canc_CPF_promo_total.clearSelection();}
										else{tblClient_Canc_CPF_promo.setCellSelectionEnabled(true);}
									}								
								});

							}
							{
								rowHeaderClient_Canc_CPF_promo = tblClient_Canc_CPF_promo.getRowHeader22();
								scrollClient_Canc_CPF_promo.setRowHeaderView(rowHeaderClient_Canc_CPF_promo);
								scrollClient_Canc_CPF_promo.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollClient_Canc_CPF_promo_total = new _JScrollPaneTotal(scrollClient_Canc_CPF_promo);
							pnlClient_Canc_CPF_promo.add(scrollClient_Canc_CPF_promo_total, BorderLayout.SOUTH);
							{
								modelClient_Canc_CPF_promo_total = new modelComm_cancCPF();
								modelClient_Canc_CPF_promo_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
										new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00) });

								tblClient_Canc_CPF_promo_total = new _JTableTotal(modelClient_Canc_CPF_promo_total, tblClient_Canc_CPF_promo);
								tblClient_Canc_CPF_promo_total.setFont(dialog11Bold);
								scrollClient_Canc_CPF_promo_total.setViewportView(tblClient_Canc_CPF_promo_total);
								((_JTableTotal) tblClient_Canc_CPF_promo_total).setTotalLabel(0);
							}
						}

					}
				}
				//end of Promo (by client)
			}

		} 
		/*{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{

				{
					btnEdit = new JButton("Edit");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}*/
	}


	//display tables
	public void displayAllUnits(DefaultTableModel modelMain, JList rowHeader, String agent_id, String status, _JTableMain table, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 						
				"SELECT * FROM (\r\n" + 
				"\r\n" + 
				"/****NO MERGED UNITS***/\r\n" + 
				"select \r\n" + 
				"\r\n" + 
				"--" +
				"a.pbl_id,\r\n" + 
				"a.phase,\r\n" + 
				"a.block,\r\n" + 
				"a.lot,\r\n" + 
				"(case when a.release_batch is null then '1' else a.release_batch end) batch,\r\n" + 
				"b.model_alias,\r\n" + 
				"g.pmt_scheme_desc,\r\n" + 
				"to_char(a.date_created,'MM-dd-yyyy') as date_opened,\r\n" + 
				"to_char(c.tran_date,'MM-dd-yyyy') as tr_date,\r\n" + 
				"to_char(d.tran_date,'MM-dd-yyyy') as or_date,\r\n" + 
				"to_char(e.tran_date,'MM-dd-yyyy') as docs_comp,\r\n" + 
				"to_char(a.ntc,'MM-dd-yyyy') as ntc_comp,\r\n" + 
				"to_char(i.award_date,'MM-dd-yyyy') as award_date,\r\n" + 
				"--j.perc_comp,\r\n" + 
				"(case when j.perc_comp >= 100 then to_char(j.comp_date,'MM-dd-yyyy') else null end) as comp_date\r\n" + 
				"--(select max(percent_accomplish)from (select * from co_ntp_accomplishment where pbl_id = a.pbl_id) a where status_id = 'A' ) --check actual perc_accomp\r\n" + 
				"\r\n" + 
				"from (select * from mf_unit_info where status_id != 'I' and pbl_id not in (select oth_pbl_id from hs_sold_other_lots where status_id = 'A') order by pbl_id::int) a\r\n" + 
				"left join mf_product_model b on a.model_id = b.model_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id = '17' and status_id = 'A' order by pbl_id, seq_no desc ) a ) c on a.pbl_id = c.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id = '01' and status_id = 'A' order by pbl_id, seq_no desc	) a ) d on a.pbl_id = d.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id in ( '18', '20') and status_id = 'A' order by pbl_id, seq_no desc ) a ) e on a.pbl_id = e.pbl_id \r\n" + 
				"left join (select distinct on (pbl_id) * from (select distinct on (pbl_id, seq_no) * from rf_sold_unit where status_id = 'A' order by pbl_id, seq_no desc) a ) f\r\n" + 
				"	on a.pbl_id = f.pbl_id \r\n" + 
				"left join mf_payment_scheme g on f.pmt_scheme_id = g.pmt_scheme_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id = '18' and status_id = 'A' order by pbl_id, seq_no desc ) a ) h on a.pbl_id = h.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select b.ntp_no, a.pbl_id, a.perc_comp, b.award_date\r\n" + 
				"	from (select distinct on (pbl_id::int) pbl_id, ntp_no, max(percent_accomplish) as perc_comp \r\n" + 
				"		from co_ntp_accomplishment a where status_id = 'A' group by pbl_id, ntp_no order by pbl_id::int 		 \r\n" + 
				"		) a\r\n" + 
				"	left join (select * from co_ntp_header where status_id !='I') b on a.ntp_no = b.ntp_no \r\n" + 
				"	) i on a.pbl_id = i.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select a.ntp_no, a.pbl_id, a.perc_comp, a.comp_date\r\n" + 
				"	from (select distinct on (pbl_id::int) pbl_id, ntp_no, max(percent_accomplish) as perc_comp, max(as_of_date) as comp_date\r\n" + 
				"		from co_ntp_accomplishment a where status_id = 'A' and percent_accomplish > 0 \r\n" + 
				"		group by pbl_id, ntp_no, as_of_date order by pbl_id::int 		 \r\n" + 
				"		) a\r\n" + 
				"	where a.perc_comp > 0\r\n" + 
				"	) j on a.pbl_id = j.pbl_id \r\n" + 
				"	\r\n" + 
				"\r\n" + 
				"UNION ALL\r\n" + 
				"\r\n" + 
				"/****WITH MERGED UNITS***/\r\n" + 
				"select \r\n" + 
				"\r\n" + 
				"--a.pbl_id,\r\n" + 
				"a.phase,\r\n" + 
				"a.block,\r\n" + 
				"a.lot,\r\n" + 
				"(case when a.release_batch is null then '1' else a.release_batch end) batch,\r\n" + 
				"b.model_alias,\r\n" + 
				"g.pmt_scheme_desc,\r\n" + 
				"to_char(a.date_created,'MM-dd-yyyy') as date_opened,\r\n" + 
				"to_char(c.tran_date,'MM-dd-yyyy') as tr_date,\r\n" + 
				"to_char(d.tran_date,'MM-dd-yyyy') as or_date,\r\n" + 
				"to_char(e.tran_date,'MM-dd-yyyy') as docs_comp,\r\n" + 
				"to_char(x.ntc,'MM-dd-yyyy') as ntc_comp,\r\n" + 
				"to_char(i.award_date,'MM-dd-yyyy') as award_date,\r\n" + 
				"--j.perc_comp,\r\n" + 
				"(case when j.perc_comp >= 100 then to_char(j.comp_date,'MM-dd-yyyy') else null end) as comp_date\r\n" + 
				"--(select max(percent_accomplish)from (select * from co_ntp_accomplishment where pbl_id = a.pbl_id) a where status_id = 'A' ) --check actual perc_accomp\r\n" + 
				"\r\n" + 
				"from (select * from hs_sold_other_lots) aa \r\n" + 
				"left join (select * from mf_unit_info) a on aa.oth_pbl_id = a.pbl_id\r\n" + 
				"left join (select * from mf_unit_info) x on aa.pbl_id = x.pbl_id\r\n" + 
				"left join mf_product_model b on a.model_id = b.model_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id = '17' and status_id = 'A' order by pbl_id, seq_no desc ) a ) c on aa.pbl_id = c.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id = '01' and status_id = 'A' order by pbl_id, seq_no desc	) a ) d on aa.pbl_id = d.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id in ( '18', '20') and status_id = 'A' order by pbl_id, seq_no desc ) a ) e on aa.pbl_id = e.pbl_id \r\n" + 
				"left join (select distinct on (pbl_id) * from (select distinct on (pbl_id, seq_no) * from rf_sold_unit where status_id = 'A' order by pbl_id, seq_no desc) a ) f\r\n" + 
				"	on aa.pbl_id = f.pbl_id \r\n" + 
				"left join mf_payment_scheme g on f.pmt_scheme_id = g.pmt_scheme_id \r\n" + 
				"left join (\r\n" + 
				"	select distinct on (pbl_id) pbl_id, tran_date from  ( select distinct on (pbl_id, seq_no) pbl_id, seq_no, tran_date \r\n" + 
				"	from rf_buyer_status where byrstatus_id = '18' and status_id = 'A' order by pbl_id, seq_no desc ) a ) h on aa.pbl_id = h.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select b.ntp_no, a.pbl_id, a.perc_comp, b.award_date\r\n" + 
				"	from (select distinct on (pbl_id::int) pbl_id, ntp_no, max(percent_accomplish) as perc_comp \r\n" + 
				"		from co_ntp_accomplishment a where status_id = 'A' group by pbl_id, ntp_no order by pbl_id::int 		 \r\n" + 
				"		) a\r\n" + 
				"	left join (select * from co_ntp_header where status_id !='I') b on a.ntp_no = b.ntp_no \r\n" + 
				"	) i on a.pbl_id = i.pbl_id \r\n" + 
				"left join (\r\n" + 
				"	select a.ntp_no, a.pbl_id, a.perc_comp, a.comp_date\r\n" + 
				"	from (select distinct on (pbl_id::int) pbl_id, ntp_no, max(percent_accomplish) as perc_comp, max(as_of_date) as comp_date\r\n" + 
				"		from co_ntp_accomplishment a where status_id = 'A' and percent_accomplish > 0 \r\n" + 
				"		group by pbl_id, ntp_no, as_of_date order by pbl_id::int 		 \r\n" + 
				"		) a\r\n" + 
				"	where a.perc_comp > 0\r\n" + 
				"	) j on a.pbl_id = j.pbl_id \r\n" + 
				"\r\n" + 
				") A\r\n" + 
				"\r\n" + 
				"ORDER BY a.phase::int, a.block::int, a.lot::int" ;

		System.out.printf("SQL #1 Client List: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}		

			//totalCommClientList(modelMain, modelTotal);			
		}		

		else {		

			if (tabMain.getSelectedIndex()==0)
			{
				modelAgentAccount_total = new modelComm_client_list();
				modelAgentAccount_total.addRow(new Object[] { "Total", null, null, null, null, null });

				tblAgentAccount_total = new _JTableTotal(modelAgentAccount_total, tblAgentAccount);
				tblAgentAccount_total.setFont(dialog11Bold);
				scrollAgentAccount_total.setViewportView(tblAgentAccount_total);
				((_JTableTotal) tblAgentAccount_total).setTotalLabel(0);}
			else
			{
				modelClientTbl_total = new modelUnitStatusCOO_listOfUnits();
				modelClientTbl_total.addRow(new Object[] { "Total", null, null, null, null, null });

				tblClientList_total = new _JTableTotal(modelClientTbl_total, tblClientList);
				tblClientList_total.setFont(dialog11Bold);
				scrollClientTbl_total.setViewportView(tblClientList_total);
				((_JTableTotal) tblClientList_total).setTotalLabel(0);
			}


		}

		table.packAll();	

		adjustRowHeight_account(table);		

	}	

	//Enable/Disable all components inside JPanel	
	public void refresh_tablesMain(){//

		//reset table 1
		FncTables.clearTable(modelJV_account);
		FncTables.clearTable(modelJV_accounttotal);			
		rowHeaderJV_account = tblJV_account.getRowHeader22();
		scrollJV_account.setRowHeaderView(rowHeaderJV_account);	
		modelJV_accounttotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null});

		//reset table 2
		FncTables.clearTable(modelJV_SL);
		FncTables.clearTable(modelJV_SL_total);			
		rowHeaderJV_SL = tblJV_SL.getRowHeader22();
		scrollJV_SL.setRowHeaderView(rowHeaderJV_SL);	
		modelJV_SL_total.addRow(new Object[] { null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
				null, null, null, null, null, null, null, null });
		
		//reset CA Liquidation Table
		FncTables.clearTable(modelAgent_CA_liqui);
		FncTables.clearTable(modelAgent_CA_liqui_total);			
		rowHeaderAgent_CA_liqui = tblAgent_CA_liqui.getRowHeader22();
		scrollAgent_CA_liqui.setRowHeaderView(rowHeaderAgent_CA_liqui);	
		modelAgent_CA_liqui_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), null });

	}

	public void enableButtons( Boolean a, Boolean b ){
		btnCancel.setEnabled(a);
		btnEdit.setEnabled(b);	
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//

		if(e.getActionCommand().equals("Cancel")) { cancel(); }  //ok

		if (e.getActionCommand().equals("Edit")) {edit(); }  //ok

		
	}

	public void mouseClicked(MouseEvent evt) {		

		//		if ((evt.getClickCount() == 1  && table.equals("tblAgentAccount"))) {
		//			displayAgent_ClientComm();
		//		}	
		//		if ((evt.getClickCount() == 1  && table.equals("tblAgent_CommSched") )) {  //.isShowing()
		//			displayAgent_ClientCommQualifier();
		//		}	
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
		//TODO Auto-generated method stub

	}

	private void cancel(){//

		refresh_tablesMain();			
		{enableButtons(true,  false );} 
		lblClientName.setEnabled(true);	

	}

	private void edit(){//		

	}	

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (lookupAgentName.getText().equals("")){
					mniOpenAgentInfo.setEnabled(false);
				} else {mniOpenAgentInfo.setEnabled(true);}

			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (lookupAgentName.getText().equals("")){
					mniOpenAgentInfo.setEnabled(false);
				} else {mniOpenAgentInfo.setEnabled(true);}

			}
		}
	}
	
	class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				int column 	= tblAgent_CA_list.getSelectedColumn();
				if (column==1) {
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
				int column 	= tblAgent_CA_list.getSelectedColumn();
				if (column==1) {
					mniopenRPLF.setEnabled(true); 
					mniopenPV.setEnabled(false); 
					mniopenJV.setEnabled(false); 
					mniopenCPF.setEnabled(false); 
				}
			}
		}
	}


	//select, lookup and get statements	
	public String getAgent_list(){//used

		return 

				"select \r\n" + 
				"\r\n" + 
				"trim(a.agent_id)  as \"Agent ID\"  ,\r\n" + 
				"trim(b.entity_name) as \"Name\" ,\r\n" + 
				"trim(c.entity_name) as \"Override\"  ,\r\n" + 
				"upper(trim(d.position_desc))  as \"Position\" ,\r\n" + 
				"upper(trim(e.type_desc)) as \"Type\"  ,\r\n" + 
				"trim(f.dept_name)  as \"Dept/Group\" \r\n" + 
				"\r\n" + 
				"from mf_sellingagent_info a\r\n" + 
				"left join rf_entity b on a.agent_id = b.entity_id\r\n" + 
				"left join rf_entity c on a.override_id = c.entity_id\r\n" + 
				"left join mf_sales_position d on a.salespos_id  = d.position_code\r\n" + 
				"left join mf_sales_type e on a.salestype_id = e.type_code\r\n" + 
				"left join mf_department f on a.dept_id = f.dept_code\r\n" + 
				"\r\n" + 
				"where (case when '"+div_id+"' in ('06','07','08','09','10','11','12') then sales_div_id = '"+div_id+"' " +
						"	else (case when '"+div_id+"' in ('01','02','04') then true else sales_div_id = '"+div_id+"' end) end)" +
				"order by b.entity_name " ;			

	}

	public String getClient_list(){//used

		return 

				"select \r\n" + 
				"\r\n" + 
				"trim(a.agent_id)  as \"Agent ID\"  ,\r\n" + 
				"trim(b.entity_name) as \"Name\" ,\r\n" + 
				"trim(c.entity_name) as \"Override\"  ,\r\n" + 
				"upper(trim(d.position_desc))  as \"Position\" ,\r\n" + 
				"upper(trim(e.type_desc)) as \"Type\"  ,\r\n" + 
				"trim(f.dept_name)  as \"Dept/Group\" \r\n" + 
				"\r\n" + 
				"from rf_sold_units a\r\n" + 
				"left join rf_entity b on a.agent_id = b.entity_id\r\n" + 
				"left join rf_entity c on a.override_id = c.entity_id\r\n" + 
				"left join mf_sales_position d on a.salespos_id  = d.position_code\r\n" + 
				"left join mf_sales_type e on a.salestype_id = e.type_code\r\n" + 
				"left join mf_department f on a.dept_id = f.dept_code\r\n" + 
				"\r\n" + 
				"where a.status_id = 'A'" ;			

	}



	//table operations
	private void totalCommClientList(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.				

		BigDecimal comm_client_list		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){				

			try { comm_client_list 	= comm_client_list.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { comm_client_list 	= comm_client_list.add(new BigDecimal(0.00)); }	

		}		

		modelTotal.addRow(new Object[] { "Total", null, null, null, null, comm_client_list });

	}

	private void checkAllClientList(){//

		int rw = tblClientList.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";

			try { name	= tblClientList.getValueAt(x,1).toString().toUpperCase();}//make sure it's not modelAgentAccount, otherwise, row number will be fixed 
			catch (NullPointerException e) { name	= ""; }

			String acct_name	= txtClientName.getText().trim().toUpperCase();	
			Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			Boolean	end			= name.endsWith(acct_name);

			if (match==true||start==true||end==true) {
				tblClientList.setRowSelectionInterval(x, x); 
				tblClientList.changeSelection(x, 1, false, false);				
				break;			
			}
			else {
				tblClientList.setRowSelectionInterval(0, 0); 
				tblClientList.changeSelection(0, 1, false, false);					
			}

			x++;

		}		
	}

	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}



	//others
	private void generateClientSchedule(){

		int row = tblClientList.getSelectedRow();		
		pbl_id = tblClientList.getValueAt(row,8).toString();
		seq_no = tblClientList.getValueAt(row,9).toString();
		entity_name = tblClientList.getValueAt(row,1).toString();
		description = tblClientList.getValueAt(row,3).toString();
											
		tblAgentAccount.packAll();

		pnlClientDetails.setBorder(JTBorderFactory.createTitleBorder("Commission Details (Buyer - " + entity_name + " | Unit - " + description + ")"   ));
	}

	private void openAgentInfo(){

		SalesAgent.agent_id = agent_id;

		SalesAgent
 agent_info = new SalesAgent();
		Home_JSystem.addWindow(agent_info);

		//SalesAgent.setAgent_mainDtls();
		SalesAgent.searchAgentList(SalesAgent.modelAgentList, SalesAgent.rowHeaderAgentList, 
				SalesAgent.modelAgentList_total, agent_id );			

		SalesAgent.setAgent_mainDtls(agent_id);

		SalesAgent.enableFields(false);
		SalesAgent.enableMainButtons(true, true, false, true);
		SalesAgent.enableMainInfoFields(false);



	}
	
	public void openDRF(){

		RequestForPayment drf = new RequestForPayment();
		Home_JSystem.addWindow(drf);

		if(co_id.equals(""))
		{

		} 
		else 
		{			
			RequestForPayment.co_id 		= "02";	
			RequestForPayment.company		= "CENQHOMES DEVELOPMENT CORPORATION";	
			RequestForPayment.lookupCompany.setValue("02");
			RequestForPayment.tagCompany.setTag(company);
			RequestForPayment.company_logo = company_logo;

			RequestForPayment.lblDRF_no.setEnabled(true);	
			RequestForPayment.lookupDRF_no.setEnabled(true);	
			RequestForPayment.lookupDRF_no.setLookupSQL(RequestForPayment.getDRF_no());

			RequestForPayment.btnAddNew.setEnabled(true);
			RequestForPayment.btnCancel.setEnabled(true);

			int column 	= tblAgent_CA_list.getSelectedColumn();
			int row 	= tblAgent_CA_list.getSelectedRow();
			String rplf = modelAgent_CA_list.getValueAt(row,column).toString().trim();

			if (rplf.equals("")) {}
			else {			
				RequestForPayment.drf_no  = rplf;
				RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);	

				RequestForPayment.setDRF_header(RequestForPayment.drf_no);
				RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF, RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no );	
				RequestForPayment.btnAddNew.setEnabled(false);
				RequestForPayment.btnRefresh.setEnabled(true);
				RequestForPayment.btnPreview.setEnabled(true);
				if(RequestForPayment.isPVcreated()==true) {RequestForPayment.btnEdit.setEnabled(false);} 
				else {RequestForPayment.btnEdit.setEnabled(true);}
				RequestForPayment.mnidelete.setEnabled(false);
				RequestForPayment.mniaddrow.setEnabled(false);
				RequestForPayment.mnisetupPV.setEnabled(true);
			}
		}		
	}	

	


}