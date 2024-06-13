package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
//import Main.CheckBoxNode;
//import Main.CheckBoxNodeRenderer;
//import Main.classes.FrmUserAccess_DB;
import components._JInternalFrame;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class UserAccess extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 6762307071762175528L;

	Dimension frameSize = new Dimension(500, 500);
	static String title = "User Access";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblEmployee;
	private JLabel lblCompany;
	private JLabel lblDivision;
	private JLabel lblDepartment;

	private JPanel pnlNorthEast;
	private JPanel pnlEmployee;
	private _JLookup lookupEmployee;
	private _JXTextField txtEmployee;

	private JPanel pnlCompany;
	private _JXTextField txtCompanyID;
	private _JXTextField txtCompany;

	private JPanel pnlDivision;
	private _JXTextField txtDivisionID;
	private _JXTextField txtDivisionName;

	private JPanel pnlDepartment;
	private _JXTextField txtDeptID;
	private _JXTextField txtDeptName;

	private JPanel pnlCenter;
	private JScrollPane scrollModuleList;
	private JTree treeMenu;
	private DefaultTreeModel modelMenu;
	private DefaultMutableTreeNode root;

	private JPopupMenu menuRightClick;
	private JMenuItem itemCheck;
	private JMenuItem itemUnCheck;

	private JPanel pnlSouth;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	public UserAccess() {
		super(title, false, true, false, true);
		initGUI();
	}

	public UserAccess(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	@Override

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setLayout(new BorderLayout());

		{
			menuRightClick = new JPopupMenu(this.getTitle());
			getContentPane().add(menuRightClick);
			{
				itemCheck = new JMenuItem("Check All");
				menuRightClick.add(itemCheck);
				itemCheck.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt) {
						TreeNode node = (TreeNode) treeMenu.getSelectionPath().getLastPathComponent();
						TreePath parent = treeMenu.getSelectionPath();
						String[] menuString = node.toString().split("~@~");

						treeMenu.getModel().valueForPathChanged(parent, new CheckBoxNode(menuString[0].trim(), menuString[1].trim(), true));

						if (node.getChildCount() >= 0) {
							eto(parent, node);
						}
					}
					public void eto(TreePath parent, TreeNode node){
						for(int x=0; x < node.getChildCount(); x++){
							TreeNode n = node.getChildAt(x);
							//System.out.println(n);
							String[] menuString2 = n.toString().split("~@~");

							//TreePath path = parent.pathByAddingChild(n);
							TreePath path = getPath(n);

							treeMenu.getModel().valueForPathChanged(path, new CheckBoxNode(menuString2[0].trim(), menuString2[1].trim(), true));

							if(n.getChildCount() > 0){
								eto(path, n);
							}
						}
					}
				});
			}
			{
				itemUnCheck = new JMenuItem("Uncheck All");
				menuRightClick.add(itemUnCheck);
				itemUnCheck.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt) {
						TreeNode node = (TreeNode) treeMenu.getSelectionPath().getLastPathComponent();
						TreePath parent = treeMenu.getSelectionPath();
						String[] menuString = node.toString().split("~@~");

						treeMenu.getModel().valueForPathChanged(parent, new CheckBoxNode(menuString[0].trim(), menuString[1].trim(), false));

						if (node.getChildCount() >= 0) {
							eto(parent, node);
						}
					}
					public void eto(TreePath parent, TreeNode node){
						for(int x=0; x < node.getChildCount(); x++){
							TreeNode n = node.getChildAt(x);
							//System.out.println(n);
							String[] menuString2 = n.toString().split("~@~");

							//TreePath path = parent.pathByAddingChild(n);
							TreePath path = getPath(n);

							treeMenu.getModel().valueForPathChanged(path, new CheckBoxNode(menuString2[0].trim(), menuString2[1].trim(), false));

							if(n.getChildCount() > 0){
								eto(path, n);
							}
						}
					}
				});
			}
		}
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(EMPTY_BORDER);
			{
				pnlNorthWest = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				{
					lblEmployee = new JLabel("*Employee");
					pnlNorthWest.add(lblEmployee);
				}
				{
					lblCompany = new JLabel("Company");
					pnlNorthWest.add(lblCompany);
				}
				{
					lblDivision = new JLabel("Division");
					pnlNorthWest.add(lblDivision);
				}
				{
					lblDepartment = new JLabel("Department");
					pnlNorthWest.add(lblDepartment);
				}
			}
			{
				pnlNorthEast = new JPanel(new GridLayout(4, 1, 3, 3));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				{
					pnlEmployee = new JPanel(new BorderLayout(5, 5));
					pnlNorthEast.add(pnlEmployee);
					{
						lookupEmployee = new _JLookup(null, "Select Employee", 0);
						pnlEmployee.add(lookupEmployee, BorderLayout.WEST);
						lookupEmployee.setPreferredSize(new Dimension(100, 0));
						lookupEmployee.setLookupSQL(sqlEmployee());
						lookupEmployee.addLookupListener(new LookupListener() {

							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									String emp_name = (String) data[1];
									String co_id = (String) data[2];
									String company_name = (String) data[3];
									String div_id = (String) data[4];
									String div_name = (String) data[5];
									String dept_id = (String) data[6];
									String dept_name = (String) data[7];

									txtEmployee.setText(emp_name);
									txtCompanyID.setText(co_id);
									txtCompany.setText(company_name);
									txtDivisionID.setText(div_id);
									txtDivisionName.setText(div_name);
									txtDeptID.setText(dept_id);
									txtDeptName.setText(dept_name);

									treeMenu.removeAll();

								}
							}
						});
					}
					{
						txtEmployee = new _JXTextField("Employee Name");
						pnlEmployee.add(txtEmployee);
					}
				}
				{
					pnlCompany = new JPanel(new BorderLayout(5, 5));
					pnlNorthEast.add(pnlCompany);
					{
						txtCompanyID = new _JXTextField("ID");
						pnlCompany.add(txtCompanyID, BorderLayout.WEST);
						txtCompanyID.setPreferredSize(new Dimension(100, 0));
						txtCompanyID.setHorizontalAlignment(JXTextField.CENTER);
					}
					{
						txtCompany = new _JXTextField("Company Name");
						pnlCompany.add(txtCompany);
					}
				}
				{
					pnlDivision = new JPanel(new BorderLayout(5, 5));
					pnlNorthEast.add(pnlDivision);
					{
						txtDivisionID = new _JXTextField("ID");
						pnlDivision.add(txtDivisionID, BorderLayout.WEST);
						txtDivisionID.setPreferredSize(new Dimension(100, 0));
						txtDivisionID.setHorizontalAlignment(JXTextField.CENTER);
					}
					{
						txtDivisionName = new _JXTextField("Division Name");
						pnlDivision.add(txtDivisionName);
					}
				}
				{
					pnlDepartment = new JPanel(new BorderLayout(5, 5));
					pnlNorthEast.add(pnlDepartment);
					{
						txtDeptID = new _JXTextField("ID");
						pnlDepartment.add(txtDeptID, BorderLayout.WEST);
						txtDeptID.setPreferredSize(new Dimension(100, 0));
						txtDeptID.setHorizontalAlignment(JXTextField.CENTER);
					}
					{
						txtDeptName = new _JXTextField("Department Name");
						pnlDepartment.add(txtDeptName);
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			this.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setBorder(EMPTY_BORDER);

			{
				scrollModuleList = new JScrollPane();
				pnlCenter.add(scrollModuleList, BorderLayout.CENTER);
				scrollModuleList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				{
					treeMenu = new JTree(displayMenu());
					scrollModuleList.setViewportView(treeMenu);
					treeMenu.setCellRenderer(new CheckBoxNodeRenderer());
					treeMenu.setCellEditor(new CheckBoxNodeEditor(treeMenu));
					treeMenu.setShowsRootHandles(true);
					treeMenu.setRootVisible(true);
				}
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new GridLayout(1, 3, 5, 5));
			pnlSouth.setBorder(EMPTY_BORDER);
			{
				btnEdit = new JButton("Edit");
				pnlSouth.add(btnEdit);
				btnEdit.setActionCommand(btnEdit.getText());
				btnEdit.setMnemonic(KeyEvent.VK_E);
				btnEdit.addActionListener(this);
			}
			{
				btnSave = new JButton("Save");
				pnlSouth.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.setMnemonic(KeyEvent.VK_S);
				btnSave.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlSouth.add(btnCancel);
				btnCancel.setActionCommand(btnCancel.getText());
				btnCancel.addActionListener(this);
			}
		}
		btnState(true, false, false);
	}//END OF INIT GUI

	private void btnState(Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private String sqlEmployee(){
		return "select \n" + 
				"TRIM(a.emp_code) AS \"Emp. Code\", TRIM(b.entity_name) as \"Emp. Name\",\n" + 
				"TRIM(a.co_id) as \"CO. ID\", TRIM(c.company_name) as \"Company Name\",\n" + 
				"TRIM(a.div_code) as \"Div. ID\", TRIM(d.division_name) as \"Division Name\",\n" + 
				"TRIM(a.dept_code) as \"Dept. Code\", TRIM(e.dept_name) as \"Dept. Name\"\n" + 
				"from em_employee a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"left join mf_company c on c.co_id = a.co_id\n" + 
				"left join mf_division d on d.division_code = a.div_code\n" + 
				"left join mf_department e on e.dept_code = a.dept_code";
	}

	private void editUserAccess(){
		if(lookupEmployee.getValue() == null){
			JOptionPane.showMessageDialog(scrollModuleList, "Please select Employee");
		}else{
			btnState(false, true, true);
		}
	}

	private void saveUserAccess(){

	}

	private void cancelUserAccess(){
		lookupEmployee.setValue(null);
		txtEmployee.setText("");
		txtCompanyID.setText("");
		txtCompany.setText("");
		txtDivisionID.setText("");
		txtDivisionName.setText("");
		txtDeptID.setText("");
		txtDeptName.setText("");
		btnState(true, false, false);
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("Edit")){
			editUserAccess();
		}
		if(actionCommand.equals("Save")){

		}
		if(actionCommand.equals("Cancel")){
			cancelUserAccess();
		}
	}

	private Object[] displayMenu(){

		pgSelect db = new pgSelect();
		String sql = "select module_desc from mf_system_modules;";
		db.select(sql);

		ArrayList<String> mainMenu = new ArrayList<String>();

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				mainMenu.add((String) db.getResult()[x][0]);
				for(int y = 0; y< mainMenu.toArray().length; y++){

				}
			}
			return mainMenu.toArray();
		}else{
			return null;
		}
	}

	private DefaultMutableTreeNode root(){
		pgSelect db = new pgSelect();
		String sql = "select module_desc from mf_system_modules;";
		db.select(sql);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode();

		if(db.isNotNull()){
			for(int x= 0; x< db.getRowCount(); x++){
				root.add((MutableTreeNode) db.getResult()[x][0]);
			}
			return root;
		}else{
			return null;
		}
	}

	private void refreshMenuTree(){
		pgSelect db = new pgSelect();
		String sql = "select module_desc from mf_system_modules";
		db.select(sql);
		FncSystem.out("Display Me", sql);

		for(int x=0; x< db.getRowCount(); x++){
			root.add((MutableTreeNode) db.getResult()[x][0]);
		}
		modelMenu = new DefaultTreeModel(root);

	}

	private Object[] getParentDesc(String module_id){
		pgSelect db = new pgSelect();
		String sql = "select unnest(parent_desc) from mf_system_parent where parent_id = '"+module_id+"'";
		db.select(sql);

		FncSystem.out("Display parent desc", sql);
		ArrayList<String> submenu = new ArrayList<String>();
		if(db.isNotNull()){
			for (int x=0; x<db.getRowCount(); x++){
				submenu.add((String) db.getResult()[x][0]);
			}
			return submenu.toArray();
		}else{
			return null;
		}
	}

	private JTree getTreeMenu() {
		if(treeMenu == null) {
			treeMenu = new JTree();
		}
		return treeMenu;
	}

	///XXX DITO SIMULA

	private boolean checkMenu(String menu_directory, String emp_code){
		String[] menu = menu_directory.split("//");
		int index_to_check = menu.length - 1;
		String menu_to_check = menu[index_to_check];
		String sql = "select distinct unnest(privileges)\n" + 
				"from mf_user_privileges\n" + 
				"where emp_code = '"+emp_code+"' ";
		FncSystem.out("Display sql1", sql);
		/*for(int x=0; x < menu.length; x++){
			sql = sql + "and trim(split_part(menu_directory, '//', "+ (x+1) +")) = '"+menu[x].replace("'", "''")+"' ";
		}
		sql = sql + "and trim(split_part(menu_directory, '//', "+menu.length+")) != ''";*/

		pgSelect db = new pgSelect();
		db.select(sql);
		ArrayList<String> mainMenu = new ArrayList<String>();

		for(int x=0; x < db.Result.length; x++){
			mainMenu.add((String) db.getResult()[x][0]);
		}
		return mainMenu.contains(menu_to_check);
	}

	private TreePath getPath(TreeNode node) {
		List<TreeNode> list = new ArrayList<TreeNode>();

		// Add all nodes to list
		while (node != null) {
			list.add(node);
			node = node.getParent();
		}
		Collections.reverse(list);

		// Convert array of nodes to TreePath
		return new TreePath(list.toArray());
	}

	private void refreshMenuTree(final JMenuBar menuBar, final String emp_code){
		new Thread(new Runnable() {
			public void run() {
				//glass.start();
				root = new DefaultMutableTreeNode("Menu");
				for (int x = 0; x < menuBar.getMenuCount(); x++) {
					String home = menuBar.getMenu(x).getText().trim();
					if (!home.equals("Window")) {
						DefaultMutableTreeNode tr = new DefaultMutableTreeNode(new CheckBoxNode(home, home + "//", checkMenu(home, emp_code)));
						root.add(tr);
						hasMenuComponent(tr, menuBar.getMenu(x), home, emp_code);
					}
				}
				modelMenu = new DefaultTreeModel(root);
				treeMenu.setModel(modelMenu);
				//glass.stop();
			}
		}, "Display Menu Access").start();
	}

	private void hasMenuComponent(DefaultMutableTreeNode tr, JMenu menu, String home_parent, String emp_id){
		for(int y=0; y < menu.getMenuComponentCount(); y++){
			String sub_home = "";
			try {
				home_parent = home_parent + ((JMenu)menu.getParent()).getText().trim();
			} catch (ClassCastException e1) { }
			try {
				sub_home = menu.getItem(y).getText().trim();
				//System.out.println("   ∟----┤ "+ home_parent + "//" + sub_home);
				DefaultMutableTreeNode tr2 = new DefaultMutableTreeNode(new CheckBoxNode(sub_home, home_parent + "//" + sub_home+"//", checkMenu(home_parent + "//" + sub_home, emp_id)));
				tr.add(tr2);

				if(isMenu(menu.getItem(y).getClass())){
					hasMenuComponent(tr2, (JMenu) menu.getItem(y), home_parent + "//" + sub_home, emp_id);
				}
			} catch (NullPointerException e) { }
		}
	}

	private void hasMenuComponent2(TreeNode tr, String emp_id, ArrayList<Boolean> toSave){//XXX
		for(int x=0; x < tr.getChildCount(); x++){
			Object[] menuString2 = tr.getChildAt(x).toString().split("~@~");
			if(new Boolean(menuString2[2].toString().trim())){
				if(!checkMenu(menuString2[1].toString().trim(), emp_id)){
					//FrmUserAccess_DB.saveUserAccessMenu(menuString2[1].toString().trim(), emp_id, toSave);
				}

				if(((DefaultMutableTreeNode)tr.getChildAt(x)).getChildCount() > 0){
					hasMenuComponent2(((DefaultMutableTreeNode)tr.getChildAt(x)), emp_id, toSave);
				}
			}else{
				if(checkMenu(menuString2[1].toString().trim(), emp_id)){
					//FrmUserAccess_DB.deleteUserAccessMenu(menuString2[1].toString().trim(), emp_id, toSave);
				}
			}
		}
	}

	private Boolean isMenu(Class<?> menuItem){
		return menuItem.equals(JMenu.class);
	}
}//XXX end

class CheckBoxNode {
	String menu;
	String menu_directory;
	boolean selected;

	public CheckBoxNode(String menu, String menu_directory, boolean selected) {
		this.menu = menu;
		this.menu_directory = menu_directory;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean newValue) {
		selected = newValue;
	}
	public String getText() {
		return menu;
	}
	public void setText(String newValue) {
		menu = newValue;
	}
	public String getMenuDirectory() {
		return menu_directory;
	}
	public void setMenuDirectory(String menu_directory) {
		this.menu_directory = menu_directory;
	}
	public String toString() {
		return menu + "~@~" + menu_directory + "~@~" + selected;
	}
}

class PopupTriggerListener extends MouseAdapter {
	JPopupMenu menuRightClick;
	public PopupTriggerListener(JPopupMenu menuRightClick){
		this.menuRightClick = menuRightClick;
	}
	public void mousePressed(MouseEvent ev) {
		if (ev.isPopupTrigger() && ((JTree) ev.getSource()).getRowForLocation(ev.getX(),ev.getY()) != (-1)){
			((JTree) ev.getSource()).setSelectionRow(((JTree) ev.getSource()).getRowForLocation(ev.getX(),ev.getY()));

			if(((JTree) ev.getSource()).isEditable()){
				TreeNode node = (TreeNode) ((JTree) ev.getSource()).getPathForLocation(ev.getX(), ev.getY()).getLastPathComponent();
				if(node.getChildCount() > 0){
					menuRightClick.show(ev.getComponent(), ev.getX(), ev.getY());
				}
			}
		}
	}
	public void mouseReleased(MouseEvent ev) {
		if (ev.isPopupTrigger() && ((JTree) ev.getSource()).getRowForLocation(ev.getX(),ev.getY()) != (-1)){
			((JTree) ev.getSource()).setSelectionRow(((JTree) ev.getSource()).getRowForLocation(ev.getX(),ev.getY()));

			if(((JTree) ev.getSource()).isEditable()){
				TreeNode node = (TreeNode) ((JTree) ev.getSource()).getPathForLocation(ev.getX(), ev.getY()).getLastPathComponent();
				if(node.getChildCount() > 0){
					menuRightClick.show(ev.getComponent(), ev.getX(), ev.getY());
				}
			}
		}
	}
	public void mouseClicked(MouseEvent ev) {
		if(((JTree) ev.getSource()).getRowForLocation(ev.getX(),ev.getY()) == (-1)) {
			((JTree) ev.getSource()).clearSelection();
		}
	}
}

class CheckBoxNodeRenderer extends JCheckBox implements TreeCellRenderer {
	private static final long serialVersionUID = -4870515086785816304L;
	//private JCheckBox leafRenderer = new JCheckBox();
	private Color selectionForeground, selectionBackground, textForeground, textBackground;

	public CheckBoxNodeRenderer() {//XXX
		Font fontValue = UIManager.getFont("Tree.font");

		if (fontValue != null) {
			setFont(fontValue);
		}

		Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
		setFocusPainted((booleanValue != null) && (booleanValue.booleanValue()));
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);

		setText(stringValue);
		setSelected(selected);
		setEnabled(tree.isEnabled());

		try {
			//System.out.println(tree.getSelectionPaths().length);
		} catch (NullPointerException e) { }

		if(selected){
			setForeground(selectionForeground);
			setBackground(selectionBackground);
		}else{
			setForeground(textForeground);
			setBackground(textBackground);
		}

		if((value != null) && (value instanceof DefaultMutableTreeNode)){
			Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
			if (userObject instanceof CheckBoxNode) {
				CheckBoxNode node = (CheckBoxNode) userObject;
				setActionCommand(node.getMenuDirectory());
				setText(node.getText());
				setSelected(node.isSelected());
			}
		}
		return this;
	}
}

class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {
	private static final long serialVersionUID = -8243049325929606121L;
	CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
	JTree tree;

	public CheckBoxNodeEditor(JTree tree) {//XXX
		this.tree = tree;
	}

	public Object getCellEditorValue() {
		JCheckBox checkbox = renderer;
		CheckBoxNode checkBoxNode = new CheckBoxNode(checkbox.getText(), checkbox.getActionCommand(), checkbox.isSelected());
		return checkBoxNode;
	}

	public boolean isCellEditable(EventObject event) {
		boolean returnValue = false;
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
			if (path != null) {
				Object node = path.getLastPathComponent();
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
					Object userObject = treeNode.getUserObject();
					returnValue = (/*(treeNode.isLeaf()) && */(userObject instanceof CheckBoxNode));
				}
			}
		}
		return returnValue;
		/*if(stopCellEditing()){
			return false;
		}else{
			return returnValue;
		}*/
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row) {
		Component editor = renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, true);

		if (editor instanceof JCheckBox) {
			((JCheckBox) editor).addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent itemEvent) {
					if (stopCellEditing()) fireEditingStopped();
				}
			});
		}
		return editor;
	}
}
