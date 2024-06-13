package Utilities;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelComm_schedRestructure;
import Accounting.Commissions.CreateCommissionSchedule;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
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

public class Comm_RestructureCommSchedule extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Re-structure Commission/Promo-Incentive Schedule";
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
	private JPanel pnlComm;
	private JPanel pnlComm_b;
	private JPanel pnlComm_b1;
	private JPanel pnlClient_status;
	private JPanel pnlClient_NSP;
	private JPanel pnlComm_c;
	private JPanel pnlAgentDtl_1;
	private JPanel pnlAgentDtl_1b;
	private JPanel pnlCommAmt;
	private JPanel pnlCommAmt_1;
	private JPanel pnlComm_x;
	private JPanel pnlComm_y;
	private JPanel pnlAgentName;
	private JPanel pnlAgentLbl;
	private JPanel pnlAgentTextFld;
	private JPanel pnlAgentTextFld_1;
	private JPanel pnlAgentTextFld_2;	
	private JPanel pnlAgentPosition;
	private JPanel pnlAgentDtl_1b_sub1;
	private JPanel pnlAgentDtl_1b_sub2;
	private JPanel pnlAgentDtl_1b_sub1a;
	private JPanel pnlAgentDtl_1b_sub1b;
	private JPanel pnlCommAmt_2;
	private JPanel pnlCommAmt_2a;
	private JPanel pnlCommAmt_2b;
	private JPanel pnlCommAmt_2b_1;
	private JPanel pnlCommAmt_2b_1a;
	private JPanel pnlCommAmt_2b_1b;

	private modelComm_schedRestructure modelComm_CommSched;
	private modelComm_schedRestructure modelComm_CommSched_total;

	private _JLookup lookupCompany;
	private _JLookup lookupClient;
	private _JLookup lookupAgent;

	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnAddNew;
	private JButton btnEdit;
	private JButton btnAddNewAgent;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblCompany;
	private JLabel lblClient;
	private JLabel lblUnit_proj;
	private JLabel lblRemarks;
	private JLabel lblAgentName;
	private JLabel lbPosition;
	private JLabel lblActualRate;
	private JLabel lblCommType;
	private JLabel lblStatus;
	private JLabel lblPrevComm;
	private JLabel lblAdjustment;
	private JLabel lblCommAmt;
	private JLabel lblCPFno;
	private JLabel lblOutofBal;

	private _JTagLabel tagCompany;
	private _JTagLabel tagClientName;
	private _JTagLabel tagProject;	
	private _JTagLabel tagStatus;	
	private _JTagLabel tagNSP;	
	private _JTagLabel tagAgentName;	

	private _JScrollPaneMain scrollComm_CommSched;
	private _JScrollPaneTotal scrollComm_CommSchedtotal;
	private _JTableMain tblComm_CommSched;
	private JList rowHeaderComm_CommSched;
	private _JTableTotal tblComm_CommSched_total;

	private JXTextField txtUnitDesc;
	private JXTextField txtRemarks;	
	private JXTextField txtPosition;	
	private JXTextField txtActualRate;
	private JXTextField txtCPFno;

	private JComboBox cmbCommType;
	private JComboBox cmbStatus;

	private _JXFormattedTextField txtPrevComm;
	private _JXFormattedTextField txtAdjustment;
	private _JXFormattedTextField txtCommAmt;
	private _JXFormattedTextField txtOutofbal;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	

	static String co_id = "";		
	String company 	= "";
	String pbl_id 	= "";
	String seq_no 	= "";
	String status	= "";
	String company_logo;		
	String proj_id 	= "";
	String agent_id	= "";
	String entity_id= "";
	String agent_nm	= "";
	String phase 	= "";
	Double nsp		= 0.00;
	String agent_rate = "";
	String scheme_id= "";
	String posn_id	= "";
	String datersvd = "";
	String to_do 	= "";  //to distinguish whether to add new agent or new sched.

	String hse_model = "";
	String comm_status = "";
	String SalesType = ""; //S-socialized : L-low-cost
	static String AgentType = ""; //001-Internal : 002-External

	Integer row_selected = null;		
	Double rate = 0.00;

	public Comm_RestructureCommSchedule() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Comm_RestructureCommSchedule(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Comm_RestructureCommSchedule(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(917, 600));
		this.setBounds(0, 0, 917, 600);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(938, 560));

		{ //start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(928, 108));	
			pnlComp.setBorder(lineBorder);

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(926, 38));	
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

									lookupClient.setLookupSQL(getEntityList2());
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
					pnlClient_center_a.setPreferredSize(new java.awt.Dimension(96, 35));	
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
					pnlClient_center_b.setPreferredSize(new java.awt.Dimension(802, 35));	
					pnlClient_center_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlClient_center_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlClient_center_b.add(pnlClient_center_b1, BorderLayout.WEST);	
						pnlClient_center_b1.setPreferredSize(new java.awt.Dimension(102, 27));					

						{
							lookupClient = new _JLookup(null, "Client ID", 2, 2);
							pnlClient_center_b1.add(lookupClient);
							lookupClient.setBounds(20, 27, 20, 25);
							lookupClient.setReturnColumn(0);
							lookupClient.setFilterName(true);
							lookupClient.setEnabled(false);	
							lookupClient.setPreferredSize(new java.awt.Dimension(101, 27));
							lookupClient.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){	

										String name = (String) data[1];
										String proj = (String) data[2];
										String unit = (String) data[3];
										pbl_id 		= ((String) data[8]).trim();
										seq_no 		= data[9].toString();
										status		= (String) data[10];
										nsp			= Double.parseDouble(data[5].toString());
										proj_id 	= (String) data[11];
										phase 		= (String) data[12];
										entity_id	= (String) data[0];
										scheme_id	= (String) data[13];
										datersvd 	= data[6].toString();
										hse_model   = (String) data[14];	

										tagClientName.setTag(name);
										tagProject.setTag(proj);
										txtUnitDesc.setText(unit);
										tagStatus.setText("Status : [ "+status+" ]");
										tagNSP.setText("NSP : [ "+nf.format(nsp)+" ]");

										lblUnit_proj.setEnabled(true);	
										txtUnitDesc.setEnabled(true);	
										tagProject.setEnabled(true);
										tagStatus.setEnabled(true);	
										tagNSP.setEnabled(true);

										displayClient_All_agentsCommission(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, pbl_id, seq_no );

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
							tagStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						}
					}		
				}
			}
			{
				pnlUnit_Project = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlUnit_Project, BorderLayout.SOUTH);	
				pnlUnit_Project.setPreferredSize(new java.awt.Dimension(926, 33));					

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

						pnlClient_NSP = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlUnit_Project_b.add(pnlClient_NSP, BorderLayout.EAST);	
						pnlClient_NSP.setPreferredSize(new java.awt.Dimension(357, 25));	
						pnlClient_NSP.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

						{
							tagNSP= new _JTagLabel("NSP : [ ]");
							pnlClient_NSP.add(tagNSP);
							tagNSP.setBounds(209, 27, 700, 22);
							tagNSP.setEnabled(false);	
							tagNSP.setPreferredSize(new java.awt.Dimension(27, 33));
							tagNSP.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
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

				pnlCommDetails = new JPanel();
				pnlSubTable.add(pnlCommDetails, BorderLayout.CENTER);
				pnlCommDetails.setLayout(new BorderLayout(0,0));
				pnlCommDetails.setBorder(lineBorder);		
				pnlCommDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlCommDetails.setBorder(JTBorderFactory.createTitleBorder("Commission / Promo-Incentive Schedule"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollComm_CommSched = new _JScrollPaneMain();
						pnlCommDetails.add(scrollComm_CommSched, BorderLayout.CENTER);
						{
							modelComm_CommSched = new modelComm_schedRestructure();

							tblComm_CommSched = new _JTableMain(modelComm_CommSched);
							scrollComm_CommSched.setViewportView(tblComm_CommSched);
							tblComm_CommSched.addMouseListener(this);								
							tblComm_CommSched.setSortable(false);
							tblComm_CommSched.getColumnModel().getColumn(0).setPreferredWidth(300);
							tblComm_CommSched.getColumnModel().getColumn(1).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(2).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(3).setPreferredWidth(150);
							tblComm_CommSched.getColumnModel().getColumn(4).setPreferredWidth(80);
							tblComm_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
							tblComm_CommSched.getColumnModel().getColumn(6).setPreferredWidth(120);
							tblComm_CommSched.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}	

							}); 
							tblComm_CommSched.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblComm_CommSched.setCellSelectionEnabled(true);}	
									btnAddNew.setEnabled(true);	
									btnEdit.setEnabled(true);	
									btnSave.setEnabled(false);
									enableAgentsfields(false);
									refreshAgentFields();
									setCommDetails();
									computeBalance();
									row_selected = tblComm_CommSched.getSelectedRow();			
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
							modelComm_CommSched_total = new modelComm_schedRestructure();
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

			{
				pnlComm = new JPanel(new BorderLayout(5, 5));
				pnlSubTable.add(pnlComm, BorderLayout.SOUTH);	
				pnlComm.setPreferredSize(new java.awt.Dimension(926, 180));	
				pnlComm.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
				pnlComm.setBorder(lineBorder);

				{
					pnlAgentName = new JPanel(new BorderLayout(5, 5));
					pnlComm.add(pnlAgentName, BorderLayout.NORTH);	
					pnlAgentName.setPreferredSize(new java.awt.Dimension(926, 32));	
					pnlAgentName.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						pnlAgentLbl = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlAgentName.add(pnlAgentLbl, BorderLayout.WEST);	
						pnlAgentLbl.setPreferredSize(new java.awt.Dimension(85, 32));	
						pnlAgentLbl.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblAgentName = new JLabel("Agent : ", JLabel.TRAILING);
							pnlAgentLbl.add(lblAgentName);
							lblAgentName.setEnabled(false);	
							lblAgentName.setPreferredSize(new java.awt.Dimension(107, 40));
							lblAgentName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}						

						pnlAgentTextFld = new JPanel(new BorderLayout(5, 5));
						pnlAgentName.add(pnlAgentTextFld, BorderLayout.CENTER);	
						pnlAgentTextFld.setPreferredSize(new java.awt.Dimension(814, 40));	
						pnlAgentTextFld.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

						{
							pnlAgentTextFld_1 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlAgentTextFld.add(pnlAgentTextFld_1, BorderLayout.CENTER);	
							pnlAgentTextFld_1.setPreferredSize(new java.awt.Dimension(118, 24));					

							{
								lookupAgent = new _JLookup(null, "Agent",0,2);
								pnlAgentTextFld_1.add(lookupAgent);
								lookupAgent.setLookupSQL(SQL_COMPANY());
								lookupAgent.setReturnColumn(0);
								lookupAgent.setFilterName(true);
								lookupAgent.setEnabled(false);
								lookupAgent.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											agent_id	= (String) data[0];	
											String agent_name	= (String) data[1];												

											if(checkIfAgentExist(agent_name)==true) {JOptionPane.showMessageDialog(getContentPane(),
													"Agent already exists.","Error",JOptionPane.ERROR_MESSAGE);
											lookupAgent.setValue("");}

											else{	

												if (isRowHouse(hse_model)==true){SalesType = "comm_rate_socialized";} //comm_rate_socialized - refers to the column in mf_sales_position
												else {SalesType = "comm_rate_lowcost";}  //comm_rate_lowcost - refers to the column in mf_sales_position

												String posn	= (String) data[3];		
												posn_id		= (String) data[6];		
												txtPosition.setText(posn);
												tagAgentName.setTag(agent_name);
												agent_rate	= getAgentRate(agent_id, SalesType);

												//no longer applicable
												/*if(getAgentRate(agent_id)==null) {JOptionPane.showMessageDialog(getContentPane(),
														"Agent rate does not exist yet. \n" +
														"Please add rate in the Sales Agent module.","Error",JOptionPane.ERROR_MESSAGE);
													lookupAgent.setValue("");}

												else {		

												}*/
												txtActualRate.setText(agent_rate);
											}
										}
									}
								});
							}																
						}
						{
							pnlAgentTextFld_2 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlAgentTextFld.add(pnlAgentTextFld_2, BorderLayout.EAST);	
							pnlAgentTextFld_2.setPreferredSize(new java.awt.Dimension(684, 24));					

							{
								tagAgentName = new _JTagLabel("[ ]");
								pnlAgentTextFld_2.add(tagAgentName);
								tagAgentName.setBounds(209, 27, 700, 22);
								tagAgentName.setEnabled(false);	
								tagAgentName.setPreferredSize(new java.awt.Dimension(27, 33));
							}											
						}
					}

				}
				{
					pnlComm_b = new JPanel(new BorderLayout(5, 5));
					pnlComm.add(pnlComm_b, BorderLayout.CENTER);	
					pnlComm_b.setPreferredSize(new java.awt.Dimension(926, 114));	
					pnlComm_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));					

					{
						pnlAgentDtl_1 = new JPanel(new BorderLayout(0, 5));
						pnlComm_b.add(pnlAgentDtl_1, BorderLayout.WEST);	
						pnlAgentDtl_1.setPreferredSize(new java.awt.Dimension(278, 113));
						pnlAgentDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						{
							pnlAgentPosition = new JPanel(new GridLayout(4, 1, 0, 5));
							pnlAgentDtl_1.add(pnlAgentPosition, BorderLayout.WEST);	
							pnlAgentPosition.setPreferredSize(new java.awt.Dimension(91, 107));
							pnlAgentPosition.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

							{
								{
									lbPosition = new JLabel("Position ", JLabel.TRAILING);
									pnlAgentPosition.add(lbPosition);
									lbPosition.setEnabled(false);	
									lbPosition.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblActualRate = new JLabel("Actual Rate ", JLabel.TRAILING);
									pnlAgentPosition.add(lblActualRate);
									lblActualRate.setEnabled(false);	
									lblActualRate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblCommType = new JLabel("Comm Type ", JLabel.TRAILING);
									pnlAgentPosition.add(lblCommType);
									lblCommType.setEnabled(false);	
									lblCommType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblStatus = new JLabel("Status ", JLabel.TRAILING);
									pnlAgentPosition.add(lblStatus);
									lblStatus.setEnabled(false);	
									lblStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
							}

							pnlAgentDtl_1b = new JPanel(new BorderLayout(0,0));
							pnlAgentDtl_1.add(pnlAgentDtl_1b, BorderLayout.CENTER);	
							pnlAgentDtl_1b.setPreferredSize(new java.awt.Dimension(127, 107));
							pnlAgentDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));

							{
								pnlAgentDtl_1b_sub1 = new JPanel(new GridLayout(1, 2, 5, 5));
								pnlAgentDtl_1b.add(pnlAgentDtl_1b_sub1, BorderLayout.NORTH);	
								pnlAgentDtl_1b_sub1.setPreferredSize(new java.awt.Dimension(111, 52));
								pnlAgentDtl_1b_sub1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

								{
									pnlAgentDtl_1b_sub1a = new JPanel(new GridLayout(2, 1, 5, 5));
									pnlAgentDtl_1b_sub1.add(pnlAgentDtl_1b_sub1a, BorderLayout.WEST);	
									pnlAgentDtl_1b_sub1a.setPreferredSize(new java.awt.Dimension(111, 52));
									pnlAgentDtl_1b_sub1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

									{
										txtPosition = new JXTextField("");
										pnlAgentDtl_1b_sub1a.add(txtPosition);
										txtPosition.setEnabled(false);	
										txtPosition.setEditable(false);
										txtPosition.setBounds(120, 25, 300, 22);	
										txtPosition.setHorizontalAlignment(JTextField.CENTER);
										txtPosition.setPreferredSize(new java.awt.Dimension(292, 24));

									}
									{
										txtActualRate = new JXTextField("");
										pnlAgentDtl_1b_sub1a.add(txtActualRate);
										txtActualRate.setEnabled(false);	
										txtActualRate.setEditable(false);
										txtActualRate.setBounds(120, 25, 300, 22);	
										txtActualRate.setHorizontalAlignment(JTextField.CENTER);
										txtActualRate.setPreferredSize(new java.awt.Dimension(292, 24));
									}
								}
								{
									pnlAgentDtl_1b_sub1b = new JPanel(new GridLayout(2, 1, 5, 5));
									pnlAgentDtl_1b_sub1.add(pnlAgentDtl_1b_sub1b, BorderLayout.CENTER);	
									pnlAgentDtl_1b_sub1b.setPreferredSize(new java.awt.Dimension(111, 52));
									pnlAgentDtl_1b_sub1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
								}
							}
							{
								pnlAgentDtl_1b_sub2 = new JPanel(new GridLayout(2, 1, 5, 5));
								pnlAgentDtl_1b.add(pnlAgentDtl_1b_sub2, BorderLayout.CENTER);	
								pnlAgentDtl_1b_sub2.setPreferredSize(new java.awt.Dimension(127, 107));
								pnlAgentDtl_1b_sub2.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

								{
									String status[] = {"NA","1 - 1st CA","2 - 2nd CA","3 - 3rd CA","4 - 4th CA","5 - 5th CA",
											"6 - 6th CA","7 - 7th CA","8 - 8th CA","9 - 9th CA", "FC - Full Comm"};					
									cmbCommType = new JComboBox(status);
									pnlAgentDtl_1b_sub2.add(cmbCommType);
									cmbCommType.setSelectedItem(null);
									cmbCommType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
									cmbCommType.setBounds(537, 15, 190, 21);	
									cmbCommType.setEnabled(false);
									cmbCommType.setEditable(false);
									cmbCommType.setPreferredSize(new java.awt.Dimension(217, 60));
									cmbCommType.setSelectedIndex(0);
									cmbCommType.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent arg0) {											
											checkCommType();
										}
									});
								}		
								{
									String status[] = {"A - Not Yet Qualified","F - For Request for Payment",
											"P - Voucher on Process","R - Commission Released"};					
									cmbStatus = new JComboBox(status);
									pnlAgentDtl_1b_sub2.add(cmbStatus);
									cmbStatus.setSelectedItem(null);
									cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
									cmbStatus.setBounds(537, 15, 190, 21);	
									cmbStatus.setEnabled(false);
									cmbStatus.setEditable(false);
									cmbStatus.setPreferredSize(new java.awt.Dimension(217, 60));
									cmbStatus.setSelectedIndex(0);	
								}
							}
						}
					}
					{
						//Start of Left Panel 
						pnlCommAmt = new JPanel(new BorderLayout(0,0));
						pnlComm_b.add(pnlCommAmt, BorderLayout.CENTER);
						pnlCommAmt.setPreferredSize(new java.awt.Dimension(675, 116));
						pnlCommAmt.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

						{
							pnlCommAmt_1 = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlCommAmt.add(pnlCommAmt_1, BorderLayout.WEST);
							pnlCommAmt_1.setPreferredSize(new java.awt.Dimension(97, 113));
							pnlCommAmt_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{
								{
									lblPrevComm = new JLabel("Prev. Comm.", JLabel.TRAILING);
									pnlCommAmt_1.add(lblPrevComm);
									lblPrevComm.setEnabled(false);	
									lblPrevComm.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblAdjustment = new JLabel("Adjustment", JLabel.TRAILING);
									pnlCommAmt_1.add(lblAdjustment);
									lblAdjustment.setEnabled(false);	
									lblAdjustment.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblCommAmt = new JLabel("Comm. Amount", JLabel.TRAILING);
									pnlCommAmt_1.add(lblCommAmt);
									lblCommAmt.setEnabled(false);
									lblCommAmt.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblCPFno = new JLabel("CPF No.", JLabel.TRAILING);
									pnlCommAmt_1.add(lblCPFno);
									lblCPFno.setEnabled(false);	
									lblCPFno.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
							}

							pnlCommAmt_2 = new JPanel(new BorderLayout(5,0));
							pnlCommAmt.add(pnlCommAmt_2, BorderLayout.CENTER);
							pnlCommAmt_2.setPreferredSize(new java.awt.Dimension(203, 118));
							pnlCommAmt_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

							{
								{
									pnlCommAmt_2a = new JPanel(new GridLayout(4, 1, 0, 5));
									pnlCommAmt_2.add(pnlCommAmt_2a, BorderLayout.WEST);
									pnlCommAmt_2a.setPreferredSize(new java.awt.Dimension(119, 119));
									pnlCommAmt_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{
										txtPrevComm = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlCommAmt_2a.add(txtPrevComm);
										txtPrevComm.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtPrevComm.setText("0.00");
										txtPrevComm.setEnabled(false);
										txtPrevComm.setBounds(120, 0, 72, 22);		
									}		
									{
										txtAdjustment = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlCommAmt_2a.add(txtAdjustment);
										txtAdjustment.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtAdjustment.setText("0.00");
										txtAdjustment.setEnabled(false);
										txtAdjustment.setBounds(120, 0, 72, 22);		
									}		
									{
										txtCommAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
										pnlCommAmt_2a.add(txtCommAmt);
										txtCommAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtCommAmt.setText("0.00");
										txtCommAmt.setEnabled(false);
										txtCommAmt.setBounds(120, 0, 72, 22);
										txtCommAmt.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent e) {		

												Double tot_comm = nsp*rate;
												Double old_comm = Double.valueOf(tblComm_CommSched.getValueAt(row_selected,5).toString().replace(",","")).doubleValue();
												Double new_comm = Double.valueOf(txtCommAmt.getText().trim().replace(",","")).doubleValue();
												Double old_comm_total  =  Double.valueOf(tblComm_CommSched_total.getValueAt(0,5).toString().replace(",","")).doubleValue();
												Double comm_diff = 0.00;

												comm_diff = (old_comm_total - old_comm + new_comm) - tot_comm;	

												txtOutofbal.setText(nf.format(comm_diff));	
											}				 
										});		
									}		
									{
										txtCPFno = new JXTextField("");
										pnlCommAmt_2a.add(txtCPFno);
										txtCPFno.setEnabled(false);	
										txtCPFno.setBounds(120, 25, 300, 22);	
										txtCPFno.setHorizontalAlignment(JTextField.LEFT);
										txtCPFno.setPreferredSize(new java.awt.Dimension(292, 24));
									}		
								}
								{
									pnlCommAmt_2b = new JPanel(new BorderLayout(5, 5));
									pnlCommAmt_2.add(pnlCommAmt_2b, BorderLayout.CENTER);
									pnlCommAmt_2b.setPreferredSize(new java.awt.Dimension(140, 118));
									pnlCommAmt_2b.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
									{

										pnlCommAmt_2b_1 = new JPanel(new BorderLayout(5, 5));
										pnlCommAmt_2b.add(pnlCommAmt_2b_1, BorderLayout.NORTH);
										pnlCommAmt_2b_1.setPreferredSize(new java.awt.Dimension(417, 27));	
										pnlCommAmt_2b_1.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

										pnlCommAmt_2b_1a = new JPanel(new BorderLayout(5, 5));
										pnlCommAmt_2b_1.add(pnlCommAmt_2b_1a, BorderLayout.WEST);	
										pnlCommAmt_2b_1a.setPreferredSize(new java.awt.Dimension(238, 27));	
										pnlCommAmt_2b_1a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

										{
											lblOutofBal = new JLabel(" Out of Balance Comm. Amount : ", JLabel.TRAILING);
											pnlCommAmt_2b_1a.add(lblOutofBal);
											lblOutofBal.setEnabled(false);	
											lblOutofBal.setPreferredSize(new java.awt.Dimension(242, 27));
											lblOutofBal.setFont(new java.awt.Font("Segoe UI",Font.BOLD|Font.ITALIC,12));
										}		

										pnlCommAmt_2b_1b = new JPanel(new BorderLayout(5, 5));
										pnlCommAmt_2b_1.add(pnlCommAmt_2b_1b, BorderLayout.CENTER);	
										pnlCommAmt_2b_1b.setPreferredSize(new java.awt.Dimension(417, 27));	
										pnlCommAmt_2b_1b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

										{	
											txtOutofbal = new _JXFormattedTextField(JXFormattedTextField.CENTER);
											pnlCommAmt_2b_1b.add(txtOutofbal);
											txtOutofbal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtOutofbal.setText("0.00");
											txtOutofbal.setEnabled(true);
											txtOutofbal.setEditable(false);
											txtOutofbal.setBounds(120, 0, 72, 22);	
										}										
									}
								}
							}
						}
					}
				}
				{
					pnlComm_c = new JPanel(new BorderLayout(5, 5));
					pnlComm.add(pnlComm_c, BorderLayout.SOUTH);	
					pnlComm_c.setPreferredSize(new java.awt.Dimension(926, 32));	
					pnlComm_c.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{

						pnlComm_x = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlComm_c.add(pnlComm_x, BorderLayout.WEST);	
						pnlComm_x.setPreferredSize(new java.awt.Dimension(86, 38));	
						pnlComm_x.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblRemarks = new JLabel("Remarks ", JLabel.TRAILING);
							pnlComm_x.add(lblRemarks);
							lblRemarks.setEnabled(false);	
							lblRemarks.setPreferredSize(new java.awt.Dimension(107, 40));
							lblRemarks.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));			
						}						

						pnlComm_y = new JPanel(new BorderLayout(5, 5));
						pnlComm_c.add(pnlComm_y, BorderLayout.CENTER);	
						pnlComm_y.setPreferredSize(new java.awt.Dimension(814, 40));	
						pnlComm_y.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

						{
							pnlComm_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlComm_y.add(pnlComm_b1, BorderLayout.WEST);	
							pnlComm_b1.setPreferredSize(new java.awt.Dimension(820, 30));					

							{
								txtRemarks = new JXTextField("");
								pnlComm_b1.add(txtRemarks);
								txtRemarks.setEnabled(false);	
								txtRemarks.setBounds(120, 25, 300, 22);	
								txtRemarks.setHorizontalAlignment(JTextField.LEFT);
							}											
						}
					}
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
					btnAddNewAgent = new JButton("Add New Agent");
					pnlSouthCenterb.add(btnAddNewAgent);
					btnAddNewAgent.setActionCommand("AddAgent");
					btnAddNewAgent.addActionListener(this);
					btnAddNewAgent.setEnabled(false);
				}
				{
					btnAddNew = new JButton("Add New Schedule");
					pnlSouthCenterb.add(btnAddNew);
					btnAddNew.setActionCommand("AddSched");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
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
			"trim(g.entity_name) as agent_name,\r\n" + 			
			"d.rate,\r\n" + 
			"f.position_abbv,\r\n" + 
			"( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" + 
			"	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" + 
			"	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" + 
			"	case when trim(a.comm_type) = 'FC' then 'Full Comm' else " +
			"	case when trim(a.tran_type) = 'BB' then 'Promo' else '' end end end end end ) as comm_type,\r\n" + 
			"a.prev_commamt,\r\n" + 
			"a.comm_amt as sked_amt,\r\n" + 
			"( case when h.status_id = 'I' then 'Canceled' else \n" +
			"	case when status = 'A' then 'Not Yet Qualified' else\r\n" + 
			"	case when status = 'P' then 'Processed' else \r\n" + 
			"	case when status = 'R' then 'Released' end end end end) as status,\r\n" + 
			"(case when h.status_id = 'I' then h.remarks else \n" +
			"	case when a.remarks is null then '' else a.remarks end end ) as remarks ," +
			"	a.rec_id,   \r\n" +
			"	f.salestype_code \n" + 
			"\r\n" + 
			"from cm_schedule_dl a\r\n" + 
			"left join cm_schedule_hd d on a.pbl_id = d.pbl_id and a.seq_no=d.seq_no and a.agent_code=d.agent_code\r\n" + 
			"left join rf_entity e on d.agent_code = e.entity_id\r\n" + 
			"left join rf_entity g on a.agent_code = g.entity_id\r\n" + 
			"left join mf_sales_position f on d.orig_position  = f.position_code\r\n" + 
			"left join cm_cdf_hd h on a.cdf_no = h.cdf_no \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"where a.pbl_id = '"+pbl_id+"' \r\n" + 
			"and a.seq_no = "+seq_no+"   " +
			"and d.status_id = 'A' " +
			"and a.status != 'I' \r\n" + 
			"\r\n" + 
			"order by d.rate, e.entity_name, a.comm_type\r\n" + 
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

			modelComm_CommSched.setEditable(true);
			tblComm_CommSched.setEditable(true);

			enableButtons(true, false, false, false, true);
			computeBalance();
		}		


		else {			

			JOptionPane.showMessageDialog(getContentPane(),"Selected unit does not \n" +
					"have commission schedule yet.","Warning",JOptionPane.WARNING_MESSAGE);
			enableButtons(false, false, false, false, true);

			modelComm_CommSched_total = new modelComm_schedRestructure();
			modelComm_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblComm_CommSched_total = new _JTableTotal(modelComm_CommSched_total, tblComm_CommSched);
			tblComm_CommSched_total.setFont(dialog11Bold);
			scrollComm_CommSchedtotal.setViewportView(tblComm_CommSched_total);
			((_JTableTotal) tblComm_CommSched_total).setTotalLabel(0);}

		tblComm_CommSched.packAll();				
		tblComm_CommSched.getColumnModel().getColumn(1).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(3).setPreferredWidth(150);
		tblComm_CommSched.getColumnModel().getColumn(4).setPreferredWidth(80);
		tblComm_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblComm_CommSched.getColumnModel().getColumn(6).setPreferredWidth(120);


		adjustRowHeight_account(tblComm_CommSched);
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){cancel();}
		
		if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true){

			if(e.getActionCommand().equals("AddAgent")){addAgent();  to_do = "addAgent"; }

			if(e.getActionCommand().equals("AddSched")){addSchedule(); to_do = "addSched";}

			if(e.getActionCommand().equals("Edit")){edit(); to_do = "editSched";}
			
		}		
		else if(FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to change commission schedule.","Warning",JOptionPane.WARNING_MESSAGE); }

		
		if(e.getActionCommand().equals("Save")){save();}
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

		if (btnSave.isEnabled()) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}

		}

		else {	execute_cancel(); 	}
	}

	private void execute_cancel(){

		enableCompFields(false);
		enableAgentsfields(false);	
		refreshCompFields();
		refreshAgentFields();
		enableButtons(false, false, false, false, false);

		lblClient.setEnabled(true);	
		lookupClient.setEnabled(true);
		tagClientName.setEnabled(true);	

		FncTables.clearTable(modelComm_CommSched);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderComm_CommSched.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		modelComm_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 4);
		modelComm_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 5);

	}

	private void save(){

		String CommType  = "";

		if (cmbCommType.getSelectedIndex()==1 ) { CommType = "1"; }
		else if  (cmbCommType.getSelectedIndex()==2 ) { CommType = "2"; }
		else if  (cmbCommType.getSelectedIndex()==3 ) { CommType = "3"; }	
		else if  (cmbCommType.getSelectedIndex()==4 ) { CommType = "4"; }
		else if  (cmbCommType.getSelectedIndex()==5 ) { CommType = "5"; }
		else if  (cmbCommType.getSelectedIndex()==6 ) { CommType = "6"; }
		else if  (cmbCommType.getSelectedIndex()==7 ) { CommType = "7"; }
		else if  (cmbCommType.getSelectedIndex()==8 ) { CommType = "8"; }
		else if  (cmbCommType.getSelectedIndex()==9 ) { CommType = "9"; }
		else if  (cmbCommType.getSelectedIndex()==10 ) { CommType = "FC"; }

		if (getSchemeDetails(CommType)==null) 
		{
			JOptionPane.showMessageDialog(getContentPane(),"Selected commission type does not exist for scheme " + scheme_id,"Warning",JOptionPane.WARNING_MESSAGE);
		}

		else 
		{

			
			if(checkifCommBalisZero()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "<html><b><i>Commission Balance<html></b></i>" + 
					" is not equal to zero.", "Commission Balance", 
					JOptionPane.WARNING_MESSAGE);}

			else {

				if(checkCompleteDetails()==false)
				{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete request details.", "Incomplete Detail", 
						JOptionPane.INFORMATION_MESSAGE);}

				else if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					pgUpdate db = new pgUpdate();	

					if (to_do.equals("addAgent")) {saveNewAgentSchedule_header(db); saveNewAgentSchedule_detail(db); db.commit();
					JOptionPane.showMessageDialog(getContentPane(),"A new agent and first commission schedule were added.","Information",JOptionPane.INFORMATION_MESSAGE);
					} 

					else if ((to_do.equals("addSched"))){saveNewAgentSchedule_detail(db); updateAgentSchedule_header(db); db.commit();
					JOptionPane.showMessageDialog(getContentPane(),"A new agent commission schedule was added.","Information",JOptionPane.INFORMATION_MESSAGE);
					}

					else if ((to_do.equals("editSched"))){updateAgentSchedule_detail(db); db.commit();
					JOptionPane.showMessageDialog(getContentPane(),"Agent commission schedule successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);
					}

					displayClient_All_agentsCommission(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, pbl_id, seq_no );
					enableButtons(true, false, false, false, true);
					computeBalance();
				}

				else {}	

			}
		}

	}

	private void edit(){

		int row = tblComm_CommSched.getSelectedRow();
		comm_status  = tblComm_CommSched.getValueAt(row,6).toString();	

		if (checkifReleased()==true) {
			JOptionPane.showMessageDialog(getContentPane(),"<html>Commission is <html>" + "<html><i><b>"+comm_status+"<html></i></b>" + 
					"<html>. Editing is not allowed.<html> ","Information",JOptionPane.WARNING_MESSAGE);
		}

		else {
			enableAgentsfields(true);
			enableButtons(false, false, false, true, true);
			txtCPFno.setEnabled(false);
			lookupAgent.setEnabled(false);
			cmbCommType.setEnabled(true);

		}
	}

	private void addSchedule(){

		enableAgentsfields(true);
		cmbCommType.setEnabled(true);
		cmbStatus.setEnabled(true);
		enableButtons(false, false, false, true, true);

		agent_id = lookupAgent.getText().trim();
		lookupAgent.setEnabled(false);

	}

	private void addAgent(){

		enableAgentsfields(true);
		cmbCommType.setEnabled(true);
		cmbStatus.setEnabled(true);
		enableButtons(false, false, false, true, true);

		lookupAgent.setValue("");
		tagAgentName.setTag("");
		txtPosition.setText("");
		txtActualRate.setText("");
		txtRemarks.setText("");
		txtCPFno.setEnabled(false);	

		lookupAgent.setLookupSQL(getAgentList());		
	}


	//enable, disable
	private void enableAgentsfields(boolean x){

		lblAgentName.setEnabled(x);	
		lookupAgent.setEnabled(x);	
		tagClientName.setEnabled(x);	
		lbPosition.setEnabled(x);	
		txtPosition.setEnabled(x);	
		lblActualRate.setEnabled(x);	
		txtActualRate.setEnabled(x);	
		lblCommType.setEnabled(x);	
		lblStatus.setEnabled(x);	
		lblRemarks.setEnabled(x);	
		txtRemarks.setEnabled(x);	
		lblPrevComm.setEnabled(x);	
		//txtPrevComm.setEnabled(x);
		lblAdjustment.setEnabled(x);	
		//txtAdjustment.setEnabled(x);
		lblCommAmt.setEnabled(x);
		txtCommAmt.setEnabled(x);
		lblCPFno.setEnabled(x);	
		txtCPFno.setEnabled(x);	
		lblOutofBal.setEnabled(x);	
		lblOutofBal.setEnabled(x);			
	}

	private void enableCompFields(boolean x){

		lblClient.setEnabled(x);	
		lookupClient.setEnabled(x);	
		tagClientName.setEnabled(x);	
		tagClientName.setEnabled(x);	
		lblUnit_proj.setEnabled(x);	
		txtUnitDesc.setEnabled(x);	
		txtUnitDesc.setEnabled(x);	
		tagStatus.setEnabled(x);	
		tagNSP.setEnabled(x);	


	}

	private void refreshCompFields(){

		lookupClient.setValue("");
		tagClientName.setText("[ ]");
		tagStatus.setText("Status : [ ]");
		tagNSP.setText("NSP : [ ]");
		txtUnitDesc.setText("");
		tagProject.setText("[ ]");

	}

	private void refreshAgentFields(){

		lookupAgent.setText("");
		tagAgentName.setText("[ ]");
		txtPosition.setText("");
		txtActualRate.setText("");
		cmbCommType.setSelectedIndex(0);	
		cmbStatus.setSelectedIndex(0);	
		txtRemarks.setText("");
		txtPrevComm.setText("0.00");
		txtAdjustment.setText("0.00");
		txtCommAmt.setText("0.00");
		txtCPFno.setText("");
		txtOutofbal.setText("0.00");		


	}

	public void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d, Boolean e){
		btnAddNewAgent.setEnabled(a);
		btnAddNew.setEnabled(b);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(d);	
		btnCancel.setEnabled(e);			
	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();					

		lblClient.setEnabled(true);	
		lookupClient.setEnabled(true);	
		tagClientName.setEnabled(true);	

		lookupClient.setLookupSQL(getEntityList2());

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
		"from ( select distinct on ( account_code, pbl_id, seq_no) account_code, proj_id, pbl_id, seq_no, agent_code from cm_schedule_hd where status_id = 'A' ) a\r\n" + 
		"left join rf_entity c on a.account_code = c.entity_id\r\n" + 
		"left join mf_project d on a.proj_id = d.proj_id\r\n" + 
		"left join mf_unit_info e on a.pbl_id = e.pbl_id\r\n" + 
		"left join (select * from rf_sold_unit where status_id != 'I') f on a.pbl_id = f.pbl_id and a.seq_no = f.seq_no\r\n" + 
		"left join ( select pmt_scheme_id, unnest(type_id) as type_id from mf_payment_scheme ) g on f.pmt_scheme_id = g.pmt_scheme_id\r\n" + 
		"left join mf_buyer_type h on g.type_id = h.type_id \r\n" + 
		"left join mf_product_model i on f.model_id = i.model_id " +
		"left join mf_record_status j on  f.status_id = j.status_id ";		
		return sql;

	}	

	public static String getEntityList2(){

		String sql = "select \r\n" + 
		"\r\n" + 
		"a.entity_id as \"Acct. Code\" ,\r\n" + 
		"trim(c.entity_name) as \"Client Name\",\r\n" + 
		"d.proj_name as \"Project\" ,\r\n" + 
		"e.description as \"Unit\",\r\n" + 
		"h.type_alias_too as \"Type\" ,\r\n" + 
		"get_unit_nsp(a.pbl_id, a.seq_no) as \"NSP\",\r\n" + 
		"a.datersvd as \"Date Reserved\" ,\r\n" + 
		"i.model_desc as \"House Model\", " +
		"a.pbl_id as \"PBL\", " +
		"a.seq_no as \"Seq. No.\"," +
		"j.status_desc as \"Status\", \r\n" +
		"trim(a.projcode) as \"Proj Code\"," +
		"trim(e.phase) as \"Phase\"," +
		"trim(scheme_code) as \"Scheme_code\"," +
		"trim(a.model_id) as \"Model ID\"  " + 
		"\r\n" + 
		"from ( select entity_id, pbl_id, seq_no, projcode, pmt_scheme_id, model_id, status_id, datersvd from rf_sold_unit where status_id != 'I' ) a  \r\n" +  //sellingprice, 
		"left join (select distinct on ( account_code, pbl_id, seq_no) account_code, proj_id, pbl_id, seq_no, agent_code, scheme_code from cm_schedule_hd where status_id = 'A' ) f" +
		"		 on a.pbl_id = f.pbl_id and a.seq_no = f.seq_no\r\n" + 
		"left join rf_entity c on a.entity_id = c.entity_id\r\n" + 		
		"left join mf_project d on a.projcode = d.proj_id\r\n" + 
		"left join mf_unit_info e on a.pbl_id = e.pbl_id\r\n" + 		
		"left join ( select pmt_scheme_id, unnest(type_id) as type_id from mf_payment_scheme ) g on a.pmt_scheme_id = g.pmt_scheme_id\r\n" + 
		"left join mf_buyer_type h on g.type_id = h.type_id \r\n" + 
		"left join mf_product_model i on a.model_id = i.model_id " +
		"left join mf_record_status j on  a.status_id = j.status_id " +
		"where a.projcode in (select proj_id from mf_project where co_id = '"+co_id+"')  \n" +
		"order by c.entity_name ";		
		return sql;

	}	

	public static String getAgentList(){

		return 

		"select \r\n" + 
		"\r\n" + 
		"trim(a.agent_id)  as \"Agent ID\"  ,\r\n" + 
		"trim(b.entity_name) as \"Name\" ,\r\n" + 
		"trim(c.entity_name) as \"Override\"  ,\r\n" + 
		"upper(trim(d.position_abbv))  as \"Position\" ,\r\n" + 
		"upper(trim(e.type_desc)) as \"Type\"  ,\r\n" + 
		"trim(f.dept_name)  as \"Dept/Group\"," +
		"trim(d.position_code) as \"Position Code\"  \r\n" + 
		"\r\n" + 
		"from mf_sellingagent_info a\r\n" + 
		"left join rf_entity b on a.agent_id = b.entity_id\r\n" + 
		"left join rf_entity c on a.override_id = c.entity_id\r\n" + 
		"left join mf_sales_position d on a.salespos_id  = d.position_code\r\n" + 
		"left join mf_sales_type e on a.salestype_id = e.type_code\r\n" + 
		"left join mf_department f on a.dept_id = f.dept_code\r\n" + 
		"\r\n" + 
		"where a.status_id = 'A'  \n" +
		"and d.salestype_code = '"+AgentType+"' " ;			

	}	

	public String getAgentDetails(String entity_name) {

		String name = "";

		String SQL = 
			"select entity_id from rf_entity where upper(entity_name) like '"+entity_name+"%' ";

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);		

		if(db.isNotNull()){
			name = (String) db.getResult()[0][0];
		}else{
			name = null;
		}

		return name;
	}

	public String getAgentRate(String agnt, String sales_type) {

		String agnt_rate = "";

		//old SQL
		/*String SQL = 
			"select rate from cm_rate_per_project_phase " +
			"where agent_code = '"+agnt+"' " +
			"and proj_code = '"+proj_id+"' " +
			"and phase = '"+phase+"' " +
			"limit 1";*/

		String SQL = "select "+sales_type+" \r\n" + 
		"from mf_sales_position \r\n" + 
		"where position_code = '"+posn_id+"' " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);		

		if(db.isNotNull()){
			if(db.getResult()[0][0].toString()==null||db.getResult()[0][0].equals("null")) {agnt_rate = "0.00";}
			else {agnt_rate = db.getResult()[0][0].toString(); }
		}else{
			agnt_rate = null;
		}

		return agnt_rate;
	}

	public static Boolean isRowHouse(String hse_model){	

		Boolean x = false;

		String SQL = 
			"select is_rowhouse from mf_product_model where model_id = '"+hse_model+"'  " ;

		System.out.printf("isRowHouse: " + SQL + "\n\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = (Boolean) db.getResult()[0][0];
		}else{
			x = false;
		}		

		System.out.printf("is Row House? : " + x + "\n");

		return x;
	}

	public String getBuyerType() {//used

		String strSQL = 			
			"select trim(buyertype) from rf_sold_unit where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+"  ";		

		System.out.printf("SQL - getBuyerType: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	public Object [] getSchemeDetails(String comm_type){	

		String SQL = 
			"select comm_type, comm_amt, daysnumfromor, comm_prcnt " +
			"from cm_scheme_dl " +
			"where scheme_id = '"+scheme_id+"'" +
			"and comm_type = '"+comm_type+"' " +
			"and status_id = 'A'  \n" +
			"order by comm_type   " ;

		System.out.printf("getSchemeDetails Rate: " + SQL );
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		
	}

	public Boolean commTypeAlreadyExist(String comm_type) {//used

		Boolean existing = false;

		String strSQL = 			
			"select rec_id from cm_schedule_dl where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and comm_type = '"+comm_type+"' " +
			"and agent_code = '"+lookupAgent.getText().trim()+"' and status != 'I' \r\n" ;		

		System.out.printf("SQL - commTypeAlreadyExist: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			existing = true;
		}else{
			existing = false;
		}

		return existing;
	}

	public Integer getCommFrequency() {//used

		String strSQL = 			
			"select max(coalesce(frequency::int,0)) from cm_schedule_dl where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+"  ";		

		System.out.printf("SQL - getCommFrequency: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (Integer) db.getResult()[0][0];
		}else{
			return 1;
		}
	}
	

	//table operations	
	private void totalAllComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal prev_comm 	= new BigDecimal(0.00);	
		BigDecimal sked_amt		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { prev_comm 	= prev_comm.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { prev_comm 	= prev_comm.add(new BigDecimal(0.00)); }

			try { sked_amt 	= sked_amt.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { sked_amt 	= sked_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null, null, prev_comm , sked_amt,  null, null });		
	}

	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}



	//processes and validation
	private boolean checkifReleased(){

		boolean x = false;

		if(checkIfReleased()==true)
		{
			x = true;

		}

		return x;
	}

	private boolean checkifCommBalisZero(){

		boolean x = false;

		Double comm_bal = Double.valueOf(txtOutofbal.getText().trim().replace(",","")).doubleValue();

		if(comm_bal==0)
		{
			x = true;

		}

		return x;
	}	

	private boolean checkIfReleased() {

		boolean checkIfReleased = false;	

		int row = tblComm_CommSched.getSelectedRow();
		String status = tblComm_CommSched.getValueAt(row,6).toString();		

		if(!status.equals("Not Yet Qualified")) { checkIfReleased = true; }

		return checkIfReleased;
	}

	private void setCommDetails(){

		int row = tblComm_CommSched.getSelectedRow();

		agent_nm 		  = tblComm_CommSched.getValueAt(row,0).toString();	
		String agent_rate = "";	
		String agent_posn = "";		

		try { agent_rate = tblComm_CommSched.getValueAt(row,1).toString(); } 
		catch (NullPointerException e) { agent_rate=""; }

		try { agent_posn = tblComm_CommSched.getValueAt(row,2).toString();} 
		catch (NullPointerException e) { agent_posn=""; }		

		String comm_type_1= tblComm_CommSched.getValueAt(row,3).toString();	
		String remarks	  = tblComm_CommSched.getValueAt(row,7).toString();	
		Double prev_amt	  = Double.parseDouble(tblComm_CommSched.getValueAt(row,4).toString());	
		Double comm_amt	  = Double.parseDouble(tblComm_CommSched.getValueAt(row,5).toString());	
		AgentType	  	  = tblComm_CommSched.getValueAt(row,9).toString();	
		Integer	comm_int  = 0;

		if (comm_type_1.equals("1st CA")) 			{comm_int  = 1; }
		else if (comm_type_1.equals("2nd CA")) 		{comm_int  = 2;}
		else if (comm_type_1.equals("3rd CA")) 		{comm_int  = 3;}
		else if (comm_type_1.equals("4th CA")) 		{comm_int  = 4;}
		else if (comm_type_1.equals("Full Comm")) 	{comm_int  = 10;}	
		else if (comm_type_1.equals("BB")) 			{comm_int  = 0;}	

		tagAgentName.setTag(agent_nm);
		lookupAgent.setText(getAgentDetails(agent_nm));
		txtPosition.setText(agent_posn);
		txtActualRate.setText(agent_rate);
		cmbCommType.setSelectedIndex(comm_int);	
		txtPrevComm.setText(nf.format(prev_amt));
		txtCommAmt.setText(nf.format(comm_amt));	
		txtRemarks.setText(remarks);
	}

	private Boolean checkCompleteDetails(){

		boolean x = false;		

		String agent, comm_amt;

		agent 		= lookupAgent.getText();
		comm_amt 	= txtCommAmt.getText();		

		if (agent.equals("") || comm_amt.equals("0.00")) {x=false;} 
		else {x=true;}		

		return x;

	}

	private void computeBalance(){

		//Double comm			= 0.00;
		//Double tot_comm_nsp = nsp*(maxRate()/100) ;
		AgentType	  	  = tblComm_CommSched.getValueAt(0,9).toString();	

		if (isRowHouse(hse_model)==true){SalesType = "comm_rate_socialized";} //comm_rate_socialized - refers to the column in mf_sales_position
		else {SalesType = "comm_rate_lowcost";}  //comm_rate_lowcost - refers to the column in mf_sales_position

		if (SalesType.equals("comm_rate_socialized")&&AgentType.equals("001")) {rate = 0.06 ;}
		else if (SalesType.equals("comm_rate_socialized")&&AgentType.equals("002")) {rate = 0.07 ;}
		else if (SalesType.equals("comm_rate_lowcost")&&AgentType.equals("001")) {rate = 0.05 ;}
		else if (SalesType.equals("comm_rate_lowcost")&&AgentType.equals("002")) {rate = 0.06 ;}

		System.out.printf("Sales Type : " + SalesType + "\n");
		System.out.printf("Agent Type : " + AgentType + "\n");

		Double tot_comm_nsp = nsp*rate ;
		Double old_comm_total  =  Double.valueOf(tblComm_CommSched_total.getValueAt(0,5).toString().replace(",","")).doubleValue();

		/*for(int x=0; x < modelComm_CommSched.getRowCount(); x++){	

			String comm_type = tblComm_CommSched.getValueAt(x,3).toString();	
			if (comm_type.equals("Promo")) {} 
			else 
			{
				comm = Double.parseDouble(tblComm_CommSched.getValueAt(x,5).toString());
				tot_comm_nsp = tot_comm_nsp - comm;
			}			
		}*/

		txtOutofbal.setText(nf.format(tot_comm_nsp - old_comm_total));		
	}

	private double maxRate(){		

		double prev_maxRate = 0.00;

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){	

			double rate = 0.00;
			try { rate = Double.parseDouble(tblComm_CommSched.getValueAt(x,1).toString()); } 
			catch (NullPointerException e) {rate = 0.00; }

			if (rate>=prev_maxRate) { prev_maxRate = rate; } 
			else {  }

		}

		System.out.printf("Max Rate: " + prev_maxRate + "\n" );
		return prev_maxRate;
	}

	private double getTotalCommOfAgent(){		

		double agent_tot_comm = 0.00;
		double new_comm     = Double.valueOf(txtCommAmt.getText().trim().replace(",","")).doubleValue();

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){	

			String nm = tblComm_CommSched.getValueAt(x,0).toString();			

			if (agent_nm.equals(nm)) {
				double comm = 0.00;
				try { comm = Double.parseDouble(tblComm_CommSched.getValueAt(x,5).toString()); } 
				catch (NullPointerException e) {comm = 0.00; }
				agent_tot_comm = agent_tot_comm + comm;				
			}

		}

		agent_tot_comm = agent_tot_comm + new_comm;

		System.out.printf("Agent Total Comm.: " + agent_tot_comm + "\n" );
		return agent_tot_comm;
	}

	private Boolean checkIfAgentExist(String name){

		Boolean existing = false;

		String strSQL = 			
			"select rec_id from cm_schedule_dl where pbl_id = '"+pbl_id+"' " +
			"and seq_no = "+seq_no+" and agent_code = '"+lookupAgent.getText().trim()+"' and status != 'I' \r\n" ;		

		System.out.printf("SQL - commTypeAlreadyExist: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			existing = true;
		}else{
			existing = false;
		}

		return existing;
	}

	private void checkCommType(){

		String CommType  = "";

		if (cmbCommType.getSelectedIndex()==1 ) { CommType = "1"; }
		else if  (cmbCommType.getSelectedIndex()==2 ) { CommType = "2"; }
		else if  (cmbCommType.getSelectedIndex()==3 ) { CommType = "3"; }	
		else if  (cmbCommType.getSelectedIndex()==4 ) { CommType = "4"; }
		else if  (cmbCommType.getSelectedIndex()==5 ) { CommType = "5"; }
		else if  (cmbCommType.getSelectedIndex()==6 ) { CommType = "6"; }
		else if  (cmbCommType.getSelectedIndex()==7 ) { CommType = "7"; }
		else if  (cmbCommType.getSelectedIndex()==8 ) { CommType = "8"; }
		else if  (cmbCommType.getSelectedIndex()==9 ) { CommType = "9"; }
		else if  (cmbCommType.getSelectedIndex()==10 ) { CommType = "FC"; }

		if (getSchemeDetails(CommType)==null && cmbCommType.getSelectedIndex()!=0) 
		{
			JOptionPane.showMessageDialog(getContentPane(),"Selected commission type does not exist for scheme " + scheme_id +
					". \n" + "Please select another commision type."
					,"Warning",JOptionPane.WARNING_MESSAGE);
			cmbCommType.setSelectedIndex(0);
		}

		else if (commTypeAlreadyExist(CommType) && btnSave.isEnabled())
		{
			JOptionPane.showMessageDialog(getContentPane(),"Selected commission type already exists. \n" + "Please select another commision type."
					,"Warning",JOptionPane.WARNING_MESSAGE);
			cmbCommType.setSelectedIndex(0);
		}



	}


	//save
	private void saveNewAgentSchedule_header(pgUpdate db){

		String sqlDetail = 
			"insert into cm_schedule_hd \n" +
			"values ( \n" +
			""+CreateCommissionSchedule.getNextRecID_hd()+",   \n" +
			"'"+entity_id+"',   \n" +
			"'"+proj_id+"',   \n" +
			"'"+pbl_id+"',   \n" +
			""+seq_no+",   \n" +
			"'"+agent_id+"',   \n" +
			""+agent_rate+",   \n" +
			""+Double.valueOf(txtCommAmt.getText().trim().replace(",","")).doubleValue()+",   \n" +
			"now(),   \n" +
			"'"+scheme_id+"',   \n" +
			"'"+posn_id+"',   \n" +
			"'F',   \n" +
			"'T',   \n" +
			"false, \n" +
			"'A'   \n" +			
			") " ;

		System.out.printf("SQL #1 - NewAgentSchedule: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void updateAgentSchedule_header(pgUpdate db){

		String sqlDetail = 
			"update cm_schedule_hd \n" +
			"set gross_amt = "+getTotalCommOfAgent()+" " +
			"where trim(pbl_id) = '"+pbl_id.trim()+"' and seq_no = "+seq_no+" and agent_code = '"+agent_id+"'  " ;

		System.out.printf("SQL #1 - updateAgentSchedule: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void saveNewAgentSchedule_detail(pgUpdate db){

		String CommType  = "";

		if (cmbCommType.getSelectedIndex()==1 ) { CommType = "1"; }
		else if  (cmbCommType.getSelectedIndex()==2 ) { CommType = "2"; }
		else if  (cmbCommType.getSelectedIndex()==3 ) { CommType = "3"; }	
		else if  (cmbCommType.getSelectedIndex()==4 ) { CommType = "4"; }
		else if  (cmbCommType.getSelectedIndex()==5 ) { CommType = "5"; }
		else if  (cmbCommType.getSelectedIndex()==6 ) { CommType = "6"; }
		else if  (cmbCommType.getSelectedIndex()==7 ) { CommType = "7"; }
		else if  (cmbCommType.getSelectedIndex()==8 ) { CommType = "8"; }
		else if  (cmbCommType.getSelectedIndex()==9 ) { CommType = "9"; }
		else if  (cmbCommType.getSelectedIndex()==10 ) { CommType = "FC"; }

		if (getSchemeDetails(CommType)==null) 
		{
			JOptionPane.showMessageDialog(getContentPane(),"Selected commission type does not exist for scheme " + scheme_id,"Warning",JOptionPane.WARNING_MESSAGE);
		}

		else 
		{
			Object[] comm_dtl 	= getSchemeDetails(CommType);
			String comm_type 	= (String) comm_dtl[0];
			Integer daysfromor 	= Integer.parseInt(comm_dtl[2].toString());		

			String sqlDetail = 
				"insert into cm_schedule_dl " +
				"values ( " +
				"'"+entity_id+"',   " + //account_code
				"'"+proj_id+"',   " +	//projcode
				"'"+pbl_id+"',   " +	//pbl_id
				""+seq_no+",   " +		//seq_no
				"'"+agent_id+"',   " +	//agent_code
				"now(),   " + 			//tran_date
				"'AA',   " +			//tran_type
				"'"+comm_type+"',   " +	//comm_type
				"null,   " +			//promo_code
				"'"+datersvd+"'::date + "+daysfromor+" ,   " +  //release_date
				"'"+getCommFrequency()+"',   " +			//frequency
				"null,   " +			//freq_identifier
				""+Double.valueOf(txtPrevComm.getText().trim().replace(",","")).doubleValue()+",   " +	//prev_commamt
				"0.00,   " +	//sked_amt
				""+Double.valueOf(txtCommAmt.getText().trim().replace(",","")).doubleValue()+",   " +	//comm_amt
				"0.00,   " +			//actual_amt
				"0.00,   " +			//adv_amount
				"'0',   " +				//adv_rplfno
				"0,   " +				//for_processing
				"'A',   " +				//status
				"null,   " +			//is_manual
				"'"+co_id+"',   " +		//co_id
				"'"+co_id+"',   " +		//busunit_id
				"null,   " +		//cdf_no
				"'"+txtRemarks.getText().trim().replace("'","''")+"'  ,   " + //remarks
				"true,   " +		//for_disbproc
				"null,   " +		//in_kind_promo_code
				"true,   " +		//view_on
				"null,   " +		//override_code
				"null,   " +		//override_date
				"null,   " +		//production_fr
				"null,   " +		//production_to
				"null,   " +		//fund_fr
				"null,   " +		//fund_to
				"null,   " +		//production_target
				"true,   " +  		//deduct_wtax
				"null,   " +		//old_cdfno
				"'"+getBuyerType()+"',   " +  //orig_btype
				"false," +
				"false   " +		//on_hold
				") " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	

		}

	}

	private void updateAgentSchedule_detail(pgUpdate db){

		int rw = tblComm_CommSched.getSelectedRow();	
		String CommType  = "";
		String rec_id	 = tblComm_CommSched.getValueAt(rw,8).toString();	

		if (cmbCommType.getSelectedIndex()==1 ) { CommType = "1"; }
		else if  (cmbCommType.getSelectedIndex()==2 ) { CommType = "2"; }
		else if  (cmbCommType.getSelectedIndex()==3 ) { CommType = "3"; }	
		else if  (cmbCommType.getSelectedIndex()==4 ) { CommType = "4"; }
		else if  (cmbCommType.getSelectedIndex()==5 ) { CommType = "5"; }
		else if  (cmbCommType.getSelectedIndex()==6 ) { CommType = "6"; }
		else if  (cmbCommType.getSelectedIndex()==7 ) { CommType = "7"; }
		else if  (cmbCommType.getSelectedIndex()==8 ) { CommType = "8"; }
		else if  (cmbCommType.getSelectedIndex()==9 ) { CommType = "9"; }
		else if  (cmbCommType.getSelectedIndex()==10 ) { CommType = "FC"; }

		String sqlDetail = 
			"update cm_schedule_dl \n" +
			"set comm_type = '"+CommType+"', \n" +
			"prev_commamt = "+Double.valueOf(txtPrevComm.getText().trim().replace(",","")).doubleValue()+", \n" +
			"comm_amt = "+Double.valueOf(txtCommAmt.getText().trim().replace(",","")).doubleValue()+",  \n" +
			"remarks = '"+txtRemarks.getText().trim().replace("'","''")+"'  \n" +
			"where rec_id = "+rec_id+" \n\n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}






}
