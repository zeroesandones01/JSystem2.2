/**
 * 
 */
package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_OtherRequest_PreviewSchedSearched {
	
	String part_desc;
	Date sched_date;
	BigDecimal amount;
	String proc_fee;
	BigDecimal mri;
	BigDecimal fire;
	BigDecimal interest;
	BigDecimal principal;
	BigDecimal vat;
	BigDecimal balance;
	BigDecimal int_rate;
	BigDecimal rpt_amt;
	
	public String getPart_desc() {
		return part_desc;
	}
	
	public void setPart_desc(String part_desc) {
		this.part_desc = part_desc;
	}

	public Date getSched_date() {
		return sched_date;
	}
	
	public void setSched_date(Date sched_date) {
		this.sched_date = sched_date;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getProc_fee() {
		return proc_fee;
	}
	
	public void setProc_fee(String proc_fee) {
		this.proc_fee = proc_fee;
	}
	
	public BigDecimal getMri() {
		return mri;
	}
	
	public void setMri(BigDecimal mri) {
		this.mri = mri;
	}
	
	public BigDecimal getFire() {
		return fire;
	}
	
	public void setFire(BigDecimal fire) {
		this.fire = fire;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}
	
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	
	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public BigDecimal getInt_rate() {
		return int_rate;
	}

	public void setInt_rate(BigDecimal int_rate) {
		this.int_rate = int_rate;
	}
	
	public BigDecimal getRpt_amt() {
		return rpt_amt;
	}

	public void setRpt_amt(BigDecimal rpt_amt) {
		this.rpt_amt = rpt_amt;
	}

	public TD_OtherRequest_PreviewSchedSearched(String part_desc, Date sched_date, BigDecimal amount, String proc_fee, BigDecimal rpt_amt,
			BigDecimal mri, BigDecimal fire, BigDecimal interest, BigDecimal principal, BigDecimal vat, BigDecimal balance, BigDecimal int_rate) {
		this.part_desc = part_desc;
		this.sched_date = sched_date;
		this.amount = amount;
		this.proc_fee = proc_fee;
		this.rpt_amt = rpt_amt;
		this.mri = mri;
		this.fire = fire;
		this.interest = interest;
		this.principal = principal;
		this.vat = vat;
		this.balance = balance;
		this.int_rate = int_rate;
	}
	
}
