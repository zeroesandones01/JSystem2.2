package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

public class TD_MRI_Issuance_Policy {
	
	String proj_alias;
	String unit_desc;
	String client_name;
	Date date_enrolled;
	Date date_approved;
	String policy_no;
	String reference_no;
	String invoice_no;
	BigDecimal amt_insured;
	BigDecimal premium;
	String stage;
	
	public String getProj_alias() {
		return proj_alias;
	}
	
	public void setProj_alias(String proj_alias) {
		this.proj_alias = proj_alias;
	}
	
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
	
	public Date getDate_enrolled() {
		return date_enrolled;
	}
	
	public void setDate_enrolled(Date date_enrolled) {
		this.date_enrolled = date_enrolled;
	}
	
	public Date getDate_approved() {
		return date_approved;
	}
	
	public void setDate_approved(Date date_approved) {
		this.date_approved = date_approved;
	}
	
	public String getPolicy_no() {
		return policy_no;
	}
	
	public void setPolicy_no(String policy_no) {
		this.policy_no = policy_no;
	}
	
	public String getReference_no() {
		return reference_no;
	}
	
	public void setReference_no(String reference_no) {
		this.reference_no = reference_no;
	}
	
	public String getInvoice_no() {
		return invoice_no;
	}
	
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	
	public BigDecimal getAmt_insured() {
		return amt_insured;
	}
	
	public void setAmt_insured(BigDecimal amt_insured) {
		this.amt_insured = amt_insured;
	}
	
	public BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public TD_MRI_Issuance_Policy(String proj_alias, String unit_desc, String client_name, Date date_enrolled, Date date_approved, String policy_no, String reference_no, String invoice_no, BigDecimal amt_insured, BigDecimal premium, String stage){
		
		this.proj_alias = proj_alias;
		this.unit_desc = unit_desc;
		this.client_name = client_name;
		this.date_enrolled = date_enrolled;
		this.date_approved = date_approved;
		this.policy_no = policy_no;
		this.reference_no = reference_no;
		this.invoice_no = invoice_no;
		this.amt_insured = amt_insured;
		this.premium = premium;
		this.stage = stage;
		
	}
}
