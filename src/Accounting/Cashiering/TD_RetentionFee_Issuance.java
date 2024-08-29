package Accounting.Cashiering;

import java.math.BigDecimal;
import java.util.Date;

public class TD_RetentionFee_Issuance {
	
	String client_name; 
	String project;
	String unit; 
	String particular;
	Date actual_date;
	BigDecimal ret_fee_amt;
	String receipt_no; 
	
	
	public BigDecimal getRet_fee_amt() {
		return ret_fee_amt;
	}

	public void setRet_fee_amt(BigDecimal ret_fee_amt) {
		this.ret_fee_amt = ret_fee_amt;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getParticular() {
		return particular;
	}

	public void setParticular(String particular) {
		this.particular = particular;
	}

	public Date getActual_date() {
		return actual_date;
	}

	public void setActual_date(Date actual_date) {
		this.actual_date = actual_date;
	}

	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public TD_RetentionFee_Issuance(String client_name, String project, String unit, String particular, Date actual_date, BigDecimal ret_fee_amt, String receipt_no) {
		
		this.client_name = client_name; 
		this.project = project; 
		this.unit = unit; 
		this.particular = particular; 
		this.actual_date = actual_date;
		this.ret_fee_amt = ret_fee_amt; 
		this.receipt_no = receipt_no; 
	}
}
