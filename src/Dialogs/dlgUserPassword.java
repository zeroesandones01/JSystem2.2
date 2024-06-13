package Dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import Database.pgUpdate;
import Functions.UserInfo;
import interfaces._GUI;

/**
 * @author Jessa Herrera (2016-07-13)
 *
 */
public class dlgUserPassword extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 941386025469762778L;

	private Dimension size = new Dimension(400, 100);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPasswordField txtNewPassword;

	private JPanel pnlSouth;


	private String username;

	/**
	 * 
	 */
	public dlgUserPassword() {
		initGUI();
	}

	/**
	 * @param owner
	 */
	public dlgUserPassword(Frame owner) {
		super(owner);
		initGUI();
	}

	/**
	 * @param owner
	 */
	public dlgUserPassword(Dialog owner) {
		super(owner);
		initGUI();
	}

	/**
	 * @param owner
	 */
	public dlgUserPassword(Window owner) {
		super(owner);
		initGUI();
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public dlgUserPassword(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public dlgUserPassword(Frame owner, String title, String username) {
		super(owner, title);
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public dlgUserPassword(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public dlgUserPassword(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	/**
	 * @param owner
	 * @param modalityType
	 */
	public dlgUserPassword(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public dlgUserPassword(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public dlgUserPassword(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public dlgUserPassword(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modalityType
	 * 
	 * edited by Alvin Gonzales (2015-03-13)
	 */
	public dlgUserPassword(Window owner, String title, ModalityType modalityType, String username) {
		super(owner, title, modalityType);

		this.username = username;

		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public dlgUserPassword(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public dlgUserPassword(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modalityType
	 * @param gc
	 */
	public dlgUserPassword(Window owner, String title, ModalityType modalityType,
			GraphicsConfiguration gc) {
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
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setPreferredSize(new Dimension(0, 25));
				{
					/*txtPassword = new JXTextField();
					pnlNorth.add(txtPassword, BorderLayout.CENTER);
					txtPassword.setActionCommand("Password");
					txtPassword.addActionListener(this);*/
					txtNewPassword = new JPasswordField("Select Password", 16);
					pnlNorth.add(txtNewPassword, BorderLayout.CENTER);
					txtNewPassword.setEchoChar((char) 0);
					txtNewPassword.setName("txtNewPassword");
					txtNewPassword.addKeyListener(new KeyAdapter(){
						public void keyReleased(KeyEvent arg0) {
							String password = "";
							for(int x=0; x<txtNewPassword.getPassword().length; x++){
								password = password + txtNewPassword.getPassword()[x];
							}
						}
					});
					txtNewPassword.addFocusListener(new FocusAdapter(){
						public void focusLost(FocusEvent arg0) {
							String password = "";
							for(int x=0; x<txtNewPassword.getPassword().length; x++){
								password = password + txtNewPassword.getPassword()[x];
							}
						}
					});
				}
			}
			
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JPanel pnlNavigation = new JPanel();
					pnlSouth.add(pnlNavigation);
					pnlNavigation.setLayout(new GridLayout(1, 10, 3, 3));
					pnlNavigation.setAlignmentX(0.5f);
					pnlNavigation.setAlignmentY(0.5f);
					pnlNavigation.setMaximumSize(new Dimension(400, 30));
					
					{
						JCheckBox chkboxBatch = new JCheckBox("Show Pword");
						pnlNavigation.add(chkboxBatch);
						chkboxBatch.setSelected(true);
						chkboxBatch.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								if (e.getStateChange() == ItemEvent.SELECTED) {
									txtNewPassword.setEchoChar((char) 0);
						        } else {
						            //txtNewPassword.setEchoChar("");
						        }
							}
						});
					}
					{
						JButton btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.setName("btnSave");
						btnSave.setPreferredSize(new Dimension(50, 25));
						btnSave.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent arg0) {
								
								String password = "";
								for(int x=0; x<txtNewPassword.getPassword().length; x++){
									password = password + txtNewPassword.getPassword()[x];
								}
								
								if(toSave()){
										save(username, password);
										dispose();
								}else{
									JOptionPane.showMessageDialog(getContentPane(), "Please fill-up required fields.", "Save", JOptionPane.WARNING_MESSAGE);
								}
							}
						});
					}
					{
						JButton btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.setName("btnCancel");
						btnCancel.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent arg0) {
								dispose();
							}
						});
					}
				}
			}
		}

	}



	private void save(String username, String password) {//XXX Save
		String SQL2 = "INSERT INTO mf_audit_trail(\n" + 
				"              system_id, activity, user_code, date_upd, entity_id, \n" + 
				"              client_seqno, projcode, pbl_id, doc_id, doc_no, remarks)\n" + 
				"      VALUES ('AD', 'Change Password', '"+UserInfo.EmployeeCode+"', NOW(), NULL, \n" + 
				"              NULL, NULL, NULL, NULL, NULL, '"+username+"');";
		
		pgUpdate db2 = new pgUpdate();
		db2.executeUpdate(SQL2, false);
		db2.commit();
		
		String SQL = "ALTER ROLE "+username+" WITH ENCRYPTED PASSWORD '"+password+"'";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, false);
		db.commit();
		
		JOptionPane.showMessageDialog(this.getContentPane(), "Password successfully updated.", "Save", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Escape")){
			this.dispose();
		}
	}

	/**
	 * Added by Alvin Gonzales (2015-05-13) due to not displaying granted privileges after saving
	 * 
	 */
	public static Map<String, ArrayList<String>> menuAccess(JMenuBar menuBar) {
		List<String> listException = Arrays.asList(new String[]{"File", "Window", "Help"});
		Map<String, ArrayList<String>> mapMenu = new HashMap<String, ArrayList<String>>();

		for(int x=0; x < menuBar.getMenuCount(); x++){
			try {
				JMenu menu = menuBar.getMenu(x);
				String module = menu.getText();

				//System.out.printf("%s%n", menuBar.getMenu(x).getText());
				if(!mapMenu.containsKey(module)){
					mapMenu.put(module, new ArrayList<String>());
				}

				if(!listException.contains(module)){
					menoLooper(mapMenu, module, menu);
				}
			} catch (NullPointerException e) { }
		}

		return mapMenu;
	}

	/**
	 * Added by Alvin Gonzales (2015-05-13) due to not displaying granted privileges after saving
	 * 
	 */
	public static void menoLooper(Map<String, ArrayList<String>> mapMenu, String menuText, JMenu menu) {
		for(Component comp : menu.getMenuComponents()){
			String simpleName = comp.getClass().getSimpleName();

			if(simpleName.equals("JMenu")){
				JMenu menuParent = (JMenu) comp;

				menoLooper(mapMenu, menuText, menuParent);
			}else if(simpleName.equals("JMenuItem")){
				JMenuItem menuItem = ((JMenuItem) comp);
				String menuitemText = menuItem.getText();

				ArrayList<String> listParent = new ArrayList<String>();
				//System.out.printf("Menu Item: %s%n", menuItem.getText());

				loopParent(listParent, menuItem);

				ArrayList<String> listMenu = mapMenu.get(menuText);
				listMenu.add(menuitemText);

				mapMenu.put(menuText, listMenu);
			}else{
				//System.out.printf("Other: %s%n", simpleName);
			}
		}
	}

	/**
	 * Added by Alvin Gonzales (2015-05-13) due to not displaying granted privileges after saving
	 * 
	 */
	public static void loopParent(ArrayList<String> listParent, Component menu) {
		try {
			JPopupMenu menuPopup = (JPopupMenu) menu.getParent();
			JMenu actMenu = (JMenu) menuPopup.getInvoker();

			//System.out.printf("                              %-30s%n", actMenu.getText());
			listParent.add(String.format("'%s'", actMenu.getText()));

			loopParent(listParent, actMenu);
		} catch (ClassCastException e) {
			//e.printStackTrace();
		}
	}
	private Boolean toSave(){
		
		String password = "";
		for(int x=0; x<txtNewPassword.getPassword().length; x++){
			password = password + txtNewPassword.getPassword()[x];
		}
		
		if(password.equals("")){
			return false;
		}else{
			return true;
		}
	}
}
