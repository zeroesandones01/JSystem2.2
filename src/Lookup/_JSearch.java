package Lookup;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.sort.RowFilters;

import Database.pgSelect;
import tablemodel.modelSearch;
import Functions.FncFocusTraversalPolicy;
import Functions.FncTables;
import components._JDialog;
import components._JScrollPane;
import components._JTableMain;

public class _JSearch extends _JDialog implements _GUI, ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;

	/*public static final String FIND_NEXT_ACTION_COMMAND = "findNext";
    public static final String FIND_PREVIOUS_ACTION_COMMAND = "findPrevious";

	public static final String SEARCH_FIELD_LABEL = "searchFieldLabel";
	public static final String SEARCH_FIELD_MNEMONIC = SEARCH_FIELD_LABEL + ".mnemonic";
	public static final String SEARCH_TITLE = "searchTitle";
	public static final String MATCH_ACTION_COMMAND = "match";

	protected PatternModel patternModel;
	private ActionContainerFactory actionFactory;*/

	private JXPanel pnlMain = new JXPanel();

	private JXPanel pnlNorth;
	private JComboBox cmbSearch;

	private _JScrollPane scrollCenter;
	private JButton btnSelect;
	private JButton btnCancel;
	private JXTextField txtSearch;
	public _JTableMain tblSearch;
	private modelSearch modelSearch = new modelSearch();

	private JXPanel pnlSouth;

	private Object[] RETURN_DATA;

	private KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public _JSearch(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	@Override
	public void initGUI() {//XXX
		try {
			this.getRootPane().registerKeyboardAction(this, "Search", KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);		
			this.getRootPane().registerKeyboardAction(this, "Close", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			this.getRootPane().registerKeyboardAction(this, "Return", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			this.addWindowListener(this);
			{
				//XXX pnlMain = new JXPanel();
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setLayout(new BorderLayout(5, 5));
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlNorth = new JXPanel();
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setLayout(new BorderLayout(5, 5));
					{
						cmbSearch = new JComboBox(new DefaultComboBoxModel(modelSearch.COLUMNS));
						pnlNorth.add(cmbSearch, BorderLayout.WEST);
						cmbSearch.setSelectedIndex(1);
						cmbSearch.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								focusManager.focusNextComponent(cmbSearch);
							}
						});
					}
					{
						txtSearch = new JXTextField("Search...");
						pnlNorth.add(txtSearch, BorderLayout.CENTER);
						txtSearch.getDocument().addDocumentListener(new DocumentListener() {
							public void removeUpdate(DocumentEvent e) {
								//searchText();

								tblSearch.setRowFilter(RowFilters.regexFilter(txtSearch.getText().trim().toUpperCase(), cmbSearch.getSelectedIndex()));
								tblSearch.packAll();
								//tblSearch.putClientProperty("MATCH_HIGHLIGHTER", txtSearch.getText().trim());
							}
							public void insertUpdate(DocumentEvent e) {
								//searchText();

								tblSearch.setRowFilter(RowFilters.regexFilter(txtSearch.getText().trim().toUpperCase(), cmbSearch.getSelectedIndex()));
								tblSearch.packAll();
								//tblSearch.putClientProperty("MATCH_HIGHLIGHTER", txtSearch.getText().trim());
							}
							public void changedUpdate(DocumentEvent e) {
								//tblSearch.putClientProperty("MATCH_HIGHLIGHTER", txtSearch.getText().trim());
							}
						});
						txtSearch.addFocusListener(new FocusAdapter() {
							@Override
							public void focusGained(FocusEvent e) {
								JXTextField field = (JXTextField) e.getSource();
								field.selectAll();
							}
						});

						/*txtSearch.addKeyListener(new KeyAdapter(){
							public void keyReleased(KeyEvent evt) {
								searchText(((JTextField) evt.getSource()).getText().trim().toUpperCase(), tblSearch.getRowCount());

								TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblDefault.getModel());
								tblDefault.setRowSorter(sorter);
								sorter.setRowFilter( RowFilter.regexFilter(((JTextField) evt.getSource()).getText().trim(), cboSearchColumn.getSelectedIndex()) );
							}
						});*/
					}
				}
				{
					scrollCenter = new _JScrollPane();
					pnlMain.add(scrollCenter, BorderLayout.CENTER);
					scrollCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollCenter.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblSearch.clearSelection();
						}
					});
					{
						tblSearch = new _JTableMain(modelSearch);
						scrollCenter.setViewportView(tblSearch);
						tblSearch.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){//XXX tblSearch

								}
							}
						});
						tblSearch.registerKeyboardAction(this, "Return", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
						tblSearch.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								if(e.getClickCount() == 2){
									returnSelected();
								}
							}
						});
					}
				}
				{
					pnlSouth = new JXPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					//pnlSouth.setBorder(FncGlobal.lineBorder);
					pnlSouth.setPreferredSize(new Dimension(780, 36));
					{
						btnSelect = new JButton("SELECT");
						pnlSouth.add(btnSelect);
						btnSelect.setActionCommand("Return");
						btnSelect.setPreferredSize(new Dimension(100, 26));
						btnSelect.addActionListener(this);
					}
					{
						btnCancel = new JButton("CANCEL");
						pnlSouth.add(btnCancel);
						btnCancel.setActionCommand("Close");
						btnCancel.setPreferredSize(new Dimension(100, 26));
						btnCancel.addActionListener(this);
					}
				}
			}
			this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbSearch, txtSearch, tblSearch));

			displayClients(tblSearch, modelSearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void returnSelected() {
		try {
			int row = tblSearch.convertRowIndexToModel(tblSearch.getSelectedRow());

			if(row != -1){
				Object[] data = new Object[modelSearch.getColumnCount()];

				for(int x=0; x < data.length; x++){
					data[x] = modelSearch.getValueAt(row, x);
				}
				setRETURN_DATA(data);
				dispose();
			}
		} catch (IndexOutOfBoundsException e) {
			int row = tblSearch.convertRowIndexToModel(0);

			if(row != -1){
				Object[] data = new Object[modelSearch.getColumnCount()];

				for(int x=0; x < data.length; x++){
					data[x] = modelSearch.getValueAt(row, x);
				}
				setRETURN_DATA(data);
				dispose();
			}
		}
	}

	public static void displayClients(_JTableMain tblSearch, modelSearch model) {//XXX Display Clients
		FncTables.clearTable(model);

		String strSQL = "SELECT * FROM jsearch;";

		//FncSystem.out("Clients", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
		
		tblSearch.hideColumns("Client ID", "Project ID");
		tblSearch.packAll();
	}

	/**
	 * @return the RETURN_DATA
	 */
	public Object[] getRETURN_DATA() {
		return RETURN_DATA;
	}

	/**
	 * @param RETURN_DATA the RETURN_DATA to set
	 */
	public void setRETURN_DATA(Object[] RETURN_DATA) {
		this.RETURN_DATA = RETURN_DATA;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Search")){
			//SearchFactory.getInstance().showFindInput(tblSearch, tblSearch, 1);
		}
		if(e.getActionCommand().equals("Close")){
			setRETURN_DATA(null);
			dispose();
		}
		if(e.getActionCommand().equals("Return")){
			returnSelected();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		focusManager.focusNextComponent(cmbSearch);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		focusManager.focusNextComponent(cmbSearch);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		focusManager.focusNextComponent(cmbSearch);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		focusManager.focusNextComponent(cmbSearch);
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		focusManager.focusNextComponent(cmbSearch);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		focusManager.focusNextComponent(cmbSearch);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		focusManager.focusNextComponent(cmbSearch);
	}

}
