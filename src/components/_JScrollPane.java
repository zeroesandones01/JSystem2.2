package components;

import java.awt.Component;

import javax.swing.JScrollPane;

public class _JScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;

	public _JScrollPane() {
		initThis();
	}

	public _JScrollPane(Component view) {
		super(view);
		initThis();
	}

	public _JScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		initThis();
	}

	public _JScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		initThis();
	}

	private void initThis() {
		
	}

}
