package components;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

public class _JScrollPaneMain extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -975181945316657544L;

	public _JScrollPaneMain() {
		initThis();
	}

	public _JScrollPaneMain(Component view) {
		super(view);
		initThis();
	}

	public _JScrollPaneMain(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		initThis();
	}

	public _JScrollPaneMain(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		initThis();
	}

	private void initThis() {
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				/**
				 * Edited 2014-11-12 by John Lester Fatallo
				 * Edited 2015-06-03 by Alvin Gonzales - added then condition isEnabled()
				 * 
				 */
				try {
					if(((_JTableMain)getViewport().getView()).isEnabled()){
						((_JTableMain)getViewport().getView()).clearSelection();
					}
				} catch (ArrayIndexOutOfBoundsException e1) { }
			}
		});
	}

}
