package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncBigDecimal;
import Functions.FncSystem;
import Functions.UserInfo;
import tablemodel.modelDM_AllDocuments;
import tablemodel.modelDM_Coapp_Submitted_Docs;
import tablemodel.modelDM_DocsOut;
import tablemodel.modelDM_DocumentsForPrinting;
import tablemodel.modelDM_Findings;
import tablemodel.modelDM_Payments;
import tablemodel.modelDM_SubmittedDocuments;

/**
 * @author Alvin Gonzales
 *
 */
public class _DocumentsMonitoring {

	public static String sqlClients() {
		String SQL = "select trim(a.entity_id)::varchar as \"ID\", trim(b.entity_name) as \"Name\", trim(e.unit_id)::varchar as \"Unit ID\", trim(e.description) as \"Unit Description\", a.seq_no as \"Seq.\",\n" + 
				"trim(c.type_desc) as \"Buyer Type\", trim(d.status_desc) as \"Status\",\n" + 
				"(select tran_date from rf_buyer_status where entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no and byrstatus_id = '17' and status_id = 'A') as \"Reservation Date\",\n" + 
				"a.offresdate as \"Official Reservation Date\", trim(a.projcode)::varchar as \"Project ID\", trim(f.proj_name) as \"Project Name\"\n" + 
				"from rf_sold_unit a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"left join mf_buyer_type c on c.type_id = a.buyertype\n" + 
				"left join mf_buyer_status d on d.byrstatus_id = a.currentstatus\n" + 
				"left join mf_unit_info e on e.proj_id = a.projcode and e.pbl_id = a.pbl_id\n" + 
				"left join mf_project f on f.proj_id = a.projcode\n" +
				"order by trim(b.entity_name), trim(e.description);";
		return SQL;		
	}
	
	public static String sqlCoapplicant(String entity_id){//XXX 
		
		String SQL = "SELECT connect_id as \"ID\", get_client_name(connect_id) AS \"Name\"\n" + 
					 "FROM rf_entity_connect \n" + 
					 "where entity_id = '"+entity_id+"'\n" + 
					 "and NULLIF(connect_id, '') is not null\n" + 
					 "and connect_type IN ('CO', 'SC') \n"+
					 "AND status_id = 'A'\n" + 
					 "order by get_client_name(connect_id);";
		
		return SQL;
	}

	public static Object[] dataClientInformation(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "SELECT TRIM(a.entity_id), TRIM(b.entity_name),\n" + 
				"TRIM(a.buyertype), TRIM(c.type_desc),\n" + 
				"TRIM(currentstatus), TRIM(d.status_desc),\n" + 
				"(SELECT to_char(tran_date, 'FMmm/DD/YYYY') FROM rf_buyer_status WHERE entity_id = a.entity_id AND proj_id = a.projcode AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND byrstatus_id = '17' AND status_id = 'A' and a.server_id is null),\n" + 
				"(SELECT to_char(tran_date, 'FMmm/DD/YYYY') FROM rf_buyer_status WHERE entity_id = a.entity_id AND proj_id = a.projcode AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND byrstatus_id = '01' AND status_id = 'A' and a.server_id is null),\n" +
				"a.projcode, e.proj_name, a.pbl_id, get_merge_unit_desc(f.unit_id), a.model_id, get_merge_model_desc(f.unit_id),"
				+ " (case when a.entity_id = '1185209653' then '01' else e.co_id end),"//added by jari cruz asof august 31 2022, reason client is special case(Hernandez, Karen both units)
				+ " h.company_name,\n" + 
				"TRIM(j.class_id), TRIM(j.class_name), c.type_group_id, f.unit_id\n" + 
				"\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN rf_entity b ON b.entity_id = a.entity_id\n" + 
				"LEFT JOIN mf_buyer_type c ON c.type_id = a.buyertype\n" + 
				"LEFT JOIN mf_buyer_status d ON d.byrstatus_id = a.currentstatus and coalesce(d.server_id, '')=coalesce(a.server_id,'')\n" + 
				"LEFT JOIN mf_project e ON e.proj_id = a.projcode /*and coalesce(e.server_id, '')=coalesce(a.server_id,'')*/\n" + 
				"LEFT JOIN mf_unit_info f ON f.proj_id = a.projcode AND f.pbl_id = a.pbl_id and coalesce(f.server_id, '')=coalesce(a.server_id,'')\n" + 
				"LEFT JOIN mf_product_model g ON g.model_id = a.model_id and coalesce(g.server_id, '')=coalesce(a.server_id,'')\n" + 
				"LEFT JOIN mf_company h ON h.co_id = (case when a.entity_id = '1185209653' then '01' else e.co_id end)\n" + //added by jari cruz asof august 31 2022, reason client is special case(Hernandez, Karen both units)
				"LEFT JOIN rf_corp_type i ON i.entity_id = a.entity_id\n" + 
				"LEFT JOIN mf_business_class j ON j.class_id = i.business_class_id\n" + 
				"\n" + 
				"WHERE trim(a.entity_id) = '"+ entity_id +"' AND trim(a.projcode) = '"+ proj_id +"' AND trim(a.pbl_id) = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +";";

		FncSystem.out("Client Information", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static ArrayList<Object[]> displayAllDocuments3(modelDM_AllDocuments model, String buyer_type_id, String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		model.clear();

		String SQL = "select false as select, 1 as copies, trim(doc_id) as doc_id,\n" + 
				"case when to_char(doc_id::INT, 'FM000') in ('081', '042', '047', '079', '181', '122', '043', '182', '019', '018', '051', '120', '040', '028') then '*' else '' end || trim(doc_desc) as doc_desc,\n" + 
				"trim(doc_alias) as doc_alias,\n" + 
				"doc_id::int in ('81', '42', '47', '79', '181', '122', '43', '182', '19', '18', '51', '120', '40', '28') as required, null as details, null as additional_info\n" + 
				"from mf_doc_details\n" + 
				"where modules = 'DM'\n" + 
				//"and doc_id::int in ('81', '42', '47', '79', '181', '122', '43', '182', '19', '18', '51', '120', '40', '28')\n" + 
				"and doc_id::int not in (select doc_id::int from dm_docs_inout where entity_id = '"+ entity_id +"' and proj_id = '"+ proj_id +"' and pbl_id = '"+ pbl_id +"' and seq_no = "+ seq_no +")" +
				"and client_class @> (select array[type_group_id]::varchar[] from mf_buyer_type where type_id = '"+ buyer_type_id +"')\n" + 
				"order by trim(doc_desc);";

		FncSystem.out("All Documents", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
			return listData;
		}else{
			return null;
		}
	}

	public static ArrayList<Object[]> displayAllDocuments(modelDM_AllDocuments model, String buyer_type_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean from_holding) {
		model.clear();

		String SQL = "SELECT * FROM view_dm_all_documents('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ buyer_type_id +"', "+ from_holding +") ORDER BY required DESC, doc_desc ASC;";

		FncSystem.out("All Documents #2", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
				//System.out.printf("Display List value: %s%n", db.getResult()[x]);
			}
			
			return listData;
		}else{
			return null;
		}
	}
	
	//ADDED 2016-05-04 FOR DISPLAY OF COAPPLICANT DOCUMENTS
	public static ArrayList<Object []> displayCoAppDocuments(modelDM_AllDocuments model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String coapplicant_id ,String buyertype_id, Boolean from_holding){
		model.clear();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_dm_coapp_documents('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+coapplicant_id+"', '"+buyertype_id+"', "+from_holding+") ORDER BY c_required DESC, c_doc_desc;\n";
		db.select(SQL);
		
		FncSystem.out("Coapplicant Documents", SQL);
		
		if(db.isNotNull()){
			ArrayList<Object[]> listDocuments = new ArrayList<Object[]>();
			for(int x =0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listDocuments.add(db.getResult()[x]);
			}
			return listDocuments;
		}else{
			return null;
		}
	}

	public static ArrayList<Object[]> displayRequiredDocuments(modelDM_AllDocuments model, String buyer_type_id,
			String entity_id, String proj_id, String unit_id, String seq_no) {
		model.clear();

		String SQL = "select false as select, 1 as copies, trim(doc_id) as doc_id, trim(doc_desc) as doc_desc, trim(doc_alias) as doc_alias,\n" + 
				"doc_id::int in ('81', '42', '47', '79', '181', '122', '43', '182', '19', '18', '51', '120', '40', '28') as required, null as details, null as additional_info\n" + 
				"from mf_doc_details\n" + 
				"where modules = 'DM'\n" + 
				"and doc_id::int in ('81', '42', '47', '79', '181', '122', '43', '182', '19', '18', '51', '120', '40', '28')\n" + 
				"and doc_id::int not in (select doc_id::int from dm_docs_inout where entity_id = '"+ entity_id +"' and proj_id = '"+ proj_id +"' and pbl_id = getinteger('"+ unit_id +"')::text and seq_no = "+ seq_no +")" + 
				"and client_class @> (select array[type_group_id]::varchar[] from mf_buyer_type where type_id = '"+ buyer_type_id +"')\n" + 
				"order by doc_desc;";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
			return listData;
		}else{
			return null;
		}
	}

	public static void displayDocumentsForPrinting(modelDM_DocumentsForPrinting model, String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		model.clear();

		/*String SQL = "SELECT FALSE as select, TRIM(doc_id) as doc_id, TRIM(doc_desc) as doc_desc, TRIM(url) as report_name\n" + 
				"FROM mf_doc_details\n" + 
				"WHERE doc_id != '2P' AND to_char(doc_id::INT, 'FM000') IN\n" + 
				"  (SELECT UNNEST(CASE\n" + 
				"    WHEN b.type_group_id = '02' THEN ARRAY['006', '017', '086', '204']\n" + 
				"    WHEN b.type_group_id = '03' THEN ARRAY['006', '035', '017', '086', '036', '096', '204']\n" + 
				"    WHEN b.type_group_id = '04' THEN ARRAY['039', '017', '086', '203', '204']\n" + 
				"    END)\n" + 
				"  FROM rf_sold_unit a\n" + 
				"  LEFT JOIN mf_buyer_type b ON b.type_id = a.buyertype\n" + 
				"  WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +")\n" + 
				//"AND doc_id NOT IN (SELECT doc_id FROM rf_buyer_documents WHERE entity_id = '"+ entity_id +"' AND projcode = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"' AND seq_no = "+ seq_no +")\n" + 
				"AND (CASE WHEN (SELECT model_id IN (SELECT MODEL_ID FROM Mf_product_model WHERE model_desc ~* '(bianca|lot)') FROM mf_unit_info WHERE proj_id = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"')\n" + 
				"  THEN doc_id not in ('086')\n" + 
				"  ELSE doc_id::integer > 000\n" + 
				"  END);";*/
		
		String SQL = "SELECT * FROM view_dm_docs_for_printing('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";

		FncSystem.out("Documents for Printing", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}
	}

	public static void displayDocsOut(modelDM_DocsOut model, String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		model.clear();

		String SQL = "SELECT false as select, a.no_copies, a.doc_id, b.doc_desc, COALESCE(a.docs_in, a.date_submitted), a.docs_out, a.date_evaluated, a.evaluation, a.status_id\n" + 
				"FROM rf_buyer_documents a\n" + 
				"LEFT JOIN mf_system_doc b ON b.doc_id = a.doc_id\n" + 
				"WHERE entity_id = '"+ entity_id +"' AND projcode = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"' AND seq_no = "+ seq_no +";";

		FncSystem.out("Docs Out", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displaySubmittedDocuments(modelDM_SubmittedDocuments model, String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		model.clear();

		String SQL = "SELECT * FROM view_pa_submitted_docs('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		FncSystem.out("Submitted Documents", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}
	}
	
	public static void displayCoAppSubmittedDocuments(modelDM_Coapp_Submitted_Docs model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String coapplicant_id){
		model.clear();
		
		pgSelect db = new pgSelect();
		/*String SQL = "SELECT a.no_copies, a.doc_id, b.doc_desc, c.received_from, c.received_date,\n" + 
				"CASE WHEN timestamp_gt(a.docs_out, a.docs_in) THEN a.docs_out ELSE (CASE WHEN a.docs_in IS NULL THEN a.docs_out ELSE NULL END) END AS docs_out,\n" + 
				"CASE WHEN timestamp_gt(a.docs_in, a.docs_out) THEN a.docs_in ELSE NULL END AS docs_in,\n" + 
				"c.remarks, a.status_id,\n" + 
				"(select array_to_string(array_agg(details), ' ')\n" + 
				"  from (select format('%s: %s;', detail_key, detail_value) as details\n" + 
				"    from rf_buyer_documents_details\n" + 
				"    where doc_id = trim(a.doc_id)\n" + 
				"    and entity_id = '"+coapplicant_id+"'\n" + 
				"    and projcode = '"+proj_id+"'\n" + 
				"    and pbl_id = '"+pbl_id+"'\n" + 
				"    and seq_no = "+seq_no+"\n" + 
				"    order by doc_sequence) a) as additional_info\n" + 
				"FROM dm_coapplicant_documents a\n" + 
				"LEFT JOIN mf_documents b on b.doc_id = a.doc_id\n" + 
				"left join dm_docs_inout c on c.entity_id = a.coapplicant_id and c.proj_id = a.projcode and c.pbl_id = a.pbl_id and c.seq_no = a.seq_no and c.doc_id = a.doc_id\n" + 
				"WHERE a.entity_id = '"+entity_id+"'\n" + 
				"and a.projcode = '"+proj_id+"'\n" + 
				"and a.pbl_id = '"+pbl_id+"'\n" + 
				"and a.seq_no = "+seq_no+"\n" + 
				"and a.coapplicant_id = '"+coapplicant_id+"'\n" + 
				"order by trim(b.doc_desc), c.received_date;";*/
		
		String SQL = "SELECT * FROM view_coapp_submitted_docs('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+coapplicant_id+"');";
		db.select(SQL);
		
		FncSystem.out("Coapplicant Submitted Documents", SQL);
		
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}
	}

	public static void saveDocuments(modelDM_AllDocuments model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String unit_id, String byrtype_id, String received_by, String received_from, Date date_submitted) {
		ArrayList<Integer> listCopies = new ArrayList<Integer>();
		ArrayList<String> listDocuments = new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			Integer copies = (Integer) model.getValueAt(x, 1);
			String doc_id = (String) model.getValueAt(x, 2);
			String doc_name = (String) model.getValueAt(x, 3);
			if(isSelected){
				System.out.printf("%s - %s%n", doc_id, doc_name);

				listCopies.add(copies);
				listDocuments.add(String.format("'%s'", doc_id));
			}
		}

		String documents = listDocuments.toString().replaceAll("\\[|\\]", "");
		String copies = listCopies.toString().replaceAll("\\[|\\]", "");

		String SQL = "SELECT sp_save_documents('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ unit_id +"', '"+ byrtype_id +"', ARRAY["+ documents +"]::VARCHAR[], ARRAY["+ copies +"]::INT[], '"+ received_by +"', '"+received_from+"', '"+date_submitted+"' ,'"+ UserInfo.EmployeeCode +"');";
		System.out.printf("%nSQL: %s%n", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
	}
	
	//JLF 2016-05-04 ADDED FOR THE SAVING OF COAPPLICANT DOCUMENTS
	//Add Received
	public static void saveCoAppDocuments(modelDM_AllDocuments model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String coapplicant_id ,String byrtype_id, String received_by, String received_from, Date date_received){
		ArrayList<Integer> listCopies = new ArrayList<Integer>();
		ArrayList<String> listDocuments = new ArrayList<String>();
		
		for(int x =0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			Integer copies = (Integer) model.getValueAt(x, 1);
			String doc_id = (String) model.getValueAt(x, 2);
			String doc_name = (String) model.getValueAt(x, 4);
			
			if(isSelected){
				System.out.printf("%s- %s%n", doc_id, doc_name);
				
				listCopies.add(copies);
				listDocuments.add(String.format("'%s'", doc_id));
			}
		}
		
		String documents = listDocuments.toString().replaceAll("\\[|\\]", "");
		String copies = listCopies.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_coapp_documents('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+coapplicant_id+"', '"+byrtype_id+"', ARRAY["+documents+"]::VARCHAR[], ARRAY["+copies+"]::INT[], '"+received_by+"', '"+received_from+"', '"+date_received+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display Coapp Doc Saving", SQL);
	}

	public static void saveDocumentsDetails(modelDM_AllDocuments model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String unit_id) {
		pgUpdate db = new pgUpdate();

		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String doc_id = (String) model.getValueAt(x, 2);
			String doc_desc = (String) model.getValueAt(x, 3);
			//Boolean isRequired = (Boolean) model.getValueAt(x, 5);
			String details = (String) model.getValueAt(x, 8);

			if(isSelected) {
				//System.out.printf("%-50s: %s (%s) %s%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length, isRequired);

				if(/*isRequired && */details != null){ //commented by Alvin Gonzales (2015-09-03)
					System.out.println();
					//System.out.printf("%-50s%n", doc_desc);
					System.out.printf("%-50s: %s (%s)%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length);

					int doc_sequence = 1;
					for(String detail_part : details.split("\\;")){
						String key = detail_part.split("\\:")[0].trim();
						String value = detail_part.split("\\:")[1].trim();
						//System.out.printf("%-50s: %s: %s%n", doc_desc, key, value);
						String SQL = "INSERT INTO rf_buyer_documents_details(\n" + 
								"            entity_id, projcode, pbl_id, seq_no, doc_id, doc_sequence, detail_key, \n" + 
								"            detail_value, created_by, date_created, status_id, unit_id)\n" + 
								"    VALUES ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ doc_id +"', "+ (doc_sequence++) +", '"+ key +"', \n" + 
								"            '"+ value +"', '"+ UserInfo.EmployeeCode +"', now(), 'A', '"+ unit_id +"');";
						db.executeUpdate(SQL, true);
					}
					//System.out.println();
					//System.out.printf("%-50s: %s (%s)%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length);
				}
			}
		}
		db.commit();
	}
	
	public static void updateDocDetails(DefaultTableModel model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String unit_id){
		pgSelect db = new pgSelect();
		
		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String doc_id = (String) model.getValueAt(x, 2);
			String doc_desc = (String) model.getValueAt(x, 3);
			//Boolean isRequired = (Boolean) model.getValueAt(x, 5);
			String details = (String) model.getValueAt(x, 11);
			
			if(isSelected){
				int doc_sequence = 1;
				for(String detail_part : details.split("\\;")){
					String key = detail_part.split("\\:")[0].trim();
					String value = detail_part.split("\\:")[1].trim();
					
					String SQL = "SELECT sp_save_doc_details('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+doc_id+"', "+(doc_sequence++)+", '"+key+"', '"+value+"', '"+UserInfo.EmployeeCode+"')";
					db.select(SQL);
					FncSystem.out("Display SQL for Save Doc Details", SQL);
				}
			}
		}
	}

	public static String sqlFindings() {
		String SQL = "SELECT TRIM(remarks_id) as \"ID\", TRIM(remarks_desc) as \"Findings\", get_employee_name(created_by) as \"Created By\", date_created as \"Date Created\"\n" + 
				"FROM mf_docs_findings\n" + 
				"WHERE status_id = 'A'\n" + 
				"ORDER BY remarks_desc;";
		return SQL;
	}

	public static void displayFindings(modelDM_Findings model, String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		model.clear();

		String SQL = "SELECT FALSE, a.date_ok IS NOT NULL ,COALESCE(b.remarks_desc, a.gen_findings), a.date_eval, a.eval_by, a.date_ok, a.ok_by, a.rec_id\n" + 
				"FROM dm_gen_findings a\n" + 
				"LEFT JOIN mf_docs_findings b ON b.remarks_id = a.gen_findings \n"+
				"LEFT JOIN em_employee c on c.emp_code = a.created_by \n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.proj_id = '"+ proj_id +"'\n" + 
				"AND a.pbl_id = '"+ pbl_id +"'\n" + 
				"AND a.seq_no = "+ seq_no +"\n"+
				//"AND c.dept_code = '"+UserInfo.Department+"' \n" + 
				"AND a.status_id = 'A'\n" + 
				"ORDER BY a.date_eval;";

		FncSystem.out("Findings", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}
	}
	
	public static void displayCoappFindings(modelDM_Findings model, String entity_id, String proj_id, String pbl_id, Integer seq_no){
		model.clear();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT FALSE, COALESCE(b.remarks_desc, a.gen_findings), a.date_eval, a.eval_by, a.date_ok, a.ok_by, a.rec_id\n" + 
					 "FROM dm_gen_findings a\n" + 
					 "LEFT JOIN mf_docs_findings b ON b.remarks_id = a.gen_findings \n"+
					 "LEFT JOIN em_employee c on c.emp_code = a.created_by \n" + 
					 "WHERE a.entity_id = '"+entity_id+"'\n" + 
					 "AND a.proj_id = '"+proj_id+"'\n" + 
					 "AND a.pbl_id = '"+pbl_id+"'\n" + 
					 "AND a.seq_no = "+seq_no+"\n" +
					 "AND c.dept_code = '"+UserInfo.Department+"'	\n" + 
					 "AND a.status_id = 'A'\n" + 
					 "ORDER BY a.date_eval;";
		db.select(SQL);
		
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}
	}

	public static void saveFindings(String entity_id, String proj_id, String pbl_id, Integer seq_no, String gen_findings, String eval_by) {
		String SQL = "INSERT INTO dm_gen_findings(\n" + 
				"            entity_id, proj_id, pbl_id, seq_no, date_eval, \n" + 
				"            eval_by, gen_findings, status_id, created_by)\n" + 
				"    VALUES ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", now(), \n" + 
				"            '"+ eval_by +"', '"+ gen_findings +"', 'A', '"+UserInfo.EmployeeCode+"');";

		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, true);
		db.commit();
	}

	public static void updateFindings(modelDM_Findings model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String eval_by) {
		pgUpdate db = new pgUpdate();
		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String gen_findings = ((String) model.getValueAt(x, 2)).replace("'", "''");
			Timestamp date_ok = (Timestamp) model.getValueAt(x, 5);
			Integer rec_id = (Integer) model.getValueAt(x, 7);
			
			System.out.printf("Display Value of Boolean: %s%n", eval_by.equals(""));
			
			if(isSelected){
				if(eval_by.equals("")){
					
					String SQL = "UPDATE dm_gen_findings\n" + 
							"SET gen_findings = '"+ gen_findings +"', date_ok = NULL, ok_by = ''\n" + 
							"WHERE entity_id = '"+ entity_id +"' AND proj_id = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"' AND seq_no = "+ seq_no +" AND rec_id = "+ rec_id +";";
					
					db.executeUpdate(SQL, true);
				}else{
					//if(date_ok != null){
						String SQL = "UPDATE dm_gen_findings\n" + 
								"SET gen_findings = '"+ gen_findings +"', date_ok = now(), ok_by = '"+ eval_by +"', complied_by = '"+UserInfo.EmployeeCode+"' \n" + 
								"WHERE entity_id = '"+ entity_id +"' AND proj_id = '"+ proj_id +"' AND pbl_id = '"+ pbl_id +"' AND seq_no = "+ seq_no +" AND rec_id = "+ rec_id +";";
						db.executeUpdate(SQL, true);
					//}
				}
			}
		}
		db.commit();
	}

	/**
	 * added by Alvin Gonzales (2015-07-03)
	 * 
	 * @param modelMain
	 * @param entity_id
	 * @param proj_id
	 * @param pbl_id
	 * @param seq_no
	 */
	public static void displayPayments(modelDM_Payments modelMain, String entity_id, String proj_id, String pbl_id, int seq_no, modelDM_Payments modelTotal) {
		modelMain.clear();

		String SQL = "SELECT c_tran_date, c_particulars, c_amount, c_pmt_type, c_check_no, c_check_date, c_check_status, c_bank, c_branch, c_account_no, c_or_no, null, c_pay_rec_id\n" + 
				"FROM view_card_payments_v2('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", true);";

		FncSystem.out("Payments", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
			totalEntries(modelMain, modelTotal);
		}
	}

	/**
	 * added by Alvin Gonzales (2015-07-03)
	 *
	 * @param modelMain
	 * @param modelTotal
	 */
	public static void totalEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal(0.00);

		modelTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 2);
		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amount = amount.add(((BigDecimal) modelMain.getValueAt(x, 2)));
				//System.out.printf("Amount: %s;%n", amount);
			} catch (NullPointerException e) {
				amount = amount.add(new BigDecimal(0.00));
			}
		}
		modelTotal.setValueAt(amount, 0, 2);
		//modelTotal.addRow(new Object[] { null, "Total", null, amount, null, null, null, null, null, null, null, null, null, null, null, null });
	}

	/**
	 * added by Alvin Gonzales (2015-07-06)
	 * 
	 */
	public static Boolean saveDocsCompletion(String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean full_dp, Date full_dp_date, Boolean ok_minor_docs,
			Date ok_minor_docs_date, Boolean accounts_complete, Date accounts_complete_date, Boolean docs_complete, Date docs_complete_date, BigDecimal co_app_desired_loan_amnt) {
		String SQL = "SELECT sp_save_dm_docs_completion_v2('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +",\n" + 
				"	"+ full_dp +", NULLIF('"+ full_dp_date +"', 'null')::TIMESTAMP, "+ ok_minor_docs +", NULLIF('"+ ok_minor_docs_date +"', 'null')::TIMESTAMP, "+ accounts_complete +", NULLIF('"+ accounts_complete_date +"', 'null')::TIMESTAMP, \n" +
				"	"+ docs_complete +", NULLIF('"+ docs_complete_date +"', 'null')::TIMESTAMP, "+co_app_desired_loan_amnt+",'"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"')";

		FncSystem.out("Docs Completion", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}

	public static Object[] getDocsCempletionData(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "SELECT b.byrstatus_id IS NOT NULL as selected, b.tran_date, (CASE WHEN f.type_group_id = '04' THEN d.dp ELSE e.dp END), a.byrstatus_id, a.status_desc--, a.*\n" + 
				"FROM mf_buyer_status a\n" + 
				"LEFT JOIN rf_buyer_status b ON b.byrstatus_id = a.byrstatus_id AND b.entity_id = '"+ entity_id +"' AND b.proj_id = '"+ proj_id +"' AND b.pbl_id = '"+ pbl_id +"' AND b.seq_no = "+ seq_no +" AND b.status_id = 'A' AND COALESCE(a.server_id, '') = COALESCE(b.server_id)\n" + 
				"LEFT JOIN rf_sold_unit c ON c.entity_id = '"+ entity_id +"' AND c.projcode = '"+ proj_id +"' AND c.pbl_id = '"+ pbl_id +"' AND c.seq_no = "+ seq_no +" AND COALESCE(c.server_id, '') = COALESCE(b.server_id) AND COALESCE(c.proj_server, '') = COALESCE(b.proj_server, '')\n" + 
				"LEFT JOIN mf_buyer_type f ON f.type_id = c.buyertype\n" + 
				"LEFT JOIN rf_pagibig_computation d ON d.entity_id = '"+ entity_id +"' AND d.proj_id = '"+ proj_id +"' AND d.pbl_id = '"+ pbl_id +"' AND d.seq_no = "+ seq_no +" AND d.status_id = 'A'\n" + 
				"LEFT JOIN rf_ihf_computation e ON e.entity_id = '"+ entity_id +"' AND e.proj_id = '"+ proj_id +"' AND e.pbl_id = '"+ pbl_id +"' AND e.seq_no = "+ seq_no +" AND e.status_id = 'A'\n" + 
				"WHERE a.byrstatus_id IN ('19', '18', '21', '20') \n"+				 
				"ORDER BY a.byrstatus_id::INT;";

		FncSystem.out("Docs Completion Details", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			
			Boolean account_complete = (Boolean) db.getResult()[0][0];
			Boolean full_dp = (Boolean) db.getResult()[1][0];
			Boolean ok_minor_docs = (Boolean) db.getResult()[2][0];
			Boolean return_documents = (Boolean) db.getResult()[3][0];
			
			Timestamp account_complete_date = (Timestamp) db.getResult()[0][1];
			Timestamp full_dp_date = (Timestamp) db.getResult()[1][1];
			Timestamp ok_minor_docs_date = (Timestamp) db.getResult()[2][1];
			Timestamp return_documents_date = (Timestamp) db.getResult()[3][1];
				
			BigDecimal dp = (BigDecimal) db.getResult()[0][2];
			
			return new Object[]{full_dp, full_dp_date, ok_minor_docs, ok_minor_docs_date, return_documents, return_documents_date, account_complete, account_complete_date, dp};
		}else{
			return null;
		}
	}
	
	public static Boolean isORDocsComplete(String entity_id, String proj_id, String pbl_id, Integer seq_no, String buyer_type_id){
		
		pgSelect db = new pgSelect();
		
		//String sql = "SELECT is_or_docs_complete('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+buyer_type_id+"')";
		
		String sql = "SELECT is_or_docs_complete_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
		//db.select(sql);
		db.select(sql, "Save", true);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public static Boolean hasActiveOKMinor(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT EXISTS (SELECT * FROM rf_buyer_status WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND byrstatus_id = '20' AND status_id = 'A')";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
		
	}
	
	public static Boolean withCommission(String pbl_id, Integer seq_no){
		pgSelect db = new pgSelect();
		String SQL = "select exists (select rec_id from cm_schedule_dl where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and status not in ('I','X') and comm_type != 'BB')";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public static Boolean withMerged(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM hs_sold_other_lots WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND status_id = 'A')";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
	}

}
