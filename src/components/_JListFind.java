package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import Functions.FncGlobal;
import Lookup.LookupDispatcher;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

public class _JListFind extends JTextField implements ActionListener, LookupDispatcher {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2142022683985538859L;
	
	private List<LookupListener> listeners = new ArrayList<LookupListener>();

	private JPopupMenu menu;
	
	//private String actionCommand;
	private String title;
	private String nullSQLWarning;
	//private String strSQL;

	private int returnColumn = 1;

	private Object[] dataSet = { };

	//private JLabel lblAction = new JLabel();
	//private JLabel label = new JLabel();
	//private JTextField txtAction = new JTextField();

	//private Component[] components = new Component[]{};

	private String value = null;
	
	//private String co_id = null;
	
	private ActionListener resetListener = null;
	
	/*************************************************/
	private String server = "All";
	private String company;
	private String project;
	private String phase;

	public _JListFind() {
		initThis();
	}

	public _JListFind(String text) {
		super(text);
		initThis();
	}

	public _JListFind(String text, String lookupTitle) {
		super(text);
		this.value = text;
		this.title = lookupTitle;
		this.nullSQLWarning = "No records to show!";
		initThis();
	}

	public _JListFind(String text, String lookupTitle, String nullSQLWarning) {
		super(text);
		this.value = text;
		this.title = lookupTitle;
		this.nullSQLWarning = nullSQLWarning;
		
		initThis();
	}

	public _JListFind(int columns) {
		super(columns);
		initThis();
	}

	public _JListFind(String text, int columns) {
		super(text, columns);
		initThis();
	}

	public _JListFind(Document doc, String text, int columns) {
		super(doc, text, columns);
		initThis();
	}
	
	private void initThis() {
		setCaretPosition(0);
		setEditable(true);
		setMargin(new Insets(1, 2, 1, 2));
		//setFont(new Font("Tahoma",0,12));
		setSize(168, 25);
		setPreferredSize(new Dimension(214, 25));
		setToolTipText("Double-click here to open lookup table..");
		setBackground(new Color(128,255,255));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(Color.BLACK);
		setCaretColor(Color.BLACK);

		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				if((arg0.getClickCount()>=2) && isEditable() && isEnabled()){
					showDialog();
				}
			}
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger()){
					if(menu == null){
						initializePaymentMenu();
					}
					menu.show((_JLookup)e.getSource(), e.getX(), e.getY());
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()){
					if(menu == null){
						initializePaymentMenu();
					}
					menu.show((_JLookup)e.getSource(), e.getX(), e.getY());
				}
			}
			public void mouseEntered(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
		});

		addActionListener(this);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F2 && isEditable() && isEnabled()){
					showDialog();
				}
			}
		});

		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				//System.out.println(evt.getPropertyName());
			}
		});

		addPropertyChangeListener("editable", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(new Boolean(evt.getNewValue().toString())){
					setToolTipText("Double-click here to open lookup table..");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}else{
					setToolTipText(null);
					setCursor(Cursor.getDefaultCursor());
				}
				//System.out.println(evt.getPropertyName());
			}
		});

		addPropertyChangeListener("enabled", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(new Boolean(evt.getNewValue().toString())){
					setBackground(new Color(128,255,255));
					setToolTipText("Double-click here to open lookup table..");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}else{
					setBackground(new Color(212, 212, 210));
					setToolTipText(null);
					setCursor(Cursor.getDefaultCursor());
					setCaretPosition(0);
				}
				//System.out.println(evt.getPropertyName());
			}
		});

		addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				_JListFind listfind = (_JListFind) e.getSource();
				if(listfind.getText().trim().equals("") || !listfind.getText().trim().equals(getValue())){
					setText(getValue());
				}else{
					select(0, 0);
				}
			}
			public void focusGained(FocusEvent e) {
				selectAll();
			}
		});
	}

	protected void initializePaymentMenu() {
		menu = new JPopupMenu();

		JMenuItem menuSelectAll = new JMenuItem(this.getActionMap().get("select-all"));
		menu.add(menuSelectAll);
		menuSelectAll.setText("Select-All");
		menuSelectAll.setFont(getFont().deriveFont(10.0f));
		menuSelectAll.setEnabled(isEditable());

		menu.add(new JSeparator());

		JMenuItem menuCut = new JMenuItem(new DefaultEditorKit.CutAction());
		menu.add(menuCut);
		menuCut.setText("Cut");
		menuCut.setFont(getFont().deriveFont(10.0f));
		menuCut.setEnabled(isEditable());

		JMenuItem menuCopy = new JMenuItem(new DefaultEditorKit.CopyAction());
		menu.add(menuCopy);
		menuCopy.setText("Copy");
		menuCopy.setFont(getFont().deriveFont(10.0f));

		JMenuItem menuPaste = new JMenuItem(new DefaultEditorKit.PasteAction());
		menu.add(menuPaste);
		menuPaste.setText("Paste");
		menuPaste.setFont(getFont().deriveFont(10.0f));
		menuPaste.setEnabled(isEditable());

		menu.add(new JSeparator());

		JMenuItem menuReset = new JMenuItem();
		menu.add(menuReset);
		menuReset.setText("Reset");
		menuReset.setFont(getFont().deriveFont(10.0f));
		menuReset.setEnabled(isEditable());
		menuReset.addActionListener(getResetListener());
	}

	/**
	 * @return the resetListener
	 */
	public ActionListener getResetListener() {
		return resetListener;
	}

	/**
	 * @param resetListener the resetListener to set
	 */
	public void addResetListener(ActionListener resetListener) {
		this.resetListener = resetListener;
	}

	private void showDialog(){//XXX Show Dialog
		if(title.equals("Project")){
			if(company == null){
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, this.nullSQLWarning, title, JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		//setDataSet(new _JListFindTable(FncGlobal.homeMDI, _JListFind.this, title, false).display());
		doClick();
	}

	public void setReturnColumn(int returnColumn) {
		this.returnColumn = returnColumn;
	}

	public int getReturnColumn() {
		return this.returnColumn;
	}

	public void setLookupSQL(String strSQL) {
		// TODO Auto-generated method stub

	}

	public String getLookupSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDataSet(Object[] data) {
		this.dataSet = data;
	}

	public Object[] getDataSet() {
		return this.dataSet;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void addLookupListener(LookupListener listener) {
		listeners.add(listener);
	}

	@Override
	public void doClick() {
		dispatchEvent();
	}

	public void dispatchEvent() {
		final LookupEvent event = new LookupEvent(this/*, 1000, getActionCommand()*/);
		for (LookupListener l : listeners) {
			dispatchRunnableOnEventQueue(l, event);
		}
	}

	public void dispatchRunnableOnEventQueue(final LookupListener listener, final LookupEvent event) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				listener.lookupPerformed(event);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		ArrayList<Object[]> listData = new ArrayList<Object[]>();

		if(title.equals("Company")){
			//System.out.println("**********DUMAAN!");

			Map<String, Object[]> treeMap = new TreeMap<String, Object[]>(new HashMap<String, Object[]>());
			for(Entry<String, Object[]> entry: treeMap.entrySet()){
				Object[] objects = entry.getValue();
				String server = (String) objects[0];
				String co_id = (String) objects[1];
				String co_name = (String) objects[2];
				String co_alias = (String) objects[3];

				Object[] data = new Object[]{co_id, co_name, co_alias};

				if(this.getServer().equals("All")){
					if(getText().equals(co_id)){
						listData.add(data);
					}
				}else{
					if(this.getServer().equals(server)){
						if(getText().equals(co_id)){
							listData.add(data);
						}
					}
				}
				
			}
		}

		if(title.equals("Project")){

			Map<String, Object[]> treeMap = new TreeMap<String, Object[]>(new HashMap<String, Object[]>());
			for(Entry<String, Object[]> entry: treeMap.entrySet()){
				Object[] objects = entry.getValue();
				String proj_id = (String) objects[0];
				String proj_name = (String) objects[1];
				String proj_alias = (String) objects[2];

				String co_id = (String) objects[3];
				String server = (String) objects[4];

				Object[] data = new Object[]{proj_id, proj_name, proj_alias};

				if(this.getServer().equals("All")){
					if(this.getCompany() != null){
						if(this.getCompany().equals(co_id)){
							if(getText().equals(proj_id)){
								listData.add(data);
							}
						}
					}else{
						listData.add(data);
					}
				} else {
					if(this.getServer().equals(server)){
						if(this.getCompany() != null){
							if(this.getCompany().equals(co_id)){
								if(getText().equals(proj_id)){
									listData.add(data);
								}
							}
						}else{
							listData.add(data);
						}
					}
				}

				/*if(!listProject.contains(proj_name)){
					listProject.add(proj_name);
					listData.add(data);
				}*/
			}
		}

		setDataSet(listData.get(0));
		setText((String) getDataSet()[getReturnColumn()]);;
		setValue((String) getDataSet()[getReturnColumn()]);

		doClick();
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

}
