package Accounting.GeneralLedger;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelJV_acct_entries;
import Accounting.Disbursements.CheckVoucher;
import Accounting.Disbursements.DocsProcessing;
import Accounting.Disbursements.PayableVoucher;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import TaxesAndPermits.EWT_Remittance;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class DebitCreditMemo extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Debit/Credit (DB) Memo)";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlDBDtl_1;
	private JPanel pnlDBDtl_2;
	private JPanel pnlDBDtl_1a;
	private JPanel pnlDBDtl_1b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlDB;
	private JPanel pnlDB_a;
	private JPanel pnlDB_a1;
	private JPanel pnlDB_a2;
	private JPanel pnlDB_a2_1;
	private JPanel pnlDB_a2_2;
	private JPanel pnlDBDtl;
	private JPanel pnlDB_a2_3;
	private JPanel pnlDBInfo;
	private JPanel pnlDBInfo_1;
	private JPanel pnlDBDtl_2a;
	private JPanel pnlDBDtl_2b;
	private JPanel pnlDate;
	private JPanel pnlRemarks;
	private JPanel pnlDB_account;
	private JPanel pnlSouthCenterb;

	private JLabel lblCompany;
	public static JLabel lblDB_no;
	private static JLabel lblStatus;
	private static JLabel lblJV_no;
	private static JLabel lblPostedBy;
	private JLabel lblDate;
	private static JLabel lblDateTrans;
	private static JLabel lblTaggedBy;
	private static JLabel lblJVStatus;

	public static _JLookup lookupCompany;
	public static _JLookup lookupDB_no;	
	public static _JLookup lookupTranType;
	
	public static modelJV_acct_entries modelJV_account;
	public static modelJV_acct_entries modelJV_accounttotal;

	private _JTabbedPane tabCenter;	

	public static _JTagLabel tagCompany;
	public static _JTagLabel tagTranType;
	private static _JTagLabel tagPeriod;

	private static JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnRefresh;
	private JButton btnOK;
	private static JButton btnDelete;
	private static JButton btnPreview;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private static _JDateChooser dteTransaction;
	private static _JDateChooser dteEdited;
	private _JDateChooser dteRefDate;
	private static _JDateChooser dtePosted;

	private JPopupMenu menu;
	private static _JScrollPaneMain scrollJV_account;
	private static _JScrollPaneTotal scrollJV_accounttotal;	

	public static JTextArea txtJV_Remark;
	private static JXTextField txtStatus;

	public static JMenuItem mnidelete;
	public static JMenuItem mniaddrow;		

	public static _JTableMain tblJV_account;
	public static JList rowHeaderJV_account;
	private static _JTableTotal tblJV_accounttotal;	

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	

	public static String jv_no = "";   
	public static Integer db_no = 0;   
	public static String co_id = "";		
	public static String company = "";
	public static String company_logo;
	public static String memo_type;
	Integer next_seqno = 0;
	private static JMenuItem mnicopy;
	private static JMenuItem mnipaste;
	private static JMenuItem mnicopyRow;
	private JMenuItem mnipasteRow;
	private JPopupMenu menu2;
	private JMenuItem mniopenPV;
	private JMenuItem mniopenCV;
	private JMenuItem mniopenDP;
	private JMenuItem mniopenRPLF;
	private static JXTextField txtJV_no;
	private static JXTextField txtJV_status;
	private static JLabel lblTransaction;
	private JPanel pnlRemarks_a;
	private static JLabel lblRemarks;
	private JPanel pnlRemarks_b;
	private JPanel pnlRemarks_b1;
	private static JLabel lblType;
	private static JComboBox cmbType;
	private _JTagLabel tagX;
	private static JButton btnAddAmount;
	private JPanel pnlEnterAmount;
	private JPanel pnlEnterAmount_a;
	private JPanel pnlEnterAmount_a1;
	private JLabel lblCompID;
	private JLabel lblAmount;
	private JPanel pnlEnterAmount_a2;
	private JPanel pnlEnterAmount_c;
	private JButton btnCrateJV;
	private _JXFormattedTextField txtAmount;
	private _JLookup lookupCompany_b;
	private JPanel pnlEnterAmount_refund;
	private static JXTextField txtRemarks;
	private static _JLookup lookupTagBy;
	private static _JTagLabel tagTagBy;
	private static _JTagLabel tagTransaction;
	private static JButton btnAddNew;	
	static String posted_by = "";
	private static String item	= "";
	public static Boolean writeoff_jv = false;
	public static String drf_no = "";		
	public static String copy_acct_id, copy_div, copy_dept, copy_proj, copy_subproj, copy_ibu, copy_acct_desc = "";	

	public static String tran_desc = "";
	
	public static String period = "";
	public static String allowed_trans = "";


	public DebitCreditMemo() {
		super(title, true, true, true, true);
		initGUI();
	}

	public DebitCreditMemo(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public DebitCreditMemo(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(935, 590));
		this.setBounds(0, 0, 935, 590);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Row      ");
			mniaddrow = new JMenuItem("Add Row");
			mnicopy = new JMenuItem("Copy");
			mnipaste = new JMenuItem("Paste");
			mnicopyRow = new JMenuItem("Copy Row");
			mnipasteRow = new JMenuItem("Paste Row");			
			menu.add(mnidelete);
			menu.add(mniaddrow);
			JSeparator sp = new JSeparator();
			menu.add(sp);	
			menu.add(mnicopy);
			menu.add(mnipaste);
			JSeparator sp2 = new JSeparator();
			menu.add(sp2);	
			menu.add(mnicopyRow);
			menu.add(mnipasteRow);
			mnipaste.setEnabled(false);
			mnipasteRow.setEnabled(false);
			mnidelete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					removeRow();				
				}
			});
			mniaddrow.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					AddRow();
				}
			});
			mnicopy.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					copy();
				}
			});
			mnipaste.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					paste();
				}
			});
			mnicopyRow.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					copyRow();				
				}
			});
			mnipasteRow.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					pasteRow();
				}
			});
		}
		{
			menu2 = new JPopupMenu("Popup");	
			mniopenRPLF = new JMenuItem("Open Payment Request");
			mniopenPV = new JMenuItem("Open Payable Voucher");
			mniopenCV = new JMenuItem("Open Disbursement Voucher");
			mniopenDP = new JMenuItem("Open Docs Processing");	
			menu2.add(mniopenRPLF);
			menu2.add(mniopenPV);
			menu2.add(mniopenCV);
			menu2.add(mniopenDP);

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
			mniopenCV.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){					
					openCV();
				}
			});
			mniopenDP.addActionListener(new ActionListener(){				
				public void	actionPerformed(ActionEvent evt){
					openDP();
				}
			});

		}


		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 221));

			pnlComp = new JPanel(new BorderLayout(5, 0));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);	

			//company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
			pnlComp_a.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(209, 30));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			{
				lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
				pnlComp_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(105, 25));
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
							company		= (String) data[1];	
							tagCompany.setTag((String) data[1]);
							company_logo = (String) data[3];

							lblDB_no.setEnabled(true);	
							lookupDB_no.setEnabled(true);	

							lookupDB_no.setLookupSQL(getDB_details());	

							{enableButtons(true,  false,  false,  false, false, false );} 
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
				tagCompany.addMouseListener(new PopupTriggerListener_panel2());
			}	

			pnlDB = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlDB, BorderLayout.CENTER);				
			pnlDB.setPreferredSize(new java.awt.Dimension(921, 163));
			pnlDB.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlDB.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlDB_a = new JPanel(new BorderLayout(5, 5));
			pnlDB.add(pnlDB_a, BorderLayout.NORTH);	
			pnlDB_a.setPreferredSize(new java.awt.Dimension(911, 42));
			pnlDB_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlDB_a.setBorder(lineBorder);

			pnlDB_a1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlDB_a.add(pnlDB_a1, BorderLayout.WEST);	
			pnlDB_a1.setPreferredSize(new java.awt.Dimension(92, 40));	
			pnlDB_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

			{
				lblDB_no = new JLabel("Memo No.", JLabel.TRAILING);
				pnlDB_a1.add(lblDB_no);
				lblDB_no.setEnabled(false);	
				lblDB_no.setPreferredSize(new java.awt.Dimension(86, 40));
				lblDB_no.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				lblDB_no.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}						

			pnlDB_a2 = new JPanel(new BorderLayout(5, 5));
			pnlDB_a.add(pnlDB_a2, BorderLayout.CENTER);	
			pnlDB_a2.setPreferredSize(new java.awt.Dimension(814, 40));	
			pnlDB_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			pnlDB_a2_1 = new JPanel(new GridLayout(1, 1, 5, 10));
			pnlDB_a2.add(pnlDB_a2_1, BorderLayout.WEST);	
			pnlDB_a2_1.setPreferredSize(new java.awt.Dimension(142, 24));					

			{
				lookupDB_no = new _JLookup(null, "DB No.", 2, 2);
				pnlDB_a2_1.add(lookupDB_no);
				lookupDB_no.setBounds(20, 27, 20, 25);
				lookupDB_no.setReturnColumn(0);
				lookupDB_no.setEnabled(false);	
				//lookupDB_no.setEditable(false);	
				lookupDB_no.setPreferredSize(new java.awt.Dimension(157, 22));
				lookupDB_no.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){	

							refresh_fields();							

							db_no = Integer.parseInt(data[0].toString());	
							lookupDB_no.setValue(data[0].toString());
							
							txtStatus.setText(data[5].toString());		

							dteTransaction.setDate((Date) data[2]);
							txtJV_no.setText(data[1].toString());		
							txtJV_status.setText(data[6].toString());		

							lookupTagBy.setValue(data[7].toString());
							lookupTranType.setValue(data[3].toString());

							tagTagBy.setTag(data[8].toString());
							tagTransaction.setTag(data[4].toString());
							txtRemarks.setText(data[9].toString());								

							mnidelete.setEnabled(false);
							mniaddrow.setEnabled(false);
							
							memo_type = data[10].toString();
							if (memo_type.equals("DM")) {cmbType.setSelectedIndex(0);} 
							else {cmbType.setSelectedIndex(1);}
							
							displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, data[1].toString() );
							enableButtons(false,  false,  true,  true, false, true );		

						}
					}
				});
			}	

			pnlDB_a2_2 = new JPanel(new GridLayout(1, 2, 5, 0));
			pnlDB_a2.add(pnlDB_a2_2, BorderLayout.CENTER);	
			pnlDB_a2_2.setPreferredSize(new java.awt.Dimension(357, 25));	
			pnlDB_a2_2.addMouseListener(new PopupTriggerListener_panel2());

			pnlDB_a2_3 = new JPanel(new GridLayout(1, 2,5,0));
			pnlDB_a2.add(pnlDB_a2_3, BorderLayout.EAST);	
			pnlDB_a2_3.setPreferredSize(new java.awt.Dimension(310, 20));	
			pnlDB_a2_3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));			

			{
				lblStatus = new JLabel("Memo Status", JLabel.TRAILING);
				pnlDB_a2_3.add(lblStatus);
				lblStatus.setEnabled(false);	
				lblStatus.addMouseListener(new PopupTriggerListener_panel2());
			}	
			{
				txtStatus = new JXTextField("");
				pnlDB_a2_3.add(txtStatus);
				txtStatus.setEnabled(false);	
				txtStatus.setEditable(false);
				txtStatus.setBounds(120, 25, 300, 22);	
				txtStatus.setHorizontalAlignment(JTextField.CENTER);	
				txtStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				txtStatus.addMouseListener(new PopupTriggerListener_panel2());
			}	
			{
				pnlDBDtl = new JPanel(new BorderLayout(0, 5));
				pnlDB.add(pnlDBDtl, BorderLayout.WEST);	
				pnlDBDtl.setPreferredSize(new java.awt.Dimension(911, 187));

				pnlDBDtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlDBDtl.add(pnlDBDtl_1, BorderLayout.WEST);	
				pnlDBDtl_1.setPreferredSize(new java.awt.Dimension(221, 116));
				pnlDBDtl_1.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

				pnlDBDtl_1a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlDBDtl_1.add(pnlDBDtl_1a, BorderLayout.WEST);	
				pnlDBDtl_1a.setPreferredSize(new java.awt.Dimension(99, 116));
				pnlDBDtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));	

				{
					lblDateTrans = new JLabel("Trans. Date", JLabel.TRAILING);
					pnlDBDtl_1a.add(lblDateTrans);
					lblDateTrans.setEnabled(false);	
				}					
				{
					lblJV_no = new JLabel("JV No.", JLabel.TRAILING);
					pnlDBDtl_1a.add(lblJV_no);
					lblJV_no.setEnabled(false);	
				}	
				{
					lblJVStatus = new JLabel("JV Status", JLabel.TRAILING);
					pnlDBDtl_1a.add(lblJVStatus);
					lblJVStatus.setEnabled(false);	
				}

				pnlDBDtl_1b = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlDBDtl_1.add(pnlDBDtl_1b, BorderLayout.CENTER);	
				pnlDBDtl_1b.setPreferredSize(new java.awt.Dimension(135, 119));
				pnlDBDtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					dteTransaction = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDBDtl_1b.add(dteTransaction);
					dteTransaction.setBounds(485, 7, 125, 21);
					dteTransaction.setDate(Calendar.getInstance().getTime());
					dteTransaction.setEnabled(false);
					dteTransaction.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteTransaction.getDateEditor()).setEditable(false);
					dteTransaction.addPropertyChangeListener( new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
							
							Object[] month_year = getMonthYear();									
							//lookupFiscalYr.setText((String) month_year[4]);
							//lookupPeriod.setText((String) month_year[0]);
							period = (String) month_year[0];
							
						}
					});	
				}		
				{
					txtJV_no = new JXTextField("");
					pnlDBDtl_1b.add(txtJV_no);
					txtJV_no.setEnabled(false);	
					txtJV_no.setEditable(false);
					txtJV_no.setBounds(120, 25, 300, 22);	
					txtJV_no.setHorizontalAlignment(JTextField.CENTER);	
					txtJV_no.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					txtJV_no.addMouseListener(new PopupTriggerListener_panel2());
				}			
				{
					txtJV_status = new JXTextField("");
					pnlDBDtl_1b.add(txtJV_status);
					txtJV_status.setEnabled(false);	
					txtJV_status.setEditable(false);
					txtJV_status.setBounds(120, 25, 300, 22);	
					txtJV_status.setHorizontalAlignment(JTextField.CENTER);	
					txtJV_status.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					txtJV_status.addMouseListener(new PopupTriggerListener_panel2());
				}	

				//Start of Left Panel 
				pnlDBInfo = new JPanel(new BorderLayout(0,0));
				pnlDBDtl.add(pnlDBInfo, BorderLayout.EAST);
				pnlDBInfo.setPreferredSize(new java.awt.Dimension(694, 113));
				pnlDBInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				pnlDBInfo_1 = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlDBInfo.add(pnlDBInfo_1, BorderLayout.WEST);
				pnlDBInfo_1.setPreferredSize(new java.awt.Dimension(112, 113));
				pnlDBInfo_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					lblType = new JLabel("Type", JLabel.TRAILING);
					pnlDBInfo_1.add(lblType);
					lblType.setEnabled(false);	
				}	
				{
					lblTaggedBy = new JLabel("Tagged By", JLabel.TRAILING);
					pnlDBInfo_1.add(lblTaggedBy);
					lblTaggedBy.setEnabled(false);	
				}	
				{
					lblTransaction = new JLabel("Transaction", JLabel.TRAILING);
					pnlDBInfo_1.add(lblTransaction);
					lblTransaction.setEnabled(false);	
				}	

				pnlDBDtl_2 = new JPanel(new BorderLayout(5,0));
				pnlDBInfo.add(pnlDBDtl_2, BorderLayout.CENTER);
				pnlDBDtl_2.setPreferredSize(new java.awt.Dimension(203, 118));
				pnlDBDtl_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

				pnlDBDtl_2a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlDBDtl_2.add(pnlDBDtl_2a, BorderLayout.WEST);
				pnlDBDtl_2a.setPreferredSize(new java.awt.Dimension(119, 119));
				pnlDBDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					String cmb_type[] = {
							"Debit Memo",
							"Credit Memo",};					
					cmbType = new JComboBox(cmb_type);
					pnlDBDtl_2a.add(cmbType);
					cmbType.setSelectedItem(null);
					cmbType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
					cmbType.setBounds(537, 15, 160, 21);	
					cmbType.setEnabled(false);
					cmbType.setSelectedIndex(0);	
					cmbType.setPreferredSize(new java.awt.Dimension(418, 65));
					cmbType.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{	
							lookupTranType.setValue("");
							tagTransaction.setTag("");
							lookupTranType.setLookupSQL(getAcctTrans());
						}
					});
				}
				
				{
					lookupTagBy = new _JLookup(null, "Tagged By", 2, 2);
					pnlDBDtl_2a.add(lookupTagBy);
					lookupTagBy.setBounds(20, 27, 20, 25);
					lookupTagBy.setReturnColumn(0);
					lookupTagBy.setEnabled(false);	
					lookupTagBy.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupTagBy.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){		
								tran_desc = (String) data[2];						
								tagTranType.setTag(tran_desc);
							}
						}
					});	
				}		
				{
					lookupTranType = new _JLookup(null, "Transaction Type", 2, 2);
					pnlDBDtl_2a.add(lookupTranType);
					lookupTranType.setBounds(20, 27, 20, 25);
					lookupTranType.setReturnColumn(0);
					lookupTranType.setEnabled(false);	
					lookupTranType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupTranType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){		
								tran_desc = (String) data[2];						
								tagTransaction.setTag(tran_desc);
								btnAddAmount.setEnabled(true);
								
								
							}
						}
					});	
				}
				{
					btnAddAmount = new JButton("Enter Amount");
					pnlDBDtl_2a.add(btnAddAmount);
					btnAddAmount.addActionListener(this);
					btnAddAmount.setEnabled(false);
					btnAddAmount.setMnemonic(KeyEvent.VK_E);
					btnAddAmount.addActionListener(new ActionListener() {
						private JPanel panelX;

						public void actionPerformed(ActionEvent e) {

							panelX = new JPanel();
							if (lookupTranType.getText().equals("00069")||lookupTranType.getText().equals("00027"))
							{panelX = pnlEnterAmount;} else {panelX = pnlEnterAmount_refund;}

							int scanOption = JOptionPane.showOptionDialog(getContentPane(), panelX, "Enter Amount",
									JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

							if ( scanOption == JOptionPane.CLOSED_OPTION ) {
								try {} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {}				
							} // CLOSED_OPTION
						}
					});
				}

				pnlDBDtl_2b = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlDBDtl_2.add(pnlDBDtl_2b, BorderLayout.CENTER);
				pnlDBDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
				pnlDBDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				pnlDBDtl_2b.addMouseListener(new PopupTriggerListener_panel2());

				{
					tagX = new _JTagLabel("[ ]");
					pnlDBDtl_2b.add(tagX);
					tagX.setBounds(209, 27, 700, 22);
					tagX.setEnabled(false);	
					tagX.setVisible(false);	
					tagX.setPreferredSize(new java.awt.Dimension(27, 33));
					tagX.addMouseListener(new PopupTriggerListener_panel2());
				}	
				{
					tagTagBy = new _JTagLabel("[ ]");
					pnlDBDtl_2b.add(tagTagBy);
					tagTagBy.setBounds(209, 27, 700, 22);
					tagTagBy.setEnabled(false);	
					tagTagBy.setPreferredSize(new java.awt.Dimension(27, 33));
					tagTagBy.addMouseListener(new PopupTriggerListener_panel2());
				}	
				{
					tagTransaction = new _JTagLabel("[ ]");
					pnlDBDtl_2b.add(tagTransaction);
					tagTransaction.setBounds(209, 27, 700, 22);
					tagTransaction.setEnabled(false);	
					tagTransaction.setPreferredSize(new java.awt.Dimension(27, 33));
					tagTransaction.addMouseListener(new PopupTriggerListener_panel2());
				}
			}	
		}
		{
			pnlDate= new JPanel();
			pnlDate.setLayout(null);
			pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
			{
				lblDate = new JLabel();
				pnlDate.add(lblDate);
				lblDate.setBounds(5, 15, 160, 20);
				lblDate.setText("Reference Doc. Date :");
			}
			{
				dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlDate.add(dteRefDate);
				dteRefDate.setBounds(130, 15, 125, 21);
				dteRefDate.setDate(null);
				dteRefDate.setEnabled(true);
				dteRefDate.setDateFormatString("yyyy-MM-dd");
				((JTextFieldDateEditor)dteRefDate.getDateEditor()).setEditable(false);
				dteRefDate.setDate(Calendar.getInstance().getTime());
				dteRefDate.addPropertyChangeListener( new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
					}
				});	
			}
			{
				btnOK = new JButton();
				pnlDate.add(btnOK);
				btnOK.setBounds(95, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
						optionPaneWindow.dispose();
					}
				});
			}
		}
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				
			pnlTable.addMouseListener(new PopupTriggerListener_panel2());

			{
				pnlRemarks = new JPanel(new BorderLayout(5, 5));
				pnlTable.add(pnlRemarks, BorderLayout.NORTH);	
				pnlRemarks.setPreferredSize(new java.awt.Dimension(923, 56));	
				pnlRemarks.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
				pnlRemarks.setBorder(lineBorder);

				{
					pnlRemarks_a = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlRemarks.add(pnlRemarks_a, BorderLayout.WEST);	
					pnlRemarks_a.setPreferredSize(new java.awt.Dimension(100, 40));	
					pnlRemarks_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblRemarks = new JLabel("JV Particular", JLabel.TRAILING);
						pnlRemarks_a.add(lblRemarks);
						lblRemarks.setEnabled(false);	
						lblRemarks.setPreferredSize(new java.awt.Dimension(117, 40));
					}						

					pnlRemarks_b = new JPanel(new BorderLayout(5, 5));
					pnlRemarks.add(pnlRemarks_b, BorderLayout.CENTER);	
					pnlRemarks_b.setPreferredSize(new java.awt.Dimension(797, 54));	
					pnlRemarks_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlRemarks_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlRemarks_b.add(pnlRemarks_b1, BorderLayout.WEST);	
						pnlRemarks_b1.setPreferredSize(new java.awt.Dimension(800, 46));					

						{
							txtRemarks = new JXTextField("");
							pnlRemarks_b1.add(txtRemarks);
							txtRemarks.setEnabled(false);	
							txtRemarks.setBounds(120, 25, 300, 22);	
							txtRemarks.setHorizontalAlignment(JTextField.LEFT);
							txtRemarks.setPreferredSize(new java.awt.Dimension(813, 32));
						}	
					}			
				}
			}

			pnlDB = new JPanel();
			pnlTable.add(pnlDB, BorderLayout.CENTER);
			pnlDB.setLayout(new BorderLayout(5, 5));
			pnlDB.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlDB.setBorder(lineBorder);		
			pnlDB.addMouseListener(new PopupTriggerListener_panel());	





			tabCenter = new _JTabbedPane();
			pnlDB.add(tabCenter, BorderLayout.CENTER);				

			{			
				pnlDB_account = new JPanel(new BorderLayout());
				tabCenter.addTab("JV Entries", null, pnlDB_account, null);
				pnlDB_account.setPreferredSize(new java.awt.Dimension(1183, 365));

				{
					scrollJV_account = new _JScrollPaneMain();
					pnlDB_account.add(scrollJV_account, BorderLayout.CENTER);
					{
						modelJV_account = new modelJV_acct_entries();

						tblJV_account = new _JTableMain(modelJV_account);
						scrollJV_account.setViewportView(tblJV_account);						
						tblJV_account.addMouseListener(this);	
						tblJV_account.setSortable(false);
						tblJV_account.addMouseListener(new PopupTriggerListener_panel());
						tblJV_account.getColumnModel().getColumn(0).setPreferredWidth(90);
						tblJV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(2).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(3).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(4).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(5).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(6).setPreferredWidth(40);
						tblJV_account.getColumnModel().getColumn(8).setPreferredWidth(100);
						tblJV_account.getColumnModel().getColumn(9).setPreferredWidth(100);

						tblJV_account.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								computeJV_amount();		
								//setTableWidth();
							}							
							public void keyPressed(KeyEvent e) {
								computeJV_amount();
								//setTableWidth();
							}

						}); 
						tblJV_account.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblJV_account.rowAtPoint(e.getPoint()) == -1){
									tblJV_accounttotal.clearSelection();

								}else{
									tblJV_account.setCellSelectionEnabled(true);

								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblJV_account.rowAtPoint(e.getPoint()) == -1){
									tblJV_accounttotal.clearSelection();

								}else{
									tblJV_account.setCellSelectionEnabled(true);

									
								}
							}
						});						
					}
					{
						rowHeaderJV_account = tblJV_account.getRowHeader22();
						scrollJV_account.setRowHeaderView(rowHeaderJV_account);						
						scrollJV_account.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollJV_accounttotal = new _JScrollPaneTotal(scrollJV_account);
					pnlDB_account.add(scrollJV_accounttotal, BorderLayout.SOUTH);
					{
						modelJV_accounttotal = new modelJV_acct_entries();
						modelJV_accounttotal.addRow(new Object[] { "Total",null,  null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null});

						tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
						tblJV_accounttotal.setFont(dialog11Bold);
						scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
						((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);
					}
				}
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(923, 35));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{				
				{
					btnAddNew = new JButton("Create New");
					pnlSouthCenterb.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenterb.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenterb.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");	
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}	
				{
					btnDelete = new JButton("Delete");
					pnlSouthCenterb.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.addActionListener(this);
					btnDelete.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
			{
				pnlDate= new JPanel();
				pnlDate.setLayout(null);
				pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
				{
					lblDate = new JLabel();
					pnlDate.add(lblDate);
					lblDate.setBounds(5, 15, 160, 20);
					lblDate.setText("Reference Doc. Date :");
				}
				{
					dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDate.add(dteRefDate);
					dteRefDate.setBounds(130, 15, 125, 21);
					dteRefDate.setDate(null);
					dteRefDate.setEnabled(true);
					dteRefDate.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteRefDate.getDateEditor()).setEditable(false);
					dteRefDate.setDate(Calendar.getInstance().getTime());
					dteRefDate.addPropertyChangeListener( new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
						}
					});	
				}
				{
					btnOK = new JButton();
					pnlDate.add(btnOK);
					btnOK.setBounds(95, 58, 69, 22);
					btnOK.setText("OK");
					btnOK.setFocusable(false);
					btnOK.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
							optionPaneWindow.dispose();
						}
					});
				}
			}
		}
		{
			pnlEnterAmount = new JPanel();
			pnlEnterAmount.setLayout(new BorderLayout(5, 5));
			pnlEnterAmount.setBorder(lineBorder);		
			pnlEnterAmount.setPreferredSize(new java.awt.Dimension(380, 123));		

			{		
				pnlEnterAmount_a = new JPanel(new BorderLayout(5, 5));
				pnlEnterAmount.add(pnlEnterAmount_a, BorderLayout.NORTH);				
				pnlEnterAmount_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlEnterAmount_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlEnterAmount_a.setPreferredSize(new java.awt.Dimension(378, 77));		

				{
					pnlEnterAmount_a1 = new JPanel(new GridLayout(2, 5, 5, 5));
					pnlEnterAmount_a.add(pnlEnterAmount_a1, BorderLayout.WEST);				
					pnlEnterAmount_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlEnterAmount_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlEnterAmount_a1.setPreferredSize(new java.awt.Dimension(90, 120));		

					{
						lblCompID = new JLabel("Company", JLabel.TRAILING);
						pnlEnterAmount_a1.add(lblCompID);
						lblCompID.setEnabled(true);	
						lblCompID.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblAmount = new JLabel("Amount", JLabel.TRAILING);
						pnlEnterAmount_a1.add(lblAmount);
						lblAmount.setEnabled(true);	
						lblAmount.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
				}	
				{
					pnlEnterAmount_a2 = new JPanel(new GridLayout(2,5,5, 5));
					pnlEnterAmount_a.add(pnlEnterAmount_a2, BorderLayout.CENTER);				
					pnlEnterAmount_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlEnterAmount_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlEnterAmount_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						lookupCompany_b = new _JLookup(null, "Company",0,2);
						pnlEnterAmount_a2.add(lookupCompany_b);
						lookupCompany_b.setLookupSQL(SQL_COMPANY());
						lookupCompany_b.setReturnColumn(0);
						lookupCompany_b.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
								}
							}
						});
					}			
					{					
						txtAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlEnterAmount_a2.add(txtAmount);
						txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAmount.setText("0.00");
						txtAmount.setEnabled(true);
						txtAmount.setBounds(120, 0, 72, 22);					
					}
				}

				pnlEnterAmount_c = new JPanel(new BorderLayout(5, 5));
				pnlEnterAmount.add(pnlEnterAmount_c, BorderLayout.SOUTH);				
				pnlEnterAmount_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlEnterAmount_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlEnterAmount_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnCrateJV = new JButton("Create JV Entries");
					pnlEnterAmount_c.add(btnCrateJV);
					btnCrateJV.addActionListener(this);
					btnCrateJV.setEnabled(true);
					btnCrateJV.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							createJV_intercomp();
						}
					});
				}
			}
		}
		{
			pnlEnterAmount_refund = new JPanel();
			pnlEnterAmount_refund.setLayout(new BorderLayout(5, 5));
			pnlEnterAmount_refund.setBorder(lineBorder);		
			pnlEnterAmount_refund.setPreferredSize(new java.awt.Dimension(380, 123));		

			{		
				pnlEnterAmount_a = new JPanel(new BorderLayout(5, 5));
				pnlEnterAmount_refund.add(pnlEnterAmount_a, BorderLayout.NORTH);				
				pnlEnterAmount_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlEnterAmount_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlEnterAmount_a.setPreferredSize(new java.awt.Dimension(378, 77));		

				{
					pnlEnterAmount_a1 = new JPanel(new GridLayout(2, 5, 5, 5));
					pnlEnterAmount_a.add(pnlEnterAmount_a1, BorderLayout.WEST);				
					pnlEnterAmount_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlEnterAmount_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlEnterAmount_a1.setPreferredSize(new java.awt.Dimension(90, 120));		

					{
						lblCompID = new JLabel("Company", JLabel.TRAILING);
						pnlEnterAmount_a1.add(lblCompID);
						lblCompID.setEnabled(true);	
						lblCompID.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblAmount = new JLabel("Amount", JLabel.TRAILING);
						pnlEnterAmount_a1.add(lblAmount);
						lblAmount.setEnabled(true);	
						lblAmount.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
				}	
				{
					pnlEnterAmount_a2 = new JPanel(new GridLayout(2,5,5, 5));
					pnlEnterAmount_a.add(pnlEnterAmount_a2, BorderLayout.CENTER);				
					pnlEnterAmount_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlEnterAmount_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlEnterAmount_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						lookupCompany_b = new _JLookup(null, "Company",0,2);
						pnlEnterAmount_a2.add(lookupCompany_b);
						lookupCompany_b.setLookupSQL(SQL_COMPANY());
						lookupCompany_b.setReturnColumn(0);
						lookupCompany_b.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
								}
							}
						});
					}			
					{					
						txtAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlEnterAmount_a2.add(txtAmount);
						txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAmount.setText("0.00");
						txtAmount.setEnabled(true);
						txtAmount.setBounds(120, 0, 72, 22);					
					}
				}

				pnlEnterAmount_c = new JPanel(new BorderLayout(5, 5));
				pnlEnterAmount_refund.add(pnlEnterAmount_c, BorderLayout.SOUTH);				
				pnlEnterAmount_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlEnterAmount_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlEnterAmount_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnCrateJV = new JButton("Create JV Entries");
					pnlEnterAmount_c.add(btnCrateJV);
					btnCrateJV.addActionListener(this);
					btnCrateJV.setEnabled(true);
					btnCrateJV.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							createJV_intercomp();
						}
					});
				}
			}
		}
		
	}


	//display tables
	public static void displayJV_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String rec_no) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"---------display JV details XX \r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"coalesce(a.acct_id,''),\r\n" + 
			"coalesce(a.div_id,''),\r\n" + 
			"coalesce(a.dept_id,''),\r\n" + 
			"coalesce(a.sect_id,''),\r\n" + 
			"coalesce(a.project_id,''),\r\n" + 
			"coalesce(a.sub_projectid,''),\r\n" + 
			"coalesce(a.inter_busunit_id,''),\r\n" + 
			"coalesce(b.acct_name,''),\r\n" + 
			"( case when a.bal_side = 'D' then a.tran_amt else 0.00 end ) as debit,\r\n" + 
			"( case when a.bal_side = 'C' then a.tran_amt else 0.00 end ) as credit,\r\n" + 
			"(coalesce(a.ref_no,'')||coalesce(d.description,'')) as ref_no  \r\n" + 
			"\r\n" + 
			"from rf_jv_detail a\r\n" + 
			"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n" +
			"left join rf_jv_header c on a.jv_no = c.jv_no " +
			"left join mf_unit_info d on a.pbl_id = d.pbl_id  and a.project_id = d.proj_id \n" + 
			"\r\n" + 
			"where trim(a.jv_no) = trim('"+rec_no+"') and a.co_id = '"+co_id+"' and a.status_id = 'A' \n\n " +
			//"(case when c.remarks like 'To set-up Sales%' then order by a.pbl_id, a.bal_side desc else " +
			"order by a.pbl_id, a.bal_side desc, a.entry_no, a.line_no \n\n " ;

		System.out.printf("SQL #2: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalJV(modelMain, modelTotal);			
		}		

		else {
			modelJV_accounttotal = new modelJV_acct_entries();
			modelJV_accounttotal.addRow(new Object[] { "Total",null,  null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null});

			tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
			tblJV_accounttotal.setFont(dialog11Bold);
			scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
			((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);}

		setTableWidth();

		//enable_fields(false);		
		adjustRowHeight_account();

	}	

	public static void creatJVtable(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''  union all  \r\n" + 
			"select '', '', '', '', '', '', '', '', 0.00, 0.00, ''   \r\n" ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalJV(modelMain, modelTotal);			
		}		


		else {
			modelJV_accounttotal = new modelJV_acct_entries();
			modelJV_accounttotal.addRow(new Object[] { "Total", null,  null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null});

			tblJV_accounttotal = new _JTableTotal(modelJV_accounttotal, tblJV_account);
			tblJV_accounttotal.setFont(dialog11Bold);
			scrollJV_accounttotal.setViewportView(tblJV_accounttotal);
			((_JTableTotal) tblJV_accounttotal).setTotalLabel(0);}

		setTableWidth();

		adjustRowHeight_account();
		modelJV_account.setEditable(true);
	}	

	public static void setJV_header(String req_no){//used

		Object[] jv_hrd = getJVdetails(req_no);	
		tran_desc = (String) jv_hrd[9];

		dteTransaction.setDate((Date) jv_hrd[1]);
		dteEdited.setDate((Date) jv_hrd[2]);
		dtePosted.setDate((Date) jv_hrd[3]);
		lookupTranType.setText((String) jv_hrd[4]);
		txtStatus.setText((String) jv_hrd[7]);
		txtJV_Remark.setText((String) jv_hrd[8]);
		tagTranType.setTag(tran_desc);

		posted_by     = (String) jv_hrd[10];

		String status = txtStatus.getText().trim();
		if(status.equals("ACTIVE")) 
		{enableButtons(true,  false,  false,  false, false, false );}
		else if (status.equals("TAGGED")) 
		{enableButtons(true,  false,  false,  false, false, false );}
		else if (status.equals("POSTED")) 
		{}
		else 
		{enableButtons(true,  false,  false,  false, false, false );}	

	}


	//Enable/Disable all components inside JPanel	
	public static void enable_fields(Boolean x){//
		
		lblStatus.setEnabled(x);	
		txtStatus.setEnabled(x);	

		lblDateTrans.setEnabled(x);
		dteTransaction.setEnabled(x);
		
		lblJV_no.setEnabled(x);
		txtJV_no.setEnabled(x);	
		lblJVStatus.setEnabled(x);	
		txtJV_status.setEnabled(x);			
		
		lblTaggedBy.setEnabled(x);	
		lookupTagBy.setEnabled(x);	
		tagTagBy.setEnabled(x);	
		lblTransaction.setEnabled(x);	
		lookupTranType.setEnabled(x);	
		tagTransaction.setEnabled(x);	
		
		lblRemarks.setEnabled(x);
		txtRemarks.setEnabled(x);	
		
		cmbType.setEnabled(x);
		lblType.setEnabled(x);	
		btnAddAmount.setEnabled(x);

		mniaddrow.setEnabled(x);
		mnidelete.setEnabled(x);
		mnicopy.setEnabled(x);
		mnicopyRow.setEnabled(x);
	}

	public static void refresh_fields(){//used

		lookupDB_no.setValue("");
		txtStatus.setText("");		

		dteTransaction.setDate(Calendar.getInstance().getTime());
		txtJV_no.setText("");		
		txtJV_status.setText("");		

		lookupTagBy.setValue("");
		lookupTranType.setValue("");

		tagTagBy.setTag("");
		tagTransaction.setTag("");
		txtRemarks.setText("");
		
		cmbType.setSelectedIndex(0);
		
	}
		
	public static void refresh_tablesMain(){//used

		//reset table 1
		FncTables.clearTable(modelJV_account);
		FncTables.clearTable(modelJV_accounttotal);			
		rowHeaderJV_account = tblJV_account.getRowHeader22();
		scrollJV_account.setRowHeaderView(rowHeaderJV_account);	
		modelJV_accounttotal.addRow(new Object[] { "Total", null, null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null});

	}

	public static void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f ){

		btnAddNew.setEnabled(a);
		btnSave.setEnabled(b);
		btnPreview.setEnabled(c);
		btnRefresh.setEnabled(d);		
		btnDelete.setEnabled(e);	
		btnCancel.setEnabled(f);	
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//used

		if(e.getActionCommand().equals("Refresh")){	refresh();	} //ok

		if(e.getActionCommand().equals("Cancel")) { cancel(); }  //ok

		if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4")==true) { add(); } //ok
		else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create a new Debit/Credit Memo.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4")==true) {edit(); }  //ok
		else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit JV.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4")==true) { saveEntries(); }	//ok
		else if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "4")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to post JV.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if(e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "4")==true){	preview();	}
		else if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "4")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print JV.","Information",JOptionPane.INFORMATION_MESSAGE); }

	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2)) {
			clickTableColumn_account();
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

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	class PopupTriggerListener_panel2 extends MouseAdapter {
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

	private void refresh(){//used		
		
		displayJV_details(modelJV_account, rowHeaderJV_account, modelJV_accounttotal, txtJV_no.getText().trim() );			

		enableButtons(false,  false,  true,  true, false, true );

		mnidelete.setEnabled(false);
		mniaddrow.setEnabled(false);

		JOptionPane.showMessageDialog(null,"Debit/Credit Memo details refreshed.","Information",JOptionPane.INFORMATION_MESSAGE);

	}

	private void cancel(){//used
		
		if(!btnSave.isEnabled())  {
			enable_fields(false);
			refresh_tablesMain();
			refresh_fields();
			{enableButtons(true,  false,  false,  false, false, false );}
			lblDB_no.setEnabled(true);	
			lookupDB_no.setEnabled(true);	
			modelJV_account.setEditable(false);
			mnipaste.setEnabled(false);
			mnipasteRow.setEnabled(false);
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				enable_fields(false);
				refresh_tablesMain();
				refresh_fields();
				{enableButtons(true,  false,  false,  false, false, false );}
				lblDB_no.setEnabled(true);	
				lookupDB_no.setEnabled(true);	
				modelJV_account.setEditable(false);
				mnipaste.setEnabled(false);
				mnipasteRow.setEnabled(false);
			} 	else {}			
		}
	}

	public static void add(){//used

		refresh_tablesMain();		
		lblDB_no.setEnabled(false);	
		lookupDB_no.setEnabled(false);		

		txtStatus.setText("ACTIVE");	

		{enableButtons(false, true,  false,  false, false, true );} 	

		lblTaggedBy.setEnabled(true);	
		lookupTranType.setEnabled(true);	
		tagTransaction.setEnabled(true);
		txtRemarks.setEditable(true);	
		txtRemarks.setEnabled(true);	
		lblRemarks.setEnabled(true);	
		lblDateTrans.setEnabled(true);	
		lblTaggedBy.setEnabled(true);	
		lookupTagBy.setEnabled(true);	
		tagTagBy.setEnabled(true);	
		lblTransaction.setEnabled(true);	
		lookupTranType.setEnabled(true);	
		tagTransaction.setEnabled(true);	
		lookupTranType.setLookupSQL(getAcctTrans());

		lookupTagBy.setText(UserInfo.EmployeeCode);
		tagTagBy.setText(UserInfo.FullName);
		cmbType.setEnabled(true);
		lblType.setEnabled(true);	

		creatJVtable(modelJV_account, rowHeaderJV_account, modelJV_accounttotal );	

		mnidelete.setEnabled(true);
		mniaddrow.setEnabled(true);
		mnicopy.setEnabled(true);
		mnicopyRow.setEnabled(true);
	}

	private void edit(){//used		

		lblDB_no.setEnabled(true);	
		lookupDB_no.setEnabled(true);	
		//lookupDB_no.setEditable(false);			

		txtStatus.setText("ACTIVE");	

		{enableButtons(true,  false,  false,  false, false, false );}

		lblTaggedBy.setEnabled(true);	
		lookupTranType.setEnabled(true);	
		//lookupTranType.setEditable(true);
		tagTranType.setEnabled(true);	
		lblPostedBy.setEnabled(true);
		//lookupPeriod.setEditable(true);	
		tagPeriod.setEnabled(true);	
		dteTransaction.setEnabled(false);
		lblDateTrans.setEnabled(false);	
		txtJV_Remark.setEditable(true);	
		txtJV_Remark.setEnabled(true);			

		mnidelete.setEnabled(true);
		mniaddrow.setEnabled(true);
		mnicopy.setEnabled(true);
		mnicopyRow.setEnabled(true);
		modelJV_account.setEditable(true);

	}

	private void saveEntries(){//used

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter transaction type.", "Information", 
				JOptionPane.WARNING_MESSAGE);}

		else {	

			if(checkNetAmount()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Net JV amount must be greater than zero.", "Information", 
					JOptionPane.WARNING_MESSAGE);}
			else {		

				if(checkAcctID_ifcomplete()==false)
				{JOptionPane.showMessageDialog(getContentPane(), "Please enter a missing account ID.", "Information", 
						JOptionPane.WARNING_MESSAGE);}

				else {	

					if(checkAcctBalanceIfEqual()==false)
					{JOptionPane.showMessageDialog(getContentPane(), "Debit and Credit balance totals are not equal", "Information", 
							JOptionPane.WARNING_MESSAGE);}

					else {	

						if(checkDebitCreditAmount()==false)
						{JOptionPane.showMessageDialog(getContentPane(), "Debit and Credit amounts cannot both be > 0", "Information", 
								JOptionPane.WARNING_MESSAGE);}

						else {	

							if(checkCostCenter()==false)
							{if (JOptionPane.showConfirmDialog(getContentPane(), "Missing Info - project / subproject, proceed anyway?", "Confirmation", 
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
								executeSave(); }
							}

							else { executeSave(); 
							} 								

						}
					}
				}

			}

		}
	}
	
	private void preview(){//used
		String criteria = "Debit Credit Memo";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("memo_type", cmbType.getSelectedItem().toString().toUpperCase());
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("jv_no", lookupDB_no.getText().trim());
		mapParameters.put("jv_date", dteTransaction.getDate());

		FncReport.generateReport("/Reports/rptDebit_Credit_Memo.jasper", reportTitle, company, mapParameters);

		if (checkIfHasWtax()==true){EWT_Remittance.previewJV(jv_no, co_id);}
	}

	private void executeSave(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {	

			setJV_no();
			pgUpdate db = new pgUpdate();	

			//save
			insertJV_header(jv_no, db);
			insertJV_detail(jv_no, db);	
			insertDB(db);
			db.commit();
			
			lookupDB_no.setText(db_no.toString());
			txtJV_no.setText(jv_no);
			txtJV_status.setText("ACTIVE");
			enableButtons(false,  false,  true,  false, false, true );			
			
			JOptionPane.showMessageDialog(getContentPane(),"New Debit/Credit Memo and Journal Voucher (JV) " + "\n" +
					"transaction saved.","Information",JOptionPane.INFORMATION_MESSAGE);						


			lblDB_no.setEnabled(true);	
			lookupDB_no.setEnabled(true);
			
		} else {}
	}


	//select, lookup and get statements	
	public static String getDB_details(){//used

		return 

		"select \r\n" + 
		"a.db_no as \"DB No.\",  \n" +  		 	//0
		"a.jv_no  as \"JV No.\",  \n" +				//1		
		"a.tran_date as \"DB Date\",  \n" +			//2
		"a.tran_id  as \"Tran. ID\" ,  \n" +		//3
		"b.tran_desc as \"Tran. Desc.\", \n " +		//4
		"c.status_desc as \"DB Status\", \n " +		//5
		"e.status_desc as \"JV Status\", \n " +		//6
		"a.tagged_by as \"Tagged By Code\", \n " +	//7
		"g.entity_name as \"Tagged By Name\", \n " +//8
		"a.remarks, \n\n " + 						//9
		"a.memo_type \n\n " + 						//10
		

		"from rf_debit_credit_memo a  \r\n" + 		
		"left join mf_acctg_trans b on a.tran_id = b.tran_id \n"  +
		"left join mf_record_status c on a.status_id = c.status_id \n" +
		"left join rf_jv_header d on a.jv_no = d.jv_no and a.co_id = d.co_id  \n" +
		"left join mf_record_status e on d.status_id = e.status_id \n" +
		"left join em_employee f on a.tagged_by = f.emp_code   \n" +
		"left join rf_entity g on f.entity_id = g.entity_id   \n" +
		
		"where a.co_id = '"+co_id+"' " +
		"order by a.db_no desc" ;			

	}

	public static String getAcctTrans(){//used
		
		if (cmbType.getSelectedIndex()==0) {allowed_trans = "'00069','00027'";}
		if (cmbType.getSelectedIndex()==1) {allowed_trans = "'00022'";}
		
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.tran_id as \"Tran ID\",\r\n" + 
		"a.system_id  as \"System ID\", " +
		"a.tran_desc  as \"Tran. Desc.\" ,\r\n" + 
		"b.status_desc  as \"Status\" \r\n" + 
		"\r\n" + 
		"from mf_acctg_trans a\r\n" + 
		"left join mf_record_status b on a.status_id=b.status_id\r\n" + 
		"where co_id = '"+co_id+"' \n" +
		"and a.tran_id in ("+allowed_trans+")"  ;		
	}

	public static String getPeriod(){//Used
		return 
		"select '01' as \"ID\", 'JANUARY' as \"Period Name\" union all "  +
		"select '02' as \"ID\", 'FEBRUARY' as \"Period Name\" union all "  +
		"select '03' as \"ID\", 'MARCH' as \"Period Name\" union all "  +
		"select '04' as \"ID\", 'APRIL' as \"Period Name\" union all "  +
		"select '05' as \"ID\", 'MAY' as \"Period Name\" union all "  +
		"select '06' as \"ID\", 'JUNE' as \"Period Name\" union all "  +
		"select '07' as \"ID\", 'JULY' as \"Period Name\" union all "  +
		"select '08' as \"ID\", 'AUGUST' as \"Period Name\" union all "  +
		"select '09' as \"ID\", 'SEPTEMBER' as \"Period Name\" union all "  +
		"select '10' as \"ID\", 'OCTOBER' as \"Period Name\" union all "  +
		"select '11' as \"ID\", 'NOVEMBER' as \"Period Name\" union all "  +
		"select '12' as \"ID\", 'DECEMBER' as \"Period Name\" union all "  +
		"select '13' as \"ID\", '13TH MONTH' as \"Period Name\" union all "  +
		"select '14' as \"ID\", '14TH MONTH' as \"Period Name\" union all "  +
		"select '15' as \"ID\", '15TH MONTH' as \"Period Name\" union all "  +
		"select '99' as \"ID\", 'CLOSING PERIOD' as \"Period Name\"  "  ;

	}	

	public String getChartofAccount(){//used

		String sql = "select " +
		"acct_id as \"Acct ID\", " +
		"acct_name as \"Acct Name\",    " +
		"bs_is as \"Balance\"  " +
		"from mf_boi_chart_of_accounts where status_id = 'A' and w_subacct is null ";		
		return sql;

	}

	public String getDivision(){//used

		String sql = "select division_code as \"Div Code\", " +
		"division_name as \"Div Name\", " +
		"division_alias as \"Div Alias\" " +
		"from mf_division " ;		
		return sql;

	}	

	public String getDepartment(_JTableMain table, DefaultTableModel model, Integer col_no){//used

		int row = table.getSelectedRow();
		String div = model.getValueAt(row,col_no).toString();

		String sql = "select dept_code as \"Dept Code\", " +
		"dept_name as \"Dept Name\", " +
		"dept_alias as \"Dept Alias\" " +
		"from mf_department " ;
		if (div.equals("")){sql = sql + "";} 
		else {sql = sql + "where division_code = '"+div+"' ";}

		return sql;

	}	

	public String getProject(){//used

		String sql = "select a.proj_id as \"Project ID\", " +
		"a.proj_name as \"Project Name\", " +
		"a.proj_alias as \"Project Alias\", " +
		"b.sub_proj_id as \"SubProject ID\", " +
		"a.vatable as \"Vatable\" " +
		"from mf_project a " +
		"left join ( select distinct on (proj_id) proj_id, sub_proj_id from mf_unit_info ) b  on a.proj_id=b.proj_id " ;		
		return sql;

	}	

	public String getSubproject(_JTableMain table, DefaultTableModel model, Integer col_no){//used
		int row = table.getSelectedRow();
		String proj = model.getValueAt(row,col_no).toString();
		String sql = 
			"select \r\n" + 
			"distinct on (a.proj_id, a.sub_proj_id)\r\n" + 
			"\r\n" + 
			"a.sub_proj_id  as \"Subproj Code\",\r\n" + 
			"a.phase as \"Phase\",  \r\n" + 
			"a.proj_id as \"Proj Code\",\r\n" + 
			"b.proj_name as \"Proj Name\"  \r\n" + 

			"from mf_unit_info a\r\n" + 
			"left join mf_project b on a.proj_id = b.proj_id\r\n" + 
			"where co_id = '"+co_id+"' and a.proj_id = '"+proj+"'   " +
			"and sub_proj_id is not null or sub_proj_id != '' " ;		
		return sql;

	}	

	public static Object [] getJVdetails(String rec_no) {//used

		String strSQL = 
			"--- Display JV Header\r\n" + 

			"select \r\n" + 
			"\r\n" + 
			"a.jv_no,\r\n" + 
			"a.jv_date,\r\n" + 
			"a.date_edited,\r\n" + 
			"a.date_posted, \n"+
			"a.tran_id,\r\n" + 
			"a.fiscal_yr,\r\n" + 
			"a.period_id,\r\n" + 
			"trim(c.status_desc),\r\n" + 
			"a.remarks, \n" +
			"b.tran_desc, \n" +
			"a.posted_by   \n"+
			"\r\n" + 
			"from rf_jv_header a\r\n" + 
			"left join mf_acctg_trans b on a.tran_id = b.tran_id\r\n" + 
			"left join mf_record_status c on a.status_id = c.status_id  \n" +			
			"where trim(a.jv_no) = trim('"+rec_no+"') and a.co_id = '"+co_id+"'   " ;		

		System.out.printf("SQL #1: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	


	//check and validate		
	private Boolean checkCompleteDetails(){//used

		boolean x = false;		

		String transaction_type;

		transaction_type = lookupTranType.getText();

		if (transaction_type.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}

	private Boolean checkNetAmount(){//used

		boolean x = false;		

		Double net_amt 	= Double.parseDouble(modelJV_accounttotal.getValueAt(0,8).toString());	
		if (net_amt>0) {x=true;} 
		else if (net_amt<0) {x=false;} 
		else {x=false;}

		return x;

	}

	private Boolean checkAcctID_ifcomplete(){//used

		boolean x = true;		

		int rw = tblJV_account.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	
			Double debit 	= Double.parseDouble(modelJV_account.getValueAt(w,8).toString());	
			Double credit 	= Double.parseDouble(modelJV_account.getValueAt(w,9).toString());	
			Double total 	= debit + credit;

			if (total>0) {

				String acct_id = modelJV_account.getValueAt(w,0).toString().trim();
				if(acct_id.equals("")){x=false; break;} else {}


			} else {}		
			w++;
		}
		return x;

	}	

	private Boolean checkCostCenter(){//used

		boolean x = true;

		int rw = tblJV_account.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	

			String acct_id 	= tblJV_account.getValueAt(w,0).toString();	

			if (!acct_id.equals("")) {

				String project 	= "";	
				String subproj 	= "";	

				try { project 	= tblJV_account.getValueAt(w,4).toString();} catch (NullPointerException e) { project 	= ""; }
				try { subproj 	= tblJV_account.getValueAt(w,5).toString();} catch (NullPointerException e) { subproj 	= ""; }

				if (project.equals("")||subproj.equals("")) { x=false;  break; } 
				else {x=true;}	

			} else {}			

			w++;
		}

		return x;

	}	

	private Boolean checkAcctBalanceIfEqual(){//used

		boolean x = true;

		Double debit 	= Double.parseDouble(modelJV_accounttotal.getValueAt(0,8).toString());	
		Double credit 	= Double.parseDouble(modelJV_accounttotal.getValueAt(0,9).toString());	
		Double diff		= debit - credit ;

		if (diff!=0) { x=false; } 
		else {x=true;}		

		return x;

	}	

	private Boolean checkDebitCreditAmount(){//used

		boolean x = true;		

		int rw = tblJV_account.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	

			Double debit 	= Double.parseDouble(modelJV_account.getValueAt(w,8).toString());	
			Double credit 	= Double.parseDouble(modelJV_account.getValueAt(w,9).toString());			

			if (debit>0&&credit>0) {
				x=false; break;
			} else {}		
			w++;
		}
		return x;

	}

	private Boolean checkIfHasWtax(){

		boolean x = false;		

		int rw = tblJV_account.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	
			String acct	= tblJV_account.getValueAt(w,0).toString();	
			if (acct.equals("03-01-06-002")) {x=true; break;} else {}		
			w++;
		}
		return x;

	}	


	//table operations
	public static void totalJV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal debit 		= new BigDecimal(0.00);	
		BigDecimal credit		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { debit 	= debit.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { debit 	= debit.add(new BigDecimal(0.00)); }

			try { credit 	= credit.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { credit 	= credit.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null,  null, null, null, null, null,null, debit, credit });		
	}

	private void computeJV_amount(){//used

		int rw = tblJV_account.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			Double debit	= Double.parseDouble(modelJV_account.getValueAt(x,8).toString());	
			Double credit	= Double.parseDouble(modelJV_account.getValueAt(x,9).toString());

			BigDecimal debit_bd 		= new BigDecimal(debit);		
			BigDecimal credit_bd 		= new BigDecimal(credit);

			modelJV_account.setValueAt(debit_bd, x, 8);
			modelJV_account.setValueAt(credit_bd, x, 9);
			x++;
		}
		totalJV(modelJV_account, modelJV_accounttotal);
	}

	private void clickTableColumn_account() {//used
		int column = tblJV_account.getSelectedColumn();
		int row = tblJV_account.getSelectedRow();				

		Integer x[] = {0,1,2,3,4,5,6,7,8,9,10};
		String sql[] = {getChartofAccount(),getDivision(),getDepartment(tblJV_account, modelJV_account,1),"",getProject(),
				getSubproject(tblJV_account, modelJV_account,4),"","","","",""};	
		String lookup_name[] = {"Chart of Account","Division","Department","","Project","Subproject","",""};		

		if (column == x[column] && modelJV_account.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0) {			
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt(data[1], row, column+7);
			} 
			if (data != null && column == 1) {			
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt("", row, column+1);
			} 
			else if (data != null && column == 2)  {				
				modelJV_account.setValueAt(data[0], row, column);	
				modelJV_account.setValueAt(RequestForPayment.sql_getDiv(data[0].toString()), row, column-1);
			}
			else if (data != null && column == 4)  {				
				modelJV_account.setValueAt(data[0], row, column);
				modelJV_account.setValueAt("", row, column+1);
			}
			else if (data != null && column == 5)  {				
				modelJV_account.setValueAt(data[0], row, column);		
			}
		}		

		else {}

		setTableWidth();
	}

	private static void adjustRowHeight_account(){//used
		int rw = tblJV_account.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblJV_account.setRowHeight(x, 22);			
			x++;
		}

	}

	private void AddRow(){//used

		modelJV_account.addRow(new Object[] { "", "", "", "", "", "", "", "", new BigDecimal(0.00), new BigDecimal(0.00), ""});
		totalJV(modelJV_account, modelJV_accounttotal);			
		((DefaultListModel) rowHeaderJV_account.getModel()).addElement(modelJV_account.getRowCount());
		totalJV(modelJV_account, modelJV_accounttotal);			
		adjustRowHeight_account();
	}

	private void removeRow(){//used

		int r  = tblJV_account.getSelectedRow();		

		if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblJV_account.getModel()).removeRow(r);  
			modelJV_account.addRow(new Object[] { "", "", "", "", "", "", "", "", new BigDecimal(0.00), new BigDecimal(0.00), ""});
			totalJV(modelJV_account, modelJV_accounttotal);			
			adjustRowHeight_account();
		}


	}

	public static void setTableWidth(){		
		tblJV_account.packAll();
		tblJV_account.getColumnModel().getColumn(0).setPreferredWidth(90);
		tblJV_account.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(3).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(4).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(5).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(6).setPreferredWidth(40);
		tblJV_account.getColumnModel().getColumn(8).setPreferredWidth(100);
		tblJV_account.getColumnModel().getColumn(9).setPreferredWidth(100);
	}

	public void createJV_intercomp(){

		String acct_id_comp1 = co_id;
		String acct_name_comp1 = co_id;
		
		String co_id_2 =  lookupCompany_b.getText();
		String acct_id_comp2 = "";
		String acct_name_comp2 = "";
		
		Double amount  = Double.valueOf(txtAmount.getText().trim().replace(",","")).doubleValue();
		BigDecimal amount_bd 	= new BigDecimal(amount);	
		
		if (co_id.equals("03")) { acct_id_comp1 = "03-01-10-002"; acct_name_comp1 = "Advances from Verdantpoint"; }
		else if (co_id.equals("02")) { acct_id_comp1 = "03-01-11-005"; acct_name_comp1 = "Advances from CENQ"; }	
		
		if (co_id_2.equals("03")) { acct_id_comp2 = "01-02-05-002"; acct_name_comp2 = "Advances to Verdantpoint"; }
		else if (co_id_2.equals("02")) { acct_id_comp2 = "01-02-05-004"; acct_name_comp2 = "Advances to CENQ"; }
		
		modelJV_account.setValueAt(acct_id_comp2, 0, 0); 
		modelJV_account.setValueAt(acct_name_comp2, 0, 7); 
		modelJV_account.setValueAt(amount_bd, 0, 8); 
		
		modelJV_account.setValueAt(acct_id_comp1, 1, 0); 
		modelJV_account.setValueAt(acct_name_comp1, 1, 7); 
		modelJV_account.setValueAt(amount_bd, 1, 9); 

		setTableWidth();
		totalJV(modelJV_account, modelJV_accounttotal);	
		
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlEnterAmount);
		optionPaneWindow.dispose();

	}	
	

	//save and insert	
	public void insertJV_header(String jv, pgUpdate db){//used

		Integer fiscal_yr_4digit = 0;
		String tran_id 		= "";
		String posted_by	= "";
		Date posted_date	= null;
		String doc_id		= "";
		Integer proc_id		= 0;
		Boolean is_for_rev	= false;
		Date rev_date		= null;
		String remarks		= "";
		String status_id	= "";
		String created_by	= "";
		String edited_by	= "";
		Date date_edited	= null;				

		fiscal_yr_4digit 	= (Integer) getMonthYear()[2];
		tran_id 	= lookupTranType.getText().trim();
		posted_by	= "";
		posted_date	= null;
		doc_id		= "11";
		proc_id		= 0;
		is_for_rev	= false;
		rev_date	= null;
		remarks		= txtRemarks.getText().trim().replace("'", "''");	
		status_id	= "A";
		created_by	= UserInfo.EmployeeCode;
		edited_by	= "";
		date_edited	= null;				

		String sqlDetail = 
			"INSERT into rf_jv_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +  		//2			
			"'"+jv_no+"',  \n" +  		//3
			"'"+dateFormat.format(dteTransaction.getDate())+"',  \n" +	//5 jv_date
			""+fiscal_yr_4digit+",  \n" +  	//5
			"'"+period+"',  \n" +  	//6
			"'"+tran_id+"',  \n" +  	//7
			"'"+posted_by+"',  \n" +  	//8
			""+posted_date+",  \n" +  	//9
			"'"+doc_id+"',  \n" +  		//10
			""+proc_id+",  \n" +  		//11
			""+is_for_rev+",  \n" +  	//12
			""+rev_date+",  \n" +  		//13
			"'"+remarks+"',  \n" +  	//14
			"'"+status_id+"',  \n" +  	//15
			"'"+created_by+"',  \n" +  	//16
			"'"+dateFormat.format(Calendar.getInstance().getTime())+"',  \n" +	//17 date_created			
			"'"+edited_by+"',  \n" + 	//18
			""+date_edited+"  \n" + 	//19				
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void insertJV_detail(String jv, pgUpdate db){//used

		int x  = 0;	
		int y  = 1;
		int rw = tblJV_account.getModel().getRowCount();	

		while(x < rw ) {	

			String account_id 	= modelJV_account.getValueAt(x,0).toString();
			String bal_side		= "";

			Double debit 		= Double.parseDouble(modelJV_account.getValueAt(x,8).toString());			
			Double credit 		= Double.parseDouble(modelJV_account.getValueAt(x,9).toString());			
			Double trans_amt	= debit + credit;

			if(debit>0) {bal_side="D";} else {bal_side="C";}

			String div_id		= modelJV_account.getValueAt(x,1).toString().trim();
			String dept_id		= modelJV_account.getValueAt(x,2).toString().trim();
			String sect_id		= modelJV_account.getValueAt(x,3).toString().trim();
			String project_id	= modelJV_account.getValueAt(x,4).toString().trim();
			String sub_projectid= modelJV_account.getValueAt(x,5).toString().trim();
			String ibu			= modelJV_account.getValueAt(x,6).toString().trim();
			String ref_no		= modelJV_account.getValueAt(x,10).toString().trim();

			if (trans_amt == 0.00) {}
			else
			{	
				String sqlDetail = 
					"INSERT into rf_jv_detail values (" +
					"'"+co_id+"',  \n" +  		//1 co_id
					"'"+co_id+"',  \n" +  		//2 busunit_id
					"'"+jv_no+"',  \n" +  		//3	jv_no		
					"1,  \n" +  				//4 entry_no
					""+y+",  \n" +  			//5	line_no		
					"'"+account_id+"',  \n" +  	//6 acct_id
					""+trans_amt+",  \n" +  	//7 tran_amt
					"'"+bal_side+"',  \n" +  	//8 bal_side
					"'"+ref_no+"',  \n" +  		//9 ref_no
					"'"+project_id+"',  \n" +  	//10 project_id
					"'"+sub_projectid+"',  \n" +//11 sub_projectid	
					"'"+div_id+"',  \n" +		//12 div_id
					"'"+dept_id+"',  \n" +		//13 dept_id		
					"'"+sect_id+"',  \n" +		//14 sect_id		
					"null,  \n" +  				//15 inter_co_id
					"'"+ibu+"',  \n" +			//16 inter_busunit_id
					"null,  \n" +  				//17 old_acct_id
					"null,  \n" +  				//18 entity_id
					"null,  \n" +  				//19 pbl_id
					"null,  \n" +  				//20 seq_no				
					"'A', " +					//21 status_id
					"'"+UserInfo.EmployeeCode+"',  \n" +  	//22 created_by
					"'"+dateFormat.format(Calendar.getInstance().getTime())+"',  \n" +	//23 date_created			
					"'',  \n" + 				//24 edited_by
					"null  \n" + 				//25 date_edited			
					")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);					
				y++;
			}	

			x++;
		}
	}
	
	public void insertDB(pgUpdate db){//used

		String sqlDetail = 
			"INSERT into rf_debit_credit_memo values (" +
			"'"+co_id+"',  \n" +  					//1 co_id
			"'"+sql_getNextDB_no()+"',  \n" +  		//2	db_no
			"'"+dateFormat.format(dteTransaction.getDate())+"',  \n" +	//3 tran_date
			"'"+lookupTranType.getText().trim()+"',  \n" +  			//4 tran_id
			"'"+jv_no+"',  \n" +  		//5 jv_no	
			"'A',  \n" +  				//6 status_id
			"'"+txtRemarks.getText().trim().replace("'", "''")+"',  \n" +  		//7 remarks		
			"'"+UserInfo.EmployeeCode+"',  \n" +  								//8 tagged_by
			"'"+dateFormat.format(Calendar.getInstance().getTime())+"'  \n" +	//9 tagged_date						
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	

	//others
	private static Object [] getMonthYear() {//used		

		DateFormat df 	 = new SimpleDateFormat("yyyy-dd-MM");					
		String month  	 = df.format(dteTransaction.getDate());								
		String month_sub = month.substring(8);		

		String month_word [] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"  };

		DateFormat df2   = new SimpleDateFormat("MM-dd-yyyy");	
		String year 	 = df2.format(dteTransaction.getDate());								
		Integer year_sub = Integer.parseInt(year.substring(6))-2000;
		Integer year_full = Integer.parseInt(year.substring(6));
		
		System.out.printf("month_sub: " + month_sub + "\n");
		System.out.printf("year: " + year + "\n");
		System.out.printf("year_sub: " + year_sub + "\n");
		System.out.printf("year_full: " + year_full + "\n");

		Object x [] 	 = {month_sub, year_sub, year_full, month_word[Integer.parseInt(month_sub)-1], year.substring(6)};

		return x;

	}

	public void setJV_no(){//used

		Integer fiscal_yr 		= 0;
		Integer fiscal_yr_full 	= 0;

		fiscal_yr 		= (Integer) getMonthYear()[1];
		fiscal_yr_full 	= (Integer) getMonthYear()[2];
		
		System.out.printf("fiscal_yr: " + fiscal_yr + "\n");
		System.out.printf("fiscal_yr_full: " + fiscal_yr_full + "\n");
		
		Integer next_srlno	= sql_getNextJV_srlno(fiscal_yr_full, period );  

		jv_no = getJVno(fiscal_yr, period, next_srlno);

	}	

	public Integer sql_getNextJV_srlno(Integer jv_fiscal_yr, String period_id ) {//used

		Integer next_no = 0;
		String SQL = 
			"select coalesce(max(jv_no),0) + 1  from \r\n" + 
			"(" +
			"select substring(jv_no, 5)::int as jv_no \r\n" + 
			"  from rf_jv_header \r\n" + 
			"  where fiscal_yr = "+jv_fiscal_yr+" \r\n" + 
			"  and trim(period_id) = '"+period_id+"' \r\n" + 
			"  order by substring(jv_no, 5)::int desc  ) a";			

		System.out.printf("SQL sql_getNextJV_srlno : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			next_no = (Integer) Integer.parseInt(db.getResult()[0][0].toString());
		}else{
			next_no = 1;
		}

		return next_no;
	}

	public String getJVno(Integer fiscal_yr, String period_id, Integer next_srlno){//used
		String SQL = 
			"select ('"+fiscal_yr+"'||'"+period_id+"'||trim(to_char("+next_srlno+",'0000'))) as next_jvno " ;

		System.out.printf("SQL getJVno: " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			jv_no = (String) db.getResult()[0][0];
		}else{
			jv_no = null;
		}

		return jv_no;
	}

	public Integer sql_getNextDB_no () {//used

		db_no = 0;
		
		String SQL = 
			"select coalesce(max(db_no),0) + 1  from rf_debit_credit_memo where co_id = '"+co_id+"'  \r\n" ;

		System.out.printf("SQL sql_getNextDB_no : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			db_no = (Integer) Integer.parseInt(db.getResult()[0][0].toString());
		}else{
			db_no = 1;
		}

		return db_no;
	}
	
	
	//righ-click
	public static void copy(){

		int column 	= tblJV_account.getSelectedColumn();
		int row 	= tblJV_account.getSelectedRow();	

		item = modelJV_account.getValueAt(row,column).toString().trim();
		mnipaste.setEnabled(true);
	}

	public void paste(){

		int column 	= tblJV_account.getSelectedColumn();
		int row 	= tblJV_account.getSelectedRow();			

		if(column==0) {
			if(RequestForPayment.isItemAnAcct(item)==true) {modelJV_account.setValueAt(item, row, column); 
			setTableWidth();}		
			else {JOptionPane.showMessageDialog(getContentPane(),"Pasted item is not a valid Account ID.","Information",JOptionPane.ERROR_MESSAGE);}
		}

		else if(column==1) {
			if(RequestForPayment.isItemDiv(item)==true) {modelJV_account.setValueAt(item, row, column); 
			setTableWidth();}		
			else {JOptionPane.showMessageDialog(getContentPane(),"Pasted item is not a valid Division ID.","Information",JOptionPane.ERROR_MESSAGE);}
		}

		else if(column==2) {
			if(RequestForPayment.isItemDept(item)==true) {modelJV_account.setValueAt(item, row, column); 
			setTableWidth();}		
			else {JOptionPane.showMessageDialog(getContentPane(),"Pasted item is not a valid Department ID.","Information",JOptionPane.ERROR_MESSAGE);}
		}

		else if(column==4) {
			if(RequestForPayment.isItemProject(item)==true) {modelJV_account.setValueAt(item, row, column); 
			setTableWidth();}		
			else {JOptionPane.showMessageDialog(getContentPane(),"Pasted item is not a valid Project ID.","Information",JOptionPane.ERROR_MESSAGE);}
		}

		else if(column==5) {
			if(RequestForPayment.isItemSubProject(item)==true) {modelJV_account.setValueAt(item, row, column); 
			setTableWidth();}		
			else {JOptionPane.showMessageDialog(getContentPane(),"Pasted item is not a valid Sub-project ID.","Information",JOptionPane.ERROR_MESSAGE);}
		}
	}

	public void copyRow(){

		int row 	= tblJV_account.getSelectedRow();			

		copy_acct_id 	= modelJV_account.getValueAt(row,0).toString().trim();
		copy_div 		= modelJV_account.getValueAt(row,1).toString().trim();
		copy_dept 		= modelJV_account.getValueAt(row,2).toString().trim();
		copy_proj 		= modelJV_account.getValueAt(row,4).toString().trim();
		copy_subproj	= modelJV_account.getValueAt(row,5).toString().trim();
		copy_ibu 		= modelJV_account.getValueAt(row,6).toString().trim();
		copy_acct_desc 	= modelJV_account.getValueAt(row,7).toString().trim();
		mnipasteRow.setEnabled(true);
	}

	public void pasteRow(){

		int row 	= tblJV_account.getSelectedRow();		

		modelJV_account.setValueAt(copy_acct_id, row, 0); 
		modelJV_account.setValueAt(copy_div, row, 1); 
		modelJV_account.setValueAt(copy_dept, row, 2); 
		modelJV_account.setValueAt(copy_proj, row, 4); 
		modelJV_account.setValueAt(copy_subproj, row, 5); 
		modelJV_account.setValueAt(copy_ibu, row, 6); 
		modelJV_account.setValueAt(copy_acct_desc, row, 7); 

		setTableWidth();		

	}	

	public static void openPV(){

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
		}		
	}

	public static void openCV(){

		CheckVoucher chk_vchr = new CheckVoucher();
		Home_JSystem.addWindow(chk_vchr);

		if(co_id.equals("")) {}
		else 
		{
			CheckVoucher.co_id 		= co_id;
			CheckVoucher.tagCompany.setTag(company);
			CheckVoucher.company	= company;
			CheckVoucher.company_logo = company_logo;
			CheckVoucher.lookupCompany.setValue(co_id);

			CheckVoucher.lblDV_no.setEnabled(true);	
			CheckVoucher.lookupDV_no.setEnabled(true);	
			CheckVoucher.lookupDV_no.setLookupSQL(CheckVoucher.getDV_no(co_id));
			CheckVoucher.enableButtons(true, false, false, false, false, false, false, false, false, false );
		}

	}

	public void openDP(){

		DocsProcessing doc_proc = new DocsProcessing();
		Home_JSystem.addWindow(doc_proc);

		DocsProcessing.co_id 		= co_id;	
		DocsProcessing.company		= company;	
		DocsProcessing.tagCompany.setTag(company);
		DocsProcessing.company_logo = company_logo;

		DocsProcessing.lblDoc_type.setEnabled(true);	
		DocsProcessing.lookupDocType.setEnabled(true);	
		//DocsProcessing.lookupDocType.setEditable(true);	
		DocsProcessing.tagDocType.setEnabled(true);	

		DocsProcessing.lookupDocType.setLookupSQL(DocsProcessing.getDocType());		
		DocsProcessing.enableButtons(false, false, false, true);

		DocsProcessing.lookupCompany.setValue(co_id);
		DocsProcessing.lookupDocType.setValue("11");
		DocsProcessing.tagDocType.setTag("GENERAL JOURNAL VOUCHER");

		DocsProcessing.lblProcess.setEnabled(true);	
		DocsProcessing.lookupProcess.setEnabled(true);	
		//DocsProcessing.lookupProcess.setEditable(true);	
		DocsProcessing.tagProcess.setEnabled(true);
		DocsProcessing.lookupProcess.setValue("");
		DocsProcessing.tagProcess.setText("[ ]");

		if (DocsProcessing.lookupDocType.getText().trim().equals("11")) {DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getJV_processes()); DocsProcessing.doc_no = "11";}
		if (DocsProcessing.lookupDocType.getText().trim().equals("12")) {DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getPV_processes()); DocsProcessing.doc_no = "12";}
		if (DocsProcessing.lookupDocType.getText().trim().equals("13")) {DocsProcessing.lookupProcess.setLookupSQL(DocsProcessing.getDV_processes()); DocsProcessing.doc_no = "13";}	

	}

}
