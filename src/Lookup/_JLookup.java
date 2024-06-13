package Lookup;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
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
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultEditorKit;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;

public class _JLookup extends JXTextField implements ActionListener, LookupDispatcher, ResetDispatcher {

	private static final long serialVersionUID = 1L;

	private _JLookupTable dlg;

	private List<LookupListener> lookupListeners = new ArrayList<LookupListener>();
	private ResetListener resetListeners = null;

	private JPopupMenu menu;

	private String actionCommand;
	private String title;
	private String nullSQLWarning = "No records to show!";

	private String strSQL;

	private int returnColumn = 1;

	private Object[] dataSet = { };

	private JLabel lblAction = new JLabel();
	private JLabel label = new JLabel();
	private JTextField txtAction = new JTextField();

	private Component[] components = new Component[]{};

	private String value;
	
	private boolean zeroBoolean = false;
	
	private boolean filterName;
	
	private boolean filterCardName;
	private Integer filterIndex;
	
	private Dimension sizeDialog = new Dimension(375, 420);

	public _JLookup() {
		super();
		initThis();
	}

	public _JLookup(String text) {
		super(text);
		initThis();
	}

	public _JLookup(String text, String lookupTitle) {
		super(text);
		this.value = text;
		this.setTitle(lookupTitle);
		initThis();
	}

	public _JLookup(String text, String lookupTitle, String nullSQLWarning) {
		super(text);
		this.value = text;
		this.setTitle(lookupTitle);
		this.nullSQLWarning = nullSQLWarning;
		initThis();
	}

	public _JLookup(String text, String lookupTitle, int returnColumn) {
		super(text);
		this.value = text;
		this.setTitle(lookupTitle);
		setReturnColumn(returnColumn);
		initThis();
	}

	public _JLookup(String text, String lookupTitle, int returnColumn, String nullSQLWarning) {
		super(text);
		this.value = text;
		this.setTitle(lookupTitle);
		setReturnColumn(returnColumn);
		this.nullSQLWarning = nullSQLWarning;
		initThis();
	}

	public _JLookup(String text, String lookupTitle, int returnColumn, int columns) {
		super(text/*, columns*/);
		this.value = text;
		this.setTitle(lookupTitle);
		setReturnColumn(returnColumn);
		initThis();
	}

	public _JLookup(int columns) {
		//super(columns);
		initThis();
	}

	public _JLookup(String text, int columns) {
		super(text/*, columns*/);
		initThis();
	}

	/*public _JLookup(Document doc, String text, int columns) {
		super(doc, text, columns);
		initThis();
	}*/

	private void initThis() {//XXX initThis
		setCaretPosition(0);
		setEditable(true);
		//setMargin(new Insets(1, 2, 1, 2));
		setFont(new Font("Tahoma", Font.PLAIN, FncLookAndFeel.font_size));
		//setSize(168, 25);
		//setPreferredSize(new Dimension(214, 25));
		setToolTipText("Double-click here to open lookup table..");
		setBackground(new Color(128,255,255));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setHorizontalAlignment(SwingConstants.CENTER);
		setMinimumSize(new Dimension(50, 25));

		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				if((arg0.getClickCount()>=2) && isEnabled() && isEditable()){
					showDialog();
				}
			}
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger()){
					if(isEnabled()){
						initializePaymentMenu();
						menu.show((_JLookup)e.getSource(), e.getX(), e.getY());
					}
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()){
					if(isEnabled()){
						initializePaymentMenu();
						menu.show((_JLookup)e.getSource(), e.getX(), e.getY());
					}
				}
			}
			public void mouseEntered(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
		});

		addActionListener(this);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F2){
					if(isEditable()){
						showDialog();
					}
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
					setBackground(new Color(128,255,255));
					setToolTipText("Double-click here to open lookup table..");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}else{
					setBackground(new Color(88,235,235));
					setToolTipText(null);
					setCursor(Cursor.getDefaultCursor());
				}
				//System.out.println(evt.getPropertyName());
			}
		});

		addPropertyChangeListener("enabled", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(new Boolean(evt.getNewValue().toString())){
					if(isEditable()){
						setBackground(new Color(128,255,255));
					}else{
						setBackground(new Color(88,235,235));
					}
					//setBackground(new Color(128,255,255));
					setToolTipText("Double-click here to open lookup table..");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}else{
					setBackground(new Color(212, 212, 210));
					setToolTipText(null);
					setCursor(Cursor.getDefaultCursor());
				}
				//System.out.println(evt.getPropertyName());
			}
		});

		addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				_JLookup lookup = (_JLookup) e.getSource();
				if(lookup.getText().trim().equals("") || !lookup.getText().trim().equals(getValue())){
					setText(getValue());
				}
			}
			public void focusGained(FocusEvent e) {

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

		JMenuItem menuReset = new JMenuItem("Reset");
		menu.add(menuReset);
		menuReset.setFont(getFont().deriveFont(10.0f));
		menuReset.setEnabled(isEditable());
		menuReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doReset();
			}
		});
	}

	private void showDialog(){
		if(strSQL != null){
			dlg = new _JLookupTable((Window) this.getTopLevelAncestor(), getSizeDialog(), _JLookup.this, getTitle(), strSQL, isZeroBoolean());
			dlg.setFilterClientName(isFilterName());
			dlg.setFilterCardName(isFilterCardName());
			dlg.setFilterIndex(filterIndex());
			dlg.setLocationRelativeTo((Window) this.getTopLevelAncestor());
			dlg.setVisible(true);

			dlgReturn(dlg.isOK());
		}else{
			JOptionPane.showMessageDialog(FncGlobal.homeMDI, this.nullSQLWarning, title, JOptionPane.WARNING_MESSAGE); //XXX
			dlgReturn(false);
		}
	}

	public String getActionCommand() {
		return actionCommand;
	}
	
	@Override
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

	// The index of the selected column to return or insert in this component's TEXT property
	public void setReturnColumn(int returnColumn){
		this.returnColumn = returnColumn;
	}
	
	public int getReturnColumn(){
		return this.returnColumn;
	}

	// SQL used to fill the lookup table
	public void setLookupSQL(String strSQL){
		this.strSQL = strSQL;		
	}	
	public String getLookupSQL(){
		return this.strSQL;
	}

	// Data Set contains an array of the values in the selected row of the lookup table
	public void setDataSet(Object[] data){
		this.dataSet = data;
	}
	public Object[] getDataSet(){
		return this.dataSet;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
		this.setText(value);
	}

	public void addTag(JLabel label){
		this.lblAction = label;
	}
	public void addTag(JTextField textfield){
		this.txtAction = textfield;
	}

	/**
	 * @return the preRequisuite
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}

	public void setRequired(Boolean isRequired) {
		if(isRequired){
			label.setFont(label.getFont().deriveFont(Font.ITALIC));
			label.setForeground(Color.RED);
			label.repaint();
		}else{
			label.setFont(label.getFont().deriveFont(Font.PLAIN));
			label.setForeground(Color.BLACK);
			label.repaint();
		}
	}

	public Boolean isRequired() {
		return (label.getForeground().equals(Color.RED));
	}

	public void clearLookup(){
		setValue(null);
		setText("");
	}

	protected void clearTags(){
		lblAction.setText("[ ]");
		txtAction.setText("");

		setText("");
		for(int x=0; x < components.length; x++){
			if(components[x] instanceof JLabel){
				((JLabel)components[x]).setText("");
			}
			if(components[x] instanceof JTextField){
				((JTextField)components[x]).setText("");
			}
		}
		setRequired(true);
	}





	/*@Override
	public void addActionListener(ActionListener listener) {
		//System.out.println("**********Outer ActionListener!");
		listeners.add(listener);
	}*/

	public void addLookupListener(LookupListener listener) {
		lookupListeners.add(listener);
	}

	public void doClick() {
		dispatchLookupEvent();
	}

	private void dispatchLookupEvent() {
		final LookupEvent event = new LookupEvent(this/*, 1000, getActionCommand()*/);
		for (LookupListener l : lookupListeners) {
			dispatchRunnableOnLookupEventQueue(l, event);
		}
	}

	private void dispatchRunnableOnLookupEventQueue(final LookupListener listener, final LookupEvent event) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				listener.lookupPerformed(event);
			}
		});
	}

	private void dlgReturn(boolean isOK) {
		if(isOK){
			setDataSet(dlg.getReturnDataSet());
			doClick();
			setRequired(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("**********Inner ActionListener!");

		if(isEnabled() && isEditable()){
			pgSelect db = new pgSelect();
			try {
				db.select(strSQL);

				ArrayList<Boolean> checkHas = new ArrayList<Boolean>();
				Object[] objArray = null;
				int row = 0;

				for(int x=0; x<db.getRowCount(); x++){
					objArray = new Object[db.getColumnCount()];
					//if(db.Result[x][returnColumn].contains(getText())){
					if(db.getResult()[x][returnColumn].toString().equals(getText())){
						checkHas.add(true);
						row = x;
					}
					/*if(this.getText().contains(db.Result[x][this.returnColumn])){
						checkHas.add(true);
						row = x;
					}*/
				}

				if(checkHas.contains(true)){
					if(checkHas.size() > 1) {
						dlg = new _JLookupTable(FncGlobal.homeMDI, getSizeDialog(), _JLookup.this, getTitle(), strSQL, isZeroBoolean());
						dlg.setFilterClientName(isFilterName());
						dlg.setFilterCardName(isFilterCardName());
						dlg.setFilterIndex(filterIndex());
						dlg.setLocationRelativeTo(FncGlobal.homeMDI);
						dlg.setVisible(true);

						dlgReturn(dlg.isOK());
					}else{
						for(int y=0; y<db.getColumnCount(); y++){
							objArray[y] = db.getResult()[row][y];
						}

						setValue(((_JLookup)e.getSource()).getText());
						setDataSet(objArray);
						doClick();
						setRequired(false);
					}
				}else{
					dlg = new _JLookupTable(FncGlobal.homeMDI, getSizeDialog(), _JLookup.this, getTitle(), strSQL, isZeroBoolean());
					dlg.setFilterClientName(isFilterName());
					dlg.setFilterCardName(isFilterCardName());
					dlg.setFilterIndex(filterIndex());
					dlg.setLocationRelativeTo(FncGlobal.homeMDI);
					dlg.setVisible(true);

					dlgReturn(dlg.isOK());
				}
			} catch (NullPointerException npE) {
				npE.printStackTrace();
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, this.nullSQLWarning, "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	@Override
	public void addResetListener(ResetListener resetListeners) {
		this.resetListeners = resetListeners;
	}

	public void doReset() {
		dispatchResetEvent();
	}

	private void dispatchResetEvent() {
		final ResetEvent event = new ResetEvent(this/*, 1000, getActionCommand()*/);
		dispatchRunnableOnResetEventQueue(resetListeners, event);
	}

	private void dispatchRunnableOnResetEventQueue(final ResetListener listener, final ResetEvent event) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					listener.resetPerformed(event);
				} catch (NullPointerException e) { }

			}
		});
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isZeroBoolean() {
		return zeroBoolean;
	}

	public void setZeroBoolean(boolean zeroBoolean) {
		this.zeroBoolean = zeroBoolean;
	}

	public boolean isFilterName() {
		return filterName;
	}

	public void setFilterName(boolean filterName) {
		this.filterName = filterName;
	}
	
	public boolean isFilterCardName(){
		return filterCardName;
	}
	
	public void setFilterCardName(boolean filterCARDName){
		this.filterCardName = filterCARDName;
	}
	
	public Integer filterIndex(){
		return filterIndex;
	}
	
	public void setFilterIndex(Integer index){
		this.filterIndex = index;
	}

	public Dimension getSizeDialog() {
		return sizeDialog;
	}

	public void setSizeDialog(Dimension sizeDialog) {
		this.sizeDialog = sizeDialog;
	}

	public String getNullSQLWarning() {
		return nullSQLWarning;
	}

	public void setNullSQLWarning(String nullSQLWarning) {
		this.nullSQLWarning = nullSQLWarning;
	}

}
