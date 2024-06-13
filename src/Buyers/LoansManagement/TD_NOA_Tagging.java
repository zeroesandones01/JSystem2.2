package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

public class TD_NOA_Tagging {
	
	String entity_name;
	String unit_desc;
	BigDecimal loan_amt;
	Integer loan_term;
	Date actual_date;
	
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
	
	public BigDecimal getLoan_amt() {
		return loan_amt;
	}
	
	public void setLoan_amt(BigDecimal loan_amt) {
		this.loan_amt = loan_amt;
	}
	
	public Integer getLoan_term() {
		return loan_term;
	}
	
	public void setLoan_term(Integer loan_term) {
		this.loan_term = loan_term;
	}
	
	public Date getActual_date() {
		return actual_date;
	}
	
	public void setActual_date(Date actual_date) {
		this.actual_date = actual_date;
	}
	
	public TD_NOA_Tagging(String entity_name, String unit_desc, BigDecimal loan_amt, Integer loan_term, Date actual_date){
		this.entity_name = entity_name;
		this.unit_desc = unit_desc;
		this.loan_amt = loan_amt;
		this.loan_term = loan_term;
		this.actual_date = actual_date;
		
	}
	
}
