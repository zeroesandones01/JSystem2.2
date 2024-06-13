package components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database.pgSelect;

public class _DB {

	public _DB() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getMS_URL(String co_id, String proj_id) {
		//select the URL that were going to save the data in MS SQL
		pgSelect dbPgSelect = new pgSelect();
		dbPgSelect.select("SELECT url as dbase FROM mf_url WHERE co_id = '"+ co_id +"' AND proj_id = '"+ proj_id +"';");

		String msURL = null;
		if(dbPgSelect.isNotNull()){
			msURL = (String) dbPgSelect.getResult()[0][0];

			//replace IP address into local IP here in mezzanine
			Pattern pattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
			Matcher matcher = pattern.matcher(msURL);
			if (matcher.find()) {
				msURL = msURL.replace(matcher.group(), "177.177.179.87");
			}
		}
		return msURL;
	}
	
	public static String getMS_URL2(String server, String dbase) {
		//select the URL that were going to save the data in MS SQL
		pgSelect dbPgSelect = new pgSelect();
		dbPgSelect.select("SELECT url as dbase FROM mf_url WHERE hostname = '"+ server +"' AND dbase ~* '"+ dbase +"';");

		String msURL = null;
		if(dbPgSelect.isNotNull()){
			msURL = (String) dbPgSelect.getResult()[0][0];

			//replace IP address into local IP here in mezzanine
			Pattern pattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
			Matcher matcher = pattern.matcher(msURL);
			if (matcher.find()) {
				msURL = msURL.replace(matcher.group(), "177.177.179.87");
			}
		}
		return msURL;
	}

}
