package Reports.LegalAndLiaisoning;

import java.awt.event.ActionListener;

import components._JInternalFrame;
import interfaces._GUI;

public class RequestedECAR extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -5728671297676625663L;

	public RequestedECAR() {
		initGUI();
	}

	public RequestedECAR(String title) {
		super(title);
		initGUI();
	}

	public RequestedECAR(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		// TODO Auto-generated method stub

	}

}
