package Dialogs;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.SortOrder;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXList;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.SpringUtilities;
import Functions.UserInfo;

/**
 * @author Alvin Gonzales (2015-05-13)
 *
 */
public class dlgUserAccess extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 941386025469762778L;

	private Dimension size = new Dimension(600, 400);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JLabel lblModule;
	private JComboBox cmbModule;

	private JPanel pnlCenter;

	private JScrollPane scrollAvailablePrivileges;
	private JXList listAvailablePrivileges;

	private JPanel pnlMiddle;
	private JButton btnAdd;
	private JButton btnAddAll;
	private JButton btnRevoke;
	private JButton btnRevokeAll;

	private JScrollPane scrollGrantedPrivileges;
	private JXList listGrantedPrivileges;

	private JPanel pnlSouth;

	private Map<String, ArrayList<String>> mapAvailablePrivileges;
	private Map<String, ArrayList<String>> mapGrantedPrivileges;
	private Map<String, Boolean> mapChanges = new HashMap<String, Boolean>();;

	private String user_id;

	/**
	 * 
	 */
	public dlgUserAccess() {
		initGUI();
	}

	/**
	 * @param owner
	 */
	public dlgUserAccess(Frame owner) {
		super(owner);
		initGUI();
	}

	/**
	 * @param owner
	 */
	public dlgUserAccess(Dialog owner) {
		super(owner);
		initGUI();
	}

	/**
	 * @param owner
	 */
	public dlgUserAccess(Window owner) {
		super(owner);
		initGUI();
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public dlgUserAccess(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public dlgUserAccess(Frame owner, String title, String user_id) {
		super(owner, title);
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public dlgUserAccess(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public dlgUserAccess(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	/**
	 * @param owner
	 * @param modalityType
	 */
	public dlgUserAccess(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public dlgUserAccess(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public dlgUserAccess(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public dlgUserAccess(Dialog owner, String title, boolean modal) {
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
	public dlgUserAccess(Window owner, String title, ModalityType modalityType, String user_id) {
		super(owner, title, modalityType);

		this.user_id = user_id;
		mapAvailablePrivileges = menuAccess(FncGlobal.menuBar);
		mapGrantedPrivileges = new HashMap<String, ArrayList<String>>();

		initGUI();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public dlgUserAccess(Frame owner, String title, boolean modal,
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
	public dlgUserAccess(Dialog owner, String title, boolean modal,
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
	public dlgUserAccess(Window owner, String title, ModalityType modalityType,
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
					lblModule = new JLabel("Menu");
					pnlNorth.add(lblModule, BorderLayout.WEST);
				}
				{
					cmbModule = new JComboBox();
					pnlNorth.add(cmbModule, BorderLayout.CENTER);
					cmbModule.setActionCommand("Module");
					cmbModule.addActionListener(this);
					//cmbModule.addItemListener(this);
				}
			}
			{
				pnlCenter = new JPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(new SpringLayout());
				{
					scrollAvailablePrivileges = new JScrollPane();
					pnlCenter.add(scrollAvailablePrivileges);
					scrollAvailablePrivileges.setBorder(components.JTBorderFactory.createTitleBorder("Available Privileges"));
					scrollAvailablePrivileges.setPreferredSize(new Dimension(0, 0));
					{
						listAvailablePrivileges = new JXList();
						scrollAvailablePrivileges.setViewportView(listAvailablePrivileges);
						listAvailablePrivileges.setSortable(true);
						listAvailablePrivileges.setSortOrder(SortOrder.ASCENDING);
						listAvailablePrivileges.setSortsOnUpdates(true);
					}
				}
				{
					pnlMiddle = new JPanel();
					pnlCenter.add(pnlMiddle);
					pnlMiddle.setLayout(new OverlayLayout(pnlMiddle));
					{
						JPanel pnlNavigation = new JPanel();
						pnlMiddle.add(pnlNavigation, BorderLayout.CENTER);
						pnlNavigation.setAlignmentX(0.5f);
						pnlNavigation.setAlignmentY(0.5f);
						pnlNavigation.setMaximumSize(new Dimension(110, 105));
						{
							btnAdd = new JButton("Add->");
							pnlNavigation.add(btnAdd);
							btnAdd.setActionCommand("Add");
							btnAdd.setFont(btnAdd.getFont().deriveFont(Font.PLAIN));
							btnAdd.addActionListener(this);
						}
						{
							btnAddAll = new JButton("Add All->>");
							pnlNavigation.add(btnAddAll);
							btnAddAll.setActionCommand("AddAll");
							btnAddAll.setFont(btnAdd.getFont().deriveFont(Font.PLAIN));
							btnAddAll.addActionListener(this);
						}
						{
							btnRevoke = new JButton("<-Revoke");
							pnlNavigation.add(btnRevoke);
							btnRevoke.setActionCommand("Revoke");
							btnRevoke.setFont(btnAdd.getFont().deriveFont(Font.PLAIN));
							btnRevoke.addActionListener(this);
						}
						{
							btnRevokeAll = new JButton("<<-Revoke All");
							pnlNavigation.add(btnRevokeAll);
							btnRevokeAll.setActionCommand("RevokeAll");
							btnRevokeAll.setFont(btnAdd.getFont().deriveFont(Font.PLAIN));
							btnRevokeAll.addActionListener(this);
						}
					}
				}
				{
					scrollGrantedPrivileges = new JScrollPane();
					pnlCenter.add(scrollGrantedPrivileges);
					scrollGrantedPrivileges.setBorder(components.JTBorderFactory.createTitleBorder("Granted Privileges"));
					scrollGrantedPrivileges.setPreferredSize(new Dimension(0, 0));
					{
						listGrantedPrivileges = new JXList();
						scrollGrantedPrivileges.setViewportView(listGrantedPrivileges);
						listGrantedPrivileges.setModel(new DefaultComboBoxModel());
						listGrantedPrivileges.setSortOrder(SortOrder.ASCENDING);
					}
				}
				SpringUtilities.makeCompactGrid(pnlCenter, 1, 3, 0, 0, 0, 0, 1, 110, false);//XXX
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JPanel pnlNavigation = new JPanel();
					pnlSouth.add(pnlNavigation);
					pnlNavigation.setLayout(new GridLayout(1, 2, 5, 5));
					pnlNavigation.setAlignmentX(0.5f);
					pnlNavigation.setAlignmentY(0.5f);
					pnlNavigation.setMaximumSize(new Dimension(155, 30));
					{
						JButton btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.setActionCommand(btnSave.getText());
						btnSave.addActionListener(this);
					}
					{
						JButton btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}

		loadMenus();
		displayMenuItems((String) cmbModule.getSelectedItem());
		this.pack();
	}

	private void loadMenus() {
		ArrayList<String> listMenu = new ArrayList<String>();
		for(Entry<String, ArrayList<String>> entry : mapAvailablePrivileges.entrySet()){
			listMenu.add(entry.getKey());
		}
		Collections.sort(listMenu);
		cmbModule.setModel(new DefaultComboBoxModel(listMenu.toArray()));
	}

	private void displayMenuItems(String selectedMenu) {
		ArrayList<String> listMenu = mapAvailablePrivileges.get(selectedMenu);

		ArrayList<String> listAvailable = null;
		ArrayList<String> listGranted = null;

		if(mapChanges.get(selectedMenu) != null && mapChanges.get(selectedMenu)){
			listAvailable = mapAvailablePrivileges.get(selectedMenu);
			listGranted = mapGrantedPrivileges.get(selectedMenu);
		}else{
			listAvailable = new ArrayList<String>();
			listGranted = new ArrayList<String>();

			pgSelect dbPrivileges = new pgSelect();

			for(String privileges : listMenu){
				//System.out.printf("%s%n", privileges);

				String SQL = "select * from public.mf_privileges where emp_code = '"+ user_id +"' and module = '"+ selectedMenu.replace("'", "''") +"' and privileges = '"+ privileges.replace("'", "''") +"';";
				dbPrivileges.select(SQL);

				if(dbPrivileges.isNull()){
					listAvailable.add(privileges);
				}else{
					listGranted.add(privileges);
				}
			}
		}

		Collections.sort(listAvailable);
		listAvailablePrivileges.setModel(new DefaultComboBoxModel(listAvailable.toArray()));

		Collections.sort(listGranted);
		listGrantedPrivileges.setModel(new DefaultComboBoxModel(listGranted.toArray()));
	}

	private void sortList() {
		String module = (String) cmbModule.getSelectedItem();

		DefaultComboBoxModel modelAvailable = ((DefaultComboBoxModel) listAvailablePrivileges.getModel());

		ArrayList<String> listAvailable = new ArrayList<String>();
		for(int x=0; x < modelAvailable.getSize(); x++){
			listAvailable.add((String) modelAvailable.getElementAt(x));
		}
		Collections.sort(listAvailable);
		listAvailablePrivileges.setModel(new DefaultComboBoxModel(listAvailable.toArray()));

		mapAvailablePrivileges.put(module, listAvailable);


		DefaultComboBoxModel modelGranted = ((DefaultComboBoxModel) listGrantedPrivileges.getModel());

		ArrayList<String> listGranted = new ArrayList<String>();
		for(int x=0; x < modelGranted.getSize(); x++){
			listGranted.add((String) modelGranted.getElementAt(x));
		}
		Collections.sort(listGranted);
		listGrantedPrivileges.setModel(new DefaultComboBoxModel(listGranted.toArray()));

		mapGrantedPrivileges.put(module, listGranted);

		mapChanges.put(module, true);
	}

	private void add(boolean isAll) {
		DefaultComboBoxModel modelAvailable = ((DefaultComboBoxModel) listAvailablePrivileges.getModel());
		DefaultComboBoxModel modelGranted = ((DefaultComboBoxModel) listGrantedPrivileges.getModel());

		if(isAll){
			for(int x=0; x < modelAvailable.getSize(); x++){
				Object value = modelAvailable.getElementAt(x);
				modelGranted.addElement(value);
			}
			modelAvailable.removeAllElements();
		}else{
			List<String> selectedAvailableList = listAvailablePrivileges.getSelectedValuesList();
			for(String value : selectedAvailableList){

				modelAvailable.removeElement(value);
				modelGranted.addElement(value);
			}
		}

		sortList();
	}

	private void revoke(boolean isAll) {
		DefaultComboBoxModel modelAvailable = ((DefaultComboBoxModel) listAvailablePrivileges.getModel());
		DefaultComboBoxModel modelGranted = ((DefaultComboBoxModel) listGrantedPrivileges.getModel());

		if(isAll){
			for(int x=0; x < modelGranted.getSize(); x++){
				Object value = modelGranted.getElementAt(x);
				modelAvailable.addElement(value);
			}
			modelGranted.removeAllElements();
		}else{
			List<String> selectedAvailableList = listGrantedPrivileges.getSelectedValuesList();
			for(String value : selectedAvailableList){

				modelGranted.removeElement(value);
				modelAvailable.addElement(value);
			}
		}
		sortList();
	}

	private void save(String module) {//XXX Save
		String SQL = null;
		
		String emp_code = UserInfo.EmployeeCode;

		pgSelect db = new pgSelect();

		System.out.printf("   Available%n");
		for(String privilege : mapAvailablePrivileges.get(module)){//**EDITED BY JED VICEDO 2019-09-30 : ADDITIONAL PARAMETER**//
			//SQL = "SELECT public.sp_save_user_access('"+ module.replace("'", "''") +"', '"+ privilege.replace("'", "''") +"', ARRAY"+ FncGlobal.mapParent.get(privilege/*.replace("'", "''")*/) +"::VARCHAR[], '"+ user_id +"', FALSE);";
			SQL = "SELECT public.sp_save_user_access('"+ module.replace("'", "''") +"', '"+ privilege.replace("'", "''") +"', ARRAY"+ FncGlobal.mapParent.get(privilege/*.replace("'", "''")*/) +"::VARCHAR[], '"+ user_id +"', '"+ emp_code +"', FALSE);";

			//System.out.printf("     %s%n", privilege);
			//System.out.printf("SQL: %s%n", SQL);
			FncSystem.out("Display sql for Saving of User Access", SQL);
			db.select(SQL);
		}

		System.out.printf("%n%n   Granted%n");
		for(String privilege : mapGrantedPrivileges.get(module)){//**EDITED BY JED VICEDO 2019-09-30 : ADDITIONAL PARAMETER**//
			//SQL = "SELECT public.sp_save_user_access('"+ module.replace("'", "''") +"', '"+ privilege.replace("'", "''") +"', ARRAY"+ FncGlobal.mapParent.get(privilege/*.replace("'", "''")*/) +"::VARCHAR[], '"+ user_id +"', TRUE);";
			SQL = "SELECT public.sp_save_user_access('"+ module.replace("'", "''") +"', '"+ privilege.replace("'", "''") +"', ARRAY"+ FncGlobal.mapParent.get(privilege/*.replace("'", "''")*/) +"::VARCHAR[], '"+ user_id +"', '"+ emp_code +"', TRUE);";
			
			//System.out.printf("     %s%n", privilege);
			//System.out.printf("SQL: %s%n", SQL);
			FncSystem.out("Display SQL For Saving of User Access", SQL);
			db.select(SQL);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Module")){
			String selectedMenu = (String) ((JComboBox)arg0.getSource()).getSelectedItem();
			displayMenuItems(selectedMenu);
		}

		if(actionCommand.equals("Add")){
			add(false);
		}
		if(actionCommand.equals("AddAll")){
			add(true);
		}
		if(actionCommand.equals("Revoke")){
			revoke(false);
		}
		if(actionCommand.equals("RevokeAll")){
			revoke(true);
		}

		if(actionCommand.equals("Save")){
			for(Entry<String, Boolean> entry : mapChanges.entrySet()){
				if(entry.getValue()){
					String module = entry.getKey();
					//System.out.printf("Module: %s%n", module);
					save(module);
				}
			}
			this.dispose();
		}
		if(actionCommand.equals("Cancel")){
			this.dispose();
		}

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

}
