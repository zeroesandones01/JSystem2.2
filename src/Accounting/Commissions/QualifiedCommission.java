package Accounting.Commissions;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.TblPtg;

import tablemodel.modelComm_agentCA_process;
import tablemodel.modelComm_agentPromo_perAcct;
import tablemodel.modelComm_qualify;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class QualifiedCommission extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Qualify Commission";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlClient_CommSched;
	private JPanel pnlClient_Promo;
	private JPanel pnlSubTable;
	private JPanel pnlClientDetails;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;

	private JLabel lblCompany;

	private static modelComm_qualify modelClient_CommSched;
	private static modelComm_qualify modelClient_CommSched_total;
	private static modelComm_qualify modelClient_Promo;
	private static modelComm_qualify modelClient_Promo_total;
	private static modelComm_agentPromo_perAcct modelAgent_Promo_perAcct_total;

	private _JTabbedPane tabClientDetails;	

	private static JButton btnShowQualified;
	private static JButton btnQualify;
	private static JButton btnShowQualifiedPromo;
	private static JButton btnOpenCommProcessing;	

	private static _JLookup lookupCompany;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JScrollPaneMain scrollClient_CommSched;
	private _JScrollPaneMain scrollClient_Promo;

	private static _JScrollPaneTotal scrollClient_CommSchedtotal;
	private static _JScrollPaneTotal scrollClient_Promo_total;	
	private static _JScrollPaneTotal scrollAgent_Promo_perAcct_total;	

	private static _JTableMain tblClient_CommSched;	
	private static _JTableMain tblClient_Promo;
	private static _JTableMain tblAgent_Promo_perAcct;

	private static JList rowHeaderClient_CommSched;
	private static JList rowHeaderClient_Promo;

	private static _JTableTotal tblClient_CommSched_total;
	private static _JTableTotal tblClient_Promo_total;
	private static _JTableTotal tblAgent_Promo_perAcct_total;

	private _JTagLabel tagCompany;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);

	static String co_id 	= "";		
	String company 	= "";
	String company_logo;
	String agent_id = "";
	String pbl_id 	= "";
	String seq_no 	= "";
	String entity_id = "";
	String comm_no 	= "";
	
	private static _JTagLabel lblRem; 

	public QualifiedCommission() {
		super(title, true, true, true, true);
		initGUI();
	}

	public QualifiedCommission(String title) {
		super(title);
		
	}

	public QualifiedCommission(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(917, 604));
		this.setBounds(0, 0, 917, 604);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));
		{
			pnlComp = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlComp, BorderLayout.NORTH);
			pnlComp.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				{
					pnlComp_a = new JPanel(new BorderLayout(5, 5));
					pnlComp.add(pnlComp_a, BorderLayout.CENTER);	
					pnlComp_a.setPreferredSize(new java.awt.Dimension(610, 30));
					{
						{
							pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
							pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
							pnlComp_a1.setPreferredSize(new java.awt.Dimension(150, 30));
							pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
							{
								{
									lblCompany = new JLabel("COMPANY", JLabel.TRAILING);
									pnlComp_a1.add(lblCompany);
									lblCompany.setHorizontalAlignment(JLabel.LEADING);
								}
								{
									lookupCompany = new _JLookup(null, "Company",0,2);
									pnlComp_a1.add(lookupCompany);
									lookupCompany.setLookupSQL(SQL_COMPANY());
									lookupCompany.setReturnColumn(0);
									lookupCompany.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){

												co_id 		= (String) data[0];	
												tagCompany.setTag((String) data[1]);
												company 	= (String) data[1];

												refresh_tablesMain();

											}
										}
									});
								}
							}
						}
						{
							tagCompany = new _JTagLabel("[ ]");
							pnlComp_a.add(tagCompany, BorderLayout.CENTER);
							tagCompany.setPreferredSize(new Dimension(30, 0));
						}
						{
							lblRem = new _JTagLabel("---"); 
							pnlComp_a.add(lblRem, BorderLayout.LINE_END);
							lblRem.setPreferredSize(new Dimension(225, 0));
							lblRem.setHorizontalAlignment(JLabel.TRAILING);
						}	
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

			{						

				pnlClientDetails = new JPanel();
				pnlSubTable.add(pnlClientDetails, BorderLayout.CENTER);
				pnlClientDetails.setLayout(new BorderLayout(5, 5));
				pnlClientDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlClientDetails.setBorder(JTBorderFactory.createTitleBorder("Commission for Processing Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));

				tabClientDetails = new _JTabbedPane();
				pnlClientDetails.add(tabClientDetails, BorderLayout.CENTER);	

				//start of Commission Schedule (by client)
				{
					pnlClient_CommSched = new JPanel(new BorderLayout());
					tabClientDetails.addTab("Commission for Processing", null, pnlClient_CommSched, null);
					pnlClient_CommSched.setPreferredSize(new java.awt.Dimension(1183, 365));	
					pnlClient_CommSched.setBorder(new EmptyBorder(5, 5, 5, 5));
					{
						scrollClient_CommSched = new _JScrollPaneMain();
						pnlClient_CommSched.add(scrollClient_CommSched, BorderLayout.CENTER);
						{
							modelClient_CommSched = new modelComm_qualify();

							tblClient_CommSched = new _JTableMain(modelClient_CommSched);
							scrollClient_CommSched.setViewportView(tblClient_CommSched);
							tblClient_CommSched.addMouseListener(this);								
							tblClient_CommSched.setSortable(false);
							tblClient_CommSched.getColumnModel().getColumn(7).setPreferredWidth(100);
							tblClient_CommSched.getColumnModel().getColumn(8).setPreferredWidth(80);
							tblClient_CommSched.getColumnModel().getColumn(9).setPreferredWidth(80);
							tblClient_CommSched.getColumnModel().getColumn(10).setPreferredWidth(80);
							tblClient_CommSched.getColumnModel().getColumn(11).setPreferredWidth(80);
							tblClient_CommSched.getColumnModel().getColumn(12).setPreferredWidth(80);
							tblClient_CommSched.getColumnModel().getColumn(0).setMinWidth(0);
							tblClient_CommSched.getColumnModel().getColumn(0).setMaxWidth(0);
							tblClient_CommSched.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}	

							}); 
							tblClient_CommSched.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblClient_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblClient_CommSched.setCellSelectionEnabled(true);}

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
							modelClient_CommSched_total = new modelComm_qualify();
							modelClient_CommSched_total.addRow(new Object[] { null,"Total", null, null, null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
									new BigDecimal(0.00), new BigDecimal(0.00), null, null });

							tblClient_CommSched_total = new _JTableTotal(modelClient_CommSched_total, tblClient_CommSched);
							tblClient_CommSched_total.setFont(dialog11Bold);
							scrollClient_CommSchedtotal.setViewportView(tblClient_CommSched_total);
							((_JTableTotal) tblClient_CommSched_total).setTotalLabel(1);
						}
					}
				} 
				//end of Commission Schedule (by client)

				//start of Promo (by client)
				{
					pnlClient_Promo = new JPanel(new BorderLayout());
					tabClientDetails.addTab("       Promo for Processing       ", null, pnlClient_Promo, null);
					pnlClient_Promo.setPreferredSize(new java.awt.Dimension(1183, 365));	


					{
						scrollClient_Promo = new _JScrollPaneMain();
						pnlClient_Promo.add(scrollClient_Promo, BorderLayout.CENTER);
						{
							modelClient_Promo = new modelComm_qualify();

							tblClient_Promo = new _JTableMain(modelClient_Promo);
							scrollClient_Promo.setViewportView(tblClient_Promo);
							tblClient_Promo.addMouseListener(this);	
							tblClient_Promo.getColumnModel().getColumn(3).setPreferredWidth(60);
							tblClient_Promo.getColumnModel().getColumn(4).setPreferredWidth(60);
							tblClient_Promo.getColumnModel().getColumn(5).setPreferredWidth(60);
							tblClient_Promo.getColumnModel().getColumn(6).setPreferredWidth(60);
							tblClient_Promo.getColumnModel().getColumn(0).setMinWidth(0);
							tblClient_Promo.getColumnModel().getColumn(0).setMaxWidth(0);
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
							modelClient_Promo_total = new modelComm_qualify();
							modelClient_Promo_total.addRow(new Object[] { null,"Total", null, null, null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
									new BigDecimal(0.00), new BigDecimal(0.00), null, null });

							tblClient_Promo_total = new _JTableTotal(modelClient_Promo_total, tblClient_Promo);
							tblClient_Promo_total.setFont(dialog11Bold);
							scrollClient_Promo_total.setViewportView(tblClient_Promo_total);
							((_JTableTotal) tblClient_Promo_total).setTotalLabel(1);
						}
					}
				}
				//end of Promo (by client)
			}

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
					btnQualify = new JButton("Qualify");
					pnlSouthCenterb.add(btnQualify);
					btnQualify.setActionCommand("Qualify");
					btnQualify.addActionListener(this);
					btnQualify.setEnabled(true);
					btnQualify.setMnemonic(KeyEvent.VK_Q);
				}
				{
					btnShowQualified = new JButton("Show Qualified Comm.");
					pnlSouthCenterb.add(btnShowQualified);
					btnShowQualified.setActionCommand("ShowQualified");
					btnShowQualified.addActionListener(this);
					btnShowQualified.setEnabled(true);
					btnShowQualified.setMnemonic(KeyEvent.VK_S);
				}
				{
					btnShowQualifiedPromo = new JButton("Show Qualified Promo");
					pnlSouthCenterb.add(btnShowQualifiedPromo);
					btnShowQualifiedPromo.setActionCommand("ShowQualifiedPromo");
					btnShowQualifiedPromo.addActionListener(this);
					btnShowQualifiedPromo.setEnabled(true);
					btnShowQualifiedPromo.setMnemonic(KeyEvent.VK_P);
				}
				{
					btnOpenCommProcessing = new JButton("Open Commission Processing");
					pnlSouthCenterb.add(btnOpenCommProcessing);
					btnOpenCommProcessing.setActionCommand("OpenCommProcessing");
					btnOpenCommProcessing.addActionListener(this);
					btnOpenCommProcessing.setEnabled(true);
					btnOpenCommProcessing.setMnemonic(KeyEvent.VK_O);
				}
				/*{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenterb.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.addActionListener(this);
					btnRefresh.setEnabled(true);
				}*/
			}
		}

		initialize_comp();
	}


	//display tables	
	public void displayClientPromo(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String agent_id ) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"--DISPLAY AGENT PROMO\r\n" + 
			"\r\n" + 
			"select \r\n" + 
			"trim(b.entity_name),\r\n" + 
			"trim(c.promo_desc),\r\n" + 
			"case when a.frequency = 'M' then 'MONTHLY' else 'ONCE' end as frequency,\r\n" + 
			"a.comm_amt,\r\n" + 
			"a.comm_amt*.1 as wtax,\r\n" + 
			"a.adv_amount,\r\n" + 
			"a.comm_amt*.9 - a.adv_amount as net_amt,\r\n" + 
			"( case when a.status = 'A' then 'Not Yet Qualified' else\r\n" + 
			"	case when a.status = 'R' then 'Promo Released' end end ) as status,\r\n" + 
			"a.cdf_no,\r\n" + 
			"d.rplf_no,\r\n" + 
			"e.cv_no,\r\n" + 
			"a.remarks\r\n" + 
			"\r\n" + 
			"from (select * from cm_schedule_dl where co_id = '"+co_id+"') a\r\n" + 
			"left join rf_entity b on a.account_code = b.entity_id\r\n" + 
			"left join cm_promo_bonus c on a.promo_code = c.promo_code \r\n" + 
			"left join cm_cdf_hd d on a.cdf_no = d.cdf_no \r\n" + 
			"left join rf_pv_header e on d.rplf_no = e.pv_no\r\n" + 
			"\r\n" + 
			"where a.tran_type = 'BB' and a.agent_code = '"+agent_id+"' and a.status != 'I' " ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalPromo(modelMain, modelTotal);			
		}		


		else {
			modelAgent_Promo_perAcct_total = new modelComm_agentPromo_perAcct();
			modelAgent_Promo_perAcct_total.addRow(new Object[] { "Total", null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
					new BigDecimal(0.00), null, null, null, null, null });

			tblAgent_Promo_perAcct_total = new _JTableTotal(modelAgent_Promo_perAcct_total, tblAgent_Promo_perAcct);
			tblAgent_Promo_perAcct_total.setFont(dialog11Bold);
			scrollAgent_Promo_perAcct_total.setViewportView(tblAgent_Promo_perAcct_total);
			((_JTableTotal) tblAgent_Promo_perAcct_total).setTotalLabel(0);}

		tblAgent_Promo_perAcct.packAll();
		tblAgent_Promo_perAcct.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblAgent_Promo_perAcct.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblAgent_Promo_perAcct.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblAgent_Promo_perAcct.getColumnModel().getColumn(6).setPreferredWidth(60);

		adjustRowHeight_account(tblAgent_Promo_perAcct);
	}	

	public static void displayClient_All_agentsCommission_activeAccounts(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//
		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String sql = "select * from view_comm_qualified_commission_active_accounts where co_id = '"+lookupCompany.getValue()+"' and tran_type = 'Commission'; ";

		System.out.println("All Agents Commission - Active Accounts: "+sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalAllComm(modelMain, modelTotal);			
		}		


		else {
			modelClient_CommSched_total = new modelComm_qualify();
			modelClient_CommSched_total.addRow(new Object[] { null,"Total", null, null, null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
					new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblClient_CommSched_total = new _JTableTotal(modelClient_CommSched_total, tblClient_CommSched);
			tblClient_CommSched_total.setFont(dialog11Bold);
			scrollClient_CommSchedtotal.setViewportView(tblClient_CommSched_total);
			((_JTableTotal) tblClient_CommSched_total).setTotalLabel(1);}

		tblClient_CommSched.packAll();	
		tblClient_CommSched.getColumnModel().getColumn(7).setPreferredWidth(100);
		tblClient_CommSched.getColumnModel().getColumn(8).setPreferredWidth(80);
		tblClient_CommSched.getColumnModel().getColumn(9).setPreferredWidth(80);
		tblClient_CommSched.getColumnModel().getColumn(10).setPreferredWidth(80);
		tblClient_CommSched.getColumnModel().getColumn(11).setPreferredWidth(80);
		tblClient_CommSched.getColumnModel().getColumn(12).setPreferredWidth(80);

		adjustRowHeight_account(tblClient_CommSched);
	}

	public static void displayClient_All_agentsPromo_activeAccounts(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "select * from view_comm_qualified_commission_active_accounts where co_id = '"+lookupCompany.getValue()+"' and tran_type = 'Promo'; "; 
		System.out.println("All Agents Promo - Active Accounts: "+sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalAllComm(modelMain, modelTotal);			
		}		


		else {
			modelClient_Promo_total = new modelComm_qualify();
			modelClient_Promo_total.addRow(new Object[] { null,"Total", null, null, null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
					new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblClient_Promo_total = new _JTableTotal(modelClient_Promo_total, tblClient_Promo);
			tblClient_Promo_total.setFont(dialog11Bold);
			scrollClient_Promo_total.setViewportView(tblClient_Promo_total);
			((_JTableTotal) tblClient_Promo_total).setTotalLabel(1);}

		tblClient_Promo.packAll();	
		tblClient_Promo.getColumnModel().getColumn(7).setPreferredWidth(100);
		tblClient_Promo.getColumnModel().getColumn(8).setPreferredWidth(80);
		tblClient_Promo.getColumnModel().getColumn(9).setPreferredWidth(80);
		tblClient_Promo.getColumnModel().getColumn(10).setPreferredWidth(80);
		tblClient_Promo.getColumnModel().getColumn(11).setPreferredWidth(80);
		tblClient_Promo.getColumnModel().getColumn(12).setPreferredWidth(80);

		adjustRowHeight_account(tblClient_Promo);
	}


	//Enable/Disable all components inside JPanel	
	public void refresh_tablesMain(){//

		//reset table 1
		FncTables.clearTable(modelClient_CommSched);
		FncTables.clearTable(modelClient_CommSched_total);			
		rowHeaderClient_CommSched = tblClient_CommSched.getRowHeader22();
		scrollClient_CommSched.setRowHeaderView(rowHeaderClient_CommSched);	
		modelClient_CommSched_total.addRow(new Object[] {  null,"Total", null, null, null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
				new BigDecimal(0.00), new BigDecimal(0.00), null, null});

		//reset table 2
		FncTables.clearTable(modelClient_Promo);
		FncTables.clearTable(modelClient_Promo_total);			
		rowHeaderClient_Promo = tblClient_Promo.getRowHeader22();
		scrollClient_Promo.setRowHeaderView(rowHeaderClient_Promo);	
		modelClient_Promo_total.addRow(new Object[] { null,"Total", null, null, null,null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
				new BigDecimal(0.00), new BigDecimal(0.00), null, null });

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();		

		lookupCompany.setValue(co_id);
	}	

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) { 

		if(e.getActionCommand().equals("ShowQualified")) {
			displayClient_All_agentsCommission_activeAccounts(modelClient_CommSched, rowHeaderClient_CommSched, modelClient_CommSched_total); 
			tabClientDetails.setSelectedIndex(0); 
		} 

		if (e.getActionCommand().equals("ShowQualifiedPromo")) {
			displayClient_All_agentsPromo_activeAccounts(modelClient_Promo, rowHeaderClient_Promo, modelClient_Promo_total ); 
			tabClientDetails.setSelectedIndex(1);
		} 

		if (e.getActionCommand().equals("Qualify")) { 
			qualifyAll(); 
		} 

		if (e.getActionCommand().equals("OpenCommProcessing")) { 			
			if(FncGlobal.homeMDI.isNotExisting("ProcessCommission")){
				openCommProcessing();
			}			
		} 
	}

	public void mouseClicked(MouseEvent evt) {		

		//		if ((evt.getClickCount() == 1  && table.equals("tblAgentAccount"))) {
		//			displayAgent_ClientComm();
		//		}	
		//		if ((evt.getClickCount() == 1  && table.equals("tblAgent_CommSched") )) {  //.isShowing()
		//			displayAgent_ClientCommQualifier();
		//		}	
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}


	//select, lookup and get statements	
	/*private String getAgent_list(){//used

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
		"where a.status_id = 'I'" ;			

	}
*/
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

	public static Integer getNumberofUnits(){

		Integer x = 0;
		/*
		String SQL = "select sum(a.count)::int \n" +
				"from \n" +
				"(select distinct on (pbl_id::int, seq_no) 1 as count, pbl_id::int, seq_no \n" +
				"from cm_schedule_dl \n" + 
				"where status = 'A' and pbl_id != '' and tran_type = 'AA' and co_id = '"+co_id+"' \n" +
				"order by pbl_id::int, seq_no) a \n" +
				"group by a.count" ;
		*/
		
		/*
		String SQL = "select sum(a.count)::int \n" + 
				"from (select distinct on (a.pbl_id::int, a.seq_no) 1 as count, a.pbl_id::int, a.seq_no \n" + 
				"from cm_schedule_dl a\n" + 
				"where a.status = 'A' and a.pbl_id != '' and a.tran_type = 'AA' and a.co_id = '02' \n" + 
				"and not exists ( select x.* from (select * from rf_buyer_status where byrstatus_id = '02' and status_id = 'A') x \n" + 
				"left join (select * from rf_buyer_status where byrstatus_id = '08' and status_id = 'A') y on x.entity_id = y.entity_id and x.proj_id = y.proj_id and x.pbl_id = y.pbl_id and x.seq_no::int = y.seq_no::int \n" + 
				"where (x.tran_date > y.tran_date or y.tran_date is null) \n" + 
				"and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int ) \n" +
				"order by pbl_id::int, seq_no \n" +
				") a \n" + 
				"group by a.count";
		*/
				
		System.out.println("select pbl_id, seq_no, account_code, projcode from view_comm_list_of_units where co_id = '"+lookupCompany.getValue()+"' group by pbl_id, seq_no, account_code, projcode");
		pgSelect dbExec = new pgSelect();
		dbExec.select("select pbl_id, seq_no, account_code, projcode from view_comm_list_of_units where co_id = '"+lookupCompany.getValue()+"' group by pbl_id, seq_no, account_code, projcode");

		if (dbExec.isNotNull()) {
			if ((Integer) dbExec.getResult()[0][0]==null || dbExec.getResult()[0][0].equals("null")) {
				x = 0;
			} else {
				x = dbExec.getRowCount(); 
			}		
		} else {
			x = 0;
		}

		return x;
	}

	public static Integer getNumberofActiveComm(String pbl_id, String seq_no){
		Integer x = 1;

		/*
		String SQL = "select sum(a.count)::int from ( select distinct on (pbl_id::int, seq_no, comm_type) 1 as count, pbl_id::int, seq_no, comm_type " +
			"from cm_schedule_dl \r\n" + 
			"where status = 'A' and pbl_id != '' and tran_type = 'AA' and pbl_id = '"+pbl_id+"' \n" +
			"and seq_no = "+seq_no+" and co_id = '"+co_id+"' \n" +
			"order by pbl_id::int, seq_no, comm_type\r\n" + 
			") a group by a.count " ;
		*/
		
		String SQL = "select * from view_comm_list_of_units where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and co_id = '"+co_id+"'"; 
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = db.getRowCount(); 
		}else{
			x = 1;
		}

		return x;
	}
	
	public static Integer getNumberofActiveComm_v2(String entity_id, String proj_id, String pbl_id, String seq_no){
		Integer x = 1;

		/*
		String SQL = "select sum(a.count)::int from ( select distinct on (pbl_id::int, seq_no, comm_type) 1 as count, pbl_id::int, seq_no, comm_type " +
			"from cm_schedule_dl \r\n" + 
			"where status = 'A' and pbl_id != '' and tran_type = 'AA' and pbl_id = '"+pbl_id+"' \n" +
			"and seq_no = "+seq_no+" and co_id = '"+co_id+"' \n" +
			"order by pbl_id::int, seq_no, comm_type\r\n" + 
			") a group by a.count " ;
		*/
		
		String SQL = "select * from view_comm_list_of_units where account_code = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and co_id = '"+co_id+"'"; 
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = db.getRowCount(); 
		}else{
			x = 1;
		}

		return x;
	}

	public static Object[][] getListofUnits() {//used
		
		/*
		String strSQL = "select distinct on (pbl_id::int, seq_no) pbl_id::int, seq_no \n" +
				"from cm_schedule_dl \n" + 
				"where status = 'A' and pbl_id != '' and tran_type = 'AA' \n" +
				"and co_id = '"+co_id+"' \n" +
				"order by pbl_id::int, seq_no";		
		*/
		
		/*
		String strSQL = "select distinct on (a.pbl_id::int, a.seq_no) a.pbl_id::int, a.seq_no \n" +
				"from cm_schedule_dl a \n" +
				"where a.status = 'A' and a.pbl_id != '' \n" + 
				"and a.tran_type = 'AA' and a.co_id = '02' \n" +
				"and not exists \n" +
				"( \n" +
				"select x.* \n" +
				"from (select * from rf_buyer_status where byrstatus_id = '02' and status_id = 'A') x \n" +
				"left join (select * from rf_buyer_status where byrstatus_id = '08' and status_id = 'A') y on x.entity_id = y.entity_id and x.proj_id = y.proj_id and x.pbl_id = y.pbl_id and x.seq_no::int = y.seq_no::int \n" +
				"where (x.tran_date > y.tran_date or y.tran_date is null) \n" +
				"and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int \n" +
				") \n" +
				"order by a.pbl_id::int, a.seq_no";
		*/ 

		pgSelect db = new pgSelect();
		db.select("select pbl_id, seq_no, account_code, projcode from view_comm_list_of_units where co_id = '"+lookupCompany.getValue()+"' group by pbl_id, seq_no, account_code, projcode");		

		if(db.isNotNull()){			
			return db.getResult();
		}else{
			return null;
		}
	}	

	public static Object[][] getActiveCommDetails(String pbl_id, String seq_no) {
		/*
		String strSQL = "select distinct on (pbl_id::int, seq_no, comm_type) pbl_id::int, seq_no, comm_type " +
			"from cm_schedule_dl \r\n" + 
			"where status = 'A' " +
			"and pbl_id != '' " +
			"and tran_type = 'AA' " +
			"and pbl_id = '"+pbl_id+"' \n" +
			"and seq_no = "+seq_no+" " +
			"and co_id = '"+co_id+"' " +
			//"and agent_code in (select agent_id from mf_sellingagent_info where status_id != 'I') \n" + (as to M'Rhea 03/10/2017 - accounts of an inactive broker would continue for comm processing  as long as they were TR when they were active
			"order by pbl_id::int, seq_no, comm_type"  ;		
		*/
		
		String SQL = "select pbl_id::int, seq_no, comm_type from view_comm_list_of_units where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and co_id = '"+co_id+"'"; 
		
		pgSelect db = new pgSelect();
		db.select(SQL);		

		if(db.isNotNull()){			
			return db.getResult();
		}else{
			return null;
		}
	}
	
	public static Object[][] getActiveCommDetails_v2(String entity_id, String proj_id, String pbl_id, String seq_no) {
		/*
		String strSQL = "select distinct on (pbl_id::int, seq_no, comm_type) pbl_id::int, seq_no, comm_type " +
			"from cm_schedule_dl \r\n" + 
			"where status = 'A' " +
			"and pbl_id != '' " +
			"and tran_type = 'AA' " +
			"and pbl_id = '"+pbl_id+"' \n" +
			"and seq_no = "+seq_no+" " +
			"and co_id = '"+co_id+"' " +
			//"and agent_code in (select agent_id from mf_sellingagent_info where status_id != 'I') \n" + (as to M'Rhea 03/10/2017 - accounts of an inactive broker would continue for comm processing  as long as they were TR when they were active
			"order by pbl_id::int, seq_no, comm_type"  ;		
		*/
		
		String SQL = "select pbl_id::int, seq_no, comm_type, account_code, projcode from view_comm_list_of_units where account_code = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and co_id = '"+co_id+"'"; 
		
		pgSelect db = new pgSelect();
		db.select(SQL);		

		if(db.isNotNull()){			
			return db.getResult();
		}else{
			return null;
		}
	}

	public static Boolean unit_canceled(String pbl_id, String seq_no){

		Boolean unit_canceled = false;

		String SQL =  "select unit_canceled('"+pbl_id+"', "+seq_no+") " ;

		//System.out.printf("SQL unit_canceled : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if((Boolean) db.getResult()[0][0]==true){unit_canceled = true;}
		else{unit_canceled = false;}

		return unit_canceled;		
	}


	//table operations	
	private static void totalAllComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal gross_comm 	= new BigDecimal(0.00);	
		BigDecimal vat_amt		= new BigDecimal(0.00);	
		BigDecimal wtax_amt		= new BigDecimal(0.00);	
		BigDecimal for_liq_amt	= new BigDecimal(0.00);	
		BigDecimal net_amt		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { gross_comm 	= gross_comm.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { gross_comm 	= gross_comm.add(new BigDecimal(0.00)); }

			try { vat_amt 	= vat_amt.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { vat_amt 	= vat_amt.add(new BigDecimal(0.00)); }

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,10)));} 
			catch (NullPointerException e) { wtax_amt = wtax_amt.add(new BigDecimal(0.00)); }

			try { for_liq_amt 	= for_liq_amt.add(((BigDecimal) modelMain.getValueAt(x,11)));} 
			catch (NullPointerException e) { for_liq_amt 	= for_liq_amt.add(new BigDecimal(0.00)); }

			try { net_amt 	= net_amt.add(((BigDecimal) modelMain.getValueAt(x,12)));} 
			catch (NullPointerException e) { net_amt 	= net_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, null, null, gross_comm, vat_amt, wtax_amt, for_liq_amt, net_amt, null, null });		
	}

	private void totalPromo(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal gross_amt	= new BigDecimal(0.00);	
		BigDecimal wtax_amt		= new BigDecimal(0.00);	
		BigDecimal vat_amt		= new BigDecimal(0.00);	
		BigDecimal ca_liq		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){	

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,3)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { vat_amt 	= vat_amt.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { vat_amt 	= vat_amt.add(new BigDecimal(0.00)); }

			try { ca_liq 	= ca_liq.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { ca_liq 	= ca_liq.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null, gross_amt, wtax_amt, vat_amt, ca_liq, null, null });			

	}

	private static void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	public void openCommProcessing(){

		ProcessCommission comm_proc = new ProcessCommission();
		Home_JSystem.addWindow(comm_proc);

		ProcessCommission.co_id = co_id;
		ProcessCommission.company		= company;	
		ProcessCommission.lookupCompany.setValue(co_id);
		ProcessCommission.tagCompany.setTag(company);
		ProcessCommission.company_logo = company_logo;

		ProcessCommission.displayClient_All_agentsCommission(ProcessCommission.modelCommListTbl, 
				ProcessCommission.rowHeaderCommList, ProcessCommission.modelCommListTotal ); 
		FncTables.clearTable(ProcessCommission.modelCashAdv);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		ProcessCommission.rowHeaderCashAdv.setModel(listModel);//Setting of DefaultListModel into rowHeader.	
		ProcessCommission.modelCashAdv_total = new modelComm_agentCA_process();
		ProcessCommission.modelCashAdv_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
				null, null });

		ProcessCommission.tblCashAdv_total = new _JTableTotal(ProcessCommission.modelCashAdv_total, ProcessCommission.tblCashAdv);
		ProcessCommission.tblCashAdv_total.setFont(dialog11Bold);
		ProcessCommission.scrollCashAdv_total.setViewportView(ProcessCommission.tblCashAdv_total);
		((_JTableTotal) ProcessCommission.tblCashAdv_total).setTotalLabel(0);

		ProcessCommission.btnProcess.setEnabled(true);
		ProcessCommission.btnSetupCA.setEnabled(false);
		ProcessCommission.btnRefresh.setEnabled(true);
		ProcessCommission.btnPreview.setEnabled(true);

		ProcessCommission.lblAgentName.setEnabled(true);	
		ProcessCommission.txtAgentName.setEnabled(true);	
		ProcessCommission.chkAcctsWithCA.setEnabled(true);	
		ProcessCommission.chkCancAccounts.setEnabled(true);	
		ProcessCommission.btnQualify.setEnabled(true);	

		ProcessCommission.chkAcctsWithCA.setSelected(false);
		ProcessCommission.chkCancAccounts.setSelected(false);

		ProcessCommission.modelCashAdv.setEditable(false);
	}
	
	private static void ButtonLock(Boolean blnRev) {
		btnShowQualified.setEnabled(blnRev);
		btnQualify.setEnabled(blnRev);
		btnShowQualifiedPromo.setEnabled(blnRev);
		btnOpenCommProcessing.setEnabled(blnRev);
		lookupCompany.setEnabled(blnRev);
	}
	
    public static void qualifyAll() {
    	
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws Exception {
				
				if (JOptionPane.showConfirmDialog(null, "This process will take a few minutes. Proceed?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					ButtonLock(false); 
					
					int no_of_units = getNumberofUnits();
					Object[][] units_dtl = getListofUnits();			
					int w = 0;
					
					System.out.println("");
					System.out.println("no_of_units: "+no_of_units);
					System.out.println("");
					
					if (no_of_units>0) { 
						while (w<no_of_units) {

							String PBLid 	= units_dtl[w][0].toString();	
							String SeqNo 	= units_dtl[w][1].toString();
							String entity_id = units_dtl[w][2].toString();
							String proj_id = units_dtl[w][3].toString();
							
							System.out.printf("Display value of entity id: %s%n", entity_id);
							System.out.printf("Display value of proj_id: %s%n", proj_id);
							System.out.printf("\n" + "\n" +  "\n" + "PBLid : " + PBLid + "\n");
							System.out.printf("SeqNo : " + SeqNo + "\n");		

							//int active_comm = getNumberofActiveComm(PBLid,SeqNo);
							int active_comm = getNumberofActiveComm_v2(entity_id, proj_id, PBLid,SeqNo);
							//Object[][] comm_dtl = getActiveCommDetails(PBLid,SeqNo);
							Object[][] comm_dtl = getActiveCommDetails_v2(entity_id, proj_id, PBLid,SeqNo);
							int x = 0; 
							System.out.printf("Active Comm: %s%n", active_comm);
							
							while (x<active_comm){		
								/*
								if (unit_canceled(PBLid, SeqNo)==true) {
									System.out.printf("Unit is already cancelled " + "\n"); 
									break;
								} else {
									String CommType = comm_dtl[x][2].toString().trim();	
			
									System.out.printf("\n" + "Commission No : " + x + "------------------"+ "\n");
									System.out.printf("CommType : " + CommType + "\n");

									if (UserInfo.EmployeeCode.equals("901074") || UserInfo.EmployeeCode.equals("900748")) {
										Qualify_Commission.strUnitID = PBLid;
										Qualify_Commission.strSeqNo = SeqNo;
										Qualify_Commission.strCommType = CommType;
										Qualify_Commission.strCoID = co_id;
										Qualify_Commission.CommThread();
									} else {
										
									}
									
									Qualify_Commission.qualifyCommAll(PBLid, SeqNo, CommType, co_id);
								}
								*/
								
								String CommType = comm_dtl[x][2].toString().trim();	
								System.out.printf("\n" + "Commission No : " + x + "------------------"+ "\n");
								System.out.printf("CommType : " + CommType + "\n");

								//Qualify_Commission.qualifyCommAll(PBLid, SeqNo, CommType, co_id);
								Qualify_Commission.qualifyCommAll_v2(entity_id, proj_id, PBLid, SeqNo, CommType, co_id);
								
								x++;
							}
							
							Thread.sleep(100);
							w++;
							lblRem.setText(w+"/"+no_of_units+" Accounts Processed");
						}
						
						ButtonLock(true);
						lblRem.setText("---"); 
						
						displayClient_All_agentsCommission_activeAccounts(modelClient_CommSched, rowHeaderClient_CommSched, modelClient_CommSched_total ); 
						displayClient_All_agentsPromo_activeAccounts(modelClient_Promo, rowHeaderClient_Promo, modelClient_Promo_total );	
						
						JOptionPane.showMessageDialog(null, "Finish Qualifying Commission."+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);	
					} else {
						JOptionPane.showMessageDialog(null, "No accounts to qualify."+ "\n", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
					
				} else {
					
				}
				
				return null;
			}
    	}; 
    	
    	sw.execute(); 
    }
}
