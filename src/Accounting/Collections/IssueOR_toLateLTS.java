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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import tablemodel.modelIssueORGoodCheck;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
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

public class IssueOR_toLateLTS extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Issuance of OR for Late LTS/BOI";
	static Dimension SIZE = new Dimension(1150, 600);

	private static JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlAccountList;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlComp_a1_a;
	private JPanel pnlComp_a1_b;
	private JPanel pnlDetails;
	private JPanel pnlDetailsWest;
	private JPanel pnlDetailsEast;
	private JPanel pnlDetailsWest_a;
	private JPanel pnlDetailsWest_b;
	private JPanel pnlDetailsWest_c;

	private JLabel lblCompany;
	private JLabel lblReceiptFr_a;
	private JLabel lblReceiptTo_a;
	private JLabel lblType;
	private JLabel lblReceiptFr_b;	
	private JLabel lblReceiptTo_b;
	private JLabel lblSearch;

	private _JLookup lookupCompany;

	private JTextField txtClient;
	private static JTextField txtReceiptFr_a;
	private static JTextField txtReceiptTo_a;
	private JTextField txtReceiptTo_b;
	private JTextField txtReceiptFr_b;
	private static JTextField txtSuffixFrom;
	private static JTextField txtSuffixTo;

	private JButton btnAssignOR;	
	private JButton btnRefresh;	
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnPreview;

	private JRadioButton rbtnA;
	private JRadioButton rbtnB;
	private JRadioButton rbtnC;
	private JRadioButton rbtnD;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTagLabel tagCompany;

	private JComboBox cmbVatable;

	private _JScrollPaneMain scrollPaymentList;
	private _JScrollPaneTotal scrollPaymentList_total;
	private _JTableMain tblPaymentList;
	private JList rowHeaderPaymentList;
	private _JTableTotal tblPaymentList_total;
	private modelIssueORGoodCheck modelPaymentList;
	private modelIssueORGoodCheck modelPaymentList_total;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		

	String co_id 	= "";		
	String company 	= "";
	String company_logo;	
	String last_receipt_no = "";
	Boolean assign_OR_complete = true;

	private _JDateChooser dtePeriod1;
	private _JDateChooser dtePeriod2; 
	
	// ADDED BY JARI CRUZ DTD MAY 9 2023
	private JComboBox cmbIssuanceType;
	private String strType = "OR";

	public IssueOR_toLateLTS() {
		super(title, true, true, true, true);
		initGUI();
	}

	public IssueOR_toLateLTS(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public IssueOR_toLateLTS(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(745, 487));
		{
			JXPanel panPage = new JXPanel(new GridLayout(1, 2, 5, 5));
			pnlMain.add(panPage, BorderLayout.NORTH);
			panPage.setPreferredSize(new Dimension(0, 80));
			{
				{
					pnlComp = new JPanel(new BorderLayout(5, 5));
					panPage.add(pnlComp, BorderLayout.CENTER);
					pnlComp.setBorder(JTBorderFactory.createTitleBorder("Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							pnlComp_a = new JPanel(new GridLayout(2, 2, 5, 5));
							pnlComp.add(pnlComp_a, BorderLayout.LINE_START);	
							pnlComp_a.setPreferredSize(new Dimension(100, 30));		
							{
								{
									{
										/*	lblCompany = new JLabel("Company: ", JLabel.TRAILING);	*/
									}
									{
										lookupCompany = new _JLookup(null, "Company", 0, 2);
										pnlComp_a.add(lookupCompany, BorderLayout.CENTER);
										lookupCompany.setLookupSQL(SQL_COMPANY());
										lookupCompany.setReturnColumn(0);
										lookupCompany.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();

												if(data != null){
													co_id = (String) data[0];	
													String name = (String) data[1];						
													tagCompany.setTag(name);									

													executeRefresh();
												}
											}
										});	
									}
								}
							}
							
							// added by jari cruz asof may 9 2023 DCRF# 2581
							{
								String[] issuanceType = {"Official Receipt","Sales Invoice"};
								cmbIssuanceType = new JComboBox(issuanceType);
								pnlComp_a.add(cmbIssuanceType);
								cmbIssuanceType.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent arg0) {
										// TODO Auto-generated method stub
										System.out.println("cmbIssuanceType was used!");
										
										if (cmbIssuanceType.getSelectedItem().equals("Official Receipt")) {
											strType = "OR";
										} else if (cmbIssuanceType.getSelectedItem().equals("Sales Invoice")) {
											strType = "SI";
										}
										
										fncGenerateTablePanel();
										fncGenerateSouthPanel();
										initialize_comp();
									}
								});
							}
						}
						
						{
							JXPanel pnlComp_b = new JXPanel(new GridLayout(2, 1, 5, 5));
							pnlComp.add(pnlComp_b, BorderLayout.CENTER);				
							{
								JXPanel panCompany = new JXPanel(new GridLayout(1, 1, 5, 5));
								pnlComp_b.add(panCompany, BorderLayout.CENTER);		
								{
									tagCompany = new _JTagLabel("[ ]");
									panCompany.add(tagCompany);
									tagCompany.setEnabled(true);	
									tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
								}	
							}
							
							// added by jari cruz asof may 9 2023 DCRF# 2581
							{
								pnlComp_b.add(new _JTagLabel("[ Receipt Type ]"));
							}
						}
					}
				}
				{
					JXPanel panPeriodPrint = new JXPanel(new BorderLayout(5, 5));
					panPage.add(panPeriodPrint, BorderLayout.CENTER);
					{
						{
							JXPanel panPeriod = new JXPanel(new GridLayout(2, 1, 5, 5)); 
							panPeriodPrint.add(panPeriod, BorderLayout.CENTER);
							panPeriod.setBorder(JTBorderFactory.createTitleBorder("Period", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									JPanel pnlFrom = new JPanel(new BorderLayout(5, 5));
									panPeriod.add(pnlFrom);
									{

										JLabel lblFrom = new JLabel("From: ");
										lblFrom.setPreferredSize(new Dimension(40, 20));
										pnlFrom.add(lblFrom, BorderLayout.WEST);
									}
									{
										dtePeriod1 = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlFrom.add(dtePeriod1, BorderLayout.CENTER);
										dtePeriod1.getCalendarButton().setVisible(false);
										dtePeriod1.setDate(null);
										dtePeriod1.setEditable(false);
									}
								}
								{
									JPanel pnlTo = new JPanel(new BorderLayout(5, 5));
									panPeriod.add(pnlTo);
									{

										JLabel lblTo = new JLabel("To:");
										lblTo.setPreferredSize(new Dimension(40, 20));
										pnlTo.add(lblTo, BorderLayout.WEST);
									}
									{
										dtePeriod2 = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlTo.add(dtePeriod2, BorderLayout.CENTER);
										dtePeriod2.getCalendarButton().setVisible(false);
										dtePeriod2.setDate(null);
										dtePeriod2.setEditable(false);
									}
								}
							}
						}
						{
							btnPreview = new JButton("Preview"); 
							panPeriodPrint.add(btnPreview, BorderLayout.LINE_END); 
							btnPreview.setPreferredSize(new Dimension(150, 0));
							btnPreview.setEnabled(false);
						}
					}
				}
			}
		}

		// modified by jari cruz dtd may 9 2023 DCRF# 2581
		fncGenerateTablePanel();
		fncGenerateSouthPanel();

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	private static KeyListener letterListener = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			if (Character.isLetter(e.getKeyChar())) {
				e.consume();
				JOptionPane.showMessageDialog(pnlMain, "You cannot enter letters in the first and last number field.\n"+
						"Enter the suffix in the adjacent field.", "Letter Found", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

		}
	};

	private static KeyListener digitListener = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			if (Character.isDigit(e.getKeyChar())) {
				e.consume();
				JOptionPane.showMessageDialog(pnlMain, "You cannot enter number(s) in the `Char` field.", "Letter Found", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

		}
	};

	//display tables
	public void displayListOfChecks(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		boolean A = rbtnA.isSelected();
		boolean B = rbtnB.isSelected();
		boolean C = rbtnC.isSelected();

		String sql = "select * from view_list_of_receipt_issuance_latelts ("+A+", "+B+", "+C+", '"+co_id+"')\r\n" ;
		
		// added by jari cruz asof may 9 2023 DCRF# 2581
		if (cmbIssuanceType.getSelectedItem().equals("Sales Invoice")) {
			System.out.println("\ndisplayListOfChecks for SI Issuance");
			sql = "select * from view_list_of_receipt_issuance_latelts_si ("+A+", "+B+", "+C+", '"+co_id+"')\r\n" ;
		}

		/*"\r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"(case when a.row_num = 1 then a.entity_name else '' end) as entity_name,\r\n" + 
			"a.proj_alias,\r\n" + 
			"a.pbl_id,\r\n" + 
			"a.seq_no,\r\n" + 
			"a.description,\r\n" + 
			"a.particulars,\r\n" + 
			"a.amount,\r\n" + 
			"(case when a.ar_no is null or a.ar_no = '' then a.or_no else a.ar_no end) as ar_no,\r\n" + 
			"'' as or_no,\r\n" + 
			"a.or_date,\r\n" + 
			"a.bank_alias,\r\n" + 
			"'' as check_branch,\r\n" + 
			"a.check_no,\r\n" + 
			"a.check_date,\r\n" + 
			"a.checkstat_desc,\r\n" + 
			"true as tag," +
			"a.cleared_date, \r\n" + 
			"a.pay_rec_id \r\n" +
			"\r\n" + 
			"from (select \r\n" ; 

		if (rbtnA.isSelected()==true||rbtnB.isSelected()==true||rbtnD.isSelected()==true)
		{
			sql = sql  + " row_number() over(PARTITION BY f.entity_name ORDER BY f.entity_name, a.pbl_id, a.seq_no, i.particulars, " +
			"	a.check_date) as row_num,trim(f.entity_name)as entity_name, \r\n" ;
		}
		else
		{
			sql = sql  + " row_number() over(PARTITION BY f.entity_name ORDER BY f.entity_name, i.particulars, a.pbl_id, a.seq_no, " +
			"	a.check_date) as row_num,trim(f.entity_name)as entity_name, \r\n" ;
		}

		sql = sql  +
		" g.proj_alias, \r\n" + 
		" trim(a.pbl_id)as pbl_id, \r\n" + 
		" a.seq_no, \r\n" + 
		" trim(i.particulars)as particulars, \r\n" + 
		"a.amount, \r\n" + 
		" coalesce(b.bank_alias,'') as bank_alias, \r\n" + 
		" a.check_no, \r\n" + 
		" to_char(a.check_date,'MM/dd/yy')as check_date , \r\n" + 
		" d.checkstat_desc, \r\n" + 
		" (case when h.reason_desc is null then '' else h.reason_desc end) as reason_desc ,  \r\n" + 
		" to_char(a.trans_date,'MM/dd/yy')as actual_date, \r\n" + 
		" trim(a.remarks) as remarks,a.pay_rec_id,\r\n" + 
		" e.description,\r\n" +
		" a.ar_no, \n" + 
		" a.or_no,\r\n" + 
		" a.or_date\r\n," +
		" j.trans_date as cleared_date \r\n" + 
		" \r\n" + 
		" from rf_payments a \r\n" + 
		" left join mf_bank b on a.bank_id = b.bank_id \r\n" + 
		" left join mf_bank_branch c on a.bank_branch_id = c.bank_branch_id \r\n" + 
		" left join mf_check_status d on a.check_stat_id = d.checkstat_id  \r\n" + 
		" left join mf_unit_info e on a.pbl_id = e.pbl_id  \r\n" + 
		" left join rf_entity f on a.entity_id = f.entity_id  \r\n" + 
		" left join mf_project g on a.proj_id = g.proj_id \r\n" + 
		" left join mf_check_bounce_reason h on a.bounce_reason_id = h.reason_id \r\n" + 
		" left join mf_pay_particular i on a.pay_part_id = i.pay_part_id \r\n" +
		" left join (select distinct on (pay_rec_id) pay_rec_id, trans_date from rf_check_history where new_checkstat_id = '01' " +
		"	and status_id = 'A' order by pay_rec_id,trans_date desc) j on a.pay_rec_id = j.pay_rec_id::int" + 
		"\n" +
		"where a.pay_part_id in (SELECT pay_part_id FROM mf_pay_particular where receipt_to_issue = '01' and status_id != 'I') \r\n" + 
		"and a.pbl_id::int in (651) \n" +
		"and a.co_id = '"+co_id+"' " +
		"and (case when a.pymnt_type = 'B' then a.check_stat_id = '01' else a.pay_rec_id is not null end) \n" +
		//"and (a.proj_id, a.pbl_id) in (select proj_id, pbl_id from mf_unit_info \n" +
		//"	where sub_proj_id in (select sub_proj_id from mf_sub_project where lts_date is not null and boi is not null))  \n" +
		"and a.or_doc_id is null or a.or_doc_id = '' \n " +
		"and (a.pbl_id, a.seq_no::int) not in (select pbl_id::text, seq_no::int from canceled_accounts) \n\n" ;

		if (rbtnA.isSelected()==true||rbtnB.isSelected()==true||rbtnD.isSelected()==true)
		{
			sql = sql  + "order by f.entity_name, a.pbl_id, a.seq_no, i.particulars, a.check_date\r\n" + 
			" 	\r\n" + 
			" ) a";
		}
		else 
		{
			sql = sql  + "order by f.entity_name, i.particulars, a.pbl_id, a.seq_no, a.check_date\r\n" + 
			" 	\r\n" + 
			" ) a";
		}
		 */


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


		else {
			modelPaymentList_total = new modelIssueORGoodCheck();
			modelPaymentList_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

			tblPaymentList_total = new _JTableTotal(modelPaymentList_total, tblPaymentList);
			tblPaymentList_total.setFont(dialog11Bold);
			scrollPaymentList_total.setViewportView(tblPaymentList_total);
			((_JTableTotal) tblPaymentList_total).setTotalLabel(0);}

		tblPaymentList.packAll();	
		int row_tot = tblPaymentList.getRowCount();			
		modelPaymentList_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		adjustRowHeight_account(tblPaymentList);
		modelPaymentList.setEditable(true);
	}	

	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("AssignOR") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==true ) {

			if (txtReceiptFr_a.getText().equals("") || txtReceiptTo_a.getText().equals("")) {
				JOptionPane.showMessageDialog(pnlMain, "Please input the series.", "Series", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if (ValidateSeries(lookupCompany.getValue())) {
					JOptionPane.showMessageDialog(getContentPane(), "This series already exists in the database.");
				} else {
					assignOR();	
				}
			}


		} else if(e.getActionCommand().equals("AssignOR")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to issue receipt.","Warning",JOptionPane.WARNING_MESSAGE);
		}

		if(e.getActionCommand().equals("Refresh")) {
			refresh(); 
		} 

		if (e.getActionCommand().equals("Save") && FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==true) {
			save();
		} else if (e.getActionCommand().equals("AssignOR")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to issue receipt.","Warning",JOptionPane.WARNING_MESSAGE);
		}

		if (e.getActionCommand().equals("Cancel")) {
			refresh();
		}
	}

	public void mouseClicked(MouseEvent evt) {

	}


	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	private void enableButtons(Boolean a, Boolean b, Boolean c){

		btnAssignOR.setEnabled(a);
		btnSave.setEnabled(b);	
		btnRefresh.setEnabled(c);			
	}

	private void initialize_comp(){

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();						

		enableButtons(true, false, true);
		// displayListOfChecks(modelPaymentList,rowHeaderPaymentList,modelPaymentList_total); commented by jari cruz dtd may 9 2023, reason ndi naman agad cenq hanap ni cashier
		lookupCompany.setValue(co_id);
		rbtnB.setSelected(true);
	}

	private void refresh(){

		if(!btnSave.isEnabled())  {executeRefresh();}
		else {			
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				executeRefresh();
			}
			else {}
		}
	}

	private void executeRefresh(){

		displayListOfChecks(modelPaymentList,rowHeaderPaymentList,modelPaymentList_total);		
		enableButtons(true, false, true);
		rbtnA.setSelected(false);
		rbtnB.setSelected(true);
		rbtnC.setSelected(false);
		rbtnD.setSelected(false);
		enableButtons(true, false, true);
		txtReceiptFr_a.setText("");
		txtReceiptTo_a.setText("");
		txtReceiptFr_b.setText("");
		txtReceiptTo_b.setText("");
		txtClient.setText("");
		cmbVatable.setSelectedIndex(0);	

		rbtnA.setEnabled(true);
		rbtnB.setEnabled(true);
		rbtnC.setEnabled(true);
		rbtnD.setEnabled(true);		

		assign_OR_complete = true;
	}

	private void save(){
		if (rbtnA.isSelected()==true||rbtnC.isSelected()==true)
		{
			JOptionPane.showMessageDialog(getContentPane(), "Issuance of "+strType+" is only functioning on per check basis only as of the moment."
					+ "\n" + "Please ask assistance from SPT.", "Incomplete "+strType+"", 
					JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			if(checkCompleteDetails_assginedOR()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Assigned "+strType+" not complete.", "Incomplete "+strType+"", 
					JOptionPane.WARNING_MESSAGE);}
			else 
			{	
				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to issue receipts?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {	
					executeSave();
				}				
			}
		}
	}

	private void executeSave(){

		pgSelect db = new pgSelect();
		saveOR(db);

	}

	private void assignOR(){

		if (rbtnA.isSelected()==false&&rbtnB.isSelected()==false&&rbtnC.isSelected()==false&&rbtnD.isSelected()==false)
		{
			JOptionPane.showMessageDialog(getContentPane(), "Please select method of "+strType+" issuance", "No Issuance Method", 
					JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			if(checkCompleteDetails()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete receipt numbers", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);}
			else 
			{			

				if (rbtnA.isSelected()==true) {
					issue1OR_perClient();
				}					
				if (rbtnB.isSelected()==true) {
					//issue1OR_perUnit();
					issue1OR_perUnit2();
				}
				if (rbtnC.isSelected()==true) {
					issue1OR_perParticular();
				}
				if (rbtnD.isSelected()==true) {
					issue1OR_perCheck();
				}	

				if (assign_OR_complete==true)
				{
					enableButtons(false, true, true);	
					txtReceiptFr_b.setText(String.format("%6s", txtReceiptFr_a.getText().trim()).replace(' ', '0')+txtSuffixFrom.getText());
					txtReceiptTo_b.setText(last_receipt_no);
					rbtnA.setEnabled(false);
					rbtnB.setEnabled(false);
					rbtnC.setEnabled(false);
					rbtnD.setEnabled(false);	
					modelPaymentList.setEditable(false);
				}				
			}
		}
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



	//processes and validation
	private Boolean checkCompleteDetails(){

		boolean x = false;		

		String receipt_fr, receipt_to;

		receipt_fr = txtReceiptFr_a.getText();
		receipt_to = txtReceiptTo_a.getText();

		if (receipt_fr.equals("") || receipt_to.equals("")) {x=false;} 
		else {x=true;}		

		return x;
	}

	private Boolean checkCompleteDetails_assginedOR(){

		boolean x = false;		

		String assigned_receipt_to;

		assigned_receipt_to = txtReceiptTo_b.getText();

		if (assigned_receipt_to.equals("") ) {x=false;} 
		else {x=true;}		

		return x;
	}



	//issue OR
	private void issue1OR_perCheck(){

		Integer receipt_fr = Integer.parseInt(txtReceiptFr_a.getText());
		Integer receipt_to = Integer.parseInt(txtReceiptTo_a.getText());
		Integer receipt_assign = 0;	
		Integer x = 0;
		Integer y = 0;
		Integer row = tblPaymentList.getModel().getRowCount();
		last_receipt_no = "";

		while (x<row)
		{		
			receipt_assign = receipt_fr + y;
			Boolean tagged = (Boolean)modelPaymentList.getValueAt(x,15);

			if (receipt_assign<=receipt_to)
			{
				if (tagged==true)
				{
					//System.out.printf("Value of receipt assign: %s%n", receipt_assign);
					last_receipt_no = String.format("%6s", receipt_assign).replace(' ', '0');
					//System.out.printf("Value of last receipt no: %s%n", last_receipt_no);
					modelPaymentList.setValueAt(last_receipt_no, x,8);	
					modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);	
					y++;
				}				
			}
			else 
			{
				if (tagged==true)
				{
					JOptionPane.showMessageDialog(getContentPane(),"Insufficient number of receipts.",
							"Error",JOptionPane.ERROR_MESSAGE);
					assign_OR_complete = false;
					break;	
				}
			}

			x++;
			assign_OR_complete=true;
		}

		tblPaymentList.packAll();
	}

	private void issue1OR_perClient(){

		Integer receipt_fr = Integer.parseInt(txtReceiptFr_a.getText());
		Integer receipt_to = Integer.parseInt(txtReceiptTo_a.getText());
		String client_name = "";
		Integer receipt_assign = 0;	
		Integer x = 0;
		Integer y = 0;
		Integer row = tblPaymentList.getModel().getRowCount();	

		while (x<row)
		{		

			try { client_name = modelPaymentList.getValueAt(x,0).toString().trim(); } catch (NullPointerException e) { client_name = ""; }

			if (client_name.equals(""))
			{
				modelPaymentList.setValueAt(String.format("%6s", receipt_assign).replace(' ', '0'), x,8);	
				modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);		
				x++;
				assign_OR_complete=true;
			}
			else
			{
				receipt_assign = receipt_fr + y;
				if (receipt_assign<=receipt_to)
				{					
					modelPaymentList.setValueAt(String.format("%6s", receipt_assign).replace(' ', '0'), x,8);	
					modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);		
					x++;	
					y++;
					assign_OR_complete=true;
				}
				else
				{
					JOptionPane.showMessageDialog(getContentPane(),"Insufficient number of receipts.",
							"Error",JOptionPane.ERROR_MESSAGE);
					assign_OR_complete = false;
					break;
				}
			}		
		}

		tblPaymentList.packAll();	
	}

	private void issue1OR_perParticular(){		

		Integer receipt_fr = Integer.parseInt(txtReceiptFr_a.getText());
		Integer receipt_to = Integer.parseInt(txtReceiptTo_a.getText());
		String client_name = "";
		String particular_current = "";
		String particular_row = "";
		Integer receipt_assign = 0;	
		Integer x = 0;
		Integer y = 0;
		Integer row = tblPaymentList.getModel().getRowCount();	

		while (x<row)
		{		

			try { particular_row = modelPaymentList.getValueAt(x,5).toString().trim(); } catch (NullPointerException e) { particular_row = ""; }
			try { client_name = modelPaymentList.getValueAt(x,0).toString().trim(); } catch (NullPointerException e) { client_name = ""; }

			if (client_name.equals("")&&particular_current.equals(particular_row))
			{
				if (particular_current.equals(particular_row))
				{
					modelPaymentList.setValueAt(String.format("%6s", receipt_assign).replace(' ', '0'), x,8);	
					modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);		
					x++;
					assign_OR_complete=true;
				}
			}
			else if (client_name.equals("")&&!particular_current.equals(particular_row))
			{

				receipt_assign = receipt_fr + y;
				if (receipt_assign<=receipt_to)
				{	
					modelPaymentList.setValueAt(String.format("%6s", receipt_assign).replace(' ', '0'), x,8);	
					modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);		
					x++;	
					y++;
					particular_current = particular_row;
					assign_OR_complete=true;
				}
				else
				{
					JOptionPane.showMessageDialog(getContentPane(),"Insufficient number of receipts.",
							"Error",JOptionPane.ERROR_MESSAGE);
					assign_OR_complete = false;
					break;
				}

			}
			else if (!client_name.equals(""))
			{
				receipt_assign = receipt_fr + y;
				if (receipt_assign<=receipt_to)
				{
					modelPaymentList.setValueAt(String.format("%6s", receipt_assign).replace(' ', '0'), x,8);	
					modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);		
					x++;	
					y++;
					particular_current = particular_row;
					assign_OR_complete=true;
				}
				else
				{
					JOptionPane.showMessageDialog(getContentPane(),"Insufficient number of receipts.",
							"Error",JOptionPane.ERROR_MESSAGE);
					assign_OR_complete = false;
					break;
				}
			}	
		}

		tblPaymentList.packAll();			
	}



	//save & insert
	private void saveOR(pgSelect db){

		if (rbtnB.isSelected()==true) {
			if(issueOR_perUnit(modelPaymentList)==true){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Official Receipts numbers are saved and issued.", "Save", JOptionPane.PLAIN_MESSAGE);
				displayListOfChecks(modelPaymentList,rowHeaderPaymentList,modelPaymentList_total);
				enableButtons(true, false, true);	
			} else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Something went wrong with saving.", "Save", JOptionPane.PLAIN_MESSAGE);	
			}	
		} else {
			JOptionPane.showMessageDialog(getContentPane(), "Please select Issue "+strType+" Per Unit");
		}
	}

	private Boolean issueOR_perCheck(modelIssueORGoodCheck model) {

		ArrayList<String> listEntityID 		= new ArrayList<String>();
		ArrayList<String> listProjID 		= new ArrayList<String>();
		ArrayList<String> listPBLID 		= new ArrayList<String>();
		ArrayList<String> listSeqNo 		= new ArrayList<String>();
		ArrayList<String> listPartID 		= new ArrayList<String>();
		ArrayList<BigDecimal> listAmount 	= new ArrayList<BigDecimal>();
		ArrayList<String> listReceiptNo 	= new ArrayList<String>();
		ArrayList<String> listPmtSeqNo 		= new ArrayList<String>();
		ArrayList<String> listPayRecID		= new ArrayList<String>();	
		ArrayList<String> listAR_no 		= new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){

			Boolean tagged = false;
			String pay_rec_id, receipt_no, AR_no = "";	
			try { pay_rec_id 	= model.getValueAt(x,17).toString().trim(); } catch (NullPointerException e) { pay_rec_id = ""; }
			try { receipt_no 	= model.getValueAt(x,8).toString().trim(); } catch (NullPointerException e) { receipt_no = ""; }
			try { AR_no      	= model.getValueAt(x,7).toString().trim(); } catch (NullPointerException e) { AR_no = ""; }
			try { tagged 	    = (Boolean)model.getValueAt(x,15);; } catch (NullPointerException e) { tagged = false; }

			Object[] pmt_dtl = getPmtdetails(pay_rec_id);				

			String entity_id = (String) pmt_dtl[0];//entity_id
			String proj_id = (String) pmt_dtl[1];  //proj_id         
			String pbl_id = (String) pmt_dtl[2];  //pbl_id                  
			String seq_no =  pmt_dtl[3].toString();  //seq_no  
			String part_id = (String) pmt_dtl[5];  //part_id   
			BigDecimal amount;  //amount 
			String pmt_seq_no = (String) pmt_dtl[7];  //pmt_seq_no

			if (tagged==true)
			{
				if(entity_id != null){listEntityID.add(String.format("'%s'", entity_id));}else{listEntityID.add("null");}
				if(proj_id != null){listProjID.add(String.format("'%s'", proj_id));}else{listProjID.add("null");}
				if(pbl_id != null){listPBLID.add(String.format("'%s'", pbl_id));}else{listPBLID.add("null");}
				if(seq_no != null){listSeqNo.add(String.format("'%s'", seq_no));}else{listSeqNo.add("null");}
				if(part_id != null){listPartID.add(String.format("'%s'", part_id));}else{listPartID.add("null");}
				try {amount = (BigDecimal) model.getValueAt(x, 6);} catch (ClassCastException e) {amount = new BigDecimal((Long) model.getValueAt(x, 6));}
				listAmount.add(amount);
				if(receipt_no != null){listReceiptNo.add(String.format("'%s'", receipt_no));}else{listReceiptNo.add("null");}
				if(pmt_seq_no != null){listPmtSeqNo.add(String.format("'%s'", pmt_seq_no));}else{listPmtSeqNo.add("null");}
				if(pay_rec_id != null){listPayRecID.add(String.format("'%s'", pay_rec_id));}else{listPayRecID.add("null");}
				if(AR_no != null){listAR_no.add(String.format("'%s'", AR_no));}else{listAR_no.add("null");}
			}
		}

		String entity_id 	= listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id 		= listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id 		= listPBLID.toString().replaceAll("\\[|\\]", "");
		String seq_no 		= listSeqNo.toString().replaceAll("\\[|\\]", "");
		String part_id 		= listPartID.toString().replaceAll("\\[|\\]", "");
		String amount 		= listAmount.toString().replaceAll("\\[|\\]", "");
		String receipt_no 	= listReceiptNo.toString().replaceAll("\\[|\\]", "");
		String pmt_seq_no 	= listPmtSeqNo.toString().replaceAll("\\[|\\]", "");
		String pay_rec_id 	= listPayRecID.toString().replaceAll("\\[|\\]", "");
		String AR_no  		= listAR_no.toString().replaceAll("\\[|\\]", "");


		String SQL = "SELECT sp_issueOR_LateLTS_perCheck(" +
				"     ARRAY["+ entity_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ proj_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ pbl_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ seq_no +"]::INTEGER[], \n" + 
				"     ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"     ARRAY["+ part_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"     ARRAY["+ receipt_no +"]::VARCHAR[], \n" + 
				"     '"+ UserInfo.EmployeeCode +"', \n" + 
				"     ARRAY["+ pmt_seq_no +"]::VARCHAR[], \n" + 
				"     ARRAY["+ pay_rec_id +"]::VARCHAR[]," +
				"	  '"+lookupCompany.getText()+"', \n" + 
				"     ARRAY["+ AR_no +"]::VARCHAR[]" +
				"     );";

		FncSystem.out("Issuance of "+strType+" to Good Check", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return true;
		}
	}

	private Boolean issueOR_perUnit(modelIssueORGoodCheck model) {

		ArrayList<String> listEntityID 		= new ArrayList<String>();
		ArrayList<String> listProjID 		= new ArrayList<String>();
		ArrayList<String> listPBLID 		= new ArrayList<String>();
		ArrayList<String> listSeqNo 		= new ArrayList<String>();
		ArrayList<String> listPartID 		= new ArrayList<String>();
		ArrayList<BigDecimal> listAmount 	= new ArrayList<BigDecimal>();
		ArrayList<String> listReceiptNo 	= new ArrayList<String>();
		ArrayList<String> listPmtSeqNo 		= new ArrayList<String>();
		ArrayList<String> listPayRecID		= new ArrayList<String>();	
		ArrayList<String> listAR_no 		= new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){

			Boolean tagged = false;
			String pay_rec_id, receipt_no, AR_no = "";	
			try { pay_rec_id 	= model.getValueAt(x,17).toString().trim(); } catch (NullPointerException e) { pay_rec_id = ""; }
			try { receipt_no 	= model.getValueAt(x,8).toString().trim(); } catch (NullPointerException e) { receipt_no = ""; }
			try { AR_no      	= model.getValueAt(x,7).toString().trim(); } catch (NullPointerException e) { AR_no = ""; }
			try { tagged 	    = (Boolean)model.getValueAt(x,15);; } catch (NullPointerException e) { tagged = false; }

			Object[] pmt_dtl = getPmtdetails(pay_rec_id);				

			String entity_id = (String) pmt_dtl[0];//entity_id
			String proj_id = (String) pmt_dtl[1];  //proj_id         
			String pbl_id = (String) pmt_dtl[2];  //pbl_id                  
			String seq_no =  pmt_dtl[3].toString();  //seq_no  
			String part_id = (String) pmt_dtl[5];  //part_id   
			BigDecimal amount;  //amount 
			String pmt_seq_no = (String) pmt_dtl[7];  //pmt_seq_no

			if (tagged==true)
			{
				if(entity_id != null){listEntityID.add(String.format("'%s'", entity_id));}else{listEntityID.add("null");}
				if(proj_id != null){listProjID.add(String.format("'%s'", proj_id));}else{listProjID.add("null");}
				if(pbl_id != null){listPBLID.add(String.format("'%s'", pbl_id));}else{listPBLID.add("null");}
				if(seq_no != null){listSeqNo.add(String.format("'%s'", seq_no));}else{listSeqNo.add("null");}
				if(part_id != null){listPartID.add(String.format("'%s'", part_id));}else{listPartID.add("null");}
				try {amount = (BigDecimal) model.getValueAt(x, 6);} catch (ClassCastException e) {amount = new BigDecimal((Long) model.getValueAt(x, 6));}
				listAmount.add(amount);
				if(receipt_no != null){listReceiptNo.add(String.format("'%s'", receipt_no));}else{listReceiptNo.add("null");}
				if(pmt_seq_no != null){listPmtSeqNo.add(String.format("'%s'", pmt_seq_no));}else{listPmtSeqNo.add("null");}
				if(pay_rec_id != null){listPayRecID.add(String.format("'%s'", pay_rec_id));}else{listPayRecID.add("null");}
				if(AR_no != null){listAR_no.add(String.format("'%s'", AR_no));}else{listAR_no.add("null");}
			}

		}

		String entity_id 	= listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id 		= listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id 		= listPBLID.toString().replaceAll("\\[|\\]", "");
		String seq_no 		= listSeqNo.toString().replaceAll("\\[|\\]", "");
		String part_id 		= listPartID.toString().replaceAll("\\[|\\]", "");
		String amount 		= listAmount.toString().replaceAll("\\[|\\]", "");
		String receipt_no 	= listReceiptNo.toString().replaceAll("\\[|\\]", "");
		String pmt_seq_no 	= listPmtSeqNo.toString().replaceAll("\\[|\\]", "");
		String pay_rec_id 	= listPayRecID.toString().replaceAll("\\[|\\]", "");
		String AR_no  		= listAR_no.toString().replaceAll("\\[|\\]", "");


		String SQL = "SELECT sp_issueOR_lateLTS_perUnit(" +
				"     ARRAY["+ entity_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ proj_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ pbl_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ seq_no +"]::INTEGER[], \n" + 
				"     ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"     ARRAY["+ part_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"     ARRAY["+ receipt_no +"]::VARCHAR[], \n" + 
				"     '"+ UserInfo.EmployeeCode +"', \n" + 
				"     ARRAY["+ pmt_seq_no +"]::VARCHAR[], \n" + 
				"     ARRAY["+ pay_rec_id +"]::VARCHAR[]," +
				"	  '"+lookupCompany.getText()+"', \n" + 
				"     ARRAY["+ AR_no +"]::VARCHAR[]" +
				"     );";

		FncSystem.out("Issuance of "+strType+" to Good Check", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return true;
		}
	}



	//SQL
	private Object [] getPmtdetails(String pay_rec_id) {

		String strSQL = 
				"select \r\n" + 
						"\r\n" + 
						"trim(a.entity_id) as entity_id,\r\n" + //0
						"trim(a.proj_id) as proj_id,\r\n" + 	//1
						"trim(a.pbl_id) as pbl_id,\r\n" + 		//2
						"a.seq_no,\r\n" + 			//3
						"a.amount,\r\n" + 			//4
						"a.pay_part_id,\r\n" + 		//5
						"a.amount,\r\n" + 			//6
						"a.client_seqno\r\n" + 		//7
						"\r\n" + 
						"from (select * from rf_payments where pay_rec_id = '"+pay_rec_id+"' and status_id = 'A') a" ;

		System.out.printf("SQL #1 getDRFdetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private boolean ValidateSeries(String co_id) {
		String receipt_type = cmbIssuanceType.getSelectedIndex() == 0 ? "01":"307";
		
		if(receipt_type.equals("307")) {
			return FncGlobal.GetBoolean("select (case when count(*) > 0 then true else false end) \n" + 
					"from \n" + 
					"( \n" + 
					"select x.si_no as receipt_no_char, replace(x.si_no, '"+txtSuffixFrom.getText()+"', '')::int as receipt_no_int from rf_payments x where (x.si_doc_id = '01' or x.si_date is not null) and x.co_id = '"+co_id+"' \n" + 
					"union \n" + 
					"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+txtSuffixFrom.getText()+"', '')::int as receipt_no_int from rf_pay_detail x where x.receipt_type = '"+receipt_type+"' and x.status_id = 'A' and coalesce(x.receipt_no, '') != '' \n" + 
					"and exists (SELECT * FROM rf_pay_header where client_seqno = x.client_seqno and co_id = '"+co_id+"') \n"+
					"union \n" + 
					"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+txtSuffixFrom.getText()+"', '')::int as receipt_no_int from rf_tra_Detail x where x.receipt_type = '"+receipt_type+"' and x.status_id = 'A' \n" +
					"AND EXISTS (SELECT * FROM rf_tra_header where client_seqno = x.client_seqno and co_id = '"+co_id+"') \n"+
					") a \n" + 
					"where coalesce(a.receipt_no_char, '') != '' \n" + 
					"and right(a.receipt_no_char, 1) = '"+txtSuffixFrom.getText()+"' \n" + 
					"and a.receipt_no_int::int between '"+txtReceiptFr_a.getText()+"'::int and '"+txtReceiptTo_a.getText()+"'::int; ");	
		}else {
			return FncGlobal.GetBoolean("select (case when count(*) > 0 then true else false end) \n" + 
				"from \n" + 
				"( \n" + 
				"select x.or_no as receipt_no_char, replace(x.or_no, '"+txtSuffixFrom.getText()+"', '')::int as receipt_no_int from rf_payments x where (x.or_doc_id = '01' or x.or_date is not null) and x.co_id = '"+co_id+"' \n" + 
				"union \n" + 
				"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+txtSuffixFrom.getText()+"', '')::int as receipt_no_int from rf_pay_detail x where x.receipt_type = '01' and x.status_id = 'A' and coalesce(x.receipt_no, '') != '' \n" + 
				"and exists (SELECT * FROM rf_pay_header where client_seqno = x.client_seqno and co_id = '"+co_id+"') \n"+
				"union \n" + 
				"select x.receipt_no as receipt_no_char, replace(x.receipt_no, '"+txtSuffixFrom.getText()+"', '')::int as receipt_no_int from rf_tra_Detail x where x.receipt_type = '01' and x.status_id = 'A' \n" +
				"AND EXISTS (SELECT * FROM rf_tra_header where client_seqno = x.client_seqno and co_id = '"+co_id+"') \n"+
				") a \n" + 
				"where coalesce(a.receipt_no_char, '') != '' \n" + 
				"and right(a.receipt_no_char, 1) = '"+txtSuffixFrom.getText()+"' \n" + 
				"and a.receipt_no_int::int between '"+txtReceiptFr_a.getText()+"'::int and '"+txtReceiptTo_a.getText()+"'::int; ");	
		}
	}

	private void issue1OR_perUnit_mod(){

		Integer receipt_fr = Integer.parseInt(txtReceiptFr_a.getText());
		Integer receipt_to = Integer.parseInt(txtReceiptTo_a.getText());
		String pbl_desc_current = "";
		String pbl_desc_row = "";
		Integer receipt_assign = 0;	
		Integer x = 0;
		Integer y = 0;
		Integer row = tblPaymentList.getModel().getRowCount();	
		last_receipt_no = "";

		while (x<row) {		

			try {
				pbl_desc_row = modelPaymentList.getValueAt(x,4).toString().trim();
			} catch (NullPointerException e) {
				pbl_desc_row = "";
			}

			Boolean tagged = (Boolean)modelPaymentList.getValueAt(x,15);

			if (pbl_desc_current.equals(pbl_desc_row)) {
				if (tagged==true) {
					last_receipt_no = String.format("%6s", receipt_assign).replace(' ', '0');
					modelPaymentList.setValueAt(last_receipt_no, x,8);	
					modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);	
				}
			} else {
				receipt_assign = receipt_fr + y;
				if (receipt_assign<=receipt_to) {
					if (tagged==true) {
						last_receipt_no = String.format("%6s", receipt_assign).replace(' ', '0');
						modelPaymentList.setValueAt(last_receipt_no, x,8);	
						modelPaymentList.setValueAt(FncGlobal.dateFormat(FncGlobal.getDateSQL()), x,9);							
						y++;
						pbl_desc_current = pbl_desc_row;
					}
				} else {
					if (tagged==true) {
						JOptionPane.showMessageDialog(getContentPane(),"Insufficient number of receipts.",
								"Error",JOptionPane.ERROR_MESSAGE);
						assign_OR_complete = false;
						break;
					}
				}
			}

			x++;
			assign_OR_complete=true;
		}

		tblPaymentList.packAll();
	}

	private Boolean issue1OR_perUnit() {
		Integer intFirst = Integer.parseInt(txtReceiptFr_a.getText());
		Integer intLast = Integer.parseInt(txtReceiptTo_a.getText());
		Integer intCurrent = 0; 

		String strUnitPrev = "";
		String strUnitNow = "";

		System.out.printf("Display value of receipt to: %s%n", intLast);

		try {
			for (int x=0; x < modelPaymentList.getRowCount(); x++) {
				if (intCurrent<intLast) {
					try {
						strUnitNow = modelPaymentList.getValueAt(x,4).toString().trim();
					} catch (NullPointerException e) {
						strUnitNow = "";
					}

					System.out.println("");
					System.out.println("strUnitPrev.equals(strUnitNow): "+strUnitPrev.equals(strUnitNow));

					if (strUnitPrev.equals(strUnitNow)) {
						if ((Boolean) modelPaymentList.getValueAt(x, 15)) {
							System.out.println("Dumaan dito sa Previous equals now");
							System.out.println("Receipt: "+intCurrent.toString()+txtSuffixFrom.getText());
							last_receipt_no=intCurrent.toString(); 
							last_receipt_no=String.format("%6s", last_receipt_no).replace(' ', '0')+txtSuffixFrom.getText();
							modelPaymentList.setValueAt(last_receipt_no, x, 8);
							System.out.printf("Display index value: %s%n", x);

						}						
					} else {
						if ((Boolean) modelPaymentList.getValueAt(x, 15)) {
							System.out.println("Dumaan dito sa else preview equals now");
							intCurrent = intFirst+x; 
							System.out.println("Receipt: "+intCurrent.toString()+txtSuffixFrom.getText());
							last_receipt_no=intCurrent.toString(); 
							last_receipt_no=String.format("%6s", last_receipt_no).replace(' ', '0')+txtSuffixFrom.getText();
							modelPaymentList.setValueAt(last_receipt_no, x, 8);	
							strUnitPrev = strUnitNow;
							System.out.printf("Display index value: %s%n", x);
							
						}	
					}
				} else {
					modelPaymentList.setValueAt(false, x, 15);
					System.out.printf("Display index value: %s%n", x);
				}
			}

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private Boolean issue1OR_perUnit2() {
		Integer intFirst = Integer.parseInt(txtReceiptFr_a.getText());
		Integer intLast = Integer.parseInt(txtReceiptTo_a.getText());
		Integer intCurrent = 0; 

		String strUnitPrev = "";
		String strUnitNow = "";
		String prev_receipt = "";

		System.out.printf("Display value of receipt to: %s%n", intLast);

		try {
			for (int x=0; x < modelPaymentList.getRowCount(); x++) {
				
				if ((Boolean) modelPaymentList.getValueAt(x, 15)) {
					try {
						strUnitNow = modelPaymentList.getValueAt(x,4).toString().trim();
					} catch (NullPointerException e) {
						strUnitNow = "";
					}
					
					if(x==0){
						System.out.println("IF");
						last_receipt_no=intCurrent.toString(); 
						last_receipt_no=String.format("%6s", txtReceiptFr_a.getText()).replace(' ', '0')+txtSuffixFrom.getText();
						modelPaymentList.setValueAt(last_receipt_no, x, 8);
					}else {
						System.out.println("Else new if");
						if(strUnitPrev.equals(strUnitNow)) {
							modelPaymentList.setValueAt(prev_receipt, x, 8);
						}else {
							System.out.println("Else");
							intCurrent = Integer.valueOf(last_receipt_no.replaceAll("[^0-9]", "")) + 1; 
							System.out.println("Receipt: "+intCurrent.toString()+txtSuffixFrom.getText());
							last_receipt_no=intCurrent.toString(); 
							last_receipt_no=String.format("%6s", last_receipt_no).replace(' ', '0')+txtSuffixFrom.getText();
							modelPaymentList.setValueAt(last_receipt_no, x, 8);	
						}
					}
					
				}

				strUnitPrev = strUnitNow;
				prev_receipt = (String) modelPaymentList.getValueAt(x, 8);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	private void fncGenerateTablePanel() {
		try {
			pnlMain.remove(pnlTable);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Nothing to do!");
		}
		
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));

			{						

				pnlAccountList = new JPanel();
				pnlSubTable.add(pnlAccountList, BorderLayout.CENTER);
				pnlAccountList.setLayout(new BorderLayout(0,0));	
				pnlAccountList.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlAccountList.setBorder(JTBorderFactory.createTitleBorder("List of Accounts (with Good Checks) for "+strType+" Issuance"));

				{			

					{
						scrollPaymentList = new _JScrollPaneMain();
						pnlAccountList.add(scrollPaymentList, BorderLayout.CENTER);
						{
							modelPaymentList = new modelIssueORGoodCheck();

							tblPaymentList = new _JTableMain(modelPaymentList);
							
							// added by jari curz dtd may 10 2023
							tblPaymentList.getColumnModel().getColumn(8).setHeaderValue(""+strType+" Number");
							tblPaymentList.getColumnModel().getColumn(9).setHeaderValue(""+strType+" Date");
							
							tblPaymentList.getTableHeader().resizeAndRepaint();
						    
							scrollPaymentList.setViewportView(tblPaymentList);
							tblPaymentList.addMouseListener(this);								
							tblPaymentList.setSortable(false);
							tblPaymentList.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}	

							}); 
							tblPaymentList.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblPaymentList.rowAtPoint(e.getPoint()) == -1){}
									else{tblPaymentList.setCellSelectionEnabled(true);}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblPaymentList.rowAtPoint(e.getPoint()) == -1){}
									else{tblPaymentList.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderPaymentList = tblPaymentList.getRowHeader22();
							scrollPaymentList.setRowHeaderView(rowHeaderPaymentList);
							scrollPaymentList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollPaymentList_total = new _JScrollPaneTotal(scrollPaymentList);
							pnlAccountList.add(scrollPaymentList_total, BorderLayout.SOUTH);
							{
								modelPaymentList_total = new modelIssueORGoodCheck();
								modelPaymentList_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

								tblPaymentList_total = new _JTableTotal(modelPaymentList_total, tblPaymentList);
								tblPaymentList_total.setFont(dialog11Bold);
								scrollPaymentList_total.setViewportView(tblPaymentList_total);
								((_JTableTotal) tblPaymentList_total).setTotalLabel(0);
							}
						}
					}
				} 
				//end 
			}

			{
				pnlDetails = new JPanel(new BorderLayout(5, 5));
				pnlTable.add(pnlDetails, BorderLayout.SOUTH);
				pnlDetails.setPreferredSize(new java.awt.Dimension(872, 136));				

				{
					pnlDetailsWest =  new JPanel(new BorderLayout(5, 5));
					pnlDetails.add(pnlDetailsWest, BorderLayout.WEST);
					pnlDetailsWest.setPreferredSize(new java.awt.Dimension(634, 221));


					{
						pnlDetailsWest_b = new JPanel(new GridLayout(4, 1, 5, 5));
						pnlDetailsWest.add(pnlDetailsWest_b, BorderLayout.WEST);
						pnlDetailsWest_b.setPreferredSize(new java.awt.Dimension(236, 123));
						pnlDetailsWest_b.setBorder(JTBorderFactory.createTitleBorder("How to Issue"));

						{
							rbtnA = new JRadioButton();
							pnlDetailsWest_b.add(rbtnA);
							rbtnA.setText("1 "+strType+" per Client");
							rbtnA.setBounds(277, 12, 77, 18);
							rbtnA.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							rbtnA.setSelected(false);
							rbtnA.setEnabled(false);
							rbtnA.setPreferredSize(new java.awt.Dimension(220, 18));
							rbtnA.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){									
									if (rbtnA.isSelected()==true)
									{		
										if (JOptionPane.showConfirmDialog(getContentPane(), "This will refresh table and tagged payments. Proceed?", "Confirmation", 
												JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {	
											rbtnB.setSelected(false);
											rbtnC.setSelected(false);
											rbtnD.setSelected(false);
											displayListOfChecks(modelPaymentList,rowHeaderPaymentList,modelPaymentList_total);
										}
									}
								}});					
						}	
						{
							rbtnB = new JRadioButton();
							pnlDetailsWest_b.add(rbtnB);
							rbtnB.setText("1 "+strType+" per Unit");
							rbtnB.setBounds(277, 12, 77, 18);
							rbtnB.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							rbtnB.setSelected(true);
							rbtnB.setEnabled(true);
							rbtnB.setPreferredSize(new java.awt.Dimension(220, 18));
							rbtnB.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){									
									if (rbtnB.isSelected()==true)
									{
										if (JOptionPane.showConfirmDialog(getContentPane(), "This will refresh table and tagged payments. Proceed?", "Confirmation", 
												JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {	
											rbtnA.setSelected(false);
											rbtnC.setSelected(false);
											rbtnD.setSelected(false);
											displayListOfChecks(modelPaymentList,rowHeaderPaymentList,modelPaymentList_total);
										}
									}
								}});					
						}	
						{
							rbtnC = new JRadioButton();
							pnlDetailsWest_b.add(rbtnC);
							rbtnC.setText("1 "+strType+" per Client/Particular");
							rbtnC.setBounds(277, 12, 77, 18);
							rbtnC.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							rbtnC.setSelected(false);
							rbtnC.setEnabled(false);
							rbtnC.setPreferredSize(new java.awt.Dimension(220, 18));
							rbtnC.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){									
									if (rbtnC.isSelected()==true)
									{
										if (JOptionPane.showConfirmDialog(getContentPane(), "This will refresh table and tagged payments. Proceed?", "Confirmation", 
												JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {	
											rbtnA.setSelected(false);
											rbtnB.setSelected(false);
											rbtnD.setSelected(false);
											displayListOfChecks(modelPaymentList,rowHeaderPaymentList,modelPaymentList_total);
										}
									}
								}});					
						}	
						{
							rbtnD = new JRadioButton();
							pnlDetailsWest_b.add(rbtnD);
							rbtnD.setText("1 "+strType+" per Payment");
							rbtnD.setBounds(277, 12, 77, 18);
							rbtnD.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							rbtnD.setSelected(false);
							rbtnD.setEnabled(false);
							rbtnD.setPreferredSize(new java.awt.Dimension(220, 18));
							rbtnD.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){									
									if (rbtnD.isSelected()==true)
									{
										if (JOptionPane.showConfirmDialog(getContentPane(), "This will refresh table and tagged payments. Proceed?", "Confirmation", 
												JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {	
											rbtnA.setSelected(false);
											rbtnB.setSelected(false);
											rbtnC.setSelected(false);
											displayListOfChecks(modelPaymentList,rowHeaderPaymentList,modelPaymentList_total);
										}
									}									
								}});					
						}	
					}
					{
						pnlDetailsWest_a = new JPanel(new GridLayout(4, 1, 5, 5));
						pnlDetailsWest.add(pnlDetailsWest_a, BorderLayout.CENTER);
						pnlDetailsWest_a.setPreferredSize(new java.awt.Dimension(197, 92));
						
						if (cmbIssuanceType.getSelectedItem().equals("Official Receipt")) {
							pnlDetailsWest_a.setBorder(JTBorderFactory.createTitleBorder("Official Receipt"));
						} else if (cmbIssuanceType.getSelectedItem().equals("Sales Invoice")) {
							pnlDetailsWest_a.setBorder(JTBorderFactory.createTitleBorder("Sales Invoice"));
						}
						
						{
							{
								lblReceiptFr_a = new JLabel("Receipt From :");
								pnlDetailsWest_a.add(lblReceiptFr_a);
								lblReceiptFr_a.setPreferredSize(new java.awt.Dimension(115, 25));
							}
							{
								JXPanel panFrom = new JXPanel(new BorderLayout(5, 5)); 
								pnlDetailsWest_a.add(panFrom); 
								{
									{
										txtReceiptFr_a = new JTextField();
										panFrom.add(txtReceiptFr_a, BorderLayout.CENTER);
										txtReceiptFr_a.setEditable(true);
										txtReceiptFr_a.setEnabled(true);
										txtReceiptFr_a.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										txtReceiptFr_a.addKeyListener(letterListener);
									}
									{
										txtSuffixFrom = new JTextField("");
										panFrom.add(txtSuffixFrom, BorderLayout.LINE_END);
										txtSuffixFrom.setEditable(true);
										txtSuffixFrom.setEnabled(true);
										txtSuffixFrom.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										txtSuffixFrom.setPreferredSize(new Dimension(40, 0));
										txtSuffixFrom.setHorizontalAlignment(JTextField.CENTER);
										txtSuffixFrom.addKeyListener(new KeyListener() {

											public void keyTyped(KeyEvent e) {
												if (Character.isDigit(e.getKeyChar())) {
													e.consume();
													JOptionPane.showMessageDialog(pnlMain, "You cannot enter number(s) in this field.", "Numbers Found", JOptionPane.INFORMATION_MESSAGE);
												}
											}

											public void keyReleased(KeyEvent e) {

											}

											@SuppressWarnings("static-access")
											public void keyPressed(KeyEvent e) {
												if (e.getKeyText(e.getKeyCode()).length()>1) {
													txtSuffixTo.setText("");
												} else {
													txtSuffixTo.setText(e.getKeyText(e.getKeyCode()));
												}
											}
										});
									}
								}
							}
							{
								lblReceiptTo_a = new JLabel("Receipt To :");
								pnlDetailsWest_a.add(lblReceiptTo_a);
								lblReceiptTo_a.setPreferredSize(new java.awt.Dimension(115, 25));
							}
							{
								JXPanel panTo = new JXPanel(new BorderLayout(5, 5)); 
								pnlDetailsWest_a.add(panTo); 
								{
									{
										txtReceiptTo_a = new JTextField();
										panTo.add(txtReceiptTo_a, BorderLayout.CENTER);
										txtReceiptTo_a.setEditable(true);
										txtReceiptTo_a.setEnabled(true);
										txtReceiptTo_a.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										txtReceiptTo_a.addKeyListener(letterListener);
									}
									{
										txtSuffixTo = new JTextField("");
										panTo.add(txtSuffixTo, BorderLayout.LINE_END);
										txtSuffixTo.setEditable(false);
										txtSuffixTo.setEnabled(true);
										txtSuffixTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										txtSuffixTo.setPreferredSize(new Dimension(40, 0));
										txtSuffixTo.setHorizontalAlignment(JTextField.CENTER);
									}
								}
							}
						}

					}
					{
						pnlDetailsWest_c = new JPanel(new GridLayout(4, 2, 5, 5));
						pnlDetailsWest.add(pnlDetailsWest_c, BorderLayout.EAST);
						pnlDetailsWest_c.setPreferredSize(new java.awt.Dimension(194, 123));
						pnlDetailsWest_c.setBorder(JTBorderFactory.createTitleBorder("Assigned "+strType+"(s)"));

						{
							lblReceiptFr_b = new JLabel("Receipt From :");
							pnlDetailsWest_c.add(lblReceiptFr_b);
							lblReceiptFr_b.setBounds(8, 11, 62, 12);
							lblReceiptFr_b.setPreferredSize(new java.awt.Dimension(115, 25));
						}
						{
							txtReceiptFr_b = new JTextField();
							pnlDetailsWest_c.add(txtReceiptFr_b, BorderLayout.CENTER);
							txtReceiptFr_b.setEditable(false);
							txtReceiptFr_b.setEnabled(true);
							txtReceiptFr_b.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}
						{
							lblReceiptTo_b = new JLabel("Receipt To :");
							pnlDetailsWest_c.add(lblReceiptTo_b);
							lblReceiptTo_b.setBounds(8, 11, 62, 12);
							lblReceiptTo_b.setPreferredSize(new java.awt.Dimension(115, 25));
						}
						{
							txtReceiptTo_b = new JTextField();
							pnlDetailsWest_c.add(txtReceiptTo_b, BorderLayout.CENTER);
							txtReceiptTo_b.setEditable(false);
							txtReceiptTo_b.setEnabled(true);
							txtReceiptTo_b.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}
					}
				}

				pnlDetailsEast = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlDetails.add(pnlDetailsEast, BorderLayout.CENTER);
				pnlDetailsEast.setPreferredSize(new java.awt.Dimension(300, 159));
				pnlDetailsEast.setBorder(JTBorderFactory.createTitleBorder("Search Options"));

				{
					lblSearch = new JLabel("Search Name :");
					pnlDetailsEast.add(lblSearch);
					lblSearch.setBounds(8, 11, 62, 12);
					lblSearch.setPreferredSize(new java.awt.Dimension(115, 25));
				}
				{
					txtClient = new JTextField();
					pnlDetailsEast.add(txtClient, BorderLayout.CENTER);
					txtClient.setEditable(true);
					txtClient.setEnabled(true);
					txtClient.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
				}
				{
					lblType = new JLabel("Type :");
					pnlDetailsEast.add(lblType);
					lblType.setBounds(8, 11, 62, 12);
					lblType.setPreferredSize(new java.awt.Dimension(115, 25));
				}
				{
					String status[] = {"Vatable","Non-Vatable"};					
					cmbVatable = new JComboBox(status);
					pnlDetailsEast.add(cmbVatable);
					cmbVatable.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
					cmbVatable.setBounds(537, 15, 160, 21);	
					cmbVatable.setEnabled(true);
					cmbVatable.setSelectedIndex(0);	
					cmbVatable.setPreferredSize(new java.awt.Dimension(418, 65));
					cmbVatable.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{	

						}
					});
				}
			}

		}
	}
	
	private void fncGenerateSouthPanel() {
		try {
			pnlMain.remove(pnlSouth);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Nothing to do!");
		}
		
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 4, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					btnRefresh = new JButton("Generate List");
					pnlSouthCenterb.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.setMnemonic(KeyEvent.VK_R);
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(this);
				}
				{
					btnAssignOR = new JButton("Assign "+strType+"");
					pnlSouthCenterb.add(btnAssignOR);
					btnAssignOR.setEnabled(true);
					btnAssignOR.setMnemonic(KeyEvent.VK_A);
					btnAssignOR.addActionListener(this);
					btnAssignOR.setActionCommand("AssignOR");
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setEnabled(true);
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
					btnSave.setActionCommand("Save");
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_R);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}
	}
}