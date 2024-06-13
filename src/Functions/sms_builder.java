package Functions;

import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import Database.pgUpdate;

import java.io.*;

public class sms_builder {
	
	/*	GitHub Account			*/
	/*	Username:	spt98		*/
	/*	Password:	h0k@g398	*/
	
	public static String strEmail = "sptacct98@gmail.com";

	//public static String strGmailPassword = "h0k@g398";
	//public static String strGmailPassword = "jst 0ff1c1@l 3m@1l";
	//c3nqh0m3sjstsp3c1@l1st
	
	
	
	
	public static String strGmailPassword = "jst 0ff1c1@l 3m@1l";
	//public static String strGmailPassword = "h0k@g398";

	public static String strPassword = "hokage98";
	public static String strDevice = "97145";
	public static String strToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhZG1pbiIsImlhdCI6MTUzNTk0NDU2NSwiZXhwIjo0MTAyNDQ0ODAwLCJ1aWQiOjUxOTQ4LCJyb2xlcyI6WyJST0xFX1VTRVIiXX0.HsRhCq1eTFymhhWNcOx91pESt2LSL8zIr6-F_dRX_fM";
	
	/*
	public static String strEmail = "penmanship27@gmail.com";
	public static String strPassword = "wolframalpha";
	public static String strDevice = "81475";n
	public static String strToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhZG1pbiIsImlhdCI6MTUyNjM0NDEyNiwiZXhwIjo0MTAyNDQ0ODAwLCJ1aWQiOjQ4ODIxLCJyb2xlcyI6WyJST0xFX1VTRVIiXX0.HA8kk1g8udXEvPVojjTNDNNbV-kb1D4POdMsSokx5jU";
	*/
	
	public static String strNumber; 
	public static String strMessage; 
	public static String itusername= "itd";
	public static String itpassword= "123456";
	//public static String strURL = "https://smsgateway.me/api/v4/message/send";
	public static String strURL = "http://acertxt.cenqhomes.com:8090/SendSMS";
	
	private static InetSocketAddress proxyInet = new InetSocketAddress("proxy1.verdantpoint.com", 3128);
	private static Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyInet);
	public static String msg_status;
	// 4
	public static void createMessageLog(String strMessageID, String strGatewayMessageID, String strMessage, String strMobile, String strResponse) {
		System.out.println("Dumaan dito sa createMessageLog");
		System.out.println("");
		
		String strSentBy = FncGlobal.GetString("select sent_by from rf_sms_batch where msg_id = '"+strMessageID+"'"); 
		
		Integer intRowID = FncGlobal.GetInteger("select (coalesce(max(log_row_id), 0)+1)::int from rf_sms_log"); 
		String strExec = "insert into rf_sms_log(log_row_id, msg_id, gateway_msg_id, message, cellphone, sent_by, date_sent, gateway_response) \n" +
				//"values ('"+intRowID+"'::int, '"+strMessageID+"'::int, '"+strGatewayMessageID+"'::int, '"+strMessage+"', '"+strMobile+"', '"+strSentBy+"', now(), '"+strResponse+"')";  
				"values ('"+intRowID+"'::int, '"+strMessageID+"'::int, null::int, '"+strMessage+"', '"+strMobile+"', '"+strSentBy+"', now(), '"+strResponse+"')";
				
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate(strExec, false);
		dbExec.commit();
	}
	
	public static void createMessageLogUSB(String strMessageID, String strGatewayMessageID, String strMessage, String strMobile, String strResponse) {
		String strSentBy = FncGlobal.GetString("select sent_by from rf_sms_batch where msg_id = '"+strMessageID+"'"); 
		
		Integer intRowID = FncGlobal.GetInteger("select (coalesce(max(log_row_id), 0)+1)::int from rf_sms_log"); 
		String strExec = "insert into rf_sms_log(log_row_id, msg_id, gateway_msg_id, message, cellphone, sent_by, date_sent, gateway_response) \n" +
				"values ('"+intRowID+"'::int, '"+strMessageID+"'::int, null::int, '"+strMessage+"', '"+strMobile+"', '"+strSentBy+"', now(), '"+strResponse+"')";  
		
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate(strExec, false);
		dbExec.commit();
	}

	public static boolean containsIllegal(String toExamine) {
		System.out.println("Dumaan dito sa containsIllegal");
	    Pattern pattern = Pattern.compile("'");
	    Matcher matcher = pattern.matcher(toExamine);
	    return matcher.find();
	}
	
	public static Boolean SendMessageToNumber() throws Exception {
    	System.out.println("Dumaan dito sa SendMessageToNumber");
    	Boolean blnSuccess = false; 
    	strEmail = strEmail.replace("@", "%40"); 
    	//strMessage = strMessage.replace(" ", "+"); 

    	URL url = new URL(strURL+"?email="+strEmail+"&password="+strPassword+"&device="+strDevice+"&number="+strNumber+"&message="+strMessage);
    	
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setRequestMethod("GET");

    	System.out.println(url);
    	System.out.println("Response Code: " + con.getResponseCode());
    	
    	if (con.getResponseCode()==200) {
    		blnSuccess = true; 
    	}

    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    	String inputLine;
    	StringBuffer content = new StringBuffer();
    	
    	while ((inputLine = in.readLine()) != null) {
    		content.append(inputLine);
    		content.append("\n");
    	}
  
    	System.out.print(content);
    	
    	in.close();
    	con.disconnect();
    	
    	return blnSuccess; 
    }
    
	public static Boolean SendMessageToManyNumber() throws Exception {
    	System.out.println("Dumaan dito sa SendMessageToManyNumber");
    	Boolean blnSuccess = false; 
    	strEmail = strEmail.replace("@", "%40"); 

    	URL url = new URL(strURL+"?email="+strEmail+"&password="+strPassword+"&device="+strDevice+"&number="+strNumber+"&message="+strMessage);
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setRequestMethod("GET");

    	System.out.print(url);
    	System.out.print("Response Code: " + con.getResponseCode());

    	if (con.getResponseCode()==200) {
    		blnSuccess = true; 
    	}

    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    	String inputLine;
    	StringBuffer content = new StringBuffer();
    	
    	while ((inputLine = in.readLine()) != null) {
    		content.append(inputLine);
    		content.append("\n");
    	}
  
    	in.close();
    	con.disconnect();

		return blnSuccess;
    }
    
    public static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params) 
          throws UnsupportedEncodingException{
            StringBuilder result = new StringBuilder();
     
            for (Map.Entry<String, String> entry : params.entrySet()) {
              result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
              result.append("=");
              result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
              result.append("&");
            }
     
            String resultString = result.toString();
            return resultString.length() > 0
              ? resultString.substring(0, resultString.length() - 1)
              : resultString;
        }
    }
    
    public static String GetMessageIDFromResponse(String strResponse) {
    	System.out.println("strResponse: "+strResponse);
    	
    	System.out.println("Message ID From Response: " + FncGlobal.GetString("select left(right('"+strResponse+"', length('"+strResponse+"') - position('\"id\":' in '"+strResponse+"') - 5), \n" + 
				"position(',\"device_id' in right('"+strResponse+"', length('"+strResponse+"') - position('\"id\":' in '"+strResponse+"') - 5)) - 1) as ID"));
    	
    	return FncGlobal.GetString("select left(right('"+strResponse+"', length('"+strResponse+"') - position('\"id\":' in '"+strResponse+"') - 5), \n" + 
				"position(',\"device_id' in right('"+strResponse+"', length('"+strResponse+"') - position('\"id\":' in '"+strResponse+"') - 5)) - 1) as ID");
    }
    
    public static String GetResponse(String strResponse) {
    	System.out.println("Response: " + FncGlobal.GetString("select left(right('"+strResponse+"', length('"+strResponse+"') - position('\"status\":' in '"+strResponse+"') - 82), \n" + 
				"position(',\"status' in right('"+strResponse+"', length('"+strResponse+"') - position('\"id\":' in '"+strResponse+"') - 5)) - 4) as ID"));
    	
    	return FncGlobal.GetString("select left(right('"+strResponse+"', length('"+strResponse+"') - position('\"status\":' in '"+strResponse+"') - 82), \n" + 
				"position(',\"status' in right('"+strResponse+"', length('"+strResponse+"') - position('\"id\":' in '"+strResponse+"') - 5)) - 4) as ID");
    }
    
	public static String GetMessageStatusFromResponse(String strResponse) { //For checking
    	
		return FncGlobal.GetString("select left(right('"+strResponse+"', LENGTH('"+strResponse+"')-position('\"status\":' in '"+strResponse+"')-9), \n" +
    			"POSITION('\"send_at\":' in right('"+strResponse+"', LENGTH('"+strResponse+"')-position('\"status\":' in '"+strResponse+"')-9))-3)");	
    }
    
    public static void InsertIntoPhoneFollowup(String str_entity_id, String str_pbl_id, String str_seq_no, String str_unit_id, String str_proj_id, String str_contact_person, String str_txt_message, String str_contact_no) {
    	String strExec = "insert into rf_complaint_phone_followup (entity_id, pbl_id, seq_no, unit_id, proj_id, purpose_of_call_id, \n" + 
    			"contact_person, status_id, created_by, date_created, conversation, contact_no) \n" + 
    			"values ('"+str_entity_id+"', '"+str_pbl_id+"', "+str_seq_no+", '"+str_unit_id+"', '"+str_proj_id+"', '06', \n" + 
    			"'"+str_contact_person+"', 'A', '"+UserInfo.EmployeeCode+"', now(), '"+str_txt_message+"', '"+str_contact_no+"')";
    	pgUpdate dbExec = new pgUpdate(); 
    	dbExec.executeUpdate(strExec, false);
    	dbExec.commit(); 
    }
    
    public static void InsertIntoPhoneFollowup(String[] str_entity_id, String str_contact_person, String str_txt_message, String str_contact_no) {
    	String str_pbl_id = "";
    	String str_seq_no = "";
    	String str_unit_id = "";
    	String str_proj_id = "";

    	String strExec = "insert into (entity_id, pbl_id, seq_no, unit_id, proj_id, purpose_of_call_id, \n" + 
    			"contact_person, status_id, created_by, date_created, conversation, contact_no) \n" + 
    			"values ('"+str_entity_id+"', '"+str_pbl_id+"', '"+str_seq_no+"', '"+str_unit_id+"', '"+str_proj_id+"', '', \n" + 
    			"'"+str_contact_person+"', 'A', '"+UserInfo.EmployeeCode+"', now(), '"+str_txt_message+"', '"+str_contact_no+"')";
    	pgUpdate dbExec = new pgUpdate(); 
    	dbExec.executeUpdate(strExec, false);
    	dbExec.commit(); 
    }
    // 3
    public static String SendMessageToNumber_withResponse() throws Exception {
    	System.out.println("Dumaan dito sa SendMessageToNumber_withResponse");
    	strEmail = strEmail.replace("@", "%40"); 
    	strMessage = strMessage.replace(" ", "+"); 
    	
    	String strJson = "[{\"phone_number\": \""+strNumber+"\", \"message\": \""+strMessage+"\", \"device_id\": "+strDevice+"}]";//Comment by Erick
    	//String strJson = "[{\"phone_number\": \""+strNumber+"\", \"message\": \""+strMessage+"\"}]";// Added by Erick
    	

    	URL url = new URL(strURL);
    	//URL url = new URL(strURL+"?username="+itusername+"&password="+itpassword+"&phone=09054146828&message='"+strMessage+"'");
    	HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
    	con.setRequestMethod("POST");
    	con.setRequestProperty("Authorization", strToken);
    	con.setRequestProperty("Content-Type", "application/json");
    	con.setDoOutput(true);
    	
    	byte[] outputBytes = strJson.getBytes("UTF-8");
    	OutputStream os = con.getOutputStream();
    	os.write(outputBytes);
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    	String inputLine;
    	StringBuffer content = new StringBuffer();
    	
    	while ((inputLine = in.readLine()) != null) {
    		content.append(inputLine);
    		content.append("\n");
    	}
    	System.out.println("strNumber: "+strNumber);
    	System.out.println("strJson: "+strJson);
    	System.out.println("url: "+url);
    	System.out.println("con: "+con);
    	System.out.println("outputBytes: "+outputBytes);
    	System.out.println("in: "+os);
    	System.out.println("in: "+in);
    	System.out.println("inputLine: "+inputLine);
    	System.out.print("content: "+content);
    	
    	in.close();
    	con.disconnect();
    	
    	//System.out.println("");
    	//System.out.println("Content:" + content.toString() );
    	//System.out.print("GetMessageIDFromResponse: "+GetMessageIDFromResponse(content.toString()));
		return GetMessageIDFromResponse(content.toString()); 
    	
    }

	public static String SendMessageToNumber_v2() throws Exception {
		System.out.println("");
    	System.out.println("3rd step get Sending Message to CP #");
    	//String msg_status = "failed";
    	strEmail = strEmail.replace("@", "%40"); 
    	strMessage = strMessage.replace(" ", "+"); 
    	
    	String strJson = "[{\"phone_number\": \""+strNumber+"\", \"message\": \""+strMessage+"\", \"device_id\": "+strDevice+"}]";//Comment by Erick
    	//String strJson = "[{\"phone_number\": \""+strNumber+"\", \"message\": \""+strMessage+"\"}]";// Added by Erick
    	

    	//URL url = new URL(strURL);
    	URL url = new URL(strURL+"?username="+itusername+"&password="+itpassword+"&phone="+strNumber+"&message="+strMessage+"");
    	HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
    	con.setRequestMethod("POST");
    	con.setRequestProperty("Authorization", strToken);
    	con.setRequestProperty("Content-Type", "application/json");
    	con.setDoOutput(true);
    	
    	byte[] outputBytes = strJson.getBytes("UTF-8");
    	OutputStream os = con.getOutputStream();
    	os.write(outputBytes);
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    	String inputLine;
    	StringBuffer content = new StringBuffer();
    	
    	while ((inputLine = in.readLine()) != null) {
    		content.append(inputLine);
    		content.append("\n");
    	}
    	System.out.println("strNumber: "+strNumber);
    	System.out.println("strJson: "+strJson);
    	System.out.println("url: "+url);
    	System.out.println("con: "+con);
    	System.out.println("outputBytes: "+outputBytes);
    	System.out.println("in: "+os);
    	System.out.println("in: "+in);
    	System.out.println("inputLine: "+inputLine);
        System.out.print("content: "+content);
    	System.out.println("getResponseCode: "+con.getResponseCode());
    	
    	in.close();
    	con.disconnect();
    	
    	//System.out.println("");
    	//System.out.println("Content:" + content.toString() );
    	//System.out.print("GetMessageIDFromResponse: "+GetMessageIDFromResponse(content.toString()));//for checking
    	//System.out.println("GetResponse:"+GetResponse(content.toString()));
    	
    	System.out.printf("Value of response: %s%n", con.getResponseCode());
    	if(con.getResponseCode() == 200) {
    		msg_status = "sent";
    		
    	}else {
    		msg_status = "failed";
    	}
    	
    	System.out.println("Message is "+msg_status);
    	return msg_status;
    	
    }
    

    private void sendMessage2() {
		/*String postURL = "http://www.example.com/page.php";
		
		HttpPost post = new HttpPost(postURL);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", "10"));
		
		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(ent);
		
		HttpClient client = new DefaultHttpClient();
		HttpResponse responsePOST = client.execute(post);*/
        
    }
    

    public static StringBuffer FetchSingleMessage(String strMsgID) throws Exception {
    	System.out.println("Dumaan dito sa FetchSingleMessage");
    	StringBuffer content = null; 
    	strEmail = strEmail.replace("@", "%40"); 

    	URL url = new URL("https://smsgateway.me/api/v4/message/"+strMsgID);
    	//URL url = new URL(strURL+"?username="+itusername+"&password="+itpassword+"&phone="+strNumber+"&message="+strMessage+"");
    	
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setRequestMethod("GET");
    	con.setRequestProperty("Authorization", strToken);
    	con.setDoOutput(true);

    	System.out.println("");
    	System.out.println("strMsgID: "+strMsgID);
    	System.out.println("SMS Check response: " + con.getURL());
    	System.out.println("SMS Check response: " + con.getResponseMessage());
    	System.out.println("getResponseCode: "+con.getResponseCode());
    	
    	if (con.getResponseCode()==200) {
    	}

    	try {
        	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        	String inputLine;
        	content = new StringBuffer();
        	
        	while ((inputLine = in.readLine()) != null) {
        		content.append(inputLine);
        		content.append("\n");
        	}
        	
        	in.close();    		
    	} catch (IOException io) {
    		
    	}
    	
    	con.disconnect();
    	
    	System.out.println(" FetchSingleMessage value of content: " + content.toString());
    	
    	return content;  
    }
    // 2
    public static String ExecuteBash(String strExec) throws InterruptedException, IOException {
    	System.out.println("Dumaan dito sa ExecuteBash");
    	String strOutput = ""; 
        Process proc = null;
        
		try {
			proc = Runtime.getRuntime().exec(strExec);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        try {
			while((line = reader.readLine()) != null) {
				strOutput = strOutput.concat(line).concat("\n"); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return strOutput.toString(); 
	}
    
    public static Boolean SendThruUSB(String strNumber, String strMessage) throws InterruptedException, IOException {
		Boolean blnSent = true; 
		
		String[] strExec = new String[] {
				"adb shell input swipe 250 650 1000 650",
				"adb shell am force-stop com.android.mms",
				"adb shell am start -a android.intent.action.SENDTO -d sms:"+strNumber+" --es sms_body \""+strMessage+"\" --ez exit_on_sent true",  
				"adb shell input keyevent 22", 
				"adb shell input keyevent 66"
			}; 

		for (int z=0; z<strExec.length; z++) {
			System.out.println("");
			System.out.println("Code: "+strExec[z]);
			
			ExecuteBash(strExec[z]); 
			Thread.sleep(10000);
		}
		
		return blnSent;
	}
    // 1
	public static StringBuffer FetchSingleDevice(String strDeviceID) throws Exception {
    	System.out.println("Dumaan dito sa FetchSingleDevice");
    	StringBuffer content = null; 

    	URL url = new URL("https://smsgateway.me/api/v4/device/"+strDeviceID+"?email="+strEmail+"&password="+strPassword);
    	//URL url = new URL("https://smsgateway.me/api/v4/device/"+strDeviceID);
    	
    	System.out.println("");
    	//System.out.println("url: " + url);
    	
    	HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
    	con.setRequestProperty("Authorization", strToken);
    	con.setRequestMethod("GET");
    	
    	if (con.getResponseCode()==200) {

    	}

    	try {
        	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        	String inputLine;
        	content = new StringBuffer();
        	
        	while ((inputLine = in.readLine()) != null) {
        		content.append(inputLine);
        		content.append("\n");
        	}
        	
        	in.close();    		
    	} catch (IOException io) {
    		
    	}
    	
    	con.disconnect();
    	
    	return content;  
    }
}