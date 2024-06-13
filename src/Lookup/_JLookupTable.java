package Lookup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

import Functions.CheckBoxHeader;
import Functions.Dialogz;
import Functions.FncGlobal;
import Renderer.DateRenderer;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;

public class _JLookupTable extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollLookup;
	private JProgressBar pgBar;
	private JButton btnRefresh;
	private JComboBox cboSearchColumn;
	private JTextField txtSearch;
	private JButton btnCancel;
	private JButton btnOK;
	private JXTable tblDefault = new JXTable();

	private Object[] ReturnDataSet;

	private Object[][] result;

	private Boolean booleanZero = false;

	private RolloverMouseAdapter rolloverAdapter;
	private RolloverBooleanRenderer renderer;

	//Disabled 2015-01-30 by Alvin Gonzales
	//private List<Object> listOfSelected;

	private _JLookup txtReturn;

	private boolean OK;

	private Dimension size = new Dimension(375, 420);

	private boolean filterName;
	
	private boolean filterCardname;
	
	private Integer filter_index;

	public _JLookupTable(_JLookup txtReturn, String strSQL) {
		initGUI(strSQL);
		this.txtReturn = txtReturn;
	}

	public _JLookupTable(Frame owner, _JLookup txtReturn, String strSQL, Boolean booleanZero) {
		super(owner);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		initGUI(strSQL);
	}

	public _JLookupTable(Dialog owner, _JLookup txtReturn, String strSQL, Boolean booleanZero) {
		super(owner);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		initGUI(strSQL);
	}

	public _JLookupTable(Window owner, _JLookup txtReturn, String strSQL, Boolean booleanZero) {
		super(owner);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		initGUI(strSQL);
	}

	public _JLookupTable(Frame owner, Dimension size, _JLookup txtReturn, String title, String strSQL, Boolean booleanZero, List<Object> listOfSelected) {
		super(owner, title);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		this.size = size;
		initGUI(strSQL);
	}

	public _JLookupTable(Dialog owner, _JLookup txtReturn, String title, String strSQL, Boolean booleanZero) {
		super(owner, title);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		initGUI(strSQL);
	}

	public _JLookupTable(Window owner, _JLookup txtReturn, String title, String strSQL, Boolean booleanZero) {
		super(owner, title);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		initGUI(strSQL);
	}

	public _JLookupTable(Window owner, Dimension size, _JLookup txtReturn, String title, String strSQL, Boolean booleanZero, List<Object> listOfSelected) {
		super(owner, title);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		this.size = size;
		initGUI(strSQL);
	}

	public _JLookupTable(Window owner, Dimension size, _JLookup txtReturn, String title, String strSQL, Boolean booleanZero) {
		super(owner, title);
		this.txtReturn = txtReturn;
		this.booleanZero = booleanZero;
		this.size = size;
		//this.listOfSelected = listOfSelected;
		initGUI(strSQL);
	}

	private void initGUI(final String strSQL) {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		this.getRootPane().registerKeyboardAction(this, "Enter", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			JPanel pnlNorth = new JPanel();
			getContentPane().add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(3, 5));
			pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlNorth.setPreferredSize(new Dimension(373, 60));
			{
				JPanel pnlNorthNorth = new JPanel();
				BorderLayout pnlNorthNorthLayout = new BorderLayout();
				pnlNorth.add(pnlNorthNorth, BorderLayout.NORTH);
				pnlNorthNorth.setLayout(pnlNorthNorthLayout);
				pnlNorthNorth.setPreferredSize(new Dimension(371, 23));
				{
					JLabel lblSearch = new JLabel("Search column");
					pnlNorthNorth.add(lblSearch, BorderLayout.WEST);
					lblSearch.setFont(lblSearch.getFont().deriveFont(Font.PLAIN));
					lblSearch.setPreferredSize(new Dimension(100, 23));
				}
				{
					cboSearchColumn = new JComboBox();
					pnlNorthNorth.add(cboSearchColumn, BorderLayout.CENTER);
					cboSearchColumn.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent arg0) {
							txtSearch.grabFocus();
							txtSearch.selectAll();
							//searchText();
						}
					});
				}
			}
			{
				JPanel pnlNorthCenter = new JPanel();
				BorderLayout pnlNorthCenterLayout = new BorderLayout();
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				pnlNorthCenter.setLayout(pnlNorthCenterLayout);
				{
					JLabel lblEnterText = new JLabel("Text to search");
					pnlNorthCenter.add(lblEnterText, BorderLayout.WEST);
					lblEnterText.setFont(lblEnterText.getFont().deriveFont(Font.PLAIN));
					lblEnterText.setPreferredSize(new Dimension(100, 32));
				}
				{
					txtSearch = new JTextField();
					pnlNorthCenter.add(txtSearch, BorderLayout.CENTER);
					txtSearch.setFont(new Font("Tahoma",Font.PLAIN,12));
					txtSearch.addKeyListener(new KeyAdapter() {
						public void keyPressed(KeyEvent pwede) {
							// Return value to internal frame when user presses ENTER on table
							/*if(pwede.getKeyCode() == KeyEvent.VK_ENTER){
								returnValue();
							}*/
							if(pwede.getKeyCode() == KeyEvent.VK_DOWN || pwede.getKeyCode() == KeyEvent.VK_TAB){
								tblDefault.requestFocus();
							}
						}
						/*public void keyReleased(KeyEvent pwede){
							searchText(((JTextField) pwede.getSource()).getText().trim().toUpperCase());
						}*/
					});
				}
			}
		}
		{
			JPanel pnlCenter = new JPanel();
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setLayout(new BorderLayout());
			pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			{
				scrollLookup = new JScrollPane();
				pnlCenter.add(scrollLookup, BorderLayout.CENTER);
				//scrollLookup.getViewport().setBackground(pnlCenter.getBackground());
				scrollLookup.setBorder(new LineBorder(new Color(102,164,87), 1, false));
				scrollLookup.setName("scrollLookup");
			}
		}
		{
			JPanel pnlSouth = new JPanel();
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout(3, 5));
			pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlSouth.setPreferredSize(new Dimension(373, 60));
			{
				JPanel pnlSouthCenter = new JPanel();
				BorderLayout pnlSouthCenterLayout = new BorderLayout();
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setLayout(pnlSouthCenterLayout);
				{
					pgBar = new JProgressBar();
					pnlSouthCenter.add(pgBar, BorderLayout.CENTER);
				}
			}
			{
				JPanel pnlSouthSouth = new JPanel();
				pnlSouth.add(pnlSouthSouth, BorderLayout.SOUTH);
				pnlSouthSouth.setLayout(new BorderLayout());
				pnlSouthSouth.setPreferredSize(new Dimension(371, 30));
				{
					JPanel pnlSouthSouthWest = new JPanel();
					pnlSouthSouth.add(pnlSouthSouthWest, BorderLayout.WEST);
					pnlSouthSouthWest.setLayout(new BorderLayout());
					pnlSouthSouthWest.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
					pnlSouthSouthWest.setPreferredSize(new Dimension(100, 30));
					{
						btnOK = new JButton("OK");
						pnlSouthSouthWest.add(btnOK, BorderLayout.CENTER);
						btnOK.setFont(btnOK.getFont().deriveFont(Font.BOLD));
						btnOK.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								returnValue();
							}
						});
					}
				}
				{
					JPanel pnlSouthSouthEast = new JPanel();
					pnlSouthSouth.add(pnlSouthSouthEast, BorderLayout.EAST);
					pnlSouthSouthEast.setLayout(new BorderLayout());
					pnlSouthSouthEast.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
					pnlSouthSouthEast.setPreferredSize(new Dimension(100, 30));
					{
						btnCancel = new JButton("CANCEL");
						pnlSouthSouthEast.add(btnCancel, BorderLayout.CENTER);
						btnCancel.setFont(btnCancel.getFont().deriveFont(Font.BOLD));
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								//txtReturn.grabFocus();
								setOK(false);
								dispose();
							}
						});
					}
				}
				{
					JPanel pnlSouthSouthCenter = new JPanel();
					pnlSouthSouth.add(pnlSouthSouthCenter, BorderLayout.CENTER);
					pnlSouthSouthCenter.setLayout(new OverlayLayout(pnlSouthSouthCenter));
					{
						btnRefresh = new JButton("REFRESH");
						pnlSouthSouthCenter.add(btnRefresh);
						btnRefresh.setAlignmentX(0.5f);
						btnRefresh.setAlignmentY(0.5f);
						//btnRefresh.setFont(new Font("Tahoma",Font.BOLD,11));
						//btnRefresh.setBackground(new Color(143,196,0));
						//btnRefresh.setForeground(new Color(255,255,255));
						btnRefresh.setMaximumSize(new Dimension(100, 30));
						btnRefresh.setFont(btnRefresh.getFont().deriveFont(Font.BOLD));
						btnRefresh.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent arg0) {
								new Thread(new Runnable() {
									public void run() {
										tblDefault.setEnabled(false);
										//jProgressBar1.setVisible(true);
										pgBar.setIndeterminate(true);
										pgBar.setString("Loading...");
										btnState(false, false, false, false, false);

										resetValues(strSQL);

										pgBar.setIndeterminate(false);
										pgBar.setString("");
										//jProgressBar1.setVisible(false);
										tblDefault.setEnabled(true);
										btnState(true, true, true, true, true);
									}
								}).start();
							}
						});

					}
				}
			}
		}

		//XXX
		new Thread(new Runnable() {
			public void run() {
				pgBar.setIndeterminate(true);
				pgBar.setString("Loading...");
				btnState(false, false, false, false, false);

				try {
					resetValues(strSQL);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

				//Disabled 2015-01-30 by Alvin Gonzales
				/*if(getBooleanZero()){
					selectOnList();
				}*/

				pgBar.setIndeterminate(false);
				pgBar.setString("");
				btnState(true, true, true, true, true);
				txtSearch.grabFocus();
			}
		}).start();
		//this.setVisible(true);
		this.pack();
		cboSearchColumn.setSelectedItem(1);
	}

	private void resetValues(String strSQL){//TODO resetValues
		int rowCount = 0;
		int colCount = 0;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Class.forName(FncGlobal.connectionDriver);

			//Load the connection
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);

			//Create a statement object
			statement = connection.createStatement();

			//connection.setReadOnly(true);

			//execute the statement object with the specified string
			resultSet = statement.executeQuery(strSQL);

			//count number of rows
			//resultSet.last();

			//resultSet.beforeFirst();

			// count number of columns
			colCount = resultSet.getMetaData().getColumnCount();

			Map<Integer, Class> mapClass = new HashMap<Integer, Class>();

			ResultSetMetaData rsmd0 = resultSet.getMetaData();
			for(int x=1; x <= rsmd0.getColumnCount(); x++){
				String className = rsmd0.getColumnClassName(x);

				//System.out.printf("Class: %s - (%s, %s)%n", x, rsmd0.getColumnClassName(x), rsmd0.getColumnTypeName(x));

				//Added and edited by Alvin Gonzales (2015-05-20) to detect the text / varchar data type of column and render in table
				if(rsmd0.getColumnTypeName(x).equals("varchar")) {
					mapClass.put(x -1, String.class);
				}else if (rsmd0.getColumnTypeName(x).equals("text")) {
					mapClass.put(x -1, Object.class);
				} else {
					mapClass.put(x -1, Class.forName(className));
				}
			}

			// Get Column Names
			String[] columnNames = new String[colCount];
			for(int x=0; x < colCount; x++) {
				if(getBooleanZero() && x==0){
					columnNames[x] = "Select";
				}else{
					columnNames[x] = rsmd0.getColumnName(x+1);
				}
			}

			ArrayList<Object[]> Result = new ArrayList<Object[]>();

			//navigate thru the resultSet
			while (resultSet.next()) {
				ArrayList alRowData = new ArrayList();

				ResultSetMetaData rsmd = resultSet.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				for(int columnIndex = 1; columnIndex <= numberOfColumns; columnIndex ++){
					alRowData.add(resultSet.getObject(columnIndex));
				}
				Result.add(alRowData.toArray());
			}
			rowCount = Result.size();

			//Map<Integer, Class> mapClass = new HashMap<Integer, Class>();
			Object[][] strFields= new Object[rowCount][colCount];
			for(int x=0; x < rowCount; x++){
				for(int y=0; y < colCount; y++){
					strFields[x][y] = Result.get(x)[y];
				}
			}

			// Close the record
			statement.close(); 
			connection.close();

			// Fill Combobox with column names
			cboSearchColumn.setModel( new DefaultComboBoxModel(columnNames));
			if(getBooleanZero()){
				cboSearchColumn.removeItemAt(0);
			}
			try {
				cboSearchColumn.setSelectedIndex( txtReturn.getReturnColumn() );
			} catch (NullPointerException e) { }

			// Create Table and fill with values based on SQL string parameter
			tblDefault = customTable(strFields, columnNames, cboSearchColumn, mapClass);
			scrollLookup.setViewportView( tblDefault );

			ColorHighlighter base = new ColorHighlighter(HighlightPredicate.EVEN, Color.WHITE, Color.BLACK);
			ColorHighlighter alternate = new ColorHighlighter(HighlightPredicate.ODD, new Color(225, 225, 225), Color.BLACK);
			ColorHighlighter rollover = new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(240, 240, 240), Color.BLACK);
			tblDefault.setHighlighters(new CompoundHighlighter(alternate, base, rollover));
			tblDefault.setBackground(new Color(232, 229, 222));

			// Select first row
			tblDefault.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			if(!getBooleanZero()){
				tblDefault.setRowSelectionInterval(0, 0);
			}

			tblDefault.grabFocus();

			//tblDefault.setGridColor(Color.lightGray);

			tblDefault.getTableHeader().setReorderingAllowed(false);
			tblDefault.setHorizontalScrollEnabled(true);

			if(getBooleanZero()){
				tblDefault.getColumnExt(0).setPreferredWidth(35);
				tblDefault.getColumnExt(0).setMinWidth(35);
				tblDefault.getColumnExt(0).setMaxWidth(35);
			}

			tblDefault.packAll();

			txtSearch.addKeyListener(new KeyAdapter(){
				public void keyReleased(KeyEvent evt) {
					//searchText(((JTextField) evt.getSource()).getText().trim().toUpperCase(), tblDefault.getRowCount());
					//System.out.println("Dumaan dito sa keyReleased");
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblDefault.getModel());
					tblDefault.setRowSorter(sorter);
					String text_search = ((JTextField) evt.getSource()).getText().trim();
					
					//XXX ADDED BY JOHN LESTER FATALLO TO FILTER WORDS WITH ENYE WHEN LETTER N IS TYPED
					if(text_search.contains("n") || text_search.contains("N")){
						text_search = text_search.replaceAll("N", "Ñ");
						text_search = text_search.replaceAll("n", "Ñ");
					}
					//sorter.setRowFilter( RowFilter.regexFilter("(?i)" + ((JTextField) evt.getSource()).getText().trim(), cboSearchColumn.getSelectedIndex() + (getBooleanZero() ? 1:0)) );
					sorter.setRowFilter( RowFilter.regexFilter("(?i)" + String.format("(%s|%s)", text_search, ((JTextField) evt.getSource()).getText().trim()), cboSearchColumn.getSelectedIndex() + (getBooleanZero() ? 1:0)) );
					
					sorter.setSortable(0, !getBooleanZero());
				}
			});
			txtSearch.grabFocus();

			//tblDefault.getTableHeader().setEnabled(false);
			//TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblDefault.getModel());
			//sorter.setSortable(0, getBooleanZero());

			tblDefault.setRowSorter(new TableRowSorter(tblDefault.getModel()) {
				@Override
				public boolean isSortable(int column) {
					return true;
				}
			});


			tblDefault.registerKeyboardAction(this, "Enter", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

			//Added by Alvin Gonzales (2105-05-18) due to Ma'am Nelia's request to make the Client Name column the default search in Client's Lookup
			
			//System.out.printf("Display Value of Filter CLient Name: %s%n", isFilterName());
			if(isFilterName()){
				//System.out.println("Dumaan dito sa Filter name Ng Index 1");
				cboSearchColumn.setSelectedIndex(1);
			}
			System.out.printf("Display value of Filter Card name: %s%n", isFilterCardName());
			if(isFilterCardName()){
				//System.out.println("Dumaan dito sa Filter Card Name");
				cboSearchColumn.setSelectedIndex(2);
			}
			
			if(filter_index != null){
				cboSearchColumn.setSelectedIndex(filter_index);
			}

			/*tblDefault.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
			tblDefault.getActionMap().put("Enter", new AbstractAction() {
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent ae) {
					returnValue();
				}
			});*/

			tblDefault.packAll();
		} catch (SQLException sqlex) {
			returnException(sqlex, strSQL);
		} catch (NullPointerException npe){
			returnException(npe, strSQL);
		} catch (IllegalArgumentException iae){
			returnException(iae, strSQL);
		} catch (ClassNotFoundException e) {
			returnException(e, strSQL);
		}
		tblDefault.packAll(); //ADDED BY LESTER 2017-09-22 FOR RESIZING OF DIALOG
	} // resetValues

	private JXTable customTable(final Object[][] tblValues, Object[] headerName, final JComboBox cboSearchColumn, final Map<Integer, Class> mapClass){//TODO customTable

		TableModel tbmCustomModel = new DefaultTableModel(tblValues, headerName){
			private static final long serialVersionUID = 1L;
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if(columnIndex == 0){
					if(getBooleanZero()){
						return Boolean.class;
					}else{
						return mapClass.get(columnIndex);
					}
				}else{
					return mapClass.get(columnIndex);
				}
			}
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return colIndex == 0 ? getBooleanZero():false;
			}
		};


		JXTable tblCustom = new JXTable(tbmCustomModel);

		// Set Width and Cell Alignment of Columns
		tblCustom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblCustom.setAutoCreateRowSorter(true);

		tblCustom.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		tblCustom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JXTable tblCustom = (JXTable) evt.getSource();
				if(tblCustom.rowAtPoint(evt.getPoint()) != -1){
					// Return value to internal frame on double click
					tblCustom.setColumnSelectionAllowed(false);
					tblCustom.setCellSelectionEnabled(false);
					tblCustom.setRowSelectionAllowed(true);
					if(evt.getClickCount() == 2) {
						returnValue();
					}
				}else{
					tblCustom.clearSelection();
				}
			}
		});
		tblCustom.getTableHeader().setToolTipText("Click here to search on this column.");

		tblCustom.getTableHeader().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent evt) {
				JXTable tblCustom = ((JXTableHeader) evt.getSource()).getXTable();
				TableColumnModel colModel = tblCustom.getColumnModel();

				int index = colModel.getColumnIndexAtX(evt.getX());

				if(getBooleanZero() && index == 0){
					return;
				}

				try {
					tblCustom.setCellSelectionEnabled(false);
					tblCustom.setColumnSelectionAllowed(true);
					tblCustom.setColumnSelectionInterval(0, index);
					cboSearchColumn.setSelectedIndex(getBooleanZero() ? index -1 : index);
				} catch (IllegalArgumentException e) { }
			}
		});

		//tblCustom.setHighlighters(HighlighterFactory.createTreeStriping(new Color(225, 225, 225), GlbVariables.cHover));

		//tblCustom.setColumnControlVisible(true);
		tblCustom.getColumnControl().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		if(getBooleanZero()){
			rolloverAdapter = new RolloverMouseAdapter(tblCustom);
			renderer = new RolloverBooleanRenderer(rolloverAdapter);

			CheckBoxHeader.RolloverAdapter ma = new CheckBoxHeader.RolloverAdapter(tblCustom);
			tblCustom.getTableHeader().addMouseListener(ma);
			tblCustom.getTableHeader().addMouseMotionListener(ma);

			tblCustom.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(tblCustom, ma));

			tblCustom.addMouseListener(rolloverAdapter);
			tblCustom.addMouseMotionListener(rolloverAdapter);

			tblCustom.getColumnModel().getColumn(0).setCellRenderer(renderer);
		}

		tblCustom.setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
		tblCustom.setDefaultRenderer(Timestamp.class, DateRenderer.getDateRenderer());
		tblCustom.setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		tblCustom.setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));
		tblCustom.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
		tblCustom.packAll();

		return tblCustom;
	}  // customTable

	/*private void searchText(String keyWord, int rows){//TODO searchText
		int col = cboSearchColumn.getSelectedIndex();
		Rectangle rect;
		Boolean bl = false;

		try {
			for(int i=0;i<rows;i++){
				String value = null;
				try {
					//value = (String)tblDefault.getValueAt(i, col);
					value = tblDefault.getValueAt(i, col).toString();
				} catch (ClassCastException e) {
					value = "";
				}

				//If the item is already in the table
				if(value.trim().toUpperCase().startsWith(keyWord) && bl == false){
					//System.out.println("Searching text...");
					tblDefault.setRowSelectionInterval(i, i);
					rect = tblDefault.getCellRect(i, col, true);
					tblDefault.scrollRectToVisible(rect);
					bl = true;
				}
			}
		} catch (NullPointerException e) { }
	}*/ // searchText

	private void returnValue() {//TODO returnValue
		if(!getBooleanZero()){
			if(tblDefault.getSelectedRows().length > 0){
				int tblColumns = tblDefault.getColumnCount();
				Object[] ReturnDataSet = new Object[tblColumns];

				for(int x=0; x<tblColumns;x++){
					try {
						ReturnDataSet[x] = tblDefault.getValueAt(tblDefault.getSelectedRow(), x );
					} catch (IndexOutOfBoundsException e) {
						Dialogz.showException(e, JOptionPane.ERROR_MESSAGE);
					}
				}

				setReturnDataSet(ReturnDataSet);

				String retName;
				try{
					try {
						// set the return string as set in the JTextLookup's returnColumn property
						retName = tblDefault.getValueAt(tblDefault.getSelectedRow(), txtReturn.getReturnColumn() ).toString();
					} catch( ArrayIndexOutOfBoundsException aoe ){
						retName = (String) tblDefault.getValueAt(tblDefault.getSelectedRow(), 1 );
					} // try-catch ARRAY INDEX
				} catch( NullPointerException npe ){
					retName = "null";
				} // try-catch NULL

				try {
					txtReturn.setText( retName );
					txtReturn.setValue(retName);
					txtReturn.setDataSet( ReturnDataSet );
					txtReturn.setRequired(false);
				} catch (NullPointerException e) { /*e.printStackTrace();*/ }

				setOK(true);
				dispose();
			}else{
				JOptionPane.showMessageDialog(this, "Please select item.", txtReturn.getTitle(), JOptionPane.WARNING_MESSAGE);
			}
		}else{
			//System.out.println("**********Boolean!");
			List<Integer> list = listOfSelected();
			int rowCount = list.size();
			int colCount = tblDefault.getColumnCount();

			Object[][] listObject = new Object[rowCount][colCount -1];
			for(int x=0; x < rowCount; x++){
				for(int y=1; y < colCount; y++){
					listObject[x][y-1] = tblDefault.getValueAt(list.get(x), y);
				}
			}
			setResult(listObject);

			setOK(true);
			dispose();
		}
	} // returnValue

	private List<Integer> listOfSelected() {
		List<Integer> list = new ArrayList<Integer>();
		for(int x=0; x < tblDefault.getRowCount(); x++){
			if((Boolean) tblDefault.getValueAt(x, 0)){
				list.add(x);
			}
		}
		return list;
	}

	//Disabled 2015-01-30 by Alvin Gonzales
	/*private void selectOnList() {
		for(int x=0; x < tblDefault.getRowCount(); x++){
			try {
				tblDefault.setValueAt(listOfSelected.contains(tblDefault.getValueAt(x, 1)), x, 0);
			} catch (NullPointerException e) {
				tblDefault.setValueAt(false, x, 0);
			}
		}
	}*/

	private void returnException(Exception e, String strSQL) {
		System.out.printf("%n%nSQL: %s%n", strSQL);

		dispose();
		//e.printStackTrace();

		setReturnDataSet(null);

		if(e instanceof IllegalArgumentException){
			try {
				JOptionPane.showMessageDialog(this.getOwner(), txtReturn.getNullSQLWarning(), this.getTitle(), JOptionPane.WARNING_MESSAGE);
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(this.getOwner(), "No records to show.", this.getTitle(), JOptionPane.WARNING_MESSAGE);
			}
		}else{
			Dialogz.showException(this.getOwner(), e, JOptionPane.ERROR_MESSAGE);
		}


		setOK(true);
		dispose();
	}

	private void btnState(Boolean cCombo, Boolean tText, Boolean bOk, Boolean bRefresh, Boolean bCancel){//TODO btnState
		cboSearchColumn.setEnabled(cCombo);
		txtSearch.setEditable(tText);
		btnOK.setEnabled(bOk);
		btnRefresh.setEnabled(bRefresh);
		btnCancel.setEnabled(bCancel);
	} // btnState

	/**
	 * @return the result
	 */
	public Object[][] getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object[][] result) {
		this.result = result;
	}

	public Object[] getReturnDataSet() {
		return ReturnDataSet;
	}

	public void setReturnDataSet(Object[] returnDataSet) {
		ReturnDataSet = returnDataSet;
	}

	/**
	 * @return the booleanZero
	 */
	public Boolean getBooleanZero() {
		return booleanZero;
	}

	/**
	 * @param booleanZero the booleanZero to set
	 */
	public void setBooleanZero(Boolean booleanZero) {
		this.booleanZero = booleanZero;
	}

	/**
	 * @return the oK
	 */
	public boolean isOK() {
		return OK;
	}

	/**
	 * @param oK the oK to set
	 */
	public void setOK(boolean oK) {
		OK = oK;
	}

	/**
	 * @return the rolloverAdapter
	 */
	public RolloverMouseAdapter getRolloverAdapter() {
		return rolloverAdapter;
	}

	/**
	 * @param rolloverAdapter the rolloverAdapter to set
	 */
	public void setRolloverAdapter(RolloverMouseAdapter rolloverAdapter) {
		this.rolloverAdapter = rolloverAdapter;
	}

	/**
	 * @return the renderer
	 */
	public RolloverBooleanRenderer getRenderer() {
		return renderer;
	}

	public static class RolloverMouseAdapter extends MouseAdapter {
		private int row = -2;
		private int column = -1;
		private JTable table;

		public RolloverMouseAdapter(JTable table) {
			this.table = table;
		}

		public boolean isRolloverCell(int row, int column) {
			return this.row == row;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//JTable table = (JTable) e.getSource();

			int lastRow = row;
			int lastColumn = column;

			//row = table.rowAtPoint(e.getPoint());

			try {
				row = ((JTable) e.getSource()).rowAtPoint(e.getPoint());
			} catch (Exception e1) {
				row = -1;
			}

			column = table.columnAtPoint(e.getPoint());

			if (row == lastRow && column == lastColumn)
				return;

			if (row >= 0 && column >= 0) {
				table.repaint(table.getCellRect(row, column, false));
			}
			if (row == -1 && column >= 0) {
				table.getTableHeader().repaint(table.getCellRect(row, column, false));
			}

			if (lastRow >= 0 && lastColumn >= 0) {
				table.repaint(table.getCellRect(lastRow, lastColumn, false));
			}
			if (lastRow == -1 && lastColumn >= 0) {
				table.getTableHeader().repaint(table.getCellRect(row, column, false));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (row >= 0 && column >= 0) {
				table.repaint(table.getCellRect(row, column, false));
			}
			if (row == -1 && column >= 0) {
				table.getTableHeader().repaint(table.getCellRect(row, column, false));
			}
			row = column = -2;
		}
	}

	public static class RolloverBooleanRenderer extends JCheckBox implements TableCellRenderer, UIResource {
		private static final long serialVersionUID = 1L;
		private static final Border noFocusBorder = new EmptyBorder(0, 0, 0, 0);
		private RolloverMouseAdapter adapter;

		public RolloverBooleanRenderer(RolloverMouseAdapter adapter) {
			super();
			this.adapter = adapter;
			setHorizontalAlignment(JLabel.CENTER);
			setBorderPainted(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			getModel().setRollover(adapter.isRolloverCell(row, column));

			//System.out.println(row + ", " + column);

			if (isSelected) {
				setForeground(table.getSelectionForeground());
				//super.setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				//setBackground(table.getBackground());
			}
			try {
				setSelected( (value != null && ((Boolean) value).booleanValue()) );
			} catch (ClassCastException e) {
				setSelected( false );
			}
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			setBorder(noFocusBorder);
			if (hasFocus) {
				setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			} else {
				setBorder(noFocusBorder);
			}

			return this;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if(action.equals("Enter")){
			returnValue();
		}
		if(action.equals("Escape")){
			dispose();
		}
	}

	public boolean isFilterName() {
		return filterName;
	}

	public void setFilterClientName(boolean filterClientName) {
		this.filterName = filterClientName;
	}
	
	public boolean isFilterCardName(){
		return filterCardname;
	}
	
	public void setFilterCardName(boolean filterCardName){
		this.filterCardname = filterCardName;
	}
	
	public Integer filterIndex3(){
		return filter_index;
	}
	
	public void setFilterIndex(Integer index){
		this.filter_index = index;
	}

}
