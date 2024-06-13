package Buyers.CreditandCollections;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Christian Paquibot
 */
public class TD_PN_Commitment {
	
	Date commit_date;
	
	public Date getCommit_date() {
		return commit_date;
	}


	public void setCommit_date(Date commit_date) {
		this.commit_date = commit_date;
	}


	BigDecimal amount;
	String to_update;
	public String getTo_update() {
		return to_update;
	}


	public void setTo_update(String to_update) {
		this.to_update = to_update;
	}


	Integer count;
	
	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	
	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public TD_PN_Commitment(Date commit_date, BigDecimal amount,String to_update,Integer count) {
		this.commit_date = commit_date;
		this.amount = amount;
		this.to_update = to_update;
		this.count = count;
	}

}
