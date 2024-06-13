/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelSORApprovedAccounts;
import tablemodel.modelSORQualifiedAccounts;
import tablemodel.modelSORReturnedAccounts;

/**
 * @author John Lester Fatallo
 */
public class _SalesOfReceivables {

	public _SalesOfReceivables() {
		// TODO Auto-generated constructor stub
	}
	
	//SQL FOR COMPANY
	public static String sqlCompany(){
		return "SELECT TRIM(co_id) AS \"Co. ID\", \n" + 
			   "TRIM(company_name) AS company_name , \n" + 
			   "TRIM(company_alias) AS \"Alias\"\n" + 
			   "FROM mf_company \n" + 
			   "ORDER BY co_id;";
	}
	
	//SQL FOR PROJECT
	public static String sqlProject(String co_id){
		return "SELECT TRIM(proj_id) AS \"ID\", \n" + 
				"proj_name as \"Project Name\", TRIM(proj_alias) as \"Alias\"\n" + 
				"FROM mf_project \n" + 
				"WHERE co_id = '"+co_id+"';\n";
	}
	
	//SQL FOR PHASE
	public static String sqlPhase(String proj_id){
		return "SELECT TRIM(sub_proj_id) as \"ID\", \n" + 
			   "sub_proj_name as \"Phase\", sub_proj_alias as \"Alias\"  \n" + 
			   "FROM mf_sub_project\n" + 
			   "WHERE proj_id = '"+proj_id+"' AND status_id = 'A'";//ADDED STATUS ID BY LESTER DCRF 2718
	}
	
	//SQL FOR BANK
	public static String sqlBank(){
		return "SELECT \n" + 
			   "TRIM(a.bank_id) AS  \"ID\", b.bank_name AS  \"Bank Name\", \n" + 
			   "TRIM(b.bank_alias) as \"Alias\", a.term as \"Term\", a.int_rate as \"Int Rate\"\n" + 
			   "FROM mf_bank_int_rate a\n" + 
			   "LEFT JOIN mf_bank b on b.bank_id = a.bank_id\n" + 
			   "WHERE a.status_id = 'A';";
	}
	
	//SQL FOR SEARCHING BY CLIENT
	public static String sqlSearchByClient(){
		return "SELECT DISTINCT ON (entity_id)\n" + 
			   "TRIM(entity_id) AS \"Entity ID\", \n" + 
			   "get_client_name(entity_id) AS \"Client Name\"\n" + 
			   "FROM rf_bank_header \n" + 
			   "ORDER BY entity_id,get_client_name(entity_id);";
	}
	
	//SQL FOR SEARCHING BY BATCH NO IN THE APPROVED ACCOUNTS
	public static String sqlAASearchByBatchNo(){
		return "SELECT DISTINCT ON (to_number(adc_batch_no, '999999')) adc_batch_no as \"Batch No\", 'Batch No' AS \"Description\"\n" + 
			   "FROM rf_bank_header \n" + 
			   "WHERE adc_batch_no <> '' \n" + 
			   "ORDER BY to_number(adc_batch_no, '999999') DESC;\n";
	}
	
	//SQL FOR SEARCHING BY BATCH NO IN THE RETURNED ACCOUNTS
	public static String sqlRASearchByBatchNo(){
		return "SELECT DISTINCT ON (to_number(adc_batch_no, '999999'))\n" + 
			   "adc_batch_no AS  \"Batch No\", 'Batch No' as \"Description\"\n" + 
			   "FROM rf_bank_header \n" + 
			   "WHERE return_date IS NULL\n" + 
			   "ORDER BY to_number(adc_batch_no, '999999') DESC";
	}
	
	public static Integer getSelectedQualifiedAcct(modelSORQualifiedAccounts model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	public static Integer getSelectedApprovedAcct(modelSORApprovedAccounts model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	public static Integer getSelectedReturnedAcct(modelSORReturnedAccounts model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	private static Date newSchedDate(Date ma_date){
		Date scheddate = null;
		pgSelect db = new pgSelect();
		
		String sql = "select DATE ((SELECT EXTRACT(YEAR FROM DATE '"+ma_date+"' + interval '1 month'))|| '-' || \n" + 
					 "(SELECT EXTRACT(MONTH FROM DATE '"+ma_date+"'))|| '-' || a.due_day) from \n" + 
					 "(select distinct eff_date, start_no, end_no, due_day, status_id FROM mf_bank_due_date \n" + 
					 "where eff_date = (select max(eff_date) from mf_bank_due_date) \n" + 
					 "and status_id = 'A') a \n" + 
					 "where (SELECT EXTRACT(DAY FROM DATE '"+ma_date+"')) \n" + 
					 "between a.start_no and a.end_no; ";
		db.select(sql);
		
		FncSystem.out("Display sql for Scheddate", sql);
		
		if(db.isNotNull()){
			scheddate = (Date) db.getResult()[0][0];
		}
		System.out.printf("Display scheddate: %s%n", scheddate);
		return scheddate;
	}
	
	private static Date nextSchedDate(Date sched_date){
		Date scheddate = null;
		
		pgSelect db = new pgSelect();
		String sql = "SELECT '"+sched_date+"'::DATE + INTERVAL '1 Month'";
		db.select(sql);
		
		FncSystem.out("Display sql for next scheddate", sql);
		if(db.isNotNull()){
			scheddate = (Date) db.getResult()[0][0];
		}
		return scheddate;
	}
	
	public static void displayQualifiedAccounts(String proj_id, String phase ,String bank_id, Date date_cut_off, modelSORQualifiedAccounts model, JList rowHeader){
		//CHECK THE DISPLAY OF QUALIFIED ACCOUNTS HERE
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String sql = "SELECT --DISTINCT ON (c.entity_id, c.proj_id , c.pbl_id, c.seq_no) \n" + 
						 "FALSE,a.projcode ,b.unit_id, a.seq_no,\n" + 
						 "TRIM(b.phase), TRIM(b.block), TRIM(b.lot),\n" + 
						 "a.entity_id, get_client_name(a.entity_id),\n" + 
						 "get_nsp(a.entity_id, a.pbl_id, a.seq_no, a.projcode), --get_nsp(a.entity_id, a.pbl_id, a.proj_id, a.seq_no),\n" + 
						 "get_loan_availed(a.entity_id, a.pbl_id, a.seq_no, a.projcode),\n" + 
						 "get_ob(a.entity_id, a.projcode, a.pbl_id, a.seq_no), \n" + 
						 "count(c.scheddate)::INT, get_bank_int_rate(count(c.scheddate)::INT, '"+bank_id+"')\n" + 
						 "FROM rf_sold_unit a\n" + 
						 "LEFT JOIN mf_unit_info b on b.proj_id = a.projcode and b.pbl_id = a.pbl_id \n" + 
						 "LEFT JOIN rf_client_schedule c on c.entity_id = a.entity_id and c.proj_id = a.projcode and c.pbl_id = a.pbl_id and c.seq_no = a.seq_no\n" + 
						 "WHERE get_group_id(a.buyertype) = '02'\n" + 
						 "AND EXISTS (SELECT * \n" + 
						 "	    FROM rf_buyer_status\n" + 
						 "	    WHERE entity_id = a.entity_id\n" + 
						 "	    AND proj_id = a.projcode\n" + 
						 "	    AND pbl_id = a.pbl_id \n" + 
						 "	    AND byrstatus_id = '19'\n" + 
						 "	    AND tran_date <= '"+date_cut_off+"' \n" + 
						 "	    AND status_id = 'A')\n" + 
						 "AND EXISTS (SELECT *\n" + 
						 "	    FROM rf_client_ledger\n" + 
						 "	    WHERE entity_id = a.entity_id\n" + 
						 "	    AND proj_id = a.projcode\n" + 
						 "	    AND pbl_id = a.pbl_id \n" + 
						 "	    AND seq_no = a.seq_no\n" + 
						 "	    AND part_id = '013'\n" + 
						 "	    AND balance = 0\n" + 
						 "	    AND date_posted <= '"+date_cut_off+"' \n" + 
						 "	    AND status_id = 'A')\n" + 
						 "AND c.part_id = '014'\n" + 
						 "AND c.status_id = 'A'\n" + 
						 "--AND c.scheddate <= 'Tue Sep 29 00:00:00 PHT 2015'\n" + 
						 "AND c.scheddate > '"+date_cut_off+"' \n"+
						 "AND a.projcode = '"+proj_id+"' AND b.sub_proj_id = '"+phase+"'\n" + 
						 "GROUP by b.unit_id, a.seq_no, b.phase, b.block, b.lot, a.entity_id, \n" + 
						 "a.pbl_id, a.projcode;\n";
			db.select(sql);
			
			FncSystem.out("Display sql for Qualified Accounts", sql);
			
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void displayApprovedAccounts(Integer searchBy ,String search,modelSORApprovedAccounts model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String sql = "SELECT FALSE, a.rec_id, TRIM(a.proj_id), TRIM(b.unit_id), a.seq_no, TRIM(b.phase), TRIM(b.block), TRIM(b.lot),\n" + 
						 "a.entity_id ,get_client_name(a.entity_id), get_nsp(a.entity_id, a.pbl_id, a.seq_no, a.proj_id), \n" + 
						 "a.sold_amount::NUMERIC, a.term , a.ma_amount::NUMERIC, a.adc_batch_no, a.int_rate::NUMERIC, a.ma_date \n" + 
						 "FROM rf_bank_header a\n" + 
						 "LEFT join mf_unit_info b on b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n" + 
						 "WHERE a.remarks IS NULL\n" + 
						 "AND (CASE WHEN "+searchBy+" = 0 THEN a.entity_id = '"+search+"' \n" + 
						 "	  WHEN "+searchBy+" = 1 THEN a.adc_batch_no = '"+search+"'\n" + 
						 "	  ELSE a.remarks IS NULL END) \n" + 
						 "ORDER BY a.pbl_id, a.rec_id desc;\n";
			
			db.select(sql);
			FncSystem.out("DIsplay sql for Approved Accounts", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayReturnedAccounts(Integer searchBy,String search, modelSORReturnedAccounts model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String sql = "SELECT FALSE, a.rec_id, TRIM(a.proj_id), TRIM(b.unit_id), a.seq_no, \n"+
						 "TRIM(b.phase), TRIM(b.block), TRIM(b.lot), a.entity_id, \n" + 
						 "get_client_name(a.entity_id), a.availed_amt, a.remarks, a.adc_batch_no \n" + 
						 "FROM rf_bank_header a\n" + 
						 "LEFT JOIN mf_unit_info b on b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n" + 
						 "WHERE a.remarks IS NULL\n" + 
						 "AND (CASE WHEN "+searchBy+" = 0 THEN a.entity_id = '"+search+"'\n" + 
						 "	  WHEN "+searchBy+" = 1 THEN a.adc_batch_no = '"+search+"' \n" + 
						 "	  ELSE a.remarks IS NULL END)\n" + 
						 "ORDER by a.pbl_id, a.rec_id desc";
			db.select(sql);
			
			FncSystem.out("Display sql for Returned Accounts", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	private static String newBatchNo(){
		String batch_no = "";
		pgSelect db = new pgSelect();
		
		String sql = "select to_char(coalesce(max(adc_batch_no::integer), 0) + 1, 'FM000000') from rf_bank_header;\n";
		db.select(sql);
		
		FncSystem.out("Display sql for new batch no", sql);
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		return batch_no;
	}
	
	public static void saveQualifiedAccounts(String bank_id,Date ma_date ,modelSORQualifiedAccounts model){
		String batch_no = newBatchNo();
		
		pgUpdate db = new pgUpdate();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				String proj_id = (String) model.getValueAt(x, 1);
				String unit_id = (String) model.getValueAt(x, 2);
				Integer seq_no = (Integer) model.getValueAt(x, 3);
				String entity_id = (String) model.getValueAt(x, 7);
				BigDecimal availed_amt = (BigDecimal) model.getValueAt(x, 10);
				BigDecimal ob = (BigDecimal) model.getValueAt(x, 11);
				Integer term = (Integer) model.getValueAt(x, 12);
				BigDecimal int_rate = (BigDecimal) model.getValueAt(x, 13);
				BigDecimal interest = int_rate.divide(new BigDecimal("100"));
				
				String sqlBankHD = "INSERT INTO rf_bank_header(rec_id, entity_id, proj_id, pbl_id, seq_no, adc_batch_no, bank_id, \n" + 
							 	   "pn_number, bank_batch_no, availed_amt, int_rate, term, ma_date, \n" + 
							 	   "repricing_date, sold_amount, ma_amount, credit_date, cts_rtn, \n" + 
							 	   "stb_rplf_no, bb_rplf_no, bank_or, or_date, date_repriced, status_rec_id, \n" + 
							 	   "sold_unit_id, status_id, type_group_id, jv_no, orig_sold_amt, \n" + 
							 	   "remarks, return_date ,new_jv_no, new_ma_amount, new_batch_no, new_ma_date, \n" + 
							 	   "new_sold_amount, new_term)\n" + 
							 	   "VALUES ((SELECT COALESCE(max(rec_id), 0) + 1 FROM rf_bank_header), \n"+
							 	   "'"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", \n"+
							 	   "'"+batch_no+"', '"+bank_id+"', NULL, NULL, "+availed_amt+", "+int_rate+", "+term+", \n"+
							 	   "'"+ma_date+"', NULL, "+availed_amt+", \n"+
							 	   "ROUND(("+ob+" * (("+interest+"/12) + (("+interest+"/12) / ((pow(1+("+interest+"/12), "+term+")) -1)))), 2), \n"+
							 	   "NULL, NULL, NULL, NULL, NULL, NULL, NULL, \n"+ 
							 	   "NULL, NULL, NULL, 'A', NULL, NULL, NULL, NULL, NULL, NULL ,NULL, NULL, NULL, NULL) \n";
				db.executeUpdate(sqlBankHD, true);
				
				String sqlByrStat = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
									"actual_date, request_no, inactive_date, status_id, trans_no, \n" + 
									"jv_no, to_mi_mo_projcode, to_mi_mo_pbl_id, branch_id, created_by)\n" + 
									"VALUES ('"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"'), "+seq_no+", \n"+
									"'28', now(), now(), NULL, NULL, 'A', NULL, NULL, NULL, NULL, NULL, '"+UserInfo.EmployeeCode+"')\n";
				db.executeUpdate(sqlByrStat, true);
			}
		}
		db.commit();
	}
	
	//SAVING OF SOR APPROVED ACCOUNTS
	public static void saveApprovedAccounts(Date credit_date, String bank_batch_no, String pn_no, String or_no, modelSORApprovedAccounts model){
		pgUpdate db = new pgUpdate();
		pgSelect dbSelect = new pgSelect();
		//OK NA YUNG INSERT NG DETAILS DITO
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String proj_id = (String) model.getValueAt(x, 2);
				String unit_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 4);
				String entity_id = (String) model.getValueAt(x, 8);
				BigDecimal sold_amt = (BigDecimal) model.getValueAt(x, 11);
				Integer terms = (Integer) model.getValueAt(x, 12);
				BigDecimal premium = (BigDecimal) model.getValueAt(x, 13);
				String batch_no = (String) model.getValueAt(x, 14);
				BigDecimal int_rate = (BigDecimal) model.getValueAt(x, 15);
				Date ma_date = (Date) model.getValueAt(x, 16);
				
				System.out.printf("Display sold amount: %s%n", sold_amt);
				String sqlRfBankHD = "UPDATE rf_bank_header \n" + 
							 		 "SET bank_batch_no = '"+bank_batch_no+"', \n" + 
							 		 "term = "+terms+", \n" + 
							 		 "sold_amount = "+sold_amt+", \n" + 
							 		 "ma_amount = "+premium+", \n" + 
							 		 "credit_date = '"+credit_date+"', \n" + 
							 		 "bank_or = '"+or_no+"', \n" + 
							 		 "pn_number = '"+pn_no+"', \n" + 
							 		 "remarks = 'Approved by Bank' 	\n" + 
							 		 "WHERE adc_batch_no = '"+batch_no+"' \n" + 
							 		 "AND rec_id = "+rec_id+" \n";
				
				db.executeUpdate(sqlRfBankHD, true);
				
				String sqlRfBuyerStatus = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
										  "actual_date, request_no, inactive_date, status_id, trans_no, \n" + 
										  "jv_no, to_mi_mo_projcode, to_mi_mo_pbl_id, branch_id, created_by)\n" + 
										  "VALUES ('"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, \n"+
										  ""+seq_no+", '25', now(), now(), NULL, NULL, 'A', NULL, \n" + 
										  "NULL, NULL, NULL, NULL, '"+UserInfo.EmployeeCode+"');\n";
				db.executeUpdate(sqlRfBuyerStatus, true);
				
				Date scheddate = newSchedDate(ma_date);
				
				int y = 1;
				
				while (y <= terms){
					System.out.printf("Display 1st scheddate: %s%n", scheddate);
					BigDecimal interest = BigDecimal.valueOf(sold_amt.doubleValue() * ((int_rate.doubleValue()/100))/12);
					BigDecimal principal = premium.subtract(interest);
					
					BigDecimal forcebalancetozero = sold_amt;
					sold_amt = sold_amt.subtract(principal);
					
					if(y == terms){
						principal = forcebalancetozero;
						sold_amt = new BigDecimal("0.00");
						premium = principal.add(interest);
					}
					
					String sqlRfBankSchedule = "INSERT INTO rf_bank_schedule(\n" + 
											   "rec_id, entity_id, projcode, pbl_id, seq_no, part_id, scheddate, \n" + 
											   "amount,interest, principal, balance, new_interest, status_id)\n" + 
											   "VALUES ((SELECT COALESCE(max(rec_id), 0) +1 FROM rf_bank_schedule ), '"+entity_id+"', \n"+
											   "'"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '014', '"+scheddate+"', \n" + 
											   "ROUND("+premium+", 2), ROUND("+interest+", 2), ROUND("+principal+", 2), ROUND("+sold_amt+", 2), ROUND("+interest+", 2), 'A');";
					db.executeUpdate(sqlRfBankSchedule, true);
	
					String sqlRfBankScheduleInterestPart = "INSERT INTO rf_bank_part_schedule(\n" + 
												   		   "rec_id, entity_id, unit_id, proj_id, pbl_id, seq_no, part_id, \n" + 
												   		   "scheddate, amount, balance, new_amount, status_id)\n" + 
												   		   "VALUES ((SELECT COALESCE(max(rec_id), 0) +1 FROM rf_bank_part_schedule), '"+entity_id+"', \n"+
												   		   "'"+unit_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '001', \n" + 
												   		   "'"+scheddate+"', ROUND("+interest+", 2), ROUND("+sold_amt+", 2), ROUND("+interest+", 2), 'A');\n";
					db.executeUpdate(sqlRfBankScheduleInterestPart, true);
					
					String sqlRfBankSchedulePrincipalPart = "INSERT INTO rf_bank_part_schedule(\n" + 
															"rec_id, entity_id, unit_id, proj_id, pbl_id, seq_no, part_id, \n" + 
															"scheddate, amount, balance, new_amount, status_id)\n" + 
															"VALUES ((SELECT COALESCE(max(rec_id), 0) +1 FROM rf_bank_part_schedule), \n"+
															"'"+entity_id+"', '"+unit_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, \n"+
															""+seq_no+", '002', '"+scheddate+"', ROUND("+principal+", 2), ROUND("+sold_amt+", 2), 0.00, 'A');\n";
					
					db.executeUpdate(sqlRfBankSchedulePrincipalPart, true);
					
					scheddate = nextSchedDate(scheddate);
					System.out.printf("Display scheddate:%s%n", scheddate);
					y++;
				}
			}
		}
		db.commit();
	}
	
	//SAVING OF SOR RETURNED ACCOUNTS
	public static void saveReturnAccounts(Date return_date, modelSORReturnedAccounts model){
		
		pgUpdate db = new pgUpdate();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String proj_id = (String) model.getValueAt(x, 2);
				String unit_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 4);
				
				String entity_id = (String) model.getValueAt(x, 8);
				String remarks = (String) model.getValueAt(x, 11);
				
				String batch_no = (String) model.getValueAt(x, 12);
				
				String sqlRfBankHD = "UPDATE rf_bank_header\n" + 
						 			 "SET remarks = nullif('"+remarks+"', 'null'), \n" + 
						 			 "return_date = '"+return_date+"'\n" + 
						 			 "WHERE adc_batch_no = '"+batch_no+"'\n" + 
						 			 "AND rec_id = "+rec_id+" \n";
				db.executeUpdate(sqlRfBankHD, true);
				
				String sqlRfBuyerStatus = "DELETE FROM rf_buyer_status \n"+
										  "WHERE entity_id = '"+entity_id+"' \n"+
										  "AND proj_id = '"+proj_id+"' \n"+
										  "AND pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
										  "AND seq_no = "+seq_no+" \n"+
										  "AND byrstatus_id = '28' \n"+
										  "AND status_id = 'A' \n";
				db.executeUpdate(sqlRfBuyerStatus, true);
				
			}
		}
		db.commit();
	}
	
}
