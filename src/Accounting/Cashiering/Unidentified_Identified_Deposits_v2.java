package Accounting.Cashiering;

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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelDirectDepositIssuance;
import tablemodel.modelIdentifiedDep_approval;
import tablemodel.modelUnidentified_deposits;

public class Unidentified_Identified_Deposits_v2 extends _JInternalFrame
		implements _GUI, ActionListener, MouseListener {



	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Unidentified / Identified Deposits";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlDep;
	private JPanel pnlDate;
	private JPanel pnlSouthCenterb;
	private JPanel pnlDep_b2_1a_sub;
	private JPanel pnlDep_b3b;
	private JPanel pnlDep_unidentified;
	private JPanel pnlDep_unidentified_north;
	private JPanel pnlDep_unidentified_north_sub1;	
	private JPanel pnlDep_unidentified_north_sub2;
	private JPanel pnlDep_unidentified_north_sub2a;
	private JPanel pnlDep_unidentified_north_sub2b;
	private JPanel pnlDep_unidentified_north_sub2c;
	private JPanel pnlDep_unidentified_north_sub3;
	private JPanel pnlDep_identified;
	private JPanel pnlDep_identified_north;
	private JPanel pnlDep_identified_north_sub1;
	private JPanel pnlDep_identified_north_sub1a;
	private JPanel pnlDep_identified_center;
	private JPanel pnlDep_identified_center_north;
	private JPanel pnlDep_identified_center_north_dupl;
	private JPanel pnlDep_identified_center_north_sub1;
	private JPanel pnlDep_identified_center_north_sub2;
	private JPanel pnlDep_identified_center_north_sub2b;
	private JPanel pnlDep_identified_center_north_sub2a;
	private JPanel pnlDep_identified_center_north_sub2c;
	private JPanel pnlDep_identified_center_north_sub3;
	private JPanel pnlDep_identified_center_center;
	private JPanel pnlDep_identified_center_center_sub1;
	private JPanel pnlDep_identified_center_center_sub1a;
	private JPanel pnlDep_identified_center_center_sub1b;
	private JPanel pnlDep_identified_center_center_sub1c;
	private JPanel pnlDep_identified_center_center_sub2;
	private JPanel pnlDep_identified_center_center_sub2a;
	private JPanel pnlDep_identified_center_center_sub2b;
	private JPanel pnlDep_identified_center_center_sub2b_a;
	private JPanel pnlDep_identified_center_center_sub2b_b;
	private JPanel pnlDep_identified_center_center_sub2b_c;
	private JPanel pnlDep_identified_center_south;
	private JPanel pnlDep_identified_center_south_sub1;
	private JPanel pnlDep_identified_center_south_sub1a;
	private JPanel pnlDep_identified_center_south_sub1b;
	private JPanel pnlDep_unidentified_south;
	private JPanel pnlDep_unidentified_center;
	private JPanel pnlDep_identified_south;
	private JPanel pnlSouthCenterb_2;
	private JPanel pnlEditDetails;
	private JPanel pnlEditDetails_a;
	private JPanel pnlEditDetails_a1;
	private JPanel pnlEditDetails_a2;
	private JPanel pnlEditDetails_c;
	private JPanel pnlApproval;
	private JPanel pnlApproval_a;

	private JLabel lblCompany;
	private JLabel lblUniden_status;
	private JLabel lblSearchBy;	
	private JLabel lblIden_DepNo;	
	private JLabel lblIden_status;
	private JLabel lblIden_ReceiptNo;
	private JLabel lblIden_bank;
	private JLabel lblIden_BankAcct;
	private JLabel lblIden_DepAmt;
	private JLabel lblIden_DepDate;
	private JLabel lblIden_TagDate;
	private JLabel lblIden_PBL;
	private JLabel lblIden_payment;	
	private JLabel lblProject;
	private JLabel lblClientName;
	private JLabel lblUniden_dep_no;
	private JLabel lblEditDate;
	private JLabel lblEditAmount;
	private JLabel lblEditAcct;
	private JLabel lblStatus;	
	private JLabel lblRecID;
	private JLabel lblSalesDivGrp;
	private JLabel lblDateFr;
	private JLabel lblDateTo;
	private JLabel lblReceiptNo;
	
	private _JLookup lookupCompany;
	private _JLookup lookupUnidenDepNo;	
	private _JLookup lookupIdentified_Depno;	
	private _JLookup lookupIden_PBL;
	private _JLookup lookupIden_payment;
	private _JLookup lookupBankAcct;	

	private modelUnidentified_deposits modelDep_unidentified;
	private modelUnidentified_deposits modelDep_unidentified_total;	

	private _JTabbedPane tabDeposit;	

	private _JTagLabel tagCompany;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnAddNew;	
	private JButton btnOK;
	private JButton btnPreview;
	private JButton btnIdentDisplay;	
	private JButton btnTagDetails;
	private JButton btnCancel_2;
	private JButton btnSaveIden;
	private JButton btnSaveDeposit;
	private JButton btnApprove;
	private JButton btnX;
	private JButton btnEditUniden;
	private JButton btnPreviewApproved;
	private JButton btnPreviewList;
	private JButton btnEditIden;	
	private JButton btnIssueOR;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dteRefDate;
	private _JDateChooser dteDeposit;
	private _JDateChooser dteRefDateFr;	
	private _JDateChooser dteRefDateTo;

	private JXTextField txtUniden_status;	
	private JXTextField txtIden_status;
	private JXTextField txtIden_ReceiptNo;
	private JXTextField txtIden_bankNo;	
	private JXTextField txtIden_BankAcct;		
	private JXTextField txtIden_DepDate;
	private JXTextField txtIden_TagDate;	
	private JXTextField txtIden_PBL;		
	private JXTextField txtIden_BankName;		
	private JXTextField txtPayment;
	private JXTextField txtProject;	
	private JXTextField txtClientName;
	private JXTextField txtRecNo;

	private JPopupMenu menu;
	private JMenuItem mnidelete;
	private JMenuItem mniaddrow;
	private JMenuItem mnieditrow;
	
	private JList rowHeaderDep_unidentified;
	private JList rowHeaderApproval;

	private _JScrollPaneMain scrollUniden;
	private _JScrollPaneMain scrollApproval;	
	private _JScrollPaneTotal scrollDep_unidentified_total;
	private _JScrollPaneTotal scrollApproval_total;
	private _JTableMain tblDep_unidentified;	
	private _JTableMain tblApproval;
	private _JTableTotal tblDep_unidentified_total;	
	private _JTableTotal tblApproval_total;
	
	private modelIdentifiedDep_approval modelApproval;
	private modelIdentifiedDep_approval modelApproval_total;
	
	private JRadioButton rbtnForIdentification;
	private JRadioButton rbtnAllDeposits;	

	private JXTextField txtSalesDivGrp;
	private _JXFormattedTextField txtIden_DepAmt;	
	private _JXFormattedTextField txtAmount;

	private JComboBox cmbStatus;
	private _JLookup lookupDirectDepositBatch;
	private _JXTextField txtDirectDepositBatch;
	private JScrollPane scrollDDForIssuance;
	private modelDirectDepositIssuance modelDDIssuance;
	private _JTableMain tblDDIssuance;
	private JList rowHeaderDDIssuance;
	
	private JButton btnIssueReceiptDD;
	private JButton btnPreviewBatch;
	private JButton btnCancelBatch;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	

	String co_id = "";		
	String company = "";
	String company_logo;	
	String seq_no 		= "";
	String pbl_id 		= "";
	String entity_id 	= "";
	String dept_name 	= "";
	String preview_what_rpt	= "identified";
	private String to_do = "";
	private JPanel pnlAddDate;
	private JButton btnRefreshForApproval;
	private JButton btnAddOK;	

	public Unidentified_Identified_Deposits_v2() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Unidentified_Identified_Deposits_v2(String title) {
		super(title);

	}

	public Unidentified_Identified_Deposits_v2(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(935, 619));
		this.setBounds(0, 0, 935, 619);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Row      ");
			mniaddrow = new JMenuItem("Add Row");
			mnieditrow = new JMenuItem("Edit Details");
			mnieditrow.setEnabled(false);
			menu.add(mnidelete);
			menu.add(mniaddrow);
			JSeparator sp = new JSeparator();
			menu.add(sp);
			menu.add(mnieditrow);	

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
			mnieditrow.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					EditDetails();
				}
			});
		}


		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 37));

			pnlComp = new JPanel(new BorderLayout(5, 0));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);	

			//company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
			pnlComp_a.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(182, 30));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			{
				lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
				pnlComp_a1.add(lblCompany);
				lblCompany.setBounds(8, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(74, 25));
				lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				lookupCompany = new _JLookup(null, "Company",0,2);
				pnlComp_a1.add(lookupCompany);
				lookupCompany.setLookupSQL(SQL_COMPANY());
				lookupCompany.setReturnColumn(0);
				lookupCompany.setValue("02");
				lookupCompany.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							co_id 		= (String) data[0];	
							company		= (String) data[1];	
							tagCompany.setTag((String) data[1]);
							company_logo = (String) data[3];

							//tab 1
							lblUniden_dep_no.setEnabled(true);	
							lookupUnidenDepNo.setEnabled(true);	
							{ enableButtons_uniden(true, false,  false,  true,  false ); } 
							lookupUnidenDepNo.setLookupSQL(getDepositNo());	

							lookupIden_PBL.setLookupSQL(getPBL());
							
							//tab 2
							lblSearchBy.setEnabled(true);
							rbtnForIdentification.setEnabled(true);
							rbtnForIdentification.setEnabled(true);
							rbtnAllDeposits.setEnabled(true);
							lblIden_DepNo.setEnabled(true);	
							lookupIdentified_Depno.setEnabled(true);	
							
							if (rbtnAllDeposits.isSelected()) {
								lookupIdentified_Depno.setLookupSQL(getDepositNo());
							} else {
								lookupIdentified_Depno.setLookupSQL(getActiveDepositNo());								
							}
							
							
							
							enableButtons_iden(false, false,  false, true,  false ); 

							displayForApproval(modelApproval, rowHeaderApproval, modelApproval_total);
							btnApprove.setEnabled(true);
							btnRefreshForApproval.setEnabled(true);
							btnPreviewApproved.setEnabled(true);
							modelApproval.setEditable(true);

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


		}
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlDep = new JPanel();
			pnlTable.add(pnlDep, BorderLayout.CENTER);
			pnlDep.setLayout(new BorderLayout(5, 5));
			pnlDep.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlDep.setBorder(lineBorder);		
			pnlDep.addMouseListener(new PopupTriggerListener_panel());			

			tabDeposit = new _JTabbedPane();
			pnlDep.add(tabDeposit, BorderLayout.CENTER);		

			//start of UNIDENTIFIED DEPOSITS
			{			
				pnlDep_unidentified = new JPanel(new BorderLayout());
				tabDeposit.addTab("Tag Unidentified Deposits", null, pnlDep_unidentified, null);
				pnlDep_unidentified.setPreferredSize(new java.awt.Dimension(916, 407));

				{

					pnlDep_unidentified_north = new JPanel(new BorderLayout(5, 5));
					pnlDep_unidentified.add(pnlDep_unidentified_north, BorderLayout.NORTH);	
					pnlDep_unidentified_north.setPreferredSize(new java.awt.Dimension(911, 42));
					pnlDep_unidentified_north.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlDep_unidentified_north.setBorder(lineBorder);

					{
						pnlDep_unidentified_north_sub1 = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlDep_unidentified_north.add(pnlDep_unidentified_north_sub1, BorderLayout.WEST);	
						pnlDep_unidentified_north_sub1.setPreferredSize(new java.awt.Dimension(92, 40));	
						pnlDep_unidentified_north_sub1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblUniden_dep_no = new JLabel("Deposit No.", JLabel.TRAILING);
							pnlDep_unidentified_north_sub1.add(lblUniden_dep_no);
							lblUniden_dep_no.setEnabled(false);	
							lblUniden_dep_no.setPreferredSize(new java.awt.Dimension(86, 40));
							lblUniden_dep_no.setFont(new java.awt.Font("Segoe UI",Font.ITALIC&Font.BOLD,12));
						}						

						pnlDep_unidentified_north_sub2 = new JPanel(new BorderLayout(5, 5));
						pnlDep_unidentified_north.add(pnlDep_unidentified_north_sub2, BorderLayout.CENTER);	
						pnlDep_unidentified_north_sub2.setPreferredSize(new java.awt.Dimension(814, 40));	
						pnlDep_unidentified_north_sub2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

						{
							pnlDep_unidentified_north_sub2a = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlDep_unidentified_north_sub2.add(pnlDep_unidentified_north_sub2a, BorderLayout.WEST);	
							pnlDep_unidentified_north_sub2a.setPreferredSize(new java.awt.Dimension(163, 24));					

							{
								lookupUnidenDepNo = new _JLookup(null, "Deposit No.", 2, 2);
								pnlDep_unidentified_north_sub2a.add(lookupUnidenDepNo);
								lookupUnidenDepNo.setBounds(20, 27, 20, 25);
								lookupUnidenDepNo.setReturnColumn(0);
								lookupUnidenDepNo.setEnabled(false);	
								lookupUnidenDepNo.setPreferredSize(new java.awt.Dimension(157, 22));
								lookupUnidenDepNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){	

											String status = (String) data[7];
											txtUniden_status.setText(status);											

											tblDep_unidentified.showColumns("Created By","Created Date","Tagged By","Tagged Date","Record No.", "Edited By", "Edited Date");	
											displayUnidentifiedDep_details(modelDep_unidentified, rowHeaderDep_unidentified, modelDep_unidentified_total, (Integer) data[0] );								

											if(status.equals("ACTIVE")||status.equals("INACTIVE"))
											{ 
												enableButtons_uniden(false, true,  false,  false, true); 
												mnidelete.setEnabled(false);
												mniaddrow.setEnabled(false);
												mnieditrow.setEnabled(true);
											}
											else if (status.equals("TAGGED"))
											{ 
												enableButtons_uniden(false, false,  false,  false, true ); 
												mnidelete.setEnabled(false);
												mniaddrow.setEnabled(false);
												mnieditrow.setEnabled(false);
											}

											tblDep_unidentified.setEditable(false);
											modelDep_unidentified.setEditable(false);
											
											//enable/disable Issuance of Receipt button
											String receipt_no = "";												
											try { receipt_no = (String) data[10];} 
											catch (NullPointerException e) { receipt_no = ""; }
											if(receipt_no.trim().equals("")&&status.equals("POSTED")) {btnIssueOR.setEnabled(true);}
											else {btnIssueOR.setEnabled(false);}
											lblReceiptNo.setText("Receipt No : " + receipt_no);

										}
									}
								});
							}	

							pnlDep_unidentified_north_sub2b = new JPanel(new GridLayout(1, 2, 5, 0));
							pnlDep_unidentified_north_sub2.add(pnlDep_unidentified_north_sub2b, BorderLayout.CENTER);	
							pnlDep_unidentified_north_sub2b.setPreferredSize(new java.awt.Dimension(357, 25));	

							{
								lblUniden_status = new JLabel("Status", JLabel.TRAILING);
								pnlDep_unidentified_north_sub2b.add(lblUniden_status);
								lblUniden_status.setEnabled(false);	
								lblUniden_status.setFont(new java.awt.Font("Segoe UI",Font.ITALIC&Font.BOLD,12));
							}	
							{
								txtUniden_status = new JXTextField("");
								pnlDep_unidentified_north_sub2b.add(txtUniden_status);
								txtUniden_status.setEnabled(false);	
								txtUniden_status.setEditable(false);
								txtUniden_status.setBounds(120, 25, 300, 22);	
								txtUniden_status.setHorizontalAlignment(JTextField.CENTER);	
								txtUniden_status.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							}	

							pnlDep_unidentified_north_sub2c = new JPanel(new GridLayout(1, 2,5,0));
							pnlDep_unidentified_north_sub2.add(pnlDep_unidentified_north_sub2c, BorderLayout.EAST);	
							pnlDep_unidentified_north_sub2c.setPreferredSize(new java.awt.Dimension(284, 24));	
							pnlDep_unidentified_north_sub2c.setAlignmentY(RIGHT_ALIGNMENT);	
							pnlDep_unidentified_north_sub2c.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));		

							{
								lblReceiptNo = new JLabel("Receipt No :", JLabel.TRAILING);
								pnlDep_unidentified_north_sub2c.add(lblReceiptNo);
								lblReceiptNo.setBounds(209, 27, 700, 22);
								lblReceiptNo.setEnabled(true);	
								lblReceiptNo.setPreferredSize(new java.awt.Dimension(27, 33));
								lblReceiptNo.setFont(new java.awt.Font("Segoe UI",Font.ITALIC&Font.BOLD,12));
							}	
							/*{
								btnUniden_display = new JButton("Display All Active");
								pnlDep_unidentified_north_sub2c.add(btnUniden_display);
								btnUniden_display.setActionCommand("Display_unidentified");
								btnUniden_display.addActionListener(this);
								btnUniden_display.setEnabled(false);
								btnUniden_display.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							}*/
						}

						pnlDep_unidentified_north_sub3 = new JPanel(new BorderLayout(5, 5));
						pnlDep_unidentified_north.add(pnlDep_unidentified_north_sub3, BorderLayout.EAST);	
						pnlDep_unidentified_north_sub3.setPreferredSize(new java.awt.Dimension(183, 40));	
						pnlDep_unidentified_north_sub3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

						{
							btnIssueOR = new JButton("Issue Receipt");
							pnlDep_unidentified_north_sub3.add(btnIssueOR);
							btnIssueOR.setActionCommand("Display_unidentifiedTagged");
							btnIssueOR.addActionListener(this);
							btnIssueOR.setEnabled(false);
							btnIssueOR.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							btnIssueOR.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									
									if(FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==true )
									{
										//IssuanceOfReceipt ior = new IssuanceOfReceipt();
										//Home_JSystem.addWindow(ior);
										if (issueReceipt()==true)
										{
											JOptionPane.showMessageDialog(getContentPane(),"Issuance of receipt successful. Proceed to Printing of Receipt","Information",JOptionPane.INFORMATION_MESSAGE);	
										}
										else
										{
											JOptionPane.showMessageDialog(getContentPane(),"Issuance of receipt failed.","Information",JOptionPane.INFORMATION_MESSAGE);	
										}
									}
									else if(FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==false ) 
									{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to issued receipt. \n"
											+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE); }				
									
								}
							});
						}
					}
					{

						pnlDep_unidentified_center = new JPanel(new BorderLayout(5, 5));
						pnlDep_unidentified.add(pnlDep_unidentified_center, BorderLayout.CENTER);	
						pnlDep_unidentified_center.setPreferredSize(new java.awt.Dimension(911, 42));
						pnlDep_unidentified_center.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
						pnlDep_unidentified_center.setBorder(lineBorder);

						scrollUniden = new _JScrollPaneMain();
						pnlDep_unidentified_center.add(scrollUniden, BorderLayout.CENTER);
						{
							modelDep_unidentified = new modelUnidentified_deposits();

							tblDep_unidentified = new _JTableMain(modelDep_unidentified);
							scrollUniden.setViewportView(tblDep_unidentified);
							tblDep_unidentified.addMouseListener(this);	
							tblDep_unidentified.setSortable(false);
							tblDep_unidentified.addMouseListener(new PopupTriggerListener_panel());
							tblDep_unidentified.getColumnModel().getColumn(1).setPreferredWidth(120);
							tblDep_unidentified.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {		
									computeUnidentifiedAmount();
									tblDep_unidentified.getColumnModel().getColumn(1).setPreferredWidth(120);
								}							
								public void keyPressed(KeyEvent e) {
									computeUnidentifiedAmount();
									tblDep_unidentified.getColumnModel().getColumn(1).setPreferredWidth(120);
								}

							}); 
							tblDep_unidentified.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblDep_unidentified.rowAtPoint(e.getPoint()) == -1){tblDep_unidentified_total.clearSelection();}
									else{tblDep_unidentified.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblDep_unidentified.rowAtPoint(e.getPoint()) == -1){tblDep_unidentified_total.clearSelection();}
									else{tblDep_unidentified.setCellSelectionEnabled(true);}
								}
							});						
						}
						{
							rowHeaderDep_unidentified = tblDep_unidentified.getRowHeader22();
							scrollUniden.setRowHeaderView(rowHeaderDep_unidentified);
							scrollUniden.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollDep_unidentified_total = new _JScrollPaneTotal(scrollUniden);
						pnlDep_unidentified_center.add(scrollDep_unidentified_total, BorderLayout.SOUTH);
						{
							modelDep_unidentified_total = new modelUnidentified_deposits();
							modelDep_unidentified_total.addRow(new Object[] { "Total", new BigDecimal(0.00), "", "", ""});

							tblDep_unidentified_total = new _JTableTotal(modelDep_unidentified_total, tblDep_unidentified);
							tblDep_unidentified_total.setFont(dialog11Bold);
							scrollDep_unidentified_total.setViewportView(tblDep_unidentified_total);
							((_JTableTotal) tblDep_unidentified_total).setTotalLabel(0);
						}
					}
				}
				{
					pnlDep_unidentified_south = new JPanel(new BorderLayout(5, 5));
					pnlDep_unidentified.add(pnlDep_unidentified_south, BorderLayout.SOUTH);	
					pnlDep_unidentified_south.setPreferredSize(new java.awt.Dimension(916, 33));
					pnlDep_unidentified_south.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlDep_unidentified_south.setBorder(lineBorder);

					pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlDep_unidentified_south.add(pnlSouthCenterb, BorderLayout.NORTH);
					pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
					{
						{
							btnAddNew = new JButton("Add New");
							pnlSouthCenterb.add(btnAddNew);
							btnAddNew.setActionCommand("AddNew");
							btnAddNew.addActionListener(this);
							btnAddNew.setEnabled(false);
							btnAddNew.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if(e.getActionCommand().equals("AddNew") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11")==true ){	
										tblDep_unidentified.hideColumns("Created By","Created Date","Tagged By","Tagged Date","Record No.", "Edited By", "Edited Date");	
										addnew();	} //ok
									else if (e.getActionCommand().equals("AddNew") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "11")==false ) 
									{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to add new unidentified deposit. \n"
											+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE); }

								}
							});
						}
						{
							btnEditUniden = new JButton("Edit");
							pnlSouthCenterb.add(btnEditUniden);
							btnEditUniden.setActionCommand("Edit");
							btnEditUniden.addActionListener(this);
							btnEditUniden.setEnabled(false);
							btnEditUniden.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									editUnidentified();

								}
							});
						}
						{
							btnSave = new JButton("Save");
							pnlSouthCenterb.add(btnSave);
							btnSave.setActionCommand("Save");
							btnSave.addActionListener(this);
							btnSave.setEnabled(false);
							btnSave.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									if (to_do.equals("addnew_unidentified"))
									{
										saveUnidentifiedDeposit();										
									}
									else 
									{
										updateUnidentified();
									}

								}
							});
						}
						{
							btnCancel = new JButton("Cancel");
							pnlSouthCenterb.add(btnCancel);
							btnCancel.setActionCommand("Cancel");
							btnCancel.addActionListener(this);
							btnCancel.setEnabled(false);
						}
						{
							btnPreview = new JButton("Preview List (Unidentified)");
							pnlSouthCenterb.add(btnPreview);
							btnPreview.addActionListener(this);
							btnPreview.setEnabled(false);
							btnPreview.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									
									if (FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11")==true ) {
										previewUnidentified();
									} else if(FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11")==false ) {
										JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print list of unidentified deposit. \n"
											+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE);
									}				
									
								}
							});
						}
					}
					{
						pnlAddDate= new JPanel();
						pnlAddDate.setLayout(null);
						pnlAddDate.setPreferredSize(new java.awt.Dimension(160, 70));
						{
							lblDateFr = new JLabel();
							pnlAddDate.add(lblDateFr);
							lblDateFr.setBounds(10, 5, 160, 20);
							lblDateFr.setText("Date from :");
						}
						{
							dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlAddDate.add(dteRefDate);
							dteRefDate.setBounds(80, 5, 125, 21);
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
							btnAddOK = new JButton();
							pnlAddDate.add(btnAddOK);
							btnAddOK.setBounds(95, 40, 69, 22);
							btnAddOK.setText("OK");
							btnAddOK.setFocusable(false);
							btnAddOK.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									modelDep_unidentified.setValueAt(dteRefDate.getDate(), tblDep_unidentified.getSelectedRow(), 0);
									
									Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddDate);
									optionPaneWindow.dispose();
								}
							});
						}
					}
					{
						pnlDate= new JPanel();
						pnlDate.setLayout(null);
						pnlDate.setPreferredSize(new java.awt.Dimension(160, 100));
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
							dteRefDateFr.setEnabled(true);
							dteRefDateFr.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dteRefDateFr.getDateEditor()).setEditable(false);
							dteRefDateFr.setDate(Calendar.getInstance().getTime());
							dteRefDateFr.addPropertyChangeListener( new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {
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
							dteRefDateTo.setDate(Calendar.getInstance().getTime());
							dteRefDateTo.addPropertyChangeListener( new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {
								}
							});	
						}
						{
							btnOK = new JButton();
							pnlDate.add(btnOK);
							btnOK.setBounds(95, 75, 69, 22);
							btnOK.setText("OK");
							btnOK.setFocusable(false);
							btnOK.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
									optionPaneWindow.dispose();

									if(FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11")==true ){

										if (preview_what_rpt.equals("identified"))
										{
											previewIdentified();
										}
										else 
										{
											previewApproved();
										}


									}
									else if(FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11")==false ) 
									{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print list of identified deposit. \n"
											+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE); }	


								}
							});
						}
					}
				}
			}
			{

				pnlEditDetails = new JPanel();
				pnlEditDetails.setLayout(new BorderLayout(5, 5));
				pnlEditDetails.setBorder(lineBorder);		
				pnlEditDetails.setPreferredSize(new java.awt.Dimension(382, 250));		

				{		
					pnlEditDetails_a = new JPanel(new BorderLayout(5, 5));
					pnlEditDetails.add(pnlEditDetails_a, BorderLayout.NORTH);				
					pnlEditDetails_a.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlEditDetails_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlEditDetails_a.setPreferredSize(new java.awt.Dimension(380, 180));		

					{
						pnlEditDetails_a1 = new JPanel(new GridLayout(5, 5, 5, 5));
						pnlEditDetails_a.add(pnlEditDetails_a1, BorderLayout.WEST);				
						pnlEditDetails_a1.setPreferredSize(new java.awt.Dimension(921, 41));
						pnlEditDetails_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						pnlEditDetails_a1.setPreferredSize(new java.awt.Dimension(107, 145));		

						{
							lblRecID = new JLabel("Record No.", JLabel.TRAILING);
							pnlEditDetails_a1.add(lblRecID);
							lblRecID.setEnabled(true);	
							lblRecID.setPreferredSize(new java.awt.Dimension(136, 24));
						}	
						{
							lblEditDate = new JLabel("Date", JLabel.TRAILING);
							pnlEditDetails_a1.add(lblEditDate);
							lblEditDate.setEnabled(true);	
							lblEditDate.setPreferredSize(new java.awt.Dimension(136, 24));
						}	
						{
							lblEditAmount = new JLabel("Amount", JLabel.TRAILING);
							pnlEditDetails_a1.add(lblEditAmount);
							lblEditAmount.setEnabled(true);	
							lblEditAmount.setPreferredSize(new java.awt.Dimension(136, 24));
						}	
						{
							lblEditAcct = new JLabel("Bank Acct ID", JLabel.TRAILING);
							pnlEditDetails_a1.add(lblEditAcct);
							lblEditAcct.setEnabled(true);	
							lblEditAcct.setPreferredSize(new java.awt.Dimension(136, 24));
						}	
						{
							lblStatus = new JLabel("Status", JLabel.TRAILING);
							pnlEditDetails_a1.add(lblStatus);
							lblStatus.setEnabled(true);	
							lblStatus.setPreferredSize(new java.awt.Dimension(136, 24));
						}
					}
					{
						pnlEditDetails_a2 = new JPanel(new GridLayout(5,5,5, 5));
						pnlEditDetails_a.add(pnlEditDetails_a2, BorderLayout.CENTER);				
						pnlEditDetails_a2.setPreferredSize(new java.awt.Dimension(921, 41));
						pnlEditDetails_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						pnlEditDetails_a2.setPreferredSize(new java.awt.Dimension(200, 150));

						{
							txtRecNo = new JXTextField("");
							pnlEditDetails_a2.add(txtRecNo);
							txtRecNo.setEnabled(false);	
							txtRecNo.setEditable(false);
							txtRecNo.setBounds(120, 25, 300, 22);	
							txtRecNo.setHorizontalAlignment(JTextField.CENTER);
							txtRecNo.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						}
						{
							dteDeposit = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlEditDetails_a2.add(dteDeposit);
							dteDeposit.setBounds(485, 7, 125, 21);
							dteDeposit.setDate(null);
							dteDeposit.setEnabled(true);
							dteDeposit.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dteDeposit.getDateEditor()).setEditable(false);
							dteDeposit.setDate(Calendar.getInstance().getTime());
						}					
						{
							txtAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
							pnlEditDetails_a2.add(txtAmount);
							txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtAmount.setText("0.00");
							txtAmount.setEnabled(true);
							txtAmount.setEditable(true);
							txtAmount.setBounds(120, 0, 72, 22);			
						}	
						{
							lookupBankAcct = new _JLookup(null, "Bank Account", 2, 2);
							pnlEditDetails_a2.add(lookupBankAcct);
							lookupBankAcct.setBounds(20, 27, 20, 25);
							lookupBankAcct.setReturnColumn(0);
							lookupBankAcct.setEnabled(true);	
							lookupBankAcct.setLookupSQL(getBankAcct());
							lookupBankAcct.setPreferredSize(new java.awt.Dimension(157, 22));
							lookupBankAcct.addLookupListener(new LookupListener() {
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
							pnlEditDetails_a2.add(cmbStatus);
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

					pnlEditDetails_c = new JPanel(new BorderLayout(5, 5));
					pnlEditDetails.add(pnlEditDetails_c, BorderLayout.SOUTH);				
					pnlEditDetails_c.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlEditDetails_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlEditDetails_c.setPreferredSize(new java.awt.Dimension(500, 40));	

					{
						btnSaveDeposit = new JButton("Update Details");
						pnlEditDetails_c.add(btnSaveDeposit);
						btnSaveDeposit.setActionCommand("UpdateDetails");
						btnSaveDeposit.addActionListener(this);
						btnSaveDeposit.setEnabled(true);
					}

				}
			}
			//end of UNIDENTIFIED DEPOSITS

			//start of IDENTIFIED DEPOSITS
			{			
				pnlDep_identified = new JPanel(new BorderLayout());
				tabDeposit.addTab("          Identify Deposits         ", null, pnlDep_identified, null);
				pnlDep_identified.setPreferredSize(new java.awt.Dimension(100, 365));					

				{
					pnlDep_identified_north = new JPanel(new BorderLayout(5, 5));
					pnlDep_identified.add(pnlDep_identified_north, BorderLayout.NORTH);	
					pnlDep_identified_north.setPreferredSize(new java.awt.Dimension(916, 73));
					pnlDep_identified_north.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlDep_identified_north.setBorder(lineBorder);

					{
						pnlDep_identified_north_sub1 = new JPanel(new GridLayout(1, 2, 5, 10));
						pnlDep_identified_north.add(pnlDep_identified_north_sub1, BorderLayout.CENTER);	
						pnlDep_identified_north_sub1.setPreferredSize(new java.awt.Dimension(914, 72));	
						pnlDep_identified_north_sub1.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

						{						
							pnlDep_identified_north_sub1a = new JPanel(new GridLayout(1, 4, 5, 10));
							pnlDep_identified_north_sub1.add(pnlDep_identified_north_sub1a, BorderLayout.WEST);	
							pnlDep_identified_north_sub1a.setPreferredSize(new java.awt.Dimension(749, 40));	
							pnlDep_identified_north_sub1a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

							{
								lblSearchBy = new JLabel("               Search by :");
								pnlDep_identified_north_sub1a.add(lblSearchBy);
								lblSearchBy.setEnabled(false);	
								lblSearchBy.setPreferredSize(new java.awt.Dimension(86, 40));
								lblSearchBy.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
								lblSearchBy.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							}		
							{
								rbtnForIdentification = new JRadioButton();
								pnlDep_identified_north_sub1a.add(rbtnForIdentification);
								rbtnForIdentification.setText("For Identification Only");
								rbtnForIdentification.setBounds(277, 12, 77, 18);
								rbtnForIdentification.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								rbtnForIdentification.setSelected(true);
								rbtnForIdentification.setEnabled(false);
								rbtnForIdentification.setPreferredSize(new java.awt.Dimension(220, 18));
								rbtnForIdentification.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){									
										rbtnForIdentification.setSelected(true);	
										rbtnAllDeposits.setSelected(false);	
										lookupIdentified_Depno.setLookupSQL(getActiveDepositNo());	
									}});					
							}
							{
								rbtnAllDeposits = new JRadioButton();
								pnlDep_identified_north_sub1a.add(rbtnAllDeposits);
								rbtnAllDeposits.setText("All (Including Identified Deposits)");
								rbtnAllDeposits.setBounds(277, 12, 77, 18);
								rbtnAllDeposits.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								rbtnAllDeposits.setSelected(false);
								rbtnAllDeposits.setEnabled(false);
								rbtnAllDeposits.setPreferredSize(new java.awt.Dimension(232, 24));
								rbtnAllDeposits.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){									
										rbtnForIdentification.setSelected(false);	rbtnAllDeposits.setSelected(true);
										lookupIdentified_Depno.setLookupSQL(getDepositNo());	
									}});
							}
							{
								btnIdentDisplay = new JButton("Display");
								pnlDep_identified_north_sub1a.add(btnIdentDisplay);
								btnIdentDisplay.setActionCommand("Identified_display");
								btnIdentDisplay.addActionListener(this);
								btnIdentDisplay.setEnabled(false);
								btnIdentDisplay.setVisible(false);
								btnIdentDisplay.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							}
						}
					}

					pnlDep_identified_center = new JPanel(new BorderLayout(5, 5));
					pnlDep_identified.add(pnlDep_identified_center, BorderLayout.CENTER);	
					pnlDep_identified_center.setPreferredSize(new java.awt.Dimension(911, 42));
					pnlDep_identified_center.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlDep_identified_center.setBorder(lineBorder);

					{
						pnlDep_identified_center_north = new JPanel(new BorderLayout(5, 5));
						pnlDep_identified_center.add(pnlDep_identified_center_north, BorderLayout.NORTH);	
						pnlDep_identified_center_north.setPreferredSize(new java.awt.Dimension(914, 44));
						pnlDep_identified_center_north.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
						pnlDep_identified_center_north.setBorder(lineBorder);						

						pnlDep_identified_center_north_dupl = new JPanel(new BorderLayout(5, 5));
						pnlDep_identified_center_north.add(pnlDep_identified_center_north_dupl, BorderLayout.NORTH);	
						pnlDep_identified_center_north_dupl.setPreferredSize(new java.awt.Dimension(912, 43));
						pnlDep_identified_center_north_dupl.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
						pnlDep_identified_center_north_dupl.setBorder(lineBorder);

						{
							{
								pnlDep_identified_center_north_sub1 = new JPanel(new GridLayout(1, 1, 5, 10));
								pnlDep_identified_center_north_dupl.add(pnlDep_identified_center_north_sub1, BorderLayout.WEST);	
								pnlDep_identified_center_north_sub1.setPreferredSize(new java.awt.Dimension(92, 38));	
								pnlDep_identified_center_north_sub1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

								{
									lblIden_DepNo = new JLabel("Deposit No.", JLabel.TRAILING);
									pnlDep_identified_center_north_sub1.add(lblIden_DepNo);
									lblIden_DepNo.setEnabled(false);	
									lblIden_DepNo.setPreferredSize(new java.awt.Dimension(86, 40));
									lblIden_DepNo.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
									lblIden_DepNo.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}
							}						
							{
								pnlDep_identified_center_north_sub2 = new JPanel(new BorderLayout(5, 5));
								pnlDep_identified_center_north_dupl.add(pnlDep_identified_center_north_sub2, BorderLayout.CENTER);
								pnlDep_identified_center_north_sub2.setPreferredSize(new java.awt.Dimension(814, 40));	
								pnlDep_identified_center_north_sub2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

								{
									pnlDep_identified_center_north_sub2a = new JPanel(new GridLayout(1, 1, 5, 10));
									pnlDep_identified_center_north_sub2.add(pnlDep_identified_center_north_sub2a, BorderLayout.WEST);
									pnlDep_identified_center_north_sub2a.setPreferredSize(new java.awt.Dimension(128, 26));	

									{
										lookupIdentified_Depno = new _JLookup(null, "PV No.", 2, 2);
										pnlDep_identified_center_north_sub2a.add(lookupIdentified_Depno);
										lookupIdentified_Depno.setBounds(20, 27, 20, 25);
										lookupIdentified_Depno.setReturnColumn(0);
										lookupIdentified_Depno.setEnabled(false);
										lookupIdentified_Depno.setPreferredSize(new java.awt.Dimension(157, 22));
										lookupIdentified_Depno.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){	

													String dep_no 		= (String) data[0].toString();
													String status 		= (String) data[7];
													String bank_acct_no = (String) data[4];
													String dep_amt 		= (String) data[5].toString();
													String dep_date 	= (String) data[1].toString();
													String tag_date 	= (String) data[8].toString();
													String bank_alias 	= (String) data[3].toString();
													String bank_name 	= (String) data[9].toString();
													String receipt_no 	= (String) data[10].toString();

													txtIden_status.setText(status);
													txtIden_BankAcct.setText(bank_acct_no);
													txtIden_DepAmt.setText(nf.format(Double.parseDouble(dep_amt)));
													txtIden_DepDate.setText(dep_date);
													txtIden_TagDate.setText(tag_date);
													txtIden_bankNo.setText(bank_alias);
													txtIden_BankName.setText(bank_name);
													txtIden_ReceiptNo.setText(receipt_no);

													lookupIden_PBL.setValue("");
													lookupIden_payment.setValue("");
													txtIden_PBL.setText("");
													txtPayment.setText("");
													txtProject.setText("");
													txtClientName.setText("");	
													setIdentified_Dep_details(dep_no);

													if (status.trim().equals("ACTIVE"))
													{ enableButtons_iden(true, false,  false, false,  true );  } 

													else if (status.trim().equals("TAGGED"))
													{ enableButtons_iden(false, true, false,  false, true ); } 

													else 
													{ enableButtons_iden(false, false, false,  false, true ); } 

													enable_fields(false);
													/*if (receipt_no.trim().equals("")&&status.equals("TAGGED"))
													{btnIssueOR.setEnabled(true);}
													else {btnIssueOR.setEnabled(false);}	*/											

												}
											}
										});
									}	
								}
								{
									pnlDep_identified_center_north_sub2b = new JPanel(new GridLayout(1, 2, 5, 0));
									pnlDep_identified_center_north_sub2.add(pnlDep_identified_center_north_sub2b, BorderLayout.CENTER);
									pnlDep_identified_center_north_sub2b.setPreferredSize(new java.awt.Dimension(357, 25));	

									{
										lblIden_status = new JLabel("Status", JLabel.TRAILING);
										pnlDep_identified_center_north_sub2b.add(lblIden_status);
										lblIden_status.setEnabled(false);	
									}	
									{
										txtIden_status = new JXTextField("");
										pnlDep_identified_center_north_sub2b.add(txtIden_status);
										txtIden_status.setEnabled(false);	
										txtIden_status.setEditable(false);
										txtIden_status.setBounds(120, 25, 300, 22);	
										txtIden_status.setHorizontalAlignment(JTextField.CENTER);	
										txtIden_status.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
									}	
								}
								{
									pnlDep_identified_center_north_sub2c = new JPanel(new GridLayout(1, 2, 5, 0));
									pnlDep_identified_center_north_sub2.add(pnlDep_identified_center_north_sub2c, BorderLayout.EAST);
									pnlDep_identified_center_north_sub2.setPreferredSize(new java.awt.Dimension(357, 25));	

									{
										lblIden_ReceiptNo = new JLabel("Receipt No.", JLabel.TRAILING);
										pnlDep_identified_center_north_sub2c.add(lblIden_ReceiptNo);
										lblIden_ReceiptNo.setPreferredSize(new java.awt.Dimension(127, 26));
										lblIden_ReceiptNo.setEnabled(false);	
									}	
									{
										txtIden_ReceiptNo = new JXTextField("");
										pnlDep_identified_center_north_sub2c.add(txtIden_ReceiptNo);
										txtIden_ReceiptNo.setEnabled(false);	
										txtIden_ReceiptNo.setEditable(false);
										txtIden_ReceiptNo.setBounds(120, 25, 300, 22);	
										txtIden_ReceiptNo.setHorizontalAlignment(JTextField.CENTER);	
										txtIden_ReceiptNo.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
									}	
								}		
								{
									pnlDep_identified_center_north_sub3 = new JPanel(new BorderLayout(5, 5));
									pnlDep_identified_center_north_dupl.add(pnlDep_identified_center_north_sub3, BorderLayout.EAST);	
									pnlDep_identified_center_north_sub3.setPreferredSize(new java.awt.Dimension(141, 42));	
									pnlDep_identified_center_north_sub3.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
								}
							}
						}

						pnlDep_identified_center_center = new JPanel(new BorderLayout(5, 5));
						pnlDep_identified_center.add(pnlDep_identified_center_center, BorderLayout.CENTER);	
						pnlDep_identified_center_center.setPreferredSize(new java.awt.Dimension(914, 146));
						pnlDep_identified_center_center.setBorder(JTBorderFactory.createTitleBorder("Deposit Details"));	

						{
							pnlDep_identified_center_center_sub1 = new JPanel(new BorderLayout(5, 5));
							pnlDep_identified_center_center.add(pnlDep_identified_center_center_sub1, BorderLayout.NORTH);	
							pnlDep_identified_center_center_sub1.setPreferredSize(new java.awt.Dimension(914, 26));

							{
								pnlDep_identified_center_center_sub1a = new JPanel(new GridLayout(1, 2, 5, 10));
								pnlDep_identified_center_center_sub1.add(pnlDep_identified_center_center_sub1a, BorderLayout.WEST);	
								pnlDep_identified_center_center_sub1a.setPreferredSize(new java.awt.Dimension(254, 36));

								{
									lblIden_bank = new JLabel("Bank", JLabel.TRAILING);
									pnlDep_identified_center_center_sub1a.add(lblIden_bank);
									lblIden_bank.setPreferredSize(new java.awt.Dimension(105, 26));
									lblIden_bank.setEnabled(false);	
								}	
								{
									txtIden_bankNo = new JXTextField("");
									pnlDep_identified_center_center_sub1a.add(txtIden_bankNo);
									txtIden_bankNo.setEnabled(false);	
									txtIden_bankNo.setEditable(false);
									txtIden_bankNo.setBounds(120, 25, 300, 22);	
									txtIden_bankNo.setHorizontalAlignment(JTextField.CENTER);	
									txtIden_bankNo.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	


								pnlDep_identified_center_center_sub1b = new JPanel(new BorderLayout(5, 5));
								pnlDep_identified_center_center_sub1.add(pnlDep_identified_center_center_sub1b, BorderLayout.CENTER);	
								pnlDep_identified_center_center_sub1b.setPreferredSize(new java.awt.Dimension(453, 36));

								{
									txtIden_BankName = new JXTextField("");
									pnlDep_identified_center_center_sub1b.add(txtIden_BankName);
									txtIden_BankName.setEnabled(false);	
									txtIden_BankName.setEditable(false);
									txtIden_BankName.setBounds(120, 25, 300, 22);	
									txtIden_BankName.setHorizontalAlignment(JTextField.CENTER);	
									txtIden_BankName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	

								pnlDep_identified_center_center_sub1c = new JPanel(new BorderLayout(5, 5));
								pnlDep_identified_center_center_sub1.add(pnlDep_identified_center_center_sub1c, BorderLayout.EAST);	
								pnlDep_identified_center_center_sub1c.setPreferredSize(new java.awt.Dimension(325, 26));
							}
						}
						{
							pnlDep_identified_center_center_sub2 = new JPanel(new BorderLayout(5, 5));
							pnlDep_identified_center_center.add(pnlDep_identified_center_center_sub2, BorderLayout.CENTER);	
							pnlDep_identified_center_center_sub2.setPreferredSize(new java.awt.Dimension(914, 66));

							{													
								pnlDep_identified_center_center_sub2a = new JPanel(new GridLayout(2, 2, 5, 10));
								pnlDep_identified_center_center_sub2.add(pnlDep_identified_center_center_sub2a, BorderLayout.WEST);	
								pnlDep_identified_center_center_sub2a.setPreferredSize(new java.awt.Dimension(254, 36));

								{
									lblIden_BankAcct = new JLabel("Bank Account", JLabel.TRAILING);
									pnlDep_identified_center_center_sub2a.add(lblIden_BankAcct);
									lblIden_BankAcct.setPreferredSize(new java.awt.Dimension(127, 26));
									lblIden_BankAcct.setEnabled(false);	
								}	
								{
									txtIden_BankAcct = new JXTextField("");
									pnlDep_identified_center_center_sub2a.add(txtIden_BankAcct);
									txtIden_BankAcct.setEnabled(false);	
									txtIden_BankAcct.setEditable(false);
									txtIden_BankAcct.setBounds(120, 25, 300, 22);	
									txtIden_BankAcct.setHorizontalAlignment(JTextField.CENTER);	
									txtIden_BankAcct.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	
								{
									lblIden_DepAmt = new JLabel("Deposit Amount.", JLabel.TRAILING);
									pnlDep_identified_center_center_sub2a.add(lblIden_DepAmt);
									lblIden_DepAmt.setPreferredSize(new java.awt.Dimension(127, 26));
									lblIden_DepAmt.setEnabled(false);	
								}	
								{
									//txtIden_DepAmt = new JXTextField("");
									txtIden_DepAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
									pnlDep_identified_center_center_sub2a.add(txtIden_DepAmt);
									txtIden_DepAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtIden_DepAmt.setText("0.00");
									txtIden_DepAmt.setBounds(120, 0, 72, 22);
									txtIden_DepAmt.setEnabled(false);	
									txtIden_DepAmt.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	
							}
							{
								pnlDep_identified_center_center_sub2b = new JPanel(new BorderLayout(5, 5));
								pnlDep_identified_center_center_sub2.add(pnlDep_identified_center_center_sub2b, BorderLayout.CENTER);	
								pnlDep_identified_center_center_sub2b.setPreferredSize(new java.awt.Dimension(547, 57));
								{
									pnlDep_identified_center_center_sub2b_a = new JPanel(new GridLayout(2, 1, 5, 10));
									pnlDep_identified_center_center_sub2b.add(pnlDep_identified_center_center_sub2b_a, BorderLayout.WEST);	
									pnlDep_identified_center_center_sub2b_a.setPreferredSize(new java.awt.Dimension(100, 57));		

									{
										lblIden_DepDate = new JLabel("Deposit Date", JLabel.TRAILING);
										pnlDep_identified_center_center_sub2b_a.add(lblIden_DepDate);
										lblIden_DepDate.setPreferredSize(new java.awt.Dimension(127, 26));
										lblIden_DepDate.setEnabled(false);	
									}	
									{
										lblIden_TagDate = new JLabel("Tagging Date", JLabel.TRAILING);
										pnlDep_identified_center_center_sub2b_a.add(lblIden_TagDate);
										lblIden_TagDate.setPreferredSize(new java.awt.Dimension(127, 26));
										lblIden_TagDate.setEnabled(false);	
									}	
								}
								{
									pnlDep_identified_center_center_sub2b_b = new JPanel(new GridLayout(2, 1, 5, 10));
									pnlDep_identified_center_center_sub2b.add(pnlDep_identified_center_center_sub2b_b, BorderLayout.CENTER);	
									pnlDep_identified_center_center_sub2b_b.setPreferredSize(new java.awt.Dimension(202, 57));		

									{
										txtIden_DepDate = new JXTextField("");
										pnlDep_identified_center_center_sub2b_b.add(txtIden_DepDate);
										txtIden_DepDate.setEnabled(false);	
										txtIden_DepDate.setEditable(false);
										txtIden_DepDate.setBounds(120, 25, 300, 22);	
										txtIden_DepDate.setHorizontalAlignment(JTextField.CENTER);	
										txtIden_DepDate.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
									}	
									{
										txtIden_TagDate = new JXTextField("");
										pnlDep_identified_center_center_sub2b_b.add(txtIden_TagDate);
										txtIden_TagDate.setEnabled(false);	
										txtIden_TagDate.setEditable(false);
										txtIden_TagDate.setBounds(120, 25, 300, 22);	
										txtIden_TagDate.setHorizontalAlignment(JTextField.CENTER);	
										txtIden_TagDate.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
									}	
								}
								{
									pnlDep_identified_center_center_sub2b_c = new JPanel(new GridLayout(2, 1, 5, 10));
									pnlDep_identified_center_center_sub2b.add(pnlDep_identified_center_center_sub2b_c, BorderLayout.EAST);	
									pnlDep_identified_center_center_sub2b_c.setPreferredSize(new java.awt.Dimension(325, 57));
								}
							}
						}



						pnlDep_identified_center_south = new JPanel(new BorderLayout(5, 5));
						pnlDep_identified_center.add(pnlDep_identified_center_south, BorderLayout.SOUTH);	
						pnlDep_identified_center_south.setPreferredSize(new java.awt.Dimension(914, 210));
						pnlDep_identified_center_south.setBorder(JTBorderFactory.createTitleBorder("Depositor Details"));

						{
							pnlDep_identified_center_south_sub1 = new JPanel(new BorderLayout(5, 5));
							pnlDep_identified_center_south.add(pnlDep_identified_center_south_sub1, BorderLayout.NORTH);	
							pnlDep_identified_center_south_sub1.setPreferredSize(new java.awt.Dimension(914, 67));				

							{
								pnlDep_identified_center_south_sub1a = new JPanel(new GridLayout(2, 2, 5, 10));
								pnlDep_identified_center_south_sub1.add(pnlDep_identified_center_south_sub1a, BorderLayout.WEST);	
								pnlDep_identified_center_south_sub1a.setPreferredSize(new java.awt.Dimension(254, 36));

								{
									lblIden_PBL = new JLabel("PBL No. | Seq. No.", JLabel.TRAILING);
									pnlDep_identified_center_south_sub1a.add(lblIden_PBL);
									lblIden_PBL.setPreferredSize(new java.awt.Dimension(127, 26));
									lblIden_PBL.setEnabled(false);	
								}	
								{
									lookupIden_PBL = new _JLookup(null, "PBL", 2, 2);
									pnlDep_identified_center_south_sub1a.add(lookupIden_PBL);
									lookupIden_PBL.setBounds(20, 27, 20, 25);
									lookupIden_PBL.setReturnColumn(0);
									lookupIden_PBL.setEnabled(false);	
									lookupIden_PBL.setPreferredSize(new java.awt.Dimension(157, 22));
									lookupIden_PBL.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){	

												pbl_id		  = (String) data[0].toString().trim();
												String desc   = (String) data[1];
												String proj   = (String) data[2];
												String client = (String) data[3];
												seq_no		  = (String) data[4].toString();
												entity_id	  = (String) data[5].toString().trim();
												dept_name	  = (String) data[6].toString().trim();

												lookupIden_PBL.setValue(pbl_id+" | "+seq_no);
												txtIden_PBL.setText(desc);
												txtProject.setText(proj);
												txtClientName.setText(entity_id+ " | " + client);
												txtSalesDivGrp.setText(dept_name);

											}
										}
									});	
								}	
								{
									lblIden_payment = new JLabel("Payment", JLabel.TRAILING);
									pnlDep_identified_center_south_sub1a.add(lblIden_payment);
									lblIden_payment.setPreferredSize(new java.awt.Dimension(127, 26));
									lblIden_payment.setEnabled(false);	
								}	
								{
									lookupIden_payment = new _JLookup(null, "Payment", 2, 2);
									pnlDep_identified_center_south_sub1a.add(lookupIden_payment);
									lookupIden_payment.setBounds(20, 27, 20, 25);
									lookupIden_payment.setReturnColumn(0);
									lookupIden_payment.setEnabled(false);	
									lookupIden_payment.setPreferredSize(new java.awt.Dimension(157, 22));
									lookupIden_payment.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){		
												String client = (String) data[2];												
												txtPayment.setText(client);
											}
										}
									});	
								}	
							}
							{
								pnlDep_identified_center_south_sub1b = new JPanel(new GridLayout(2, 2, 5, 10));
								pnlDep_identified_center_south_sub1.add(pnlDep_identified_center_south_sub1b, BorderLayout.CENTER);	
								pnlDep_identified_center_south_sub1b.setPreferredSize(new java.awt.Dimension(254, 36));


								{
									txtIden_PBL = new JXTextField("");
									pnlDep_identified_center_south_sub1b.add(txtIden_PBL);
									txtIden_PBL.setEnabled(false);	
									txtIden_PBL.setEditable(false);
									txtIden_PBL.setBounds(120, 25, 300, 22);	
									txtIden_PBL.setHorizontalAlignment(JTextField.CENTER);	
									txtIden_PBL.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	
								{
									txtPayment = new JXTextField("");
									pnlDep_identified_center_south_sub1b.add(txtPayment);
									txtPayment.setEnabled(false);	
									txtPayment.setEditable(false);
									txtPayment.setBounds(120, 25, 300, 22);	
									txtPayment.setHorizontalAlignment(JTextField.CENTER);	
									txtPayment.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	
							}

							pnlDep_b3b = new JPanel(new BorderLayout(5, 5));
							pnlDep_identified_center_south.add(pnlDep_b3b, BorderLayout.CENTER);	
							pnlDep_b3b.setPreferredSize(new java.awt.Dimension(914, 95));

							{
								pnlDep_b2_1a_sub = new JPanel(new GridLayout(3, 2, 5, 10));
								pnlDep_b3b.add(pnlDep_b2_1a_sub, BorderLayout.WEST);	
								pnlDep_b2_1a_sub.setPreferredSize(new java.awt.Dimension(125, 83));
								{
									lblProject = new JLabel("Project", JLabel.TRAILING);
									pnlDep_b2_1a_sub.add(lblProject);
									lblProject.setPreferredSize(new java.awt.Dimension(127, 26));
									lblProject.setEnabled(false);	
								}	
								{
									lblClientName = new JLabel("Client ID | Name", JLabel.TRAILING);
									pnlDep_b2_1a_sub.add(lblClientName);
									lblClientName.setPreferredSize(new java.awt.Dimension(127, 26));
									lblClientName.setEnabled(false);	
								}
								{
									lblSalesDivGrp = new JLabel("Sales Div. - Grp.", JLabel.TRAILING);
									pnlDep_b2_1a_sub.add(lblSalesDivGrp);
									lblSalesDivGrp.setPreferredSize(new java.awt.Dimension(127, 26));
									lblSalesDivGrp.setEnabled(false);	
								}
							}
							{
								pnlDep_b2_1a_sub = new JPanel(new GridLayout(3, 2, 5, 10));
								pnlDep_b3b.add(pnlDep_b2_1a_sub, BorderLayout.CENTER);	
								pnlDep_b2_1a_sub.setPreferredSize(new java.awt.Dimension(125, 83));

								{
									txtProject = new JXTextField("");
									pnlDep_b2_1a_sub.add(txtProject);
									txtProject.setEnabled(false);	
									txtProject.setEditable(false);
									txtProject.setBounds(120, 25, 300, 22);	
									txtProject.setHorizontalAlignment(JTextField.CENTER);	
									txtProject.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	
								{
									txtClientName = new JXTextField("");
									pnlDep_b2_1a_sub.add(txtClientName);
									txtClientName.setEnabled(false);	
									txtClientName.setEditable(false);
									txtClientName.setBounds(120, 25, 300, 22);	
									txtClientName.setHorizontalAlignment(JTextField.CENTER);	
									txtClientName.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	
								{
									txtSalesDivGrp = new JXTextField("");
									pnlDep_b2_1a_sub.add(txtSalesDivGrp);
									txtSalesDivGrp.setEnabled(false);	
									txtSalesDivGrp.setEditable(false);
									txtSalesDivGrp.setBounds(120, 25, 300, 22);	
									txtSalesDivGrp.setHorizontalAlignment(JTextField.CENTER);	
									txtSalesDivGrp.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
								}	
							}
						}
					}
				}
				{
					pnlDep_identified_south = new JPanel(new BorderLayout(5, 5));
					pnlDep_identified.add(pnlDep_identified_south, BorderLayout.SOUTH);	
					pnlDep_identified_south.setPreferredSize(new java.awt.Dimension(916, 34));
					pnlDep_identified_south.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlDep_identified_south.setBorder(lineBorder);

					pnlSouthCenterb_2 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlDep_identified_south.add(pnlSouthCenterb_2, BorderLayout.NORTH);
					pnlSouthCenterb_2.setPreferredSize(new java.awt.Dimension(921, 31));
					{						
						{
							btnTagDetails = new JButton("Tag Details");
							pnlSouthCenterb_2.add(btnTagDetails);
							btnTagDetails.setActionCommand("tag_detail");
							btnTagDetails.addActionListener(this);
							btnTagDetails.setEnabled(false);
						}
						{
							btnEditIden = new JButton("Edit");
							pnlSouthCenterb_2.add(btnEditIden);
							btnEditIden.setActionCommand("Edit");
							btnEditIden.addActionListener(this);
							btnEditIden.setEnabled(false);
							btnEditIden.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									tag_details();

								}
							});
						}
						{
							btnSaveIden = new JButton("Save");
							pnlSouthCenterb_2.add(btnSaveIden);
							btnSaveIden.setActionCommand("Save_2");
							btnSaveIden.addActionListener(this);
							btnSaveIden.setEnabled(false);
						}
						{
							btnPreviewList = new JButton("Preview List (Identified)");
							pnlSouthCenterb_2.add(btnPreviewList);
							btnPreviewList.addActionListener(this);
							btnPreviewList.setEnabled(false);
							btnPreviewList.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									preview_what_rpt = "identified";
									
									int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Report Period",
											JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

									if ( scanOption == JOptionPane.CLOSED_OPTION ) {
										try {	

										} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {}				
									} // CLOSED_OPTION
								}
							});
						}
						{
							btnCancel_2 = new JButton("Cancel");
							pnlSouthCenterb_2.add(btnCancel_2);
							btnCancel_2.setActionCommand("Cancel_2");
							btnCancel_2.addActionListener(this);
							btnCancel_2.setEnabled(false);
						}
					}
				}
			}


			//start of Promo (by client)
			{
				pnlApproval = new JPanel(new BorderLayout());
				tabDeposit.addTab("Approve Identified Deposits", null, pnlApproval, null);
				pnlApproval.setPreferredSize(new java.awt.Dimension(1183, 365));

				pnlApproval_a = new JPanel(new BorderLayout(5, 5));
				pnlApproval.add(pnlApproval_a, BorderLayout.CENTER);	
				pnlApproval_a.setPreferredSize(new java.awt.Dimension(911, 42));
				pnlApproval_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

				{
					scrollApproval = new _JScrollPaneMain();
					pnlApproval_a.add(scrollApproval, BorderLayout.CENTER);
					{
						modelApproval = new modelIdentifiedDep_approval();

						tblApproval = new _JTableMain(modelApproval);
						scrollApproval.setViewportView(tblApproval);
						//tblApproval.addMouseListener(this);							
						tblApproval.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {tblApproval.packAll();}							
							public void keyPressed(KeyEvent e) {tblApproval.packAll();}	
						}); 
						tblApproval.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblApproval.rowAtPoint(e.getPoint()) == -1){tblApproval_total.clearSelection();}
								else{tblApproval.setCellSelectionEnabled(true);}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblApproval.rowAtPoint(e.getPoint()) == -1){tblApproval_total.clearSelection();}
								else{tblApproval.setCellSelectionEnabled(true);}
							}
						});
						tblApproval.hideColumns("entity_id", "pbl_id", "seq_no");
					}
					{
						rowHeaderApproval= tblApproval.getRowHeader22();
						scrollApproval.setRowHeaderView(rowHeaderApproval);
						scrollApproval.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollApproval_total = new _JScrollPaneTotal(scrollApproval);
					pnlApproval_a.add(scrollApproval_total, BorderLayout.SOUTH);
					{
						modelApproval_total = new modelIdentifiedDep_approval();
						modelApproval_total.addRow(new Object[] { "Total",null, null, null, new BigDecimal(0.00)});

						tblApproval_total = new _JTableTotal(modelApproval_total, tblApproval);
						tblApproval_total.setFont(dialog11Bold);
						scrollApproval_total.setViewportView(tblApproval_total);
						((_JTableTotal) tblApproval_total).setTotalLabel(0);
					}
				}
				{
					pnlDep_identified_south = new JPanel(new BorderLayout(5, 5));
					pnlApproval.add(pnlDep_identified_south, BorderLayout.SOUTH);	
					pnlDep_identified_south.setPreferredSize(new java.awt.Dimension(916, 34));
					pnlDep_identified_south.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlDep_identified_south.setBorder(lineBorder);

					pnlSouthCenterb_2 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlDep_identified_south.add(pnlSouthCenterb_2, BorderLayout.NORTH);
					pnlSouthCenterb_2.setPreferredSize(new java.awt.Dimension(921, 31));
					{						
						{
							btnApprove = new JButton("Approve");
							pnlSouthCenterb_2.add(btnApprove);
							btnApprove.setActionCommand("approve");
							btnApprove.addActionListener(this);
							btnApprove.setEnabled(false);
							btnApprove.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									approveDeposits();			
								}
							});
						}
						{
							btnPreviewApproved = new JButton("Display Approved Deposits");
							pnlSouthCenterb_2.add(btnPreviewApproved);
							btnPreviewApproved.setVisible(true);
							btnPreviewApproved.setEnabled(false);
							btnPreviewApproved.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									preview_what_rpt = "approved";
									
									int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Report Period",
											JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

									if ( scanOption == JOptionPane.CLOSED_OPTION ) {
										try {	

										} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {}				
									}
								}
							});
						}
						{
							btnRefreshForApproval = new JButton("Refesh List for Approval");
							pnlSouthCenterb_2.add(btnRefreshForApproval);
							btnRefreshForApproval.setVisible(true);
							btnRefreshForApproval.setEnabled(false);
							btnRefreshForApproval.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									displayForApproval(modelApproval, rowHeaderApproval, modelApproval_total);
									JOptionPane.showMessageDialog(getContentPane(),"List of deposits for approval refreshed.","Information",JOptionPane.INFORMATION_MESSAGE);	
								}
							});
						}
						{
							btnX = new JButton("Cancel");
							pnlSouthCenterb_2.add(btnX);
							btnX.setVisible(false);
						}
					}
				}
			}
			
			{
				JPanel pnlIssuanceofDirectDeposit = new JPanel(new BorderLayout(5, 5));
				tabDeposit.addTab("Issuance of Direct Deposit", null, pnlIssuanceofDirectDeposit, null);
				{
					JPanel pnlDirectDepositMain = new JPanel(new BorderLayout(5, 5));
					pnlIssuanceofDirectDeposit.add(pnlDirectDepositMain);
					pnlDirectDepositMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlNorth = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlDirectDepositMain.add(pnlNorth, BorderLayout.NORTH);
						pnlNorth.setPreferredSize(new Dimension(0, 25));
						{
							JPanel pnlNorthWest = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlNorthWest);
							{
								JLabel lblDirectDepositBatchNo = new JLabel("Batch No:");
								pnlNorthWest.add(lblDirectDepositBatchNo, BorderLayout.WEST);
							}
							{
								JPanel pnlBatchDirectDeposit = new JPanel(new BorderLayout(5, 5));
								pnlNorthWest.add(pnlBatchDirectDeposit, BorderLayout.CENTER);
								
								{
									lookupDirectDepositBatch = new _JLookup(null, "Batch", 0);
									pnlBatchDirectDeposit.add(lookupDirectDepositBatch, BorderLayout.WEST);
									lookupDirectDepositBatch.setPreferredSize(new Dimension(100, 0));
									lookupDirectDepositBatch.setLookupSQL(sqlDDBatch());
									lookupDirectDepositBatch.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null) {
												String batch_no = (String) data[0];
												
												displayDDForIssuance(modelDDIssuance, rowHeaderDDIssuance, lookupCompany.getValue(), batch_no);
												if(isBatchIssued(batch_no)) {
													btnIssueReceiptDD.setEnabled(false);
												}else {
													btnIssueReceiptDD.setEnabled(true);
												}
												
												btnPreviewBatch.setEnabled(true);
												btnCancelBatch.setEnabled(true);
											}
										}
									});
								}
							}
							
						}
						{
							JPanel pnlNorthEast = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlNorthEast);
						}
					}
					{
						JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
						pnlDirectDepositMain.add(pnlCenter, BorderLayout.CENTER);
						{
							scrollDDForIssuance = new JScrollPane();
							pnlCenter.add(scrollDDForIssuance, BorderLayout.CENTER);
							scrollDDForIssuance.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							{
								modelDDIssuance = new modelDirectDepositIssuance();
								tblDDIssuance = new _JTableMain(modelDDIssuance);
								scrollDDForIssuance.setViewportView(tblDDIssuance);
								tblDDIssuance.hideColumns("Rec. ID");
								tblDDIssuance.setSortable(false);
							}
							{
								rowHeaderDDIssuance = tblDDIssuance.getRowHeader();
								rowHeaderDDIssuance.setModel(new DefaultListModel());
								scrollDDForIssuance.setRowHeaderView(rowHeaderDDIssuance);
								scrollDDForIssuance.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
					{
						JPanel pnlSouth = new JPanel(new GridLayout(1, 5, 5, 5));
						pnlDirectDepositMain.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setPreferredSize(new Dimension(0, 50));
						{
							pnlSouth.add(Box.createHorizontalBox());
							pnlSouth.add(Box.createHorizontalBox());
						}
						{
							btnIssueReceiptDD = new JButton("Issue Receipt");
							pnlSouth.add(btnIssueReceiptDD);
							btnIssueReceiptDD.setActionCommand("Issue Receipt DD");
							btnIssueReceiptDD.addActionListener(this);
							btnIssueReceiptDD.setEnabled(false);
						}
						{
							btnPreviewBatch = new JButton("Preview Batch");
							pnlSouth.add(btnPreviewBatch);
							btnPreviewBatch.setActionCommand("Preview DD Batch");
							btnPreviewBatch.addActionListener(this);
							btnPreviewBatch.setEnabled(false);
						}
						{
							btnCancelBatch = new JButton("Cancel");
							pnlSouth.add(btnCancelBatch);
							btnCancelBatch.setActionCommand("Cancel Batch");
							btnCancelBatch.addActionListener(this);
							btnCancelBatch.setEnabled(false);
						}
					}
				}
			}
			//end of Promo (by client)
		} 
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display tables
	public void displayUnidentifiedDep_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, Integer rec_no) {//ok

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"---------display Unidentified Deposits details \r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"a.deposit_date,\r\n" + 
			"a.amount,\r\n" +  
			"a.bank_acct_id,\r\n" + 
			"a.bank_account,\r\n" + 
			"d.bank_alias," +
			"a.record_id, " +
			"upper(trim(ee.last_name))||', '||left(upper(trim(ee.first_name)),1)||left(upper(trim(ee.middle_name)),1),\r\n" + 
			"a.tagged_date," +
			"upper(trim(ff.last_name))||', '||left(upper(trim(ff.first_name)),1)||left(upper(trim(ff.middle_name)),1),\r\n" + 
			"a.identified_date, \n" +
			"upper(trim(gg.last_name))||', '||left(upper(trim(gg.first_name)),1)||left(upper(trim(gg.middle_name)),1),\r\n" + 
			"a.edited_date \n" +

			"from tf_unidentified_dep a \r\n" + 			
			"left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id  \r\n" + 
			"left join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id\r\n" + 
			"left join mf_bank d on c.bank_id = d.bank_id  \n" +
			"left join em_employee e on a.tagged_by = e.emp_code \n" +
			"left join rf_entity ee on e.entity_id = ee.entity_id \n" +
			"left join em_employee f on a.identified_by = f.emp_code \n" +
			"left join rf_entity ff on f.entity_id = ff.entity_id \n\n" +
			"left join em_employee g on a.edited_by = g.emp_code \n" +
			"left join rf_entity gg on g.entity_id = gg.entity_id \n\n" +

			"where a.record_id = "+rec_no+" and a.co_id = '"+co_id+"'  " +
			"order by a.deposit_date" ;

		System.out.printf("SQL #1: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalUnidentified(modelMain, modelTotal);			
		}		

		else {
			modelDep_unidentified_total = new modelUnidentified_deposits();
			modelDep_unidentified_total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), null, null});

			tblDep_unidentified_total = new _JTableTotal(modelDep_unidentified_total, tblDep_unidentified);
			tblDep_unidentified_total.setFont(dialog11Bold);
			scrollDep_unidentified_total.setViewportView(tblDep_unidentified_total);
			((_JTableTotal) tblDep_unidentified_total).setTotalLabel(0);
		}

		tblDep_unidentified.packAll();
		tblDep_unidentified.getColumnModel().getColumn(1).setPreferredWidth(120);

		adjustRowHeight_account(tblDep_unidentified);

	}
	
	private String sqlDDBatch() {
		return "SELECT batch_no as \"Batch No\" FROM rf_direct_deposit_for_issuance where batch_no is not null group by batch_no;";
	}
	
	private void displayDDForIssuance(DefaultTableModel model, JList rowheader,String co_id ,String batch_no) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowheader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_direct_deposit_for_issuance_v2('"+co_id+"', '"+batch_no+"')";
		db.select(SQL);
		
		if(db.isNotNull()) {
			for(int x= 0;x<db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	private void previewReceiptBatch() {
		
		for(int x= 0; x<modelDDIssuance.getRowCount(); x++) {
			String client_seqno = (String) modelDDIssuance.getValueAt(x, 0);
			String receipt_type = (String) modelDDIssuance.getValueAt(x, 2);
			String receipt_no = (String) modelDDIssuance.getValueAt(x, 0);
			
			if(isLedgerApplied(client_seqno) && isLedgerAppliedCorrect(client_seqno)) {
				if(receipt_type.equals("AR")) {
					Map<String, Object> mapParametersAR = new HashMap<String, Object>();
					mapParametersAR.put("client_seqno", client_seqno);
					mapParametersAR.put("ar_no", receipt_no);
					mapParametersAR.put("prepared_by", UserInfo.Alias.toUpperCase());
					
					FncReport.generateReport("/Reports/rptARReceipt_2.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", receipt_no), mapParametersAR);
				}
				if(receipt_type.equals("ORV")) {
					Map<String, Object> mapParametersOR = new HashMap<String, Object>();
					mapParametersOR.put("client_seqno", client_seqno);
					mapParametersOR.put("or_no", receipt_no);
					mapParametersOR.put("prepared_by", UserInfo.Alias.toUpperCase());
					
					FncReport.generateReport("/Reports/rptORReceipt_v2.jasper", "Official Receipt", String.format("OR No.: %s", receipt_no), mapParametersOR);
				}
				
				if(receipt_type.equals("PIR")) {
					Map<String, Object> mapParametersPIR = new HashMap<String, Object>();
					mapParametersPIR.put("client_seqno", client_seqno);
					mapParametersPIR.put("or_no", receipt_no);
					mapParametersPIR.put("prepared_by", UserInfo.Alias.toUpperCase());
					
					FncReport.generateReport("/Reports/rptPagIBIGReceipt.jasper", "PagIBIG Official Receipt", String.format("PagIBIG OR No.: %s", receipt_no), mapParametersPIR);
				}
				
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Payment application not in order. Contact JST.", "Preview", JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	
	private Boolean isLedgerApplied(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT is_ledger_applied('"+client_seqno+"')";
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		}else {
			return false;
		}
	}

	private Boolean isLedgerAppliedCorrect(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT is_ledger_applied_correct('"+client_seqno+"')";
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		}else {
			return false;
		}
	}

	public void displayAllUnidentifiedDep_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String status) {//ok

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"---------display Unidentified Deposits details \r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"a.deposit_date,\r\n" + 
			"a.amount,\r\n" +  
			"a.bank_acct_id,\r\n" + 
			"a.bank_account,\r\n" + 
			"d.bank_alias," +
			"a.record_id, " +  
			"trim(upper(ee.entity_name))," +
			"a.tagged_date," +
			"trim(upper(ff.entity_name))," +
			"a.identified_date \n" +

			"from tf_unidentified_dep a\r\n" + 			
			"left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id  \r\n" + 
			"left join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id\r\n" + 
			"left join mf_bank d on c.bank_id = d.bank_id  " +
			"left join em_employee e on a.tagged_by = e.emp_code \n" +
			"left join rf_entity ee on e.entity_id = ee.entity_id \n" +
			"left join em_employee f on a.identified_by = f.emp_code \n" +
			"left join rf_entity ff on f.entity_id = ff.entity_id \n\n" +

			"where a.co_id = '"+co_id+"' and a.status_id = '"+status+"'  " +
			"order by a.deposit_date" ;


		System.out.printf("SQL #1: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalUnidentified(modelMain, modelTotal);			
		}		

		else {
			modelDep_unidentified_total = new modelUnidentified_deposits();
			modelDep_unidentified_total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), null, null});

			tblDep_unidentified_total = new _JTableTotal(modelDep_unidentified_total, tblDep_unidentified);
			tblDep_unidentified_total.setFont(dialog11Bold);
			scrollDep_unidentified_total.setViewportView(tblDep_unidentified_total);
			((_JTableTotal) tblDep_unidentified_total).setTotalLabel(0);
		}

		tblDep_unidentified.packAll();
		tblDep_unidentified.getColumnModel().getColumn(1).setPreferredWidth(120);

		adjustRowHeight_account(tblDep_unidentified);

	}	

	public void creatUnidentifiedtable(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" +  
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" + 
			"select '', 0.00, '', '', ''  union all  \r\n" +  
			"select '', 0.00, '', '', ''  union all  \r\n" +  
			"select '', 0.00, '', '', ''   \r\n" ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalUnidentified(modelMain, modelTotal);			
		}		


		else {
			modelDep_unidentified_total = new modelUnidentified_deposits();
			modelDep_unidentified_total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), "", "", ""});

			tblDep_unidentified_total = new _JTableTotal(modelDep_unidentified_total, tblDep_unidentified);
			tblDep_unidentified_total.setFont(dialog11Bold);
			scrollDep_unidentified_total.setViewportView(tblDep_unidentified_total);
			((_JTableTotal) tblDep_unidentified_total).setTotalLabel(0);
		}

		tblDep_unidentified.packAll();
		tblDep_unidentified.getColumnModel().getColumn(1).setPreferredWidth(120);

		adjustRowHeight_account(tblDep_unidentified);
	}	

	private void setIdentified_Dep_details(String req_no){//used

		Object[] dep_dtl = getDepositorDetails(req_no);	

		String pbl_desc = "";
		String pay_part = "";
		String pay_desc = "";
		String project  = "";
		String client   = "";

		try { seq_no = (String) dep_dtl[1].toString(); } catch (NullPointerException e) { seq_no = ""; }
		try { pbl_id = (String) dep_dtl[0].toString(); } catch (NullPointerException e) { pbl_id = ""; }
		try { entity_id = (String) dep_dtl[2].toString(); } catch (NullPointerException e) { entity_id = ""; }		
		try { pbl_desc = (String) dep_dtl[3]; } catch (NullPointerException e) { pbl_desc = ""; }		
		try { pay_part = (String) dep_dtl[6]; } catch (NullPointerException e) { pay_part = ""; }
		try { pay_desc = (String) dep_dtl[7]; } catch (NullPointerException e) { pay_desc = ""; }
		try { project = (String) dep_dtl[4]; } catch (NullPointerException e) { project = ""; }
		try { client = (String) dep_dtl[5]; } catch (NullPointerException e) { client = ""; }

		lookupIden_PBL.setText(pbl_id + " | "+seq_no);	
		txtIden_PBL.setText(pbl_desc);	
		lookupIden_payment.setText(pay_part);	
		txtPayment.setText(pay_desc);	
		txtProject.setText(project);	
		txtClientName.setText(entity_id+" | "+client);	
	}

	public void displayForApproval(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "select *, (SELECT proj_id from mf_unit_info where pbl_id = a.pbl_id), \n"+
			"(SELECT unit_id from mf_unit_info where pbl_id = a.pbl_id) \n" + 
			"from view_ud_for_approval a \n" + 
			"where co_id = '"+lookupCompany.getValue()+"' and status_id = 'F'; " ;

		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display For Approval", sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalApprove(modelMain, modelTotal);			
		}		

		else {
			modelApproval_total = new modelIdentifiedDep_approval();
			modelApproval_total.addRow(new Object[] { "Total",null, null, null, new BigDecimal(0.00)});

			tblApproval_total = new _JTableTotal(modelApproval_total, tblApproval);
			tblApproval_total.setFont(dialog11Bold);
			scrollApproval_total.setViewportView(tblApproval_total);
			((_JTableTotal) tblApproval_total).setTotalLabel(0);
		}

		tblApproval.packAll();
		adjustRowHeight_account(tblApproval);

	}


	//Enable/Disable all components inside JPanel	
	public void enable_fields(Boolean x){//

		lblIden_PBL.setEnabled(x);	
		lookupIden_PBL.setEnabled(x);	
		txtIden_PBL.setEnabled(x);

		lblIden_payment.setEnabled(x);	
		lookupIden_payment.setEnabled(x);	
		txtPayment.setEnabled(x);

		lblProject.setEnabled(x);	
		txtProject.setEnabled(x);	

		lblClientName.setEnabled(x);	
		txtClientName.setEnabled(x);

		lblSalesDivGrp.setEnabled(x);	
		txtSalesDivGrp.setEnabled(x);	

	}

	public void refresh_fields_unidentified(){//ok

		lblUniden_dep_no.setEnabled(true);	
		lookupUnidenDepNo.setEnabled(true);		
		lookupUnidenDepNo.setText("");

		{ enableButtons_uniden(true, false,  false,  true,  true ); } 	
	}

	public void refresh_fields_identified(){//ok

		lookupIdentified_Depno.setValue("");	
		txtIden_status.setText("");
		txtIden_ReceiptNo.setText("");
		txtIden_bankNo.setText("");
		txtIden_bankNo.setText("");
		txtIden_DepAmt.setText("0.00");
		txtIden_DepDate.setText("");
		txtIden_TagDate.setText("");
		txtIden_BankAcct.setText("");
		txtIden_BankName.setText("");

		lookupIden_PBL.setValue("");
		lookupIden_payment.setValue("");
		txtIden_PBL.setText("");
		txtPayment.setText("");
		txtProject.setText("");
		txtClientName.setText("");		

	}

	public void refresh_tablesMain(){//ok

		//reset table 1
		FncTables.clearTable(modelDep_unidentified);
		FncTables.clearTable(modelDep_unidentified_total);			
		rowHeaderDep_unidentified = tblDep_unidentified.getRowHeader22();
		scrollUniden.setRowHeaderView(rowHeaderDep_unidentified);	
		modelDep_unidentified_total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), null, null});

	}

	public void enableButtons_uniden( Boolean a, Boolean b, Boolean c, Boolean d, Boolean e ){  //ok

		btnAddNew.setEnabled(a);
		btnEditUniden.setEnabled(b);		
		btnSave.setEnabled(c);		
		btnPreview.setEnabled(d);
		btnCancel.setEnabled(e);
		//btnUniden_display.setEnabled(g);
		//btnUniden_displayTagged.setEnabled(h);

	}

	public void enableButtons_iden( Boolean a, Boolean b, Boolean c, Boolean d, Boolean e ){  //ok

		btnTagDetails.setEnabled(a);
		btnEditIden.setEnabled(b);
		btnSaveIden.setEnabled(c);
		btnPreviewList.setEnabled(d);
		btnCancel_2.setEnabled(e);
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		//tab 1
		lblUniden_dep_no.setEnabled(true);	
		lookupUnidenDepNo.setEnabled(true);	
		{ enableButtons_uniden(true, false,  false,  true,  false ); } 
		lookupUnidenDepNo.setLookupSQL(getDepositNo());	

		//tab 2
		lblSearchBy.setEnabled(true);
		rbtnForIdentification.setEnabled(true);
		rbtnForIdentification.setEnabled(true);
		rbtnAllDeposits.setEnabled(true);
		lblIden_DepNo.setEnabled(true);	
		lookupIdentified_Depno.setEnabled(true);	
		lookupIdentified_Depno.setLookupSQL(getActiveDepositNo());	
		{ enableButtons_iden(false, false,  false, true,  false ); } 

		displayForApproval(modelApproval, rowHeaderApproval, modelApproval_total);
		btnApprove.setEnabled(true);
		btnRefreshForApproval.setEnabled(true);
		btnPreviewApproved.setEnabled(true);
		modelApproval.setEditable(true);
		
		lookupCompany.setValue(co_id);
	}
	
	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//ok
		
		String actionCommand = e.getActionCommand();

		if(e.getActionCommand().equals("Cancel")) { cancel(); }  //ok

		if(e.getActionCommand().equals("Cancel_2")) { cancel_2(); }  //ok

		if (e.getActionCommand().equals("tag_detail")) {tag_details(); }  //ok

		if (e.getActionCommand().equals("Save_2")) { saveIdentifiedDeposit(); }	//ok
		
		if(actionCommand.equals("Issue Receipt DD")) {
			if(isBatchIssued(lookupDirectDepositBatch.getValue()) == false) {
				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to issue receipts for this batch?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					
					IssueReceiptDD();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Receipts has been issued to this batch", "Issue", JOptionPane.PLAIN_MESSAGE);
					btnIssueReceiptDD.setEnabled(false);
					displayDDForIssuance(modelDDIssuance, rowHeaderDDIssuance, lookupCompany.getValue(), lookupDirectDepositBatch.getValue());
					previewReceiptBatch();
					//put display of receipts to be printed here
				}
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "This batch is already issued", "Issue", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		if(actionCommand.equals("Preview DD Batch")){
			previewDirectDepositBatch(lookupDirectDepositBatch.getValue());
			previewReceiptBatch();
		}
		
		if(actionCommand.equals("Cancel Batch")) {
			FncTables.clearTable(modelDDIssuance);
			lookupDirectDepositBatch.setValue(null);
			btnIssueReceiptDD.setEnabled(false);
			btnPreviewBatch.setEnabled(false);
			btnCancelBatch.setEnabled(false);
		}

		//if(e.getActionCommand().equals("Preview") && !txtUniden_status.getText().equals("ACTIVE") ){selectPreviewDate();}		

		/*if(e.getActionCommand().equals("Display_unidentified"))
		{	
			displayAllUnidentifiedDep_details(modelDep_unidentified, rowHeaderDep_unidentified, modelDep_unidentified_total, "A" );							
			lookupUnidenDepNo.setValue("");
			txtUniden_status.setText("ACTIVE");
			{ 
				enableButtons_uniden(true, false,  true,  false,  true, false, false, false ); 
				mnidelete.setEnabled(false);
				mniaddrow.setEnabled(false);
				mnieditrow.setEnabled(true);
			} 		
		} //ok

		if(e.getActionCommand().equals("Display_unidentifiedTagged"))
		{	
			displayAllUnidentifiedDep_details(modelDep_unidentified, rowHeaderDep_unidentified, modelDep_unidentified_total, "F" );							
			lookupUnidenDepNo.setValue("");
			txtUniden_status.setText("TAGGED");
			{ 
				enableButtons_uniden(true, false,  true,  false,  true, false, false, false ); 
				mnidelete.setEnabled(false);
				mniaddrow.setEnabled(false);
				mnieditrow.setEnabled(false);
			} 		
		} //ok
		 */	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2)) {
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

	private void addnew(){//ok

		lblUniden_dep_no.setEnabled(false);	
		lookupUnidenDepNo.setEnabled(false);	
		//btnUniden_display.setEnabled(false);		

		lblUniden_status.setEnabled(true);	
		txtUniden_status.setEnabled(true);	
		txtUniden_status.setText("ACTIVE");	

		creatUnidentifiedtable(modelDep_unidentified, rowHeaderDep_unidentified, modelDep_unidentified_total );	
		tblDep_unidentified.setEditable(true);
		modelDep_unidentified.setEditable(true);

		to_do = "addnew_unidentified";

		{ enableButtons_uniden(false, false,  true,  true,  true ); } 		
		tblDep_unidentified.hideColumns("Created By","Created Date","Tagged By","Tagged Date","Record No.", "Edited By", "Edited Date");	


	}	

	private void editUnidentified(){//ok

		lblUniden_dep_no.setEnabled(false);	
		lookupUnidenDepNo.setEnabled(false);	
		tblDep_unidentified.setEditable(true);
		modelDep_unidentified.setEditable(true);

		{ enableButtons_uniden(false, false,  true,  false,  true ); } 		

	}	

	private void cancel(){//ok

		if(!btnSave.isEnabled()||checkBlankTable()==true)  {

			refresh_tablesMain();
			refresh_fields_unidentified();
			{ enableButtons_uniden(true, false,  false,  true,  false ); } 

			lookupUnidenDepNo.setValue("");
			tabDeposit.setSelectedIndex(0);	

			lblUniden_status.setEnabled(false);	
			txtUniden_status.setEnabled(false);	
			txtUniden_status.setText("");	

			tblDep_unidentified.setEditable(false);
			modelDep_unidentified.setEditable(false);
			lblReceiptNo.setText("Receipt No :");
			btnIssueOR.setEnabled(false);
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				refresh_tablesMain();
				refresh_fields_unidentified();

				lookupUnidenDepNo.setValue("");
				tabDeposit.setSelectedIndex(0);	

				lblUniden_status.setEnabled(false);	
				txtUniden_status.setEnabled(false);	
				txtUniden_status.setText("");	

				tblDep_unidentified.setEditable(false);
				modelDep_unidentified.setEditable(false);
				lblReceiptNo.setText("Receipt No :");
				btnIssueOR.setEnabled(false);

			} 	else {}			
		}
	}

	private void cancel_2(){//ok

		if(!btnSaveIden.isEnabled())  {

			//refresh_tablesMain();
			refresh_fields_identified();
			enable_fields(false);
			{ enableButtons_iden(false, false,  false, true,  false ); } 

			lblSearchBy.setEnabled(true);
			rbtnForIdentification.setEnabled(true);
			rbtnForIdentification.setEnabled(true);
			rbtnAllDeposits.setEnabled(true);
			lblIden_DepNo.setEnabled(true);	
			lookupIdentified_Depno.setEnabled(true);	
			lookupIdentified_Depno.setLookupSQL(getActiveDepositNo());	
		} 
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				refresh_fields_identified();
				enable_fields(false);
				lblSearchBy.setEnabled(true);
				rbtnForIdentification.setEnabled(true);
				rbtnForIdentification.setEnabled(true);
				rbtnAllDeposits.setEnabled(true);
				lblIden_DepNo.setEnabled(true);	
				lookupIdentified_Depno.setEnabled(true);
				lookupIdentified_Depno.setLookupSQL(getActiveDepositNo());	
				{ enableButtons_iden(false, false,  false, true,  false ); } 

			} 	else {}			
		}
	}

	private void tag_details(){//ok		

		enable_fields(true);	
		lookupIden_PBL.setLookupSQL(getPBL());
		lookupIden_payment.setLookupSQL(getPayment());

		{ enableButtons_iden(false, false,  true, false,  false ); } 

		modelDep_unidentified.setEditable(true);
	}

	private void saveUnidentifiedDeposit(){//ok

		if(checkBlankTable()==true)
		{JOptionPane.showMessageDialog(getContentPane(), "No details to save.", "Information", 
				JOptionPane.INFORMATION_MESSAGE);}		

		else {	

			if(checkDepositDate()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please enter deposit date", "Information", 
					JOptionPane.INFORMATION_MESSAGE);}

			else {	

				if(checkDetails_ifcomplete()==false)
				{JOptionPane.showMessageDialog(getContentPane(), "Please enter a missing account ID.", "Information", 
						JOptionPane.INFORMATION_MESSAGE);}

				else {	

					if(checkDepositAmount()==false)
					{JOptionPane.showMessageDialog(getContentPane(), "Please enter deposit amount", "Information", 
							JOptionPane.INFORMATION_MESSAGE);}

					else {						

						if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							pgUpdate db = new pgUpdate();	
							insertUnidentified_Deposit(db);
							db.commit();						

							refresh_fields_unidentified();
							refresh_tablesMain();
							txtUniden_status.setText("");
							JOptionPane.showMessageDialog(getContentPane(),"New unidentified deposit(s) saved.","Information",JOptionPane.INFORMATION_MESSAGE);	
							tblDep_unidentified.showColumns("Created By","Created Date","Tagged By","Tagged Date","Record No.", "Edited By", "Edited Date");	

						}  else {}

					}
				}
			}
		}
	}

	private void saveIdentifiedDeposit(){//ok

		if(checkIdentifiedDetails_ifcomplete()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete depositor details.", "Information", 
				JOptionPane.INFORMATION_MESSAGE);}

		else {	

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String deposit_no = lookupIdentified_Depno.getText().trim();

				pgUpdate db = new pgUpdate();	
				updateIdentified_Deposit(db, deposit_no);					
				db.commit();						
				JOptionPane.showMessageDialog(getContentPane(),"Unidentified deposit details saved.","Information",JOptionPane.INFORMATION_MESSAGE);	
				txtIden_status.setText("TAGGED");
				{ enableButtons_iden(false, true, false,  false,  true ); } 
				enable_fields(false);

			}  else {}

		}
	}

	private void previewUnidentified(){//

		String criteria = "List of Unidentified Deposits";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date", Calendar.getInstance().getTime());

		FncReport.generateReport("/Reports/rptUnidentified_deposits.jasper", reportTitle, company, mapParameters);
	}

	private void previewIdentified(){//

		String criteria = "List of Identified Deposits";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date", Calendar.getInstance().getTime());
		mapParameters.put("date_from", dteRefDateFr.getDate());
		mapParameters.put("date_to", dteRefDateTo.getDate());

		FncReport.generateReport("/Reports/rptIdentified_deposits.jasper", reportTitle, company, mapParameters);
	}

	private void previewApproved(){//

		String criteria = "List of Approved Deposits";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date", Calendar.getInstance().getTime());
		mapParameters.put("date_from", dteRefDateFr.getDate());
		mapParameters.put("date_to", dteRefDateTo.getDate());

		FncReport.generateReport("/Reports/rptApproved_deposits.jasper", reportTitle, company, mapParameters);
	}

	private void updateUnidentified(){

		if(checkDepositDate()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter deposit date", "Information", 
				JOptionPane.INFORMATION_MESSAGE);}

		else {	

			if(checkDetails_ifcomplete()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please enter a missing account ID.", "Information", 
					JOptionPane.INFORMATION_MESSAGE);}

			else {	

				if(checkDepositAmount()==false)
				{JOptionPane.showMessageDialog(getContentPane(), "Please enter deposit amount", "Information", 
						JOptionPane.INFORMATION_MESSAGE);}

				else {	

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to update the deposit details?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						String deposit_no = lookupIdentified_Depno.getText().trim();

						pgUpdate db = new pgUpdate();	
						updateUnidentified_Deposit(db, deposit_no);					
						db.commit();						
						JOptionPane.showMessageDialog(getContentPane(),"Unidentified deposit details updated.","Information",JOptionPane.INFORMATION_MESSAGE);				
						{ enableButtons_iden(false, true, false,  false,  true ); } 
						enable_fields(false);
						displayUnidentifiedDep_details(modelDep_unidentified, rowHeaderDep_unidentified, modelDep_unidentified_total, Integer.parseInt(lookupUnidenDepNo.getText()) );								


					}  

					else {}

				}
			}
		}
	}



	//select, lookup and get statements	
	public String getPBL(){
		return "select \n" + 
		"a.pbl_id as \"PBL ID\", \n" + 
		"b.description  as \"Description\", \n" + 
		"trim(c.proj_name) as \"Project\", \n" + 
		"trim(d.entity_name) as \"Client\", \n" + 
		"a.seq_no as \"Seq. No.\", \n" + 
		"a.entity_id as \"Entity ID\",\n" + 
		"e.dept_name as \"Dept\"  \n" + 
		"from rf_sold_unit a \n" + 
		"left join mf_unit_info b on a.projcode = b.proj_id and a.pbl_id = b.pbl_id \n" + 
		"left join mf_project c on b.proj_id = c.proj_id\n" + 
		"left join rf_entity d on a.entity_id = d.entity_id \n" + 
		"left join mf_department e on a.mktgarm = e.dept_code \n" + 
		"where c.co_id = '"+lookupCompany.getValue()+"' and a.currentstatus is not null"; 		
	}

	public String getPayment(){//ok
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(pay_part_id) as \"Part ID\",\r\n" + 
		"trim(particulars) as \"Particular\", \r\n" + 
		"trim(partdesc) as \"Description\" \r\n" + 		
		"from mf_pay_particular\r\n"   +
		"where pay_part_id IN ('197', '223', '224', '268', '033') \r\n"+
		"order by particulars " ;
	}

	public String getPayment2(){//ok
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.part_id as \"Part ID\",\r\n" + 
		"a.part_desc  as \"Description\", " +
		"a.part_abbv as \"Part Abbrev.\" " +

		"\r\n" + 
		"from mf_client_ledger_part a\r\n"   ;		
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

	public String getDepositNo(){//ok

		String sql = 

			"select " +
			"a.record_id as \"Deposit No.\", " +
			"to_char(a.deposit_date,'MM/dd/yy') as \"Deposit Date\" , " +
			"a.bank_acct_id as \"Bank Acct ID\" , " +
			"d.bank_alias as \"Bank Alias\"  , " +
			"a.bank_account as \"Bank Acct.\" , " +
			"a.amount as \"Amount\" , " +
			"e.company_alias as \"Company\", " +
			"( case when a.status_id = 'A' then 'ACTIVE'  else" +
			"  case when a.status_id = 'F' then 'TAGGED' else" +
			"  case when a.status_id = 'P' then 'POSTED' else" +
			"  case when a.status_id = 'I' then 'INACTIVE' else" +
			"  case when a.status_id = 'X' then 'DELETED' else '' end end end end end) as \"Status\",  " +
			"to_char(a.tagged_date,'MM/dd/yy') as \"Tagged Date\",  " +
			"d.bank_name as \"Bank Name\"," +
			"trim(a.receipt_no) as \"Receipt No.\" " +

			"from tf_unidentified_dep a " +
			"left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id  \r\n" + 
			"left join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id\r\n" + 
			"left join mf_bank d on c.bank_id = d.bank_id  " +
			"left join mf_company e on a.co_id = e.co_id " +
			"where a.co_id = '"+lookupCompany.getValue()+"' " +
			"order by a.record_id ";

		return sql;

	}	

	public String getBankAcct(){//ok

		return 			
		"select \r\n" + 

		"a.bank_acct_id as \"Bank Acct. ID\"  ,\r\n" + 
		"a.bank_acct_no as \"Bank Acct. No.\" ,\r\n" + 
		"d.bank_alias  as \"Bank Alias\"  ,\r\n" + 
		"( case when a.fund_class_id = '01' then 'COLLECTING ACCOUNT' else \r\n" + 
		"	case when a.fund_class_id = '02' then 'DISBURSING ACCOUNT' else\r\n" + 
		"		case when a.fund_class_id = '03' then 'MONEY MARKET PLACEMENT ACCOUNT' end end end) as \"Account Type\"," +
		"a.acct_id as \"Acct ID\"  \r\n" + 

		"from mf_bank_account a\r\n" + 
		"left join mf_bank_branch c on a.bank_branch_id = c.bank_branch_id\r\n" + 
		"left join mf_bank d on c.bank_id = d.bank_id  " +		
		"order by a.bank_acct_id \r\n" ;		

	}

	public Integer sql_getDepNo_line_count() {
		Integer x = 1;
		String SQL = "select max(record_id)+1 from tf_unidentified_dep";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){

			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {x = 1;}
			else {x = (Integer) db.getResult()[0][0]; }

		}else{
			x = 1;
		}

		return x;
	}

	private Object [] getDepositorDetails(String req_no) {//used

		String strSQL = 
			"--- Display Depositor Details \r\n" + 
			"\r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"trim(a.pbl_id), " +		//0
			"a.seq_no, " +				//1
			"a.entity_id, " +			//2
			"trim(c.description), " +	//3	
			"trim(d.proj_name), " +		//4
			"trim(e.entity_name), " +	//5
			"trim(a.payment_part), " +	//6
			"trim(f.partdesc) " +		//7

			"" + 
			"from tf_unidentified_dep a\r\n" + 
			"left join rf_sold_unit b on a.pbl_id = b.pbl_id and a.seq_no = b.seq_no " +
			"left join mf_unit_info c on a.pbl_id = c.pbl_id\r\n" + 
			"left join mf_project d on c.proj_id = d.proj_id " +
			"left join rf_entity e on a.entity_id = e.entity_id " +	
			"left join mf_pay_particular f on a.payment_part = f.pay_part_id " +		

			"" + 
			"where a.record_id = "+req_no+" and a.co_id = '"+co_id+"'\r\n" + 
			""  ;


		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	public String sql_getBankAcctNo(String rec) {//ok	

		String bank_no = "";

		String SQL = 
			"select bank_acct_no from mf_bank_account where bank_acct_id = '"+rec+"' \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {bank_no = "";}
			else {bank_no = db.getResult()[0][0].toString(); }	

		}else{
			bank_no = "";
		}

		return bank_no;
	}

	public String sql_getDepositStatus(String rec) {//ok	

		String bank_no = "";

		String SQL = 
			"select " +
			"(case when status_id = 'A' then 'ACTIVE' else" +
			"(case when status_id = 'I' then 'INACTIVE' else 'TAGGED' end) end ) " +
			"from tf_unidentified_dep " +
			"where record_id = '"+rec+"' \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {bank_no = "";}
			else {bank_no = db.getResult()[0][0].toString(); }	

		}else{
			bank_no = "";
		}

		return bank_no;
	}


	//check and validate	
	private Boolean checkBlankTable(){//ok

		boolean x = true;	

		int rw = tblDep_unidentified.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	

			String dep_date	= "";
			if (modelDep_unidentified.getValueAt(w,0)==null)  {} 
			else {dep_date= modelDep_unidentified.getValueAt(w,0).toString().trim();}
			Double dep_amt = Double.parseDouble(modelDep_unidentified.getValueAt(w,1).toString());	
			String acct_id = modelDep_unidentified.getValueAt(w,2).toString().trim();
			if (dep_amt>0||!acct_id.equals("")||!dep_date.equals("")) {x=false; break;} 
			else {}		
			w++;
		}

		return x;

	}	

	private Boolean checkDetails_ifcomplete(){//ok

		boolean x = true;	

		int rw = tblDep_unidentified.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	
			Double dep_amt 	= Double.parseDouble(modelDep_unidentified.getValueAt(w,1).toString());	

			if (dep_amt>0) {

				String acct_id = modelDep_unidentified.getValueAt(w,2).toString().trim();
				if(acct_id.equals("")){x=false; break;} else {}

			} else {}		
			w++;
		}
		return x;

	}	

	private Boolean checkIdentifiedDetails_ifcomplete(){//ok

		boolean x = true;		

		String pbl_no  = lookupIden_PBL.getText().trim();
		String payment = lookupIden_payment.getText().trim();

		if (pbl_no.equals("")||payment.equals("")){ x=false; }

		return x;

	}	

	private Boolean checkDepositAmount(){//ok

		boolean x = true;		

		int rw = tblDep_unidentified.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	
			String acct_id = modelDep_unidentified.getValueAt(w,2).toString().trim();			

			if (!acct_id.equals("")) {

				Double dep_amt 	= Double.parseDouble(modelDep_unidentified.getValueAt(w,1).toString());	
				if(dep_amt==0.00){x=false; break;} else {}

			} else {}		
			w++;
		}
		return x;

	}	

	private Boolean checkDepositDate(){//ok

		boolean x = true;		

		int rw = tblDep_unidentified.getModel().getRowCount();	
		int w = 0;

		while (w < rw) {	

			Double dep_amt 	= Double.parseDouble(modelDep_unidentified.getValueAt(w,1).toString());	

			if (dep_amt>0) {

				String dep_date	= "";
				if (modelDep_unidentified.getValueAt(w,0)==null)  {} 
				else {dep_date= modelDep_unidentified.getValueAt(w,0).toString().trim();}

				if(dep_date.equals("")){x=false; break;} else {}

			} else {}		
			w++;
		}
		return x;

	}


	//table operations
	public void totalUnidentified(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal unidentified_amt 		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { unidentified_amt 	= unidentified_amt.add(((BigDecimal) modelMain.getValueAt(x,1)));} 
			catch (NullPointerException e) { unidentified_amt 	= unidentified_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", unidentified_amt, null, null });		
	}

	public void totalApprove(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal approve_amt 		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { approve_amt 	= approve_amt.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { approve_amt 	= approve_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null,null,null,null,approve_amt });		
	}

	private void computeUnidentifiedAmount(){//ok

		int rw = tblDep_unidentified.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			Double unident_amt	= Double.parseDouble(modelDep_unidentified.getValueAt(x,1).toString());	

			BigDecimal uniden = new BigDecimal(unident_amt);	

			modelDep_unidentified.setValueAt(uniden, x, 1);
			x++;
		}
		totalUnidentified(modelDep_unidentified, modelDep_unidentified_total);
	}

	private void clickTableColumn_account() {//ok
		int column = tblDep_unidentified.getSelectedColumn();
		int row = tblDep_unidentified.getSelectedRow();				

		Integer x[] = {0,1,2,3};
		String sql[] = {"","",getBankAcct(),""};	
		String lookup_name[] = {"","","Department",""};		

		if (column == 0) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddDate, "Deposit Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

			if ( scanOption == JOptionPane.CLOSED_OPTION ) {
				try {						
					modelDep_unidentified.setValueAt(dteRefDate.getDate(), tblDep_unidentified.getSelectedRow(), 0);
				} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
			} // CLOSED_OPTION

		}

		if (column == x[column] && modelDep_unidentified.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 2)  {				
				modelDep_unidentified.setValueAt(data[0], row, column);	
				modelDep_unidentified.setValueAt(data[1], row, column+1);	
				modelDep_unidentified.setValueAt(data[2], row, column+2);	
			}
		}		

		else {}

		tblDep_unidentified.packAll();	
		tblDep_unidentified.getColumnModel().getColumn(1).setPreferredWidth(120);
	}

	private void adjustRowHeight_account(_JTableMain table){//ok
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}


	//save and insert	
	private void insertUnidentified_Deposit(pgUpdate db){//ok

		int dep_no = sql_getDepNo_line_count();
		int x  = 0;	
		int rw = tblDep_unidentified.getModel().getRowCount();	

		while(x < rw ) {	

			Double dep_amt 		= Double.parseDouble(modelDep_unidentified.getValueAt(x,1).toString());				

			if (dep_amt == 0.00) {}
			else
			{					
				String dep_date	= "";
				if (modelDep_unidentified.getValueAt(x,0)==null)  {} else {dep_date= modelDep_unidentified.getValueAt(x,0).toString().trim();}
				String bank_id 		= modelDep_unidentified.getValueAt(x,2).toString();
				String bank_acct 	= modelDep_unidentified.getValueAt(x,3).toString();		

				String sqlDetail = 
					"INSERT into tf_unidentified_dep values (" +
					""+dep_no+",  \n" +  		//1
					"'"+dep_date+"',  \n" +  	//2
					"'"+bank_id+"',  \n" +  	//3	
					"'"+bank_acct+"',  \n" +  	//4
					""+dep_amt+",  \n" +  		//5
					"'A',  \n" +  				//6
					"'',  \n" +  				//6	
					"null,  \n" +  				//7
					"'',  \n" +  				//8
					"'"+UserInfo.EmployeeCode+"',  \n" +  	//9
					"'"+Calendar.getInstance().getTime()+"',  \n" +  //10
					"'',  \n" +  				//11
					"null,  \n" +  				//12
					"'',  \n" +  				//13
					"'"+co_id+"',  \n" +  		//14
					"''  \n" +  				//15				

					")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);	
				dep_no++;
			}	

			x++;
		}
	}

	private void updateUnidentified_Deposit(pgUpdate db, String rec_no) {//ok		

		String dep_date	= "";
		if (modelDep_unidentified.getValueAt(0,0)==null)  {} else {dep_date= modelDep_unidentified.getValueAt(0,0).toString().trim();}
		Double amount 		= Double.parseDouble(modelDep_unidentified.getValueAt(0,1).toString());
		String bank_id 		= modelDep_unidentified.getValueAt(0,2).toString();
		String bank_acct 	= modelDep_unidentified.getValueAt(0,3).toString();	
		String dep_no   	= lookupUnidenDepNo.getText().trim();

		String sqlDetail = 
			"update tf_unidentified_dep set " +
			"deposit_date = '"+dep_date+"', " +
			"bank_acct_id = '"+bank_id+"', " +
			"bank_account = '"+bank_acct+"', " +
			"amount = "+amount+", " +
			"edited_by = '"+UserInfo.EmployeeCode+"', " +
			"edited_date = '"+Calendar.getInstance().getTime()+"' " +
			"where record_id = "+dep_no+" " +
			"and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void updateIdentified_Deposit(pgUpdate db, String rec_no) {//ok

		String sqlDetail = 
			"update tf_unidentified_dep set " +
			"pbl_id = '"+pbl_id+"', " +
			"seq_no = '"+seq_no+"', " +
			"entity_id = '"+entity_id+"', " +			
			"payment_part = '"+lookupIden_payment.getText().trim()+"',  " +
			"status_id = 'F'," +
			"identified_by = '"+UserInfo.EmployeeCode+"', " +
			"identified_date = '"+dateFormat.format(Calendar.getInstance().getTime())+"' " +
			"where record_id = "+rec_no+" and co_id = '"+co_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void approveDeposits(){
		if (nothingtoProcess()==false) {
			execute_approve();
		} else if (nothingtoProcess()==true) {						
			JOptionPane.showMessageDialog(getContentPane(),"Please select item(s) to approve.","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void execute_approve(){

		if (JOptionPane.showConfirmDialog(getContentPane(), 
				"Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			int x=0; 

			pgUpdate db = new pgUpdate();
			String sequence = "";
			pgSelect dbExec = new pgSelect(); 
			String batch_no = FncGlobal.GetString("(select trim(to_char((max(COALESCE(batch_no::int, 0)) + 1), '0000000000')) from rf_direct_deposit_for_issuance)"); //QUERY BATCH NUMBER HERE
			
			while ( x < modelApproval.getRowCount()) {
				Boolean isTrue = false;
				if (tblApproval.getValueAt(x,0) instanceof String) {
					isTrue = new Boolean((String)tblApproval.getValueAt(x,0));
				}
				
				if (tblApproval.getValueAt(x,0) instanceof Boolean) {
					isTrue = (Boolean)tblApproval.getValueAt(x,0);
				}

				if (isTrue){	
					String record_id = tblApproval.getValueAt(x,13).toString().trim();
					String entity_id = (String) modelApproval.getValueAt(x, 14);
					String pbl_id = (String) modelApproval.getValueAt(x, 15);
					Integer seq_no = (Integer) modelApproval.getValueAt(x, 16);
					String proj_id = (String) modelApproval.getValueAt(x, 18);
					String unit_id = (String) modelApproval.getValueAt(x, 19);
					BigDecimal amount = (BigDecimal) modelApproval.getValueAt(x, 6);
					String account_no = (String) modelApproval.getValueAt(x, 8);
					String pay_part_id = (String) modelApproval.getValueAt(x, 5);
					
					String sqlDetail = "update tf_unidentified_dep \n" +
						"set status_id = 'P', " +
						"approved_date = '"+dateFormat.format(Calendar.getInstance().getTime())+"' " +
						"where record_id = '"+record_id+"' and co_id = '"+lookupCompany.getValue()+"'" ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);
					
					if(UserInfo.EmployeeCode.equals("900876")) {
						//create batch number here
						sequence = FncGlobal.GetString("select sp_create_pay_header('"+UserInfo.branch_id+"', \n" +
								"'"+entity_id+"','"+proj_id+"','"+unit_id+"',"+seq_no+", "+amount+", \n" +
								"'"+UserInfo.EmployeeCode+"', "+record_id+")");
						
						dbExec = new pgSelect(); 
						dbExec.select("select sp_create_pay_detail_v2('"+sequence+"', '"+entity_id+"', '"+pay_part_id+"', \n" + 
								"'"+account_no+"', "+amount+", '', "+record_id+", '"+batch_no+"', \n " +
								"'"+UserInfo.EmployeeCode+"'); ");
					}
				}

				x++;
			}
			db.commit();
			
			db = new pgUpdate();
			db.executeUpdate("update tf_unidentified_dep \n" + 
					"set status_id = 'A', approved_date = null \n" + 
					"where coalesce(entity_id, '') = '' and status_id = 'P'", false);
			db.commit();
			
			journalize_dd(dateFormat.format(Calendar.getInstance().getTime()));
			previewDirectDepositBatch(batch_no);
			JOptionPane.showMessageDialog(getContentPane(),"Deposits successfully approved and posted.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayForApproval(modelApproval, rowHeaderApproval, modelApproval_total);
		}

	}
	
	private void IssueReceiptDD() {
		
		for(int x= 0; x<modelDDIssuance.getRowCount(); x++) {
			pgSelect db = new pgSelect();
			String client_seqno = (String) modelDDIssuance.getValueAt(x, 0);
			String SQL = "SELECT sp_ir_post_ordered('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
			db.select(SQL);
		}
	}
	
	private boolean isBatchIssued(String batch_no) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_direct_deposit_for_issuance where batch_no = '"+batch_no+"' and status_id = 'P');";
		db.select(SQL);
		
		if(db.isNotNull()) {
			return (boolean) db.getResult()[0][0];
		}else {
			return true;
		}
	}
	
	private void previewDirectDepositBatch(String batch_no){//

		String criteria = "List of Approved Deposits";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", tagCompany.getText());
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date", Calendar.getInstance().getTime());
		mapParameters.put("batch_no", batch_no);

		FncReport.generateReport("/Reports/rptDirectDepositForIssuance_Batch.jasper", reportTitle, tagCompany.getText(), mapParameters);
		
	}
	
	private Boolean issueReceipt(){		
		
		String sqlDetail = 
			"select sp_issuereceipt_identified_dep('"+co_id+"','"+UserInfo.EmployeeCode+"','"+lookupUnidenDepNo.getText()+"')" ;

		FncSystem.out("SQL", sqlDetail);
		pgSelect db = new pgSelect();
		db.select(sqlDetail);
		
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];			
		}else{
			return false;
		}	
		
	}
	
	

	//right-click
	private void AddRow(){

		modelDep_unidentified.addRow(new Object[] { null, new BigDecimal(0.00), "", "", "" });		
		((DefaultListModel) rowHeaderDep_unidentified.getModel()).addElement(modelDep_unidentified.getRowCount());
		adjustRowHeight();
	}

	private void removeRow(){		

		int r  = tblDep_unidentified.getSelectedRow();

		if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblDep_unidentified.getModel()).removeRow(r);  
			modelDep_unidentified.addRow(new Object[] { null, new BigDecimal(0.00), "", "", "" });			
			adjustRowHeight();
		}

	}

	private void adjustRowHeight(){		

		int rw = tblDep_unidentified.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblDep_unidentified.setRowHeight(x, 22);			
			x++;
		}
	}

	public void EditDetails(){		

		int w = tblDep_unidentified.getSelectedRow();		

		Date dep_dte 	= (Date) tblDep_unidentified.getValueAt(w,0);
		String dep_amt	= tblDep_unidentified.getValueAt(w,1).toString().trim();
		String dep_acct	= tblDep_unidentified.getValueAt(w,2).toString().trim();	
		String rec_no	= tblDep_unidentified.getValueAt(w,5).toString().trim();	
		String status	= txtUniden_status.getText().trim();

		dteDeposit.setDate((Date) dep_dte);	
		txtAmount.setText(dep_amt);	
		lookupBankAcct.setText(dep_acct);
		txtRecNo.setText(rec_no);

		if (status.equals("ACTIVE")) 
		{
			cmbStatus.setSelectedIndex(0);	
		}
		else 
		{
			cmbStatus.setSelectedIndex(1);	
		}

		btnSaveDeposit.setEnabled(true);

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlEditDetails, "Edit Details",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
			try {			



			} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
		} // CLOSED_OPTION
	}

	public void selectPreviewDate(){		
		{

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Select Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

			if ( scanOption == JOptionPane.CLOSED_OPTION ) {
				try {						

					previewIdentified();

				} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}				
			} // CLOSED_OPTION

		}
	}

	private boolean nothingtoProcess() {		

		boolean nothingtoProcess = true;		

		for(int x=0; x < modelApproval.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelApproval.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelApproval.getValueAt(x,0));
			}
			if(modelApproval.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelApproval.getValueAt(x,0);
			}

			if(isTrue){		
				nothingtoProcess = false;	
				break;
			} else {
				
			}
		}
		return nothingtoProcess;

	}

	private void journalize_dd(String strDate) {
		pgSelect dbSel = new pgSelect();
		pgSelect dbSel1 = new pgSelect();
		pgUpdate dbExec = new pgUpdate(); 
		
		dbSel.select("select get_client_name(a.entity_id), concat('call sp_create_jv_direct_deposit(''', a.entity_id, ''', ''', a.pbl_id, ''', ', b.seq_no, ', ''', a.deposit_date, '''::date, ''', a.identified_by, '''); '), a.*\n" + 
				"from tf_unidentified_dep a\n" + 
				"inner join rf_sold_unit b on a.entity_id = b.entity_id and a.pbl_id = b.pbl_id\n" + 
				"where jv_no is null and a.approved_date::date = '"+strDate+"'::date\n" + 
				"order by deposit_date::date, a.entity_id");
		
		for (int x=0; x<dbSel.getRowCount(); x++) {
			System.out.println(dbSel.Result[x][1].toString());
			
			try {
				dbExec = new pgUpdate();
				dbExec.executeUpdate(dbSel.Result[x][1].toString(), false);
				dbExec.commit();
			} catch (Exception e) {
				
			}
		}
	}
	
	public String getActiveDepositNo(){

		String sql = "select a.record_id as \"Deposit No.\", " +
			"to_char(a.deposit_date,'MM/dd/yy') as \"Deposit Date\" , " +
			"a.bank_acct_id as \"Bank Acct ID\" , " +
			"d.bank_alias as \"Bank Alias\"  , " +
			"a.bank_account as \"Bank Acct.\" , " +
			"a.amount as \"Amount\" , " +
			"e.company_alias as \"Company\", " +
			"( case when a.status_id = 'A' then 'ACTIVE'  else" +
			"  case when a.status_id = 'F' then 'TAGGED' else" +
			"  case when a.status_id = 'I' then 'INACTIVE' else" +
			"  case when a.status_id = 'X' then 'DELETED' else '' end end end end) as \"Status\",  " +
			"to_char(a.tagged_date,'MM/dd/yy') as \"Tagged Date\"," +
			"d.bank_name as \"Bank Name\"," +
			"a.receipt_no as \"Receipt No.\" " +
			"from tf_unidentified_dep a " +
			"left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id  \r\n" + 
			"left join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id\r\n" + 
			"left join mf_bank d on c.bank_id = d.bank_id  " +
			"left join mf_company e on a.co_id = e.co_id " +
			"where a.status_id = 'A' and a.co_id = '"+lookupCompany.getValue()+"'" +
			"order by a.record_id ";

		return sql;

	}	


}
