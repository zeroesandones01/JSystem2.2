package components;

import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class _JTabbedPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7333056701811405207L;

	public _JTabbedPane() {
		initThis();
	}

	public _JTabbedPane(int tabPlacement) {
		super(tabPlacement);
		initThis();
	}

	public _JTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
		initThis();
	}
	
	private void initThis() {
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				//System.out.printf("_JTabbedPane: %s%n", evt.getPropertyName());
				UIManager.put("TabbedPane.tabInsets", new Insets(2, 20, 1, 20));
			}
		});
	}

}
