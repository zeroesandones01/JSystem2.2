package Accounting.FixedAssets;

public class _AssetCard {

public static String getCustodian(){
	
	String strsql="select b.entity_name,a.emp_code,e.co_id,e.company_name, company_logo, company_alias\n" + 
			"from  em_employee a\n" + 
			"left join rf_entity b ON a.entity_id=b.entity_id\n" + 
			"left join mf_department as c on a.dept_code=c.dept_code\n" + 
			"left join mf_division as d on c.division_code=d.division_code \n" + 
			"left join mf_company e on a.co_id=e.co_id\n" + 
			"where  emp_status not in('c') ";// view inactive emp
			//"where  emp_status not in('I') ";//view active emp only
			//+ "and e.co_id::int="+co_id+"";
	System.out.println();
	System.out.println("getCustodian: "+strsql);
	return strsql;



}
}
