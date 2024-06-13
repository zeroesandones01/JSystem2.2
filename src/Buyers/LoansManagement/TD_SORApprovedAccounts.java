/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_SORApprovedAccounts {

	String phase;
	String block;
	String lot;
	String client_name;
	BigDecimal availed_amt;
	BigDecimal sold_amt;
	BigDecimal ma_amount;
	Integer terms;
	Date ma_date;
	Date credit_date;

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

	public BigDecimal getAvailed_amt() {
		return availed_amt;
	}

	public void setAvailed_amt(BigDecimal availed_amt) {
		this.availed_amt = availed_amt;
	}

	public BigDecimal getSold_amt() {
		return sold_amt;
	}

	public void setSold_amt(BigDecimal sold_amt) {
		this.sold_amt = sold_amt;
	}

	public BigDecimal getMa_amount() {
		return ma_amount;
	}

	public void setMa_amount(BigDecimal ma_amount) {
		this.ma_amount = ma_amount;
	}

	public Integer getTerms() {
		return terms;
	}

	public void setTerms(Integer terms) {
		this.terms = terms;
	}

	public Date getMa_date() {
		return ma_date;
	}

	public void setMa_date(Date ma_date) {
		this.ma_date = ma_date;
	}

	public Date getCredit_date() {
		return credit_date;
	}

	public void setCredit_date(Date credit_date) {
		this.credit_date = credit_date;
	}
	
	public TD_SORApprovedAccounts(String phase, String block, String lot, String client_name, BigDecimal availed_amt, BigDecimal sold_amt,
			BigDecimal ma_amount, Integer terms, Date ma_date, Date credit_date) {
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.client_name = client_name;
		this.availed_amt = availed_amt;
		this.sold_amt = sold_amt;
		this.ma_amount = ma_amount;
		this.terms = terms;
		this.ma_date = ma_date;
		this.credit_date = credit_date;
	}

}
