package components;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

import Functions.FncTables;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;

public class _JTableTotal extends JXTable {

	private static final long serialVersionUID = -6679681613525256269L;
	
	public _JTableTotal() {
		initThis(null);
	}

	public _JTableTotal(TableModel dm) {
		super(dm);
		initThis(null);
	}

	public _JTableTotal(TableModel dm, _JTableMain tableMain) {
		super(dm);
		initThis(tableMain);
	}

	public _JTableTotal(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		initThis(null);
	}

	public _JTableTotal(int numRows, int numColumns) {
		super(numRows, numColumns);
		initThis(null);
	}

	public _JTableTotal(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		initThis(null);
	}

	public _JTableTotal(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		initThis(null);
	}

	public _JTableTotal(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		initThis(null);
	}

	private void initThis(final _JTableMain tableMain) {
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTableHeader().setReorderingAllowed(false);
		
		setTableHeader(null);

		setRowHeight(20);
		setCellSelectionEnabled(true);
		
		ColorHighlighter base = new ColorHighlighter(HighlightPredicate.EVEN, Color.WHITE, Color.BLACK);
		ColorHighlighter alternate = new ColorHighlighter(HighlightPredicate.ODD, new Color(225, 225, 225), Color.BLACK);
		ColorHighlighter rollover = new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(255, 255, 255), Color.BLACK);

		setHighlighters(new CompoundHighlighter(alternate, base, rollover));
		setBackground(new Color(232, 229, 222));
		
		setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(rowAtPoint(e.getPoint()) == -1){
					clearSelection();
				}
			}
		});
		
		addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				((_JTableTotal)e.getSource()).clearSelection();
			}
		});
		
		tableMain.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
			public void columnAdded(TableColumnModelEvent arg0) { }
			public void columnMarginChanged(ChangeEvent arg0) {
				TableColumnModel tablemainColumnModel = tableMain.getColumnModel();
				TableColumnModel footerColumnModel = getColumnModel();

				for (int i = 0; i < tablemainColumnModel.getColumnCount(); i++) {
					int w = tablemainColumnModel.getColumn(i).getWidth();

					footerColumnModel.getColumn(i).setMinWidth(w);
					footerColumnModel.getColumn(i).setMaxWidth(w);
					footerColumnModel.getColumn(i).setPreferredWidth(w);
				}
				doLayout();
				repaint();
				//tableMain.repaint();
			}
			public void columnMoved(TableColumnModelEvent arg0) { }
			public void columnRemoved(TableColumnModelEvent arg0) { }
			public void columnSelectionChanged(ListSelectionEvent arg0) { }
		});
		
		/*getColumnModel().addColumnModelListener(new TableColumnModelListener() {
			public void columnAdded(TableColumnModelEvent arg0) { }
			public void columnMarginChanged(ChangeEvent arg0) {
				TableColumnModel tableColumnModel = getColumnModel();
				TableColumnModel footerColumnModel = tableMain.getColumnModel();
				for (int i = 0; i < tableColumnModel.getColumnCount(); i++) {
					int w = tableColumnModel.getColumn(i).getWidth();
					footerColumnModel.getColumn(i).setMinWidth(w);
					footerColumnModel.getColumn(i).setMaxWidth(w);
					footerColumnModel.getColumn(i).setPreferredWidth(w);
				}
				tableMain.doLayout();
				tableMain.repaint();
				//tableMain.repaint();
			}
			public void columnMoved(TableColumnModelEvent arg0) { }
			public void columnRemoved(TableColumnModelEvent arg0) { }
			public void columnSelectionChanged(ListSelectionEvent arg0) { }
		});*/
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
			String columnName = (String) getColumnModel().getColumn(x).getHeaderValue();
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
			getColumnExt(column).setVisible(false);
		}
	}
	
	public void setTotalLabel(int column) {
		addHighlighter(FncTables.getHighlighterTOTAL(column));
		setDefaultRenderer(getColumnClass(column), TextRenderer.getTextRenderer(SwingConstants.CENTER));
	}
	
	public void showColumns(String... columnNames) {
		for(String column : columnNames){
			try {
				getColumnExt(column).setVisible(true);
			} catch (NullPointerException e) { }
		}
	}

}
