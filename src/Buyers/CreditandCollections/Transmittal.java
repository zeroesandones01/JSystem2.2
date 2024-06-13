package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import org.jdesktop.swingx.JXPanel;

import components._JInternalFrame;
import components._JTabbedPane;
import interfaces._GUI;

public class Transmittal extends _JInternalFrame implements _GUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Transmittal";
	public static Dimension frameSize = new Dimension(600, 600);
	private _JTabbedPane tabCenter;
	private pnlNoticesTagForCourier pnlNoticesTag;
	private pnlTransmittalForPostOffice pnlTransmittal;
	private pnlPostOfficeUtility pnlPostOffice;

	public Transmittal() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Transmittal(String title) {
		super(title);
		initGUI();
	}

	public Transmittal(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new Dimension(frameSize));
			getContentPane().setLayout(new BorderLayout());
			{
				JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					tabCenter = new _JTabbedPane();
					pnlMain.add(tabCenter, BorderLayout.CENTER);
					{
						pnlNoticesTag = new pnlNoticesTagForCourier(this); 
						tabCenter.addTab("Notices Tag For Courier", pnlNoticesTag);
					}
					{
						pnlPostOffice = new pnlPostOfficeUtility(this); 
						tabCenter.addTab("Post Office Utility", pnlPostOffice);
					}
					{
						pnlTransmittal = new pnlTransmittalForPostOffice(this); 
						tabCenter.addTab("Transmittal Retrieval", pnlTransmittal);
					}
				}
			}

		} catch (Exception e) {
		}

	}

}
