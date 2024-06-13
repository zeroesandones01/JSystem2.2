package Admin;


public class _receiptMaintenance {

	public static String sqlCompany(){
		return "select TRIM(co_id)::VARCHAR as \"Company ID\", \n" + 
				"TRIM(company_name) as \"Company Name\", \n" + 
				"TRIM(company_alias)::VARCHAR as \"Company Alias\" \n" + 
				"from mf_company \n" + 
				"order by company_name";
	}

	public static String sqlBranch(){
		return "SELECT TRIM(branch_id)::VARCHAR AS \"ID\", \n"+
				"TRIM(branch_name) AS \"Branch Name\", \n"+
				"TRIM(branch_alias)::VARCHAR AS \"Alias\" \n"+
				"FROM mf_office_branch \n"+
				"WHERE status_id = 'A' \n"+
				"ORDER BY branch_id";
	}

	public static String sqlDocument(){
		/*
		int x = 0; 
		String doc_id = "'0'"; 
		
		while (x<model.getRowCount()){
			String doc = model.getValueAt(x,8).toString().trim();	
			//String cashier = modelAddReceipt.getValueAt(x, 5).toString().trim();
			String cashier = model.getValueAt(x,7).toString().trim();	
			//String branch_id2 = sql_getBranchID(modelAddReceipt.getValueAt(x,4).toString().trim());
			String branch_id2 = model.getValueAt(x,3).toString().trim();
			String status_id2 = model.getValueAt(x,15).toString().trim();

			System.out.printf("UserInfo.FullName " + UserInfo.FullName + "\n");
			System.out.printf("branch_id " + branch_id + "\n");
			System.out.printf("branch_id2 " + branch_id2 + "\n");
			System.out.printf("cashier: %s%n", cashier);
			System.out.printf("status_id2 " + status_id2 + "\n");

			if (cashier.equals(UserInfo.FullName) && branch_id.equals(branch_id2)&&status_id2.equals("A")){	doc_id = doc_id + ",'"+doc+"'" ;}
			else {}	

			x++;
		}

		System.out.printf("Display doc_id: %s%n", doc_id);
		*/
		
		String SQL = "SELECT \n" + 
				"TRIM(doc_id) AS \"ID\",\n" + 
				"TRIM(doc_desc) AS \"Description\", \n" + 
				"TRIM(doc_alias) AS \"Alias\" \n" + 
				"FROM mf_doc_details \n" +
				"WHERE doc_id IN ('01', '03', '08', '307') \n" +
				"ORDER BY doc_id";

		System.out.printf("SQL (List of Doc ID - allowed): " + SQL);
		return SQL;

	}

}
