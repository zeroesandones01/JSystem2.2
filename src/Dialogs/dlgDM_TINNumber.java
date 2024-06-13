package Dialogs;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import components._JXTextField;

public class dlgDM_TINNumber extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(300, 100);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlDateOfApproval;
	private JLabel lblDateOfApproval;
	private _JXTextField txtTINNumber;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JButton btnCancel;

	private String TINnumber;

	public dlgDM_TINNumber() {
		initGUI();
	}

	public dlgDM_TINNumber(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_TINNumber(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_TINNumber(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_TINNumber(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_TINNumber(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_TINNumber(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_TINNumber(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_TINNumber(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_TINNumber(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_TINNumber(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_TINNumber(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_TINNumber(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_TINNumber(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_TINNumber(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_TINNumber(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridLayout(1, 1, 3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlDateOfApproval = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlDateOfApproval);
					{
						lblDateOfApproval = new JLabel("TIN #");
						pnlDateOfApproval.add(lblDateOfApproval, BorderLayout.WEST);
						lblDateOfApproval.setPreferredSize(new Dimension(60, 37));
					}
					{
						txtTINNumber = new _JXTextField("TIN Number");
						pnlDateOfApproval.add(txtTINNumber, BorderLayout.CENTER);
						txtTINNumber.setEditable(true);
					}
				}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setAlignmentX(0.5f);
					pnlNavigation.setAlignmentY(0.5f);
					pnlNavigation.setMaximumSize(new Dimension(205, 30));
					{
						btnOK = new JButton("OK");
						pnlNavigation.add(btnOK);
						btnOK.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}
		this.pack();
	}//XXX END OF INIT GUI

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			setTINnumber(txtTINNumber.getText());
			dispose();
		}
		if(action.equals("Cancel")){
			setTINnumber(null);
			dispose();
		}
		if(action.equals("Escape")){
			setTINnumber(null);
			dispose();
		}
	}

	/**
	 * @return the tINnumber
	 */
	public String getTINnumber() {
		return TINnumber;
	}

	/**
	 * @param tINnumber the tINnumber to set
	 */
	public void setTINnumber(String tINnumber) {
		TINnumber = tINnumber;
	}

}
