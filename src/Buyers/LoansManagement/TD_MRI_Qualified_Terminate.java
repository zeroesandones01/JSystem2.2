/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_MRI_Qualified_Terminate {

	String phase;
	String block;
	String lot;
	String client_name;
	BigDecimal amt_insured;
	BigDecimal balance;
	Integer term;
	BigDecimal premium;
	Date effectivity_date;
	String invoice_no;
	String certificate_no;
	Date date_from;
	Date date_to;
	String reason;
	
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

	public BigDecimal getAmt_insured() {
		return amt_insured;
	}

	public void setAmt_insured(BigDecimal amt_insured) {
		this.amt_insured = amt_insured;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public Date getEffectivity_date() {
		return effectivity_date;
	}

	public void setEffectivity_date(Date effectivity_date) {
		this.effectivity_date = effectivity_date;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getCertificate_no() {
		return certificate_no;
	}

	public void setCertificate_no(String certificate_no) {
		this.certificate_no = certificate_no;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public TD_MRI_Qualified_Terminate(String phase, String block, String lot, String client_name, BigDecimal amt_insured, BigDecimal balance, Integer term, BigDecimal premium, Date effectivity, String invoice_no, String certificate_no, Date date_from, Date date_to, String reason) {
		
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.client_name = client_name;
		this.amt_insured = amt_insured;
		this.balance = balance;
		this.term = term;
		this.premium = premium;
		this.effectivity_date = effectivity;
		this.invoice_no = invoice_no;
		this.certificate_no = certificate_no;
		this.date_from = date_from;
		this.date_to = date_to;
		this.reason = reason;
		
	}

}
