package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXTextField;

import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Reports.Accounting.SummaryofDeposits;

import components._JInternalFrame;
import components._JTagLabel;

public class AddCheckNumber extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Add Check Number";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlNorth_comp;
	private JPanel pnlTransDtl_2a;
	private JPanel pnlTransDtl_2b;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;
	private JPanel pnlTrans_a;
	private JPanel pnlTrans_a1;
	private JPanel pnlTrans_a2;
	private JPanel pnlTrans_a2_1;
	private JPanel pnlTrans_a2_2;

	private JLabel lblCompany;
	private static JLabel lblBranch;
	private static JLabel lblFirstNo;
	private static JLabel lblStatus;
	public static JLabel lblTransNo;

	private static _JTagLabel tagBranch;

	private _JLookup lookupProject;
	public static _JLookup lookupCompany;
	private static _JLookup lookupBranch;
	public static _JLookup lookupTransNo;

	public static JTextField txtCompany;

	private static JButton btnAddNew;
	private static JButton btnCancel;
	private static JButton btnEdit;
	private static JButton btnSave;

	private static JComboBox cmbStatus;
	private static JXTextField txtFirstNo;
	private static JXTextField txtLastNo;

	public static String company;
	public static String company_logo;		

	private static _JDateChooser dteDateFrom;
	private static _JDateChooser dateDateTo;	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public String req_type_id 		= "";
	public String req_type_name		= "";
	public String payee_id 			= "";
	public String payee_name		= "";
	public String availer_id 		= "";
	public String availer_name 		= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String branch_id			= "";
	public String branch_name		= "";
	public String acct_id			= "";
	public String bank_acct_id			= "";
	public String acct_name			= "";
	public String status_id			= "";
	public String status_name		= "";
	public String emp_code		    = "";

	public static String co_id 	= "";
	public String trans_no 	= "";
	public Integer next_trans_no = 0;
	public String to_do     = "";

	private static JLabel lblUser;

	private static _JLookup lookupUser;

	private _JTagLabel tagX;

	private static _JTagLabel tagUser;

	private static JLabel lblLastNoUsed;

	private static JXTextField txtLastNoUsed;

	private static JLabel lblBank;
	private static _JLookup lookupBank;
	private static _JTagLabel tagBank;
	private static JLabel lblLastNo;


	public AddCheckNumber() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddCheckNumber(String title) {
		super(title);
		initGUI();
	}

	public AddCheckNumber(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(599, 426));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(594, 363));

			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(552, 115));

				{
					pnlNorth_comp = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorth_comp, BorderLayout.NORTH);
					pnlNorth_comp.setPreferredSize(new java.awt.Dimension(547, 63));
					pnlNorth_comp.setBorder(components.JTBorderFactory.createTitleBorder("Company"));// XXX

					{
						pnlNorthWest = new JPanel(new GridLayout(1,1, 5, 5));
						pnlNorth_comp.add(pnlNorthWest, BorderLayout.WEST);
						pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));

						{
							lblCompany = new JLabel("Company", JLabel.TRAILING);
							pnlNorthWest.add(lblCompany);
						}
						{
							lookupCompany = new _JLookup(null, "Company");
							pnlNorthWest.add(lookupCompany);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(SummaryofDeposits.company());
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){

										company = (String) data[1];
										company_logo = (String) data[3];

										String name = (String) data[1];						
										txtCompany.setText(name);

										lblTransNo.setEnabled(true);	
										lookupTransNo.setEnabled(true);	
										enableButtons(true, false, false, true);

										lookupTransNo.setLookupSQL(getTransaction());	
									}
								}
							});
						}


						pnlNorthEast = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlNorth_comp.add(pnlNorthEast, BorderLayout.CENTER);
						pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
						{
							txtCompany = new JTextField();
							pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
							txtCompany.setEditable(false);
						}

					}
				}
				{
					pnlTrans_a = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlTrans_a, BorderLayout.CENTER);	
					pnlTrans_a.setPreferredSize(new java.awt.Dimension(552, 47));
					pnlTrans_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlTrans_a.setBorder(lineBorder);

					pnlTrans_a1 = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlTrans_a.add(pnlTrans_a1, BorderLayout.WEST);	
					pnlTrans_a1.setPreferredSize(new java.awt.Dimension(72, 45));	
					pnlTrans_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblTransNo = new JLabel("Trans. No.", JLabel.TRAILING);
						pnlTrans_a1.add(lblTransNo);
						lblTransNo.setEnabled(false);	
						lblTransNo.setPreferredSize(new java.awt.Dimension(74, 45));
						lblTransNo.setFont(new java.awt.Font("Segoe UI",Font.ITALIC|Font.BOLD,12));
					}						

					pnlTrans_a2 = new JPanel(new BorderLayout(5, 5));
					pnlTrans_a.add(pnlTrans_a2, BorderLayout.CENTER);	
					pnlTrans_a2.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlTrans_a2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

					pnlTrans_a2_1 = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlTrans_a2.add(pnlTrans_a2_1, BorderLayout.WEST);	
					pnlTrans_a2_1.setPreferredSize(new java.awt.Dimension(148, 29));					
					{
						lookupTransNo = new _JLookup(null, "Transaction No.", 2, 2);
						pnlTrans_a2_1.add(lookupTransNo);
						lookupTransNo.setBounds(20, 27, 20, 25);
						lookupTransNo.setReturnColumn(0);
						lookupTransNo.setEnabled(false);	
						lookupTransNo.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupTransNo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									
									FncSystem.out("Lookup for Trans No.", getTransaction());
									
									refreshFields();
									
									trans_no 			= data[0].toString();
									String bank_id		= (String) data[1];
									String bank_acct_name	= (String) data[2];
									String branch_id	= (String) data[3];
									String branch_name	= (String) data[4];									
									String first_no		= (String) data[5];
									String last_no		= (String) data[6];
									String status		= (String) data[7];
									String last_no_used	= (String) data[8];
									String bank			= (String) data[9];
									
									lookupTransNo.setValue(trans_no);
									lookupBranch.setValue(branch_id);
									lookupBank.setValue(bank_id);
									tagBranch.setTag(branch_name);
									tagBank.setTag(bank_acct_name+" | "+bank);
									txtFirstNo.setText(first_no);
									txtLastNo.setText(last_no);
									txtLastNoUsed.setText(last_no_used);
									lookupUser.setValue((String) data[10]);
									tagUser.setTag((String) data[11]);
									
									if(status.equals("A"))
									{
										cmbStatus.setSelectedIndex(0);	
										enableButtons(false, true, false, true);
									}
									else if(status.equals("I") && last_no_used.equals(last_no) )										
									{
										cmbStatus.setSelectedIndex(1);	
										enableButtons(false, false, false, true);
									}
									else if(status.equals("I") && !last_no_used.equals(last_no) )										
									{
										cmbStatus.setSelectedIndex(1);	
										enableButtons(false, true, false, true);
									}
									
									
								}
							}
						});
					}

					pnlTrans_a2_2 = new JPanel(new GridLayout(1, 2, 5, 0));
					pnlTrans_a2.add(pnlTrans_a2_2, BorderLayout.CENTER);	
					pnlTrans_a2_2.setPreferredSize(new java.awt.Dimension(357, 25));						
				}

			}	
			{
				pnlCenter =  new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new java.awt.Dimension(611, 204));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));

				{		
					pnlCenter_a = new JPanel(new GridLayout(7, 1, 0, 5));
					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
					pnlCenter_a.setPreferredSize(new java.awt.Dimension(74, 198));

					{
						lblBranch = new JLabel("Branch", JLabel.TRAILING);
						pnlCenter_a.add(lblBranch);
						lblBranch.setEnabled(false);	
						lblBranch.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblBank = new JLabel("Bank Account", JLabel.TRAILING);
						pnlCenter_a.add(lblBank);
						lblBank.setEnabled(false);	
						lblBank.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						lblFirstNo= new JLabel("First No.", JLabel.TRAILING);
						pnlCenter_a.add(lblFirstNo);
						lblFirstNo.setEnabled(false);	
						lblFirstNo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}	
					{
						lblLastNo = new JLabel("Last No.", JLabel.TRAILING);
						pnlCenter_a.add(lblLastNo);
						lblLastNo.setEnabled(false);	
						lblLastNo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}	
					{
						lblLastNoUsed = new JLabel("Last No. Used", JLabel.TRAILING);
						pnlCenter_a.add(lblLastNoUsed);
						lblLastNoUsed.setEnabled(false);	
						lblLastNoUsed.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}	
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlCenter_a.add(lblStatus);
						lblStatus.setEnabled(false);	
						lblStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}	
					{
						lblUser = new JLabel("User", JLabel.TRAILING);
						pnlCenter_a.add(lblUser);
						lblUser.setEnabled(false);	
						lblUser.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}	

					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 118));
					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

					pnlTransDtl_2a = new JPanel(new GridLayout(7, 1, 0, 5));
					pnlCenter_2.add(pnlTransDtl_2a, BorderLayout.WEST);
					pnlTransDtl_2a.setPreferredSize(new java.awt.Dimension(100, 185));
					pnlTransDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						lookupBranch = new _JLookup(null, "Branch", 2, 2);
						pnlTransDtl_2a.add(lookupBranch);
						lookupBranch.setBounds(20, 27, 20, 25);
						lookupBranch.setReturnColumn(0);
						lookupBranch.setEnabled(false);
						//lookupBranch.setEditable(false);
						lookupBranch.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupBranch.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									branch_id = (String) data[0];		
									String branch_name 	= (String) data[1];		
									tagBranch.setTag(branch_name);
								}
							}
						});	
					}	
					{
						lookupBank = new _JLookup(null, "Bank Account", 2, 2);
						pnlTransDtl_2a.add(lookupBank);
						lookupBank.setBounds(20, 27, 20, 25);
						lookupBank.setFilterName(true);
						lookupBank.setReturnColumn(0);
						lookupBank.setEnabled(false);
						//lookupBank.setEditable(false);
						//lookupBank.setReturnColumn(2);
						lookupBank.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupBank.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		

									bank_acct_id = (String) data[0];		
									String bank_name 	= (String) data[1];		
									tagBank.setTag(bank_name);
								}
							}
						});	
					}	
					{
						txtFirstNo = new JXTextField("");
						pnlTransDtl_2a.add(txtFirstNo);
						txtFirstNo.setEnabled(false);	
						txtFirstNo.setEditable(false);
						txtFirstNo.setBounds(120, 25, 300, 22);	
						txtFirstNo.setHorizontalAlignment(JTextField.CENTER);	
					}
					{
						txtLastNo = new JXTextField("");
						pnlTransDtl_2a.add(txtLastNo);
						txtLastNo.setEnabled(false);	
						txtLastNo.setEditable(false);
						txtLastNo.setBounds(120, 25, 300, 22);	
						txtLastNo.setHorizontalAlignment(JTextField.CENTER);	
					}
					{
						txtLastNoUsed = new JXTextField("");
						pnlTransDtl_2a.add(txtLastNoUsed);
						txtLastNoUsed.setEnabled(false);	
						txtLastNoUsed.setEditable(false);
						txtLastNoUsed.setBounds(120, 25, 300, 22);	
						txtLastNoUsed.setHorizontalAlignment(JTextField.CENTER);	
					}
					{
						String status[] = {"Active","Inactive"};					
						cmbStatus = new JComboBox(status);
						pnlTransDtl_2a.add(cmbStatus);
						cmbStatus.setSelectedItem(null);
						cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbStatus.setBounds(537, 15, 190, 21);	
						cmbStatus.setEnabled(false);
						cmbStatus.setPreferredSize(new java.awt.Dimension(131, 80));
						cmbStatus.setSelectedIndex(0);	
					}
					{
						lookupUser = new _JLookup(null, "User", 2, 2);
						pnlTransDtl_2a.add(lookupUser);
						lookupUser.setBounds(20, 27, 20, 25);
						lookupUser.setReturnColumn(0);
						lookupUser.setEnabled(false);
						lookupUser.setFilterName(true);
						//lookupBank.setEditable(false);
						lookupUser.setReturnColumn(0);
						lookupUser.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupUser.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		

									emp_code = (String) data[0];		
									String emp_name 	= (String) data[1];		
									tagUser.setTag(emp_name);
								}
							}
						});	
					}	

					pnlTransDtl_2b = new JPanel(new GridLayout(7, 1, 0, 5));
					pnlCenter_2.add(pnlTransDtl_2b, BorderLayout.CENTER);
					pnlTransDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
					pnlTransDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						tagBranch = new _JTagLabel("[ ]");
						pnlTransDtl_2b.add(tagBranch);
						tagBranch.setBounds(209, 27, 700, 22);
						tagBranch.setEnabled(false);	
						tagBranch.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagBank = new _JTagLabel("[ ]");
						pnlTransDtl_2b.add(tagBank);
						tagBank.setBounds(209, 27, 700, 22);
						tagBank.setEnabled(false);	
						tagBank.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagX = new _JTagLabel("[ ]");
						pnlTransDtl_2b.add(tagX);
						tagX.setBounds(209, 27, 700, 22);
						tagX.setEnabled(false);	
						tagX.setVisible(false);	
						tagX.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagX = new _JTagLabel("[ ]");
						pnlTransDtl_2b.add(tagX);
						tagX.setBounds(209, 27, 700, 22);
						tagX.setEnabled(false);	
						tagX.setVisible(false);	
						tagX.setPreferredSize(new java.awt.Dimension(27, 33));
					}						
					{
						tagX = new _JTagLabel("[ ]");
						pnlTransDtl_2b.add(tagX);
						tagX.setBounds(209, 27, 700, 22);
						tagX.setEnabled(false);	
						tagX.setVisible(false);	
						tagX.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagX = new _JTagLabel("[ ]");
						pnlTransDtl_2b.add(tagX);
						tagX.setBounds(209, 27, 700, 22);
						tagX.setEnabled(false);	
						tagX.setVisible(false);	
						tagX.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagUser = new _JTagLabel("[ ]");
						pnlTransDtl_2b.add(tagUser);
						tagUser.setBounds(209, 27, 700, 22);
						tagUser.setEnabled(false);	
						tagUser.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
				}
			}

			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));
				{
					btnAddNew = new JButton("Add New");
					pnlSouth.add(btnAddNew);
					btnAddNew.setAlignmentX(0.5f);
					btnAddNew.setEnabled(false);
					btnAddNew.setAlignmentY(0.5f);
					btnAddNew.setMaximumSize(new Dimension(100, 30));
					btnAddNew.setActionCommand("Add");
					btnAddNew.setMnemonic(KeyEvent.VK_P);
					btnAddNew.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setAlignmentX(0.5f);
					btnEdit.setEnabled(false);
					btnEdit.setAlignmentY(0.5f);
					btnEdit.setMaximumSize(new Dimension(100, 30));
					btnEdit.setActionCommand("Edit");
					btnEdit.setMnemonic(KeyEvent.VK_P);
					btnEdit.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setAlignmentX(0.5f);
					btnSave.setEnabled(false);
					btnSave.setAlignmentY(0.5f);
					btnSave.setActionCommand("Save");
					btnSave.setMaximumSize(new Dimension(100, 30));
					btnSave.setMnemonic(KeyEvent.VK_P);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				dteDateFrom, dateDateTo, btnAddNew));
		this.setBounds(0, 0, 599, 426);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	@Override
	public void ancestorAdded(AncestorEvent event) {

	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {	

		if(e.getActionCommand().equals("Cancel")){ cancel(); }

		if(e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true){ addnew(); to_do  = "save";}
		else if (e.getActionCommand().equals("Add") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to add new check number(s).","Information",JOptionPane.INFORMATION_MESSAGE); }

		if(e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true){ save(); }
		else if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to save check number(s).","Information",JOptionPane.INFORMATION_MESSAGE); }

		if(e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==true){ edit(); to_do  = "edit";}
		else if (e.getActionCommand().equals("Edit") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "3")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit check number(s).","Information",JOptionPane.INFORMATION_MESSAGE); }


	}

	public static void enabledFields(boolean x){

		lblTransNo.setEnabled(x);	
		lookupTransNo.setEnabled(x);	
		//lookupTransNo.setEditable(x);	

		lblBranch.setEnabled(x);
		lookupBranch.setEnabled(x);
		//lookupBranch.setEditable(x);
		tagBranch.setEnabled(x);	

		lblBank.setEnabled(x);
		lookupBank.setEnabled(x);
		//lookupBank.setEditable(x);
		lookupBranch.setEnabled(x);
		//lookupBranch.setEditable(x);
		tagBank.setEnabled(x);	

		lblFirstNo.setEnabled(x);	
		txtFirstNo.setEnabled(x);	
		txtFirstNo.setEditable(x);

		lblLastNo.setEnabled(x);	
		txtLastNo.setEnabled(x);	
		txtLastNo.setEditable(x);

		lblLastNoUsed.setEnabled(x);	
		txtLastNoUsed.setEnabled(x);	
		txtLastNoUsed.setEditable(x);

		lblStatus.setEnabled(x);	
		cmbStatus.setEnabled(x);
		
		lblUser.setEnabled(x);	
		lookupUser.setEnabled(x);
		tagUser.setEnabled(x);			
	}

	public void refreshFields(){

		lookupTransNo.setValue("");
		lookupBranch.setValue("");	
		lookupBank.setValue("");

		tagBranch.setTag("");
		tagBank.setTag("");

		txtFirstNo.setText("");	
		txtLastNo.setText("");	
		txtLastNoUsed.setText("");	

		cmbStatus.setSelectedIndex(0);

		trans_no 	= "";
		next_trans_no = 0;
		to_do     = "";
	}

	public static void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnCancel.setEnabled(d);

	}

	public void cancel(){

		if(btnSave.isEnabled()==true)
		{
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				refreshFields();
				enabledFields(false);
				enableButtons(true, false, false, false);
				lblTransNo.setEnabled(true);	
				lookupTransNo.setEnabled(true);	
				//lookupTransNo.setEditable(true);	
			}
		}

		else 
		{			
			refreshFields();
			enabledFields(false);
			enableButtons(true, false, false, false);
			lblTransNo.setEnabled(true);	
			lookupTransNo.setEnabled(true);	
			//lookupTransNo.setEditable(true);	
		}


	}

	public void addnew(){

		enabledFields(true);
		enableButtons(false, false, true, true);
		refreshFields();

		lookupBranch.setLookupSQL(getOfficeBranch());	
		lookupBank.setLookupSQL(getBank());	
		lookupUser.setLookupSQL(getEmployeeList());	

		lblTransNo.setEnabled(false);	
		lookupTransNo.setEnabled(false);

		lblLastNoUsed.setEnabled(false);	
		txtLastNoUsed.setEnabled(false);	
		txtLastNoUsed.setEditable(false);

		txtLastNoUsed.setText("");		

		lookupUser.setValue(UserInfo.EmployeeCode);
		tagUser.setText(UserInfo.FullName);
	}

	public void save(){		

		if(lookupBranch.getText().equals(""))
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter an office branch.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}
		else {			

			if(lookupBank.getText().equals(""))
			{JOptionPane.showMessageDialog(getContentPane(), "Please enter a bank account.", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);}
			else {	

				if(txtFirstNo.getText().equals(""))
				{JOptionPane.showMessageDialog(getContentPane(), "Please enter the first check no.", "Incomplete Detail", 
						JOptionPane.WARNING_MESSAGE);}
				else {	

					if(txtLastNo.getText().equals(""))
					{JOptionPane.showMessageDialog(getContentPane(), "Please enter the last check no.", "Incomplete Detail", 
							JOptionPane.WARNING_MESSAGE);}
					else {	

						if (checkIfActiveChecksExists()==true&&to_do.equals("save")){

							if (JOptionPane.showConfirmDialog(getContentPane(), 
									"An active check series already exists under the" + "\n" +
									"selected Office Branch and Bank Account?"
									+ "\n" + "Would you like to create a new one?", "Confirmation", 
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

								if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
									pgUpdate db = new pgUpdate();	

									if (to_do.equals("save")) 
									{
										insertCheck(db);
										db.commit();
										JOptionPane.showMessageDialog(getContentPane(),"New check numbers saved.","Information",JOptionPane.INFORMATION_MESSAGE);
									}
									
									enableButtons(false, true, false, true);
									enabledFields(false);
									lblTransNo.setEnabled(true);	
									lookupTransNo.setEnabled(true);	
									lookupTransNo.setText(next_trans_no.toString());
								}								
							}
						}			

						else {

							if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
								pgUpdate db = new pgUpdate();	

								if (to_do.equals("save")) 
								{
									insertCheck(db);
									db.commit();
									JOptionPane.showMessageDialog(getContentPane(),"New check numbers saved.","Information",JOptionPane.INFORMATION_MESSAGE);
								}
								else 
								{
									updateCheck(db);
									db.commit();
									JOptionPane.showMessageDialog(getContentPane(),"Check numbers updated.","Information",JOptionPane.INFORMATION_MESSAGE);
								}

								enableButtons(false, true, false, true);
								enabledFields(false);
								lblTransNo.setEnabled(true);	
								lookupTransNo.setEnabled(true);	
								//lookupTransNo.setText(next_trans_no.toString());
							}	
						}
					}					
				}					
			}
		}

	}

	public void edit(){
		enabledFields(true);
		enableButtons(false, false, true, true);
		lookupBranch.setLookupSQL(getOfficeBranch());	
		lookupBank.setLookupSQL(getBank());
		lookupUser.setLookupSQL(getEmployeeList());	
		
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";	
		company_logo = RequestForPayment.sql_getCompanyLogo();				
		txtCompany.setText(company);

		lblTransNo.setEnabled(true);	
		lookupTransNo.setEnabled(true);	
		enableButtons(true, false, false, true);

		lookupTransNo.setLookupSQL(getTransaction());	
		
		lookupCompany.setValue(co_id);
	}
	

	//lookup
	public static String getTransaction(){

		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"a.rec_id as \"Trans. No.\", \r\n" + 
			"a.bank_acct_id as \"Bank Acct. ID\",\r\n" + 
			"trim(b.bank_acct_no) as \"Bank Acct. Desc.\",\r\n" + 
			"a.branch_id  as \"Branch ID\",\r\n" + 
			"trim(c.branch_name) as \"Branch Name\" ,\r\n" + 
			"trim(a.from_check_no) as \"Check No (from)\",\r\n" + 
			"trim(a.to_check_no) as \"Check No (to)\" ,\r\n" + 
			"a.status_id as \"status\", \r\n" + 
			"trim(a.last_no_used) as \"Last Used No.\"," +
			"trim(e.bank_alias) as \"Bank\",  \r\n" +
			"a.user_id as \"User ID\" ,\r\n" + 
			"trim(g.entity_name) as \"User Name\"  \n" + 
			"\r\n" + 
			"from (select * from rf_check_book where status_id = 'A') a\r\n" + 
			"left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id\r\n" + 
			"left join mf_office_branch c on a.branch_id = c.branch_id\r\n" + 
			"left join mf_bank_branch d on b.bank_branch_id = d.bank_branch_id   " +
			"left join mf_bank e on d.bank_id = e.bank_id   \n" +
			"left join em_employee f on a.user_id = f.emp_code\r\n" + 
			"left join rf_entity g on f.entity_id = g.entity_id " +
			"\r\n" + 
			"order by a.rec_id";		
		return sql;

	}	

	public String getOfficeBranch(){

		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"branch_id as \"Branch\" , \r\n" + 
			"trim(branch_name) as \"Branch Name\" \r\n" + 
			"\r\n" + 
			"from mf_office_branch \r\n" + 
			"order by branch_id";		
		return sql;

	}	

	public String getBank(){		

		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"bank_acct_id as \"Bank Account ID\" , \r\n" + 
			"bank_acct_no as \"Bank Acct. No.\",  \r\n" + 
			"trim(acct_desc) as \"Bank Account Desc.\" \r\n" + 
			
			"\r\n" + 
			"from mf_bank_account \r\n" + 
			"order by bank_acct_id";		
		return sql;

	}	

	public Integer sql_getNextCheckRecID() {

		Integer rec = 1;

		String SQL = 
			"select max(rec_id)+1 from rf_check_book   \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			rec = (Integer) db.getResult()[0][0];
		}else{
			rec = 1;
		}

		return rec;
	}

	public String getEmployeeList(){		

		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"a.emp_code,\r\n" + 
			"trim(b.entity_name) as emp_name\r\n" + 
			"\r\n" + 
			"from em_employee a\r\n" + 
			"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
			"\r\n" + 
			"where a.dept_code = '04'\r\n" + 
			"\r\n" + 
			"order by b.entity_name";		
		return sql;

	}	
	

	//validation
	public Boolean checkIfActiveChecksExists(){

		Boolean activeCheckExists = false;		

		String SQL = 
			"select rec_id from rf_check_book " +
			"where bank_acct_id = '"+bank_acct_id+"' " +
			"and branch_id = '"+branch_id+"' " +
			"and status_id = 'A' \r\n" + 
			"and last_no_used != to_check_no" ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			activeCheckExists = true;
		}else{
			activeCheckExists = false;
		}

		return activeCheckExists;

	}


	//save and insert		
	public void insertCheck(pgUpdate db){

		next_trans_no = sql_getNextCheckRecID();

		if (next_trans_no==null||next_trans_no.equals("null")) {next_trans_no=1;} 
		else {}

		String sqlDetail = 
			"INSERT into rf_check_book values (" +
			""+next_trans_no+",  \n" +  		//0
			"'"+lookupBank.getText().trim()+"',  \n" +	//1
			"'"+lookupBranch.getText().trim()+"',  \n" +//2
			"'"+txtFirstNo.getText().trim()+"',  \n" +	//3
			"'"+txtLastNo.getText().trim()+"',  \n" +	//4
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//5
			"'',  \n" +									//6
			"'"+lookupUser.getText()+"',   \n" +		//7	changed 03-18-2016
			"'A',  \n" +								//8
			"'"+UserInfo.EmployeeCode+"',   \n" +		//9	
			"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//10
			"null, \n"  +								//11
			"null  \n"  +								//12
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		


	}

	public void updateCheck(pgUpdate db){

		String stat = "";
		if(cmbStatus.getSelectedIndex()==0) {stat="A";} else {stat="I";}

		String sqlDetail = 
			"update rf_check_book set " +
			"branch_id = '"+lookupBranch.getText().trim()+"',  \n" +  		//1
			"bank_acct_id = '"+lookupBank.getText().trim()+"',  \n" +		//2
			"from_check_no = '"+txtFirstNo.getText().trim()+"',  \n" +		//3
			"to_check_no = '"+txtLastNo.getText().trim()+"',  \n" +			//4
			"last_no_used = '"+txtLastNoUsed.getText().trim()+"',  \n" +	//5
			"user_id = '"+lookupUser.getText().trim()+"',  \n" +			//6
			"edited_by = '"+UserInfo.EmployeeCode+"',   \n" +				//7
			"date_edited = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" +	//8
			"status_id = '"+stat+"'  \n" +

			"where rec_id = "+lookupTransNo.getText().trim()+"  \n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}


}
