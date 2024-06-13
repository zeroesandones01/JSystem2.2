package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import Functions.FncTables;

public class _JScrollPaneTotal extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1302402410887794482L;

	public _JScrollPaneTotal() {
		initThis(null);
	}

	public _JScrollPaneTotal(_JScrollPaneMain scrollMain) {
		initThis(scrollMain);
	}

	public _JScrollPaneTotal(Component view) {
		super(view);
		initThis(null);
	}

	public _JScrollPaneTotal(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		initThis(null);
	}

	public _JScrollPaneTotal(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		initThis(null);
	}

	private void initThis(final _JScrollPaneMain scrollMain) {
		setPreferredSize(new Dimension(799, 37));
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 17));
		getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				scrollMain.getHorizontalScrollBar().setValue(e.getValue());
			}
		});
		setRowHeaderView(FncTables.getRowHeader_Footer("0"));

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				((_JTableTotal)getViewport().getView()).clearSelection();
			}
		});
	}

}
