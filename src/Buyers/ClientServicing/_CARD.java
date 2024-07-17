package Buyers.ClientServicing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import tablemodel.modelCARD_AccountStatus;
import tablemodel.modelCARD_BankAccounts;
import tablemodel.modelCARD_ChangeDueDate;
import tablemodel.modelCARD_CheckHistory;
import tablemodel.modelCARD_ClientRequestHistory;
import tablemodel.modelCARD_CreditOfPayment;
import tablemodel.modelCARD_DocFindings;
import tablemodel.modelCARD_DocsHistory;
import tablemodel.modelCARD_PrintedDocuments;
import tablemodel.modelCARD_RefundOfPayment;
import tablemodel.modelCARD_ReqDocDetails;
import tablemodel.modelCARD_ReqDocPurpose;
import tablemodel.modelCARD_ReqDocStatus;
import tablemodel.modelCARD_RequestedDocs;
import tablemodel.modelCARD_ReservationDocs;
import tablemodel.modelCARD_ReservationDocuments;
import tablemodel.modelCARD_Ledger;
import tablemodel.modelCARD_Fire;
import tablemodel.modelCARD_LOG_Details;
import tablemodel.modelCARD_Notices;
import tablemodel.modelCARD_Payments;
import tablemodel.modelCARD_PmtsWaived;
import tablemodel.modelCARD_Schedule;
import tablemodel.modelClientFollowup;
import tablemodel.modelConnectionList;
import tablemodel.modelDocStatus;
import tablemodel.modelHouseConstructedHistory;
import tablemodel.modelTOverMoveIn;
import tablemodel.modelTctTaxdec;
import tablemodel.modelUnitStatus;
import tablemodel.model_HouseRepair;
import tablemodel.model_accomplishment;
import tablemodel.model_pcost;
import tablemodel.model_pcost_CARD;
import tablemodel.model_tcost;
import tablemodel.modelCARD_Dues;
import tablemodel.modelCARD_MRI;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup._JLookup;
import components._JXTextField;

/**
 * @author Alvin Gonzales
 */
public class _CARD {
	
	//Changes in change branch

	public static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);

	public static String sqlClients() {
		return "SELECT * FROM jsearch2 \n";
		/*return "SELECT * FROM jsearch " ;
			  // "union all (select entity_id, upper(entity_name), '', '' , null, '', '', '' from rf_entity)";*/	
	}

	public static String sqlClients2(){
		return "SELECT *, '' FROM jsearch \n"+
				"union all (select entity_id, upper(entity_name), '', '' , null, '', '015', 'TERRAVERDE RESIDENCES', '' from rf_entity where entity_id IN ('4270585379', '8785068676', '3252083086', '6731932436', '3746524839', '7704625972')) \n"+ //COMMENTED BY MONIQUE DTD 2023-07-14 --uncomment by lester 2023-07-31 for ahppy well payments
				"union all\n" + 
				"SELECT *, 'itsreal' as server FROM jsearch_itsreal ";
	}
	//Done
	public static Object[] displayClientInformation(String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		String SQL = "select * from view_card_client_details('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")"; 
		/*
		String SQL = "SELECT " +
				"TRIM(a.entity_id) AS client_id, " +
				"get_client_coapp(a.entity_id) AS client_name, " +
				"TRIM(a.projcode) AS proj_id, " +
				"TRIM(b.proj_name) AS proj_name, " +
				"TRIM(a.pbl_id) AS pbl_id,\n" + 
				//"	TRIM(c.description) AS description, " +
				"TRIM(get_merge_unit_desc_v3(a.entity_id, a.projcode, a.pbl_id, a.seq_no)) as description, \n"+
				"a.seq_no, " +
				"TRIM(a.model_id) AS model_id, " +
				//"TRIM(get_merge_model_desc(c.unit_id)) as model_desc, \n"+
				//"TRIM(get_card_model_desc(c.unit_id, false)) as model_desc, \n"+
				"TRIM(get_card_model_desc_v2(a.entity_id, c.unit_id, a.seq_no, false)) as model_desc, \n"+
				//"TRIM(d.model_desc) AS model_desc, " +
				//"FORMAT('%s / %s', d.model_desc, (SELECT hse_color from rf_marketing_scheme_pricelist where proj_id = '"+proj_id+"' and phase = c.phase and block = c.block and lot = c.lot order by version_no desc limit 1)) as model_desc ,"+
				"TRIM(a.buyertype) AS buyertype_id,\n" + 
				"	TRIM(e.type_card_display) AS buyertype_name, " +
				"TRIM(a.pmt_scheme_id) AS pmt_scheme_id, " +
				"TRIM(f.pmt_scheme_desc) AS pmt_scheme_desc, " +
				"TRIM(a.currentstatus) AS status_id,\n" + 
				"	TRIM(g.status_desc) AS status_desc, " +
				"a.sellingprice AS selling_price,\n" + 
				"	(CASE WHEN e.type_group_id = '04' THEN h.disc_amount WHEN e.type_group_id = '05' THEN o.disc_amount ELSE i.disc_amount END) AS disc_amount,\n" + 
				"	(CASE WHEN e.type_group_id = '04' THEN h.dp WHEN e.type_group_id = '05' THEN COALESCE(NULLIF(o.dp_equity_15pct, 0), o.dp) ELSE i.dp END) AS dp_equity,\n" + 
				"	(CASE WHEN e.type_group_id = '04' THEN h.loanable_amount WHEN e.type_group_id = '05' THEN COALESCE(NULLIF(o.total_loanable_85pct, 0), o.loanable_amount) ELSE (n.net_sprice - i.dp) END) AS loanable_amount,\n" + 
				//"	(CASE WHEN e.type_group_id = '04' THEN h.loanable_amount ELSE (a.sellingprice - i.dp) END) -\n" + 
				"	(CASE WHEN e.type_group_id = '05' THEN o.disc_tcp_85pct ELSE (n.net_sprice - (SELECT COALESCE(sum(amount), 0)\n" + 
				"          FROM rf_client_ledger\n" + 
				"          WHERE entity_id = '"+ entity_id +"' AND proj_id = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"' AND seq_no = "+ seq_no +" AND status_id = 'A'\n" + 
				"          AND part_id in ('002', '013')\n" + 
				"          AND status_id = 'A')) END) AS balance,\n" +

				"        (CASE WHEN e.type_group_id = '04' THEN NULL ELSE i.dp_rate END) AS dp_rate,\n" + 
				"        (CASE WHEN e.type_group_id = '04' THEN h.dp_term WHEN e.type_group_id = '05' THEN o.dp_term ELSE i.dp_term END) AS dp_term,\n" + 
				"        (CASE WHEN e.type_group_id = '04' THEN h.total_ma WHEN e.type_group_id = '05' THEN o.total_ma ELSE i.total_ma END) AS ma_amount,\n" + 
				"        (CASE WHEN e.type_group_id = '04' THEN NULL ELSE (100 - i.dp_rate) END) AS ma_rate,\n" + 
				"        (CASE WHEN e.type_group_id = '04' THEN h.term WHEN e.type_group_id = '05' THEN 1 ELSE i.ma_term END) AS ma_term,\n" + 
				"        e.type_group_id, a.sellingagent as agent_id, get_client_name(a.sellingagent) as agent_name,\n" + 
				//"        (SELECT TRIM(dept_name) as sales_group FROM mf_department WHERE dept_code IN (SELECT TRIM(dept_id) FROM mf_sellingagent_info WHERE agent_id = a.sellingagent) AND status_id = 'A'),\n" +
				"        (SELECT TRIM(dept_name) as sales_group FROM mf_department WHERE dept_code = a.mktgarm),\n" +
				"        (CASE WHEN e.type_group_id = '04' THEN ROUND((h.comp_factor * 1000)::NUMERIC, 5) WHEN e.type_group_id = '05' THEN ROUND((o.comp_factor * 1000)::NUMERIC, 5) ELSE ROUND((i.comp_factor * 1000)::NUMERIC, 5)END)::VARCHAR AS comp_factor\n,\n" + 
				"        a.sellingagent, FORMAT('%s (%s)', get_client_name(a.sellingagent), s.position_abbv), COALESCE(l.dept_alias, 'N/A'), COALESCE(get_employee_name(l.dept_head_code), 'N/A'),\n" + 
				"        get_card_payment_stage(a.entity_id, a.projcode, a.pbl_id, a.seq_no),\n" + 
				//"        (CASE WHEN e.type_group_id = '04' THEN ((h.dp / h.dp_term) + h.proc_fee) WHEN e.type_group_id = '05' THEN ((o.dp / o.dp_term) + o.proc_fee) ELSE (i.dp / i.dp_term) END) AS dp_amount,\n" + 
				"		 get_card_dp_amount(a.entity_id, a.projcode ,a.pbl_id, a.seq_no) as dp_amount, \n"+
				"        (CASE WHEN e.type_group_id = '04' THEN h.int_rate WHEN e.type_group_id = '05' THEN o.int_rate ELSE i.int_rate END) AS int_rate, get_merge_lot_area(c.unit_id), get_merge_floor_area(c.unit_id), c.house_constructed, t.toclient,\n" +
				"	c.ntc as ntc,\n" + 
				//"   (select hse_color from rf_marketing_scheme_pricelist where proj_id = a.projcode and phase = c.phase and block = c.block and lot = c.lot and status_id = 'A' order by version_no desc limit 1), \n"+ 
				"		 get_merge_house_color_v2(a.entity_id, c.unit_id, a.seq_no), \n"+
				"   get_card_pmt_status(a.entity_id, a.projcode, a.pbl_id, a.seq_no), is_with_merge_unit(c.unit_id), \n"+
				"   (p.ntp_no||(case when qqq.ntp_no is null then '' else ' | Taken Over '||qqq.ntp_no end)) as ntp_no, coalesce(upper(q3.entity_name),''), q.ntp_date, p.remarks, q.contract_no, \n"+
				"(CASE WHEN is_with_merge_unit(c.unit_id) then FORMAT('%s / %s',p.work_desc, (select work_desc from co_ntp_detail x where x.pbl_id = (SELECT oth_pbl_id from hs_sold_other_lots where entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no and status_id = 'A') and exists (select * from co_ntp_header where co_id = '02' and ntp_no = x.ntp_no AND proj_id = a.projcode AND ntp_type_id = '02')) ) else p.work_desc end), \n" +

				//"coalesce(to_char(q.start_date, 'MM/dd/yyyy'),''), \n" +
				//"coalesce(to_char(q.finish_date, 'MM/dd/yyyy'),''), \n" +

				"coalesce(to_char((select x.start_date from co_ntp_header x where x.status_id = 'A' and ntp_type_id = '02' " +
				"	and x.ntp_no in (select y.ntp_no from co_ntp_detail y where y.pbl_id = a.pbl_id and y.status_id = 'A') order by x.ntp_date::date desc limit 1), 'MM/dd/yyyy'),''), \n" +
				"coalesce(to_char((select x.finish_date from co_ntp_header x where x.status_id = 'A' and ntp_type_id = '02' " +
				"	and x.ntp_no in (select y.ntp_no from co_ntp_detail y where y.pbl_id = a.pbl_id and y.status_id = 'A') order by x.ntp_date::date desc limit 1), 'MM/dd/yyyy'),''), \n" +


				"TRIM(get_card_model_desc_v2(a.entity_id, c.unit_id, a.seq_no ,true)) as model_desc2, \n"+

				"	get_tech_desc(c.unit_id), \n" +
				"	r.doc_no, r.mother_doc_no, to_char(r.date_created, 'MM/dd/yyyy'), r.facilities, r.serial_no, r.book_no, r.page_no, upper(coalesce((case when q1.entity_name is null then q2.entity_name else q1.entity_name end),'')), \n" + // ADDED BY JESSA HERRERA 2017-05-15
				//"(CASE WHEN get_group_id(a.buyertype) = '05' THEN (get_loan_availed(a.entity_id, a.pbl_id, a.seq_no, a.projcode) - (a.sellingprice - get_dp_amount(a.entity_id, a.pbl_id, a.seq_no, a.projcode))) ELSE 0.00 END) as os_misc_fee_balance\n" + 
				"(CASE WHEN e.type_group_id = '05' THEN COALESCE(NULLIF(o.misc_fee_85pct, 0), ROUND((o.proc_fee * .85), -3)) ELSE 0.00 END) as os_misc_fee_balance, \n" + 
				"(CASE WHEN e.type_group_id = '05' THEN COALESCE(NULLIF(o.misc_fee_15pct, 0), (o.proc_fee - o.misc_fee_85pct)) else 0.00 END) as misc_fee_15pct, \n" + 
				"(CASE WHEN e.type_group_id = '05' THEN COALESCE(NULLIF(o.cash_outlay_15pct, 0.00), (o.misc_fee_15pct + o.dp_equity_15pct)) ELSE 0.00 END) as cash_outlay_15pct, \n"+
				"(CASE WHEN e.type_group_id = '05' THEN o.term ELSE null END) AS bank_term, c.phase, (case when u.as_of_date is not null then u.as_of_date else v.as_of_date end) as as_of_date, t.movein_date \n"+
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_project b on b.proj_id = a.projcode\n" + 
				"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_product_model d on d.model_id = a.model_id\n" + 
				"LEFT JOIN mf_buyer_type e on e.type_id = a.buyertype\n" + 
				"LEFT JOIN mf_payment_scheme f on f.pmt_scheme_id = a.pmt_scheme_id\n" + 
				"LEFT JOIN mf_buyer_status g on g.byrstatus_id = a.currentstatus\n" + 
				"LEFT JOIN rf_pagibig_computation h ON h.entity_id = a.entity_id AND h.proj_id = a.projcode AND h.pbl_id = a.pbl_id AND h.seq_no = a.seq_no AND (CASE WHEN a.status_id = 'I' THEN h.status_id = 'I' ELSE h.status_id = 'A' END)\n" + 
				"LEFT JOIN rf_ihf_computation i ON i.entity_id = a.entity_id AND i.proj_id = a.projcode AND i.pbl_id = a.pbl_id AND i.seq_no = a.seq_no AND (CASE WHEN a.status_id = 'I' THEN i.status_id = 'I' ELSE i.status_id = 'A' END)\n" + 
				"LEFT JOIN mf_sellingagent_info j ON j.agent_id = a.sellingagent \n"+ //AND j.status_id = 'A'\n" + UNCOMMENT STATUS ID HERE WHEN AGENT STATUS IS ACTIVE
				//"LEFT JOIN mf_sellingagent_info j ON j.agent_id = a.sellingagent AND j.status_id = 'A'\n" + 
				"LEFT JOIN mf_division k ON k.division_code = j.sales_div_id AND k.status_id = 'A'\n" +
				//"LEFT JOIN mf_division k ON k.division_code = j.sales_div_id AND k.status_id = 'A'\n" +
				"LEFT JOIN mf_department l ON l.dept_code = a.mktgarm AND l.status_id = 'A'\n" + 
				"LEFT JOIN rf_unit_status m on m.proj_id = a.projcode and m.pbl_id = a.pbl_id\n" +
				"LEFT JOIN rf_client_price_history n on n.entity_id = a.entity_id and n.proj_id = a.projcode and n.pbl_id = a.pbl_id and n.seq_no = a.seq_no and (CASE WHEN a.status_id= 'I' THEN n.status_id = 'I' ELSE n.status_id = 'A' END) \n"+
				"LEFT JOIN rf_bank_finance_computation o on o.entity_id = a.entity_id and o.proj_id = a.projcode and o.pbl_id = a.pbl_id and o.seq_no = a.seq_no and (CASE WHEN a.status_id = 'I' THEN o.status_id = 'I' ELSE o.status_id = 'A' END) \n"+
				"LEFT JOIN (select * from co_ntp_detail where ntp_no not in (select takenover_ntpno from co_ntp_header where status_id <> 'I' and takenover_ntpno is not null) \n" + 
				"	and status_id = 'A' and ntp_no in (select ntp_no from co_ntp_header where co_id = '02' and ntp_type_id = '02')) p on p.pbl_id = a.pbl_id \n" + 
				"LEFT JOIN (select * from co_ntp_header \n" + 
				"	where ntp_no not in (select takenover_ntpno from co_ntp_header where status_id <> 'I'  and ntp_type_id = '02' and takenover_ntpno is not null))q on q.ntp_no = p.ntp_no and q.status_id = 'A' and q.ntp_type_id = '02'\n" + 
				"LEFT JOIN rf_entity q3 on q3.entity_id = q.entity_id \n" +
				"LEFT JOIN (select * from co_ntp_detail where ntp_no not in (select takenover_ntpno from co_ntp_header where status_id <> 'I'  and ntp_type_id = '02' and takenover_ntpno is not null) \n" + 
				"	and status_id = 'A' and ntp_no in (select ntp_no from co_ntp_header where co_id = '03')) pp on pp.pbl_id = a.pbl_id \n" + 
				"LEFT JOIN (select * from co_ntp_header \n" + 
				"	where ntp_no not in (select takenover_ntpno from co_ntp_header where status_id <> 'I' and ntp_type_id = '02' and takenover_ntpno is not null)) qq on qq.ntp_no = pp.ntp_no and qq.status_id = 'A'\n" + 
				"LEFT JOIN rf_entity q1 on qq.entity_id = q1.entity_id  \n" + 
				"LEFT JOIN (select * from co_ntp_header \n" + 
				"	where ntp_no in (select takenover_ntpno from co_ntp_header where status_id <> 'I'  and ntp_type_id = '02' and takenover_ntpno is not null)) qqq on qqq.ntp_no = q.takenover_ntpno and qqq.status_id = 'A'\n" + 
				"LEFT JOIN rf_entity q2 on qqq.entity_id = q2.entity_id  \n" + 

				"LEFT JOIN rf_tct_taxdec_monitoring_hd r on r.pbl_id = a.pbl_id and r.doc_type = '64' and r.status_id = 'A'\n"+
				"LEFT JOIN mf_sales_position s on s.position_code = j.salespos_id\n" +
				"LEFT JOIN rf_qualified4orientation t on a.projcode = t.projcode and a.pbl_id = t.pbl_id and a.entity_id = t.entity_id and a.seq_no = t.seq_no\n" +
				"left join (select distinct on (pbl_id) * from co_ntp_accomplishment where status_id = 'A' order by pbl_id, as_of_date desc) u on u.pbl_id = a.pbl_id \n" +
				"left join (select MAX(as_of_date) as as_of_date, pbl_id from co_ntp_accomplishment where status_id = 'A' group by pbl_id) v on v.pbl_id = a.pbl_id \n" +
				"\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.projcode = '"+ proj_id +"'\n" + 
				"AND a.pbl_id = '"+ pbl_id +"'\n" + 
				"AND a.seq_no = "+ seq_no +";";
		 */

		if(toPrint){
			FncSystem.out("Client Information", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	//Done
	public static void displaySchedule(modelCARD_Schedule model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		model.clear();

		//05-19-2020 : Original function - view_card_schedule_v3
		String SQL = "SELECT * FROM view_card_schedule_v4('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";


		if(toPrint){
			FncSystem.out("Client Schedule", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//05-19-20 Display ECQ  Schedule (Del Gonzales)
	public static void displayScheduleECQ(modelCARD_Schedule model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		model.clear();

		String SQL = "SELECT * FROM view_card_schedule_ECQ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";

		if(toPrint){
			FncSystem.out("Client Schedule ECQ", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//05-20-20 Display Paid Sched before ECQ (Del Gonzales)
	public static void displaySchedulePaidBeforeECQ(modelCARD_Schedule model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		model.clear();

		String SQL = "SELECT * FROM view_card_schedule_PaidSchedBeforeECQ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";

		if(toPrint){
			FncSystem.out("Paid Sched Prior to ECQ", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//05-19-20 Display Combined (Regular + ECQ) Schedule (Del Gonzales)
	public static void displayScheduleCombined(modelCARD_Schedule model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		model.clear();

		String SQL = "SELECT * FROM view_card_schedule_combined('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";

		if(toPrint){
			FncSystem.out("Client Schedule ECQ", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	//Done
	public static void displayDues(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, Date current_date, modelCARD_Dues modelTotal, Boolean toPrint) {
		modelMain.clear();

		String SQL = "SELECT * FROM view_card_dues('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE)";

		if(toPrint){
			FncSystem.out("Dues", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);
		}
	}

	//Done	
	public static void displayDuesExcludeDate(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, Date current_date, Date exclude_date, modelCARD_Dues modelTotal, Boolean toPrint){
		modelMain.clear();

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_dues('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE) WHERE c_scheddate::DATE != NULLIF('"+exclude_date+"', 'null')::DATE";

		db.select(SQL);
		if(toPrint){
			FncSystem.out("Dues w/ exclusion", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);
		}
	}

	//Done
	public static void displayLedger(modelCARD_Ledger model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		model.clear();
		String SQL = "";
		if(isLedgerSpecial(entity_id, proj_id, pbl_id, seq_no)) {
			SQL = "SELECT * \n" +
				"FROM view_card_ledger_v2('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", false) \n" +
				"ORDER BY c_trans_date, c_sched_date,  (case when c_penalty is not null then 1 else 2 end);";
		}else {
			/*
			SQL = "SELECT * \n" +
				"FROM view_card_ledger_v2('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", false) \n" +
				"ORDER BY COALESCE(c_percent_paid, 0), c_pay_rec_id, c_trans_date, c_sched_date, (case when c_penalty is not null then 1 else 2 end);";
			*/
			
			/*if(UserInfo.EmployeeCode.equals("900876")) {
				SQL = "SELECT * \n" + 
						"FROM view_card_ledger_with_moratorium_v2_test('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", false) \n"; 
						
			}else {*/
				SQL = "SELECT * \n" + 
						"FROM view_card_ledger_with_moratorium_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", false)";
			//}
			
		}

		//String SQL = "SELECT * FROM view_card_ledger_test('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", false)";

		if(toPrint){
			FncSystem.out("Client Ledger", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static Boolean isLedgerSpecial(String entity_id, String proj_id, String pbl_id, int seq_no) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_special_ledger_ordering where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and status_id = 'A')";
		db.select(SQL);
		
		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0]; //true
		}else {
			return (Boolean) db.getResult()[0][0]; //false
		}
	}

	//ADDED BY JOHN LESTER FATALLO 2016-04-05 FOR REFUNDED LEDGER
	public static void displayLedgerRefund(modelCARD_Ledger model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		model.clear();

		String SQL = "SELECT * FROM view_card_ledger_v2('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", TRUE)";

		if(toPrint){
			FncSystem.out("Client Ledger Refunded", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayPayments(modelCARD_Payments model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		model.clear();

		//String SQL = "SELECT * FROM view_card_payments('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";
		//String SQL = "SELECT * FROM view_card_payments_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+");";

		String SQL = "SELECT * FROM view_card_payments_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+toPrint+")";

		if(toPrint){
			FncSystem.out("Client Payments", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayCheckHistory(modelCARD_CheckHistory model, String pay_rec_id) {
		model.clear();

		String SQL = 
				/* "SELECT a.trans_date, b.checkstat_desc, a.status_id, a.dep_no, c.reason_desc\n" + 
				"FROM rf_check_history a\n" + 
				"LEFT JOIN mf_check_status b on b.checkstat_id = a.new_checkstat_id\n" + 
				"LEFT JOIN mf_check_bounce_reason c on c.reason_id = a.bounce_reason_id\n" + 
				"WHERE a.pay_rec_id = '"+ pay_rec_id +"'\n" + 
				"ORDER BY a.trans_date;";*/

				"SELECT * from view_check_history('"+pay_rec_id+"')";

		FncSystem.out("Client Payments", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayReservationDocuments(modelCARD_ReservationDocuments model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		FncTables.clearTable(model);

		String SQL = "SELECT * FROM view_card_reservation_docs_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		if(toPrint){
			FncSystem.out("Client Reservation Documents", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	//Done
	public static void displayPrintedDocuments(modelCARD_PrintedDocuments model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		FncTables.clearTable(model);

		String SQL = "SELECT b.doc_desc, a.date_printed, initcap(get_employee_name(printed_by))\n" + 
				"FROM rf_printed_documents a\n" + 
				"left join rf_sold_unit \n" +
				"on a.entity_id = rf_sold_unit.entity_id and a.proj_id = rf_sold_unit.projcode and a.pbl_id = rf_sold_unit.pbl_id and a.seq_no = rf_sold_unit.seq_no \n" +
				"LEFT JOIN mf_system_doc b ON b.doc_id = a.doc_id \n" + 
				"WHERE a.entity_id = '"+ entity_id +"' AND a.proj_id = '"+ proj_id +"' AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +" AND a.status_id = 'A'\n" + 
				"AND CASE WHEN b.doc_id = '309' THEN TRUE ELSE coalesce(b.server_id, '') = coalesce(rf_sold_unit.server_id, '') END \n"+
				"ORDER BY a.date_printed;";

		if(toPrint){
			FncSystem.out("Client Printed Documents", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayDocsHistory(modelCARD_DocsHistory model, String entity_id, String proj_id, String pbl_id, int seq_no, String doc_id ,Boolean toPrint){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();

		String SQL = "SELECT (CASE WHEN inout_code THEN 'OUT' ELSE 'IN' END) AS status, \n" + 
				"received_by, received_date, received_from\n" + 
				"FROM dm_docs_inout\n" + 
				"WHERE entity_id = '"+entity_id+"'\n" + 
				"and proj_id = '"+proj_id+"'\n" + 
				"and pbl_id = '"+pbl_id+"'\n" + 
				"and seq_no = "+seq_no+"\n" + 
				"and doc_id = '"+doc_id+"' \n" + 
				"and status_id = 'A'\n" + 
				"ORDER BY rec_id;";
		db.select(SQL);

		if(toPrint){
			FncSystem.out("Docs History", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	//Done
	public static void displayDocsHistoryCoapp(modelCARD_DocsHistory model, String entity_id, String proj_id, String pbl_id, int seq_no, String doc_id, Boolean toPrint){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();
		String SQL = "SELECT (CASE WHEN inout_code THEN 'OUT' ELSE 'IN' END) AS status, \n" + 
				"received_by, received_date, received_from\n" + 
				"FROM dm_docs_inout\n" + 
				"WHERE entity_id = (SELECT TRIM(connect_id) FROM rf_entity_connect WHERE connect_type IN ('SC', 'CO') AND entity_id = '"+entity_id+"' AND status_id = 'A')\n" + 
				"and proj_id = '"+proj_id+"'\n" + 
				"and pbl_id = '"+pbl_id+"'\n" + 
				"and seq_no = "+seq_no+"\n" + 
				"and doc_id = '"+doc_id+"' \n" + 
				"and status_id = 'A'\n" + 
				"ORDER BY rec_id;";
		db.select(SQL);

		if(toPrint){
			FncSystem.out("Docs History Coapp", SQL);
		}

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayDocFindings(modelCARD_DocFindings model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_card_docs_findings('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
		db.select(SQL);

		FncSystem.out("Doc Findings", SQL);

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayReqDocDetails(modelCARD_ReqDocDetails model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_requested_doc_detail('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
		db.select(SQL);

		FncSystem.out("Display Req Doc Details", SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayReqDocPurpose(modelCARD_ReqDocPurpose model, String request_no){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();
		String SQL = "select b.pp_desc, COALESCE(a.others_spcfd, '')\n" + 
				"from rf_buyer_docs_dl a\n" + 
				"left join mf_buyer_request_purpose b on b.pp_code = a.req_purpose_id\n" + 
				"where a.request_no = '"+request_no+"' \n" + 
				"and COALESCE(a.req_purpose_id, '') != ''";
		db.select(SQL);

		FncSystem.out("Display Request Docs Purpose", SQL);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayRequestedDocuments(modelCARD_RequestedDocs model, String request_no){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();

		String SQL = "SELECT a.doc_id, b.doc_desc, COALESCE(a.others_spcfd, '')\n" + 
				"FROM rf_buyer_docs_dl a\n" + 
				"LEFT JOIN mf_buyer_request_docs b on b.doc_id = a.doc_id\n" + 
				"where a.request_no = '"+request_no+"'\n" + 
				"and COALESCE(a.doc_id, '') != '' \n" + 
				"ORDER BY b.doc_desc;\n";
		db.select(SQL);

		FncSystem.out("Display Requested Documents", SQL);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayReqDocStatus(modelCARD_ReqDocStatus model, String request_no, String doc_id){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();
		String SQL = "select (case when release_date is not null then 'Released' else 'On Process' end), release_date, get_employee_name(released_by) \n" + 
				"from rf_buyer_docs_dl where request_no = '"+request_no+"' and doc_id = '"+doc_id+"';";
		db.select(SQL);

		FncSystem.out("Display Requested Doc Status", SQL);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayNotices(modelCARD_Notices model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_notices_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
		db.select(SQL);

		if(toPrint){
			FncSystem.out("Client Notices", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayAccountStatus(modelCARD_AccountStatus model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint) {
		FncTables.clearTable(model);

		String SQL = "SELECT * FROM view_card_general_history_v2('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +") ORDER BY c_tran_date;";

		if(toPrint){
			FncSystem.out("Client Status", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//02-15-16 John Lester Fatallo
	public static void displayClientReqHistory(modelCARD_ClientRequestHistory model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		//model.clear();
		FncTables.clearTable(model);

		String SQL = "SELECT * FROM view_card_client_request_history_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") ORDER BY c_request_date";

		if(toPrint){
			FncSystem.out("Client Request History", SQL);
		}

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayRefundHistory(modelCARD_RefundOfPayment model, String request_no, Boolean toPrint, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listmodel = new DefaultListModel();
			rowHeader.setModel(listmodel);

			pgSelect db = new pgSelect();

			String SQL = "SELECT * FROM view_card_refund('"+request_no+"')";

			if(toPrint){
				FncSystem.out("Display History of Refund", SQL);
			}
			db.select(SQL);

			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listmodel.addElement(model.getRowCount());
				}
			}
		}
	}

	//Done
	public static void displayPmtsWaived(modelCARD_PmtsWaived model, String request_no, JList rowHeader, modelCARD_PmtsWaived modelTotal){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_wp_payments_waived('"+request_no+"')";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
				totalWPEntries(model, modelTotal);
			}else{
				modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 0);
			}
		}
	}

	//Done
	public static void displayChangeDueDate(modelCARD_ChangeDueDate model, JList rowHeader, String request_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_client_request_change_due_date('"+request_no+"');";

			db.select(SQL);
			FncSystem.out("Display SQL for Change Due Date", SQL);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	//Done
	public static void displayNTP_History(modelHouseConstructedHistory model, String entity_id, String proj_id, String pbl_id, Integer seq_no){
		if(model != null){
			FncTables.clearTable(model);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_house_construction_ntp_history_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}

	public static void totalWPEntries(DefaultTableModel model,DefaultTableModel modelWP_Total){
		BigDecimal pmts_waived = new BigDecimal("0.00");

		modelWP_Total.setValueAt(FncBigDecimal.zeroValue(), 0, 0);

		for(int x= 0; x<model.getRowCount(); x++){
			pmts_waived = pmts_waived.add((BigDecimal) ((BigDecimal) model.getValueAt(x, 0) == null ? new BigDecimal("0.00"):model.getValueAt(x, 0)));
		}

		modelWP_Total.setValueAt(pmts_waived, 0, 0);
	}

	//John Lester Fatallo (2016-03-08)
	/*public static void displayCreditOfPayment(modelCARD_CreditOfPayment model, int pay_rec_id, Boolean toPrint){
		model.clear();

		String SQL = "SELECT * FROM view_card_credit_of_payment("+ pay_rec_id +");";

		if(toPrint){
			FncSystem.out("Credit of Payment", SQL);
		}

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}*/

	//Done
	public static void displayPmtHistory(modelCARD_CreditOfPayment model, String entity_id, String proj_id, String pbl_id, Integer seq_no, int pay_rec_id, String request_no, Boolean toPrint){
		model.clear();

		pgSelect db = new pgSelect();
		//String SQL = "SELECT * FROM view_card_pmt_history('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+pay_rec_id+", '"+request_no+"')";
		String SQL = "SELECT * FROM view_card_pmt_history_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+pay_rec_id+", '"+request_no+"');";

		db.select(SQL);

		if(toPrint){
			FncSystem.out("Display Payment History", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayCreditOfPmtRequest(modelCARD_CreditOfPayment model, String entity_id, String proj_id, String pbl_id, Integer seq_no ,int pay_rec_id, String request_no ,Boolean toPrint){
		model.clear();

		String SQL = "SELECT * FROM view_card_credit_of_payment_history('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+pay_rec_id+", '"+request_no+"');";
		if(toPrint){
			FncSystem.out("Display Credit of Payment Request", SQL);
		}

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayLOG_Details(modelCARD_LOG_Details model, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean toPrint){
		model.clear();

		String SQL = "SELECT * FROM view_card_log_details('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		if(toPrint){
			FncSystem.out("Display SQL for LOG Details", SQL);
		}

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	//Done
	public static void displayTOverMoveIn(modelTOverMoveIn model, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean toPrint){
		model.clear();

		String SQL = "SELECT * FROM view_card_TOverMoveIn_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		if(toPrint){
			FncSystem.out("Display SQL for LOG Details", SQL);
		}

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	//Done
	public static void displayUnitStatus(modelUnitStatus model, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean other_lot ,Boolean toPrint){
		model.clear();

		String SQL = "SELECT * FROM view_card_unitstatus('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+other_lot+")";

		if(toPrint){
			FncSystem.out("Display SQL for LOG Details", SQL);
		}

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayMRI(modelCARD_MRI model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		model.clear();

		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_card_mri('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+") order by c_date_to";
		db.select(SQL);

		if(toPrint){				
			FncSystem.out("Display MRI", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayFire(modelCARD_Fire model, String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		model.clear();

		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_card_fire('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+");";
		db.select(SQL);

		if(toPrint){
			FncSystem.out("Display FIre", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayCARD_Bank_Accts(modelCARD_BankAccounts model, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean toPrint){
		model.clear();

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_bank_accounts('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
		db.select(SQL);

		FncSystem.out("Display SQL for CARD Bank Accounts", SQL);

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayCARD_Connections(modelConnectionList model, String entity_id){
		model.clear();

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_ci_connections('"+entity_id+"')";
		db.select(SQL);

		FncSystem.out("Display SQL For Connections List", SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayCARD_OtherInfo(String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean toPrint){
		pgSelect db = new pgSelect();

		String SQL = "";
		db.select(SQL);

		FncSystem.out("Display SQL for CARD Other Info", SQL);

		if(db.isNotNull()){

		}
	}

	//Done
	public static void displayFollowUp(modelClientFollowup model,String entity_id, String proj_id, String pbl_id, int seq_no, Boolean toPrint){
		FncTables.clearTable(model);

		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_client_followup_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", FALSE, '"+UserInfo.Department+"')";
		db.select(SQL);

		if(toPrint){
			FncSystem.out("Client Follow Up", SQL);
		}

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayAccomplishment(model_accomplishment model, String pbl_id, String ntp_no, String contract_no){
		//model.clear();
		FncTables.clearTable(model);

		/*
		String SQL = "SELECT as_of_date, percent_accomplish, remarks \n" +
				"FROM co_ntp_accomplishment \n" +
				"where pbl_id = '"+pbl_id+"' and status_id != 'I' \n" + 
				"ORDER BY as_of_date DESC";
		 */

		/*	Modified by Mann2x; Date Modified: May 8, 2018; Accomplishments with cancelled contract numbers should no longer be reflected;	*/
		/*String SQL = "SELECT a.as_of_date, a.percent_accomplish, a.remarks \n" +
				"FROM co_ntp_accomplishment a \n" +
				"where a.pbl_id = '"+pbl_id+"' \n"+
				"AND a.ntp_no = '"+ntp_no+"' \n"+
				"AND a.contract_no = '"+contract_no+"' \n"+
				"AND a.status_id != 'I' \n" + 
				//"and exists(select * from co_ntp_header x where x.ntp_no = a.ntp_no and x.status_id = 'A') \n" +
				"ORDER BY a.as_of_date DESC";*/

		String SQL = "SELECT * FROM view_card_ntp_accomplishment('"+pbl_id+"', '"+ntp_no+"', '"+contract_no+"')";

		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Display SQL for accomplishment", SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void display2ndAccomplishment(DefaultTableModel model, String pbl_id){
		FncTables.clearTable(model);

		String SQL = "SELECT a.as_of_date, a.percent_accomplish, a.remarks \n" +
				"FROM co_ntp_accomplishment a \n" +
				"where a.pbl_id = (SELECT oth_pbl_id FROM hs_sold_other_lots WHERE pbl_id = '"+pbl_id+"' and status_id = 'A') and a.status_id != 'I' \n" + 
				"and exists(select * from co_ntp_header x where x.ntp_no = a.ntp_no and x.status_id = 'A') \n" +
				"ORDER BY a.as_of_date DESC";
		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Display SQL for accomplishment", SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//Done
	public static void displayDocStatus(modelDocStatus model, String pbl_id, String doc_no, String proj_id){ //ADDED BY JESSA HERRERA 2017-05-15
		//model.clear();
		FncTables.clearTable(model);
		
		if(CARD.server == null) {
			String SQL = "SELECT b.status_desc, a.status_date, get_client_name_emp_id(a.done_by), get_client_name(a.recipient), a.remarks \n"

				+ "FROM rf_tct_taxdec_monitoring_dl a\n"

				+ "LEFT JOIN mf_tct_taxdec_status b on TRIM(a.doc_status) = b.status_code\n"

				+ "where TRIM(a.pbl_id) = '"+ pbl_id +"'\n" 
				+ "and TRIM(a.doc_no) = '"+ doc_no +"' \n"
				+ "and a.status_id = 'A' \n"
				+ "and b.status_type = 'S' \n"
				+ "order by a.date_created desc;";
		
		System.out.println("displayDocStatus: "+ SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
		}else {
			String SQL = "(Select b.status_desc, a.status_date::date, a.done_by, a.receipient, '' as status_type , a.remarks \n" + 
					"from rf_tct_taxdec_buyer_monitoring_dl a \n" + 
					"left join mf_tct_taxdec_status b on  trim(a.doc_status) = trim(b.status_code) and a.server_id = b.server_id\n" +
					"LEFT JOIN rf_tct_taxdec_monitoring_hd c ON TRIM(c.doc_no) = trim(a.doc_no) and TRIM(c.pbl_id) = TRIM(a.pbl_id) \n" +
					"where a.status_id = 'A'\n" + 
					"and a.pbl_id = '"+pbl_id+"' \n" + 
					"and a.proj_id = '"+proj_id+"' \n" + 
					"and trim(a.doc_no) = trim('"+ doc_no +"') \n" + 
					"and a.server_id IS NULL \n" +
					"order by a.status_date::date) \n" +
					"\n"+
					"UNION \n"+ // ADDED BY MONIQUE DTD 06-29-23; TO REFLECT DOC STATUS TAGGED AT JSYSTEM (ITSREAL ACCNTS) 
					"\n"+
					"(SELECT b.status_desc, a.status_date, get_client_name_emp_id(a.done_by), get_client_name(a.recipient), '' as status_type , a.remarks \n" +
					"FROM rf_tct_taxdec_monitoring_dl a\n" +
					"LEFT JOIN mf_tct_taxdec_status b on TRIM(a.doc_status) = b.status_code\n" +
					"where TRIM(a.pbl_id) = '"+ pbl_id +"'\n" + 	
					"and TRIM(a.doc_no) = '"+ doc_no +"' \n" +
					"and a.status_id = 'A' \n" +
					"and b.status_type = 'S' \n" +	
					"order by a.date_created desc)\n"+
					"\n"+
					"UNION \n"+ // ADDED BY MONIQUE DTD 2023-07-14; TO REFLECT OTHER DOC STATUS OF ITSREAL ACCNTS
					"\n"+
					"(Select b.status_desc, a.status_date::date, a.done_by, a.receipient, '' as status_type , a.remarks \n" + 
					"from rf_tct_taxdec_buyer_monitoring_dl a \n" + 
					"left join mf_tct_taxdec_buyer_status_itsreal b on  trim(a.doc_status) = trim(b.status_code) and a.server_id = b.server_id\n" +
					"LEFT JOIN rf_tct_taxdec_monitoring_hd c ON TRIM(c.doc_no) = trim(a.doc_no) and TRIM(c.pbl_id) = TRIM(a.pbl_id) \n" +
					"where a.status_id = 'A'\n" + 
					"and a.pbl_id = '"+pbl_id+"' \n" + 
					"and a.proj_id = '"+proj_id+"' \n" + 
					"and trim(a.doc_no) = trim('"+ doc_no +"') \n" + 
					"and c.proj_server = b.proj_server \n" +
					"order by a.status_date::date) \n" +
					"ORDER BY status_date DESC \n" +
					"\n";
			
			// MODIFIED BY MONIQUE DTD 2023-06-08
			/*String SQL = "select b.status_desc, a.status_date::date, a.done_by, a.receipient,'' as status_type , a.remarks \n" + 
					"from rf_tct_taxdec_buyer_monitoring_dl a \n" + 
					"left join mf_tct_taxdec_status b on  trim(a.doc_status) = trim(b.status_code) and a.server_id = b.server_id\n" + 
					"where a.status_id = 'A'\n" + 
					"and a.pbl_id = '"+pbl_id+"' \n" + 
					"and a.proj_id = '"+proj_id+"' \n" + 
					"and trim(a.doc_no) = trim('"+ doc_no +"') \n" + 
					"and case when a.proj_id in ('001','002', '003', '004','005','006', '019')\n" + 
					"then b.proj_server = 'old' \n" + 
					"when a.proj_id in ('009','011', '021') \n" + 
					"then b.proj_server = 'vdc-new' \n" + 
					"when a.proj_id in ('007') \n" + 
					"then b.proj_server = 'em' \n" + 
					"when a.proj_id in ('010', '012', '008') \n" + 
					"then b.proj_server = 'cenq_eb' \n" + 
					"when a.proj_id in ('014') \n" + 
					"then b.proj_server = 'ev' end order by a.status_date::date ";*/
		
		System.out.println("displayDocStatus: "+ SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);	
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
		}
	}

	//Done
	public static void displayDocLocation(modelDocStatus model, String pbl_id, String doc_no){ //ADDED BY JED  2019-08-12 : FOR DISPLAYING LOCATION OF TITLE**//
		//model.clear();
		FncTables.clearTable(model);

		// MODIFIED BY MONIQUE DTD 06-20-2023; TO DISPLAY OTHER DETAILS FOR ITSREAL ACCNTS
		/*String SQL = "SELECT b.status_desc, a.status_date, get_client_name_emp_id(a.done_by), get_client_name(a.recipient), a.remarks \n"
				+ "FROM rf_tct_taxdec_monitoring_dl a\n"
				+ "LEFT JOIN mf_tct_taxdec_status b on TRIM(a.doc_status) = b.status_code\n"
				+ "where TRIM(a.pbl_id) = '"+ pbl_id +"'\n" 
				+ "and TRIM(a.doc_no) = '"+ doc_no +"' \n"
				+ "and TRIM(a.status_id) = 'A' \n"
				+ "and b.status_type = 'L' \n"
				+ "order by a.date_created desc;";*/
		
		String SQL = "SELECT b.status_desc, a.status_date, (CASE WHEN a.server_id IS NULL THEN get_client_name_emp_id(a.done_by) ELSE a.done_by END), (CASE WHEN a.server_id IS NULL THEN get_client_name(a.recipient) ELSE a.recipient END), a.remarks \n"
				+ "FROM rf_tct_taxdec_monitoring_dl a\n"
				+ "LEFT JOIN mf_tct_taxdec_status b on TRIM(a.doc_status) = b.status_code\n"
				+ "where TRIM(a.pbl_id) = '"+ pbl_id +"'\n" 
				+ "and TRIM(a.doc_no) = '"+ doc_no +"' \n"
				+ "and TRIM(a.status_id) = 'A' \n"
				+ "and b.status_type = 'L' \n"
				+ "order by a.date_created desc;";

		System.out.println("displayDocLocation: "+ SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	//	public static void displayTctTaxdec(modelTctTaxdec model, String pbl_id){ //ADDED BY JESSA HERRERA 2017-05-15
	//		//model.clear();
	//		FncTables.clearTable(model);
	//
	//		String SQL = "SELECT a.doc_no, b.doc_desc, a.mother_doc_no, a.date_created, a.facilities, a.serial_no, a.book_no, a.page_no \n"
	//				
	//				+ "FROM rf_tct_taxdec_monitoring_hd a\n"
	//				
	//				+ "LEFT JOIN mf_system_doc b on a.doc_type = b.doc_id\n"
	//
	//				+ "where a.pbl_id = '"+ pbl_id +"'\n" 
	//				+ "and a.status_id = 'A' \n"
	//				+ "order by a.date_created desc;";
	//		
	//		pgSelect db = new pgSelect();
	//		db.select(SQL);
	//		FncSystem.out("Display SQL", SQL);
	//		if(db.isNotNull()){
	//			for(int x = 0; x<db.getRowCount(); x++){
	//				model.addRow(db.getResult()[x]);
	//			}
	//		}
	//	}


	//------modified by JED 9-12-18 : for displaying TCT Tax for other lots---------//

	//Done
	public static void displayTctTaxdec(modelTctTaxdec model, String pbl_id, String other_pbl_id, String proj_id){ //ADDED BY JESSA HERRERA 2017-05-15
		//model.clear();
		FncTables.clearTable(model);
		//Modified by Erick for migration
		
		if(CARD.server == null) {
			String SQL = "\n" + 
					"\n" + 
					"(SELECT a.doc_no, b.doc_desc, a.mother_doc_no, a.date_created, a.facilities, a.serial_no, a.book_no, a.page_no \n" + 
					"FROM rf_tct_taxdec_monitoring_hd a\n" + 
					"LEFT JOIN mf_system_doc b on a.doc_type = b.doc_id and coalesce(a.server_id, '') = coalesce(b.server_id,'') and coalesce(a.proj_server, '') = coalesce(b.proj_server,'')\n" + 
					"where a.pbl_id = '"+pbl_id+"'\n" + 
					"and a.proj_id = '"+proj_id+"'\n" + 
					"and a.status_id = 'A' \n" + 
					"order by a.date_created desc)\n" + 
					"\n" + 
					"union\n" + 
					"\n" + 
					"(SELECT a.doc_no, b.doc_desc, a.mother_doc_no, a.date_created, a.facilities, a.serial_no, a.book_no, a.page_no \n" + 
					"FROM rf_tct_taxdec_monitoring_hd a\n" + 
					"LEFT JOIN mf_system_doc b on a.doc_type = b.doc_id and coalesce(a.server_id, '') = coalesce(b.server_id,'') and coalesce(a.proj_server, '') = coalesce(b.proj_server,'')\n" + 
					"where a.pbl_id = '"+other_pbl_id+"'\n" + 
					"and a.proj_id = '"+proj_id+"' \n" + 
					"and a.status_id = 'A' \n" + 
					"order by a.date_created desc)\n" + 
					"\n"; 

		
			pgSelect db = new pgSelect();
			db.select(SQL);
			FncSystem.out("Display SQL", SQL);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
				}
			}
		}else {
			String SQL ="(SELECT trim(a.doc_no), b.doc_desc, a.mother_doc_no, a.date_created, a.facilities, a.serial_no, a.book_no, a.page_no \n" + 
					"FROM rf_tct_taxdec_monitoring_hd a\n" + 
//					"LEFT JOIN mf_system_doc b on trim(a.doc_type) = trim(b.doc_id) \n" +  -- MODIFIED BY MONIQUE DTD 06-20-2023; REPLACE previous doc type/id (from ItsReal) to active doc id used in JSystem 
					"LEFT JOIN mf_system_doc b on trim(b.doc_id) = (CASE WHEN a.doc_type = '66' THEN '181' ELSE a.doc_type END) \n" + 
					"where trim(a.pbl_id) = '"+pbl_id+"'\n" + 
					"and a.status_id = 'A' \n" + 
					"and a.proj_id = '"+proj_id+"'\n" + 
					"and b.status_id = 'A' \n"+ 
					//Commented by Tim 03-06-2023
					/*"and case when a.proj_id in ('001','002', '003', '004','005','006', '019')\n" + 
					"then b.proj_server = 'old' \n" + 
					"when a.proj_id in ('009','011', '021') \n" + 
					"then b.proj_server = 'vdc-new' \n" + 
					"when a.proj_id in ('007') \n" + 
					"then b.proj_server = 'em' \n" + 
					"when a.proj_id in ('010', '012', '008') \n" + 
					"then b.proj_server = 'cenq_eb' \n" + 
					"when a.proj_id in ('014') \n" + 
					"then b.proj_server = 'ev' \n" + 
					"end \n" + */
					
					"order by a.date_created desc) \n" + 
					"\n" + 
					"union\n" + 
					"\n" + 
					"(SELECT trim(a.doc_no), b.doc_desc, a.mother_doc_no, a.date_created, a.facilities, a.serial_no, a.book_no, a.page_no \n" + 
					"FROM rf_tct_taxdec_monitoring_hd a\n" + 
					"LEFT JOIN mf_system_doc b on a.doc_type = b.doc_id\n" + 
					"where trim(a.pbl_id) = '0'\n" + 
					"and a.status_id = 'A' \n" + 
					"order by a.date_created desc)\n" + 
					"\n" + 
					"union all\n" + 
					"\n" + 
					"(select distinct on (a.doc_no) trim(a.doc_no),  b.doc_desc, '' as mother_doc_no, a.date_created, '' as facilities, '' as serial_no, '' as book_no, '' as page_no\n" + 
					"from ( select * from rf_tct_taxdec_buyer_monitoring_dl where server_id is not null) a \n" + 
					"left join mf_system_doc b on  trim(a.doc_type) = trim(b.doc_id) and a.proj_server = b.proj_server\n" + 
					"where a.doc_no is not null and trim(a.pbl_id) = '"+pbl_id+"'\n" + 
					"and trim(a.proj_id) = '"+proj_id+"' and not exists (select * from rf_tct_taxdec_buyer_monitoring_dl  where trim(a.doc_type) = trim(b.doc_id)))";
			pgSelect db = new pgSelect();
			db.select(SQL);
			FncSystem.out("Display SQL", SQL);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
				}
			}
		}

	}

	//Done
	public static void displayPCostDetails(model_pcost_CARD modelPCD, model_pcost_CARD modeltotal,String entity_id, String proj_id ,String pbl_id, Integer seq_no) {

		FncTables.clearTable(modelPCD);

		/*String SQL = "SELECT distinct on (a.pcostid_dl) false, trim(b.pcostdtl_desc), a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.tran_type, null, a.batch_no, a.rpt_or_no, a.rpt_year \n" + 
					"FROM rf_processing_cost a \n" + 
					"LEFT JOIN mf_processing_cost_dl b ON a.pcostid_dl = b.pcostdtl_id  \n" + 
					"WHERE a.status_id = 'A' \n" + 
					"AND a.pbl_id = '"+pbl_id+"' \n" + 
					"GROUP BY b.pcostdtl_desc, a.tran_date, a.pcost_amt, a.remarks, a.doc_no, a.rplf_no, a.jv_no, a.batch_no, a.pcostid_dl, a.tran_type, a.rpt_or_no, a.rpt_year;";*/
		
		//String SQL = "SELECT * FROM view_card_processing_cost_v3('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+")";
		String SQL = "SELECT * FROM view_card_processing_cost_v4('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+")"; //Added by erick 2023-10-04 DCRF 2736
		
		FncSystem.out("Display SQL for PCostDetails", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelPCD.addRow(db.getResult()[x]);
			}
		}
		BigDecimal total = new BigDecimal(0.00);
		for(int y=0; y < modelPCD.getRowCount(); y++){
			total = total.add((BigDecimal) modelPCD.getValueAt(y, 3));
			modeltotal.setValueAt(total, 0, 3);
		}
	}
	//Done
	public static void displayTCostDetails(model_tcost model, model_tcost modeltotal, String entity_id, String proj_id ,String pbl_id, Integer seq_no) {

		FncTables.clearTable(model);

		/*String SQL = "SELECT distinct on (a.tcostid_dl) false, trim(b.tcostdtl_desc), a.tran_date, a.tcost_amt, a.remarks, a.rplf_no\n" + 
					"FROM rf_transfer_cost a \n" + 
					"LEFT JOIN mf_transfer_cost_dl b ON a.tcostid_dl = b.tcostdtl_id  \n" + 
					"WHERE a.status_id = 'A' \n" + 
					"AND a.pbl_id = '"+pbl_id+"' \n" + 
					"GROUP BY b.tcostdtl_desc, a.tran_date, a.tcost_amt, a.remarks, a.rplf_no, a.tcostid_dl;";*/

		//String SQL = "SELECT * FROM view_card_transfer_cost_v2('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+")";
		String SQL = "SELECT * FROM view_card_transfer_cost_v3('"+entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+")";//Added by erick 2023-10-04 DCRF 2736

		pgSelect db = new pgSelect();
		db.select(SQL);

		FncSystem.out("Display SQL for TCostDetails", SQL);

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
		BigDecimal total = new BigDecimal(0.00);
		for(int y=0; y < model.getRowCount(); y++){
			total = total.add((BigDecimal) model.getValueAt(y, 3));
			modeltotal.setValueAt(total, 0, 3);
		}
	}

	//Done
	public static Boolean isNOAReleased(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "SELECT * " + 
				"FROM rf_buyer_status " + 
				"WHERE entity_id = '"+ entity_id +"' AND proj_id = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"' AND seq_no = "+ seq_no +" AND byrstatus_id = '35' AND status_id = 'A'\n" + 
				"AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = '"+ entity_id +"' AND proj_id = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"' AND seq_no = "+ seq_no +" AND byrstatus_id = '32' AND status_id = 'A')";

		pgSelect db= new pgSelect();
		db.select(SQL);
		return db.isNotNull();
	}

	//Done
	public static Boolean isUnitTransferred(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		pgSelect db = new pgSelect();

		String SQL = "SELECT EXISTS (SELECT * FROM rf_buyer_status WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND byrstatus_id IN ('03', '16') AND request_no IS NOT NULL /*AND status_id = 'A'*/)";
		db.select(SQL);

		FncSystem.out("Display Exists", SQL);
		return (Boolean) db.getResult()[0][0];

	}

	//Done
	public static Boolean withNTC(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		pgSelect db = new pgSelect();

		String SQL = "SELECT EXISTS (SELECT * FROM rf_buyer_status WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = '"+seq_no+"' AND byrstatus_id = '29' AND status_id = 'A')";
		db.select(SQL);

		FncSystem.out("Display if with NTC", SQL);

		return (Boolean) db.getResult()[0][0];
	}

	/*
	 * public static Boolean withHousingLoan(String entity_id, String proj_id,
	 * String pbl_id, Integer seq_no){ pgSelect db = new pgSelect(); String SQL =
	 * "SELECT EXISTS (SELECT * FROM rf_buyer_documents WHERE entity_id = '"
	 * +entity_id+"' AND projcode = '"+proj_id+"' AND pbl_id = '"
	 * +pbl_id+"' AND seq_no = "
	 * +seq_no+" AND doc_id = '234' AND docs_out is null AND status_id = 'A')";
	 * db.select(SQL);
	 * 
	 * FncSystem.out("Display if with Housing Loan", SQL);
	 * 
	 * return (Boolean) db.getResult()[0][0]; }
	 */

	//Done
	public static String getTransferredUnit(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		String title = "";

		pgSelect db = new pgSelect();
		String SQL = "SELECT description FROM mf_unit_info WHERE unit_id = (SELECT new_unit_id FROM req_rt_request_header WHERE request_no = (SELECT request_no FROM rf_buyer_status WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND byrstatus_id IN ('03', '16') ORDER BY tran_date DESC LIMIT 1 /*AND status_id = 'A'*/))";
		db.select(SQL);

		FncSystem.out("Display SQL for Transferred Unit", SQL);

		title = (String) db.getResult()[0][0];

		return title;
	}

	//Done
	public static String getCancelledDate(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		String canc_date = "";
		pgSelect db = new pgSelect();

		String SQL = "SELECT to_char(tran_date, 'Mon DD, YYYY') \n"+
				"FROM rf_buyer_status \n"+
				"WHERE entity_id = '"+entity_id+"' \n"+
				"AND proj_id = '"+proj_id+"' \n"+
				"AND pbl_id = '"+pbl_id+"' \n"+
				"AND seq_no = "+seq_no+" \n"+
				"AND byrstatus_id = '02'";
		db.select(SQL);

		FncSystem.out("Display SQL for Cancelled Date", SQL);

		canc_date = (String) db.getResult()[0][0];

		return canc_date;
	}

	//Done
	public static String getLoanReleasedDate(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		String lnrel_date = "";
		pgSelect db = new pgSelect();

		String SQL = "SELECT to_char(tran_date, 'Mon DD, YYYY') \n" + 
				"FROM rf_buyer_status \n" + 
				"WHERE entity_id = '"+entity_id+"' \n" + 
				"AND proj_id = '"+proj_id+"' \n" + 
				"AND pbl_id = '"+pbl_id+"' \n" + 
				"AND seq_no = "+seq_no+" \n" + 
				"AND byrstatus_id = '32'";
		db.select(SQL);

		FncSystem.out("Display SQL for Loan Relesed Date", SQL);
		lnrel_date = (String) db.getResult()[0][0];

		return lnrel_date;
	}

	//Done
	public static String getBankfFinanceFullSettledDate(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		String full_settled = "";
		pgSelect db = new pgSelect();

		String SQL = "SELECT to_char(tran_date, 'Mon DD, YYYY') \n" + 
				"FROM rf_buyer_status \n" + 
				"WHERE entity_id = '"+entity_id+"' \n" + 
				"AND proj_id = '"+proj_id+"' \n" + 
				"AND pbl_id = '"+pbl_id+"' \n" + 
				"AND seq_no = "+seq_no+" \n" + 
				"AND byrstatus_id = '135'";
		db.select(SQL);

		FncSystem.out("Display SQL for Loan Relesed Date", SQL);
		full_settled = (String) db.getResult()[0][0];

		return full_settled;
	}

	//Done
	public static String getSoldToBankDate(String entity_id, String proj_id, String pbl_id ,Integer seq_no){
		String stb_date = "";
		pgSelect db = new pgSelect();

		String SQL = "SELECT to_char(tran_date, 'Mon DD, YYYY') \n" + 
				"FROM rf_buyer_status \n" + 
				"WHERE entity_id = '"+entity_id+"' \n" + 
				"AND proj_id = '"+proj_id+"' \n" + 
				"AND pbl_id = '"+pbl_id+"' \n" + 
				"AND seq_no = "+seq_no+" \n" + 
				"AND byrstatus_id = '25'";
		db.select(SQL);

		FncSystem.out("Display SQL for Sold To Bank Date", SQL);

		return stb_date;
	}

	//Done
	public static String getCARD_Blinking_Status(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		String card_status = "";

		pgSelect db = new pgSelect();
		String SQL = "SELECT get_card_blinking_status('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		db.select(SQL);
		FncSystem.out("Dispya card blink status", SQL);
		card_status = (String) db.getResult()[0][0];

		return card_status;
	}

	//Done
	public static Boolean withHousingLoan(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		Boolean card_status = false;

		pgSelect db = new pgSelect();
		String SQL = "SELECT is_with_housing_loan('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		db.select(SQL);
		FncSystem.out("Dispya card blink status", SQL);
		card_status = (Boolean) db.getResult()[0][0];

		return card_status;
	}
	
	public static Boolean isItsrealClient(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and server_id IS NOT NULL)";
		db.select(SQL);
		FncSystem.out("Display Itsreal Client", SQL);
		
		return (Boolean) db.getResult()[0][0];
	}

	public static void initTAGS(JPanel pnlNorth, CARD card) {
		for(Component comp : pnlNorth.getComponents()){
			if(comp instanceof _JLookup){
				return;
			}

			if(comp instanceof _JXTextField){
				/*System.out.println("Dumaan dito sa _JXTextfield");
				System.out.println("");*/
				_JXTextField field = (_JXTextField) comp;

				if(field.getName() != null && field.getName().equals("Factor")){

				}else{
					field.setText("[ ]");
					field.setEditable(false);
					field.setForeground(Color.BLUE);
					field.setBorder(BorderFactory.createEmptyBorder());
					field.setOpaque(false);
					field.addMouseListener(card);
					field.getDocument().addDocumentListener(new DocumentListener() {

						@Override
						public void removeUpdate(DocumentEvent e) { }

						@Override
						public void insertUpdate(DocumentEvent e) { }

						@Override
						public void changedUpdate(DocumentEvent e) {
							Object obj = e.getDocument().getProperty("source");

							if(obj instanceof _JXTextField){
								_JXTextField field = (_JXTextField) obj;

								FontMetrics fm = field.getFontMetrics(font11);
								try {
									String text = e.getDocument().getText(0, e.getDocument().getLength());

									int adv = fm.stringWidth(text);
									Dimension size = new Dimension(adv+5, 20);

									field.setPreferredSize(size);
									field.setSize(size);
									field.repaint();
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
							}

							if(obj instanceof _JXFormattedTextField){
								_JXFormattedTextField field = (_JXFormattedTextField) obj;

								FontMetrics fm = field.getFontMetrics(font11);
								try {
									String text = e.getDocument().getText(0, e.getDocument().getLength());

									int adv = fm.stringWidth(text);
									Dimension size = new Dimension(adv+5, 20);

									field.setPreferredSize(size);
									field.setSize(size);
									field.repaint();
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
							}
						}
					});
				}
			}

			//Added by Alvin Gonzales (2015-05-20)
			if(comp instanceof _JXFormattedTextField){
				_JXFormattedTextField field = (_JXFormattedTextField) comp;
				field.setEditable(false);
				field.addMouseListener(card);
				field.setBackground(UIManager.getColor("FormattedTextField.background"));
			}

			if(comp instanceof JXTextField){
				JXTextField field = (JXTextField) comp;
				field.setEditable(false);
				field.addMouseListener(card);
			}

			if(comp instanceof JXLabel){
				JXLabel label = (JXLabel) comp;
				label.addMouseListener(card);
				//label.setBorder(lineBorder);
				label.setHorizontalAlignment(SwingConstants.RIGHT);
			}

			if(comp instanceof JPanel){
				JPanel panel = (JPanel) comp;
				panel.addMouseListener(card);

				initTAGS((JPanel) comp, card);
			}

			//comp.setFont(new Font("Tahoma", Font.PLAIN, 11));
			comp.setFont(font11);
		}
	}

	public static void totalEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal proc_fee = new BigDecimal("0.00");
		BigDecimal rpt = new BigDecimal("0.00");
		BigDecimal mri = new BigDecimal("0.00");
		BigDecimal fire = new BigDecimal("0.00");
		BigDecimal maf = new BigDecimal("0.00");
		BigDecimal vat = new BigDecimal("0.00");
		BigDecimal soi = new BigDecimal("0.00");
		BigDecimal sop = new BigDecimal("0.00");
		BigDecimal penalty = new BigDecimal("0.00");
		BigDecimal interest = new BigDecimal("0.00");
		BigDecimal accrued_interest = new BigDecimal("0.00");

		BigDecimal amount = new BigDecimal("0.00");
		BigDecimal due_amt = new BigDecimal("0.00");

		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);

		for(int x=0; x < modelMain.getRowCount(); x++){
			//try {
			proc_fee = proc_fee.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 3) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 3)));
			rpt = rpt.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 4) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 4)));
			mri = mri.add((BigDecimal) (modelMain.getValueAt(x, 5) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 5)));
			fire = fire.add((BigDecimal) (modelMain.getValueAt(x, 6) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 6)));
			maf = maf.add((BigDecimal) (modelMain.getValueAt(x, 7) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 7)));
			vat = vat.add((BigDecimal) (modelMain.getValueAt(x, 8) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 8)));
			soi = soi.add((BigDecimal) (modelMain.getValueAt(x, 9) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 9)));
			sop = sop.add((BigDecimal) (modelMain.getValueAt(x, 10) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 10)));
			penalty = penalty.add((BigDecimal) (modelMain.getValueAt(x, 11) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 11)));
			interest = interest.add((BigDecimal) (modelMain.getValueAt(x, 13) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 13)));
			accrued_interest = accrued_interest.add((BigDecimal) (modelMain.getValueAt(x, 14) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 14)));
			amount = amount.add((BigDecimal) (modelMain.getValueAt(x, 15) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 15)));
			due_amt = due_amt.add((BigDecimal) (modelMain.getValueAt(x, 16) == null ? new BigDecimal("0.00"):modelMain.getValueAt(x, 16)));

		}

		/*System.out.printf("Display Sum of Amount: %s%n", amount);
		System.out.printf("Display sum of proc_fee: %s%n", proc_fee);*/

		modelTotal.setValueAt(proc_fee, 0, 3);
		modelTotal.setValueAt(rpt, 0, 4);
		modelTotal.setValueAt(mri, 0, 5);
		modelTotal.setValueAt(fire, 0, 6);
		modelTotal.setValueAt(maf, 0, 7);
		modelTotal.setValueAt(vat, 0, 8);
		modelTotal.setValueAt(soi, 0, 9);
		modelTotal.setValueAt(sop, 0, 10);
		modelTotal.setValueAt(penalty, 0, 11);
		modelTotal.setValueAt(interest, 0, 13);
		modelTotal.setValueAt(accrued_interest, 0, 14);
		modelTotal.setValueAt(amount, 0, 15);
		modelTotal.setValueAt(due_amt, 0, 16);

		//modelTotal.addRow(new Object[] { null, "Total", null, amount, null, null, null, null, null, null, null, null, null, null, null, null });
	}

	//Done
	public static void display_hrdetails(model_HouseRepair model_HR, String entity_id, String pbl_id, String proj_id, Integer seq_no) {

		FncTables.clearTable(model_HR);

		pgSelect db = new pgSelect();
		String SQL =  "SELECT * FROM view_house_repair('"+entity_id+"','"+pbl_id+"','"+proj_id+"', "+seq_no+")";

		db.select(SQL);

		FncSystem.out("SQL House Repair",SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				System.out.println("@@@@@@@@@@@@@@@@@@");
				System.out.println(db.getResult()[x][0]);

				model_HR.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayDuesExcludeDateMixed(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, Date current_date, Date exclude_date, modelCARD_Dues modelTotal, Boolean toPrint){
		modelMain.clear();

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_dues_mixed('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE) WHERE c_scheddate::DATE != NULLIF('"+exclude_date+"', 'null')::DATE";

		db.select(SQL);
		if(toPrint){
			FncSystem.out("Dues w/ exclusion", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);
		}
	}

	public static void displayDuesExcludeDateMoratorium(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, Date current_date, Date exclude_date, modelCARD_Dues modelTotal, Boolean toPrint){
		modelMain.clear();

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_dues_moratorium('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE) WHERE c_scheddate::DATE != NULLIF('"+exclude_date+"', 'null')::DATE";

		db.select(SQL);
		if(toPrint){
			FncSystem.out("Dues w/ exclusion", SQL);
		}

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);
		}
	}

	/**05-20-20 Adjusted Dues Display - Emman*/
	public static void displayDuesMixed(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, Date current_date, modelCARD_Dues modelTotal, Boolean toPrint) {
		modelMain.clear();

		String SQL = "SELECT * FROM view_card_dues_mixed('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE)";

		if(toPrint){
			FncSystem.out("ECQ Dues", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);
		}
	}

	/**05-20-20 Adjusted Dues Display - Emman*/
	public static void displayDuesMoratorium(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, Date current_date, modelCARD_Dues modelTotal, Boolean toPrint) {
		modelMain.clear();

		String SQL = "SELECT * FROM view_card_dues_moratorium('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE)";

		if(toPrint){
			FncSystem.out("ECQ Dues", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);
		}
	}

	public static void displayDuesRegular(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, Date current_date, modelCARD_Dues modelTotal, Boolean toPrint, Date exclude_date, Boolean exclude) {
		modelMain.clear();
		String SQL = ""; 
		
		if (afterECQ(entity_id, proj_id, pbl_id, seq_no) || entity_id.equals("5935348691") || isItsrealClient(entity_id, proj_id, pbl_id, seq_no)) {
			SQL = "SELECT * FROM view_card_dues('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE) WHERE CASE WHEN "+exclude+" THEN c_scheddate::DATE != NULLIF('"+exclude_date+"', 'null')::DATE ELSE TRUE END";	
			//SQL = "SELECT * FROM view_card_dues_itsreal('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE)"; //Added by Erick 2022-04-27 for migration	
		} else {
			SQL = "SELECT * FROM view_card_dues_regular('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ current_date +"'::TIMESTAMP, FALSE)";
		}
		
		if(toPrint){
			FncSystem.out("Regular Dues", SQL);
		}

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			for(int x=0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		} else {
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 3);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 4);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 8);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 11);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 13);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 15);
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 16);
		}
	}
	
	public static boolean afterECQ(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		
		boolean blnTR = FncGlobal.GetBoolean("select exists\n" + 
				"(\n" + 
				"	select *\n" + 
				"	from rf_buyer_status\n" + 
				"	where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no::int = '"+seq_no+"'::int\n" + 
				"	and byrstatus_id = '17' and status_id = 'A' and tran_date::date >= '2020-07-01'::date\n" + 
				")"); 

		boolean blnSched = false; 
		
		try {
			blnSched = FncGlobal.GetBoolean("select min(scheddate) >= '2020-07-01'::date\n" + 
					"from rf_client_schedule \n" + 
					"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no::int = '"+seq_no+"'::int\n" + 
					"and status_id = 'A'");
		} catch (NullPointerException e) {
			blnSched = false;
		}
		
		boolean blnWithECQSched = FncGlobal.GetBoolean("select exists(select * \n" + 
				"from rf_client_schedule_moratorium \n" + 
				"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no::int = '"+seq_no+"'::int\n" + 
				"and status_id = 'A')");
		
		return blnTR || blnSched || !blnWithECQSched; 
	}
	
	public static String getCWT_TaxExempt(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT get_cwt_tax_exempt('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
		db.select(SQL);
		
		return (String) db.getResult()[0][0];
	}
	
	// ADDED BY JARI CRUZ ASOF NOV 21 2022

	public static String checkIfTwoLot(String entity_id, String proj_id, String pbl_id, Object seq_no) {
		String SQL = "select oth_pbl_id from hs_sold_other_lots "
				+ "where entity_id = '"+entity_id+"' "
				+ "and proj_id = '"+proj_id+"' "
				+ "and pbl_id = '"+pbl_id+"' "
				+ "and seq_no = "+seq_no+" "
				+ "and status_id = 'A'";
		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("checkIfTwoLot", SQL);
		try {
			return (String) db.getResult()[0][0];
		}catch (Exception e) {
			return null;
		}
	}
	
	// ADDED BY JARI CRUZ ASOF NOV 23 2022

	public static String getClientCompany(String proj_id) {
		String SQL = "select co_id from mf_project "
				+ "where proj_id = '"+proj_id+"' "
				+ "and status_id = 'A'";
		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("getClientCompany", SQL);
		try {
			return (String) db.getResult()[0][0];
		}catch (Exception e) {
			return null;
		}
	}
	
	// ADDED BY JARI CRUZ ASOF NOV 23 2022

	public static Date getClientLatestWaterReading(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "select reading_date::date from rf_water_reading_v2\n"
				+ "where entity_id = '"+entity_id+"'\n"
				+ "and proj_id = '"+proj_id+"'\n"
				+ "and pbl_id = '"+pbl_id+"'\n"
				+ "and seq_no = '"+seq_no+"'\n"
				+ "and status_id = 'A'\n"
				+ "order by reading_date desc limit 1";
		pgSelect db = new pgSelect();
		db.select(SQL);
		System.out.println("getClientLatestWaterReading "+(Date) db.getResult()[0][0]);
		FncSystem.out("getClientLatestWaterReading", SQL);
		try {
			return (Date) db.getResult()[0][0];
		}catch (Exception e) {
			return null;
		}
	}
}


