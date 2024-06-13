package Accounting.ContractorsPayment;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelContractorHouseRepair;
import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class HouseRepair extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "House Repair";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlBillingTable;
	private JPanel pnlSouth;
	private JPanel pnlTable;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlRepair;
	private JPanel pnTableRepair;
	private JPanel pnlHouseContractDtl;
	private JPanel pnlHouseContractDtl_1;
	private JPanel pnlHouseContractDtl_1a;
	private JPanel pnlHouseContractDtl_1b;
	private JPanel pnlHouseContractDtl_2;
	private JPanel pnlHouseContractDtl_3;
	private JPanel pnlHouseContractDtl_3a;
	private JPanel pnlHouseContractDtl_3b;
	private JPanel pnlAddNewBilling;
	private JPanel pnlAddNewBilling_a;
	private JPanel pnlAddNewBilling_a1;	
	private JPanel pnlAddNewBilling_a2;
	private JPanel pnlAddNewBilling_b;
	private JPanel pnlAddNewBilling_c;
	private JPanel pnlAddNewBilling_a3;

	private JLabel lblCompany;
	private JLabel lblContractor;
	private JLabel lblPayeeType;	
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblAwardedCost;
	private JLabel lblDownpmt;
	private JLabel lblRetention;
	private JLabel lblContract;
	private JLabel lblPayee;
	private JLabel lblBillingPayeeType;
	private JLabel lblRepairDate;
	private JLabel lblRepairAmt;
	private JLabel lblPremCost;
	private JLabel lblWTax_amt;
	private JLabel lblNet_amt;
	private JLabel tagX;
	private JLabel lblChargedTo;
	private JLabel lblChargeToType;
	private JLabel lblChargeAmt;

	private _JTagLabel tagCompany;
	private _JTagLabel tagContractor;	
	private _JTagLabel tagProject;
	private _JTagLabel tagUnit;
	private _JTagLabel tagContract;
	private _JTagLabel tagPayeeType;
	private _JTagLabel tagPayee;
	private _JTagLabel tagBillingPayeeType;
	private _JTagLabel tagChargePayee;
	private _JTagLabel tagChargePayeeType;

	private JXTextField txtContract;
	private JXTextField txtContractor;
	private JXTextField txtPayeeType;	

	private _JXFormattedTextField txtDownpmt;
	private _JXFormattedTextField txtRetention;
	private _JXFormattedTextField txtPremCost;
	private _JXFormattedTextField txtWTaxAmt;
	private _JXFormattedTextField txtNetAmt;
	private _JXFormattedTextField txtChargeAmt;	
	private static _JXFormattedTextField txtContractPrice;
	private _JXFormattedTextField txtGrossAmt;

	private _JScrollPaneMain scrollRepair;
	private _JScrollPaneTotal scrollRepair_total;

	private modelContractorHouseRepair modelRepair;
	private modelContractorHouseRepair modelRepair_total;
	private _JTableTotal tblRepair_total;	
	private _JTableMain tblRepair;
	private JList rowHeaderRepair;	

	private _JLookup lookupCompany;
	private _JLookup lookupUnit;
	private _JLookup lookupProject;
	private _JLookup lookupPayee;
	private _JLookup lookupBillingPayeeType;
	private _JLookup lookupChargeTo;
	private _JLookup lookupChargeToPayeeType;


	private JButton btnCancel;
	private JButton btnAddNew;
	private JButton btnRefresh;	
	private JButton btnEdit;
	private JButton btnBillingSave;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JScrollPane scpRepairDesc;
	private JTextArea txtAcctDesc;

	private _JDateChooser dteRepair;

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	String co_id = "";
	String company 		= "";
	public static String company_logo;
	String proj_id = "";
	String proj_name = "";
	String pbl_no = "";
	String pbl_desc = "";
	String ntp_no 		= "";
	String contract_no 	= "";
	String contractor_name 	= "";	
	String payee_id 	= "";
	String payee_name 	= "";
	String payee_type_id 	= "";
	String payee_type_name 	= "";
	String charge_payee_id 	= "";
	String charge_payee_name 	= "";
	String charge_payee_type_id 	= "";
	String charge_payee_type_name 	= "";
	Double wtax_rate = 0.00;
	String rplf_no = "";
	String repair_no = "";	
	Double gr_amt   = 0.00;
	Double vat_amt  = 0.00;
	Double prem_cost  = 0.00;
	Double wtax_amt = 0.00;
	Double net_amt  = 0.00;
	Double charge_amt  = 0.00;
	Double vat_rate = 0.00;
	Double exp_amt  = 0.00;
	String to_do = "addnew";  //to distinguish saving from updating

	private JPopupMenu menu;
	private JMenuItem mniopenRPLF;
	//private JMenuItem mniopenPV;
	private JMenuItem mniDelete;
	protected String acct_name;
	protected String subcon_id;
	protected String subcon_name;
	public static _JTagLabel tagsub_con;
	public static _JLookup lookupsub_con;
	public static JLabel lblsub_con;

	public HouseRepair() {
		super(title, true, true, true, true);
		initGUI();
	}

	public HouseRepair(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public HouseRepair(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(935, 538));
		this.setBounds(0, 0, 935, 538);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			//mniopenPV = new JMenuItem("Open Payable Voucher");	
			mniDelete = new JMenuItem("Delete");
			menu.add(mniopenRPLF);
			//menu.add(mniopenPV);
			menu.add(mniDelete);

			mniopenRPLF.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openDRF();				
				}
			});
			/*mniopenPV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openPV();				
				}
			});*/
			mniDelete.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					executeDelete();			
				}
			});
		}


		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(0,0));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 225));				

			//company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlComp_a, BorderLayout.NORTH);	
			pnlComp_a.setPreferredSize(new java.awt.Dimension(921, 94));	
			pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));	
			pnlComp_a.setBorder(lineBorder);

			pnlComp_a1 = new JPanel(new GridLayout(3, 2, 5, 5));
			pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(207, 97));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

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
							company     = (String) data[1];						
							tagCompany.setTag(company);

							lblProject.setEnabled(true);
							lookupProject.setEnabled(true);
							tagProject.setEnabled(true);	

							lookupProject.setValue("");	
							tagProject.setTag("");	

							KEYBOARD_MANAGER.focusNextComponent();		
							lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
						}
					}
				});
			}	
			{
				lblProject = new JLabel("Project", JLabel.TRAILING);
				pnlComp_a1.add(lblProject);
				lblProject.setBounds(8, 11, 62, 12);
				lblProject.setEnabled(false);
				lblProject.setPreferredSize(new java.awt.Dimension(96, 26));
			}
			{
				lookupProject = new _JLookup(null, "Project",0,2);
				pnlComp_a1.add(lookupProject);
				lookupProject.setReturnColumn(0);
				lookupProject.setEnabled(false);
				lookupProject.setPreferredSize(new java.awt.Dimension(103, 26));
				lookupProject.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							proj_id =(String) data[0];	
							proj_name 	= (String) data[1];				
							tagProject.setTag(proj_name);

							lblUnit.setEnabled(true);	
							lookupUnit.setEnabled(true);	
							tagUnit.setEnabled(true);	

							lookupUnit.setValue("");	
							tagUnit.setTag("");	

							KEYBOARD_MANAGER.focusNextComponent();		
							lookupUnit.setLookupSQL(getUnitList());
						}
					}
				});
			}	
			{
				lblUnit = new JLabel("Unit", JLabel.TRAILING);
				pnlComp_a1.add(lblUnit);
				lblUnit.setBounds(8, 11, 62, 12);
				lblUnit.setEnabled(false);
				lblUnit.setPreferredSize(new java.awt.Dimension(96, 26));
			}
			{
				lookupUnit = new _JLookup(null, "Unit",0,2);
				pnlComp_a1.add(lookupUnit);
				lookupUnit.setReturnColumn(0);
				lookupUnit.setEnabled(false);
				lookupUnit.setPreferredSize(new java.awt.Dimension(103, 26));
				lookupUnit.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							pbl_no =(String) data[0];	
							pbl_desc 	= (String) data[1];				
							tagUnit.setTag(pbl_desc);

							enableButtons(true, false, true, true);
							enable_fields(true);

							refresh_fields();
							setContractDetails();
							displayContractChangeOrder(modelRepair, rowHeaderRepair, modelRepair_total);
						}
					}
				});
			}


			pnlComp_a2 = new JPanel(new GridLayout(3, 1, 5, 5));
			pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
			pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
			pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				{
					tagCompany = new _JTagLabel("[ ]");
					pnlComp_a2.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);	
					tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagProject = new _JTagLabel("[ ]");
					pnlComp_a2.add(tagProject);
					tagProject.setBounds(209, 27, 700, 22);
					tagProject.setEnabled(false);	
					tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
				}	
				{
					tagUnit = new _JTagLabel("[ ]");
					pnlComp_a2.add(tagUnit);
					tagUnit.setBounds(209, 27, 700, 22);
					tagUnit.setEnabled(false);	
					tagUnit.setPreferredSize(new java.awt.Dimension(27, 33));
				}	
			}

			pnlRepair = new JPanel(new BorderLayout(0,0));
			pnlNorth.add(pnlRepair, BorderLayout.CENTER);				
			pnlRepair.setPreferredSize(new java.awt.Dimension(921, 135));
			pnlRepair.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));

			{

				pnlHouseContractDtl = new JPanel(new BorderLayout(5, 5));
				pnlRepair.add(pnlHouseContractDtl, BorderLayout.WEST);	
				pnlHouseContractDtl.setPreferredSize(new java.awt.Dimension(818, 123));

				pnlHouseContractDtl_1 = new JPanel(new BorderLayout(5, 5));
				pnlHouseContractDtl.add(pnlHouseContractDtl_1, BorderLayout.WEST);	
				pnlHouseContractDtl_1.setPreferredSize(new java.awt.Dimension(211, 94));
				pnlHouseContractDtl_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

				pnlHouseContractDtl_1a = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlHouseContractDtl_1.add(pnlHouseContractDtl_1a, BorderLayout.WEST);	
				pnlHouseContractDtl_1a.setPreferredSize(new java.awt.Dimension(89, 119));
				pnlHouseContractDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));	

				{
					lblContract = new JLabel("Contract/NTP", JLabel.TRAILING);
					pnlHouseContractDtl_1a.add(lblContract);
					lblContract.setEnabled(false);	
				}	
				{
					lblContractor = new JLabel("Contractor", JLabel.TRAILING);
					pnlHouseContractDtl_1a.add(lblContractor);
					lblContractor.setEnabled(false);	
				}	
				{
					lblPayeeType = new JLabel("Payee Type", JLabel.TRAILING);
					pnlHouseContractDtl_1a.add(lblPayeeType);
					lblPayeeType.setEnabled(false);	
				}

				pnlHouseContractDtl_1b = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlHouseContractDtl_1.add(pnlHouseContractDtl_1b, BorderLayout.CENTER);	
				pnlHouseContractDtl_1b.setPreferredSize(new java.awt.Dimension(107, 84));
				pnlHouseContractDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					txtContract = new JXTextField("");
					pnlHouseContractDtl_1b.add(txtContract);
					txtContract.setEnabled(false);	
					txtContract.setEditable(false);	
					txtContract.setBounds(120, 25, 300, 22);	
					txtContract.setHorizontalAlignment(JTextField.CENTER);	
				}	
				{
					txtContractor = new JXTextField("");
					pnlHouseContractDtl_1b.add(txtContractor);
					txtContractor.setEnabled(false);	
					txtContractor.setEditable(false);	
					txtContractor.setBounds(120, 25, 300, 22);	
					txtContractor.setHorizontalAlignment(JTextField.CENTER);	
				}	
				{
					txtPayeeType = new JXTextField("");
					pnlHouseContractDtl_1b.add(txtPayeeType);
					txtPayeeType.setEnabled(false);	
					txtPayeeType.setEditable(false);	
					txtPayeeType.setBounds(120, 25, 300, 22);	
					txtPayeeType.setHorizontalAlignment(JTextField.CENTER);	
				}	

				pnlHouseContractDtl_2 = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlHouseContractDtl.add(pnlHouseContractDtl_2, BorderLayout.CENTER);	
				pnlHouseContractDtl_2.setPreferredSize(new java.awt.Dimension(309, 123));
				pnlHouseContractDtl_2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

				{
					tagContract = new _JTagLabel("[ ]");
					pnlHouseContractDtl_2.add(tagContract);
					tagContract.setEnabled(false);	
				}	
				{
					tagContractor = new _JTagLabel("[ ]");
					pnlHouseContractDtl_2.add(tagContractor);
					tagContractor.setEnabled(false);	
				}		
				{
					tagPayeeType = new _JTagLabel("[ ]");
					pnlHouseContractDtl_2.add(tagPayeeType);
					tagPayeeType.setEnabled(false);	
				}

				pnlHouseContractDtl_3 = new JPanel(new BorderLayout(5, 5));
				pnlHouseContractDtl.add(pnlHouseContractDtl_3, BorderLayout.EAST);	
				pnlHouseContractDtl_3.setPreferredSize(new java.awt.Dimension(289, 135));
				pnlHouseContractDtl_3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

				pnlHouseContractDtl_3a = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlHouseContractDtl_3.add(pnlHouseContractDtl_3a, BorderLayout.WEST);	
				pnlHouseContractDtl_3a.setPreferredSize(new java.awt.Dimension(133, 135));
				pnlHouseContractDtl_3a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));						

				{
					lblAwardedCost = new JLabel("Contract Price", JLabel.TRAILING);
					pnlHouseContractDtl_3a.add(lblAwardedCost);
					lblAwardedCost.setEnabled(false);	
				}	
				{
					lblDownpmt = new JLabel("Unrecouped DP", JLabel.TRAILING);
					pnlHouseContractDtl_3a.add(lblDownpmt);
					lblDownpmt.setEnabled(false);	
				}					
				{
					lblRetention = new JLabel("Retention Balance", JLabel.TRAILING);
					pnlHouseContractDtl_3a.add(lblRetention);
					lblRetention.setEnabled(false);	
				}	

				pnlHouseContractDtl_3b = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlHouseContractDtl_3.add(pnlHouseContractDtl_3b, BorderLayout.CENTER);	
				pnlHouseContractDtl_3b.setPreferredSize(new java.awt.Dimension(122, 135));
				pnlHouseContractDtl_3b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));	

				{
					txtContractPrice = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlHouseContractDtl_3b.add(txtContractPrice);
					txtContractPrice.setEnabled(false);	
					txtContractPrice.setEditable(false);						
					txtContractPrice.setBounds(120, 25, 300, 22);	
					txtContractPrice.setHorizontalAlignment(JTextField.CENTER);	
				}	
				{
					txtDownpmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlHouseContractDtl_3b.add(txtDownpmt);
					txtDownpmt.setEnabled(false);	
					txtDownpmt.setEditable(false);	
					txtDownpmt.setBounds(120, 25, 300, 22);	
					txtDownpmt.setHorizontalAlignment(JTextField.CENTER);	
				}					
				{
					txtRetention = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlHouseContractDtl_3b.add(txtRetention);
					txtRetention.setEnabled(false);	
					txtRetention.setBounds(120, 25, 300, 22);	
					txtRetention.setEditable(false);	
					txtRetention.setHorizontalAlignment(JTextField.CENTER);	
				}	
			}


		}
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlBillingTable = new JPanel();
			pnlTable.add(pnlBillingTable, BorderLayout.CENTER);
			pnlBillingTable.setLayout(new BorderLayout(5, 5));
			pnlBillingTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlBillingTable.setBorder(lineBorder);		

			{			
				pnTableRepair = new JPanel(new BorderLayout());
				pnlBillingTable.add(pnTableRepair, "Center");
				pnTableRepair.setPreferredSize(new java.awt.Dimension(1183, 365));				

				{
					scrollRepair = new _JScrollPaneMain();
					pnTableRepair.add(scrollRepair, BorderLayout.CENTER);
					{
						modelRepair = new modelContractorHouseRepair();

						tblRepair = new _JTableMain(modelRepair);
						scrollRepair.setViewportView(tblRepair);
						tblRepair.addMouseListener(this);	
						tblRepair.addMouseListener(new PopupTriggerListener_panel());
						tblRepair.getColumnModel().getColumn(0).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(1).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(2).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(3).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(4).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(5).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(6).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(7).setPreferredWidth(80);
						tblRepair.getColumnModel().getColumn(8).setPreferredWidth(80);
						tblRepair.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {

							}							
							public void keyPressed(KeyEvent e) {

							}

						}); 
						tblRepair.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblRepair.rowAtPoint(e.getPoint()) == -1){
									tblRepair_total.clearSelection();
								}else{
									tblRepair.setCellSelectionEnabled(true);
									btnEdit.setEnabled(true);
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblRepair.rowAtPoint(e.getPoint()) == -1){
									tblRepair_total.clearSelection();
								}else{
									btnEdit.setEnabled(true);
								}
							}
						});

					}
					{
						rowHeaderRepair = tblRepair.getRowHeader22();
						scrollRepair.setRowHeaderView(rowHeaderRepair);
						scrollRepair.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollRepair_total = new _JScrollPaneTotal(scrollRepair);
					pnTableRepair.add(scrollRepair_total, BorderLayout.SOUTH);
					{
						modelRepair_total = new modelContractorHouseRepair();
						modelRepair_total.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),  
								null, null, null, null, new BigDecimal(0.00), null, null });

						tblRepair_total = new _JTableTotal(modelRepair_total, tblRepair);
						tblRepair_total.setFont(dialog11Bold);
						scrollRepair_total.setViewportView(tblRepair_total);
						((_JTableTotal) tblRepair_total).setTotalLabel(0);
					}
				}
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new Dimension(55, 30));
			{
				JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

				{
					btnAddNew = new JButton("Add New");
					pnlSouthCenter.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenter.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenter.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");	
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenter.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}
		{

			pnlAddNewBilling = new JPanel();
			pnlAddNewBilling.setLayout(new BorderLayout(5, 5));
			pnlAddNewBilling.setBorder(lineBorder);		
			pnlAddNewBilling.setPreferredSize(new java.awt.Dimension(600, 470));		

			{		
				pnlAddNewBilling_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewBilling.add(pnlAddNewBilling_a, BorderLayout.NORTH);				
				pnlAddNewBilling_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBilling_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewBilling_a.setPreferredSize(new java.awt.Dimension(600, 330));		

				{
					pnlAddNewBilling_a1 = new JPanel(new GridLayout(11, 1, 0, 5));
					pnlAddNewBilling_a.add(pnlAddNewBilling_a1, BorderLayout.WEST);				
					pnlAddNewBilling_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewBilling_a1.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlAddNewBilling_a1.setPreferredSize(new java.awt.Dimension(100, 145));		

					{
						lblPayee = new JLabel("Payee", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblPayee);
						lblPayee.setEnabled(true);	
						lblPayee.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblBillingPayeeType = new JLabel("Payee Type", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblBillingPayeeType);
						lblBillingPayeeType.setEnabled(false);	
						lblBillingPayeeType.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblRepairDate = new JLabel("Repair Date", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblRepairDate);
						lblRepairDate.setEnabled(true);	
						lblRepairDate.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblRepairAmt = new JLabel("Gross Amt.", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblRepairAmt);
						lblRepairAmt.setEnabled(true);	
						lblRepairAmt.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblPremCost = new JLabel("Premium Cost", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblPremCost);
						lblPremCost.setEnabled(true);	
						lblPremCost.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblWTax_amt = new JLabel("WTax Amt.", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblWTax_amt);
						lblWTax_amt.setEnabled(true);	
						lblWTax_amt.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblNet_amt = new JLabel("Net Amt.", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblNet_amt);
						lblNet_amt.setEnabled(true);	
						lblNet_amt.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblChargedTo = new JLabel("Charged To", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblChargedTo);
						lblChargedTo.setEnabled(true);	
						lblChargedTo.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblChargeToType = new JLabel("Type", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblChargeToType);
						lblChargeToType.setEnabled(true);	
						lblChargeToType.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblsub_con = new JLabel("Sub-Con", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblsub_con);
						lblsub_con.setEnabled(false);	
					}
					{
						lblChargeAmt = new JLabel("Charged Amt.", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblChargeAmt);
						lblChargeAmt.setEnabled(true);	
						lblChargeAmt.setPreferredSize(new java.awt.Dimension(136, 24));
						
					}	
					/*{
						JLabel lblacct_id = new JLabel("Acct ID", JLabel.TRAILING);
						pnlAddNewBilling_a1.add(lblacct_id);
						lblacct_id.setEnabled(true);
						lblChargeAmt.setPreferredSize(new java.awt.Dimension(136, 24));
					}*/

				}
				{
					pnlAddNewBilling_a2 = new JPanel(new GridLayout(11, 1, 0, 5));
					pnlAddNewBilling_a.add(pnlAddNewBilling_a2, BorderLayout.CENTER);				
					pnlAddNewBilling_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewBilling_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlAddNewBilling_a2.setPreferredSize(new java.awt.Dimension(100, 150));

					{
						lookupPayee = new _JLookup(null, "Payee", 2, 2);
						pnlAddNewBilling_a2.add(lookupPayee);
						lookupPayee.setBounds(20, 27, 20, 25);
						lookupPayee.setReturnColumn(0);
						lookupPayee.setEnabled(true);	
						lookupPayee.setFilterName(true);
						lookupPayee.setLookupSQL(_NoticeToProceed.Contractor());
						lookupPayee.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupPayee.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	

									payee_id =(String) data[0];	
									payee_name 	= (String) data[1];				
									tagPayee.setTag(payee_name);

									lblBillingPayeeType.setEnabled(true);	
									lookupBillingPayeeType.setEnabled(true);	
									tagBillingPayeeType.setEnabled(true);	

									lookupBillingPayeeType.setValue("");	
									tagBillingPayeeType.setTag("");	

									lookupBillingPayeeType.setLookupSQL(RequestForPayment.getEntity_type(payee_id));
									
									computeRepairAmt();									
								}
							}
						});	
					}
					{
						lookupBillingPayeeType = new _JLookup(null, "Payee Type", 2, 2);
						pnlAddNewBilling_a2.add(lookupBillingPayeeType);
						lookupBillingPayeeType.setBounds(20, 27, 20, 25);
						lookupBillingPayeeType.setReturnColumn(0);
						lookupBillingPayeeType.setEnabled(false);	
						lookupBillingPayeeType.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupBillingPayeeType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	

									payee_type_id =(String) data[0];	
									payee_type_name = (String) data[1];				
									//tagBillingPayeeType.setTag(payee_type_name);

									wtax_rate = Double.parseDouble(data[3].toString());
									tagBillingPayeeType.setTag(payee_type_name +" ( "+wtax_rate+"% )");		
									
									computeRepairAmt();		
								}
							}
						});	
					}	
					{
						dteRepair = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlAddNewBilling_a2.add(dteRepair);
						dteRepair.setBounds(485, 7, 125, 21);
						dteRepair.setDate(null);
						dteRepair.setEnabled(true);
						dteRepair.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dteRepair.getDateEditor()).setEditable(false);
						dteRepair.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}	
					{
						txtGrossAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlAddNewBilling_a2.add(txtGrossAmt);
						txtGrossAmt.setEnabled(true);	
						txtGrossAmt.setEditable(true);	
						txtGrossAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtGrossAmt.setText("0.00");
						txtGrossAmt.setBounds(120, 25, 300, 22);						
						/*txtGrossAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {		

								computeRepairAmt();		

							}				 
						});	*/	
					}	
					{
						txtPremCost = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlAddNewBilling_a2.add(txtPremCost);
						txtPremCost.setEnabled(true);	
						txtPremCost.setEditable(true);
						txtPremCost.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtPremCost.setText("0.00");
						txtPremCost.setBounds(120, 25, 300, 22);
						txtPremCost.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {		

								computeRepairAmt();		

							}				 
						});		
					}
					{
						txtWTaxAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlAddNewBilling_a2.add(txtWTaxAmt);
						txtWTaxAmt.setEnabled(false);	
						txtWTaxAmt.setEditable(false);							
						txtWTaxAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtWTaxAmt.setText("0.00");
						txtWTaxAmt.setBounds(120, 25, 300, 22);	
					}
					{
						txtNetAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlAddNewBilling_a2.add(txtNetAmt);
						txtNetAmt.setEnabled(true);	
						txtNetAmt.setEditable(false);
						txtNetAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtNetAmt.setText("0.00");
						txtNetAmt.setBounds(120, 25, 300, 22);	
					}
					{
						lookupChargeTo = new _JLookup(null, "Charge To", 2, 2);
						pnlAddNewBilling_a2.add(lookupChargeTo);
						lookupChargeTo.setBounds(20, 27, 20, 25);
						lookupChargeTo.setReturnColumn(0);
						lookupChargeTo.setEnabled(true);	
						lookupChargeTo.setFilterName(true);
						lookupChargeTo.setLookupSQL(_NoticeToProceed.Contractor());
						lookupChargeTo.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupChargeTo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									charge_payee_id =(String) data[0];	
									charge_payee_name 	= (String) data[1];				
									tagChargePayee.setTag(charge_payee_name);

									lblChargeToType.setEnabled(true);	
									lookupChargeToPayeeType.setEnabled(true);	
									tagChargePayeeType.setEnabled(true);	

									lookupChargeToPayeeType.setValue("");	
									tagChargePayeeType.setTag("");	

									lookupChargeToPayeeType.setLookupSQL(RequestForPayment.getEntity_type(charge_payee_id));
									
									if(charge_payee_id.equals("7115114070")) {
										lookupsub_con.setEnabled(true);
										lookupsub_con.setEditable(true);
										lblsub_con.setEnabled(true);
										tagsub_con.setEnabled(true);
										lookupsub_con.setLookupSQL(_NoticeToProceed.Contractor());
									}
								}
							}
						});	
					}
					{
						lookupChargeToPayeeType = new _JLookup(null, "Charge To Payee Type", 2, 2);
						pnlAddNewBilling_a2.add(lookupChargeToPayeeType);
						lookupChargeToPayeeType.setBounds(20, 27, 20, 25);
						lookupChargeToPayeeType.setReturnColumn(0);
						lookupChargeToPayeeType.setEnabled(false);	
						lookupChargeToPayeeType.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupChargeToPayeeType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									charge_payee_type_id =(String) data[0];	
									charge_payee_type_name 	= (String) data[1];				
									tagChargePayeeType.setTag(charge_payee_type_name);

									//txtChargeAmt.setEnabled(true);	
									//txtChargeAmt.setEditable(true);
								}
							}
						});	
					}
					{
						lookupsub_con = new _JLookup();
						pnlAddNewBilling_a2.add(lookupsub_con);
						lookupsub_con.setReturnColumn(0);
						lookupsub_con.setEnabled(false);	
						lookupsub_con.setEditable(false);
						lookupsub_con.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									subcon_id =(String) data[0];	
									subcon_name= (String) data[1];
									
									tagsub_con.setTag(subcon_name);
									String subcon_remarks= txtAcctDesc.getText().trim() +  "\n" + 
											"Sub Contractor: " +subcon_name+ "  ";
											//"Reference No.:"+txtref_no.getText()  ;
									txtAcctDesc.setText(subcon_remarks);
								}
							}
						});
					}
					{
						txtChargeAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlAddNewBilling_a2.add(txtChargeAmt);
						txtChargeAmt.setEnabled(true);	
						txtChargeAmt.setEditable(false);							
						txtChargeAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtChargeAmt.setText("0.00");
						txtChargeAmt.setBounds(120, 25, 300, 22);	
						txtChargeAmt.setHorizontalAlignment(JTextField.CENTER);	
						txtChargeAmt.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {		

								Double gr_amt  = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue();	

								txtPremCost.setText("0.00");	
								txtWTaxAmt.setText("0.00");
								txtNetAmt.setText(df.format(gr_amt));	
							}				 
						});		


					}
					/*{
						lookupacct_id = new _JLookup();
						pnlAddNewBilling_a2.add(lookupacct_id);
						lookupacct_id.setEnabled(true);
						lookupacct_id.setLookupSQL(getacct_id());
						lookupacct_id.addLookupListener(new LookupListener() {
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null) {
									acct_id = (String) data[0];
									acct_name = (String) data[1];
									
									lookupacct_id.setValue(acct_id);
									tagacct_id.setTag(acct_name);
								}
							}
						});
						
					}*/
				}
				{
					pnlAddNewBilling_a3 = new JPanel(new GridLayout(11, 1, 0, 5));
					pnlAddNewBilling_a.add(pnlAddNewBilling_a3, BorderLayout.EAST);				
					pnlAddNewBilling_a3.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewBilling_a3.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlAddNewBilling_a3.setPreferredSize(new java.awt.Dimension(350, 145));		
					
					
					{
						tagPayee = new _JTagLabel("[ ]");
						pnlAddNewBilling_a3.add(tagPayee);
						tagPayee.setEnabled(true);	
						tagPayee.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						tagBillingPayeeType = new _JTagLabel("[ ]");
						pnlAddNewBilling_a3.add(tagBillingPayeeType);
						tagBillingPayeeType.setEnabled(false);	
						tagBillingPayeeType.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						tagX = new JLabel("[]", JLabel.TRAILING);
						pnlAddNewBilling_a3.add(tagX);
						tagX.setEnabled(true);	
						tagX.setVisible(false);	
					}	
					{
						tagX = new JLabel("[]", JLabel.TRAILING);
						pnlAddNewBilling_a3.add(tagX);
						tagX.setEnabled(true);	
						tagX.setVisible(false);	
					}	
					{
						tagX = new JLabel("[]", JLabel.TRAILING);
						pnlAddNewBilling_a3.add(tagX);
						tagX.setEnabled(true);	
						tagX.setVisible(false);	
					}	
					{
						tagX = new JLabel("[]", JLabel.TRAILING);
						pnlAddNewBilling_a3.add(tagX);
						tagX.setEnabled(true);	
						tagX.setVisible(false);	
					}	
					{
						tagX = new JLabel("[]", JLabel.TRAILING);
						pnlAddNewBilling_a3.add(tagX);
						tagX.setEnabled(true);	
						tagX.setVisible(false);	
					}	
					{
						tagChargePayee = new _JTagLabel("[ ]");
						pnlAddNewBilling_a3.add(tagChargePayee);
						tagChargePayee.setEnabled(true);	
						tagChargePayee.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						tagChargePayeeType = new _JTagLabel("[ ]");
						pnlAddNewBilling_a3.add(tagChargePayeeType);
						tagChargePayeeType.setEnabled(true);	
						tagChargePayeeType.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						tagsub_con = new _JTagLabel("[ ]");
						pnlAddNewBilling_a3.add(tagsub_con);
						tagsub_con.setEnabled(false);
						tagsub_con.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						tagX = new JLabel("[]", JLabel.TRAILING);
						pnlAddNewBilling_a3.add(tagX);
						tagX.setEnabled(true);	
						tagX.setVisible(false);	
					}
					/*{
						tagacct_id = new _JTagLabel("[ ]");
						pnlAddNewBilling_a3.add(tagacct_id);
						tagacct_id.setEnabled(true);	
					}*/
				}

				pnlAddNewBilling_b = new JPanel(new BorderLayout(5, 5));
				pnlAddNewBilling.add(pnlAddNewBilling_b, BorderLayout.CENTER);				
				pnlAddNewBilling_b.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBilling_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewBilling_b.setPreferredSize(new java.awt.Dimension(500, 150));		
				{
					scpRepairDesc = new JScrollPane();
					pnlAddNewBilling_b.add(scpRepairDesc);
					scpRepairDesc.setBounds(82, 7, 309, 61);
					scpRepairDesc.setOpaque(true);
					scpRepairDesc.setPreferredSize(new java.awt.Dimension(375, 159));

					{
						txtAcctDesc = new JTextArea();
						scpRepairDesc.add(txtAcctDesc);
						scpRepairDesc.setViewportView(txtAcctDesc);
						txtAcctDesc.setText("House Repair Description / Remarks :");
						txtAcctDesc.setBounds(77, 3, 250, 81);
						txtAcctDesc.setLineWrap(true);
						txtAcctDesc.setPreferredSize(new java.awt.Dimension(366, 133));
						txtAcctDesc.setEditable(true);
						txtAcctDesc.setEnabled(true);
						txtAcctDesc.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {	

							}});	
					}		

				}

				pnlAddNewBilling_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewBilling.add(pnlAddNewBilling_c, BorderLayout.SOUTH);				
				pnlAddNewBilling_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBilling_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewBilling_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnBillingSave = new JButton("Save");
					pnlAddNewBilling_c.add(btnBillingSave);
					btnBillingSave.addActionListener(this);
					btnBillingSave.setEnabled(true);
					btnBillingSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==true) { 

								if(txtNetAmt.getText().equals("0.00"))
								{JOptionPane.showMessageDialog(getContentPane(), "Net amount must be greater than zero.", "Zero Amount", 
										JOptionPane.WARNING_MESSAGE);}
								else {

									if(lookupPayee.getText().equals(""))
									{JOptionPane.showMessageDialog(getContentPane(), "Enter payee.", "No Payee", 
											JOptionPane.WARNING_MESSAGE);}
									else {	

										if(lookupBillingPayeeType.getText().equals(""))
										{JOptionPane.showMessageDialog(getContentPane(), "Select payee type.", "No Payee Type", 
												JOptionPane.WARNING_MESSAGE);}
										else {	

											if(!lookupChargeTo.getText().equals("")&&txtChargeAmt.getText().equals("0.00")||
													!lookupChargeTo.getText().equals("")&&lookupChargeToPayeeType.getText().equals(""))
											{JOptionPane.showMessageDialog(getContentPane(), "Please complete backcharge details.", "Incomplete Backcharge Info.", 
													JOptionPane.WARNING_MESSAGE);}

											else {

												if(!lookupChargeTo.getText().equals("")){

													if(Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue()!=Double.valueOf(txtChargeAmt.getText().trim().replace(",","")).doubleValue())
													{JOptionPane.showMessageDialog(getContentPane(), "Repair amount not equal to backcharge amount.", "Incomplete Backcharge Info.", 
															JOptionPane.WARNING_MESSAGE);}
													else {
														
															executeSave();
															Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddNewBilling_c);
															optionPaneWindow.dispose();
													}
												}

												else 
												{
													executeSave();
													Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddNewBilling_c);
													optionPaneWindow.dispose();
												}
											}
										}
									}
								}
							}
							else if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==false ) 
							{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process house repair billings.","Information",JOptionPane.INFORMATION_MESSAGE); }

						}
					});
				}

			}
		}
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display tables
	public void displayContractChangeOrder(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {   //used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"a.repair_no, \r\n" +
			"to_char(a.repair_date,'MM-dd-yyyy') as date,\r\n" + 
			"a.amount,\r\n" + 
			"a.vat_amt,\r\n" + 
			"a.wtax_amt,\r\n" + 
			"a.prem_cost,\r\n" + 
			"a.net_amt,\r\n" + 
			"a.rplf_no,\r\n" + 
			"upper(trim(b.entity_name)) as payee,\r\n" + 
			"trim(c.entity_type_desc) as payee_type_id ,\r\n" + 
			"upper(trim(bb.entity_name)) as charged_to,\r\n" + 
			"trim(cc.entity_type_desc) as entity_type_id,\r\n" + 
			"d.amount,\r\n" + 
			"(case when g.date_paid is not null then 'RELEASED' else \n" + 
			"		(case when a.status_id = 'I' then 'INACTIVE' else\n" + 
			"		(case when trim(e.status_desc) is not null then trim(e.status_desc) else\n" + 
			"		'ACTIVE' end) end) end) as status,\r\n" + 
			"g.date_paid, \n" +
			"trim(a.remarks), \r\n" + 
			"a.prem_cost \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"from co_house_repair a\r\n" + 
			"left join rf_entity b on a.payee_id = b.entity_id\r\n" + 
			"left join mf_entity_type c on a.payee_type_id = c.entity_type_id\r\n" + 			
			"left join co_house_repair_backcharges d on a.repair_no = d.repair_no\r\n" + 
			"left join rf_entity bb on d.entity_id = bb.entity_id\r\n" + 
			"left join mf_entity_type cc on d.entity_type_id = cc.entity_type_id\r\n" + 			
			"left join (select * from rf_pv_header where status_id != 'I') f on a.rplf_no = f.pv_no\r\n" + 
			"left join rf_request_header ff on a.rplf_no = ff.rplf_no \n" +
			"left join mf_record_status e on f.status_id = e.status_id \r\n" + 
			"left join rf_cv_header g on f.cv_no = g.cv_no\r\n" + 
			"\r\n" + 
			"where a.pbl_id = '"+pbl_no+"'" +
			"order by a.rplf_no " ;
		System.out.println("displayContractChangeOrder: "+sql );
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalRepair(modelMain, modelTotal);			
		}		

		else {
			modelRepair_total = new modelContractorHouseRepair();
			modelRepair_total.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), 
					null, null, null, null, new BigDecimal(0.00), null, null });

			tblRepair_total = new _JTableTotal(modelRepair_total, tblRepair);
			tblRepair_total.setFont(dialog11Bold);
			scrollRepair_total.setViewportView(tblRepair_total);
			((_JTableTotal) tblRepair_total).setTotalLabel(0);}

		tblRepair.packAll();

		btnRefresh.setEnabled(true);
		adjustRowHeight();

	}	

	public void setContractDetails(){

		Object[] cont_dtl = getContractDetails();	

		contractor_name = "";
		//contract_no = cont_dtl[1].toString();
		try { contract_no = cont_dtl[1].toString();} catch (NullPointerException e) {  contract_no = ""; }

		try { contractor_name = cont_dtl[3].toString(); } catch (NullPointerException e) {  contractor_name = ""; }

		try { txtContract.setText(cont_dtl[0].toString()); } catch (NullPointerException e) {  txtContract.setText(""); }
		try { tagContract.setTag(cont_dtl[1].toString()); } catch (NullPointerException e) {  tagContract.setTag(""); }
		try { txtContractor.setText(cont_dtl[2].toString()); } catch (NullPointerException e) {  txtContractor.setText(""); }
		try { tagContractor.setTag(contractor_name); } catch (NullPointerException e) {  tagContractor.setTag(""); }
		try { txtPayeeType.setText(cont_dtl[4].toString()); } catch (NullPointerException e) {  txtPayeeType.setText(""); }
		try { tagPayeeType.setTag(cont_dtl[5].toString()); } catch (NullPointerException e) {  tagPayeeType.setTag(""); }
		try { txtContractPrice.setText(nf.format(Double.parseDouble(cont_dtl[6].toString()))); } catch (NullPointerException e) {  txtContractPrice.setText("0.00"); }
		try { txtDownpmt.setText(nf.format(Double.parseDouble(cont_dtl[7].toString()))); } catch (NullPointerException e) {  txtDownpmt.setText("0.00"); }
		try { txtRetention.setText(nf.format(Double.parseDouble(cont_dtl[8].toString()))); } catch (NullPointerException e) {  txtRetention.setText("0.00"); }

	}


	//Enable/Disable all components inside JPanel
	

	private void enable_fields(Boolean x){   

		lblContract.setEnabled(x);	
		lblContractor.setEnabled(x);	
		lblPayeeType.setEnabled(x);	
		lblAwardedCost.setEnabled(x);	
		lblDownpmt.setEnabled(x);	
		lblRetention.setEnabled(x);	

		txtContract.setEnabled(x);	
		txtContractor.setEnabled(x);	
		txtPayeeType.setEnabled(x);	
		txtContractPrice.setEnabled(x);	
		txtPremCost.setEnabled(x);	
		txtDownpmt.setEnabled(x);	
		txtRetention.setEnabled(x);	

		tagContract.setEnabled(x);	
		tagContractor.setEnabled(x);	
		tagPayeeType.setEnabled(x);	

	}

	private void refresh_fields(){  			

		txtContract.setText("");
		txtContractor.setText("");
		txtPayeeType.setText("");

		tagContract.setTag("");
		tagContractor.setTag("");
		tagPayeeType.setTag("");

		txtContractPrice.setText("0.00");
		txtDownpmt.setText("0.00");
		txtRetention.setText("0.00");	

	}

	private void refresh_fields2(){  			

		lookupUnit.setValue("");
		lookupProject.setValue("");
		tagProject.setTag("");
		tagUnit.setTag("");
		lookupUnit.setEnabled(false);
		tagUnit.setEnabled(false);
		lblUnit.setEnabled(false);

	}

	private void refresh_fields_fromSaving(){  

		lookupPayee.setValue("");
		lookupBillingPayeeType.setValue("");
		dteRepair.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		txtGrossAmt.setText("0.00");
		txtPremCost.setText("0.00");
		txtWTaxAmt.setText("0.00");
		txtNetAmt.setText("0.00");
		lookupChargeTo.setValue("");
		lookupChargeToPayeeType.setValue("");
		txtChargeAmt.setText("0.00");
		tagPayee.setTag("");
		tagBillingPayeeType.setTag("");
		tagChargePayee.setTag("");
		tagChargePayeeType.setTag("");
		txtAcctDesc.setText(" House Repair Description / Remarks :");
		lookupsub_con.setValue("");
		tagsub_con.setTag("");

	}

	private void refresh_tablesMain(){  		
		//reset table 1
		FncTables.clearTable(modelRepair);
		FncTables.clearTable(modelRepair_total);			
		rowHeaderRepair = tblRepair.getRowHeader22();
		scrollRepair.setRowHeaderView(rowHeaderRepair);	
		modelRepair_total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00), 
				null, null, null, null, new BigDecimal(0.00), null, null });			
	}

	private void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnRefresh.setEnabled(c);
		btnCancel.setEnabled(d);

	}

	private void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		lblProject.setEnabled(true);
		lookupProject.setEnabled(true);
		tagProject.setEnabled(true);	

		lookupProject.setValue("");	
		tagProject.setTag("");	

		KEYBOARD_MANAGER.focusNextComponent();		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		
		lookupCompany.setValue(co_id);
	}
	
	public static String getacct_id() {
		return "select acct_id, acct_name \n" + 
				" from mf_boi_chart_of_accounts \n" + 
				" where acct_id in('01-02-07-002','01-02-07-003')";
	}

	
	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Refresh")){ refresh(); }

		if(e.getActionCommand().equals("Cancel")){ cancel(); }

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==true) { addnew();	}
		else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process change order.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==true) {edit();} //
		else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit change order.","Information",JOptionPane.INFORMATION_MESSAGE); }

	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {

		}
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

	public class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column 	= tblRepair.getSelectedColumn();
				int row 	= tblRepair.getSelectedRow();
				String statusID = "";
				try { statusID 	= modelRepair.getValueAt(row,13).toString().trim(); } catch (NullPointerException e) { statusID = ""; }
				
				if (column==7) {
					menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true); 
					//mniopenPV.setEnabled(true) ;
				}else {
					menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); 
					//mniopenPV.setEnabled(false) ;
				}
				
				if (statusID.equals("ACTIVE")) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniDelete.setEnabled(true);}
				else {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniDelete.setEnabled(false);}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column 	= tblRepair.getSelectedColumn();
				int row 	= tblRepair.getSelectedRow();
				String statusID = "";
				try { statusID 	= modelRepair.getValueAt(row,13).toString().trim(); } catch (NullPointerException e) { statusID = ""; }
				
				if (column==7) {
					menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  
					//mniopenPV.setEnabled(true) ;
				}else {
					menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); 
					//mniopenPV.setEnabled(false) ; 
				}
				
				if (statusID.equals("ACTIVE")) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniDelete.setEnabled(true);} 
				else {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniDelete.setEnabled(false);}
			}
		}
	}

	private void refresh(){

		displayContractChangeOrder(modelRepair, rowHeaderRepair, modelRepair_total);
		enableButtons(true, false, true, true);
		JOptionPane.showMessageDialog(getContentPane(),"Data refreshed.","Information",JOptionPane.INFORMATION_MESSAGE);	

	}

	private void cancel(){
		enable_fields(false);
		refresh_tablesMain();
		refresh_fields();	
		refresh_fields2();
		enableButtons(false, false, false, false);
	}

	private void addnew(){

		refresh_fields_fromSaving();
		to_do = "addnew";

		lblBillingPayeeType.setEnabled(false);
		lookupBillingPayeeType.setEnabled(false);
		tagBillingPayeeType.setEnabled(false);	
		dteRepair.setEnabled(true);

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBilling, "Add New House Repair Billing",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {	
				//selectDate();
			} catch ( java.lang.ArrayIndexOutOfBoundsException ex ) {}		

		} // CLOSED_OPTION	
	}

	private void executeSave(){
		
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			

			if (to_do.equals("addnew")) 
			{
				pgUpdate db = new pgUpdate();
				rplf_no = sql_getNextRPLFno();
				repair_no = sql_getNextRepairNo();
				insertHouseRepairDtl(db, rplf_no);	
				insertBackchargesDtl(db);	
				insertRPLF_header(db, rplf_no);				
				insertRPLF_detail(db, rplf_no);
				
				//insertPV_header(db, rplf_no);
				//insertPV_detail(db, rplf_no);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"New house repair details saved.","Information",JOptionPane.INFORMATION_MESSAGE);	
				displayContractChangeOrder(modelRepair, rowHeaderRepair, modelRepair_total);
				enableButtons(true, false, true, true);

			}
			else if (to_do.equals("update")) 
			{						
				pgUpdate db = new pgUpdate();

				updateHouserepairDtl(db, repair_no);
				updateBackcharges(db, repair_no);	

				updateRPLF_header(db, rplf_no);	
				updateRPLF_detail(rplf_no, db);
				insertRPLF_detail(db, rplf_no);	

				//updatePV_header(db, rplf_no);
				//updatePV_details(rplf_no, db);
				//insertPV_detail(db, rplf_no);

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"House repair details updated.","Information",JOptionPane.INFORMATION_MESSAGE);	
				displayContractChangeOrder(modelRepair, rowHeaderRepair, modelRepair_total);
				enableButtons(true, false, true, true);
			}

		}		
	}

	private void edit(){	

		refresh_fields_fromSaving();
		to_do = "update";
		int x = tblRepair.getSelectedRow();

		String status = "";
		payee_id 	= "";
		payee_name 	= "";
		payee_type_id 	= "";
		payee_type_name 	= "";
		charge_payee_id 	= "";
		charge_payee_name 	= "";
		charge_payee_type_id 	= "";
		charge_payee_type_name 	= "";
		wtax_rate = 0.00;
		rplf_no = "";
		repair_no = "";	
		gr_amt   = 0.00;
		vat_amt  = 0.00;
		wtax_amt = 0.00;
		net_amt  = 0.00;
		charge_amt  = 0.00;
		prem_cost  = 0.00;


		//STATUS
		try { status = modelRepair.getValueAt(x,13).toString().trim(); } catch (NullPointerException e) { status = ""; }

		if (status.equals("ACTIVE"))
		{

			//PAYEE
			try { payee_id = getEntityID(modelRepair.getValueAt(x,8).toString().trim()); } catch (NullPointerException e) { payee_id = ""; }
			try { payee_name = modelRepair.getValueAt(x,8).toString().trim(); } catch (NullPointerException e) { payee_name = ""; }
			try { payee_type_id = getEntityTypeID(modelRepair.getValueAt(x,9).toString().trim()); } catch (NullPointerException e) { payee_type_id = ""; }
			try { payee_type_name = modelRepair.getValueAt(x,9).toString().trim(); } catch (NullPointerException e) { payee_type_name = ""; }
			lookupPayee.setText(payee_id);
			tagPayee.setTag(payee_name);
			lookupBillingPayeeType.setText(payee_type_id);		
			tagBillingPayeeType.setTag(payee_type_name);

			//CHARGED TO
			try { charge_payee_id = getEntityID(modelRepair.getValueAt(x,10).toString().trim()); } catch (NullPointerException e) { charge_payee_id = ""; }
			try { charge_payee_name = modelRepair.getValueAt(x,10).toString().trim(); } catch (NullPointerException e) { charge_payee_name = ""; }
			try { charge_payee_type_id = getEntityTypeID(modelRepair.getValueAt(x,11).toString().trim()); } catch (NullPointerException e) { charge_payee_type_id = ""; }
			try { charge_payee_type_name = modelRepair.getValueAt(x,11).toString().trim(); } catch (NullPointerException e) { charge_payee_type_name = ""; }
			lookupChargeTo.setText(charge_payee_id);
			tagChargePayee.setTag(charge_payee_name);
			lookupChargeToPayeeType.setText(charge_payee_type_id);		
			tagChargePayeeType.setTag(charge_payee_type_name);	

			//AMOUNTS
			gr_amt  = Double.valueOf(modelRepair.getValueAt(x,2).toString().trim()).doubleValue();
			//vat_amt  = Double.valueOf(modelRepair.getValueAt(x,3).toString().trim()).doubleValue();
			//wtax_amt  = Double.valueOf(modelRepair.getValueAt(x,4).toString().trim()).doubleValue();
			prem_cost  = Double.valueOf(modelRepair.getValueAt(x,5).toString().trim()).doubleValue();
			net_amt  = Double.valueOf(modelRepair.getValueAt(x,6).toString().trim()).doubleValue();
			charge_amt  = Double.valueOf(modelRepair.getValueAt(x,12).toString().trim()).doubleValue();
			txtGrossAmt.setText(df.format(gr_amt)); 
			//txtWTaxAmt.setText(df.format(wtax_amt)); 
			txtNetAmt.setText(df.format(gr_amt)); 
			txtChargeAmt.setText(df.format(charge_amt)); 
			txtPremCost.setText(df.format(prem_cost)); 


			txtAcctDesc.setText(modelRepair.getValueAt(x,15).toString().trim()); 
			dteRepair.setEnabled(false);

			try { rplf_no = modelRepair.getValueAt(x,7).toString().trim(); } catch (NullPointerException e) { rplf_no = ""; }
			try { repair_no = modelRepair.getValueAt(x,0).toString().trim(); } catch (NullPointerException e) { repair_no = ""; }

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBilling, "Update House Repair Billing : Repair No. " + repair_no,
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

			if ( scanOption == JOptionPane.CLOSED_OPTION ) {
				try {	
					//selectDate();
				} catch ( java.lang.ArrayIndexOutOfBoundsException ex ) {}		

			} // CLOSED_OPTION	

		}
		else 
		{
			JOptionPane.showMessageDialog(getContentPane(),"Status must be active to allow editing.","STATUS WARNING",JOptionPane.WARNING_MESSAGE);
		}
	}

	private void executeDelete(){
		
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete?", "Delete", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {	
			
			int row 	= tblRepair.getSelectedRow();
			String repairNo = "";
			String rplfNo = "";
			try { repairNo 	= modelRepair.getValueAt(row,0).toString().trim(); } catch (NullPointerException e) { repairNo = ""; }
			try { rplfNo 	= modelRepair.getValueAt(row,7).toString().trim(); } catch (NullPointerException e) { rplfNo = ""; }
			
				pgUpdate db = new pgUpdate();
				deleteHouserepairDtl(db, repairNo, rplfNo);
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"House repair deleted.","Information",JOptionPane.INFORMATION_MESSAGE);	
				displayContractChangeOrder(modelRepair, rowHeaderRepair, modelRepair_total);
				enableButtons(true, false, true, true);			
		}		
	}
	
	
	//select, lookup and get statements	
	private String getUnitList(){    
		return 
		"-- select * from mf_unit_info\r\n" + 
		"\r\n" + 
		"select\r\n" + 
		"\r\n" + 
		"a.pbl_id as \"PBL ID\" ,\r\n" + 
		//"a.description as \"Descripton\" ,\r\n" + 
		"a.phase || '-' || block ||'-'|| lot as \"Descripton\",\r\n" + //Added by erick 2023-07-19 DCRF 2688
		"b.model_desc as \"House Model\" \r\n" + 
		"\r\n" + 
		"from mf_unit_info a\r\n" + 
		"left join mf_product_model b on a.model_id = b.model_id and coalesce(a.server_id,'') = coalesce(b.server_id, '') \r\n" +  //Edited by erick 2023-07-19 DCRF 2688 added server_id as filter
		"left join rf_marketing_scheme_main c on a.sub_proj_id = c.sub_proj_id  and a.proj_id = c.proj_id \r\n" +  //Edited by erick 2023-07-19 DCRF 2688 added proj_id as filter
		"\r\n" + 
		"where a.status_id != 'I'\r\n" + 
		"and c.status_id = 'P'\r\n" +
		"and a.proj_id = '"+proj_id+"' \r\n" + 
		"\r\n" + 
		"order by a.pbl_id::int "  ;

	}

	private Object [] getContractDetails() {			

		String strSQL = 
			"select\r\n" + 
			"\r\n" + 

			"a.ntp_no,\r\n" + 
			"j.contract_no,\r\n" + 			
			"j.entity_id  as contractor_id,\r\n" + 
			"upper(trim(c.entity_name)) as contractor_name,\r\n" + 
			"d.entity_type_id,\r\n" + 
			"trim(e.entity_type_desc) as entity_type_desc,\r\n" + 
			"j.orig_contract_price,\r\n" + 
			"(coalesce(f.dp_paid,0)-coalesce(g.recouped_dp)) as unrecouped_dp,\r\n" + 
			"(coalesce(h.retention,0)-coalesce(i.retention_paid)) as balance_ret\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"from co_ntp_detail a\r\n" + 
			"left join co_ntp_header b on a.ntp_no = b.ntp_no\r\n" + 
			"left join rf_entity c on b.entity_id = c.entity_id\r\n" + 
			"left join rf_entity_assigned_type d on c.entity_id = d.entity_id\r\n" + 
			"left join mf_entity_type e on d.entity_type_id = e.entity_type_id\r\n" + 
			"left join (select distinct on (a.ntp_no) a.ntp_no, sum(a.gross_amt) as dp_paid \r\n" + 
			"	from (select * from co_billing_detail where status_id != 'I') a\r\n" + 
			"	left join rf_pv_header b on a.rplf_no = b.rplf_no\r\n" + 
			"	where a.billing_type = '01'\r\n" + 
			"	and b.cv_no in (select cv_no from rf_cv_header where date_paid is not null)\r\n" + 
			"	group by a.ntp_no\r\n" + 
			"	)  f on a.ntp_no = f.ntp_no\r\n" + 
			"left join (select distinct on (a.ntp_no) a.ntp_no, sum(a.dp_recoupment) as recouped_dp \r\n" + 
			"	from (select * from co_billing_detail where status_id != 'I') a\r\n" + 
			"	left join rf_pv_header b on a.rplf_no = b.rplf_no\r\n" + 
			"	where a.billing_type = '02'\r\n" + 
			"	and b.cv_no in (select cv_no from rf_cv_header where date_paid is not null)\r\n" + 
			"	group by a.ntp_no\r\n" + 
			"	)  g on a.ntp_no = g.ntp_no\r\n" + 
			"left join (select distinct on (a.ntp_no) a.ntp_no, sum(a.retention_amt) as retention \r\n" + 
			"	from (select * from co_billing_detail where status_id != 'I') a\r\n" + 
			"	left join rf_pv_header b on a.rplf_no = b.rplf_no\r\n" + 
			"	where a.billing_type = '02'\r\n" + 
			"	and b.cv_no in (select cv_no from rf_cv_header where date_paid is not null)\r\n" + 
			"	group by a.ntp_no\r\n" + 
			"	)  h on a.ntp_no = h.ntp_no\r\n" + 
			"left join (select distinct on (a.ntp_no) a.ntp_no, sum(a.gross_amt) as retention_paid \r\n" + 
			"	from (select * from co_billing_detail where status_id != 'I') a\r\n" + 
			"	left join rf_pv_header b on a.rplf_no = b.rplf_no\r\n" + 
			"	where a.billing_type = '03'\r\n" + 
			"	and b.cv_no in (select cv_no from rf_cv_header where date_paid is not null)\r\n" + 
			"	group by a.ntp_no\r\n" + 
			"	)  i on a.ntp_no = i.ntp_no\r\n" + 
			"left join (select a.ntp_no, a.contract_no, a.orig_contract_price, a.entity_id, a.status_id \r\n" + 
	        "	from co_ntp_header a\r\n" + 
	        "	left join co_ntp_detail b on b.ntp_no = a.ntp_no\r\n" +  
	        "	where b.pbl_id = '"+pbl_no+"'\r\n" + 
	        "	and a.ntp_type_id = '02'\r\n" + 
	        "	and (case when a.status_id = 'I' then a.is_takeover_ntp ELSE a.status_id = 'A' END)\r\n" + 
	        "	and a.entity_id != '7115114070' \r\n" + 
	        "	AND a.status_id != 'I' \r\n" + 
	        "	order by a.date_created) j on j.ntp_no = b.ntp_no \r\n" + 
			"\r\n" + 
			"where a.pbl_id = '"+pbl_no+"'\r\n" + 
			"and j.status_id = 'A' ";

		System.out.printf("Display : %s", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
		
	}	

	private String sql_getNextRepairNo() {		

		String rep_no = "";
		String SQL = 
			"select trim(to_char(max(coalesce(repair_no::int,0))+1,'000000')) from co_house_repair where co_id = '"+co_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {rep_no = "000001";}
			else {rep_no = (String) db.getResult()[0][0];}			

		}else{
			rep_no = "000001";
		}

		return rep_no;
	}

	private Integer sql_getBackchargesNo() {

		Integer nxt_bc_no = 0;

		String SQL = 
			"select coalesce(rec_id,0) + 1  from co_house_repair_backcharges " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {nxt_bc_no = 1;}
			else {nxt_bc_no = (Integer) db.getResult()[0][0]; }

		}else{
			nxt_bc_no = 1;
		}

		return nxt_bc_no;
	}

	private String getEntityID(String entity_name){

		String entity_id = "";

		String SQL = 
			"select trim(entity_id) from rf_entity where upper(trim(entity_name)) = '"+entity_name+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			entity_id = (String) db.getResult()[0][0];
		}else{
			entity_id = null;
		}

		return entity_id;
	}

	private String getEntityTypeID(String entity_type_name){

		String entity_type_id = "";

		String SQL = 
			"select trim(entity_type_id) from mf_entity_type where upper(trim(entity_type_desc)) = '"+entity_type_name+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			entity_type_id = (String) db.getResult()[0][0];
		}else{
			entity_type_id = null;
		}

		return entity_type_id;
	}

	private String sql_getNextRPLFno() {//EDITED BY JED 2021-05-17 : CHANGE THE GENERATION OF RPLF INSIDE THE FUNCTION		

		String rplf = "";
//		String SQL = 
//			"select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+co_id+"' " ;
		String SQL = 
			"select * from fn_get_rplf_no('"+co_id+"')" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			rplf = (String) db.getResult()[0][0];
		}else{
			rplf = "000000001";
		}

		return rplf;
	}

	private Boolean isEntityVatable(){

		Boolean vatable = false;

		String SQL = 
			"select vat_registered from rf_entity where entity_id = '"+payee_id+"' \r\n"   ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			vatable = (Boolean) db.getResult()[0][0];			
		}else{
			vatable = false;
		}

		return vatable;

	}
	
	

	//table operations	
	private void totalRepair(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal gr_amt_bd 	= new BigDecimal(0.00);	
		//BigDecimal vat_amt_bd 	= new BigDecimal(0.00);	
		//BigDecimal wtax_amt_bd	= new BigDecimal(0.00);	
		BigDecimal prem_cost_bd 	= new BigDecimal(0.00);	
		BigDecimal net_amt_bd 	= new BigDecimal(0.00);	
		BigDecimal chrge_amt_bd 	= new BigDecimal(0.00);	


		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { gr_amt_bd 	= gr_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,2)));} 
			catch (NullPointerException e) { gr_amt_bd 	= gr_amt_bd.add(new BigDecimal(0.00)); }
			

			//try { vat_amt_bd 	= vat_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,3)));} 
			//catch (NullPointerException e) { vat_amt_bd 	= vat_amt_bd.add(new BigDecimal(0.00)); }

			//try { wtax_amt_bd 	= wtax_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			//catch (NullPointerException e) { wtax_amt_bd 	= wtax_amt_bd.add(new BigDecimal(0.00)); }
			
			try { prem_cost_bd 	= prem_cost_bd.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { prem_cost_bd 	= prem_cost_bd.add(new BigDecimal(0.00)); }
			
			try { net_amt_bd 	= net_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,2)));} 
			catch (NullPointerException e) { net_amt_bd 	= net_amt_bd.add(new BigDecimal(0.00)); }

			try { chrge_amt_bd 	= chrge_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,12)));} 
			catch (NullPointerException e) { chrge_amt_bd 	= chrge_amt_bd.add(new BigDecimal(0.00)); }			

		}	

		modelTotal.addRow(new Object[] { "Total", null, gr_amt_bd, null, null, 
											prem_cost_bd, net_amt_bd, null,null,
											null,null,null, chrge_amt_bd  });
	}
//vat_amt_bd, wtax_amt_bd,
	private void adjustRowHeight(){		

		int rw = tblRepair.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblRepair.setRowHeight(x, 22);			
			x++;
		}
	}


	//save and insert
	private void insertHouseRepairDtl(pgUpdate db, String rec) { 

		String sqlDetail = 
			"INSERT into co_house_repair values (" +
			"'"+proj_id+"',  \n" +		//1
			"'"+pbl_no+"',  \n" +		//2
			"'"+repair_no+"',  \n" + 	//3
			"'"+payee_id+"',  \n" + 	//4
			"'"+payee_type_id+"',  \n" + 	//5
			"'"+dteRepair.getDate()+"',  \n" +	//6
			""+txtGrossAmt.getText().trim().replace(",","")+" , \n" +	//7
			"null,  \n" +		//8
			"null,  \n" +			//9
			""+txtNetAmt.getText().trim().replace(",","")+" , \n" +		//10	
			"'"+charge_payee_id+"',  \n" + 		//11
			"'"+charge_payee_type_id+"',  \n" + //12
			"'"+txtAcctDesc.getText().replace("'", "''")+"',  \n" +		//13
			"'"+co_id+"' , \n" +		//14
			"'"+co_id+"' , \n" +		//15
			"'"+rplf_no+"' , \n" +		//16
			"null,  \n" +				//17
			"null,  \n" +				//18
			"'A',  \n" +				//19
			"'"+UserInfo.EmployeeCode+"',  \n" +	//20
			"now(),  \n" +				//21
			"null,  \n" +				//22
			"null,  \n" +				//23	
			"null,  \n" +				//24
			"null,  \n" +					//25
			""+txtPremCost.getText().trim().replace(",","")+"  \n" +	  //26
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void insertBackchargesDtl(pgUpdate db) { 

		String sqlDetail = 
			"INSERT into co_house_repair_backcharges values (" +
			""+sql_getBackchargesNo()+",  \n" +		//1
			"'"+repair_no+"',  \n" +	//2
			"'"+charge_payee_id+"',  \n" + 	//3
			"'"+charge_payee_type_id+"',  \n" +//4
			""+txtChargeAmt.getText().trim().replace(",","")+",  \n" + 	//5
			"'"+txtAcctDesc.getText().replace("'", "''")+"',  \n" +		//6
			"'"+lookupCompany.getValue()+"',\n" +
			"'',\n" +
			"'"+lookupsub_con.getValue()+"' \n" +
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void updateHouserepairDtl(pgUpdate db, String rec) { 

		String sqlDetail = 
			"update co_house_repair set " +
			"payee_id = '"+payee_id+"',  \n" + 	
			"payee_type_id = '"+payee_type_id+"',  \n" + 	
			"amount = "+txtGrossAmt.getText().trim().replace(",","")+" , \n" +	
			"'' , \n" +			
			"'' , \n" +	
			"net_amt = "+txtNetAmt.getText().trim().replace(",","")+" , \n" +		
			"entity_id = '"+charge_payee_id+"',  \n" + 		
			"entity_type_id = '"+charge_payee_type_id+"',  \n" + 
			"remarks = '"+txtAcctDesc.getText().replace("'", "''")+"',  \n" +		
			"co_id = '"+co_id+"' , \n" +		
			"busunit_id = '"+co_id+"' , \n" +		
			"edited_by = '"+UserInfo.EmployeeCode+"',  \n" +	
			"date_edited = now() \n" +
			"where repair_no = '"+rec+"'   \r\n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void updateBackcharges(pgUpdate db, String rec) { 

		String sqlDetail = 
			"update co_house_repair_backcharges set " +
			"entity_id = '"+charge_payee_id+"',  \n" + 	
			"entity_type_id = '"+charge_payee_type_id+"',  \n" + 	
			"amount = "+txtChargeAmt.getText().trim().replace(",","")+" , \n" +	
			"remarks = '"+txtAcctDesc.getText().replace("'", "''")+"'  \n" +		
			"where repair_no = '"+rec+"'   \r\n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void insertRPLF_header(pgUpdate db, String rec){	

		Date date_liq_ext	= null;
		String rplf_type_id = "";
		String sd_no		= "";
		String doc_id		= "";
		Integer proc_id		= null;	
		String branch_id	= "";	
		String justif		= "";	
		String remarks		= "";	
		String status_id	= "";	
		String created_by	= "";	
		String edited_by	= "";	
		Date edited_date	= null;					

		date_liq_ext= null;
		rplf_type_id= "01";
		sd_no		= null;
		doc_id		= "09";
		proc_id		= 1;	
		branch_id	= null;	
		justif		= null;	
		remarks		= "\n" + contract_no + "\n" + txtAcctDesc.getText().replace("'", "''");	
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	= null;				

		String sqlDetail = 
				
			"INSERT into rf_request_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +		//2
			"'"+rec+"',  \n" +		//3
			"'"+dteRepair.getDate()+"',  \n" +	//4
			"'"+dteRepair.getDate()+"',  \n" + //5
			""+date_liq_ext+",  \n" + 	//6
			"'"+rplf_type_id+"' , \n" +	//7
			"'"+payee_id+"',  \n" +		//8
			"'"+charge_payee_id+"',  \n" +		//9
			"'"+payee_type_id+"',  \n" +//10
			""+sd_no+",  \n" +			//11
			"'"+doc_id+"' , \n" +		//12
			""+proc_id+",  \n" +		//13
			""+branch_id+" , \n" +		//14
			""+justif+",  \n" +			//15
			"'"+remarks+"' , \n" +		//16
			"'"+status_id+"' , \n" +	//17
			"'"+created_by+"',  \n" +	//18
			"'"+dteRepair.getDate()+"' , \n" +	//19
			"'"+edited_by+"' , \n" +	//20
			""+edited_date+", \n" +		//21	
			"'B' \n" +					//22
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void updateRPLF_header(pgUpdate db, String rec){	

		String sqlDetail = 
			"update rf_request_header set " +
			"entity_id1 = '"+payee_id+"',  \n" +	
			"entity_type_id = '"+payee_type_id+"',  \n" +	
			"remarks = '"+txtAcctDesc.getText().replace("'", "''")+"' , \n" +	
			"edited_by = '"+UserInfo.EmployeeCode+"' , \n" +	
			"date_edited = now() \n" +				
			"where rplf_no = '"+rec+"'  and co_id = '"+co_id+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void insertRPLF_detail(pgUpdate db, String rec){			

		Integer line_no		= null;
		String ref_doc_id 	= "";
		Date ref_doc_date	= null;
		Boolean	with_budget	= false;
		String part_desc	= "";
		String acct_id		= "";
		String remarks		= "";
		String div_id		= "";
		String dept_id		= "";
		String sec_id		= "";
		String inter_bus_id	= "";
		String inter_co_id	= "";
		String sd_no		= "";
		String asset_no		= "";
		String old_acct_id	= "";		

		line_no		= 1;
		ref_doc_id 	= "105";  // 105 - House Repairs
		ref_doc_date= FncGlobal.dateFormat(FncGlobal.getDateSQL());
		with_budget	= false;
		part_desc	= 
			"House Repair for unit " + pbl_desc + "\n" +
			proj_name + "\n" +
			" by " + contractor_name.replace("'", "''") + "\n" +
			"Backcharged to : " + charge_payee_name;  //???
		System.out.printf("contractor_name :%s",part_desc);
		int x  = 0;	

		acct_id = "01-02-07-002"; //01-02-07-002 - Advances to Contractors - Backcharges		
				//"01-02-07-003"; //Advances to Contractors - Utilities	
		remarks		= null;		
		div_id		= "";
		dept_id		= "";
		sec_id		= "";
		inter_bus_id= "";
		inter_co_id	= "";	
		sd_no		= null;
		asset_no	= null;
		old_acct_id	= null;			

		gr_amt   = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue();
		prem_cost  = Double.valueOf(txtPremCost.getText().trim().replace(",","")).doubleValue();
		//wtax_amt = Double.valueOf(txtWTaxAmt.getText().trim().replace(",","")).doubleValue();
		net_amt  = Double.valueOf(txtNetAmt.getText().trim().replace(",","")).doubleValue();
		charge_amt  = Double.valueOf(txtChargeAmt.getText().trim().replace(",","")).doubleValue();

		Double exp_amt = gr_amt  ;//- vat_amt;

		if (charge_amt<gr_amt) {x = 0;} else {x = 1;}

		String sqlDetail = "";

		while(x <= 1 ) {	

			sqlDetail =
				"INSERT into rf_request_detail values (" +
				"'"+co_id+"',  \n" +  		//1
				"'"+co_id+"',  \n" +		//2
				"'"+rec+"',  \n" +			//3
				""+line_no+",  \n" +		//4
				"'"+ref_doc_id+"',  \n" + 	//5
				"'"+repair_no+"',  \n" +			//6	
				"'"+ref_doc_date+"',  \n" + //7
				""+with_budget+" , \n" +	//8
				"'"+part_desc+"',  \n" +	//9
				"'"+acct_id+"',  \n" +		//10			
				""+remarks+",  \n" +		//11			
				""+gr_amt+",  \n" +			//12			
				"'"+charge_payee_id+"',  \n" +	//13			
				"'"+charge_payee_type_id+"' , \n" +	//14
				"'',  \n" +		//15			
				"'' , \n" +	    //16			
				"'"+div_id+"',  \n" +		//17
				"'"+dept_id+"' , \n" +		//18
				"'"+sec_id+"',  \n" +		//19
				"'"+inter_bus_id+"' , \n" +	//20
				"'"+inter_co_id+"' , \n" +	//21
				"false , \n" +					//22
				"false, \n" +					//23
				"false, \n" +				//24	
				"false, \n" +				//25
				"get_wtaxid_given_entitytype('"+payee_type_id+"') , \n" +	//26
				"null, \n" +	       		//27
				"null, \n" +					//28
				"null, \n" +				//29	
				"0, \n" +					//30		
				"null, \n" +					//31			
				""+exp_amt+", \n" +			//32		
				""+net_amt+", \n" +			//33		
				""+sd_no+", \n" +			//34	
				"'', \n" +					//35
				""+asset_no+", \n" +		//36		
				""+old_acct_id+", \n" +		//37
				"'A', \n" +					//38
				"'"+UserInfo.EmployeeCode+"',  \n" +	//39
				"now(),  \n" +				//40
				"null, \n" +				//41
				"null, \n" +				//42
				"null, \n" +				//43
				"null, \n" +				//42
				"null, \n" +				//43
				""+prem_cost+"  \n" +		//44	
				")   " ;

			x++;
		}

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	private void insertPV_header(pgUpdate db, String rec){		

		String created_by		= UserInfo.EmployeeCode;
		String edited_by		= null;
		Date date_edited		= null;		

		String sqlDetail = 
			"INSERT into rf_pv_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +  		//2			
			"'"+rec+"',  \n" +  		//3
			"'"+co_id+"',  \n" +  		//4
			"'"+co_id+"',  \n" +  		//5	
			"'"+rplf_no+"',  \n" +  	//6
			"'',  \n" +  				//7
			"'"+dteRepair.getDate()+"',  \n" + //8
			"'B',  \n" + 				//9
			"'"+payee_id+"',  \n" + 	//10
			"'',  \n" + 				//11
			"'"+payee_type_id+"',  \n" + //12
			"'',  \n" +		 			//13
			"'"+dteRepair.getDate()+"',  \n" + //14
			"null,  \n" + 				//15
			"null,  \n" + 				//16
			"'',  \n" + 				//17
			"null,  \n" + 				//18
			"'',  \n" +  				//19
			"'12',  \n" +  				//20
			"0,  \n" +  				//21
			"false,  \n" +  			//22
			"false,  \n" +  			//23
			"null,  \n" + 				//24
			"null,  \n" + 				//25
			"'A',  \n" +  				//26
			"'"+created_by+"',  \n" +  	//27
			"'"+dteRepair.getDate()+"',  \n" + //28
			"'"+edited_by+"',  \n" + 	//29
			""+date_edited+"  \n" + 	//30	

			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void updatePV_header(pgUpdate db, String rec){		

		String sqlDetail = 
			"update rf_pv_header set " +
			"entity_id1 = '"+payee_id+"',  \n" + 
			"entity_type_id = '"+payee_type_id+"',  \n" + 
			"remarks = '"+txtAcctDesc.getText()+"' , \n" +	
			"edited_by = '"+UserInfo.EmployeeCode+"' , \n" +	
			"date_edited = '"+Calendar.getInstance().getTime()+"' \n" +	
			"where rplf_no = '"+rec+"'  and co_id = '"+co_id+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void insertPV_detail(pgUpdate db, String rec){	

		gr_amt   = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue();
		prem_cost  = Double.valueOf(txtPremCost.getText().trim().replace(",","")).doubleValue();
		wtax_amt = Double.valueOf(txtWTaxAmt.getText().trim().replace(",","")).doubleValue();
		net_amt  = Double.valueOf(txtNetAmt.getText().trim().replace(",","")).doubleValue();
		charge_amt  = Double.valueOf(txtChargeAmt.getText().trim().replace(",","")).doubleValue();	

		exp_amt = gr_amt - charge_amt;


		int x  = 0;	
		int y  = 1;	
		//                   adv_to_cont-bc    cip_overhead     cip_housing	  acct_payable 
		String acct_id [] = {"01-02-07-002", "01-03-07-000", "01-03-06-001", "03-01-01-001" };		
		String bal_side [] = {"D","D","C","C"};
		Double amount [] = {gr_amt, exp_amt/1.12, prem_cost, net_amt};

		while(x <= 3 ) {				

			if (amount[x]==0){
				
			}else if (amount[x]==Double.valueOf(txtPremCost.getText().trim().replace(",","")).doubleValue() && prem_cost < 0 ){

				String sql = 
					"INSERT into rf_pv_detail values (" +
					"'"+co_id+"',  \n" +  		//1
					"'"+co_id+"',  \n" +  		//2
					"'"+rec+"',  \n" +  		//3			
					""+2+",  \n" +  			//4
					"'01-03-06-001',  \n" +  	//5
					""+prem_cost+",  \n" +  	//6
					"'C',  \n" +  				//8	
					"'',  \n" +  				//9
					"'',  \n" +					//10
					"'',  \n" +  				//11
					"'',  \n" +  				//12
					"'',  \n" +  				//13
					"'',  \n" +  				//14
					"'',  \n" +  				//15	
					"'',  \n" +  				//16
					"'A',  "	+				//17
					"'"+UserInfo.EmployeeCode+"',  \n" +  	//18
					"'"+dteRepair.getDate()+"',  \n" + //19
					"'',  \n" + 				//20
					"null  \n" + 				//21	
					")   " ;
				db.executeUpdate(sql, false);	
				
				String sqlDetail = 
						"INSERT into rf_pv_detail values (" +
						"'"+co_id+"',  \n" +  		//1
						"'"+co_id+"',  \n" +  		//2
						"'"+rec+"',  \n" +  		//3			
						""+y+",  \n" +  			//4
						"'"+acct_id [x]+"',  \n" +  //5
						""+amount[x]+",  \n" +  	//6
						"'"+bal_side[x]+"',  \n" +  //8	
						"'',  \n" +  				//9
						"'',  \n" +					//10
						"'',  \n" +  				//11
						"'',  \n" +  				//12
						"'',  \n" +  				//13
						"'',  \n" +  				//14
						"'',  \n" +  				//15	
						"'',  \n" +  				//16
						"'A',  "	+				//17
						"'"+UserInfo.EmployeeCode+"',  \n" +  	//18
						"'"+dteRepair.getDate()+"',  \n" + //19
						"'',  \n" + 				//20
						"null  \n" + 				//21	
						")   " ;
	
				db.executeUpdate(sqlDetail, false);	
			}else {

				String sqlDetail = 
					"INSERT into rf_pv_detail values (" +
					"'"+co_id+"',  \n" +  		//1
					"'"+co_id+"',  \n" +  		//2
					"'"+rec+"',  \n" +  		//3			
					""+y+",  \n" +  			//4
					"'"+acct_id [x]+"',  \n" +  //5
					""+amount[x]+",  \n" +  	//6
					"'"+bal_side[x]+"',  \n" +  //8	
					"'',  \n" +  				//9
					"'',  \n" +					//10
					"'',  \n" +  				//11
					"'',  \n" +  				//12
					"'',  \n" +  				//13
					"'',  \n" +  				//14
					"'',  \n" +  				//15	
					"'',  \n" +  				//16
					"'A',  "	+				//17
					"'"+UserInfo.EmployeeCode+"',  \n" +  	//18
					"'"+dteRepair.getDate()+"',  \n" + //19
					"'',  \n" + 				//20
					"null  \n" + 				//21	
					")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);	
				db.executeUpdate(sqlDetail, false);	

				y++;
			}
			x++;
		}


	}
	
	/*private void insertPV_detail_premium(pgUpdate db, String rec){	

		gr_amt   = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue();
		prem_cost  = Double.valueOf(txtPremCost.getText().trim().replace(",","")).doubleValue();
		wtax_amt = Double.valueOf(txtWTaxAmt.getText().trim().replace(",","")).doubleValue();
		net_amt  = Double.valueOf(txtNetAmt.getText().trim().replace(",","")).doubleValue();
		charge_amt  = Double.valueOf(txtChargeAmt.getText().trim().replace(",","")).doubleValue();	

		exp_amt = gr_amt - charge_amt;


		int x  = 0;	
		int y  = 1;	
		
		String acct_id  = "01-03-06-001"; //cip_housing	
		String bal_side = "C";
		Double amount = prem_cost;

		while(x <=1 ) {				

			if (amount== prem_cost && prem_cost < 0 ){}
			else {

				String sqlDetail = 
					"INSERT into rf_pv_detail values (" +
					"'"+co_id+"',  \n" +  		//1
					"'"+co_id+"',  \n" +  		//2
					"'"+rec+"',  \n" +  		//3			
					""+y+",  \n" +  			//4
					"'"+acct_id +"',  \n" +  	//5
					""+amount+",  \n" +  		//6
					"'"+bal_side+"',  \n" +		//8	
					"'',  \n" +  				//9
					"'',  \n" +					//10
					"'',  \n" +  				//11
					"'',  \n" +  				//12
					"'',  \n" +  				//13
					"'',  \n" +  				//14
					"'',  \n" +  				//15	
					"'',  \n" +  				//16
					"'A',  "	+				//17
					"'"+UserInfo.EmployeeCode+"',  \n" +  	//18
					"'"+dteRepair.getDate()+"',  \n" +		//19
					"'',  \n" + 				//20
					"null  \n" + 				//21	
					")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);				
				db.executeUpdate(sqlDetail, false);	

				y++;
			}
			x++;
		}


	}
*/

	
	private void updateRPLF_detail(String rplf_no, pgUpdate db) {

		String sqlDetail = 
			"update rf_request_detail " +
			"set status_id = 'I'," +
			"edited_by = '"+UserInfo.EmployeeCode+"'," +
			"date_edited = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"'   " +
			"where trim(rplf_no) = '"+rplf_no+"' " +
			"and co_id = '"+co_id+"' " ;


		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}

	/*private void updatePV_details(String rplf_no, pgUpdate db){	

		String sqlDetail = 
			"update rf_pv_detail set " +
			"status_id = 'I',  \n" +		
			"edited_by = '"+UserInfo.EmployeeCode+"'," +
			"date_edited = '"+Calendar.getInstance().getTime()+"'   " +
			"where pv_no = '"+rplf_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}
*/

	private void deleteHouserepairDtl(pgUpdate db, String rec, String rplf) { 

		String sqlDetail = 
			"update co_house_repair set " +
			"status_id = 'I',  \n" + 	
			"deleted_by = '"+UserInfo.EmployeeCode+"',  \n" +	
			"date_deleted = now() \n" +
			"where repair_no = '"+rec+"'  " +
			"and status_id = 'A' \r\n" ;
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
		
		String sqlDetail2 = 
			"update rf_request_header " +
			"set status_id = 'I'  \n" + 
			"where rplf_no = '"+rplf+"'  " +
			"and status_id = 'A' \r\n" ;
		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);	
		
		String sqlDetail3 = 
			"update rf_pv_header " +
			"set status_id = 'I'  \n" + 
			"where pv_no = '"+rplf+"'  " +
			"and status_id = 'A' \r\n" ;
		System.out.printf("SQL #3: %s", sqlDetail3);
		db.executeUpdate(sqlDetail3, false);
		
	}
	
	
	//Right-click operations	
	public void openDRF(){

		RequestForPayment drf = new RequestForPayment();
		Home_JSystem.addWindow(drf);

		if(co_id.equals(""))
		{

		} 
		else 
		{			
			RequestForPayment.co_id 		= co_id;	
			RequestForPayment.company		= company;	
			RequestForPayment.lookupCompany.setValue(co_id);
			RequestForPayment.tagCompany.setTag(company);
			RequestForPayment.company_logo = company_logo;

			RequestForPayment.lblDRF_no.setEnabled(true);	
			RequestForPayment.lookupDRF_no.setEnabled(true);	
			RequestForPayment.lookupDRF_no.setLookupSQL(RequestForPayment.getDRF_no());

			RequestForPayment.btnAddNew.setEnabled(true);
			RequestForPayment.btnCancel.setEnabled(true);

			int column 	= tblRepair.getSelectedColumn();
			int row 	= tblRepair.getSelectedRow();
			String rplf = modelRepair.getValueAt(row,column).toString().trim();

			if (rplf.equals("")) {}
			else {			
				RequestForPayment.drf_no  = rplf;
				RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);	

				RequestForPayment.setDRF_header(RequestForPayment.drf_no);
				RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF, RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no );	
				RequestForPayment.btnAddNew.setEnabled(false);
				RequestForPayment.btnRefresh.setEnabled(true);
				RequestForPayment.btnPreview.setEnabled(true);
				if(RequestForPayment.isPVcreated()==true) 
				{RequestForPayment.btnEdit.setEnabled(false); RequestForPayment.mnisetupPV.setEnabled(false);} 
				else {RequestForPayment.btnEdit.setEnabled(true); RequestForPayment.mnisetupPV.setEnabled(true);}
				RequestForPayment.mnidelete.setEnabled(false);
				RequestForPayment.mniaddrow.setEnabled(false);
			}
		}		
	}

	public void openPV(){

		PayableVoucher pv = new PayableVoucher();
		Home_JSystem.addWindow(pv);

		if(co_id.equals(""))
		{

		} 
		else 
		{
			PayableVoucher.co_id 		= co_id;	
			PayableVoucher.company		= company;	
			PayableVoucher.lookupCompany.setValue(co_id);
			PayableVoucher.tagCompany.setTag(company);
			PayableVoucher.company_logo = company_logo;
			PayableVoucher.btnAddNew.setEnabled(true);
			PayableVoucher.btnCancel.setEnabled(true);

			PayableVoucher.lblPV_no.setEnabled(true);	
			PayableVoucher.lookupPV_no.setEnabled(true);	
			//PayableVoucher.lookupPV_no.setEditable(true);	
			PayableVoucher.lookupPV_no.setLookupSQL(PayableVoucher.getPV_no());	
			PayableVoucher.lookupDRF_no.setLookupSQL(PayableVoucher.getDRF_no());

			int column 	= tblRepair.getSelectedColumn();
			int row 	= tblRepair.getSelectedRow();
			String rplf = modelRepair.getValueAt(row,column).toString().trim();

			if(!rplf.equals(""))			{

				PayableVoucher.refresh_fields();							

				PayableVoucher.pv_no = rplf;	
				PayableVoucher.lookupPV_no.setValue(rplf);

				PayableVoucher.setPV_header(rplf);
				PayableVoucher.displayPV_details(PayableVoucher.modelPV_account,PayableVoucher.rowHeaderPV_account, PayableVoucher.modelPV_accounttotal, rplf );	
				PayableVoucher.displayPV_subsidiaryledgers(PayableVoucher.modelPV_SL, PayableVoucher.rowHeaderPV_SL, PayableVoucher.modelPV_SL_total, rplf );	
				PayableVoucher.btnAddNew.setEnabled(false);
				PayableVoucher.btnRefresh.setEnabled(true);

				//set particulars							
				Integer w = 1;
				Integer line_count = PayableVoucher.sql_getDRF_line_count(rplf);
				String part_desc;
				String particulars = "";
				
				//COMMENTED BY LESTER 2023-05-25 FOR REPEAT DISPLAY OF REMARKS
				
//				while (w<=line_count){	
//					part_desc = PayableVoucher.getDRF_particulars(w, rplf).toString().trim();
//					particulars = ""+particulars+"  "+part_desc+"  \n" ;
//
//					w++; 
//				}
//				PayableVoucher.txtDRFRemark.setText(particulars);	

				if (PayableVoucher.getPV_status(rplf).equals("P")&&PayableVoucher.lookupPV_no.isEnabled()) 
				{								
					PayableVoucher.btnSave.setText("Post");  
					PayableVoucher.btnSave.setActionCommand("Post");		
					PayableVoucher.enable_buttons(false, false, false, true, true, true);
					PayableVoucher.mniInactivate.setEnabled(false);
				} 
				else if (PayableVoucher.getPV_status(rplf).equals("F")) 
				{								
					PayableVoucher.btnSave.setText("Post");  
					PayableVoucher.btnSave.setActionCommand("Post");
					PayableVoucher.enable_buttons(false, false, true, true, true, true);
					PayableVoucher.mniInactivate.setEnabled(false);
				}
				else if (PayableVoucher.getPV_status(rplf).equals("A")) 
				{								
					PayableVoucher.btnSave.setText("Tag");  
					PayableVoucher.btnSave.setActionCommand("Tag");
					PayableVoucher.enable_buttons(false, true, true, true, true, true);
					PayableVoucher.mniInactivate.setEnabled(true);
				}
				PayableVoucher.modelPV_account.setEditable(false);
				PayableVoucher.tblPV_account.setEditable(false);
				PayableVoucher.btnPreview.setEnabled(true);				
			}
		}
	}


	
	//computation
	private void computeRepairAmt(){
		
	/*	if (isEntityVatable()==true){vat_rate = 0.12;}
		else {vat_rate = 0.00;}*/
		
		Double gr_amt  = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue();
		Double prem_cost  = Double.valueOf(txtPremCost.getText().trim().replace(",","")).doubleValue();
		//Double vat_amt = (gr_amt / (1+vat_rate)* vat_rate) ;
		//Double wtax_amt= (gr_amt / (1+vat_rate))* wtax_rate/100 ;
		Double net_amt =  gr_amt-prem_cost;
		Double chDouble = gr_amt;

		//txtPremCost.setText(df.format(prem_cost));	
		txtChargeAmt.setText(df.format(chDouble));
		txtNetAmt.setText(df.format(net_amt));
		txtGrossAmt.setText(df.format(gr_amt));
		
	}


}
