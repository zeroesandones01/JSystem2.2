/**
 * 
 */
package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_RefundofPayment_PaymentsMade {
	
	Date datepaid;
	String particulars;
	BigDecimal amount;
	String receipt_no;
	
	public Date getDatepaid() {
		return datepaid;
	}

	public void setDatepaid(Date datepaid) {
		this.datepaid = datepaid;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public TD_RefundofPayment_PaymentsMade(Date datepaid, String particulars, BigDecimal amount, String receipt_no) {
		
		this.datepaid = datepaid;	
		this.particulars = particulars;
		this.amount = amount;
		this.receipt_no = receipt_no;
	
	}
}
