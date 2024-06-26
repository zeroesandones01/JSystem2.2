package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import DateChooser._JDateChooser;
import tablemodel.modelReceiptPrinting;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class ReceiptPrinting extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6941612072290729931L;

	static String title = "Receipt Printing";
	Dimension frameSize = new Dimension(800, 500);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JScrollPane scrollReceipts;
	private _JTableMain tblReceipts;
	private modelReceiptPrinting modelReceipts;
	private JList rowheaderCheckHistory;

	private JXPanel panDate;
	private _JDateChooser dteDate; 

	public ReceiptPrinting() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ReceiptPrinting(String title) {
		super(title);
		initGUI();
	}

	public ReceiptPrinting(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(frameSize));
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel(new BorderLayout(3, 3));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				scrollReceipts = new JScrollPane();
				pnlMain.add(scrollReceipts, BorderLayout.CENTER);
				scrollReceipts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollReceipts.setBorder(JTBorderFactory.createTitleBorder("Check History", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					modelReceipts = new modelReceiptPrinting();
					modelReceipts.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == TableModelEvent.DELETE){
								rowheaderCheckHistory.setModel(new DefaultListModel());
							}
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowheaderCheckHistory.getModel()).addElement(modelReceipts.getRowCount());
								//scrollCheckHistory.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelReceipts.getRowCount())));
							}
						}
					});

					tblReceipts = new _JTableMain(modelReceipts);
					scrollReceipts.setViewportView(tblReceipts);
					tblReceipts.hideColumns("Client ID", "PBL ID", "Actual Date");
				}
				{
					rowheaderCheckHistory = tblReceipts.getRowHeader();
					rowheaderCheckHistory.setModel(new DefaultListModel());
					scrollReceipts.setRowHeaderView(rowheaderCheckHistory);
					scrollReceipts.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				//pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					{
						JButton btnGenerate = new JButton("Generate");
						pnlSouth.add(btnGenerate);
						btnGenerate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								new Thread(new Runnable() {
									public void run() {
										displayReceipts(modelReceipts, rowheaderCheckHistory);
									}
								}).run();
							}
						});
					}
					{
						JButton btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
					{
						JButton btnRefresh = new JButton("Refresh");
						pnlSouth.add(btnRefresh);
						btnRefresh.setMnemonic(KeyEvent.VK_R);
						btnRefresh.addActionListener(this);
					}
				}
			}
		}
		{
			panDate = new JXPanel(new BorderLayout(3, 3));
			panDate.setPreferredSize(new Dimension(100, 20));
			{
				{
					dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
					panDate.add(dteDate);
					dteDate.getCalendarButton().setVisible(true);
					dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}	
			}
		}
	}

	private void displayReceipts(modelReceiptPrinting model, JList rowHeader) {

		String dteTran = ""; 
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		
		/*
		if (UserInfo.Branch.equals("10")) {
			int scanOption = JOptionPane.showOptionDialog(getContentPane(), panDate, "Transaction Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

			if (scanOption==JOptionPane.CLOSED_OPTION) {
				try {
					dteTran = (String) sdfTo.format(dteDate.getDate());
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {

				}				
			}	
		} else {
			dteTran = (String) sdfTo.format(FncGlobal.getDateToday());
		}
		 */

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), panDate, "Transaction Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

		if (scanOption==JOptionPane.CLOSED_OPTION) {
			try {
				dteTran = (String) sdfTo.format(dteDate.getDate());
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				dteTran = (String) sdfTo.format(FncGlobal.getDateToday());
			}				
		}	

		FncTables.clearTable(model);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String SQL = "SELECT a.pymnt_type, c.particulars, a.amount,\n" + 
				"  (CASE WHEN a.or_doc_id IS NOT NULL THEN a.or_no ELSE NULL END) as or_no,\n" + 
				"  (CASE WHEN a.pr_doc_id IS NOT NULL THEN COALESCE(a.ar_no, a.or_no) ELSE NULL END) as ar_no, \n" +
				"  (CASE WHEN a.si_doc_id IS NOT NULL THEN a.si_no ELSE NULL END) as si_no, NULL as pfr_no,\n" +
				"  d.doc_alias, a.trans_date, a.actual_date, a.entity_id, " +
				"  (CASE WHEN a.proj_id = '015' and a.pbl_id = '2424' THEN 'SAMANTE, VANESSA ANNE TEODORO' /*WHEN  a.proj_id = '019' and a.pbl_id = '10661' THEN 'ESON, ROWELYN ANTARAN'*/ " + //MODIFIED BY MONIQUE DTD 2023-03-10; 
			    "  ELSE get_client_name(a.entity_id) END) as client_name, " + 																											  // REFER TO DCRF #2500 -- Commented ESON w/ request of Change Civil Status 03-20-2024
				"a.pbl_id, b.description, a.seq_no, a.client_seqno\n" + 
				"FROM rf_payments a\n" + 
				"LEFT JOIN mf_unit_info b ON b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_pay_particular c ON c.pay_part_id = a.pay_part_id\n" + 
				"LEFT JOIN mf_system_doc d ON d.doc_id = (CASE when a.or_doc_id is not null then a.or_doc_id when a.si_doc_id is not null then a.si_doc_id else a.pr_doc_id end) \n" +
				"WHERE a.client_seqno is not null AND TRIM(a.status_id) = 'A' \n"+
				"AND CASE WHEN a.pay_rec_id::INT IN (524785, 524784, 476766, 220865, 256610, 520760, 520759, 521028, 520700, 520701, 495918,256433,220866,237629,256419,256420,256432,495929, 532873) THEN TRUE ELSE (CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10'  AND a.date_created::DATE = '"+dteTran+"'::DATE) WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id = '01'  AND a.trans_date::DATE = '"+dteTran+"'::DATE) ELSE TRUE END) END\n" + //XXX ADDED BY LESTER FOR FILTER OF LOADING OF RECEIPTS
				//" (CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10'  AND a.date_created::DATE = '"+dteTran+"') WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id = '01'  AND a.trans_date::DATE = '"+dteTran+"'::DATE) ELSE TRUE END)\n" + 
				"AND case when a.pay_rec_id::INT IN (476766, 220865, 256610, 520760, 520759, 521028, 520700, 520701, 495918,256433,220866,237629,256419,256420,256432,495929, 532873) THEN TRUE ELSE (CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10'  AND CASE WHEN a.remarks ~*'Direct Deposit' THEN TRUE ELSE a.date_created::DATE = '"+dteTran+"'::DATE END) WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id = '01' AND CASE WHEN a.remarks ~*'Direct Deposit' THEN TRUE ELSE a.date_created::DATE = '"+dteTran+"'::DATE END) ELSE TRUE END) END\n" + //XXX ADDED BY LESTER FOR FILTER OF LOADING OF RECEIPTS
				
				/*"\n" +
				//added by jari sept 2 2022
				"UNION ALL\n"
				+ "\n"
				+ "SELECT a.pymnt_type, c.particulars, a.amount,\n"
				+ "  (CASE WHEN a.or_doc_id IS NOT NULL THEN a.or_no ELSE NULL END) as or_no,\n"
				+ "  (CASE WHEN a.pr_doc_id IS NOT NULL THEN COALESCE(a.ar_no, a.or_no) ELSE NULL END) as ar_no, \n"
				+ "  (CASE WHEN a.si_doc_id IS NOT NULL THEN a.si_no ELSE NULL END) as si_no, NULL as pfr_no,\n"
				+ "  d.doc_alias, a.trans_date, a.actual_date, a.entity_id, get_client_name(a.entity_id), a.pbl_id, b.description, a.seq_no, a.client_seqno\n"
				+ "FROM rf_payments a\n"
				+ "LEFT JOIN mf_unit_info b ON b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n"
				+ "LEFT JOIN mf_pay_particular c ON c.pay_part_id = a.pay_part_id\n"
				+ "LEFT JOIN mf_system_doc d ON d.doc_id = (CASE when a.or_doc_id is not null then a.or_doc_id when a.si_doc_id is not null then a.si_doc_id else a.pr_doc_id end) \n"
				+ "WHERE a.client_seqno is not null AND TRIM(a.status_id) = 'A' \n"
				+ "AND CASE WHEN a.pay_rec_id::INT IN (476766, 220865, 256610, 520760, 520759, 521028, 520700, 520701) THEN TRUE ELSE (CASE WHEN '01' = '10' THEN (a.branch_id = '10'  AND a.trans_date::DATE = '"+dteTran+"') WHEN '01' = '01' THEN (a.branch_id = '01'  AND a.trans_date::DATE = '"+dteTran+"'::DATE) ELSE TRUE END) END\n"
				+ "AND case when a.pay_rec_id::INT IN (476766, 220865, 256610, 520760, 520759, 521028, 520700, 520701) THEN TRUE ELSE (CASE WHEN '01' = '10' THEN (a.branch_id = '10'  AND CASE WHEN a.remarks ~*'Direct Deposit' THEN TRUE ELSE a.trans_date::DATE = '"+dteTran+"' END) WHEN '01' = '01' THEN (a.branch_id = '01' AND CASE WHEN a.remarks ~*'Direct Deposit' THEN TRUE ELSE a.trans_date::DATE = '"+dteTran+"' END) ELSE TRUE END) END\n"
				+ "AND a.pay_part_id = '268'\n"
				//added by jari sept 2 20228*/
				
				"\n"+
				"UNION ALL\n" +  //ADDED BY MONIQUE DTD 2022-09-22; TO REFLECT PAYMENTS OF LATE OR WITH GOOD CHECKS 
				"\n" + 
				"SELECT a.pymnt_type, c.particulars, a.amount,\n" + 
				"  (CASE WHEN a.or_doc_id IS NOT NULL THEN a.or_no ELSE NULL END) as or_no,\n" + 
				"  (CASE WHEN a.pr_doc_id IS NOT NULL THEN COALESCE(a.ar_no, a.or_no) ELSE NULL END) as ar_no, \n" +
				"  (CASE WHEN a.si_doc_id IS NOT NULL THEN a.si_no ELSE NULL END) as si_no, NULL as pfr_no,\n" +
				"  d.doc_alias, a.trans_date, a.actual_date, a.entity_id, get_client_name(a.entity_id), a.pbl_id, b.description, a.seq_no, a.client_seqno\n" + 
				"FROM rf_payments a\n" + 
				"LEFT JOIN mf_unit_info b ON b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_pay_particular c ON c.pay_part_id = a.pay_part_id\n" + 
				"LEFT JOIN mf_system_doc d ON d.doc_id = (CASE when a.or_doc_id is not null then a.or_doc_id when a.si_doc_id is not null then a.si_doc_id else a.pr_doc_id end) \n" +
				"WHERE a.client_seqno is not null AND TRIM(a.status_id) = 'A' AND a.remarks ~* 'LATE OR Issuance for Good Check' AND a.pymnt_type = 'B' \n"+
				"AND CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10'  AND (a.or_date::DATE = '"+dteTran+"' OR a.si_date::DATE = '"+dteTran+"')) WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id in ('01', '1') AND (a.or_date::DATE = '"+dteTran+"'::DATE OR a.si_date::DATE = '"+dteTran+"')) ELSE TRUE END \n" +  //SI DATE ADDED BY MONIQUE DTD 12-27-2022
				
				"\n"+
				"UNION ALL\n" +  //ADDED BY MONIQUE DTD 2023-04-24; TO REFLECT PAYMENTS OF LATE LTS/BOI
				"\n" + 
				"SELECT a.pymnt_type, c.particulars, a.amount,\n" + 
				"  (CASE WHEN a.or_doc_id IS NOT NULL THEN a.or_no ELSE NULL END) as or_no,\n" + 
				"  (CASE WHEN a.pr_doc_id IS NOT NULL THEN COALESCE(a.ar_no, a.or_no) ELSE NULL END) as ar_no, \n" +
				"  (CASE WHEN a.si_doc_id IS NOT NULL THEN a.si_no ELSE NULL END) as si_no, NULL as pfr_no,\n" +
				"  d.doc_alias, a.trans_date, a.actual_date, a.entity_id, get_client_name(a.entity_id), a.pbl_id, b.description, a.seq_no, a.client_seqno\n" + 
				"FROM rf_payments a\n" + 
				"LEFT JOIN mf_unit_info b ON b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_pay_particular c ON c.pay_part_id = a.pay_part_id\n" + 
				"LEFT JOIN mf_system_doc d ON d.doc_id = (CASE when a.or_doc_id is not null then a.or_doc_id when a.si_doc_id is not null then a.si_doc_id else a.pr_doc_id end) \n" +
				"WHERE a.client_seqno is not null AND TRIM(a.status_id) = 'A' AND a.remarks ~ 'Late LTS/BOI' \n"+
				"AND CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10'  AND (a.or_date::DATE = '"+dteTran+"' OR a.si_date::DATE = '"+dteTran+"')) WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id in ('01', '1') AND (a.or_date::DATE = '"+dteTran+"'::DATE OR a.si_date::DATE = '"+dteTran+"')) ELSE TRUE END \n" +  
				
				"\n"+
				"UNION ALL\n" + 
				"\n" + 
				"SELECT (CASE WHEN b.bank IS NULL THEN 'A' ELSE 'B' END), c.particulars, b.amount, NULL as or_no, NULL as ar_no, NULL as si_no, b.receipt_no as pfr_no, d.doc_alias, a.trans_date, a.booking_date,\n" + 
				"  a.entity_id, get_client_name(a.entity_id), a.pbl_id, e.description, a.seq_no, a.client_seqno\n" + 
				"FROM rf_pay_header a\n" + 
				"LEFT JOIN rf_pay_detail b ON b.client_seqno = a.client_seqno\n" + 
				"LEFT JOIN mf_pay_particular c ON c.pay_part_id = b.part_type\n" + 
				"LEFT JOIN mf_system_doc d ON d.doc_id = b.receipt_type\n" + 
				"LEFT JOIN mf_unit_info e ON e.proj_id = a.proj_id AND e.pbl_id = a.pbl_id\n" + 
				"WHERE NOT EXISTS(SELECT client_seqno FROM rf_payments WHERE client_seqno = a.client_seqno) AND b.receipt_type = '08' \n"+
				"AND (CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10' AND a.date_created::DATE = '"+dteTran+"') WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id = '01' AND a.trans_date::DATE = '"+dteTran+"') ELSE TRUE END)\n" + //XXX ADDED BY LESTER FOR FILTER OF LOADING OF RECEIPTS
				"\n" +
				"UNION ALL\r\n" + 
				"\r\n" + 
				"SELECT (CASE WHEN b.bank IS NULL THEN 'A' ELSE 'B' END), c.particulars, b.amount, (CASE WHEN b.receipt_type = '01' then b.receipt_no ELSE NULL END) as or_no, " +
				"(CASE WHEN b.receipt_type = '03'then b.receipt_no ELSE NULL END) as ar_no, (CASE WHEN b.receipt_type = '307' then b.receipt_no ELSE NULL END) as si_no,null as pfr_no, d.doc_alias, a.trans_date, a.booking_date,\r\n" + 
				"  a.entity_id, get_client_name(a.entity_id), a.pbl_id, '' as description, a.seq_no, a.client_seqno\r\n" + 
				"FROM rf_pay_header a\r\n" + 
				"LEFT JOIN rf_pay_detail b ON b.client_seqno = a.client_seqno\r\n" + 
				"LEFT JOIN mf_pay_particular c ON c.pay_part_id = b.part_type\r\n" + 
				"LEFT JOIN mf_system_doc d ON d.doc_id = b.receipt_type\r\n" + 
				"WHERE NOT EXISTS(SELECT client_seqno FROM rf_payments WHERE client_seqno = a.client_seqno) AND b.receipt_type != '08'\r\n " +
				"AND (CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10' AND a.date_created::DATE = '"+dteTran+"') WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id = '01' AND a.date_created::DATE = '"+dteTran+"') ELSE TRUE END) \n"+//XXX ADDED BY LESTER FOR FILTER OF LOADING OF RECEIPTS
				"AND case when exists (SELECT * FROM issued_garbage_fee where client_seqno = a.client_seqno) then a.op_status = 'P' else true end \n"+//XXX ADDED BY JARI CRUZ TO ONLY DISPLAY OP STATUS WITH P
				"\r\n" + 
				"UNION ALL \n" + //FOR HOLDING TRANSACTIONS
				"SELECT (case when b.check_no is null then 'A' else 'B' end) as pmt_type, \r\n" + 
				"d.particulars, a.total_amt_paid, \r\n" + 
				"(case when a.receipt_type = '01' then a.receipt_no else null end) as or_no,\r\n" + 
				"(case when a.receipt_type = '03' then a.receipt_no else null end) as ar_no,\r\n" + 
				"(case when a.receipt_type = '307' then 'SI' else null end) as si_no,\r\n" + 
				"(case when a.receipt_type = '08' then a.receipt_no else null end) as pfr_no,\r\n" + 
				"(case when a.receipt_type = '03' then 'AR' else \r\n" + 
				"	case when a.receipt_type = '01' then 'OR' else 'PFR' end end) as receipt_type , \r\n" + 
				"a.trans_date, a.actual_date, a.entity_id, \r\n" + 
				"get_client_name(a.entity_id), a.pbl_id, c.description, a.seq_no, a.client_seqno\r\n" + 
				"FROM (select * from rf_tra_header where status_id = 'P') a\r\n" + 
				"LEFT JOIN rf_tra_detail b on a.client_seqno = b.client_seqno\r\n" + 
				"LEFT JOIN mf_unit_info c ON a.proj_id = c.proj_id AND a.pbl_id = c.pbl_id\r\n" + 
				"LEFT JOIN mf_pay_particular d ON b.part_id = d.pay_part_id \n" + 
				"WHERE (CASE WHEN '"+UserInfo.Branch+"' = '10' THEN (a.branch_id = '10' AND a.trans_date::DATE = '"+dteTran+"') WHEN '"+UserInfo.Branch+"' = '01' THEN (a.branch_id = '01' AND a.trans_date::DATE = '"+dteTran+"') ELSE TRUE END)\n"+
				"ORDER BY trans_date desc, client_seqno;";

		System.out.printf("displayReceipts : " + SQL);

		pgSelect db = new pgSelect();
		FncSystem.out("Display Receipts", SQL);
		db.select(SQL);

		for(int x=0; x < db.getRowCount(); x++){
			model.addRow(db.getResult()[x]);
		}

		scrollReceipts.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReceipts.getRowCount())));
		tblReceipts.packAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();

		if(action.equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==true){

			if (tblReceipts.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(getContentPane(),"Please select row to preview.","Information",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				int row = tblReceipts.convertRowIndexToModel(tblReceipts.getSelectedRow());

				String or_no = (String) modelReceipts.getValueAt(row, 3);
				String ar_no = (String) modelReceipts.getValueAt(row, 4);
				String si_no = (String) modelReceipts.getValueAt(row, 5);
				String pagibig_or = (String) modelReceipts.getValueAt(row, 6);
				String client_seqno = (String) modelReceipts.getValueAt(row, 15);
				String pymnt_type = (String) modelReceipts.getValueAt(row, 0); 

				if(isLedgerApplied(client_seqno) && isLedgerAppliedCorrect(client_seqno)) {
					System.out.println("Dumaan dito sa Print Receipt");
					if(or_no != null){

						Object[] rcpt_dtl = getReceiptDtls(or_no, client_seqno);
						String credited_ar_no = "", recpt_type = "";
						Double credited_amount = null; 
						try { credited_ar_no = rcpt_dtl[0].toString();} catch (NullPointerException x) { credited_ar_no = ""; }
						try { recpt_type = rcpt_dtl[2].toString();} catch (NullPointerException x) { recpt_type = ""; }
						try { credited_amount = Double.parseDouble(rcpt_dtl[1].toString());} catch (NullPointerException x) { credited_amount = 0.00; }

						Map<String, Object> mapParameters = new HashMap<String, Object>();
						mapParameters.put("client_seqno", client_seqno);
						mapParameters.put("or_no", or_no);
						mapParameters.put("credit_ar_no", credited_ar_no);
						mapParameters.put("recpt_type", recpt_type);
						mapParameters.put("credited_amount", credited_amount);
						mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
						String co_id = FncGlobal.GetString("select co_id from rf_payments a \n" + 
								"where client_seqno = '"+client_seqno+"' and or_no = '"+or_no+"' ");

						//FncReport.generateReport("/Reports/rptORReceipt_EDC.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
						//FncReport.generateReport("/Reports/rptSalesInvoice_VDC.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
						
						if(UserInfo.Branch.equals("10")) {
							FncReport.generateReport("/Reports/rptORReceipt_v2.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);		
						}else {
							if(isRetFeeOnline(client_seqno)) {
							//FncReport.generateReport("/Reports/rptOR_RetFeeOL.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
							//FncReport.generateReport("/Reports/rpt_RetFeeOL_CDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
							}else if(co_id.equals("05")) {
								FncReport.generateReport("/Reports/rptORReceipt_EDC.jasper", "Official Receipt", String.format("OR No.: %s", ar_no), mapParameters);
							}else {
								FncReport.generateReport("/Reports/rptORReceipt.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
								
							}
						}
					}

					if(ar_no != null){	

						Object[] rcpt_dtl = getReceiptDtls(ar_no, client_seqno);
						String credited_ar_no = "", recpt_type = "";
						Double credited_amount = null; 
						try { credited_ar_no = rcpt_dtl[0].toString();} catch (NullPointerException x) { credited_ar_no = ""; }
						try { recpt_type = rcpt_dtl[2].toString();} catch (NullPointerException x) { recpt_type = ""; }
						try { credited_amount = Double.parseDouble(rcpt_dtl[1].toString());} catch (NullPointerException x) { credited_amount = 0.00; }


						Map<String, Object> mapParameters = new HashMap<String, Object>();
						mapParameters.put("client_seqno", client_seqno);
						mapParameters.put("ar_no", ar_no);
						mapParameters.put("credit_ar_no", credited_ar_no);
						mapParameters.put("recpt_type", recpt_type);
						mapParameters.put("credited_amount", credited_amount);
						mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
						mapParameters.put("pymnt_type", pymnt_type);
						
						System.out.printf("recpt_type : " + recpt_type);
						System.out.printf("credited_amount : " + credited_amount);
						/*String co_id = FncGlobal.GetString("select b.co_id from rf_payments a \n" + 
								"left join mf_project b on a.proj_id = b.proj_id\n" + 
								"where a.client_seqno = '"+client_seqno+"'");*/
						String co_id = FncGlobal.GetString("select co_id from rf_pay_header a \n" + 
								"where client_seqno = '"+client_seqno+"'");
						
						
						//FncReport.generateReport("/Reports/rptARReceipt_EDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						
						//FncReport.generateReport("/Reports/rptARReceipt_2.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						//FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						//Added by Erick for migration
						
						 if (isRetFeeOnlineMP(client_seqno)) {
							FncReport.generateReport("/Reports/rptARRetFeeOL_CDC.jasper", "Acknowledgment Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						} else if(co_id.equals("01")) {
							FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						}else if(co_id.equals("02")) {
							FncReport.generateReport("/Reports/rptARReceipt_CDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						}else if(co_id.equals("04")) {
							FncReport.generateReport("/Reports/rptARReceipt_ADC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						}else if(co_id.equals("05")) {
							FncReport.generateReport("/Reports/rptARReceipt_EDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
						}
					}
					//ADDED BY MONIQUE FOR SALES INVOICE DTD 2022-07-11 WITH DCRF#2121
					if(si_no != null){	

						Object[] rcpt_dtl = getSalesInvoiceDtls(si_no, client_seqno);
						String credited_ar_no = "", recpt_type = "";
						Double credited_amount = null; 
						try { credited_ar_no = rcpt_dtl[0].toString();} catch (NullPointerException x) { credited_ar_no = ""; }
						try { recpt_type = rcpt_dtl[2].toString();} catch (NullPointerException x) { recpt_type = ""; }
						try { credited_amount = Double.parseDouble(rcpt_dtl[1].toString());} catch (NullPointerException x) { credited_amount = 0.00; }


						Map<String, Object> mapParameters = new HashMap<String, Object>();
						mapParameters.put("client_seqno", client_seqno);
						mapParameters.put("si_no", si_no);
						mapParameters.put("credit_ar_no", credited_ar_no);
						mapParameters.put("recpt_type", recpt_type);
						mapParameters.put("credited_amount", credited_amount);
						mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
						System.out.printf("recpt_type : " + recpt_type);
						System.out.printf("credited_amount : " + credited_amount);
						String co_id = FncGlobal.GetString("select co_id from rf_pay_header a \n" + 
								"where client_seqno = '"+client_seqno+"'");
						
						if(isRetFeeOnline(client_seqno)) {
							FncReport.generateReport("/Reports/rpt_RetFeeOL_CDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
						} else if(co_id.equals("01") || co_id.equals("04") || co_id.equals("05")){
							FncReport.generateReport("/Reports/rptSalesInvoice_VDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
						}else if(co_id.equals("02")) {
							FncReport.generateReport("/Reports/rptSalesInvoice_CDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
						}
					}

					if(pagibig_or != null){
						System.out.printf("PagIBIG OR #: %s%n", pagibig_or);

						Map<String, Object> mapParameters = new HashMap<String, Object>();
						mapParameters.put("client_seqno", client_seqno);
						mapParameters.put("or_no", pagibig_or);
						mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

						FncReport.generateReport("/Reports/rptPagIBIGReceipt.jasper", "PagIBIG Official Receipt", String.format("PagIBIG OR No.: %s", pagibig_or), mapParameters);
					}
				}else {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Payment application not in order. Contact JST.", "Preview", JOptionPane.WARNING_MESSAGE);
				}

				//System.out.printf("Client Seq. #: %s%n", client_seqno);

				}
			}

		else if(e.getActionCommand().equals("Preview")&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to print receipt.","Warning",JOptionPane.WARNING_MESSAGE); }

		if(action.equals("Refresh")){
			displayReceipts(modelReceipts, rowheaderCheckHistory);
			JOptionPane.showMessageDialog(getContentPane(),"Table refreshed.","Information",JOptionPane.INFORMATION_MESSAGE); 
		}

	}

	/*	Modified by Mann2x; Date Modified: January 18, 2016; "Credited from" remark appears on the receipt because of the OR's AR counterpart;	*/ 
	/*
	public static Object [] getReceiptDtls(String recpt_no) {

		String strSQL = 
				"select receipt_id, amount, " +
				"(case when or_date is not null then 'OR No.' else " +
				"	case when pr_doc_id = '03' then 'AR No. ' else 'PR No. ' end end) " +
				"from rf_payments where or_no = '"+recpt_no+"' and receipt_id is not null order by receipt_id \r\n" ;

		System.out.printf("SQL #1 getReceiptDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	 */

	public static Object [] getReceiptDtls(String recpt_no, String strNo) {
		String strSQL = "select receipt_id, amount, " +
				"(case when or_date is not null then 'OR No.' else " +
				"	case when pr_doc_id = '03' then 'AR No. ' else 'PR No. ' end end) " +
				"from rf_payments where or_no = '"+recpt_no+"' and receipt_id is not null and client_seqno = '"+strNo+"' order by receipt_id \r\n" ;

		System.out.printf("SQL #1 getReceiptDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
		}
	}
	public static Object [] getSalesInvoiceDtls(String recpt_no, String strNo) {
		String strSQL = "select receipt_id, amount " +
				"from rf_payments where si_no = '"+recpt_no+"' and receipt_id is not null and client_seqno = '"+strNo+"' order by receipt_id \r\n" ;

		System.out.printf("SQL #1 getReceiptDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if (db.isNotNull()) {
			return db.getResult()[0];
		} else {
			return null;
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

	private Boolean isRetFeeOnline(String client_seqno) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_payments where client_seqno = '"+client_seqno+"' AND remarks ~*'Ret Fee Online' AND status_id = 'A' AND or_no IS NOT NULL AND pr_doc_id = '03')";
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];	
		}else {
			return false;
		}
	}
	
	private Boolean isRetFeeOnlineMP(String client_seqno) { //ADDED BY MONIQUE DTD 9-14-2022; FOR TAGGED ACCNTS ON RETFEEOL (MULTIPAYMENTS)
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_payments where client_seqno = '"+client_seqno+"' AND /*remarks ~*'Ret Fee Online'*/ pay_part_id in ('218', '246', '247') AND status_id = 'A' AND  pr_doc_id = '03')"; //DCRF #2193
		db.select(SQL);

		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		}else {
			return false;
		}
	}
}
