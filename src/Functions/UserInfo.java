package Functions;

import Database.pgSelect;

/**
 * @author Alvin Gonzales
 *
 */
public class UserInfo {

	public static String EmployeeCode = "900668";
	public static String FirstName = null;
	public static String MiddleName = null;
	public static String LastName = null;
	public static String Status = null;
	public static String Status_Desc = null;
	public static String Rank = null;
	public static String Rank_Desc = null;
	public static String Position = null;
	public static String Division = null;
	public static String Division_Alias = null;
	public static String Department = null;
	public static String Department_Alias = null;
	public static String Branch = null;
	public static String Branch_Alias = null;

	public static String FullName = null;
	public static String FullName2 = null;
	//public static String FullName = null;
	public static String Alias = null;

	public static Boolean ADMIN = false;
	public static String branch_id = "01";

	public static String Announcement = "";
	public static Integer rec_id;
	public static void initializeEmployeeInfo(String username) {
		//System.out.printf("Server: %s%n", FncGlobal.server);
		
		/*if(FncGlobal.server.equals("Site - Tent")){
			branch_id = "07";
		}else{
			branch_id = "01";
		}*/
		
		pgSelect dbBranch = new pgSelect();
		String strSQL = "SELECT branch_id FROM ";
		
		
		String SQL = "SELECT * FROM sp_user_login_details('"+ username +"', '"+ branch_id +"');";

		//FncSystem.out("Employee Information", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Login", false, true);

		if(db.isNotNull()){
			EmployeeCode = (String) db.getResult()[0][0];
			FirstName = (String) db.getResult()[0][2];
			MiddleName = (String) db.getResult()[0][3];
			LastName = (String) db.getResult()[0][4];
			Status = (String) db.getResult()[0][5];
			Status_Desc = (String) db.getResult()[0][6];
			Rank = (String) db.getResult()[0][7];
			Rank_Desc = (String) db.getResult()[0][8];
			Position = (String) db.getResult()[0][9];
			Division = (String) db.getResult()[0][10];
			Division_Alias = (String) db.getResult()[0][11];
			Department = (String) db.getResult()[0][12];
			Department_Alias = (String) db.getResult()[0][13];
			Branch = (String) db.getResult()[0][14];
			Branch_Alias = (String) db.getResult()[0][15];
			ADMIN = (Boolean) db.getResult()[0][17];
			
			FullName2 = (String) db.getResult()[0][1];
			FullName = FullName2.toUpperCase();
			Alias = username;

			printEmployeeDetailsInConsole();
		}else{
			printEmployeeDetailsInConsole();
		}
		
		
		String strSQLA = "select announcement,rec_id from rf_announcement order by rec_id desc limit 1;";
		
		pgSelect dbs = new pgSelect();
		dbs.select(strSQLA, "Login", false, true);

		if(dbs.isNotNull()){
			Announcement = (String) dbs.getResult()[0][0];
			rec_id = (Integer) dbs.getResult()[0][1];
		}
		
		
		  
	}

	public static void printEmployeeDetailsInConsole() {
		System.out.println("************************ Employee Details ************************");
		System.out.printf("%-64s %s%n", "*", "*");

		System.out.printf("*  %-20s %-40s %s%n", "Employee Code :", EmployeeCode, "*");
		System.out.printf("*  %-20s %-40s %s%n", "Full Name :", FullName, "*");
		System.out.printf("*  %-20s %-40s %s%n", "Alias :", Alias, "*");

		System.out.printf("%-64s %s%n", "*", "*");

		System.out.printf("*  %-20s %-40s %s%n", "First Name :", FirstName, "*");
		System.out.printf("*  %-20s %-40s %s%n", "Middle Name :", MiddleName, "*");
		System.out.printf("*  %-20s %-40s %s%n", "Last Name :", LastName, "*");

		System.out.printf("%-64s %s%n", "*", "*");

		System.out.printf("*  %-20s %-40s %s%n", "Division :", String.format("%s - %s", Division, Division_Alias), "*");
		System.out.printf("*  %-20s %-40s %s%n", "Department :", String.format("%s - %s", Department, Department_Alias), "*");
		System.out.printf("*  %-20s %-40s %s%n", "Position :", Position, "*");
		System.out.printf("*  %-20s %-40s %s%n", "Rank :", String.format("%s - %s", Rank, Rank_Desc), "*");
		System.out.printf("*  %-20s %-40s %s%n", "Status :", String.format("%s - %s", Status, Status_Desc), "*");

		System.out.printf("%-64s %s%n", "*", "*");

		System.out.printf("*  %-20s %-40s %s%n", "Branch :", String.format("%s - %s", Branch, Branch_Alias), "*");

		System.out.printf("%-64s %s%n", "*", "*");
		System.out.println("************************ Employee Details ************************");
	}
}
