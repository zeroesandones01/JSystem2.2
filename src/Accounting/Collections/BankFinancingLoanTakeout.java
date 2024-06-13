/**
 * 
 */
package Accounting.Collections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import tablemodel.modelBankFinLoanTakeout;
import tablemodel.modelBankFinLoanTakeout_tagged;
import Buyers.LoansManagement._SalesOfReceivables;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class BankFinancingLoanTakeout extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = 4710172250487804596L;

	private static String title = "Bank Financing Loan Takeout";
	static Dimension SIZE = new Dimension(650, 550);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTabbedPane tabSORCenter;

	private JPanel pnlSORQualifiedAccounts;
	private JPanel pnlQANorth;
	private JPanel pnlQANorthCenter;
	private JPanel pnlQANorthComponent;
	private JPanel pnlQACompany;
	private JPanel pnlQAProject;
	private JPanel pnlQAPhase;
	private JPanel pnlQABank;
	private JPanel pnlQACenter;
	private JPanel pnlQANorthLabel;
	private JLabel lblQACompany;
	private JLabel lblQAProject;
	private JLabel lblQAPhase;
	private JLabel lblQABank;
	private JPanel pnlSORSouth;	
	private JPanel pnlAddDate;
	
	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupQAPhase;
	private _JLookup lookupQABank;
	
	private _JXTextField txtCompany;	
	private _JXTextField txtProject;
	private _JXTextField txtQAPhase;
	private _JXTextField txtQABank;
	private _JDateChooser dteRefDate;
	private JLabel lblDateFr;
	
	private JButton btnQAQualify;
	private JButton btnSORPost;
	private JButton btnSORCancel;
	private JButton btnAddOK;	
	
	private JScrollPane scrollQualifiedAcct;
	
	private _JTableMain tblQualifiedAcct;
	private JList rowHeaderQualifiedAcct;
	private modelBankFinLoanTakeout modelSORQA;
	
	/*private JPanel pnlAANorthLabel;
	private JPanel pblAANorthComponent;*/
	
	private String co_id = "02";
	private String co_name = "CENQHOMES DEVELOPMENT CORPORATION";
	private String proj_id = "015";
	private String proj_name = "TERRAVERDE RESIDENCES";
	//private String ph_code = "";
	private String ph_no = "";
	//private String bank_no = "";
	private String bank_name = "";

	private JPanel pnlTaggedCenter;
	private JPanel pnlTaggedAccounts;
	private JScrollPane scrollTaggedAcct;
	private _JTableMain tblTaggedAcct;
	private modelBankFinLoanTakeout_tagged modelTaggedAcct;
	private JList rowHeaderTaggedAcct;

	public BankFinancingLoanTakeout() {
		super(title, true, true, true, true);
		initGUI();
	}

	public BankFinancingLoanTakeout(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public BankFinancingLoanTakeout(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(1017, 550));
		this.setBounds(0, 0, 1017, 550);

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		{
			tabSORCenter = new _JTabbedPane();
			pnlMain.add(tabSORCenter, BorderLayout.CENTER);
			tabSORCenter.setEnabled(true);			
			
			{
				pnlSORQualifiedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("Tag Loan Released Accounts", pnlSORQualifiedAccounts);
				pnlSORQualifiedAccounts.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				//pnlSORQualifiedAccounts.action(arg0, arg1)
				
				{
					pnlQANorth = new JPanel(new BorderLayout(3, 3));
					pnlSORQualifiedAccounts.add(pnlQANorth, BorderLayout.NORTH);
					{
						pnlQANorthCenter = new JPanel(new BorderLayout(3, 3));
						pnlQANorth.add(pnlQANorthCenter, BorderLayout.CENTER);
						{
							pnlQANorthLabel = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlQANorthCenter.add(pnlQANorthLabel, BorderLayout.WEST);
							{
								lblQACompany = new JLabel("Company");
								pnlQANorthLabel.add(lblQACompany);
							}
							{
								lblQAProject = new JLabel("Project");
								pnlQANorthLabel.add(lblQAProject);
							}
							{
								lblQAPhase = new JLabel("Phase");
								pnlQANorthLabel.add(lblQAPhase);
							}
							{
								lblQABank = new JLabel("Bank");
								pnlQANorthLabel.add(lblQABank);
							}							
						}
						{
							pnlQANorthComponent = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlQANorthCenter.add(pnlQANorthComponent, BorderLayout.CENTER);
							{
								pnlQACompany = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQACompany);
								{
									lookupCompany = new _JLookup(null, "Select Company", 0);
									pnlQACompany.add(lookupCompany, BorderLayout.WEST);
									lookupCompany.setPreferredSize(new Dimension(50, 0));
									lookupCompany.setLookupSQL(_SalesOfReceivables.sqlCompany());
									lookupCompany.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												co_id = (String) data[0];
												co_name = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Company", lookupCompany.getLookupSQL());
												txtCompany.setText(co_name);
												lookupProject.setLookupSQL(_SalesOfReceivables.sqlProject(co_id));
											
												lookupProject.setValue("");
												txtProject.setText("");
												lookupQAPhase.setValue("");
												txtQAPhase.setText("");
											}
										}
									});
								}
								{
									txtCompany = new _JXTextField();
									pnlQACompany.add(txtCompany, BorderLayout.CENTER);
								}
							}
							{
								pnlQAProject = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQAProject);
								{
									lookupProject = new _JLookup(null, "Select Project", 0, "Please select Company first");
									pnlQAProject.add(lookupProject, BorderLayout.WEST);
									lookupProject.setPreferredSize(new Dimension(50, 0));
									lookupProject.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												proj_id = (String) data[0];
												proj_name = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Project", lookupProject.getLookupSQL());
												txtProject.setText(proj_name);
												lookupQAPhase.setLookupSQL(_SalesOfReceivables.sqlPhase(proj_id));
												
												lookupQAPhase.setValue("");
												txtQAPhase.setText("");
											}
										}
									});

								}
								{
									txtProject = new _JXTextField();
									pnlQAProject.add(txtProject, BorderLayout.CENTER);
								}
							}
							{
								pnlQAPhase = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQAPhase);
								{
									lookupQAPhase = new _JLookup(null, "Select Phase", 0, "Please select project first");
									pnlQAPhase.add(lookupQAPhase, BorderLayout.WEST);
									lookupQAPhase.setPreferredSize(new Dimension(50, 0));
									lookupQAPhase.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												//ph_code = (String) data[0];
												ph_no = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Phase", lookupQAPhase.getLookupSQL());
												txtQAPhase.setText(ph_no);
											}
										}
									});
								}
								{
									txtQAPhase = new _JXTextField();
									pnlQAPhase.add(txtQAPhase, BorderLayout.CENTER);
								}
							}
							{
								pnlQABank = new JPanel(new BorderLayout(3, 3));
								pnlQANorthComponent.add(pnlQABank);
								{
									lookupQABank = new _JLookup(null, "Select Bank", 0);
									pnlQABank.add(lookupQABank, BorderLayout.WEST);
									lookupQABank.setPreferredSize(new Dimension(50, 0));
									lookupQABank.setLookupSQL(_SalesOfReceivables.sqlBank());
									lookupQABank.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();

											if(data != null){
												//bank_no = (String) data[0];
												bank_name = (String) data[1];
												
												FncSystem.out("Display sql for lookup of Bank", lookupQABank.getLookupSQL());
												txtQABank.setText(bank_name);
											}
										}
									});
								}
								{
									txtQABank = new _JXTextField();
									pnlQABank.add(txtQABank, BorderLayout.CENTER);
								}
							}
							
						}
					}
					{
						btnQAQualify = new JButton("Generate");
						pnlQANorth.add(btnQAQualify, BorderLayout.EAST);
						btnQAQualify.setActionCommand("Generate");
						btnQAQualify.addActionListener(this);
						btnQAQualify.setPreferredSize(new java.awt.Dimension(200, 102));
					}
				}
				{
					pnlQACenter = new JPanel(new BorderLayout(3, 3));
					pnlSORQualifiedAccounts.add(pnlQACenter, BorderLayout.CENTER);
					{
						scrollQualifiedAcct = new JScrollPane();
						pnlQACenter.add(scrollQualifiedAcct, BorderLayout.CENTER);
						{
							modelSORQA = new modelBankFinLoanTakeout();
							tblQualifiedAcct = new _JTableMain(modelSORQA);
							tblQualifiedAcct.addMouseListener(this);	
							scrollQualifiedAcct.setViewportView(tblQualifiedAcct);
							scrollQualifiedAcct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							rowHeaderQualifiedAcct = tblQualifiedAcct.getRowHeader();
							rowHeaderQualifiedAcct.setModel(new DefaultListModel());
							scrollQualifiedAcct.setRowHeaderView(rowHeaderQualifiedAcct);
							scrollQualifiedAcct.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
				{
					pnlSORSouth = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlSORQualifiedAccounts.add(pnlSORSouth, BorderLayout.SOUTH);
					pnlSORSouth.setPreferredSize(new java.awt.Dimension(834, 33));
					{
						btnSORPost = new JButton("Post");
						pnlSORSouth.add(btnSORPost);
						btnSORPost.setActionCommand("Post");
						btnSORPost.addActionListener(this);
					}
					/*{
						btnSORPreview = new JButton("Preview");
						pnlSORSouth.add(btnSORPreview);
						btnSORPreview.setActionCommand("SOR Preview");
						btnSORPreview.addActionListener(this);
					}*/
					{
						btnSORCancel = new JButton("Refresh");
						pnlSORSouth.add(btnSORCancel);
						btnSORCancel.setActionCommand("Refresh");
						btnSORCancel.addActionListener(this);
					}
				}
			}
			{
				pnlTaggedAccounts = new JPanel(new BorderLayout(3, 3));
				tabSORCenter.add("     List of Tagged Accounts      ", pnlTaggedAccounts);
				pnlTaggedAccounts.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
							
				{
					pnlTaggedCenter = new JPanel(new BorderLayout(3, 3));
					pnlTaggedAccounts.add(pnlTaggedCenter, BorderLayout.CENTER);
					
					{
						scrollTaggedAcct = new JScrollPane();
						pnlTaggedCenter.add(scrollTaggedAcct, BorderLayout.CENTER);
						{
							modelTaggedAcct = new modelBankFinLoanTakeout_tagged();
							tblTaggedAcct = new _JTableMain(modelTaggedAcct);
							tblTaggedAcct.addMouseListener(this);	
							scrollTaggedAcct.setViewportView(tblTaggedAcct);
							scrollTaggedAcct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							rowHeaderTaggedAcct = tblTaggedAcct.getRowHeader();
							rowHeaderTaggedAcct.setModel(new DefaultListModel());
							scrollTaggedAcct.setRowHeaderView(rowHeaderTaggedAcct);
							scrollTaggedAcct.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
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

						modelSORQA.setValueAt(dteRefDate.getDate(), tblQualifiedAcct.getSelectedRow(), 4);
						
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddDate);
						optionPaneWindow.dispose();
					}
				});
			}
		}
		
		initialize_comp();
		displayTaggedClients(modelTaggedAcct, rowHeaderTaggedAcct, tblTaggedAcct);
	}//XXX END OF INIT GUI
	
	
	//CHECKS THE REQUIRED FIELD BEFORE SAVING
	private void postAccount(){

		if(tabSORCenter.getSelectedIndex() == 0){
			if (nothingtoProcess()==false) {				
				
				if(checkBlankDate()==true){					
					
					if(checkBlankOtherIncome()==true){saveTaggedAccount();}						
					
					else {						
						JOptionPane.showMessageDialog(getContentPane(),"One of the tagged units has no Other Income.","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				else {						
					JOptionPane.showMessageDialog(getContentPane(),"One of the tagged units has no release date.","Error",JOptionPane.ERROR_MESSAGE);
				}				
			}
			else {						
				JOptionPane.showMessageDialog(getContentPane(),"Please select item(s) to post","Information",JOptionPane.INFORMATION_MESSAGE);
			}
		}	
	}
		
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Generate")){
			displayClients(modelSORQA, rowHeaderQualifiedAcct, tblQualifiedAcct);	
		}
		
		if(actionCommand.equals("Post")){
			postAccount();
		}
		
		if(actionCommand.equals("Refresh")){
			refresh();
		}

	}
	
	private void initialize_comp(){		

		co_id 	= "02";	
		co_name	= "CENQHOMES DEVELOPMENT CORPORATION";	
		proj_id = "015";
		proj_name = "TERRAVERDE RESIDENCES";
		//company_logo = RequestForPayment.sql_getCompanyLogo();
		lookupCompany.setValue(co_id);
		txtCompany.setText(co_name);
		lookupProject.setValue(proj_id);
		txtProject.setText(proj_name);
		
		//enabledFields(true);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));	
		lookupQAPhase.setLookupSQL(_SalesOfReceivables.sqlPhase(proj_id));
	}
	
	
	//DISPLAY
	public void displayClients(DefaultTableModel modelMain, JList rowHeader, _JTableMain table) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"select \r\n" + 
				"\r\n" + 
				"false," +
				"f.proj_alias as project,\r\n" + 				
				"c.description,\r\n" + 
				"upper(d.entity_name) as client_name,\r\n" + 
				"null as rlsd_date," +
				"e.total_loanable_85pct as total_loan_amt,\r\n" + 
				"e.disc_tcp_85pct as loan_prin_amt,\r\n" + 
				"(e.misc_fee_85pct - coalesce(tcost_amt,0)) as loan_misc_amt,\n" +
				"coalesce(tcost_amt,0) as tran_fee_amt, \n" +
				"0 as other_income, \n" +
				"a.pbl_id,\r\n" + 
				"b.seq_no::text \r\n" + 
				"from (select * from rf_sold_unit where status_id != 'I' \n" +
				"		and (pbl_id, seq_no) not in (select pbl_id, seq_no from rf_bankfin_loanrlsd where status_id = 'A')) a\r\n" + 
				"join (select * from rf_buyer_status where status_id != 'I' and byrstatus_id = '134') b on a.pbl_id = b.pbl_id and a.seq_no = b.seq_no\r\n" + 
				"left join mf_unit_info c on a.pbl_id = c.pbl_id\r\n" + 
				"left join rf_entity d on a.entity_id = d.entity_id\r\n" + 
				"left join (select * from rf_bank_finance_computation where status_id != 'I') e on a.pbl_id = e.pbl_id and a.seq_no = e.seq_no\r\n" + 
				"left join mf_project f on a.projcode = f.proj_id \n" +
				"left join (select distinct on (pbl_id, seq_no) pbl_id, seq_no, sum(tcost_amt) as tcost_amt " +
				"		from rf_transfer_cost group by pbl_id, seq_no ) g on a.pbl_id = g.pbl_id and a.seq_no = g.seq_no " +
				"order by d.entity_name " ;

		System.out.printf("SQL #1 Display Client: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}		
		}	
		
		table.packAll();	
		//tblQualifiedAcct.getColumnModel().getColumn(10).setPreferredWidth(60);

	}	
	
	public void displayTaggedClients(DefaultTableModel modelMain, JList rowHeader, _JTableMain table) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"select * from ( \n" +
				"select \r\n" + 
				"\r\n" + 
				"h.client_seqno," +
				"i.or_no as or_no, " +				
				"'FSBF' as particulars,\r\n" + 
				"to_char(g.date_created,'MM-dd-yyyy') as rlsd_date," +
				"f.proj_alias as project,\r\n" + 				
				"c.description,\r\n" + 
				"upper(d.entity_name) as client_name,\r\n" + 	
				"g.total_loan_proceed as total_loan_amt,\r\n" + 				
				"(coalesce(g.total_loan_proceed,0) - coalesce(g.proc_fee_amt,0) " +
				"		- coalesce(g.trans_fee_amt,0)) as loan_prin_amt,\r\n" + 
				"g.proc_fee_amt as proc_fee_amt,\n" +
				"g.trans_fee_amt as trans_fee_amt, \n" +
				"g.other_inc_amt as other_income, \n" +
				"a.pbl_id,\r\n" + 
				"b.seq_no::text \r\n" + 
				"from (select * from rf_sold_unit where status_id != 'I' \r\n" + 
				"		and (pbl_id, seq_no) in (select pbl_id, seq_no from rf_bankfin_loanrlsd where status_id = 'A')) a\r\n" + 
				"join (select * from rf_buyer_status where status_id != 'I' and byrstatus_id = '134') b on a.pbl_id = b.pbl_id and a.seq_no = b.seq_no\r\n" + 
				"left join mf_unit_info c on a.pbl_id = c.pbl_id\r\n" + 
				"left join rf_entity d on a.entity_id = d.entity_id\r\n" + 
				"left join (select * from rf_bank_finance_computation where status_id != 'I') e on a.pbl_id = e.pbl_id and a.seq_no = e.seq_no\r\n" + 
				"left join mf_project f on a.projcode = f.proj_id \n" +
				"join rf_bankfin_loanrlsd g on a.pbl_id = g.pbl_id and a.seq_no = g.seq_no " +
				"left join (select * from rf_pay_header where client_seqno in  \r\n" + 
				"	(select client_seqno from rf_pay_detail where part_type = '260' and status_id = 'A')) h on a.pbl_id = h.pbl_id and a.seq_no = h.seq_no \n" +
				"left join (select * from rf_payments where pay_part_id = '260' and status_id = 'A') i on a.pbl_id = i.pbl_id and a.seq_no = i.seq_no \n" +
				"left join mf_pay_particular j on i.pay_part_id = j.pay_part_id \n" +
				
				"union all \n" +
				
				"select \r\n" + 
				"\r\n" + 
				"a.client_seqno,\r\n" + 
				"k.or_no as or_no, \r\n" + 				
				"j.particulars,\r\n" + 
				"to_char(b.trans_date,'MM-dd-yyyy') as rlsd_date," +
				"f.proj_alias as project,\r\n" + 
				"c.description,\r\n" + 
				"upper(d.entity_name) as client_name,\r\n" + 
				"0 as total_loan_amt,\r\n" + 
				"0 as loan_prin_amt,\r\n" + 
				"0 as proc_fee_amt,\r\n" + 
				"0 as trans_fee_amt, \r\n" + 
				"g.other_inc_amt as other_income, \r\n" + 
				"b.pbl_id,\r\n" + 
				"null as seq_no\r\n" + 
				"from ( select * from rf_pay_detail where part_type = '174' and status_id = 'A' ) a\r\n" + 
				"join rf_pay_header b on a.client_seqno = b.client_seqno\r\n" + 
				"left join rf_entity d on a.entity_id = d.entity_id\r\n" + 
				"left join mf_project f on b.proj_id = f.proj_id \r\n" + 
				"join rf_bankfin_loanrlsd g on b.pbl_id = g.pbl_id and b.seq_no = g.seq_no left join (select * from rf_pay_header where client_seqno in  \r\n" + 
				"	(select client_seqno from rf_pay_detail where part_type = '174' and status_id = 'A')) h on b.pbl_id = h.pbl_id and b.seq_no = h.seq_no \r\n" + 
				"left join mf_pay_particular j on a.part_type = j.pay_part_id\r\n" + 
				"left join rf_payments k on a.client_seqno = k.client_seqno\r\n" + 
				"left join mf_unit_info c on b.pbl_id = c.pbl_id  \n" +
				") a \n" +
				"order by a.rlsd_date desc, a.client_seqno desc " ;

		System.out.printf("SQL #1 Display Tagged Client: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}		
		}	
		
		table.packAll();	
		//tblQualifiedAcct.getColumnModel().getColumn(10).setPreferredWidth(60);

	}	
	
		
	//ACTION PERFORMED
	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2)) {
			clickTableColumn_account();
		}	
	}
	
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
	
	private void clickTableColumn_account() {//ok
		
		int column = tblQualifiedAcct.getSelectedColumn();			
		
		if (column == 4) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddDate, "Deposit Date",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

			if ( scanOption == JOptionPane.CLOSED_OPTION ) {
				/*try {						
					modelSORQA.setValueAt(dteRefDate.getDate(), tblQualifiedAcct.getSelectedRow(), 0);
				} catch ( java.lang.ArrayIndexOutOfBoundsException e ) {}	*/			
			} // CLOSED_OPTION

		}

		else {}
	}
	
	private void refresh(){
		
		initialize_comp();
		lookupQAPhase.setValue("");
		txtQAPhase.setText("");
		lookupQABank.setValue("");
		txtQABank.setText("");
		
		FncTables.clearTable(modelSORQA);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderQualifiedAcct.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		
	}
	
	
	//CHECK and VALIDATE
	private boolean nothingtoProcess() {		

		boolean nothingtoProcess = true;		

		for(int x=0; x < modelSORQA.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelSORQA.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelSORQA.getValueAt(x,0));
			}
			if(modelSORQA.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelSORQA.getValueAt(x,0);
			}

			if(isTrue){		
				nothingtoProcess = false;	
				break;
			}		
			else {}
		}
		return nothingtoProcess;

	}
	
	private boolean checkBlankDate() {		

		boolean blankDate = true;		

		for(int x=0; x < modelSORQA.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelSORQA.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelSORQA.getValueAt(x,0));
			}
			if(modelSORQA.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelSORQA.getValueAt(x,0);
			}
			
			String dep_date	= "";
			if (modelSORQA.getValueAt(x,4)==null)  {} 
			else {dep_date = modelSORQA.getValueAt(x,4).toString().trim();}

			if(isTrue && dep_date.equals("")){		
				blankDate = false;	
				break;
			}		
			else {}
		}
		return blankDate;
	}
	
	private boolean checkBlankOtherIncome() {		

		boolean blankOthIncome = true;		

		for(int x=0; x < modelSORQA.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelSORQA.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelSORQA.getValueAt(x,0));
			}
			if(modelSORQA.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelSORQA.getValueAt(x,0);
			}
			
			Double other_inc = 0.00 ;
			other_inc = Double.parseDouble(tblQualifiedAcct.getValueAt(x,9).toString());

			if(isTrue && (other_inc==0.00||other_inc.equals(null))){		
				blankOthIncome = false;	
				break;
			}		
			else {}
		}
		return blankOthIncome;
	}
	
	/*private boolean checkBlankOR_no() {		

		boolean blankOR_no = true;		

		for(int x=0; x < modelSORQA.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelSORQA.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelSORQA.getValueAt(x,0));
			}
			if(modelSORQA.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelSORQA.getValueAt(x,0);
			}
			
			String orNo = "" ;
			orNo = tblQualifiedAcct.getValueAt(x,10).toString();

			if(isTrue && (orNo.equals("")||orNo.equals(null))){		
				blankOR_no = false;	
				break;
			}		
			else {}
		}
		return blankOR_no;
	}*/

	
	//SAVE
	private void saveTaggedAccount(){
		
		if (JOptionPane.showConfirmDialog(getContentPane(), 
				"<html>This process will create the following; <html>" + "\n" +
				"<html><b><i>1. Payment - for OR Issuance<html></b></i>." + "\n" +
				"<html><b><i>2. Buyer Status<html></b></i>." + "\n" +
				"<html><b><i>3. Client Ledger Application<html></b></i>." + "\n" +
				"<html>Are you sure all tagged accounts are correct?<html>", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			
			int x=0; 
			
			ArrayList<Object> listPbl_id 		= new ArrayList<Object>();
			ArrayList<Object> listSeq_no 		= new ArrayList<Object>();
			ArrayList<Object> listRelDate 		= new ArrayList<Object>();
			ArrayList<Object> listTotal_loan 	= new ArrayList<Object>();
			ArrayList<Object> listPrin_amt		= new ArrayList<Object>();
			ArrayList<Object> listProc_fee_amt	= new ArrayList<Object>();
			ArrayList<Object> listTrans_fee_amt	= new ArrayList<Object>();
			ArrayList<Object> listOther_inc_amt	= new ArrayList<Object>();
			
			while ( x < modelSORQA.getRowCount()) {
				
				Boolean isTrue = false;
				if(tblQualifiedAcct.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)tblQualifiedAcct.getValueAt(x,0));}
				if(tblQualifiedAcct.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)tblQualifiedAcct.getValueAt(x,0);}

				if(isTrue){	
					
					String pbl_id 	= "";
					String seq_no 	= "";
					Double total_loan 	= null;
					Double prin_amt 	= null;
					Double proc_fee_amt	= null;
					Double trans_fee_amt= null;
					Double other_inc_amt= null;
					String dep_date	= "";
					
					if (modelSORQA.getValueAt(x,4)==null)  {} else {dep_date = modelSORQA.getValueAt(x,4).toString().trim();}					
					try { pbl_id 	= tblQualifiedAcct.getValueAt(x,10).toString().trim().replaceAll("'", "''"); } catch (NullPointerException ev) { pbl_id = ""; }					
					try { seq_no 	= tblQualifiedAcct.getValueAt(x,11).toString().trim().replaceAll("'", "''"); } catch (NullPointerException ev) { seq_no = ""; }				
					total_loan		= Double.parseDouble(tblQualifiedAcct.getValueAt(x,5).toString());
					prin_amt		= Double.parseDouble(tblQualifiedAcct.getValueAt(x,6).toString());
					proc_fee_amt	= Double.parseDouble(tblQualifiedAcct.getValueAt(x,7).toString());
					trans_fee_amt	= Double.parseDouble(tblQualifiedAcct.getValueAt(x,8).toString());
					other_inc_amt	= Double.parseDouble(tblQualifiedAcct.getValueAt(x,9).toString());
					
					listPbl_id.add(String.format("'%s'", pbl_id));
					listSeq_no.add(String.format("'%s'", seq_no));
					listRelDate.add(String.format("'%s'", dep_date));
					listTotal_loan.add(total_loan);
					listPrin_amt.add(prin_amt);
					listProc_fee_amt.add(proc_fee_amt);
					listTrans_fee_amt.add(trans_fee_amt);
					listOther_inc_amt.add(other_inc_amt);
				}
				
				x++;
			}
			
			String pblId 		= listPbl_id.toString().replaceAll("\\[|\\]", "");
			String seqNo 		= listSeq_no.toString().replaceAll("\\[|\\]", "");		
			String relDate 		= listRelDate.toString().replaceAll("\\[|\\]", "");		
			String totalLoanAmt	= listTotal_loan.toString().replaceAll("\\[|\\]", "");
			String prinAmt		= listPrin_amt.toString().replaceAll("\\[|\\]", "");
			String procFeeAmt	= listProc_fee_amt.toString().replaceAll("\\[|\\]", "");
			String transFeeAmt	= listTrans_fee_amt.toString().replaceAll("\\[|\\]", "");
			String OthIncAmt	= listOther_inc_amt.toString().replaceAll("\\[|\\]", "");
			
			/*----------PROCESS BANK FINANCING LOAN TAKEOUT--------------------*/
			String SQL = "SELECT sp_bank_financing_loan_takeout_tagging(\n" + 		
			
			"   ARRAY["+ pblId +"]::VARCHAR[],\n" + 
			"   ARRAY["+ seqNo +"]::VARCHAR[],\n" + 
			"   ARRAY["+ relDate +"]::VARCHAR[],\n" + 
			"   ARRAY["+ totalLoanAmt +"]::NUMERIC[],\n" + 
			"   ARRAY["+ prinAmt +"]::NUMERIC[],\n" + 
			"   ARRAY["+ procFeeAmt +"]::NUMERIC[],\n" + 
			"   ARRAY["+ transFeeAmt +"]::NUMERIC[],\n" + 
			"   ARRAY["+ OthIncAmt +"]::NUMERIC[],\n" + 
			"	'"+UserInfo.EmployeeCode+"'::VARCHAR\n" + 
			");";

			Boolean bln = false;
			FncSystem.out("BF Tagging: %s%n", SQL);
			pgSelect sdb = new pgSelect();
			sdb.select(SQL, "Save", true);
			if(sdb.isNotNull()){bln = (Boolean) sdb.getResult()[0][0];}
			else{}				

			if (bln==true)
			{
				JOptionPane.showMessageDialog(getContentPane(),"<html><b><i>Bank Financing Loan Takeout<html></b></i>" +
						"<html> successfully posted.<html>","", JOptionPane.INFORMATION_MESSAGE);	
				displayClients(modelSORQA, rowHeaderQualifiedAcct, tblQualifiedAcct);
				displayTaggedClients(modelTaggedAcct, rowHeaderTaggedAcct, tblTaggedAcct);
			}
			else
			{
				JOptionPane.showMessageDialog(getContentPane(),"<html><b><i>Bank Financing Loan Takeout tagging <html></b></i>" +
						"<html>failed.<html>","", JOptionPane.INFORMATION_MESSAGE);		
			}
			
		}
	}
	
		
	
	

}
