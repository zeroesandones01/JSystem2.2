package Projects.ConstructionManagement;

public class noticetoconstruct {

	String client_name, phase, block, lot;
	
	public String getClient_name() {
		return client_name;
	}

	public void getClient_name(String client_name, String phase, String block, String lot) {
		this.client_name = client_name;
		this.phase = phase;
		this.block = block;
		this.lot = lot;
	}

	public noticetoconstruct(String client_name, String phase, String block, String lot) {
		this.client_name = client_name;
		this.phase = phase;
		this.block = block;
		this.lot = lot;
	}

}
