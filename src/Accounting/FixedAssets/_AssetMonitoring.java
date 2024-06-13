package Accounting.FixedAssets;

import java.awt.Component;
import java.awt.Frame;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.formula.functions.Mode;
import org.apache.poi.ss.formula.ptg.DeletedArea3DPtg;
import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncReport;
import Functions.FncSelectRecords;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import components._JTableMain;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tablemodel.modelRepair;
import tablemodel.modelapproval;
import tablemodel.modelAssetMonitoring;
import tablemodel.modelMovement;;
public class _AssetMonitoring {
	
	public static modelAssetMonitoring modelAssets;
	public static JList rowheaderapproval;
	public  static _JTableMain tblapproval;
	private static String user_code;

	public _AssetMonitoring() {
		
	}

	/**
	 * No need to format columns such as timestamp, numeric into to_char.
	 * The table will automatically format it into a readable format.
	 * 
	 * @param model
	 * @param rowHeader
	 */
	
	
	
	public static void saveAsserForReplacement(String replacement_no, String requested_by, String reason, ArrayList<String> asset_no, ArrayList<String> asset_code, String asset_nos, String requested_name){
		pgUpdate db=new pgUpdate();
		for(int x=0; x<asset_no.size(); x++)
		{
		String strSQL = "INSERT INTO tbl_asset_replacement( \n" +
					"replacement_no, \n" +
					"requested_by, \n" +
					"date_requested, \n" +
					"reason, \n" +
					"asset_no, \n" +
					"asset_code, \n" +
					"tagged_by, \n" +
					"date_tagged, \n" +
					"status_id) \n" +
					"VALUES (\n" +
					""+replacement_no+", \n" +//replacement_no
					""+requested_by+", \n" +//requested_by
					"current_date, \n" +//date_requested
					"'"+reason+"', \n" +//reason
					""+asset_no.get(x)+", \n" +//asset_no
					"'"+asset_code.get(x)+"', \n" +//asset_code
					""+UserInfo.EmployeeCode+", \n" +//tagged_by
					"current_date, \n" +//date_tagged
					"'A') \n";//status_id
		db.executeUpdate(strSQL, true);
		}
		db.commit();
	}
	
	public static void disposeAsset(JTable table){
		pgUpdate db= new pgUpdate();
		for(int x=0; x<table.getRowCount(); x++)
		{
		if(new Boolean(table.getValueAt(x, 0).toString()))
			{
			String strSQL = "update tbl_asset set status='I', remarks='DISPOSED', date_retired=current_date where asset_no="+table.getValueAt(x, 1)+" " ;
			db.executeUpdate(strSQL, true);
				
			FncSystem.out("Display Sql", strSQL);
			String strSQL2 = "INSERT INTO tbl_asset_history( \n" +
						"asset_code, \n" +
						"prev_cust, \n" +
						"current_cust, \n" +
						"trans_date, \n" +
						"reason, \n" +
						"remarks, \n" +
						"status, \n" +
						"move_no, \n" +
						"asset_no, \n" +
						"trans_by) \n" +
						"VALUES (\n" +
						"'"+table.getValueAt(x, 2)+"', \n" +//asset_code
						"(select current_cust from tbl_asset where asset_no ="+table.getValueAt(x, 1)+"), \n" +//prev_cust
						"(select current_cust from tbl_asset where asset_no ="+table.getValueAt(x, 1)+"), \n" +//current_cust
						"current_date, \n" +//trans_date
						"'Disposed', \n" +//reason
						"'Disposed by "+UserInfo.FullName+"', \n" +//remarks
						"'I', \n" +//status
						"(select coalesce(max(move_no),0) + 1 from tbl_asset_history), \n" +//move_no
						""+table.getValueAt(x, 1)+", \n" +//asset_no
						"'" +UserInfo.EmployeeCode + "') \n";//trans_by
				
			FncSystem.out("Display Sql", strSQL2);
			db.executeUpdate(strSQL2, true);
			}
		}
		db.commit();
	}
	public static void retireAsset(JTable table){
		pgUpdate db=new pgUpdate();	
		for(int x=0; x<table.getRowCount(); x++)
		{
		if(new Boolean(table.getValueAt(x, 0).toString()))
			{
			String strSQL = "update tbl_asset set status='I', remarks='RETIRED', date_retired=current_date where asset_no="+table.getValueAt(x, 1)+" " ;
			db.executeUpdate(strSQL, true);
			String strSQL2 = "INSERT INTO tbl_asset_history( \n" +
						"asset_code, \n" +
						"prev_cust, \n" +
						"current_cust, \n" +
						"trans_date, \n" +
						"reason, \n" +
						"remarks, \n" +
						"status, \n" +
						"move_no, \n" +
						"asset_no, \n" +
						"trans_by) \n" +
						"VALUES (\n" +
						"'"+table.getValueAt(x, 2)+"', \n" +//asset_code
						"(select current_cust from tbl_asset where asset_no ="+table.getValueAt(x, 1)+"), \n" +//prev_cust
						"(select current_cust from tbl_asset where asset_no ="+table.getValueAt(x, 1)+"), \n" +//current_cust
						"current_date, \n" +//trans_date
						"'Retired', \n" +//reason
						"'Retired by "+UserInfo.FullName+"', \n" +//remarks
						"'A', \n" +//status
						"(select coalesce(max(move_no),0) + 1 from tbl_asset_history), \n" +//move_no
						""+table.getValueAt(x, 1)+", \n" +//asset_no
						"'" + UserInfo.EmployeeCode+ "') \n";//trans_by
			
			db.executeUpdate(strSQL2, true);
			}
		}
		db.commit();
	}
	
	public static void savefortransferapproval(modelAssetMonitoring model, Integer move_no, String prev_cust, String current_cust, String remarks, String new_cust_name,String dept_code){
		pgUpdate db= new pgUpdate();
		ArrayList<String> listAsset_no = new ArrayList<String>();
		ArrayList<Boolean> isSaved = new ArrayList<Boolean>();
		for(int x=0; x<model.getRowCount(); x++)
		{
			Boolean selected= (Boolean) model.getValueAt(x, 0);
			if(selected)
			{
				move_no+=1;
				String asset_no = ((String)model.getValueAt(x, 1)).trim();
				//String custodian_id=modelAssets.getValueAt(x, 5).toString();
				//String custodian_id =  Integer.valueOf(model.getValueAt(x, 5));
				String custodian_id =   (String) model.getValueAt(x, 5).toString();
				String strSQL = "INSERT INTO tbl_asset_history( \n" +
							"asset_code, " +
							"prev_cust, " +
							"current_cust, " +
							//"trans_date, " +
							"reason, " +
							"remarks, \n" +
							"status, " +
							"move_no, " +
							"asset_no, " +
							"trans_by,\n"+
							"dept_code) \n" +
							"VALUES (" +
							"(select asset_code from tbl_asset where asset_no='"+asset_no+"' ), " +//asset_code
							"'"+custodian_id+"', \n" +//prev_cust
							"'"+current_cust+"', \n" +//current_cust
							//"current_date, \n" +//trans_date
							"'TRANSFER', \n" +//reason
							"'"+remarks+"', \n" +//remarks
							"'A', \n" +//status
							"'"+move_no+"', \n" +//move_no
							"'"+asset_no+"', \n" +//asset_no
							"'"+UserInfo.EmployeeCode+"',\n"+
							"'"+dept_code+"') \n";//trans_by
				
				db.executeUpdate(strSQL, true);
				
				//String strSQL2 = "update tbl_asset set current_cust='"+current_cust+"'  where asset_no='"+asset_no+"'::int";
				//db.executeUpdate(strSQL2, false);
				listAsset_no.add(asset_no);
				isSaved.add(true);
				FncSystem.out("transfer asset", strSQL);
			}
		}
		db.commit();
	}
	
	
	
	public static void updatereplacement(Integer assettoreplace){
		
		pgUpdate db =new pgUpdate();
		
		String strSQL="update tbl_asset  set remarks= remarks || ' - '|| 'Retired', status='I', date_retired=current_date where status='A' and asset_no='"+assettoreplace+"'";
		
		db.executeUpdate(strSQL, false);
		System.out.printf("updatereplacement sql= "+ strSQL);
		db.commit();
	}
	
	public static void approvedbydepthead(DefaultTableModel model, String depthead_id){
	
		//ArrayList<Boolean> asset_foraprvl = new ArrayList<Boolean>();
		for(int x=0; x<model.getRowCount(); x++)
		{
		Boolean Selected= (Boolean) model.getValueAt(x, 0);
		depthead_id= UserInfo.EmployeeCode;
		 if(Selected)
			 {
			 Integer asset_transfer=  (Integer) model.getValueAt(x, 1);
			 Integer move_no= (Integer) model.getValueAt(x, 2);
			 pgUpdate db= new pgUpdate();
			 String strsql="update tbl_asset_history  set approvedby_dept_head  ='"+depthead_id+"', date_approvedby_dept_head='now()' where asset_no in ('"+asset_transfer+"')\n"+
							"and move_no='"+move_no+"'and status='A' and reason not in('DISPOSED')";
			 db.executeUpdate(strsql, false);
			 db.commit();	
			 FncSystem.out("asset4transfer", strsql);
			 System.out.println(asset_transfer);
			 }
		}
	}
	
	public static void approvedbyhad( DefaultTableModel model, String had_id, Integer current_cust, Integer asset_no){
		
		//ArrayList<Boolean> asset_foraprvl = new ArrayList<Boolean>();
		for (int x=0;x<model.getRowCount();x++)
		{
			Boolean Selected = (Boolean) model.getValueAt(x, 0);
			had_id=UserInfo.EmployeeCode;
			if(Selected)
			{
			Integer asset_transfer=  (Integer) model.getValueAt(x, 1);
			Integer move_no= (Integer) model.getValueAt(x, 2);
			Integer new_cust= (Integer) model.getValueAt(x,5);
			String strsql = "update tbl_asset_history  set approvedby_had ='"+had_id+"', \n"+
								"date_approvedby_had='now()', \n"+
								"trans_date='now()'\n"+
						        "where asset_no in ('"+asset_transfer+"')\n"+
								"and move_no='"+move_no+"'and status='A' and reason not in('DISPOSED') \n"+
								"and approvedby_dept_head is not null and date_approvedby_dept_head is not null";
			pgUpdate db= new pgUpdate();
			db.executeUpdate(strsql, false);
			
			String strSQL2 = "update tbl_asset set current_cust='"+new_cust+"'  where asset_no='"+asset_no+"'";
			db.executeUpdate(strSQL2, false);
			db.commit();
			FncSystem.out("update tbl_asset_history", strsql);
			FncSystem.out("update tbl_asset", strSQL2);
			}
		}
	}
	public static void displayforapprovalbydepthead(DefaultTableModel model, JList row,String custodian_id,String dept_code){
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		row.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strsql="select \n" + 
				"false,\n" + 
				"asset_no,\n" + 
				"move_no,\n" + 
				"asset_code,\n" + 
				"format('%s, %s %s.', c.last_name, c.first_name, left(c.middle_name, 1)) as prev_cust,\n" + 
				"format('%s, %s %s.', d.last_name, d.first_name, left(d.middle_name, 1)) as new_cust,\n" + 
				"trans_date,\n" + 
				"reason,\n" + 
				"a.remarks,\n" + 
				"a.approvedby_dept_head,\n" + 
				"date_approvedby_dept_head,\n" + 
				"approvedby_had, \n" + 
				"date_approvedby_had\n" + 
				"from tbl_asset_history a\n" + 
				"left join em_employee b on a.prev_cust=b.emp_code::int\n" + 
				"left join em_employee e on a.current_cust=e.emp_code::int\n" + 
				"left join rf_entity c on b.entity_id=c.entity_id\n" + 
				"left join rf_entity d on e.entity_id=d.entity_id\n" + 
				"where  status='A' and reason='TRANSFER' and a.dept_code='"+dept_code+"' and approvedby_dept_head is null";
		/*
		String strsql="select false,asset_no,move_no,asset_code,prev_cust,current_cust,trans_date,reason,remarks,approvedby_dept_head,date_approvedby_dept_head,approvedby_had, date_approvedby_had\n" +
				"from tbl_asset_history  where  status='A' and reason='TRANSFER' and dept_code='"+dept_code+"' and approvedby_dept_head is null";
		*/
		
		FncSystem.out("displayforapprovalbydepthead", strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);

		if(db.isNotNull())
		{
		for(int x=0; x < db.getRowCount(); x++)
			{
			//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
			model.addRow(db.getResult()[x]);
			//For every row added in model, the table header will also add the row number.
			listModel.addElement(model.getRowCount());
			}
		}
		//tblapproval.packAll();
	}
	public static void displayforapprovalbyhad(DefaultTableModel model, JList row,String custodian_id){
		FncTables.clearTable(model);
		try {
			DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
			row.setModel(listModel);//Setting of listModel into rowHeader.
			
			String strsql="select false,asset_no,move_no,asset_code,prev_cust,current_cust,trans_date,reason,remarks,approvedby_dept_head,date_approvedby_dept_head,approvedby_had, date_approvedby_had\n" + 
					"from tbl_asset_history  where  status='A'  and  reason='TRANSFER'  and approvedby_dept_head is not null and approvedby_had is null" ;
			
			FncSystem.out("displayforapprovalbyhad", strsql);
			pgSelect db = new pgSelect();
			db.select(strsql);

			if(db.isNotNull())
				{
				for(int x=0; x < db.getRowCount(); x++)
						{
						//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
						model.addRow(db.getResult()[x]);
						//For every row added in model, the table header will also add the row number.
						listModel.addElement(model.getRowCount());
						}
			}
			} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//tblapproval.packAll();
	}
	
	/*public static void savetransferforapproval(modelAssetMonitoring model, Integer new_cust, Integer move_no, String remarks){
		
		ArrayList<String> listassetno= new ArrayList<>();
		
		pgUpdate dbexec = new pgUpdate();
		dbexec.executeUpdate("delete from tmp_asset_for_transfer where added_by='"+UserInfo.EmployeeCode+"' ", false);
		dbexec.commit();
		
		for(int x=0; x<model.getRowCount();x++){
			if((Boolean) model.getValueAt(x, 0)){
				dbexec = new pgUpdate();
				dbexec.executeUpdate("insert into tmp_asset_for_transfer values ('"+model.getValueAt(x, 1)+"','"+model.getValueAt(x, 5)+"','"+new_cust+"','"+move_no+"','"+remarks+"','"+UserInfo.EmployeeCode+"')", false);
				dbexec.commit();
			}
		}
		
		System.out.println("");
		System.out.println("added_by:"+UserInfo.EmployeeCode);
	}*/
	
	public static boolean chckdepthead(){
		//user_code=UserInfo.EmployeeCode;
		String strsql="select dept_head_code from mf_department  where dept_head_code='"+UserInfo.EmployeeCode+"'";
		
		FncSystem.out("chkdepthead", strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);
		if(db.isNotNull())
		{
			return true;
		}else
		{
			return false;
		}
	} 
	
	public static void addAsset(
			
			String asset_code,
			String item_id,
			String asset_name,
			String asset_cost,
			Integer asset_ulm,
			String asset_no,
			String asset_serial, 
			String asset_model,
			String asset_bk_val,
			String date_acquired,
			String owned,
			String insured,
			String insured_value,
			String remarks,
			String supp_id,
			String current_cust, 
			String reference_no, 
			String original_cost, 
			String capitalized, 
			String comp_id,
			String custodian_name,
			int noofAsset,
			//Byte[] img,
			boolean toPrint,
			String co_id,
			//String user_name,
			//String asset_user_id,
			String item_found,
			String item_name,
			String brand,
			String description,
			String dept_code,
			Object remarks2,
			String location_id,
			Integer assettoreplace){
		pgUpdate db=new pgUpdate();
		
		ArrayList<Boolean> isSave= new ArrayList<Boolean>();
		int num = Integer.parseInt(asset_no)-1;
		
		ArrayList<Boolean> toPrints = new ArrayList<Boolean>();
		int first=1;
		
		
		String assetToPrint = "";
		for(int x=0; x < noofAsset; x++)
		{
			int asset_number = num+=1;
			first -= 1;
			assetToPrint = first==0 ? assetToPrint + asset_number:assetToPrint + ", "+asset_number;
			toPrints.add(true);
			
			
			System.out.println("asset_no" + asset_no);
			
			String strSQL =
					"INSERT INTO tbl_asset( \n" +
							"asset_code, \n" +
							"item_id, \n" +
							"asset_name, \n" +
							"asset_cost, \n" +
							"asset_ulm, \n" +
							"asset_mon_dep, \n" +
							"asset_no, \n" +
							"asset_serial, \n" +
							"asset_model, \n" +
							"asset_bk_val, \n" +
							"asset_ret_cost, \n" +
							"date_acquired, \n" +
							"from_dep, \n" +
							"to_dep, \n" +
							"date_retired, \n" +
							"owned, \n" +
							"insured, \n" +
							"insured_value, \n" +
							"remarks, \n" +
							"status, \n" +
							"supp_id, \n" +
							"current_cust, \n" +
							"added_by, \n" +
							"date_added, \n" +
							"reference_no, \n" +
							//"asset_pic,\n"+
							"original_cost, \n" +							
							"capitalized, \n" +
							//"edited_by, \n" +
							//"date_edited, \n" +
							"comp_id, \n" +
							"co_id, \n" +
							//"user_name,\n"+
							//"asset_user_id,\n"+
							"item_found, \n" +
							"item_name, \n"+
							"brand, \n"+
							"description, \n"+
							"dept_code, \n"+
							"remarks2, \n"+
							"loc_id,\n"+
							"replaced_assetno,\n"+
							"date_disposed)\n"+
							"VALUES (\n" +
							"'"+generateNewAssetCount(current_cust)+"', \n" +	//asset_code
							""+item_id+", \n" +									//item_id
							"'"+asset_name+"', \n" +							//asset_name
							""+asset_cost+", \n" +								//asset_cost
							""+asset_ulm+", \n" +								//asset_ulm
							"("+asset_cost+" -1)/"+asset_ulm+", \n" +			//asset_mon_dep
							""+asset_number+", \n" +							//asset_no
							"'"+asset_serial+"', \n" +							//asset_serial
							"'"+asset_model+"', \n" +							//asset_model
							""+asset_bk_val+", \n" +							//asset_bk_val
							"1.00, \n" +										//asset_ret_cost
							"'"+date_acquired+"', \n" +							//date_acquired
							"(select LAST_DAY('"+date_acquired+"'::date) + 1), \n" +//from_dep
							//"(select ( date '"+date_acquired+"' + INTERVAL '1 MONTH' )), \n" +					//from_dep
							"(select ( date '"+date_acquired+"' + INTERVAL '"+((asset_ulm) + 1)+" MONTH' )), \n" +	//to_dep
							"null, \n" +										//date_retired
							""+owned+", \n" +									//owned
							""+insured+", \n" +									//insured
							""+insured_value+", \n" +							//insured_value
							"'"+remarks+"', \n" +								//remarks
							"'A', \n" +											//status
							""+supp_id+", \n" +									//supp_id
							"'"+current_cust+"', \n" +							//current_cust
							"'"+UserInfo.EmployeeCode+"', \n" +					//added_by
							"now(), \n" +										//date_added
							"'"+reference_no+"', \n" +							//reference_no
							//"null, \n" +										//asset_pic	
							""+original_cost+", \n" +							//original_cost
							""+capitalized+", \n" +								//capitalized
							""+comp_id+", \n"+									//comp_id
							"'"+co_id+"', \n"+									//co_id
							//"'"+user_name+"', \n"+							//asset_user_name
							//"'"+asset_user_id+"'\n"+							//asset_user_id
							//"'"+item_found+"')";								//item_found
							""+item_found+",\n"+								//item_found
							"'"+item_name+"',\n"+								//item_name
							"'"+brand+"',\n"+									//item_brand
							"'"+description+"',\n"+								//Description
							"'"+dept_code+"', \n"+ 								//dept_code
							"'"+remarks2+"',\n"+ 								// remarks
							"'"+location_id+"',\n"+								//location id of the asset
							""+assettoreplace+",\n"+
							"null)";								//asset number replace by the new item
			
			db.executeUpdate(strSQL, true);

		}
		db.commit();
		
		assetToPrint = assetToPrint + "";
			
			String strSQL = "\n" + 
					"select \n" + 
					"a.asset_code,\n" + 
					"a.asset_no, \n" + 
					"a.asset_name, \n" + 
					"to_char(a.asset_no, 'FM00000000') as anum, \n" + 
					"c.entity_name as nme,\n" + 
					"current_date,\n" + 
					"lower(format('%s%s%s',left(c.first_name,1),left(c.middle_name,1),c.last_name))\n" + 
					"--lower(substr(c.first_name, left,1)||substr(c.middle_name, left,1)||c.last_name) as cuser \n" + 
					"from tbl_asset  a \n" + 
					"left join em_employee b on a.current_cust::varchar=b.emp_code\n" + 
					"left join rf_entity as c on b.entity_id=c.entity_id\n" + 
					"where a.asset_no='"+assetToPrint+"' and b.emp_code= '"+UserInfo.EmployeeCode+"'\n" + 
					"order by a.asset_no";
			FncSystem.out("assetToPrint", strSQL);
			System.out.println("");
			System.out.println("asset_no" + asset_no);
	}
	
	public static void updateAsset(
			
			//String user_name,
			//String asset_user_id,
			String item_id,
			String asset_name,
			String asset_cost,
			Integer asset_ulm,
			String asset_no,
			String asset_serial,
			String asset_model,
			String asset_bk_val,
			String date_acquired,
			String owned,
			String insured,
			String insured_value,
			String remarks,
			String supp_id,
			String reference_no, 
			String original_cost, 
			String capitalized, 
			String comp_id,
			String current_cust,
			String custodian_name,
			int noofAsset,
			byte[] img,
			Boolean toPrint,
			String item_found,
			String item_name,
			String brand,
			String description,
			String location){
		
		pgUpdate db= new pgUpdate();
		
		ArrayList<Boolean> isSaved = new ArrayList<Boolean>();
		int num = Integer.parseInt(asset_no) - 1;

		ArrayList<Boolean> toPrints = new ArrayList<Boolean> ();
		int first = 1;

		String assetToPrint = "";
		String status=(String) panelAssetInformation.cmbstatus.getSelectedItem();
		for(int x=0; x < noofAsset; x++)
		{
			int asset_number = num+=1;
			first -= 1;
			assetToPrint = first==0 ? assetToPrint + asset_number:assetToPrint + ", "+asset_number;
			toPrints.add(true);

			String strSQL = "UPDATE tbl_asset \n" +
					"SET \n" +
					//"user_name='"+user_name+"',\n"+						//username
					//"asset_user_id='"+asset_user_id+"',\n"+				//userid
					"item_id='"+item_id+"', \n" +							//itemid
					"asset_name='"+asset_name+"', \n" +						//assetname
					"asset_cost="+asset_cost+", \n" +						//assetcost
					"asset_ulm="+asset_ulm+", \n" +							//assetulm
					"asset_mon_dep=(("+asset_cost+" -1)/"+asset_ulm+"), \n" +//assetcost
					"asset_serial='"+asset_serial+"', \n" +					//assetserial
					"asset_model='"+asset_model+"', \n" +					//assetmodel
					"asset_bk_val="+asset_bk_val+", \n" +					//assetbkval
					"date_acquired='"+date_acquired+"', \n" +				//dateacquired
					"from_dep=(select ( date '"+date_acquired+"' + INTERVAL '1 MONTH' )), \n" +				//fromdep
					"to_dep=(select ( date '"+date_acquired+"' + INTERVAL '"+asset_ulm+" MONTH' )), \n" +	//todep
					"\"owned\"="+owned+", \n" +								//owned
					"insured="+insured+", \n" +								//insured
					"insured_value="+insured_value+", \n" +					//insuredvalue
					"remarks='"+remarks+"', \n" +							//remarks
					"supp_id="+supp_id+", \n" +								//suppid
					"reference_no='"+reference_no+"', \n" +					//referenceno
					"original_cost="+original_cost+", \n" +					//originalcost
					"capitalized="+capitalized+", \n" +						//capitalized
					"edited_by='"+UserInfo.EmployeeCode+"', \n" +			//editedby
					"date_edited='now()', \n" +								//dateedited
					"item_found="+item_found+",\n"+							//itemfound
					"item_name ='"+item_name+"',\n"+						//itemname
					"brand='"+brand+"',\n"+									//brand
					"description='"+description+"',\n"+						//description
					"status='"+status+"',\n"+								//status
					"loc_id='"+location+"'\n"+								//location
					"WHERE asset_no="+asset_no+" \n";						//assetno
			
			db.executeUpdate(strSQL, true);
			FncSystem.out("UpdateAsset", strSQL);
			
				//Functions.FncUDIRecords.commit();
				isSaved.add(true);

			//addImage(img, asset_number);
		}
		db.commit();
		
		assetToPrint = assetToPrint + "";

		String strSQL = "select \n" +
				"a.asset_code, \n" +
				"a.asset_no, \n" +
				"a.asset_name, \n" +
				"to_char(a.asset_no, 'FM00000000') as anum, \n" +
				"getempname(a.current_cust) as nme, \n" +
				"to_char(current_date, 'MM/DD/YY') as cdate, \n" +
				"lower(substr(b.emp_fname, 1,1)||substr(b.emp_mi, 1,1)||b.emp_lname) as cuser \n" +
				"from tbl_asset a \n" +
				"left join tbl_user b \n" +
				"on b.emp_id='"+UserInfo.EmployeeCode+"' \n" +
				"where a.asset_no in "+assetToPrint+ " \n" +
				"order by a.asset_no \n";
		
		FncSystem.out("assetToPrint", strSQL);
		
		

	}
	
	public static void displayIndividualAssets(DefaultTableModel model, JList rowHeader, String emp_code){
	
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		//Individual display of assets
		String strSQL = "select false, to_char(a.asset_no,'FM00000000'),\n" +
				"a.asset_code, \n" + 
				"a.asset_name, \n" + 
				"a.date_Acquired,\n" + 
				"lpad(a.current_cust::text, 6, '0'::text),\n" + 
				"get_employee_name(lpad(a.current_cust::text, 6, '0'::text)), \n" + 
				"a.reference_no, \n" + 
				"format('%s',left(a.status,1)) as status \n"+
				"from tbl_asset a \n" + 
				"where  a.current_cust='"+emp_code+"'\n" + 
				"and a.status not in ('I')\n" + 
				"order by asset_no ";
		
		FncSystem.out("displayIndividualAssets", strSQL);	
		 
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++)
			{
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				model.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void displayAssetForReplacement(String replacement_no, String asset_no, String reason, String requested_by){
		String strSQL = "select distinct on (a.asset_no) \n" +
				"to_char(a.asset_no,'FM00000000') as asset_no, \n" +
				"to_char(a.date_acquired, 'MM-dd-yyyy') as date_acquired, \n" +
				"b.company_id as company_id, \n" +
				"b.company_name as company_name, \n" +
				"a.current_cust as custodian_id, \n" +
				"get_employee_name(a.current_cust::text) as custodian_name, \n" +
				"d.category_id as category_id, \n" +
				"d.category_name as category_name, \n" +
				"to_char(a.item_id, 'FM000000') as item_id, \n" +
				"trim(split_part(asset_name, ',', 1)) as item_name, \n" +
				"trim(split_part(asset_name, ',', 2)) as brand, \n" +
				"trim(split_part(asset_name, ',', 3)) as description, \n" +
				"e.supp_id as supplier_id, \n" +
				"e.supp_name as supplier_name, \n" +
				"trim(a.asset_code) as asset_code, \n" +
				"get_asset_status(substr(a.status, 1, 1)) as status, \n" +
				"a.remarks as remarks, \n" +
				"to_char(a.asset_cost, 'FM999,999,999.00') as net_cost, \n" +
				"a.asset_ulm as ulm, \n" +
				"a.asset_serial as serial_no, \n" +
				"a.asset_model as model_no, \n" +
				"case when a.\"owned\" is true then 'true' else 'false' end as \"owned\", \n" +
				"case when a.capitalized is true then 'true' else 'false' end as capitalized, \n" +
				"case when a.insured is true then 'true' else 'false' end as insured, \n" +
				"a.insured_value as insured_value, \n" +
				"a.reference_no as reference_no, \n" +
				"to_char(round(a.asset_mon_dep,2)::numeric, 'FM999,999,990.00') as monthly_depreciation, \n" +
				"to_char(round(a.asset_bk_val,2)::numeric, 'FM999,999,990.00') as book_value, \n" +
				"to_char(a.asset_ret_cost, 'FM999,999,999.00') as retirement_cost, \n" +
				"to_char(a.from_dep, 'Mon dd, yyyy') as from_depreciation, \n" +
				"to_char(a.to_dep, 'Mon dd, yyyy') as to_depreciation, \n" +
				"a.asset_pic \n\n" +

			"from tbl_asset a \n\n" +

			"left join rf_company b \n" +
			"on b.company_id=to_char(a.comp_id, 'FM00') \n\n" +

			"left join tbl_item c \n" +
			"on c.item_name=trim(split_part(asset_name, ',', 1)) \n\n" +

			"left join tbl_category d \n" +
			"on d.category_id=c.category_id \n\n" +

			"left join tbl_supplier e \n" +
			"on e.supp_id=a.supp_id \n" +
			"where a.asset_no in ("+asset_no+") \n";
			pgSelect db=new pgSelect();
		

	}

	public static String generateNewAssetCode(String emp_id){
		pgSelect db = new pgSelect();
		
		String strSQL = "select format('%s-%s',  dv.division_alias,dp.dept_alias)\n" + 
				"from em_employee  as us\n" + 
				"left join rf_location as lc on us.loc_id=lc.loc_id::int\n" + 
				"left join mf_division as dv on us.div_code=dv.division_code\n" + 
				"left join mf_department as dp on us.dept_code=dp.dept_code\n" + 
				"where emp_code='"+emp_id+"'";                                                                                                                                                                                                               
		System.out.println("generateNewAssetCode= "+strSQL);
		db.select(strSQL);
		//db.setQuerySql(strSQL, false);
		return db.getResult()[0][0].toString();
	}
	
	public static String generateNewAssetCount(String emp_code){
		pgSelect db = new pgSelect();
		
		
		String strSQL = "select '"+generateNewAssetCode(emp_code)+"'||'-'||trim(to_char(assetCount::integer + 1, '0000')) as assetCount \n" +
				"from ( \n" +
			"select assetCount from (select coalesce(to_char(max(substr(asset_code, char_length(asset_code)-3, 4)::integer), 'FM0000'), '0000') as assetCount from tbl_asset \n" +
			"where asset_code ~* '"+generateNewAssetCode(emp_code)+"' \n" +
			"limit 1) a \n" +
			"union \n" +
			"select assetCount from (select coalesce(to_char(max(substr(asset_code, char_length(asset_code)-3, 4)::integer), 'FM0000'), '0000') as assetCount from tbl_asset_history \n" +
			"where asset_code ~* '"+generateNewAssetCode(emp_code)+"' \n" +
			"limit 1) a \n" +
			"order by assetCount desc limit 1 \n" +
			") a \n";
		System.out.println("generateNewAssetCount= "+strSQL);
		db.select(strSQL);
		return db.isNull() ? generateAlternateAssetCode(generateNewAssetCount(emp_code)):db.getResult()[0][0].toString();
	}
	
	public static String generateAlternateAssetCode(String asset_part){
			//SelectClass db = new SelectClass();
			pgSelect db= new pgSelect();
			String strsql="select '"+asset_part+"'||'-'||to_char('0'::integer + 1, 'FM0000') as assetCount ";
			//db.setQuerySql("select '"+asset_part+"'||'-'||to_char('0'::integer + 1, 'FM0000') as assetCount ", false);
			db.select(strsql);
			return db.Result[0][0].toString();
		
	}
	
	public static void displayMovementHistory(DefaultTableModel model, JList rowHeader , String asset_no){
		
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strSQL ="\n" + 
				"select  a.move_no,\n" + 
				"a.asset_code, \n" + 
				"format('%s, %s %s.', c.last_name, c.first_name, left(c.middle_name, 1)),\n" + 
				"format('%s, %s %s.', d.last_name, d.first_name, left(d.middle_name, 1)),\n" + 
				"a.trans_date,\n" + 
				"a.reason,\n" + 
				"a.remarks,\n" + 
				"coalesce((select loc_name from tbl_asset_location where loc_id = a.old_location), 'NOT CHANGED'),\n" + //added by jari cruz 7.8.2022
				"a.approvedby_dept_head, \n"+
				"a.date_approvedby_dept_head, \n"+
				"a.approvedby_had, \n"+
				"a.date_approvedby_had \n"+
				"from tbl_asset_history a  \n" + 
				"left join em_employee b on a.prev_cust=b.emp_code::int\n" + 
				"left join em_employee e on a.current_cust=e.emp_code::int\n" + 
				"--left join tbl_asset_history  on b.emp_code=a.current_cust::varchar\n" + 
				"left join rf_entity c on b.entity_id=c.entity_id\n" + 
				"left join rf_entity d on e.entity_id=d.entity_id \n" + 
				"where a.asset_no="+asset_no+"\n" + 
				"order by a.move_no desc, a.trans_date desc ";
		
		pgSelect db= new pgSelect(); 
		db.select(strSQL);
		
		FncSystem.out("displayMovementHistory", strSQL);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){

				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				model.addRow(db.getResult()[x]);

				//For every row added in model, the table header will also add the row number.
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void displayRepairHistory( String asset_no, DefaultTableModel model){
		pgSelect db= new pgSelect();

		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		String strSQL ="select \n" + 
				"to_char(a.date_trans, 'Mon dd, yyyy')as date,\n" + 
				"c.last_name||', '||c.first_name||' '||substr(c.middle_name, 1, 1) as trans_by,\n" + 
				"to_char(round(a.repair_cost,2), 'FM999,999,999.00') as repair_cost,\n" + 
				"to_char(round(a.old_bk_val,2), 'FM999,999,999.00') as old_bk_val,\n" + 
				"to_char(round(a.old_mon_dep,2), 'FM999,999,999.00') as old_mon_dep,\n" + 
				"to_char(round(a.old_cost,2), 'FM999,999,999.00') as old_cost,\n" + 
				"a.details\n" + 
				"from tbl_repair a\n" + 
				"left join em_employee b on a.trans_by=b.emp_code::int\n" + 
				"left join rf_entity c on b.entity_id=c.entity_id\n" + 
				"left join rf_system_user d on a.trans_by=d.emp_code::int\n" + 
				"where a.asset_no="+asset_no+"";
				
		
		db.select(strSQL);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){

				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				model.addRow(db.getResult()[x]);

				//For every row added in model, the table header will also add the row number.
				listModel.addElement(model.getRowCount());
			}
		}

		
	}
	
	public static String getCustodian2(){
		return "\n" + 
				"select a.emp_code, b.entity_name,c.dept_code,c.dept_name,d.division_code,d.division_name, dept_code\n" + 
				"from em_employee as a \n" + 
				"left join rf_entity as b\n" + 
				"	on a.entity_id=b.entity_id\n" + 
				"left join mf_department as c\n" + 
				"	on a.dept_code=c.dept_code\n" + 
				"left join mf_division as d\n" + 
				"	on c.division_code=d.division_code\n" + 
				"where not emp_status  ='I' order by dept_code";
	}
	
	public static String getNewReplacementNo(){
		pgSelect db = new pgSelect();
		String strSQL="select to_char(COALESCE(MAX(replacement_no),0) + 1, 'FM000000') from tbl_asset_replacement";
		//db.setQuerySql("select to_char(max(replacement_no) + 1, 'FM000000') from tbl_asset_replacement");
		db.select(strSQL);
		return db.Result[0][0].toString();
		
	}
	
	public static String getAssetNo(){
		pgSelect db = new pgSelect();
		String strSQL = "select to_char( COALESCE(MAX(asset_no),0) + 1 , 'FM00000000') FROM tbl_asset";
		db.select(strSQL);
		return db.Result[0][0].toString();
	}
	
	public static String getCompany(){
		//return "select co_id as \"Company ID\", company_name as \"Company Name\", company_alias as \"Company Alias\",company_logo as \"Company_Logo\" from mf_company where not co_id='03' order by co_id";//ADC,VDC,CDC ONLY
		return "select co_id as \"Company ID\", company_name as \"Company Name\", company_alias as \"Company Alias\",company_logo as \"Company_Logo\" from mf_company where not co_id='null' order by co_id";//ALL COMPANY
	}
	
	public static String getSupplier(){
		return "select to_char(supp_id, 'FM000000') as \"ID\", supp_name as \"Supplier Name\" from tbl_supplier order by supp_name";
	}
	
	public static String getItem(String category_id){
		return "select to_char(item_id, 'FM000000') as \"ID\", item_name as \"Item Name\" from tbl_item where category_id="+category_id+"";
	}
	
	public static String getCategory(){
		return "select category_id as \"ID\", category_name as \"Category Name\" from tbl_category order by category_name";
	}
	//public static String pnlinformationgetCustodian(){
		public static String pnlinformationgetCustodian(String co_id){
			
			String strsql="select a.emp_code,  b.entity_name,e.co_id,e.company_name,d.division_code,d.division_name, c.dept_code \n" + 
					"from  em_employee a\n" + 
					"left join rf_entity b ON a.entity_id=b.entity_id\n" + 
					"left join mf_department as c on a.dept_code=c.dept_code\n"+
					"left join mf_division as d on c.division_code=d.division_code \n"+
					"left join mf_company e on a.co_id=e.co_id\n"+
					"where e.co_id::int="+co_id+"\n";
					//"AND not emp_status='I' \n";//lookup active emp only
			
			return strsql;
			
		}
	public static String getCustodian(){
	//public static String getCustodian(String co_id){
		
		String strsql="select a.emp_code, b.entity_name,e.co_id,e.company_name,d.division_code,d.division_name \n" + 
				"from  em_employee a\n" + 
				"left join rf_entity b ON a.entity_id=b.entity_id\n" + 
				"left join mf_department as c on a.dept_code=c.dept_code\n"+
				"left join mf_division as d on c.division_code=d.division_code \n"+
				"left join mf_company e on a.co_id=e.co_id\n"+
				"where e.co_id  not in('03')";
				//"where e.co_id::int="+co_id+"\n"+
				//"AND not emp_status='I' \n";
		
		return strsql;
		
	}
	public static String getUser (){
	   String strsql="select a.emp_code, b.entity_name,e.co_id,e.company_name \n" + 
				"from  em_employee a \n" + 
				"left join rf_entity b ON a.entity_id=b.entity_id \n" + 
				"left join mf_department as c on a.dept_code=c.dept_code\n" + 
				"left join mf_division as d on c.division_code=d.division_code \n" + 
				"left join mf_company e on a.co_id=e.co_id\n"+
				"where not emp_status='I'";
				//"where  d.division_code='"+division_code+"'\n" + 
				//"AND not emp_status='I' ";
	   
		return strsql;
	}
	
	public static String getMoveNo(){
		pgSelect db = new pgSelect();
		
		String strSQL = "select trim(to_char(max(move_no) + 1, '0000000000')) from tbl_asset_history";
		db.select(strSQL);
		return db.getResult()[0][0].toString();
	}
}
