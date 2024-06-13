/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_MRI_Qualifed_Enroll {
	
	
	String phase;
	String block;
	String lot;
	String client_name;
	BigDecimal loan_amt;
	BigDecimal amt_insured;
	String term;
	BigDecimal premium;
	Date date_insurance;
	String certificate_no;
	String invoice_no;
	String business_class;
	
	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public BigDecimal getLoan_amt() {
		return loan_amt;
	}

	public void setLoan_amt(BigDecimal loan_amt) {
		this.loan_amt = loan_amt;
	}

	public BigDecimal getAmt_insured() {
		return amt_insured;
	}

	public void setAmt_insured(BigDecimal amt_insured) {
		this.amt_insured = amt_insured;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public Date getDate_insurance() {
		return date_insurance;
	}

	public void setDate_insurance(Date date_insurance) {
		this.date_insurance = date_insurance;
	}

	public String getCertificate_no() {
		return certificate_no;
	}

	public void setCertificate_no(String certificate_no) {
		this.certificate_no = certificate_no;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getBusiness_class() {
		return business_class;
	}

	public void setBusiness_class(String business_class) {
		this.business_class = business_class;
	}

	public TD_MRI_Qualifed_Enroll(String phase, String block, String lot, String client_name, BigDecimal loan_amt, BigDecimal amt_insured, String term, BigDecimal premium, Date date_insurance, String certificate_no, String invoice_no, String business_class) {
		
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.client_name = client_name;
		this.loan_amt = loan_amt;
		this.amt_insured = amt_insured;
		this.term = term;
		this.premium = premium;
		this.date_insurance = date_insurance;
		this.certificate_no = certificate_no;
		this.invoice_no = invoice_no;
		this.business_class = business_class;
		
	}
}
