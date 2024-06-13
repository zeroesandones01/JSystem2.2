package Buyers.LoansManagement;

import java.util.Date;

public class TD_PST_ForPostApproval {
	
	String entity_name;
	String unit_desc;
	Date tct_annotated;
	
	public String getEntity_name() {
		return entity_name;
	}
	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}
	
	public String getUnit_desc() {
		return unit_desc;
	}
	
	public void setUnit_desc(String unit_desc) {
		this.unit_desc = unit_desc;
	}

	public Date getTct_annotated() {
		return tct_annotated;
	}
	public void setTct_annotated(Date tct_annotated) {
		this.tct_annotated = tct_annotated;
	}
	
	public TD_PST_ForPostApproval(String entity_name, String unit_desc, Date tct_annotated){
		this.entity_name = entity_name;
		this.unit_desc = unit_desc;
		this.tct_annotated = tct_annotated;
	}
	
}
