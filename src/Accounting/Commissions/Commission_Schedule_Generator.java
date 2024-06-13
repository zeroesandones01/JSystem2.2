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
import java.text.SimpleDateFormat;
import java.util.Date;

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

import tablemodel.modelCommSchedule;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
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

public class Commission_Schedule_Generator extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Commission Schedule Generator";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlAccountList;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlComp_a1_a;
	private JPanel pnlComp_a1_b;
	private JPanel pnlDetails;
	private JPanel pnlDetailsWest;
	private JPanel pnlDetailsEast;
	
	private JLabel lblUnit;
	private JLabel lblEntity;
	private JLabel lblAgent;
	private JLabel lblCashier;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblModel;
	private JLabel lblCompany;		

	private _JLookup lookupCompany;
	
	private JTextField txtUnit_ID;
	private JTextField txtClient_ID;
	private JTextField txtAgent_ID;
	private JTextField txtScheme_ID;
	private JTextField txtProject_ID;
	private JTextField txtPhase_ID;
	private JTextField txtModel_ID;
	private JTextField txtUnit;
	private JTextField txtClient;
	private JTextField txtAgent;
	private JTextField txtScheme;
	private JTextField txtProject;
	private JTextField txtPhase;
	private JTextField txtModel;
	
	private JButton btnCreateCommissionSched;	
	private JButton btnRefresh;	
	
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	private _JTagLabel tagCompany;

	private _JScrollPaneMain scrollAcctList;
	private _JScrollPaneTotal scrollAcctList_total;
	private _JTableMain tblCommSchedule;
	private JList rowHeaderAccountList;
	private _JTableTotal tblCommSchedule_total;
	private modelCommSchedule modelCommSchedule;
	private modelCommSchedule modelCommSchedule_total;
	
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		

	String co_id 	= "";		
	String company 	= "";
	String company_logo;	
	String to_do 	= "";  //to distinguish whether to add new agent or new sched.			
	String entity_id 	= "";		
	String entity_name 	= "";
	String pbl_id 		= "";		
	Integer seq_no 		= 0;
	String datersvd 	= "";
	Double sellingprice = 0.00;
	String agent_id 	= "";
	String agent_name 	= "";
	String pmt_scheme_id 	= "";
	String pmt_scheme_desc = "";	
	String proj_id 		= "";	
	String proj_desc	= "";	
	String hse_model_id	= "";	
	String hse_model_desc= "";	
	String phase_code	= "";	
	String phase_no		= "";
	
	
	public Commission_Schedule_Generator() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Commission_Schedule_Generator(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Commission_Schedule_Generator(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(884, 577));
		this.setBounds(0, 0, 884, 577);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(745, 487));

		{ //start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(905, 40));	
			pnlComp.setBorder(lineBorder);

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(926, 38));	
				pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));			

				{
					pnlComp_a1 = new JPanel(new BorderLayout(0, 0));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(152, 33));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						pnlComp_a1_a = new JPanel(new BorderLayout(0, 5));
						pnlComp_a1.add(pnlComp_a1_a, BorderLayout.WEST);	
						pnlComp_a1_a.setPreferredSize(new java.awt.Dimension(75, 33));
						pnlComp_a1_a.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

						lblCompany = new JLabel("COMPANY  ", JLabel.TRAILING);
						pnlComp_a1_a.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(115, 25));
						lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD|Font.ITALIC,12));
					}
					{
						pnlComp_a1_b = new JPanel(new BorderLayout(0, 5));
						pnlComp_a1.add(pnlComp_a1_b, BorderLayout.CENTER);	
						pnlComp_a1_b.setPreferredSize(new java.awt.Dimension(186, 33));
						pnlComp_a1_b.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

						lookupCompany = new _JLookup(null, "Company",0,2);
						pnlComp_a1_b.add(lookupCompany);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setReturnColumn(0);
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id 		= (String) data[0];	
									String name = (String) data[1];						
									tagCompany.setTag(name);									

									enableButtons(false, false);	
									displayCommSchedule(modelCommSchedule,rowHeaderAccountList,modelCommSchedule_total);
									enableFields(false);		
									enableButtons(false, true);
									refreshFields();
								}
							}
						});
					}	
				}
				{
					pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(8, 0, 5, 5));

					{
						tagCompany = new _JTagLabel("[ ]");
						pnlComp_a2.add(tagCompany);
						tagCompany.setBounds(209, 27, 700, 22);
						tagCompany.setEnabled(true);	
						tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
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

				pnlAccountList = new JPanel();
				pnlSubTable.add(pnlAccountList, BorderLayout.CENTER);
				pnlAccountList.setLayout(new BorderLayout(0,0));
				pnlAccountList.setBorder(lineBorder);		
				pnlAccountList.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlAccountList.setBorder(JTBorderFactory.createTitleBorder("List of Accounts without Commission Schedule"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollAcctList = new _JScrollPaneMain();
						pnlAccountList.add(scrollAcctList, BorderLayout.CENTER);
						{
							modelCommSchedule = new modelCommSchedule();

							tblCommSchedule = new _JTableMain(modelCommSchedule);
							scrollAcctList.setViewportView(tblCommSchedule);
							tblCommSchedule.addMouseListener(this);								
							tblCommSchedule.setSortable(false);
							tblCommSchedule.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {clickTable();}							
								public void keyPressed(KeyEvent e) {clickTable();}	

							}); 
							tblCommSchedule.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblCommSchedule.rowAtPoint(e.getPoint()) == -1){}
									else{tblCommSchedule.setCellSelectionEnabled(true);}
									clickTable();
								}
								public void mouseReleased(MouseEvent e) {
									if(tblCommSchedule.rowAtPoint(e.getPoint()) == -1){}
									else{tblCommSchedule.setCellSelectionEnabled(true);}
									clickTable();
								}
							});

						}
						{
							rowHeaderAccountList = tblCommSchedule.getRowHeader22();
							scrollAcctList.setRowHeaderView(rowHeaderAccountList);
							scrollAcctList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollAcctList_total = new _JScrollPaneTotal(scrollAcctList);
							pnlAccountList.add(scrollAcctList_total, BorderLayout.SOUTH);
							{
								modelCommSchedule_total = new modelCommSchedule();
								modelCommSchedule_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

								tblCommSchedule_total = new _JTableTotal(modelCommSchedule_total, tblCommSchedule);
								tblCommSchedule_total.setFont(dialog11Bold);
								scrollAcctList_total.setViewportView(tblCommSchedule_total);
								((_JTableTotal) tblCommSchedule_total).setTotalLabel(0);
							}
						}
					}
				} 
				//end 
			}
			
			{
				pnlDetails = new JPanel(new BorderLayout(5, 5));
				pnlTable.add(pnlDetails, BorderLayout.SOUTH);
				pnlDetails.setPreferredSize(new java.awt.Dimension(872, 221));
				pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Account Details"));// XXX
				{
					pnlDetailsWest = new JPanel(new GridLayout(7, 2, 5, 5));
					pnlDetails.add(pnlDetailsWest, BorderLayout.WEST);
					pnlDetailsWest.setPreferredSize(new java.awt.Dimension(163, 186));
					
					{
						lblUnit = new JLabel("Unit | Seq No.", JLabel.TRAILING);
						pnlDetailsWest.add(lblUnit);
						lblUnit.setEnabled(false);	
					}
					{
						txtUnit_ID = new JTextField();
						pnlDetailsWest.add(txtUnit_ID, BorderLayout.CENTER);
						txtUnit_ID.setEditable(false);
						txtUnit_ID.setEnabled(false);
						txtUnit_ID.setHorizontalAlignment(JTextField.CENTER);
						txtUnit_ID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblEntity = new JLabel("Client Name", JLabel.TRAILING);
						pnlDetailsWest.add(lblEntity);
						lblEntity.setEnabled(false);	
					}
					{
						txtClient_ID = new JTextField();
						pnlDetailsWest.add(txtClient_ID, BorderLayout.CENTER);
						txtClient_ID.setEditable(false);
						txtClient_ID.setHorizontalAlignment(JTextField.CENTER);
						txtClient_ID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblAgent = new JLabel("Agent", JLabel.TRAILING);
						pnlDetailsWest.add(lblAgent);
						lblAgent.setEnabled(false);	
					}
					{
						txtAgent_ID = new JTextField();
						pnlDetailsWest.add(txtAgent_ID, BorderLayout.CENTER);
						txtAgent_ID.setEditable(false);
						txtAgent_ID.setHorizontalAlignment(JTextField.CENTER);
						txtAgent_ID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblCashier = new JLabel("Pmt. Scheme", JLabel.TRAILING);
						pnlDetailsWest.add(lblCashier);
						lblCashier.setEnabled(false);	
					}
					{
						txtScheme_ID = new JTextField();
						pnlDetailsWest.add(txtScheme_ID, BorderLayout.CENTER);
						txtScheme_ID.setEditable(false);
						txtScheme_ID.setHorizontalAlignment(JTextField.CENTER);
						txtScheme_ID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlDetailsWest.add(lblProject);
						lblProject.setEnabled(false);	
					}
					{
						txtProject_ID = new JTextField();
						pnlDetailsWest.add(txtProject_ID, BorderLayout.CENTER);
						txtProject_ID.setEditable(false);
						txtProject_ID.setHorizontalAlignment(JTextField.CENTER);
						txtProject_ID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlDetailsWest.add(lblPhase);
						lblPhase.setEnabled(false);	
					}
					{
						txtPhase_ID = new JTextField();
						pnlDetailsWest.add(txtPhase_ID, BorderLayout.CENTER);
						txtPhase_ID.setEditable(false);
						txtPhase_ID.setHorizontalAlignment(JTextField.CENTER);
						txtPhase_ID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblModel = new JLabel("Hse. Model", JLabel.TRAILING);
						pnlDetailsWest.add(lblModel);
						lblModel.setEnabled(false);	
					}
					{
						txtModel_ID = new JTextField();
						pnlDetailsWest.add(txtModel_ID, BorderLayout.CENTER);
						txtModel_ID.setEditable(false);
						txtModel_ID.setHorizontalAlignment(JTextField.CENTER);
						txtModel_ID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
				}
				pnlDetailsEast = new JPanel(new GridLayout(7, 1, 5, 5));
				pnlDetails.add(pnlDetailsEast, BorderLayout.CENTER);
				pnlDetailsEast.setPreferredSize(new java.awt.Dimension(300, 159));
				{
					txtUnit = new JTextField();
					pnlDetailsEast.add(txtUnit, BorderLayout.CENTER);
					txtUnit.setEditable(false);
					txtUnit.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}
				{
					txtClient = new JTextField();
					pnlDetailsEast.add(txtClient, BorderLayout.CENTER);
					txtClient.setEditable(false);
					txtClient.setEnabled(false);
					txtClient.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}
				{
					txtAgent = new JTextField();
					pnlDetailsEast.add(txtAgent, BorderLayout.CENTER);
					txtAgent.setEditable(false);
					txtAgent.setEnabled(false);
					txtAgent.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}
				{
					txtScheme = new JTextField();
					pnlDetailsEast.add(txtScheme, BorderLayout.CENTER);
					txtScheme.setEditable(false);
					txtScheme.setEnabled(false);
					txtScheme.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}	
				{
					txtProject = new JTextField();
					pnlDetailsEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
					txtProject.setEnabled(false);
					txtProject.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}	
				{
					txtPhase = new JTextField();
					pnlDetailsEast.add(txtPhase, BorderLayout.CENTER);
					txtPhase.setEditable(false);
					txtPhase.setEnabled(false);
					txtPhase.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}	
				{
					txtModel = new JTextField();
					pnlDetailsEast.add(txtModel, BorderLayout.CENTER);
					txtModel.setEditable(false);
					txtModel.setEnabled(false);
					txtModel.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}	
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
					btnCreateCommissionSched = new JButton("Create Comm. Schedule");
					pnlSouthCenterb.add(btnCreateCommissionSched);
					btnCreateCommissionSched.setAlignmentX(0.5f);
					btnCreateCommissionSched.setAlignmentY(0.5f);
					btnCreateCommissionSched.setEnabled(false);
					btnCreateCommissionSched.setMaximumSize(new Dimension(100, 30));
					btnCreateCommissionSched.setMnemonic(KeyEvent.VK_C);
					btnCreateCommissionSched.addActionListener(this);
					btnCreateCommissionSched.setActionCommand("Generate");
				}
				{
					btnRefresh = new JButton("Refresh Table");
					pnlSouthCenterb.add(btnRefresh);
					btnRefresh.setAlignmentX(0.5f);
					btnRefresh.setAlignmentY(0.5f);
					btnRefresh.setMaximumSize(new Dimension(100, 30));
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.setMnemonic(KeyEvent.VK_R);
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}
			}
		}
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	//display tables
	public void displayCommSchedule(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

//		String sql = 			
//			"SELECT " +
//			"a.entity_id ,   " +		//0
//			"trim(b.entity_name),  " +	//1
//			"a.pbl_id ," +				//2
//			"a.seq_no , " +				//3
//			"trim(f.description) , " +	//4		
//			"a.datersvd ," +			//5
//			"v.net_sprice  ," +			//6
//			"a.sellingagent  , " +		//7
//			"trim(c.entity_name)  ,  " +//8
//			"a.pmt_scheme_id  ," +		//9
//			"trim(d.pmt_scheme_desc) ," + //10
//			"a.projcode ," +			//11			
//			"e.proj_name,  " +			//12
//			"h.sub_proj_id ,  " +		//13
//			"h.phase,  " +				//14
//			"a.model_id ,  " +			//15
//			"g.model_desc   " +			//16
//			
//			
//			"FROM (select * from rf_sold_unit where status_id = 'A' ) a " +
//			"left join rf_entity b on a.entity_id = b.entity_id \n"  +
//			"left join rf_entity c on a.sellingagent = c.entity_id \n"  +
//			"left join mf_payment_scheme d on a.pmt_scheme_id = d.pmt_scheme_id \n" +
//			"left join mf_project e on a.projcode = e.proj_id   \n" +
//			"left join mf_unit_info f on a.pbl_id = f.pbl_id \n"  +
//			"left join mf_product_model g on a.model_id = g.model_id  \n" +
//			"left join mf_sub_project h on f.proj_id = h.proj_id and f.phase = h.phase \n\n "  +
//			"left join (select distinct on (pbl_id, seq_no) * from rf_client_price_history order by pbl_id, seq_no,tran_date desc) v " +
//			"	on a.pbl_id = v.pbl_id and a.seq_no = v.seq_no and a.entity_id = v.entity_id\r\n" + 
//			
//			"where (a.pbl_id, a.seq_no) not in (select pbl_id, seq_no from cm_schedule_hd where status_id = 'A') " +
//			"and (a.pbl_id, a.seq_no) in (select pbl_id, seq_no from rf_buyer_status where byrstatus_id = '01' and status_id = 'A')  \n" +
//			"and e.co_id = '"+co_id+"' \n\n";
		
		String sql = "SELECT a.entity_id ,   \n" + 
				"trim(b.entity_name),  a.pbl_id ,a.seq_no, trim(f.description), \n" + 
				"a.datersvd ,v.net_sprice  ,a.sellingagent, trim(c.entity_name),  \n" + 
				"a.pmt_scheme_id  ,trim(d.pmt_scheme_desc),a.projcode,\n" + 
				"e.proj_name, h.sub_proj_id, h.phase, a.model_id, g.model_desc   \n" + 
				"FROM (select * from rf_sold_unit where status_id = 'A' ) a \n" + 
				"left join rf_entity b on a.entity_id = b.entity_id \n" + 
				"left join rf_entity c on a.sellingagent = c.entity_id \n" + 
				"left join mf_payment_scheme d on a.pmt_scheme_id = d.pmt_scheme_id \n" + 
				"left join mf_project e on a.projcode = e.proj_id   \n" + 
				"left join mf_unit_info f on TRIM(a.projcode) = TRIM(f.proj_id) and TRIM(a.pbl_id) = TRIM(f.pbl_id) AND COALESCE(a.server_id, '') = COALESCE(f.server_id, '') and COALESCE(a.proj_server, '') = coalesce(f.proj_server, '')\n" + 
				"left join mf_product_model g on TRIM(a.model_id) = TRIM(g.model_id) AND COALESCE(a.server_id, '') = COALESCE(g.server_id, '') and COALESCE(a.proj_server, '') = coalesce(g.proj_server, '')\n" + 
				"left join mf_sub_project h on trim(f.proj_id) = TRIM(h.proj_id) and TRIM(f.phase) = TRIM(h.phase) AND h.status_id = 'A' \n" + 
				"left join rf_client_price_history v on trim(a.entity_id) = trim(v.entity_id) and trim(a.projcode) = trim(v.proj_id) and trim(a.pbl_id) = trim(v.pbl_id) and a.seq_no = v.seq_no and v.status_id = 'A'\n" + 
				"where NOT EXISTS (SELECT *\n" + 
				"		    FROM cm_schedule_hd\n" + 
				"		    where trim(account_code) = trim(a.entity_id)\n" + 
				"		    and TRIM(proj_id) = trim(a.projcode)\n" + 
				"		    and trim(pbl_id) = trim(a.pbl_id)\n" + 
				"		    and seq_no = a.seq_no \n" + 
				"		    and trim(status_id) = 'A')\n" + 
				"and EXISTS (SELECT *\n" + 
				"		    FROM rf_buyer_status\n" + 
				"		    where TRIM(entity_id) = TRIM(a.entity_id) \n" + 
				"		    and TRIM(proj_id) = TRIM(a.projcode)\n" + 
				"		    and trim(pbl_id) = trim(a.pbl_id)\n" + 
				"		    and seq_no = a.seq_no \n" + 
				"		    and trim(byrstatus_id) = '01'\n" + 
				"		    AND TRIM(status_id) = 'A')\n" + 
				"and e.co_id = '"+co_id+"'";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalAllComm(modelMain, modelTotal);			
		}		


		else {
			modelCommSchedule_total = new modelCommSchedule();
			modelCommSchedule_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

			tblCommSchedule_total = new _JTableTotal(modelCommSchedule_total, tblCommSchedule);
			tblCommSchedule_total.setFont(dialog11Bold);
			scrollAcctList_total.setViewportView(tblCommSchedule_total);
			((_JTableTotal) tblCommSchedule_total).setTotalLabel(0);}

		tblCommSchedule.packAll();				
		tblCommSchedule.getColumnModel().getColumn(0).setPreferredWidth(80);

		int row_tot = tblCommSchedule.getRowCount();			
		modelCommSchedule_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		adjustRowHeight_account(tblCommSchedule);
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		
		if(e.getActionCommand().equals("Generate") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true ){			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all details correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {				
				
				/*CreateCommissionSchedule.createCommHeader(txtAgent_ID.getText(), pmt_scheme_id, entity_id, proj_id, pbl_id, 
					seq_no, sellingprice, co_id, datersvd, hse_model_id, phase_code, 1);*/
								
				if (comm_Create_sched(txtAgent_ID.getText(), pmt_scheme_id, entity_id, proj_id, pbl_id, 
						seq_no, sellingprice, co_id, datersvd, hse_model_id, phase_code)==true)
				{
					JOptionPane.showMessageDialog(null,"Commission Schedule generation successful.","Information",JOptionPane.INFORMATION_MESSAGE); 
				}
				
				else
				{
					JOptionPane.showMessageDialog(null,"Commission Schedule generation failed.","Information",JOptionPane.INFORMATION_MESSAGE); 
				}
				
				displayCommSchedule(modelCommSchedule,rowHeaderAccountList,modelCommSchedule_total);
				refreshFields();
				btnCreateCommissionSched.setEnabled(false);
				
				if (pbl_schedule_exist(pbl_id, seq_no)==true) { refreshFields(); }
			}
		}		
		else if(e.getActionCommand().equals("Generate")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create commission schedule.","Warning",JOptionPane.WARNING_MESSAGE); }
		
		if(e.getActionCommand().equals("Refresh")){refresh();}		

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

	private void refresh(){

		displayCommSchedule(modelCommSchedule,rowHeaderAccountList,modelCommSchedule_total);
		enableFields(false);		
		enableButtons(false, true);
		refreshFields();
		initializeVariables();
	}
	

	//enable, disable
	private void enableFields(boolean x){

		lblUnit.setEnabled(x);	
		lblEntity.setEnabled(x);	
		lblAgent.setEnabled(x);	
		lblCashier.setEnabled(x);	
		lblProject.setEnabled(x);	
		lblPhase.setEnabled(x);	
		lblModel.setEnabled(x);	
		
		txtUnit_ID.setEnabled(x);	
		txtUnit.setEnabled(x);	
		txtClient_ID.setEnabled(x);	
		txtClient.setEnabled(x);	
		txtAgent_ID.setEnabled(x);	
		txtAgent.setEnabled(x);	
		txtScheme_ID.setEnabled(x);	
		txtScheme.setEnabled(x);	
		txtProject_ID.setEnabled(x);	
		txtProject.setEnabled(x);	
		txtPhase_ID.setEnabled(x);	
		txtPhase.setEnabled(x);	
		txtModel_ID.setEnabled(x);	
		txtModel.setEnabled(x);	
		
	}
	
	private void refreshFields(){

		txtUnit_ID.setText("");	
		txtUnit.setText("");	
		txtClient_ID.setText("");	
		txtClient.setText("");		
		txtAgent_ID.setText("");	
		txtAgent.setText("");	
		txtScheme_ID.setText("");	
		txtScheme.setText("");		
		txtProject_ID.setText("");		
		txtProject.setText("");	
		txtPhase_ID.setText("");		
		txtPhase.setText("");	
		txtModel_ID.setText("");	
		txtModel.setText("");			
		
	}

	public void enableButtons(Boolean a, Boolean b){

		btnCreateCommissionSched.setEnabled(a);
		btnRefresh.setEnabled(b);			
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();						

		enableButtons(false, true);	
		//displayCommSchedule(modelCommSchedule,rowHeaderAccountList,modelCommSchedule_total);
		
		lookupCompany.setValue(co_id);
	}
	
	private void initializeVariables(){

		entity_id 	= "";		
		entity_name = "";
		pbl_id 		= "";		
		seq_no 		= 0;
		datersvd 	= "";
		sellingprice = 0.00;
		agent_id 	= "";
		agent_name 	= "";
		pmt_scheme_id 	= "";
		pmt_scheme_desc = "";	
		proj_id 	= "";	
		proj_desc	= "";	
		hse_model_id= "";	
		hse_model_desc= "";	
		phase_code	= "";	
		phase_no	= "";
		
	}
	

	//table operations	
	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	private void displayRowDetails(){

		Integer row = tblCommSchedule.getSelectedRow();
		
		String unit_desc 		= "";	

		try { pbl_id 		= tblCommSchedule.getValueAt(row,2).toString().trim();} catch (NullPointerException e) { pbl_id 	= ""; }
		try { seq_no 		= Integer.parseInt(tblCommSchedule.getValueAt(row,3).toString().trim());} catch (NullPointerException e) { seq_no 	= 0; }
		try { unit_desc 	= tblCommSchedule.getValueAt(row,4).toString().trim();} catch (NullPointerException e) { unit_desc 	= ""; }
		try { entity_id 	= tblCommSchedule.getValueAt(row,0).toString().trim();} catch (NullPointerException e) { entity_id 	= ""; }
		try { entity_name 	= tblCommSchedule.getValueAt(row,1).toString().trim();} catch (NullPointerException e) { entity_name 	= ""; }
		try { agent_id 		= tblCommSchedule.getValueAt(row,7).toString().trim();} catch (NullPointerException e) { agent_id 	= ""; }
		try { agent_name 	= tblCommSchedule.getValueAt(row,8).toString().trim();} catch (NullPointerException e) { agent_name 	= ""; }
		try { pmt_scheme_id	= tblCommSchedule.getValueAt(row,9).toString().trim();} catch (NullPointerException e) { pmt_scheme_id 	= ""; }
		try { pmt_scheme_desc = tblCommSchedule.getValueAt(row,10).toString().trim();} catch (NullPointerException e) { pmt_scheme_desc 	= ""; }
		try { proj_id 		= tblCommSchedule.getValueAt(row,11).toString().trim();} catch (NullPointerException e) { proj_id 	= ""; }
		try { proj_desc 	= tblCommSchedule.getValueAt(row,12).toString().trim();} catch (NullPointerException e) { proj_desc 	= ""; }
		try { phase_code 	= tblCommSchedule.getValueAt(row,13).toString().trim();} catch (NullPointerException e) { phase_code 	= ""; }
		try { phase_no 		= tblCommSchedule.getValueAt(row,14).toString().trim();} catch (NullPointerException e) { phase_no 	= ""; }
		try { hse_model_id 	= tblCommSchedule.getValueAt(row,15).toString().trim();} catch (NullPointerException e) { hse_model_id 	= ""; }
		try { hse_model_desc 	= tblCommSchedule.getValueAt(row,16).toString().trim();} catch (NullPointerException e) { hse_model_id 	= ""; }
		
		try { sellingprice 	= Double.parseDouble(tblCommSchedule.getValueAt(row,6).toString().trim());} catch (NullPointerException e) { sellingprice = 0.00; }	
		try { datersvd 	= tblCommSchedule.getValueAt(row,5).toString().trim();} catch (NullPointerException e) { datersvd 	= ""; }
		
		txtUnit_ID.setText(pbl_id+" | "+seq_no);
		txtUnit.setText(unit_desc);
		txtClient_ID.setText(entity_id);
		txtClient.setText(entity_name);
		txtAgent_ID.setText(agent_id);
		txtAgent.setText(agent_name);
		txtScheme_ID.setText(pmt_scheme_id);
		txtScheme.setText(pmt_scheme_desc);
		txtProject_ID.setText(proj_id);
		txtProject.setText(proj_desc);
		txtPhase_ID.setText(phase_code);
		txtPhase.setText(phase_no);
		txtModel_ID.setText(hse_model_id);
		txtModel.setText(hse_model_desc);

	}

	private void clickTable(){		
		displayRowDetails();
		enableFields(true);
		enableButtons(true, true);
	}
		

	
	//processes and validation
	public static Boolean pbl_schedule_exist(String pbl_id, Integer seq_no) {

		Boolean exist = false;

		String SQL = 
			"select rec_id from cm_schedule_hd where trim(pbl_id) = '"+pbl_id.trim()+"' and seq_no = "+seq_no+" \n" ;

		System.out.printf("pbl_schedule_exist :" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			exist = true;
		}else{
			exist = false;
		}

		return exist;
	}
	
	public static Boolean comm_Create_sched(String agentID, String pmtSchemeID, String entityID, String projID,
			String pblID, Integer seqNo, Double sellingprice, String coID, String datersvd, String modelID, String phCode) {
		
		Boolean success = false;
		System.out.printf("Agent ID : " + agentID);
		
		String SQL = 
			"select comm_create_schedule (" +
			"	'"+agentID+"' , '"+pmtSchemeID+"' , '"+entityID+"' , '"+projID+"' , '"+pblID+"' ," +
			"	"+seqNo+", "+sellingprice+", '"+coID+"', '"+datersvd+"', '"+modelID+"', '"+phCode+"', 1 ) " ;

		System.out.printf("comm_Create_sched :" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			success = true;
		}else{
			success = false;
		}

		return success;
	}

	




}
