package Accounting.Commissions;

import java.util.Calendar;

import javax.swing.JOptionPane;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;

public class CreateCommissionSchedule {

	//variables
	public static String AgentCode 		= "";
	public static String PmtSchemeID 	= "";
	public static String AgentSalesType = "";  //internal(001) or external(002)
	public static String AgentSalesPosn = "";
	public static String AgentOverride 	= "";
	public static String NewAgentOverride= "";
	public static String CommSchemeID 	= "";
	public static Double AgentRate 		= 0.00;
	public static Double PrevRate 		= 0.00;
	public static Double NewRate 		= 0.00;
	public static Double GrossCommAmt	= 0.00;
	public static String EntityID 		= "";
	public static String ProjID 		= "";
	public static String PBL_ID 		= "";
	public static Integer SeqNo 		= 0;
	public static Double NSP			= 0.00;
	public static String CommType 		= "";
	public static String SalesType 		= ""; //S-socialized : L-low-cost
	public static String CoID 			= "";
	public static String HseModel		= "";
	public static String DteReserved	= "";	
	public static Boolean AgentDetailsMissing = false;	
	public static Integer NextRecID_hd 	= 0;
	public static Integer NextRecID_dl 	= 0;	
	public static String Phase_Code 	= "";
	public static Integer Comm_Frequency	= 1;


	//main class
	public static void createCommHeader(String agent_id, String pmt_scheme_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, 
			Double netSprice, String co_id, String date_rsvd, String hse_model, String ph_code, Integer comm_frequency){

		AgentCode 		= agent_id;
		PmtSchemeID 	= pmt_scheme_id;
		EntityID 		= entity_id;
		ProjID 			= proj_id;
		PBL_ID 			= pbl_id;
		SeqNo 			= seq_no;
		NSP 			= netSprice;
		CoID 			= co_id;
		HseModel 		= hse_model.trim();
		DteReserved 	= date_rsvd;
		NextRecID_hd	= getNextRecID_hd();
		Phase_Code		= ph_code;
		Comm_Frequency	= comm_frequency;

		System.out.printf("House Model : " + HseModel + "\n");		

		//determine whether Socialized or Low-Cost ; according to M'Rhea (02/03/2016) - All RH, regardless of NSP, shall be treated as socialized
		if (isRowHouse()==true){SalesType = "comm_rate_socialized";} //comm_rate_socialized - refers to the column in mf_sales_position
		else {SalesType = "comm_rate_lowcost";}  //comm_rate_lowcost - refers to the column in mf_sales_position

		Integer head_count = 1;		
		Integer last_head_count = 0;		

		pgUpdate db = new pgUpdate();	

		while (head_count<4) //4 (plus 1) - maximum number of heads entitled for commission (refer to table mf_sales_position)
		{				
			if (head_count==1) //for sales agent 
			{ 
				Object[] agent_dtl = getAgentDetails(AgentCode);

				if (AgentDetailsMissing==true) 
				{ 
					JOptionPane.showMessageDialog(null, 
							"Agent details does not exist yet.", "Error",  JOptionPane.ERROR_MESSAGE); 
					JOptionPane.showMessageDialog(null,"Commission Schedule not created.","Information",JOptionPane.INFORMATION_MESSAGE);
					break; 
				} 
				else 
				{
					AgentSalesType = (String) agent_dtl[0]; 
					AgentSalesPosn = (String) agent_dtl[1]; 
					AgentOverride  = (String) agent_dtl[2];

					System.out.printf("Sales Type : " + AgentSalesType + "\n");
					System.out.printf("Sales Position : " + AgentSalesPosn + "\n");
					System.out.printf("Override : " + AgentOverride + "\n");
				}									

				AgentRate		= Double.parseDouble(getAgentRate(SalesType));
				CommSchemeID	= getCommSchemeID();  //done once

				System.out.printf("getCommSchemeID:"+ getCommSchemeID()+ "\n");					
				System.out.printf("CommSchemeID:"+ CommSchemeID+ "\n");			

				PrevRate		= AgentRate;
				GrossCommAmt	= (AgentRate/100)*NSP;		

				insertComm_header(db, AgentCode, AgentRate, AgentSalesPosn, GrossCommAmt  );				
				insertComm_details_agent(db, AgentCode );
				last_head_count = 1;
			}	

			//for non-sales agent
			else if (head_count>1) 
			{ 

				if (AgentOverride.equals("")||AgentOverride.equals(null))
				{
					break;
				}
				else
				{
					Object[] agent_dtl = getAgentDetails(AgentOverride);	

					if (AgentDetailsMissing==true) 
					{ JOptionPane.showMessageDialog(null, 
							"Agent details does not exist yet.", "Error",  JOptionPane.ERROR_MESSAGE);  
					JOptionPane.showMessageDialog(null,"Commission Schedule not created.","Information",JOptionPane.INFORMATION_MESSAGE);
					break; } 
					else 
					{ 
						AgentSalesPosn	= (String) agent_dtl[1]; 
						AgentRate	= Double.parseDouble(getAgentRate(SalesType));
					}	

					GrossCommAmt	= (AgentRate - PrevRate) / 100*NSP;			
					PrevRate		= AgentRate ;					

					insertComm_header(db, AgentOverride, AgentRate, AgentSalesPosn, GrossCommAmt);
					insertComm_details_override(db, AgentOverride);

					AgentOverride	= (String) agent_dtl[2];	
					if (AgentOverride.equals("")||AgentOverride.equals(null)) { last_head_count = head_count;  break;}
					else { last_head_count = 1; }
				}
			}				

			head_count++;
		}	

		if (last_head_count>0) 
		{			
			db.commit();
			JOptionPane.showMessageDialog(null,"New Commission Schedule created.","Information",JOptionPane.INFORMATION_MESSAGE);				
		}	
	}	



	//insert commission schedule
	public static void insertComm_header(pgUpdate db, String agent, Double agent_rate, String agent_posn, Double gross_amt){//used

		String sqlDetail = 
			"insert into cm_schedule_hd \n" +
			"values ( \n" +
			""+NextRecID_hd+",   \n" +
			"'"+EntityID+"',   \n" +
			"'"+ProjID+"',   \n" +
			"'"+PBL_ID+"',   \n" +
			""+SeqNo+",   \n" +
			"'"+agent+"',   \n" +
			""+agent_rate+",   \n" +
			""+gross_amt+",   \n" +
			"'"+Calendar.getInstance().getTime()+"',   \n" +
			"'"+CommSchemeID+"',   \n" +
			"'"+agent_posn+"',   \n" +
			"'F',   \n" +
			"'T',   \n" +
			"false, " +
			"'A',   \n" +		
			""+Comm_Frequency+"   	 " +     //frequency : 1 - Original Schedule ; 2up - revision
			") " ;

		System.out.printf("SQL #1 - insertComm_header: " + sqlDetail + "\n");
		db.executeUpdate(sqlDetail, false);	
		NextRecID_hd++;
	}

	public static void insertComm_details_agent(pgUpdate db, String agent){//used

		Double CommDue		= 0.00;  //due commission for a particular comm-type
		Double CommDueTotal	= 0.00;  //sum of due commission for a particular agent/override
		Double CommAmount	= 0.00;  //fixed commission released on the first comm schedule
		Double CommDue_forReversal = 0.00;  //first commission released and deducted on the 2nd comm sched
		Integer sch_trms 	= getSchemeTerms();	//Get the commission scheme number of terms	
		Integer count 		= 1;		

		while (count<=sch_trms) {			

			Double comm_prcnt = 0.00;

			//Get the Commission Type (refer to table cm_scheme_dl)
			if (count==1 && sch_trms!=1 ) { CommType = "1"; } else if (count==1 && sch_trms==1 ) { CommType = "FC"; }
			else if (count==2 && sch_trms!=2 ) { CommType = "2"; } else if (count==2 && sch_trms==2 ) { CommType = "FC"; }
			else if (count==3 && sch_trms!=3 ) { CommType = "3"; } else if (count==3 && sch_trms==3 ) { CommType = "FC"; }			
			else if (count==4 && sch_trms!=4 ) { CommType = "4"; } else if (count==4 && sch_trms==4 ) { CommType = "FC"; }
			else if (count==5 && sch_trms!=5 ) { CommType = "5"; } else if (count==5 && sch_trms==5 ) { CommType = "FC"; }

			Object[] comm_dtl 	= getSchemeDetails(CommType);			
			String comm_type 	= "";
			try { comm_type 	= (String) comm_dtl[0];} catch (NullPointerException e) 
			{ JOptionPane.showMessageDialog(null,"<html><i><b>Commission Type<html></b></i>" + " does not exist.","Information",JOptionPane.INFORMATION_MESSAGE); break;  }

			//In case the Commission Amount & Percentage is null based on comm scheme details; then set Comm Amount & Percent to zero
			if (comm_dtl[1]==null||comm_dtl[1].equals(null)) { CommAmount = 0.00; } 
			else { CommAmount = Double.parseDouble(comm_dtl[1].toString()); }			
			if (comm_dtl[3]==null||comm_dtl[3].equals(null)) {comm_prcnt = 0.00;} 
			else {comm_prcnt = Double.parseDouble(comm_dtl[3].toString());}	

			System.out.printf("GrossCommAmt :" + GrossCommAmt);
			System.out.printf("CommDueTotal :" + CommDueTotal);
			
			//In case the Commission Amount & Percentage is "NOT" null based on comm scheme details 
			if (comm_dtl[1]!=null) 
			{ CommDue = CommAmount; CommDue_forReversal = CommAmount;  }
			else if (comm_dtl[1]==null&&comm_dtl[3]!=null&&CommDue_forReversal>0&&!CommType.equals("FC")) 
			{ CommDue = GrossCommAmt * comm_prcnt / 100 - CommDue_forReversal ;
			  CommDue_forReversal = CommDue_forReversal - CommDue_forReversal; }
			else if (comm_dtl[1]==null&&comm_dtl[3]!=null&&CommDue_forReversal<=0&&!CommType.equals("FC")) 
			{ CommDue = GrossCommAmt * comm_prcnt / 100 ; }
			else if (comm_dtl[1]==null&&comm_dtl[3]==null&&CommType.equals("FC")&&sch_trms>1) 
			{ CommDue = GrossCommAmt - CommDueTotal ; }		
			else if (sch_trms==1) 
			{ CommDue = GrossCommAmt  ; }	

			CommDueTotal = CommDueTotal + CommDue; //meant for FC comm_type		

			Integer daysfromor 	= Integer.parseInt(comm_dtl[2].toString());

			String sqlDetail = 
				"insert into cm_schedule_dl " +
				"values ( " +
				"'"+EntityID+"',   " +
				"'"+ProjID+"',   " +
				"'"+PBL_ID+"',   " +
				""+SeqNo+",   " +
				"'"+agent+"',   " +
				"'"+Calendar.getInstance().getTime()+"',   " +
				"'AA',   " +
				"'"+comm_type+"',   " +
				"null,   " +
				"'"+DteReserved+"'::date + "+daysfromor+" ,   " +
				""+Comm_Frequency+",   	 " +     //frequency : 1 - Original Schedule ; 2up - revision
				"null,   " +
				"0.00,   " +
				"0.00,   " +
				""+CommDue+",   " +
				"0.00,   " +
				"0.00,   " +
				"'0',   " +
				"0,   " +
				"'A',   " +
				"null,   " +
				"'"+CoID+"',   " +
				"'"+CoID+"',   " +
				"null,   " +
				"null,   " +
				"true,   " +
				"null,   " +
				"true,   " +				
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +				
				"true,   " +  //temporary
				"null,   " +
				"'"+getBuyerType()+"',   " +  
				"false   " +
				") " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	

			count++;			
		}
	}

	public static void insertComm_details_override(pgUpdate db, String agent){//used

		Double CommDue		= 0.00;  //due commission for a particular comm-type
		Double CommDueTotal	= 0.00;  //sum of due commission for a particular agent/override
		Double CommAmount	= 0.00;  //fixed commission released on the first comm schedule
		Double CommDue_forReversal = 0.00;  //first commission released and deducted on the 2nd comm sched
		Integer sch_trms 	= getSchemeTerms();		
		Integer count 		= 1;		

		while (count<=sch_trms) {			

			Double comm_prcnt = 0.00;

			//Get the Commission Type (refer to table cm_scheme_dl)
			if (count==1 && sch_trms!=1 ) { CommType = "1"; } else if (count==1 && sch_trms==1 ) { CommType = "FC"; }
			else if (count==2 && sch_trms!=2 ) { CommType = "2"; } else if (count==2 && sch_trms==2 ) { CommType = "FC"; }
			else if (count==3 && sch_trms!=3 ) { CommType = "3"; } else if (count==3 && sch_trms==3 ) { CommType = "FC"; }			
			else if (count==4 && sch_trms!=4 ) { CommType = "4"; } else if (count==4 && sch_trms==4 ) { CommType = "FC"; }
			else if (count==5 && sch_trms!=5 ) { CommType = "5"; } else if (count==5 && sch_trms==5 ) { CommType = "FC"; }

			Object[] comm_dtl 	= getSchemeDetails_override(CommType);			
			String comm_type 	= "";
			try { comm_type 	= (String) comm_dtl[0];} catch (NullPointerException e) 
			{ JOptionPane.showMessageDialog(null,"<html><i><b>Commission Type<html></b></i>" + " does not exist.","Information",JOptionPane.INFORMATION_MESSAGE); 
			break;  }

			if (comm_dtl[1]==null||comm_dtl[1].equals(null)) { CommAmount = 0.00; } 
			else { CommAmount = Double.parseDouble(comm_dtl[1].toString()); }

			if (comm_dtl[3]==null||comm_dtl[3].equals(null)) {comm_prcnt = 0.00;} 
			else {comm_prcnt = Double.parseDouble(comm_dtl[3].toString());}	

			if (comm_dtl[1]!=null) 
			{ CommDue = CommAmount; CommDue_forReversal = CommAmount;  }
			else if (comm_dtl[1]==null&&comm_dtl[3]!=null&&CommDue_forReversal>0&&!CommType.equals("FC")) 
			{ CommDue = GrossCommAmt * comm_prcnt / 100 - CommDue_forReversal ; CommDue_forReversal = CommDue_forReversal - CommDue; }
			else if (comm_dtl[1]==null&&comm_dtl[3]!=null&&CommDue_forReversal<=0&&!CommType.equals("FC")) 
			{ CommDue = GrossCommAmt * comm_prcnt / 100 ; }
			else if (comm_dtl[1]==null&&comm_dtl[3]==null&&CommType.equals("FC")&&sch_trms>1) 
			{ CommDue = GrossCommAmt - CommDueTotal ; }		
			else if (sch_trms==1) 
			{ CommDue = GrossCommAmt  ; }	

			CommDueTotal = CommDueTotal + CommDue; //meant for FC comm_type		

			Integer daysfromor 	= Integer.parseInt(comm_dtl[2].toString());

			String sqlDetail = 
				"insert into cm_schedule_dl " +
				"values (" +
				"'"+EntityID+"',   " +
				"'"+ProjID+"',   " +
				"'"+PBL_ID+"',   " +
				""+SeqNo+",   " +
				"'"+agent+"',   " +
				"'"+Calendar.getInstance().getTime()+"',   " +
				"'AA',   " +
				"'"+comm_type+"',   " +
				"null,   " +
				"'"+DteReserved+"'::date + "+daysfromor+" ,   " +
				""+Comm_Frequency+",   	 " +     //frequency : 1 - Original Schedule ; 2up - revision
				"null,   " +
				"0.00,   " +
				"0.00,   " +
				""+CommDue+",   " +
				"0.00,   " +
				"0.00,   " +
				"'0',   " +
				"0,   " +
				"'A',   " +
				"null,   " +
				"'"+CoID+"',   " +
				"'"+CoID+"',   " +
				"null,   " +
				"null,   " +
				"true,   " +
				"null,   " +
				"true,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"null,   " +
				"true,   " +  //temporary
				"null,   " +
				"'"+getBuyerType()+"',   " +  
				"false   " +
				") " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	
			count++;			
		}
	}



	//SQL 
	public static Object [] getAgentDetails(String agent) {//used

		String strSQL = 

			"select \r\n" + 
			"\r\n" + 
			"trim(a.salestype_id),\r\n" + 
			"trim(a.salespos_id),\r\n" + 
			"trim(a.override_id) \r\n" + 

			"from mf_sellingagent_info a  \n " +

			"where a.agent_id = '"+agent+"' \n" +
			"and a.status_id = 'A'  \n" +
			"and a.agent_id  = '"+agent+"'   \n\n";				

		System.out.printf("SQL - Agent Details: " + strSQL + "\n" );
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			AgentDetailsMissing = false;
			return db.getResult()[0];
		}else{
			AgentDetailsMissing = true;
			return null; 
		}

	}	

	public static String getCommSchemeID() {//used

		String strSQL = 			
			"select \n " +
			"( case when '"+AgentSalesType.trim()+"' = '001' then a.cm_scheme_id_internal else \n" +
			"  case when '"+AgentSalesType.trim()+"' = '002' then a.cm_scheme_id_external else '' end end )  \r\n" + 

			"from rf_marketing_scheme_detail a \n" +
			"left join rf_marketing_scheme_main b on a.rec_id = b.rec_id \r\n" +
			"where trim(a.pmt_scheme_id) = '"+PmtSchemeID.trim()+"' \n" +
			"and b.sub_proj_id = '"+Phase_Code+"' \n" +
			"and a.status_id = 'A' \n" +
			"and b.status_id = 'P'  \n\n\n";		

		System.out.printf("SQL - getCommSched: " + strSQL +"\n");
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}	

	public static String getPBL_phase() {//used

		String strSQL = 			
			"select phase from mf_unit_info where pbl_id = '"+PBL_ID+"'  \n\n\n ";		

		System.out.printf("SQL - getCommSched: " + strSQL + "\n");
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}	

	public static String getBuyerType() {//used

		String strSQL = 			
			"select trim(buyertype) from rf_sold_unit where pbl_id = '"+PBL_ID+"' and seq_no = "+SeqNo+"  ";		

		System.out.printf("SQL - getCommSched: " + strSQL+ "\n");
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	public static String getAgentRate(String bracket) {//used

		String agent_rate = "0.00";

		String SQL = 
			"select "+bracket+" \r\n" + 
			"from mf_sales_position \r\n" + 
			"where position_code = '"+AgentSalesPosn+"' " ;

		System.out.printf("getAgentRate : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if(db.getResult()[0][0].toString()==null||db.getResult()[0][0].equals("null")) {agent_rate = "0.00";}
			else {agent_rate = db.getResult()[0][0].toString(); }

		}else{
			agent_rate = "0.00";
		}

		System.out.printf("Agent Rate : " + agent_rate + "\n");

		return agent_rate;	

	}

	public static Integer getNextRecID_hd(){

		Integer x = 1;

		String SQL = 
			"select case when max(rec_id) is null then 0 else  max(rec_id) end + 1 from cm_schedule_hd    " ;


		System.out.printf("getNextRecID_hd: " + SQL + "\n\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = (Integer) db.getResult()[0][0];
		}else{
			x = 1;
		}

		return x;

	}


	public static Integer getSchemeTerms(){	

		Integer x = 1;

		String SQL = 
			"select terms from cm_scheme_hd where scheme_id = '"+CommSchemeID+"' and status_id = 'A'  " ;

		System.out.printf("getSchemeTerms: " + SQL + "\n\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = (Integer) db.getResult()[0][0];
		}else{
			x = 1;
		}		

		return x;
	}

	public static Object [] getSchemeDetails(String comm_type){	

		String SQL = 
			"select comm_type, comm_amt, daysnumfromor, comm_prcnt " +
			"from cm_scheme_dl " +
			"where scheme_id = '"+CommSchemeID+"' " +
			"and comm_type = '"+comm_type+"' " +
			"and status_id = 'A' " +
			"order by comm_type   " ;

		System.out.printf("getSchemeDetails: " + SQL + "\n\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		
	}

	public static Object [] getSchemeDetails_override(String comm_type){	

		String SQL = 
			"select comm_type, override_amt, daysnumfromor, override_prcnt " +
			"from cm_scheme_dl " +
			"where scheme_id = '"+CommSchemeID+"'" +
			"and comm_type = '"+comm_type+"' " +
			"and status_id = 'A' " +
			"order by comm_type   " ;

		System.out.printf("getSchemeDetails_override: " + SQL + "\n\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		
	}

	public static Boolean isRowHouse(){	

		Boolean x = false;

		String SQL = 
			"select is_rowhouse from mf_product_model where model_id = '"+HseModel+"'  " ;

		System.out.printf("isRowHouse: " + SQL + "\n\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			x = (Boolean) db.getResult()[0][0];
		}else{
			x = false;
		}		

		System.out.printf("is Row House? : " + x + "\n");

		return x;
	}


	//For Documents Monitoring- Tagging of OR ; Creation of Commission Schedule
	public static Object [] getUnitDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {//used

		String strSQL = 			
			"select " +
			"a.pbl_id, " +  	//0
			"b.co_id, " +		//1
			"(case when d.net_sprice is null then c.sellingprice else d.net_sprice end) as NSP, " +//2
			"c.sellingagent, " +//3
			"c.pmt_scheme_id, "+//4
			"c.datersvd, " +	//5
			"c.model_id, " +	//6
			"a.sub_proj_id  \r\n" + //7
			"from mf_unit_info a\r\n" + 
			"left join mf_project b on a.proj_id = b.proj_id  \r\n" + 
			"left join (select * from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"'" +
			"and seq_no = "+seq_no+" and status_id = 'A') c on a.pbl_id = c.pbl_id and a.proj_id = c.projcode\r\n" + 
			"left join rf_client_price_history d on d.entity_id = c.entity_id and d.proj_id = c.projcode and a.pbl_id = d.pbl_id and d.seq_no = c.seq_no and c.status_id = 'A' \r\n" + 
			"\r\n" + 
			"where a.proj_id = '"+proj_id+"' and a.pbl_id = '"+pbl_id+"'  \n\n ";	

		System.out.printf("SQL #1 getUnitDetails : %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	


	//For Client Request
	public static Boolean isClientRequestHasEffectOnCommission(String request_no){	

		Boolean x = false;

		//the SQL is designed such that it only displays request with effect on commission, if the concatenated request_IDs contain
		//at least one request that has an effect on commission, then it will trigger re-creation of commission schedule
		/*String SQL = 
			"select with_effect_commission from mf_request_type \n" +
			"where request_id in ( '"+request_id+"' ) and status_id = 'A' and with_effect_commission = true  " ;*/

		String SQL = "SELECT CASE WHEN EXISTS (SELECT * \n" + 
		"		  FROM mf_request_type \n" + 
		"		  where request_id in (select regexp_split_to_table(req_type_id,',') \n" + 
		"				       from req_rt_request_header \n" + 
		"				       where request_no = '"+request_no+"') \n" + 
		"		  and with_effect_commission) then true else false end;";

		System.out.printf("isClientRequestHasEffectOnCommission: " + SQL + "\n\n");
		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Display sql if with effect on commission", SQL);

		if(db.isNotNull()){
			x = (Boolean) db.getResult()[0][0];
		}else{
			x = false;
		}		

		System.out.printf("isClientRequestHasEffectOnCommission? : " + x + "\n");

		return x;
	}

	public static void cancelCommSchedule(String pbl_id, Integer seq_no, String entity_id) {

		//cancel Commission Header
		String sqlDetail = 
			"update cm_schedule_hd set status_id = 'I' \n" +
			"where trim(pbl_id) = '"+pbl_id+"' \n" +
			"and account_code = '"+entity_id+"'  \n"+
			"and seq_no = '"+seq_no+"'   \n\n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		pgUpdate db = new pgUpdate();	
		db.executeUpdate(sqlDetail, false);	

		//cancel Commission Detail
		String sqlDetail2 = 
			"update cm_schedule_dl set status = 'I' \n" +
			"where trim(pbl_id) = '"+pbl_id+"' \n" +
			"and account_code = '"+entity_id+"'  \n"+
			"and seq_no = '"+seq_no+"'   \n\n" ;

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);		

		db.commit();	
	}









}