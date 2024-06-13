package Buyers.LoansManagement;

import java.math.BigDecimal;

public class TD_PST_TCT_ForwardedToRD {

	String entity_name;
	String unit_desc;
	BigDecimal nsp;
	BigDecimal loan_amt;
	String house_model;
	String acct_status;
	
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
	
	public BigDecimal getNsp() {
		return nsp;
	}
	
	public void setNsp(BigDecimal nsp) {
		this.nsp = nsp;
	}
	
	public BigDecimal getLoan_amt() {
		return loan_amt;
	}
	
	public void setLoan_amt(BigDecimal loan_amt) {
		this.loan_amt = loan_amt;
	}
	
	public String getHouse_model() {
		return house_model;
	}
	
	public void setHouse_model(String house_model) {
		this.house_model = house_model;
	}
	
	public String getAcct_status() {
		return acct_status;
	}
	
	public void setAcct_status(String acct_status) {
		this.acct_status = acct_status;
	}
	
	
	public TD_PST_TCT_ForwardedToRD(String entity_name, String unit_desc, BigDecimal nsp, BigDecimal loan_amt, String house_model, String acct_status){
		
		this.entity_name = entity_name;
		this.unit_desc = unit_desc;
		this.nsp = nsp;
		this.loan_amt = loan_amt;
		this.house_model = house_model;
		this.acct_status = acct_status;
		
	}
}
