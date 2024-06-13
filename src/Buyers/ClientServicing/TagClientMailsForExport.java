package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import components._JInternalFrame;

/**
 * 
 * @author Alvin Gonzales (2015-05-04)
 *
 */
public class TagClientMailsForExport extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6314696421311178770L;
	static String title = "Tag Client Mails for Export";
	private JPanel pnlSouth;
	private JScrollPane scrollNotices;
	private JPanel pnlNorth;
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Dimension frameSize = new Dimension(450, 500);
	
	private JPanel pnlMain;

	public TagClientMailsForExport() {
		super(title, true, true, true, true);
		initGUI();
	}

	public TagClientMailsForExport(String title) {
		super(title);
		initGUI();
	}

	public TagClientMailsForExport(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		
		pnlMain = new JPanel(new BorderLayout());
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new BorderLayout());
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(LINE_BORDER);
			pnlNorth.setPreferredSize(new Dimension(438, 65));
		}
		{
			scrollNotices = new JScrollPane();
			pnlMain.add(scrollNotices, BorderLayout.CENTER);
		}
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setBorder(LINE_BORDER);
			pnlSouth.setPreferredSize(new Dimension(438, 30));
		}
	}

}
