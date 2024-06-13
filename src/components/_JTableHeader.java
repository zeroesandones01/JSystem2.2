package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class _JTableHeader extends JTable {

	private static final long serialVersionUID = 1L;

	public _JTableHeader(JTable table) {
		super(getModel(table));
		initThis(table);
	}

	private void initThis(final JTable table) {
		//headerTable = new JTable(getModel(table));
		for (int i = 0; i < table.getRowCount(); i++) {
			System.out.println("Row " + (i + 1));
			setValueAt((i + 1), i, 0);
		}
		setShowGrid(false);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//setPreferredScrollableViewportSize(new Dimension(30, 0));
		getColumnModel().getColumn(0).setPreferredWidth(30);
		getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

				boolean selected = table.getSelectionModel().isSelectedIndex(row);
				Component component = table.getTableHeader().getDefaultRenderer() .getTableCellRendererComponent(table, value, false, false, -1, -2);
				
				((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
				if (selected) {
					component.setFont(component.getFont().deriveFont(Font.BOLD));
					component.setForeground(Color.red);
				} else {
					component.setFont(component.getFont().deriveFont(Font.PLAIN));
				}
				return component;
			}
		});
	}

	private static DefaultTableModel getModel(final JTable table) {
		return new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public int getColumnCount() {
				return 1;
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			@Override
			public int getRowCount() {
				return table.getRowCount();
			}

			@Override
			public Class<?> getColumnClass(int colNum) {
				switch (colNum) {
				case 0:
					return String.class;
				default:
					return super.getColumnClass(colNum);
				}
			}
		};
	}

}
