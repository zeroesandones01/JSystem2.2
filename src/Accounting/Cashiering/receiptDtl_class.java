package Accounting.Cashiering;


public class receiptDtl_class {

	private String pymnt_part;
	public String getPymnt_part() {
		return pymnt_part;
	}



	public void setPymnt_part(String pymnt_part) {
		this.pymnt_part = pymnt_part;
	}



	private Double amount;
	
	

	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public receiptDtl_class(String pymnt_part, Double amount) {
				
		this.pymnt_part = pymnt_part;
		this.amount = amount;	
		

	}

}
