package Buyers.CreditandCollections;

import java.math.BigDecimal;

public class TD_Past_Due_BuyBack {

	private String phase;
	private String block;
	private String lot;
	private String clientname;
	private BigDecimal obCompany;
	private BigDecimal obBank;
	private String dueDate;
	private Integer monthsPD;
	private String reason;
	private String tctNo;
	private String remarks;
	
	
	
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


	public BigDecimal getObCompany() {
		return obCompany;
	}

	public void setObCompany(BigDecimal obCompany) {
		this.obCompany = obCompany;
	}

	public BigDecimal getObBank() {
		return obBank;
	}

	public void setObBank(BigDecimal obBank) {
		this.obBank = obBank;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getMonthsPD() {
		return monthsPD;
	}

	public void setMonthsPD(Integer monthsPD) {
		this.monthsPD = monthsPD;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTctNo() {
		return tctNo;
	}

	public void setTctNo(String tctNo) {
		this.tctNo = tctNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	
	public TD_Past_Due_BuyBack(String phase,
	 String block,
	 String lot,
	 String clientname,
	 BigDecimal obCompany,
	 BigDecimal obBank,
	 String dueDate,
	 Integer monthsPD,
	 String reason,
	 String tctNo,
	 String remarks){
		
		
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.clientname = clientname;
		this.obCompany = obCompany;
		this.obBank = obBank;
		this.dueDate = dueDate;
		this.monthsPD = monthsPD;
		this.reason = reason;
		this.tctNo = tctNo;
		this.remarks = remarks;
		
		
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	
}
