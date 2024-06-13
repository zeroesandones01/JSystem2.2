package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.util.Date;

public class TD_WaivePenalty_PmtsMade {
	
	Date date_paid;
	String particular;
	BigDecimal amount;
	
	public Date getDate_paid() {
		return date_paid;
	}
	public void setDate_paid(Date date_paid) {
		this.date_paid = date_paid;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public TD_WaivePenalty_PmtsMade(Date date_paid, String particular, BigDecimal amount){
		this.date_paid = date_paid;
		this.particular = particular;
		this.amount = amount;
	}
	
}
