package Functions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.table.TableColumnExt;

import components._JTableMain;
import components._JTableTotal;

public class FncTables {

	public static int[] getSelectedIndices(ListSelectionModel model, int start, int stop) {
		if ((start == -1) || (stop == -1)) {
			return new int[0];
		}
		int guesses[] = new int[stop - start + 1];
		int index = 0;
		for (int i = start; i <= stop; i++) {
			if (model.isSelectedIndex(i)) {
				guesses[index++] = i;
			}
		}
		int realthing[] = new int[index];
		System.arraycopy(guesses, 0, realthing, 0, index);
		return realthing;
	}

	public static void clearTable(DefaultTableModel model){
		model.setRowCount(0);
	}

	public static JTableHeader getRowHeader_Header() {
		JTableHeader th = new JTableHeader();
		th.setReorderingAllowed(false);

		TableColumn tc = new TableColumn();
		th.getColumnModel().addColumn(tc);
		tc.setHeaderValue("#");
		tc.setWidth(40);
		tc.setMinWidth(40);
		tc.setMaxWidth(40);

		return th;
	}

	public static JViewport getRowHeader_Footer(String value) {
		JLabel label = new JLabel(value);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font(FncLookAndFeel.font_name, Font.PLAIN, 10));
		label.setBackground(new Color(232, 232, 230));
		label.setForeground(Color.RED);
		label.setOpaque(true);
		label.setMaximumSize(new Dimension(40, 20));
		label.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JViewport jv = new JViewport();
		jv.setView(label);
		jv.setPreferredSize(label.getMaximumSize());

		return jv;
	}

	public static ColorHighlighter getHighlighterTOTAL(final int index) {
		HighlightPredicate highlightTotal = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return adapter.getValue(index).equals("Total") || adapter.getValue(index).equals("Totals");
			}
		};
		return new ColorHighlighter(highlightTotal, new Color(255, 175, 175), Color.BLACK);//255, 175, 175
	}

	public static ColorHighlighter getHighlighterTOTAL(final int index, Color backgroundColor) {
		HighlightPredicate highlightTotal = new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component table, ComponentAdapter adapter) {
				return adapter.getValue(index).equals("Total") && adapter.getColumnClass().equals(BigDecimal.class);
			}
		};
		return new ColorHighlighter(highlightTotal, backgroundColor, Color.BLACK);
	}

	public static void totalAccountEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal, int indexDebit, int indexCredit) {
		BigDecimal amountDebit = new BigDecimal(0.00);
		BigDecimal amountCredit = new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amountDebit = amountDebit.add(((BigDecimal) modelMain.getValueAt(x, indexDebit)));
			} catch (NullPointerException e1) {
				amountDebit = amountDebit.add(new BigDecimal(0.00));
			}
			try {
				amountCredit = amountCredit.add(((BigDecimal) modelMain.getValueAt(x, indexCredit)));
			} catch (NullPointerException e) {
				amountCredit = amountCredit.add(new BigDecimal(0.00));
			}
		}
		modelTotal.setValueAt(amountDebit, 0, indexDebit);
		modelTotal.setValueAt(amountCredit, 0, indexCredit);
	}

	/**
	 * Bind column of Main table into Total table
	 */
	public static void bindColumnTables(_JTableMain tableMain, final _JTableTotal tableTotal){
		for(int x=0; x < tableMain.getColumnCount(); x++){
			String headerValue = (String) tableMain.getColumnExt(x).getHeaderValue();

			tableMain.getColumnExt(headerValue).addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					TableColumnExt columnExt = (TableColumnExt) evt.getSource();
					if(evt.getPropertyName().equals("visible")){
						tableTotal.getColumnExt(columnExt.getHeaderValue()).setVisible((Boolean) evt.getNewValue());
					}
				}
			});
		}
	}

}
