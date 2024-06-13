package Buyers.LoansManagement;

import java.util.Date;

public class TD_PST_WithEPEB {
	
	String entity_name;
	String unit_desc;
	String epeb_no;
	String epeb_date;
	
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
	public String getEpeb_no() {
		return epeb_no;
	}
	public void setEpeb_no(String epeb_no) {
		this.epeb_no = epeb_no;
	}
	
	public String getEpeb_date() {
		return epeb_date;
	}
	public void setEpeb_date(String epeb_date) {
		this.epeb_date = epeb_date;
	}
	
	public TD_PST_WithEPEB(String entity_name, String unit_desc, String epeb_no, String epeb_date){
		this.entity_name = entity_name;
		this.unit_desc = unit_desc;
		this.epeb_no = epeb_no;
		this.epeb_date = epeb_date;
	}
}
