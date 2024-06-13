package Buyers.ClientServicing;

import java.math.BigDecimal;

public class TD_ROP_RefundAllocation {
	
	String part_desc;
	BigDecimal amount;
	
	public String getPart_desc() {
		return part_desc;
	}
	public void setPart_desc(String part_desc) {
		this.part_desc = part_desc;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public TD_ROP_RefundAllocation(String part_desc, BigDecimal amount){
		this.part_desc = part_desc;
		this.amount = amount;
	}
}
