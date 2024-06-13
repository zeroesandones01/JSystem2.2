package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import interfaces._GUI;

import org.jdesktop.swingx.JXPanel;

public class hdmfMon_accountQuery extends JXPanel implements _GUI {

	private static final long serialVersionUID = -2893676554089288730L;
	private PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 351);
	
	public hdmfMon_accountQuery(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_accountQuery(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfMon_accountQuery(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfMon_accountQuery(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			
		}
	}
}
