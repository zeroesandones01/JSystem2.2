package Accounting.ContractorsPayment;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelContractorChangeOrder;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Accounting.GeneralLedger.JournalVoucher;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncDate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class ChangeOrder extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Change Order";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlBillingTable;
	private JPanel pnlSouth;
	private JPanel pnlChangeOrderDtl_1;
	private JPanel pnlChangeOrderDtl_2;
	private JPanel pnlChangeOrderDtl_1a;
	private JPanel pnlChangeOrderDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlChangeOrderDtl_3;
	private JPanel pnlChangeOrderDtl_3a;
	private JPanel pnlSurety;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlChangeOrder;
	private JPanel pnlChangeOrder_a;
	private JPanel pnlChangeOrder_a1;
	private JPanel pnlChangeOrder_a2;
	private JPanel pnlChangeOrder_contprice;
	private JPanel pnlChangeOrder_lblcontractprice;
	private JPanel pnlChangeOrder_txtcontractprice;
	private JPanel pnlChangeOrder_Type;
	private JPanel pnlChangeOrder_lblType;
	private JPanel pnlChangeOrder_cmbType;
	private JPanel pnlChangeOrder_Date;
	private JPanel pnlChangeOrder_lblDate;
	private JPanel pnlChangeOrder_datechooser;
	
	//private JPanel pnlChangeOrder_lbltype;
	private JPanel pnlChangeOrder_cmbtype;
	private JPanel pnlChangeOrder_a2_1;
	private JPanel pnlChangeOrder_a2_2;
	private JPanel pnlTB_CO;
	private JPanel pnlChangeOrderDtl_3b;	
	private JPanel pnlComp_b;
	private JPanel pnlComp_b1;
	private JPanel pnlComp_b2;
	private JPanel pnlComp_b2a;
	private JPanel pnlComp_b2b;
	private JPanel pnlChangeOrderDtl;
	private JPanel pnlChangeOrder_ins;
	private JPanel pnlChangeOrder_a2_3;
	private JPanel pnlChangeOrderInfo;
	private JPanel pnlEditAmount;

	private JLabel lblCompany;
	private JLabel lblContractor;
	private JLabel lblEntityType;
	private JLabel lblPercentage;
	
	
	private _JTagLabel tagContractor;
	private _JTagLabel tagEntityType;	
	//private _JTagLabel tagRPLF;	
	//private _JTagLabel tagPercentage;
	
	private JLabel lblCont_NTP;
	private JLabel lblcontractprice;
	private JLabel lblChangeOrderNo;
	private JLabel lblChangeOrderAmt;
	//private JLabel lblChangeOrderDPRecoup;
	private JLabel lblCO_vat_amt;
	private JLabel lblCO_wtax_amt;
	private JLabel lblCO_ret_amt;
	private JLabel lblCO_net_amt;
	private JLabel lblTranType;
	private _JTagLabel tagTranType;
	private JLabel lblType;
	private JLabel lblDate;
	private JLabel lblPrevPercentage;
	private JLabel lblEditAmount;
	private JLabel lblPrevPerc;
	private JLabel lblEditPerc;

	private _JScrollPaneMain scrollCO;
	private _JScrollPaneTotal scrollCO_total;
	private JScrollPane scpJVRemark;

	private JTextArea txtJVRemark;

	private modelContractorChangeOrder modelCO;
	private modelContractorChangeOrder modelCO_total;
	private _JTableTotal tblCO_total;	
	private _JTableMain tblCO;
	private JList rowHeaderCo;	

	private static _JLookup lookupCompany;
	private _JLookup lookupEntity;	
	private _JLookup lookupTransaction;	
	private static _JLookup lookupContNTP;
	//private _JLookup lookupChangeOrderNo;
	private _JLookup lookupContractor;

	private _JTagLabel tagCompany;
	private _JTagLabel tagNTPno;	

	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnAddNew;
	private JButton btnRefresh;
	private JButton btnOK;

	private JComboBox cmbType;	

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dteChangeOrder;


	private JMenuItem mnieditperc;


	private _JXFormattedTextField txtGrossAmt;
	private _JXFormattedTextField txtCO_vat_amt;
	private _JXFormattedTextField txtCO_wtax_amt;
	private _JXFormattedTextField txtCO_ret_amt;
	private _JXFormattedTextField txtCO_net_amt;
	private _JXFormattedTextField txtEditAmount;
	
	private _JXFormattedTextField txtPrevPerc;
	private _JXFormattedTextField txtEditPerc;

	private _JXFormattedTextField txtcontprice;
	private _JXFormattedTextField txtPrevPercentage;
	private _JXFormattedTextField txtPercentage;



	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	String ntp_no 		= "";
	String contract_no 	= "";
	String company 		= "";
	Integer change_order_seq_no = 0;	
	public static String company_logo;

	String rplf_no = "";
	String jv_no = "";
	String contr_name = "";
	String part_desc = "";
	Integer next_seqno = 0;
	String billing_date = "" ;
	Date billing_dte = FncGlobal.dateFormat(FncGlobal.getDateSQL());
	Double accomp_perc, gr_amt, vat_amt, wtx_amt, dpr_amt, ret_amt, bc_amt, od_amt, net_amt, exp_amt, perc, contprice;
	private JPopupMenu menu2;
	private JMenuItem mniopenContBilling;
	String surety = "";
	private JPopupMenu menu;
	private JMenuItem mniopenRPLF;
	private JMenuItem mniopenPV;
	private JXTextField txtChangeOrderNo;
	String change_order_no = "";
	private JButton btnEdit;
	Double wtax_rate = 0.00;
	String to_do = "save";  //to distinguish saving from updating
	private Integer rec_id;
	public static String co_id;



	public ChangeOrder() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ChangeOrder(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ChangeOrder(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(945, 538));
		this.setBounds(0, 0, 950, 538);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1144, 645));

		{
						
			menu2 = new JPopupMenu("Popup");
			mniopenContBilling = new JMenuItem("Open Contractors Billing");
			mnieditperc= new JMenuItem("Enter Percentage");	
			menu2.add(mniopenContBilling);
			menu2.add(mnieditperc);

			mniopenContBilling.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openContBilling();				
				}
			});	
			
			mnieditperc.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					//modelProgBillMain.setEditable(true);		
					editAmount();
				}
			});

		}
		{
			menu = new JPopupMenu("Popup");
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenPV = new JMenuItem("Open Payable Voucher");	
			menu.add(mniopenRPLF);
			menu.add(mniopenPV);

			mniopenRPLF.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openDRF();				
				}
			});
			mniopenPV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					openPV();				
				}
			});

		}


		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 299));				

			pnlComp = new JPanel(new BorderLayout(5, 0));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(921, 61));	

			//company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
			pnlComp_a.setPreferredSize(new java.awt.Dimension(610, 30));	
			pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));			

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
				//lookupCompany.setReturnColumn(0);
				lookupCompany.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							co_id 		= (String) data[0];	
							company     = (String) data[1];						
							tagCompany.setTag(company);
							
							lookupCompany.setValue((String) data[0]); 
							System.out.println("Company: "+co_id);
							KEYBOARD_MANAGER.focusNextComponent();		
							//lookupContNTP.setLookupSQL(getLookupContractNo());
							lookupContNTP.setLookupSQL(getLookupContractNo(co_id));
							lookupContNTP.setEnabled(true);
						}
					}
				});
			}			

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

			//
			pnlComp_b = new JPanel(new BorderLayout(5, 0));
			pnlComp.add(pnlComp_b, BorderLayout.CENTER);	
			pnlComp_b.setPreferredSize(new java.awt.Dimension(921, 30));	
			pnlComp_b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			pnlComp_b1 = new JPanel(new GridLayout(1, 1, 0, 0));
			pnlComp_b.add(pnlComp_b1, BorderLayout.WEST);	
			pnlComp_b1.setPreferredSize(new java.awt.Dimension(101, 31));
			pnlComp_b1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				lblCont_NTP = new JLabel("Contract/NTP No.", JLabel.TRAILING);
				pnlComp_b1.add(lblCont_NTP);
				lblCont_NTP.setBounds(8, 11, 62, 12);
				lblCont_NTP.setPreferredSize(new java.awt.Dimension(96, 26));
				lblCont_NTP.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
			}

			pnlComp_b2 = new JPanel(new BorderLayout(5, 5));
			pnlComp_b.add(pnlComp_b2, BorderLayout.CENTER);	
			pnlComp_b2.setPreferredSize(new java.awt.Dimension(492, 31));	
			pnlComp_b2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

			pnlComp_b2a = new JPanel(new GridLayout(1, 1, 0, 0));
			pnlComp_b2.add(pnlComp_b2a, BorderLayout.WEST);	
			pnlComp_b2a.setPreferredSize(new java.awt.Dimension(181, 21));	
			pnlComp_b2a.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

			{
				lookupContNTP = new _JLookup(null, "Contract No.",0,2);
				pnlComp_b2a.add(lookupContNTP);
				//lookupContNTP.setLookupSQL(SQL_COMPANY());.
				
				lookupContNTP.setReturnColumn(0);
				lookupContNTP.setEnabled(false);
				lookupContNTP.addLookupListener(new LookupListener() {
					

					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							contract_no=(String) data[0];	
							ntp_no 	= (String) data[1];				
							tagNTPno.setTag(ntp_no);
							 //Double cont_no = (Double.valueOf(data[7].toString())); 
							//txtRPLF.setText((String) data[4]);
							//tagRPLF.setText("[ "+(String) data[5]+" ]");
							lblChangeOrderNo.setEnabled(true);	
							txtChangeOrderNo.setEnabled(true);	

							//lookupChangeOrderNo.setLookupSQL(getChangeOrderNo());
							displayContractChangeOrder(modelCO, rowHeaderCo, modelCO_total);
							refresh_fields();

							surety = (String) data[6];
							//txtcontprice.setText(df.format(cont_no));

							enableButtons(true, false, false, true, true);
						}
					}
				});
			}	
			pnlComp_b2b = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlComp_b2.add(pnlComp_b2b, BorderLayout.CENTER);	

			{
				tagNTPno = new _JTagLabel("[ ]");
				pnlComp_b2.add(tagNTPno);
				tagNTPno.setBounds(209, 27, 700, 22);
				tagNTPno.setEnabled(true);	
				tagNTPno.setPreferredSize(new java.awt.Dimension(27, 33));
				tagNTPno.addMouseListener(new PopupTriggerListener_panel2());
			}

			pnlChangeOrder = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlChangeOrder, BorderLayout.CENTER);				
			pnlChangeOrder.setPreferredSize(new java.awt.Dimension(921, 162));
			pnlChangeOrder.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlChangeOrder.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlChangeOrder_a = new JPanel(new BorderLayout(5, 5));
			pnlChangeOrder.add(pnlChangeOrder_a, BorderLayout.NORTH);	
			pnlChangeOrder_a.setPreferredSize(new java.awt.Dimension(930, 42));
			pnlChangeOrder_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlChangeOrder_a.setBorder(lineBorder);

			pnlChangeOrder_a1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlChangeOrder_a.add(pnlChangeOrder_a1, BorderLayout.WEST);	
			pnlChangeOrder_a1.setPreferredSize(new java.awt.Dimension(120, 36));	
			pnlChangeOrder_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

			{
				lblChangeOrderNo = new JLabel("Change Order No.", JLabel.CENTER);
				pnlChangeOrder_a1.add(lblChangeOrderNo);
				lblChangeOrderNo.setEnabled(false);	
				lblChangeOrderNo.setPreferredSize(new java.awt.Dimension(108, 36));
				lblChangeOrderNo.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
			}						

			pnlChangeOrder_a2 = new JPanel(new BorderLayout(5, 5));
			pnlChangeOrder_a.add(pnlChangeOrder_a2, BorderLayout.CENTER);	
			pnlChangeOrder_a2.setPreferredSize(new java.awt.Dimension(765, 36));	
			pnlChangeOrder_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			pnlChangeOrder_a2_1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlChangeOrder_a2.add(pnlChangeOrder_a2_1, BorderLayout.WEST);	
			pnlChangeOrder_a2_1.setPreferredSize(new java.awt.Dimension(180, 22));					

			{
				txtChangeOrderNo = new JXTextField("");
				pnlChangeOrder_a2_1.add(txtChangeOrderNo);
				txtChangeOrderNo.setEnabled(false);	
				txtChangeOrderNo.setEditable(true);	
				txtChangeOrderNo.setHorizontalAlignment(JTextField.CENTER);				
			}	

			pnlChangeOrder_a2_2 = new JPanel(new BorderLayout(5, 5));
			pnlChangeOrder_a2.add(pnlChangeOrder_a2_2, BorderLayout.CENTER);	
			pnlChangeOrder_a2_2.setPreferredSize(new java.awt.Dimension(320, 25));
			//pnlChangeOrder_a2_2.setBorder(BorderFactory.createLineBorder(Color.green));
			
			pnlChangeOrder_contprice = new JPanel(new BorderLayout(0, 0));
			pnlChangeOrder_a2_2.add(pnlChangeOrder_contprice, BorderLayout.CENTER);	
			pnlChangeOrder_contprice.setPreferredSize(new java.awt.Dimension(250, 36));	
			
			pnlChangeOrder_lblcontractprice = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlChangeOrder_contprice.add(pnlChangeOrder_lblcontractprice, BorderLayout.WEST);	
			pnlChangeOrder_lblcontractprice.setPreferredSize(new java.awt.Dimension(100, 36));	
				{
					lblcontractprice = new JLabel("Contract Price", JLabel.CENTER);
					pnlChangeOrder_lblcontractprice.add(lblcontractprice);
					lblcontractprice.setEnabled(false);
					lblcontractprice.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				}
			pnlChangeOrder_txtcontractprice = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlChangeOrder_contprice.add(pnlChangeOrder_txtcontractprice, BorderLayout.CENTER);	
				{
					txtcontprice = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrder_txtcontractprice.add(txtcontprice);
					txtcontprice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtcontprice.setText("0.00");
					txtcontprice.setEnabled(false);	
					txtcontprice.setEditable(false);
					
				}

			pnlChangeOrder_cmbtype = new JPanel(new BorderLayout(5, 5));
			pnlChangeOrder_a2_2.add(pnlChangeOrder_cmbtype, BorderLayout.EAST);	
			pnlChangeOrder_cmbtype.setPreferredSize(new java.awt.Dimension(150, 22));
			//pnlChangeOrder_cmbtype.setBorder(BorderFactory.createLineBorder(Color.cyan));
			
			pnlChangeOrder_Type = new JPanel(new BorderLayout(0, 0));
			pnlChangeOrder_cmbtype.add(pnlChangeOrder_Type, BorderLayout.CENTER);	
			pnlChangeOrder_Type.setPreferredSize(new java.awt.Dimension(150, 36));	
			
			pnlChangeOrder_lblType = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlChangeOrder_Type.add(pnlChangeOrder_lblType, BorderLayout.WEST);	
			pnlChangeOrder_lblType.setPreferredSize(new java.awt.Dimension(45, 36));	
				{
					lblType = new JLabel("Type", JLabel.CENTER);
					pnlChangeOrder_lblType.add(lblType);
					lblType.setEnabled(false);
					lblType.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				}
			pnlChangeOrder_cmbType = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlChangeOrder_Type.add(pnlChangeOrder_cmbType, BorderLayout.CENTER);		
				{
					String status[] = {"Additive(+)","Deductive(-)"};					
					cmbType = new JComboBox(status);
					pnlChangeOrder_cmbType.add(cmbType);
					cmbType.setSelectedItem(null);
					cmbType.setEnabled(false);
					cmbType.setSelectedIndex(0);	
					cmbType.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
	
						}
					});		
				}

			pnlChangeOrder_a2_3 = new JPanel(new BorderLayout(5, 5));
			pnlChangeOrder_a2.add(pnlChangeOrder_a2_3, BorderLayout.EAST);	
			pnlChangeOrder_a2_3.setPreferredSize(new java.awt.Dimension(158, 20));	
			
			pnlChangeOrder_Date = new JPanel(new BorderLayout(0, 0));
			pnlChangeOrder_a2_3.add(pnlChangeOrder_Date, BorderLayout.CENTER);	
			pnlChangeOrder_Date.setPreferredSize(new java.awt.Dimension(158, 36));	
			
			pnlChangeOrder_lblDate = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlChangeOrder_Date.add(pnlChangeOrder_lblDate, BorderLayout.WEST);	
			pnlChangeOrder_lblDate.setPreferredSize(new java.awt.Dimension(40, 36));	
			//pnlChangeOrder_lblDate.setBorder(BorderFactory.createLineBorder(Color.green));	
				{
					lblDate = new JLabel("Date", JLabel.CENTER);
					pnlChangeOrder_lblDate.add(lblDate);
					lblDate.setEnabled(false);
					lblDate.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				}
			pnlChangeOrder_datechooser = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlChangeOrder_Date.add(pnlChangeOrder_datechooser, BorderLayout.CENTER);	
				{
					dteChangeOrder = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlChangeOrder_datechooser.add(dteChangeOrder);
					dteChangeOrder.setDate(null);
					dteChangeOrder.setEnabled(false);
					dteChangeOrder.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteChangeOrder.getDateEditor()).setEditable(false);
					dteChangeOrder.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteChangeOrder.addPropertyChangeListener( new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
						}
					});	
				}
			{
				pnlChangeOrderDtl = new JPanel(new BorderLayout(0, 5));
				pnlChangeOrder.add(pnlChangeOrderDtl, BorderLayout.WEST);	
				pnlChangeOrderDtl.setPreferredSize(new java.awt.Dimension(921, 187));

				pnlChangeOrderDtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlChangeOrderDtl.add(pnlChangeOrderDtl_1, BorderLayout.WEST);	
				pnlChangeOrderDtl_1.setPreferredSize(new java.awt.Dimension(217, 135));
				pnlChangeOrderDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

				pnlChangeOrderDtl_1a = new JPanel(new GridLayout(6, 1, 0, 5));
				pnlChangeOrderDtl_1.add(pnlChangeOrderDtl_1a, BorderLayout.WEST);	
				pnlChangeOrderDtl_1a.setPreferredSize(new java.awt.Dimension(107, 126));
				pnlChangeOrderDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));							

				{
					lblContractor = new JLabel("Contractor", JLabel.TRAILING);
					pnlChangeOrderDtl_1a.add(lblContractor);
					lblContractor.setEnabled(false);	
				}	
				{
					lblEntityType = new JLabel("Entity Type", JLabel.TRAILING);
					pnlChangeOrderDtl_1a.add(lblEntityType);
					lblEntityType.setEnabled(false);	
				}
				{
					lblTranType = new JLabel("Transaction", JLabel.TRAILING);
					pnlChangeOrderDtl_1a.add(lblTranType);
					lblTranType.setEnabled(false);	
				}
				{
					lblPrevPercentage = new JLabel("Prev.Accomp.%", JLabel.TRAILING);
					pnlChangeOrderDtl_1a.add(lblPrevPercentage);
					lblPrevPercentage.setEnabled(false);	
				}
				{
					lblPercentage = new JLabel("Accomp.%", JLabel.TRAILING);
					pnlChangeOrderDtl_1a.add(lblPercentage);
					lblPercentage.setEnabled(false);	
				}
				{
					lblChangeOrderAmt = new JLabel("Gross Amt.", JLabel.TRAILING);
					pnlChangeOrderDtl_1a.add(lblChangeOrderAmt);
					lblChangeOrderAmt.setEnabled(false);	
				}	
				/*{
					lblChangeOrderDPRecoup = new JLabel("DP Recoup.", JLabel.TRAILING);
					pnlChangeOrderDtl_1a.add(lblChangeOrderDPRecoup);
					lblChangeOrderDPRecoup.setEnabled(false);	
				}	*/

				pnlChangeOrderDtl_1b = new JPanel(new GridLayout(6, 1, 5, 5));
				pnlChangeOrderDtl_1.add(pnlChangeOrderDtl_1b, BorderLayout.CENTER);	
				pnlChangeOrderDtl_1b.setPreferredSize(new java.awt.Dimension(240, 125));
				pnlChangeOrderDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					lookupContractor = new _JLookup(null, "Contract", 2, 2);
					pnlChangeOrderDtl_1b.add(lookupContractor);
					lookupContractor.setBounds(30, 27, 20, 25);
					lookupContractor.setReturnColumn(0);
					lookupContractor.setFilterName(true);
					lookupContractor.setEnabled(false);	
					lookupContractor.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupContractor.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){	
								contr_name 	= (String) data[1];		
								tagContractor.setText("[ "+ contr_name+" ]");		
								lblEntityType.setEnabled(true);	
								lookupEntity.setEnabled(true);	
								tagEntityType.setEnabled(true);	
								lookupEntity.setLookupSQL(getEntity_type((String) data[0]));
							}
						}
					});	
				}	
				{
					lookupEntity = new _JLookup(null, "Entity", 2, 2);
					pnlChangeOrderDtl_1b.add(lookupEntity);
					lookupEntity.setBounds(30, 27, 20, 25);
					lookupEntity.setReturnColumn(0);
					lookupEntity.setFilterName(true);
					lookupEntity.setEnabled(false);	
					lookupEntity.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupEntity.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								String entity_type 	= (String) data[1];		
								wtax_rate = Double.parseDouble(data[2].toString());
								tagEntityType.setText("[ "+ entity_type +" ]"+"[ "+wtax_rate+" ]");		
								
								Double contprice = Double.valueOf(txtEditAmount.getText().trim().replace(",","")).doubleValue();
								Double prev = Double.valueOf(txtPrevPerc.getText().trim().replace(",","")).doubleValue();
								Double perc = Double.valueOf(txtEditPerc.getText().trim().replace(",","")).doubleValue();
								Double accomp = (perc-prev)/100;
								Double dp_amt  = (0.00);
								Double wtax_amt= Double.valueOf(txtCO_wtax_amt.getText().trim().replace(",","")).doubleValue();
								//Double ret_amt = Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")).doubleValue();
								
								if(lookupTransaction.getText().equals("02"))
								{	
									lblcontractprice.setEnabled(false);	
									txtcontprice.setEnabled(false);
									txtcontprice.setEditable(true);
									txtcontprice.setValue(contprice);
									
									lblPercentage.setEnabled(false);	
									txtPercentage.setEnabled(false);
									txtPercentage.setEditable(false);
									txtPercentage.setText("0.00");
									
									txtPrevPercentage.setEnabled(false);	
									txtCO_vat_amt.setEnabled(false);	
									txtCO_wtax_amt.setEnabled(false);	
									txtCO_ret_amt.setEnabled(false);
									
									Double gr_amt  = (contprice*accomp);
									Double ret_amt  = (gr_amt*.10);
									Double net_amt =  gr_amt-wtax_amt-ret_amt;

									txtCO_net_amt.setText(df.format(net_amt));	
									computeNetAmount(entity_type, wtax_rate);
								}
								else 
								{
														
									lblcontractprice.setEnabled(false);	
									txtcontprice.setEnabled(false);
									txtcontprice.setEditable(false);
									txtcontprice.setValue(contprice);
									
									lblPercentage.setEnabled(false);	
									txtPercentage.setEnabled(false);
									txtPercentage.setEditable(false);
									
									txtPrevPercentage.setEnabled(false);	
									txtCO_vat_amt.setEnabled(false);	
									txtCO_wtax_amt.setEnabled(false);	
									txtCO_ret_amt.setEnabled(false);
									
									txtPercentage.setText("0.00");
									txtPrevPercentage.setText("0.00");
									txtCO_vat_amt.setText("0.00");
									txtCO_wtax_amt.setText("0.00");
									txtCO_ret_amt.setText("0.00");
									
									Double gr_amt  = (((contprice)*.1)/accomp);
									txtCO_net_amt.setText(df.format(gr_amt));
								}
								
								
							}
						}
					});	
				}	
				{
					lookupTransaction = new _JLookup(null, "Entity", 2, 2);
					pnlChangeOrderDtl_1b.add(lookupTransaction);
					lookupTransaction.setBounds(30, 27, 20, 25);
					lookupTransaction.setReturnColumn(0);
					lookupTransaction.setEnabled(false);	
					lookupTransaction.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupTransaction.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){								
								String tran_type 	= (String) data[1];		
								String tran_type_id	= (String) data[0];
								tagTranType.setText("[ "+ tran_type+" ]");	
								
								editAmount();
								Double contprice = Double.valueOf(txtEditAmount.getText().trim().replace(",","")).doubleValue();
								Double prev = Double.valueOf(txtPrevPerc.getText().trim().replace(",","")).doubleValue();
								Double perc = Double.valueOf(txtEditPerc.getText().trim().replace(",","")).doubleValue();
								Double accomp = (perc-prev)/100;
								Double dp_amt  = (0.00);
								Double wtax_amt= Double.valueOf(txtCO_wtax_amt.getText().trim().replace(",","")).doubleValue();
								Double ret_amt = Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")).doubleValue();

								if(tran_type_id.equals("03")) {
									
									lblcontractprice.setEnabled(true);	
									txtcontprice.setEnabled(true);
									txtcontprice.setEditable(false);
									txtcontprice.setValue(contprice);
									
									
									lblPercentage.setEnabled(true);	
									txtPercentage.setEnabled(true);
									txtPercentage.setEditable(false);
									txtPercentage.setValue(perc);
									
									lblPrevPercentage.setEnabled(true);	
									txtPrevPercentage.setEnabled(true);
									txtPrevPercentage.setEditable(false);
									txtPrevPercentage.setValue(prev);
									
									txtCO_vat_amt.setEnabled(false);	
									txtCO_wtax_amt.setEnabled(false);	
									txtCO_ret_amt.setEnabled(false);
									
									txtCO_vat_amt.setText("0.00");
									txtCO_wtax_amt.setText("0.00");
									txtCO_ret_amt.setText("0.00");
									
									Double gr_amt  = (((contprice)*.1)/accomp);
									Double net_amt =  gr_amt-wtax_amt-ret_amt;

									txtGrossAmt.setText(df.format(gr_amt));
									txtCO_net_amt.setText(df.format(net_amt));
									
								}
								else {
									lblcontractprice.setEnabled(true);	
									txtcontprice.setEnabled(true);
									txtcontprice.setEditable(true);
									txtcontprice.setValue(contprice);
									
									lblPercentage.setEnabled(true);	
									txtPercentage.setEnabled(true);
									txtPercentage.setEditable(false);
									txtPercentage.setValue(perc);
									
									lblPrevPercentage.setEnabled(true);	
									txtPrevPercentage.setEnabled(true);
									txtPrevPercentage.setEditable(false);
									txtPrevPercentage.setValue(prev);
									
									txtCO_vat_amt.setEnabled(true);	
									txtCO_wtax_amt.setEnabled(true);	
									txtCO_ret_amt.setEnabled(true);
									
									Double gr_amt  = (contprice*accomp);
									Double net_amt =  gr_amt-wtax_amt-ret_amt;
									ret_amt =  (gr_amt*.1);

									txtGrossAmt.setText(df.format(gr_amt));
									txtCO_net_amt.setText(df.format(net_amt));	
									txtCO_ret_amt.setText(df.format(ret_amt));	
									computeNetAmount(lookupEntity.getText(),wtax_rate);
								}								
							}
						}
					});	
					
				}
				{
					txtPrevPercentage = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrderDtl_1b.add(txtPrevPercentage);
					txtPrevPercentage.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtPrevPercentage.setEnabled(false);	
					txtPrevPercentage.setEditable(false);	
					txtPrevPercentage.setBounds(130, 25, 300, 22);	
					txtPrevPercentage.setHorizontalAlignment(JTextField.CENTER);
				
				}
				{
					txtPercentage = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrderDtl_1b.add(txtPercentage);
					txtPercentage.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtPercentage.setEnabled(false);	
					txtPercentage.setEditable(false);	
					txtPercentage.setBounds(130, 25, 300, 22);	
					txtPercentage.setHorizontalAlignment(JTextField.CENTER);
				
				}
				{

					txtGrossAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrderDtl_1b.add(txtGrossAmt);
					txtGrossAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtGrossAmt.setText("0.00");
					txtGrossAmt.setEnabled(false);	
					txtGrossAmt.setBounds(130, 0, 72, 22);

				}	

				//Start of Left Panel 
				pnlChangeOrderInfo = new JPanel(new BorderLayout(0,0));
				pnlChangeOrderDtl.add(pnlChangeOrderInfo, BorderLayout.EAST);
				pnlChangeOrderInfo.setPreferredSize(new java.awt.Dimension(702, 187));
				pnlChangeOrderInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlChangeOrder_ins = new JPanel(new BorderLayout(10,5));
				pnlChangeOrderInfo.add(pnlChangeOrder_ins, BorderLayout.NORTH);
				pnlChangeOrder_ins.setPreferredSize(new java.awt.Dimension(715, 120));
				pnlChangeOrder_ins.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlChangeOrderDtl_2 = new JPanel(new GridLayout(4, 1, 0, 0));
				pnlChangeOrder_ins.add(pnlChangeOrderDtl_2, BorderLayout.WEST);
				pnlChangeOrderDtl_2.setPreferredSize(new java.awt.Dimension(415, 126));
				pnlChangeOrderDtl_2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					tagContractor = new _JTagLabel("[ ]");
					pnlChangeOrderDtl_2.add(tagContractor);
					tagContractor.setEnabled(false);	
				}		
				{
					tagEntityType = new _JTagLabel("[ ]");
					pnlChangeOrderDtl_2.add(tagEntityType);
					tagEntityType.setEnabled(false);	
				}	
				{
					tagTranType = new _JTagLabel("[ ]");
					pnlChangeOrderDtl_2.add(tagTranType);
					tagTranType.setEnabled(false);	
				}

				pnlChangeOrderDtl_3 = new JPanel(new BorderLayout(5, 5));
				pnlChangeOrder_ins.add(pnlChangeOrderDtl_3, BorderLayout.CENTER);
				pnlChangeOrderDtl_3.setPreferredSize(new java.awt.Dimension(350, 126));
				pnlChangeOrderDtl_3.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));

				pnlChangeOrderDtl_3a = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlChangeOrderDtl_3.add(pnlChangeOrderDtl_3a, BorderLayout.WEST);
				pnlChangeOrderDtl_3a.setPreferredSize(new java.awt.Dimension(145, 135));
				pnlChangeOrderDtl_3a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));	

				{
					lblCO_vat_amt = new JLabel("VAT Amount", JLabel.TRAILING);
					pnlChangeOrderDtl_3a.add(lblCO_vat_amt);
					lblCO_vat_amt.setEnabled(false);	
				}	
				{
					lblCO_wtax_amt = new JLabel("WTax Amount", JLabel.TRAILING);
					pnlChangeOrderDtl_3a.add(lblCO_wtax_amt);
					lblCO_wtax_amt.setEnabled(false);	
				}					
				{
					lblCO_ret_amt = new JLabel("Retention Amt.", JLabel.TRAILING);
					pnlChangeOrderDtl_3a.add(lblCO_ret_amt);
					lblCO_ret_amt.setEnabled(false);	
				}	
				{
					lblCO_net_amt= new JLabel("Net Amount", JLabel.TRAILING);
					pnlChangeOrderDtl_3a.add(lblCO_net_amt);
					lblCO_net_amt.setEnabled(false);	
				}	

				pnlChangeOrderDtl_3b = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlChangeOrderDtl_3.add(pnlChangeOrderDtl_3b, BorderLayout.CENTER);
				pnlChangeOrderDtl_3b.setPreferredSize(new java.awt.Dimension(160, 135));
				pnlChangeOrderDtl_3b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				//pnlChangeOrderDtl_3b.setBackground(Color.cyan);

				{					
					txtCO_vat_amt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrderDtl_3b.add(txtCO_vat_amt);
					txtCO_vat_amt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtCO_vat_amt.setText("0.00");
					txtCO_vat_amt.setEnabled(false);
					txtCO_vat_amt.setEditable(false);
					txtCO_vat_amt.setBounds(95, 0, 80, 22);							
				}	
				{
					txtCO_wtax_amt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrderDtl_3b.add(txtCO_wtax_amt);
					txtCO_wtax_amt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtCO_wtax_amt.setText("0.00");
					txtCO_wtax_amt.setEnabled(false);
					txtCO_wtax_amt.setEditable(false);
					txtCO_wtax_amt.setBounds(95, 0, 80, 22);		
				}					
				{
					txtCO_ret_amt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrderDtl_3b.add(txtCO_ret_amt);
					txtCO_ret_amt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtCO_ret_amt.setText("0.00");
					txtCO_ret_amt.setEnabled(false);
					txtCO_ret_amt.setBounds(95, 0, 80, 22);			
					txtCO_ret_amt.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {				

							Double gr_amt  = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue();
							Double dp_amt  = (0.00);
							Double wtax_amt= Double.valueOf(txtCO_wtax_amt.getText().trim().replace(",","")).doubleValue();
							Double ret_amt = Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")).doubleValue();
							Double net_amt =  gr_amt-dp_amt-wtax_amt-ret_amt;

							txtCO_net_amt.setText(df.format(net_amt));
						}				 
					});		
				}	
				{
					txtCO_net_amt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlChangeOrderDtl_3b.add(txtCO_net_amt);
					txtCO_net_amt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtCO_net_amt.setText("0.00");
					txtCO_net_amt.setEnabled(false);
					txtCO_net_amt.setEditable(false);
					txtCO_ret_amt.setBounds(95, 0, 80, 22);		
				}	

				pnlSurety = new JPanel(new BorderLayout(5, 5));
				pnlChangeOrderInfo.add(pnlSurety, BorderLayout.SOUTH);	
				pnlSurety.setPreferredSize(new java.awt.Dimension(702, 59));
				pnlSurety.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
				pnlSurety.setBorder(lineBorder);

				scpJVRemark = new JScrollPane();
				pnlSurety.add(scpJVRemark);
				scpJVRemark.setBounds(75, 7, 300, 61);
				scpJVRemark.setOpaque(true);
				scpJVRemark.setPreferredSize(new java.awt.Dimension(360, 159));

				{
					txtJVRemark = new JTextArea();
					scpJVRemark.add(txtJVRemark);
					scpJVRemark.setViewportView(txtJVRemark);
					txtJVRemark.setText(" Remarks :");
					txtJVRemark.setBounds(67, 3, 200, 81);
					txtJVRemark.setLineWrap(true);
					txtJVRemark.setPreferredSize(new java.awt.Dimension(350, 133));
					txtJVRemark.setEditable(false);
					txtJVRemark.setEnabled(false);
					txtJVRemark.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	

						}});	
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
				pnlTB_CO = new JPanel(new BorderLayout());
				pnlBillingTable.add(pnlTB_CO, "Center");
				pnlTB_CO.setPreferredSize(new java.awt.Dimension(1183, 365));				

				{
					scrollCO = new _JScrollPaneMain();
					pnlTB_CO.add(scrollCO, BorderLayout.CENTER);
					{
						modelCO = new modelContractorChangeOrder();
						tblCO = new _JTableMain(modelCO);
						scrollCO.setViewportView(tblCO);
						tblCO.addMouseListener(this);	
						tblCO.addMouseListener(new PopupTriggerListener_panel());
						tblCO.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()) {
									try {
										
										int row = tblCO.getSelectedRow();
										  rec_id = (Integer) modelCO.getValueAt(row, 14);
										  
										  displayChangeOrderDetails();
										  System.out.println("Rec ID: "+rec_id);
									}catch(ArrayIndexOutOfBoundsException ex) {}
								}
							}
						});
						
						tblCO.getColumnModel().getColumn(0).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(1).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(2).setPreferredWidth(130);
						tblCO.getColumnModel().getColumn(3).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(4).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(5).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(6).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(7).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(8).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(9).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(10).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(11).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(12).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(13).setPreferredWidth(80);
						tblCO.getColumnModel().getColumn(14).setPreferredWidth(80);

						
						tblCO.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {

							}							
							public void keyPressed(KeyEvent e) {

							}

						}); 
						tblCO.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblCO.rowAtPoint(e.getPoint()) == -1){
									tblCO_total.clearSelection();
								}else{
									//Comment by Erick 2023/03/21 
 									//displayChangeOrderDetails();
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblCO.rowAtPoint(e.getPoint()) == -1){
									tblCO_total.clearSelection();
								}else{
									tblCO.setCellSelectionEnabled(true);
								}
							}
						});
					}
					{
						rowHeaderCo = tblCO.getRowHeader22();
						scrollCO.setRowHeaderView(rowHeaderCo);
						scrollCO.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollCO_total = new _JScrollPaneTotal(scrollCO);
					pnlTB_CO.add(scrollCO_total, BorderLayout.SOUTH);
					{
						modelCO_total = new modelContractorChangeOrder();
						modelCO_total.addRow(new Object[] {
								null, "Total", null, null,  null, 
								new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
								new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null });

						tblCO_total = new _JTableTotal(modelCO_total, tblCO);
						tblCO_total.setFont(dialog11Bold);
						scrollCO_total.setViewportView(tblCO_total);
						((_JTableTotal) tblCO_total).setTotalLabel(1);
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
					btnSave = new JButton("Save");
					pnlSouthCenter.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
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
			pnlEditAmount= new JPanel();
			pnlEditAmount.setLayout(null);
			pnlEditAmount.setPreferredSize(new java.awt.Dimension(265, 140));
			{
				lblEditAmount = new JLabel();
				pnlEditAmount.add(lblEditAmount);
				lblEditAmount.setBounds(5, 7, 160, 20);
				lblEditAmount.setText("Contract Amount");
			}
			{
				lblPrevPerc = new JLabel();
				pnlEditAmount.add(lblPrevPerc);
				lblPrevPerc.setBounds(5, 35, 160, 20);
				lblPrevPerc.setText("Previous Accomp. %");
			}
			{
				lblEditPerc = new JLabel();
				pnlEditAmount.add(lblEditPerc);
				lblEditPerc.setBounds(5, 63, 160, 20);
				lblEditPerc.setText("Current Accomp. %");
			}
			{
				txtEditAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
				pnlEditAmount.add(txtEditAmount);
				txtEditAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL2);
				txtEditAmount.setText("0.0000");
				txtEditAmount.setEnabled(true);
				txtEditAmount.setEditable(true);
				txtEditAmount.setBounds(130, 7, 125, 21);
			}
			{
				txtPrevPerc = new _JXFormattedTextField(JXFormattedTextField.CENTER);
				pnlEditAmount.add(txtPrevPerc);
				txtPrevPerc.setFormatterFactory(_JXFormattedTextField.DECIMAL2);
				txtPrevPerc.setText("0.0000");
				txtPrevPerc.setEnabled(true);
				txtPrevPerc.setEditable(true);
				txtPrevPerc.setBounds(130, 35, 125, 21);
			}
			{
				txtEditPerc = new _JXFormattedTextField(JXFormattedTextField.CENTER);
				pnlEditAmount.add(txtEditPerc);
				txtEditPerc.setFormatterFactory(_JXFormattedTextField.DECIMAL2);
				txtEditPerc.setText("0.0000");
				txtEditPerc.setEnabled(true);
				txtEditPerc.setEditable(true);
				txtEditPerc.setBounds(130, 63, 125, 21);
			}
			{
				btnOK = new JButton();
				pnlEditAmount.add(btnOK);
				btnOK.setBounds(95, 103, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(true);
				btnOK.setMnemonic(KeyEvent.VK_ENTER);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlEditAmount);						
						optionPaneWindow.dispose();	

					}
				});
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
			"(case when a.billing_type = '02' then 'BILLING' else 'RETENTION' end) as transaction ,\r\n" + //0
			"to_char(a.change_order_date,'MM-dd-yyyy'),\r\n" + //1
			"trim(a.change_order_no),\r\n" + //2
			"(case when a.add_ded_code = 'N' then 'N/A' else 'Additive' end ) as type,\r\n" + //3 
			"a.accomp_perc, \r\n" + //4
			"a.amount, \r\n" +      //5
			"a.vat_amt,\r\n" +      //6
			"a.wtax_amt,\r\n" + 	//7
			"a.ret_amt,\r\n" + 		//8
			"coalesce(a.dp_recoupment,0.00),\r\n" +  //9
			"a.net_amt,\r\n" + 		//10
			"a.rplf_no,\r\n" + 		//11
			"e.date_paid ,\r\n" +	//12
			"(case when e.date_paid is not null then 'Released' else \r\n" + 
			"	case when e.cv_no is not null then 'Processed' else " +
			"	case when d.status_id = 'P' then 'Posted' else 'Active' end end end) as req_status, \r\n" + //13
			"a.rec_id \n"+
			
			"from co_change_order a " +
			"left join rf_pv_header d on a.rplf_no = d.rplf_no and a.co_id = d.co_id\r\n" + 
			"left join rf_cv_header e on d.cv_no = e.cv_no and d.co_id = e.co_id \r\n" + 
			"where trim(a.ntp_no) = '"+ntp_no+"' and a.status_id = 'A'    " + 
			"order by a.rplf_no " ;
		
		System.out.println("");
		System.out.println("displayContractChangeOrder: "+sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalCO(modelMain, modelTotal);			
		}		

		else {
			modelCO_total = new modelContractorChangeOrder();
			modelCO_total.addRow(new Object[] { null, "Total", null, null, null, 
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), 
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null });

			tblCO_total = new _JTableTotal(modelCO_total, tblCO);
			tblCO_total.setFont(dialog11Bold);
			scrollCO_total.setViewportView(tblCO_total);
			((_JTableTotal) tblCO_total).setTotalLabel(1);}

		tblCO.packAll();
		tblCO.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(1).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(2).setPreferredWidth(130);
		tblCO.getColumnModel().getColumn(3).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(4).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(5).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(6).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(7).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(8).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(9).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(10).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(11).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(12).setPreferredWidth(80);
		tblCO.getColumnModel().getColumn(13).setPreferredWidth(80);


		btnRefresh.setEnabled(true);
		adjustRowHeight();

	}	

	public void displayChangeOrderDetails(){

		int rw = tblCO.getSelectedRow();	
		change_order_no = tblCO.getValueAt(rw,2).toString().trim();
		String rplf_status = tblCO.getValueAt(rw,13).toString().trim();

		Object[] ch_ord = getChangeOrderDetails(change_order_no, rec_id);	
		txtChangeOrderNo.setText((String) ch_ord[0]);
		String add_ded_type = (String) ch_ord[1];
			if (add_ded_type.equals("A")) {cmbType.setSelectedIndex(0);} 
			else {cmbType.setSelectedIndex(1);}
		dteChangeOrder.setDate((Date) ch_ord[2]);
		lookupContractor.setValue((String) ch_ord[3]);
		tagContractor.setTag((String) ch_ord[4]);
		lookupEntity.setValue((String) ch_ord[5]);
		tagEntityType.setTag((String) ch_ord[6]);
		lookupTransaction.setValue((String) ch_ord[7]);
		tagTranType.setTag((String) ch_ord[8]);
		txtPercentage.setText(nf.format(Double.parseDouble(ch_ord[9].toString())));
		//txtGrossAmt.setText(nf.format(Double.parseDouble(ch_ord[10].toString())));comment by Erick 11/29/19
		
		txtCO_vat_amt.setText(nf.format(Double.parseDouble(ch_ord[12].toString())));
		txtCO_wtax_amt.setText(nf.format(Double.parseDouble(ch_ord[13].toString())));
		txtCO_ret_amt.setText(nf.format(Double.parseDouble(ch_ord[14].toString())));
		txtCO_net_amt.setText(nf.format(Double.parseDouble(ch_ord[15].toString())));
		//txtDPrecoup.setText(nf.format(Double.parseDouble(ch_ord[11].toString())));
		//txtRPLF.setText((String) ch_ord[15]);
		//tagRPLF.setTag((String) ch_ord[16]);
		txtJVRemark.setText((String) ch_ord[18]);
		Double contrct_no= ((Double.parseDouble(ch_ord[12].toString())/ .12 )* 1.12);
		txtGrossAmt.setText(nf.format(contrct_no));//added by erick 11/29/19
		txtcontprice.setText(nf.format(contrct_no));

		if(rplf_status.equals("Active")) {enableButtons(false, true, false, true, true);}
		else {enableButtons(false, false, false, true, true);}
		enable_fields(false);

	}

	//Enable/Disable all components inside JPanel
	public void setComponentEnabled(JPanel panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			comp.setEnabled(enable);
		}
	}

	public void enable_fields(Boolean x){   

		lblChangeOrderNo.setEnabled(x);	
		txtChangeOrderNo.setEnabled(x);	
		lblType.setEnabled(x);	
		//cmbType.setEnabled(x);
		lblDate.setEnabled(x);	
		dteChangeOrder.setEnabled(x);

		//lblRPLF.setEnabled(x);	
		lblContractor.setEnabled(x);	
		lblEntityType.setEnabled(x);	
		lblTranType.setEnabled(x);	
		lblChangeOrderAmt.setEnabled(x);
		lblPercentage.setEnabled(x);
		lblPrevPercentage.setEnabled(x);
		//lblChangeOrderDPRecoup.setEnabled(x);	

		//txtRPLF.setEnabled(x);	
		lookupContractor.setEnabled(x);	
		lookupEntity.setEnabled(x);	
		lookupTransaction.setEnabled(x);	
		txtGrossAmt.setEnabled(x);
		txtChangeOrderNo.setEnabled(x);
		txtChangeOrderNo.setEditable(x);
		txtcontprice.setEnabled(x);
		txtPrevPercentage.setEnabled(x);
		txtPrevPerc.setEnabled(x);
		txtPercentage.setEnabled(x);	

		//tagRPLF.setEnabled(x);	
		tagContractor.setEnabled(x);	
		tagEntityType.setEnabled(x);	
		tagTranType.setEnabled(x);	

		lblCO_vat_amt.setEnabled(x);	
		lblCO_wtax_amt.setEnabled(x);	
		lblCO_ret_amt.setEnabled(x);	
		lblCO_net_amt.setEnabled(x);	

		txtCO_vat_amt.setEnabled(x);	
		txtCO_wtax_amt.setEnabled(x);	
		txtCO_ret_amt.setEnabled(x);	
		txtCO_net_amt.setEnabled(x);
		txtJVRemark.setEnabled(x);
		txtJVRemark.setEditable(x);
	}

	public void refresh_fields(){  			

		txtChangeOrderNo.setText("");
		cmbType.setSelectedIndex(0);	
		dteChangeOrder.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

		//txtRPLF.setText("");
		//tagRPLF.setText("[ ]");
		lookupContractor.setValue("");
		lookupEntity.setValue("");
		lookupTransaction.setValue("");
		tagContractor.setText("[ ]");
		tagEntityType.setText("[ ]");
		tagTranType.setText("[ ]");	

		txtcontprice.setText("0.00");
		txtGrossAmt.setText("0.00");
		txtPrevPercentage.setText("0.00");
		txtPrevPerc.setText("0.00");
		txtPercentage.setText("0.00");
		txtEditAmount.setText("0.00");
		//txtDPrecoup.setText("0.00");
		txtCO_vat_amt.setText("0.00");
		txtCO_wtax_amt.setText("0.00");
		txtCO_ret_amt.setText("0.00");
		txtCO_net_amt.setText("0.00");

		txtJVRemark.setText(" Remarks :");
		txtChangeOrderNo.setEditable(false);	

	}

	public void refresh_fields_fromSaving(){  

		txtChangeOrderNo.setText("");
		cmbType.setSelectedIndex(0);	
		dteChangeOrder.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

		lookupContractor.setValue("");
		lookupEntity.setValue("");
		lookupTransaction.setValue("");
		tagContractor.setText("[ ]");
		tagEntityType.setText("[ ]");
		tagTranType.setText("[ ]");		
		//txtRPLF.setText("");
		//tagRPLF.setText("[ ]");		
	
		//txtDPrecoup.setText("0.00");
		txtPrevPercentage.setText("0.00");
		txtPercentage.setText("0.00");
		txtGrossAmt.setText("0.00");
		txtcontprice.setText("0.00");
		txtCO_vat_amt.setText("0.00");
		txtCO_wtax_amt.setText("0.00");
		txtCO_ret_amt.setText("0.00");
		txtCO_net_amt.setText("0.00");

		txtJVRemark.setText(" Remarks :");

	}

	public void refresh_tablesMain(){  		
		//reset table 1
		FncTables.clearTable(modelCO);
		FncTables.clearTable(modelCO_total);			
		rowHeaderCo = tblCO.getRowHeader22();
		scrollCO.setRowHeaderView(rowHeaderCo);	
		modelCO_total.addRow(new Object[] { null, "Total", null, null, null, 
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null });			
	}

	public void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnRefresh.setEnabled(d);
		btnCancel.setEnabled(e);

	}

	public void initialize_comp(){		
		
		//co_id 		= "02";	
		//company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		//tagCompany.setTag(company);
		//company_logo = RequestForPayment.sql_getCompanyLogo();

		KEYBOARD_MANAGER.focusNextComponent();		
		//lookupContNTP.setLookupSQL(getLookupContractNo());
		
		//lookupCompany.setValue(co_id);
}

	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Refresh")){ 
			refresh(); 
			lookupContNTP.setEnabled(false);
		}

		if(e.getActionCommand().equals("Cancel")){ 
			cancel(); 
		}

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==true) { 
			addnew();	
		}else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==false ) { 
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process change order.","Information",JOptionPane.INFORMATION_MESSAGE); 
		}

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==true) {
			save(); 
		}else if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==false ) { 
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process change order.","Information",JOptionPane.INFORMATION_MESSAGE); 
		}
		
		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==true) {
			edit(); 
		}else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "6")==false ) { 
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit change order.","Information",JOptionPane.INFORMATION_MESSAGE); 
		}

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

	public class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	public class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column 	= tblCO.getSelectedColumn();
				if (column==11) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ;}
				if (column==12) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; }
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column 	= tblCO.getSelectedColumn();
				if (column==11) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; }
				if (column==12) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ; }
			}
		}
	}

	public void refresh(){
		displayContractChangeOrder(modelCO, rowHeaderCo, modelCO_total);	
		refresh_fields_fromSaving();
		lblEntityType.setEnabled(false);	
		lookupEntity.setEnabled(false);	
		tagEntityType.setEnabled(false);
		enableButtons(true, false, false, false, true);	
	}

	public void cancel(){
		enable_fields(false);
		refresh_tablesMain();
		refresh_fields();		
		lookupContNTP.setValue("");
		lookupContNTP.setEnabled(false);
		tagNTPno.setText("[ ]");
		enableButtons(false, false, false, false, false);
	}

	public void addnew(){

		enable_fields(true);		
		int rw = tblCO.getModel().getRowCount()+1 ;
		txtChangeOrderNo.setText(lookupContNTP.getText().trim()+"-C"+rw);	
		lookupContractor.setLookupSQL(getContractor());
		lookupTransaction.setLookupSQL(getBillingType());
		lblEntityType.setEnabled(false);	
		lookupEntity.setEnabled(false);	
		tagEntityType.setEnabled(false);	
		txtJVRemark.setText("");
		txtChangeOrderNo.setEditable(true);
		enableButtons(false, false, true, false, true);
		to_do = "save";
	}

	public void save(){
		if(checkNetAmount()==false){JOptionPane.showMessageDialog(getContentPane(), "Net amount must be greater than zero.", "Information", 
				JOptionPane.WARNING_MESSAGE);
		}else {

			if(checkChangeOrderDetails()==false){
				JOptionPane.showMessageDialog(getContentPane(), "Enter complete details (Contractor, Entity Type and Transaction).", "Information", JOptionPane.WARNING_MESSAGE);
			}else {	

				if(checkEntity()==false){
					JOptionPane.showMessageDialog(getContentPane(), "Select contractor.", "Information", JOptionPane.WARNING_MESSAGE);
				}else {		

					if(checkEntityType()==false){
						JOptionPane.showMessageDialog(getContentPane(), "Select entity type.", "Information", JOptionPane.WARNING_MESSAGE);
					}else {	

						if(checkTransaction()==false){
							JOptionPane.showMessageDialog(getContentPane(), "Select transaction.", "Information", JOptionPane.WARNING_MESSAGE);
						}else {
							executeSave();
						}
					}
				}
			}
		}
	}

	public void executeSave(){
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			
			System.out.printf("Display value of todo: %s%n", to_do);
			
			if (to_do.equals("save")) 
			{
				pgUpdate db = new pgUpdate();			
				setBillingAmountParticulars();
				rplf_no = sql_getNextRPLFno();
				System.out.printf("Display value of rplf 1: %s%n", rplf_no);
				insertChangeOrder(db, rplf_no);
				db.commit();
				
				JOptionPane.showMessageDialog(getContentPane(),"New change order details saved.","Information",JOptionPane.INFORMATION_MESSAGE);				
			}
			else if (to_do.equals("update")) 
			{
				pgUpdate db = new pgUpdate();		
				//String rplf_no = txtRPLF.getText().trim() ;
				setBillingAmountParticulars();
				updateChangeOrder(db, change_order_no, rec_id);  //ok
				System.out.printf("Display value of rplf 5: %s%n", rplf_no);
				//updateRPLF_header(db, rplf_no);	//ok
				//System.out.printf("Display value of rplf 6: %s%n", rplf_no);
				//updateRPLF_detail(rplf_no, db); //ok			
				//System.out.printf("Display value of rplf 6: %s%n", rplf_no);
				//insertRPLF_detail(db, rplf_no, change_order_seq_no);
				//System.out.printf("Display value of rplf 6: %s%n", rplf_no);
				//insertPV_detail(db, rplf_no);
				//System.out.printf("Display value of rplf 6: %s%n", rplf_no);
				//updatePV_header(db, rplf_no);
				//updatePV_details(rplf_no, db); //ok
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"Change order details updated.","Information",JOptionPane.INFORMATION_MESSAGE);				
			}
			
			pgUpdate db = new pgUpdate();
			System.out.printf("Display value of rplf 2: %s%n", rplf_no);
			insertRPLF_header(db, rplf_no);				
			System.out.printf("Display value of rplf 3: %s%n", rplf_no);
			insertRPLF_detail(db, rplf_no, change_order_seq_no);
			System.out.printf("Display value of rplf 4: %s%n", rplf_no);
			//insertPV_header(db, rplf_no);
			//insertPV_detail(db, rplf_no);
			db.commit();
			
			//refresh data					
			displayContractChangeOrder(modelCO, rowHeaderCo, modelCO_total);	
			refresh_fields_fromSaving();
			lblEntityType.setEnabled(false);	
			lookupEntity.setEnabled(false);	
			tagEntityType.setEnabled(false);	
			enableButtons(true, false, false, false, true);
		}		
	}

	public void edit(){		
		enable_fields(true);
		txtChangeOrderNo.setEditable(true);
		lookupContractor.setLookupSQL(getContractor());
		lookupTransaction.setLookupSQL(getBillingType());
		lookupEntity.setLookupSQL(getEntity_type(lookupContractor.getText()));
		enableButtons(false, false, true, false, true);
		wtax_rate = sql_getWTax_rate(lookupEntity.getText());
		to_do = "update";
	}



	//select, lookup and get statements	
	public static String getLookupContractNo( String company_id){   
		System.out.println("Coco_id: "+co_id);
		System.out.println("lookup: "+lookupCompany.getValue());
		
		return 
		"select \r\n" + 
		"a.contract_no as Contract_No, \r\n" + 
		"a.ntp_no as NTP_No, \r\n" + 
		"a.entity_id as Contractor_ID, \r\n" + 
		"b.entity_name as Contractor_Name," +
		"a.proj_id, \r\n" + 
		"c.proj_name as Project," +
		//"d.sub_proj_id, \n"+
		"(case when a.with_surety_bond = false then 'YES' else 'NO' end) as with_surety_bond \r\n" + 
		//"d.contract_price\n"+
		"from co_ntp_header a\r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
		"left join mf_project c on a.proj_id = c.proj_id\r\n" + 
		//"left join mf_sub_project d on c.proj_id=d.proj_id\n"+
		//"left join co_change_order d on a.ntp_no=d.ntp_no\n"+
		"where a.co_id = '"+company_id+"' \r\n" +
		"order by ntp_no::integer desc \r\n";

	}

	public String getChangeOrderNo(){ 

		String sql = "select change_order_no, ntp_no, change_order_date, co_id from co_change_order where trim(ntp_no) = '"+ntp_no+"'  ";		
		return sql;

	}

	public static String getBillingType(){  	 

		return 

		"select \n" +
		"trim(billingtype_id) as \"Billing ID\", \n" +
		"trim(billingtype_desc) as \"Billing Description\", \n" +
		"trim(billingtype_alias) as \"Billing Alias\" \n" +
		"from mf_billing_type where trim(billingtype_id) != '01' ";


	}

	public String getContractor(){	

		return

		"select trim(entity_id) as entity_id , \r\n" + 
		"trim(entity_name) as entity_name , \r\n" + 
		"trim(entity_kind) as kind \r\n" + 
		"from rf_entity \r\n" + 
		"where entity_kind ='C' \r\n" + 
		"and status_id = 'A'" +
		"and entity_id in (select entity_id from rf_entity_assigned_type where entity_type_id in ('05','06','20','21','34','35','18','19' ))" +
		"group by trim(entity_id), trim(entity_name), trim(entity_kind)  " +		
		"order by trim(entity_name)";
		
	}

	public String getEntity_type(String entity_id){		 

		return

		"select a.entity_type_id as \"Entity Type ID\",  " +
		"trim(b.entity_type_desc) as \"Entity Type Desc\" , " +
		"c.wtax_rate as \"WTax Rate\" \n" + 
		"from (select * from rf_entity_assigned_type where trim(entity_id) =  '"+entity_id+"' ) a \r\n" + 
		"left join mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n" + 		
		"left join rf_withholding_tax c on b.wtax_id = c.wtax_id   "  ;
	}

	public Integer sql_getChangeOrderNo() {	

		Integer next_no = 0;
		String SQL = 
			"select nextval('tf_co_change_order_no') " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			next_no = (Integer) Integer.parseInt(db.getResult()[0][0].toString());
		}else{
			next_no = null;
		}

		return next_no;
	}
	
	public String sql_getNextRPLFno() {		

		String rplf = "";
		String SQL = 
			//select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+co_id+"' " ;
				"select fn_get_rplf_no('"+co_id+"') ";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			rplf = (String) db.getResult()[0][0];
		}else{
			rplf = "000000001";
		}

		return rplf;
	}

	public String sql_getAcctID(String rplf_no) {		

		String acct_id = "";
		System.out.printf("Display value of ntp_no: %s%n", rplf_no);
		String SQL = 
		
			"select(case when a.billing_type = '02' then c.acct_id else (select acct_id from mf_boi_chart_of_accounts where acct_name = 'Retentions Payable') end ) as acct_id \n" +
			"from (select * from co_change_order where trim(rplf_no) = '"+rplf_no+"')a \n" + 
			"left join co_ntp_header b on b.ntp_no = a.ntp_no \n" +
			"left join mf_ntp_type c on c.type_id=b.ntp_type_id   " ;

		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Display SQL for Acct ID", SQL);
		//System.out.printf("Display value of acct_id: %s%n", db.getResult()[0][0]);
		if(db.isNotNull()){
			acct_id = (String) db.getResult()[0][0];
		}else{
			System.out.println("DB is null");
			acct_id = null;
		}
		System.out.printf("Display value of acct_id: %s%n", acct_id);

		return acct_id;
		
	}
	
	public Double sql_getWTax_rate(String wtax_id) {	
		
		String SQL = 
			"select wtax_rate from rf_withholding_tax  where wtax_id = '"+wtax_id+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			wtax_rate = Double.parseDouble(db.getResult()[0][0].toString());			
		}else{
			wtax_rate = 0.00;
		}

		return wtax_rate;
	}

	public static Object [] getProjectDtls(String proj_id) {			

		String strSQL = 
			"select a.vatable,   " +

			"from (select * from mf_project where trim(proj_id)='"+proj_id+"') a \n"  ;


		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	public static Object [] getChangeOrderDetails(String change_order_no, Integer rec_id) {			

		String strSQL = 
			"\r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"a.change_order_no,\r\n" + //0
			"(case when add_ded_code = 'A' then 'A' else 'D' end) as type,\r\n" + //1
			"a.change_order_date,\r\n" + //2
			"a.entity_id,\r\n" + //3--Contractor
			"upper(trim(b.entity_name)) as contractor,\r\n" + //4 
			"a.entity_type_id,\r\n" + //5
			"trim(c.entity_type_desc) as entity_type,\r\n" + //6
			"a.billing_type,\r\n" + //7
			"(case when a.billing_type = '02' then 'PROGRESS BILLING' else 'RETENTION PAYMENT' end) as tran_type,\r\n" + //8
			"coalesce(a.accomp_perc,0) as accomp_perc,\r\n" + //9 -- Edited by Erick 2023-07-13
			"a.amount,\r\n" + //10 
			"a.dp_recoupment,\r\n" + //11
			"a.vat_amt,\r\n" +  //12
			"a.wtax_amt,\r\n" + //13
			"a.ret_amt,\r\n" +  //14
			"a.net_amt,\r\n" +  //15
			"a.rplf_no,\r\n" +  //16
			"(case when e.date_paid is not null and e.date_posted is not null and e.posted_by is not null then 'Released' else \r\n" + 
			"	case when e.cv_no is not null then 'Processed' else 'Active' end end) as req_status,\r\n" + //17
			"trim(a.remarks) as remarks \n" + //18
			"\r\n" + 
			"\r\n" + 
			"from co_change_order a\r\n" + 
			"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
			"left join mf_entity_type c on a.entity_type_id = c.entity_type_id\r\n" + 
			"left join rf_pv_header d on a.rplf_no = d.rplf_no and a.co_id = d.co_id\r\n" + 
			"left join rf_cv_header e on d.cv_no = e.cv_no and d.co_id = e.co_id\r\n" + 
			"\r\n" + 
			"where change_order_no = '"+change_order_no+"' \n"+
			"and a.rec_id = "+rec_id+"";

		System.out.println("getChangeOrderDetails: "+strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	public String getJVno(Integer fiscal_yr, String period_id, Integer next_srlno){

		String SQL = 
			"select ('"+fiscal_yr+"'||'"+period_id+"'||trim(to_char("+next_srlno+",'0000'))) as next_jvno " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			jv_no = (String) db.getResult()[0][0];
		}else{
			jv_no = null;
		}

		return jv_no;
	}



	//check and validate 
	public Boolean checkEntity(){

		boolean x = false;		

		String payee;

		payee = lookupContractor.getText();

		if (payee.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}

	public Boolean checkEntityType(){

		boolean x = false;		

		String entity_type;

		entity_type = lookupEntity.getText();

		if (entity_type.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}

	public Boolean checkTransaction(){

		boolean x = false;		

		String tran;

		tran = lookupTransaction.getText();

		if (tran.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}

	public Boolean checkChangeOrderDetails(){

		boolean x = false;		

		String payee, entity_type, tran;

		payee = lookupContractor.getText();
		entity_type = lookupEntity.getText();
		tran = lookupTransaction.getText();

		if (payee.equals("")&&entity_type.equals("")&&tran.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}



	//table operations			
	private Boolean checkNetAmount(){ 

		boolean x = false;		

		double net_amt 	= Double.valueOf(txtCO_net_amt.getText().trim().replace(",","")).doubleValue();
		if (net_amt>0) {x=true;} 
		else if (net_amt<0) {x=false;} 
		else {x=false;}

		return x;

	}

	public static void totalCO(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal gr_amt_bd 	= new BigDecimal(0.00);	
		BigDecimal vat_amt_bd 	= new BigDecimal(0.00);	
		BigDecimal wtax_amt_bd	= new BigDecimal(0.00);	
		BigDecimal ret_amt_bd 	= new BigDecimal(0.00);	
		BigDecimal dpr_amt_bd 	= new BigDecimal(0.00);	
		BigDecimal net_amt_bd 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { gr_amt_bd 	= gr_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { gr_amt_bd 	= gr_amt_bd.add(new BigDecimal(0.00)); }

			try { vat_amt_bd 	= vat_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { vat_amt_bd 	= vat_amt_bd.add(new BigDecimal(0.00)); }

			try { wtax_amt_bd 	= wtax_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,7)));} 
			catch (NullPointerException e) { wtax_amt_bd 	= wtax_amt_bd.add(new BigDecimal(0.00)); }

			try { ret_amt_bd 	= ret_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { ret_amt_bd 	= ret_amt_bd.add(new BigDecimal(0.00)); }

			try { dpr_amt_bd 	= dpr_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { dpr_amt_bd 	= dpr_amt_bd.add(new BigDecimal(0.00)); }

			try { net_amt_bd 	= net_amt_bd.add(((BigDecimal) modelMain.getValueAt(x,10)));} 
			catch (NullPointerException e) { net_amt_bd 	= net_amt_bd.add(new BigDecimal(0.00)); }

		}	

		modelTotal.addRow(new Object[] { null, "Total", null, null, null, gr_amt_bd, vat_amt_bd, wtax_amt_bd, ret_amt_bd, dpr_amt_bd, net_amt_bd, null,null, null });
	}

	private void adjustRowHeight(){		

		int rw = tblCO.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblCO.setRowHeight(x, 22);			
			x++;
		}
	}


	//save and insert
	public void insertChangeOrder(pgUpdate db, String rec) { 
		
		rplf_no = sql_getNextRPLFno();	
		billing_date 		    = dateFormat.format(dteChangeOrder.getDate());	
		String billint_type 	= "";	
		String entity_type_id 	= "";	
		String change_order_no	= "";	
		Integer type   = cmbType.getSelectedIndex();
		String tran_type = "";
		if (type==0){tran_type = "A";} else {tran_type = "D";}
		String remarks = txtJVRemark.getText().replace("'","''") + "\n" + setPartDesc(rplf_no);
		Double accomp_perc = Double.valueOf(txtEditPerc.getText().trim().replace(",","")).doubleValue();
		Double contprice = Double.valueOf(txtEditAmount.getText().trim().replace(",","")).doubleValue();
		Double ret_amt = Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")).doubleValue();
		
		
		change_order_seq_no		= sql_getChangeOrderNo();	
		change_order_no			= txtChangeOrderNo.getText().trim();
		billint_type 			= lookupTransaction.getText().trim();
		entity_type_id 			= lookupEntity.getText().trim();
		


		String sqlDetail = 
			"INSERT into co_change_order values (" +
			//"'"+change_order_seq_no+"',  \n" +  			
			"'"+ntp_no+"',  \n" +				//1
			"'"+lookupContractor.getText().trim()+"',  \n" +	//2
			"'"+entity_type_id+"',  \n" + 		//3
			"'"+billint_type+"',  \n" +			//4			
			"'"+change_order_no+"',  \n" +		//5
			"'"+billing_date+"',  \n" +	//6
			""+gr_amt+" , \n" +			//7
			""+vat_amt+",  \n" +		//8
			""+wtx_amt+",  \n" +		//9
			""+ret_amt+",  \n" +		//10			
			""+net_amt+" , \n" +		//11
			""+dpr_amt+",  \n" +		//12
			"'"+tran_type+"',  \n" +	//13
			"'"+remarks+"',  \n" +		//14
			"'"+co_id+"' , \n" +		//15
			"'"+co_id+"' , \n" +		//16
			"'"+rplf_no+"' , \n" +		//17
			"'"+jv_no+"',  \n" +		//18
			"12.00,  \n" +				//19
			"null,  \n" +				//20		
			"'A',  \n" +				//21
			"'"+UserInfo.EmployeeCode+"',  \n" +	//22
			"now(),  \n" +				//23
			"'' , \n" +					//24
			"null , \n" +				//25	
			""+accomp_perc+" ,   \n" +	//26	
			"'"+contprice+"'  \n" +	//27	
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
		
		
	}
	
	public void updateChangeOrder(pgUpdate db, String rec, Integer rec_id) { 
		
		String billing_type 	= "";	
		String entity_type_id 	= "";
		//String change_order_no = txtChangeOrderNo.getText().trim() ;
		String remarks = txtJVRemark.getText().replace("'","''");

		change_order_seq_no		= sql_getChangeOrderNo();	
		change_order_no			= txtChangeOrderNo.getText().trim();
		billing_type 			= lookupTransaction.getText().trim();
		entity_type_id 			= lookupEntity.getText().trim();
		rplf_no 				= sql_getNextRPLFno();	

		String sqlDetail = 
			"update co_change_order set  " +			
			"entity_id = '"+lookupContractor.getText().trim()+"',  \n" +	
			"entity_type_id = '"+entity_type_id+"',  \n" + 		
			"billing_type = '"+billing_type+"',  \n" +	
			"change_order_no = '"+change_order_no+"',  \n" +	
			"amount = "+gr_amt+" , \n" +			
			"vat_amt = "+vat_amt+",  \n" +		
			"wtax_amt = "+wtx_amt+",  \n" +		
			"ret_amt = "+ret_amt+",  \n" +					
			"net_amt = "+net_amt+" , \n" +		
			"dp_recoupment = "+dpr_amt+",  \n" +		
			"remarks = '"+remarks+"',  \n" +		
			"edited_by = '"+UserInfo.EmployeeCode+"',  \n" +				
			"date_edited = now()  " +
			"where change_order_no = '"+rec+"'\n"+
			"and rec_id = "+rec_id+" \n"+
			"and co_id = '"+co_id+"' \n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}

	public void insertRPLF_header(pgUpdate db, String rplf_no){	

		Date date_liq_ext	= null;
		String rplf_type_id = "";
		String entity_id1	= "";
		String entity_id2	= "";
		String ent_type_id 	= "";
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
		String pay_type_id  = "";

		date_liq_ext= null;
		rplf_type_id= "01";
		entity_id1	= lookupContractor.getText().trim();
		entity_id2	= lookupContractor.getText().trim();
		ent_type_id = lookupEntity.getText().trim();
		sd_no		= null;
		doc_id		= "09";
		proc_id		= 1;	
		branch_id	= null;	
		justif		= null;	
		remarks		= null;	
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	=FncGlobal.dateFormat(FncGlobal.getDateSQL());

		String sqlDetail = 
			"INSERT into rf_request_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +		//2
			"'"+rplf_no+"',  \n" +		//3
			"'"+billing_date+"',  \n" +	//4
			"'"+billing_date+"',  \n" + //5
			""+date_liq_ext+",  \n" + 	//6
			"'"+rplf_type_id+"' , \n" +	//7
			"'"+entity_id1+"',  \n" +	//8
			"'"+entity_id2+"',  \n" +	//9
			"'"+ent_type_id+"',  \n" +	//10
			""+sd_no+",  \n" +			//11
			"'"+doc_id+"' , \n" +		//12
			""+proc_id+",  \n" +		//13
			""+branch_id+" , \n" +		//14
			""+justif+",  \n" +			//15
			""+remarks+" , \n" +		//16
			"'"+status_id+"' , \n" +	//17
			"'"+created_by+"',  \n" +	//18
			"'"+billing_date+"', \n" +	//19
			"'"+edited_by+"' , \n" +	//20
			"'"+edited_date+"',  " +	//21
			"'B', \n" +	//22
			"'' \n" +		//23
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	private void updateRPLF_header(pgUpdate db, String rplf_no){	
		
		String entity_id1	= "";
		String ent_type_id 	= "";			
		
		entity_id1	= lookupContractor.getText().trim();
		ent_type_id = lookupEntity.getText().trim();

		String sqlDetail = 
			"update rf_request_header set " +
			"entity_id1 = '"+entity_id1+"',  \n" +	
			"entity_type_id = '"+ent_type_id+"',  \n" +	
			"remarks = '"+txtJVRemark.getText().replace("'", "''")+"' , \n" +	
			"edited_by = '"+UserInfo.EmployeeCode+"' , \n" +	
			"date_edited = now() \n" +				
			"where rplf_no = '"+rplf_no+"'  and co_id = '"+co_id+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void insertRPLF_detail(pgUpdate db, String rplf_no, Integer rec2){			

		Integer line_no		= null;
		String ref_doc_id 	= "";
		Date ref_doc_date	= null;
		Boolean	with_budget	= false;
		String part_desc	= "";
		String acct_id		= "";
		String remarks		= "";
		String entity_id	= "";
		String entity_type_id	= "";
		String div_id		= "";
		String dept_id		= "";
		String sec_id		= "";
		String inter_bus_id	= "";
		String inter_co_id	= "";
		String sd_no		= "";
		String asset_no		= "";
		String old_acct_id	= "";		

		line_no		= 1;
		ref_doc_id 	= "97";  // 97- Change Order - mf_system_doc
		ref_doc_date= FncGlobal.dateFormat(FncGlobal.getDateSQL());
		with_budget	= false;
		part_desc	= txtJVRemark.getText().replace("'","''") + "\n" + setPartDesc(rplf_no);
		acct_id		= sql_getAcctID(rplf_no);		
		remarks		= null;	
		entity_id	= lookupContractor.getText().trim();		
		entity_type_id	= lookupEntity.getText().trim();
		div_id		= "";
		dept_id		= "";
		sec_id		= "";
		inter_bus_id= "";
		inter_co_id	= "";	
		sd_no		= null;
		asset_no	= null;
		old_acct_id	= null;		
		//Double contprice = Double.valueOf(txtEditAmount.getText().trim().replace(",","")).doubleValue();

		String sqlDetail = 
			"INSERT into rf_request_detail values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +		//2
			"'"+rplf_no+"',  \n" +			//3
			""+line_no+",  \n" +		//4
			"'"+ref_doc_id+"',  \n" + 	//5
			"'"+rec2+"',  \n" +			//6	
			"'"+ref_doc_date+"',  \n" + //7
			""+with_budget+" , \n" +	//8
			"'"+part_desc+"',  \n" +	//9
			"'"+acct_id+"',  \n" +		//10			
			""+remarks+",  \n" +		//11			
			""+gr_amt+",  \n" +			//12		
			//""+contprice+",  \n" +			//12
			"'"+entity_id+"',  \n" +	//13			
			"'"+entity_type_id+"' , \n" +	//14
			"'',  \n" +		//15			
			"'' , \n" +	//16			
			"'"+div_id+"',  \n" +		//17
			"'"+dept_id+"' , \n" +		//18
			"'"+sec_id+"',  \n" +		//19
			"'"+inter_bus_id+"' , \n" +	//20
			"'"+inter_co_id+"' , \n" +	//21
			"false , \n" +				//22
			"false, \n" +				//23
			"false, \n" +				//24	
			"false, \n" +				//25
			"get_wtaxid_given_entitytype('"+entity_type_id+"') , \n" +	//26
			""+wtax_rate+", \n" +		//27
			""+wtx_amt+", \n" +			//28
			"null, \n" +				//29	
			"0, \n" +					//30		
			""+vat_amt+", \n" +			//31			
			""+exp_amt+", \n" +			//32		
			""+net_amt+", \n" +			//33		
			""+sd_no+", \n" +			//34	
			"'', \n" +					//35
			""+asset_no+", \n" +		//36		
			""+old_acct_id+", \n" +		//37
			"'A', \n" +					//38
			"'"+UserInfo.EmployeeCode+"',  \n" +	//39
			"now(),  \n" +				//40
			"'' , \n" +					//41
			"null,  \n" +				//42
			""+ret_amt+",  \n" +		//43
			""+dpr_amt+"  \n" +			//44
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		System.out.printf("Display value of RPLF %s", rplf_no);
		System.out.printf("Display value of Acct_ID %s", acct_id);
		System.out.println("exp_amt: "+ exp_amt);
		System.out.println("ret_amt: "+ ret_amt);


		db.executeUpdate(sqlDetail, false);		
	}
	
	public void insertPV_header(pgUpdate db, String rec){		

		String entity_id1		= lookupContractor.getText().trim();
		String entity_id2		= "";
		String entity_type_id	= lookupEntity.getText().trim();
		String created_by		= UserInfo.EmployeeCode;
		String edited_by		= null;
		String remarks   		= txtJVRemark.getText().replace("'","''") + "\n" + setPartDesc(rplf_no);
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
			"'"+billing_date+"',  \n" + //8
			"'B',  \n" + 				//9
			"'"+entity_id1+"',  \n" + 	//10
			"'"+entity_id2+"',  \n" + 	//11
			"'"+entity_type_id+"',  \n" + //12
			"'',  \n" +		 			//13
			"'"+billing_date+"',  \n" + //14
			"null,  \n" + 				//15
			"null,  \n" + 				//16
			"'',  \n" + 				//17
			"null,  \n" + 				//18
			"'"+remarks+"', \n" +  		//19
			"'12',  \n" +  				//20
			"0,  \n" +  				//21
			"false,  \n" +  			//22
			"false,  \n" +  			//23
			"null,  \n" + 				//24
			"null,  \n" + 				//25
			"'A',  \n" +  				//26
			"'"+created_by+"',  \n" +  	//27
			"'"+billing_date+"',  \n" + //28
			"'"+edited_by+"',  \n" + 	//29
			""+date_edited+"  \n" + 	//30	
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	public void updatePV_header(pgUpdate db, String rec){		

		String entity_id1		= lookupContractor.getText().trim();
		String entity_type_id	= lookupEntity.getText().trim();

		String sqlDetail = 
			"update rf_pv_header set " +
			"entity_id1 = '"+entity_id1+"',  \n" + 
			"entity_type_id = '"+entity_type_id+"',  \n" + 
			"remarks = '"+txtJVRemark.getText()+"' , \n" +	
			"edited_by = '"+UserInfo.EmployeeCode+"' , \n" +	
			"date_edited = now() \n" +	
			"where rplf_no = '"+rec+"'  and co_id = '"+co_id+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void insertPV_detail(pgUpdate db, String rec){	

		int x  = 0;	
		//                                        vat         acct_payable       wtax         dpr            ret   
		String acct_id [] = {sql_getAcctID(rplf_no),"01-99-03-000","03-01-01-001","03-01-06-002","01-02-07-001","03-01-13-000"};		
		String bal_side [] = {"D","D","C","C","C","C" };
		Double amount [] = {exp_amt,vat_amt,net_amt,wtx_amt,dpr_amt,ret_amt};

		while(x <= 5 ) {				

			if (amount[x]==0){}

			else {

				String sqlDetail = 
					"INSERT into rf_pv_detail values (" +
					"'"+co_id+"',  \n" +  		//1
					"'"+co_id+"',  \n" +  		//2
					"'"+rec+"',  \n" +  		//3			
					""+x+",  \n" +  			//4
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
					"'"+dateFormat.format(dteChangeOrder.getDate())+"',  \n" + //19
					"'',  \n" + 				//20
					"null  \n" + 				//21	
					")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);				
				db.executeUpdate(sqlDetail, false);	
			}
			x++;
		}


	}

	public void updateRPLF_detail(String rplf_no, pgUpdate db) {

		String sqlDetail = 
			"update rf_request_detail " +
			"set status_id = 'I'," +
			"edited_by = '"+UserInfo.EmployeeCode+"'," +
			"date_edited = now()   " +
			"where trim(rplf_no) = '"+rplf_no+"' " +
			"and co_id = '"+co_id+"' " ;
			

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}
	
	public void updatePV_details(String rplf_no, pgUpdate db){	

		String sqlDetail = 
			"update rf_pv_detail set " +
			"status_id = 'I',  \n" +		
			"edited_by = '"+UserInfo.EmployeeCode+"'," +
			"date_edited = now()  " +
			"where pv_no = '"+rplf_no+"' and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}
		
	//set values of variables 
	
public String setPartDesc(String rplfNo){	
		
		//Object[] ntp_dtls = getNTPdetails(lookupContNTP.getText());		
		
		if (lookupTransaction.getText().trim().equals("02")){		
		 part_desc = "Payment for Change Order No. "+ ""+txtChangeOrderNo.getText().trim()+"" + "\n"  +
			"with RPLF No. " + ""+rplfNo+"" + " --  " + ""+contr_name+"" + "\n"  +
			"for Contract No. " + ""+lookupContNTP.getText()+"" + "\n"  +
			"re : Contract Amt " + "P"+ ""+txtcontprice.getText()+"" +  "\n"  +
			"Previous Accomplishment %: " + ""+txtPrevPercentage.getText()+"" + "\n"  +
			"Current Accomplishment % : " + ""+txtPercentage.getText()+"" + "\n"  +
			"Retention Fee : " + ""+df.format(Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")))+"" + "\n"  +
			"WTax : " + ""+df.format(Double.valueOf(txtCO_wtax_amt.getText().trim().replace(",","")))+"" + "\n"  +
			"";
		}else{
		 part_desc = "Retention Payment for Change Order No. "+ ""+txtChangeOrderNo.getText().trim()+"" + "\n"  +
			"with RPLF No. " + ""+rplfNo+"" + " " + ""+contr_name+"" + "\n"  +
			"for Contract No. " + ""+lookupContNTP.getText()+"" + "\n"  +
			"re : Contract Amt " + "P"+ ""+txtcontprice.getText()+"" +  "\n"  +
			"Retention Fee : " + ""+df.format(Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")))+"" + "\n"  +
			"";
		};

		return part_desc ;

	}

	public void setBillingAmountParticulars(){
		Double contprice = Double.valueOf(txtEditAmount.getText().trim().replace(",","")).doubleValue();
		Double prev = Double.valueOf(txtPrevPerc.getText().trim().replace(",","")).doubleValue();
		Double accomp = Double.valueOf(txtPercentage.getText().trim().replace(",","")).doubleValue();
		Double perc = (accomp-prev)/100;

		dpr_amt  = 0.00;
		vat_amt = Double.valueOf(txtCO_vat_amt.getText().trim().replace(",","")).doubleValue();
		wtx_amt	= Double.valueOf(txtCO_wtax_amt.getText().trim().replace(",","")).doubleValue();
		ret_amt = Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")).doubleValue();
		net_amt = Double.valueOf(txtCO_net_amt.getText().trim().replace(",","")).doubleValue();
		
		
		if (lookupTransaction.getText().trim().equals("03")){

			txtEditPerc.setText("100.00");	
			txtcontprice.setText(df.format(contprice));	
			txtPercentage.setText("100.00");
			gr_amt = net_amt;	
			exp_amt = gr_amt - vat_amt ;

		}else{
			txtcontprice.setText(df.format(contprice));	
			txtPercentage.setText(df.format(perc));
			//gr_amt = Double.valueOf(txtCO_net_amt.getText().trim().replace(",","")).doubleValue()/perc;
			gr_amt = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue()/perc;
			exp_amt = gr_amt - vat_amt ;
			ret_amt = (gr_amt * .1);
			
			System.out.println("gr_amt: "+gr_amt);
			System.out.println("vat_amt: "+vat_amt);
			System.out.println("exp_amt: "+exp_amt);

		};
		
	}

	public static Object [] getNTPdetails(String cont_no) {

		String strSQL = 
			"select * from get_change_order_part_details( '"+cont_no+"' )  " ;

		System.out.printf("SQL #1 getNTPdetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	//Right-click operations
	public void openContBilling(){

		ContractorsBilling cont_bill = new ContractorsBilling();
		Home_JSystem.addWindow(cont_bill);		

		if(co_id.equals(""))
		{

		} 
		else 
		{			
			ContractorsBilling.co_id 		= co_id;	
			ContractorsBilling.company		= company;	
			ContractorsBilling.lookupCompany.setValue(co_id);
			ContractorsBilling.tagCompany.setTag(company);
			ContractorsBilling.company_logo = company_logo;

			ContractorsBilling.lblContractNo.setEnabled(true);	
			ContractorsBilling.lookupContractNo.setEnabled(true);	
			ContractorsBilling.tagContractNo.setEnabled(true);									
			ContractorsBilling.lookupContractNo.setLookupSQL(getLookupContractNo(co_id));

			if (ntp_no.equals("")) {}
			else {			
				ContractorsBilling.setContractDetails(surety,ntp_no);
				ContractorsBilling.lookupContractNo.setValue(contract_no);
			}
		}		
	}

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

			int column 	= tblCO.getSelectedColumn();
			int row 	= tblCO.getSelectedRow();
			String rplf = modelCO.getValueAt(row,column).toString().trim();

			if (rplf.equals("")) {}
			else {			
				RequestForPayment.drf_no  = rplf;
				RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);	

				RequestForPayment.setDRF_header(RequestForPayment.drf_no);
				RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF, RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no );	
				RequestForPayment.btnAddNew.setEnabled(false);
				RequestForPayment.btnRefresh.setEnabled(true);
				if(RequestForPayment.isPVcreated()==true) {
					RequestForPayment.btnEdit.setEnabled(false);
				} else {
					RequestForPayment.btnEdit.setEnabled(true);
				}
				RequestForPayment.mnidelete.setEnabled(false);
				RequestForPayment.mniaddrow.setEnabled(false);
			}
		}		
	}

	public void openJV(){

		JournalVoucher jv = new JournalVoucher();
		Home_JSystem.addWindow(jv);

		if(co_id.equals(""))
		{

		} 
		else 	
		{

			JournalVoucher.co_id 		= co_id;	
			JournalVoucher.company		= company;	
			JournalVoucher.lookupCompany.setValue(co_id);
			JournalVoucher.tagCompany.setTag(company);
			JournalVoucher.company_logo = company_logo;

			JournalVoucher.lblJV_no.setEnabled(true);	
			JournalVoucher.lookupJV.setEnabled(true);	
			//JournalVoucher.lookupJV.setEditable(true);	

			JournalVoucher.lookupJV.setLookupSQL(JournalVoucher.getJV_no());	

			JournalVoucher.enableButtons(true,  false,  false,  false, false, 
					false, false,  false,  false, false );

			int column 	= tblCO.getSelectedColumn();
			int row 	= tblCO.getSelectedRow();
			String jvno = modelCO.getValueAt(row,column).toString().trim();

			if (jvno.equals("")) {}
			else {

				JournalVoucher.refresh_fields();							

				JournalVoucher.jv_no = jvno;	
				JournalVoucher.lookupJV.setValue(jvno);

				JournalVoucher.setJV_header(jvno);
				JournalVoucher.displayJV_details(JournalVoucher.modelJV_account, JournalVoucher.rowHeaderJV_account, JournalVoucher.modelJV_accounttotal, jvno );								

				JournalVoucher.mnidelete.setEnabled(false);
				JournalVoucher.mniaddrow.setEnabled(false);

			}			
		}		
	}

	public void openPV(){

		if(Home_JSystem.isNotExisting("PayableVoucher")){
			PayableVoucher pv = new PayableVoucher();
			Home_JSystem.addWindow(pv);
		}
		
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

			int column 	= tblCO.getSelectedColumn();
			int row 	= tblCO.getSelectedRow();
			String rplf = modelCO.getValueAt(row,column).toString().trim();

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


	//computations
	public void computeNetAmount(String entitytype, Double wtaxrate){

		Double gr_amt  = Double.valueOf(txtGrossAmt.getText().trim().replace(",","")).doubleValue();
		Double dp_amt  = 0.00;
		Double vat_amt = (gr_amt / 1.12)*.12 ;
		Double wtax_amt= (gr_amt / 1.12)* wtax_rate/100 ;
		Double ret_amt = Double.valueOf(txtCO_ret_amt.getText().trim().replace(",","")).doubleValue();
		Double net_amt =  gr_amt-dp_amt-wtax_amt-ret_amt;

		txtCO_vat_amt.setText(df.format(vat_amt));	
		txtCO_wtax_amt.setText(df.format(wtax_amt));
		txtCO_ret_amt.setText(df.format(ret_amt));
		txtCO_net_amt.setText(df.format(net_amt)); 
		
		System.out.println("gr_amt: "+gr_amt);
		System.out.println("vat_amt: "+vat_amt);
		System.out.println("wtax_amt: "+wtax_amt);
		System.out.println("ret_amt: "+ret_amt);
		System.out.println("net_amt: "+net_amt);
		System.out.println("wtax_rate: "+wtax_rate);
		
	}
	public void editAmount(){
		

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlEditAmount, "Other Details",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		
		
		System.out.printf("ScanOption: %s", scanOption);
		
		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {
				Double contprice = Double.valueOf(txtEditAmount.getText().trim().replace(",","")).doubleValue();
				Double prev = Double.valueOf(txtPrevPerc.getText().trim().replace(",","")).doubleValue();
				Double accomp = (Double.valueOf(txtPercentage.getText().trim().replace(",","")).doubleValue());
				Double perc = (accomp-prev)/100;

					if (lookupTransaction.getText().trim().equals("03")){

						txtEditPerc.setText("100.00");	
						txtcontprice.setText(df.format(contprice));	
						txtPercentage.setText(df.format(perc));	
					}else{
						txtcontprice.setText(df.format(contprice));	
						txtPercentage.setText(df.format(perc));
					};
				
									
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}	
			System.out.printf("Value of Contract Price : %s", txtEditAmount.getText());
		} // CLOSED_OPTION
	}
	
}
