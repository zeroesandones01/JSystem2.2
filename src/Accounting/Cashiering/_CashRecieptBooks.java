package Accounting.Cashiering;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import tablemodel.modelCRBAccountEntries;
import Functions.FncSystem;
import Functions.FncTables;

public class _CashRecieptBooks {

	public static String getReceiptSQL(String receipt_type, String projcode, String phase) {
		String strSQL = "";
		if(receipt_type != null){
			if(receipt_type.equals("AR")){
						/*
					    "select distinct\n" +
						"ltrim(rtrim(a.rb_id)) as \"CRB ID\",\n" +
						"ltrim(rtrim(e.doc_desc)) as \"Type\",\n" +
						"rtrim(a.particulars) as \"Particulars\",\n" +
						"sum(a.amount) as \"Amount\",\n" +
						"coalesce(b.OR_Date, b.Trans_Date) as \"Date Issued\",\n" +
						//"getDate() as \"Date Issued\",\n" +
						//Modified By Mann2x; Modified Date: October 7, 2016; Changed because some reference values are inconsistent when the payment is Cash Return;
						//"coalesce(ltrim(rtrim(b.pbl_id)),'none') as \"PBL ID\",\n" +
						//"coalesce(c.Description, 'none') as \"Description\",\n" +
						"coalesce(ltrim(rtrim(b.pbl_id)),'') as \"PBL ID\",\n" +
						"coalesce(c.Description, '') as \"Description\",\n" +
						"coalesce (b.seq_no, 0) as \"Seq.\",\n" +
						"ltrim(rtrim(b.Entity_ID)) as \"Client ID\",\n" +
						"ltrim(rtrim(d.entity_name)) as \"Client Name\", \n\n" +
						"upper(trim(f.status_desc)) \n" +

						"from rf_crb_header a --with(nolock)\n" +
						"left join " +
						"(" +
						"	select or_date, trans_date, pbl_id, seq_no, entity_id, pay_Rec_ID, status_id, proj_id, client_seqno from rf_payments union all \n" +
						"	select a.trans_date, a.booking_date, a.pbl_id, a.seq_no, a.entity_id, a.pay_header_ID, a.status_id, a.proj_id, a.client_seqno \r\n" + 
						"			from rf_pay_header a\r\n" + 
						"			left join rf_pay_detail b on a.client_seqno = b.client_seqno\r\n" + 
						//Modified By Mann2x; Modified Date: October 7, 2016; Changed because some reference values are inconsistent when the payment is Cash Return;
						//"			where b.receipt_type = '03' and b.part_type = '054' union all \n" +
						"			where b.receipt_type = '03' and b.part_type IN (SELECT x.pay_part_id FROM mf_pay_particular x WHERE partdesc LIKE '%CASH%' AND COALESCE(receipt_to_issue, '') <> '') union all \n" +
						
						"	select trans_date, actual_date, pbl_id, seq_no, entity_id, tra_header_ID, status_id, proj_id, client_seqno from rf_tra_header " +
						") b on a.pay_rec_id::int = b.pay_Rec_ID " +
						//"and b.status_id in ('A', 'P') " +
						" and to_char(a.issued_date,'yyyy-MM-dd')= to_char(b.trans_date,'yyyy-MM-dd')" +
						" and a.reference_no = b.client_seqno  " +
						"--and b.pbl_id = d.PBL_ID and b.seq_no = d.seq_no  -- with(nolock)\n" +
						"left join mf_unit_info c  on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id  --with(nolock)\n" +
						"left join rf_entity d  on b.Entity_ID = d.entity_id --with(nolock)\n" +
						"left join mf_system_doc e on a.doc_id = e.doc_id --with(nolock) \n" +
						"left join mf_record_status f on a.status_id =  f.status_id \n" +

						"where (case when '"+ projcode +"' = 'null' or '"+ projcode.trim()+"' = '' then a.rb_id is not null else a.proj_id = '"+ projcode +"' end ) " +
						"and (case when '"+ phase +"' = 'null' or '"+ phase.trim()+"' = '' then a.rb_id is not null else a.phase = '"+ phase +"' end ) " +
						"and a.doc_id = '03' \n\n" +

						"group by ltrim(rtrim(a.rb_id)), ltrim(rtrim(e.doc_desc)), a.particulars, ltrim(rtrim(b.pbl_id)), " +
						"c.Description, b.seq_no, ltrim(rtrim(b.Entity_ID)), ltrim(rtrim(d.entity_name)), coalesce(b.OR_Date, b.Trans_Date)," +
						"f.status_desc ;";
						*/
				strSQL = "SELECT distinct ltrim(rtrim(a.rb_id)) as \"CRB ID\", ltrim(rtrim(e.doc_desc)) as \"Type\", rtrim(a.particulars) as \"Particulars\", \n" +
						"sum(a.amount) as \"Amount\", " +
						"coalesce(b.OR_Date, b.Trans_Date) as \"Date Issued\", " + //--edited by D.G. based on DCRF No. 391
						
						"coalesce(ltrim(rtrim(b.pbl_id)),'') as \"PBL ID\", \n" +
						"coalesce(c.Description, '') as \"Description\", coalesce (b.seq_no, 0) as \"Seq.\", ltrim(rtrim(b.Entity_ID)) as \"Client ID\", \n" +
						"ltrim(rtrim(d.entity_name)) as \"Client Name\", upper(trim(f.status_desc)) \n" +
						"from rf_crb_header a \n" +
						"left join \n" +
						"(select or_date, trans_date, pbl_id, seq_no, entity_id, pay_Rec_ID, status_id, proj_id, client_seqno \n" + 
						"from rf_payments \n" +
						"union all \n" +
						"select a.trans_date, a.booking_date, a.pbl_id, a.seq_no, a.entity_id, a.pay_header_ID, a.status_id, a.proj_id, a.client_seqno \n" +
						"from rf_pay_header a \n" +
						"left join rf_pay_detail b on a.client_seqno = b.client_seqno \n" +  
						"where b.receipt_type = '03' and b.part_type IN (SELECT x.pay_part_id FROM mf_pay_particular x WHERE partdesc LIKE '%CASH%' AND COALESCE(receipt_to_issue, '') <> '') \n" + 
						"union all \n" +
						"select trans_date, actual_date, pbl_id, seq_no, entity_id, tra_header_ID, status_id, proj_id, client_seqno \n" +
						/*Modified by Mann2x; Date Modified: October 19, 2017;	*/
						/*"from rf_tra_header) b on a.pay_rec_id::int = b.pay_Rec_ID and to_char(a.issued_date,'yyyy-MM-dd')= to_char(b.trans_date,'yyyy-MM-dd') and a.reference_no = b.client_seqno \n" +*/
						"from rf_tra_header) b on a.pay_rec_id::int = b.pay_Rec_ID and a.reference_no = b.client_seqno \n" +
						"left join mf_unit_info c  on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id \n" +
						"left join rf_entity d  on b.Entity_ID = d.entity_id \n" +
						"left join mf_system_doc e on a.doc_id = e.doc_id \n" +
						"left join mf_record_status f on a.status_id =  f.status_id \n" + 
						"where (a.proj_id = '"+ projcode +"' or '"+ projcode +"' = '' or '"+ projcode +"' is null) \n" +
						"and (a.phase = '"+ phase +"' or '"+ phase +"' = '' or '"+ phase +"' is null) and a.doc_id = '03' \n" + 
						"group by a.proj_id, a.phase, ltrim(rtrim(a.rb_id)), ltrim(rtrim(e.doc_desc)), a.particulars, ltrim(rtrim(b.pbl_id)), \n" +
						"c.Description, b.seq_no, ltrim(rtrim(b.Entity_ID)), ltrim(rtrim(d.entity_name)), coalesce(b.OR_Date, b.Trans_Date), f.status_desc"; 
						
				} else if (receipt_type.equals("SI")) { /*Added by Monique dtd 10-11-2022; FOR Receipt Details of Sales Invoice*/
					strSQL = "select distinct rtrim(a.rb_id) as \"CRB ID\", rtrim(e.doc_desc) as Type, f.particulars as Particulars, sum(a.amount) as Amount, \n" +
							"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else " +
							"(case when b.OR_Date is null or to_char(or_date,'yy')::int >= 18 then b.Trans_Date" +
							"	else b.OR_Date end)" +
							"end) as \"Date Issued\", \n" +
							"rtrim(b.pbl_id) as \"PBL ID\", c.Description as Description, b.seq_no as \"Seq.\", rtrim(b.Entity_ID) as \"Client ID\", \n" +
							"rtrim(d.entity_name) as \"Client Name\", upper(trim(h.status_desc)) \n" +
							"from rf_crb_header a \n" +
							"left join rf_payments b on a.pay_rec_id::int = b.pay_Rec_ID and b.status_id in ('A', 'P') \n" +
							"left join mf_unit_info c on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id \n" +
							"left join rf_entity d on b.Entity_ID = d.entity_id \n" +
							"left join mf_system_doc e on a.doc_id = e.doc_id \n" +
							"left join (select rb_id, particulars, pay_rec_id from rf_crb_header aa group by rb_id, particulars, pay_rec_id) f on a.rb_id = f.rb_id and b.pay_rec_id::int = f.pay_rec_id::int \n" +
							"left join rf_pay_header g on b.client_seqno = g.Client_seqno \n" +
							"left join mf_record_status h on a.status_id =  h.status_id \n" +
							"where (a.proj_id = '"+ projcode +"' or '"+ projcode +"' = '' or '"+ projcode +"' is null) \n" +
							"and (a.phase = '"+ phase +"' or '"+ phase +"' = '' or '"+ phase +"' is null) \n" +
							"and a.doc_id = '307' \n" +
							"group by rtrim(a.rb_id), rtrim(e.doc_desc), f.particulars, rtrim(b.pbl_id), \n" +
							"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else coalesce(b.OR_Date, b.Trans_Date) end), \n" +
							"c.Description, b.seq_no, rtrim(b.Entity_ID), rtrim(d.entity_name), h.status_desc"; 
					
				} else {
				strSQL = "select distinct rtrim(a.rb_id) as \"CRB ID\", rtrim(e.doc_desc) as Type, f.particulars as Particulars, sum(a.amount) as Amount, \n" +
						"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else " +
						"(case when b.OR_Date is null or to_char(or_date,'yy')::int >= 18 then b.Trans_Date" +
						"	else b.OR_Date end)" +
						"end) as \"Date Issued\", \n" +
						"rtrim(b.pbl_id) as \"PBL ID\", c.Description as Description, b.seq_no as \"Seq.\", rtrim(b.Entity_ID) as \"Client ID\", \n" +
						"rtrim(d.entity_name) as \"Client Name\", upper(trim(h.status_desc)) \n" +
						"from rf_crb_header a \n" +
						"left join rf_payments b on a.pay_rec_id::int = b.pay_Rec_ID and b.status_id in ('A', 'P') \n" +
						"left join mf_unit_info c on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id \n" +
						"left join rf_entity d on b.Entity_ID = d.entity_id \n" +
						"left join mf_system_doc e on a.doc_id = e.doc_id \n" +
						"left join (select rb_id, particulars, pay_rec_id from rf_crb_header aa group by rb_id, particulars, pay_rec_id) f on a.rb_id = f.rb_id and b.pay_rec_id::int = f.pay_rec_id::int \n" +
						"left join rf_pay_header g on b.client_seqno = g.Client_seqno \n" +
						"left join mf_record_status h on a.status_id =  h.status_id \n" +
						"where (a.proj_id = '"+ projcode +"' or '"+ projcode +"' = '' or '"+ projcode +"' is null) \n" +
						"and (a.phase = '"+ phase +"' or '"+ phase +"' = '' or '"+ phase +"' is null) \n" +
						/*Modified by Mann2x; Date Modified: October 24, 2017; So that other income payments would be included;	*/
						/*"and c.Description is not null and a.doc_id = '01' \n" +*/
						"and a.doc_id = '01' \n" +
						"group by rtrim(a.rb_id), rtrim(e.doc_desc), f.particulars, rtrim(b.pbl_id), \n" +
						"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else coalesce(b.OR_Date, b.Trans_Date) end), \n" +
						"c.Description, b.seq_no, rtrim(b.Entity_ID), rtrim(d.entity_name), h.status_desc"; 
						
						/*
						"select distinct\n" +
						"rtrim(a.rb_id) as \"CRB ID\",\n" +
						"rtrim(e.doc_desc) as \"Type\",\n" +
						"f.particulars as \"Particulars\",\n" +
						"sum(a.amount) as \"Amount\",\n" +
						"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else coalesce(b.OR_Date, b.Trans_Date) end) as \"Date Issued\",\n" +
						"rtrim(b.pbl_id) as \"PBL ID\",\n" +
						"c.Description as \"Description\",\n" +
						"b.seq_no as \"Seq.\",\n" +
						"rtrim(b.Entity_ID) as \"Client ID\",\n" +
						"rtrim(d.entity_name) as \"Client Name\", \n\n" +
						"upper(trim(h.status_desc)) \n" +

						"from rf_crb_header a --with(nolock)\n\n" +

						"left join rf_payments b --with(nolock)\n" +
						"on a.pay_rec_id::int = b.pay_Rec_ID --and b.pbl_id = d.PBL_ID and b.seq_no = d.seq_no\n" +
						"and b.status_id in ('A', 'P')\n\n" +

						"left join mf_unit_info c --with(nolock)\n" +
						"on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id --and a.phase = c.Phase\n\n" +

						"left join rf_entity d --with(nolock)\n" +
						"on b.Entity_ID = d.entity_id\n\n" +

						"left join mf_system_doc e --with(nolock)\n" +
						"on a.doc_id = e.doc_id\n\n" +

						"left join (select rb_id, particulars, pay_rec_id \n" +
						--"	ltrim(rtrim(stuff((select distinct ', ' + particulars\n" +
						--"		from rf_crb_header\n" +
						--"		where rb_id = aa.rb_id\n" +
						--"		for XML PATH('')), 1, 1, ''))) [particulars]\n" +
						"	from rf_crb_header aa\n" +
						"	group by rb_id, particulars, pay_rec_id) f\n" +
						"on a.rb_id = f.rb_id and b.pay_rec_id::int = f.pay_rec_id::int\n\n" +

						"left join rf_pay_header g --with(nolock)\n" +
						"on b.client_seqno = g.Client_seqno\n\n" +

						"left join mf_record_status h on a.status_id =  h.status_id \n" +

						"where a.proj_id = '"+ projcode +"'\n" +
						"and a.phase like '"+ phase +"%'\n" +
						"and c.Description is not null\n" +
						"and a.doc_id = '01' \n\n" +

						"group by\n" +
						"rtrim(a.rb_id),\n" +
						"rtrim(e.doc_desc),\n" +
						"f.particulars,\n" +
						"rtrim(b.pbl_id),\n" +
						"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else coalesce(b.OR_Date, b.Trans_Date) end),\n" +
						"c.Description,\n" +
						"b.seq_no,\n" +
						"rtrim(b.Entity_ID),\n" +
						"rtrim(d.entity_name)," +
						"h.status_desc ;";
						*/
			}
			//FncSystem.out("Receipt", strSQL);
		}
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println(strSQL);
		return strSQL;
	}

	public static void displayAccountEntries(String receipt_type, modelCRBAccountEntries model, modelCRBAccountEntries _modelTotal, JList rowHeader, String pbl_id, String seq_no, String co_id, String receipt_no, ArrayList<Object[]> originalEntries, Boolean toCollapse) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String strSQL = "SELECT * FROM view_crb_receipt_details_v2('"+ receipt_type +"', '"+ receipt_no +"', '"+ pbl_id +"', "+ seq_no +", '"+co_id+"');"; 
		FncSystem.out("Account Entries", strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());

				originalEntries.add(db.getResult()[x]);
				totalAccountEntries(model, _modelTotal);
			}

			if(toCollapse==true){
				collapseAccountEntries(model, _modelTotal, rowHeader);
			}
		}else{
			_modelTotal.setValueAt(null, 0, 3);//new BigDecimal(0.00)
			_modelTotal.setValueAt(null, 0, 4);//new BigDecimal(0.00)
		}
	}

	public static void expandAccountEntries(modelCRBAccountEntries model, modelCRBAccountEntries _modelTotal, JList rowHeader, ArrayList<Object[]> originalEntries) {
		if(model.getRowCount() > 0){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			for(int x=0; x < originalEntries.size(); x++){
				// Adding of row in table
				model.addRow(originalEntries.get(x));
				listModel.addElement(model.getRowCount());
			}
			totalAccountEntries(model, _modelTotal);
		}
	}

	public static void collapseAccountEntries(modelCRBAccountEntries model, modelCRBAccountEntries _modelTotal, JList rowHeader) {
		if(model.getRowCount() > 0){
			Map<String, String> mapParticulars = new HashMap<String, String>();
			Map<String, String> mapAccountID = new HashMap<String, String>();
			Map<String, List<BigDecimal>> mapDebit = new LinkedHashMap<String, List<BigDecimal>>();
			Map<String, List<BigDecimal>> mapCredit = new LinkedHashMap<String, List<BigDecimal>>();
			Map<String, String> mapRemarks = new HashMap<String, String>();
			Map<String, String> mapRecID = new HashMap<String, String>();

			int rowCount = model.getRowCount();
			for(int x=0; x < rowCount; x++){
				String particular = (String) model.getValueAt(x, 0);
				String acct_id = (String) model.getValueAt(x, 1);
				String acct_name = (String) model.getValueAt(x, 2);
				BigDecimal debit = (BigDecimal) model.getValueAt(x, 3);
				BigDecimal credit = (BigDecimal) model.getValueAt(x, 4);
				String remarks = (String) model.getValueAt(x, 5);
				String recID = (String) model.getValueAt(x, 6);

				if(debit != null && debit.compareTo(new BigDecimal(0.00)) == 1){
					mapParticulars.put(acct_name, particular);
					mapAccountID.put(acct_name, acct_id);
					mapRemarks.put(acct_name, remarks);
					mapRecID.put(acct_name, recID);

					if(!mapDebit.containsKey(acct_name)){
						List<BigDecimal> listDebit = new ArrayList<BigDecimal>();
						listDebit.add(debit);

						mapDebit.put(acct_name, listDebit);
					}else{
						List<BigDecimal> listDebit = mapDebit.get(acct_name);
						listDebit.add(debit);

						mapDebit.put(acct_name, listDebit);
					}
					//System.out.println(mapDebit.get(acct_name));
				}
				if(credit != null && credit.compareTo(new BigDecimal(0.00)) == 1){
					mapParticulars.put(acct_name, particular);
					mapAccountID.put(acct_name, acct_id);
					mapRemarks.put(acct_name, remarks);
					mapRecID.put(acct_name, recID);

					if(!mapCredit.containsKey(acct_name)){
						List<BigDecimal> listDebit = new ArrayList<BigDecimal>();
						listDebit.add(credit);

						mapCredit.put(acct_name, listDebit);
					}else{
						List<BigDecimal> listDebit = mapCredit.get(acct_name);
						listDebit.add(credit);

						mapCredit.put(acct_name, listDebit);
					}
					//System.out.println(mapCredit.get(acct_name));
				}
			}

			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			//System.out.println("\n\n*****Debit");
			for(String key : mapDebit.keySet()){
				List<BigDecimal> listDebit = mapDebit.get(key);

				model.addRow(new Object[]{mapParticulars.get(key), mapAccountID.get(key), key, sum(listDebit), null, mapRemarks.get(key), mapRecID.get(key)});
				listModel.addElement(model.getRowCount());
				//System.out.printf("%s %s %n", key, sum(listDebit));
			}

			//System.out.println("\n\n*****Credit");
			for(String key : mapCredit.keySet()){
				List<BigDecimal> listCredit = mapCredit.get(key);

				model.addRow(new Object[]{mapParticulars.get(key), mapAccountID.get(key), key, null, sum(listCredit), mapRemarks.get(key), mapRecID.get(key)});
				listModel.addElement(model.getRowCount());
				//System.out.printf("%s %s %n", key, sum(listCredit));
			}

			totalAccountEntries(model, _modelTotal);
		}
	}

	public static void totalAccountEntries(modelCRBAccountEntries model, modelCRBAccountEntries _modelTotal) {
		BigDecimal amountDebit = new BigDecimal(0.00);
		BigDecimal amountCredit = new BigDecimal(0.00);

		for(int x=0; x < model.getRowCount(); x++){
			try {
				amountDebit = amountDebit.add(((BigDecimal) model.getValueAt(x, 3)));
			} catch (NullPointerException e1) {
				amountDebit = amountDebit.add(new BigDecimal(0.00));
			}
			try {
				amountCredit = amountCredit.add(((BigDecimal) model.getValueAt(x, 4)));
			} catch (NullPointerException e) {
				amountCredit = amountCredit.add(new BigDecimal(0.00));
			}
		}
		_modelTotal.setValueAt(amountDebit, 0, 3);
		_modelTotal.setValueAt(amountCredit, 0, 4);
	}

	public static BigDecimal sum(List<BigDecimal> listAmount) {
		BigDecimal total = new BigDecimal(0.00);
		for(BigDecimal amount : listAmount){
			total = total.add(amount);
		}
		return total;
	}

	public static String getReceiptSQL(String receipt_type, String strCoID, String projcode, String phase) {
		String strSQL = "";
		if(receipt_type != null){
			if(receipt_type.equals("AR")){

				strSQL = "SELECT distinct ltrim(rtrim(a.rb_id)) as \"CRB ID\", ltrim(rtrim(e.doc_desc)) as \"Type\", rtrim(a.particulars) as \"Particulars\", \n" +
						"sum(a.amount) as \"Amount\", " +
						"b.Trans_Date as \"Date Issued\", " + 
						//"coalesce(b.OR_Date, b.Trans_Date) as \"Date Issued\", " + //--edited by D.G. based on DCRF No. 391
						//"(case when b.OR_Date is null or to_char(or_date,'yy')::int >= 18 then b.Trans_Date" +
						//"	else b.OR_Date end) as \"Date Issued\", " +
						"coalesce(ltrim(rtrim(b.pbl_id)),'') as \"PBL ID\", \n" +
						"coalesce(c.Description, '') as \"Description\", coalesce (b.seq_no, 0) as \"Seq.\", ltrim(rtrim(b.Entity_ID)) as \"Client ID\", \n" +
						"ltrim(rtrim(d.entity_name)) as \"Client Name\", upper(trim(f.status_desc)) \n" +
						"from rf_crb_header a \n" +
						"left join \n" +
						//"(select or_date, trans_date, pbl_id, seq_no, entity_id, pay_Rec_ID, status_id, proj_id, client_seqno, coalesce(NULLIF(or_no, ''), ar_no) as receipt_no \n" + // MODIFIED BY MONIQUE DTD 03-22-2023
						"(select or_date, trans_date, pbl_id, seq_no, entity_id, pay_Rec_ID, status_id, proj_id, client_seqno, (CASE WHEN '"+receipt_type+"' = 'AR' THEN COALESCE(ar_no, or_no) when '"+receipt_type+"' = 'OR' THEN or_no ELSE si_no END) as receipt_no \n" +
						"from rf_payments \n" +
						"union all \n" +
						"select a.trans_date, a.booking_date, a.pbl_id, a.seq_no, a.entity_id, a.pay_header_ID, a.status_id, a.proj_id, a.client_seqno, b.receipt_no \n" +
						"from rf_pay_header a \n" +
						"left join rf_pay_detail b on a.client_seqno = b.client_seqno \n" +  
						"where ((b.receipt_type = '03' OR b.part_type IN (SELECT x.pay_part_id FROM mf_pay_particular x WHERE partdesc LIKE '%CASH%' AND COALESCE(receipt_to_issue, '') <> '')) or b.part_type IN ('191', '275')) \n" + 
						"union all \n" +
						"select trans_date, actual_date, pbl_id, seq_no, entity_id, tra_header_ID, status_id, proj_id, client_seqno, receipt_no \n" +
						//"from rf_tra_header) b on a.pay_rec_id::int = b.pay_Rec_ID and a.reference_no = b.client_seqno \n" + COMMENTED BY LESTER TO DISPLAY RIGHT ENTRIES FOR MERALCO FUND
						"from rf_tra_header) b on a.pay_rec_id::int = b.pay_Rec_ID and a.rb_id = b.receipt_no --and a.reference_no = b.client_seqno \n" + 
						"left join mf_unit_info c  on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id \n" +
						"left join rf_entity d  on b.Entity_ID = d.entity_id \n" +
						"left join mf_system_doc e on a.doc_id = e.doc_id \n" +
						"left join mf_record_status f on a.status_id =  f.status_id \n" + 
						"where (a.co_id = '"+strCoID+"' or '"+strCoID+"' = '') and (a.proj_id = '"+ projcode +"' or '"+ projcode +"' = '' or '"+ projcode +"' is null) \n" +
						"and (a.phase = '"+ phase +"' or '"+ phase +"' = '' or '"+ phase +"' is null) and a.doc_id = '03' \n" + 
						"group by a.proj_id, a.phase, ltrim(rtrim(a.rb_id)), ltrim(rtrim(e.doc_desc)), a.particulars, ltrim(rtrim(b.pbl_id)), \n" +
						"c.Description, b.seq_no, ltrim(rtrim(b.Entity_ID)), ltrim(rtrim(d.entity_name)), b.Trans_Date, f.status_desc"; 
						
			} else if (receipt_type.equals("SI")) { /*Added by Monique dtd 10-11-2022; FOR Receipt Details of Sales Invoice*/
				strSQL = "select distinct rtrim(a.rb_id) as \"CRB ID\", rtrim(e.doc_desc) as Type, f.particulars as Particulars, sum(a.amount) as Amount, \n" +
						"(case when rtrim(f.particulars) = 'LNREL' then a.issued_date when b.si_date is null or to_char(si_date,'yy')::int >= 18 and b.remarks !~* 'Late LTS/BOI' and b.remarks !~* 'Late OR Issuance for Good Check' then b.Trans_Date else b.si_date end) as \"Date Issued\", \n" +
						"rtrim(b.pbl_id) as \"PBL ID\", c.Description as Description, b.seq_no as \"Seq.\", rtrim(b.Entity_ID) as \"Client ID\", \n" +
						"rtrim(d.entity_name) as \"Client Name\", upper(trim(h.status_desc)) \n" +
						"from rf_crb_header a \n" +
						"left join rf_payments b on a.pay_rec_id::int = b.pay_Rec_ID and TRIM(b.status_id) in ('A', 'P', 'I') --ADDED STATUS ID INACTIVE TO DISPLAY PAYMENTS OF CHANGE PA\n" +
						"left join mf_unit_info c on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id \n" +
						"left join rf_entity d on b.Entity_ID = d.entity_id \n" +
						"left join mf_system_doc e on a.doc_id = e.doc_id \n" +
						"left join (select rb_id, particulars, pay_rec_id from rf_crb_header aa group by rb_id, particulars, pay_rec_id) f on a.rb_id = f.rb_id and b.pay_rec_id::int = f.pay_rec_id::int \n" +
						"left join rf_pay_header g on b.client_seqno = g.Client_seqno \n" +
						"left join mf_record_status h on a.status_id =  h.status_id \n" +
						"where (a.co_id = '"+strCoID+"' or '"+strCoID+"' = '') and (a.proj_id = '"+ projcode +"' or '"+ projcode +"' = '' or '"+ projcode +"' is null) \n" +
						"and (a.phase = '"+ phase +"' or '"+ phase +"' = '' or '"+ phase +"' is null) \n" +
						"and a.doc_id = '307' \n" +
						"group by rtrim(a.rb_id), rtrim(e.doc_desc), f.particulars, rtrim(b.pbl_id), \n" +
						"(case when rtrim(f.particulars) = 'LNREL' then a.issued_date else " +
						"(case when b.si_Date is null or to_char(si_date,'yy')::int >= 18 then b.Trans_Date" +
						"	else b.si_date end)" +
						"end), \n" +
						"c.Description, b.seq_no, rtrim(b.Entity_ID), rtrim(d.entity_name), h.status_desc, a.issued_date, b.si_date, b.Trans_Date, b.remarks"; 
				
			} else {
				strSQL = "select distinct rtrim(a.rb_id) as \"CRB ID\", rtrim(e.doc_desc) as Type, f.particulars as Particulars, sum(a.amount) as Amount, \n" +
						//"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else " +
						//"coalesce(b.OR_Date, b.Trans_Date) " +--edited by D.G. based on DCRF No. 391
						//Modified by Mann2x; Date Modifed: February 28, 2018; 
						//"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else (case when b.OR_Date is null or to_char(or_date,'yy')::int >= 18 then b.Trans_Date else b.OR_Date end) end) as \"Date Issued\", \n" + 
						"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date when b.OR_Date is null or to_char(or_date,'yy')::int >= 18 and b.remarks !~* 'Late LTS/BOI' and b.remarks !~* 'Late OR Issuance for Good Check' then b.Trans_Date else b.OR_Date end) as \"Date Issued\", \n" +
						"rtrim(b.pbl_id) as \"PBL ID\", c.Description as Description, b.seq_no as \"Seq.\", rtrim(b.Entity_ID) as \"Client ID\", \n" +
						"rtrim(d.entity_name) as \"Client Name\", \n"+
						"upper(trim(h.status_desc)) \n"+
						//"case when (a.canceled_date is not null and a.status_id= 'I') THEN 'CANCELLED' WHEN a.status_id = 'I' AND a.canceled_date is null then 'INACTIVE' ELSE upper(trim(h.status_desc)) end\n" +
						"from rf_crb_header a \n" +
						"left join rf_payments b on a.pay_rec_id::int = b.pay_Rec_ID and TRIM(b.status_id) in ('A', 'P', 'I') --ADDED STATUS ID INACTIVE TO DISPLAY PAYMENTS OF CHANGE PA\n" +
						"left join mf_unit_info c on b.pbl_id = c.Pbl_ID and b.proj_id = c.Proj_id \n" +
						"left join rf_entity d on b.Entity_ID = d.entity_id \n" +
						"left join mf_system_doc e on a.doc_id = e.doc_id \n" +
						"left join (select rb_id, particulars, pay_rec_id from rf_crb_header aa group by rb_id, particulars, pay_rec_id) f on a.rb_id = f.rb_id and b.pay_rec_id::int = f.pay_rec_id::int \n" +
						"left join rf_pay_header g on b.client_seqno = g.Client_seqno \n" +
						"left join mf_record_status h on a.status_id =  h.status_id \n" +
						"where (a.co_id = '"+strCoID+"' or '"+strCoID+"' = '') and (a.proj_id = '"+ projcode +"' or '"+ projcode +"' = '' or '"+ projcode +"' is null) \n" +
						"and (a.phase = '"+ phase +"' or '"+ phase +"' = '' or '"+ phase +"' is null) \n" +
						"and a.doc_id = '01' \n" +
						"group by rtrim(a.rb_id), rtrim(e.doc_desc), f.particulars, rtrim(b.pbl_id), \n" +
						"(case when rtrim(f.particulars) = 'LNREL' then g.Booking_Date else " +
						//"coalesce(b.OR_Date, b.Trans_Date) " +
						"(case when b.OR_Date is null or to_char(or_date,'yy')::int >= 18 then b.Trans_Date" +
						"	else b.OR_Date end)" +
						"end), \n" +
						"c.Description, b.seq_no, rtrim(b.Entity_ID), rtrim(d.entity_name), h.status_desc, g.Booking_Date, b.OR_Date, b.Trans_Date, b.remarks"; 
			}
		}
		
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println(strSQL);
		return strSQL;
	}
}
