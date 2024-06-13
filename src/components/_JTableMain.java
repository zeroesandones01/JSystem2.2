package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.rollover.RolloverProducer;

import DateChooser._JDateChooser;
import Functions.CheckBoxHeader;
import Functions.FncListRenderer;
import Lookup._JLookup;
import Lookup._JLookupTable.RolloverMouseAdapter;
import Renderer.DateRenderer;
import Renderer.DateRenderer2;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;

/**
 * Customized JXTable 
 * @author Alvin Gonzales
 */
public class _JTableMain extends JXTable {

	private static final long serialVersionUID = -6679681613525256269L;

	private Boolean headerMenuVisible;

	private JCheckBox checkHeader;
	
	
	public _JTableMain() {
		initThis();
	}

	/**
	 * Customized JXTable 
	 * @param dm = TableModel
	 * @author Alvin Gonzales
	 */
	public _JTableMain(TableModel dm) {
		super(dm);
		initThis();
	}

	public _JTableMain(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		initThis();
	}

	public _JTableMain(int numRows, int numColumns) {
		super(numRows, numColumns);
		initThis();
	}

	public _JTableMain(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		initThis();
	}

	public _JTableMain(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		initThis();
	}

	public _JTableMain(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		initThis();
	}

	private void initThis() {
		setColumnControlVisible(true);
		setHorizontalScrollEnabled(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTableHeader().setReorderingAllowed(false);
		setCellSelectionEnabled(true);
		setRowHeight((Integer) UIManager.get("Table.rowHeight"));

		// XXX EVEN
		ColorHighlighter base = new ColorHighlighter(HighlightPredicate.EVEN, new Color(255, 255, 255), Color.BLACK);
		HighlightPredicate highlightTotalEVEN = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) == 0) && adapter.getColumnClass().equals(BigDecimal.class);
			}
		};
		ColorHighlighter bigdecimalEVEN = new ColorHighlighter(highlightTotalEVEN, new Color(152, 255, 152), Color.BLACK);

		// XXX ODD
		ColorHighlighter alternate = new ColorHighlighter(HighlightPredicate.ODD, new Color(225, 225, 225), Color.BLACK);
		HighlightPredicate highlightTotalODD = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) != 0) && adapter.getColumnClass().equals(BigDecimal.class);
			}
		};
		ColorHighlighter bigdecimalODD = new ColorHighlighter(highlightTotalODD, new Color(127, 225, 127), Color.BLACK);

		// XXX ROLLOVER
		ColorHighlighter rollover = new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(240, 240, 240), Color.BLACK);
		HighlightPredicate highlightTotalROLLOVER = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				if (!adapter.getComponent().isEnabled()) return false;
				Point p = (Point) adapter.getComponent().getClientProperty(RolloverProducer.ROLLOVER_KEY);
				return (p != null &&  p.y == adapter.row) && adapter.getColumnClass().equals(BigDecimal.class);
			}
		};
		ColorHighlighter bigdecimalROLLOVER = new ColorHighlighter(highlightTotalROLLOVER, new Color(142, 240, 142), Color.BLACK);



		// XXX TIMESTAMP
		HighlightPredicate hpTimestampODD = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) != 0) && (adapter.getColumnClass().equals(Timestamp.class) || adapter.getColumnClass().equals(Date.class) || adapter.getColumnClass().equals(_JDateChooser.class));
			}
		};
		ColorHighlighter chTimestampODD = new ColorHighlighter(hpTimestampODD, new Color(135, 207, 255), Color.BLACK);

		HighlightPredicate hpTimestampEVEN = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) == 0) && (adapter.getColumnClass().equals(Timestamp.class) || adapter.getColumnClass().equals(Date.class) || adapter.getColumnClass().equals(_JDateChooser.class));
			}
		};
		ColorHighlighter chTimestampEVEN = new ColorHighlighter(hpTimestampEVEN, new Color(155, 227, 255), Color.BLACK);

		HighlightPredicate hpTimestampROLLOVER = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				if (!adapter.getComponent().isEnabled()) return false;
				Point p = (Point) adapter.getComponent().getClientProperty(RolloverProducer.ROLLOVER_KEY);
				return (p != null &&  p.y == adapter.row) && (adapter.getColumnClass().equals(Timestamp.class) || adapter.getColumnClass().equals(Date.class) || adapter.getColumnClass().equals(_JDateChooser.class));
			}
		};
		ColorHighlighter chTimestampROLLOVER = new ColorHighlighter(hpTimestampROLLOVER, new Color(150, 222, 255), Color.BLACK);



		// XXX LOOK-UP
		HighlightPredicate hpLookupODD = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) != 0) && (adapter.getColumnClass().equals(_JLookup.class) || adapter.getColumnClass().equals(Date.class));
			}
		};
		ColorHighlighter chLookupODD = new ColorHighlighter(hpLookupODD, new Color(128, 255, 255), Color.BLACK);

		HighlightPredicate hpLookupEVEN = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) == 0) && (adapter.getColumnClass().equals(_JLookup.class) || adapter.getColumnClass().equals(Date.class));
			}
		};
		ColorHighlighter chLookupEVEN = new ColorHighlighter(hpLookupEVEN, new Color(148, 255, 255), Color.BLACK);

		HighlightPredicate hpLookupROLLOVER = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				if (!adapter.getComponent().isEnabled()) return false;
				Point p = (Point) adapter.getComponent().getClientProperty(RolloverProducer.ROLLOVER_KEY);
				return (p != null &&  p.y == adapter.row) && (adapter.getColumnClass().equals(_JLookup.class) || adapter.getColumnClass().equals(Date.class));
			}
		};
		ColorHighlighter chLookupROLLOVER = new ColorHighlighter(hpLookupROLLOVER, new Color(143, 250, 255), Color.BLACK);

		// XXX TEXT AREA
		HighlightPredicate hpTextAreaODD = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) != 0) && (adapter.getColumnClass().equals(_JTextAreaRenderer.class) || adapter.getColumnClass().equals(Date.class));
			}
		};
		ColorHighlighter chTextAreaODD = new ColorHighlighter(hpTextAreaODD, new Color(128, 255, 255), Color.BLACK);

		HighlightPredicate hpTextAreaEVEN = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return ((adapter.row % 2) == 0) && (adapter.getColumnClass().equals(_JTextAreaRenderer.class) || adapter.getColumnClass().equals(Date.class));
			}
		};
		ColorHighlighter chTextAreaEVEN = new ColorHighlighter(hpTextAreaEVEN, new Color(148, 255, 255), Color.BLACK);

		HighlightPredicate hpTextAreaROLLOVER = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				if (!adapter.getComponent().isEnabled()) return false;
				Point p = (Point) adapter.getComponent().getClientProperty(RolloverProducer.ROLLOVER_KEY);
				return (p != null &&  p.y == adapter.row) && (adapter.getColumnClass().equals(_JTextAreaRenderer.class) || adapter.getColumnClass().equals(Date.class));
			}
		};
		ColorHighlighter chTextAreaROLLOVER = new ColorHighlighter(hpTextAreaROLLOVER, new Color(143, 250, 255), Color.BLACK);


		//XXX 
		setHighlighters(new CompoundHighlighter(alternate, base, bigdecimalODD, bigdecimalEVEN, chTimestampODD, chTimestampEVEN, chLookupODD, chLookupEVEN,chTextAreaODD,chTextAreaEVEN, rollover, bigdecimalROLLOVER, chTimestampROLLOVER, chLookupROLLOVER,chTextAreaROLLOVER));

		setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
		setDefaultRenderer(Time.class, DateRenderer.getTimeRenderer());
		setDefaultRenderer(Timestamp.class, DateRenderer.getDateRenderer());
		setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));
		setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
		setDefaultRenderer(_JLookup.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		setDefaultRenderer(_JTextAreaRenderer.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));

		setDefaultRenderer(_JDateChooser.class, new DateRenderer(new SimpleDateFormat("MM/dd/YYYY"), SwingConstants.CENTER));
		setDefaultEditor(_JDateChooser.class, new DateEditor());

		setFillsViewportHeight(false);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(rowAtPoint(e.getPoint()) != -1){
					setCellSelectionEnabled(true);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(rowAtPoint(e.getPoint()) != -1){
					setCellSelectionEnabled(true);
				}
			}
		});

		/**
		 * @author Alvin Gonzales
		 * Added 2014-11-06 by 
		 */
		addPropertyChangeListener("swingx.rollover", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				//packAll();
			}
		});

		//Added 2015-01-20 by Alvin Gonzales
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		DefaultTableModel model = (DefaultTableModel) this.getModel();
		if(model.getColumnClass(0).equals(Boolean.class)){

			RolloverMouseAdapter rolloverAdapter = new RolloverMouseAdapter(this);
			//RolloverBooleanRenderer renderer = new RolloverBooleanRenderer(rolloverAdapter);

			CheckBoxHeader.RolloverAdapter ma = new CheckBoxHeader.RolloverAdapter(this);
			
			CheckBoxHeader checkBox = new CheckBoxHeader(this, ma);
			
			this.getTableHeader().addMouseListener(ma);
			this.getTableHeader().addMouseMotionListener(ma);
		
			
			this.getColumnModel().getColumn(0).setHeaderRenderer(checkBox);
			this.getColumnModel().getColumn(0).setMaxWidth(35);
			this.getColumnModel().getColumn(0).setMinWidth(35);
			this.getColumnModel().getColumn(0).setPreferredWidth(35);

			this.addMouseListener(rolloverAdapter);
			this.addMouseMotionListener(rolloverAdapter);
			
			setCheckHeader(checkBox.getCheckBox());
			
			//this.getColumnModel().getColumn(0).setCellRenderer(renderer);

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.getModel());
			this.setRowSorter(sorter);
			sorter.setSortable(0, false);
		}

		//System.out.printf("Row Height: %s (%s)%n", UIManager.get("Table.rowHeight"), this.getRowHeight());
	}

	/**
	 * @return the headerMenuVisible
	 */
	public Boolean isHeaderMenuVisible() {
		return headerMenuVisible;
	}

	/**
	 * Declare this after setting columns preferred width
	 * 
	 * @param headerMenuVisible the headerMenuVisible to set
	 * @author Alvin Gonzales
	 */
	public void setHeaderMenuVisible(Boolean headerMenuVisible) {
		this.headerMenuVisible = headerMenuVisible;
	}
	
	public JList getRowHeader() {
		JList rowHeader = new JList();
		rowHeader.setToolTipText("Click here to select whole row.");
		rowHeader.setFixedCellWidth(40);
		rowHeader.setFixedCellHeight((Integer) UIManager.get("Table.rowHeight"));
		rowHeader.setFocusable(false);
		rowHeader.setBackground(new Color(232, 232, 230));
		rowHeader.setCellRenderer(new FncListRenderer(this));
		rowHeader.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		rowHeader.setSelectionModel(getSelectionModel());

		rowHeader.setDragEnabled(true);

		rowHeader.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setColumnSelectionAllowed(false);
				setCellSelectionEnabled(false);
				setRowSelectionAllowed(true);
			}
		});

		rowHeader.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					int selectedIndex = ((JList)e.getSource()).getSelectedIndex();
					scrollRowToVisible(selectedIndex);
				}
			}
		});

		return rowHeader;
	}	
	public JList getRowHeader22() { //adjusted row height to 22
		JList rowHeader = new JList();
		rowHeader.setToolTipText("Click here to select whole row.");
		rowHeader.setFixedCellWidth(40);
		rowHeader.setFixedCellHeight(22);
		rowHeader.setFocusable(false);
		rowHeader.setBackground(new Color(232, 232, 230));
		rowHeader.setCellRenderer(new FncListRenderer(this));
		rowHeader.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		rowHeader.setSelectionModel(getSelectionModel());

		rowHeader.setDragEnabled(true);

		rowHeader.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setColumnSelectionAllowed(false);
				setCellSelectionEnabled(false);
				setRowSelectionAllowed(true);
			}
		});

		rowHeader.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					int selectedIndex = ((JList)e.getSource()).getSelectedIndex();
					scrollRowToVisible(selectedIndex);
				}
			}
		});

		return rowHeader;
	}

	/**
	 * Pack the given columns with the given margin
	 * 
	 * @param margin e.g packColumns(10);
	 * @author Alvin Gonzales
	 */
	public void packAll(int margin) {
		for(int x=0; x < getColumnCount(); x++){
			packColumn(x, margin);
		}
	}

	/**
	 * Pack the given columns with the margin of 10
	 * 
	 * @param columnNames e.g packColumns("ColumnName 1", "ColumnName 2");
	 * @author Alvin Gonzales
	 */
	public void packColumns(String... columnNames) {
		List<String> listOfColumns = Arrays.asList(columnNames);

		for(int x=0; x < getColumnCount(); x++){
			String columnName = (String) getColumnModel().getColumn(x).getHeaderValue();
			if(listOfColumns.contains(columnName)){
				packColumn(x, 10);
			}
		}
	}

	/**
	 * Pack the given columns with the margin
	 * 
	 * @param columnNames e.g packColumns(10, "ColumnName 1", "ColumnName 2");
	 * @author Alvin Gonzales
	 */
	public void packColumns(int margin, String... columnNames) {
		List<String> listOfColumns = Arrays.asList(columnNames);

		for(int x=0; x < getColumnCount(); x++){
			String columnName = ((String) getColumnModel().getColumn(x).getHeaderValue()).trim();
			if(listOfColumns.contains(columnName)){
				packColumn(x, margin);
			}
		}
	}

	/**
	 * Hide given columns
	 * 
	 * @param columnNames e.g hideColumns("ColumnName 1", "ColumnName 2");
	 * @author Alvin Gonzales
	 */
	public void hideColumns(String... columnNames) {
		for(String column : columnNames){
			try {
				getColumnExt(column).setVisible(false);
				//getColumnExt(column).setEditable(false);
			} catch (NullPointerException e) { }
		}
	}
	
	/**
	 * Unhide given columns
	 * 
	 * @param columnNames e.g unhideColumns("ColumnName 1", "ColumnName 2");
	 * @author Emmanuel D. Apostol
	 */
	public void unhideColumns(String... columnNames) {
		for(String column : columnNames){
			try {
				getColumnExt(column).setVisible(true);
				//getColumnExt(column).setEditable(false);
			} catch (NullPointerException e) { }
		}
	}

	/**
	 * Editable  given columns
	 * 
	 * @param columnNames e.g hideColumns("ColumnName 1", "ColumnName 2");
	 * @author Christian Paquibot
	 */

	public void setEditbaleColumn(String... columnNames) {
		for(String column : columnNames){
			try {
				getColumnExt(column).setEditable(false);
			} catch (NullPointerException e) { }
		}
	}
	
	//
	/*public void setLineWarp(String... columnNames) {
		for(String column : columnNames){
			try {
				getColumnExt(column).setCellRenderer((TableCellRenderer) new JTextArea());
			} catch (NullPointerException e) { }
		}
	}*/

	/**
	 * convertColumnIndexToModel
	 * 
	 * @param columnName e.g "ID";
	 * @author Alvin Gonzales (2015-01-28)
	 */
	public int getModelColumn(String columnName) {
		return convertColumnIndexToModel(getColumnModel().getColumnIndex(columnName));
	}

	/**
	 * convertRowIndexToModel
	 * 
	 * @param selectedRow e.g 1;
	 * @author Alvin Gonzales (2015-01-28)
	 * 
	 */
	public int getModelRow(int selectedRow) {
		return convertRowIndexToModel(selectedRow);
	}

	protected class DateEditor extends AbstractCellEditor implements TableCellEditor {//XXX
		private static final long serialVersionUID = -2447406754201061987L;
		//DateRenderer dateR = new DateRenderer();

		DateRenderer2 dateChooser = new DateRenderer2();

		public DateEditor() {

		}

		public boolean isCellEditable(EventObject event) {
			return true;
		}
		public Object getCellEditorValue() {
			/*JDateChooser checkbox = dateR;
			checkbox.setDateFormatString("MM/dd/yy");
			try {
				return new SimpleDateFormat("MM/dd/yy").format(checkbox.getDate());
			} catch (NullPointerException e) {
				return null;
			}*/
			return dateChooser.getDate();
		}
		public Component getTableCellEditorComponent(final JTable table, final Object value, boolean isSelected, final int row, final int column) {
			Component editor = dateChooser.getTableCellRendererComponent(table, value, isSelected, true, row, column);
			editor.setFont(table.getFont());

			editor.addPropertyChangeListener("date", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					//System.out.printf("%s (%s, %s)%n", arg0.getPropertyName(), arg0.getOldValue(), arg0.getNewValue());
					DateEditor.this.fireEditingStopped();
				}
			});
			return editor;
		}
	}

	public void showColumns(String... columnNames) {
		for(String column : columnNames){
			try {
				getColumnExt(column).setVisible(true);
			} catch (NullPointerException e) { }
		}
	}

	public JCheckBox getCheckHeader() {
		return checkHeader;
	}

	public void setCheckHeader(JCheckBox checkHeader) {
		this.checkHeader = checkHeader;
	}
}
