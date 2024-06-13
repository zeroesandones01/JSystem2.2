package Buyers.ClientServicing;

import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelCARD_Dues;
import tablemodel.modelHoldingAndReservation_PaySchemeDetails;
import tablemodel.modelHoldingAndReservation_Requests;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncSystem;

public class _HoldingAndReservation {

	public _HoldingAndReservation() {
		// TODO Auto-generated constructor stub
	}

	public static String client() {
		return "select trim(entity_id)::VARCHAR as \"ID\",\n" + 
				"trim(entity_name) as \"Name\",\n" + 
				"trim(entity_kind)::VARCHAR as \"Kind\",\n" + 
				"date_of_birth as \"Birth Date\",\n" + 
				"coalesce(case when gender = 'M' then 'MALE' else 'FEMALE' end, '')::VARCHAR as \"Gender\"\n" + 
				"from rf_entity \n"+
				"where status_id = 'A' \n" + 
				"order by entity_name;";
	}

	public static String getUnits(String proj_id) {
		/*String SQL = "select\n" + 
				"a.unit_id as \"ID\", TRIM(a.description) as \"Description\", b.model_id as \"Model ID\", TRIM(b.model_desc) as \"Model Description\", a.lotarea as \"Lot Area\",\n" + 
				"a.floorarea as \"Floor Area\", a.sellingprice *compute_UnitSellingPrice(a.proj_id, a.pbl_id, a.model_id, null) (2015-03-23) * as \"Selling Price\", a.appraised_value as \"Appraised Value\", b.with_changemodel as \"With Change Model\"\n" + 
				"from mf_unit_info a\n" + 
				"left join mf_product_model b on b.model_id =  a.model_id\n" + 
				"where a.proj_id = '"+ proj_id +"'\n" + 
				"and a.sub_proj_id in (SELECT sub_proj_id FROM rf_marketing_scheme_main WHERE status_id = 'P')\n" + 
				"and a.status_id = 'A'\n" + 
				"order by getinteger(a.phase), gettext(a.phase), getinteger(a.block), gettext(a.block), getinteger(a.lot), gettext(a.lot);";*/

		String SQL = "SELECT * FROM view_holding_and_reservation_open_units('"+ proj_id +"');";
		FncSystem.out("Units", SQL);
		return SQL;
	}

	public static String getExtensionUnits(String entity_id) {
		String SQL = "SELECT DISTINCT a.unit_id as \"Unit ID\", get_unit_description_pbl(a.proj_id, a.pbl_id) as \"Unit Description\", a.model_id as \"Model ID\", get_model_name(a.model_id) as \"Model Name\",\n" + 
				"     a.proj_id as \"Project ID\", c.proj_name as \"Project Name\", a.sellingprice as \"Selling Price\", a.dept_code as \"Department ID\",\n" + 
				"     get_department_name(a.dept_code) as \"Department Name\", a.trans_date as \"Valid From\", a.hold_until as \"Valid To\",\n" +
				"     a.co_id as \"Company ID\", d.company_name as \"Company Name\", a.total_amt_paid as \"Amount Paid\", a.client_seqno as \"Client Sequence No.\",\n" + 
				"     timestamp_pl_interval(NOW()::TIMESTAMP, INTERVAL '4 Days') as \"Extension Date\", a.remarks as \"Remarks\", a.tran_type as \"Tran Type\"\n" + //edited by Alvin Gonzales (2015-06-22): adding extension date
				"FROM rf_tra_header a\n" + 
				"LEFT JOIN rf_tra_detail b ON b.client_seqno = a.client_seqno\n" + 
				"LEFT JOIN mf_project c ON c.proj_id = a.proj_id\n" + 
				"LEFT JOIN mf_company d ON d.co_id = a.co_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.status_id IN ('O', 'P')\n" + //edited by Alvin Gonzales (2015-05-05): adding of "P" status
				"AND a.tran_type IN ('0', '3', '4')\n" + 
				"AND COALESCE(b.part_id, '168') = '168'\n" + 
				"AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND byrstatus_id = '17')\n" + 
				"ORDER BY c.proj_name;";
		FncSystem.out("Extension SQL", SQL);
		return SQL;
	}

	public static String AGENTS() {
		String SQL = 
			
			"SELECT TRIM(a.entity_id)::VARCHAR as entity_id , TRIM(a.entity_name) , TRIM(c.division_code)::VARCHAR , TRIM(c.division_name) ,\r\n" + 
			"TRIM(c.division_alias) , TRIM(b.dept_id)::VARCHAR, TRIM(d.dept_name)  \r\n" + 
			"\r\n" + 
			"FROM rf_entity a\r\n" + 
			"				\r\n" + 
			"JOIN (select * from mf_sellingagent_info where status_id = 'A' and salespos_id not in ('004','009')) b on b.agent_id = a.entity_id\r\n" + 
			"LEFT  JOIN mf_division c on c.division_code = b.sales_div_id\r\n" + 
			"LEFT  JOIN mf_department d on b.dept_id = d.dept_code\r\n" + 
			"ORDER BY TRIM(a.entity_name)";
			    /*"SELECT TRIM(a.entity_id)::VARCHAR as \"Agent ID\", TRIM(a.entity_name) as \"Agent Name\", TRIM(c.division_code)::VARCHAR as \"Division ID\", TRIM(c.division_name) as \"Division Name\",\n" + 
				"     TRIM(c.division_alias) as \"Division Alias\", TRIM(d.dept_code)::VARCHAR as \"Department ID\", TRIM(d.dept_name) as \"Department Name\"\n" + 
				"FROM rf_entity a\n" + 
				//"INNER JOIN rf_sellingagent_info b on b.agent_id = a.entity_id\n" +
				"INNER JOIN (select * from mf_sellingagent_info where status_id = 'A') b on b.agent_id = a.entity_id\n" + 
				"INNER JOIN mf_division c on c.division_code = b.sales_div_id\n" + 
				"INNER JOIN mf_department d on d.dept_code = b.dept_id\n" + 
				"WHERE a.entity_id in (SELECT entity_id \n" + 
				"	FROM rf_entity_assigned_type \n" + 
				"	WHERE entity_type_id in (SELECT entity_type_id FROM mf_entity_type WHERE entity_type_desc ~* 'agent' AND status_id = 'A')) \n" + 
				"ORDER BY TRIM(a.entity_name);";*/
		return SQL;
	}

	public static String DIVISION() {
		String SQL = "SELECT TRIM(division_code)::VARCHAR as \"ID\", TRIM(division_name) as \"Name\", TRIM(division_alias)::VARCHAR as \"Alias\"\n" + 
				"FROM mf_division\n" + 
				"WHERE division_name ~* 'BUSINESS DEVELOPMENT'\n" +
				"AND status_id = 'A' \n" + 
				"ORDER BY division_name;";
		return SQL;
	}

	public static String DEPARTMENT(String div_id) {
		String SQL = "SELECT TRIM(dept_code)::VARCHAR \"ID\", TRIM(dept_name) as \"Name\", TRIM(dept_alias)::VARCHAR \"Alias\" FROM mf_department WHERE division_code = '"+ div_id +"' AND status_id = 'A' ORDER BY TRIM(dept_name);";
		return SQL;
	}

	public static String[] getCorporationType() {
		String SQL = "SELECT FORMAT('%s - %s', TRIM(entity_type_id), TRIM(entity_type_desc))\n" + 
				"FROM mf_entity_type\n" + 
				"WHERE TRIM(entity_type_desc) NOT IN ('EMPLOYEE')\n" + 
				"ORDER BY TRIM(entity_type_id);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount()];
			for(int x=0; x < db.getRowCount(); x++){
				notices[x] = (String) db.getResult()[x][0];
			}
			return notices;
		}else{
			return null;
		}
	}

	public static String[] getProvince() {
		String SQL = "SELECT FORMAT('%s - %s', TRIM(province_id), TRIM(province_name))\n" + 
				"FROM mf_province\n" + 
				"ORDER BY TRIM(province_id)::INT;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount() + 1];
			notices[0] = "NONE";

			for(int x=1; x < db.getRowCount() + 1; x++){
				notices[x] = (String) db.getResult()[x-1][0];
			}
			return notices;
		}else{
			return null;
		}
	}

	public static String[] getCity() {
		String SQL = "SELECT FORMAT('%s - %s', TRIM(city_id), TRIM(city_name)) FROM mf_city ORDER BY TRIM(city_id)::INT;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount()];
			for(int x=0; x < db.getRowCount(); x++){
				notices[x] = (String) db.getResult()[x][0];
			}
			return notices;
		}else{
			return null;
		}
	}

	public static String[] getMunicipality(String province_id) {
		String SQL = "SELECT FORMAT('%s - %s', TRIM(municipality_id), TRIM(municipality_name)) FROM mf_municipality WHERE province_id = '"+ province_id +"' ORDER BY TRIM(municipality_id)::INT;";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount()];
			for(int x=0; x < db.getRowCount(); x++){
				notices[x] = (String) db.getResult()[x][0];
			}
			return notices;
		}else{
			return null;
		}
	}
	
	public static String[] getCityMunicipality(String province_id){
		
		pgSelect db = new pgSelect();
		String SQL = "select FORMAT('%s - %s', TRIM(a.city_id), TRIM(a.city_name)), trim(a.province_id)\n" + 
					 "FROM (SELECT city_id, city_name, province_id from mf_city\n" + 
					 "      UNION \n" + 
					 "      SELECT municipality_id, municipality_name, province_id from mf_municipality) a\n" + 
					 "WHERE "+ (province_id.equals("NONE") ? "a.province_id is null":"a.province_id = '"+ province_id +"'") +"\n" + 
					 "ORDER BY city_name;";
		db.select(SQL);
		
		if(db.isNotNull()){
			String [] city_municipality = new String[db.getRowCount()];
			
			for(int x = 0; x<db.getRowCount(); x++){
				city_municipality[x] = (String) db.getResult()[x][0];
			}
			return city_municipality;
		}else{
			return null;
		}
	}

	public static String sqlView(String entity_id) {
		String SQL = "SELECT EXISTS(SELECT x.* FROM rf_pay_detail y INNER JOIN rf_pay_header x ON x.client_seqno = y.client_seqno WHERE y.ar_no = a.receipt_no AND x.op_status = 'P') \"Credited\",\n" +
				"  TRIM(a.unit_id) as \"Unit ID\", NULLIF(FORMAT('%s-%s-%s', TRIM(d.phase), TRIM(d.block), TRIM(d.lot)), '--')::VARCHAR as \"Ph-Bl-Lt\", a.tran_type::INT as \"Trans. ID\",\n" + 
				"  (CASE WHEN a.tran_type = '0' THEN 'Hold' WHEN a.tran_type = '1' THEN 'Unhold' WHEN a.tran_type = '2' THEN 'Float' WHEN a.tran_type = '3' THEN 'Extension' WHEN a.tran_type = '4' THEN 'Management Hold' WHEN a.tran_type = '5' THEN 'Commitment Fee' END)::VARCHAR as \"Transaction\",\n" + 
				"  a.client_seqno::VARCHAR as \"Client Sequence No.\", a.status_id as \"Status\", a.receipt_no as \"A.R. #\", TRIM(a.co_id)::VARCHAR as \"Company ID\", TRIM(b.company_name) as \"Company Name\", TRIM(a.proj_id)::VARCHAR as \"Project ID\",\n" + 
				"  TRIM(c.proj_name) as \"Project Name\", TRIM(a.model_id)::VARCHAR as \"Model ID\", TRIM(e.model_desc) as \"Model Name\", a.sellingprice as \"Selling Price\",\n" + 
				"  TRIM(a.selling_agent)::VARCHAR as \"Agent ID\", TRIM(f.entity_name) as \"Agent Name\", g.division_code as \"Sales Division ID\", h.division_name as \"Sales Division\", TRIM(a.dept_code)::VARCHAR as \"Sales Group ID\", TRIM(g.dept_name) as \"Sales Group\",\n" + 
				"  a.trans_date as \"Trans. Date\", a.hold_until as \"Hold Until\", d.description \"Description\", a.total_amt_paid as \"Amount Paid\", a.tra_header_id::VARCHAR \"Rec. ID\"\n" +
				"FROM rf_tra_header a\n" + 
				"LEFT JOIN mf_company b ON b.co_id = a.co_id\n" + 
				"LEFT JOIN mf_project c ON c.proj_id = a.proj_id\n" + 
				"LEFT JOIN mf_unit_info d ON d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_product_model e ON e.model_id = a.model_id\n" + 
				"LEFT JOIN rf_entity f ON f.entity_id = a.selling_agent\n" + 
				"LEFT JOIN mf_department g ON g.dept_code = a.dept_code AND g.status_id = 'A'\n" + 
				"LEFT JOIN mf_division h ON h.division_code = g.division_code AND h.status_id = 'A'\n" + 
				"WHERE a.entity_id = '"+ entity_id +"' AND a.tran_type != '1' --AND a.status_id != 'I'\n" + 
				"ORDER BY client_seqno::BIGINT DESC;";
		System.out.println("View: " + SQL);
		return SQL;
	}





	/******************************** TEMPORARY RESERVATION ********************************/
	public static String sqlProject(String entity_id) {
		return 
		        //FROM RF_TRA_HEADER
				"SELECT DISTINCT a.proj_id as \"ID\", TRIM(c.proj_name) as \"Name\", c.proj_alias as \"Alias\"\n" + 
				"FROM rf_tra_header a\n" + 
				"INNER JOIN rf_tra_detail b ON b.client_seqno = a.client_seqno\n" + 
				"INNER JOIN mf_project c ON c.proj_id = a.proj_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.status_id = 'P'\n" + 
				"AND b.part_id in ('203','168', '229')\n" + 
				"AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND byrstatus_id = '17')\n" + 
				"AND NOT EXISTS(SELECT * FROM rf_pay_header WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND new_reserved = 'Y' AND status_id = 'A')\n" + 
				"AND a.receipt_no IS NOT NULL\n" +
				
				"UNION ALL \n" +
				
				//FROM RF_PAYMENTS
				"SELECT DISTINCT a.proj_id, \n" + 
				"	TRIM(c.proj_name), \n" + 
				"	c.proj_alias \n" + 
				"	FROM rf_payments a\n" + 
				"	INNER JOIN mf_project c ON c.proj_id = a.proj_id\n" + 
				"	WHERE a.entity_id = '"+entity_id+"'\n" + 
				"	AND a.status_id != 'I'\n" + 
				"	AND a.pay_part_id in ('203','168', '229')\n" + 
				"	AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id \n" + 
				"		AND seq_no = a.seq_no AND byrstatus_id = '17')\n" + 
				"	AND NOT EXISTS(SELECT * FROM rf_pay_header WHERE entity_id = a.entity_id AND proj_id = a.proj_id \n" + 
				"		AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND new_reserved = 'Y' AND cs_time_out IS NOT NULL AND status_id = 'A')\n" + 
				"	AND a.or_no IS NOT NULL\n" ;				
				//"ORDER BY TRIM(c.proj_name);";
	}

	public static String sqlUnits(String entity_id, String proj_id) {
		return 
				//FROM RF_TRA_HEADER
		  		"SELECT " +
		  		"	distinct on (a.entity_id, a.pbl_id)\n" + 
		  		"	a.unit_id as \"ID\", \n" +
				"	trim(c.description) as \"Description\", " +
				"	a.seq_no as \"Seq.\", " +
				"	a.proj_id as \"Project ID\", " +
				"	trim(d.proj_name) as \"Project Name\",\n" + 
				"	a.model_id as \"Model ID\"," +
				"	trim(e.model_desc) as \"Model Desc.\", " +
				"	a.selling_agent as \"Selling Agent ID\", " +
				"	trim(f.entity_name) as \"Selling Agent Name\",\n" + 
				"	a.sellingprice as \"GSP\", " +
				"	(CASE WHEN c.vat THEN i.vat_rate::numeric ELSE NULL END) as \"VAT\", " +   //10
				"	j.loanable_amt as \"Loanable Amount\", " +
				"	(a.sellingprice - j.loanable_amt) as \"DP Equity\", " +
				"	c.sub_proj_id as \"Phase ID\"  \n" +   //13
				"	\n" + 
				"	FROM rf_tra_header a\n" + 
				"	LEFT JOIN rf_tra_detail b ON b.client_seqno = a.client_seqno\n" + 
				"	LEFT JOIN mf_unit_info c ON c.unit_id = a.unit_id\n" + 
				"	LEFT JOIN mf_project d ON d.proj_id = c.proj_id\n" + 
				"	LEFT JOIN mf_product_model e ON e.model_id = a.model_id\n" + 
				"	LEFT JOIN rf_entity f ON f.entity_id = a.selling_agent\n" + 
				"	LEFT JOIN mf_company i ON i.co_id = a.co_id\n" + 
				"	LEFT JOIN mf_unit_pricing j ON j.proj_id = c.proj_id AND j.pbl_id = c.pbl_id\n" + 
				"	\n" + 
				"	WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"	AND a.proj_id = '"+ proj_id +"'\n" + 
				"	AND a.tran_type IN ('0', '3', '2')\n" + 
				"	AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id /*AND seq_no = a.seq_no*/ AND byrstatus_id = '17' AND status_id = 'A')\n" + 
				"	AND NOT EXISTS(SELECT * FROM rf_pay_header WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id /*AND seq_no = a.seq_no*/ AND new_reserved = 'Y' AND cs_time_out IS NOT NULL AND status_id = 'A')\n" + 
				"	AND a.receipt_no IS NOT NULL\n" +
				"	AND a.status_id = 'P' \n" +
				
				"	UNION ALL \n" +
				
				//FROM RF_PAYMENTS
				"	SELECT " +
				"	distinct on (a.entity_id, a.pbl_id) \n" +
				"	a.unit_id,\n" + 
				"	trim(c.description), \n" + 
				"	a.seq_no , \n" + 
				"	a.proj_id , \n" + 
				"	trim(d.proj_name),\n" + 
				"	c.model_id ,\n" + 
				"	trim(e.model_desc), \n" + 
				"	k.selling_agent , \n" + 
				"	trim(f.entity_name) ,\n" + 
				"	c.sellingprice, \n" + 
				"	(CASE WHEN c.vat THEN i.vat_rate::numeric ELSE NULL END) ,  \n" + 
				"	j.loanable_amt , \n" + 
				"	(c.sellingprice - j.loanable_amt) , \n" + 
				"	c.sub_proj_id \n" + 
				"	\n" + 
				"	FROM rf_payments a\n" + 
				"	LEFT JOIN mf_unit_info c ON c.unit_id = a.unit_id\n" + 
				"	LEFT JOIN mf_project d ON d.proj_id = c.proj_id\n" + 
				"	LEFT JOIN mf_product_model e ON e.model_id = c.model_id\n" + 
				"	LEFT JOIN rf_special_holding_agent k on a.entity_id = k.entity_id and a.pbl_id = k.pbl_id\n" + 
				"	LEFT JOIN rf_entity f ON f.entity_id = k.selling_agent\n" + 
				"	LEFT JOIN mf_company i ON i.co_id = a.co_id\n" + 
				"	LEFT JOIN mf_unit_pricing j ON j.proj_id = c.proj_id AND j.pbl_id = c.pbl_id\n" + 
				"		\n" + 
				"	WHERE a.entity_id = '"+entity_id+"'\n" + 
				"	AND a.proj_id = '"+proj_id+"'\n" + 
				"	AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = a.entity_id \n" + 
				"		AND proj_id = a.proj_id AND pbl_id = a.pbl_id \n" + 
				"		AND byrstatus_id = '17' AND status_id = 'A')\n" + 
				"	AND NOT EXISTS(SELECT * FROM rf_pay_header WHERE entity_id = a.entity_id \n" + 
				"		AND proj_id = a.proj_id AND pbl_id = a.pbl_id \n" + 
				"		AND new_reserved = 'Y' AND cs_time_out IS NOT NULL AND status_id = 'A')\n" + 
				"	AND a.or_no IS NOT NULL\n" + 
				"	AND a.status_id != 'I';";
	}

	public static String sqlAgents(String entity_id, String proj_id, String unit_id) {
		String SQL = "SELECT * FROM view_sales_agent_v2('"+ entity_id +"', '"+ proj_id +"', '"+ unit_id +"');";
		return SQL;
	}

	public static String sqlBuyertype(String sub_proj_id, String entity_kind) {
		String SQL =  "select a.type_id as \"ID\", TRIM(a.type_desc) as \"Name\", a.type_alias as \"Alias\",\n" + 
				"TRIM(b.group_desc) as \"Group\", TRIM(a.type_card_display) as \"CARD Display\",\n" + 
				"a.bank_rem_convertible as \"Bank REM Convertible\", mri_insurance as \"MRI Insurance\", with_otherlot as \"W/ Other Lot\"\n" + 

				//updated by Del Gonzales 02-01-2016 - to display only buyer_types included in pricelist (see rf_marketing_scheme_detail)
				"from (select distinct on (c.type_id) c.type_id, c.type_group_id, c.type_desc, c.type_alias, " +
				"c.type_card_display, c.bank_rem_convertible, c.mri_insurance, with_otherlot, c.status_id \r\n" + 
				"				from rf_marketing_scheme_detail a\r\n" + 
				"				left join (select pmt_scheme_id, unnest(type_id) as type_id from mf_payment_scheme) b on a.pmt_scheme_id = b.pmt_scheme_id\r\n" + 
				"				left join mf_buyer_type c on b.type_id = c.type_id \n" +
				"				left join rf_marketing_scheme_main d on a.rec_id = d.rec_id \n" +
				"				where d.sub_proj_id = '"+sub_proj_id+"'  ) a\n" + 
				"left join mf_buyer_type_group b on b.group_id = a.type_group_id\n" + 
				"where a.status_id = 'A' \n"+
				"AND CASE WHEN '"+entity_kind+"' = 'I' THEN a.type_group_id IN ('02', '03', '04') else a.type_group_id != '04' end;";
		System.out.println("BuyerType: " + SQL);
		return SQL;
	}

	public static String sqlPaymentScheme(String entity_id, String proj_id, String unit_id, String buyer_type_id, String model_id, String phase_id, String cluster_id) {
		/*String SQL = "SELECT DISTINCT a.pmt_scheme_id as \"ID\", TRIM(a.pmt_scheme_desc) as \"Name\", b.disc_rate as \"Discount Rate\", a.int_rate as \"Interest Rate\", a.comp_factor as \"Comp. Factor\"\n" + 
				"FROM mf_payment_scheme a\n" + 
				"LEFT JOIN mf_discount_scheme b on b.type_id = '"+ buyer_type_id +"'\n" + 

				//remove by Del Gonzales 02-01-2016 - to display only payment schemes included in pricelist (see rf_marketing_scheme_detail)
				"WHERE a.proj_id @> ARRAY['"+ proj_id +"']::VARCHAR[]\n" + 
				"AND a.model_id @> ARRAY['"+ model_id +"']::VARCHAR[]\n" + 

				"WHERE a.type_id @> ARRAY['"+ buyer_type_id +"']::VARCHAR[]\n" + 				
				"AND a.phase @> (SELECT ARRAY[sub_proj_id]::VARCHAR[] FROM mf_unit_info WHERE unit_id = '"+ unit_id +"')\n" + 
				"AND EXISTS(SELECT * FROM mf_pay_scheme_detail WHERE pmt_scheme_id = a.pmt_scheme_id AND status_id = 'A')\n" +
				"AND a.pmt_scheme_id in ( select pmt_scheme_id from rf_marketing_scheme_detail \r\n" + 
				"	where rec_id in (select rec_id  from rf_marketing_scheme_main where sub_proj_id = '"+phase_id+"' ) ) \n" + 
				"AND a.status_id = 'A'\n" + 
				"ORDER BY a.pmt_scheme_id;";*/
		String SQL = "";
		if(proj_id.equals("017") || proj_id.equals("019")) {
			SQL = "SELECT DISTINCT a.pmt_scheme_id as \"ID\", TRIM(a.pmt_scheme_desc) as \"Name\", b.disc_rate as \"Discount Rate\", a.int_rate as \"Interest Rate\", \n" + 
					 "a.comp_factor as \"Comp. Factor\", c.type_id as \"Buyer ID\", c.type_desc as \"Buyer Type\", c.type_group_id as \"Group ID\" \n" + 
					 "FROM mf_payment_scheme a\n" + 
					 "LEFT JOIN mf_discount_scheme b on b.type_id = array_to_string(a.type_id, ',')\n" + 
					 "LEFT JOIN mf_buyer_type c on c.type_id = array_to_string(a.type_id, ',') \n" + 
					 "LEFT JOIN mf_unit_info d ON d.unit_id = '"+unit_id+"'\n" + 
					 "LEFT JOIN rf_marketing_scheme_pricelist e on e.phase = d.phase and e.block = d.block and e.lot = d.lot and e.status_id = 'A'\n" + 
					 "WHERE a.phase @> (SELECT ARRAY[d.sub_proj_id]::VARCHAR[])\n" + 
					 "AND EXISTS(SELECT * FROM mf_pay_scheme_detail WHERE pmt_scheme_id = a.pmt_scheme_id AND status_id = 'A')\n" + 
					 "AND a.pmt_scheme_id in ( select pmt_scheme_id from rf_marketing_scheme_detail \n" + 
					 "	where rec_id in (select rec_id  from rf_marketing_scheme_main where sub_proj_id = '"+phase_id+"' ) )\n" + 
					 "and (CASE when is_client_ofw('"+entity_id+"') then (a.pmt_scheme_id IN ('008','012','014','017','019','021','025') OR c.type_group_id IN ('03', '05')) \n"+
					 "			WHEN (COALESCE(e.bf_85_disc_tcp, 0.00) = 0.00 AND COALESCE(e.sp_disc_tcp, 0.00) = 0.00) then (case when ('"+cluster_id+"' = 'N' AND  get_client_income('"+entity_id+"') <= 15000) then a.int_rate = 3\n" + 
					 "													     when ('"+cluster_id+"' = 'P' AND  get_client_income('"+entity_id+"') <= 12000) then a.int_rate = 3\n" + 
					 "							else (a.int_rate != 3 OR c.type_group_id IN ('03')) end) \n" + 
					 "	  when COALESCE(e.sp_disc_tcp, 0.00) != 0.00 THEN c.type_group_id IN ('03', '04') \n" + 
					 "	  	    when coalesce(e.bf_85_disc_tcp, 0.00) != 0.00 THEN c.type_group_id IN ('05', '03', '04') ELSE false end)\n" + 
					 "AND a.status_id = 'A'\n" + 
					 "ORDER BY a.pmt_scheme_id;";
			System.out.printf("Payment Scheme: %s%n", SQL);
		}else {
			SQL = "SELECT DISTINCT a.pmt_scheme_id as \"ID\", TRIM(a.pmt_scheme_desc) as \"Name\", b.disc_rate as \"Discount Rate\", a.int_rate as \"Interest Rate\", \n" + 
					 "a.comp_factor as \"Comp. Factor\", c.type_id as \"Buyer ID\", c.type_desc as \"Buyer Type\", c.type_group_id as \"Group ID\" \n" + 
					 "FROM mf_payment_scheme a\n" + 
					 "LEFT JOIN mf_discount_scheme b on b.type_id = array_to_string(a.type_id, ',')\n" + 
					 "LEFT JOIN mf_buyer_type c on c.type_id = array_to_string(a.type_id, ',') \n" + 
					 "LEFT JOIN mf_unit_info d ON d.unit_id = '"+unit_id+"'\n" + 
					 "LEFT JOIN rf_marketing_scheme_pricelist e on e.phase = d.phase and e.block = d.block and e.lot = d.lot and e.status_id = 'A'\n" + 
					 "WHERE CASE WHEN d.server_id IS NOT NULL THEN TRUE ELSE (case when d.phase = '' and d.proj_id = '018' THEN TRUE ELSE a.phase @> (SELECT ARRAY[d.sub_proj_id]::VARCHAR[]) END) END\n" + 
					 "AND CASE WHEN d.server_id IS NOT NULL THEN TRUE ELSE EXISTS(SELECT * FROM mf_pay_scheme_detail WHERE pmt_scheme_id = a.pmt_scheme_id AND status_id = 'A') END\n" + 
					 "AND CASE WHEN d.server_id IS NOT NULL THEN TRUE ELSE a.pmt_scheme_id in ( select pmt_scheme_id from rf_marketing_scheme_detail \n" + 
					 "	where rec_id in (select rec_id  from rf_marketing_scheme_main where sub_proj_id = '"+phase_id+"' ) ) END\n" + 
					 "and case when d.server_id IS NOT NULL THEN TRUE ELSE (CASE when is_client_ofw('"+entity_id+"') then (a.pmt_scheme_id IN ('008','012','014','017','019','021','025') OR c.type_group_id IN ('03', '05')) \n"+
					 "			WHEN (COALESCE(e.bf_85_disc_tcp, 0.00) = 0.00 AND COALESCE(e.sp_disc_tcp, 0.00) = 0.00) then (COALESCE(a.int_rate, 0) != 3 AND c.type_group_id IN ('03', '04')) \n" + 
					 "	  when COALESCE(e.sp_disc_tcp, 0.00) != 0.00 THEN c.type_group_id IN ('03', '04') \n" + 
					 "	  	    when coalesce(e.bf_85_disc_tcp, 0.00) != 0.00 THEN c.type_group_id IN ('05', '03', '04') ELSE false end) END\n" + 
					 "AND a.status_id = 'A'\n" + 
					 "ORDER BY a.pmt_scheme_id;";
			System.out.printf("Payment Scheme: %s%n", SQL);
		}
		
		
		return SQL;
	}

	public static Integer getLatestAR(String doc_id) {
		String SQL = "SELECT get_receipt_no_to_issue('02', '"+ doc_id +"', '01')::INT;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return (Integer) db.getResult()[0][0];
		}else{
			return 0;
		}
	}

	public static BigDecimal getDP(String proj_id, String pbl_id, String byrtype_id, String pmt_scheme_id) {//XXX EDIT HERE
		String SQL = "SELECT get_tr_dp('"+ proj_id +"', getinteger('"+ pbl_id +"')::VARCHAR, '"+ byrtype_id +"', '"+ pmt_scheme_id +"');";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return (BigDecimal) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	
	public static BigDecimal getDP(String proj_id, String pbl_id, String pbl_id2 ,String byrtype_id, String pmt_scheme_id) {//XXX EDIT HERE
		String SQL = "SELECT get_tr_dp('"+ proj_id +"', getinteger('"+ pbl_id +"')::VARCHAR, getinteger('"+pbl_id2+"')::VARCHAR ,'"+ byrtype_id +"', '"+ pmt_scheme_id +"');";

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		FncSystem.out("DIsplay SQL For Equity: %s%n", SQL);
		if(db.isNotNull()){
			return (BigDecimal) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	public static BigDecimal getMA(String proj_id, String pbl_id, String byrtype_id, String pmt_scheme_id) {//XXX EDIT HERE
		String SQL = "SELECT get_tr_ma('"+ proj_id +"', getinteger('"+ pbl_id +"')::VARCHAR, '"+ byrtype_id +"', '"+ pmt_scheme_id +"');";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return (BigDecimal) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	
	public static BigDecimal getMA(String proj_id, String pbl_id, String pbl_id2 ,String byrtype_id, String pmt_scheme_id) {//XXX EDIT HERE
		String SQL = "SELECT get_tr_ma('"+ proj_id +"', getinteger('"+ pbl_id +"')::VARCHAR, getinteger('"+pbl_id2+"')::VARCHAR ,'"+ byrtype_id +"', '"+ pmt_scheme_id +"');";

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		FncSystem.out("Display SQL for Loanable: %s%n", SQL);
		if(db.isNotNull()){
			return (BigDecimal) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	public static void updatePerRate(modelHoldingAndReservation_PaySchemeDetails model, Integer row, String part_id, BigDecimal rate, BigDecimal nsp, _JXFormattedTextField dp, _JXFormattedTextField ma) {
		BigDecimal hundred = new BigDecimal("100");

		for(int x=0; x < model.getRowCount(); x++){
			String particular_id = (String) model.getValueAt(x, 1);

			if(part_id.equals("013")){
				if(particular_id.equals("014")){
					BigDecimal new_rate = hundred.subtract(rate);

					model.setValueAt(new_rate, x, 3);
					dp.setValue(nsp.multiply(rate.divide(hundred)));
					ma.setValue(nsp.multiply(new_rate.divide(hundred)));
				}
			}
			if(part_id.equals("014")){
				if(particular_id.equals("013")){
					BigDecimal new_rate = hundred.subtract(rate);

					model.setValueAt(new_rate, x, 3);
					dp.setValue(nsp.multiply(new_rate.divide(hundred)));
					ma.setValue(nsp.multiply(rate.divide(hundred)));
				}
			}
		}
	}







	/******************************** OFFICIAL RESERVATION ********************************/
	public static String sqlORProject(String entity_id) {
		String SQL = "SELECT DISTINCT trim(a.projcode)::VARCHAR as \"ID\", trim(b.proj_name) as \"Name\", trim(b.proj_alias)::VARCHAR as \"Alias\"\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_project b ON b.proj_id = a.projcode\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" +
				"AND a.currentstatus IN ('17', '18', '20')\n" +
				"AND NOT EXISTS(SELECT *\n" + 
				"	FROM rf_pay_header\n" + 
				"	WHERE entity_id = a.entity_id\n" + 
				"	AND proj_id = a.projcode\n" + 
				"	AND pbl_id = a.pbl_id\n" + 
				"	AND seq_no = a.seq_no\n" + 
				"	AND client_seqno NOT IN (SELECT client_seqno FROM rf_pay_detail WHERE entity_id = a.entity_id AND part_type = '106')\n" + 
				"	AND total_amt_paid IS NULL\n" + 
				"	AND op_status = 'A');";
		return SQL;
	}

	public static String sqlORUnits(String entity_id, String proj_id) {
		String SQL = "SELECT trim(b.unit_id)::VARCHAR as \"ID\", trim(b.description) as \"Description\", a.seq_no as \"Sequence\",\n" + 
				"   trim(a.model_id)::VARCHAR as \"Model ID\", trim(c.model_desc) as \"Model Desc.\", trim(a.currentstatus)::VARCHAR as \"Status ID\", trim(d.status_desc) as \"Status Desc.\",\n" + 
				"   a.sellingprice as \"GSP\", a.sellingprice - (SELECT COALESCE(ROUND(selling_price * (disc_rate / 100), 2), 0) FROM rf_pay_header where entity_id = TRIM(a.entity_id) and proj_id = TRIM(a.projcode) and pbl_id = TRIM(a.pbl_id) and seq_no = a.seq_no and new_reserved = 'Y') as \"NSP\"\n" +
				//"   is_qualified_for_or(a.entity_id, a.projcode, a.pbl_id, a.seq_no, a.buyertype) as \"OR Processed\"\n" +
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_unit_info b ON b.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_product_model c ON c.model_id = a.model_id\n" + 
				"LEFT JOIN mf_buyer_status d ON d.byrstatus_id = a.currentstatus\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.projcode = '"+ proj_id +"'\n" + 
				"AND a.currentstatus IN ('17', '18', '20');";
		//FncSystem.out("Client Info.", SQL);
		return SQL;
	}

	public static Object[] getPaymentInformation(String entity_id, String proj_id, String unit_id, Integer seq_no) {
		String SQL = "SELECT trim(a.buyertype), trim(b.type_desc), trim(a.pmt_scheme_id), trim(c.pmt_scheme_desc),\n" + 
				"  (select to_char(tran_date, 'FMMM/dd/YYYY') from rf_buyer_status where entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no and byrstatus_id = '17') as tr_date,\n" + 
				"  to_char(a.offresdate, 'FMMM/dd/YYYY'),\n" + 
				"  (SELECT SUM(amount) FROM rf_payments WHERE entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no AND status_id != 'I') as res_amount_paid,\n" + 
				"  (SELECT SUM(amount) FROM rf_client_schedule WHERE entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no AND part_id = '012' AND status_id != 'I') as total_res\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_buyer_type b ON b.type_id = a.buyertype\n" + 
				"LEFT JOIN mf_payment_scheme c ON c.pmt_scheme_id = a.pmt_scheme_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.projcode = '"+ proj_id +"'\n" + 
				"AND a.pbl_id = getinteger('"+ unit_id +"')::text\n" + 
				"AND a.seq_no = "+ seq_no +";";

		//FncSystem.out("Payment Info.", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static void displayRequests(modelHoldingAndReservation_Requests model, String entity_id, String proj_id, String unit_id, String seq_no) {
		model.clear();

		String SQL = "SELECT TRIM(a.request_no) as request_no, TRIM(b.request_id) as request_id, TRIM(b.request_desc) as request_desc, a.request_date, TRIM(a.request_by) as request_by, TRIM(a.request_remarks) as request_remarks\n" + 
				"FROM req_rt_request_header a\n" + 
				"LEFT JOIN mf_request_type b ON b.request_id = a.req_type_id\n" + 
				"WHERE a.old_entity_id = '"+ entity_id +"'\n" + 
				"AND a.old_proj_id = '"+ proj_id +"'\n" + 
				"AND a.old_unit_id = '"+ unit_id +"'\n" + 
				"AND a.old_seq_no = "+ seq_no +"\n" + 
				"AND a.request_status = 'S';";

		FncSystem.out("Requests", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayPastDues(modelCARD_Dues model, String entity_id, String proj_id, String unit_id, String seq_no) {
		model.clear();

		String SQL = "SELECT * FROM view_past_dues('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ unit_id +"')::VARCHAR, "+ seq_no +", NOW()::TIMESTAMP, TRUE);";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayDues(modelCARD_Dues modelMain, String entity_id, String proj_id, String pbl_id, String seq_no, modelCARD_Dues modelTotal) {
		modelMain.clear();

		pgSelect db = new pgSelect();
		db.select("SELECT * FROM view_card_dues('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ pbl_id +"')::VARCHAR, "+ seq_no +", NOW()::TIMESTAMP, FALSE)");

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
		}
	}

	public static void totalEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal(0.00);

		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 14);
		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amount = amount.add(((BigDecimal) modelMain.getValueAt(x, 14)));
				System.out.printf("Amount: %s;%n", amount);
			} catch (NullPointerException e) {
				amount = amount.add(new BigDecimal(0.00));
			}
		}
		modelTotal.setValueAt(amount, 0, 14);
	}

	public static String getSQLReservationUnits(String entity_id) {
		String SQL = "SELECT a.client_seqno as \"Client Seq. #\", a.op_status as \"Status\", NULLIF(FORMAT('%s-%s-%s', TRIM(d.phase), TRIM(d.block), TRIM(d.lot)), '--')::VARCHAR as \"Ph-Bl-Lt\", a.unit_id as \"Unit ID\", \n" + 
				"  a.model_id as \"Model ID\", e.model_desc as \"Model Name\", a.proj_id as \"Project ID\", c.proj_name as \"Project Name\",\n" + 
				"  d.sellingprice as \"GSP\", g.tcp_discounted as \"NSP\", a.selling_agent as \"Agent ID\", get_client_name(a.selling_agent) as \"Agent Name\",\n" + 
				"  a.type_id as \"BuyerType ID\", h.type_desc as \"BuyerType Name\", a.pmt_scheme_id as \"Payment Scheme ID\", i.pmt_scheme_desc as \"Payment Scheme Name\", a.disc_rate as \"Disc. Rate\",\n" + 
				"  (a.selling_price - (a.selling_price * a.disc_rate)) as \"Disc. Amount\", a.dp_amount as \"DP Equity\", a.loan_availed as \"Loan Availed\", d.sub_proj_id as \"Phase ID\"\n" + 
				"FROM rf_pay_header a\n" + 
				"LEFT JOIN mf_company b ON b.co_id = a.co_id\n" + 
				"LEFT JOIN mf_project c ON c.proj_id = a.proj_id\n" + 
				"LEFT JOIN mf_unit_info d ON d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_product_model e ON e.model_id = a.model_id\n" + 
				"LEFT JOIN rf_entity f ON f.entity_id = a.selling_agent\n" + 
				"LEFT JOIN rf_marketing_scheme_pricelist g ON g.proj_id = a.proj_id AND g.phase = d.phase AND g.block = d.block AND g.lot = d.lot AND g.status_id = 'A'\n" + 
				"LEFT JOIN mf_buyer_type h ON h.type_id = a.type_id\n" + 
				"LEFT JOIN mf_payment_scheme i ON i.pmt_scheme_id = a.pmt_scheme_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"' AND new_reserved = 'Y' AND a.status_id = 'A'\n" + 
				"ORDER BY trans_date DESC;";
		//System.out.printf("***** Reservation Units ***** %n%s%n", SQL);
		return SQL;

	}
	
	public static void updateStatus(String proj_id, String unit_id, String status){
		
		pgUpdate db = new pgUpdate();
		
		String SQL = "UPDATE mf_unit_info SET status_id = '"+status+"' WHERE proj_id = '"+proj_id+"' AND unit_id = '"+unit_id+"'";
		
		db.executeUpdate(SQL, true);
		db.commit();
	}
	
	public static Boolean isOpen(String proj_id, String unit_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM mf_unit_info WHERE proj_id = '"+proj_id+"' AND unit_id = '"+unit_id+"' AND status_id = 'A'";
		db.select(SQL);
		
		FncSystem.out("Display SQL For Is Open", SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean withOtherUnit(String unit_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT is_with_merge_unit('"+unit_id+"')";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public static String getMergeUnitID(String unit_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT get_other_merge_unit('"+unit_id+"')";
		db.select(SQL);
		
		return (String) db.getResult()[0][0];
	}
	
	public static String getMergeUnitDesc(String unit_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT get_merge_unit_desc('"+unit_id+"')";
		db.select(SQL);
		
		return (String) db.getResult()[0][0];
	}
	
	public static String getMergeModelDesc(String unit_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT get_merge_model_desc('"+unit_id+"')";
		db.select(SQL);
		
		return (String) db.getResult()[0][0];
	}
	
	public static String entityKind(String entity_id){
		String entity_kind = "";
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT entity_kind FROM rf_entity WHERE entity_id = '"+entity_id+"'";
		db.select(SQL);
		
		if(db.isNotNull()){
			entity_kind = (String) db.getResult()[0][0];
		}
		
		return entity_kind;
	}
	
	public static Boolean withDiscount(String unit_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM ";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
			
	}
	
	public static Object[] getDiscountPhase4A(String unit_id){
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT a.sp_discount, ROUND(((a.sp_discount/a.tcp_discounted_roundoff)*100), 2) as disc_rate\n" + 
					 "FROM rf_marketing_scheme_pricelist a \n" + 
					 "LEFT JOIN mf_unit_info b on b.phase = a.phase and b.block = a.block and b.lot = a.lot\n" + 
					 "WHERE b.unit_id = '"+unit_id+"'\n" + 
					 "AND a.sp_disc_tcp != 0.00 \n"+
					 "AND a.status_id = 'A';";
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	
}
