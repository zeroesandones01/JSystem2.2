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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelBank;
import tablemodel.modelBankBranch;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
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

public class AddBank extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Bank / Bank Branch";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlBanks;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlBankBranch;
	private JPanel pnlComp_a1_a;
	private JPanel pnlComp_a1_b;

	private modelBank modelBank;
	private modelBank modelBank_total;

	private _JLookup lookupCompany;

	private JButton btnCancel;
	private JButton btnAddNew;
	private JButton btnEdit;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblCompany;
	private JLabel lblStatus;

	private _JTagLabel tagCompany;
	
	private _JScrollPaneMain scrollBank;
	private _JScrollPaneTotal scrollBank_total;
	private _JTableMain tblBank;
	private JList rowHeaderBank;
	private _JTableTotal tblBank_total;

	private JComboBox cmbStatus;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		

	String co_id 	= "";		
	String company 	= "";
	String company_logo;	
	String to_do 	= "addnewbank";  //to distinguish whether to add new agent or new sched.
	private _JScrollPaneMain scrollBankBranch;
	private modelBankBranch modelBankBranch;
	private _JTableMain tblBankBranch;
	private JList rowHeaderBankBranch;
	private _JScrollPaneTotal scrollBankBranch_total;
	private modelBankBranch modelBankBranch_total;
	private _JTableTotal tblBankBranch_total;

	String bank_id = "";
	private JPanel pnlSelect;
	private JButton btnOK;
	private JRadioButton rbtnBank;
	private JRadioButton rbtnBankBranch;
	private JPanel pnlAddNewBank;
	private JPanel pnlAddNewBank_a;
	private JPanel pnlAddNewBank_a1;
	private JLabel lblBankID;
	private Component lblBankName;
	private JLabel lblBankAlias;
	private JLabel lblEntityID;
	private JPanel pnlAddNewBank_a2;
	private JXTextField txBankID;
	private JXTextField txBankName;
	private JXTextField txBankAlias;
	private _JLookup lookupEntity;
	private JPanel pnlAddNewBank_b;
	private JScrollPane scpRemarks;
	private JTextArea txtBankDesc;
	private JPanel pnlAddNewBank_c;
	private JButton btnSaveBank;
	
	String bank_name 	= "";
	String bank_alias 	= "";
	String bank_entity 	= "";
	String bank_status 	= "";
	String bank_desc 	= "";
	
	String branch_id 	= "";
	String bank_id_branch = "";
	String location 	= "";
	String branch_status = "";
	
	private JPanel pnlAddNewBranch;
	private JPanel pnlAddNewBranch_a;
	private JPanel pnlAddNewBranch_a1;
	private JPanel pnlAddNewBranch_a2;
	private JPanel pnlAddNewBranch_c;
	private JButton btnSaveBranch;
	private JLabel lblBranchID;
	private JLabel lblBranchName;
	private JLabel lblBankID_branch;
	private JXTextField txBranchID;
	private JXTextField txtLocation;
	private _JLookup lookupBank_branch;
	private JComboBox cmbStatusBranch;
	private JPanel pnlSearch_a2;
	private JXTextField txtSearch;

	public AddBank() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AddBank(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public AddBank(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(749, 543));
		this.setBounds(0, 0, 749, 543);

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
									
									displayBanks(modelBank,rowHeaderBank,modelBank_total);
									
									btnAddNew.setEnabled(true);
									btnEdit.setEnabled(false);
									btnCancel.setEnabled(false);
									txtSearch.setEnabled(true);	
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
				
				pnlSearch_a2 = new JPanel(new BorderLayout(0,0));
				pnlComp_a2.add(pnlSearch_a2, BorderLayout.CENTER);	
				pnlSearch_a2.setPreferredSize(new java.awt.Dimension(778, 36));	
				pnlSearch_a2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					txtSearch = new JXTextField("");
					pnlSearch_a2.add(txtSearch);
					txtSearch.setEnabled(false);	
					txtSearch.setEditable(true);	
					txtSearch.setBounds(120, 25, 300, 22);	
					txtSearch.setHorizontalAlignment(JTextField.CENTER);	
					txtSearch.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	
							displayBanks(modelBank,rowHeaderBank,modelBank_total);
						}				 
					});					
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

				pnlBanks = new JPanel();
				pnlSubTable.add(pnlBanks, BorderLayout.CENTER);
				pnlBanks.setLayout(new BorderLayout(0,0));
				pnlBanks.setBorder(lineBorder);		
				pnlBanks.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlBanks.setBorder(JTBorderFactory.createTitleBorder("List of Banks"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollBank = new _JScrollPaneMain();
						pnlBanks.add(scrollBank, BorderLayout.CENTER);
						{
							modelBank = new modelBank();

							tblBank = new _JTableMain(modelBank);
							scrollBank.setViewportView(tblBank);
							tblBank.addMouseListener(this);								
							tblBank.setSortable(false);
							tblBank.getColumnModel().getColumn(0).setPreferredWidth(80);
							tblBank.getColumnModel().getColumn(1).setPreferredWidth(80);
							tblBank.getColumnModel().getColumn(2).setPreferredWidth(250);
							tblBank.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {clickTable();}							
								public void keyPressed(KeyEvent e) {clickTable();}	

							}); 
							tblBank.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblBank.rowAtPoint(e.getPoint()) == -1){}
									else{tblBank.setCellSelectionEnabled(true);}
									clickTable();
									btnEdit.setActionCommand("EditBank");
									btnEdit.setText("Edit Bank");
									btnEdit.setEnabled(true);
									btnCancel.setEnabled(true);
								}
								public void mouseReleased(MouseEvent e) {
									if(tblBank.rowAtPoint(e.getPoint()) == -1){}
									else{tblBank.setCellSelectionEnabled(true);}
									clickTable();
									btnEdit.setActionCommand("EditBank");
									btnEdit.setText("Edit Bank");
									btnEdit.setEnabled(true);
									btnCancel.setEnabled(true);
								}
							});

						}
						{
							rowHeaderBank = tblBank.getRowHeader22();
							scrollBank.setRowHeaderView(rowHeaderBank);
							scrollBank.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollBank_total = new _JScrollPaneTotal(scrollBank);
							pnlBanks.add(scrollBank_total, BorderLayout.SOUTH);
							{
								modelBank_total = new modelBank();
								modelBank_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

								tblBank_total = new _JTableTotal(modelBank_total, tblBank);
								tblBank_total.setFont(dialog11Bold);
								scrollBank_total.setViewportView(tblBank_total);
								((_JTableTotal) tblBank_total).setTotalLabel(0);
							}
						}
					}
				} 
				//end 
			}
			{
				pnlBankBranch = new JPanel(new BorderLayout(5, 5));
				pnlSubTable.add(pnlBankBranch, BorderLayout.SOUTH);	
				pnlBankBranch.setPreferredSize(new java.awt.Dimension(735, 215));	
				pnlBankBranch.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
				pnlBankBranch.setBorder(JTBorderFactory.createTitleBorder("List of Bank Branches"));

				{
					//start of Commission Schedule (by client)
					{			

						{
							scrollBankBranch = new _JScrollPaneMain();
							pnlBankBranch.add(scrollBankBranch, BorderLayout.CENTER);
							{
								modelBankBranch = new modelBankBranch();

								tblBankBranch = new _JTableMain(modelBankBranch);
								scrollBankBranch.setViewportView(tblBankBranch);
								tblBankBranch.addMouseListener(this);								
								tblBankBranch.setSortable(false);
								tblBankBranch.getColumnModel().getColumn(0).setPreferredWidth(80);
								tblBankBranch.getColumnModel().getColumn(1).setPreferredWidth(80);
								tblBankBranch.getColumnModel().getColumn(2).setPreferredWidth(250);
								tblBankBranch.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent evt) {clickTable();}														
									public void keyPressed(KeyEvent e) {clickTable();}			

								}); 
								tblBankBranch.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblBankBranch.rowAtPoint(e.getPoint()) == -1){}
										else{tblBankBranch.setCellSelectionEnabled(true);}
										btnEdit.setActionCommand("EditBranch");
										btnEdit.setText("Edit Branch");
										btnEdit.setEnabled(true);	

									}
									public void mouseReleased(MouseEvent e) {
										if(tblBankBranch.rowAtPoint(e.getPoint()) == -1){}
										else{tblBankBranch.setCellSelectionEnabled(true);}
										btnEdit.setActionCommand("EditBranch");
										btnEdit.setText("Edit Branch");
										btnEdit.setEnabled(true);	

									}
								});

							}
							{
								rowHeaderBankBranch = tblBankBranch.getRowHeader22();
								scrollBankBranch.setRowHeaderView(rowHeaderBankBranch);
								scrollBankBranch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
							{
								scrollBankBranch_total = new _JScrollPaneTotal(scrollBankBranch);
								pnlBankBranch.add(scrollBankBranch_total, BorderLayout.SOUTH);
								{
									modelBankBranch_total = new modelBankBranch();
									modelBankBranch_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

									tblBankBranch_total = new _JTableTotal(modelBankBranch_total, tblBankBranch);
									tblBankBranch_total.setFont(dialog11Bold);
									scrollBankBranch_total.setViewportView(tblBankBranch_total);
									((_JTableTotal) tblBankBranch_total).setTotalLabel(0);
								}
							}
						}
					} 

				}
				//end 			
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
					btnAddNew = new JButton("Add New");
					pnlSouthCenterb.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
				}
				{
					btnEdit = new JButton("Edit Bank");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
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

	{
		pnlSelect= new JPanel();
		pnlSelect.setLayout(null);
		pnlSelect.setPreferredSize(new java.awt.Dimension(280, 80));
		
		{

			rbtnBank = new JRadioButton();
			pnlSelect.add(rbtnBank);
			rbtnBank.setText("Add Bank");
			rbtnBank.setBounds(20, 15, 80, 21);
			rbtnBank.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			rbtnBank.setSelected(true);
			rbtnBank.setEnabled(true);
			rbtnBank.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae){	
					if (rbtnBank.isSelected()==true) {rbtnBankBranch.setSelected(false);to_do = "addnewbank";}
					else {rbtnBankBranch.setSelected(true);to_do = "addnewbranch";}
				}});					

		}
		{

			rbtnBankBranch = new JRadioButton();
			pnlSelect.add(rbtnBankBranch);
			rbtnBankBranch.setText("Add Bank Branch");
			rbtnBankBranch.setBounds(150, 15, 125, 21);
			rbtnBankBranch.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			rbtnBankBranch.setSelected(false);
			rbtnBankBranch.setEnabled(true);
			rbtnBankBranch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae){		
					if (rbtnBankBranch.isSelected()==true) {rbtnBank.setSelected(false);to_do = "addnewbranch";}
					else {rbtnBank.setSelected(true);to_do = "addnewbank";}
				}});					

		}
		{
			btnOK = new JButton();
			pnlSelect.add(btnOK);
			btnOK.setBounds(95, 58, 69, 22);
			btnOK.setText("OK");
			btnOK.setFocusable(false);
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlSelect);
					optionPaneWindow.dispose();
				}
			});
		}
	}
	{

		pnlAddNewBank = new JPanel();
		pnlAddNewBank.setLayout(new BorderLayout(5, 5));
		pnlAddNewBank.setBorder(lineBorder);		
		pnlAddNewBank.setPreferredSize(new java.awt.Dimension(382, 400));		

		{		
			pnlAddNewBank_a = new JPanel(new BorderLayout(5, 5));
			pnlAddNewBank.add(pnlAddNewBank_a, BorderLayout.NORTH);				
			pnlAddNewBank_a.setPreferredSize(new java.awt.Dimension(921, 41));
			pnlAddNewBank_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlAddNewBank_a.setPreferredSize(new java.awt.Dimension(380, 180));		

			{
				pnlAddNewBank_a1 = new JPanel(new GridLayout(5, 5, 5, 5));
				pnlAddNewBank_a.add(pnlAddNewBank_a1, BorderLayout.WEST);				
				pnlAddNewBank_a1.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBank_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlAddNewBank_a1.setPreferredSize(new java.awt.Dimension(107, 145));		

				{
					lblBankID = new JLabel("Bank ID", JLabel.TRAILING);
					pnlAddNewBank_a1.add(lblBankID);
					lblBankID.setEnabled(true);	
					lblBankID.setPreferredSize(new java.awt.Dimension(136, 24));
				}	
				{
					lblBankName = new JLabel("Bank Name", JLabel.TRAILING);
					pnlAddNewBank_a1.add(lblBankName);
					lblBankName.setEnabled(true);	
					lblBankName.setPreferredSize(new java.awt.Dimension(136, 24));
				}	
				{
					lblBankAlias = new JLabel("Bank Alias", JLabel.TRAILING);
					pnlAddNewBank_a1.add(lblBankAlias);
					lblBankAlias.setEnabled(true);	
					lblBankAlias.setPreferredSize(new java.awt.Dimension(136, 24));
				}	
				{
					lblEntityID = new JLabel("Entity ID", JLabel.TRAILING);
					pnlAddNewBank_a1.add(lblEntityID);
					lblEntityID.setEnabled(true);	
					lblEntityID.setPreferredSize(new java.awt.Dimension(136, 24));
				}	
				{
					lblStatus = new JLabel("Status", JLabel.TRAILING);
					pnlAddNewBank_a1.add(lblStatus);
					lblStatus.setEnabled(true);	
					lblStatus.setPreferredSize(new java.awt.Dimension(136, 24));
				}
			}
			{
				pnlAddNewBank_a2 = new JPanel(new GridLayout(5,5,5, 5));
				pnlAddNewBank_a.add(pnlAddNewBank_a2, BorderLayout.CENTER);				
				pnlAddNewBank_a2.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBank_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlAddNewBank_a2.setPreferredSize(new java.awt.Dimension(200, 150));
				
				{
					txBankID = new JXTextField("");
					pnlAddNewBank_a2.add(txBankID);
					txBankID.setEnabled(true);	
					txBankID.setEditable(false);
					txBankID.setBounds(120, 25, 150, 22);	
					txBankID.setHorizontalAlignment(JTextField.CENTER);
					txBankID.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					txBankID.setText(sql_getNextBankID());
				}	
				{
					txBankName = new JXTextField("");
					pnlAddNewBank_a2.add(txBankName);
					txBankName.setEnabled(true);	
					txBankName.setEditable(true);
					txBankName.setBounds(120, 25, 300, 22);	
					txBankName.setHorizontalAlignment(JTextField.CENTER);
					txBankName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}	
				{
					txBankAlias = new JXTextField("");
					pnlAddNewBank_a2.add(txBankAlias);
					txBankAlias.setEnabled(true);	
					txBankAlias.setEditable(true);
					txBankAlias.setBounds(120, 25, 300, 22);	
					txBankAlias.setHorizontalAlignment(JTextField.CENTER);
					txBankAlias.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}	
				{
					lookupEntity = new _JLookup(null, "Parent Account ID", 2, 2);
					pnlAddNewBank_a2.add(lookupEntity);
					lookupEntity.setBounds(20, 27, 20, 25);
					lookupEntity.setReturnColumn(0);
					lookupEntity.setFilterName(true);
					lookupEntity.setEnabled(true);	
					lookupEntity.setLookupSQL(getEntityList());
					lookupEntity.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupEntity.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

							}
						}
					});	
				}			
				{
					String status[] = {"Active","Inactive"};					
					cmbStatus = new JComboBox(status);
					pnlAddNewBank_a2.add(cmbStatus);
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
			}

			pnlAddNewBank_b = new JPanel(new BorderLayout(5, 5));
			pnlAddNewBank.add(pnlAddNewBank_b, BorderLayout.CENTER);				
			pnlAddNewBank_b.setPreferredSize(new java.awt.Dimension(921, 41));
			pnlAddNewBank_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlAddNewBank_b.setPreferredSize(new java.awt.Dimension(500, 150));		
			{
				scpRemarks = new JScrollPane();
				pnlAddNewBank_b.add(scpRemarks);
				scpRemarks.setBounds(82, 7, 309, 61);
				scpRemarks.setOpaque(true);
				scpRemarks.setPreferredSize(new java.awt.Dimension(375, 159));

				{
					txtBankDesc = new JTextArea();
					scpRemarks.add(txtBankDesc);
					scpRemarks.setViewportView(txtBankDesc);
					txtBankDesc.setText(" Description :");
					txtBankDesc.setBounds(77, 3, 250, 81);
					txtBankDesc.setLineWrap(true);
					txtBankDesc.setPreferredSize(new java.awt.Dimension(366, 133));
					txtBankDesc.setEditable(true);
					txtBankDesc.setEnabled(true);
					txtBankDesc.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	

						}});	
				}		

			}

			pnlAddNewBank_c = new JPanel(new BorderLayout(5, 5));
			pnlAddNewBank.add(pnlAddNewBank_c, BorderLayout.SOUTH);				
			pnlAddNewBank_c.setPreferredSize(new java.awt.Dimension(921, 41));
			pnlAddNewBank_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlAddNewBank_c.setPreferredSize(new java.awt.Dimension(500, 40));	

			{
				btnSaveBank = new JButton("Save New Bank");
				pnlAddNewBank_c.add(btnSaveBank);
				btnSaveBank.setActionCommand("SaveBank");
				btnSaveBank.addActionListener(this);
				btnSaveBank.setEnabled(true);
			}

		}
	}
	{

		pnlAddNewBranch = new JPanel();
		pnlAddNewBranch.setLayout(new BorderLayout(5, 5));
		pnlAddNewBranch.setBorder(lineBorder);		
		pnlAddNewBranch.setPreferredSize(new java.awt.Dimension(382, 190));		

		{		
			pnlAddNewBranch_a = new JPanel(new BorderLayout(5, 5));
			pnlAddNewBranch.add(pnlAddNewBranch_a, BorderLayout.NORTH);				
			pnlAddNewBranch_a.setPreferredSize(new java.awt.Dimension(921, 41));
			pnlAddNewBranch_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlAddNewBranch_a.setPreferredSize(new java.awt.Dimension(380, 180));		

			{
				pnlAddNewBranch_a1 = new JPanel(new GridLayout(5, 5, 5, 5));
				pnlAddNewBranch_a.add(pnlAddNewBranch_a1, BorderLayout.WEST);				
				pnlAddNewBranch_a1.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBranch_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlAddNewBranch_a1.setPreferredSize(new java.awt.Dimension(107, 145));		

				{
					lblBranchID = new JLabel("Branch ID", JLabel.TRAILING);
					pnlAddNewBranch_a1.add(lblBranchID);
					lblBranchID.setEnabled(true);	
					lblBranchID.setPreferredSize(new java.awt.Dimension(136, 24));
				}	
				{
					lblBankID_branch = new JLabel("Bank ID", JLabel.TRAILING);
					pnlAddNewBranch_a1.add(lblBankID_branch);
					lblBankID_branch.setEnabled(true);	
					lblBankID_branch.setPreferredSize(new java.awt.Dimension(136, 24));
				}	
				{
					lblBranchName = new JLabel("Branch Name", JLabel.TRAILING);
					pnlAddNewBranch_a1.add(lblBranchName);
					lblBranchName.setEnabled(true);	
					lblBranchName.setPreferredSize(new java.awt.Dimension(136, 24));
				}	
				{
					lblStatus = new JLabel("Status", JLabel.TRAILING);
					pnlAddNewBranch_a1.add(lblStatus);
					lblStatus.setEnabled(true);	
					lblStatus.setPreferredSize(new java.awt.Dimension(136, 24));
				}
			}
			{
				pnlAddNewBranch_a2 = new JPanel(new GridLayout(5,5,5, 5));
				pnlAddNewBranch_a.add(pnlAddNewBranch_a2, BorderLayout.CENTER);				
				pnlAddNewBranch_a2.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlAddNewBranch_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlAddNewBranch_a2.setPreferredSize(new java.awt.Dimension(200, 150));
				
				{
					txBranchID = new JXTextField("");
					pnlAddNewBranch_a2.add(txBranchID);
					txBranchID.setEnabled(true);	
					txBranchID.setEditable(false);
					txBranchID.setBounds(120, 25, 150, 22);	
					txBranchID.setHorizontalAlignment(JTextField.CENTER);
					txBranchID.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					txBranchID.setText(sql_getNextBranchID());
				}					
				{
					lookupBank_branch = new _JLookup(null, "Bank", 2, 2);
					pnlAddNewBranch_a2.add(lookupBank_branch);
					lookupBank_branch.setBounds(20, 27, 20, 25);
					lookupBank_branch.setReturnColumn(0);
					lookupBank_branch.setEnabled(true);	
					lookupBank_branch.setFilterName(true);
					lookupBank_branch.setLookupSQL(getBank());
					lookupBank_branch.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupBank_branch.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

							}
						}
					});	
				}	
				{
					txtLocation = new JXTextField("");
					pnlAddNewBranch_a2.add(txtLocation);
					txtLocation.setEnabled(true);	
					txtLocation.setEditable(true);
					txtLocation.setBounds(120, 25, 300, 22);	
					txtLocation.setHorizontalAlignment(JTextField.CENTER);
					txtLocation.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}	
				{
					String status[] = {"Active","Inactive"};					
					cmbStatusBranch = new JComboBox(status);
					pnlAddNewBranch_a2.add(cmbStatusBranch);
					cmbStatusBranch.setBounds(537, 15, 160, 21);	
					cmbStatusBranch.setEnabled(true);
					cmbStatusBranch.setSelectedIndex(0);	
					cmbStatusBranch.setPreferredSize(new java.awt.Dimension(89, 26));
					cmbStatusBranch.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{

						}
					});		
				}
			}
			
			pnlAddNewBranch_c = new JPanel(new BorderLayout(5, 5));
			pnlAddNewBranch.add(pnlAddNewBranch_c, BorderLayout.SOUTH);				
			pnlAddNewBranch_c.setPreferredSize(new java.awt.Dimension(921, 41));
			pnlAddNewBranch_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			pnlAddNewBranch_c.setPreferredSize(new java.awt.Dimension(500, 40));	

			{
				btnSaveBranch = new JButton("Save New Branch");
				pnlAddNewBranch_c.add(btnSaveBranch);
				btnSaveBranch.setActionCommand("SaveBank");
				btnSaveBranch.addActionListener(this);
				btnSaveBranch.setEnabled(true);
			}

		}
	}

	//display tables
	public void displayBanks(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		String txt = txtSearch.getText().toUpperCase();
		
		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"a.bank_id, \r\n" + 
			"a.bank_name, \r\n" + 
			"a.bank_alias,  \r\n" + 
			"upper(trim(c.entity_name)) as created_by,\r\n" + 
			"to_char(a.date_created,'MM-dd-yyyy') as date_created,\r\n" + 
			"d.status_desc\r\n" + 
			"\r\n" + 
			"from mf_bank a\r\n" + 
			"left join em_employee b on a.created_by = b.emp_code\r\n" + 
			"left join rf_entity c on b.entity_id = c.entity_id\r\n" + 
			"left join mf_record_status d on a.status_id = d.status_id\r\n " +
			"where a.proj_server is null and a.server_id is null "; //ADDED BY MONIQUE DTD 2022-09-21; FOR LIST OF BANKS (JSYSTEM)
		
			if (txt.equals("")) {}
			else {
				sql = sql + "and upper(a.bank_alias) like '%"+txt+"%' or upper(a.bank_name) like '%"+txt+"%' "
						+ " and a.proj_server is null and a.server_id is null "; //ADDED BY MONIQUE DTD 2022-09-21; FOR LIST OF BANKS (JSYSTEM)
				
			}
		
		
			sql = sql +
			"order by a.bank_name " ;

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
			modelBank_total = new modelBank();
			modelBank_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

			tblBank_total = new _JTableTotal(modelBank_total, tblBank);
			tblBank_total.setFont(dialog11Bold);
			scrollBank_total.setViewportView(tblBank_total);
			((_JTableTotal) tblBank_total).setTotalLabel(0);}

		tblBank.packAll();				
		tblBank.getColumnModel().getColumn(0).setPreferredWidth(80);

		int row_tot = tblBank.getRowCount();			
		modelBank_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		adjustRowHeight_account(tblBank);
	}	

	public void displayBankBranch(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"a.bank_branch_id,\r\n" + 
			"a.bank_branch_location,\r\n" + 
			"upper(trim(c.entity_name)) as created_by,\r\n" + 
			"to_char(a.date_created,'MM-dd-yyyy') as date_created,\r\n" + 
			"d.status_desc\r\n" + 
			"\r\n" + 
			"from \r\n" + 
			"mf_bank_branch a\r\n" + 
			"left join em_employee b on a.created_by = b.emp_code\r\n" + 
			"left join rf_entity c on b.entity_id = c.entity_id\r\n" + 
			"left join mf_record_status d on a.status_id = d.status_id \r\n" + 
			"where a.bank_id = '"+bank_id+"' \r\n" + 
			"\r\n" + 
			"order by bank_branch_location " ;

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
			modelBankBranch_total = new modelBankBranch();
			modelBankBranch_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

			tblBankBranch_total = new _JTableTotal(modelBankBranch_total, tblBankBranch);
			tblBankBranch_total.setFont(dialog11Bold);
			scrollBankBranch_total.setViewportView(tblBankBranch_total);
			((_JTableTotal) tblBankBranch_total).setTotalLabel(0);}

		tblBankBranch.packAll();				
		tblBankBranch.getColumnModel().getColumn(0).setPreferredWidth(80);

		int row_tot = tblBankBranch.getRowCount();			
		modelBankBranch_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		adjustRowHeight_account(tblBankBranch);
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){cancel();}

		if(e.getActionCommand().equals("Add")){selectToAdd();}		

		if(e.getActionCommand().equals("EditBank")){EditBank();}
		
		if(e.getActionCommand().equals("EditBranch")){EditBranch();}
		
		if(e.getActionCommand().equals("SaveBank")){executeSave();}
		
		if(e.getActionCommand().equals("SaveBranch")){executeSave();}
		
		if(e.getActionCommand().equals("UpdateBank")){executeSave();}
		
		if(e.getActionCommand().equals("UpdateBranch")){executeSave();}


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
		btnAddNew.setEnabled(true);
		btnEdit.setEnabled(false);
		btnCancel.setEnabled(false);
		displayBanks(modelBank,rowHeaderBank,modelBank_total);
		resetTable();	
		txtSearch.setText("");	
	}

	private void executeSave(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete bank/branch details.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}

		else if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	

			if (to_do.equals("addnewbank")) 
			{
				insertNewBank(db); 
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"A new bank was added.","Information",JOptionPane.INFORMATION_MESSAGE);
				btnSaveBank.setEnabled(false);	
				btnEdit.setEnabled(false);
				displayBanks(modelBank,rowHeaderBank,modelBank_total);	
				resetTable();		
			} 
			
			if (to_do.equals("addnewbranch")) 
			{
				insertNewBranch(db); 
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"A new bank branch was added.","Information",JOptionPane.INFORMATION_MESSAGE);
				btnSaveBranch.setEnabled(false);
				btnEdit.setEnabled(false);
				resetTable();		
			} 

			else if (to_do.equals("updatebank"))
			{				
				updateBank(db); 
				db.commit();				
				JOptionPane.showMessageDialog(getContentPane(),"Bank details were successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);				
				displayBanks(modelBank,rowHeaderBank,modelBank_total);
				btnSaveBank.setEnabled(false);	
				btnEdit.setEnabled(false);
				resetTable();		
			}	
			
			else if (to_do.equals("updatebranch"))
			{				
				updateBranch(db); 
				db.commit();				
				JOptionPane.showMessageDialog(getContentPane(),"Bank branch details were successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);				
				displayBanks(modelBank,rowHeaderBank,modelBank_total);
				btnSaveBranch.setEnabled(false);
				btnEdit.setEnabled(false);
				resetTable();		
			}	
		}

		else {}	



	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();
		
		displayBanks(modelBank,rowHeaderBank,modelBank_total);
		
		btnAddNew.setEnabled(true);
		btnEdit.setEnabled(false);
		btnCancel.setEnabled(false);
		txtSearch.setEnabled(true);	
		
		lookupCompany.setValue(co_id);
	}
	
	
	//enable, disable
	private void resetTable(){
		//reset table 1
		FncTables.clearTable(modelBankBranch);
		FncTables.clearTable(modelBankBranch_total);			
		rowHeaderBankBranch = tblBankBranch.getRowHeader22();
		scrollBankBranch.setRowHeaderView(rowHeaderBankBranch);	
		
		modelBankBranch_total = new modelBankBranch();
		modelBankBranch_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

		tblBankBranch_total = new _JTableTotal(modelBankBranch_total, tblBankBranch);
		tblBankBranch_total.setFont(dialog11Bold);
		scrollBankBranch_total.setViewportView(tblBankBranch_total);
		((_JTableTotal) tblBankBranch_total).setTotalLabel(0);
	}

	//select, lookup and get statements		
	public static String sql_getBankID(String bank_alias) {

		String bank_id = "";

		String SQL = 
			"select bank_id from mf_bank where trim(bank_alias) = '"+bank_alias+"' "
					+ " and proj_server is null and server_id is null; " ; //ADDED BY MONIQUE DTD 2022-09-21; FOR LIST OF BANKS (JSYSTEM)

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			bank_id = (String) db.getResult()[0][0];
		}else{
			bank_id = "";
		}

		return bank_id;
	}

	public static String sql_getBankBranchID(String bank_branch) {

		String branch_id = "";

		String SQL = 
			"select bank_branch_id from mf_bank_branch where upper(trim(bank_branch_location)) = '"+bank_branch+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			branch_id = (String) db.getResult()[0][0];
		}else{
			branch_id = "";
		}

		return branch_id;
	}

	public static String sql_getAcct(String acct_id) {

		String acct_name = "";

		String SQL = 
			"select acct_name from mf_boi_chart_of_accounts where trim(acct_id) = '"+acct_id.trim()+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			acct_name = (String) db.getResult()[0][0];
		}else{
			acct_name = "";
		}

		return acct_name;
	}

	public String getAccount(){

		String sql = 
			"select acct_id as \"Acct ID\", " +
			"trim(acct_name) as \"Acct Name\" " +
			"from mf_boi_chart_of_accounts " +
			"where acct_id like '01-01-04%'  and status_id = 'A' " +
			"or acct_id like '01-01-05%' and status_id = 'A'" +
			"order by acct_id\r\n" ;
		return sql;

	}	

	public String getBank(){

		String sql = 
			"select trim(bank_id) as \"Bank ID\", " +
			"bank_alias as \"Bank Alias\", " +
			"trim(bank_name) as \"Bank Name\" " +
			"from mf_bank " +
			"where status_id = 'A' " +
			"and proj_server is null and server_id is null " + //ADDED BY MONIQUE DTD 2022-09-21; FOR LIST OF BANKS (JSYSTEM) 

			"order by bank_id/*::int*/  \r\n" ;

		return sql;

	}	
	
	public String getEntityList(){

		String sql = 
			"\r\n" + 
			"select \r\n" + 
			"entity_id as \"Entity ID\",\r\n" + 
			"trim(entity_name) as \"Name\",\r\n" + 
			"entity_kind as \"Kind\" \r\n" + 
			"from rf_entity " +
			"where entity_kind  = 'C' \r\n" + 
			"order by entity_name" ;
		return sql;

	}	

	public String getBankBranch(String bank){

		String sql = 
			"select bank_branch_id as \"Bank Branch ID\", " +
			"trim(bank_branch_location) as \"Address\" " +
			"from mf_bank_branch " +
			"where status_id = 'A' " +
			"and bank_id = '"+bank+"' " +
			"order by bank_branch_id \r\n" ;
		return sql;

	}	

	public static String sql_getNextBankID() {//ok	

		String bnk_id = "";
		String SQL = 
			"select max(getinteger(bank_id)::int) + 1 from mf_bank \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {bnk_id = "1";}
			else {bnk_id = db.getResult()[0][0].toString(); }	
			
		}else{
			bnk_id = "1";
		}

		return bnk_id;
	}
	
	public static String sql_getNextBranchID() {//ok	

		String branch_id = "";
		String SQL = 
			"select max(bank_branch_id::int) + 1 from mf_bank_branch \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {branch_id = "1";}
			else {branch_id = db.getResult()[0][0].toString(); }	
			
		}else{
			branch_id = "1";
		}

		return branch_id;
	}
	
	public String sql_getBankEntity() {//ok	

		String entityid = "";
		
		String SQL = 
			"select (case when entity_id is null then '' else entity_id end) from mf_bank where bank_id = '"+bank_id+"' \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {entityid = "";}
			else {entityid = db.getResult()[0][0].toString(); }	
			
		}else{
			entityid = "";
		}

		return entityid;
	}
	
	public String sql_getBankDesc() {//ok	

		String rem = "";
		
		String SQL = 
			"select (case when remarks is null then '' else remarks end) from mf_bank where bank_id = '"+bank_id+"' \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {rem = "";}
			else {rem = db.getResult()[0][0].toString(); }	
			
		}else{
			rem = "";
		}

		return rem;
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
	
	private void clickTable(){		

		Integer row = tblBank.getSelectedRow();

		bank_id 	= "";
		try { bank_id 	= tblBank.getValueAt(row,0).toString().trim();} catch (NullPointerException e) { bank_id 	= ""; }
		displayBankBranch(modelBankBranch,rowHeaderBankBranch,modelBankBranch_total);
	}


	//processes and validation
	private Boolean checkCompleteDetails(){

		boolean x = false;			
		
		if (to_do.equals("addnewbank")||to_do.equals("updatebank")) 
		{
			bank_id 	= txBankID.getText();
			bank_name 	= txBankName.getText();
			bank_alias 	= txBankAlias.getText();	

			if (bank_id.equals("") || bank_name.equals("")|| bank_alias.equals("")) {x=false;} 
			else {x=true;}			
		}
		else if (to_do.equals("addnewbranch")||to_do.equals("updatebranch")) 
		{
			bank_id_branch 	= lookupBank_branch.getText().trim();
			location 	= txtLocation.getText().trim();

			if (bank_id_branch.equals("") || location.equals("")) {x=false;} 
			else {x=true;}			
		}
		
			

		

		return x;

	}


	//save
	private void insertNewBranch(pgUpdate db){

		branch_id 		= txBankID.getText();
		bank_id_branch 	= lookupBank_branch.getText();
		location 		= txtLocation.getText();
		
		if (cmbStatusBranch.getSelectedIndex()==0) {branch_status = "A";}
		else {branch_status = "I";}			
		
		String sqlDetail = 
			"insert into mf_bank_branch \n" +
			"values ( \n" +
			"'"+bank_id_branch+"',   \n" +   		//1 
			"'"+sql_getNextBranchID()+"',   \n" +	//2 
			"'"+location+"',   \n" +				//3 
			"null,   \n" +							//4 
			"'"+branch_status+"'," +				//5
			"'"+UserInfo.EmployeeCode+"', \n" +  	//6
			"now(),  \n" +	//7
			"null,  \n" +	//8
			"null " +		//9			
			") " ;

		System.out.printf("SQL #1 - NewBankBranch: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}
	
	private void insertNewBank(pgUpdate db){

		branch_id 	= txBankID.getText();
		bank_name 	= txBankName.getText();
		bank_alias 	= txBankAlias.getText();
		bank_desc 	= txtBankDesc.getText();
		bank_entity	= lookupEntity.getText();
		
		if (cmbStatus.getSelectedIndex()==0) {bank_status = "A";}
		else {bank_status = "I";}			
		
		String sqlDetail = 
			"insert into mf_bank \n" +
			"values ( \n" +
			"'"+sql_getNextBankID()+"',   \n" +   	//1 
			"'"+bank_name+"',   \n" +	//2 
			"'"+bank_alias+"',   \n" +	//3 
			"'"+bank_entity+"',   \n" +	//4 
			"null," +					//5
			"null," +					//6
			"'"+bank_desc+"',   \n" +	//7
			"'"+bank_status+"',   \n" +	//8
			"'"+UserInfo.EmployeeCode+"', \n" +  //9
			"now(),  \n" +	//10
			"null,  \n" +	//11
			"null " +		//12			
			") " ;

		System.out.printf("SQL #1 - NewBank: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void updateBank(pgUpdate db){

		branch_id 	= txBankID.getText();
		bank_name 	= txBankName.getText();
		bank_alias 	= txBankAlias.getText();
		bank_desc 	= txtBankDesc.getText();
		bank_entity	= lookupEntity.getText();
		
		if (cmbStatus.getSelectedIndex()==0) {bank_status = "A";}
		else {bank_status = "I";}			
		
		String sqlDetail = 
			"update mf_bank set \n" +
			"bank_name = '"+bank_name+"',   \n" +
			"bank_alias = '"+bank_alias+"',   \n" +
			"entity_id = '"+bank_entity+"',   \n" +
			"remarks = '"+bank_desc+"',   \n" +
			"status_id = '"+bank_status+"',   \n" +
			"edited_by = '"+UserInfo.EmployeeCode+"',   \n" +
			"date_edited = now()   \n" +
			"where bank_id::int = "+bank_id+"  ";

		System.out.printf("SQL #1 - updateBank: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}
	
	private void updateBranch(pgUpdate db){

		branch_id 		= txBranchID.getText();
		bank_id_branch 	= lookupBank_branch.getText();
		location 		= txtLocation.getText();
		
		if (cmbStatusBranch.getSelectedIndex()==0) {branch_status = "A";}
		else {branch_status = "I";}			
						
		String sqlDetail = 
			"update mf_bank_branch set \n" +
			"bank_id = '"+bank_id_branch+"',   \n" +
			"bank_branch_location = '"+location+"',   \n" +
			"status_id = '"+branch_status+"',   \n" +
			"edited_by = '"+UserInfo.EmployeeCode+"',   \n" +
			"date_edited = now()   \n" +
			"where bank_branch_id::int = "+branch_id+"  ";

		System.out.printf("SQL #1 - updateBankBranch: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}



	//
	public void selectToAdd(){		
		
		rbtnBank.setSelected(true);
		rbtnBankBranch.setSelected(false);
		to_do 	= "addnewbank";
		
		btnSaveBank.setEnabled(true);
		txBankID.setText(sql_getNextBankID());
		txBankName.setText("");
		txBankAlias.setText("");
		lookupEntity.setValue("");
		txtBankDesc.setText("Description :");
		btnSaveBranch.setEnabled(true);
		txBranchID.setText(sql_getNextBranchID());
		lookupBank_branch.setText("");
		txtLocation.setText("");		
		
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlSelect, "Select",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {	
				
				if (rbtnBank.isSelected()==true) 
				{
					AddNewBank(); 					
				}
				else {
					AddNewBranch();							
				}
				
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION
	}
	
	public void AddNewBank(){	
		
		btnSaveBank.setActionCommand("SaveBank");
		btnSaveBank.setText("Save New Bank");
		
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBank, "Add New Bank",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {			
				
					
				
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION
	}
	
	public void EditBank(){	
		
		btnSaveBank.setActionCommand("UpdateBank");
		btnSaveBank.setText("Update Bank");
		
		to_do = "updatebank";
		
		int row = tblBank.getSelectedRow();		
		
		bank_name 	= tblBank.getValueAt(row,1).toString().trim();	
		bank_alias 	= tblBank.getValueAt(row,2).toString().trim();	
		bank_status	= tblBank.getValueAt(row,5).toString().trim();	
		bank_entity	= sql_getBankEntity();	
		bank_desc	= sql_getBankDesc();	
		
		txBankID.setText(bank_id);	
		txBankName.setText(bank_name);	
		txBankAlias.setText(bank_alias);	
		lookupEntity.setText(bank_entity);	
		txtBankDesc.setText(bank_desc);	
		
		if (bank_status.equals("ACTIVE")) 
		{
			cmbStatus.setSelectedIndex(0);	
		}
		else 
		{
			cmbStatus.setSelectedIndex(1);	
		}
		
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBank, "Edit Bank",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {			
				
					
				
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION
	}
	
	public void AddNewBranch(){	
		
		btnSaveBranch.setActionCommand("SaveBranch");
		btnSaveBranch.setText("Save Branch");
		
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBranch, "Add New Branch",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {			
				
					
				
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION
	}
	
	public void EditBranch(){	
		
		btnSaveBranch.setActionCommand("UpdateBranch");
		btnSaveBranch.setText("Update Branch");
		
		to_do = "updatebranch";
		
		int row = tblBankBranch.getSelectedRow();		
		
		branch_id 		= tblBankBranch.getValueAt(row,0).toString().trim();
		location 		= tblBankBranch.getValueAt(row,1).toString().trim();
		branch_status	= tblBankBranch.getValueAt(row,4).toString().trim();
				
		txBranchID.setText(branch_id);	
		txtLocation.setText(location);	
		lookupBank_branch.setText(bank_id);
		
		if (branch_status.equals("ACTIVE")) 
		{
			cmbStatusBranch.setSelectedIndex(0);	
		}
		else 
		{
			cmbStatusBranch.setSelectedIndex(1);	
		}
				
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddNewBranch, "Edit Branch",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {			
				
					
				
			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION
	}

	
	



}