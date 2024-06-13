package Buyers.LoansManagement;

import java.util.Date;

public class TD_PST_PQA_DocsCompletion {

	String proj_name;
	String unit_desc;
	String entity_name;
	Date or_date;
	String house_perc;
	Date cec_date;
	Date cte_date;
	Date itr_date;
	Date jc_date;
	Date afs_date;
	Date payslip;
	//Date msvs_date;
	Date esav_date;
	Date pagibig_or_date;
	Date bsl_date;
	Date hdmf_or_p24;
	Date scd_in;
	String business_class;
	Integer dp_term;
	String pmt_stage;
	String dp_pct;
	String pmt_status;
	Date ci_form;
	Date verified_docs;

	public String getProj_name() {
		return proj_name;
	}

	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}

	public String getUnit_desc() {
		return unit_desc;
	}

	public void setUnit_desc(String unit_desc) {
		this.unit_desc = unit_desc;
	}

	public String getEntity_name() {
		return entity_name;
	}

	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}

	public Date getOr_date() {
		return or_date;
	}

	public void setOr_date(Date or_date) {
		this.or_date = or_date;
	}

	public String getHouse_perc() {
		return house_perc;
	}

	public void setHouse_perc(String house_perc) {
		this.house_perc = house_perc;
	}

	public Date getCec_date() {
		return cec_date;
	}

	public void setCec_date(Date cec_date) {
		this.cec_date = cec_date;
	}

	public Date getCte_date() {
		return cte_date;
	}

	public void setCte_date(Date cte_date) {
		this.cte_date = cte_date;
	}

	public Date getItr_date() {
		return itr_date;
	}

	public void setItr_date(Date itr_date) {
		this.itr_date = itr_date;
	}

	
	/*
	 * public Date getMsvs_date() { return msvs_date; }
	 * 
	 * public void setMsvs_date(Date msvs_date) { this.msvs_date = msvs_date; }
	 */
	 

	public Date getEsav_date() {
		return esav_date;
	}

	public void setEsav_date(Date esav_date) {
		this.esav_date = esav_date;
	}

	public Date getPagibig_or_date() {
		return pagibig_or_date;
	}

	public void setPagibig_or_date(Date pagibig_or_date) {
		this.pagibig_or_date = pagibig_or_date;
	}

	public Date getBsl_date() {
		return bsl_date;
	}

	public void setBsl_date(Date bsl_date) {
		this.bsl_date = bsl_date;
	}

	public Date getHdmf_or_p24() {
		return hdmf_or_p24;
	}

	public void setHdmf_or_p24(Date hdmf_or_p24) {
		this.hdmf_or_p24 = hdmf_or_p24;
	}

	public Date getScd_in() {
		return scd_in;
	}

	public void setScd_in(Date scd_in) {
		this.scd_in = scd_in;
	}

	public String getBusiness_class() {
		return business_class;
	}

	public void setBusiness_class(String business_class) {
		this.business_class = business_class;
	}

	public Integer getDp_term() {
		return dp_term;
	}

	public void setDp_term(Integer dp_term) {
		this.dp_term = dp_term;
	}

	public String getPmt_stage() {
		return pmt_stage;
	}

	public void setPmt_stage(String pmt_stage) {
		this.pmt_stage = pmt_stage;
	}

	public String getPmt_status() {
		return pmt_status;
	}

	public void setPmt_status(String pmt_status) {
		this.pmt_status = pmt_status;
	}

	public Date getJc_date() {
		return jc_date;
	}

	public void setJc_date(Date jc_date) {
		this.jc_date = jc_date;
	}

	public Date getAfs_date() {
		return afs_date;
	}

	public void setAfs_date(Date afs_date) {
		this.afs_date = afs_date;
	}

	public Date getPayslip() {
		return payslip;
	}

	public void setPayslip(Date payslip) {
		this.payslip = payslip;
	}

	public String getDp_pct() {
		return dp_pct;
	}

	public void setDp_pct(String dp_pct) {
		this.dp_pct = dp_pct;
	}

	public Date getCi_form() {
		return ci_form;
	}

	public void setCi_form(Date ci_form) {
		this.ci_form = ci_form;
	}

	public Date getVerified_cec() {
		return verified_docs;
	}

	public void setVerified_cec(Date verified_cec) {
		this.verified_docs = verified_cec;
	}

	public Date getVerified_docs() {
		return verified_docs;
	}

	public void setVerified_docs(Date verified_docs) {
		this.verified_docs = verified_docs;
	}

	public TD_PST_PQA_DocsCompletion(String proj_name, String unit_desc, String entity_name, Date or_date,
			String house_perc, Date scd_in, String business_class, Integer dp_term, String dp_pct, String pmt_status,
			Date cec_date, Date cte_date, Date itr_date, Date jc_date, Date afs_date, Date payslip,
			Date esav_date, Date pagibig_or_date, Date bsl_date, Date hdmf_or_p24, Date ci_form, Date verified_docs) {

		this.proj_name = proj_name;
		this.unit_desc = unit_desc;
		this.entity_name = entity_name;
		this.or_date = or_date;
		this.house_perc = house_perc;
		this.scd_in = scd_in;
		this.business_class = business_class;
		this.dp_term = dp_term;
		this.dp_pct = dp_pct;
		this.pmt_status = pmt_status;
		this.cec_date = cec_date;
		this.cte_date = cte_date;
		this.itr_date = itr_date;
		this.jc_date = jc_date;
		this.afs_date = afs_date;
		this.payslip = payslip;
		//this.msvs_date = msvs_date;
		this.esav_date = esav_date;
		this.pagibig_or_date = pagibig_or_date;
		this.bsl_date = bsl_date;
		this.hdmf_or_p24 = hdmf_or_p24;
		this.ci_form = ci_form;
		this.verified_docs = verified_docs;

	}

}
