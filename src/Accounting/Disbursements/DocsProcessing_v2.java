package Accounting.Disbursements;

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;
import interfaces._GUI;
import tablemodel.modelDocsProcessing;
import tablemodel.modelDocsProcessing_total;

public class DocsProcessing_v2 extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Documents Processing (Reform - Prototype)";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlTable;
	private JPanel pnlDProc;
	private JPanel pnlDProc_a1;
	private JPanel pnlDProc_a2;
	private JPanel pnlDProc_a2_1;
	private JPanel pnlDProc_a2_2;
	private JPanel pnlReq_Main;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;

	private JLabel lblCompany;
	private JLabel lblDateEdited;
	private JLabel lblJV_no;
	private JLabel lblStatus;
	private JLabel lblFiscalyear;
	private JLabel lblPeriod;
	private JLabel lblDateTrans;
	private JLabel lblTransType;
	private JLabel lblDatePosted;
	public static JLabel lblDoc_type;
	public static JLabel lblProcess;

	public static _JLookup lookupCompany;
	public static _JLookup lookupDocType;	
	private _JLookup lookupFiscalYr;
	private _JLookup lookupPeriod;
	private _JLookup lookupTranType;
	public static  _JLookup lookupProcess;

	private modelDocsProcessing modelDProc_account;
	private modelDocsProcessing_total modelDProc_accounttotal;

	public static _JTagLabel tagCompany;
	public static _JTagLabel tagDocType;
	private _JTagLabel tagPeriod;
	public static  _JTagLabel tagProcess;	

	private static JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnRefresh;	
	private static JButton btnPreview;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dteTransaction;
	private _JDateChooser dteEdited;
	private _JDateChooser dtePosted;

	private JPopupMenu menu;	;
	private _JScrollPaneMain scrollDProc_account;
	private _JScrollPaneTotal scrollDProc_accounttotal;	

	private JTextArea txtJV_Remark;
	private JXTextField txtStatus;

	private JMenuItem mnidelete;
	private JMenuItem mniaddrow;		

	private _JTableMain tblDoc_proc;
	private JList rowHeaderDProc;
	private _JTableTotal tblDoc_proctotal;	

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	

	public static String co_id = "02";		
	public static String company = "";
	public static String company_logo;		
	public static String doc_no = "";
	String proc_no = "";	
	Integer return_row = 0;	
	String display_sql = "";
	private JPanel pnlDate;
	private JLabel lblDateFr;
	private _JDateChooser dteRefDateFr;
	private JLabel lblDateTo;
	private _JDateChooser dteRefDateTo;
	private JButton btnOK;	
	public String date_from = "01-01-2016";
	private String pay_type = "";
	
	DecimalFormat df = new DecimalFormat("#,###,##0.00");
	private JCheckBox chkIncludeCash;
	
	Boolean inc_cash = true;
	private JCheckBox chkEnableDateFrom;
	private JLabel lblRemarks;
	private JTextField txtRemarks;

	public DocsProcessing_v2() {
		super(title, true, true, true, true);
		initGUI();
	}

	public DocsProcessing_v2(String title) {
		super(title);
		
	}

	public DocsProcessing_v2(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(833, 545));
		this.setBounds(0, 0, 833, 545);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		pnlReq_Main = new JPanel();		
		menu = new JPopupMenu("Popup");
		mnidelete = new JMenuItem("Remove Row      ");
		mniaddrow = new JMenuItem("Add Row");
		pnlReq_Main.add(menu);
		menu.add(mnidelete);
		menu.add(mniaddrow);
		mnidelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){

			}
		});
		mniaddrow.addActionListener(new ActionListener(){
			public void	actionPerformed(ActionEvent evt){

			}
		});	

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(789, 150));		

			pnlDProc = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlDProc, BorderLayout.CENTER);				
			pnlDProc.setPreferredSize(new java.awt.Dimension(921, 85));			
			pnlDProc.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));			

			pnlDProc_a1 = new JPanel(new GridLayout(4, 1, 5, 10));
			pnlDProc.add(pnlDProc_a1, BorderLayout.WEST);	
			pnlDProc_a1.setPreferredSize(new java.awt.Dimension(120, 112));	
			pnlDProc_a1.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			{
				lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
				pnlDProc_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(105, 25));
				lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				lblDoc_type = new JLabel("Document Type", JLabel.TRAILING);
				pnlDProc_a1.add(lblDoc_type);
				lblDoc_type.setEnabled(false);	
				lblDoc_type.setPreferredSize(new java.awt.Dimension(106, 40));
				lblDoc_type.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				lblDoc_type.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}	
			{
				lblProcess = new JLabel("Process", JLabel.TRAILING);
				pnlDProc_a1.add(lblProcess);
				lblProcess.setEnabled(false);	
				lblProcess.setPreferredSize(new java.awt.Dimension(106, 40));
				lblProcess.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				lblProcess.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				lblRemarks = new JLabel("Remarks", JLabel.TRAILING);
				pnlDProc_a1.add(lblRemarks);
				lblRemarks.setEnabled(false);
				lblRemarks.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
				lblRemarks.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}

			pnlDProc_a2 = new JPanel(new BorderLayout(5, 5));
			pnlDProc.add(pnlDProc_a2, BorderLayout.CENTER);	
			pnlDProc_a2.setPreferredSize(new java.awt.Dimension(814, 40));	
			pnlDProc_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			pnlDProc_a2_1 = new JPanel(new GridLayout(4, 1, 5, 10));
			pnlDProc_a2.add(pnlDProc_a2_1, BorderLayout.WEST);	
			pnlDProc_a2_1.setPreferredSize(new java.awt.Dimension(101, 24));	

			{
				lookupCompany = new _JLookup(null, "Company",0,2);
				pnlDProc_a2_1.add(lookupCompany);
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

							lblDoc_type.setEnabled(true);	
							lookupDocType.setEnabled(true);	
							lookupDocType.setEditable(true);	
							tagDocType.setEnabled(true);	

							lookupDocType.setLookupSQL(getDocType());							

							enableButtons(false, false, false, true);
						}
					}
				});
			}		

			{
				lookupDocType = new _JLookup(null, "Document Type", 2, 2);
				pnlDProc_a2_1.add(lookupDocType);
				lookupDocType.setBounds(20, 27, 20, 25);
				lookupDocType.setReturnColumn(0);
				lookupDocType.setEnabled(false);	
				//lookupDocType.setEditable(false);	
				lookupDocType.setPreferredSize(new java.awt.Dimension(100, 24));
				lookupDocType.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){	

							String doc_name = (String) data[1];	
							tagDocType.setTag(doc_name);

							lblProcess.setEnabled(true);	
							lookupProcess.setEnabled(true);	
							lookupProcess.setEditable(true);	
							tagProcess.setEnabled(true);
							lookupProcess.setValue("");
							tagProcess.setText("[ ]");
							refresh_tablesMain();
							btnPreview.setText("Preview");
							btnPreview.setActionCommand("Preview");
							txtRemarks.setText("");

							if (lookupDocType.getText().trim().equals("11")) {lookupProcess.setLookupSQL(getJV_processes()); doc_no = "11";}
							if (lookupDocType.getText().trim().equals("12")) {lookupProcess.setLookupSQL(getPV_processes()); doc_no = "12";}
							if (lookupDocType.getText().trim().equals("13 - DV")) {lookupProcess.setLookupSQL(getDV_processes()); doc_no = "13 - DV";}
							if (lookupDocType.getText().trim().equals("13 - Com")) {lookupProcess.setLookupSQL(getCom_processes()); doc_no = "13 - Com";}

						}
					}
				});
			}

			{
				lookupProcess = new _JLookup(null, "Process", 2, 2);
				pnlDProc_a2_1.add(lookupProcess);
				lookupProcess.setBounds(20, 27, 20, 25);
				lookupProcess.setReturnColumn(0);
				lookupProcess.setEnabled(false);	
				//lookupProcess.setEditable(false);	
				lookupProcess.setPreferredSize(new java.awt.Dimension(100, 24));
				lookupProcess.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						
						if(data != null){
							
							tblDoc_proc.showColumns("For Process","For Return");
							tblDoc_proctotal.showColumns("For Process","For Return");
							
							tblDoc_proc.showColumns("Check Date", "Check No.");
							tblDoc_proctotal.showColumns("Check Date", "Check No.");
							
							tblDoc_proc.showColumns("Date Posted", "Payee");
							tblDoc_proctotal.showColumns("Date Posted", "Payee");

							String process_no = (String) data[1];	
							tagProcess.setTag(process_no);
							refresh_tablesMain();
							
							disbursementVoucherProcess(); 
							
						}
					}
				});
			}
			{
				txtRemarks = new JTextField();
				pnlDProc_a2_1.add(txtRemarks);
				txtRemarks.setEnabled(false);
			}


			pnlDProc_a2_2 = new JPanel(new GridLayout(4, 2, 5, 5));
			pnlDProc_a2.add(pnlDProc_a2_2, BorderLayout.CENTER);	
			pnlDProc_a2_2.setPreferredSize(new java.awt.Dimension(357, 25));	

			{
				tagCompany = new _JTagLabel("[ ]");
				pnlDProc_a2_2.add(tagCompany);
				tagCompany.setBounds(209, 27, 700, 22);
				tagCompany.setEnabled(true);	
				tagCompany.setPreferredSize(new java.awt.Dimension(380, 24));
			}	
			{
				tagDocType = new _JTagLabel("[ ]");
				pnlDProc_a2_2.add(tagDocType);
				tagDocType.setBounds(209, 27, 700, 22);
				tagDocType.setEnabled(false);	
				tagDocType.setPreferredSize(new java.awt.Dimension(380, 24));
			}	
			{
				tagProcess = new _JTagLabel("[ ]");
				pnlDProc_a2_2.add(tagProcess);
				tagProcess.setBounds(209, 27, 700, 22);
				tagProcess.setEnabled(false);	
				tagProcess.setPreferredSize(new java.awt.Dimension(380, 24));
			}	
		}	
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlDProc = new JPanel();
			pnlTable.add(pnlDProc, BorderLayout.CENTER);
			pnlDProc.setLayout(new BorderLayout(5, 5));
			pnlDProc.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlDProc.setBorder(lineBorder);		
			pnlDProc.addMouseListener(new PopupTriggerListener_panel());			

			{	
				{
					scrollDProc_account = new _JScrollPaneMain();
					pnlDProc.add(scrollDProc_account, BorderLayout.CENTER);
					{
						modelDProc_account = new modelDocsProcessing();

						tblDoc_proc = new _JTableMain(modelDProc_account);
						scrollDProc_account.setViewportView(tblDoc_proc);
						tblDoc_proc.getColumnModel().getColumn(3).setPreferredWidth(40);
						tblDoc_proc.addMouseListener(this);	
						tblDoc_proc.setSortable(false);
						tblDoc_proc.addMouseListener(new PopupTriggerListener_panel());
						tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
						tblDoc_proc.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
														
							}							
							public void keyPressed(KeyEvent e) {
														
							}

						}); 
						tblDoc_proc.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblDoc_proc.rowAtPoint(e.getPoint()) == -1){
									tblDoc_proctotal.clearSelection();

								}else{
									tblDoc_proc.setCellSelectionEnabled(true);

								}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblDoc_proc.rowAtPoint(e.getPoint()) == -1){
									tblDoc_proctotal.clearSelection();

								}else{
									tblDoc_proc.setCellSelectionEnabled(true);

								}
							}
						});						
					}
					{
						rowHeaderDProc = tblDoc_proc.getRowHeader22();
						scrollDProc_account.setRowHeaderView(rowHeaderDProc);
						scrollDProc_account.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollDProc_accounttotal = new _JScrollPaneTotal(scrollDProc_account);
					pnlDProc.add(scrollDProc_accounttotal, BorderLayout.SOUTH);
					{
						modelDProc_accounttotal = new modelDocsProcessing_total();
						modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

						tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
						tblDoc_proctotal.setFont(dialog11Bold);
						scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
						((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);
					}
				}
			}			
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(789, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));

			{
				btnSave = new JButton("Save");
				pnlSouthCenterb.add(btnSave);
				btnSave.setActionCommand("Save");	
				btnSave.setEnabled(false);
				btnSave.addActionListener(this);
			}
			{
				btnPreview = new JButton("Preview");
				pnlSouthCenterb.add(btnPreview);
				btnPreview.setActionCommand("Preview");	
				btnPreview.setEnabled(false);
				btnPreview.addActionListener(this);
				btnPreview.addActionListener(new ActionListener() {
					//**EDITED BY JED 2019-03-18 DCRF NO. 967 : SEPARATE METHOD**//
					public void actionPerformed(ActionEvent e) {/*

						if(doc_no.equals("13 - DV")&&proc_no.equals("5")){

						}else{

							int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Report Period",
									JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

							if ( scanOption == JOptionPane.CLOSED_OPTION ) {
								try {	

								} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {

								}				
							} // CLOSED_OPTION
						}
					*/}
				});
			}
			{
				btnRefresh = new JButton("Refresh");
				pnlSouthCenterb.add(btnRefresh);
				btnRefresh.setActionCommand("Refresh");	
				btnRefresh.setEnabled(false);
				btnRefresh.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlSouthCenterb.add(btnCancel);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(this);
				btnCancel.setEnabled(false);
			}
		}
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();

	}
	{
		pnlDate= new JPanel();
		pnlDate.setLayout(null);
		pnlDate.setPreferredSize(new java.awt.Dimension(320, 140));
		{
			lblDateFr = new JLabel();
			pnlDate.add(lblDateFr);
			lblDateFr.setBounds(10, 5, 160, 20);
			lblDateFr.setText("Date from :");
		}
		{
			dteRefDateFr = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
			pnlDate.add(dteRefDateFr);
			dteRefDateFr.setBounds(80, 5, 125, 21);
			dteRefDateFr.setDate(null);
			dteRefDateFr.setEnabled(false);
			dteRefDateFr.setDateFormatString("yyyy-MM-dd");
			((JTextFieldDateEditor)dteRefDateFr.getDateEditor()).setEditable(false);
			dteRefDateFr.setDate(FncGlobal.dateFormat("2016-01-01"));
			dteRefDateFr.addPropertyChangeListener( new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
				}
			});	
		}
		{							
			chkEnableDateFrom = new JCheckBox("set Date From");
			pnlDate.add(chkEnableDateFrom);
			chkEnableDateFrom.setSelected(false);
			chkEnableDateFrom.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
			chkEnableDateFrom.setPreferredSize(new java.awt.Dimension(131, 26));			
			chkEnableDateFrom.setBounds(210, 5, 125, 21);
			chkEnableDateFrom.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;
					if (selected) {
						date_from = dateFormat.format(dteRefDateFr.getDate());
						dteRefDateFr.setEnabled(true);
						} 
					else{date_from="01-01-2016";
						dteRefDateFr.setDate(FncGlobal.dateFormat("2016-01-01"));
						dteRefDateFr.setEnabled(false);}
				}
			});							
		}
		{
			lblDateTo = new JLabel();
			pnlDate.add(lblDateTo);
			lblDateTo.setBounds(10, 38, 160, 20);
			lblDateTo.setText("Date to :");
		}
		{
			dteRefDateTo = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
			pnlDate.add(dteRefDateTo);
			dteRefDateTo.setBounds(80, 38, 125, 21);
			dteRefDateTo.setDate(null);
			dteRefDateTo.setEnabled(true);
			dteRefDateTo.setDateFormatString("yyyy-MM-dd");
			((JTextFieldDateEditor)dteRefDateTo.getDateEditor()).setEditable(false);
			dteRefDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dteRefDateTo.addPropertyChangeListener( new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
				}
			});	
		}
		{							
			chkIncludeCash = new JCheckBox("Include Cash DV");
			pnlDate.add(chkIncludeCash);
			chkIncludeCash.setEnabled(true);	
			chkIncludeCash.setSelected(true);
			chkIncludeCash.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
			chkIncludeCash.setPreferredSize(new java.awt.Dimension(131, 26));
			chkIncludeCash.setBounds(80, 75, 125, 21);
			chkIncludeCash.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;
					if (selected) {inc_cash = true;} 
					else{inc_cash = false;}
				}
			});							
		}
		{
			btnOK = new JButton();
			pnlDate.add(btnOK);
			btnOK.setBounds(95, 110, 69, 22);
			btnOK.setText("OK");
			btnOK.setFocusable(false);
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
					optionPaneWindow.dispose();				
					
					if (doc_no.equals("13 - DV")) {						
						if (proc_no.equals("13") && inc_cash==true) {
							preview_CV(); 
							preview_CV_cash();
						} else {
							preview_CV();
						}						
					} 
				}
			});
		}
	}


	//display tables
	public void displayJV_forChecking(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"select \r\n" + 
			"\r\n" + 
			"a.jv_no,\r\n" +
			"null,\r\n" + 
			"null,\r\n" + 
			"b.amount,\r\n" + 
			"false,\r\n" + 
			"false,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"upper(trim(g.entity_name)),\r\n" + 
			"a.jv_date,  \r\n" + 
			"trim(e.return_message),  " +
			"a.date_posted, (select get_client_name(entity_id) from rf_jv_detail where jv_no = a.jv_no LIMIT 1)" +
			"\r\n" + 
			"\r\n" + 
			"from rf_jv_header a\r\n" + 
			"left join ( \r\n" + 
			"	select distinct on (jv_no, co_id) a.jv_no, a.co_id, sum(tran_amt) as amount from (\r\n" + 
			"	select jv_no, co_id, tran_amt  from rf_jv_detail where bal_side = 'D' and status_id = 'A' and co_id = '"+co_id+"') a\r\n" + 
			"	group by jv_no, co_id  ) b on trim(a.jv_no) = trim(b.jv_no) and a.co_id = b.co_id\r\n" + 
			"left join em_employee c on a.created_by=c.emp_code\r\n" + 
			"left join em_employee d on a.edited_by=d.emp_code\r\n" + 
			"left join rf_entity f on c.entity_id = f.entity_id   \n" +
			"left join rf_entity g on d.entity_id = g.entity_id   \n" +
			"left join (select * from rf_doc_return_message where status_id = 'A' and doc_id = '11' ) e on a.jv_no = e.doc_no and trim(a.co_id)=trim(e.co_id)    "  +			
			"\r\n" + 
			"where a.status_id = 'A'\r\n" + 
			"and a.proc_id = 0 \n" +
			"and a.co_id = '"+co_id+"'  " +
			"order by a.jv_no  desc" ;

		System.out.printf("JV For Checking :" + display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Date Posted", "Payee");
		tblDoc_proctotal.showColumns("Date Posted", "Payee");
		
		tblDoc_proc.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		computeTotal();

	}	

	public void displayJV_forApproval(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"select \r\n" + 
			"\r\n" + 
			"a.jv_no,\r\n" +
			"null,\r\n" + 
			"null,\r\n" + 
			"b.amount,\r\n" + 
			"false,\r\n" + 
			"false,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"upper(trim(g.entity_name)),\r\n" + 
			"a.jv_date,  \r\n" + 
			"trim(e.return_message),  " +
			"a.date_posted, (select get_client_name(entity_id) from rf_jv_detail where jv_no = a.jv_no LIMIT 1)" +
			"\r\n" + 
			"from rf_jv_header a\r\n" + 
			"left join ( \r\n" + 
			"	select distinct on (jv_no, co_id) a.jv_no, a.co_id, sum(tran_amt) as amount from (\r\n" + 
			"	select jv_no, co_id, tran_amt  from rf_jv_detail where bal_side = 'D' and status_id = 'A' and co_id = '"+co_id+"') a\r\n" + 
			"	group by jv_no, co_id  ) b on trim(a.jv_no) = trim(b.jv_no) and a.co_id = b.co_id\r\n" + 
			"left join em_employee c on a.created_by=c.emp_code\r\n" + 
			"left join em_employee d on a.edited_by=d.emp_code\r\n" + 
			"left join rf_entity f on c.entity_id = f.entity_id   \n" +
			"left join rf_entity g on d.entity_id = g.entity_id   \n" +
			"left join (select * from rf_doc_return_message where status_id = 'A' and doc_id = '11' ) e on a.jv_no = e.doc_no and trim(a.co_id)=trim(e.co_id)    "  +
			"\r\n" + 
			"\r\n" + 
			"where a.status_id = 'F'\r\n" + 
			"and a.proc_id = 1 \n" +
			"and a.co_id = '"+co_id+"'  " +
			"order by a.jv_no  desc" ;

		System.out.printf("JV For Approval :" + display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Date Posted", "Payee");
		tblDoc_proctotal.showColumns("Date Posted", "Payee");
		
		tblDoc_proc.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		computeTotal();

	}	
	
	public void displayJV_Approved(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"select \r\n" + 
			"\r\n" + 
			"a.jv_no,\r\n" + 
			"null,\r\n" + 
			"null,\r\n" + 
			"b.amount,\r\n" + 
			"false,\r\n" + 
			"false,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"upper(trim(g.entity_name)),\r\n" + 
			"a.jv_date,  \r\n" + 
			"trim(e.return_message),  \r\n" +
			"a.date_posted, (select get_client_name(entity_id) from rf_jv_detail where jv_no = a.jv_no LIMIT 1)" +
			"\r\n" + 
			"from rf_jv_header a\r\n" + 
			"left join ( \r\n" + 
			"	select distinct on (jv_no, co_id) a.jv_no, a.co_id, sum(tran_amt) as amount from (\r\n" + 
			"	select jv_no, co_id, tran_amt  from rf_jv_detail where bal_side = 'D' and status_id = 'A' and co_id = '"+co_id+"') a\r\n" + 
			"	group by jv_no, co_id  ) b on trim(a.jv_no) = trim(b.jv_no) and a.co_id = b.co_id\r\n" + 
			"left join em_employee c on a.created_by=c.emp_code\r\n" + 
			"left join em_employee d on a.edited_by=d.emp_code\r\n" + 
			"left join rf_entity f on c.entity_id = f.entity_id   \n" +
			"left join rf_entity g on d.entity_id = g.entity_id   \n" +
			"left join (select * from rf_doc_return_message where status_id = 'A' and doc_id = '11' ) e on a.jv_no = e.doc_no and trim(a.co_id)=trim(e.co_id)    "  +
			"\r\n" + 
			"\r\n" + 
			"where a.status_id = 'P'\r\n" + 
			"and a.co_id = '"+co_id+"'  " +
			"order by a.jv_no  desc" ;

		System.out.printf("JV For Approval :" + display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.hideColumns("For Process","For Return");
		tblDoc_proctotal.hideColumns("For Process","For Return");
		tblDoc_proc.showColumns("Date Posted", "Payee");
		tblDoc_proctotal.showColumns("Date Posted", "Payee");
		
		tblDoc_proc.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		computeTotal();

	}

	public void displayPV_forChecking(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"select \r\n" + 
			"\r\n" + 
			"a.pv_no,\r\n" + 
			"null,\r\n" + 
			"null,\r\n" + 
			"b.amount,\r\n" + 
			"false,\r\n" + 
			"false,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"upper(trim(g.entity_name)),\r\n" + 
			"a.pv_date,  \r\n" + 
			"trim(e.return_message), \r\n " +
			"a.date_posted,  get_client_name(a.entity_id1)" +
			"\r\n" + 
			"from rf_pv_header a\r\n" + 
			"left join ( \r\n" + 
			"	select distinct on (pv_no, co_id) a.pv_no, a.co_id, sum(tran_amt) as amount from (\r\n" + 
			"	select pv_no, co_id, tran_amt  from rf_pv_detail where bal_side = 'D' and status_id = 'A' and co_id = '"+co_id+"') a\r\n" + 
			"	group by pv_no, co_id  ) b on trim(a.pv_no) = trim(b.pv_no) and a.co_id = b.co_id\r\n" + 
			"left join em_employee c on a.created_by=c.emp_code\r\n" + 
			"left join em_employee d on a.edited_by=d.emp_code\r\n" + 
			"left join rf_entity f on c.entity_id = f.entity_id   \n" +
			"left join rf_entity g on d.entity_id = g.entity_id   \n" +
			"left join (select * from rf_doc_return_message where status_id = 'A' and doc_id = '12' ) e on a.pv_no = e.doc_no and trim(a.co_id)=trim(e.co_id)  "  +
			"\r\n" + 
			"\r\n" + 
			"where a.status_id = 'A'\r\n" + 
			"and a.co_id = '"+co_id+"' " +
			"order by a.pv_no desc " ;

		System.out.printf("PV For Checking :" + display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Date Posted", "Payee");
		tblDoc_proctotal.showColumns("Date Posted", "Payee");
		
		tblDoc_proc.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		computeTotal();

	}	

	public void displayPV_forApproval(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"select \r\n" + 
			"\r\n" + 
			"a.pv_no,\r\n" + 
			"null,\r\n" + 
			"null,\r\n" + 
			"b.amount,\r\n" + 
			"false,\r\n" + 
			"false,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"upper(trim(g.entity_name)),\r\n" + 
			"a.pv_date,  \r\n" + 
			"trim(e.return_message), \r\n " +
			"a.date_posted,  get_client_name(a.entity_id1)" +
			"\r\n" + 
			"from rf_pv_header a\r\n" + 
			"left join ( \r\n" + 
			"	select distinct on (pv_no, co_id) a.pv_no, a.co_id, sum(tran_amt) as amount from (\r\n" + 
			"	select pv_no, co_id, tran_amt  from rf_pv_detail where bal_side = 'D' and status_id = 'A' and co_id = '"+co_id+"') a\r\n" + 
			"	group by pv_no, co_id  ) b on trim(a.pv_no) = trim(b.pv_no) and a.co_id = b.co_id\r\n" + 
			"left join em_employee c on a.created_by=c.emp_code\r\n" + 
			"left join em_employee d on a.edited_by=d.emp_code\r\n" + 
			"left join rf_entity f on c.entity_id = f.entity_id   \n" +
			"left join rf_entity g on d.entity_id = g.entity_id   \n" +
			"left join (select * from rf_doc_return_message where status_id = 'A' and doc_id = '12' ) e on a.pv_no = e.doc_no and trim(a.co_id)=trim(e.co_id)    "  +
			"\r\n" + 
			"\r\n" + 
			"where a.status_id = 'F'\r\n" + 
			"and a.proc_id = 1 \n" +
			"and a.co_id = '"+co_id+"'  " +
			"order by a.pv_no  desc" ;

		System.out.printf("PV For Approval :" + display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Date Posted", "Payee");
		tblDoc_proctotal.showColumns("Date Posted", "Payee");
		
		tblDoc_proc.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		computeTotal();

	}	

	public void displayPV_forSendingtoTreasury(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"select \r\n" + 
			"\r\n" + 
			"a.pv_no,\r\n" + 
			"null,\r\n" + 
			"null,\r\n" + 
			"b.amount,\r\n" + 
			"false,\r\n" + 
			"false,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"upper(trim(g.entity_name)),\r\n" + 
			"a.pv_date,  \r\n" + 
			"trim(e.return_message), \r\n " +
			"a.date_posted,  get_client_name(a.entity_id1)" +
			"\r\n" + 
			"from rf_pv_header a\r\n" + 
			"left join ( \r\n" + 
			"	select distinct on (pv_no, co_id) a.pv_no, a.co_id, sum(tran_amt) as amount from (\r\n" + 
			"	select pv_no, co_id, tran_amt  from rf_pv_detail where bal_side = 'D' and status_id = 'A' and co_id = '"+co_id+"') a\r\n" + 
			"	group by pv_no, co_id  ) b on trim(a.pv_no) = trim(b.pv_no) and a.co_id = b.co_id\r\n" + 
			"left join em_employee c on a.created_by=c.emp_code\r\n" + 
			"left join em_employee d on a.edited_by=d.emp_code\r\n" + 
			"left join rf_entity f on c.entity_id = f.entity_id   \n" +
			"left join rf_entity g on d.entity_id = g.entity_id   \n" +
			"left join (select * from rf_doc_return_message where status_id = 'A' and doc_id = '12' ) e on a.pv_no = e.doc_no and trim(a.co_id)=trim(e.co_id)    "  +
			"\r\n" + 
			"\r\n" + 
			"where a.status_id = 'F'\r\n" + 
			"and a.proc_id = 2 \n" +
			"and a.co_id = '"+co_id+"'  " +
			"order by a.pv_no  desc" ;

		System.out.printf("PV For Sending to Treasury :" + display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Date Posted", "Payee");
		tblDoc_proctotal.showColumns("Date Posted", "Payee");
		
		tblDoc_proc.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		computeTotal();

	}	
	
	public void displayPV_forSenttoTreasury(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"select \r\n" + 
			"\r\n" + 
			"a.pv_no,\r\n" + 
			"null,\r\n" + 
			"null,\r\n" + 
			"b.amount,\r\n" + 
			"false,\r\n" + 
			"false,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"upper(trim(g.entity_name)),\r\n" + 
			"a.pv_date,  \r\n" + 
			"trim(e.return_message), \r\n " +
			"a.date_posted,  get_client_name(a.entity_id1)" +
			"\r\n" + 
			"from rf_pv_header a\r\n" + 
			"left join ( \r\n" + 
			"	select distinct on (pv_no, co_id) a.pv_no, a.co_id, sum(tran_amt) as amount from (\r\n" + 
			"	select pv_no, co_id, tran_amt  from rf_pv_detail where bal_side = 'D' and status_id = 'A' and co_id = '"+co_id+"') a\r\n" + 
			"	group by pv_no, co_id  ) b on trim(a.pv_no) = trim(b.pv_no) and a.co_id = b.co_id\r\n" + 
			"left join em_employee c on a.created_by=c.emp_code\r\n" + 
			"left join em_employee d on a.edited_by=d.emp_code\r\n" + 
			"left join rf_entity f on c.entity_id = f.entity_id   \n" +
			"left join rf_entity g on d.entity_id = g.entity_id   \n" +
			"left join (select * from rf_doc_return_message where status_id = 'A' and doc_id = '12' ) e on a.pv_no = e.doc_no and trim(a.co_id)=trim(e.co_id)    "  +
			"\r\n" + 
			"\r\n" + 
			"where a.status_id = 'P'\r\n" + 
			"and a.proc_id = 3 \n" +
			"and a.co_id = '"+co_id+"'  " +
			"order by a.pv_no  desc" ;

		System.out.printf("PV For Sending to Treasury :" + display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.hideColumns("For Process","For Return");
		tblDoc_proctotal.hideColumns("For Process","For Return");
		tblDoc_proc.showColumns("Date Posted", "Payee");
		tblDoc_proctotal.showColumns("Date Posted", "Payee");
		
		tblDoc_proc.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.hideColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		computeTotal();

	}	

	public void displayCV_forChecking(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String remarks, Integer intProcess) {//used

		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String display_sql = ""; 
		
		if (intProcess==1) {
			display_sql = "--CV : FOR CHECKING \n" +
					"select * from view_CV_forChecking_check('"+co_id+"', '"+remarks+"')";	
		} else {
			display_sql = "--CV : FOR CHECKING \n" +
					"select * from view_CV_forChecking_cash('"+co_id+"', '"+remarks+"')";
		}


		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();

	}	

	public void displayCV_forApproval(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String remarks) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR APPROVAL \n" +
			"select * from view_CV_forApproval('"+co_id+"', '"+remarks+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}
	
	public void displayCV_forCheckSignature_Cash(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR CHECK SIGNATURE - CASH\n" +
			"select * from view_CV_forCheckSignature_Cash('"+co_id+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}

	public void displayCV_forCheckSignature_Check(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String remarks) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR CHECK SIGNATURE - CHECK\n" +
			"select * from view_CV_forCheckSignature('"+co_id+"', '"+remarks+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}	

	public void displayCV_forReleasetoPayee(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String remarks) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR RELEASE TO PAYEE \n" +
			"select * from view_cv_forreleasetopayee('"+co_id+"', '"+remarks+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}	
	
	public void displayCV_ReleasedtoPayee_Cash(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : PAID OUT \n" +
			"select * from view_cv_releasetopayee_cash('"+co_id+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.hideColumns("For Process","For Return");
		tblDoc_proctotal.hideColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}	
	
	public void displayCV_ReleasedtoPayee_Check(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String remarks) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : PAID OUT \n" +
			"select * from view_cv_releasetopayee_v2('"+co_id+"', '"+remarks+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.hideColumns("For Process","For Return");
		tblDoc_proctotal.hideColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}
	
	public void displayCom_forChecking(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR CHECKING \n" +
			"select * from view_com_forChecking('"+co_id+"')";


		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();

	}	

	public void displayCom_forApproval(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR APPROVAL \n" +
			"select * from view_com_forApproval('"+co_id+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}	

	public void displayCom_forCheckSignature(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR CHECK SIGNATURE \n" +
			"select * from view_com_forCheckSignature('"+co_id+"')";	

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}		
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}

	public void displayCom_forReleasetoPayee_summit(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : FOR RELEASE TO PAYEE - SUMMIT\n" +
			"select * from view_com_forReleasetoPayee_summit('"+co_id+"')";		

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}	
	
	public void displayCom_forReleasetoPayee_carmona(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel);

		String display_sql = 			

			"--CV : FOR RELEASE TO PAYEE - CARMONA\n" +
			"select * from view_com_forReleasetoPayee_carmona('"+co_id+"')";		

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}	
	
	public void displayCom_ReleasedtoPayee_summit(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String display_sql = 			

			"--CV : PAID OUT - SUMMIT\n" +
			"select * from view_com_ReleasetoPayee_summit('"+co_id+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.hideColumns("For Process","For Return");
		tblDoc_proctotal.hideColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}
	
	public void displayCom_ReleasedtoPayee_carmona(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String display_sql = 			

			"--CV : PAID OUT - CARMONA\n" +
			"select * from view_com_ReleasetoPayee_carmona('"+co_id+"')";

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalJV(modelMain, modelTotal);			
		}		

		else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.hideColumns("For Process","For Return");
		tblDoc_proctotal.hideColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}
	
	public void enable_fields(Boolean x){//
		lblJV_no.setEnabled(x);	
		lookupDocType.setEnabled(x);	
		lblStatus.setEnabled(x);	
		txtStatus.setEnabled(x);	

		lblDateTrans.setEnabled(x);
		dteTransaction.setEnabled(x);
		lblDateEdited.setEnabled(x);	
		dteEdited.setEnabled(x);
		lblDatePosted.setEnabled(x);	
		dtePosted.setEnabled(x);

		lblTransType.setEnabled(x);	
		lblFiscalyear.setEnabled(x);	
		lblPeriod.setEnabled(x);	

		lookupTranType.setEnabled(x);	
		//lookupTranType.setEditable(x);
		lookupFiscalYr.setEnabled(x);	
		//lookupFiscalYr.setEditable(x);	
		lookupPeriod.setEnabled(x);	
		lookupPeriod.setEditable(x);

		tagDocType.setEnabled(x);	
		tagPeriod.setEnabled(x);	
		txtJV_Remark.setEditable(x);		
	}

	public void refresh_tablesMain(){//used

		//reset table 1
		FncTables.clearTable(modelDProc_account);
		FncTables.clearTable(modelDProc_accounttotal);			
		rowHeaderDProc = tblDoc_proc.getRowHeader22();
		scrollDProc_account.setRowHeaderView(rowHeaderDProc);	
		modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), null, null, null, null, null, null, null, null});

	}

	public static void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d){		
		btnSave.setEnabled(a);		
		btnPreview.setEnabled(b);
		btnRefresh.setEnabled(c);
		btnCancel.setEnabled(d);		
	}

	public void initialize_comp(){		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = sql_getCompanyLogo();	
		
		lblDoc_type.setEnabled(true);	
		lookupDocType.setEnabled(true);	
		lookupDocType.setEditable(true);	
		tagDocType.setEnabled(true);	
		
		lookupDocType.setLookupSQL(getDocType());							
		
		enableButtons(false, false, false, true);
		lookupCompany.setValue(co_id);
		inc_cash = true;
	}

	public void actionPerformed(ActionEvent e) { 		

		if (e.getActionCommand().equals("Save")) {
			checkAccess();
		}

		if (e.getActionCommand().equals("Preview")) {
			preview();
		} 

		if (e.getActionCommand().equals("Refresh")) {
			refresh();
		}

		if (e.getActionCommand().equals("Cancel")) {
			cancel();
		}
		
		if(e.getActionCommand().equals("Generate")) {
			generate();
		}
	}
	
	private void preview(){//**ADDED BY JED 2019-03-18 : THE DIALOG BOX WILL NOT APPEAR EVERY PRESS OF PREVIEW**//
		
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Report Period",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {	

			} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {

			}				
		} // CLOSED_OPTION
		
	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 1)) {
			clickTableColumn_account();
		}	
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

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

	private void save(){

		if (checkCompleteDetails()==false) {
			JOptionPane.showMessageDialog(getContentPane(), "Please enter return message at row no. " + return_row, "Information", 
				JOptionPane.WARNING_MESSAGE);
		} else {	

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {					

				pgUpdate db = new pgUpdate();				

				if (doc_no.equals("13 - DV") && proc_no.equals("6")) {
					updateHeaderStatus(db, "F", "rf_cv_header", "cv_no", 6); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");
				}
				
				if (doc_no.equals("13 - DV") && proc_no.equals("7")) {
					updateHeaderStatus(db, "F", "rf_cv_header", "cv_no", 7); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");
				}
				
				if (doc_no.equals("13 - DV") && proc_no.equals("8")) {
					updateHeaderStatus(db, "F", "rf_cv_header", "cv_no", 8); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");
				}

				if (doc_no.equals("13 - DV") && proc_no.equals("9")) {
					updateHeaderStatus(db, "P", "rf_cv_header", "cv_no", 9); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");
				}
				
				if (doc_no.equals("13 - DV") && proc_no.equals("10")) {
					updateHeaderStatus(db, "P", "rf_cv_header", "cv_no", 10); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");
				}
				
				if (doc_no.equals("13 - DV") && proc_no.equals("11")) {
					updateHeaderStatus(db, "P", "rf_cv_header", "cv_no", 11); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");
				}
				
				if (doc_no.equals("13 - DV") && proc_no.equals("12")) {
					updateHeaderStatus(db, "P", "rf_cv_header", "cv_no", 12); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");
				}
				
				if (doc_no.equals("13 - DV") && proc_no.equals("13")) {
					updateHeaderStatus(db, "P", "rf_cv_header", "cv_no", 13); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");  
					updateCV_header(db); 
					updateCDF_header(db);
					updateAuditTrail(db);
				}
				
				if (doc_no.equals("13 - DV") && proc_no.equals("14")) {
					updateHeaderStatus(db, "P", "rf_cv_header", "cv_no", 14); 
					returnDocument(db, "A", "rf_cv_header", "cv_no", 0, "13");  
					updateCV_header(db); 
					updateCDF_header(db);
					updateAuditTrail(db);
				}
				
				db.commit();					
				JOptionPane.showMessageDialog(getContentPane(),"Document's status updated.","Information",JOptionPane.INFORMATION_MESSAGE);	
				refresh();

				lblDoc_type.setEnabled(true);	
				lookupDocType.setEnabled(true);	
				lookupDocType.setEditable(true);	
			}
		}		
	}

	private void cancel(){//used
		if(!btnSave.isEnabled())  {

			lookupDocType.setValue("");
			tagDocType.setText("[ ]");
			lblProcess.setEnabled(false);	
			lookupProcess.setEnabled(false);	
			//lookupProcess.setEditable(false);	
			lookupProcess.setValue("");
			tagProcess.setEnabled(false);	
			tagProcess.setText("[ ]");
			refresh_tablesMain();
			enableButtons(false, false, false, true);
			txtRemarks.setText("");
			txtRemarks.setEnabled(false);
			lblRemarks.setEnabled(false);
			btnPreview.setText("Preview");
			btnPreview.setActionCommand("Preview");
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				lookupDocType.setValue("");
				tagDocType.setText("[ ]");
				lblProcess.setEnabled(false);	
				lookupProcess.setEnabled(false);	
				//lookupProcess.setEditable(false);	
				lookupProcess.setValue("");
				tagProcess.setEnabled(false);	
				tagProcess.setText("[ ]");
				refresh_tablesMain();
				enableButtons(false, false, false, true);	
				txtRemarks.setText("");
				txtRemarks.setEnabled(false);
				lblRemarks.setEnabled(false);
				btnPreview.setText("Preview");
				btnPreview.setActionCommand("Preview");
			} 	else {}			
		}
	}
	
	private void preview_Com(){ 

		String doc_id = "";
		String date_name = "***";
		String process = "";
		String status_id = "";
		String rw = String.valueOf(tblDoc_proc.getModel().getRowCount());
		String branch_id = ""; 
		
		Integer proc_id = 0;

		if(proc_no.equals("1")){ doc_id = "COMMISSION";  process = "FOR CHECKING";  proc_id = 0; status_id = "A"; date_name = "cv_date";}
		if(proc_no.equals("2")){ doc_id = "COMMISSION";  process = "FOR APPROVAL";  proc_id = 1; status_id = "F"; date_name = "cv_date";}
		if(proc_no.equals("3")){ doc_id = "COMMISSION";  process = "FOR CHECK SIGNATURE";  proc_id = 2; status_id = "F"; date_name = "cv_date";}

		if (proc_no.equals("4")) {
			doc_id = "COMMISSION";
			process = "SEND TO CASHIER SUMMIT";  
			proc_id = 3; 
			status_id = "P"; 
			date_name = "cv_date";
			branch_id = "01"; 
		}
		
		if (proc_no.equals("5")) {
			doc_id = "COMMISSION";
			process = "SEND TO CASHIER CARMONA";  
			proc_id = 3; 
			status_id = "P"; 
			date_name = "cv_date";
			branch_id = "10"; 
		}
		
		if(proc_no.equals("6")){ doc_id = "COMMISSION";  process = "FOR RELEASE TO PAYEE - SUMMIT";  proc_id = 5; status_id = "P"; date_name = "date_posted";}
		if(proc_no.equals("7")){ doc_id = "COMMISSION";  process = "FOR RELEASE TO PAYEE - CARMONA";  proc_id = 6; status_id = "P"; date_name = "date_posted";}
		if(proc_no.equals("8")){ doc_id = "COMMISSION";  process = "PAID OUT - SUMMIT";  proc_id = 7; status_id = "P"; date_name = "date_paid";}
		if(proc_no.equals("9")){ doc_id = "COMMISSION";  process = "PAID OUT - CARMONA";  proc_id = 8; status_id = "P"; date_name = "date_paid";}

		//DATE NAME		
		
		String criteria = "Document Processing Monitoring Report - Commission";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("logo1", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("logo2", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("doc_id", doc_id);
		mapParameters.put("process", process);
		mapParameters.put("proc_id", proc_id);
		mapParameters.put("status_id", status_id);
		mapParameters.put("row_count", rw);
		mapParameters.put("cur_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("date_name", date_name);	
		mapParameters.put("date_from", dteRefDateFr.getDate());	
		mapParameters.put("date_to", dteRefDateTo.getDate());
		mapParameters.put("date_to", dteRefDateTo.getDate());
		mapParameters.put("branch_id", branch_id);

		if(proc_no.equals("1") || proc_no.equals("2") || proc_no.equals("3") || proc_no.equals("4") || proc_no.equals("5")) {
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_Monitoring_Rpt_withPhase.jasper", "Document Processing - Commission with Phase", company, mapParameters); 
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_Monitoring_Rpt.jasper", reportTitle, company, mapParameters); 
		} else if(proc_no.equals("6")) {
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_ForReleaseToPayee_withPhase_summit.jasper", "Document Processing - Commission with Phase", company, mapParameters); 
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_ForReleaseToPayee_perDiv_summit.jasper", "Document Processing - Commission per Division", company, mapParameters); 
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_ForReleaseToPayee_summit.jasper", reportTitle, company, mapParameters); 
		} else if(proc_no.equals("7")) {
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_ForReleaseToPayee_withPhase_carmona.jasper", "Document Processing - Commission with Phase", company, mapParameters); 
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_ForReleaseToPayee_perDiv_carmona.jasper", "Document Processing - Commission per Division", company, mapParameters); 
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_ForReleaseToPayee_carmona.jasper", reportTitle, company, mapParameters); 
		} else if(proc_no.equals("8")) {
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_Monitoring_Rpt_Check_withPhase_summit.jasper", "Document Processing - Commission with Phase", company, mapParameters); 
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_Monitoring_Rpt_Check_summit.jasper", reportTitle, company, mapParameters); 
		} else if(proc_no.equals("9")) {
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_Monitoring_Rpt_Check_withPhase_carmona.jasper", "Document Processing - Commission with Phase", company, mapParameters); 
			FncReport.generateReport("/Reports/rptCom_Docs_Processing_Monitoring_Rpt_Check_carmona.jasper", reportTitle, company, mapParameters); 
		}
	}
	
	private void preview_CV_cash(){//used

		String doc_id = "";
		String date_name = "***";
		String process = "";
		String status_id = "";
		Integer proc_id = 0;
		String rw = String.valueOf(tblDoc_proc.getModel().getRowCount());	
		String remarks = txtRemarks.getText();

		doc_id = "DISBURSEMENT VOUCHER";  
		process = "FOR RELEASE TO PAYEE";  
		proc_id = 9; 
		status_id = "P"; 
		date_name = "date_posted"; 	

		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", company);
		mapParameters2.put("co_id", co_id);
		mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters2.put("logo1", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters2.put("logo2", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters2.put("prepared_by", UserInfo.FullName);
		mapParameters2.put("doc_id", doc_id);
		mapParameters2.put("process", process);
		mapParameters2.put("proc_id", proc_id);
		mapParameters2.put("status_id", status_id);
		mapParameters2.put("row_count", rw);
		mapParameters2.put("cur_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters2.put("date_name", date_name);	
		mapParameters2.put("date_from", dteRefDateFr.getDate());	
		mapParameters2.put("date_to", dteRefDateTo.getDate());	
		mapParameters2.put("inc_cash", true);
		mapParameters2.put("pay_type", "A");
		mapParameters2.put("remarks", remarks);//**ADDED BY JED 2019-03-18 DCRF NO.967**//
		
		FncReport.generateReport("/Reports/rptCV_Docs_Processing_Monitoring_Rpt.jasper", "Document Processing - CV (Cash)", mapParameters2);
		
	}
	
	private void preview_JV(){//used

		String doc_id 	= "";
		String process 	= "";
		String status_id= "";
		String date_name= "***";
		Integer proc_id = 0;
		String rw 		= String.valueOf(tblDoc_proc.getModel().getRowCount());	

		//JOURNAL VOUCHER
		if(proc_no.equals("1")){ doc_id = "JOURNAL VOUCHER";  process = "FOR CHECKING";  proc_id = 0; status_id = "A"; date_name = "jv_date";}
		if(proc_no.equals("2")){ doc_id = "JOURNAL VOUCHER";  process = "FOR APPROVAL";  proc_id = 1; status_id = "F"; date_name = "jv_date";}
		if(proc_no.equals("3")){ doc_id = "JOURNAL VOUCHER";  process = "APPROVED";  proc_id = 2; status_id = "P"; date_name = "date_posted";}
		
		String criteria = "Document Processing Monitoring Report - JV";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("doc_id", doc_id);
		mapParameters.put("process", process);
		mapParameters.put("proc_id", proc_id);
		mapParameters.put("status_id", status_id);
		mapParameters.put("row_count", rw);
		mapParameters.put("cur_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("date_name", date_name);	
		mapParameters.put("date_from", dteRefDateFr.getDate());	
		mapParameters.put("date_to", dteRefDateTo.getDate());	

		FncReport.generateReport("/Reports/rptJV_Docs_Processing_Monitoring_Rpt.jasper", reportTitle, company, mapParameters); 

	}
	
	private void preview_PV(){//used

		String doc_id 	= "";
		String process 	= "";
		String status_id= "";
		Integer proc_id = 0;
		String date_name= "***";
		String rw 		= String.valueOf(tblDoc_proc.getModel().getRowCount());	
	
		//PAYABLE VOUCHER
		if(proc_no.equals("1")){ doc_id = "PAYABLE VOUCHER";  process = "FOR CHECKING";  proc_id = 0; status_id = "A"; date_name = "pv_date";}
		if(proc_no.equals("2")){ doc_id = "PAYABLE VOUCHER";  process = "FOR APPROVAL";  proc_id = 1; status_id = "F"; date_name = "pv_date";}
		if(proc_no.equals("3")){ doc_id = "PAYABLE VOUCHER";  process = "FOR SENDING TO TREASURY";  proc_id = 2; status_id = "F"; date_name = "pv_date";}
		if(proc_no.equals("4")){ doc_id = "PAYABLE VOUCHER";  process = "SENT TO TREASURY";  proc_id = 3; status_id = "P"; date_name = "date_posted";}

		String criteria = "Document Processing Monitoring Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("doc_id", doc_id);
		mapParameters.put("process", process);
		mapParameters.put("proc_id", proc_id);
		mapParameters.put("status_id", status_id);
		mapParameters.put("row_count", rw);
		mapParameters.put("cur_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("date_name", date_name);	
		mapParameters.put("date_from", dteRefDateFr.getDate());	
		mapParameters.put("date_to", dteRefDateTo.getDate());	
		
		FncReport.generateReport("/Reports/rptPV_Docs_Processing_Monitoring_Rpt.jasper", reportTitle, company, mapParameters); 


	}

	public static String getDocType() { 

		return "select '13 - DV' as \"Doc Type No.\" ,   'DISBURSEMENT VOUCHER' as \"Doc. Name\"\n"; 

	}

	public static  String getJV_processes() {
		return 

		"select '1' as \"Process No.\" ,   'FOR CHECKING' as \"Process Name\"    union all    \n" +
		"select '2' as \"Process No.\" ,   'FOR APPROVAL' as \"Process Name\"     union all   \n" +
		"select '3' as \"Process No.\" ,   'APPROVED' as \"Process Name\"      \n" ;
	}

	public static  String getPV_processes(){//used
		return 

		"select '1' as \"Process No.\" ,   'FOR CHECKING' as \"Process Name\"    union all    \n" +
		"select '2' as \"Process No.\" ,   'FOR APPROVAL' as \"Process Name\"    union all    \n" +
		"select '3' as \"Process No.\" ,   'SEND TO TREASURY' as \"Process Name\"     union all    \n" +
		"select '4' as \"Process No.\" ,   'SENT TO TREASURY' as \"Process Name\"      \n" ;
	}

	public static  String getDV_processes() { 
		return
				
		"select '6' as \"Process No.\" ,   'FOR CHECKING - CHECK' as \"Process Name\"    union all    \n" +
		"select '7' as \"Process No.\" ,   'FOR CHECKING - CASH' as \"Process Name\"    union all    \n" +
		"select '8' as \"Process No.\" ,   'FOR APPROVAL' as \"Process Name\"    union all    \n" +
		"select '9' as \"Process No.\" ,   'FOR SIGNATURE - CASH' as \"Process Name\"    union all    \n" +
		"select '10' as \"Process No.\" ,   'FOR CHECK SIGNATURE SEND TO OTB' as \"Process Name\"    union all    \n" +
		"select '11' as \"Process No.\" ,   'SEND TO MANDALA' as \"Process Name\"    union all    \n" +
		"select '12' as \"Process No.\" ,   'SEND TO SUMMIT' as \"Process Name\"    union all    \n" +
		"select '13' as \"Process No.\" ,   'FOR RELEASE TO PAYEE - SUMMIT' as \"Process Name\" union all      \n" +
		"select '14' as \"Process No.\" ,   'FOR RELEASE TO PAYEE - MANDALA' as \"Process Name\" union all      \n" +
		"select '15' as \"Process No.\" ,   'PAID OUT - CHECK SUMMIT' as \"Process Name\"  union all     \n" +
		"select '16' as \"Process No.\" ,   'PAID OUT - CHECK MANDALA' as \"Process Name\"  union all     \n" +
		"select '17' as \"Process No.\" ,   'PAID OUT - CASH' as \"Process Name\"      \n" ;
	}
	
	public static  String getCom_processes() {
		return 

		"select '1' as \"Process No.\" ,   'FOR CHECKING' as \"Process Name\"    union all    \n" +
		"select '2' as \"Process No.\" ,   'FOR APPROVAL' as \"Process Name\"    union all    \n" +
		"select '3' as \"Process No.\" ,   'FOR CHECK SIGNATURE' as \"Process Name\"    union all    \n" +
		"select '4' as \"Process No.\" ,   'SEND TO CASHIER SUMMIT' as \"Process Name\"    union all    \n" +
		"select '5' as \"Process No.\" ,   'SEND TO CASHIER CARMONA' as \"Process Name\"    union all    \n" +
		"select '6' as \"Process No.\" ,   'FOR RELEASE TO PAYEE - SUMMIT' as \"Process Name\" union all      \n" +
		"select '7' as \"Process No.\" ,   'FOR RELEASE TO PAYEE - CARMONA' as \"Process Name\" union all      \n" +
		"select '8' as \"Process No.\" ,   'PAID OUT - SUMMIT' as \"Process Name\" union all      \n" +
		"select '9' as \"Process No.\" ,   'PAID OUT - CARMONA' as \"Process Name\"    \n" ;
	}

	public String getPeriod(){//Used
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

	public String sql_getCDFno(String doc) {

		String cdf_no = ""; 
		
		String SQL = 
			" select cdf_no from cm_cdf_hd where rplf_no in ( select pv_no from rf_pv_header where cv_no = '"+doc+"' and co_id = '"+co_id+"' )     \r\n" ;

		System.out.printf("SQL - sql_getCDFno : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){			
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {cdf_no = "";}
			else {cdf_no = (String) db.getResult()[0][0]; }
			
		}else{
			cdf_no = "";
		}

		return cdf_no;
	}
	
	public static String sql_getCompanyLogo() {

		String a = "";

		String SQL = 
			"select company_logo from mf_company \n" + 
			"where co_id = '"+co_id+"' " ;

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
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
			
	private Boolean checkCompleteDetails(){//

		boolean comp = true;
		int rw = tblDoc_proc.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(x,5) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(x,5));
			}
			if(modelDProc_account.getValueAt(x,5) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(x,5);
			}

			return_row = x + 1;

			if(isTrue){					
				try {@SuppressWarnings("unused")
					String return_remarks = modelDProc_account.getValueAt(x,9).toString().trim();} 
				catch (NullPointerException e) { comp = false; break; }				
			}

			x++;
		}

		return comp;
	}

	private void checkAccess() {
		
		System.out.println("Checking Access..");
		
		if (doc_no.equals("13 - DV") && proc_no.equals("6") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {
			save();
		} else if (doc_no.equals("13 - DV") && proc_no.equals("6") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to check disbursement.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		if (doc_no.equals("13 - DV") && proc_no.equals("7") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {
			save();
		} else if (doc_no.equals("13 - DV") && proc_no.equals("7") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to check disbursement.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (doc_no.equals("13 - DV")&&proc_no.equals("8") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==true){
			save();
		} else if (doc_no.equals("13 - DV")&&proc_no.equals("8") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to post PV.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		if (doc_no.equals("13 - DV")&&proc_no.equals("9") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true){
			save();
		} else if (doc_no.equals("13 - DV")&&proc_no.equals("9") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to send \n" +
				"disbursement for cash signature.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (doc_no.equals("13 - DV")&&proc_no.equals("10") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true){
			save();
		} else if (doc_no.equals("13 - DV")&&proc_no.equals("10") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to send \n" +
				"disbursement for check signature.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (doc_no.equals("13 - DV")&&proc_no.equals("11") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true){
			save();
		} else if (doc_no.equals("13 - DV")&&proc_no.equals("11") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to send \n" +
				"disbursement to Summit.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (doc_no.equals("13 - DV")&&proc_no.equals("12") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true){
			save();
		} else if (doc_no.equals("13 - DV")&&proc_no.equals("12") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to send \n" +
				"disbursement to Mandala.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		if (doc_no.equals("13 - DV")&&proc_no.equals("13") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {
			save();
		} else if (doc_no.equals("13 - DV") && proc_no.equals("13") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to release \n" +
				"disbursement to payee.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (doc_no.equals("13 - DV")&&proc_no.equals("14") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true) {
			save();
		} else if (doc_no.equals("13 - DV") && proc_no.equals("14") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to release \n" +
				"disbursement to payee.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	private void countTaggedRow(Integer col_count){//used

		int rw = tblDoc_proc.getModel().getRowCount();	
		int x = 0;
		int count = 0;

		while (x < rw) {			

			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(x,col_count) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(x,col_count));
			}
			if(modelDProc_account.getValueAt(x,col_count) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(x,col_count);
			}

			if(isTrue){				
				count++;
			}

			x++;
		}

		modelDProc_accounttotal.setValueAt(count, 0, col_count);

	}

	private void clickTableColumn_account() {//used

		int column = tblDoc_proc.getSelectedColumn();
		int row = tblDoc_proc.getSelectedRow();				

		if (column == 4 ) {  

			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(row,4) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(row,4));
			}
			if(modelDProc_account.getValueAt(row,4) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(row,4);
			}

			if(isTrue){				
				modelDProc_account.setValueAt(false, row, 5);
				countTaggedRow(4);
				countTaggedRow(5);
			}		
			else {}			
		}

		if (column == 5 ) {  

			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(row,5) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(row,5));
			}
			if(modelDProc_account.getValueAt(row,5) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(row,5);
			}

			if(isTrue){				
				modelDProc_account.setValueAt(false, row, 4);
				countTaggedRow(4);
				countTaggedRow(5);
			}		
			else {}			
		}
	}

	private void adjustRowHeight_account(){//used
		int rw = tblDoc_proc.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblDoc_proc.setRowHeight(x, 22);			
			x++;
		}

	}

	public void returnDocument(pgUpdate db, String status, String table, String doc, Integer proc_id, String doc_id) {//used	

		for(int y=0; y < modelDProc_account.getRowCount(); y++){
			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(y,5) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(y,5));
			}
			if(modelDProc_account.getValueAt(y,5) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(y,5);
			}

			if(isTrue){	
				//update doc_table status
				String doc_no = modelDProc_account.getValueAt(y,0).toString().trim();
				String sqlDetail = 
					"update "+table+" set \n" ;
				if (status.equals("A")||status.equals("F")) {
					sqlDetail = sqlDetail +
					"posted_by = null, \n" +
					"date_posted = null, \n" ;						
				} 
				sqlDetail = sqlDetail +		
				"status_id = '"+status+"', \n" +
				"proc_id = "+proc_id+" \n" +
				"where trim("+doc+") = trim('"+doc_no+"') and co_id = '"+co_id+"'   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);	

				//update return message status				
				String sqlDetail2 = 
					"update rf_doc_return_message set \n" +
					"status_id = 'I' \n" +
					"where trim(doc_no) = trim('"+doc_no+"') and co_id = '"+co_id+"'   " ;

				System.out.printf("SQL #2: %s", sqlDetail2);
				db.executeUpdate(sqlDetail2, false);					

				//insert return message
				String message = modelDProc_account.getValueAt(y,9).toString().trim().replace("'", "''");
				String sqlDetail3 = 
					"insert into rf_doc_return_message values (   \n" +					
					"'"+doc_id+"' , \n" +
					"'"+doc_no+"' , \n" +
					"'"+UserInfo.EmployeeCode+"',  \n" +
					"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"', \n" +
					"'"+message+"' , \n" +
					"'A',  \n" +
					"'"+co_id+"'  \n" +
					") " ;

				System.out.printf("SQL #3: %s", sqlDetail3);
				db.executeUpdate(sqlDetail3, false);	

			}			
		}
	}

	public void updateCV_header(pgUpdate db){

		for(int y=0; y < modelDProc_account.getRowCount(); y++){
			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(y,4) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(y,4));
			}
			if(modelDProc_account.getValueAt(y,4) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(y,4);
			}

			if(isTrue){	
				String doc_no = modelDProc_account.getValueAt(y,0).toString().trim();
				String sqlDetail = 
					"update rf_cv_header set " +
					"date_paid = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"'  \n" +  //5
					"where cv_no = '"+doc_no+"' and co_id = '"+co_id+"'   \n" ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);		
			}
		}
	}
	
	public void updateCheck_summit(){

		for(int y=0; y < modelDProc_account.getRowCount(); y++){
			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(y,4) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(y,4));
			}
			if(modelDProc_account.getValueAt(y,4) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(y,4);
			}

			if(isTrue){	
				String doc_no = modelDProc_account.getValueAt(y,0).toString().trim();
				String SQL = "UPDATE rf_check set branch = '01' where cv_no = '"+doc_no+"'";
		
				pgUpdate db = new pgUpdate();
				db.executeUpdate(SQL, false);
				db.commit();	
			}
		}
	}
	
	public void updateCheck_carmona(){

		for(int y=0; y < modelDProc_account.getRowCount(); y++){
			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(y,4) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(y,4));
			}
			if(modelDProc_account.getValueAt(y,4) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(y,4);
			}

			if(isTrue){	
				String doc_no = modelDProc_account.getValueAt(y,0).toString().trim();
				String SQL = "UPDATE rf_check set branch = '10' where cv_no = '"+doc_no+"'";
		
				pgUpdate db = new pgUpdate();
				db.executeUpdate(SQL, false);
				db.commit();	
			}
		}
	}

	public void updateCDF_header(pgUpdate db){

		for(int y=0; y < modelDProc_account.getRowCount(); y++){
			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(y,4) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(y,4));
			}
			if(modelDProc_account.getValueAt(y,4) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(y,4);
			}

			if(isTrue){	
				//CDF status update
				String doc_no = modelDProc_account.getValueAt(y,0).toString().trim();
				String sqlDetail = 
					"update cm_cdf_hd set \n" +
					"status_id = 'R', \n" +
					"date_paid = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +  
					"date_released = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"'  \n" +  
					"where rplf_no in ( select pv_no from rf_pv_header where cv_no = '"+doc_no+"' and co_id = '"+co_id+"' )  \n" ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);		
				
				//Comm Sched Status Update
				String sqlDetail2 = 
					"update cm_schedule_dl set \n" +
					"status = 'R' \n" +
					"where cdf_no = '"+sql_getCDFno(doc_no)+"' and co_id = '"+co_id+"' " +
					"and agent_code in ( select entity_id1 from rf_pv_header where cv_no = '"+doc_no+"' and co_id = '"+co_id+"' )   \n" ;

				System.out.printf("SQL #2: %s", sqlDetail2);
				db.executeUpdate(sqlDetail2, false);		
			}
		}
	}
	
	public void updateAuditTrail(pgUpdate db){

		for(int y=0; y < modelDProc_account.getRowCount(); y++){
			Boolean isTrue = false;
			if(modelDProc_account.getValueAt(y,4) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(y,4));
			}
			if(modelDProc_account.getValueAt(y,4) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(y,4);
			}

			if(isTrue){	
				String doc_no = modelDProc_account.getValueAt(y,0).toString().trim();
				String user_code	= UserInfo.EmployeeCode;	

				String sqlDetail = 
						"INSERT INTO mf_audit_trail(\n" + 
						"	              system_id, activity, user_code, date_upd, entity_id, \n" + 
						"	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n" + 
						"	      VALUES ('CV', 'Paid-Out', '"+user_code+"', NOW(), NULL, \n" + 
						"	              NULL, NULL, NULL, NULL, NULL, '"+doc_no+"');" ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);	
			}
		}
	}

	private void computeTotal() {
		BigDecimal totalAmountCommitted = new BigDecimal("0.00");

		for (int x = 0; x < modelDProc_account.getRowCount(); x++) {
			BigDecimal totalamount = (BigDecimal) modelDProc_account.getValueAt(x, 3);

			try {
				totalAmountCommitted = totalAmountCommitted.add(totalamount);
			} catch (Exception e1) { }
		}

		modelDProc_accounttotal.setValueAt(df.format(totalAmountCommitted), 0, 3);
	}
	
	public void displayCom_sendto(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String strBranch) {//used

		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String display_sql = "--CV : FOR SEND TO SUMMIT/CARMONA\n" +
			"select * from view_com_sendto('"+co_id+"', '"+strBranch+"')";	

		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		
		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}			
		} else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);
		}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}
	
	private void disbursementVoucherProcess() {
		
		if (lookupProcess.getText().trim().equals("6")) {
			proc_no = "6"; 
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true); 
			btnPreview.setText("Generate"); 
			btnPreview.setActionCommand("Generate"); 		
		} else if (lookupProcess.getText().trim().equals("7")) { 
			proc_no = "7"; 
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true); 
			btnPreview.setText("Generate"); 
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("8")) { 
			proc_no = "8";
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true);
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("9")) {
			proc_no = "9"; 
			txtRemarks.setText("");
		} else if (lookupProcess.getText().trim().equals("10")) {
			proc_no = "10";
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true);
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("11")) {
			proc_no = "11";
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("12")) {
			proc_no = "12";
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("13")) {
			proc_no = "13"; 
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true);
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("14")) {
			proc_no = "14"; 
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true);
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("15")) {
			proc_no = "15";
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true);
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("16")) {
			proc_no = "16";
			txtRemarks.setEnabled(true); 
			lblRemarks.setEnabled(true);
			btnPreview.setText("Generate");
			btnPreview.setActionCommand("Generate");
		} else if (lookupProcess.getText().trim().equals("17")) {
			proc_no = "17"; 
			txtRemarks.setText("");
		}
		
		if (doc_no.equals("13 - DV") && proc_no.equals("9")) {
			displayCV_forCheckSignature_Cash(modelDProc_account, rowHeaderDProc, modelDProc_accounttotal);
		}

		if (doc_no.equals("13 - DV") && proc_no.equals("17")) {
			displayCV(modelDProc_account, rowHeaderDProc, modelDProc_accounttotal, txtRemarks.getText(), new Integer(proc_no));
		}
	
		if (doc_no.equals("13 - DV") && (proc_no.equals("15") || proc_no.equals("16"))) {		
			enableButtons(false, true, true, true);
			tblDoc_proc.hideColumns("For Process", "For Return");
			tblDoc_proctotal.hideColumns("For Process", "For Return");
		}
		
		modelDProc_account.setEditable(true);
		enableButtons(true, true, true, true);
	}
	
	private void generate() {
		String remarks = txtRemarks.getText();
		
		displayCV(modelDProc_account, rowHeaderDProc, modelDProc_accounttotal, remarks, new Integer(proc_no));
		
		txtRemarks.setEnabled(false);
		lblRemarks.setEnabled(false);
		btnPreview.setText("Preview");
		btnPreview.setActionCommand("Preview");
	}
	
	public void displayCV(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String remarks, Integer intProcess) {

		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String display_sql = "";
		
		if (intProcess==6) {
			display_sql = "select * from view_cv_forchecking_check('"+co_id+"', '"+remarks+"')";
		} else if (intProcess==7) {
			display_sql = "select * from view_cv_forchecking_cash('"+co_id+"', '"+remarks+"')";
		} else if (intProcess==8) {
			display_sql = "select * from view_cv_forapproval_v2('"+co_id+"', '"+remarks+"')";
		} else if (intProcess==9) {
			display_sql = "select * from view_cv_forsignature_cash('"+co_id+"')";
		} else if (intProcess==10) {
			display_sql = "select * from view_cv_forsignature_check('"+co_id+"')";
		} else if (intProcess==11) {
			display_sql = "select * from view_cv_sendtomandala('"+co_id+"')";
		} else if (intProcess==12) {
			display_sql = "select * from view_cv_sendtosummit('"+co_id+"')";
		} else if (intProcess==13) {
			display_sql = "select * from view_cv_forreleasetopayee_summit('"+co_id+"', '"+remarks+"')"; 
		} else if (intProcess==14) {
			display_sql = "select * from view_cv_forreleasetopayee_mandala('"+co_id+"', '"+remarks+"')"; 
		} else if (intProcess==15) {
			display_sql = "select * from view_cv_paidoutcheck_summit('"+co_id+"', '"+remarks+"')";
		} else if (intProcess==16) {
			display_sql = "select * from view_cv_paidoutcheck_mandala('"+co_id+"', '"+remarks+"')";
		} else if (intProcess==17) {
			//display_sql = "select * from view_cv_paidoutcash('"+co_id+"', '"+remarks+"')";
			display_sql = "select * from view_cv_paidoutcash('"+co_id+"')";
		}
		
		System.out.printf(display_sql);
		pgSelect db = new pgSelect();
		db.select(display_sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}			
		} else {
			modelDProc_accounttotal = new modelDocsProcessing_total();
			modelDProc_accounttotal.addRow(new Object[] { null, null, "Total", new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null, null });

			tblDoc_proctotal = new _JTableTotal(modelDProc_accounttotal, tblDoc_proc);
			tblDoc_proctotal.setFont(dialog11Bold);
			scrollDProc_accounttotal.setViewportView(tblDoc_proctotal);
			((_JTableTotal) tblDoc_proctotal).setTotalLabel(3);
		}

		tblDoc_proc.packAll();	

		adjustRowHeight_account();
		tblDoc_proc.getColumnModel().getColumn(9).setPreferredWidth(300);
		tblDoc_proc.showColumns("For Process","For Return");
		tblDoc_proctotal.showColumns("For Process","For Return");
		tblDoc_proc.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proctotal.showColumns("Check Date", "Check No.", "Authorized Person", "Authorized Representative");
		tblDoc_proc.hideColumns("Date Posted", "Payee");
		tblDoc_proctotal.hideColumns("Date Posted", "Payee");
		
		computeTotal();
	}
	
	public void updateHeaderStatus(pgUpdate db, String status, String table, String doc, Integer proc_id) {

		for(int y=0; y < modelDProc_account.getRowCount(); y++){
			Boolean isTrue = false;
			if (modelDProc_account.getValueAt(y,4) instanceof String){
				isTrue = new Boolean((String)modelDProc_account.getValueAt(y, 4));
			}
			if (modelDProc_account.getValueAt(y,4) instanceof Boolean){
				isTrue = (Boolean)modelDProc_account.getValueAt(y, 4);
			}

			if(isTrue){	

				String doc_no = modelDProc_account.getValueAt(y,0).toString().trim();
				String sqlDetail = "update "+table+" set \n" ;
				
				if (status.equals("P") && table != "rf_liq_header") {
					sqlDetail = sqlDetail +
					"posted_by = '"+UserInfo.EmployeeCode+"', \n" +
					"date_posted = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',\n";							
				}
				
				if (status.equals("P") && table.equals("rf_liq_header")) {
					sqlDetail = sqlDetail +
					"edited_by = '"+UserInfo.EmployeeCode+"', \n" +
					"date_edited = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',\n";							
				}
				
				if (status.equals("A")||status.equals("F") && table != "rf_liq_header") {
					sqlDetail = sqlDetail +
					"posted_by = null, \n" +
					"date_posted = null, \n" ;						
				}
				
				if (table.equals("rf_cv_header") && proc_id.equals(14) && doc_no.equals("13 - DV")) {
					sqlDetail = sqlDetail +
					"date_paid = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',\n";						
				}
				
				if (table.equals("rf_cv_header") && proc_id.equals(14) && doc_no.equals("13 - DV")) {
					sqlDetail = sqlDetail +
					"date_paid = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',\n";						
				}
				
				sqlDetail = sqlDetail +		
				"status_id = '"+status+"', \n" +
				"proc_id = "+proc_id+" \n" +
				"where trim("+doc+") = trim('"+doc_no+"') and co_id = '"+co_id+"'   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);	

			}			
		}
	}
	
	private void refresh() {
		doc_no = lookupDocType.getText().trim();			
		proc_no = lookupProcess.getText().trim();
		String remarks = txtRemarks.getText();
		refresh_tablesMain();

		displayCV(modelDProc_account, rowHeaderDProc, modelDProc_accounttotal, remarks, new Integer(proc_no));
		
		modelDProc_account.setEditable(true);
		btnSave.setEnabled(true);
		btnRefresh.setEnabled(true);
		btnCancel.setEnabled(true);
		inc_cash = true;
	}
	
	
	private void preview_CV() {

		String doc_id = "";
		String date_name = "***";
		String process = "";
		String status_id = "";
		Integer proc_id = 0;
		String rw = String.valueOf(tblDoc_proc.getModel().getRowCount());	
		String remarks = txtRemarks.getText();

		if (proc_no.equals("6")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "FOR CHECKING";
			proc_id = 0; 
			status_id = "A"; 
			date_name = "cv_date"; 
			pay_type = "";
		}
		
		if (proc_no.equals("7")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "FOR CHECKING";
			proc_id = 0; 
			status_id = "A"; 
			date_name = "cv_date"; 
			pay_type = "";
		}
		
		if (proc_no.equals("8")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "FOR APPROVAL";
			proc_id = 6; 
			status_id = "F"; 
			date_name = "cv_date"; 
			pay_type = "";
		}
		
		if (proc_no.equals("9")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "FOR SIGNATURE - CASH";
			proc_id = 8; 
			status_id = "F"; 
			date_name = "cv_date"; 
			pay_type = "A";
		}
		
		if (proc_no.equals("10")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "FOR CHECK SIGNATURE SEND TO OTB";
			proc_id = 8; 
			status_id = "F"; 
			date_name = "cv_date"; 
			pay_type = "B";
		}
		
		if (proc_no.equals("11")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "SEND TO MANDALA";
			proc_id = 9; 
			status_id = "F"; 
			date_name = "cv_date"; 
			pay_type = "B";
		}
		
		if (proc_no.equals("12")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "SEND TO SUMMIT";
			proc_id = 12; 
			status_id = "F"; 
			date_name = "cv_date"; 
			pay_type = "B";
		}
		
		if (proc_no.equals("13")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "FOR RELEASE TO PAYEE - SUMMIT";
			proc_id = 11; 
			status_id = "P"; 
			date_name = "date_posted"; 
			pay_type = "B";
		}

		if (proc_no.equals("14")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "FOR RELEASE TO PAYEE - MANDALA";
			proc_id = 12; 
			status_id = "P"; 
			date_name = "date_posted"; 
			pay_type = "B";
		}
		
		if (proc_no.equals("17")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "PAID OUT - CASH";
			proc_id = 13; 
			status_id = "P"; 
			date_name = "date_paid"; 
			pay_type = "A";
		}
		
		if (proc_no.equals("15")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "PAID OUT - CHECK SUMMIT";
			proc_id = 13; 
			status_id = "P"; 
			date_name = "date_paid"; 
			pay_type = "B";
		}

		if (proc_no.equals("16")) {
			doc_id = "DISBURSEMENT VOUCHER";
			process = "PAID OUT - CHECK MANDALA";
			proc_id = 14; 
			status_id = "P"; 
			date_name = "date_paid"; 
			pay_type = "B";
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("logo1", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("logo2", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("doc_id", doc_id);
		mapParameters.put("process", process);
		mapParameters.put("proc_id", proc_id);
		mapParameters.put("status_id", status_id);
		mapParameters.put("row_count", rw);
		mapParameters.put("cur_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("date_name", date_name);	
		mapParameters.put("date_from", dteRefDateFr.getDate());	
		mapParameters.put("date_to", dteRefDateTo.getDate());	
		mapParameters.put("inc_cash", inc_cash);	
		mapParameters.put("pay_type", pay_type);
		
		System.out.println("");
		System.out.println("co_id: "+co_id);
		System.out.println("proc_id: "+proc_id);
		System.out.println("date_from: "+dteRefDateFr.getDate());
		System.out.println("date_to: "+dteRefDateTo.getDate());
		System.out.println("status_id: "+status_id);
		System.out.println("inc_cash: "+inc_cash);
		System.out.println("pay_type: "+pay_type);
		
		FncReport.generateReport("/Reports/rptCV_Docs_Processing_Monitoring_Rpt_withPhase.jasper", "Document Processing - CV with Phase", mapParameters); 
		
		Map<String, Object> mapParameters3 = new HashMap<String, Object>();
		mapParameters3.put("company", company);
		mapParameters3.put("co_id", co_id);
		mapParameters3.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters3.put("logo1", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters3.put("logo2", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters3.put("prepared_by", UserInfo.FullName);
		mapParameters3.put("doc_id", doc_id);
		mapParameters3.put("process", process);
		mapParameters3.put("proc_id", proc_id);
		mapParameters3.put("status_id", status_id);
		mapParameters3.put("row_count", rw);
		mapParameters3.put("cur_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters3.put("date_name", date_name);	
		mapParameters3.put("date_from", dteRefDateFr.getDate());
		mapParameters3.put("date_to", dteRefDateTo.getDate());	
		mapParameters3.put("inc_cash", inc_cash);	
		mapParameters3.put("pay_type", pay_type);
		mapParameters3.put("remarks", remarks);
		FncReport.generateReport("/Reports/rptCV_Docs_Processing_Monitoring_Rpt.jasper", "Document Processing - CV", mapParameters3);
	}
}