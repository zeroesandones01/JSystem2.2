package Functions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

public class FncListRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;

	private JTable table;
	
	public FncListRenderer(JTable table) {
		this.table = table;
		initThis();
	}

	public FncListRenderer(String text) {
		super(text);
		initThis();
	}

	public FncListRenderer(Icon image) {
		super(image);
		initThis();
	}

	public FncListRenderer(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		initThis();
	}

	public FncListRenderer(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		initThis();
	}

	public FncListRenderer(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		initThis();
	}
	
	private void initThis() {
		setBorder(BorderFactory.createLineBorder(table.getGridColor()));
		setHorizontalAlignment(CENTER);
		setForeground(Color.BLACK);
		setBackground(new Color(232, 232, 230));
		setFont(new Font(FncLookAndFeel.font_name, Font.PLAIN, 10));
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText((value == null) ? "" : value.toString());
		if(isSelected){
			setBackground(table.getSelectionBackground());
			setForeground(Color.WHITE);
		}else{
			setBackground(new Color(232, 232, 230));
			setForeground(Color.BLACK);
		}
		
		
		return this;
	}

}
