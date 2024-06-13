package Buyers.ClientServicing;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelCARD_Ledger;
import tablemodel.modelCARD_Payments;
import tablemodel.modelCARD_Schedule;
import tablemodel.modelClientFollowup;

public class _ClientRequestOldDetails {

	public _ClientRequestOldDetails() {
		// TODO Auto-generated constructor stub
	}
	
	public static String sqlOldClients(){
		return "SELECT * FROM view_client_request_old_clients();\n";
	}
	
	public static Object[] displayClientDetails(String request_no){
		
		String SQL = "SELECT * FROM view_client_old_details('"+request_no+"')";
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		FncSystem.out("Display Client OLD Details", SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	public static void displaySchedule(modelCARD_Schedule model, String request_no, Boolean toPrint) {
		model.clear();

		String SQL = "SELECT * FROM view_request_old_schedule('"+request_no+"');";

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
	
	public static void displayOldLedger(modelCARD_Ledger model, String request_no, Boolean toPrint){
		model.clear();
		
		String SQL = "SELECT * FROM view_client_request_old_ledger('"+request_no+"') ORDER BY COALESCE(c_percent_paid, 0), c_trans_date, c_sched_date, (case when c_penalty is not null then 1 else 2 end);";
		
		if(toPrint){
			FncSystem.out("Display Old Ledger", SQL);
		}
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static void displayOldPayments(modelCARD_Payments model, String request_no, Boolean toPrint){
		model.clear();
		
		String SQL = "SELECT * FROM view_client_request_old_payments('"+request_no+"')";
		
		if(toPrint){
			FncSystem.out("Display SQL Old Payments", SQL);
		}
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static void displayFollowUp(modelClientFollowup model,String request_no, Boolean toPrint){
		FncTables.clearTable(model);
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM view_client_followup('"+request_no+"')";
		
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
}
