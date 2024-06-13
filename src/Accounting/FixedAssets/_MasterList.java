package Accounting.FixedAssets;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class _MasterList {
	
	public static String getCategory(){
		return "select category_id as \"ID\", category_name as \"Category Name\" from tbl_category order by category_name";	
	}
	public static String getSupplier(){
		return "select to_char(supp_id, 'FM000000') as \"ID\", supp_name as \"Supplier Name\" from tbl_supplier order by supp_name";
	}
	public static String getDepartment(){
		return"select dept_code as \"ID\", dept_name as department_name from mf_department order by dept_name ";
	}
	public static String getDivision(){
		return"select division_code as \"ID\", division_name as \"div_name\"from mf_division  order by division_name";
	}
	public static void printMasterList(String category_id, String div_id, String dept_id, String supp_id){
		String strSQL = 
			"select * from (select \n"+
			"a.asset_code, \n"+
			"a.item_id, \n"+
			"b.category_name, \n"+
			"a.asset_name, \n"+
			"a.original_cost, \n"+
			"a.asset_ulm, \n"+
			"a.asset_mon_dep, \n"+
			"a.asset_no, \n"+
			"a.asset_serial, \n"+
			"a.asset_model, \n"+
			"a.asset_bk_val, \n"+
			"a.asset_ret_cost, \n"+
			"a.date_acquired, \n"+
			"a.from_dep, \n"+
			"a.to_dep, \n"+
			"a.date_retired, \n"+
			"a.owned, \n"+
			"a.insured, \n"+
			"a.insured_value, \n"+
			"i.remarks, \n"+
			"get_asset_status(substr(a.status, 1, 1)) as status, \n"+
			"case e.emp_fname when 'STOCKROOM' then 'STOCKROOM' else e.emp_lname || ', ' || e.emp_fname || ' ' || e.emp_mi end \n"+
			"as current_cust, \n"+
			"f.div_alias as division_alias, \n"+
			"g.dept_alias, \n"+
			"h.supp_name \n"+
			
			"from \n"+
			"tbl_asset a \n"+
			"left join tbl_item d \n"+
			"on a.item_id = d.item_id \n"+
			"left join tbl_category b \n"+
			"on b.category_id = d.category_id \n"+
			"left join tbl_user e \n"+
			"on a.current_cust = e.emp_id \n"+
			"left join rf_division f \n"+
			"on e.rf_div_id = f.div_id \n"+
			"left join rf_department g \n"+
			"on e.rf_dept_id = g.dept_id \n"+
			"left join tbl_supplier h \n"+
			"on a.supp_id = h.supp_id \n"+
			"left join tbl_asset_history i \n"+
			"on a.asset_no=i.asset_no and a.current_cust=i.current_cust \n" +
			
			"where substr(a.status, 1, 1) = 'A' \n"+
			"and a.asset_bk_val > 0.00 \n";

		if(!category_id.equals("All")){
			strSQL = strSQL + "and b.category_id='"+category_id+"' \n";
		}
		if(!div_id.equals("All")){
			strSQL = strSQL + "and e.rf_div_id='"+div_id+"' \n";
		}
		if(!dept_id.equals("All")){
			strSQL = strSQL + "and e.rf_dept_id='"+dept_id+"' \n";
		}
		if(!supp_id.equals("All")){
			strSQL = strSQL + "and a.supp_id='"+supp_id+"' \n";
		}

		strSQL = strSQL + "order by division_alias, dept_alias, current_cust, asset_no) a where current_cust is not null \n";

		/*try {
			functions.FncSelectRecords.select(strSQL);
			
			Map<String, Object> mapMasterlist = new HashMap<String, Object>();
			mapMasterlist.put("preparedBy", GlbVariables.emp_alias);

			JasperPrint print = JasperFillManager.fillReport("reports/rptMasterList.jasper", mapMasterlist, new JRResultSetDataSource(functions.FncSelectRecords.rs));
			
			if(print.getPages().size() > 0){
				JasperViewer printAssetSticker = new JasperViewer(print, false);
				printAssetSticker.setTitle("Masterlist");
				printAssetSticker.isMaximumSizeSet();
				printAssetSticker.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(GlbVariables.parentFrame, "No Reports Generated!", "Masterlist", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (JRException e) { }*/
	}
	
}
