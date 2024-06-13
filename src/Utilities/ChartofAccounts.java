package Utilities;

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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelChartofAcctsMain;
import tablemodel.modelContractorOtherDeductions;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
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

public class ChartofAccounts extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Chart of Accounts";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlAccountList;
	private JPanel pnlSouth;
	private JPanel pnlTable;
	private JPanel pnlAccountSub;
	private JPanel pnlComp;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlAccount;
	private JPanel pnlAccount_a1;
	private JPanel pnlAccount_a2;
	private JPanel pnlAccount_a2_1;
	private JPanel pnlDesc;
	private JPanel pnlSubAccountList;
	private JPanel pnlProgBill;
	private JPanel pnlTblBC;
	private JPanel pnlSub;	
	private JPanel pnlSearch_a;
	private JPanel pnlSearch_a1;
	private JPanel pnlSearch_a2;
	private JPanel pnlComp_a3;
	private JPanel pnlAddNewAcct;
	private JPanel pnlAddNewAcct_a;
	private JPanel pnlAddNewAcct_a1;
	private JPanel pnlAddNewAcct_b;
	private JPanel pnlAddNewAcct_a2;
	private JPanel pnlAddNewAcct_c;

	private _JScrollPaneMain scrollAcctList;	
	private _JScrollPaneMain scrollSubAcct;	
	private static _JScrollPaneTotal scrollAcctList_total;
	private static _JScrollPaneTotal scrollSubAcctList_total;
	private JScrollPane scpAcctDesc;
	private JScrollPane scpDescription;

	private static modelChartofAcctsMain modelAcctList;
	private static modelChartofAcctsMain modelSubAcct;
	private static modelContractorOtherDeductions modelSubAcct_total;
	private static modelChartofAcctsMain modelSubAcctList_total;
	private static modelChartofAcctsMain modelAcctList_total;

	private static _JTableMain tblAcctList;
	private static _JTableMain tblSubAcct;	

	static _JLookup lookupCompany;	
	private _JLookup lookupParentAcct;	
	
	static _JTagLabel tagCompany;
	private static _JTagLabel tagAcctLevel;	
	private static JList rowHeaderAcctList;
	private static JList rowHeaderSubAcct;

	private JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnAddNew;
	private static JButton btnEdit;
	private JButton btnPreview;	

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	private static JLabel lblAcctLevel;
	private static JLabel lblSearch;
	private JLabel lblCompany;
	private JLabel lblLevel;	
	private JLabel lblParentAcct;
	private JLabel lblName;	
	private JLabel lblBalance;
	private JLabel lblStatus;
	private JLabel lblType;
	private JLabel lblAcctID;

	private JPopupMenu menu2;
	private JMenuItem mniopenRPLF;
	private JMenuItem mniopenJV;
	private JMenuItem mniopenPV;
	
	private static JComboBox cmbLevel;
	private JComboBox cmbStatus;
	private JComboBox cmbType;
	private JComboBox cmbBalance;
	private JComboBox cmbLevelNew;
	
	private static JXTextField txtSearch;
	private JXTextField txtAcctID;
	private JXTextField txtName;
	
	private static JCheckBox chkIncludeInactive;
	private static _JTableTotal tblSubAcctList_total;
	private static _JTableTotal tblAcctList_total;
	
	private JMenuItem mniopenGL;	
	private JTextArea txtAcctDesc;
	private static JTextArea txtDescription;
	private JCheckBox chkFiltered;
	
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public static String company = "";
	public static String company_logo;
	static String co_id = "";
	private String to_do = "addnew";	

	

	public ChartofAccounts() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ChartofAccounts(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ChartofAccounts(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(935, 604));
		this.setBounds(0, 0, 935, 604);

		{
			menu2 = new JPopupMenu("Popup");
			mniopenGL = new JMenuItem("Open General Ledger");		
			menu2.add(mniopenGL);

			mniopenGL.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){

				}
			});

		}


		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 82));				

			pnlComp = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(921, 32));		

			pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(200, 29));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			{
				lblCompany = new JLabel("        COMPANY");
				pnlComp_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(88, 31));
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
							tagCompany.setTag(company);
							company_logo = (String) data[3];

							KEYBOARD_MANAGER.focusNextComponent();							

							enable_fields(true);
							enableButtons(true, false, true, true);
							displayAccountList(modelAcctList, rowHeaderAcctList, 1, "A", "");

						}
					}
				});
			}

			pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlComp.add(pnlComp_a2, BorderLayout.CENTER);	
			pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
			pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

			{
				tagCompany = new _JTagLabel("[ ]");
				pnlComp_a2.add(tagCompany);
				tagCompany.setBounds(209, 27, 700, 22);
				tagCompany.setEnabled(true);	
				tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
			}	

			pnlComp_a3 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlComp.add(pnlComp_a3, BorderLayout.EAST);	
			pnlComp_a3.setPreferredSize(new java.awt.Dimension(243, 29));	
			pnlComp_a3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

			{
				chkIncludeInactive = new JCheckBox("Include Inactive Accounts");
				pnlComp_a3.add(chkIncludeInactive);
				chkIncludeInactive.setEnabled(false);	
				chkIncludeInactive.setPreferredSize(new java.awt.Dimension(383, 26));
				chkIncludeInactive.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {

						generateList();
						btnCancel.setEnabled(true);
					}
				});
			}


			pnlAccount = new JPanel(new BorderLayout(5, 5));
			pnlNorth.add(pnlAccount, BorderLayout.CENTER);				
			pnlAccount.setPreferredSize(new java.awt.Dimension(921, 41));
			pnlAccount.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlAccount.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

			pnlAccount_a1 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlAccount.add(pnlAccount_a1, BorderLayout.WEST);	
			pnlAccount_a1.setPreferredSize(new java.awt.Dimension(87, 36));	
			pnlAccount_a1.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

			{
				lblAcctLevel = new JLabel("Acct. Level :", JLabel.TRAILING);
				pnlAccount_a1.add(lblAcctLevel);
				lblAcctLevel.setEnabled(false);	
				lblAcctLevel.setPreferredSize(new java.awt.Dimension(92, 25));
				lblAcctLevel.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,14));
			}		

			pnlAccount_a2 = new JPanel(new BorderLayout(5, 5));
			pnlAccount.add(pnlAccount_a2, BorderLayout.CENTER);	
			pnlAccount_a2.setPreferredSize(new java.awt.Dimension(639, 41));	
			pnlAccount_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			pnlAccount_a2_1 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlAccount_a2.add(pnlAccount_a2_1, BorderLayout.WEST);	
			pnlAccount_a2_1.setPreferredSize(new java.awt.Dimension(97, 26));					

			{
				String status[] = {"1","2","3","4","ALL"};					
				cmbLevel = new JComboBox(status);
				pnlAccount_a2_1.add(cmbLevel);
				cmbLevel.setSelectedItem(0);
				cmbLevel.setBounds(537, 15, 160, 21);	
				cmbLevel.setEnabled(false);
				cmbLevel.setSelectedIndex(0);	
				cmbLevel.setPreferredSize(new java.awt.Dimension(89, 26));
				cmbLevel.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent evt) 
					{		
						generateList();
						btnCancel.setEnabled(true);
					}
				});		
			}

			pnlAccount_a2_1 = new JPanel(new GridLayout(1, 1, 5, 5));
			pnlAccount_a2.add(pnlAccount_a2_1, BorderLayout.CENTER);	
			pnlAccount_a2_1.setPreferredSize(new java.awt.Dimension(197, 22));					

			{
				tagAcctLevel = new _JTagLabel("[ All Accounts ]");
				pnlAccount_a2_1.add(tagAcctLevel);
				tagAcctLevel.setBounds(209, 27, 700, 22);
				tagAcctLevel.setEnabled(true);	
				tagAcctLevel.setPreferredSize(new java.awt.Dimension(27, 33));
			}	

//			pnlSearch_a = new JPanel(new BorderLayout(0,0));
//			pnlAccount_a2.add(pnlSearch_a, BorderLayout.EAST);	
//			pnlSearch_a.setPreferredSize(new java.awt.Dimension(509, 26));
//			pnlSearch_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));			
//
//			pnlSearch_a1 = new JPanel(new GridLayout(1, 1, 5, 5));
//			pnlSearch_a.add(pnlSearch_a1, BorderLayout.WEST);	
//			pnlSearch_a1.setPreferredSize(new java.awt.Dimension(149, 26));	
//			pnlSearch_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//
//			{
//				lblSearch = new JLabel("Search Acct. ID/Name : ", JLabel.TRAILING);
//				pnlSearch_a1.add(lblSearch);
//				lblSearch.setEnabled(false);	
//				lblSearch.setPreferredSize(new java.awt.Dimension(134, 26));
//				lblSearch.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,14));
//			}						
//
//			pnlSearch_a2 = new JPanel(new BorderLayout(0,0));
//			pnlSearch_a.add(pnlSearch_a2, BorderLayout.CENTER);	
//			pnlSearch_a2.setPreferredSize(new java.awt.Dimension(778, 36));	
//			pnlSearch_a2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//
//			{
//				txtSearch = new JXTextField("");
//				pnlSearch_a2.add(txtSearch);
//				txtSearch.setEnabled(false);	
//				txtSearch.setEditable(false);	
//				txtSearch.setBounds(120, 25, 300, 22);	
//				txtSearch.setHorizontalAlignment(JTextField.CENTER);	
//				txtSearch.addKeyListener(new KeyAdapter() {
//					public void keyReleased(KeyEvent e) {	
//						displayAccountList(modelAcctList, rowHeaderAcctList, 1, "A", txtSearch.getText());
//						btnEdit.setEnabled(false);
//					}				 
//				});					
//			}	
		}
		{

			pnlAddNewAcct = new JPanel();
			pnlAddNewAcct.setLayout(new BorderLayout(5, 5));
			pnlAddNewAcct.setBorder(lineBorder);		
			pnlAddNewAcct.setPreferredSize(new java.awt.Dimension(382, 450));		

			{		
				pnlAddNewAcct_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewAcct.add(pnlAddNewAcct_a, BorderLayout.NORTH);				
				pnlAddNewAcct_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewAcct_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewAcct_a.setPreferredSize(new java.awt.Dimension(380, 234));		

				{
					pnlAddNewAcct_a1 = new JPanel(new GridLayout(8, 1, 0, 5));
					pnlAddNewAcct_a.add(pnlAddNewAcct_a1, BorderLayout.WEST);				
					pnlAddNewAcct_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewAcct_a1.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlAddNewAcct_a1.setPreferredSize(new java.awt.Dimension(107, 145));		

					{
						lblLevel = new JLabel("Account Level", JLabel.TRAILING);
						pnlAddNewAcct_a1.add(lblLevel);
						lblLevel.setEnabled(true);	
						lblLevel.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblParentAcct = new JLabel("Parent Account", JLabel.TRAILING);
						pnlAddNewAcct_a1.add(lblParentAcct);
						lblParentAcct.setEnabled(false);	
						lblParentAcct.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblAcctID = new JLabel("Acct. ID", JLabel.TRAILING);
						pnlAddNewAcct_a1.add(lblAcctID);
						lblAcctID.setEnabled(true);	
						lblAcctID.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblName = new JLabel("Acct. Name", JLabel.TRAILING);
						pnlAddNewAcct_a1.add(lblName);
						lblName.setEnabled(true);	
						lblName.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblType = new JLabel("Type", JLabel.TRAILING);
						pnlAddNewAcct_a1.add(lblType);
						lblType.setEnabled(true);	
						lblType.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblBalance = new JLabel("Balance", JLabel.TRAILING);
						pnlAddNewAcct_a1.add(lblBalance);
						lblBalance.setEnabled(true);	
						lblBalance.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlAddNewAcct_a1.add(lblStatus);
						lblStatus.setEnabled(true);	
						lblStatus.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						pnlAddNewAcct_a1.add(Box.createHorizontalBox());
					}
				}
				{
					pnlAddNewAcct_a2 = new JPanel(new GridLayout(8, 1, 0, 5));
					pnlAddNewAcct_a.add(pnlAddNewAcct_a2, BorderLayout.CENTER);				
					pnlAddNewAcct_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewAcct_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlAddNewAcct_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						String level[] = {"1","2","3","4"};					
						cmbLevelNew = new JComboBox(level);
						pnlAddNewAcct_a2.add(cmbLevelNew);
						cmbLevelNew.setSelectedItem(null);
						cmbLevelNew.setBounds(537, 15, 160, 21);	
						cmbLevelNew.setEnabled(true);
						cmbLevelNew.setSelectedIndex(0);	
						cmbLevelNew.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbLevelNew.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{
								if(cmbLevelNew.getSelectedIndex()==0)
								{
									lookupParentAcct.setValue("");
									lookupParentAcct.setEnabled(false);
									lblParentAcct.setEnabled(false);										
									refresh_fieldsAddNew();
									txtAcctID.setText(sql_getNextGrp()+"-00-00-000");
								}
								else if(cmbLevelNew.getSelectedIndex()==1)
								{
									lookupParentAcct.setLookupSQL(getChartofAccount(1));
									lookupParentAcct.setEnabled(true);
									lblParentAcct.setEnabled(true);	
									refresh_fieldsAddNew();
								}
								else if(cmbLevelNew.getSelectedIndex()==2)
								{
									lookupParentAcct.setLookupSQL(getChartofAccount(2));
									lookupParentAcct.setEnabled(true);
									lblParentAcct.setEnabled(true);	
									refresh_fieldsAddNew();
								}
								else if(cmbLevelNew.getSelectedIndex()==3)
								{
									lookupParentAcct.setLookupSQL(getChartofAccount(3));
									lookupParentAcct.setEnabled(true);
									lblParentAcct.setEnabled(true);	
									refresh_fieldsAddNew();
								}


							}
						});		
					}
					{
						lookupParentAcct = new _JLookup(null, "Parent Account ID", 2, 2);
						pnlAddNewAcct_a2.add(lookupParentAcct);
						lookupParentAcct.setBounds(20, 27, 20, 25);
						lookupParentAcct.setReturnColumn(0);
						lookupParentAcct.setFilterName(true);
						lookupParentAcct.setEnabled(false);	
						lookupParentAcct.setLookupSQL(getChartofAccount(1));
						lookupParentAcct.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupParentAcct.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		

									if(cmbLevelNew.getSelectedIndex()==1) 
									{
										txtAcctID.setText(sql_getNextSubGrp((String) data[0]));
									}

									else if(cmbLevelNew.getSelectedIndex()==2) 
									{
										txtAcctID.setText(sql_getNextMainAcct((String) data[0]));
									}

									else if(cmbLevelNew.getSelectedIndex()==3) 
									{
										txtAcctID.setText(sql_getNextSubAcct((String) data[0]));
									}


								}
							}
						});	
					}	
					{
						txtAcctID = new JXTextField("");
						pnlAddNewAcct_a2.add(txtAcctID);
						txtAcctID.setEnabled(true);	
						txtAcctID.setEditable(false);
						txtAcctID.setBounds(120, 25, 300, 22);	
						txtAcctID.setHorizontalAlignment(JTextField.CENTER);
						txtAcctID.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						txtAcctID.setText(sql_getNextGrp()+"-00-00-000");
					}	
					{
						txtName = new JXTextField("");
						pnlAddNewAcct_a2.add(txtName);
						txtName.setEnabled(true);	
						txtName.setEditable(true);
						txtName.setBounds(120, 25, 300, 22);	
						txtName.setHorizontalAlignment(JTextField.CENTER);
						txtName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}	
					{
						String type[] = {"01","02","03","04"};					
						cmbType = new JComboBox(type);
						pnlAddNewAcct_a2.add(cmbType);
						cmbType.setBounds(537, 15, 160, 21);	
						cmbType.setEnabled(true);
						cmbType.setSelectedIndex(0);	
						cmbType.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbType.setToolTipText("01-Assets ; 02-Liabilities ; 03-Equity; 04-Sales & Revenue");
						cmbType.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
					{
						String balance[] = {"Debit","Credit"};					
						cmbBalance = new JComboBox(balance);
						pnlAddNewAcct_a2.add(cmbBalance);
						cmbBalance.setBounds(537, 15, 160, 21);	
						cmbBalance.setEnabled(true);
						cmbBalance.setSelectedIndex(0);	
						cmbBalance.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbBalance.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
					{
						String status[] = {"Active","Inactive"};					
						cmbStatus = new JComboBox(status);
						pnlAddNewAcct_a2.add(cmbStatus);
						cmbStatus.setBounds(537, 15, 160, 21);	
						cmbStatus.setEnabled(true);
						cmbStatus.setSelectedIndex(0);	
						cmbStatus.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbStatus.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
					{
						chkFiltered = new JCheckBox("Filtered?");
						pnlAddNewAcct_a2.add(chkFiltered);
						chkFiltered.setEnabled(UserInfo.EmployeeCode.equals("900054") || UserInfo.EmployeeCode.equals("900655"));
					}
				}

				pnlAddNewAcct_b = new JPanel(new BorderLayout(5, 5));
				pnlAddNewAcct.add(pnlAddNewAcct_b, BorderLayout.CENTER);				
				pnlAddNewAcct_b.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewAcct_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewAcct_b.setPreferredSize(new java.awt.Dimension(500, 150));		
				{
					scpAcctDesc = new JScrollPane();
					pnlAddNewAcct_b.add(scpAcctDesc);
					scpAcctDesc.setBounds(82, 7, 309, 61);
					scpAcctDesc.setOpaque(true);
					scpAcctDesc.setPreferredSize(new java.awt.Dimension(375, 159));

					{
						txtAcctDesc = new JTextArea();
						scpAcctDesc.add(txtAcctDesc);
						scpAcctDesc.setViewportView(txtAcctDesc);
						txtAcctDesc.setText(" Description :");
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

				pnlAddNewAcct_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewAcct.add(pnlAddNewAcct_c, BorderLayout.SOUTH);				
				pnlAddNewAcct_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewAcct_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewAcct_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnSave = new JButton("Save");
					pnlAddNewAcct_c.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(true);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							saveNewAcct();
							Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddNewAcct_c);
							optionPaneWindow.dispose();
							
						}
					});
				}

			}
		}
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlAccountList = new JPanel();
			pnlTable.add(pnlAccountList, BorderLayout.CENTER);
			pnlAccountList.setLayout(new BorderLayout(5, 5));
			pnlAccountList.setPreferredSize(new java.awt.Dimension(923, 321));
			pnlAccountList.setBorder(lineBorder);			

			//Progress Billing
			{
				pnlProgBill = new JPanel(new BorderLayout());
				pnlAccountList.add(pnlProgBill, BorderLayout.CENTER);
				pnlProgBill.setPreferredSize(new java.awt.Dimension(921, 178));
				{
					scrollAcctList = new _JScrollPaneMain();
					pnlProgBill.add(scrollAcctList, BorderLayout.CENTER);
					{
						modelAcctList = new modelChartofAcctsMain();

						tblAcctList = new _JTableMain(modelAcctList);
						scrollAcctList.setViewportView(tblAcctList);
						tblAcctList.addMouseListener(new PopupTriggerListener_panel2());
						tblAcctList.addMouseListener(this);
						tblAcctList.setSortable(false);
						tblAcctList.getColumnModel().getColumn(1).setPreferredWidth(80);
						tblAcctList.getColumnModel().getColumn(2).setPreferredWidth(200);
						tblAcctList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){displaySubAccountList(modelSubAcct, rowHeaderSubAcct);
								}
							}
						});
						/*tblAcctList.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								displaySubAccountList(modelSubAcct, rowHeaderSubAcct);							
							}

							public void keyPressed(KeyEvent e) {

							}

						}); 


						tblAcctList.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblAcctList.rowAtPoint(e.getPoint()) == -1){
									tblAcctListTotal.clearSelection();
								}else{
									tblAcctList.setCellSelectionEnabled(true);
								}
								displaySubAccountList(modelSubAcct, rowHeaderSubAcct);	
							}
							public void mouseReleased(MouseEvent e) {
								if(tblAcctList.rowAtPoint(e.getPoint()) == -1){
									tblAcctListTotal.clearSelection();
								}else{
									tblAcctList.setCellSelectionEnabled(true);
								}
								displaySubAccountList(modelSubAcct, rowHeaderSubAcct);	
							}
						});
*/
					}
					{
						rowHeaderAcctList = tblAcctList.getRowHeader22();
						scrollAcctList.setRowHeaderView(rowHeaderAcctList);
						scrollAcctList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollAcctList_total = new _JScrollPaneTotal(scrollAcctList);
						pnlProgBill.add(scrollAcctList_total, BorderLayout.SOUTH);
						{
							modelAcctList_total = new modelChartofAcctsMain();
							modelAcctList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

							tblAcctList_total = new _JTableTotal(modelAcctList_total, tblAcctList);
							tblAcctList_total.setFont(dialog11Bold);
							scrollAcctList_total.setViewportView(tblAcctList_total);
							((_JTableTotal) tblAcctList_total).setTotalLabel(0);
						}
					}
				}				
			}


			pnlAccountSub = new JPanel(new BorderLayout(5, 5));
			pnlTable.add(pnlAccountSub, BorderLayout.SOUTH);
			pnlAccountSub.setPreferredSize(new java.awt.Dimension(923, 195));
			pnlAccountSub.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			pnlDesc = new JPanel(new GridLayout(1, 1, 0, 0));
			pnlAccountSub.add(pnlDesc, BorderLayout.EAST);
			pnlDesc.setPreferredSize(new java.awt.Dimension(310, 255));
			pnlDesc.setBorder(lineBorder);
			pnlDesc.setBorder(JTBorderFactory.createTitleBorder("Description"));

			{			
				pnlTblBC = new JPanel(new BorderLayout());
				pnlDesc.add(pnlTblBC, "Center");
				pnlTblBC.setPreferredSize(new java.awt.Dimension(481, 253));				

				scpDescription = new JScrollPane();
				pnlTblBC.add(scpDescription);
				scpDescription.setBounds(82, 7, 309, 61);
				scpDescription.setOpaque(true);
				scpDescription.setPreferredSize(new java.awt.Dimension(375, 159));

				{
					txtDescription = new JTextArea();
					scpDescription.add(txtDescription);
					scpDescription.setViewportView(txtDescription);
					txtDescription.setBounds(77, 3, 250, 81);
					txtDescription.setLineWrap(true);
					txtDescription.setPreferredSize(new java.awt.Dimension(366, 133));
					txtDescription.setEditable(false);
					txtDescription.setEnabled(false);
					txtDescription.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	

						}});	
				}		
			}


			//Backcharges

			pnlSubAccountList = new JPanel(new GridLayout(1, 1, 0, 0));
			pnlAccountSub.add(pnlSubAccountList, BorderLayout.CENTER);
			pnlSubAccountList.setPreferredSize(new java.awt.Dimension(400, 255));	
			pnlSubAccountList.setBorder(lineBorder);
			pnlSubAccountList.setBorder(JTBorderFactory.createTitleBorder("Sub-Account"));

			//Deduction
			{			
				pnlSub = new JPanel(new BorderLayout());
				pnlSubAccountList.add(pnlSub, BorderLayout.CENTER);
				pnlSub.setPreferredSize(new java.awt.Dimension(401, 253));				

				{
					scrollSubAcct = new _JScrollPaneMain();
					pnlSub.add(scrollSubAcct, BorderLayout.CENTER);
					{
						modelSubAcct = new modelChartofAcctsMain();

						tblSubAcct = new _JTableMain(modelSubAcct);
						scrollSubAcct.setViewportView(tblSubAcct);
						//tblSubAcct.addMouseListener(this);					

						tblSubAcct.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {

								}
							}
							public void mouseReleased(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {

								}
							}
						});

						tblSubAcct.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {

							}						
							public void keyPressed(KeyEvent e) {

							}

						});


					}
					{
						rowHeaderSubAcct = tblSubAcct.getRowHeader22();
						scrollSubAcct.setRowHeaderView(rowHeaderSubAcct);
						scrollSubAcct.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollSubAcctList_total = new _JScrollPaneTotal(scrollAcctList);
						pnlSub.add(scrollSubAcctList_total, BorderLayout.SOUTH);
						{
							modelSubAcctList_total = new modelChartofAcctsMain();
							modelSubAcctList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

							tblSubAcctList_total = new _JTableTotal(modelSubAcctList_total, tblSubAcct);
							tblSubAcctList_total.setFont(dialog11Bold);
							scrollSubAcctList_total.setViewportView(tblSubAcctList_total);
							((_JTableTotal) tblSubAcctList_total).setTotalLabel(0);
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
					btnPreview = new JButton("Preview");
					pnlSouthCenter.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
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
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display
	public static void displayAccountList(DefaultTableModel modelMain, JList rowHeader, Integer level, String status, String acct) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select \r\n" + 
			"\r\n" + 
			"a.acct_id,\r\n" + 
			"a.acct_name,\r\n" + 
			"( case when a.acct_type = '01' then 'ASSETS' else \r\n" + 
			"	( case when a.acct_type = '02' then 'LIABILITIES' else \r\n" + 
			"	( case when a.acct_type = '03' then 'EQUITY' else \r\n" + 
			"	( case when a.acct_type = '04' then 'SALES & REVENUE' else '' end) end) end) end ) as acct_type,\r\n" + 			
			"( case when a.dbt_cdt = 'D' then 'Debit' else " +
			"	( case when  a.dbt_cdt = 'C' then 'Credit' else 'null' end) end) as balance,  \r\n" + 
			"( upper(trim(bb.first_name))||' '||upper(trim(bb.last_name))) as created_by, " +
			"to_char(a.date_created,'MM-dd-yyyy') as date_created,\r\n" + 
			"( upper(trim(cc.first_name))||' '||upper(trim(cc.last_name))) as created_by,\r\n" + 
			"a.w_subacct as w_sub,\r\n" + 
			"( case when a.status_id = 'A' then 'Active' else 'Inactive' end) as status, " +
			"a.acct_level, coalesce(a.filtered, false) \r\n" + 
			"\r\n" + 
			"from ( select * from mf_boi_chart_of_accounts \n" ;
		if (acct.equals("")) {} else {sql = sql + "where acct_id like '%"+acct+"%' or upper(acct_name) like '%"+acct.toUpperCase()+"%' \n" ;} 
		sql = sql +
		")a\r\n" + 
		"left join em_employee b on a.created_by = b.emp_code\r\n" + 
		"left join rf_entity bb on b.entity_id = bb.entity_id  " +
		"left join em_employee c on a.edited_by = c.emp_code " +
		"left join rf_entity cc on c.entity_id = cc.entity_id  " +
		"where a.acct_id is not null \r\n" ;

		if (level>0&&acct.equals("")) {sql = sql + "and a.acct_level = "+level+" \n" ;} 			
		if (status.equals("A")) {sql = sql + "and a.status_id = '"+status+"' " ;} else {}	
		sql = sql +
		"order by a.acct_id ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblAcctList.packAll();	
		adjustRowHeight(tblAcctList);

		int row = tblAcctList.getRowCount();			
		modelAcctList_total.setValueAt(new BigDecimal(row), 0, 1);
		
	}	
	
	public static void displaySubAccountList(DefaultTableModel modelMain, JList rowHeader) {
		
		if(tblAcctList.getSelectedRows().length > 0) {
			int row = tblAcctList.getSelectedRow();			

			String acct_id = modelAcctList.getValueAt(row,0).toString().trim();		
			Integer level  = Integer.parseInt(tblAcctList.getValueAt(row,9).toString().trim()) + 1;	

			FncTables.clearTable(modelMain);//Code to clear modelMain.
			DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
			rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

			String sql = 				
				"select \r\n" + 
				"\r\n" + 
				"a.acct_id,\r\n" + 
				"a.acct_name,\r\n" + 
				"( case when a.acct_type = '01' then 'ASSETS' else \r\n" + 
				"	( case when a.acct_type = '02' then 'LIABILITIES' else \r\n" + 
				"	( case when a.acct_type = '03' then 'EQUITY' else \r\n" + 
				"	( case when a.acct_type = '04' then 'SALES & REVENUE' else '' end) end) end) end ) as acct_type,\r\n" + 
				"( case when a.dbt_cdt = 'D' then 'Debit' else " +
				"	( case when  a.dbt_cdt = 'C' then 'Credit' else 'null' end) end) as balance,  \r\n" + 	
				"( upper(trim(bb.first_name))||' '||upper(trim(bb.last_name))) as created_by,\r\n" + 			
				"to_char(a.date_created,'MM-dd-yyyy') as date_created,\r\n" + 
				"( upper(trim(cc.first_name))||' '||upper(trim(cc.last_name))) as created_by,\r\n" + 
				"a.w_subacct as w_sub,\r\n" + 
				"( case when a.status_id = 'A' then 'Active' else 'Inactive' end) as status, \n" +
				"a.acct_level, coalesce(a.filtered, false) \n\n" +  
				"\r\n" + 
				"from ( select * from mf_boi_chart_of_accounts where acct_mainacct_id = '"+acct_id+"' " +
				"or acct_grp_id = '"+acct_id+"' or acct_subgrp_id = '"+acct_id+"'   \n" + 

				")a\r\n" + 
				"left join em_employee b on a.created_by = b.emp_code\r\n" + 
				"left join rf_entity bb on b.entity_id = bb.entity_id  " +
				"left join em_employee c on a.edited_by = c.emp_code " +
				"left join rf_entity cc on c.entity_id = cc.entity_id  " +
				"where a.acct_id is not null \n" ;


			if (!chkIncludeInactive.isSelected()){ sql = sql + "and a.status_id = 'A' \r\n" ;} else {}

			if (level<4){ sql = sql + "and a.acct_level = "+level+" \r\n" ;} else {}

			sql = sql +
			"order by a.acct_id ";

			System.out.printf("SQL #1: %s", sql);
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					// Adding of row in table
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}			
			}	

			tblSubAcct.packAll();	
			adjustRowHeight(tblSubAcct);

			int row_tot = tblSubAcct.getRowCount();	
			modelSubAcctList_total.setValueAt(new BigDecimal(row_tot), 0, 1);		

			txtDescription.setText(sql_getAcctRemarks(acct_id));
			btnEdit.setEnabled(true);
		}
	}	

	public static void generateList(){
		
		if (cmbLevel.getSelectedIndex()==0&&!chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 1, "A", "");
		}
		else if (cmbLevel.getSelectedIndex()==1&&!chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 2, "A", "");
		}
		else if (cmbLevel.getSelectedIndex()==2&&!chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 3, "A", "");
		}
		else if (cmbLevel.getSelectedIndex()==3&&!chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 4, "A", "");
		}
		else if (cmbLevel.getSelectedIndex()==4&&!chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 0, "A", "");
		}
		else if (cmbLevel.getSelectedIndex()==0&&chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 1, "", "");
		}
		else if (cmbLevel.getSelectedIndex()==1&&chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 2, "", "");
		}
		else if (cmbLevel.getSelectedIndex()==2&&chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 3, "", "");
		}
		else if (cmbLevel.getSelectedIndex()==3&&chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 4, "", "");
		}
		else if (cmbLevel.getSelectedIndex()==4&&chkIncludeInactive.isSelected()) 
		{
			displayAccountList(modelAcctList, rowHeaderAcctList, 0, "", "");
		}	
		btnEdit.setEnabled(false);
	}


	//Enable/Disable all components inside JPanel
	public void setComponentEnabled(JPanel panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			comp.setEnabled(enable);
		}
	}

	public static void enable_fields(Boolean x){

		lblAcctLevel.setEnabled(x);	
		cmbLevel.setEnabled(x);
		tagAcctLevel.setEnabled(x);	
		//lblSearch.setEnabled(x);	
//		txtSearch.setEnabled(x);	
//		txtSearch.setEditable(x);
		chkIncludeInactive.setEnabled(x);	
		txtDescription.setEnabled(x);
	}

	public void refresh_fieldsAddNew(){

		lookupParentAcct.setValue("");
		txtAcctID.setText("");
		txtName.setText("");
		cmbType.setSelectedItem(null);
		cmbBalance.setSelectedItem(null);
		cmbStatus.setSelectedItem(null);
		chkFiltered.setSelected(false);

	}

	public void refresh_tablesMain(){

		//reset table 1
		FncTables.clearTable(modelAcctList);
		FncTables.clearTable(modelAcctList_total);			
		rowHeaderAcctList = tblAcctList.getRowHeader22();
		scrollAcctList.setRowHeaderView(rowHeaderAcctList);	
		modelAcctList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

		//reset table 2
		FncTables.clearTable(modelSubAcct);
		FncTables.clearTable(modelSubAcctList_total);			
		rowHeaderSubAcct = tblSubAcct.getRowHeader22();
		scrollSubAcct.setRowHeaderView(rowHeaderSubAcct);	
		modelSubAcctList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
		
		
	}

	public void refresh_tables(){		

		//reset table 2
		FncTables.clearTable(modelAcctList);
		FncTables.clearTable(modelAcctList_total);			
		rowHeaderAcctList = tblAcctList.getRowHeader();
		scrollAcctList.setRowHeaderView(rowHeaderAcctList);	
		modelAcctList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

		//reset table 3
		FncTables.clearTable(modelSubAcct);
		FncTables.clearTable(modelSubAcct_total);			
		rowHeaderSubAcct = tblSubAcct.getRowHeader();
		scrollSubAcct.setRowHeaderView(rowHeaderSubAcct);	
		modelSubAcctList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
	}

	public void enableButtons(Boolean a, Boolean b, Boolean d, Boolean e ) {

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnPreview.setEnabled(d);
		btnCancel.setEnabled(e);

	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		KEYBOARD_MANAGER.focusNextComponent();							

		enable_fields(true);
		enableButtons(true, false, true, true);
		displayAccountList(modelAcctList, rowHeaderAcctList, 1, "A", "");
		
		lookupCompany.setValue(co_id);
}
	

	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {		

		if(e.getActionCommand().equals("Cancel")){

			enable_fields(true);	
			refresh_tablesMain();
			enableButtons(true, false, false, false);
			cmbLevel.setSelectedIndex(0);
			//txtSearch.setText("");
			chkIncludeInactive.setSelected(false);	
			generateList();
			txtDescription.setText("");
		}

		if (e.getActionCommand().equals("Add")) { addNewAcct(); }		

		//if (e.getActionCommand().equals("Save")) { saveNewAcct(); }		

		if (e.getActionCommand().equals("Edit")) { editAcct(); }	
		
		if (e.getActionCommand().equals("Preview")) { 
			
			String criteria = "Chart of Accounts";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.FullName);
			
			FncReport.generateReport("/Reports/rptChartOfAccounts.jasper", reportTitle, company, mapParameters); 
			
		
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
				int column 	= tblAcctList.getSelectedColumn();
				if (column==12) {menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false);}
				if (column==13) {menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ;  mniopenJV.setEnabled(true);}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column 	= tblAcctList.getSelectedColumn();
				if (column==12) {menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(true);  mniopenPV.setEnabled(true) ; mniopenJV.setEnabled(false);}
				if (column==13) {menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniopenRPLF.setEnabled(false); mniopenPV.setEnabled(false) ;  mniopenJV.setEnabled(true);}
			}
		}
	}

	public void addNewAcct(){		

		cmbLevelNew.setEnabled(true);
		
		to_do = "addnew";

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewAcct, "Add New Account",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			generateList();
		} // CLOSED_OPTION

	}

	public void saveNewAcct(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete account details.", "Incomplete Detail", 
				JOptionPane.INFORMATION_MESSAGE);}
		else {			

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();	
				if(to_do.equals("addnew")) 
				{		
					insertNewAccount(db);	
					updateParentAccountWSub(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"New account saved.","Information",JOptionPane.INFORMATION_MESSAGE);
					cmbLevelNew.setSelectedIndex(0);
					lookupParentAcct.setValue("");
					refresh_fieldsAddNew();
					txtAcctID.setText(sql_getNextGrp()+"-00-00-000");
					txtAcctDesc.setText(" Description :");
					//generateList();
				}
				else if(to_do.equals("edit_acct")) 
				{
					updateAccount(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"Account updated.","Information",JOptionPane.INFORMATION_MESSAGE);
					cmbLevelNew.setSelectedIndex(0);
					lookupParentAcct.setValue("");
					refresh_fieldsAddNew();
					txtAcctID.setText(sql_getNextGrp()+"-00-00-000");
					txtAcctDesc.setText(" Description :");
					//generateList();
				}
				

			}}	
	}

	public void editAcct(){

		cmbLevelNew.setEnabled(false);
	
		int row = tblAcctList.getSelectedRow();			

		String acct_id 	 = tblAcctList.getValueAt(row,0).toString().trim();	
		String acct_name = tblAcctList.getValueAt(row,1).toString().trim();
		String type_name = tblAcctList.getValueAt(row,2).toString().trim();
		String status    = tblAcctList.getValueAt(row,8).toString().trim();
		String balance   = tblAcctList.getValueAt(row,3).toString().trim();
		String remarks   = txtDescription.getText().trim();
		Boolean filtered = (Boolean) modelAcctList.getValueAt(row, 10);

		txtAcctID.setText(acct_id);
		txtName.setText(acct_name);
		txtAcctDesc.setText(remarks);
		
		if (type_name.equals("ASSETS")) {cmbType.setSelectedIndex(0);}
		else if (type_name.equals("LIABILITIES")) {cmbType.setSelectedIndex(1);}
		else if (type_name.equals("EQUITY")) {cmbType.setSelectedIndex(2);}
		else if (type_name.equals("SALES & REVENUE")) {cmbType.setSelectedIndex(3);}
		else if (type_name.equals(null)||type_name.equals("")) {cmbType.setSelectedItem(null);}

		if (status.equals("Active")) {cmbStatus.setSelectedIndex(0);} else {cmbStatus.setSelectedIndex(1);}

		if (balance.equals("Debit")) {cmbBalance.setSelectedIndex(0);} 
		else if (balance.equals("Credit")) {cmbBalance.setSelectedIndex(1);}
		else if (balance.equals("null")) {cmbBalance.setSelectedItem(null);}
		
		chkFiltered.setSelected(filtered);
		chkFiltered.setEnabled(false);
		
		if(UserInfo.EmployeeCode.equals("900054") || UserInfo.EmployeeCode.equals("900925") || UserInfo.ADMIN) {
			chkFiltered.setEnabled(true);
		}

		to_do = "edit_acct";

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewAcct, "Edit Account",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			generateList();
		} // CLOSED_OPTION

	}



	//select, lookup and get statements	
	public String getChartofAccount(Integer lvl){ //ok

		String sql = 
			"select acct_id as \"Acct ID\", " +
			"trim(acct_name) as \"Acct Name\", " +
			"bs_is as \"Balance\" \r\n" + 
			"from mf_boi_chart_of_accounts \r\n" + 
			"where status_id = 'A' \r\n" + 
			"and acct_level = "+lvl+" " +
			"order by acct_id ";		
		return sql;

	}

	public static String sql_getAcctRemarks(String acct) {//ok	

		String acct_id = "";
		String SQL = 
			"select acct_remark from mf_boi_chart_of_accounts where acct_id = '"+acct+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			acct_id = (String) db.getResult()[0][0];
		}else{
			acct_id = null;
		}

		return acct_id;
	}

	public static String sql_getNextGrp() {//ok	

		String grp = "";
		String SQL = 
			"select distinct (trim(to_char(( select max(cast(substr(acct_id,1,2) as int)) + 1 " +
			"from mf_boi_chart_of_accounts where acct_level = 1  ),'00'))) from mf_boi_chart_of_accounts" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			grp = (String) db.getResult()[0][0];
		}else{
			grp = null;
		}

		return grp;
	}

	public static String sql_getNextSubGrp(String acct_id) {//ok	

		String subgrp = "";
		String SQL = 
			"select substr('"+acct_id+"',1,2) || '-' || (select distinct (trim(to_char((select coalesce(max(cast(substr(acct_id,4,2) as int)),0) + 1 \r\n" + 
			"	from (select * from mf_boi_chart_of_accounts where substr(acct_id,1,2) = '"+acct_id.substring(0,2)+"' and substr(acct_id,4,2) != '99' \r\n" + 
			"	)as a),'00'))) from mf_boi_chart_of_accounts) || '-00-000'" ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			subgrp = (String) db.getResult()[0][0];
		}else{
			subgrp = "";
		}

		return subgrp;
	}

	public static String sql_getNextMainAcct(String acct_id) {//ok	

		String mainacct = "";
		String SQL = 
			"select substr('"+acct_id+"',1,5) || '-' || (select distinct (trim(to_char((select coalesce(max(cast(substr(acct_id,7,2) as int)),0) + 1 \r\n" + 
			"	from (select * from mf_boi_chart_of_accounts where substr(acct_id,1,5) = '"+acct_id.substring(0,5)+"' and substr(acct_id,7,2) != '99' \r\n" + 
			"	)as a),'00'))) from mf_boi_chart_of_accounts) || '-000'" ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			mainacct = (String) db.getResult()[0][0];
		}else{
			mainacct = "";
		}

		return mainacct;
	}

	public static String sql_getNextSubAcct(String acct_id) {//ok	

		String subacct = "";
		String SQL = 
			"select substr('"+acct_id+"',1,8) || '-' || (select distinct (trim(to_char((select coalesce(max(cast(substr(acct_id,10,3) as int)),0) + 1 \r\n" + 
			"	from (select * from mf_boi_chart_of_accounts where substr(acct_id,1,8) = '"+acct_id.substring(0,8)+"' and substr(acct_id,10,3) != '099' \r\n" + 
			"	)as a),'000'))) from mf_boi_chart_of_accounts) " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			subacct = (String) db.getResult()[0][0];
		}else{
			subacct = "";
		}

		return subacct;
	}


	//table operations	
	private static void adjustRowHeight(_JTableMain tbl){		

		int rw = tbl.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tbl.setRowHeight(x, 22);			
			x++;
		}
	}


	//save and insert
	public void insertNewAccount(pgUpdate db) {		

		String acct = lookupParentAcct.getText();
		Integer level = cmbLevelNew.getSelectedIndex()+1;
		Integer type = cmbType.getSelectedIndex();
		Boolean filter = chkFiltered.isSelected();

		//account parents
		String parent = "";
		if(level==1){
			parent = "null, null, null,  \n";
		}else if(level==2){
			parent = "'"+acct.substring(0,2)+"-00-00-000', null, null";
		}else if(level==3){
			parent = "'"+acct.substring(0,2)+"-00-00-000'," +"'"+acct.substring(0,5)+"-00-000', null \n";
		}else if(level==4){
			parent = "'"+acct.substring(0,2)+"-00-00-000'," +"'"+acct.substring(0,5)+"-00-000',"+"'"+acct.substring(0,8)+"-000'  \n";
		} 

		String sqlDetail = 
			"INSERT into mf_boi_chart_of_accounts(\n"
			+ "	acct_id, acct_name, acct_type, acct_grp_id, acct_subgrp_id, acct_mainacct_id, dbt_cdt, bs_is, proj_id, status_id, created_by, date_created, edited_by, date_edited, old_acct_id, acct_remark, acct_level, w_subacct, ledger_part_ids, pay_part_ids, type_group_id, corollary, filtered)"
			+ " values (" +
			"'"+txtAcctID.getText().trim()+"',  \n" +  	//1
			"'"+txtName.getText().trim().replace("'", "''")+"',  \n" +  	//2
			"'"+cmbType.getSelectedItem()+"',  \n" ;  	//3

		//account parents
		if(level==1) {
			sqlDetail = sqlDetail + "null, null, null,  \n";
		}else{
			sqlDetail = sqlDetail + ""+parent+",  \n";
		} 

		//account balance
		if(cmbBalance.getSelectedIndex()==0) {
			sqlDetail = sqlDetail + "'D',  \n";
		}else if(cmbBalance.getSelectedIndex()==1){
			sqlDetail = sqlDetail + "'C',  \n";
		} 

		//type
		if(type==0||type==1||type==2) {
			sqlDetail = sqlDetail + "'BS',  \n";
		} else if(type==3) {
			sqlDetail = sqlDetail + "'IS',  \n";
		} 

		sqlDetail = sqlDetail +			

		"null, \n" +
		"'A', \n" +
		"'"+UserInfo.EmployeeCode+"', \n" +
		"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	
		"'', \n" +
		"null, \n" +
		"null, \n" +
		"'"+txtAcctDesc.getText().replace("'","''")+"', \n" +
		""+level+", \n" +
		"null, \n" +
		"null, \n" +
		"null, \n" +
		"null,  \n" +
		"null, \n"+
		""+filter+")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void updateParentAccountWSub(pgUpdate db){

		String sqlDetail = 
			"update mf_boi_chart_of_accounts set w_subacct = true where trim(acct_id) = '"+lookupParentAcct.getText().trim()+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	public void updateAccount(pgUpdate db) {		

		String acct = txtAcctID.getText();
		Integer type = cmbType.getSelectedIndex();
		Integer status = cmbStatus.getSelectedIndex();

		String sqlDetail = 
			"update mf_boi_chart_of_accounts set " +		
			"acct_name = '"+txtName.getText().trim().replace("'", "''")+"',  \n" +  	//2
			"acct_type = '"+cmbType.getSelectedItem()+"',  \n" ;  	//3

		//account balance
		if(cmbBalance.getSelectedIndex()==0) {sqlDetail = sqlDetail + "dbt_cdt = 'D',  \n";} 
		else if(cmbBalance.getSelectedIndex()==1)  {sqlDetail = sqlDetail + "dbt_cdt = 'C',  \n";} 

		//type
		if(type==0||type==1||type==2) {sqlDetail = sqlDetail + "bs_is = 'BS',  \n";} 
		else if(type==3)  {sqlDetail = sqlDetail + "bs_is = 'IS',  \n";} 
		
		//status
		if(status==0) {sqlDetail = sqlDetail + "status_id = 'A',  \n";} 
		else if(status==1)  {sqlDetail = sqlDetail + "status_id = 'I',  \n";} 
		
		sqlDetail = sqlDetail +		
		
		"edited_by = '"+UserInfo.EmployeeCode+"', \n" +
		"date_edited = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"', " +
		"acct_remark = '"+txtAcctDesc.getText().replace("'","''")+"' ,\n" +
		"filtered = "+chkFiltered.isSelected()+" \n"+
		"where acct_id = '"+acct+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	
	//validation
	private Boolean checkCompleteDetails(){

		boolean x = false;		

		String acctid, acctname;
		String type, balance, status = "";

		try { type = cmbType.getSelectedItem().toString();} catch (NullPointerException e) { type 	= ""; }
		try { balance = cmbBalance.getSelectedItem().toString();} catch (NullPointerException e) { balance 	= ""; }
		try { status = cmbStatus.getSelectedItem().toString();} catch (NullPointerException e) { status	= ""; }
		
		
		acctid 		= txtAcctID.getText();
		acctname 	= txtName.getText();

		if (acctid.equals("") || acctname.equals("")||type.equals("")||balance.equals("")||status.equals("")) {x=false;} 
		else {x=true;}		

		return x;
	}

}
