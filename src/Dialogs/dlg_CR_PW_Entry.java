package Dialogs;

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
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import interfaces._GUI;

public class dlg_CR_PW_Entry extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816263625314557883L;
	private Dimension size = new Dimension(300, 100);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private JPanel pnlPassword;
	private JLabel lblPassword;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnOK;
	private JPasswordField txtPassword;
	
	private String password;

	public dlg_CR_PW_Entry() {
		initGUI();
	}

	public dlg_CR_PW_Entry(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_CR_PW_Entry(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_CR_PW_Entry(Window owner) {
		super(owner);
		initGUI();
	}

	public dlg_CR_PW_Entry(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_CR_PW_Entry(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_CR_PW_Entry(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_CR_PW_Entry(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_CR_PW_Entry(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_CR_PW_Entry(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_CR_PW_Entry(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlg_CR_PW_Entry(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_CR_PW_Entry(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_CR_PW_Entry(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlg_CR_PW_Entry(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_CR_PW_Entry(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
					pnlPassword = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlPassword);
					{
						lblPassword = new JLabel("Password");
						pnlPassword.add(lblPassword, BorderLayout.WEST);
						lblPassword.setPreferredSize(new Dimension(80, 37));
					}
					{
						txtPassword = new JPasswordField(30);
						pnlPassword.add(txtPassword, BorderLayout.CENTER);
						txtPassword.setBounds(157, 39, 205, 25);
						txtPassword.setName("txtNewPassword");
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
					pnlNavigation.setMaximumSize(new Dimension(100, 30));
					{
						btnOK = new JButton("OK");
						pnlNavigation.add(btnOK);
						btnOK.addActionListener(this);
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
			setPassword();
			dispose();
		}
		if(action.equals("Cancel")){
			setPassword();
			dispose();
		}
		if(action.equals("Escape")){
			setPassword();
			dispose();
		}
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(){
		String password = "";
		
		for(int x=0; x<txtPassword.getPassword().length; x++){
			password = password + txtPassword.getPassword()[x];
		}
		
		this.password = password;
	}
	
}
