package Buyers.LoansManagement;

import java.util.Date;

public class TD_PST_LoanFiled {

	String unit_desc;
	String client_name;
	Date date_completed;
	String pct_constructed;
	Integer dp_term;
	String pmt_stage;
	public String getUnit_desc() {
		return unit_desc;
	}
	public void setUnit_desc(String unit_desc) {
		this.unit_desc = unit_desc;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public Date getDate_completed() {
		return date_completed;
	}
	public void setDate_completed(Date date_completed) {
		this.date_completed = date_completed;
	}
	public String getPct_constructed() {
		return pct_constructed;
	}
	public void setPct_constructed(String pct_constructed) {
		this.pct_constructed = pct_constructed;
	}
	public Integer getDp_term() {
		return dp_term;
	}
	public void setDp_term(Integer dp_term) {
		this.dp_term = dp_term;
	}
	public String getPmt_stage() {
		return pmt_stage;
	}
	public void setPmt_stage(String pmt_stage) {
		this.pmt_stage = pmt_stage;
	}
	
	public TD_PST_LoanFiled(String unit_desc, String client_name, Date date_completed, String pct_constructed, Integer dp_term, String pmt_stage){
		this.unit_desc = unit_desc;
		this.client_name = client_name;
		this.date_completed = date_completed;
		this.pct_constructed = pct_constructed;
		this.dp_term = dp_term;
		this.pmt_stage = pmt_stage;
	}
}
