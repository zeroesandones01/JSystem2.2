package Buyers.LoansManagement;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelCI_Fee_collection;
import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class CI_Fee_Payments extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "CI Fee Payment";
	static Dimension SIZE = new Dimension(1000, 600);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlNorth_a;
	private JPanel pnlNorth_a1;
	private JPanel pnlNorth_b;
	private JPanel pnlNorth_a3;
	private JPanel pnlNorth_a3_1;
	private JPanel pnlNorth_a3_2;
	private JPanel pnlNorth_a3_fixed;
	private JPanel pnlCIfee;
	private JPanel pnlNorth_a_sub1;

	private _JTabbedPane tabCenter;

	private JLabel lblCompany;	
	private JLabel lblProject;
	private JLabel lblBatchNo;
	private JLabel lblPhase;

	private _JScrollPaneMain scrollCI_fee;

	private _JScrollPaneTotal scrollCI_feeTotal;

	private modelCI_Fee_collection modelCI_fee_total;
	private modelCI_Fee_collection modelCI_fee;
	private _JTableTotal tblCashCountTotal;
	private _JTableMain tblCashCount;	

	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupPhase;
	private _JLookup lookupBatchNo;

	private _JTagLabel tagCompany;	
	private _JTagLabel tagProject;
	private _JTagLabel tagPhase;
	private _JTagLabel tagBatchNo;	

	private JButton btnCancel;	
	private JButton btnPreview;
	private JButton btnSave;	
	private JButton btnRefresh;

	private JList rowHeaderCashCount;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);	

	String co_id = "";		
	String company = "";
	String company_logo;

	String rplf_no = "";
	Integer batch_no = null;
	String proj_id = "";
	String proj_name = "";
	String sub_proj_id = "";		
	String sub_proj_name = "";	

	public CI_Fee_Payments() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CI_Fee_Payments(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public CI_Fee_Payments(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(819, 602));
		this.setBounds(0, 0, 819, 602);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.CENTER);
			pnlNorth.setLayout(new BorderLayout(0,0));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(581, 485));				

			pnlNorth_a = new JPanel(new BorderLayout(0,0));
			pnlNorth.add(pnlNorth_a, BorderLayout.NORTH);			
			pnlNorth_a.setPreferredSize(new java.awt.Dimension(805, 127));	

			{

				pnlNorth_a_sub1 = new JPanel(new BorderLayout(0,0));
				pnlNorth_a.add(pnlNorth_a_sub1, BorderLayout.NORTH);			
				pnlNorth_a_sub1.setPreferredSize(new java.awt.Dimension(805, 127));	
				pnlNorth_a_sub1.setBorder(lineBorder);	

				pnlNorth_a1 = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlNorth_a_sub1.add(pnlNorth_a1, BorderLayout.WEST);	
				pnlNorth_a1.setPreferredSize(new java.awt.Dimension(78, 96));
				pnlNorth_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

				{
					lblCompany = new JLabel("COMPANY", JLabel.TRAILING);
					pnlNorth_a1.add(lblCompany);
					lblCompany.setBounds(8, 11, 62, 12);
					lblCompany.setPreferredSize(new java.awt.Dimension(101, 16));
					lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}	
				{
					lblProject = new JLabel("Project", JLabel.TRAILING);
					pnlNorth_a1.add(lblProject);
					lblProject.setEnabled(true);	
					lblProject.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}
				{
					lblPhase = new JLabel("Phase", JLabel.TRAILING);
					pnlNorth_a1.add(lblPhase);
					lblPhase.setEnabled(true);	
					lblPhase.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}
				{
					lblBatchNo = new JLabel("Batch No.", JLabel.TRAILING);
					pnlNorth_a1.add(lblBatchNo);
					lblBatchNo.setEnabled(true);	
					lblBatchNo.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}


				pnlNorth_a3 = new JPanel(new BorderLayout(0, 5));
				pnlNorth_a_sub1.add(pnlNorth_a3, BorderLayout.CENTER);	
				pnlNorth_a3.setPreferredSize(new java.awt.Dimension(490, 96));
				pnlNorth_a3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				pnlNorth_a3_fixed = new JPanel(new BorderLayout(0, 5));
				pnlNorth_a3.add(pnlNorth_a3_fixed, BorderLayout.WEST);	
				pnlNorth_a3_fixed.setPreferredSize(new java.awt.Dimension(137, 96));
				pnlNorth_a3_fixed.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));			

				pnlNorth_a3_2 = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlNorth_a3_fixed.add(pnlNorth_a3_2, BorderLayout.WEST);	
				pnlNorth_a3_2.setPreferredSize(new java.awt.Dimension(126, 86));
				pnlNorth_a3_2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					lookupCompany = new _JLookup(null, "Company",0,2);
					pnlNorth_a3_2.add(lookupCompany);
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

								String name = (String) data[1];						
								tagCompany.setTag(name);														
							}
						}
					});
				}
				{
					lookupProject = new _JLookup(null, "Project", 0, 2);
					pnlNorth_a3_2.add(lookupProject);
					lookupProject.setPreferredSize(new java.awt.Dimension(445, 20));
					lookupProject.setEnabled(true);	
					lookupProject.setReturnColumn(0);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								proj_id = (String) data[0];		
								proj_name = (String) data[1];
								tagProject.setTag(proj_name);
								lookupPhase.setValue("");
								tagPhase.setTag("");
								sub_proj_id = "";		
								sub_proj_name = "";	
								
								lookupPhase.setLookupSQL(getPhase());
								displayCollectedFI_Pmts(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
							}
						}
					});
				}	
				{
					lookupPhase = new _JLookup(null, "Phase", 0, 2);
					pnlNorth_a3_2.add(lookupPhase);
					lookupPhase.setPreferredSize(new java.awt.Dimension(445, 20));
					lookupPhase.setEnabled(true);	
					lookupPhase.setReturnColumn(0);
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								sub_proj_id = (String) data[0];		
								sub_proj_name = (String) data[1];
								tagPhase.setTag(sub_proj_name);

								displayCollectedFI_Pmts(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
							}
						}
					});
				}
				{
					lookupBatchNo = new _JLookup(null, "Batch No.", 0, 2);
					pnlNorth_a3_2.add(lookupBatchNo);
					lookupBatchNo.setPreferredSize(new java.awt.Dimension(445, 20));
					lookupBatchNo.setEnabled(true);	
					lookupBatchNo.setReturnColumn(0);
					lookupBatchNo.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								batch_no = Integer.parseInt(data[0].toString());
								rplf_no = data[3].toString();
								tagBatchNo.setTag("RPLF No. " + rplf_no);
								displayCollectedFI_Pmts_paid(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
								modelCI_fee.setEditable(false);
								tblCashCount.setEditable(false);
								enableButtons(false, true, true, true);
							}
						}
					});
				}

				pnlNorth_a3_1 = new JPanel(new GridLayout(4, 2, 5, 5));
				pnlNorth_a3.add(pnlNorth_a3_1, BorderLayout.CENTER);	
				pnlNorth_a3_1.setPreferredSize(new java.awt.Dimension(430, 96));
				pnlNorth_a3_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{
					tagCompany = new _JTagLabel("[ ]");
					pnlNorth_a3_1.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);	
					tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagProject = new _JTagLabel("[ ]");
					pnlNorth_a3_1.add(tagProject);
					tagProject.setBounds(209, 27, 700, 22);
					tagProject.setEnabled(true);	
					tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagPhase = new _JTagLabel("[ ]");
					pnlNorth_a3_1.add(tagPhase);
					tagPhase.setBounds(209, 27, 700, 22);
					tagPhase.setEnabled(true);	
					tagPhase.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagBatchNo = new _JTagLabel("[ ]");
					pnlNorth_a3_1.add(tagBatchNo);
					tagBatchNo.setBounds(209, 27, 700, 22);
					tagBatchNo.setEnabled(true);	
					tagBatchNo.setPreferredSize(new java.awt.Dimension(27, 33));
				}

			}

			//end of header

			pnlNorth_b = new JPanel(new BorderLayout(0, 0));
			pnlNorth.add(pnlNorth_b, BorderLayout.CENTER);				
			pnlNorth_b.setPreferredSize(new java.awt.Dimension(805, 402));
			pnlNorth_b.setBorder(lineBorder);	

			tabCenter = new _JTabbedPane();
			pnlNorth_b.add(tabCenter, BorderLayout.CENTER);			
			tabCenter.setPreferredSize(new java.awt.Dimension(1192, 433));

			{
				pnlCIfee = new JPanel(new BorderLayout());
				tabCenter.addTab("CI Fee Collected", null, pnlCIfee, null);
				pnlCIfee.setPreferredSize(new java.awt.Dimension(1183, 365));

				{
					scrollCI_fee = new _JScrollPaneMain();
					pnlCIfee.add(scrollCI_fee, BorderLayout.CENTER);
					{
						modelCI_fee = new modelCI_Fee_collection();

						tblCashCount = new _JTableMain(modelCI_fee);
						scrollCI_fee.setViewportView(tblCashCount);
						//tblCashCount.addMouseListener(this);						

						tblCashCount.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblCashCount.rowAtPoint(e.getPoint()) == -1){tblCashCountTotal.clearSelection();}
								else{tblCashCount.setCellSelectionEnabled(true);}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblCashCount.rowAtPoint(e.getPoint()) == -1){tblCashCountTotal.clearSelection();}
								else{tblCashCount.setCellSelectionEnabled(true);}								
							}
						});

						tblCashCount.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent arg0) {
								btnSave.setEnabled(true);
							}
						});						

					}
					{
						rowHeaderCashCount = tblCashCount.getRowHeader22();
						scrollCI_fee.setRowHeaderView(rowHeaderCashCount);
						scrollCI_fee.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollCI_feeTotal = new _JScrollPaneTotal(scrollCI_fee);
					pnlCIfee.add(scrollCI_feeTotal, BorderLayout.SOUTH);
					{
						modelCI_fee_total = new modelCI_Fee_collection();
						modelCI_fee_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

						tblCashCountTotal = new _JTableTotal(modelCI_fee_total, tblCashCount);
						scrollCI_feeTotal.setViewportView(tblCashCountTotal);
						tblCashCountTotal.setFont(dialog11Bold);
						((_JTableTotal) tblCashCountTotal).setTotalLabel(0);
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
					btnSave = new JButton("Save");
					pnlSouthCenter.add(btnSave);
					btnSave.addActionListener(this);
					btnSave.setEnabled(true);					
				}	
				{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenter.add(btnRefresh);
					btnRefresh.addActionListener(this);
					btnRefresh.setEnabled(true);
					btnRefresh.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) { 					
							if (batch_no==null)
							{
								displayCollectedFI_Pmts(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
							}
							else
							{
								displayCollectedFI_Pmts_paid(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
							}
						}});
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
					btnCancel.setEnabled(true);
				}
			}
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display		
	private void displayCollectedFI_Pmts(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"---------display list of Collected CI Fee (unpaid)\r\n" + 
			"select \r\n" + 
			"false, " +
			"a.entity_id, " +
			"b.entity_name,\r\n" + 
			"c.description,\r\n" + 
			"(-1*a.trans_amt) as amount,\r\n" + 
			"d.issued_date::date,\r\n" + 
			"a.pay_rec_id,\r\n" + 
			"c.proj_id,\r\n" + 
			"c.sub_proj_id," +
			"a.rb_id \r\n" + 
			"\r\n" + 
			"from (select * from rf_crb_detail where acct_id = '03-02-21-000' and status_id != 'I' " +
			"	and rb_id not in (select ar_no from rf_ci_fee_payment where status_id != 'I')" +
			") a \r\n" + 
			"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
			"left join mf_unit_info c on a.pbl_id = c.pbl_id\r\n" + 
			"left join rf_crb_header d on a.rb_id = d.rb_id and a.doc_id = d.doc_id\r\n" + 
			"left join mf_project e on c.proj_id = e.proj_id\r\n" + 
			"left join mf_sub_project f on c.sub_proj_id = f.sub_proj_id AND f.status_id = 'A'" +//ADDED STATUS ID BY LESTER DCRF 2718
		
			"where a.rb_id is not null " ;
			if(proj_id.equals("")){} else {sql = sql + "and a.proj_id = '"+proj_id+"'";}
			if(sub_proj_id.equals("")){} else {sql = sql + "and a.phase = '"+sub_proj_name+"'";}
			

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
			modelCI_fee_total = new modelCI_Fee_collection();
			modelCI_fee_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

			tblCashCountTotal = new _JTableTotal(modelCI_fee_total, tblCashCount);
			scrollCI_feeTotal.setViewportView(tblCashCountTotal);
			tblCashCountTotal.setFont(dialog11Bold);
			((_JTableTotal) tblCashCountTotal).setTotalLabel(0);
		}

		totalCIFee(modelCI_fee, modelCI_fee_total);
		tblCashCount.packAll();
		adjustRowHeight(tblCashCount);
		enableButtons(true, true, false, true);
	}

	private void displayCollectedFI_Pmts_paid(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"---------display list of Collected CI Fee (unpaid)\r\n" + 
			"select \r\n" + 
			"true, " +
			"a.entity_id, " +
			"b.entity_name,\r\n" + 
			"c.description,\r\n" + 
			"(-1*a.trans_amt) as amount,\r\n" + 
			"d.issued_date::date,\r\n" + 
			"a.pay_rec_id,\r\n" + 
			"c.proj_id,\r\n" + 
			"c.sub_proj_id," +
			"a.rb_id \r\n" + 
			"\r\n" + 
			"from (select * from rf_crb_detail where acct_id = '03-02-21-000' and status_id != 'I'" +
			//"	and rb_id not in (select ar_no from rf_ci_fee_payment where status_id != 'I')" +
			") a \r\n" + 
			"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
			"left join mf_unit_info c on a.pbl_id = c.pbl_id\r\n" + 
			"left join rf_crb_header d on a.rb_id = d.rb_id and a.doc_id = d.doc_id\r\n" + 
			"left join mf_project e on c.proj_id = e.proj_id\r\n" + 
			"left join mf_sub_project f on c.sub_proj_id = f.sub_proj_id AND f.status_id = 'A'" +//ADDED STATUS ID BY LESTER DCRF 2718 
			"where a.rb_id in (select ar_no from rf_ci_fee_payment where batch_no = '"+batch_no+"')" ;

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
			modelCI_fee_total = new modelCI_Fee_collection();
			modelCI_fee_total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

			tblCashCountTotal = new _JTableTotal(modelCI_fee_total, tblCashCount);
			scrollCI_feeTotal.setViewportView(tblCashCountTotal);
			tblCashCountTotal.setFont(dialog11Bold);
			((_JTableTotal) tblCashCountTotal).setTotalLabel(0);
		}

		totalCIFee(modelCI_fee, modelCI_fee_total);
		tblCashCount.packAll();
		adjustRowHeight(tblCashCount);
	}


	//properties
	private void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d){
		btnSave.setEnabled(a);
		btnRefresh.setEnabled(b);
		btnPreview.setEnabled(c);
		btnCancel.setEnabled(d);
	}

	private void refreshFields(){

		lookupProject.setValue("");		
		lookupPhase.setValue("");		
		lookupBatchNo.setValue("");
		tagProject.setTag("");
		tagPhase.setTag("");
		tagBatchNo.setTag("");
		lookupCompany.setValue(co_id);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getPhase());
		lookupBatchNo.setLookupSQL(getBatch());
		displayCollectedFI_Pmts(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
		tblCashCount.setEditable(true);

		rplf_no = "";
		batch_no = null;
		proj_id = "";
		proj_name = "";
		sub_proj_id = "";		
		sub_proj_name = "";	
		
		displayCollectedFI_Pmts(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
		enableButtons(true, true, false, true);
	}	

	private void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		lookupCompany.setValue(co_id);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getPhase());
		lookupBatchNo.setLookupSQL(getBatch());
		displayCollectedFI_Pmts(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Preview")){	preview(); preview2(); 	}

		if(e.getActionCommand().equals("Save")){ saveCI_fee(); }

		if(e.getActionCommand().equals("Cancel")){ refreshFields(); }

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


	//operations
	private void totalCIFee(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal total_ci_fee = new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try {
				total_ci_fee = total_ci_fee.add(((BigDecimal) modelMain.getValueAt(x, 4)));//Dont forget to adjust column number

			} catch (NullPointerException e) {
				total_ci_fee = total_ci_fee.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { "Total" , null, null, null, nf.format(total_ci_fee)});
	}	

	private Boolean isThereAnItemtoSend(){

		boolean x = false;		
		int w = 0;

		//create CDF 
		while ( w < tblCashCount.getRowCount()) {
			Boolean isTrue = false;
			if(tblCashCount.getValueAt(w,0) instanceof String){isTrue = new Boolean((String)tblCashCount.getValueAt(w,0));}
			if(tblCashCount.getValueAt(w,0) instanceof Boolean){isTrue = (Boolean)tblCashCount.getValueAt(w,0);}
			if(isTrue){	x = true; break; }

			w++;
		}
		return x;		
	}


	//lookup 
	private String sql_getNextRPLFno() {

		String rplf = "";

		String SQL = 
			"select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header " +
			"where co_id = '"+lookupCompany.getText()+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {rplf = "000000001";}
			else {rplf = (String) db.getResult()[0][0]; }

		}else{
			rplf = "000000001";
		}

		return rplf;
	}

	private Integer sql_getNextBatchno() {

		Integer btch = 1;

		String SQL = 
			"select coalesce(max(batch_no)::int,0) + 1 from rf_ci_fee_payment " +
			"where status_id != 'I' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {btch = 1;}
			else {btch = (Integer) db.getResult()[0][0]; }

		}else{
			btch = 1;
		}

		return btch;
	}

	private String getPhase(){//used
		
		String sql = 
		
		"select\r\n" + 
		"\r\n" + 
		"trim(a.sub_proj_id) as \"Phase ID\",\r\n" + 
		"trim(a.phase) as \"Phase\", \r\n" +
		"trim(b.proj_alias) as \"Project\"," +
		"trim(c.company_alias) as \"Company\" " + 
		"\r\n" + 
		"from mf_sub_project a \r\n" +
		"left join mf_project b on a.proj_id = b.proj_id " +
		"left join mf_company c on b.co_id = c.co_id " ;
		if (proj_id.equals(""))
		{
			
		}
		else
		{
			sql = sql + "where a.proj_id = '"+proj_id+"' \n";
		}
		
		sql = sql +
		"order by a.sub_proj_id "  ;
		
		return  sql;
	}

	private String getBatch(){//used
		return 
		"select distinct on (a.batch_no) \r\n" + 
		"\r\n" + 
		"batch_no as \"Batch No.\", \r\n" + 
		"datecreated as \"Date Created\", \r\n" +
		"trim(c.entity_name) as \"Created By \"," +
		"a.rplf_no as \"RPLF No.\"  " +
		"\r\n" + 
		"from rf_ci_fee_payment a \r\n" +
		"left join em_employee b on a.createdby = b.emp_code " +
		"left join rf_entity c on b.entity_id = c.entity_id " + 
		"\r\n" + 
		"order by a.batch_no "  ;

	}


	//preview
	private void preview(){//used
		String criteria = "Request for Payment";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		
		Double rplf_amt_tot	= Double.parseDouble(modelCI_fee.getValueAt(0,4).toString());	
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("rplf_no", rplf_no);
		mapParameters.put("rplf_amt", rplf_amt_tot);
		FncReport.generateReport("/Reports/rptRPLF_preview.jasper", reportTitle, company, mapParameters);
		
	}

	private void preview2(){//used
		
		String criteria2 = "List of CI Fee Payments";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());	
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", company);
		mapParameters2.put("co_id", co_id);
		mapParameters2.put("batch_no", batch_no);
		mapParameters2.put("prepared_by", UserInfo.FullName);
		mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		FncReport.generateReport("/Reports/rptCIFee_List.jasper", reportTitle2, company, mapParameters2);
	}

	
	
	private void adjustRowHeight(_JTableMain table){		

		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}
	}

	
	//save and insert
	private void saveCI_fee(){//ok

		if (isThereAnItemtoSend()==true)
		{
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();	
				rplf_no = sql_getNextRPLFno();
				batch_no = sql_getNextBatchno();
				insertRPLF_header(rplf_no, db);
				insertRPLF_detail(rplf_no, batch_no, db);
				db.commit();						
				enableButtons(false, true, true, true);
				modelCI_fee.setEditable(false);
				tblCashCount.setEditable(false);
				JOptionPane.showMessageDialog(getContentPane(),"CI Fee Payment Request created. RPLF No. " + rplf_no,"Information",JOptionPane.INFORMATION_MESSAGE);	
				lookupBatchNo.setText(batch_no.toString());
				tagBatchNo.setTag("RPLF No. " + rplf_no);
				displayCollectedFI_Pmts_paid(modelCI_fee, rowHeaderCashCount, modelCI_fee_total);
			}
		}

		else 
		{
			{JOptionPane.showMessageDialog(getContentPane(), "Please tag item(s) for payment.", "Warning", 
					JOptionPane.WARNING_MESSAGE);}
		}


	}

	private void insertRPLF_header(String rplf_no, pgUpdate db){

		Date date_liq_ext	= null;
		String rplf_type_id = "";
		String entity_id1	= "";
		String entity_id2	= "";
		String ent_type_id 	= "";
		String sd_no		= "";
		String doc_id		= "";
		Integer proc_id		= null;	
		String branch_id	= "";	
		String justif		= "";	
		String remarks		= "";	
		String status_id	= "";	
		String created_by	= "";	
		String edited_by	= "";
		String pay_type		= "";
		Date edited_date	= null;					

		date_liq_ext= null;
		rplf_type_id= "02"; //Cash Advance
		entity_id1	= "5153446583"; //Home Development Mutual Fund
		entity_id2	= "0000000040"; //Mavic Galicia
		ent_type_id = "13";
		pay_type	= "B";
		sd_no		= null;
		doc_id		= "09";
		proc_id		= 1;	
		branch_id	= null;	
		justif		= "";
		remarks		= "";
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	= null;		

		String sqlDetail = 
			"INSERT into rf_request_header values (" +
			"'"+co_id+"',  \n" +  		//1
			"'"+co_id+"',  \n" +		//2
			"'"+rplf_no+"',  \n" +		//3
			"now()::date,  \n" +		//4
			"now()::date,  \n" +		//5
			""+date_liq_ext+",  \n" + 	//6
			"'"+rplf_type_id+"' , \n" +	//7
			"'"+entity_id1+"',  \n" +	//8
			"'"+entity_id2+"',  \n" +	//9
			"'"+ent_type_id+"',  \n" +	//10
			""+sd_no+",  \n" +			//11
			"'"+doc_id+"' , \n" +		//12
			""+proc_id+",  \n" +		//13
			""+branch_id+" , \n" +		//14
			"'"+justif+"',  \n" +		//15
			"'"+remarks+"' , \n" +		//16
			"'"+status_id+"' , \n" +	//17
			"'"+created_by+"',  \n" +	//18
			"now(),  \n" +				//19
			"'"+edited_by+"' , \n" +	//20
			""+edited_date+",  \n" +	//21	
			"'"+pay_type+"'  \n" +		//22
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void insertRPLF_detail(String rplf_no, Integer batch_no, pgUpdate db){

		int rw = tblCashCount.getModel().getRowCount();	
		int x = 0;
		int y = 1;

		while (x < rw) {		

			Boolean isTrue = false;
			if(modelCI_fee.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelCI_fee.getValueAt(x,0));
			}
			if(modelCI_fee.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelCI_fee.getValueAt(x,0);
			}		

			if(isTrue){			

				Double amount	= Double.parseDouble(modelCI_fee.getValueAt(x,4).toString());	

				if (amount==0)  {}

				else {							
					String ref_doc_id 	= "";
					String ref_doc_no 	= "";
					String ref_doc_date	= "";
					Boolean	with_budget	= false;
					Boolean	isprojVatable	= false;
					Boolean	entityVatable	= false;
					Boolean	istaxPaidbyco	= false;
					String part_desc	= "";
					String acct_id		= "";
					String remarks		= "";
					String entity_id	= "";
					String entity_type_id	= "";
					String proj_id		= "";
					String subproj_id	= "";
					String div_id		= "";
					String dept_id		= "";
					String sec_id		= "";
					String inter_bus_id	= "";
					String inter_co_id	= "";
					String sd_no		= "";
					String asset_no		= "";
					String old_acct_id	= "";	
					String wtax_id		= "";
					ref_doc_id 			= "";
					ref_doc_no 			= "";
					ref_doc_date		= null;

					with_budget	= false;	
					isprojVatable = false;	
					entityVatable = false;	
					istaxPaidbyco = false;

					String ar_no	= "";
					String client_name	= "";
					String ar_date	= "";
					String pbl_desc	= "";
					String client_id= "";
					try { ar_no	= modelCI_fee.getValueAt(x,9).toString(); } catch (NullPointerException e) { ar_no	= ""; }	
					try { client_name	= modelCI_fee.getValueAt(x,2).toString(); } catch (NullPointerException e) { client_name	= ""; }	
					try { pbl_desc	= modelCI_fee.getValueAt(x,3).toString(); } catch (NullPointerException e) { pbl_desc	= ""; }	
					try { ar_date	= modelCI_fee.getValueAt(x,5).toString(); } catch (NullPointerException e) { ar_date	= ""; }	
					try { client_id	= modelCI_fee.getValueAt(x,1).toString(); } catch (NullPointerException e) { client_id	= ""; }	
					part_desc 	= "CA payment for C.I. fee for "+client_name + pbl_desc + " @ " + amount +
					" thru AR# "+ ar_no + " dtd " + ar_date + " (for advance CI at HDMF).";
					acct_id		= "03-02-21-000";
					remarks		= "";
					entity_id	= "0000000040";
					entity_type_id	= "13";
					proj_id	= ""; 
					subproj_id	= "";
					div_id	= "";
					dept_id	= "";
					sec_id	= ""; 
					wtax_id	= "";

					inter_bus_id= "";
					inter_co_id	= "";	

					sd_no		= null;
					asset_no	= null;
					old_acct_id	= null;	

					Double wtax_rate	= 0.00;	
					Double wtax_amount	= 0.00;	
					Double vat_rate		= 0.00;	
					Double vat_amount	= 0.00;	
					Double exp_amount	= 0.00;	
					Double pv_amount	= 0.00;	

					try { exp_amount	= Double.parseDouble(modelCI_fee.getValueAt(x,4).toString()); } catch (NullPointerException e) { exp_amount	= 0.00; }	
					try { pv_amount		= Double.parseDouble(modelCI_fee.getValueAt(x,4).toString()); } catch (NullPointerException e) { pv_amount	= 0.00; }	

					String sqlDetail = 
						"INSERT into rf_request_detail values (" +
						"'"+co_id+"',  \n" +  		//1 co_id
						"'"+co_id+"',  \n" +		//2 busunit_id
						"'"+rplf_no+"',  \n" +		//3 rplf_no
						""+y+",  \n" +				//4 line_no
						"'"+ref_doc_id+"',  \n" + 	//5	ref_doc_id				
						"'"+ref_doc_no+"',  \n" + 	//6	ref_doc_no	 		
						""+ref_doc_date+" , \n" +	//7 with_budget			
						""+with_budget+" , \n" +	//8 with_budget
						"'"+part_desc+"',  \n" +	//9 part_desc
						"'"+acct_id+"',  \n" +		//10 acct_id
						"'"+remarks+"',  \n" +		//11 remarks	
						""+amount+",  \n" +			//12 amount
						"'"+entity_id+"',  \n" +	//13 entity_id			
						"'"+entity_type_id+"' , \n" +	//14 entity_type_id	 
						"'"+proj_id+"',  \n" +		//15 project_id			
						"'"+subproj_id+"',  \n" +	//16 sub_projectid
						"'"+div_id+"',  \n" +		//17 div_id
						"'"+dept_id+"' , \n" +		//18 dept_id
						"'"+sec_id+"',  \n" +		//19 sect_id	
						"'"+inter_bus_id+"' , \n" +	//20 inter_busunit_id
						"'"+inter_co_id+"' , \n" +	//21 inter_co_id
						""+isprojVatable+" , \n" +	//22 is_vatproject
						""+entityVatable+" , \n" +	//23 is_vatentity
						""+istaxPaidbyco+" , \n" +	//24 is_taxpaidbyco
						"false, \n" +				//25 is_gross
						"'"+wtax_id+"' , \n" +		//26 wtax_id
						""+wtax_rate+", \n" +		//27 wtax_rate
						""+wtax_amount+", \n" +		//28 wtax_amt
						"null, \n" +				//29 vat_acct_id
						""+vat_rate+", \n" +		//30 vat_rate
						""+vat_amount+", \n" +		//31 vat_amt
						""+exp_amount+", \n" +		//32 exp_amt	 	
						""+pv_amount+", \n" +		//33 pv_amt
						""+sd_no+", \n" +			//34 sd_no	 
						"'', \n" +					//35 item_id
						""+asset_no+", \n" +		//36 asset_no
						""+old_acct_id+"," +		//37 old_acct_id	
						"'A',   \n"  +				//38 status_id
						"'"+UserInfo.EmployeeCode+"',  \n" +	//39 created_by
						"now(),  \n" +				//40 date_created
						"'' , \n" +					//41 edited_by
						"null,  \n" +				//42 date_edited	
						"0.00, \n" +				//43 ret_amt
						"0.00, \n" +				//44 dp_recoup_amt
						"0.00, \n" +		//45 bc_liqui_amt
						"0.00 \n" +			//46 other_liqui_amt
						")   " ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);	

					String sqlDetail2 = 
						"INSERT into rf_ci_fee_payment values (" +
						""+batch_no+",  \n" +  		//1 batch_no
						"'"+ar_no+"',  \n" +		//2 ar_no
						"'"+client_id+"',  \n" +	//3 client_id
						"'"+UserInfo.EmployeeCode+"',  \n" +	//4 createdby
						"now(),  \n" +				//5 datecreated
						"'A'," +					//6 status_id
						"'"+rplf_no+"'  \n" +				//7 rplf_no	
						")   " ;

					System.out.printf("SQL #1: %s", sqlDetail2);

					db.executeUpdate(sqlDetail2, false);		

					y++;
				}				
			}			

			x++;
		}

	}









}
