/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;

/**
 * @author PC-113l
 */
public class TD_SORQualifiedAccounts {

	String proj_id;
	String unit_id;
	String client_name;
	BigDecimal nsp;
	BigDecimal sold_amt;
	Integer terms;
	BigDecimal int_rate;
	
	public String getProj_id() {
		return proj_id;
	}

	public void setProj_id(String proj_id) {
		this.proj_id = proj_id;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public BigDecimal getNsp() {
		return nsp;
	}

	public void setNsp(BigDecimal nsp) {
		this.nsp = nsp;
	}

	public BigDecimal getSold_amt() {
		return sold_amt;
	}

	public void setSold_amt(BigDecimal sold_amt) {
		this.sold_amt = sold_amt;
	}

	public Integer getTerms() {
		return terms;
	}

	public void setTerms(Integer terms) {
		this.terms = terms;
	}

	public BigDecimal getInt_rate() {
		return int_rate;
	}

	public void setInt_rate(BigDecimal int_rate) {
		this.int_rate = int_rate;
	}

	public TD_SORQualifiedAccounts(String proj_id, String unit_id, String client_name, BigDecimal nsp, 
			BigDecimal sold_amt, Integer terms, BigDecimal int_rate) {
		this.proj_id = proj_id;
		this.unit_id = unit_id;
		this.client_name = client_name;
		this.nsp = nsp;
		this.sold_amt = sold_amt;
		this.terms = terms;
		this.int_rate = int_rate;
	}

}
