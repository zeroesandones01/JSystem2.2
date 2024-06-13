package Buyers.LoansManagement;

import java.math.BigDecimal;

public class TD_FI_Qualified_Enroll {

	String proj_alias;
	String phase;
	String block;
	String lot;
	String client_name;
	String house_model;
	BigDecimal model_cost;
	String percent_constructed;
	String term;
	
	public String getProj_alias() {
		return proj_alias;
	}
	
	public void setProj_alias(String proj_alias) {
		this.proj_alias = proj_alias;
	}
	
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
	public String getHouse_model() {
		return house_model;
	}
	
	public void setHouse_model(String house_model) {
		this.house_model = house_model;
	}
	
	public BigDecimal getModel_cost() {
		return model_cost;
	}
	
	public void setModel_cost(BigDecimal model_cost) {
		this.model_cost = model_cost;
	}
	
	public String getPercent_constructed() {
		return percent_constructed;
	}
	
	public void setPercent_constructed(String percent_constructed) {
		this.percent_constructed = percent_constructed;
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public TD_FI_Qualified_Enroll(String proj_alias, String phase, String block, String lot, String client_name, String house_model, BigDecimal model_cost, String percent_constructed, String term){
		this.proj_alias = proj_alias;
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.client_name = client_name;
		this.house_model = house_model;
		this.model_cost = model_cost;
		this.percent_constructed = percent_constructed;
		this.term = term;
	}
	
	
}
