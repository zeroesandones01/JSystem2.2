package Accounting.BOI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import components._JInternalFrame;

public class EnterNotaryDate extends _JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3942407781186035886L;
	static String title = "Enter Notary Date";
	private Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel jPanel1;
	private JTextField jTextField1;

	private JPanel pnlMain;

	public EnterNotaryDate() {
		super(title, true, true, true, true);
		initGUI();
	}

	public EnterNotaryDate(String title) {
		super(title);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(798, 85));
			{
				jPanel1 = new JPanel();
				pnlMain.add(jPanel1, BorderLayout.NORTH);
				jPanel1.setBorder(LINE_BORDER);
				jPanel1.setPreferredSize(new java.awt.Dimension(788, 112));
			}
			{
				jTextField1 = new JTextField();
				pnlMain.add(jTextField1, BorderLayout.SOUTH);
				jTextField1.setText("jTextField1");
			}
		}
		// TODO Auto-generated constructor stub
	}

	public EnterNotaryDate(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

}
