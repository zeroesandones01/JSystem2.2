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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import Database.pgSelect;
import Functions.UserInfo;
import components._JXTextField;

/**
 * @author Alvin Gonzales
 */
public class dlgOpenHouseImport extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6050842694669369269L;

	private Dimension size = new Dimension(300, 300);

	_JXTextField txtHost;
	_JXTextField txtDatabase;
	_JXTextField txtUsername;
	JPasswordField txtPassword;

	public dlgOpenHouseImport() {
		initGUI();
	}

	public dlgOpenHouseImport(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgOpenHouseImport(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgOpenHouseImport(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgOpenHouseImport(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgOpenHouseImport(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgOpenHouseImport(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgOpenHouseImport(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgOpenHouseImport(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgOpenHouseImport(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgOpenHouseImport(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgOpenHouseImport(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgOpenHouseImport(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgOpenHouseImport(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgOpenHouseImport(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgOpenHouseImport(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
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
			JPanel pnlMain = new JPanel(new BorderLayout(10, 0));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlWest = new JPanel(new GridLayout(6, 1, 0, 10));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				pnlWest.setPreferredSize(new Dimension(80, 0));
				{
					JLabel lblHost = new JLabel("Host");
					pnlWest.add(lblHost);
				}
				{
					JLabel lblDatabase = new JLabel("Database");
					pnlWest.add(lblDatabase);
				}
				{
					JLabel lblUsername = new JLabel("Username");
					pnlWest.add(lblUsername);
				}
				{
					JLabel lblPassword = new JLabel("Password");
					pnlWest.add(lblPassword);
				}
			}
			{
				JPanel pnlCenter = new JPanel(new GridLayout(6, 1, 0, 10));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					txtHost = new _JXTextField("Host");
					pnlCenter.add(txtHost);
					txtHost.setEditable(true);
				}
				{
					txtDatabase = new _JXTextField("Database");
					pnlCenter.add(txtDatabase);
					txtDatabase.setEditable(true);
				}
				{
					txtUsername = new _JXTextField("Username");
					pnlCenter.add(txtUsername);
					txtUsername.setEditable(true);
				}
				{
					txtPassword = new JPasswordField();
					pnlCenter.add(txtPassword);
					txtPassword.setEditable(true);
				}
			}
			{
				JPanel pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JPanel pnlNav = new JPanel(new GridLayout(1, 2, 5, 0));
					pnlSouth.add(pnlNav);
					pnlNav.setAlignmentX(0.5f);
					pnlNav.setAlignmentY(0.5f);
					pnlNav.setMaximumSize(new Dimension(200, 30));
					{
						JButton btnOK = new JButton("OK");
						pnlNav.add(btnOK);
						btnOK.setMnemonic(KeyEvent.VK_O);
						btnOK.addActionListener(this);
					}
					{
						JButton btnCancel = new JButton("Cancel");
						pnlNav.add(btnCancel);
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(this);
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("OK")){
			String host = txtHost.getText();
			String database = txtDatabase.getText();
			String username = txtUsername.getText();
			String password = new String(txtPassword.getPassword());
			String user = String.format("%s %s", UserInfo.FirstName, UserInfo.LastName);

			System.out.printf("Host: %s%n", host);
			System.out.printf("Database: %s%n", database);
			System.out.printf("Username: %s%n", username);
			System.out.printf("Password: %s%n", password);

			String SQL = "SELECT sp_open_house_import('"+ host +"', '"+ database +"', '"+ user +"');";
			System.out.printf("%nSQL: %s%n", SQL);

			pgSelect db = new pgSelect();
			db.select(SQL, "Import", username, password, true);
			if(db.isNotNull()){
				JOptionPane.showMessageDialog(this, db.getResult()[0][0], "Import", JOptionPane.INFORMATION_MESSAGE);
			}

		}
		if(action.equals("Cancel")){
			this.dispose();
		}

		if(action.equals("Escape")){
			this.dispose();
		}
	}

}
