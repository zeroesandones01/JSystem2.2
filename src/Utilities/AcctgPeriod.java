package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelAcctgPeriod;
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

public class AcctgPeriod extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Accounting Period";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlPeriodList;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlPeriodDtls;
	private JPanel pnlPeriodDtls_1;
	private JPanel pnlPeriodDtls_1b;
	private JPanel pnlAgentPosition;
	private JPanel pnlPeriodDtlsAmt2;
	private JPanel pnlComp_a1_a;
	private JPanel pnlComp_a1_b;

	private modelAcctgPeriod modelPeriod;
	private modelAcctgPeriod modelPeriod_total;

	private _JLookup lookupCompany;
	private _JLookup lookupYear;
	private _JLookup lookupMonth;

	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnAddNew;
	private JButton btnEdit;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblCompany;
	private JLabel lblYear;
	private JLabel lblMonth;
	private JLabel lblStatus;

	private _JTagLabel tagCompany;
	private _JTagLabel tagX;
	private _JTagLabel tagMonth;

	private _JScrollPaneMain scrollPeriod;
	private _JScrollPaneTotal scrollPeriod_total;
	private _JTableMain tblPeriod;
	private JList rowHeaderPeriod;
	private _JTableTotal tblPeriod_total;

	private JComboBox cmbStatus;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		

	String company_name = "";
	String co_id 	= "";		
	String company 	= "";
	String company_logo;	
	String to_do 	= "";  //to distinguish whether to add new agent or new sched.
	String year = "";
	String month = "";
	String period_no = "";
	private JButton btnPreview;

	public AcctgPeriod() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AcctgPeriod(String title) {
		super(title);

	}

	public AcctgPeriod(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

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
		this.setPreferredSize(new java.awt.Dimension(680, 519));
		this.setBounds(0, 0, 680, 519);

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
									company_name = (String) data[1];						
									tagCompany.setTag(company_name);									

									enableButtons(true, false, false, false);	
									displayAcctgPeriod(modelPeriod,rowHeaderPeriod,modelPeriod_total);
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

				pnlPeriodList = new JPanel();
				pnlSubTable.add(pnlPeriodList, BorderLayout.CENTER);
				pnlPeriodList.setLayout(new BorderLayout(0,0));
				pnlPeriodList.setBorder(lineBorder);		
				pnlPeriodList.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlPeriodList.setBorder(JTBorderFactory.createTitleBorder("List of Accounting Periods"));
				
				{			

					{
						scrollPeriod = new _JScrollPaneMain();
						pnlPeriodList.add(scrollPeriod, BorderLayout.CENTER);
						{
							modelPeriod = new modelAcctgPeriod();

							tblPeriod = new _JTableMain(modelPeriod);
							scrollPeriod.setViewportView(tblPeriod);
							tblPeriod.addMouseListener(this);								
							tblPeriod.setSortable(false);
							//tblPeriod.getColumnModel().getColumn(0).setPreferredWidth(80);
							//tblPeriod.getColumnModel().getColumn(1).setPreferredWidth(80);
							//tblPeriod.getColumnModel().getColumn(2).setPreferredWidth(250);
							tblPeriod.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {clickTable();}							
								public void keyPressed(KeyEvent e) {clickTable();}	

							}); 
							tblPeriod.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblPeriod.rowAtPoint(e.getPoint()) == -1){}
									else{tblPeriod.setCellSelectionEnabled(true);}
									clickTable();
								}
								public void mouseReleased(MouseEvent e) {
									if(tblPeriod.rowAtPoint(e.getPoint()) == -1){}
									else{tblPeriod.setCellSelectionEnabled(true);}
									clickTable();
								}
							});
						}
						{
							rowHeaderPeriod = tblPeriod.getRowHeader22();
							scrollPeriod.setRowHeaderView(rowHeaderPeriod);
							scrollPeriod.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollPeriod_total = new _JScrollPaneTotal(scrollPeriod);
							pnlPeriodList.add(scrollPeriod_total, BorderLayout.SOUTH);
							{
								modelPeriod_total = new modelAcctgPeriod();
								modelPeriod_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

								tblPeriod_total = new _JTableTotal(modelPeriod_total, tblPeriod);
								tblPeriod_total.setFont(dialog11Bold);
								scrollPeriod_total.setViewportView(tblPeriod_total);
								((_JTableTotal) tblPeriod_total).setTotalLabel(0);
							}
						}
					}
				} 
				//end 
			}
			{
				pnlPeriodDtls = new JPanel(new BorderLayout(5, 5));
				pnlSubTable.add(pnlPeriodDtls, BorderLayout.SOUTH);	
				pnlPeriodDtls.setPreferredSize(new java.awt.Dimension(549, 94));	
				pnlPeriodDtls.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
				pnlPeriodDtls.setBorder(lineBorder);

				{
					pnlPeriodDtls_1 = new JPanel(new BorderLayout(0, 5));
					pnlPeriodDtls.add(pnlPeriodDtls_1, BorderLayout.WEST);	
					pnlPeriodDtls_1.setPreferredSize(new java.awt.Dimension(210, 130));
					pnlPeriodDtls_1.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));

					{
						pnlAgentPosition = new JPanel(new GridLayout(3, 1,5, 5));
						pnlPeriodDtls_1.add(pnlAgentPosition, BorderLayout.WEST);	
						pnlAgentPosition.setPreferredSize(new java.awt.Dimension(91, 107));
						pnlAgentPosition.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

						{
							{
								lblYear = new JLabel("Year ", JLabel.TRAILING);
								pnlAgentPosition.add(lblYear);
								lblYear.setEnabled(false);	
								lblYear.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}	
							{
								lblMonth = new JLabel("Month ", JLabel.TRAILING);
								pnlAgentPosition.add(lblMonth);
								lblMonth.setEnabled(false);	
								lblMonth.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}	
							{
								lblStatus = new JLabel("Status ", JLabel.TRAILING);
								pnlAgentPosition.add(lblStatus);
								lblStatus.setEnabled(false);	
								lblStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
						}

						pnlPeriodDtls_1b = new JPanel(new GridLayout(3, 1, 0, 5));
						pnlPeriodDtls_1.add(pnlPeriodDtls_1b, BorderLayout.CENTER);	
						pnlPeriodDtls_1b.setPreferredSize(new java.awt.Dimension(127, 107));
						pnlPeriodDtls_1b.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
						
						{
							lookupYear = new _JLookup(null, "Year",0,2);
							pnlPeriodDtls_1b.add(lookupYear);								
							lookupYear.setReturnColumn(1);
							lookupYear.setEnabled(false);
							lookupYear.setFilterName(true);
							lookupYear.setLookupSQL(getYear());
							lookupYear.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										year = (String) data[1];
									}
								}
							});
						}			
						{
							lookupMonth = new _JLookup(null, "Month",0,2);
							pnlPeriodDtls_1b.add(lookupMonth);
							lookupMonth.setLookupSQL(getMonth());
							lookupMonth.setReturnColumn(1);
							lookupMonth.setEnabled(false);
							lookupMonth.setFilterName(true);
							lookupMonth.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){				
										month = (String) data[1];
										tagMonth.setTag((String) data[2]);	
									}
								}
							});
						}
						{
							String type[] = {"Open","Closed"};					
							cmbStatus = new JComboBox(type);
							pnlPeriodDtls_1b.add(cmbStatus);
							cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							cmbStatus.setBounds(537, 15, 190, 21);	
							cmbStatus.setEnabled(false);
							cmbStatus.setEditable(false);
							cmbStatus.setPreferredSize(new java.awt.Dimension(217, 60));
							cmbStatus.setSelectedIndex(0);	
						}	
					}
				}
				{
					pnlPeriodDtlsAmt2 = new JPanel(new GridLayout(3, 1, 0, 5));
					pnlPeriodDtls.add(pnlPeriodDtlsAmt2, BorderLayout.CENTER);
					pnlPeriodDtlsAmt2.setPreferredSize(new java.awt.Dimension(675, 116));
					pnlPeriodDtlsAmt2.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));

					{
						tagX = new _JTagLabel("[ ]");
						pnlPeriodDtlsAmt2.add(tagX);
						tagX.setBounds(209, 27, 700, 22);
						tagX.setEnabled(false);	
						tagX.setVisible(false);	
						tagX.setPreferredSize(new java.awt.Dimension(27, 33));
					}
					{
						tagMonth = new _JTagLabel("[ ]");
						pnlPeriodDtlsAmt2.add(tagMonth);
						tagMonth.setBounds(209, 27, 700, 22);
						tagMonth.setEnabled(false);	
						tagMonth.setPreferredSize(new java.awt.Dimension(27, 33));
					}
				}
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
					btnEdit = new JButton("Edit");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
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

	//display tables
	public void displayAcctgPeriod(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"a.period_no, \n" +
			"a.period_year, \n" +
			"b.month_desc, \n" +
			"(case when a.status_id = 'A' then 'Open' else 'Closed' end) as status, \n" +
			"a.date_opened, \n" +
			"a.date_closed, \n" +
			"trim(d.entity_name) as opened_by, " +
			"trim(f.entity_name) as closed_by " +
			"" +
			"from mf_acctg_period a \n" +
			"left join mf_acctg_month b on a.month_code = b.month_code \n" +
			"left join em_employee c on a.opened_by = c.emp_code \n" +
			"left join rf_entity d on c.entity_id = d.entity_id \n" +
			"left join em_employee e on a.closed_by = e.emp_code \n" +
			"left join rf_entity f on e.entity_id = f.entity_id \n" +
			"where a.co_id = '"+co_id+"' " +
			"order by a.period_year, a.month_code::int " ;

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
			modelPeriod_total = new modelAcctgPeriod();
			modelPeriod_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

			tblPeriod_total = new _JTableTotal(modelPeriod_total, tblPeriod);
			tblPeriod_total.setFont(dialog11Bold);
			scrollPeriod_total.setViewportView(tblPeriod_total);
			((_JTableTotal) tblPeriod_total).setTotalLabel(0);}

		tblPeriod.packAll();				
		tblPeriod.getColumnModel().getColumn(0).setPreferredWidth(60);

		int row_tot = tblPeriod.getRowCount();			
		modelPeriod_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		adjustRowHeight_account(tblPeriod);
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){
			cancel();
		}

		if(e.getActionCommand().equals("Add")){
			if(UserInfo.EmployeeCode.equals("900054")||UserInfo.EmployeeCode.equals("900449")||UserInfo.EmployeeCode.equals("900933")||UserInfo.EmployeeCode.equals("900553")||UserInfo.EmployeeCode.equals("900748") || UserInfo.EmployeeCode.equals("900365") || UserInfo.EmployeeCode.equals("900876") || UserInfo.ADMIN)  //||UserInfo.EmployeeCode.equals("900703") -S'Jerick; 900553-Rowena Impat
			{
				addPeriod();
			}
			else 
			{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to add an accounting period.",
					"Information",JOptionPane.WARNING_MESSAGE); }
		}

		if (e.getActionCommand().equals("Edit")) {
			if(UserInfo.EmployeeCode.equals("900054")||UserInfo.EmployeeCode.equals("900449")||UserInfo.EmployeeCode.equals("900933")||UserInfo.EmployeeCode.equals("900553")||UserInfo.EmployeeCode.equals("900748") || UserInfo.EmployeeCode.equals("900876")) //||UserInfo.EmployeeCode.equals("900703"); 900553-Rowena Impat
			{
				edit(); 
			} else {
				JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to update an accounting period.", "Information",JOptionPane.WARNING_MESSAGE);
			}
		}

		if(e.getActionCommand().equals("Save")){
			save();
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

	private void cancel(){

		if (btnSave.isEnabled()) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}

		}

		else {	execute_cancel(); 	}
	}

	private void execute_cancel(){

		enableFields(false);		
		enableButtons(true, false, false, false);
		refreshFields();
	}

	private void save(){

		if (checkCompleteDetails()==false) {
			JOptionPane.showMessageDialog(getContentPane(), "Please enter complete details.", "Incomplete Detail", JOptionPane.WARNING_MESSAGE);
		} else if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	

			if (to_do.equals("addnew")) {
				if(isPeriodExisting(co_id, year, month)) {
					JOptionPane.showMessageDialog(getContentPane(), "An active entry for this period is already existing", "Save", JOptionPane.WARNING_MESSAGE);
				}else {
					saveAcctgPeriod(db); 
					db.commit();
					JOptionPane.showMessageDialog(getContentPane(),"A new accounting period was added.","Information",JOptionPane.INFORMATION_MESSAGE);
					execute_cancel();
					displayAcctgPeriod(modelPeriod,rowHeaderPeriod,modelPeriod_total);
				}
			} else if (to_do.equals("edit")) {
				if (AllowClosing()) {
					updateAcctgPeriod(db); 
					db.commit();				
					JOptionPane.showMessageDialog(getContentPane(),"An accounting period was successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);
					execute_cancel();
					displayAcctgPeriod(modelPeriod,rowHeaderPeriod,modelPeriod_total);	
				}
			}			
		} else {
			
		}
	}

	private void edit(){

		enableFields(true);
		enableButtons(false, false, true, true);

		to_do = "edit";
	}

	private void addPeriod(){
		enableFields(true);		
		enableButtons(false, false, true, true);
		refreshFields();
		to_do = "addnew";
	}


	//enable, disable
	private void enableFields(boolean x){

		lblYear.setEnabled(x);	
		lblMonth.setEnabled(x);	
		lblStatus.setEnabled(x);
		
		lookupYear.setEnabled(x);
		lookupMonth.setEnabled(x);
		cmbStatus.setEnabled(x);
		
		tagMonth.setEnabled(x);	

	}

	private void refreshFields(){

		lookupYear.setText("");
		lookupMonth.setText("");
		tagMonth.setTag("");
		cmbStatus.setSelectedIndex(0);
	}

	public void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);	
		btnCancel.setEnabled(d);			
	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();						

		enableButtons(true, false, false, false);	
		displayAcctgPeriod(modelPeriod,rowHeaderPeriod,modelPeriod_total);

		lookupCompany.setValue(co_id);
	}



	//select, lookup and get statements		
	private String getYear(){

		String sql = 
			"select " +
			"year_no as \"Year No.\", \n" +
			"year_txt as \"Year\" \n" +
			"from mf_acctg_year \r\n" +
			"order by year_txt::int " ;
		return sql;

	}	

	private String getMonth(){

		String sql = 
			"select month_no as \"Month No.\", " +
			"month_code as \"Month Code\", " +
			"trim(month_desc) as \"Month Desc.\" " +
			"from mf_acctg_month " +
			"order by month_no \r\n" ;
		return sql;

	}	

	private Integer sql_getNextPeriod_no() {//ok	

		Integer bnk_acct_id = 0;
		String SQL = 
			"select max(coalesce(period_no,0)) + 1 from mf_acctg_period \r\n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {bnk_acct_id = 1;}
			else {bnk_acct_id = (Integer) db.getResult()[0][0]; }			
			//bnk_acct_id = (String) db.getResult()[0][0];
		}else{
			bnk_acct_id = 1;
		}

		return bnk_acct_id;
	}

	private String sql_getMonthCode(String month_desc) {

		String a = "";

		String SQL = 
				"select month_code from mf_acctg_month " +
				"where month_desc = '"+month_desc+"'\r\n" ;

		System.out.printf("SQL #1: sql_getMonthCode", SQL);
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
	
	

	//table operations	
	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	private void displayRowDetails(){

		Integer row = tblPeriod.getSelectedRow();
		
		String yr 	= "";	
		String month_desc 	= "";	
		String status 		= "";	

		try { period_no 	= tblPeriod.getValueAt(row,0).toString().trim();} catch (NullPointerException e) { period_no 	= ""; }
		try { yr 	= tblPeriod.getValueAt(row,1).toString().trim();} catch (NullPointerException e) { yr 	= ""; }
		try { month_desc = tblPeriod.getValueAt(row,2).toString().trim();} catch (NullPointerException e) { month_desc 	= ""; }
		try { status 	 = tblPeriod.getValueAt(row,3).toString().trim();} catch (NullPointerException e) { status 	= ""; }

		lookupYear.setValue(yr);
		lookupMonth.setText(sql_getMonthCode(month_desc));
		tagMonth.setTag(month_desc);
		if (status.equals("Open")) {cmbStatus.setSelectedIndex(0);} else {cmbStatus.setSelectedIndex(1);}
		enableButtons(false, true, false, true);

	}

	private void clickTable(){		
		displayRowDetails();
		enableFields(false);
		enableButtons(true, true, false, true);
	}


	//processes and validation
	private Boolean checkCompleteDetails(){

		boolean x = false;	

		year 		= lookupYear.getText();
		month    	= lookupMonth.getText();	

		if (year.equals("") || month.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}


	//save
	private void saveAcctgPeriod(pgUpdate db){

		String status = "";
		if (cmbStatus.getSelectedIndex()==0) {status = "A";}
		else {status = "I";}		
		
		String sqlDetail = 
			"insert into mf_acctg_period \n" +
			"values ( \n" +
			"'"+sql_getNextPeriod_no()+"',   \n" +   			//1 period_no
			"'"+year+"',   \n" +								//2 period_year
			"'"+month+"',   \n" +								//3 period_month
			"now(),   \n" +										//4 date_opened
			"null,   \n" +										//5 opened_by
			"'"+UserInfo.EmployeeCode+"',   \n" +				//6 date_closed			
			"null,   \n" +										//7 closed_by
			"'"+status+"',  \n"  +								//8 status_id		
			"'"+co_id+"'  \n"  +								//9 co_id
		") " ;

		System.out.printf("SQL #1 - NewPeriod: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}
	
	private Boolean isPeriodExisting(String co_id, String year, String month) {
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM mf_acctg_period where co_id = '"+co_id+"' and period_year = "+year+" and month_code = '"+month+"' and status_id = 'A')";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
	}

	private void updateAcctgPeriod(pgUpdate db){
		String status = "";

		if (cmbStatus.getSelectedIndex()==0) {
			status = "A";
		} else {
			status = "I";
		}	
		
		String sqlDetail = "update mf_acctg_period set \n" +
			"period_year = '"+year+"', \n" +
			"month_code = '"+month+"', \n" +
			"status_id = '"+status+"', \n" ;
			
		if (cmbStatus.getSelectedIndex()==0) {
			sqlDetail = sqlDetail + 
			"date_opened = now(), \n" +	//4 date_opened
			"opened_by = '"+UserInfo.EmployeeCode+"',   \n" ;	//6 opened_by 
		}
		
		if (cmbStatus.getSelectedIndex()==1) {sqlDetail = sqlDetail + 
			"date_closed = now(), \n" +	//5 date_closed
			"closed_by = '"+UserInfo.EmployeeCode+"',   \n" ;	//7 closed_by
		}
		
		sqlDetail = sqlDetail + "co_id = '"+co_id+"' where period_no = "+period_no+"   \n" ;
		System.out.printf("SQL #1 - updateAcctgPeriod: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private boolean AllowClosing() {
		boolean blnProceed = true; 
		/*
		boolean blnJV = false; 
		boolean blnPV = false;
		boolean blnCRB = false;
		
		*/
		
//		blnJV = FncGlobal.GetBoolean("select not exists(select * from rf_jv_header where status_id = 'A' and co_id = '"+co_id+"' and ((date_part('month', jv_date)::int = '"+month+"'::int \n" + 
//				"and date_part('year', jv_date)::int = '"+year+"'::int) or (fiscal_yr::int = '"+year+"'::int and period_id::int = '"+month+"'::int)))"); 
		
		/*
		blnJV = FncGlobal.GetBoolean("select not exists(select * from rf_jv_header where status_id = 'A' and co_id = '"+co_id+"' and (fiscal_yr::int = '"+year+"'::int and period_id::int = '"+month+"'::int))"); 
		blnPV = FncGlobal.GetBoolean("select not exists(select * from rf_pv_header where status_id = 'A' and co_id = '"+co_id+"' and date_part('month', pv_date)::int = '"+month+"'::int and date_part('year', pv_date)::int = '"+year+"'::int)"); 
		blnCRB = FncGlobal.GetBoolean("select not exists(\n" +
				"select * \n" +
				"from rf_crb_header a \n" +
				"where a.status_id = 'A' and a.co_id = '"+co_id+"' and date_part('month', a.issued_date)::int = '"+month+"'::int \n" +
				"and date_part('year', a.issued_date)::int = '"+year+"'::int \n" +
				"and a.pay_rec_id::int not in (select x.pay_rec_id::int from rf_payments x where coalesce(x.remarks, '') ~* 'Late LTS/BOI' or coalesce(x.remarks, '') ~* 'Late OR Issuance for Good Check') \n" +
				"and a.pay_rec_id::int not in (select x.pay_rec_id::int from rf_payments x where coalesce(x.remarks, '') ~* 'Special Case') \n" +
				")"); 

		System.out.println("blnJV: "+blnJV);
		System.out.println("blnPV: "+blnPV);
		System.out.println("blnCRB: "+blnCRB);
		*/
		
		FncGlobal.startProgress("Checking unposted entries.. Please wait..");
		blnProceed = FncGlobal.GetBoolean("select (case when count(*)::int > 0 then false else true end) \n" +
				"from view_all_unposted_entries('"+co_id+"', '"+month+"', '"+year+"')"); 
		FncGlobal.stopProgress();

		
		if (!blnProceed) {
			blnProceed = false;
			Object[] options = {"CANCEL", "Preview"};
			
			if(JOptionPane.showOptionDialog(getContentPane(),
					"Some JV, PV and CRB entries are not yet posted. Would you like to preview the list?",
					"Confirmation",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null, 
					options, 
					options[0]) 
					== JOptionPane.NO_OPTION) {
				
				System.out.println("LIST OF REPORT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				
				String company_id = lookupCompany.getValue();
				System.out.printf("company id is: %s/n", company_id);
				String company_logo = sql_getCompanyLogo(company_id);
				String company_name = tagCompany.getText();
				
//				if(company_id.equals("02")){
//					company_name = "CENQHOMES DEVELOPMENT CORPORATION";
//				} else{
//					company_name = "VERDANTPOINT DEVELOPMENT CORPORATION";
//				}
				
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("company_name", company_name);
				mapParameters.put("co_id", co_id);
				mapParameters.put("month", lookupMonth.getText());
				mapParameters.put("year", lookupYear.getText());
				mapParameters.put("company_logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
				mapParameters.put("employee", UserInfo.Alias);
				
				FncReport.generateReport("/Reports/rptAll_Unposted_Entries.jasper", "All Unposted Entries", mapParameters);
			}
			
			
		}
		
		return blnProceed; 
	}
	
	private static String sql_getCompanyLogo(String company_id) {

		String a = "";

		String SQL = 
			"select company_logo from mf_company \n" + 
			"where co_id = '"+company_id+"' " ;

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
}
