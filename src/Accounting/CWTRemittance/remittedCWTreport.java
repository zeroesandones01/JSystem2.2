package Accounting.CWTRemittance;

import interfaces._GUI;

import java.awt.event.ActionListener;

import components._JInternalFrame;

public class remittedCWTreport extends _JInternalFrame implements _GUI,
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7511369059806595820L;
	static String title = "CWT Report";
	
	public remittedCWTreport() {
		initGUI();
	}

	public remittedCWTreport(String title) {
		super(title);
		initGUI();
	}

	public remittedCWTreport(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		
	}

}
