package interfaces;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author Alvin A. Gonzales
 *
 */
public interface _GUI {

	Dimension _SIZE = new Dimension(800, 600);
	Border _LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	
	/**
	 * BorderFactory.createEmptyBorder(5, 5, 5, 5)
	 * 
	 */
	Border _EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	public void initGUI();
}
