package Projects.BiddingandAwarding;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelNTPProgressBilling;
import tablemodel.modelNTPWorkItems;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;
import components._DB;
import components._JCheckListItem;

public class _NoticeToProceed extends _DB {

	public static String NTPNo() {
		String sql = "select ntp_no as \"NTP No.\", contract_no as \"Contract No.\", contractor_id as \"Contractor ID\", contractor_name as \"Contractor\", status_id as \"Status\",\n" + 
				"project as \"Project\", company as \"Company\"\n" + 
				"from (select trim(a.ntp_no) as ntp_no,\n" + 
				"	trim(a.contract_no) as contract_no,\n" + 
				"	trim(a.entity_id) as contractor_id,\n" + 
				"	trim(b.entity_name) as contractor_name,\n" + 
				"	trim(c.proj_alias) as project,\n" + 
				"	trim(d.company_alias) as company, a.status_id as status_id\n" + 
				"	from co_ntp_header a\n" + 
				"	left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"	left join mf_project c on c.proj_id = a.proj_id\n" + 
				"	left join mf_company d on d.co_id = a.co_id\n" +
				//"	where a.status_id = 'A'\n" + 
				"	) a\n" + 
				"group by ntp_no, contract_no, contractor_id, contractor_name, project, company, status_id\n" + 
				"order by ntp_no::integer desc;";
		System.out.printf("SQL: %s%n", sql);
		return sql;
	}

	public static String NTPType() {
		String sql = "select distinct type_id as \"ID\", trim(type_desc) as \"Description\", trim(type_alias) as \"Alias\" from mf_ntp_type order by type_id";
		return sql;
	}

	public static  ArrayList<_JCheckListItem> getPhase(String ntp_no, String co_id, String proj_id) {
		ArrayList<_JCheckListItem> listCheckItem = new ArrayList<_JCheckListItem>();

		String sql = "";
		
		if(co_id.equals("01") && proj_id.equals("019")) { //added by lester for because of null contract no. for housing
			sql = "SELECT * FROM view_ntp_phase_v2('"+ ntp_no +"', '"+ co_id +"', '"+ proj_id +"');";
		}else {
			sql = "SELECT * FROM view_ntp_phase('"+ ntp_no +"', '"+ co_id +"', '"+ proj_id +"');";
		}
		
		FncSystem.out("Phase", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				String phase_no = (String) db.getResult()[x][0];
				boolean isSelected = (Boolean) db.getResult()[x][1];

				listCheckItem.add(new _JCheckListItem(phase_no, isSelected));
			}
			return listCheckItem;
		}else{
			return null;
		}
	}
	
	public static  ArrayList<_JCheckListItem> getBlock(String co_id, String proj_id, String phase) {
		ArrayList<_JCheckListItem> listCheckItem1 = new ArrayList<_JCheckListItem>();

		String sql = "SELECT * FROM view_ntp_block('"+ co_id +"', '"+ proj_id +"', '"+ phase +"');";

		FncSystem.out("Block", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				String block = (String) db.getResult()[x][0];
				boolean isSelected = (Boolean) db.getResult()[x][1];

				listCheckItem1.add(new _JCheckListItem(block, isSelected));
			}
			return listCheckItem1;
		}else{
			return null;
		}
	}

	public static String Contractor() {
		String sql = "SELECT a.*, COALESCE(b.tin_no, '') AS \"TIN\" \n"+
				     "FROM view_ntp_contractors() a \n"+
				     "LEFT JOIN rf_entity_id_no b on b.entity_id = a.\"ID\";";
		return sql;
	}

	public static String AddUnits(String proj_id, String phase, String ntp_type_id, String cse_type) {
		String sql = "SELECT * FROM view_ntp_units('"+ proj_id +"', "+ phase +", '"+ ntp_type_id +"', '"+ cse_type +"', null, null);";
		System.out.println(sql);
		return sql;
	}
	public static String AddUnitsSRDC(String proj_id, String phase, String ntp_type_id, String cse_type) {
		String sql = "SELECT * FROM view_ntp_unitsSRDC('"+ proj_id +"', "+ phase +", '"+ ntp_type_id +"', '"+ cse_type +"', null, null);";
		System.out.println(sql);
		return sql;
	}

	public static String AddSpecialProjectUnits(String proj_id, String phase, String ntp_type_id, String cse_type, Map<String, ArrayList<String>> map) {
			ArrayList<String> listBlocks = new ArrayList<String>();
			ArrayList<String> listLots = new ArrayList<String>();
		
			for(Entry<String, ArrayList<String>> entry : map.entrySet()){
				listBlocks.add(String.format("'%s'", entry.getKey()));
				listLots.add(entry.getValue().toString().replaceAll("\\[|\\]", "'").replaceAll("[,]", "$0 ").replaceAll("\\s+", " "));
			}
		
			System.out.printf("List Blocks: %s%n", listBlocks.toString());
			System.out.printf("List Lots: %s%n", listLots.toString());
			
			String sql = "SELECT * FROM view_ntp_units('"+ proj_id +"', "+ phase +", '"+ ntp_type_id +"', '"+ cse_type +"', ARRAY["+ (listBlocks.size() > 0 ? listBlocks.toString().replaceAll("\\[|\\]", ""):"null") +"], ARRAY["+ (listLots.size() > 0 ? listLots.toString().replaceAll("\\[|\\]", ""):"null") +"]);";
			System.out.println(sql);
			return sql;
	}

	public static String UOM() {
		String sql = "select distinct trim(uom_id) as \"ID\", trim(uom_desc) as \"Description\" from mf_unit_of_measure order by trim(uom_desc);";
		return sql;
	}

	public static String generateNewNTPNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(ntp_no::INT), 0) + 1, 'FM000000') FROM co_ntp_header;";

		FncSystem.out("NTP No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	public static Object[] getContractInfo(String ntp_no) {
		String sql = "select\n" +
				"trim(a.contract_no),\n" +
				"trim(a.ntp_type_id),\n" +
				"trim(b.type_desc) || ' (' || (select contract[array_length(contract)] from (select regexp_split_to_array(contract_no, '-') as contract from co_ntp_header where ntp_no = '"+ ntp_no +"') l) || ')' as ntp_type_desc,\n" +
				"trim(a.co_id),\n" +
				"trim(c.company_name) as co_name,\n" +
				"trim(a.proj_id),\n" +
				"trim(d.proj_name) || ' (' || trim(d.proj_alias) || ')' as proj_name,\n" +
				"trim(a.entity_id),\n" +
				"trim(e.entity_name) as entity_name,\n" +
				"a.dp_percentage,\n" +
				"a.with_surety_bond,\n" +
				"a.reference_no,\n" +
				"a.start_date,\n" +
				"a.finish_date,\n" +
				"a.ntp_date,\n" +
				"a.award_date,\n" +
				"a.coc_date,\n" +
				"a.coc_expiry_date,\n" +
				"exists(select * from co_billing_detail where ntp_no = '"+ ntp_no +"' and status_id != 'I'),\n" +
				"f.ntp_subject_description,\n" + 
				"a.extension_date,\n" + 
				"(case when a.status_id = 'A' then 'ACTIVE' else 'CANCEL' end),\n" +
				"e.vat_registered, COALESCE(g.tin_no, ''), a.remarks \n"+
				"from co_ntp_header a\n" +
				"left join mf_ntp_type b on b.type_id = a.ntp_type_id\n" +
				"left join mf_company c on c.co_id = a.co_id\n" +
				"left join mf_project d on d.proj_id = a.proj_id\n" +
				"left join rf_entity e on e.entity_id = a.entity_id\n" +
				"LEFT JOIN co_ntp_subjects f ON f.ntp_no = a.ntp_no\n" + 
				"LEFT JOIN rf_entity_id_no g on g.entity_id = a.entity_id \n"+

				"where a.ntp_no = '"+ ntp_no +"';\n";

		System.out.printf("Contract Info: %s%n", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.getResult() != null){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static void displayWorkItems(modelNTPWorkItems model, JList rowHeader, String ntp_no) {
		model.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		/*
		String strSQL = "select\n" +
				"a.seq_no::int,\n" +
				"trim(a.work_desc) as work_desc,\n" +
				"upper(trim(a.uom_id)) as uom_id,\n" +
				"a.work_qty,\n" +
				"a.other_cost,\n" +
				"a.other_cost,\n" +
				"a.other_cost * a.work_qty as total_unit_cost,\n" +
				"nullif(trim(a.pbl_id), '')::int as pbl_id,\n" +
				"trim(b.description) as description,\n" +
				"d.hse_color as color_scheme, \n" +
				"a.work_percent,\n" +
				"trim(a.pe_no) as pe_no\n" +
				"from co_ntp_detail a\n" +
				"left join mf_unit_info b on b.pbl_id = a.pbl_id \n" +
				"left join (select * from rf_marketing_scheme_pricelist where status_id = 'A') d on b.proj_id = d.proj_id\n" + 
				"and b.phase = d.phase and b.block = d.block and b.lot = d.lot \n" +
				"where a.ntp_no = '"+ ntp_no +"'\n" +
				//"and a.status_id = 'A'\n" +
				"order by a.seq_no::int;";
		*/
		
		String strSQL = "select a.seq_no::int, trim(a.work_desc) as work_desc, upper(trim(a.uom_id)) as uom_id, a.work_qty, a.other_cost, a.other_cost, \n" + 
				"a.other_cost * a.work_qty as total_unit_cost, nullif(trim(a.pbl_id), '')::int as pbl_id, trim(b.description) as description, \n" + 
				"(select x.hse_color from rf_marketing_scheme_pricelist x where x.proj_id = b.proj_id and x.phase = b.phase and x.block = b.block and x.lot = b.lot and x.status_id = 'A' order by date_created desc, version_no desc limit 1) as color_scheme, \n" + 
				"a.work_percent, \n" + 
				"trim(a.pe_no) as pe_no \n" + 
				"from co_ntp_detail a \n" + 
				"left join mf_unit_info b on b.pbl_id = a.pbl_id --and coalesce(b.server_id, '') = coalesce(a.server_id, '') \n" + //Added by Monique 7-4-22 - to filter server id  
				"where a.ntp_no = '"+ntp_no+"' and a.status_id = 'A' \n" +
				"and case when a.pbl_id IS NULL THEN TRUE ELSE b.proj_id = (SELECT case when proj_id = '016' then '015' else proj_id end from co_ntp_header where ntp_no = '"+ntp_no+"') end \n"+
				"order by a.seq_no::int"; 
		
		System.out.println("");
		System.out.println("strSQL: "+strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}


		/*String contract_code = null;
		if(ntp_tpye_id.equals("01")){
			contract_code = "L";
		}
		if(ntp_tpye_id.equals("02")){
			contract_code = "H";
		}
		if(ntp_tpye_id.equals("04")){
			contract_code = "CF";
		}
		if(ntp_tpye_id.equals("05")){
			contract_code = "SP";
		}
		if(ntp_tpye_id.equals("06") || ntp_tpye_id.equals("08")){
			contract_code = "MIS";
		}
		if(ntp_tpye_id.equals("07")){
			contract_code = "OSP";
		}

		String sql = "select to_char(max(getinteger(contract[array_length(contract)])) +1, 'FM000') || gettext(contract[array_length(contract)]) as contract_code\n" +
				"from (select *, regexp_split_to_array(contract_no, '-') as contract from co_ntp_header where proj_id = '"+ proj_id +"' and status_id = 'A') a\n" +
				"where gettext(contract[array_length(contract)]) = '"+ contract_code +"'\n" +
				"group by gettext(contract[array_length(contract)]);";

		FncSystem.out("Contract Code", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return String.format("%s%s", "001", contract_code);
		}
	}*/
	
	/*public static String contractCodeSP(String ntp_tpye_id, String proj_id, String sp_type) {
		//FncSystem.out("Contract Code", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		return (String) db.getResult()[0][0];
	}*/

	public static void displayProgressBilling(modelNTPProgressBilling model, JList rowHeader, String ntp_no) {
		model.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String strSQL = "select\n" +
				"b.billingtype_alias,\n" +
				"a.billing_date,\n" +
				"nullif(a.accomp_pctg, 0),\n" +
				"nullif(a.gross_amt, 0),\n" +
				"nullif(a.vat_amt, 0),\n" +
				"nullif(a.wtax_amt, 0),\n" +
				"nullif(a.dp_recoupment, 0),\n" +
				"nullif(a.retention_amt, 0),\n" +
				"nullif(a.ca_liquidation, 0),\n" +
				"nullif(a.other_deductions, 0),\n" +
				"nullif(a.net_amt, 0),\n" +
				"a.billing_no,\n" +
				"a.rplf_no,\n" +
				"a.jv_no,\n" +
				"to_char(d.date_paid,  'MM/dd/yyyy') as date_released,\n" +
				"(case when d.date_paid is not null then 'RELEASED' else " +
				"	(case when c.status_id = 'F' then 'TAGGED' else \r\n" + 
				"	(case when c.status_id = 'P' then 'POSTED' else \r\n" + 
				"	(case when c.status_id = 'A' then 'ACTIVE' else 'INACTIVE' end) end) end) end ) as status \r\n" + 

				"from co_billing_detail a\n" +
				"left join mf_billing_type b on b.billingtype_id = a.billing_type\n" +
				"left join rf_pv_header c on a.rplf_no = c.pv_no and a.co_id = c.co_id  \n" +
				"left join rf_cv_header d on c.cv_no = d.cv_no and c.co_id =d.co_id  \n" +

				"where a.ntp_no = '"+ ntp_no +"'\n" +
				"and a.status_id != 'I'\n" +

				"order by ntp_no desc, a.rec_id;";

		//System.out.printf("Progress Billing%n%s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	public static void saveNTP(String ntp_no,
			Timestamp ntp_date, 
			String co_id, 
			String proj_id,
			String entity_id,
			Timestamp start_date, 
			Timestamp finish_date,
			Timestamp award_date,
			String contract_no,
			BigDecimal contract_price,
			BigDecimal orig_contract_price,
			String ntp_type_id,
			Object dp_percentage, 
			boolean with_surety_bond,
			String remarks,
			boolean is_takeover_ntp,
			String takenover_ntpno,
			BigDecimal dp_based_on_contract_price,
			Timestamp coc_date,
			Timestamp coc_expiry_date,
			Double vat_rate, 
			String subject,
			modelNTPWorkItems modelNTP,
			Timestamp extension_date,
			String strDesc,
			String sub_proj_id) {

		//select the URL that were going to save the data in MS SQL
		//pgSelect dbPgSelect = new pgSelect();
		//dbPgSelect.select("SELECT url as dbase FROM mf_url WHERE co_id = '"+ co_id +"' AND proj_id = '"+ proj_id +"';");

//		String pgsqlHeader = "INSERT INTO co_ntp_header(\n" + 
//				"	ntp_no,"
//				+ " ntp_date, "
//				+ "co_id,"
//				+ " proj_id, "
//				+ "entity_id, "
//				+ "start_date, "
//				+ "finish_date, "
//				+ "award_date, "
//				+ "contract_no, "
//				+ "contract_price,"
//				+ " orig_contract_price,"
//				+ " ntp_type_id, dp_percentage,"
//				+ " reference_no, with_surety_bond,"
//				+ " remarks, is_takeover_ntp,"
//				+ " takenover_ntpno, "
//				+ "dp_based_on_contract_price, "
//				+ "coc_date,"
//				+ " coc_expiry_date, "
//				+ "vat_rate,"
//				+ "status_id,"
//				+ " created_by, "
//				+ "date_created,"
//				+ "sub_proj_id) VALUES (" +
//				"'"+ ntp_no +"', --ntp_no\n" +
//				"nullif('"+ ntp_date +"', 'null')::timestamp, --ntp_date\n" +
//				"'"+ co_id +"', --co_id\n" +
//				"'"+ proj_id +"', --proj_id\n" +
//				"'"+ entity_id +"', --entity_id\n" +
//				"nullif('"+ start_date +"', 'null')::timestamp, --start_date\n" +
//				"nullif('"+ finish_date +"', 'null')::timestamp, --finish_date\n" +
//				"nullif('"+ award_date +"', 'null')::timestamp, --award_date\n" +
//				"'"+ contract_no +"', --contract_no\n" +
//				""+ contract_price +", --contract_price\n" +
//				""+ orig_contract_price +", --orig_contract_price\n" +
//				"'"+ ntp_type_id +"', --ntp_type_id\n" +
//				""+ dp_percentage +", --dp_percentage\n" +
//				//"nullif('"+ reference_no +"', 'null'), --reference_no\n" +
//				"null, --reference_no\n" +
//				""+ with_surety_bond +", --with_surety_bond\n" +
//				"nullif('"+ remarks +"', 'null'), --remarks\n" +
//				""+ is_takeover_ntp +", --is_takeover_ntp\n" +
//				"nullif('"+ takenover_ntpno +"', 'null'), --takenover_ntpno\n" +
//				""+ dp_based_on_contract_price +", --dp_based_on_contract_price\n" +
//				"nullif('"+ coc_date +"', 'null')::timestamp, --coc_date\n" +
//				"nullif('"+ coc_expiry_date +"', 'null')::timestamp, --coc_expiry_date\n" +
//				""+ vat_rate +", --vat_rate\n" +
//				"'A',--status_id\n" +
//				"'"+ UserInfo.EmployeeCode +"', --created_by\n" +
//				"now(), null, null, nullif('"+extension_date+"', 'null')::timestamp, null, null, '"+strDesc+"'"
//				+ "); ";
		String pgsqlHeader = "INSERT INTO co_ntp_header(\n" + 
				"	ntp_no, "
				+ "ntp_date, "
				+ "co_id, "
				+ "proj_id, "
				+ "entity_id, "
				+ "start_date, "
				+ "finish_date, "
				+ "award_date, "
				+ "contract_no,"
				+ " contract_price, "
				+ "orig_contract_price, "
				+ "ntp_type_id, "
				+ "dp_percentage,"
				+ " reference_no, "
				+ "with_surety_bond,"
				+ " remarks,"
				+ " is_takeover_ntp, "
				+ "takenover_ntpno, "
				+ "dp_based_on_contract_price, "
				+ "coc_date,"
				+ " coc_expiry_date,"
				+ " vat_rate,"
				+ " status_id,"
				+ " created_by, "
				+ "date_created,"
				+ " edited_by, "
				+ "date_edited, "
				+ "extension_date,"
				+ " deleted_by, "
				+ "deleted_date, "
				+ "oth_description,"
				+ "  sub_proj_id) VALUES (" +
				"'"+ ntp_no +"', --ntp_no\n" +
				"nullif('"+ ntp_date +"', 'null')::timestamp, --ntp_date\n" +
				"'"+ co_id +"', --co_id\n" +
				"'"+ proj_id +"', --proj_id\n" +
				"'"+ entity_id +"', --entity_id\n" +
				"nullif('"+ start_date +"', 'null')::timestamp, --start_date\n" +
				"nullif('"+ finish_date +"', 'null')::timestamp, --finish_date\n" +
				"nullif('"+ award_date +"', 'null')::timestamp, --award_date\n" +
				"'"+ contract_no +"', --contract_no\n" +
				""+ contract_price +", --contract_price\n" +
				""+ orig_contract_price +", --orig_contract_price\n" +
				"'"+ ntp_type_id +"', --ntp_type_id\n" +
				""+ dp_percentage +", --dp_percentage\n" +
				//"nullif('"+ reference_no +"', 'null'), --reference_no\n" +
				"null, --reference_no\n" +
				""+ with_surety_bond +", --with_surety_bond\n" +
				"nullif('"+ remarks +"', 'null'), --remarks\n" +
				""+ is_takeover_ntp +", --is_takeover_ntp\n" +
				"nullif('"+ takenover_ntpno +"', 'null'), --takenover_ntpno\n" +
				""+ dp_based_on_contract_price +", --dp_based_on_contract_price\n" +
				"nullif('"+ coc_date +"', 'null')::timestamp, --coc_date\n" +
				"nullif('"+ coc_expiry_date +"', 'null')::timestamp, --coc_expiry_date\n" +
				""+ vat_rate +", --vat_rate\n" +
				"'A',--status_id\n" +
				"'"+ UserInfo.EmployeeCode +"', --created_by\n" +
				"now(), null, null, nullif('"+extension_date+"', 'null')::timestamp, null, null, '"+strDesc+"',nullif('"+sub_proj_id+"', 'null')); --date_created,extension_date";
		System.out.println(pgsqlHeader);
		pgUpdate dbPgUpdate = new pgUpdate();
		dbPgUpdate.executeUpdate(pgsqlHeader, true);
		
		insertAudit_trail("Add-NTP-Header", remarks.concat(strDesc), dbPgUpdate);

		/**
		 * loop on work items
		 */
		for(int x=0; x < modelNTP.getRowCount(); x++){
			Integer pbl_id = (Integer) modelNTP.getValueAt(x, 7);
			String work_desc = (String) modelNTP.getValueAt(x, 1);
			
			String pgsqlDetail = "INSERT INTO co_ntp_detail(\n" +
					"	ntp_no, seq_no, pe_no, work_desc, work_qty, uom_id, labor_cost, \n" +
					"   material_cost, other_cost, pbl_id, work_percent, remarks, taken_over, \n" +
					"   takenover_recid, partial_accomp_amt, status_id, created_by, date_created)\n" +
					"VALUES (" +
					"'"+ ntp_no +"', --ntp_no\n" +
					"'"+ modelNTP.getValueAt(x, 0) +"', --seq_no\n" +
					"nullif('"+ modelNTP.getValueAt(x, 11) +"', 'null'), --pe_no\n" +
					"'"+ modelNTP.getValueAt(x, 1) +"', --work_desc\n" +
					"'"+ modelNTP.getValueAt(x, 3) +"', --work_qty\n" +
					"'"+ modelNTP.getValueAt(x, 2) +"', --uom_id\n" +
					"0.0000, --labor_cost\n" +
					"0.0000, --material_cost\n" +
					"'"+ modelNTP.getValueAt(x, 4) +"', --other_cost\n" +
					"nullif('"+ pbl_id +"', 'null'), --pbl_id\n" +
					"nullif('"+ modelNTP.getValueAt(x, 10) +"', 'null')::real, --work_percent\n" +
					"nullif('"+ remarks +"', ''), --remarks\n" +
					""+ is_takeover_ntp +", --taken_over\n" +
					"nullif('"+ takenover_ntpno +"', 'null')::int, --takenover_recid\n" +
					"null, --partial_accomp_amt\n" +
					"'A', --status_id\n" +
					"'"+ UserInfo.EmployeeCode +"', --created_by\n" +
					"now()); --date_created";
			
			dbPgUpdate.executeUpdate(pgsqlDetail, true);
			
			insertAudit_trail("Add-NTP-Detail- Work_Desc", (String) modelNTP.getValueAt(x, 1) , dbPgUpdate);
		}
		String pgSubject = "INSERT INTO co_ntp_subjects(ntp_no, ntp_subject_description, created_by, date_created)\n" + 
				"    VALUES ('"+ ntp_no +"', '"+ subject +"', '"+ UserInfo.EmployeeCode +"', now());";
		dbPgUpdate.executeUpdate(pgSubject, true);

		dbPgUpdate.commit();
		
	}

	public static void updateNTP(String ntp_no, Timestamp ntp_date, String co_id, String proj_id, String entity_id,
			Timestamp start_date, Timestamp finish_date, Timestamp award_date, String contract_no, BigDecimal contract_price,
			BigDecimal orig_contract_price,String ntp_type_id, Object dp_percentage, 
			boolean with_surety_bond, String remarks, boolean is_takeover_ntp, String takenover_ntpno,
			BigDecimal dp_based_on_contract_price, Timestamp coc_date, Timestamp coc_expiry_date, Double vat_rate, String subject,
			modelNTPWorkItems modelNTP, Timestamp extension_date, String strDesc) { 

		//Update co_ntp_header in PostgresSQL
		String pgsqlHeader = "UPDATE co_ntp_header\n" +
				"   SET ntp_date=nullif('"+ ntp_date +"', 'null')::timestamp, co_id='"+ co_id +"', proj_id='"+ proj_id +"', entity_id='"+ entity_id +"', start_date=nullif('"+ start_date +"', 'null')::timestamp, \n" + 
				"       finish_date=nullif('"+ finish_date +"', 'null')::timestamp, award_date=nullif('"+ award_date +"', 'null')::timestamp, contract_price="+ contract_price +", \n" +  //REMOVED CONTRACT NUMBER TO THIS LINE
				"       orig_contract_price="+ orig_contract_price +", ntp_type_id='"+ ntp_type_id +"', dp_percentage="+ dp_percentage +", \n" + 
				"       with_surety_bond="+ with_surety_bond +", remarks=nullif('"+ remarks +"', 'null'), \n" + 
				"       dp_based_on_contract_price="+ dp_based_on_contract_price +", coc_date=nullif('"+ coc_date +"', 'null')::timestamp, coc_expiry_date=nullif('"+ coc_expiry_date +"', 'null')::timestamp, \n" + 
				"       vat_rate="+ vat_rate +", status_id='A', edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now(), extension_date = nullif('"+ extension_date +"', 'null')::timestamp, \n" +
				"		oth_description='"+strDesc+"'\n"+
				" WHERE ntp_no = '"+ ntp_no +"';\n";

		pgUpdate dbPgUpdate = new pgUpdate();
		dbPgUpdate.executeUpdate(pgsqlHeader, true);

		//Delete selected NTP in co_ntp_detail in PostgreSQL
		dbPgUpdate.executeUpdate("DELETE FROM co_ntp_detail WHERE ntp_no = '"+ ntp_no +"';", false);

		for(int x=0; x < modelNTP.getRowCount(); x++){
			Integer pbl_id = (Integer) modelNTP.getValueAt(x, 7);

			String sqlPgDetail = "INSERT INTO co_ntp_detail(\n" +
					"	ntp_no, seq_no, pe_no, work_desc, work_qty, uom_id, labor_cost, \n" +
					"   material_cost, other_cost, pbl_id, work_percent, remarks, taken_over, \n" +
					"   takenover_recid, partial_accomp_amt, status_id, created_by, date_created, edited_by, date_edited)\n" +
					"VALUES (" +
					"'"+ ntp_no +"', --ntp_no\n" +
					"'"+ modelNTP.getValueAt(x, 0) +"', --seq_no\n" +
					"nullif('"+ modelNTP.getValueAt(x, 11) +"', 'null'), --pe_no\n" +
					"'"+ modelNTP.getValueAt(x, 1) +"', --work_desc\n" +
					"'"+ modelNTP.getValueAt(x, 3) +"', --work_qty\n" +
					"'"+ modelNTP.getValueAt(x, 2) +"', --uom_id\n" +
					"0.0000, --labor_cost\n" +
					"0.0000, --material_cost\n" +
					"'"+ modelNTP.getValueAt(x, 4) +"', --other_cost\n" +
					"nullif('"+ pbl_id +"', 'null'), --pbl_id\n" +
					"nullif('"+ modelNTP.getValueAt(x, 10) +"', 'null')::real, --work_percent\n" +
					"nullif('"+ remarks +"', ''), --remarks\n" +
					""+ is_takeover_ntp +", --taken_over\n" +
					"nullif('"+ takenover_ntpno +"', 'null')::int, --takenover_recid\n" +
					"null, --partial_accomp_amt\n" +
					"'A', --status_id\n" +
					"(select created_by from co_ntp_header where ntp_no = '"+ ntp_no +"'), --created_by\n" +
					"(select date_created from co_ntp_header where ntp_no = '"+ ntp_no +"'), --date_created\n" +
					"'"+ UserInfo.EmployeeCode +"', --edited_by\n" +
					"now()); --date_edited";

			dbPgUpdate.executeUpdate(sqlPgDetail, false);
		}

		String pgSubject = "UPDATE co_ntp_subjects SET ntp_subject_description = '"+ subject +"', edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now(), status_id = 'A'\n" +
				"WHERE ntp_no = '"+ ntp_no +"';";
		dbPgUpdate.executeUpdate(pgSubject, false);

		dbPgUpdate.commit();
	}

	public static void deleteNTP(String ntp_no, String co_id, String proj_id) { 
		pgUpdate dbPgUpdate = new pgUpdate();
		dbPgUpdate.executeUpdate("UPDATE co_ntp_header SET status_id = 'I', " +
				"	deleted_by = '"+UserInfo.EmployeeCode+"', " +
				"	deleted_date = now()  " +
				"	WHERE ntp_no = '"+ ntp_no +"';", false);

		dbPgUpdate.executeUpdate("UPDATE co_ntp_detail SET status_id = 'I' " +
				"	WHERE ntp_no = '"+ ntp_no +"';", false);

		//dbMsUpdate.commit();
		dbPgUpdate.commit();
	}

	public static void computeTotalProgressBilling(modelNTPProgressBilling model, modelNTPProgressBilling total) {
		BigDecimal totalGrossAmt = new BigDecimal("0.00");
		BigDecimal totalVAT = new BigDecimal("0.00");
		BigDecimal totalWTAX = new BigDecimal("0.00");
		BigDecimal totalDPReoupment = new BigDecimal("0.00");
		BigDecimal totalRetentionAmt = new BigDecimal("0.00");
		BigDecimal totalBCLiquidation = new BigDecimal("0.00");
		BigDecimal totalOtherDeduction = new BigDecimal("0.00");
		BigDecimal totalNetAmt = new BigDecimal("0.00");

		for(int x=0; x < model.getRowCount(); x++){
			BigDecimal gross_amt = (BigDecimal) model.getValueAt(x, 3);
			BigDecimal vat = (BigDecimal) model.getValueAt(x, 4);
			BigDecimal wtax = (BigDecimal) model.getValueAt(x, 5);
			BigDecimal dp_recoupment = (BigDecimal) model.getValueAt(x, 6);
			BigDecimal retention_amt = (BigDecimal) model.getValueAt(x, 7);
			BigDecimal bc_liquidation = (BigDecimal) model.getValueAt(x, 8);
			BigDecimal other_deduction = (BigDecimal) model.getValueAt(x, 9);
			BigDecimal net_amt = (BigDecimal) model.getValueAt(x, 10);

			try {
				totalGrossAmt = totalGrossAmt.add(gross_amt);
			} catch (Exception e) { }
			try {
				totalVAT = totalVAT.add(vat);
			} catch (Exception e) { }
			try {
				totalWTAX = totalWTAX.add(wtax);
			} catch (Exception e) { }
			try {
				totalDPReoupment = totalDPReoupment.add(dp_recoupment);
			} catch (Exception e) { }
			try {
				totalRetentionAmt = totalRetentionAmt.add(retention_amt);
			} catch (Exception e) { }
			try {
				totalBCLiquidation = totalBCLiquidation.add(bc_liquidation);
			} catch (Exception e) { }
			try {
				totalOtherDeduction = totalOtherDeduction.add(other_deduction);
			} catch (Exception e) { }
			try {
				totalNetAmt = totalNetAmt.add(net_amt);
			} catch (Exception e) { }
		}

		total.setValueAt(totalGrossAmt, 0, 3);
		total.setValueAt(totalVAT, 0, 4);
		total.setValueAt(totalWTAX, 0, 5);
		total.setValueAt(totalDPReoupment, 0, 6);
		total.setValueAt(totalRetentionAmt, 0, 7);
		total.setValueAt(totalBCLiquidation, 0, 8);
		total.setValueAt(totalOtherDeduction, 0, 9);
		total.setValueAt(totalNetAmt, 0, 10);
	}

	public static String getNTPTabulation(String ntp_type_id, Object downpayment) {
		String strSQL = "(select trim(print_type) as \"Print Type\", paper_size as \"Paper Size\", paper_orientation as \"Paper Orientation\", printed_counter as \"Print\"\n" +
				"from mf_report_list\n" +
				"where report_name = 'NTPTabulation'\n" +
				"and report_module @> array[(case when '"+ ntp_type_id +"' = '08' then '06' else '"+ ntp_type_id +"' end)]::varchar[]\n" +
				"and status_id = 'A'\n" +
				"union all\n" +
				"select trim(print_type) as \"Print Type\", paper_size as \"Paper Size\", paper_orientation as \"Paper Orientation\", printed_counter as \"Print\"\n" +
				"from mf_report_list\n" +
				"where report_name = 'NTPTabulation'\n" +
				"and report_module is null\n" +
				"and (case when "+ downpayment +" > 0 then print_type ~* 'SURETY' else print_type !~* 'SURETY' end))\n" +
				"order by \"Print Type\"";

		FncSystem.out("NTP Preview", strSQL);
		return strSQL;
	}

	public static String contractCode(String ntp_tpye_id, String proj_id, String phase) {
		String sql = "SELECT sp_ntp_generate_contract_code('"+ ntp_tpye_id +"', '"+ proj_id +"', '"+ phase +"');";

		FncSystem.out("Contract Code", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}
	
	public static String contractCode2(String co_id, String proj_id, String sub_proj_id, String ntp_type_id) {
		String sql = "SELECT sp_ntp_generate_contract_code_v2('"+co_id+"', '"+proj_id+"', '"+sub_proj_id+"', '"+ntp_type_id+"');";

		FncSystem.out("Contract Code", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}
	
	public static void insertAudit_trail(String activity, String remarks, pgUpdate db) {

		String user_code = UserInfo.EmployeeCode;

		String sqlDetail = "INSERT INTO mf_audit_trail(\n"
				+ "	              system_id, activity, user_code, date_upd, entity_id, \n"
				+ "	              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n"
				+ "	      VALUES ('RP', '" + activity + "', '" + user_code + "', NOW(), NULL, \n"
				+ "	              NULL, NULL, NULL, NULL, NULL, '" + remarks + "');";

		System.out.printf("SQL #DETAIL: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

	}
}
