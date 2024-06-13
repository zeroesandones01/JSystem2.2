package Functions;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class FncKeyboardFocusManager extends KeyboardFocusManager {

	public FncKeyboardFocusManager() {
		
	}

	@Override
	public boolean dispatchEvent(AWTEvent e) {
		return false;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		return false;
	}

	@Override
	public boolean postProcessKeyEvent(KeyEvent e) {
		return false;
	}

	@Override
	public void processKeyEvent(Component focusedComponent, KeyEvent e) {

	}

	@Override
	protected void enqueueKeyEvents(long after, Component untilFocused) {

	}

	@Override
	protected void dequeueKeyEvents(long after, Component untilFocused) {

	}

	@Override
	protected void discardKeyEvents(Component comp) {

	}

	@Override
	public void focusNextComponent(Component aComponent) {

	}

	@Override
	public void focusPreviousComponent(Component aComponent) {

	}

	@Override
	public void upFocusCycle(Component aComponent) {

	}

	@Override
	public void downFocusCycle(Container aContainer) {

	}

}
