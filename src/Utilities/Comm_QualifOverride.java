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

public class Comm_QualifOverride extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Commission Qualification Override";
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
	private JPanel pnlUnit_Project_a;
	private JPanel pnlUnit_Project_b;
	private JPanel pnlUnit_Project_b1;
	private JPanel pnlUnit_Project_b2;
	private JPanel pnlClient_center;
	private JPanel pnlClient_center_a;
	private JPanel pnlClient_center_b;
	private JPanel pnlClient_center_b1;
	private JPanel pnlClient_center_b2;
	private JPanel pnlUnit_Project;
	private JPanel pnlRemarks;
	private JPanel pnlRemarks_a;
	private JPanel pnlRemarks_b;
	private JPanel pnlRemarks_b1;
	private JPanel pnlClient_status;

	private modelComm_client_comm_override modelComm_CommSched;
	private modelComm_client_comm_override modelComm_CommSched_total;

	private _JLookup lookupCompany;
	private _JLookup lookupClient;

	private JButton btnCancel;
	private JButton btnSave;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblCompany;
	private JLabel lblClient;
	private JLabel lblUnit_proj;
	private JLabel lblRemarks;
	
	private _JTagLabel tagCompany;
	private _JTagLabel tagClientName;
	private _JTagLabel tagProject;	
	private _JTagLabel tagStatus;	
	
	private _JScrollPaneMain scrollComm_CommSched;
	private _JScrollPaneTotal scrollComm_CommSchedtotal;
	private _JTableMain tblComm_CommSched;
	private JList rowHeaderComm_CommSched;
	private _JTableTotal tblComm_CommSched_total;
	private JXTextField txtUnitDesc;
	private JXTextField txtRemarks;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	static String co_id 	= "";		
	String company 	= "";
	String pbl_id 	= "";
	String seq_no 	= "";
	String status	= "";
	String company_logo;	

	public Comm_QualifOverride() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Comm_QualifOverride(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Comm_QualifOverride(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
			pnlComp.setPreferredSize(new java.awt.Dimension(928, 107));	
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

									lblClient.setEnabled(true);	
									lookupClient.setEnabled(true);	
									tagClientName.setEnabled(true);	

									lookupClient.setLookupSQL(getEntityList());
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
						lblClient = new JLabel("Client ID | Name", JLabel.TRAILING);
						pnlClient_center_a.add(lblClient);
						lblClient.setEnabled(false);	
						lblClient.setPreferredSize(new java.awt.Dimension(100, 40));
						lblClient.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
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
							lookupClient = new _JLookup(null, "Client ID", 2, 2);
							pnlClient_center_b1.add(lookupClient);
							lookupClient.setBounds(20, 27, 20, 25);
							lookupClient.setReturnColumn(0);
							lookupClient.setEnabled(false);	
							lookupClient.setPreferredSize(new java.awt.Dimension(135, 24));
							lookupClient.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){	

										String name = (String) data[1];
										String proj = (String) data[2];
										String unit = (String) data[3];
										pbl_id 		= (String) data[8];
										seq_no 		= data[9].toString();
										status		= (String) data[10];

										tagClientName.setTag(name);
										tagProject.setTag(proj);
										txtUnitDesc.setText(unit);
										tagStatus.setText("Status : [ "+status+" ]");

										lblUnit_proj.setEnabled(true);	
										txtUnitDesc.setEnabled(true);	
										tagProject.setEnabled(true);	
										lblRemarks.setEnabled(true);	
										txtRemarks.setEnabled(true);	
										txtRemarks.setEditable(true);
										tagStatus.setEnabled(true);	

										btnSave.setEnabled(true);
										btnCancel.setEnabled(true);

										displayClient_All_agentsCommission(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, pbl_id, seq_no );
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

							tagClientName = new _JTagLabel("[ ]");
							pnlClient_center_b2.add(tagClientName);
							tagClientName.setBounds(209, 27, 700, 22);
							tagClientName.setEnabled(false);	
							tagClientName.setPreferredSize(new java.awt.Dimension(27, 33));
						}
					}		
					{
						pnlClient_status = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlClient_center_b.add(pnlClient_status, BorderLayout.EAST);	
						pnlClient_status.setPreferredSize(new java.awt.Dimension(357, 25));	
						pnlClient_status.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						{
							tagStatus= new _JTagLabel("Status : [ ]");
							pnlClient_status.add(tagStatus);
							tagStatus.setBounds(209, 27, 700, 22);
							tagStatus.setEnabled(false);	
							tagStatus.setPreferredSize(new java.awt.Dimension(27, 33));
						}
					}		
				}
			}
			{
				pnlUnit_Project = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlUnit_Project, BorderLayout.SOUTH);	
				pnlUnit_Project.setPreferredSize(new java.awt.Dimension(926, 34));					

				{
					pnlUnit_Project_a = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlUnit_Project.add(pnlUnit_Project_a, BorderLayout.WEST);	
					pnlUnit_Project_a.setPreferredSize(new java.awt.Dimension(95, 40));	
					pnlUnit_Project_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblUnit_proj = new JLabel("Unit | Project ", JLabel.TRAILING);
						pnlUnit_Project_a.add(lblUnit_proj);
						lblUnit_proj.setEnabled(false);	
						lblUnit_proj.setPreferredSize(new java.awt.Dimension(100, 40));
						lblUnit_proj.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}						

					pnlUnit_Project_b = new JPanel(new BorderLayout(5, 5));
					pnlUnit_Project.add(pnlUnit_Project_b, BorderLayout.CENTER);	
					pnlUnit_Project_b.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlUnit_Project_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlUnit_Project_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlUnit_Project_b.add(pnlUnit_Project_b1, BorderLayout.WEST);	
						pnlUnit_Project_b1.setPreferredSize(new java.awt.Dimension(137, 29));					

						{
							txtUnitDesc = new JXTextField("");
							pnlUnit_Project_b1.add(txtUnitDesc);
							txtUnitDesc.setEnabled(false);	
							txtUnitDesc.setBounds(120, 25, 300, 22);	
							txtUnitDesc.setHorizontalAlignment(JTextField.CENTER);
						}	
					}
					{
						pnlUnit_Project_b2 = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlUnit_Project_b.add(pnlUnit_Project_b2, BorderLayout.CENTER);	
						pnlUnit_Project_b2.setPreferredSize(new java.awt.Dimension(357, 25));	
						pnlUnit_Project_b2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

						{
							tagProject = new _JTagLabel("[ ]");
							pnlUnit_Project_b2.add(tagProject);
							tagProject.setBounds(209, 27, 700, 22);
							tagProject.setEnabled(false);	
							tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
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
					pnlRemarks_a.setPreferredSize(new java.awt.Dimension(135, 40));	
					pnlRemarks_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblRemarks = new JLabel("Reason for Qualifying ", JLabel.TRAILING);
						pnlRemarks_a.add(lblRemarks);
						lblRemarks.setEnabled(false);	
						lblRemarks.setPreferredSize(new java.awt.Dimension(107, 40));
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
				pnlCommDetails.setBorder(JTBorderFactory.createTitleBorder("Active / For Processing Commission Schedule"));

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
									checkandValidate();				

								}
								public void mouseReleased(MouseEvent e) {
									if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblComm_CommSched.setCellSelectionEnabled(true);}
									checkandValidate();
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
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
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
	public void displayClient_All_agentsCommission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String pbl_id, String seq_no ) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select\r\n" + 
			"\r\n" + 
			//"a.agent_code,\r\n" + 
			"false," +
			"trim(e.entity_name) as agent_name,\r\n" + 			
			"d.rate,\r\n" + 
			"f.position_abbv,\r\n" + 
			"( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" + 
			"	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" + 
			"	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" + 
			"	case when trim(a.comm_type) = 'FC' then 'Full Comm' else '' end end end end ) as comm_type,\r\n" + 
			"a.prev_commamt,\r\n" + 
			"a.comm_amt as sked_amt,\r\n" + 
			"0.00 as adjustment,\r\n" + 
			"a.comm_amt as gross_amt,\r\n" + 
			"a.comm_amt*.10 as wtax_amt,\r\n" + 
			"adv_amount,\r\n" + 
			"(case when a.actual_amt = 0 then a.comm_amt*.9 else a.actual_amt end ) as actual_amt ,\r\n" + 
			"( case when status = 'A' then 'Not Yet Qualified' else\r\n" + 
			"	case when status = 'P' then 'Released' else \r\n" + 
			"	case when status = 'R' then 'Released' end end end ) as status,\r\n" + 
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
			"left join rf_pv_header c on b.rplf_no = c.pv_no\r\n" + 
			"left join cm_schedule_hd d on a.pbl_id = d.pbl_id and a.seq_no=d.seq_no and a.agent_code=d.agent_code\r\n" + 
			"left join rf_entity e on d.agent_code = e.entity_id\r\n" + 
			"left join mf_sales_position f on d.orig_position  = f.position_code\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"where tran_type != 'BB'\r\n" + 
			"and a.pbl_id = '"+pbl_id+"' \r\n" + 
			"and a.seq_no = "+seq_no+" \n" +
			"and a.qualified is null   \n" +
			"and a.status != 'I' \r\n" +
			"and d.status_id = 'A' \n" + 
			"\r\n" + 
			"order by  d.rate, e.entity_name, a.comm_type\r\n" + 
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
			
			JOptionPane.showMessageDialog(getContentPane(),"Selected unit does not \n" +
					"have commission schedule yet.","Warning",JOptionPane.WARNING_MESSAGE);
			btnSave.setEnabled(false);
			
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
		tblComm_CommSched.getColumnModel().getColumn(11).setPreferredWidth(60);

		adjustRowHeight_account(tblComm_CommSched);
	}	

	
	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){cancel();}

		if(e.getActionCommand().equals("Save")&&FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true){save();}
		else if(e.getActionCommand().equals("Save")&&FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to override commission schedule.","Warning",JOptionPane.WARNING_MESSAGE); }


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
		lblUnit_proj.setEnabled(false);	
		txtUnitDesc.setEnabled(false);	
		tagProject.setEnabled(false);	
		lblRemarks.setEnabled(false);	
		txtRemarks.setEnabled(false);	
		tagStatus.setEnabled(false);	

		lookupClient.setValue("");
		txtUnitDesc.setText("");
		tagProject.setText("[ ]");
		txtRemarks.setText("");
		tagClientName.setText("[ ]");
		tagStatus.setText("Status : [ ]");

		btnSave.setEnabled(false);
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

	private void save(){

		if (!status.equals("ACTIVE")) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Account is already canceled. Proceed anyway?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				if (nothingtoProcess()==false) {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						pgUpdate db = new pgUpdate();				
						saveOverride(db);
						db.commit();

						JOptionPane.showMessageDialog(getContentPane(),"Commission schedule(s) selected overrode.","Information",
								JOptionPane.INFORMATION_MESSAGE);
						displayClient_All_agentsCommission(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, pbl_id, seq_no );
						//execute_cancel();
					}

					else {}			
				}

				else if (nothingtoProcess()==true) {						
					JOptionPane.showMessageDialog(getContentPane(),"Please select commission to process.","Information",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		else {

			if (nothingtoProcess()==false) {

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					pgUpdate db = new pgUpdate();				
					saveOverride(db);
					db.commit();

					JOptionPane.showMessageDialog(getContentPane(),"Commission schedule(s) selected overrode.","Information",JOptionPane.INFORMATION_MESSAGE);
					execute_cancel();
				}

				else {}			
			}

			else if (nothingtoProcess()==true) {						
				JOptionPane.showMessageDialog(getContentPane(),"Please select commission to process.","Information",JOptionPane.INFORMATION_MESSAGE);				
			}
		}


	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		lblClient.setEnabled(true);	
		lookupClient.setEnabled(true);	
		tagClientName.setEnabled(true);	

		lookupClient.setLookupSQL(getEntityList());
		
		lookupCompany.setValue(co_id);
	}
	
	
	//select, lookup and get statements	
	public static String getEntityList(){

		String sql = "select \r\n" + 
		"\r\n" + 
		"a.account_code as \"Acct. Code\" ,\r\n" + 
		"trim(c.entity_name) as \"Client Name\",\r\n" + 
		"d.proj_name as \"Project\" ,\r\n" + 
		"e.description as \"Unit\",\r\n" + 
		"h.type_alias_too as \"Type\" ,\r\n" + 
		"f.sellingprice as \"NSP\",\r\n" + 
		"f.datersvd  as \"Date Reserved\" ,\r\n" + 
		"i.model_desc as \"House Model\", " +
		"a.pbl_id  as \"PBL\", " +
		"a.seq_no as \"Seq. No.\"," +
		"j.status_desc as \"Status\" \r\n" + 
		"\r\n" + 
		"from ( select distinct on ( account_code, pbl_id, seq_no) account_code, proj_id, pbl_id, seq_no, agent_code from cm_schedule_hd where status_id = 'A'  ) a\r\n" + 
		"left join rf_entity c on a.account_code = c.entity_id\r\n" + 
		"left join mf_project d on a.proj_id = d.proj_id\r\n" + 
		"left join mf_unit_info e on a.pbl_id = e.pbl_id\r\n" + 
		"left join (select * from rf_sold_unit) f on a.pbl_id = f.pbl_id and a.seq_no = f.seq_no\r\n" + 
		"left join ( select pmt_scheme_id, unnest(type_id) as type_id from mf_payment_scheme ) g on f.pmt_scheme_id = g.pmt_scheme_id\r\n" + 
		"left join mf_buyer_type h on g.type_id = h.type_id \r\n" + 
		"left join mf_product_model i on f.model_id = i.model_id " +
		"left join mf_record_status j on  f.status_id = j.status_id " +
		"where a.proj_id in (select proj_id from mf_project where co_id = '"+co_id+"')  \n" 
		;		
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
	private void checkandValidate(){

		if(checkIfReleased()==true)
		{
			JOptionPane.showMessageDialog(getContentPane(),"Commission selected is already released or processed.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private boolean checkIfReleased() {

		boolean checkIfReleased = false;		

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}

			if(isTrue){		
				String status = tblComm_CommSched.getValueAt(x,12).toString();				
				if (status.equals("Released")||status.equals("Processed")) {
					tblComm_CommSched.setValueAt(false, x, 0);
					checkIfReleased=true;
					break;
				} else {}		
			}		
			else {}
		}
		return checkIfReleased;

	}

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


	//save
	private void saveOverride(pgUpdate db){

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
				String agent_code 	= 	modelComm_CommSched.getValueAt(x,19).toString().trim();

				if (comm_type_1.equals("1st CA")) 			{comm_type_2  = "1"; }
				else if (comm_type_1.equals("2nd CA")) 		{comm_type_2  = "2";}
				else if (comm_type_1.equals("3rd CA")) 		{comm_type_2  = "3";}
				else if (comm_type_1.equals("4th CA")) 		{comm_type_2  = "4";}
				else if (comm_type_1.equals("Full Comm")) 	{comm_type_2  = "FC";}	

				String sqlDetail = 
					"update cm_schedule_dl set " +					
					"qualified = true, " +
					"date_qualified =now(),  \n" +	
					"override_date = now(),  \n" +	
					"override_code = '"+UserInfo.EmployeeCode+"',  \n" +	
					"remarks = '"+remarks+"'  \n" +
					"where pbl_id = '"+pbl_id+"' \n" +
					"and seq_no = "+seq_no+"  \n" +
					"and comm_type = '"+comm_type_2+"' \n" +
					"and agent_code = '"+agent_code+"' \n\n" ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);						
			}		
			else {}
		}

	}






}
