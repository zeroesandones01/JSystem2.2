package Functions;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class CheckBoxHeader extends JCheckBox implements TableCellRenderer, MouseListener, ItemListener {

	protected static final long serialVersionUID = 5064928133566796912L;

	protected JTable table;

	protected int column;
	protected boolean mousePressed = false;
	protected boolean state = true;

	private RolloverAdapter adapter;
	
	public CheckBoxHeader(JTable table) {
		this.table = table;
		addItemListener(this);
	}

	public CheckBoxHeader(JTable table, RolloverAdapter adapter) {
		this.table = table;
		this.adapter = adapter;
		addItemListener(this);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		getModel().setRollover(adapter.isRolloverCell(row, column));

		if (table != null) {
			JTableHeader header = table.getTableHeader();
			if (header != null) {
				setForeground(header.getForeground());
				setBackground(new Color(225, 225, 225));
				setFont(header.getFont());
				setHorizontalAlignment(CENTER);
				setToolTipText("Select/Deselect All");
				setEnabled(this.isEnabled());
				header.addMouseListener(this);
			}
		}
		setText("");
		setColumn(column);
		setEnabled(table.isEnabled());
		//setBorder(UIManager.getBorder("TableHeader.cellBorder"));

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else {
			setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return this;
	}
	protected void setColumn(int column) {
		this.column = column;
	}
	public int getColumn() {
		return column;
	}

	protected void handleClickEvent(MouseEvent e) {
		if (mousePressed) {
			mousePressed=false;
			JTableHeader header = (JTableHeader)(e.getSource());
			JTable tableView = header.getTable();
			TableColumnModel columnModel = tableView.getColumnModel();
			int viewColumn = columnModel.getColumnIndexAtX(e.getX());
			int column = tableView.convertColumnIndexToModel(viewColumn);

			if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {
				doClick();
			}
		}
	}
	public void mouseClicked(MouseEvent e) {
		if(isEnabled()){
			handleClickEvent(e);
			((JTableHeader)e.getSource()).repaint();
		}
	}
	public void mousePressed(MouseEvent e) {
		if(isEnabled()){
			//setSelected(true);
			mousePressed = true;
		}
	}
	public void mouseReleased(MouseEvent e) {
		if(isEnabled()){
			//setSelected(true);
		}
	}
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }

	public boolean isEnabled() {
		return state;
	}

	public void setEnabled(boolean state) {
		this.state = state;
	}

	/*public boolean isSelected() {
		return value;
	}

	public void setSelected(boolean value) {
		this.value = value;
	}*/

	public static class RolloverAdapter extends MouseAdapter {
		private int row = -2;
		private int column = -1;
		private JTable table;

		public RolloverAdapter(JTable table) {
			this.table = table;
		}

		public boolean isRolloverCell(int row, int column) {
			return this.row == row && this.column == column;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			int lastRow = row;
			int lastColumn = column;

			row = -1;
			column = table.columnAtPoint(e.getPoint());

			if (row == lastRow && column == lastColumn)
				return;

			if (row == -1 && column >= 0) {
				table.getTableHeader().repaint();
			}

			if (lastRow == -1 && lastColumn >= 0) {
				table.getTableHeader().repaint();
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (row == -1 && column >= 0) {
				table.getTableHeader().repaint();
			}
			row = column = -2;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		if (source instanceof AbstractButton == false){
			return;
		}
		boolean checked = e.getStateChange() == ItemEvent.SELECTED;

		for(int x = 0, y = table.getRowCount(); x < y; x++){
			table.setValueAt(new Boolean(checked),x,0);
		}
	}
	
	public JCheckBox getCheckBox() {
		return this;
	}

}
