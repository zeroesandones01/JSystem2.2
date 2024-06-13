package Buyers.CreditandCollections;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TD_Past_Due {

	private String ph;
	
	public String getPh() {
		return ph;
	}



	public void setPh(String ph) {
		this.ph = ph;
	}



	public String getBlk() {
		return blk;
	}



	public void setBlk(String blk) {
		this.blk = blk;
	}



	public String getLot() {
		return lot;
	}



	public void setLot(String lot) {
		this.lot = lot;
	}



	private String blk;
	
	private String lot;
	
	private String unit_pbl;
	
	public String getUnit_pbl() {
		return unit_pbl;
	}



	public void setUnit_pbl(String unit_pbl) {
		this.unit_pbl = unit_pbl;
	}



	public String getClient_name() {
		return client_name;
	}



	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}



	public String getBuyer_type() {
		return buyer_type;
	}



	public void setBuyer_type(String buyer_type) {
		this.buyer_type = buyer_type;
	}



	public String getAccount_status() {
		return account_status;
	}



	public void setAccount_status(String account_status) {
		this.account_status = account_status;
	}



	public String getHouse_status() {
		return house_status;
	}



	public void setHouse_status(String house_status) {
		this.house_status = house_status;
	}



	public String getHouse_model() {
		return house_model;
	}



	public void setHouse_model(String house_model) {
		this.house_model = house_model;
	}



	public String getSale_div() {
		return sale_div;
	}



	public void setSale_div(String sale_div) {
		this.sale_div = sale_div;
	}



	public String getContact_no() {
		return contact_no;
	}



	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}



	public Timestamp getLast_paid_date() {
		return last_paid_date;
	}



	public void setLast_paid_date(Timestamp last_paid_date) {
		this.last_paid_date = last_paid_date;
	}



	public Timestamp getDefault_date() {
		return default_date;
	}



	public void setDefault_date(Timestamp default_date) {
		this.default_date = default_date;
	}



	public Integer getMonths_pd() {
		return months_pd;
	}



	public void setMonths_pd(Integer months_pd) {
		this.months_pd = months_pd;
	}



	public String getPayment_stage() {
		return payment_stage;
	}



	public void setPayment_stage(String payment_stage) {
		this.payment_stage = payment_stage;
	}



	public BigDecimal getNsp() {
		return nsp;
	}



	public void setNsp(BigDecimal nsp) {
		this.nsp = nsp;
	}



	public String getPhase() {
		return phase;
	}



	public void setPhase(String phase) {
		this.phase = phase;
	}



	private String client_name;
	private String buyer_type;
	private String account_status;
	private String house_status;
	private String house_model;
	private String sale_div;
	private String contact_no;
	private Timestamp last_paid_date;
	private Timestamp default_date;
	private Integer months_pd;
	private String payment_stage;
	private BigDecimal nsp;
	private String phase;



	public TD_Past_Due(String ph,String blk, String lot,String unit_pbl, String client_name, String buyer_type, String account_status, String house_status, String house_model, String sale_div, String contact_no, Timestamp last_paid_date, Timestamp default_date, Integer months_pd,
	 String payment_stage,
	 BigDecimal nsp,
	 String phase) {
	
		
		 this.ph = ph;
		 this.blk = blk;
		 this.lot = lot;
		 this.unit_pbl = unit_pbl;
		 this.client_name = client_name;
		 this.buyer_type = buyer_type;
		 this.account_status = account_status;
		 this.house_status = house_status;
		 this.house_model = house_model;
		 this.sale_div = sale_div;
		 this.contact_no = contact_no;
		 this.last_paid_date = last_paid_date;
		 this.default_date = default_date;
		 this.months_pd = months_pd;
		 this.payment_stage = payment_stage;
		 this.nsp = nsp;
		 this.phase = phase;
		
		
		

	}

}
