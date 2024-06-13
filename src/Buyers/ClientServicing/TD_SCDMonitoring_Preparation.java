package Buyers.ClientServicing;

public class TD_SCDMonitoring_Preparation {

	String entity_id;
	String proj_id;
	String pbl_id;
	Integer seq_no;
	
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getProj_id() {
		return proj_id;
	}
	public void setProj_id(String proj_id) {
		this.proj_id = proj_id;
	}
	public String getPbl_id() {
		return pbl_id;
	}
	public void setPbl_id(String pbl_id) {
		this.pbl_id = pbl_id;
	}
	public Integer getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(Integer seq_no) {
		this.seq_no = seq_no;
	}
	
	public TD_SCDMonitoring_Preparation(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		
		this.entity_id = entity_id;
		this.proj_id = proj_id;
		this.pbl_id = pbl_id;
		this.seq_no = seq_no;
	
	}
	
	
}
