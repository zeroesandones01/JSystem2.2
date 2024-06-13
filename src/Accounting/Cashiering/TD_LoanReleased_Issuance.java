package Accounting.Cashiering;

import java.math.BigDecimal;
import java.util.Date;

public class TD_LoanReleased_Issuance {
	
	String client_name;
	String unit; 
	BigDecimal loanable_amount; 
	BigDecimal mri_sri_doc_stamp; 
	BigDecimal fire; 
	BigDecimal processing_fee;
	BigDecimal appraisal_fee; 
	BigDecimal interim_mri; 
	BigDecimal retention_fee; 
	BigDecimal service_fee; 
	BigDecimal net_proceeds;
	BigDecimal total_deductions;
	Date loan_released;
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getLoanable_amount() {
		return loanable_amount;
	}
	public void setLoanable_amount(BigDecimal loanable_amount) {
		this.loanable_amount = loanable_amount;
	}
	public BigDecimal getMri_sri_doc_stamp() {
		return mri_sri_doc_stamp;
	}
	public void setMri_sri_doc_stamp(BigDecimal mri_sri_doc_stamp) {
		this.mri_sri_doc_stamp = mri_sri_doc_stamp;
	}
	public BigDecimal getFire() {
		return fire;
	}
	public void setFire(BigDecimal fire) {
		this.fire = fire;
	}
	public BigDecimal getProcessing_fee() {
		return processing_fee;
	}
	public void setProcessing_fee(BigDecimal processing_fee) {
		this.processing_fee = processing_fee;
	}
	public BigDecimal getAppraisal_fee() {
		return appraisal_fee;
	}
	public void setAppraisal_fee(BigDecimal appraisal_fee) {
		this.appraisal_fee = appraisal_fee;
	}
	public BigDecimal getInterim_mri() {
		return interim_mri;
	}
	public void setInterim_mri(BigDecimal interim_mri) {
		this.interim_mri = interim_mri;
	}
	public BigDecimal getRetention_fee() {
		return retention_fee;
	}
	public void setRetention_fee(BigDecimal retention_fee) {
		this.retention_fee = retention_fee;
	}
	public BigDecimal getService_fee() {
		return service_fee;
	}
	public void setService_fee(BigDecimal service_fee) {
		this.service_fee = service_fee;
	}
	public BigDecimal getNet_proceeds() {
		return net_proceeds;
	}
	public void setNet_proceeds(BigDecimal net_proceeds) {
		this.net_proceeds = net_proceeds;
	} 
	

	
	public Date getLoan_released() {
		return loan_released;
	}
	public void setLoan_released(Date loan_released) {
		this.loan_released = loan_released;
	}
	public BigDecimal getTotal_deductions() {
		return total_deductions;
	}
	public void setTotal_deductions(BigDecimal total_deductions) {
		this.total_deductions = total_deductions;
	}
	
	public TD_LoanReleased_Issuance(String client_name, String unit, BigDecimal loanable_amount, BigDecimal mri_sri_doc_stamp, BigDecimal fire, BigDecimal processing_fee, BigDecimal appraisal_fee, BigDecimal interim_mri, BigDecimal retention_fee, BigDecimal service_fee, BigDecimal net_proceeds, Date loan_released, BigDecimal total_deductions)
	{
		
		this.client_name = client_name; 
		this.unit = unit; 
		this.loanable_amount = loanable_amount; 
		this.mri_sri_doc_stamp = mri_sri_doc_stamp; 
		this.fire = fire; 
		this.processing_fee = processing_fee; 
		this.appraisal_fee = appraisal_fee; 
		this.interim_mri = interim_mri; 
		this.retention_fee = retention_fee; 
		this.service_fee = service_fee; 
		this.net_proceeds = net_proceeds;
		this.loan_released = loan_released;
		this.total_deductions = total_deductions;
	
	}

}
