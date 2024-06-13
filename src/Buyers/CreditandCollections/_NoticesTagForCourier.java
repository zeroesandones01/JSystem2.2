package Buyers.CreditandCollections;

import java.util.ArrayList;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.model_NoticesTagBatch;
import tablemodel.model_NoticesTaggedBatch;
import Functions.UserInfo;

public class _NoticesTagForCourier {

	public _NoticesTagForCourier() {
	}

	public static void saveBatchTagging(model_NoticesTagBatch model) {

		ArrayList<String> listBatch = new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String batch_no = (String) model.getValueAt(x, 1);
			if(isSelected){
				listBatch.add(String.format("'%s'", batch_no));
			}
		}

		String batch_no = listBatch.toString().replaceAll("\\[|\\]", "");

		String SQL = "SELECT sp_save_notices_sent(ARRAY["+ batch_no +"]::VARCHAR[], '"+ UserInfo.EmployeeCode +"','tagged');";
		System.out.printf("SQL: %s%n", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
	}

	public static void SendLogBook(model_NoticesTaggedBatch model){

		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String batch_no = (String) model.getValueAt(x, 1);
			if(isSelected){

				pgUpdate pU = new pgUpdate();

				pU.executeUpdate("UPDATE rf_notices_sent SET status = 'for pick up' WHERE tagged_by = '"+UserInfo.EmployeeCode+ "'  AND pick_up_date ISNULL AND status = 'tagged' and batch_no = '"+batch_no+"'", true);

				pU.commit();
			}
		}
	}

	public static void SendPostOffice(model_NoticesTaggedBatch model){

		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String batch_no = (String) model.getValueAt(x, 1);
			if(isSelected){

				pgUpdate pU = new pgUpdate();


				pU.executeUpdate("UPDATE rf_notices_sent SET status = 'sent', sent_by = '" +UserInfo.EmployeeCode+ "', sent_to = 'POST OFFICE', tagged_by = '"+UserInfo.EmployeeCode+ "',pick_up_date = now() WHERE batch_no = '"+batch_no+"' ",true);

				pU.executeUpdate("UPDATE rf_client_notices SET datesent =  now(), mailedthru = 'POST OFFICE' WHERE batch_no in (SELECT batch_no FROM rf_notices_sent WHERE sent_to = 'POST OFFICE' AND sent_by = '"+UserInfo.EmployeeCode+ "' and batch_no = '"+batch_no+"'  ) and mailedthru ISNULL ",true);


				pU.commit();
			}
		}


	}

}
