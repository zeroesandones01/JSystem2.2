package System;

import java.awt.Container;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.FncUDIRecords2;
import Functions.SelectClass;

public class _TransferJournalEntries {

	//XXX lookupQuery
	public static String getBookID(int selectedIndex) {
		String sqlQuery = "";
		switch(selectedIndex){
		case 0:
			sqlQuery = "(select trim(jv_id) as jv_id, created_date, posted_date, 'All' from tf_jv_main_all order by trim(jv_id) desc) " +
					"union all " +
					"(select trim(jv_id) as jv_id, created_date, posted_date, 'ER7' from tf_jv_main_er7 order by trim(jv_id) desc) " +
					"union all " +
					"(select trim(jv_id) as jv_id, created_date, posted_date, 'ER8' from tf_jv_main_er8 order by trim(jv_id) desc) " +
					"union all " +
					"(select trim(jv_id) as jv_id, created_date, posted_date, 'ER9' from tf_jv_main_er9 order by trim(jv_id) desc)";
			break;
		case 1:
			sqlQuery = "(select trim(cv_no) as cv_no, date_created, date_posted, 'All' from tf_cv_main_all order by trim(cv_no) desc) " +
					"union all " +
					"(select trim(cv_no) as cv_no, date_created, date_posted, 'ER7' from tf_cv_main_er7 order by trim(cv_no) desc) " +
					"union all " +
					"(select trim(cv_no) as cv_no, date_created, date_posted, 'ER8' from tf_cv_main_er8 order by trim(cv_no) desc) " +
					"union all " +
					"(select trim(cv_no) as cv_no, date_created, date_posted, 'ER9' from tf_cv_main_er9 order by trim(cv_no) desc)";
			break;
		case 2:
			sqlQuery = "(select trim(rb_id) as rb_id, issued_date, posted_date, 'All' from tf_rb_main_all order by trim(rb_id) desc) " +
					"union all " +
					"(select trim(rb_id) as rb_id, issued_date, posted_date, 'ER7' from tf_rb_main_er7 order by trim(rb_id) desc) " +
					"union all " +
					"(select trim(rb_id) as rb_id, issued_date, posted_date, 'ER8' from tf_rb_main_er8 order by trim(rb_id) desc) " +
					"union all " +
					"(select trim(rb_id) as rb_id, issued_date, posted_date, 'ER9' from tf_rb_main_er9 order by trim(rb_id) desc)";
			break;
		default:
			sqlQuery = "";
			break;
		}
		//System.out.println(selectedIndex +" - "+sqlQuery);
		return sqlQuery;
	}

	//XXX RB
	public static void displayRB(tablemodelTransferJournalEntries modelMain, tablemodelTransferJournalEntries modelTotal, JList rowHeader, ArrayList<String> projectName, String co_id, Date dateFrom, Date dateTo, String project){
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String strSQL = "select * from public.crb('"+ co_id +"', '"+ project +"', '"+ dateFrom +"', '"+ dateTo +"');";

		FncSystem.out("CRB", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());

				projectName.add((String) db.getResult()[x][10]);
			}
			FncTables.totalAccountEntries(modelMain, modelTotal, 2, 3);
		}else{
			modelTotal.setValueAt(0.00, 0, 2);
			modelTotal.setValueAt(0.00, 0, 3);
		}
	}

	//XXX CV
	public static void displayCV(tablemodelTransferJournalEntries modelMain, tablemodelTransferJournalEntries modelTotal, JList rowHeader, ArrayList<String> projectName, String co_id, Date dateFrom, Date dateTo, String project){
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String strSQL = "select * from public.cv('"+ co_id +"', '"+ project +"', '"+ dateFrom +"', '"+ dateTo +"');";

		FncSystem.out("Check Voucher", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());

				projectName.add((String) db.getResult()[x][10]);
			}
			FncTables.totalAccountEntries(modelMain, modelTotal, 2, 3);
		}else{
			modelTotal.setValueAt(0.00, 0, 2);
			modelTotal.setValueAt(0.00, 0, 3);
		}
	}

	//XXX JV
	public static void displayJV(tablemodelTransferJournalEntries modelMain, tablemodelTransferJournalEntries modelTotal, JList rowHeader, ArrayList<String> projectName, String co_id, Date dateFrom, Date dateTo, String project) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String strSQL = "select * from public.jv('"+ co_id +"', '"+ project +"', '"+ dateFrom +"', '"+ dateTo +"') where trim(jv_jv_id) = '13120150';";

		FncSystem.out("Journal Voucher", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());

				projectName.add((String) db.getResult()[x][10]);
			}
			FncTables.totalAccountEntries(modelMain, modelTotal, 2, 3);
		}else{
			modelTotal.setValueAt(0.00, 0, 2);
			modelTotal.setValueAt(0.00, 0, 3);
		}
	}

	//XXX transferJournalEntries
	public static void transferJournalEntries(Container cont, tablemodelTransferJournalEntries model, String connectionURL, Object object, ArrayList<String> projectName){
		String _journalNumber = "", _sequence = "", prev_sequence = "";
		for(int x=0; x<model.getRowCount(); x++) {
			SelectClass db = new SelectClass();
			db.setQuerySql("select accnt_id from accnt where accnt_extref = '"+model.getValueAt(x, 0)+"'", false, connectionURL);
			String accnt_id = db.Result[0][0];

			//generating Journal Number & G/L Sequence from PostBooks
			try {
				if(!model.getValueAt(x, 4).equals(model.getValueAt(x -1, 4)) ||
						!model.getValueAt(x, 6).equals(model.getValueAt(x -1, 6)) ||
						!model.getValueAt(x, 7).equals(model.getValueAt(x -1, 7))) {
					
					String book_type = (String) model.getValueAt(x -1, 9);
					String book_number = (String) model.getValueAt(x -1, 10);
					String project = (String) model.getValueAt(x -1, 11);
					
					//System.out.println("****************************POST: " + prev_sequence + "\n");
					postJournalEntries(connectionURL, prev_sequence, _journalNumber);
					updateFS_Books(project, book_type, book_number, _journalNumber);
					
					System.out.printf("****************************[ %s, %s, %s, %s ]%n%n", project, book_type, book_number, _journalNumber);

					db.setQuerySql("select fetchJournalNumber('J/P')", false, connectionURL);
					_journalNumber = db.Result[0][0];

					db.setQuerySql("select fetchGLSequence()", false, connectionURL);
					_sequence = db.Result[0][0];
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				db.setQuerySql("select fetchJournalNumber('J/P')", false, connectionURL);
				_journalNumber = db.Result[0][0];

				db.setQuerySql("select fetchGLSequence()", false, connectionURL);
				_sequence = db.Result[0][0];
			} catch (NullPointerException e1) {
				db.setQuerySql("select fetchJournalNumber('J/P')", false, connectionURL);
				_journalNumber = db.Result[0][0];

				db.setQuerySql("select fetchGLSequence()", false, connectionURL);
				_sequence = db.Result[0][0];
			}

			//adding negative sign in amount for debit side in dBase
			BigDecimal amount = new BigDecimal(0.00);

			try {
				if(((BigDecimal) model.getValueAt(x, 2)).compareTo(new BigDecimal(0.00)) == 1){
					amount = ((BigDecimal) model.getValueAt(x, 2)).multiply(new BigDecimal(-1));
				}else{
					amount = (BigDecimal) model.getValueAt(x, 3);
				}
			} catch (NullPointerException e1) {
				amount = (BigDecimal) model.getValueAt(x, 3);
			}

			/*try {
				amount = ((BigDecimal) model.getValueAt(x, 2)).multiply(new BigDecimal(-1));
			} catch (NullPointerException e1) {
				amount = (BigDecimal) model.getValueAt(x, 3);
			}*/
			
			

			String strSQL = "INSERT INTO sltrans( " +
					"sltrans_id, " +
					"sltrans_created, " +
					"sltrans_date, " +
					"sltrans_sequence, " +
					"sltrans_accnt_id, " +
					"sltrans_source, " +
					"sltrans_docnumber, " +
					//"sltrans_misc_id, " +
					"sltrans_amount, " +
					"sltrans_notes, " +
					"sltrans_journalnumber, " +
					"sltrans_posted, " +
					"sltrans_doctype, " +
					"sltrans_username, " +
					"sltrans_gltrans_journalnumber, " +
					"sltrans_rec, " +
					"sltrans_dateposted, " +
					"sltrans_remarks, " +
					"sltrans_project) " +
					"VALUES (" +
					"(select nextval('sltrans_sltrans_id_seq')), " + //sltrans_id
					"'"+model.getValueAt(x, 4)+"'::timestamp with time zone, " + //sltrans_created
					"'"+model.getValueAt(x, 4)+"'::date, " + //sltrans_date
					""+_sequence+", " + //sltrans_sequence
					""+accnt_id+", " + //sltrans_accnt_id
					"'G/L', " + //sltrans_source
					"'" +model.getValueAt(x, 6) +"', " + //sltrans_docnumber
					//""+bookNumber.get(x)+", " + //sltrans_misc_id
					""+amount+", " + //sltrans_amount
					//"'"+/*tblTransfer.getValueAt(x, 6)+ */"JV ID: " +tblTransfer.getValueAt(x, 6)+"', " + //sltrans_notes
					"'" +model.getValueAt(x, 7)+"', " + //sltrans_notes
					""+_journalNumber+", " + //sltrans_journalnumber
					"false, " + //sltrans_posted
					"'JE', " + //sltrans_doctype
					"'"+model.getValueAt(x, 5)+"', " + //sltrans_username
					"null, " + //sltrans_gltrans_journalnumber
					"false, " + //sltrans_rec
					"'"+model.getValueAt(x, 4)+"'::timestamp with time zone, " + //sltrans_dateposted
					"'"+model.getValueAt(x, 8)+"', " + //sltrans_remarks
					"'"+projectName.get(x)+"')"; //sltrans_project
			try {
				FncUDIRecords2.udirec(strSQL, false, connectionURL);
				FncUDIRecords2.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if(_sequence.equals(prev_sequence)){
				if(x == (model.getRowCount() -1)) {
					String book_type = (String) model.getValueAt(x -1, 9);
					String book_number = (String) model.getValueAt(x -1, 10);
					String project = (String) model.getValueAt(x -1, 11);
					
					postJournalEntries(connectionURL, _sequence, _journalNumber);
					updateFS_Books(project, book_type, book_number, _journalNumber);
					
					System.out.printf("****************************[ %s, %s, %s, %s ]%n%n", project, book_type, book_number, _journalNumber);
				}
				prev_sequence = _sequence;
			} else {
				prev_sequence = _sequence;
			}
			//System.out.println("Doc "+ x +": "+model.getValueAt(x, 6) + "/" + model.getValueAt(x, 4) + " - " + _journalNumber);
			System.out.printf("Line %s: %s / %s - %s%n", x, model.getValueAt(x, 6), model.getValueAt(x, 4), _journalNumber);
			
			
		}
		JOptionPane.showMessageDialog(cont, object + " successfully transfered.", "Transfer", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void postJournalEntries(String connectionURL, String pSLSequence, String _journalNumber) {
		SelectClass dbPost = new SelectClass();

		// Journal Number
		//dbPost.setQuerySql("select fetchJournalNumber('J/P')", false, connectionURL);
		//int _journalNumber = Integer.parseInt(dbPost.Result[0][0]);

		// Journal Sequence
		dbPost.setQuerySql("select fetchGLSequence()", false, connectionURL);
		int _sequence = Integer.parseInt(dbPost.Result[0][0]);

		//save to glTrans
		String loopSLTrans = "SELECT * \n" +
				"FROM sltrans \n" +
				"WHERE ((sltrans_amount<>0.0) \n" +
				"AND (NOT sltrans_posted) \n" +
				"AND (sltrans_source='G/L') \n" +
				"AND (sltrans_sequence="+pSLSequence+")) \n" +
				"ORDER BY sltrans_id \n";
		dbPost.setQuerySql(loopSLTrans, false, connectionURL);

		for(int y=0; y < dbPost.getRowCount; y++) {
			String savetoGLTrans = "INSERT INTO gltrans (" +
					"gltrans_posted, " +
					"gltrans_exported, " +
					"gltrans_created, " +
					"gltrans_date, " +
					"gltrans_sequence, " +
					"gltrans_accnt_id, " +
					"gltrans_source, " +
					"gltrans_notes, " +
					"gltrans_doctype, " +
					"gltrans_docnumber, " +
					//"gltrans_misc_id, " +
					"gltrans_amount, " +
					"gltrans_journalnumber, " +
					"gltrans_username, " +
					"gltrans_remarks, " +
					"gltrans_project) " +
					"VALUES (" +
					"FALSE, " +							//_posted
					"FALSE, " +							//_exported
					"'"+dbPost.Result[y][1]+"', " +		//_created
					"'"+dbPost.Result[y][2]+"', " +		//_date
					""+_sequence+", " +					//_sequence
					""+dbPost.Result[y][4]+", " +		//_accnt_id
					"'"+dbPost.Result[y][5]+"', " +		//_source
					"'"+dbPost.Result[y][9]+"', " +		//_notes
					"'JP', " +							//_doctype
					"'"+dbPost.Result[y][6]+"', " +		//_docnumber
					//"'"+dbPost.Result[y][7]+"', " +	//_misc_id
					""+dbPost.Result[y][8]+", " +		//_amount
					""+_journalNumber+", " +			//_journalNumber
					"'"+dbPost.Result[y][13]+"', " +	//_username
					"'"+dbPost.Result[y][17]+"', " +	//_remarks
					"'"+dbPost.Result[y][18]+"')";		//_project
			try {
				FncUDIRecords2.udirec(savetoGLTrans, false, connectionURL);
				FncUDIRecords2.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//update slTrans
			String updateSLTrans = "UPDATE sltrans SET \n" +
					"sltrans_posted=true, \n" +
					"sltrans_gltrans_journalnumber="+_journalNumber+" \n" +
					"WHERE ((NOT sltrans_posted) \n" +
					"AND (sltrans_source='G/L') \n" +
					"AND (sltrans_sequence="+pSLSequence+"))";
			try {
				FncUDIRecords2.udirec(updateSLTrans, false, connectionURL);
				FncUDIRecords2.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//save to trialBal
			SelectClass dbTrialBal = new SelectClass();
			dbTrialBal.setQuerySql("select postIntoTrialBalance("+_sequence+")", false, connectionURL);
		}
	}
	
	public static void updateFS_Books(String project, String book_type, String book_number, String journalNumber) {
		String table = "";
		String column = "";

		if(project.toLowerCase().equals("all")){
			if(book_type.equals("CRB")){
				table = "tf_rb_main_all";
				column = "rb_id";
			}
			if(book_type.equals("CV")){
				table = "tf_cv_main_all";
				column = "its_real_cvno";
			}
			if(book_type.equals("JV")){
				table = "tf_jv_main_all";
				column = "its_real_jvno";
			}
		}
		if(project.toLowerCase().equals("er7")){
			if(book_type.equals("CRB")){
				table = "tf_rb_main_er7";
				column = "rb_id";
			}
			if(book_type.equals("CV")){
				table = "tf_cv_main_er7";
				column = "its_real_cvno";
			}
			if(book_type.equals("JV")){
				table = "tf_jv_main_er7";
				column = "its_real_jvno";
			}
		}
		if(project.toLowerCase().equals("er8")){
			if(book_type.equals("CRB")){
				table = "tf_rb_main_er8";
				column = "rb_id";
			}
			if(book_type.equals("CV")){
				table = "tf_cv_main_er8";
				column = "its_real_cvno";
			}
			if(book_type.equals("JV")){
				table = "tf_jv_main_er8";
				column = "its_real_jvno";
			}
		}
		if(project.toLowerCase().equals("er9")){
			if(book_type.equals("CRB")){
				table = "tf_rb_main_er9";
				column = "rb_id";
			}
			if(book_type.equals("CV")){
				table = "tf_cv_main_er9";
				column = "its_real_cvno";
			}
			if(book_type.equals("JV")){
				table = "tf_jv_main_er9";
				column = "its_real_jvno";
			}
		}

		String strSQL = "UPDATE fs_books."+ table +" SET pb_no_er7 = '"+ journalNumber +"' WHERE trim("+column+") = '"+ book_number +"';";
		pgUpdate db = new pgUpdate();
		db.executeUpdate(strSQL, true, true);
	}

	//XXX RB
	public static void displayAll(tablemodelTransferJournalEntries modelMain, tablemodelTransferJournalEntries modelTotal, JList rowHeader, ArrayList<String> projectName, String co_id, Date dateFrom, Date dateTo, String project){
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String strSQL = "select *,\n" +
				"(case when (split_part(crb_docnumber, '#', 1) = 'AR' or split_part(crb_docnumber, '#', 1) = 'ORV') then 'CRB' else split_part(crb_docnumber, '#', 1) end) as book_type,\n" +
				"split_part(crb_docnumber, '#', 2) as book_number\n" +
				"from (\n " +
				"	select *, 0 as seq from public.crb('"+ co_id +"', '"+ project +"', '"+ dateFrom +"', '"+ dateTo +"')\n " +
				"	union\n " +
				"	select *, 1 as seq from public.cv('"+ co_id +"', '"+ project +"', '"+ dateFrom +"', '"+ dateTo +"')\n " + 
				"	union\n " +
				"	select *, 2 as seq from public.jv('"+ co_id +"', '"+ project +"', '"+ dateFrom +"', '"+ dateTo +"')\n" +
				") a\n" +
				"where (coalesce(crb_debit, 0.00) + coalesce(crb_credit, 0.00)) > 0.00\n" +
				"order by crb_posted_date, seq, crb_crb_rb_id, case when crb_credit is null then 0.00 else crb_credit end, crb_debit;";

		FncSystem.out("All", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
				
				modelMain.setValueAt(db.getResult()[x][14], x, 9);
				modelMain.setValueAt(db.getResult()[x][15], x, 10);
				modelMain.setValueAt(db.getResult()[x][10], x, 11);

				projectName.add((String) db.getResult()[x][10]);
			}
			FncTables.totalAccountEntries(modelMain, modelTotal, 2, 3);
		}else{
			modelTotal.setValueAt(0.00, 0, 2);
			modelTotal.setValueAt(0.00, 0, 3);
		}
	}

}
