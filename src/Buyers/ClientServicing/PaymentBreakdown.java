package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;

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

import tablemodel.model_CWTtable;
import tablemodel.model_PaymentBreak;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class PaymentBreakdown extends _JInternalFrame implements ActionListener {
	
	private static final long serialVersionUID = 2418095530588675805L;
	static String title = "Payment Breakdown";
	Dimension frameSize = new Dimension(500, 350);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	
	private JButton btnBreak;
	private JButton btnSave;
	private JButton btnCancel;
	
	private _JLookup txtNo;
	
	private JTextField txtAmount;
	private JTextField txtDate;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtProject;
	private JTextField txtUnit;
	private JTextField txtSeq;

	private JLabel lblReceipt;
	private JLabel lblName;
	
	private model_PaymentBreak modelBreak;
	private _JTableMain tblBreak;
	public static JList rowHeaderBreak;
	private JScrollPane scrollMainCenter;
	private JXPanel Panel1;
	
	private static String strDefaultAmt;
	private static String strDefaultType;
	private static String strResult;
	
	private BigDecimal dbTotal;
	
	public PaymentBreakdown() {
		super(title, true, true, false, true);
		InitGUI();
	}

	public PaymentBreakdown(String title) {
		super(title);
		InitGUI();
	}

	public PaymentBreakdown(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		InitGUI();
	}
	
	public void InitGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPage, BorderLayout.PAGE_START);
			panPage.setPreferredSize(new Dimension(0, 160));
			{
				JXPanel panDetails = new JXPanel(new BorderLayout(5, 5));
				panPage.add(panDetails, BorderLayout.PAGE_START);
				panDetails.setPreferredSize(new Dimension(0, 125));
				panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel panLabel = new JXPanel(new GridLayout(3, 1, 5, 5));
					panDetails.add(panLabel, BorderLayout.LINE_START);
					panLabel.setPreferredSize(new Dimension(100, 0));
					{
						lblReceipt = new JLabel("Receipt No.");
						panLabel.add(lblReceipt, BorderLayout.CENTER);
						lblReceipt.setHorizontalAlignment(JLabel.LEADING);
					}
					{
						lblName = new JLabel("Client Details");
						panLabel.add(lblName, BorderLayout.CENTER);
						lblName.setHorizontalAlignment(JLabel.LEADING);
					}
				}
				JXPanel panReceiptDet = new JXPanel(new GridLayout(3, 1, 5, 5));
				panDetails.add(panReceiptDet, BorderLayout.CENTER);
				panReceiptDet.setPreferredSize(new Dimension(0, 140));
				{
					JXPanel panARnBreak = new JXPanel(new BorderLayout(5, 5));
					panReceiptDet.add(panARnBreak, BorderLayout.CENTER);
					{
						JXPanel panAR = new JXPanel(new BorderLayout(5, 5));
						panARnBreak.add(panAR, BorderLayout.CENTER);
						/*panAR.setPreferredSize(new Dimension(300, 0));*/
						{
							txtNo = new _JLookup("");
							panAR.add(txtNo, BorderLayout.LINE_START);
							txtNo.setHorizontalAlignment(JTextField.CENTER);
							txtNo.setPreferredSize(new Dimension(120, 0));
							txtNo.setReturnColumn(0);
							txtNo.setLookupSQL(ReceiptSQL());
							txtNo.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									
									System.out.println("");
									System.out.println(ReceiptSQL());
									
									if (data != null) {
										try {
											txtAmount.setText((String) data[1].toString());
											dbTotal=(BigDecimal) data[1];
											txtDate.setText((String) data[2].toString());
											txtID.setText((String) data[3].toString());
											txtName.setText((String) data[4].toString());
											txtProject.setText((String) data[5].toString());
											txtUnit.setText((String) data[6].toString());
											txtSeq.setText((String) data[7].toString());
										} catch (NullPointerException ex) {
											
										}
									}
									
									strDefaultType = (String) data[10].toString();
									
									FillGrid(modelBreak, rowHeaderBreak, (String) data[0].toString(), (String) data[8].toString(), (String) data[9].toString(), (String) data[10].toString());
								}
							});
						}
						{
							txtAmount = new JTextField("0.00");
							panAR.add(txtAmount, BorderLayout.CENTER);
							txtAmount.setHorizontalAlignment(JTextField.CENTER);
							txtAmount.setEditable(false);
						}
						{
							txtDate = new JTextField("");
							panAR.add(txtDate, BorderLayout.LINE_END);
							txtDate.setPreferredSize(new Dimension(120, 0));
							txtDate.setHorizontalAlignment(JTextField.CENTER);
							txtDate.setEditable(false);
						}
						/*
						JXPanel panBreak = new JXPanel(new BorderLayout(5, 5));
						panARnBreak.add(panBreak, BorderLayout.CENTER);
						{
							btnBreak = new JButton("Break");
							panBreak.add(btnBreak, BorderLayout.CENTER);
							btnBreak.addActionListener(this);
							btnBreak.setActionCommand("Break");
							btnBreak.setEnabled(false);
						}
						*/	
					}
				}
				{
					JXPanel panName = new JXPanel(new BorderLayout(5, 5));
					panReceiptDet.add(panName, BorderLayout.CENTER);
					{
						txtID = new JTextField("");
						panName.add(txtID, BorderLayout.LINE_START);
						txtID.setHorizontalAlignment(JTextField.CENTER);
						txtID.setPreferredSize(new Dimension(120, 0));
						txtID.setEditable(false);
					}
					{
						txtName = new JTextField("");
						panName.add(txtName, BorderLayout.CENTER);
						txtName.setHorizontalAlignment(JTextField.CENTER);
						txtName.setEditable(false);						
					}
				}
				{
					JXPanel panProjectUnit = new JXPanel(new BorderLayout(5, 5));
					panReceiptDet.add(panProjectUnit, BorderLayout.CENTER);
					{
						txtProject = new JTextField("");
						panProjectUnit.add(txtProject, BorderLayout.LINE_START);
						txtProject.setHorizontalAlignment(JTextField.CENTER);
						txtProject.setPreferredSize(new Dimension(120, 0));
						txtProject.setEditable(false);
					}
					{
						txtUnit = new JTextField("");
						panProjectUnit.add(txtUnit, BorderLayout.CENTER);
						txtUnit.setHorizontalAlignment(JTextField.CENTER);
						txtUnit.setEditable(false);
					}
					{
						txtSeq = new JTextField("");
						panProjectUnit.add(txtSeq, BorderLayout.LINE_END);
						txtSeq.setHorizontalAlignment(JTextField.CENTER);
						txtSeq.setPreferredSize(new Dimension(90, 0));
						txtSeq.setEditable(false);
					}
				}
				JXPanel panButton = new JXPanel(new BorderLayout(0, 0));
				panPage.add(panButton, BorderLayout.CENTER);
				{
					btnBreak = new JButton("Break");
					panPage.add(btnBreak, BorderLayout.CENTER);
					btnBreak.addActionListener(this);
					btnBreak.setActionCommand("Break");
					btnBreak.setEnabled(false);
				}
			}
			JXPanel panMid = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panMid, BorderLayout.CENTER);
			{
				CreatePages();
				panMid.add(Panel1);
			}
			JXPanel panEnd = new JXPanel(new GridLayout(1, 2, 5, 5));
			panMain.add(panEnd, BorderLayout.PAGE_END);
			panEnd.setPreferredSize(new Dimension(0, 30));
			{
				btnSave = new JButton("Save");
				panEnd.add(btnSave, BorderLayout.CENTER);
				btnSave.addActionListener(this);
				btnSave.setActionCommand("Save");
				btnSave.setEnabled(false);
			}
			{
				btnCancel = new JButton("Cancel");
				panEnd.add(btnCancel, BorderLayout.CENTER);
				btnCancel.addActionListener(this);
				btnCancel.setActionCommand("Cancel");
				btnCancel.setEnabled(false);
			}
			
			button_state(true);
		}
	}
	
	private static String ReceiptSQL() {
		return "SELECT A.receipt_no, A.total_amt_paid, A.trans_date::DATE, A.entity_id, B.entity_name as name, C.proj_alias as project, D.description as unit, \n" +
			   "E.seq_no, A.tra_header_id, A.client_seqno, 'TRA' as Table \n" +
			   "FROM rf_tra_header a \n" +
			   "INNER JOIN rf_entity B ON A.entity_id = B.entity_id \n" + 
			   "LEFT JOIN mf_project C ON A.proj_id = C.proj_id \n" +
			   "LEFT JOIN mf_unit_info D ON A.proj_id = D.proj_id AND A.pbl_id = D.pbl_id \n" + 
			   "LEFT JOIN rf_sold_unit E ON A.entity_id = B.entity_id AND A.proj_id = E.projcode AND A.pbl_id = E.pbl_id AND E.status_id = 'A' \n" + 
			   "WHERE A.status_id != 'I' and A.credit_date IS NULL \n" +
			   "AND NOT EXISTS(SELECT * FROM rf_pay_header WHERE entity_id = A.entity_id AND proj_id = A.proj_id AND pbl_id = A.pbl_id AND seq_no = A.seq_no and status_id!= 'I') \n" +
			   "AND NOT EXISTS(SELECT * FROM rf_sold_unit WHERE entity_id = A.entity_id AND projcode = A.proj_id AND pbl_id = A.pbl_id AND seq_no = A.seq_no) \n" +
			   "AND NOT EXISTS(SELECT * FROM rf_payments WHERE receipt_id = receipt_no  and status_id!= 'I') \n" +
			   "AND NULLIF(total_amt_paid, 0.00) IS NOT NULL AND tran_type != '4' AND A.receipt_no is not null \n" +
			   "AND A.receipt_no not in (select receipt_id from rf_payments where status_id != 'I' and receipt_id is not null) \n" +
			   "/*UNION ALL \n" +
			   "SELECT A.or_no, A.amount, A.trans_date::DATE, A.entity_id, B.entity_name as name, C.proj_alias as project, D.description as unit, \n" + 
			   "E.seq_no, A.pay_rec_id, A.client_seqno, 'PAY' as Table \n" +
			   "from (select * from rf_payments where status_id != 'I') a \n" +
			   "INNER JOIN rf_entity B ON A.entity_id = B.entity_id \n" +
			   "LEFT JOIN mf_project C ON A.proj_id = C.proj_id \n" +
			   "LEFT JOIN mf_unit_info D ON A.proj_id = D.proj_id AND A.pbl_id = D.pbl_id \n" + 
			   "LEFT JOIN rf_sold_unit E ON A.entity_id = B.entity_id AND A.proj_id = E.projcode AND A.pbl_id = E.pbl_id AND E.status_id = 'A' \n" + 
			   "where trim((case when A.remarks is null then '' else A.remarks end)) !~* 'credit of payment' \n" +
			   "AND A.or_no not in (select receipt_id from rf_payments where status_id != 'I' and receipt_id is not null)*/";
	}
	
	private void button_state(Boolean blnDo) {
		btnBreak.setEnabled(blnDo);
		btnSave.setEnabled(!blnDo);
		btnCancel.setEnabled(!blnDo);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Break")) {
			button_state(false);
			
			Boolean blnWith = false;
			for (int x = 0; x < tblBreak.getRowCount(); x++) {
				if (tblBreak.getValueAt(x, 3).equals("A")) {
					blnWith = true;
				}
			}
			
			blnWith = true;
			if (!blnWith) {
				JOptionPane.showMessageDialog(getContentPane(), "This payment has already been credited.");
			} else {
				BigDecimal bd = new BigDecimal("0.00");
				
				//strDefaultAmt = tblBreak.getValueAt(0, 2).toString();
				
				modelBreak.addRow(new Object[] {
					modelBreak.getValueAt(0, 0), 
					modelBreak.getValueAt(0, 1),
					bd, 
					modelBreak.getValueAt(0, 3), 
					modelBreak.getValueAt(0, 4), 
					modelBreak.getValueAt(0, 5), 
				});
			}
		} else if (e.getActionCommand().equals("Save")) {
			String strSQL = "";
			pgUpdate dbExec_1 = new pgUpdate();
			pgUpdate dbExec_2 = new pgUpdate();
			pgUpdate dbExec_3 = new pgUpdate();
			
			/**	Check Amounts	**/
			System.out.println("");
			System.out.println("Rowcount: " + modelBreak.getRowCount());
			
			for (int x = 0; x < modelBreak.getRowCount(); x++) {				
				BigDecimal db = new BigDecimal(modelBreak.getValueAt(x, 2).toString());
				
				System.out.println("");
				System.out.println("modelBreak.getValueAt(x, 2).toString(): " + modelBreak.getValueAt(x, 2).toString());
				System.out.println("db: " + db.toString());
				System.out.println("db.compareTo(new BigDecimal(0): " + db.compareTo(new BigDecimal("0")));
				
				if (db.compareTo(new BigDecimal("0"))==-1||db.compareTo(new BigDecimal("0"))==0) {
					JOptionPane.showMessageDialog(getContentPane(), "Row " + (x + 1) + " amount is not set. Please\n");
				}
				
				/**		Insert Header			**/
				dbExec_1 = new pgUpdate();
				strSQL = "INSERT INTO rf_tra_header (client_seqno, branch_id, trans_date, actual_date, entity_id, proj_id, pbl_id, seq_no, model_id, sellingprice, total_amt_paid, selling_agent, \n" + 
						"dept_code, status_id, receipt_type, receipt_no, trans_to, hold_until, remarks, tran_type, forfeit_date, co_id, op_user_id, credit_date, unit_id, extension_date) \n" +
						"SELECT client_seqno, branch_id, trans_date, actual_date, entity_id, proj_id, pbl_id, seq_no, model_id, sellingprice, '"+modelBreak.getValueAt(x, 2)+"'::numeric(19, 2), selling_agent, \n" +
						"dept_code, status_id, receipt_type, receipt_no, trans_to, hold_until, remarks, tran_type, forfeit_date, co_id, op_user_id, credit_date, unit_id, extension_date \n" +
						"FROM rf_tra_header x \n" +
						"WHERE x.tra_header_id::int = '"+modelBreak.getValueAt(0, 4)+"'::int";
				dbExec_1.executeUpdate(strSQL, false);
				dbExec_1.commit();
				
				System.out.println("");
				System.out.println("strSQL: " + strSQL);
				
				/**		Get new Header ID		**/
				Integer intRecID = FncGlobal.GetInteger("select x.tra_header_id::int from rf_tra_header x where x.receipt_no = '"+txtNo.getValue()+"' and total_amt_paid::numeric(19, 2) = '"+modelBreak.getValueAt(x, 2).toString()+"'");
				System.out.println("");
				System.out.println("select x.tra_header_id::int from rf_tra_header x where x.receipt_no = '"+txtNo.getValue()+"' and total_amt_paid::numeric(19, 2) = '"+modelBreak.getValueAt(x, 2).toString()+"'");
				
				/**		Insert Detail ID		**/
				dbExec_1 = new pgUpdate();
				strSQL = "insert into rf_tra_detail (header_id, tran_date, actual_date, receipt_type, receipt_no, part_id, bank, branch, acct_no, check_no, check_date, \n" + 
						"amount, status_id, issued_cashier, old_header_id, client_seqno, bank_remit, refund, forfeit, remarks, refund_no, branch_id, checkstat_id, rplf_no, date_rlsd) \n" +
						"select "+intRecID+"::bigint, tran_date, actual_date, receipt_type, receipt_no, part_id, bank, branch, acct_no, check_no, check_date, '"+modelBreak.getValueAt(x, 2)+"'::numeric(19, 2), \n" + 
						"status_id, issued_cashier, old_header_id, client_seqno, bank_remit, refund, forfeit, remarks, refund_no, branch_id, checkstat_id, rplf_no, date_rlsd \n" +
						"from rf_tra_detail x \n" +
						"where x.header_id::int = '"+modelBreak.getValueAt(0, 4)+"'::int";
				dbExec_1.executeUpdate(strSQL, false);
				dbExec_1.commit();
				
				System.out.println("");
				System.out.println("strSQL: " + strSQL);
			}
			
			/**		Deactivate Header		**/
			strSQL = "update rf_tra_header set status_id = 'I' where tra_header_id::int = '"+modelBreak.getValueAt(0, 4)+"'::int";
			dbExec_1 = new pgUpdate();
			dbExec_1.executeUpdate(strSQL, false);
			dbExec_1.commit();
			
			System.out.println("");
			System.out.println("strSQL: " + strSQL);
			
			strSQL = "update rf_tra_detail set status_id = 'I' where header_id::int = '"+modelBreak.getValueAt(0, 4)+"'::int";
			dbExec_1 = new pgUpdate();
			dbExec_1.executeUpdate(strSQL, false);
			dbExec_1.commit();
			
			System.out.println("");
			System.out.println("strSQL: " + strSQL);

			/*
			String SQL = "";
			String SQL_1 = "";
			
			pgUpdate db_update1 = new pgUpdate();
			pgUpdate db_update2 = new pgUpdate();
			
			String strReceipt = (String) tblBreak.getValueAt(0, 0).toString();
			String strHeader = (String) tblBreak.getValueAt(0, 4).toString();
			String strSequence = (String) tblBreak.getValueAt(0, 5).toString();
			
			String strID = "";
			
			try {
				strID = (String) tblBreak.getValueAt(0, 6).toString();
			} catch (NullPointerException ex) {
				strID = "";
			}
			
			String strRemaining = (String) tblBreak.getValueAt(0, 2).toString();
			
			if (strDefaultType.equals("TRA")) {
				SQL = "INSERT INTO rf_tra_detail (header_id, tran_date, actual_date, receipt_type, receipt_no, part_id, \n" + 
				 	  "bank, branch, acct_no, check_no, check_date, amount, status_id, \n" +
				 	  "issued_cashier, old_header_id, client_seqno, bank_remit, refund, forfeit, \n" +
				 	  "remarks, refund_no, branch_id, checkstat_id, rplf_no, date_rlsd)\n" +
				 	  "SELECT header_id, tran_date, actual_date, receipt_type, receipt_no, part_id, \n" + 
				 	  "bank, branch, acct_no, check_no, check_date, "+strResult+"::numeric as amount, status_id, \n" +
				 	  "issued_cashier, old_header_id, client_seqno, bank_remit, refund, forfeit, \n" +
				 	  "remarks, refund_no, branch_id, checkstat_id, rplf_no, date_rlsd \n" +
				 	  "FROM rf_tra_detail \n" +
				 	  "WHERE receipt_no = '"+strReceipt+"' AND header_id::INT = '"+strHeader+"'::INT AND client_seqno = '"+strSequence+"' \n" + 
				 	  "AND amount::DECIMAL = '"+strDefaultAmt+"'::DECIMAL LIMIT 1";

				SQL_1 = "UPDATE rf_tra_detail SET amount = '"+strRemaining+"'::NUMERIC WHERE tra_detail_id::INT = '"+strID+"'::INT";
			} else {
				SQL = "INSERT INTO rf_payments (entity_id, proj_id, pbl_id, seq_no, actual_date, trans_date, pay_part_id, pymnt_type, \n" +
					  "bank_id, bank_branch_id, amount, acct_no, check_no, check_date, check_stat_id, bounce_reason_id, \n" +
					  "or_no, or_date, ar_no, brstn, request_no, applied_amt, cancelled, remarks, branch_id, post_date, \n" +
					  "client_seqno, or_doc_id, pr_doc_id, status_id, wdraw_stat, date_wdrawn, wdraw_no, wdraw_reason, \n" +
					  "repl_wdraw_by, date_remitted, remit_batch, reversed, pay_rec_id, check_type, receipt_id, \n" +
					  "co_id, unit_id, total_ar_amt, created_by, date_created, refund_date) \n" +
					  "SELECT entity_id, proj_id, pbl_id, seq_no, actual_date, trans_date, pay_part_id, pymnt_type, \n" + 
					  "bank_id, bank_branch_id, "+strResult+"::numeric, acct_no, check_no, check_date, check_stat_id, bounce_reason_id, \n" + 
					  "or_no, or_date, ar_no, brstn, request_no, applied_amt, cancelled, remarks, branch_id, post_date, \n" +
					  "client_seqno, or_doc_id, pr_doc_id, status_id, wdraw_stat, date_wdrawn, wdraw_no, wdraw_reason, \n" +
					  "repl_wdraw_by, date_remitted, remit_batch, reversed, nextval('rf_payments_rec_id_seq'), check_type, \n" +
					  "receipt_id, co_id, unit_id, total_ar_amt, created_by, date_created, refund_date \n" +
					  "FROM rf_payments \n" +
					  "WHERE pay_rec_id::INT = '"+strHeader+"'::INT LIMIT 1";
				
				SQL_1 = "UPDATE rf_payments SET amount = '"+strRemaining+"'::numeric WHERE pay_rec_id::INT = '"+strHeader+"'::INT";
			}
			
			db_update1.executeUpdate(SQL, false);
			db_update1.commit();
			
			db_update2.executeUpdate(SQL_1, false);
			db_update2.commit();
			
			System.out.println("");
			System.out.println(SQL);
			
			System.out.println("");
			System.out.println(SQL_1);
			
			FillGrid(modelBreak, rowHeaderBreak, strReceipt, strHeader, strSequence, strDefaultType);
			
			button_state(true);
			*/
		} else if (e.getActionCommand().equals("Cancel")) {
			button_state(true);
		}
	}
	
	private void CreatePages() {
		Panel1 = new JXPanel(new GridLayout(1, 1, 0, 0));
		Panel1.setOpaque(isOpaque());
		{
			scrollMainCenter = new JScrollPane();
			Panel1.add(scrollMainCenter, BorderLayout.CENTER);
			scrollMainCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMainCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			{
				modelBreak = new model_PaymentBreak();
				tblBreak = new _JTableMain(modelBreak);
				tblBreak.addKeyListener(new KeyListener() {
					public void keyTyped(KeyEvent arg0) {
						System.out.println("");
						System.out.println("keyTyped");
						System.out.println(tblBreak.getValueAt(tblBreak.getSelectedRow(), 2).toString());

						BigDecimal bd = new BigDecimal((String) tblBreak.getValueAt(tblBreak.getRowCount() - 1, 2).toString());
						Recompute(bd);
					}

					public void keyReleased(KeyEvent arg0) {
						System.out.println("");
						System.out.println("keyReleased");
						System.out.println(tblBreak.getValueAt(tblBreak.getRowCount() - 1, 2).toString());
						
						BigDecimal bd = new BigDecimal((String) tblBreak.getValueAt(tblBreak.getRowCount() - 1, 2).toString());
						Recompute(bd);
					}

					public void keyPressed(KeyEvent arg0) {
						System.out.println("");
						System.out.println("keyPressed");
						System.out.println(tblBreak.getValueAt(tblBreak.getSelectedRow(), 2));

						BigDecimal bd = new BigDecimal((String) tblBreak.getValueAt(tblBreak.getRowCount() - 1, 2).toString());
						Recompute(bd);
					}
				});
			}
			rowHeaderBreak = tblBreak.getRowHeader();
			scrollMainCenter.setViewportView(tblBreak);

			tblBreak.getColumnModel().getColumn(0).setPreferredWidth(100);
			tblBreak.getColumnModel().getColumn(1).setPreferredWidth(125);
			tblBreak.getColumnModel().getColumn(2).setPreferredWidth(120);
			tblBreak.getColumnModel().getColumn(3).setPreferredWidth(75);
			tblBreak.getColumnModel().getColumn(4).setPreferredWidth(75);
			tblBreak.getColumnModel().getColumn(5).setPreferredWidth(100);
		}
		{
			rowHeaderBreak = tblBreak.getRowHeader();
			rowHeaderBreak.setModel(new DefaultListModel());
			scrollMainCenter.setRowHeaderView(rowHeaderBreak);
			scrollMainCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
		}
	}
	
	public static void FillGrid(DefaultTableModel modelMain, JList rowHeader, String strReceipt, String strHeader, String strSeq, String strType) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String SQL = "";
		
		if (strType.equals("TRA")) {
			SQL = "SELECT X.receipt_no, Y.particulars, X.amount, \n" +
				  "(CASE WHEN X.remarks ~'Credited' THEN 'True' ELSE 'False' END) as Credited, \n" + 
				  "X.header_id as RecordID, X.client_seqno, X.tra_detail_id \n" +
				  "FROM rf_tra_detail X \n" +
				  "LEFT JOIN mf_pay_particular Y ON X.part_id = Y.pay_part_id \n" +
				  "WHERE COALESCE(X.receipt_no, '') = '"+strReceipt+"' AND X.header_id = '"+strHeader+"' AND X.client_seqno = '"+strSeq+"' \n" +
				  "ORDER BY header_id, X.tra_detail_id";	
		} else {
			SQL = "SELECT X.or_no, Y.particulars, X.amount, \n" +
				  "(CASE WHEN X.remarks ~'Credited' THEN 'True' ELSE 'False' END) as Credited, \n" + 
				  "X.pay_rec_id as RecordID, X.client_seqno \n" +
				  "FROM rf_payments X \n" +
				  "LEFT JOIN mf_pay_particular Y ON X.pay_part_id = Y.pay_part_id \n" +
				  "WHERE COALESCE(or_no, '') = '"+strReceipt+"' /*AND X.pay_rec_id = '"+strHeader+"' */AND X.client_seqno = '"+strSeq+"' \n" +
				  "ORDER BY X.pay_rec_id";
		}
		
		System.out.println("");
		System.out.println(SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	private void Recompute(BigDecimal dbAmt) {
		System.out.println("");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("dbAmt: " + dbAmt);
		System.out.println("Recomputing..");
		
		System.out.println("");
		System.out.println("dbTotal: " + dbTotal);
		System.out.println("dbAmt.subtract(dbTotal)" + dbAmt.subtract(dbTotal));
		
		strResult = dbAmt.toString();
		tblBreak.setValueAt((BigDecimal) dbTotal.subtract(dbAmt), 0, 2);
	}
}
