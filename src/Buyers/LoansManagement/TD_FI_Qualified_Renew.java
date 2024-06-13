/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_FI_Qualified_Renew {

	String proj_alias;
	String phase;
	String block;
	String lot;
	String client_name;
	String house_model;
	BigDecimal model_cost;
	String percent_constructed;
	Integer term;
	String policy_no;
	Date date_from;
	Date date_to;
	
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

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}
	
	public String getPolicy_no() {
		return policy_no;
	}

	public void setPolicy_no(String policy_no) {
		this.policy_no = policy_no;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public TD_FI_Qualified_Renew(String proj_alias, String phase, String block, String lot ,String client_name, String house_model, BigDecimal model_cost, String percent_constructed, String policy_no, Date date_from, Date date_to) {
		
		this.proj_alias = proj_alias;
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.client_name = client_name;
		this.house_model = house_model;
		this.model_cost = model_cost;
		this.percent_constructed = percent_constructed;
		this.policy_no = policy_no;
		this.date_from = date_from;
		this.date_to = date_to;
		
		//this.term = term;
		
	}

}
