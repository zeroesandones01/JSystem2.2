package Buyers.ClientServicing;

import java.math.BigDecimal;

public class TD_WaivePenalty_PmtsWaived {
	
	BigDecimal amount;
	String particular;
	String remarks;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public TD_WaivePenalty_PmtsWaived(BigDecimal amount, String particular, String remarks){
		this.amount = amount;
		this.particular = particular;
		this.remarks = remarks;
	}

}
