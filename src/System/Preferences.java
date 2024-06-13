/**
 * 
 */
package System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTree;

import Admin.AddCompany;
import Admin.AddDepartment;
import Admin.AddDivision;
import Admin.AddEditUser;
import Admin.AddProject;
import Admin.AddSubProject;
import Buyers.ClientServicing.pnlOpenHouse;
import Functions.FncLookAndFeel;
import Functions.UserInfo;
import components._JButton;
import components._JInternalFrame;


/**
 * @author PC-111l
 *
 */
public class Preferences extends _JInternalFrame implements ActionListener, TreeSelectionListener {

	private static final long serialVersionUID = 1L;

	static String TITLE = "Preferences";
	static Dimension FRAME_SIZE = new Dimension(800, 600);
	static Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);

	private JXPanel pnlMain;

	private JSplitPane splitCenter;
	private JScrollPane scrollLeft;
	private JScrollPane scrollRight;

	private JXTree treeMenu;

	private JXPanel pnlSouth;
	private JXPanel pnlSouthEast;
	private _JButton btnSave;
	private _JButton btnApply;
	private _JButton btnCancel;

	/**
	 * 
	 */
	public Preferences() {
		super(TITLE, true, true, true, true);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(TITLE);
		this.setSize(FRAME_SIZE);
		this.setPreferredSize(new Dimension(FRAME_SIZE));
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				splitCenter = new JSplitPane();
				pnlMain.add(splitCenter, BorderLayout.CENTER);
				splitCenter.setDividerLocation(200);
				{
					scrollLeft = new JScrollPane();
					splitCenter.add(scrollLeft, JSplitPane.LEFT);
					{
						treeMenu = new JXTree();//XXX
						scrollLeft.setViewportView(treeMenu);

						//treeMenu.setLeafIcon(null);
						treeMenu.setRootVisible(false);
						//treeMenu.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

						treeMenu.setClosedIcon(null);
						treeMenu.setLeafIcon(null);
						treeMenu.setOpenIcon(null);

						treeMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
						treeMenu.addTreeSelectionListener(this);
					}
				}
				{
					scrollRight = new JScrollPane();
					splitCenter.add(scrollRight, JSplitPane.RIGHT);
					{

					}
				}
			}
			{
				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new BorderLayout());
				//pnlSouth.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				pnlSouth.setPreferredSize(new Dimension(788, 30));
				{
					pnlSouthEast = new JXPanel();
					pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
					pnlSouthEast.setLayout(new GridLayout(1, 3, 5, 5));
					pnlSouthEast.setPreferredSize(new Dimension(310, 30));
					{
						btnApply = new _JButton("Apply");
						pnlSouthEast.add(btnApply);
						btnApply.addActionListener(this);
					}
					{
						btnSave = new _JButton("Save");
						pnlSouthEast.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new _JButton("Cancel");
						pnlSouthEast.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}
		initTree();
	}

	public void initTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Preferences");
		{
			DefaultMutableTreeNode nodeGeneral = new DefaultMutableTreeNode("Admin");
			top.add(nodeGeneral);
			{
				DefaultMutableTreeNode nodeUser = new DefaultMutableTreeNode("User");
				nodeGeneral.add(nodeUser);
				{
					DefaultMutableTreeNode nodeInformation = new DefaultMutableTreeNode("Information");
					nodeUser.add(nodeInformation);
				}
				{
					DefaultMutableTreeNode nodeAccess = new DefaultMutableTreeNode("Access (Login)");

					//check if the user logged in is under admin group
					if(UserInfo.ADMIN){
						nodeUser.add(nodeAccess);
					}
				}
				{
					DefaultMutableTreeNode nodePassword = new DefaultMutableTreeNode("Password");
					nodeUser.add(nodePassword);
				}
			}
			{
				DefaultMutableTreeNode nodeDepartment = new DefaultMutableTreeNode("Department");
				nodeGeneral.add(nodeDepartment);	
			}
			{
				DefaultMutableTreeNode nodeDivision = new DefaultMutableTreeNode("Division");
				nodeGeneral.add(nodeDivision);
			}
			{
				DefaultMutableTreeNode nodeSubProject = new DefaultMutableTreeNode("Sub Project");
				nodeGeneral.add(nodeSubProject);
			}
			{
				DefaultMutableTreeNode nodeProject = new DefaultMutableTreeNode("Project");
				nodeGeneral.add(nodeProject);
			}
			{
				DefaultMutableTreeNode nodeCompany = new DefaultMutableTreeNode("Company");
				nodeGeneral.add(nodeCompany);
			}
			{
				DefaultMutableTreeNode nodeOpenHouse = new DefaultMutableTreeNode("Open House");
				if(UserInfo.ADMIN || UserInfo.Department.equals("02")){
					nodeGeneral.add(nodeOpenHouse);
				}
				/*{
					DefaultMutableTreeNode nodeExport = new DefaultMutableTreeNode("Export");
					nodeOpenHouse.add(nodeExport);
				}
				{
					DefaultMutableTreeNode nodeImport = new DefaultMutableTreeNode("Import");
					nodeOpenHouse.add(nodeImport);
				}*/
			}
		}
		{
			DefaultMutableTreeNode nodeInterface = new DefaultMutableTreeNode("Interface");
			top.add(nodeInterface);
			{
				DefaultMutableTreeNode nodeTheme = new DefaultMutableTreeNode("Bookmarks");
				nodeInterface.add(nodeTheme);
			}
			{
				DefaultMutableTreeNode nodeTheme = new DefaultMutableTreeNode("Theme (Color)");
				nodeInterface.add(nodeTheme);
			}
		}

		treeMenu.setModel(new DefaultTreeModel(top));
		treeMenu.expandAll();
	}

	private void setView(Component view, String title) {
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			JLabel lblTitle = new JLabel(title);
			pnlMain.add(lblTitle, BorderLayout.NORTH);
			lblTitle.setFont(FncLookAndFeel.systemFont_Bold);
		}
		{
			pnlMain.add(view, BorderLayout.CENTER);
		}
		scrollRight.setViewportView(pnlMain);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Apply")){

		}
		if(e.getActionCommand().equals("Save")){

		}
		if(e.getActionCommand().equals("Cancel")){

		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {//XXX TreeSelection
		JXTree tree = (JXTree) arg0.getSource();
		try {
			String action = tree.getLastSelectedPathComponent().toString();
			//System.out.printf("Selected: %s%n", action);

			setView(new JPanel(), action);
			if(action.equals("Information")){//01-25-15 JOHN LESTER FATALLO
				setView(new AddEditUser(), "Information");
			}
			if(action.equals("Access (Login)")){
				setView(new pnlUsers(), action);
			}
			if(action.equals("Password")){
				setView(new pnlPassword(), action);
			}
			if(action.equals("Department")){//01-25-15 JOHN LESTER FATALLO
				setView(new AddDepartment(), "Department");
			}
			if(action.equals("Division")){//01-25-15 JOHN LESTER FATALLO
				setView(new AddDivision(), "Division");
			}
			if(action.equals("Sub Project")){//01-25-15 JOHN LESTER FATALLO
				setView(new AddSubProject(), "Sub Project");
			}
			if(action.equals("Project")){//01-25-15 JOHN LESTER FATALLO
				setView(new AddProject(), "Project");
			}
			if(action.equals("Company")){//01-25-15 JOHN LESTER FATALLO
				setView(new AddCompany(), "Company");
			}
			if(action.equals("Bookmarks")){
				setView(new pnlBookmarks(), "Bookmarks");
			}
			if(action.equals("Open House")){
				setView(new pnlOpenHouse(), "Open House");
			}
			if(action.equals("Theme (Color)")){
				setView(new JColorChooser(FncLookAndFeel.grayColor), "Theme (Color)");
			}
		} catch (NullPointerException e) {
			setView(new JPanel(), null);
		}
	}
}

/*class IconData {
	protected Icon m_icon;
	protected Icon m_expandedIcon;
	protected Object m_data;

	public IconData(Icon icon, Object data) {
		m_icon = icon;
		m_expandedIcon = null;
		m_data = data;
	}

	public IconData(Icon icon, Icon expandedIcon, Object data) {
		m_icon = icon;
		m_expandedIcon = expandedIcon;
		m_data = data;
	}

	public Icon getIcon() {
		return m_icon;
	}

	public Icon getExpandedIcon() {
		return m_expandedIcon != null ? m_expandedIcon : m_icon;
	}

	public Object getObject() {
		return m_data;
	}

	public String toString() {
		return m_data.toString();
	}
}*/
