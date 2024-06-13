package Utilities;

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
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelComm_client_comm_override;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncAcounting;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class Comm_Reprocessing extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Commission Reprocessing";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlCommDetails;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlClient_center;
	private JPanel pnlClient_center_a;
	private JPanel pnlClient_center_b;
	private JPanel pnlClient_center_b1;
	private JPanel pnlClient_center_b2;
	private JPanel pnlRemarks;
	private JPanel pnlRemarks_a;
	private JPanel pnlRemarks_b;
	private JPanel pnlRemarks_b1;

	private modelComm_client_comm_override modelComm_CommSched;
	private modelComm_client_comm_override modelComm_CommSched_total;

	private _JLookup lookupCompany;
	private _JLookup lookupBroker;

	private JButton btnCancel;
	private JButton btnReprocess;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblCompany;
	private JLabel lblBroker;
	private JLabel lblRemarks;

	private _JTagLabel tagCompany;
	private _JTagLabel tagBrokerName;

	private _JScrollPaneMain scrollComm_CommSched;
	private _JScrollPaneTotal scrollComm_CommSchedtotal;
	private _JTableMain tblComm_CommSched;
	private JList rowHeaderComm_CommSched;
	private _JTableTotal tblComm_CommSched_total;
	private JXTextField txtRemarks;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	static String co_id 	= "";		
	String company 	= "";
	String pbl_id 	= "";
	String seq_no 	= "";
	String status	= "";
	String company_logo;	
	String agent_id 	= "";
	String agent_name 	= "";
	private JButton btnRecheck;

	public Comm_Reprocessing() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Comm_Reprocessing(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Comm_Reprocessing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(940, 551));
		this.setBounds(0, 0, 940, 551);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{ //start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(928, 73));	
			pnlComp.setBorder(lineBorder);

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(926, 36));	
				pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));			

				{
					pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(207, 30));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

					{
						lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
						pnlComp_a1.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(115, 25));
						lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
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
									String name = (String) data[1];						
									tagCompany.setTag(name);

									lblBroker.setEnabled(true);	
									lookupBroker.setEnabled(true);	
									tagBrokerName.setEnabled(true);	

									lookupBroker.setLookupSQL(getBrokerList());
								}
							}
						});
					}	
				}
				{
					pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

					{
						tagCompany = new _JTagLabel("[ ]");
						pnlComp_a2.add(tagCompany);
						tagCompany.setBounds(209, 27, 700, 22);
						tagCompany.setEnabled(true);	
						tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
				}
			}
			{
				pnlClient_center = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlClient_center, BorderLayout.CENTER);	
				pnlClient_center.setPreferredSize(new java.awt.Dimension(921, 35));					

				{
					pnlClient_center_a = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlClient_center.add(pnlClient_center_a, BorderLayout.WEST);	
					pnlClient_center_a.setPreferredSize(new java.awt.Dimension(95, 40));	
					pnlClient_center_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblBroker = new JLabel("Agent/Broker", JLabel.TRAILING);
						pnlClient_center_a.add(lblBroker);
						lblBroker.setEnabled(false);	
						lblBroker.setPreferredSize(new java.awt.Dimension(100, 40));
						lblBroker.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}						

					pnlClient_center_b = new JPanel(new BorderLayout(5, 5));
					pnlClient_center.add(pnlClient_center_b, BorderLayout.CENTER);	
					pnlClient_center_b.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlClient_center_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlClient_center_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlClient_center_b.add(pnlClient_center_b1, BorderLayout.WEST);	
						pnlClient_center_b1.setPreferredSize(new java.awt.Dimension(102, 29));					

						{
							lookupBroker = new _JLookup(null, "Client ID", 2, 2);
							pnlClient_center_b1.add(lookupBroker);
							lookupBroker.setBounds(20, 27, 20, 25);
							lookupBroker.setReturnColumn(0);
							lookupBroker.setEnabled(false);	
							lookupBroker.setPreferredSize(new java.awt.Dimension(135, 24));
							lookupBroker.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){	

										agent_id = (String) data[0];
										agent_name = (String) data[1];

										tagBrokerName.setTag(agent_name);

										btnReprocess.setEnabled(true);
										btnCancel.setEnabled(true);

										lblRemarks.setEnabled(true);
										txtRemarks.setEnabled(true);	

										btnReprocess.setEnabled(true);
										btnRecheck.setEnabled(true);
										btnCancel.setEnabled(true);

										displayAll_agentsCommission(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, pbl_id, seq_no );
										modelComm_CommSched.setEditable(true);
										tblComm_CommSched.setEditable(true);
									}
								}
							});
						}	
					}
					{
						pnlClient_center_b2 = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlClient_center_b.add(pnlClient_center_b2, BorderLayout.CENTER);	
						pnlClient_center_b2.setPreferredSize(new java.awt.Dimension(357, 25));	
						pnlClient_center_b2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						{

							tagBrokerName = new _JTagLabel("[ ]");
							pnlClient_center_b2.add(tagBrokerName);
							tagBrokerName.setBounds(209, 27, 700, 22);
							tagBrokerName.setEnabled(false);	
							tagBrokerName.setPreferredSize(new java.awt.Dimension(27, 33));
						}
					}						
				}
			}				
		} //end of Company

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlSubTable.setBorder(lineBorder);		

			{
				pnlRemarks = new JPanel(new BorderLayout(5, 5));
				pnlSubTable.add(pnlRemarks, BorderLayout.NORTH);	
				pnlRemarks.setPreferredSize(new java.awt.Dimension(95, 40));	
				pnlRemarks.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					pnlRemarks_a = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlRemarks.add(pnlRemarks_a, BorderLayout.WEST);	
					pnlRemarks_a.setPreferredSize(new java.awt.Dimension(216, 40));	
					pnlRemarks_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblRemarks = new JLabel("Reason for Reprocessing/New-Check ", JLabel.TRAILING);
						pnlRemarks_a.add(lblRemarks);
						lblRemarks.setEnabled(false);	
						lblRemarks.setPreferredSize(new java.awt.Dimension(230, 40));
					}						

					pnlRemarks_b = new JPanel(new BorderLayout(5, 5));
					pnlRemarks.add(pnlRemarks_b, BorderLayout.CENTER);	
					pnlRemarks_b.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlRemarks_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlRemarks_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlRemarks_b.add(pnlRemarks_b1, BorderLayout.WEST);	
						pnlRemarks_b1.setPreferredSize(new java.awt.Dimension(551, 32));					

						{
							txtRemarks = new JXTextField("");
							pnlRemarks_b1.add(txtRemarks);
							txtRemarks.setEnabled(false);	
							txtRemarks.setBounds(120, 25, 300, 22);	
							txtRemarks.setHorizontalAlignment(JTextField.LEFT);
						}	
					}			
				}
			}

			{						

				pnlCommDetails = new JPanel();
				pnlSubTable.add(pnlCommDetails, BorderLayout.CENTER);
				pnlCommDetails.setLayout(new BorderLayout(0,0));
				pnlCommDetails.setBorder(lineBorder);		
				pnlCommDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlCommDetails.setBorder(JTBorderFactory.createTitleBorder("List of Agent's Commission/Promo"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollComm_CommSched = new _JScrollPaneMain();
						pnlCommDetails.add(scrollComm_CommSched, BorderLayout.CENTER);
						{
							modelComm_CommSched = new modelComm_client_comm_override();

							tblComm_CommSched = new _JTableMain(modelComm_CommSched);
							scrollComm_CommSched.setViewportView(tblComm_CommSched);
							tblComm_CommSched.addMouseListener(this);								
							tblComm_CommSched.setSortable(false);
							tblComm_CommSched.getColumnModel().getColumn(0).setPreferredWidth(40);
							tblComm_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(6).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(7).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(8).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(9).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(10).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(11).setPreferredWidth(60);
							tblComm_CommSched.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}	

							}); 
							tblComm_CommSched.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblComm_CommSched.setCellSelectionEnabled(true);}									
										

								}
								public void mouseReleased(MouseEvent e) {
									if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblComm_CommSched.setCellSelectionEnabled(true);}
									
								}
							});

						}
						{
							rowHeaderComm_CommSched = tblComm_CommSched.getRowHeader22();
							scrollComm_CommSched.setRowHeaderView(rowHeaderComm_CommSched);
							scrollComm_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollComm_CommSchedtotal = new _JScrollPaneTotal(scrollComm_CommSched);
						pnlCommDetails.add(scrollComm_CommSchedtotal, BorderLayout.SOUTH);
						{
							modelComm_CommSched_total = new modelComm_client_comm_override();
							modelComm_CommSched_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null, null });

							tblComm_CommSched_total = new _JTableTotal(modelComm_CommSched_total, tblComm_CommSched);
							tblComm_CommSched_total.setFont(dialog11Bold);
							scrollComm_CommSchedtotal.setViewportView(tblComm_CommSched_total);
							((_JTableTotal) tblComm_CommSched_total).setTotalLabel(0);
						}
					}
				} 
				//end of Commission Schedule (by client)
			}
		} 
		{
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
					btnReprocess = new JButton("Reprocess Commission");
					pnlSouthCenterb.add(btnReprocess);
					btnReprocess.setActionCommand("Reprocess");
					btnReprocess.addActionListener(this);
					btnReprocess.setEnabled(false);
				}
				{
					btnRecheck = new JButton("Create New Check");
					pnlSouthCenterb.add(btnRecheck);
					btnRecheck.setActionCommand("NewCheck");
					btnRecheck.addActionListener(this);
					btnRecheck.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	//display tables
	public void displayAll_agentsCommission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String pbl_id, String seq_no ) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select * from ( \n" +

			"select\r\n" + 
			"\r\n" + 
			"false," +
			"trim(e.entity_name) as agent_name,\r\n" + 			
			"bb.rate,\r\n" + 
			"f.position_abbv,\r\n" + 
			"( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" + 
			"	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" + 
			"	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" + 
			"	case when trim(a.comm_type) = 'FC' then 'Full Comm' else " +
			"	case when trim(a.comm_type) = 'BB' then 'Promo' " +
			"	else '' end end end end end) as comm_type,\r\n" + 
			"a.prev_commamt,\r\n" + 
			"a.comm_amt as sked_amt,\r\n" + 
			"0.00 as adjustment,\r\n" + 
			"a.comm_amt as gross_amt,\r\n" + 
			"bb.wtax_amt as wtax_amt,\r\n" + 
			"bb.caliq_amt as liq_amt,\r\n" + 
			"(a.comm_amt-bb.wtax_amt-bb.caliq_amt) as net_amt, \n" +
			"( case when g.applied_amt = 0.00 then 'Released' else \n " +
			"	case when b.status_id = 'I' then 'Canceled' else \n" +
			"	case when status = 'A' and a.qualified = true then 'Qualified' else\r\n" + 
			"	case when status = 'A' and a.qualified is null then 'Not Yet Qualified' else\r\n" +
			"	case when a.status = 'P' and h.pv_no is null then 'Voucher Preparation' else\r\n" + 
			"	case when a.status = 'P' and h.pv_no is not null and i.cv_no is null then 'Voucher on Process' else\r\n" + 
			"	case when a.status = 'P' and h.pv_no is not null and i.cv_no is not null and i.date_paid is null and i.proc_id in (0,1,2) then 'For Check Signature' else \n" + 
			"	case when a.status = 'P' and h.pv_no is not null and i.cv_no is not null and i.date_paid is null and i.proc_id = 3 then 'For Release' else \n" + 
			"	case when a.status = 'P' and h.pv_no is not null and i.cv_no is not null and i.date_paid is null and i.proc_id = 5 then 'Released' else \n" + 
			"	case when b.status_id = 'R' then 'Released' end end end end end end end end end end) as status, \n" +
			"a.release_date as rel_sked,\r\n" + 
			"coalesce(b.date_released, null),\r\n" + 
			"(case when a.cdf_no is null then a.old_cdfno else a.cdf_no end) as cdf_no,\r\n" + 
			"b.rplf_no,\r\n" + 
			"c.cv_no,\r\n" + 
			"a.remarks,\r\n" +
			"a.agent_code \n" + 
			"\r\n" + 
			"from cm_schedule_dl a\r\n" + 
			"left join cm_cdf_hd b on a.cdf_no = b.cdf_no \r\n" + 
			"left join cm_cdf_dl bb on a.cdf_no = bb.cdf_no and a.comm_type = bb.comm_type and \r\n" +
			"		(case when bb.account_code is not null then a.account_code = bb.account_code else true end)\r\n" + 
			"left join rf_pv_header c on b.rplf_no = c.pv_no\r\n" + 
			"left join cm_schedule_hd d on a.pbl_id = d.pbl_id \n" +
			"		and a.seq_no=d.seq_no and a.agent_code=d.agent_code\r\n" + 
			"left join rf_entity e on bb.account_code = e.entity_id\r\n" + 
			"left join mf_sales_position f on d.orig_position  = f.position_code\r\n" + 
			"left join cm_cdf_dl g on b.cdf_no = g.cdf_no and a.tran_type = g.tran_type \n" +
			"		and a.pbl_id = g.pbl_id and a.pbl_id = g.pbl_id and a.comm_type = g.comm_type and a.promo_code = g.promo_code \n" + 
			"left join rf_pv_header h on b.rplf_no = h.pv_no\r\n" + 
			"left join rf_cv_header i on h.cv_no = i.cv_no\r\n" + 
			"\r\n" + 
			"where a.agent_code = '"+agent_id+"' \r\n" +			
			"order by  e.entity_name, bb.cdf_no, a.comm_type ) a \r\n" + 
			"where a.status not in ('Not Yet Qualified','Released','Qualified','Canceled')" + 
			"" ;

		System.out.printf("SQL #1: %s", sql);
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

			JOptionPane.showMessageDialog(getContentPane(),"Selected agent/broker does not \n" +
					"have processed commission yet.","Warning",JOptionPane.WARNING_MESSAGE);
			btnReprocess.setEnabled(false);

			modelComm_CommSched_total = new modelComm_client_comm_override();
			modelComm_CommSched_total.addRow(new Object[] { "Total", null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null, null });

			tblComm_CommSched_total = new _JTableTotal(modelComm_CommSched_total, tblComm_CommSched);
			tblComm_CommSched_total.setFont(dialog11Bold);
			scrollComm_CommSchedtotal.setViewportView(tblComm_CommSched_total);
			((_JTableTotal) tblComm_CommSched_total).setTotalLabel(0);}

		tblComm_CommSched.packAll();		
		tblComm_CommSched.getColumnModel().getColumn(0).setPreferredWidth(40);
		tblComm_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(6).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(7).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(8).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(9).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(10).setPreferredWidth(60);
		//tblComm_CommSched.getColumnModel().getColumn(11).setPreferredWidth(60);

		adjustRowHeight_account(tblComm_CommSched);
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){cancel();}

		if(e.getActionCommand().equals("Reprocess")&&FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true){saveReprocess();}
		else if(e.getActionCommand().equals("Reprocess")&&FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to reprocess commission.","Warning",JOptionPane.WARNING_MESSAGE); }

		if(e.getActionCommand().equals("NewCheck")&&FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true){saveNewCheck();}
		else if(e.getActionCommand().equals("NewCheck")&&FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create new check.","Warning",JOptionPane.WARNING_MESSAGE); }


	}

	public void mouseClicked(MouseEvent evt) {
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

	private void cancel(){

		if (checkTaggedAccountBeforeCancel()==true) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}

		}

		else {	execute_cancel(); 	}
	}

	private void execute_cancel(){

		lblRemarks.setEnabled(false);	
		txtRemarks.setEnabled(false);
		lookupBroker.setValue("");
		txtRemarks.setText("");
		tagBrokerName.setText("[ ]");

		btnReprocess.setEnabled(false);
		btnRecheck.setEnabled(false);
		btnCancel.setEnabled(false);

		FncTables.clearTable(modelComm_CommSched);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderComm_CommSched.setModel(listModel);//Setting of DefaultListModel into rowHeader.	

		modelComm_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 5);
		modelComm_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 6);
		modelComm_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 7);
		modelComm_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 8);
		modelComm_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 9);
	}

	private void saveReprocess(){		

		if (!txtRemarks.getText().equals("")) {

			if (nothingtoProcess()==false) {

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to reprocess selected commission/promo?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					pgUpdate db = new pgUpdate();				
					executeReprocess(db);
					db.commit();

					JOptionPane.showMessageDialog(getContentPane(),"Commission/Promo selected reprocessed.","Information",
							JOptionPane.INFORMATION_MESSAGE);
					displayAll_agentsCommission(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, pbl_id, seq_no );

				}

				else {}			
			}

			else if (nothingtoProcess()==true) {						
				JOptionPane.showMessageDialog(getContentPane(),"Please select commission/promo to reprocess.","Error",JOptionPane.ERROR_MESSAGE);
			}
		}

		else {						
			JOptionPane.showMessageDialog(getContentPane(),"Reason for reprocessing is required.","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void saveNewCheck(){		

		if (!txtRemarks.getText().equals("")) {

			if (nothingtoProcessNewCheck()==false) {

				if (noCV()==false) {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to create new check for selected commission/promo?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();				
						executeNewCheck(db);
						db.commit();

						JOptionPane.showMessageDialog(getContentPane(),"Commission/Promo selected is/are ready for new check preparation.","Information",
								JOptionPane.INFORMATION_MESSAGE);
						displayAll_agentsCommission(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, pbl_id, seq_no );

					}

					else {}	

				}

				else if (noCV()==true) {						
					JOptionPane.showMessageDialog(getContentPane(),"Selected Commission/Promo has no CV yet.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}

			else if (nothingtoProcessNewCheck()==true) {						
				JOptionPane.showMessageDialog(getContentPane(),"Please select commission/promo for new check preparation","Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else {						
			JOptionPane.showMessageDialog(getContentPane(),"Reason for reprocessing is required.","Error",JOptionPane.ERROR_MESSAGE);
		}

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		lblBroker.setEnabled(true);	
		lookupBroker.setEnabled(true);	
		tagBrokerName.setEnabled(true);	

		lookupBroker.setLookupSQL(getBrokerList());
		lookupCompany.setValue(co_id);
	}


	//select, lookup and get statements	
	public static String getBrokerList(){

		String sql = "select \r\n" + 
		"\r\n" + 
		"a.agent_code as \"Agent Code\" ,\r\n" + 
		"trim(c.entity_name) as \"Client Name\"\r\n" +
		"\r\n" + 
		"from ( select distinct on ( agent_code ) agent_code from cm_schedule_hd where status_id = 'A'  ) a\r\n" + 
		"left join rf_entity c on agent_code = c.entity_id\r\n" +
		"order by c.entity_name " ;
		return sql;

	}	


	//table operations	
	private void totalAllComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal prev_comm 	= new BigDecimal(0.00);	
		BigDecimal sked_amt		= new BigDecimal(0.00);	
		BigDecimal adjustment	= new BigDecimal(0.00);	
		BigDecimal gross_amt	= new BigDecimal(0.00);	
		BigDecimal wtax_amt		= new BigDecimal(0.00);	
		BigDecimal ca_liq		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { prev_comm 	= prev_comm.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { prev_comm 	= prev_comm.add(new BigDecimal(0.00)); }

			try { sked_amt 	= sked_amt.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { sked_amt 	= sked_amt.add(new BigDecimal(0.00)); }

			try { adjustment 	= adjustment.add(((BigDecimal) modelMain.getValueAt(x,7)));} 
			catch (NullPointerException e) { adjustment = adjustment.add(new BigDecimal(0.00)); }

			try { gross_amt 	= gross_amt.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { gross_amt 	= gross_amt.add(new BigDecimal(0.00)); }

			try { wtax_amt 	= wtax_amt.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { wtax_amt 	= wtax_amt.add(new BigDecimal(0.00)); }

			try { ca_liq 	= ca_liq.add(((BigDecimal) modelMain.getValueAt(x,10)));} 
			catch (NullPointerException e) { ca_liq 	= ca_liq.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null, null, null, prev_comm , sked_amt, adjustment, gross_amt, wtax_amt, ca_liq, null, null });		
	}

	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}


	//processes	
	private boolean checkTaggedAccountBeforeCancel() {

		boolean containsTaggedAcct = false;		

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}

			if(isTrue){					
				containsTaggedAcct=true;
				break;					
			}		
			else {}
		}
		return containsTaggedAcct;

	}

	private boolean nothingtoProcess() {

		boolean nothingtoProcess = true;		

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}

			if(isTrue){		
				nothingtoProcess = false;	
				break;
			}		
			else {}
		}
		return nothingtoProcess;

	}

	private boolean nothingtoProcessNewCheck() {

		boolean nothingtoProcess = true;		

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}	


			if(isTrue){		
				nothingtoProcess = false;	
				break;	
			}	
		}
		return nothingtoProcess;

	}

	private boolean noCV() {

		boolean noCV = false;		

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}	

			String cv_no = 	"";

			if(isTrue){					
				try { cv_no = 	modelComm_CommSched.getValueAt(x,17).toString().trim();} 
				catch (NullPointerException e) {  cv_no = ""; }	

				if (cv_no==""||cv_no.equals(""))
				{
					noCV = true;
					break;
				}
				else
				{

				}				
			}	
		}
		return noCV;

	}


	//save
	private void executeReprocess(pgUpdate db){

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}

			if(isTrue){		

				String remarks 		=	txtRemarks.getText().trim().replace("'", "''");
				String comm_type_1 	= 	modelComm_CommSched.getValueAt(x,4).toString().trim();
				String comm_type_2 	= 	"";
				String tran_type	= 	"";
				String agent_code 	= 	lookupBroker.getText();
				String cdf_no 		= 	modelComm_CommSched.getValueAt(x,15).toString().trim();


				if (comm_type_1.equals("1st CA")) 			{comm_type_2  = "1"; tran_type = "AA";}
				else if (comm_type_1.equals("2nd CA")) 		{comm_type_2  = "2"; tran_type = "AA";}
				else if (comm_type_1.equals("3rd CA")) 		{comm_type_2  = "3"; tran_type = "AA";}
				else if (comm_type_1.equals("4th CA")) 		{comm_type_2  = "4"; tran_type = "AA";}
				else if (comm_type_1.equals("Full Comm")) 	{comm_type_2  = "FC"; tran_type = "AA";}	
				else if (comm_type_1.equals("Promo")) 		{comm_type_2  = "BB"; tran_type = "BB";}	

				String sqlDetail = 
					"update cm_schedule_dl set " +					
					"status = 'A', \n" +
					"cdf_no = '', " +
					"remarks = '"+remarks+"',  \n" +
					"old_cdfno = '"+cdf_no+"'  \n" +				
					"where cdf_no = '"+cdf_no+"'  \n" +
					"and tran_type = '"+tran_type+"' \n" +
					"and comm_type = '"+comm_type_2+"' \n\n"  +
					"and agent_code = '"+agent_code+"' \n\n" ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);						
			}		
			else {}
		}

	}

	private void executeNewCheck(pgUpdate db){

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}

			String cv_no = 	"";
			try { cv_no = 	modelComm_CommSched.getValueAt(x,17).toString().trim();} 
			catch (NullPointerException e) {  cv_no = ""; }

			if(isTrue&&cv_no!=""&&!cv_no.equals("")){		

				String remarks 	=	sql_CVRemarks(cv_no)+ " ; " + txtRemarks.getText().trim().replace("'", "''");
				String pv_no = 	"";
				try { pv_no = 	modelComm_CommSched.getValueAt(x,16).toString().trim();} 
				catch (NullPointerException e) {  pv_no = ""; }

				String sqlDetail = 
					"update rf_cv_header set " +					
					"status_id = 'I', \n" +
					"remarks ='"+remarks+"'  \n" +
					"where cv_no = '"+cv_no+"' " ;
				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);		

				String sqlDetail2 = 
					"update rf_pv_header set " +					
					"cv_no = '', \n" +
					"proc_id = 3, " +
					"status_id = 'P',  \n" +
					"edited_by = '"+UserInfo.EmployeeCode+"'," +
					"date_edited = now() " +
					"where pv_no = '"+pv_no+"'" +
					"and status_id != 'I' " ;					
				System.out.printf("SQL #2: %s", sqlDetail2);
				db.executeUpdate(sqlDetail2, false);		

				String sqlDetail3 = 
					"update rf_check set " +	
					"status_id = 'I'  \n" +
					"where cv_no = '"+cv_no+"' " ;			
				System.out.printf("SQL #3: %s", sqlDetail3);
				db.executeUpdate(sqlDetail3, false);		
			}		
			else {}
		}

	}


	//SQL	
	private String sql_CVRemarks(String cv_no) {

		String a = "";

		String SQL = 
			"select remarks from rf_cv_header where cv_no = '"+cv_no+"'\r\n"  ;

		System.out.printf("SQL #1: sql_CVRemarks", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {a = "";}
			else {a = (String) db.getResult()[0][0]; }

		}else{
			a = "";
		}

		return a;
	}





}