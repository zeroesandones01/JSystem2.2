package Projects.BiddingandAwarding;

import java.math.BigDecimal;
import java.util.Date;

import Database.pgSelect;
import Functions.UserInfo;

public class _PaymentScheme {

	public static String SQL_SCHEME() {
		return "SELECT a.pmt_scheme_id as \"ID\", TRIM(a.pmt_scheme_desc) as \"Name\",\n" + 
				"  ARRAY_TO_STRING(a.type_id, ', ') as \"Buyer Type ID\", TRIM(b.type_desc) as \"Buyer Type Name\",\n" + 
				"  FORMAT('[%s]', ARRAY_TO_STRING(a.proj_id, ', ')) as \"Project ID's\",\n" + 
				"  FORMAT('[%s]', ARRAY_TO_STRING(a.phase, ', ')) as \"Phase\",\n" + 
				"  FORMAT('[%s]', ARRAY_TO_STRING(a.model_id, ', ')) as \"Model ID's\",\n" + 
				"  a.res_from as \"RES From\", a.res_to as \"RES To\", a.int_rate as \"Int. Rate\",\n" + 
				"  a.cm_scheme_id_reg as \"IHF Regular Account ID\", (SELECT TRIM(scheme_desc) FROM cm_scheme_hd WHERE scheme_id = a.cm_scheme_id_reg) as \"IHF Regular Account Name\",\n" + 
				"  a.cm_scheme_id_spc as \"IHF Special Account ID\", (SELECT TRIM(scheme_desc) FROM cm_scheme_hd WHERE scheme_id = a.cm_scheme_id_spc) as \"IHF Special Account Name\",\n" + 
				"  a.cm_scheme_id_ext_reg as \"Broker Regular Account ID\", (SELECT TRIM(scheme_desc) FROM cm_scheme_hd WHERE scheme_id = a.cm_scheme_id_ext_reg) as \"Broker Regular Account Name\",\n" + 
				"  a.cm_scheme_id_ext_spc as \"Broker Special Account ID\", (SELECT TRIM(scheme_desc) FROM cm_scheme_hd WHERE scheme_id = a.cm_scheme_id_ext_spc) as \"Broker Special Account Name\"\n" + 
				"FROM mf_payment_scheme a\n" + 
				"LEFT JOIN mf_buyer_type b ON b.type_id = ARRAY_TO_STRING(a.type_id, ', ')\n" + 
				"WHERE a.status_id = 'A'\n" + 
				"ORDER BY a.pmt_scheme_id::INT;";
	}

	public static String SQL_BUYERTYPE() {
		return "SELECT TRIM(a.type_id) as \"ID\", TRIM(a.type_desc) as \"Name\", TRIM(a.type_alias) as \"Alias\",\n" + 
				"b.group_id as \"Group ID\", b.group_desc as \"Group\", a.type_card_display as \"CARD Display\",\n" + 
				"a.bank_rem_convertible as \"Bank REM Convertible\", mri_insurance as \"MRI Insurance\", with_otherlot as \"W/ Other Lot\"\n" + 
				"FROM mf_buyer_type a\n" + 
				"LEFT JOIN mf_buyer_type_group b ON b.group_id = a.type_group_id\n" + 
				"WHERE a.status_id = 'A'\n" +
				"ORDER BY TRIM(a.type_id)::INT;";
	}

	public static String getBuyerType(String selectedTypeID) {
		String SQL = "SELECT ARRAY["+ selectedTypeID +"]::VARCHAR[] @> ARRAY[TRIM(a.type_id)]::VARCHAR[] as \"Select\", a.type_id as \"ID\", a.type_desc as \"Decription\", \n" +
				"	a.type_alias_too as \"Alias\", b.group_id as \"Group ID\", b.group_desc as \"Group Desc.\", a.bank_rem_convertible as \"Bank REM Convertible\", \n" +
				"	a.mri_insurance as \"MRI Insurance\"\n" + 
				"FROM mf_buyer_type a\n" + 
				"LEFT JOIN mf_buyer_type_group b on b.group_id = a.type_group_id\n" + 
				"ORDER BY a.type_id;";
		//Functions.FncSystem.out("Buyer Type", SQL);
		return SQL;
	}

	public static String getProject(String selectedProjectID) {
		String SQL = "SELECT ARRAY["+ selectedProjectID +"]::VARCHAR[] @> ARRAY[TRIM(a.proj_id)]::VARCHAR[] as \"Select\", a.proj_id as \"ID\", a.proj_name as \"Name\", a.proj_alias as \"Alias\",\n" + 
				"	b.co_id as \"Company ID\", b.company_name as \"Company Name\", b.company_alias as \"Company Alias\"\n" + 
				"FROM mf_project a\n" + 
				"LEFT JOIN mf_company b ON b.co_id = a.co_id\n" + 
				"ORDER BY a.proj_id;";
		//Functions.FncSystem.out("Project", SQL);
		return SQL;
	}

	public static String getPhase(String selectedProjectID, String selectedPhase) {//XXX Phase
		String SQL = "SELECT ARRAY["+ selectedPhase +"]::VARCHAR[] @> ARRAY[a.sub_proj_id]::VARCHAR[] as \"Select\",\n" + 
				"a.sub_proj_id as \"ID\",\n" + 
				"'Phase ' || TRIM(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" + 
				"b.proj_alias || getinteger(a.phase) as \"Alias\"\n" +
				"FROM mf_sub_project a\n" +
				"LEFT JOIN mf_project b ON a.proj_id = b.proj_id\n" +
				"WHERE ARRAY["+ selectedProjectID +"]::VARCHAR[] @> ARRAY[TRIM(a.proj_id)]::VARCHAR[]\n" +
				"AND a.status_id = 'A'\n" +
				"GROUP BY a.sub_proj_id, b.proj_alias, getinteger(a.phase)\n" + 
				"ORDER BY a.sub_proj_id;";
		//Functions.FncSystem.out("Phase", SQL);
		return SQL;
	}

	public static String getModel(String selectedModelID) {
		String SQL = "SELECT ARRAY["+ selectedModelID +"]::VARCHAR[] @> ARRAY[TRIM(a.model_id)]::VARCHAR[] as \"Select\", a.model_id as \"ID\", a.model_desc as \"Description\", a.model_alias as \"Alias\",\n" + 
				"	a.model_cost as \"Cost\", a.appraised_value as \"Appraised Value\", with_changemodel as \"W/ Change Model\"\n" + 
				"FROM mf_product_model a\n" + 
				"WHERE a.status_id = 'A'\n" + 
				"ORDER BY a.model_desc;";
		//Functions.FncSystem.out("Model", SQL);
		return SQL;
	}

	public static String getIHFRegularAccount(String buyer_type_id) {
		String SQL = "SELECT TRIM(scheme_id) as \"ID\", TRIM(scheme_desc) as \"Name\", terms as \"Terms\"\n" + 
				"FROM cm_scheme_hd\n" + 
				"WHERE buyer_type = '"+ buyer_type_id +"' AND agent_type = '001' AND scheme_type = 'R' AND status_id = 'A'\n" + 
				"ORDER BY scheme_id::INT;";
		//Functions.FncSystem.out("IHF Regular Account", SQL);
		return SQL;
	}

	public static String getIHFSpecialAccount(String buyer_type_id) {
		String SQL = "SELECT TRIM(scheme_id) as \"ID\", TRIM(scheme_desc) as \"Name\", terms as \"Terms\"\n" + 
				"FROM cm_scheme_hd\n" + 
				"WHERE buyer_type = '"+ buyer_type_id +"' AND agent_type = '001' AND scheme_type = 'S' AND status_id = 'A'\n" + 
				"ORDER BY scheme_id::INT;";
		//Functions.FncSystem.out("IHF Special Account", SQL);
		return SQL;
	}

	public static String getBrokerRegularAccount(String buyer_type_id) {
		String SQL = "SELECT TRIM(scheme_id) as \"ID\", TRIM(scheme_desc) as \"Name\", terms as \"Terms\"\n" + 
				"FROM cm_scheme_hd\n" + 
				"WHERE buyer_type = '"+ buyer_type_id +"' AND agent_type = '002' AND scheme_type = 'R' AND status_id = 'A'\n" + 
				"ORDER BY scheme_id::INT;";
		//Functions.FncSystem.out("Broker Regular Account", SQL);
		return SQL;
	}

	public static String getBrokerSpecialAccount(String buyer_type_id) {
		String SQL = "SELECT TRIM(scheme_id) as \"ID\", TRIM(scheme_desc) as \"Name\", terms as \"Terms\"\n" + 
				"FROM cm_scheme_hd\n" + 
				"WHERE buyer_type = '"+ buyer_type_id +"' AND agent_type = '002' AND scheme_type = 'S' AND status_id = 'A'\n" + 
				"ORDER BY scheme_id::INT;";
		//Functions.FncSystem.out("Broker Special Account", SQL);
		return SQL;
	}

	public static String getNewPaymentSchemeID() {
		pgSelect db = new pgSelect();
		db.select("SELECT to_char(COALESCE(MAX(pmt_scheme_id::INT), 0) + 1, 'FM000') FROM mf_payment_scheme WHERE status_id = 'A';");
		return (String) db.getResult()[0][0];
	}

	public static Boolean savePaymentScheme(String pmt_scheme_id, String pmt_scheme_desc, String type_id, String proj_id, String model_id, Date res_from, Date res_to,
			BigDecimal int_rate, String cm_scheme_id_reg, String cm_scheme_id_spc, String cm_scheme_id_ext_reg, String cm_scheme_id_ext_spc, String phase,
			BigDecimal tr_amount, BigDecimal or_amount, Integer res_days_interval, BigDecimal dp_rate, Integer dp_terms,
			BigDecimal ma_rate, Integer ma_terms, BigDecimal ma_com_factor) {
		proj_id = proj_id.replaceAll("\\[|\\]", "'").replace(", ", "', '");
		model_id = model_id.replaceAll("\\[|\\]", "'").replace(", ", "', '");
		phase = phase.replaceAll("\\[|\\]", "'").replace(", ", "', '");

		String SQL = "SELECT sp_save_payment_scheme('"+ pmt_scheme_id +"', '"+ pmt_scheme_desc +"', '"+ type_id +"', ARRAY["+ proj_id +"]::VARCHAR[],\n" + 
				"        ARRAY["+ model_id +"]::VARCHAR[], NULLIF('"+ res_from +"', 'null')::TIMESTAMP, NULLIF('"+ res_to +"', 'null')::TIMESTAMP, "+ int_rate +", NULLIF('"+ cm_scheme_id_reg +"', 'null'), NULLIF('"+ cm_scheme_id_spc +"', 'null'),\n" + 
				"        NULLIF('"+ cm_scheme_id_ext_reg +"', 'null'), NULLIF('"+ cm_scheme_id_ext_spc +"', 'null'), ARRAY["+ phase +"]::VARCHAR[], NULL, "+ tr_amount +", "+ or_amount +",\n" + 
				"        "+ res_days_interval +", "+ dp_rate +", "+ dp_terms +", "+ ma_rate +", "+ ma_terms +", "+ ma_com_factor +",\n" + 
				"        '"+ UserInfo.EmployeeCode +"');";

		System.out.printf("Save Payment Scheme:%n%s%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}

	public static Boolean updatePaymentScheme(String pmt_scheme_id, String pmt_scheme_desc, String type_id, String proj_id, String model_id, Date res_from, Date res_to,
			BigDecimal int_rate, String cm_scheme_id_reg, String cm_scheme_id_spc, String cm_scheme_id_ext_reg, String cm_scheme_id_ext_spc, String phase,
			BigDecimal tr_amount, BigDecimal or_amount, Integer res_days_interval, BigDecimal dp_rate, Integer dp_terms,
			BigDecimal ma_rate, Integer ma_terms, BigDecimal ma_com_factor) {

		proj_id = proj_id.replaceAll("\\[|\\]", "'").replace(", ", "', '");
		model_id = model_id.replaceAll("\\[|\\]", "'").replace(", ", "', '");
		phase = phase.replaceAll("\\[|\\]", "'").replace(", ", "', '");

		String SQL = "SELECT sp_update_payment_scheme('"+ pmt_scheme_id +"', '"+ pmt_scheme_desc.replace("'", "''") +"', '"+ type_id +"', ARRAY["+ proj_id +"]::VARCHAR[],\n" + 
				"        ARRAY["+ model_id +"]::VARCHAR[], NULLIF('"+ res_from +"', 'null')::TIMESTAMP, NULLIF('"+ res_to +"', 'null')::TIMESTAMP, "+ int_rate +", NULLIF('"+ cm_scheme_id_reg +"', 'null'), NULLIF('"+ cm_scheme_id_spc +"', 'null'),\n" + 
				"        NULLIF('"+ cm_scheme_id_ext_reg +"', 'null'), NULLIF('"+ cm_scheme_id_ext_spc +"', 'null'), ARRAY["+ phase +"]::VARCHAR[], NULL, "+ tr_amount +", "+ or_amount +",\n" + 
				"        "+ res_days_interval +", "+ dp_rate +", "+ dp_terms +", "+ ma_rate +", "+ ma_terms +", "+ ma_com_factor +",\n" + 
				"        '"+ UserInfo.EmployeeCode +"');";

		System.out.printf("Update Payment Scheme:%n%s%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}

	public static Object[] getSchemeDetails(String pmt_scheme_id) {
		String SQL = "SELECT a.amount, b.amount, b.interval, NULLIF(c.rate, 0), c.term, NULLIF(d.rate, 0), d.term\n" + 

				"FROM mf_pay_scheme_detail a\n" + 
				"LEFT JOIN mf_pay_scheme_detail b ON b.pmt_scheme_id = a.pmt_scheme_id\n" + 
				"LEFT JOIN mf_pay_scheme_detail c ON c.pmt_scheme_id = a.pmt_scheme_id\n" + 
				"LEFT JOIN mf_pay_scheme_detail d ON d.pmt_scheme_id = a.pmt_scheme_id\n" + 

				"WHERE a.pmt_scheme_id = '"+ pmt_scheme_id +"'\n" + 
				"AND a.part_id IN ('012') AND a.part_sequence = 1\n" + 
				"AND b.part_id IN ('012') AND b.part_sequence = 2\n" + 
				"AND c.part_id IN ('013') AND c.part_sequence = 3\n" + 
				"AND (CASE WHEN d.part_id IN ('014') THEN (d.part_id IN ('014') AND d.part_sequence = 4) ELSE TRUE END);";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static String getBuyerTypeGroupID(String type_id) {
		pgSelect db = new pgSelect();
		db.select("SELECT type_group_id FROM mf_buyer_type WHERE type_id = '"+ type_id +"';");

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}


	}

}
