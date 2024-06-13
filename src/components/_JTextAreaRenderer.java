package components;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.Document;

public class _JTextAreaRenderer extends JTextArea implements TableCellRenderer{

	private static final long serialVersionUID = 2742870288398585070L;
	private JPanel pnlCenter;
	private JScrollPane scrollpane;
	private JTextArea txtArea;

	public _JTextAreaRenderer() {
		initThis();
	}

	public _JTextAreaRenderer(String text) {
		super(text);
		initThis();
	}

	public _JTextAreaRenderer(Document doc) {
		super(doc);
		initThis();
	}

	public _JTextAreaRenderer(int rows, int columns) {
		super(rows, columns);
		initThis();
	}

	public _JTextAreaRenderer(String text, int rows, int columns) {
		super(text, rows, columns);
		initThis();
	}

	public _JTextAreaRenderer(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		initThis();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.setText(String.valueOf(value));
		this.selectAll();
		this.setLineWrap(true);
		/*System.out.printf("Display value of Text: %s%n", value);
		scrollpane.getComponents();
		this.requestFocus();
		
		if(scrollpane.hasFocus()){
			txtArea.selectAll();
			System.out.println("Dumaan dito sa Select All");
		}
		
		if(hasFocus){
			this.getComponents();
			this.repaint();
		}*/
		return scrollpane;
	}
	
	private void initThis(){
		this.setLineWrap(true);
		this.setEnabled(true);
		this.setEditable(true);
		
		scrollpane = new JScrollPane();
		scrollpane.setViewportView(this);
		scrollpane.setEnabled(true);
		//scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollpane.setVerticalScrollBar(verticalScrollBar);
	}

}
