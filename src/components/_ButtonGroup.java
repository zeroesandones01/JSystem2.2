package components;

import java.awt.event.ActionEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Alvin Gonzales
 */
public class _ButtonGroup extends ButtonGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8013395353650450585L;

	/*public _ButtonGroup() {
		// TODO Auto-generated constructor stub
	}*/

	public void setSelectedButton(ActionEvent e) {
		JButton button = ((JButton)e.getSource());
		button.setText(String.format("<html><font size=\"6\">%s</font></html>", button.getText()));

		Enumeration<AbstractButton> enums = this.getElements(); 
		while(enums.hasMoreElements()) {
			AbstractButton b = enums.nextElement();
			b.setSelected(b.equals(button));
		}
	}

	public void setSelectedButton(JButton button) {
		//JButton button = ((JButton)e.getSource());
		System.out.println("***** DUMAAN *****");
		button.setText(String.format("<html><font size=\"6\">%s</font></html>", button.getText()));

		Enumeration<AbstractButton> enums = this.getElements(); 
		while(enums.hasMoreElements()) {
			AbstractButton b = enums.nextElement();
			b.setSelected(b.equals(button));
		}
	}

	public void clearSelection() {
		Enumeration<AbstractButton> e = this.getElements(); 
		while(e.hasMoreElements()) {
			AbstractButton b = e.nextElement();
			b.setText(Jsoup.parse(b.getText()).text());
			b.setSelected(false);
		}
	}

	public JButton getSelectedButton() {
		JButton selectedButton = null;
		for(Enumeration<AbstractButton> buttons = getElements(); buttons.hasMoreElements();){
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				selectedButton = (JButton) button;
			}
		}
		return selectedButton;
	}

	public String getActionCommand() {
		try {
			String selection = getSelectedButton().getActionCommand();
			Document doc = Jsoup.parse(selection);
			selection = doc.body().text();
			
			return selection;
		} catch (NullPointerException e) {
			return null;
		}
	}

}
