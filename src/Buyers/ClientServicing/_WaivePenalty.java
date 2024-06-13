package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncBigDecimal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelWP_PaymentsMade;
import tablemodel.modelWP_PaymentsWaived;

public class _WaivePenalty {
	
	public static String sqlClient(){
		return "SELECT entity_id as \"Entity ID\", \n" + 
				"get_client_name(entity_id) as \"Name\", \n" + 
				"projcode as \"Proj. ID\", get_project_name(projcode) as \"Proj. Name\",\n" + 
				"get_unit_id(projcode, pbl_id) as \"Unit ID\",\n" + 
				"get_unit_description(projcode, pbl_id) as \"Unit\",\n" + 
				"seq_no as \"Seq\", \n"+
				"get_nsp(entity_id, pbl_id, seq_no, projcode) as \"Selling Price\", \n" + 
				"_get_tr_date(entity_id, projcode, pbl_id, seq_no) as \"TR Date\", \n" + 
				"_get_or_date(entity_id, projcode, pbl_id, seq_no) as \"OR Date\", \n" + 
				"get_current_status(entity_id, projcode, pbl_id, seq_no) as \"Status\"\n" + 
				"FROM rf_sold_unit \n" + 
				"where status_id != 'I'\n" + 
				"AND currentstatus != '02'\n" + 
				"order by get_client_name(entity_id);";
	}
	
	public static String sqlSearch(){
		return "SELECT a.request_no as \"Request No\", get_client_name(a.new_entity_id) as \"Client\", \n" + 
				"b.req_status_desc as \"Request Status\", a.request_date as \"Request Date\", \n" + 
				"a.new_entity_id as \"Entity ID\", a.new_proj_id as \"Proj. ID\", \n" + 
				"a.new_unit_id as \"Unit ID\", a.new_seq_no as \"Seq.\", a.request_by as \"Request By\", \n" + 
				"a.request_remarks as \"Remarks\"\n" + 
				"from req_rt_request_header a\n" + 
				"LEFT JOIN mf_request_status b on b.req_status_id = a.request_status\n" + 
				"where a.req_type_id ~*'Waive Penalty';";
	}
	
	public static void displayPaymentsMade(modelWP_PaymentsMade model, String entity_id, String proj_id, String unit_id, Integer seq_no, String request_no){
		FncTables.clearTable(model);
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_wp_payments_made('"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+request_no+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL for Payments Made", SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static void displayPaymentsMadeSearch(modelWP_PaymentsMade model, String request_no){
		model.clear();
		
		pgSelect db = new pgSelect();
		String SQL = "";
		db.select(SQL);
		
		FncSystem.out("Display SQL for Searched payments Mde", SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[0]);
			}
		}
	}
	
	public static void displayPaymentsWaived(modelWP_PaymentsWaived model, String request_no, modelWP_PaymentsWaived modelWP_Total){
		if(model != null){
			model.clear();
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_wp_payments_waived('"+request_no+"')";
			db.select(SQL);
			
			FncSystem.out("Display SQL for Payments Waived", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
				}
				totalWPEntries(model, modelWP_Total);
			}else{
				modelWP_Total.setValueAt(FncBigDecimal.zeroValue(), 0, 0);
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
	
	public static String getRequestNo(){
		String request_no = "";
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT TRIM(get_new_request_no())";
		db.select(SQL);
		
		if(db.isNotNull()){
		   request_no = (String) db.getResult()[0][0];
		}
		
		return request_no;
	}
	
	public static void saveWP(modelWP_PaymentsMade model, String request_no ,String entity_id, String proj_id, String unit_id, Integer seq_no, String request_by, String request_remarks){
		pgSelect db = new pgSelect();
		
		ArrayList<Integer> listPayRecID = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			Integer pay_rec_id = (Integer) model.getValueAt(x, 6);
			
			if(selected){
				listPayRecID.add(pay_rec_id);
			}
		}
		
		String pay_rec_id = listPayRecID.toString().replaceAll("\\[|\\]", "");
		
		String SQL = "SELECT sp_save_waive_penalty('"+request_no+"', '"+entity_id+"', '"+proj_id+"', '"+unit_id+"', "+seq_no+", ARRAY["+pay_rec_id+"]::INTEGER[], '"+request_by+"', '"+request_remarks+"', '"+UserInfo.EmployeeCode+"' ,FALSE)";
		db.select(SQL);
		
		FncSystem.out("Display SQL for Saving of Waive Penalty", SQL);
	}
	
	public static void postWP(String request_no){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_post_waive_penalty('"+request_no+"', '"+UserInfo.EmployeeCode+"', FALSE)";
		db.select(SQL);
		
		FncSystem.out("Display SQL for Posting of Waive Penalty", SQL);
	}
	
}
