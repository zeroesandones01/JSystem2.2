package Buyers.LoansManagement;

import java.util.Date;

public class TD_PST_WithDOASigned {

	String unit_desc;
	String entity_name;
	Date noa_released_date;
	
	
	public String getUnit_desc() {
		return unit_desc;
	}
	
	public void setUnit_desc(String unit_desc) {
		this.unit_desc = unit_desc;
	}
	
	public String getEntity_name() {
		return entity_name;
	}
	
	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}
	
	public Date getNoa_released_date() {
		return noa_released_date;
	}
	
	public void setNoa_released_date(Date noa_released_date) {
		this.noa_released_date = noa_released_date;
	}
	
	public TD_PST_WithDOASigned(String unit_desc, String entity_name, Date noa_released_date){
		
		this.unit_desc = unit_desc;
		this.entity_name = entity_name;
		this.noa_released_date = noa_released_date;
		
	}
	
}
