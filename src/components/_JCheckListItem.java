package components;


public class _JCheckListItem {
	private String label;
	private boolean isSelected = false;

	public _JCheckListItem(String label) {
		this.label = label;
	}

	public _JCheckListItem(String label, boolean isSelected) {
		this.label = label;
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String toString() {
		return label;
	}

}
