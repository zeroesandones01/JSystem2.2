package Functions;


public class UpdateCRBAmounts {

	public static void main(String[] arg0){
		//updateARHeader();
		//updateARDetails();
		
		//updateORHeader();
		//updateORDetails();
	}

	public static void updateARHeader() {/*
		String strSQL = "select co_id, phase, rb_id, reference_no, pay_rec_id, amount\n" +
				"from itsreal.boi_crb_ar_header\n" +
				"where coalesce(nullif(split_part(amount::text, '.', 2), ''), '0')::bigint > 0;";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(!db.isNull()){
			UpdateDB update = new UpdateDB();
			for(int x=0; x < db.getResult().length; x++){
				System.out.printf("%-10s %-10s %-10s %-5s %-15s %-10s %s %n", x, db.Result[x][0], db.Result[x][1], db.Result[x][2], db.Result[x][3], db.Result[x][4], db.Result[x][5]);

				update.executeUpdate("update boi_crb_ar_header\n" +
						"set amount = "+ db.Result[x][5] +"\n" +
						"where co_id = '"+ db.Result[x][0] +"'\n" +
						"and phase = '"+ db.Result[x][1] +"'\n" +
						"and rb_id = '"+ db.Result[x][2] +"'\n" +
						"and coalesce(reference_no, '') = '"+ db.Result[x][3] +"'\n" +
						"and coalesce(pay_rec_id, '') = '"+ db.Result[x][4] +"'", false);

				//System.out.println();
			}
			update.commit();
		}
	*/}

	public static void updateARDetails() {/*
		String strSQL = "select rb_id, pbl_id, seq_no, entity_id, line_no, trans_amt\n" +
				"from itsreal.boi_crb_ar_detail\n" +
				"where coalesce(nullif(split_part(trans_amt::text, '.', 2), ''), '0')::bigint > 0;";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(!db.isNull()){
			UpdateDB update = new UpdateDB();
			for(int x=0; x < db.getResult().length; x++){
				System.out.printf("%-10s %-10s %-10s %-5s %-15s %-10s %s %n", x, db.Result[x][0], db.Result[x][1], db.Result[x][2], db.Result[x][3], db.Result[x][4], db.Result[x][5]);

				update.executeUpdate("update boi_crb_ar_detail set trans_amt = "+ db.Result[x][5] +" " +
						"where rb_id = '"+ db.Result[x][0] +"' " +
						"and pbl_id = '"+ db.Result[x][1] +"' " +
						"and seq_no = "+ db.Result[x][2] +" " +
						"and entity_id = '"+ db.Result[x][3] +"' " +
						"and line_no = "+ db.Result[x][4] +"", false);

				//System.out.println();
			}
			update.commit();
		}
	*/}

	public static void updateORHeader() {/*
		String strSQL = "select co_id, phase, rb_id, reference_no, pay_rec_id, amount\n" +
				"from itsreal.boi_crb_or_header\n" +
				"where coalesce(nullif(split_part(amount::text, '.', 2), ''), '0')::bigint > 0;";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(!db.isNull()){
			UpdateDB update = new UpdateDB();
			for(int x=0; x < db.getResult().length; x++){
				System.out.printf("%-10s %-10s %-10s %-5s %-15s %-10s %s %n", x, db.Result[x][0], db.Result[x][1], db.Result[x][2], db.Result[x][3], db.Result[x][4], db.Result[x][5]);

				update.executeUpdate("update boi_crb_or_header\n" +
						"set amount = "+ db.Result[x][5] +"\n" +
						"where co_id = '"+ db.Result[x][0] +"'\n" +
						"and phase = '"+ db.Result[x][1] +"'\n" +
						"and rb_id = '"+ db.Result[x][2] +"'\n" +
						"and coalesce(reference_no, '') = '"+ db.Result[x][3] +"'\n" +
						"and coalesce(pay_rec_id, '') = '"+ db.Result[x][4] +"'", false);

				//System.out.println();
			}
			update.commit();
		}
	*/}

	public static void updateORDetails() {/*
		String strSQL = "select rb_id, pbl_id, seq_no, entity_id, line_no, trans_amt\n" +
				"from itsreal.boi_crb_or_detail\n" +
				"where coalesce(nullif(split_part(trans_amt::text, '.', 2), ''), '0')::bigint > 0;";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(!db.isNull()){
			UpdateDB update = new UpdateDB();
			for(int x=0; x < db.getResult().length; x++){
				System.out.printf("%-10s %-10s %-10s %-5s %-15s %-10s %s %n", x, db.Result[x][0], db.Result[x][1], db.Result[x][2], db.Result[x][3], db.Result[x][4], db.Result[x][5]);

				update.executeUpdate("update boi_crb_or_detail set trans_amt = "+ db.Result[x][5] +" " +
						"where rb_id = '"+ db.Result[x][0] +"' " +
						"and pbl_id = '"+ db.Result[x][1] +"' " +
						"and seq_no = "+ db.Result[x][2] +" " +
						"and entity_id = '"+ db.Result[x][3] +"' " +
						"and line_no = "+ db.Result[x][4] +"", false);

				//System.out.println();
			}
			update.commit();
		}
	*/}
}
