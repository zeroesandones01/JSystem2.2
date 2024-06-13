/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author John Lester Fatallo
 */
public class TD_Fire_Issuance_Policy {

	String proj_alias;
	String policy_no;
	String batch_no;
	String unit_desc;
	String client_name;
	String house_model;
	String pmt_stage;
	BigDecimal amt_insured;
	Date date_from;
	Date date_to;
	BigDecimal fire_lightning;
	BigDecimal full_earthquake;
	BigDecimal typhoon;
	BigDecimal flood;
	BigDecimal ext_cover;
	BigDecimal rsmd;
	BigDecimal premium_sub_total;
	BigDecimal doc_stamps;
	BigDecimal evat;
	BigDecimal fst;
	BigDecimal lgt;
	BigDecimal premium;
	
	
	public String getPolicy_no() {
		return policy_no;
	}


	public void setPolicy_no(String policy_no) {
		this.policy_no = policy_no;
	}


	public String getBatch_no() {
		return batch_no;
	}


	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}


	public String getUnit_desc() {
		return unit_desc;
	}


	public void setUnit_desc(String unit_desc) {
		this.unit_desc = unit_desc;
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


	public String getPmt_stage() {
		return pmt_stage;
	}


	public void setPmt_stage(String pmt_stage) {
		this.pmt_stage = pmt_stage;
	}


	public BigDecimal getAmt_insured() {
		return amt_insured;
	}


	public void setAmt_insured(BigDecimal amt_insured) {
		this.amt_insured = amt_insured;
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


	public BigDecimal getFire_lightning() {
		return fire_lightning;
	}


	public void setFire_lightning(BigDecimal fire_lightning) {
		this.fire_lightning = fire_lightning;
	}


	public BigDecimal getFull_earthquake() {
		return full_earthquake;
	}


	public void setFull_earthquake(BigDecimal full_earthquake) {
		this.full_earthquake = full_earthquake;
	}


	public BigDecimal getTyphoon() {
		return typhoon;
	}


	public void setTyphoon(BigDecimal typhoon) {
		this.typhoon = typhoon;
	}


	public BigDecimal getFlood() {
		return flood;
	}


	public void setFlood(BigDecimal flood) {
		this.flood = flood;
	}


	public BigDecimal getExt_cover() {
		return ext_cover;
	}


	public void setExt_cover(BigDecimal ext_cover) {
		this.ext_cover = ext_cover;
	}


	public BigDecimal getRsmd() {
		return rsmd;
	}


	public void setRsmd(BigDecimal rsmd) {
		this.rsmd = rsmd;
	}


	public BigDecimal getPremium_sub_total() {
		return premium_sub_total;
	}


	public void setPremium_sub_total(BigDecimal premium_sub_total) {
		this.premium_sub_total = premium_sub_total;
	}


	public BigDecimal getDoc_stamps() {
		return doc_stamps;
	}


	public void setDoc_stamps(BigDecimal doc_stamps) {
		this.doc_stamps = doc_stamps;
	}


	public BigDecimal getEvat() {
		return evat;
	}


	public void setEvat(BigDecimal evat) {
		this.evat = evat;
	}


	public BigDecimal getFst() {
		return fst;
	}


	public void setFst(BigDecimal fst) {
		this.fst = fst;
	}


	public BigDecimal getLgt() {
		return lgt;
	}


	public void setLgt(BigDecimal lgt) {
		this.lgt = lgt;
	}


	public BigDecimal getPremium() {
		return premium;
	}


	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public String getProj_alias() {
		return proj_alias;
	}


	public void setProj_alias(String proj_alias) {
		this.proj_alias = proj_alias;
	}


	public TD_Fire_Issuance_Policy(String proj_alias, String policy_no, String batch_no, String unit_desc, String client_name, String house_model, 
			String pmt_stage, BigDecimal amt_insured, Date date_from, Date date_to ,BigDecimal fire_lightning, BigDecimal full_earthquake, 
			BigDecimal typhoon, BigDecimal flood, BigDecimal ext_cover, BigDecimal rsmd, BigDecimal premium_sub_total, BigDecimal doc_stamps, 
			BigDecimal evat, BigDecimal fst, BigDecimal lgt, BigDecimal premium) {
		
		this.proj_alias = proj_alias;
		this.policy_no = policy_no;
		this.batch_no = batch_no;
		this.unit_desc = unit_desc;
		this.client_name = client_name;
		this.house_model = house_model;
		this.pmt_stage = pmt_stage;
		this.amt_insured = amt_insured;
		this.date_from = date_from;
		this.date_to = date_to;
		this.fire_lightning = fire_lightning;
		this.full_earthquake = full_earthquake;
		this.typhoon = typhoon;
		this.flood = flood;
		this.ext_cover = ext_cover;
		this.rsmd = rsmd;
		this.premium_sub_total = premium_sub_total;
		this.doc_stamps = doc_stamps;
		this.evat = evat;
		this.fst = fst;
		this.lgt = lgt;
		this.premium = premium;
		
	}

}
