package Buyers.LoansManagement;

import java.util.Date;

public class TD_PST_WithAnnotatedTCT {
	
	String entity_name;
	String unit_desc;
	Date tct_forwarded_date;
	
	
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

	public Date getTct_forwarded_date() {
		return tct_forwarded_date;
	}


	public void setTct_forwarded_date(Date tct_forwarded_date) {
		this.tct_forwarded_date = tct_forwarded_date;
	}

	public TD_PST_WithAnnotatedTCT(String entity_name, String unit_desc, Date tct_forwarded_date){
		this.entity_name = entity_name;
		this.unit_desc = unit_desc;
		this.tct_forwarded_date = tct_forwarded_date;
	}
}
