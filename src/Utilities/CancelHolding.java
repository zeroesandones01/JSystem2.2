package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelIssuanceOfReceipt_PaymentList;
import tablemodel.model_CTSNotarization;
import Buyers.ClientServicing._CARD;
import Buyers.ClientServicing._OrderOfPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class CancelHolding extends _JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 5878038193714466360L;
	static String title = "Holding Cancellation";
	Dimension frameSize = new Dimension(600, 385);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private JLabel lblName;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblModel;
	private JLabel lblPrice;
	private JLabel lblTran;
	private JLabel lblAct;
	
	private _JDateChooser dteTran;
	private _JDateChooser dteAct;
	
	private _JLookup txtClientID;
	
	private JTextField txtClient;
	private JTextField txtProID;
	private JTextField txtProject;
	private JTextField txtUnitID;
	private JTextField txtUnit;
	private JTextField txtModelID;
	private JTextField txtModel;
	private JTextField txtPrice;
	private JTextField txtReceipt;
	
	private JButton btnCancelHolding;
	
	private JXPanel pnlTab;
	private JScrollPane scrHolding;
	private modelIssuanceOfReceipt_PaymentList modelHolding;	
	public static _JTableMain tblHolding;
	public static JList rowHolding;
	
	public CancelHolding() {
		super(title, true, true, false, false);
		initGUI();
	}

	public CancelHolding(String title) {
		super(title);
		initGUI();
	}

	public CancelHolding(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPage, BorderLayout.PAGE_START);
			panPage.setPreferredSize(new Dimension(0, 150));
			{
				JXPanel panDet = new JXPanel(new BorderLayout(5, 5));
				panPage.add(panDet, BorderLayout.CENTER);
				panDet.setBorder(JTBorderFactory.createTitleBorder("Transaction Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel panLabel = new JXPanel(new GridLayout(4, 2, 5, 5));
					panDet.add(panLabel, BorderLayout.LINE_START);
					panLabel.setPreferredSize(new Dimension(200, 0));
					{
						lblName = new JLabel("Name:");
						panLabel.add(lblName, BorderLayout.CENTER);
						lblName.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						txtClientID = new _JLookup();
						panLabel.add(txtClientID, BorderLayout.CENTER);
						txtClientID.setHorizontalAlignment(JTextField.CENTER);
						txtClientID.setReturnColumn(0);
						txtClientID.setLookupSQL(HoldingSQL());
						txtClientID.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								System.out.println("");
								System.out.println(HoldingSQL());
								
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								
								if (data!=null) {
									try {
										txtClientID.setValue(data[2].toString());	
									} catch (NullPointerException Ex) {
										
									}

									try {
										txtClient.setText(data[1].toString());	
									} catch (NullPointerException Ex) {
										
									}

									try {
										txtProject.setText(data[6].toString());
									} catch (NullPointerException Ex) {
										
									}

									try {
										txtProID.setText(data[3].toString());
									} catch (NullPointerException Ex) {
										
									}
									
									try {
										txtUnitID.setText(data[4].toString());	
									} catch (NullPointerException Ex) {
										
									}
									
									try {
										txtUnit.setText(data[7].toString());	
									} catch (NullPointerException Ex) {
										
									}

									try {
										txtModelID.setText(data[9].toString());	
									} catch (NullPointerException Ex) {
										
									}
									
									try {
										txtModel.setText(data[10].toString());	
									} catch (NullPointerException Ex) {
										
									}
									
									try {
										txtPrice.setText(data[8].toString());	
									} catch (NullPointerException Ex) {
										
									}
									
									try {
										btnCancelHolding.setEnabled(true);
										payDetail(modelHolding, rowHolding, data[0].toString());
									} catch (NullPointerException Ex) {
										btnCancelHolding.setEnabled(false);
										
										System.out.println("");
										System.out.println("Null pointer exception caught.");
									}
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project:");
						panLabel.add(lblProject, BorderLayout.CENTER);
						lblProject.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						txtProID = new JTextField();
						panLabel.add(txtProID, BorderLayout.CENTER);
						txtProID.setHorizontalAlignment(JTextField.CENTER);
						txtProID.setEditable(false);
					}
					{
						lblUnit = new JLabel("Unit:");
						panLabel.add(lblUnit, BorderLayout.CENTER);
						lblUnit.setHorizontalAlignment(JTextField.LEADING);						
					}
					{
						txtUnitID = new JTextField();
						panLabel.add(txtUnitID, BorderLayout.CENTER);
						txtUnitID.setHorizontalAlignment(JTextField.CENTER);
						txtUnitID.setEditable(false);						
					}
					{
						lblModel = new JLabel("Model:");
						panLabel.add(lblModel, BorderLayout.CENTER);
						lblModel.setHorizontalAlignment(JTextField.LEADING);						
					}
					{
						txtModelID = new JTextField();
						panLabel.add(txtModelID, BorderLayout.CENTER);
						txtModelID.setHorizontalAlignment(JTextField.CENTER);
						txtModelID.setEditable(false);						
					}
					JXPanel panBox = new JXPanel(new GridLayout(4, 1, 5, 5));
					panDet.add(panBox, BorderLayout.CENTER);
					{
						txtClient = new JTextField();
						panBox.add(txtClient, BorderLayout.CENTER);
						txtClient.setHorizontalAlignment(JTextField.CENTER);
						txtClient.setEditable(false);	
					}
					{
						txtProject = new JTextField();
						panBox.add(txtProject, BorderLayout.CENTER);
						txtProject.setHorizontalAlignment(JTextField.CENTER);
						txtProject.setEditable(false);	
					}
					{
						txtUnit = new JTextField();
						panBox.add(txtUnit, BorderLayout.CENTER);
						txtUnit.setHorizontalAlignment(JTextField.CENTER);
						txtUnit.setEditable(false);						
					}
					{
						JXPanel panModPrice = new JXPanel(new BorderLayout(5, 5));
						panBox.add(panModPrice, BorderLayout.CENTER);
						{
							txtModel = new JXTextField();
							panModPrice.add(txtModel, BorderLayout.CENTER);
							txtModel.setHorizontalAlignment(JTextField.CENTER);
							txtModel.setEditable(false);
						}
						{
							JXPanel panPrice = new JXPanel(new BorderLayout(5, 5));
							panModPrice.add(panPrice, BorderLayout.LINE_END);
							panPrice.setPreferredSize(new Dimension(200, 0));
							{
								lblPrice = new JLabel("Selling Price:");
								panPrice.add(lblPrice, BorderLayout.LINE_START);
								lblPrice.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								txtPrice = new JTextField();
								panPrice.add(txtPrice, BorderLayout.CENTER);
								txtPrice.setHorizontalAlignment(JTextField.CENTER);
								txtPrice.setEditable(false);
							}
						}
					}
				}
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			{
				JXPanel panDateRec = new JXPanel(new BorderLayout(5, 5));
				panCenter.add(panDateRec, BorderLayout.LINE_START);
				panDateRec.setPreferredSize(new Dimension(200, 0));
				{
					JXPanel panDate = new JXPanel(new GridLayout(2, 1, 5, 5));
					panDateRec.add(panDate, BorderLayout.PAGE_START);
					panDate.setPreferredSize(new Dimension(0, 90));
					panDate.setBorder(JTBorderFactory.createTitleBorder("Date Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panTran = new JXPanel(new BorderLayout(5, 5));
						panDate.add(panTran, BorderLayout.CENTER);
						{
							lblTran = new JLabel("Trans.:");
							panTran.add(lblTran, BorderLayout.LINE_START);
							lblTran.setPreferredSize(new Dimension(50, 0));
						}
						{
							dteTran = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							panTran.add(dteTran);
							dteTran.getCalendarButton().setVisible(true);
							dteTran.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteTran.setEditable(false);
						}
						JXPanel panAct = new JXPanel(new BorderLayout(5, 5));
						panDate.add(panAct, BorderLayout.CENTER);
						{
							lblAct = new JLabel("Actual:");
							panAct.add(lblAct, BorderLayout.LINE_START);
							lblAct.setPreferredSize(new Dimension(50, 0));
						}
						{
							dteAct = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							panAct.add(dteAct);
							dteAct.getCalendarButton().setVisible(true);
							dteAct.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteAct.setEditable(false);
						}
					}
					JXPanel panRec = new JXPanel(new BorderLayout(5, 5));
					panDateRec.add(panRec, BorderLayout.CENTER);
					panRec.setPreferredSize(new Dimension(0, 120));
					panRec.setBorder(JTBorderFactory.createTitleBorder("Receipt No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						txtReceipt = new JTextField();
						panRec.add(txtReceipt, BorderLayout.CENTER);
						txtReceipt.setHorizontalAlignment(JTextField.CENTER);
						txtReceipt.setEditable(false);
					}
				}
				JXPanel panGrid = new JXPanel(new BorderLayout(5, 5));
				panCenter.add(panGrid, BorderLayout.CENTER);
				{
					CreateTable();
					panGrid.add(pnlTab, BorderLayout.CENTER);
				}
			}
			JXPanel panEnd = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panEnd, BorderLayout.PAGE_END);
			panEnd.setPreferredSize(new Dimension(0, 30));
			{
				btnCancelHolding = new JButton("Cancel Holding");
				panEnd.add(btnCancelHolding, BorderLayout.CENTER);
				btnCancelHolding.setActionCommand("CancelHolding");
				btnCancelHolding.addActionListener(this);
				btnCancelHolding.setEnabled(false);
			}
		}
	}
	
	public void CreateTable() {
		pnlTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab.setOpaque(isOpaque());
		{
			scrHolding = new JScrollPane();
			pnlTab.add(scrHolding, BorderLayout.CENTER);
			scrHolding.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelHolding = new modelIssuanceOfReceipt_PaymentList();
				modelHolding.setEditable(false);
				
				tblHolding = new _JTableMain(modelHolding);
				tblHolding.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblHolding.getSelectedRow());
						}
					}
				});
				
				rowHolding = tblHolding.getRowHeader();
				scrHolding.setViewportView(tblHolding);
				
				tblHolding.getColumnModel().getColumn(0).setPreferredWidth(50);
				tblHolding.getColumnModel().getColumn(1).setPreferredWidth(100);
				tblHolding.getColumnModel().getColumn(2).setPreferredWidth(100);
				tblHolding.getColumnModel().getColumn(3).setPreferredWidth(50);
				tblHolding.getColumnModel().getColumn(4).setPreferredWidth(100);
				tblHolding.getColumnModel().getColumn(5).setPreferredWidth(50);
				tblHolding.getColumnModel().getColumn(6).setPreferredWidth(110);
				tblHolding.getColumnModel().getColumn(7).setPreferredWidth(110);
				tblHolding.getColumnModel().getColumn(8).setPreferredWidth(150);
				tblHolding.getColumnModel().getColumn(9).setPreferredWidth(50);
				tblHolding.getColumnModel().getColumn(10).setPreferredWidth(200);
				tblHolding.getColumnModel().getColumn(11).setPreferredWidth(50);
				tblHolding.getColumnModel().getColumn(12).setPreferredWidth(200);
				tblHolding.getColumnModel().getColumn(13).setPreferredWidth(100);
				tblHolding.getColumnModel().getColumn(14).setPreferredWidth(50);
				tblHolding.getColumnModel().getColumn(15).setPreferredWidth(100);
				tblHolding.getColumnModel().getColumn(16).setPreferredWidth(75);

				rowHolding = tblHolding.getRowHeader();
				rowHolding.setModel(new DefaultListModel());
				scrHolding.setRowHeaderView(rowHolding);
				scrHolding.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblHolding.hideColumns("Part ID", "Check Type ID", "Bank ID", "Branch ID", "Receipt ID");
			}
		}
	}
	
	private static String HoldingSQL() {
		return "SELECT * \n" +
			   "FROM \n" +
			   "( \n" +
			   "SELECT a.receipt_no, get_client_name(a.entity_id) as client_name, a.entity_id, a.proj_id, a.pbl_id, a.seq_no, TRIM(c.proj_name) as project_name, \n" +
			   "format('%s-%s-%s', TRIM(d.phase), TRIM(d.block), TRIM(d.lot)) as unit_description, d.sellingprice as selling_price, e.model_id, TRIM((select x.model_desc from mf_product_model x where x.model_id = e.model_id)) as current_model, \n" +
			   "d.lotarea as lot_area, d.house_constructed as house_constructed, d.ntc as with_ntc, a.trans_date as date_hold, (CASE WHEN a.tran_type = '3' THEN a.hold_until END) as extension_date, \n" +
			   "a.total_amt_paid as payment, a.selling_agent, TRIM(f.dept_alias) as sales_group, a.dept_code, get_employee_name(a.op_user_id) as hold_by, TRIM(a.remarks) as remarks, a.client_seqno, a.tran_type, a.status_id \n" +
			   "FROM rf_tra_header a \n" +
			   "INNER JOIN rf_tra_detail b ON b.header_id = a.tra_header_id \n" +
			   "LEFT JOIN mf_project c ON c.proj_id = a.proj_id \n" +
			   "LEFT JOIN mf_unit_info d ON d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id \n" +
			   "LEFT JOIN mf_product_model e ON e.model_id = d.model_id \n" +
			   "LEFT JOIN mf_department f ON f.dept_code = a.dept_code \n" +
			   "WHERE a.co_id = '02' \n" +
			   "AND (CASE WHEN ('All' = 'All' OR NULLIF(UPPER('All'), 'NULL') IS NULL) THEN TRUE ELSE a.proj_id = 'All' END) \n" +
			   "AND (CASE WHEN a.tran_type = '2' THEN TRUE ELSE  \n" +
			   "(CASE WHEN ('All' = 'All' OR NULLIF(UPPER('All'), 'NULL') IS NULL) THEN TRUE ELSE d.phase = 'All' END) END) \n" +
			   "AND (CASE WHEN ('All' = 'All' OR NULLIF(UPPER('All'), 'NULL') IS NULL) THEN TRUE ELSE d.phase = 'All' END) \n" +
			   "AND a.status_id != 'I' \n" +
			   "AND b.part_id in ( '168', '203' ) \n" +
			   "AND (case when null is null then true else a.trans_date::date = null end) \n" +
			   "AND coalesce(a.receipt_no,'') not in (select receipt_id from rf_payments where receipt_id is not null) \n" +
			   "UNION ALL \n" +
			   "SELECT a.or_no, get_client_name(a.entity_id) as client_name, a.entity_id, a.proj_id, a.pbl_id, a.seq_no, 'NO PROJECT' as project_name, \n" +
			   "format('%s-%s-%s', TRIM(d.phase), TRIM(d.block), TRIM(d.lot)) as unit_description, d.sellingprice as selling_price, e.model_id, TRIM((select x.model_desc from mf_product_model x where x.model_id = e.model_id)) as current_model, \n" +
			   "d.lotarea as lot_area, d.house_constructed as house_constructed, d.ntc as with_ntc, a.trans_date as date_hold, null as extension_date, a.amount as payment, \n" +
			   "'' as selling_agent, '' as sales_group,  '' as dept_code, get_employee_name(f.created_by) as hold_by, TRIM(a.remarks) as remarks,  \n" +
			   "a.client_seqno, '2' as tran_type, a.status_id \n" +
			   "FROM rf_payments a \n" +
			   "LEFT JOIN mf_project c ON c.proj_id = a.proj_id \n" +
			   "LEFT JOIN (select * from mf_unit_info where status_id != 'I') d ON d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id \n" +
			   "LEFT JOIN mf_product_model e ON e.model_id = d.model_id \n" +
			   "left join rf_pay_header f on a.client_seqno = f.client_seqno \n" +
			   "WHERE a.co_id = '02' AND a.status_id != 'I' AND a.pay_part_id = '203' \n" +
			   "AND a.or_no not in (select receipt_id from rf_payments where receipt_id is not null) \n" +
			   "AND (CASE WHEN ('All' = 'All' OR NULLIF(UPPER('All'), 'NULL') IS NULL) THEN TRUE ELSE a.proj_id = 'All' END) \n" +
			   "AND (CASE WHEN ('All' = 'All' OR NULLIF(UPPER('All'), 'NULL') IS NULL) THEN TRUE ELSE d.phase = 'All' END) \n" +
			   "AND (case when null is null then true else a.trans_date::date = null end) \n" +
			   ") A \n" +
			   "ORDER BY  a.project_name, get_client_name(a.entity_id), a.project_name";
	}
	
	private void payDetail(DefaultTableModel modelMain, JList rowHeader, String strRec) {
		String strSQL = "SELECT TRIM(a.part_type), TRIM(b.partdesc), a.amount, NULL, TRIM(a.check_no), TRIM(a.check_type), TRIM(e.check_desc), \n" + 
				"a.check_date, TRIM(a.acct_no), TRIM(a.bank),TRIM(c.bank_name), TRIM(a.branch), TRIM(d.bank_branch_location), TRIM(a.ar_no), \n" +
				"TRIM(a.receipt_type), f.doc_alias, a.receipt_no, a.receipt_type, (case when ar_no is not null then true else false end) \n" +
				"FROM rf_pay_detail a \n" +
				"LEFT JOIN mf_pay_particular b ON b.pay_part_id = a.part_type \n" +
				"LEFT JOIN mf_bank c ON c.bank_id = a.bank \n" +
				"LEFT JOIN mf_bank_branch d ON d.bank_branch_id = a.branch \n" +
				"LEFT JOIN mf_check_type e ON e.check_id = a.check_type \n" +
				"LEFT JOIN (SELECT TRIM(doc_id) as doc_id, TRIM(doc_alias) as doc_alias \n" + 
				"FROM mf_doc_details WHERE doc_id IN ('01', '03', '08')) f on a.receipt_type = f.doc_id \n" +   
				"WHERE a.receipt_no = '"+strRec+"' \n" +
				"ORDER BY pay_detail_id";
		
		System.out.println("");
		System.out.println(strSQL);
		
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(getContentPane(), "No payment details were retrieved.");
		}
	}
}
