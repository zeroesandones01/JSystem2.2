package Accounting.Collections;

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
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.table.NumberEditorExt;

import tablemodel.modelByrPmtPosting_CDR;
import tablemodel.modelByrPmtPosting_CDR_LateOR;
import tablemodel.modelByrPmtPosting_CRB;
import tablemodel.modelByrPmtPosting_ledger;
import tablemodel.modelByrPmtPosting_schedule;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class BuyersPaymentPosting extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Buyers Payment Posting";
	static Dimension SIZE = new Dimension(1200, 625);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pblCheckTable;
	private JPanel pnlA;
	private JPanel pnlB;	
	private JPanel pnlTable_a;
	private JPanel pnlTable_b;
	private JPanel pnlTable_acct_info1;
	private JPanel pnlTable_acct_info2;
	private JPanel pnlTable_acct_info1a;
	private JPanel pnlTable_acct_info1b;	
	private JPanel pnlTable_acct_info2a;	
	private JPanel pnlTable_acct_info2b;	
	private JPanel pnlTable_pmtsch1;
	private JPanel pnlTable_pmtsch1a;
	private JPanel pnlTable_pmtsch1b;	
	private JPanel pnlTable_pmtsch2;
	private JPanel pnlTable_pmtsch2a;
	private JPanel pnlTable_pmtsch2b;
	private JPanel pnlTable_acct_info;
	private JPanel pnlSouth_2;
	private JPanel pnlTable;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblOfficeBranch;
	private JLabel lblPhase;

	private JLabel lblBuyerType;
	private JLabel lblAcctStatus;
	private JLabel lblDocStatus;
	private JLabel lblORdate;
	private JLabel lblNSP;
	private JLabel lblGSP;
	private JLabel lblDiscount;
	private JLabel lblVAT;
	private JLabel lblClientName;
	private JLabel lblPBL;
	private JLabel lblHouseModel;
	private JLabel lblUnitID;
	private JLabel lblCashierDate;
	private JLabel lblDPterm;
	private JLabel lblMAterm;
	private JLabel lblDPamt;
	private JLabel lblMAamt;

	private _JTagLabel tagCompany;
	private _JTagLabel tagBranch;
	private _JTagLabel tagProject;
	private _JTagLabel tagPhase;
	private _JTagLabel tagClientName;
	private _JTagLabel tagUnitID;
	private _JTagLabel tagPBL;
	private _JTagLabel tagHouseModel;
	private _JTagLabel tagBuyerType;
	private _JTagLabel tagAcctStatus;
	private _JTagLabel tagDocStatus;
	private _JTagLabel tagORdate;
	private _JTagLabel tagNSP;
	private _JTagLabel tagGSP;
	private _JTagLabel tagDiscount;
	private _JTagLabel tagVAT;
	private _JTagLabel tagDPterm;
	private _JTagLabel tagMAterm;
	private _JTagLabel tagDPamt;
	private _JTagLabel tagMAamt;

	private _JTabbedPane tabCenter;	

	private _JScrollPaneMain scrollSample2Main;
	private _JScrollPaneMain scrollSample5Main;	
	private _JScrollPaneMain scrollSample1Main;
	private _JScrollPaneMain scrollSample3Main;
	private _JScrollPaneMain scrollSample4Main;

	private _JScrollPaneTotal scrollSample1Total;
	private _JScrollPaneTotal scrollSample2Total;	
	private _JScrollPaneTotal scrollSample3Total;
	private _JScrollPaneTotal scrollSample4Total;
	private _JScrollPaneTotal scrollSample5Total;

	private modelByrPmtPosting_schedule modelSample4Main;
	private modelByrPmtPosting_schedule modelSample4Total;
	private modelByrPmtPosting_ledger modelSample3Main;
	private modelByrPmtPosting_ledger modelSample3Total;
	private modelByrPmtPosting_CDR modelSample2Main;
	private modelByrPmtPosting_CDR modelSample1Main;
	private modelByrPmtPosting_CDR modelSample2Total;
	private modelByrPmtPosting_CDR modelSample1Total;		
	private modelByrPmtPosting_CRB modelSample5Main;
	private modelByrPmtPosting_CRB modelSample5Total;

	private _JTableMain tblSample1Main;
	private _JTableMain tblSample2Main;	
	private _JTableMain tblSample3Main;
	private _JTableMain tblSample4Main;
	private _JTableMain tblSample5Main;

	private _JTableTotal tblSample1Total;	
	private _JTableTotal tblSample2Total;
	private _JTableTotal tblSample3Total;
	private _JTableTotal tblSample4Total;
	private _JTableTotal tblSample5Total;

	private _JLookup lookupCompany;
	private _JLookup lookupOfficeBranch;
	private _JLookup lookupProject;
	private _JLookup lookupPhase;

	private JList rowHeaderSample1Main;
	private JList rowHeaderSample2Main;
	private JList rowHeaderSample3Main;	
	private JList rowHeaderSample4Main;	
	private JList rowHeaderSample5Main;

	private _JDateChooser dteCashierDate;

	//private JButton btnDisplayCDR;
	private JButton btnPostAll;
	private JButton btnRefreshCDR;
	private JButton btnCancel;		
	private JButton btnEdit;
	private JButton btnPostEntries;
	private JButton btnRefreshCRB;
	private JButton btnSave;	

	private JPopupMenu menu;
	private JMenuItem mnidelete;
	private JMenuItem mniaddrow;
	private JMenuItem mnicopy;
	private JMenuItem mnipaste;

	private JCheckBox chkActive;

	private Boolean blnPostAll;
	private Boolean blnRefreshCDR;
	private Boolean blnCancel;		
	private Boolean blnEdit;
	private Boolean blnPostEntries;
	private Boolean blnRefreshCRB;
	private Boolean blnSave;
	private Boolean blnCRBentries;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
	private NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 
	private Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);

	//cdr details
	String entity_id= "";
	String co_id 	= "";
	String proj_id 	= "";
	String branch_id= "";
	String rec_id 	= "";
	String crb_no 	= "";
	String pbl_id 	= "";
	Integer seq_no 	= 0;

	//crb details
	String acct_id 	= "";
	String acct_name= "";
	Double debit	= 0.00;
	Double credit	= 0.00;
	String remarks	= "";
	String rb_fiscal_yr = "";
	String rb_month	= "";
	String ar_no	= "";
	String proj		= "";
	String phase	= "";
	String doc_id 	= "";
	String date1 	= "trans_date";
	String date2 	= "tran_date";
	String date3 	= "trans_date";

	private String company = "";
	private String company_logo;
	private JButton btnCRBentries;
	private JSplitPane splitPanel;

	java.util.Date cashier_Date;
	java.util.Date cashier_Date_2;
	private JComboBox cmbFilterby;

	/*	Modified by Mann2x; Date Modified: July 31, 2017; Buyer's payment posting remodeling by Mann2x;	*/
	private _JScrollPaneMain scrollOR;
	private _JScrollPaneTotal scrollORTotal;
	private modelByrPmtPosting_CDR_LateOR modelORMain;
	private modelByrPmtPosting_CDR_LateOR modelORTotal;
	private _JTableMain tblORMain;
	private _JTableTotal tblORTotal;
	private JList rowORMain;

	private JTextArea txtParticulars; 

	public BuyersPaymentPosting() {
		super(title, true, true, true, true);
		initGUI();
	}

	public BuyersPaymentPosting(String title) {
		super(title);

	}

	public BuyersPaymentPosting(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}


	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Row      ");
			mniaddrow = new JMenuItem("Add Row");
			mnicopy = new JMenuItem("Copy Row");
			mnipaste = new JMenuItem("Paste Row");
			menu.add(mnidelete);
			menu.add(mniaddrow);	
			JSeparator sp = new JSeparator();
			menu.add(sp);	
			menu.add(mnicopy);
			menu.add(mnipaste);
			mnidelete.setEnabled(false);
			mniaddrow.setEnabled(false);
			mnicopy.setEnabled(false);
			mnipaste.setEnabled(false);

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
					copyRow();
				}
			});
			mnipaste.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					pasteRow();
				}
			});
		}

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));
		{

		}
		splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		pnlMain.add(splitPanel);
		splitPanel.setOneTouchExpandable(true);
		splitPanel.setResizeWeight(.7d);
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			splitPanel.add(pnlNorth, JSplitPane.LEFT);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			//pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(606, 574));	
			{
				pnlA = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlA, BorderLayout.NORTH);			
				pnlA.setPreferredSize(new java.awt.Dimension(568, 195));
				pnlA.setBorder(JTBorderFactory.createTitleBorder("Search Option", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel panDivA = new JXPanel(new GridLayout(5, 1, 5, 5));
					pnlA.add(panDivA, BorderLayout.CENTER);
					{
						{
							JXPanel panCom = new JXPanel(new BorderLayout(5, 5));
							panDivA.add(panCom, BorderLayout.CENTER);
							{
								JXPanel panComLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
								panCom.add(panComLabel, BorderLayout.LINE_START);
								panComLabel.setPreferredSize(new Dimension(200, 0));
								{
									{
										lblCompany = new JLabel("Company", JLabel.TRAILING);
										panComLabel.add(lblCompany, BorderLayout.CENTER);
										lblCompany.setHorizontalAlignment(JTextField.LEADING);
									}
									{
										lookupCompany = new _JLookup(null, "Company",0,2);
										panComLabel.add(lookupCompany);
										lookupCompany.setLookupSQL(SQL_COMPANY());
										lookupCompany.setReturnColumn(0);
										lookupCompany.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){

													String name = (String) data[1];		

													company		= (String) data[1];	
													company_logo = (String) data[3];
													co_id = (String) data[0];
													tagCompany.setTag(name);							

													lblProject.setEnabled(true);
													lookupProject.setEnabled(true);	
													tagProject.setEnabled(true);	

													lblOfficeBranch.setEnabled(true);	
													lookupOfficeBranch.setEnabled(true);	
													tagBranch.setEnabled(true);	

													lblCashierDate.setEnabled(true);	
													dteCashierDate.setEnabled(true);

													btnCancel.setEnabled(true);

													KEYBOARD_MANAGER.focusNextComponent();	
													lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
												}
											}
										});
									}
									lookupCompany.setValue("02");
								}
								{
									tagCompany = new _JTagLabel("[ ]");
									panCom.add(tagCompany, BorderLayout.CENTER);
									tagCompany.setEnabled(true);
								}
							}		
						}
						{
							JXPanel panProject = new JXPanel(new BorderLayout(5, 5));
							panDivA.add(panProject, BorderLayout.CENTER);
							{
								JXPanel panProjectLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
								panProject.add(panProjectLabel, BorderLayout.LINE_START);
								panProjectLabel.setPreferredSize(new Dimension(200, 0));
								{
									{
										lblProject = new JLabel("Project", JLabel.TRAILING);
										panProjectLabel.add(lblProject);
										lblProject.setHorizontalAlignment(JTextField.LEADING);
									}				
									{
										lookupProject = new _JLookup(null, "Project", 0, 2);
										panProjectLabel.add(lookupProject);
										lookupProject.setBounds(20, 27, 20, 25);
										lookupProject.setEnabled(false);	
										lookupProject.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													proj_id = (String) data[0];
													String name = (String) data[1];						
													tagProject.setTag(name);

													lookupPhase.setLookupSQL(sqlPhase(lookupProject.getValue()));

													pgUpdate dbDelete = new pgUpdate(); 
													dbDelete.executeUpdate("delete from tmpcrb where user_id = '"+UserInfo.EmployeeCode+"'", false);
													dbDelete.commit();
												}
											}
										});
									}
								}
								{
									tagProject = new _JTagLabel("[ ]");
									panProject.add(tagProject, BorderLayout.CENTER);
									tagProject.setEnabled(true);	
								}
							}	
						}
						{
							JXPanel panBranch = new JXPanel(new BorderLayout(5, 5));
							panDivA.add(panBranch, BorderLayout.CENTER);
							{
								JXPanel panBranchLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
								panBranch.add(panBranchLabel, BorderLayout.LINE_START);
								panBranchLabel.setPreferredSize(new Dimension(200, 0));
								{
									{
										lblOfficeBranch = new JLabel("Branch", JLabel.TRAILING);
										panBranchLabel.add(lblOfficeBranch, BorderLayout.CENTER);
										lblOfficeBranch.setHorizontalAlignment(JTextField.LEADING);
									}	
									{
										lookupOfficeBranch = new _JLookup(null, "Phase", 0, 2);
										panBranchLabel.add(lookupOfficeBranch);
										lookupOfficeBranch.setEnabled(false);	
										lookupOfficeBranch.setLookupSQL(SQL_OFFICE_BRANCH());
										lookupOfficeBranch.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													branch_id = (String) data[0];
													String name = (String) data[1];						
													tagBranch.setTag(name);
													/*	displayCDR();	*/

													pgUpdate dbDelete = new pgUpdate(); 
													dbDelete.executeUpdate("delete from tmpcrb where user_id = '"+UserInfo.EmployeeCode+"'", false);
													dbDelete.commit();

													setParticular(lookupCompany.getValue(), lookupOfficeBranch.getValue(), dteCashierDate.getDate().toString());
												}
											}
										});
										lookupOfficeBranch.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													lookupOfficeBranch.setValue("");
													tagBranch.setText("[ ]");
													/*	displayCDR();	*/
													pgUpdate dbDelete = new pgUpdate(); 
													dbDelete.executeUpdate("delete from tmpcrb where user_id = '"+UserInfo.EmployeeCode+"'", false);
													dbDelete.commit();
												}
											}

											public void keyReleased(KeyEvent e) {

											}

											public void keyPressed(KeyEvent arg0) {

											}
										});
										lookupOfficeBranch.setValue("");
									}
								}
								{
									tagBranch = new _JTagLabel("[ ]");
									panBranch.add(tagBranch, BorderLayout.CENTER);
									tagBranch.setHorizontalAlignment(JTextField.LEADING);
								}	
							}
						}
						{
							JXPanel panPhase = new JXPanel(new BorderLayout(5, 5));
							panDivA.add(panPhase, BorderLayout.CENTER);
							{
								{
									JXPanel panPhaseLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
									panPhase.add(panPhaseLabel, BorderLayout.LINE_START);
									panPhaseLabel.setPreferredSize(new Dimension(200, 0));
									{
										{
											lblPhase = new JLabel("Phase", JLabel.TRAILING);
											panPhaseLabel.add(lblPhase, BorderLayout.CENTER);
											lblPhase.setHorizontalAlignment(JTextField.LEADING);
										}
										{
											lookupPhase = new _JLookup(null, "Phase", 0, 2);
											panPhaseLabel.add(lookupPhase);
											lookupPhase.setLookupSQL(sqlPhase(lookupProject.getValue()));
											lookupPhase.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){
														branch_id = (String) data[0];
														String name = (String) data[1];						
														tagPhase.setTag(name);

														pgUpdate dbDelete = new pgUpdate(); 
														dbDelete.executeUpdate("delete from tmpcrb where user_id = '"+UserInfo.EmployeeCode+"'", false);
														dbDelete.commit();
													}
												}
											});
											lookupPhase.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
														lookupPhase.setValue("");
														tagPhase.setText("[ ]");
														/*	displayCDR();	*/

														pgUpdate dbDelete = new pgUpdate(); 
														dbDelete.executeUpdate("delete from tmpcrb where user_id = '"+UserInfo.EmployeeCode+"'", false);
														dbDelete.commit();
													}
												}

												public void keyReleased(KeyEvent e) {

												}

												public void keyPressed(KeyEvent arg0) {

												}
											});	
										}
									}
								}
								{
									tagPhase = new _JTagLabel("[ ]");
									panPhase.add(tagPhase, BorderLayout.CENTER);
									tagPhase.setHorizontalAlignment(JTextField.LEADING);
								}	
							}
						}
						{
							JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
							panDivA.add(panDate, BorderLayout.CENTER);
							{
								{
									JXPanel panDateLine = new JXPanel(new BorderLayout(5, 5));
									panDate.add(panDateLine, BorderLayout.LINE_START);
									panDateLine.setPreferredSize(new Dimension(97, 0));
									{
										lblCashierDate = new JLabel("Date");
										panDateLine.add(lblCashierDate, BorderLayout.CENTER);
										lblCashierDate.setHorizontalAlignment(JTextField.LEADING);
									}
								}
								{
									JXPanel panDateCenter = new JXPanel(new GridLayout(1, 2, 5, 5));
									panDate.add(panDateCenter, BorderLayout.CENTER);
									{
										{
											dteCashierDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
											panDateCenter.add(dteCashierDate, BorderLayout.CENTER);
											dteCashierDate.setDate(null);
											dteCashierDate.setEnabled(false);
											dteCashierDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											dteCashierDate.addDateListener(new DateListener() {
												public void datePerformed(DateEvent event) {

													insertParticular(); 

													pgUpdate dbDelete = new pgUpdate(); 
													dbDelete.executeUpdate("delete from tmpcrb where user_id = '"+UserInfo.EmployeeCode+"'", false);
													dbDelete.commit();

													/*	displayCDR();	*/
													display_CDR displayCDR = new display_CDR();
													Thread thread = new Thread(displayCDR);
													thread.start();

													setParticular(lookupCompany.getValue(), lookupOfficeBranch.getValue(), dteCashierDate.getDate().toString());
													updateCashReturnParticulars(lookupCompany.getValue(), lookupProject.getValue(), lookupOfficeBranch.getValue(), lookupPhase.getValue(), dteCashierDate.getDate());
												}
											});
										}
										{
											String status2[] = {
													"Transaction Date", 
													"Actual Date", 
													"OR Date"
											};

											cmbFilterby = new JComboBox(status2);
											panDateCenter.add(cmbFilterby, BorderLayout.CENTER);
											cmbFilterby.setSelectedItem(null);
											cmbFilterby.setEnabled(true);
											cmbFilterby.setSelectedIndex(0);
											cmbFilterby.addItemListener(new ItemListener() {
												public void itemStateChanged(ItemEvent evt) 
												{
													if (cmbFilterby.getSelectedIndex()==0)
													{
														date1 = "trans_date"; 
														date2 = "tran_date";
														date3 = "trans_date";

														/*	displayCDR();	*/
														display_CDR displayCDR = new display_CDR();
														Thread thread = new Thread(displayCDR);
														thread.start();
													} 

													if (cmbFilterby.getSelectedIndex()==1) {							
														date1 = "actual_date"; 
														date2 = "actual_date";
														date3 = "booking_date";

														/*	displayCDR();	*/
														display_CDR displayCDR = new display_CDR();
														Thread thread = new Thread(displayCDR);
														thread.start();
													}

													if (cmbFilterby.getSelectedIndex()==1) {							
														/*	displayCDR();	*/
														display_CDR displayCDR = new display_CDR();
														Thread thread = new Thread(displayCDR);
														thread.start();
													}
												}
											});
										}
									}
								}
								{
									JXPanel panDateEnd = new JXPanel(new BorderLayout(5, 5));
									panDate.add(panDateEnd, BorderLayout.LINE_END);
									panDateEnd.setPreferredSize(new Dimension(125, 0));
									{
										chkActive = new JCheckBox("Late OR Mode");
										//panDateEnd.add(chkActive);
										chkActive.setHorizontalAlignment(JTextField.CENTER);
										chkActive.setSelected(false);
										chkActive.setEnabled(true);
										chkActive.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												enableAcctInfo(true);
												enableButtons(true);

												if (chkActive.isSelected()) {
													displayCDR_ORMode(modelORMain, rowORMain, modelORTotal);							
												} else {
													displayCDR(modelSample2Main, rowHeaderSample2Main, modelSample2Total);
												}
											}
										});
									}
								}
							}
						}
					}


					/*
					{
						pnlA1 = new JPanel(new BorderLayout(5, 5));
						pnlA.add(pnlA1, BorderLayout.WEST);	
						pnlA1.setPreferredSize(new java.awt.Dimension(569, 126));
						pnlA1.setBorder(lineBorder);
						{
							pnlA1_a = new JPanel(new GridLayout(3, 2, 5, 5));
							pnlA1.add(pnlA1_a, BorderLayout.WEST);	
							pnlA1_a.setPreferredSize(new java.awt.Dimension(168, 30));
							pnlA1_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
							{
								lblCompany = new JLabel("COMPANY", JLabel.TRAILING);
								pnlA1_a.add(lblCompany);
								lblCompany.setBounds(8, 11, 62, 12);
								lblCompany.setPreferredSize(new java.awt.Dimension(101, 16));
								lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							}
							{
								lookupCompany = new _JLookup(null, "Company",0,2);
								pnlA1_a.add(lookupCompany);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setReturnColumn(0);
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											String name = (String) data[1];		

											company		= (String) data[1];	
											company_logo = (String) data[3];
											co_id = (String) data[0];
											tagCompany.setTag(name);							

											lblProject.setEnabled(true);
											lookupProject.setEnabled(true);	
											tagProject.setEnabled(true);	

											lblOfficeBranch.setEnabled(true);	
											lookupOfficeBranch.setEnabled(tru{
								lblOfficeBranch = new JLabel("Branch", JLabel.TRAILING);
								pnlA1_a.add(lblOfficeBranch);
								lblOfficeBranch.setEnabled(false);	
								lblOfficeBranch.setPreferredSize(new java.awt.Dimension(74, 22));
							}	
							{
								lookupOfficeBranch = new _JLookup(null, "Phase", 0, 2);
								pnlA1_a.add(lookupOfficeBranch);
								lookupOfficeBranch.setPreferredSize(new java.awt.Dimension(445, 20));
								lookupOfficeBranch.setEnabled(false);	
								lookupOfficeBranch.setLookupSQL(SQL_OFFICE_BRANCH());
								lookupOfficeBranch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											branch_id = (String) data[0];
											String name = (String) data[1];						
											tagBranch.setTag(name);
											displayCDR(); 
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
								lookupOfficeBranch.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											lookupOfficeBranch.setValue("");
											tagBranch.setText("[ ]");
											displayCDR();
										}
									}

									public void keyReleased(KeyEvent e) {

									}

									public void keyPressed(KeyEvent arg0) {

									}
								});
							}e);	
											tagBranch.setEnabled(true);	

											lblCashierDate.setEnabled(true);	
											dteCashierDate.setEnabled(true);

											//btnDisplayCDR.setEnabled(true);
											btnCancel.setEnabled(true);

											KEYBOARD_MANAGER.focusNextComponent();	
											lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
										}
									}
								});
							}
							{
								lblProject = new JLabel("Project", JLabel.TRAILING);
								pnlA1_a.add(lblProject);
								lblProject.setEnabled(false);	
							}				
							{
								lookupProject = new _JLookup(null, "Project", 0, 2);
								pnlA1_a.add(lookupProject);
								lookupProject.setBounds(20, 27, 20, 25);
								lookupProject.setEnabled(false);	
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											proj_id = (String) data[0];
											String name = (String) data[1];						
											tagProject.setTag(name);

											displayCDR();	
										}
									}
								});
							}
							{
								lblOfficeBranch = new JLabel("Branch", JLabel.TRAILING);
								pnlA1_a.add(lblOfficeBranch);
								lblOfficeBranch.setEnabled(false);	
								lblOfficeBranch.setPreferredSize(new java.awt.Dimension(74, 22));
							}	
							{
								lookupOfficeBranch = new _JLookup(null, "Phase", 0, 2);
								pnlA1_a.add(lookupOfficeBranch);
								lookupOfficeBranch.setPreferredSize(new java.awt.Dimension(445, 20));
								lookupOfficeBranch.setEnabled(false);	
								lookupOfficeBranch.setLookupSQL(SQL_OFFICE_BRANCH());
								lookupOfficeBranch.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											branch_id = (String) data[0];
											String name = (String) data[1];						
											tagBranch.setTag(name);
											displayCDR(); 
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
								lookupOfficeBranch.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											lookupOfficeBranch.setValue("");
											tagBranch.setText("[ ]");
											displayCDR();
										}
									}

									public void keyReleased(KeyEvent e) {

									}

									public void keyPressed(KeyEvent arg0) {

									}
								});
							}	
						}
					}
					{
						pnlA1_b = new JPanel(new GridLayout(3, 1, 5, 5));
						pnlA1.add(pnlA1_b, BorderLayout.CENTER);	
						pnlA1_b.setPreferredSize(new java.awt.Dimension(417, 125));	
						pnlA1_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						{
							tagCompany = new _JTagLabel("[ ]");
							pnlA1_b.add(tagCompany);
							tagCompany.setBounds(209, 27, 700, 22);
							tagCompany.setEnabled(true);	
							tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagProject = new _JTagLabel("[ ]");
							pnlA1_b.add(tagProject);
							tagProject.setBounds(209, 27, 700, 22);
							tagProject.setEnabled(false);	
						}
						{
							tagBranch = new _JTagLabel("[ ]");
							pnlA1_b.add(tagBranch);
							tagBranch.setBounds(209, 27, 700, 22);
							tagBranch.setEnabled(false);	
							tagBranch.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
					}
					{

					}
					{
					pnlA2 = new JPanel(new BorderLayout(5, 5));
					pnlA.add(pnlA2, BorderLayout.SOUTH);	
					pnlA2.setPreferredSize(new java.awt.Dimension(588, 28));

					pnlA2_a = new JPanel(new BorderLayout(5, 5));
					pnlA2.add(pnlA2_a, BorderLayout.WEST);	
					pnlA2_a.setPreferredSize(new java.awt.Dimension(296, 66));	

					pnlA2_aa = new JPanel(new GridLayout(1, 1, 5, 0));
					pnlA2_a.add(pnlA2_aa, BorderLayout.WEST);	
					pnlA2_aa.setPreferredSize(new java.awt.Dimension(80, 35));
					pnlA2_aa.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
					{
						lblCashierDate = new JLabel("Date", JLabel.TRAILING);
						pnlA2_aa.add(lblCashierDate);
						lblCashierDate.setEnabled(false);	
						lblCashierDate.setPreferredSize(new java.awt.Dimension(67, 25));
					}

					pnlA2_ab = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlA2_a.add(pnlA2_ab, BorderLayout.CENTER);	
					pnlA2_ab.setPreferredSize(new java.awt.Dimension(211, 66));
					pnlA2_ab.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					{
						dteCashierDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlA2_ab.add(dteCashierDate);
						dteCashierDate.setBounds(485, 7, 125, 21);
						dteCashierDate.setDate(null);
						dteCashierDate.setEnabled(false);
						dteCashierDate.setPreferredSize(new java.awt.Dimension(207, 25));
						dteCashierDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						dteCashierDate.addDateListener(new DateListener() {
							public void datePerformed(DateEvent event) {
								displayCDR();
							}
						});
					}				

					pnlA2_b = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlA2.add(pnlA2_b, BorderLayout.CENTER);	
					pnlA2_b.setPreferredSize(new java.awt.Dimension(296, 66));	
					{
//						btnDisplayCDR = new JButton("Display CDR");
//						pnlA2_b.add(btnDisplayCDR);
//						btnDisplayCDR.setActionCommand("DisplayCDR");	
//						btnDisplayCDR.setVisible(false);
//						btnDisplayCDR.addActionListener(this);

						String status2[] = {"Transaction Date","Actual Date"};					
						cmbFilterby = new JComboBox(status2);
						pnlA2_b.add(cmbFilterby);
						cmbFilterby.setSelectedItem(null);
						cmbFilterby.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbFilterby.setBounds(537, 15, 180, 21);	
						cmbFilterby.setEnabled(true);
						cmbFilterby.setSelectedIndex(0);
						cmbFilterby.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{
								if (cmbFilterby.getSelectedIndex()==0)
								{
									date1 = "trans_date"; 
									date2 = "tran_date";
									date3 = "trans_date";
									displayCDR();
								} 
								else 
								{							
									date1 = "actual_date"; 
									date2 = "actual_date";
									date3 = "booking_date";
									displayCDR();
								}
							}
						});
					}
					{
						--Added by Mann2x; Date Added: November 8, 2016; Switching between the actual collection and the Late OR list;
						JXPanel pnlEndCheck = new JXPanel(new GridLayout(1, 1, 5, 0));
						pnlA2_b.add(pnlEndCheck, BorderLayout.LINE_END);
						pnlEndCheck.setPreferredSize(new Dimension(200, 30));
						{
							chkActive = new JCheckBox("Late OR Mode");
							pnlEndCheck.add(chkActive);
							chkActive.setHorizontalAlignment(JTextField.CENTER);
							chkActive.setSelected(false);
							chkActive.setEnabled(true);
							chkActive.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									enableAcctInfo(true);
									enableButtons(true);

									if (chkActive.isSelected()) {
										displayCDR_ORMode(modelSample2Main, rowHeaderSample2Main, modelSample2Total);								
									} else {
										displayCDR(modelSample2Main, rowHeaderSample2Main, modelSample2Total);
									}
								}
							});
						}
					}

				}
					 */
				}
			}
			{
				JXPanel panDivB = new JXPanel(new BorderLayout(5, 5));
				pnlNorth.add(panDivB, BorderLayout.CENTER);
				panDivB.setPreferredSize(new java.awt.Dimension(1119, 185));
				{
					JSplitPane splitRegOr = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
					panDivB.add(splitRegOr);
					splitRegOr.setOneTouchExpandable(true);
					splitRegOr.setResizeWeight(.7d);
					{
						{
							pnlB = new JPanel(new BorderLayout(0, 0));
							splitRegOr.add(pnlB);				
							pnlB.setBorder(JTBorderFactory.createTitleBorder("Daily Collection Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									scrollSample2Main = new _JScrollPaneMain();
									pnlB.add(scrollSample2Main, BorderLayout.CENTER);
									{
										modelSample2Main = new modelByrPmtPosting_CDR();
										tblSample2Main = new _JTableMain(modelSample2Main);
										scrollSample2Main.setViewportView(tblSample2Main);
										tblSample2Main.addMouseListener(this);
										tblSample2Main.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if(tblSample2Main.rowAtPoint(e.getPoint()) == -1) {
													tblSample2Total.clearSelection();
												} else {
													tblSample2Main.setCellSelectionEnabled(true);
												}
												tblORMain.clearSelection();
												displayClientInfo(modelSample2Main, tblSample2Main.convertRowIndexToModel(tblSample2Main.getSelectedRow()));								
											}
											public void mouseReleased(MouseEvent e) {
												if (tblSample2Main.rowAtPoint(e.getPoint()) == -1) {
													tblSample2Total.clearSelection();
												} else {
													tblSample2Main.setCellSelectionEnabled(true);
												}
												tblORMain.clearSelection();
												displayClientInfo(modelSample2Main, tblSample2Main.convertRowIndexToModel(tblSample2Main.getSelectedRow()));	
											}
										});

										tblSample2Main.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
											public void propertyChange(PropertyChangeEvent arg0) {
												tblORMain.clearSelection();
												displayClientInfo(modelSample2Main, tblSample2Main.convertRowIndexToModel(tblSample2Main.getSelectedRow()));
											}
										});							
									}
									{
										rowHeaderSample2Main = tblSample2Main.getRowHeader22();
										scrollSample2Main.setRowHeaderView(rowHeaderSample2Main);
										scrollSample2Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
								{
									scrollSample2Total = new _JScrollPaneTotal(scrollSample2Main);
									pnlB.add(scrollSample2Total, BorderLayout.SOUTH);
									{
										modelSample2Total = new modelByrPmtPosting_CDR();
										modelSample2Total.addRow(new Object[] { "Total", null,  null, null, null, null, null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null });	

										tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
										scrollSample2Total.setViewportView(tblSample2Total);
										tblSample2Total.setFont(dialog11Bold);
										((_JTableTotal) tblSample2Total).setTotalLabel(0);
									}
								}
							}						
						}
						{
							JXPanel pnlC = new JXPanel(new BorderLayout(0, 0));
							splitRegOr.add(pnlC);
							pnlC.setPreferredSize(new java.awt.Dimension(1119, 150));
							pnlC.setBorder(JTBorderFactory.createTitleBorder("Issued OR to PDC Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									scrollOR = new _JScrollPaneMain();
									pnlC.add(scrollOR, BorderLayout.CENTER);
									{
										modelORMain = new modelByrPmtPosting_CDR_LateOR();
										tblORMain = new _JTableMain(modelORMain);
										scrollOR.setViewportView(tblORMain);
										tblORMain.addMouseListener(this);
										tblORMain.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												System.out.println("");
												System.out.println("Clicked OR!");

												if(tblORMain.rowAtPoint(e.getPoint()) == -1) {
													tblORTotal.clearSelection();
												} else {
													tblORMain.setCellSelectionEnabled(true);
												}
												tblSample2Main.clearSelection();
												displayClientInfoOR(modelORMain, tblORMain.getSelectedRow());								
											}
											public void mouseReleased(MouseEvent e) {
												System.out.println("");
												System.out.println("Clicked!");

												if (tblORMain.rowAtPoint(e.getPoint()) == -1) {
													tblORTotal.clearSelection();
												} else {
													tblORMain.setCellSelectionEnabled(true);
												}
												tblSample2Main.clearSelection();
												displayClientInfoOR(modelORMain, tblORMain.getSelectedRow());	
											}
										});

										tblORMain.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
											public void propertyChange(PropertyChangeEvent arg0) {
												tblSample2Main.clearSelection();
												displayClientInfoOR(modelORMain, tblORMain.getSelectedRow());
											}
										});							
									}
									{
										rowORMain = tblORMain.getRowHeader22();
										scrollOR.setRowHeaderView(rowORMain);
										scrollOR.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
								{
									scrollORTotal = new _JScrollPaneTotal(scrollOR);
									pnlC.add(scrollORTotal, BorderLayout.SOUTH);
									{
										modelORTotal = new modelByrPmtPosting_CDR_LateOR();
										modelORTotal.addRow(new Object[] { "Total", null,  null, null, null, null, null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null });	

										tblORTotal = new _JTableTotal(modelORTotal, tblORMain);
										scrollORTotal.setViewportView(tblORTotal);
										tblORTotal.setFont(dialog11Bold);
										((_JTableTotal) tblORTotal).setTotalLabel(0);
									}
								}
							}
						}
					}
				}
			}
			pnlSouth = new JPanel(new GridLayout(1, 4, 5, 5));
			pnlNorth.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setPreferredSize(new java.awt.Dimension(588, 30));
			{				
				btnPostAll = new JButton("Post All Payments");
				pnlSouth.add(btnPostAll);
				btnPostAll.setActionCommand("PostAll");
				btnPostAll.addActionListener(this);
				btnPostAll.setEnabled(false);				
			}
			{
				btnCRBentries = new JButton("CRB Entries");
				pnlSouth.add(btnCRBentries);
				btnCRBentries.setActionCommand("CRB_Entries");
				btnCRBentries.addActionListener(this);
				btnCRBentries.setEnabled(false);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlSouth.add(btnCancel);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(this);
				btnCancel.setEnabled(false);
			}
			{
				btnRefreshCDR= new JButton("Generate CDR");
				pnlSouth.add(btnRefreshCDR);
				btnRefreshCDR.setActionCommand("RefreshCDR");
				btnRefreshCDR.addActionListener(this);
				btnRefreshCDR.setVisible(true);
			}
		}	

		//----------------------PANEL ACCOUNT INFO.

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			//pnlMain.add(pnlTable, BorderLayout.EAST);	
			splitPanel.add(pnlTable, JSplitPane.RIGHT);
			pnlTable.setPreferredSize(new java.awt.Dimension(507, 574));		
			//pnlTable.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));

			pnlTable_a = new JPanel(new BorderLayout(5, 5));
			pnlTable.add(pnlTable_a, BorderLayout.NORTH);			
			pnlTable_a.setPreferredSize(new java.awt.Dimension(655, 267));				

			pnlTable_acct_info =  new JPanel(new BorderLayout(5, 5));
			pnlTable_a.add(pnlTable_acct_info, BorderLayout.NORTH);			
			pnlTable_acct_info.setPreferredSize(new java.awt.Dimension(655, 137));	
			pnlTable_acct_info.setBorder(JTBorderFactory.createTitleBorder("Account Info.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));	

			pnlTable_acct_info1 =  new JPanel(new BorderLayout(5, 5));
			pnlTable_acct_info.add(pnlTable_acct_info1, BorderLayout.WEST);			
			pnlTable_acct_info1.setPreferredSize(new java.awt.Dimension(333, 137));	

			pnlTable_acct_info1a = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_acct_info1.add(pnlTable_acct_info1a, BorderLayout.WEST);			
			pnlTable_acct_info1a.setPreferredSize(new java.awt.Dimension(86, 136));		

			{
				lblClientName = new JLabel("Client Name :", JLabel.TRAILING);
				pnlTable_acct_info1a.add(lblClientName);
				lblClientName.setEnabled(false);	
				lblClientName.setPreferredSize(new java.awt.Dimension(74, 22));
			}				
			{
				lblUnitID = new JLabel("Unit ID | Seq :", JLabel.TRAILING);
				pnlTable_acct_info1a.add(lblUnitID);
				lblUnitID.setEnabled(false);	
				lblUnitID.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblPBL = new JLabel("PBL :", JLabel.TRAILING);
				pnlTable_acct_info1a.add(lblPBL);
				lblPBL.setEnabled(false);	
				lblPBL.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblHouseModel = new JLabel("House Model :", JLabel.TRAILING);
				pnlTable_acct_info1a.add(lblHouseModel);
				lblHouseModel.setEnabled(false);	
				lblHouseModel.setPreferredSize(new java.awt.Dimension(74, 22));
			}						

			pnlTable_acct_info1b = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_acct_info1.add(pnlTable_acct_info1b, BorderLayout.CENTER);			
			pnlTable_acct_info1b.setPreferredSize(new java.awt.Dimension(235, 160));

			{
				tagClientName = new _JTagLabel("[ ]");
				pnlTable_acct_info1b.add(tagClientName);
				tagClientName.setBounds(209, 27, 700, 22);
				tagClientName.setEnabled(false);	
			}
			{
				tagUnitID = new _JTagLabel("[ ]");
				pnlTable_acct_info1b.add(tagUnitID);
				tagUnitID.setBounds(209, 27, 700, 22);
				tagUnitID.setEnabled(false);	
			}
			{
				tagPBL = new _JTagLabel("[ ]");
				pnlTable_acct_info1b.add(tagPBL);
				tagPBL.setBounds(209, 27, 700, 22);
				tagPBL.setEnabled(false);	
			}
			{
				tagHouseModel = new _JTagLabel("[ ]");
				pnlTable_acct_info1b.add(tagHouseModel);
				tagHouseModel.setBounds(209, 27, 700, 22);
				tagHouseModel.setEnabled(false);	
			}

			pnlTable_acct_info2 =  new JPanel(new BorderLayout(5, 5));
			pnlTable_acct_info.add(pnlTable_acct_info2, BorderLayout.CENTER);			
			pnlTable_acct_info2.setPreferredSize(new java.awt.Dimension(345, 160));	

			pnlTable_acct_info2a = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_acct_info2.add(pnlTable_acct_info2a, BorderLayout.WEST);			
			pnlTable_acct_info2a.setPreferredSize(new java.awt.Dimension(92, 137));

			{
				lblBuyerType = new JLabel("Buyer Type :", JLabel.TRAILING);
				pnlTable_acct_info2a.add(lblBuyerType);
				lblBuyerType.setEnabled(false);	
				lblBuyerType.setPreferredSize(new java.awt.Dimension(93, 30));
			}				
			{
				lblAcctStatus = new JLabel("Acct. Status :", JLabel.TRAILING);
				pnlTable_acct_info2a.add(lblAcctStatus);
				lblAcctStatus.setEnabled(false);	
				lblAcctStatus.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblDocStatus = new JLabel("Doc. Status :", JLabel.TRAILING);
				pnlTable_acct_info2a.add(lblDocStatus);
				lblDocStatus.setEnabled(false);	
				lblDocStatus.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblORdate = new JLabel("OR Date :", JLabel.TRAILING);
				pnlTable_acct_info2a.add(lblORdate);
				lblORdate.setEnabled(false);	
				lblORdate.setPreferredSize(new java.awt.Dimension(74, 22));
			}			

			pnlTable_acct_info2b = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_acct_info2.add(pnlTable_acct_info2b, BorderLayout.CENTER);			
			pnlTable_acct_info2b.setPreferredSize(new java.awt.Dimension(85, 160));

			{
				tagBuyerType = new _JTagLabel("[ ]");
				pnlTable_acct_info2b.add(tagBuyerType);
				tagBuyerType.setBounds(209, 27, 700, 22);
				tagBuyerType.setEnabled(false);	
			}
			{
				tagAcctStatus = new _JTagLabel("[ ]");
				pnlTable_acct_info2b.add(tagAcctStatus);
				tagAcctStatus.setBounds(209, 27, 700, 22);
				tagAcctStatus.setEnabled(false);	
			}
			{
				tagDocStatus = new _JTagLabel("[ ]");
				pnlTable_acct_info2b.add(tagDocStatus);
				tagDocStatus.setBounds(209, 27, 700, 22);
				tagDocStatus.setEnabled(false);	
			}
			{
				tagORdate = new _JTagLabel("[ ]");
				pnlTable_acct_info2b.add(tagORdate);
				tagORdate.setBounds(209, 27, 700, 22);
				tagORdate.setEnabled(false);	
			}

			//Payment Scheme Info.
			pnlTable_b = new JPanel(new BorderLayout(5, 5));
			pnlTable_a.add(pnlTable_b, BorderLayout.SOUTH);			
			pnlTable_b.setPreferredSize(new java.awt.Dimension(655, 127));	
			pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));

			pnlTable_pmtsch1 =  new JPanel(new BorderLayout(5, 5));
			pnlTable_b.add(pnlTable_pmtsch1, BorderLayout.WEST);			
			pnlTable_pmtsch1.setPreferredSize(new java.awt.Dimension(349, 127));	

			pnlTable_pmtsch1a = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_pmtsch1.add(pnlTable_pmtsch1a, BorderLayout.WEST);			
			pnlTable_pmtsch1a.setPreferredSize(new java.awt.Dimension(86, 127));		

			{
				lblGSP = new JLabel("GSP :", JLabel.TRAILING);
				pnlTable_pmtsch1a.add(lblGSP);
				lblGSP.setEnabled(false);	
				lblGSP.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblDiscount = new JLabel("Discount :", JLabel.TRAILING);
				pnlTable_pmtsch1a.add(lblDiscount);
				lblDiscount.setEnabled(false);	
				lblDiscount.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblVAT = new JLabel("VAT :", JLabel.TRAILING);
				pnlTable_pmtsch1a.add(lblVAT);
				lblVAT.setEnabled(false);	
				lblVAT.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblNSP = new JLabel("NSP :", JLabel.TRAILING);
				pnlTable_pmtsch1a.add(lblNSP);
				lblNSP.setEnabled(false);	
				lblNSP.setPreferredSize(new java.awt.Dimension(74, 22));
			}			

			pnlTable_pmtsch1b = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_pmtsch1.add(pnlTable_pmtsch1b, BorderLayout.CENTER);			
			pnlTable_pmtsch1b.setPreferredSize(new java.awt.Dimension(251, 127));		

			{
				tagGSP = new _JTagLabel("[ ]");
				pnlTable_pmtsch1b.add(tagGSP);
				tagGSP.setBounds(209, 27, 700, 22);
				tagGSP.setEnabled(false);	
			}
			{
				tagDiscount = new _JTagLabel("[ ]");
				pnlTable_pmtsch1b.add(tagDiscount);
				tagDiscount.setBounds(209, 27, 700, 22);
				tagDiscount.setEnabled(false);	
			}
			{
				tagVAT = new _JTagLabel("[ ]");
				pnlTable_pmtsch1b.add(tagVAT);
				tagVAT.setBounds(209, 27, 700, 22);
				tagVAT.setEnabled(false);	
			}
			{
				tagNSP = new _JTagLabel("[ ]");
				pnlTable_pmtsch1b.add(tagNSP);
				tagNSP.setBounds(209, 27, 700, 22);
				tagNSP.setEnabled(false);	
			}

			pnlTable_pmtsch2 =  new JPanel(new BorderLayout(5, 5));
			pnlTable_b.add(pnlTable_pmtsch2, BorderLayout.CENTER);			
			pnlTable_pmtsch2.setPreferredSize(new java.awt.Dimension(345, 160));	

			pnlTable_pmtsch2a = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_pmtsch2.add(pnlTable_pmtsch2a, BorderLayout.WEST);			
			pnlTable_pmtsch2a.setPreferredSize(new java.awt.Dimension(76, 127));

			{
				lblDPterm = new JLabel("DP Term :", JLabel.TRAILING);
				pnlTable_pmtsch2a.add(lblDPterm);
				lblDPterm.setEnabled(false);	
				lblDPterm.setPreferredSize(new java.awt.Dimension(74, 22));
			}				
			{
				lblMAterm = new JLabel("MA Term :", JLabel.TRAILING);
				pnlTable_pmtsch2a.add(lblMAterm);
				lblMAterm.setEnabled(false);	
				lblMAterm.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblDPamt = new JLabel("DP Amount :", JLabel.TRAILING);
				pnlTable_pmtsch2a.add(lblDPamt);
				lblDPamt.setEnabled(false);	
				lblDPamt.setPreferredSize(new java.awt.Dimension(74, 22));
			}	
			{
				lblMAamt = new JLabel("MA Amount :", JLabel.TRAILING);
				pnlTable_pmtsch2a.add(lblMAamt);
				lblMAamt.setEnabled(false);	
				lblMAamt.setPreferredSize(new java.awt.Dimension(74, 22));
			}			

			pnlTable_pmtsch2b = new JPanel(new GridLayout(4, 1, 5, 5));
			pnlTable_pmtsch2.add(pnlTable_pmtsch2b, BorderLayout.CENTER);			
			pnlTable_pmtsch2b.setPreferredSize(new java.awt.Dimension(85, 160));

			{
				tagDPterm = new _JTagLabel("[ ]");
				pnlTable_pmtsch2b.add(tagDPterm);
				tagDPterm.setBounds(209, 27, 700, 22);
				tagDPterm.setEnabled(false);	
			}
			{
				tagMAterm = new _JTagLabel("[ ]");
				pnlTable_pmtsch2b.add(tagMAterm);
				tagMAterm.setBounds(209, 27, 700, 22);
				tagMAterm.setEnabled(false);	
			}
			{
				tagDPamt = new _JTagLabel("[ ]");
				pnlTable_pmtsch2b.add(tagDPamt);
				tagDPamt.setBounds(209, 27, 700, 22);
				tagDPamt.setEnabled(false);	
			}
			{
				tagMAamt = new _JTagLabel("[ ]");
				pnlTable_pmtsch2b.add(tagMAamt);
				tagMAamt.setBounds(209, 27, 700, 22);
				tagMAamt.setEnabled(false);	
			}			



			pblCheckTable = new JPanel();
			pnlTable.add(pblCheckTable, BorderLayout.CENTER);
			pblCheckTable.setLayout(new BorderLayout(5, 5));
			pblCheckTable.setPreferredSize(new java.awt.Dimension(1186, 404));
			pblCheckTable.setBorder(JTBorderFactory.createTitleBorder("Buyer's Payment Entries / Ledger / Schedule", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));

			tabCenter = new _JTabbedPane();
			pblCheckTable.add(tabCenter, BorderLayout.CENTER);			
			tabCenter.setPreferredSize(new java.awt.Dimension(636, 285));
			{
				//Check Deposit
				{
					JPanel pnlSample2 = new JPanel(new BorderLayout());
					tabCenter.addTab("CRB Entries", null, pnlSample2, null);
					pnlSample2.setPreferredSize(new java.awt.Dimension(631, 354));
					{
						scrollSample5Main = new _JScrollPaneMain();
						pnlSample2.add(scrollSample5Main, BorderLayout.CENTER);
						{
							modelSample5Main = new modelByrPmtPosting_CRB();

							tblSample5Main = new _JTableMain(modelSample5Main);
							scrollSample5Main.setViewportView(tblSample5Main);
							tblSample5Main.addMouseListener(this);
							tblSample5Main.setCellSelectionEnabled(false);
							tblSample5Main.setEditable(false);
							tblSample5Main.setSortable(false);
							tblSample5Main.getColumnModel().getColumn(2).setPreferredWidth(100);
							tblSample5Main.getColumnModel().getColumn(3).setPreferredWidth(100);
							tblSample5Main.addMouseListener(new PopupTriggerListener_panel());
							tblSample5Main.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblSample5Main.rowAtPoint(e.getPoint()) == -1){tblSample5Total.clearSelection();}
									else{tblSample5Main.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblSample5Main.rowAtPoint(e.getPoint()) == -1){tblSample5Total.clearSelection();}
									else{tblSample5Main.setCellSelectionEnabled(true);}
								}
							});
							tblSample5Main.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {
									computeCRB_amount();
								}							
								public void keyPressed(KeyEvent e) {
									computeCRB_amount();
								}

							}); 
						}
						{
							rowHeaderSample5Main = tblSample5Main.getRowHeader22();
							scrollSample5Main.setRowHeaderView(rowHeaderSample5Main);
							scrollSample5Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollSample5Total = new _JScrollPaneTotal(scrollSample5Main);
						pnlSample2.add(scrollSample5Total, BorderLayout.SOUTH);
						{
							modelSample5Total = new modelByrPmtPosting_CRB();
							modelSample5Total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), null });	

							tblSample5Total = new _JTableTotal(modelSample5Total, tblSample5Main);
							scrollSample5Total.setViewportView(tblSample5Total);
							tblSample5Total.setFont(dialog11Bold);
							((_JTableTotal) tblSample5Total).setTotalLabel(0);
						}
					}
				}

				//Cash Deposit
				{
					JPanel pnlSample1 = new JPanel(new BorderLayout());
					tabCenter.addTab("Buyer's Payment (All)", null, pnlSample1, null);
					pnlSample1.setPreferredSize(new java.awt.Dimension(1183, 365));
					{
						scrollSample1Main = new _JScrollPaneMain();
						pnlSample1.add(scrollSample1Main, BorderLayout.CENTER);
						{
							modelSample1Main = new modelByrPmtPosting_CDR();

							tblSample1Main = new _JTableMain(modelSample1Main);
							scrollSample1Main.setViewportView(tblSample1Main);
							tblSample1Main.addMouseListener(this);						

							tblSample1Main.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblSample1Main.rowAtPoint(e.getPoint()) == -1){tblSample1Total.clearSelection();}
									else{tblSample1Main.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblSample1Main.rowAtPoint(e.getPoint()) == -1){tblSample1Total.clearSelection();}
									else{tblSample1Main.setCellSelectionEnabled(true);}					
								}
							});

							tblSample1Main.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent arg0) {
									_JTableMain table = (_JTableMain) arg0.getSource();

									Object oldValue = null;
									try {
										oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
									} catch (NullPointerException e) { }

									if(oldValue != null){

										int row = table.getEditingRow();

										int denominationColumn = table.convertColumnIndexToModel(0);
										int amountColumn = table.convertColumnIndexToModel(2);

										BigDecimal denomination = (BigDecimal) table.getValueAt(row, denominationColumn);

										modelSample1Main.setValueAt(denomination.multiply(new BigDecimal((Integer)oldValue)), row, amountColumn);
										totalEntries2(modelSample1Main, modelSample1Total);

									}
								}
							});						

						}
						{
							rowHeaderSample1Main = tblSample1Main.getRowHeader22();
							scrollSample1Main.setRowHeaderView(rowHeaderSample1Main);
							scrollSample1Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollSample1Total = new _JScrollPaneTotal(scrollSample1Main);
						pnlSample1.add(scrollSample1Total, BorderLayout.SOUTH);
						{
							modelSample1Total = new modelByrPmtPosting_CDR();
							modelSample1Total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

							tblSample1Total = new _JTableTotal(modelSample1Total, tblSample1Main);
							scrollSample1Total.setViewportView(tblSample1Total);
							tblSample1Total.setFont(dialog11Bold);
							((_JTableTotal) tblSample1Total).setTotalLabel(0);
						}
					}
				}

				//Other Check Inventory
				{
					JPanel pnlSample3 = new JPanel(new BorderLayout());
					tabCenter.addTab("Buyer's Ledger", null, pnlSample3, null);
					pnlSample3.setPreferredSize(new java.awt.Dimension(1116, 335));
					{
						scrollSample3Main = new _JScrollPaneMain();
						pnlSample3.add(scrollSample3Main, BorderLayout.CENTER);
						{
							modelSample3Main = new modelByrPmtPosting_ledger();

							tblSample3Main = new _JTableMain(modelSample3Main);
							scrollSample3Main.setViewportView(tblSample3Main);
							tblSample3Main.addMouseListener(this);	
							tblSample3Main.getColumnModel().getColumn(0).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(1).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(2).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(3).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(4).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(5).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(6).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(7).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(8).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(9).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(10).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(11).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(12).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(13).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(14).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(15).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(16).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(17).setPreferredWidth(80);
							tblSample3Main.getColumnModel().getColumn(17).setPreferredWidth(80);

							tblSample3Main.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblSample3Main.rowAtPoint(e.getPoint()) == -1){tblSample3Total.clearSelection();}
									else{tblSample3Main.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblSample3Main.rowAtPoint(e.getPoint()) == -1){tblSample3Total.clearSelection();}
									else{tblSample3Main.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderSample3Main = tblSample3Main.getRowHeader22();
							scrollSample3Main.setRowHeaderView(rowHeaderSample3Main);
							scrollSample3Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollSample3Total = new _JScrollPaneTotal(scrollSample3Main);
						pnlSample3.add(scrollSample3Total, BorderLayout.SOUTH);
						{
							modelSample3Total = new modelByrPmtPosting_ledger();
							modelSample3Total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
									new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	

							tblSample3Total = new _JTableTotal(modelSample3Total, tblSample3Main);
							scrollSample3Total.setViewportView(tblSample3Total);
							tblSample3Total.setFont(dialog11Bold);
							((_JTableTotal) tblSample3Total).setTotalLabel(0);
						}
					}
				}

				//Account Entries
				{
					JPanel pnlSample4 = new JPanel(new BorderLayout());
					tabCenter.addTab("Buyer's Schedule", null, pnlSample4, null);
					pnlSample4.setPreferredSize(new java.awt.Dimension(1183, 365));
					{
						scrollSample4Main = new _JScrollPaneMain();
						pnlSample4.add(scrollSample4Main, BorderLayout.CENTER);
						{
							modelSample4Main = new modelByrPmtPosting_schedule();

							tblSample4Main = new _JTableMain(modelSample4Main);
							scrollSample4Main.setViewportView(tblSample4Main);
							tblSample4Main.addMouseListener(this);		
							tblSample4Main.getColumnModel().getColumn(0).setPreferredWidth(50);
							tblSample4Main.getColumnModel().getColumn(1).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(2).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(3).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(4).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(5).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(6).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(7).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(8).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(9).setPreferredWidth(80);
							tblSample4Main.getColumnModel().getColumn(10).setPreferredWidth(80);

							tblSample4Main.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblSample4Main.rowAtPoint(e.getPoint()) == -1){tblSample4Total.clearSelection();}
									else{tblSample4Main.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblSample4Main.rowAtPoint(e.getPoint()) == -1){tblSample4Total.clearSelection();}
									else{tblSample4Main.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderSample4Main = tblSample4Main.getRowHeader22();
							scrollSample4Main.setRowHeaderView(rowHeaderSample4Main);
							scrollSample4Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollSample4Total = new _JScrollPaneTotal(scrollSample4Main);
						pnlSample4.add(scrollSample4Total, BorderLayout.SOUTH);
						{
							modelSample4Total = new modelByrPmtPosting_schedule();
							modelSample4Total.addRow(new Object[] { "Total", null,  null, null, null, null, 0.00, null });

							tblSample4Total = new _JTableTotal(modelSample4Total, tblSample4Main);
							scrollSample4Total.setViewportView(tblSample4Total);
							tblSample4Total.setFont(dialog11Bold);
							((_JTableTotal) tblSample4Total).setTotalLabel(0);
						}
					}
				}
				{
					JPanel panel = new JPanel(new BorderLayout(5, 5));
					tabCenter.addTab("Particulars", null, panel, null);
					panel.setBorder(new EmptyBorder(10, 10, 10, 10));
					{
						{
							txtParticulars = new JTextArea(""); 
							txtParticulars.setEditable(true); 
							txtParticulars.setFont(new Font("Tahoma", Font.PLAIN, 12));
							txtParticulars.setLineWrap(true);
							txtParticulars.setWrapStyleWord(true);
							txtParticulars.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

							JScrollPane scr = new JScrollPane(txtParticulars); 
							panel.add(txtParticulars, BorderLayout.CENTER);	
						}
						{
							JPanel panButton = new JPanel(new GridLayout(1, 4, 5, 5)); 
							panel.add(panButton, BorderLayout.PAGE_END);
							panButton.setPreferredSize(new Dimension(0, 30));
							{
								panButton.add(new JLabel(""));
								panButton.add(new JLabel("")); 
								panButton.add(new JLabel("")); 

								JButton btnSave = new JButton("Save"); 
								panButton.add(btnSave); 
								btnSave.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										if (JOptionPane.showConfirmDialog(null, "Save this particular?", "Confirm", 
												JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
											String strCoID = ((lookupCompany.getValue()==null)?"":lookupCompany.getValue());
											String strProjID = ((lookupProject.getValue()==null)?"":lookupProject.getValue());
											String strBranch = ((lookupOfficeBranch.getValue()==null)?"":lookupOfficeBranch.getValue());
											String strPhase = ((lookupPhase.getValue()==null)?"":lookupOfficeBranch.getValue());
											String strDate = dteCashierDate.getDate().toString(); 

											lookupMethods.saveParticular(strCoID, strProjID, strBranch, strPhase, strDate, txtParticulars.getText());
										}
									}
								});
							}
						}
					}
				}
			}
		} 
		{
			pnlSouth_2 = new JPanel();
			pnlTable.add(pnlSouth_2, BorderLayout.SOUTH);
			pnlSouth_2.setLayout(new BorderLayout());
			//pnlSouth_2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth_2.setPreferredSize(new Dimension(55, 30));
			{
				JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth_2.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

				{
					btnEdit = new JButton("Edit Entries");
					pnlSouthCenter.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnSave = new JButton("Save Entries");
					pnlSouthCenter.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnPostEntries = new JButton("Post Entries");
					pnlSouthCenter.add(btnPostEntries);
					btnPostEntries.setActionCommand("PostEntries");
					btnPostEntries.addActionListener(this);
					btnPostEntries.setEnabled(false);
				}
				{
					btnRefreshCRB = new JButton("Refresh CRB");
					pnlSouthCenter.add(btnRefreshCRB);
					btnRefreshCRB.setActionCommand("RefreshCRB");	
					btnRefreshCRB.setEnabled(false);
					btnRefreshCRB.addActionListener(this);
				}
			}
		}

		FncTables.bindColumnTables(tblSample2Main, tblSample2Total);
		FncTables.bindColumnTables(tblORMain, tblORTotal);
		initialize_comp();
	}

	private void displayAllPayments(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		

		String sql = 	

				"--displayAllPayments \n" +


			"select 	\n" +
			"	trim(j.doc_alias) as receipt_type,\r\n" + 
			"	trim(c.receipt_no), \r\n" + 
			"	(trim(k.phase)||'-'||trim(k.block)||'-'||trim(k.lot)) as pbl, \r\n" + 
			"   trim(c.pbl_id),  \r\n" +
			"   c.seq_no,  \r\n" +
			"	trim(c.entity_name), 	\r\n" + 
			"	c.particulars, \r\n" + 
			"	e.bank_alias, \r\n" + 
			"	c.check_no,\r\n" + 
			"	coalesce(c.check_amount, 0.00),\r\n" + 
			"	coalesce(c.cash_amount, 0.00),\r\n" + 
			"	coalesce(c.other_amount, 0.00) as other_amt, \r\n" + 
			"	(coalesce(c.check_amount,0.00) + coalesce(c.cash_amount,0.00) + coalesce(c.other_amount, 0)) as  amount, \r\n" + 
			"	c.pay_rec_id,\r\n " +
			"	c.tra_header_id, \r\n" + 
			"	(select x.status_id from rf_crb_header x where x.rb_id = trim(c.receipt_no) and x.pay_rec_id::int = (case \r\n" +
			"	when coalesce(c.pay_rec_id::int, 0) = 0 then coalesce(c.tra_header_id::int, 0) else coalesce(c.pay_rec_id::int, 0) end) and x.status_id != 'I') as status_id /*coalesce(m.status_id,'A')*/, \r\n" + 
			"	c.entity_id 				\r\n" + 

			"	from (select (case when a.true_or_no is null or a.true_or_no = '' then a.true_ar_no else a.true_or_no end) as receipt_no,  \r\n" + 
			"		a.pbl_id, \r\n" + 
			"		a.entity_name, \r\n" + 
			"		a.particulars, \r\n" + 
			"		a.bank_id, \r\n" + 
			"		a.bank_branch_id, \r\n" + 
			"		a.check_no, \r\n" + 
			"		a.check_date, \r\n" + 
			"		a.pymnt_type, \r\n" + 
			"		(case when a.or_date = a.actual_date then (coalesce(a.cash_amount,0) - coalesce(a.other_cash_payment,0)) else\r\n" + 
			"			(case when a.cash_amount is null or a.ar_no is not null or a.ar_no != ''  then null else \r\n" + 
			"			(coalesce(a.cash_amount,0) - coalesce(a.other_cash_payment,0)) end) end ) as cash_amount,  \r\n" + 
			"		(case when a.or_date = a.actual_date then (coalesce(a.check_amount,0) - coalesce(a.other_cash_payment,0)) else\r\n" + 
			"			(case when a.check_amount is null  or a.ar_no is not null or a.ar_no != ''  then null else  \r\n" + 
			"			(coalesce(a.check_amount,0) - coalesce(a.other_cash_payment,0)) end) end ) as check_amount,  \r\n" + 
			"		(case when a.or_date = a.actual_date then null else\r\n" + 
			"			(case when a.ar_no is not null || a.ar_no != '' then a.amount else 0.00 end) + coalesce(a.other_cash_payment,0) end ) as other_amount, " +	
			/*"		(case when a.cash_amount is null or a.ar_no is not null or a.ar_no != ''  then null else \n" +
			"			(coalesce(a.cash_amount,0) - coalesce(a.other_cash_payment,0)) end) as cash_amount,  \r\n" +   //less credited payments
			"		(case when a.check_amount is null  or a.ar_no is not null or a.ar_no != ''  then null else   \n" +
			"			(coalesce(a.check_amount,0) - coalesce(a.other_cash_payment,0)) end) as check_amount,  \r\n" + //less credited payments
			"		( case when a.ar_no is not null || a.ar_no != '' then a.amount else 0.00 end) + coalesce(a.other_cash_payment,0) as other_amount, \r\n" + //credited payments
			 */			"		a.amount, \r\n" + 
			 "		a.pay_rec_id, \n " +
			 "		0 as tra_header_id, \r\n" + 
			 "		a.or_doc_id, \r\n" + 
			 "		a.pr_doc_id, \r\n" + 
			 "		a.branch_id, \r\n" + 
			 "		a.co_id, \r\n" + 
			 "		a.proj_id, \r\n" + 
			 "		a.seq_no, \r\n" + 
			 "		a.entity_id, \r\n" +
			 "		a.receipt_type   \r\n" + 

			 "		from ( \r\n" +
			 "			select  (case when a.or_no is null || trim(a.or_no) = '' then trim(a.ar_no) else trim(a.or_no) end) as receipt_no, \r\n" + 
			 "			b.entity_name, \r\n" + 
			 "			trim(c.partdesc)as particulars, \r\n" + 
			 "			a.bank_id, \r\n" + 
			 "			a.bank_branch_id, \r\n" + 
			 "			a.check_no, \r\n" + 
			 "			a.check_date, \r\n" + 
			 "			a.pymnt_type,\r\n" + 
			 "			(case when a.pymnt_type = 'B' then a.amount else null end) as check_amount, \r\n" + 
			 "			(case when a.pymnt_type = 'A' then a.amount else null end) as cash_amount, \r\n" + 
			 "			coalesce(e.amount,0) as other_cash_payment,\r\n" + 
			 "			a.amount, \r\n" + 
			 "			(case when or_doc_id is null or trim(or_doc_id) = '' then null else a.or_no end) as true_or_no,  \r\n" + 
			 "			(case when or_doc_id is null or trim(or_doc_id) = '' then (case when a.or_no is null or a.or_no = '' then a.ar_no else a.or_no end)\r\n" + 
			 "				else null end) as true_ar_no, \n" + 
			 "			a.pay_rec_id, \r\n" + 
			 "			a.or_doc_id,\r\n" + 
			 "			a.pr_doc_id, \r\n" + 
			 "			a.branch_id, \r\n" + 
			 "			a.co_id, \r\n" + 
			 "			a.proj_id, \r\n" + 
			 "			a.pbl_id, \r\n" + 
			 "			a.seq_no, \r\n" + 
			 "			a.entity_id, \r\n" +
			 "			( case when a.or_doc_id is not null then a.or_doc_id else a.pr_doc_id end ) as receipt_type, \n" +
			 "			a.ar_no, \r\n" + 
			 " 			to_char(a.actual_date, 'MM-dd-yyyy') as actual_date ,\r\n" + 
			 "			to_char(a.or_date, 'MM-dd-yyyy') as or_date \n" +

			 "			from ( select * from rf_payments \n" +
			 "				where co_id = '" + lookupCompany.getText() +"' \n" +
			 "				and pbl_id = '"+pbl_id+"'  \n" +
			 "				and seq_no = "+seq_no+" \n" +
			 "				and co_id = '"+co_id+"' \n" +
			 "				and entity_id = '"+entity_id+"'  \n" ;

		sql = sql +

				"				order by or_doc_id desc, pr_doc_id ) a						\r\n" + 
				"				left join rf_entity b on a.entity_id = b.entity_id 				\r\n" +
				"				left join mf_pay_particular c on a.pay_part_id = c.pay_part_id	 \n" +
				"				left join (select distinct on (client_seqno) * from rf_pay_detail) d on a.client_seqno = d.client_seqno		 \n" +
				"				left join rf_tra_detail e on a.receipt_id like e.receipt_no	\n" + 
				"			) a \r\n\n" + 
				"UNION ALL\r\n\n" + 
				"\r\n" + 
				"\r\n" + 
				"		-----start of tra-header/detail\r\n" + 
				"\r\n" + 
				"		select\r\n" + 
				"\r\n" + 
				"		a.receipt_no,\r\n" + 
				"		b.pbl_id,\r\n" + 
				"		c.entity_name, \r\n" + 
				"		trim(d.partdesc) as particular,\r\n" + 
				"		a.bank,\r\n" + 
				"		a.branch,\r\n" + 
				"		a.check_no,\r\n" + 
				"		a.check_date,\r\n" + 
				"		(case when a.check_no is null or  a.check_no = ''  then 'A' else 'B' end ) as pymnttype,\r\n" + 
				"		(case when a.check_no is null or  a.check_no = ''  then a.amount else 0 end ) as cash_amount,	\r\n" + 
				"		(case when a.check_no is null or  a.check_no = ''  then 0 else a.amount end ) as check_amount,\r\n" + 
				"		0 as other_cash_payment,\r\n" + 
				"		a.amount,\r\n" + 
				"		0 as pay_rec_id, \n" +
				"		b.tra_header_id, \r\n" + 
				"		(case when b.receipt_type = '01' or b.receipt_type = '08' then b.receipt_type else '' end) as or_doc_id,\r\n" + 
				"		(case when b.receipt_type = '03' then b.receipt_type else '' end) as pr_doc_id,\r\n" + 
				"		a.branch_id,\r\n" + 
				"		b.co_id,\r\n" + 
				"		b.proj_id,\r\n" + 
				"		b.seq_no,\r\n" + 
				"		b.entity_id," +
				"		'03' as receipt_type \r\n\n" + 
				"\r\n" + 
				"			from   ( select * from rf_tra_detail ) a\r\n" + 
				"			join (select * from rf_tra_header \n" +
				"				where pbl_id = '"+pbl_id+"'  \n" +
				"				and seq_no = "+seq_no+" \n" +
				"				and entity_id = '"+entity_id+"'  \n" +
				"				and co_id = '"+co_id+"'   \n" +
				")b on a.client_seqno = b.client_seqno \r\n" + 
				"			left join rf_entity c  on b.entity_id = c.entity_id\r\n" + 
				"			left join mf_pay_particular d on a.part_id = d.pay_part_id\r\n" + 
				"\r\n" + 
				"			\r\n" + 
				"		-----end of tra-header/detail\r\n" + 
				"		" +
				"		) c \n\n" + 

		"left join mf_bank e \r\n" + 
		"on c.bank_id = e.bank_id \n\n" + 

		"left join mf_company f \r\n" + 
		"on c.co_id = f.co_id \n\n" + 

		"left join mf_project g \r\n" + 
		"on c.proj_id = g.proj_id \n\n" + 

		"left join (select distinct on (branch_id) branch_id, branch_name from mf_office_branch) h \r\n" + 
		"on c.branch_id = h.branch_id \n\n" + 

		"left join mf_bank_branch i \r\n" + 
		"on c.bank_branch_id = i.bank_branch_id 	\n\n" + 

		"left join mf_doc_details j \n" +
		"on c.receipt_type = j.doc_id \r\n" + 

		"left join mf_unit_info k \r\n" + 
		"on c.pbl_id = k.pbl_id \n\n" + 

		"left join mf_pay_particular l\r\n" + 
		"on c.particulars = l.particulars\n\n" + 

		"left join rf_crb_header m\r\n" + 
		"on c.receipt_no=m.rb_id and (case when c.pay_rec_id = 0 then c.tra_header_id else c.pay_rec_id end)::char = m.reference_no::char \n\n" +

		"order by c.receipt_no \n\n" ;

		System.out.printf("SQL displayAllPayments: %s%n", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries2(modelMain, modelTotal);

		}	

		else {
			modelSample1Total = new modelByrPmtPosting_CDR();
			modelSample1Total.addRow(new Object[] {  "Total", null,  null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null });	
			tblSample1Total = new _JTableTotal(modelSample1Total, tblSample1Main);
			scrollSample1Total.setViewportView(tblSample1Total);
			tblSample1Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample1Total).setTotalLabel(0);}

		tblSample1Main.packAll();
		adjustRowHeight(tblSample1Main);
	}

	private void displayLedger(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		

		String sql = 

				"--displayLedger \n" +
						"select\r\n" + 
						"			--a.pay_rec_id, \r\n" + 
						"			to_char(a.date_paid::date, 'Mon dd, yyyy'),\r\n" + 
						"			to_char(a.sched_date::date, 'Mon dd, yyyy'),\r\n" + 
						"			coalesce(b.pmt_amount, null) as amount, \r\n" + 
						"			null as other_fees, \r\n" + 
						"			coalesce(d.amount, null) as proc_fee, \r\n" + 
						"			coalesce(e.amount, null) as res, \r\n" + 
						"			coalesce(f.amount, null) as dp, \r\n" + 
						"			coalesce(g.amount, null) as mri,\r\n" + 
						"			coalesce(h.amount, null) as fire, \r\n" + 
						"			coalesce(i.amount, null) as maf, \r\n" + 
						"			coalesce(j.amount, null) as vat, \r\n" + 
						"			coalesce(k.amount, null) as soi, \r\n" + 			
						"			coalesce(l.amount, null) as sop, \r\n" + 
						"			coalesce(m.amount, null) as cbp, \r\n" + 
						"			coalesce(n.amount, null) as adj, \r\n" + 
						"			coalesce(o.amount, null) as interest, \r\n" + 
						"			coalesce(p.amount, null) as principal, \r\n" + 
						"			q.balance as balance \r\n" + 
						"			from rf_client_ledger a\r\n" + 
						"\r\n" + 
						"				--amount paid \r\n" + 
						"				left join (select distinct on (pay_rec_id) pay_rec_id, date_paid, sched_date, pmt_amount \r\n" + 
						"				from rf_client_ledger \r\n" + 
						"				where entity_id='"+entity_id+"' \r\n" + 
						"				and pbl_id='"+pbl_id+"' \r\n" + 
						"				and seq_no="+seq_no+"\r\n" + 
						"			 	and status_id='A'\r\n" + 
						"				and pay_rec_id = '"+rec_id+"'  \n" +
						"				order by pay_rec_id, date_paid::date, sched_date::date asc) b \r\n" + 
						"				on b.date_paid=a.date_paid and b.sched_date=a.sched_date and b.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--processing fee\r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id='019' \r\n" + 
						"			 	and a.status_id='A'\r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) d \r\n" + 
						"				on a.sched_date=d.sched_date::date and a.date_paid::date=d.date_paid::date and a.pay_rec_id=d.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--reservation \r\n" + 
						"				left join (select a.sched_date::date, a.date_paid, sum(a.amount) as amount, pay_rec_id, part_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('012') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id, part_id\r\n" + 
						"				order by a.sched_date) e \r\n" + 
						"				on a.sched_date::date=e.sched_date::date and a.date_paid::date=e.date_paid::date and a.pay_rec_id=e.pay_rec_id   -- and a.part_id=e.part_id \r\n" + 
						"\r\n" + 
						"				--downpayment \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id, part_id \r\n" + 
						"				from rf_client_ledger a\r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('013') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id, part_id \r\n" + 
						"				order by a.sched_date) f \r\n" + 
						"				on f.sched_date::date=a.sched_date::date and f.date_paid::date=a.date_paid::date and f.pay_rec_id=a.pay_rec_id    -- and f.part_id=a.part_id \r\n" + 
						"\r\n" + 
						"				--mri\r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('003', '018') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id\r\n" + 
						"				order by a.sched_date) g \r\n" + 
						"				on g.sched_date=a.sched_date and g.date_paid::date=a.date_paid::date and g.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--fire \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a\r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id='004'\r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) h \r\n" + 
						"				on h.sched_date=a.sched_date::date and h.date_paid::date=a.date_paid::date and h.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--maf \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id='005' \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) i \r\n" + 
						"				on i.sched_date=a.sched_date::date and i.date_paid::date=a.date_paid::date and i.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--vat \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a\r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('008', '023') \r\n" + 
						"				and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) j \r\n" + 
						"				on j.sched_date=a.sched_date::date and j.date_paid::date=a.date_paid::date and j.pay_rec_id=a.pay_rec_id\r\n" + 
						"\r\n" + 
						"				--soi \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"'\r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('006') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by sched_date) k \r\n" + 
						"				on k.sched_date=a.sched_date::date and k.date_paid::date=a.date_paid::date and k.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--sop \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('007', '020', '021') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) l \r\n" + 
						"				on l.sched_date=a.sched_date::date and l.date_paid::date=a.date_paid::date and l.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--fc/cbp \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id='009' \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) m \r\n" + 
						"				on m.sched_date=a.sched_date::date and m.date_paid::date=a.date_paid::date and m.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--adjustment \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('011', '016') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) n \r\n" + 
						"				on n.sched_date=a.sched_date::date and n.date_paid::date=a.date_paid::date and n.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--interest \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
						"				from rf_client_ledger a left join rf_client_schedule b \r\n" + 
						"				on a.entity_id=b.entity_id and a.pbl_id=b.pbl_id and a.sched_date=b.scheddate \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('001', '015') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
						"				order by a.sched_date) o \r\n" + 
						"				on o.sched_date=a.sched_date::date and o.date_paid::date=a.date_paid::date and o.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--monthly amortization \r\n" + 
						"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id, part_id \r\n" + 
						"				from rf_client_ledger a \r\n" + 
						"				where a.pbl_id='"+pbl_id+"'\r\n" + 
						"				and a.entity_id='"+entity_id+"' \r\n" + 
						"				and a.seq_no="+seq_no+"\r\n" + 
						"				and a.pay_rec_id = '"+rec_id+"'  \n" +
						"				and a.part_id in ('002') \r\n" + 
						"			 	and a.status_id='A' \r\n" + 
						"				group by a.sched_date, a.date_paid, pay_rec_id, part_id \r\n" + 
						"				order by a.sched_date) p \r\n" + 
						"				on p.sched_date=a.sched_date::date and p.date_paid::date=a.date_paid::date and p.pay_rec_id=a.pay_rec_id \r\n" + 
						"\r\n" + 
						"				--balance \r\n" + 
						"				left join (select distinct on (date_paid, pay_rec_id, sched_date, balance) * \r\n" + 
						"				from (select date_paid, pay_rec_id, sched_date, balance, part_id \r\n" + 
						"				from rf_client_ledger \r\n" + 
						"				where pbl_id='"+pbl_id+"'\r\n" + 
						"				and entity_id='"+entity_id+"' \r\n" + 
						"				and seq_no="+seq_no+"\r\n" + 
						"				and pay_rec_id = '"+rec_id+"'  \n" +
						"				and part_id in ('012', '013', '014') \r\n" + 
						"				and status_id='A' \r\n" + 
						"				--group by date_paid, pay_rec_id, sched_date, balance \r\n" + 
						"				--order by date_paid, sched_date, pay_rec_id \r\n" + 
						"			\r\n" + 
						/*"				union \r\n" + 
				"			\r\n" + 
				"				select date_paid, pay_rec_id, sched_date, balance, part_id \r\n" + 
				"				from rf_client_ledger \r\n" + 
				"				where pbl_id='"+pbl_id+"'\r\n" + 
				"				and entity_id='"+entity_id+"' \r\n" + 
				"				and seq_no="+seq_no+"\r\n" + 
				"				and pay_rec_id = '"+rec_id+"'  \n" +
				"				and part_id not in ('012', '013', '014') \r\n" + 
				"			 	and status_id='A' \r\n" + 
				"				--group by date_paid, pay_rec_id, sched_date, balance \r\n" + 
				"				order by date_paid, sched_date, pay_rec_id" +*/
				") a) q \r\n" + 
				"				on q.sched_date::date=a.sched_date::date and q.date_paid::date=a.date_paid::date and q.pay_rec_id=a.pay_rec_id-- and q.part_id=a.part_id \r\n" + 
				"			\r\n" + 
				"			where a.entity_id='"+entity_id+"' \r\n" + 
				"			and a.pbl_id='"+pbl_id+"'\r\n" + 
				"			and a.seq_no="+seq_no+"\r\n" + 
				"			and a.pay_rec_id = '"+rec_id+"'  \n" +
				"			and a.status_id='A' \r\n" + 
				"			--and a.pay_rec_id='\"+rec_id+\"' \r\n" + 
				"			\r\n" + 
				"			group by a.pay_rec_id, a.date_paid::date, a.sched_date::date, b.pmt_amount, d.amount, e.amount, f.amount, \r\n" + 
				"			g.amount, h.amount, i.amount, j.amount, k.amount, l.amount, m.amount, n.amount, o.amount, p.amount, \r\n" + 
				"			q.balance \r\n" + 
				"			\r\n" + 
				"			order by a.date_paid::date, a.sched_date::date  " ;




		System.out.printf("SQL displayLedger: \n" + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries3(modelMain, modelTotal);

		}	

		else {
			modelSample3Total = new modelByrPmtPosting_ledger();
			modelTotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	
			tblSample3Total = new _JTableTotal(modelSample3Total, tblSample3Main);
			scrollSample3Total.setViewportView(tblSample3Total);
			tblSample3Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample3Total).setTotalLabel(0);}

		adjustRowHeight(tblSample3Main);
	}

	private void displaySchedule(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		

		String sql = 	

				"select   \r\n" + 
						"		b.part_abbv,\r\n" + 
						"		a.scheddate::date, \r\n" + 
						"		a.amount, \r\n" + 
						"		null as other_fees, \r\n" + 
						"		a.mri, \r\n" + 
						"		a.fire, \r\n" + 
						"		a.maf, \r\n" + 
						"		a.vat, \r\n" + 
						"		a.interest, \r\n" + 
						"		a.principal, \r\n" + 
						"		a.balance \r\n" + 
						"		from rf_client_schedule a \r\n" + 
						"		left join mf_client_ledger_part b on  a.part_id = b.part_id \r\n" + 
						"		where a.entity_id = '"+entity_id+"' \r\n" + 
						"		and a.pbl_id= '"+pbl_id+"'  and a.seq_no = "+seq_no+" and a.status_id = 'A'\r\n" + 
						"		order by a.scheddate " ;

		System.out.printf("SQL: %s%n", sql);

		System.out.printf("SQL displaySchedule: \n" + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries4(modelMain, modelTotal);

		}	

		else {
			modelSample4Total = new modelByrPmtPosting_schedule();
			modelTotal.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	
			tblSample4Total = new _JTableTotal(modelSample4Total, tblSample4Main);
			scrollSample4Total.setViewportView(tblSample4Total);
			tblSample4Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample4Total).setTotalLabel(0);}

		adjustRowHeight(tblSample4Main);
	}

	private void displayAccountInfo(){

		if(getAccountInfo1(pbl_id, seq_no)==null) 
		{
			JOptionPane.showMessageDialog(getContentPane(), "Account has no Account Information yet.", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);	
		}
		else {

			Object[] acct_info1 = getAccountInfo1(pbl_id, seq_no);	

			try { tagHouseModel.setTag((String) acct_info1[1]); } 
			catch (NullPointerException e) { tagHouseModel.setTag(""); }

			try { tagBuyerType.setTag((String) acct_info1[2]); } 
			catch (NullPointerException e) { tagBuyerType.setTag(""); }

			try { tagAcctStatus.setTag((String) acct_info1[3]); } 
			catch (NullPointerException e) { tagAcctStatus.setTag(""); }

			try { tagDocStatus.setTag((String) acct_info1[5]); } 
			catch (NullPointerException e) { tagDocStatus.setTag(""); }

			try { tagORdate.setTag((String) df2.format(acct_info1[4]).toString()); } 
			catch (NullPointerException e) { tagORdate.setTag(""); }

			try { pnlTable_acct_info.setBorder(JTBorderFactory.createTitleBorder("Account Info. [" +(String) acct_info1[6]+ "]" )); } 
			catch (NullPointerException e) { pnlTable_acct_info.setBorder(JTBorderFactory.createTitleBorder("Account Info. [ ]"));; }

			try { tagORdate.setTag((String) df2.format(acct_info1[4]).toString()); } 
			catch (NullPointerException e) { tagORdate.setTag(""); }

			String buyer_type = (String) acct_info1[7];

			if (buyer_type.equals("07")||buyer_type.equals("08")||buyer_type.equals("09")||buyer_type.equals("12"))
			{
				displayPbgPmtSchemeDetails();
			}				 

			else if (buyer_type.equals("02")||buyer_type.equals("11"))		
			{
				displaySpotCashDetails();
			}

			else if (buyer_type.equals("01")||buyer_type.equals("03")||buyer_type.equals("05")
					||buyer_type.equals("16")||buyer_type.equals("10")||buyer_type.equals("04"))
			{
				displayIHFSchemeDetails();			
			}
		}

		displayAllPayments(modelSample1Main, rowHeaderSample1Main, modelSample1Total);
		displayLedger(modelSample3Main, rowHeaderSample3Main, modelSample3Total);		 
		displaySchedule(modelSample4Main, rowHeaderSample4Main, modelSample4Total);

	}

	private void displayPbgPmtSchemeDetails(){

		if (getPbgPmtSchemeDetails(pbl_id, seq_no)==null)
		{
			JOptionPane.showMessageDialog(getContentPane(), "Account has no payment scheme yet.", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);	
		}
		else 
		{
			Object[] pmt_sch = getPbgPmtSchemeDetails(pbl_id, seq_no);	
			try { pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info. [" +(String) pmt_sch[5]+ "]")); } 
			catch (NullPointerException e) { pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info. [ ]")); }

			try { tagGSP.setTag(nf.format(Double.parseDouble((String) pmt_sch[0].toString()))); } 
			catch (NullPointerException e) { tagGSP.setTag("0.00"); }

			try { tagDiscount.setTag(nf.format(Double.parseDouble((String) pmt_sch[1].toString()))); } 
			catch (NullPointerException e) { tagDiscount.setTag("0.00"); }

			try { tagVAT.setTag(nf.format(Double.parseDouble((String) pmt_sch[2].toString())));	 } 
			catch (NullPointerException e) { tagVAT.setTag("0.00"); }

			try { tagNSP.setTag(nf.format(Double.parseDouble((String) pmt_sch[4].toString()))); } 
			catch (NullPointerException e) { tagNSP.setTag("0.00"); }

			try { tagDPterm.setTag((String) pmt_sch[6].toString()); } 
			catch (NullPointerException e) { tagDPterm.setTag(""); }

			try { tagMAterm.setTag((String) pmt_sch[7].toString());	 } 
			catch (NullPointerException e) { tagMAterm.setTag(""); }

			try { tagDPamt.setTag(nf.format(Double.parseDouble((String) pmt_sch[8].toString()))); } 
			catch (NullPointerException e) { tagDPamt.setTag("0.00"); }

			try { tagMAamt.setTag(nf.format(Double.parseDouble((String) pmt_sch[9].toString())));	 } 
			catch (NullPointerException e) { tagMAamt.setTag("0.00"); }
		}
	}

	private void displaySpotCashDetails(){

		if (getSpotCashDetails(pbl_id, seq_no)==null)
		{
			JOptionPane.showMessageDialog(getContentPane(), "Account has no payment scheme yet.", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);	
		}
		else 
		{		
			Object[] pmt_sch = getSpotCashDetails(pbl_id, seq_no);

			try {pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info. [" +(String) pmt_sch[5]+ "]")); } 
			catch (NullPointerException e) { pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info. [ ]")); }			

			try {tagGSP.setTag(nf.format(Double.parseDouble((String) pmt_sch[0].toString())));	 } 
			catch (NullPointerException e) {tagGSP.setTag("0.00");	 }	

			try {tagDiscount.setTag(nf.format(Double.parseDouble((String) pmt_sch[1].toString()))); } 
			catch (NullPointerException e) {tagDiscount.setTag("0.00");	 }	

			try {tagVAT.setTag(nf.format(Double.parseDouble((String) pmt_sch[2].toString())));	} 
			catch (NullPointerException e) {tagVAT.setTag("0.00");	 }	

			try {tagNSP.setTag(nf.format(Double.parseDouble((String) pmt_sch[4].toString())));	} 
			catch (NullPointerException e) {tagNSP.setTag("0.00");	 }	

			try {tagNSP.setTag(nf.format(Double.parseDouble((String) pmt_sch[4].toString())));	} 
			catch (NullPointerException e) {tagNSP.setTag("0.00");	 }	

			tagDPterm.setText("0.00");	
			tagMAterm.setText("0.00");	
			tagDPamt.setText("0.00");	
			tagMAamt.setText("0.00");	
		}

	}

	private void displayIHFSchemeDetails(){

		if (getIHFSchemeDetails(pbl_id, seq_no)==null)
		{
			JOptionPane.showMessageDialog(getContentPane(), "Account has no payment scheme yet.", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);	
		}
		else 
		{
			Object[] pmt_sch = getIHFSchemeDetails(pbl_id, seq_no);			

			try {pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info. [" +(String) pmt_sch[5]+ "]"));} 
			catch (NullPointerException e) {pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info. [ ]"));}	

			try {tagGSP.setTag(nf.format(Double.parseDouble((String) pmt_sch[0].toString())));} 
			catch (NullPointerException e) {tagGSP.setTag("0.00");}	

			try {tagDiscount.setTag(nf.format(Double.parseDouble((String) pmt_sch[1].toString())));	} 
			catch (NullPointerException e) {tagDiscount.setTag("0.00");}	

			try {tagVAT.setTag(nf.format(Double.parseDouble((String) pmt_sch[2].toString())));	} 
			catch (NullPointerException e) {tagVAT.setTag("0.00");}	

			try {tagNSP.setTag(nf.format(Double.parseDouble((String) pmt_sch[4].toString())));	} 
			catch (NullPointerException e) {tagNSP.setTag("0.00");}	

			try {tagDPterm.setTag((String) pmt_sch[6].toString());	} 
			catch (NullPointerException e) {tagDPterm.setTag("");}	

			try {tagMAterm.setTag((String) pmt_sch[7].toString());		} 
			catch (NullPointerException e) {tagMAterm.setTag("");}	

			try {tagDPamt.setTag(nf.format(Double.parseDouble((String) pmt_sch[8].toString())));		} 
			catch (NullPointerException e) {tagDPamt.setTag("0.00");}	

			try {tagMAamt.setTag(nf.format(Double.parseDouble((String) pmt_sch[9].toString())));		} 
			catch (NullPointerException e) {tagMAamt.setTag("0.00");}
		}		
	}


	//Enable/Disable all components inside JPanel
	private void refreshAcctInfo(){

		tagClientName.setText("[ ]");
		tagUnitID.setText("[ ]");
		tagPBL.setText("[ ]");
		tagHouseModel.setText("[ ]");
		tagBuyerType.setText("[ ]");
		tagAcctStatus.setText("[ ]");
		tagDocStatus.setText("[ ]");
		tagORdate.setText("[ ]");

		tagGSP.setText("[ ]");
		tagDiscount.setText("[ ]");
		tagVAT.setText("[ ]");
		tagNSP.setText("[ ]");
		tagDPterm.setText("[ ]");
		tagMAterm.setText("[ ]");
		tagDPamt.setText("[ ]");
		tagMAamt.setText("[ ]");
		pnlTable_acct_info.setBorder(JTBorderFactory.createTitleBorder("Account Info."));	
		pnlTable_b.setBorder(JTBorderFactory.createTitleBorder("Payment Scheme Info."));

	}

	private void enableAcctInfo(boolean x){

		tagClientName.setEnabled(x);
		tagUnitID.setEnabled(x);
		tagPBL.setEnabled(x);
		tagHouseModel.setEnabled(x);
		tagBuyerType.setEnabled(x);
		tagAcctStatus.setEnabled(x);
		tagDocStatus.setEnabled(x);
		tagORdate.setEnabled(x);

		lblClientName.setEnabled(x);
		lblUnitID.setEnabled(x);
		lblPBL.setEnabled(x);
		lblHouseModel.setEnabled(x);
		lblBuyerType.setEnabled(x);
		lblAcctStatus.setEnabled(x);
		lblDocStatus.setEnabled(x);
		lblORdate.setEnabled(x);		

		tagGSP.setEnabled(x);
		tagDiscount.setEnabled(x);
		tagVAT.setEnabled(x);
		tagNSP.setEnabled(x);
		tagDPterm.setEnabled(x);
		tagMAterm.setEnabled(x);
		tagDPamt.setEnabled(x);
		tagMAamt.setEnabled(x);

		lblGSP.setEnabled(x);
		lblDiscount.setEnabled(x);
		lblVAT.setEnabled(x);
		lblNSP.setEnabled(x);
		lblDPterm.setEnabled(x);
		lblMAterm.setEnabled(x);
		lblDPamt.setEnabled(x);
		lblMAamt.setEnabled(x);


	}

	private void enableButtons(boolean x) {

		btnCancel.setEnabled(x);
		btnEdit.setEnabled(x);
		btnPostEntries.setEnabled(x);
		btnPostAll.setEnabled(x);		
		btnRefreshCDR.setEnabled(x);
		btnCRBentries.setEnabled(x);
	}

	private String checkCRBstatus(){

		String strSQL = 
				"select status_id " +
						"from rf_crb_header " +
						"where trim(pay_rec_id) = '"+rec_id+"' " +
						"and  trim(rb_id) = '"+crb_no+"'  ";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}


	}

	private void refresh_table_main(){

		FncTables.clearTable(modelSample2Main);
		FncTables.clearTable(modelSample2Total);
		rowHeaderSample2Main = tblSample2Main.getRowHeader22();
		scrollSample2Main.setRowHeaderView(rowHeaderSample2Main);
		modelSample2Total.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null });	
	}

	private void refresh_tables(){

		FncTables.clearTable(modelSample1Main);
		FncTables.clearTable(modelSample1Total);			
		FncTables.clearTable(modelSample3Main);
		FncTables.clearTable(modelSample3Total);
		FncTables.clearTable(modelSample4Main);
		FncTables.clearTable(modelSample4Total);
		FncTables.clearTable(modelSample5Main);
		FncTables.clearTable(modelSample5Total);		

		rowHeaderSample1Main = tblSample1Main.getRowHeader22();
		scrollSample1Main.setRowHeaderView(rowHeaderSample1Main);	

		rowHeaderSample3Main = tblSample3Main.getRowHeader22();
		scrollSample3Main.setRowHeaderView(rowHeaderSample3Main);	

		rowHeaderSample4Main = tblSample4Main.getRowHeader22();
		scrollSample4Main.setRowHeaderView(rowHeaderSample4Main);	

		rowHeaderSample5Main = tblSample5Main.getRowHeader22();
		scrollSample5Main.setRowHeaderView(rowHeaderSample5Main);	

		modelSample1Total.addRow(new Object[] { "Total", null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null });	
		modelSample3Total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	
		modelSample4Total.addRow(new Object[] { "Total",null,  new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	
		modelSample5Total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00), null });	
	}

	private void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();								

		lblProject.setEnabled(true);
		lookupProject.setEnabled(true);	
		tagProject.setEnabled(true);	

		lblOfficeBranch.setEnabled(true);	
		lookupOfficeBranch.setEnabled(true);	
		tagBranch.setEnabled(true);	

		lblCashierDate.setEnabled(true);	
		dteCashierDate.setEnabled(true);

		//btnDisplayCDR.setEnabled(true);
		btnCancel.setEnabled(true);

		KEYBOARD_MANAGER.focusNextComponent();	
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

		lookupCompany.setValue("02");
		lookupOfficeBranch.setValue("");
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("RefreshCDR")) 
		{ 			
			/*	displayCDR();  */
			display_CDR displayCDR = new display_CDR();
			Thread thread = new Thread(displayCDR);
			thread.start();
		}				

		if(e.getActionCommand().equals("Cancel")) { cancel(); }

		if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "12")==true ) {  edit(); }
		else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "12")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit CRB.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("PostEntries") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "12")==true ) { postEntries(); }
		else if (e.getActionCommand().equals("PostEntries") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "12")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to post CRB.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("PostAll") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "12")==true ) {
			postAll();
		} else if (e.getActionCommand().equals("PostAll") && FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "12")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to post CRB.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("RefreshCDR") ) {
			refreshCDR(); 
			btnRefreshCDR.setText("Refresh CDR");
		}

		if (e.getActionCommand().equals("RefreshCRB") ) {
			refreshCRB();
		}

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "12")==true ) {
			saveCRB();
		} else if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "12")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to update CRB.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getActionCommand().equals("CRB_Entries") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==true) {
			new Thread(new Runnable() {
				public void run() {
					Object[] options1 = {
							"Regular CRB", 
							"Late OR CRB"
					};

					int result = JOptionPane.showOptionDialog(null, "Select which type of report will be printed.", "Printing Options",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, options1, null);

					System.out.println("");
					System.out.println("result: " + result);

					if (JOptionPane.showConfirmDialog(null, "The particular that will reflect is: \n"+txtParticulars.getText(), 
							"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {

						if (result==JOptionPane.YES_OPTION) {
							previewCRB(); 
						} else {
							previewCRB_LateOR();
						}					

						if (result==JOptionPane.YES_OPTION) {
							previewCRB_summary();
						} else {
							previewCRB_summary_LateOR();
						}	
					}
				}
			}).run();

			/*
			if (result==JOptionPane.YES_OPTION) {

				System.out.println("");
				System.out.println("Clicked Late Regular Report");

				preview_crb_detailed preview_detailed = new preview_crb_detailed();
		        Thread thread_detailed = new Thread(preview_detailed);
		        thread_detailed.start();
		        try {
		        	System.out.println("thread_detailed: " + thread_detailed.getState());
					thread_detailed.join();
				} catch (InterruptedException e1) {
					JOptionPane.showMessageDialog(getContentPane(), "");
				}

		        preview_crb_summary preview_summary = new preview_crb_summary();
		        Thread thread_summary = new Thread(preview_summary);
		        thread_summary.start();
		        try {
		        	System.out.println("thread_summary: " + thread_summary.getState());
					thread_summary.join();
				} catch (InterruptedException e1) {
					JOptionPane.showMessageDialog(getContentPane(), "");
				}

		        System.out.println("thread_detailed: " + thread_detailed.getState());
		        System.out.println("thread_summary: " + thread_summary.getState());

		        FncGlobal.stopProgress();
				btnCRBentries.setEnabled(true);

			} else {
				if (modelORMain.getRowCount()==0) {
					JOptionPane.showMessageDialog(getContentPane(), "No Acknowledgement Receipt was assigned\nan OR for this transaction date.");
				} else {
					preview_crb_detailed_or preview_detailed_or = new preview_crb_detailed_or();
			        Thread thread_detailed_or = new Thread(preview_detailed_or);
			        thread_detailed_or.start();
			        try {
						thread_detailed_or.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

			        preview_crb_summary_or preview_summary_or = new preview_crb_summary_or();
			        Thread thread_summary_or = new Thread(preview_summary_or);
			        thread_summary_or.start();
			        try {
						thread_summary_or.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				FncGlobal.stopProgress();
				btnCRBentries.setEnabled(true);
			}
			 */
		} else if (e.getActionCommand().equals("CRB_Entries") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==false ) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview CRB entries.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {
			clickTableColumn();
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

	private void postAll() {
		if (PeriodOpen()) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to post all CRB entries?", "Save", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String tra_rec_id 	= "";
				String pay_rec_id 	= "";
				String rb_id    	= "";
				String record_id   	= "";
				String status   	= "";

				pgUpdate db = new pgUpdate();	

				for(int x=0; x < modelSample2Main.getRowCount(); x++){			

					try {
						status = tblSample2Main.getValueAt(x,15).toString().trim();
					} catch (NullPointerException e) {
						status = ""; 
					}

					try {
						rb_id = tblSample2Main.getValueAt(x,1).toString().trim();
					} catch (NullPointerException e) {
						rb_id = "";
					}

					try {
						tra_rec_id = tblSample2Main.getValueAt(x,13).toString().trim();
					} catch (NullPointerException e) {
						tra_rec_id = "0";
					}

					try {
						pay_rec_id = tblSample2Main.getValueAt(x,14).toString().trim();
					} catch (NullPointerException e) {
						pay_rec_id = "0";
					}

					if (tra_rec_id.equals("0")&&pay_rec_id.equals("0")) {
						record_id = "";
					} else if (!tra_rec_id.equals("0")&&pay_rec_id.equals("0")) {
						record_id = tra_rec_id;
					} else if (tra_rec_id.equals("0")&&!pay_rec_id.equals("0")) {
						record_id = pay_rec_id;
					}			

					if (status.equals("A")) {
						updateCRBstatus(rb_id, record_id, db);
					}	
					else {

					}
				}

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"All CRB entries are successfully posted.","", JOptionPane.INFORMATION_MESSAGE);		
				btnPostAll.setEnabled(false);

				/*	Added by Mann2x; Date Added: November 8, 2016; Switching between the actual collection and the Late OR list;	*/
				System.out.println("");
				System.out.println("OR Mode");

				/*
				if (chkActive.isSelected()) {
					displayCDR_ORMode(modelORMain, rowORMain, modelORTotal);
				} else {
					displayCDR(modelSample2Main, rowHeaderSample2Main, modelSample2Total);	
				}
				 */

				display_CDR displayCDR = new display_CDR();
				Thread thread = new Thread(displayCDR);
				thread.start();
			}

			checkIfPosted();	
		} else {
			JOptionPane.showMessageDialog(getContentPane(), "Accounting period for this entry is closed!");
		}
	}

	private void postEntries(){
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Post CRB", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();			
			postCRB_entries(db);				
			db.commit();
			JOptionPane.showMessageDialog(getContentPane(),"CRB entries successfully posted.","", JOptionPane.INFORMATION_MESSAGE);		
			btnPostEntries.setEnabled(false);
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnRefreshCRB.setEnabled(false);

			/*	Added by Mann2x; Date Added: November 8, 2016; Switching between the actual collection and the Late OR list;	*/
			System.out.println("");
			System.out.println("OR Mode");

			/*
			if (chkActive.isSelected()) {
				displayCDR_ORMode(modelORMain, rowORMain, modelORTotal);
			} else {
				displayCDR(modelSample2Main, rowHeaderSample2Main, modelSample2Total);	
			}
			 */
			display_CDR displayCDR = new display_CDR();
			Thread thread = new Thread(displayCDR);
			thread.start(); 

			modelSample5Main.setEditable(false);
			tblSample5Main.setEditable(false);
		}
	}

	private void edit(){

		modelSample5Main.setEditable(true);
		tblSample5Main.setEditable(true);

		btnEdit.setEnabled(false);
		btnSave.setEnabled(true);
		btnPostEntries.setEnabled(false);	
		btnRefreshCRB.setEnabled(true);

		mnidelete.setEnabled(true);
		mniaddrow.setEnabled(true);
		mnicopy.setEnabled(true);
		mnipaste.setEnabled(false);

	}

	private void cancel(){

		lookupProject.setValue("");
		lookupOfficeBranch.setValue("");
		tagBranch.setText("[ ]");
		tagProject.setText("[ ]");

		dteCashierDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

		refresh_table_main();
		refresh_tables();
		refreshAcctInfo();
		enableAcctInfo(false);
		enableButtons(false);

		btnRefreshCDR.setEnabled(true);
		//btnDisplayCDR.setEnabled(true);
		btnCRBentries.setEnabled(false);

		entity_id= "";		
		proj_id  = "";
		branch_id= "";
		rec_id 	= "";
		crb_no 	= "";
		pbl_id 	= "";
		seq_no 	= 0;

		//crb details
		acct_id 	= "";
		acct_name= "";
		debit	= 0.00;
		credit	= 0.00;
		remarks	= "";
		rb_fiscal_yr = "";
		rb_month	= "";
		ar_no	= "";
		proj		= "";
		phase	= "";
		doc_id 	= "";
	}

	private void refreshCDR(){
		enableAcctInfo(true);
		enableButtons(true);
		btnCRBentries.setEnabled(true);

		/*	Added by Mann2x; Date Added: November 8, 2016; Switching between the actual collection and the Late OR list;	*/
		System.out.println("");
		System.out.println("OR Mode");

		if (chkActive.isSelected()) {
			displayCDR_ORMode(modelORMain, rowORMain, modelORTotal);
		} else {
			/*	Original Line	*/
			displayCDR(modelSample2Main, rowHeaderSample2Main, modelSample2Total);
		}

		//btnDisplayCDR.setEnabled(true);
		JOptionPane.showMessageDialog(getContentPane(),"CDR refreshed.","", JOptionPane.INFORMATION_MESSAGE);		
	}

	private void refreshCRB(){

		modelSample5Main.setEditable(false);
		tblSample5Main.setEditable(false);
		displayCRB(modelSample5Main, rowHeaderSample5Main, modelSample5Total, rec_id);
		JOptionPane.showMessageDialog(getContentPane(),"CRB refreshed.","", JOptionPane.INFORMATION_MESSAGE);	

		btnEdit.setEnabled(true);
		btnSave.setEnabled(false);
		btnPostEntries.setEnabled(true);	
		btnRefreshCRB.setEnabled(false);

		mnidelete.setEnabled(false);
		mniaddrow.setEnabled(false);
		mnicopy.setEnabled(false);
		mnipaste.setEnabled(false);
	}

	private void saveCRB(){

		if(checkAcctID_ifcomplete()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please select account.", "Information", 
				JOptionPane.WARNING_MESSAGE);}

		else {	

			if(checkAcctBalanceIfEqual()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Debit and Credit balance totals are not equal.", "Information", 
					JOptionPane.WARNING_MESSAGE);}
			else {	

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save the CRB entries?", "Save", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {


					pgUpdate db = new pgUpdate();						
					Integer pay_rec_id = null;

					if(tblSample2Main.getSelectedRows().length == 1) {
						pay_rec_id = (Integer) modelSample2Main.getValueAt(tblSample2Main.getSelectedRow(), 13);
						if(pay_rec_id == 0) {
							//System.out.printf("value of tra rec id: %s%n", modelSample2Main.getValueAt(tblSample2Main.getSelectedRow(), 14));
							pay_rec_id = (Integer) modelSample2Main.getValueAt(tblSample2Main.getSelectedRow(), 14);
						}
					}
					
					if(tblORMain.getSelectedRows().length == 1) {
						pay_rec_id = (Integer) modelORMain.getValueAt(tblORMain.getSelectedRow(), 13);
						if(pay_rec_id == 0) {
							//System.out.printf("value of tra rec id: %s%n", modelSample2Main.getValueAt(tblSample2Main.getSelectedRow(), 14));
							pay_rec_id = (Integer) modelORMain.getValueAt(tblORMain.getSelectedRow(), 14);
						}
					}
					//pay_rec_id = model
					//get other CRB details neede in saving
					/*setCRBdetails(crb_no, rec_id);

					//saving implementation						
					inactivateOld_CRB(crb_no, rec_id, db);		
					insertNew_CRBdetail(crb_no, rec_id, db);*/

					setCRBdetails(crb_no, String.valueOf(pay_rec_id));

					//saving implementation						
					inactivateOld_CRB(crb_no, String.valueOf(pay_rec_id), db);		
					insertNew_CRBdetail(crb_no, String.valueOf(pay_rec_id), db);

					db.commit();
					JOptionPane.showMessageDialog(getContentPane(),"CRB entries are successfully updated.","", JOptionPane.INFORMATION_MESSAGE);		
					btnPostEntries.setEnabled(true);
					btnEdit.setEnabled(true);
					btnSave.setEnabled(false);
					btnRefreshCRB.setEnabled(false);
					displayCRB(modelSample5Main, rowHeaderSample5Main, modelSample5Total, rec_id);
					modelSample5Main.setEditable(false);
					tblSample5Main.setEditable(false);
				}
			}
		}

	}

	private void previewCRB(){
		String criteria = "Daily CRB Entries";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		String branch   = lookupOfficeBranch.getText();

		if (branch.equals("")||branch.equals(null)) {
			branch = "";
		} else {

		}

		String proj = lookupProject.getText();

		if (proj.equals("") || proj.equals(null)) {
			proj = "";
		} else {

		}

		String phase = lookupPhase.getText();

		if (phase.equals("") || phase.equals(null)) {
			phase = "";
		} else {

		}

		System.out.println("co_id: "+co_id);
		System.out.println("branch: "+branch);
		System.out.println("cdr_date_str: "+df2.format(cashier_Date));
		System.out.println("proj_id: "+proj_id);
		System.out.println("phase: "+phase);

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("branch", branch);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cdr_date", cashier_Date ); //dteCashierDate.getDate()
		mapParameters.put("cdr_date_str", df2.format(cashier_Date)); //df.format(dteCashierDate.getDate()
		mapParameters.put("status", "POSTED");
		mapParameters.put("posted_by", UserInfo.EmployeeCode );
		mapParameters.put("proj_id", proj);
		mapParameters.put("phase", phase);
		FncReport.generateReport("/Reports/rptCRB_daily_preview.jasper", reportTitle, company, mapParameters);		
	}

	private void previewCRB_summary(){

		String criteria2 = "Daily CRB Entries Summary";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());

		Double cdr_amt	= Double.parseDouble(modelSample2Total.getValueAt(0,12).toString());	

		String branch = lookupOfficeBranch.getText();
		String proj = lookupProject.getText();
		String phase = lookupPhase.getText();

		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", company);
		mapParameters2.put("co_id", co_id);
		mapParameters2.put("branch", branch);
		mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters2.put("prepared_by", UserInfo.FullName);
		mapParameters2.put("cdr_date", dteCashierDate.getDate());
		mapParameters2.put("cdr_date_str", df2.format(dteCashierDate.getDate()));
		mapParameters2.put("crb_amt", cdr_amt);
		mapParameters2.put("status", "POSTED");
		mapParameters2.put("posted_by", UserInfo.EmployeeCode );
		mapParameters2.put("proj_id", proj);
		mapParameters2.put("phase", phase);
		mapParameters2.put("user_id", UserInfo.EmployeeCode);
		mapParameters2.put("particulars", txtParticulars.getText());
		//FncReport.generateReport("/Reports/rptCRB_daily_preview_summary.jasper", reportTitle2, company, mapParameters2);		
		FncReport.generateReport("/Reports/rptCRB_daily_preview_summary_v2.jasper", reportTitle2, company, mapParameters2);
	}

	//select statements
	private void clickTableColumn() {
		int column = tblSample5Main.getSelectedColumn();
		int row = tblSample5Main.getSelectedRow();

		if (column == 0 && modelSample5Main.isEditable()) {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Chart of Account", getChartofAccount(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setFilterClientName(true);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelSample5Main.setValueAt(data[0], row, column);
				modelSample5Main.setValueAt(data[1], row, column+1);
			}
		}		

		tblSample5Main.packAll();
		tblSample5Main.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblSample5Main.getColumnModel().getColumn(3).setPreferredWidth(100);
	}

	private String getChartofAccount(){

		String sql = "select " +
				"acct_id as \"Acct ID\", " +
				"trim(acct_name) as \"Acct Name\", " +
				"bs_is as \"Balance\" " +
				"from mf_boi_chart_of_accounts where status_id = 'A' and (w_subacct is null or filtered = false)"; //ADDED FILTER BY LESTER DCRF 2719		
		return sql;

	}

	private Boolean isClientAlreadyReserved(String str1, Integer intgr) {

		Boolean x = false;

		String SQL = 
				"select distinct on (pbl_id, seq_no) * from rf_sold_unit where pbl_id = '"+str1+"' and seq_no= "+intgr+" " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = true;
		}else{}

		return x;
	}

	/*
	private Object [] getUnitID (String rec_id) {

		String SQL = 
			"SELECT pbl_id||' | '||seq_no, pbl_id, seq_no, entity_id " +
			"FROM rf_payments WHERE rec_id = "+rec_id+" \r\n" ;

		System.out.printf("SQL getUnitID: \n" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		
	}
	 */

	private Object [] getAccountInfo1 (String pbl_id, Integer seq_no) {

		String SQL = 
				"--SQL getAccountInfo1 \n" +
						"select b.proj_alias, \n" +
						"c.model_desc, \n" +
						"d.type_alias_too, \n" +
						"g.status_desc, \n" +
						"a.datersvd, \n" +
						"(case when i.status_desc is null then '' else i.status_desc end), \n" + 
						"(case when a.status_id = 'A' then 'ACTIVE' else 'INACTIVE' end), \n" +
						"a.buyertype \n\n" +

			"from ( select distinct on (pbl_id, seq_no) * from rf_sold_unit where pbl_id = '"+pbl_id+"' and seq_no= "+seq_no+" \n" + 
			"		order by  pbl_id, seq_no )  a  \n" + 
			"left join mf_project b on a.projcode = b.proj_id \n" + 
			"left join mf_product_model c on a.model_id=c.model_id \n" + 
			"left join mf_buyer_type d on a.buyertype=d.type_id \n" + 
			"left join (select distinct on (pbl_id, seq_no) byrstatus_id, pbl_id, seq_no  from rf_buyer_status \n" + 
			"		where trim(pbl_id)= '"+pbl_id+"' and seq_no= "+seq_no+" order by pbl_id, seq_no, byrstatus_id desc ) e \n" + 
			"		on a.pbl_id=e.pbl_id and a.seq_no = e.seq_no \n" + 
			"left join mf_buyer_status f on e.byrstatus_id=f.byrstatus_id 	\n" + 
			"left join mf_buyer_status g on a.currentstatus=g.byrstatus_id  \n" + 
			"left join (select distinct on (pbl_id, seq_no) byrstatus_id, pbl_id, seq_no from rf_buyer_status \n" + 
			"		where byrstatus_id in ('18', '20', '21') and pbl_id = '"+pbl_id+"' and seq_no= "+seq_no+" order by pbl_id, seq_no, tran_date desc) h \n" + 
			"		on a.pbl_id=h.pbl_id and a.seq_no=h.seq_no \n" + 
			"left join mf_buyer_status i on h.byrstatus_id=i.byrstatus_id \n\n" +

			"where a.pbl_id =  '"+pbl_id+"' and a.seq_no = "+seq_no+"	\n\n" ;

		System.out.printf("SQL getAccountInfo1: \n" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		
	}	

	private Object [] getPbgPmtSchemeDetails (String pbl_id, Integer seq_no) {
		/*	Modified by Mann2x; Date Modified: February 28, 2017; Just so payment scheme details for bank financed accounts would show;
		String SQL = 
			"--SQL getPbgPmtSchemeDetails \n" +
			"select a.gross_sprice, \n" +
			"a.discount,a.vat, \n" +
			"a.vat_rate, \n" +
			"a.net_sprice, \n" + 			
			"c.pmt_scheme_desc, \n" +
			"coalesce(b.dp_term,0), \n" +
			"coalesce(b.term,0), \n" +
			"coalesce(b.dp,0), \n" +
			"coalesce(b.total_ma,0) \n\n" + 

			"from rf_client_price_history a  \n" + 
			"left join rf_pagibig_computation b on a.pbl_id=b.pbl_id and a.seq_no=b.seq_no \n" + 
			"left join rf_sold_unit d on b.pbl_id = d.pbl_id and b.seq_no=d.seq_no  \n" + 
			"left join mf_payment_scheme c on d.pmt_scheme_id::int = c.pmt_scheme_id::int  \n\n" + 

			"where a.pbl_id = '"+pbl_id+"'  \n" +
			"and a.seq_no = "+seq_no+"  \n" + 
			"and b.pbl_id = '"+pbl_id+"' \n" +
			"and b.seq_no = "+seq_no+" \n" + 
			"and b.status_id = 'A' \n" +
			"and a.status_id = 'A' \n\n" +

			"order by a.tran_date desc \n\n";
		 */
		String SQL = "select * from ( \n" +
				"select a.gross_sprice, a.discount,a.vat, a.vat_rate, a.net_sprice, c.pmt_scheme_desc, \n" + 
				"coalesce(b.dp_term,0), coalesce(b.term,0), coalesce(b.dp,0), coalesce(b.total_ma,0), a.tran_date \n" +
				"from rf_client_price_history a \n" +
				"left join rf_pagibig_computation b on a.pbl_id=b.pbl_id and a.seq_no=b.seq_no \n" + 
				"left join rf_sold_unit d on b.pbl_id = d.pbl_id and b.seq_no=d.seq_no \n" +
				"left join mf_payment_scheme c on d.pmt_scheme_id::int = c.pmt_scheme_id::int \n" +
				"where a.pbl_id = '"+pbl_id+"' and a.seq_no = "+seq_no+" and b.pbl_id = '"+pbl_id+"' and b.seq_no = "+seq_no+" and b.status_id = 'A' and a.status_id = 'A' \n" + 
				"union ALL \n" +
				"select a.gross_sprice, a.discount,a.vat, a.vat_rate, a.net_sprice, c.pmt_scheme_desc, \n" + 
				"coalesce(b.dp_term,0), coalesce(b.term,0), coalesce(b.dp,0), coalesce(b.total_ma,0), a.tran_date \n" +
				"from rf_client_price_history a \n" +
				"left join rf_bank_finance_computation b on a.pbl_id=b.pbl_id and a.seq_no=b.seq_no \n" + 
				"left join rf_sold_unit d on b.pbl_id = d.pbl_id and b.seq_no=d.seq_no \n" +
				"left join mf_payment_scheme c on d.pmt_scheme_id::int = c.pmt_scheme_id::int \n" +
				"where a.pbl_id = '"+pbl_id+"' and a.seq_no = "+seq_no+" and b.pbl_id = '"+pbl_id+"' and b.seq_no = "+seq_no+" and b.status_id = 'A' and a.status_id = 'A' \n" + 
				") a order by a.tran_date desc";

		System.out.printf("SQL getPbgPmtSchemeDetails: \n" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		

	}	

	private Object [] getIHFSchemeDetails (String pbl_id, Integer seq_no) {

		String SQL = 
				"--SQL getIHFSchemeDetails \n" + 
						"select a.gross_sprice, \n" +
						"a.discount, \n" +
						"a.vat, \n" +
						"a.vat_rate, \n" +
						"a.net_sprice, \n" +
						"c.pmt_scheme_desc, \n" +
						"coalesce(b.dp_term,0), \n" +
						"coalesce(b.ma_term,0), \n" +
						"coalesce(b.dp,0), \n" +
						"coalesce(b.total_ma,0) \n\n" + 

			"from rf_client_price_history a \n" + 
			"left join rf_ihf_computation b on a.pbl_id=b.pbl_id and a.seq_no=b.seq_no \n" + 
			"left join mf_payment_scheme c on b.pmt_scheme_id::int = c.pmt_scheme_id::int  \n" + 

			"where a.pbl_id = '"+pbl_id+"' \n" +
			"and a.seq_no = "+seq_no+" \n" + 
			"and b.pbl_id = '"+pbl_id+"' \n" +
			"and b.seq_no = "+seq_no+" \n" + 
			"and  b.status_id = 'A' \n" +
			"and a.status_id = 'A' \n\n" +

			"order by a.tran_date desc \n\n" ;

		System.out.printf("SQL getIHFSchemeDetails: \n" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		

	}

	private Object [] getSpotCashDetails (String pbl_id, Integer seq_no) {

		String SQL = 
				"--SQL getSpotCashDetails: \n" +
						"select coalesce(a.gross_sprice,0), \n" +
						"coalesce(a.discount,0), \n" +
						"coalesce(a.vat,0), \n" +
						"coalesce(a.vat_rate,0), \n" +
						"coalesce(a.net_sprice), \n" + 			
						"c.pmt_scheme_desc \n\n" + 

			"from rf_client_price_history a   \n" + 
			"left join rf_sold_unit d on a.pbl_id = d.pbl_id and a.seq_no=d.seq_no 	\n" + 
			"left join mf_payment_scheme c on d.pmt_scheme_id::int = c.pmt_scheme_id::int  \n\n" + 

			"where a.pbl_id = '"+pbl_id+"' \n" +
			"and a.seq_no = "+seq_no+"	\n" + 
			"and a.status_id = 'A' \n\n" +

			"order by a.tran_date desc  \n\n" ;

		System.out.printf("SQL getSpotCashDetails: \n" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		

	}

	private Object [] getCRB_details (String receipt_no, String rec_id) {

		/*String SQL = 
			"--SQL getCRB_details: \n" +
			"select " +   	
			"rb_fiscal_year, " +  //0
			"rb_month, " +	//1
			"ar_no, " +		//2
			"proj_id, " +	//3
			"phase, " +		//4
			"doc_id " +		//5
			"from (select distinct on (rb_id) * from rf_crb_detail) a " +
			"where rb_id = '"+receipt_no+"' " +
			"and trim(pay_rec_id) = '"+rec_id+"'\n\n" ;*/

		String SQL = "select rb_fiscal_year, rb_month, ar_no, proj_id, phase, doc_id from rf_crb_detail where rb_id = '"+receipt_no+"' and trim(pay_rec_id) = '"+rec_id+"' LIMIT 1";

		System.out.printf("SQL getSpotCashDetails: \n" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}		
	}

	private void checkIfPosted() {
		Boolean blnPosted = false;
		for (int x = 0; x < modelSample2Main.getRowCount(); x++) {
			System.out.println("modelSample2Main.getValueAt(x, 15): "+modelSample2Main.getValueAt(x, 15));
			if ((Boolean) modelSample2Main.getValueAt(x, 15).equals("A")) {
				blnPosted = true;
			}
		}

		btnPostAll.setEnabled(blnPosted);
		btnPostEntries.setEnabled(blnPosted);
		/*
		String status = "";

		for (int x=0; x < modelSample2Main.getRowCount(); x++) {
		 */
		/*	Modified by Mann2x; Modified date: November 8, 2016; Index 12 is "other amount" and status id index 15	*/
		/*status = modelSample2Main.getValueAt(x,12).toString().trim();*/

		/*
			status = modelSample2Main.getValueAt(x, 15).toString().trim();

			System.out.println("");
			System.out.println("status: " + status);

			if (status.equals("A")) {
				break;
			} else {
				btnPostEntries.setEnabled(false);
				btnEdit.setEnabled(false);
				btnCRBentries.setEnabled(false);

				System.out.println("");
				System.out.println("btnPostEntries.isEnabled(): " + btnPostEntries.isEnabled());
				System.out.println("btnEdit.isEnabled(): " + btnEdit.isEnabled());
				System.out.println("btnCRBentries.isEnabled(): " + btnCRBentries.isEnabled());
			}
		}
		 */
	}

	private void totalEntries2(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal total_check_amt 	= new BigDecimal(0.00);	
		BigDecimal total_cash_amt 	= new BigDecimal(0.00);	
		BigDecimal total_other_amt 	= new BigDecimal(0.00);	
		BigDecimal total_amt 		= new BigDecimal(0.00);		

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try { total_check_amt 	= total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 9)));} 
			catch (NullPointerException e) { total_check_amt 	= total_check_amt.add(new BigDecimal(0.00)); }

			try { total_cash_amt 	= total_cash_amt.add(((BigDecimal) modelMain.getValueAt(x, 10)));} 
			catch (NullPointerException e) { total_cash_amt 	= total_cash_amt.add(new BigDecimal(0.00)); }

			try { total_other_amt 	= total_other_amt.add(((BigDecimal) modelMain.getValueAt(x, 11)));} 
			catch (NullPointerException e) { total_other_amt 	= total_other_amt.add(new BigDecimal(0.00)); }		

			try { total_amt 	= total_amt.add(((BigDecimal) modelMain.getValueAt(x, 12)));} 
			catch (NullPointerException e) { total_amt 	= total_amt.add(new BigDecimal(0.00)); }		

		}		
		modelTotal.addRow(new Object[] { "Total",  null, null, null, null, null, null, null, null, total_check_amt, total_cash_amt, total_other_amt, total_amt, null });	
	}	

	private void totalEntries3(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal amt_paid 	= new BigDecimal(0.00);	
		BigDecimal other_fees 	= new BigDecimal(0.00);	
		BigDecimal proc_fees 	= new BigDecimal(0.00);	
		BigDecimal res 			= new BigDecimal(0.00);
		BigDecimal dp 			= new BigDecimal(0.00);
		BigDecimal mri 			= new BigDecimal(0.00);
		BigDecimal fi 			= new BigDecimal(0.00);
		BigDecimal maf 			= new BigDecimal(0.00);
		BigDecimal vat 			= new BigDecimal(0.00);
		BigDecimal soi 			= new BigDecimal(0.00);
		BigDecimal sop 			= new BigDecimal(0.00);
		BigDecimal cbp 			= new BigDecimal(0.00);
		BigDecimal adjus 		= new BigDecimal(0.00);
		BigDecimal interest		= new BigDecimal(0.00);
		BigDecimal principal	= new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){
			try { amt_paid 	= amt_paid.add(((BigDecimal) modelMain.getValueAt(x, 2)));} 
			catch (NullPointerException e) { amt_paid 	= amt_paid.add(new BigDecimal(0.00)); }

			try { other_fees 	= other_fees.add(((BigDecimal) modelMain.getValueAt(x, 3)));} 
			catch (NullPointerException e) { other_fees 	= other_fees.add(new BigDecimal(0.00)); }

			try { proc_fees 	= proc_fees.add(((BigDecimal) modelMain.getValueAt(x, 4)));} 
			catch (NullPointerException e) { proc_fees 	= proc_fees.add(new BigDecimal(0.00)); }

			try { res 	= res.add(((BigDecimal) modelMain.getValueAt(x, 5)));} 
			catch (NullPointerException e) { res 	= res.add(new BigDecimal(0.00)); }

			try { dp 	= dp.add(((BigDecimal) modelMain.getValueAt(x, 6)));} 
			catch (NullPointerException e) { dp 	= dp.add(new BigDecimal(0.00)); }

			try { mri 	= mri.add(((BigDecimal) modelMain.getValueAt(x, 7)));} 
			catch (NullPointerException e) { mri 	= mri.add(new BigDecimal(0.00)); }

			try { fi 	= fi.add(((BigDecimal) modelMain.getValueAt(x, 8)));} 
			catch (NullPointerException e) { fi 	= fi.add(new BigDecimal(0.00)); }

			try { maf 	= maf.add(((BigDecimal) modelMain.getValueAt(x, 9)));} 
			catch (NullPointerException e) { maf 	= maf.add(new BigDecimal(0.00)); }

			try { vat 	= vat.add(((BigDecimal) modelMain.getValueAt(x, 10)));} 
			catch (NullPointerException e) { vat 	= vat.add(new BigDecimal(0.00)); }

			try { soi 	= soi.add(((BigDecimal) modelMain.getValueAt(x, 11)));} 
			catch (NullPointerException e) { soi 	= soi.add(new BigDecimal(0.00)); }

			try { sop 	= sop.add(((BigDecimal) modelMain.getValueAt(x, 12)));} 
			catch (NullPointerException e) { sop 	= sop.add(new BigDecimal(0.00)); }

			try { cbp 	= cbp.add(((BigDecimal) modelMain.getValueAt(x, 13)));} 
			catch (NullPointerException e) { cbp 	= cbp.add(new BigDecimal(0.00)); }

			try { adjus 	= adjus.add(((BigDecimal) modelMain.getValueAt(x, 14)));} 
			catch (NullPointerException e) { adjus 	= adjus.add(new BigDecimal(0.00)); }

			try { interest 	= interest.add(((BigDecimal) modelMain.getValueAt(x, 15)));} 
			catch (NullPointerException e) { interest 	= interest.add(new BigDecimal(0.00)); }

			try { principal 	= principal.add(((BigDecimal) modelMain.getValueAt(x, 16)));} 
			catch (NullPointerException e) { principal 	= principal.add(new BigDecimal(0.00)); }

		}		
		modelTotal.addRow(new Object[] {  "Total",null, amt_paid, other_fees, proc_fees, res, dp, mri, fi, maf, vat, soi, sop, cbp, adjus, interest, principal, null });	




	}	

	private void totalEntries5(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal debit 		= new BigDecimal(0.00);	
		BigDecimal credit 		= new BigDecimal(0.00);	

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try { debit 	= debit.add(((BigDecimal) modelMain.getValueAt(x, 2)));} 
			catch (NullPointerException e) { debit 	= debit.add(new BigDecimal(0.00)); }

			try { credit 	= credit.add(((BigDecimal) modelMain.getValueAt(x, 3)));} 
			catch (NullPointerException e) { credit = credit.add(new BigDecimal(0.00)); }
		}		
		modelTotal.addRow(new Object[] { "Total", null, debit, credit, null });	

	}

	private void totalEntries4(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal amount 		= new BigDecimal(0.00);	
		BigDecimal other_fees 	= new BigDecimal(0.00);	
		BigDecimal mri 			= new BigDecimal(0.00);	
		BigDecimal fi 			= new BigDecimal(0.00);
		BigDecimal maf 			= new BigDecimal(0.00);
		BigDecimal vat 			= new BigDecimal(0.00);
		BigDecimal interest		= new BigDecimal(0.00);
		BigDecimal principal	= new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){
			try { amount 	= amount.add(((BigDecimal) modelMain.getValueAt(x, 2)));} 
			catch (NullPointerException e) { amount 	= amount.add(new BigDecimal(0.00)); }

			try { other_fees 	= other_fees.add(((BigDecimal) modelMain.getValueAt(x, 3)));} 
			catch (NullPointerException e) { other_fees 	= other_fees.add(new BigDecimal(0.00)); }

			try { mri 	= mri.add(((BigDecimal) modelMain.getValueAt(x, 4)));} 
			catch (NullPointerException e) { mri 	= mri.add(new BigDecimal(0.00)); }

			try { fi 	= fi.add(((BigDecimal) modelMain.getValueAt(x, 5)));} 
			catch (NullPointerException e) { fi 	= fi.add(new BigDecimal(0.00)); }

			try { maf 	= maf.add(((BigDecimal) modelMain.getValueAt(x, 6)));} 
			catch (NullPointerException e) { maf 	= maf.add(new BigDecimal(0.00)); }

			try { vat 	= vat.add(((BigDecimal) modelMain.getValueAt(x, 7)));} 
			catch (NullPointerException e) { vat 	= vat.add(new BigDecimal(0.00)); }

			try { interest 	= interest.add(((BigDecimal) modelMain.getValueAt(x, 8)));} 
			catch (NullPointerException e) { interest 	= interest.add(new BigDecimal(0.00)); }

			try { principal 	= principal.add(((BigDecimal) modelMain.getValueAt(x, 9)));} 
			catch (NullPointerException e) { principal 	= principal.add(new BigDecimal(0.00)); }
		}		
		modelTotal.addRow(new Object[] { "Total", null, amount, other_fees, mri, fi, maf, vat, interest, principal, null });	

	}

	private void adjustRowHeight(_JTableMain tbl){

		int rw = tbl.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tbl.setRowHeight(x, 22);			
			x++;
		}
	}

	private void computeCRB_amount(){

		int rw = tblSample5Main.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			Double debit	= Double.parseDouble(tblSample5Main.getValueAt(x,2).toString());	
			Double credit	= Double.parseDouble(tblSample5Main.getValueAt(x,3).toString());

			BigDecimal debit_bd 		= new BigDecimal(debit);		
			BigDecimal credit_bd 		= new BigDecimal(credit);

			tblSample5Main.setValueAt(debit_bd, x, 2);
			tblSample5Main.setValueAt(credit_bd, x, 3);
			x++;
		}
		totalEntries5(modelSample5Main, modelSample5Total);
	}


	//check and validate		
	private Boolean checkAcctID_ifcomplete(){

		boolean x = true;		

		int rw = tblSample5Main.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	

			Double debit 	= 0.00;	
			Double credit 	= 0.00;

			try { debit 	= Double.parseDouble(tblSample5Main.getValueAt(w,2).toString());;} 
			catch (NullPointerException e) { debit = 0.00; }

			try { credit 	= Double.parseDouble(tblSample5Main.getValueAt(w,3).toString());;} 
			catch (NullPointerException e) { credit = 0.00; }

			Double total 	= debit + credit;

			if (total>0) {

				String acct_id = tblSample5Main.getValueAt(w,0).toString().trim();
				if(acct_id.equals("")){x=false; break;} else {}

			} else {}		
			w++;
		}
		return x;

	}	

	private Boolean checkAcctBalanceIfEqual(){

		boolean x = true;

		Double debit 	= Double.parseDouble(tblSample5Total.getValueAt(0,2).toString());	
		Double credit 	= Double.parseDouble(tblSample5Total.getValueAt(0,3).toString());	

		Double diff		= debit - credit ;

		if (diff!=0) { x=false; } 
		else {x=true;}		

		return x;

	}	


	//save and insert	
	private void postCRB_entries(pgUpdate db) {

		String sqlDetail = 
				"--postCRB_entries \n" +
						"UPDATE rf_crb_header " +
						"set status_id = 'P',   \n"  +	
						"posted_by = '"+UserInfo.EmployeeCode+"', " +
						"posted_date = now() \n"  +
						"where trim(pay_rec_id) = '"+rec_id+"' " +
						"and trim(rb_id) = '"+crb_no+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void updateCRBstatus(String receipt_no, String record_id, pgUpdate db) {

		String sqlDetail = "UPDATE rf_crb_header set " +
				"status_id = 'P', posted_by = '"+UserInfo.EmployeeCode+"', posted_date= now() \n"  +
				"where trim(rb_id) = '"+receipt_no+"' and trim(pay_rec_id) = '"+record_id+"' and status_id != 'I'" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}

	private void inactivateOld_CRB(String receipt_no, String record_id, pgUpdate db) {

		String sqlDetail = 

				"--inactivateOld_CRB \n" +
						"UPDATE rf_crb_detail set \n" +
						"status_id = 'I'  \n" +
						"where trim(rb_id) = '"+receipt_no+"' \n" +
						"and trim(pay_rec_id) = '"+record_id+"' \n\n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);				
	}

	private void insertNew_CRBdetail(String receipt_no, String record_id, pgUpdate db){

		int x  = 0;	
		int y  = 1;
		int rw = tblSample5Main.getModel().getRowCount();	

		while(x < rw ) {	

			String account_id 	= tblSample5Main.getValueAt(x,0).toString();
			String remarks 		= tblSample5Main.getValueAt(x,4).toString().replace("'","''").trim();

			Double debit 		= Double.parseDouble(tblSample5Main.getValueAt(x,2).toString());			
			Double credit 		= -1*Double.parseDouble(tblSample5Main.getValueAt(x,3).toString());			
			Double trans_amt	= debit + credit;

			if (trans_amt == 0.00) {}
			else
			{	
				String sqlDetail = 
						"INSERT into rf_crb_detail values (" +
								"'"+receipt_no+"',  \n" +  	//1 rb_id
								"'"+entity_id+"',  \n" +  	//2 entity_id
								"'"+pbl_id+"',  \n" +  		//3 pbl_id
								"'"+seq_no+"',  \n" +  		//4	seq_no	
								""+y+",  \n" +  			//5	line_no		
								"'"+account_id+"',  \n" +  	//6 acct_id
								""+trans_amt+",  \n" +  	//7 trans_amt
								"'"+rb_fiscal_yr+"',  \n" + //8 rb_fiscal_year
								"'"+rb_month+"',  \n" ; 	//9 rb_month

				if(ar_no==null) {sqlDetail = sqlDetail + "null,  \n" ; }  		//10 ar_no
				else {sqlDetail = sqlDetail + "'"+ar_no+"',  \n" ; }		//10 ar_no
				sqlDetail = sqlDetail +

						"'"+remarks+"',  \n" +  	//11 remarks
						"'"+record_id+"',  \n" +  	//12 pay_rec_id
						"'"+proj+"',  \n" +  	//13 proj_id
						"'"+phase+"',  \n" +  		//14 phase
						"'"+co_id+"',  \n" +  		//15 co_id
						"'"+doc_id+"',  \n" +  		//16 doc_id					
						"'A'  \n" + 				//17 status_id
						")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);					
				y++;
			}	

			x++;
		}
	}

	private void setCRBdetails(String receipt_no, String record_id){
		Object[] pmt_sch = getCRB_details(receipt_no, record_id);	

		try { rb_fiscal_yr 	= (String) pmt_sch[0].toString(); } catch (NullPointerException e) { rb_fiscal_yr = ""; }
		try { rb_month 		= (String) pmt_sch[1].toString(); } catch (NullPointerException e) { rb_month = ""; }
		try { ar_no 		= (String) pmt_sch[2].toString(); } catch (NullPointerException e) { ar_no = null; }
		try { proj 			= (String) pmt_sch[3].toString(); } catch (NullPointerException e) { proj = ""; }
		try { phase 		= (String) pmt_sch[4].toString(); } catch (NullPointerException e) { phase = ""; }
		try { doc_id 		= (String) pmt_sch[5].toString(); } catch (NullPointerException e) { doc_id = ""; }
	}


	private void removeRow(){		

		int r  = tblSample5Main.getSelectedRow();

		if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblSample5Main.getModel()).removeRow(r);  
			modelSample5Main.addRow(new Object[] { "", "",  new BigDecimal(0.00), new BigDecimal(0.00), "" });	
			adjustRowHeight(tblSample5Main);
		}

		totalEntries5(modelSample5Main, modelSample5Total);
	}

	private void AddRow(){	

		modelSample5Main.addRow(new Object[] { "", "",  new BigDecimal(0.00), new BigDecimal(0.00), "" });			
		((DefaultListModel) rowHeaderSample5Main.getModel()).addElement(modelSample5Main.getRowCount());
		adjustRowHeight(tblSample5Main);
		totalEntries5(modelSample5Main, modelSample5Total);
	}

	private void copyRow(){
		int row 	= tblSample5Main.getSelectedRow();	
		acct_id 	= tblSample5Main.getValueAt(row,0).toString().trim();
		acct_name 	= tblSample5Main.getValueAt(row,1).toString().trim();
		remarks 	= tblSample5Main.getValueAt(row,4).toString().trim();
		debit	 	= Double.parseDouble(tblSample5Main.getValueAt(row,2).toString().trim());
		credit	 	= Double.parseDouble(tblSample5Main.getValueAt(row,3).toString().trim());		
		mnipaste.setEnabled(true);
	}

	private void pasteRow(){

		int row 	= tblSample5Main.getSelectedRow();		

		modelSample5Main.setValueAt(acct_id, row, 0); 
		modelSample5Main.setValueAt(acct_name, row, 1); 
		tblSample5Main.setValueAt(new BigDecimal(debit), row, 2); 
		tblSample5Main.setValueAt(new BigDecimal(credit), row, 3); 
		tblSample5Main.setValueAt(remarks, row, 4); 

		tblSample5Main.packAll();
		adjustRowHeight(tblSample5Main);
		tblSample5Main.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblSample5Main.getColumnModel().getColumn(3).setPreferredWidth(100);
		totalEntries5(modelSample5Main, modelSample5Total);

	}	

	private String sqlPhase(String strProject) {
		return "select x.phase, x.sub_proj_name \n" +
				"from mf_sub_project x \n" +
				"where x.phase in (select distinct a.phase from mf_unit_info a) \n" +
				"and (proj_id = '"+strProject+"' or '"+strProject+"' = 'null') \n" +
				"and status_id = 'A' \n" +
				"order by x.phase";
	}

	private void Buttons(Boolean blnDo) {
		if (blnDo) {
			btnPostAll.setEnabled(blnPostAll);
			btnRefreshCDR.setEnabled(blnRefreshCDR);
			btnCancel.setEnabled(blnCancel);
			btnEdit.setEnabled(blnEdit);
			btnPostEntries.setEnabled(blnPostEntries);
			btnRefreshCRB.setEnabled(blnRefreshCRB);
			btnSave.setEnabled(blnSave);
			btnCRBentries.setEnabled(blnCRBentries);
		} else {
			blnPostAll = btnPostAll.isEnabled();
			blnRefreshCDR = btnRefreshCDR.isEnabled();
			blnCancel = btnCancel.isEnabled();
			blnEdit = btnEdit.isEnabled();
			blnPostEntries = btnPostEntries.isEnabled();
			blnRefreshCRB = btnRefreshCRB.isEnabled();
			blnSave = btnSave.isEnabled();
			blnCRBentries = btnCRBentries.isEnabled();

			btnPostAll.setEnabled(false);
			btnRefreshCDR.setEnabled(false);
			btnCancel.setEnabled(false);
			btnEdit.setEnabled(false);
			btnPostEntries.setEnabled(false);
			btnRefreshCRB.setEnabled(false);
			btnSave.setEnabled(false);
			btnCRBentries.setEnabled(false);
		}
	}

	private class display_CDR implements Runnable {

		public void run() {
			System.out.println("");
			System.out.println("OR Mode");

			Buttons(false);
			FncGlobal.startProgress("Please wait..");

			String branch = lookupOfficeBranch.getText();
			String proj = lookupProject.getText();
			String phase = lookupPhase.getText();

			System.out.println("");
			System.out.println("branch: " + branch);
			System.out.println("proj: " + proj);
			System.out.println("phase: " + phase);

			pgSelect dbExec = new pgSelect(); 
			dbExec.select("select * from view_crb_report_summary_v3('"+co_id+"', '"+proj+"', '"+branch+"', '"+phase+"', '"+dteCashierDate.getDate().toString()+"', '"+UserInfo.EmployeeCode+"') x");

			displayCDR(modelSample2Main, rowHeaderSample2Main, modelSample2Total);
			displayCDR_ORMode(modelORMain, rowORMain, modelORTotal);


			FncGlobal.stopProgress();
			Buttons(true);

			enableAcctInfo(true);
			enableButtons(true);

			checkIfPosted();
			btnCRBentries.setEnabled(true);
		}

	}

	private void displayCDR(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {
		if (dteCashierDate.getDate()==null) {
			cashier_Date = FncGlobal.dateFormat(FncGlobal.getDateSQL());
		} else {
			cashier_Date = dteCashierDate.getDate(); 
		}		

		FncTables.clearTable(modelMain);						//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();	//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);							//Setting of DefaultListModel into rowHeader.		

		String strCo = lookupCompany.getText();
		String strPro = lookupProject.getText();
		String strBrch = lookupOfficeBranch.getText();
		String strPhase = lookupPhase.getText();

		/*	Modified by Mann2x; Modified Date: January 6, 2017; Transferred the code into SQL function for ease of modification; Original block was removed;	*/
		/*	String sql = "select * from view_crb_posting ('"+strCo+"', '"+strPro+"', '"+strBrch+"', '"+strPhase+"', '"+dteCashierDate.getDate().toString()+"')";	*/
		/*	System.out.printf("SQL: %s%n", sql);	*/

		String sql = "";
		if (cmbFilterby.getSelectedIndex()==2) {
			sql = "select * from view_crb_posting_ordate ('"+strCo+"', '"+strPro+"', '"+strBrch+"', '"+strPhase+"', '"+dteCashierDate.getDate().toString()+"')";
		} else {
			sql = "select * from view_crb_posting ('"+strCo+"', '"+strPro+"', '"+strBrch+"', '"+strPhase+"', '"+dteCashierDate.getDate().toString()+"')";
		}

		System.out.println("");
		System.out.println("SQL "+sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {

				try {
					modelMain.addRow(db.getResult()[x]);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}

				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries2(modelMain, modelTotal);
		} else {
			//JOptionPane.showMessageDialog(getContentPane(),"No payment for the selected parameters.","Information",JOptionPane.WARNING_MESSAGE);
			enableButtons(false);

			modelSample2Total = new modelByrPmtPosting_CDR();
			modelSample2Total.addRow(new Object[] { "Total", null,  null, null, null, null, null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null });	
			tblSample2Total = new _JTableTotal(modelSample2Total, tblSample2Main);
			scrollSample2Total.setViewportView(tblSample2Total);
			tblSample2Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample2Total).setTotalLabel(0);
		}

		tblSample2Main.packAll();
		checkIfPosted();
		adjustRowHeight(tblSample2Main);
	}

	private void displayCDR_ORMode(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {
		if (dteCashierDate.getDate()==null)	{
			cashier_Date = FncGlobal.dateFormat(FncGlobal.getDateSQL());
		} else {
			cashier_Date = dteCashierDate.getDate(); 
		}		

		FncTables.clearTable(modelMain);						//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();	//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);							//Setting of DefaultListModel into rowHeader.		

		String strCo = lookupCompany.getText();
		String strPro = lookupProject.getText();
		String strBrch = lookupOfficeBranch.getText();
		String strPhase = lookupPhase.getText();

		/*	Modified by Mann2x; Modified Date: May 11, 2017; Transferred the code into SQL function for ease of modification; Original block was removed;	*/
		/*	String sql = "select * from view_crb_posting_or ('"+strCo+"', '"+strPro+"', '"+strBrch+"', '"+strPhase+"', '"+dteCashierDate.getDate().toString()+"')";	*/
		/*	System.out.printf("SQL: %s%n", sql);	*/

		/*	Modidied by Mann2x; Date Modified: August 30, 2017; Added OR date filter;	*/
		/*	Modidied by Mann2x; Date Modified: December 13, 2017; Added OR date filter;	*/

		String sql = "";
		/*
		if (cmbFilterby.getSelectedIndex()==2) {
			sql = "select * from view_crb_posting_or_ordate ('"+strCo+"', '"+strPro+"', '"+strBrch+"', '"+strPhase+"', '"+dteCashierDate.getDate().toString()+"')";
		} else {
			sql = "select * from view_crb_posting_or ('"+strCo+"', '"+strPro+"', '"+strBrch+"', '"+strPhase+"', '"+dteCashierDate.getDate().toString()+"')";
		}
		 */

		sql = "select * from view_crb_posting_or_ordate ('"+strCo+"', '"+strPro+"', '"+strBrch+"', '"+strPhase+"', '"+dteCashierDate.getDate().toString()+"')";

		System.out.printf("SQL: %s%n", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries2(modelMain, modelTotal);
		} else {
			//JOptionPane.showMessageDialog(getContentPane(),"No payment for the selected parameters.","Information",JOptionPane.WARNING_MESSAGE);
			enableButtons(false);

			modelORTotal = new modelByrPmtPosting_CDR_LateOR();
			modelORTotal.addRow(new Object[] { "Total", null,  null, null, null, null, null,null,null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null });	
			tblORTotal = new _JTableTotal(modelORTotal, tblORMain);
			scrollORTotal.setViewportView(tblORTotal);
			tblORTotal.setFont(dialog11Bold);
			((_JTableTotal) tblORTotal).setTotalLabel(0);
		}

		tblORMain.packAll();
		checkIfPosted();
		adjustRowHeight(tblORMain);
	}

	private class preview_crb_detailed extends Thread {

		public void run() {
			btnCRBentries.setEnabled(false);
			FncGlobal.startProgress("Please wait..");

			previewCRB(); 
		}

	}

	private class preview_crb_detailed_or extends Thread {

		public void run() {
			btnCRBentries.setEnabled(false);
			FncGlobal.startProgress("Please wait..");

			if (modelORMain.getRowCount()>0) {
				previewCRB_LateOR();	
			}

		}

	}

	private class preview_crb_summary extends Thread {

		public void run() {
			System.out.print("I went here summary");

			btnCRBentries.setEnabled(false);
			FncGlobal.startProgress("Please wait..");
			previewCRB_summary();

		}

	}

	private class preview_crb_summary_or extends Thread {

		public void run() {
			btnCRBentries.setEnabled(false);
			FncGlobal.startProgress("Generating Late OR CRB Summary..");

			if (modelORMain.getRowCount()>0) {
				previewCRB_summary_LateOR();	
			}

			//btnCRBentries.setEnabled(true);
			//FncGlobal.stopProgress();
		}

	}

	private void previewCRB_LateOR(){

		String criteria = "Daily CRB Entries For Late OR";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		//Double cdr_amt	= Double.parseDouble(modelSample2Total.getValueAt(0,12).toString());	
		String branch   = lookupOfficeBranch.getText();
		if (branch.equals("")||branch.equals(null)) {branch = "All";} 
		else {}

		String proj   = lookupProject.getText();
		if (proj.equals("")||proj.equals(null)) {proj = "All";} 
		else {}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("branch", branch);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cdr_date", cashier_Date ); 					//dteCashierDate.getDate()
		mapParameters.put("cdr_date_str", df2.format(cashier_Date)); 	//df.format(dteCashierDate.getDate()
		mapParameters.put("status", "POSTED");
		mapParameters.put("posted_by", UserInfo.EmployeeCode );
		mapParameters.put("proj_id", proj);
		FncReport.generateReport("/Reports/rptCRB_daily_preview_LateOR.jasper", reportTitle, company, mapParameters);		
	}

	/*	Created by Mann2x; Date Created: November 3, 2016; To separate the entries of Late ORs; Pending completion; Subject for checking;	*/
	private void previewCRB_summary_LateOR() {
		String criteria2 = "Daily CRB Entries Summary For Late OR";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());

		Double cdr_amt	= Double.parseDouble(modelSample2Total.getValueAt(0,12).toString());	

		String branch = lookupOfficeBranch.getText();
		String proj = lookupProject.getText();
		String phase = lookupPhase.getText();

		System.out.println("");
		System.out.println("branch: " + branch);
		System.out.println("proj: " + proj);
		System.out.println("phase: " + phase);

		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", company);
		mapParameters2.put("co_id", co_id);
		mapParameters2.put("branch", branch);
		mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters2.put("prepared_by", UserInfo.FullName);
		mapParameters2.put("cdr_date", dteCashierDate.getDate());
		mapParameters2.put("cdr_date_str", df2.format(dteCashierDate.getDate()));
		mapParameters2.put("crb_amt", cdr_amt);
		mapParameters2.put("status", "POSTED");
		mapParameters2.put("posted_by", UserInfo.EmployeeCode );
		mapParameters2.put("proj_id", proj);
		mapParameters2.put("phase", phase);
		
		System.out.println("company: " + company);
		System.out.println("co_id: " + co_id);
		System.out.println("branch: " + branch);
		System.out.println("logo: " + this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		System.out.println("prepared_by: " + UserInfo.FullName);
		System.out.println("cdr_date: " + dteCashierDate.getDate());
		System.out.println("cdr_date_str: " + df2.format(dteCashierDate.getDate()));
		System.out.println("crb_amt: " + cdr_amt);
		System.out.println("status: " + "POSTED");
		System.out.println("posted_by: " + UserInfo.EmployeeCode );
		System.out.println("proj_id: " + proj);
		System.out.println("phase: " + phase);
		FncReport.generateReport("/Reports/rptCRB_daily_preview_summary_LateOR.jasper", reportTitle2, company, mapParameters2);		
	}

	private void displayCRB(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String rec_id) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		

		String sql = 	

				"--displayCRB \n" +
						"select * from ( \n" +

			"--#1 \n" +
			"select trim(a.acct_id) as acct_id, \n" +
			"trim(d.acct_name), \n" + 
			"(case when a.trans_amt<0 then 0.00 else a.trans_amt end) as debit, \n" + 
			"(case when a.trans_amt>0 then 0.00 else -1*a.trans_amt end) as credit, \n" + 
			"coalesce(a.remarks, '') as remarks \n" + 

			"from \n" + 
			"( \n" +			
			"	select * from rf_crb_detail \n" +
			"	where co_id = '"+co_id+"' \n" +
			"	and status_id = 'A' \n"
			+ "" +
			"	and trans_amt <> 0 \n" +
			"	and rb_id = '"+crb_no+"' \n" + 
			"	and rb_id in (select rb_id from rf_crb_header where co_id = '"+co_id+"' \n" +
			"	and to_char(issued_date,'yyyy-MM-dd') = '"+df.format(dteCashierDate.getDate())+"' ) and pay_rec_id::int = '"+rec_id+"'::int order by line_no ) as a \n" + 

			"left join mf_boi_chart_of_accounts d \n" + 
			"on a.acct_id = d.acct_id \n" +

			"union all --#2 (entries of previous AR, if in case AR and OR have the same date) \n" + 

			"select trim(f.acct_id) as acct_id, \n" + 
			"trim(g.acct_name), \n" + 
			"(case when f.trans_amt<0 then 0.00 else f.trans_amt end) as debit, \n" + 
			"(case when f.trans_amt>0 then 0.00 else -1*f.trans_amt end) as credit, \n" + 
			"coalesce(f.remarks, '') as remarks \n" + 
			"from (select * from rf_payments where to_char(actual_date, 'MM-dd-yyyy') = to_char(or_date, 'MM-dd-yyyy') and or_no = '"+crb_no+"' and pay_rec_id::int = '"+rec_id+"'::int) e \n" +  
			"left join ( select * from rf_crb_detail \n" + 
			"	where co_id = '"+co_id+"' \n" + 
			"	and status_id = 'A' \n" + 
			"	and trans_amt <> 0 \n" + 
			"	and rb_id in (select rb_id from rf_crb_header where co_id = '"+co_id+"' \n" +
			"	and to_char(issued_date,'yyyy-MM-dd') = '"+df.format(dteCashierDate.getDate())+"') order by line_no ) f on e.ar_no = f.rb_id \n" +  
			"left join mf_boi_chart_of_accounts g on f.acct_id = g.acct_id \n" +
			") a \n" +  
			"where a.acct_id is not null order by a.credit, a.debit" ;	

		System.out.printf("SQL: %s%n", sql);

		System.out.printf("SQL displayCRB: \n" + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries5(modelMain, modelTotal);

		}	

		else {
			modelSample5Total = new modelByrPmtPosting_CRB();
			modelSample5Total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), null });	
			tblSample5Total = new _JTableTotal(modelSample5Total, tblSample5Main);
			scrollSample5Total.setViewportView(tblSample5Total);
			tblSample5Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample5Total).setTotalLabel(0);}

		tblSample5Main.packAll();
		adjustRowHeight(tblSample5Main);
		tblSample5Main.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblSample5Main.getColumnModel().getColumn(3).setPreferredWidth(100);

		if (checkCRBstatus()==null) {

		} else {
			if (checkCRBstatus().equals("A")) {
				btnEdit.setEnabled(true);
				btnPostEntries.setEnabled(true);
			} else {
				btnEdit.setEnabled(false);
				btnPostEntries.setEnabled(false);
			}
		}
		btnRefreshCRB.setEnabled(false);		
		mnidelete.setEnabled(false);
		mniaddrow.setEnabled(false);
		mnicopy.setEnabled(false);
		mnipaste.setEnabled(false);
	}

	private void displayCRB_OR(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String rec_id) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		

		/*
		String sql = 	

			"--displayCRB \n" +
			"select * from ( \n" +

			"--#1 \n" +
			"select trim(a.acct_id) as acct_id, \n" +
			"trim(d.acct_name), \n" + 
			"(case when a.trans_amt<0 then 0.00 else a.trans_amt end) as debit, \n" + 
			"(case when a.trans_amt>0 then 0.00 else -1*a.trans_amt end) as credit, \n" + 
			"a.remarks \n" + 

			"from \n" + 
			"( \n" +			
			"	select * from rf_crb_detail \n" +
			"	where co_id = '"+co_id+"' \n" +
			"	and status_id = 'A' \n"
			+ "" +
			"	and trans_amt <> 0 \n" +
			"	and rb_id = '"+crb_no+"' \n" + 
			"	and rb_id in (select rb_id from rf_crb_header where co_id = '"+co_id+"' \n" +
			"	and to_char(issued_date,'yyyy-MM-dd') = '"+df.format(dteCashierDate.getDate())+"' ) and pay_rec_id::int = '"+rec_id+"'::int order by line_no ) as a \n" + 

			"left join mf_boi_chart_of_accounts d \n" + 
			"on a.acct_id = d.acct_id \n" +

			"union all --#2 (entries of previous AR, if in case AR and OR have the same date) \n" + 

			"select trim(f.acct_id) as acct_id, \n" + 
			"trim(g.acct_name), \n" + 
			"(case when f.trans_amt<0 then 0.00 else f.trans_amt end) as debit, \n" + 
			"(case when f.trans_amt>0 then 0.00 else -1*f.trans_amt end) as credit, \n" + 
			"f.remarks \n" + 
			"from (select * from rf_payments where to_char(actual_date, 'MM-dd-yyyy') = to_char(or_date, 'MM-dd-yyyy') and or_no = '"+crb_no+"' and pay_rec_id::int = '"+rec_id+"'::int) e \n" +  
			"left join ( select * from rf_crb_detail \n" + 
			"	where co_id = '"+co_id+"' \n" + 
			"	and status_id = 'A' \n" + 
			"	and trans_amt <> 0 \n" + 
			"	and rb_id in (select rb_id from rf_crb_header where co_id = '"+co_id+"' \n" +
			"	and to_char(issued_date,'yyyy-MM-dd') = '"+df.format(dteCashierDate.getDate())+"') order by line_no ) f on e.ar_no = f.rb_id \n" +  
			"left join mf_boi_chart_of_accounts g on f.acct_id = g.acct_id \n" +
			") a \n" +  
			"where a.acct_id is not null order by a.credit, a.debit" ;
		 */

		String sql = "select * \n" + 
				"from \n" + 
				"( \n" + 
				"	select trim(a.acct_id) as acct_id, \n" + 
				"	trim(d.acct_name), \n" + 
				"	(case when a.trans_amt<0 then 0.00 else a.trans_amt end) as debit, \n" + 
				"	(case when a.trans_amt>0 then 0.00 else -1*a.trans_amt end) as credit, \n" + 
				"	a.remarks \n" + 
				"	from \n" + 
				"	( \n" + 
				"		select * \n" + 
				"		from rf_crb_detail \n" + 
				"		where co_id = '"+co_id+"' and status_id = 'A' and trans_amt <> 0 \n" + 
				"		and rb_id = '"+crb_no+"' and rb_id in \n" + 
				"		(\n" + 
				"			select x.rb_id\n" + 
				"			from rf_crb_header x\n" + 
				"			inner join (select * from rf_payments y where y.remarks ~* 'Late LTS/BOI' or y.remarks ~* 'Late OR Issuance for Good Check') y \n" + 
				"			on y.client_seqno = x.reference_no and y.or_date::date is not null \n" + 
				"			and x.pay_rec_id::int = y.pay_rec_id::int and y.or_no = x.rb_id \n" + 
				"			where x.co_id = '"+co_id+"' and y.or_date::Date = '"+df.format(dteCashierDate.getDate())+"'::date \n" + 
				"		) and pay_rec_id::int = '"+rec_id+"'::int order by line_no\n" + 
				"	) as a \n" + 
				"	left join mf_boi_chart_of_accounts d on a.acct_id = d.acct_id \n" + 
				"	union all \n" + 
				// added by jari cruz asof sept 6 2022 reason SI entries
				"	select trim(a.acct_id) as acct_id, \n" + 
				"	trim(d.acct_name), \n" + 
				"	(case when a.trans_amt<0 then 0.00 else a.trans_amt end) as debit, \n" + 
				"	(case when a.trans_amt>0 then 0.00 else -1*a.trans_amt end) as credit, \n" + 
				"	a.remarks \n" + 
				"	from \n" + 
				"	( \n" + 
				"		select * \n" + 
				"		from rf_crb_detail \n" + 
				"		where co_id = '"+co_id+"' and status_id = 'A' and trans_amt <> 0 \n" + 
				"		and rb_id = '"+crb_no+"' and rb_id in \n" + 
				"		(\n" + 
				"			select x.rb_id\n" + 
				"			from rf_crb_header x\n" + 
				"			inner join (select * from rf_payments y where y.remarks ~* 'Late LTS/BOI' or y.remarks ~* 'Late OR Issuance for Good Check') y \n" + 
				"			on y.client_seqno = x.reference_no and y.si_date::date is not null \n" + 
				"			and x.pay_rec_id::int = y.pay_rec_id::int and y.si_no = x.rb_id \n" + 
				"			where x.co_id = '"+co_id+"' and y.si_date::Date = '"+df.format(dteCashierDate.getDate())+"'::date \n" + 
				"		) and pay_rec_id::int = '"+rec_id+"'::int order by line_no\n" + 
				"	) as a \n" + 
				"	left join mf_boi_chart_of_accounts d on a.acct_id = d.acct_id \n" + 
				"	union all \n" + 
				// added by jari cruz asof sept 6 2022 reason SI entries
				
				"	select trim(f.acct_id) as acct_id, \n" + 
				"	trim(g.acct_name), \n" + 
				"	(case when f.trans_amt<0 then 0.00 else f.trans_amt end) as debit, \n" + 
				"	(case when f.trans_amt>0 then 0.00 else -1*f.trans_amt end) as credit, \n" + 
				"	f.remarks \n" + 
				"	from (select * from rf_payments where to_char(actual_date, 'MM-dd-yyyy') = to_char(or_date, 'MM-dd-yyyy') and or_no = '"+crb_no+"' and pay_rec_id::int = '"+rec_id+"'::int) e \n" + 
				"	left join \n" + 
				"	(\n" + 
				"		select * \n" + 
				"		from rf_crb_detail \n" + 
				"		where co_id = '"+co_id+"' and status_id = 'A' and trans_amt <> 0 \n" + 
				"		and rb_id in \n" + 
				"		(\n" + 
				"			select x.rb_id \n" + 
				"			from rf_crb_header x \n" + 
				"			inner join (select * from rf_payments y where y.remarks ~* 'Late LTS/BOI' or y.remarks ~* 'Late OR Issuance for Good Check') y \n" + 
				"			on y.client_seqno = x.reference_no and y.or_date::date is not null \n" + 
				"			and x.pay_rec_id::int = y.pay_rec_id::int and y.or_no = x.rb_id \n" + 
				"			where x.co_id = '"+co_id+"' and y.or_date::Date = '"+df.format(dteCashierDate.getDate())+"'::date \n" + 
				"		) order by line_no \n" + 
				"	) f on e.ar_no = f.rb_id \n" + 
				"	left join mf_boi_chart_of_accounts g on f.acct_id = g.acct_id \n" + 
				") a \n" + 
				"where a.acct_id is not null order by a.credit, a.debit"; 

		System.out.printf("SQL: %s%n", sql);

		System.out.printf("SQL displayCRB: \n" + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries5(modelMain, modelTotal);

		}	

		else {
			modelSample5Total = new modelByrPmtPosting_CRB();
			modelSample5Total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), null });	
			tblSample5Total = new _JTableTotal(modelSample5Total, tblSample5Main);
			scrollSample5Total.setViewportView(tblSample5Total);
			tblSample5Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample5Total).setTotalLabel(0);}

		tblSample5Main.packAll();
		adjustRowHeight(tblSample5Main);
		tblSample5Main.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblSample5Main.getColumnModel().getColumn(3).setPreferredWidth(100);

		if (checkCRBstatus()==null) {

		} else {
			if (checkCRBstatus().equals("A")) {
				btnEdit.setEnabled(true);
				btnPostEntries.setEnabled(true);
			} else {
				btnEdit.setEnabled(false);
				btnPostEntries.setEnabled(false);
			}
		}
		btnRefreshCRB.setEnabled(false);		
		mnidelete.setEnabled(false);
		mniaddrow.setEnabled(false);
		mnicopy.setEnabled(false);
		mnipaste.setEnabled(false);
	}

	private void displayClientInfo(DefaultTableModel modelMain, int row){

		refreshAcctInfo();
		enableAcctInfo(true);
		refresh_tables();

		String tra_rec_id = "";
		String pay_rec_id = "";

		rec_id = "";
		crb_no = "";
		pbl_id = "";
		seq_no = 0;
		entity_id = "";

		try {
			tra_rec_id = modelMain.getValueAt(row,13).toString().trim();
		} catch (NullPointerException e) {
			tra_rec_id = "";
		}

		try {
			pay_rec_id = modelMain.getValueAt(row,14).toString().trim();
		} catch (NullPointerException e) {
			pay_rec_id = "";
		}

		if (tra_rec_id.equals("0") && pay_rec_id.equals("0")) {
			rec_id = "";
		} else if (!tra_rec_id.equals("0") && pay_rec_id.equals("0")) {
			rec_id = tra_rec_id;
		} else if (tra_rec_id.equals("0") && !pay_rec_id.equals("0")) {
			rec_id = pay_rec_id;
		}

		try {
			crb_no = modelMain.getValueAt(row,1).toString().trim();
		} catch (NullPointerException e) {
			crb_no = "";
			if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no receipt_no, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				crb_no = "";
			}
		}

		try {
			pbl_id = modelMain.getValueAt(row,3).toString().trim(); 
			tagPBL.setTag(modelMain.getValueAt(row,2).toString().trim());
		} catch (NullPointerException e) {
			pbl_id = ""; 
			if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no pbl_id, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				pbl_id = ""; 
				tagPBL.setTag("");
			}
		}

		try {
			seq_no = Integer.parseInt(modelMain.getValueAt(row,4).toString().trim());
		} catch (NullPointerException e) { 
			if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no seq_no, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				seq_no =  1;
			}
		}

		try {
			entity_id = modelMain.getValueAt(row,16).toString().trim();
		} catch (NullPointerException e) {
			entity_id = ""; 

			if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no pbl_id, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				entity_id = "";
			}
		}	

		try {
			tagPBL.setTag(modelMain.getValueAt(row,2).toString().trim());
		} catch (NullPointerException e) {
			tagPBL.setTag("");
		}	

		try {
			tagClientName.setTag(modelMain.getValueAt(row,5).toString().trim());
		} catch (NullPointerException e) {
			tagClientName.setTag("");
		}

		try {
			tagUnitID.setTag(pbl_id);
		} catch (NullPointerException e) {
			tagUnitID.setTag("");
		}		

		if (isClientAlreadyReserved(pbl_id, seq_no)==false) {
			tagAcctStatus.setTag("Holding");	
		} else {
			displayAccountInfo();			
		}

		displayCRB(modelSample5Main, rowHeaderSample5Main, modelSample5Total, rec_id);
	}

	private void displayClientInfoOR(DefaultTableModel modelMain, int row){

		refreshAcctInfo();
		enableAcctInfo(true);
		refresh_tables();

		String tra_rec_id = "";
		String pay_rec_id = "";

		rec_id = "";
		crb_no = "";
		pbl_id = "";
		seq_no = 0;
		entity_id = "";

		try {
			tra_rec_id = modelMain.getValueAt(row,13).toString().trim();
		} catch (NullPointerException e) {
			tra_rec_id = "";
		}

		try {
			pay_rec_id = modelMain.getValueAt(row,14).toString().trim();
		} catch (NullPointerException e) {
			pay_rec_id = "";
		}

		if (tra_rec_id.equals("0") && pay_rec_id.equals("0")) {
			rec_id = "";
		} else if (!tra_rec_id.equals("0") && pay_rec_id.equals("0")) {
			rec_id = tra_rec_id;
		} else if (tra_rec_id.equals("0") && !pay_rec_id.equals("0")) {
			rec_id = pay_rec_id;
		}

		if(tblSample2Main.getSelectedRows().length == 1) {
			try {
				crb_no = modelMain.getValueAt(row,1).toString().trim();
			} catch (NullPointerException e) {
				crb_no = "";
				if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no receipt_no, proceed?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					crb_no = "";
				}
			}
		}
		if(tblORMain.getSelectedRows().length == 1) {
			try {
				crb_no = modelORMain.getValueAt(row,1).toString().trim();
			} catch (NullPointerException e) {
				crb_no = "";
				if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no receipt_no, proceed?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					crb_no = "";
				}
			}
		}

		try {
			pbl_id = modelMain.getValueAt(row,3).toString().trim(); 
			tagPBL.setTag(modelMain.getValueAt(row,2).toString().trim());
		} catch (NullPointerException e) {
			pbl_id = ""; 
			if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no pbl_id, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				pbl_id = ""; 
				tagPBL.setTag("");
			}
		}

		try {
			seq_no = Integer.parseInt(modelMain.getValueAt(row,4).toString().trim());
		} catch (NullPointerException e) { 
			if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no seq_no, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				seq_no =  1;
			}
		}

		try {
			entity_id = modelMain.getValueAt(row,16).toString().trim();
		} catch (NullPointerException e) {
			entity_id = ""; 

			if (JOptionPane.showConfirmDialog(getContentPane(), "Account has no pbl_id, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				entity_id = "";
			}
		}	

		try {
			tagPBL.setTag(modelMain.getValueAt(row,2).toString().trim());
		} catch (NullPointerException e) {
			tagPBL.setTag("");
		}	

		try {
			tagClientName.setTag(modelMain.getValueAt(row,5).toString().trim());
		} catch (NullPointerException e) {
			tagClientName.setTag("");
		}

		try {
			tagUnitID.setTag(pbl_id);
		} catch (NullPointerException e) {
			tagUnitID.setTag("");
		}		

		if (isClientAlreadyReserved(pbl_id, seq_no)==false) {
			tagAcctStatus.setTag("Holding");	
		} else {
			displayAccountInfo();			
		}

		displayCRB_OR(modelSample5Main, rowHeaderSample5Main, modelSample5Total, rec_id);
	}

	private boolean PeriodOpen() {
		return FncGlobal.GetBoolean("select exists(select * \n" + 
				"from mf_acctg_period x \n" + 
				"where x.co_id = '"+lookupCompany.getValue()+"' and x.period_year::int = date_part('year', '"+dteCashierDate.getDate()+"'::date)::int \n" + 
				"and x.month_code::int = date_part('month', '"+dteCashierDate.getDate()+"'::date)::int \n" + 
				"and (x.status_id = 'A' and x.date_closed is null))");  
	}

	private void setParticular(String strCoID, String strBranch, String strDate) {
		strCoID = ((strCoID==null)?"":strCoID);
		strBranch = ((strBranch==null)?"":strBranch);
		strDate = ((strDate==null)?"":strDate);

		String particular = "";
		particular = FncGlobal.GetString("select remarks from rf_crb_special_remarks where coalesce(co_id, '') = '"+strCoID+"' and coalesce(branch_id, '') = '"+strBranch+"' and trans_date::date = '"+strDate+"'::date"); 

		if (particular.equals("")) {
			particular = "To record the collection for ";
			particular = particular.concat(FncGlobal.GetString("select '"+strDate+"'::date::varchar; ")).concat(", ");
			particular = particular.concat(FncGlobal.GetString("select concat(branch_alias, ' ', 'BRANCH') from mf_office_branch where branch_id = '"+strBranch+"'; "));
		}

		txtParticulars.setText(particular);
	}
	
	private void updateCashReturnParticulars(String co_id, String proj_id, String branch_id, String phase, Date trans_date) {
		
		String particulars = txtParticulars.getText();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT * from sp_update_bpp_summary_particulars(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+branch_id+"','null'), NULLIF('"+phase+"', 'null'), '"+trans_date+"', '"+particulars+"');";
		db.select(SQL);
		FncSystem.out("Display Remarks SQL:", SQL);
		
		String remarks = (String) db.getResult()[0][0];
		
		txtParticulars.setText(remarks);
	}

	private void insertParticular() {

		String company = ((lookupCompany.getValue()==null)?"":lookupCompany.getValue());
		String project = ((lookupProject.getValue()==null)?"":lookupProject.getValue());
		String branch = ((lookupOfficeBranch.getValue()==null)?"":lookupOfficeBranch.getValue());
		String phase = ((lookupPhase.getValue()==null)?"":lookupPhase.getValue());
		String crb_date = FncGlobal.GetString("select '"+dteCashierDate.getDate().toString()+"'::date::varchar; "); 

		if (!FncGlobal.GetBoolean("select exists(select * from rf_crb_special_remarks where branch_id = '"+lookupOfficeBranch.getValue()+"' and trans_date::date = '"+dteCashierDate.getDate().toString()+"'::date)")) {
			String branch_name = FncGlobal.GetString("select concat(branch_alias, ' ', 'BRANCH') from mf_office_branch where branch_id = '"+lookupOfficeBranch.getValue()+"'; ");
			String SQL = "insert into rf_crb_special_remarks (co_id, proj_id, branch_id, phase, trans_date, remarks, status_id, date_created, created_by)\n" + 
					"values ('"+company+"', '"+project+"', '"+branch+"', '"+phase+"', '"+crb_date+"'::date, concat('To record the collection for ', '"+crb_date+"', ', "+branch_name+"'), 'A', now(), '"+UserInfo.EmployeeCode+"'); "; 

			pgUpdate dbExec = new pgUpdate(); 
			dbExec.executeUpdate(SQL, true);
			dbExec.commit();
		}
	}
}