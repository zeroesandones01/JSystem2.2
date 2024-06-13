package Functions;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Created by Alvin Gonzales (2015-03-11)
 * 
 */
public class FncComponent {

	public FncComponent() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Added 2014-11-06 by Alvin Gonzales
	 *
	 */
	public static void setComponentsEnabled(JPanel panel, boolean enable) {
		panelLooper(panel, enable);
	}

	public static void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				panelLooper((JScrollPane) comp, enable);
			} else {
				if (comp.getName() != null) {
					comp.setEnabled(enable);
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						comp.setEnabled(enable);
					}
				}
			}
		}
	}
	
	/**
	 * Added 2014-11-07 by Alvin Gonzales
	 *
	 */
	public void setComponentsEditable(JPanel panel, boolean editable) {
		panelLooperEditable(panel, editable);
	}

	public void panelLooperEditable(Container panel, boolean editable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooperEditable((JPanel) comp, editable);
			} else if (comp instanceof JScrollPane) {
				panelLooperEditable((JScrollPane) comp, editable);
			} else {
				if(comp instanceof JTextField){
					((JTextField)comp).setEditable(editable);
				}
			}
		}
	}

}
