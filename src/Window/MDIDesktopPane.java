package Window;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;

//FOR WINDOW/FRAME MANAGEMENT (CASCADE,TILE,SELECT) 
//AND AUTOSCROLL OF FRAME WHEN INTERNAL FRAMES ARE MOVED

public class MDIDesktopPane extends JDesktopPane {
	private static final long serialVersionUID = -2735286653977098497L;
	private static int FRAME_OFFSET = 20;
	private MDIDesktopManager manager;

	public MDIDesktopPane() {
		manager = new MDIDesktopManager(this);
		setDesktopManager(manager);
		setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

		/*for(Object key : getActionMap().allKeys()){
			System.out.printf("%-35s %s%n", key, getActionMap().get(key));
		}*/

		// Define keystrokes
		KeyStroke pressCtrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK, false);
		KeyStroke depressCtrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK, true);

		// Define actions
		Action tabKeyPressAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.out.println("Yes I'm pressing keys.");
			}
		};

		Action tabKeyDepressAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				System.out.println("Yes I'm depressing keys.");
			}
		};

		// Remove ctrl-tab from normal focus traversal
		Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
		forwardKeys.remove(pressCtrlTab);

		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

		// Configure map
		InputMap inputMap = getInputMap();
		ActionMap actionMap = getActionMap();

		inputMap.put(pressCtrlTab, "TabPress");
		actionMap.put("TabPress", tabKeyPressAction);

		inputMap.put(depressCtrlTab, "TabDepress");
		actionMap.put("TabDepress", tabKeyDepressAction);
	}

	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		checkDesktopSize();
		/*try {//XXX
			checkDesktopSize();
		} catch (NullPointerException e) { }*/
	}

	public Component add(JInternalFrame frame) {
		JInternalFrame[] array = getAllFrames();
		Point p;
		//int w;
		//int h;

		Component retval = super.add(frame);
		
		checkDesktopSize();
		/*try {//XXX
			checkDesktopSize();
		} catch (Exception e1) { }*/

		if (array.length > 0) {
			p = array[0].getLocation();
			p.x = p.x + FRAME_OFFSET;
			p.y = p.y + FRAME_OFFSET;
		} else {
			p = new Point(0, 0);
		}
		frame.setLocation(p.x, p.y);
		/*if (frame.isResizable()) {
			w = getWidth() - (getWidth() / 3);
			h = getHeight() - (getHeight() / 3);
			if (w < frame.getMinimumSize().getWidth())
				w = (int) frame.getMinimumSize().getWidth();
			if (h < frame.getMinimumSize().getHeight())
				h = (int) frame.getMinimumSize().getHeight();
			frame.setSize(w, h);
		}*/
		moveToFront(frame);
		frame.setVisible(true);
		try {
			frame.setSelected(true);
		} catch (PropertyVetoException e) {
			frame.toBack();
		}
		return retval;
	}

	public void remove(Component c) {
		super.remove(c);
		checkDesktopSize();
	}

	public Boolean hasMaximizeFrame() {
		ArrayList<Boolean> listMaximized = new ArrayList<Boolean>();
		for(JInternalFrame frame : getAllFrames()){
			listMaximized.add(frame.isMaximum() && !frame.isIcon());
		}
		return listMaximized.contains(true);
	}

	/**
	 * Cascade all internal frames
	 */
	public void cascadeFrames() {
		int x = 0;
		int y = 0;
		JInternalFrame allFrames[] = getAllFrames();

		manager.setNormalSize();
		for (int i = allFrames.length - 1; i >= 0; i--) {

			allFrames[i].setLocation(x, y);
			x = x + FRAME_OFFSET;
			y = y + FRAME_OFFSET;
		} // for

		manager.resizeDesktop();
	}

	/**
	 * Tile all internal frames
	 */
	public void tileFrames() {
		java.awt.Component allFrames[] = getAllFrames();
		manager.setNormalSize();

		int x = 0, nextY = 0;  					////////// 
		int y = 0;
		for (int i = 0; i < allFrames.length; i++) {

			allFrames[i].setLocation(x, y);

			x = x + allFrames[i].getWidth();
			// Get the height of the frame which has the most height (para di overlap)
			nextY = (nextY>y + allFrames[i].getHeight()) ? nextY : y + allFrames[i].getHeight() ;
			if  ( x > Toolkit.getDefaultToolkit().getScreenSize().width )
			{
				x = 0;
				y = nextY;
				nextY = 0;
			} // if

		} // for

		manager.resizeDesktop();
	}

	/**
	 * Sets all component size properties ( maximum, minimum, preferred) to the
	 * given dimension.
	 */
	public void setAllSize(Dimension d) {
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);
	} // setAllSize

	/**
	 * Sets all component size properties ( maximum, minimum, preferred) to the
	 * given width and height.
	 */
	public void setAllSize(int width, int height) {
		setAllSize(new Dimension(width, height));
	} // setAllSize

	private void checkDesktopSize() {
		if (getParent() != null && isVisible())
			manager.resizeDesktop();
	} // checkDesktopSize
}// MDIDesktopPane
