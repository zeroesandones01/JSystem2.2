package Projects.ConstructionManagement;

public class TD_QR_Generator {
	
	String unit_id;
	String description;
	String phase;
	String block;
	String lot;
	String proj_name;
	Integer column;
	
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
	public String getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public TD_QR_Generator(String unit_id, String description, String phase, String block, String lot, String proj_name, Integer column){
		this.unit_id = unit_id;
		this.description = description;
		this.column = column;
		this.phase = phase;
		this.block = block;
		this.lot = lot;
		this.proj_name = proj_name;
	}
}
