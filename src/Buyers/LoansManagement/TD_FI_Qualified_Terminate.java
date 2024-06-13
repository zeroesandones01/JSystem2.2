/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_FI_Qualified_Terminate {

	String proj_alias;
	String phase;
	String block;
	String lot;
	String client_name;
	BigDecimal house_cost;
	String insurance_term;
	Date effectivity_date;
	String policy_no;
	Date date_from;
	Date date_to;
	String reason;

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

	public BigDecimal getHouse_cost() {
		return house_cost;
	}

	public void setHouse_cost(BigDecimal house_cost) {
		this.house_cost = house_cost;
	}

	public String getInsurance_term() {
		return insurance_term;
	}

	public void setInsurance_term(String insurance_term) {
		this.insurance_term = insurance_term;
	}

	public Date getEffectivity_date() {
		return effectivity_date;
	}

	public void setEffectivity_date(Date effectivity_date) {
		this.effectivity_date = effectivity_date;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public TD_FI_Qualified_Terminate(String proj_alias, String phase, String block, String lot, String client_name, BigDecimal house_cost,
			String insurance_term, Date effectivity_date, String policy_no, Date date_from, Date date_to, String reason) {
		
		this.proj_alias = proj_alias;
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.client_name = client_name;
		this.house_cost = house_cost;
		this.insurance_term = insurance_term;
		this.effectivity_date = effectivity_date;
		this.policy_no = policy_no;
		this.date_from = date_from;
		this.date_to = date_to;
		this.reason = reason;
		
	}

}
